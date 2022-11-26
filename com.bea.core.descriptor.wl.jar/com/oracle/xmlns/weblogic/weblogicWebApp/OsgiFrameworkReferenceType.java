package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface OsgiFrameworkReferenceType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(OsgiFrameworkReferenceType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("osgiframeworkreferencetype58a8type");

   String getName();

   XmlString xgetName();

   void setName(String var1);

   void xsetName(XmlString var1);

   String getBundlesDirectory();

   XmlString xgetBundlesDirectory();

   boolean isSetBundlesDirectory();

   void setBundlesDirectory(String var1);

   void xsetBundlesDirectory(XmlString var1);

   void unsetBundlesDirectory();

   String getApplicationBundleSymbolicName();

   XmlString xgetApplicationBundleSymbolicName();

   boolean isSetApplicationBundleSymbolicName();

   void setApplicationBundleSymbolicName(String var1);

   void xsetApplicationBundleSymbolicName(XmlString var1);

   void unsetApplicationBundleSymbolicName();

   String getApplicationBundleVersion();

   XmlString xgetApplicationBundleVersion();

   boolean isSetApplicationBundleVersion();

   void setApplicationBundleVersion(String var1);

   void xsetApplicationBundleVersion(XmlString var1);

   void unsetApplicationBundleVersion();

   public static final class Factory {
      public static OsgiFrameworkReferenceType newInstance() {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().newInstance(OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      public static OsgiFrameworkReferenceType newInstance(XmlOptions options) {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().newInstance(OsgiFrameworkReferenceType.type, options);
      }

      public static OsgiFrameworkReferenceType parse(String xmlAsString) throws XmlException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      public static OsgiFrameworkReferenceType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(xmlAsString, OsgiFrameworkReferenceType.type, options);
      }

      public static OsgiFrameworkReferenceType parse(File file) throws XmlException, IOException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(file, OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      public static OsgiFrameworkReferenceType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(file, OsgiFrameworkReferenceType.type, options);
      }

      public static OsgiFrameworkReferenceType parse(URL u) throws XmlException, IOException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(u, OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      public static OsgiFrameworkReferenceType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(u, OsgiFrameworkReferenceType.type, options);
      }

      public static OsgiFrameworkReferenceType parse(InputStream is) throws XmlException, IOException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(is, OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      public static OsgiFrameworkReferenceType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(is, OsgiFrameworkReferenceType.type, options);
      }

      public static OsgiFrameworkReferenceType parse(Reader r) throws XmlException, IOException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(r, OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      public static OsgiFrameworkReferenceType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(r, OsgiFrameworkReferenceType.type, options);
      }

      public static OsgiFrameworkReferenceType parse(XMLStreamReader sr) throws XmlException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(sr, OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      public static OsgiFrameworkReferenceType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(sr, OsgiFrameworkReferenceType.type, options);
      }

      public static OsgiFrameworkReferenceType parse(Node node) throws XmlException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(node, OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      public static OsgiFrameworkReferenceType parse(Node node, XmlOptions options) throws XmlException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(node, OsgiFrameworkReferenceType.type, options);
      }

      /** @deprecated */
      public static OsgiFrameworkReferenceType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(xis, OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static OsgiFrameworkReferenceType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (OsgiFrameworkReferenceType)XmlBeans.getContextTypeLoader().parse(xis, OsgiFrameworkReferenceType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OsgiFrameworkReferenceType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, OsgiFrameworkReferenceType.type, options);
      }

      private Factory() {
      }
   }
}
