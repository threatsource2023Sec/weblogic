package org.glassfish.grizzly.nio.transport;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CloseReason;
import org.glassfish.grizzly.CloseType;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Context;
import org.glassfish.grizzly.EmptyCompletionHandler;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.IOEventLifeCycleListener;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.Processor;
import org.glassfish.grizzly.ProcessorExecutor;
import org.glassfish.grizzly.ProcessorSelector;
import org.glassfish.grizzly.Reader;
import org.glassfish.grizzly.StandaloneProcessor;
import org.glassfish.grizzly.StandaloneProcessorSelector;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.asyncqueue.AsyncQueueEnabledTransport;
import org.glassfish.grizzly.asyncqueue.AsyncQueueIO;
import org.glassfish.grizzly.asyncqueue.AsyncQueueReader;
import org.glassfish.grizzly.asyncqueue.AsyncQueueWriter;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.filterchain.Filter;
import org.glassfish.grizzly.filterchain.FilterChainEnabledTransport;
import org.glassfish.grizzly.localization.LogMessages;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.monitoring.MonitoringUtils;
import org.glassfish.grizzly.nio.ChannelConfigurator;
import org.glassfish.grizzly.nio.NIOConnection;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.RegisterChannelResult;
import org.glassfish.grizzly.nio.SelectorRunner;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorIO;
import org.glassfish.grizzly.nio.tmpselectors.TemporarySelectorsEnabledTransport;

public final class TCPNIOTransport extends NIOTransport implements AsyncQueueEnabledTransport, FilterChainEnabledTransport, TemporarySelectorsEnabledTransport {
   static final Logger LOGGER = Grizzly.logger(TCPNIOTransport.class);
   public static final ChannelConfigurator DEFAULT_CHANNEL_CONFIGURATOR = new DefaultChannelConfigurator();
   public static final int MAX_RECEIVE_BUFFER_SIZE = Integer.getInteger(TCPNIOTransport.class.getName() + ".max-receive-buffer-size", Integer.MAX_VALUE);
   public static final int MAX_SEND_BUFFER_SIZE = Integer.getInteger(TCPNIOTransport.class.getName() + ".max-send-buffer-size", Integer.MAX_VALUE);
   public static final boolean DEFAULT_TCP_NO_DELAY = true;
   public static final boolean DEFAULT_KEEP_ALIVE = true;
   public static final int DEFAULT_LINGER = -1;
   public static final int DEFAULT_SERVER_CONNECTION_BACKLOG = 4096;
   private static final String DEFAULT_TRANSPORT_NAME = "TCPNIOTransport";
   final Collection serverConnections;
   final AsyncQueueIO asyncQueueIO;
   int linger;
   int serverConnectionBackLog;
   boolean tcpNoDelay;
   boolean isKeepAlive;
   private final Filter defaultTransportFilter;
   final RegisterChannelCompletionHandler selectorRegistrationHandler;
   private final TCPNIOConnectorHandler connectorHandler;
   private final TCPNIOBindingHandler bindingHandler;

   public TCPNIOTransport() {
      this("TCPNIOTransport");
   }

   TCPNIOTransport(String name) {
      super(name != null ? name : "TCPNIOTransport");
      this.linger = -1;
      this.serverConnectionBackLog = 4096;
      this.tcpNoDelay = true;
      this.isKeepAlive = true;
      this.connectorHandler = new TransportConnectorHandler();
      this.bindingHandler = new TCPNIOBindingHandler(this);
      this.readBufferSize = -1;
      this.writeBufferSize = -1;
      this.selectorRegistrationHandler = new RegisterChannelCompletionHandler();
      this.asyncQueueIO = AsyncQueueIO.Factory.createImmutable(new TCPNIOAsyncQueueReader(this), new TCPNIOAsyncQueueWriter(this));
      this.attributeBuilder = Grizzly.DEFAULT_ATTRIBUTE_BUILDER;
      this.defaultTransportFilter = new TCPNIOTransportFilter(this);
      this.serverConnections = new ConcurrentLinkedQueue();
   }

   protected TemporarySelectorIO createTemporarySelectorIO() {
      return new TemporarySelectorIO(new TCPNIOTemporarySelectorReader(this), new TCPNIOTemporarySelectorWriter(this));
   }

