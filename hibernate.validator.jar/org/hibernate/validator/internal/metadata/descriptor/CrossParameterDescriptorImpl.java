package org.hibernate.validator.internal.metadata.descriptor;

import java.util.List;
import java.util.Set;
import javax.validation.metadata.CrossParameterDescriptor;

public class CrossParameterDescriptorImpl extends ElementDescriptorImpl implements CrossParameterDescriptor {
   public CrossParameterDescriptorImpl(Set constraintDescriptors, boolean defaultGroupSequenceRedefined, List defaultGroupSequence) {
      super(Object[].class, constraintDescriptors, defaultGroupSequenceRedefined, defaultGroupSequence);
   }
}
