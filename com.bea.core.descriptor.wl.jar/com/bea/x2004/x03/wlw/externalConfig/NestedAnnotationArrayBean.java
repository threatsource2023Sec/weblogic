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

public interface NestedAnnotationArrayBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NestedAnnotationArrayBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("nestedannotationarraybeanc036type");

   String getMemberName();

   XmlString xgetMemberName();

   void setMemberName(String var1);

   void xsetMemberName(XmlString var1);

   AnnotationInstanceBean[] getAnnotationArray();

   AnnotationInstanceBean getAnnotationArray(int var1);

   int sizeOfAnnotationArray();

   void setAnnotationArray(AnnotationInstanceBean[] var1);

   void setAnnotationArray(int var1, AnnotationInstanceBean var2);

   AnnotationInstanceBean insertNewAnnotation(int var1);

   AnnotationInstanceBean addNewAnnotation();

   void removeAnnotation(int var1);

   public static final class Factory {
      public static NestedAnnotationArrayBean newInstance() {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().newInstance(NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationArrayBean newInstance(XmlOptions options) {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().newInstance(NestedAnnotationArrayBean.type, options);
      }

      public static NestedAnnotationArrayBean parse(String xmlAsString) throws XmlException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationArrayBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, NestedAnnotationArrayBean.type, options);
      }

      public static NestedAnnotationArrayBean parse(File file) throws XmlException, IOException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(file, NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationArrayBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(file, NestedAnnotationArrayBean.type, options);
      }

      public static NestedAnnotationArrayBean parse(URL u) throws XmlException, IOException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(u, NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationArrayBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(u, NestedAnnotationArrayBean.type, options);
      }

      public static NestedAnnotationArrayBean parse(InputStream is) throws XmlException, IOException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(is, NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationArrayBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(is, NestedAnnotationArrayBean.type, options);
      }

      public static NestedAnnotationArrayBean parse(Reader r) throws XmlException, IOException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(r, NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationArrayBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(r, NestedAnnotationArrayBean.type, options);
      }

      public static NestedAnnotationArrayBean parse(XMLStreamReader sr) throws XmlException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(sr, NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationArrayBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(sr, NestedAnnotationArrayBean.type, options);
      }

      public static NestedAnnotationArrayBean parse(Node node) throws XmlException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(node, NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      public static NestedAnnotationArrayBean parse(Node node, XmlOptions options) throws XmlException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(node, NestedAnnotationArrayBean.type, options);
      }

      /** @deprecated */
      public static NestedAnnotationArrayBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(xis, NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NestedAnnotationArrayBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NestedAnnotationArrayBean)XmlBeans.getContextTypeLoader().parse(xis, NestedAnnotationArrayBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NestedAnnotationArrayBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NestedAnnotationArrayBean.type, options);
      }

      private Factory() {
      }
   }
}
