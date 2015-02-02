# WordCamp-Android

### Server setup
Download wordpress-meta-environment which has the copy of WordCamp Central which we will be using for testing purpose.

As local Vagrant can't be accessed from phones create a local server with the following script.

``` php
<?php
$cu = curl_init($_GET["url"]);
$result = curl_exec($cu);
json_decode($result, true);
?>
```
### Project setup
Clone this repo and import the project in Android studio. Gradle build will import all necessary libs required for the project.

Just change the path of your local server in `WPAPIClient.java`

Run it!