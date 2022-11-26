package com.oracle.weblogic.lifecycle.provisioning.core.handlers.buildxml;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import com.oracle.weblogic.lifecycle.provisioning.wls.PartitionProvisioningResourceSelector;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Documented
@HandlesResources(
   value = {"build.xml"},
   selectorClass = PartitionProvisioningResourceSelector.class
)
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BuildXml {
}
