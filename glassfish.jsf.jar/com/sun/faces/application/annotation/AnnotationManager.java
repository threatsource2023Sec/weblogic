package com.sun.faces.application.annotation;

import com.sun.faces.util.FacesLogger;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.behavior.ClientBehaviorBase;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.SystemEvent;
import javax.faces.render.ClientBehaviorRenderer;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.validator.Validator;

public class AnnotationManager {
   private static final Logger LOGGER;
   private static final Scanner RESOURCE_DEPENDENCY_SCANNER;
   private static final Scanner LISTENER_FOR_SCANNER;
   private static final Scanner[] BEHAVIOR_SCANNERS;
   private static final Scanner[] CLIENT_BEHAVIOR_RENDERER_SCANNERS;
   private static final Scanner[] UICOMPONENT_SCANNERS;
   private static final Scanner[] VALIDATOR_SCANNERS;
   private static final Scanner[] CONVERTER_SCANNERS;
   private static final Scanner[] RENDERER_SCANNERS;
   private static final Scanner[] EVENTS_SCANNERS;
   private ConcurrentMap cache = new ConcurrentHashMap(40, 0.75F, 32);

   public void applyConfigAnnotations(FacesContext ctx, Class annotationType, Set annotatedClasses) {
      if (annotatedClasses != null && !annotatedClasses.isEmpty()) {
         ConfigAnnotationHandler handler = (ConfigAnnotationHandler)this.getConfigAnnotationHandlers().get(annotationType);
         if (handler == null) {
            throw new IllegalStateException("Internal Error: No ConfigAnnotationHandler for type: " + annotationType);
         }

         Iterator var5 = annotatedClasses.iterator();

         while(var5.hasNext()) {
            Class clazz = (Class)var5.next();
            handler.collect(clazz, clazz.getAnnotation(annotationType));
         }

         handler.push(ctx);
      }

   }

   public void applyBehaviorAnnotations(FacesContext ctx, Behavior b) {
      this.applyAnnotations(ctx, b.getClass(), AnnotationManager.ProcessingTarget.Behavior, b);
      if (b instanceof ClientBehaviorBase) {
         ClientBehaviorBase clientBehavior = (ClientBehaviorBase)b;
         String rendererType = clientBehavior.getRendererType();
         RenderKit renderKit = ctx.getRenderKit();
         if (null != rendererType && null != renderKit) {
            ClientBehaviorRenderer behaviorRenderer = renderKit.getClientBehaviorRenderer(rendererType);
            if (null != behaviorRenderer) {
               this.applyClientBehaviorRendererAnnotations(ctx, behaviorRenderer);
            }
         }
      }

   }

   public void applyClientBehaviorRendererAnnotations(FacesContext ctx, ClientBehaviorRenderer b) {
      this.applyAnnotations(ctx, b.getClass(), AnnotationManager.ProcessingTarget.ClientBehaviorRenderer, b);
   }

   public void applyComponentAnnotations(FacesContext ctx, UIComponent c) {
      this.applyAnnotations(ctx, c.getClass(), AnnotationManager.ProcessingTarget.UIComponent, c);
   }

   public void applyValidatorAnnotations(FacesContext ctx, Validator v) {
      this.applyAnnotations(ctx, v.getClass(), AnnotationManager.ProcessingTarget.Validator, v);
   }

   public void applyConverterAnnotations(FacesContext ctx, Converter c) {
      this.applyAnnotations(ctx, c.getClass(), AnnotationManager.ProcessingTarget.Converter, c);
   }

   public void applyRendererAnnotations(FacesContext ctx, Renderer r, UIComponent c) {
      this.applyAnnotations(ctx, r.getClass(), AnnotationManager.ProcessingTarget.Renderer, r, c);
   }

   public void applySystemEventAnnotations(FacesContext ctx, SystemEvent e) {
      this.applyAnnotations(ctx, e.getClass(), AnnotationManager.ProcessingTarget.SystemEvent, e);
   }

