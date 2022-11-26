package org.opensaml.saml.common.binding.decoding;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.messaging.decoder.MessageDecoder;

public interface SAMLMessageDecoder extends MessageDecoder {
   @Nonnull
   @NotEmpty
   String getBindingURI();
}
