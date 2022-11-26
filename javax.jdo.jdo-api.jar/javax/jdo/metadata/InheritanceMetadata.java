package javax.jdo.metadata;

import javax.jdo.annotations.InheritanceStrategy;

public interface InheritanceMetadata extends Metadata {
   InheritanceMetadata setStrategy(InheritanceStrategy var1);

   InheritanceStrategy getStrategy();

   InheritanceMetadata setCustomStrategy(String var1);

   String getCustomStrategy();

   DiscriminatorMetadata newDiscriminatorMetadata();

   DiscriminatorMetadata getDiscriminatorMetadata();

   JoinMetadata newJoinMetadata();

   JoinMetadata getJoinMetadata();
}
