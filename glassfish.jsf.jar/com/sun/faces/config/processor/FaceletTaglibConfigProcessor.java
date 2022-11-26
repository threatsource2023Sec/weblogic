package com.sun.faces.config.processor;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.facelets.compiler.Compiler;
import com.sun.faces.facelets.tag.TagLibrary;
import com.sun.faces.facelets.tag.TagLibraryImpl;
import com.sun.faces.facelets.tag.jsf.CompositeComponentTagLibrary;
import com.sun.faces.facelets.util.ReflectionUtil;
import com.sun.faces.util.FacesLogger;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FaceletTaglibConfigProcessor extends AbstractConfigProcessor {
   private static final Logger LOGGER;
   private static final String LIBRARY_CLASS = "library-class";
   private static final String TAGLIB_NAMESPACE = "namespace";
   private static final String TAG = "tag";
   private static final String FUNCTION = "function";
   private static final String TAG_NAME = "tag-name";
   private static final String COMPONENT = "component";
   private static final String VALIDATOR = "validator";
   private static final String CONVERTER = "converter";
   private static final String BEHAVIOR = "behavior";
   private static final String SOURCE = "source";
   private static final String RESOURCE_ID = "resource-id";
   private static final String HANDLER_CLASS = "handler-class";
   private static final String VALIDATOR_ID = "validator-id";
   private static final String CONVERTER_ID = "converter-id";
   private static final String BEHAVIOR_ID = "behavior-id";
   private static final String COMPONENT_TYPE = "component-type";
   private static final String RENDERER_TYPE = "renderer-type";
   private static final String FUNCTION_NAME = "function-name";
   private static final String FUNCTION_CLASS = "function-class";
   private static final String FUNCTION_SIGNATURE = "function-signature";
   private static final String COMPOSITE_LIBRARY_NAME = "composite-library-name";

   public void process(ServletContext sc, FacesContext facesContext, DocumentInfo[] documentInfos) throws Exception {
      ApplicationAssociate associate = ApplicationAssociate.getInstance(facesContext.getExternalContext());
      Compiler compiler = associate.getCompiler();
      int i = 0;

      for(int length = documentInfos.length; i < length; ++i) {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("Processing facelet-taglibrary document: ''{0}''", documentInfos[i].getSourceURI()));
         }

         Document document = documentInfos[i].getDocument();
         String namespace = document.getDocumentElement().getNamespaceURI();
         Element documentElement = document.getDocumentElement();
         NodeList libraryClass = documentElement.getElementsByTagNameNS(namespace, "library-class");
         if (libraryClass != null && libraryClass.getLength() > 0) {
            this.processTaglibraryClass(sc, facesContext, libraryClass, compiler);
         } else {
            this.processTagLibrary(sc, facesContext, documentElement, namespace, compiler);
         }
      }

   }

   private void processTaglibraryClass(ServletContext servletContext, FacesContext facesContext, NodeList libraryClass, Compiler compiler) {
      Node n = libraryClass.item(0);
      String className = this.getNodeText(n);
      TagLibrary taglib = (TagLibrary)this.createInstance(servletContext, facesContext, className, n);
      compiler.addTagLibrary(taglib);
   }

   private void processTagLibrary(ServletContext sc, FacesContext facesContext, Element documentElement, String namespace, Compiler compiler) {
      NodeList children = documentElement.getChildNodes();
      if (children != null && children.getLength() > 0) {
         String taglibNamespace = null;
         String compositeLibraryName = null;
         int i = 0;

         for(int ilen = children.getLength(); i < ilen; ++i) {
            Node n = children.item(i);
            if (n.getLocalName() != null) {
               switch (n.getLocalName()) {
                  case "namespace":
                     taglibNamespace = this.getNodeText(n);
                     break;
                  case "composite-library-name":
                     compositeLibraryName = this.getNodeText(n);
               }
            }
         }

         Object taglibrary;
         if (compositeLibraryName != null) {
            taglibrary = new CompositeComponentTagLibrary(taglibNamespace, compositeLibraryName);
            compiler.addTagLibrary((TagLibrary)taglibrary);
         } else {
            taglibrary = new TagLibraryImpl(taglibNamespace);
         }

         NodeList tags = documentElement.getElementsByTagNameNS(namespace, "tag");
         this.processTags(sc, facesContext, documentElement, tags, (TagLibraryImpl)taglibrary);
         NodeList functions = documentElement.getElementsByTagNameNS(namespace, "function");
         this.processFunctions(sc, facesContext, functions, (TagLibraryImpl)taglibrary);
         compiler.addTagLibrary((TagLibrary)taglibrary);
      }

   }

   private void processTags(ServletContext servletContext, FacesContext facesContext, Element documentElement, NodeList tags, TagLibraryImpl taglibrary) {
      if (tags != null && tags.getLength() > 0) {
         int i = 0;

         for(int ilen = tags.getLength(); i < ilen; ++i) {
            Node tagNode = tags.item(i);
            NodeList children = tagNode.getChildNodes();
            String tagName = null;
            NodeList component = null;
            NodeList converter = null;
            NodeList validator = null;
            NodeList behavior = null;
            Node source = null;
            Node handlerClass = null;
            int j = 0;

            for(int jlen = children.getLength(); j < jlen; ++j) {
               Node n = children.item(j);
               if (n.getLocalName() != null) {
                  switch (n.getLocalName()) {
                     case "tag-name":
                        tagName = this.getNodeText(n);
                        break;
                     case "component":
                        component = n.getChildNodes();
                        break;
                     case "converter":
                        converter = n.getChildNodes();
                        break;
                     case "validator":
                        validator = n.getChildNodes();
                        break;
                     case "behavior":
                        behavior = n.getChildNodes();
                        break;
                     case "source":
                        source = n;
                        break;
                     case "handler-class":
                        handlerClass = n;
                  }
               }
            }

            if (component != null) {
               this.processComponent(servletContext, facesContext, documentElement, component, taglibrary, tagName);
            } else if (converter != null) {
               this.processConverter(servletContext, facesContext, converter, taglibrary, tagName);
            } else if (validator != null) {
               this.processValidator(servletContext, facesContext, validator, taglibrary, tagName);
            } else if (behavior != null) {
               this.processBehavior(servletContext, facesContext, behavior, taglibrary, tagName);
            } else if (source != null) {
               this.processSource(documentElement, source, taglibrary, tagName);
            } else if (handlerClass != null) {
               this.processHandlerClass(servletContext, facesContext, handlerClass, taglibrary, tagName);
            }
         }
      }

   }

   private void processBehavior(ServletContext sc, FacesContext facesContext, NodeList behavior, TagLibraryImpl taglibrary, String tagName) {
      if (behavior != null && behavior.getLength() > 0) {
         String behaviorId = null;
         String handlerClass = null;
         int i = 0;

         for(int ilen = behavior.getLength(); i < ilen; ++i) {
            Node n = behavior.item(i);
            if (n.getLocalName() != null) {
               switch (n.getLocalName()) {
                  case "behavior-id":
                     behaviorId = this.getNodeText(n);
                     break;
                  case "handler-class":
                     handlerClass = this.getNodeText(n);
               }
            }
         }

         if (handlerClass != null) {
            try {
               Class clazz = this.loadClass(sc, facesContext, handlerClass, this, (Class)null);
               taglibrary.putBehavior(tagName, behaviorId, clazz);
            } catch (ClassNotFoundException var13) {
               throw new ConfigurationException(var13);
            }
         } else {
            taglibrary.putBehavior(tagName, behaviorId);
         }
      }

   }

   private void processHandlerClass(ServletContext sc, FacesContext facesContext, Node handlerClass, TagLibraryImpl taglibrary, String name) {
      String className = this.getNodeText(handlerClass);
      if (className == null) {
         throw new ConfigurationException("The tag named " + name + " from namespace " + taglibrary.getNamespace() + " has a null handler-class defined");
      } else {
         try {
            try {
               Class clazz = this.loadClass(sc, facesContext, className, this, (Class)null);
               taglibrary.putTagHandler(name, clazz);
            } catch (NoClassDefFoundError var10) {
               String message = var10.toString();
               if (!message.contains("com/sun/facelets/") && !message.contains("com.sun.facelets.")) {
                  throw var10;
               }

               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "jsf.config.legacy.facelet.warning", new Object[]{handlerClass});
               }
            }

         } catch (ClassNotFoundException var11) {
            throw new ConfigurationException(var11);
         }
      }
   }

   private void processSource(Element documentElement, Node source, TagLibraryImpl taglibrary, String name) {
      String docURI = documentElement.getOwnerDocument().getDocumentURI();
      String s = this.getNodeText(source);

      try {
         URL url = new URL(new URL(docURI), s);
         taglibrary.putUserTag(name, url);
      } catch (MalformedURLException var8) {
         throw new FacesException(var8);
      }
   }

   private void processResourceId(Element documentElement, Node compositeSource, TagLibraryImpl taglibrary, String name) {
      String resourceId = this.getNodeText(compositeSource);
      taglibrary.putCompositeComponentTag(name, resourceId);
   }

   private void processValidator(ServletContext sc, FacesContext facesContext, NodeList validator, TagLibraryImpl taglibrary, String name) {
      if (validator != null && validator.getLength() > 0) {
         String validatorId = null;
         String handlerClass = null;
         int i = 0;

         for(int ilen = validator.getLength(); i < ilen; ++i) {
            Node n = validator.item(i);
            if (n.getLocalName() != null) {
               switch (n.getLocalName()) {
                  case "validator-id":
                     validatorId = this.getNodeText(n);
                     break;
                  case "handler-class":
                     handlerClass = this.getNodeText(n);
               }
            }
         }

         if (handlerClass != null) {
            try {
               Class clazz = this.loadClass(sc, facesContext, handlerClass, this, (Class)null);
               taglibrary.putValidator(name, validatorId, clazz);
            } catch (NoClassDefFoundError var13) {
               String message = var13.toString();
               if (!message.contains("com/sun/facelets/") && !message.contains("com.sun.facelets.")) {
                  throw var13;
               }

               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "jsf.config.legacy.facelet.warning", new Object[]{handlerClass});
               }
            } catch (ClassNotFoundException var14) {
               throw new ConfigurationException(var14);
            }
         } else {
            taglibrary.putValidator(name, validatorId);
         }
      }

   }

   private void processConverter(ServletContext sc, FacesContext facesContext, NodeList converter, TagLibraryImpl taglibrary, String name) {
      if (converter != null && converter.getLength() > 0) {
         String converterId = null;
         String handlerClass = null;
         int i = 0;

         for(int ilen = converter.getLength(); i < ilen; ++i) {
            Node n = converter.item(i);
            if (n.getLocalName() != null) {
               switch (n.getLocalName()) {
                  case "converter-id":
                     converterId = this.getNodeText(n);
                     break;
                  case "handler-class":
                     handlerClass = this.getNodeText(n);
               }
            }
         }

         if (handlerClass != null) {
            try {
               Class clazz = this.loadClass(sc, facesContext, handlerClass, this, (Class)null);
               taglibrary.putConverter(name, converterId, clazz);
            } catch (NoClassDefFoundError var13) {
               String message = var13.toString();
               if (!message.contains("com/sun/facelets/") && !message.contains("com.sun.facelets.")) {
                  throw var13;
               }

               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "jsf.config.legacy.facelet.warning", new Object[]{handlerClass});
               }
            } catch (ClassNotFoundException var14) {
               throw new ConfigurationException(var14);
            }
         } else {
            taglibrary.putConverter(name, converterId);
         }
      }

   }

   private void processComponent(ServletContext sc, FacesContext facesContext, Element documentElement, NodeList component, TagLibraryImpl taglibrary, String name) {
      if (component != null && component.getLength() > 0) {
         String componentType = null;
         String rendererType = null;
         String handlerClass = null;
         Node resourceId = null;
         int i = 0;

         for(int ilen = component.getLength(); i < ilen; ++i) {
            Node n = component.item(i);
            if (n.getLocalName() != null) {
               switch (n.getLocalName()) {
                  case "component-type":
                     componentType = this.getNodeText(n);
                     break;
                  case "renderer-type":
                     rendererType = this.getNodeText(n);
                     break;
                  case "handler-class":
                     handlerClass = this.getNodeText(n);
                     break;
                  case "resource-id":
                     resourceId = n;
               }
            }
         }

         if (handlerClass != null) {
            try {
               Class clazz = this.loadClass(sc, facesContext, handlerClass, this, (Class)null);
               taglibrary.putComponent(name, componentType, rendererType, clazz);
            } catch (NoClassDefFoundError var16) {
               String message = var16.toString();
               if (!message.contains("com/sun/facelets/") && !message.contains("com.sun.facelets.")) {
                  throw var16;
               }

               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.log(Level.WARNING, "jsf.config.legacy.facelet.warning", new Object[]{handlerClass});
               }
            } catch (ClassNotFoundException var17) {
               throw new ConfigurationException(var17);
            }
         } else if (resourceId != null) {
            this.processResourceId(documentElement, resourceId, taglibrary, name);
         } else {
            taglibrary.putComponent(name, componentType, rendererType);
         }
      }

   }

   private void processFunctions(ServletContext sc, FacesContext facesContext, NodeList functions, TagLibraryImpl taglibrary) {
      if (functions != null && functions.getLength() > 0) {
         int i = 0;

         for(int ilen = functions.getLength(); i < ilen; ++i) {
            NodeList children = functions.item(i).getChildNodes();
            String functionName = null;
            String functionClass = null;
            String functionSignature = null;
            int j = 0;

            for(int jlen = children.getLength(); j < jlen; ++j) {
               Node n = children.item(j);
               if (n.getLocalName() != null) {
                  switch (n.getLocalName()) {
                     case "function-name":
                        functionName = this.getNodeText(n);
                        break;
                     case "function-class":
                        functionClass = this.getNodeText(n);
                        break;
                     case "function-signature":
                        functionSignature = this.getNodeText(n);
                  }
               }
            }

            try {
               Class clazz = this.loadClass(sc, facesContext, functionClass, this, (Class)null);
               Method m = createMethod(clazz, functionSignature);
               taglibrary.putFunction(functionName, m);
            } catch (Exception var16) {
               throw new ConfigurationException(var16);
            }
         }
      }

   }

   private static Method createMethod(Class type, String signatureParam) throws Exception {
      String signature = signatureParam.replaceAll("\\s+", " ");
      int pos = signature.indexOf(32);
      if (pos == -1) {
         throw new Exception("Must Provide Return Type: " + signature);
      } else {
         int pos2 = signature.indexOf(40, pos + 1);
         if (pos2 == -1) {
            throw new Exception("Must provide a method name, followed by '(': " + signature);
         } else {
            String mn = signature.substring(pos + 1, pos2).trim();
            pos = signature.indexOf(41, pos2 + 1);
            if (pos == -1) {
               throw new Exception("Must close parentheses, ')' missing: " + signature);
            } else {
               String[] ps = signature.substring(pos2 + 1, pos).trim().split(",");
               Class[] pc;
               if (ps.length == 1 && "".equals(ps[0])) {
                  pc = new Class[0];
               } else {
                  pc = new Class[ps.length];

                  for(int i = 0; i < pc.length; ++i) {
                     pc[i] = ReflectionUtil.forName(ps[i].trim());
                  }
               }

               try {
                  return type.getMethod(mn, pc);
               } catch (NoSuchMethodException var9) {
                  throw new Exception("No Function Found on type: " + type.getName() + " with signature: " + signature);
               }
            }
         }
      }
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
