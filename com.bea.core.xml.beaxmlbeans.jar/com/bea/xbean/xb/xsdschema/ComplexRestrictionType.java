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

public interface ComplexRestrictionType extends RestrictionType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ComplexRestrictionType.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("complexrestrictiontype1b7dtype");

   public static final class Factory {
      public static ComplexRestrictionType newInstance() {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().newInstance(ComplexRestrictionType.type, (XmlOptions)null);
      }

      public static ComplexRestrictionType newInstance(XmlOptions options) {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().newInstance(ComplexRestrictionType.type, options);
      }

      public static ComplexRestrictionType parse(String xmlAsString) throws XmlException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ComplexRestrictionType.type, (XmlOptions)null);
      }

      public static ComplexRestrictionType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ComplexRestrictionType.type, options);
      }

      public static ComplexRestrictionType parse(File file) throws XmlException, IOException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse((File)file, ComplexRestrictionType.type, (XmlOptions)null);
      }

      public static ComplexRestrictionType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse(file, ComplexRestrictionType.type, options);
      }

      public static ComplexRestrictionType parse(URL u) throws XmlException, IOException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse((URL)u, ComplexRestrictionType.type, (XmlOptions)null);
      }

      public static ComplexRestrictionType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse(u, ComplexRestrictionType.type, options);
      }

      public static ComplexRestrictionType parse(InputStream is) throws XmlException, IOException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse((InputStream)is, ComplexRestrictionType.type, (XmlOptions)null);
      }

      public static ComplexRestrictionType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse(is, ComplexRestrictionType.type, options);
      }

      public static ComplexRestrictionType parse(Reader r) throws XmlException, IOException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse((Reader)r, ComplexRestrictionType.type, (XmlOptions)null);
      }

      public static ComplexRestrictionType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse(r, ComplexRestrictionType.type, options);
      }

      public static ComplexRestrictionType parse(XMLStreamReader sr) throws XmlException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ComplexRestrictionType.type, (XmlOptions)null);
      }

      public static ComplexRestrictionType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse(sr, ComplexRestrictionType.type, options);
      }

      public static ComplexRestrictionType parse(Node node) throws XmlException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse((Node)node, ComplexRestrictionType.type, (XmlOptions)null);
      }

      public static ComplexRestrictionType parse(Node node, XmlOptions options) throws XmlException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse(node, ComplexRestrictionType.type, options);
      }

      /** @deprecated */
      public static ComplexRestrictionType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ComplexRestrictionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ComplexRestrictionType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ComplexRestrictionType)XmlBeans.getContextTypeLoader().parse(xis, ComplexRestrictionType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ComplexRestrictionType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ComplexRestrictionType.type, options);
      }

      private Factory() {
      }
   }
}
