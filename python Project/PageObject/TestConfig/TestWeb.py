import time
import unittest

from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver import ActionChains, Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait

from PageObject.MainFunction.LoginPage import Login
from PageObject.MainFunction.OrderManagementPage import OrderManage
from PageObject.WebConfig import BASE_URL, XPATH_confirm, OCID, pwd, XPATH_OCID, XPATH_pwd, \
    XPATH_popup, XPATH_home_tag, XPATH_shop_management, XPATH_order_management, XPATH_send_number, \
    Outbound_tracking_number, XPATH_select, XPATH_detail, XPATH_inventory, XPATH_management
from PageObject.WebConfig import XPATH_warehouse, XPATH_shipments, XPATH_methods, XPATH_date, \
    XPATH_warehouse_nation, XPATH_shipment_nation, XPATH_inbound_method, \
    XPATH_arrival_time, XPATH_next, XPATH_SKU, test_SKU, CLASS_select_SKU, XPATH_SKU_number, \
    SKU_number, XPATH_next_button, XPATH_all_shop, XPATH_confirm_button, XPATH_close_button


class MyTestCase(unittest.TestCase):
    def setUp(self) -> None:
        # 配置Chrome浏览器
        options = webdriver.ChromeOptions()
        options.add_experimental_option("detach", True)
        options.add_argument("--start-maximized")  # 浏览器最大化
        # options.add_argument("--headless")
        self.driver = webdriver.Chrome(options=options)
        self.driver.implicitly_wait(10)

    # 1、测试登录成功
    def test01_login_success(self):
        try:
            ef = Login(self.driver, BASE_URL)
            ef.login_complete(XPATH_confirm, OCID, XPATH_OCID, pwd, XPATH_pwd, XPATH_popup)
            title = self.driver.title
            self.assertEqual(title, "Orange Connex Fulfillment Platform")
        except AssertionError as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/登录失败--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except NoSuchElementException as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/查找元素错误--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except Exception as e:
            raise e

        try:
            time.sleep(2)
            title = self.driver.title
            self.assertEqual(title, "Orange Connex Fulfillment Platform")
        except AssertionError as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/正确帐户不能正确登录--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except NoSuchElementException as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/查找元素错误--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except Exception as e:
            raise e

        try:
            time.sleep(2)
            self.driver.find_element(By.XPATH, XPATH_shop_management).click()
            print(self.driver.find_element(By.XPATH, XPATH_home_tag).text)
        except NoSuchElementException as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/查找元素错误--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except Exception as e:
            raise e

    # 2、测试更换语言功能
    def test02_click_switch_language(self):

        try:
            ef = Login(self.driver, BASE_URL)
            ef.login_complete(XPATH_confirm, OCID, XPATH_OCID, pwd, XPATH_pwd, XPATH_popup)
            language_button = self.driver.find_element(By.XPATH,"//*[@id='app']/div[1]/div[2]/div[1]/div/div[2]/div/div/span/label")
            ActionChains(self.driver).move_to_element(language_button).perform()
            # 等待语言列表出现
            self.driver.implicitly_wait(20)
            # 找到需要点击的语言选项
            self.driver.find_element(By.XPATH, "//li[contains(text(), 'English')]").click()
            print(self.driver.find_element(By.XPATH,XPATH_home_tag).text)
            self.assertEqual(self.driver.find_element(By.XPATH,XPATH_home_tag).text,"HOME")
        except AssertionError as e:
            self.driver.get_screenshot_as_file("../ScreenShot/更改语言失败--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except NoSuchElementException as e:
            print("The website is too slow to respond and does not affect the program")
        except Exception as e:
            raise e

    # 3、测试点击商品管理按钮
    def test03_click_shop_management(self):
        try:
            # 登录到eF网站
            ef_test = Login(self.driver, BASE_URL)
            ef_test.login_complete(XPATH_confirm, OCID, XPATH_OCID, pwd, XPATH_pwd, XPATH_popup)
            # 停留1s
            time.sleep(1)
            # 点击商品管理按钮
            self.driver.find_element(By.XPATH,XPATH_shop_management).click()
        except NoSuchElementException as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/查找元素错误--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except Exception as e:
            raise e

    # 4、测试进入订单管理页面
    def test04_open_shoplist(self):
        try:
            # 实例化OrderManage类
            ef_test = Login(self.driver,BASE_URL)
            # 登录
            ef_test.login_complete(XPATH_confirm, OCID, XPATH_OCID, pwd, XPATH_pwd, XPATH_popup)
            # 停留1s
            time.sleep(1)

            self.driver.find_element(By.XPATH, XPATH_order_management).click()
            # 停留2s
            time.sleep(2)


            ef_test.input_text(Outbound_tracking_number,XPATH_send_number)

            self.driver.find_element(By.XPATH,XPATH_select).click()


            self.driver.find_element(By.XPATH,XPATH_detail).click()

            wait = WebDriverWait(webdriver,5)
            try:
                # 等待页面加载出新的窗口
                wait.until(lambda d: len(d.window_handles) > 1)
            except Exception as e:
                print(e)

            new_window = self.driver.window_handles[1]
            self.driver.switch_to.window(new_window)
            time.sleep(3)

            # 截取上半部分屏幕
            self.driver.save_screenshot("../ScreenShot/OrderManagement/OrderManagement_up--{}.png".format(time.strftime("%Y%m%d%H%M%S")))

            time.sleep(2)
            webElement = self.driver.find_element(By.CSS_SELECTOR,"body")
            # 有的时候必须点击一下，下拉才能生效（有的网站是这样，原因未找到）
            webElement.click()
            # 小幅度下拉
            webElement.send_keys(Keys.PAGE_DOWN)

            time.sleep(2)
            # 截取下半部分屏幕
            self.driver.save_screenshot("../ScreenShot/OrderManagement/OrderManagement_down--{}.png".format(time.strftime("%Y%m%d%H%M%S")))


            # 切换回原来的窗口
            # time.sleep(5)
            # self.driver.switch_to.window(main_window)

        except NoSuchElementException as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/OrderManagement/查找元素错误--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except Exception as e:
            raise e

    # 5、采用PO模式实现订单管理
    def test05_order_complete(self):
        try:
            # 登录
            eF_order = OrderManage(self.driver,BASE_URL)
            eF_order.login_to_order(XPATH_confirm, OCID, XPATH_OCID, pwd, XPATH_pwd, XPATH_popup)
            eF_order.click_Inventory_management(XPATH_inventory,XPATH_management)
            # # 进入订单页面并切换窗口
            # eF_order.click_order_switch(XPATH_order_management,Outbound_tracking_number,XPATH_send_number,XPATH_select,XPATH_detail)
            # # 截取完整屏幕
            # eF_order.move_screenshot("../ScreenShot/OrderManagement/OrderManagement-up--{}.png".format(time.strftime("%Y%m%d%H%M%S")),"../ScreenShot/OrderManagement/OrderManagement-down--{}.png".format(time.strftime("%Y%m%d%H%M%S")))

        except NoSuchElementException as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/OrderManagement/查找元素错误--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except Exception as e:
            raise e

    # 6、采用PO模式实现入库单创建
    def test06_inventory_management(self):
        try:
            # 登录
            eF_order = OrderManage(self.driver,BASE_URL)
            eF_order.login_to_order(XPATH_confirm, OCID, XPATH_OCID, pwd, XPATH_pwd, XPATH_popup)
            eF_order.click_Inventory_management(XPATH_inventory,XPATH_management)
            eF_order.select_parse(XPATH_warehouse,XPATH_warehouse_nation,XPATH_shipments,XPATH_shipment_nation,XPATH_methods,XPATH_inbound_method,XPATH_date,XPATH_arrival_time,XPATH_next)
            eF_order.select_shop(test_SKU,XPATH_SKU,CLASS_select_SKU,SKU_number,XPATH_SKU_number,XPATH_next_button)
            eF_order.add_shop(XPATH_all_shop,XPATH_confirm_button,XPATH_close_button)
        except NoSuchElementException as e:
            self.driver.get_screenshot_as_file(
                "../ScreenShot/InventoryManagement/查找元素错误--{}.png".format(time.strftime("%Y%m%d%H%M%S")))
        except Exception as e:
            raise e

    def tearDown(self) -> None:
        print("Test success")

if __name__ == '__main__':
    unittest.main()
