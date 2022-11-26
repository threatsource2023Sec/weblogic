package weblogic.common.internal;

import java.io.Serializable;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.t3.srvr.RemoteClientContext;

public final class T3ClientParams implements Serializable {
   private static final long serialVersionUID = -6997801029634041436L;
   public static final int DISCONNECT_MIN = -2;
   public static final int DISCONNECT_TIMEOUT_DEFAULT = -2;
   public static final int DISCONNECT_TIMEOUT_NEVER = -1;
   public boolean verbose = false;
   public int hardDisconnectTimeoutMins = -2;
   public int softDisconnectTimeoutMins = -2;
   public int idleSoftDisconnectTimeoutMins = -2;
   public String serverName;
   public String wsName;
   public String wsID;
   public int ccID;
   public RemoteClientContext rcc;
   public byte QOS;
   public AuthenticatedUser user;
}
