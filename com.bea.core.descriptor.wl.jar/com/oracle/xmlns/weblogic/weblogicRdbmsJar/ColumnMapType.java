package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
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

public interface ColumnMapType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ColumnMapType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("columnmaptyped4d4type");

   ForeignKeyColumnType getForeignKeyColumn();

   void setForeignKeyColumn(ForeignKeyColumnType var1);

   ForeignKeyColumnType addNewForeignKeyColumn();

   KeyColumnType getKeyColumn();

   void setKeyColumn(KeyColumnType var1);

   KeyColumnType addNewKeyColumn();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ColumnMapType newInstance() {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().newInstance(ColumnMapType.type, (XmlOptions)null);
      }

      public static ColumnMapType newInstance(XmlOptions options) {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().newInstance(ColumnMapType.type, options);
      }

      public static ColumnMapType parse(String xmlAsString) throws XmlException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ColumnMapType.type, (XmlOptions)null);
      }

      public static ColumnMapType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ColumnMapType.type, options);
      }

      public static ColumnMapType parse(File file) throws XmlException, IOException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(file, ColumnMapType.type, (XmlOptions)null);
      }

      public static ColumnMapType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(file, ColumnMapType.type, options);
      }

      public static ColumnMapType parse(URL u) throws XmlException, IOException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(u, ColumnMapType.type, (XmlOptions)null);
      }

      public static ColumnMapType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(u, ColumnMapType.type, options);
      }

      public static ColumnMapType parse(InputStream is) throws XmlException, IOException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(is, ColumnMapType.type, (XmlOptions)null);
      }

      public static ColumnMapType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(is, ColumnMapType.type, options);
      }

      public static ColumnMapType parse(Reader r) throws XmlException, IOException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(r, ColumnMapType.type, (XmlOptions)null);
      }

      public static ColumnMapType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(r, ColumnMapType.type, options);
      }

      public static ColumnMapType parse(XMLStreamReader sr) throws XmlException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(sr, ColumnMapType.type, (XmlOptions)null);
      }

      public static ColumnMapType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(sr, ColumnMapType.type, options);
      }

      public static ColumnMapType parse(Node node) throws XmlException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(node, ColumnMapType.type, (XmlOptions)null);
      }

      public static ColumnMapType parse(Node node, XmlOptions options) throws XmlException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(node, ColumnMapType.type, options);
      }

      /** @deprecated */
      public static ColumnMapType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(xis, ColumnMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ColumnMapType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ColumnMapType)XmlBeans.getContextTypeLoader().parse(xis, ColumnMapType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ColumnMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ColumnMapType.type, options);
      }

      private Factory() {
      }
   }
}
