package weblogic.j2ee.descriptor;

public interface EjbRefBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getEjbRefName();

   void setEjbRefName(String var1);

   String getEjbRefType();

   void setEjbRefType(String var1);

   String getHome();

   void setHome(String var1);

   String getRemote();

   void setRemote(String var1);

   String getEjbLink();

   void setEjbLink(String var1);

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
