package com.bea.httppubsub.descriptor;

public interface SupportedTransportBean {
   String[] getTypes();

   void addType(String var1);

   void removeType(String var1);
}
