package com.bea.x2004.x03.wlw.externalConfig;

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

public interface EnumDefinitionBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EnumDefinitionBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("enumdefinitionbeane984type");

   String getEnumClassName();

   XmlString xgetEnumClassName();

   void setEnumClassName(String var1);

   void xsetEnumClassName(XmlString var1);

   String[] getEnumValue1Array();

   String getEnumValue1Array(int var1);

   XmlString[] xgetEnumValue1Array();

   XmlString xgetEnumValue1Array(int var1);

   int sizeOfEnumValue1Array();

   void setEnumValue1Array(String[] var1);

   void setEnumValue1Array(int var1, String var2);

   void xsetEnumValue1Array(XmlString[] var1);

   void xsetEnumValue1Array(int var1, XmlString var2);

   void insertEnumValue1(int var1, String var2);

   void addEnumValue1(String var1);

   XmlString insertNewEnumValue1(int var1);

   XmlString addNewEnumValue1();

   void removeEnumValue1(int var1);

   public static final class Factory {
      public static EnumDefinitionBean newInstance() {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().newInstance(EnumDefinitionBean.type, (XmlOptions)null);
      }

      public static EnumDefinitionBean newInstance(XmlOptions options) {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().newInstance(EnumDefinitionBean.type, options);
      }

      public static EnumDefinitionBean parse(String xmlAsString) throws XmlException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnumDefinitionBean.type, (XmlOptions)null);
      }

      public static EnumDefinitionBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnumDefinitionBean.type, options);
      }

      public static EnumDefinitionBean parse(File file) throws XmlException, IOException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(file, EnumDefinitionBean.type, (XmlOptions)null);
      }

      public static EnumDefinitionBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(file, EnumDefinitionBean.type, options);
      }

      public static EnumDefinitionBean parse(URL u) throws XmlException, IOException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(u, EnumDefinitionBean.type, (XmlOptions)null);
      }

      public static EnumDefinitionBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(u, EnumDefinitionBean.type, options);
      }

      public static EnumDefinitionBean parse(InputStream is) throws XmlException, IOException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(is, EnumDefinitionBean.type, (XmlOptions)null);
      }

      public static EnumDefinitionBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(is, EnumDefinitionBean.type, options);
      }

      public static EnumDefinitionBean parse(Reader r) throws XmlException, IOException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(r, EnumDefinitionBean.type, (XmlOptions)null);
      }

      public static EnumDefinitionBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(r, EnumDefinitionBean.type, options);
      }

      public static EnumDefinitionBean parse(XMLStreamReader sr) throws XmlException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(sr, EnumDefinitionBean.type, (XmlOptions)null);
      }

      public static EnumDefinitionBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(sr, EnumDefinitionBean.type, options);
      }

      public static EnumDefinitionBean parse(Node node) throws XmlException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(node, EnumDefinitionBean.type, (XmlOptions)null);
      }

      public static EnumDefinitionBean parse(Node node, XmlOptions options) throws XmlException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(node, EnumDefinitionBean.type, options);
      }

      /** @deprecated */
      public static EnumDefinitionBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(xis, EnumDefinitionBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EnumDefinitionBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EnumDefinitionBean)XmlBeans.getContextTypeLoader().parse(xis, EnumDefinitionBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnumDefinitionBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnumDefinitionBean.type, options);
      }

      private Factory() {
      }
   }
}
