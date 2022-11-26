package com.oracle.xmlns.weblogic.weblogicApplicationClient;

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

public interface ProviderUrlType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProviderUrlType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("providerurltype1ccatype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ProviderUrlType newInstance() {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().newInstance(ProviderUrlType.type, (XmlOptions)null);
      }

      public static ProviderUrlType newInstance(XmlOptions options) {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().newInstance(ProviderUrlType.type, options);
      }

      public static ProviderUrlType parse(String xmlAsString) throws XmlException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProviderUrlType.type, (XmlOptions)null);
      }

      public static ProviderUrlType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProviderUrlType.type, options);
      }

      public static ProviderUrlType parse(File file) throws XmlException, IOException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(file, ProviderUrlType.type, (XmlOptions)null);
      }

      public static ProviderUrlType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(file, ProviderUrlType.type, options);
      }

      public static ProviderUrlType parse(URL u) throws XmlException, IOException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(u, ProviderUrlType.type, (XmlOptions)null);
      }

      public static ProviderUrlType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(u, ProviderUrlType.type, options);
      }

      public static ProviderUrlType parse(InputStream is) throws XmlException, IOException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(is, ProviderUrlType.type, (XmlOptions)null);
      }

      public static ProviderUrlType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(is, ProviderUrlType.type, options);
      }

      public static ProviderUrlType parse(Reader r) throws XmlException, IOException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(r, ProviderUrlType.type, (XmlOptions)null);
      }

      public static ProviderUrlType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(r, ProviderUrlType.type, options);
      }

      public static ProviderUrlType parse(XMLStreamReader sr) throws XmlException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(sr, ProviderUrlType.type, (XmlOptions)null);
      }

      public static ProviderUrlType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(sr, ProviderUrlType.type, options);
      }

      public static ProviderUrlType parse(Node node) throws XmlException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(node, ProviderUrlType.type, (XmlOptions)null);
      }

      public static ProviderUrlType parse(Node node, XmlOptions options) throws XmlException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(node, ProviderUrlType.type, options);
      }

      /** @deprecated */
      public static ProviderUrlType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(xis, ProviderUrlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProviderUrlType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProviderUrlType)XmlBeans.getContextTypeLoader().parse(xis, ProviderUrlType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProviderUrlType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProviderUrlType.type, options);
      }

      private Factory() {
      }
   }
}
