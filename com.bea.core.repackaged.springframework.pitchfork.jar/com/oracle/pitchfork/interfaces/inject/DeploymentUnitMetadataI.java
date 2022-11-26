package com.oracle.pitchfork.interfaces.inject;

import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import java.util.List;

public interface DeploymentUnitMetadataI {
   List getDefaultInterceptorMetadata();

   void registerDefaultInterceptorMetadata(InterceptorMetadataI var1);

   List getDeployedComponentMetadata();

   boolean isLimitToSpec();

   void startup();
}
