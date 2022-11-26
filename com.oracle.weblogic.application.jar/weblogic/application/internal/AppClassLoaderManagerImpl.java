package weblogic.application.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.CannotRedeployException;
import weblogic.application.ClassLoaderNotFoundException;
import weblogic.application.NonFatalDeploymentException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.ClassLoaderUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.xml.util.EmptyIterator;

@Service
public final class AppClassLoaderManagerImpl implements AppClassLoaderManager {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");
   private static final String NULL_KEY = new String();
   private final Map interAppCLMap = new ConcurrentHashMap();
   private final CLInfo rootInfo = new CLInfo((GenericClassLoader)null);

   private AppClassLoaderManagerImpl() {
   }

   public void addModuleLoader(GenericClassLoader gcl, String uri) {
      this.rootInfo.findOrCreateInfo(gcl, uri);
   }

   public void removeApplicationLoader(String appName) {
      CLInfo info = this.rootInfo.findInfo(appName);
      if (info != null) {
         GenericClassLoader gcl = info.getClassLoader();
         this.removeClassLoaderFromCLMap(gcl);
         Iterator it = info.children.values().iterator();

         while(it.hasNext()) {
            gcl = ((CLInfo)it.next()).getClassLoader();
            this.removeClassLoaderFromCLMap(gcl);
         }
      }

      this.rootInfo.removeAppInfo(appName);
   }

   public Iterator iterateModuleLoaders(String appName) {
      CLInfo info = this.rootInfo.findInfo(appName);
      if (info == null) {
         return EmptyIterator.getInstance();
      } else {
         final Iterator it = info.children.values().iterator();
         return new Iterator() {
            public boolean hasNext() {
               return it.hasNext();
            }

            public GenericClassLoader next() {
               return ((CLInfo)it.next()).getClassLoader();
            }

            public void remove() {
               throw new UnsupportedOperationException("remove");
            }
         };
      }
   }

   private void removeClassLoaderFromCLMap(GenericClassLoader gcl) {
      if (gcl != null) {
         this.interAppCLMap.remove(gcl);
         Iterator var2 = this.interAppCLMap.keySet().iterator();

         while(var2.hasNext()) {
            GenericClassLoader key = (GenericClassLoader)var2.next();
            if (this.sameAnnotation(key.getAnnotation(), gcl.getAnnotation())) {
               this.interAppCLMap.remove(key);
            } else if (key.getParent() != null && key.getParent() instanceof GenericClassLoader && this.sameAnnotation(((GenericClassLoader)key.getParent()).getAnnotation(), gcl.getAnnotation())) {
               this.interAppCLMap.remove(key);
            }
         }

         var2 = this.interAppCLMap.values().iterator();

         while(var2.hasNext()) {
            Map value = (Map)var2.next();
            value.remove(gcl);
            Iterator var4 = value.keySet().iterator();

            while(var4.hasNext()) {
               ClassLoader key = (ClassLoader)var4.next();
               if (key instanceof GenericClassLoader) {
                  if (this.sameAnnotation(((GenericClassLoader)key).getAnnotation(), gcl.getAnnotation())) {
                     value.remove(key);
                  } else if (key.getParent() != null && key.getParent() instanceof GenericClassLoader && this.sameAnnotation(((GenericClassLoader)key.getParent()).getAnnotation(), gcl.getAnnotation())) {
                     value.remove(key);
                  }
               }
            }
         }
      }

   }

   private boolean sameAnnotation(Annotation ann1, Annotation ann2) {
      return ann1.getAnnotationString().length() != 0 && ann2.getAnnotationString().length() != 0 && ann1.equals(ann2);
   }

   public GenericClassLoader findLoader(Annotation annotation) {
      ComponentInvocationContextManager mgr = ComponentInvocationContextManager.getInstance();
      ComponentInvocationContext cic = mgr.getCurrentComponentInvocationContext();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("findLoader(" + annotation + ")");
         debugLogger.debug("** Dumping CLInfo tree");
         debugLogger.debug(this.rootInfo.toString());
         debugLogger.debug("cic: " + cic + ", annotation: '" + annotation + "'");
      }

      if (cic.getApplicationName() == null && !StringUtils.isEmptyString(annotation.getAnnotationString())) {
         return null;
      } else {
         CLInfo info = this.rootInfo.findInfo(annotation);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Returning from findLoader(" + annotation + "): " + (info != null ? info.getClassLoader() : "Nothing Found"));
         }

