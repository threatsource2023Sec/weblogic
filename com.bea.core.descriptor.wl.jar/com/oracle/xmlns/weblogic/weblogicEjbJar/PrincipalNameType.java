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

public interface PrincipalNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PrincipalNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("principalnametypeffc0type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PrincipalNameType newInstance() {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(PrincipalNameType.type, (XmlOptions)null);
      }

      public static PrincipalNameType newInstance(XmlOptions options) {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(PrincipalNameType.type, options);
      }

      public static PrincipalNameType parse(String xmlAsString) throws XmlException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PrincipalNameType.type, (XmlOptions)null);
      }

      public static PrincipalNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PrincipalNameType.type, options);
      }

      public static PrincipalNameType parse(File file) throws XmlException, IOException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, PrincipalNameType.type, (XmlOptions)null);
      }

      public static PrincipalNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, PrincipalNameType.type, options);
      }

      public static PrincipalNameType parse(URL u) throws XmlException, IOException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, PrincipalNameType.type, (XmlOptions)null);
      }

      public static PrincipalNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, PrincipalNameType.type, options);
      }

      public static PrincipalNameType parse(InputStream is) throws XmlException, IOException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, PrincipalNameType.type, (XmlOptions)null);
      }

      public static PrincipalNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, PrincipalNameType.type, options);
      }

      public static PrincipalNameType parse(Reader r) throws XmlException, IOException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, PrincipalNameType.type, (XmlOptions)null);
      }

      public static PrincipalNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, PrincipalNameType.type, options);
      }

      public static PrincipalNameType parse(XMLStreamReader sr) throws XmlException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, PrincipalNameType.type, (XmlOptions)null);
      }

      public static PrincipalNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, PrincipalNameType.type, options);
      }

      public static PrincipalNameType parse(Node node) throws XmlException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, PrincipalNameType.type, (XmlOptions)null);
      }

      public static PrincipalNameType parse(Node node, XmlOptions options) throws XmlException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, PrincipalNameType.type, options);
      }

      /** @deprecated */
      public static PrincipalNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, PrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PrincipalNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, PrincipalNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PrincipalNameType.type, options);
      }

      private Factory() {
      }
   }
}
