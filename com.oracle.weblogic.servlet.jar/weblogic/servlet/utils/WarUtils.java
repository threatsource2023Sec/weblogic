package weblogic.servlet.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.xml.stream.XMLStreamException;
import weblogic.application.utils.ClassLoaderUtils;
import weblogic.application.utils.annotation.AnnotationDetector;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.ParamValueBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.ContainerDescriptorBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationPackagesBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationResourcesBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.internal.War;
import weblogic.servlet.internal.WebAppDescriptor;
import weblogic.servlet.internal.fragment.WebFragmentLoader;
import weblogic.utils.Debug;
import weblogic.utils.URIUtils;
import weblogic.utils.application.WarDetector;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.ChangeAwareClassLoader;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarSource;
import weblogic.utils.classloaders.Source;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFile;

public final class WarUtils {
   private static final String WEB_XML;
   private static final String WEB_XML_URI = "WEB-INF/web.xml";
   public static final String WEBLOGIC_XML;
   private static final String WEB_SERVICES_XML;
   private static final String WEB_SERVICES_URI = "WEB-INF/web-services.xml";
   private static final String JSF_RI_LIB_NAME = "jsf";
   public static final String FACES_SERVLET_NAME = "javax.faces.webapp.FacesServlet";
   private static final String JAVAX_FACES_CONFIG_FILES = "javax.faces.CONFIG_FILES";
   private static final boolean POJAR_SCAN_DISABLED;
   private static final boolean DI_DISABLED;
   private static final boolean FCL_DISABLED;
   public static final String WEB_INF_FACES_CONFIG = "/WEB-INF/faces-config.xml";
   public static final String META_INF_FACES_CONFIG = "META-INF/faces-config.xml";
   private static final String LIB_URI = "/lib/";
   public static final String WEB_FRAGMENT_RESOURCE_URI = "META-INF/web-fragment.xml";
   public static final String AVATAR_APP_INDICATOR = "avatar";
   private static final String WEB_INF_BEANS_CONFIG = "/WEB-INF/beans.xml";
   private static final String META_INF_BEANS_CONFIG = "/META-INF/beans.xml";
   private static final String IDENTITY_STORE = "javax.security.enterprise.identitystore.IdentityStore";
   private static final String AUTH_MECHANISM = "javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism";
   static final String[] JSF2_ANNOTATIONS;
   static final String[] JSR375_ANNOTATIONS;

   public static boolean isWar(File f) throws IOException {
      return WarDetector.instance.isWar(f);
   }

   public static boolean isAvatarApplication(File f) throws IOException {
      if (f.isDirectory()) {
         return (new File(f, "avatar")).exists();
      } else {
         return f.getName().toLowerCase().endsWith(".zip") ? existsInWar(f, "avatar") : false;
      }
   }

   public static boolean canBeDeployedAsWarBasicCheck(File f) throws IOException {
      return isWar(f) || isAvatarApplication(f);
   }

   public static boolean catchAllCheck(File f) {
      return f.isDirectory();
   }

   public static boolean isPre15War(File f) throws IOException {
      return f.isDirectory() ? (new File(f, WEB_XML)).exists() : existsInWar(f, "WEB-INF/web.xml");
   }

   public static boolean isWebServices(File f) throws IOException {
      return f.isDirectory() ? (new File(f, WEB_SERVICES_XML)).exists() : existsInWar(f, "WEB-INF/web-services.xml");
   }

   private static boolean existsInWar(File webapp, String dd) throws IOException {
      ZipFile zf = null;

      boolean var3;
      try {
         zf = new ZipFile(webapp);
         var3 = zf.getEntry(dd) != null;
      } catch (ZipException var12) {
         throw new ZipException("Error opening file - " + webapp.getPath() + " Message - " + var12.getMessage());
      } finally {
         if (zf != null) {
            try {
               zf.close();
            } catch (IOException var11) {
            }
         }

      }

      return var3;
   }

