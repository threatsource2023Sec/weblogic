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

public interface SecurityDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("securityfaeedoctype");

   ResourceAdapterSecurityType getSecurity();

   void setSecurity(ResourceAdapterSecurityType var1);

   ResourceAdapterSecurityType addNewSecurity();

   public static final class Factory {
      public static SecurityDocument newInstance() {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().newInstance(SecurityDocument.type, (XmlOptions)null);
      }

      public static SecurityDocument newInstance(XmlOptions options) {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().newInstance(SecurityDocument.type, options);
      }

      public static SecurityDocument parse(String xmlAsString) throws XmlException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityDocument.type, (XmlOptions)null);
      }

      public static SecurityDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityDocument.type, options);
      }

      public static SecurityDocument parse(File file) throws XmlException, IOException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(file, SecurityDocument.type, (XmlOptions)null);
      }

      public static SecurityDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(file, SecurityDocument.type, options);
      }

      public static SecurityDocument parse(URL u) throws XmlException, IOException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(u, SecurityDocument.type, (XmlOptions)null);
      }

      public static SecurityDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(u, SecurityDocument.type, options);
      }

      public static SecurityDocument parse(InputStream is) throws XmlException, IOException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(is, SecurityDocument.type, (XmlOptions)null);
      }

      public static SecurityDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(is, SecurityDocument.type, options);
      }

      public static SecurityDocument parse(Reader r) throws XmlException, IOException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(r, SecurityDocument.type, (XmlOptions)null);
      }

      public static SecurityDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(r, SecurityDocument.type, options);
      }

      public static SecurityDocument parse(XMLStreamReader sr) throws XmlException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(sr, SecurityDocument.type, (XmlOptions)null);
      }

      public static SecurityDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(sr, SecurityDocument.type, options);
      }

      public static SecurityDocument parse(Node node) throws XmlException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(node, SecurityDocument.type, (XmlOptions)null);
      }

      public static SecurityDocument parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(node, SecurityDocument.type, options);
      }

      /** @deprecated */
      public static SecurityDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(xis, SecurityDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityDocument)XmlBeans.getContextTypeLoader().parse(xis, SecurityDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityDocument.type, options);
      }

      private Factory() {
      }
   }
}
