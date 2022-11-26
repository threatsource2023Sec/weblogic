package org.glassfish.grizzly;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.monitoring.MonitoringAware;
import org.glassfish.grizzly.monitoring.MonitoringConfig;
import org.glassfish.grizzly.utils.NullaryFunction;

public interface Connection extends Readable, Writeable, Closeable, AttributeStorage, MonitoringAware {
   Transport getTransport();

   boolean isOpen();

   void assertOpen() throws IOException;

   CloseReason getCloseReason();

   void configureBlocking(boolean var1);

   boolean isBlocking();

   /** @deprecated */
   @Deprecated
   void configureStandalone(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isStandalone();

   Processor obtainProcessor(IOEvent var1);

   Processor getProcessor();

   void setProcessor(Processor var1);

   ProcessorSelector getProcessorSelector();

   void setProcessorSelector(ProcessorSelector var1);

   Object obtainProcessorState(Processor var1, NullaryFunction var2);

   void executeInEventThread(IOEvent var1, Runnable var2);

   MemoryManager getMemoryManager();

   Object getPeerAddress();

   Object getLocalAddress();

   int getReadBufferSize();

   void setReadBufferSize(int var1);

   int getWriteBufferSize();

   void setWriteBufferSize(int var1);

   int getMaxAsyncWriteQueueSize();

   void setMaxAsyncWriteQueueSize(int var1);

   long getReadTimeout(TimeUnit var1);

   void setReadTimeout(long var1, TimeUnit var3);

   long getWriteTimeout(TimeUnit var1);

   void setWriteTimeout(long var1, TimeUnit var3);

   void simulateIOEvent(IOEvent var1) throws IOException;

   void enableIOEvent(IOEvent var1) throws IOException;

   void disableIOEvent(IOEvent var1) throws IOException;

   MonitoringConfig getMonitoringConfig();

   void terminateSilently();

   GrizzlyFuture terminate();

   void terminateWithReason(IOException var1);

   GrizzlyFuture close();

   /** @deprecated */
   void close(CompletionHandler var1);

   void closeSilently();

   void closeWithReason(IOException var1);

   void addCloseListener(org.glassfish.grizzly.CloseListener var1);

   boolean removeCloseListener(org.glassfish.grizzly.CloseListener var1);

   /** @deprecated */
   @Deprecated
   void addCloseListener(CloseListener var1);

   /** @deprecated */
   @Deprecated
   boolean removeCloseListener(CloseListener var1);

   void notifyConnectionError(Throwable var1);

   /** @deprecated */
   @Deprecated
   public static enum CloseType implements ICloseType {
      LOCALLY,
      REMOTELY;
   }

   /** @deprecated */
   @Deprecated
   public interface CloseListener extends org.glassfish.grizzly.CloseListener {
      void onClosed(Connection var1, CloseType var2) throws IOException;
   }
}
