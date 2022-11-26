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

public interface AnnotationManifestDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotationManifestDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotationmanifestb692doctype");

   AnnotationManifestBean getAnnotationManifest();

   void setAnnotationManifest(AnnotationManifestBean var1);

   AnnotationManifestBean addNewAnnotationManifest();

   public static final class Factory {
      public static AnnotationManifestDocument newInstance() {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().newInstance(AnnotationManifestDocument.type, (XmlOptions)null);
      }

      public static AnnotationManifestDocument newInstance(XmlOptions options) {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().newInstance(AnnotationManifestDocument.type, options);
      }

      public static AnnotationManifestDocument parse(String xmlAsString) throws XmlException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationManifestDocument.type, (XmlOptions)null);
      }

      public static AnnotationManifestDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationManifestDocument.type, options);
      }

      public static AnnotationManifestDocument parse(File file) throws XmlException, IOException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(file, AnnotationManifestDocument.type, (XmlOptions)null);
      }

      public static AnnotationManifestDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(file, AnnotationManifestDocument.type, options);
      }

      public static AnnotationManifestDocument parse(URL u) throws XmlException, IOException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(u, AnnotationManifestDocument.type, (XmlOptions)null);
      }

      public static AnnotationManifestDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(u, AnnotationManifestDocument.type, options);
      }

      public static AnnotationManifestDocument parse(InputStream is) throws XmlException, IOException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(is, AnnotationManifestDocument.type, (XmlOptions)null);
      }

      public static AnnotationManifestDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(is, AnnotationManifestDocument.type, options);
      }

      public static AnnotationManifestDocument parse(Reader r) throws XmlException, IOException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(r, AnnotationManifestDocument.type, (XmlOptions)null);
      }

      public static AnnotationManifestDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(r, AnnotationManifestDocument.type, options);
      }

      public static AnnotationManifestDocument parse(XMLStreamReader sr) throws XmlException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(sr, AnnotationManifestDocument.type, (XmlOptions)null);
      }

      public static AnnotationManifestDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(sr, AnnotationManifestDocument.type, options);
      }

      public static AnnotationManifestDocument parse(Node node) throws XmlException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(node, AnnotationManifestDocument.type, (XmlOptions)null);
      }

      public static AnnotationManifestDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(node, AnnotationManifestDocument.type, options);
      }

      /** @deprecated */
      public static AnnotationManifestDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(xis, AnnotationManifestDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotationManifestDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotationManifestDocument)XmlBeans.getContextTypeLoader().parse(xis, AnnotationManifestDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationManifestDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationManifestDocument.type, options);
      }

      private Factory() {
      }
   }
}
