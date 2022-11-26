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

public interface DerbyDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DerbyDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("derbydictionarytype117ftype");

   boolean getShutdownOnClose();

   XmlBoolean xgetShutdownOnClose();

   boolean isSetShutdownOnClose();

   void setShutdownOnClose(boolean var1);

   void xsetShutdownOnClose(XmlBoolean var1);

   void unsetShutdownOnClose();

   public static final class Factory {
      public static DerbyDictionaryType newInstance() {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().newInstance(DerbyDictionaryType.type, (XmlOptions)null);
      }

      public static DerbyDictionaryType newInstance(XmlOptions options) {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().newInstance(DerbyDictionaryType.type, options);
      }

      public static DerbyDictionaryType parse(String xmlAsString) throws XmlException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DerbyDictionaryType.type, (XmlOptions)null);
      }

      public static DerbyDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DerbyDictionaryType.type, options);
      }

      public static DerbyDictionaryType parse(File file) throws XmlException, IOException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(file, DerbyDictionaryType.type, (XmlOptions)null);
      }

      public static DerbyDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(file, DerbyDictionaryType.type, options);
      }

      public static DerbyDictionaryType parse(URL u) throws XmlException, IOException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(u, DerbyDictionaryType.type, (XmlOptions)null);
      }

      public static DerbyDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(u, DerbyDictionaryType.type, options);
      }

      public static DerbyDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(is, DerbyDictionaryType.type, (XmlOptions)null);
      }

      public static DerbyDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(is, DerbyDictionaryType.type, options);
      }

      public static DerbyDictionaryType parse(Reader r) throws XmlException, IOException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(r, DerbyDictionaryType.type, (XmlOptions)null);
      }

      public static DerbyDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(r, DerbyDictionaryType.type, options);
      }

      public static DerbyDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, DerbyDictionaryType.type, (XmlOptions)null);
      }

      public static DerbyDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, DerbyDictionaryType.type, options);
      }

      public static DerbyDictionaryType parse(Node node) throws XmlException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(node, DerbyDictionaryType.type, (XmlOptions)null);
      }

      public static DerbyDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(node, DerbyDictionaryType.type, options);
      }

      /** @deprecated */
      public static DerbyDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, DerbyDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DerbyDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DerbyDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, DerbyDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DerbyDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DerbyDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
