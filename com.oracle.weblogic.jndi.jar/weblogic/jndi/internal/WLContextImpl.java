package weblogic.jndi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Hashtable;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.spi.NamingManager;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.jndi.Alias;
import weblogic.jndi.Environment;
import weblogic.jndi.JNDILogger;
import weblogic.jndi.ThreadLocalMap;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ProtocolStack;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.transaction.TransactionHelper;

public class WLContextImpl implements WLInternalContext, RemoteWrapper, Externalizable {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   static final long serialVersionUID = -7759756621062870766L;
   private NamingNode node;
   private Hashtable env;
   private transient Thread loginThread = null;
   private transient boolean disableWarning = false;
   private transient boolean enableLogout;
   protected Environment environment = null;

   public WLContextImpl(Hashtable env, NamingNode node) {
      this.node = node;
      this.env = env;
      if (this.env != null) {
         this.env.remove("jmx.remote.protocol.provider.class.loader");
      }

      if (env != null && (env.containsKey("weblogic.rmi.clientTimeout") || env.containsKey("weblogic.jndi.responseReadTimeout"))) {
         this.environment = new Environment(env);
      }

      if (!this.isLocalURL()) {
         this.loginThread = Thread.currentThread();
         if (env != null) {
            String propertyVal = (String)env.get("weblogic.jndi.disableLoggingOfWarningMsg");
            if (propertyVal != null) {
               this.disableWarning = Boolean.parseBoolean(propertyVal);
            }
         }
      }

   }

   public Remote getRemoteDelegate() {
      return this.node;
   }

   public void enableLogoutOnClose() {
      this.loginThread = Thread.currentThread();
      this.enableLogout = true;
   }

   /** @deprecated */
   @Deprecated
   public void disableThreadWarningOnClose() {
      this.disableWarning = true;
   }

   public void close() throws NamingException {
      if (this.loginThread != null) {
         if (this.loginThread != Thread.currentThread()) {
            if (!this.disableWarning) {
               JNDILogger.logDiffThread();
            }
         } else {
            if (!this.isLocalURL()) {
               ProtocolStack.pop();
            }

            if (this.enableLogout) {
               SecurityServiceManager.popSubject(kernelId);
               JNDIEnvironment.getJNDIEnvironment().nullSSLClientCertificate();
            }
         }

         this.loginThread = null;
      }

      JNDIEnvironment.getJNDIEnvironment().removeConnectionTimeout(this);
      TransactionHelper.popTransactionHelper();
   }

   private final boolean isLocalURL() {
      return this.env != null && this.env.get("java.naming.provider.url") == null && KernelStatus.isServer();
   }

   public Context createSubcontext(Name name) throws NamingException {
      return this.createSubcontext(name.toString());
   }

