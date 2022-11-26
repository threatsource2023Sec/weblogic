package org.hibernate.validator.internal.engine.constraintvalidation;

import java.util.Collections;
import java.util.Map;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ConstraintViolationCreationContext {
   private final String message;
   private final PathImpl propertyPath;
   private final Map messageParameters;
   private final Map expressionVariables;
   private final Object dynamicPayload;

   public ConstraintViolationCreationContext(String message, PathImpl property) {
      this(message, property, Collections.emptyMap(), Collections.emptyMap(), (Object)null);
   }

   public ConstraintViolationCreationContext(String message, PathImpl property, Map messageParameters, Map expressionVariables, Object dynamicPayload) {
      this.message = message;
      this.propertyPath = property;
      this.messageParameters = CollectionHelper.toImmutableMap(messageParameters);
      this.expressionVariables = CollectionHelper.toImmutableMap(expressionVariables);
      this.dynamicPayload = dynamicPayload;
   }

   public final String getMessage() {
      return this.message;
   }

   public final PathImpl getPath() {
      return this.propertyPath;
   }

   public Map getMessageParameters() {
      return this.messageParameters;
   }

   public Map getExpressionVariables() {
      return this.expressionVariables;
   }

   public Object getDynamicPayload() {
      return this.dynamicPayload;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("ConstraintViolationCreationContext{");
      sb.append("message='").append(this.message).append('\'');
      sb.append(", propertyPath=").append(this.propertyPath);
      sb.append(", messageParameters=").append(this.messageParameters);
      sb.append(", expressionVariables=").append(this.expressionVariables);
      sb.append(", dynamicPayload=").append(this.dynamicPayload);
      sb.append('}');
      return sb.toString();
   }
}
