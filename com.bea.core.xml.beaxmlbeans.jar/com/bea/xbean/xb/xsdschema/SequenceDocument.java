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

public interface SequenceDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SequenceDocument.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("sequencecba2doctype");

   ExplicitGroup getSequence();

   void setSequence(ExplicitGroup var1);

   ExplicitGroup addNewSequence();

   public static final class Factory {
      public static SequenceDocument newInstance() {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().newInstance(SequenceDocument.type, (XmlOptions)null);
      }

      public static SequenceDocument newInstance(XmlOptions options) {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().newInstance(SequenceDocument.type, options);
      }

      public static SequenceDocument parse(String xmlAsString) throws XmlException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, SequenceDocument.type, (XmlOptions)null);
      }

      public static SequenceDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, SequenceDocument.type, options);
      }

      public static SequenceDocument parse(File file) throws XmlException, IOException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse((File)file, SequenceDocument.type, (XmlOptions)null);
      }

      public static SequenceDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse(file, SequenceDocument.type, options);
      }

      public static SequenceDocument parse(URL u) throws XmlException, IOException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse((URL)u, SequenceDocument.type, (XmlOptions)null);
      }

      public static SequenceDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse(u, SequenceDocument.type, options);
      }

      public static SequenceDocument parse(InputStream is) throws XmlException, IOException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse((InputStream)is, SequenceDocument.type, (XmlOptions)null);
      }

      public static SequenceDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse(is, SequenceDocument.type, options);
      }

      public static SequenceDocument parse(Reader r) throws XmlException, IOException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse((Reader)r, SequenceDocument.type, (XmlOptions)null);
      }

      public static SequenceDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse(r, SequenceDocument.type, options);
      }

      public static SequenceDocument parse(XMLStreamReader sr) throws XmlException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, SequenceDocument.type, (XmlOptions)null);
      }

      public static SequenceDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse(sr, SequenceDocument.type, options);
      }

      public static SequenceDocument parse(Node node) throws XmlException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse((Node)node, SequenceDocument.type, (XmlOptions)null);
      }

      public static SequenceDocument parse(Node node, XmlOptions options) throws XmlException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse(node, SequenceDocument.type, options);
      }

      /** @deprecated */
      public static SequenceDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, SequenceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SequenceDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SequenceDocument)XmlBeans.getContextTypeLoader().parse(xis, SequenceDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SequenceDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SequenceDocument.type, options);
      }

      private Factory() {
      }
   }
}
