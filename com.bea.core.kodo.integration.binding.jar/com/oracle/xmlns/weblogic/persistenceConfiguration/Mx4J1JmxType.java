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

public interface Mx4J1JmxType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Mx4J1JmxType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("mx4j1jmxtypea934type");

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

   String getHost();

   XmlString xgetHost();

   void setHost(String var1);

   void xsetHost(XmlString var1);

   String getPort();

   XmlString xgetPort();

   void setPort(String var1);

   void xsetPort(XmlString var1);

   String getJNDIName();

   XmlString xgetJNDIName();

   void setJNDIName(String var1);

   void xsetJNDIName(XmlString var1);

   public static final class Factory {
      public static Mx4J1JmxType newInstance() {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().newInstance(Mx4J1JmxType.type, (XmlOptions)null);
      }

      public static Mx4J1JmxType newInstance(XmlOptions options) {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().newInstance(Mx4J1JmxType.type, options);
      }

      public static Mx4J1JmxType parse(String xmlAsString) throws XmlException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Mx4J1JmxType.type, (XmlOptions)null);
      }

      public static Mx4J1JmxType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, Mx4J1JmxType.type, options);
      }

      public static Mx4J1JmxType parse(File file) throws XmlException, IOException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(file, Mx4J1JmxType.type, (XmlOptions)null);
      }

      public static Mx4J1JmxType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(file, Mx4J1JmxType.type, options);
      }

      public static Mx4J1JmxType parse(URL u) throws XmlException, IOException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(u, Mx4J1JmxType.type, (XmlOptions)null);
      }

      public static Mx4J1JmxType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(u, Mx4J1JmxType.type, options);
      }

      public static Mx4J1JmxType parse(InputStream is) throws XmlException, IOException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(is, Mx4J1JmxType.type, (XmlOptions)null);
      }

      public static Mx4J1JmxType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(is, Mx4J1JmxType.type, options);
      }

      public static Mx4J1JmxType parse(Reader r) throws XmlException, IOException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(r, Mx4J1JmxType.type, (XmlOptions)null);
      }

      public static Mx4J1JmxType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(r, Mx4J1JmxType.type, options);
      }

      public static Mx4J1JmxType parse(XMLStreamReader sr) throws XmlException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(sr, Mx4J1JmxType.type, (XmlOptions)null);
      }

      public static Mx4J1JmxType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(sr, Mx4J1JmxType.type, options);
      }

      public static Mx4J1JmxType parse(Node node) throws XmlException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(node, Mx4J1JmxType.type, (XmlOptions)null);
      }

      public static Mx4J1JmxType parse(Node node, XmlOptions options) throws XmlException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(node, Mx4J1JmxType.type, options);
      }

      /** @deprecated */
      public static Mx4J1JmxType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(xis, Mx4J1JmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Mx4J1JmxType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Mx4J1JmxType)XmlBeans.getContextTypeLoader().parse(xis, Mx4J1JmxType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Mx4J1JmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Mx4J1JmxType.type, options);
      }

      private Factory() {
      }
   }
}
