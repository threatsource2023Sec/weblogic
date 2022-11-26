package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.MessageUtils;
import javax.el.ValueExpression;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionListener;
import javax.faces.webapp.UIComponentClassicTagBase;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SetPropertyActionListenerTag extends TagSupport {
   static final long serialVersionUID = 7966883942522780374L;
   private ValueExpression target = null;
   private ValueExpression value = null;

   public void setTarget(ValueExpression target) {
      this.target = target;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public int doStartTag() throws JspException {
      UIComponentClassicTagBase tag = UIComponentELTag.getParentUIComponentClassicTagBase(this.pageContext);
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
            Object[] params = new Object[]{"setPropertyActionListener", "javax.faces.component.ActionSource"};
            throw new JspException(MessageUtils.getExceptionMessageString("com.sun.faces.NOT_NESTED_IN_TYPE_TAG_ERROR", params));
         } else {
            ActionListener handler = new SetPropertyActionListenerImpl(this.target, this.value);
            ((ActionSource)component).addActionListener(handler);
            return 0;
         }
      }
   }

   public void release() {
      this.value = null;
      this.target = null;
   }
}
