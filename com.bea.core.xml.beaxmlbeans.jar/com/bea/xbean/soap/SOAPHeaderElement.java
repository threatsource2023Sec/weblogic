package com.bea.xbean.soap;

public interface SOAPHeaderElement extends SOAPElement {
   void setActor(String var1);

   String getActor();

   void setMustUnderstand(boolean var1);

   boolean getMustUnderstand();
}
