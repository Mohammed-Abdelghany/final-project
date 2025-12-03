@echo off
REM إنشاء هيكل الفولدرات في نفس المسار الحالي

mkdir config
mkdir config\exception
mkdir controller
mkdir dto
mkdir helper
mkdir mapper
mkdir model
mkdir repo
mkdir service

echo.
echo ✅ All folders created successfully in: %cd%
pause
