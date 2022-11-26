package com.oracle.xmlns.weblogic.persistenceConfiguration;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
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

public interface FileSchemaFactoryType extends SchemaFactoryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FileSchemaFactoryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("fileschemafactorytypeb8bdtype");

   String getFileName();

   XmlString xgetFileName();

   boolean isNilFileName();

   boolean isSetFileName();

   void setFileName(String var1);

   void xsetFileName(XmlString var1);

   void setNilFileName();

   void unsetFileName();

   String getFile();

   XmlString xgetFile();

   boolean isNilFile();

   boolean isSetFile();

   void setFile(String var1);

   void xsetFile(XmlString var1);

   void setNilFile();

   void unsetFile();

   public static final class Factory {
      public static FileSchemaFactoryType newInstance() {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(FileSchemaFactoryType.type, (XmlOptions)null);
      }

      public static FileSchemaFactoryType newInstance(XmlOptions options) {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().newInstance(FileSchemaFactoryType.type, options);
      }

      public static FileSchemaFactoryType parse(String xmlAsString) throws XmlException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FileSchemaFactoryType.type, (XmlOptions)null);
      }

      public static FileSchemaFactoryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, FileSchemaFactoryType.type, options);
      }

      public static FileSchemaFactoryType parse(File file) throws XmlException, IOException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, FileSchemaFactoryType.type, (XmlOptions)null);
      }

      public static FileSchemaFactoryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(file, FileSchemaFactoryType.type, options);
      }

      public static FileSchemaFactoryType parse(URL u) throws XmlException, IOException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, FileSchemaFactoryType.type, (XmlOptions)null);
      }

      public static FileSchemaFactoryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(u, FileSchemaFactoryType.type, options);
      }

      public static FileSchemaFactoryType parse(InputStream is) throws XmlException, IOException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, FileSchemaFactoryType.type, (XmlOptions)null);
      }

      public static FileSchemaFactoryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(is, FileSchemaFactoryType.type, options);
      }

      public static FileSchemaFactoryType parse(Reader r) throws XmlException, IOException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, FileSchemaFactoryType.type, (XmlOptions)null);
      }

      public static FileSchemaFactoryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(r, FileSchemaFactoryType.type, options);
      }

      public static FileSchemaFactoryType parse(XMLStreamReader sr) throws XmlException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, FileSchemaFactoryType.type, (XmlOptions)null);
      }

      public static FileSchemaFactoryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(sr, FileSchemaFactoryType.type, options);
      }

      public static FileSchemaFactoryType parse(Node node) throws XmlException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, FileSchemaFactoryType.type, (XmlOptions)null);
      }

      public static FileSchemaFactoryType parse(Node node, XmlOptions options) throws XmlException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(node, FileSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static FileSchemaFactoryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, FileSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FileSchemaFactoryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FileSchemaFactoryType)XmlBeans.getContextTypeLoader().parse(xis, FileSchemaFactoryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FileSchemaFactoryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FileSchemaFactoryType.type, options);
      }

      private Factory() {
      }
   }
}
