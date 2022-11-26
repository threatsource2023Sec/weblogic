package com.bea.x2004.x03.wlw.externalConfig;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface ArrayMemberBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ArrayMemberBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("arraymemberbeanf465type");

   String getMemberName();

   XmlString xgetMemberName();

   void setMemberName(String var1);

   void xsetMemberName(XmlString var1);

   String[] getMemberValueArray();

   String getMemberValueArray(int var1);

   XmlString[] xgetMemberValueArray();

   XmlString xgetMemberValueArray(int var1);

   int sizeOfMemberValueArray();

   void setMemberValueArray(String[] var1);

   void setMemberValueArray(int var1, String var2);

   void xsetMemberValueArray(XmlString[] var1);

   void xsetMemberValueArray(int var1, XmlString var2);

   void insertMemberValue(int var1, String var2);

   void addMemberValue(String var1);

   XmlString insertNewMemberValue(int var1);

   XmlString addNewMemberValue();

   void removeMemberValue(int var1);

   String[] getOverrideValueArray();

   String getOverrideValueArray(int var1);

   XmlString[] xgetOverrideValueArray();

   XmlString xgetOverrideValueArray(int var1);

   int sizeOfOverrideValueArray();

   void setOverrideValueArray(String[] var1);

   void setOverrideValueArray(int var1, String var2);

   void xsetOverrideValueArray(XmlString[] var1);

   void xsetOverrideValueArray(int var1, XmlString var2);

   void insertOverrideValue(int var1, String var2);

   void addOverrideValue(String var1);

   XmlString insertNewOverrideValue(int var1);

   XmlString addNewOverrideValue();

   void removeOverrideValue(int var1);

   boolean getRequiresEncryption();

   XmlBoolean xgetRequiresEncryption();

   boolean isSetRequiresEncryption();

   void setRequiresEncryption(boolean var1);

   void xsetRequiresEncryption(XmlBoolean var1);

   void unsetRequiresEncryption();

   String[] getCleartextOverrideValueArray();

   String getCleartextOverrideValueArray(int var1);

   XmlString[] xgetCleartextOverrideValueArray();

   XmlString xgetCleartextOverrideValueArray(int var1);

   int sizeOfCleartextOverrideValueArray();

   void setCleartextOverrideValueArray(String[] var1);

   void setCleartextOverrideValueArray(int var1, String var2);

   void xsetCleartextOverrideValueArray(XmlString[] var1);

   void xsetCleartextOverrideValueArray(int var1, XmlString var2);

   void insertCleartextOverrideValue(int var1, String var2);

   void addCleartextOverrideValue(String var1);

   XmlString insertNewCleartextOverrideValue(int var1);

   XmlString addNewCleartextOverrideValue();

   void removeCleartextOverrideValue(int var1);

   String getSecuredOverrideValueEncrypted();

   XmlString xgetSecuredOverrideValueEncrypted();

   boolean isSetSecuredOverrideValueEncrypted();

   void setSecuredOverrideValueEncrypted(String var1);

   void xsetSecuredOverrideValueEncrypted(XmlString var1);

   void unsetSecuredOverrideValueEncrypted();

   public static final class Factory {
      public static ArrayMemberBean newInstance() {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().newInstance(ArrayMemberBean.type, (XmlOptions)null);
      }

      public static ArrayMemberBean newInstance(XmlOptions options) {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().newInstance(ArrayMemberBean.type, options);
      }

      public static ArrayMemberBean parse(String xmlAsString) throws XmlException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, ArrayMemberBean.type, (XmlOptions)null);
      }

      public static ArrayMemberBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, ArrayMemberBean.type, options);
      }

      public static ArrayMemberBean parse(File file) throws XmlException, IOException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(file, ArrayMemberBean.type, (XmlOptions)null);
      }

      public static ArrayMemberBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(file, ArrayMemberBean.type, options);
      }

      public static ArrayMemberBean parse(URL u) throws XmlException, IOException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(u, ArrayMemberBean.type, (XmlOptions)null);
      }

      public static ArrayMemberBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(u, ArrayMemberBean.type, options);
      }

      public static ArrayMemberBean parse(InputStream is) throws XmlException, IOException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(is, ArrayMemberBean.type, (XmlOptions)null);
      }

      public static ArrayMemberBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(is, ArrayMemberBean.type, options);
      }

      public static ArrayMemberBean parse(Reader r) throws XmlException, IOException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(r, ArrayMemberBean.type, (XmlOptions)null);
      }

      public static ArrayMemberBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(r, ArrayMemberBean.type, options);
      }

      public static ArrayMemberBean parse(XMLStreamReader sr) throws XmlException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(sr, ArrayMemberBean.type, (XmlOptions)null);
      }

      public static ArrayMemberBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(sr, ArrayMemberBean.type, options);
      }

      public static ArrayMemberBean parse(Node node) throws XmlException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(node, ArrayMemberBean.type, (XmlOptions)null);
      }

      public static ArrayMemberBean parse(Node node, XmlOptions options) throws XmlException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(node, ArrayMemberBean.type, options);
      }

      /** @deprecated */
      public static ArrayMemberBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(xis, ArrayMemberBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ArrayMemberBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ArrayMemberBean)XmlBeans.getContextTypeLoader().parse(xis, ArrayMemberBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ArrayMemberBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ArrayMemberBean.type, options);
      }

      private Factory() {
      }
   }
}
