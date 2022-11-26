package org.hibernate.validator.internal.metadata.descriptor;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.metadata.ConstructorDescriptor;
import javax.validation.metadata.CrossParameterDescriptor;
import javax.validation.metadata.MethodDescriptor;
import javax.validation.metadata.ParameterDescriptor;
import javax.validation.metadata.ReturnValueDescriptor;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ExecutableDescriptorImpl extends ElementDescriptorImpl implements ConstructorDescriptor, MethodDescriptor {
   private final String name;
   private final List parameters;
   private final CrossParameterDescriptor crossParameterDescriptor;
   private final ReturnValueDescriptor returnValueDescriptor;
   private final boolean isGetter;

   public ExecutableDescriptorImpl(Type returnType, String name, Set crossParameterConstraints, ReturnValueDescriptor returnValueDescriptor, List parameters, boolean defaultGroupSequenceRedefined, boolean isGetter, List defaultGroupSequence) {
      super(returnType, Collections.emptySet(), defaultGroupSequenceRedefined, defaultGroupSequence);
      this.name = name;
      this.parameters = CollectionHelper.toImmutableList(parameters);
      this.returnValueDescriptor = returnValueDescriptor;
      this.crossParameterDescriptor = new CrossParameterDescriptorImpl(crossParameterConstraints, defaultGroupSequenceRedefined, defaultGroupSequence);
      this.isGetter = isGetter;
   }

   public String getName() {
      return this.name;
   }

   public List getParameterDescriptors() {
      return this.parameters;
   }

   public ReturnValueDescriptor getReturnValueDescriptor() {
      return this.returnValueDescriptor;
   }

   public boolean hasConstrainedParameters() {
      if (this.crossParameterDescriptor.hasConstraints()) {
         return true;
      } else {
         Iterator var1 = this.parameters.iterator();

         ParameterDescriptor oneParameter;
         do {
            if (!var1.hasNext()) {
               return false;
            }

            oneParameter = (ParameterDescriptor)var1.next();
         } while(!oneParameter.hasConstraints() && !oneParameter.isCascaded());

         return true;
      }
   }

   public boolean hasConstrainedReturnValue() {
      return this.returnValueDescriptor != null && (this.returnValueDescriptor.hasConstraints() || this.returnValueDescriptor.isCascaded());
   }

   public CrossParameterDescriptor getCrossParameterDescriptor() {
      return this.crossParameterDescriptor;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ExecutableDescriptorImpl");
      sb.append("{name='").append(this.name).append('\'');
      sb.append('}');
      return sb.toString();
   }

   public boolean isGetter() {
      return this.isGetter;
   }
}
