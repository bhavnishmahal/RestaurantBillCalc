@echo off
setlocal
echo ======================================================
echo           Restaurant Bill Calculator Launcher
echo ======================================================

set "JAVAC_CMD="
set "JAVA_CMD="
set "JAVAW_CMD="
where javac >nul 2>&1
if not errorlevel 1 (
    set "JAVAC_CMD=javac"
    set "JAVA_CMD=java"
    set "JAVAW_CMD=javaw"
)

if not defined JAVAC_CMD if defined JAVA_HOME if exist "%JAVA_HOME%\bin\javac.exe" (
    set "JAVAC_CMD=%JAVA_HOME%\bin\javac.exe"
    set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
    set "JAVAW_CMD=%JAVA_HOME%\bin\javaw.exe"
)

if not defined JAVAC_CMD for /d %%J in ("C:\Program Files\Microsoft\jdk-*" "C:\Program Files\Eclipse Adoptium\jdk-*") do if exist "%%~J\bin\javac.exe" (
    set "JAVAC_CMD=%%~J\bin\javac.exe"
    set "JAVA_CMD=%%~J\bin\java.exe"
    set "JAVAW_CMD=%%~J\bin\javaw.exe"
)

if not defined JAVAC_CMD (
    echo [ERROR] JDK 17 or newer was not found.
    echo Install a JDK or configure JAVA_HOME, then try again.
    pause
    exit /b 1
)

if not exist "target\classes" mkdir "target\classes"

echo Compiling Java source files...
"%JAVAC_CMD%" -d target\classes src\main\java\*.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed. Ensure JDK is installed.
    pause
    exit /b %ERRORLEVEL%
)
if exist "src\main\resources" xcopy /E /I /Y "src\main\resources\*" "target\classes\" >nul

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
    "%JAVAC_CMD%" -d target\test-classes -cp target\classes src\test\java\*.java
    "%JAVA_CMD%" -cp "target\classes;target\test-classes" BillTest
) else (
    echo Launching Swing GUI...
    start "" "%JAVAW_CMD%" -cp target\classes Main
)

pause
