package weblogic.xml.stax;

import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.HashSet;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import weblogic.utils.collections.Stack;
import weblogic.xml.babel.reader.XmlChars;
import weblogic.xml.stax.util.NamespaceContextImpl;

public class XMLWriterBase extends ReaderToWriter implements XMLStreamWriter {
   protected static final String DEFAULTNS = "";
   private Writer writer;
   private boolean startElementOpened = false;
   private boolean isEmpty = false;
   private ConfigurationContextBase config;
   private CharsetEncoder encoder;
   private Stack localNameStack = new Stack();
   private Stack prefixStack = new Stack();
   private Stack uriStack = new Stack();
   protected NamespaceContextImpl context = new NamespaceContextImpl();
   private HashSet needToWrite;
   private boolean isPrefixDefaulting;
   private int defaultPrefixCount = 0;
   private boolean isEscapingCR;

   protected XMLWriterBase() {
   }

   public XMLWriterBase(Writer writer) {
      assert writer != null;

      this.writer = writer;
      this.setStreamWriter(this);
   }

   public void setConfigurationContext(ConfigurationContextBase c) {
      this.config = c;
      this.isPrefixDefaulting = this.config.isPrefixDefaulting();
      if (this.config.getBool("javax.xml.stream.isEscapingCharacters") && this.writer instanceof OutputStreamWriter) {
         String charsetName = ((OutputStreamWriter)this.writer).getEncoding();
         this.encoder = Charset.forName(charsetName).newEncoder();
      } else {
         this.encoder = null;
      }

      this.isEscapingCR = this.config.getBool("weblogic.xml.stream.isEscapingCR");
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

   private final boolean checkForBadChar(char c) {
      switch (c) {
         case '\u0000':
         case '"':
         case '&':
         case '<':
         case '>':
         case '\ufffe':
         case '\uffff':
            return true;
         case '\r':
            return this.isEscapingCR;
         default:
            return this.encoder != null && !this.encoder.canEncode(c) || !XmlChars.isChar(c);
      }
   }

   protected void writeCharactersInternal(String characters, boolean isAttributeValue) throws XMLStreamException {
      boolean fastPath = true;
      int i = 0;

      for(int len = characters.length(); i < len; ++i) {
         if (this.checkForBadChar(characters.charAt(i))) {
            fastPath = false;
            break;
         }
      }

      if (fastPath) {
         this.write(characters);
      } else {
         this.slowWriteCharacters(characters.toCharArray(), 0, characters.length(), isAttributeValue);
      }

   }

   protected void writeCharactersInternal(char[] characters, int start, int length, boolean isAttributeValue) throws XMLStreamException {
      if (length != 0) {
         boolean fastPath = true;
         int i = 0;

         for(int len = length; i < len; ++i) {
            if (this.checkForBadChar(characters[i + start])) {
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
            case '\u0000':
               this.write("&#x0;");
               break;
            case '\t':
               if (isAttributeValue) {
                  this.write("&#x9;");
               } else {
                  this.write('\t');
               }
               break;
            case '\n':
               if (isAttributeValue) {
                  this.write("&#xA;");
               } else {
                  this.write('\n');
               }
               break;
            case '\r':
               if (this.isEscapingCR) {
                  this.write("&#xD;");
               } else {
                  this.write('\r');
               }
               break;
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
            case '\ufffe':
               this.write("&#xFFFE;");
               break;
            case '\uffff':
               this.write("&#xFFFF;");
               break;
            default:
               if (this.encoder != null && !this.encoder.canEncode(c)) {
                  this.write("&#");
                  this.write(Integer.toString(c));
                  this.write(';');
               } else if (!XmlChars.isChar(c)) {
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
         this.context.closeScope(false);
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
      if (!"".equals(namespaceURI) && namespaceURI != null) {
         prefix = this.getPrefixInternal(namespaceURI);
      }

      if (!"".equals(prefix)) {
         this.write(prefix);
         this.write(":");
      }

      this.write(localName);
      return prefix;
   }

   protected String getPrefixInternal(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI may not be null.");
      } else {
         String prefix = this.context.getPrefix(namespaceURI);
         return prefix == null ? "" : prefix;
      }
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

   protected void prepareNamespace(String uri) throws XMLStreamException {
      if (this.isPrefixDefaulting) {
         if (!"".equals(uri)) {
            if (uri != null) {
               String prefix = this.getPrefix(uri);
               if (prefix == null) {
                  ++this.defaultPrefixCount;
                  prefix = "ns" + this.defaultPrefixCount;
                  this.setPrefix(prefix, uri);
               }
            }
         }
      }
   }

   private void removeNamespace(String uri) {
      if (this.isPrefixDefaulting) {
         this.needToWrite.remove(uri);
      }
   }

   protected void flushNamespace() throws XMLStreamException {
      if (this.isPrefixDefaulting) {
         if (this.needToWrite != null) {
            Iterator i = this.needToWrite.iterator();

            while(i.hasNext()) {
               String uri = (String)i.next();
               String prefix = this.context.getPrefix(uri);
               if (prefix == null) {
                  throw new XMLStreamException("Unable to default prefix with uri:" + uri);
               }

               if (uri == null) {
                  throw new XMLStreamException("Attempt to write a null uri.");
               }

               this.writeNamespace(prefix, uri);
            }

            this.needToWrite.clear();
         }
      }
   }

   protected void writeStartElementInternal(String namespaceURI, String localName) throws XMLStreamException {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI may not be null");
      } else if (localName == null) {
         throw new IllegalArgumentException("The local name  may not be null");
      } else {
         this.openStartTag();
         this.prepareNamespace(namespaceURI);
         this.prefixStack.push(this.writeName("", namespaceURI, localName));
         this.localNameStack.push(localName);
         this.uriStack.push(namespaceURI);
      }
   }

   protected void writeStartElementInternal(String prefix, String namespaceURI, String localName) throws XMLStreamException {
      this.writeStartElementInternal(namespaceURI, localName);
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      this.context.openScope();
      this.openStartElement();
      this.writeStartElementInternal(namespaceURI, localName);
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI may not be null");
      } else if (localName == null) {
         throw new IllegalArgumentException("The local name may not be null");
      } else if (prefix == null) {
         throw new IllegalArgumentException("The prefix may not be null");
      } else {
         this.openStartElement();
         this.context.openScope();
         this.prepareNamespace(namespaceURI);
         this.context.bindNamespace(prefix, namespaceURI);
         this.writeStartElementInternal(prefix, namespaceURI, localName);
      }
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.openStartElement();
      this.context.openScope();
      this.writeStartElementInternal("", localName);
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI may not be null");
      } else if (localName == null) {
         throw new IllegalArgumentException("The local name may not be null");
      } else {
         this.context.openScope();
         this.openStartElement();
         this.openStartTag();
         this.prepareNamespace(namespaceURI);
         this.isEmpty = true;
         this.writeName("", namespaceURI, localName);
      }
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI may not be null");
      } else if (localName == null) {
         throw new IllegalArgumentException("The local name may not be null");
      } else if (prefix == null) {
         throw new IllegalArgumentException("The prefix may not be null");
      } else {
         this.context.openScope();
         this.openStartElement();
         this.openStartTag();
         this.prepareNamespace(namespaceURI);
         this.isEmpty = true;
         if (!"".equals(prefix)) {
            this.write(prefix);
            this.write(":");
         }

         this.write(localName);
      }
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

   protected final boolean isEmptyElement() {
      return this.isEmpty;
   }

   public void writeEndElement() throws XMLStreamException {
      this.closeStartElement();
      String prefix = (String)this.prefixStack.pop();
      String local = (String)this.localNameStack.pop();
      this.openEndTag();
      this.writeName(prefix, (String)null, local);
      this.closeEndTag();
      this.context.closeScope(false);
   }

   public void writeRaw(String data) throws XMLStreamException {
      this.closeStartElement();
      this.write(data);
   }

   public void close() throws XMLStreamException {
      this.writeEndDocument();
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
      this.closeStartElement();

      while(!this.localNameStack.isEmpty()) {
         this.writeEndElement();
      }

   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      if (localName == null) {
         throw new IllegalArgumentException("The local name of an attribute may not be null");
      } else if (value == null) {
         throw new IllegalArgumentException("An attribute value may not be null");
      } else {
         this.writeAttribute("", localName, value);
      }
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      if (!this.isOpen()) {
         throw new XMLStreamException("A start element must be written before an attribute");
      } else if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI of an attribute may not be null");
      } else if (localName == null) {
         throw new IllegalArgumentException("The local name of an attribute may not be null");
      } else if (value == null) {
         throw new IllegalArgumentException("An attribute value may not be null");
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
         this.setPrefix(prefix, namespaceURI);
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
      } else if (namespaceURI == null) {
         throw new IllegalArgumentException("The namespace URI of a namespace may not be null");
      } else if (prefix != null && !"".equals(prefix) && !"xmlns".equals(prefix)) {
         this.write(" xmlns:");
         this.write(prefix);
         this.write("=\"");
         this.writeCharactersInternal(namespaceURI.toCharArray(), 0, namespaceURI.length(), true);
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
         this.writeCharactersInternal(namespaceURI.toCharArray(), 0, namespaceURI.length(), true);
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
         this.write(" " + text);
      }

      this.write("?>");
   }

   public void writeDTD(String dtd) throws XMLStreamException {
      if (this.config.getBool("weblogic.xml.stream.isWritingDTD")) {
         this.write(dtd);
      }

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
      if (text == null) {
         throw new IllegalArgumentException("text cannot be null");
      } else {
         this.closeStartElement();
         this.writeCharactersInternal(text, false);
      }
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

   public Object getProperty(String name) {
      return this.config.getProperty(name);
   }

   public Writer getWriter() {
      return this.writer;
   }

   public static void main(String[] args) throws Exception {
      Writer w = new OutputStreamWriter(System.out);
      XMLWriterBase writer = new XMLWriterBase(w);
      writer.writeStartDocument();
      writer.setPrefix("c", "http://c");
      writer.setDefaultNamespace("http://c");
      writer.writeStartElement("http://c", "a");
      writer.writeAttribute("b", "blah");
      writer.writeNamespace("c", "http://c");
      writer.writeDefaultNamespace("http://c");
      writer.setPrefix("d", "http://c");
      writer.writeEmptyElement("http://c", "d");
      writer.writeEmptyElement("", "doodlebug", "http://c");
      writer.writeAttribute("http://c", "chris", "fry");
      writer.writeNamespace("d", "http://c");
      writer.writeCharacters("foo bar foo");
      writer.writeEndElement();
      writer.flush();
      XMLOutputFactory output = XMLOutputFactory.newInstance();
      output.setProperty("javax.xml.stream.isRepairingNamespaces", Boolean.TRUE);
      XMLStreamWriter writer2 = output.createXMLStreamWriter(System.out);
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
      if (args.length == 2) {
         Writer ww = new PrintWriter(System.out);
         XMLWriterBase b = new XMLWriterBase(w);
         XMLStreamReaderBase i = new XMLStreamReaderBase(new FileReader(args[0]));

         while(i.hasNext()) {
            b.write((XMLStreamReader)i);
            i.next();
         }

         b.flush();
         ww.flush();
      }

      StringWriter sw = new StringWriter();
      writer = new XMLWriterBase(sw);
      writer.writeEmptyElement("foo");
      writer.close();
      sw.close();
      System.out.println("DOC=" + sw.getBuffer());
   }

   public boolean isEscapingCR() {
      return this.isEscapingCR;
   }

   public void setEscapingCR(boolean isEscapingCR) {
      this.isEscapingCR = isEscapingCR;
   }
}
