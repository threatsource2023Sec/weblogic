package weblogic.jndi.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jndi.Environment;
import weblogic.jndi.ObjectCopier;
import weblogic.jndi.internal.SSL.SSLProxy;
import weblogic.protocol.ServerIdentity;
import weblogic.rjvm.RJVMManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.security.internal.encryption.EncryptionServiceException;

public abstract class JNDIEnvironment {
   private ConcurrentHashMap contextRJVMHolder = new ConcurrentHashMap();
   private ConcurrentSkipListSet objectCopiers = new ConcurrentSkipListSet(new ObjectCopierComparator());
   private static ObjectCopier DEFAULT_COPIER = new DefaultObjectCopier();

   public void addCopier(ObjectCopier copier) {
      this.objectCopiers.add(copier);
   }

   Object copyObject(Object object) throws RemoteException {
      ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
      if (cic.getApplicationName() == null) {
         cic = mgr.createComponentInvocationContext(cic.getPartitionName(), "_JNDI_ENVIRONMENT_", cic.getApplicationVersion(), cic.getModuleName(), cic.getComponentName());
      }

      try {
         ManagedInvocationContext mic = mgr.setCurrentComponentInvocationContext(cic);
         Throwable var5 = null;

         Object var6;
         try {
            var6 = this.getCopierFor(object).copyObject(object);
         } catch (Throwable var16) {
            var5 = var16;
            throw var16;
         } finally {
            if (mic != null) {
               if (var5 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var15) {
                     var5.addSuppressed(var15);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var6;
      } catch (ClassNotFoundException | IOException var18) {
         throw new RemoteException(var18.getMessage(), var18);
      }
   }

   private ObjectCopier getCopierFor(Object objectToCopy) {
      Iterator var2 = this.objectCopiers.iterator();

      ObjectCopier copier;
      do {
         if (!var2.hasNext()) {
            return DEFAULT_COPIER;
         }

         copier = (ObjectCopier)var2.next();
      } while(!copier.mayCopy(objectToCopy));

      return copier;
   }

   private static JNDIEnvironment newInstance() {
      JNDIEnvironment singleton;
      try {
         singleton = (JNDIEnvironment)Class.forName("weblogic.jndi.WLSJNDIEnvironmentImpl").newInstance();
      } catch (Exception var4) {
         try {
            singleton = (JNDIEnvironment)Class.forName("weblogic.jndi.WLSClientJNDIEnvironmentImpl").newInstance();
         } catch (Exception var3) {
            throw new IllegalArgumentException(var3.toString());
         }
      }

      return singleton;
   }

   public static JNDIEnvironment getJNDIEnvironment() {
      return JNDIEnvironment.LazyHolder.INSTANCE;
   }

   static void setJNDIEnvironment(JNDIEnvironment helper) {
      JNDIEnvironment.LazyHolder.INSTANCE = helper;
   }

   protected JNDIEnvironment() {
      this.addCopier(new ProxyCopier());
      this.addCopier(new SerializableObjectCopier());
   }

   public abstract SSLProxy getSSLProxy();

   public abstract Object copyObjectViaSerialization(Object var1) throws IOException, ClassNotFoundException;

   public abstract void prepareKernel();

   public abstract void nullSSLClientCertificate();

   public abstract void loadTransportableFactories(List var1) throws ConfigurationException;

   public abstract Context getDelegateContext(ServerIdentity var1, Environment var2, String var3) throws RemoteException, NamingException;

   public abstract void prepareSubjectManager();

   public abstract void activateTransactionHelper();

   public abstract void deactivateTransactionHelper();

   public abstract void pushThreadEnvironment(Environment var1);

   public abstract Environment popThreadEnvironment();

   public abstract void pushSubject(AuthenticatedSubject var1, AuthenticatedSubject var2);

   public abstract void popSubject(AuthenticatedSubject var1);

   public abstract AuthenticatedSubject getCurrentSubject(AuthenticatedSubject var1);

   public abstract AuthenticatedSubject getASFromAU(AuthenticatedUser var1);

   public abstract ObjectOutput getReplacerObjectOutputStream(ObjectOutput var1) throws IOException;

   public abstract ObjectInput getReplacerObjectInputStream(ObjectInput var1) throws IOException;

   public void storeConnectionTimeout(Context ctx, ServerIdentity rjvm, long connectionTimeout) {
      if (rjvm != null) {
         this.contextRJVMHolder.put(ctx, rjvm);
         RJVMManager.getRJVMManager().storeConnectionTimeout(rjvm, connectionTimeout);
      }

   }

   public void removeConnectionTimeout(Context ctx) {
      ServerIdentity id = (ServerIdentity)this.contextRJVMHolder.remove(ctx);
      if (id != null) {
         RJVMManager.getRJVMManager().removeConnectionTimeout(id);
      }

   }

   public abstract byte[] encryptionHelperDecrypt(byte[] var1, AuthenticatedSubject var2) throws EncryptionServiceException;

   public abstract byte[] encryptionHelperClear(byte[] var1);

   private static class DefaultObjectCopier implements ObjectCopier {
      private DefaultObjectCopier() {
      }

      public boolean mayCopy(Object objectToCopy) {
         return true;
      }

      public Object copyObject(Object objectToCopy) throws IOException, ClassNotFoundException {
         return objectToCopy;
      }

      public int getPriority() {
         return 0;
      }

      // $FF: synthetic method
      DefaultObjectCopier(Object x0) {
         this();
      }
   }

   private class SerializableObjectCopier implements ObjectCopier {
      private SerializableObjectCopier() {
      }

      public boolean mayCopy(Object objectToCopy) {
         return objectToCopy instanceof Serializable;
      }

      public Object copyObject(Object objectToCopy) throws IOException, ClassNotFoundException {
         return JNDIEnvironment.this.copyObjectViaSerialization(objectToCopy);
      }

      public int getPriority() {
         return 40;
      }

      // $FF: synthetic method
      SerializableObjectCopier(Object x1) {
         this();
      }
   }

   private class ProxyCopier implements ObjectCopier {
      private ProxyCopier() {
      }

      public boolean mayCopy(Object objectToCopy) {
         return objectToCopy instanceof Proxy;
      }

      public Object copyObject(Object objectToCopy) throws IOException, ClassNotFoundException {
         return JNDIEnvironment.this.copyObjectViaSerialization(objectToCopy);
      }

      public int getPriority() {
         return 100;
      }

      // $FF: synthetic method
      ProxyCopier(Object x1) {
         this();
      }
   }

   private static class ObjectCopierComparator implements Comparator {
      private ObjectCopierComparator() {
      }

      public int compare(ObjectCopier o1, ObjectCopier o2) {
         return Integer.compare(o2.getPriority(), o1.getPriority());
      }

      // $FF: synthetic method
      ObjectCopierComparator(Object x0) {
         this();
      }
   }

   private static class LazyHolder {
      private static JNDIEnvironment INSTANCE = JNDIEnvironment.newInstance();
   }
}
