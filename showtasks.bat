call runcrud.bat
if "%ERRORLEVEL%" ==  "0" goto showtasks
echo.
echo There were errors in runcrud.bat file
goto end

:showtasks
call start chrome http://localhost:8080/crud/v1/task/getTasks

:end
echo.
echo Work done.