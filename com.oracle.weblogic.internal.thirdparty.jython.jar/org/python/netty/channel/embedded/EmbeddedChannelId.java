package org.python.netty.channel.embedded;

import org.python.netty.channel.ChannelId;

final class EmbeddedChannelId implements ChannelId {
   private static final long serialVersionUID = -251711922203466130L;
   static final ChannelId INSTANCE = new EmbeddedChannelId();

   private EmbeddedChannelId() {
   }

   public String asShortText() {
      return this.toString();
   }

   public String asLongText() {
      return this.toString();
   }

   public int compareTo(ChannelId o) {
      return o instanceof EmbeddedChannelId ? 0 : this.asLongText().compareTo(o.asLongText());
   }

   public int hashCode() {
      return 0;
   }

   public boolean equals(Object obj) {
      return obj instanceof EmbeddedChannelId;
   }

   public String toString() {
      return "embedded";
   }
}
