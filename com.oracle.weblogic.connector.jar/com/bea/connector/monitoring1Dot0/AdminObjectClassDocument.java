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

public interface AdminObjectClassDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectClassDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("adminobjectclass3746doctype");

   String getAdminObjectClass();

   XmlString xgetAdminObjectClass();

   void setAdminObjectClass(String var1);

   void xsetAdminObjectClass(XmlString var1);

   public static final class Factory {
      public static AdminObjectClassDocument newInstance() {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectClassDocument.type, (XmlOptions)null);
      }

      public static AdminObjectClassDocument newInstance(XmlOptions options) {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectClassDocument.type, options);
      }

      public static AdminObjectClassDocument parse(String xmlAsString) throws XmlException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectClassDocument.type, (XmlOptions)null);
      }

      public static AdminObjectClassDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectClassDocument.type, options);
      }

      public static AdminObjectClassDocument parse(File file) throws XmlException, IOException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectClassDocument.type, (XmlOptions)null);
      }

      public static AdminObjectClassDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectClassDocument.type, options);
      }

      public static AdminObjectClassDocument parse(URL u) throws XmlException, IOException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectClassDocument.type, (XmlOptions)null);
      }

      public static AdminObjectClassDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectClassDocument.type, options);
      }

      public static AdminObjectClassDocument parse(InputStream is) throws XmlException, IOException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectClassDocument.type, (XmlOptions)null);
      }

      public static AdminObjectClassDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectClassDocument.type, options);
      }

      public static AdminObjectClassDocument parse(Reader r) throws XmlException, IOException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectClassDocument.type, (XmlOptions)null);
      }

      public static AdminObjectClassDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectClassDocument.type, options);
      }

      public static AdminObjectClassDocument parse(XMLStreamReader sr) throws XmlException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectClassDocument.type, (XmlOptions)null);
      }

      public static AdminObjectClassDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectClassDocument.type, options);
      }

      public static AdminObjectClassDocument parse(Node node) throws XmlException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectClassDocument.type, (XmlOptions)null);
      }

      public static AdminObjectClassDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectClassDocument.type, options);
      }

      /** @deprecated */
      public static AdminObjectClassDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdminObjectClassDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdminObjectClassDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectClassDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectClassDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectClassDocument.type, options);
      }

      private Factory() {
      }
   }
}
