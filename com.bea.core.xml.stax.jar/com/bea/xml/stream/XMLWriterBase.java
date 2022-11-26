package com.bea.xml.stream;

import com.bea.xml.stream.util.NamespaceContextImpl;
import com.bea.xml.stream.util.Stack;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class XMLWriterBase extends ReaderToWriter implements XMLStreamWriter {
   protected static final String DEFAULTNS = "";
   private Writer writer;
   private boolean startElementOpened = false;
   private boolean isEmpty = false;
   private ConfigurationContextBase config;
   private CharsetEncoder encoder;
   private Stack localNameStack = new Stack();
   private Stack prefixStack = new Stack();
   protected NamespaceContextImpl context = new NamespaceContextImpl();
   private HashSet needToWrite;
   private boolean isPrefixDefaulting;
   private int defaultPrefixCount = 0;

   public XMLWriterBase() {
   }

   public XMLWriterBase(Writer writer) {
      this.writer = writer;
      this.setWriter(writer);
   }

   public void setWriter(Writer writer) {
      this.writer = writer;
      this.setStreamWriter(this);
      if (writer instanceof OutputStreamWriter) {
         String charsetName = ((OutputStreamWriter)writer).getEncoding();
         this.encoder = Charset.forName(charsetName).newEncoder();
      } else {
         this.encoder = null;
      }

   }

   public void setConfigurationContext(ConfigurationContextBase c) {
      this.config = c;
      this.isPrefixDefaulting = this.config.isPrefixDefaulting();
   }

   protected void write(String s) throws XMLStreamException {
      try {
         this.writer.write(s);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   protected void write(char c) throws XMLStreamException {
      try {
         this.writer.write(c);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   protected void write(char[] c) throws XMLStreamException {
      try {
         this.writer.write(c);
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }

   protected void write(char[] c, int start, int len) throws XMLStreamException {
      try {
         this.writer.write(c, start, len);
      } catch (IOException var5) {
         throw new XMLStreamException(var5);
      }
   }

   protected void writeCharactersInternal(char[] characters, int start, int length, boolean isAttributeValue) throws XMLStreamException {
      if (length != 0) {
         boolean fastPath = true;
         int i = 0;

         for(int len = length; i < len; ++i) {
            switch (characters[i + start]) {
               case '"':
               case '&':
               case '<':
               case '>':
                  fastPath = false;
            }

            if (this.encoder != null && !this.encoder.canEncode(characters[i + start])) {
               fastPath = false;
               break;
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
               if (this.encoder != null && !this.encoder.canEncode(c)) {
                  this.write("&#");
                  this.write(Integer.toString(c));
                  this.write(';');
               } else {
                  this.write(c);
               }
         }
      }

   }

   protected void closeStartElement() throws XMLStreamException {
      if (this.startElementOpened) {
         this.closeStartTag();
         this.startElementOpened = false;
      }

   }

   protected boolean isOpen() {
      return this.startElementOpened;
   }

   protected void closeStartTag() throws XMLStreamException {
      this.flushNamespace();
      if (this.isEmpty) {
         this.write("/>");
         this.isEmpty = false;
      } else {
         this.write(">");
      }

   }

   private void openStartElement() throws XMLStreamException {
      if (this.startElementOpened) {
         this.closeStartTag();
      } else {
         this.startElementOpened = true;
      }

   }

   protected String writeName(String prefix, String namespaceURI, String localName) throws XMLStreamException {
      if (!"".equals(namespaceURI)) {
         prefix = this.getPrefixInternal(namespaceURI);
      }

      if (!"".equals(prefix)) {
         this.write(prefix);
         this.write(":");
      }

      this.write(localName);
      return prefix;
   }

   private String getPrefixInternal(String namespaceURI) {
      String prefix = this.context.getPrefix(namespaceURI);
      return prefix == null ? "" : prefix;
   }

   protected String getURIInternal(String prefix) {
      String uri = this.context.getNamespaceURI(prefix);
      return uri == null ? "" : uri;
   }

   protected void openStartTag() throws XMLStreamException {
      this.write("<");
   }

   private void needToWrite(String uri) {
      if (this.needToWrite == null) {
         this.needToWrite = new HashSet();
      }

      this.needToWrite.add(uri);
   }

   private void prepareNamespace(String uri) throws XMLStreamException {
      if (this.isPrefixDefaulting) {
         if (!"".equals(uri)) {
            String prefix = this.getPrefix(uri);
            if (prefix == null) {
               ++this.defaultPrefixCount;
               prefix = "ns" + this.defaultPrefixCount;
               this.setPrefix(prefix, uri);
            }
         }
      }
   }

   private void removeNamespace(String uri) {
      if (this.isPrefixDefaulting) {
         this.needToWrite.remove(uri);
      }
   }

   private void flushNamespace() throws XMLStreamException {
      if (this.isPrefixDefaulting) {
         Iterator i = this.needToWrite.iterator();

         while(i.hasNext()) {
            String uri = (String)i.next();
            String prefix = this.context.getPrefix(uri);
            if (prefix == null) {
               throw new XMLStreamException("Unable to default prefix with uri:" + uri);
            }

            this.writeNamespace(prefix, uri);
         }

         this.needToWrite.clear();
      }
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      this.context.openScope();
      this.openStartElement();
      this.openStartTag();
      this.prepareNamespace(namespaceURI);
      this.prefixStack.push(this.writeName("", namespaceURI, localName));
      this.localNameStack.push(localName);
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.prepareNamespace(namespaceURI);
      this.context.bindNamespace(prefix, namespaceURI);
      this.writeStartElement(namespaceURI, localName);
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.writeStartElement("", localName);
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      this.openStartElement();
      this.prepareNamespace(namespaceURI);
      this.isEmpty = true;
      this.write("<");
      this.writeName("", namespaceURI, localName);
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.openStartElement();
      this.prepareNamespace(namespaceURI);
      this.isEmpty = true;
      this.write("<");
      this.write(prefix);
      this.write(":");
      this.write(localName);
   }

   public void writeEmptyElement(String localName) throws XMLStreamException {
      this.writeEmptyElement("", localName);
   }

   protected void openEndTag() throws XMLStreamException {
      this.write("</");
   }

   protected void closeEndTag() throws XMLStreamException {
      this.write(">");
   }

   public void writeEndElement() throws XMLStreamException {
      this.closeStartElement();
      String prefix = (String)this.prefixStack.pop();
      String local = (String)this.localNameStack.pop();
      this.openEndTag();
      this.writeName(prefix, "", local);
      this.closeEndTag();
      this.context.closeScope();
   }

   public void writeRaw(String data) throws XMLStreamException {
      this.closeStartElement();
      this.write(data);
   }

   public void close() throws XMLStreamException {
      this.flush();
   }

   public void flush() throws XMLStreamException {
      try {
         this.writer.flush();
      } catch (IOException var2) {
         throw new XMLStreamException(var2);
      }
   }

   public void writeEndDocument() throws XMLStreamException {
      while(!this.localNameStack.isEmpty()) {
         this.writeEndElement();
      }

   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      this.writeAttribute("", localName, value);
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before an attribute");
      } else {
         this.prepareNamespace(namespaceURI);
         this.write(" ");
         this.writeName("", namespaceURI, localName);
         this.write("=\"");
         this.writeCharactersInternal(value.toCharArray(), 0, value.length(), true);
         this.write("\"");
      }
   }

   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before an attribute");
      } else {
         this.prepareNamespace(namespaceURI);
         this.context.bindNamespace(prefix, namespaceURI);
         this.write(" ");
         this.writeName(prefix, namespaceURI, localName);
         this.write("=\"");
         this.writeCharactersInternal(value.toCharArray(), 0, value.length(), true);
         this.write("\"");
      }
   }

   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before a namespace");
      } else if (prefix != null && !"".equals(prefix) && !"xmlns".equals(prefix)) {
         this.write(" xmlns:");
         this.write(prefix);
         this.write("=\"");
         this.write(namespaceURI);
         this.write("\"");
         this.setPrefix(prefix, namespaceURI);
      } else {
         this.writeDefaultNamespace(namespaceURI);
      }
   }

   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before the default namespace");
      } else {
         this.write(" xmlns");
         this.write("=\"");
         this.write(namespaceURI);
         this.write("\"");
         this.setPrefix("", namespaceURI);
      }
   }

   public void writeComment(String data) throws XMLStreamException {
      this.closeStartElement();
      this.write("<!--");
      if (data != null) {
         this.write(data);
      }

      this.write("-->");
   }

   public void writeProcessingInstruction(String target) throws XMLStreamException {
      this.closeStartElement();
      this.writeProcessingInstruction(target, (String)null);
   }

   public void writeProcessingInstruction(String target, String text) throws XMLStreamException {
      this.closeStartElement();
      this.write("<?");
      if (target != null) {
         this.write(target);
      }

      if (text != null) {
         this.write(text);
      }

      this.write("?>");
   }

   public void writeDTD(String dtd) throws XMLStreamException {
      this.write(dtd);
   }

   public void writeCData(String data) throws XMLStreamException {
      this.closeStartElement();
      this.write("<![CDATA[");
      if (data != null) {
         this.write(data);
      }

      this.write("]]>");
   }

   public void writeEntityRef(String name) throws XMLStreamException {
      this.closeStartElement();
      this.write("&");
      this.write(name);
      this.write(";");
   }

   public void writeStartDocument() throws XMLStreamException {
      this.write("<?xml version='1.0' encoding='utf-8'?>");
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      this.write("<?xml version='");
      this.write(version);
      this.write("'?>");
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      this.write("<?xml version='");
      this.write(version);
      this.write("' encoding='");
      this.write(encoding);
      this.write("'?>");
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.closeStartElement();
      this.writeCharactersInternal(text.toCharArray(), 0, text.length(), false);
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.closeStartElement();
      this.writeCharactersInternal(text, start, len, false);
   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.context.getPrefix(uri);
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      this.needToWrite(uri);
      this.context.bindNamespace(prefix, uri);
   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      this.needToWrite(uri);
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

   public Object getProperty(String name) throws IllegalArgumentException {
      return this.config.getProperty(name);
   }

   public static void main(String[] args) throws Exception {
      XMLOutputFactory output = XMLOutputFactoryBase.newInstance();
      output.setProperty("javax.xml.stream.isRepairingNamespaces", new Boolean(true));
      Writer myWriter = new OutputStreamWriter(new FileOutputStream("tmp"), "us-ascii");
      XMLStreamWriter writer2 = output.createXMLStreamWriter(myWriter);
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
      writer2.writeCharacters("bad char coming[");
      char c = 4132;
      char[] array = new char[]{c};
      writer2.writeCharacters(new String(array));
      writer2.writeCharacters("]");
      writer2.writeEndElement();
      writer2.flush();
   }
}
