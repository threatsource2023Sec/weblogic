package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface CustomProxyManagerType extends ProxyManagerType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CustomProxyManagerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("customproxymanagertype5888type");

   String getClassname();

   XmlString xgetClassname();

   boolean isNilClassname();

   boolean isSetClassname();

   void setClassname(String var1);

   void xsetClassname(XmlString var1);

   void setNilClassname();

   void unsetClassname();

   PropertiesType getProperties();

   boolean isNilProperties();

   boolean isSetProperties();

   void setProperties(PropertiesType var1);

   PropertiesType addNewProperties();

   void setNilProperties();

   void unsetProperties();

   public static final class Factory {
      public static CustomProxyManagerType newInstance() {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomProxyManagerType.type, (XmlOptions)null);
      }

      public static CustomProxyManagerType newInstance(XmlOptions options) {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().newInstance(CustomProxyManagerType.type, options);
      }

      public static CustomProxyManagerType parse(String xmlAsString) throws XmlException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomProxyManagerType.type, (XmlOptions)null);
      }

      public static CustomProxyManagerType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CustomProxyManagerType.type, options);
      }

      public static CustomProxyManagerType parse(File file) throws XmlException, IOException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomProxyManagerType.type, (XmlOptions)null);
      }

      public static CustomProxyManagerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(file, CustomProxyManagerType.type, options);
      }

      public static CustomProxyManagerType parse(URL u) throws XmlException, IOException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomProxyManagerType.type, (XmlOptions)null);
      }

      public static CustomProxyManagerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(u, CustomProxyManagerType.type, options);
      }

      public static CustomProxyManagerType parse(InputStream is) throws XmlException, IOException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomProxyManagerType.type, (XmlOptions)null);
      }

      public static CustomProxyManagerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(is, CustomProxyManagerType.type, options);
      }

      public static CustomProxyManagerType parse(Reader r) throws XmlException, IOException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomProxyManagerType.type, (XmlOptions)null);
      }

      public static CustomProxyManagerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(r, CustomProxyManagerType.type, options);
      }

      public static CustomProxyManagerType parse(XMLStreamReader sr) throws XmlException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomProxyManagerType.type, (XmlOptions)null);
      }

      public static CustomProxyManagerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(sr, CustomProxyManagerType.type, options);
      }

      public static CustomProxyManagerType parse(Node node) throws XmlException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomProxyManagerType.type, (XmlOptions)null);
      }

      public static CustomProxyManagerType parse(Node node, XmlOptions options) throws XmlException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(node, CustomProxyManagerType.type, options);
      }

      /** @deprecated */
      public static CustomProxyManagerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomProxyManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CustomProxyManagerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CustomProxyManagerType)XmlBeans.getContextTypeLoader().parse(xis, CustomProxyManagerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomProxyManagerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CustomProxyManagerType.type, options);
      }

      private Factory() {
      }
   }
}
