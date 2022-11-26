package com.oracle.weblogic.lifecycle.provisioning.api;

import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import org.glassfish.hk2.api.AnnotationLiteral;

public final class ProvisioningOperationScopedLiteral extends AnnotationLiteral implements ProvisioningOperationScoped {
   private static final long serialVersionUID = -3210966824991506886L;
   public static final ProvisioningOperationScoped instance = new ProvisioningOperationScopedLiteral();
}
