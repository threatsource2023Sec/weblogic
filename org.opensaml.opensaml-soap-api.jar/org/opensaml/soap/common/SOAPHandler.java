package org.opensaml.soap.common;

import java.util.Set;
import javax.annotation.Nonnull;
import org.opensaml.messaging.handler.MessageHandler;

public interface SOAPHandler extends MessageHandler {
   @Nonnull
   Set understandsHeaders();
}
