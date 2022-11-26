package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlID;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface AnnotationDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotationDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("annotationb034doctype");

   Annotation getAnnotation();

   void setAnnotation(Annotation var1);

   Annotation addNewAnnotation();

   public static final class Factory {
      public static AnnotationDocument newInstance() {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().newInstance(AnnotationDocument.type, (XmlOptions)null);
      }

      public static AnnotationDocument newInstance(XmlOptions options) {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().newInstance(AnnotationDocument.type, options);
      }

      public static AnnotationDocument parse(String xmlAsString) throws XmlException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, AnnotationDocument.type, (XmlOptions)null);
      }

      public static AnnotationDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationDocument.type, options);
      }

      public static AnnotationDocument parse(File file) throws XmlException, IOException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse((File)file, AnnotationDocument.type, (XmlOptions)null);
      }

      public static AnnotationDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse(file, AnnotationDocument.type, options);
      }

      public static AnnotationDocument parse(URL u) throws XmlException, IOException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse((URL)u, AnnotationDocument.type, (XmlOptions)null);
      }

      public static AnnotationDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse(u, AnnotationDocument.type, options);
      }

      public static AnnotationDocument parse(InputStream is) throws XmlException, IOException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, AnnotationDocument.type, (XmlOptions)null);
      }

      public static AnnotationDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse(is, AnnotationDocument.type, options);
      }

      public static AnnotationDocument parse(Reader r) throws XmlException, IOException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, AnnotationDocument.type, (XmlOptions)null);
      }

      public static AnnotationDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse(r, AnnotationDocument.type, options);
      }

      public static AnnotationDocument parse(XMLStreamReader sr) throws XmlException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, AnnotationDocument.type, (XmlOptions)null);
      }

      public static AnnotationDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse(sr, AnnotationDocument.type, options);
      }

      public static AnnotationDocument parse(Node node) throws XmlException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse((Node)node, AnnotationDocument.type, (XmlOptions)null);
      }

      public static AnnotationDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse(node, AnnotationDocument.type, options);
      }

      /** @deprecated */
      public static AnnotationDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, AnnotationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotationDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotationDocument)XmlBeans.getContextTypeLoader().parse(xis, AnnotationDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Annotation extends OpenAttrs {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Annotation.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("annotation5abfelemtype");

      AppinfoDocument.Appinfo[] getAppinfoArray();

      AppinfoDocument.Appinfo getAppinfoArray(int var1);

      int sizeOfAppinfoArray();

      void setAppinfoArray(AppinfoDocument.Appinfo[] var1);

      void setAppinfoArray(int var1, AppinfoDocument.Appinfo var2);

      AppinfoDocument.Appinfo insertNewAppinfo(int var1);

      AppinfoDocument.Appinfo addNewAppinfo();

      void removeAppinfo(int var1);

      DocumentationDocument.Documentation[] getDocumentationArray();

      DocumentationDocument.Documentation getDocumentationArray(int var1);

      int sizeOfDocumentationArray();

      void setDocumentationArray(DocumentationDocument.Documentation[] var1);

      void setDocumentationArray(int var1, DocumentationDocument.Documentation var2);

      DocumentationDocument.Documentation insertNewDocumentation(int var1);

      DocumentationDocument.Documentation addNewDocumentation();

      void removeDocumentation(int var1);

      String getId();

      XmlID xgetId();

      boolean isSetId();

      void setId(String var1);

      void xsetId(XmlID var1);

      void unsetId();

      public static final class Factory {
         public static Annotation newInstance() {
            return (Annotation)XmlBeans.getContextTypeLoader().newInstance(AnnotationDocument.Annotation.type, (XmlOptions)null);
         }

         public static Annotation newInstance(XmlOptions options) {
            return (Annotation)XmlBeans.getContextTypeLoader().newInstance(AnnotationDocument.Annotation.type, options);
         }

         private Factory() {
         }
      }
   }
}
