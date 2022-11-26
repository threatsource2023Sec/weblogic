package weblogic.j2ee.descriptor;

public interface ResourceEnvRefBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getResourceEnvRefName();

   void setResourceEnvRefName(String var1);

   String getResourceEnvRefType();

   void setResourceEnvRefType(String var1);

   String getMappedName();

   void setMappedName(String var1);

   InjectionTargetBean[] getInjectionTargets();

   InjectionTargetBean createInjectionTarget();

   void destroyInjectionTarget(InjectionTargetBean var1);

   String getLookupName();

   void setLookupName(String var1);

   String getId();

   void setId(String var1);
}
