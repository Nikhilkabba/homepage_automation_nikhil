package testcases;

import acko.utilities.JsonUtility;
import acko.utilities.PropertiesFileUtility;
import acko.utilities.TestListener;
import ackoService.CommonUtils;
import ackoService.web.pages.HomePage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.util.HashMap;

@Listeners(TestListener.class)
public class TS_05_header extends BaseClass{
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
    @DataProvider(name = "category")
    public Object[] category() {
        return new Object [] {"Products","Renewals","Claims","Resources"};
    }
    @DataProvider(name = "subcategory")
    public Object[] subcategory() { return new Object [] {"Car","Bike","Health","Travel","Corporate"};}
    @DataProvider(name = "subcategoryrenewals")
    public Object[] subcategoryrenewals() {
        return new Object [] {"Car","Bike","Health"};
    }
    @DataProvider(name = "subcategoryclaims")
    public Object[] subcategoryclaims() {
        return new Object [] {"Car","Bike","Health"};
    }
    @DataProvider(name = "loginandsignup")
    public Object[] loginsignup() {
        return new Object [] {"Login","Sign"};}





    @Test(dataProvider = "category")
    public void TC_01_Verify_heading_logo(ITestContext context,String data)
    {
        try {
            Assert.assertTrue(homePageObject.entercategory(data), "Either Logo or text is not visible as per expectation in "+data+" journey on homepage");
            Assert.assertTrue(homePageObject.verifylogoandheading(data), "Either Logo or text is not visible as per expectation in "+data+" journey on homepage");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test(dataProvider = "subcategoryrenewals")
    public void TC_02_Verify_renewals_category_links(ITestContext context,String data)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.entercategory("Renewals"), "Not able to enter renewals category");
            Assert.assertTrue(homePageObject.categorylinksrenewals((JSONArray) jsonObject.get(data+"linksrenewals"),data), "Renewals category links are not working correctly");

        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test(dataProvider = "subcategoryclaims")
    public void TC_03_Verify_claim_category_links(ITestContext context,String data)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.entercategory("Claims"), "Not able to enter claims category");
            Assert.assertTrue(homePageObject.categorylinksclaims((JSONArray) jsonObject.get(data+"linksclaims"),data), "Claims category links not working correctly");

        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_04_Verify_resources_category_links(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.entercategory("Resources"), "Not able to enter resource category");
            Assert.assertTrue(homePageObject.resources((JSONArray) jsonObject.get("resourcesdata")), "Resource category links not working correctly");

        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test(dataProvider = "loginandsignup")
    public void TC_05_Verify_loginsignup(ITestContext context,String data)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifysignuplogin((JSONArray) jsonObject.get(data+"buttonlink"),data), "qwerty");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_06_Verify_ackologo_redirection(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.verifyAckoLogo((JSONArray) jsonObject.get("ackoLogoLinksout")), "qwerty");

        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test(dataProvider = "subcategory")
    public void TC_07_Verify_products_category_links(ITestContext context,String data)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.entercategory("Products"), "Not able to enter products category");
            Assert.assertTrue(homePageObject.categorylinks((JSONArray) jsonObject.get(data+"headerlinks"),data), "Products category links for"+data+"are not working correctly");

        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }
    @Test
    public void TC_08_Verify_products_category_links_electronics(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.entercategory("Products"), "Not able to enter products category");
            Assert.assertTrue(homePageObject.categorylinkselectronics((JSONArray) jsonObject.get("Electronicsheaderlinks"),"Electronics"), "Products category links are not working correctly");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }

    //login test cases will go below this
    @Test
    public void TC_09_Verify_user_profile(ITestContext context)
    {
        try {
            driver.navigateToURL(config.get("baseURL"));
            Assert.assertTrue(homePageObject.logintheuser(), "Not able to login the user");
            Assert.assertTrue(homePageObject.verifyProfileSection((JSONArray) jsonObject.get("viewprofilelink"),(JSONArray) jsonObject.get("userprofiledata")), "Profile section for logged in user is not working as per expectations");
        }catch (Exception e){
            Assert.fail(e.getMessage().toString());
        }

    }



    @AfterClass
    public void cleanUp() {
        driver.closeBrowser();
    }
}
