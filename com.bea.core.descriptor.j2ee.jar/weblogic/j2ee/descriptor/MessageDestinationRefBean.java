package weblogic.j2ee.descriptor;

public interface MessageDestinationRefBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getMessageDestinationRefName();

   void setMessageDestinationRefName(String var1);

   String getMessageDestinationType();

   void setMessageDestinationType(String var1);

   String getMessageDestinationUsage();

   void setMessageDestinationUsage(String var1);

   String getMessageDestinationLink();

   void setMessageDestinationLink(String var1);

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
