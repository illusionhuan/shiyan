#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Time    : 2023/8/8 16:19
# @Author  : MZ
# @FileName: main.py
# @Software: PyCharm
# 接口自动化测试主函数
# !/usr/bin/env python
# -*- coding:utf-8 -*-
# fileName: main.py

"""
作者：白帝城的农村人
时间：2021年3月
版本：v1.0
内容：接口自动化测试主函数
更新：
"""
from AotoInterfaceTest.conf import settings, logsettings
from AotoInterfaceTest.common import SendEmail
from logging import config, getLogger

config.dictConfig(logsettings.LOGGING_DIC)
log = getLogger('log_msg')
import os
import pytest

if __name__ == '__main__':
    # pytest.main(['-vs' ,f'--alluredir={settings.RESULT_JSON_FILE_PATH}',f'{settings.TEST_CASE_PATH}/'])
    os.system(f'pytest -v -s --alluredir={settings.RESULT_JSON_FILE_PATH} {settings.TEST_CASE_PATH}')
    REPORT = os.popen(settings.GENERATE_ALLURE_REPORT, "r").read()  # 读取CMD内容
    log.info(f'生成allure报告：{REPORT}')
    SendEmail.send_email(settings.SEND_REPORT_EMAIL_PATH)


