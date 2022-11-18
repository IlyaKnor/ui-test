package com.example.uitest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(id = "loginEmail")
    public WebElement loginEmail;
    @FindBy(id = "loginPassword")
    public WebElement loginPassword;
    @FindBy(id = "authButton")
    public WebElement authButton;
    @FindBy(id = "invalidEmailPassword")
    public WebElement emailAndPasswordAlert;
    @FindBy(id = "emailFormatError")
    public WebElement emailAlert;
    @FindBy(xpath = "//*[@id=\"emailFormatError\"]/a")
    WebElement emailAlertButton;
    @FindBy(xpath = "//*[@id=\"invalidEmailPassword\"]/a")
    WebElement emailAndPasswordAlertButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
