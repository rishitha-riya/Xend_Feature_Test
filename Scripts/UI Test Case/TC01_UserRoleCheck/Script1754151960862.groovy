import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import org.openqa.selenium.WebElement as WebElement
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.callTestCase(findTestCase('SharedSteps/Login'), [:], FailureHandling.STOP_ON_FAILURE)

GroupName = WebUI.getText(findTestObject('Object Repository/WelcomePage/GroupName'))

assert GroupName == 'Zion Groups'

List AppLists = WebUI.findWebElements(findTestObject('Object Repository/WelcomePage/AppList'), 3).collect({ 
        it.text
    })

assert AppLists == ['Zion App', 'Zion MBM']

WebUI.click(findTestObject('Object Repository/WelcomePage/ZionApp'))

WebUI.waitForPageLoad(5)

HomePage = WebUI.getText(findTestObject('Object Repository/DashBoard/HomePage_Title'))

assert HomePage == 'XendCampus'

WebUI.setText(findTestObject('Object Repository/DashBoard/SearchField'), 'Settings')

WebUI.waitForElementVisible(findTestObject('Object Repository/DashBoard/SuggestionList'), 10)

List suggestions = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/DashBoard/SuggestionList'), 10)

// Iterate and click the suggestion matching
String searchWord = 'Settings'

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

List Genearl_Tabs = WebUI.findWebElements(findTestObject('Object Repository/Settings/General/Tabs'), 3).collect({ 
        it.text
    })

assert Genearl_Tabs == Tabs : 'Tab Names are not same with the List'

WebUI.click(findTestObject('Object Repository/Settings/General/TabNames_ByName', [('Name') : Tabs[1]]))

WebUI.delay(3)

WebUI.setText(findTestObject('Object Repository/Settings/Users/Search'), 'Reddy')

WebUI.click(findTestObject('Object Repository/Settings/Users/SearchIcon'))

WebUI.waitForElementVisible(findTestObject('Object Repository/Settings/Users/SuggestedList_AfterSearch'), 10)

//List UserList = WebUiCommonHelper.findWebElements(
//	findTestObject('Object Repository/Settings/Users/SuggestedList_AfterSearch'), 10
//).collect({it.text})
//
//println UserList
def userElements = WebUiCommonHelper.findWebElements(findTestObject('Object Repository/Settings/Users/SuggestedList_AfterSearch'), 
    10)

List EmailName = []

for (def element : userElements) {
    if (element.text.contains('Reddy test')) {
        WebUI.click(findTestObject('Object Repository/Settings/Users/EllipsisIcon_ByName', [('Name') : element.text]))

        String MailID = WebUI.getText(findTestObject('Object Repository/Settings/Users/EmailID_ByName', [('Name') : element.text]))

        EmailName.add(MailID)

        WebUI.click(findTestObject('Object Repository/Settings/Users/EditButton_ByName', [('Name') : element.text]))

        println('Clicked on: ' + element.text)

        break
    }
}

println(EmailName)

List EditUser_Fields = WebUI.findWebElements(findTestObject('Object Repository/Settings/Edit User/EditUser_FieldsList'), 
    3).collect({ 
        it.text.trim()
    })

// Filter out empty strings and clean the fields
List cleanedFields = EditUser_Fields.findAll({ 
        it != '' // Remove empty strings
    }).collect({ 
        it.replaceAll('\\s*\\*\\s*$', '').trim()
    })

// Define expected list
List EditUserList = ['First Name', 'Last Name', 'Email', 'Roles', 'Associated Locations']

// Assert the fields match
assert cleanedFields == EditUserList : 'Field Names are not same with the Expected List'

List UserRoles = WebUI.findWebElements(findTestObject('Object Repository/Settings/Edit User/EditUser_RolesList'), 3).collect(
    { 
        it.text.trim()
    })

println(UserRoles)

List EditUser_Roles_Locations = WebUI.findWebElements(findTestObject('Object Repository/Settings/Edit User/EditUser_SelectedRoles_AssociatedLocations'), 
    3).collect({ 
        it.text.trim()
    }).collect({ 
        it.replaceAll('Remove', '').trim() // Remove the "Remove" text and trim extra spaces
    })

println(EditUser_Roles_Locations)

WebUI.click(findTestObject('Object Repository/Settings/Edit User/EditUser_CancelButton'))

WebUI.click(findTestObject('Object Repository/DashBoard/UserProfile_Icon'))

WebUI.click(findTestObject('Object Repository/DashBoard/UserProfile_Logout'))

WebUI.delay(5)

WebUI.click(findTestObject('Object Repository/LoginPage/Google_SignIn'))

WebUI.setText(findTestObject('Object Repository/LoginPage/Google_Username'), EmailName[0])

WebUI.click(findTestObject('Object Repository/LoginPage/Google_Next'))

WebUI.setText(findTestObject('Object Repository/LoginPage/Password'), GmailPassword)

WebUI.click(findTestObject('Object Repository/LoginPage/LoginButton'))



