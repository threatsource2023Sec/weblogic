package weblogic.buzzmessagebus;

import com.oracle.buzzmessagebus.api.BuzzAdmin;
import com.oracle.buzzmessagebus.api.BuzzAdminReceiver;
import com.oracle.buzzmessagebus.api.BuzzFactory;
import com.oracle.common.internal.Platform;
import com.oracle.common.net.InetAddresses;
import com.oracle.common.net.exabus.EndPoint;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicReference;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.KernelStatus;

public final class BuzzController implements BuzzAdminReceiver {
   private static final DebugLogger debugBuzzProtocol = DebugLogger.getDebugLogger("DebugBuzzProtocol");
   private static BuzzController theBuzzController = null;
   private final AtomicReference buzzSender = new AtomicReference((Object)null);
   private BuzzSubprotocolReceiverHttpWLSImpl httpBuzzSubprotocolReceiver = null;
   private BuzzAdmin buzzAdmin = null;

   private BuzzController() {
   }

   static synchronized BuzzController getInstance() {
      if (theBuzzController == null) {
         theBuzzController = new BuzzController();
      }

      return theBuzzController;
   }

   void init(BuzzHTTPFactory bhf, BuzzMessageBusEndPointMBeanInfo bmbInfo) {
      String sBind = this.getMsgBusEndPointURL(bmbInfo);
      this.httpBuzzSubprotocolReceiver = new BuzzSubprotocolReceiverHttpWLSImpl(bhf);
      boolean set = this.buzzSender.compareAndSet((Object)null, BuzzFactory.getBuilder(new String[0]).name("service").address(sBind).addSubprotocolReceiver(this.httpBuzzSubprotocolReceiver).addAdminReceiver(this).logger(new BuzzLoggerWLSImpl()).build());
      if (!set) {
         throw new IllegalArgumentException("Attempt to initialize BuzzSender more than once.");
      } else {
         BuzzMessageBusLogger.logBuzzMessageBusInitialized(sBind);
      }
   }

   void close() {
      if (debugBuzzProtocol.isDebugEnabled()) {
         debugBuzzProtocol.debug("BuzzController close");
      }

      try {
         this.httpBuzzSubprotocolReceiver.cleanUp();
      } catch (Throwable var5) {
         if (debugBuzzProtocol.isDebugEnabled()) {
            debugBuzzProtocol.debug("Throwable while calling close", var5);
         }
      } finally {
         this.buzzSender.set((Object)null);
         this.buzzAdmin.close();
      }

   }

   private String getMsgBusEndPointURL(BuzzMessageBusEndPointMBeanInfo buzzMessageBusEndPointMBeanInfo) {
      StringBuilder sb = new StringBuilder();
      if (Platform.getPlatform().isExaEnabled()) {
         sb.append("imb://");
      } else {
         sb.append("tmb://");
      }

      String address = buzzMessageBusEndPointMBeanInfo.getBuzzAddress();

      try {
         if (address != null && address.length() > 0) {
            sb.append(address);
         } else {
            sb.append(InetAddresses.getLocalHost().getHostAddress());
         }
      } catch (UnknownHostException var5) {
         sb.append("127.0.0.1");
      }

      sb.append(":").append(buzzMessageBusEndPointMBeanInfo.getBuzzPort());
      return sb.toString();
   }

   public void open(EndPoint endPoint) {
      KernelStatus.setBuzzAddress(endPoint.getCanonicalName());
   }

   public void close(EndPoint endPoint) {
      KernelStatus.setBuzzAddress((String)null);
      KernelStatus.setBuzzSocketAddress((InetSocketAddress)null);
   }

   public void setBuzzAdmin(BuzzAdmin b) {
      this.buzzAdmin = b;
   }
}
