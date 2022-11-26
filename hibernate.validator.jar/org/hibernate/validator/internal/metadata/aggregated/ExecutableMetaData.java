package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.ElementKind;
import org.hibernate.validator.internal.engine.MethodValidationConfiguration;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.aggregated.rule.MethodConfigurationRule;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.descriptor.ExecutableDescriptorImpl;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ExecutableHelper;
import org.hibernate.validator.internal.util.ExecutableParameterNameProvider;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;

public class ExecutableMetaData extends AbstractConstraintMetaData {
   private final Class[] parameterTypes;
   private final List parameterMetaDataList;
   private final ValidatableParametersMetaData validatableParametersMetaData;
   private final Set crossParameterConstraints;
   private final boolean isGetter;
   private final Set signatures;
   private final ReturnValueMetaData returnValueMetaData;
   private final ElementKind kind;

   private ExecutableMetaData(String name, Type returnType, Class[] parameterTypes, ElementKind kind, Set signatures, Set returnValueConstraints, Set returnValueContainerElementConstraints, List parameterMetaDataList, Set crossParameterConstraints, CascadingMetaData cascadingMetaData, boolean isConstrained, boolean isGetter) {
      super(name, returnType, returnValueConstraints, returnValueContainerElementConstraints, cascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements(), isConstrained);
      this.parameterTypes = parameterTypes;
      this.parameterMetaDataList = CollectionHelper.toImmutableList(parameterMetaDataList);
      this.validatableParametersMetaData = new ValidatableParametersMetaData(parameterMetaDataList);
      this.crossParameterConstraints = CollectionHelper.toImmutableSet(crossParameterConstraints);
      this.signatures = signatures;
      this.returnValueMetaData = new ReturnValueMetaData(returnType, returnValueConstraints, returnValueContainerElementConstraints, cascadingMetaData);
      this.isGetter = isGetter;
      this.kind = kind;
   }

   public ParameterMetaData getParameterMetaData(int parameterIndex) {
      return (ParameterMetaData)this.parameterMetaDataList.get(parameterIndex);
   }

   public Class[] getParameterTypes() {
      return this.parameterTypes;
   }

   public Set getSignatures() {
      return this.signatures;
   }

   public Set getCrossParameterConstraints() {
      return this.crossParameterConstraints;
   }

   public ValidatableParametersMetaData getValidatableParametersMetaData() {
      return this.validatableParametersMetaData;
   }

   public ReturnValueMetaData getReturnValueMetaData() {
      return this.returnValueMetaData;
   }

   public ExecutableDescriptorImpl asDescriptor(boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      return new ExecutableDescriptorImpl(this.getType(), this.getName(), this.asDescriptors(this.getCrossParameterConstraints()), this.returnValueMetaData.asDescriptor(defaultGroupSequenceRedefined, defaultGroupSequence), this.parametersAsDescriptors(defaultGroupSequenceRedefined, defaultGroupSequence), defaultGroupSequenceRedefined, this.isGetter, defaultGroupSequence);
   }

   private List parametersAsDescriptors(boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      List parameterDescriptorList = CollectionHelper.newArrayList();
      Iterator var4 = this.parameterMetaDataList.iterator();

      while(var4.hasNext()) {
         ParameterMetaData parameterMetaData = (ParameterMetaData)var4.next();
         parameterDescriptorList.add(parameterMetaData.asDescriptor(defaultGroupSequenceRedefined, defaultGroupSequence));
      }

      return parameterDescriptorList;
   }

   public ElementKind getKind() {
      return this.kind;
   }

   public String toString() {
      StringBuilder parameterBuilder = new StringBuilder();
      Class[] var2 = this.getParameterTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class oneParameterType = var2[var4];
         parameterBuilder.append(oneParameterType.getSimpleName());
         parameterBuilder.append(", ");
      }

