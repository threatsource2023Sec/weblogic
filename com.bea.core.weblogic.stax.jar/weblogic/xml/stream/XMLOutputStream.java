package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface XMLOutputStream {
   void add(XMLEvent var1) throws XMLStreamException;

   void add(XMLInputStream var1) throws XMLStreamException;

   void add(String var1) throws XMLStreamException;

   void add(Attribute var1) throws XMLStreamException;

   void close() throws XMLStreamException;

   void close(boolean var1) throws XMLStreamException;

   void flush() throws XMLStreamException;
}
