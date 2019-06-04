package com.sy.globaltake_driver;

/**
 * Created by sunnetdesign3 on 2017/2/7.
 */
public class Const {
    public static final String IS_FIRST = "is_first";
    //请求响应返回码字段
    public static final String Code = "code";
    //请求成功
    public static final String reques_success = "1";
    public static final String EXTRA_BUNDLE = "extra_bundle";
    public static final String Language = "language";
    public static final String zh = "zh";
    public static final String en = "en";
    //叫车类型
    public static final String Driving = "driving";
    public static final String Moto = "BICYCLING";//摩托车
    public static final String WALKING = "WALKING";//步行
    //是否登录
    public static final String isLogin = "isLogin";
    //司机类型
    public static final String driverType = "driverType";
    //出租车司机类型
    public static final String driverType_taxi = "1";
    //MOTO车司机类型
    public static final String driverType_moto = "2";
    //司机车牌号
    public static final String driverCarNumber = "driverCarNumber";
    //出车
    public static final String carStatus_out = "2";
    //收车
    public static final String carStatus_in = "1";


    /*缓存文件名*/
    //用户手机号
    public static final String USER_PHONE = "user_phone";
    //用户输入的密码（加密前）
    public static final String USER_PWD = "user_pwd";
    /*缓存文件名*/
    //用户Email
    public static final String User_Email = "User_Email";
    //用户ID
    public static final String UserId = "user_id";
    //用户fdPassengerCode
    public static final String fdPassengerCode = "fdPassengerCode";
    //用户fdPassengerSex
    public static final String fdPassengerSex = "fdPassengerSex";
    //用户密码（加密后）
    public static final String fdPassowrd = "fdPassowrd";
    //用户昵称
    public static final String User_NickName = "User_NickName";
    //用户名
    public static final String User_Name = "User_Name";
    //用户xx状态
    public static final String User_Stauts = "User_Stauts";
    //订单状态
    public static final String orderStauts = "orderStauts";
    //用户头像地址
    public static final String User_HeadIcon = "User_HeadIcon";
    //客服电话
    public static final String waiterPhone = "waiterPhone";

    /*计价规则参数留存*/
    //出租车
    public static final String startingfare = "startingfare";//起步价
    public static final String journey = "journey";//里程
    public static final String meter = "meter";//米数
    public static final String moneybymeter = "moneybymeter";//多少钱每多少米
    public static final String moneybyminute = "moneybyminute";//多少钱每分钟
    //摩托车
    public static final String startingfare_moto = "startingfare_moto";//起步价
    public static final String journey_moto = "journey_moto";//里程
    public static final String meter_moto = "meter_moto";//米数
    public static final String moneybymeter_moto = "moneybymeter_moto";//多少钱每多少米
    public static final String moneybyminute_moto = "moneybyminute_moto";//多少钱每分钟

    public static final int GO_GUIDE=100;
    public static final int GO_MAIN=101;
    public static final int SPLASH_DELAY_TIME=1000;//闪屏时间

//    public static final String root_url = "http://120.24.234.123:8080/";//注意必须加上http://
    public static final String root_url = "http://www.global-take.com/";//注意必须加上http://
    //司机登录
    public static final String user_login = root_url+"api/member/login";
    //获取验证码
    public static final String getCode = root_url+"api/member/usercode";
    //重置密码
    public static final String resetPWD = root_url+"api/member/updatePassword";
    //修改头像
    public static final String changeHead = root_url+"api/member/updateheadPortrait";
    //修改其他信息
    public static final String changeOtherInfo = root_url+"api/member/updatememberinfo";
    //退出登录
    public static final String exitLogin = root_url+"api/member/gooutuser";
    //时常更新定位
    public static final String sendMyLocation = root_url+"api/member/driverlocation";
    //行程详情
    public static final String getTripInfo = root_url+"api/trip/tripInfobytripid";
    //订单详情
    public static final String getOrderInfo = root_url+"api/order/orderinformation";
    //司机抢单
    public static final String qiangOrder = root_url+"api/order/roborder";
    //司机出车
    public static final String Chuche = root_url+"api/trip/outcar";
    //司机手车
    public static final String Shouche = root_url+"api/trip/collectcar";

    //取消订单
    public static final String cancleOrder = root_url+"api/trip/quxiaoorder";
    //删除订单
    public static final String deleteOrder = root_url+"api/trip/getclearorder";
    //开始计费
    public static final String startBilling = root_url+"api/trip/actionbilling";
    //结束订单
    public static final String endOrder = root_url+"api/trip/endorder";
    //上传订单价格
    public static final String sendOrderPrice = root_url+"api/order/uploadbyprice";
    //出租车计价规则
    public static final String taxiPriceRule = root_url+"api/trip/valuationrule";
    //摩托车计价规则
    public static final String motoPriceRule = root_url+"api/trip/valuationrulebymo";
    //获取我的消息
    public static final String getMyMsg = root_url+"api/message/mymessage";
    //获取我的行程
    public static final String getMyTrip = root_url+"api/trip/mydrivertrip";
    //获取客服电话
    public static final String getWaiterPhone = root_url+"api/trip/givephone";

    //修改手机号
    public static final String changeCellPhone = root_url+"api/member/updatephone";
    //投诉与反馈
    public static final String tousu = root_url+"api/member/myfeedback";
    //系统消息
    public static final String xtMessage = root_url+"api/message/messagebyid";

    //帮助中心，关于，注册协议
    public static final String webRootUrl = root_url+"api/helpinfo/list?language=zh&key=";

    public static final String KeyRegister="1487140108418E7PB31";//注册协议的key
    public static final String KeyHelp="148714074211464W5FH";//帮助中心的key
    public static final String KeyAbout="148714037641967KSPB";//关于我们的key
//    public static final String googlesevice = "http://www.miui.com/forum.php?mod=attachment&aid=NDM2MTYyMHxjNTBjMjA4OHwxNDkwMTY4MDU2fDIyMzE0NDYxOTh8NDE4NDQzMw%3D%3D&ck=532e35e4";
    public static final String googlesevice = "http://shouji.360tpcdn.com/170316/4b4094bdbf42b275ce190446aa06c524/com.goplaycn.googleinstall_15.apk";


}
