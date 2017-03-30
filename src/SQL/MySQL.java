package SQL;

import Config.Config;
import java.sql.*;

/**
 * Created by dangmei on 2017/3/8.
 */


public class MySQL {

    //  Database credentials
    static final String url= Config.GetValueByKey("conf/server.properties","myurl");    //JDBC的URL
    static final String username= Config.GetValueByKey("conf/server.properties","myusername");
    static final String password= Config.GetValueByKey("conf/server.properties","mypassword");
    public static void main(String[] args){
        int i=affect("INSERT INTO user (id, pwd, pin, pinCount) VALUES('2','1','1',5)");
        System.out.println("Query OK, "+i+" row affected");
        select("SELECT id, pwd, pin, pinCount FROM user");
        DLL("create database q2");
        DLL("drop database q2");
    }
    /*SQL Select 语句
    * Select
    */
    public static void select(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(url,username,password);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String pwd = rs.getString("pwd");
                String pin = rs.getString("pin");
                int pinCount = rs.getInt("pinCount");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", Age: " + pwd);
                System.out.print(", First: " + pin);
                System.out.println(", Last: " + pinCount);
            }
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se3){
                se3.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
    /*SQL 影响行 语句
    * INSERT UPDATE DELETE
    */
    public static int affect(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(url,username,password);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            int rs = stmt.executeUpdate(sql);
            //STEP 6: Clean-up environment
            stmt.close();
            conn.close();
            return rs;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return 0;
    }//end main
    /*SQL DDL 语句
    * CREATE ALTER DROP TRUNCATE COMMENT RENAME
    */
    public static boolean DLL(String sql) {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(url,username,password);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            boolean rs = stmt.execute(sql);
            //STEP 6: Clean-up environment
            stmt.close();
            conn.close();
            return rs;
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        return false;
    }//end main
}//end FirstExample
