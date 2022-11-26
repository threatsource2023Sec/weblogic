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

public interface ResourceadapterVersionDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceadapterVersionDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("resourceadapterversiona5f4doctype");

   String getResourceadapterVersion();

   XmlString xgetResourceadapterVersion();

   void setResourceadapterVersion(String var1);

   void xsetResourceadapterVersion(XmlString var1);

   public static final class Factory {
      public static ResourceadapterVersionDocument newInstance() {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().newInstance(ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterVersionDocument newInstance(XmlOptions options) {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().newInstance(ResourceadapterVersionDocument.type, options);
      }

      public static ResourceadapterVersionDocument parse(String xmlAsString) throws XmlException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterVersionDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceadapterVersionDocument.type, options);
      }

      public static ResourceadapterVersionDocument parse(File file) throws XmlException, IOException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(file, ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterVersionDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(file, ResourceadapterVersionDocument.type, options);
      }

      public static ResourceadapterVersionDocument parse(URL u) throws XmlException, IOException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(u, ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterVersionDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(u, ResourceadapterVersionDocument.type, options);
      }

      public static ResourceadapterVersionDocument parse(InputStream is) throws XmlException, IOException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(is, ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterVersionDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(is, ResourceadapterVersionDocument.type, options);
      }

      public static ResourceadapterVersionDocument parse(Reader r) throws XmlException, IOException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(r, ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterVersionDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(r, ResourceadapterVersionDocument.type, options);
      }

      public static ResourceadapterVersionDocument parse(XMLStreamReader sr) throws XmlException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(sr, ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterVersionDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(sr, ResourceadapterVersionDocument.type, options);
      }

      public static ResourceadapterVersionDocument parse(Node node) throws XmlException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(node, ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterVersionDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(node, ResourceadapterVersionDocument.type, options);
      }

      /** @deprecated */
      public static ResourceadapterVersionDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(xis, ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceadapterVersionDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceadapterVersionDocument)XmlBeans.getContextTypeLoader().parse(xis, ResourceadapterVersionDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceadapterVersionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceadapterVersionDocument.type, options);
      }

      private Factory() {
      }
   }
}
