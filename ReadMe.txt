Hub:
sudo java -jar selenium-server-standalone-2.39.0.jar -role hub
Nodes(Chrome >= 31):
sudo java -jar selenium-server-standalone-2.39.0.jar -role webdriver -hub http://localhost:4444/grid/register -nodeConfig brw.json -Dwebdriver.chrome.driver=chromedriver
