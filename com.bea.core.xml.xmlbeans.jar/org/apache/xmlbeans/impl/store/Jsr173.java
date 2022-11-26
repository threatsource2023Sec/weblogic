package org.apache.xmlbeans.impl.store;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.XmlDocumentProperties;
import org.apache.xmlbeans.XmlLineNumber;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Node;

public class Jsr173 {
   public static Node nodeFromStream(XMLStreamReader xs) {
      if (!(xs instanceof Jsr173GateWay)) {
         return null;
      } else {
         Jsr173GateWay gw = (Jsr173GateWay)xs;
         Locale l = gw._l;
         if (l.noSync()) {
            l.enter();

            Node var3;
            try {
               var3 = nodeFromStreamImpl(gw);
            } finally {
               l.exit();
            }

            return var3;
         } else {
            synchronized(l) {
               l.enter();

               Node var4;
               try {
                  var4 = nodeFromStreamImpl(gw);
               } finally {
                  l.exit();
               }

               return var4;
            }
         }
      }
   }

   public static Node nodeFromStreamImpl(Jsr173GateWay gw) {
      Cur c = gw._xs.getStreamCur();
      return c.isNode() ? (Node)c.getDom() : (Node)null;
   }

   public static XMLStreamReader newXmlStreamReader(Cur c, Object src, int off, int cch) {
      XMLStreamReaderBase xs = new XMLStreamReaderForString(c, src, off, cch);
      return (XMLStreamReader)(c._locale.noSync() ? new UnsyncedJsr173(c._locale, xs) : new SyncedJsr173(c._locale, xs));
   }

   public static XMLStreamReader newXmlStreamReader(Cur c, XmlOptions options) {
      options = XmlOptions.maskNull(options);
      boolean inner = options.hasOption("SAVE_INNER") && !options.hasOption("SAVE_OUTER");
      int k = c.kind();
      Object xs;
      if (k != 0 && k >= 0) {
         if (inner) {
            if (!c.hasAttrs() && !c.hasChildren()) {
               xs = new XMLStreamReaderForString(c, c.getFirstChars(), c._offSrc, c._cchSrc);
            } else {
               assert c.isContainer();

               xs = new XMLStreamReaderForNode(c, true);
            }
         } else {
            xs = new XMLStreamReaderForNode(c, false);
         }
      } else {
         xs = new XMLStreamReaderForString(c, c.getChars(-1), c._offSrc, c._cchSrc);
      }

      return (XMLStreamReader)(c._locale.noSync() ? new UnsyncedJsr173(c._locale, (XMLStreamReaderBase)xs) : new SyncedJsr173(c._locale, (XMLStreamReaderBase)xs));
   }

   private static final class UnsyncedJsr173 extends Jsr173GateWay implements XMLStreamReader, Location, NamespaceContext {
      public UnsyncedJsr173(Locale l, XMLStreamReaderBase xs) {
         super(l, xs);
      }

