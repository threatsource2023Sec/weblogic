package weblogic.rmi.cluster;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;

public interface ReplicaHandler {
   String FAILOVER_DEBUGGER_NAME = "DebugFailOver";
   String FAILOVER_VERBOSE_DEBUGGER_NAME = "DebugFailOverVerbose";
   String LOADBALANCING_DEBUGGER_NAME = "DebugLoadBalancing";

   RemoteReference failOver(RemoteReference var1, RuntimeMethodDescriptor var2, Method var3, Object[] var4, RemoteException var5, RetryHandler var6) throws RemoteException;

   RemoteReference loadBalance(RemoteReference var1, Method var2, Object[] var3, TransactionalAffinityHandler var4, RuntimeMethodDescriptor var5);

   ReplicaList getReplicaList();

   void resetReplicaList(ReplicaList var1);
}
