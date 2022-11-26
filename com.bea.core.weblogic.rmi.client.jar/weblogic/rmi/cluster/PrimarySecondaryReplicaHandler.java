package weblogic.rmi.cluster;

import java.io.EOFException;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.CommunicationException;
import javax.naming.Context;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PeerInfoable;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.rjvm.PeerGoneException;
import weblogic.rmi.RMILogger;
import weblogic.rmi.RemoteEJBInvokeException;
import weblogic.rmi.RemoteEJBPreInvokeException;
import weblogic.rmi.cluster.ejb.PreInvokeDeserializationException;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.UnrecoverableConnectionException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtilsClient;

public final class PrimarySecondaryReplicaHandler implements ReplicaHandler, PiggybackRequester, Externalizable {
   private static final DebugLogger debugFailoverLogger = DebugLogger.getDebugLogger("DebugFailOver");
   private static final DebugLogger debugFailoverVerboseLogger = DebugLogger.getDebugLogger("DebugFailOverVerbose");
   private static final long serialVersionUID = -1707367770014954050L;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static int DEFAULT_REPLICA_QUERY_TIMEOUT = 30000;
   private ReplicaList replicaList;
   private transient Hashtable staledReplicas = new Hashtable();
   private transient String clusterURL = null;
   private transient Object env = null;
   private transient String partitionName = null;

   public PrimarySecondaryReplicaHandler(ReplicaAwareInfo info, RemoteReference primary) {
      this.replicaList = new BasicReplicaList(primary);
   }

   private boolean isConnectionFailure(Exception e) {
      Throwable t = e;

      while(t != null) {
         if (e instanceof PeerGoneException) {
            return true;
         }

         if (t instanceof EOFException) {
            return true;
         }

         if (t instanceof ConnectException) {
            return true;
         }

         if (t instanceof java.rmi.ConnectException) {
            return true;
         }

         if (t instanceof UnrecoverableConnectionException) {
            return true;
         }

         if (!(t instanceof IOException)) {
            t = ((Throwable)t).getCause();
         } else {
            String msg = ((Throwable)t).getMessage();
            if (msg == null) {
               t = ((Throwable)t).getCause();
            } else {
               if (msg.contains("UnrecoverableConnectionException")) {
                  return true;
               }

               if (msg.contains("No available router to destination")) {
                  return true;
               }

               if (msg.contains("Connection reset by peer")) {
                  return true;
               }

               t = ((Throwable)t).getCause();
            }
         }
      }

      return false;
   }

   protected boolean isRecoverableFailure(RuntimeMethodDescriptor md, RemoteException e, List recorder) {
      if (e instanceof RemoteEJBPreInvokeException) {
         return true;
      } else {
         if (!(e instanceof RemoteEJBInvokeException)) {
            ReplicaID ri = this.replicaList.getReplicaID();
            if (ri != null && ri.getID() instanceof byte[] && this.isConnectionFailure(e)) {
               return true;
            }
         } else {
            Throwable t = BasicReplicaHandler.unwrapRemoteEJBInvokeException((RemoteEJBInvokeException)e);
            if (!(t instanceof RemoteException)) {
               return false;
            }

            e = (RemoteException)t;
         }

         boolean b;
         if (md.isIdempotent()) {
            b = RemoteHelper.isRecoverableFailure(e);
            if (!b) {
               recorder.add("[" + md + "]UnrecoverableFailure");
            }

            return b;
         } else {
            b = RemoteHelper.isRecoverablePreInvokeFailure(e);
            if (!b) {
               recorder.add("[" + md + "]Non-RecoverablePreInvokeFailure");
            }

            return b;
         }
      }
   }

   public RemoteReference loadBalance(RemoteReference currentReplica, Method method, Object[] params, TransactionalAffinityHandler txnAffinityHandler, RuntimeMethodDescriptor md) {
      if (debugFailoverVerboseLogger.isDebugEnabled()) {
         debugFailoverVerboseLogger.debug("In PrimarySecondaryReplicaHandler.LOADBALANCE(currentReplica=" + currentReplica + ", txnAffinityHandler=" + txnAffinityHandler + "), replicaList=[" + this.replicaList + "]");
      }

      if (txnAffinityHandler != null && txnAffinityHandler.requiresAffinityBasedHandling(md)) {
         RemoteReference preferredRef = txnAffinityHandler.findTxnAffinityBasedRef(currentReplica, "", this.replicaList);
         if (debugFailoverVerboseLogger.isDebugEnabled()) {
            debugFailoverVerboseLogger.debug("PrimarySecondaryReplicaHandler.localbalance(currentReplica=" + currentReplica + ", txnAffinityHandler=" + txnAffinityHandler + "): Found preferred replica " + preferredRef);
         }

         if (preferredRef != null) {
            return preferredRef;
         }
      }

      return currentReplica;
   }

