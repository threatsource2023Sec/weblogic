package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface TableJdorMappingFactoryType extends MappingFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TableJdorMappingFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("tablejdormappingfactorytyped1c0type");

   boolean getUseSchemaValidation();

   XmlBoolean xgetUseSchemaValidation();

   boolean isSetUseSchemaValidation();

   void setUseSchemaValidation(boolean var1);

   void xsetUseSchemaValidation(XmlBoolean var1);

   void unsetUseSchemaValidation();

   String getTypeColumn();

   XmlString xgetTypeColumn();

   boolean isNilTypeColumn();

   boolean isSetTypeColumn();

   void setTypeColumn(String var1);

   void xsetTypeColumn(XmlString var1);

   void setNilTypeColumn();

   void unsetTypeColumn();

   boolean getConstraintNames();

   XmlBoolean xgetConstraintNames();

   boolean isSetConstraintNames();

   void setConstraintNames(boolean var1);

   void xsetConstraintNames(XmlBoolean var1);

   void unsetConstraintNames();

   String getTable();

   XmlString xgetTable();

   boolean isNilTable();

   boolean isSetTable();

   void setTable(String var1);

   void xsetTable(XmlString var1);

   void setNilTable();

   void unsetTable();

   String getTypes();

   XmlString xgetTypes();

   boolean isNilTypes();

   boolean isSetTypes();

   void setTypes(String var1);

   void xsetTypes(XmlString var1);

   void setNilTypes();

   void unsetTypes();

   int getStoreMode();

   XmlInt xgetStoreMode();

   boolean isSetStoreMode();

   void setStoreMode(int var1);

   void xsetStoreMode(XmlInt var1);

   void unsetStoreMode();

   String getMappingColumn();

   XmlString xgetMappingColumn();

   boolean isNilMappingColumn();

   boolean isSetMappingColumn();

   void setMappingColumn(String var1);

   void xsetMappingColumn(XmlString var1);

   void setNilMappingColumn();

   void unsetMappingColumn();

   boolean getStrict();

   XmlBoolean xgetStrict();

   boolean isSetStrict();

   void setStrict(boolean var1);

   void xsetStrict(XmlBoolean var1);

   void unsetStrict();

   String getNameColumn();

   XmlString xgetNameColumn();

   boolean isNilNameColumn();

   boolean isSetNameColumn();

   void setNameColumn(String var1);

   void xsetNameColumn(XmlString var1);

   void setNilNameColumn();

   void unsetNameColumn();

   public static final class Factory {
      public static TableJdorMappingFactoryType newInstance() {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableJdorMappingFactoryType newInstance(XmlOptions options) {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().newInstance(TableJdorMappingFactoryType.type, options);
      }

      public static TableJdorMappingFactoryType parse(String xmlAsString) throws XmlException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableJdorMappingFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TableJdorMappingFactoryType.type, options);
      }

      public static TableJdorMappingFactoryType parse(File file) throws XmlException, IOException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableJdorMappingFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(file, TableJdorMappingFactoryType.type, options);
      }

      public static TableJdorMappingFactoryType parse(URL u) throws XmlException, IOException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableJdorMappingFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(u, TableJdorMappingFactoryType.type, options);
      }

      public static TableJdorMappingFactoryType parse(InputStream is) throws XmlException, IOException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableJdorMappingFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(is, TableJdorMappingFactoryType.type, options);
      }

      public static TableJdorMappingFactoryType parse(Reader r) throws XmlException, IOException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableJdorMappingFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(r, TableJdorMappingFactoryType.type, options);
      }

      public static TableJdorMappingFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableJdorMappingFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(sr, TableJdorMappingFactoryType.type, options);
      }

      public static TableJdorMappingFactoryType parse(Node node) throws XmlException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      public static TableJdorMappingFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(node, TableJdorMappingFactoryType.type, options);
      }

      /** @deprecated */
      public static TableJdorMappingFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TableJdorMappingFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TableJdorMappingFactoryType)XmlBeans.getContextTypeLoader().parse(xis, TableJdorMappingFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableJdorMappingFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TableJdorMappingFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
