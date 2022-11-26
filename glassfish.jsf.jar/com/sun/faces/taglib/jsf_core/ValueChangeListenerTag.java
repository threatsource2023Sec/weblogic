package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.faces.webapp.UIComponentClassicTagBase;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ValueChangeListenerTag extends TagSupport {
   private static final long serialVersionUID = -212845116876281363L;
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
      UIComponentClassicTagBase tag = UIComponentClassicTagBase.getParentUIComponentClassicTagBase(this.pageContext);
      if (tag == null) {
         throw new JspException(MessageUtils.getExceptionMessageString("com.sun.faces.NOT_NESTED_IN_FACES_TAG_ERROR"));
      } else if (!tag.getCreated()) {
         return 0;
      } else {
         UIComponent component = tag.getComponentInstance();
         if (component == null) {
            throw new JspException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_COMPONENT_ERROR"));
         } else if (!(component instanceof EditableValueHolder)) {
            Object[] params = new Object[]{"valueChangeListener", "javax.faces.component.EditableValueHolder"};
            throw new JspException(MessageUtils.getExceptionMessageString("com.sun.faces.NOT_NESTED_IN_TYPE_TAG_ERROR", params));
         } else {
            Object listener;
            if (this.binding == null && this.type != null && this.type.isLiteralText()) {
               try {
                  listener = (ValueChangeListener)Util.getListenerInstance(this.type, (ValueExpression)null);
               } catch (Exception var5) {
                  throw new JspException(var5.getMessage(), var5.getCause());
               }
            } else {
               listener = new BindingValueChangeListener(this.type, this.binding);
            }

            ((EditableValueHolder)component).addValueChangeListener((ValueChangeListener)listener);
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

   private static class BindingValueChangeListener implements ValueChangeListener, Serializable {
      private static final long serialVersionUID = -703503904910636450L;
      private ValueExpression type;
      private ValueExpression binding;

      public BindingValueChangeListener(ValueExpression type, ValueExpression binding) {
         this.type = type;
         this.binding = binding;
      }

      public void processValueChange(ValueChangeEvent event) throws AbortProcessingException {
         ValueChangeListener instance = (ValueChangeListener)Util.getListenerInstance(this.type, this.binding);
         if (instance != null) {
            instance.processValueChange(event);
         } else if (ValueChangeListenerTag.LOGGER.isLoggable(Level.WARNING)) {
            ValueChangeListenerTag.LOGGER.log(Level.WARNING, "jsf.core.taglib.action_or_valuechange_listener.null_type_binding", new Object[]{"ValueChangeListener", event.getComponent().getClientId(FacesContext.getCurrentInstance())});
         }

      }
   }
}
