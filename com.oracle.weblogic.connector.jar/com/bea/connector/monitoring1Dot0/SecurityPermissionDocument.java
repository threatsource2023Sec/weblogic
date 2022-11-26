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

public interface SecurityPermissionDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityPermissionDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("securitypermissionc084doctype");

   SecurityPermissionType getSecurityPermission();

   void setSecurityPermission(SecurityPermissionType var1);

   SecurityPermissionType addNewSecurityPermission();

   public static final class Factory {
      public static SecurityPermissionDocument newInstance() {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().newInstance(SecurityPermissionDocument.type, (XmlOptions)null);
      }

      public static SecurityPermissionDocument newInstance(XmlOptions options) {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().newInstance(SecurityPermissionDocument.type, options);
      }

      public static SecurityPermissionDocument parse(String xmlAsString) throws XmlException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPermissionDocument.type, (XmlOptions)null);
      }

      public static SecurityPermissionDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPermissionDocument.type, options);
      }

      public static SecurityPermissionDocument parse(File file) throws XmlException, IOException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(file, SecurityPermissionDocument.type, (XmlOptions)null);
      }

      public static SecurityPermissionDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(file, SecurityPermissionDocument.type, options);
      }

      public static SecurityPermissionDocument parse(URL u) throws XmlException, IOException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(u, SecurityPermissionDocument.type, (XmlOptions)null);
      }

      public static SecurityPermissionDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(u, SecurityPermissionDocument.type, options);
      }

      public static SecurityPermissionDocument parse(InputStream is) throws XmlException, IOException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(is, SecurityPermissionDocument.type, (XmlOptions)null);
      }

      public static SecurityPermissionDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(is, SecurityPermissionDocument.type, options);
      }

      public static SecurityPermissionDocument parse(Reader r) throws XmlException, IOException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(r, SecurityPermissionDocument.type, (XmlOptions)null);
      }

      public static SecurityPermissionDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(r, SecurityPermissionDocument.type, options);
      }

      public static SecurityPermissionDocument parse(XMLStreamReader sr) throws XmlException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(sr, SecurityPermissionDocument.type, (XmlOptions)null);
      }

      public static SecurityPermissionDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(sr, SecurityPermissionDocument.type, options);
      }

      public static SecurityPermissionDocument parse(Node node) throws XmlException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(node, SecurityPermissionDocument.type, (XmlOptions)null);
      }

      public static SecurityPermissionDocument parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(node, SecurityPermissionDocument.type, options);
      }

      /** @deprecated */
      public static SecurityPermissionDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(xis, SecurityPermissionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityPermissionDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityPermissionDocument)XmlBeans.getContextTypeLoader().parse(xis, SecurityPermissionDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPermissionDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPermissionDocument.type, options);
      }

      private Factory() {
      }
   }
}
