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
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom
import org.apache.commons.lang.RandomStringUtils
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.ThreadLocalRandom
import org.apache.commons.lang.RandomStringUtils
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

String randomNumber = RandomStringUtils.randomNumeric(12)  // e.g. 5-digit number
LocalDate start = LocalDate.of(2015, 1, 1)  // Define start and end range for DOB
LocalDate end = LocalDate.of(2018, 12, 31)
// Generate a random date within the range
long randomEpochDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay())
LocalDate randomDOB = LocalDate.ofEpochDay(randomEpochDay)
// Format as dd-MMM-yyyy, e.g., 01-Aug-1995
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
String dobFormatted = randomDOB.format(formatter)

// Reusable method to select random option from a dropdown
void selectRandomDropdownOption(TestObject dropdownToggle, TestObject optionListLocator, String label) {
	// Click the dropdown toggle
	WebElement toggleElement = WebUI.findWebElement(dropdownToggle)
	WebUI.executeJavaScript('arguments[0].click();', [toggleElement])

	// Find all options
	List<WebElement> options = WebUI.findWebElements(optionListLocator, 2)
	assert options.size() > 0 : "${label} list is empty"

	// Pick a random option
	int randomIndex = new Random().nextInt(options.size())
	WebElement selectedOption = options[randomIndex]

	// Scroll and click
	JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getWebDriver()
	js.executeScript("arguments[0].scrollIntoView(true);", selectedOption)
	selectedOption.click()

	println "Selected ${label}: ${selectedOption.getText()}"
}


// Loop through each dropdown key and apply selection
for (String key : SelectionList) {
	selectRandomDropdownOption(
		findTestObject('Object Repository/DashBoard/DropdownsList_WorkRelated', [('Name') : key]),
		findTestObject('Object Repository/DashBoard/GenderList'),  // Assuming all lists use the same locator
		key
	)
}

WebUI.setText(findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'qualification']), 'Bachelors')

// Reusable method to set text and press Enter
void setTextAndEnter(TestObject field, String value) {
    WebUI.setText(field, value)
    WebUI.sendKeys(field, Keys.chord(Keys.ENTER))
}

// Usage for Date of Joining
TestObject dojField = findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'dateOfJoining'])
setTextAndEnter(dojField, dobFormatted)

// Usage for Aadhar Number
TestObject aadharField = findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'aadharNo'])
setTextAndEnter(aadharField, randomNumber)