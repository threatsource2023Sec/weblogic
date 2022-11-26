package com.oracle.pitchfork.interfaces;

import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;

public interface WSEEComponentContributorBroker {
   void init(ComponentContributor var1);

   Object getBean(String var1) throws IllegalAccessException, InstantiationException, ClassNotFoundException;

   Jsr250MetadataI newJsr250Metadata(String var1, Class var2, DeploymentUnitMetadataI var3);
}
