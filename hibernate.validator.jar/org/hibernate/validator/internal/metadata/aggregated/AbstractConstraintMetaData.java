package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.descriptor.ContainerElementTypeDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.location.TypeArgumentConstraintLocation;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.TypeVariables;

public abstract class AbstractConstraintMetaData implements ConstraintMetaData {
   private final String name;
   private final Type type;
   private final Set directConstraints;
   private final Set containerElementsConstraints;
   private final Set allConstraints;
   private final boolean isCascading;
   private final boolean isConstrained;

   public AbstractConstraintMetaData(String name, Type type, Set directConstraints, Set containerElementsConstraints, boolean isCascading, boolean isConstrained) {
      this.name = name;
      this.type = type;
      this.directConstraints = CollectionHelper.toImmutableSet(directConstraints);
      this.containerElementsConstraints = CollectionHelper.toImmutableSet(containerElementsConstraints);
      this.allConstraints = (Set)Stream.concat(directConstraints.stream(), containerElementsConstraints.stream()).collect(Collectors.collectingAndThen(Collectors.toSet(), CollectionHelper::toImmutableSet));
      this.isCascading = isCascading;
      this.isConstrained = isConstrained;
   }

   public String getName() {
      return this.name;
   }

   public Type getType() {
      return this.type;
   }

   public Iterator iterator() {
      return this.allConstraints.iterator();
   }

   public Set getAllConstraints() {
      return this.allConstraints;
   }

   public Set getDirectConstraints() {
      return this.directConstraints;
   }

   public Set getContainerElementsConstraints() {
      return this.containerElementsConstraints;
   }

   public final boolean isCascading() {
      return this.isCascading;
   }

   public boolean isConstrained() {
      return this.isConstrained;
   }

