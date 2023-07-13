package ackoService.web.pages;

import acko.utilities.ObjectRepositoryLoader;
import acko.utilities.PropertiesFileUtility;
import acko.web.actions.Driver;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.*;
import java.util.List;

public class HomePage extends ackoService.web.pages.CommonPage {


    HashMap<String, HashMap<String, String>> HomePage = null;
    public HashMap<String, String> config = null;
    PropertiesFileUtility propertiesFileUtility = new PropertiesFileUtility();

    ackoService.web.pages.CommonPage commonPageObject = new ackoService.web.pages.CommonPage();
    public void loginUsingCookie()
    {
        Date dt = new Date();
        Cookie ck = new Cookie("user_id","7LUF0IhcgAF5Zi2HY7LosQ:1670322874263:a7546ce1cc1cf8bf117fe2b771948aa15498651b","www.ackodev.com","/",new Date(dt.getTime() + (1000 * 60 * 60 * 24)),true);
        driver.getWebDriver().manage().addCookie(ck);
        driver.refresh();
    }

    public HomePage() {
        try {
            config = PropertiesFileUtility.getConfigData();
            this.HomePage = new ObjectRepositoryLoader().getObjectRepository("HomePage.xml");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean verifyLogoAndTexthp(JSONArray msg,String journey) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        int i=0;
        try {
            switch (journey){
                case"car":
                    i=1;
                    break;
                case"bike":
                    i=2;
                    break;
            }
            driver.waitForElementToAppear(HomePage.get("verifier"),15);
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("lobjourneyhp").get("XPATH"), "{{index}}", String.valueOf(i)));
            driver.clickWhenReady(currentProductPageXpath);
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            if( !driver.isElementDisplayed(HomePage.get(journey+"TextPathhp")) || !driver. checkIfElementIsPresent(HomePage.get(journey+"TextPathhp")) )
                return isDone;
            String Text = driver.find(HomePage.get(journey+"TextPathhp")).getText();
            if (!Text.toLowerCase(Locale.ROOT).contains(journey)){
                 return isDone;}
            Text = driver.find(HomePage.get(journey+"MsgPathhp")).getText();
              if (Text.equals(msg.get(0)))
                isDone = true;
              return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean verifyLogoAndTexthphealth(JSONArray msg,String journey) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        int i=0;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("lobjourneyhp").get("XPATH"), "{{index}}", "3"));
            driver.clickWhenReady(currentProductPageXpath);
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            if( !driver.isElementDisplayed(HomePage.get(journey+"TextPathhp")) || !driver. checkIfElementIsPresent(HomePage.get(journey+"TextPathhp")) )
                return isDone;
            String Text = driver.find(HomePage.get(journey+"TextPathhp")).getText();
            if (!Text.toLowerCase(Locale.ROOT).contains(journey)){
                return isDone;}
            Text = driver.find(HomePage.get(journey+"MsgPathhp")).getText();
            if (Text.equals(msg.get(0)))
                isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean healthgetquote(JSONArray link,String journey,String value) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        int i=0;
        try {
            switch (journey){
                case"health":
                    i=3;
                    break;
                case"travel":
                    i=4;
                    break;
            }
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("lobjourneyhp").get("XPATH"), "{{index}}", String.valueOf(i)));
            driver.clickWhenReady(currentProductPageXpath);
            driver.waitForTime(3);
            textXpath = commonPageObject.getXpath(HomePage.get("bikebuttons").get("XPATH"), "{{value}}", value);
            driver.clickWhenReady(textXpath);
            driver.waitForTime(2);
            driver.SwichToNewWindow();
            switch (journey){
                case"health":
                    textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "What kind of insurance do you need?");
                    break;
                case"travel":
                    textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "What kind of insurance do you need?");
                    break;
            }
            if(!driver.waitForElementToAppear(textXpath,15)){
                System.out.println(textXpath+"did not found");
                return isDone;
            }
            if(!driver.checkIfElementIsPresent(textXpath)||!driver.getWebDriver().getCurrentUrl().equals(link.get(0))){
                return isDone;
            }
            System.out.println("Done");
            isDone=true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean installapp(JSONArray link,String value) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("lobjourneyhp").get("XPATH"), "{{index}}", "3"));
            driver.clickWhenReady(currentProductPageXpath);
            driver.waitForTime(3);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", value);
            driver.clickWhenReady(textXpath);
            driver.waitForTime(2);
            driver.SwichToNewWindow();
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Looking for your corporate health policy?");
            if(!driver.waitForElementToAppear(textXpath,15)){
                System.out.println(textXpath+"did not found");
                return isDone;
            }
            if(!driver.checkIfElementIsPresent(textXpath)||!driver.getWebDriver().getCurrentUrl().equals(link.get(0))){
                return isDone;
            }
            System.out.println("Done");
            isDone=true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean verifynewvehicleflowhp(JSONArray link,String journey) {
        boolean isDone = false;
        try {
            if(!driver.waitForElementToAppear(HomePage.get("verifier"),15))
            {
                System.out.println("verifier did not found");
                return isDone;
            }
            if(!driver.checkIfElementIsPresent(HomePage.get("verifier"))){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            driver.clickWhenReady(HomePage.get(journey+"LogoPathhp"));
            driver.waitForElementToAppear(HomePage.get(journey+"newhp"),10);
            driver.clickWhenReady(HomePage.get(journey+"newhp"));
            if(!driver.waitForElementToAppear(HomePage.get(journey+"newverifier"),15)){
                System.out.println("The redirected page did not load correctly");
                return isDone;
            }
            String redirectedlink=driver.getWebDriver().getCurrentUrl();
            if (!redirectedlink.contains(link.get(0).toString()))
                return isDone;
            if( !driver.isElementDisplayed(HomePage.get(journey+"newverifier")) || !driver. checkIfElementIsPresent(HomePage.get(journey+"newverifier")) ){
                driver.goBack();
                return isDone;}
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean verifyofficicalpartners(JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            textXpath = commonPageObject.getXpath(HomePage.get("genericH2xpath").get("XPATH"), "{{value}}", "Official partner");
            driver.scrollToElement(textXpath);
            for(int i=0;i<data.size();i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(HomePage.get("genericofficialpartner").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.isElementDisplayed(textXpath) || !driver.checkIfElementIsPresent(textXpath)) {
                    System.out.println("Ui problem with Official partners");
                    return isDone;
                }
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean editregistration() {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Edit");
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath))
                return isDone;
            driver.clickWhenReady(textXpath);
            if(!driver.waitForElementToAppear(HomePage.get("verifier"),10)){
                System.out.println("Edit registeration number is not clickable");
                return isDone;
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean logintoknowmore(JSONArray link) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            textXpath = commonPageObject.getXpath(HomePage.get("bikebuttons").get("XPATH"), "{{value}}", "Get quote");
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("get quote button is not clickable after clicking edit registration number");
                return isDone;}
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Login to know more");
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath))
                return isDone;
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("bikebuttons").get("XPATH"), "{{value}}", "Log in");
            if(!driver.waitForElementToAppear(textXpath,15)){
                System.out.println(textXpath+"did not found");
                return isDone;
            }
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath))
                return isDone;
            if (!driver.getWebDriver().getCurrentUrl().equals(link.get(0)))
                return isDone;
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean buybikeinsurance(JSONArray link) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            textXpath = commonPageObject.getXpath(HomePage.get("bikebuttons").get("XPATH"), "{{value}}", "Get quote");
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("get quote button is not clickable after clicking edit registration number");
                return isDone;}
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Buy");
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath))
                return isDone;
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Save big with our zero commission insurance");
            if(!driver.waitForElementToAppear(textXpath,15)){
                System.out.println(textXpath+"did not found");
                return isDone;
            }
            if (!driver.getWebDriver().getCurrentUrl().equals(link.get(0)) || !driver.isElementDisplayed(textXpath)){
                System.out.println("checking or statement");
                System.out.println(driver.getWebDriver().getCurrentUrl());
                return isDone;}
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean buycarinsurance(JSONArray link) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            textXpath = commonPageObject.getXpath(HomePage.get("bikebuttons").get("XPATH"), "{{value}}", "Get quote");
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("get quote button is not clickable after clicking edit registration number");
                return isDone;}
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Buy");
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath))
                return isDone;
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Car insurance starting at");
            if(!driver.waitForElementToAppear(textXpath,20)){
                System.out.println(textXpath+"did not found");
                return isDone;
            }
            if (!driver.getWebDriver().getCurrentUrl().equals(link.get(0)) || !driver.isElementDisplayed(textXpath)){
                return isDone;
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean ViewPolicy(JSONArray link) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            textXpath = commonPageObject.getXpath(HomePage.get("bikebuttons").get("XPATH"), "{{value}}", "Get quote");
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("get quote button is not clickable after clicking edit registration number");
                return isDone;}
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "View policy");
            if(!driver.checkIfElementIsPresent(textXpath) || !driver.isElementDisplayed(textXpath)){
                System.out.println("view policy button is not clickable in if statement");
                return isDone;}
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Covered by ACKO");
            if(!driver.waitForElementToAppear(textXpath,15)){
                System.out.println(textXpath+"did not found");
                return isDone;
            }
            if (!driver.getWebDriver().getCurrentUrl().equals(link.get(0))){
                System.out.println("the url entered is not correct");
                return isDone;}
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean verifydatavalidation(JSONArray msg,String journey,String value) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        int i=0;
        try {
            driver.navigateToURL(config.get("baseURL"));
            if(journey=="car"){
                i=1;
            }
            if(journey=="bike"){
                i=2;
            }
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("lobjourneyhp").get("XPATH"), "{{index}}", String.valueOf(i)));
            driver.clickWhenReady(currentProductPageXpath);
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            driver.setText(HomePage.get(journey+"placeholder"),value);
            textXpath = commonPageObject.getXpath(HomePage.get("bikebuttons").get("XPATH"), "{{value}}", "Get quote");
            driver.clickWhenReady(textXpath);
            String Text = driver.find(HomePage.get("datavalidation")).getText();
            if (!Text.toLowerCase(Locale.ROOT).contains(journey)){
                return isDone;}
            Text = driver.find(HomePage.get("datavalidation")).getText();
            System.out.println(Text);
            System.out.println(msg.get(0));
            if (Text.equals(msg.get(0)))
                isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean enterflow(String journey,String value) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        int i=0;
        try {
            if(journey=="car"){
                i=1;
            }else {
                i=2;
            }
            driver.waitForElementToAppear(HomePage.get("verifier"),15);
            if(!driver.checkIfElementIsPresent(HomePage.get("verifier"))){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("lobjourneyhp").get("XPATH"), "{{index}}", String.valueOf(i)));
            driver.clickWhenReady(currentProductPageXpath);
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            driver.setText(HomePage.get(journey+"placeholder"),value);
            textXpath = commonPageObject.getXpath(HomePage.get("bikebuttons").get("XPATH"), "{{value}}", "Get quote");
            driver.clickWhenReady(textXpath);
            isDone=true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean enterflowall(String journey) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        int i=0;
        try {
            switch (journey){
                case"car":
                    i=1;
                    break;
                case"bike":
                    i=2;
                    break;
                case"health":
                    i=3;
                    break;
                case"travel":
                    i=4;
                    break;
            }
            if(!driver.waitForElementToAppear(HomePage.get("verifier"),15)){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            if(!driver.checkIfElementIsPresent(HomePage.get("verifier"))){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("lobjourneyhp").get("XPATH"), "{{index}}", String.valueOf(i)));
            driver.clickWhenReady(currentProductPageXpath);
            driver.waitForElementToAppear(HomePage.get("verifier"),10);
            isDone=true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean verifyloblink(JSONArray link,String journey) {
        boolean isDone = false;
        int i=0;
        try {
            String Link = driver.getWebDriver().getCurrentUrl();

            if (!Link.equals(link.get(0)) || !Link.contains(journey)){
                System.out.println("link of the redirected page is incorrect");
                return isDone;
            }

            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean verifywidget(JSONArray msg,String value) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        int i=0;
        try {
            if (!driver.isElementDisplayed(HomePage.get("popupvalidation1")) || !driver.checkIfElementIsPresent(HomePage.get("popupvalidation1"))) {
                System.out.println("logo in pop-up widget is not okay");
                return isDone;
            }
            textXpath = commonPageObject.getXpath(HomePage.get("popupvalidation2").get("XPATH"), "{{value}}",value);
            if (!driver.isElementDisplayed(textXpath) || !driver.checkIfElementIsPresent(textXpath)) {
                System.out.println("vehicle number is not visible properly");
                return isDone;
            }
            textXpath = commonPageObject.getXpath(HomePage.get("popupvalidation3").get("XPATH"), "{{value}}",value);
            String Text = driver.find(textXpath).getText();
            if (Text.equals(msg.get(0)))
                isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean verifywidgetBikeinCarjourney() {
        boolean isDone = false;
        try {
            driver.waitForTime(4);
            if (!driver.isElementDisplayed(HomePage.get("bikeincarjourneymsg1")) || !driver.checkIfElementIsPresent(HomePage.get("bikeincarjourneymsg1"))) {
                System.out.println("pop-up widget is not as per expectations");
                return isDone;
            }
            if (!driver.isElementDisplayed(HomePage.get("bikeincarjourneymsg2")) || !driver.checkIfElementIsPresent(HomePage.get("bikeincarjourneymsg2"))) {
                System.out.println("pop-up widget is not as per expectations");
                return isDone;
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean logintheuser() {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),20);
            loginUsingCookie();
            driver.waitForTime(2);
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("spanxpath").get("XPATH"), "{{value}}", "My account")) ;
            if(!driver.waitForElementToAppear(currentProductPageXpath,20)){
                System.out.println("Not able to login the user");
                return isDone;
            }
            if(!driver.checkIfElementIsPresent(currentProductPageXpath)){
                return isDone;
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean verifywidgetintervehicle(String condition) {
        boolean isDone = false;
        try {
            driver.waitForTime(4);
            if (!driver.isElementDisplayed(HomePage.get(condition+"journeymsg1")) || !driver.checkIfElementIsPresent(HomePage.get(condition+"journeymsg1"))) {
                System.out.println("pop-up widget is not as per expectations");
                return isDone;
            }
            if (!driver.isElementDisplayed(HomePage.get(condition+"journeymsg2")) || !driver.checkIfElementIsPresent(HomePage.get(condition+"journeymsg2"))) {
                System.out.println("pop-up widget is not as per expectations");
                return isDone;
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean verifyfreshflow(JSONArray link,String journey) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        int i=0;
        try {

            switch (journey){
                case"car":
                    textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Insure your car in 2 minutes");
                    if(!driver.waitForElementToAppear(textXpath,15)){
                        System.out.println(textXpath+"did not found");
                        return isDone;
                    }
                    break;
                case"bike":
                    textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Select Add-ons");
                    if(!driver.waitForElementToAppear(textXpath,15)){
                        System.out.println(textXpath+"did not found");
                        return isDone;
                    }
                    break;
            }
            if(!driver.checkIfElementIsPresent(textXpath)){
                System.out.println("LOB page did not load properly");
                return isDone;
            }
            String Link = driver.getWebDriver().getCurrentUrl();
            switch (journey){
                case"car":
                    Link = Link.substring(0, 57);
                    break;
                case"bike":
                    Link = Link.substring(0, 53);
                    break;
            }
            System.out.println(Link);
            if (!Link.equals(link.get(0))){
                System.out.println("link of the redirected page is incorrect");
                return isDone;}

            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }

    public boolean verifyFooter(String footerHeading,JSONArray links){
        HashMap<String, String> currentPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),15);
            if(!driver.checkIfElementIsPresent(HomePage.get("verifier"))){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            HashMap<String, String> genericFooterLinks = commonPageObject.getXpath(HomePage.get(footerHeading + "FooterLinks").get("XPATH").toString(), "[{{index}}]", "");
            List<WebElement> pages = driver.findAll(genericFooterLinks);
            System.out.println(pages.size());
            System.out.println(links.size());
            if (pages.size() > links.size()) {
                System.out.println("Number of pages are more than expected");
                return isDone;
            } else if (pages.size() < links.size()) {
                System.out.println("Number of pages are less than expected");
                return isDone;
            }


            for (int i = 1; i <= pages.size(); i++) {
                currentPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get(footerHeading + "FooterLinks").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.clickWhenReady(currentPageXpath);
                parentWindowHandle = driver.getWindowHandle();
                driver.waitForTime(8);
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }

                driver.switchToWindow(childWindowHandle);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1))) {
                    System.out.println("The link on the above index didnt match" +driver.getWebDriver().getCurrentUrl());
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }

                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                driver.scrollToElement(HomePage.get("verifier"));
            }
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean entercategory(String journey) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        int i=0;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),15);
            if(!driver.checkIfElementIsPresent(HomePage.get("verifier"))){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("headercategory").get("XPATH"), "{{value}}", journey)) ;
            driver.clickWhenReady(currentProductPageXpath);
            isDone=true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    //header functions
    public boolean verifylogoandheading(String journey) {
        boolean isDone = false;
        HashMap<String, String> currentProductPageXpath = null;
        HashMap<String, String> textXpath = null;
        try {
            textXpath = commonPageObject.getXpath(HomePage.get("genericcategoryheading").get("XPATH"), "{{value}}",journey);
            if(!driver.isElementDisplayed(textXpath)){
                System.out.println("Element not found");
                return isDone;
            }
            isDone=true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }
    }
    public boolean categorylinks(JSONArray data,String category) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),20);
            textXpath = commonPageObject.getXpath(HomePage.get("genericcatergorytexts").get("XPATH"), "{{value}}", category );
            driver.mouseHover(textXpath);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(HomePage.get(category+"genericcatergorylinks").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (testData.containsKey("redirectionLink")) {
                    driver.clickWhenReady(textXpath);
                    parentWindowHandle = driver.getWindowHandle();
                    driver.waitForTime(8);
                    Set<String> handles = driver.getWindowHandles();
                    Iterator<String> it = handles.iterator();
                    while (it.hasNext()) {
                        if (it.next() != parentWindowHandle)
                            childWindowHandle = it.next();
                    }
                    driver.switchToWindow(childWindowHandle);
                    driver.waitForTime(3);
                    if(!driver.waitForElementToAppear(HomePage.get("verifier"),20))
                    {
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")) || !driver.isElementDisplayed(HomePage.get("verifier"))) {
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean categorylinkselectronics(JSONArray data,String category) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        try {
            if(!driver.waitForElementToAppear(HomePage.get("verifier"),20)){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            textXpath = commonPageObject.getXpath(HomePage.get("genericcatergorytexts").get("XPATH"), "{{value}}", category );
            driver.mouseHover(textXpath);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(HomePage.get(category+"genericcatergorylinks").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (testData.containsKey("redirectionLink")) {
                    driver.clickWhenReady(textXpath);
                    parentWindowHandle = driver.getWindowHandle();
                    driver.waitForTime(8);
                    Set<String> handles = driver.getWindowHandles();
                    Iterator<String> it = handles.iterator();
                    while (it.hasNext()) {
                        if (it.next() != parentWindowHandle)
                            childWindowHandle = it.next();
                    }
                    driver.switchToWindow(childWindowHandle);
                    driver.waitForTime(3);
                    textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Help");
                    if(!driver.waitForElementToAppear(textXpath,20)){
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")) || !driver.isElementDisplayed(textXpath)) {
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly 2");
                        return isDone;
                    }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean categorylinksrenewals(JSONArray data,String category) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),25);
            textXpath = commonPageObject.getXpath(HomePage.get("genericcatergorytextsrenewals").get("XPATH"), "{{value}}", category );
            driver.mouseHover(textXpath);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(HomePage.get(category+"genericcatergorylinksrenewals").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)){
                    System.out.println("The textxpath not found");
                    return isDone;}
                if (testData.containsKey("redirectionLink")) {
                    if(i==1){
                        driver.click(textXpath);
                        parentWindowHandle = driver.getWindowHandle();
                        driver.waitForTime(8);
                        Set<String> handles = driver.getWindowHandles();
                        Iterator<String> it = handles.iterator();
                        while (it.hasNext()) {
                            if (it.next() != parentWindowHandle)
                                childWindowHandle = it.next();
                        }

                        driver.switchToWindow(childWindowHandle);
                        driver.waitForElementToAppear(HomePage.get("verifier"),15);
                        if(!driver.isElementDisplayed(HomePage.get("verifier")) ||!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))){
                            System.out.println("How claims works page did not load properly");
                        }
                    }else {

                        driver.click(textXpath);
                        parentWindowHandle = driver.getWindowHandle();
                        driver.waitForTime(8);
                        Set<String> handles = driver.getWindowHandles();
                        Iterator<String> it = handles.iterator();
                        while (it.hasNext()) {
                            if (it.next() != parentWindowHandle)
                                childWindowHandle = it.next();
                        }

                        driver.switchToWindow(childWindowHandle);
                        textXpath = commonPageObject.getXpath(HomePage.get("genericTextElement3").get("XPATH"), "{{value}}", "Log in with your phone number");
                        driver.waitForElementToAppear(textXpath,10);
                        if (!driver.isElementDisplayed(textXpath) ||!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))) {
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                    }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean categorylinksclaims(JSONArray data,String category) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),20);
            textXpath = commonPageObject.getXpath(HomePage.get("genericcatergorytextsclaims").get("XPATH"), "{{value}}", category );
            driver.mouseHover(textXpath);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(HomePage.get(category+"genericcatergorylinksclaims").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath) && !driver.isElementDisplayed(textXpath)){
                    System.out.println("The textxpath not found");
                    return isDone;}
                if (testData.containsKey("redirectionLink")) {
                    if(i==2){
                        driver.click(textXpath);
                        parentWindowHandle = driver.getWindowHandle();
                        driver.waitForTime(8);
                        Set<String> handles = driver.getWindowHandles();
                        Iterator<String> it = handles.iterator();
                        while (it.hasNext()) {
                            if (it.next() != parentWindowHandle)
                                childWindowHandle = it.next();
                        }
                        driver.switchToWindow(childWindowHandle);
                        if(!driver.waitForElementToAppear(HomePage.get("verifier"),10)){
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                        if(!driver.isElementDisplayed(HomePage.get("verifier")) ||!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))){
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                    }else {
                    driver.click(textXpath);
                        parentWindowHandle = driver.getWindowHandle();
                        driver.waitForTime(8);
                        Set<String> handles = driver.getWindowHandles();
                        Iterator<String> it = handles.iterator();
                        while (it.hasNext()) {
                            if (it.next() != parentWindowHandle)
                                childWindowHandle = it.next();
                        }
                        driver.switchToWindow(childWindowHandle);
                    textXpath = commonPageObject.getXpath(HomePage.get("genericTextElement3").get("XPATH"), "{{value}}", "Log in with your phone number");
                    if(!driver.waitForElementToAppear(textXpath,10)){
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                        if (!driver.isElementDisplayed(textXpath) || !driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))) {
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                    }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean resources(JSONArray data) {
        boolean isDone = false;
        String parentWindowHandle = null;
        String childWindowHandle = null;
        HashMap<String, String> textXpath = null;
        HashMap<String, String> currentProductPageXpath = null;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),20);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(HomePage.get("genericcatergorytextsresources").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath) && !driver.isElementDisplayed(textXpath)){
                    System.out.println("The textxpath not found");
                    return isDone;}
                if (testData.containsKey("redirectionLink")) {
                    driver.clickWhenReady(textXpath);
                    parentWindowHandle = driver.getWindowHandle();
                    driver.waitForTime(8);
                    Set<String> handles = driver.getWindowHandles();
                    Iterator<String> it = handles.iterator();
                    while (it.hasNext()) {
                        if (it.next() != parentWindowHandle)
                            childWindowHandle = it.next();
                    }
                    driver.switchToWindow(childWindowHandle);
                    if(!driver.waitForElementToAppear(HomePage.get("verifier"),15)){
                        driver.closeWindow();
                        driver.switchToWindow(parentWindowHandle);
                        System.out.println("The New Page did not load properly");
                        return isDone;
                    }
                    if (!driver.isElementDisplayed(HomePage.get("verifier")) ||!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))) {
                            driver.closeWindow();
                            driver.switchToWindow(parentWindowHandle);
                            System.out.println("The New Page did not load properly");
                            return isDone;
                        }
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("headercategory").get("XPATH"), "{{value}}", "Resources")) ;
                driver.clickWhenReady(currentProductPageXpath);

            }
            isDone = true;
            return isDone;

        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean verifysignuplogin(JSONArray data,String category) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),20);
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                textXpath = commonPageObject.getXpath(HomePage.get("signuplogin").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath))
                    return isDone;
                if (testData.containsKey("redirectionLink")) {
                    driver.click(textXpath);
                    driver.waitForTime(5);
                    if (!driver.isElementDisplayed(HomePage.get("singuploginidentifier")) || !driver.checkIfElementIsPresent(HomePage.get("singuploginidentifier")))
                        return isDone;
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink")))
                        return isDone;
                    driver.goBack();
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean verifyAckoLogo(JSONArray links) {

        boolean isDone = false;
        try {
            driver.clickWhenReady(HomePage.get("ackoLogoSection"));
            driver.waitForTime(5);
            if (!driver.getWebDriver().getCurrentUrl().equals(links.get(0)))
                return isDone;
            isDone = true;
            return isDone;
        } catch (Exception e) {

            e.printStackTrace();
            return isDone;
        }

    }
    public boolean verifyProfileSection(JSONArray link,JSONArray data) {
        boolean isDone = false;
        HashMap<String, String> textXpath = null;
        HashMap<String, String> currentProductPageXpath = null;

        try {
            currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("spanxpath").get("XPATH"), "{{value}}", "My account")) ;
            driver.clickWhenReady(currentProductPageXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "nikhil Kabba");
            if(!driver.checkIfElementIsPresent(textXpath)){
              System.out.println("User name is not visible properly");
              return isDone;
               }
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "View Profile");
            driver.clickWhenReady(textXpath);
            textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Contact info");
           if(!driver.waitForElementToAppear(textXpath,20)){
               System.out.println("The New Page did not load properly");
               return isDone;
           }
            if(!driver.getWebDriver().getCurrentUrl().equals(link.get(0))){
                System.out.println("Redirected link is not correct");
                return isDone;
            }
             driver.goBack();
            if(!driver.waitForElementToAppear(HomePage.get("verifier"),20)){
                System.out.println("The New Page did not load properly");
                return isDone;
            }
            for (int i = 0; i < data.size(); i++) {
                JSONObject testData = (JSONObject) data.get(i);
                currentProductPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get("spanxpath").get("XPATH"), "{{value}}", "My account")) ;
                driver.clickWhenReady(currentProductPageXpath);
                textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", testData.get("visibleText").toString());
                if (!driver.checkIfElementIsPresent(textXpath)){
                    System.out.println("The visible text element is not correct");
                    return isDone;}
                if (testData.containsKey("redirectionLink")) {
                    driver.click(textXpath);
                    driver.waitForTime(8);
                    textXpath = commonPageObject.getXpath(HomePage.get("generic*xpath").get("XPATH"), "{{value}}", "Covered by ACKO");
                    if(!driver.waitForElementToAppear(textXpath,15)){
                        System.out.println("my account page did not load as expected");
                        return isDone;
                    }
                    if (!driver.checkIfElementIsPresent(textXpath)){
                        System.out.println("my account page did not load as expected");
                        return isDone;
                    }
                    if (!driver.getWebDriver().getCurrentUrl().equals(testData.get("redirectionLink"))){
                        System.out.println("The redirected link is not correct");
                        return isDone;}
                    driver.goBack();
                }
            }
            isDone = true;
            return isDone;


        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }

    }
    public boolean verifyFooterRedirectionSection2(JSONArray links, String journey) {

        HashMap<String, String> currentPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {
            HashMap<String, String> genericFooterLinks = commonPageObject.getXpath(HomePage.get(journey + "FooterLinks").get("XPATH").toString(), "[{{index}}]", "");
            List<WebElement> pages = driver.findAll(genericFooterLinks);
            if (pages.size() > links.size()) {
                System.out.println("Number of pages are more than expected");
                return isDone;
            } else if (pages.size() < links.size()) {
                System.out.println("Number of pages are less than expected");
                return isDone;
            }
            for (int i = 1; i <= links.size(); i++) {
                currentPageXpath = new HashMap<>(commonPageObject.getXpath(HomePage.get(journey + "FooterLinks").get("XPATH"), "{{index}}", String.valueOf(i)));
                driver.waitForElementToAppear(currentPageXpath,10);
                driver.clickWhenReady(currentPageXpath);
                parentWindowHandle = driver.getWindowHandle();
                Set<String> handles = driver.getWindowHandles();
                Iterator<String> it = handles.iterator();
                while (it.hasNext()) {
                    if (it.next() != parentWindowHandle)
                        childWindowHandle = it.next();
                }
                driver.switchToWindow(childWindowHandle);
                if (!driver.getWebDriver().getCurrentUrl().equals(links.get(i - 1))) {
                    driver.waitForTime(2);
                    driver.closeWindow();
                    driver.switchToWindow(parentWindowHandle);
                    return isDone;
                }
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                driver.waitForElementToAppear(HomePage.get("verifier"),5);
                driver.scrollToElement(HomePage.get("verifier"));
            }

            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }


    }
    public boolean appdownload(JSONArray links, String value) {

        HashMap<String, String> currentPageXpath = null;
        String childWindowHandle = null;
        String parentWindowHandle = null;
        boolean isDone = false;
        try {
            driver.waitForElementToAppear(HomePage.get("verifier"),15);
            if(!driver.checkIfElementIsPresent(HomePage.get("verifier"))){
                System.out.println(config.get("baseURL")+"Took too long to load");
                return isDone;
            }
            driver.clickWhenReady(HomePage.get(value+"Download"));
            parentWindowHandle = driver.getWindowHandle();
            driver.waitForTime(8);
            Set<String> handles = driver.getWindowHandles();
            Iterator<String> it = handles.iterator();
            while (it.hasNext()) {
                if (it.next() != parentWindowHandle)
                    childWindowHandle = it.next();
            }
            driver.switchToWindow(childWindowHandle);
            if(!driver.waitForElementToAppear(HomePage.get(value+"verifier"),15)){
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                return isDone;
            }
            String url = driver.getWebDriver().getCurrentUrl();
            url = url.substring(0,23);
            if (!url.equals(links.get(0)) || !driver.checkIfElementIsPresent(HomePage.get(value+"verifier"))) {
                driver.closeWindow();
                driver.switchToWindow(parentWindowHandle);
                return isDone;
            }
            driver.closeWindow();
            driver.switchToWindow(parentWindowHandle);
            driver.scrollToElement(HomePage.get("verifier"));
            isDone = true;
            return isDone;
        } catch (Exception e) {
            e.printStackTrace();
            return isDone;
        }


    }
    //header functions end
}
