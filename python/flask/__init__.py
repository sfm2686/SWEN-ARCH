from flask import Flask
import os

app = Flask(__name__)
app.secret_key = b'_1#m6I"E7Q8z\n\xec]/'

app.run()

# login manager
# login_manager = LoginManager()
# login_manager.init_app(app)

# import routes