   protected void listen() {
      Iterator var1 = this.serverConnections.iterator();

      while(var1.hasNext()) {
         TCPNIOServerConnection serverConnection = (TCPNIOServerConnection)var1.next();

         try {
            this.listenServerConnection(serverConnection);
         } catch (Exception var4) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TRANSPORT_START_SERVER_CONNECTION_EXCEPTION(serverConnection), var4);
         }
      }

   }

   protected int getDefaultSelectorRunnersCount() {
      return Runtime.getRuntime().availableProcessors() + 1;
   }

   void listenServerConnection(TCPNIOServerConnection serverConnection) throws IOException {
      serverConnection.listen();
   }

   public TCPNIOServerConnection bind(int port) throws IOException {
      return this.bind(new InetSocketAddress(port));
   }

   public TCPNIOServerConnection bind(String host, int port) throws IOException {
      return this.bind(host, port, this.serverConnectionBackLog);
   }

   public TCPNIOServerConnection bind(String host, int port, int backlog) throws IOException {
      return this.bind((SocketAddress)(new InetSocketAddress(host, port)), backlog);
   }

   public TCPNIOServerConnection bind(SocketAddress socketAddress) throws IOException {
      return this.bind(socketAddress, this.serverConnectionBackLog);
   }

   public TCPNIOServerConnection bind(SocketAddress socketAddress, int backlog) throws IOException {
      return this.bindingHandler.bind(socketAddress, backlog);
   }

   public TCPNIOServerConnection bindToInherited() throws IOException {
      return this.bindingHandler.bindToInherited();
   }

   public TCPNIOServerConnection bind(String host, PortRange portRange, int backlog) throws IOException {
      return (TCPNIOServerConnection)this.bindingHandler.bind(host, portRange, backlog);
   }

   public void unbind(Connection connection) {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      try {
         if (connection != null && this.serverConnections.remove(connection)) {
            GrizzlyFuture future = connection.close();

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
               LOGGER.log(Level.FINE, "Exception occurred when closing server connection: " + serverConnection, var8);
            }
         }

         this.serverConnections.clear();
      } finally {
         lock.unlock();
      }

   }

   public GrizzlyFuture connect(String host, int port) {
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
            LOGGER.log(Level.FINE, "TCPNIOTransport.closeChannel exception", var5);
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

   TCPNIOConnection obtainNIOConnection(SocketChannel channel) {
      TCPNIOConnection connection = new TCPNIOConnection(this, channel);
      this.configureNIOConnection(connection);
      return connection;
   }

   TCPNIOServerConnection obtainServerNIOConnection(ServerSocketChannel channel) {
      TCPNIOServerConnection connection = new TCPNIOServerConnection(this, channel);
      this.configureNIOConnection(connection);
      return connection;
   }

   public ChannelConfigurator getChannelConfigurator() {
      ChannelConfigurator cc = this.channelConfigurator;
      return cc != null ? cc : DEFAULT_CHANNEL_CONFIGURATOR;
   }

   public AsyncQueueIO getAsyncQueueIO() {
      return this.asyncQueueIO;
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

   public int getLinger() {
      return this.linger;
   }

   public void setLinger(int linger) {
      this.linger = linger;
      notifyProbesConfigChanged(this);
   }

   public boolean isKeepAlive() {
      return this.isKeepAlive;
   }

   public void setKeepAlive(boolean isKeepAlive) {
      this.isKeepAlive = isKeepAlive;
      notifyProbesConfigChanged(this);
   }

   public boolean isTcpNoDelay() {
      return this.tcpNoDelay;
   }

   public void setTcpNoDelay(boolean tcpNoDelay) {
      this.tcpNoDelay = tcpNoDelay;
      notifyProbesConfigChanged(this);
   }

   public int getServerConnectionBackLog() {
      return this.serverConnectionBackLog;
   }

   public void setServerConnectionBackLog(int serverConnectionBackLog) {
      this.serverConnectionBackLog = serverConnectionBackLog;
   }

   public Filter getTransportFilter() {
      return this.defaultTransportFilter;
   }

   public TemporarySelectorIO getTemporarySelectorIO() {
      return this.temporarySelectorIO;
   }

   public void fireIOEvent(IOEvent ioEvent, Connection connection, IOEventLifeCycleListener listener) {
      if (ioEvent == IOEvent.SERVER_ACCEPT) {
         try {
            ((TCPNIOServerConnection)connection).onAccept();
         } catch (ClosedByInterruptException var8) {
            failProcessingHandler(ioEvent, connection, listener, var8);

            try {
               this.rebindAddress(connection);
            } catch (IOException var7) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, LogMessages.SEVERE_GRIZZLY_TRANSPORT_LISTEN_INTERRUPTED_REBIND_EXCEPTION(connection.getLocalAddress()), var7);
               }
            }
         } catch (IOException var9) {
            failProcessingHandler(ioEvent, connection, listener, var9);
         }

      } else if (ioEvent == IOEvent.CLIENT_CONNECTED) {
         try {
            ((TCPNIOConnection)connection).onConnect();
         } catch (IOException var6) {
            failProcessingHandler(ioEvent, connection, listener, var6);
         }

      } else {
         ProcessorExecutor.execute(Context.create(connection, connection.obtainProcessor(ioEvent), ioEvent, listener));
      }
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

   public Buffer read(Connection connection, Buffer buffer) throws IOException {
      TCPNIOConnection tcpConnection = (TCPNIOConnection)connection;
      boolean isAllocate = buffer == null;
      int read;
      EOFException e;
      if (isAllocate) {
         try {
            buffer = TCPNIOUtils.allocateAndReadBuffer(tcpConnection);
            read = buffer.position();
            tcpConnection.onRead(buffer, read);
         } catch (Exception var7) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "TCPNIOConnection (" + connection + ") (allocated) read exception", var7);
            }

            read = -1;
         }

         if (read == 0) {
            buffer = null;
         } else if (read < 0) {
            e = new EOFException();
            tcpConnection.terminate0((CompletionHandler)null, new CloseReason(CloseType.REMOTELY, e));
            throw e;
         }
      } else if (buffer.hasRemaining()) {
         try {
            read = TCPNIOUtils.readBuffer(tcpConnection, buffer);
         } catch (Exception var8) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "TCPNIOConnection (" + connection + ") (existing) read exception", var8);
            }

            read = -1;
         }

         tcpConnection.onRead(buffer, read);
         if (read < 0) {
            e = new EOFException();
            tcpConnection.terminate0((CompletionHandler)null, new CloseReason(CloseType.REMOTELY, e));
            throw e;
         }
      }

      return buffer;
   }

   public int write(TCPNIOConnection connection, WritableMessage message) throws IOException {
      return this.write(connection, message, (WriteResult)null);
   }

   public int write(TCPNIOConnection connection, WritableMessage message, WriteResult currentResult) throws IOException {
      int written;
      if (message.remaining() == 0) {
         written = 0;
      } else if (message instanceof Buffer) {
         Buffer buffer = (Buffer)message;

         try {
            written = buffer.isComposite() ? TCPNIOUtils.writeCompositeBuffer(connection, (CompositeBuffer)buffer) : TCPNIOUtils.writeSimpleBuffer(connection, buffer);
            boolean hasWritten = written >= 0;
            connection.onWrite(buffer, (long)written);
            if (hasWritten && currentResult != null) {
               currentResult.setMessage(message);
               currentResult.setWrittenSize(currentResult.getWrittenSize() + (long)written);
               currentResult.setDstAddressHolder(connection.peerSocketAddressHolder);
            }
         } catch (IOException var7) {
            connection.terminate0((CompletionHandler)null, new CloseReason(CloseType.REMOTELY, var7));
            throw var7;
         }
      } else {
         if (!(message instanceof FileTransfer)) {
            throw new IllegalStateException("Unhandled message type");
         }

         written = (int)((FileTransfer)message).writeTo((SocketChannel)connection.getChannel());
      }

      return written;
   }

   private static void failProcessingHandler(IOEvent ioEvent, Connection connection, IOEventLifeCycleListener processingHandler, IOException e) {
      if (processingHandler != null) {
         try {
            processingHandler.onError(Context.create(connection, (Processor)null, ioEvent, processingHandler), e);
         } catch (IOException var5) {
         }
      }

   }

   protected Object createJmxManagementObject() {
      return MonitoringUtils.loadJmxObject("org.glassfish.grizzly.nio.transport.jmx.TCPNIOTransport", this, TCPNIOTransport.class);
   }

   private void rebindAddress(Connection connection) throws IOException {
      Lock lock = this.state.getStateLocker().writeLock();
      lock.lock();

      try {
         if (Thread.currentThread().isInterrupted()) {
            Thread.interrupted();
         }

         if (this.serverConnections.remove(connection)) {
            SocketAddress address = (SocketAddress)connection.getLocalAddress();
            this.bind(address);
         }
      } finally {
         lock.unlock();
      }

   }

   private static class DefaultChannelConfigurator implements ChannelConfigurator {
      private DefaultChannelConfigurator() {
      }

      public void preConfigure(NIOTransport transport, SelectableChannel channel) throws IOException {
         TCPNIOTransport tcpNioTransport = (TCPNIOTransport)transport;
         if (channel instanceof SocketChannel) {
            SocketChannel sc = (SocketChannel)channel;
            Socket socket = sc.socket();
            sc.configureBlocking(false);
            boolean reuseAddress = tcpNioTransport.isReuseAddress();

            try {
               socket.setReuseAddress(reuseAddress);
            } catch (IOException var9) {
               TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_REUSEADDRESS_EXCEPTION(reuseAddress), var9);
            }
         } else {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)channel;
            ServerSocket serverSocket = serverSocketChannel.socket();
            serverSocketChannel.configureBlocking(false);

            try {
               serverSocket.setReuseAddress(tcpNioTransport.isReuseAddress());
            } catch (IOException var8) {
               TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_REUSEADDRESS_EXCEPTION(tcpNioTransport.isReuseAddress()), var8);
            }
         }

      }

      public void postConfigure(NIOTransport transport, SelectableChannel channel) throws IOException {
         TCPNIOTransport tcpNioTransport = (TCPNIOTransport)transport;
         if (channel instanceof SocketChannel) {
            SocketChannel sc = (SocketChannel)channel;
            Socket socket = sc.socket();
            int linger = tcpNioTransport.getLinger();

            try {
               if (linger >= 0) {
                  socket.setSoLinger(true, linger);
               }
            } catch (IOException var15) {
               TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_LINGER_EXCEPTION(linger), var15);
            }

            boolean keepAlive = tcpNioTransport.isKeepAlive();

            try {
               socket.setKeepAlive(keepAlive);
            } catch (IOException var14) {
               TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_KEEPALIVE_EXCEPTION(keepAlive), var14);
            }

            boolean tcpNoDelay = tcpNioTransport.isTcpNoDelay();

            try {
               socket.setTcpNoDelay(tcpNoDelay);
            } catch (IOException var13) {
               TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_TCPNODELAY_EXCEPTION(tcpNoDelay), var13);
            }

            int clientSocketSoTimeout = tcpNioTransport.getClientSocketSoTimeout();

            try {
               socket.setSoTimeout(clientSocketSoTimeout);
            } catch (IOException var12) {
               TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_TIMEOUT_EXCEPTION(tcpNioTransport.getClientSocketSoTimeout()), var12);
            }
         } else {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel)channel;
            ServerSocket serverSocket = serverSocketChannel.socket();

            try {
               serverSocket.setSoTimeout(tcpNioTransport.getServerSocketSoTimeout());
            } catch (IOException var11) {
               TCPNIOTransport.LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_SOCKET_TIMEOUT_EXCEPTION(tcpNioTransport.getServerSocketSoTimeout()), var11);
            }
         }

      }

      // $FF: synthetic method
      DefaultChannelConfigurator(Object x0) {
         this();
      }
   }

   class TransportConnectorHandler extends TCPNIOConnectorHandler {
      public TransportConnectorHandler() {
         super(TCPNIOTransport.this);
      }

      public Processor getProcessor() {
         return TCPNIOTransport.this.getProcessor();
      }

      public ProcessorSelector getProcessorSelector() {
         return TCPNIOTransport.this.getProcessorSelector();
      }
   }

   class RegisterChannelCompletionHandler extends EmptyCompletionHandler {
      public void completed(RegisterChannelResult result) {
         SelectionKey selectionKey = result.getSelectionKey();
         TCPNIOConnection connection = (TCPNIOConnection)TCPNIOTransport.this.getSelectionKeyHandler().getConnectionForKey(selectionKey);
         if (connection != null) {
            SelectorRunner selectorRunner = result.getSelectorRunner();
            connection.setSelectionKey(selectionKey);
            connection.setSelectorRunner(selectorRunner);
         }

      }
   }
}
