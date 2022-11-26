package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlID;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface RedefineDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RedefineDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("redefine3f55doctype");

   Redefine getRedefine();

   void setRedefine(Redefine var1);

   Redefine addNewRedefine();

   public static final class Factory {
      public static RedefineDocument newInstance() {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().newInstance(RedefineDocument.type, (XmlOptions)null);
      }

      public static RedefineDocument newInstance(XmlOptions options) {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().newInstance(RedefineDocument.type, options);
      }

      public static RedefineDocument parse(String xmlAsString) throws XmlException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, RedefineDocument.type, (XmlOptions)null);
      }

      public static RedefineDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, RedefineDocument.type, options);
      }

      public static RedefineDocument parse(File file) throws XmlException, IOException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse((File)file, RedefineDocument.type, (XmlOptions)null);
      }

      public static RedefineDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse(file, RedefineDocument.type, options);
      }

      public static RedefineDocument parse(URL u) throws XmlException, IOException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse((URL)u, RedefineDocument.type, (XmlOptions)null);
      }

      public static RedefineDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse(u, RedefineDocument.type, options);
      }

      public static RedefineDocument parse(InputStream is) throws XmlException, IOException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, RedefineDocument.type, (XmlOptions)null);
      }

      public static RedefineDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse(is, RedefineDocument.type, options);
      }

      public static RedefineDocument parse(Reader r) throws XmlException, IOException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, RedefineDocument.type, (XmlOptions)null);
      }

      public static RedefineDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse(r, RedefineDocument.type, options);
      }

      public static RedefineDocument parse(XMLStreamReader sr) throws XmlException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, RedefineDocument.type, (XmlOptions)null);
      }

      public static RedefineDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse(sr, RedefineDocument.type, options);
      }

      public static RedefineDocument parse(Node node) throws XmlException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse((Node)node, RedefineDocument.type, (XmlOptions)null);
      }

      public static RedefineDocument parse(Node node, XmlOptions options) throws XmlException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse(node, RedefineDocument.type, options);
      }

      /** @deprecated */
      public static RedefineDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, RedefineDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RedefineDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RedefineDocument)XmlBeans.getContextTypeLoader().parse(xis, RedefineDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RedefineDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RedefineDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Redefine extends OpenAttrs {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Redefine.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("redefine9e9felemtype");

      AnnotationDocument.Annotation[] getAnnotationArray();

      AnnotationDocument.Annotation getAnnotationArray(int var1);

      int sizeOfAnnotationArray();

      void setAnnotationArray(AnnotationDocument.Annotation[] var1);

      void setAnnotationArray(int var1, AnnotationDocument.Annotation var2);

      AnnotationDocument.Annotation insertNewAnnotation(int var1);

      AnnotationDocument.Annotation addNewAnnotation();

      void removeAnnotation(int var1);

      TopLevelSimpleType[] getSimpleTypeArray();

      TopLevelSimpleType getSimpleTypeArray(int var1);

      int sizeOfSimpleTypeArray();

      void setSimpleTypeArray(TopLevelSimpleType[] var1);

      void setSimpleTypeArray(int var1, TopLevelSimpleType var2);

      TopLevelSimpleType insertNewSimpleType(int var1);

      TopLevelSimpleType addNewSimpleType();

      void removeSimpleType(int var1);

      TopLevelComplexType[] getComplexTypeArray();

      TopLevelComplexType getComplexTypeArray(int var1);

      int sizeOfComplexTypeArray();

      void setComplexTypeArray(TopLevelComplexType[] var1);

      void setComplexTypeArray(int var1, TopLevelComplexType var2);

      TopLevelComplexType insertNewComplexType(int var1);

      TopLevelComplexType addNewComplexType();

      void removeComplexType(int var1);

      NamedGroup[] getGroupArray();

      NamedGroup getGroupArray(int var1);

      int sizeOfGroupArray();

      void setGroupArray(NamedGroup[] var1);

      void setGroupArray(int var1, NamedGroup var2);

      NamedGroup insertNewGroup(int var1);

      NamedGroup addNewGroup();

      void removeGroup(int var1);

      NamedAttributeGroup[] getAttributeGroupArray();

      NamedAttributeGroup getAttributeGroupArray(int var1);

      int sizeOfAttributeGroupArray();

      void setAttributeGroupArray(NamedAttributeGroup[] var1);

      void setAttributeGroupArray(int var1, NamedAttributeGroup var2);

      NamedAttributeGroup insertNewAttributeGroup(int var1);

      NamedAttributeGroup addNewAttributeGroup();

      void removeAttributeGroup(int var1);

      String getSchemaLocation();

      XmlAnyURI xgetSchemaLocation();

      void setSchemaLocation(String var1);

      void xsetSchemaLocation(XmlAnyURI var1);

      String getId();

      XmlID xgetId();

      boolean isSetId();

      void setId(String var1);

      void xsetId(XmlID var1);

      void unsetId();

      public static final class Factory {
         public static Redefine newInstance() {
            return (Redefine)XmlBeans.getContextTypeLoader().newInstance(RedefineDocument.Redefine.type, (XmlOptions)null);
         }

         public static Redefine newInstance(XmlOptions options) {
            return (Redefine)XmlBeans.getContextTypeLoader().newInstance(RedefineDocument.Redefine.type, options);
         }

         private Factory() {
         }
      }
   }
}
