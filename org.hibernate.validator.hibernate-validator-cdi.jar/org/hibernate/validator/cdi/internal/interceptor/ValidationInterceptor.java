package org.hibernate.validator.cdi.internal.interceptor;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;
import javax.validation.Validator;
import javax.validation.executable.ExecutableValidator;

@MethodValidated
@Interceptor
@Priority(4800)
public class ValidationInterceptor implements Serializable {
   private static final long serialVersionUID = 604440259030722151L;
   @Inject
   private Validator validator;

   @AroundInvoke
   public Object validateMethodInvocation(InvocationContext ctx) throws Exception {
      ExecutableValidator executableValidator = this.validator.forExecutables();
      Set violations = executableValidator.validateParameters(ctx.getTarget(), ctx.getMethod(), ctx.getParameters(), new Class[0]);
      if (!violations.isEmpty()) {
         throw new ConstraintViolationException(this.getMessage(ctx.getMethod(), ctx.getParameters(), violations), violations);
      } else {
         Object result = ctx.proceed();
         violations = executableValidator.validateReturnValue(ctx.getTarget(), ctx.getMethod(), result, new Class[0]);
         if (!violations.isEmpty()) {
            throw new ConstraintViolationException(this.getMessage(ctx.getMethod(), ctx.getParameters(), violations), violations);
         } else {
            return result;
         }
      }
   }

   @AroundConstruct
   public void validateConstructorInvocation(InvocationContext ctx) throws Exception {
      ExecutableValidator executableValidator = this.validator.forExecutables();
      Set violations = executableValidator.validateConstructorParameters(ctx.getConstructor(), ctx.getParameters(), new Class[0]);
      if (!violations.isEmpty()) {
         throw new ConstraintViolationException(this.getMessage(ctx.getConstructor(), ctx.getParameters(), violations), violations);
      } else {
         ctx.proceed();
         Object createdObject = ctx.getTarget();
         violations = this.validator.forExecutables().validateConstructorReturnValue(ctx.getConstructor(), createdObject, new Class[0]);
         if (!violations.isEmpty()) {
            throw new ConstraintViolationException(this.getMessage(ctx.getConstructor(), ctx.getParameters(), violations), violations);
         }
      }
   }

   private String getMessage(Member member, Object[] args, Set violations) {
      StringBuilder message = new StringBuilder();
      message.append(violations.size());
      message.append(" constraint violation(s) occurred during method validation.");
      message.append("\nConstructor or Method: ");
      message.append(member);
      message.append("\nArgument values: ");
      message.append(Arrays.toString(args));
      message.append("\nConstraint violations: ");
      int i = 1;

      for(Iterator var6 = violations.iterator(); var6.hasNext(); ++i) {
         ConstraintViolation constraintViolation = (ConstraintViolation)var6.next();
         Path.Node leafNode = this.getLeafNode(constraintViolation);
         message.append("\n (");
         message.append(i);
         message.append(")");
         message.append(" Kind: ");
         message.append(leafNode.getKind());
         if (leafNode.getKind() == ElementKind.PARAMETER) {
            message.append("\n parameter index: ");
            message.append(((Path.ParameterNode)leafNode.as(Path.ParameterNode.class)).getParameterIndex());
         }

         message.append("\n message: ");
         message.append(constraintViolation.getMessage());
         message.append("\n root bean: ");
         message.append(constraintViolation.getRootBean());
         message.append("\n property path: ");
         message.append(constraintViolation.getPropertyPath());
         message.append("\n constraint: ");
         message.append(constraintViolation.getConstraintDescriptor().getAnnotation());
      }

      return message.toString();
   }

   private Path.Node getLeafNode(ConstraintViolation constraintViolation) {
      Iterator nodes = constraintViolation.getPropertyPath().iterator();

      Path.Node leafNode;
      for(leafNode = null; nodes.hasNext(); leafNode = (Path.Node)nodes.next()) {
      }

      return leafNode;
   }
}
