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

String randomNumber = RandomStringUtils.randomNumeric(3)  // e.g. 5-digit number
String email = "Automation${randomNumber}@gmail.com"
String mobile = '9' + RandomStringUtils.randomNumeric(9)
LocalDate start = LocalDate.of(1990, 1, 1)  // Define start and end range for DOB
LocalDate end = LocalDate.of(1998, 12, 31)
// Generate a random date within the range
long randomEpochDay = ThreadLocalRandom.current().nextLong(start.toEpochDay(), end.toEpochDay())
LocalDate randomDOB = LocalDate.ofEpochDay(randomEpochDay)
// Format as dd-MMM-yyyy, e.g., 01-Aug-1995
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
String dobFormatted = randomDOB.format(formatter)

EmpLevels = WebUI.findWebElements(findTestObject('Object Repository/DashBoard/Employees_Levels'), 2).collect({it.text})

assert EmpLevels.size() == 4
assert EmpLevels == ['Personal Information', 'Employment Details', 'Banking Details', 'Preview']

List fields = WebUI.findWebElements(findTestObject('DashBoard/PersonalInfo_Fields'), 2)
List fieldIds = []

fields.each { el ->
	fieldIds << el.getAttribute('id')?.trim()
}

// Find index of 'dateOfBirth' and insert after it
int insertIndex = fieldIds.indexOf('dateOfBirth') + 1
fieldIds.add(insertIndex, 'gender')
fieldIds.add(insertIndex + 1, 'maritalStatus')

println fieldIds

WebUI.setText(findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName',[('Name'):'firstName']),'Automation')

WebUI.setText(findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'lastName']), 'Test')

WebUI.setText(findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'email']), email)

WebUI.setText(findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'mobile']), mobile)

TestObject dobField = findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'dateOfBirth'])
WebUI.scrollToElement(dobField, 3)
WebUI.setText(dobField, dobFormatted)
WebUI.sendKeys(dobField, Keys.chord(Keys.ENTER))

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

WebUI.setText(findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'permanentAddressLine1']), 'Hyderabad')

WebUI.setText(findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'permanentArea']), 'DLF Cyber City')

WebUI.setText(findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'permanentCity']), 'Hyderabad')

WebUI.setText(findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'permanentState']), 'Tamil Nadu')

TestObject permanentPinCode = findTestObject('Object Repository/DashBoard/PersonalInfo_Fields_ByName', [('Name') : 'permanentPinCode'])
WebUI.setText(permanentPinCode, '524'+randomNumber)
WebUI.sendKeys(permanentPinCode, Keys.chord(Keys.ENTER))

//WebUI.click(findTestObject('Object Repository/DashBoard/NextButton'))
