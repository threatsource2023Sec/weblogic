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

public interface NestedAnnotationBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NestedAnnotationBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("nestedannotationbeand4eatype");

   String getMemberName();

   XmlString xgetMemberName();

   void setMemberName(String var1);

   void xsetMemberName(XmlString var1);

   AnnotationInstanceBean getAnnotation();

   void setAnnotation(AnnotationInstanceBean var1);

   AnnotationInstanceBean addNewAnnotation();

   public static final class Factory {
      public static NestedAnnotationBean newInstance() {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().newInstance(NestedAnnotationBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationBean newInstance(XmlOptions options) {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().newInstance(NestedAnnotationBean.type, options);
      }

      public static NestedAnnotationBean parse(String xmlAsString) throws XmlException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, NestedAnnotationBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, NestedAnnotationBean.type, options);
      }

      public static NestedAnnotationBean parse(File file) throws XmlException, IOException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(file, NestedAnnotationBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(file, NestedAnnotationBean.type, options);
      }

      public static NestedAnnotationBean parse(URL u) throws XmlException, IOException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(u, NestedAnnotationBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(u, NestedAnnotationBean.type, options);
      }

      public static NestedAnnotationBean parse(InputStream is) throws XmlException, IOException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(is, NestedAnnotationBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(is, NestedAnnotationBean.type, options);
      }

      public static NestedAnnotationBean parse(Reader r) throws XmlException, IOException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(r, NestedAnnotationBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(r, NestedAnnotationBean.type, options);
      }

      public static NestedAnnotationBean parse(XMLStreamReader sr) throws XmlException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(sr, NestedAnnotationBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(sr, NestedAnnotationBean.type, options);
      }

      public static NestedAnnotationBean parse(Node node) throws XmlException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(node, NestedAnnotationBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationBean parse(Node node, XmlOptions options) throws XmlException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(node, NestedAnnotationBean.type, options);
      }

      /** @deprecated */
      public static NestedAnnotationBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(xis, NestedAnnotationBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NestedAnnotationBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NestedAnnotationBean)XmlBeans.getContextTypeLoader().parse(xis, NestedAnnotationBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NestedAnnotationBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NestedAnnotationBean.type, options);
      }

      private Factory() {
      }
   }
}
