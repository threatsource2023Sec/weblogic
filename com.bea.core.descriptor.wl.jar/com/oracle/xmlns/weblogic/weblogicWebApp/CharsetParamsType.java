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

public interface CharsetParamsType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CharsetParamsType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("charsetparamstypee3datype");

   InputCharsetType[] getInputCharsetArray();

   InputCharsetType getInputCharsetArray(int var1);

   int sizeOfInputCharsetArray();

   void setInputCharsetArray(InputCharsetType[] var1);

   void setInputCharsetArray(int var1, InputCharsetType var2);

   InputCharsetType insertNewInputCharset(int var1);

   InputCharsetType addNewInputCharset();

   void removeInputCharset(int var1);

   CharsetMappingType[] getCharsetMappingArray();

   CharsetMappingType getCharsetMappingArray(int var1);

   int sizeOfCharsetMappingArray();

   void setCharsetMappingArray(CharsetMappingType[] var1);

   void setCharsetMappingArray(int var1, CharsetMappingType var2);

   CharsetMappingType insertNewCharsetMapping(int var1);

   CharsetMappingType addNewCharsetMapping();

   void removeCharsetMapping(int var1);

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CharsetParamsType newInstance() {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().newInstance(CharsetParamsType.type, (XmlOptions)null);
      }

      public static CharsetParamsType newInstance(XmlOptions options) {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().newInstance(CharsetParamsType.type, options);
      }

      public static CharsetParamsType parse(String xmlAsString) throws XmlException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CharsetParamsType.type, (XmlOptions)null);
      }

      public static CharsetParamsType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CharsetParamsType.type, options);
      }

      public static CharsetParamsType parse(File file) throws XmlException, IOException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(file, CharsetParamsType.type, (XmlOptions)null);
      }

      public static CharsetParamsType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(file, CharsetParamsType.type, options);
      }

      public static CharsetParamsType parse(URL u) throws XmlException, IOException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(u, CharsetParamsType.type, (XmlOptions)null);
      }

      public static CharsetParamsType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(u, CharsetParamsType.type, options);
      }

      public static CharsetParamsType parse(InputStream is) throws XmlException, IOException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(is, CharsetParamsType.type, (XmlOptions)null);
      }

      public static CharsetParamsType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(is, CharsetParamsType.type, options);
      }

      public static CharsetParamsType parse(Reader r) throws XmlException, IOException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(r, CharsetParamsType.type, (XmlOptions)null);
      }

      public static CharsetParamsType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(r, CharsetParamsType.type, options);
      }

      public static CharsetParamsType parse(XMLStreamReader sr) throws XmlException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(sr, CharsetParamsType.type, (XmlOptions)null);
      }

      public static CharsetParamsType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(sr, CharsetParamsType.type, options);
      }

      public static CharsetParamsType parse(Node node) throws XmlException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(node, CharsetParamsType.type, (XmlOptions)null);
      }

      public static CharsetParamsType parse(Node node, XmlOptions options) throws XmlException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(node, CharsetParamsType.type, options);
      }

      /** @deprecated */
      public static CharsetParamsType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(xis, CharsetParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CharsetParamsType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CharsetParamsType)XmlBeans.getContextTypeLoader().parse(xis, CharsetParamsType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CharsetParamsType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CharsetParamsType.type, options);
      }

      private Factory() {
      }
   }
}
