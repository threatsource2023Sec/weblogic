package javax.jdo.metadata;

import javax.jdo.annotations.SequenceStrategy;

public interface SequenceMetadata extends Metadata {
   String getName();

   SequenceStrategy getSequenceStrategy();

   SequenceMetadata setDatastoreSequence(String var1);

   String getDatastoreSequence();

   SequenceMetadata setFactoryClass(String var1);

   String getFactoryClass();

   SequenceMetadata setInitialValue(int var1);

   Integer getInitialValue();

   SequenceMetadata setAllocationSize(int var1);

   Integer getAllocationSize();
}
