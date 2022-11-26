package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
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

public interface MysqlDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MysqlDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("mysqldictionarytype6179type");

   String getTableType();

   XmlString xgetTableType();

   boolean isNilTableType();

   boolean isSetTableType();

   void setTableType(String var1);

   void xsetTableType(XmlString var1);

   void setNilTableType();

   void unsetTableType();

   boolean getUseClobs();

   XmlBoolean xgetUseClobs();

   boolean isSetUseClobs();

   void setUseClobs(boolean var1);

   void xsetUseClobs(XmlBoolean var1);

   void unsetUseClobs();

   boolean getDriverDeserializesBlobs();

   XmlBoolean xgetDriverDeserializesBlobs();

   boolean isSetDriverDeserializesBlobs();

   void setDriverDeserializesBlobs(boolean var1);

   void xsetDriverDeserializesBlobs(XmlBoolean var1);

   void unsetDriverDeserializesBlobs();

   public static final class Factory {
      public static MysqlDictionaryType newInstance() {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().newInstance(MysqlDictionaryType.type, (XmlOptions)null);
      }

      public static MysqlDictionaryType newInstance(XmlOptions options) {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().newInstance(MysqlDictionaryType.type, options);
      }

      public static MysqlDictionaryType parse(String xmlAsString) throws XmlException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MysqlDictionaryType.type, (XmlOptions)null);
      }

      public static MysqlDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MysqlDictionaryType.type, options);
      }

      public static MysqlDictionaryType parse(File file) throws XmlException, IOException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(file, MysqlDictionaryType.type, (XmlOptions)null);
      }

      public static MysqlDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(file, MysqlDictionaryType.type, options);
      }

      public static MysqlDictionaryType parse(URL u) throws XmlException, IOException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(u, MysqlDictionaryType.type, (XmlOptions)null);
      }

      public static MysqlDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(u, MysqlDictionaryType.type, options);
      }

      public static MysqlDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(is, MysqlDictionaryType.type, (XmlOptions)null);
      }

      public static MysqlDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(is, MysqlDictionaryType.type, options);
      }

      public static MysqlDictionaryType parse(Reader r) throws XmlException, IOException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(r, MysqlDictionaryType.type, (XmlOptions)null);
      }

      public static MysqlDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(r, MysqlDictionaryType.type, options);
      }

      public static MysqlDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, MysqlDictionaryType.type, (XmlOptions)null);
      }

      public static MysqlDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, MysqlDictionaryType.type, options);
      }

      public static MysqlDictionaryType parse(Node node) throws XmlException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(node, MysqlDictionaryType.type, (XmlOptions)null);
      }

      public static MysqlDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(node, MysqlDictionaryType.type, options);
      }

      /** @deprecated */
      public static MysqlDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, MysqlDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MysqlDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MysqlDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, MysqlDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MysqlDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MysqlDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
