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

public interface RemoveAsPrincipalNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RemoveAsPrincipalNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("removeasprincipalnametypedcf2type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RemoveAsPrincipalNameType newInstance() {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RemoveAsPrincipalNameType newInstance(XmlOptions options) {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().newInstance(RemoveAsPrincipalNameType.type, options);
      }

      public static RemoveAsPrincipalNameType parse(String xmlAsString) throws XmlException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RemoveAsPrincipalNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RemoveAsPrincipalNameType.type, options);
      }

      public static RemoveAsPrincipalNameType parse(File file) throws XmlException, IOException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RemoveAsPrincipalNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(file, RemoveAsPrincipalNameType.type, options);
      }

      public static RemoveAsPrincipalNameType parse(URL u) throws XmlException, IOException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RemoveAsPrincipalNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(u, RemoveAsPrincipalNameType.type, options);
      }

      public static RemoveAsPrincipalNameType parse(InputStream is) throws XmlException, IOException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RemoveAsPrincipalNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(is, RemoveAsPrincipalNameType.type, options);
      }

      public static RemoveAsPrincipalNameType parse(Reader r) throws XmlException, IOException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RemoveAsPrincipalNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(r, RemoveAsPrincipalNameType.type, options);
      }

      public static RemoveAsPrincipalNameType parse(XMLStreamReader sr) throws XmlException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RemoveAsPrincipalNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(sr, RemoveAsPrincipalNameType.type, options);
      }

      public static RemoveAsPrincipalNameType parse(Node node) throws XmlException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      public static RemoveAsPrincipalNameType parse(Node node, XmlOptions options) throws XmlException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(node, RemoveAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static RemoveAsPrincipalNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RemoveAsPrincipalNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RemoveAsPrincipalNameType)XmlBeans.getContextTypeLoader().parse(xis, RemoveAsPrincipalNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RemoveAsPrincipalNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RemoveAsPrincipalNameType.type, options);
      }

      private Factory() {
      }
   }
}
