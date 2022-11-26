package com.oracle.pitchfork.interfaces.inject;

public interface ComponentContributor {
   void contribute(EnricherI var1);

   Jsr250MetadataI newJsr250Metadata(String var1, Class var2, DeploymentUnitMetadataI var3);

   Jsr250MetadataI getMetadata(String var1);
}
