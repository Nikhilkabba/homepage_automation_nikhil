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

public class TS_02_bikeFlow extends BaseClass{
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

    @DataProvider(name="bikevalidationdata")
    public static Object[] visibleCarPolicyData ()
    {
        JSONArray policiesTestData= (JSONArray) jsonObject.get("validationTestDataBike");
        return policiesTestData.toArray();
    }
    @DataProvider(name="bikewidgetdata")
    public static Object[] carwidgetdata ()
    {
        JSONArray data= (JSONArray) jsonObject.get("Bikejourneydata");
        return data.toArray();
    }
    @DataProvider(name="bikewidgetdatalogin")
    public static Object[] carwidgetdatalogin ()
    {
        JSONArray data= (JSONArray) jsonObject.get("Bikejourneydatalogin");
        return data.toArray();
    }
    public void loginUsingCookie()
    {
        Date dt = new Date();
        Cookie ck = new Cookie("user_id","7LUF0IhcgAF5Zi2HY7LosQ:1670322874263:a7546ce1cc1cf8bf117fe2b771948aa15498651b","www.ackodev.com","/",new Date(dt.getTime() + (1000 * 60 * 60 * 24)),true);
        driver.getWebDriver().manage().addCookie(ck);
        driver.refresh();
    }
    @Test(dataProvider = "bikevalidationdata")
    public void TC_01_Verify_validation_inBikewidget(JSONObject data,ITestContext context)
    {
        try {
            Assert.assertTrue(homePageObject.verifydatavalidation((JSONArray) data.get("validationmsg"),"bike", data.get("value").toString()), "Bike widget validation is not working correctly for :- "+data.get("value"));
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_02_Verify_newbike_inBikewidget(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifynewvehicleflowhp((JSONArray) jsonObject.get("newbikehplink"), "bike"), "New bike flow in not working on homepage");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_03_Verify_freshflow_inBikewidget(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("bike","HR26CK2406"), "Not able to enter the bike number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifyfreshflow((JSONArray) jsonObject.get("freshbikelink"),"bike"), "Fresh bike journey is not working correctly on homepage");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test(dataProvider = "bikewidgetdata")
    public void TC_04_Verify_activePolicy_inBikewidget(JSONObject data,ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("bike",data.get("vehicleNumber").toString()), "Not able to enter the car number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifywidget((JSONArray) data.get("widgetMsg"),data.get("vehicleNumber").toString()), "widget pop-up for "+data.get("description") +" is not shown correctly");
            Assert.assertTrue(homePageObject.editregistration(), "Edit registration number is not clickable");
            Assert.assertTrue(homePageObject.logintoknowmore((JSONArray) jsonObject.get("loginlink")), "Not able to click on login button");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_05_Verify_car_inBikewidget(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("bike","HR26CK1234"), "Not able to enter the car number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifywidgetintervehicle("carinbike"), "Widget pop-up is not shown correctly for car in bike journey");
            Assert.assertTrue(homePageObject.editregistration(), "Edit registration number is not clickable");
            Assert.assertTrue(homePageObject.buycarinsurance((JSONArray) jsonObject.get("carseolink")), "Buy bike insurance button is not working fine in pop up");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    //login cases will go below this
    @Test
    public void TC_06_Verify_freshflow_inBikewidget_login(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.logintheuser(), "Not able to login the user");
            Assert.assertTrue(homePageObject.enterflow("bike","HR26CK2406"), "Not able to enter the bike number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifyfreshflow((JSONArray) jsonObject.get("freshbikelink"),"bike"), "Fresh bike journey is not working correctly for logged in user");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_07_Verify_newbike_inBikewidget_login(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifynewvehicleflowhp((JSONArray) jsonObject.get("newbikehplink"), "bike"), "New bike flow in not working on homepage for logged in user");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test(dataProvider = "bikewidgetdata")
    public void TC_08_Verify_activePolicy_inBikewidget_login(JSONObject data,ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("bike",data.get("vehicleNumber").toString()), "Not able to enter the car number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifywidget((JSONArray) data.get("widgetMsg"),data.get("vehicleNumber").toString()), "widget pop-up for "+data.get("description") +" is not shown correctly");
            Assert.assertTrue(homePageObject.editregistration(), "Edit registration number is not clickable");
            Assert.assertTrue(homePageObject.ViewPolicy((JSONArray) jsonObject.get("viewpolicylink")), "view policy not working in widget pop-up");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_09_Verify_car_inBikewidget_login(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("bike","HR26CK1234"), "Not able to enter the car number in placeholder correctly");
            Assert.assertTrue(homePageObject.verifywidgetintervehicle("carinbike"), "Widget pop-up is not shown correctly for car in bike journey for logged in user");
            Assert.assertTrue(homePageObject.editregistration(), "Edit registration number is not clickable");
            Assert.assertTrue(homePageObject.buycarinsurance((JSONArray) jsonObject.get("carseolink")), "Buy bike insurance button is not working fine in pop up");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_10_Verify_differentactivepolicy_inBikewidget_login(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflow("car","UP85BD0681"), "Not able to enter the bike number in placeholder correctly");
            Assert.assertTrue(homePageObject.editregistration(), "Edit registration number is not clickable");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }


    @AfterClass
    public void cleanUp() {
        driver.closeBrowser();
    }


}
