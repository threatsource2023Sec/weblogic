package org.hibernate.validator.internal.engine;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import javax.validation.metadata.ConstraintDescriptor;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.messageinterpolation.HibernateMessageInterpolatorContext;

public class MessageInterpolatorContext implements HibernateMessageInterpolatorContext {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final ConstraintDescriptor constraintDescriptor;
   private final Object validatedValue;
   private final Class rootBeanType;
   private final Map messageParameters;
   private final Map expressionVariables;

   public MessageInterpolatorContext(ConstraintDescriptor constraintDescriptor, Object validatedValue, Class rootBeanType, Map messageParameters, Map expressionVariables) {
      this.constraintDescriptor = constraintDescriptor;
      this.validatedValue = validatedValue;
      this.rootBeanType = rootBeanType;
      this.messageParameters = CollectionHelper.toImmutableMap(messageParameters);
      this.expressionVariables = CollectionHelper.toImmutableMap(expressionVariables);
   }

   public ConstraintDescriptor getConstraintDescriptor() {
      return this.constraintDescriptor;
   }

   public Object getValidatedValue() {
      return this.validatedValue;
   }

   public Class getRootBeanType() {
      return this.rootBeanType;
   }

   public Map getMessageParameters() {
      return this.messageParameters;
   }

   public Map getExpressionVariables() {
      return this.expressionVariables;
   }

   public Object unwrap(Class type) {
      if (type.isAssignableFrom(HibernateMessageInterpolatorContext.class)) {
         return type.cast(this);
      } else {
         throw LOG.getTypeNotSupportedForUnwrappingException(type);
      }
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         MessageInterpolatorContext that;
         label45: {
            that = (MessageInterpolatorContext)o;
            if (this.constraintDescriptor != null) {
               if (this.constraintDescriptor.equals(that.constraintDescriptor)) {
                  break label45;
               }
            } else if (that.constraintDescriptor == null) {
               break label45;
            }

            return false;
         }

         label38: {
            if (this.rootBeanType != null) {
               if (this.rootBeanType.equals(that.rootBeanType)) {
                  break label38;
               }
            } else if (that.rootBeanType == null) {
               break label38;
            }

            return false;
         }

         if (this.validatedValue != null) {
            if (this.validatedValue != that.validatedValue) {
               return false;
            }
         } else if (that.validatedValue != null) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.constraintDescriptor != null ? this.constraintDescriptor.hashCode() : 0;
      result = 31 * result + System.identityHashCode(this.validatedValue);
      result = 31 * result + (this.rootBeanType != null ? this.rootBeanType.hashCode() : 0);
      return result;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("MessageInterpolatorContext");
      sb.append("{constraintDescriptor=").append(this.constraintDescriptor);
      sb.append(", validatedValue=").append(this.validatedValue);
      sb.append(", messageParameters=").append(this.messageParameters);
      sb.append(", expressionVariables=").append(this.expressionVariables);
      sb.append('}');
      return sb.toString();
   }
}
