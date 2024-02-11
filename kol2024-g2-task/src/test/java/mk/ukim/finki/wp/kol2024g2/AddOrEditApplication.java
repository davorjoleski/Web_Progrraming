package mk.ukim.finki.wp.kol2024g2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditApplication extends AbstractPage {
    private WebElement name;
    private WebElement originStory;
    private WebElement dateCreated;
    private WebElement applicationType;
    private WebElement scientist;
    private WebElement submit;

    public AddOrEditApplication(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String name, String originStory, String dateCreated, String applicationType, String scientist) {
        get(driver, addPath);
        assertRelativeUrl(driver, addPath);

        AddOrEditApplication addOrEditApplication = PageFactory.initElements(driver, AddOrEditApplication.class);
        addOrEditApplication.assertNoError();
        addOrEditApplication.name.sendKeys(name);
        addOrEditApplication.originStory.sendKeys(originStory);
        addOrEditApplication.dateCreated.sendKeys(dateCreated);

        Select selectType = new Select(addOrEditApplication.applicationType);
        selectType.selectByValue(applicationType);

        Select selectLocation = new Select(addOrEditApplication.scientist);
        selectLocation.selectByValue(scientist);

        addOrEditApplication.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static ItemsPage update(WebDriver driver, WebElement editButton, String name, String originStory, String dateCreated, String applicationType, String scientist) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        assertAbsoluteUrl(driver, href);

        AddOrEditApplication addOrEditApplication = PageFactory.initElements(driver, AddOrEditApplication.class);
        addOrEditApplication.name.clear();
        addOrEditApplication.name.sendKeys(name);
        addOrEditApplication.originStory.clear();
        addOrEditApplication.originStory.sendKeys(originStory);
        addOrEditApplication.dateCreated.clear();
        addOrEditApplication.dateCreated.sendKeys(dateCreated);

        Select selectType = new Select(addOrEditApplication.applicationType);
        selectType.selectByValue(applicationType);

        Select selectLocation = new Select(addOrEditApplication.scientist);
        selectLocation.selectByValue(scientist);

        addOrEditApplication.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }
}