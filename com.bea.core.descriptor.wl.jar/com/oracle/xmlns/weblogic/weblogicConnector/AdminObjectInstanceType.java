package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.JndiNameType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface AdminObjectInstanceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AdminObjectInstanceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("adminobjectinstancetype0dcbtype");

   JndiNameType getJndiName();

   void setJndiName(JndiNameType var1);

   JndiNameType addNewJndiName();

   ConfigPropertiesType getProperties();

   boolean isSetProperties();

   void setProperties(ConfigPropertiesType var1);

   ConfigPropertiesType addNewProperties();

   void unsetProperties();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AdminObjectInstanceType newInstance() {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().newInstance(AdminObjectInstanceType.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceType newInstance(XmlOptions options) {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().newInstance(AdminObjectInstanceType.type, options);
      }

      public static AdminObjectInstanceType parse(String xmlAsString) throws XmlException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectInstanceType.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AdminObjectInstanceType.type, options);
      }

      public static AdminObjectInstanceType parse(File file) throws XmlException, IOException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(file, AdminObjectInstanceType.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(file, AdminObjectInstanceType.type, options);
      }

      public static AdminObjectInstanceType parse(URL u) throws XmlException, IOException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(u, AdminObjectInstanceType.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(u, AdminObjectInstanceType.type, options);
      }

      public static AdminObjectInstanceType parse(InputStream is) throws XmlException, IOException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(is, AdminObjectInstanceType.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(is, AdminObjectInstanceType.type, options);
      }

      public static AdminObjectInstanceType parse(Reader r) throws XmlException, IOException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(r, AdminObjectInstanceType.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(r, AdminObjectInstanceType.type, options);
      }

      public static AdminObjectInstanceType parse(XMLStreamReader sr) throws XmlException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectInstanceType.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(sr, AdminObjectInstanceType.type, options);
      }

      public static AdminObjectInstanceType parse(Node node) throws XmlException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(node, AdminObjectInstanceType.type, (XmlOptions)null);
      }

      public static AdminObjectInstanceType parse(Node node, XmlOptions options) throws XmlException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(node, AdminObjectInstanceType.type, options);
      }

      /** @deprecated */
      public static AdminObjectInstanceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectInstanceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AdminObjectInstanceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AdminObjectInstanceType)XmlBeans.getContextTypeLoader().parse(xis, AdminObjectInstanceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectInstanceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AdminObjectInstanceType.type, options);
      }

      private Factory() {
      }
   }
}
