package com.bea.x2004.x03.wlw.externalConfig;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
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

public interface MemberConstraintBean extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MemberConstraintBean.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("memberconstraintbeand295type");

   ConstraintType.Enum getConstraintType();

   ConstraintType xgetConstraintType();

   void setConstraintType(ConstraintType.Enum var1);

   void xsetConstraintType(ConstraintType var1);

   String getMaxLength();

   XmlString xgetMaxLength();

   boolean isSetMaxLength();

   void setMaxLength(String var1);

   void xsetMaxLength(XmlString var1);

   void unsetMaxLength();

   String getMinValue();

   XmlString xgetMinValue();

   boolean isSetMinValue();

   void setMinValue(String var1);

   void xsetMinValue(XmlString var1);

   void unsetMinValue();

   String getMaxValue();

   XmlString xgetMaxValue();

   boolean isSetMaxValue();

   void setMaxValue(String var1);

   void xsetMaxValue(XmlString var1);

   void unsetMaxValue();

   int getScale();

   XmlInt xgetScale();

   boolean isSetScale();

   void setScale(int var1);

   void xsetScale(XmlInt var1);

   void unsetScale();

   public static final class Factory {
      public static MemberConstraintBean newInstance() {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().newInstance(MemberConstraintBean.type, (XmlOptions)null);
      }

      public static MemberConstraintBean newInstance(XmlOptions options) {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().newInstance(MemberConstraintBean.type, options);
      }

      public static MemberConstraintBean parse(String xmlAsString) throws XmlException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, MemberConstraintBean.type, (XmlOptions)null);
      }

      public static MemberConstraintBean parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(xmlAsString, MemberConstraintBean.type, options);
      }

      public static MemberConstraintBean parse(File file) throws XmlException, IOException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(file, MemberConstraintBean.type, (XmlOptions)null);
      }

      public static MemberConstraintBean parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(file, MemberConstraintBean.type, options);
      }

      public static MemberConstraintBean parse(URL u) throws XmlException, IOException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(u, MemberConstraintBean.type, (XmlOptions)null);
      }

      public static MemberConstraintBean parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(u, MemberConstraintBean.type, options);
      }

      public static MemberConstraintBean parse(InputStream is) throws XmlException, IOException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(is, MemberConstraintBean.type, (XmlOptions)null);
      }

      public static MemberConstraintBean parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(is, MemberConstraintBean.type, options);
      }

      public static MemberConstraintBean parse(Reader r) throws XmlException, IOException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(r, MemberConstraintBean.type, (XmlOptions)null);
      }

      public static MemberConstraintBean parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(r, MemberConstraintBean.type, options);
      }

      public static MemberConstraintBean parse(XMLStreamReader sr) throws XmlException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(sr, MemberConstraintBean.type, (XmlOptions)null);
      }

      public static MemberConstraintBean parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(sr, MemberConstraintBean.type, options);
      }

      public static MemberConstraintBean parse(Node node) throws XmlException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(node, MemberConstraintBean.type, (XmlOptions)null);
      }

      public static MemberConstraintBean parse(Node node, XmlOptions options) throws XmlException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(node, MemberConstraintBean.type, options);
      }

      /** @deprecated */
      public static MemberConstraintBean parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(xis, MemberConstraintBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MemberConstraintBean parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MemberConstraintBean)XmlBeans.getContextTypeLoader().parse(xis, MemberConstraintBean.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MemberConstraintBean.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MemberConstraintBean.type, options);
      }

      private Factory() {
      }
   }

   public interface ConstraintType extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ConstraintType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("constrainttype9e2belemtype");
      Enum DATE = MemberConstraintBean.ConstraintType.Enum.forString("DATE");
      Enum DECIMAL = MemberConstraintBean.ConstraintType.Enum.forString("DECIMAL");
      Enum INTEGER = MemberConstraintBean.ConstraintType.Enum.forString("INTEGER");
      Enum URL = MemberConstraintBean.ConstraintType.Enum.forString("URL");
      Enum URN = MemberConstraintBean.ConstraintType.Enum.forString("URN");
      Enum URI = MemberConstraintBean.ConstraintType.Enum.forString("URI");
      Enum XML = MemberConstraintBean.ConstraintType.Enum.forString("XML");
      Enum FILE = MemberConstraintBean.ConstraintType.Enum.forString("FILE");
      Enum CUSTOM = MemberConstraintBean.ConstraintType.Enum.forString("CUSTOM");
      Enum QNAME = MemberConstraintBean.ConstraintType.Enum.forString("QNAME");
      Enum TEXT = MemberConstraintBean.ConstraintType.Enum.forString("TEXT");
      Enum JNDINAME_DATASOURCE = MemberConstraintBean.ConstraintType.Enum.forString("JNDINAME_DATASOURCE");
      Enum JNDINAME_EJB = MemberConstraintBean.ConstraintType.Enum.forString("JNDINAME_EJB");
      Enum JNDINAME_JMS_TOPIC = MemberConstraintBean.ConstraintType.Enum.forString("JNDINAME_JMS_TOPIC");
      Enum JNDINAME_JMS_QUEUE = MemberConstraintBean.ConstraintType.Enum.forString("JNDINAME_JMS_QUEUE");
      Enum JNDINAME_OTHER = MemberConstraintBean.ConstraintType.Enum.forString("JNDINAME_OTHER");
      int INT_DATE = 1;
      int INT_DECIMAL = 2;
      int INT_INTEGER = 3;
      int INT_URL = 4;
      int INT_URN = 5;
      int INT_URI = 6;
      int INT_XML = 7;
      int INT_FILE = 8;
      int INT_CUSTOM = 9;
      int INT_QNAME = 10;
      int INT_TEXT = 11;
      int INT_JNDINAME_DATASOURCE = 12;
      int INT_JNDINAME_EJB = 13;
      int INT_JNDINAME_JMS_TOPIC = 14;
      int INT_JNDINAME_JMS_QUEUE = 15;
      int INT_JNDINAME_OTHER = 16;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static ConstraintType newValue(Object obj) {
            return (ConstraintType)MemberConstraintBean.ConstraintType.type.newValue(obj);
         }

         public static ConstraintType newInstance() {
            return (ConstraintType)XmlBeans.getContextTypeLoader().newInstance(MemberConstraintBean.ConstraintType.type, (XmlOptions)null);
         }

         public static ConstraintType newInstance(XmlOptions options) {
            return (ConstraintType)XmlBeans.getContextTypeLoader().newInstance(MemberConstraintBean.ConstraintType.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_DATE = 1;
         static final int INT_DECIMAL = 2;
         static final int INT_INTEGER = 3;
         static final int INT_URL = 4;
         static final int INT_URN = 5;
         static final int INT_URI = 6;
         static final int INT_XML = 7;
         static final int INT_FILE = 8;
         static final int INT_CUSTOM = 9;
         static final int INT_QNAME = 10;
         static final int INT_TEXT = 11;
         static final int INT_JNDINAME_DATASOURCE = 12;
         static final int INT_JNDINAME_EJB = 13;
         static final int INT_JNDINAME_JMS_TOPIC = 14;
         static final int INT_JNDINAME_JMS_QUEUE = 15;
         static final int INT_JNDINAME_OTHER = 16;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("DATE", 1), new Enum("DECIMAL", 2), new Enum("INTEGER", 3), new Enum("URL", 4), new Enum("URN", 5), new Enum("URI", 6), new Enum("XML", 7), new Enum("FILE", 8), new Enum("CUSTOM", 9), new Enum("QNAME", 10), new Enum("TEXT", 11), new Enum("JNDINAME_DATASOURCE", 12), new Enum("JNDINAME_EJB", 13), new Enum("JNDINAME_JMS_TOPIC", 14), new Enum("JNDINAME_JMS_QUEUE", 15), new Enum("JNDINAME_OTHER", 16)});
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
