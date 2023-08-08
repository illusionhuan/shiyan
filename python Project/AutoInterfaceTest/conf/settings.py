#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Time    : 2023/8/8 17:07
# @Author  : MZ
# @FileName: settings.py
# @Software: PyCharm

"""
配置
"""
import os
import time

# 时间格式
timestamp = time.strftime('%Y-%m-%d %H:%M:%S')

## 获取项目当前运行路径
BASE_PATH = os.path.dirname(
    os.path.dirname(__file__)
)
# 测试数据地址
CASE_TABLE = 'test_Case.xls'

# 需要执行的case py文件
CASE_FILE = 'test_Demo.py'

# 测试用例路径
TEST_CASE_PATH = os.path.join(
    BASE_PATH, 'test_case')

# 获取测试用例EXCEL表格 路径
CASE_DATA_PATH = os.path.join(
       BASE_PATH, 'data', CASE_TABLE)

# 运行日志存放路径
CASE_RUN_PATH = os.path.join(
    BASE_PATH, 'log')


# allure  JONS文件路径
RESULT_JSON_FILE_PATH = os.path.join(
    BASE_PATH,'report','result')

# allure HTML报告路径
RESULT_HTML_FILE_PATH = os.path.join(
    BASE_PATH,'report','HTML')

# 发送邮件 报告所在地址
SEND_REPORT_EMAIL_PATH = os.path.join(
    BASE_PATH,'report','HTML','index.html')

RUN_TEST_CASE = ''

# 生成测试报告命令
GENERATE_ALLURE_REPORT = f'allure generate {RESULT_JSON_FILE_PATH} -o  {RESULT_HTML_FILE_PATH} --clean' #
# GENERATE_ALLURE_REPORT = f'allure generate {RESULT_JSON_FILE_PATH} -c  {RESULT_HTML_FILE_PATH} --clean' #清楚历史数据
# 邮件配置
smtp_server = 'smtp.qq.com'
smtp_user = '@qq.com' # 发件邮箱
smtp_password = ''  # 授权码

sender = smtp_user  # 发件人
receiver = '@126.com'  # 收件人
subject = '自动化接口测试报告'  # 邮件主题


