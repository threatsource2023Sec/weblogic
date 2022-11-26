package weblogic.xml.stax;

import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import weblogic.utils.collections.Stack;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.stax.events.StartElementEvent;
import weblogic.xml.stax.util.NamespaceContextImpl;
import weblogic.xml.util.DebugHandler;

public class SAXDriver implements XMLStreamWriter {
   private NamespaceContextImpl context = new NamespaceContextImpl();
   ContentHandler handler;
   private boolean startElementOpened = false;
   private char[] tempArray = new char[32];
   private StartElementEvent startElement = new StartElementEvent();
   private Stack localNameStack = new Stack();
   private Stack prefixStack = new Stack();
   private Stack uriStack = new Stack();
   private AttributesImpl atts = new AttributesImpl();

   public SAXDriver(ContentHandler ch) {
      this.handler = ch;
   }

   protected void write(String s) throws XMLStreamException {
      this.write(s.toCharArray());
   }

   protected void write(char c) throws XMLStreamException {
      this.tempArray[0] = c;
      this.write(this.tempArray, 0, 1);
   }

   protected void write(char[] c) throws XMLStreamException {
      this.write(c, 0, c.length);
   }

   protected void write(char[] c, int start, int len) throws XMLStreamException {
      try {
         this.handler.characters(c, start, len);
      } catch (SAXException var5) {
         throw new XMLStreamException("Error processing XML", var5);
      }
   }

   protected void writeCharactersInternal(char[] characters, int start, int length, boolean isAttributeValue) throws XMLStreamException {
      if (length != 0) {
         boolean fastPath = true;
         int i = 0;
         int len = length;

         while(i < len) {
            switch (characters[i + start]) {
               case '"':
               case '&':
               case '<':
               case '>':
                  fastPath = false;
               default:
                  ++i;
            }
         }

         if (fastPath) {
            this.write(characters, start, length);
         } else {
            this.slowWriteCharacters(characters, start, length, isAttributeValue);
         }

      }
   }

   private void slowWriteCharacters(char[] chars, int start, int length, boolean isAttributeValue) throws XMLStreamException {
      int i = 0;

      for(int len = length; i < len; ++i) {
         char c = chars[i + start];
         switch (c) {
            case '"':
               if (isAttributeValue) {
                  this.write("&quot;");
               } else {
                  this.write('"');
               }
               break;
            case '&':
               this.write("&amp;");
               break;
            case '<':
               this.write("&lt;");
               break;
            case '>':
               this.write("&gt;");
               break;
            default:
               this.write(c);
         }
      }

   }

   private String getQName(String prefix, String localName) {
      return prefix != null && !"".equals(prefix) ? prefix + ":" + localName : localName;
   }

   protected void closeStartElement() throws XMLStreamException {
      try {
         if (this.startElementOpened) {
            Iterator i = this.startElement.getNamespaces();

            while(i.hasNext()) {
               Namespace n = (Namespace)i.next();
               this.handler.startPrefixMapping(n.getPrefix(), n.getNamespaceURI());
            }

            this.atts.clear();
            i = this.startElement.getAttributes();

            while(i.hasNext()) {
               Attribute a = (Attribute)i.next();
               this.atts.addAttribute(a.getName().getNamespaceURI(), a.getName().getLocalPart(), this.getQName(a.getName().getPrefix(), a.getName().getLocalPart()), a.getDTDType(), a.getValue());
            }

            String uri = this.startElement.getName().getNamespaceURI();
            String lname = this.startElement.getName().getLocalPart();
            String prefix = this.startElement.getName().getPrefix();
            String qname = this.getQName(prefix, lname);
            this.handler.startElement(uri, lname, qname, this.atts);
            this.startElementOpened = false;
         }

      } catch (SAXException var6) {
         throw new XMLStreamException("Error processing XML", var6);
      }
   }

   protected boolean isOpen() {
      return this.startElementOpened;
   }

   private void openStartElement() throws XMLStreamException {
      if (this.startElementOpened) {
         this.closeStartElement();
      }

      this.startElement.reset();
      this.startElementOpened = true;
   }

   protected String getPrefixInternal(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI may not be null.");
      } else {
         String prefix = this.context.getPrefix(namespaceURI);
         return prefix == null ? "" : prefix;
      }
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      this.writeStartElement("", localName, namespaceURI);
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.openStartElement();
      this.context.openScope();
      if (prefix == null) {
         prefix = "";
      }

      if (namespaceURI == null) {
         namespaceURI = "";
      }

