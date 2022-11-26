package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface AdminObjectsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("adminobjectstype297ctype");

   ConfigPropertiesType getDefaultProperties();

   boolean isSetDefaultProperties();

   void setDefaultProperties(ConfigPropertiesType var1);

   ConfigPropertiesType addNewDefaultProperties();

   void unsetDefaultProperties();

   AdminObjectGroupType[] getAdminObjectGroupArray();

   AdminObjectGroupType getAdminObjectGroupArray(int var1);

   int sizeOfAdminObjectGroupArray();

   void setAdminObjectGroupArray(AdminObjectGroupType[] var1);

   void setAdminObjectGroupArray(int var1, AdminObjectGroupType var2);

   AdminObjectGroupType insertNewAdminObjectGroup(int var1);

   AdminObjectGroupType addNewAdminObjectGroup();

   void removeAdminObjectGroup(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AdminObjectsType newInstance() {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().newInstance(AdminObjectsType.type, (XmlOptions)null);
      }

      public static AdminObjectsType newInstance(XmlOptions options) {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().newInstance(AdminObjectsType.type, options);
      }

      public static AdminObjectsType parse(String xmlAsString) throws XmlException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectsType.type, (XmlOptions)null);
      }

      public static AdminObjectsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectsType.type, options);
      }

      public static AdminObjectsType parse(File file) throws XmlException, IOException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(file, AdminObjectsType.type, (XmlOptions)null);
      }

      public static AdminObjectsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(file, AdminObjectsType.type, options);
      }

      public static AdminObjectsType parse(URL u) throws XmlException, IOException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(u, AdminObjectsType.type, (XmlOptions)null);
      }

      public static AdminObjectsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(u, AdminObjectsType.type, options);
      }

      public static AdminObjectsType parse(InputStream is) throws XmlException, IOException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(is, AdminObjectsType.type, (XmlOptions)null);
      }

      public static AdminObjectsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(is, AdminObjectsType.type, options);
      }

      public static AdminObjectsType parse(Reader r) throws XmlException, IOException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(r, AdminObjectsType.type, (XmlOptions)null);
      }

      public static AdminObjectsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(r, AdminObjectsType.type, options);
      }

      public static AdminObjectsType parse(XMLStreamReader sr) throws XmlException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectsType.type, (XmlOptions)null);
      }

      public static AdminObjectsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectsType.type, options);
      }

      public static AdminObjectsType parse(Node node) throws XmlException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(node, AdminObjectsType.type, (XmlOptions)null);
      }

      public static AdminObjectsType parse(Node node, XmlOptions options) throws XmlException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(node, AdminObjectsType.type, options);
      }

      /** @deprecated */
      public static AdminObjectsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdminObjectsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdminObjectsType)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectsType.type, options);
      }

      private Factory() {
      }
   }
}
