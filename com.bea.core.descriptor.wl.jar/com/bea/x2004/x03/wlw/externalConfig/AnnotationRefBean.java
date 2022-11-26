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

public interface AnnotationRefBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotationRefBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotationrefbean750ctype");

   public static final class Factory {
      public static AnnotationRefBean newInstance() {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationRefBean.type, (XmlOptions)null);
      }

      public static AnnotationRefBean newInstance(XmlOptions options) {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationRefBean.type, options);
      }

      public static AnnotationRefBean parse(String xmlAsString) throws XmlException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationRefBean.type, (XmlOptions)null);
      }

      public static AnnotationRefBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationRefBean.type, options);
      }

      public static AnnotationRefBean parse(File file) throws XmlException, IOException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationRefBean.type, (XmlOptions)null);
      }

      public static AnnotationRefBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationRefBean.type, options);
      }

      public static AnnotationRefBean parse(URL u) throws XmlException, IOException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationRefBean.type, (XmlOptions)null);
      }

      public static AnnotationRefBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationRefBean.type, options);
      }

      public static AnnotationRefBean parse(InputStream is) throws XmlException, IOException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationRefBean.type, (XmlOptions)null);
      }

      public static AnnotationRefBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationRefBean.type, options);
      }

      public static AnnotationRefBean parse(Reader r) throws XmlException, IOException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationRefBean.type, (XmlOptions)null);
      }

      public static AnnotationRefBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationRefBean.type, options);
      }

      public static AnnotationRefBean parse(XMLStreamReader sr) throws XmlException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationRefBean.type, (XmlOptions)null);
      }

      public static AnnotationRefBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationRefBean.type, options);
      }

      public static AnnotationRefBean parse(Node node) throws XmlException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationRefBean.type, (XmlOptions)null);
      }

      public static AnnotationRefBean parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationRefBean.type, options);
      }

      /** @deprecated */
      public static AnnotationRefBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationRefBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotationRefBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotationRefBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationRefBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationRefBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationRefBean.type, options);
      }

      private Factory() {
      }
   }
}
