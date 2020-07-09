package action;

import com.hzz.hzzjdbc.jdbcutil.jdkjdbc.JdkDataSource;
import oraclecontest.Myframe;
import oraclecontest.dbnet;

import java.util.ArrayList;
import java.util.Map;

public class dbnetaction {

  /**
   * 生成当前用户所有表的生成语句 owner 用户名
   */
  public void getallteXt(String owner) {

    ArrayList tables = new ArrayList();
    dbnet dbnet = new dbnet();
    tables = dbnet.getall(owner);
    String alltext = "";// 填入文本域的字符串
    for (int i = 0; i < tables.size(); i++) {
      String tablename = (String) tables.get(i);

      alltext += tablename + "\n";
    }
    Myframe.myframe.jf.setText(alltext);// 将内容回显到界面中
    // System.out.println(alltext);
  }



  /**
   * 从数据库中查询不同用户名和表名的生成表的语句,这个是用户名和表名全部输入的时候的方法
   * 
   * @param username 这个是用户名
   * 
   * @param tablename 这个是表名
   */
  public void getteXt(String username, String tablename) {
    dbnet net = new dbnet();
    ArrayList cols = new ArrayList();
    username = username.toUpperCase();
    tablename = tablename.toUpperCase();
    cols = net.get(username, tablename);
    String createtext = "";
    String sqltype = (String) Myframe.myframe.tx_sqltype.getSelectedItem();

    if ("查询".equals(sqltype)) {
      createtext += getjsonsql(cols, username, tablename);// 获取到
      createtext += getsearchsqlpage(cols, username, tablename);
    } else if ("新增".equals(sqltype)) {
      createtext += getjsonsql(cols, username, tablename);// 获取到
      createtext += getinsertsql(cols, username, tablename);// 获取到插入语句
    } else if ("修改".equals(sqltype)) {
      createtext += getjsonsql(cols, username, tablename);// 获取到
      createtext += getupdatesql(cols, username, tablename);// 获取到修改语句
    } else if ("删除".equals(sqltype)) {
      createtext += getjsonsql(cols, username, tablename);// 获取到
      createtext += getdeletesql(cols, username, tablename);// 获取到删除语句

    } else if ("传值转码".equals(sqltype)) {
      createtext += "//传值转码语句为:\n";
      createtext += getchuansql(cols, username, tablename);// 获取到插入语句
    } else if ("json传值".equals(sqltype)) {
      createtext += "//json传值转码语句为:\n";
      createtext += getjsonsql(cols, username, tablename);// 获取到插入语句
    } else if ("liger表".equals(sqltype)) {
      createtext += getligersql(cols, username, tablename);// 获取到插入语句
    } else if ("传值不转码".equals(sqltype)) {
      createtext += "//传值转码语句为:\n";

      createtext += getchuannosql(cols, username, tablename);// 获取到插入语句
    } else if ("插入语句".equals(sqltype)) {
      createtext += "//插入语句为:\n";

      createtext += getinsertsql(cols, username, tablename);// 获取到插入语句
    } else if ("修改语句".equals(sqltype)) {
      createtext += "\n//修改语句为:\n";

      createtext += getupdatesql(cols, username, tablename);// 获取到修改语句
    } else if ("删除语句".equals(sqltype)) {
      createtext += "\n//删除语句为:\n";

      createtext += getdeletesql(cols, username, tablename);// 获取到删除语句
    } else if ("查询语句".equals(sqltype)) {
      createtext += "\n//查询语句为:\n";

      createtext += getsearchsql(cols, username, tablename);// 获取到查询语句
    }

    else if ("分页查询语句".equals(sqltype)) {
      createtext += "\n//带分页的查询语句为:\n";

      createtext += getsearchsqlpage(cols, username, tablename);// 获取到删除语句
    }
    Myframe.myframe.jf.setText("");
    Myframe.myframe.jf.setText(createtext);


  }


