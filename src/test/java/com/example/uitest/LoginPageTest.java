package com.example.uitest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class LoginPageTest {
    WebDriver driver;
    LoginPage loginPage;
    private static String applicationPath;
    private static String chromeDriver;


    @BeforeAll
    public static void configuration() {
        TestProperties testProperties = new TestProperties();
        applicationPath = testProperties.readProperty("application.path");
        chromeDriver = testProperties.readProperty("driver.chrome");
    }

    @BeforeEach
    public void setup() {

        System.setProperty("webdriver.chrome.driver", chromeDriver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(applicationPath);

        loginPage = new LoginPage(driver);
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    //Проверка осуществляется по возвращенному статусу 200, но в тестовом его не отправить
    @Test
    public void shouldPass_WithCorrectLoginAndPassword() {
        loginPage.loginEmail.sendKeys("test@protei.ru");
        loginPage.loginPassword.sendKeys("test");
        loginPage.authButton.click();
    }

    @Test
    public void shouldNotPass_WithIncorrectPasswordAndCorrectEmail() {
        loginPage.loginEmail.sendKeys("test@protei.ru");
        loginPage.loginPassword.sendKeys("test123");
        loginPage.authButton.click();
        Assertions.assertEquals(loginPage.emailAndPasswordAlert
                .getAttribute("textContent"), "Неверный E-Mail или пароль");
    }

    @Test
    public void shouldNotPass_WithNoPassword() {
        loginPage.loginEmail.sendKeys("test@protei.ru");
        loginPage.authButton.click();
        Assertions.assertEquals(loginPage.emailAndPasswordAlert
                .getAttribute("textContent"), "Неверный E-Mail или пароль");
    }

    @Test
    public void shouldNotPass_PassWithNoEmail() {
        loginPage.loginEmail.sendKeys(" ");
        loginPage.loginPassword.sendKeys("test");
        loginPage.authButton.click();
        Assertions.assertEquals(loginPage.emailAlert
                .getAttribute("textContent"), "Неверный формат E-Mail");
    }

    @Test
    public void shouldNotPass_WithoutLoginAndPassword() {
        loginPage.authButton.click();
        Assertions.assertEquals(loginPage.emailAlert
                .getAttribute("textContent"), "Неверный формат E-Mail");
    }

    @Test
    public void shouldNotPass_WithCorrectPasswordAndIncorrectEmail() {
        loginPage.loginEmail.sendKeys("dave@test.ru");
        loginPage.loginPassword.sendKeys("test");
        loginPage.authButton.click();
        Assertions.assertEquals(loginPage.emailAndPasswordAlert
                .getAttribute("textContent"), "Неверный E-Mail или пароль");
    }

    @Test
    public void shouldNotPass_WithCorrectPasswordAndEmailWithSymbol() {
        loginPage.loginEmail.sendKeys("test$protei.ru");
        loginPage.loginPassword.sendKeys("test");
        loginPage.authButton.click();
        Assertions.assertEquals(loginPage.emailAlert
                .getAttribute("textContent"), "Неверный формат E-Mail");

    }

    //Симво в email в верхенм регистре
    @Test
    public void shouldNotPass_WithCorrectPasswordButIncorrectEmail() {
        loginPage.loginEmail.sendKeys("teSt@protei.ru");
        loginPage.loginPassword.sendKeys("test");
        loginPage.authButton.click();
        Assertions.assertEquals(loginPage.emailAndPasswordAlert
                .getAttribute("textContent"), "Неверный E-Mail или пароль");
    }

    //Проверка максимальной длины email
    @Test
    public void maxSizeEmail() {
        String email = "a".repeat(1001) + "@mail.ru";
        loginPage.loginEmail.sendKeys(email);
        loginPage.loginPassword.sendKeys("test");
        loginPage.authButton.click();
        Assertions.assertEquals(loginPage.emailAndPasswordAlert
                .getAttribute("textContent"), "Неверный E-Mail или пароль");
    }

    @Test
    public void shouldNotPass_After1000ClickOnButton() {
        int count = 0;
        loginPage.loginEmail.sendKeys("dave@test.ru");
        loginPage.loginPassword.sendKeys("test");
        while (count != 1000) {
            loginPage.authButton.click();
            count++;
        }
        Assertions.assertEquals(loginPage.emailAndPasswordAlert
                .getAttribute("textContent"), "Неверный E-Mail или пароль");
    }

    @Test
    public void shouldCloseEmailAlertMessage() {
        loginPage.authButton.click();
        loginPage.emailAlertButton.click();
    }

    @Test
    public void closeEmailAndPasswordAlertMessage() {
        loginPage.loginEmail.sendKeys("test@mail.ru");
        loginPage.authButton.click();
        loginPage.emailAndPasswordAlertButton.click();
    }
}
