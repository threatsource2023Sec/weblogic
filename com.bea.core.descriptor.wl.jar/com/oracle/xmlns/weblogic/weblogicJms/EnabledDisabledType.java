package com.oracle.xmlns.weblogic.weblogicJms;

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

public interface EnabledDisabledType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(EnabledDisabledType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("enableddisabledtype87aetype");
   Enum ENABLED = EnabledDisabledType.Enum.forString("enabled");
   Enum DISABLED = EnabledDisabledType.Enum.forString("disabled");
   int INT_ENABLED = 1;
   int INT_DISABLED = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static EnabledDisabledType newValue(Object obj) {
         return (EnabledDisabledType)EnabledDisabledType.type.newValue(obj);
      }

      public static EnabledDisabledType newInstance() {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().newInstance(EnabledDisabledType.type, (XmlOptions)null);
      }

      public static EnabledDisabledType newInstance(XmlOptions options) {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().newInstance(EnabledDisabledType.type, options);
      }

      public static EnabledDisabledType parse(String xmlAsString) throws XmlException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnabledDisabledType.type, (XmlOptions)null);
      }

      public static EnabledDisabledType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(xmlAsString, EnabledDisabledType.type, options);
      }

      public static EnabledDisabledType parse(File file) throws XmlException, IOException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(file, EnabledDisabledType.type, (XmlOptions)null);
      }

      public static EnabledDisabledType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(file, EnabledDisabledType.type, options);
      }

      public static EnabledDisabledType parse(URL u) throws XmlException, IOException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(u, EnabledDisabledType.type, (XmlOptions)null);
      }

      public static EnabledDisabledType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(u, EnabledDisabledType.type, options);
      }

      public static EnabledDisabledType parse(InputStream is) throws XmlException, IOException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(is, EnabledDisabledType.type, (XmlOptions)null);
      }

      public static EnabledDisabledType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(is, EnabledDisabledType.type, options);
      }

      public static EnabledDisabledType parse(Reader r) throws XmlException, IOException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(r, EnabledDisabledType.type, (XmlOptions)null);
      }

      public static EnabledDisabledType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(r, EnabledDisabledType.type, options);
      }

      public static EnabledDisabledType parse(XMLStreamReader sr) throws XmlException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(sr, EnabledDisabledType.type, (XmlOptions)null);
      }

      public static EnabledDisabledType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(sr, EnabledDisabledType.type, options);
      }

      public static EnabledDisabledType parse(Node node) throws XmlException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(node, EnabledDisabledType.type, (XmlOptions)null);
      }

      public static EnabledDisabledType parse(Node node, XmlOptions options) throws XmlException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(node, EnabledDisabledType.type, options);
      }

      /** @deprecated */
      public static EnabledDisabledType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(xis, EnabledDisabledType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static EnabledDisabledType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (EnabledDisabledType)XmlBeans.getContextTypeLoader().parse(xis, EnabledDisabledType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnabledDisabledType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, EnabledDisabledType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_ENABLED = 1;
      static final int INT_DISABLED = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("enabled", 1), new Enum("disabled", 2)});
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
