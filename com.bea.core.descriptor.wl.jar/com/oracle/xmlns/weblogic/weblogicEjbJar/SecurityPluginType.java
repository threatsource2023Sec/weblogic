package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface SecurityPluginType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityPluginType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("securityplugintypec624type");

   XsdStringType getPluginClass();

   void setPluginClass(XsdStringType var1);

   XsdStringType addNewPluginClass();

   XsdStringType getKey();

   void setKey(XsdStringType var1);

   XsdStringType addNewKey();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SecurityPluginType newInstance() {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().newInstance(SecurityPluginType.type, (XmlOptions)null);
      }

      public static SecurityPluginType newInstance(XmlOptions options) {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().newInstance(SecurityPluginType.type, options);
      }

      public static SecurityPluginType parse(String xmlAsString) throws XmlException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPluginType.type, (XmlOptions)null);
      }

      public static SecurityPluginType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPluginType.type, options);
      }

      public static SecurityPluginType parse(File file) throws XmlException, IOException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(file, SecurityPluginType.type, (XmlOptions)null);
      }

      public static SecurityPluginType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(file, SecurityPluginType.type, options);
      }

      public static SecurityPluginType parse(URL u) throws XmlException, IOException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(u, SecurityPluginType.type, (XmlOptions)null);
      }

      public static SecurityPluginType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(u, SecurityPluginType.type, options);
      }

      public static SecurityPluginType parse(InputStream is) throws XmlException, IOException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(is, SecurityPluginType.type, (XmlOptions)null);
      }

      public static SecurityPluginType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(is, SecurityPluginType.type, options);
      }

      public static SecurityPluginType parse(Reader r) throws XmlException, IOException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(r, SecurityPluginType.type, (XmlOptions)null);
      }

      public static SecurityPluginType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(r, SecurityPluginType.type, options);
      }

      public static SecurityPluginType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(sr, SecurityPluginType.type, (XmlOptions)null);
      }

      public static SecurityPluginType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(sr, SecurityPluginType.type, options);
      }

      public static SecurityPluginType parse(Node node) throws XmlException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(node, SecurityPluginType.type, (XmlOptions)null);
      }

      public static SecurityPluginType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(node, SecurityPluginType.type, options);
      }

      /** @deprecated */
      public static SecurityPluginType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(xis, SecurityPluginType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityPluginType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityPluginType)XmlBeans.getContextTypeLoader().parse(xis, SecurityPluginType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPluginType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPluginType.type, options);
      }

      private Factory() {
      }
   }
}
