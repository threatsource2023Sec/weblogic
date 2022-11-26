package weblogic.management.descriptors.ejb20;

public interface EnterpriseBeansMBean extends weblogic.management.descriptors.ejb11.EnterpriseBeansMBean {
   MessageDrivenMBean[] getMessageDrivens();

   void setMessageDrivens(MessageDrivenMBean[] var1);

   void addMessageDriven(MessageDrivenMBean var1);

   void removeMessageDriven(MessageDrivenMBean var1);
}
