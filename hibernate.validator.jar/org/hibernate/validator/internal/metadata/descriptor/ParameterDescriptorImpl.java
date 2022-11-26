package org.hibernate.validator.internal.metadata.descriptor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import javax.validation.metadata.ParameterDescriptor;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ParameterDescriptorImpl extends ElementDescriptorImpl implements ParameterDescriptor {
   private final int index;
   private final String name;
   private final Set constrainedContainerElementTypes;
   private final boolean cascaded;
   private final Set groupConversions;

   public ParameterDescriptorImpl(Type type, int index, String name, Set constraints, Set constrainedContainerElementTypes, boolean isCascaded, boolean defaultGroupSequenceRedefined, List defaultGroupSequence, Set groupConversions) {
      super(type, constraints, defaultGroupSequenceRedefined, defaultGroupSequence);
      this.index = index;
      this.name = name;
      this.constrainedContainerElementTypes = CollectionHelper.toImmutableSet(constrainedContainerElementTypes);
      this.cascaded = isCascaded;
      this.groupConversions = CollectionHelper.toImmutableSet(groupConversions);
   }

   public int getIndex() {
      return this.index;
   }

   public Set getConstrainedContainerElementTypes() {
      return this.constrainedContainerElementTypes;
   }

   public String getName() {
      return this.name;
   }

   public boolean isCascaded() {
      return this.cascaded;
   }

   public Set getGroupConversions() {
      return this.groupConversions;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("ParameterDescriptorImpl");
      sb.append("{cascaded=").append(this.cascaded);
      sb.append(", index=").append(this.index);
      sb.append(", name=").append(this.name);
      sb.append('}');
      return sb.toString();
   }
}
