package com.bea.connector.monitoring1Dot0;

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

public interface PoolParamsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PoolParamsDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("poolparams0f89doctype");

   ConnectionPoolParamsType getPoolParams();

   void setPoolParams(ConnectionPoolParamsType var1);

   ConnectionPoolParamsType addNewPoolParams();

   public static final class Factory {
      public static PoolParamsDocument newInstance() {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().newInstance(PoolParamsDocument.type, (XmlOptions)null);
      }

      public static PoolParamsDocument newInstance(XmlOptions options) {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().newInstance(PoolParamsDocument.type, options);
      }

      public static PoolParamsDocument parse(String xmlAsString) throws XmlException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PoolParamsDocument.type, (XmlOptions)null);
      }

      public static PoolParamsDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, PoolParamsDocument.type, options);
      }

      public static PoolParamsDocument parse(File file) throws XmlException, IOException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(file, PoolParamsDocument.type, (XmlOptions)null);
      }

      public static PoolParamsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(file, PoolParamsDocument.type, options);
      }

      public static PoolParamsDocument parse(URL u) throws XmlException, IOException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(u, PoolParamsDocument.type, (XmlOptions)null);
      }

      public static PoolParamsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(u, PoolParamsDocument.type, options);
      }

      public static PoolParamsDocument parse(InputStream is) throws XmlException, IOException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(is, PoolParamsDocument.type, (XmlOptions)null);
      }

      public static PoolParamsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(is, PoolParamsDocument.type, options);
      }

      public static PoolParamsDocument parse(Reader r) throws XmlException, IOException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(r, PoolParamsDocument.type, (XmlOptions)null);
      }

      public static PoolParamsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(r, PoolParamsDocument.type, options);
      }

      public static PoolParamsDocument parse(XMLStreamReader sr) throws XmlException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(sr, PoolParamsDocument.type, (XmlOptions)null);
      }

      public static PoolParamsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(sr, PoolParamsDocument.type, options);
      }

      public static PoolParamsDocument parse(Node node) throws XmlException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(node, PoolParamsDocument.type, (XmlOptions)null);
      }

      public static PoolParamsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(node, PoolParamsDocument.type, options);
      }

      /** @deprecated */
      public static PoolParamsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(xis, PoolParamsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PoolParamsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PoolParamsDocument)XmlBeans.getContextTypeLoader().parse(xis, PoolParamsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PoolParamsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PoolParamsDocument.type, options);
      }

      private Factory() {
      }
   }
}
