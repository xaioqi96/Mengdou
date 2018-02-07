package com.md.mq.db.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.md.utils.HttpUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by lijingxin on 2018/1/30.
 */
public class OpsParams {





    public static void main( String[] args ) {

        String token = "e030bcb5-d902-47ef-98e3-748863482eb0";
        try {




            OpsParams.runOpsParams(token);


            System.out.println("执行完成。");

        }catch (Exception e){
            e.printStackTrace();
        }


    }


    public static void runOpsParams(String token)throws  Exception{

        List<Map<String,String>> list = OpsParams.getOpsPrams();

        System.out.println(list.size());


        for(Map<String,String> map :list){

            String id = map.get("id");
            String params = map.get("params");
            String url = "https://hb.52ydwf.com/SERVER/service.html?SERVERID=SCMS-1500045&ACCESS_TOKEN="+token+"&APP_ID=00015&"+params;
            String ss = HttpUtil.getSend(url);
            JSONObject jb = JSONObject.parseObject(ss);
            int status = 1;
            if(!jb.getBoolean("FLAG")) {
                status = 9;
            }
            OpsParams.updOpsParams(id,status);

            //System.out.println(ss);

        }

    }




    /***
     * 查询
     * @throws SQLException
     */
    public static List<Map<String,String>> getOpsPrams() throws SQLException {


        List<Map<String,String>> list = new ArrayList<Map<String,String>>();

        try{
            Statement stmt = DbUtil.getStatement();

            String sql= "SELECT id,params FROM `ops_params` p WHERE 1 = 1 AND p.status = 0" ;
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){

                String id = rs.getString("id");
                String params = rs.getString("params");


                Map<String,String> map = new HashMap<String,String>();
                map.put("id",id);
                map.put("params",params);

                list.add(map);
            }

            HashSet h  =   new  HashSet(list);
            list.clear();
            list.addAll(h);

            System.out.println(JSON.toJSONString(list));
        }

        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }




    /***
     * 更新
     * @throws SQLException
     */
    public static void updOpsParams(String id,int status) throws SQLException {




        try{
            Statement stmt = DbUtil.getStatement();

            String sql= "UPDATE `ops_params` SET status = "+status+"  WHERE 1 = 1 AND id = '"+id+"'" ;

            int rs = stmt.executeUpdate(sql);

            //System.out.println(rs);
        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }



}
