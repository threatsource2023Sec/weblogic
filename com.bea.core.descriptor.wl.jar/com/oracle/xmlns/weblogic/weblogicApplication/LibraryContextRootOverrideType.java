package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface LibraryContextRootOverrideType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LibraryContextRootOverrideType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("librarycontextrootoverridetype5210type");

   String getContextRoot();

   XmlString xgetContextRoot();

   void setContextRoot(String var1);

   void xsetContextRoot(XmlString var1);

   String getOverrideValue();

   XmlString xgetOverrideValue();

   void setOverrideValue(String var1);

   void xsetOverrideValue(XmlString var1);

   public static final class Factory {
      public static LibraryContextRootOverrideType newInstance() {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().newInstance(LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      public static LibraryContextRootOverrideType newInstance(XmlOptions options) {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().newInstance(LibraryContextRootOverrideType.type, options);
      }

      public static LibraryContextRootOverrideType parse(String xmlAsString) throws XmlException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      public static LibraryContextRootOverrideType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LibraryContextRootOverrideType.type, options);
      }

      public static LibraryContextRootOverrideType parse(File file) throws XmlException, IOException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(file, LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      public static LibraryContextRootOverrideType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(file, LibraryContextRootOverrideType.type, options);
      }

      public static LibraryContextRootOverrideType parse(URL u) throws XmlException, IOException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(u, LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      public static LibraryContextRootOverrideType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(u, LibraryContextRootOverrideType.type, options);
      }

      public static LibraryContextRootOverrideType parse(InputStream is) throws XmlException, IOException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(is, LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      public static LibraryContextRootOverrideType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(is, LibraryContextRootOverrideType.type, options);
      }

      public static LibraryContextRootOverrideType parse(Reader r) throws XmlException, IOException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(r, LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      public static LibraryContextRootOverrideType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(r, LibraryContextRootOverrideType.type, options);
      }

      public static LibraryContextRootOverrideType parse(XMLStreamReader sr) throws XmlException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(sr, LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      public static LibraryContextRootOverrideType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(sr, LibraryContextRootOverrideType.type, options);
      }

      public static LibraryContextRootOverrideType parse(Node node) throws XmlException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(node, LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      public static LibraryContextRootOverrideType parse(Node node, XmlOptions options) throws XmlException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(node, LibraryContextRootOverrideType.type, options);
      }

      /** @deprecated */
      public static LibraryContextRootOverrideType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(xis, LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LibraryContextRootOverrideType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LibraryContextRootOverrideType)XmlBeans.getContextTypeLoader().parse(xis, LibraryContextRootOverrideType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LibraryContextRootOverrideType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LibraryContextRootOverrideType.type, options);
      }

      private Factory() {
      }
   }
}
