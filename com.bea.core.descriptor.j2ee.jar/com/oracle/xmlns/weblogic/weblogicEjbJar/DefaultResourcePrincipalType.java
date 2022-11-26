package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.javaee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DefaultResourcePrincipalType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultResourcePrincipalType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("defaultresourceprincipaltype96f1type");

   String getName();

   void setName(String var1);

   String addNewName();

   String getPassword();

   void setPassword(String var1);

   String addNewPassword();

   public static final class Factory {
      public static DefaultResourcePrincipalType newInstance() {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().newInstance(DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      public static DefaultResourcePrincipalType newInstance(XmlOptions options) {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().newInstance(DefaultResourcePrincipalType.type, options);
      }

      public static DefaultResourcePrincipalType parse(java.lang.String xmlAsString) throws XmlException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      public static DefaultResourcePrincipalType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultResourcePrincipalType.type, options);
      }

      public static DefaultResourcePrincipalType parse(File file) throws XmlException, IOException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(file, DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      public static DefaultResourcePrincipalType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(file, DefaultResourcePrincipalType.type, options);
      }

      public static DefaultResourcePrincipalType parse(URL u) throws XmlException, IOException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(u, DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      public static DefaultResourcePrincipalType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(u, DefaultResourcePrincipalType.type, options);
      }

      public static DefaultResourcePrincipalType parse(InputStream is) throws XmlException, IOException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(is, DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      public static DefaultResourcePrincipalType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(is, DefaultResourcePrincipalType.type, options);
      }

      public static DefaultResourcePrincipalType parse(Reader r) throws XmlException, IOException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(r, DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      public static DefaultResourcePrincipalType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(r, DefaultResourcePrincipalType.type, options);
      }

      public static DefaultResourcePrincipalType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(sr, DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      public static DefaultResourcePrincipalType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(sr, DefaultResourcePrincipalType.type, options);
      }

      public static DefaultResourcePrincipalType parse(Node node) throws XmlException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(node, DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      public static DefaultResourcePrincipalType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(node, DefaultResourcePrincipalType.type, options);
      }

      /** @deprecated */
      public static DefaultResourcePrincipalType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(xis, DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultResourcePrincipalType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultResourcePrincipalType)XmlBeans.getContextTypeLoader().parse(xis, DefaultResourcePrincipalType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultResourcePrincipalType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultResourcePrincipalType.type, options);
      }

      private Factory() {
      }
   }
}
