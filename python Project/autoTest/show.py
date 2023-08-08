from selenium import webdriver
from selenium.webdriver.common.by import By
# from PIL import Image
import time

# 配置Chrome浏览器
# 配置Edge浏览器
options = webdriver.ChromeOptions()
options.add_experimental_option("detach",True)
options.add_argument("--start-maximized") #浏览器最大化
# options.add_argument('headless')
driver = webdriver.Chrome(options=options)
# 设置OCID和pwd
OCID = "11941811"
pwd = "Aa123456"

# 打开EF官网首页并点击选择Cookies
def run_webdriver(url):
    driver.get(url)
    driver.find_element(By.XPATH,"/html/body/div[3]/div/div[3]/div/button").click()
    input_login_message(OCID,pwd)

def input_login_message(OCID,pwd):
    driver.find_element(By.XPATH,"//*[@id='area']/form/div[1]/div/div[1]/input").send_keys(OCID)
    driver.find_element(By.XPATH, "//*[@id='area']/form/div[2]/div/div/input").send_keys(pwd)
    driver.find_element(By.ID,"submitbtn").click()
    title = driver.title
    # if(title == "Orange Connex Fulfillment Platform"):
    #     driver.quit()
    # else:
    #     time.sleep(5)

if __name__ == '__main__':
    ef_url = 'https://fulfillment-stage.orangeconnex.com/'
    run_webdriver(ef_url)
