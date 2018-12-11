# Secure Web

### Requirements
You need python 3 to run this project. It is recommended you use
virtualenv for this project in order not to install packages
systemwide.

### Steps
Install and make a virutalenv for this project and once you do that, activate it and run

```
(virtualenv)$ pip install -r requirements.txt
```

Once your virtualenv is made and you have your dependencies installed, run the following

```
(virtualenv)$ export FLASK_APP=app
(virtualenv)$ export FLASK_ENV=development
(virtualenv)$ flask run
```
After configuring flask variables, navigate to `http://127.0.0.1:5000` or whatever the flask development server points you too
