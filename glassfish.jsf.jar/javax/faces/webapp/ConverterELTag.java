package javax.faces.webapp;

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class ConverterELTag extends TagSupport {
   private static final long serialVersionUID = -1876768812840134640L;

   public int doStartTag() throws JspException {
      UIComponentClassicTagBase tag = UIComponentClassicTagBase.getParentUIComponentClassicTagBase(this.pageContext);
      if (tag == null) {
         throw new JspException("Not nested in a UIComponentTag Error for tag with handler class:" + this.getClass().getName());
      } else if (!tag.getCreated()) {
         return 0;
      } else {
         UIComponent component = tag.getComponentInstance();
         if (component == null) {
            throw new JspException("Can't create Component from tag.");
         } else if (!(component instanceof ValueHolder)) {
            throw new JspException("Not nested in a tag of proper type. Error for tag with handler class:" + this.getClass().getName());
         } else {
            Converter converter = this.createConverter();
            if (converter == null) {
               throw new JspException("Can't create class of type: javax.faces.convert.Converter, converter is null");
            } else {
               ValueHolder vh = (ValueHolder)component;
               FacesContext context = FacesContext.getCurrentInstance();
               vh.setConverter(converter);
               Object localValue = vh.getLocalValue();
               if (localValue instanceof String) {
                  try {
                     localValue = converter.getAsObject(context, (UIComponent)vh, (String)localValue);
                     vh.setValue(localValue);
                  } catch (ConverterException var8) {
                  }
               }

               return 0;
            }
         }
      }
   }

   protected abstract Converter createConverter() throws JspException;
}
