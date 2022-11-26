package com.bea.xbean.xb.xsdschema;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
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

public interface Facet extends Annotated {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Facet.class.getClassLoader(), "schemacom_bea_xml.system.sXMLSCHEMA").resolveHandle("facet446etype");

   XmlAnySimpleType getValue();

   void setValue(XmlAnySimpleType var1);

   XmlAnySimpleType addNewValue();

   boolean getFixed();

   XmlBoolean xgetFixed();

   boolean isSetFixed();

   void setFixed(boolean var1);

   void xsetFixed(XmlBoolean var1);

   void unsetFixed();

   public static final class Factory {
      public static Facet newInstance() {
         return (Facet)XmlBeans.getContextTypeLoader().newInstance(Facet.type, (XmlOptions)null);
      }

      public static Facet newInstance(XmlOptions options) {
         return (Facet)XmlBeans.getContextTypeLoader().newInstance(Facet.type, options);
      }

      public static Facet parse(String xmlAsString) throws XmlException {
         return (Facet)XmlBeans.getContextTypeLoader().parse((String)xmlAsString, Facet.type, (XmlOptions)null);
      }

      public static Facet parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (Facet)XmlBeans.getContextTypeLoader().parse(xmlAsString, Facet.type, options);
      }

      public static Facet parse(File file) throws XmlException, IOException {
         return (Facet)XmlBeans.getContextTypeLoader().parse((File)file, Facet.type, (XmlOptions)null);
      }

      public static Facet parse(File file, XmlOptions options) throws XmlException, IOException {
         return (Facet)XmlBeans.getContextTypeLoader().parse(file, Facet.type, options);
      }

      public static Facet parse(URL u) throws XmlException, IOException {
         return (Facet)XmlBeans.getContextTypeLoader().parse((URL)u, Facet.type, (XmlOptions)null);
      }

      public static Facet parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (Facet)XmlBeans.getContextTypeLoader().parse(u, Facet.type, options);
      }

      public static Facet parse(InputStream is) throws XmlException, IOException {
         return (Facet)XmlBeans.getContextTypeLoader().parse((InputStream)is, Facet.type, (XmlOptions)null);
      }

      public static Facet parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (Facet)XmlBeans.getContextTypeLoader().parse(is, Facet.type, options);
      }

      public static Facet parse(Reader r) throws XmlException, IOException {
         return (Facet)XmlBeans.getContextTypeLoader().parse((Reader)r, Facet.type, (XmlOptions)null);
      }

      public static Facet parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (Facet)XmlBeans.getContextTypeLoader().parse(r, Facet.type, options);
      }

      public static Facet parse(XMLStreamReader sr) throws XmlException {
         return (Facet)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)sr, Facet.type, (XmlOptions)null);
      }

      public static Facet parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (Facet)XmlBeans.getContextTypeLoader().parse(sr, Facet.type, options);
      }

      public static Facet parse(Node node) throws XmlException {
         return (Facet)XmlBeans.getContextTypeLoader().parse((Node)node, Facet.type, (XmlOptions)null);
      }

      public static Facet parse(Node node, XmlOptions options) throws XmlException {
         return (Facet)XmlBeans.getContextTypeLoader().parse(node, Facet.type, options);
      }

      /** @deprecated */
      public static Facet parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (Facet)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, Facet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static Facet parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (Facet)XmlBeans.getContextTypeLoader().parse(xis, Facet.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Facet.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, Facet.type, options);
      }

      private Factory() {
      }
   }
}
