package org.apache.xml.security.stax.impl.stax;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import org.apache.xml.security.stax.ext.stax.XMLSecCharacters;
import org.apache.xml.security.stax.ext.stax.XMLSecEndElement;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public abstract class XMLSecEventBaseImpl implements XMLSecEvent {
   private static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
   protected XMLSecStartElement parentXMLSecStartELement;

   protected static EmptyIterator getEmptyIterator() {
      return EMPTY_ITERATOR;
   }

   public void setParentXMLSecStartElement(XMLSecStartElement xmlSecStartElement) {
      this.parentXMLSecStartELement = xmlSecStartElement;
   }

   public XMLSecStartElement getParentXMLSecStartElement() {
      return this.parentXMLSecStartELement;
   }

   public int getDocumentLevel() {
      return this.parentXMLSecStartELement != null ? this.parentXMLSecStartELement.getDocumentLevel() : 0;
   }

   public void getElementPath(List list) {
      if (this.parentXMLSecStartELement != null) {
         this.parentXMLSecStartELement.getElementPath(list);
      }

   }

   public List getElementPath() {
      List elementPath = new ArrayList();
      this.getElementPath(elementPath);
      return elementPath;
   }

   public XMLSecStartElement getStartElementAtLevel(int level) {
      return this.getDocumentLevel() < level ? null : this.parentXMLSecStartELement.getStartElementAtLevel(level);
   }

   public Location getLocation() {
      return new LocationImpl();
   }

   public boolean isStartElement() {
      return false;
   }

   public boolean isAttribute() {
      return false;
   }

   public boolean isNamespace() {
      return false;
   }

   public boolean isEndElement() {
      return false;
   }

   public boolean isEntityReference() {
      return false;
   }

   public boolean isProcessingInstruction() {
      return false;
   }

   public boolean isCharacters() {
      return false;
   }

   public boolean isStartDocument() {
      return false;
   }

   public boolean isEndDocument() {
      return false;
   }

   public XMLSecStartElement asStartElement() {
      throw new ClassCastException();
   }

   public XMLSecEndElement asEndElement() {
      throw new ClassCastException();
   }

   public XMLSecCharacters asCharacters() {
      throw new ClassCastException();
   }

   public QName getSchemaType() {
      return null;
   }

   public void writeAsEncodedUnicode(Writer writer) throws XMLStreamException {
      throw new UnsupportedOperationException("writeAsEncodedUnicode not implemented for " + this.getClass().getName());
   }

   private static final class EmptyIterator implements Iterator {
      private EmptyIterator() {
      }

      public boolean hasNext() {
         return false;
      }

      public Object next() {
         throw new NoSuchElementException();
      }

      public void remove() {
         throw new IllegalStateException();
      }

      // $FF: synthetic method
      EmptyIterator(Object x0) {
         this();
      }
   }

   static final class LocationImpl implements Location {
      public int getLineNumber() {
         return 0;
      }

      public int getColumnNumber() {
         return 0;
      }

      public int getCharacterOffset() {
         return 0;
      }

      public String getPublicId() {
         return null;
      }

      public String getSystemId() {
         return null;
      }
   }
}
