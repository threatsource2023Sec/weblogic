package weblogic.server.embed;

import weblogic.server.embed.internal.EmbeddedServerImpl;

public final class EmbeddedServerFactory {
   public static EmbeddedServer getEmbeddedServer() {
      return EmbeddedServerImpl.get();
   }
}
