package weblogic.management.j2ee;

public interface J2EEDeployedObjectMBean extends J2EEManagedObjectMBean {
   String getdeploymentDescriptor();

   String getserver();

   String getproductSpecificDeploymentDescriptor();
}
