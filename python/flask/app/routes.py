from flask import Flask, flash, redirect, render_template, session, url_for, request
from flask_login import current_user, login_user, login_required, logout_user
from app.models import User
from app.forms import LoginForm, RegistrationForm
import os
from app import app, db
from werkzeug.urls import url_parse

@app.route('/', methods=['GET', 'POST'])
def login():
    if current_user.is_authenticated:
        return redirect(url_for('dashboard'))
    form = LoginForm()
    if form.validate_on_submit():
        user = User.query.filter_by(username=form.username.data).first()
        if user is None or not user.check_password(form.password.data):
            flash('Invalid username or password')
            return redirect(url_for('login'))
        login_user(user)
        session.permanent = True
        next_page = request.args.get('next')
        if not next_page or url_parse(next_page).netloc != '':
            next_page = url_for('dashboard')
        return redirect(next_page)
    return render_template('login.html', form=form, session_name=app.session_cookie_name)

@app.route('/dashboard')
@login_required
def dashboard():
    # print('\n\n\n\n')
    # print(app.session_cookie_name)
    return render_template('dashboard.html', session_name=app.session_cookie_name)

@app.route('/profile')
@login_required
def profile():
    return render_template('profile.html', session_name=app.session_cookie_name)

@app.route('/register', methods=['GET', 'POST'])
def register():
    if current_user.is_authenticated:
        return redirect(url_for('dashboard'))
    form = RegistrationForm()
    if form.validate_on_submit():
        user = User(username=form.username.data)
        user.set_password(form.password.data)
        user.validate_and_set_role(form.role.data)
        db.session.add(user)
        db.session.commit()
        return redirect(url_for('login'))
    return render_template('register.html', form=form, session_name=app.session_cookie_name)

@app.route('/logout')
@login_required
def logout():
    logout_user()
    return redirect(url_for('login'))

# @app.before_request
# def inactivity():
#     app.permanent_session_lifetime = timedelta(seconds=6)
