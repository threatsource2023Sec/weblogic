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
import org.apache.xmlbeans.XmlLanguage;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlToken;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface SchemaDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SchemaDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("schema0782doctype");

   Schema getSchema();

   void setSchema(Schema var1);

   Schema addNewSchema();

   public static final class Factory {
      public static SchemaDocument newInstance() {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().newInstance(SchemaDocument.type, (XmlOptions)null);
      }

      public static SchemaDocument newInstance(XmlOptions options) {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().newInstance(SchemaDocument.type, options);
      }

      public static SchemaDocument parse(String xmlAsString) throws XmlException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, SchemaDocument.type, (XmlOptions)null);
      }

      public static SchemaDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, SchemaDocument.type, options);
      }

      public static SchemaDocument parse(File file) throws XmlException, IOException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse((File)file, SchemaDocument.type, (XmlOptions)null);
      }

      public static SchemaDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse(file, SchemaDocument.type, options);
      }

      public static SchemaDocument parse(URL u) throws XmlException, IOException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse((URL)u, SchemaDocument.type, (XmlOptions)null);
      }

      public static SchemaDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse(u, SchemaDocument.type, options);
      }

      public static SchemaDocument parse(InputStream is) throws XmlException, IOException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, SchemaDocument.type, (XmlOptions)null);
      }

      public static SchemaDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse(is, SchemaDocument.type, options);
      }

      public static SchemaDocument parse(Reader r) throws XmlException, IOException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, SchemaDocument.type, (XmlOptions)null);
      }

      public static SchemaDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse(r, SchemaDocument.type, options);
      }

      public static SchemaDocument parse(XMLStreamReader sr) throws XmlException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, SchemaDocument.type, (XmlOptions)null);
      }

      public static SchemaDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse(sr, SchemaDocument.type, options);
      }

      public static SchemaDocument parse(Node node) throws XmlException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse((Node)node, SchemaDocument.type, (XmlOptions)null);
      }

      public static SchemaDocument parse(Node node, XmlOptions options) throws XmlException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse(node, SchemaDocument.type, options);
      }

      /** @deprecated */
      public static SchemaDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, SchemaDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SchemaDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SchemaDocument)XmlBeans.getContextTypeLoader().parse(xis, SchemaDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SchemaDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SchemaDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Schema extends OpenAttrs {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Schema.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("schemad77felemtype");

      IncludeDocument.Include[] getIncludeArray();

      IncludeDocument.Include getIncludeArray(int var1);

      int sizeOfIncludeArray();

      void setIncludeArray(IncludeDocument.Include[] var1);

      void setIncludeArray(int var1, IncludeDocument.Include var2);

      IncludeDocument.Include insertNewInclude(int var1);

      IncludeDocument.Include addNewInclude();

      void removeInclude(int var1);

      ImportDocument.Import[] getImportArray();

      ImportDocument.Import getImportArray(int var1);

      int sizeOfImportArray();

      void setImportArray(ImportDocument.Import[] var1);

      void setImportArray(int var1, ImportDocument.Import var2);

      ImportDocument.Import insertNewImport(int var1);

      ImportDocument.Import addNewImport();

      void removeImport(int var1);

      RedefineDocument.Redefine[] getRedefineArray();

      RedefineDocument.Redefine getRedefineArray(int var1);

      int sizeOfRedefineArray();

      void setRedefineArray(RedefineDocument.Redefine[] var1);

      void setRedefineArray(int var1, RedefineDocument.Redefine var2);

      RedefineDocument.Redefine insertNewRedefine(int var1);

      RedefineDocument.Redefine addNewRedefine();

      void removeRedefine(int var1);

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

      TopLevelElement[] getElementArray();

      TopLevelElement getElementArray(int var1);

      int sizeOfElementArray();

      void setElementArray(TopLevelElement[] var1);

      void setElementArray(int var1, TopLevelElement var2);

      TopLevelElement insertNewElement(int var1);

      TopLevelElement addNewElement();

      void removeElement(int var1);

      TopLevelAttribute[] getAttributeArray();

      TopLevelAttribute getAttributeArray(int var1);

      int sizeOfAttributeArray();

      void setAttributeArray(TopLevelAttribute[] var1);

      void setAttributeArray(int var1, TopLevelAttribute var2);

      TopLevelAttribute insertNewAttribute(int var1);

      TopLevelAttribute addNewAttribute();

      void removeAttribute(int var1);

      NotationDocument.Notation[] getNotationArray();

      NotationDocument.Notation getNotationArray(int var1);

      int sizeOfNotationArray();

      void setNotationArray(NotationDocument.Notation[] var1);

      void setNotationArray(int var1, NotationDocument.Notation var2);

      NotationDocument.Notation insertNewNotation(int var1);

      NotationDocument.Notation addNewNotation();

      void removeNotation(int var1);

      String getTargetNamespace();

      XmlAnyURI xgetTargetNamespace();

      boolean isSetTargetNamespace();

      void setTargetNamespace(String var1);

      void xsetTargetNamespace(XmlAnyURI var1);

      void unsetTargetNamespace();

      String getVersion();

      XmlToken xgetVersion();

      boolean isSetVersion();

      void setVersion(String var1);

      void xsetVersion(XmlToken var1);

      void unsetVersion();

      Object getFinalDefault();

      FullDerivationSet xgetFinalDefault();

      boolean isSetFinalDefault();

      void setFinalDefault(Object var1);

      void xsetFinalDefault(FullDerivationSet var1);

      void unsetFinalDefault();

      Object getBlockDefault();

      BlockSet xgetBlockDefault();

      boolean isSetBlockDefault();

      void setBlockDefault(Object var1);

      void xsetBlockDefault(BlockSet var1);

      void unsetBlockDefault();

      FormChoice.Enum getAttributeFormDefault();

      FormChoice xgetAttributeFormDefault();

      boolean isSetAttributeFormDefault();

      void setAttributeFormDefault(FormChoice.Enum var1);

      void xsetAttributeFormDefault(FormChoice var1);

      void unsetAttributeFormDefault();

      FormChoice.Enum getElementFormDefault();

      FormChoice xgetElementFormDefault();

      boolean isSetElementFormDefault();

      void setElementFormDefault(FormChoice.Enum var1);

      void xsetElementFormDefault(FormChoice var1);

      void unsetElementFormDefault();

      String getId();

      XmlID xgetId();

      boolean isSetId();

      void setId(String var1);

      void xsetId(XmlID var1);

      void unsetId();

      String getLang();

      XmlLanguage xgetLang();

      boolean isSetLang();

      void setLang(String var1);

      void xsetLang(XmlLanguage var1);

      void unsetLang();

      public static final class Factory {
         public static Schema newInstance() {
            return (Schema)XmlBeans.getContextTypeLoader().newInstance(SchemaDocument.Schema.type, (XmlOptions)null);
         }

         public static Schema newInstance(XmlOptions options) {
            return (Schema)XmlBeans.getContextTypeLoader().newInstance(SchemaDocument.Schema.type, options);
         }

         private Factory() {
         }
      }
   }
}
