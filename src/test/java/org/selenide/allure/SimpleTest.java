package org.selenide.allure;

import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.*;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.codeborne.selenide.Selenide.*;

public class SimpleTest {

  @BeforeAll
  static void init(){
    SelenideLogger.addListener("AllureSelenide",
            new AllureSelenide()
                    .screenshots(true) // add screenshot as attachments
                    .savePageSource(true)); // add DOM source code as attachement
  }

  @Test
  @DisplayName("Simple Selenide test with steps")
  @Link("blob/master/org/selenide/allure/SimpleTest.java")
  void passedTest() throws IOException {
    openGoogle();
    startSearch();
    somethingFound();
  }

  @Step
  private void openGoogle() {
      open("https://google.com/ncr");
  }

  @Step
  private void startSearch() {
    $("[name=q]").setValue("selenide");
    $("input[name=btnK]").click();
  }

  @Step
  private void startSearchWrongLocator() {
    $("[name=q]").setValue("selenide");
    $("input[name=XXX]").click();
  }

  @Step
  private void somethingFound() {
    $$(".g").shouldHave(CollectionCondition.sizeGreaterThan(1));
  }

  @Step
  private void nothingFound() {
    $$(".g").shouldHave(CollectionCondition.size(0));
  }


  @Test
  void skippedTest() {
    Assumptions.assumeTrue(false);
  }

  @Test
  void failedTest() {
    openGoogle();
    startSearch();
    nothingFound();
  }

  @Test
  void wrongLocator() {
    openGoogle();
    startSearchWrongLocator();
  }

  @Test
  void testWithExceptions() {
    openGoogle();
    startSearch();
    throw new RuntimeException("Any exception you not aware of");
  }

}
