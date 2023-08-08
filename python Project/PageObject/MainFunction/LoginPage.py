'''
实现步骤:(1)继承basepage,(2)元素传参,(3)调取方法
测试登录功能
'''
import time

from selenium.webdriver.common.by import By
from PageObject.BaseConfig.BasePage import BasePage


class Login(BasePage):
    def __init__(self, driver, url):
        BasePage.__init__(self, driver, url)

    # 进入eF官网
    def open_eF(self):
        self.get()

    # 点击确认选择Cookies按钮
    def click_confirm(self, xpath_confirm):
        self.left_click(By.XPATH, xpath_confirm)

    # 输入OCID
    def input_OCID(self, OCID, xpath_OCID):
        self.send_text(OCID, By.XPATH, xpath_OCID)

    # 输入密码
    def input_pwd(self, pwd, xpath_pwd):
        self.send_text(pwd, By.XPATH, xpath_pwd)

    # 点击按钮
    def click_login(self):
        self.left_click(By.ID, "submitbtn")

    def click_close(self, xpath_popup):
        self.left_click(By.XPATH, xpath_popup)
    def click_close_test(self,xpath_close):
        self.left_click(By.XPATH,xpath_close)
    def login_complete(self, xpath_confirm, OCID, xpath_OCID, pwd, xpath_pwd, xpath_popup):
        self.open_eF()
        self.click_confirm(xpath_confirm)
        self.input_OCID(OCID, xpath_OCID)
        self.input_pwd(pwd, xpath_pwd)
        self.click_login()
        time.sleep(5)
        self.click_close(xpath_popup)
        time.sleep(2)


    def login_test(self, xpath_confirm, OCID, xpath_OCID, pwd, xpath_pwd):
        self.open_eF()
        self.click_confirm(xpath_confirm)
        self.input_OCID(OCID, xpath_OCID)
        self.input_pwd(pwd, xpath_pwd)
        self.click_login()

    def input_text(self, text, xpath_send_number):
        self.send_text(text, By.XPATH, xpath_send_number)