      public Object getProperty(String name) {
         Object var2;
         try {
            this._l.enter();
            var2 = this._xs.getProperty(name);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public int next() throws XMLStreamException {
         int var1;
         try {
            this._l.enter();
            var1 = this._xs.next();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
         try {
            this._l.enter();
            this._xs.require(type, namespaceURI, localName);
         } finally {
            this._l.exit();
         }

      }

      public String getElementText() throws XMLStreamException {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getElementText();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public int nextTag() throws XMLStreamException {
         int var1;
         try {
            this._l.enter();
            var1 = this._xs.nextTag();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public boolean hasNext() throws XMLStreamException {
         boolean var1;
         try {
            this._l.enter();
            var1 = this._xs.hasNext();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public void close() throws XMLStreamException {
         try {
            this._l.enter();
            this._xs.close();
         } finally {
            this._l.exit();
         }

      }

      public String getNamespaceURI(String prefix) {
         String var2;
         try {
            this._l.enter();
            var2 = this._xs.getNamespaceURI(prefix);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public boolean isStartElement() {
         boolean var1;
         try {
            this._l.enter();
            var1 = this._xs.isStartElement();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public boolean isEndElement() {
         boolean var1;
         try {
            this._l.enter();
            var1 = this._xs.isEndElement();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public boolean isCharacters() {
         boolean var1;
         try {
            this._l.enter();
            var1 = this._xs.isCharacters();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public boolean isWhiteSpace() {
         boolean var1;
         try {
            this._l.enter();
            var1 = this._xs.isWhiteSpace();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getAttributeValue(String namespaceURI, String localName) {
         String var3;
         try {
            this._l.enter();
            var3 = this._xs.getAttributeValue(namespaceURI, localName);
         } finally {
            this._l.exit();
         }

         return var3;
      }

      public int getAttributeCount() {
         int var1;
         try {
            this._l.enter();
            var1 = this._xs.getAttributeCount();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public QName getAttributeName(int index) {
         QName var2;
         try {
            this._l.enter();
            var2 = this._xs.getAttributeName(index);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public String getAttributeNamespace(int index) {
         String var2;
         try {
            this._l.enter();
            var2 = this._xs.getAttributeNamespace(index);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public String getAttributeLocalName(int index) {
         String var2;
         try {
            this._l.enter();
            var2 = this._xs.getAttributeLocalName(index);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public String getAttributePrefix(int index) {
         String var2;
         try {
            this._l.enter();
            var2 = this._xs.getAttributePrefix(index);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public String getAttributeType(int index) {
         String var2;
         try {
            this._l.enter();
            var2 = this._xs.getAttributeType(index);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public String getAttributeValue(int index) {
         String var2;
         try {
            this._l.enter();
            var2 = this._xs.getAttributeValue(index);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public boolean isAttributeSpecified(int index) {
         boolean var2;
         try {
            this._l.enter();
            var2 = this._xs.isAttributeSpecified(index);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public int getNamespaceCount() {
         int var1;
         try {
            this._l.enter();
            var1 = this._xs.getNamespaceCount();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getNamespacePrefix(int index) {
         String var2;
         try {
            this._l.enter();
            var2 = this._xs.getNamespacePrefix(index);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public String getNamespaceURI(int index) {
         String var2;
         try {
            this._l.enter();
            var2 = this._xs.getNamespaceURI(index);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public NamespaceContext getNamespaceContext() {
         return this;
      }

      public int getEventType() {
         int var1;
         try {
            this._l.enter();
            var1 = this._xs.getEventType();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getText() {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getText();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public char[] getTextCharacters() {
         char[] var1;
         try {
            this._l.enter();
            var1 = this._xs.getTextCharacters();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
         int var5;
         try {
            this._l.enter();
            var5 = this._xs.getTextCharacters(sourceStart, target, targetStart, length);
         } finally {
            this._l.exit();
         }

         return var5;
      }

      public int getTextStart() {
         int var1;
         try {
            this._l.enter();
            var1 = this._xs.getTextStart();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public int getTextLength() {
         int var1;
         try {
            this._l.enter();
            var1 = this._xs.getTextLength();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getEncoding() {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getEncoding();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public boolean hasText() {
         boolean var1;
         try {
            this._l.enter();
            var1 = this._xs.hasText();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public Location getLocation() {
         Location var1;
         try {
            this._l.enter();
            var1 = this._xs.getLocation();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public QName getName() {
         QName var1;
         try {
            this._l.enter();
            var1 = this._xs.getName();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getLocalName() {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getLocalName();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public boolean hasName() {
         boolean var1;
         try {
            this._l.enter();
            var1 = this._xs.hasName();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getNamespaceURI() {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getNamespaceURI();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getPrefix() {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getPrefix();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getVersion() {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getVersion();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public boolean isStandalone() {
         boolean var1;
         try {
            this._l.enter();
            var1 = this._xs.isStandalone();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public boolean standaloneSet() {
         boolean var1;
         try {
            this._l.enter();
            var1 = this._xs.standaloneSet();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getCharacterEncodingScheme() {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getCharacterEncodingScheme();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getPITarget() {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getPITarget();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getPIData() {
         String var1;
         try {
            this._l.enter();
            var1 = this._xs.getPIData();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public String getPrefix(String namespaceURI) {
         String var2;
         try {
            this._l.enter();
            var2 = this._xs.getPrefix(namespaceURI);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public Iterator getPrefixes(String namespaceURI) {
         Iterator var2;
         try {
            this._l.enter();
            var2 = this._xs.getPrefixes(namespaceURI);
         } finally {
            this._l.exit();
         }

         return var2;
      }

      public int getCharacterOffset() {
         int var1;
         try {
            this._l.enter();
            var1 = this._xs.getCharacterOffset();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public int getColumnNumber() {
         int var1;
         try {
            this._l.enter();
            var1 = this._xs.getColumnNumber();
         } finally {
            this._l.exit();
         }

         return var1;
      }

      public int getLineNumber() {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.getLineNumber();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getLocationURI() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getLocationURI();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getPublicId() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getPublicId();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getSystemId() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getSystemId();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }
   }

   private static final class SyncedJsr173 extends Jsr173GateWay implements XMLStreamReader, Location, NamespaceContext {
      public SyncedJsr173(Locale l, XMLStreamReaderBase xs) {
         super(l, xs);
      }

      public Object getProperty(String name) {
         synchronized(this._l) {
            this._l.enter();

            Object var3;
            try {
               var3 = this._xs.getProperty(name);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public int next() throws XMLStreamException {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.next();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
         synchronized(this._l) {
            this._l.enter();

            try {
               this._xs.require(type, namespaceURI, localName);
            } finally {
               this._l.exit();
            }

         }
      }

      public String getElementText() throws XMLStreamException {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getElementText();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public int nextTag() throws XMLStreamException {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.nextTag();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public boolean hasNext() throws XMLStreamException {
         synchronized(this._l) {
            this._l.enter();

            boolean var2;
            try {
               var2 = this._xs.hasNext();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public void close() throws XMLStreamException {
         synchronized(this._l) {
            this._l.enter();

            try {
               this._xs.close();
            } finally {
               this._l.exit();
            }

         }
      }

      public String getNamespaceURI(String prefix) {
         synchronized(this._l) {
            this._l.enter();

            String var3;
            try {
               var3 = this._xs.getNamespaceURI(prefix);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public boolean isStartElement() {
         synchronized(this._l) {
            this._l.enter();

            boolean var2;
            try {
               var2 = this._xs.isStartElement();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public boolean isEndElement() {
         synchronized(this._l) {
            this._l.enter();

            boolean var2;
            try {
               var2 = this._xs.isEndElement();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public boolean isCharacters() {
         synchronized(this._l) {
            this._l.enter();

            boolean var2;
            try {
               var2 = this._xs.isCharacters();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public boolean isWhiteSpace() {
         synchronized(this._l) {
            this._l.enter();

            boolean var2;
            try {
               var2 = this._xs.isWhiteSpace();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getAttributeValue(String namespaceURI, String localName) {
         synchronized(this._l) {
            this._l.enter();

            String var4;
            try {
               var4 = this._xs.getAttributeValue(namespaceURI, localName);
            } finally {
               this._l.exit();
            }

            return var4;
         }
      }

      public int getAttributeCount() {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.getAttributeCount();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public QName getAttributeName(int index) {
         synchronized(this._l) {
            this._l.enter();

            QName var3;
            try {
               var3 = this._xs.getAttributeName(index);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public String getAttributeNamespace(int index) {
         synchronized(this._l) {
            this._l.enter();

            String var3;
            try {
               var3 = this._xs.getAttributeNamespace(index);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public String getAttributeLocalName(int index) {
         synchronized(this._l) {
            this._l.enter();

            String var3;
            try {
               var3 = this._xs.getAttributeLocalName(index);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public String getAttributePrefix(int index) {
         synchronized(this._l) {
            this._l.enter();

            String var3;
            try {
               var3 = this._xs.getAttributePrefix(index);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public String getAttributeType(int index) {
         synchronized(this._l) {
            this._l.enter();

            String var3;
            try {
               var3 = this._xs.getAttributeType(index);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public String getAttributeValue(int index) {
         synchronized(this._l) {
            this._l.enter();

            String var3;
            try {
               var3 = this._xs.getAttributeValue(index);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public boolean isAttributeSpecified(int index) {
         synchronized(this._l) {
            this._l.enter();

            boolean var3;
            try {
               var3 = this._xs.isAttributeSpecified(index);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public int getNamespaceCount() {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.getNamespaceCount();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getNamespacePrefix(int index) {
         synchronized(this._l) {
            this._l.enter();

            String var3;
            try {
               var3 = this._xs.getNamespacePrefix(index);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public String getNamespaceURI(int index) {
         synchronized(this._l) {
            this._l.enter();

            String var3;
            try {
               var3 = this._xs.getNamespaceURI(index);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public NamespaceContext getNamespaceContext() {
         return this;
      }

      public int getEventType() {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.getEventType();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getText() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getText();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public char[] getTextCharacters() {
         synchronized(this._l) {
            this._l.enter();

            char[] var2;
            try {
               var2 = this._xs.getTextCharacters();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
         synchronized(this._l) {
            this._l.enter();

            int var6;
            try {
               var6 = this._xs.getTextCharacters(sourceStart, target, targetStart, length);
            } finally {
               this._l.exit();
            }

            return var6;
         }
      }

      public int getTextStart() {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.getTextStart();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public int getTextLength() {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.getTextLength();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getEncoding() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getEncoding();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public boolean hasText() {
         synchronized(this._l) {
            this._l.enter();

            boolean var2;
            try {
               var2 = this._xs.hasText();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public Location getLocation() {
         synchronized(this._l) {
            this._l.enter();

            Location var2;
            try {
               var2 = this._xs.getLocation();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public QName getName() {
         synchronized(this._l) {
            this._l.enter();

            QName var2;
            try {
               var2 = this._xs.getName();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getLocalName() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getLocalName();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public boolean hasName() {
         synchronized(this._l) {
            this._l.enter();

            boolean var2;
            try {
               var2 = this._xs.hasName();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getNamespaceURI() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getNamespaceURI();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getPrefix() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getPrefix();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getVersion() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getVersion();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public boolean isStandalone() {
         synchronized(this._l) {
            this._l.enter();

            boolean var2;
            try {
               var2 = this._xs.isStandalone();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public boolean standaloneSet() {
         synchronized(this._l) {
            this._l.enter();

            boolean var2;
            try {
               var2 = this._xs.standaloneSet();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getCharacterEncodingScheme() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getCharacterEncodingScheme();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getPITarget() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getPITarget();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getPIData() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getPIData();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getPrefix(String namespaceURI) {
         synchronized(this._l) {
            this._l.enter();

            String var3;
            try {
               var3 = this._xs.getPrefix(namespaceURI);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public Iterator getPrefixes(String namespaceURI) {
         synchronized(this._l) {
            this._l.enter();

            Iterator var3;
            try {
               var3 = this._xs.getPrefixes(namespaceURI);
            } finally {
               this._l.exit();
            }

            return var3;
         }
      }

      public int getCharacterOffset() {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.getCharacterOffset();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public int getColumnNumber() {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.getColumnNumber();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public int getLineNumber() {
         synchronized(this._l) {
            this._l.enter();

            int var2;
            try {
               var2 = this._xs.getLineNumber();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getLocationURI() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getLocationURI();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getPublicId() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getPublicId();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }

      public String getSystemId() {
         synchronized(this._l) {
            this._l.enter();

            String var2;
            try {
               var2 = this._xs.getSystemId();
            } finally {
               this._l.exit();
            }

            return var2;
         }
      }
   }

   private abstract static class Jsr173GateWay {
      Locale _l;
      XMLStreamReaderBase _xs;

      public Jsr173GateWay(Locale l, XMLStreamReaderBase xs) {
         this._l = l;
         this._xs = xs;
      }
   }

   private static final class XMLStreamReaderForString extends XMLStreamReaderBase {
      private Cur _cur;
      private Object _src;
      private int _off;
      private int _cch;

      XMLStreamReaderForString(Cur c, Object src, int off, int cch) {
         super(c);
         this._src = src;
         this._off = off;
         this._cch = cch;
         this._cur = c;
      }

      protected Cur getStreamCur() {
         return this._cur;
      }

      public String getText() {
         this.checkChanged();
         return CharUtil.getString(this._src, this._off, this._cch);
      }

      public char[] getTextCharacters() {
         this.checkChanged();
         char[] chars = new char[this._cch];
         CharUtil.getChars(chars, 0, this._src, this._off, this._cch);
         return chars;
      }

      public int getTextStart() {
         this.checkChanged();
         return this._off;
      }

      public int getTextLength() {
         this.checkChanged();
         return this._cch;
      }

      public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) {
         this.checkChanged();
         if (length < 0) {
            throw new IndexOutOfBoundsException();
         } else if (sourceStart > this._cch) {
            throw new IndexOutOfBoundsException();
         } else {
            if (sourceStart + length > this._cch) {
               length = this._cch - sourceStart;
            }

            CharUtil.getChars(target, targetStart, this._src, this._off + sourceStart, length);
            return length;
         }
      }

      public int getEventType() {
         this.checkChanged();
         return 4;
      }

      public boolean hasName() {
         this.checkChanged();
         return false;
      }

      public boolean hasNext() {
         this.checkChanged();
         return false;
      }

      public boolean hasText() {
         this.checkChanged();
         return true;
      }

      public boolean isCharacters() {
         this.checkChanged();
         return true;
      }

      public boolean isEndElement() {
         this.checkChanged();
         return false;
      }

      public boolean isStartElement() {
         this.checkChanged();
         return false;
      }

      public int getAttributeCount() {
         throw new IllegalStateException();
      }

      public String getAttributeLocalName(int index) {
         throw new IllegalStateException();
      }

      public QName getAttributeName(int index) {
         throw new IllegalStateException();
      }

      public String getAttributeNamespace(int index) {
         throw new IllegalStateException();
      }

      public String getAttributePrefix(int index) {
         throw new IllegalStateException();
      }

      public String getAttributeType(int index) {
         throw new IllegalStateException();
      }

      public String getAttributeValue(int index) {
         throw new IllegalStateException();
      }

      public String getAttributeValue(String namespaceURI, String localName) {
         throw new IllegalStateException();
      }

      public String getElementText() {
         throw new IllegalStateException();
      }

      public String getLocalName() {
         throw new IllegalStateException();
      }

      public QName getName() {
         throw new IllegalStateException();
      }

      public int getNamespaceCount() {
         throw new IllegalStateException();
      }

      public String getNamespacePrefix(int index) {
         throw new IllegalStateException();
      }

      public String getNamespaceURI(int index) {
         throw new IllegalStateException();
      }

      public String getNamespaceURI() {
         throw new IllegalStateException();
      }

      public String getPIData() {
         throw new IllegalStateException();
      }

      public String getPITarget() {
         throw new IllegalStateException();
      }

      public String getPrefix() {
         throw new IllegalStateException();
      }

      public boolean isAttributeSpecified(int index) {
         throw new IllegalStateException();
      }

      public int next() {
         throw new IllegalStateException();
      }

      public int nextTag() {
         throw new IllegalStateException();
      }

      public String getPublicId() {
         throw new IllegalStateException();
      }

      public String getSystemId() {
         throw new IllegalStateException();
      }
   }

   private abstract static class XMLStreamReaderBase implements XMLStreamReader, NamespaceContext, Location {
      private Locale _locale;
      private long _version;
      String _uri;
      int _line = -1;
      int _column = -1;
      int _offset = -1;

      XMLStreamReaderBase(Cur c) {
         this._locale = c._locale;
         this._version = this._locale.version();
      }

      protected final void checkChanged() {
         if (this._version != this._locale.version()) {
            throw new ConcurrentModificationException("Document changed while streaming");
         }
      }

      public void close() throws XMLStreamException {
         this.checkChanged();
      }

      public boolean isWhiteSpace() {
         this.checkChanged();
         String s = this.getText();
         return this._locale.getCharUtil().isWhiteSpace(s, 0, s.length());
      }

      public Location getLocation() {
         this.checkChanged();
         Cur c = this.getStreamCur();
         XmlLineNumber ln = (XmlLineNumber)c.getBookmark(XmlLineNumber.class);
         this._uri = null;
         if (ln != null) {
            this._line = ln.getLine();
            this._column = ln.getColumn();
            this._offset = ln.getOffset();
         } else {
            this._line = -1;
            this._column = -1;
            this._offset = -1;
         }

         return this;
      }

      public Object getProperty(String name) {
         this.checkChanged();
         if (name == null) {
            throw new IllegalArgumentException("Property name is null");
         } else {
            return null;
         }
      }

      public String getCharacterEncodingScheme() {
         this.checkChanged();
         Locale var10000 = this._locale;
         XmlDocumentProperties props = Locale.getDocProps(this.getStreamCur(), false);
         return props == null ? null : props.getEncoding();
      }

      public String getEncoding() {
         return null;
      }

      public String getVersion() {
         this.checkChanged();
         Locale var10000 = this._locale;
         XmlDocumentProperties props = Locale.getDocProps(this.getStreamCur(), false);
         return props == null ? null : props.getVersion();
      }

      public boolean isStandalone() {
         this.checkChanged();
         Locale var10000 = this._locale;
         XmlDocumentProperties props = Locale.getDocProps(this.getStreamCur(), false);
         return props == null ? false : props.getStandalone();
      }

      public boolean standaloneSet() {
         this.checkChanged();
         return false;
      }

      public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
         this.checkChanged();
         if (type != this.getEventType()) {
            throw new XMLStreamException();
         } else if (namespaceURI != null && !this.getNamespaceURI().equals(namespaceURI)) {
            throw new XMLStreamException();
         } else if (localName != null && !this.getLocalName().equals(localName)) {
            throw new XMLStreamException();
         }
      }

      public int getCharacterOffset() {
         return this._offset;
      }

      public int getColumnNumber() {
         return this._column;
      }

      public int getLineNumber() {
         return this._line;
      }

      public String getLocationURI() {
         return this._uri;
      }

      public String getPublicId() {
         return null;
      }

      public String getSystemId() {
         return null;
      }

      public NamespaceContext getNamespaceContext() {
         throw new RuntimeException("This version of getNamespaceContext should not be called");
      }

      public String getNamespaceURI(String prefix) {
         this.checkChanged();
         Cur c = this.getStreamCur();
         c.push();
         if (!c.isContainer()) {
            c.toParent();
         }

         String ns = c.namespaceForPrefix(prefix, true);
         c.pop();
         return ns;
      }

      public String getPrefix(String namespaceURI) {
         this.checkChanged();
         Cur c = this.getStreamCur();
         c.push();
         if (!c.isContainer()) {
            c.toParent();
         }

         String prefix = c.prefixForNamespace(namespaceURI, (String)null, false);
         c.pop();
         return prefix;
      }

      public Iterator getPrefixes(String namespaceURI) {
         this.checkChanged();
         HashMap map = new HashMap();
         map.put(namespaceURI, this.getPrefix(namespaceURI));
         return map.values().iterator();
      }

      protected abstract Cur getStreamCur();
   }

   private static final class XMLStreamReaderForNode extends XMLStreamReaderBase {
      private boolean _wholeDoc;
      private boolean _done;
      private Cur _cur;
      private Cur _end;
      private boolean _srcFetched;
      private Object _src;
      private int _offSrc;
      private int _cchSrc;
      private boolean _textFetched;
      private char[] _chars;
      private int _offChars;
      private int _cchChars;

      public XMLStreamReaderForNode(Cur c, boolean inner) {
         super(c);

         assert c.isContainer() || c.isComment() || c.isProcinst() || c.isAttr();

         if (inner) {
            assert c.isContainer();

            this._cur = c.weakCur(this);
            if (!this._cur.toFirstAttr()) {
               this._cur.next();
            }

            this._end = c.weakCur(this);
            this._end.toEnd();
         } else {
            this._cur = c.weakCur(this);
            if (c.isRoot()) {
               this._wholeDoc = true;
            } else {
               this._end = c.weakCur(this);
               if (c.isAttr()) {
                  if (!this._end.toNextAttr()) {
                     this._end.toParent();
                     this._end.next();
                  }
               } else {
                  this._end.skip();
               }
            }
         }

         if (!this._wholeDoc) {
            this._cur.push();

            try {
               this.next();
            } catch (XMLStreamException var4) {
               throw new RuntimeException(var4.getMessage(), var4);
            }

            this._cur.pop();
         }

         assert this._wholeDoc || !this._cur.isSamePos(this._end);

      }

      protected Cur getStreamCur() {
         return this._cur;
      }

      public boolean hasNext() throws XMLStreamException {
         this.checkChanged();
         return !this._done;
      }

      public int getEventType() {
         switch (this._cur.kind()) {
            case -2:
               return 2;
            case -1:
               return 8;
            case 0:
               return 4;
            case 1:
               return 7;
            case 2:
               return 1;
            case 3:
               return this._cur.isXmlns() ? 13 : 10;
            case 4:
               return 5;
            case 5:
               return 3;
            default:
               throw new IllegalStateException();
         }
      }

      public int next() throws XMLStreamException {
         this.checkChanged();
         if (!this.hasNext()) {
            throw new IllegalStateException("No next event in stream");
         } else {
            int kind = this._cur.kind();
            if (kind == -1) {
               assert this._wholeDoc;

               this._done = true;
            } else {
               if (kind == 3) {
                  if (!this._cur.toNextAttr()) {
                     this._cur.toParent();
                     this._cur.next();
                  }
               } else if (kind != 4 && kind != 5) {
                  if (kind == 1) {
                     if (!this._cur.toFirstAttr()) {
                        this._cur.next();
                     }
                  } else {
                     this._cur.next();
                  }
               } else {
                  this._cur.skip();
               }

               assert this._wholeDoc || this._end != null;

               this._done = this._wholeDoc ? this._cur.kind() == -1 : this._cur.isSamePos(this._end);
            }

            this._textFetched = false;
            this._srcFetched = false;
            return this.getEventType();
         }
      }

      public String getText() {
         this.checkChanged();
         int k = this._cur.kind();
         if (k == 4) {
            return this._cur.getValueAsString();
         } else if (k == 0) {
            return this._cur.getCharsAsString(-1);
         } else {
            throw new IllegalStateException();
         }
      }

      public boolean isStartElement() {
         return this.getEventType() == 1;
      }

      public boolean isEndElement() {
         return this.getEventType() == 2;
      }

      public boolean isCharacters() {
         return this.getEventType() == 4;
      }

      public String getElementText() throws XMLStreamException {
         this.checkChanged();
         if (!this.isStartElement()) {
            throw new IllegalStateException();
         } else {
            StringBuffer sb = new StringBuffer();

            while(this.hasNext()) {
               int e = this.next();
               if (e == 2) {
                  return sb.toString();
               }

               if (e == 1) {
                  throw new XMLStreamException();
               }

               if (e != 5 && e != 3) {
                  sb.append(this.getText());
               }
            }

            throw new XMLStreamException();
         }
      }

      public int nextTag() throws XMLStreamException {
         this.checkChanged();

         while(!this.isStartElement() && !this.isEndElement()) {
            if (!this.isWhiteSpace()) {
               throw new XMLStreamException();
            }

            if (!this.hasNext()) {
               throw new XMLStreamException();
            }

            this.next();
         }

         return this.getEventType();
      }

      private static boolean matchAttr(Cur c, String uri, String local) {
         assert c.isNormalAttr();

         QName name = c.getName();
         return name.getLocalPart().equals(local) && (uri == null || name.getNamespaceURI().equals(uri));
      }

      private static Cur toAttr(Cur c, String uri, String local) {
         if (uri != null && local != null && local.length() != 0) {
            Cur ca = c.tempCur();
            boolean match = false;
            if (c.isElem()) {
               if (ca.toFirstAttr()) {
                  do {
                     if (ca.isNormalAttr() && matchAttr(ca, uri, local)) {
                        match = true;
                        break;
                     }
                  } while(ca.toNextSibling());
               }
            } else {
               if (!c.isNormalAttr()) {
                  throw new IllegalStateException();
               }

               match = matchAttr(c, uri, local);
            }

            if (!match) {
               ca.release();
               ca = null;
            }

            return ca;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public String getAttributeValue(String uri, String local) {
         Cur ca = toAttr(this._cur, uri, local);
         String value = null;
         if (ca != null) {
            value = ca.getValueAsString();
            ca.release();
         }

         return value;
      }

      private static Cur toAttr(Cur c, int i) {
         if (i < 0) {
            throw new IndexOutOfBoundsException("Attribute index is negative");
         } else {
            Cur ca = c.tempCur();
            boolean match = false;
            if (c.isElem()) {
               if (ca.toFirstAttr()) {
                  do {
                     if (ca.isNormalAttr() && i-- == 0) {
                        match = true;
                        break;
                     }
                  } while(ca.toNextSibling());
               }
            } else {
               if (!c.isNormalAttr()) {
                  throw new IllegalStateException();
               }

               match = i == 0;
            }

            if (!match) {
               ca.release();
               throw new IndexOutOfBoundsException("Attribute index is too large");
            } else {
               return ca;
            }
         }
      }

      public int getAttributeCount() {
         int n = 0;
         if (this._cur.isElem()) {
            Cur ca = this._cur.tempCur();
            if (ca.toFirstAttr()) {
               do {
                  if (ca.isNormalAttr()) {
                     ++n;
                  }
               } while(ca.toNextSibling());
            }

            ca.release();
         } else {
            if (!this._cur.isNormalAttr()) {
               throw new IllegalStateException();
            }

            ++n;
         }

         return n;
      }

      public QName getAttributeName(int index) {
         Cur ca = toAttr(this._cur, index);
         QName name = ca.getName();
         ca.release();
         return name;
      }

      public String getAttributeNamespace(int index) {
         return this.getAttributeName(index).getNamespaceURI();
      }

      public String getAttributeLocalName(int index) {
         return this.getAttributeName(index).getLocalPart();
      }

      public String getAttributePrefix(int index) {
         return this.getAttributeName(index).getPrefix();
      }

      public String getAttributeType(int index) {
         toAttr(this._cur, index).release();
         return "CDATA";
      }

      public String getAttributeValue(int index) {
         Cur ca = toAttr(this._cur, index);
         String value = null;
         if (ca != null) {
            value = ca.getValueAsString();
            ca.release();
         }

         return value;
      }

      public boolean isAttributeSpecified(int index) {
         Cur ca = toAttr(this._cur, index);
         ca.release();
         return false;
      }

      public int getNamespaceCount() {
         int n = 0;
         if (!this._cur.isElem() && this._cur.kind() != -2) {
            if (!this._cur.isXmlns()) {
               throw new IllegalStateException();
            }

            ++n;
         } else {
            Cur ca = this._cur.tempCur();
            if (this._cur.kind() == -2) {
               ca.toParent();
            }

            if (ca.toFirstAttr()) {
               do {
                  if (ca.isXmlns()) {
                     ++n;
                  }
               } while(ca.toNextSibling());
            }

            ca.release();
         }

         return n;
      }

      private static Cur toXmlns(Cur c, int i) {
         if (i < 0) {
            throw new IndexOutOfBoundsException("Namespace index is negative");
         } else {
            Cur ca = c.tempCur();
            boolean match = false;
            if (!c.isElem() && c.kind() != -2) {
               if (!c.isXmlns()) {
                  throw new IllegalStateException();
               }

               match = i == 0;
            } else {
               if (c.kind() == -2) {
                  ca.toParent();
               }

               if (ca.toFirstAttr()) {
                  do {
                     if (ca.isXmlns() && i-- == 0) {
                        match = true;
                        break;
                     }
                  } while(ca.toNextSibling());
               }
            }

            if (!match) {
               ca.release();
               throw new IndexOutOfBoundsException("Namespace index is too large");
            } else {
               return ca;
            }
         }
      }

      public String getNamespacePrefix(int index) {
         Cur ca = toXmlns(this._cur, index);
         String prefix = ca.getXmlnsPrefix();
         ca.release();
         return prefix;
      }

      public String getNamespaceURI(int index) {
         Cur ca = toXmlns(this._cur, index);
         String uri = ca.getXmlnsUri();
         ca.release();
         return uri;
      }

      private void fetchChars() {
         if (!this._textFetched) {
            int k = this._cur.kind();
            Cur cText = null;
            if (k == 4) {
               cText = this._cur.tempCur();
               cText.next();
            } else {
               if (k != 0) {
                  throw new IllegalStateException();
               }

               cText = this._cur;
            }

            Object src = cText.getChars(-1);
            this.ensureCharBufLen(cText._cchSrc);
            CharUtil.getChars(this._chars, this._offChars = 0, src, cText._offSrc, this._cchChars = cText._cchSrc);
            if (cText != this._cur) {
               cText.release();
            }

            this._textFetched = true;
         }

      }

      private void ensureCharBufLen(int cch) {
         if (this._chars == null || this._chars.length < cch) {
            int l;
            for(l = 256; l < cch; l *= 2) {
            }

            this._chars = new char[l];
         }

      }

      public char[] getTextCharacters() {
         this.checkChanged();
         this.fetchChars();
         return this._chars;
      }

      public int getTextStart() {
         this.checkChanged();
         this.fetchChars();
         return this._offChars;
      }

      public int getTextLength() {
         this.checkChanged();
         this.fetchChars();
         return this._cchChars;
      }

      public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
         if (length < 0) {
            throw new IndexOutOfBoundsException();
         } else if (targetStart >= 0 && targetStart < target.length) {
            if (targetStart + length > target.length) {
               throw new IndexOutOfBoundsException();
            } else {
               if (!this._srcFetched) {
                  int k = this._cur.kind();
                  Cur cText = null;
                  if (k == 4) {
                     cText = this._cur.tempCur();
                     cText.next();
                  } else {
                     if (k != 0) {
                        throw new IllegalStateException();
                     }

                     cText = this._cur;
                  }

                  this._src = cText.getChars(-1);
                  this._offSrc = cText._offSrc;
                  this._cchSrc = cText._cchSrc;
                  if (cText != this._cur) {
                     cText.release();
                  }

                  this._srcFetched = true;
               }

               if (sourceStart > this._cchSrc) {
                  throw new IndexOutOfBoundsException();
               } else {
                  if (sourceStart + length > this._cchSrc) {
                     length = this._cchSrc - sourceStart;
                  }

                  CharUtil.getChars(target, targetStart, this._src, this._offSrc, length);
                  return length;
               }
            }
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public boolean hasText() {
         int k = this._cur.kind();
         return k == 4 || k == 0;
      }

      public boolean hasName() {
         int k = this._cur.kind();
         return k == 2 || k == -2;
      }

      public QName getName() {
         if (!this.hasName()) {
            throw new IllegalStateException();
         } else {
            return this._cur.getName();
         }
      }

      public String getNamespaceURI() {
         return this.getName().getNamespaceURI();
      }

      public String getLocalName() {
         return this.getName().getLocalPart();
      }

      public String getPrefix() {
         return this.getName().getPrefix();
      }

      public String getPITarget() {
         return this._cur.kind() == 5 ? this._cur.getName().getLocalPart() : null;
      }

      public String getPIData() {
         return this._cur.kind() == 5 ? this._cur.getValueAsString() : null;
      }
   }
}
