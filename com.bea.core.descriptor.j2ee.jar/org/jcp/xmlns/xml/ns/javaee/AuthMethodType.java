package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface AuthMethodType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AuthMethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("authmethodtype95d7type");

   public static final class Factory {
      public static AuthMethodType newInstance() {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().newInstance(AuthMethodType.type, (XmlOptions)null);
      }

      public static AuthMethodType newInstance(XmlOptions options) {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().newInstance(AuthMethodType.type, options);
      }

      public static AuthMethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthMethodType.type, (XmlOptions)null);
      }

      public static AuthMethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthMethodType.type, options);
      }

      public static AuthMethodType parse(File file) throws XmlException, IOException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(file, AuthMethodType.type, (XmlOptions)null);
      }

      public static AuthMethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(file, AuthMethodType.type, options);
      }

      public static AuthMethodType parse(URL u) throws XmlException, IOException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(u, AuthMethodType.type, (XmlOptions)null);
      }

      public static AuthMethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(u, AuthMethodType.type, options);
      }

      public static AuthMethodType parse(InputStream is) throws XmlException, IOException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(is, AuthMethodType.type, (XmlOptions)null);
      }

      public static AuthMethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(is, AuthMethodType.type, options);
      }

      public static AuthMethodType parse(Reader r) throws XmlException, IOException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(r, AuthMethodType.type, (XmlOptions)null);
      }

      public static AuthMethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(r, AuthMethodType.type, options);
      }

      public static AuthMethodType parse(XMLStreamReader sr) throws XmlException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(sr, AuthMethodType.type, (XmlOptions)null);
      }

      public static AuthMethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(sr, AuthMethodType.type, options);
      }

      public static AuthMethodType parse(Node node) throws XmlException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(node, AuthMethodType.type, (XmlOptions)null);
      }

      public static AuthMethodType parse(Node node, XmlOptions options) throws XmlException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(node, AuthMethodType.type, options);
      }

      /** @deprecated */
      public static AuthMethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(xis, AuthMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AuthMethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AuthMethodType)XmlBeans.getContextTypeLoader().parse(xis, AuthMethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthMethodType.type, options);
      }

      private Factory() {
      }
   }
}
