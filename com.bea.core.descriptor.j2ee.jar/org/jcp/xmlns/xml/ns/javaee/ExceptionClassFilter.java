package org.jcp.xmlns.xml.ns.javaee;

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

public interface ExceptionClassFilter extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ExceptionClassFilter.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("exceptionclassfilter9d24type");

   Include[] getIncludeArray();

   Include getIncludeArray(int var1);

   int sizeOfIncludeArray();

   void setIncludeArray(Include[] var1);

   void setIncludeArray(int var1, Include var2);

   Include insertNewInclude(int var1);

   Include addNewInclude();

   void removeInclude(int var1);

   Exclude[] getExcludeArray();

   Exclude getExcludeArray(int var1);

   int sizeOfExcludeArray();

   void setExcludeArray(Exclude[] var1);

   void setExcludeArray(int var1, Exclude var2);

   Exclude insertNewExclude(int var1);

   Exclude addNewExclude();

   void removeExclude(int var1);

   public static final class Factory {
      public static ExceptionClassFilter newInstance() {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().newInstance(ExceptionClassFilter.type, (XmlOptions)null);
      }

      public static ExceptionClassFilter newInstance(XmlOptions options) {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().newInstance(ExceptionClassFilter.type, options);
      }

      public static ExceptionClassFilter parse(java.lang.String xmlAsString) throws XmlException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExceptionClassFilter.type, (XmlOptions)null);
      }

      public static ExceptionClassFilter parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(xmlAsString, ExceptionClassFilter.type, options);
      }

      public static ExceptionClassFilter parse(File file) throws XmlException, IOException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(file, ExceptionClassFilter.type, (XmlOptions)null);
      }

      public static ExceptionClassFilter parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(file, ExceptionClassFilter.type, options);
      }

      public static ExceptionClassFilter parse(URL u) throws XmlException, IOException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(u, ExceptionClassFilter.type, (XmlOptions)null);
      }

      public static ExceptionClassFilter parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(u, ExceptionClassFilter.type, options);
      }

      public static ExceptionClassFilter parse(InputStream is) throws XmlException, IOException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(is, ExceptionClassFilter.type, (XmlOptions)null);
      }

      public static ExceptionClassFilter parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(is, ExceptionClassFilter.type, options);
      }

      public static ExceptionClassFilter parse(Reader r) throws XmlException, IOException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(r, ExceptionClassFilter.type, (XmlOptions)null);
      }

      public static ExceptionClassFilter parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(r, ExceptionClassFilter.type, options);
      }

      public static ExceptionClassFilter parse(XMLStreamReader sr) throws XmlException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(sr, ExceptionClassFilter.type, (XmlOptions)null);
      }

      public static ExceptionClassFilter parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(sr, ExceptionClassFilter.type, options);
      }

      public static ExceptionClassFilter parse(Node node) throws XmlException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(node, ExceptionClassFilter.type, (XmlOptions)null);
      }

      public static ExceptionClassFilter parse(Node node, XmlOptions options) throws XmlException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(node, ExceptionClassFilter.type, options);
      }

      /** @deprecated */
      public static ExceptionClassFilter parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(xis, ExceptionClassFilter.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ExceptionClassFilter parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ExceptionClassFilter)XmlBeans.getContextTypeLoader().parse(xis, ExceptionClassFilter.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExceptionClassFilter.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ExceptionClassFilter.type, options);
      }

      private Factory() {
      }
   }

   public interface Exclude extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Exclude.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("exclude93eaelemtype");

      java.lang.String getClass1();

      XmlString xgetClass1();

      void setClass1(java.lang.String var1);

      void xsetClass1(XmlString var1);

      public static final class Factory {
         public static Exclude newInstance() {
            return (Exclude)XmlBeans.getContextTypeLoader().newInstance(ExceptionClassFilter.Exclude.type, (XmlOptions)null);
         }

         public static Exclude newInstance(XmlOptions options) {
            return (Exclude)XmlBeans.getContextTypeLoader().newInstance(ExceptionClassFilter.Exclude.type, options);
         }

         private Factory() {
         }
      }
   }

   public interface Include extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Include.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("include4d38elemtype");

      java.lang.String getClass1();

      XmlString xgetClass1();

      void setClass1(java.lang.String var1);

      void xsetClass1(XmlString var1);

      public static final class Factory {
         public static Include newInstance() {
            return (Include)XmlBeans.getContextTypeLoader().newInstance(ExceptionClassFilter.Include.type, (XmlOptions)null);
         }

         public static Include newInstance(XmlOptions options) {
            return (Include)XmlBeans.getContextTypeLoader().newInstance(ExceptionClassFilter.Include.type, options);
         }

         private Factory() {
         }
      }
   }
}