   public static WebAppDescriptor getWebAppDescriptor(File altDD, VirtualJarFile virtualJarFile, File config, DeploymentPlanBean plan, String uri) {
      return altDD != null ? new WebAppDescriptor(altDD, (File)null, config, plan, uri) : new WebAppDescriptor(virtualJarFile, config, plan, uri);
   }

   public static WeblogicWebAppBean getWlWebAppBean(WebAppDescriptor desc) throws ToolFailureException {
      WeblogicWebAppBean wlBean = null;

      try {
         wlBean = desc.getWeblogicWebAppBean();
         return wlBean;
      } catch (IOException var3) {
         throw new ToolFailureException(var3.getMessage());
      } catch (XMLStreamException var4) {
         throw new ToolFailureException(var4.getMessage());
      }
   }

   public static WebAppBean getWebAppBean(WebAppDescriptor desc) throws ToolFailureException {
      WebAppBean webBean = null;

      try {
         webBean = desc.getWebAppBean();
         return webBean;
      } catch (IOException var3) {
         throw new ToolFailureException(var3.getMessage());
      } catch (XMLStreamException var4) {
         throw new ToolFailureException(var4.getMessage());
      }
   }

   public static boolean definedFacesServlet(WebAppBean webBean) {
      if (webBean != null) {
         ServletBean[] servlets = webBean.getServlets();
         if (servlets != null) {
            for(int i = 0; i < servlets.length; ++i) {
               if ("javax.faces.webapp.FacesServlet".equals(servlets[i].getServletClass())) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean isJsfApplication(WebAppBean webBean, WeblogicWebAppBean wlWebBean, War war) {
      if (definedFacesServlet(webBean)) {
         return true;
      } else if (war.getClassFinder().getSource("/WEB-INF/faces-config.xml") != null) {
         return true;
      } else if (!isDIEnabled(webBean)) {
         return false;
      } else {
         Set annotatedClasses = war.getAnnotatedClasses(JSF2_ANNOTATIONS);
         return annotatedClasses != null && !annotatedClasses.isEmpty();
      }
   }

   public static boolean isJSR375Application(WebAppBean webBean, ClassLoader cl, War war) {
      if (!isDIEnabled(webBean)) {
         return false;
      } else if (war.getResourceFinder("").getSource("/WEB-INF/beans.xml") == null && war.getResourceFinder("").getSource("/META-INF/beans.xml") == null) {
         return false;
      } else {
         Set annotatedClasses = war.getAnnotatedClasses(JSR375_ANNOTATIONS);
         if (annotatedClasses != null && !annotatedClasses.isEmpty()) {
            return true;
         } else {
            Set idStoreClasses = war.getHandlesImpls(cl, "javax.security.enterprise.identitystore.IdentityStore");
            if (idStoreClasses != null && !idStoreClasses.isEmpty()) {
               return true;
            } else {
               Set hamClasses = war.getHandlesImpls(cl, "javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism");
               return hamClasses != null && !hamClasses.isEmpty();
            }
         }
      }
   }

   public static String getFacesConfigFiles(WebAppBean bean) {
      if (bean == null) {
         return null;
      } else {
         ParamValueBean[] params = bean.getContextParams();

         for(int i = 0; i < params.length; ++i) {
            if ("javax.faces.CONFIG_FILES".equals(params[i].getParamName())) {
               return params[i].getParamValue();
            }
         }

         return null;
      }
   }

   public static boolean isDIEnabled(WebAppBean bean) {
      if (DI_DISABLED) {
         return false;
      } else {
         String schemaVersion = ((DescriptorBean)bean).getDescriptor().getOriginalVersionInfo();
         Debug.assertion(schemaVersion != null);

         try {
            return (double)Float.parseFloat(schemaVersion) >= 2.5 && bean.getVersion() != null;
         } catch (Exception var3) {
            return false;
         }
      }
   }

   public static ContainerInitializerConfiguration isContainerInitializerEnabledExplicitly(WeblogicWebAppBean wlWebBean) {
      if (wlWebBean != null && wlWebBean.getContainerDescriptors() != null && wlWebBean.getContainerDescriptors().length != 0) {
         ContainerDescriptorBean cd = wlWebBean.getContainerDescriptors()[0];
         return cd.isContainerInitializerEnabledSet() ? (cd.isContainerInitializerEnabled() ? ContainerInitializerConfiguration.ENABLED : ContainerInitializerConfiguration.DISABLED) : ContainerInitializerConfiguration.NONE;
      } else {
         return ContainerInitializerConfiguration.NONE;
      }
   }

   public static float getWebappVersion(WebAppBean bean) {
      if (bean.getVersion() == null) {
         return -1.0F;
      } else {
         String schemaVersion = ((DescriptorBean)bean).getDescriptor().getOriginalVersionInfo();
         if ("DTD".equals(schemaVersion)) {
            return -1.0F;
         } else {
            try {
               return Float.parseFloat(schemaVersion);
            } catch (Exception var4) {
               return -1.0F;
            }
         }
      }
   }

   public static boolean isAnnotationEnabled(WebAppBean webBean) {
      return isDIEnabled(webBean) && !webBean.isMetadataComplete();
   }

   public static Set addAllIfNotEmpty(Set target, Collection source) {
      Set result = target;
      if (source != null && !source.isEmpty()) {
         if (target == null) {
            result = new LinkedHashSet();
         }

         ((Set)result).addAll(source);
      }

      return (Set)result;
   }

   public static List addAllIfNotEmpty(List target, Collection source) {
      List result = target;
      if (source != null && !source.isEmpty()) {
         if (target == null) {
            result = new ArrayList();
         }

         ((List)result).addAll(source);
      }

      return (List)result;
   }

   public static boolean configureFCL(WeblogicWebAppBean wlWebAppBean, GenericClassLoader appClassLoader, boolean isStandaloneWebApp) throws ToolFailureException {
      PreferApplicationPackagesBean cBean = null;
      PreferApplicationResourcesBean rBean = null;
      if (wlWebAppBean != null && wlWebAppBean.getContainerDescriptors() != null && wlWebAppBean.getContainerDescriptors().length > 0) {
         ContainerDescriptorBean containerBean = wlWebAppBean.getContainerDescriptors()[0];
         cBean = containerBean.getPreferApplicationPackages();
         rBean = containerBean.getPreferApplicationResources();
         if ((cBean != null && cBean.getPackageNames().length > 0 || rBean != null && rBean.getResourceNames().length > 0) && containerBean.isPreferWebInfClasses()) {
            throw new ToolFailureException("Neither <prefer-application-packages> nor <prefer-application-resources> can be specified when <prefer-web-inf-classes> is turned on in weblogic.xml");
         }
      }

      ClassLoaderUtils.initFilterPatterns(cBean, rBean, appClassLoader);
      return false;
   }

   public static List findFacesConfigs(String jsfConfigFiles, ClassFinder resourceFinder, ClassFinder classFinder) {
      List results = null;
      List facesConfigURIs = findMetaInfFacesConfigs(classFinder);
      results = addAllIfNotEmpty((List)results, facesConfigURIs);
      List configFiles = findFacesConfigFiles(jsfConfigFiles, resourceFinder);
      List results = addAllIfNotEmpty((List)results, configFiles);
      ResourceLocation location = findDefaultFacesConfig(resourceFinder);
      if (location != null) {
         if (results == null) {
            results = new ArrayList();
         }

         ((List)results).add(location);
      }

      return (List)(results == null ? Collections.EMPTY_LIST : results);
   }

   public static void findTlds(ClassFinder rf, List tldUris, ClassFinder cf) {
      Enumeration e = rf.getSources("/WEB-INF/");

      File dir;
      while(e.hasMoreElements()) {
         Source s = (Source)e.nextElement();
         URL u = s.getURL();
         String protocol = u.getProtocol();
         if ("file".equals(protocol)) {
            dir = new File(u.getPath());
            if (dir.isDirectory()) {
               findTlds(dir, "/WEB-INF", tldUris);
            }
         }
      }

      String cp = cf.getClassPath();
      List jarFiles = getWebInfLibJarFiles(cp, true);
      Iterator it = jarFiles.iterator();

      while(it.hasNext()) {
         dir = (File)it.next();
         findTlds(dir, tldUris);
      }

   }

   public static Set getTagClasses(ClassFinder finder, Map tldInfo, boolean isAnnotationOnly, String type) {
      if (!"tag-class".equals(type) && !"listener-class".equals(type)) {
         return Collections.EMPTY_SET;
      } else {
         Set tagClasses = null;
         Set allTagClasses = (Set)tldInfo.get(type);
         if (isAnnotationOnly) {
            if (allTagClasses != null && !allTagClasses.isEmpty()) {
               Iterator var6 = allTagClasses.iterator();

               while(var6.hasNext()) {
                  Object o = var6.next();
                  String tagClass = (String)o;
                  if (AnnotationDetector.hasAnnotation(tagClass, finder)) {
                     if (tagClasses == null) {
                        tagClasses = new HashSet();
                     }

                     ((Set)tagClasses).add(tagClass);
                  }
               }
            }
         } else {
            tagClasses = allTagClasses;
         }

         return (Set)(tagClasses == null ? Collections.EMPTY_SET : tagClasses);
      }
   }

   private static void findTlds(File root, String prefixUrl, List tldUrls) {
      String[] s = root.list();
      if (s != null && s.length != 0) {
         for(int i = 0; i < s.length; ++i) {
            File subdir = new File(root, s[i]);
            if (subdir.isDirectory()) {
               findTlds(subdir, prefixUrl + '/' + s[i], tldUrls);
            } else if (subdir.isFile() && s[i].endsWith(".tld")) {
               tldUrls.add(new ResourceLocation(subdir, prefixUrl + '/' + s[i], 1));
            }
         }

      }
   }

   public static void findTlds(File jar, List tldUrls) {
      JarFile jarFile = null;

      try {
         jarFile = new JarFile(jar);
         Enumeration e = jarFile.entries();

         while(e.hasMoreElements()) {
            JarEntry entry = (JarEntry)e.nextElement();
            String name = entry.getName();
            if (name.endsWith(".tld")) {
               tldUrls.add(new ResourceLocation.JarResourceLocation(jar, name, 1));
            }
         }
      } catch (IOException var14) {
      } finally {
         if (jarFile != null) {
            try {
               jarFile.close();
            } catch (IOException var13) {
            }
         }

      }

   }

   private static List findFacesConfigFiles(String jsfConfigFiles, ClassFinder rf) {
      if (jsfConfigFiles == null) {
         return null;
      } else {
         jsfConfigFiles = jsfConfigFiles.trim();
         if (jsfConfigFiles.length() == 0) {
            return null;
         } else {
            String[] files = jsfConfigFiles.split(",");
            List facesConfigFiles = null;

            for(int i = 0; i < files.length; ++i) {
               if (!"/WEB-INF/faces-config.xml".equals(files[i])) {
                  if (!files[i].startsWith("/")) {
                     files[i] = "/" + files[i];
                  }

                  Source source = rf.getSource(files[i]);
                  if (source != null) {
                     URL url = source.getURL();
                     String protocol = url.getProtocol();
                     String file = url.getFile();
                     if ("file".equals(protocol)) {
                        if (facesConfigFiles == null) {
                           facesConfigFiles = new ArrayList();
                        }

                        facesConfigFiles.add(new ResourceLocation(new File(file), files[i], 2));
                     }
                  }
               }
            }

            return facesConfigFiles;
         }
      }
   }

   private static ResourceLocation findDefaultFacesConfig(ClassFinder rf) {
      Source source = rf.getSource("/WEB-INF/faces-config.xml");
      if (source == null) {
         return null;
      } else {
         URL url = source.getURL();
         String protocol = url.getProtocol();
         if (!"file".equals(protocol)) {
            return null;
         } else {
            String file = url.getFile();
            return new ResourceLocation(new File(file), "/WEB-INF/faces-config.xml", 2);
         }
      }
   }

   public static List findMetaInfFacesConfigs(ClassFinder cf) {
      List configFiles = null;
      List jarFiles = getWebInfLibJarFiles(cf.getClassPath(), false);
      Iterator it = jarFiles.iterator();

      while(it.hasNext()) {
         File jar = (File)it.next();
         JarFile jarFile = null;

         try {
            jarFile = new JarFile(jar);
            JarEntry entry = jarFile.getJarEntry("META-INF/faces-config.xml");
            if (entry != null) {
               if (configFiles == null) {
                  configFiles = new ArrayList();
               }

               configFiles.add(new ResourceLocation.JarResourceLocation(jar, "META-INF/faces-config.xml", 2));
            }
         } catch (IOException var15) {
         } finally {
            if (jarFile != null) {
               try {
                  jarFile.close();
               } catch (IOException var14) {
               }
            }

         }
      }

      return (List)(configFiles == null ? Collections.EMPTY_LIST : configFiles);
   }

   private static List getWebFragmentsFromClassesFolders(String classpath) {
      if (classpath == null) {
         return Collections.EMPTY_LIST;
      } else {
         String[] paths = classpath.split(File.pathSeparator);
         List results = null;

         for(int i = 0; i < paths.length; ++i) {
            if (paths[i].endsWith(File.separatorChar + "classes")) {
               File webFragmentFile = new File(paths[i] + File.separatorChar + "META-INF" + File.separatorChar + "web-fragment.xml");
               if (webFragmentFile.exists()) {
                  if (results == null) {
                     results = new ArrayList();
                  }

                  results.add(webFragmentFile);
               }
            }
         }

         return results;
      }
   }

   private static List getWebInfLibJarFiles(String classpath, boolean excludeInternalJars) {
      if (classpath == null) {
         return Collections.EMPTY_LIST;
      } else {
         String[] paths = classpath.split(File.pathSeparator);
         List files = new ArrayList();

         for(int i = 0; i < paths.length; ++i) {
            if (paths[i].endsWith(".jar") && (!excludeInternalJars || !paths[i].endsWith("_wl_cls_gen.jar"))) {
               File jar = new File(paths[i]);
               if (paths[i].replace(File.separatorChar, '/').endsWith("/lib/" + jar.getName())) {
                  files.add(jar);
               }
            }
         }

         return files;
      }
   }

   public static Set getWebFragments(String uri, ClassFinder cf, ClassFinder rf) {
      Set loaderSet = null;
      List files = getWebInfLibJarFiles(cf.getClassPath(), true);
      JarFile jar = null;

      Iterator var7;
      File webFragmentFile;
      try {
         File[] rootFiles = getRootFiles(rf);
         var7 = files.iterator();

         while(var7.hasNext()) {
            webFragmentFile = (File)var7.next();
            jar = new JarFile(webFragmentFile);
            JarEntry dd = jar.getJarEntry("META-INF/web-fragment.xml");
            if (loaderSet == null) {
               loaderSet = new HashSet();
            }

            URI relativeURI;
            if (dd != null) {
               JarSource s = new JarSource(jar, dd);
               relativeURI = URIUtils.getResourceURI(s.getURL());
               URI relativeURI = URIUtils.getRelativeURI(rootFiles, relativeURI);
               loaderSet.add(new WebFragmentLoader(s.getURL(), uri, relativeURI.toString(), true));
            } else if (!POJAR_SCAN_DISABLED) {
               URI resourceURI = webFragmentFile.getCanonicalFile().toURI();
               relativeURI = URIUtils.getRelativeURI(rootFiles, resourceURI);
               loaderSet.add(new WebFragmentLoader(getURL(jar), uri, relativeURI.toString(), false));
            }

            if (jar != null) {
               jar.close();
            }
         }
      } catch (IOException var23) {
      } finally {
         if (jar != null) {
            try {
               jar.close();
            } catch (IOException var21) {
            }
         }

      }

      List webFragmentsFromClassesFolder = getWebFragmentsFromClassesFolders(cf.getClassPath());
      if (webFragmentsFromClassesFolder != null) {
         var7 = webFragmentsFromClassesFolder.iterator();

         while(var7.hasNext()) {
            webFragmentFile = (File)var7.next();

            try {
               if (loaderSet == null) {
                  loaderSet = new HashSet();
               }

               loaderSet.add(new WebFragmentLoader(webFragmentFile.toURL(), uri, "", true));
            } catch (MalformedURLException var22) {
            }
         }
      }

      return (Set)(loaderSet == null ? Collections.emptySet() : loaderSet);
   }

   private static URL getURL(JarFile jar) {
      String zipFileName = jar.getName().replace(File.separatorChar, '/').replaceAll("#", "%23").replaceAll(" ", "%20");

      try {
         return new URL("zip", "", zipFileName);
      } catch (MalformedURLException var5) {
         try {
            return new URL("jar", "", (new File(zipFileName)).toURL().toString());
         } catch (MalformedURLException var4) {
            return null;
         }
      }
   }

   private static File[] getRootFiles(ClassFinder resourceFinder) throws IOException {
      Enumeration resources = resourceFinder.getSources("/");
      List rootFileList = new ArrayList();

      while(resources.hasMoreElements()) {
         Source source = (Source)resources.nextElement();
         URL url = source.getURL();
         File rootFile = new File(url.getFile());
         rootFileList.add(rootFile.getCanonicalFile());
      }

      return (File[])rootFileList.toArray(new File[0]);
   }

   public static boolean shouldUseSystemJSF(ClassLoader classLoader) {
      try {
         Class facesClazz = classLoader.loadClass("javax.faces.webapp.FacesServlet");
         return facesClazz.getClassLoader() == WarUtils.class.getClassLoader();
      } catch (ClassNotFoundException var2) {
         return false;
      }
   }

   public static ChangeAwareClassLoader createChangeAwareClassLoader(ClassFinder finder, boolean childFirst, ClassLoader parent, boolean isEar, Annotation annotation) {
      ChangeAwareClassLoader gc = new ChangeAwareClassLoader(finder, childFirst, parent);
      gc.setAnnotation(annotation);
      return gc;
   }

   static {
      WEB_XML = "WEB-INF" + File.separator + "web.xml";
      WEBLOGIC_XML = "WEB-INF" + File.separator + "weblogic.xml";
      WEB_SERVICES_XML = "WEB-INF" + File.separator + "web-services.xml";
      POJAR_SCAN_DISABLED = Boolean.getBoolean("weblogic.servlet.POJARScanDisabled");
      DI_DISABLED = Boolean.getBoolean("weblogic.servlet.DIDisabled");
      FCL_DISABLED = Boolean.getBoolean("weblogic.servlet.FCLDisabled");
      JSF2_ANNOTATIONS = new String[]{"javax.faces.bean.ManagedBean", "javax.faces.component.FacesComponent", "javax.faces.validator.FacesValidator", "javax.faces.convert.FacesConverter", "javax.faces.render.FacesBehaviorRenderer", "javax.faces.application.ResourceDependency", "javax.faces.application.ResourceDependencies", "javax.faces.event.ListenerFor", "javax.faces.event.ListenersFor", "javax.faces.component.UIComponent", "javax.faces.validator.Validator", "javax.faces.convert.Converter", "javax.faces.render.Renderer"};
      JSR375_ANNOTATIONS = new String[]{"javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition", "javax.security.enterprise.identitystore.LdapIdentityStoreDefinition", "javax.security.enterprise.identitystore.IdentityStoreHandler", "javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition", "javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition", "javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition", "javax.security.enterprise.authentication.mechanism.http.RememberMe", "javax.security.enterprise.authentication.mechanism.http.LoginToContinue"};
   }
}
