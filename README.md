# jira-xray-executor.java

### SETUP
1. Download jar file to location [PROJECT-ROOT]/vendor/  
[zfj-cloud-rest-client-1.3-jar-with-dependencies.jar](https://github.com/zephyrdeveloper/zapi-cloud/blob/master/Samples/production/zapi-cloud/generator/java/target/zfj-cloud-rest-client-1.3-jar-with-dependencies.jar?raw=true)

2. Add following dependency in your test maven project:
    ```XML
    <dependency>
        <groupId>com.qainfotech.tap</groupId>
        <artifactId>jira-xray-executor</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
    <dependency>
        <groupId>com.thed.zhephy.cloud.rest</groupId>
        <artifactId>zfj-cloud-rest-client</artifactId>
        <version>1.0</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/vendor/zfj-cloud-rest-client-1.3-jar-with-dependencies.jar</systemPath>
    </dependency>
    ```

3. Add following plugin to your test maven project:
    ```XML
      <plugin>
        <artifactId>maven-surefire-plugin</artifactId>
        <configuration>
          <skip>true</skip>
        </configuration>
      </plugin>
      <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>exec-maven-plugin</artifactId>
        <version>1.6.0</version>
        <executions>
          <execution>
            <id>JIRA Xray Test Execution</id>
            <phase>verify</phase>
            <goals>
              <goal>java</goal>
            </goals>
            <configuration>
              <mainClass>com.qainfotech.tap.JiraXrayExecutor</mainClass>
              <classpathScope>test</classpathScope>
              <cleanupDaemonThreads>false</cleanupDaemonThreads>
            </configuration>
          </execution>
        </executions>
      </plugin>
    ``` 

4. create jiraConfig.properties in project root with following parameters:
    ```JAVA
    xray.accessKey=[Your Account's Xray Access Key]
    xray.secretKey=[Your Account's Xray Secret Key]
    jira.accountId=[Your Account ID]
    jira.userId=[Your Account's Jira username]
    jira.apiKey=[Your Account's Jira api key]

    test.projectId=[JIRA Project ID]
    test.versionId=[JIRA version id, usualy '-1']
    test.label=[Label name to create test cycle from]
    test.testCycleName=[Name of test cycle to be created/executed]
    
    jenkins.url=[JENKINS BASE URL]
    ```
5. label test issues in Zephyr with class and method of TestNG Test.

For example:
 if Test Class is: com.qainfotech.tap.test.app1.ApplicationLandingPageTest
 and Test Method in class is: loginForIsDisplayedTest()
 
 The Test Issue in Jira Zephyr should have a label: @taid=com.qainfotech.tap.test.app1.ApplicationLandingPageTest#loginForIsDisplayedTest
 

### EXECUTE TESTS
```JAVA
    $> mvn clean verify
```

----
## ADVANCED

### Override jiraConfig:
Pass following maven command line flags:
```BASH
 -Dzephyr.accessKey=[Your Account's Zephyr Access Key]
 -Dzephyr.secretKey=[Your Account's Zephyr Secret Key]
 -Djira.accountId=[Your Account ID]
 -Djira.userId=[Your Account's Jira username]
 -Djira.apiKey=[Your Account's Jira api key]

 -Dtest.projectId=[JIRA Project ID]
 -Dtest.versionId=[JIRA version id, usualy '-1']
 -Dtest.label=[Label name to create test cycle from]
 -Dtest.testCycleName=[Name of test cycle to be created/executed]
 
 -Djenkins.url=[JENKINS BASE URL]
 -Djenkins.jobPath=[${JOB_NAME}/${BUILD_NUMBER}] // in Jenkins
```

### Stepped execution (Staged mode):
Pass any of the following flags to run specific step in Jira Test Execution:
```BASH
    -DjiraTestRunner.mode.staged
    -DjiraTestRunner.step.createNewCycle [Optional]
    -DjiraTestRunner.step.buildSuite [Optional]
    -DjiraTestRunner.step.runSuite [Optional]
    -DjiraTestRunner.step.postResults [Optional]
````
