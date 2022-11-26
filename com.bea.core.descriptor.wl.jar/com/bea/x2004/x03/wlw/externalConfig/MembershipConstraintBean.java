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

public interface MembershipConstraintBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MembershipConstraintBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("membershipconstraintbeanaa59type");

   String getMembershipRule();

   XmlString xgetMembershipRule();

   void setMembershipRule(String var1);

   void xsetMembershipRule(XmlString var1);

   String[] getMemberNameArray();

   String getMemberNameArray(int var1);

   XmlString[] xgetMemberNameArray();

   XmlString xgetMemberNameArray(int var1);

   int sizeOfMemberNameArray();

   void setMemberNameArray(String[] var1);

   void setMemberNameArray(int var1, String var2);

   void xsetMemberNameArray(XmlString[] var1);

   void xsetMemberNameArray(int var1, XmlString var2);

   void insertMemberName(int var1, String var2);

   void addMemberName(String var1);

   XmlString insertNewMemberName(int var1);

   XmlString addNewMemberName();

   void removeMemberName(int var1);

   public static final class Factory {
      public static MembershipConstraintBean newInstance() {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().newInstance(MembershipConstraintBean.type, (XmlOptions)null);
      }

      public static MembershipConstraintBean newInstance(XmlOptions options) {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().newInstance(MembershipConstraintBean.type, options);
      }

      public static MembershipConstraintBean parse(String xmlAsString) throws XmlException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, MembershipConstraintBean.type, (XmlOptions)null);
      }

      public static MembershipConstraintBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, MembershipConstraintBean.type, options);
      }

      public static MembershipConstraintBean parse(File file) throws XmlException, IOException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(file, MembershipConstraintBean.type, (XmlOptions)null);
      }

      public static MembershipConstraintBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(file, MembershipConstraintBean.type, options);
      }

      public static MembershipConstraintBean parse(URL u) throws XmlException, IOException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(u, MembershipConstraintBean.type, (XmlOptions)null);
      }

      public static MembershipConstraintBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(u, MembershipConstraintBean.type, options);
      }

      public static MembershipConstraintBean parse(InputStream is) throws XmlException, IOException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(is, MembershipConstraintBean.type, (XmlOptions)null);
      }

      public static MembershipConstraintBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(is, MembershipConstraintBean.type, options);
      }

      public static MembershipConstraintBean parse(Reader r) throws XmlException, IOException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(r, MembershipConstraintBean.type, (XmlOptions)null);
      }

      public static MembershipConstraintBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(r, MembershipConstraintBean.type, options);
      }

      public static MembershipConstraintBean parse(XMLStreamReader sr) throws XmlException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(sr, MembershipConstraintBean.type, (XmlOptions)null);
      }

      public static MembershipConstraintBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(sr, MembershipConstraintBean.type, options);
      }

      public static MembershipConstraintBean parse(Node node) throws XmlException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(node, MembershipConstraintBean.type, (XmlOptions)null);
      }

      public static MembershipConstraintBean parse(Node node, XmlOptions options) throws XmlException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(node, MembershipConstraintBean.type, options);
      }

      /** @deprecated */
      public static MembershipConstraintBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(xis, MembershipConstraintBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MembershipConstraintBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MembershipConstraintBean)XmlBeans.getContextTypeLoader().parse(xis, MembershipConstraintBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MembershipConstraintBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MembershipConstraintBean.type, options);
      }

      private Factory() {
      }
   }
}
