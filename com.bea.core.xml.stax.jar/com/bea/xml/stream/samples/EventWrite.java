package com.bea.xml.stream.samples;

import java.io.FileReader;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

public class EventWrite {
   private static String filename = null;

   private static void printUsage() {
      System.out.println("usage: java com.bea.xml.stream.samples.EventWrite <xmlfile>");
   }

   public static void main(String[] args) throws Exception {
      try {
         filename = args[0];
      } catch (ArrayIndexOutOfBoundsException var5) {
         printUsage();
         System.exit(0);
      }

      System.setProperty("javax.xml.stream.XMLInputFactory", "com.bea.xml.stream.MXParserFactory");
      System.setProperty("javax.xml.stream.XMLOutputFactory", "com.bea.xml.stream.XMLOutputFactoryBase");
      System.setProperty("javax.xml.stream.XMLEventFactory", "com.bea.xml.stream.EventFactory");
      XMLInputFactory xmlif = XMLInputFactory.newInstance();
      XMLOutputFactory xmlof = XMLOutputFactory.newInstance();
      xmlif.setProperty("javax.xml.stream.isReplacingEntityReferences", Boolean.TRUE);
      XMLEventReader xmlr = xmlif.createXMLEventReader(new FileReader(filename));
      XMLEventWriter xmlw = xmlof.createXMLEventWriter(System.out);
      xmlw.add(xmlr);
      xmlw.flush();
   }
}
