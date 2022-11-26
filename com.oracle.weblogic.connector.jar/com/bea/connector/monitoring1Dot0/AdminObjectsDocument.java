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

public interface AdminObjectsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectsDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("adminobjects376adoctype");

   AdminObjects getAdminObjects();

   void setAdminObjects(AdminObjects var1);

   AdminObjects addNewAdminObjects();

   public static final class Factory {
      public static AdminObjectsDocument newInstance() {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectsDocument.type, (XmlOptions)null);
      }

      public static AdminObjectsDocument newInstance(XmlOptions options) {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectsDocument.type, options);
      }

      public static AdminObjectsDocument parse(String xmlAsString) throws XmlException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectsDocument.type, (XmlOptions)null);
      }

      public static AdminObjectsDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectsDocument.type, options);
      }

      public static AdminObjectsDocument parse(File file) throws XmlException, IOException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectsDocument.type, (XmlOptions)null);
      }

      public static AdminObjectsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectsDocument.type, options);
      }

      public static AdminObjectsDocument parse(URL u) throws XmlException, IOException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectsDocument.type, (XmlOptions)null);
      }

      public static AdminObjectsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectsDocument.type, options);
      }

      public static AdminObjectsDocument parse(InputStream is) throws XmlException, IOException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectsDocument.type, (XmlOptions)null);
      }

      public static AdminObjectsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectsDocument.type, options);
      }

      public static AdminObjectsDocument parse(Reader r) throws XmlException, IOException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectsDocument.type, (XmlOptions)null);
      }

      public static AdminObjectsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectsDocument.type, options);
      }

      public static AdminObjectsDocument parse(XMLStreamReader sr) throws XmlException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectsDocument.type, (XmlOptions)null);
      }

      public static AdminObjectsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectsDocument.type, options);
      }

      public static AdminObjectsDocument parse(Node node) throws XmlException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectsDocument.type, (XmlOptions)null);
      }

      public static AdminObjectsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectsDocument.type, options);
      }

      /** @deprecated */
      public static AdminObjectsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdminObjectsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdminObjectsDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectsDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface AdminObjects extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjects.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("adminobjects5ba8elemtype");

      AdminObjectGroupDocument.AdminObjectGroup[] getAdminObjectGroupArray();

      AdminObjectGroupDocument.AdminObjectGroup getAdminObjectGroupArray(int var1);

      int sizeOfAdminObjectGroupArray();

      void setAdminObjectGroupArray(AdminObjectGroupDocument.AdminObjectGroup[] var1);

      void setAdminObjectGroupArray(int var1, AdminObjectGroupDocument.AdminObjectGroup var2);

      AdminObjectGroupDocument.AdminObjectGroup insertNewAdminObjectGroup(int var1);

      AdminObjectGroupDocument.AdminObjectGroup addNewAdminObjectGroup();

      void removeAdminObjectGroup(int var1);

      public static final class Factory {
         public static AdminObjects newInstance() {
            return (AdminObjects)XmlBeans.getContextTypeLoader().newInstance(AdminObjectsDocument.AdminObjects.type, (XmlOptions)null);
         }

         public static AdminObjects newInstance(XmlOptions options) {
            return (AdminObjects)XmlBeans.getContextTypeLoader().newInstance(AdminObjectsDocument.AdminObjects.type, options);
         }

         private Factory() {
         }
      }
   }
}
