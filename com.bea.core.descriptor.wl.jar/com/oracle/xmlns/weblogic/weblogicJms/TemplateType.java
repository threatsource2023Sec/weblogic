package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface TemplateType extends NamedEntityType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(TemplateType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("templatetypedc7ctype");

   String[] getDestinationKeyArray();

   String getDestinationKeyArray(int var1);

   XmlString[] xgetDestinationKeyArray();

   XmlString xgetDestinationKeyArray(int var1);

   int sizeOfDestinationKeyArray();

   void setDestinationKeyArray(String[] var1);

   void setDestinationKeyArray(int var1, String var2);

   void xsetDestinationKeyArray(XmlString[] var1);

   void xsetDestinationKeyArray(int var1, XmlString var2);

   void insertDestinationKey(int var1, String var2);

   void addDestinationKey(String var1);

   XmlString insertNewDestinationKey(int var1);

   XmlString addNewDestinationKey();

   void removeDestinationKey(int var1);

   ThresholdParamsType getThresholds();

   boolean isSetThresholds();

   void setThresholds(ThresholdParamsType var1);

   ThresholdParamsType addNewThresholds();

   void unsetThresholds();

   DeliveryParamsOverridesType getDeliveryParamsOverrides();

   boolean isSetDeliveryParamsOverrides();

   void setDeliveryParamsOverrides(DeliveryParamsOverridesType var1);

   DeliveryParamsOverridesType addNewDeliveryParamsOverrides();

   void unsetDeliveryParamsOverrides();

   DeliveryFailureParamsType getDeliveryFailureParams();

   boolean isSetDeliveryFailureParams();

   void setDeliveryFailureParams(DeliveryFailureParamsType var1);

   DeliveryFailureParamsType addNewDeliveryFailureParams();

   void unsetDeliveryFailureParams();

   MessageLoggingParamsType getMessageLoggingParams();

   boolean isSetMessageLoggingParams();

   void setMessageLoggingParams(MessageLoggingParamsType var1);

   MessageLoggingParamsType addNewMessageLoggingParams();

   void unsetMessageLoggingParams();

   AttachSender.Enum getAttachSender();

   AttachSender xgetAttachSender();

   boolean isSetAttachSender();

   void setAttachSender(AttachSender.Enum var1);

   void xsetAttachSender(AttachSender var1);

   void unsetAttachSender();

   boolean getProductionPausedAtStartup();

   XmlBoolean xgetProductionPausedAtStartup();

   boolean isSetProductionPausedAtStartup();

   void setProductionPausedAtStartup(boolean var1);

   void xsetProductionPausedAtStartup(XmlBoolean var1);

   void unsetProductionPausedAtStartup();

   boolean getInsertionPausedAtStartup();

   XmlBoolean xgetInsertionPausedAtStartup();

   boolean isSetInsertionPausedAtStartup();

   void setInsertionPausedAtStartup(boolean var1);

   void xsetInsertionPausedAtStartup(XmlBoolean var1);

   void unsetInsertionPausedAtStartup();

   boolean getConsumptionPausedAtStartup();

   XmlBoolean xgetConsumptionPausedAtStartup();

   boolean isSetConsumptionPausedAtStartup();

   void setConsumptionPausedAtStartup(boolean var1);

   void xsetConsumptionPausedAtStartup(XmlBoolean var1);

   void unsetConsumptionPausedAtStartup();

   int getMaximumMessageSize();

   XmlInt xgetMaximumMessageSize();

   boolean isSetMaximumMessageSize();

   void setMaximumMessageSize(int var1);

   void xsetMaximumMessageSize(XmlInt var1);

   void unsetMaximumMessageSize();

   String getQuota();

   XmlString xgetQuota();

   boolean isNilQuota();

   boolean isSetQuota();

   void setQuota(String var1);

   void xsetQuota(XmlString var1);

   void setNilQuota();

   void unsetQuota();

   boolean getDefaultUnitOfOrder();

   XmlBoolean xgetDefaultUnitOfOrder();

   boolean isSetDefaultUnitOfOrder();

   void setDefaultUnitOfOrder(boolean var1);

   void xsetDefaultUnitOfOrder(XmlBoolean var1);

   void unsetDefaultUnitOfOrder();

   SafExportPolicy.Enum getSafExportPolicy();

   SafExportPolicy xgetSafExportPolicy();

   boolean isSetSafExportPolicy();

   void setSafExportPolicy(SafExportPolicy.Enum var1);

   void xsetSafExportPolicy(SafExportPolicy var1);

   void unsetSafExportPolicy();

   TopicSubscriptionParamsType getTopicSubscriptionParams();

   boolean isSetTopicSubscriptionParams();

   void setTopicSubscriptionParams(TopicSubscriptionParamsType var1);

   TopicSubscriptionParamsType addNewTopicSubscriptionParams();

   void unsetTopicSubscriptionParams();

   MulticastParamsType getMulticast();

   boolean isSetMulticast();

   void setMulticast(MulticastParamsType var1);

   MulticastParamsType addNewMulticast();

   void unsetMulticast();

   GroupParamsType[] getGroupParamsArray();

   GroupParamsType getGroupParamsArray(int var1);

   int sizeOfGroupParamsArray();

   void setGroupParamsArray(GroupParamsType[] var1);

   void setGroupParamsArray(int var1, GroupParamsType var2);

   GroupParamsType insertNewGroupParams(int var1);

   GroupParamsType addNewGroupParams();

   void removeGroupParams(int var1);

   int getMessagingPerformancePreference();

   XmlInt xgetMessagingPerformancePreference();

   boolean isSetMessagingPerformancePreference();

   void setMessagingPerformancePreference(int var1);

   void xsetMessagingPerformancePreference(XmlInt var1);

   void unsetMessagingPerformancePreference();

   UnitOfWorkHandlingPolicy.Enum getUnitOfWorkHandlingPolicy();

   UnitOfWorkHandlingPolicy xgetUnitOfWorkHandlingPolicy();

   boolean isSetUnitOfWorkHandlingPolicy();

   void setUnitOfWorkHandlingPolicy(UnitOfWorkHandlingPolicy.Enum var1);

   void xsetUnitOfWorkHandlingPolicy(UnitOfWorkHandlingPolicy var1);

   void unsetUnitOfWorkHandlingPolicy();

   int getIncompleteWorkExpirationTime();

   XmlInt xgetIncompleteWorkExpirationTime();

   boolean isSetIncompleteWorkExpirationTime();

   void setIncompleteWorkExpirationTime(int var1);

   void xsetIncompleteWorkExpirationTime(XmlInt var1);

   void unsetIncompleteWorkExpirationTime();

   public static final class Factory {
      public static TemplateType newInstance() {
         return (TemplateType)XmlBeans.getContextTypeLoader().newInstance(TemplateType.type, (XmlOptions)null);
      }

      public static TemplateType newInstance(XmlOptions options) {
         return (TemplateType)XmlBeans.getContextTypeLoader().newInstance(TemplateType.type, options);
      }

      public static TemplateType parse(String xmlAsString) throws XmlException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TemplateType.type, (XmlOptions)null);
      }

      public static TemplateType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(xmlAsString, TemplateType.type, options);
      }

      public static TemplateType parse(File file) throws XmlException, IOException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(file, TemplateType.type, (XmlOptions)null);
      }

      public static TemplateType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(file, TemplateType.type, options);
      }

      public static TemplateType parse(URL u) throws XmlException, IOException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(u, TemplateType.type, (XmlOptions)null);
      }

      public static TemplateType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(u, TemplateType.type, options);
      }

      public static TemplateType parse(InputStream is) throws XmlException, IOException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(is, TemplateType.type, (XmlOptions)null);
      }

      public static TemplateType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(is, TemplateType.type, options);
      }

      public static TemplateType parse(Reader r) throws XmlException, IOException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(r, TemplateType.type, (XmlOptions)null);
      }

      public static TemplateType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(r, TemplateType.type, options);
      }

      public static TemplateType parse(XMLStreamReader sr) throws XmlException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(sr, TemplateType.type, (XmlOptions)null);
      }

      public static TemplateType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(sr, TemplateType.type, options);
      }

      public static TemplateType parse(Node node) throws XmlException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(node, TemplateType.type, (XmlOptions)null);
      }

      public static TemplateType parse(Node node, XmlOptions options) throws XmlException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(node, TemplateType.type, options);
      }

      /** @deprecated */
      public static TemplateType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(xis, TemplateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static TemplateType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (TemplateType)XmlBeans.getContextTypeLoader().parse(xis, TemplateType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TemplateType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, TemplateType.type, options);
      }

      private Factory() {
      }
   }

   public interface UnitOfWorkHandlingPolicy extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UnitOfWorkHandlingPolicy.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("unitofworkhandlingpolicye023elemtype");
      Enum PASS_THROUGH = TemplateType.UnitOfWorkHandlingPolicy.Enum.forString("PassThrough");
      Enum SINGLE_MESSAGE_DELIVERY = TemplateType.UnitOfWorkHandlingPolicy.Enum.forString("SingleMessageDelivery");
      int INT_PASS_THROUGH = 1;
      int INT_SINGLE_MESSAGE_DELIVERY = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static UnitOfWorkHandlingPolicy newValue(Object obj) {
            return (UnitOfWorkHandlingPolicy)TemplateType.UnitOfWorkHandlingPolicy.type.newValue(obj);
         }

         public static UnitOfWorkHandlingPolicy newInstance() {
            return (UnitOfWorkHandlingPolicy)XmlBeans.getContextTypeLoader().newInstance(TemplateType.UnitOfWorkHandlingPolicy.type, (XmlOptions)null);
         }

         public static UnitOfWorkHandlingPolicy newInstance(XmlOptions options) {
            return (UnitOfWorkHandlingPolicy)XmlBeans.getContextTypeLoader().newInstance(TemplateType.UnitOfWorkHandlingPolicy.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_PASS_THROUGH = 1;
         static final int INT_SINGLE_MESSAGE_DELIVERY = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("PassThrough", 1), new Enum("SingleMessageDelivery", 2)});
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

   public interface SafExportPolicy extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafExportPolicy.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("safexportpolicy039eelemtype");
      Enum ALL = TemplateType.SafExportPolicy.Enum.forString("All");
      Enum NONE = TemplateType.SafExportPolicy.Enum.forString("None");
      int INT_ALL = 1;
      int INT_NONE = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static SafExportPolicy newValue(Object obj) {
            return (SafExportPolicy)TemplateType.SafExportPolicy.type.newValue(obj);
         }

         public static SafExportPolicy newInstance() {
            return (SafExportPolicy)XmlBeans.getContextTypeLoader().newInstance(TemplateType.SafExportPolicy.type, (XmlOptions)null);
         }

         public static SafExportPolicy newInstance(XmlOptions options) {
            return (SafExportPolicy)XmlBeans.getContextTypeLoader().newInstance(TemplateType.SafExportPolicy.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_ALL = 1;
         static final int INT_NONE = 2;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("All", 1), new Enum("None", 2)});
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

   public interface AttachSender extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AttachSender.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("attachsendere7e5elemtype");
      Enum SUPPORTS = TemplateType.AttachSender.Enum.forString("supports");
      Enum ALWAYS = TemplateType.AttachSender.Enum.forString("always");
      Enum NEVER = TemplateType.AttachSender.Enum.forString("never");
      int INT_SUPPORTS = 1;
      int INT_ALWAYS = 2;
      int INT_NEVER = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static AttachSender newValue(Object obj) {
            return (AttachSender)TemplateType.AttachSender.type.newValue(obj);
         }

         public static AttachSender newInstance() {
            return (AttachSender)XmlBeans.getContextTypeLoader().newInstance(TemplateType.AttachSender.type, (XmlOptions)null);
         }

         public static AttachSender newInstance(XmlOptions options) {
            return (AttachSender)XmlBeans.getContextTypeLoader().newInstance(TemplateType.AttachSender.type, options);
         }

         private Factory() {
         }
      }

      public static final class Enum extends StringEnumAbstractBase {
         static final int INT_SUPPORTS = 1;
         static final int INT_ALWAYS = 2;
         static final int INT_NEVER = 3;
         public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("supports", 1), new Enum("always", 2), new Enum("never", 3)});
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
