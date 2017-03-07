package SQL;

import Config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by TOM on 2017/3/7.
 */
public class SQLite {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            // 0 连接SQLite的JDBC
            Class.forName("org.sqlite.JDBC");

            // 1 建立一个数据库名tim.db的连接，如果不存在就在当前目录下创建之
            Connection conn = DriverManager.getConnection( Config.GetValueByKey("conf/server.properties","sqilte"));
            Statement stat = conn.createStatement();

            // 2 创建一个表tbl1，录入数据
            stat.executeUpdate("drop table if exists tbl1;");
            stat.executeUpdate("create table if not exists tbl1(name varchar(20), salary int);");// 创建一个表，两列
            stat.executeUpdate("insert into tbl1 values(\'ZhangSan\',8000);"); // 插入数据
            stat.executeUpdate("insert into tbl1 values(\'LiSi\',7800);");
            stat.executeUpdate("insert into tbl1 values(\'WangWu\',5800);");
            stat.executeUpdate("insert into tbl1 values(\'ZhaoLiu\',9100);");
            ResultSet rs = stat.executeQuery("select * from tbl1;"); // 查询数据
            System.out.println("创建表结构录入数据操作演示：");
            while (rs.next()) { // 将查询到的数据打印出来
                System.out.print("name = " + rs.getString("name") + ", "); // 列属性一
                        System.out.println("salary = " + rs.getString("salary") + ", ");// 列属性二
            }
            rs.close();


            // 3 修改表结构，添加字段 address varchar(20) default \'changsha\';
            stat.executeUpdate("alter table tbl1 add column address varchar(20) not null default \'changsha\'; ");// 创建一个表，两列
            stat.executeUpdate("insert into tbl1 values(\'HongQi\',9000,\'tianjing\');"); // 插入数据
            stat.executeUpdate("insert into tbl1(name,salary) values(\'HongQi\',9000);"); // 插入数据
            rs = stat.executeQuery("select * from tbl1;"); // 查询数据
            System.out.println("表结构变更操作演示：");
            while (rs.next()) { // 将查询到的数据打印出来
                System.out.print("name = " + rs.getString("name") + ", "); // 列属性一
                System.out.print("salary = " + rs.getString("salary") + ", ");// 列属性二
                System.out.println("address = " + rs.getString("address")); // 列属性三
            }
            rs.close();
            conn.close(); // 结束数据库的连接
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void test1( String args[] )
    {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE COMPANY " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        CHAR(50), " +
                    " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}
