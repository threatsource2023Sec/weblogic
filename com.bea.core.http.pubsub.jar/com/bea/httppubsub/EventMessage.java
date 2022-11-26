package com.bea.httppubsub;

import java.io.Serializable;

public interface EventMessage extends BayeuxMessage, Serializable {
   String getPayLoad();

   void setPayLoad(String var1);
}
