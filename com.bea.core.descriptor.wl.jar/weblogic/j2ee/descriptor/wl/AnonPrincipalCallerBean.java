package weblogic.j2ee.descriptor.wl;

public interface AnonPrincipalCallerBean extends AnonPrincipalBean {
   boolean isUseCallerIdentity();

   void setUseCallerIdentity(boolean var1);
}
