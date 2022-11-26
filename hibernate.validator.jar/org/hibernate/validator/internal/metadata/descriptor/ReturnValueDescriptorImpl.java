package org.hibernate.validator.internal.metadata.descriptor;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import javax.validation.metadata.ReturnValueDescriptor;
import org.hibernate.validator.internal.util.CollectionHelper;

public class ReturnValueDescriptorImpl extends ElementDescriptorImpl implements ReturnValueDescriptor {
   private final Set constrainedContainerElementTypes;
   private final boolean cascaded;
   private final Set groupConversions;

   public ReturnValueDescriptorImpl(Type returnType, Set returnValueConstraints, Set constrainedContainerElementTypes, boolean cascaded, boolean defaultGroupSequenceRedefined, List defaultGroupSequence, Set groupConversions) {
      super(returnType, returnValueConstraints, defaultGroupSequenceRedefined, defaultGroupSequence);
      this.constrainedContainerElementTypes = CollectionHelper.toImmutableSet(constrainedContainerElementTypes);
      this.cascaded = cascaded;
      this.groupConversions = CollectionHelper.toImmutableSet(groupConversions);
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
      sb.append("ReturnValueDescriptorImpl");
      sb.append("{cascaded=").append(this.cascaded);
      sb.append('}');
      return sb.toString();
   }
}
