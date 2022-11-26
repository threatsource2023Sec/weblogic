package weblogic.jaxrs.client.internal;

import java.net.HttpURLConnection;
import javax.annotation.Priority;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.ws.rs.client.Client;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.internal.HttpUrlConnector;
import org.glassfish.jersey.client.spi.Connector;
import org.glassfish.jersey.internal.util.collection.LazyValue;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.internal.util.collection.Values;
import weblogic.kernel.KernelStatus;
import weblogic.net.http.CompatibleSOAPHttpsURLConnection;

@Priority(5001)
public class WlsUrlConnectionProvider extends HttpUrlConnectorProvider {
   private LazyValue wlsSslSocketFactory;

   protected Connector createHttpUrlConnector(final Client client, HttpUrlConnectorProvider.ConnectionFactory connectionFactory, int chunkSize, boolean fixLengthStreaming, boolean setMethodWorkaround) {
      this.wlsSslSocketFactory = Values.lazy(new Value() {
         public SSLSocketFactory get() {
            return client.getSslContext().getSocketFactory();
         }
      });
      return new HttpUrlConnector(client, connectionFactory, chunkSize, fixLengthStreaming, setMethodWorkaround) {
         protected void secureConnection(JerseyClient client, HttpURLConnection uc) {
            final HostnameVerifier verifier;
            if (uc instanceof HttpsURLConnection) {
               HttpsURLConnection suc = (HttpsURLConnection)uc;
               verifier = client.getHostnameVerifier();
               if (verifier != null) {
                  suc.setHostnameVerifier(verifier);
               }

               suc.setSSLSocketFactory((SSLSocketFactory)WlsUrlConnectionProvider.this.wlsSslSocketFactory.get());
            } else {
               SSLSocketFactory socketFactory;
               if (uc instanceof weblogic.net.http.HttpsURLConnection) {
                  weblogic.net.http.HttpsURLConnection wlsHttpsUrlConnection = (weblogic.net.http.HttpsURLConnection)uc;
                  verifier = client.getHostnameVerifier();
                  if (verifier != null) {
                     wlsHttpsUrlConnection.setHostnameVerifier(new weblogic.security.SSL.HostnameVerifier() {
                        public boolean verify(String hostname, SSLSession session) {
                           return verifier.verify(hostname, session);
                        }
                     });
                  }

                  if (!client.isDefaultSslContext()) {
                     socketFactory = (SSLSocketFactory)WlsUrlConnectionProvider.this.wlsSslSocketFactory.get();
                     wlsHttpsUrlConnection.setSSLSocketFactory(new weblogic.security.SSL.SSLSocketFactory(socketFactory) {
                     });
                  }
               } else if (KernelStatus.isServer() && uc instanceof CompatibleSOAPHttpsURLConnection) {
                  CompatibleSOAPHttpsURLConnection wlsHttpsUrlConnectionx = (CompatibleSOAPHttpsURLConnection)uc;
                  verifier = client.getHostnameVerifier();
                  if (verifier != null) {
                     wlsHttpsUrlConnectionx.setHostnameVerifier(new weblogic.security.SSL.HostnameVerifier() {
                        public boolean verify(String hostname, SSLSession session) {
                           return verifier.verify(hostname, session);
                        }
                     });
                  }

                  if (!client.isDefaultSslContext()) {
                     socketFactory = (SSLSocketFactory)WlsUrlConnectionProvider.this.wlsSslSocketFactory.get();
                     wlsHttpsUrlConnectionx.setSSLSocketFactory(socketFactory);
                  }
               }
            }

         }
      };
   }
}
