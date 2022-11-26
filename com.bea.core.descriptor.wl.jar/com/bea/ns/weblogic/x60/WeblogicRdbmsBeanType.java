package com.bea.ns.weblogic.x60;

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

public interface WeblogicRdbmsBeanType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicRdbmsBeanType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicrdbmsbeantype5d97type");

   EjbNameType getEjbName();

   void setEjbName(EjbNameType var1);

   EjbNameType addNewEjbName();

   PoolNameType getPoolName();

   boolean isSetPoolName();

   void setPoolName(PoolNameType var1);

   PoolNameType addNewPoolName();

   void unsetPoolName();

   DataSourceJndiNameType getDataSourceJndiName();

   boolean isSetDataSourceJndiName();

   void setDataSourceJndiName(DataSourceJndiNameType var1);

   DataSourceJndiNameType addNewDataSourceJndiName();

   void unsetDataSourceJndiName();

   TableNameType getTableName();

   void setTableName(TableNameType var1);

   TableNameType addNewTableName();

   FieldMapType[] getFieldMapArray();

   FieldMapType getFieldMapArray(int var1);

   int sizeOfFieldMapArray();

   void setFieldMapArray(FieldMapType[] var1);

   void setFieldMapArray(int var1, FieldMapType var2);

   FieldMapType insertNewFieldMap(int var1);

   FieldMapType addNewFieldMap();

   void removeFieldMap(int var1);

   FinderType[] getFinderArray();

   FinderType getFinderArray(int var1);

   int sizeOfFinderArray();

   void setFinderArray(FinderType[] var1);

   void setFinderArray(int var1, FinderType var2);

   FinderType insertNewFinder(int var1);

   FinderType addNewFinder();

   void removeFinder(int var1);

   TrueFalseType getEnableTunedUpdates();

   boolean isSetEnableTunedUpdates();

   void setEnableTunedUpdates(TrueFalseType var1);

   TrueFalseType addNewEnableTunedUpdates();

   void unsetEnableTunedUpdates();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static WeblogicRdbmsBeanType newInstance() {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType newInstance(XmlOptions options) {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(String xmlAsString) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(File file) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(URL u) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(Reader r) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsBeanType.type, options);
      }

      public static WeblogicRdbmsBeanType parse(Node node) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsBeanType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsBeanType.type, options);
      }

      /** @deprecated */
      public static WeblogicRdbmsBeanType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicRdbmsBeanType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsBeanType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsBeanType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsBeanType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsBeanType.type, options);
      }

      private Factory() {
      }
   }
}
