package testcases;
import org.testng.annotations.DataProvider;
import acko.utilities.JsonUtility;
import acko.utilities.PropertiesFileUtility;
import ackoService.CommonUtils;
import ackoService.web.pages.HomePage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.Cookie;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashMap;
import acko.utilities.TestListener;
import org.testng.annotations.Listeners;

@Listeners(TestListener.class)
public class TS_01_lobwidget extends BaseClass {

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
    @DataProvider(name = "lobjourney")
    public Object[] lobjourney() {
        return new Object [] {"bike","health","travel","car"};
    }
    @DataProvider (name = "autojourney")
    public Object[] journey() {
        return new Object [] {"car","bike"};
    }


    public void loginUsingCookie()
    {
        Date dt = new Date();
        Cookie ck = new Cookie("user_id","7LUF0IhcgAF5Zi2HY7LosQ:1670322874263:a7546ce1cc1cf8bf117fe2b771948aa15498651b","www.ackodev.com","/",new Date(dt.getTime() + (1000 * 60 * 60 * 24)),true);
        driver.getWebDriver().manage().addCookie(ck);
        driver.refresh();
    }

    @Test(dataProvider = "autojourney")
    public void TC_01_Verify_auto_lob_widget_logo_and_text_message(ITestContext context,String data)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifyLogoAndTexthp((JSONArray) jsonObject.get("lobwidgetmsg"+data), data), "Either Logo or text is not visible as per expectation in "+data+" journey on homepage");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_03_Verify_health_lob_widget_logo_and_text_message(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifyLogoAndTexthphealth((JSONArray) jsonObject.get("lobwidgetmsghealth"), "health"), "Either Health Logo or text is not visible on screen as expected");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_04_Verify_travel_lob_widget_logo_and_text_message(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifyLogoAndTexthp((JSONArray) jsonObject.get("lobwidgetmsgtravel"), "travel"), "Either Travel Logo or text is not visible on screen as expected");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test(dataProvider = "lobjourney")
    public void TC_05_Verify_lob_widget_redirection_url(ITestContext context,String data)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.enterflowall(data), "Not able to enter the lob widget flow");
            Assert.assertTrue(homePageObject.verifyloblink((JSONArray) jsonObject.get(data+"lobwidgetlink"),data), "The redirected urls for lob widget are wrong");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }

    @Test
    public void TC_07_Verify_getQuote_health(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.healthgetquote((JSONArray) jsonObject.get("healthlinkhp"), "health","Check prices"), "Get quote Health journey is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_07_Verify_install_app_health(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.installapp((JSONArray) jsonObject.get("healthinstallapp"), "Corporate health policy from ACKO?"), "Install app Health journey is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_08_Verify_getNotified_travel_flow(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.healthgetquote((JSONArray) jsonObject.get("travellinkhp"), "travel","Get notified"), "Travel journey is not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_09_Verify_offical_partner(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifyofficicalpartners((JSONArray) jsonObject.get("officialpartnerdata")), "Official partners are not visible on screen as expected");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }

//login Test-cases will go after this
@Test
public void TC_10_Verify_getQuote_health_flow_login(ITestContext context)
{
    try {
        driver.navigateToURL(config.get("baseURL"));
        Assert.assertTrue(homePageObject.logintheuser(), "Not able to login the user");
        Assert.assertTrue(homePageObject.healthgetquote((JSONArray) jsonObject.get("healthlinkhp"), "health","Check prices"), "Get quote Health journey is not working correctly");
    }catch (Exception e){
        Assert.fail(e.getMessage().toString());
    }

}
    @AfterClass
    public void cleanUp() {
        driver.closeBrowser();
    }
}
