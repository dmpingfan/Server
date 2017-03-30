package Json;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dangmei on 2017/3/8.
 */
public class ClientJson {
    private static final Logger logger = Logger.getLogger(ClientJson.class.getName());
    static {
        PropertyConfigurator.configure("conf/log4j.properties");
    }
    public static String command="Register";
    public static String thirdUserId=null;
    public static String username=null;
    public static String tel=null;
    public static String email="client@java.com";
    public static String orgId="1001";
    public static String pin="1";
    public static String serialNum="123456";

    //Register
    public static String password="1";
    //Sign
    public static String Msg="";//验证码已关，后期修改
    public static String Plaintext=null;
    public static String ticket=null;
    public static String ClientType="Android";
    //Verify also has Plaintext
    public static String cert=null;
    public static String signData=null;
    /*
     *JSONArray [1,2,3,4]   JSONArray.put(1) ...
     *JSONObject {"name","lisi"}   JSONObject.put("name","lisi"):
    */

    public static void main(String[] args) throws Exception{
        String s="{\"SignV\":{\"cert\":\"11\",\"signAlgorithm\":\"22\",\"signature\":\"33\"},\"Data\":{\"orgId\":\"1001\",\"username\":\"1\",\"cert\":\"certcertcert\",\"Plaintext\":\"company:1\",\"pin\":\"1\",\"email\":\"client@java.com\",\"tel\":\"1\",\"command\":\"Verify\",\"thirdUserId\":\"1\",\"serialNum\":\"123456\",\"signData\":\"0x0x0x0xSignData\"}}";
        JSONObject jsonResponse=new JSONObject(s);
        String data=jsonResponse.getString("Data");
        logger.debug(data);
        JSONObject jsonData=new JSONObject(data);
        String signData=jsonData.getString("signData");
        logger.debug(signData);

     /*   ClientJson clientJson =new ClientJson();
        clientJson.setCommand("Register");
        clientJson.setTel("1");
        clientJson.getJson();

        ClientJson clientJson1 =new ClientJson();
        clientJson.setCommand("Sign");
        clientJson.setPlaintext("company:1");
        clientJson.setTicket("ticket0x0x");
        clientJson.setTel("1");
        clientJson.getJson();

        ClientJson clientJson2 =new ClientJson();
        clientJson.setCommand("Verify");
        clientJson.setPlaintext("company:1");
        clientJson.setCert("certcertcert");
        clientJson.setSignData("0x0x0x0xSignData");
        clientJson.setTel("1");
        clientJson.getJson();
        */
    }
    public static void setJson(String s) throws JSONException {
        return;
    }

