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

public interface AdminObjectGroupDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectGroupDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("adminobjectgroupe99fdoctype");

   AdminObjectGroup getAdminObjectGroup();

   void setAdminObjectGroup(AdminObjectGroup var1);

   AdminObjectGroup addNewAdminObjectGroup();

   public static final class Factory {
      public static AdminObjectGroupDocument newInstance() {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      public static AdminObjectGroupDocument newInstance(XmlOptions options) {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectGroupDocument.type, options);
      }

      public static AdminObjectGroupDocument parse(String xmlAsString) throws XmlException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      public static AdminObjectGroupDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectGroupDocument.type, options);
      }

      public static AdminObjectGroupDocument parse(File file) throws XmlException, IOException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      public static AdminObjectGroupDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectGroupDocument.type, options);
      }

      public static AdminObjectGroupDocument parse(URL u) throws XmlException, IOException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      public static AdminObjectGroupDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectGroupDocument.type, options);
      }

      public static AdminObjectGroupDocument parse(InputStream is) throws XmlException, IOException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      public static AdminObjectGroupDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectGroupDocument.type, options);
      }

      public static AdminObjectGroupDocument parse(Reader r) throws XmlException, IOException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      public static AdminObjectGroupDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectGroupDocument.type, options);
      }

      public static AdminObjectGroupDocument parse(XMLStreamReader sr) throws XmlException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      public static AdminObjectGroupDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectGroupDocument.type, options);
      }

      public static AdminObjectGroupDocument parse(Node node) throws XmlException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      public static AdminObjectGroupDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectGroupDocument.type, options);
      }

      /** @deprecated */
      public static AdminObjectGroupDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdminObjectGroupDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdminObjectGroupDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectGroupDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectGroupDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectGroupDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface AdminObjectGroup extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectGroup.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("adminobjectgroupd9aaelemtype");

      String getAdminObjectInterface();

      XmlString xgetAdminObjectInterface();

      void setAdminObjectInterface(String var1);

      void xsetAdminObjectInterface(XmlString var1);

      String getAdminObjectClass();

      XmlString xgetAdminObjectClass();

      void setAdminObjectClass(String var1);

      void xsetAdminObjectClass(XmlString var1);

      AdminObjectInstanceDocument.AdminObjectInstance[] getAdminObjectInstanceArray();

      AdminObjectInstanceDocument.AdminObjectInstance getAdminObjectInstanceArray(int var1);

      int sizeOfAdminObjectInstanceArray();

      void setAdminObjectInstanceArray(AdminObjectInstanceDocument.AdminObjectInstance[] var1);

      void setAdminObjectInstanceArray(int var1, AdminObjectInstanceDocument.AdminObjectInstance var2);

      AdminObjectInstanceDocument.AdminObjectInstance insertNewAdminObjectInstance(int var1);

      AdminObjectInstanceDocument.AdminObjectInstance addNewAdminObjectInstance();

      void removeAdminObjectInstance(int var1);

      public static final class Factory {
         public static AdminObjectGroup newInstance() {
            return (AdminObjectGroup)XmlBeans.getContextTypeLoader().newInstance(AdminObjectGroupDocument.AdminObjectGroup.type, (XmlOptions)null);
         }

         public static AdminObjectGroup newInstance(XmlOptions options) {
            return (AdminObjectGroup)XmlBeans.getContextTypeLoader().newInstance(AdminObjectGroupDocument.AdminObjectGroup.type, options);
         }

         private Factory() {
         }
      }
   }
}
