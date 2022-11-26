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

public interface ResSharingScopeType extends String {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ResSharingScopeType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("ressharingscopetype5eb1type");
   Enum SHAREABLE = ResSharingScopeType.Enum.forString("Shareable");
   Enum UNSHAREABLE = ResSharingScopeType.Enum.forString("Unshareable");
   int INT_SHAREABLE = 1;
   int INT_UNSHAREABLE = 2;

   StringEnumAbstractBase enumValue();

   void set(StringEnumAbstractBase var1);

   public static final class Factory {
      public static ResSharingScopeType newInstance() {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().newInstance(ResSharingScopeType.type, (XmlOptions)null);
      }

      public static ResSharingScopeType newInstance(XmlOptions options) {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().newInstance(ResSharingScopeType.type, options);
      }

      public static ResSharingScopeType parse(java.lang.String xmlAsString) throws XmlException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResSharingScopeType.type, (XmlOptions)null);
      }

      public static ResSharingScopeType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ResSharingScopeType.type, options);
      }

      public static ResSharingScopeType parse(File file) throws XmlException, IOException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(file, ResSharingScopeType.type, (XmlOptions)null);
      }

      public static ResSharingScopeType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(file, ResSharingScopeType.type, options);
      }

      public static ResSharingScopeType parse(URL u) throws XmlException, IOException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(u, ResSharingScopeType.type, (XmlOptions)null);
      }

      public static ResSharingScopeType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(u, ResSharingScopeType.type, options);
      }

      public static ResSharingScopeType parse(InputStream is) throws XmlException, IOException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(is, ResSharingScopeType.type, (XmlOptions)null);
      }

      public static ResSharingScopeType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(is, ResSharingScopeType.type, options);
      }

      public static ResSharingScopeType parse(Reader r) throws XmlException, IOException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(r, ResSharingScopeType.type, (XmlOptions)null);
      }

      public static ResSharingScopeType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(r, ResSharingScopeType.type, options);
      }

      public static ResSharingScopeType parse(XMLStreamReader sr) throws XmlException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(sr, ResSharingScopeType.type, (XmlOptions)null);
      }

      public static ResSharingScopeType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(sr, ResSharingScopeType.type, options);
      }

      public static ResSharingScopeType parse(Node node) throws XmlException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(node, ResSharingScopeType.type, (XmlOptions)null);
      }

      public static ResSharingScopeType parse(Node node, XmlOptions options) throws XmlException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(node, ResSharingScopeType.type, options);
      }

      /** @deprecated */
      public static ResSharingScopeType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(xis, ResSharingScopeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ResSharingScopeType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ResSharingScopeType)XmlBeans.getContextTypeLoader().parse(xis, ResSharingScopeType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResSharingScopeType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ResSharingScopeType.type, options);
      }

      private Factory() {
      }
   }

   public static final class Enum extends StringEnumAbstractBase {
      static final int INT_SHAREABLE = 1;
      static final int INT_UNSHAREABLE = 2;
      public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("Shareable", 1), new Enum("Unshareable", 2)});
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
