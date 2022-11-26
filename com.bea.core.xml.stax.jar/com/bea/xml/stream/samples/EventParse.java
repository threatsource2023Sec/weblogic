package com.bea.xml.stream.samples;

import com.bea.xml.stream.XMLEventAllocatorBase;
import com.bea.xml.stream.util.ElementTypeNames;
import java.io.FileReader;
import java.util.Iterator;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;

public class EventParse {
   private static String filename = null;

   private static void printUsage() {
      System.out.println("usage: java com.bea.xml.stream.samples.EventParse <xmlfile>");
   }

   public static void main(String[] args) throws Exception {
      try {
         filename = args[0];
      } catch (ArrayIndexOutOfBoundsException var3) {
         printUsage();
         System.exit(0);
      }

      System.setProperty("javax.xml.stream.XMLInputFactory", "com.bea.xml.stream.MXParserFactory");
      XMLInputFactory xmlif = XMLInputFactory.newInstance();
      System.out.println("FACTORY: " + xmlif);
      xmlif.setProperty("javax.xml.stream.isReplacingEntityReferences", Boolean.FALSE);
      XMLStreamReader xmlr = xmlif.createXMLStreamReader(new FileReader(filename));
      System.out.println("READER:  " + xmlr + "\n");

      while(xmlr.hasNext()) {
         printEvent(xmlr);
         xmlr.next();
      }

   }

   public static final String getEventTypeString(int eventType) {
      return ElementTypeNames.getEventTypeString(eventType);
   }

   private static void printEvent(XMLStreamReader xmlr) {
      System.out.print("EVENT:[" + xmlr.getLocation().getLineNumber() + "][" + xmlr.getLocation().getColumnNumber() + "] ");
      System.out.print(getEventTypeString(xmlr.getEventType()));
      System.out.print(" [");
      switch (xmlr.getEventType()) {
         case 1:
            System.out.print("<");
            printName(xmlr);
            printNamespaces(XMLEventAllocatorBase.getNamespaces(xmlr));
            printAttributes(xmlr);
            System.out.print(">");
            break;
         case 2:
            System.out.print("</");
            printName(xmlr);
            printNamespaces(XMLEventAllocatorBase.getNamespaces(xmlr));
            System.out.print(">");
            break;
         case 3:
            System.out.print("<?");
            if (xmlr.hasText()) {
               System.out.print(xmlr.getText());
            }

            System.out.print("?>");
            break;
         case 4:
         case 6:
            int start = xmlr.getTextStart();
            int length = xmlr.getTextLength();
            System.out.print(new String(xmlr.getTextCharacters(), start, length));
            break;
         case 5:
            System.out.print("<!--");
            if (xmlr.hasText()) {
               System.out.print(xmlr.getText());
            }

            System.out.print("-->");
            break;
         case 7:
            System.out.print("<?xml");
            System.out.print(" version='" + xmlr.getVersion() + "'");
            System.out.print(" encoding='" + xmlr.getCharacterEncodingScheme() + "'");
            if (xmlr.isStandalone()) {
               System.out.print(" standalone='yes'");
            } else {
               System.out.print(" standalone='no'");
            }

            System.out.print("?>");
         case 8:
         case 10:
         case 11:
         default:
            break;
         case 9:
            System.out.print(xmlr.getLocalName() + "=");
            if (xmlr.hasText()) {
               System.out.print("[" + xmlr.getText() + "]");
            }
            break;
         case 12:
            System.out.print("<![CDATA[");
            if (xmlr.hasText()) {
               System.out.print(xmlr.getText());
            }

            System.out.print("]]>");
      }

      System.out.println("]");
   }

   private static void printEventType(int eventType) {
      System.out.print("EVENT TYPE(" + eventType + "):");
      System.out.println(getEventTypeString(eventType));
   }

   private static void printName(XMLStreamReader xmlr) {
      if (xmlr.hasName()) {
         String prefix = xmlr.getPrefix();
         String uri = xmlr.getNamespaceURI();
         String localName = xmlr.getLocalName();
         printName(prefix, uri, localName);
      }

   }

   private static void printName(String prefix, String uri, String localName) {
      if (uri != null && !"".equals(uri)) {
         System.out.print("['" + uri + "']:");
      }

      if (prefix != null) {
         System.out.print(prefix + ":");
      }

      if (localName != null) {
         System.out.print(localName);
      }

   }

   private static void printValue(XMLStreamReader xmlr) {
      if (xmlr.hasText()) {
         System.out.println("HAS VALUE: " + xmlr.getText());
      } else {
         System.out.println("HAS NO VALUE");
      }

   }

   private static void printAttributes(XMLStreamReader xmlr) {
      if (xmlr.getAttributeCount() > 0) {
         Iterator ai = XMLEventAllocatorBase.getAttributes(xmlr);

         while(ai.hasNext()) {
            System.out.print(" ");
            Attribute a = (Attribute)ai.next();
            printAttribute(a);
         }
      }

   }

   private static void printAttribute(Attribute a) {
      printName(a.getName().getPrefix(), a.getName().getNamespaceURI(), a.getName().getLocalPart());
      System.out.print("='" + a.getValue() + "'");
   }

   private static void printNamespaces(Iterator ni) {
      while(ni.hasNext()) {
         System.out.print(" ");
         Namespace n = (Namespace)ni.next();
         printNamespace(n);
      }

   }

   private static void printNamespace(Namespace n) {
      if (n.isDefaultNamespaceDeclaration()) {
         System.out.print("xmlns='" + n.getNamespaceURI() + "'");
      } else {
         System.out.print("xmlns:" + n.getPrefix() + "='" + n.getNamespaceURI() + "'");
      }

   }
}
