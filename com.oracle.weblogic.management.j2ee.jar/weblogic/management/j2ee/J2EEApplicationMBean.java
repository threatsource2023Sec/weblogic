package weblogic.management.j2ee;

public interface J2EEApplicationMBean extends J2EEDeployedObjectMBean {
   String[] getmodules();
}
