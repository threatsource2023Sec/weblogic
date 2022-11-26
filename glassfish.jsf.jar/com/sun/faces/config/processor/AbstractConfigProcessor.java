package com.sun.faces.config.processor;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.ApplicationInstanceFactoryMetadataMap;
import com.sun.faces.config.ConfigManager;
import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.Util;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class AbstractConfigProcessor implements ConfigProcessor {
   private static final Logger LOGGER;
   private static final String CLASS_METADATA_MAP_KEY_SUFFIX = ".METADATA";

   private ApplicationInstanceFactoryMetadataMap getClassMetadataMap(ServletContext servletContext) {
      ApplicationInstanceFactoryMetadataMap classMetadataMap = (ApplicationInstanceFactoryMetadataMap)servletContext.getAttribute(this.getClassMetadataMapKey());
      if (classMetadataMap == null) {
         classMetadataMap = new ApplicationInstanceFactoryMetadataMap(new ConcurrentHashMap());
         servletContext.setAttribute(this.getClassMetadataMapKey(), classMetadataMap);
      }

      return classMetadataMap;
   }

   public void initializeClassMetadataMap(ServletContext servletContext, FacesContext facesContext) {
      this.getClassMetadataMap(servletContext);
   }

   protected String getClassMetadataMapKey() {
      return this.getClass().getName() + ".METADATA";
   }

   public void destroy(ServletContext sc, FacesContext facesContext) {
   }

   protected Application getApplication() {
      return ((ApplicationFactory)FactoryFinder.getFactory("javax.faces.application.ApplicationFactory")).getApplication();
   }

   protected String getNodeText(Node node) {
      String res = null;
      if (node != null) {
         res = node.getTextContent();
         if (res != null) {
            res = res.trim();
         }
      }

      return res != null && res.length() != 0 ? res : null;
   }

   protected Map getTextMap(List list) {
      if (list != null && !list.isEmpty()) {
         int len = list.size();
         HashMap names = new HashMap(len, 1.0F);

         for(int i = 0; i < len; ++i) {
            Node node = (Node)list.get(i);
            String textValue = this.getNodeText(node);
            if (textValue != null) {
               if (node.hasAttributes()) {
                  NamedNodeMap attributes = node.getAttributes();
                  String lang = this.getNodeText(attributes.getNamedItem("lang"));
                  if (lang == null) {
                     lang = this.getNodeText(attributes.getNamedItem("xml:lang"));
                  }

                  if (lang != null) {
                     names.put(lang, textValue);
                  } else {
                     names.put("DEFAULT", textValue);
                  }
               } else {
                  names.put("DEFAULT", textValue);
               }
            }
         }

         return names;
      } else {
         return null;
      }
   }

   protected Class findRootType(ServletContext sc, FacesContext facesContext, String source, Node sourceNode, Class[] ctorArguments) {
      try {
         Class sourceClass = this.loadClass(sc, facesContext, source, this, (Class)null);
         Class[] var7 = ctorArguments;
         int var8 = ctorArguments.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Class ctorArg = var7[var9];
            if (ReflectionUtils.lookupConstructor(sourceClass, ctorArg) != null) {
               return ctorArg;
            }
         }

         return null;
      } catch (ClassNotFoundException var11) {
         throw new ConfigurationException(this.buildMessage(MessageFormat.format("Unable to find class ''{0}''", source), sourceNode), var11);
      }
   }

   protected Object createInstance(ServletContext sc, FacesContext facesContext, String className, Node source) {
      return this.createInstance(sc, facesContext, className, (Class)null, (Object)null, source);
   }

   protected Object createInstance(ServletContext sc, FacesContext facesContext, String className, Class rootType, Object root, Node source) {
      boolean[] didPerformInjection = new boolean[]{false};
      Object result = this.createInstance(sc, facesContext, className, rootType, root, source, true, didPerformInjection);
      return result;
   }

   protected Object createInstance(ServletContext sc, FacesContext facesContext, String className, Class rootType, Object root, Node source, boolean performInjection, boolean[] didPerformInjection) {
      Object returnObject = null;
      if (className != null) {
         try {
            Class clazz = this.loadClass(sc, facesContext, className, returnObject, (Class)null);
            if (clazz != null) {
               if (returnObject == null && rootType != null && root != null) {
                  Constructor construct = ReflectionUtils.lookupConstructor(clazz, rootType);
                  if (construct != null) {
                     returnObject = construct.newInstance(root);
                  }
               }

               if (clazz != null && returnObject == null) {
                  returnObject = clazz.newInstance();
               }

               ApplicationInstanceFactoryMetadataMap classMetadataMap = this.getClassMetadataMap(sc);
               if (classMetadataMap.hasAnnotations(className) && performInjection) {
                  InjectionProvider injectionProvider = (InjectionProvider)facesContext.getAttributes().get(ConfigManager.INJECTION_PROVIDER_KEY);

                  try {
                     injectionProvider.inject(returnObject);
                  } catch (InjectionProviderException var15) {
                     LOGGER.log(Level.SEVERE, "Unable to inject instance" + className, var15);
                     throw new FacesException(var15);
                  }

                  try {
                     injectionProvider.invokePostConstruct(returnObject);
                  } catch (InjectionProviderException var14) {
                     LOGGER.log(Level.SEVERE, "Unable to invoke @PostConstruct annotated method on instance " + className, var14);
                     throw new FacesException(var14);
                  }

                  didPerformInjection[0] = true;
               }
            }
         } catch (ClassNotFoundException var16) {
            throw new ConfigurationException(this.buildMessage(MessageFormat.format("Unable to find class ''{0}''", className), source), var16);
         } catch (NoClassDefFoundError var17) {
            throw new ConfigurationException(this.buildMessage(MessageFormat.format("Class ''{0}'' is missing a runtime dependency: {1}", className, var17.toString()), source), var17);
         } catch (ClassCastException var18) {
            throw new ConfigurationException(this.buildMessage(MessageFormat.format("Class ''{0}'' is not an instance of ''{1}''", className, rootType), source), var18);
         } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | FacesException | InstantiationException var19) {
            throw new ConfigurationException(this.buildMessage(MessageFormat.format("Unable to create a new instance of ''{0}'': {1}", className, var19.toString()), source), var19);
         }
      }

      return returnObject;
   }

   protected void destroyInstance(ServletContext sc, FacesContext facesContext, String className, Object instance) {
      if (instance != null) {
         ApplicationInstanceFactoryMetadataMap classMetadataMap = this.getClassMetadataMap(sc);
         if (classMetadataMap.hasAnnotations(className)) {
            InjectionProvider injectionProvider = (InjectionProvider)facesContext.getAttributes().get(ConfigManager.INJECTION_PROVIDER_KEY);
            if (injectionProvider != null) {
               try {
                  injectionProvider.invokePreDestroy(instance);
               } catch (InjectionProviderException var8) {
                  LOGGER.log(Level.SEVERE, "Unable to invoke @PreDestroy annotated method on instance " + className, var8);
                  throw new FacesException(var8);
               }
            }
         }
      }

   }

   protected Class loadClass(ServletContext sc, FacesContext facesContext, String className, Object fallback, Class expectedType) throws ClassNotFoundException {
      ApplicationInstanceFactoryMetadataMap classMetadataMap = this.getClassMetadataMap(sc);
      Class clazz = (Class)classMetadataMap.get(className);
      if (clazz == null) {
         try {
            clazz = Util.loadClass(className, fallback);
            if (!this.isDevModeEnabled(sc, facesContext)) {
               classMetadataMap.put(className, clazz);
            } else {
               classMetadataMap.scanForAnnotations(className, clazz);
            }
         } catch (Exception var9) {
            throw new FacesException(var9.getMessage(), var9);
         }
      }

      if (expectedType != null && !expectedType.isAssignableFrom(clazz)) {
         throw new ClassCastException();
      } else {
         return clazz;
      }
   }

   protected void processAnnotations(FacesContext ctx, Class annotationType) {
      ApplicationAssociate.getInstance(ctx.getExternalContext()).getAnnotationManager().applyConfigAnnotations(ctx, annotationType, (Set)ConfigManager.getAnnotatedClasses(ctx).get(annotationType));
   }

   private String buildMessage(String cause, Node source) {
      return MessageFormat.format("\n  Source Document: {0}\n  Cause: {1}", source.getOwnerDocument().getDocumentURI(), cause);
   }

   private boolean isDevModeEnabled(ServletContext sc, FacesContext facesContext) {
      return this.getProjectStage(sc, facesContext).equals(ProjectStage.Development);
   }

   private ProjectStage getProjectStage(ServletContext sc, FacesContext facesContext) {
      String projectStageKey = AbstractConfigProcessor.class.getName() + ".PROJECTSTAGE";
      ProjectStage projectStage = (ProjectStage)sc.getAttribute(projectStageKey);
      if (projectStage == null) {
         WebConfiguration webConfig = WebConfiguration.getInstance(facesContext.getExternalContext());
         String value = webConfig.getEnvironmentEntry(WebConfiguration.WebEnvironmentEntry.ProjectStage);
         if (value != null) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "ProjectStage configured via JNDI: {0}", value);
            }
         } else {
            value = webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.JavaxFacesProjectStage);
            if (value != null && LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "ProjectStage configured via servlet context init parameter: {0}", value);
            }
         }

         if (value != null) {
            try {
               projectStage = ProjectStage.valueOf(value);
            } catch (IllegalArgumentException var8) {
               if (LOGGER.isLoggable(Level.INFO)) {
                  LOGGER.log(Level.INFO, "Unable to discern ProjectStage for value {0}.", value);
               }
            }
         }

         if (projectStage == null) {
            projectStage = ProjectStage.Production;
         }

         sc.setAttribute(projectStageKey, projectStage);
      }

      return projectStage;
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
