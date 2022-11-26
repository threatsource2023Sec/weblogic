package org.apache.xmlbeans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.ref.SoftReference;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Node;

public interface XmlFactoryHook {
   XmlObject newInstance(SchemaTypeLoader var1, SchemaType var2, XmlOptions var3);

   XmlObject parse(SchemaTypeLoader var1, String var2, SchemaType var3, XmlOptions var4) throws XmlException;

   XmlObject parse(SchemaTypeLoader var1, InputStream var2, SchemaType var3, XmlOptions var4) throws XmlException, IOException;

   XmlObject parse(SchemaTypeLoader var1, XMLStreamReader var2, SchemaType var3, XmlOptions var4) throws XmlException;

   XmlObject parse(SchemaTypeLoader var1, Reader var2, SchemaType var3, XmlOptions var4) throws XmlException, IOException;

   XmlObject parse(SchemaTypeLoader var1, Node var2, SchemaType var3, XmlOptions var4) throws XmlException;

   /** @deprecated */
   XmlObject parse(SchemaTypeLoader var1, XMLInputStream var2, SchemaType var3, XmlOptions var4) throws XmlException, XMLStreamException;

   XmlSaxHandler newXmlSaxHandler(SchemaTypeLoader var1, SchemaType var2, XmlOptions var3);

   DOMImplementation newDomImplementation(SchemaTypeLoader var1, XmlOptions var2);

   public static final class ThreadContext {
      private static ThreadLocal threadHook = new ThreadLocal();

      public static XmlFactoryHook getHook() {
         SoftReference softRef = (SoftReference)threadHook.get();
         return softRef == null ? null : (XmlFactoryHook)softRef.get();
      }

      public static void setHook(XmlFactoryHook hook) {
         threadHook.set(new SoftReference(hook));
      }

      private ThreadContext() {
      }
   }
}
