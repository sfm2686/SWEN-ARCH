from flask import Flask, flash, redirect, render_template, request, session
from __init__ import app

@app.route('/')
def home():
    # return render_template('index.html')
    if not session.get('logged_in'):
        print('not logged in')
        return render_template('login.html')
    print('not logged in')
    return render_template('dashboard.html')

@app.route('/login', methods=['POST'])
def login():
    if request.form['password'] == 'password' and request.form['username'] == 'admin':
        session['logged_in'] = True
        print('correct')
    else:
        print('INcorrect')
        flash('Wrong password or username')
    return home()

@app.route("/logout")
def logout():
    session['logged_in'] = False
    return home()

# if __name__ == "__main__":
#     app.run()
