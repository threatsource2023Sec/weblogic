package org.apache.xmlbeans;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.apache.xmlbeans.xml.stream.XMLStreamException;
import org.w3c.dom.Node;

public interface XmlDateTime extends XmlAnySimpleType {
   SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_dateTime");

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
      public static XmlDateTime newInstance() {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().newInstance(XmlDateTime.type, (XmlOptions)null);
      }

      public static XmlDateTime newInstance(XmlOptions options) {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().newInstance(XmlDateTime.type, options);
      }

      public static XmlDateTime newValue(Object obj) {
         return (XmlDateTime)XmlDateTime.type.newValue(obj);
      }

      public static XmlDateTime parse(String s) throws XmlException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse((String)s, XmlDateTime.type, (XmlOptions)null);
      }

      public static XmlDateTime parse(String s, XmlOptions options) throws XmlException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse(s, XmlDateTime.type, options);
      }

      public static XmlDateTime parse(File f) throws XmlException, IOException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse((File)f, XmlDateTime.type, (XmlOptions)null);
      }

      public static XmlDateTime parse(File f, XmlOptions options) throws XmlException, IOException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse(f, XmlDateTime.type, options);
      }

      public static XmlDateTime parse(URL u) throws XmlException, IOException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse((URL)u, XmlDateTime.type, (XmlOptions)null);
      }

      public static XmlDateTime parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse(u, XmlDateTime.type, options);
      }

      public static XmlDateTime parse(InputStream is) throws XmlException, IOException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse((InputStream)is, XmlDateTime.type, (XmlOptions)null);
      }

      public static XmlDateTime parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse(is, XmlDateTime.type, options);
      }

      public static XmlDateTime parse(Reader r) throws XmlException, IOException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse((Reader)r, XmlDateTime.type, (XmlOptions)null);
      }

      public static XmlDateTime parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse(r, XmlDateTime.type, options);
      }

      public static XmlDateTime parse(Node node) throws XmlException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse((Node)node, XmlDateTime.type, (XmlOptions)null);
      }

      public static XmlDateTime parse(Node node, XmlOptions options) throws XmlException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse(node, XmlDateTime.type, options);
      }

      /** @deprecated */
      public static XmlDateTime parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse((XMLInputStream)xis, XmlDateTime.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XmlDateTime parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse(xis, XmlDateTime.type, options);
      }

      public static XmlDateTime parse(XMLStreamReader xsr) throws XmlException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse((XMLStreamReader)xsr, XmlDateTime.type, (XmlOptions)null);
      }

      public static XmlDateTime parse(XMLStreamReader xsr, XmlOptions options) throws XmlException {
         return (XmlDateTime)XmlBeans.getContextTypeLoader().parse(xsr, XmlDateTime.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDateTime.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, XmlDateTime.type, options);
      }

      private Factory() {
      }
   }
}
