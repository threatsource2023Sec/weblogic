package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface EnableAccessOutsideAppDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EnableAccessOutsideAppDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("enableaccessoutsideappf566doctype");

   boolean getEnableAccessOutsideApp();

   XmlBoolean xgetEnableAccessOutsideApp();

   void setEnableAccessOutsideApp(boolean var1);

   void xsetEnableAccessOutsideApp(XmlBoolean var1);

   public static final class Factory {
      public static EnableAccessOutsideAppDocument newInstance() {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().newInstance(EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      public static EnableAccessOutsideAppDocument newInstance(XmlOptions options) {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().newInstance(EnableAccessOutsideAppDocument.type, options);
      }

      public static EnableAccessOutsideAppDocument parse(String xmlAsString) throws XmlException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      public static EnableAccessOutsideAppDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnableAccessOutsideAppDocument.type, options);
      }

      public static EnableAccessOutsideAppDocument parse(File file) throws XmlException, IOException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(file, EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      public static EnableAccessOutsideAppDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(file, EnableAccessOutsideAppDocument.type, options);
      }

      public static EnableAccessOutsideAppDocument parse(URL u) throws XmlException, IOException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(u, EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      public static EnableAccessOutsideAppDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(u, EnableAccessOutsideAppDocument.type, options);
      }

      public static EnableAccessOutsideAppDocument parse(InputStream is) throws XmlException, IOException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(is, EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      public static EnableAccessOutsideAppDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(is, EnableAccessOutsideAppDocument.type, options);
      }

      public static EnableAccessOutsideAppDocument parse(Reader r) throws XmlException, IOException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(r, EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      public static EnableAccessOutsideAppDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(r, EnableAccessOutsideAppDocument.type, options);
      }

      public static EnableAccessOutsideAppDocument parse(XMLStreamReader sr) throws XmlException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(sr, EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      public static EnableAccessOutsideAppDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(sr, EnableAccessOutsideAppDocument.type, options);
      }

      public static EnableAccessOutsideAppDocument parse(Node node) throws XmlException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(node, EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      public static EnableAccessOutsideAppDocument parse(Node node, XmlOptions options) throws XmlException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(node, EnableAccessOutsideAppDocument.type, options);
      }

      /** @deprecated */
      public static EnableAccessOutsideAppDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(xis, EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EnableAccessOutsideAppDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EnableAccessOutsideAppDocument)XmlBeans.getContextTypeLoader().parse(xis, EnableAccessOutsideAppDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnableAccessOutsideAppDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnableAccessOutsideAppDocument.type, options);
      }

      private Factory() {
      }
   }
}
