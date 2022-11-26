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

public interface XmlTime extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_time");

   Calendar getCalendarValue();

   void setCalendarValue(Calendar var1);

   GDate getGDateValue();

   void setGDateValue(GDate var1);

   /** @deprecated */
   Calendar calendarValue();

   /** @deprecated */
   void set(Calendar var1);

   /** @deprecated */
   GDate gDateValue();

   /** @deprecated */
   void set(GDateSpecification var1);

   public static final class Factory {
      public static XmlTime newInstance() {
         return (XmlTime)XmlBeans.getContextTypeLoader().newInstance(XmlTime.type, (XmlOptions)null);
      }

      public static XmlTime newInstance(XmlOptions options) {
         return (XmlTime)XmlBeans.getContextTypeLoader().newInstance(XmlTime.type, options);
      }

      public static XmlTime newValue(Object obj) {
         return (XmlTime)XmlTime.type.newValue(obj);
      }

      public static XmlTime parse(String s) throws XmlException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse((String)s, XmlTime.type, (XmlOptions)null);
      }

      public static XmlTime parse(String s, XmlOptions options) throws XmlException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse(s, XmlTime.type, options);
      }

      public static XmlTime parse(File f) throws XmlException, IOException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse((File)f, XmlTime.type, (XmlOptions)null);
      }

      public static XmlTime parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse(f, XmlTime.type, options);
      }

      public static XmlTime parse(URL u) throws XmlException, IOException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse((URL)u, XmlTime.type, (XmlOptions)null);
      }

      public static XmlTime parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse(u, XmlTime.type, options);
      }

      public static XmlTime parse(InputStream is) throws XmlException, IOException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlTime.type, (XmlOptions)null);
      }

      public static XmlTime parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse(is, XmlTime.type, options);
      }

      public static XmlTime parse(Reader r) throws XmlException, IOException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlTime.type, (XmlOptions)null);
      }

      public static XmlTime parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse(r, XmlTime.type, options);
      }

      public static XmlTime parse(Node node) throws XmlException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse((Node)node, XmlTime.type, (XmlOptions)null);
      }

      public static XmlTime parse(Node node, XmlOptions options) throws XmlException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse(node, XmlTime.type, options);
      }

      /** @deprecated */
      public static XmlTime parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlTime.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlTime parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse(xis, XmlTime.type, options);
      }

      public static XmlTime parse(XMLStreamReader xsr) throws XmlException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlTime.type, (XmlOptions)null);
      }

      public static XmlTime parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlTime)XmlBeans.getContextTypeLoader().parse(xsr, XmlTime.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlTime.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlTime.type, options);
      }

      private Factory() {
      }
   }
}
