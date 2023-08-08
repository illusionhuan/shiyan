# 基本页面
from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains  # 鼠标操作


class BasePage():
    '''该类封装所有界面都公用的方法。例如driver,find_element等'''
    '''实例化BasePage类时，实现执行__init__方法，该方法需要传递参数'''

    def __init__(self, driver, url):
        self.driver = driver
        self.base_url = url

    # 进入网站
    def get(self):
        self.driver.get(self.base_url)

    # 元素定位,替代八大定位
    def get_element(self, *locator):
        return self.driver.find_element(*locator)

    # 点击
    def left_click(self, *locator):
        ActionChains(self.driver).click(self.get_element(*locator)).perform()

    # 输入
    def send_text(self, text, *locator):
        self.driver.find_element(*locator).send_keys(text)

    # 清除
    def clear_text(self, *locator):
        self.driver.find_element(*locator).clear()

    # 截图
    def take_screenshot(self, path):
        self.driver.save_screenshot(path)
