package com.bea.staxb.runtime.internal.util;

import com.bea.staxb.runtime.internal.ClassLoadingUtils;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class PrettyXMLStreamWriter implements XMLStreamWriter {
   private final XMLStreamWriter delegate;
   private final String indentText;
   private int depth;
   private boolean lastTagWasStart;
   private final List charEvents;
   private static final int DEFAULT_INDENT_SIZE = 2;
   private static final String NEWLINE = System.getProperty("line.separator");
   private static Class XMLWRITERBASE_CLASS;
   private static Method GETWRITER_METHOD;
   private Writer underlyingWriter;

   public PrettyXMLStreamWriter(XMLStreamWriter underlying) {
      this(underlying, 2);
   }

   public PrettyXMLStreamWriter(XMLStreamWriter delegate, int indentSize) {
      this.charEvents = new ArrayList();
      this.delegate = delegate;
      this.indentText = this.createIndentText(indentSize);
      if (XMLWRITERBASE_CLASS != null && XMLWRITERBASE_CLASS.isAssignableFrom(delegate.getClass())) {
         try {
            this.underlyingWriter = (Writer)GETWRITER_METHOD.invoke(delegate);
         } catch (Exception var4) {
         }
      }

   }

   public XMLStreamWriter getDelegate() {
      return this.delegate;
   }

   private String createIndentText(int indentSize) {
      char[] spaces = new char[indentSize];
      Arrays.fill(spaces, ' ');
      return new String(spaces);
   }

   public void writeStartElement(String localName) throws XMLStreamException {
      this.preStartElement();
      this.delegate.writeStartElement(localName);
   }

   private void preStartElement() throws XMLStreamException {
      this.flushCharEvents(false);
      if (this.depth > 0) {
         this.indent();
      }

      this.lastTagWasStart = true;
      ++this.depth;
   }

   private void flushCharEvents(boolean write_space) throws XMLStreamException {
      int i = 0;

      for(int len = this.charEvents.size(); i < len; ++i) {
         String s = (String)this.charEvents.get(i);
         if (write_space || !isSpace(s)) {
            this.delegate.writeCharacters(s);
         }
      }

      this.charEvents.clear();
   }

   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
      this.preStartElement();
      this.delegate.writeStartElement(namespaceURI, localName);
   }

   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.preStartElement();
      this.delegate.writeStartElement(prefix, localName, namespaceURI);
   }

   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
      this.preStartElement();
      this.delegate.writeEmptyElement(namespaceURI, localName);
      this.lastTagWasStart = false;
      --this.depth;
   }

   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
      this.preStartElement();
      this.delegate.writeEmptyElement(prefix, localName, namespaceURI);
      this.lastTagWasStart = false;
      --this.depth;
   }

   public void writeEmptyElement(String localName) throws XMLStreamException {
      this.preStartElement();
      this.delegate.writeEmptyElement(localName);
      this.lastTagWasStart = false;
      --this.depth;
   }

   public void writeEndElement() throws XMLStreamException {
      this.flushCharEvents(this.lastTagWasStart);
      --this.depth;
      if (!this.lastTagWasStart) {
         this.indent();
      }

      this.lastTagWasStart = false;
      this.delegate.writeEndElement();
   }

   private void indent() throws XMLStreamException {
      this.newLine();

      for(int i = this.depth; i > 0; --i) {
         this.delegate.writeCharacters(this.indentText);
      }

   }

   public void writeEndDocument() throws XMLStreamException {
      this.newLine();
      this.delegate.writeEndDocument();
   }

   public void close() throws XMLStreamException {
      this.delegate.close();
   }

   public void flush() throws XMLStreamException {
      this.delegate.flush();
   }

   public void writeAttribute(String localName, String value) throws XMLStreamException {
      this.delegate.writeAttribute(localName, value);
   }

   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
      this.delegate.writeAttribute(prefix, namespaceURI, localName, value);
   }

   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
      this.delegate.writeAttribute(namespaceURI, localName, value);
   }

   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
      this.delegate.writeNamespace(prefix, namespaceURI);
   }

   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
      this.delegate.writeDefaultNamespace(namespaceURI);
   }

   public void writeComment(String data) throws XMLStreamException {
      this.indent();
      this.delegate.writeComment(data);
   }

   public void writeProcessingInstruction(String target) throws XMLStreamException {
      this.delegate.writeProcessingInstruction(target);
   }

   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
      this.delegate.writeProcessingInstruction(target, data);
   }

   public void writeCData(String data) throws XMLStreamException {
      this.delegate.writeCData(data);
   }

   public void writeDTD(String dtd) throws XMLStreamException {
      this.delegate.writeDTD(dtd);
   }

   public void writeEntityRef(String name) throws XMLStreamException {
      this.delegate.writeEntityRef(name);
   }

   public void writeStartDocument() throws XMLStreamException {
      this.delegate.writeStartDocument();
      this.newLine();
   }

   public void writeStartDocument(String version) throws XMLStreamException {
      this.delegate.writeStartDocument(version);
      this.newLine();
   }

   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
      this.delegate.writeStartDocument(encoding, version);
      this.newLine();
   }

   private void newLine() throws XMLStreamException {
      if (this.underlyingWriter != null) {
         try {
            this.delegate.writeCharacters("");
            this.underlyingWriter.write(NEWLINE);
         } catch (IOException var2) {
            throw new XMLStreamException(var2);
         }
      } else {
         this.delegate.writeCharacters(NEWLINE);
      }

   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.charEvents.add(text);
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.charEvents.add(new String(text, start, len));
   }

   public String getPrefix(String uri) throws XMLStreamException {
      return this.delegate.getPrefix(uri);
   }

   public void setPrefix(String prefix, String uri) throws XMLStreamException {
      this.delegate.setPrefix(prefix, uri);
   }

   public void setDefaultNamespace(String uri) throws XMLStreamException {
      this.delegate.setDefaultNamespace(uri);
   }

   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
      this.delegate.setNamespaceContext(context);
   }

   public NamespaceContext getNamespaceContext() {
      return this.delegate.getNamespaceContext();
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      return this.delegate.getProperty(name);
   }

   static boolean isSpace(String cs) {
      int i = 0;

      for(int len = cs.length(); i < len; ++i) {
         if (!isSpace(cs.charAt(i))) {
            return false;
         }
      }

      return true;
   }

   static boolean isSpace(char c) {
      return c == ' ' || c == '\t' || c == '\n' || c == '\r';
   }

   static {
      try {
         XMLWRITERBASE_CLASS = ClassLoadingUtils.loadClass("weblogic.xml.stax.XMLWriterBase", (ClassLoader)null);
         GETWRITER_METHOD = XMLWRITERBASE_CLASS.getMethod("getWriter");
      } catch (Exception var1) {
      }

   }
}
