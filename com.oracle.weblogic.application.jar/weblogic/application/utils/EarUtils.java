package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ApplicationFileManager;
import weblogic.application.Module;
import weblogic.application.ModuleContext;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.internal.FlowContext;
import weblogic.application.io.Ear;
import weblogic.deploy.internal.DeploymentPlanDescriptorLoader;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.PermissionBean;
import weblogic.j2ee.descriptor.PermissionsBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.security.internal.MakePermission;
import weblogic.security.service.SecurityServiceException;
import weblogic.utils.FileUtils;
import weblogic.utils.StackTraceUtils;
import weblogic.utils.application.WarDetector;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.JarFileUtils;
import weblogic.utils.jars.VirtualJarFile;

public final class EarUtils {
   static final String EXPLODED_EAR_SUFFIX = ".ear";
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   private EarUtils() {
   }

   public static boolean isDebugOn() {
      return debugLogger.isDebugEnabled();
   }

   public static void debug(String s) {
      debugLogger.debug(addClassName(s));
   }

   public static String addClassName(String s) {
      StackTraceElement[] elements = (new Exception()).getStackTrace();
      String className = elements[2].getClassName();
      int i = className.lastIndexOf(".");
      if (i != -1) {
         className = className.substring(i + 1);
      }

      return "[" + className + "] " + s;
   }

   public static void handleUnsetContextRoot(String webUri, String contextRoot, ApplicationBean appXml, WeblogicWebAppBean wlsXml, WebAppBean webXml) {
      if (appXml != null) {
         ModuleBean[] modules = appXml.getModules();

         for(int i = 0; i < modules.length; ++i) {
            WebBean webBean = modules[i].getWeb();
            if (webBean != null && webBean.getWebUri().equals(webUri)) {
               if (webBean.getContextRoot() != null && webBean.getContextRoot().startsWith("__BEA_WLS_INTERNAL_UNSET_CONTEXT_ROOT")) {
                  String cp = null;
                  if (contextRoot != null) {
                     cp = contextRoot;
                  } else if (wlsXml != null && wlsXml.getContextRoots().length > 0) {
                     cp = wlsXml.getContextRoots()[0];
                  } else if (webXml != null && webXml.getDefaultContextPaths().length > 0 && !webXml.getDefaultContextPaths()[0].endsWith("/")) {
                     cp = webXml.getDefaultContextPaths()[0];
                  } else if (webUri != null) {
                     cp = WarDetector.instance.stem(webUri);
                  }

                  if (cp != null) {
                     webBean.setContextRoot(cp);
                  }
               }

               return;
            }
         }

      }
   }

   public static String fixAppContextRoot(String contextRoot) {
      return contextRoot != null && contextRoot.startsWith("__BEA_WLS_INTERNAL_UNSET_CONTEXT_ROOT") ? null : contextRoot;
   }

   public static boolean isEar(ApplicationArchive app) {
      return app.getName().endsWith(".ear") || app.getEntry("META-INF/application.xml") != null;
   }

   public static boolean isEar(File f) {
      if (!f.isDirectory()) {
         return f.getName().endsWith(".ear");
      } else {
         return (new File(f, "META-INF/application.xml")).exists() || (new File(f, "META-INF/weblogic-application.xml")).exists() || isSplitDir(f) || f.getName().endsWith(".ear");
      }
   }

   private static ApplicationBean createApplicationBean() {
      return (ApplicationBean)(new DescriptorManager()).createDescriptorRoot(ApplicationBean.class).getRootBean();
   }

   private static File extractAndGetTempRoot(VirtualJarFile vjf) throws IOException {
      String vjfName = vjf.getName();
      int slash = vjfName.lastIndexOf(System.getProperty("file.separator"));
      if (slash > -1) {
         vjfName.substring(slash);
      }

      File tempDir = FileUtils.createTempDir("_earscanner_tmp", (File)null);
      if (tempDir.exists()) {
         FileUtils.remove(tempDir);
      }

      tempDir.mkdirs();
      JarFileUtils.extract(vjf, tempDir);
      return tempDir;
   }

