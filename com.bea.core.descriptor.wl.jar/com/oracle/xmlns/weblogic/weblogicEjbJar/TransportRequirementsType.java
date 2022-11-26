package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface TransportRequirementsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TransportRequirementsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("transportrequirementstype1f2atype");

   IntegrityType getIntegrity();

   boolean isSetIntegrity();

   void setIntegrity(IntegrityType var1);

   IntegrityType addNewIntegrity();

   void unsetIntegrity();

   ConfidentialityType getConfidentiality();

   boolean isSetConfidentiality();

   void setConfidentiality(ConfidentialityType var1);

   ConfidentialityType addNewConfidentiality();

   void unsetConfidentiality();

   ClientCertAuthenticationType getClientCertAuthentication();

   boolean isSetClientCertAuthentication();

   void setClientCertAuthentication(ClientCertAuthenticationType var1);

   ClientCertAuthenticationType addNewClientCertAuthentication();

   void unsetClientCertAuthentication();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static TransportRequirementsType newInstance() {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().newInstance(TransportRequirementsType.type, (XmlOptions)null);
      }

      public static TransportRequirementsType newInstance(XmlOptions options) {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().newInstance(TransportRequirementsType.type, options);
      }

      public static TransportRequirementsType parse(String xmlAsString) throws XmlException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransportRequirementsType.type, (XmlOptions)null);
      }

      public static TransportRequirementsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TransportRequirementsType.type, options);
      }

      public static TransportRequirementsType parse(File file) throws XmlException, IOException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(file, TransportRequirementsType.type, (XmlOptions)null);
      }

      public static TransportRequirementsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(file, TransportRequirementsType.type, options);
      }

      public static TransportRequirementsType parse(URL u) throws XmlException, IOException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(u, TransportRequirementsType.type, (XmlOptions)null);
      }

      public static TransportRequirementsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(u, TransportRequirementsType.type, options);
      }

      public static TransportRequirementsType parse(InputStream is) throws XmlException, IOException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(is, TransportRequirementsType.type, (XmlOptions)null);
      }

      public static TransportRequirementsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(is, TransportRequirementsType.type, options);
      }

      public static TransportRequirementsType parse(Reader r) throws XmlException, IOException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(r, TransportRequirementsType.type, (XmlOptions)null);
      }

      public static TransportRequirementsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(r, TransportRequirementsType.type, options);
      }

      public static TransportRequirementsType parse(XMLStreamReader sr) throws XmlException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(sr, TransportRequirementsType.type, (XmlOptions)null);
      }

      public static TransportRequirementsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(sr, TransportRequirementsType.type, options);
      }

      public static TransportRequirementsType parse(Node node) throws XmlException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(node, TransportRequirementsType.type, (XmlOptions)null);
      }

      public static TransportRequirementsType parse(Node node, XmlOptions options) throws XmlException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(node, TransportRequirementsType.type, options);
      }

      /** @deprecated */
      public static TransportRequirementsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(xis, TransportRequirementsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TransportRequirementsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TransportRequirementsType)XmlBeans.getContextTypeLoader().parse(xis, TransportRequirementsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransportRequirementsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TransportRequirementsType.type, options);
      }

      private Factory() {
      }
   }
}
