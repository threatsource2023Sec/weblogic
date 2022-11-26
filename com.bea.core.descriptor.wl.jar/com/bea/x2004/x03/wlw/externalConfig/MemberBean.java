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

public interface MemberBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MemberBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("memberbean3731type");

   String getMemberName();

   XmlString xgetMemberName();

   void setMemberName(String var1);

   void xsetMemberName(XmlString var1);

   String getMemberValue();

   XmlString xgetMemberValue();

   boolean isSetMemberValue();

   void setMemberValue(String var1);

   void xsetMemberValue(XmlString var1);

   void unsetMemberValue();

   String getOverrideValue();

   XmlString xgetOverrideValue();

   boolean isSetOverrideValue();

   void setOverrideValue(String var1);

   void xsetOverrideValue(XmlString var1);

   void unsetOverrideValue();

   boolean getRequiresEncryption();

   XmlBoolean xgetRequiresEncryption();

   boolean isSetRequiresEncryption();

   void setRequiresEncryption(boolean var1);

   void xsetRequiresEncryption(XmlBoolean var1);

   void unsetRequiresEncryption();

   String getCleartextOverrideValue();

   XmlString xgetCleartextOverrideValue();

   boolean isSetCleartextOverrideValue();

   void setCleartextOverrideValue(String var1);

   void xsetCleartextOverrideValue(XmlString var1);

   void unsetCleartextOverrideValue();

   String getSecuredOverrideValueEncrypted();

   XmlString xgetSecuredOverrideValueEncrypted();

   boolean isSetSecuredOverrideValueEncrypted();

   void setSecuredOverrideValueEncrypted(String var1);

   void xsetSecuredOverrideValueEncrypted(XmlString var1);

   void unsetSecuredOverrideValueEncrypted();

   public static final class Factory {
      public static MemberBean newInstance() {
         return (MemberBean)XmlBeans.getContextTypeLoader().newInstance(MemberBean.type, (XmlOptions)null);
      }

      public static MemberBean newInstance(XmlOptions options) {
         return (MemberBean)XmlBeans.getContextTypeLoader().newInstance(MemberBean.type, options);
      }

      public static MemberBean parse(String xmlAsString) throws XmlException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, MemberBean.type, (XmlOptions)null);
      }

      public static MemberBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, MemberBean.type, options);
      }

      public static MemberBean parse(File file) throws XmlException, IOException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(file, MemberBean.type, (XmlOptions)null);
      }

      public static MemberBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(file, MemberBean.type, options);
      }

      public static MemberBean parse(URL u) throws XmlException, IOException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(u, MemberBean.type, (XmlOptions)null);
      }

      public static MemberBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(u, MemberBean.type, options);
      }

      public static MemberBean parse(InputStream is) throws XmlException, IOException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(is, MemberBean.type, (XmlOptions)null);
      }

      public static MemberBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(is, MemberBean.type, options);
      }

      public static MemberBean parse(Reader r) throws XmlException, IOException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(r, MemberBean.type, (XmlOptions)null);
      }

      public static MemberBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(r, MemberBean.type, options);
      }

      public static MemberBean parse(XMLStreamReader sr) throws XmlException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(sr, MemberBean.type, (XmlOptions)null);
      }

      public static MemberBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(sr, MemberBean.type, options);
      }

      public static MemberBean parse(Node node) throws XmlException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(node, MemberBean.type, (XmlOptions)null);
      }

      public static MemberBean parse(Node node, XmlOptions options) throws XmlException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(node, MemberBean.type, options);
      }

      /** @deprecated */
      public static MemberBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(xis, MemberBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MemberBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MemberBean)XmlBeans.getContextTypeLoader().parse(xis, MemberBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MemberBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MemberBean.type, options);
      }

      private Factory() {
      }
   }
}
