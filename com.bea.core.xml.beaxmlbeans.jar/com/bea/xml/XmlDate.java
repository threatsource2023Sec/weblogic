package com.bea.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface XmlDate extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_date");

   /** @deprecated */
   Calendar calendarValue();

   /** @deprecated */
   void set(Calendar var1);

   /** @deprecated */
   GDate gDateValue();

   /** @deprecated */
   void set(GDateSpecification var1);

   /** @deprecated */
   Date dateValue();

   /** @deprecated */
   void set(Date var1);

   Calendar getCalendarValue();

   void setCalendarValue(Calendar var1);

   GDate getGDateValue();

   void setGDateValue(GDate var1);

   Date getDateValue();

   void setDateValue(Date var1);

   public static final class Factory {
      public static XmlDate newInstance() {
         return (XmlDate)XmlBeans.getContextTypeLoader().newInstance(XmlDate.type, (XmlOptions)null);
      }

      public static XmlDate newInstance(XmlOptions options) {
         return (XmlDate)XmlBeans.getContextTypeLoader().newInstance(XmlDate.type, options);
      }

      public static XmlDate newValue(Object obj) {
         return (XmlDate)XmlDate.type.newValue(obj);
      }

      public static XmlDate parse(String s) throws XmlException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse((String)s, XmlDate.type, (XmlOptions)null);
      }

      public static XmlDate parse(String s, XmlOptions options) throws XmlException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse(s, XmlDate.type, options);
      }

      public static XmlDate parse(File f) throws XmlException, IOException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse((File)f, XmlDate.type, (XmlOptions)null);
      }

      public static XmlDate parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse(f, XmlDate.type, options);
      }

      public static XmlDate parse(URL u) throws XmlException, IOException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse((URL)u, XmlDate.type, (XmlOptions)null);
      }

      public static XmlDate parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse(u, XmlDate.type, options);
      }

      public static XmlDate parse(InputStream is) throws XmlException, IOException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlDate.type, (XmlOptions)null);
      }

      public static XmlDate parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse(is, XmlDate.type, options);
      }

      public static XmlDate parse(Reader r) throws XmlException, IOException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlDate.type, (XmlOptions)null);
      }

      public static XmlDate parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse(r, XmlDate.type, options);
      }

      public static XmlDate parse(Node node) throws XmlException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse((Node)node, XmlDate.type, (XmlOptions)null);
      }

      public static XmlDate parse(Node node, XmlOptions options) throws XmlException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse(node, XmlDate.type, options);
      }

      /** @deprecated */
      public static XmlDate parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlDate.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlDate parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse(xis, XmlDate.type, options);
      }

      public static XmlDate parse(XMLStreamReader xsr) throws XmlException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlDate.type, (XmlOptions)null);
      }

      public static XmlDate parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlDate)XmlBeans.getContextTypeLoader().parse(xsr, XmlDate.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDate.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDate.type, options);
      }

      private Factory() {
      }
   }
}
