# #!/usr/bin/python3
# # -*- coding: utf-8 -*-
# # @Time    : 2023/8/7 15:42
# # @Author  : MZ
# # @FileName: test.py
# # @Software: PyCharm
#
# from selenium.webdriver import ActionChains
# from selenium import webdriver
# import time
#
# driver=webdriver.Chrome()
# driver.get("https://www.qichacha.com/user_login")
# time.sleep(1)
# driver.find_element_by_xpath('//*[@id="normalLogin"]').click()
# time.sleep(1)
# huakuai=driver.find_element_by_xpath('//*[@id="nc_1_n1z"]')
#
# def get_track(distance):      # distance为传入的总距离
#     # 移动轨迹
#     track=[]
#     # 当前位移
#     current=0
#     # 减速阈值
#     mid=distance*4/5
#     # 计算间隔
#     t=0.2
#     # 初速度
#     v=1
#
#     while current<distance:
#         if current<mid:
#             # 加速度为2
#             a=4
#         else:
#             # 加速度为-2
#             a=-3
#         v0=v
#         # 当前速度
#         v=v0+a*t
#         # 移动距离
#         move=v0*t+1/2*a*t*t
#         # 当前位移
#         current+=move
#         # 加入轨迹
#         track.append(round(move))
#     return track
# def move_to_gap(slider,tracks):     # slider是要移动的滑块,tracks是要传入的移动轨迹
#     ActionChains(driver).click_and_hold(slider).perform()
#     for x in tracks:
#         ActionChains(driver).move_by_offset(xoffset=x,yoffset=0).perform()
#     time.sleep(0.5)
#     ActionChains(driver).release().perform()
#
# if __name__ == '__main__':
#     move_to_gap(huakuai,get_track(340))
