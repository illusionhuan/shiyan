'''
实现步骤:(1)继承basepage,(2)元素传参,(3)调取方法
测试订单管理功能
'''
import time

from selenium.webdriver import Keys
from selenium.webdriver.chrome import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait

from PageObject.BaseConfig.BasePage import BasePage
from PageObject.MainFunction.LoginPage import Login


class OrderManage(BasePage):
    def __init__(self, driver, url):
        BasePage.__init__(self, driver, url)

    # 进入eF卖家前台网站
    def login_to_order(self, xpath_confirm, OCID, xpath_OCID, pwd, xpath_pwd, xpath_popup):
        lo = Login(self.driver, self.base_url)
        lo.login_complete(xpath_confirm, OCID, xpath_OCID, pwd, xpath_pwd, xpath_popup)

    # 进行登录
    def click_order(self, xpath_order_management):
        self.left_click(By.XPATH, xpath_order_management)

    def click_inventory(self,xpath_inventory):
        self.left_click(By.XPATH, xpath_inventory)

    def click_management(self, xpath_management):
        self.left_click(By.XPATH, xpath_management)
    # 输入出库单号
    def input_number(self, Outbound_number, xpath_send_number):
        self.send_text(Outbound_number, By.XPATH, xpath_send_number)

    # 点击查询按钮
    def click_select(self, xpath_select):
        self.left_click(By.XPATH, xpath_select)

    # 点击详细信息按钮
    def click_detail(self, xpath_detail):
        self.left_click(By.XPATH, xpath_detail)

    def select_warehouse(self,xpath_warehouse,xpath_warehouse_nation):
        self.left_click(By.XPATH,xpath_warehouse)
        time.sleep(2)
        self.left_click(By.XPATH,xpath_warehouse_nation)

    def select_shipments(self,xpath_shipments,xpath_shipment_nation):
        self.left_click(By.XPATH,xpath_shipments)
        time.sleep(2)
        self.left_click(By.XPATH,xpath_shipment_nation)
    def select_methods(self,xpath_methods,xpath_inbound_method):
        self.left_click(By.XPATH,xpath_methods)
        time.sleep(2)
        self.left_click(By.XPATH,xpath_inbound_method)
    def select_date(self,xpath_date,xpath_arrival_time):
        self.left_click(By.XPATH,xpath_date)
        time.sleep(2)
        self.left_click(By.XPATH,xpath_arrival_time)

    def input_SKU(self,test_SKU,xpath_SKU,class_select_SKU):
        self.send_text(test_SKU,By.XPATH,xpath_SKU)
        time.sleep(1)
        self.left_click(By.CLASS_NAME,class_select_SKU)

    def input_SKU_number(self,SKU_number,xpath_SKU_number):
        self.send_text(SKU_number,By.XPATH,xpath_SKU_number)

    def click_next_button(self,xpath_next_button):
        self.left_click(By.XPATH,xpath_next_button)

    # 选择商品(指定数量)
    def select_shop(self,test_SKU,xpath_SKU,class_select_SKU,SKU_number,xpath_SKU_number,xpath_next_button):
        self.input_SKU(test_SKU,xpath_SKU,class_select_SKU)
        time.sleep(1)
        self.clear_text(By.XPATH,xpath_SKU_number)
        self.input_SKU_number(SKU_number,xpath_SKU_number)
        time.sleep(1)
        self.click_next_button(xpath_next_button)
        time.sleep(2)
    def add_shop(self,xpath_all_shop,xpath_confirm_button,xpath_close_button):
        self.left_click(By.XPATH,xpath_all_shop)
        time.sleep(1)
        self.left_click(By.XPATH,xpath_confirm_button)
        time.sleep(2)
        self.left_click(By.XPATH,xpath_close_button)


    # 进入库存管理
    def click_Inventory_management(self,xpath_inventory,xpath_management):
        self.click_inventory(xpath_inventory)
        time.sleep(2)
        self.click_management(xpath_management)
        time.sleep(2)
    # 选择参数
    def select_parse(self,xpath_warehouse,xpath_warehouse_nation,xpath_shipments,xpath_shipment_nation,xpath_methods,xpath_inbound_method,xpath_date,xpath_arrival_time,xpath_next):
        self.select_warehouse(xpath_warehouse,xpath_warehouse_nation)
        time.sleep(1)
        self.select_shipments(xpath_shipments,xpath_shipment_nation)
        time.sleep(1)
        self.select_methods(xpath_methods,xpath_inbound_method)
        time.sleep(1)
        self.select_date(xpath_date, xpath_arrival_time)
        time.sleep(1)
        self.left_click(By.XPATH,xpath_next)

    # 进入订单页面并切换窗口
    def click_order_switch(self, xpath_order_management, outbound_tracking_number, xpath_send_number, xpath_select,
                           xpath_detail):
        self.click_order(xpath_order_management)
        time.sleep(3)
        # 点击订单管理按钮
        self.click_order(xpath_order_management)
        time.sleep(2)

        # 输入出库单号
        self.input_number(outbound_tracking_number, xpath_send_number)
        # 点击查询按钮
        self.click_select(xpath_select)
        time.sleep(1)
        # 点击跳转至详细信息页面
        self.click_detail(xpath_detail)
        # 切换窗口
        self.switch_windows()

        # 下拉滚动条
    def down_scoll(self):
        webElement = self.driver.find_element(By.CSS_SELECTOR, "body")
        # 有的时候必须点击一下，下拉才能生效（有的网站是这样，原因未找到）
        webElement.click()
        # 小幅度下拉
        webElement.send_keys(Keys.PAGE_DOWN)

        # 直接下拉到底
        # webElement.sendKeys(Keys.END);

    # 截取完整屏幕
    def move_screenshot(self, path_up, path_down):

        time.sleep(2)
        self.take_screenshot(path_up)

        time.sleep(2)
        self.down_scoll()
        time.sleep(2)

        self.take_screenshot(path_down)
    # 切换窗口
    def switch_windows(self):
        wait = WebDriverWait(webdriver, 5)
        try:
            # 等待页面加载出新的窗口
            page_load = wait.until(lambda d: len(d.window_handles) > 1)
        except Exception as e:
            text = e
        # 切换到新的窗口
        new_window = self.driver.window_handles[1]
        self.driver.switch_to.window(new_window)
        time.sleep(3)

        # 原来的窗口
        # main_window = self.driver.window_handles[0]

        # 切换回原来的窗口
        # time.sleep(5)
        # self.driver.switch_to.window(main_window)

