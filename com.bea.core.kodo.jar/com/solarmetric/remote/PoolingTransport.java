package com.solarmetric.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.openjpa.lib.util.Localizer;

public class PoolingTransport extends GenericObjectPool implements Transport, PoolableObjectFactory {
   public static final int ACTION_DESTROY = 0;
   public static final int ACTION_VALIDATE = 1;
   public static final int ACTION_NONE = 2;
   private static final Object CHANNEL_MARKER = new Object();
   private static final Localizer _loc = Localizer.forPackage(PoolingTransport.class);
   private final Transport _transport;
   private final CommandIO _io;
   private int _validationTime = 300000;
   private int _exceptionAction = 0;

   public PoolingTransport(Transport transport, CommandIO io) {
      super((PoolableObjectFactory)null);
      this.setFactory(this);
      this.setWhenExhaustedAction((byte)1);
      this.setMaxWait(3000L);
      this.setTestOnBorrow(true);
      this._transport = transport;
      this._io = io;
   }

   public Transport getDelegate() {
      return this._transport;
   }

   public CommandIO getCommandIO() {
      return this._io;
   }

   public int getValidationTimeout() {
      return this._validationTime;
   }

   public void setValidationTimeout(int time) {
      this._validationTime = time;
   }

   public void setWhenExhaustedAction(String action) {
      if (action != null) {
         if ("block".equals(action)) {
            this.setWhenExhaustedAction((byte)1);
         } else if ("exception".equals(action)) {
            this.setWhenExhaustedAction((byte)0);
         } else if ("grow".equals(action)) {
            this.setWhenExhaustedAction((byte)2);
         } else {
            byte b;
            try {
               b = Byte.parseByte(action);
            } catch (RuntimeException var4) {
               throw new IllegalArgumentException(action);
            }

            this.setWhenExhaustedAction(b);
         }

      }
   }

   public void setExceptionAction(String action) {
      if (action != null) {
         if ("destroy".equals(action)) {
            this.setExceptionAction(0);
         } else if ("validate".equals(action)) {
            this.setExceptionAction(1);
         } else {
            if (!"none".equals(action)) {
               throw new IllegalArgumentException(action);
            }

            this.setExceptionAction(2);
         }

      }
   }

   public int getExceptionAction() {
      return this._exceptionAction;
   }

   public void setExceptionAction(int action) {
      this._exceptionAction = action;
   }

   public Transport.Server getServer() throws Exception {
      return this._transport.getServer();
   }

   public Transport.Channel getClientChannel() throws Exception {
      if (this.getMaxActive() < 1) {
         return this.makeChannel();
      } else {
         if (this._io.getLog() != null && this._io.getLog().isTraceEnabled()) {
            this._io.getLog().trace(this.msg(this.toString(), (Transport.Channel)null));
         }

         long start = System.currentTimeMillis();
         Object o = this.borrowObject();
         long time = System.currentTimeMillis() - start;
         Transport.Channel channel;
         if (o == CHANNEL_MARKER) {
            channel = this.makeChannel();
         } else {
            channel = (Transport.Channel)o;
         }

         if (this._io.getLog() != null) {
            if (this.getMaxWait() > 0L && time >= 500L && this._io.getLog().isInfoEnabled()) {
               this._io.getLog().info(_loc.get("wait-on-conn", String.valueOf(time), channel));
            }

            if (this._io.getLog().isTraceEnabled()) {
               this._io.getLog().trace(this.msg("checkout", channel));
            }
         }

         return channel;
      }
   }

   private void returnClientChannel(PoolChannel channel) {
      if (this.getMaxActive() < 1) {
         this.destroyObject(channel);
      } else {
         if (this._io.getLog() != null && this._io.getLog().isTraceEnabled()) {
            this._io.getLog().trace(this.msg("return", channel));
         }

         try {
            this.returnObject(channel);
         } catch (Exception var3) {
         }
      }

   }

   public Object makeObject() {
      return CHANNEL_MARKER;
   }

   private Transport.Channel makeChannel() throws Exception {
      Transport.Channel channel = new PoolChannel(this._transport.getClientChannel());
      if (this._io.getLog() != null && this._io.getLog().isTraceEnabled()) {
         this._io.getLog().trace(this.msg("open", channel));
      }

      return channel;
   }

   public void destroyObject(Object obj) {
      if (obj != CHANNEL_MARKER) {
         PoolChannel channel = (PoolChannel)obj;
         if (this._io.getLog() != null && this._io.getLog().isTraceEnabled()) {
            this._io.getLog().trace(this.msg("free", channel));
         }

         try {
            channel.getDelegate().close();
         } catch (Exception var4) {
         }

      }
   }

   public boolean validateObject(Object obj) {
      if (obj == CHANNEL_MARKER) {
         return true;
      } else {
         PoolChannel channel = (PoolChannel)obj;

         try {
            int errs = channel.getErrorCount();
            if (errs > 0 && this._exceptionAction == 0) {
               return false;
            } else {
               if (errs > 0 && this._exceptionAction == 1 || this._validationTime <= 0 || channel.getLastValidatedTime() + (long)this._validationTime <= System.currentTimeMillis()) {
                  this._io.test(channel);
                  if (this._validationTime > 0) {
                     channel.setLastValidatedTime(System.currentTimeMillis());
                  }
               }

               channel.resetErrorCount();
               return true;
            }
         } catch (Exception var4) {
            if (this._io.getLog() != null && this._io.getLog().isTraceEnabled()) {
               this._io.getLog().trace(this.msg("validation failed", channel));
            }

            return false;
         }
      }
   }

   public void activateObject(Object value) {
   }

   public void passivateObject(Object value) {
   }

   public void close() {
      try {
         super.close();
      } catch (Exception var2) {
      }

   }

   private Localizer.Message msg(String msg, Transport.Channel channel) {
      StringBuffer buf = new StringBuffer(25 + msg.length());
      buf.append("<t ").append(Thread.currentThread().hashCode());
      if (channel != null) {
         buf.append(", chan ").append(channel.hashCode());
      }

      buf.append("> ");
      buf.append(msg);
      return _loc.get(buf.toString());
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("channel pool: active=").append(this.getNumActive()).append(", idle=").append(this.getNumIdle());
      return buf.toString();
   }

   public class PoolChannel implements Transport.Channel {
      private final Transport.Channel _delegate;
      private long _validated = System.currentTimeMillis();
      private int _errs = 0;

      public PoolChannel(Transport.Channel delegate) {
         this._delegate = delegate;
      }

      public Transport.Channel getDelegate() {
         return this._delegate;
      }

      public long getLastValidatedTime() {
         return this._validated;
      }

      public void setLastValidatedTime(long time) {
         this._validated = time;
      }

      public int getErrorCount() {
         return this._errs;
      }

      public void resetErrorCount() {
         this._errs = 0;
      }

      public InputStream getInput() throws Exception {
         return this._delegate.getInput();
      }

      public OutputStream getOutput() throws Exception {
         return this._delegate.getOutput();
      }

      public void error(IOException ioe) {
         ++this._errs;
      }

      public void close() throws Exception {
         PoolingTransport.this.returnClientChannel(this);
      }
   }
}
