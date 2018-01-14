__author__ = 'cj'
# A placeholder Flash implementation. Doesn't do anything right now.

from flask import Flask, request, jsonify
import database
DB = database.Database()

app = Flask(__name__)

def _group_get(dct):
    def group_get(*keys):
        for key in keys:
            val = dct.get(key)
            if val is None:
                return val
        return None
    return group_get

@app.route('/')
def hello_world():
    return 'Hello from Flask!'

@app.route('/add_user', methods=['GET','POST'])
def add_new_user():
    """
    Need name and new photo to create new user
    """
    group_get = _group_get(request.values)
    name = group_get('name', 'username', 'user_name')
    photo = group_get('photo')
    if all((name, photo)):
        DB.create_user(name, photo)

@app.route('/rooms_by_user/<userid>')
def get_rooms(userid):
    return jsonify(DB.available_rooms(userid))

@app.route('/add_room', methods=['GET', 'POST'])
def add_new_room():
    """
    Need name and room name. Description, expire date, and dating optional.
    """
    group_get = _group_get(request.values)
    creator = group_get('creator', 'username', 'user_name')
    room_name = group_get('room_name', 'roomname', 'name')
    if all((creator, room_name)):
        description = group_get('description', 'room_description', 'roomdescription')
        expire_date = group_get('expire_date', 'expire')
        dating = group_get('dating')
        DB.create_room(creator, room_name, description, expire_date, dating)

@app.route('/users_by_room', methods=['GET', 'POST'])
def get_users():
    group_get = _group_get(request.values)
    room_passphrase = group_get('passphrase', 'room_passphrase', 'password')
    if room_passphrase:
        creator = group_get('creator', 'username', 'user_name')
        DB.create_room(room_passphrase, creator)

@app.route('/new_message', methods=['GET', 'POST'])
def create_message():
    group_get = _group_get(request.values)
    sender_id = group_get('sender', 'sender_id', 'user1_id')
    recipient_id = group_get('recipient', 'recipient_id', 'user2_id')
    content = group_get('content', 'message')
    if all((sender_id, recipient_id, content)):
        return DB.new_message(sender_id, recipient_id, content)

@app.route('/chats_by_user/<userid>')
def get_chats(userid):
    return jsonify(DB.get_chats(userid))

@app.route('/view_chat', methods=['GET', 'POST'])
def view_chat():
    group_get = _group_get(request.values)
    user1_id = group_get('sender', 'sender_id', 'user1_id')
    user2_id = group_get('recipient', 'recipient_id', 'user2_id')
    if all((user1_id, user2_id)):


# @app.route('/block_user/', methods=['GET', 'POST'])
# def block_user(sender, recipient):
#     group_get = _group_get(request.values)
#     sender = group_get('user1_id', 'sender')
#     recipient = group_get('user2_id', 'recipient')
#     if all((sender, recipient)):
#         return DB.block_user(sender, recipient)
