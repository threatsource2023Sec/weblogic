package javax.faces.webapp;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/** @deprecated */
public class AttributeTag extends TagSupport {
   private static final long serialVersionUID = -7782950243436672334L;
   private String name = null;
   private String value = null;

   public void setName(String name) {
      this.name = name;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public int doStartTag() throws JspException {
      UIComponentClassicTagBase tag = UIComponentClassicTagBase.getParentUIComponentClassicTagBase(this.pageContext);
      if (tag == null) {
         throw new JspException("Not nested in a UIComponentTag");
      } else {
         UIComponent component = tag.getComponentInstance();
         if (component == null) {
            throw new JspException("No component associated with UIComponentTag");
         } else {
            FacesContext context = FacesContext.getCurrentInstance();
            ExpressionFactory exprFactory = context.getApplication().getExpressionFactory();
            ELContext elContext = context.getELContext();
            String nameVal = (String)exprFactory.createValueExpression(elContext, this.name, String.class).getValue(elContext);
            Object valueVal = exprFactory.createValueExpression(elContext, this.value, Object.class).getValue(elContext);
            if (component.getAttributes().get(nameVal) == null) {
               component.getAttributes().put(nameVal, valueVal);
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
