package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface RequiredWorkContextDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RequiredWorkContextDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("requiredworkcontextf67fdoctype");

   String getRequiredWorkContext();

   XmlString xgetRequiredWorkContext();

   void setRequiredWorkContext(String var1);

   void xsetRequiredWorkContext(XmlString var1);

   public static final class Factory {
      public static RequiredWorkContextDocument newInstance() {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().newInstance(RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      public static RequiredWorkContextDocument newInstance(XmlOptions options) {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().newInstance(RequiredWorkContextDocument.type, options);
      }

      public static RequiredWorkContextDocument parse(String xmlAsString) throws XmlException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      public static RequiredWorkContextDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, RequiredWorkContextDocument.type, options);
      }

      public static RequiredWorkContextDocument parse(File file) throws XmlException, IOException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(file, RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      public static RequiredWorkContextDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(file, RequiredWorkContextDocument.type, options);
      }

      public static RequiredWorkContextDocument parse(URL u) throws XmlException, IOException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(u, RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      public static RequiredWorkContextDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(u, RequiredWorkContextDocument.type, options);
      }

      public static RequiredWorkContextDocument parse(InputStream is) throws XmlException, IOException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(is, RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      public static RequiredWorkContextDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(is, RequiredWorkContextDocument.type, options);
      }

      public static RequiredWorkContextDocument parse(Reader r) throws XmlException, IOException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(r, RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      public static RequiredWorkContextDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(r, RequiredWorkContextDocument.type, options);
      }

      public static RequiredWorkContextDocument parse(XMLStreamReader sr) throws XmlException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(sr, RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      public static RequiredWorkContextDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(sr, RequiredWorkContextDocument.type, options);
      }

      public static RequiredWorkContextDocument parse(Node node) throws XmlException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(node, RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      public static RequiredWorkContextDocument parse(Node node, XmlOptions options) throws XmlException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(node, RequiredWorkContextDocument.type, options);
      }

      /** @deprecated */
      public static RequiredWorkContextDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(xis, RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RequiredWorkContextDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RequiredWorkContextDocument)XmlBeans.getContextTypeLoader().parse(xis, RequiredWorkContextDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RequiredWorkContextDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RequiredWorkContextDocument.type, options);
      }

      private Factory() {
      }
   }
}
