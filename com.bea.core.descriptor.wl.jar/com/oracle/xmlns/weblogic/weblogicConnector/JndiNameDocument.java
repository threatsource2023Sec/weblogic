package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.JndiNameType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface JndiNameDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JndiNameDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("jndinameaf0bdoctype");

   JndiNameType getJndiName();

   void setJndiName(JndiNameType var1);

   JndiNameType addNewJndiName();

   public static final class Factory {
      public static JndiNameDocument newInstance() {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().newInstance(JndiNameDocument.type, (XmlOptions)null);
      }

      public static JndiNameDocument newInstance(XmlOptions options) {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().newInstance(JndiNameDocument.type, options);
      }

      public static JndiNameDocument parse(String xmlAsString) throws XmlException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, JndiNameDocument.type, (XmlOptions)null);
      }

      public static JndiNameDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, JndiNameDocument.type, options);
      }

      public static JndiNameDocument parse(File file) throws XmlException, IOException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(file, JndiNameDocument.type, (XmlOptions)null);
      }

      public static JndiNameDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(file, JndiNameDocument.type, options);
      }

      public static JndiNameDocument parse(URL u) throws XmlException, IOException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(u, JndiNameDocument.type, (XmlOptions)null);
      }

      public static JndiNameDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(u, JndiNameDocument.type, options);
      }

      public static JndiNameDocument parse(InputStream is) throws XmlException, IOException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(is, JndiNameDocument.type, (XmlOptions)null);
      }

      public static JndiNameDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(is, JndiNameDocument.type, options);
      }

      public static JndiNameDocument parse(Reader r) throws XmlException, IOException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(r, JndiNameDocument.type, (XmlOptions)null);
      }

      public static JndiNameDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(r, JndiNameDocument.type, options);
      }

      public static JndiNameDocument parse(XMLStreamReader sr) throws XmlException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(sr, JndiNameDocument.type, (XmlOptions)null);
      }

      public static JndiNameDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(sr, JndiNameDocument.type, options);
      }

      public static JndiNameDocument parse(Node node) throws XmlException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(node, JndiNameDocument.type, (XmlOptions)null);
      }

      public static JndiNameDocument parse(Node node, XmlOptions options) throws XmlException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(node, JndiNameDocument.type, options);
      }

      /** @deprecated */
      public static JndiNameDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(xis, JndiNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static JndiNameDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (JndiNameDocument)XmlBeans.getContextTypeLoader().parse(xis, JndiNameDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JndiNameDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, JndiNameDocument.type, options);
      }

      private Factory() {
      }
   }
}
