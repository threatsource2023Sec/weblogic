package com.oracle.xmlns.weblogic.weblogicEjbJar;

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

public interface ResourceAdapterJndiNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResourceAdapterJndiNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("resourceadapterjndinametypeaf88type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ResourceAdapterJndiNameType newInstance() {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().newInstance(ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      public static ResourceAdapterJndiNameType newInstance(XmlOptions options) {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().newInstance(ResourceAdapterJndiNameType.type, options);
      }

      public static ResourceAdapterJndiNameType parse(String xmlAsString) throws XmlException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      public static ResourceAdapterJndiNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResourceAdapterJndiNameType.type, options);
      }

      public static ResourceAdapterJndiNameType parse(File file) throws XmlException, IOException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(file, ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      public static ResourceAdapterJndiNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(file, ResourceAdapterJndiNameType.type, options);
      }

      public static ResourceAdapterJndiNameType parse(URL u) throws XmlException, IOException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(u, ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      public static ResourceAdapterJndiNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(u, ResourceAdapterJndiNameType.type, options);
      }

      public static ResourceAdapterJndiNameType parse(InputStream is) throws XmlException, IOException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(is, ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      public static ResourceAdapterJndiNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(is, ResourceAdapterJndiNameType.type, options);
      }

      public static ResourceAdapterJndiNameType parse(Reader r) throws XmlException, IOException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(r, ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      public static ResourceAdapterJndiNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(r, ResourceAdapterJndiNameType.type, options);
      }

      public static ResourceAdapterJndiNameType parse(XMLStreamReader sr) throws XmlException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(sr, ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      public static ResourceAdapterJndiNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(sr, ResourceAdapterJndiNameType.type, options);
      }

      public static ResourceAdapterJndiNameType parse(Node node) throws XmlException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(node, ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      public static ResourceAdapterJndiNameType parse(Node node, XmlOptions options) throws XmlException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(node, ResourceAdapterJndiNameType.type, options);
      }

      /** @deprecated */
      public static ResourceAdapterJndiNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(xis, ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResourceAdapterJndiNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResourceAdapterJndiNameType)XmlBeans.getContextTypeLoader().parse(xis, ResourceAdapterJndiNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceAdapterJndiNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResourceAdapterJndiNameType.type, options);
      }

      private Factory() {
      }
   }
}
