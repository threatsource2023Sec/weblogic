package com.sun.java.xml.ns.javaee;

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

public interface RoleNameType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RoleNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("rolenametype427dtype");

   public static final class Factory {
      public static RoleNameType newInstance() {
         return (RoleNameType)XmlBeans.getContextTypeLoader().newInstance(RoleNameType.type, (XmlOptions)null);
      }

      public static RoleNameType newInstance(XmlOptions options) {
         return (RoleNameType)XmlBeans.getContextTypeLoader().newInstance(RoleNameType.type, options);
      }

      public static RoleNameType parse(java.lang.String xmlAsString) throws XmlException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RoleNameType.type, (XmlOptions)null);
      }

      public static RoleNameType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RoleNameType.type, options);
      }

      public static RoleNameType parse(File file) throws XmlException, IOException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(file, RoleNameType.type, (XmlOptions)null);
      }

      public static RoleNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(file, RoleNameType.type, options);
      }

      public static RoleNameType parse(URL u) throws XmlException, IOException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(u, RoleNameType.type, (XmlOptions)null);
      }

      public static RoleNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(u, RoleNameType.type, options);
      }

      public static RoleNameType parse(InputStream is) throws XmlException, IOException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(is, RoleNameType.type, (XmlOptions)null);
      }

      public static RoleNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(is, RoleNameType.type, options);
      }

      public static RoleNameType parse(Reader r) throws XmlException, IOException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(r, RoleNameType.type, (XmlOptions)null);
      }

      public static RoleNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(r, RoleNameType.type, options);
      }

      public static RoleNameType parse(XMLStreamReader sr) throws XmlException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(sr, RoleNameType.type, (XmlOptions)null);
      }

      public static RoleNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(sr, RoleNameType.type, options);
      }

      public static RoleNameType parse(Node node) throws XmlException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(node, RoleNameType.type, (XmlOptions)null);
      }

      public static RoleNameType parse(Node node, XmlOptions options) throws XmlException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(node, RoleNameType.type, options);
      }

      /** @deprecated */
      public static RoleNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(xis, RoleNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RoleNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RoleNameType)XmlBeans.getContextTypeLoader().parse(xis, RoleNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RoleNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RoleNameType.type, options);
      }

      private Factory() {
      }
   }
}
