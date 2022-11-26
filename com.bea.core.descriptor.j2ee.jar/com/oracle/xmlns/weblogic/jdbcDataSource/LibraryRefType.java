package com.oracle.xmlns.weblogic.jdbcDataSource;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface LibraryRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LibraryRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("libraryreftype3a69type");

   String getLibraryName();

   XmlString xgetLibraryName();

   void setLibraryName(String var1);

   void xsetLibraryName(XmlString var1);

   String getSpecificationVersion();

   XmlString xgetSpecificationVersion();

   boolean isSetSpecificationVersion();

   void setSpecificationVersion(String var1);

   void xsetSpecificationVersion(XmlString var1);

   void unsetSpecificationVersion();

   String getImplementationVersion();

   XmlString xgetImplementationVersion();

   boolean isSetImplementationVersion();

   void setImplementationVersion(String var1);

   void xsetImplementationVersion(XmlString var1);

   void unsetImplementationVersion();

   boolean getExactMatch();

   XmlBoolean xgetExactMatch();

   boolean isSetExactMatch();

   void setExactMatch(boolean var1);

   void xsetExactMatch(XmlBoolean var1);

   void unsetExactMatch();

   String getContextRoot();

   XmlString xgetContextRoot();

   boolean isSetContextRoot();

   void setContextRoot(String var1);

   void xsetContextRoot(XmlString var1);

   void unsetContextRoot();

   public static final class Factory {
      public static LibraryRefType newInstance() {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().newInstance(LibraryRefType.type, (XmlOptions)null);
      }

      public static LibraryRefType newInstance(XmlOptions options) {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().newInstance(LibraryRefType.type, options);
      }

      public static LibraryRefType parse(String xmlAsString) throws XmlException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LibraryRefType.type, (XmlOptions)null);
      }

      public static LibraryRefType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LibraryRefType.type, options);
      }

      public static LibraryRefType parse(File file) throws XmlException, IOException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(file, LibraryRefType.type, (XmlOptions)null);
      }

      public static LibraryRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(file, LibraryRefType.type, options);
      }

      public static LibraryRefType parse(URL u) throws XmlException, IOException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(u, LibraryRefType.type, (XmlOptions)null);
      }

      public static LibraryRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(u, LibraryRefType.type, options);
      }

      public static LibraryRefType parse(InputStream is) throws XmlException, IOException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(is, LibraryRefType.type, (XmlOptions)null);
      }

      public static LibraryRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(is, LibraryRefType.type, options);
      }

      public static LibraryRefType parse(Reader r) throws XmlException, IOException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(r, LibraryRefType.type, (XmlOptions)null);
      }

      public static LibraryRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(r, LibraryRefType.type, options);
      }

      public static LibraryRefType parse(XMLStreamReader sr) throws XmlException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(sr, LibraryRefType.type, (XmlOptions)null);
      }

      public static LibraryRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(sr, LibraryRefType.type, options);
      }

      public static LibraryRefType parse(Node node) throws XmlException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(node, LibraryRefType.type, (XmlOptions)null);
      }

      public static LibraryRefType parse(Node node, XmlOptions options) throws XmlException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(node, LibraryRefType.type, options);
      }

      /** @deprecated */
      public static LibraryRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(xis, LibraryRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LibraryRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LibraryRefType)XmlBeans.getContextTypeLoader().parse(xis, LibraryRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LibraryRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LibraryRefType.type, options);
      }

      private Factory() {
      }
   }
}
