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

public interface UnitOfOrderRoutingType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UnitOfOrderRoutingType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("unitoforderroutingtype056etype");
   Enum HASH = UnitOfOrderRoutingType.Enum.forString("Hash");
   Enum PATH_SERVICE = UnitOfOrderRoutingType.Enum.forString("PathService");
   int INT_HASH = 1;
   int INT_PATH_SERVICE = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static UnitOfOrderRoutingType newValue(Object obj) {
         return (UnitOfOrderRoutingType)UnitOfOrderRoutingType.type.newValue(obj);
      }

      public static UnitOfOrderRoutingType newInstance() {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().newInstance(UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      public static UnitOfOrderRoutingType newInstance(XmlOptions options) {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().newInstance(UnitOfOrderRoutingType.type, options);
      }

      public static UnitOfOrderRoutingType parse(String xmlAsString) throws XmlException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      public static UnitOfOrderRoutingType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UnitOfOrderRoutingType.type, options);
      }

      public static UnitOfOrderRoutingType parse(File file) throws XmlException, IOException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(file, UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      public static UnitOfOrderRoutingType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(file, UnitOfOrderRoutingType.type, options);
      }

      public static UnitOfOrderRoutingType parse(URL u) throws XmlException, IOException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(u, UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      public static UnitOfOrderRoutingType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(u, UnitOfOrderRoutingType.type, options);
      }

      public static UnitOfOrderRoutingType parse(InputStream is) throws XmlException, IOException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(is, UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      public static UnitOfOrderRoutingType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(is, UnitOfOrderRoutingType.type, options);
      }

      public static UnitOfOrderRoutingType parse(Reader r) throws XmlException, IOException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(r, UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      public static UnitOfOrderRoutingType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(r, UnitOfOrderRoutingType.type, options);
      }

      public static UnitOfOrderRoutingType parse(XMLStreamReader sr) throws XmlException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(sr, UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      public static UnitOfOrderRoutingType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(sr, UnitOfOrderRoutingType.type, options);
      }

      public static UnitOfOrderRoutingType parse(Node node) throws XmlException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(node, UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      public static UnitOfOrderRoutingType parse(Node node, XmlOptions options) throws XmlException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(node, UnitOfOrderRoutingType.type, options);
      }

      /** @deprecated */
      public static UnitOfOrderRoutingType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(xis, UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UnitOfOrderRoutingType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UnitOfOrderRoutingType)XmlBeans.getContextTypeLoader().parse(xis, UnitOfOrderRoutingType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UnitOfOrderRoutingType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UnitOfOrderRoutingType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_HASH = 1;
      static final int INT_PATH_SERVICE = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Hash", 1), new Enum("PathService", 2)});
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
