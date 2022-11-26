package weblogic.nodemanager.util;

import java.util.List;

public interface DomainInfo {
   ServerInfo getServerInfo(String var1, String var2);

   List getAllServerInfo();

   boolean isServerRegistered(String var1, String var2);

   boolean isServerConfigured(String var1, String var2);

   boolean isAuthorized(String var1, String var2);
}
