package weblogic.rmi.internal;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.Context;
import weblogic.rmi.extensions.server.ClusterAwareRemoteReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.OutboundRequest;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.Debug;

public final class RedeployableRef implements RemoteReference {
   private RemoteReference delegate;
   private transient boolean DEBUG = false;
   private Context context = null;
   private String jndiName = null;

   public void setContext(Context context) {
      this.context = context;
   }

   public void setJNDIName(String jndiName) {
      this.jndiName = jndiName;
   }

   public RedeployableRef(RemoteReference ref, Context context, String jndiName) {
      if (ref instanceof ClusterAwareRemoteReference) {
         this.delegate = ((ClusterAwareRemoteReference)ref).getPrimaryRef();
      } else {
         this.delegate = ref;
      }

      this.context = context;
      this.jndiName = jndiName;
   }

   public Object invoke(Remote stub, RuntimeMethodDescriptor md, Object[] params, Method m) throws Throwable {
      try {
         return this.delegate.invoke(stub, md, params, m);
      } catch (RemoteException var7) {
         if (this.DEBUG) {
            Debug.say("--- redeployable --- got exception during invoke " + var7);
         }

         RemoteReference ref = this.getFailOverRef();
         if (ref != null) {
            this.delegate = ref;
            if (this.DEBUG) {
               Debug.say("--- redeployable --- Retrying with new delegate " + this.delegate);
            }

            return this.delegate.invoke(stub, md, params, m);
         } else {
            throw var7;
         }
      }
   }

   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionName, String partitionURL) throws IOException {
      return this.delegate.getOutboundRequest(md, partitionName, partitionURL);
   }

   /** @deprecated */
   @Deprecated
   public OutboundRequest getOutboundRequest(RuntimeMethodDescriptor md, String partitionURL) throws IOException {
      return this.delegate.getOutboundRequest(md, partitionURL);
   }

   private RemoteReference getFailOverRef() {
      if (this.context != null && this.jndiName != null) {
         try {
            Object obj = this.context.lookup(this.jndiName);
            if (this.DEBUG) {
               Debug.say("--- redeployable --- looked up new object " + obj);
            }

            if (obj == null) {
               return null;
            } else {
               Object stubInfo = RemoteObjectReplacer.getReplacer().replaceObject(obj);
               if (this.DEBUG) {
                  Debug.say("--- redeployable --- Got failover stubinfo " + stubInfo);
               }

               return ((StubInfo)stubInfo).getRemoteRef();
            }
         } catch (Throwable var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   public int getObjectID() {
      return this.delegate.getObjectID();
   }

   public HostID getHostID() {
      return this.delegate.getHostID();
   }

   public Channel getChannel() {
      return this.delegate.getChannel();
   }

   public String getCodebase() {
      return this.delegate.getCodebase();
   }

   public void setRequestTimedOut(boolean flag) {
   }

   public boolean hasRequestTimedOut() {
      return false;
   }
}
