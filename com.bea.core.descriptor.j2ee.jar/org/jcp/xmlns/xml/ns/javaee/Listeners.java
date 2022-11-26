package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface Listeners extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Listeners.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("listeners5b18type");

   Listener[] getListenerArray();

   Listener getListenerArray(int var1);

   int sizeOfListenerArray();

   void setListenerArray(Listener[] var1);

   void setListenerArray(int var1, Listener var2);

   Listener insertNewListener(int var1);

   Listener addNewListener();

   void removeListener(int var1);

   public static final class Factory {
      public static Listeners newInstance() {
         return (Listeners)XmlBeans.getContextTypeLoader().newInstance(Listeners.type, (XmlOptions)null);
      }

      public static Listeners newInstance(XmlOptions options) {
         return (Listeners)XmlBeans.getContextTypeLoader().newInstance(Listeners.type, options);
      }

      public static Listeners parse(java.lang.String xmlAsString) throws XmlException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(xmlAsString, Listeners.type, (XmlOptions)null);
      }

      public static Listeners parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(xmlAsString, Listeners.type, options);
      }

      public static Listeners parse(File file) throws XmlException, IOException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(file, Listeners.type, (XmlOptions)null);
      }

      public static Listeners parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(file, Listeners.type, options);
      }

      public static Listeners parse(URL u) throws XmlException, IOException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(u, Listeners.type, (XmlOptions)null);
      }

      public static Listeners parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(u, Listeners.type, options);
      }

      public static Listeners parse(InputStream is) throws XmlException, IOException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(is, Listeners.type, (XmlOptions)null);
      }

      public static Listeners parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(is, Listeners.type, options);
      }

      public static Listeners parse(Reader r) throws XmlException, IOException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(r, Listeners.type, (XmlOptions)null);
      }

      public static Listeners parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(r, Listeners.type, options);
      }

      public static Listeners parse(XMLStreamReader sr) throws XmlException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(sr, Listeners.type, (XmlOptions)null);
      }

      public static Listeners parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(sr, Listeners.type, options);
      }

      public static Listeners parse(Node node) throws XmlException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(node, Listeners.type, (XmlOptions)null);
      }

      public static Listeners parse(Node node, XmlOptions options) throws XmlException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(node, Listeners.type, options);
      }

      /** @deprecated */
      public static Listeners parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(xis, Listeners.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Listeners parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Listeners)XmlBeans.getContextTypeLoader().parse(xis, Listeners.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Listeners.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Listeners.type, options);
      }

      private Factory() {
      }
   }
}
