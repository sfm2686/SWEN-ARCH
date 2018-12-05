from django.db import models
from django.contrib.auth.models import User


class UserInfo(models.Model):
    user = models.OneToOneField(User,on_delete=models.CASCADE)
    user = models.PositiveIntegerField(range(1, 1000))

    def __str__(self):
        return self.user.username