   public Context createSubcontext(String name) throws NamingException {
      try {
         return this.node.createSubcontext(name, this.env);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public Object lookupLink(Name name) throws NamingException {
      return this.lookupLink(name.toString());
   }

   public Object lookupLink(String name) throws NamingException {
      try {
         return this.node.lookupLink(name, this.env);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public void destroySubcontext(Name name) throws NamingException {
      this.destroySubcontext(name.toString());
   }

   public void destroySubcontext(String name) throws NamingException {
      try {
         this.node.destroySubcontext(name, this.env);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public void rebind(Name name, Object object) throws NamingException {
      try {
         this.node.rebind(name, object, this.env);
      } catch (RemoteException var4) {
         throw this.translateException(var4);
      }
   }

   public void rebind(String name, Object object) throws NamingException {
      try {
         this.node.rebind(name, object, this.env);
      } catch (RemoteException var4) {
         throw this.translateException(var4);
      }
   }

   public void rebind(String name, Object oldService, Object newService) throws NamingException {
      try {
         this.node.rebind(name, oldService, newService, this.env);
      } catch (RemoteException var5) {
         throw this.translateException(var5);
      }
   }

   public NameParser getNameParser(Name ctxName) throws NamingException {
      return this.getNameParser(ctxName.toString());
   }

   public NameParser getNameParser(String ctxName) throws NamingException {
      try {
         return this.node.getNameParser(ctxName, this.env);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public NamingEnumeration list(Name ctxName) throws NamingException {
      return this.list(ctxName.toString());
   }

   public NamingEnumeration list(String ctxName) throws NamingException {
      try {
         return this.node.list(ctxName, this.env);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public Object removeFromEnvironment(String property) throws NamingException {
      Object value = null;
      if (this.env != null) {
         value = this.env.remove(property);
      }

      if (this.env.size() == 0) {
         this.env = null;
      }

      return value;
   }

   public void unbind(Name name) throws NamingException {
      this.unbind(name.toString());
   }

   public void unbind(String name) throws NamingException {
      try {
         this.node.unbind(name, (Object)null, this.env);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public void unbind(Name name, Object object) throws NamingException {
      this.unbind(name.toString(), object);
   }

   public void unbind(String name, Object object) throws NamingException {
      try {
         this.node.unbind(name, object, this.env);
      } catch (RemoteException var4) {
         throw this.translateException(var4);
      }
   }

   public Name composeName(Name suffix, Name prefix) throws NamingException {
      return new CompositeName(this.composeName(suffix.toString(), prefix.toString()));
   }

   public String composeName(String suffix, String prefix) throws NamingException {
      if (prefix.length() == 0) {
         return prefix;
      } else {
         return suffix.length() == 0 ? prefix : prefix + "." + suffix;
      }
   }

   public String getNameInNamespace() throws NamingException {
      try {
         return this.node.getNameInNamespace();
      } catch (RemoteException var2) {
         throw this.translateException(var2);
      }
   }

   public String getNameInNamespace(String relativeName) throws NamingException {
      try {
         return this.node.getNameInNamespace(relativeName);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public Hashtable getEnvironment() throws NamingException {
      return this.env;
   }

   public NamingEnumeration listBindings(Name ctxName) throws NamingException {
      return this.listBindings(ctxName.toString());
   }

   public NamingEnumeration listBindings(String ctxName) throws NamingException {
      try {
         return this.node.listBindings(ctxName, this.env);
      } catch (RemoteException var3) {
         throw this.translateException(var3);
      }
   }

   public void rename(Name name1, Name name2) throws NamingException {
      this.rename(name1.toString(), name2.toString());
   }

   public void rename(String name1, String name2) throws NamingException {
      try {
         this.node.rename(name1, name2, this.env);
      } catch (RemoteException var4) {
         throw this.translateException(var4);
      }
   }

   public Object lookup(Name name) throws NamingException {
      Object ret = this.lookup(name, name.toString());
      if (ret instanceof Alias) {
         String nameStr = ((Alias)ret).getRealName();
         ret = this.lookup(nameStr);
      }

      return ret;
   }

   public Object lookup(String name) throws NamingException {
      Object ret = this.lookup(WLNameParser.defaultParse(name), name);
      if (ret instanceof Alias) {
         name = ((Alias)ret).getRealName();
         ret = this.lookup(name);
      }

      return ret;
   }

   protected Object lookup(Name name, String nameStr) throws NamingException {
      boolean pushedEnv = false;

      Object var5;
      try {
         pushedEnv = this.pushEnvOntoThread();
         Object o = this.node.lookup(nameStr, this.env);
         ServerHelper.ensureJNDIName(o, nameStr);
         var5 = NamingManager.getObjectInstance(o, name, this, this.env);
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
            ThreadLocalMap.pop();
         }

      }

      return var5;
   }

   protected boolean pushEnvOntoThread() {
      boolean pushedEnv = false;
      if (this.env != null && (this.env.containsKey("weblogic.jndi.responseReadTimeout") || this.env.containsKey("weblogic.rmi.clientTimeout") || this.env.containsKey("java.naming.provider.url"))) {
         ThreadLocalMap.push(this.env);
         pushedEnv = true;
      }

      return pushedEnv;
   }

   public Object lookup(String nameStr, AuthenticatedSubject lookupID) throws NamingException, RemoteException {
      try {
         Object o = this.node.authenticatedLookup(nameStr, this.env, lookupID);
         Name name = WLNameParser.defaultParse(nameStr);
         return NamingManager.getObjectInstance(o, name, this, this.env);
      } catch (RemoteException var5) {
         throw this.translateException(var5);
      } catch (NamingException var6) {
         throw var6;
      } catch (RuntimeException var7) {
         throw var7;
      } catch (Exception var8) {
         throw new NamingException(var8.getMessage());
      }
   }

   public void bind(Name name, Object object) throws NamingException {
      this.bind(name.toString(), object);
   }

   public void bind(String name, Object object) throws NamingException {
      try {
         this.node.bind(name, object, this.env);
      } catch (RemoteException var4) {
         throw this.translateException(var4);
      }
   }

   public Object addToEnvironment(String property, Object value) throws NamingException {
      if (this.env == null) {
         this.env = new Hashtable(5);
      }

      return this.env.put(property, value);
   }

   public NamingNode getNode() {
      return this.node;
   }

   protected NamingException translateException(RemoteException re) {
      return ExceptionTranslator.toNamingException((Throwable)re);
   }

   public boolean equals(Object object) {
      if (object == null) {
         return false;
      } else if (object == this) {
         return true;
      } else if (object.getClass() != this.getClass()) {
         return false;
      } else {
         return this.node != null ? this.node.equals(((WLContextImpl)object).getNode()) : false;
      }
   }

   public int hashCode() {
      return this.node == null ? 0 : this.node.hashCode();
   }

   public String toString() {
      try {
         return "WLContext (" + this.getNameInNamespace() + ")";
      } catch (NamingException var2) {
         return "WLContext ( NAME UNKNOWN )";
      }
   }

   public WLContextImpl() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      if (out instanceof WLObjectOutput) {
         WLObjectOutput wlOut = (WLObjectOutput)out;
         wlOut.writeObjectWL(this.node);
         wlOut.writeObjectWL(this.env);
      } else {
         ObjectOutput objOutput = JNDIEnvironment.getJNDIEnvironment().getReplacerObjectOutputStream(out);
         objOutput.writeObject(this.node);
         objOutput.writeObject(this.env);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      if (in instanceof WLObjectInput) {
         WLObjectInput wlIn = (WLObjectInput)in;
         this.node = (NamingNode)wlIn.readObjectWL();
         this.env = (Hashtable)wlIn.readObjectWL();
      } else {
         ObjectInput objInput = JNDIEnvironment.getJNDIEnvironment().getReplacerObjectInputStream(in);
         this.node = (NamingNode)objInput.readObject();
         this.env = (Hashtable)objInput.readObject();
      }

   }

   public void bind(String name, Object obj, boolean allowCrossPartitionAccess) throws NamingException {
      this.env.put("weblogic.jndi.crossPartitionAware", String.valueOf(allowCrossPartitionAccess));

      try {
         this.node.bind(name, obj, this.env);
      } catch (RemoteException var8) {
         throw this.translateException(var8);
      } finally {
         this.env.remove("weblogic.jndi.crossPartitionAware");
      }

   }

   public void bind(Name name, Object obj, boolean allowCrossPartitionAccess) throws NamingException {
      this.bind(name.toString(), obj, allowCrossPartitionAccess);
   }

   public boolean isBindable(String name, Object boundObject) throws NamingException {
      boolean pushedEnv = false;

      boolean var4;
      try {
         pushedEnv = this.pushEnvOntoThread();
         var4 = this.node.isBindable(name, boundObject, this.env);
      } catch (RemoteException var11) {
         throw this.translateException(var11);
      } catch (NamingException var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (Exception var14) {
         throw new NamingException(var14.getMessage());
      } finally {
         if (pushedEnv) {
            ThreadLocalMap.pop();
         }

      }

      return var4;
   }

   public boolean isBindable(String name, boolean isAggregatable) throws NamingException {
      boolean pushedEnv = false;

      boolean var4;
      try {
         pushedEnv = this.pushEnvOntoThread();
         var4 = this.node.isBindable(name, isAggregatable, this.env);
      } catch (RemoteException var11) {
         throw this.translateException(var11);
      } catch (NamingException var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (Exception var14) {
         throw new NamingException(var14.getMessage());
      } finally {
         if (pushedEnv) {
            ThreadLocalMap.pop();
         }

      }

      return var4;
   }
}
