package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface Wls81JmxType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Wls81JmxType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("wls81jmxtype12cdtype");

   String getMBeanServerStrategy();

   XmlString xgetMBeanServerStrategy();

   void setMBeanServerStrategy(String var1);

   void xsetMBeanServerStrategy(XmlString var1);

   boolean getEnableLogMBean();

   XmlBoolean xgetEnableLogMBean();

   void setEnableLogMBean(boolean var1);

   void xsetEnableLogMBean(XmlBoolean var1);

   boolean getEnableRuntimeMBean();

   XmlBoolean xgetEnableRuntimeMBean();

   void setEnableRuntimeMBean(boolean var1);

   void xsetEnableRuntimeMBean(XmlBoolean var1);

   boolean getURL();

   XmlBoolean xgetURL();

   void setURL(boolean var1);

   void xsetURL(XmlBoolean var1);

   boolean getUserName();

   XmlBoolean xgetUserName();

   void setUserName(boolean var1);

   void xsetUserName(XmlBoolean var1);

   boolean getPassword();

   XmlBoolean xgetPassword();

   void setPassword(boolean var1);

   void xsetPassword(XmlBoolean var1);

   public static final class Factory {
      public static Wls81JmxType newInstance() {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().newInstance(Wls81JmxType.type, (XmlOptions)null);
      }

      public static Wls81JmxType newInstance(XmlOptions options) {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().newInstance(Wls81JmxType.type, options);
      }

      public static Wls81JmxType parse(String xmlAsString) throws XmlException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Wls81JmxType.type, (XmlOptions)null);
      }

      public static Wls81JmxType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Wls81JmxType.type, options);
      }

      public static Wls81JmxType parse(File file) throws XmlException, IOException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(file, Wls81JmxType.type, (XmlOptions)null);
      }

      public static Wls81JmxType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(file, Wls81JmxType.type, options);
      }

      public static Wls81JmxType parse(URL u) throws XmlException, IOException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(u, Wls81JmxType.type, (XmlOptions)null);
      }

      public static Wls81JmxType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(u, Wls81JmxType.type, options);
      }

      public static Wls81JmxType parse(InputStream is) throws XmlException, IOException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(is, Wls81JmxType.type, (XmlOptions)null);
      }

      public static Wls81JmxType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(is, Wls81JmxType.type, options);
      }

      public static Wls81JmxType parse(Reader r) throws XmlException, IOException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(r, Wls81JmxType.type, (XmlOptions)null);
      }

      public static Wls81JmxType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(r, Wls81JmxType.type, options);
      }

      public static Wls81JmxType parse(XMLStreamReader sr) throws XmlException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(sr, Wls81JmxType.type, (XmlOptions)null);
      }

      public static Wls81JmxType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(sr, Wls81JmxType.type, options);
      }

      public static Wls81JmxType parse(Node node) throws XmlException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(node, Wls81JmxType.type, (XmlOptions)null);
      }

      public static Wls81JmxType parse(Node node, XmlOptions options) throws XmlException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(node, Wls81JmxType.type, options);
      }

      /** @deprecated */
      public static Wls81JmxType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(xis, Wls81JmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Wls81JmxType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Wls81JmxType)XmlBeans.getContextTypeLoader().parse(xis, Wls81JmxType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Wls81JmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Wls81JmxType.type, options);
      }

      private Factory() {
      }
   }
}
