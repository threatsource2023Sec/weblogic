package com.sun.faces.application.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.render.ClientBehaviorRenderer;
import javax.faces.render.FacesBehaviorRenderer;
import javax.faces.render.FacesRenderer;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;

public class RenderKitConfigHandler implements ConfigAnnotationHandler {
   private static final Collection HANDLES;
   Map annotatedRenderers;

   public Collection getHandledAnnotations() {
      return HANDLES;
   }

   public void collect(Class target, Annotation annotation) {
      if (this.annotatedRenderers == null) {
         this.annotatedRenderers = new HashMap();
      }

      this.annotatedRenderers.put(target, annotation);
   }

   public void push(FacesContext ctx) {
      if (this.annotatedRenderers != null) {
         RenderKitFactory rkf = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");
         Iterator var3 = this.annotatedRenderers.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            Class rClass = (Class)entry.getKey();
            RenderKit rk;
            if (entry.getValue() instanceof FacesRenderer) {
               FacesRenderer ra = (FacesRenderer)entry.getValue();

               try {
                  rk = rkf.getRenderKit(ctx, ra.renderKitId());
                  if (rk == null) {
                     throw new IllegalStateException("Error processing annotated Renderer " + ra.toString() + " on class " + rClass.getName() + ".  Unable to find specified RenderKit.");
                  }

                  rk.addRenderer(ra.componentFamily(), ra.rendererType(), (Renderer)rClass.newInstance());
               } catch (InstantiationException | IllegalAccessException | IllegalStateException var9) {
                  throw new FacesException(var9);
               }
            } else if (entry.getValue() instanceof FacesBehaviorRenderer) {
               FacesBehaviorRenderer bra = (FacesBehaviorRenderer)entry.getValue();

               try {
                  rk = rkf.getRenderKit(ctx, bra.renderKitId());
                  if (rk == null) {
                     throw new IllegalStateException("Error processing annotated ClientBehaviorRenderer " + bra.toString() + " on class " + rClass.getName() + ".  Unable to find specified RenderKit.");
                  }

                  rk.addClientBehaviorRenderer(bra.rendererType(), (ClientBehaviorRenderer)rClass.newInstance());
               } catch (InstantiationException | IllegalAccessException | IllegalStateException var8) {
                  throw new FacesException(var8);
               }
            }
         }
      }

   }

   static {
      Collection handles = new ArrayList(2);
      handles.add(FacesRenderer.class);
      handles.add(FacesBehaviorRenderer.class);
      HANDLES = Collections.unmodifiableCollection(handles);
   }
}
