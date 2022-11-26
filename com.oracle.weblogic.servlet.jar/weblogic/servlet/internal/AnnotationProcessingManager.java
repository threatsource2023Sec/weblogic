package weblogic.servlet.internal;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.annotation.MultipartConfig;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.FilterBean;
import weblogic.j2ee.descriptor.ListenerBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.internal.fragment.MergeException;
import weblogic.servlet.internal.fragment.WebFragmentLoader;
import weblogic.servlet.internal.fragment.WebFragmentManager;
import weblogic.servlet.utils.WarUtils;
import weblogic.utils.ErrorCollectionException;

public final class AnnotationProcessingManager {
   private WebAppHelper helper;
   private WebAnnotationProcessor processor;
   private WebFragmentManager webFragmentManager;
   private Map categorizedClasses;
   private static Class[] supportedClassLevelAnnotations = null;

   public AnnotationProcessingManager(WebAppHelper helper, WebAnnotationProcessor processor, WebFragmentManager webFragmentManager) {
      this.helper = helper;
      this.processor = processor;
      this.webFragmentManager = webFragmentManager;
   }

   public static synchronized Class[] getSupportedClassLevelAnnotations() {
      if (supportedClassLevelAnnotations == null) {
         ArrayList list = new ArrayList();
         ClassType[] var1 = AnnotationProcessingManager.ClassType.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            ClassType t = var1[var3];
            if (t.getAnnotationClass() != null) {
               try {
                  list.add(Class.forName(t.getAnnotationClass()));
               } catch (ClassNotFoundException var6) {
                  throw new AssertionError("Could not load " + t.getAnnotationClass());
               }
            }
         }

         supportedClassLevelAnnotations = (Class[])list.toArray(new Class[0]);
      }

