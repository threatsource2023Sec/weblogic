package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
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

public interface SchemasType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SchemasType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("schemastypef03atype");

   String[] getSchemaArray();

   String getSchemaArray(int var1);

   XmlString[] xgetSchemaArray();

   XmlString xgetSchemaArray(int var1);

   boolean isNilSchemaArray(int var1);

   int sizeOfSchemaArray();

   void setSchemaArray(String[] var1);

   void setSchemaArray(int var1, String var2);

   void xsetSchemaArray(XmlString[] var1);

   void xsetSchemaArray(int var1, XmlString var2);

   void setNilSchemaArray(int var1);

   void insertSchema(int var1, String var2);

   void addSchema(String var1);

   XmlString insertNewSchema(int var1);

   XmlString addNewSchema();

   void removeSchema(int var1);

   public static final class Factory {
      public static SchemasType newInstance() {
         return (SchemasType)XmlBeans.getContextTypeLoader().newInstance(SchemasType.type, (XmlOptions)null);
      }

      public static SchemasType newInstance(XmlOptions options) {
         return (SchemasType)XmlBeans.getContextTypeLoader().newInstance(SchemasType.type, options);
      }

      public static SchemasType parse(String xmlAsString) throws XmlException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SchemasType.type, (XmlOptions)null);
      }

      public static SchemasType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SchemasType.type, options);
      }

      public static SchemasType parse(File file) throws XmlException, IOException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(file, SchemasType.type, (XmlOptions)null);
      }

      public static SchemasType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(file, SchemasType.type, options);
      }

      public static SchemasType parse(URL u) throws XmlException, IOException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(u, SchemasType.type, (XmlOptions)null);
      }

      public static SchemasType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(u, SchemasType.type, options);
      }

      public static SchemasType parse(InputStream is) throws XmlException, IOException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(is, SchemasType.type, (XmlOptions)null);
      }

      public static SchemasType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(is, SchemasType.type, options);
      }

      public static SchemasType parse(Reader r) throws XmlException, IOException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(r, SchemasType.type, (XmlOptions)null);
      }

      public static SchemasType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(r, SchemasType.type, options);
      }

      public static SchemasType parse(XMLStreamReader sr) throws XmlException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(sr, SchemasType.type, (XmlOptions)null);
      }

      public static SchemasType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(sr, SchemasType.type, options);
      }

      public static SchemasType parse(Node node) throws XmlException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(node, SchemasType.type, (XmlOptions)null);
      }

      public static SchemasType parse(Node node, XmlOptions options) throws XmlException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(node, SchemasType.type, options);
      }

      /** @deprecated */
      public static SchemasType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(xis, SchemasType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SchemasType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SchemasType)XmlBeans.getContextTypeLoader().parse(xis, SchemasType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SchemasType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SchemasType.type, options);
      }

      private Factory() {
      }
   }
}
