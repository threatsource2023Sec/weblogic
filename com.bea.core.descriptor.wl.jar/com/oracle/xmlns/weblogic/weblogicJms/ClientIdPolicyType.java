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

public interface ClientIdPolicyType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ClientIdPolicyType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("clientidpolicytype2e54type");
   Enum RESTRICTED = ClientIdPolicyType.Enum.forString("Restricted");
   Enum UNRESTRICTED = ClientIdPolicyType.Enum.forString("Unrestricted");
   int INT_RESTRICTED = 1;
   int INT_UNRESTRICTED = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ClientIdPolicyType newValue(Object obj) {
         return (ClientIdPolicyType)ClientIdPolicyType.type.newValue(obj);
      }

      public static ClientIdPolicyType newInstance() {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().newInstance(ClientIdPolicyType.type, (XmlOptions)null);
      }

      public static ClientIdPolicyType newInstance(XmlOptions options) {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().newInstance(ClientIdPolicyType.type, options);
      }

      public static ClientIdPolicyType parse(String xmlAsString) throws XmlException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientIdPolicyType.type, (XmlOptions)null);
      }

      public static ClientIdPolicyType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ClientIdPolicyType.type, options);
      }

      public static ClientIdPolicyType parse(File file) throws XmlException, IOException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(file, ClientIdPolicyType.type, (XmlOptions)null);
      }

      public static ClientIdPolicyType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(file, ClientIdPolicyType.type, options);
      }

      public static ClientIdPolicyType parse(URL u) throws XmlException, IOException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(u, ClientIdPolicyType.type, (XmlOptions)null);
      }

      public static ClientIdPolicyType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(u, ClientIdPolicyType.type, options);
      }

      public static ClientIdPolicyType parse(InputStream is) throws XmlException, IOException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(is, ClientIdPolicyType.type, (XmlOptions)null);
      }

      public static ClientIdPolicyType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(is, ClientIdPolicyType.type, options);
      }

      public static ClientIdPolicyType parse(Reader r) throws XmlException, IOException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(r, ClientIdPolicyType.type, (XmlOptions)null);
      }

      public static ClientIdPolicyType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(r, ClientIdPolicyType.type, options);
      }

      public static ClientIdPolicyType parse(XMLStreamReader sr) throws XmlException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ClientIdPolicyType.type, (XmlOptions)null);
      }

      public static ClientIdPolicyType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(sr, ClientIdPolicyType.type, options);
      }

      public static ClientIdPolicyType parse(Node node) throws XmlException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(node, ClientIdPolicyType.type, (XmlOptions)null);
      }

      public static ClientIdPolicyType parse(Node node, XmlOptions options) throws XmlException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(node, ClientIdPolicyType.type, options);
      }

      /** @deprecated */
      public static ClientIdPolicyType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ClientIdPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ClientIdPolicyType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ClientIdPolicyType)XmlBeans.getContextTypeLoader().parse(xis, ClientIdPolicyType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientIdPolicyType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ClientIdPolicyType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_RESTRICTED = 1;
      static final int INT_UNRESTRICTED = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Restricted", 1), new Enum("Unrestricted", 2)});
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
