package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlNonNegativeInteger;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface AnyDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AnyDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("anye729doctype");

   Any getAny();

   void setAny(Any var1);

   Any addNewAny();

   public static final class Factory {
      public static AnyDocument newInstance() {
         return (AnyDocument)XmlBeans.getContextTypeLoader().newInstance(AnyDocument.type, (XmlOptions)null);
      }

      public static AnyDocument newInstance(XmlOptions options) {
         return (AnyDocument)XmlBeans.getContextTypeLoader().newInstance(AnyDocument.type, options);
      }

      public static AnyDocument parse(String xmlAsString) throws XmlException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, AnyDocument.type, (XmlOptions)null);
      }

      public static AnyDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, AnyDocument.type, options);
      }

      public static AnyDocument parse(File file) throws XmlException, IOException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse((File)file, AnyDocument.type, (XmlOptions)null);
      }

      public static AnyDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse(file, AnyDocument.type, options);
      }

      public static AnyDocument parse(URL u) throws XmlException, IOException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse((URL)u, AnyDocument.type, (XmlOptions)null);
      }

      public static AnyDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse(u, AnyDocument.type, options);
      }

      public static AnyDocument parse(InputStream is) throws XmlException, IOException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, AnyDocument.type, (XmlOptions)null);
      }

      public static AnyDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse(is, AnyDocument.type, options);
      }

      public static AnyDocument parse(Reader r) throws XmlException, IOException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, AnyDocument.type, (XmlOptions)null);
      }

      public static AnyDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse(r, AnyDocument.type, options);
      }

      public static AnyDocument parse(XMLStreamReader sr) throws XmlException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, AnyDocument.type, (XmlOptions)null);
      }

      public static AnyDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse(sr, AnyDocument.type, options);
      }

      public static AnyDocument parse(Node node) throws XmlException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse((Node)node, AnyDocument.type, (XmlOptions)null);
      }

      public static AnyDocument parse(Node node, XmlOptions options) throws XmlException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse(node, AnyDocument.type, options);
      }

      /** @deprecated */
      public static AnyDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, AnyDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static AnyDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (AnyDocument)XmlBeans.getContextTypeLoader().parse(xis, AnyDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnyDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, AnyDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface Any extends Wildcard {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Any.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("anye9d1elemtype");

      BigInteger getMinOccurs();

      XmlNonNegativeInteger xgetMinOccurs();

      boolean isSetMinOccurs();

      void setMinOccurs(BigInteger var1);

      void xsetMinOccurs(XmlNonNegativeInteger var1);

      void unsetMinOccurs();

      Object getMaxOccurs();

      AllNNI xgetMaxOccurs();

      boolean isSetMaxOccurs();

      void setMaxOccurs(Object var1);

      void xsetMaxOccurs(AllNNI var1);

      void unsetMaxOccurs();

      public static final class Factory {
         public static Any newInstance() {
            return (Any)XmlBeans.getContextTypeLoader().newInstance(AnyDocument.Any.type, (XmlOptions)null);
         }

         public static Any newInstance(XmlOptions options) {
            return (Any)XmlBeans.getContextTypeLoader().newInstance(AnyDocument.Any.type, options);
         }

         private Factory() {
         }
      }
   }
}
