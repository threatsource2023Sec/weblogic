package com.oracle.xmlns.weblogic.persistenceConfiguration;

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

public interface JmxDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JmxDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jmx4761doctype");

   JmxType getJmx();

   void setJmx(JmxType var1);

   JmxType addNewJmx();

   public static final class Factory {
      public static JmxDocument newInstance() {
         return (JmxDocument)XmlBeans.getContextTypeLoader().newInstance(JmxDocument.type, (XmlOptions)null);
      }

      public static JmxDocument newInstance(XmlOptions options) {
         return (JmxDocument)XmlBeans.getContextTypeLoader().newInstance(JmxDocument.type, options);
      }

      public static JmxDocument parse(String xmlAsString) throws XmlException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmxDocument.type, (XmlOptions)null);
      }

      public static JmxDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, JmxDocument.type, options);
      }

      public static JmxDocument parse(File file) throws XmlException, IOException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(file, JmxDocument.type, (XmlOptions)null);
      }

      public static JmxDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(file, JmxDocument.type, options);
      }

      public static JmxDocument parse(URL u) throws XmlException, IOException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(u, JmxDocument.type, (XmlOptions)null);
      }

      public static JmxDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(u, JmxDocument.type, options);
      }

      public static JmxDocument parse(InputStream is) throws XmlException, IOException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(is, JmxDocument.type, (XmlOptions)null);
      }

      public static JmxDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(is, JmxDocument.type, options);
      }

      public static JmxDocument parse(Reader r) throws XmlException, IOException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(r, JmxDocument.type, (XmlOptions)null);
      }

      public static JmxDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(r, JmxDocument.type, options);
      }

      public static JmxDocument parse(XMLStreamReader sr) throws XmlException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(sr, JmxDocument.type, (XmlOptions)null);
      }

      public static JmxDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(sr, JmxDocument.type, options);
      }

      public static JmxDocument parse(Node node) throws XmlException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(node, JmxDocument.type, (XmlOptions)null);
      }

      public static JmxDocument parse(Node node, XmlOptions options) throws XmlException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(node, JmxDocument.type, options);
      }

      /** @deprecated */
      public static JmxDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(xis, JmxDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JmxDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JmxDocument)XmlBeans.getContextTypeLoader().parse(xis, JmxDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmxDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JmxDocument.type, options);
      }

      private Factory() {
      }
   }
}
