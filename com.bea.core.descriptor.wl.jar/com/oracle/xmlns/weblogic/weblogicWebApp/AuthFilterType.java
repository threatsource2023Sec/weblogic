package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface AuthFilterType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AuthFilterType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("authfiltertyped23etype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AuthFilterType newInstance() {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().newInstance(AuthFilterType.type, (XmlOptions)null);
      }

      public static AuthFilterType newInstance(XmlOptions options) {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().newInstance(AuthFilterType.type, options);
      }

      public static AuthFilterType parse(String xmlAsString) throws XmlException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthFilterType.type, (XmlOptions)null);
      }

      public static AuthFilterType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AuthFilterType.type, options);
      }

      public static AuthFilterType parse(File file) throws XmlException, IOException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(file, AuthFilterType.type, (XmlOptions)null);
      }

      public static AuthFilterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(file, AuthFilterType.type, options);
      }

      public static AuthFilterType parse(URL u) throws XmlException, IOException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(u, AuthFilterType.type, (XmlOptions)null);
      }

      public static AuthFilterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(u, AuthFilterType.type, options);
      }

      public static AuthFilterType parse(InputStream is) throws XmlException, IOException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(is, AuthFilterType.type, (XmlOptions)null);
      }

      public static AuthFilterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(is, AuthFilterType.type, options);
      }

      public static AuthFilterType parse(Reader r) throws XmlException, IOException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(r, AuthFilterType.type, (XmlOptions)null);
      }

      public static AuthFilterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(r, AuthFilterType.type, options);
      }

      public static AuthFilterType parse(XMLStreamReader sr) throws XmlException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(sr, AuthFilterType.type, (XmlOptions)null);
      }

      public static AuthFilterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(sr, AuthFilterType.type, options);
      }

      public static AuthFilterType parse(Node node) throws XmlException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(node, AuthFilterType.type, (XmlOptions)null);
      }

      public static AuthFilterType parse(Node node, XmlOptions options) throws XmlException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(node, AuthFilterType.type, options);
      }

      /** @deprecated */
      public static AuthFilterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(xis, AuthFilterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AuthFilterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AuthFilterType)XmlBeans.getContextTypeLoader().parse(xis, AuthFilterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthFilterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AuthFilterType.type, options);
      }

      private Factory() {
      }
   }
}
