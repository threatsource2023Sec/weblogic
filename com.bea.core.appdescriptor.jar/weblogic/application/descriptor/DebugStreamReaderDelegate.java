package weblogic.application.descriptor;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import weblogic.utils.Debug;

public abstract class DebugStreamReaderDelegate extends StreamReaderDelegate implements XMLStreamReader {
   private boolean debug = Debug.getCategory("weblogic.descriptor.reader").isEnabled();

   public DebugStreamReaderDelegate(XMLStreamReader reader) throws XMLStreamException {
      super(reader);
   }

   public char[] getTextCharacters() {
      char[] c = super.getTextCharacters();
      if (this.debug) {
         System.out.println("->getTextCharacters: " + (c == null ? "null" : new String(c)));
      }

      return c;
   }

   public int next() throws XMLStreamException {
      int next = super.next();
      if (this.debug) {
         System.out.println("->next = " + Utils.type2Str(next, this));
      }

      return next;
   }

   public void setParent(XMLStreamReader a) {
      if (this.debug) {
         System.out.println("->setParent");
      }

      super.setParent(a);
   }

   public XMLStreamReader getParent() {
      if (this.debug) {
         System.out.println("->getParent");
      }

      return super.getParent();
   }

   public int nextTag() throws XMLStreamException {
      int tag = super.nextTag();
      if (this.debug) {
         System.out.println("->nextTag: " + tag);
      }

      return tag;
   }

   public String getElementText() throws XMLStreamException {
      String et = super.getElementText();
      if (this.debug) {
         System.out.println("->getElementText: " + et);
      }

      return et;
   }

   public void require(int a, String b, String c) throws XMLStreamException {
      if (this.debug) {
         System.out.println("->require");
      }

      super.require(a, b, c);
   }

   public boolean hasNext() throws XMLStreamException {
      boolean b = super.hasNext();
      if (this.debug) {
         System.out.println("->hasNext: = " + b);
      }

      return b;
   }

   public void close() throws XMLStreamException {
      if (this.debug) {
         System.out.println("->close");
      }

      super.close();
   }

   public String getNamespaceURI(String a) {
      String n = super.getNamespaceURI(a);
      if (this.debug) {
         System.out.println("->getNamespaceURI(String): (" + a + "): " + n);
      }

      return n;
   }

   public NamespaceContext getNamespaceContext() {
      NamespaceContext n = super.getNamespaceContext();
      if (this.debug) {
         System.out.println("->getNamespaceContext: " + n);
      }

      return n;
   }

   public boolean isStartElement() {
      boolean b = super.isStartElement();
      if (this.debug) {
         System.out.println("->isStartElement: " + b);
         if (!b) {
            System.out.println("... eventType = " + Utils.type2Str(super.getEventType(), this));
         }
      }

      return b;
   }

   public boolean isEndElement() {
      if (this.debug) {
         System.out.println("->isEndElement");
      }

      return super.isEndElement();
   }

   public boolean isCharacters() {
      boolean b = super.isCharacters();
      if (this.debug) {
         System.out.println("->isCharacters: " + b);
      }

      return b;
   }

   public boolean isWhiteSpace() {
      if (this.debug) {
         System.out.println("->isWhiteSpace");
      }

      return super.isWhiteSpace();
   }

   public String getAttributeValue(String a, String b) {
      String s = super.getAttributeValue(a, b);
      if (this.debug) {
         System.out.println("->getAttributeValue(" + a + ", " + b + ") returns: " + s);
      }

      return s;
   }

   public int getAttributeCount() {
      int c = super.getAttributeCount();
      if (this.debug) {
         System.out.println("->getAttributeCount() returns " + c);
      }

      return c;
   }

   public QName getAttributeName(int index) {
      QName q = super.getAttributeName(index);
      if (this.debug) {
         System.out.println("->getAttributeName(" + index + ") returns: " + q);
      }

      return q;
   }

   public String getAttributePrefix(int index) {
      String s = super.getAttributePrefix(index);
      if (this.debug) {
         System.out.println("->getAttributePrefix(" + index + ") return " + s);
      }

      return s;
   }

