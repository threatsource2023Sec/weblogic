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

public interface AnnotationDefinitionBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotationDefinitionBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotationdefinitionbeanb056type");

   String getAnnotationClassName();

   XmlString xgetAnnotationClassName();

   void setAnnotationClassName(String var1);

   void xsetAnnotationClassName(XmlString var1);

   MembershipConstraintBean getMembershipConstraint();

   boolean isSetMembershipConstraint();

   void setMembershipConstraint(MembershipConstraintBean var1);

   MembershipConstraintBean addNewMembershipConstraint();

   void unsetMembershipConstraint();

   boolean getAllowedOnDeclaration();

   XmlBoolean xgetAllowedOnDeclaration();

   void setAllowedOnDeclaration(boolean var1);

   void xsetAllowedOnDeclaration(XmlBoolean var1);

   MemberDefinitionBean[] getMemberDefinitionArray();

   MemberDefinitionBean getMemberDefinitionArray(int var1);

   int sizeOfMemberDefinitionArray();

   void setMemberDefinitionArray(MemberDefinitionBean[] var1);

   void setMemberDefinitionArray(int var1, MemberDefinitionBean var2);

   MemberDefinitionBean insertNewMemberDefinition(int var1);

   MemberDefinitionBean addNewMemberDefinition();

   void removeMemberDefinition(int var1);

   public static final class Factory {
      public static AnnotationDefinitionBean newInstance() {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      public static AnnotationDefinitionBean newInstance(XmlOptions options) {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationDefinitionBean.type, options);
      }

      public static AnnotationDefinitionBean parse(String xmlAsString) throws XmlException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      public static AnnotationDefinitionBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationDefinitionBean.type, options);
      }

      public static AnnotationDefinitionBean parse(File file) throws XmlException, IOException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      public static AnnotationDefinitionBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationDefinitionBean.type, options);
      }

      public static AnnotationDefinitionBean parse(URL u) throws XmlException, IOException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      public static AnnotationDefinitionBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationDefinitionBean.type, options);
      }

      public static AnnotationDefinitionBean parse(InputStream is) throws XmlException, IOException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      public static AnnotationDefinitionBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationDefinitionBean.type, options);
      }

      public static AnnotationDefinitionBean parse(Reader r) throws XmlException, IOException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      public static AnnotationDefinitionBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationDefinitionBean.type, options);
      }

      public static AnnotationDefinitionBean parse(XMLStreamReader sr) throws XmlException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      public static AnnotationDefinitionBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationDefinitionBean.type, options);
      }

      public static AnnotationDefinitionBean parse(Node node) throws XmlException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      public static AnnotationDefinitionBean parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationDefinitionBean.type, options);
      }

      /** @deprecated */
      public static AnnotationDefinitionBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotationDefinitionBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotationDefinitionBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationDefinitionBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationDefinitionBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationDefinitionBean.type, options);
      }

      private Factory() {
      }
   }
}
