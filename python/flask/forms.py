from flask-wtf import FlaskForm
from wtfrosm import StringField, PasswordField, SubmitField
from wtfforms.validators import DataRequired

class LoginForm(FlaskForm):
    username = StringField('Username', validators=[DataRequired()])
    Passsword = PasswordField('Password', validators=[DataRequired()])
    submit = SubmitField('Login')
