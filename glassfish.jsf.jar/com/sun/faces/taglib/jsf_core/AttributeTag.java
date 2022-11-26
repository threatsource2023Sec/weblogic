package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.MessageUtils;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentClassicTagBase;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class AttributeTag extends TagSupport {
   private static final long serialVersionUID = -4058910110356397536L;
   private ValueExpression name = null;
   private ValueExpression value = null;

   public void setName(ValueExpression name) {
      this.name = name;
   }

   public void setValue(ValueExpression value) {
      this.value = value;
   }

   public int doStartTag() throws JspException {
      UIComponentClassicTagBase tag = UIComponentELTag.getParentUIComponentClassicTagBase(this.pageContext);
      if (tag == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NOT_NESTED_IN_UICOMPONENT_TAG_ERROR");
         throw new JspException(message);
      } else {
         UIComponent component = tag.getComponentInstance();
         if (component == null) {
            String message = MessageUtils.getExceptionMessageString("com.sun.faces.NO_COMPONENT_ASSOCIATED_WITH_UICOMPONENT_TAG");
            throw new JspException(message);
         } else {
            FacesContext context = FacesContext.getCurrentInstance();
            ELContext elContext = context.getELContext();
            String nameVal = null;
            Object valueVal = null;
            boolean isLiteral = false;
            if (this.name != null) {
               nameVal = (String)this.name.getValue(elContext);
            }

            if (this.value != null && (isLiteral = this.value.isLiteralText())) {
               valueVal = this.value.getValue(elContext);
            }

            if (component.getAttributes().get(nameVal) == null) {
               if (isLiteral) {
                  component.getAttributes().put(nameVal, valueVal);
               } else {
                  component.setValueExpression(nameVal, this.value);
               }
            }

            return 0;
         }
      }
   }

   public int doEndTag() throws JspException {
      this.release();
      return 6;
   }

   public void release() {
      this.name = null;
      this.value = null;
   }
}