         return info == null ? null : info.getClassLoader();
      }
   }

   private CLInfo findModuleInfo(String appName, String moduleURI) {
      CLInfo info = this.rootInfo.findInfo(appName, moduleURI);
      if (info == null) {
         info = this.rootInfo.findInfo(appName);
      }

      return info;
   }

   public GenericClassLoader findModuleLoader(String appName, String moduleURI) {
      CLInfo info = this.findModuleInfo(appName, moduleURI);
      return info == null ? null : info.getClassLoader();
   }

   public Class loadApplicationClass(Annotation annotation, String className) throws ClassNotFoundException {
      GenericClassLoader gcl = this.findLoader(annotation);
      if (gcl == null) {
         throw new ClassLoaderNotFoundException(className + " is not found due to missing GenericClassLoader.annotation:" + annotation);
      } else {
         return Class.forName(className, false, gcl);
      }
   }

   public Source findApplicationSource(Annotation annotation, String className) {
      GenericClassLoader gcl = this.findLoader(annotation);
      return gcl == null ? null : gcl.getClassFinder().getSource(className);
   }

   public GenericClassLoader findOrCreateIntraAppLoader(Annotation annotation, ClassLoader parent) {
      return this.findLoader(annotation);
   }

   public GenericClassLoader findOrCreateInterAppLoader(Annotation annotation, ClassLoader parent) {
      GenericClassLoader gcl = this.findLoader(annotation);
      if (gcl == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Class loader with annotation " + annotation + " not found. Unable to create inter-app class loader");
         }

         return null;
      } else if (parent != ClassLoader.getSystemClassLoader() && parent != AugmentableClassLoaderManager.getAugmentableSystemClassLoader().getParent()) {
         Map innerMap = (Map)this.interAppCLMap.get(gcl);
         if (innerMap == null) {
            synchronized(gcl) {
               innerMap = (Map)this.interAppCLMap.get(gcl);
               if (innerMap == null) {
                  innerMap = new ConcurrentHashMap();
                  this.interAppCLMap.put(gcl, innerMap);
               }
            }
         }

         GenericClassLoader toReturn = (GenericClassLoader)((Map)innerMap).get(parent);
         if (toReturn == null) {
            synchronized(parent) {
               toReturn = (GenericClassLoader)((Map)innerMap).get(parent);
               if (toReturn == null) {
                  toReturn = ClassLoaderUtils.createInterAppClassLoader(gcl, parent);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Created inter-app class loader " + toReturn + " as a child of " + parent + " with finder from " + gcl);
                  }

                  if (!(parent instanceof GenericClassLoader)) {
                     toReturn.setAnnotation(gcl.getAnnotation());
                  }

                  ((Map)innerMap).put(parent, toReturn);
               }
            }
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Returning inter-app class loader: " + toReturn);
         }

         return toReturn;
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Parent class loader is found to be System Classloader  or the same as the parent of the augmentable system classloader.Returning the class loader that was found using the annotation. " + gcl);
         }

         return gcl;
      }
   }

   public void checkPartialRedeploy(FlowContext appCtx, String[] uris) throws NonFatalDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("** Dumping CLInfo tree");
         debugLogger.debug(this.rootInfo.toString());
      }

      String appName = appCtx.getApplicationId();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("** checkPartialRedeploy for " + appName);
      }

      for(int i = 0; i < uris.length; ++i) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("** Checking " + uris[i]);
         }

         CLInfo info = this.findModuleInfo(appName, uris[i]);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("** My info is " + info);
         }

         CLInfo appInfo = this.rootInfo.findInfo(appName);
         GenericClassLoader appCl = appInfo != null ? appInfo.cl : null;
         if (info != null) {
            if (appCl != null && info.cl == appCl) {
               if (appCtx.isStopOperation()) {
                  throw new NonFatalDeploymentException("Module '" + uris[i] + "' has the same ClassLoader as the Application '" + appName + "'. Consider stopping the entire application.");
               }

               throw new CannotRedeployException("Module '" + uris[i] + "' has the same ClassLoader as the Application '" + appName + "'. Consider redeploying the entire application.");
            }

            Set s = info.findMissingURIs(new HashSet(Arrays.asList(uris)));
            if (!s.isEmpty()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("** MISSING " + prettyPrint(s));
               }

               if (appCtx.isStopOperation()) {
                  throw new NonFatalDeploymentException("Module " + uris[i] + " in application '" + appName + "' cannot be stopped because the following modules which depend on " + uris[i] + " were not included in the list: " + prettyPrint(s));
               }

               throw new CannotRedeployException("Module " + uris[i] + " in application '" + appName + "' cannot be redeployed because the following modules which depend on " + uris[i] + " were not included in the redeploy list: " + prettyPrint(s));
            }
         }
      }

   }

   public Set updatePartialDeploySet(FlowContext appCtx, String[] uris) throws NonFatalDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("** Dumping CLInfo tree");
         debugLogger.debug(this.rootInfo.toString());
      }

      String appName = appCtx.getApplicationId();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("** updatePartialRedeploySet for " + appName);
      }

      Set uriSet = new HashSet(Arrays.asList(uris));
      CLInfo appInfo = this.rootInfo.findInfo(appName);
      GenericClassLoader appCl = appInfo != null ? appInfo.cl : null;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("** Application " + appName + " classloader ->" + appCl);
      }

      for(int i = 0; i < uris.length; ++i) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("** Checking " + uris[i]);
         }

         CLInfo info = this.findModuleInfo(appName, uris[i]);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("** CLInfo is " + info);
         }

         if (info != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("** Module " + uris[i] + " Classloader -> " + info.cl);
            }

            if (appCl != null && info.cl == appCl) {
               if (appCtx.isStopOperation()) {
                  throw new NonFatalDeploymentException("Module '" + uris[i] + "' has the same ClassLoader as the Application '" + appName + "'. Consider stopping the entire application.");
               }

               throw new CannotRedeployException("Module '" + uris[i] + "' has the same ClassLoader as the Application '" + appName + "'. Consider redeploying the entire application.");
            }

            Set s = info.findMissingURIs(uriSet);
            if (!s.isEmpty()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("** MISSING " + prettyPrint(s));
                  debugLogger.debug("** Adding missing set to UriSet" + uriSet);
               }

               uriSet.addAll(s);
            }
         }
      }

      return uriSet;
   }

   private static String prettyPrint(Set s) {
      String sep = "";
      StringBuffer sb = new StringBuffer();

      for(Iterator it = s.iterator(); it.hasNext(); sep = ", ") {
         sb.append(sep);
         sb.append((String)it.next());
      }

      return sb.toString();
   }

   private static class CLInfo {
      private GenericClassLoader cl;
      private Map children = Collections.emptyMap();
      private final Set uriSet = Collections.synchronizedSet(new HashSet());

      CLInfo(GenericClassLoader cl) {
         this.cl = cl;
      }

      void setClassLoader(GenericClassLoader gcl) {
         this.cl = gcl;
      }

      GenericClassLoader getClassLoader() {
         return this.cl;
      }

      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb = this.toStringBuffer(sb, 0);
         return sb.toString();
      }

      StringBuffer toStringBuffer(StringBuffer sb, int indent) {
         for(int i = 0; i < indent; ++i) {
            sb.append(" ");
         }

         sb.append("[Annotation]: " + (this.cl == null ? "Null CL" : this.cl.getAnnotation().toString()));
         sb.append(" [uris:] " + AppClassLoaderManagerImpl.prettyPrint(this.uriSet));
         sb.append("\n");
         Iterator it = this.children.values().iterator();

         while(it.hasNext()) {
            CLInfo child = (CLInfo)it.next();
            child.toStringBuffer(sb, indent + 2);
         }

         return sb;
      }

      private void addChild(String key, CLInfo child) {
         if (this.children.size() == 0) {
            this.children = new ConcurrentHashMap();
         }

         this.children.put(key == null ? AppClassLoaderManagerImpl.NULL_KEY : key, child);
      }

      CLInfo findInfo(final String appName) {
         return this.findInfo(new Finder() {
            CLInfo findInfo(CLInfo root) {
               return (CLInfo)root.children.get(appName == null ? AppClassLoaderManagerImpl.NULL_KEY : appName);
            }
         });
      }

      CLInfo findInfo(String appName, String moduleName) {
         return moduleName == null ? this.findInfo(appName) : this.findInfo((Finder)(new ModuleFinder(appName, moduleName)));
      }

      CLInfo findInfo(Annotation an) {
         return this.findInfo((Finder)(new AnnotationFinder(an)));
      }

      private CLInfo findInfo(Finder f) {
         return f.findInfo(this);
      }

      CLInfo findOrCreateInfo(GenericClassLoader gcl, String uri) {
         LinkedList clList = new LinkedList();

         for(ClassLoader cl = gcl; cl instanceof GenericClassLoader; cl = ((ClassLoader)cl).getParent()) {
            clList.add(0, cl);
            Annotation ann = ((GenericClassLoader)cl).getAnnotation();
            if (ann.getModuleName() == null) {
               break;
            }
         }

         return this.findOrCreateInfo(clList.iterator(), uri);
      }

      private CLInfo findOrCreateInfo(Iterator it, String uri) {
         if (!it.hasNext()) {
            this.uriSet.add(uri);
            return this;
         } else {
            GenericClassLoader gcl = (GenericClassLoader)it.next();
            Annotation an = gcl.getAnnotation();
            String key = an.getModuleName() == null ? an.getApplicationName() : an.getModuleName();
            CLInfo child = null;
            child = (CLInfo)this.children.get(key == null ? AppClassLoaderManagerImpl.NULL_KEY : key);
            if (child == null) {
               child = new CLInfo(gcl);
               this.addChild(key, child);
            } else if (child.getClassLoader() != gcl) {
               child.children.clear();
               child.setClassLoader(gcl);
            }

            return child.findOrCreateInfo(it, uri);
         }
      }

      void removeAppInfo(String appName) {
         this.children.remove(appName == null ? AppClassLoaderManagerImpl.NULL_KEY : appName);
      }

      Set findMissingURIs(Set redeployingUris) {
         Set missing = new HashSet();
         this.findMissingURIs(missing, redeployingUris);
         return missing;
      }

      private void findMissingURIs(Set missing, Set redeployingUris) {
         Iterator it = this.uriSet.iterator();

         while(it.hasNext()) {
            String uri = (String)it.next();
            if (AppClassLoaderManagerImpl.debugLogger.isDebugEnabled()) {
               AppClassLoaderManagerImpl.debugLogger.debug("** Checking if " + uri + " is being redeployed");
            }

            if (!redeployingUris.contains(uri)) {
               if (AppClassLoaderManagerImpl.debugLogger.isDebugEnabled()) {
                  AppClassLoaderManagerImpl.debugLogger.debug("** " + uri + " is missing");
               }

               missing.add(uri);
            }
         }

         if (AppClassLoaderManagerImpl.debugLogger.isDebugEnabled()) {
            AppClassLoaderManagerImpl.debugLogger.debug("** Checking my " + this.children.size() + " children");
         }

         Iterator iter = this.children.values().iterator();

         while(iter.hasNext()) {
            CLInfo child = (CLInfo)iter.next();
            child.findMissingURIs(missing, redeployingUris);
         }

      }

      private static class ModuleFinder extends Finder {
         private final String appName;
         private final String moduleName;

         ModuleFinder(String appName, String moduleName) {
            super(null);
            this.appName = appName;
            this.moduleName = moduleName;
         }

         CLInfo findInfo(CLInfo root) {
            CLInfo appInfo = (CLInfo)root.children.get(this.appName == null ? AppClassLoaderManagerImpl.NULL_KEY : this.appName);
            return appInfo == null ? null : this.findModuleInfo(appInfo);
         }

         private CLInfo findModuleInfo(CLInfo node) {
            if (node.uriSet.contains(this.moduleName)) {
               return node;
            } else {
               Iterator it = node.children.values().iterator();

               CLInfo r;
               do {
                  if (!it.hasNext()) {
                     return null;
                  }

                  CLInfo child = (CLInfo)it.next();
                  r = this.findModuleInfo(child);
               } while(r == null);

               return r;
            }
         }
      }

      private static class AnnotationFinder extends Finder {
         private final Annotation annotation;

         AnnotationFinder(Annotation annotation) {
            super(null);
            this.annotation = annotation;
         }

         CLInfo findInfo(CLInfo node) {
            if (node.cl != null && this.annotation.equals(node.cl.getAnnotation())) {
               return node;
            } else {
               Iterator it = node.children.values().iterator();

               CLInfo r;
               do {
                  if (!it.hasNext()) {
                     return null;
                  }

                  CLInfo child = (CLInfo)it.next();
                  r = this.findInfo(child);
               } while(r == null);

               return r;
            }
         }
      }

      private abstract static class Finder {
         private Finder() {
         }

         abstract CLInfo findInfo(CLInfo var1);

         // $FF: synthetic method
         Finder(Object x0) {
            this();
         }
      }
   }
}
