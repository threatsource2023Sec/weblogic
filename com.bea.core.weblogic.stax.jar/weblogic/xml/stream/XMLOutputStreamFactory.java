package weblogic.xml.stream;

import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.xml.sax.ContentHandler;
import weblogic.xml.babel.stream.XMLOutputStreamFactoryImpl;

/** @deprecated */
@Deprecated
public abstract class XMLOutputStreamFactory {
   public static XMLOutputStreamFactory newInstance() {
      return new XMLOutputStreamFactoryImpl();
   }

   public abstract XMLOutputStream newOutputStream(OutputStream var1) throws XMLStreamException;

   public abstract XMLOutputStream newOutputStream(Writer var1) throws XMLStreamException;

   public abstract XMLOutputStream newOutputStream(Document var1) throws XMLStreamException;

   public abstract XMLOutputStream newOutputStream(Document var1, DocumentFragment var2) throws XMLStreamException;

   public abstract XMLOutputStream newOutputStream(ContentHandler var1) throws XMLStreamException;

   public abstract XMLOutputStream newDebugOutputStream(OutputStream var1) throws XMLStreamException;

   public abstract XMLOutputStream newDebugOutputStream(Writer var1) throws XMLStreamException;

   public abstract XMLInputOutputStream newInputOutputStream() throws XMLStreamException;

   public abstract XMLOutputStream newCanonicalOutputStream(Writer var1) throws XMLStreamException;

   public abstract XMLOutputStream newCanonicalOutputStream(OutputStream var1) throws XMLStreamException;

   public abstract XMLOutputStream newCanonicalOutputStream(Writer var1, Map var2) throws XMLStreamException;

   public abstract XMLOutputStream newCanonicalOutputStream(OutputStream var1, Map var2) throws XMLStreamException;

   public abstract XMLOutputStream newOutputStream(OutputStream var1, boolean var2) throws XMLStreamException;

   public abstract XMLOutputStream newOutputStream(Writer var1, boolean var2) throws XMLStreamException;

   public abstract XMLOutputStream newDebugOutputStream(OutputStream var1, boolean var2) throws XMLStreamException;

   public abstract XMLOutputStream newDebugOutputStream(Writer var1, boolean var2) throws XMLStreamException;
}
