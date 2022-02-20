Test using Java and Selenium

Test steps:
1.	Navigate to https://www.westernunion.com/lt/en/home.html
2.	Expand Burger menu
3.	Select Settings page
4.	Change WU.com Country to United States
5.	Assert that correct country page was loaded.

NOTE: The driver user for test is "ChromeDriver 98.0.4758.102". If the version does not match, find correct one from website: https://chromedriver.storage.googleapis.com/index.html 
The required place to log test results wasn't provided, so logging is done to terminal. 
Test may wait up to 45 seconds to remove pop-up when United States page is loaded. 
