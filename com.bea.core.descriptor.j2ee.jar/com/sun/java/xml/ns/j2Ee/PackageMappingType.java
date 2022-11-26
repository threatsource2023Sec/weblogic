package com.sun.java.xml.ns.j2Ee;

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

public interface PackageMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PackageMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("packagemappingtype67a8type");

   FullyQualifiedClassType getPackageType();

   void setPackageType(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewPackageType();

   XsdAnyURIType getNamespaceURI();

   void setNamespaceURI(XsdAnyURIType var1);

   XsdAnyURIType addNewNamespaceURI();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static PackageMappingType newInstance() {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().newInstance(PackageMappingType.type, (XmlOptions)null);
      }

      public static PackageMappingType newInstance(XmlOptions options) {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().newInstance(PackageMappingType.type, options);
      }

      public static PackageMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PackageMappingType.type, (XmlOptions)null);
      }

      public static PackageMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, PackageMappingType.type, options);
      }

      public static PackageMappingType parse(File file) throws XmlException, IOException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(file, PackageMappingType.type, (XmlOptions)null);
      }

      public static PackageMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(file, PackageMappingType.type, options);
      }

      public static PackageMappingType parse(URL u) throws XmlException, IOException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(u, PackageMappingType.type, (XmlOptions)null);
      }

      public static PackageMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(u, PackageMappingType.type, options);
      }

      public static PackageMappingType parse(InputStream is) throws XmlException, IOException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(is, PackageMappingType.type, (XmlOptions)null);
      }

      public static PackageMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(is, PackageMappingType.type, options);
      }

      public static PackageMappingType parse(Reader r) throws XmlException, IOException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(r, PackageMappingType.type, (XmlOptions)null);
      }

      public static PackageMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(r, PackageMappingType.type, options);
      }

      public static PackageMappingType parse(XMLStreamReader sr) throws XmlException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(sr, PackageMappingType.type, (XmlOptions)null);
      }

      public static PackageMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(sr, PackageMappingType.type, options);
      }

      public static PackageMappingType parse(Node node) throws XmlException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(node, PackageMappingType.type, (XmlOptions)null);
      }

      public static PackageMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(node, PackageMappingType.type, options);
      }

      /** @deprecated */
      public static PackageMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(xis, PackageMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static PackageMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (PackageMappingType)XmlBeans.getContextTypeLoader().parse(xis, PackageMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PackageMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, PackageMappingType.type, options);
      }

      private Factory() {
      }
   }
}
