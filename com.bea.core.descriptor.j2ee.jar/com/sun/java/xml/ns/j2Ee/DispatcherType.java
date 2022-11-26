package com.sun.java.xml.ns.j2Ee;

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

public interface DispatcherType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DispatcherType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("dispatchertype7bbatype");
   Enum FORWARD = DispatcherType.Enum.forString("FORWARD");
   Enum INCLUDE = DispatcherType.Enum.forString("INCLUDE");
   Enum REQUEST = DispatcherType.Enum.forString("REQUEST");
   Enum ERROR = DispatcherType.Enum.forString("ERROR");
   int INT_FORWARD = 1;
   int INT_INCLUDE = 2;
   int INT_REQUEST = 3;
   int INT_ERROR = 4;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static DispatcherType newInstance() {
         return (DispatcherType)XmlBeans.getContextTypeLoader().newInstance(DispatcherType.type, (XmlOptions)null);
      }

      public static DispatcherType newInstance(XmlOptions options) {
         return (DispatcherType)XmlBeans.getContextTypeLoader().newInstance(DispatcherType.type, options);
      }

      public static DispatcherType parse(java.lang.String xmlAsString) throws XmlException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DispatcherType.type, (XmlOptions)null);
      }

      public static DispatcherType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DispatcherType.type, options);
      }

      public static DispatcherType parse(File file) throws XmlException, IOException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(file, DispatcherType.type, (XmlOptions)null);
      }

      public static DispatcherType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(file, DispatcherType.type, options);
      }

      public static DispatcherType parse(URL u) throws XmlException, IOException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(u, DispatcherType.type, (XmlOptions)null);
      }

      public static DispatcherType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(u, DispatcherType.type, options);
      }

      public static DispatcherType parse(InputStream is) throws XmlException, IOException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(is, DispatcherType.type, (XmlOptions)null);
      }

      public static DispatcherType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(is, DispatcherType.type, options);
      }

      public static DispatcherType parse(Reader r) throws XmlException, IOException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(r, DispatcherType.type, (XmlOptions)null);
      }

      public static DispatcherType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(r, DispatcherType.type, options);
      }

      public static DispatcherType parse(XMLStreamReader sr) throws XmlException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(sr, DispatcherType.type, (XmlOptions)null);
      }

      public static DispatcherType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(sr, DispatcherType.type, options);
      }

      public static DispatcherType parse(Node node) throws XmlException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(node, DispatcherType.type, (XmlOptions)null);
      }

      public static DispatcherType parse(Node node, XmlOptions options) throws XmlException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(node, DispatcherType.type, options);
      }

      /** @deprecated */
      public static DispatcherType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(xis, DispatcherType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DispatcherType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DispatcherType)XmlBeans.getContextTypeLoader().parse(xis, DispatcherType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DispatcherType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DispatcherType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_FORWARD = 1;
      static final int INT_INCLUDE = 2;
      static final int INT_REQUEST = 3;
      static final int INT_ERROR = 4;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("FORWARD", 1), new Enum("INCLUDE", 2), new Enum("REQUEST", 3), new Enum("ERROR", 4)});
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
