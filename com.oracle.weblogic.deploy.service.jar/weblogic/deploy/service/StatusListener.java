package weblogic.deploy.service;

import java.io.Serializable;

public interface StatusListener {
   void statusReceived(Serializable var1, String var2);

   void statusReceived(long var1, Serializable var3, String var4);
}