   public static void linkURI(Ear ear, ApplicationFileManager appFileMgr, String uri, File f) throws IOException {
      ear.registerLink(uri, f);
      appFileMgr.registerLink(uri, f.getAbsolutePath());
   }

   public static String reallyGetModuleURI(ModuleBean b) {
      return b.getWeb() != null ? b.getWeb().getWebUri() : getModuleURI(b);
   }

   public static String getModuleURI(ModuleBean b) {
      if (b.getConnector() != null) {
         return b.getConnector();
      } else if (b.getEjb() != null) {
         return b.getEjb();
      } else if (b.getJava() != null) {
         return b.getJava();
      } else if (b.getWeb() != null) {
         return getContextRootName(b);
      } else if (b.getAltDd() != null) {
         return b.getAltDd();
      } else {
         throw new AssertionError("ModuleBean contains no module URI");
      }
   }

   public static Module getModule(FlowContext ctx, String moduleURI) {
      Module[] var2 = ctx.getModuleManager().getModules();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Module m = var2[var4];
         ModuleContext modCtx = ctx.getModuleContext(m.getId());
         if (moduleURI.equals(modCtx.getURI())) {
            return m;
         }
      }

      return null;
   }

   public static void informDescriptor(DescriptorBean bean) {
      try {
         (new EditableDescriptorManager()).writeDescriptorAsXML(bean.getDescriptor(), System.out);
      } catch (Exception var2) {
         debugLogger.debug("Failed to write descriptor to stdout because of " + var2);
      }

   }

   public static void handleParsingError(Throwable th, String earName) throws ToolFailureException {
      throw new ToolFailureException(J2EELogger.logAppcErrorParsingEARDescriptorsLoggable(earName, StackTraceUtils.throwable2StackTrace(th)).getMessage(), th);
   }

   public static File getSplitDirProperties(File ear) {
      return new File(ear, ".beabuild.txt");
   }

   public static boolean isSplitDir(File ear) {
      return getSplitDirProperties(ear).exists();
   }

   public static String getContextRootName(ModuleBean m) {
      WebBean w = m.getWeb();
      if (w == null) {
         return null;
      } else {
         String c = fixAppContextRoot(w.getContextRoot());
         return c != null && !"".equals(c) ? c : w.getWebUri();
      }
   }

   public static File getConfigDir(ApplicationContextInternal appCtx) {
      return appCtx.getAppDeploymentMBean().getPlanDir() == null ? null : new File(appCtx.getAppDeploymentMBean().getLocalPlanDir());
   }

   public static void informRoleToPrincipalsMapping(Map m) {
      if (m != null) {
         debugLogger.debug("**********************************");
         Iterator iter = m.entrySet().iterator();

         while(true) {
            Map.Entry entry;
            String[] principals;
            do {
               if (!iter.hasNext()) {
                  return;
               }

               entry = (Map.Entry)iter.next();
               principals = (String[])((String[])entry.getValue());
            } while(principals.length <= 0);

            debugLogger.debug("Role: " + entry.getKey() + " -> principals: ");

            for(int i = 0; i < principals.length; ++i) {
               debugLogger.debug(principals[i]);
            }
         }
      }
   }

   private static DeploymentPlanBean getDeploymentPlanDescriptor(String path) {
      if (path == null) {
         return null;
      } else {
         File pf = new File(path);
         DeploymentPlanBean plan = null;

         try {
            DeploymentPlanDescriptorLoader dpd = new DeploymentPlanDescriptorLoader(pf);
            plan = dpd.getDeploymentPlanBean();
            return plan;
         } catch (XMLStreamException var4) {
            throw new IllegalArgumentException(var4.getMessage(), var4);
         } catch (IOException var5) {
            throw new IllegalArgumentException(var5.getMessage(), var5);
         } catch (ClassCastException var6) {
            throw new IllegalArgumentException(var6.getMessage(), var6);
         }
      }
   }

   private static Map addAppDescriptors(AppDeploymentMBean appDepMBean, List appFiles, VirtualJarFile vjf) {
      String[] uris = new String[]{"META-INF/application.xml", "META-INF/weblogic-application.xml", "META-INF/weblogic-extension.xml"};
      Map descriptors = new HashMap(uris.length);

      for(int i = 0; i < uris.length; ++i) {
         File desc = getFile(uris[i], vjf);
         if (desc != null) {
            descriptors.put(uris[i], desc);
         }
      }

      File absPlanDir = appDepMBean.getAbsolutePlanDir() == null ? null : new File(appDepMBean.getAbsolutePlanDir());

      for(int i = 0; i < uris.length; ++i) {
         File planDD = getExternalPlanDescriptorFile(getDeploymentPlanDescriptor(appDepMBean.getAbsolutePlanPath()), absPlanDir, appDepMBean.getApplicationName(), uris[i]);
         if (planDD != null && planDD.exists()) {
            descriptors.put(uris[i], planDD);
         }
      }

      String altdd = appDepMBean.getAltDescriptorPath();
      if (altdd != null && altdd.trim().length() > 0) {
         descriptors.put("META-INF/application.xml", new File(altdd));
      }

      String wlAltdd = appDepMBean.getAltWLSDescriptorPath();
      if (wlAltdd != null && wlAltdd.trim().length() > 0) {
         descriptors.put("META-INF/weblogic-application.xml", new File(wlAltdd));
      }

      Iterator i = descriptors.values().iterator();

      while(i.hasNext()) {
         File f = (File)i.next();
         if (f.exists()) {
            appFiles.add(f);
         }
      }

      return descriptors;
   }

   private static File getFile(String uri, VirtualJarFile vjf) {
      File[] roots = vjf.getRootFiles();

      for(int i = 0; i < roots.length; ++i) {
         File ret = new File(roots[i], uri);
         if (ret.exists()) {
            return ret;
         }
      }

      return null;
   }

   private static void addAppURIs(ApplicationBean appDD, Set ids, List appFiles, VirtualJarFile vjf) {
      if (appDD != null) {
         ModuleBean[] modules = appDD.getModules();

         for(int i = 0; i < modules.length; ++i) {
            String uri = null;
            String name = null;
            File f;
            if (modules[i].getWeb() != null) {
               uri = modules[i].getWeb().getWebUri();
               name = fixAppContextRoot(modules[i].getWeb().getContextRoot());
               if (name == null || "".equals(name) || "/".equals(name)) {
                  name = uri;
               }

               if (ids.contains(name)) {
                  f = getFile(uri, vjf);
                  if (f != null) {
                     appFiles.add(f);
                  }

                  ids.remove(name);
               }
            } else {
               if (modules[i].getEjb() != null) {
                  uri = modules[i].getEjb();
               } else if (modules[i].getConnector() != null) {
                  uri = modules[i].getConnector();
               } else if (modules[i].getJava() != null) {
                  uri = modules[i].getJava();
               }

               if (uri != null && ids.contains(uri)) {
                  f = getFile(uri, vjf);
                  if (f != null) {
                     appFiles.add(f);
                  }

                  ids.remove(uri);
               }
            }

            if (ids.isEmpty()) {
               break;
            }
         }

      }
   }

   private static void addWlAppURIs(WeblogicApplicationBean wlapp, Set ids, List appFiles, VirtualJarFile vjf) {
      if (wlapp != null) {
         WeblogicModuleBean[] mods = wlapp.getModules();

         for(int i = 0; i < mods.length; ++i) {
            File f;
            if ("JDBC".equals(mods[i].getType())) {
               if (ids.contains(mods[i].getName())) {
                  ids.remove(mods[i].getName());
                  f = getFile(mods[i].getPath(), vjf);
                  if (f != null) {
                     appFiles.add(f);
                  }
               }
            } else if (ids.contains(mods[i].getPath())) {
               ids.remove(mods[i].getPath());
               f = getFile(mods[i].getPath(), vjf);
               if (f != null) {
                  appFiles.add(f);
               }
            }
         }

      }
   }

   private static File getExternalPlanDescriptorFile(DeploymentPlanBean planBean, File configDir, String moduleName, String descriptorUri) {
      if (planBean != null && configDir != null) {
         ModuleDescriptorBean md = planBean.findModuleDescriptor(moduleName, descriptorUri);
         if (md != null && md.isExternal()) {
            File config;
            if (planBean.rootModule(moduleName)) {
               config = configDir;
            } else {
               config = new File(configDir, moduleName);
            }

            return new File(config, md.getUri());
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static File resolveAltDD(ApplicationBean appDD, VirtualJarFile earVjf, String moduleUri) throws IOException {
      String altDDUri = getAltDDUri(appDD, moduleUri);
      if (altDDUri == null) {
         return null;
      } else {
         File altDD = resolveAltDD(earVjf, altDDUri);
         if (altDD == null) {
            throw new IOException("Unable to find the alt-dd for module " + moduleUri + " with the alt-dd " + altDDUri);
         } else {
            return altDD;
         }
      }
   }

   public static String getAltDDUri(ApplicationBean appDD, String moduleUri) throws IOException {
      if (appDD == null) {
         return null;
      } else {
         ModuleBean[] modules = appDD.getModules();

         for(int i = 0; i < modules.length; ++i) {
            if (reallyGetModuleURI(modules[i]).equals(moduleUri)) {
               return modules[i].getAltDd();
            }
         }

         return null;
      }
   }

   public static File resolveAltDD(VirtualJarFile earVjf, String altDDUri) {
      File[] rootFiles = earVjf.getRootFiles();

      for(int j = 0; j < rootFiles.length; ++j) {
         File altDDFile = new File(rootFiles[j], altDDUri);
         if (altDDFile.exists()) {
            return altDDFile;
         }
      }

      return null;
   }

   public static File getExternalAltDDFile(ApplicationContextInternal appCtx) {
      File altDD = null;
      AppDeploymentMBean dmb = appCtx.getAppDeploymentMBean();
      if (dmb != null) {
         String altDDPath = dmb.getAltDescriptorPath();
         if (altDDPath != null) {
            altDD = new File(altDDPath);
         }
      }

      return altDD;
   }

   public static String toModuleId(ApplicationContextInternal appCtx, String uri) {
      if (uri == null) {
         return uri;
      } else {
         ApplicationBean dd = appCtx.getApplicationDD();
         if (dd == null) {
            return uri;
         } else {
            ModuleBean[] modules = dd.getModules();
            if (modules == null) {
               return uri;
            } else {
               Map moduleURItoIdMap = appCtx.getModuleURItoIdMap();
               String retValue = moduleURItoIdMap != null ? (String)moduleURItoIdMap.get(uri) : null;
               return retValue != null ? retValue : uri;
            }
         }
      }
   }

   public static String[] toModuleIds(ApplicationContextInternal appCtx, String[] uris) {
      if (uris != null && uris.length != 0) {
         ApplicationBean dd = appCtx.getApplicationDD();
         if (dd == null) {
            return uris;
         } else {
            ModuleBean[] modules = dd.getModules();
            if (modules == null) {
               return uris;
            } else {
               int len = uris.length;
               String[] moduleIds = new String[len];

               for(int i = 0; i < len; ++i) {
                  moduleIds[i] = toModuleId(appCtx, uris[i]);
               }

               return moduleIds;
            }
         }
      } else {
         return uris;
      }
   }

   public static boolean isValidModule(FlowContext appCtx, String uriOrContextRoot) {
      if (appCtx.getModuleManager().findModuleWithId(uriOrContextRoot) != null) {
         return true;
      } else {
         HashSet stoppedModules = new HashSet();
         if (appCtx.getStoppedModules() != null) {
            Collections.addAll(stoppedModules, appCtx.getStoppedModules());
            if (stoppedModules.contains(uriOrContextRoot)) {
               return true;
            }

            HashSet additionalStoppedModules = new HashSet();
            Iterator var4 = stoppedModules.iterator();

            while(var4.hasNext()) {
               String stoppedModule = (String)var4.next();
               String stoppedModuleContextRoot = findContextRoot(appCtx, stoppedModule);
               if (stoppedModuleContextRoot != null) {
                  additionalStoppedModules.add(stoppedModuleContextRoot);
               }
            }

            if (additionalStoppedModules.contains(uriOrContextRoot)) {
               return true;
            }

            stoppedModules.addAll(additionalStoppedModules);
         }

         String contextRoot = findContextRoot(appCtx, uriOrContextRoot);
         if (contextRoot != null) {
            if (appCtx.getModuleManager().findModuleWithId(contextRoot) != null) {
               return true;
            }

            if (stoppedModules.contains(contextRoot)) {
               return true;
            }
         }

         ModuleBean[] moduleBeans = appCtx.getApplicationDD().getModules();
         int var7;
         ModuleBean module;
         ModuleBean[] var13;
         int var15;
         if (moduleBeans != null) {
            var13 = moduleBeans;
            var15 = moduleBeans.length;

            for(var7 = 0; var7 < var15; ++var7) {
               module = var13[var7];
               WebBean web = module.getWeb();
               if (web != null && uriOrContextRoot.equals(web.getWebUri())) {
                  return true;
               }
            }
         }

         if (appCtx.getApplicationDD() != null && appCtx.getApplicationDD().getModules() != null) {
            var13 = appCtx.getApplicationDD().getModules();
            var15 = var13.length;

            for(var7 = 0; var7 < var15; ++var7) {
               module = var13[var7];
               if (uriOrContextRoot.equals(module.getConnector()) || uriOrContextRoot.equals(module.getEjb()) || uriOrContextRoot.equals(module.getJava())) {
                  return true;
               }

               if (module.getWeb() != null && (uriOrContextRoot.equals(module.getWeb().getWebUri()) || uriOrContextRoot.equals(getContextRootName(module)))) {
                  return true;
               }
            }
         }

         WeblogicApplicationBean wldd = appCtx.getWLApplicationDD();
         if (wldd != null) {
            WeblogicModuleBean[] wlModuleBeans = wldd.getModules();
            if (wlModuleBeans != null) {
               WeblogicModuleBean[] var17 = wlModuleBeans;
               int var18 = wlModuleBeans.length;

               for(int var19 = 0; var19 < var18; ++var19) {
                  WeblogicModuleBean wlModuleBean = var17[var19];
                  if (uriOrContextRoot.equals(wlModuleBean.getName())) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public static String findContextRoot(FlowContext appCtx, String webModuleUri) {
      if (appCtx.getApplicationDD() != null && appCtx.getApplicationDD().getModules() != null) {
         ModuleBean[] var2 = appCtx.getApplicationDD().getModules();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ModuleBean module = var2[var4];
            if (module.getWeb() != null && module.getWeb().getWebUri().equals(webModuleUri)) {
               return getContextRootName(module);
            }
         }
      }

      return null;
   }

   public static PermissionCollection getPermissions(PermissionsBean permissionsBean) throws SecurityServiceException {
      PermissionCollection pc = null;
      if (permissionsBean != null) {
         PermissionBean[] pbs = permissionsBean.getPermissions();
         if (pbs != null) {
            PermissionBean[] var3 = pbs;
            int var4 = pbs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               PermissionBean b = var3[var5];
               if (pc == null) {
                  pc = new Permissions();
               }

               pc.add(MakePermission.makePermission(b.getClassName(), b.getName(), b.getActions()));
            }
         }
      }

      return pc;
   }
}
