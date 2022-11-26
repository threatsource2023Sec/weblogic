package com.oracle.xmlns.weblogic.weblogicWebApp;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface ResourcePathType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourcePathType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("resourcepathtype5485type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResourcePathType newInstance() {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().newInstance(ResourcePathType.type, (XmlOptions)null);
      }

      public static ResourcePathType newInstance(XmlOptions options) {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().newInstance(ResourcePathType.type, options);
      }

      public static ResourcePathType parse(String xmlAsString) throws XmlException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourcePathType.type, (XmlOptions)null);
      }

      public static ResourcePathType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourcePathType.type, options);
      }

      public static ResourcePathType parse(File file) throws XmlException, IOException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(file, ResourcePathType.type, (XmlOptions)null);
      }

      public static ResourcePathType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(file, ResourcePathType.type, options);
      }

      public static ResourcePathType parse(URL u) throws XmlException, IOException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(u, ResourcePathType.type, (XmlOptions)null);
      }

      public static ResourcePathType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(u, ResourcePathType.type, options);
      }

      public static ResourcePathType parse(InputStream is) throws XmlException, IOException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(is, ResourcePathType.type, (XmlOptions)null);
      }

      public static ResourcePathType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(is, ResourcePathType.type, options);
      }

      public static ResourcePathType parse(Reader r) throws XmlException, IOException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(r, ResourcePathType.type, (XmlOptions)null);
      }

      public static ResourcePathType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(r, ResourcePathType.type, options);
      }

      public static ResourcePathType parse(XMLStreamReader sr) throws XmlException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(sr, ResourcePathType.type, (XmlOptions)null);
      }

      public static ResourcePathType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(sr, ResourcePathType.type, options);
      }

      public static ResourcePathType parse(Node node) throws XmlException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(node, ResourcePathType.type, (XmlOptions)null);
      }

      public static ResourcePathType parse(Node node, XmlOptions options) throws XmlException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(node, ResourcePathType.type, options);
      }

      /** @deprecated */
      public static ResourcePathType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(xis, ResourcePathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourcePathType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourcePathType)XmlBeans.getContextTypeLoader().parse(xis, ResourcePathType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourcePathType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourcePathType.type, options);
      }

      private Factory() {
      }
   }
}
