package weblogic.websocket.tyrus;

public class ClientConfigConstants {
   public static enum Option {
      PROXY_HOST("weblogic.websocket.client.PROXY_HOST"),
      PROXY_PORT("weblogic.websocket.client.PROXY_PORT"),
      PROXY_USERNAME("weblogic.websocket.client.PROXY_USERNAME"),
      PROXY_PASSWORD("weblogic.websocket.client.PROXY_PASSWORD"),
      SSL_PROTOCOLS_PROPERTY("weblogic.websocket.client.SSL_PROTOCOLS"),
      SSL_TRUSTSTORE_PROPERTY("weblogic.websocket.client.SSL_TRUSTSTORE"),
      SSL_TRUSTSTORE_PWD_PROPERTY("weblogic.websocket.client.SSL_TRUSTSTORE_PWD"),
      INCOMING_BUFFER_SIZE("weblogic.websocket.tyrus.incoming-buffer-size");

      private String optionName;

      private Option(String optionName) {
         this.optionName = optionName;
      }

      public String getOptionName() {
         return this.optionName;
      }
   }
}
