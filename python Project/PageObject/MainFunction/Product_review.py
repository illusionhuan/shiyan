#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Time    : 2023/8/8 9:22
# @Author  : MZ
# @FileName: Product_review.py
# @Software: PyCharm

import time

from selenium.webdriver import Keys
from selenium.webdriver.chrome import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait

from PageObject.BaseConfig.BasePage import BasePage

class Product_review(BasePage):

    def __init__(self, driver, url):
        BasePage.__init__(self, driver, url)

    def open_eF(self):
        self.get()

    # 输入OCID
    def input_username_test(self, username):
        self.send_text(username, By.ID, "username")

    # 输入密码
    def input_password_test(self, password):
        self.send_text(password, By.ID, "password")

    def click_login_test(self):
        self.left_click(By.ID,"submitbtn")

    def login_complete_test(self,username,password):
        self.open_eF()
        self.input_username_test(username)
        self.input_password_test(password)
        self.click_login_test()

