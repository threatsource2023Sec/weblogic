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

public interface AdminObjectInterfaceDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectInterfaceDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("adminobjectinterfacee5a5doctype");

   String getAdminObjectInterface();

   XmlString xgetAdminObjectInterface();

   void setAdminObjectInterface(String var1);

   void xsetAdminObjectInterface(XmlString var1);

   public static final class Factory {
      public static AdminObjectInterfaceDocument newInstance() {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInterfaceDocument newInstance(XmlOptions options) {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectInterfaceDocument.type, options);
      }

      public static AdminObjectInterfaceDocument parse(String xmlAsString) throws XmlException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInterfaceDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectInterfaceDocument.type, options);
      }

      public static AdminObjectInterfaceDocument parse(File file) throws XmlException, IOException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInterfaceDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectInterfaceDocument.type, options);
      }

      public static AdminObjectInterfaceDocument parse(URL u) throws XmlException, IOException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInterfaceDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectInterfaceDocument.type, options);
      }

      public static AdminObjectInterfaceDocument parse(InputStream is) throws XmlException, IOException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInterfaceDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectInterfaceDocument.type, options);
      }

      public static AdminObjectInterfaceDocument parse(Reader r) throws XmlException, IOException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInterfaceDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectInterfaceDocument.type, options);
      }

      public static AdminObjectInterfaceDocument parse(XMLStreamReader sr) throws XmlException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInterfaceDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectInterfaceDocument.type, options);
      }

      public static AdminObjectInterfaceDocument parse(Node node) throws XmlException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInterfaceDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectInterfaceDocument.type, options);
      }

      /** @deprecated */
      public static AdminObjectInterfaceDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdminObjectInterfaceDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdminObjectInterfaceDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectInterfaceDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectInterfaceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectInterfaceDocument.type, options);
      }

      private Factory() {
      }
   }
}
