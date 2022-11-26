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

public interface EnumRefBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EnumRefBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("enumrefbean139etype");

   String getEnumClassName();

   XmlString xgetEnumClassName();

   void setEnumClassName(String var1);

   void xsetEnumClassName(XmlString var1);

   String[] getDefaultValueArray();

   String getDefaultValueArray(int var1);

   XmlString[] xgetDefaultValueArray();

   XmlString xgetDefaultValueArray(int var1);

   int sizeOfDefaultValueArray();

   void setDefaultValueArray(String[] var1);

   void setDefaultValueArray(int var1, String var2);

   void xsetDefaultValueArray(XmlString[] var1);

   void xsetDefaultValueArray(int var1, XmlString var2);

   void insertDefaultValue(int var1, String var2);

   void addDefaultValue(String var1);

   XmlString insertNewDefaultValue(int var1);

   XmlString addNewDefaultValue();

   void removeDefaultValue(int var1);

   public static final class Factory {
      public static EnumRefBean newInstance() {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().newInstance(EnumRefBean.type, (XmlOptions)null);
      }

      public static EnumRefBean newInstance(XmlOptions options) {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().newInstance(EnumRefBean.type, options);
      }

      public static EnumRefBean parse(String xmlAsString) throws XmlException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnumRefBean.type, (XmlOptions)null);
      }

      public static EnumRefBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnumRefBean.type, options);
      }

      public static EnumRefBean parse(File file) throws XmlException, IOException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(file, EnumRefBean.type, (XmlOptions)null);
      }

      public static EnumRefBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(file, EnumRefBean.type, options);
      }

      public static EnumRefBean parse(URL u) throws XmlException, IOException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(u, EnumRefBean.type, (XmlOptions)null);
      }

      public static EnumRefBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(u, EnumRefBean.type, options);
      }

      public static EnumRefBean parse(InputStream is) throws XmlException, IOException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(is, EnumRefBean.type, (XmlOptions)null);
      }

      public static EnumRefBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(is, EnumRefBean.type, options);
      }

      public static EnumRefBean parse(Reader r) throws XmlException, IOException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(r, EnumRefBean.type, (XmlOptions)null);
      }

      public static EnumRefBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(r, EnumRefBean.type, options);
      }

      public static EnumRefBean parse(XMLStreamReader sr) throws XmlException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(sr, EnumRefBean.type, (XmlOptions)null);
      }

      public static EnumRefBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(sr, EnumRefBean.type, options);
      }

      public static EnumRefBean parse(Node node) throws XmlException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(node, EnumRefBean.type, (XmlOptions)null);
      }

      public static EnumRefBean parse(Node node, XmlOptions options) throws XmlException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(node, EnumRefBean.type, options);
      }

      /** @deprecated */
      public static EnumRefBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(xis, EnumRefBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EnumRefBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EnumRefBean)XmlBeans.getContextTypeLoader().parse(xis, EnumRefBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnumRefBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnumRefBean.type, options);
      }

      private Factory() {
      }
   }
}
