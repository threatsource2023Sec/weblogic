package weblogic.management.j2ee.internal;

public class J2EEModuleMBeanImpl extends J2EEDeployedObjectMBeanImpl {
   protected final String[] javaVMS;

   public J2EEModuleMBeanImpl(String name, String server, String javaVM, ApplicationInfo info) {
      super(name, server, info);
      this.javaVMS = new String[]{javaVM};
   }

   public String[] getjavaVMs() {
      return this.javaVMS;
   }
}