   public String getAttributeNamespace(int index) {
      String s = super.getAttributeNamespace(index);
      if (this.debug) {
         System.out.println("->getAttributeNamespace(" + index + ") returns " + s);
      }

      return s;
   }

   public String getAttributeLocalName(int index) {
      String s = super.getAttributeLocalName(index);
      if (this.debug) {
         System.out.println("->getAttributeLocalName(" + index + ") returns " + s);
      }

      return s;
   }

   public String getAttributeType(int a) {
      String s = super.getAttributeType(a);
      if (this.debug) {
         System.out.println("->getAttributeType(" + a + " returns " + s);
      }

      return s;
   }

   public String getAttributeValue(int index) {
      String s = super.getAttributeValue(index);
      if (this.debug) {
         System.out.println("->getAttributeValue(" + index + ") returns: " + s);
      }

      return s;
   }

   public boolean isAttributeSpecified(int index) {
      boolean b = super.isAttributeSpecified(index);
      if (this.debug) {
         System.out.println("->isAttributeSpecified(" + index + ") returns " + b);
      }

      return b;
   }

   public int getNamespaceCount() {
      int c = super.getNamespaceCount();
      if (this.debug) {
         System.out.println("->getNamespaceCount return " + c);
      }

      return c;
   }

   public String getNamespacePrefix(int index) {
      String s = super.getNamespacePrefix(index);
      if (this.debug) {
         System.out.println("->getNamespacePrefix(" + index + ") return " + s);
      }

      return s;
   }

   public String getNamespaceURI(int a) {
      String s = super.getNamespaceURI(a);
      if (this.debug) {
         System.out.println("->getNamespaceURI(" + a + "): " + s);
      }

      return s;
   }

   public int getEventType() {
      int c = super.getEventType();
      if (this.debug) {
         System.out.println("->getEventType: " + Utils.type2Str(c, this));
      }

      return c;
   }

   public String getText() {
      String s = super.getText();
      if (this.debug) {
         System.out.println("->getText: " + s);
      }

      return s;
   }

   public int getTextCharacters(int a, char[] b, int c, int d) throws XMLStreamException {
      if (this.debug) {
         System.out.println("->getTextCharacters");
      }

      return super.getTextCharacters(a, b, c, d);
   }

   public int getTextStart() {
      int i = super.getTextStart();
      if (this.debug) {
         System.out.println("->getTextStart");
      }

      return i;
   }

   public int getTextLength() {
      if (this.debug) {
         System.out.println("->getTextLength");
      }

      return super.getTextLength();
   }

   public String getEncoding() {
      if (this.debug) {
         System.out.println("->getEncoding");
      }

      return super.getEncoding();
   }

   public boolean hasText() {
      if (this.debug) {
         System.out.println("->hasText");
      }

      return super.hasText();
   }

   public Location getLocation() {
      Location l = super.getLocation();
      if (this.debug) {
         System.out.println("->getLocation: " + l);
      }

      return l;
   }

   public QName getName() {
      if (this.debug) {
         System.out.println("->getName");
      }

      return super.getName();
   }

   public boolean hasName() {
      if (this.debug) {
         System.out.println("->hasName");
      }

      return super.hasName();
   }

   public String getPrefix() {
      if (this.debug) {
         System.out.println("->getPrefix");
      }

      return super.getPrefix();
   }

   public String getVersion() {
      if (this.debug) {
         System.out.println("->getVersion");
      }

      return super.getVersion();
   }

   public boolean isStandalone() {
      if (this.debug) {
         System.out.println("->isStandalone");
      }

      return super.isStandalone();
   }

   public boolean standaloneSet() {
      if (this.debug) {
         System.out.println("->standaloneSet");
      }

      return super.standaloneSet();
   }

   public String getCharacterEncodingScheme() {
      if (this.debug) {
         System.out.println("->getCharacterEncodingScheme");
      }

      return super.getCharacterEncodingScheme();
   }

   public String getPITarget() {
      if (this.debug) {
         System.out.println("->getPITarget");
      }

      return super.getPITarget();
   }

   public String getPIData() {
      if (this.debug) {
         System.out.println("->getPIData");
      }

      return super.getPIData();
   }

   public Object getProperty(String a) {
      if (this.debug) {
         System.out.println("->getProperty");
      }

      return super.getProperty(a);
   }
}
