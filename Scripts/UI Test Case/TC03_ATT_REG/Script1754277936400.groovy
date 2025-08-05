import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import org.openqa.selenium.WebElement

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.webui.common.WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
WebUI.callTestCase(findTestCase('SharedSteps/Login'), [:], FailureHandling.STOP_ON_FAILURE)

WebUI.click(findTestObject('Object Repository/WelcomePage/ZionApp'))

WebUI.waitForPageLoad(5)
WebUI.click(findTestObject('Attendance/Regularization'))

WebUI.click(findTestObject('Attendance/Add reg'))

WebUI.click(findTestObject('Object Repository/Attendance/DropDown'))

// Get the list of all employee elements
List AllEmployees_List = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Attendance/AllEmployees_List'), 5)

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
 



