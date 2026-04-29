package Logintry;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Try {

    static final String EMAIL    = "uniquetandukar8645@gmail.com";
    static final String PASSWORD = "Tha chaina 098!";   // ← fill this in

    static final String ORG_ID   = "4758a134-3b32-4308-a5d4-fd3a3aa7f1ed";
    static final String DASHBOARD = "/dashboard";

    public static void main(String[] args) throws Exception {

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
            "--disable-notifications",
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--remote-allow-origins=*"
        );

        ChromeDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.manage().window().maximize();

            Loginpage loginPage = new Loginpage(driver);

            boolean authLoaded = loginPage.loadSavedAuth();

            if (!authLoaded) {
                // ── FIRST RUN ──────────────────────────────────────────────
                // Opens login page, you solve CAPTCHA, press ENTER → saved
                loginPage.manualLoginWithCaptcha(EMAIL, PASSWORD);
            }

            // ── EVERY RUN (including first, after save) ────────────────────
            loginPage.gotoAuthenticated(DASHBOARD, ".*\\/dashboard.*");

            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("✅ Dashboard reached! Add your automation below.");

            // ─── YOUR AUTOMATION STARTS HERE ──────────────────────────────
            // e.g. new InboxPage(driver).openFirstConversation();

            Thread.sleep(2000); // keep browser open briefly so you can see it
            
            System.out.println("Step 5: Clicking profile circle...");
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'rounded-full') " +
                         "and contains(@class,'shrink-0') " +
                         "and contains(@class,'h-8') " +
                         "and contains(@class,'w-8')]")
            )).click();
            System.out.println("Profile circle clicked!");
            Thread.sleep(1000);

            // Step: Click on org name
            try {
                WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[text()='All']"))).click();
            } catch (Exception e) {
                System.out.println("Org name click bhayena...");
            }

            // Switch org click
            wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//section[text()='Switch Organization']")
            )).click();
            System.out.println("Switch Organization clicked!");

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            
        }
    }
}