   private Map getConfigAnnotationHandlers() {
      ConfigAnnotationHandler[] handlers = new ConfigAnnotationHandler[]{new ComponentConfigHandler(), new ConverterConfigHandler(), new ValidatorConfigHandler(), new BehaviorConfigHandler(), new RenderKitConfigHandler(), new ManagedBeanConfigHandler(), new NamedEventConfigHandler()};
      Map handlerMap = new HashMap();
      ConfigAnnotationHandler[] var3 = handlers;
      int var4 = handlers.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ConfigAnnotationHandler handler = var3[var5];
         Collection handledClasses = handler.getHandledAnnotations();
         Iterator var8 = handledClasses.iterator();

         while(var8.hasNext()) {
            Class handled = (Class)var8.next();
            handlerMap.put(handled, handler);
         }
      }

      return handlerMap;
   }

   private void applyAnnotations(FacesContext ctx, Class targetClass, ProcessingTarget processingTarget, Object... params) {
      Map map = this.getHandlerMap(targetClass, processingTarget);
      if (map != null && !map.isEmpty()) {
         Iterator var6 = map.values().iterator();

         while(var6.hasNext()) {
            RuntimeAnnotationHandler handler = (RuntimeAnnotationHandler)var6.next();
            handler.apply(ctx, params);
         }
      }

   }

   private Map getHandlerMap(Class targetClass, ProcessingTarget processingTarget) {
      while(true) {
         Future f = (Future)this.cache.get(targetClass);
         if (f == null) {
            ProcessAnnotationsTask t = new ProcessAnnotationsTask(targetClass, processingTarget.scanners);
            FutureTask ft = new FutureTask(t);
            f = (Future)this.cache.putIfAbsent(targetClass, ft);
            if (f == null) {
               f = ft;
               ft.run();
            }
         }

         try {
            return (Map)((Future)f).get();
         } catch (InterruptedException | CancellationException var6) {
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, var6.toString(), var6);
            }

            this.cache.remove(targetClass);
         } catch (ExecutionException var7) {
            throw new FacesException(var7);
         }
      }
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      RESOURCE_DEPENDENCY_SCANNER = new ResourceDependencyScanner();
      LISTENER_FOR_SCANNER = new ListenerForScanner();
      BEHAVIOR_SCANNERS = new Scanner[]{RESOURCE_DEPENDENCY_SCANNER};
      CLIENT_BEHAVIOR_RENDERER_SCANNERS = new Scanner[]{RESOURCE_DEPENDENCY_SCANNER};
      UICOMPONENT_SCANNERS = new Scanner[]{RESOURCE_DEPENDENCY_SCANNER, LISTENER_FOR_SCANNER};
      VALIDATOR_SCANNERS = new Scanner[]{RESOURCE_DEPENDENCY_SCANNER};
      CONVERTER_SCANNERS = new Scanner[]{RESOURCE_DEPENDENCY_SCANNER};
      RENDERER_SCANNERS = new Scanner[]{RESOURCE_DEPENDENCY_SCANNER, LISTENER_FOR_SCANNER};
      EVENTS_SCANNERS = new Scanner[]{RESOURCE_DEPENDENCY_SCANNER};
   }

   private static final class ProcessAnnotationsTask implements Callable {
      private static final Map EMPTY;
      private Class clazz;
      private Scanner[] scanners;

      public ProcessAnnotationsTask(Class clazz, Scanner[] scanners) {
         this.clazz = clazz;
         this.scanners = scanners;
      }

      public Map call() throws Exception {
         Map map = null;
         Scanner[] var2 = this.scanners;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Scanner scanner = var2[var4];
            RuntimeAnnotationHandler handler = scanner.scan(this.clazz);
            if (handler != null) {
               if (map == null) {
                  map = new HashMap(2, 1.0F);
               }

               map.put(scanner.getAnnotation(), handler);
            }
         }

         return (Map)(map != null ? map : EMPTY);
      }

      static {
         EMPTY = Collections.EMPTY_MAP;
      }
   }

   private static enum ProcessingTarget {
      Behavior(AnnotationManager.BEHAVIOR_SCANNERS),
      ClientBehaviorRenderer(AnnotationManager.CLIENT_BEHAVIOR_RENDERER_SCANNERS),
      UIComponent(AnnotationManager.UICOMPONENT_SCANNERS),
      Validator(AnnotationManager.VALIDATOR_SCANNERS),
      Converter(AnnotationManager.CONVERTER_SCANNERS),
      Renderer(AnnotationManager.RENDERER_SCANNERS),
      SystemEvent(AnnotationManager.EVENTS_SCANNERS);

      private Scanner[] scanners;

      private ProcessingTarget(Scanner[] scanners) {
         this.scanners = scanners;
      }
   }
}
