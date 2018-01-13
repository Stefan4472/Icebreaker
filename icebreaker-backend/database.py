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

class Database:
    def __init__(self):
        self.conn = sql.connect(database_name)
        self.c = self.conn.cursor()
        self.create_tables()

    def log_mistake(self, error):
        """If something goes wrong, ignore it (don't crash) and save the error"""
        with open('ignores.log', 'a') as f:
            f.write("Had conflict at {time}.\nError was {error}\n".format(time=datetime.now().strftime('%Y-%m-%d %H:%M:%S'), error=str(error)))

    def create_tables(self):
        """
        Creates the table for the first time
        """
        self.c.execute("""CREATE TABLE IF NOT EXISTS user_info(
            user_id integer primary key autoincrement,
            name text not null
        )""")
        self.c.execute("""CREATE TABLE IF NOT EXISTS rooms_info(
            room_password text primary key check(length(room_password) == 4), room_name text not null,
            room_description text,
            expire_date datetime not null,
            dating boolean not null check(dating in (0,1))
        )""")
        self.c.execute("""CREATE TABLE IF NOT EXISTS room_users(
            id integer primary key autoincrement,
            room_password text,
            user_id integer
        )""")
        self.c.execute("""CREATE UNIQUE INDEX IF NOT EXISTS room_user ON room_users(room_password, user_id)""")
        self.c.execute("""CREATE TABLE IF NOT EXISTS user_chats(
            chat_id integer primary key autoincrement,
            user1_id integer not null,
            user2_id integer not null,
            CONSTRAINT user_order check(user1_id<user2_id)
        )""")
        self.c.execute("""CREATE UNIQUE INDEX IF NOT EXISTS user_chat ON user_chats(user1_id, user2_id)""")
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
            with open('user_photos/{0}'.format(user_id), 'rb') as f:
                # Photo is baseencoded, we don't need to decode it though
                f.write(photo)
            self.conn.commit()
            return user_id
        except sql.IntegrityError as e:
            # insert failed (probably user_name is null)
            self.log_mistake(e)
            return None

    def get_rooms(self, user_id):
        """
        Gets all the rooms that a user is in
        """
        self.c.execute("SELECT room_password WHERE user_id = ?", (user_id,))
        return self.c.fetchall()

if __name__ == '__main__':
    db = Database()
