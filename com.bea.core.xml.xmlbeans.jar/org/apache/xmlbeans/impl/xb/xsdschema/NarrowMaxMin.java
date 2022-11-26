package org.apache.xmlbeans.impl.xb.xsdschema;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlNonNegativeInteger;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface NarrowMaxMin extends LocalElement {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NarrowMaxMin.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("narrowmaxmin926atype");

   public static final class Factory {
      public static NarrowMaxMin newInstance() {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().newInstance(NarrowMaxMin.type, (XmlOptions)null);
      }

      public static NarrowMaxMin newInstance(XmlOptions options) {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().newInstance(NarrowMaxMin.type, options);
      }

      public static NarrowMaxMin parse(String xmlAsString) throws XmlException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, NarrowMaxMin.type, (XmlOptions)null);
      }

      public static NarrowMaxMin parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse(xmlAsString, NarrowMaxMin.type, options);
      }

      public static NarrowMaxMin parse(File file) throws XmlException, IOException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse((File)file, NarrowMaxMin.type, (XmlOptions)null);
      }

      public static NarrowMaxMin parse(File file, XmlOptions options) throws XmlException, IOException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse(file, NarrowMaxMin.type, options);
      }

      public static NarrowMaxMin parse(URL u) throws XmlException, IOException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse((URL)u, NarrowMaxMin.type, (XmlOptions)null);
      }

      public static NarrowMaxMin parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse(u, NarrowMaxMin.type, options);
      }

      public static NarrowMaxMin parse(InputStream is) throws XmlException, IOException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse((InputStream)is, NarrowMaxMin.type, (XmlOptions)null);
      }

      public static NarrowMaxMin parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse(is, NarrowMaxMin.type, options);
      }

      public static NarrowMaxMin parse(Reader r) throws XmlException, IOException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse((Reader)r, NarrowMaxMin.type, (XmlOptions)null);
      }

      public static NarrowMaxMin parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse(r, NarrowMaxMin.type, options);
      }

      public static NarrowMaxMin parse(XMLStreamReader sr) throws XmlException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, NarrowMaxMin.type, (XmlOptions)null);
      }

      public static NarrowMaxMin parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse(sr, NarrowMaxMin.type, options);
      }

      public static NarrowMaxMin parse(Node node) throws XmlException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse((Node)node, NarrowMaxMin.type, (XmlOptions)null);
      }

      public static NarrowMaxMin parse(Node node, XmlOptions options) throws XmlException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse(node, NarrowMaxMin.type, options);
      }

      /** @deprecated */
      public static NarrowMaxMin parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, NarrowMaxMin.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static NarrowMaxMin parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (NarrowMaxMin)XmlBeans.getContextTypeLoader().parse(xis, NarrowMaxMin.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NarrowMaxMin.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, NarrowMaxMin.type, options);
      }

      private Factory() {
      }
   }

   public interface MaxOccurs extends AllNNI {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MaxOccurs.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("maxoccursd85dattrtype");

      Object getObjectValue();

      void setObjectValue(Object var1);

      /** @deprecated */
      Object objectValue();

      /** @deprecated */
      void objectSet(Object var1);

      SchemaType instanceType();

      public static final class Factory {
         public static MaxOccurs newValue(Object obj) {
            return (MaxOccurs)NarrowMaxMin.MaxOccurs.type.newValue(obj);
         }

         public static MaxOccurs newInstance() {
            return (MaxOccurs)XmlBeans.getContextTypeLoader().newInstance(NarrowMaxMin.MaxOccurs.type, (XmlOptions)null);
         }

         public static MaxOccurs newInstance(XmlOptions options) {
            return (MaxOccurs)XmlBeans.getContextTypeLoader().newInstance(NarrowMaxMin.MaxOccurs.type, options);
         }

         private Factory() {
         }
      }
   }

   public interface MinOccurs extends XmlNonNegativeInteger {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MinOccurs.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("minoccurs1acbattrtype");

      public static final class Factory {
         public static MinOccurs newValue(Object obj) {
            return (MinOccurs)NarrowMaxMin.MinOccurs.type.newValue(obj);
         }

         public static MinOccurs newInstance() {
            return (MinOccurs)XmlBeans.getContextTypeLoader().newInstance(NarrowMaxMin.MinOccurs.type, (XmlOptions)null);
         }

         public static MinOccurs newInstance(XmlOptions options) {
            return (MinOccurs)XmlBeans.getContextTypeLoader().newInstance(NarrowMaxMin.MinOccurs.type, options);
         }

         private Factory() {
         }
      }
   }
}
