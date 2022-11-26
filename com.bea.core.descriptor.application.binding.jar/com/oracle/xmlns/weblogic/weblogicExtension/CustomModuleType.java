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

public interface CustomModuleType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomModuleType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("custommoduletype9b20type");

   String getUri();

   XmlString xgetUri();

   void setUri(String var1);

   void xsetUri(XmlString var1);

   String getProviderName();

   XmlString xgetProviderName();

   void setProviderName(String var1);

   void xsetProviderName(XmlString var1);

   ConfigurationSupportType getConfigurationSupport();

   boolean isSetConfigurationSupport();

   void setConfigurationSupport(ConfigurationSupportType var1);

   ConfigurationSupportType addNewConfigurationSupport();

   void unsetConfigurationSupport();

   public static final class Factory {
      public static CustomModuleType newInstance() {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().newInstance(CustomModuleType.type, (XmlOptions)null);
      }

      public static CustomModuleType newInstance(XmlOptions options) {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().newInstance(CustomModuleType.type, options);
      }

      public static CustomModuleType parse(String xmlAsString) throws XmlException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomModuleType.type, (XmlOptions)null);
      }

      public static CustomModuleType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomModuleType.type, options);
      }

      public static CustomModuleType parse(File file) throws XmlException, IOException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(file, CustomModuleType.type, (XmlOptions)null);
      }

      public static CustomModuleType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(file, CustomModuleType.type, options);
      }

      public static CustomModuleType parse(URL u) throws XmlException, IOException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(u, CustomModuleType.type, (XmlOptions)null);
      }

      public static CustomModuleType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(u, CustomModuleType.type, options);
      }

      public static CustomModuleType parse(InputStream is) throws XmlException, IOException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(is, CustomModuleType.type, (XmlOptions)null);
      }

      public static CustomModuleType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(is, CustomModuleType.type, options);
      }

      public static CustomModuleType parse(Reader r) throws XmlException, IOException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(r, CustomModuleType.type, (XmlOptions)null);
      }

      public static CustomModuleType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(r, CustomModuleType.type, options);
      }

      public static CustomModuleType parse(XMLStreamReader sr) throws XmlException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(sr, CustomModuleType.type, (XmlOptions)null);
      }

      public static CustomModuleType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(sr, CustomModuleType.type, options);
      }

      public static CustomModuleType parse(Node node) throws XmlException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(node, CustomModuleType.type, (XmlOptions)null);
      }

      public static CustomModuleType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(node, CustomModuleType.type, options);
      }

      /** @deprecated */
      public static CustomModuleType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(xis, CustomModuleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomModuleType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomModuleType)XmlBeans.getContextTypeLoader().parse(xis, CustomModuleType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomModuleType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomModuleType.type, options);
      }

      private Factory() {
      }
   }
}
