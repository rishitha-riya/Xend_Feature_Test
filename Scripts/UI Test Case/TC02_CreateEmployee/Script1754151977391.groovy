import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.apache.commons.lang.RandomStringUtils as RandomStringUtils
import java.time.LocalDate as LocalDate
import java.time.format.DateTimeFormatter as DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom as ThreadLocalRandom
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper

WebUI.callTestCase(findTestCase('SharedSteps/Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/WelcomePage/ZionApp'))

WebUI.waitForPageLoad(5)



def element = WebUI.findWebElement(findTestObject('DashBoard/Employees'))

WebUI.executeJavaScript('arguments[0].click();', [element])

WebUI.delay(5)

List headers = WebUI.findWebElements(findTestObject('DashBoard/Employees_HeaderList'), 3)

List headerTexts = []

headers.each({ def el ->
        WebUI.executeJavaScript('arguments[0].scrollIntoView(true);', [el])

        WebUI.delay(0.2 // optional short delay to allow rendering
            )

        headerTexts << el.getText().trim()
    })

assert headerTexts == HeaderList : 'Employees Header are Incorrect'

WebUI.click(findTestObject('Object Repository/DashBoard/AddEmployee_Button'))

WebUI.delay(2)

WebUI.verifyElementText(findTestObject('Object Repository/DashBoard/AddEmployee_Header'), 'Add New Employee')

WebUI.callTestCase(findTestCase('SharedSteps/Employees_EnterPersonalInfo'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.callTestCase(findTestCase('Test Cases/SharedSteps/Employees_WorkRelated'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/DashBoard/SkipForNow'))

WebUI.delay(2)

Given_MobileNumber = WebUI.getText(findTestObject('Object Repository/DashBoard/Given_MobileNumber'))

println('Mobile Number : ' + Given_MobileNumber)

def SubmitButton = WebUI.findWebElement(findTestObject('Object Repository/DashBoard/SubmitButton'))

WebUI.executeJavaScript('arguments[0].click();', [SubmitButton])

assert WebUI.getText(findTestObject('Object Repository/DashBoard/Success_Title')) == 'Success' : 'Title is incorrect'

assert WebUI.getText(findTestObject('Object Repository/DashBoard/Success_Status')) == 'Employee added successfully' : 'Employee creation message is incorrect or Creation is not Successful'

WebUI.click(findTestObject('Object Repository/DashBoard/Employees_SearchDropdown'))

WebUI.waitForElementVisible(findTestObject('Object Repository/DashBoard/Employees_SearchDropDownList'), 10)

List suggestions = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/DashBoard/Employees_SearchDropDownList'), 
    10)

// Iterate and click the suggestion matching
String searchWord = 'Mobile Number'

boolean matchFound = false

for (WebElement suggestion : suggestions) {
    if (suggestion.getText().equalsIgnoreCase(searchWord)) {
        suggestion.click()

        matchFound = true

        break
    }
}

if (!(matchFound)) {
    WebUI.comment("No suggestion matches the search word '$searchWord'")
}

TestObject Employess_SearchInput = findTestObject('Object Repository/DashBoard/Employess_SearchInput')

WebUI.setText(Employess_SearchInput, Given_MobileNumber)

WebUI.sendKeys(Employess_SearchInput, Keys.chord(Keys.ENTER))

WebUI.delay(5)

List<WebElement> employessListAfterSearch = WebUI.findWebElements(findTestObject('Object Repository/DashBoard/EmployessList_AfterSearch'), 
    3)

assert employessListAfterSearch.size() == 1 : 'Duplicate or missing employee — expected exactly 1 match'

MobileNumber_AfterSearch = WebUI.getText(findTestObject('Object Repository/DashBoard/MobileNumber_AfterSearch'))

assert MobileNumber_AfterSearch == Given_MobileNumber

WebUI.callTestCase(findTestCase('SharedSteps/Logout'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.closeBrowser()