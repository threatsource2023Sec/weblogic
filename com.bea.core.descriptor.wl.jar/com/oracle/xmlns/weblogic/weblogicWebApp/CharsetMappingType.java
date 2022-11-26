package com.oracle.xmlns.weblogic.weblogicWebApp;

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

public interface CharsetMappingType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CharsetMappingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("charsetmappingtype98cetype");

   IanaCharsetNameType getIanaCharsetName();

   void setIanaCharsetName(IanaCharsetNameType var1);

   IanaCharsetNameType addNewIanaCharsetName();

   JavaCharsetNameType getJavaCharsetName();

   void setJavaCharsetName(JavaCharsetNameType var1);

   JavaCharsetNameType addNewJavaCharsetName();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CharsetMappingType newInstance() {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().newInstance(CharsetMappingType.type, (XmlOptions)null);
      }

      public static CharsetMappingType newInstance(XmlOptions options) {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().newInstance(CharsetMappingType.type, options);
      }

      public static CharsetMappingType parse(String xmlAsString) throws XmlException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CharsetMappingType.type, (XmlOptions)null);
      }

      public static CharsetMappingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CharsetMappingType.type, options);
      }

      public static CharsetMappingType parse(File file) throws XmlException, IOException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(file, CharsetMappingType.type, (XmlOptions)null);
      }

      public static CharsetMappingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(file, CharsetMappingType.type, options);
      }

      public static CharsetMappingType parse(URL u) throws XmlException, IOException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(u, CharsetMappingType.type, (XmlOptions)null);
      }

      public static CharsetMappingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(u, CharsetMappingType.type, options);
      }

      public static CharsetMappingType parse(InputStream is) throws XmlException, IOException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(is, CharsetMappingType.type, (XmlOptions)null);
      }

      public static CharsetMappingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(is, CharsetMappingType.type, options);
      }

      public static CharsetMappingType parse(Reader r) throws XmlException, IOException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(r, CharsetMappingType.type, (XmlOptions)null);
      }

      public static CharsetMappingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(r, CharsetMappingType.type, options);
      }

      public static CharsetMappingType parse(XMLStreamReader sr) throws XmlException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(sr, CharsetMappingType.type, (XmlOptions)null);
      }

      public static CharsetMappingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(sr, CharsetMappingType.type, options);
      }

      public static CharsetMappingType parse(Node node) throws XmlException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(node, CharsetMappingType.type, (XmlOptions)null);
      }

      public static CharsetMappingType parse(Node node, XmlOptions options) throws XmlException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(node, CharsetMappingType.type, options);
      }

      /** @deprecated */
      public static CharsetMappingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(xis, CharsetMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CharsetMappingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CharsetMappingType)XmlBeans.getContextTypeLoader().parse(xis, CharsetMappingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CharsetMappingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CharsetMappingType.type, options);
      }

      private Factory() {
      }
   }
}
