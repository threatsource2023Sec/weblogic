package com.bea.ns.weblogic.x60;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface WeblogicRdbmsJarType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WeblogicRdbmsJarType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("weblogicrdbmsjartypeb840type");

   WeblogicRdbmsBeanType[] getWeblogicRdbmsBeanArray();

   WeblogicRdbmsBeanType getWeblogicRdbmsBeanArray(int var1);

   int sizeOfWeblogicRdbmsBeanArray();

   void setWeblogicRdbmsBeanArray(WeblogicRdbmsBeanType[] var1);

   void setWeblogicRdbmsBeanArray(int var1, WeblogicRdbmsBeanType var2);

   WeblogicRdbmsBeanType insertNewWeblogicRdbmsBean(int var1);

   WeblogicRdbmsBeanType addNewWeblogicRdbmsBean();

   void removeWeblogicRdbmsBean(int var1);

   TrueFalseType getCreateDefaultDbmsTables();

   boolean isSetCreateDefaultDbmsTables();

   void setCreateDefaultDbmsTables(TrueFalseType var1);

   TrueFalseType addNewCreateDefaultDbmsTables();

   void unsetCreateDefaultDbmsTables();

   ValidateDbSchemaWithType getValidateDbSchemaWith();

   boolean isSetValidateDbSchemaWith();

   void setValidateDbSchemaWith(ValidateDbSchemaWithType var1);

   ValidateDbSchemaWithType addNewValidateDbSchemaWith();

   void unsetValidateDbSchemaWith();

   DatabaseTypeType getDatabaseType();

   boolean isSetDatabaseType();

   void setDatabaseType(DatabaseTypeType var1);

   DatabaseTypeType addNewDatabaseType();

   void unsetDatabaseType();

   public static final class Factory {
      public static WeblogicRdbmsJarType newInstance() {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarType newInstance(XmlOptions options) {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().newInstance(WeblogicRdbmsJarType.type, options);
      }

      public static WeblogicRdbmsJarType parse(String xmlAsString) throws XmlException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(xmlAsString, WeblogicRdbmsJarType.type, options);
      }

      public static WeblogicRdbmsJarType parse(File file) throws XmlException, IOException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(file, WeblogicRdbmsJarType.type, options);
      }

      public static WeblogicRdbmsJarType parse(URL u) throws XmlException, IOException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(u, WeblogicRdbmsJarType.type, options);
      }

      public static WeblogicRdbmsJarType parse(InputStream is) throws XmlException, IOException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(is, WeblogicRdbmsJarType.type, options);
      }

      public static WeblogicRdbmsJarType parse(Reader r) throws XmlException, IOException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(r, WeblogicRdbmsJarType.type, options);
      }

      public static WeblogicRdbmsJarType parse(XMLStreamReader sr) throws XmlException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(sr, WeblogicRdbmsJarType.type, options);
      }

      public static WeblogicRdbmsJarType parse(Node node) throws XmlException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      public static WeblogicRdbmsJarType parse(Node node, XmlOptions options) throws XmlException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(node, WeblogicRdbmsJarType.type, options);
      }

      /** @deprecated */
      public static WeblogicRdbmsJarType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WeblogicRdbmsJarType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WeblogicRdbmsJarType)XmlBeans.getContextTypeLoader().parse(xis, WeblogicRdbmsJarType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsJarType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WeblogicRdbmsJarType.type, options);
      }

      private Factory() {
      }
   }
}
