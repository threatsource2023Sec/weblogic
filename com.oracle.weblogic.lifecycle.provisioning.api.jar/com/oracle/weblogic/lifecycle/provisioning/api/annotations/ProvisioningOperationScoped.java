package com.oracle.weblogic.lifecycle.provisioning.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Scope;
import org.glassfish.hk2.api.Proxiable;
import org.glassfish.hk2.api.ProxyForSameScope;

@Documented
@Proxiable
@ProxyForSameScope(false)
@Retention(RetentionPolicy.RUNTIME)
@Scope
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
public @interface ProvisioningOperationScoped {
}
