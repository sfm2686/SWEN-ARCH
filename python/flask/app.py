from flask import Flask, flash, redirect, render_template, request, session, url_for
from forms import LoginForm
from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate
from config import Config
import os

app = Flask(__name__)
app.config.from_object(Config)
# database
db = SQLAlchemy(app)
migrate = Migrate(app, db)

# login manager
# login_manager = LoginManager()
# login_manager.init_app(app)

@app.route('/', methods=['GET', 'POST'])
def login():
    form = LoginForm()
    if form.validate_on_submit():
        flash('Login requested for user {}'.format(
            form.username.data))
        return redirect(url_for('dashboard'))

    return render_template('login.html', form=form)

@app.route('/dashboard')
def dashboard():
    return render_template('dashboard.html')

if __name__ == "__main__":
    app.run()
