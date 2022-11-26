package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ElementKind;
import org.hibernate.validator.HibernateValidatorPermission;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.core.ConstraintHelper;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.core.MetaConstraints;
import org.hibernate.validator.internal.metadata.descriptor.PropertyDescriptorImpl;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.location.GetterConstraintLocation;
import org.hibernate.validator.internal.metadata.location.TypeArgumentConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedField;
import org.hibernate.validator.internal.metadata.raw.ConstrainedType;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.TypeResolutionHelper;
import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredMethod;

public class PropertyMetaData extends AbstractConstraintMetaData {
   private final Set cascadables;

   private PropertyMetaData(String propertyName, Type type, Set constraints, Set containerElementsConstraints, Set cascadables, boolean cascadingProperty) {
      super(propertyName, type, constraints, containerElementsConstraints, !cascadables.isEmpty(), !cascadables.isEmpty() || !constraints.isEmpty() || !containerElementsConstraints.isEmpty());
      this.cascadables = CollectionHelper.toImmutableSet(cascadables);
   }

   public PropertyDescriptorImpl asDescriptor(boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      CascadingMetaData firstCascadingMetaData = this.cascadables.isEmpty() ? null : ((Cascadable)this.cascadables.iterator().next()).getCascadingMetaData();
      return new PropertyDescriptorImpl(this.getType(), this.getName(), this.asDescriptors(this.getDirectConstraints()), this.asContainerElementTypeDescriptors(this.getContainerElementsConstraints(), firstCascadingMetaData, defaultGroupSequenceRedefined, defaultGroupSequence), firstCascadingMetaData != null ? firstCascadingMetaData.isCascading() : false, defaultGroupSequenceRedefined, defaultGroupSequence, firstCascadingMetaData != null ? firstCascadingMetaData.getGroupConversionDescriptors() : Collections.emptySet());
   }

   public Set getCascadables() {
      return this.cascadables;
   }

   public String toString() {
      return "PropertyMetaData [type=" + this.getType() + ", propertyName=" + this.getName() + "]]";
   }

   public ElementKind getKind() {
      return ElementKind.PROPERTY;
   }

