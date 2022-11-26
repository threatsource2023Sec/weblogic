package weblogic.rmi.internal;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.Map;
import java.util.WeakHashMap;
import weblogic.rmi.extensions.server.DescriptorHelper;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.ArrayMap;

public final class DescriptorManager {
   static final String RTD_SUFFIX = "RTD.xml";
   private static final boolean DEBUG = false;
   private static final boolean POSTJDK8 = !System.getProperty("java.version").startsWith("1.");
   private static final Map DESCRIPTOR_MAP = new WeakHashMap();

   private DescriptorManager() {
   }

   public static void removeDescriptor(Class c) {
      if (c != null) {
         ClassLoader cl = getClassLoaderForKey(c);
         Map m = (Map)DESCRIPTOR_MAP.get(c);
         if (m != null) {
            RuntimeDescriptor descriptor = (RuntimeDescriptor)m.remove(cl);
            if (m.isEmpty()) {
               DESCRIPTOR_MAP.remove(c);
            }
         }
      }

   }

   public static RuntimeDescriptor getDescriptor(Object o) throws RemoteException {
      return getDescriptor(o.getClass());
   }

   public static RuntimeDescriptor getDescriptor(Class c) throws RemoteException {
      RuntimeDescriptor desc = null;
      ClassLoader cl = getClassLoaderForKey(c);
      Map m;
      synchronized(DESCRIPTOR_MAP) {
         m = (Map)DESCRIPTOR_MAP.get(c);
         if (m != null) {
            desc = (RuntimeDescriptor)m.get(cl);
         }
      }

      if (desc == null) {
         desc = getBasicRuntimeDescriptor(c);
         synchronized(DESCRIPTOR_MAP) {
            m = (Map)DESCRIPTOR_MAP.get(c);
            if (m != null) {
               m.put(cl, desc);
            } else {
               Map m = new WeakHashMap();
               m.put(cl, desc);
               DESCRIPTOR_MAP.put(c, m);
            }
         }
      }

      return (RuntimeDescriptor)desc;
   }

   public static RuntimeDescriptor getDescriptorForRMIC(Class c) throws RemoteException {
      ArrayMap desc = null;

      try {
         desc = getDescriptorAsMap(c);
         return BasicRuntimeDescriptor.getRuntimeDescriptorForRMIC(desc, c);
      } catch (Exception var3) {
         throw new UnexpectedException("Failed to parse descriptor file", var3);
      }
   }

   public static BasicRuntimeDescriptor getBasicRuntimeDescriptor(Class c) throws RemoteException {
      RuntimeDescriptor rtd = createRuntimeDescriptor(c);
      if (rtd == null) {
         rtd = new BasicRuntimeDescriptor(c);
      }

      return (BasicRuntimeDescriptor)rtd;
   }

   public static RuntimeDescriptor createRuntimeDescriptor(Class c) throws RemoteException {
      try {
         ArrayMap desc = getDescriptorAsMap(c);
         return desc != null ? new BasicRuntimeDescriptor(desc, c) : null;
      } catch (Exception var2) {
         throw new UnexpectedException("Failed to parse descriptor file", var2);
      }
   }

   public static RuntimeDescriptor createRuntimeDescriptor(InputStream in, String remoteClassName, Class[] remoteInterfaces) throws RemoteException {
      try {
         ArrayMap desc = getDescriptorAsMapFromXml(in);
         return desc != null ? new BasicRuntimeDescriptor(desc, remoteClassName, remoteInterfaces) : null;
      } catch (Exception var4) {
         throw new UnexpectedException("Failed to parse descriptor file", var4);
      }
   }

   private static boolean isJavaOrJavax(String className) {
      return className.matches("^javax?\\..*");
   }

   static String getDescriptorFileName(String remoteClassName) {
      int index = remoteClassName.lastIndexOf(46);
      StringBuilder sb = new StringBuilder();
      if (index > 0) {
         if (POSTJDK8 && isJavaOrJavax(remoteClassName)) {
            sb.append("/");
            sb.append(remoteClassName.replaceAll("\\.", "/"));
         } else {
            sb.append(remoteClassName.substring(index + 1));
         }
      } else {
         sb.append(remoteClassName);
      }

      sb.append("RTD.xml");
      return sb.toString();
   }

   static ArrayMap getDescriptorAsMap(Class c) throws Exception {
      ArrayMap m = getDescriptorAsMapFromAnnotation(c);
      if (m != null) {
         return m;
      } else {
         String fileName = getDescriptorFileName(c.getName());
         return POSTJDK8 && isJavaOrJavax(c.getName()) ? getDescriptorAsMapFromXml(DescriptorManager.class.getResourceAsStream(fileName)) : getDescriptorAsMapFromXml(c.getResourceAsStream(fileName));
      }
   }

   static ArrayMap getDescriptorAsMapFromAnnotation(Class c) throws IOException {
      return RMIAnnotationProcessor.getDescriptorAsMap(c);
   }

   static ArrayMap getDescriptorAsMapFromXml(InputStream in) throws Exception {
      if (in != null) {
         ArrayMap var1;
         try {
            var1 = DescriptorHelper.getDescriptorAsMap(in);
         } finally {
            try {
               in.close();
            } catch (IOException var8) {
            }

         }

         return var1;
      } else {
         return null;
      }
   }

   private static ClassLoader getClassLoaderForKey(Class c) {
      ClassLoader cl = c.getClassLoader();
      if (cl == null) {
         cl = Thread.currentThread().getContextClassLoader();
      }

      while(cl instanceof GenericClassLoader) {
         GenericClassLoader gcl = (GenericClassLoader)cl;
         String appName = gcl.getAnnotation().getAnnotationString();
         if (appName != null && appName.length() != 0) {
            return cl;
         }

         cl = gcl.getParent();
      }

      return cl;
   }

   public static void main(String[] args) {
      if (args.length == 0) {
         System.out.println("usage: java weblogic.rmi.internal.DescriptorManager<remote class name>");
      } else {
         try {
            String className = args[0];
            Class c = Class.forName(className);
            getBasicRuntimeDescriptor(c);
            System.out.println("Descriptor Parse and Validation Succeeded");
         } catch (Exception var3) {
            System.err.println("Descriptor Parse and Validation Failed");
            var3.printStackTrace();
         }

      }
   }
}
