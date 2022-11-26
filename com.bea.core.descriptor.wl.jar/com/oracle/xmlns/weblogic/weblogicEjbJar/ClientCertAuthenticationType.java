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

public interface ClientCertAuthenticationType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClientCertAuthenticationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("clientcertauthenticationtypecff5type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ClientCertAuthenticationType newInstance() {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().newInstance(ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientCertAuthenticationType newInstance(XmlOptions options) {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().newInstance(ClientCertAuthenticationType.type, options);
      }

      public static ClientCertAuthenticationType parse(String xmlAsString) throws XmlException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientCertAuthenticationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientCertAuthenticationType.type, options);
      }

      public static ClientCertAuthenticationType parse(File file) throws XmlException, IOException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(file, ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientCertAuthenticationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(file, ClientCertAuthenticationType.type, options);
      }

      public static ClientCertAuthenticationType parse(URL u) throws XmlException, IOException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(u, ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientCertAuthenticationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(u, ClientCertAuthenticationType.type, options);
      }

      public static ClientCertAuthenticationType parse(InputStream is) throws XmlException, IOException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(is, ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientCertAuthenticationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(is, ClientCertAuthenticationType.type, options);
      }

      public static ClientCertAuthenticationType parse(Reader r) throws XmlException, IOException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(r, ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientCertAuthenticationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(r, ClientCertAuthenticationType.type, options);
      }

      public static ClientCertAuthenticationType parse(XMLStreamReader sr) throws XmlException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(sr, ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientCertAuthenticationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(sr, ClientCertAuthenticationType.type, options);
      }

      public static ClientCertAuthenticationType parse(Node node) throws XmlException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(node, ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      public static ClientCertAuthenticationType parse(Node node, XmlOptions options) throws XmlException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(node, ClientCertAuthenticationType.type, options);
      }

      /** @deprecated */
      public static ClientCertAuthenticationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(xis, ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClientCertAuthenticationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClientCertAuthenticationType)XmlBeans.getContextTypeLoader().parse(xis, ClientCertAuthenticationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientCertAuthenticationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientCertAuthenticationType.type, options);
      }

      private Factory() {
      }
   }
}
