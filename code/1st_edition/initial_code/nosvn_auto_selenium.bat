REM this is the nosvn file referenced in the book as nosvn.bat
REM if I run this locally while also having svn builds then I will overwrite my logs
REM so I run the variant which has the directory structure that I need
rmdir /s /q "C:\Documents and Settings\Alan\.hudson\jobs\automated selenium simplified tests\workspace\"
mkdir "C:\Documents and Settings\Alan\.hudson\jobs\automated selenium simplified tests\workspace\"
xcopy "C:\selenium_tests\InitialSeleniumTests" "C:\Documents and Settings\Alan\.hudson\jobs\automated selenium simplified tests\workspace" /e