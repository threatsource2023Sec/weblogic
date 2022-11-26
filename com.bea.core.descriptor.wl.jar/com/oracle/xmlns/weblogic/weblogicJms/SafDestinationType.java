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

public interface SafDestinationType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafDestinationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("safdestinationtyped92ftype");

   String getRemoteJndiName();

   XmlString xgetRemoteJndiName();

   void setRemoteJndiName(String var1);

   void xsetRemoteJndiName(XmlString var1);

   String getLocalJndiName();

   XmlString xgetLocalJndiName();

   boolean isNilLocalJndiName();

   boolean isSetLocalJndiName();

   void setLocalJndiName(String var1);

   void xsetLocalJndiName(XmlString var1);

   void setNilLocalJndiName();

   void unsetLocalJndiName();

   PersistentQos.Enum getPersistentQos();

   PersistentQos xgetPersistentQos();

   boolean isSetPersistentQos();

   void setPersistentQos(PersistentQos.Enum var1);

   void xsetPersistentQos(PersistentQos var1);

   void unsetPersistentQos();

   NonPersistentQos.Enum getNonPersistentQos();

   NonPersistentQos xgetNonPersistentQos();

   boolean isSetNonPersistentQos();

   void setNonPersistentQos(NonPersistentQos.Enum var1);

   void xsetNonPersistentQos(NonPersistentQos var1);

   void unsetNonPersistentQos();

   String getSafErrorHandling();

   XmlString xgetSafErrorHandling();

   boolean isNilSafErrorHandling();

   boolean isSetSafErrorHandling();

   void setSafErrorHandling(String var1);

   void xsetSafErrorHandling(XmlString var1);

   void setNilSafErrorHandling();

   void unsetSafErrorHandling();

   long getTimeToLiveDefault();

   XmlLong xgetTimeToLiveDefault();

   boolean isSetTimeToLiveDefault();

   void setTimeToLiveDefault(long var1);

   void xsetTimeToLiveDefault(XmlLong var1);

   void unsetTimeToLiveDefault();

   boolean getUseSafTimeToLiveDefault();

   XmlBoolean xgetUseSafTimeToLiveDefault();

   boolean isSetUseSafTimeToLiveDefault();

   void setUseSafTimeToLiveDefault(boolean var1);

   void xsetUseSafTimeToLiveDefault(XmlBoolean var1);

   void unsetUseSafTimeToLiveDefault();

   UnitOfOrderRoutingType.Enum getUnitOfOrderRouting();

   UnitOfOrderRoutingType xgetUnitOfOrderRouting();

   boolean isSetUnitOfOrderRouting();

   void setUnitOfOrderRouting(UnitOfOrderRoutingType.Enum var1);

   void xsetUnitOfOrderRouting(UnitOfOrderRoutingType var1);

   void unsetUnitOfOrderRouting();

   MessageLoggingParamsType getMessageLoggingParams();

   boolean isSetMessageLoggingParams();

   void setMessageLoggingParams(MessageLoggingParamsType var1);

   MessageLoggingParamsType addNewMessageLoggingParams();

   void unsetMessageLoggingParams();

   public static final class Factory {
      /** @deprecated */
      public static SafDestinationType newInstance() {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().newInstance(SafDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SafDestinationType newInstance(XmlOptions options) {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().newInstance(SafDestinationType.type, options);
      }

      public static SafDestinationType parse(String xmlAsString) throws XmlException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafDestinationType.type, (XmlOptions)null);
      }

      public static SafDestinationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, SafDestinationType.type, options);
      }

      public static SafDestinationType parse(File file) throws XmlException, IOException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(file, SafDestinationType.type, (XmlOptions)null);
      }

      public static SafDestinationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(file, SafDestinationType.type, options);
      }

      public static SafDestinationType parse(URL u) throws XmlException, IOException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(u, SafDestinationType.type, (XmlOptions)null);
      }

      public static SafDestinationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(u, SafDestinationType.type, options);
      }

      public static SafDestinationType parse(InputStream is) throws XmlException, IOException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(is, SafDestinationType.type, (XmlOptions)null);
      }

      public static SafDestinationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(is, SafDestinationType.type, options);
      }

      public static SafDestinationType parse(Reader r) throws XmlException, IOException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(r, SafDestinationType.type, (XmlOptions)null);
      }

      public static SafDestinationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(r, SafDestinationType.type, options);
      }

      public static SafDestinationType parse(XMLStreamReader sr) throws XmlException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(sr, SafDestinationType.type, (XmlOptions)null);
      }

      public static SafDestinationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(sr, SafDestinationType.type, options);
      }

      public static SafDestinationType parse(Node node) throws XmlException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(node, SafDestinationType.type, (XmlOptions)null);
      }

      public static SafDestinationType parse(Node node, XmlOptions options) throws XmlException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(node, SafDestinationType.type, options);
      }

      /** @deprecated */
      public static SafDestinationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(xis, SafDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static SafDestinationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (SafDestinationType)XmlBeans.getContextTypeLoader().parse(xis, SafDestinationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, SafDestinationType.type, options);
      }

      private Factory() {
      }
   }

   public interface NonPersistentQos extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(NonPersistentQos.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("nonpersistentqos8e94elemtype");
      Enum AT_MOST_ONCE = SafDestinationType.NonPersistentQos.Enum.forString("At-Most-Once");
      Enum AT_LEAST_ONCE = SafDestinationType.NonPersistentQos.Enum.forString("At-Least-Once");
      Enum EXACTLY_ONCE = SafDestinationType.NonPersistentQos.Enum.forString("Exactly-Once");
      int INT_AT_MOST_ONCE = 1;
      int INT_AT_LEAST_ONCE = 2;
      int INT_EXACTLY_ONCE = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static NonPersistentQos newValue(Object obj) {
            return (NonPersistentQos)SafDestinationType.NonPersistentQos.type.newValue(obj);
         }

         public static NonPersistentQos newInstance() {
            return (NonPersistentQos)XmlBeans.getContextTypeLoader().newInstance(SafDestinationType.NonPersistentQos.type, (XmlOptions)null);
         }

         public static NonPersistentQos newInstance(XmlOptions options) {
            return (NonPersistentQos)XmlBeans.getContextTypeLoader().newInstance(SafDestinationType.NonPersistentQos.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_AT_MOST_ONCE = 1;
         static final int INT_AT_LEAST_ONCE = 2;
         static final int INT_EXACTLY_ONCE = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("At-Most-Once", 1), new Enum("At-Least-Once", 2), new Enum("Exactly-Once", 3)});
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

   public interface PersistentQos extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(PersistentQos.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("persistentqos1194elemtype");
      Enum AT_MOST_ONCE = SafDestinationType.PersistentQos.Enum.forString("At-Most-Once");
      Enum AT_LEAST_ONCE = SafDestinationType.PersistentQos.Enum.forString("At-Least-Once");
      Enum EXACTLY_ONCE = SafDestinationType.PersistentQos.Enum.forString("Exactly-Once");
      int INT_AT_MOST_ONCE = 1;
      int INT_AT_LEAST_ONCE = 2;
      int INT_EXACTLY_ONCE = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static PersistentQos newValue(Object obj) {
            return (PersistentQos)SafDestinationType.PersistentQos.type.newValue(obj);
         }

         public static PersistentQos newInstance() {
            return (PersistentQos)XmlBeans.getContextTypeLoader().newInstance(SafDestinationType.PersistentQos.type, (XmlOptions)null);
         }

         public static PersistentQos newInstance(XmlOptions options) {
            return (PersistentQos)XmlBeans.getContextTypeLoader().newInstance(SafDestinationType.PersistentQos.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_AT_MOST_ONCE = 1;
         static final int INT_AT_LEAST_ONCE = 2;
         static final int INT_EXACTLY_ONCE = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("At-Most-Once", 1), new Enum("At-Least-Once", 2), new Enum("Exactly-Once", 3)});
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
