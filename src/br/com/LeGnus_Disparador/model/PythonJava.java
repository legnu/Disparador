/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.LeGnus_Disparador.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
/**
 *
 * @author Ad3ln0r
 */
public class PythonJava {
    public static void main(String[] args) throws InterruptedException{
        
    System.setProperty("webdriver.gecko.driver", "D:\\HD\\Projetos\\Java\\Legnus Disparador\\geckodriver.exe");
    FirefoxOptions options = new FirefoxOptions();
    
   // ProfilesIni profini = new ProfilesIni();    
    //FirefoxProfile prof = profini.getProfile("default");   
    
    options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
   // options.setProfile(prof);
    
    WebDriver driver = new FirefoxDriver(options);
    Actions act = new Actions(driver);
    
    
    driver.get("https://web.whatsapp.com/");
    
    Thread.sleep(30000);
    
    driver.findElement(By.xpath("//div[contains(@class,'copyable-text selectable-text')]")).click();
    act.sendKeys("Legnu Infortec").perform();
    Thread.sleep(1000);    
    act.sendKeys(Keys.ARROW_DOWN, Keys.ENTER).perform();
    driver.findElement(By.cssSelector("div[title='Mensagem']")).click();
    act.sendKeys("").perform();
    act.sendKeys(Keys.ENTER).perform();
    }
}
