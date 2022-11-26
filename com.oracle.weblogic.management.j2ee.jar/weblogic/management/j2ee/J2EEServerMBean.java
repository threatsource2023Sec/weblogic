package weblogic.management.j2ee;

public interface J2EEServerMBean extends J2EEManagedObjectMBean {
   String[] getdeployedObjects();

   String[] getresources();

   String[] getjavaVMs();

   String getserverVendor();

   String getserverVersion();
}
