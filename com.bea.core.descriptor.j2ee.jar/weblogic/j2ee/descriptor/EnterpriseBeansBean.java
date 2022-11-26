package weblogic.j2ee.descriptor;

public interface EnterpriseBeansBean {
   SessionBeanBean[] getSessions();

   SessionBeanBean createSession();

   SessionBeanBean createSession(String var1);

   void destroySession(SessionBeanBean var1);

   SessionBeanBean lookupSession(String var1);

   EntityBeanBean[] getEntities();

   EntityBeanBean createEntity();

   void destroyEntity(EntityBeanBean var1);

   MessageDrivenBeanBean[] getMessageDrivens();

   MessageDrivenBeanBean createMessageDriven();

   MessageDrivenBeanBean createMessageDriven(String var1);

   void destroyMessageDriven(MessageDrivenBeanBean var1);

   MessageDrivenBeanBean lookupMessageDriven(String var1);

   String getId();

   void setId(String var1);
}