  // public String getdeletesql(ArrayList cols, String username, String tablename) {
  // String datas = "";
  // Map date = (Map) cols.get(0);
  // String COLTYPE = (String) date.get("COLTYPE");
  // String COLNAME = (String) date.get("COLNAME");
  // COLNAME = COLNAME.replaceAll("_", "");
  // datas =
  // "delete from " + username + "." + tablename + " where 1=1 and " + COLNAME + "= '\""
  // + COLNAME + "\"' ";
  // return datas;
  // }

  /**
   * 获取到JSON传值语句
   * 
   * @param cols
   * @param username
   * @param tablename
   * @return
   */
  public String getligersql(ArrayList cols, String username, String tablename) {
    String dates = "";
    String requestcol = " ";// 用来存放获取数据的
    int co = 0;
    for (Object object : cols) {
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");
      COLNAME = COLNAME.replaceAll("_", "");

      String COLMEAN = (String) date.get("COLMEAN");
      requestcol += "{display:'" + COLMEAN + "',name:'" + COLNAME.toLowerCase() + "'},\n";
    }
    requestcol += "{ display: '操作', render: function (rowdata, rowindex, value){\n";
    requestcol += "}}\n";
    dates += requestcol + "\n";
    return dates;
  }

  /**
   * 获取到JSON传值语句
   * 
   * @param cols
   * @param username
   * @param tablename
   * @return
   */
  public String getjsonsql(ArrayList cols, String username, String tablename) {
    String dates = "";
    String requestcol = " ";// 用来存放获取数据的
    requestcol +=
        "datatool datatool=new datatool();\n String datas=request.getParameter(\"datas\");\n";
    requestcol += " JSONObject json=datatool.datetojson(datas);\n";
    int co = 0;
    for (Object object : cols) {
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");
      COLNAME = COLNAME.replaceAll("_", "");
      String COLMEAN = (String) date.get("COLMEAN");
      if ("VARCHAR2".equals(COLTYPE) || "NUMBER".equals(COLTYPE)) {// 如果是字符串
        requestcol +=
            "String " + COLNAME + "=String.valueOf(json.get(\"" + COLNAME + "\"));//" + COLMEAN
                + "\n";
        co = co + 1;
      } else {// 如果是时间
        requestcol +=
            "String " + COLNAME + "=String.valueOf(json.get(\"" + COLNAME + "\"));//" + COLMEAN
                + "\n";
        String COLNAME1 = COLNAME;
        COLNAME = COLNAME1 + "_start";
        requestcol +=
            "String " + COLNAME + "=String.valueOf(json.get(\"" + COLNAME + "\"));//" + COLMEAN
                + "\n";
        COLNAME = COLNAME1 + "_end";
        requestcol +=
            "String " + COLNAME + "=String.valueOf(json.get(\"" + COLNAME + "\"));//" + COLMEAN
                + "\n";
      }
    }
    dates += requestcol + "\n";
    return dates;
  }

