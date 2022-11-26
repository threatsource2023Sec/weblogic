package com.sun.faces.facelets.tag;

import com.sun.faces.facelets.tag.jsf.CompositeComponentTagHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.faces.view.Location;
import javax.faces.view.facelets.BehaviorConfig;
import javax.faces.view.facelets.BehaviorHandler;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.ConverterConfig;
import javax.faces.view.facelets.ConverterHandler;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.FaceletHandler;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttributes;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;
import javax.faces.view.facelets.TagHandler;
import javax.faces.view.facelets.ValidatorConfig;
import javax.faces.view.facelets.ValidatorHandler;

public abstract class AbstractTagLibrary implements TagLibrary {
   private final Map factories;
   private final String namespace;
   private final Map functions;

   public AbstractTagLibrary(String namespace) {
      this.namespace = namespace;
      this.factories = new HashMap();
      this.functions = new HashMap();
   }

   protected final void addComponent(String name, String componentType, String rendererType) {
      this.factories.put(name, new ComponentHandlerFactory(componentType, rendererType));
   }

   protected final void addComponent(String name, String componentType, String rendererType, Class handlerType) {
      this.factories.put(name, new UserComponentHandlerFactory(componentType, rendererType, handlerType));
   }

   protected final void addConverter(String name, String converterId) {
      this.factories.put(name, new ConverterHandlerFactory(converterId));
   }

   protected final void addConverter(String name, String converterId, Class type) {
      this.factories.put(name, new UserConverterHandlerFactory(converterId, type));
   }

   protected final void addValidator(String name, String validatorId) {
      this.factories.put(name, new ValidatorHandlerFactory(validatorId));
   }

   protected final void addValidator(String name, String validatorId, Class type) {
      this.factories.put(name, new UserValidatorHandlerFactory(validatorId, type));
   }

   protected final void addBehavior(String name, String behaviorId) {
      this.factories.put(name, new BehaviorHandlerFactory(behaviorId));
   }

   protected final void addBehavior(String name, String behaviorId, Class type) {
      this.factories.put(name, new UserBehaviorHandlerFactory(behaviorId, type));
   }

   protected final void addTagHandler(String name, Class handlerType) {
      this.factories.put(name, new HandlerFactory(handlerType));
   }

   protected final void addUserTag(String name, URL source) {
      this.factories.put(name, new UserTagFactory(source));
   }

   protected final void addCompositeComponentTag(String name, String resourceId) {
      this.factories.put(name, new CompositeComponentTagFactory(resourceId));
   }

   protected final void addFunction(String name, Method method) {
      this.functions.put(name, method);
   }

   public boolean containsNamespace(String ns, Tag t) {
      return this.namespace.equals(ns);
   }

   public boolean containsTagHandler(String ns, String localName) {
      return this.namespace.equals(ns) && this.factories.containsKey(localName);
   }

   public TagHandler createTagHandler(String ns, String localName, TagConfig tag) throws FacesException {
      if (this.namespace.equals(ns)) {
         TagHandlerFactory f = (TagHandlerFactory)this.factories.get(localName);
         if (f != null) {
            return f.createHandler(tag);
         }
      }

      return null;
   }

   public boolean containsFunction(String ns, String name) {
      return this.namespace.equals(ns) ? this.functions.containsKey(name) : false;
   }

   public Method createFunction(String ns, String name) {
      return this.namespace.equals(ns) ? (Method)this.functions.get(name) : null;
   }

   public boolean equals(Object obj) {
      return obj instanceof TagLibrary && obj.hashCode() == this.hashCode();
   }

   public int hashCode() {
      return this.namespace.hashCode();
   }

   public String getNamespace() {
      return this.namespace;
   }

   private static class UserBehaviorHandlerFactory implements TagHandlerFactory {
      private static final Class[] CONS_SIG = new Class[]{BehaviorConfig.class};
      protected final String behaviorId;
      protected final Class type;
      protected final Constructor constructor;

      public UserBehaviorHandlerFactory(String behaviorId, Class type) {
         this.behaviorId = behaviorId;
         this.type = type;

         try {
            this.constructor = this.type.getConstructor(CONS_SIG);
         } catch (SecurityException | NoSuchMethodException var4) {
            throw new FaceletException("Must have a Constructor that takes in a BehaviorConfig", var4);
         }
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         try {
            BehaviorConfig ccfg = new BehaviorConfigWrapper(cfg, this.behaviorId);
            return (TagHandler)this.constructor.newInstance(ccfg);
         } catch (InvocationTargetException var3) {
            throw new FaceletException(var3.getCause().getMessage(), var3.getCause().getCause());
         } catch (IllegalAccessException | IllegalArgumentException | InstantiationException var4) {
            throw new FaceletException("Error Instantiating BehaviorHandler: " + this.type.getName(), var4);
         }
      }
   }