   public int hashCode() {
      return super.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         return this.getClass() == obj.getClass();
      }
   }

   // $FF: synthetic method
   PropertyMetaData(String x0, Type x1, Set x2, Set x3, Set x4, boolean x5, Object x6) {
      this(x0, x1, x2, x3, x4, x5);
   }

   public static class Builder extends MetaDataBuilder {
      private static final EnumSet SUPPORTED_ELEMENT_KINDS;
      private final String propertyName;
      private final Map cascadableBuilders = new HashMap();
      private final Type propertyType;
      private boolean cascadingProperty = false;
      private Method getterAccessibleMethod;

      public Builder(Class beanClass, ConstrainedField constrainedField, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
         super(beanClass, constraintHelper, typeResolutionHelper, valueExtractorManager);
         this.propertyName = constrainedField.getField().getName();
         this.propertyType = ReflectionHelper.typeOf(constrainedField.getField());
         this.add(constrainedField);
      }

      public Builder(Class beanClass, ConstrainedType constrainedType, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
         super(beanClass, constraintHelper, typeResolutionHelper, valueExtractorManager);
         this.propertyName = null;
         this.propertyType = null;
         this.add(constrainedType);
      }

      public Builder(Class beanClass, ConstrainedExecutable constrainedMethod, ConstraintHelper constraintHelper, TypeResolutionHelper typeResolutionHelper, ValueExtractorManager valueExtractorManager) {
         super(beanClass, constraintHelper, typeResolutionHelper, valueExtractorManager);
         this.propertyName = ReflectionHelper.getPropertyName(constrainedMethod.getExecutable());
         this.propertyType = ReflectionHelper.typeOf(constrainedMethod.getExecutable());
         this.add(constrainedMethod);
      }

      public boolean accepts(ConstrainedElement constrainedElement) {
         if (!SUPPORTED_ELEMENT_KINDS.contains(constrainedElement.getKind())) {
            return false;
         } else {
            return constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.METHOD && !((ConstrainedExecutable)constrainedElement).isGetterMethod() ? false : Objects.equals(this.getPropertyName(constrainedElement), this.propertyName);
         }
      }

      public final void add(ConstrainedElement constrainedElement) {
         if (constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.METHOD && constrainedElement.isConstrained()) {
            this.getterAccessibleMethod = this.getAccessible((Method)((ConstrainedExecutable)constrainedElement).getExecutable());
         }

         super.add(constrainedElement);
         this.cascadingProperty = this.cascadingProperty || constrainedElement.getCascadingMetaDataBuilder().isCascading();
         if (constrainedElement.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements() || constrainedElement.getCascadingMetaDataBuilder().hasGroupConversionsOnAnnotatedObjectOrContainerElements()) {
            Cascadable.Builder builder;
            if (constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.FIELD) {
               Field field = ((ConstrainedField)constrainedElement).getField();
               builder = (Cascadable.Builder)this.cascadableBuilders.get(field);
               if (builder == null) {
                  Cascadable.Builder builder = new FieldCascadable.Builder(this.valueExtractorManager, field, constrainedElement.getCascadingMetaDataBuilder());
                  this.cascadableBuilders.put(field, builder);
               } else {
                  builder.mergeCascadingMetaData(constrainedElement.getCascadingMetaDataBuilder());
               }
            } else if (constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.METHOD) {
               Method method = (Method)((ConstrainedExecutable)constrainedElement).getExecutable();
               builder = (Cascadable.Builder)this.cascadableBuilders.get(method);
               if (builder == null) {
                  Cascadable.Builder builder = new GetterCascadable.Builder(this.valueExtractorManager, this.getterAccessibleMethod, constrainedElement.getCascadingMetaDataBuilder());
                  this.cascadableBuilders.put(method, builder);
               } else {
                  builder.mergeCascadingMetaData(constrainedElement.getCascadingMetaDataBuilder());
               }
            }
         }

      }

      protected Set adaptConstraints(ConstrainedElement constrainedElement, Set constraints) {
         if (!constraints.isEmpty() && constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.METHOD) {
            ConstraintLocation getterConstraintLocation = ConstraintLocation.forGetter(this.getterAccessibleMethod);
            return (Set)constraints.stream().map((c) -> {
               return this.withGetterLocation(getterConstraintLocation, c);
            }).collect(Collectors.toSet());
         } else {
            return constraints;
         }
      }

      private MetaConstraint withGetterLocation(ConstraintLocation getterConstraintLocation, MetaConstraint constraint) {
         ConstraintLocation converted = null;
         if (!(constraint.getLocation() instanceof TypeArgumentConstraintLocation)) {
            if (constraint.getLocation() instanceof GetterConstraintLocation) {
               converted = constraint.getLocation();
            } else {
               converted = getterConstraintLocation;
            }
         } else {
            Deque locationStack = new ArrayDeque();
            ConstraintLocation current = constraint.getLocation();

            do {
               locationStack.addFirst(current);
               if (current instanceof TypeArgumentConstraintLocation) {
                  current = ((TypeArgumentConstraintLocation)current).getDelegate();
               } else {
                  current = null;
               }
            } while(current != null);

            Iterator var6 = locationStack.iterator();

            while(var6.hasNext()) {
               ConstraintLocation location = (ConstraintLocation)var6.next();
               if (!(location instanceof TypeArgumentConstraintLocation)) {
                  if (location instanceof GetterConstraintLocation) {
                     converted = location;
                  } else {
                     converted = getterConstraintLocation;
                  }
               } else {
                  converted = ConstraintLocation.forTypeArgument(converted, ((TypeArgumentConstraintLocation)location).getTypeParameter(), location.getTypeForValidatorResolution());
               }
            }
         }

         return MetaConstraints.create(this.typeResolutionHelper, this.valueExtractorManager, constraint.getDescriptor(), converted);
      }

      private String getPropertyName(ConstrainedElement constrainedElement) {
         if (constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.FIELD) {
            return ReflectionHelper.getPropertyName(((ConstrainedField)constrainedElement).getField());
         } else {
            return constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.METHOD ? ReflectionHelper.getPropertyName(((ConstrainedExecutable)constrainedElement).getExecutable()) : null;
         }
      }

      private Method getAccessible(Method original) {
         SecurityManager sm = System.getSecurityManager();
         if (sm != null) {
            sm.checkPermission(HibernateValidatorPermission.ACCESS_PRIVATE_MEMBERS);
         }

         Class clazz = original.getDeclaringClass();
         return (Method)this.run(GetDeclaredMethod.andMakeAccessible(clazz, original.getName()));
      }

      private Object run(PrivilegedAction action) {
         return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
      }

      public PropertyMetaData build() {
         Set cascadables = (Set)this.cascadableBuilders.values().stream().map((b) -> {
            return b.build();
         }).collect(Collectors.toSet());
         return new PropertyMetaData(this.propertyName, this.propertyType, this.adaptOriginsAndImplicitGroups(this.getDirectConstraints()), this.adaptOriginsAndImplicitGroups(this.getContainerElementConstraints()), cascadables, this.cascadingProperty);
      }

      static {
         SUPPORTED_ELEMENT_KINDS = EnumSet.of(ConstrainedElement.ConstrainedElementKind.TYPE, ConstrainedElement.ConstrainedElementKind.FIELD, ConstrainedElement.ConstrainedElementKind.METHOD);
      }
   }
}
