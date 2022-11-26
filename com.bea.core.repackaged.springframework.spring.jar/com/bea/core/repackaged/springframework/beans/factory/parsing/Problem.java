package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class Problem {
   private final String message;
   private final Location location;
   @Nullable
   private final ParseState parseState;
   @Nullable
   private final Throwable rootCause;

   public Problem(String message, Location location) {
      this(message, location, (ParseState)null, (Throwable)null);
   }

   public Problem(String message, Location location, ParseState parseState) {
      this(message, location, parseState, (Throwable)null);
   }

   public Problem(String message, Location location, @Nullable ParseState parseState, @Nullable Throwable rootCause) {
      Assert.notNull(message, (String)"Message must not be null");
      Assert.notNull(location, (String)"Location must not be null");
      this.message = message;
      this.location = location;
      this.parseState = parseState;
      this.rootCause = rootCause;
   }

   public String getMessage() {
      return this.message;
   }

   public Location getLocation() {
      return this.location;
   }

   public String getResourceDescription() {
      return this.getLocation().getResource().getDescription();
   }

   @Nullable
   public ParseState getParseState() {
      return this.parseState;
   }

   @Nullable
   public Throwable getRootCause() {
      return this.rootCause;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("Configuration problem: ");
      sb.append(this.getMessage());
      sb.append("\nOffending resource: ").append(this.getResourceDescription());
      if (this.getParseState() != null) {
         sb.append('\n').append(this.getParseState());
      }

      return sb.toString();
   }
}
