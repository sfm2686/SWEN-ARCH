from django import forms
from secureweb.models import UserInfo
from django.contrib.auth.models import User


class UserForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput())
    car_id = forms.IntegerField(min_value=0, max_value=1000)
    class Meta():
        model = User
        fields = ('username','password')
#
# class UserProfileInfoForm(forms.ModelForm):
#     class Meta():
#         model = UserProfileInfo
#         fields = ('portfolio_site','profile_pic')
