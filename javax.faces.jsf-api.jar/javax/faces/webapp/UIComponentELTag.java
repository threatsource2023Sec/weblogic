package javax.faces.webapp;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

public abstract class UIComponentELTag extends UIComponentClassicTagBase implements Tag {
   private ValueExpression binding = null;
   private ValueExpression rendered = null;

   public void setBinding(ValueExpression binding) throws JspException {
      this.binding = binding;
   }

   protected boolean hasBinding() {
      return null != this.binding;
   }

   public void setRendered(ValueExpression rendered) {
      this.rendered = rendered;
   }

   protected ELContext getELContext() {
      FacesContext fc = this.getFacesContext();
      ELContext result = null;
      if (null != fc) {
         result = fc.getELContext();
      }

      return result;
   }

   public void release() {
      this.binding = null;
      this.rendered = null;
      super.release();
   }

   protected void setProperties(UIComponent component) {
      if (this.rendered != null) {
         if (this.rendered.isLiteralText()) {
            try {
               component.setRendered(Boolean.valueOf(this.rendered.getExpressionString()));
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            component.setValueExpression("rendered", this.rendered);
         }
      }

      if (this.getRendererType() != null) {
         component.setRendererType(this.getRendererType());
      }

   }

   protected UIComponent createComponent(FacesContext context, String newId) throws JspException {
      Application application = context.getApplication();
      UIComponent component;
      if (this.binding != null) {
         component = application.createComponent(this.binding, context, this.getComponentType());
         component.setValueExpression("binding", this.binding);
      } else {
         component = application.createComponent(this.getComponentType());
      }

      component.setId(newId);
      this.setProperties(component);
      return component;
   }
}