   public String toString() {
      return "AbstractConstraintMetaData [name=" + this.name + ", type=" + this.type + ", directConstraints=" + this.directConstraints + ", containerElementsConstraints=" + this.containerElementsConstraints + ", isCascading=" + this.isCascading + ", isConstrained=" + this.isConstrained + "]";
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         AbstractConstraintMetaData other = (AbstractConstraintMetaData)obj;
         if (this.name == null) {
            if (other.name != null) {
               return false;
            }
         } else if (!this.name.equals(other.name)) {
            return false;
         }

         return true;
      }
   }

   protected Set asDescriptors(Set constraints) {
      Set theValue = CollectionHelper.newHashSet();
      Iterator var3 = constraints.iterator();

      while(var3.hasNext()) {
         MetaConstraint oneConstraint = (MetaConstraint)var3.next();
         theValue.add(oneConstraint.getDescriptor());
      }

      return theValue;
   }

   protected Set asContainerElementTypeDescriptors(Set containerElementsConstraints, CascadingMetaData cascadingMetaData, boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      return this.asContainerElementTypeDescriptors(this.type, AbstractConstraintMetaData.ContainerElementMetaDataTree.of(cascadingMetaData, containerElementsConstraints), defaultGroupSequenceRedefined, defaultGroupSequence);
   }

   private Set asContainerElementTypeDescriptors(Type type, ContainerElementMetaDataTree containerElementMetaDataTree, boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      Set containerElementTypeDescriptors = new HashSet();
      Iterator var6 = containerElementMetaDataTree.nodes.entrySet().iterator();

      while(var6.hasNext()) {
         Map.Entry entry = (Map.Entry)var6.next();
         TypeVariable childTypeParameter = (TypeVariable)entry.getKey();
         ContainerElementMetaDataTree childContainerElementMetaDataTree = (ContainerElementMetaDataTree)entry.getValue();
         Set childrenDescriptors = this.asContainerElementTypeDescriptors(childContainerElementMetaDataTree.elementType, childContainerElementMetaDataTree, defaultGroupSequenceRedefined, defaultGroupSequence);
         containerElementTypeDescriptors.add(new ContainerElementTypeDescriptorImpl(childContainerElementMetaDataTree.elementType, childContainerElementMetaDataTree.containerClass, TypeVariables.getTypeParameterIndex(childTypeParameter), this.asDescriptors(childContainerElementMetaDataTree.constraints), childrenDescriptors, childContainerElementMetaDataTree.cascading, defaultGroupSequenceRedefined, defaultGroupSequence, childContainerElementMetaDataTree.groupConversionDescriptors));
      }

      return containerElementTypeDescriptors;
   }

   private static class ContainerElementMetaDataTree {
      private final Map nodes = new HashMap();
      private Type elementType = null;
      private Class containerClass;
      private final Set constraints = new HashSet();
      private boolean cascading = false;
      private Set groupConversionDescriptors = new HashSet();

      private static ContainerElementMetaDataTree of(CascadingMetaData cascadingMetaData, Set containerElementsConstraints) {
         ContainerElementMetaDataTree containerElementMetaConstraintTree = new ContainerElementMetaDataTree();
         Iterator var3 = containerElementsConstraints.iterator();

         while(var3.hasNext()) {
            MetaConstraint constraint = (MetaConstraint)var3.next();
            ConstraintLocation currentLocation = constraint.getLocation();

            ArrayList constraintPath;
            TypeArgumentConstraintLocation typeArgumentConstraintLocation;
            for(constraintPath = new ArrayList(); currentLocation instanceof TypeArgumentConstraintLocation; currentLocation = typeArgumentConstraintLocation.getDelegate()) {
               typeArgumentConstraintLocation = (TypeArgumentConstraintLocation)currentLocation;
               constraintPath.add(typeArgumentConstraintLocation.getTypeParameter());
            }

            Collections.reverse(constraintPath);
            containerElementMetaConstraintTree.addConstraint(constraintPath, constraint);
         }

         if (cascadingMetaData != null && cascadingMetaData.isContainer() && cascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements()) {
            containerElementMetaConstraintTree.addCascadingMetaData(new ArrayList(), (ContainerCascadingMetaData)cascadingMetaData.as(ContainerCascadingMetaData.class));
         }

         return containerElementMetaConstraintTree;
      }

      private void addConstraint(List path, MetaConstraint constraint) {
         ContainerElementMetaDataTree tree = this;

         TypeVariable typeArgument;
         for(Iterator var4 = path.iterator(); var4.hasNext(); tree = (ContainerElementMetaDataTree)tree.nodes.computeIfAbsent(typeArgument, (ta) -> {
            return new ContainerElementMetaDataTree();
         })) {
            typeArgument = (TypeVariable)var4.next();
         }

         TypeArgumentConstraintLocation constraintLocation = (TypeArgumentConstraintLocation)constraint.getLocation();
         tree.elementType = constraintLocation.getTypeForValidatorResolution();
         tree.containerClass = ((TypeArgumentConstraintLocation)constraint.getLocation()).getContainerClass();
         tree.constraints.add(constraint);
      }

      private void addCascadingMetaData(List path, ContainerCascadingMetaData cascadingMetaData) {
         Iterator var3 = cascadingMetaData.getContainerElementTypesCascadingMetaData().iterator();

         while(var3.hasNext()) {
            ContainerCascadingMetaData nestedCascadingMetaData = (ContainerCascadingMetaData)var3.next();
            List nestedPath = new ArrayList(path);
            nestedPath.add(nestedCascadingMetaData.getTypeParameter());
            ContainerElementMetaDataTree tree = this;

            TypeVariable typeArgument;
            for(Iterator var7 = nestedPath.iterator(); var7.hasNext(); tree = (ContainerElementMetaDataTree)tree.nodes.computeIfAbsent(typeArgument, (ta) -> {
               return new ContainerElementMetaDataTree();
            })) {
               typeArgument = (TypeVariable)var7.next();
            }

            tree.elementType = TypeVariables.getContainerElementType(nestedCascadingMetaData.getEnclosingType(), nestedCascadingMetaData.getTypeParameter());
            tree.containerClass = nestedCascadingMetaData.getDeclaredContainerClass();
            tree.cascading = nestedCascadingMetaData.isCascading();
            tree.groupConversionDescriptors = nestedCascadingMetaData.getGroupConversionDescriptors();
            if (nestedCascadingMetaData.isMarkedForCascadingOnAnnotatedObjectOrContainerElements()) {
               this.addCascadingMetaData(nestedPath, nestedCascadingMetaData);
            }
         }

      }
   }
}
