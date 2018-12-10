# Secure Web

To run this application in development/debug mode:

Install and make a virutalenv for this project and once you do that, activate it and run

`(virtualenv)$ pip install -r requirements.txt`

Once your virtualenv is made and you have your dependencies, run the following

```
(virtualenv)$ export FLASK_APP=app
(virtualenv)$ export FLASK_ENV=development
flask run
```
After configuring flask variables, navigate to `http://127.0.0.1:5000` or whatever the flask development server points you too
