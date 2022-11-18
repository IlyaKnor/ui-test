package com.example.uitest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Arrays;

public class QuestionnairePageTest {
    WebDriver driver;
    QuestionnairePage questionnairePage;
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
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        logIn();
        questionnairePage = new QuestionnairePage(driver);
    }

    private void logIn() {
        driver.get(applicationPath);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginEmail.sendKeys("test@protei.ru");
        loginPage.loginPassword.sendKeys("test");
        loginPage.authButton.click();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void shouldAddUsersWithCorrectEmailAndNameWithAllCheckBoxAndRadioButtonsAndMaleGender() {
        questionnairePage.dataEmail.sendKeys("Dave@gmail.com");
        questionnairePage.dataName.sendKeys("Dave");
        questionnairePage.checkBox11.click();
        questionnairePage.checkBox12.click();
        questionnairePage.radioButton21.click();
        questionnairePage.maleGender.click();
        questionnairePage.sendButton.click();
        questionnairePage.modalButton.click();

        Assertions.assertEquals(questionnairePage.userInfo.getText(),
                "Dave@gmail.com Dave Мужской 1.1, 1.2 2.1");
    }

    @Test
    public void shouldAddUsersWithCorrectEmailAndNameAndRadioButtonsAndMaleGenderWithoutAllCheckBox() {
        questionnairePage.dataEmail.sendKeys("Dave@gmail.com");
        questionnairePage.dataName.sendKeys("Dave");
        questionnairePage.radioButton21.click();
        questionnairePage.maleGender.click();
        questionnairePage.sendButton.click();
        questionnairePage.modalButton.click();

        Assertions.assertEquals(questionnairePage.userInfo.getText(),
                "Dave@gmail.com Dave Мужской Нет 2.1");
    }

    @Test
    public void shouldAddUsersWithCorrectEmailAndNameAndOneCheckBoxAndMaleGenderWithoutRadioButtons() {
        questionnairePage.dataEmail.sendKeys("Dave@gmail.com");
        questionnairePage.dataName.sendKeys("Dave");
        questionnairePage.checkBox12.click();
        questionnairePage.maleGender.click();
        questionnairePage.sendButton.click();
        questionnairePage.modalButton.click();

        Assertions.assertEquals(questionnairePage.userInfo.getText(),
                "Dave@gmail.com Dave Мужской 1.2");
    }

    @Test
    public void shouldAddUsersWithCorrectEmailAndNameWithAllCheckBoxAndRadioButtonsAndFemaleGender() {
        questionnairePage.dataEmail.sendKeys("Kate@gmail.com");
        questionnairePage.dataName.sendKeys("Kate");
        questionnairePage.checkBox11.click();
        questionnairePage.checkBox12.click();
        questionnairePage.radioButton21.click();
        questionnairePage.femaleGender.click();
        questionnairePage.sendButton.click();
        questionnairePage.modalButton.click();

        Assertions.assertEquals(questionnairePage.userInfo.getText(),
                "Kate@gmail.com Kate Женский 1.1, 1.2 2.1");
    }

    @Test
    public void shouldAddUsersWithCorrectEmailAndNameWithoutAllCheckBoxAndAllRadioButtons() {
        questionnairePage.dataEmail.sendKeys("Dave@gmail.com");
        questionnairePage.dataName.sendKeys("Dave");
        questionnairePage.sendButton.click();
        questionnairePage.modalButton.click();

        Assertions.assertEquals(questionnairePage.userInfo.getText(), "Dave@gmail.com Dave Мужской Нет");
    }


    @Test
    public void shouldAddUsersWithCorrectEmailAndName() {
        int counter = 0;
        while (counter != 3) {
            questionnairePage.dataEmail.sendKeys("Dave@gmail.com");
            questionnairePage.dataName.sendKeys("Dave");
            questionnairePage.sendButton.click();
            questionnairePage.modalButton.click();

            counter++;
        }
        Assertions.assertEquals(Arrays.stream(questionnairePage.userInfo.getText()
                        .split("\\n"))
                .toList().size(), 3);
    }

    @Test
    public void shouldNotAddUserWithoutEmail() {
        questionnairePage.dataName.sendKeys("Dave");
        questionnairePage.sendButton.click();
        Assertions.assertEquals(questionnairePage.emailFormatError.getAttribute("textContent"),
                "Неверный формат E-Mail");
    }

    @Test
    public void shouldNotAddUserWithoutName() {
        questionnairePage.dataEmail.sendKeys("Dave@ya.ru");
        questionnairePage.sendButton.click();
        Assertions.assertEquals(questionnairePage.blankNameError.getAttribute("textContent"),
                "Поле имя не может быть пустым");
    }

    @Test
    public void shouldCloseBlankNameError() {
        questionnairePage.dataEmail.sendKeys("Dave@ya.ru");
        questionnairePage.sendButton.click();
        questionnairePage.blankNameErrorButton.click();
    }

    @Test
    public void shouldCloseEmailFormatError() {
        questionnairePage.dataName.sendKeys("Dave");
        questionnairePage.sendButton.click();
        questionnairePage.emailFormatErrorButton.click();
    }

}
