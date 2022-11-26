package org.hibernate.validator.internal.engine;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.invoke.MethodHandles;
import java.util.Map;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.engine.HibernateConstraintViolation;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ConstraintViolationImpl implements HibernateConstraintViolation, Serializable {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final long serialVersionUID = -4970067626703103139L;
   private final String interpolatedMessage;
   private final Object rootBean;
   private final Object value;
   private final Path propertyPath;
   private final Object leafBeanInstance;
   private final ConstraintDescriptor constraintDescriptor;
   private final String messageTemplate;
   private final Map messageParameters;
   private final Map expressionVariables;
   private final Class rootBeanClass;
   private final ElementType elementType;
   private final Object[] executableParameters;
   private final Object executableReturnValue;
   private final Object dynamicPayload;
   private final int hashCode;

   public static ConstraintViolation forBeanValidation(String messageTemplate, Map messageParameters, Map expressionVariables, String interpolatedMessage, Class rootBeanClass, Object rootBean, Object leafBeanInstance, Object value, Path propertyPath, ConstraintDescriptor constraintDescriptor, ElementType elementType, Object dynamicPayload) {
      return new ConstraintViolationImpl(messageTemplate, messageParameters, expressionVariables, interpolatedMessage, rootBeanClass, rootBean, leafBeanInstance, value, propertyPath, constraintDescriptor, elementType, (Object[])null, (Object)null, dynamicPayload);
   }

   public static ConstraintViolation forParameterValidation(String messageTemplate, Map messageParameters, Map expressionVariables, String interpolatedMessage, Class rootBeanClass, Object rootBean, Object leafBeanInstance, Object value, Path propertyPath, ConstraintDescriptor constraintDescriptor, ElementType elementType, Object[] executableParameters, Object dynamicPayload) {
      return new ConstraintViolationImpl(messageTemplate, messageParameters, expressionVariables, interpolatedMessage, rootBeanClass, rootBean, leafBeanInstance, value, propertyPath, constraintDescriptor, elementType, executableParameters, (Object)null, dynamicPayload);
   }

   public static ConstraintViolation forReturnValueValidation(String messageTemplate, Map messageParameters, Map expressionVariables, String interpolatedMessage, Class rootBeanClass, Object rootBean, Object leafBeanInstance, Object value, Path propertyPath, ConstraintDescriptor constraintDescriptor, ElementType elementType, Object executableReturnValue, Object dynamicPayload) {
      return new ConstraintViolationImpl(messageTemplate, messageParameters, expressionVariables, interpolatedMessage, rootBeanClass, rootBean, leafBeanInstance, value, propertyPath, constraintDescriptor, elementType, (Object[])null, executableReturnValue, dynamicPayload);
   }

   private ConstraintViolationImpl(String messageTemplate, Map messageParameters, Map expressionVariables, String interpolatedMessage, Class rootBeanClass, Object rootBean, Object leafBeanInstance, Object value, Path propertyPath, ConstraintDescriptor constraintDescriptor, ElementType elementType, Object[] executableParameters, Object executableReturnValue, Object dynamicPayload) {
      this.messageTemplate = messageTemplate;
      this.messageParameters = messageParameters;
      this.expressionVariables = expressionVariables;
      this.interpolatedMessage = interpolatedMessage;
      this.rootBean = rootBean;
      this.value = value;
      this.propertyPath = propertyPath;
      this.leafBeanInstance = leafBeanInstance;
      this.constraintDescriptor = constraintDescriptor;
      this.rootBeanClass = rootBeanClass;
      this.elementType = elementType;
      this.executableParameters = executableParameters;
      this.executableReturnValue = executableReturnValue;
      this.dynamicPayload = dynamicPayload;
      this.hashCode = this.createHashCode();
   }

   public final String getMessage() {
      return this.interpolatedMessage;
   }

   public final String getMessageTemplate() {
      return this.messageTemplate;
   }

   public Map getMessageParameters() {
      return this.messageParameters;
   }

   public Map getExpressionVariables() {
      return this.expressionVariables;
   }

   public final Object getRootBean() {
      return this.rootBean;
   }

   public final Class getRootBeanClass() {
      return this.rootBeanClass;
   }

   public final Object getLeafBean() {
      return this.leafBeanInstance;
   }

   public final Object getInvalidValue() {
      return this.value;
   }

   public final Path getPropertyPath() {
      return this.propertyPath;
   }

   public final ConstraintDescriptor getConstraintDescriptor() {
      return this.constraintDescriptor;
   }

   public Object unwrap(Class type) {
      if (type.isAssignableFrom(ConstraintViolation.class)) {
         return type.cast(this);
      } else if (type.isAssignableFrom(HibernateConstraintViolation.class)) {
         return type.cast(this);
      } else {
         throw LOG.getTypeNotSupportedForUnwrappingException(type);
      }
   }

   public Object[] getExecutableParameters() {
      return this.executableParameters;
   }

   public Object getExecutableReturnValue() {
      return this.executableReturnValue;
   }

   public Object getDynamicPayload(Class type) {
      return this.dynamicPayload != null && type.isAssignableFrom(this.dynamicPayload.getClass()) ? type.cast(this.dynamicPayload) : null;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ConstraintViolationImpl that;
         label105: {
            that = (ConstraintViolationImpl)o;
            if (this.interpolatedMessage != null) {
               if (this.interpolatedMessage.equals(that.interpolatedMessage)) {
                  break label105;
               }
            } else if (that.interpolatedMessage == null) {
               break label105;
            }

            return false;
         }

         label98: {
            if (this.messageTemplate != null) {
               if (this.messageTemplate.equals(that.messageTemplate)) {
                  break label98;
               }
            } else if (that.messageTemplate == null) {
               break label98;
            }

            return false;
         }

         if (this.propertyPath != null) {
            if (!this.propertyPath.equals(that.propertyPath)) {
               return false;
            }
         } else if (that.propertyPath != null) {
            return false;
         }

         if (this.rootBean != null) {
            if (this.rootBean != that.rootBean) {
               return false;
            }
         } else if (that.rootBean != null) {
            return false;
         }

         label77: {
            if (this.leafBeanInstance != null) {
               if (this.leafBeanInstance == that.leafBeanInstance) {
                  break label77;
               }
            } else if (that.leafBeanInstance == null) {
               break label77;
            }

            return false;
         }

         label70: {
            if (this.value != null) {
               if (this.value == that.value) {
                  break label70;
               }
            } else if (that.value == null) {
               break label70;
            }

            return false;
         }

         if (this.constraintDescriptor != null) {
            if (!this.constraintDescriptor.equals(that.constraintDescriptor)) {
               return false;
            }
         } else if (that.constraintDescriptor != null) {
            return false;
         }

         if (this.elementType != null) {
            if (!this.elementType.equals(that.elementType)) {
               return false;
            }
         } else if (that.elementType != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.hashCode;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ConstraintViolationImpl");
      sb.append("{interpolatedMessage='").append(this.interpolatedMessage).append('\'');
      sb.append(", propertyPath=").append(this.propertyPath);
      sb.append(", rootBeanClass=").append(this.rootBeanClass);
      sb.append(", messageTemplate='").append(this.messageTemplate).append('\'');
      sb.append('}');
      return sb.toString();
   }

   private int createHashCode() {
      int result = this.interpolatedMessage != null ? this.interpolatedMessage.hashCode() : 0;
      result = 31 * result + (this.propertyPath != null ? this.propertyPath.hashCode() : 0);
      result = 31 * result + System.identityHashCode(this.rootBean);
      result = 31 * result + System.identityHashCode(this.leafBeanInstance);
      result = 31 * result + System.identityHashCode(this.value);
      result = 31 * result + (this.constraintDescriptor != null ? this.constraintDescriptor.hashCode() : 0);
      result = 31 * result + (this.messageTemplate != null ? this.messageTemplate.hashCode() : 0);
      result = 31 * result + (this.elementType != null ? this.elementType.hashCode() : 0);
      return result;
   }
}
