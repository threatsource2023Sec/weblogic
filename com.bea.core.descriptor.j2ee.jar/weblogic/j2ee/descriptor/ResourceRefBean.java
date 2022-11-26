package weblogic.j2ee.descriptor;

public interface ResourceRefBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getResRefName();

   void setResRefName(String var1);

   String getResType();

   void setResType(String var1);

   String getResAuth();

   void setResAuth(String var1);

   String getResSharingScope();

   void setResSharingScope(String var1);

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