    public static String getJson() throws JSONException {
        JSONObject Data=new JSONObject();
        JSONObject SignV=new JSONObject();
        JSONObject Result=new JSONObject();
        //默认注册
        if (tel==null&&username==null&&thirdUserId==null){
            logger.error("tel,username and thirdUserId all null. please use settel/setusername/setthirdUserId");
            return null;
        }else{
            String id=tel!=null?tel:(username!=null?username:(thirdUserId!=null?thirdUserId:null));
            if(id==null){
                return null;
            }else{
                tel=tel!=null?tel:id;
                username=username!=null?username:id;
                thirdUserId=thirdUserId!=null?thirdUserId:id;
            }
        }
        if (command.equals("Register")){
            Data.put("password",password);
        }else if (command.equals("Sign")){

            if (Plaintext==null){
                logger.error("Sign.Plaintext null. please use setPlaintext");
                return null;
            }
            if (ticket==null){
                logger.error("Sign.ticket null. please use setticket");
                return null;
            }
            if (ClientType==null){
                logger.error("Sign.ClientType null. please use setClientType");
                return null;
            }
            if (Msg==null){
                logger.error("Sign.Msg null. please use setMsg");
                return null;
            }
            Data.put("Plaintext",Plaintext);
            Data.put("ticket",ticket);
            Data.put("ClientType",ClientType);
            Data.put("Msg",Msg);
        }else if (command.equals("Verify")){
            if (Plaintext==null){
                logger.error("Verify.Plaintext null. please use setPlaintext");
                return null;
            }
            if (cert==null){
                logger.error("Verify.cert null. please use setcert");
                return null;
            }
            if (signData==null){
                logger.error("Verify.signData null. please use setsignData");
                return null;
            }
            Data.put("Plaintext",Plaintext);
            Data.put("cert",cert);
            Data.put("signData",signData);
        }else{
            logger.error("no support command");
        }

        Data.put("command",command);
        Data.put("tel",tel);
        Data.put("username",username);
        Data.put("thirdUserId",thirdUserId);
        Data.put("email",email);
        Data.put("orgId",orgId);
        Data.put("pin",pin);
        Data.put("serialNum",serialNum);

        SignV.put("cert","11");
        SignV.put("signAlgorithm","22");
        SignV.put("signature","33");
        Result.put("SignV",SignV);
        Result.put("Data",Data);

        //String jsonStr_Reg = "{'Data':{'command':'Register','thirdUserId':'test51','username':'test51','email':'zwqxcd@qq.com','orgId':'1001','pin':'11','password':'1234','serialNum':'123456','tel':'18660160509','Msg':'945183'},'SignV':{'cert':'11','signAlgorithm':'22','signature':'33'}}";
        //String jsonStr_Sign =   "{'Data':{'command':'Sign','thirdUserId':'test168','Msg':'945824','username':'test168','email':'zwqxcd@qq.com','Plaintext':'abcdefghijklmnopqrstuvwxyz123456','orgId':'1001','pin':'11','serialNum':'123456','tel':'18660160509','ticket':'eyJEYXRhIjp7ImF1ZCI6InRlc3QxNjgiLCJleHAiOiIyMDE3MDMwMTE2NDMxOSIsImZyb21fdXNlciI6IiIsImlhdCI6IjIwMTcwMzAxMTYzODE5IiwiaXNzIjoiMTAwMSIsInN1YiI6InRlc3QxNjgiLCJ0YXJnZXRfdXNlciI6IiJ9LCJTaWduViI6eyJjZXJ0IjoiMTEiLCJzaWduQWxnb3JpdGhtIjoiMjIiLCJzaWduYXR1cmUiOiIzMyJ9fQ==','ClientType':'Android'},'SignV':{'cert':'11','signAlgorithm':'22','signature':'33'}}";
        //String jsonStr_Verify = "{'Data':{'thirdUserId':'test168','username':'test168','cert':'AAEAAK7ZMl5aT/+HsoULGnHUP+3EDLeHPP7RPZLxORS503EX3r9y7JjbzRFDEkpOuQKvvvXmQDs7ZOfD3WAcTrHntRI=','command':'Verify','Plaintext':'abcdefghijklmnopqrstuvwxyz123456','email':'zwqxcd@qq.com','orgId':'1001','pin':'11','serialNum':'123456','signData':'6EX8VAIZ4c7664AuGCKNylQPs+19bdaUdcFwfN38TgD+di6DkRs5lm8KaWph5+Ielwp1/WQWtYq6fo3NPiHEMA==','tel':'18660160509'},'SignV':{'cert':'11','signAlgorithm':'22','signature':'33'}}";
        //logger.debug("Register模板："+jsonStr_Reg);
        //logger.debug("Sign模板："+jsonStr_Sign);
        //logger.debug("Verify模板："+jsonStr_Verify);
        logger.debug("组合报文结果："+Result);
        return Result.toString();
    }

    public static String getCommand() {
        return command;
    }

    public static String getThirdUserId() {
        return thirdUserId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getTel() {
        return tel;
    }

    public static String getEmail() {
        return email;
    }

    public static String getOrgId() {
        return orgId;
    }

    public static String getPin() {
        return pin;
    }

    public static String getSerialNum() {
        return serialNum;
    }

    public static String getPassword() {
        return password;
    }

    public static String getMsg() {
        return Msg;
    }

    public static String getPlaintext() {
        return Plaintext;
    }

    public static String getTicket() {
        return ticket;
    }

    public static String getClientType() {
        return ClientType;
    }

    public static String getCert() {
        return cert;
    }

    public static String getSignData() {
        return signData;
    }

    public static void setCommand(String command) {
        ClientJson.command = command;
    }

    public static void setThirdUserId(String thirdUserId) {
        ClientJson.thirdUserId = thirdUserId;
    }

    public static void setUsername(String username) {
        ClientJson.username = username;
    }

    public static void setTel(String tel) {
        ClientJson.tel = tel;
    }

    public static void setEmail(String email) {
        ClientJson.email = email;
    }

    public static void setOrgId(String orgId) {
        ClientJson.orgId = orgId;
    }

    public static void setPin(String pin) {
        ClientJson.pin = pin;
    }

    public static void setSerialNum(String serialNum) {
        ClientJson.serialNum = serialNum;
    }

    public static void setPassword(String password) {
        ClientJson.password = password;
    }

    public static void setMsg(String msg) {
        Msg = msg;
    }

    public static void setPlaintext(String plaintext) {
        Plaintext = plaintext;
    }

    public static void setTicket(String ticket) {
        ClientJson.ticket = ticket;
    }

    public static void setClientType(String clientType) {
        ClientType = clientType;
    }

    public static void setCert(String cert) {
        ClientJson.cert = cert;
    }

    public static void setSignData(String signData) {
        ClientJson.signData = signData;
    }
}
