package weblogic.rmi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.utils.Utilities;
import weblogic.rmi.utils.WLRMIClassLoaderDelegate;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;

public class ClientRuntimeDescriptor implements Externalizable {
   private static final boolean DEBUG = false;
   private static Map canonical = new WeakHashMap(103);
   static final long serialVersionUID = 8291399479334920769L;
   private String[] interfaceNames;
   private String applicationName;
   private ClientMethodDescriptor defaultClientMD;
   private static final boolean isApplet = KernelStatus.isApplet();
   private static boolean ignoreInterAppClassLoaderCheck = false;
   private static final DebugLogger debugStubGeneration = DebugLogger.getDebugLogger("DebugStubGeneration");
   private final transient Map cache;
   private Map descriptorBySignature;
   private int hash;
   private transient String stubName;
   private transient String codebase;

   private static boolean equals(Object o1, Object o2) {
      return o1 == null ? o2 == null : o1.equals(o2);
   }

   public ClientRuntimeDescriptor() {
      this.cache = Collections.synchronizedMap(new WeakHashMap());
   }

   public ClientRuntimeDescriptor(String[] interfaceNames, String applicationName, Map descriptorBySignature, ClientMethodDescriptor desc, String stubName) {
      this(interfaceNames, applicationName, descriptorBySignature, desc, stubName, (String)null);
   }

   public ClientRuntimeDescriptor(String[] interfaceNames, String applicationName, Map descriptorBySignature, ClientMethodDescriptor desc, String stubName, String codebase) {
      this.cache = Collections.synchronizedMap(new WeakHashMap());
      this.interfaceNames = interfaceNames;
      this.applicationName = applicationName;
      this.descriptorBySignature = descriptorBySignature;
      this.stubName = stubName;
      this.defaultClientMD = desc;
      this.codebase = codebase;
      this.computeHashCode();
   }

   public ClientRuntimeDescriptor intern() {
      synchronized(canonical) {
         WeakReference r = (WeakReference)canonical.get(this);
         if (r != null) {
            ClientRuntimeDescriptor cd = (ClientRuntimeDescriptor)r.get();
            if (cd != null) {
               return cd;
            }
         }

         if (this.applicationName != null) {
            this.applicationName = this.applicationName.intern();
         }

         canonical.put(this, new WeakReference(this));
         return this;
      }
   }

   public MethodDescriptor describe(Method m) {
      return null;
   }

   public String getApplicationName() {
      return this.applicationName;
   }

   Map getDescriptorBySignature() {
      return this.descriptorBySignature;
   }

   void setDescriptorBySignature(Map dbs) {
      this.descriptorBySignature = dbs;
      this.computeHashCode();
   }

   ClientMethodDescriptor getDefaultClientMethodDescriptor() {
      return this.defaultClientMD;
   }

   void setDefaultClientMethodDescriptor(ClientMethodDescriptor cmd) {
      this.defaultClientMD = cmd;
   }

   public int hashCode() {
      return this.hash;
   }

   private void computeHashCode() {
      int h = this.stubName.hashCode();
      h = this.applicationName != null ? h ^ this.applicationName.hashCode() : h;
      h = this.descriptorBySignature != null ? h ^ this.descriptorBySignature.size() : h;
      if (this.interfaceNames != null) {
         String[] var2 = this.interfaceNames;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String interfaceName = var2[var4];
            h ^= interfaceName.hashCode();
         }
      }

