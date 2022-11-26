package org.opensaml.saml.common.binding.encoding;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.encoder.MessageEncoder;

public interface SAMLMessageEncoder extends MessageEncoder {
   @Nonnull
   @NotEmpty
   String getBindingURI();
}
