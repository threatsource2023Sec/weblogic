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

public interface Jmx2JmxType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Jmx2JmxType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("jmx2jmxtype4d95type");

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

   String getNamingImpl();

   XmlString xgetNamingImpl();

   void setNamingImpl(String var1);

   void xsetNamingImpl(XmlString var1);

   String getServiceURL();

   XmlString xgetServiceURL();

   void setServiceURL(String var1);

   void xsetServiceURL(XmlString var1);

   public static final class Factory {
      public static Jmx2JmxType newInstance() {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().newInstance(Jmx2JmxType.type, (XmlOptions)null);
      }

      public static Jmx2JmxType newInstance(XmlOptions options) {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().newInstance(Jmx2JmxType.type, options);
      }

      public static Jmx2JmxType parse(String xmlAsString) throws XmlException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Jmx2JmxType.type, (XmlOptions)null);
      }

      public static Jmx2JmxType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Jmx2JmxType.type, options);
      }

      public static Jmx2JmxType parse(File file) throws XmlException, IOException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(file, Jmx2JmxType.type, (XmlOptions)null);
      }

      public static Jmx2JmxType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(file, Jmx2JmxType.type, options);
      }

      public static Jmx2JmxType parse(URL u) throws XmlException, IOException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(u, Jmx2JmxType.type, (XmlOptions)null);
      }

      public static Jmx2JmxType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(u, Jmx2JmxType.type, options);
      }

      public static Jmx2JmxType parse(InputStream is) throws XmlException, IOException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(is, Jmx2JmxType.type, (XmlOptions)null);
      }

      public static Jmx2JmxType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(is, Jmx2JmxType.type, options);
      }

      public static Jmx2JmxType parse(Reader r) throws XmlException, IOException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(r, Jmx2JmxType.type, (XmlOptions)null);
      }

      public static Jmx2JmxType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(r, Jmx2JmxType.type, options);
      }

      public static Jmx2JmxType parse(XMLStreamReader sr) throws XmlException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(sr, Jmx2JmxType.type, (XmlOptions)null);
      }

      public static Jmx2JmxType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(sr, Jmx2JmxType.type, options);
      }

      public static Jmx2JmxType parse(Node node) throws XmlException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(node, Jmx2JmxType.type, (XmlOptions)null);
      }

      public static Jmx2JmxType parse(Node node, XmlOptions options) throws XmlException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(node, Jmx2JmxType.type, options);
      }

      /** @deprecated */
      public static Jmx2JmxType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(xis, Jmx2JmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Jmx2JmxType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Jmx2JmxType)XmlBeans.getContextTypeLoader().parse(xis, Jmx2JmxType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Jmx2JmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Jmx2JmxType.type, options);
      }

      private Factory() {
      }
   }
}
