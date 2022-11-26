package com.bea.x2004.x03.wlw.externalConfig;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface AnnotationOverridesBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotationOverridesBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotationoverridesbean2460type");

   AnnotatedClassBean[] getAnnotatedClassArray();

   AnnotatedClassBean getAnnotatedClassArray(int var1);

   int sizeOfAnnotatedClassArray();

   void setAnnotatedClassArray(AnnotatedClassBean[] var1);

   void setAnnotatedClassArray(int var1, AnnotatedClassBean var2);

   AnnotatedClassBean insertNewAnnotatedClass(int var1);

   AnnotatedClassBean addNewAnnotatedClass();

   void removeAnnotatedClass(int var1);

   public static final class Factory {
      public static AnnotationOverridesBean newInstance() {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationOverridesBean.type, (XmlOptions)null);
      }

      public static AnnotationOverridesBean newInstance(XmlOptions options) {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationOverridesBean.type, options);
      }

      public static AnnotationOverridesBean parse(String xmlAsString) throws XmlException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationOverridesBean.type, (XmlOptions)null);
      }

      public static AnnotationOverridesBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationOverridesBean.type, options);
      }

      public static AnnotationOverridesBean parse(File file) throws XmlException, IOException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationOverridesBean.type, (XmlOptions)null);
      }

      public static AnnotationOverridesBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationOverridesBean.type, options);
      }

      public static AnnotationOverridesBean parse(URL u) throws XmlException, IOException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationOverridesBean.type, (XmlOptions)null);
      }

      public static AnnotationOverridesBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationOverridesBean.type, options);
      }

      public static AnnotationOverridesBean parse(InputStream is) throws XmlException, IOException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationOverridesBean.type, (XmlOptions)null);
      }

      public static AnnotationOverridesBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationOverridesBean.type, options);
      }

      public static AnnotationOverridesBean parse(Reader r) throws XmlException, IOException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationOverridesBean.type, (XmlOptions)null);
      }

      public static AnnotationOverridesBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationOverridesBean.type, options);
      }

      public static AnnotationOverridesBean parse(XMLStreamReader sr) throws XmlException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationOverridesBean.type, (XmlOptions)null);
      }

      public static AnnotationOverridesBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationOverridesBean.type, options);
      }

      public static AnnotationOverridesBean parse(Node node) throws XmlException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationOverridesBean.type, (XmlOptions)null);
      }

      public static AnnotationOverridesBean parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationOverridesBean.type, options);
      }

      /** @deprecated */
      public static AnnotationOverridesBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationOverridesBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotationOverridesBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotationOverridesBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationOverridesBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationOverridesBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationOverridesBean.type, options);
      }

      private Factory() {
      }
   }
}
