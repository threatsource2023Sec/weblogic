package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface ConfigPropertyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfigPropertyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("configpropertytype6d26type");

   String getName();

   void setName(String var1);

   String addNewName();

   String getValue();

   boolean isSetValue();

   void setValue(String var1);

   String addNewValue();

   void unsetValue();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ConfigPropertyType newInstance() {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType newInstance(XmlOptions options) {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(java.lang.String xmlAsString) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(File file) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(URL u) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(InputStream is) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(Reader r) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(XMLStreamReader sr) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertyType.type, options);
      }

      public static ConfigPropertyType parse(Node node) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyType.type, (XmlOptions)null);
      }

      public static ConfigPropertyType parse(Node node, XmlOptions options) throws XmlException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertyType.type, options);
      }

      /** @deprecated */
      public static ConfigPropertyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfigPropertyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfigPropertyType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertyType.type, options);
      }

      private Factory() {
      }
   }
}
