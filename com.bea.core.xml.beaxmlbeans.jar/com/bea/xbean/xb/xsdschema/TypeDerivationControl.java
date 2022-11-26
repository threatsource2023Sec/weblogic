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

public interface TypeDerivationControl extends DerivationControl {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TypeDerivationControl.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("typederivationcontrol3239type");
   DerivationControl.Enum EXTENSION = DerivationControl.EXTENSION;
   DerivationControl.Enum RESTRICTION = DerivationControl.RESTRICTION;
   DerivationControl.Enum LIST = DerivationControl.LIST;
   DerivationControl.Enum UNION = DerivationControl.UNION;
   int INT_EXTENSION = 2;
   int INT_RESTRICTION = 3;
   int INT_LIST = 4;
   int INT_UNION = 5;

   public static final class Factory {
      public static TypeDerivationControl newValue(Object obj) {
         return (TypeDerivationControl)TypeDerivationControl.type.newValue(obj);
      }

      public static TypeDerivationControl newInstance() {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().newInstance(TypeDerivationControl.type, (XmlOptions)null);
      }

      public static TypeDerivationControl newInstance(XmlOptions options) {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().newInstance(TypeDerivationControl.type, options);
      }

      public static TypeDerivationControl parse(String xmlAsString) throws XmlException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, TypeDerivationControl.type, (XmlOptions)null);
      }

      public static TypeDerivationControl parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse(xmlAsString, TypeDerivationControl.type, options);
      }

      public static TypeDerivationControl parse(File file) throws XmlException, IOException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse((File)file, TypeDerivationControl.type, (XmlOptions)null);
      }

      public static TypeDerivationControl parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse(file, TypeDerivationControl.type, options);
      }

      public static TypeDerivationControl parse(URL u) throws XmlException, IOException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse((URL)u, TypeDerivationControl.type, (XmlOptions)null);
      }

      public static TypeDerivationControl parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse(u, TypeDerivationControl.type, options);
      }

      public static TypeDerivationControl parse(InputStream is) throws XmlException, IOException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse((InputStream)is, TypeDerivationControl.type, (XmlOptions)null);
      }

      public static TypeDerivationControl parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse(is, TypeDerivationControl.type, options);
      }

      public static TypeDerivationControl parse(Reader r) throws XmlException, IOException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse((Reader)r, TypeDerivationControl.type, (XmlOptions)null);
      }

      public static TypeDerivationControl parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse(r, TypeDerivationControl.type, options);
      }

      public static TypeDerivationControl parse(XMLStreamReader sr) throws XmlException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, TypeDerivationControl.type, (XmlOptions)null);
      }

      public static TypeDerivationControl parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse(sr, TypeDerivationControl.type, options);
      }

      public static TypeDerivationControl parse(Node node) throws XmlException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse((Node)node, TypeDerivationControl.type, (XmlOptions)null);
      }

      public static TypeDerivationControl parse(Node node, XmlOptions options) throws XmlException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse(node, TypeDerivationControl.type, options);
      }

      /** @deprecated */
      public static TypeDerivationControl parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, TypeDerivationControl.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TypeDerivationControl parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TypeDerivationControl)XmlBeans.getContextTypeLoader().parse(xis, TypeDerivationControl.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TypeDerivationControl.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TypeDerivationControl.type, options);
      }

      private Factory() {
      }
   }
}
