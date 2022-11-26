package com.oracle.pitchfork.interfaces;

import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;

public interface ManagedBeanContributorBroker {
   void initialize(ClassLoader var1, ComponentContributor var2);

   Object createBeanInstance(String var1, Class var2, boolean var3) throws IllegalAccessException, InstantiationException;

   Jsr250MetadataI createJsr250Metadata(DeploymentUnitMetadataI var1, String var2, Class var3);
}
