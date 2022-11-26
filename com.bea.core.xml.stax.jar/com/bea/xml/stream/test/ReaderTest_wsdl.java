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

public class ReaderTest_wsdl extends BaseTestCase {
   protected String input;
   protected String master;
   protected XMLInputFactory factory;
   // $FF: synthetic field
   static Class class$com$bea$xml$stream$test$ReaderTest_wsdl;

   public static void main(String[] args) {
      TestRunner.run(suite());
   }

   protected void setUp() {
      this.input = "./files/wsdl_babelfish.xml";
      this.master = "./files/wsdl_babelfish.stream";
      this.factory = XMLInputFactory.newInstance();
   }

   public static Test suite() {
      return new TestSuite(class$com$bea$xml$stream$test$ReaderTest_wsdl == null ? (class$com$bea$xml$stream$test$ReaderTest_wsdl = class$("com.bea.xml.stream.test.ReaderTest_wsdl")) : class$com$bea$xml$stream$test$ReaderTest_wsdl);
   }

   public void testStreamEquals() throws XMLStreamException, FileNotFoundException {
      this.logger.info("Can the XMLStreamReader properly parse the WSDL document?");
      XMLStreamReader r1 = this.factory.createXMLStreamReader(new FileReader(this.input));
      XMLStreamReader r2 = new XMLStreamPlayer(new FileReader(this.master));
      Util util = new Util();
      if (r1 == null) {
         Assert.fail("Reader is null");
      }

      Assert.assertTrue(util.equals((XMLStreamReader)r1, (XMLStreamReader)r2).getValue());
      this.logger.info("XMLStreamReader successfully parsed WSDL document");
      r1.close();
      r2.close();
   }

   public void testEventEquals() throws XMLStreamException, FileNotFoundException {
      this.logger.info("Can the XMLEventReader properly parse the WSDL document?");
      XMLEventReader e1 = this.factory.createXMLEventReader(new FileReader(this.input));
      XMLEventReader e2 = this.factory.createXMLEventReader(new XMLStreamPlayer(new FileReader(this.master)));
      Util util = new Util();
      if (e1 == null) {
         Assert.fail("Reader is null");
      }

      Assert.assertTrue(util.equals(e1, e2).getValue());
      this.logger.info("XMLEventReader successfully parsed WSDL document");
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
