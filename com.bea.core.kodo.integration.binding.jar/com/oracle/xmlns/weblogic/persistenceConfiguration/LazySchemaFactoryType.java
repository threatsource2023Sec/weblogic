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

public interface LazySchemaFactoryType extends SchemaFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LazySchemaFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("lazyschemafactorytypec4b5type");

   boolean getForeignKeys();

   XmlBoolean xgetForeignKeys();

   boolean isSetForeignKeys();

   void setForeignKeys(boolean var1);

   void xsetForeignKeys(XmlBoolean var1);

   void unsetForeignKeys();

   boolean getIndexes();

   XmlBoolean xgetIndexes();

   boolean isSetIndexes();

   void setIndexes(boolean var1);

   void xsetIndexes(XmlBoolean var1);

   void unsetIndexes();

   boolean getPrimaryKeys();

   XmlBoolean xgetPrimaryKeys();

   boolean isSetPrimaryKeys();

   void setPrimaryKeys(boolean var1);

   void xsetPrimaryKeys(XmlBoolean var1);

   void unsetPrimaryKeys();

   public static final class Factory {
      public static LazySchemaFactoryType newInstance() {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(LazySchemaFactoryType.type, (XmlOptions)null);
      }

      public static LazySchemaFactoryType newInstance(XmlOptions options) {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(LazySchemaFactoryType.type, options);
      }

      public static LazySchemaFactoryType parse(String xmlAsString) throws XmlException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LazySchemaFactoryType.type, (XmlOptions)null);
      }

      public static LazySchemaFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LazySchemaFactoryType.type, options);
      }

      public static LazySchemaFactoryType parse(File file) throws XmlException, IOException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, LazySchemaFactoryType.type, (XmlOptions)null);
      }

      public static LazySchemaFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, LazySchemaFactoryType.type, options);
      }

      public static LazySchemaFactoryType parse(URL u) throws XmlException, IOException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, LazySchemaFactoryType.type, (XmlOptions)null);
      }

      public static LazySchemaFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, LazySchemaFactoryType.type, options);
      }

      public static LazySchemaFactoryType parse(InputStream is) throws XmlException, IOException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, LazySchemaFactoryType.type, (XmlOptions)null);
      }

      public static LazySchemaFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, LazySchemaFactoryType.type, options);
      }

      public static LazySchemaFactoryType parse(Reader r) throws XmlException, IOException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, LazySchemaFactoryType.type, (XmlOptions)null);
      }

      public static LazySchemaFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, LazySchemaFactoryType.type, options);
      }

      public static LazySchemaFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, LazySchemaFactoryType.type, (XmlOptions)null);
      }

      public static LazySchemaFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, LazySchemaFactoryType.type, options);
      }

      public static LazySchemaFactoryType parse(Node node) throws XmlException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, LazySchemaFactoryType.type, (XmlOptions)null);
      }

      public static LazySchemaFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, LazySchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static LazySchemaFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, LazySchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LazySchemaFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LazySchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, LazySchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LazySchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LazySchemaFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
