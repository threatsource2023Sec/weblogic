package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface FractionDigitsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(FractionDigitsDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("fractiondigitsed7bdoctype");

   NumFacet getFractionDigits();

   void setFractionDigits(NumFacet var1);

   NumFacet addNewFractionDigits();

   public static final class Factory {
      public static FractionDigitsDocument newInstance() {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().newInstance(FractionDigitsDocument.type, (XmlOptions)null);
      }

      public static FractionDigitsDocument newInstance(XmlOptions options) {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().newInstance(FractionDigitsDocument.type, options);
      }

      public static FractionDigitsDocument parse(String xmlAsString) throws XmlException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, FractionDigitsDocument.type, (XmlOptions)null);
      }

      public static FractionDigitsDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, FractionDigitsDocument.type, options);
      }

      public static FractionDigitsDocument parse(File file) throws XmlException, IOException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse((File)file, FractionDigitsDocument.type, (XmlOptions)null);
      }

      public static FractionDigitsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse(file, FractionDigitsDocument.type, options);
      }

      public static FractionDigitsDocument parse(URL u) throws XmlException, IOException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse((URL)u, FractionDigitsDocument.type, (XmlOptions)null);
      }

      public static FractionDigitsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse(u, FractionDigitsDocument.type, options);
      }

      public static FractionDigitsDocument parse(InputStream is) throws XmlException, IOException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, FractionDigitsDocument.type, (XmlOptions)null);
      }

      public static FractionDigitsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse(is, FractionDigitsDocument.type, options);
      }

      public static FractionDigitsDocument parse(Reader r) throws XmlException, IOException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, FractionDigitsDocument.type, (XmlOptions)null);
      }

      public static FractionDigitsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse(r, FractionDigitsDocument.type, options);
      }

      public static FractionDigitsDocument parse(XMLStreamReader sr) throws XmlException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, FractionDigitsDocument.type, (XmlOptions)null);
      }

      public static FractionDigitsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse(sr, FractionDigitsDocument.type, options);
      }

      public static FractionDigitsDocument parse(Node node) throws XmlException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse((Node)node, FractionDigitsDocument.type, (XmlOptions)null);
      }

      public static FractionDigitsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse(node, FractionDigitsDocument.type, options);
      }

      /** @deprecated */
      public static FractionDigitsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, FractionDigitsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static FractionDigitsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (FractionDigitsDocument)XmlBeans.getContextTypeLoader().parse(xis, FractionDigitsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FractionDigitsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, FractionDigitsDocument.type, options);
      }

      private Factory() {
      }
   }
}
