package weblogic.work.concurrent.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import weblogic.invocation.PartitionTable;
import weblogic.work.concurrent.ConcurrentManagedObject;

public class ConcurrentUtils {
   private static final List defaultConcurrentInfo = new ArrayList();
   private static final String DELIMITER = "@@@";

   public static boolean isDefaultJSR236Name(String name) {
      Iterator var1 = defaultConcurrentInfo.iterator();

      DefaultConcurrentObjectInfo info;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         info = (DefaultConcurrentObjectInfo)var1.next();
      } while(!info.getName().equals(name));

      return true;
   }

   public static DefaultConcurrentObjectInfo getDefaultMESInfo() {
      Iterator var0 = defaultConcurrentInfo.iterator();

      DefaultConcurrentObjectInfo info;
      do {
         if (!var0.hasNext()) {
            throw new IllegalStateException();
         }

         info = (DefaultConcurrentObjectInfo)var0.next();
      } while(!info.getClassName().equals(ManagedExecutorService.class.getName()));

      return info;
   }

   public static DefaultConcurrentObjectInfo getDefaultMSESInfo() {
      Iterator var0 = defaultConcurrentInfo.iterator();

      DefaultConcurrentObjectInfo info;
      do {
         if (!var0.hasNext()) {
            throw new IllegalStateException();
         }

         info = (DefaultConcurrentObjectInfo)var0.next();
      } while(!info.getClassName().equals(ManagedScheduledExecutorService.class.getName()));

      return info;
   }

   public static DefaultConcurrentObjectInfo getDefaultMTFInfo() {
      Iterator var0 = defaultConcurrentInfo.iterator();

      DefaultConcurrentObjectInfo info;
      do {
         if (!var0.hasNext()) {
            throw new IllegalStateException();
         }

         info = (DefaultConcurrentObjectInfo)var0.next();
      } while(!info.getClassName().equals(ManagedThreadFactory.class.getName()));

      return info;
   }

   public static DefaultConcurrentObjectInfo getDefaultCSInfo() {
      Iterator var0 = defaultConcurrentInfo.iterator();

      DefaultConcurrentObjectInfo info;
      do {
         if (!var0.hasNext()) {
            throw new IllegalStateException();
         }

         info = (DefaultConcurrentObjectInfo)var0.next();
      } while(!info.getClassName().equals(ContextService.class.getName()));

      return info;
   }

   public static List getAllDefaultConcurrentObjectInfo() {
      return defaultConcurrentInfo;
   }

   public static String getDefaultJSR236ComponentJNDI(String clazz) {
      Iterator var1 = defaultConcurrentInfo.iterator();

      DefaultConcurrentObjectInfo info;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         info = (DefaultConcurrentObjectInfo)var1.next();
      } while(!info.getClassName().equals(clazz));

      return info.getCompJndi();
   }

   public static String getDefaultJSR236CMOName(String clazz) {
      Iterator var1 = defaultConcurrentInfo.iterator();

      DefaultConcurrentObjectInfo info;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         info = (DefaultConcurrentObjectInfo)var1.next();
      } while(!info.getClassName().equals(clazz));

      return info.getName();
   }

   public static Date getScheduledTime(long delta, TimeUnit unit) {
      long millsTime = unit.toMillis(delta < 0L ? 0L : delta);
      return new Date(System.currentTimeMillis() + millsTime);
   }

   public static String getGlobalName(ConcurrentManagedObject obj, String suffix) {
      StringBuilder sb = new StringBuilder();
      String partitionName = obj.getPartitionName();
      if (partitionName != null) {
         sb.append(partitionName);
         sb.append("#");
      }

      String applicationName = obj.getAppId();
      if (applicationName != null) {
         sb.append(applicationName);
         sb.append("#");
      }

      String moduleId = obj.getModuleId();
      if (moduleId != null) {
         sb.append(moduleId);
         sb.append("#");
      }

      sb.append(obj.getName());
      sb.append(suffix);
      return sb.toString();
   }

   public static boolean isSameString(String a, String b) {
      if (a == b) {
         return true;
      } else {
         return a == null ? false : a.equals(b);
      }
   }

   public static void serializeByClassloader(Object object, final ClassLoader pcl) throws Exception {
      if (object != null) {
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(out) {
            protected void annotateClass(Class cl) throws IOException {
               super.annotateClass(cl);
            }
         };
         objectOutputStream.writeObject(object);
         ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
         ObjectInputStream objectInputStream = new ObjectInputStream(in) {
            protected Class resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
               String name = desc.getName();
               return pcl.loadClass(name);
            }

            protected Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
               ClassLoader nonPublicLoader = null;
               boolean hasNonPublicInterface = false;
               Class[] classObjs = new Class[interfaces.length];

               for(int i = 0; i < interfaces.length; ++i) {
                  Class cl = Class.forName(interfaces[i], false, pcl);
                  if ((cl.getModifiers() & 1) == 0) {
                     if (hasNonPublicInterface) {
                        if (nonPublicLoader != cl.getClassLoader()) {
                           throw new IllegalAccessError("conflicting non-public interface class loaders");
                        }
                     } else {
                        nonPublicLoader = cl.getClassLoader();
                        hasNonPublicInterface = true;
                     }
                  }

                  classObjs[i] = cl;
               }

               try {
                  return Proxy.getProxyClass(hasNonPublicInterface ? nonPublicLoader : pcl, classObjs);
               } catch (IllegalArgumentException var7) {
                  throw new ClassNotFoundException((String)null, var7);
               }
            }
         };
         objectInputStream.readObject();
      }
   }

   public static String genKey(String appId, String moduleId, String compName) {
      StringBuilder builder = new StringBuilder();
      builder.append(appId == null ? "" : appId);
      builder.append("@@@");
      builder.append(moduleId == null ? "" : moduleId);
      builder.append("@@@");
      builder.append(compName == null ? "" : compName);
      return builder.toString();
   }

   public static boolean isDomainPartitionName(String partitionName) {
      if (partitionName == null) {
         return true;
      } else if (partitionName.length() == 0) {
         return true;
      } else {
         return PartitionTable.getInstance().getGlobalPartitionName().equals(partitionName);
      }
   }

   public static String getDomainPartitionName() {
      return PartitionTable.getInstance().getGlobalPartitionName();
   }

   static {
      Class[] concurrentClass = new Class[]{ManagedExecutorService.class, ManagedScheduledExecutorService.class, ManagedThreadFactory.class, ContextService.class};
      Class[] var1 = concurrentClass;
      int var2 = concurrentClass.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class cls = var1[var3];
         defaultConcurrentInfo.add(new DefaultConcurrentObjectInfo(cls.getName(), "Default" + cls.getSimpleName(), "Default" + cls.getSimpleName(), "weblogic.concurrent.Default" + cls.getSimpleName()));
      }

   }
}
