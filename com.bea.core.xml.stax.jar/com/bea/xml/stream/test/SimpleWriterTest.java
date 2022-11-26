package com.bea.xml.stream.test;

import com.bea.xml.stream.ReaderToWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class SimpleWriterTest extends BaseTestCase {
   protected String input;
   protected String output;
   File streamOutput;
   File eventOutput;
   protected XMLInputFactory inputFactory;
   protected XMLOutputFactory outputFactory;
   public static String fileName;

   public static void main(String[] args) {
      fileName = args[0];
      TestRunner.run(suite());
   }

   public void setParams(String in) {
      this.logger.info("Running SimpleWriter Test");
      this.input = in + ".xml";
   }

   protected void setUp() {
      try {
         if (this.input == null) {
            this.input = "./files/play.xml";
         }

         this.inputFactory = XMLInputFactory.newInstance();
         this.outputFactory = XMLOutputFactory.newInstance();
         this.streamOutput = File.createTempFile("sw_out1", ".tmp", new File("files"));
         this.eventOutput = File.createTempFile("sw_out2", ".tmp", new File("files"));
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public static Test suite() {
      TestSuite testSuite = new TestSuite();
      SimpleWriterTest tc1 = new SimpleWriterTest();
      tc1.setParams(fileName);
      testSuite.addTest(tc1);
      return testSuite;
   }

   public void testStreamEquals() throws XMLStreamException, FileNotFoundException, IOException {
      this.logger.info("Can the XMLStreamWriter properly parse the XML document?");
      XMLStreamReader r1 = this.inputFactory.createXMLStreamReader(new FileReader(this.input));
      XMLStreamWriter sw = this.outputFactory.createXMLStreamWriter(new FileWriter(this.streamOutput));
      ReaderToWriter rtow = new ReaderToWriter(sw);
      sw = rtow.writeAll(r1);
      r1 = this.inputFactory.createXMLStreamReader(new FileReader(this.input));
      XMLStreamReader r2 = this.inputFactory.createXMLStreamReader(new FileReader(this.streamOutput));
      Util util = new Util();
      if (r1 == null || r2 == null) {
         Assert.fail("Writer is null");
      }

      EqualityResult r = util.equals(r1, r2);
      this.logger.info(r.toString());
      Assert.assertTrue(r.getValue());
      r1.close();
      r2.close();
      sw.close();
      this.logger.info("XMLStreamWriter successfully parsed XML document " + this.input);
   }

   public void testEventEquals() throws XMLStreamException, FileNotFoundException, IOException {
      this.logger.info("Can the XMLEventWriter properly parse the XML document?");
      XMLEventReader e1 = this.inputFactory.createXMLEventReader(new FileReader(this.input));
      XMLEventWriter ew = this.outputFactory.createXMLEventWriter(new FileWriter(this.eventOutput));
      ew.add(e1);
      ew.flush();
      e1 = this.inputFactory.createXMLEventReader(new FileReader(this.input));
      XMLEventReader e2 = this.inputFactory.createXMLEventReader(new FileReader(this.eventOutput));
      Util util = new Util();
      if (e1 == null || e2 == null) {
         Assert.fail("Writer is null");
      }

      EqualityResult r = util.equals(e1, e2);
      this.logger.info(r.toString());
      Assert.assertTrue(r.getValue());
      ew.close();
      this.logger.info("XMLEventWriter successfully parsed XML document " + this.input);
   }

   protected void tearDown() {
      this.streamOutput.delete();
      this.eventOutput.delete();
   }
}