   private static class UserValidatorHandlerFactory implements TagHandlerFactory {
      private static final Class[] CONS_SIG = new Class[]{ValidatorConfig.class};
      protected final String validatorId;
      protected final Class type;
      protected final Constructor constructor;

      public UserValidatorHandlerFactory(String validatorId, Class type) {
         this.validatorId = validatorId;
         this.type = type;

         try {
            this.constructor = this.type.getConstructor(CONS_SIG);
         } catch (SecurityException | NoSuchMethodException var4) {
            throw new FaceletException("Must have a Constructor that takes in a ValidatorConfig", var4);
         }
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         try {
            ValidatorConfig ccfg = new ValidatorConfigWrapper(cfg, this.validatorId);
            return (TagHandler)this.constructor.newInstance(ccfg);
         } catch (InvocationTargetException var3) {
            throw new FaceletException(var3.getCause().getMessage(), var3.getCause().getCause());
         } catch (IllegalAccessException | IllegalArgumentException | InstantiationException var4) {
            throw new FaceletException("Error Instantiating ValidatorHandler: " + this.type.getName(), var4);
         }
      }
   }

   private static class UserConverterHandlerFactory implements TagHandlerFactory {
      private static final Class[] CONS_SIG = new Class[]{ConverterConfig.class};
      protected final String converterId;
      protected final Class type;
      protected final Constructor constructor;

      public UserConverterHandlerFactory(String converterId, Class type) {
         this.converterId = converterId;
         this.type = type;

         try {
            this.constructor = this.type.getConstructor(CONS_SIG);
         } catch (SecurityException | NoSuchMethodException var4) {
            throw new FaceletException("Must have a Constructor that takes in a ConverterConfig", var4);
         }
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         try {
            ConverterConfig ccfg = new ConverterConfigWrapper(cfg, this.converterId);
            return (TagHandler)this.constructor.newInstance(ccfg);
         } catch (InvocationTargetException var3) {
            throw new FaceletException(var3.getCause().getMessage(), var3.getCause().getCause());
         } catch (IllegalAccessException | IllegalArgumentException | InstantiationException var4) {
            throw new FaceletException("Error Instantiating ConverterHandler: " + this.type.getName(), var4);
         }
      }
   }

   private static final class BehaviorHandlerFactory implements TagHandlerFactory {
      private final String behaviorId;

      public BehaviorHandlerFactory(String behaviorId) {
         this.behaviorId = behaviorId;
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         return new BehaviorHandler(new BehaviorConfigWrapper(cfg, this.behaviorId));
      }
   }

   private static class ConverterHandlerFactory implements TagHandlerFactory {
      protected final String converterId;

      public ConverterHandlerFactory(String converterId) {
         this.converterId = converterId;
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         return new ConverterHandler(new ConverterConfigWrapper(cfg, this.converterId));
      }
   }

   private static class ValidatorHandlerFactory implements TagHandlerFactory {
      protected final String validatorId;

      public ValidatorHandlerFactory(String validatorId) {
         this.validatorId = validatorId;
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         return new ValidatorHandler(new ValidatorConfigWrapper(cfg, this.validatorId));
      }
   }

   private static class UserComponentHandlerFactory implements TagHandlerFactory {
      private static final Class[] CONS_SIG = new Class[]{ComponentConfig.class};
      protected final String componentType;
      protected final String renderType;
      protected final Class type;
      protected final Constructor constructor;

      public UserComponentHandlerFactory(String componentType, String renderType, Class type) {
         this.componentType = componentType;
         this.renderType = renderType;
         this.type = type;

         try {
            this.constructor = this.type.getConstructor(CONS_SIG);
         } catch (SecurityException | NoSuchMethodException var5) {
            throw new FaceletException("Must have a Constructor that takes in a ComponentConfig", var5);
         }
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         try {
            ComponentConfig ccfg = new ComponentConfigWrapper(cfg, this.componentType, this.renderType);
            return (TagHandler)this.constructor.newInstance(ccfg);
         } catch (InvocationTargetException var3) {
            throw new FaceletException(var3.getCause().getMessage(), var3.getCause().getCause());
         } catch (IllegalAccessException | IllegalArgumentException | InstantiationException var4) {
            throw new FaceletException("Error Instantiating ComponentHandler: " + this.type.getName(), var4);
         }
      }
   }

   private static class ComponentHandlerFactory implements TagHandlerFactory {
      protected final String componentType;
      protected final String renderType;

