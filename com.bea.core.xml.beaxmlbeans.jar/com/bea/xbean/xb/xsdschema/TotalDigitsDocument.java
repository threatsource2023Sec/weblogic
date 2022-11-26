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

public interface TotalDigitsDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TotalDigitsDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("totaldigits4a8bdoctype");

   TotalDigits getTotalDigits();

   void setTotalDigits(TotalDigits var1);

   TotalDigits addNewTotalDigits();

   public static final class Factory {
      public static TotalDigitsDocument newInstance() {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().newInstance(TotalDigitsDocument.type, (XmlOptions)null);
      }

      public static TotalDigitsDocument newInstance(XmlOptions options) {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().newInstance(TotalDigitsDocument.type, options);
      }

      public static TotalDigitsDocument parse(String xmlAsString) throws XmlException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, TotalDigitsDocument.type, (XmlOptions)null);
      }

      public static TotalDigitsDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, TotalDigitsDocument.type, options);
      }

      public static TotalDigitsDocument parse(File file) throws XmlException, IOException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse((File)file, TotalDigitsDocument.type, (XmlOptions)null);
      }

      public static TotalDigitsDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse(file, TotalDigitsDocument.type, options);
      }

      public static TotalDigitsDocument parse(URL u) throws XmlException, IOException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse((URL)u, TotalDigitsDocument.type, (XmlOptions)null);
      }

      public static TotalDigitsDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse(u, TotalDigitsDocument.type, options);
      }

      public static TotalDigitsDocument parse(InputStream is) throws XmlException, IOException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, TotalDigitsDocument.type, (XmlOptions)null);
      }

      public static TotalDigitsDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse(is, TotalDigitsDocument.type, options);
      }

      public static TotalDigitsDocument parse(Reader r) throws XmlException, IOException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, TotalDigitsDocument.type, (XmlOptions)null);
      }

      public static TotalDigitsDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse(r, TotalDigitsDocument.type, options);
      }

      public static TotalDigitsDocument parse(XMLStreamReader sr) throws XmlException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, TotalDigitsDocument.type, (XmlOptions)null);
      }

      public static TotalDigitsDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse(sr, TotalDigitsDocument.type, options);
      }

      public static TotalDigitsDocument parse(Node node) throws XmlException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse((Node)node, TotalDigitsDocument.type, (XmlOptions)null);
      }

      public static TotalDigitsDocument parse(Node node, XmlOptions options) throws XmlException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse(node, TotalDigitsDocument.type, options);
      }

      /** @deprecated */
      public static TotalDigitsDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, TotalDigitsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TotalDigitsDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TotalDigitsDocument)XmlBeans.getContextTypeLoader().parse(xis, TotalDigitsDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TotalDigitsDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TotalDigitsDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface TotalDigits extends NumFacet {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TotalDigits.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("totaldigits2615elemtype");

      public static final class Factory {
         public static TotalDigits newInstance() {
            return (TotalDigits)XmlBeans.getContextTypeLoader().newInstance(TotalDigitsDocument.TotalDigits.type, (XmlOptions)null);
         }

         public static TotalDigits newInstance(XmlOptions options) {
            return (TotalDigits)XmlBeans.getContextTypeLoader().newInstance(TotalDigitsDocument.TotalDigits.type, options);
         }

         private Factory() {
         }
      }
   }
}
