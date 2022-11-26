package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.ComponentSupport;
import com.sun.faces.facelets.util.ReflectionUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;
import javax.faces.view.facelets.TagException;

public class PhaseListenerHandler extends TagHandlerImpl {
   private final TagAttribute binding = this.getAttribute("binding");
   private final String listenerType;
   private final TagAttribute typeAttribute = this.getAttribute("type");

   public PhaseListenerHandler(TagConfig config) {
      super(config);
      if (null != this.typeAttribute) {
         String stringType = null;
         if (!this.typeAttribute.isLiteral()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FaceletContext ctx = (FaceletContext)context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);
            stringType = (String)this.typeAttribute.getValueExpression(ctx, String.class).getValue(ctx);
         } else {
            stringType = this.typeAttribute.getValue();
         }

         this.checkType(stringType);
         this.listenerType = stringType;
      } else {
         this.listenerType = null;
      }

   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      if (ComponentHandler.isNew(parent)) {
         UIViewRoot root = ComponentSupport.getViewRoot(ctx, parent);
         if (root == null) {
            throw new TagException(this.tag, "UIViewRoot not available");
         }

         ValueExpression b = null;
         if (this.binding != null) {
            b = this.binding.getValueExpression(ctx, PhaseListener.class);
         }

         PhaseListener pl = new LazyPhaseListener(this.listenerType, b);
         List listeners = root.getPhaseListeners();
         if (!listeners.contains(pl)) {
            root.addPhaseListener(pl);
         }
      }

   }

   private void checkType(String type) {
      try {
         ReflectionUtil.forName(type);
      } catch (ClassNotFoundException var3) {
         throw new TagAttributeException(this.typeAttribute, "Couldn't qualify ActionListener", var3);
      }
   }

   private static final class LazyPhaseListener implements PhaseListener, Serializable {
      private static final long serialVersionUID = -6496143057319213401L;
      private final String type;
      private final ValueExpression binding;

      public LazyPhaseListener(String type, ValueExpression binding) {
         this.type = type;
         this.binding = binding;
      }

      private PhaseListener getInstance() {
         PhaseListener instance = null;
         FacesContext faces = FacesContext.getCurrentInstance();
         if (faces == null) {
            return null;
         } else {
            if (this.binding != null) {
               instance = (PhaseListener)this.binding.getValue(faces.getELContext());
            }

            if (instance == null && this.type != null) {
               try {
                  instance = (PhaseListener)ReflectionUtil.forName(this.type).newInstance();
               } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var4) {
                  throw new AbortProcessingException("Couldn't Lazily instantiate PhaseListener", var4);
               }

               if (this.binding != null) {
                  this.binding.setValue(faces.getELContext(), instance);
               }
            }

            return instance;
         }
      }

      public void afterPhase(PhaseEvent event) {
         PhaseListener pl = this.getInstance();
         if (pl != null) {
            pl.afterPhase(event);
         }

      }

      public void beforePhase(PhaseEvent event) {
         PhaseListener pl = this.getInstance();
         if (pl != null) {
            pl.beforePhase(event);
         }

      }

      public PhaseId getPhaseId() {
         PhaseListener pl = this.getInstance();
         return pl != null ? pl.getPhaseId() : PhaseId.ANY_PHASE;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            LazyPhaseListener that = (LazyPhaseListener)o;
            if (this.binding != null) {
               if (!this.binding.equals(that.binding)) {
                  return false;
               }
            } else if (that.binding != null) {
               return false;
            }

            if (this.type != null) {
               if (this.type.equals(that.type)) {
                  return true;
               }
            } else if (that.type == null) {
               return true;
            }

            return false;
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.type != null ? this.type.hashCode() : 0;
         result = 31 * result + (this.binding != null ? this.binding.hashCode() : 0);
         return result;
      }
   }
}