      if (localName == null) {
         throw new NullPointerException();
      } else {
         this.context.bindNamespace(prefix, namespaceURI);
         this.startElement.setName(new QName(namespaceURI, localName, prefix));
         this.prefixStack.push(prefix);
         this.localNameStack.push(localName);
         this.uriStack.push(namespaceURI);
      }
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.writeStartElement("", localName, "");
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      this.writeStartElement(this.getPrefixInternal(namespaceURI), localName, namespaceURI);
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.writeStartElement(prefix, localName, namespaceURI);
   }

   public void writeEmptyElement(String localName) throws XMLStreamException {
      this.writeStartElement("", localName, "");
   }

   public void writeEndElement() throws XMLStreamException {
      this.closeStartElement();
      String prefix = (String)this.prefixStack.pop();
      String local = (String)this.localNameStack.pop();
      String uri = (String)this.uriStack.pop();
      if (uri == null) {
         uri = "";
      }

      try {
         this.handler.endElement(uri, local, this.getQName(prefix, local));
         List outOfScope = this.context.closeScope();
         int i = 0;

         for(int len = outOfScope.size(); i < len; ++i) {
            PrefixMapping p = (PrefixMapping)outOfScope.get(i);
            this.handler.endPrefixMapping(p.getPrefix());
         }

      } catch (SAXException var8) {
         throw new XMLStreamException("Error processing XML", var8);
      }
   }

   public void close() throws XMLStreamException {
      this.flush();
   }

   public void flush() throws XMLStreamException {
   }

   public void writeEndDocument() throws XMLStreamException {
      while(!this.localNameStack.isEmpty()) {
         this.writeEndElement();
      }

      try {
         this.handler.endDocument();
      } catch (SAXException var2) {
         throw new XMLStreamException("Error processing XML", var2);
      }
   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before a namespace");
      } else {
         this.startElement.addAttribute(new AttributeBase("", localName, value));
      }
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before an attribute");
      } else {
         this.startElement.addAttribute(new AttributeBase("", localName, value));
      }
   }

   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before an attribute");
      } else {
         this.startElement.addAttribute(new AttributeBase(prefix, namespaceURI, localName, value, ""));
      }
   }

   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before a namespace");
      } else {
         if (prefix == null) {
            this.startElement.addNamespace(new NamespaceBase(namespaceURI));
         }

      }
   }

   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before a namespace");
      } else {
         this.startElement.addNamespace(new NamespaceBase(namespaceURI));
      }
   }

   public void writeComment(String data) throws XMLStreamException {
      this.closeStartElement();
   }

   public void writeProcessingInstruction(String target) throws XMLStreamException {
      this.closeStartElement();

      try {
         this.handler.processingInstruction(target, (String)null);
      } catch (SAXException var3) {
         throw new XMLStreamException("Error processing XML", var3);
      }
   }

   public void writeProcessingInstruction(String target, String text) throws XMLStreamException {
      this.closeStartElement();

      try {
         this.handler.processingInstruction(target, text);
      } catch (SAXException var4) {
         throw new XMLStreamException("Error processing XML", var4);
      }
   }

   public void writeDTD(String dtd) throws XMLStreamException {
   }

   public void writeCData(String data) throws XMLStreamException {
      this.write(data);
   }

   public void writeEntityRef(String name) throws XMLStreamException {
      this.write("&" + name + ";");
   }

   public void writeStartDocument() throws XMLStreamException {
      try {
         this.handler.startDocument();
      } catch (SAXException var2) {
         throw new XMLStreamException("Error processing XML", var2);
      }
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      try {
         this.handler.startDocument();
      } catch (SAXException var3) {
         throw new XMLStreamException("Error processing XML", var3);
      }
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      try {
         this.handler.startDocument();
      } catch (SAXException var4) {
         throw new XMLStreamException("Error processing XML", var4);
      }
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.closeStartElement();
      this.write(text);
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.closeStartElement();
      this.writeCharactersInternal(text, start, len, false);
   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.context.getPrefix(uri);
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      this.context.bindNamespace(prefix, uri);
   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      this.context.bindDefaultNameSpace(uri);
   }

   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
      if (context == null) {
         throw new NullPointerException("The namespace  context may not be null.");
      } else {
         this.context = new NamespaceContextImpl(context);
      }
   }

   public NamespaceContext getNamespaceContext() {
      return this.context;
   }

   public Object getProperty(String name) {
      throw new IllegalArgumentException(name + " not supported");
   }

   public static void main(String[] args) throws Exception {
      SAXDriver writer = new SAXDriver(new DebugHandler());
      XMLStreamReaderBase r = new XMLStreamReaderBase(new FileReader(args[0]));
      ReaderToWriter rtow = new ReaderToWriter(writer);

      while(r.hasNext()) {
         rtow.write(r);
         r.next();
      }

      writer.flush();
   }
}
