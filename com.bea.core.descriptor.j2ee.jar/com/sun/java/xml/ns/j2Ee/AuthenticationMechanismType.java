package com.sun.java.xml.ns.j2Ee;

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

public interface AuthenticationMechanismType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AuthenticationMechanismType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("authenticationmechanismtypead17type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   XsdStringType getAuthenticationMechanismType();

   void setAuthenticationMechanismType(XsdStringType var1);

   XsdStringType addNewAuthenticationMechanismType();

   CredentialInterfaceType getCredentialInterface();

   void setCredentialInterface(CredentialInterfaceType var1);

   CredentialInterfaceType addNewCredentialInterface();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AuthenticationMechanismType newInstance() {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().newInstance(AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType newInstance(XmlOptions options) {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().newInstance(AuthenticationMechanismType.type, options);
      }

      public static AuthenticationMechanismType parse(java.lang.String xmlAsString) throws XmlException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthenticationMechanismType.type, options);
      }

      public static AuthenticationMechanismType parse(File file) throws XmlException, IOException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(file, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(file, AuthenticationMechanismType.type, options);
      }

      public static AuthenticationMechanismType parse(URL u) throws XmlException, IOException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(u, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(u, AuthenticationMechanismType.type, options);
      }

      public static AuthenticationMechanismType parse(InputStream is) throws XmlException, IOException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(is, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(is, AuthenticationMechanismType.type, options);
      }

      public static AuthenticationMechanismType parse(Reader r) throws XmlException, IOException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(r, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(r, AuthenticationMechanismType.type, options);
      }

      public static AuthenticationMechanismType parse(XMLStreamReader sr) throws XmlException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(sr, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(sr, AuthenticationMechanismType.type, options);
      }

      public static AuthenticationMechanismType parse(Node node) throws XmlException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(node, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType parse(Node node, XmlOptions options) throws XmlException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(node, AuthenticationMechanismType.type, options);
      }

      /** @deprecated */
      public static AuthenticationMechanismType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(xis, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AuthenticationMechanismType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(xis, AuthenticationMechanismType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthenticationMechanismType.type, options);
      }

      private Factory() {
      }
   }
}
