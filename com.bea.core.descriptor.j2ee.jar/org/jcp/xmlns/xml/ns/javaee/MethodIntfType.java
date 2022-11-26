package org.jcp.xmlns.xml.ns.javaee;

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

public interface MethodIntfType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MethodIntfType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("methodintftype941atype");
   Enum HOME = MethodIntfType.Enum.forString("Home");
   Enum REMOTE = MethodIntfType.Enum.forString("Remote");
   Enum LOCAL_HOME = MethodIntfType.Enum.forString("LocalHome");
   Enum LOCAL = MethodIntfType.Enum.forString("Local");
   Enum SERVICE_ENDPOINT = MethodIntfType.Enum.forString("ServiceEndpoint");
   Enum TIMER = MethodIntfType.Enum.forString("Timer");
   Enum MESSAGE_ENDPOINT = MethodIntfType.Enum.forString("MessageEndpoint");
   Enum LIFECYCLE_CALLBACK = MethodIntfType.Enum.forString("LifecycleCallback");
   int INT_HOME = 1;
   int INT_REMOTE = 2;
   int INT_LOCAL_HOME = 3;
   int INT_LOCAL = 4;
   int INT_SERVICE_ENDPOINT = 5;
   int INT_TIMER = 6;
   int INT_MESSAGE_ENDPOINT = 7;
   int INT_LIFECYCLE_CALLBACK = 8;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static MethodIntfType newInstance() {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().newInstance(MethodIntfType.type, (XmlOptions)null);
      }

      public static MethodIntfType newInstance(XmlOptions options) {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().newInstance(MethodIntfType.type, options);
      }

      public static MethodIntfType parse(java.lang.String xmlAsString) throws XmlException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodIntfType.type, (XmlOptions)null);
      }

      public static MethodIntfType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MethodIntfType.type, options);
      }

      public static MethodIntfType parse(File file) throws XmlException, IOException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(file, MethodIntfType.type, (XmlOptions)null);
      }

      public static MethodIntfType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(file, MethodIntfType.type, options);
      }

      public static MethodIntfType parse(URL u) throws XmlException, IOException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(u, MethodIntfType.type, (XmlOptions)null);
      }

      public static MethodIntfType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(u, MethodIntfType.type, options);
      }

      public static MethodIntfType parse(InputStream is) throws XmlException, IOException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(is, MethodIntfType.type, (XmlOptions)null);
      }

      public static MethodIntfType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(is, MethodIntfType.type, options);
      }

      public static MethodIntfType parse(Reader r) throws XmlException, IOException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(r, MethodIntfType.type, (XmlOptions)null);
      }

      public static MethodIntfType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(r, MethodIntfType.type, options);
      }

      public static MethodIntfType parse(XMLStreamReader sr) throws XmlException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(sr, MethodIntfType.type, (XmlOptions)null);
      }

      public static MethodIntfType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(sr, MethodIntfType.type, options);
      }

      public static MethodIntfType parse(Node node) throws XmlException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(node, MethodIntfType.type, (XmlOptions)null);
      }

      public static MethodIntfType parse(Node node, XmlOptions options) throws XmlException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(node, MethodIntfType.type, options);
      }

      /** @deprecated */
      public static MethodIntfType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(xis, MethodIntfType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MethodIntfType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MethodIntfType)XmlBeans.getContextTypeLoader().parse(xis, MethodIntfType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodIntfType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MethodIntfType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_HOME = 1;
      static final int INT_REMOTE = 2;
      static final int INT_LOCAL_HOME = 3;
      static final int INT_LOCAL = 4;
      static final int INT_SERVICE_ENDPOINT = 5;
      static final int INT_TIMER = 6;
      static final int INT_MESSAGE_ENDPOINT = 7;
      static final int INT_LIFECYCLE_CALLBACK = 8;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Home", 1), new Enum("Remote", 2), new Enum("LocalHome", 3), new Enum("Local", 4), new Enum("ServiceEndpoint", 5), new Enum("Timer", 6), new Enum("MessageEndpoint", 7), new Enum("LifecycleCallback", 8)});
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
