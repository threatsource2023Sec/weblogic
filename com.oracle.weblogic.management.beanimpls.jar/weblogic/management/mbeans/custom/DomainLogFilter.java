package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.management.configuration.LogFilterMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public final class DomainLogFilter extends ConfigurationMBeanCustomizer {
   private int severityLevel = 16;
   private String[] userIds;
   private String[] subSystems;
   LogFilterMBean delegate = null;

   public DomainLogFilter(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public int getSeverityLevel() {
      return this.delegate != null ? this.delegate.getSeverityLevel() : this.severityLevel;
   }

   public void setSeverityLevel(int severity) {
      this.severityLevel = severity;
      if (this.delegate != null) {
         this.delegate.setSeverityLevel(severity);
      }

   }

   public String[] getSubsystemNames() {
      return this.delegate != null ? this.delegate.getSubsystemNames() : this.subSystems;
   }

   public void setSubsystemNames(String[] subsystemList) throws InvalidAttributeValueException {
      this.subSystems = subsystemList;
      if (this.delegate != null) {
         this.delegate.setSubsystemNames(this.subSystems);
      }

   }

   public String[] getUserIds() {
      return this.delegate != null ? this.delegate.getUserIds() : this.userIds;
   }

   public void setUserIds(String[] userIdList) throws InvalidAttributeValueException {
      this.userIds = userIdList;
      if (this.delegate != null) {
         this.delegate.setUserIds(this.userIds);
      }

   }

   public LogFilterMBean getDelegate() {
      return this.delegate;
   }

   public void setDelegate(LogFilterMBean other) {
      this.delegate = other;
   }
}
