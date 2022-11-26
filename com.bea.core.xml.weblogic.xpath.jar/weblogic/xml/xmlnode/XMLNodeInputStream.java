package weblogic.xml.xmlnode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.xml.sax.InputSource;
import weblogic.utils.collections.CircularQueue;
import weblogic.xml.babel.adapters.ElementFactory;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.SymbolTable;
import weblogic.xml.babel.stream.XMLInputStreamBase;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.ChangePrefixMappingEvent;
import weblogic.xml.stream.events.EndPrefixMappingEvent;
import weblogic.xml.stream.events.StartPrefixMappingEvent;

public class XMLNodeInputStream extends XMLInputStreamBase {
   XMLNodeStreamIterator nodeIterator;
   SymbolTable symbolTable;

   public XMLNodeInputStream() {
      this.symbolTable = new SymbolTable();
      this.symbolTable.put("", (String)null);
      this.symbolTable.put("xml", "http://www.w3.org/XML/1998/namespace");
   }

   public XMLNodeInputStream(InputStream inputStream) throws XMLStreamException {
      this();
      this.open(inputStream);
   }

   public XMLNodeInputStream(XMLNode node) {
      this();
      this.open(node);
   }

   public static void main(String[] args) throws Exception {
      XMLInputStream inputStream = new XMLNodeInputStream(new FileInputStream(args[0]));

      while(inputStream.hasNext()) {
         XMLEvent element = inputStream.next();
         System.out.println(element);
         if (element.getType() == 2) {
         }
      }

   }

   public void open(InputSource inputSource) throws XMLStreamException {
      throw new XMLStreamException("Use open(XMLNode node) to open this stream.");
   }

   public void open(InputSource inputSource, ElementFactory factory) throws XMLStreamException {
      throw new XMLStreamException("Use open(XMLNode node) to open this stream.");
   }

   public void open(InputStream inputStream) throws XMLStreamException {
      XMLNode node = new XMLNode();

      try {
         node.read(inputStream);
      } catch (IOException var4) {
         throw new XMLStreamException(var4);
      }

      this.open(node);
   }

   public void open(XMLNode node) {
      this.nodeIterator = new XMLNodeStreamIterator(node);
      this.elementQ = new CircularQueue(8);
      this.open = true;
   }

   public boolean parseSome() throws XMLStreamException {
      if (debugSubStream) {
         System.out.println("Parsing Some in Node");
      }

      if (!this.nodeIterator.hasNext()) {
         return false;
      } else {
         XMLEvent event = this.nodeIterator.next();
         if (event.isStartElement()) {
            this.symbolTable.openScope();
            StartElement se = (StartElement)event;
            AttributeIterator i = se.getNamespaces();

            while(i.hasNext()) {
               Attribute a = i.next();
               String name = a.getName().getLocalName();
               if (name.equals("xmlns")) {
                  name = "";
               }

               this.add(new StartPrefixMappingEvent(name, a.getValue()));
               this.symbolTable.put(name, a.getValue());
            }
         }

         this.add(event);
         if (event.isEndElement()) {
            List keys = this.symbolTable.closeScope();
            int i = 0;

            for(int len = keys.size(); i < len; ++i) {
               PrefixMapping prefixMapping = (PrefixMapping)keys.get(i);
               if (prefixMapping.getUri() == null) {
                  this.add(new EndPrefixMappingEvent(prefixMapping.getPrefix()));
               } else {
                  this.add(new ChangePrefixMappingEvent(prefixMapping.getOldUri(), prefixMapping.getUri(), prefixMapping.getPrefix()));
               }
            }
         }

         return true;
      }
   }

   public boolean hasNext() throws XMLStreamException {
      if (!this.open) {
         return false;
      } else if (!this.elementQ.isEmpty()) {
         return true;
      } else {
         return this.nodeIterator.hasNext();
      }
   }

   public XMLEvent peek() throws XMLStreamException {
      if (!this.elementQ.isEmpty()) {
         return (XMLEvent)this.elementQ.peek();
      } else {
         this.parseSome();
         return (XMLEvent)this.elementQ.peek();
      }
   }

   public void close() throws XMLStreamException {
      super.close();
      this.nodeIterator = null;
   }
}
