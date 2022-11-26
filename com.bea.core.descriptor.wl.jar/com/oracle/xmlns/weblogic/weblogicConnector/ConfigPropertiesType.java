package com.oracle.xmlns.weblogic.weblogicConnector;

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

public interface ConfigPropertiesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfigPropertiesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("configpropertiestype4a04type");

   ConfigPropertyType[] getPropertyArray();

   ConfigPropertyType getPropertyArray(int var1);

   int sizeOfPropertyArray();

   void setPropertyArray(ConfigPropertyType[] var1);

   void setPropertyArray(int var1, ConfigPropertyType var2);

   ConfigPropertyType insertNewProperty(int var1);

   ConfigPropertyType addNewProperty();

   void removeProperty(int var1);

   public static final class Factory {
      public static ConfigPropertiesType newInstance() {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertiesType.type, (XmlOptions)null);
      }

      public static ConfigPropertiesType newInstance(XmlOptions options) {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().newInstance(ConfigPropertiesType.type, options);
      }

      public static ConfigPropertiesType parse(String xmlAsString) throws XmlException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertiesType.type, (XmlOptions)null);
      }

      public static ConfigPropertiesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigPropertiesType.type, options);
      }

      public static ConfigPropertiesType parse(File file) throws XmlException, IOException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertiesType.type, (XmlOptions)null);
      }

      public static ConfigPropertiesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(file, ConfigPropertiesType.type, options);
      }

      public static ConfigPropertiesType parse(URL u) throws XmlException, IOException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertiesType.type, (XmlOptions)null);
      }

      public static ConfigPropertiesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(u, ConfigPropertiesType.type, options);
      }

      public static ConfigPropertiesType parse(InputStream is) throws XmlException, IOException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertiesType.type, (XmlOptions)null);
      }

      public static ConfigPropertiesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(is, ConfigPropertiesType.type, options);
      }

      public static ConfigPropertiesType parse(Reader r) throws XmlException, IOException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertiesType.type, (XmlOptions)null);
      }

      public static ConfigPropertiesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(r, ConfigPropertiesType.type, options);
      }

      public static ConfigPropertiesType parse(XMLStreamReader sr) throws XmlException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertiesType.type, (XmlOptions)null);
      }

      public static ConfigPropertiesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(sr, ConfigPropertiesType.type, options);
      }

      public static ConfigPropertiesType parse(Node node) throws XmlException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertiesType.type, (XmlOptions)null);
      }

      public static ConfigPropertiesType parse(Node node, XmlOptions options) throws XmlException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(node, ConfigPropertiesType.type, options);
      }

      /** @deprecated */
      public static ConfigPropertiesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertiesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfigPropertiesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfigPropertiesType)XmlBeans.getContextTypeLoader().parse(xis, ConfigPropertiesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertiesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigPropertiesType.type, options);
      }

      private Factory() {
      }
   }
}
