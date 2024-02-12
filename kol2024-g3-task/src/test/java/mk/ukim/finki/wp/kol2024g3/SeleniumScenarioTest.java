package mk.ukim.finki.wp.kol2024g3;

import com.fasterxml.jackson.core.JsonProcessingException;
import mk.ukim.finki.wp.exam.util.CodeExtractor;
import mk.ukim.finki.wp.exam.util.ExamAssert;
import mk.ukim.finki.wp.exam.util.SubmissionHelper;
import mk.ukim.finki.wp.kol2024g3.model.Presentation;
import mk.ukim.finki.wp.kol2024g3.model.PresentationType;
import mk.ukim.finki.wp.kol2024g3.model.Scientist;
import mk.ukim.finki.wp.kol2024g3.service.PresentationService;
import mk.ukim.finki.wp.kol2024g3.service.ScientistService;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {

    //TODO: CHANGE THE VALUE OF THE SubmissionHelper.index WITH YOUR INDEX NUMBER!!!
    static {
        SubmissionHelper.exam = "kol2024-g3";
        SubmissionHelper.index = "td";
    }

    @Autowired
    ScientistService scientistService;

    @Autowired
    PresentationService presentationService;

    @Order(1)
    @Test
    public void test_list_15pt() {
        SubmissionHelper.startTest("test-list-15");
        List<Presentation> items = this.presentationService.listAll();
        int itemNum = items.size();

        ExamAssert.assertNotEquals("Empty db", 0, itemNum);

        ItemsPage listPage = ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");
        listPage.assertItems(itemNum);

        SubmissionHelper.endTest();
    }

    @Order(2)
    @Test
    public void test_filter_5pt() {
        SubmissionHelper.startTest("test-filter-5");
        ItemsPage listPage = ItemsPage.to(this.driver);

        listPage.filter("", "");
        listPage.assertItems(10);

        listPage.filter("30", "");
        listPage.assertItems(5);

        listPage.filter("", PresentationType.TECHNOLOGY.name());
        listPage.assertItems(5);

        listPage.filter("30", PresentationType.TECHNOLOGY.name());
        listPage.assertItems(3);

        SubmissionHelper.endTest();
    }

    @Order(3)
    @Test
    public void test_filter_service_5pt() {
        SubmissionHelper.startTest("test-filter-service-5");

        ExamAssert.assertEquals("without filter", 10, this.presentationService.filterPresentations(null, null).size());
        ExamAssert.assertEquals("by years more than only", 5, this.presentationService.filterPresentations(30, null).size());
        ExamAssert.assertEquals("by type only", 5, this.presentationService.filterPresentations(null, PresentationType.SOCIAL).size());
        ExamAssert.assertEquals("by years less than and type", 3, this.presentationService.filterPresentations(30, PresentationType.TECHNOLOGY).size());

        SubmissionHelper.endTest();
    }

    @Order(4)
    @Test
    public void test_create_10pt() {
        SubmissionHelper.startTest("test-create-10");
        List<Scientist> scientists = this.scientistService.listAll();
        List<Presentation> presentations = this.presentationService.listAll();

        int itemNum = presentations.size();
        ItemsPage listPage = null;

        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
        }

        String date = LocalDate.now().minusYears(30).toString();

        listPage = AddOrEditPresentation.add(this.driver, ADD_URL, "testName", "testDescription", date, PresentationType.TECHNOLOGY.name(), scientists.get(0).getId().toString());
        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);
        listPage.assertNoError();
        listPage.assertItems(itemNum + 1);

        SubmissionHelper.endTest();
    }

    @Order(5)
    @Test
    public void test_create_mvc_10pt() throws Exception {
        SubmissionHelper.startTest("test-create-mvc-10");
        List<Scientist> scientists = this.scientistService.listAll();
        List<Presentation> presentations = this.presentationService.listAll();

        int itemNum = presentations.size();

        MockHttpServletRequestBuilder addRequest = MockMvcRequestBuilders
                .post("/presentations")
                .param("name", "testName")
                .param("description", "testDescription")
                .param("datePresented", LocalDate.now().minusDays(30).toString())
                .param("presentationType", PresentationType.TECHNOLOGY.name())
                .param("scientist", scientists.get(0).getId().toString());

        this.mockMvc.perform(addRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(LIST_URL));

        presentations = this.presentationService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum + 1, presentations.size());

        addRequest = MockMvcRequestBuilders
                .get("/presentations/add");

        this.mockMvc.perform(addRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(new ViewMatcher("form")));

        SubmissionHelper.endTest();
    }

    @Order(6)
    @Test
    public void test_edit_10pt() {
        SubmissionHelper.startTest("test-edit-10");
        List<Scientist> scientists = this.scientistService.listAll();
        List<Presentation> presentations = this.presentationService.listAll();

        int itemNum = presentations.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!LIST_URL.equals(this.driver.getCurrentUrl())) {
            System.err.println("Reloading items page");
            listPage = ItemsPage.to(this.driver);
        }

        listPage = AddOrEditPresentation.update(this.driver, listPage.getEditButtons().get(itemNum - 1), "testName", "testDescription", LocalDate.now().minusYears(30).toString(), PresentationType.TECHNOLOGY.name(), scientists.get(0).getId().toString());
        listPage.assertNoError();

        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);
        if (listPage.assertItems(itemNum)) {
            ExamAssert.assertEquals("The updated presentation name is not as expected.", "testName", listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(0).getText().trim());
        }

        SubmissionHelper.endTest();
    }

    @Order(7)
    @Test
    public void test_edit_mvc_10pt() throws Exception {
        SubmissionHelper.startTest("test-edit-mvc-10");
        List<Scientist> scientists = this.scientistService.listAll();
        List<Presentation> presentations = this.presentationService.listAll();

        int itemNum = presentations.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/presentations/" + presentations.get(itemNum - 1).getId())
                .param("name", "testName")
                .param("description", "testDescription")
                .param("datePresented", LocalDate.now().minusYears(30).toString())
                .param("presentationType", PresentationType.SOCIAL.name())
                .param("scientist", scientists.get(0).getId().toString());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(LIST_URL));

        presentations = this.presentationService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum, presentations.size());
        ExamAssert.assertEquals("The updated presentation name is not as expected.", "testName", presentations.get(itemNum - 1).getName());

        request = MockMvcRequestBuilders
                .get("/presentations/edit/" + presentations.get(itemNum - 1).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(new ViewMatcher("form")));

        SubmissionHelper.endTest();
    }

    @Order(8)
    @Test
    public void test_delete_5pt() throws Exception {
        SubmissionHelper.startTest("test-delete-5");
        List<Presentation> items = this.presentationService.listAll();
        int itemNum = items.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!LIST_URL.equals(this.driver.getCurrentUrl())) {
            System.err.println("Reloading items page");
            listPage = ItemsPage.to(this.driver);
        }

        listPage.getDeleteButtons().get(itemNum - 1).click();
        listPage.assertNoError();

        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);
        listPage.assertItems(itemNum - 1);

        SubmissionHelper.endTest();
    }

    @Order(9)
    @Test
    public void test_delete_mvc_5pt() throws Exception {
        SubmissionHelper.startTest("test-delete-5");
        List<Presentation> items = this.presentationService.listAll();
        int itemNum = items.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/presentations/delete/" + items.get(itemNum - 1).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(LIST_URL));

        items = this.presentationService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum - 1, items.size());

        SubmissionHelper.endTest();
    }

    @Order(10)
    @Test
    public void test_security_urls_10pt() {
        SubmissionHelper.startTest("test-security-urls-10");
        List<Presentation> players = this.presentationService.listAll();
        String editUrl = "/presentations/edit/" + players.get(0).getId();

        ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");

        AbstractPage.get(this.driver, LIST_URL);
        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);
        AbstractPage.get(this.driver, ADD_URL);
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);
        AbstractPage.get(this.driver, editUrl);
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);
        AbstractPage.get(this.driver, "/random");
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);

        LoginPage loginPage = LoginPage.openLogin(this.driver);
        LoginPage.doLogin(this.driver, loginPage, admin, admin);
        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);

        AbstractPage.get(this.driver, LIST_URL);
        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);

        AbstractPage.get(this.driver, ADD_URL);
        AbstractPage.assertRelativeUrl(this.driver, ADD_URL);

        AbstractPage.get(this.driver, editUrl);
        AbstractPage.assertRelativeUrl(this.driver, editUrl);

        LoginPage.logout(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");

        SubmissionHelper.endTest();
    }

    @Order(11)
    @Test
    public void test_security_buttons_10pt() {
        SubmissionHelper.startTest("test-security-buttons-10");
        List<Presentation> presentations = this.presentationService.listAll();
        int itemNum = presentations.size();

        ItemsPage playersPage = ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");
        playersPage.assertButtons(0, 0, 0, 0);

        LoginPage loginPage1 = LoginPage.openLogin(this.driver);
        playersPage = LoginPage.doLogin(this.driver, loginPage1, admin, admin);
        playersPage.assertButtons(itemNum, itemNum, 1, 0);
        LoginPage.logout(this.driver);

        LoginPage loginPage2 = LoginPage.openLogin(this.driver);
        playersPage = LoginPage.doLogin(this.driver, loginPage2, user, user);
        playersPage.assertButtons(0, 0, 0, itemNum);
        LoginPage.logout(this.driver);
        SubmissionHelper.endTest();
    }

    @Order(12)
    @Test
    public void test_vote_3pt() throws Exception {
        SubmissionHelper.startTest("test-vote-3");
        List<Presentation> presentations = this.presentationService.listAll();

        int itemNum = presentations.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, user, user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!LIST_URL.equals(this.driver.getCurrentUrl())) {
            System.err.println("Reloading items page");
            listPage = ItemsPage.to(this.driver);
        }

        listPage.getVoteButtons().get(itemNum - 1).click();
        listPage.assertNoError();

        AbstractPage.assertRelativeUrl(this.driver, LIST_URL);
        ExamAssert.assertEquals("The updated presentation votes number is not as expected.", "1", listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(5).getText().trim());

        SubmissionHelper.endTest();
    }

    @Order(13)
    @Test
    public void test_vote_mvc_2pt() throws Exception {
        SubmissionHelper.startTest("test-vote-mvc-2");
        List<Presentation> presentations = this.presentationService.listAll();

        int itemNum = presentations.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/presentations/vote/" + presentations.get(0).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(LIST_URL));

        presentations = this.presentationService.listAll();
        ExamAssert.assertEquals("Number of votes", presentations.get(0).getVotes(), 1);

        SubmissionHelper.endTest();
    }

    private HtmlUnitDriver driver;
    private MockMvc mockMvc;

    private static String admin = "admin";
    private static String user = "user";

    @BeforeEach
    private void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.driver = new HtmlUnitDriver(true);
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }

    @AfterAll
    public static void finalizeAndSubmit() throws JsonProcessingException {
        CodeExtractor.submitSourcesAndLogs();
    }

    public static final String LIST_URL = "/presentations";
    public static final String ADD_URL = "/presentations/add";
    public static final String LOGIN_URL = "/login";

    static class ViewMatcher implements Matcher<String> {

        final String baseName;

        ViewMatcher(String baseName) {
            this.baseName = baseName;
        }

        @Override
        public boolean matches(Object o) {
            if (o instanceof String) {
                String s = (String) o;
                return s.startsWith(baseName);
            }
            return false;
        }

        @Override
        public void describeMismatch(Object o, Description description) {
        }

        @Override
        public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
        }

        @Override
        public void describeTo(Description description) {
        }
    }
}
