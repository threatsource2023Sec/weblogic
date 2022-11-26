package weblogic.server.channels;

public interface ServerChannelConstants {
   byte HAS_PUBLIC_ADDRESS = 1;
   byte HAS_LISTEN_PORT = 2;
   byte HAS_PUBLIC_PORT = 4;
   byte HAS_LISTEN_ADDRESS = 8;
   byte SUPPORTS_TLS = 32;
   byte SUPPORTS_HTTP = 16;
   byte SUPPORTS_SDP = 64;
}
