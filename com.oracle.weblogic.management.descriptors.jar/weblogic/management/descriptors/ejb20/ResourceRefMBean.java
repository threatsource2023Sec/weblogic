package weblogic.management.descriptors.ejb20;

public interface ResourceRefMBean extends weblogic.management.descriptors.ejb11.ResourceRefMBean {
   String getResSharingScope();

   void setResSharingScope(String var1);
}
