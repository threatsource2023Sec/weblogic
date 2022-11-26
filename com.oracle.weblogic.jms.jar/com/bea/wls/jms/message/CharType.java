package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface CharType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CharType.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("chartype5197type");

   public static final class Factory {
      public static CharType newValue(Object obj) {
         return (CharType)CharType.type.newValue(obj);
      }

      public static CharType newInstance() {
         return (CharType)XmlBeans.getContextTypeLoader().newInstance(CharType.type, (XmlOptions)null);
      }

      public static CharType newInstance(XmlOptions options) {
         return (CharType)XmlBeans.getContextTypeLoader().newInstance(CharType.type, options);
      }

      public static CharType parse(String xmlAsString) throws XmlException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CharType.type, (XmlOptions)null);
      }

      public static CharType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CharType.type, options);
      }

      public static CharType parse(File file) throws XmlException, IOException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(file, CharType.type, (XmlOptions)null);
      }

      public static CharType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(file, CharType.type, options);
      }

      public static CharType parse(URL u) throws XmlException, IOException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(u, CharType.type, (XmlOptions)null);
      }

      public static CharType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(u, CharType.type, options);
      }

      public static CharType parse(InputStream is) throws XmlException, IOException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(is, CharType.type, (XmlOptions)null);
      }

      public static CharType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(is, CharType.type, options);
      }

      public static CharType parse(Reader r) throws XmlException, IOException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(r, CharType.type, (XmlOptions)null);
      }

      public static CharType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(r, CharType.type, options);
      }

      public static CharType parse(XMLStreamReader sr) throws XmlException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(sr, CharType.type, (XmlOptions)null);
      }

      public static CharType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(sr, CharType.type, options);
      }

      public static CharType parse(Node node) throws XmlException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(node, CharType.type, (XmlOptions)null);
      }

      public static CharType parse(Node node, XmlOptions options) throws XmlException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(node, CharType.type, options);
      }

      /** @deprecated */
      public static CharType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(xis, CharType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CharType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CharType)XmlBeans.getContextTypeLoader().parse(xis, CharType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CharType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CharType.type, options);
      }

      private Factory() {
      }
   }
}
