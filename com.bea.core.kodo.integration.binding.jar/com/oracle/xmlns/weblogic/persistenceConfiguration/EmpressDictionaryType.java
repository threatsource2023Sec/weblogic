package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface EmpressDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EmpressDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("empressdictionarytypef472type");

   boolean getAllowConcurrentRead();

   XmlBoolean xgetAllowConcurrentRead();

   boolean isSetAllowConcurrentRead();

   void setAllowConcurrentRead(boolean var1);

   void xsetAllowConcurrentRead(XmlBoolean var1);

   void unsetAllowConcurrentRead();

   public static final class Factory {
      public static EmpressDictionaryType newInstance() {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().newInstance(EmpressDictionaryType.type, (XmlOptions)null);
      }

      public static EmpressDictionaryType newInstance(XmlOptions options) {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().newInstance(EmpressDictionaryType.type, options);
      }

      public static EmpressDictionaryType parse(String xmlAsString) throws XmlException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EmpressDictionaryType.type, (XmlOptions)null);
      }

      public static EmpressDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EmpressDictionaryType.type, options);
      }

      public static EmpressDictionaryType parse(File file) throws XmlException, IOException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(file, EmpressDictionaryType.type, (XmlOptions)null);
      }

      public static EmpressDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(file, EmpressDictionaryType.type, options);
      }

      public static EmpressDictionaryType parse(URL u) throws XmlException, IOException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(u, EmpressDictionaryType.type, (XmlOptions)null);
      }

      public static EmpressDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(u, EmpressDictionaryType.type, options);
      }

      public static EmpressDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(is, EmpressDictionaryType.type, (XmlOptions)null);
      }

      public static EmpressDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(is, EmpressDictionaryType.type, options);
      }

      public static EmpressDictionaryType parse(Reader r) throws XmlException, IOException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(r, EmpressDictionaryType.type, (XmlOptions)null);
      }

      public static EmpressDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(r, EmpressDictionaryType.type, options);
      }

      public static EmpressDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, EmpressDictionaryType.type, (XmlOptions)null);
      }

      public static EmpressDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, EmpressDictionaryType.type, options);
      }

      public static EmpressDictionaryType parse(Node node) throws XmlException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(node, EmpressDictionaryType.type, (XmlOptions)null);
      }

      public static EmpressDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(node, EmpressDictionaryType.type, options);
      }

      /** @deprecated */
      public static EmpressDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, EmpressDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EmpressDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EmpressDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, EmpressDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EmpressDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EmpressDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
