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

public interface AdminObjectInstanceDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectInstanceDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("adminobjectinstancede7bdoctype");

   AdminObjectInstance getAdminObjectInstance();

   void setAdminObjectInstance(AdminObjectInstance var1);

   AdminObjectInstance addNewAdminObjectInstance();

   public static final class Factory {
      public static AdminObjectInstanceDocument newInstance() {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceDocument newInstance(XmlOptions options) {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().newInstance(AdminObjectInstanceDocument.type, options);
      }

      public static AdminObjectInstanceDocument parse(String xmlAsString) throws XmlException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectInstanceDocument.type, options);
      }

      public static AdminObjectInstanceDocument parse(File file) throws XmlException, IOException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(file, AdminObjectInstanceDocument.type, options);
      }

      public static AdminObjectInstanceDocument parse(URL u) throws XmlException, IOException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(u, AdminObjectInstanceDocument.type, options);
      }

      public static AdminObjectInstanceDocument parse(InputStream is) throws XmlException, IOException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(is, AdminObjectInstanceDocument.type, options);
      }

      public static AdminObjectInstanceDocument parse(Reader r) throws XmlException, IOException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(r, AdminObjectInstanceDocument.type, options);
      }

      public static AdminObjectInstanceDocument parse(XMLStreamReader sr) throws XmlException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectInstanceDocument.type, options);
      }

      public static AdminObjectInstanceDocument parse(Node node) throws XmlException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(node, AdminObjectInstanceDocument.type, options);
      }

      /** @deprecated */
      public static AdminObjectInstanceDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdminObjectInstanceDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdminObjectInstanceDocument)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectInstanceDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectInstanceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectInstanceDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface AdminObjectInstance extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectInstance.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("adminobjectinstanced9caelemtype");

      String getJndiName();

      XmlString xgetJndiName();

      void setJndiName(String var1);

      void xsetJndiName(XmlString var1);

      ConfigPropertiesType getProperties();

      boolean isSetProperties();

      void setProperties(ConfigPropertiesType var1);

      ConfigPropertiesType addNewProperties();

      void unsetProperties();

      public static final class Factory {
         public static AdminObjectInstance newInstance() {
            return (AdminObjectInstance)XmlBeans.getContextTypeLoader().newInstance(AdminObjectInstanceDocument.AdminObjectInstance.type, (XmlOptions)null);
         }

         public static AdminObjectInstance newInstance(XmlOptions options) {
            return (AdminObjectInstance)XmlBeans.getContextTypeLoader().newInstance(AdminObjectInstanceDocument.AdminObjectInstance.type, options);
         }

         private Factory() {
         }
      }
   }
}
