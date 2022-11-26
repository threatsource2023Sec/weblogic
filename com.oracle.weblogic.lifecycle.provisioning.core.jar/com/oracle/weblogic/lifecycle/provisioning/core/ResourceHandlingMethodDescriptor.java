package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import java.lang.reflect.Method;

interface ResourceHandlingMethodDescriptor {
   HandlesResources getHandlesResourcesQualifier();

   Method getMethod();
}
