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

public interface GuiJmxType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GuiJmxType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("guijmxtype8509type");

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
      public static GuiJmxType newInstance() {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().newInstance(GuiJmxType.type, (XmlOptions)null);
      }

      public static GuiJmxType newInstance(XmlOptions options) {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().newInstance(GuiJmxType.type, options);
      }

      public static GuiJmxType parse(String xmlAsString) throws XmlException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GuiJmxType.type, (XmlOptions)null);
      }

      public static GuiJmxType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GuiJmxType.type, options);
      }

      public static GuiJmxType parse(File file) throws XmlException, IOException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(file, GuiJmxType.type, (XmlOptions)null);
      }

      public static GuiJmxType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(file, GuiJmxType.type, options);
      }

      public static GuiJmxType parse(URL u) throws XmlException, IOException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(u, GuiJmxType.type, (XmlOptions)null);
      }

      public static GuiJmxType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(u, GuiJmxType.type, options);
      }

      public static GuiJmxType parse(InputStream is) throws XmlException, IOException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(is, GuiJmxType.type, (XmlOptions)null);
      }

      public static GuiJmxType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(is, GuiJmxType.type, options);
      }

      public static GuiJmxType parse(Reader r) throws XmlException, IOException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(r, GuiJmxType.type, (XmlOptions)null);
      }

      public static GuiJmxType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(r, GuiJmxType.type, options);
      }

      public static GuiJmxType parse(XMLStreamReader sr) throws XmlException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(sr, GuiJmxType.type, (XmlOptions)null);
      }

      public static GuiJmxType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(sr, GuiJmxType.type, options);
      }

      public static GuiJmxType parse(Node node) throws XmlException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(node, GuiJmxType.type, (XmlOptions)null);
      }

      public static GuiJmxType parse(Node node, XmlOptions options) throws XmlException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(node, GuiJmxType.type, options);
      }

      /** @deprecated */
      public static GuiJmxType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(xis, GuiJmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GuiJmxType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GuiJmxType)XmlBeans.getContextTypeLoader().parse(xis, GuiJmxType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GuiJmxType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GuiJmxType.type, options);
      }

      private Factory() {
      }
   }
}
