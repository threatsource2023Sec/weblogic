package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface JmsClientIdType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JmsClientIdType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("jmsclientidtype61e4type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static JmsClientIdType newInstance() {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().newInstance(JmsClientIdType.type, (XmlOptions)null);
      }

      public static JmsClientIdType newInstance(XmlOptions options) {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().newInstance(JmsClientIdType.type, options);
      }

      public static JmsClientIdType parse(String xmlAsString) throws XmlException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsClientIdType.type, (XmlOptions)null);
      }

      public static JmsClientIdType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmsClientIdType.type, options);
      }

      public static JmsClientIdType parse(File file) throws XmlException, IOException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(file, JmsClientIdType.type, (XmlOptions)null);
      }

      public static JmsClientIdType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(file, JmsClientIdType.type, options);
      }

      public static JmsClientIdType parse(URL u) throws XmlException, IOException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(u, JmsClientIdType.type, (XmlOptions)null);
      }

      public static JmsClientIdType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(u, JmsClientIdType.type, options);
      }

      public static JmsClientIdType parse(InputStream is) throws XmlException, IOException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(is, JmsClientIdType.type, (XmlOptions)null);
      }

      public static JmsClientIdType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(is, JmsClientIdType.type, options);
      }

      public static JmsClientIdType parse(Reader r) throws XmlException, IOException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(r, JmsClientIdType.type, (XmlOptions)null);
      }

      public static JmsClientIdType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(r, JmsClientIdType.type, options);
      }

      public static JmsClientIdType parse(XMLStreamReader sr) throws XmlException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(sr, JmsClientIdType.type, (XmlOptions)null);
      }

      public static JmsClientIdType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(sr, JmsClientIdType.type, options);
      }

      public static JmsClientIdType parse(Node node) throws XmlException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(node, JmsClientIdType.type, (XmlOptions)null);
      }

      public static JmsClientIdType parse(Node node, XmlOptions options) throws XmlException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(node, JmsClientIdType.type, options);
      }

      /** @deprecated */
      public static JmsClientIdType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(xis, JmsClientIdType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JmsClientIdType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JmsClientIdType)XmlBeans.getContextTypeLoader().parse(xis, JmsClientIdType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsClientIdType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmsClientIdType.type, options);
      }

      private Factory() {
      }
   }
}
