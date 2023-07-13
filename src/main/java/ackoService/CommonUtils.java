package ackoService;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration2.*;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import acko.utilities.ConnectionFactory;

public class CommonUtils {


    public static String getValueForKey(String jsonResponse, String key) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(jsonResponse);
        String value = "";
        try {
            if (!key.contains(".")) {
                value = String.valueOf(json.get(key));
            } else {
                String outerkey = key.split("\\.")[0];
                String innerkey = key.split("\\.")[1];
                JSONObject j2 = (JSONObject) json.get(outerkey);
                value = j2.get(innerkey).toString();
            }
        } catch (Exception e) {
            return value = "";
        }

        if (!Objects.equals(value, "null")) {
            return value;
        } else {
            return value = "";
        }
    }


    public static String modifyPayloadWithKey(String payload, String key, String value) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(payload);
        json.put(key, value);
        return json.toString();
    }

    public static String getUniqueString() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("ddMMyyhhmmss");
        String datetime = ft.format(date);
        return datetime;
    }

    public static String getFromDateForProcessFile() {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        String datetime = ft.format(date);
        return datetime;

    }

    public static String getFromDateForPaymentResponseFile() {

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        date = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        String datetime = ft.format(date);
        return datetime;

    }

    public static String getToDateForProcessFile() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        String datetime = ft.format(date);
        return datetime;
    }

    public static String getValueOfSeconds() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        SimpleDateFormat ft = new SimpleDateFormat("s");
        String datetime = ft.format(date);
        return datetime;
    }

    public static JSONObject convertStringToJson(String payload) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(payload);
        return json;
    }

    public static JSONObject modifyJsonWithKey(JSONObject payload, String key, String value) throws ParseException {
        payload.put(key, value);
        return payload;
    }

    public static JSONObject modifyJsonWithKey(JSONObject payload, String key, Object value) throws ParseException {
        payload.put(key, value);
        return payload;
    }

    public static String encodeBase64Encoding(String string) {
        byte[] encoded = Base64.encodeBase64(string.getBytes());
        return new String(encoded);
    }

    public static String decodeBase64Encoding(String string) {
        //decoding byte array into base64
        byte[] decoded = Base64.decodeBase64(string);
        return new String(decoded);
    }

    public boolean saveResponseToHTMLFIle(String fileName, String dataString) {

        boolean status = false;
        try {
            FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "/" + fileName);
            myWriter.write(dataString);
            myWriter.close();
            status = true;
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return status;
    }

    // type: [ackomunication, analytics]
    public void updatePropertyFile(String type) {

        try {
            Parameters params = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("commonConfig.properties"));
            Configuration config = builder.getConfiguration();

            config.setProperty("dbIP", config.getProperty(type + "DbIP"));
            config.setProperty("dbPort", Integer.valueOf((String) config.getProperty(type + "DbPort")));
            config.setProperty("dbName", config.getProperty(type + "DbName"));
            config.setProperty("dbUserName", config.getProperty(type + "DbUserName"));
            config.setProperty("dbPassword", config.getProperty(type + "DbPassword"));

            builder.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String fetchOTP(String mobileNumber, String type) throws Exception {

        String sql = null;
        String otpValue = "";
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            updatePropertyFile(type);
            ConnectionFactory connectionFactory = new ConnectionFactory();


            if (type.equals("ackomunication")) {
                sql = "SELECT template_context_data->>'otp' FROM sms_report " +
                        "WHERE template_name ='send_otp_default' AND recipient='" + mobileNumber +
                        "' AND created_on > NOW()- INTERVAL '300 second' ORDER BY id DESC LIMIT 1";
            } else if (type.equals("analytics")) {
                sql = "SELECT edata->>'otp' FROM r2d2_event WHERE edata->>'gupshup_phone'='" + mobileNumber + "' " +
                        "AND ekind='otp_generated' AND created_on > NOW()- INTERVAL '300 second' "
                        + "ORDER BY id DESC LIMIT 1";
            }

            con = connectionFactory.getConnection();
            preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                otpValue = resultSet.getString(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage().toString());
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception ignored) {
            }
            try {
                if (con != null) {
                    con.close();
                    con = null;
                }
            } catch (Exception ignored) {
            }
        }
        return otpValue;
    }

    public HashMap<String, Object> getOrderDetailsPayload() {
        HashMap<String, Object> payload = new HashMap<>();
        HashMap<String, Object> context = new HashMap<>();
        try {

            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            String time = String.valueOf(timeStamp.getTime());
            payload.put("amount", "1500");
            payload.put("client_reference_id", time);
            payload.put("customer_email", "test@gmail.com");
            payload.put("customer_phone", "7017991887");
            payload.put("customer_id", "581001");
            payload.put("lob", "auto");
            payload.put("recurring", "false");
            payload.put("app", "Acko.Desktop");
            payload.put("timestamp", time);
            payload.put("return_url_client", "https://payment-service-ui-ng.ackodev.com/page2.html");
            context.put("type", "car_comprehensive");
            context.put("subtype", "Maruti Alto VXI");
            context.put("gst", "300");
            context.put("premium", "1200");
            context.put("journey", "auto.private_car");
            payload.put("context", context);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return payload;
    }

    public  Response post(String uri,HashMap<String,Object> jsonPayload) {
        Response response = null;

        try {
            RequestSpecification request = RestAssured.given();
            request.contentType(ContentType.JSON);
            request.body(jsonPayload);
            response = (Response)request.post(uri, new Object[0]);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return response;
    }

   public void deleteKeyFromMap(HashMap<String,Object> map,String key)
    {
        try{
            map.remove(key);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateJourney(String key, String value)
    {
        try{
            HashMap<String,Object> payload=getOrderDetailsPayload();
            payload.put("key",value);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean validateResponse(String response,String expectedErrorMessage)
    {
        try{
            JSONObject payload=this.convertStringToJson(response);
            ArrayList<String> parameterInPayload=new ArrayList<>();
            parameterInPayload.add("category");
            parameterInPayload.add("code");
            parameterInPayload.add("error");
            parameterInPayload.add("message");
            parameterInPayload.add("success");

            for(int i=0;i<parameterInPayload.size();i++)
            {
                if(!payload.containsKey(parameterInPayload.get(i)))
                    return false;
            }

            if(!payload.get("category").toString().contains("Input Validation Failed"))
            {
                System.out.println("wrong value of category is coming in response");
                return false;
            }

            if(!payload.get("code").toString().contains("IVERR_001"))
            {
                System.out.println("wrong value of code is coming in response");
                return false;
            }

            if(!payload.get("message").toString().contains(expectedErrorMessage))
            {
                System.out.println("wrong error Message is coming in response");
                return false;
            }

            if(!payload.get("success").toString().contains("false"))
            {
                System.out.println("wrong success value is coming in response");
                return false;
            }

        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public boolean serverErrorResponse(String response)
    {
        try{
            JSONObject payload=this.convertStringToJson(response);
            ArrayList<String> parameterInPayload=new ArrayList<>();
            parameterInPayload.add("category");
            parameterInPayload.add("code");
            parameterInPayload.add("error");
            parameterInPayload.add("message");
            parameterInPayload.add("success");

            for(int i=0;i<parameterInPayload.size();i++)
            {
                if(!payload.containsKey(parameterInPayload.get(i)))
                    return false;
            }

            if(!payload.get("category").toString().contains("Internal Server Error"))
            {
                System.out.println("wrong value of category is coming in response");
                return false;
            }

            if(!payload.get("code").toString().contains("DBERR_001"))
            {
                System.out.println("wrong value of code is coming in response");
                return false;
            }

            if(!payload.get("message").toString().contains("could not execute statement"))
            {
                System.out.println("wrong error Message is coming in response");
                return false;
            }

            if(!payload.get("success").toString().contains("false"))
            {
                System.out.println("wrong success value is coming in response");
                return false;
            }

        }catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    public String getTimestamp()
    {
        String timeStamp=null;
        try{
            Timestamp time = new Timestamp(System.currentTimeMillis());
            timeStamp=String.valueOf(time.getTime());

        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return timeStamp;
    }


}
