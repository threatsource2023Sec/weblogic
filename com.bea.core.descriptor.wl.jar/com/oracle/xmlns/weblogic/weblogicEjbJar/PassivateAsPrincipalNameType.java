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

public interface PassivateAsPrincipalNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PassivateAsPrincipalNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("passivateasprincipalnametypebb50type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PassivateAsPrincipalNameType newInstance() {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static PassivateAsPrincipalNameType newInstance(XmlOptions options) {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(PassivateAsPrincipalNameType.type, options);
      }

      public static PassivateAsPrincipalNameType parse(String xmlAsString) throws XmlException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static PassivateAsPrincipalNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PassivateAsPrincipalNameType.type, options);
      }

      public static PassivateAsPrincipalNameType parse(File file) throws XmlException, IOException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static PassivateAsPrincipalNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, PassivateAsPrincipalNameType.type, options);
      }

      public static PassivateAsPrincipalNameType parse(URL u) throws XmlException, IOException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static PassivateAsPrincipalNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, PassivateAsPrincipalNameType.type, options);
      }

      public static PassivateAsPrincipalNameType parse(InputStream is) throws XmlException, IOException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static PassivateAsPrincipalNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, PassivateAsPrincipalNameType.type, options);
      }

      public static PassivateAsPrincipalNameType parse(Reader r) throws XmlException, IOException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static PassivateAsPrincipalNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, PassivateAsPrincipalNameType.type, options);
      }

      public static PassivateAsPrincipalNameType parse(XMLStreamReader sr) throws XmlException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static PassivateAsPrincipalNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, PassivateAsPrincipalNameType.type, options);
      }

      public static PassivateAsPrincipalNameType parse(Node node) throws XmlException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static PassivateAsPrincipalNameType parse(Node node, XmlOptions options) throws XmlException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, PassivateAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static PassivateAsPrincipalNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PassivateAsPrincipalNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PassivateAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, PassivateAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PassivateAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PassivateAsPrincipalNameType.type, options);
      }

      private Factory() {
      }
   }
}
