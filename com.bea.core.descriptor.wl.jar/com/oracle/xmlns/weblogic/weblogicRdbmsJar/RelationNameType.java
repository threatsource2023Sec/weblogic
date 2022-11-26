package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface RelationNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RelationNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("relationnametype9c39type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RelationNameType newInstance() {
         return (RelationNameType)XmlBeans.getContextTypeLoader().newInstance(RelationNameType.type, (XmlOptions)null);
      }

      public static RelationNameType newInstance(XmlOptions options) {
         return (RelationNameType)XmlBeans.getContextTypeLoader().newInstance(RelationNameType.type, options);
      }

      public static RelationNameType parse(String xmlAsString) throws XmlException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationNameType.type, (XmlOptions)null);
      }

      public static RelationNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RelationNameType.type, options);
      }

      public static RelationNameType parse(File file) throws XmlException, IOException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(file, RelationNameType.type, (XmlOptions)null);
      }

      public static RelationNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(file, RelationNameType.type, options);
      }

      public static RelationNameType parse(URL u) throws XmlException, IOException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(u, RelationNameType.type, (XmlOptions)null);
      }

      public static RelationNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(u, RelationNameType.type, options);
      }

      public static RelationNameType parse(InputStream is) throws XmlException, IOException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(is, RelationNameType.type, (XmlOptions)null);
      }

      public static RelationNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(is, RelationNameType.type, options);
      }

      public static RelationNameType parse(Reader r) throws XmlException, IOException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(r, RelationNameType.type, (XmlOptions)null);
      }

      public static RelationNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(r, RelationNameType.type, options);
      }

      public static RelationNameType parse(XMLStreamReader sr) throws XmlException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(sr, RelationNameType.type, (XmlOptions)null);
      }

      public static RelationNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(sr, RelationNameType.type, options);
      }

      public static RelationNameType parse(Node node) throws XmlException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(node, RelationNameType.type, (XmlOptions)null);
      }

      public static RelationNameType parse(Node node, XmlOptions options) throws XmlException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(node, RelationNameType.type, options);
      }

      /** @deprecated */
      public static RelationNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(xis, RelationNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RelationNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RelationNameType)XmlBeans.getContextTypeLoader().parse(xis, RelationNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RelationNameType.type, options);
      }

      private Factory() {
      }
   }
}
