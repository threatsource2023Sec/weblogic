package weblogic.xml.babel.stream;

import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import weblogic.utils.collections.CircularQueue;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.StartElementEvent;
import weblogic.xml.stream.util.TypeFilter;

public class XMLOutputStreamBase implements XMLOutputStream {
   private static int MAXBUFSIZE = 1024;
   protected CircularQueue elementQ = null;
   protected XMLWriter xmlWriter = new XMLWriter();
   protected int maxBufferSize;
   protected StartElementEvent lastStartElement;
   private boolean writeEmptyTags;

   public XMLOutputStreamBase() {
      this.maxBufferSize = MAXBUFSIZE;
      this.lastStartElement = null;
      this.writeEmptyTags = false;
   }

   public XMLOutputStreamBase(XMLWriter xmlWriter) {
      this.maxBufferSize = MAXBUFSIZE;
      this.lastStartElement = null;
      this.writeEmptyTags = false;
      this.xmlWriter = xmlWriter;
   }

   public void setWriteEmptyTags(boolean val) {
      this.writeEmptyTags = val;
   }

   public XMLOutputStreamBase(Writer writer) {
      this.maxBufferSize = MAXBUFSIZE;
      this.lastStartElement = null;
      this.writeEmptyTags = false;
      this.setWriter(writer);
   }

   public void setMaxBufferSize(int size) {
      this.maxBufferSize = size;
   }

   public int getMaxBufferSize() {
      return this.maxBufferSize;
   }

   public void setWriter(Writer writer) {
      this.xmlWriter.setWriter(writer);
   }

   protected boolean writeXMLEvent(XMLEvent element) throws XMLStreamException {
      return this.xmlWriter.write(element);
   }

   protected boolean write() throws XMLStreamException {
      if (this.elementQ == null) {
         return false;
      } else if (this.xmlWriter == null) {
         throw new XMLStreamException("Atttempt to write XML Stream without setting the java.io.Writer.");
      } else {
         while(true) {
            while(true) {
               while(!this.elementQ.isEmpty()) {
                  if (this.writeEmptyTags) {
                     XMLEvent e = (XMLEvent)this.elementQ.remove();
                     XMLEvent nextE = (XMLEvent)this.elementQ.peek();
                     if (nextE != null && nextE.isCharacterData() && ((CharacterDataEvent)nextE).getContent().equals("")) {
                        this.elementQ.remove();
                        nextE = (XMLEvent)this.elementQ.peek();
                     }

                     if (e.isStartElement() && nextE != null && nextE.isEndElement()) {
                        this.xmlWriter.writeEmpty((StartElement)e);
                        this.elementQ.remove();
                     } else {
                        this.writeXMLEvent(e);
                     }
                  } else {
                     this.writeXMLEvent((XMLEvent)this.elementQ.remove());
                  }
               }

               return true;
            }
         }
      }
   }

   protected void addXMLEvent(XMLEvent e) throws XMLStreamException {
      if (this.elementQ == null) {
         this.elementQ = new CircularQueue(8);
      }

      if (e.isStartElement()) {
         if (!(e instanceof StartElementEvent)) {
            this.lastStartElement = new StartElementEvent((StartElement)e);
         } else {
            this.lastStartElement = (StartElementEvent)e;
         }

         this.elementQ.add(this.lastStartElement);
      } else {
         this.lastStartElement = null;
         this.elementQ.add(e);
         if (this.elementQ.size() > this.maxBufferSize) {
            this.flush();
         }

      }
   }

   public void add(Attribute attribute) throws XMLStreamException {
      if (this.lastStartElement == null) {
         throw new XMLStreamException("Attempt to add an attribute to a null start element");
      } else {
         if (this.isNamespace(attribute)) {
            this.lastStartElement.addNamespace(attribute);
         } else {
            this.lastStartElement.addAttribute(attribute);
         }

      }
   }

   private boolean isNamespace(Attribute a) {
      XMLName name = a.getName();
      if (name == null) {
         return false;
      } else {
         String prefix = name.getPrefix();
         if (prefix != null && "xmlns".equals(prefix)) {
            return true;
         } else {
            String localName = name.getLocalName();
            return prefix == null && localName != null && "xmlns".equals(localName);
         }
      }
   }

   public void add(XMLEvent element) throws XMLStreamException {
      this.addXMLEvent(element);
   }

   public void add(XMLInputStream inputStream) throws XMLStreamException {
      while(inputStream.hasNext()) {
         this.addXMLEvent(inputStream.next());
      }

   }

   public void add(String markup) throws XMLStreamException {
      new XMLInputStreamFactoryImpl();
      XMLInputStreamFactory factory = XMLInputStreamFactoryImpl.newInstance();
      XMLInputStream stream = factory.newInputStream((Reader)(new StringReader(markup)), (ElementFilter)(new TypeFilter(8318)));
      this.add(stream);
   }

   public void close(boolean flush) throws XMLStreamException {
      this.flush(flush);
      this.elementQ = null;
   }

   public void close() throws XMLStreamException {
      this.close(true);
   }

   private void flush(boolean flushWriter) throws XMLStreamException {
      this.lastStartElement = null;
      this.write();
      if (flushWriter) {
         this.xmlWriter.flush();
      }

   }

   public void flush() throws XMLStreamException {
      this.flush(true);
   }

   public static void main(String[] args) throws Exception {
      XMLWriter debugWriter = XMLWriter.getSymmetricWriter(new PrintWriter(System.out, true));
      XMLInputStreamBase root = new XMLInputStreamBase();
      root.open(SAXElementFactory.createInputSource(args[0]));
      XMLOutputStreamBase output = new XMLOutputStreamBase(debugWriter);
      output.add((XMLInputStream)root);
      output.flush();
   }

   static {
      String bufferSize = System.getProperty("weblogic.xml.outputStream.bufferSize");
      if (bufferSize != null) {
         try {
            int size = Integer.parseInt(bufferSize);
            if (size > 0) {
               MAXBUFSIZE = size;
            }
         } catch (NumberFormatException var2) {
         }
      }

   }
}
