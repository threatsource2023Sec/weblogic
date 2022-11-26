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

public interface AnnotationManifestBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnnotationManifestBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("annotationmanifestbeanb472type");

   AnnotatedClassBean[] getAnnotatedClassArray();

   AnnotatedClassBean getAnnotatedClassArray(int var1);

   int sizeOfAnnotatedClassArray();

   void setAnnotatedClassArray(AnnotatedClassBean[] var1);

   void setAnnotatedClassArray(int var1, AnnotatedClassBean var2);

   AnnotatedClassBean insertNewAnnotatedClass(int var1);

   AnnotatedClassBean addNewAnnotatedClass();

   void removeAnnotatedClass(int var1);

   AnnotationDefinitionBean[] getAnnotationDefinitionArray();

   AnnotationDefinitionBean getAnnotationDefinitionArray(int var1);

   int sizeOfAnnotationDefinitionArray();

   void setAnnotationDefinitionArray(AnnotationDefinitionBean[] var1);

   void setAnnotationDefinitionArray(int var1, AnnotationDefinitionBean var2);

   AnnotationDefinitionBean insertNewAnnotationDefinition(int var1);

   AnnotationDefinitionBean addNewAnnotationDefinition();

   void removeAnnotationDefinition(int var1);

   EnumDefinitionBean[] getEnumDefinitionArray();

   EnumDefinitionBean getEnumDefinitionArray(int var1);

   int sizeOfEnumDefinitionArray();

   void setEnumDefinitionArray(EnumDefinitionBean[] var1);

   void setEnumDefinitionArray(int var1, EnumDefinitionBean var2);

   EnumDefinitionBean insertNewEnumDefinition(int var1);

   EnumDefinitionBean addNewEnumDefinition();

   void removeEnumDefinition(int var1);

   String getVersion();

   XmlString xgetVersion();

   boolean isSetVersion();

   void setVersion(String var1);

   void xsetVersion(XmlString var1);

   void unsetVersion();

   public static final class Factory {
      public static AnnotationManifestBean newInstance() {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationManifestBean.type, (XmlOptions)null);
      }

      public static AnnotationManifestBean newInstance(XmlOptions options) {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().newInstance(AnnotationManifestBean.type, options);
      }

      public static AnnotationManifestBean parse(String xmlAsString) throws XmlException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationManifestBean.type, (XmlOptions)null);
      }

      public static AnnotationManifestBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnnotationManifestBean.type, options);
      }

      public static AnnotationManifestBean parse(File file) throws XmlException, IOException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationManifestBean.type, (XmlOptions)null);
      }

      public static AnnotationManifestBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(file, AnnotationManifestBean.type, options);
      }

      public static AnnotationManifestBean parse(URL u) throws XmlException, IOException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationManifestBean.type, (XmlOptions)null);
      }

      public static AnnotationManifestBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(u, AnnotationManifestBean.type, options);
      }

      public static AnnotationManifestBean parse(InputStream is) throws XmlException, IOException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationManifestBean.type, (XmlOptions)null);
      }

      public static AnnotationManifestBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(is, AnnotationManifestBean.type, options);
      }

      public static AnnotationManifestBean parse(Reader r) throws XmlException, IOException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationManifestBean.type, (XmlOptions)null);
      }

      public static AnnotationManifestBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(r, AnnotationManifestBean.type, options);
      }

      public static AnnotationManifestBean parse(XMLStreamReader sr) throws XmlException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationManifestBean.type, (XmlOptions)null);
      }

      public static AnnotationManifestBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(sr, AnnotationManifestBean.type, options);
      }

      public static AnnotationManifestBean parse(Node node) throws XmlException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationManifestBean.type, (XmlOptions)null);
      }

      public static AnnotationManifestBean parse(Node node, XmlOptions options) throws XmlException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(node, AnnotationManifestBean.type, options);
      }

      /** @deprecated */
      public static AnnotationManifestBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationManifestBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnnotationManifestBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnnotationManifestBean)XmlBeans.getContextTypeLoader().parse(xis, AnnotationManifestBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationManifestBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnnotationManifestBean.type, options);
      }

      private Factory() {
      }
   }
}
