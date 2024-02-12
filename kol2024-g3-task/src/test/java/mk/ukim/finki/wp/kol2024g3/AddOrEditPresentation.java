package mk.ukim.finki.wp.kol2024g3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditPresentation extends AbstractPage {
    private WebElement name;
    private WebElement description;
    private WebElement datePresented;
    private WebElement presentationType;
    private WebElement scientist;
    private WebElement submit;

    public AddOrEditPresentation(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String name, String description, String datePresented, String type, String scientist) {
        get(driver, addPath);
        assertRelativeUrl(driver, addPath);

        AddOrEditPresentation addOrEditPresentation = PageFactory.initElements(driver, AddOrEditPresentation.class);
        addOrEditPresentation.assertNoError();
        addOrEditPresentation.name.sendKeys(name);
        addOrEditPresentation.description.sendKeys(description);
        addOrEditPresentation.datePresented.sendKeys(datePresented);

        Select selectType = new Select(addOrEditPresentation.presentationType);
        selectType.selectByValue(type);

        Select selectLocation = new Select(addOrEditPresentation.scientist);
        selectLocation.selectByValue(scientist);

        addOrEditPresentation.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static ItemsPage update(WebDriver driver, WebElement editButton, String name, String description, String datePresented, String type, String scientist) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        assertAbsoluteUrl(driver, href);

        AddOrEditPresentation addOrEditPresentation = PageFactory.initElements(driver, AddOrEditPresentation.class);
        addOrEditPresentation.name.clear();
        addOrEditPresentation.name.sendKeys(name);
        addOrEditPresentation.description.clear();
        addOrEditPresentation.description.sendKeys(description);
        addOrEditPresentation.datePresented.clear();
        addOrEditPresentation.datePresented.sendKeys(datePresented);

        Select selectType = new Select(addOrEditPresentation.presentationType);
        selectType.selectByValue(type);

        Select selectLocation = new Select(addOrEditPresentation.scientist);
        selectLocation.selectByValue(scientist);

        addOrEditPresentation.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }
}