package com.bea.httppubsub;

public interface MessageFactory {
   EventMessage createEventMessage(LocalClient var1, String var2, String var3);
}
