package com.sun.java.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
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

public interface TimeUnitTypeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TimeUnitTypeType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("timeunittypetypefc90type");
   Enum DAYS = TimeUnitTypeType.Enum.forString("Days");
   Enum HOURS = TimeUnitTypeType.Enum.forString("Hours");
   Enum MINUTES = TimeUnitTypeType.Enum.forString("Minutes");
   Enum SECONDS = TimeUnitTypeType.Enum.forString("Seconds");
   Enum MILLISECONDS = TimeUnitTypeType.Enum.forString("Milliseconds");
   Enum MICROSECONDS = TimeUnitTypeType.Enum.forString("Microseconds");
   Enum NANOSECONDS = TimeUnitTypeType.Enum.forString("Nanoseconds");
   int INT_DAYS = 1;
   int INT_HOURS = 2;
   int INT_MINUTES = 3;
   int INT_SECONDS = 4;
   int INT_MILLISECONDS = 5;
   int INT_MICROSECONDS = 6;
   int INT_NANOSECONDS = 7;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static TimeUnitTypeType newInstance() {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().newInstance(TimeUnitTypeType.type, (XmlOptions)null);
      }

      public static TimeUnitTypeType newInstance(XmlOptions options) {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().newInstance(TimeUnitTypeType.type, options);
      }

      public static TimeUnitTypeType parse(java.lang.String xmlAsString) throws XmlException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimeUnitTypeType.type, (XmlOptions)null);
      }

      public static TimeUnitTypeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TimeUnitTypeType.type, options);
      }

      public static TimeUnitTypeType parse(File file) throws XmlException, IOException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(file, TimeUnitTypeType.type, (XmlOptions)null);
      }

      public static TimeUnitTypeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(file, TimeUnitTypeType.type, options);
      }

      public static TimeUnitTypeType parse(URL u) throws XmlException, IOException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(u, TimeUnitTypeType.type, (XmlOptions)null);
      }

      public static TimeUnitTypeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(u, TimeUnitTypeType.type, options);
      }

      public static TimeUnitTypeType parse(InputStream is) throws XmlException, IOException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(is, TimeUnitTypeType.type, (XmlOptions)null);
      }

      public static TimeUnitTypeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(is, TimeUnitTypeType.type, options);
      }

      public static TimeUnitTypeType parse(Reader r) throws XmlException, IOException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(r, TimeUnitTypeType.type, (XmlOptions)null);
      }

      public static TimeUnitTypeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(r, TimeUnitTypeType.type, options);
      }

      public static TimeUnitTypeType parse(XMLStreamReader sr) throws XmlException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(sr, TimeUnitTypeType.type, (XmlOptions)null);
      }

      public static TimeUnitTypeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(sr, TimeUnitTypeType.type, options);
      }

      public static TimeUnitTypeType parse(Node node) throws XmlException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(node, TimeUnitTypeType.type, (XmlOptions)null);
      }

      public static TimeUnitTypeType parse(Node node, XmlOptions options) throws XmlException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(node, TimeUnitTypeType.type, options);
      }

      /** @deprecated */
      public static TimeUnitTypeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(xis, TimeUnitTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TimeUnitTypeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TimeUnitTypeType)XmlBeans.getContextTypeLoader().parse(xis, TimeUnitTypeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimeUnitTypeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TimeUnitTypeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_DAYS = 1;
      static final int INT_HOURS = 2;
      static final int INT_MINUTES = 3;
      static final int INT_SECONDS = 4;
      static final int INT_MILLISECONDS = 5;
      static final int INT_MICROSECONDS = 6;
      static final int INT_NANOSECONDS = 7;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Days", 1), new Enum("Hours", 2), new Enum("Minutes", 3), new Enum("Seconds", 4), new Enum("Milliseconds", 5), new Enum("Microseconds", 6), new Enum("Nanoseconds", 7)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(java.lang.String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(java.lang.String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
