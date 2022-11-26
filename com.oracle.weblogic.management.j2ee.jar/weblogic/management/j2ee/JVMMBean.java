package weblogic.management.j2ee;

public interface JVMMBean extends J2EEManagedObjectMBean {
   String getjavaVersion();

   String getjavaVendor();

   String getnode();
}
