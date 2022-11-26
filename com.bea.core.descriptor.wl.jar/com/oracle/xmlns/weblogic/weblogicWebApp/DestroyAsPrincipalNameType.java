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

public interface DestroyAsPrincipalNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DestroyAsPrincipalNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("destroyasprincipalnametypef5b3type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static DestroyAsPrincipalNameType newInstance() {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static DestroyAsPrincipalNameType newInstance(XmlOptions options) {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(DestroyAsPrincipalNameType.type, options);
      }

      public static DestroyAsPrincipalNameType parse(String xmlAsString) throws XmlException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static DestroyAsPrincipalNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestroyAsPrincipalNameType.type, options);
      }

      public static DestroyAsPrincipalNameType parse(File file) throws XmlException, IOException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static DestroyAsPrincipalNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, DestroyAsPrincipalNameType.type, options);
      }

      public static DestroyAsPrincipalNameType parse(URL u) throws XmlException, IOException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static DestroyAsPrincipalNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, DestroyAsPrincipalNameType.type, options);
      }

      public static DestroyAsPrincipalNameType parse(InputStream is) throws XmlException, IOException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static DestroyAsPrincipalNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, DestroyAsPrincipalNameType.type, options);
      }

      public static DestroyAsPrincipalNameType parse(Reader r) throws XmlException, IOException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static DestroyAsPrincipalNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, DestroyAsPrincipalNameType.type, options);
      }

      public static DestroyAsPrincipalNameType parse(XMLStreamReader sr) throws XmlException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static DestroyAsPrincipalNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, DestroyAsPrincipalNameType.type, options);
      }

      public static DestroyAsPrincipalNameType parse(Node node) throws XmlException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static DestroyAsPrincipalNameType parse(Node node, XmlOptions options) throws XmlException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, DestroyAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static DestroyAsPrincipalNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DestroyAsPrincipalNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DestroyAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, DestroyAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestroyAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestroyAsPrincipalNameType.type, options);
      }

      private Factory() {
      }
   }
}
