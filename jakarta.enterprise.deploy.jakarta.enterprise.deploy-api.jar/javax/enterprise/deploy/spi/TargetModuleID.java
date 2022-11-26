package javax.enterprise.deploy.spi;

public interface TargetModuleID {
   Target getTarget();

   String getModuleID();

   String getWebURL();

   String toString();

   TargetModuleID getParentTargetModuleID();

   TargetModuleID[] getChildTargetModuleID();
}
