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

public interface AnnotationOverridesDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotationOverridesDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotationoverrides3ba0doctype");

   AnnotationOverridesBean getAnnotationOverrides();

   void setAnnotationOverrides(AnnotationOverridesBean var1);

   AnnotationOverridesBean addNewAnnotationOverrides();

   public static final class Factory {
      public static AnnotationOverridesDocument newInstance() {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().newInstance(AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      public static AnnotationOverridesDocument newInstance(XmlOptions options) {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().newInstance(AnnotationOverridesDocument.type, options);
      }

      public static AnnotationOverridesDocument parse(String xmlAsString) throws XmlException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      public static AnnotationOverridesDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationOverridesDocument.type, options);
      }

      public static AnnotationOverridesDocument parse(File file) throws XmlException, IOException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(file, AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      public static AnnotationOverridesDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(file, AnnotationOverridesDocument.type, options);
      }

      public static AnnotationOverridesDocument parse(URL u) throws XmlException, IOException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(u, AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      public static AnnotationOverridesDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(u, AnnotationOverridesDocument.type, options);
      }

      public static AnnotationOverridesDocument parse(InputStream is) throws XmlException, IOException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(is, AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      public static AnnotationOverridesDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(is, AnnotationOverridesDocument.type, options);
      }

      public static AnnotationOverridesDocument parse(Reader r) throws XmlException, IOException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(r, AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      public static AnnotationOverridesDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(r, AnnotationOverridesDocument.type, options);
      }

      public static AnnotationOverridesDocument parse(XMLStreamReader sr) throws XmlException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(sr, AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      public static AnnotationOverridesDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(sr, AnnotationOverridesDocument.type, options);
      }

      public static AnnotationOverridesDocument parse(Node node) throws XmlException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(node, AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      public static AnnotationOverridesDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(node, AnnotationOverridesDocument.type, options);
      }

      /** @deprecated */
      public static AnnotationOverridesDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(xis, AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotationOverridesDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotationOverridesDocument)XmlBeans.getContextTypeLoader().parse(xis, AnnotationOverridesDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationOverridesDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationOverridesDocument.type, options);
      }

      private Factory() {
      }
   }
}
