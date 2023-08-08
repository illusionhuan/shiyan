#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Time    : 2023/8/8 9:11
# @Author  : MZ
# @FileName: TestWebManagement.py
# @Software: PyCharm

import time
import unittest
import urllib
import cv2
import numpy as np
import pandas as pd

from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver import ActionChains, Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait

from PageObject.MainFunction.Product_review import Product_review

from PageObject.WebConfig import test_URL,username_test,password_test



class MyTestCase_test(unittest.TestCase):

    def setUp(self) -> None:
        # 配置Chrome浏览器
        options = webdriver.ChromeOptions()
        options.add_experimental_option("detach", True)
        options.add_argument("--start-maximized")  # 浏览器最大化
        # options.add_argument("--headless")
        self.driver = webdriver.Chrome(options=options)
        self.driver.implicitly_wait(10)

    def test01_login(self):
        try:
            manage = Product_review(self.driver,test_URL)
            manage.login_complete_test(username_test,password_test)
            title = self.driver.title
            self.assertEqual(title, "I'm Groot")
        except AssertionError as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/登录失败--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except NoSuchElementException as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/查找元素错误--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except Exception as e:
            raise e

    def tearDown(self) -> None:
        print("Test success")

