package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface InformixDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InformixDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("informixdictionarytypef761type");

   boolean getLockModeEnabled();

   XmlBoolean xgetLockModeEnabled();

   boolean isSetLockModeEnabled();

   void setLockModeEnabled(boolean var1);

   void xsetLockModeEnabled(XmlBoolean var1);

   void unsetLockModeEnabled();

   int getLockWaitSeconds();

   XmlInt xgetLockWaitSeconds();

   boolean isSetLockWaitSeconds();

   void setLockWaitSeconds(int var1);

   void xsetLockWaitSeconds(XmlInt var1);

   void unsetLockWaitSeconds();

   boolean getSwapSchemaAndCatalog();

   XmlBoolean xgetSwapSchemaAndCatalog();

   boolean isSetSwapSchemaAndCatalog();

   void setSwapSchemaAndCatalog(boolean var1);

   void xsetSwapSchemaAndCatalog(XmlBoolean var1);

   void unsetSwapSchemaAndCatalog();

   public static final class Factory {
      public static InformixDictionaryType newInstance() {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().newInstance(InformixDictionaryType.type, (XmlOptions)null);
      }

      public static InformixDictionaryType newInstance(XmlOptions options) {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().newInstance(InformixDictionaryType.type, options);
      }

      public static InformixDictionaryType parse(String xmlAsString) throws XmlException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InformixDictionaryType.type, (XmlOptions)null);
      }

      public static InformixDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InformixDictionaryType.type, options);
      }

      public static InformixDictionaryType parse(File file) throws XmlException, IOException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(file, InformixDictionaryType.type, (XmlOptions)null);
      }

      public static InformixDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(file, InformixDictionaryType.type, options);
      }

      public static InformixDictionaryType parse(URL u) throws XmlException, IOException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(u, InformixDictionaryType.type, (XmlOptions)null);
      }

      public static InformixDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(u, InformixDictionaryType.type, options);
      }

      public static InformixDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(is, InformixDictionaryType.type, (XmlOptions)null);
      }

      public static InformixDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(is, InformixDictionaryType.type, options);
      }

      public static InformixDictionaryType parse(Reader r) throws XmlException, IOException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(r, InformixDictionaryType.type, (XmlOptions)null);
      }

      public static InformixDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(r, InformixDictionaryType.type, options);
      }

      public static InformixDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, InformixDictionaryType.type, (XmlOptions)null);
      }

      public static InformixDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, InformixDictionaryType.type, options);
      }

      public static InformixDictionaryType parse(Node node) throws XmlException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(node, InformixDictionaryType.type, (XmlOptions)null);
      }

      public static InformixDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(node, InformixDictionaryType.type, options);
      }

      /** @deprecated */
      public static InformixDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, InformixDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InformixDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InformixDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, InformixDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InformixDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InformixDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
