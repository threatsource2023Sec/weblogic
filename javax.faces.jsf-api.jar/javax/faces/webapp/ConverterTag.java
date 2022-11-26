package javax.faces.webapp;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/** @deprecated */
public class ConverterTag extends TagSupport {
   private static final long serialVersionUID = -5909792518081427720L;
   private String converterId = null;
   private String binding = null;

   public void setConverterId(String converterId) {
      this.converterId = converterId;
   }

   public void setBinding(String binding) throws JspException {
      if (binding != null && !UIComponentTag.isValueReference(binding)) {
         throw new JspException("Invalid Expression:" + binding);
      } else {
         this.binding = binding;
      }
   }

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
               String converterError = null;
               if (this.binding != null) {
                  converterError = this.binding;
               }

               if (this.converterId != null) {
                  if (converterError != null) {
                     converterError = converterError + " or " + this.converterId;
                  } else {
                     converterError = this.converterId;
                  }
               }

               throw new JspException("Can't create class of type:javax.faces.convert.Converter for:" + converterError);
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

   public void release() {
      this.converterId = null;
   }

   protected Converter createConverter() throws JspException {
      FacesContext context = FacesContext.getCurrentInstance();
      Converter converter = null;
      ValueExpression vb = null;
      if (this.binding != null) {
         try {
            vb = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), this.binding, Object.class);
            if (vb != null) {
               converter = (Converter)vb.getValue(context.getELContext());
               if (converter != null) {
                  return converter;
               }
            }
         } catch (Exception var7) {
            throw new JspException(var7);
         }
      }

      if (this.converterId != null) {
         try {
            String converterIdVal = this.converterId;
            if (UIComponentTag.isValueReference(this.converterId)) {
               ValueExpression idBinding = context.getApplication().getExpressionFactory().createValueExpression(context.getELContext(), this.converterId, Object.class);
               converterIdVal = (String)idBinding.getValue(context.getELContext());
            }

            converter = context.getApplication().createConverter(converterIdVal);
            if (converter != null && vb != null) {
               vb.setValue(context.getELContext(), converter);
            }
         } catch (Exception var6) {
            throw new JspException(var6);
         }
      }

      return converter;
   }
}
