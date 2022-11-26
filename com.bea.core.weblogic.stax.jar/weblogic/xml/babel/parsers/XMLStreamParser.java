package weblogic.xml.babel.parsers;

import java.io.Reader;
import java.util.Map;
import weblogic.xml.stream.XMLStreamException;

public interface XMLStreamParser {
   void clear();

   void recycleStream(Reader var1) throws XMLStreamException;

   boolean streamParseSome() throws XMLStreamException;

   boolean hasNext() throws XMLStreamException;

   void setFragmentParser(boolean var1);

   void addNamespaceDeclarations(Map var1) throws XMLStreamException;
}
