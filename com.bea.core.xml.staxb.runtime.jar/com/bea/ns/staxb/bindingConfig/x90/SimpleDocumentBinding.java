package com.bea.ns.staxb.bindingConfig.x90;

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

public interface SimpleDocumentBinding extends BindingType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SimpleDocumentBinding.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("simpledocumentbinding51b9type");

   String getTypeOfElement();

   XmlSignature xgetTypeOfElement();

   void setTypeOfElement(String var1);

   void xsetTypeOfElement(XmlSignature var1);

   public static final class Factory {
      public static SimpleDocumentBinding newInstance() {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().newInstance(SimpleDocumentBinding.type, (XmlOptions)null);
      }

      public static SimpleDocumentBinding newInstance(XmlOptions options) {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().newInstance(SimpleDocumentBinding.type, options);
      }

      public static SimpleDocumentBinding parse(String xmlAsString) throws XmlException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleDocumentBinding.type, (XmlOptions)null);
      }

      public static SimpleDocumentBinding parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(xmlAsString, SimpleDocumentBinding.type, options);
      }

      public static SimpleDocumentBinding parse(File file) throws XmlException, IOException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(file, SimpleDocumentBinding.type, (XmlOptions)null);
      }

      public static SimpleDocumentBinding parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(file, SimpleDocumentBinding.type, options);
      }

      public static SimpleDocumentBinding parse(URL u) throws XmlException, IOException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(u, SimpleDocumentBinding.type, (XmlOptions)null);
      }

      public static SimpleDocumentBinding parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(u, SimpleDocumentBinding.type, options);
      }

      public static SimpleDocumentBinding parse(InputStream is) throws XmlException, IOException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(is, SimpleDocumentBinding.type, (XmlOptions)null);
      }

      public static SimpleDocumentBinding parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(is, SimpleDocumentBinding.type, options);
      }

      public static SimpleDocumentBinding parse(Reader r) throws XmlException, IOException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(r, SimpleDocumentBinding.type, (XmlOptions)null);
      }

      public static SimpleDocumentBinding parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(r, SimpleDocumentBinding.type, options);
      }

      public static SimpleDocumentBinding parse(XMLStreamReader sr) throws XmlException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(sr, SimpleDocumentBinding.type, (XmlOptions)null);
      }

      public static SimpleDocumentBinding parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(sr, SimpleDocumentBinding.type, options);
      }

      public static SimpleDocumentBinding parse(Node node) throws XmlException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(node, SimpleDocumentBinding.type, (XmlOptions)null);
      }

      public static SimpleDocumentBinding parse(Node node, XmlOptions options) throws XmlException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(node, SimpleDocumentBinding.type, options);
      }

      /** @deprecated */
      public static SimpleDocumentBinding parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(xis, SimpleDocumentBinding.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SimpleDocumentBinding parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SimpleDocumentBinding)XmlBeans.getContextTypeLoader().parse(xis, SimpleDocumentBinding.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleDocumentBinding.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SimpleDocumentBinding.type, options);
      }

      private Factory() {
      }
   }
}
