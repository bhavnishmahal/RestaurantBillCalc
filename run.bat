@echo off
setlocal
echo ======================================================
echo           Restaurant Bill Calculator Launcher
echo ======================================================

if not exist "target\classes" mkdir "target\classes"

echo Compiling Java source files...
javac -d target\classes src\main\java\*.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed. Ensure JDK is installed.
    pause
    exit /b %ERRORLEVEL%
)

echo [OK] Compiled successfully!
echo.
echo Select mode:
echo   [1] Launch Swing GUI (Default)
echo   [2] Run Terminal CLI
echo   [3] Run Unit Tests
echo.
set /p CHOICE="Enter choice (1-3) [Default: 1]: "

if "%CHOICE%"=="2" (
    echo Running Terminal CLI...
    java -cp target\classes Main --cli
) else if "%CHOICE%"=="3" (
    if not exist "target\test-classes" mkdir "target\test-classes"
    javac -d target\test-classes -cp target\classes src\test\java\*.java
    java -cp "target\classes;target\test-classes" BillTest
) else (
    echo Launching Swing GUI...
    start "" javaw -cp target\classes Main
)

pause