      String parameters = parameterBuilder.length() > 0 ? parameterBuilder.substring(0, parameterBuilder.length() - 2) : parameterBuilder.toString();
      return "ExecutableMetaData [executable=" + this.getType() + " " + this.getName() + "(" + parameters + "), isCascading=" + this.isCascading() + ", isConstrained=" + this.isConstrained() + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + Arrays.hashCode(this.parameterTypes);
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ExecutableMetaData other = (ExecutableMetaData)obj;
         return Arrays.equals(this.parameterTypes, other.parameterTypes);
      }
   }

   // $FF: synthetic method
   ExecutableMetaData(String x0, Type x1, Class[] x2, ElementKind x3, Set x4, Set x5, Set x6, List x7, Set x8, CascadingMetaData x9, boolean x10, boolean x11, Object x12) {
      this(x0, x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11);
   }

   public static class Builder extends MetaDataBuilder {
      private final Set signatures = CollectionHelper.newHashSet();
      private final ConstrainedElement.ConstrainedElementKind kind;
      private final Set constrainedExecutables = CollectionHelper.newHashSet();
      private Executable executable;
      private final boolean isGetterMethod;
      private final Set crossParameterConstraints = CollectionHelper.newHashSet();
      private final Set rules;
      private boolean isConstrained = false;
      private CascadingMetaDataBuilder cascadingMetaDataBuilder;
      private final Map executablesByDeclaringType = CollectionHelper.newHashMap();
      private final ExecutableHelper executableHelper;
      private final ExecutableParameterNameProvider parameterNameProvider;

      public Builder(Class beanClass, ConstrainedExecutable constrainedExecutable, ConstraintHelper constraintHelper, ExecutableHelper executableHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager, ExecutableParameterNameProvider parameterNameProvider, MethodValidationConfiguration methodValidationConfiguration) {
         super(beanClass, constraintHelper, typeResolutionHelper, valueExtractorManager);
         this.executableHelper = executableHelper;
         this.parameterNameProvider = parameterNameProvider;
         this.kind = constrainedExecutable.getKind();
         this.executable = constrainedExecutable.getExecutable();
         this.rules = methodValidationConfiguration.getConfiguredRuleSet();
         this.isGetterMethod = constrainedExecutable.isGetterMethod();
         this.add(constrainedExecutable);
      }

      public boolean accepts(ConstrainedElement constrainedElement) {
         if (this.kind != constrainedElement.getKind()) {
            return false;
         } else {
            Executable candidate = ((ConstrainedExecutable)constrainedElement).getExecutable();
            return this.executable.equals(candidate) || this.overrides(this.executable, candidate) || this.overrides(candidate, this.executable);
         }
      }

      private boolean overrides(Executable first, Executable other) {
         return !(first instanceof Constructor) && !(other instanceof Constructor) ? this.executableHelper.overrides((Method)first, (Method)other) : false;
      }

      public final void add(ConstrainedElement constrainedElement) {
         super.add(constrainedElement);
         ConstrainedExecutable constrainedExecutable = (ConstrainedExecutable)constrainedElement;
         this.signatures.add(ExecutableHelper.getSignature(constrainedExecutable.getExecutable()));
         this.constrainedExecutables.add(constrainedExecutable);
         this.isConstrained = this.isConstrained || constrainedExecutable.isConstrained();
         this.crossParameterConstraints.addAll(constrainedExecutable.getCrossParameterConstraints());
         if (this.cascadingMetaDataBuilder == null) {
            this.cascadingMetaDataBuilder = constrainedExecutable.getCascadingMetaDataBuilder();
         } else {
            this.cascadingMetaDataBuilder = this.cascadingMetaDataBuilder.merge(constrainedExecutable.getCascadingMetaDataBuilder());
         }

         this.addToExecutablesByDeclaringType(constrainedExecutable);
         if (this.executable != null && this.overrides(constrainedExecutable.getExecutable(), this.executable)) {
            this.executable = constrainedExecutable.getExecutable();
         }

      }

      private void addToExecutablesByDeclaringType(ConstrainedExecutable executable) {
         Class beanClass = executable.getExecutable().getDeclaringClass();
         ConstrainedExecutable mergedExecutable = (ConstrainedExecutable)this.executablesByDeclaringType.get(beanClass);
         if (mergedExecutable != null) {
            mergedExecutable = mergedExecutable.merge(executable);
         } else {
            mergedExecutable = executable;
         }

         this.executablesByDeclaringType.put(beanClass, mergedExecutable);
      }

      public ExecutableMetaData build() {
         this.assertCorrectnessOfConfiguration();
         return new ExecutableMetaData(this.kind == ConstrainedElement.ConstrainedElementKind.CONSTRUCTOR ? this.executable.getDeclaringClass().getSimpleName() : this.executable.getName(), ReflectionHelper.typeOf(this.executable), this.executable.getParameterTypes(), this.kind == ConstrainedElement.ConstrainedElementKind.CONSTRUCTOR ? ElementKind.CONSTRUCTOR : ElementKind.METHOD, this.kind == ConstrainedElement.ConstrainedElementKind.CONSTRUCTOR ? Collections.singleton(ExecutableHelper.getSignature(this.executable)) : CollectionHelper.toImmutableSet(this.signatures), this.adaptOriginsAndImplicitGroups(this.getDirectConstraints()), this.adaptOriginsAndImplicitGroups(this.getContainerElementConstraints()), this.findParameterMetaData(), this.adaptOriginsAndImplicitGroups(this.crossParameterConstraints), this.cascadingMetaDataBuilder.build(this.valueExtractorManager, this.executable), this.isConstrained, this.isGetterMethod);
      }

      private List findParameterMetaData() {
         List parameterBuilders = null;
         Iterator var2 = this.constrainedExecutables.iterator();

         while(true) {
            while(var2.hasNext()) {
               ConstrainedExecutable oneExecutable = (ConstrainedExecutable)var2.next();
               if (parameterBuilders == null) {
                  parameterBuilders = CollectionHelper.newArrayList();
                  Iterator var9 = oneExecutable.getAllParameterMetaData().iterator();

                  while(var9.hasNext()) {
                     ConstrainedParameter oneParameter = (ConstrainedParameter)var9.next();
                     parameterBuilders.add(new ParameterMetaData.Builder(this.executable.getDeclaringClass(), oneParameter, this.constraintHelper, this.typeResolutionHelper, this.valueExtractorManager, this.parameterNameProvider));
                  }
               } else {
                  int i = 0;

                  for(Iterator var5 = oneExecutable.getAllParameterMetaData().iterator(); var5.hasNext(); ++i) {
                     ConstrainedParameter oneParameter = (ConstrainedParameter)var5.next();
                     ((ParameterMetaData.Builder)parameterBuilders.get(i)).add(oneParameter);
                  }
               }
            }

            List parameterMetaDatas = CollectionHelper.newArrayList();
            Iterator var8 = parameterBuilders.iterator();

            while(var8.hasNext()) {
               ParameterMetaData.Builder oneBuilder = (ParameterMetaData.Builder)var8.next();
               parameterMetaDatas.add(oneBuilder.build());
            }

            return parameterMetaDatas;
         }
      }

      private void assertCorrectnessOfConfiguration() {
         Iterator var1 = this.executablesByDeclaringType.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            Iterator var3 = this.executablesByDeclaringType.entrySet().iterator();

            while(var3.hasNext()) {
               Map.Entry otherEntry = (Map.Entry)var3.next();
               Iterator var5 = this.rules.iterator();

               while(var5.hasNext()) {
                  MethodConfigurationRule rule = (MethodConfigurationRule)var5.next();
                  rule.apply((ConstrainedExecutable)entry.getValue(), (ConstrainedExecutable)otherEntry.getValue());
               }
            }
         }

      }
   }
}
