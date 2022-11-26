package weblogic.server.embed;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public interface EmbeddedServer {
   State getState();

   void start() throws EmbeddedServerException;

   void cleanupOnExit();

   void suspend() throws EmbeddedServerException;

   void resume() throws EmbeddedServerException;

   void shutdown() throws EmbeddedServerException;

   Logger getLogger();

   Config getConfig();

   Deployer getDeployer() throws EmbeddedServerException;

   HttpURLConnection getHttpURLConnection(URL var1) throws IOException, EmbeddedServerException;

   public static enum State {
      NEW,
      CONFIGURED,
      STARTED,
      FAILED,
      SUSPENDED;
   }
}
