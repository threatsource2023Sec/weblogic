package org.opensaml.soap.messaging.context;

import java.util.Set;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.collection.LazySet;
import org.opensaml.messaging.context.BaseContext;

public class InboundSOAPContext extends BaseContext {
   private LazySet nodeActors = new LazySet();
   private boolean finalDestination = true;
   private LazySet understoodHeaders = new LazySet();

   @Nonnull
   public Set getNodeActors() {
      return this.nodeActors;
   }

   @Nonnull
   public Set getUnderstoodHeaders() {
      return this.understoodHeaders;
   }

   public boolean isFinalDestination() {
      return this.finalDestination;
   }

   public void setFinalDestination(boolean newValue) {
      this.finalDestination = newValue;
   }
}
