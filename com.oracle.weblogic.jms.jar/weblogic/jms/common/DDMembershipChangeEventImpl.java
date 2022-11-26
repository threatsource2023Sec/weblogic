package weblogic.jms.common;

public class DDMembershipChangeEventImpl {
   private String ddConfigName;
   private String ddJndiName;
   private boolean isDD;
   private DDMemberInformation[] addedDDMemberInformation;
   private DDMemberInformation[] removedDDMemberInformation;

   public DDMembershipChangeEventImpl(boolean isDD, String ddConfigName, String ddJndiName, DDMemberInformation[] addedDDMemberInformation, DDMemberInformation[] removedDDMemberInformation) {
      this.isDD = isDD;
      this.ddConfigName = ddConfigName;
      this.ddJndiName = ddJndiName;
      this.addedDDMemberInformation = addedDDMemberInformation;
      this.removedDDMemberInformation = removedDDMemberInformation;
   }

   public String getDDConfigName() {
      return this.ddConfigName;
   }

   public String getDDJndiName() {
      return this.ddJndiName;
   }

   public DDMemberInformation[] getAddedDDMemberInformation() {
      return this.addedDDMemberInformation;
   }

   public DDMemberInformation[] getRemovedDDMemberInformation() {
      return this.removedDDMemberInformation;
   }

   public void setAdded(DDMemberInformation[] added) {
      this.addedDDMemberInformation = added;
   }

   public void setRemoved(DDMemberInformation[] removed) {
      this.removedDDMemberInformation = removed;
   }

   public boolean isDD() {
      return this.isDD;
   }
}
