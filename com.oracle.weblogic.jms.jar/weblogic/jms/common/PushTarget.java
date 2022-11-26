package weblogic.jms.common;

public interface PushTarget {
   void pushMessage(JMSPushRequest var1);

   JMSID getJMSID();
}
