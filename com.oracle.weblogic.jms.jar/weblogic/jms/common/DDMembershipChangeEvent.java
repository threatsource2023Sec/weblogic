package weblogic.jms.common;

public interface DDMembershipChangeEvent {
   boolean isDD();

   String getDDJndiName();

   String getDDConfigName();

   DDMemberInformation[] getAddedDDMemberInformation();

   DDMemberInformation[] getRemovedDDMemberInformation();
}
