"""
Handles all database operations.
    Schema information held in database_documentation.txt
    As of this docstring, handles users' and rooms' information

Whenever another process wants information about users or rooms, it should request it from this file.
This file should not return handles (multithreading is not setup) so the input and output data is abstracted from db
"""
__author__ = 'cj'
import sqlite3 as sql
from datetime import datetime

database_name = "server_info.db"
def log_mistake(error):
    """If something goes wrong, ignore it (don't crash) and save the error"""
    with open('/home/partrico/Icebreaker/icebreaker-backend/ignores.log', 'a') as f:
        f.write("Had conflict at {time}.\nError was {error}\n".format(time=datetime.now().strftime('%Y-%m-%d %H:%M:%S'), error=str(error)))

class Database:
    def __init__(self):
        self.conn = sql.connect(database_name)
        self.c = self.conn.cursor()
        self.create_tables()

    def create_tables(self):
        """
        Creates the table for the first time
        """
        self.c.execute("""CREATE TABLE IF NOT EXISTS user_info(
            user_id integer primary key autoincrement,
            name text not null
        )""")
        self.c.execute("""CREATE TABLE IF NOT EXISTS rooms_info(
            room_id integer primary key autoincrement, room_name text not null,
            room_description text,
            expire_date datetime not null,
            dating boolean not null check(dating in (0,1))
        )""")
        self.c.execute("""CREATE TABLE IF NOT EXISTS room_users(
            id integer primary key autoincrement,
            room_id integer,
            user_id integer
        )""")
        self.c.execute("""CREATE UNIQUE INDEX IF NOT EXISTS room_user ON room_users(room_id, user_id)""")
        self.c.execute("""CREATE TABLE IF NOT EXISTS user_chats(
            chat_id integer primary key autoincrement,
            user1_id integer not null,
            user2_id integer not null,
            CONSTRAINT user_order check(user1_id<user2_id)
        )""")
        self.c.execute("""CREATE UNIQUE INDEX IF NOT EXISTS user_chat ON user_chats(user1_id, user2_id)""")
        self.c.execute("""CREATE TABLE IF NOT EXISTS passphrases(
            room_id integer primary key,
            room_passphrase text not null
            )""")
        self.conn.commit()

    def _generate_passphrases(self):
        passphrase = lambda n: ''.join(chr(n//(26**i)%26+97) for i in range(4))
        l = [passphrase(i) for i in range(26**4)]
        from random import shuffle
        shuffle(l)
        self.c.executemany("INSERT INTO passphrases(room_id, room_passphrase) VALUES(?, ?)", (zip(range(26**4), l)))
        self.conn.commit()

    def create_user(self, user_name, photo):
        """
        Creates a new user, adding their photo and name to db
        Returns the new user_id for the user
        """
        try:
            # Not sure if this is thread safe. ID might change between insert!!
            self.c.execute("""INSERT INTO user_info(name) values(?)""", (user_name,))
            user_id = self.c.lastrowid
            # Also add photo
            with open('/home/partrico/Icebreaker/icebreaker-backend/user_photos/{0}'.format(user_id), 'wb') as f:
                # Photo is baseencoded, we don't need to decode it though
                f.write(photo)
            self.conn.commit()
            return user_id
        except sql.IntegrityError as e:
            # insert failed (probably user_name is null)
            log_mistake(e)
            return None

    def _get_rooms(self, user_id):
        """
        Gets all the rooms that a user is in
        """
        self.c.execute("SELECT room_id FROM room_users WHERE user_id = ?", (user_id,))
        return self.c.fetchall()

    def _number_users(self, room_id):
        """
        Get the number of users in a room
        """
        self.c.execute("SELECT * FROM room_users WHERE room_id = ?", (room_id,))
        return sum(1 for _ in self.c)

    def _get_room_info(self, room_id):
        """
        For a given room_id, gets the name, passphrase, description, and current number of users
        """
        self.c.execute("""SELECT (room_name, room_passphrase, room_description)
            FROM rooms_info INNER JOIN passphrases
                ON rooms_info.room_id = passphrases.room_id
            WHERE room_id = ?""", (room_id,))
        return {k: v for (k, v) in zip(
            ('name', 'passphrase', 'description', 'num_users'),
            list(self.c.fetchone()) + [self._number_users(room_id)]
        )}

    def available_rooms(self, user_id):
        """
        Gets the room_id, name, passphrase, description and number of users for each room a user has
        """
        return [self._get_room_info(room_id) for room_id in self._get_rooms(user_id)]

    def get_photo(self, user_id):
        """
        Returns the base64 encoding of a user's profile image
        """
        with open('/home/partrico/Icebreaker/icebreaker-backend/user_photos/{0}.png'.format(user_id), 'rb') as f:
            read_data = f.read()
        return read_data

    def room_users(self, room_passphrase, request_user_id=None):
        """
        Gets all the users from the room, and returns a list of their information
        (excluding the user that requested the data)
        """
        self.c.execute("""SELECT (user_id, name) from room_users
            INNER JOIN user_info ON room_users.user_id = user_info.user_id
            INNER JOIN passphrases ON room_users.room_id = passphrases.room_id
            WHERE room_passphrase = ?
        """, (room_passphrase,))
        return [(user_id, user_name, self.get_photo(user_id))
                for (user_id, user_name) in self.c if user_id != request_user_id]

    # def block_user(self, sender, recipient):
    #     pass

    def view_chat(self, user1_id, user2_id):
        user1_messages = [{
            'sender': user1_id,
            'recipient': user2_id,
            'message': message,
            'timestamp': timestamp,
        } for (message, timestamp) in self.c.execute("""SELECT (content, timestamp) from messages WHERE
        sender_id = ? AND recipient_id = ?""", user1_id, user2_id)]
        user2_messages = [{
                              'sender': user2_id,
                              'recipient': user1_id,
                              'message': message,
                              'timestamp': timestamp,
                          } for (message, timestamp) in self.c.execute("""SELECT (content, timestamp) from messages WHERE
        sender_id = ? AND recipient_id = ?""", user2_id, user1_id)]
        return sorted(user1_messages + user2_messages, key=lambda x: x['timestamp'])

if __name__ == '__main__':
    db = Database()
    print(__file__)