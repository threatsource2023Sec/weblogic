package org.opensaml.soap.client;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.security.SecurityException;
import org.opensaml.soap.common.SOAPException;

@ThreadSafe
public interface SOAPClient {
   void send(@Nonnull @NotEmpty String var1, @Nonnull InOutOperationContext var2) throws SOAPException, SecurityException;

   public interface SOAPRequestParameters {
   }
}
