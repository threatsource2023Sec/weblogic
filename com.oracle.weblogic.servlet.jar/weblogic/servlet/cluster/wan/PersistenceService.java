package weblogic.servlet.cluster.wan;

import org.jvnet.hk2.annotations.Contract;
import weblogic.servlet.internal.session.WANSessionData;

@Contract
public interface PersistenceService {
   void update(String var1, long var2, String var4, int var5, long var6, SessionDiff var8);

   boolean fetchState(String var1, String var2, WANSessionData var3);

   void invalidate(String var1, String var2);

   boolean isServiceAvailable();

   void flushUponShutdown(String var1, long var2, String var4, int var5, long var6, SessionDiff var8);
}
