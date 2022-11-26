package org.glassfish.grizzly.compression.lzma;

import java.io.IOException;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.compression.lzma.impl.Base;
import org.glassfish.grizzly.compression.lzma.impl.Decoder;
import org.glassfish.grizzly.memory.MemoryManager;

public class LZMADecoder extends AbstractTransformer {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(LZMAInputState.class, 2);

   public String getName() {
      return "lzma-decoder";
   }

   public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
      return input.hasRemaining();
   }

   protected TransformationResult transformImpl(AttributeStorage storage, Buffer input) throws TransformationException {
      MemoryManager memoryManager = this.obtainMemoryManager(storage);
      LZMAInputState state = (LZMAInputState)this.obtainStateObject(storage);
      state.setMemoryManager(memoryManager);
      Buffer decodedBuffer = null;
      Decoder.State decState = null;
      if (input.hasRemaining()) {
         decState = this.decodeBuffer(memoryManager, input, state);
         decodedBuffer = state.getDst();
      }

      boolean hasRemainder = input.hasRemaining();
      return decState != Decoder.State.NEED_MORE_DATA && decodedBuffer != null ? TransformationResult.createCompletedResult(decodedBuffer.flip(), hasRemainder ? input : null) : TransformationResult.createIncompletedResult(hasRemainder ? input : null);
   }

   protected AbstractTransformer.LastResultAwareState createStateObject() {
      return create();
   }

   public static LZMAInputState create() {
      LZMAInputState state = (LZMAInputState)ThreadCache.takeFromCache(CACHE_IDX);
      return state != null ? state : new LZMAInputState();
   }

   public void finish(AttributeStorage storage) {
      LZMAInputState state = (LZMAInputState)this.obtainStateObject(storage);
      state.recycle();
   }

   private Decoder.State decodeBuffer(MemoryManager memoryManager, Buffer buffer, LZMAInputState state) {
      state.setSrc(buffer);

      Decoder.State decState;
      try {
         decState = state.getDecoder().code(state, -1L);
      } catch (IOException var6) {
         disposeDstBuffer(state);
         throw new IllegalStateException(var6);
      }

      if (decState == Decoder.State.ERR) {
         disposeDstBuffer(state);
         throw new IllegalStateException("Invalid decoder state.");
      } else {
         return decState;
      }
   }

   private static void disposeDstBuffer(LZMAInputState state) {
      Buffer dstBuffer = state.getDst();
      if (dstBuffer != null) {
         dstBuffer.dispose();
         state.setDst((Buffer)null);
      }

   }

   public static class LZMAInputState extends AbstractTransformer.LastResultAwareState implements Cacheable {
      private final Decoder decoder = new Decoder();
      private boolean initialized;
      private final byte[] decoderConfigBits = new byte[5];
      private Buffer src;
      private Buffer dst;
      private MemoryManager mm;
      public int state;
      public int rep0;
      public int rep1;
      public int rep2;
      public int rep3;
      public long nowPos64;
      public byte prevByte;
      public boolean decInitialized;
      public int posState;
      public int lastMethodResult;
      public int inner1State;
      public int inner2State;
      public Decoder.LiteralDecoder.Decoder2 decoder2;
      public int staticReverseDecodeMethodState;
      public int staticM;
      public int staticBitIndex;
      public int staticSymbol;
      public int state3Len;
      public int state31;
      public int state311;
      public int state311Distance;
      public int state32;
      public int state32PosSlot;
      public int state321;
      public int state321NumDirectBits;

      public boolean initialize(Buffer buffer) {
         buffer.get(this.decoderConfigBits);
         this.initialized = this.decoder.setDecoderProperties(this.decoderConfigBits);
         this.state = Base.stateInit();
         return this.initialized;
      }

      public boolean isInitialized() {
         return this.initialized;
      }

      public Decoder getDecoder() {
         return this.decoder;
      }

      public Buffer getSrc() {
         return this.src;
      }

      public void setSrc(Buffer src) {
         this.src = src;
      }

      public Buffer getDst() {
         return this.dst;
      }

      public void setDst(Buffer dst) {
         this.dst = dst;
      }

      public MemoryManager getMemoryManager() {
         return this.mm;
      }

      public void setMemoryManager(MemoryManager mm) {
         this.mm = mm;
      }

      public void recycle() {
         this.state = 0;
         this.rep0 = 0;
         this.rep1 = 0;
         this.rep2 = 0;
         this.rep3 = 0;
         this.nowPos64 = 0L;
         this.prevByte = 0;
         this.src = null;
         this.dst = null;
         this.lastResult = null;
         this.initialized = false;
         this.decInitialized = false;
         this.mm = null;
         this.posState = 0;
         this.lastMethodResult = 0;
         this.inner1State = 0;
         this.inner2State = 0;
         this.decoder2 = null;
         this.staticReverseDecodeMethodState = 0;
         this.state31 = 0;
         this.state311 = 0;
         this.state32 = 0;
         this.state321 = 0;
         ThreadCache.putToCache(LZMADecoder.CACHE_IDX, this);
      }
   }
}
