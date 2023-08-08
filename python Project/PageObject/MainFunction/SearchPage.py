'''
实现步骤:(1)继承basepage,(2)元素传参,(3)调取方法
测试搜索功能
'''
from selenium.webdriver.common.by import By
from PageObject.BaseConfig.BasePage import BasePage

class Search(BasePage):
    def __init__(self,driver,url):
        BasePage.__init__(self,driver,url)
     #进入百度
    def open_baidu(self):
        self.get()
    #输入搜索内容
    def input_search_content(self,text):
        self.send_text(text,By.ID,"kw")
    #点击按钮
    def click_baidu_search(self):
        self.left_click(By.ID,"su")