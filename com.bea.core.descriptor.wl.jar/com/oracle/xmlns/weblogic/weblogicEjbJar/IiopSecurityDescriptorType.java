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

public interface IiopSecurityDescriptorType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IiopSecurityDescriptorType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("iiopsecuritydescriptortypeae86type");

   TransportRequirementsType getTransportRequirements();

   boolean isSetTransportRequirements();

   void setTransportRequirements(TransportRequirementsType var1);

   TransportRequirementsType addNewTransportRequirements();

   void unsetTransportRequirements();

   ClientAuthenticationType getClientAuthentication();

   boolean isSetClientAuthentication();

   void setClientAuthentication(ClientAuthenticationType var1);

   ClientAuthenticationType addNewClientAuthentication();

   void unsetClientAuthentication();

   IdentityAssertionType getIdentityAssertion();

   boolean isSetIdentityAssertion();

   void setIdentityAssertion(IdentityAssertionType var1);

   IdentityAssertionType addNewIdentityAssertion();

   void unsetIdentityAssertion();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static IiopSecurityDescriptorType newInstance() {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().newInstance(IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      public static IiopSecurityDescriptorType newInstance(XmlOptions options) {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().newInstance(IiopSecurityDescriptorType.type, options);
      }

      public static IiopSecurityDescriptorType parse(String xmlAsString) throws XmlException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      public static IiopSecurityDescriptorType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IiopSecurityDescriptorType.type, options);
      }

      public static IiopSecurityDescriptorType parse(File file) throws XmlException, IOException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(file, IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      public static IiopSecurityDescriptorType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(file, IiopSecurityDescriptorType.type, options);
      }

      public static IiopSecurityDescriptorType parse(URL u) throws XmlException, IOException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(u, IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      public static IiopSecurityDescriptorType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(u, IiopSecurityDescriptorType.type, options);
      }

      public static IiopSecurityDescriptorType parse(InputStream is) throws XmlException, IOException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(is, IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      public static IiopSecurityDescriptorType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(is, IiopSecurityDescriptorType.type, options);
      }

      public static IiopSecurityDescriptorType parse(Reader r) throws XmlException, IOException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(r, IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      public static IiopSecurityDescriptorType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(r, IiopSecurityDescriptorType.type, options);
      }

      public static IiopSecurityDescriptorType parse(XMLStreamReader sr) throws XmlException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      public static IiopSecurityDescriptorType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(sr, IiopSecurityDescriptorType.type, options);
      }

      public static IiopSecurityDescriptorType parse(Node node) throws XmlException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(node, IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      public static IiopSecurityDescriptorType parse(Node node, XmlOptions options) throws XmlException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(node, IiopSecurityDescriptorType.type, options);
      }

      /** @deprecated */
      public static IiopSecurityDescriptorType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IiopSecurityDescriptorType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IiopSecurityDescriptorType)XmlBeans.getContextTypeLoader().parse(xis, IiopSecurityDescriptorType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IiopSecurityDescriptorType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IiopSecurityDescriptorType.type, options);
      }

      private Factory() {
      }
   }
}
