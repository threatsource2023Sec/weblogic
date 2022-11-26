package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface XMLInputStream {
   XMLEvent next() throws XMLStreamException;

   boolean hasNext() throws XMLStreamException;

   void skip() throws XMLStreamException;

   void skipElement() throws XMLStreamException;

   XMLEvent peek() throws XMLStreamException;

   boolean skip(int var1) throws XMLStreamException;

   boolean skip(XMLName var1) throws XMLStreamException;

   boolean skip(XMLName var1, int var2) throws XMLStreamException;

   XMLInputStream getSubStream() throws XMLStreamException;

   void close() throws XMLStreamException;

   ReferenceResolver getReferenceResolver();

   void setReferenceResolver(ReferenceResolver var1);
}
