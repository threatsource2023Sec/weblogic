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

public interface MemberDefinitionBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MemberDefinitionBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("memberdefinitionbean430btype");

   String getMemberName();

   XmlString xgetMemberName();

   void setMemberName(String var1);

   void xsetMemberName(XmlString var1);

   boolean getIsArray1();

   XmlBoolean xgetIsArray1();

   void setIsArray1(boolean var1);

   void xsetIsArray1(XmlBoolean var1);

   boolean getIsRequired();

   XmlBoolean xgetIsRequired();

   void setIsRequired(boolean var1);

   void xsetIsRequired(XmlBoolean var1);

   String getAnnotationRef();

   XmlString xgetAnnotationRef();

   boolean isSetAnnotationRef();

   void setAnnotationRef(String var1);

   void xsetAnnotationRef(XmlString var1);

   void unsetAnnotationRef();

   EnumRefBean getEnumRef();

   boolean isSetEnumRef();

   void setEnumRef(EnumRefBean var1);

   EnumRefBean addNewEnumRef();

   void unsetEnumRef();

   SimpleTypeDefinitionBean getSimpleTypeDefinition();

   boolean isSetSimpleTypeDefinition();

   void setSimpleTypeDefinition(SimpleTypeDefinitionBean var1);

   SimpleTypeDefinitionBean addNewSimpleTypeDefinition();

   void unsetSimpleTypeDefinition();

   public static final class Factory {
      public static MemberDefinitionBean newInstance() {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().newInstance(MemberDefinitionBean.type, (XmlOptions)null);
      }

      public static MemberDefinitionBean newInstance(XmlOptions options) {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().newInstance(MemberDefinitionBean.type, options);
      }

      public static MemberDefinitionBean parse(String xmlAsString) throws XmlException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, MemberDefinitionBean.type, (XmlOptions)null);
      }

      public static MemberDefinitionBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, MemberDefinitionBean.type, options);
      }

      public static MemberDefinitionBean parse(File file) throws XmlException, IOException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(file, MemberDefinitionBean.type, (XmlOptions)null);
      }

      public static MemberDefinitionBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(file, MemberDefinitionBean.type, options);
      }

      public static MemberDefinitionBean parse(URL u) throws XmlException, IOException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(u, MemberDefinitionBean.type, (XmlOptions)null);
      }

      public static MemberDefinitionBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(u, MemberDefinitionBean.type, options);
      }

      public static MemberDefinitionBean parse(InputStream is) throws XmlException, IOException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(is, MemberDefinitionBean.type, (XmlOptions)null);
      }

      public static MemberDefinitionBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(is, MemberDefinitionBean.type, options);
      }

      public static MemberDefinitionBean parse(Reader r) throws XmlException, IOException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(r, MemberDefinitionBean.type, (XmlOptions)null);
      }

      public static MemberDefinitionBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(r, MemberDefinitionBean.type, options);
      }

      public static MemberDefinitionBean parse(XMLStreamReader sr) throws XmlException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(sr, MemberDefinitionBean.type, (XmlOptions)null);
      }

      public static MemberDefinitionBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(sr, MemberDefinitionBean.type, options);
      }

      public static MemberDefinitionBean parse(Node node) throws XmlException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(node, MemberDefinitionBean.type, (XmlOptions)null);
      }

      public static MemberDefinitionBean parse(Node node, XmlOptions options) throws XmlException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(node, MemberDefinitionBean.type, options);
      }

      /** @deprecated */
      public static MemberDefinitionBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(xis, MemberDefinitionBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MemberDefinitionBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MemberDefinitionBean)XmlBeans.getContextTypeLoader().parse(xis, MemberDefinitionBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MemberDefinitionBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MemberDefinitionBean.type, options);
      }

      private Factory() {
      }
   }
}
