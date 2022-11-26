package org.hibernate.validator.internal.metadata.descriptor;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.groups.Default;
import javax.validation.metadata.ElementDescriptor;
import javax.validation.metadata.Scope;
import org.hibernate.validator.internal.engine.groups.Group;
import org.hibernate.validator.internal.engine.groups.ValidationOrder;
import org.hibernate.validator.internal.engine.groups.ValidationOrderGenerator;
import org.hibernate.validator.internal.metadata.core.ConstraintOrigin;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.TypeHelper;

public abstract class ElementDescriptorImpl implements ElementDescriptor, Serializable {
   private final Class type;
   private final Set constraintDescriptors;
   private final boolean defaultGroupSequenceRedefined;
   private final List defaultGroupSequence;

   public ElementDescriptorImpl(Type type, Set constraintDescriptors, boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      this.type = (Class)TypeHelper.getErasedType(type);
      this.constraintDescriptors = CollectionHelper.toImmutableSet(constraintDescriptors);
      this.defaultGroupSequenceRedefined = defaultGroupSequenceRedefined;
      this.defaultGroupSequence = CollectionHelper.toImmutableList(defaultGroupSequence);
   }

   public final boolean hasConstraints() {
      return this.constraintDescriptors.size() != 0;
   }

   public final Class getElementClass() {
      return this.type;
   }

   public final Set getConstraintDescriptors() {
      return this.findConstraints().getConstraintDescriptors();
   }

   public final ElementDescriptor.ConstraintFinder findConstraints() {
      return new ConstraintFinderImpl();
   }

   private class ConstraintFinderImpl implements ElementDescriptor.ConstraintFinder {
      private List groups;
      private final EnumSet definedInSet;
      private final EnumSet elementTypes;

      ConstraintFinderImpl() {
         this.elementTypes = EnumSet.of(ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE_USE, ElementType.PARAMETER);
         this.definedInSet = EnumSet.allOf(ConstraintOrigin.class);
         this.groups = Collections.emptyList();
      }

      public ElementDescriptor.ConstraintFinder unorderedAndMatchingGroups(Class... classes) {
         this.groups = CollectionHelper.newArrayList();
         Class[] var2 = classes;
         int var3 = classes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class clazz = var2[var4];
            if (Default.class.equals(clazz) && ElementDescriptorImpl.this.defaultGroupSequenceRedefined) {
               this.groups.addAll(ElementDescriptorImpl.this.defaultGroupSequence);
            } else {
               this.groups.add(clazz);
            }
         }

         return this;
      }

      public ElementDescriptor.ConstraintFinder lookingAt(Scope visibility) {
         if (visibility.equals(Scope.LOCAL_ELEMENT)) {
            this.definedInSet.remove(ConstraintOrigin.DEFINED_IN_HIERARCHY);
         }

         return this;
      }

      public ElementDescriptor.ConstraintFinder declaredOn(ElementType... elementTypes) {
         this.elementTypes.clear();
         this.elementTypes.addAll(Arrays.asList(elementTypes));
         return this;
      }

      public Set getConstraintDescriptors() {
         Set matchingDescriptors = new HashSet();
         this.findMatchingDescriptors(matchingDescriptors);
         return CollectionHelper.toImmutableSet(matchingDescriptors);
      }

      public boolean hasConstraints() {
         return this.getConstraintDescriptors().size() != 0;
      }

      private void addMatchingDescriptorsForGroup(Class group, Set matchingDescriptors) {
         Iterator var3 = ElementDescriptorImpl.this.constraintDescriptors.iterator();

         while(var3.hasNext()) {
            ConstraintDescriptorImpl descriptor = (ConstraintDescriptorImpl)var3.next();
            if (this.definedInSet.contains(descriptor.getDefinedOn()) && this.elementTypes.contains(descriptor.getElementType()) && descriptor.getGroups().contains(group)) {
               matchingDescriptors.add(descriptor);
            }
         }

      }

      private void findMatchingDescriptors(Set matchingDescriptors) {
         if (!this.groups.isEmpty()) {
            ValidationOrder validationOrder = (new ValidationOrderGenerator()).getValidationOrder(this.groups);
            Iterator groupIterator = validationOrder.getGroupIterator();

            while(groupIterator.hasNext()) {
               Group g = (Group)groupIterator.next();
               this.addMatchingDescriptorsForGroup(g.getDefiningClass(), matchingDescriptors);
            }
         } else {
            Iterator var5 = ElementDescriptorImpl.this.constraintDescriptors.iterator();

            while(var5.hasNext()) {
               ConstraintDescriptorImpl descriptor = (ConstraintDescriptorImpl)var5.next();
               if (this.definedInSet.contains(descriptor.getDefinedOn()) && this.elementTypes.contains(descriptor.getElementType())) {
                  matchingDescriptors.add(descriptor);
               }
            }
         }

      }
   }
}
