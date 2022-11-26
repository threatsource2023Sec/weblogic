package weblogic.diagnostics.accessor.config;

import java.util.Map;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.EditableAccessorConfiguration;

public class DefaultEditableAccessorConfiguration implements EditableAccessorConfiguration {
   private String name;
   private ColumnInfo[] columnInfos;
   private boolean participantInSizeBasedDataRetirement;
   private boolean ageBasedDataRetirementEnabled;
   private int retirementAge = 72;
   private int retirementPeriod = 24;
   private int retirementTime = 0;

   public DefaultEditableAccessorConfiguration(String name, ColumnInfo[] columnInfos, boolean participantInSizeBasedDataRetirement) {
      this.name = name;
      this.columnInfos = columnInfos;
      this.participantInSizeBasedDataRetirement = participantInSizeBasedDataRetirement;
   }

   public int getRetirementAge() {
      return this.retirementAge;
   }

   public void setRetirementAge(int retirementAge) {
      this.retirementAge = retirementAge;
   }

   public int getRetirementPeriod() {
      return this.retirementPeriod;
   }

   public void setRetirementPeriod(int retirementPeriod) {
      this.retirementPeriod = retirementPeriod;
   }

   public int getRetirementTime() {
      return this.retirementTime;
   }

   public void setRetirementTime(int retirementTime) {
      this.retirementTime = retirementTime;
   }

   public boolean isAgeBasedDataRetirementEnabled() {
      return this.ageBasedDataRetirementEnabled;
   }

   public void setAgeBasedDataRetirementEnabled(boolean ageBasedDataRetirementEnabled) {
      this.ageBasedDataRetirementEnabled = ageBasedDataRetirementEnabled;
   }

   public boolean isParticipantInSizeBasedDataRetirement() {
      return this.participantInSizeBasedDataRetirement;
   }

   public Map getAccessorParameters() {
      return null;
   }

   public ColumnInfo[] getColumns() {
      return this.columnInfos;
   }

   public String getName() {
      return this.name;
   }

   public boolean isModifiableConfiguration() {
      return false;
   }
}
