package com.oracle.xmlns.weblogic.weblogicExtension;

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

public interface ConfigurationSupportType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConfigurationSupportType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("configurationsupporttype31d0type");

   String getBaseRootElement();

   XmlString xgetBaseRootElement();

   void setBaseRootElement(String var1);

   void xsetBaseRootElement(XmlString var1);

   String getConfigRootElement();

   XmlString xgetConfigRootElement();

   boolean isSetConfigRootElement();

   void setConfigRootElement(String var1);

   void xsetConfigRootElement(XmlString var1);

   void unsetConfigRootElement();

   String getBaseNamespace();

   XmlString xgetBaseNamespace();

   void setBaseNamespace(String var1);

   void xsetBaseNamespace(XmlString var1);

   String getConfigNamespace();

   XmlString xgetConfigNamespace();

   boolean isSetConfigNamespace();

   void setConfigNamespace(String var1);

   void xsetConfigNamespace(XmlString var1);

   void unsetConfigNamespace();

   String getBaseUri();

   XmlString xgetBaseUri();

   void setBaseUri(String var1);

   void xsetBaseUri(XmlString var1);

   String getConfigUri();

   XmlString xgetConfigUri();

   boolean isSetConfigUri();

   void setConfigUri(String var1);

   void xsetConfigUri(XmlString var1);

   void unsetConfigUri();

   String getBasePackageName();

   XmlString xgetBasePackageName();

   void setBasePackageName(String var1);

   void xsetBasePackageName(XmlString var1);

   String getConfigPackageName();

   XmlString xgetConfigPackageName();

   boolean isSetConfigPackageName();

   void setConfigPackageName(String var1);

   void xsetConfigPackageName(XmlString var1);

   void unsetConfigPackageName();

   public static final class Factory {
      public static ConfigurationSupportType newInstance() {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().newInstance(ConfigurationSupportType.type, (XmlOptions)null);
      }

      public static ConfigurationSupportType newInstance(XmlOptions options) {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().newInstance(ConfigurationSupportType.type, options);
      }

      public static ConfigurationSupportType parse(String xmlAsString) throws XmlException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigurationSupportType.type, (XmlOptions)null);
      }

      public static ConfigurationSupportType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ConfigurationSupportType.type, options);
      }

      public static ConfigurationSupportType parse(File file) throws XmlException, IOException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(file, ConfigurationSupportType.type, (XmlOptions)null);
      }

      public static ConfigurationSupportType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(file, ConfigurationSupportType.type, options);
      }

      public static ConfigurationSupportType parse(URL u) throws XmlException, IOException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(u, ConfigurationSupportType.type, (XmlOptions)null);
      }

      public static ConfigurationSupportType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(u, ConfigurationSupportType.type, options);
      }

      public static ConfigurationSupportType parse(InputStream is) throws XmlException, IOException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(is, ConfigurationSupportType.type, (XmlOptions)null);
      }

      public static ConfigurationSupportType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(is, ConfigurationSupportType.type, options);
      }

      public static ConfigurationSupportType parse(Reader r) throws XmlException, IOException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(r, ConfigurationSupportType.type, (XmlOptions)null);
      }

      public static ConfigurationSupportType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(r, ConfigurationSupportType.type, options);
      }

      public static ConfigurationSupportType parse(XMLStreamReader sr) throws XmlException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(sr, ConfigurationSupportType.type, (XmlOptions)null);
      }

      public static ConfigurationSupportType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(sr, ConfigurationSupportType.type, options);
      }

      public static ConfigurationSupportType parse(Node node) throws XmlException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(node, ConfigurationSupportType.type, (XmlOptions)null);
      }

      public static ConfigurationSupportType parse(Node node, XmlOptions options) throws XmlException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(node, ConfigurationSupportType.type, options);
      }

      /** @deprecated */
      public static ConfigurationSupportType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(xis, ConfigurationSupportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ConfigurationSupportType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ConfigurationSupportType)XmlBeans.getContextTypeLoader().parse(xis, ConfigurationSupportType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigurationSupportType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ConfigurationSupportType.type, options);
      }

      private Factory() {
      }
   }
}
