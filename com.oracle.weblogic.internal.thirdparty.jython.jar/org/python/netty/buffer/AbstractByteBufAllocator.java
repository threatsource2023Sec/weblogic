package org.python.netty.buffer;

import org.python.netty.util.ResourceLeakDetector;
import org.python.netty.util.ResourceLeakTracker;
import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.StringUtil;

public abstract class AbstractByteBufAllocator implements ByteBufAllocator {
   static final int DEFAULT_INITIAL_CAPACITY = 256;
   static final int DEFAULT_MAX_CAPACITY = Integer.MAX_VALUE;
   static final int DEFAULT_MAX_COMPONENTS = 16;
   static final int CALCULATE_THRESHOLD = 4194304;
   private final boolean directByDefault;
   private final ByteBuf emptyBuf;

   protected static ByteBuf toLeakAwareBuffer(ByteBuf buf) {
      ResourceLeakTracker leak;
      switch (ResourceLeakDetector.getLevel()) {
         case SIMPLE:
            leak = AbstractByteBuf.leakDetector.track(buf);
            if (leak != null) {
               buf = new SimpleLeakAwareByteBuf((ByteBuf)buf, leak);
            }
            break;
         case ADVANCED:
         case PARANOID:
            leak = AbstractByteBuf.leakDetector.track(buf);
            if (leak != null) {
               buf = new AdvancedLeakAwareByteBuf((ByteBuf)buf, leak);
            }
      }

      return (ByteBuf)buf;
   }

   protected static CompositeByteBuf toLeakAwareBuffer(CompositeByteBuf buf) {
      ResourceLeakTracker leak;
      switch (ResourceLeakDetector.getLevel()) {
         case SIMPLE:
            leak = AbstractByteBuf.leakDetector.track(buf);
            if (leak != null) {
               buf = new SimpleLeakAwareCompositeByteBuf((CompositeByteBuf)buf, leak);
            }
            break;
         case ADVANCED:
         case PARANOID:
            leak = AbstractByteBuf.leakDetector.track(buf);
            if (leak != null) {
               buf = new AdvancedLeakAwareCompositeByteBuf((CompositeByteBuf)buf, leak);
            }
      }

      return (CompositeByteBuf)buf;
   }

   protected AbstractByteBufAllocator() {
      this(false);
   }

   protected AbstractByteBufAllocator(boolean preferDirect) {
      this.directByDefault = preferDirect && PlatformDependent.hasUnsafe();
      this.emptyBuf = new EmptyByteBuf(this);
   }

   public ByteBuf buffer() {
      return this.directByDefault ? this.directBuffer() : this.heapBuffer();
   }

   public ByteBuf buffer(int initialCapacity) {
      return this.directByDefault ? this.directBuffer(initialCapacity) : this.heapBuffer(initialCapacity);
   }

   public ByteBuf buffer(int initialCapacity, int maxCapacity) {
      return this.directByDefault ? this.directBuffer(initialCapacity, maxCapacity) : this.heapBuffer(initialCapacity, maxCapacity);
   }

   public ByteBuf ioBuffer() {
      return PlatformDependent.hasUnsafe() ? this.directBuffer(256) : this.heapBuffer(256);
   }

   public ByteBuf ioBuffer(int initialCapacity) {
      return PlatformDependent.hasUnsafe() ? this.directBuffer(initialCapacity) : this.heapBuffer(initialCapacity);
   }

   public ByteBuf ioBuffer(int initialCapacity, int maxCapacity) {
      return PlatformDependent.hasUnsafe() ? this.directBuffer(initialCapacity, maxCapacity) : this.heapBuffer(initialCapacity, maxCapacity);
   }

   public ByteBuf heapBuffer() {
      return this.heapBuffer(256, Integer.MAX_VALUE);
   }

   public ByteBuf heapBuffer(int initialCapacity) {
      return this.heapBuffer(initialCapacity, Integer.MAX_VALUE);
   }

   public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
      if (initialCapacity == 0 && maxCapacity == 0) {
         return this.emptyBuf;
      } else {
         validate(initialCapacity, maxCapacity);
         return this.newHeapBuffer(initialCapacity, maxCapacity);
      }
   }

   public ByteBuf directBuffer() {
      return this.directBuffer(256, Integer.MAX_VALUE);
   }

   public ByteBuf directBuffer(int initialCapacity) {
      return this.directBuffer(initialCapacity, Integer.MAX_VALUE);
   }

   public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
      if (initialCapacity == 0 && maxCapacity == 0) {
         return this.emptyBuf;
      } else {
         validate(initialCapacity, maxCapacity);
         return this.newDirectBuffer(initialCapacity, maxCapacity);
      }
   }

   public CompositeByteBuf compositeBuffer() {
      return this.directByDefault ? this.compositeDirectBuffer() : this.compositeHeapBuffer();
   }

   public CompositeByteBuf compositeBuffer(int maxNumComponents) {
      return this.directByDefault ? this.compositeDirectBuffer(maxNumComponents) : this.compositeHeapBuffer(maxNumComponents);
   }

   public CompositeByteBuf compositeHeapBuffer() {
      return this.compositeHeapBuffer(16);
   }

   public CompositeByteBuf compositeHeapBuffer(int maxNumComponents) {
      return toLeakAwareBuffer(new CompositeByteBuf(this, false, maxNumComponents));
   }

   public CompositeByteBuf compositeDirectBuffer() {
      return this.compositeDirectBuffer(16);
   }

   public CompositeByteBuf compositeDirectBuffer(int maxNumComponents) {
      return toLeakAwareBuffer(new CompositeByteBuf(this, true, maxNumComponents));
   }

   private static void validate(int initialCapacity, int maxCapacity) {
      if (initialCapacity < 0) {
         throw new IllegalArgumentException("initialCapacity: " + initialCapacity + " (expected: 0+)");
      } else if (initialCapacity > maxCapacity) {
         throw new IllegalArgumentException(String.format("initialCapacity: %d (expected: not greater than maxCapacity(%d)", initialCapacity, maxCapacity));
      }
   }

   protected abstract ByteBuf newHeapBuffer(int var1, int var2);

   protected abstract ByteBuf newDirectBuffer(int var1, int var2);

   public String toString() {
      return StringUtil.simpleClassName((Object)this) + "(directByDefault: " + this.directByDefault + ')';
   }

   public int calculateNewCapacity(int minNewCapacity, int maxCapacity) {
      if (minNewCapacity < 0) {
         throw new IllegalArgumentException("minNewCapacity: " + minNewCapacity + " (expected: 0+)");
      } else if (minNewCapacity > maxCapacity) {
         throw new IllegalArgumentException(String.format("minNewCapacity: %d (expected: not greater than maxCapacity(%d)", minNewCapacity, maxCapacity));
      } else {
         int threshold = 4194304;
         if (minNewCapacity == 4194304) {
            return 4194304;
         } else {
            int newCapacity;
            if (minNewCapacity > 4194304) {
               newCapacity = minNewCapacity / 4194304 * 4194304;
               if (newCapacity > maxCapacity - 4194304) {
                  newCapacity = maxCapacity;
               } else {
                  newCapacity += 4194304;
               }

               return newCapacity;
            } else {
               for(newCapacity = 64; newCapacity < minNewCapacity; newCapacity <<= 1) {
               }

               return Math.min(newCapacity, maxCapacity);
            }
         }
      }
   }
}
