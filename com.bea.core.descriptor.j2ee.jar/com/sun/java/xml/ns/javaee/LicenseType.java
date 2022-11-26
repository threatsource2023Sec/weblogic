package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface LicenseType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LicenseType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("licensetypefbbctype");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   TrueFalseType getLicenseRequired();

   void setLicenseRequired(TrueFalseType var1);

   TrueFalseType addNewLicenseRequired();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static LicenseType newInstance() {
         return (LicenseType)XmlBeans.getContextTypeLoader().newInstance(LicenseType.type, (XmlOptions)null);
      }

      public static LicenseType newInstance(XmlOptions options) {
         return (LicenseType)XmlBeans.getContextTypeLoader().newInstance(LicenseType.type, options);
      }

      public static LicenseType parse(java.lang.String xmlAsString) throws XmlException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LicenseType.type, (XmlOptions)null);
      }

      public static LicenseType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LicenseType.type, options);
      }

      public static LicenseType parse(File file) throws XmlException, IOException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(file, LicenseType.type, (XmlOptions)null);
      }

      public static LicenseType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(file, LicenseType.type, options);
      }

      public static LicenseType parse(URL u) throws XmlException, IOException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(u, LicenseType.type, (XmlOptions)null);
      }

      public static LicenseType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(u, LicenseType.type, options);
      }

      public static LicenseType parse(InputStream is) throws XmlException, IOException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(is, LicenseType.type, (XmlOptions)null);
      }

      public static LicenseType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(is, LicenseType.type, options);
      }

      public static LicenseType parse(Reader r) throws XmlException, IOException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(r, LicenseType.type, (XmlOptions)null);
      }

      public static LicenseType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(r, LicenseType.type, options);
      }

      public static LicenseType parse(XMLStreamReader sr) throws XmlException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(sr, LicenseType.type, (XmlOptions)null);
      }

      public static LicenseType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(sr, LicenseType.type, options);
      }

      public static LicenseType parse(Node node) throws XmlException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(node, LicenseType.type, (XmlOptions)null);
      }

      public static LicenseType parse(Node node, XmlOptions options) throws XmlException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(node, LicenseType.type, options);
      }

      /** @deprecated */
      public static LicenseType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(xis, LicenseType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LicenseType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LicenseType)XmlBeans.getContextTypeLoader().parse(xis, LicenseType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LicenseType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LicenseType.type, options);
      }

      private Factory() {
      }
   }
}