  // 分页语句
  public String getsearchsqlpage(ArrayList cols, String username, String tablename) {
    String datas = "String sql=\"\";\n";// 插入的语句
    datas += "pageQueryBean pqry =new pageQueryBean(); \n";
    datas += " String strPage = myreq.toCn(request.getParameter(\"page\"));\n";
    datas += "String strPagesize = myreq.toCn(request.getParameter(\"pagesize\"));\n";
    datas += "String strTotalSize =  myreq.toCn(request.getParameter(\"totalSize\")); \n";
    datas +=
        "int pageNo = Integer.parseInt((StringUtils.isBlank(strPage)||"
            + "!StringUtils.isNumeric(strPage))?\"1\":strPage);\n";
    datas +=
        "int pageSize = Integer.parseInt((StringUtils.isBlank(strPagesize)||!StringUtils.isNumeric(strPagesize))?\"10\":strPagesize);\n";
    datas +=
        " int totalSize = Integer.parseInt((StringUtils.isBlank(strTotalSize)||!StringUtils.isNumeric(strTotalSize))?\"-1\":strTotalSize);\n";
    datas += "pqry.setJNDI_BufferSession(session);\n";
    datas += " pqry.setTotalSize(totalSize);\n";
    datas += " pqry.setPageSize(pageSize);\n";
    datas += " pqry.setCurrentPageno(pageNo);\n";
    datas += " pqry.setPackSize(pageSize);\n";
    String wheresql = "";// 后面的查询条件
    String selectsql = "";
    for (Object object : cols) {
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");
      String COLNAME1 = COLNAME.replaceAll("_", "");

      if ("VARCHAR2".equals(COLTYPE) || "NUMBER".equals(COLTYPE)) {// 假如是字符串
        selectsql += "a." + COLNAME + "  " + COLNAME1 + ",";
        wheresql += "\nif(StringUtils.isNotBlank(" + COLNAME1 + ")&&" + COLNAME1 + "!=\"null\"){\n";
        wheresql += "sql+=\" and  a." + COLNAME + "='\"+" + COLNAME1 + "+\"'  \";\n";
        wheresql += "}\n";
      } else if ("DATE".equals(COLTYPE)) {// 如果是日期
        selectsql += "to_char(a." + COLNAME + ",'yyyy-mm-dd hh24:mi:ss') " + COLNAME1 + ",";
        wheresql +=
            "\n if(StringUtils.isNotBlank(" + COLNAME1 + "_start)&&" + COLNAME1
                + "_start!=\"null\"&&StringUtils.isNotBlank(" + COLNAME1 + "_end)&&" + COLNAME1
                + "_end!=\"null\"){\n";
        wheresql +=
            "sql+=\" and  a." + COLNAME + " between to_date('\"+" + COLNAME1
                + "_start+\"','yyyy-mm-dd hh24:mi:ss')  and  to_date('\"+" + COLNAME1
                + "_end+\"','yyyy-mm-dd hh24:mi:ss')   \";\n";
        wheresql += "}\n";
      }


    }
    if (!"".equals(selectsql)) {
      selectsql = selectsql.substring(0, selectsql.length() - 1);
    }
    datas +=
        "sql=\"select " + selectsql + " from " + username + "." + tablename + " a where 1=1\";";
    datas += wheresql;

    datas += "\npqry.clearSQL();\n";
    datas += "pqry.addSQL(sql);\n";
    datas += "pqry.openSql(-6);\n";
    datas += "DataUtil.addPageData(request,pqry);\n";
    datas += "out.print(DataUtil.successCode(request,\"suc\"));\n";
    return datas;
  }

