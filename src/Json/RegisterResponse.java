package Json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TOM on 2017/3/31.
 */
public class RegisterResponse extends Response {
    String Command;
    String result;
    String errorMsg;

    public static void main(String[] args) {
        //String s=new RegisterResponse("Register", "00", "注册成功").toString();
        String s=new RegisterResponse("{\"Command\":\"Register\",\"result\":\"00\",\"errorMsg\":\"注册成功\"}").getResult();
        System.out.println(s);
    }
    public RegisterResponse(String jsonString) {
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            setCommand(jsonObject.getString("Command"));
            setResult(jsonObject.getString("result"));
            setErrorMsg(jsonObject.getString("errorMsg"));
        }catch (JSONException e){
            System.out.println("报文格式错误");
        }
    }

    public RegisterResponse(String command, String result, String errorMsg) {
        this.Command = command;
        this.result = result;
        this.errorMsg = errorMsg;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"Command\":\"")
                .append(Command).append('\"');
        sb.append(",\"result\":\"")
                .append(result).append('\"');
        sb.append(",\"errorMsg\":\"")
                .append(errorMsg).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
