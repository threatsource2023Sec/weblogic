package com.bea.xml.stream.test;

import com.bea.xml.stream.XMLEventPlayer;
import com.bea.xml.stream.XMLStreamPlayer;
import com.bea.xml.stream.filters.TypeFilter;
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

public class SimpleFilterTest extends BaseTestCase {
   protected String input;
   protected String master;
   protected XMLInputFactory factory;
   TypeFilter f;
   // $FF: synthetic field
   static Class class$com$bea$xml$stream$test$SimpleFilterTest;

   public static void main(String[] args) {
      SimpleFilterTest fTest = new SimpleFilterTest();
      fTest.setParams(args[0]);
      TestRunner.run(suite());
   }

   public void setParams(String in) {
      this.logger.info("Running SimpleFilter Test");
      this.input = in + ".xml";
   }

   protected void setUp() {
      if (this.input == null) {
         this.input = "./files/play.xml";
      }

      if (this.master == null) {
         this.master = "./files/play_filtered_START_ELEMENT_END_ELEMENT.stream";
      }

      this.factory = XMLInputFactory.newInstance();
      this.f = new TypeFilter();
      this.f.addType(1);
      this.f.addType(2);
   }

   public static Test suite() {
      return new TestSuite(class$com$bea$xml$stream$test$SimpleFilterTest == null ? (class$com$bea$xml$stream$test$SimpleFilterTest = class$("com.bea.xml.stream.test.SimpleFilterTest")) : class$com$bea$xml$stream$test$SimpleFilterTest);
   }

   public void testStreamEquals() throws XMLStreamException, FileNotFoundException {
      this.logger.info("Can the XMLStreamReader properly parse the XML document with filter?");
      XMLStreamReader r1 = this.factory.createFilteredReader(this.factory.createXMLStreamReader(new FileReader(this.input)), this.f);
      XMLStreamReader r2 = new XMLStreamPlayer(new FileReader(this.master));
      Util util = new Util();
      if (r1 == null) {
         Assert.fail("Reader is null");
      }

      EqualityResult r = util.equals((XMLStreamReader)r1, (XMLStreamReader)r2);
      this.logger.info(r.toString());
      Assert.assertTrue(r.getValue());
      r1.close();
      r2.close();
      this.logger.info("XMLStreamReader successfully parsed " + this.input + " with filter");
   }

   public void testEventEquals() throws XMLStreamException, FileNotFoundException {
      this.logger.info("Can the XMLEventReader properly parse the XML document with filter?");
      XMLEventReader e1 = this.factory.createFilteredReader(this.factory.createXMLEventReader(new FileReader(this.input)), this.f);
      XMLEventReader e2 = new XMLEventPlayer(new XMLStreamPlayer(new FileReader(this.master)));
      Util util = new Util();
      if (e1 == null) {
         Assert.fail("Reader is null");
      }

      EqualityResult r = util.equals((XMLEventReader)e1, (XMLEventReader)e2);
      this.logger.info(r.toString());
      System.out.println("----------------------------------" + r.toString());
      Assert.assertTrue(r.getValue());
      this.logger.info("XMLEventReader successfully parsed " + this.input + " with filter");
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
