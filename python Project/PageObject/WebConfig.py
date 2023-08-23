
# 网站URL
BASE_URL = "https://fulfillment-stage.orangeconnex.com"
# BASE_URL = "https://fulfillment.orangeconnex.com/"


# 账号和密码
OCID = "11941811"
pwd = "Aa123456"

# OCID = "11545514"
# pwd = "Abc1234%"

# 登录页面所需的XPATH
XPATH_confirm = "/html/body/div[3]/div/div[3]/div/button"
XPATH_OCID = "//*[@id='area']/form/div[1]/div/div[1]/input"
XPATH_pwd = "//*[@id='area']/form/div[2]/div/div/input"
XPATH_popup = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[5]/div/div[3]/div/button"

# 测试网站首页所需的XPATH
XPATH_home_tag = "//*[@id='app']/div[1]/div[1]/div[2]/div/div[1]/div/ul/div[1]/a/li/span"

# XPATH_close = "//*[@id='app']/div[1]/div[3]/div/div[3]/div/div[2]/div[1]/button/span"
# //*[@id="app"]/div[1]/div[3]/div/div[3]/div/div[2]/div[1]/button/span

# 商品管理
XPATH_shop_management = "//*[@id='app']/div[1]/div[1]/div[2]/div/div[1]/div/ul/div[2]/li"
# 订单管理
XPATH_order_management = "//*[@id='app']/div[1]/div[1]/div[2]/div/div[1]/div/ul/div[4]/a"


XPATH_number = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/form/div[1]/div[1]/div/div[1]/div/div/div[1]/div/div[1]/input"
# 输入出库单号的文本框
XPATH_send_number = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/form/div[1]/div[1]/div/div[1]/div/div/div[2]/textarea"

# 出库单号
Outbound_tracking_number = "OCO2000012017337GB"

# 查询订单按钮
XPATH_select = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[2]/div/button[1]"
# 订单详细信息
XPATH_detail = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div/div[2]/div[4]/div[2]/table/tbody/tr/td[17]/div/div/a"

# 库存管理
XPATH_inventory = "//*[@id='app']/div[1]/div[1]/div[2]/div/div[1]/div/ul/div[3]/li"
# 入库单创建
XPATH_management = "//*[@id='app']/div[1]/div[1]/div[2]/div/div[1]/div/ul/div[3]/li/ul/div[1]/a/li"

# 仓配中心
XPATH_warehouse = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[3]/form/div[1]/div[1]/div/div/div/div/div[1]/span/span/i"
# 发货地
XPATH_shipments = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[3]/form/div[1]/div[2]/div/div/div/div/div/span/span/i"
# 入库方式
XPATH_methods = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[3]/form/div[2]/div[1]/div/div/div[1]/div/div[1]/span/span/i"
# 到达日期
XPATH_date = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[3]/form/div[2]/div[2]/div/div/div/input"

# 国家
#TODO
XPATH_warehouse_nation = "/html/body/div[4]/div[1]/div[1]/ul/li[4]"
# 发货
#TODO
XPATH_shipment_nation = "/html/body/div[5]/div[1]/div[1]/ul/li[1]"
# 入库方式
#TODO
XPATH_inbound_method = "/html/body/div[6]/div[1]/div[1]/ul/li[1]"
# 到达时间
#TODO
XPATH_arrival_time = "/html/body/div[7]/div[1]/div/div[2]/table[1]/tbody/tr[3]/td[5]/div"
# 下一步按钮
XPATH_next = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[3]/div/button"

# SKU
#TODO
test_SKU = "au_test"
# 输入SKU
XPATH_SKU = "//*[@id='inputSelf']"
# 选择SKU
CLASS_select_SKU = "el-tree-node__label"

# 商品数量
XPATH_SKU_number = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[1]/form/div[2]/div[3]/table/tbody/tr/td[3]/div/div/div/input"
# 商品数量
#TODO
SKU_number = 1
# 下一步按钮
XPATH_next_button = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/button[3]"
# 添加全部商品
XPATH_all_shop = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[2]/form/div[1]/a[2]"
# 点击确认
XPATH_confirm_button = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[2]/div/button[3]"
# 点击关闭
XPATH_close_button = "//*[@id='app']/div[1]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[1]/button"

username_test = "279716"

password_test = "OrangeConnex@123"

test_URL = "https://ef-sso-stage-hk.orangeconnex.com"