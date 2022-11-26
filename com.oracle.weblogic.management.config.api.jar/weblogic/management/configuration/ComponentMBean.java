package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

/** @deprecated */
@Deprecated
public interface ComponentMBean extends DeploymentMBean {
   ApplicationMBean getApplication();

   void setApplication(ApplicationMBean var1) throws InvalidAttributeValueException;

   String getURI();

   void setURI(String var1);

   TargetMBean[] getActivatedTargets();

   void addActivatedTarget(TargetMBean var1);

   void removeActivatedTarget(TargetMBean var1);

   void setActivatedTargets(TargetMBean[] var1);

   boolean activated(TargetMBean var1);

   void refreshDDsIfNeeded(String[] var1);
}
