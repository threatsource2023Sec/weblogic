package org.opensaml.saml.common.messaging.context;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import org.opensaml.messaging.context.BaseContext;

public class ChannelBindingsContext extends BaseContext {
   @Nonnull
   @NonnullElements
   private Collection channelBindings = new ArrayList();

   @Nonnull
   @NonnullElements
   @Live
   public Collection getChannelBindings() {
      return this.channelBindings;
   }
}
