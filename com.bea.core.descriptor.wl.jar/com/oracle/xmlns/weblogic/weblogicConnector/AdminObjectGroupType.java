package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface AdminObjectGroupType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectGroupType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("adminobjectgrouptype1607type");

   String getAdminObjectInterface();

   void setAdminObjectInterface(String var1);

   String addNewAdminObjectInterface();

   String getAdminObjectClass();

   boolean isSetAdminObjectClass();

   void setAdminObjectClass(String var1);

   String addNewAdminObjectClass();

   void unsetAdminObjectClass();

   ConfigPropertiesType getDefaultProperties();

   boolean isSetDefaultProperties();

   void setDefaultProperties(ConfigPropertiesType var1);

   ConfigPropertiesType addNewDefaultProperties();

   void unsetDefaultProperties();

   AdminObjectInstanceType[] getAdminObjectInstanceArray();

   AdminObjectInstanceType getAdminObjectInstanceArray(int var1);

   int sizeOfAdminObjectInstanceArray();

   void setAdminObjectInstanceArray(AdminObjectInstanceType[] var1);

   void setAdminObjectInstanceArray(int var1, AdminObjectInstanceType var2);

   AdminObjectInstanceType insertNewAdminObjectInstance(int var1);

   AdminObjectInstanceType addNewAdminObjectInstance();

   void removeAdminObjectInstance(int var1);

   public static final class Factory {
      public static AdminObjectGroupType newInstance() {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().newInstance(AdminObjectGroupType.type, (XmlOptions)null);
      }

      public static AdminObjectGroupType newInstance(XmlOptions options) {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().newInstance(AdminObjectGroupType.type, options);
      }

      public static AdminObjectGroupType parse(java.lang.String xmlAsString) throws XmlException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectGroupType.type, (XmlOptions)null);
      }

      public static AdminObjectGroupType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectGroupType.type, options);
      }

      public static AdminObjectGroupType parse(File file) throws XmlException, IOException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(file, AdminObjectGroupType.type, (XmlOptions)null);
      }

      public static AdminObjectGroupType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(file, AdminObjectGroupType.type, options);
      }

      public static AdminObjectGroupType parse(URL u) throws XmlException, IOException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(u, AdminObjectGroupType.type, (XmlOptions)null);
      }

      public static AdminObjectGroupType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(u, AdminObjectGroupType.type, options);
      }

      public static AdminObjectGroupType parse(InputStream is) throws XmlException, IOException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(is, AdminObjectGroupType.type, (XmlOptions)null);
      }

      public static AdminObjectGroupType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(is, AdminObjectGroupType.type, options);
      }

      public static AdminObjectGroupType parse(Reader r) throws XmlException, IOException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(r, AdminObjectGroupType.type, (XmlOptions)null);
      }

      public static AdminObjectGroupType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(r, AdminObjectGroupType.type, options);
      }

      public static AdminObjectGroupType parse(XMLStreamReader sr) throws XmlException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectGroupType.type, (XmlOptions)null);
      }

      public static AdminObjectGroupType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectGroupType.type, options);
      }

      public static AdminObjectGroupType parse(Node node) throws XmlException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(node, AdminObjectGroupType.type, (XmlOptions)null);
      }

      public static AdminObjectGroupType parse(Node node, XmlOptions options) throws XmlException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(node, AdminObjectGroupType.type, options);
      }

      /** @deprecated */
      public static AdminObjectGroupType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectGroupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdminObjectGroupType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdminObjectGroupType)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectGroupType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectGroupType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectGroupType.type, options);
      }

      private Factory() {
      }
   }
}
