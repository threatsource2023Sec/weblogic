package com.bea.xml.stream;

import com.bea.xml.stream.util.ElementTypeNames;
import java.io.FileReader;
import java.io.Reader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class SubReader extends ReaderDelegate {
   private int depth = 0;
   private boolean open = true;

   public SubReader(XMLStreamReader reader) throws XMLStreamException {
      super(reader);
      if (!reader.isStartElement()) {
         throw new XMLStreamException("Unable to instantiate a subReader because the underlying reader was not on a start element.");
      } else {
         this.open = true;
         ++this.depth;
      }
   }

   public int next() throws XMLStreamException {
      if (this.depth <= 0) {
         this.open = false;
      }

      int type = super.next();
      if (this.isStartElement()) {
         ++this.depth;
      }

      if (this.isEndElement()) {
         --this.depth;
      }

      return type;
   }

   public int nextElement() throws XMLStreamException {
      this.next();

      while(this.hasNext() && !this.isStartElement() && !this.isEndElement()) {
         this.next();
      }

      return super.getEventType();
   }

   public boolean hasNext() throws XMLStreamException {
      return !this.open ? false : super.hasNext();
   }

   public boolean moveToStartElement() throws XMLStreamException {
      if (this.isStartElement()) {
         return true;
      } else {
         while(this.hasNext()) {
            if (this.isStartElement()) {
               return true;
            }

            this.next();
         }

         return false;
      }
   }

   public boolean moveToStartElement(String localName) throws XMLStreamException {
      if (localName == null) {
         return false;
      } else {
         while(this.moveToStartElement()) {
            if (localName.equals(this.getLocalName())) {
               return true;
            }

            if (!this.hasNext()) {
               return false;
            }

            this.next();
         }

         return false;
      }
   }

   public boolean moveToStartElement(String localName, String namespaceUri) throws XMLStreamException {
      if (localName != null && namespaceUri != null) {
         while(this.moveToStartElement(localName)) {
            if (namespaceUri.equals(this.getNamespaceURI())) {
               return true;
            }

            if (!this.hasNext()) {
               return false;
            }

            this.next();
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean moveToEndElement() throws XMLStreamException {
      if (this.isEndElement()) {
         return true;
      } else {
         while(this.hasNext()) {
            if (this.isEndElement()) {
               return true;
            }

            this.next();
         }

         return false;
      }
   }

   public boolean moveToEndElement(String localName) throws XMLStreamException {
      if (localName == null) {
         return false;
      } else {
         while(this.moveToEndElement()) {
            if (localName.equals(this.getLocalName())) {
               return true;
            }

            if (!this.hasNext()) {
               return false;
            }

            this.next();
         }

         return false;
      }
   }

   public boolean moveToEndElement(String localName, String namespaceUri) throws XMLStreamException {
      if (localName != null && namespaceUri != null) {
         while(this.moveToEndElement(localName)) {
            if (namespaceUri.equals(this.getNamespaceURI())) {
               return true;
            }

            if (!this.hasNext()) {
               return false;
            }

            this.next();
         }

         return false;
      } else {
         return false;
      }
   }

   public static void print(XMLStreamReader r, int depth) throws XMLStreamException {
      System.out.print("[" + depth + "]Sub: " + ElementTypeNames.getEventTypeString(r.getEventType()));
      if (r.hasName()) {
         System.out.println("->" + r.getLocalName());
      } else if (r.hasText()) {
         System.out.println("->[" + r.getText() + "]");
      } else {
         System.out.println();
      }

   }

   public static void sub(XMLStreamReader r, int depth) throws Exception {
      while(r.hasNext()) {
         print(r, depth);
         r.next();
      }

   }

   public static void main(String[] args) throws Exception {
      MXParser r = new MXParser();
      r.setInput((Reader)(new FileReader(args[0])));
      r.moveToStartElement();
      r.next();

      while(r.moveToStartElement()) {
         System.out.println("SE->" + r.getName());
         XMLStreamReader subr = r.subReader();
         sub(subr, 1);
      }

   }
}
