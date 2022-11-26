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

public interface FieldMapType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FieldMapType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("fieldmaptypeb306type");

   CmpFieldType getCmpField();

   void setCmpField(CmpFieldType var1);

   CmpFieldType addNewCmpField();

   DbmsColumnType getDbmsColumn();

   void setDbmsColumn(DbmsColumnType var1);

   DbmsColumnType addNewDbmsColumn();

   DbmsColumnTypeType getDbmsColumnType();

   boolean isSetDbmsColumnType();

   void setDbmsColumnType(DbmsColumnTypeType var1);

   DbmsColumnTypeType addNewDbmsColumnType();

   void unsetDbmsColumnType();

   TrueFalseType getDbmsDefaultValue();

   boolean isSetDbmsDefaultValue();

   void setDbmsDefaultValue(TrueFalseType var1);

   TrueFalseType addNewDbmsDefaultValue();

   void unsetDbmsDefaultValue();

   GroupNameType getGroupName();

   boolean isSetGroupName();

   void setGroupName(GroupNameType var1);

   GroupNameType addNewGroupName();

   void unsetGroupName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static FieldMapType newInstance() {
         return (FieldMapType)XmlBeans.getContextTypeLoader().newInstance(FieldMapType.type, (XmlOptions)null);
      }

      public static FieldMapType newInstance(XmlOptions options) {
         return (FieldMapType)XmlBeans.getContextTypeLoader().newInstance(FieldMapType.type, options);
      }

      public static FieldMapType parse(String xmlAsString) throws XmlException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FieldMapType.type, (XmlOptions)null);
      }

      public static FieldMapType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FieldMapType.type, options);
      }

      public static FieldMapType parse(File file) throws XmlException, IOException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(file, FieldMapType.type, (XmlOptions)null);
      }

      public static FieldMapType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(file, FieldMapType.type, options);
      }

      public static FieldMapType parse(URL u) throws XmlException, IOException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(u, FieldMapType.type, (XmlOptions)null);
      }

      public static FieldMapType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(u, FieldMapType.type, options);
      }

      public static FieldMapType parse(InputStream is) throws XmlException, IOException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(is, FieldMapType.type, (XmlOptions)null);
      }

      public static FieldMapType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(is, FieldMapType.type, options);
      }

      public static FieldMapType parse(Reader r) throws XmlException, IOException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(r, FieldMapType.type, (XmlOptions)null);
      }

      public static FieldMapType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(r, FieldMapType.type, options);
      }

      public static FieldMapType parse(XMLStreamReader sr) throws XmlException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(sr, FieldMapType.type, (XmlOptions)null);
      }

      public static FieldMapType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(sr, FieldMapType.type, options);
      }

      public static FieldMapType parse(Node node) throws XmlException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(node, FieldMapType.type, (XmlOptions)null);
      }

      public static FieldMapType parse(Node node, XmlOptions options) throws XmlException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(node, FieldMapType.type, options);
      }

      /** @deprecated */
      public static FieldMapType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(xis, FieldMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FieldMapType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FieldMapType)XmlBeans.getContextTypeLoader().parse(xis, FieldMapType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FieldMapType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FieldMapType.type, options);
      }

      private Factory() {
      }
   }
}
