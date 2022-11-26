package com.bea.httppubsub;

public interface MessageFilter {
   boolean handleMessage(EventMessage var1);
}
