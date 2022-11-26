package weblogic.j2ee.descriptor.wl;

public interface TargetableBean extends NamedEntityBean {
   String getSubDeploymentName();

   void setSubDeploymentName(String var1) throws IllegalArgumentException;

   boolean isDefaultTargetingEnabled();

   void setDefaultTargetingEnabled(boolean var1) throws IllegalArgumentException;
}