  /**
   * 获取到查询语句拼接,不分页的
   * 
   * @param cols
   * @param username
   * @param tablename
   * @return
   */
  public String getsearchsql(ArrayList cols, String username, String tablename) {
    String datas = "String sql=\"\";\n";// 插入的语句
    String selectsql = "";
    String wheresql = "";// 后面的查询条件

    for (Object object : cols) {
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");

      if ("VARCHAR2".equals(COLTYPE) || "NUMBER".equals(COLTYPE)) {// 假如是字符串
        selectsql += "a." + COLNAME + " " + COLNAME.replaceAll("_", "") + ",";
        wheresql +=
            "\nif(StringUtils.isNotBlank(" + COLNAME.replaceAll("_", "") + ")&&"
                + COLNAME.replaceAll("_", "") + "!=\"null\"){\n";
        wheresql +=
            "sql+=\" and  a." + COLNAME + "='\"+" + COLNAME.replaceAll("_", "") + "+\"'  \";\n";
        wheresql += "}\n";
      } else if ("DATE".equals(COLTYPE)) {// 如果是日期
        selectsql +=
            "to_char(a." + COLNAME + ",'yyyy-mm-dd hh24:mi:ss') " + COLNAME.replaceAll("_", "")
                + ",";
        wheresql +=
            "\n if(StringUtils.isNotBlank(" + COLNAME.replaceAll("_", "") + "_start)&&"
                + COLNAME.replaceAll("_", "") + "_start!=\"null\"&&StringUtils.isNotBlank("
                + COLNAME.replaceAll("_", "") + "_end)&&" + COLNAME.replaceAll("_", "")
                + "_end!=\"null\"){\n";
        wheresql +=
            "sql+=\" and  a." + COLNAME + " between to_date('\"+" + COLNAME.replaceAll("_", "")
                + "_start+\"','yyyy-mm-dd hh24:mi:ss')  and  to_date('\"+"
                + COLNAME.replaceAll("_", "") + "_end+\"','yyyy-mm-dd hh24:mi:ss')   \";\n";
        wheresql += "}\n";
      }

    }
    if (!"".equals(selectsql)) {
      selectsql = selectsql.substring(0, selectsql.length() - 1);
    }

    datas +=
        "sql=\"select " + selectsql + " from " + username + "." + tablename + " a where 1=1 \" ;";
    datas += wheresql;
    datas += "\npqry.clearSQL();\n";
    datas += "pqry.addSQL(sql);\n";
    datas += "pqry.openSqlNoPage();\n";
    datas += "DataUtil.addData(request,pqry);\n";
    datas += "out.print(DataUtil.successCode(request,\"suc\"));\n";
    return datas;
  }

  /**
   * 获取到删除语句拼接
   * 
   * @param cols
   * @param username
   * @param tablename
   * @return
   */
  public String getdeletesql(ArrayList cols, String username, String tablename) {
    String datas = "String sql=\"\";\n";// 插入的语句
    datas += "pageQueryBean pqry=new pageQueryBean();\n";
    int co = 0;
    String deletenid = "";
    for (Object object : cols) {
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");
      if (co == 0) {
        deletenid = COLNAME;
      }
      co = co + 1;
    }
    datas +=
        "sql=\"delete from " + username + "." + tablename + " where "
            + deletenid.replaceAll("_", "") + "='\"+" + deletenid + "+\"'\";";
    datas += "\npqry.clearSQL(); \n";
    datas += "pqry.addSQL(sql);\npqry.executeSql();\n";
    return datas;

  }

  /**
   * 获取到修改语句
   * 
   * @param cols
   * @param username
   * @param tablename
   * @return
   */
  public String getupdatesql(ArrayList cols, String username, String tablename) {
    String updatesql = "String sql=\"\";\n";// 插入的语句
    updatesql += "pageQueryBean pqry=new pageQueryBean();\n";
    updatesql += "sql =\"update " + username + "." + tablename + " set \";\n";
    String wheresql = "";
    for (int i = 0; i < cols.size(); i++) {
      Object object = cols.get(i);
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");
      if (i != 0) {
        updatesql +=
            "\nif(StringUtils.isNotBlank(" + COLNAME.replaceAll("_", "") + ")&&"
                + COLNAME.replaceAll("_", "") + "!=\"null\"){\n";
        if ("VARCHAR2".equals(COLTYPE) || "NUMBER".equals(COLTYPE)) {// 假如是字符串
          updatesql += "sql+=\" " + COLNAME + "='\"+" + COLNAME.replaceAll("_", "") + "+\"',\";\n";
        } else if ("DATE".equals(COLTYPE)) {// 如果是日期
          updatesql +=
              "sql+=\"" + COLNAME + "=to_date('\"+" + COLNAME.replaceAll("_", "")
                  + "+\"','yyyy-mm-dd hh24:mi:ss'),\";\n";
        }
        updatesql += "}\n";
      } else {
        wheresql =
            "\nsql+=\"  where 1=1 and " + COLNAME + "='\"+" + COLNAME.replaceAll("_", "")
                + "+\"' \";";
      }
    }
    updatesql += "	sql=sql.substring(0,sql.length()-1);\n";
    updatesql += wheresql;
    updatesql += "\npqry.clearSQL(); \n";
    updatesql += "pqry.addSQL(sql);\npqry.executeSql();\n";
    return updatesql;

  }

