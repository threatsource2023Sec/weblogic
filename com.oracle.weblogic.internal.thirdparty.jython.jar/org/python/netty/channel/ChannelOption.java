package org.python.netty.channel;

import org.python.netty.util.AbstractConstant;
import org.python.netty.util.ConstantPool;

public class ChannelOption extends AbstractConstant {
   private static final ConstantPool pool = new ConstantPool() {
      protected ChannelOption newConstant(int id, String name) {
         return new ChannelOption(id, name);
      }
   };
   public static final ChannelOption ALLOCATOR = valueOf("ALLOCATOR");
   public static final ChannelOption RCVBUF_ALLOCATOR = valueOf("RCVBUF_ALLOCATOR");
   public static final ChannelOption MESSAGE_SIZE_ESTIMATOR = valueOf("MESSAGE_SIZE_ESTIMATOR");
   public static final ChannelOption CONNECT_TIMEOUT_MILLIS = valueOf("CONNECT_TIMEOUT_MILLIS");
   /** @deprecated */
   @Deprecated
   public static final ChannelOption MAX_MESSAGES_PER_READ = valueOf("MAX_MESSAGES_PER_READ");
   public static final ChannelOption WRITE_SPIN_COUNT = valueOf("WRITE_SPIN_COUNT");
   /** @deprecated */
   @Deprecated
   public static final ChannelOption WRITE_BUFFER_HIGH_WATER_MARK = valueOf("WRITE_BUFFER_HIGH_WATER_MARK");
   /** @deprecated */
   @Deprecated
   public static final ChannelOption WRITE_BUFFER_LOW_WATER_MARK = valueOf("WRITE_BUFFER_LOW_WATER_MARK");
   public static final ChannelOption WRITE_BUFFER_WATER_MARK = valueOf("WRITE_BUFFER_WATER_MARK");
   public static final ChannelOption ALLOW_HALF_CLOSURE = valueOf("ALLOW_HALF_CLOSURE");
   public static final ChannelOption AUTO_READ = valueOf("AUTO_READ");
   /** @deprecated */
   @Deprecated
   public static final ChannelOption AUTO_CLOSE = valueOf("AUTO_CLOSE");
   public static final ChannelOption SO_BROADCAST = valueOf("SO_BROADCAST");
   public static final ChannelOption SO_KEEPALIVE = valueOf("SO_KEEPALIVE");
   public static final ChannelOption SO_SNDBUF = valueOf("SO_SNDBUF");
   public static final ChannelOption SO_RCVBUF = valueOf("SO_RCVBUF");
   public static final ChannelOption SO_REUSEADDR = valueOf("SO_REUSEADDR");
   public static final ChannelOption SO_LINGER = valueOf("SO_LINGER");
   public static final ChannelOption SO_BACKLOG = valueOf("SO_BACKLOG");
   public static final ChannelOption SO_TIMEOUT = valueOf("SO_TIMEOUT");
   public static final ChannelOption IP_TOS = valueOf("IP_TOS");
   public static final ChannelOption IP_MULTICAST_ADDR = valueOf("IP_MULTICAST_ADDR");
   public static final ChannelOption IP_MULTICAST_IF = valueOf("IP_MULTICAST_IF");
   public static final ChannelOption IP_MULTICAST_TTL = valueOf("IP_MULTICAST_TTL");
   public static final ChannelOption IP_MULTICAST_LOOP_DISABLED = valueOf("IP_MULTICAST_LOOP_DISABLED");
   public static final ChannelOption TCP_NODELAY = valueOf("TCP_NODELAY");
   /** @deprecated */
   @Deprecated
   public static final ChannelOption DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION = valueOf("DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION");
   public static final ChannelOption SINGLE_EVENTEXECUTOR_PER_GROUP = valueOf("SINGLE_EVENTEXECUTOR_PER_GROUP");

   public static ChannelOption valueOf(String name) {
      return (ChannelOption)pool.valueOf(name);
   }

   public static ChannelOption valueOf(Class firstNameComponent, String secondNameComponent) {
      return (ChannelOption)pool.valueOf(firstNameComponent, secondNameComponent);
   }

   public static boolean exists(String name) {
      return pool.exists(name);
   }

   public static ChannelOption newInstance(String name) {
      return (ChannelOption)pool.newInstance(name);
   }

   private ChannelOption(int id, String name) {
      super(id, name);
   }

   /** @deprecated */
   @Deprecated
   protected ChannelOption(String name) {
      this(pool.nextId(), name);
   }

   public void validate(Object value) {
      if (value == null) {
         throw new NullPointerException("value");
      }
   }

   // $FF: synthetic method
   ChannelOption(int x0, String x1, Object x2) {
      this(x0, x1);
   }
}
