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

public interface DestinationType extends TargetableType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DestinationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("destinationtypeed1atype");

   String getTemplate();

   XmlString xgetTemplate();

   boolean isNilTemplate();

   boolean isSetTemplate();

   void setTemplate(String var1);

   void xsetTemplate(XmlString var1);

   void setNilTemplate();

   void unsetTemplate();

   String[] getDestinationKeyArray();

   String getDestinationKeyArray(int var1);

   XmlString[] xgetDestinationKeyArray();

   XmlString xgetDestinationKeyArray(int var1);

   boolean isNilDestinationKeyArray(int var1);

   int sizeOfDestinationKeyArray();

   void setDestinationKeyArray(String[] var1);

   void setDestinationKeyArray(int var1, String var2);

   void xsetDestinationKeyArray(XmlString[] var1);

   void xsetDestinationKeyArray(int var1, XmlString var2);

   void setNilDestinationKeyArray(int var1);

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

   String getJndiName();

   XmlString xgetJndiName();

   boolean isNilJndiName();

   boolean isSetJndiName();

   void setJndiName(String var1);

   void xsetJndiName(XmlString var1);

   void setNilJndiName();

   void unsetJndiName();

   String getLocalJndiName();

   XmlString xgetLocalJndiName();

   boolean isNilLocalJndiName();

   boolean isSetLocalJndiName();

   void setLocalJndiName(String var1);

   void xsetLocalJndiName(XmlString var1);

   void setNilLocalJndiName();

   void unsetLocalJndiName();

   String getJmsCreateDestinationIdentifier();

   XmlString xgetJmsCreateDestinationIdentifier();

   boolean isNilJmsCreateDestinationIdentifier();

   boolean isSetJmsCreateDestinationIdentifier();

   void setJmsCreateDestinationIdentifier(String var1);

   void xsetJmsCreateDestinationIdentifier(XmlString var1);

   void setNilJmsCreateDestinationIdentifier();

   void unsetJmsCreateDestinationIdentifier();

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

   String getLoadBalancingPolicy();

   XmlString xgetLoadBalancingPolicy();

   boolean isNilLoadBalancingPolicy();

   boolean isSetLoadBalancingPolicy();

   void setLoadBalancingPolicy(String var1);

   void xsetLoadBalancingPolicy(XmlString var1);

   void setNilLoadBalancingPolicy();

   void unsetLoadBalancingPolicy();

   UnitOfOrderRoutingType.Enum getUnitOfOrderRouting();

   UnitOfOrderRoutingType xgetUnitOfOrderRouting();

   boolean isSetUnitOfOrderRouting();

   void setUnitOfOrderRouting(UnitOfOrderRoutingType.Enum var1);

   void xsetUnitOfOrderRouting(UnitOfOrderRoutingType var1);

   void unsetUnitOfOrderRouting();

   public static final class Factory {
      public static DestinationType newInstance() {
         return (DestinationType)XmlBeans.getContextTypeLoader().newInstance(DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType newInstance(XmlOptions options) {
         return (DestinationType)XmlBeans.getContextTypeLoader().newInstance(DestinationType.type, options);
      }

      public static DestinationType parse(String xmlAsString) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DestinationType.type, options);
      }

      public static DestinationType parse(File file) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(file, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(file, DestinationType.type, options);
      }

      public static DestinationType parse(URL u) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(u, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(u, DestinationType.type, options);
      }

      public static DestinationType parse(InputStream is) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(is, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(is, DestinationType.type, options);
      }

      public static DestinationType parse(Reader r) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(r, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(r, DestinationType.type, options);
      }

      public static DestinationType parse(XMLStreamReader sr) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(sr, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(sr, DestinationType.type, options);
      }

      public static DestinationType parse(Node node) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(node, DestinationType.type, (XmlOptions)null);
      }

      public static DestinationType parse(Node node, XmlOptions options) throws XmlException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(node, DestinationType.type, options);
      }

      /** @deprecated */
      public static DestinationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(xis, DestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DestinationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DestinationType)XmlBeans.getContextTypeLoader().parse(xis, DestinationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DestinationType.type, options);
      }

      private Factory() {
      }
   }

   public interface UnitOfWorkHandlingPolicy extends XmlString {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UnitOfWorkHandlingPolicy.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("unitofworkhandlingpolicyf413elemtype");
      Enum PASS_THROUGH = DestinationType.UnitOfWorkHandlingPolicy.Enum.forString("PassThrough");
      Enum SINGLE_MESSAGE_DELIVERY = DestinationType.UnitOfWorkHandlingPolicy.Enum.forString("SingleMessageDelivery");
      int INT_PASS_THROUGH = 1;
      int INT_SINGLE_MESSAGE_DELIVERY = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static UnitOfWorkHandlingPolicy newValue(Object obj) {
            return (UnitOfWorkHandlingPolicy)DestinationType.UnitOfWorkHandlingPolicy.type.newValue(obj);
         }

         public static UnitOfWorkHandlingPolicy newInstance() {
            return (UnitOfWorkHandlingPolicy)XmlBeans.getContextTypeLoader().newInstance(DestinationType.UnitOfWorkHandlingPolicy.type, (XmlOptions)null);
         }

         public static UnitOfWorkHandlingPolicy newInstance(XmlOptions options) {
            return (UnitOfWorkHandlingPolicy)XmlBeans.getContextTypeLoader().newInstance(DestinationType.UnitOfWorkHandlingPolicy.type, options);
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
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(SafExportPolicy.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("safexportpolicydab8elemtype");
      Enum ALL = DestinationType.SafExportPolicy.Enum.forString("All");
      Enum NONE = DestinationType.SafExportPolicy.Enum.forString("None");
      int INT_ALL = 1;
      int INT_NONE = 2;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static SafExportPolicy newValue(Object obj) {
            return (SafExportPolicy)DestinationType.SafExportPolicy.type.newValue(obj);
         }

         public static SafExportPolicy newInstance() {
            return (SafExportPolicy)XmlBeans.getContextTypeLoader().newInstance(DestinationType.SafExportPolicy.type, (XmlOptions)null);
         }

         public static SafExportPolicy newInstance(XmlOptions options) {
            return (SafExportPolicy)XmlBeans.getContextTypeLoader().newInstance(DestinationType.SafExportPolicy.type, options);
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
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(AttachSender.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("attachsenderbd11elemtype");
      Enum SUPPORTS = DestinationType.AttachSender.Enum.forString("supports");
      Enum ALWAYS = DestinationType.AttachSender.Enum.forString("always");
      Enum NEVER = DestinationType.AttachSender.Enum.forString("never");
      int INT_SUPPORTS = 1;
      int INT_ALWAYS = 2;
      int INT_NEVER = 3;

      StringEnumAbstractBase enumValue();

      void set(StringEnumAbstractBase var1);

      public static final class Factory {
         public static AttachSender newValue(Object obj) {
            return (AttachSender)DestinationType.AttachSender.type.newValue(obj);
         }

         public static AttachSender newInstance() {
            return (AttachSender)XmlBeans.getContextTypeLoader().newInstance(DestinationType.AttachSender.type, (XmlOptions)null);
         }

         public static AttachSender newInstance(XmlOptions options) {
            return (AttachSender)XmlBeans.getContextTypeLoader().newInstance(DestinationType.AttachSender.type, options);
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
