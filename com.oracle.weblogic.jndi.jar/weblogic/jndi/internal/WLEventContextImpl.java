package weblogic.jndi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.event.EventContext;
import javax.naming.event.NamingListener;
import javax.naming.spi.NamingManager;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.utils.AssertionError;

public final class WLEventContextImpl extends WLContextImpl implements EventContext, Externalizable, InteropWriteReplaceable {
   private List listeners;
   public static final String ENABLE_EVENTS = "weblogic.jndi.events.enable";
   private static final long serialVersionUID = -570490474896887060L;
   private NamingNode node;
   private Hashtable env;
   private boolean copyNotRequired;

   public WLEventContextImpl(Hashtable env, NamingNode node, boolean copyNotRequired) {
      super(env, node);
      this.listeners = Collections.synchronizedList(new ArrayList());
      this.node = node;
      this.env = env;
      this.copyNotRequired = copyNotRequired;
   }

   public WLEventContextImpl(Hashtable env, NamingNode node) {
      this(env, node, false);
   }

   public Context createSubcontext(String name) throws NamingException {
      try {
         return this.node != null ? this.node.createSubcontext(name, this.env) : super.createSubcontext(name);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public Object lookupLink(String name) throws NamingException {
      try {
         return this.node != null ? this.node.lookupLink(name, this.env) : super.lookupLink(name);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public void destroySubcontext(String name) throws NamingException {
      try {
         if (this.node != null) {
            this.node.destroySubcontext(name, this.env);
         } else {
            super.destroySubcontext(name);
         }

      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public void rebind(String name, Object object) throws NamingException {
      try {
         if (this.node != null) {
            Object copy = this.copyObject(object);
            this.node.rebind(name, copy, this.env);
         } else {
            super.rebind(name, object);
         }

      } catch (RemoteException var4) {
         throw this.translateException(var4);
      }
   }

   public void rebind(String name, Object oldObject, Object newObject) throws NamingException {
      try {
         if (this.node != null) {
            Object newCopy = this.copyObject(newObject);
            Object oldCopy = this.copyObject(oldObject);
            this.node.rebind(name, oldCopy, newCopy, this.env);
         } else {
            super.rebind(name, oldObject, newObject);
         }

      } catch (RemoteException var6) {
         throw this.translateException(var6);
      }
   }

   public NameParser getNameParser(String ctxName) throws NamingException {
      try {
         return this.node != null ? this.node.getNameParser(ctxName, this.env) : super.getNameParser(ctxName);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public NamingEnumeration list(String ctxName) throws NamingException {
      try {
         return this.node != null ? this.node.list(ctxName, this.env) : super.list(ctxName);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public void unbind(String name) throws NamingException {
      try {
         if (this.node != null) {
            this.node.unbind(name, (Object)null, this.env);
         } else {
            super.unbind(name);
         }

      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public void unbind(String name, Object object) throws NamingException {
      int reset = 0;
      Object oldValue = null;
      if (name.startsWith("sharable:")) {
         if (this.env == null) {
            this.env = new Hashtable();
            reset = 1;
         } else {
            reset = 2;
         }

         name = name.substring(9);
         oldValue = this.env.put("weblogic.jndi.createUnderSharable", "true");
      } else if (name.startsWith("domain:")) {
         if (this.env == null) {
            this.env = new Hashtable();
            reset = 1;
         } else {
            reset = 3;
         }

         name = name.substring(7);
         oldValue = this.env.put("weblogic.jndi.partitionInformation", "DOMAIN");
      } else if (name.startsWith("partition:")) {
         if (this.env == null) {
            this.env = new Hashtable();
            reset = 1;
         } else {
            reset = 4;
         }

         int resolvedPos = name.indexOf(47);
         String partitionInfo = null;
         if (resolvedPos == -1) {
            partitionInfo = name.substring(10);
            name = "";
         } else {
            partitionInfo = name.substring(10, resolvedPos);
            name = name.substring(resolvedPos + 1);
         }

         oldValue = this.env.put("weblogic.jndi.partitionInformation", partitionInfo);
      }

      boolean var10 = false;

      try {
         var10 = true;
         if (this.node != null) {
            this.node.unbind(name, object, this.env);
            var10 = false;
         } else {
            super.unbind(name, object);
            var10 = false;
         }
      } catch (RemoteException var11) {
         throw this.translateException(var11);
      } finally {
         if (var10) {
            switch (reset) {
               case 1:
                  this.env = null;
                  break;
               case 2:
                  if (oldValue == null) {
                     this.env.remove("weblogic.jndi.createUnderSharable");
                  } else {
                     this.env.put("weblogic.jndi.createUnderSharable", oldValue);
                  }
                  break;
               case 3:
               case 4:
                  if (oldValue == null) {
                     this.env.remove("weblogic.jndi.partitionInformation");
                  } else {
                     this.env.put("weblogic.jndi.partitionInformation", oldValue);
                  }
            }

         }
      }

      switch (reset) {
         case 1:
            this.env = null;
            break;
         case 2:
            if (oldValue == null) {
               this.env.remove("weblogic.jndi.createUnderSharable");
            } else {
               this.env.put("weblogic.jndi.createUnderSharable", oldValue);
            }
            break;
         case 3:
         case 4:
            if (oldValue == null) {
               this.env.remove("weblogic.jndi.partitionInformation");
            } else {
               this.env.put("weblogic.jndi.partitionInformation", oldValue);
            }
      }

   }

   public String getNameInNamespace() throws NamingException {
      try {
         return this.node != null ? this.node.getNameInNamespace() : super.getNameInNamespace();
      } catch (RemoteException var2) {
         throw this.translateException(var2);
      }
   }

   public String getNameInNamespace(String relativeName) throws NamingException {
      try {
         return this.node != null ? this.node.getNameInNamespace(relativeName) : super.getNameInNamespace(relativeName);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public NamingEnumeration listBindings(String ctxName) throws NamingException {
      try {
         return this.node != null ? this.node.listBindings(ctxName, this.env) : super.listBindings(ctxName);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public void rename(String name1, String name2) throws NamingException {
      try {
         if (this.node != null) {
            this.node.rename(name1, name2, this.env);
         } else {
            super.rename(name1, name2);
         }

      } catch (RemoteException var4) {
         throw this.translateException(var4);
      }
   }

   protected Object lookup(Name name, String nameStr) throws NamingException {
      boolean pushedEnv = false;

      Object o;
      try {
         if (this.node != null) {
            pushedEnv = this.pushEnvOntoThread();
            o = this.node.lookup(nameStr, this.env);
            Object var5 = NamingManager.getObjectInstance(this.copyObject(o), name, this, this.env);
            return var5;
         }

         o = super.lookup(name, nameStr);
      } catch (RemoteException var12) {
         throw this.translateException(var12);
      } catch (NamingException var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (Exception var15) {
         throw new NamingException(var15.getMessage());
      } finally {
         if (pushedEnv) {
            RMIEnvironment.getEnvironment().threadEnvironmentPop();
         }

      }

      return o;
   }

   public void bind(String name, Object object) throws NamingException {
      try {
         if (this.node != null) {
            Object copy = this.copyObject(object);
            this.node.bind(name, copy, this.env);
         } else {
            super.bind(name, object);
         }

      } catch (RemoteException var4) {
         throw this.translateException(var4);
      }
   }

   public boolean equals(Object object) {
      if (object == null) {
         return false;
      } else if (object == this) {
         return true;
      } else if (object.getClass() != this.getClass()) {
         return false;
      } else {
         return this.node != null ? this.node.equals(((WLEventContextImpl)object).node) : false;
      }
   }

   public int hashCode() {
      return this.node == null ? 0 : this.node.hashCode();
   }

   public void addNamingListener(Name target, int scope, NamingListener l) throws NamingException {
      this.addNamingListener(target.toString(), scope, l);
   }

   public void addNamingListener(String target, int scope, NamingListener l) throws NamingException {
      if (this.node != null) {
         this.node.addNamingListener(target, scope, l, this.env);
         this.listeners.add(l);
      } else {
         throw new OperationNotSupportedException("Unsupported operationaddNamingListener");
      }
   }

   public void removeNamingListener(NamingListener l) throws NamingException {
      if (this.node != null) {
         this.node.removeNamingListener(l, this.env);
         this.listeners.remove(l);
      } else {
         throw new OperationNotSupportedException("Unsupported operationaddNamingListener");
      }
   }

   public boolean targetMustExist() {
      return false;
   }

   public void close() throws NamingException {
      super.close();
      synchronized(this.listeners) {
         for(Iterator i = this.listeners.iterator(); i.hasNext(); i.remove()) {
            NamingListener l = (NamingListener)i.next();
            if (this.node != null) {
               this.node.removeNamingListener(l, this.env);
            }
         }

      }
   }

   public String toString() {
      try {
         return "EventContext (" + this.getNameInNamespace() + ")";
      } catch (NamingException var2) {
         return "EventContext ( NAME UNKNOWN )";
      }
   }

   private Object copyObject(Object object) throws RemoteException {
      return this.copyNotRequired ? object : JNDIEnvironment.getJNDIEnvironment().copyObject(object);
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      int major = peerInfo.getMajor();
      int minor = peerInfo.getMinor();
      int servicePack = peerInfo.getServicePack();
      return (major != 7 || minor != 0 || servicePack < 3) && (major != 8 || minor != 1 || servicePack < 1) ? new WLContextImpl(this.env, this.node) : new WLEventContextImpl(this.env, this.node);
   }

   public WLEventContextImpl() {
      this.listeners = Collections.synchronizedList(new ArrayList());
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);

      try {
         NamingNode nd = this.getNode();
         if (ServerHelper.isLocal(nd)) {
            this.node = (NamingNode)ServerHelper.getServerReference(nd).getImplementation();
            this.env = this.getEnvironment();
            this.copyNotRequired = false;
            this.listeners = new ArrayList();
         }

      } catch (NamingException var3) {
         throw new AssertionError("Unexpected exception", var3);
      }
   }

   public Object readResolve() throws ObjectStreamException {
      if (this.node != null) {
         return this;
      } else {
         try {
            return new WLContextImpl(this.getEnvironment(), this.getNode());
         } catch (NamingException var2) {
            throw new AssertionError("Unexpected exception", var2);
         }
      }
   }

   public void bind(String name, Object obj, boolean allowCrossPartitionAccess) throws NamingException {
      if (this.node != null) {
         this.env.put("weblogic.jndi.crossPartitionAware", String.valueOf(allowCrossPartitionAccess));

         try {
            this.node.bind(name, this.copyObject(obj), this.env);
         } catch (RemoteException var8) {
            throw this.translateException(var8);
         } finally {
            this.env.remove("weblogic.jndi.crossPartitionAware");
         }
      } else {
         super.bind(name, obj, allowCrossPartitionAccess);
      }

   }
}
