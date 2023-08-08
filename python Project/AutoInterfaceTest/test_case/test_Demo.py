#!/usr/bin/python3
# -*- coding: utf-8 -*-
# @Time    : 2023/8/8 17:44
# @Author  : MZ
# @FileName: test_Demo.py
# @Software: PyCharm

#!/usr/bin/env python
# -*- coding:utf-8 -*-
# fileName: test_Demo.py
import pytest
import json
from AutoInterfaceTest.common.RequestsMethods import RequestsMethod
# from AutoInterfaceTest.common.DataProcee import Procee
# from AutoInterfaceTest.common.Asert import Asert
from logging import config, getLogger
from AutoInterfaceTest.conf import logsettings
import allure
config.dictConfig(logsettings.LOGGING_DIC)

log = getLogger('log_msg')
req = RequestsMethod()
data = Procee()


@allure.feature('测试')
@pytest.mark.parametrize('datas',data.runCase_excel_Data())
def test_fk_api(datas):

     log.info(f'开始测试用例编号:{datas["用例编号"]}  *******************开始测试*****************')
     r = req.request(url=datas['请求URL'], method=datas['请求方式'], headers=json.loads(datas['headers']), data=datas['请求参数'])
     assert r.status_code == int(datas['预期结果'])
     log.info(f'用例编号:{datas["用例编号"]}  *************测试测试完成*************************')

if __name__ == '__main__':

    pytest.main(['-v', '-s', "test_Demo.py"])