      this.hash = h;
   }

   Class[] getInterfaces() {
      ClassLoader cl = this.findLoader();
      WeakReference ref = (WeakReference)this.cache.get(cl);
      Object value = null;
      if (ref != null && (value = ref.get()) != null) {
         return (Class[])((Class[])value);
      } else {
         ClassLoader loader = this.findLoader();
         Class[] interfaces = this.computeInterfaces(loader);
         this.cache.put(loader, new WeakReference(interfaces));
         return interfaces;
      }
   }

   public ClassLoader getClassLoader() {
      return this.findLoader();
   }

   String[] getInterfaceNames() {
      return this.interfaceNames;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof ClientRuntimeDescriptor)) {
         return false;
      } else {
         ClientRuntimeDescriptor other = (ClientRuntimeDescriptor)o;
         return equals(this.stubName, other.stubName) && equals(this.applicationName, other.applicationName) && Arrays.equals(this.interfaceNames, other.interfaceNames) && Objects.equals(this.descriptorBySignature, other.descriptorBySignature);
      }
   }

   public void setStubName(String stubName) {
      this.stubName = stubName;
      this.computeHashCode();
   }

   void setCodebase(String codebase) {
      this.codebase = codebase;
   }

   String toString(String prefix) {
      return this.applicationName + "/" + this.stubName;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.interfaceNames);
      out.writeObject(this.applicationName);
      out.writeObject(this.descriptorBySignature);
      out.writeInt(this.hash);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.interfaceNames = (String[])((String[])in.readObject());
      this.applicationName = (String)in.readObject();
      this.descriptorBySignature = (Map)in.readObject();
      this.hash = in.readInt();
   }

   public String toString() {
      return this.toString("");
   }

   private Class[] computeInterfaces(ClassLoader loader) {
      Class[] interfaces = new Class[this.interfaceNames.length];
      int j = 0;

      for(int i = 0; i < this.interfaceNames.length; ++i) {
         this.interfaceNames[i] = this.interfaceNames[i].intern();
         if (debugStubGeneration.isDebugEnabled()) {
            debugStubGeneration.debug("Loading interface " + this.interfaceNames[i]);
         }

         try {
            Class ci = Utilities.loadClass(this.interfaceNames[i], this.applicationName, this.codebase, loader);
            interfaces[j++] = ci;
         } catch (ClassNotFoundException var8) {
            if (debugStubGeneration.isDebugEnabled()) {
               debugStubGeneration.debug("Exception while computing interfaces.\n" + var8);
               StringBuilder sb = new StringBuilder();
               sb.append("Unable to load class : ").append(this.interfaceNames[i]);
               sb.append("\n\tApplication Name: ").append(this.applicationName);
               sb.append("\n\tStubName:").append(this.stubName);
               if (this.codebase != null) {
                  sb.append("\n\tcodebase: ").append(this.codebase);
               }

               sb.append("\n\tContext CL: ").append(Thread.currentThread().getContextClassLoader());
               sb.append("\n\tLoader: ").append(loader);
               debugStubGeneration.debug(sb.toString());
            }
         }
      }

      if (j < interfaces.length) {
         Class[] old = interfaces;
         interfaces = new Class[j];
         System.arraycopy(old, 0, interfaces, 0, j);
      }

      return interfaces;
   }

   private ClassLoader findLoader() {
      ClassLoader ccl = RMIEnvironment.getEnvironment().getDescriptorClassLoader();
      if (isApplet) {
         return ccl;
      } else {
         if (this.applicationName != null && KernelStatus.isServer() && this.isInterAppClassLoaderNeeded()) {
            ClassLoader acl = WLRMIClassLoaderDelegate.getInstance().findInterAppClassLoader(this.applicationName, ccl);
            if (acl != null) {
               return acl;
            }
         }

         return AugmentableClassLoaderManager.getAugmentableClassLoader(ccl);
      }
   }

   private boolean isInterAppClassLoaderNeeded() {
      if (!ignoreInterAppClassLoaderCheck && this.descriptorBySignature != null) {
         ClassLoader ccl = RMIEnvironment.getEnvironment().getDescriptorClassLoader();
         ClassLoader acl = WLRMIClassLoaderDelegate.getInstance().findClassLoader(this.applicationName);
         if (acl != null && acl != ccl) {
            Class[] interfaces = this.computeInterfaces(ccl);
            if (interfaces.length != this.interfaceNames.length) {
               return true;
            } else {
               Map signatureMap = Utilities.getRemoteMethodsAndSignatures(interfaces);
               Iterator itr = this.descriptorBySignature.keySet().iterator();

               String signature;
               do {
                  if (!itr.hasNext()) {
                     return true;
                  }

                  signature = (String)itr.next();
               } while(signatureMap.containsKey(signature));

               return false;
            }
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   public ClientMethodDescriptor getEffectiveDescriptor(String methodSignature) {
      return this.descriptorBySignature == null ? this.defaultClientMD : (ClientMethodDescriptor)this.descriptorBySignature.get(methodSignature);
   }

   static {
      if (!KernelStatus.isApplet()) {
         ignoreInterAppClassLoaderCheck = Boolean.getBoolean("weblogic.ignoreInterAppClassLoaderCheck");
      }

   }
}
