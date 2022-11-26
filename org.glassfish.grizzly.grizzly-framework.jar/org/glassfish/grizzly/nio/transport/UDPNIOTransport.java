package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.GracefulShutdownListener;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.ProcessorSelector;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.Reader;
import org.glassfish.grizzly.StandaloneProcessor;
import org.glassfish.grizzly.StandaloneProcessorSelector;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.asyncqueue.AsyncQueueIO;
import org.glassfish.grizzly.asyncqueue.AsyncQueueReader;
import org.glassfish.grizzly.asyncqueue.AsyncQueueWriter;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChainEnabledTransport;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.memory.ByteBufferArray;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.nio.ChannelConfigurator;
import org.glassfish.grizzly.nio.DirectByteBufferRecord;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.nio.SelectorRunner;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO;
import org.glassfish.grizzly.utils.Futures;

public final class UDPNIOTransport extends NIOTransport implements FilterChainEnabledTransport {
   public static final ChannelConfigurator DEFAULT_CHANNEL_CONFIGURATOR = new DefaultChannelConfigurator();
   static final Logger LOGGER = Grizzly.logger(UDPNIOTransport.class);
   private static final String DEFAULT_TRANSPORT_NAME = "UDPNIOTransport";
   protected final Collection serverConnections;
   protected final AsyncQueueIO asyncQueueIO;
   private final Filter transportFilter;
   protected final RegisterChannelCompletionHandler registerChannelCompletionHandler;
   private final UDPNIOConnectorHandler connectorHandler;
   private final UDPNIOBindingHandler bindingHandler;

   public UDPNIOTransport() {
      this("UDPNIOTransport");
   }

   public UDPNIOTransport(String name) {
      super(name != null ? name : "UDPNIOTransport");
      this.connectorHandler = new TransportConnectorHandler();
      this.bindingHandler = new UDPNIOBindingHandler(this);
      this.readBufferSize = -1;
      this.writeBufferSize = -1;
      this.registerChannelCompletionHandler = new RegisterChannelCompletionHandler();
      this.asyncQueueIO = AsyncQueueIO.Factory.createImmutable(new UDPNIOAsyncQueueReader(this), new UDPNIOAsyncQueueWriter(this));
      this.transportFilter = new UDPNIOTransportFilter(this);
      this.serverConnections = new ConcurrentLinkedQueue();
   }

   protected TemporarySelectorIO createTemporarySelectorIO() {
      return new TemporarySelectorIO(new UDPNIOTemporarySelectorReader(this), new UDPNIOTemporarySelectorWriter(this));
   }

