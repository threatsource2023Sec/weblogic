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

public interface ReducedDerivationControl extends DerivationControl {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ReducedDerivationControl.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("reducedderivationcontrole1cbtype");
   DerivationControl.Enum EXTENSION = DerivationControl.EXTENSION;
   DerivationControl.Enum RESTRICTION = DerivationControl.RESTRICTION;
   int INT_EXTENSION = 2;
   int INT_RESTRICTION = 3;

   public static final class Factory {
      public static ReducedDerivationControl newValue(Object obj) {
         return (ReducedDerivationControl)ReducedDerivationControl.type.newValue(obj);
      }

      public static ReducedDerivationControl newInstance() {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().newInstance(ReducedDerivationControl.type, (XmlOptions)null);
      }

      public static ReducedDerivationControl newInstance(XmlOptions options) {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().newInstance(ReducedDerivationControl.type, options);
      }

      public static ReducedDerivationControl parse(String xmlAsString) throws XmlException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, ReducedDerivationControl.type, (XmlOptions)null);
      }

      public static ReducedDerivationControl parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse(xmlAsString, ReducedDerivationControl.type, options);
      }

      public static ReducedDerivationControl parse(File file) throws XmlException, IOException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse((File)file, ReducedDerivationControl.type, (XmlOptions)null);
      }

      public static ReducedDerivationControl parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse(file, ReducedDerivationControl.type, options);
      }

      public static ReducedDerivationControl parse(URL u) throws XmlException, IOException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse((URL)u, ReducedDerivationControl.type, (XmlOptions)null);
      }

      public static ReducedDerivationControl parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse(u, ReducedDerivationControl.type, options);
      }

      public static ReducedDerivationControl parse(InputStream is) throws XmlException, IOException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse((InputStream)is, ReducedDerivationControl.type, (XmlOptions)null);
      }

      public static ReducedDerivationControl parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse(is, ReducedDerivationControl.type, options);
      }

      public static ReducedDerivationControl parse(Reader r) throws XmlException, IOException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse((Reader)r, ReducedDerivationControl.type, (XmlOptions)null);
      }

      public static ReducedDerivationControl parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse(r, ReducedDerivationControl.type, options);
      }

      public static ReducedDerivationControl parse(XMLStreamReader sr) throws XmlException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, ReducedDerivationControl.type, (XmlOptions)null);
      }

      public static ReducedDerivationControl parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse(sr, ReducedDerivationControl.type, options);
      }

      public static ReducedDerivationControl parse(Node node) throws XmlException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse((Node)node, ReducedDerivationControl.type, (XmlOptions)null);
      }

      public static ReducedDerivationControl parse(Node node, XmlOptions options) throws XmlException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse(node, ReducedDerivationControl.type, options);
      }

      /** @deprecated */
      public static ReducedDerivationControl parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, ReducedDerivationControl.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ReducedDerivationControl parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ReducedDerivationControl)XmlBeans.getContextTypeLoader().parse(xis, ReducedDerivationControl.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReducedDerivationControl.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReducedDerivationControl.type, options);
      }

      private Factory() {
      }
   }
}
