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

public interface SybaseDictionaryType extends BuiltInDbdictionaryType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SybaseDictionaryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_kodo_integration_binding_3_0_0_0").resolveHandle("sybasedictionarytype86c0type");

   boolean getCreateIdentityColumn();

   XmlBoolean xgetCreateIdentityColumn();

   boolean isSetCreateIdentityColumn();

   void setCreateIdentityColumn(boolean var1);

   void xsetCreateIdentityColumn(XmlBoolean var1);

   void unsetCreateIdentityColumn();

   String getIdentityColumnName();

   XmlString xgetIdentityColumnName();

   boolean isSetIdentityColumnName();

   void setIdentityColumnName(String var1);

   void xsetIdentityColumnName(XmlString var1);

   void unsetIdentityColumnName();

   public static final class Factory {
      public static SybaseDictionaryType newInstance() {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().newInstance(SybaseDictionaryType.type, (XmlOptions)null);
      }

      public static SybaseDictionaryType newInstance(XmlOptions options) {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().newInstance(SybaseDictionaryType.type, options);
      }

      public static SybaseDictionaryType parse(String xmlAsString) throws XmlException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SybaseDictionaryType.type, (XmlOptions)null);
      }

      public static SybaseDictionaryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SybaseDictionaryType.type, options);
      }

      public static SybaseDictionaryType parse(File file) throws XmlException, IOException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(file, SybaseDictionaryType.type, (XmlOptions)null);
      }

      public static SybaseDictionaryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(file, SybaseDictionaryType.type, options);
      }

      public static SybaseDictionaryType parse(URL u) throws XmlException, IOException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(u, SybaseDictionaryType.type, (XmlOptions)null);
      }

      public static SybaseDictionaryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(u, SybaseDictionaryType.type, options);
      }

      public static SybaseDictionaryType parse(InputStream is) throws XmlException, IOException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(is, SybaseDictionaryType.type, (XmlOptions)null);
      }

      public static SybaseDictionaryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(is, SybaseDictionaryType.type, options);
      }

      public static SybaseDictionaryType parse(Reader r) throws XmlException, IOException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(r, SybaseDictionaryType.type, (XmlOptions)null);
      }

      public static SybaseDictionaryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(r, SybaseDictionaryType.type, options);
      }

      public static SybaseDictionaryType parse(XMLStreamReader sr) throws XmlException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, SybaseDictionaryType.type, (XmlOptions)null);
      }

      public static SybaseDictionaryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(sr, SybaseDictionaryType.type, options);
      }

      public static SybaseDictionaryType parse(Node node) throws XmlException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(node, SybaseDictionaryType.type, (XmlOptions)null);
      }

      public static SybaseDictionaryType parse(Node node, XmlOptions options) throws XmlException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(node, SybaseDictionaryType.type, options);
      }

      /** @deprecated */
      public static SybaseDictionaryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, SybaseDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SybaseDictionaryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SybaseDictionaryType)XmlBeans.getContextTypeLoader().parse(xis, SybaseDictionaryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SybaseDictionaryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SybaseDictionaryType.type, options);
      }

      private Factory() {
      }
   }
}
