package com.bea.xml.stream.test;

import com.bea.xml.stream.XMLStreamPlayer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class SimpleReaderTest extends BaseTestCase {
   protected String input;
   protected String master;
   protected XMLInputFactory factory;
   public static String fileName;

   public static void main(String[] args) throws Exception {
      fileName = args[0];
      TestRunner.run(suite());
   }

   public void setParams(String in) {
      this.input = in + ".xml";
      this.master = in + ".stream";
   }

   protected void setUp() {
      if (this.input == null) {
         this.input = "./files/play.xml";
      }

      if (this.master == null) {
         this.master = "./files/play.stream";
      }

      this.factory = XMLInputFactory.newInstance();
   }

   public static Test suite() {
      TestSuite testSuite = new TestSuite();
      SimpleReaderTest tc1 = new SimpleReaderTest();
      tc1.setParams(fileName);
      testSuite.addTest(tc1);
      return testSuite;
   }

   public void testStreamEquals() throws XMLStreamException, FileNotFoundException {
      this.globalCounter.increment();
      this.logger.info("Can the XMLStreamReader properly parse the XML document?");
      XMLStreamReader r1 = this.factory.createXMLStreamReader(new FileReader(this.input));
      XMLStreamReader r2 = new XMLStreamPlayer(new FileReader(this.master));
      Util util = new Util();
      if (r1 == null) {
         Assert.fail("Reader is null");
      }

      Assert.assertTrue(util.equals((XMLStreamReader)r1, (XMLStreamReader)r2).getValue());
      r1.close();
      r2.close();
      this.logger.info("XMLStreamReader successfully parsed " + this.input);
   }

   public void testEventEquals() throws XMLStreamException, FileNotFoundException {
      this.globalCounter.increment();
      this.logger.info("Can the XMLEventReader properly parse the XML document?");
      XMLEventReader e1 = this.factory.createXMLEventReader(new FileReader(this.input));
      XMLEventReader e2 = this.factory.createXMLEventReader(new XMLStreamPlayer(new FileReader(this.master)));
      Util util = new Util();
      if (e1 == null) {
         Assert.fail("Reader is null");
      }

      Assert.assertTrue(util.equals(e1, e2).getValue());
      this.logger.info("XMLEventReader successfully parsed " + this.input);
   }

   protected void tearDown() {
      this.logger.info("Number of tests run so far " + this.globalCounter.getCount());
   }
}
