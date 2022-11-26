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

public interface LocalJmxType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocalJmxType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("localjmxtype8299type");

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

   public static final class Factory {
      public static LocalJmxType newInstance() {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().newInstance(LocalJmxType.type, (XmlOptions)null);
      }

      public static LocalJmxType newInstance(XmlOptions options) {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().newInstance(LocalJmxType.type, options);
      }

      public static LocalJmxType parse(String xmlAsString) throws XmlException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalJmxType.type, (XmlOptions)null);
      }

      public static LocalJmxType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalJmxType.type, options);
      }

      public static LocalJmxType parse(File file) throws XmlException, IOException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(file, LocalJmxType.type, (XmlOptions)null);
      }

      public static LocalJmxType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(file, LocalJmxType.type, options);
      }

      public static LocalJmxType parse(URL u) throws XmlException, IOException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(u, LocalJmxType.type, (XmlOptions)null);
      }

      public static LocalJmxType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(u, LocalJmxType.type, options);
      }

      public static LocalJmxType parse(InputStream is) throws XmlException, IOException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(is, LocalJmxType.type, (XmlOptions)null);
      }

      public static LocalJmxType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(is, LocalJmxType.type, options);
      }

      public static LocalJmxType parse(Reader r) throws XmlException, IOException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(r, LocalJmxType.type, (XmlOptions)null);
      }

      public static LocalJmxType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(r, LocalJmxType.type, options);
      }

      public static LocalJmxType parse(XMLStreamReader sr) throws XmlException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(sr, LocalJmxType.type, (XmlOptions)null);
      }

      public static LocalJmxType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(sr, LocalJmxType.type, options);
      }

      public static LocalJmxType parse(Node node) throws XmlException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(node, LocalJmxType.type, (XmlOptions)null);
      }

      public static LocalJmxType parse(Node node, XmlOptions options) throws XmlException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(node, LocalJmxType.type, options);
      }

      /** @deprecated */
      public static LocalJmxType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(xis, LocalJmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocalJmxType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocalJmxType)XmlBeans.getContextTypeLoader().parse(xis, LocalJmxType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalJmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalJmxType.type, options);
      }

      private Factory() {
      }
   }
}
