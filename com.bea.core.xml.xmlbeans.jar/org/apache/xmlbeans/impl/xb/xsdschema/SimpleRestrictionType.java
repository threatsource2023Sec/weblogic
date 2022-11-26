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
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface SimpleRestrictionType extends RestrictionType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleRestrictionType.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sXMLSCHEMA").resolveHandle("simplerestrictiontypeeab1type");

   public static final class Factory {
      public static SimpleRestrictionType newInstance() {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().newInstance(SimpleRestrictionType.type, (XmlOptions)null);
      }

      public static SimpleRestrictionType newInstance(XmlOptions options) {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().newInstance(SimpleRestrictionType.type, options);
      }

      public static SimpleRestrictionType parse(String xmlAsString) throws XmlException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, SimpleRestrictionType.type, (XmlOptions)null);
      }

      public static SimpleRestrictionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleRestrictionType.type, options);
      }

      public static SimpleRestrictionType parse(File file) throws XmlException, IOException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse((File)file, SimpleRestrictionType.type, (XmlOptions)null);
      }

      public static SimpleRestrictionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse(file, SimpleRestrictionType.type, options);
      }

      public static SimpleRestrictionType parse(URL u) throws XmlException, IOException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse((URL)u, SimpleRestrictionType.type, (XmlOptions)null);
      }

      public static SimpleRestrictionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse(u, SimpleRestrictionType.type, options);
      }

      public static SimpleRestrictionType parse(InputStream is) throws XmlException, IOException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse((InputStream)is, SimpleRestrictionType.type, (XmlOptions)null);
      }

      public static SimpleRestrictionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse(is, SimpleRestrictionType.type, options);
      }

      public static SimpleRestrictionType parse(Reader r) throws XmlException, IOException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse((Reader)r, SimpleRestrictionType.type, (XmlOptions)null);
      }

      public static SimpleRestrictionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse(r, SimpleRestrictionType.type, options);
      }

      public static SimpleRestrictionType parse(XMLStreamReader sr) throws XmlException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, SimpleRestrictionType.type, (XmlOptions)null);
      }

      public static SimpleRestrictionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse(sr, SimpleRestrictionType.type, options);
      }

      public static SimpleRestrictionType parse(Node node) throws XmlException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse((Node)node, SimpleRestrictionType.type, (XmlOptions)null);
      }

      public static SimpleRestrictionType parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse(node, SimpleRestrictionType.type, options);
      }

      /** @deprecated */
      public static SimpleRestrictionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, SimpleRestrictionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleRestrictionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleRestrictionType)XmlBeans.getContextTypeLoader().parse(xis, SimpleRestrictionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleRestrictionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleRestrictionType.type, options);
      }

      private Factory() {
      }
   }
}
