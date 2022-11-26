package weblogic.xml.stream;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import weblogic.xml.babel.stream.XMLInputStreamFactoryImpl;
import weblogic.xml.stream.util.XMLPullReader;

/** @deprecated */
@Deprecated
public abstract class XMLInputStreamFactory {
   public static XMLInputStreamFactory newInstance() {
      return new XMLInputStreamFactoryImpl();
   }

   public abstract XMLInputStream newInputStream(File var1) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(InputStream var1) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(Reader var1) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(XMLPullReader var1, InputSource var2) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(Document var1) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(Node var1) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(XMLPullReader var1, InputSource var2, ElementFilter var3) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(Document var1, ElementFilter var2) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(Node var1, ElementFilter var2) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(File var1, ElementFilter var2) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(InputStream var1, ElementFilter var2) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(Reader var1, ElementFilter var2) throws XMLStreamException;

   public abstract XMLInputStream newInputStream(XMLInputStream var1, ElementFilter var2) throws XMLStreamException;

   public abstract BufferedXMLInputStream newBufferedInputStream(XMLInputStream var1) throws XMLStreamException;

   public abstract XMLInputStream newCanonicalInputStream(XMLInputStream var1) throws XMLStreamException;

   public abstract XMLInputStream newDTDAwareInputStream(InputStream var1) throws XMLStreamException;

   public abstract XMLInputStream newDTDAwareInputStream(Reader var1) throws XMLStreamException;

   public abstract XMLInputStream newFragmentInputStream(InputStream var1, Map var2) throws XMLStreamException;

   public abstract XMLInputStream newFragmentInputStream(Reader var1, Map var2) throws XMLStreamException;

   public abstract void setFilter(ElementFilter var1) throws XMLStreamException;
}
