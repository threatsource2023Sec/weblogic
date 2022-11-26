package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface BufferedXMLInputStream extends XMLInputStream {
   void mark() throws XMLStreamException;

   void reset() throws XMLStreamException;
}
