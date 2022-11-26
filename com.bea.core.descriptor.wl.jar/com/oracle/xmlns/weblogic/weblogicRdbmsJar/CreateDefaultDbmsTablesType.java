package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface CreateDefaultDbmsTablesType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CreateDefaultDbmsTablesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("createdefaultdbmstablestype9a40type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CreateDefaultDbmsTablesType newInstance() {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().newInstance(CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      public static CreateDefaultDbmsTablesType newInstance(XmlOptions options) {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().newInstance(CreateDefaultDbmsTablesType.type, options);
      }

      public static CreateDefaultDbmsTablesType parse(String xmlAsString) throws XmlException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      public static CreateDefaultDbmsTablesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CreateDefaultDbmsTablesType.type, options);
      }

      public static CreateDefaultDbmsTablesType parse(File file) throws XmlException, IOException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(file, CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      public static CreateDefaultDbmsTablesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(file, CreateDefaultDbmsTablesType.type, options);
      }

      public static CreateDefaultDbmsTablesType parse(URL u) throws XmlException, IOException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(u, CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      public static CreateDefaultDbmsTablesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(u, CreateDefaultDbmsTablesType.type, options);
      }

      public static CreateDefaultDbmsTablesType parse(InputStream is) throws XmlException, IOException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(is, CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      public static CreateDefaultDbmsTablesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(is, CreateDefaultDbmsTablesType.type, options);
      }

      public static CreateDefaultDbmsTablesType parse(Reader r) throws XmlException, IOException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(r, CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      public static CreateDefaultDbmsTablesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(r, CreateDefaultDbmsTablesType.type, options);
      }

      public static CreateDefaultDbmsTablesType parse(XMLStreamReader sr) throws XmlException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(sr, CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      public static CreateDefaultDbmsTablesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(sr, CreateDefaultDbmsTablesType.type, options);
      }

      public static CreateDefaultDbmsTablesType parse(Node node) throws XmlException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(node, CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      public static CreateDefaultDbmsTablesType parse(Node node, XmlOptions options) throws XmlException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(node, CreateDefaultDbmsTablesType.type, options);
      }

      /** @deprecated */
      public static CreateDefaultDbmsTablesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(xis, CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CreateDefaultDbmsTablesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CreateDefaultDbmsTablesType)XmlBeans.getContextTypeLoader().parse(xis, CreateDefaultDbmsTablesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CreateDefaultDbmsTablesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CreateDefaultDbmsTablesType.type, options);
      }

      private Factory() {
      }
   }
}
