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

public interface AnnotatedClassBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotatedClassBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotatedclassbeand1e8type");

   String getAnnotatedClassName();

   XmlString xgetAnnotatedClassName();

   void setAnnotatedClassName(String var1);

   void xsetAnnotatedClassName(XmlString var1);

   String getComponentType();

   XmlString xgetComponentType();

   boolean isSetComponentType();

   void setComponentType(String var1);

   void xsetComponentType(XmlString var1);

   void unsetComponentType();

   AnnotationInstanceBean[] getAnnotationArray();

   AnnotationInstanceBean getAnnotationArray(int var1);

   int sizeOfAnnotationArray();

   void setAnnotationArray(AnnotationInstanceBean[] var1);

   void setAnnotationArray(int var1, AnnotationInstanceBean var2);

   AnnotationInstanceBean insertNewAnnotation(int var1);

   AnnotationInstanceBean addNewAnnotation();

   void removeAnnotation(int var1);

   AnnotatedFieldBean[] getFieldArray();

   AnnotatedFieldBean getFieldArray(int var1);

   int sizeOfFieldArray();

   void setFieldArray(AnnotatedFieldBean[] var1);

   void setFieldArray(int var1, AnnotatedFieldBean var2);

   AnnotatedFieldBean insertNewField(int var1);

   AnnotatedFieldBean addNewField();

   void removeField(int var1);

   AnnotatedMethodBean[] getMethodArray();

   AnnotatedMethodBean getMethodArray(int var1);

   int sizeOfMethodArray();

   void setMethodArray(AnnotatedMethodBean[] var1);

   void setMethodArray(int var1, AnnotatedMethodBean var2);

   AnnotatedMethodBean insertNewMethod(int var1);

   AnnotatedMethodBean addNewMethod();

   void removeMethod(int var1);

   public static final class Factory {
      public static AnnotatedClassBean newInstance() {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().newInstance(AnnotatedClassBean.type, (XmlOptions)null);
      }

      public static AnnotatedClassBean newInstance(XmlOptions options) {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().newInstance(AnnotatedClassBean.type, options);
      }

      public static AnnotatedClassBean parse(String xmlAsString) throws XmlException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotatedClassBean.type, (XmlOptions)null);
      }

      public static AnnotatedClassBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotatedClassBean.type, options);
      }

      public static AnnotatedClassBean parse(File file) throws XmlException, IOException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(file, AnnotatedClassBean.type, (XmlOptions)null);
      }

      public static AnnotatedClassBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(file, AnnotatedClassBean.type, options);
      }

      public static AnnotatedClassBean parse(URL u) throws XmlException, IOException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(u, AnnotatedClassBean.type, (XmlOptions)null);
      }

      public static AnnotatedClassBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(u, AnnotatedClassBean.type, options);
      }

      public static AnnotatedClassBean parse(InputStream is) throws XmlException, IOException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(is, AnnotatedClassBean.type, (XmlOptions)null);
      }

      public static AnnotatedClassBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(is, AnnotatedClassBean.type, options);
      }

      public static AnnotatedClassBean parse(Reader r) throws XmlException, IOException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(r, AnnotatedClassBean.type, (XmlOptions)null);
      }

      public static AnnotatedClassBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(r, AnnotatedClassBean.type, options);
      }

      public static AnnotatedClassBean parse(XMLStreamReader sr) throws XmlException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotatedClassBean.type, (XmlOptions)null);
      }

      public static AnnotatedClassBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotatedClassBean.type, options);
      }

      public static AnnotatedClassBean parse(Node node) throws XmlException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(node, AnnotatedClassBean.type, (XmlOptions)null);
      }

      public static AnnotatedClassBean parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(node, AnnotatedClassBean.type, options);
      }

      /** @deprecated */
      public static AnnotatedClassBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotatedClassBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotatedClassBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotatedClassBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotatedClassBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotatedClassBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotatedClassBean.type, options);
      }

      private Factory() {
      }
   }
}