  /**
   * 获取到传值不转码语句
   * 
   * @param cols
   * @param username
   * @param tablename
   * @return
   */
  public String getchuannosql(ArrayList cols, String username, String tablename) {
    String dates = "";
    String requestcol = "";// 用来存放获取数据的
    int co = 0;
    for (Object object : cols) {
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");
      String COLMEAN = (String) date.get("COLMEAN");
      COLNAME = COLNAME.replaceAll("_", "");

      if ("VARCHAR2".equals(COLTYPE) || "NUMBER".equals(COLTYPE)) {// 如果是字符串
        requestcol +=
            "String " + COLNAME + "=myreq.toCn(request.getParameter(\"" + COLNAME.toUpperCase()
                + "\"));//" + COLMEAN + "\n";

        co = co + 1;

      } else {// 如果是时间
        requestcol +=
            "String " + COLNAME + "=myreq.toCn(request.getParameter(\"" + COLNAME.toUpperCase()
                + "\"));//" + COLMEAN + "\n";
        String COLNAME1 = COLNAME;
        COLNAME = COLNAME1 + "_start";
        requestcol +=
            "String " + COLNAME + "=myreq.toCn(request.getParameter(\"" + COLNAME.toUpperCase()
                + "\"));//" + COLMEAN + "\n";
        COLNAME = COLNAME1 + "_end";
        requestcol +=
            "String " + COLNAME + "=myreq.toCn(request.getParameter(\"" + COLNAME.toUpperCase()
                + "\"));//" + COLMEAN + "\n";


      }
    }
    dates += requestcol + "\n";
    return dates;
  }

  /**
   * 获取到传值转码语句
   * 
   * @param cols
   * @param username
   * @param tablename
   * @return
   */
  public String getchuansql(ArrayList cols, String username, String tablename) {
    String dates = "";
    String requestcol = "";// 用来存放获取数据的
    int co = 0;
    for (Object object : cols) {
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");
      COLNAME = COLNAME.replaceAll("_", "");
      String COLMEAN = (String) date.get("COLMEAN");
      if ("VARCHAR2".equals(COLTYPE) || "NUMBER".equals(COLTYPE)) {// 如果是字符串
        requestcol +=
            "String " + COLNAME + "=myreq.toCn(request.getParameter(\"" + COLNAME.toUpperCase()
                + "\"));//" + COLMEAN + "\n";
        requestcol += "if(!\"\".equals(" + COLNAME + ")&&" + COLNAME + "!=null){\n";
        requestcol += COLNAME + "=java.net.URLDecoder.decode(" + COLNAME + ", \"UTF-8\");\n}\n";
        co = co + 1;

      } else {// 如果是时间
        requestcol +=
            "String " + COLNAME + "=myreq.toCn(request.getParameter(\"" + COLNAME.toUpperCase()
                + "\"));//" + COLMEAN + "\n";
        requestcol += "if(!\"\".equals(" + COLNAME + ")&&" + COLNAME + "!=null){\n";
        requestcol += COLNAME + "=java.net.URLDecoder.decode(" + COLNAME + ", \"UTF-8\");\n}\n";
        String COLNAME1 = COLNAME;
        COLNAME = COLNAME1 + "_start";
        requestcol +=
            "String " + COLNAME + "=myreq.toCn(request.getParameter(\"" + COLNAME.toUpperCase()
                + "\"));//" + COLMEAN + "\n";
        requestcol += "if(!\"\".equals(" + COLNAME + ")&&" + COLNAME + "!=null){\n";
        requestcol += COLNAME + "=java.net.URLDecoder.decode(" + COLNAME + ", \"UTF-8\");\n}\n";
        COLNAME = COLNAME1 + "_end";
        requestcol +=
            "String " + COLNAME + "=myreq.toCn(request.getParameter(\"" + COLNAME.toUpperCase()
                + "\"));//" + COLMEAN + "\n";
        requestcol += "if(!\"\".equals(" + COLNAME + ")&&" + COLNAME + "!=null){\n";
        requestcol += COLNAME + "=java.net.URLDecoder.decode(" + COLNAME + ", \"UTF-8\");\n}\n";

      }
    }
    dates += requestcol + "\n";
    return dates;
  }

