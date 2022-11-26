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

public interface ReauthenticationSupportDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ReauthenticationSupportDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("reauthenticationsupport45e1doctype");

   boolean getReauthenticationSupport();

   XmlBoolean xgetReauthenticationSupport();

   void setReauthenticationSupport(boolean var1);

   void xsetReauthenticationSupport(XmlBoolean var1);

   public static final class Factory {
      public static ReauthenticationSupportDocument newInstance() {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().newInstance(ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      public static ReauthenticationSupportDocument newInstance(XmlOptions options) {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().newInstance(ReauthenticationSupportDocument.type, options);
      }

      public static ReauthenticationSupportDocument parse(String xmlAsString) throws XmlException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      public static ReauthenticationSupportDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, ReauthenticationSupportDocument.type, options);
      }

      public static ReauthenticationSupportDocument parse(File file) throws XmlException, IOException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(file, ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      public static ReauthenticationSupportDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(file, ReauthenticationSupportDocument.type, options);
      }

      public static ReauthenticationSupportDocument parse(URL u) throws XmlException, IOException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(u, ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      public static ReauthenticationSupportDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(u, ReauthenticationSupportDocument.type, options);
      }

      public static ReauthenticationSupportDocument parse(InputStream is) throws XmlException, IOException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(is, ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      public static ReauthenticationSupportDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(is, ReauthenticationSupportDocument.type, options);
      }

      public static ReauthenticationSupportDocument parse(Reader r) throws XmlException, IOException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(r, ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      public static ReauthenticationSupportDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(r, ReauthenticationSupportDocument.type, options);
      }

      public static ReauthenticationSupportDocument parse(XMLStreamReader sr) throws XmlException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(sr, ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      public static ReauthenticationSupportDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(sr, ReauthenticationSupportDocument.type, options);
      }

      public static ReauthenticationSupportDocument parse(Node node) throws XmlException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(node, ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      public static ReauthenticationSupportDocument parse(Node node, XmlOptions options) throws XmlException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(node, ReauthenticationSupportDocument.type, options);
      }

      /** @deprecated */
      public static ReauthenticationSupportDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(xis, ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ReauthenticationSupportDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ReauthenticationSupportDocument)XmlBeans.getContextTypeLoader().parse(xis, ReauthenticationSupportDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReauthenticationSupportDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReauthenticationSupportDocument.type, options);
      }

      private Factory() {
      }
   }
}
