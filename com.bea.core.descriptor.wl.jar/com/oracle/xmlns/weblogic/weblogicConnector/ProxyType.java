package com.oracle.xmlns.weblogic.weblogicConnector;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ProxyType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProxyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("proxytypec654type");

   XsdNonNegativeIntegerType getInactiveConnectionTimeoutSeconds();

   boolean isSetInactiveConnectionTimeoutSeconds();

   void setInactiveConnectionTimeoutSeconds(XsdNonNegativeIntegerType var1);

   XsdNonNegativeIntegerType addNewInactiveConnectionTimeoutSeconds();

   void unsetInactiveConnectionTimeoutSeconds();

   TrueFalseType getConnectionProfilingEnabled();

   boolean isSetConnectionProfilingEnabled();

   void setConnectionProfilingEnabled(TrueFalseType var1);

   TrueFalseType addNewConnectionProfilingEnabled();

   void unsetConnectionProfilingEnabled();

   String getUseConnectionProxies();

   boolean isSetUseConnectionProxies();

   void setUseConnectionProxies(String var1);

   String addNewUseConnectionProxies();

   void unsetUseConnectionProxies();

   public static final class Factory {
      public static ProxyType newInstance() {
         return (ProxyType)XmlBeans.getContextTypeLoader().newInstance(ProxyType.type, (XmlOptions)null);
      }

      public static ProxyType newInstance(XmlOptions options) {
         return (ProxyType)XmlBeans.getContextTypeLoader().newInstance(ProxyType.type, options);
      }

      public static ProxyType parse(java.lang.String xmlAsString) throws XmlException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProxyType.type, (XmlOptions)null);
      }

      public static ProxyType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProxyType.type, options);
      }

      public static ProxyType parse(File file) throws XmlException, IOException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(file, ProxyType.type, (XmlOptions)null);
      }

      public static ProxyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(file, ProxyType.type, options);
      }

      public static ProxyType parse(URL u) throws XmlException, IOException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(u, ProxyType.type, (XmlOptions)null);
      }

      public static ProxyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(u, ProxyType.type, options);
      }

      public static ProxyType parse(InputStream is) throws XmlException, IOException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(is, ProxyType.type, (XmlOptions)null);
      }

      public static ProxyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(is, ProxyType.type, options);
      }

      public static ProxyType parse(Reader r) throws XmlException, IOException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(r, ProxyType.type, (XmlOptions)null);
      }

      public static ProxyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(r, ProxyType.type, options);
      }

      public static ProxyType parse(XMLStreamReader sr) throws XmlException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(sr, ProxyType.type, (XmlOptions)null);
      }

      public static ProxyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(sr, ProxyType.type, options);
      }

      public static ProxyType parse(Node node) throws XmlException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(node, ProxyType.type, (XmlOptions)null);
      }

      public static ProxyType parse(Node node, XmlOptions options) throws XmlException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(node, ProxyType.type, options);
      }

      /** @deprecated */
      public static ProxyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(xis, ProxyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProxyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProxyType)XmlBeans.getContextTypeLoader().parse(xis, ProxyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProxyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProxyType.type, options);
      }

      private Factory() {
      }
   }
}
