package com.bea.xml.stream.test;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AllTests {
   public static void main(String[] args) {
      TestRunner.run(suite());
   }

   public static Test suite() {
      TestSuite suite = new TestSuite("All Stream Parser Tests");
      suite.addTest(SimpleReaderTest.suite());
      suite.addTest(ReaderTest_wsdl.suite());
      suite.addTest(SimpleWriterTest.suite());
      suite.addTest(WriterTest_wsdl.suite());
      TestSetup wrapper = new TestSetup(suite) {
         protected void setUp() {
            System.setProperty("javax.xml.stream.XMLInputFactory", "com.bea.xml.stream.MXParserFactory");
            System.setProperty("javax.xml.stream.XMLOutputFactory", "com.bea.xml.stream.XMLOutputFactoryBase");
            System.setProperty("javax.xml.stream.XMLEventFactory", "com.bea.xml.stream.EventFactory");
            System.setProperty("log4j.configuration", "log4j.properties");
         }

         protected void tearDown() {
         }
      };
      return wrapper;
   }
}