      return supportedClassLevelAnnotations;
   }

   public WebAppBean processAnnotationsOnClone(ClassLoader classLoader, WebAppBean webBean, WeblogicWebAppBean wlWebAppBean) throws ErrorCollectionException, ClassNotFoundException, MergeException {
      if (!WarUtils.isAnnotationEnabled(webBean)) {
         return webBean;
      } else {
         Descriptor clone = (Descriptor)((DescriptorBean)webBean).getDescriptor().clone();
         WebAppBean webBeanClone = (WebAppBean)clone.getRootBean();
         this.processAnnotations(classLoader, webBeanClone, wlWebAppBean, false);
         return webBeanClone;
      }
   }

   public void processAnnotations(ClassLoader classLoader, WebAppBean webBean, WeblogicWebAppBean wlWebAppBean) throws ErrorCollectionException, ClassNotFoundException, MergeException {
      this.processAnnotations(classLoader, webBean, wlWebAppBean, true);
   }

   private void processAnnotations(ClassLoader classLoader, WebAppBean webBean, WeblogicWebAppBean wlWebAppBean, boolean mergeFragment) throws ErrorCollectionException, ClassNotFoundException, MergeException {
      if (WarUtils.isAnnotationEnabled(webBean)) {
         try {
            this.helper.startAnnotationProcess();
            long beginTime = System.currentTimeMillis();
            this.categorizedClasses = this.categorizeClasses(this.helper, webBean, wlWebAppBean, classLoader);
            List webFragments = this.webFragmentManager.getSortedFragments();
            if (webFragments != null) {
               Iterator var8 = webFragments.iterator();

               while(var8.hasNext()) {
                  WebFragmentLoader webFragment = (WebFragmentLoader)var8.next();
                  if (mergeFragment) {
                     this.webFragmentManager.mergeFragment(webFragment);
                  }

                  if (!webFragment.isMetadataComplete()) {
                     this.processAnnotationsInWebFragment(classLoader, webBean, webFragment);
                  }
               }
            }

            this.processAnnotationsOutsideWebFragment(classLoader, webBean);
            this.processAnnotationsInWebXml(classLoader, webBean);
            WebComponentContributor.dbg("Annotation processing took " + (System.currentTimeMillis() - beginTime) + " ms");
            this.processor.validate(classLoader, (DescriptorBean)webBean, true);
         } finally {
            this.helper.completeAnnotationProcess();
         }

      }
   }

   private void processAnnotations(ClassLoader classLoader, WebAppBean webBean, AnnotationProcessingFilter filter) throws ClassNotFoundException {
      ClassType[] var4 = AnnotationProcessingManager.ClassType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ClassType type = var4[var6];
         this.processAnnotationForClasses(type, filter, classLoader, webBean);
      }

   }

   private void processAnnotationsInWebFragment(ClassLoader classLoader, WebAppBean webBean, final WebFragmentLoader webFragment) throws ClassNotFoundException {
      AnnotationProcessingFilter filter = new AnnotationProcessingFilter() {
         public boolean accept(String className) {
            URL classSourceUrl = AnnotationProcessingManager.this.helper.getClassSourceUrl(className);
            return webFragment.shouldProcessAnnotation(classSourceUrl);
         }
      };
      this.processAnnotations(classLoader, webBean, filter);
   }

   private void processAnnotationsOutsideWebFragment(ClassLoader classLoader, final WebAppBean webBean) throws ClassNotFoundException {
      AnnotationProcessingFilter filter = new AnnotationProcessingFilter() {
         public boolean accept(String className) {
            return AnnotationProcessingManager.this.shouldProcessClassOutsideWebFragments(webBean, className);
         }
      };
      this.processAnnotations(classLoader, webBean, filter);
   }

   private void processAnnotationForClasses(ClassType type, AnnotationProcessingFilter filter, ClassLoader classLoader, WebAppBean webBean) throws ClassNotFoundException {
      Set classes = (Set)this.categorizedClasses.get(type);
      if (classes != null && !classes.isEmpty()) {
         Iterator it = classes.iterator();

         while(it.hasNext()) {
            String className = (String)it.next();
            if (filter.accept(className)) {
               it.remove();
               Class clz = classLoader.loadClass(className);
               switch (type) {
                  case WEB_SERVLET:
                     this.processor.processWebServletAnnotation(clz, webBean);
                     break;
                  case WEB_FILTER:
                     this.processor.processWebFilterAnnotation(clz, webBean);
                     break;
                  case WEB_LISTENER:
                     this.processor.processWebListenerAnnotation(clz, webBean);
                     break;
                  case MULTIPART_CONFIG:
                     this.processor.processMultipartConfigAnnotation(clz, webBean);
                     break;
                  case SERVLET_SECURITY:
                     this.processor.processServletSecurityAnnotation(clz, webBean);
                     break;
                  case WL_SERVLET:
                     this.processor.processWLServletAnnotation(clz, webBean);
                     break;
                  case WL_FILTER:
                     this.processor.processWLFilterAnnotation(clz, webBean);
                     break;
                  case TAG_HANDLER:
                  case TAG_LISTENER:
                  case MANAGED_BEAN:
                     this.processor.processJ2eeAnnotations(clz, webBean);
                     break;
                  case WEBSOCKET:
                     this.processor.processWebSocketAnnotation(clz, webBean);
                     this.processor.processJ2eeAnnotations(clz, webBean);
               }
            }
         }

      }
   }

   private void processAnnotationsInWebXml(ClassLoader cl, WebAppBean webBean) throws ClassNotFoundException {
      this.processServlets(webBean, cl);
      this.processFilters(webBean, cl);
      this.processListeners(webBean, cl);
   }

   private void processServlets(WebAppBean bean, ClassLoader cl) throws ClassNotFoundException {
      ServletBean[] servlets = bean.getServlets();
      if (servlets != null && servlets.length != 0) {
         ServletBean[] var4 = servlets;
         int var5 = servlets.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ServletBean servlet = var4[var6];
            String servletClass = servlet.getServletClass();
            if (servletClass != null && this.shouldProcessClassInWebXml(servletClass)) {
               Class clz = cl.loadClass(servletClass);
               this.processor.processServlet(bean, servlet, clz);
            }
         }

      }
   }

   private void processFilters(WebAppBean bean, ClassLoader cl) throws ClassNotFoundException {
      FilterBean[] filters = bean.getFilters();
      if (filters != null && filters.length != 0) {
         FilterBean[] var4 = filters;
         int var5 = filters.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            FilterBean filter = var4[var6];
            String filterClass = filter.getFilterClass();
            if (filterClass != null && this.shouldProcessClassInWebXml(filterClass)) {
               Class clz = cl.loadClass(filterClass);
               this.processor.processJ2eeAnnotations(clz, bean);
            }
         }

      }
   }

   private void processListeners(WebAppBean bean, ClassLoader cl) throws ClassNotFoundException {
      ListenerBean[] listeners = bean.getListeners();
      if (listeners != null && listeners.length != 0) {
         ListenerBean[] var4 = listeners;
         int var5 = listeners.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ListenerBean listener = var4[var6];
            String listenerClass = listener.getListenerClass();
            if (listenerClass != null && this.shouldProcessClassInWebXml(listenerClass)) {
               Class clz = cl.loadClass(listenerClass);
               this.processor.processJ2eeAnnotations(clz, bean);
            }
         }

      }
   }

   private Map categorizeClasses(WebAppHelper helper, WebAppBean webBean, WeblogicWebAppBean wlWebAppBean, ClassLoader classLoader) {
      Map classes = new HashMap();
      ClassType[] var6 = AnnotationProcessingManager.ClassType.values();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         ClassType classType = var6[var8];
         String annotation = classType.getAnnotationClass();
         if (annotation != null && classType != AnnotationProcessingManager.ClassType.MANAGED_BEAN) {
            classes.put(classType, helper.getAnnotatedClasses(annotation));
         }
      }

      classes.put(AnnotationProcessingManager.ClassType.TAG_HANDLER, helper.getTagHandlers(true));
      classes.put(AnnotationProcessingManager.ClassType.TAG_LISTENER, helper.getTagListeners(true));
      String facesConfigs = WarUtils.getFacesConfigFiles(webBean);
      classes.put(AnnotationProcessingManager.ClassType.MANAGED_BEAN, helper.getManagedBeanClasses(facesConfigs));
      if (!this.isDefaultMultipartConfigDisabled(classLoader) && this.isMultiPartAnnotatedJSFApplication(helper, webBean, wlWebAppBean, classLoader)) {
         Set multipartClasses = (Set)classes.get(AnnotationProcessingManager.ClassType.MULTIPART_CONFIG);
         if (multipartClasses == null || ((Set)multipartClasses).isEmpty()) {
            multipartClasses = new HashSet();
         }

         ((Set)multipartClasses).add("javax.faces.webapp.FacesServlet");
         classes.put(AnnotationProcessingManager.ClassType.MULTIPART_CONFIG, multipartClasses);
      }

      return classes;
   }

   private boolean isDefaultMultipartConfigDisabled(ClassLoader classLoader) {
      if (classLoader.getResource("/META-INF/com.sun.faces.config.disableDefaultMultipartConfig") != null) {
         WebComponentContributor.dbg("Found /META-INF/com.sun.faces.config.disableDefaultMultipartConfig, Disable MultipartConfig in FacesServlet");
         return true;
      } else {
         return false;
      }
   }

   private boolean isMultiPartAnnotatedJSFApplication(WebAppHelper helper, WebAppBean webBean, WeblogicWebAppBean wlWebAppBean, ClassLoader classLoader) {
      if (!WarUtils.isJsfApplication(webBean, wlWebAppBean, (War)helper)) {
         return false;
      } else {
         try {
            Class clz = classLoader.loadClass("javax.faces.webapp.FacesServlet");
            MultipartConfig annotation = (MultipartConfig)clz.getAnnotation(MultipartConfig.class);
            return annotation != null;
         } catch (ClassNotFoundException var7) {
            return false;
         }
      }
   }

   private boolean shouldProcessClassInWebXml(String className) {
      URL classSourceUrl = this.helper.getClassSourceUrl(className);
      return this.webFragmentManager.shouldProcessAnnotation(classSourceUrl);
   }

   private boolean shouldProcessClassOutsideWebFragments(WebAppBean webBean, String annotatedClass) {
      URL classSourceUrl = this.helper.getClassSourceUrl(annotatedClass);
      return !this.webFragmentManager.isClassFromWebFragments(classSourceUrl) || this.isClassInWebXml(annotatedClass, webBean);
   }

   private boolean isClassInWebXml(String annotatedClass, WebAppBean webBean) {
      ServletBean[] servlets = webBean.getServlets();
      int var6;
      if (servlets != null) {
         ServletBean[] var4 = servlets;
         int var5 = servlets.length;

         for(var6 = 0; var6 < var5; ++var6) {
            ServletBean servlet = var4[var6];
            if (annotatedClass.equals(servlet.getServletClass())) {
               return true;
            }
         }
      }

      FilterBean[] filters = webBean.getFilters();
      int var14;
      if (filters != null) {
         FilterBean[] var11 = filters;
         var6 = filters.length;

         for(var14 = 0; var14 < var6; ++var14) {
            FilterBean filter = var11[var14];
            if (annotatedClass.equals(filter.getFilterClass())) {
               return true;
            }
         }
      }

      ListenerBean[] listeners = webBean.getListeners();
      if (listeners != null) {
         ListenerBean[] var13 = listeners;
         var14 = listeners.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            ListenerBean listener = var13[var15];
            if (annotatedClass.equals(listener.getListenerClass())) {
               return true;
            }
         }
      }

      return false;
   }

   void beginRecording() {
      this.processor.beginRecording();
   }

   Set endRecording() {
      return this.processor.endRecording();
   }

   private static enum ClassType {
      WEB_SERVLET("@WebServlet", "javax.servlet.annotation.WebServlet"),
      WEB_FILTER("@WebFilter", "javax.servlet.annotation.WebFilter"),
      WEB_LISTENER("@WebListener", "javax.servlet.annotation.WebListener"),
      MULTIPART_CONFIG("@MultipartConfig", "javax.servlet.annotation.MultipartConfig"),
      SERVLET_SECURITY("@ServletSecurity", "javax.servlet.annotation.ServletSecurity"),
      WL_SERVLET("@WLServlet", "weblogic.servlet.annotation.WLServlet"),
      WL_FILTER("@WLFilter", "weblogic.servlet.annotation.WLFilter"),
      MANAGED_BEAN("managed bean", "javax.faces.bean.ManagedBean"),
      TAG_LISTENER("tag listener", (String)null),
      TAG_HANDLER("tag handler", (String)null),
      WEBSOCKET("@WebSocket", "weblogic.websocket.annotation.WebSocket");

      private String description;
      private String annotationClass;

      private ClassType(String description, String annotationClass) {
         this.description = description;
         this.annotationClass = annotationClass;
      }

      public String toString() {
         return this.description;
      }

      public String getAnnotationClass() {
         return this.annotationClass;
      }
   }
}
