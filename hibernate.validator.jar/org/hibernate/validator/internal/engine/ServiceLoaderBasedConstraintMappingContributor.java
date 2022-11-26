package org.hibernate.validator.internal.engine;

import com.fasterxml.classmate.ResolvedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintValidator;
import org.hibernate.validator.cfg.ConstraintMapping;
import org.hibernate.validator.cfg.context.ConstraintDefinitionContext;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.privilegedactions.GetInstancesFromServiceLoader;
import org.hibernate.validator.spi.cfg.ConstraintMappingContributor;

public class ServiceLoaderBasedConstraintMappingContributor implements ConstraintMappingContributor {
   private final TypeResolutionHelper typeResolutionHelper;
   private final ClassLoader primaryClassLoader;

   public ServiceLoaderBasedConstraintMappingContributor(TypeResolutionHelper typeResolutionHelper, ClassLoader primaryClassLoader) {
      this.primaryClassLoader = primaryClassLoader;
      this.typeResolutionHelper = typeResolutionHelper;
   }

   public void createConstraintMappings(ConstraintMappingContributor.ConstraintMappingBuilder builder) {
      Map customValidators = CollectionHelper.newHashMap();
      List discoveredConstraintValidators = (List)this.run(GetInstancesFromServiceLoader.action(this.primaryClassLoader, ConstraintValidator.class));

      Class constraintValidatorClass;
      Object validators;
      for(Iterator var4 = discoveredConstraintValidators.iterator(); var4.hasNext(); ((List)validators).add(constraintValidatorClass)) {
         ConstraintValidator constraintValidator = (ConstraintValidator)var4.next();
         constraintValidatorClass = constraintValidator.getClass();
         Class annotationType = this.determineAnnotationType(constraintValidatorClass);
         validators = (List)customValidators.get(annotationType);
         if (annotationType != null && validators == null) {
            validators = new ArrayList();
            customValidators.put(annotationType, validators);
         }
      }

      ConstraintMapping constraintMapping = builder.addConstraintMapping();
      Iterator var10 = customValidators.entrySet().iterator();

      while(var10.hasNext()) {
         Map.Entry entry = (Map.Entry)var10.next();
         this.registerConstraintDefinition(constraintMapping, (Class)entry.getKey(), (List)entry.getValue());
      }

   }

   private void registerConstraintDefinition(ConstraintMapping constraintMapping, Class constraintType, List validatorTypes) {
      ConstraintDefinitionContext context = constraintMapping.constraintDefinition(constraintType).includeExistingValidators(true);
      Iterator var5 = validatorTypes.iterator();

      while(var5.hasNext()) {
         Class validatorType = (Class)var5.next();
         context.validatedBy(validatorType);
      }

   }

   private Class determineAnnotationType(Class constraintValidatorClass) {
      ResolvedType resolvedType = this.typeResolutionHelper.getTypeResolver().resolve(constraintValidatorClass, new Type[0]);
      return ((ResolvedType)resolvedType.typeParametersFor(ConstraintValidator.class).get(0)).getErasedType();
   }

   private Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
