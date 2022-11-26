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

public interface AnnotatedFieldBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotatedFieldBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotatedfieldbean6c6atype");

   String getFieldName();

   XmlString xgetFieldName();

   void setFieldName(String var1);

   void xsetFieldName(XmlString var1);

   String getInstanceType();

   XmlString xgetInstanceType();

   void setInstanceType(String var1);

   void xsetInstanceType(XmlString var1);

   AnnotationInstanceBean[] getAnnotationArray();

   AnnotationInstanceBean getAnnotationArray(int var1);

   int sizeOfAnnotationArray();

   void setAnnotationArray(AnnotationInstanceBean[] var1);

   void setAnnotationArray(int var1, AnnotationInstanceBean var2);

   AnnotationInstanceBean insertNewAnnotation(int var1);

   AnnotationInstanceBean addNewAnnotation();

   void removeAnnotation(int var1);

   public static final class Factory {
      public static AnnotatedFieldBean newInstance() {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().newInstance(AnnotatedFieldBean.type, (XmlOptions)null);
      }

      public static AnnotatedFieldBean newInstance(XmlOptions options) {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().newInstance(AnnotatedFieldBean.type, options);
      }

      public static AnnotatedFieldBean parse(String xmlAsString) throws XmlException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotatedFieldBean.type, (XmlOptions)null);
      }

      public static AnnotatedFieldBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotatedFieldBean.type, options);
      }

      public static AnnotatedFieldBean parse(File file) throws XmlException, IOException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(file, AnnotatedFieldBean.type, (XmlOptions)null);
      }

      public static AnnotatedFieldBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(file, AnnotatedFieldBean.type, options);
      }

      public static AnnotatedFieldBean parse(URL u) throws XmlException, IOException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(u, AnnotatedFieldBean.type, (XmlOptions)null);
      }

      public static AnnotatedFieldBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(u, AnnotatedFieldBean.type, options);
      }

      public static AnnotatedFieldBean parse(InputStream is) throws XmlException, IOException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(is, AnnotatedFieldBean.type, (XmlOptions)null);
      }

      public static AnnotatedFieldBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(is, AnnotatedFieldBean.type, options);
      }

      public static AnnotatedFieldBean parse(Reader r) throws XmlException, IOException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(r, AnnotatedFieldBean.type, (XmlOptions)null);
      }

      public static AnnotatedFieldBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(r, AnnotatedFieldBean.type, options);
      }

      public static AnnotatedFieldBean parse(XMLStreamReader sr) throws XmlException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotatedFieldBean.type, (XmlOptions)null);
      }

      public static AnnotatedFieldBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotatedFieldBean.type, options);
      }

      public static AnnotatedFieldBean parse(Node node) throws XmlException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(node, AnnotatedFieldBean.type, (XmlOptions)null);
      }

      public static AnnotatedFieldBean parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(node, AnnotatedFieldBean.type, options);
      }

      /** @deprecated */
      public static AnnotatedFieldBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotatedFieldBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotatedFieldBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotatedFieldBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotatedFieldBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotatedFieldBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotatedFieldBean.type, options);
      }

      private Factory() {
      }
   }
}
