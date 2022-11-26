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

public interface ClientAuthenticationType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClientAuthenticationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("clientauthenticationtype2bbetype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ClientAuthenticationType newInstance() {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().newInstance(ClientAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientAuthenticationType newInstance(XmlOptions options) {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().newInstance(ClientAuthenticationType.type, options);
      }

      public static ClientAuthenticationType parse(String xmlAsString) throws XmlException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientAuthenticationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientAuthenticationType.type, options);
      }

      public static ClientAuthenticationType parse(File file) throws XmlException, IOException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(file, ClientAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientAuthenticationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(file, ClientAuthenticationType.type, options);
      }

      public static ClientAuthenticationType parse(URL u) throws XmlException, IOException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(u, ClientAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientAuthenticationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(u, ClientAuthenticationType.type, options);
      }

      public static ClientAuthenticationType parse(InputStream is) throws XmlException, IOException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(is, ClientAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientAuthenticationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(is, ClientAuthenticationType.type, options);
      }

      public static ClientAuthenticationType parse(Reader r) throws XmlException, IOException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(r, ClientAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientAuthenticationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(r, ClientAuthenticationType.type, options);
      }

      public static ClientAuthenticationType parse(XMLStreamReader sr) throws XmlException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(sr, ClientAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientAuthenticationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(sr, ClientAuthenticationType.type, options);
      }

      public static ClientAuthenticationType parse(Node node) throws XmlException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(node, ClientAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientAuthenticationType parse(Node node, XmlOptions options) throws XmlException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(node, ClientAuthenticationType.type, options);
      }

      /** @deprecated */
      public static ClientAuthenticationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(xis, ClientAuthenticationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClientAuthenticationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClientAuthenticationType)XmlBeans.getContextTypeLoader().parse(xis, ClientAuthenticationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientAuthenticationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientAuthenticationType.type, options);
      }

      private Factory() {
      }
   }
}
