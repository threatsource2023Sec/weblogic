package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.webapp.ConverterELTag;
import javax.servlet.jsp.JspException;

public class AbstractConverterTag extends ConverterELTag {
   private static final long serialVersionUID = -8219789624438804540L;
   private static final Logger LOGGER;
   protected ValueExpression binding = null;
   protected ValueExpression converterId = null;

   public void setBinding(ValueExpression binding) {
      this.binding = binding;
   }

   public void setConverterId(ValueExpression converterId) {
      this.converterId = converterId;
   }

   protected Converter createConverter() throws JspException {
      try {
         return createConverter(this.converterId, this.binding, FacesContext.getCurrentInstance());
      } catch (FacesException var2) {
         throw new JspException(var2.getCause());
      }
   }

   protected static Converter createConverter(ValueExpression converterId, ValueExpression binding, FacesContext facesContext) {
      ELContext elContext = facesContext.getELContext();
      Converter converter = null;
      if (binding != null) {
         try {
            converter = (Converter)binding.getValue(elContext);
            if (converter != null) {
               return converter;
            }
         } catch (Exception var7) {
            throw new FacesException(var7);
         }
      }

      if (converterId != null) {
         try {
            String converterIdVal = (String)converterId.getValue(elContext);
            converter = facesContext.getApplication().createConverter(converterIdVal);
            if (converter != null && binding != null) {
               binding.setValue(elContext, converter);
            }
         } catch (Exception var6) {
            throw new FacesException(var6);
         }
      }

      if (converter == null && LOGGER.isLoggable(Level.WARNING)) {
         LOGGER.log(Level.WARNING, MessageUtils.getExceptionMessageString("com.sun.faces.CANNOT_CONVERT", converterId != null ? converterId.getExpressionString() : "", binding != null ? binding.getExpressionString() : ""));
      }

      return converter;
   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
   }
}
