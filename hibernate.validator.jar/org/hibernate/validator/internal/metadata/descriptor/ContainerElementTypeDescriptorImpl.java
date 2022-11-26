package org.hibernate.validator.internal.metadata.descriptor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import javax.validation.metadata.ContainerElementTypeDescriptor;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ContainerElementTypeDescriptorImpl extends ElementDescriptorImpl implements ContainerElementTypeDescriptor {
   private final Class containerClass;
   private final Integer typeArgumentIndex;
   private final Set constrainedContainerElementTypes;
   private final boolean cascaded;
   private final Set groupConversions;

   public ContainerElementTypeDescriptorImpl(Type type, Class containerClass, Integer typeArgumentIndex, Set constraints, Set constrainedContainerElementTypes, boolean cascaded, boolean defaultGroupSequenceRedefined, List defaultGroupSequence, Set groupConversions) {
      super(type, constraints, defaultGroupSequenceRedefined, defaultGroupSequence);
      this.containerClass = containerClass;
      this.typeArgumentIndex = typeArgumentIndex;
      this.constrainedContainerElementTypes = CollectionHelper.toImmutableSet(constrainedContainerElementTypes);
      this.cascaded = cascaded;
      this.groupConversions = CollectionHelper.toImmutableSet(groupConversions);
   }

   public Class getContainerClass() {
      return this.containerClass;
   }

   public Integer getTypeArgumentIndex() {
      return this.typeArgumentIndex;
   }

   public Set getConstrainedContainerElementTypes() {
      return this.constrainedContainerElementTypes;
   }

   public boolean isCascaded() {
      return this.cascaded;
   }

   public Set getGroupConversions() {
      return this.groupConversions;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ContainerElementTypeDescriptorImpl{");
      sb.append("containerClass=").append(this.containerClass);
      sb.append(", typeArgumentIndex=").append(this.typeArgumentIndex);
      sb.append(", cascaded=").append(this.cascaded);
      sb.append('}');
      return sb.toString();
   }
}
