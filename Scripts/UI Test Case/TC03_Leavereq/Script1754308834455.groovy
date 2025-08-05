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
import org.openqa.selenium.WebElement
import com.kms.katalon.core.webui.common.WebUiCommonHelper


WebUI.callTestCase(findTestCase('SharedSteps/Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/WelcomePage/ZionApp'))

WebUI.waitForPageLoad(5)


WebUI.click(findTestObject("Object Repository/Leave_request/Leave_request"))

WebUI.click(findTestObject('Object Repository/Leave_request/Apply_leave'))

WebUI.click(findTestObject('Object Repository/Leave_request/Emp_list'))


// Get the list of all employee elements
List AllEmployees_List = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Leave_request/Dropdown_list'), 5)
println 'Find ttoal Employees in the List11 : '+ AllEmployees_List.get(0)

// Check if the list is not empty
if (!AllEmployees_List.isEmpty()) {
	
	int randomIndex = new Random().nextInt(AllEmployees_List.size())
	
	println 'Total Employees in the List : '+ randomIndex

	WebElement randomEmployee = AllEmployees_List.get(randomIndex)

	randomEmployee.click()

	WebUI.comment("Clicked on random employee: " + randomEmployee.getText())
} else {
	WebUI.comment("Employee list is empty.")
}

WebUI.click(findTestObject('Object Repository/Leave_request/Leave_type'))

// Get the list of all leave types
List AllEmployees_List = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Leave_request/Leave_type_dropdown'), 5)

// Check if the list is not empty
if (!Leave_type_dropdown.isEmpty()) {
	
	int randomIndex = new Random().nextInt(AllEmployees_List.size())
	
	println 'Total Employees in the List : '+ randomIndex

	WebElement randomEmployee = AllEmployees_List.get(randomIndex)

	randomEmployee.click()

	WebUI.comment("Clicked on random employee: " + randomEmployee.getText())
} else {
	WebUI.comment("List is empty")
}	

