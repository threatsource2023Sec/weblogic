package com.sun.faces.config.manager.spi;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.spi.AnnotationProvider;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.FacesBehavior;
import javax.faces.convert.FacesConverter;
import javax.faces.event.NamedEvent;
import javax.faces.render.FacesBehaviorRenderer;
import javax.faces.render.FacesRenderer;
import javax.faces.validator.FacesValidator;
import javax.faces.view.facelets.FaceletsResourceResolver;
import javax.servlet.ServletContext;

public abstract class AnnotationScanner extends AnnotationProvider {
   private static final Logger LOGGER;
   private static final String WILDCARD = "*";
   protected static final Set FACES_ANNOTATIONS;
   protected static final Set FACES_ANNOTATION_TYPE;
   private boolean isAnnotationScanPackagesSet = false;
   private String[] webInfClassesPackages;
   private Map classpathPackages;

   public AnnotationScanner(ServletContext sc) {
      super(sc);
      WebConfiguration webConfig = WebConfiguration.getInstance(sc);
      this.initializeAnnotationScanPackages(sc, webConfig);
   }

   private void initializeAnnotationScanPackages(ServletContext sc, WebConfiguration webConfig) {
      if (webConfig.isSet(WebConfiguration.WebContextInitParameter.AnnotationScanPackages)) {
         this.isAnnotationScanPackagesSet = true;
         this.classpathPackages = new HashMap(4);
         this.webInfClassesPackages = new String[0];
         String[] options = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.AnnotationScanPackages, "\\s+");
         List packages = new ArrayList(4);
         String[] var5 = options;
         int var6 = options.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String option = var5[var7];
            if (option.length() != 0) {
               if (option.startsWith("jar:")) {
                  String[] parts = Util.split(sc, option, ":");
                  if (parts.length != 3) {
                     if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, "jsf.annotation.scanner.configuration.invalid", new String[]{WebConfiguration.WebContextInitParameter.AnnotationScanPackages.getQualifiedName(), option});
                     }
                  } else if ("*".equals(parts[1]) && !this.classpathPackages.containsKey("*")) {
                     this.classpathPackages.clear();
                     this.classpathPackages.put("*", this.normalizeJarPackages(Util.split(sc, parts[2], ",")));
                  } else if ("*".equals(parts[1]) && this.classpathPackages.containsKey("*")) {
                     if (LOGGER.isLoggable(Level.WARNING)) {
                        LOGGER.log(Level.WARNING, "jsf.annotation.scanner.configuration.duplicate.wildcard", new String[]{WebConfiguration.WebContextInitParameter.AnnotationScanPackages.getQualifiedName(), option});
                     }
                  } else if (!this.classpathPackages.containsKey("*")) {
                     this.classpathPackages.put(parts[1], this.normalizeJarPackages(Util.split(sc, parts[2], ",")));
                  }
               } else if ("*".equals(option) && !packages.contains("*")) {
                  packages.clear();
                  packages.add("*");
               } else if (!packages.contains("*")) {
                  packages.add(option);
               }
            }
         }

         this.webInfClassesPackages = (String[])packages.toArray(new String[packages.size()]);
      }
   }

   private String[] normalizeJarPackages(String[] packages) {
      if (packages.length == 0) {
         return packages;
      } else {
         List normalizedPackages = new ArrayList(packages.length);
         String[] var3 = packages;
         int var4 = packages.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String pkg = var3[var5];
            if ("*".equals(pkg)) {
               normalizedPackages.clear();
               normalizedPackages.add("*");
               break;
            }

            normalizedPackages.add(pkg);
         }

         return (String[])normalizedPackages.toArray(new String[normalizedPackages.size()]);
      }
   }

   protected boolean processJar(String entry) {
      return this.classpathPackages == null || this.classpathPackages.containsKey(entry) || this.classpathPackages.containsKey("*");
   }

   protected boolean processClass(String candidate) {
      return this.processClass(candidate, this.webInfClassesPackages);
   }

   protected boolean processClass(String candidate, String[] packages) {
      if (packages == null) {
         return true;
      } else {
         String[] var3 = packages;
         int var4 = packages.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String packageName = var3[var5];
            if (candidate.startsWith(packageName) || "*".equals(packageName)) {
               return true;
            }
         }

         return false;
      }
   }

   protected Map processClassList(Set classList) {
      Map annotatedClasses = null;
      if (classList.size() > 0) {
         annotatedClasses = new HashMap(6, 1.0F);
         Iterator var3 = classList.iterator();

         while(var3.hasNext()) {
            String className = (String)var3.next();

            try {
               Class clazz = Util.loadClass(className, this);
               Annotation[] annotations = clazz.getAnnotations();
               Annotation[] var7 = annotations;
               int var8 = annotations.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  Annotation annotation = var7[var9];
                  Class annoType = annotation.annotationType();
                  if (FACES_ANNOTATION_TYPE.contains(annoType)) {
                     Set classes = (Set)annotatedClasses.get(annoType);
                     if (classes == null) {
                        classes = new HashSet();
                        annotatedClasses.put(annoType, classes);
                     }

                     ((Set)classes).add(clazz);
                  }
               }
            } catch (ClassNotFoundException var13) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Unable to load annotated class: {0}", className);
                  LOGGER.log(Level.SEVERE, "", var13);
               }
            } catch (NoClassDefFoundError var14) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "Unable to load annotated class: {0}, reason: {1}", new Object[]{className, var14.toString()});
               }
            }
         }
      }

      return (Map)(annotatedClasses != null ? annotatedClasses : Collections.emptyMap());
   }

   protected boolean isAnnotationScanPackagesSet() {
      return this.isAnnotationScanPackagesSet;
   }

   protected Map getClasspathPackages() {
      return this.classpathPackages;
   }

   protected String[] getWebInfClassesPackages() {
      return this.webInfClassesPackages;
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
      HashSet annotations = new HashSet(8, 1.0F);
      Collections.addAll(annotations, new String[]{"Ljavax/faces/component/FacesComponent;", "Ljavax/faces/convert/FacesConverter;", "Ljavax/faces/validator/FacesValidator;", "Ljavax/faces/render/FacesRenderer;", "Ljavax/faces/bean/ManagedBean;", "Ljavax/faces/event/NamedEvent;", "Ljavax/faces/component/behavior/FacesBehavior;", "Ljavax/faces/render/FacesBehaviorRenderer;", "Ljavax/faces/view/facelets/FaceletsResourceResolver;", "javax.faces.component.FacesComponent", "javax.faces.convert.FacesConverter", "javax.faces.validator.FacesValidator", "javax.faces.render.FacesRenderer", "javax.faces.bean.ManagedBean", "javax.faces.event.NamedEvent", "javax.faces.component.behavior.FacesBehavior", "javax.faces.render.FacesBehaviorRenderer", "javax.faces.view.facelets.FaceletsResourceResolver"});
      FACES_ANNOTATIONS = Collections.unmodifiableSet(annotations);
      HashSet annotationInstances = new HashSet(8, 1.0F);
      Collections.addAll(annotationInstances, new Class[]{FacesComponent.class, FacesConverter.class, FacesValidator.class, FacesRenderer.class, ManagedBean.class, NamedEvent.class, FacesBehavior.class, FacesBehaviorRenderer.class, FaceletsResourceResolver.class});
      FACES_ANNOTATION_TYPE = Collections.unmodifiableSet(annotationInstances);
   }
}
