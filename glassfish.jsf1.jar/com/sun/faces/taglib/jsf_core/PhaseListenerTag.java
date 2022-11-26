package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.component.UIViewRoot;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class PhaseListenerTag extends TagSupport {
   private static final Logger LOGGER;
   private ValueExpression type = null;
   private ValueExpression binding = null;

   public void setType(ValueExpression type) {
      this.type = type;
   }

   public void setBinding(ValueExpression binding) {
      this.binding = binding;
   }

   public int doStartTag() throws JspException {
      Tag parent = this;
      UIComponentELTag tag = null;

      while(null != (parent = ((Tag)parent).getParent())) {
         if (parent instanceof UIComponentELTag) {
            tag = (UIComponentELTag)parent;
         }
      }

      if (tag == null) {
         Object[] params = new Object[]{this.getClass().getName()};
         throw new JspException(MessageUtils.getExceptionMessageString("com.sun.faces.NOT_NESTED_IN_FACES_TAG_ERROR", params));
      } else if (!tag.getCreated()) {
         return 0;
      } else {
         UIViewRoot viewRoot = (UIViewRoot)tag.getComponentInstance();
         if (viewRoot == null) {
            throw new JspException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_COMPONENT_ERROR"));
         } else {
            Object listener;
            if (this.binding == null && this.type != null && this.type.isLiteralText()) {
               try {
                  listener = (PhaseListener)Util.getListenerInstance(this.type, (ValueExpression)null);
               } catch (Exception var6) {
                  throw new JspException(var6.getMessage(), var6.getCause());
               }
            } else {
               listener = new BindingPhaseListener(this.type, this.binding);
            }

            viewRoot.addPhaseListener((PhaseListener)listener);
            return 0;
         }
      }
   }

   public void release() {
      this.type = null;
   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
   }

   private static class BindingPhaseListener implements PhaseListener, Serializable {
      private ValueExpression type;
      private ValueExpression binding;

      public BindingPhaseListener(ValueExpression type, ValueExpression binding) {
         this.type = type;
         this.binding = binding;
      }

      public void afterPhase(PhaseEvent event) {
         PhaseListener listener = this.getPhaseListener();
         if (listener != null) {
            listener.afterPhase(event);
         }

      }

      public void beforePhase(PhaseEvent event) {
         PhaseListener listener = this.getPhaseListener();
         if (listener != null) {
            listener.beforePhase(event);
         }

      }

      public PhaseId getPhaseId() {
         PhaseListener listener = this.getPhaseListener();
         return listener != null ? listener.getPhaseId() : null;
      }

      public PhaseListener getPhaseListener() throws AbortProcessingException {
         PhaseListener instance = (PhaseListener)Util.getListenerInstance(this.type, this.binding);
         if (instance != null) {
            return instance;
         } else {
            if (PhaseListenerTag.LOGGER.isLoggable(Level.WARNING)) {
               PhaseListenerTag.LOGGER.warning("PhaseListener will not be processed - both 'binding' and 'type' are null");
            }

            return null;
         }
      }
   }
}
