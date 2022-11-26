package javax.faces.event;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import javax.inject.Qualifier;
import javax.websocket.CloseReason;

public final class WebsocketEvent implements Serializable {
   private static final long serialVersionUID = 1L;
   private final String channel;
   private final Serializable user;
   private final CloseReason.CloseCode code;

   public WebsocketEvent(String channel, Serializable user, CloseReason.CloseCode code) {
      this.channel = channel;
      this.user = user;
      this.code = code;
   }

   public String getChannel() {
      return this.channel;
   }

   public Serializable getUser() {
      return this.user;
   }

   public CloseReason.CloseCode getCloseCode() {
      return this.code;
   }

   public int hashCode() {
      return super.hashCode() + Objects.hash(new Object[]{this.channel, this.user, this.code});
   }

   public boolean equals(Object other) {
      return other != null && this.getClass() == other.getClass() && Objects.equals(this.channel, ((WebsocketEvent)other).channel) && Objects.equals(this.user, ((WebsocketEvent)other).user) && Objects.equals(this.code, ((WebsocketEvent)other).code);
   }

   public String toString() {
      return String.format("WebsocketEvent[channel=%s, user=%s, closeCode=%s]", this.channel, this.user, this.code);
   }

   @Qualifier
   @Target({ElementType.PARAMETER})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface Closed {
   }

   @Qualifier
   @Target({ElementType.PARAMETER})
   @Retention(RetentionPolicy.RUNTIME)
   @Documented
   public @interface Opened {
   }
}
