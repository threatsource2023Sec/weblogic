package com.sun.faces.facelets.el;

import com.sun.faces.component.CompositeComponentStackManager;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.Location;

public final class ContextualCompositeValueExpression extends ValueExpression {
   private static final long serialVersionUID = -2637560875633456679L;
   private ValueExpression originalVE;
   private Location location;

   public ContextualCompositeValueExpression() {
   }

   public ContextualCompositeValueExpression(Location location, ValueExpression originalVE) {
      this.originalVE = originalVE;
      this.location = location;
   }

   public Object getValue(ELContext elContext) {
      FacesContext ctx = (FacesContext)elContext.getContext(FacesContext.class);
      boolean pushed = this.pushCompositeComponent(ctx);

      Object var4;
      try {
         var4 = this.originalVE.getValue(elContext);
      } finally {
         if (pushed) {
            this.popCompositeComponent(ctx);
         }

      }

      return var4;
   }

   public void setValue(ELContext elContext, Object o) {
      FacesContext ctx = (FacesContext)elContext.getContext(FacesContext.class);
      boolean pushed = this.pushCompositeComponent(ctx);

      try {
         this.originalVE.setValue(elContext, o);
      } finally {
         if (pushed) {
            this.popCompositeComponent(ctx);
         }

      }

   }

   public boolean isReadOnly(ELContext elContext) {
      FacesContext ctx = (FacesContext)elContext.getContext(FacesContext.class);
      boolean pushed = this.pushCompositeComponent(ctx);

      boolean var4;
      try {
         var4 = this.originalVE.isReadOnly(elContext);
      } finally {
         if (pushed) {
            this.popCompositeComponent(ctx);
         }

      }

      return var4;
   }

   public Class getType(ELContext elContext) {
      FacesContext ctx = (FacesContext)elContext.getContext(FacesContext.class);
      boolean pushed = this.pushCompositeComponent(ctx);

      Class var4;
      try {
         var4 = this.originalVE.getType(elContext);
      } finally {
         if (pushed) {
            this.popCompositeComponent(ctx);
         }

      }

      return var4;
   }

   public Class getExpectedType() {
      FacesContext ctx = FacesContext.getCurrentInstance();
      boolean pushed = this.pushCompositeComponent(ctx);

      Class var3;
      try {
         var3 = this.originalVE.getExpectedType();
      } finally {
         if (pushed) {
            this.popCompositeComponent(ctx);
         }

      }

      return var3;
   }

   public String getExpressionString() {
      return this.originalVE.getExpressionString();
   }

   public boolean equals(Object o) {
      return this.originalVE.equals(o);
   }

   public int hashCode() {
      return this.originalVE.hashCode();
   }

   public boolean isLiteralText() {
      return this.originalVE.isLiteralText();
   }

   public String toString() {
      return this.originalVE.toString();
   }

   public Location getLocation() {
      return this.location;
   }

   private boolean pushCompositeComponent(FacesContext ctx) {
      CompositeComponentStackManager manager = CompositeComponentStackManager.getManager(ctx);
      UIComponent cc = manager.findCompositeComponentUsingLocation(ctx, this.location);
      return manager.push(cc);
   }

   private void popCompositeComponent(FacesContext ctx) {
      CompositeComponentStackManager manager = CompositeComponentStackManager.getManager(ctx);
      manager.pop();
   }
}
