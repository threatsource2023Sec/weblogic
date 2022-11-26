package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
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

public interface AuthenticationMechanismType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AuthenticationMechanismType.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("authenticationmechanismtyped1e0type");

   String[] getDescriptionArray();

   String getDescriptionArray(int var1);

   XmlString[] xgetDescriptionArray();

   XmlString xgetDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(String[] var1);

   void setDescriptionArray(int var1, String var2);

   void xsetDescriptionArray(XmlString[] var1);

   void xsetDescriptionArray(int var1, XmlString var2);

   void insertDescription(int var1, String var2);

   void addDescription(String var1);

   XmlString insertNewDescription(int var1);

   XmlString addNewDescription();

   void removeDescription(int var1);

   String getAuthenticationMechanismType();

   XmlString xgetAuthenticationMechanismType();

   void setAuthenticationMechanismType(String var1);

   void xsetAuthenticationMechanismType(XmlString var1);

   String getCredentialInterface();

   XmlString xgetCredentialInterface();

   void setCredentialInterface(String var1);

   void xsetCredentialInterface(XmlString var1);

   public static final class Factory {
      public static AuthenticationMechanismType newInstance() {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().newInstance(AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType newInstance(XmlOptions options) {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().newInstance(AuthenticationMechanismType.type, options);
      }

      public static AuthenticationMechanismType parse(String xmlAsString) throws XmlException {
         return (AuthenticationMechanismType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthenticationMechanismType.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismType parse(String xmlAsString, XmlOptions options) throws XmlException {
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
