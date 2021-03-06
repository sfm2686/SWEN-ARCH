from flask import Flask
# from flask_login import LoginManager, SecureCookieSessionInterface
from flask_login import LoginManager
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate
from app import config

# app
app = Flask(__name__)
app.config.from_object(config.Config)

# database
db = SQLAlchemy(app)
migrate = Migrate(app, db)

# login manager
login = LoginManager(app)
login.login_view = 'login'

from app import routes
