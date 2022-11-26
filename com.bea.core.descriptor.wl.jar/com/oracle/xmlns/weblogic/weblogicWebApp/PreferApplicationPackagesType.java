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

public interface PreferApplicationPackagesType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PreferApplicationPackagesType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("preferapplicationpackagestypec1e8type");

   String[] getPackageNameArray();

   String getPackageNameArray(int var1);

   XmlString[] xgetPackageNameArray();

   XmlString xgetPackageNameArray(int var1);

   int sizeOfPackageNameArray();

   void setPackageNameArray(String[] var1);

   void setPackageNameArray(int var1, String var2);

   void xsetPackageNameArray(XmlString[] var1);

   void xsetPackageNameArray(int var1, XmlString var2);

   void insertPackageName(int var1, String var2);

   void addPackageName(String var1);

   XmlString insertNewPackageName(int var1);

   XmlString addNewPackageName();

   void removePackageName(int var1);

   public static final class Factory {
      public static PreferApplicationPackagesType newInstance() {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().newInstance(PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      public static PreferApplicationPackagesType newInstance(XmlOptions options) {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().newInstance(PreferApplicationPackagesType.type, options);
      }

      public static PreferApplicationPackagesType parse(String xmlAsString) throws XmlException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      public static PreferApplicationPackagesType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PreferApplicationPackagesType.type, options);
      }

      public static PreferApplicationPackagesType parse(File file) throws XmlException, IOException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(file, PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      public static PreferApplicationPackagesType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(file, PreferApplicationPackagesType.type, options);
      }

      public static PreferApplicationPackagesType parse(URL u) throws XmlException, IOException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(u, PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      public static PreferApplicationPackagesType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(u, PreferApplicationPackagesType.type, options);
      }

      public static PreferApplicationPackagesType parse(InputStream is) throws XmlException, IOException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(is, PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      public static PreferApplicationPackagesType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(is, PreferApplicationPackagesType.type, options);
      }

      public static PreferApplicationPackagesType parse(Reader r) throws XmlException, IOException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(r, PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      public static PreferApplicationPackagesType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(r, PreferApplicationPackagesType.type, options);
      }

      public static PreferApplicationPackagesType parse(XMLStreamReader sr) throws XmlException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(sr, PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      public static PreferApplicationPackagesType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(sr, PreferApplicationPackagesType.type, options);
      }

      public static PreferApplicationPackagesType parse(Node node) throws XmlException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(node, PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      public static PreferApplicationPackagesType parse(Node node, XmlOptions options) throws XmlException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(node, PreferApplicationPackagesType.type, options);
      }

      /** @deprecated */
      public static PreferApplicationPackagesType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(xis, PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PreferApplicationPackagesType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PreferApplicationPackagesType)XmlBeans.getContextTypeLoader().parse(xis, PreferApplicationPackagesType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PreferApplicationPackagesType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PreferApplicationPackagesType.type, options);
      }

      private Factory() {
      }
   }
}
