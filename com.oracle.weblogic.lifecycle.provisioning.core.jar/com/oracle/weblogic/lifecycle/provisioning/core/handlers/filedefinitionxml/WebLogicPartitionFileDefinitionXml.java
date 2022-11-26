package com.oracle.weblogic.lifecycle.provisioning.core.handlers.filedefinitionxml;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Documented
@HandlesResources({"_partition/file-definition.xml"})
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface WebLogicPartitionFileDefinitionXml {
}