   protected void listen() {
      Iterator var1 = this.serverConnections.iterator();

      while(var1.hasNext()) {
         UDPNIOServerConnection serverConnection = (UDPNIOServerConnection)var1.next();

         try {
            serverConnection.register();
         } catch (Exception var4) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_START_SERVER_CONNECTION_EXCEPTION(serverConnection), var4);
         }
      }

   }

   public synchronized boolean addShutdownListener(GracefulShutdownListener shutdownListener) {
      Transport.State state = (Transport.State)this.getState().getState();
      if (state == Transport.State.STOPPING && state == Transport.State.STOPPED) {
         return false;
      } else {
         if (this.shutdownListeners == null) {
            this.shutdownListeners = new HashSet();
         }

         return this.shutdownListeners.add(shutdownListener);
      }
   }

   public UDPNIOServerConnection bind(int port) throws IOException {
      return this.bind(new InetSocketAddress(port));
   }

   public UDPNIOServerConnection bind(String host, int port) throws IOException {
      return this.bind(host, port, 50);
   }

   public UDPNIOServerConnection bind(String host, int port, int backlog) throws IOException {
      return this.bind((SocketAddress)(new InetSocketAddress(host, port)), backlog);
   }

   public UDPNIOServerConnection bind(SocketAddress socketAddress) throws IOException {
      return this.bind((SocketAddress)socketAddress, 4096);
   }

   public UDPNIOServerConnection bind(SocketAddress socketAddress, int backlog) throws IOException {
      return this.bindingHandler.bind(socketAddress, backlog);
   }

   public Connection bindToInherited() throws IOException {
      return this.bindingHandler.bindToInherited();
   }

   public UDPNIOServerConnection bind(String host, PortRange portRange, int backlog) throws IOException {
      return (UDPNIOServerConnection)this.bindingHandler.bind(host, portRange, backlog);
   }

   public void unbind(Connection connection) {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      try {
         if (connection != null && this.serverConnections.remove(connection)) {
            FutureImpl future = Futures.createSafeFuture();
            ((UDPNIOServerConnection)connection).unbind(Futures.toCompletionHandler(future));

            try {
               future.get(1000L, TimeUnit.MILLISECONDS);
               future.recycle(false);
            } catch (Exception var8) {
               LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_UNBINDING_CONNECTION_EXCEPTION(connection), var8);
            }
         }
      } finally {
         lock.unlock();
      }

   }

   public void unbindAll() {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      try {
         Iterator var2 = this.serverConnections.iterator();

         while(var2.hasNext()) {
            Connection serverConnection = (Connection)var2.next();

            try {
               this.unbind(serverConnection);
            } catch (Exception var8) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "Exception occurred when closing server connection: " + serverConnection, var8);
               }
            }
         }

         this.serverConnections.clear();
      } finally {
         lock.unlock();
      }
   }

   public GrizzlyFuture connect() throws IOException {
      return this.connectorHandler.connect();
   }

   public GrizzlyFuture connect(String host, int port) throws IOException {
      return this.connectorHandler.connect(host, port);
   }

   public GrizzlyFuture connect(SocketAddress remoteAddress) {
      return this.connectorHandler.connect(remoteAddress);
   }

   public void connect(SocketAddress remoteAddress, CompletionHandler completionHandler) {
      this.connectorHandler.connect(remoteAddress, completionHandler);
   }

   public GrizzlyFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
      return this.connectorHandler.connect(remoteAddress, localAddress);
   }

   public void connect(SocketAddress remoteAddress, SocketAddress localAddress, CompletionHandler completionHandler) {
      this.connectorHandler.connect(remoteAddress, localAddress, completionHandler);
   }

   protected void closeConnection(Connection connection) throws IOException {
      SelectableChannel nioChannel = ((NIOConnection)connection).getChannel();
      if (nioChannel != null) {
         try {
            nioChannel.close();
         } catch (IOException var5) {
            LOGGER.log(Level.FINE, "UDPNIOTransport.closeChannel exception", var5);
         }
      }

      if (this.asyncQueueIO != null) {
         AsyncQueueReader reader = this.asyncQueueIO.getReader();
         if (reader != null) {
            reader.onClose(connection);
         }

         AsyncQueueWriter writer = this.asyncQueueIO.getWriter();
         if (writer != null) {
            writer.onClose(connection);
         }
      }

   }

   public synchronized void configureStandalone(boolean isStandalone) {
      if (this.isStandalone != isStandalone) {
         this.isStandalone = isStandalone;
         if (isStandalone) {
            this.processor = StandaloneProcessor.INSTANCE;
            this.processorSelector = StandaloneProcessorSelector.INSTANCE;
         } else {
            this.processor = null;
            this.processorSelector = null;
         }
      }

   }

   public Filter getTransportFilter() {
      return this.transportFilter;
   }

   public AsyncQueueIO getAsyncQueueIO() {
      return this.asyncQueueIO;
   }

   public TemporarySelectorIO getTemporarySelectorIO() {
      return this.temporarySelectorIO;
   }

   public void fireIOEvent(IOEvent ioEvent, Connection connection, IOEventLifeCycleListener listener) {
      Processor conProcessor = connection.obtainProcessor(ioEvent);
      ProcessorExecutor.execute(Context.create(connection, conProcessor, ioEvent, listener));
   }

   public Reader getReader(Connection connection) {
      return this.getReader(connection.isBlocking());
   }

   public Reader getReader(boolean isBlocking) {
      return (Reader)(isBlocking ? this.getTemporarySelectorIO().getReader() : this.getAsyncQueueIO().getReader());
   }

   public Writer getWriter(Connection connection) {
      return this.getWriter(connection.isBlocking());
   }

   public Writer getWriter(boolean isBlocking) {
      return (Writer)(isBlocking ? this.getTemporarySelectorIO().getWriter() : this.getAsyncQueueIO().getWriter());
   }

   private int readConnected(UDPNIOConnection connection, Buffer buffer, ReadResult currentResult) throws IOException {
      int oldPos = buffer.position();
      int read;
      if (buffer.isComposite()) {
         ByteBufferArray array = buffer.toByteBufferArray();
         ByteBuffer[] byteBuffers = (ByteBuffer[])array.getArray();
         int size = array.size();
         read = (int)((DatagramChannel)connection.getChannel()).read(byteBuffers, 0, size);
         array.restore();
         array.recycle();
      } else {
         read = ((DatagramChannel)connection.getChannel()).read(buffer.toByteBuffer());
      }

      boolean hasRead = read > 0;
      if (hasRead) {
         buffer.position(oldPos + read);
      }

      if (hasRead && currentResult != null) {
         currentResult.setMessage(buffer);
         currentResult.setReadSize(currentResult.getReadSize() + read);
         currentResult.setSrcAddressHolder(connection.peerSocketAddressHolder);
      }

      return read;
   }

   private int readNonConnected(UDPNIOConnection connection, Buffer buffer, ReadResult currentResult) throws IOException {
      DirectByteBufferRecord ioRecord = DirectByteBufferRecord.get();

      SocketAddress peerAddress;
      int read;
      try {
         ByteBuffer directByteBuffer = ioRecord.allocate(buffer.limit());
         int initialBufferPos = directByteBuffer.position();
         peerAddress = ((DatagramChannel)connection.getChannel()).receive(directByteBuffer);
         read = directByteBuffer.position() - initialBufferPos;
         if (read > 0) {
            directByteBuffer.flip();
            buffer.put(directByteBuffer);
         }
      } finally {
         ioRecord.release();
      }

      boolean hasRead = read > 0;
      if (hasRead && currentResult != null) {
         currentResult.setMessage(buffer);
         currentResult.setReadSize(currentResult.getReadSize() + read);
         currentResult.setSrcAddress(peerAddress);
      }

      return read;
   }

   public int read(UDPNIOConnection connection, Buffer buffer) throws IOException {
      return this.read(connection, buffer, (ReadResult)null);
   }

   public int read(UDPNIOConnection connection, Buffer buffer, ReadResult currentResult) throws IOException {
      int read = 0;
      boolean isAllocate = buffer == null && currentResult != null;
      if (isAllocate) {
         buffer = this.memoryManager.allocateAtLeast(connection.getReadBufferSize());
      }

      try {
         read = connection.isConnected() ? this.readConnected(connection, buffer, currentResult) : this.readNonConnected(connection, buffer, currentResult);
         connection.onRead(buffer, read);
      } catch (Exception var10) {
         read = -1;
      } finally {
         if (isAllocate) {
            if (read <= 0) {
               buffer.dispose();
            } else {
               buffer.allowBufferDispose(true);
            }
         }

      }

      return read;
   }

   public long write(UDPNIOConnection connection, SocketAddress dstAddress, WritableMessage message) throws IOException {
      return this.write(connection, dstAddress, message, (WriteResult)null);
   }

   public long write(UDPNIOConnection connection, SocketAddress dstAddress, WritableMessage message, WriteResult currentResult) throws IOException {
      long written;
      if (message instanceof Buffer) {
         Buffer buffer = (Buffer)message;
         int oldPos = buffer.position();
         if (dstAddress != null) {
            written = (long)((DatagramChannel)connection.getChannel()).send(buffer.toByteBuffer(), dstAddress);
         } else if (buffer.isComposite()) {
            ByteBufferArray array = buffer.toByteBufferArray();
            ByteBuffer[] byteBuffers = (ByteBuffer[])array.getArray();
            int size = array.size();
            written = ((DatagramChannel)connection.getChannel()).write(byteBuffers, 0, size);
            array.restore();
            array.recycle();
         } else {
            written = (long)((DatagramChannel)connection.getChannel()).write(buffer.toByteBuffer());
         }

         if (written > 0L) {
            buffer.position(oldPos + (int)written);
         }

         connection.onWrite(buffer, (int)written);
      } else {
         if (!(message instanceof FileTransfer)) {
            throw new IllegalStateException("Unhandled message type");
         }

         written = ((FileTransfer)message).writeTo((DatagramChannel)connection.getChannel());
      }

      if (currentResult != null) {
         currentResult.setMessage(message);
         currentResult.setWrittenSize(currentResult.getWrittenSize() + written);
         currentResult.setDstAddressHolder(connection.peerSocketAddressHolder);
      }

      return written;
   }

   public ChannelConfigurator getChannelConfigurator() {
      ChannelConfigurator cc = this.channelConfigurator;
      return cc != null ? cc : DEFAULT_CHANNEL_CONFIGURATOR;
   }

   UDPNIOConnection obtainNIOConnection(DatagramChannel channel) {
      UDPNIOConnection connection = new UDPNIOConnection(this, channel);
      this.configureNIOConnection(connection);
      return connection;
   }

   UDPNIOServerConnection obtainServerNIOConnection(DatagramChannel channel) {
      UDPNIOServerConnection connection = new UDPNIOServerConnection(this, channel);
      this.configureNIOConnection(connection);
      return connection;
   }

   protected Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.nio.transport.jmx.UDPNIOTransport", this, UDPNIOTransport.class);
   }

   private static class DefaultChannelConfigurator implements ChannelConfigurator {
      private DefaultChannelConfigurator() {
      }

      public void preConfigure(NIOTransport transport, SelectableChannel channel) throws IOException {
         UDPNIOTransport udpNioTransport = (UDPNIOTransport)transport;
         DatagramChannel datagramChannel = (DatagramChannel)channel;
         DatagramSocket datagramSocket = datagramChannel.socket();
         datagramChannel.configureBlocking(false);

         try {
            datagramSocket.setReuseAddress(udpNioTransport.isReuseAddress());
         } catch (IOException var7) {
            UDPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_REUSEADDRESS_EXCEPTION(udpNioTransport.isReuseAddress()), var7);
         }

      }

      public void postConfigure(NIOTransport transport, SelectableChannel channel) throws IOException {
         UDPNIOTransport udpNioTransport = (UDPNIOTransport)transport;
         DatagramChannel datagramChannel = (DatagramChannel)channel;
         DatagramSocket datagramSocket = datagramChannel.socket();
         boolean isConnected = datagramChannel.isConnected();
         int soTimeout = isConnected ? udpNioTransport.getClientSocketSoTimeout() : udpNioTransport.getServerSocketSoTimeout();

         try {
            datagramSocket.setSoTimeout(soTimeout);
         } catch (IOException var9) {
            UDPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_TIMEOUT_EXCEPTION(soTimeout), var9);
         }

      }

      // $FF: synthetic method
      DefaultChannelConfigurator(Object x0) {
         this();
      }
   }

   protected class TransportConnectorHandler extends UDPNIOConnectorHandler {
      public TransportConnectorHandler() {
         super(UDPNIOTransport.this);
      }

      public Processor getProcessor() {
         return UDPNIOTransport.this.getProcessor();
      }

      public ProcessorSelector getProcessorSelector() {
         return UDPNIOTransport.this.getProcessorSelector();
      }
   }

   protected class RegisterChannelCompletionHandler extends EmptyCompletionHandler {
      public void completed(RegisterChannelResult result) {
         SelectionKey selectionKey = result.getSelectionKey();
         UDPNIOConnection connection = (UDPNIOConnection)UDPNIOTransport.this.getSelectionKeyHandler().getConnectionForKey(selectionKey);
         if (connection != null) {
            SelectorRunner selectorRunner = result.getSelectorRunner();
            connection.setSelectionKey(selectionKey);
            connection.setSelectorRunner(selectorRunner);
         }

      }
   }
}
