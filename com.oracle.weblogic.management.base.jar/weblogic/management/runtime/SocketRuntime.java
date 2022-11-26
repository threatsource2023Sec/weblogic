package weblogic.management.runtime;

public interface SocketRuntime {
   int getFileDescriptor();

   String getLocalAddress();

   int getLocalPort();

   String getRemoteAddress();

   int getRemotePort();
}
