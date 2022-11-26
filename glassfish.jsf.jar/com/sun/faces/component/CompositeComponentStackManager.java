package com.sun.faces.component;

import java.util.Stack;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.Location;

public class CompositeComponentStackManager {
   private static final String MANAGER_KEY = CompositeComponentStackManager.class.getName();
   private StackHandler treeCreation = new TreeCreationStackHandler();
   private StackHandler runtime = new RuntimeStackHandler();

   private CompositeComponentStackManager() {
   }

   public static CompositeComponentStackManager getManager(FacesContext ctx) {
      CompositeComponentStackManager manager = (CompositeComponentStackManager)ctx.getAttributes().get(MANAGER_KEY);
      if (manager == null) {
         manager = new CompositeComponentStackManager();
         ctx.getAttributes().put(MANAGER_KEY, manager);
      }

      return manager;
   }

   public boolean push(UIComponent compositeComponent) {
      return this.getStackHandler(CompositeComponentStackManager.StackType.Evaluation).push(compositeComponent);
   }

   public boolean push(UIComponent compositeComponent, StackType stackType) {
      return this.getStackHandler(stackType).push(compositeComponent);
   }

   public boolean push() {
      return this.getStackHandler(CompositeComponentStackManager.StackType.Evaluation).push();
   }

   public boolean push(StackType stackType) {
      return this.getStackHandler(stackType).push();
   }

   public void pop(StackType stackType) {
      this.getStackHandler(stackType).pop();
   }

   public void pop() {
      this.getStackHandler(CompositeComponentStackManager.StackType.Evaluation).pop();
   }

   public UIComponent peek() {
      return this.getStackHandler(CompositeComponentStackManager.StackType.Evaluation).peek();
   }

   public UIComponent peek(StackType stackType) {
      return this.getStackHandler(stackType).peek();
   }

   public UIComponent getParentCompositeComponent(StackType stackType, FacesContext ctx, UIComponent forComponent) {
      return this.getStackHandler(stackType).getParentCompositeComponent(ctx, forComponent);
   }

   public UIComponent findCompositeComponentUsingLocation(FacesContext ctx, Location location) {
      StackHandler sh = this.getStackHandler(CompositeComponentStackManager.StackType.TreeCreation);
      Stack s = sh.getStack(false);
      String path;
      if (s != null) {
         path = location.getPath();

         for(int i = s.size(); i > 0; --i) {
            UIComponent cc = (UIComponent)s.get(i - 1);
            Resource r = (Resource)cc.getAttributes().get("javax.faces.application.Resource.ComponentResource");
            if (path.endsWith('/' + r.getResourceName()) && path.contains(r.getLibraryName())) {
               return cc;
            }
         }
      } else {
         path = location.getPath();

         for(UIComponent cc = UIComponent.getCurrentCompositeComponent(ctx); cc != null; cc = UIComponent.getCompositeComponentParent(cc)) {
            Resource r = (Resource)cc.getAttributes().get("javax.faces.application.Resource.ComponentResource");
            if (path.endsWith('/' + r.getResourceName()) && path.contains(r.getLibraryName())) {
               return cc;
            }
         }
      }

      return UIComponent.getCurrentCompositeComponent(ctx);
   }

   private StackHandler getStackHandler(StackType type) {
      StackHandler handler = null;
      switch (type) {
         case TreeCreation:
            handler = this.treeCreation;
            break;
         case Evaluation:
            handler = this.runtime;
      }

      return handler;
   }

   private final class TreeCreationStackHandler extends BaseStackHandler {
      private TreeCreationStackHandler() {
         super(null);
      }

      public void pop() {
         Stack s = this.getStack(false);
         if (s != null && !this.stack.isEmpty()) {
            this.stack.pop();
            if (this.stack.isEmpty()) {
               this.delete();
            }
         }

      }

      public boolean push() {
         return false;
      }

      public boolean push(UIComponent compositeComponent) {
         if (compositeComponent != null) {
            assert UIComponent.isCompositeComponent(compositeComponent);

            Stack s = this.getStack(true);
            s.push(compositeComponent);
            return true;
         } else {
            return false;
         }
      }

      public UIComponent getParentCompositeComponent(FacesContext ctx, UIComponent forComponent) {
         Stack s = this.getStack(false);
         if (s == null) {
            return null;
         } else {
            int idx = s.indexOf(forComponent);
            return idx == 0 ? null : (UIComponent)s.get(idx - 1);
         }
      }

      // $FF: synthetic method
      TreeCreationStackHandler(Object x1) {
         this();
      }
   }

   private final class RuntimeStackHandler extends BaseStackHandler {
      private RuntimeStackHandler() {
         super(null);
      }

      public void delete() {
         Stack s = this.getStack(false);
         if (s != null) {
            s.clear();
         }

      }

      public void pop() {
         Stack s = this.getStack(false);
         if (s != null && !s.isEmpty()) {
            s.pop();
         }

      }

      public boolean push() {
         return this.push((UIComponent)null);
      }

      public boolean push(UIComponent compositeComponent) {
         Stack tstack = CompositeComponentStackManager.this.treeCreation.getStack(false);
         Stack stack = this.getStack(false);
         UIComponent ccp;
         if (tstack != null) {
            ccp = compositeComponent;
         } else {
            stack = this.getStack(false);
            if (compositeComponent == null) {
               if (stack != null && !stack.isEmpty()) {
                  ccp = this.getCompositeParent((UIComponent)stack.peek());
               } else {
                  ccp = this.getCompositeParent(UIComponent.getCurrentCompositeComponent(FacesContext.getCurrentInstance()));
               }
            } else {
               ccp = compositeComponent;
            }
         }

         if (ccp != null) {
            if (stack == null) {
               stack = this.getStack(true);
            }

            stack.push(ccp);
            return true;
         } else {
            return false;
         }
      }

      public UIComponent getParentCompositeComponent(FacesContext ctx, UIComponent forComponent) {
         return this.getCompositeParent(forComponent);
      }

      private UIComponent getCompositeParent(UIComponent comp) {
         return UIComponent.getCompositeComponentParent(comp);
      }

      // $FF: synthetic method
      RuntimeStackHandler(Object x1) {
         this();
      }
   }

   private abstract class BaseStackHandler implements StackHandler {
      protected Stack stack;

      private BaseStackHandler() {
      }

      public void delete() {
         this.stack = null;
      }

      public Stack getStack(boolean create) {
         if (this.stack == null && create) {
            this.stack = new Stack();
         }

         return this.stack;
      }

      public UIComponent peek() {
         return this.stack != null && !this.stack.isEmpty() ? (UIComponent)this.stack.peek() : null;
      }

      // $FF: synthetic method
      BaseStackHandler(Object x1) {
         this();
      }
   }

   private interface StackHandler {
      boolean push(UIComponent var1);

      boolean push();

      void pop();

      UIComponent peek();

      UIComponent getParentCompositeComponent(FacesContext var1, UIComponent var2);

      void delete();

      Stack getStack(boolean var1);
   }

   public static enum StackType {
      TreeCreation,
      Evaluation;
   }
}
