package oraclecontest;


import com.hzz.hzzjdbc.jdbcutil.dbmain.MysqlDao;
import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;

import java.util.ArrayList;
import java.util.List;


public class dbnet {


    //获取用户名，表名 对应的字段名和类型
    public ArrayList get(String OWNER, String TABLE_NAME) {



        String sql = "select * from (select a.OWNER owner,\n" +
                "       a.TABLE_NAME as tablename,\n" +
                "       a.column_name colname,\n" +
                "       decode(a.comments,\n" +
                "              '',\n" +
                "              a.column_name,\n" +
                "              null,\n" +
                "              a.column_name,\n" +
                "              a.comments) as colmean,\n" +
                "       b.data_type coltype\n" +
                "  from all_col_comments A, all_tab_cols B\n" +
                " where 1 = 1\n" +
                "   and a.owner = b.owner\n" +
                "   and a.table_name = b.table_name\n" +
                "   and a.column_name = b.column_name\n" +
                " order by b.column_id ) where tablename='" + TABLE_NAME + "' and owner='" + OWNER + "'\n";
        MysqlDao mysqldb = JdkDataSource.mysqldb;
        List query = mysqldb.query(sql);

        return (ArrayList) query;


    }


    //获取用户的所有表名
    public ArrayList getall(String OWNER) {
        return new ArrayList();
    }


    //获取所有用户名
    public ArrayList getusers() {

        return new ArrayList();

    }
}
