package weblogic.deploy.service;

import java.io.Serializable;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface StatusRelayer {
   void relayStatus(String var1, Serializable var2);

   void relayStatus(long var1, String var3, Serializable var4);
}