      public ComponentHandlerFactory(String componentType, String renderType) {
         this.componentType = componentType;
         this.renderType = renderType;
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         ComponentConfig ccfg = new ComponentConfigWrapper(cfg, this.componentType, this.renderType);
         return new ComponentHandler(ccfg);
      }
   }

   private static class CompositeComponentTagFactory implements TagHandlerFactory {
      protected final String resourceId;

      public CompositeComponentTagFactory(String resourceId) {
         this.resourceId = resourceId;
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         ComponentConfig componentConfig = new ComponentConfigWrapper(cfg, "javax.faces.NamingContainer", "javax.faces.Composite");
         ResourceHandler resourceHandler = FacesContext.getCurrentInstance().getApplication().getResourceHandler();
         TagHandler result = null;
         Resource resource = resourceHandler.createResourceFromId(this.resourceId);
         if (null != resource) {
            result = new CompositeComponentTagHandler(resource, componentConfig);
            return result;
         } else {
            Location loc = new Location(this.resourceId, 0, 0);
            Tag tag = new Tag(loc, "", "", "", (TagAttributes)null);
            throw new TagException(tag, "Cannot create composite component tag handler for composite-source element in taglib.xml file");
         }
      }
   }

   private static class UserTagFactory implements TagHandlerFactory {
      protected final URL location;

      public UserTagFactory(URL location) {
         this.location = location;
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         return new UserTagHandler(cfg, this.location);
      }
   }

   protected static class ComponentConfigWrapper implements ComponentConfig {
      protected final TagConfig parent;
      protected final String componentType;
      protected final String rendererType;

      public ComponentConfigWrapper(TagConfig parent, String componentType, String rendererType) {
         this.parent = parent;
         this.componentType = componentType;
         this.rendererType = rendererType;
      }

      public String getComponentType() {
         return this.componentType;
      }

      public String getRendererType() {
         return this.rendererType;
      }

      public FaceletHandler getNextHandler() {
         return this.parent.getNextHandler();
      }

      public Tag getTag() {
         return this.parent.getTag();
      }

      public String getTagId() {
         return this.parent.getTagId();
      }
   }

   private static class HandlerFactory implements TagHandlerFactory {
      private static final Class[] CONSTRUCTOR_SIG = new Class[]{TagConfig.class};
      protected final Class handlerType;

      public HandlerFactory(Class handlerType) {
         this.handlerType = handlerType;
      }

      public TagHandler createHandler(TagConfig cfg) throws FacesException, ELException {
         try {
            return (TagHandler)this.handlerType.getConstructor(CONSTRUCTOR_SIG).newInstance(cfg);
         } catch (InvocationTargetException var4) {
            Throwable t = var4.getCause();
            if (t instanceof FacesException) {
               throw (FacesException)t;
            } else if (t instanceof ELException) {
               throw (ELException)t;
            } else {
               throw new FacesException("Error Instantiating: " + this.handlerType.getName(), t);
            }
         } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException var5) {
            throw new FacesException("Error Instantiating: " + this.handlerType.getName(), var5);
         }
      }
   }

   private static final class BehaviorConfigWrapper implements BehaviorConfig {
      private final TagConfig parent;
      private final String behaviorId;

      public BehaviorConfigWrapper(TagConfig parent, String behaviorId) {
         this.parent = parent;
         this.behaviorId = behaviorId;
      }

      public String getBehaviorId() {
         return this.behaviorId;
      }

      public FaceletHandler getNextHandler() {
         return this.parent.getNextHandler();
      }

      public Tag getTag() {
         return this.parent.getTag();
      }

      public String getTagId() {
         return this.parent.getTagId();
      }
   }

   private static class ConverterConfigWrapper implements ConverterConfig {
      private final TagConfig parent;
      private final String converterId;

      public ConverterConfigWrapper(TagConfig parent, String converterId) {
         this.parent = parent;
         this.converterId = converterId;
      }

      public String getConverterId() {
         return this.converterId;
      }

      public FaceletHandler getNextHandler() {
         return this.parent.getNextHandler();
      }

      public Tag getTag() {
         return this.parent.getTag();
      }

      public String getTagId() {
         return this.parent.getTagId();
      }
   }

   private static class ValidatorConfigWrapper implements ValidatorConfig {
      private final TagConfig parent;
      private final String validatorId;

      public ValidatorConfigWrapper(TagConfig parent, String validatorId) {
         this.parent = parent;
         this.validatorId = validatorId;
      }

      public String getValidatorId() {
         return this.validatorId;
      }

      public FaceletHandler getNextHandler() {
         return this.parent.getNextHandler();
      }

      public Tag getTag() {
         return this.parent.getTag();
      }

      public String getTagId() {
         return this.parent.getTagId();
      }
   }
}
