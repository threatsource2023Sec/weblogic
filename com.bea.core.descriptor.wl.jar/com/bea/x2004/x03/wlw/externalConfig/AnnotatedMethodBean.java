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

public interface AnnotatedMethodBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotatedMethodBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotatedmethodbean9f0dtype");

   String getMethodKey();

   XmlString xgetMethodKey();

   void setMethodKey(String var1);

   void xsetMethodKey(XmlString var1);

   String getMethodName();

   XmlString xgetMethodName();

   void setMethodName(String var1);

   void xsetMethodName(XmlString var1);

   AnnotationInstanceBean[] getAnnotationArray();

   AnnotationInstanceBean getAnnotationArray(int var1);

   int sizeOfAnnotationArray();

   void setAnnotationArray(AnnotationInstanceBean[] var1);

   void setAnnotationArray(int var1, AnnotationInstanceBean var2);

   AnnotationInstanceBean insertNewAnnotation(int var1);

   AnnotationInstanceBean addNewAnnotation();

   void removeAnnotation(int var1);

   String[] getParamterTypeArray();

   String getParamterTypeArray(int var1);

   XmlString[] xgetParamterTypeArray();

   XmlString xgetParamterTypeArray(int var1);

   int sizeOfParamterTypeArray();

   void setParamterTypeArray(String[] var1);

   void setParamterTypeArray(int var1, String var2);

   void xsetParamterTypeArray(XmlString[] var1);

   void xsetParamterTypeArray(int var1, XmlString var2);

   void insertParamterType(int var1, String var2);

   void addParamterType(String var1);

   XmlString insertNewParamterType(int var1);

   XmlString addNewParamterType();

   void removeParamterType(int var1);

   public static final class Factory {
      public static AnnotatedMethodBean newInstance() {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().newInstance(AnnotatedMethodBean.type, (XmlOptions)null);
      }

      public static AnnotatedMethodBean newInstance(XmlOptions options) {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().newInstance(AnnotatedMethodBean.type, options);
      }

      public static AnnotatedMethodBean parse(String xmlAsString) throws XmlException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotatedMethodBean.type, (XmlOptions)null);
      }

      public static AnnotatedMethodBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotatedMethodBean.type, options);
      }

      public static AnnotatedMethodBean parse(File file) throws XmlException, IOException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(file, AnnotatedMethodBean.type, (XmlOptions)null);
      }

      public static AnnotatedMethodBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(file, AnnotatedMethodBean.type, options);
      }

      public static AnnotatedMethodBean parse(URL u) throws XmlException, IOException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(u, AnnotatedMethodBean.type, (XmlOptions)null);
      }

      public static AnnotatedMethodBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(u, AnnotatedMethodBean.type, options);
      }

      public static AnnotatedMethodBean parse(InputStream is) throws XmlException, IOException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(is, AnnotatedMethodBean.type, (XmlOptions)null);
      }

      public static AnnotatedMethodBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(is, AnnotatedMethodBean.type, options);
      }

      public static AnnotatedMethodBean parse(Reader r) throws XmlException, IOException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(r, AnnotatedMethodBean.type, (XmlOptions)null);
      }

      public static AnnotatedMethodBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(r, AnnotatedMethodBean.type, options);
      }

      public static AnnotatedMethodBean parse(XMLStreamReader sr) throws XmlException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotatedMethodBean.type, (XmlOptions)null);
      }

      public static AnnotatedMethodBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotatedMethodBean.type, options);
      }

      public static AnnotatedMethodBean parse(Node node) throws XmlException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(node, AnnotatedMethodBean.type, (XmlOptions)null);
      }

      public static AnnotatedMethodBean parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(node, AnnotatedMethodBean.type, options);
      }

      /** @deprecated */
      public static AnnotatedMethodBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotatedMethodBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotatedMethodBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotatedMethodBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotatedMethodBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotatedMethodBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotatedMethodBean.type, options);
      }

      private Factory() {
      }
   }
}
