package weblogic.server.embed;

public abstract class EmbeddedServerInternal implements EmbeddedServer {
   public abstract void suspend() throws EmbeddedServerException;

   public abstract void shutdown() throws EmbeddedServerException;
}
