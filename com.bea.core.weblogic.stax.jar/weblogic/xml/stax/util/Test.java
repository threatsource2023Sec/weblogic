package weblogic.xml.stax.util;

import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import weblogic.xml.stax.StaticAllocator;
import weblogic.xml.stax.XMLStreamInputFactory;
import weblogic.xml.stax.XMLStreamOutputFactory;

public class Test {
   public static Reader openFile(String fname) throws Exception {
      return new FileReader(fname);
   }

   public static void readerInput(String fname) throws Exception {
      XMLInputFactory f = new XMLStreamInputFactory();
      System.out.println("InputFactory=[" + f + "]");
      XMLStreamReader r = f.createXMLStreamReader(openFile(fname));

      while(r.hasNext()) {
         System.out.println(r.toString());
         r.next();
      }

   }

   public static void eventReaderInput(String fname) throws Exception {
      XMLInputFactory f = new XMLStreamInputFactory();
      System.out.println("InputFactory=[" + f + "]");
      XMLEventReader r = f.createXMLEventReader(openFile(fname));

      while(r.hasNext()) {
         XMLEvent e = r.nextEvent();
         System.out.println("[" + TypeNames.getName(e.getEventType()) + "][" + e + "]");
      }

   }

   public static void staticReaderInput(String fname) throws Exception {
      XMLInputFactory f = new XMLStreamInputFactory();
      f.setEventAllocator(new StaticAllocator());
      System.out.println("InputFactory=[" + f + "]");
      XMLEventReader r = f.createXMLEventReader(openFile(fname));

      while(r.hasNext()) {
         XMLEvent e = r.nextEvent();
         System.out.println("[" + e.hashCode() + "][" + TypeNames.getName(e.getEventType()) + "][" + e + "]");
      }

   }

   public static void writerOutput() throws Exception {
      Writer w = new OutputStreamWriter(System.out);
      XMLOutputFactory output = new XMLStreamOutputFactory();
      System.out.println("OutputFactory[" + output + "]");
      XMLStreamWriter writer = output.createXMLStreamWriter(w);
      writer.writeStartDocument();
      writer.setPrefix("c", "http://c");
      writer.setDefaultNamespace("http://c");
      writer.writeStartElement("http://c", "a");
      writer.writeAttribute("b", "blah");
      writer.writeNamespace("c", "http://c");
      writer.writeDefaultNamespace("http://c");
      writer.setPrefix("d", "http://c");
      writer.writeEmptyElement("http://c", "d");
      writer.writeAttribute("http://c", "chris", "fry");
      writer.writeNamespace("d", "http://c");
      writer.writeCharacters("foo bar foo");
      writer.writeEndElement();
      writer.flush();
      output.setProperty("javax.xml.stream.isRepairingNamespaces", Boolean.TRUE);
      XMLStreamWriter writer2 = output.createXMLStreamWriter(w);
      writer2.writeStartDocument();
      writer2.setPrefix("c", "http://c");
      writer2.setDefaultNamespace("http://d");
      writer2.writeStartElement("http://c", "a");
      writer2.writeAttribute("b", "blah");
      writer2.writeEmptyElement("http://c", "d");
      writer2.writeEmptyElement("http://d", "e");
      writer2.writeEmptyElement("http://e", "f");
      writer2.writeEmptyElement("http://f", "g");
      writer2.writeAttribute("http://c", "chris", "fry");
      writer2.writeCharacters("foo bar foo");
      writer2.writeEndElement();
      writer2.flush();
   }

   public static void eventReaderToWriter(String fname) throws Exception {
      Writer w = new OutputStreamWriter(System.out);
      XMLOutputFactory output = new XMLStreamOutputFactory();
      XMLEventWriter writer = output.createXMLEventWriter(w);
      XMLInputFactory input = new XMLStreamInputFactory();
      XMLEventReader reader = input.createXMLEventReader(openFile(fname));

      while(reader.hasNext()) {
         XMLEvent e = reader.nextEvent();
         System.out.println("about to add:[" + e + "];");
         writer.add(e);
      }

      writer.flush();
   }

   public static void main(String[] args) throws Exception {
      String fileName = args[0];
      readerInput(fileName);
      eventReaderInput(fileName);
      staticReaderInput(fileName);
      writerOutput();
      eventReaderToWriter(fileName);
   }
}
