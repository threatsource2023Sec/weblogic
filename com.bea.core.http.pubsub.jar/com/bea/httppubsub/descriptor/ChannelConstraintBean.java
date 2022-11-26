package com.bea.httppubsub.descriptor;

public interface ChannelConstraintBean {
   ChannelResourceCollectionBean[] getChannelResourceCollections();

   ChannelResourceCollectionBean createChannelResourceCollection();

   void destroyChannelResourceCollection(ChannelResourceCollectionBean var1);

   AuthConstraintBean getAuthConstraint();

   AuthConstraintBean createAuthConstraint();

   void destroyAuthConstraint(AuthConstraintBean var1);
}
