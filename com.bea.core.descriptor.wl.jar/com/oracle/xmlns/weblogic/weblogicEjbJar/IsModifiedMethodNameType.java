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

public interface IsModifiedMethodNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(IsModifiedMethodNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("ismodifiedmethodnametypefffatype");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static IsModifiedMethodNameType newInstance() {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().newInstance(IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      public static IsModifiedMethodNameType newInstance(XmlOptions options) {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().newInstance(IsModifiedMethodNameType.type, options);
      }

      public static IsModifiedMethodNameType parse(String xmlAsString) throws XmlException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      public static IsModifiedMethodNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, IsModifiedMethodNameType.type, options);
      }

      public static IsModifiedMethodNameType parse(File file) throws XmlException, IOException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(file, IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      public static IsModifiedMethodNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(file, IsModifiedMethodNameType.type, options);
      }

      public static IsModifiedMethodNameType parse(URL u) throws XmlException, IOException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(u, IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      public static IsModifiedMethodNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(u, IsModifiedMethodNameType.type, options);
      }

      public static IsModifiedMethodNameType parse(InputStream is) throws XmlException, IOException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(is, IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      public static IsModifiedMethodNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(is, IsModifiedMethodNameType.type, options);
      }

      public static IsModifiedMethodNameType parse(Reader r) throws XmlException, IOException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(r, IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      public static IsModifiedMethodNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(r, IsModifiedMethodNameType.type, options);
      }

      public static IsModifiedMethodNameType parse(XMLStreamReader sr) throws XmlException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(sr, IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      public static IsModifiedMethodNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(sr, IsModifiedMethodNameType.type, options);
      }

      public static IsModifiedMethodNameType parse(Node node) throws XmlException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(node, IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      public static IsModifiedMethodNameType parse(Node node, XmlOptions options) throws XmlException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(node, IsModifiedMethodNameType.type, options);
      }

      /** @deprecated */
      public static IsModifiedMethodNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(xis, IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static IsModifiedMethodNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (IsModifiedMethodNameType)XmlBeans.getContextTypeLoader().parse(xis, IsModifiedMethodNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IsModifiedMethodNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, IsModifiedMethodNameType.type, options);
      }

      private Factory() {
      }
   }
}
