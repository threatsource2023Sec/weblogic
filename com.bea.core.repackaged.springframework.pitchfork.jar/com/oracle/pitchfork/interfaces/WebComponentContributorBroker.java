package com.oracle.pitchfork.interfaces;

import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;

public interface WebComponentContributorBroker {
   void initialize(ClassLoader var1, String var2, String var3, boolean var4, ComponentContributor var5);

   Object getNewInstance(String var1) throws IllegalAccessException, InstantiationException, ClassNotFoundException;

   Jsr250MetadataI createJsr250Metadata(DeploymentUnitMetadataI var1, String var2, Class var3);
}
