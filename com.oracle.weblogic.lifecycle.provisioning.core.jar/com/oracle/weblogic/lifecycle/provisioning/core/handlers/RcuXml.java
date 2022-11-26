package com.oracle.weblogic.lifecycle.provisioning.core.handlers;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.HandlesResources;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@HandlesResources({"_partition/rcu.xml"})
public @interface RcuXml {
}
