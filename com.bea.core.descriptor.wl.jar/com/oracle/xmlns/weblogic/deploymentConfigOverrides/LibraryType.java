package com.oracle.xmlns.weblogic.deploymentConfigOverrides;

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

public interface LibraryType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LibraryType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("librarytype182dtype");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getSourcePath();

   XmlString xgetSourcePath();

   void setSourcePath(String var1);

   void xsetSourcePath(XmlString var1);

   String getGeneratedVersion();

   XmlString xgetGeneratedVersion();

   boolean isSetGeneratedVersion();

   void setGeneratedVersion(String var1);

   void xsetGeneratedVersion(XmlString var1);

   void unsetGeneratedVersion();

   public static final class Factory {
      public static LibraryType newInstance() {
         return (LibraryType)XmlBeans.getContextTypeLoader().newInstance(LibraryType.type, (XmlOptions)null);
      }

      public static LibraryType newInstance(XmlOptions options) {
         return (LibraryType)XmlBeans.getContextTypeLoader().newInstance(LibraryType.type, options);
      }

      public static LibraryType parse(String xmlAsString) throws XmlException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LibraryType.type, (XmlOptions)null);
      }

      public static LibraryType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LibraryType.type, options);
      }

      public static LibraryType parse(File file) throws XmlException, IOException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(file, LibraryType.type, (XmlOptions)null);
      }

      public static LibraryType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(file, LibraryType.type, options);
      }

      public static LibraryType parse(URL u) throws XmlException, IOException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(u, LibraryType.type, (XmlOptions)null);
      }

      public static LibraryType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(u, LibraryType.type, options);
      }

      public static LibraryType parse(InputStream is) throws XmlException, IOException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(is, LibraryType.type, (XmlOptions)null);
      }

      public static LibraryType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(is, LibraryType.type, options);
      }

      public static LibraryType parse(Reader r) throws XmlException, IOException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(r, LibraryType.type, (XmlOptions)null);
      }

      public static LibraryType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(r, LibraryType.type, options);
      }

      public static LibraryType parse(XMLStreamReader sr) throws XmlException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(sr, LibraryType.type, (XmlOptions)null);
      }

      public static LibraryType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(sr, LibraryType.type, options);
      }

      public static LibraryType parse(Node node) throws XmlException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(node, LibraryType.type, (XmlOptions)null);
      }

      public static LibraryType parse(Node node, XmlOptions options) throws XmlException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(node, LibraryType.type, options);
      }

      /** @deprecated */
      public static LibraryType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(xis, LibraryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LibraryType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LibraryType)XmlBeans.getContextTypeLoader().parse(xis, LibraryType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LibraryType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LibraryType.type, options);
      }

      private Factory() {
      }
   }
}
