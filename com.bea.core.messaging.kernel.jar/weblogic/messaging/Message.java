package weblogic.messaging;

public interface Message {
   MessageID getMessageID();

   long size();

   Message duplicate();

   Object getWorkContext();
}
