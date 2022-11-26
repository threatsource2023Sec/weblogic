package weblogic.j2ee.descriptor;

public interface EnvEntryBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getEnvEntryName();

   void setEnvEntryName(String var1);

   String getEnvEntryType();

   void setEnvEntryType(String var1);

   String getEnvEntryValue();

   void setEnvEntryValue(String var1);

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
