package weblogic.management.configuration;

public interface ResourceGroupMBean extends ResourceGroupTemplateMBean {
   ResourceGroupTemplateMBean getResourceGroupTemplate();

   void setResourceGroupTemplate(ResourceGroupTemplateMBean var1);

   TargetMBean[] findEffectiveTargets();

   @ExportCustomizeableValues(
      saveDefault = true
   )
   TargetMBean[] getTargets();

   TargetMBean lookupTarget(String var1);

   void addTarget(TargetMBean var1);

   void removeTarget(TargetMBean var1);

   void setTargets(TargetMBean[] var1);

   boolean isUseDefaultTarget();

   void setUseDefaultTarget(boolean var1);

   Boolean[] areDefinedInTemplate(ConfigurationMBean[] var1);

   void setAutoTargetAdminServer(boolean var1);

   boolean isAutoTargetAdminServer();

   void setAdministrative(boolean var1);

   boolean isAdministrative();
}
