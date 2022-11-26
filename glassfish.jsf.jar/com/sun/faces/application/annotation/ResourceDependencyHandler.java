package com.sun.faces.application.annotation;

import com.sun.faces.el.ELUtils;
import com.sun.faces.util.RequestStateManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.ResourceDependency;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

class ResourceDependencyHandler implements RuntimeAnnotationHandler {
   private ResourceDependency[] dependencies;
   private Map expressionsMap;

   public ResourceDependencyHandler(ResourceDependency[] dependencies) {
      this.dependencies = dependencies;
      Map attrs = FacesContext.getCurrentInstance().getAttributes();
      this.expressionsMap = new HashMap(dependencies.length, 1.0F);
      ResourceDependency[] var3 = dependencies;
      int var4 = dependencies.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ResourceDependency dep = var3[var5];
         Expressions exprs = new Expressions();
         exprs.name = dep.name();
         String lib = dep.library();
         String thisLibrary;
         if (lib.length() > 0) {
            if ("this".equals(lib)) {
               thisLibrary = (String)attrs.get("com.sun.faces.composite.this.library");

               assert null != thisLibrary;

               lib = thisLibrary;
            }

            exprs.library = lib;
         }

         thisLibrary = dep.target();
         if (thisLibrary.length() > 0) {
            exprs.target = thisLibrary;
         }

         this.expressionsMap.put(dep, exprs);
      }

   }

   public void apply(FacesContext ctx, Object... params) {
      ResourceDependency[] var3 = this.dependencies;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ResourceDependency dep = var3[var5];
         if (!this.hasBeenProcessed(ctx, dep)) {
            this.pushResourceToRoot(ctx, this.createComponentResource(ctx, dep));
            this.markProcssed(ctx, dep);
         }
      }

   }

   private void pushResourceToRoot(FacesContext ctx, UIComponent c) {
      ctx.getViewRoot().addComponentResource(ctx, c, (String)c.getAttributes().get("target"));
   }

   private boolean hasBeenProcessed(FacesContext ctx, ResourceDependency dep) {
      Set dependencies = (Set)RequestStateManager.get(ctx, "com.sun.faces.PROCESSED_RESOURCE_DEPENDENCIES");
      return dependencies != null && dependencies.contains(dep);
   }

   private UIComponent createComponentResource(FacesContext ctx, ResourceDependency dep) {
      Expressions exprs = (Expressions)this.expressionsMap.get(dep);
      Application app = ctx.getApplication();
      String resname = exprs.getName(ctx);
      UIComponent c = ctx.getApplication().createComponent("javax.faces.Output");
      c.setRendererType(app.getResourceHandler().getRendererTypeForResourceName(resname));
      Map attrs = c.getAttributes();
      attrs.put("name", resname);
      if (exprs.library != null) {
         attrs.put("library", exprs.getLibrary(ctx));
      }

      if (exprs.target != null) {
         attrs.put("target", exprs.getTarget(ctx));
      }

      return c;
   }

   private void markProcssed(FacesContext ctx, ResourceDependency dep) {
      Set dependencies = (Set)RequestStateManager.get(ctx, "com.sun.faces.PROCESSED_RESOURCE_DEPENDENCIES");
      if (dependencies == null) {
         dependencies = new HashSet(6);
         RequestStateManager.set(ctx, "com.sun.faces.PROCESSED_RESOURCE_DEPENDENCIES", dependencies);
      }

      ((Set)dependencies).add(dep);
   }

   private static final class Expressions {
      ValueExpression nameExpression;
      ValueExpression libraryExpression;
      ValueExpression targetExpression;
      String name;
      String library;
      String target;

      private Expressions() {
      }

      String getName(FacesContext ctx) {
         if (this.nameExpression == null) {
            this.nameExpression = ELUtils.createValueExpression(this.name, String.class);
         }

         return (String)this.nameExpression.getValue(ctx.getELContext());
      }

      String getLibrary(FacesContext ctx) {
         if (this.library != null) {
            if (this.libraryExpression == null) {
               this.libraryExpression = ELUtils.createValueExpression(this.library, String.class);
            }

            return (String)this.libraryExpression.getValue(ctx.getELContext());
         } else {
            return null;
         }
      }

      String getTarget(FacesContext ctx) {
         if (this.target != null) {
            if (this.targetExpression == null) {
               this.targetExpression = ELUtils.createValueExpression(this.target, String.class);
            }

            return (String)this.targetExpression.getValue(ctx.getELContext());
         } else {
            return null;
         }
      }

      // $FF: synthetic method
      Expressions(Object x0) {
         this();
      }
   }
}
