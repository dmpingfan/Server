package Json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TOM on 2017/3/30.
 */
public class RegisterRequest extends Request{
    /**
     * Command : Register
     * id : 1
     * passwd : 1
     */
    private String Command;
    private String id;
    private String passwd;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"Command\":\"")
                .append(Command).append('\"');
        sb.append(",\"id\":\"")
                .append(id).append('\"');
        sb.append(",\"passwd\":\"")
                .append(passwd).append('\"');
        sb.append('}');
        return sb.toString();
    }

    public RegisterRequest(String jsonString) {
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            setCommand(jsonObject.getString("Command"));
            setId(jsonObject.getString("id"));
            setPasswd(jsonObject.getString("passwd"));
        }catch (JSONException e){
            System.out.println("报文格式错误");
        }
    }

    public RegisterRequest(String command, String id, String passwd) {
        this.Command = command;
        this.id = id;
        this.passwd = passwd;
    }

    public static void main(String[] args) {
        //String s=new RegisterRequest("1","2","3").toString();
        String s =new RegisterRequest("{\"Command\":\"1\",\"id\":\"2\",\"passwd\":\"3\"}").getCommand();
        System.out.println(s);

    }

}
