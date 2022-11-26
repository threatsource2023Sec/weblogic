package com.bea.connector.monitoring1Dot0;

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

public interface LicenseDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LicenseDocument.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("licenseb85fdoctype");

   License getLicense();

   void setLicense(License var1);

   License addNewLicense();

   public static final class Factory {
      public static LicenseDocument newInstance() {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().newInstance(LicenseDocument.type, (XmlOptions)null);
      }

      public static LicenseDocument newInstance(XmlOptions options) {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().newInstance(LicenseDocument.type, options);
      }

      public static LicenseDocument parse(String xmlAsString) throws XmlException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, LicenseDocument.type, (XmlOptions)null);
      }

      public static LicenseDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, LicenseDocument.type, options);
      }

      public static LicenseDocument parse(File file) throws XmlException, IOException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(file, LicenseDocument.type, (XmlOptions)null);
      }

      public static LicenseDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(file, LicenseDocument.type, options);
      }

      public static LicenseDocument parse(URL u) throws XmlException, IOException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(u, LicenseDocument.type, (XmlOptions)null);
      }

      public static LicenseDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(u, LicenseDocument.type, options);
      }

      public static LicenseDocument parse(InputStream is) throws XmlException, IOException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(is, LicenseDocument.type, (XmlOptions)null);
      }

      public static LicenseDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(is, LicenseDocument.type, options);
      }

      public static LicenseDocument parse(Reader r) throws XmlException, IOException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(r, LicenseDocument.type, (XmlOptions)null);
      }

      public static LicenseDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(r, LicenseDocument.type, options);
      }

      public static LicenseDocument parse(XMLStreamReader sr) throws XmlException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(sr, LicenseDocument.type, (XmlOptions)null);
      }

      public static LicenseDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(sr, LicenseDocument.type, options);
      }

      public static LicenseDocument parse(Node node) throws XmlException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(node, LicenseDocument.type, (XmlOptions)null);
      }

      public static LicenseDocument parse(Node node, XmlOptions options) throws XmlException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(node, LicenseDocument.type, options);
      }

      /** @deprecated */
      public static LicenseDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(xis, LicenseDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LicenseDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LicenseDocument)XmlBeans.getContextTypeLoader().parse(xis, LicenseDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LicenseDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LicenseDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface License extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(License.class.getClassLoader(), "schemacom_bea_xml.system.conn_mon").resolveHandle("license9912elemtype");

      String[] getDescriptionArray();

      String getDescriptionArray(int var1);

      XmlString[] xgetDescriptionArray();

      XmlString xgetDescriptionArray(int var1);

      int sizeOfDescriptionArray();

      void setDescriptionArray(String[] var1);

      void setDescriptionArray(int var1, String var2);

      void xsetDescriptionArray(XmlString[] var1);

      void xsetDescriptionArray(int var1, XmlString var2);

      void insertDescription(int var1, String var2);

      void addDescription(String var1);

      XmlString insertNewDescription(int var1);

      XmlString addNewDescription();

      void removeDescription(int var1);

      boolean getLicenseRequired();

      XmlBoolean xgetLicenseRequired();

      void setLicenseRequired(boolean var1);

      void xsetLicenseRequired(XmlBoolean var1);

      public static final class Factory {
         public static License newInstance() {
            return (License)XmlBeans.getContextTypeLoader().newInstance(LicenseDocument.License.type, (XmlOptions)null);
         }

         public static License newInstance(XmlOptions options) {
            return (License)XmlBeans.getContextTypeLoader().newInstance(LicenseDocument.License.type, options);
         }

         private Factory() {
         }
      }
   }
}
