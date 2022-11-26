package com.oracle.xmlns.weblogic.weblogicEjbJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface SecurityPermissionSpecType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SecurityPermissionSpecType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("securitypermissionspectype9ea2type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static SecurityPermissionSpecType newInstance() {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().newInstance(SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      public static SecurityPermissionSpecType newInstance(XmlOptions options) {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().newInstance(SecurityPermissionSpecType.type, options);
      }

      public static SecurityPermissionSpecType parse(String xmlAsString) throws XmlException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      public static SecurityPermissionSpecType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SecurityPermissionSpecType.type, options);
      }

      public static SecurityPermissionSpecType parse(File file) throws XmlException, IOException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(file, SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      public static SecurityPermissionSpecType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(file, SecurityPermissionSpecType.type, options);
      }

      public static SecurityPermissionSpecType parse(URL u) throws XmlException, IOException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(u, SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      public static SecurityPermissionSpecType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(u, SecurityPermissionSpecType.type, options);
      }

      public static SecurityPermissionSpecType parse(InputStream is) throws XmlException, IOException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(is, SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      public static SecurityPermissionSpecType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(is, SecurityPermissionSpecType.type, options);
      }

      public static SecurityPermissionSpecType parse(Reader r) throws XmlException, IOException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(r, SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      public static SecurityPermissionSpecType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(r, SecurityPermissionSpecType.type, options);
      }

      public static SecurityPermissionSpecType parse(XMLStreamReader sr) throws XmlException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(sr, SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      public static SecurityPermissionSpecType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(sr, SecurityPermissionSpecType.type, options);
      }

      public static SecurityPermissionSpecType parse(Node node) throws XmlException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(node, SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      public static SecurityPermissionSpecType parse(Node node, XmlOptions options) throws XmlException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(node, SecurityPermissionSpecType.type, options);
      }

      /** @deprecated */
      public static SecurityPermissionSpecType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(xis, SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SecurityPermissionSpecType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SecurityPermissionSpecType)XmlBeans.getContextTypeLoader().parse(xis, SecurityPermissionSpecType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPermissionSpecType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SecurityPermissionSpecType.type, options);
      }

      private Factory() {
      }
   }
}
