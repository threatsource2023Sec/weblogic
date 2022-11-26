package weblogic.servlet.spi;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.WritableByteChannel;
import javax.servlet.http.HttpServletResponse;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.protocol.ServerChannel;
import weblogic.servlet.FileSender;
import weblogic.servlet.internal.AbstractHttpConnectionHandler;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.WebAppServletContext;

public interface ContainerSupportProvider {
   boolean hasPermissionOnChannel(ServletRequestImpl var1, ServletResponseImpl var2) throws IOException;

   ObjectInputStream getObjectInputStream(InputStream var1) throws IOException;

   ObjectOutputStream getObjectOutputStream(OutputStream var1) throws IOException;

   void setServerChannelThreadLocal(ServerChannel var1);

   ServerChannel getServerChannelThreadLocal();

   SubjectHandle getDeploymentInitiator(WebAppServletContext var1);

   String[] getInternalWebAppListeners();

   RequestExecutor createRequestExecutor(AbstractHttpConnectionHandler var1);

   WritableByteChannel getWritableByteChannel(Socket var1);

   int sizeOf(Object var1) throws IOException;

   byte[] toByteArray(Object var1) throws IOException;

   Object toObject(byte[] var1) throws ClassNotFoundException, IOException;

   boolean isProductionMode();

   boolean isAdminChannel(ServerChannel var1);

   FileSender getZeroCopyFileSender(HttpServletResponse var1);

   FileSender getFileSender(HttpServletResponse var1);

   void sendFileNative(String var1, ServletRequestImpl var2, long var3) throws IOException;

   String getReleaseBuildVersion();

   String getWebServerReleaseString();

   String getXPoweredByHeaderValue(String var1);

   String getServerHashForExtendedSessionID(String var1);

   ComponentInvocationContextManager getComponentInvocationContextManager();
}
