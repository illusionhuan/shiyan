#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Time    : 2023/8/8 15:47
# @Author  : MZ
# @FileName: Api_test.py
# @Software: PyCharm

import pytest
import requests

class TestSendRequest():
    total_number = ""
    def test_get_list(self):
        url = "https://reqres.in/api/users?page=2"
        req = requests.get(url=url)
        print(req.json())
        TestSendRequest.total_number = req.json()["total"]
if __name__ == '__main__':
    api = TestSendRequest()
    api.test_get_list()
    print(api.total_number)
