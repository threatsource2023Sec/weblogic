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

public interface IdentityAssertionType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IdentityAssertionType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("identityassertiontype3669type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static IdentityAssertionType newInstance() {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().newInstance(IdentityAssertionType.type, (XmlOptions)null);
      }

      public static IdentityAssertionType newInstance(XmlOptions options) {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().newInstance(IdentityAssertionType.type, options);
      }

      public static IdentityAssertionType parse(String xmlAsString) throws XmlException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IdentityAssertionType.type, (XmlOptions)null);
      }

      public static IdentityAssertionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IdentityAssertionType.type, options);
      }

      public static IdentityAssertionType parse(File file) throws XmlException, IOException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(file, IdentityAssertionType.type, (XmlOptions)null);
      }

      public static IdentityAssertionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(file, IdentityAssertionType.type, options);
      }

      public static IdentityAssertionType parse(URL u) throws XmlException, IOException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(u, IdentityAssertionType.type, (XmlOptions)null);
      }

      public static IdentityAssertionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(u, IdentityAssertionType.type, options);
      }

      public static IdentityAssertionType parse(InputStream is) throws XmlException, IOException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(is, IdentityAssertionType.type, (XmlOptions)null);
      }

      public static IdentityAssertionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(is, IdentityAssertionType.type, options);
      }

      public static IdentityAssertionType parse(Reader r) throws XmlException, IOException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(r, IdentityAssertionType.type, (XmlOptions)null);
      }

      public static IdentityAssertionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(r, IdentityAssertionType.type, options);
      }

      public static IdentityAssertionType parse(XMLStreamReader sr) throws XmlException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(sr, IdentityAssertionType.type, (XmlOptions)null);
      }

      public static IdentityAssertionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(sr, IdentityAssertionType.type, options);
      }

      public static IdentityAssertionType parse(Node node) throws XmlException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(node, IdentityAssertionType.type, (XmlOptions)null);
      }

      public static IdentityAssertionType parse(Node node, XmlOptions options) throws XmlException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(node, IdentityAssertionType.type, options);
      }

      /** @deprecated */
      public static IdentityAssertionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(xis, IdentityAssertionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IdentityAssertionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IdentityAssertionType)XmlBeans.getContextTypeLoader().parse(xis, IdentityAssertionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IdentityAssertionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IdentityAssertionType.type, options);
      }

      private Factory() {
      }
   }
}
