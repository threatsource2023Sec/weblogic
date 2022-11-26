package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlLong;
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

public interface QuotaType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(QuotaType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("quotatype9130type");

   long getBytesMaximum();

   XmlLong xgetBytesMaximum();

   boolean isSetBytesMaximum();

   void setBytesMaximum(long var1);

   void xsetBytesMaximum(XmlLong var1);

   void unsetBytesMaximum();

   long getMessagesMaximum();

   XmlLong xgetMessagesMaximum();

   boolean isSetMessagesMaximum();

   void setMessagesMaximum(long var1);

   void xsetMessagesMaximum(XmlLong var1);

   void unsetMessagesMaximum();

   Policy.Enum getPolicy();

   Policy xgetPolicy();

   boolean isSetPolicy();

   void setPolicy(Policy.Enum var1);

   void xsetPolicy(Policy var1);

   void unsetPolicy();

   boolean getShared();

   XmlBoolean xgetShared();

   boolean isSetShared();

   void setShared(boolean var1);

   void xsetShared(XmlBoolean var1);

   void unsetShared();

   public static final class Factory {
      public static QuotaType newInstance() {
         return (QuotaType)XmlBeans.getContextTypeLoader().newInstance(QuotaType.type, (XmlOptions)null);
      }

      public static QuotaType newInstance(XmlOptions options) {
         return (QuotaType)XmlBeans.getContextTypeLoader().newInstance(QuotaType.type, options);
      }

      public static QuotaType parse(String xmlAsString) throws XmlException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QuotaType.type, (XmlOptions)null);
      }

      public static QuotaType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(xmlAsString, QuotaType.type, options);
      }

      public static QuotaType parse(File file) throws XmlException, IOException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(file, QuotaType.type, (XmlOptions)null);
      }

      public static QuotaType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(file, QuotaType.type, options);
      }

      public static QuotaType parse(URL u) throws XmlException, IOException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(u, QuotaType.type, (XmlOptions)null);
      }

      public static QuotaType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(u, QuotaType.type, options);
      }

      public static QuotaType parse(InputStream is) throws XmlException, IOException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(is, QuotaType.type, (XmlOptions)null);
      }

      public static QuotaType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(is, QuotaType.type, options);
      }

      public static QuotaType parse(Reader r) throws XmlException, IOException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(r, QuotaType.type, (XmlOptions)null);
      }

      public static QuotaType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(r, QuotaType.type, options);
      }

      public static QuotaType parse(XMLStreamReader sr) throws XmlException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(sr, QuotaType.type, (XmlOptions)null);
      }

      public static QuotaType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(sr, QuotaType.type, options);
      }

      public static QuotaType parse(Node node) throws XmlException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(node, QuotaType.type, (XmlOptions)null);
      }

      public static QuotaType parse(Node node, XmlOptions options) throws XmlException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(node, QuotaType.type, options);
      }

      /** @deprecated */
      public static QuotaType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(xis, QuotaType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static QuotaType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (QuotaType)XmlBeans.getContextTypeLoader().parse(xis, QuotaType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QuotaType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, QuotaType.type, options);
      }

      private Factory() {
      }
   }

   public interface Policy extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Policy.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("policy7ca2elemtype");
      Enum FIFO = QuotaType.Policy.Enum.forString("FIFO");
      Enum PREEMPTIVE = QuotaType.Policy.Enum.forString("Preemptive");
      int INT_FIFO = 1;
      int INT_PREEMPTIVE = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static Policy newValue(Object obj) {
            return (Policy)QuotaType.Policy.type.newValue(obj);
         }

         public static Policy newInstance() {
            return (Policy)XmlBeans.getContextTypeLoader().newInstance(QuotaType.Policy.type, (XmlOptions)null);
         }

         public static Policy newInstance(XmlOptions options) {
            return (Policy)XmlBeans.getContextTypeLoader().newInstance(QuotaType.Policy.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_FIFO = 1;
         static final int INT_PREEMPTIVE = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("FIFO", 1), new Enum("Preemptive", 2)});
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
}
