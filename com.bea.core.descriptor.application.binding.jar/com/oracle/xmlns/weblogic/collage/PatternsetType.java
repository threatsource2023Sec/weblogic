package com.oracle.xmlns.weblogic.collage;

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

public interface PatternsetType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PatternsetType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("patternsettyped4b3type");

   String[] getIncludeArray();

   String getIncludeArray(int var1);

   XmlString[] xgetIncludeArray();

   XmlString xgetIncludeArray(int var1);

   int sizeOfIncludeArray();

   void setIncludeArray(String[] var1);

   void setIncludeArray(int var1, String var2);

   void xsetIncludeArray(XmlString[] var1);

   void xsetIncludeArray(int var1, XmlString var2);

   void insertInclude(int var1, String var2);

   void addInclude(String var1);

   XmlString insertNewInclude(int var1);

   XmlString addNewInclude();

   void removeInclude(int var1);

   String[] getExcludeArray();

   String getExcludeArray(int var1);

   XmlString[] xgetExcludeArray();

   XmlString xgetExcludeArray(int var1);

   int sizeOfExcludeArray();

   void setExcludeArray(String[] var1);

   void setExcludeArray(int var1, String var2);

   void xsetExcludeArray(XmlString[] var1);

   void xsetExcludeArray(int var1, XmlString var2);

   void insertExclude(int var1, String var2);

   void addExclude(String var1);

   XmlString insertNewExclude(int var1);

   XmlString addNewExclude();

   void removeExclude(int var1);

   String getId();

   XmlString xgetId();

   void setId(String var1);

   void xsetId(XmlString var1);

   public static final class Factory {
      public static PatternsetType newInstance() {
         return (PatternsetType)XmlBeans.getContextTypeLoader().newInstance(PatternsetType.type, (XmlOptions)null);
      }

      public static PatternsetType newInstance(XmlOptions options) {
         return (PatternsetType)XmlBeans.getContextTypeLoader().newInstance(PatternsetType.type, options);
      }

      public static PatternsetType parse(String xmlAsString) throws XmlException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PatternsetType.type, (XmlOptions)null);
      }

      public static PatternsetType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PatternsetType.type, options);
      }

      public static PatternsetType parse(File file) throws XmlException, IOException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(file, PatternsetType.type, (XmlOptions)null);
      }

      public static PatternsetType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(file, PatternsetType.type, options);
      }

      public static PatternsetType parse(URL u) throws XmlException, IOException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(u, PatternsetType.type, (XmlOptions)null);
      }

      public static PatternsetType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(u, PatternsetType.type, options);
      }

      public static PatternsetType parse(InputStream is) throws XmlException, IOException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(is, PatternsetType.type, (XmlOptions)null);
      }

      public static PatternsetType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(is, PatternsetType.type, options);
      }

      public static PatternsetType parse(Reader r) throws XmlException, IOException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(r, PatternsetType.type, (XmlOptions)null);
      }

      public static PatternsetType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(r, PatternsetType.type, options);
      }

      public static PatternsetType parse(XMLStreamReader sr) throws XmlException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(sr, PatternsetType.type, (XmlOptions)null);
      }

      public static PatternsetType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(sr, PatternsetType.type, options);
      }

      public static PatternsetType parse(Node node) throws XmlException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(node, PatternsetType.type, (XmlOptions)null);
      }

      public static PatternsetType parse(Node node, XmlOptions options) throws XmlException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(node, PatternsetType.type, options);
      }

      /** @deprecated */
      public static PatternsetType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(xis, PatternsetType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PatternsetType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PatternsetType)XmlBeans.getContextTypeLoader().parse(xis, PatternsetType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PatternsetType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PatternsetType.type, options);
      }

      private Factory() {
      }
   }
}
