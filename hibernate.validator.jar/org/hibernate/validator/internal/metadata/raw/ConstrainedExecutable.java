package org.hibernate.validator.internal.metadata.raw;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ConstrainedExecutable extends AbstractConstrainedElement {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Executable executable;
   private final List parameterMetaData;
   private final boolean hasParameterConstraints;
   private final Set crossParameterConstraints;
   private final boolean isGetterMethod;

   public ConstrainedExecutable(ConfigurationSource source, Executable executable, Set returnValueConstraints, Set typeArgumentConstraints, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
      this(source, executable, Collections.emptyList(), Collections.emptySet(), returnValueConstraints, typeArgumentConstraints, cascadingMetaDataBuilder);
   }

   public ConstrainedExecutable(ConfigurationSource source, Executable executable, List parameterMetaData, Set crossParameterConstraints, Set returnValueConstraints, Set typeArgumentConstraints, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
      super(source, executable instanceof Constructor ? ConstrainedElement.ConstrainedElementKind.CONSTRUCTOR : ConstrainedElement.ConstrainedElementKind.METHOD, returnValueConstraints, typeArgumentConstraints, cascadingMetaDataBuilder);
      this.executable = executable;
      if (parameterMetaData.size() != executable.getParameterTypes().length) {
         throw LOG.getInvalidLengthOfParameterMetaDataListException(executable, executable.getParameterTypes().length, parameterMetaData.size());
      } else {
         this.crossParameterConstraints = CollectionHelper.toImmutableSet(crossParameterConstraints);
         this.parameterMetaData = CollectionHelper.toImmutableList(parameterMetaData);
         this.hasParameterConstraints = this.hasParameterConstraints(parameterMetaData) || !crossParameterConstraints.isEmpty();
         this.isGetterMethod = ReflectionHelper.isGetterMethod(executable);
      }
   }

   public ConstrainedParameter getParameterMetaData(int parameterIndex) {
      if (parameterIndex >= 0 && parameterIndex <= this.parameterMetaData.size() - 1) {
         return (ConstrainedParameter)this.parameterMetaData.get(parameterIndex);
      } else {
         throw LOG.getInvalidExecutableParameterIndexException(this.executable, parameterIndex);
      }
   }

   public List getAllParameterMetaData() {
      return this.parameterMetaData;
   }

   public Set getCrossParameterConstraints() {
      return this.crossParameterConstraints;
   }

   public boolean isConstrained() {
      return super.isConstrained() || this.hasParameterConstraints;
   }

   public boolean hasParameterConstraints() {
      return this.hasParameterConstraints;
   }

   public boolean isGetterMethod() {
      return this.isGetterMethod;
   }

   public Executable getExecutable() {
      return this.executable;
   }

   public String toString() {
      return "ConstrainedExecutable [executable=" + StringHelper.toShortString((Member)this.executable) + ", parameterMetaData=" + this.parameterMetaData + ", hasParameterConstraints=" + this.hasParameterConstraints + "]";
   }

   private boolean hasParameterConstraints(List parameterMetaData) {
      Iterator var2 = parameterMetaData.iterator();

      ConstrainedParameter oneParameter;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         oneParameter = (ConstrainedParameter)var2.next();
      } while(!oneParameter.isConstrained());

      return true;
   }

   public boolean isEquallyParameterConstrained(ConstrainedExecutable other) {
      if (!this.getDescriptors(this.crossParameterConstraints).equals(this.getDescriptors(other.crossParameterConstraints))) {
         return false;
      } else {
         int i = 0;

         for(Iterator var3 = this.parameterMetaData.iterator(); var3.hasNext(); ++i) {
            ConstrainedParameter parameter = (ConstrainedParameter)var3.next();
            ConstrainedParameter otherParameter = other.getParameterMetaData(i);
            if (!parameter.getCascadingMetaDataBuilder().equals(otherParameter.getCascadingMetaDataBuilder()) || !this.getDescriptors(parameter.getConstraints()).equals(this.getDescriptors(otherParameter.getConstraints()))) {
               return false;
            }
         }

         return true;
      }
   }

   public ConstrainedExecutable merge(ConstrainedExecutable other) {
      ConfigurationSource mergedSource = ConfigurationSource.max(this.source, other.source);
      List mergedParameterMetaData = CollectionHelper.newArrayList(this.parameterMetaData.size());
      int i = 0;

      for(Iterator var5 = this.parameterMetaData.iterator(); var5.hasNext(); ++i) {
         ConstrainedParameter parameter = (ConstrainedParameter)var5.next();
         mergedParameterMetaData.add(parameter.merge(other.getParameterMetaData(i)));
      }

      Set mergedCrossParameterConstraints = CollectionHelper.newHashSet((Collection)this.crossParameterConstraints);
      mergedCrossParameterConstraints.addAll(other.crossParameterConstraints);
      Set mergedReturnValueConstraints = CollectionHelper.newHashSet((Collection)this.constraints);
      mergedReturnValueConstraints.addAll(other.constraints);
      Set mergedTypeArgumentConstraints = new HashSet(this.typeArgumentConstraints);
      mergedTypeArgumentConstraints.addAll(other.typeArgumentConstraints);
      CascadingMetaDataBuilder mergedCascadingMetaDataBuilder = this.cascadingMetaDataBuilder.merge(other.cascadingMetaDataBuilder);
      return new ConstrainedExecutable(mergedSource, this.executable, mergedParameterMetaData, mergedCrossParameterConstraints, mergedReturnValueConstraints, mergedTypeArgumentConstraints, mergedCascadingMetaDataBuilder);
   }

   private Set getDescriptors(Iterable constraints) {
      Set descriptors = CollectionHelper.newHashSet();
      Iterator var3 = constraints.iterator();

      while(var3.hasNext()) {
         MetaConstraint constraint = (MetaConstraint)var3.next();
         descriptors.add(constraint.getDescriptor());
      }

      return descriptors;
   }

   public int hashCode() {
      int prime = true;
      int result = super.hashCode();
      result = 31 * result + (this.executable == null ? 0 : this.executable.hashCode());
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
         ConstrainedExecutable other = (ConstrainedExecutable)obj;
         if (this.executable == null) {
            if (other.executable != null) {
               return false;
            }
         } else if (!this.executable.equals(other.executable)) {
            return false;
         }

         return true;
      }
   }
}
