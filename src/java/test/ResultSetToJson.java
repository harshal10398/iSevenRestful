/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;


/**
 *
 * @author harshal
 */
public class ResultSetToJson {
    
    public static JsonArray getJson(ResultSet rs) throws SQLException{
        JsonArrayBuilder retArray=Json.createArrayBuilder();
        
        JsonObjectBuilder job=Json.createObjectBuilder();
        ResultSetMetaData rsmd=rs.getMetaData();
        while(rs.next()){
            job.add("row", rs.getRow());
            JsonObjectBuilder internalBuilder=Json.createObjectBuilder();
            for(int i=1;i<=rsmd.getColumnCount();i++)
                internalBuilder.add(rsmd.getColumnName(i), rs.getString(i));
            job.add("values", internalBuilder);
            retArray.add(job.build());
            System.out.println(job.build());
            
        }
        return retArray.build();
    }
}
