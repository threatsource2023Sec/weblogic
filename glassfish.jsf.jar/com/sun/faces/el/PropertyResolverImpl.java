package com.sun.faces.el;

import com.sun.faces.util.MessageUtils;
import java.lang.reflect.Array;
import java.util.List;
import javax.el.ELException;
import javax.el.PropertyNotWritableException;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;

public class PropertyResolverImpl extends PropertyResolver {
   private PropertyResolver delegate;

   public Class getType(Object base, int index) throws EvaluationException, PropertyNotFoundException {
      assertInput(base, index);
      if (this.delegate != null) {
         return this.delegate.getType(base, index);
      } else {
         Class type = base.getClass();

         try {
            if (type.isArray()) {
               Array.get(base, index);
               return type.getComponentType();
            } else if (base instanceof List) {
               Object value = ((List)base).get(index);
               return value != null ? value.getClass() : null;
            } else {
               throw new PropertyNotFoundException(MessageUtils.getExceptionMessageString("com.sun.faces.PROPERTY_TYPE_ERROR", base));
            }
         } catch (ArrayIndexOutOfBoundsException var5) {
            throw new PropertyNotFoundException(MessageUtils.getExceptionMessageString("com.sun.faces.SIZE_OUT_OF_BOUNDS_ERROR", base, index, Array.getLength(base)));
         } catch (IndexOutOfBoundsException var6) {
            throw new PropertyNotFoundException(MessageUtils.getExceptionMessageString("com.sun.faces.SIZE_OUT_OF_BOUNDS_ERROR", base, index, ((List)base).size()));
         }
      }
   }

   public Class getType(Object base, Object property) {
      assertInput(base, property);
      if (this.delegate != null) {
         return this.delegate.getType(base, property);
      } else {
         try {
            FacesContext context = FacesContext.getCurrentInstance();
            return context.getApplication().getELResolver().getType(context.getELContext(), base, property);
         } catch (javax.el.PropertyNotFoundException var4) {
            throw new PropertyNotFoundException(var4);
         } catch (ELException var5) {
            throw new EvaluationException(var5);
         }
      }
   }

   public Object getValue(Object base, int index) {
      if (base == null) {
         return null;
      } else if (this.delegate != null) {
         return this.delegate.getValue(base, index);
      } else if (base.getClass().isArray()) {
         try {
            return Array.get(base, index);
         } catch (ArrayIndexOutOfBoundsException var4) {
            return null;
         }
      } else if (base instanceof List) {
         try {
            return ((List)base).get(index);
         } catch (IndexOutOfBoundsException var5) {
            return null;
         }
      } else {
         throw new PropertyNotFoundException(MessageUtils.getExceptionMessageString("com.sun.faces.PROPERTY_TYPE_ERROR", base));
      }
   }

   public Object getValue(Object base, Object property) {
      if (this.delegate != null) {
         return this.delegate.getValue(base, property);
      } else {
         try {
            FacesContext context = FacesContext.getCurrentInstance();
            return context.getApplication().getELResolver().getValue(context.getELContext(), base, property);
         } catch (javax.el.PropertyNotFoundException var4) {
            throw new PropertyNotFoundException(var4);
         } catch (ELException var5) {
            throw new EvaluationException(var5);
         }
      }
   }

   public boolean isReadOnly(Object base, int index) {
      assertInput(base, index);
      if (this.delegate != null) {
         return this.delegate.isReadOnly(base, index);
      } else if (!(base instanceof List) && !base.getClass().isArray()) {
         throw new PropertyNotFoundException(MessageUtils.getExceptionMessageString("com.sun.faces.PROPERTY_TYPE_ERROR", base));
      } else {
         return false;
      }
   }

   public boolean isReadOnly(Object base, Object property) {
      if (this.delegate != null) {
         return this.delegate.isReadOnly(base, property);
      } else {
         try {
            FacesContext context = FacesContext.getCurrentInstance();
            return context.getApplication().getELResolver().isReadOnly(context.getELContext(), base, property);
         } catch (ELException var4) {
            throw new EvaluationException(var4);
         }
      }
   }

   public void setValue(Object base, int index, Object value) {
      assertInput(base, index);
      if (this.delegate != null) {
         this.delegate.setValue(base, index, value);
      } else {
         FacesContext context = FacesContext.getCurrentInstance();
         Class type = base.getClass();
         if (type.isArray()) {
            try {
               Array.set(base, index, context.getApplication().getExpressionFactory().coerceToType(value, type.getComponentType()));
            } catch (ArrayIndexOutOfBoundsException var8) {
               throw new PropertyNotFoundException(MessageUtils.getExceptionMessageString("com.sun.faces.SIZE_OUT_OF_BOUNDS_ERROR", base, index, Array.getLength(base)));
            }
         } else {
            if (!(base instanceof List)) {
               throw new PropertyNotFoundException(MessageUtils.getExceptionMessageString("com.sun.faces.PROPERTY_TYPE_ERROR", base));
            }

            try {
               ((List)base).set(index, value);
            } catch (IndexOutOfBoundsException var7) {
               throw new PropertyNotFoundException(MessageUtils.getExceptionMessageString("com.sun.faces.SIZE_OUT_OF_BOUNDS_ERROR", base, index, ((List)base).size()));
            }
         }

      }
   }

   public void setValue(Object base, Object property, Object value) {
      if (this.delegate != null) {
         this.delegate.setValue(base, property, value);
      } else {
         try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.getApplication().getELResolver().setValue(context.getELContext(), base, property, value);
         } catch (PropertyNotWritableException | javax.el.PropertyNotFoundException var5) {
            throw new PropertyNotFoundException(var5);
         } catch (ELException var6) {
            throw new EvaluationException(var6);
         }
      }
   }

   public void setDelegate(PropertyResolver delegate) {
      this.delegate = delegate;
   }

   protected static void assertInput(Object base, Object property) throws PropertyNotFoundException {
      String message;
      if (base == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base");
         throw new PropertyNotFoundException(message);
      } else if (property == null) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "property");
         throw new PropertyNotFoundException(message);
      }
   }

   protected static void assertInput(Object base, int index) throws PropertyNotFoundException {
      if (base == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "base");
         throw new PropertyNotFoundException(message);
      } else if (index < 0) {
         throw new PropertyNotFoundException(MessageUtils.getExceptionMessageString("com.sun.faces.OUT_OF_BOUNDS_ERROR", base, index));
      }
   }
}
