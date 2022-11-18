package com.example.uitest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class QuestionnairePage {
    @FindBy(id = "dataEmail")
    public WebElement dataEmail;
    @FindBy(id = "dataName")
    public WebElement dataName;
    @FindBy(xpath = "//*[@id=\"dataGender\"]/option[1]")
    public WebElement maleGender;
    @FindBy(xpath = "//*[@id=\"dataGender\"]/option[2]")
    public WebElement femaleGender;
    @FindBy(id = "dataCheck11")
    public WebElement checkBox11;
    @FindBy(id = "dataCheck12")
    public WebElement checkBox12;
    @FindBy(id = "dataSelect21")
    public WebElement radioButton21;
    @FindBy(id = "dataSelect22")
    public WebElement radioButton22;
    @FindBy(id = "dataSelect23")
    public WebElement radioButton23;
    @FindBy(id = "dataSend")
    public WebElement sendButton;
    @FindBy(xpath = "//*[@id=\"dataTable\"]/tbody")
    public WebElement userInfo;
    @FindBy(xpath = "/html/body/div[3]/div/div/div[2]/button")
    WebElement modalButton;

    @FindBy(id = "blankNameError")
    WebElement blankNameError;

    @FindBy(xpath = "//*[@id=\"blankNameError\"]/a")
    WebElement blankNameErrorButton;

    @FindBy(id = "emailFormatError")
    WebElement emailFormatError;

    @FindBy(xpath = "//*[@id=\"emailFormatError\"]/a")
    WebElement emailFormatErrorButton;

    public QuestionnairePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
