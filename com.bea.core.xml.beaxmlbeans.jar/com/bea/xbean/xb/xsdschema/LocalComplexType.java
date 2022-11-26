package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface LocalComplexType extends ComplexType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(LocalComplexType.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("localcomplextype6494type");

   public static final class Factory {
      public static LocalComplexType newInstance() {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().newInstance(LocalComplexType.type, (XmlOptions)null);
      }

      public static LocalComplexType newInstance(XmlOptions options) {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().newInstance(LocalComplexType.type, options);
      }

      public static LocalComplexType parse(String xmlAsString) throws XmlException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, LocalComplexType.type, (XmlOptions)null);
      }

      public static LocalComplexType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse(xmlAsString, LocalComplexType.type, options);
      }

      public static LocalComplexType parse(File file) throws XmlException, IOException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse((File)file, LocalComplexType.type, (XmlOptions)null);
      }

      public static LocalComplexType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse(file, LocalComplexType.type, options);
      }

      public static LocalComplexType parse(URL u) throws XmlException, IOException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse((URL)u, LocalComplexType.type, (XmlOptions)null);
      }

      public static LocalComplexType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse(u, LocalComplexType.type, options);
      }

      public static LocalComplexType parse(InputStream is) throws XmlException, IOException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse((InputStream)is, LocalComplexType.type, (XmlOptions)null);
      }

      public static LocalComplexType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse(is, LocalComplexType.type, options);
      }

      public static LocalComplexType parse(Reader r) throws XmlException, IOException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse((Reader)r, LocalComplexType.type, (XmlOptions)null);
      }

      public static LocalComplexType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse(r, LocalComplexType.type, options);
      }

      public static LocalComplexType parse(XMLStreamReader sr) throws XmlException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, LocalComplexType.type, (XmlOptions)null);
      }

      public static LocalComplexType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse(sr, LocalComplexType.type, options);
      }

      public static LocalComplexType parse(Node node) throws XmlException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse((Node)node, LocalComplexType.type, (XmlOptions)null);
      }

      public static LocalComplexType parse(Node node, XmlOptions options) throws XmlException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse(node, LocalComplexType.type, options);
      }

      /** @deprecated */
      public static LocalComplexType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, LocalComplexType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static LocalComplexType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (LocalComplexType)XmlBeans.getContextTypeLoader().parse(xis, LocalComplexType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalComplexType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, LocalComplexType.type, options);
      }

      private Factory() {
      }
   }
}
