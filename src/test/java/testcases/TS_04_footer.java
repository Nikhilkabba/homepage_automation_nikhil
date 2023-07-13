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
public class TS_04_footer extends BaseClass {
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
    public void loginUsingCookie()
    {
        Date dt = new Date();
        Cookie ck = new Cookie("user_id","7LUF0IhcgAF5Zi2HY7LosQ:1670322874263:a7546ce1cc1cf8bf117fe2b771948aa15498651b","www.ackodev.com","/",new Date(dt.getTime() + (1000 * 60 * 60 * 24)),true);
        driver.getWebDriver().manage().addCookie(ck);
        driver.refresh();
    }
    @DataProvider(name="footerdata")
    public static Object[] footerdata ()
    {
        JSONArray data= (JSONArray) jsonObject.get("footerlinksdata");
        return data.toArray();
    }
    @DataProvider (name = "appdownload")
    public Object[] journey() {
        return new Object [] {"AppStore","GooglePlay"};
    }

    @Test(dataProvider = "footerdata")
    public void TC_01_Products_links(JSONObject data,ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifyFooter(data.get("footerHeading").toString(),(JSONArray) data.get("links")), "Footer Links for"+data.get("footerHeading")+" are not working properly.");
             }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test
    public void TC_02_Verify_socialMedia_Footer_Redirections(ITestContext context)
    {
        try
        {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifyFooterRedirectionSection2((JSONArray) jsonObject.get("socialsFooterLinks"),"socials"),"Social media links are not working correctly on homepage");
        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }
    @Test(dataProvider = "appdownload")
    public void TC_03_Verify_app_download_redirection(String data,ITestContext context)
    {
        try
        {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.appdownload((JSONArray) jsonObject.get(data+"downloadlink"),data),"App download link"+data+ "is not working correctly on homepage");

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage().toString());
        }
    }
    @AfterClass
    public void cleanUp() {
        driver.closeBrowser();
    }
}