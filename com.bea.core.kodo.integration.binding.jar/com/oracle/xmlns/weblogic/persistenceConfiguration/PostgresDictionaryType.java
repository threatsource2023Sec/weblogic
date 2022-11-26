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

public interface PostgresDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PostgresDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("postgresdictionarytype3742type");

   String getAllSequencesSql();

   XmlString xgetAllSequencesSql();

   boolean isNilAllSequencesSql();

   boolean isSetAllSequencesSql();

   void setAllSequencesSql(String var1);

   void xsetAllSequencesSql(XmlString var1);

   void setNilAllSequencesSql();

   void unsetAllSequencesSql();

   String getNamedSequencesFromAllSchemasSql();

   XmlString xgetNamedSequencesFromAllSchemasSql();

   boolean isNilNamedSequencesFromAllSchemasSql();

   boolean isSetNamedSequencesFromAllSchemasSql();

   void setNamedSequencesFromAllSchemasSql(String var1);

   void xsetNamedSequencesFromAllSchemasSql(XmlString var1);

   void setNilNamedSequencesFromAllSchemasSql();

   void unsetNamedSequencesFromAllSchemasSql();

   String getAllSequencesFromOneSchemaSql();

   XmlString xgetAllSequencesFromOneSchemaSql();

   boolean isNilAllSequencesFromOneSchemaSql();

   boolean isSetAllSequencesFromOneSchemaSql();

   void setAllSequencesFromOneSchemaSql(String var1);

   void xsetAllSequencesFromOneSchemaSql(XmlString var1);

   void setNilAllSequencesFromOneSchemaSql();

   void unsetAllSequencesFromOneSchemaSql();

   String getNamedSequencesFromOneSchemaSql();

   XmlString xgetNamedSequencesFromOneSchemaSql();

   boolean isNilNamedSequencesFromOneSchemaSql();

   boolean isSetNamedSequencesFromOneSchemaSql();

   void setNamedSequencesFromOneSchemaSql(String var1);

   void xsetNamedSequencesFromOneSchemaSql(XmlString var1);

   void setNilNamedSequencesFromOneSchemaSql();

   void unsetNamedSequencesFromOneSchemaSql();

   boolean getSupportsSetFetchSize();

   XmlBoolean xgetSupportsSetFetchSize();

   boolean isSetSupportsSetFetchSize();

   void setSupportsSetFetchSize(boolean var1);

   void xsetSupportsSetFetchSize(XmlBoolean var1);

   void unsetSupportsSetFetchSize();

   public static final class Factory {
      public static PostgresDictionaryType newInstance() {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().newInstance(PostgresDictionaryType.type, (XmlOptions)null);
      }

      public static PostgresDictionaryType newInstance(XmlOptions options) {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().newInstance(PostgresDictionaryType.type, options);
      }

      public static PostgresDictionaryType parse(String xmlAsString) throws XmlException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PostgresDictionaryType.type, (XmlOptions)null);
      }

      public static PostgresDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PostgresDictionaryType.type, options);
      }

      public static PostgresDictionaryType parse(File file) throws XmlException, IOException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(file, PostgresDictionaryType.type, (XmlOptions)null);
      }

      public static PostgresDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(file, PostgresDictionaryType.type, options);
      }

      public static PostgresDictionaryType parse(URL u) throws XmlException, IOException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(u, PostgresDictionaryType.type, (XmlOptions)null);
      }

      public static PostgresDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(u, PostgresDictionaryType.type, options);
      }

      public static PostgresDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(is, PostgresDictionaryType.type, (XmlOptions)null);
      }

      public static PostgresDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(is, PostgresDictionaryType.type, options);
      }

      public static PostgresDictionaryType parse(Reader r) throws XmlException, IOException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(r, PostgresDictionaryType.type, (XmlOptions)null);
      }

      public static PostgresDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(r, PostgresDictionaryType.type, options);
      }

      public static PostgresDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, PostgresDictionaryType.type, (XmlOptions)null);
      }

      public static PostgresDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, PostgresDictionaryType.type, options);
      }

      public static PostgresDictionaryType parse(Node node) throws XmlException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(node, PostgresDictionaryType.type, (XmlOptions)null);
      }

      public static PostgresDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(node, PostgresDictionaryType.type, options);
      }

      /** @deprecated */
      public static PostgresDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, PostgresDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PostgresDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PostgresDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, PostgresDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PostgresDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PostgresDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
