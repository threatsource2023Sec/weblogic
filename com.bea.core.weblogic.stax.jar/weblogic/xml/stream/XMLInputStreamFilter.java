package weblogic.xml.stream;

/** @deprecated */
@Deprecated
public interface XMLInputStreamFilter extends XMLInputStream {
   XMLInputStream getParent();

   void setParent(XMLInputStream var1) throws XMLStreamException;
}
