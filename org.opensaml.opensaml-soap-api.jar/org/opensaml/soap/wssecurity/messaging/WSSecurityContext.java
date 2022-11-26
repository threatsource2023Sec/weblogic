package org.opensaml.soap.wssecurity.messaging;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyList;
import org.joda.time.DateTime;
import org.opensaml.messaging.context.BaseContext;

public class WSSecurityContext extends BaseContext {
   private LazyList tokens = new LazyList();
   private DateTime timestampCreated;
   private DateTime timestampExpires;

   @Nonnull
   public List getTokens() {
      return this.tokens;
   }

   @Nullable
   public DateTime getTimestampCreated() {
      return this.timestampCreated;
   }

   public void setTimestampCreated(@Nullable DateTime value) {
      this.timestampCreated = value;
   }

   @Nullable
   public DateTime getTimestampExpires() {
      return this.timestampExpires;
   }

   public void setTimestampExpires(@Nullable DateTime value) {
      this.timestampExpires = value;
   }
}
