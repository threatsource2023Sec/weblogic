package weblogic.j2ee.descriptor;

public interface MessageListenerBean {
   String getMessageListenerType();

   void setMessageListenerType(String var1);

   ActivationSpecBean getActivationSpec();

   ActivationSpecBean createActivationSpec();

   void destroyActivationSpec(ActivationSpecBean var1);

   String getId();

   void setId(String var1);
}
