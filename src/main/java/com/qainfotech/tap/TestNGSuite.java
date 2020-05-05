package com.qainfotech.tap;

import org.testng.xml.XmlSuite;
import org.testng.xml.SuiteXmlParser;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlTest;
import org.testng.xml.XmlInclude;
import org.testng.TestNG;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TestNGSuite {

    public static void addTest(String testName, String testClass, String testMethod) throws FileNotFoundException, IOException{
        XmlSuite suite = new XmlSuite();
        suite.setName("JIRATestNGTestSuite");
        suite.setFileName("JIRATestNGTestSuite.xml");
        File suiteFile = new File("target/JIRATestNGTestSuite.xml");
        if(suiteFile.exists()){
            suite = new SuiteXmlParser().parse("JIRATestNGTestSuite",
                    new FileInputStream(suiteFile), true);
        }
        XmlTest test = new XmlTest(suite);
        test.setName(testName);

        List<XmlClass> testClasses = new ArrayList<>();
        XmlClass xmlClass = new XmlClass(testClass);
        List<XmlInclude> methods = new ArrayList<>();
        methods.add(new XmlInclude(testMethod.trim()));
        xmlClass.setIncludedMethods(methods);
        testClasses.add(xmlClass);
        test.setXmlClasses(testClasses);
        suiteFile.createNewFile();
        try (FileWriter writer = new FileWriter(suiteFile)) {
            writer.write(suite.toXml());
            writer.flush();
            writer.close();
        }
    }

    public static void run(){
        TestNG testng = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add("target/JIRATestNGTestSuite.xml");
        testng.setTestSuites(suites);
        testng.setOutputDirectory("target/test-report");
        testng.run();
    }
}