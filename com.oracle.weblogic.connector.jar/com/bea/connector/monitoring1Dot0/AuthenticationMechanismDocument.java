package com.bea.connector.monitoring1Dot0;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface AuthenticationMechanismDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AuthenticationMechanismDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("authenticationmechanisme86adoctype");

   AuthenticationMechanismType getAuthenticationMechanism();

   void setAuthenticationMechanism(AuthenticationMechanismType var1);

   AuthenticationMechanismType addNewAuthenticationMechanism();

   public static final class Factory {
      public static AuthenticationMechanismDocument newInstance() {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().newInstance(AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismDocument newInstance(XmlOptions options) {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().newInstance(AuthenticationMechanismDocument.type, options);
      }

      public static AuthenticationMechanismDocument parse(String xmlAsString) throws XmlException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthenticationMechanismDocument.type, options);
      }

      public static AuthenticationMechanismDocument parse(File file) throws XmlException, IOException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(file, AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(file, AuthenticationMechanismDocument.type, options);
      }

      public static AuthenticationMechanismDocument parse(URL u) throws XmlException, IOException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(u, AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(u, AuthenticationMechanismDocument.type, options);
      }

      public static AuthenticationMechanismDocument parse(InputStream is) throws XmlException, IOException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(is, AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(is, AuthenticationMechanismDocument.type, options);
      }

      public static AuthenticationMechanismDocument parse(Reader r) throws XmlException, IOException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(r, AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(r, AuthenticationMechanismDocument.type, options);
      }

      public static AuthenticationMechanismDocument parse(XMLStreamReader sr) throws XmlException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(sr, AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(sr, AuthenticationMechanismDocument.type, options);
      }

      public static AuthenticationMechanismDocument parse(Node node) throws XmlException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(node, AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      public static AuthenticationMechanismDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(node, AuthenticationMechanismDocument.type, options);
      }

      /** @deprecated */
      public static AuthenticationMechanismDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(xis, AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AuthenticationMechanismDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AuthenticationMechanismDocument)XmlBeans.getContextTypeLoader().parse(xis, AuthenticationMechanismDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthenticationMechanismDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthenticationMechanismDocument.type, options);
      }

      private Factory() {
      }
   }
}
