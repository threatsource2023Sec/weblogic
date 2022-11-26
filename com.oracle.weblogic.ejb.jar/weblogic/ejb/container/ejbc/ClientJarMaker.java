package weblogic.ejb.container.ejbc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;

public final class ClientJarMaker {
   private static final DebugLogger debugLogger;
   private final ClassLoader jarLoader;
   private Set clientJarFiles = new HashSet();
   private boolean containsRemoteEJBs = false;

   public ClientJarMaker(ClassLoader jl) {
      assert jl != null;

      this.jarLoader = jl;
   }

   public String[] createClientJar(Collection beanInfos, Collection extCls) {
      Iterator var3 = extCls.iterator();

      while(var3.hasNext()) {
         Class c = (Class)var3.next();
         this.addClass(c);
      }

      this.createClientJar(beanInfos);
      if (this.clientJarFiles.isEmpty()) {
         if (this.containsRemoteEJBs) {
            EJBLogger.logUnableToCreateClientJarDueToClasspathIssues();
         } else {
            EJBLogger.logSkippingClientJarCreationSinceNoRemoteEJBsFound();
         }

         return new String[0];
      } else {
         String[] cjFileNames = new String[this.clientJarFiles.size()];
         cjFileNames = (String[])this.clientJarFiles.toArray(cjFileNames);
         return cjFileNames;
      }
   }

   public String[] createClientJar(Collection beanInfos) {
      Iterator var2 = beanInfos.iterator();

      while(true) {
         BeanInfo bi;
         ClientDrivenBeanInfo cbi;
         do {
            do {
               if (!var2.hasNext()) {
                  return (String[])this.clientJarFiles.toArray(new String[this.clientJarFiles.size()]);
               }

               bi = (BeanInfo)var2.next();
            } while(!(bi instanceof ClientDrivenBeanInfo));

            cbi = (ClientDrivenBeanInfo)bi;
         } while(!cbi.hasRemoteClientView());

         this.containsRemoteEJBs = true;
         if (cbi.hasDeclaredRemoteHome()) {
            if (!this.addClass(cbi.getHomeInterfaceClass())) {
               EJBLogger.logUnableToAddToClientJarDueToClasspath("home", cbi.getHomeInterfaceClass().getName());
            }

            if (!this.addClass(cbi.getRemoteInterfaceClass())) {
               EJBLogger.logUnableToAddToClientJarDueToClasspath("remote", cbi.getRemoteInterfaceClass().getName());
            }
         }

         if (bi instanceof SessionBeanInfo) {
            SessionBeanInfo sbi = (SessionBeanInfo)bi;
            Iterator var6 = sbi.getBusinessRemotes().iterator();

            while(var6.hasNext()) {
               Class iface = (Class)var6.next();
               if (!this.addClass(iface)) {
                  EJBLogger.logUnableToAddToClientJarDueToClasspath("business-remote", iface.getName());
               }
            }
         }

         if (bi instanceof EntityBeanInfo) {
            EntityBeanInfo ebi = (EntityBeanInfo)bi;
            if (!ebi.isUnknownPrimaryKey()) {
               this.addClass(ebi.getPrimaryKeyClass());
            }
         }
      }
   }

   private void addClass(Class[] classes) {
      Class[] var2 = classes;
      int var3 = classes.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         this.addClass(c);
      }

   }

   private void addClass(Field[] fields) {
      Field[] var2 = fields;
      int var3 = fields.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field f = var2[var4];
         this.addClass(f.getType());
      }

   }

   private void addClass(Constructor[] constructors) {
      Constructor[] var2 = constructors;
      int var3 = constructors.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Constructor c = var2[var4];
         this.addClass(c.getParameterTypes());
         this.addClass(c.getExceptionTypes());
      }

   }

   private void addClass(Method[] methods) {
      Method[] var2 = methods;
      int var3 = methods.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Method m = var2[var4];
         this.addClass(m.getReturnType());
         this.addClass(m.getExceptionTypes());
         this.addClass(m.getParameterTypes());
      }

   }

   private boolean addClass(Class c) {
      if (c == null) {
         return false;
      } else if (c.isArray()) {
         return this.addClass(c.getComponentType());
      } else if (c.getClassLoader() != this.jarLoader) {
         if (debugLogger.isDebugEnabled()) {
            debug("** Rejecting class not from jar: " + c.getName());
         }

         return false;
      } else if (this.clientJarFiles.contains(c.getName())) {
         if (debugLogger.isDebugEnabled()) {
            debug("** We already have: " + c.getName());
         }

         return true;
      } else {
         if (debugLogger.isDebugEnabled()) {
            debug("** Adding: " + c.getName());
         }

         this.clientJarFiles.add(c.getName());
         this.addClass(c.getSuperclass());
         this.addClass(c.getInterfaces());
         this.addClass(c.getDeclaredFields());
         this.addClass(c.getConstructors());
         this.addClass(c.getDeclaredMethods());
         return true;
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[ClientJarMaker] " + s);
   }

   static {
      debugLogger = EJBDebugService.compilationLogger;
   }
}
