package com.sun.faces.config.processor;

import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.scripting.ActionListenerProxy;
import com.sun.faces.scripting.ELResolverProxy;
import com.sun.faces.scripting.NavigationHandlerProxy;
import com.sun.faces.scripting.PhaseListenerProxy;
import com.sun.faces.scripting.RendererProxy;
import com.sun.faces.scripting.ViewHandlerProxy;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.Util;
import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.el.ELResolver;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.event.ActionListener;
import javax.faces.event.PhaseListener;
import javax.faces.render.Renderer;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class AbstractConfigProcessor implements ConfigProcessor {
   private ConfigProcessor nextProcessor;

   public void setNext(ConfigProcessor nextProcessor) {
      this.nextProcessor = nextProcessor;
   }

   public void invokeNext(ServletContext sc, Document[] documents) throws Exception {
      if (this.nextProcessor != null) {
         this.nextProcessor.process(sc, documents);
      }

   }

   protected Application getApplication() {
      ApplicationFactory afactory = (ApplicationFactory)FactoryFinder.getFactory("javax.faces.application.ApplicationFactory");
      return afactory.getApplication();
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

   protected Object createInstance(String className, Node source) {
      return this.createInstance(className, (Class)null, (Object)null, source);
   }

   protected Object createInstance(String className, Class rootType, Object root, Node source) {
      Object returnObject = null;
      if (className != null) {
         try {
            Class clazz = this.loadClass(className, returnObject, rootType);
            if (clazz != null) {
               if (this.isDevModeEnabled()) {
                  Class[] interfaces = clazz.getInterfaces();
                  if (interfaces != null) {
                     Class[] arr$ = interfaces;
                     int len$ = interfaces.length;

                     for(int i$ = 0; i$ < len$; ++i$) {
                        Class c = arr$[i$];
                        if ("groovy.lang.GroovyObject".equals(c.getName())) {
                           returnObject = this.createScriptProxy(rootType, className, root);
                           break;
                        }
                     }
                  }
               }

               if (returnObject == null && rootType != null && root != null) {
                  Constructor construct = ReflectionUtils.lookupConstructor(clazz, rootType);
                  if (construct != null) {
                     returnObject = construct.newInstance(root);
                  }
               }

               if (clazz != null && returnObject == null) {
                  returnObject = clazz.newInstance();
               }
            }
         } catch (ClassNotFoundException var12) {
            throw new ConfigurationException(this.buildMessage(MessageFormat.format("Unable to find class ''{0}''", className), source));
         } catch (NoClassDefFoundError var13) {
            throw new ConfigurationException(this.buildMessage(MessageFormat.format("Class ''{0}'' is missing a runtime dependency: {1}", className, var13.toString()), source), var13);
         } catch (ClassCastException var14) {
            throw new ConfigurationException(this.buildMessage(MessageFormat.format("Class ''{0}'' is not an instance of ''{1}''", className, rootType), source));
         } catch (Exception var15) {
            throw new ConfigurationException(this.buildMessage(MessageFormat.format("Unable to create a new instance of ''{0}'': {1}", className, var15.toString()), source), var15);
         }
      }

      return returnObject;
   }

   protected Class loadClass(String className, Object fallback, Class expectedType) throws ClassNotFoundException {
      Class clazz = Util.loadClass(className, fallback);
      if (expectedType != null && !expectedType.isAssignableFrom(clazz)) {
         throw new ClassCastException();
      } else {
         return clazz;
      }
   }

   private String buildMessage(String cause, Node source) {
      return MessageFormat.format("\n  Source Document: {0}\n  Cause: {1}", source.getOwnerDocument().getDocumentURI(), cause);
   }

   private Object createScriptProxy(Class artifactType, String scriptName, Object root) {
      if (Renderer.class.equals(artifactType)) {
         return new RendererProxy(scriptName);
      } else if (PhaseListener.class.equals(artifactType)) {
         return new PhaseListenerProxy(scriptName);
      } else if (ViewHandler.class.equals(artifactType)) {
         return new ViewHandlerProxy(scriptName, (ViewHandler)root);
      } else if (NavigationHandler.class.equals(artifactType)) {
         return new NavigationHandlerProxy(scriptName, (NavigationHandler)root);
      } else if (ActionListener.class.equals(artifactType)) {
         return new ActionListenerProxy(scriptName, (ActionListener)root);
      } else {
         return ELResolver.class.equals(artifactType) ? new ELResolverProxy(scriptName) : null;
      }
   }

   private boolean isDevModeEnabled() {
      WebConfiguration webconfig = WebConfiguration.getInstance();
      return webconfig != null && webconfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DevelopmentMode);
   }
}
