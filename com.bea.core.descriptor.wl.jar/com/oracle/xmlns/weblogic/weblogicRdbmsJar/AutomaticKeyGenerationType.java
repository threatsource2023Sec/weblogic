package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.XsdPositiveIntegerType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface AutomaticKeyGenerationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AutomaticKeyGenerationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("automatickeygenerationtypef6d7type");

   GeneratorTypeType getGeneratorType();

   void setGeneratorType(GeneratorTypeType var1);

   GeneratorTypeType addNewGeneratorType();

   GeneratorNameType getGeneratorName();

   boolean isSetGeneratorName();

   void setGeneratorName(GeneratorNameType var1);

   GeneratorNameType addNewGeneratorName();

   void unsetGeneratorName();

   XsdPositiveIntegerType getKeyCacheSize();

   boolean isSetKeyCacheSize();

   void setKeyCacheSize(XsdPositiveIntegerType var1);

   XsdPositiveIntegerType addNewKeyCacheSize();

   void unsetKeyCacheSize();

   TrueFalseType getSelectFirstSequenceKeyBeforeUpdate();

   boolean isSetSelectFirstSequenceKeyBeforeUpdate();

   void setSelectFirstSequenceKeyBeforeUpdate(TrueFalseType var1);

   TrueFalseType addNewSelectFirstSequenceKeyBeforeUpdate();

   void unsetSelectFirstSequenceKeyBeforeUpdate();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static AutomaticKeyGenerationType newInstance() {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().newInstance(AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      public static AutomaticKeyGenerationType newInstance(XmlOptions options) {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().newInstance(AutomaticKeyGenerationType.type, options);
      }

      public static AutomaticKeyGenerationType parse(String xmlAsString) throws XmlException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      public static AutomaticKeyGenerationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, AutomaticKeyGenerationType.type, options);
      }

      public static AutomaticKeyGenerationType parse(File file) throws XmlException, IOException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(file, AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      public static AutomaticKeyGenerationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(file, AutomaticKeyGenerationType.type, options);
      }

      public static AutomaticKeyGenerationType parse(URL u) throws XmlException, IOException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(u, AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      public static AutomaticKeyGenerationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(u, AutomaticKeyGenerationType.type, options);
      }

      public static AutomaticKeyGenerationType parse(InputStream is) throws XmlException, IOException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(is, AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      public static AutomaticKeyGenerationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(is, AutomaticKeyGenerationType.type, options);
      }

      public static AutomaticKeyGenerationType parse(Reader r) throws XmlException, IOException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(r, AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      public static AutomaticKeyGenerationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(r, AutomaticKeyGenerationType.type, options);
      }

      public static AutomaticKeyGenerationType parse(XMLStreamReader sr) throws XmlException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(sr, AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      public static AutomaticKeyGenerationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(sr, AutomaticKeyGenerationType.type, options);
      }

      public static AutomaticKeyGenerationType parse(Node node) throws XmlException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(node, AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      public static AutomaticKeyGenerationType parse(Node node, XmlOptions options) throws XmlException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(node, AutomaticKeyGenerationType.type, options);
      }

      /** @deprecated */
      public static AutomaticKeyGenerationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(xis, AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AutomaticKeyGenerationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AutomaticKeyGenerationType)XmlBeans.getContextTypeLoader().parse(xis, AutomaticKeyGenerationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AutomaticKeyGenerationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AutomaticKeyGenerationType.type, options);
      }

      private Factory() {
      }
   }
}
