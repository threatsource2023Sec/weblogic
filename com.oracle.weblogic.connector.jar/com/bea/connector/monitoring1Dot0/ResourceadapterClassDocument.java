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

public interface ResourceadapterClassDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceadapterClassDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("resourceadapterclass0a94doctype");

   String getResourceadapterClass();

   XmlString xgetResourceadapterClass();

   void setResourceadapterClass(String var1);

   void xsetResourceadapterClass(XmlString var1);

   public static final class Factory {
      public static ResourceadapterClassDocument newInstance() {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().newInstance(ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterClassDocument newInstance(XmlOptions options) {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().newInstance(ResourceadapterClassDocument.type, options);
      }

      public static ResourceadapterClassDocument parse(String xmlAsString) throws XmlException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterClassDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceadapterClassDocument.type, options);
      }

      public static ResourceadapterClassDocument parse(File file) throws XmlException, IOException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(file, ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterClassDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(file, ResourceadapterClassDocument.type, options);
      }

      public static ResourceadapterClassDocument parse(URL u) throws XmlException, IOException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(u, ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterClassDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(u, ResourceadapterClassDocument.type, options);
      }

      public static ResourceadapterClassDocument parse(InputStream is) throws XmlException, IOException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(is, ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterClassDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(is, ResourceadapterClassDocument.type, options);
      }

      public static ResourceadapterClassDocument parse(Reader r) throws XmlException, IOException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(r, ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterClassDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(r, ResourceadapterClassDocument.type, options);
      }

      public static ResourceadapterClassDocument parse(XMLStreamReader sr) throws XmlException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterClassDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(sr, ResourceadapterClassDocument.type, options);
      }

      public static ResourceadapterClassDocument parse(Node node) throws XmlException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(node, ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      public static ResourceadapterClassDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(node, ResourceadapterClassDocument.type, options);
      }

      /** @deprecated */
      public static ResourceadapterClassDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceadapterClassDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceadapterClassDocument)XmlBeans.getContextTypeLoader().parse(xis, ResourceadapterClassDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceadapterClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceadapterClassDocument.type, options);
      }

      private Factory() {
      }
   }
}