  /**
   * 获取到插入语句
   * 
   * @param cols
   * @param username
   * @param tablename
   * @return
   */
  public String getinsertsql(ArrayList cols, String username, String tablename) {
    String sqlS1 = "create sequence " + username + ".sequ_" + tablename + "_id \r\n" + " ";

    JdkDataSource.mysqldb.excutesql(sqlS1);
    String dates = "";
    String insertsql = "String sql=\"\";\n";// 插入的语句

    insertsql = "sql=\"insert into " + username + "." + tablename + " (  \";\n";
    int co = 0;
    for (int i = 0; i < cols.size(); i++) {
      Object object = cols.get(i);
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");
      if (i != 0) {
        insertsql +=
            "\nif(StringUtils.isNotBlank(" + COLNAME.replaceAll("_", "") + ")&&"
                + COLNAME.replaceAll("_", "") + "!=\"null\"){\n";
        insertsql += "sql+=\"" + date.get("COLNAME") + ",\";\n";
        insertsql += "}\n";
      } else {
        insertsql += "sql+=\"" + date.get("COLNAME") + " , \";\n";
      }
    }
    insertsql += "	sql=sql.substring(0,sql.length()-1);\n";
    insertsql += "sql+=\") values (\"; \n";

    for (int i = 0; i < cols.size(); i++) {
      Object object = cols.get(i);
      Map date = (Map) object;
      String COLTYPE = (String) date.get("COLTYPE");
      String COLNAME = (String) date.get("COLNAME");
      COLNAME = COLNAME.replaceAll("_", "");

      if (i != 0) {
        insertsql += "\nif(StringUtils.isNotBlank(" + COLNAME + ")&&" + COLNAME + "!=\"null\"){\n";
        if ("VARCHAR2".equals(COLTYPE) || "NUMBER".equals(COLTYPE)) {// 假如是字符串
          insertsql += "sql+=\" '\"+" + COLNAME + "+\"' ,\";\n";
        } else if ("DATE".equals(COLTYPE)) {// 如果是日期
          insertsql += "sql+=\" to_date('\"+" + COLNAME + "+\"','yyyy-mm-dd hh24:mi:ss') ,\";\n";
        }
        insertsql += "}\n";
      } else {
        insertsql += "sql+=\"" + username + ".sequ_" + tablename + "_id.nextval ,\";\n";

      }
    }
    insertsql += "	sql=sql.substring(0,sql.length()-1);\n";
    insertsql += "sql+=\" )\";\n ";
    dates += "String sql=\"\";\n";
    dates += "pageQueryBean pqry=new pageQueryBean();\n";

    dates += insertsql;
    dates += "\npqry.clearSQL(); \n";
    dates += "pqry.addSQL(sql);\npqry.executeSql();\n";
    return dates;
  }



  // 获取所有用户，并且填充到界面中
  public void getusernames() {

    dbnet dbnet = new dbnet();
    ArrayList names = new ArrayList();// 用来存放所有的用户名
    names = dbnet.getusers();// 获取所有用户数据
    String[] strArray = new String[names.size()];
    for (int i = 0; i < names.size(); i++) {
      strArray[i] = (String) names.get(i);
    }
    Myframe.myframe.tx_username.removeAllItems();
    for (int i = 0; i < strArray.length; i++) {

      Myframe.myframe.tx_username.addItem(strArray[i]);
    }


  }


}
