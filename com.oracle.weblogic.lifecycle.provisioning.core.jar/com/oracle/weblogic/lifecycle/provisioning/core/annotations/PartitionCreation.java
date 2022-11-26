package com.oracle.weblogic.lifecycle.provisioning.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.glassfish.hk2.extras.interception.InterceptionBinder;

@InterceptionBinder
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PartitionCreation {
}
