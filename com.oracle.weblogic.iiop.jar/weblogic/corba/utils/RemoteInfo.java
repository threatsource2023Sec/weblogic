package weblogic.corba.utils;

import java.lang.ref.WeakReference;
import java.rmi.RemoteException;
import java.util.Map;
import weblogic.rmi.internal.DescriptorManager;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public final class RemoteInfo {
   private RepositoryId repId;
   private Class theClass;
   private boolean idlInterface = false;
   private RuntimeDescriptor descriptor = null;
   private static Map classInfoMap = new ConcurrentWeakHashMap();
   private static Map repositoryIdMap = new ConcurrentHashMap();
   private static ClassLoadingUtils classLoadingUtils = new ClassLoadingUtils() {
      public Class getClassFromID(RepositoryId repId) {
         return CorbaUtils.getClassFromID(repId);
      }

      public Class getClassFromID(RepositoryId repId, String codebase) {
         return CorbaUtils.getClassFromID(repId, codebase);
      }
   };

   public RemoteInfo(RepositoryId repId) {
      this.repId = repId;
      this.theClass = classLoadingUtils.getClassFromID(repId);
      this.init();
   }

   public RemoteInfo(Class theClass) {
      this.theClass = theClass;
      this.repId = new RepositoryId(theClass);
      this.init();
   }

   private RemoteInfo(RepositoryId repId, Class c) {
      this.repId = repId;
      this.theClass = c;
      this.init();
   }

   private void init() {
      this.idlInterface = CorbaUtils.isIDLInterface(this.theClass);
      this.repId.setClassLoader(this.theClass.getClassLoader());

      try {
         this.descriptor = DescriptorManager.createRuntimeDescriptor(this.theClass);
         if (this.descriptor == null) {
            Class c = classLoadingUtils.getClassFromID(this.repId);
            if (c != null && c != this.theClass) {
               this.descriptor = DescriptorManager.createRuntimeDescriptor(c);
            }
         }

      } catch (RemoteException var2) {
         throw new AssertionError(var2);
      }
   }

   public Class getTheClass() {
      return this.theClass;
   }

   public RuntimeDescriptor getDescriptor() {
      return this.descriptor;
   }

   public RepositoryId getRepositoryId() {
      return this.repId;
   }

   public String getClassName() {
      return this.theClass.getName();
   }

   public boolean isIDLInterface() {
      return this.idlInterface;
   }

   public static RepositoryId getRepositoryId(String repid) {
      RemoteInfo info = findRemoteInfo(new RepositoryId(repid));
      return info.getRepositoryId();
   }

   public boolean equals(Object other) {
      return other instanceof RemoteInfo && this.equals((RemoteInfo)other);
   }

   private boolean equals(RemoteInfo other) {
      return this == other || this.theClass == other.theClass && this.repId.equals((Object)other.repId);
   }

   public int hashCode() {
      return this.repId == null ? 0 : this.repId.hashCode();
   }

   public String toString() {
      return this.repId.toString();
   }

   public static RemoteInfo findRemoteInfo(RepositoryId repid) {
      return findRemoteInfo(repid, (String)null);
   }

   public static RemoteInfo findRemoteInfo(RepositoryId repId, String codebase) {
      RemoteInfoReference cir = (RemoteInfoReference)repositoryIdMap.get(repId);
      if (cir == null && repId.getAnnotation() == null) {
         String annotation = CorbaUtils.getAnnotation((ClassLoader)null);
         if (annotation != null) {
            cir = (RemoteInfoReference)repositoryIdMap.get(new RepositoryId(repId, annotation));
            if (cir != null) {
               repId.setAnnotation(annotation);
            }
         }
      }

      RemoteInfo remoteInfo = cir != null ? (RemoteInfo)cir.get() : null;
      if (remoteInfo == null) {
         Class c = classLoadingUtils.getClassFromID(repId, codebase);
         if (c == null) {
            return null;
         }

         remoteInfo = new RemoteInfo(repId, c);
         repositoryIdMap.put(repId, new RemoteInfoReference(remoteInfo));
         classInfoMap.put(c, remoteInfo);
      }

      return remoteInfo;
   }

   public static RemoteInfo findRemoteInfo(RepositoryId repId, Class c) {
      RemoteInfoReference cir = (RemoteInfoReference)repositoryIdMap.get(repId);
      RemoteInfo remoteInfo = cir != null ? (RemoteInfo)cir.get() : null;
      if (remoteInfo == null) {
         remoteInfo = createRemoteInfo(repId, c);
      }

      return remoteInfo;
   }

   private static RemoteInfo createRemoteInfo(RepositoryId repId, Class c) {
      assert repId != null && c != null;

      RemoteInfo rInfo = new RemoteInfo(repId, c);
      Class ifclass = classLoadingUtils.getClassFromID(repId);
      if (ifclass == null || ifclass == c) {
         repositoryIdMap.put(repId, new RemoteInfoReference(rInfo));
      }

      return rInfo;
   }

   public static RemoteInfo findRemoteInfo(Class theClass) {
      RemoteInfo cinfo = (RemoteInfo)classInfoMap.get(theClass);
      if (cinfo == null) {
         cinfo = new RemoteInfo(theClass);
         Class ifclass = classLoadingUtils.getClassFromID(cinfo.repId);
         if (ifclass == null || ifclass == theClass) {
            repositoryIdMap.put(cinfo.repId, new RemoteInfoReference(cinfo));
         }

         classInfoMap.put(theClass, cinfo);
      }

      return cinfo;
   }

   private static class RemoteInfoReference extends WeakReference {
      private int hash;

      RemoteInfoReference(RemoteInfo key) {
         super(key);
         this.hash = key.hashCode();
      }

      public boolean equals(Object o) {
         if (!(o instanceof RemoteInfoReference)) {
            return false;
         } else {
            boolean var10000;
            label31: {
               RemoteInfoReference e = (RemoteInfoReference)o;
               Object k1 = this.get();
               Object k2 = e.get();
               if (this.hash == e.hash) {
                  if (k1 == null) {
                     if (k2 == null) {
                        break label31;
                     }
                  } else if (k1.equals(k2)) {
                     break label31;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      }

      public int hashCode() {
         return this.hash;
      }
   }
}
