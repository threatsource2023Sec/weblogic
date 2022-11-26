package com.oracle.xmlns.weblogic.webservicePolicyRef;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface StatusType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(StatusType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("statustypeba64type");
   Enum ENABLED = StatusType.Enum.forString("enabled");
   Enum DISABLED = StatusType.Enum.forString("disabled");
   Enum DELETED = StatusType.Enum.forString("deleted");
   int INT_ENABLED = 1;
   int INT_DISABLED = 2;
   int INT_DELETED = 3;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static StatusType newValue(Object obj) {
         return (StatusType)StatusType.type.newValue(obj);
      }

      public static StatusType newInstance() {
         return (StatusType)XmlBeans.getContextTypeLoader().newInstance(StatusType.type, (XmlOptions)null);
      }

      public static StatusType newInstance(XmlOptions options) {
         return (StatusType)XmlBeans.getContextTypeLoader().newInstance(StatusType.type, options);
      }

      public static StatusType parse(String xmlAsString) throws XmlException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatusType.type, (XmlOptions)null);
      }

      public static StatusType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(xmlAsString, StatusType.type, options);
      }

      public static StatusType parse(File file) throws XmlException, IOException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(file, StatusType.type, (XmlOptions)null);
      }

      public static StatusType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(file, StatusType.type, options);
      }

      public static StatusType parse(URL u) throws XmlException, IOException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(u, StatusType.type, (XmlOptions)null);
      }

      public static StatusType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(u, StatusType.type, options);
      }

      public static StatusType parse(InputStream is) throws XmlException, IOException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(is, StatusType.type, (XmlOptions)null);
      }

      public static StatusType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(is, StatusType.type, options);
      }

      public static StatusType parse(Reader r) throws XmlException, IOException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(r, StatusType.type, (XmlOptions)null);
      }

      public static StatusType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(r, StatusType.type, options);
      }

      public static StatusType parse(XMLStreamReader sr) throws XmlException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(sr, StatusType.type, (XmlOptions)null);
      }

      public static StatusType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(sr, StatusType.type, options);
      }

      public static StatusType parse(Node node) throws XmlException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(node, StatusType.type, (XmlOptions)null);
      }

      public static StatusType parse(Node node, XmlOptions options) throws XmlException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(node, StatusType.type, options);
      }

      /** @deprecated */
      public static StatusType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(xis, StatusType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static StatusType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (StatusType)XmlBeans.getContextTypeLoader().parse(xis, StatusType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatusType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, StatusType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ENABLED = 1;
      static final int INT_DISABLED = 2;
      static final int INT_DELETED = 3;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("enabled", 1), new Enum("disabled", 2), new Enum("deleted", 3)});
      private static final long serialVersionUID = 1L;

      public static Enum forString(String s) {
         return (Enum)table.forString(s);
      }

      public static Enum forInt(int i) {
         return (Enum)table.forInt(i);
      }

      private Enum(String s, int i) {
         super(s, i);
      }

      private Object readResolve() {
         return forInt(this.intValue());
      }
   }
}
