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
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface Annotated extends OpenAttrs {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Annotated.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("annotateda52dtype");

   AnnotationDocument.Annotation getAnnotation();

   boolean isSetAnnotation();

   void setAnnotation(AnnotationDocument.Annotation var1);

   AnnotationDocument.Annotation addNewAnnotation();

   void unsetAnnotation();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static Annotated newInstance() {
         return (Annotated)XmlBeans.getContextTypeLoader().newInstance(Annotated.type, (XmlOptions)null);
      }

      public static Annotated newInstance(XmlOptions options) {
         return (Annotated)XmlBeans.getContextTypeLoader().newInstance(Annotated.type, options);
      }

      public static Annotated parse(String xmlAsString) throws XmlException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Annotated.type, (XmlOptions)null);
      }

      public static Annotated parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse(xmlAsString, Annotated.type, options);
      }

      public static Annotated parse(File file) throws XmlException, IOException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse((File)file, Annotated.type, (XmlOptions)null);
      }

      public static Annotated parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse(file, Annotated.type, options);
      }

      public static Annotated parse(URL u) throws XmlException, IOException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse((URL)u, Annotated.type, (XmlOptions)null);
      }

      public static Annotated parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse(u, Annotated.type, options);
      }

      public static Annotated parse(InputStream is) throws XmlException, IOException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse((InputStream)is, Annotated.type, (XmlOptions)null);
      }

      public static Annotated parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse(is, Annotated.type, options);
      }

      public static Annotated parse(Reader r) throws XmlException, IOException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse((Reader)r, Annotated.type, (XmlOptions)null);
      }

      public static Annotated parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse(r, Annotated.type, options);
      }

      public static Annotated parse(XMLStreamReader sr) throws XmlException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Annotated.type, (XmlOptions)null);
      }

      public static Annotated parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse(sr, Annotated.type, options);
      }

      public static Annotated parse(Node node) throws XmlException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse((Node)node, Annotated.type, (XmlOptions)null);
      }

      public static Annotated parse(Node node, XmlOptions options) throws XmlException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse(node, Annotated.type, options);
      }

      /** @deprecated */
      public static Annotated parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Annotated.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Annotated parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Annotated)XmlBeans.getContextTypeLoader().parse(xis, Annotated.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Annotated.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Annotated.type, options);
      }

      private Factory() {
      }
   }
}
