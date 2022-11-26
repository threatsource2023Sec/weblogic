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

public class WriterTest_wsdl extends BaseTestCase {
   protected String input;
   protected String output;
   File streamOutput;
   File eventOutput;
   protected XMLInputFactory inputFactory;
   protected XMLOutputFactory outputFactory;
   // $FF: synthetic field
   static Class class$com$bea$xml$stream$test$WriterTest_wsdl;

   public static void main(String[] args) {
      TestRunner.run(suite());
   }

   protected void setUp() {
      try {
         this.logger.info("Writer for wsdl Test Setup");
         this.input = "./files/wsdl_babelfish.xml";
         this.inputFactory = XMLInputFactory.newInstance();
         this.outputFactory = XMLOutputFactory.newInstance();
         this.streamOutput = File.createTempFile("wsdl_out1", ".tmp", new File("files"));
         this.eventOutput = File.createTempFile("wsdl_out2", ".tmp", new File("files"));
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public static Test suite() {
      return new TestSuite(class$com$bea$xml$stream$test$WriterTest_wsdl == null ? (class$com$bea$xml$stream$test$WriterTest_wsdl = class$("com.bea.xml.stream.test.WriterTest_wsdl")) : class$com$bea$xml$stream$test$WriterTest_wsdl);
   }

   public void testStreamEquals() throws XMLStreamException, FileNotFoundException, IOException {
      this.logger.info("Can the XMLStreamWriter properly parse the WSDL document?");
      XMLStreamReader r1 = this.inputFactory.createXMLStreamReader(new FileReader(this.input));
      XMLStreamWriter sw = this.outputFactory.createXMLStreamWriter(new FileWriter(this.streamOutput));
      ReaderToWriter rtow = new ReaderToWriter(sw);
      sw = rtow.writeAll(r1);
      XMLStreamReader r2 = this.inputFactory.createXMLStreamReader(new FileReader(this.streamOutput));
      Util util = new Util();
      if (r1 == null || r2 == null) {
         Assert.fail("Writer is null");
      }

      Assert.assertTrue("Completed StreamEquals() for Writer", util.equals(r1, r2).getValue());
      r1.close();
      r2.close();
      sw.close();
      this.logger.info("XMLStreamWriter successfully parsed WSDL document");
   }

   public void testEventEquals() throws XMLStreamException, FileNotFoundException, IOException {
      this.logger.info("Can the XMLEventWriter properly parse the WSDL document?");
      XMLEventReader e1 = this.inputFactory.createXMLEventReader(new FileReader(this.input));
      XMLEventWriter ew = this.outputFactory.createXMLEventWriter(new FileWriter(this.eventOutput));
      ew.add(e1);
      ew.flush();
      XMLEventReader e2 = this.inputFactory.createXMLEventReader(new FileReader(this.eventOutput));
      Util util = new Util();
      if (e1 == null || e2 == null) {
         Assert.fail("Writer is null");
      }

      Assert.assertTrue("Completed EventEquals() for Writer", util.equals(e1, e2).getValue());
      ew.close();
      this.logger.info("XMLEventWriter successfully parsed WSDL document");
   }

   protected void tearDown() {
      this.streamOutput.delete();
      this.eventOutput.delete();
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