   public RemoteReference failOver(RemoteReference failedReplica, RuntimeMethodDescriptor md, Method method, Object[] params, RemoteException re, RetryHandler retryHandler) throws RemoteException {
      this.DEBUG("failOver", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + ", class=" + failedReplica.getClass().getName() + "]), exception=" + re.getMessage() + ", repliaList=[" + this.replicaList + "]" + retryHandler.isStaleListRevivingAttempted());
      this.replicaList.remove(failedReplica);
      this.staledReplicas.put(failedReplica.getHostID(), failedReplica);
      List recorder = new ArrayList();
      boolean replicaliststaled = false;
      boolean isrecoverable = this.isRecoverableFailure(md, re, recorder);
      synchronized(this.replicaList) {
         if (this.replicaList.size() > 0 && isrecoverable) {
            this.DEBUG("failOver", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Failing over to " + this.replicaList.getPrimary().getHostID());
            return this.replicaList.getPrimary();
         }

         if (this.replicaList.size() == 0) {
            replicaliststaled = true;
         } else {
            recorder.add("ReplicaListSize[" + this.replicaList.size() + "]");
         }
      }

      if (replicaliststaled && isrecoverable) {
         this.DEBUG("failOver", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): replicaliststaled=true, isRecoverableFailure=true, exception=[" + re.getClass() + ", " + re.getCause() + "]");
         RemoteReference ref = null;
         if (re instanceof PreInvokeDeserializationException) {
            ref = ((PreInvokeDeserializationException)re).getFailoverRemoteRef();
         } else if (re.getCause() instanceof PreInvokeDeserializationException) {
            ref = ((PreInvokeDeserializationException)re.getCause()).getFailoverRemoteRef();
         }

         if (ref != null) {
            this.DEBUG("failOver", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): exception=" + re + ", failoverRef=" + ref);
            return ref;
         }

         ref = this.tryQueryReplica(failedReplica, re, retryHandler, recorder);
         if (ref != null) {
            this.DEBUG("failOver", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): return ref=" + ref + "[class=" + ref.getClass().getName() + "]");
            return ref;
         }
      }

      if (retryHandler.getRetryCount() >= retryHandler.getMaxRetryCount()) {
         this.logFailureTrace(recorder, retryHandler.getMaxRetryCount());
      }

      this.DEBUG("failOver", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Unable to failover");
      throw RemoteHelper.returnOrUnwrap(re);
   }

   private void logFailureTrace(List recorder, int maxRetries) {
      StringBuilder builder = new StringBuilder();
      Iterator var4 = recorder.iterator();

      while(var4.hasNext()) {
         Object item = var4.next();
         if (item instanceof String) {
            builder.append(item + "\n");
         } else if (item instanceof Throwable) {
            builder.append("\n" + StackTraceUtilsClient.throwable2StackTrace((Throwable)item) + "\n");
         }
      }

      RMILogger.logFailOverFailureTrace(builder.toString(), maxRetries);
   }

   public ReplicaList getReplicaList() {
      return this.replicaList;
   }

   public void resetReplicaList(ReplicaList newList) {
      this.replicaList = newList;
   }

   public void resetRefreshedCount() {
   }

   public Version getPiggybackRequest() {
      return this.replicaList.version();
   }

   public void setPiggybackResponse(Object response) {
      if (response instanceof ReplicaList) {
         this.replicaList.reset((ReplicaList)response);
      } else if (response instanceof ReplicaVersion) {
         this.replicaList.setReplicaVersion((ReplicaVersion)response);
      }

   }

   private RemoteReference tryQueryReplica(RemoteReference failedReplica, RemoteException re, RetryHandler retryHandler, List recorder) throws RemoteException {
      this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]), exception=" + re.getMessage() + ", query attempted=" + retryHandler.isStaleListRevivingAttempted());
      if (retryHandler.isStaleListRevivingAttempted()) {
         recorder.add("QueryReplicaAlreadyAttempted");
         return null;
      } else {
         ReplicaID replicaID = this.replicaList.getReplicaID();
         ReplicaVersion replicaVersion = this.replicaList.getReplicaVersion();
         if (replicaID == null) {
            recorder.add("ReplicaID null");
            this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): The ReplicaID used for query replica not available");
            return null;
         } else if (replicaVersion == null) {
            recorder.add("ReplicaVersion null");
            this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): The ReplicaVersion used for query replica not available");
            return null;
         } else {
            retryHandler.setStaleListRevivingAttempted();

            try {
               Object myenv = this.env;
               boolean isnewenv = false;
               recorder.add("Environment: " + myenv);
               if (myenv == null) {
                  myenv = RMIEnvironment.getEnvironment().threadEnvironmentGet();
                  recorder.add("Thread Environment: " + myenv);
               }

               if (myenv == null) {
                  myenv = RMIEnvironment.getEnvironment().newEnvironment();
                  isnewenv = true;
                  recorder.add("New Environment: " + myenv);
                  this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): threadEnvironmentGet returns null, use newEnvironment");
               }

               ReplicaInfo replicaInfo = null;
               ReplicaInfo replicaInfoFromCluster = null;
               boolean isIIOP = failedReplica.getHostID().getHostURI().startsWith("iiop");
               if (!isnewenv || !isIIOP) {
                  replicaInfo = this.tryDefaultJNDIContext(failedReplica, myenv, replicaID, replicaVersion, recorder);
                  if (replicaInfo != null && replicaInfo.getRemoteRef() != null) {
                     return replicaInfo.getRemoteRef();
                  }
               }

               String myurl = this.clusterURL;
               if (myurl != null) {
                  recorder.add("Try ClusterURL " + myurl);
                  this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Try cluster url " + myurl);
                  Context ctx = this.getJNDIContextFromEnvForURL(failedReplica, myurl, myenv, recorder);
                  if (ctx != null) {
                     label457: {
                        RemoteReference var14;
                        try {
                           replicaInfoFromCluster = this.findReplicaRemoteRef(failedReplica, ctx, replicaID, replicaVersion, recorder);
                           if (replicaInfoFromCluster == null || replicaInfoFromCluster.getRemoteRef() == null) {
                              break label457;
                           }

                           var14 = replicaInfoFromCluster.getRemoteRef();
                        } finally {
                           try {
                              ctx.close();
                           } catch (Throwable var41) {
                           }

                        }

                        return var14;
                     }
                  }
               }

               ReplicaInfo rinfo = this.tryStaledReplicaList(failedReplica, myenv, replicaID, replicaVersion, recorder);
               if (rinfo != null && rinfo.getRemoteRef() != null) {
                  return rinfo.getRemoteRef();
               } else {
                  if (rinfo != null) {
                     replicaInfo = rinfo;
                  }

                  String[] targetClusterAddress = null;
                  if (replicaInfoFromCluster != null) {
                     targetClusterAddress = replicaInfoFromCluster.getTargetClusterAddresses();
                  }

                  if (targetClusterAddress == null && replicaInfo != null) {
                     targetClusterAddress = replicaInfo.getTargetClusterAddresses();
                  }

                  if (targetClusterAddress != null && targetClusterAddress.length != 0) {
                     recorder.add("Try TargetClusterAddress[" + Arrays.toString(targetClusterAddress) + "][partition=" + this.partitionName + "]");
                     String[] var15 = targetClusterAddress;
                     int var16 = targetClusterAddress.length;

                     for(int var17 = 0; var17 < var16; ++var17) {
                        String address = var15[var17];
                        if (isIIOP && address.startsWith("t3")) {
                           address = address.replaceFirst("t3", "iiop");
                        }

                        this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Try target cluster address " + address + " for partition: " + this.partitionName);
                        Context ctx = this.getJNDIContextFromEnvForURL(failedReplica, address, myenv, recorder);
                        if (ctx != null) {
                           try {
                              rinfo = this.findReplicaRemoteRef(failedReplica, ctx, replicaID, replicaVersion, recorder);
                              if (rinfo != null && rinfo.getRemoteRef() != null) {
                                 RemoteReference var20 = rinfo.getRemoteRef();
                                 return var20;
                              }
                           } finally {
                              try {
                                 ctx.close();
                              } catch (Throwable var40) {
                              }

                           }
                        }
                     }

                     return null;
                  } else {
                     return null;
                  }
               }
            } catch (Exception var44) {
               recorder.add(var44);
               this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Got exception while attempting to revive replicalist: " + var44, var44);
               throw new RemoteException(var44.getMessage(), var44);
            }
         }
      }
   }

   private ReplicaInfo tryDefaultJNDIContext(RemoteReference failedReplica, Object myenv, ReplicaID replicaID, ReplicaVersion replicaVersion, List recorder) {
      Hashtable propsDefault = RMIEnvironment.getEnvironment().getProperties(myenv);
      Hashtable propsDefaultLog = new Hashtable(propsDefault);
      propsDefaultLog.remove("java.naming.security.credentials");
      Context ctxDefault = null;

      try {
         ManagedInvocationContext ignored = RmiInvocationFacade.setPartitionName(kernelId, RmiInvocationFacade.getGlobalPartitionName());
         Throwable var10 = null;

         try {
            ctxDefault = RMIEnvironment.getEnvironment().getContext(myenv);
         } catch (Throwable var34) {
            var10 = var34;
            throw var34;
         } finally {
            if (ignored != null) {
               if (var10 != null) {
                  try {
                     ignored.close();
                  } catch (Throwable var33) {
                     var10.addSuppressed(var33);
                  }
               } else {
                  ignored.close();
               }
            }

         }
      } catch (Exception var36) {
         if (recorder != null) {
            recorder.add(var36);
         }

         this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]):Exception on getting JNDI context using default env[" + propsDefaultLog + "]: " + var36);
      }

      if (ctxDefault == null) {
         return null;
      } else {
         if (recorder != null) {
            recorder.add("Try Default JNDI Context");
         }

         this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Try default JNDI environment[" + propsDefaultLog + "]");

         ReplicaInfo var37;
         try {
            var37 = this.findReplicaRemoteRef(failedReplica, ctxDefault, replicaID, replicaVersion, recorder);
         } finally {
            try {
               ctxDefault.close();
            } catch (Throwable var31) {
            }

         }

         return var37;
      }
   }

   private ReplicaInfo tryStaledReplicaList(RemoteReference failedReplica, Object myenv, ReplicaID replicaID, ReplicaVersion replicaVersion, List recorder) throws Exception {
      List replicas = new ArrayList(this.staledReplicas.values());
      this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Try provider urls from staled replicalist " + replicas);
      Iterator var7 = replicas.iterator();

      while(true) {
         Context ctx;
         do {
            if (!var7.hasNext()) {
               return null;
            }

            RemoteReference replica = (RemoteReference)var7.next();
            HostID oldhid = replica.getHostID();
            String uri = oldhid.getHostURI();
            ctx = this.getJNDIContextFromEnvForURL(failedReplica, uri, myenv, recorder);
         } while(ctx == null);

         try {
            ReplicaInfo info = this.findReplicaRemoteRef(failedReplica, ctx, replicaID, replicaVersion, recorder);
            if (info != null) {
               ReplicaInfo var13 = info;
               return var13;
            }
         } finally {
            try {
               ctx.close();
            } catch (Throwable var21) {
            }

         }
      }
   }

   private Context getJNDIContextFromEnvForURL(RemoteReference failedReplica, String url, Object myenv, List recorder) throws Exception {
      Class c = Class.forName("weblogic.jndi.Environment");
      Method m = c.getMethod("setProviderURL", String.class);
      m.invoke(myenv, url);
      if (recorder != null) {
         recorder.add("JNDI Context URL: " + url);
      }

      Context ctx = null;
      int retries = 1;

      for(int i = 0; i < retries; ++i) {
         try {
            ManagedInvocationContext ignored = RmiInvocationFacade.setPartitionName(kernelId, RmiInvocationFacade.getGlobalPartitionName());
            Throwable var11 = null;

            try {
               ctx = RMIEnvironment.getEnvironment().getContext(myenv);
            } catch (Throwable var23) {
               var11 = var23;
               throw var23;
            } finally {
               if (ignored != null) {
                  if (var11 != null) {
                     try {
                        ignored.close();
                     } catch (Throwable var22) {
                        var11.addSuppressed(var22);
                     }
                  } else {
                     ignored.close();
                  }
               }

            }
         } catch (Exception var25) {
            if (recorder != null) {
               recorder.add(var25);
            }

            this.DEBUG("tryQueryReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Exception on getting JNDI context using url " + url + ": " + var25, var25);
            if (!(var25 instanceof CommunicationException)) {
               break;
            }

            try {
               Thread.sleep(1000L);
            } catch (Exception var21) {
            }
         }
      }

      return ctx;
   }

   private ReplicaInfo findReplicaRemoteRef(RemoteReference failedReplica, Context ctx, ReplicaID replicaID, ReplicaVersion replicaVersion, List recorder) {
      ReplicaInfo info = null;

      try {
         info = this.findReplica(failedReplica, ctx, replicaID, replicaVersion, recorder);
      } catch (Exception var8) {
         if (recorder != null) {
            recorder.add(var8);
         }

         this.DEBUG("findReplicaRemoteRef", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Exception on findReplica(): " + var8, var8);
      }

      return info;
   }

   private ReplicaInfo findReplica(RemoteReference failedReplica, Context ctx, ReplicaID replicaID, ReplicaVersion replicaVersion, List recorder) throws Exception {
      this.DEBUG("findReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Lookup ReplicaService with url " + ctx.getEnvironment().get("java.naming.provider.url"));
      RemoteReplicaService rrs = (RemoteReplicaService)ctx.lookup("weblogic.cluster.RemoteReplicaService");
      if (recorder != null) {
         recorder.add("RemoteReplicaService@" + rrs);
      }

      this.DEBUG("findReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Looked up ReplicaService[" + rrs + "]");
      ReplicaInfo info = rrs.findReplica(replicaID, replicaVersion, this.partitionName);
      this.DEBUG("findReplica", "failedReplica[" + failedReplica.getHostID() + ", " + failedReplica.getObjectID() + "]): Found replica " + info);
      return info;
   }

   public String toString() {
      return this.replicaList != null ? this.replicaList.toString() : super.toString();
   }

   public PrimarySecondaryReplicaHandler() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         WLObjectOutput wlOut = (WLObjectOutput)out;
         wlOut.writeObjectWL(this.replicaList);
      } else {
         out.writeObject(this.replicaList);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      if (in instanceof WLObjectInput) {
         WLObjectInput wlIn = (WLObjectInput)in;
         this.replicaList = (ReplicaList)wlIn.readObjectWL();
      } else {
         this.replicaList = (ReplicaList)in.readObject();
      }

      try {
         this.env = RMIEnvironment.getEnvironment().threadEnvironmentGet();
         RemoteReference ref = null;
         HostID host = null;
         EndPoint ep = null;
         if (this.replicaList != null) {
            ref = this.replicaList.getPrimary();
            if (ref != null) {
               host = ref.getHostID();
               if (host != null) {
                  ep = RMIRuntime.findEndPoint(host);
                  if (ep != null) {
                     String v = ep.getClusterURL(in);
                     if (v != null) {
                        this.clusterURL = v;
                     }
                  }
               }
            }
         }

         if (this.clusterURL == null && in instanceof PeerInfoable) {
            this.DEBUG("readExternal", "env=" + this.env + ", clusterURL=" + this.clusterURL + ", replicaList=" + this.replicaList + ", ref=" + ref + ", host=" + host + ", ep=" + ep);
         }
      } catch (Exception var6) {
         this.DEBUG("readExternal", "Exception while getting env=" + this.env + " or clusterURL=" + this.clusterURL + ": " + var6, var6);
      }

   }

   void setPartitionName(String partitionName) {
      this.partitionName = partitionName;
   }

   private void DEBUG(String method, String msg) {
      this.DEBUG(method, msg, (Throwable)null);
   }

   private void DEBUG(String method, String msg, Throwable e) {
      if (debugFailoverLogger.isDebugEnabled()) {
         if (e == null) {
            debugFailoverLogger.debug("PrimarySecondaryReplicaHandler." + method + "(): " + msg);
         } else {
            debugFailoverLogger.debug(msg, e);
         }
      }

   }
}
