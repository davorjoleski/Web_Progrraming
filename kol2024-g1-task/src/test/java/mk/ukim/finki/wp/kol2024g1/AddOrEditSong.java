package mk.ukim.finki.wp.kol2024g1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditSong extends AbstractPage {
    private WebElement name;
    private WebElement originStory;
    private WebElement dateCreated;
    private WebElement genre;
    private WebElement artist;
    private WebElement submit;

    public AddOrEditSong(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String name, String originStory, String dateCreated, String genre, String artist) {
        get(driver, addPath);
        assertRelativeUrl(driver, addPath);

        AddOrEditSong addOrEditSong = PageFactory.initElements(driver, AddOrEditSong.class);
        addOrEditSong.assertNoError();
        addOrEditSong.name.sendKeys(name);
        addOrEditSong.originStory.sendKeys(originStory);
        addOrEditSong.dateCreated.sendKeys(dateCreated);

        Select selectType = new Select(addOrEditSong.genre);
        selectType.selectByValue(genre);

        Select selectLocation = new Select(addOrEditSong.artist);
        selectLocation.selectByValue(artist);

        addOrEditSong.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static ItemsPage update(WebDriver driver, WebElement editButton, String name, String originStory, String dateCreated, String genre, String artist) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        assertAbsoluteUrl(driver, href);

        AddOrEditSong addOrEditSong = PageFactory.initElements(driver, AddOrEditSong.class);
        addOrEditSong.name.clear();
        addOrEditSong.name.sendKeys(name);
        addOrEditSong.originStory.clear();
        addOrEditSong.originStory.sendKeys(originStory);
        addOrEditSong.dateCreated.clear();
        addOrEditSong.dateCreated.sendKeys(dateCreated);

        Select selectType = new Select(addOrEditSong.genre);
        selectType.selectByValue(genre);

        Select selectLocation = new Select(addOrEditSong.artist);
        selectLocation.selectByValue(artist);

        addOrEditSong.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }
}