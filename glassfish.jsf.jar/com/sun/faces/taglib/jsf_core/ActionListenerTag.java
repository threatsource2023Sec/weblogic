package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.webapp.UIComponentClassicTagBase;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ActionListenerTag extends TagSupport {
   private static final long serialVersionUID = -5222351612904952740L;
   private static final Logger logger;
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
         Object[] params = new Object[]{this.getClass().getName()};
         throw new JspException(MessageUtils.getExceptionMessageString("com.sun.faces.NOT_NESTED_IN_FACES_TAG_ERROR", params));
      } else if (!tag.getCreated()) {
         return 0;
      } else {
         UIComponent component = tag.getComponentInstance();
         if (component == null) {
            throw new JspException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_COMPONENT_ERROR"));
         } else if (!(component instanceof ActionSource)) {
            throw new JspException(MessageUtils.getExceptionMessageString("com.sun.faces.NOT_NESTED_IN_TYPE_TAG_ERROR", "actionListener", "javax.faces.component.ActionSource"));
         } else {
            Object listener;
            if (this.binding == null && this.type != null && this.type.isLiteralText()) {
               try {
                  listener = (ActionListener)Util.getListenerInstance(this.type, (ValueExpression)null);
               } catch (Exception var5) {
                  throw new JspException(var5.getMessage(), var5.getCause());
               }
            } else {
               listener = new BindingActionListener(this.type, this.binding);
            }

            ((ActionSource)component).addActionListener((ActionListener)listener);
            return 0;
         }
      }
   }

   public void release() {
      this.type = null;
   }

   static {
      logger = FacesLogger.TAGLIB.getLogger();
   }

   private static class BindingActionListener implements ActionListener, Serializable {
      private static final long serialVersionUID = -718826166288464533L;
      private ValueExpression type;
      private ValueExpression binding;

      public BindingActionListener(ValueExpression type, ValueExpression binding) {
         this.type = type;
         this.binding = binding;
      }

      public void processAction(ActionEvent event) throws AbortProcessingException {
         ActionListener instance = (ActionListener)Util.getListenerInstance(this.type, this.binding);
         if (instance != null) {
            instance.processAction(event);
         } else if (ActionListenerTag.logger.isLoggable(Level.WARNING)) {
            ActionListenerTag.logger.log(Level.WARNING, "jsf.core.taglib.action_or_valuechange_listener.null_type_binding", new Object[]{"ActionListener", event.getComponent().getClientId(FacesContext.getCurrentInstance())});
         }

      }
   }
}
