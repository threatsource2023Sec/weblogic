package com.sun.faces.facelets.el;

import com.sun.faces.component.CompositeComponentStackManager;
import com.sun.faces.util.FacesLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.MethodNotFoundException;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.Location;

public class ContextualCompositeMethodExpression extends MethodExpression {
   private static final long serialVersionUID = -6281398928485392830L;
   private static final Logger LOGGER;
   private final MethodExpression delegate;
   private final ValueExpression source;
   private final Location location;
   private String ccClientId;

   public ContextualCompositeMethodExpression(ValueExpression source, MethodExpression delegate) {
      this.delegate = delegate;
      this.source = source;
      this.location = null;
      FacesContext ctx = FacesContext.getCurrentInstance();
      UIComponent cc = UIComponent.getCurrentCompositeComponent(ctx);
      cc.subscribeToEvent(PostAddToViewEvent.class, new SetClientIdListener(this));
   }

   public ContextualCompositeMethodExpression(Location location, MethodExpression delegate) {
      this.delegate = delegate;
      this.location = location;
      this.source = null;
      FacesContext ctx = FacesContext.getCurrentInstance();
      UIComponent cc = UIComponent.getCurrentCompositeComponent(ctx);
      cc.subscribeToEvent(PostAddToViewEvent.class, new SetClientIdListener(this));
   }

   public MethodInfo getMethodInfo(ELContext elContext) {
      return this.delegate.getMethodInfo(elContext);
   }

   public Object invoke(ELContext elContext, Object[] objects) {
      FacesContext ctx = (FacesContext)elContext.getContext(FacesContext.class);

      Object fallback;
      try {
         boolean pushed = this.pushCompositeComponent(ctx);

         try {
            fallback = this.delegate.invoke(elContext, objects);
         } finally {
            if (pushed) {
               this.popCompositeComponent(ctx);
            }

         }

         return fallback;
      } catch (ELException var12) {
         if (var12.getCause() != null && var12.getCause() instanceof ValidatorException) {
            throw (ValidatorException)var12.getCause();
         } else {
            if (this.source != null && var12 instanceof MethodNotFoundException) {
               try {
                  fallback = this.source.getValue(elContext);
                  if (fallback != null && fallback instanceof MethodExpression) {
                     return ((MethodExpression)fallback).invoke(elContext, objects);
                  }
               } catch (ELException var10) {
                  if (var10.getCause() != null && var10.getCause() instanceof ValidatorException) {
                     throw (ValidatorException)var10.getCause();
                  }

                  if (LOGGER.isLoggable(Level.WARNING)) {
                     LOGGER.log(Level.WARNING, var12.toString());
                     LOGGER.log(Level.WARNING, "jsf.facelets.el.method.expression.invoke.error: {0} {1}", new Object[]{var10.toString(), this.source.getExpressionString()});
                  }

                  if (!(var10 instanceof MethodNotFoundException)) {
                     throw var10;
                  }
               }
            }

            throw var12;
         }
      }
   }

   public String getExpressionString() {
      return this.delegate.getExpressionString();
   }

   public boolean equals(Object o) {
      return this.delegate.equals(o);
   }

   public int hashCode() {
      return this.delegate.hashCode();
   }

   public boolean isLiteralText() {
      return this.delegate.isLiteralText();
   }

   private boolean pushCompositeComponent(FacesContext ctx) {
      CompositeComponentStackManager manager = CompositeComponentStackManager.getManager(ctx);
      UIComponent foundCc = null;
      if (this.location != null) {
         foundCc = manager.findCompositeComponentUsingLocation(ctx, this.location);
      } else if (this.source instanceof TagValueExpression) {
         ValueExpression orig = ((TagValueExpression)this.source).getWrapped();
         if (orig instanceof ContextualCompositeValueExpression) {
            foundCc = manager.findCompositeComponentUsingLocation(ctx, ((ContextualCompositeValueExpression)orig).getLocation());
         }
      }

      if (null == foundCc) {
         foundCc = ctx.getViewRoot().findComponent(this.ccClientId);
      }

      return manager.push(foundCc);
   }

   private void popCompositeComponent(FacesContext ctx) {
      CompositeComponentStackManager manager = CompositeComponentStackManager.getManager(ctx);
      manager.pop();
   }

   static {
      LOGGER = FacesLogger.FACELETS_EL.getLogger();
   }

   private class SetClientIdListener implements ComponentSystemEventListener {
      private ContextualCompositeMethodExpression ccME;

      public SetClientIdListener() {
      }

      public SetClientIdListener(ContextualCompositeMethodExpression ccME) {
         this.ccME = ccME;
      }

      public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
         this.ccME.ccClientId = event.getComponent().getClientId();
         event.getComponent().unsubscribeFromEvent(PostAddToViewEvent.class, this);
      }
   }
}
