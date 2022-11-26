package com.bea.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Calendar;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XmlGYear extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_gYear");

   Calendar getCalendarValue();

   void setCalendarValue(Calendar var1);

   GDate getGDateValue();

   void setGDateValue(GDate var1);

   int getIntValue();

   void setIntValue(int var1);

   /** @deprecated */
   Calendar calendarValue();

   /** @deprecated */
   void set(Calendar var1);

   /** @deprecated */
   GDate gDateValue();

   /** @deprecated */
   void set(GDateSpecification var1);

   /** @deprecated */
   int intValue();

   /** @deprecated */
   void set(int var1);

   public static final class Factory {
      public static XmlGYear newInstance() {
         return (XmlGYear)XmlBeans.getContextTypeLoader().newInstance(XmlGYear.type, (XmlOptions)null);
      }

      public static XmlGYear newInstance(XmlOptions options) {
         return (XmlGYear)XmlBeans.getContextTypeLoader().newInstance(XmlGYear.type, options);
      }

      public static XmlGYear newValue(Object obj) {
         return (XmlGYear)XmlGYear.type.newValue(obj);
      }

      public static XmlGYear parse(String s) throws XmlException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse((String)s, XmlGYear.type, (XmlOptions)null);
      }

      public static XmlGYear parse(String s, XmlOptions options) throws XmlException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse(s, XmlGYear.type, options);
      }

      public static XmlGYear parse(File f) throws XmlException, IOException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse((File)f, XmlGYear.type, (XmlOptions)null);
      }

      public static XmlGYear parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse(f, XmlGYear.type, options);
      }

      public static XmlGYear parse(URL u) throws XmlException, IOException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse((URL)u, XmlGYear.type, (XmlOptions)null);
      }

      public static XmlGYear parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse(u, XmlGYear.type, options);
      }

      public static XmlGYear parse(InputStream is) throws XmlException, IOException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlGYear.type, (XmlOptions)null);
      }

      public static XmlGYear parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse(is, XmlGYear.type, options);
      }

      public static XmlGYear parse(Reader r) throws XmlException, IOException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlGYear.type, (XmlOptions)null);
      }

      public static XmlGYear parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse(r, XmlGYear.type, options);
      }

      public static XmlGYear parse(Node node) throws XmlException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse((Node)node, XmlGYear.type, (XmlOptions)null);
      }

      public static XmlGYear parse(Node node, XmlOptions options) throws XmlException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse(node, XmlGYear.type, options);
      }

      /** @deprecated */
      public static XmlGYear parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlGYear.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlGYear parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse(xis, XmlGYear.type, options);
      }

      public static XmlGYear parse(XMLStreamReader xsr) throws XmlException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlGYear.type, (XmlOptions)null);
      }

      public static XmlGYear parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlGYear)XmlBeans.getContextTypeLoader().parse(xsr, XmlGYear.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlGYear.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlGYear.type, options);
      }

      private Factory() {
      }
   }
}
