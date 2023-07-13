package testcases;
import acko.utilities.JsonUtility;
import acko.utilities.PropertiesFileUtility;
import ackoService.CommonUtils;
import ackoService.web.pages.HomePage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.util.Date;
import java.util.HashMap;
import acko.utilities.TestListener;

@Listeners(TestListener.class)
public class TS_03_carFlow extends BaseClass{
    HashMap<String,String> config=null;
    static JSONObject jsonObject;
    public static String jsonFilePath = System.getProperty("user.dir") + "/dataParam.json";
    String loggedInNumber;


    //pageObjects
    CommonUtils commonUtils=new CommonUtils();
    HomePage homePageObject =new HomePage();

    @BeforeClass
    public void classPrerequisite(ITestContext context)
    {
        try{
            config= PropertiesFileUtility.getConfigData();
            jsonObject = JsonUtility.getJsonPayload(jsonFilePath);
            JSONObject genericData= (JSONObject) jsonObject.get("genericData");
            context.setAttribute("WebDriver",driver);
            initilization(config.get("baseURL"));
            //this.loginUsingCookie();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @DataProvider(name="carvalidationdata")
    public static Object[] carvalidation ()
    {
        JSONArray data= (JSONArray) jsonObject.get("validationTestDataCar");
        return data.toArray();
    }
    @DataProvider(name="carwidgetdata")
    public static Object[] carwidgetdata ()
    {
        JSONArray data= (JSONArray) jsonObject.get("Carjourneydata");
        return data.toArray();
    }
    @DataProvider(name="carwidgetdatalogin")
    public static Object[] carwidgetdatalogin ()
    {
        JSONArray data= (JSONArray) jsonObject.get("Carjourneydatalogin");
        return data.toArray();
    }


    public void loginUsingCookie()
    {
        Date dt = new Date();
        Cookie ck = new Cookie("user_id","7LUF0IhcgAF5Zi2HY7LosQ:1670322874263:a7546ce1cc1cf8bf117fe2b771948aa15498651b","www.ackodev.com","/",new Date(dt.getTime() + (1000 * 60 * 60 * 24)),true);
        driver.getWebDriver().manage().addCookie(ck);
        driver.refresh();
    }
    @Test(dataProvider = "carvalidationdata")
    public void TC_01_Verify_car_widget_validation(JSONObject data,ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifydatavalidation((JSONArray) data.get("validationmsg"),"car",data.get("value").toString()), "Bike widget validation is not working correctly for :- "+data.get("value"));
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_02_Verify_new_car_flow(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifynewvehicleflowhp((JSONArray) jsonObject.get("newcarhplink"), "car"), "New car flow in not working on homepage");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_03_Verify_freshflow_inCarwidget(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("car","HR26NK0001"), "Not able to enter the car number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifyfreshflow((JSONArray) jsonObject.get("freshcarlink"),"car"), "Fresh car journey is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test(dataProvider = "carwidgetdata")
    public void TC_04_Verify_activePolicy_inCarwidget(JSONObject data,ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("car",data.get("vehicleNumber").toString()), "Not able to enter the car number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifywidget((JSONArray) data.get("widgetMsg"),data.get("vehicleNumber").toString()), "widget pop-up for "+data.get("description") +" is not shown correctly");
            Assert.assertTrue(homePageObject.editregistration(), "Edit registration number is not clickable");
            Assert.assertTrue(homePageObject.logintoknowmore((JSONArray) jsonObject.get("loginlink")), "Not able to click on login button");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_05_Verify_Verify_bike_inCarwidget(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("car","HR05AJ1342"), "Either Signup now button is wrong or the redirected url is having an issue");
            Assert.assertTrue(homePageObject.verifywidgetintervehicle("bikeincar"), "Either Signup now button is wrong or the redirected url is having an issue");
            Assert.assertTrue(homePageObject.editregistration(), "Edit registration number is not clickable");
            Assert.assertTrue(homePageObject.buybikeinsurance((JSONArray) jsonObject.get("bikeseolink")), "Buy bike insurance button is not working fine in pop up");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    //login test cases will go after this

    @Test
    public void TC_06_Verify_newcar_inCarwidget_login(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.logintheuser(), "Not able to login the user");
            Assert.assertTrue(homePageObject.verifynewvehicleflowhp((JSONArray) jsonObject.get("newcarhplink"), "car"), "New car flow in not working on homepage");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_07_Verify_freshflow_inCarwidget(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("car","HR26NK0001"), "Not able to enter the car number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifyfreshflow((JSONArray) jsonObject.get("freshcarlink"),"car"), "Fresh car journey is not working");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test(dataProvider = "carwidgetdatalogin")
    public void TC_08_Verify_activepolicy_inCarwidget_login(JSONObject data,ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("car",data.get("vehicleNumber").toString()), "Not able to enter the car number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifywidget((JSONArray) data.get("widgetMsg"),data.get("vehicleNumber").toString()), "widget pop-up for"+data.get("description") +"is not shown correctly");
            Assert.assertTrue(homePageObject.editregistration(), "Edit registration number is not clickable");
            Assert.assertTrue(homePageObject.ViewPolicy((JSONArray) jsonObject.get("viewpolicylink")), "view policy not working in widget pop-up");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_09_Verify_bike_inCarwidget_login(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("car","HR05AJ1342"), "Either Signup now button is wrong or the redirected url is having an issue");
            Assert.assertTrue(homePageObject.verifywidgetBikeinCarjourney(), "Either Signup now button is wrong or the redirected url is having an issue");
            Assert.assertTrue(homePageObject.editregistration(), "Edit registration number is not clickable");
            Assert.assertTrue(homePageObject.buybikeinsurance((JSONArray) jsonObject.get("bikeseolink")), "Buy bike insurance button is not working fine in pop up");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @AfterClass
    public void cleanUp() {
        driver.closeBrowser();
    }
}
