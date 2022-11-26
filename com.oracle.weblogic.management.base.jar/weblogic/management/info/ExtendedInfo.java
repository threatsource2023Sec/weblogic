package weblogic.management.info;

import javax.management.Attribute;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanRegistrationException;
import weblogic.utils.StringUtils;

public class ExtendedInfo extends MBeanInfo {
   static final long serialVersionUID = 1L;
   private static final String SILVERSWORD_RELEASE = "6.1.0.0";
   private boolean cachingDisabled = false;
   private boolean isPersistent = true;
   private boolean isAbstract = false;
   private String legalDelete = null;
   private String legalDeleteResponse = null;
   private String since = "6.1.0.0";
   private boolean isPersistedEvenIfDefaulted = false;
   private int majorVersion = 6;
   private int minorVersion = 1;
   private int servicePack = 0;
   private int rollingPatch = 0;
   private String customizerClass = null;

   public String getCustomizerClass() {
      return this.customizerClass;
   }

   public ExtendedInfo(String classNameArg, String descriptionArg, MBeanAttributeInfo[] attributesArg, MBeanConstructorInfo[] constructorsArg, MBeanOperationInfo[] operationsArg, MBeanNotificationInfo[] notificationsArg, boolean cachingDisabledArg, boolean isPersistentArg, boolean isAbstractArg, String legalDelete, String legalDeleteResponse, String sinceArg, boolean isPersistedEvenIfDefaultedArg, String customizerClass) {
      super(classNameArg, descriptionArg, attributesArg, constructorsArg, operationsArg, notificationsArg);
      this.cachingDisabled = cachingDisabledArg;
      this.isPersistent = isPersistentArg;
      this.isAbstract = isAbstractArg;
      this.legalDelete = legalDelete;
      this.legalDeleteResponse = legalDeleteResponse;
      this.isPersistedEvenIfDefaulted = isPersistedEvenIfDefaultedArg;
      this.customizerClass = customizerClass;
      if (sinceArg != null) {
         this.since = sinceArg;
         this.createVersionInfo();
      }

   }

   public boolean isCachingDisabled() {
      return this.cachingDisabled;
   }

   public boolean isPersistent() {
      return this.isPersistent;
   }

   public boolean isAbstract() {
      return this.isAbstract;
   }

   public String getLegalDeleteCheck() {
      return this.legalDelete;
   }

   public String getLegalDeleteCheckResponse() {
      return this.legalDeleteResponse;
   }

   public String getSince() {
      return this.since;
   }

   public boolean isPersistedEvenIfDefaulted() {
      return this.isPersistedEvenIfDefaulted;
   }

   public int getMajorVersion() {
      return this.majorVersion;
   }

   public int getMinorVersion() {
      return this.minorVersion;
   }

   public int getServicePack() {
      return this.servicePack;
   }

   public int getRollingPatch() {
      return this.rollingPatch;
   }

   public Object clone() {
      return this;
   }

   public Object copy() {
      return new ExtendedInfo(this.getClassName(), this.getDescription(), this.getAttributes(), this.getConstructors(), this.getOperations(), this.getNotifications(), this.cachingDisabled, this.isPersistent, this.isAbstract, this.legalDelete, this.legalDeleteResponse, this.since, this.isPersistedEvenIfDefaulted, this.customizerClass);
   }

   public void validateAttribute(Object mBean, Attribute attribute) throws InvalidAttributeValueException {
   }

   public void validateAddOperation(Object mBean, String attribute, Object value) throws InvalidAttributeValueException {
   }

   public void validateRemoveOperation(Object mBean, String attribute, Object value) throws InvalidAttributeValueException {
   }

   public void validateAttributes(Object mBean, Attribute[] attributes) throws InvalidAttributeValueException {
   }

   public void validateDeletion(Object mBean) throws MBeanRegistrationException {
   }

   private void createVersionInfo() {
      try {
         String[] versions = StringUtils.splitCompletely(this.since, " .");
         if (versions.length > 0) {
            this.majorVersion = Integer.parseInt(versions[0]);
            if (versions.length > 1) {
               this.minorVersion = Integer.parseInt(versions[1]);
               if (versions.length > 2) {
                  this.servicePack = Integer.parseInt(versions[2]);
                  if (versions.length > 3) {
                     this.rollingPatch = Integer.parseInt(versions[3]);
                  }
               }
            } else {
               this.minorVersion = 0;
            }
         }
      } catch (NumberFormatException var2) {
      }

   }
}
