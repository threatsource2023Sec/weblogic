package weblogic.j2ee.descriptor;

public interface SecurityIdentityBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   EmptyBean getUseCallerIdentity();

   EmptyBean createUseCallerIdentity();

   void destroyUseCallerIdentity(EmptyBean var1);

   RunAsBean getRunAs();

   RunAsBean createRunAs();

   void destroyRunAs(RunAsBean var1);

   String getId();

   void setId(String var1);
}
