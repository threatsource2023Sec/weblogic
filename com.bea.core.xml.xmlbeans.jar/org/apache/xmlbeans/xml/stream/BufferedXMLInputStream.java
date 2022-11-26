package org.apache.xmlbeans.xml.stream;

/** @deprecated */
public interface BufferedXMLInputStream extends XMLInputStream {
   void mark() throws XMLStreamException;

   void reset() throws XMLStreamException;
}
