REM this is the nosvn file referenced in the document
REM but the directory structure has changed so that I don't
REM overwrite the logs of the tests which are run from svn
rmdir /s /q "C:\hudson\home\jobs\Automated Selenium Simplified Tests\workspace\"
mkdir "C:\hudson\home\jobs\Automated Selenium Simplified Tests\workspace\"
xcopy "C:\Users\alan\Documents\Documents\Compendium Developments\testing\selenium_testing_book\junit_java_Code\junit\InitialSeleniumTests" "C:\hudson\home\jobs\Automated Selenium Simplified Tests\workspace" /e