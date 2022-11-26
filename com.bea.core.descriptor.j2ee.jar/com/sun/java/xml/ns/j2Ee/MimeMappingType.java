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

public interface MimeMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MimeMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("mimemappingtyped808type");

   String getExtension();

   void setExtension(String var1);

   String addNewExtension();

   MimeTypeType getMimeType();

   void setMimeType(MimeTypeType var1);

   MimeTypeType addNewMimeType();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MimeMappingType newInstance() {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().newInstance(MimeMappingType.type, (XmlOptions)null);
      }

      public static MimeMappingType newInstance(XmlOptions options) {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().newInstance(MimeMappingType.type, options);
      }

      public static MimeMappingType parse(java.lang.String xmlAsString) throws XmlException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MimeMappingType.type, (XmlOptions)null);
      }

      public static MimeMappingType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MimeMappingType.type, options);
      }

      public static MimeMappingType parse(File file) throws XmlException, IOException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(file, MimeMappingType.type, (XmlOptions)null);
      }

      public static MimeMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(file, MimeMappingType.type, options);
      }

      public static MimeMappingType parse(URL u) throws XmlException, IOException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(u, MimeMappingType.type, (XmlOptions)null);
      }

      public static MimeMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(u, MimeMappingType.type, options);
      }

      public static MimeMappingType parse(InputStream is) throws XmlException, IOException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(is, MimeMappingType.type, (XmlOptions)null);
      }

      public static MimeMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(is, MimeMappingType.type, options);
      }

      public static MimeMappingType parse(Reader r) throws XmlException, IOException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(r, MimeMappingType.type, (XmlOptions)null);
      }

      public static MimeMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(r, MimeMappingType.type, options);
      }

      public static MimeMappingType parse(XMLStreamReader sr) throws XmlException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(sr, MimeMappingType.type, (XmlOptions)null);
      }

      public static MimeMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(sr, MimeMappingType.type, options);
      }

      public static MimeMappingType parse(Node node) throws XmlException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(node, MimeMappingType.type, (XmlOptions)null);
      }

      public static MimeMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(node, MimeMappingType.type, options);
      }

      /** @deprecated */
      public static MimeMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(xis, MimeMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MimeMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MimeMappingType)XmlBeans.getContextTypeLoader().parse(xis, MimeMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MimeMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MimeMappingType.type, options);
      }

      private Factory() {
      }
   }
}
