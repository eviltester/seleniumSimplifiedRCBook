REM this is the nosvn file referenced in the document
REM but the directory structure has changed so that I don't
REM overwrite the logs of the tests which are run from svn
rmdir /s /q "C:\Documents and Settings\Alan\.hudson\jobs\automated selenium tests with no svn\workspace\"
mkdir "C:\Documents and Settings\Alan\.hudson\jobs\automated selenium tests with no svn\workspace\"
xcopy "C:\selenium_tests\InitialSeleniumTests" "C:\Documents and Settings\Alan\.hudson\jobs\automated selenium tests with no svn\workspace" /e