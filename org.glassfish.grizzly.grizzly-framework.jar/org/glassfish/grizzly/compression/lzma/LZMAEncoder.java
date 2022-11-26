package org.glassfish.grizzly.compression.lzma;

import java.io.IOException;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Cacheable;
import org.glassfish.grizzly.ThreadCache;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.compression.lzma.impl.Encoder;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public class LZMAEncoder extends AbstractTransformer {
   private static final ThreadCache.CachedTypeIndex CACHE_IDX = ThreadCache.obtainIndex(LZMAOutputState.class, 2);
   private final LZMAProperties lzmaProperties;

   public LZMAEncoder() {
      this(new LZMAProperties());
   }

   public LZMAEncoder(LZMAProperties lzmaProperties) {
      this.lzmaProperties = lzmaProperties;
   }

   public String getName() {
      return "lzma-encoder";
   }

   public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
      return input.hasRemaining();
   }

   protected TransformationResult transformImpl(AttributeStorage storage, Buffer input) throws TransformationException {
      MemoryManager memoryManager = this.obtainMemoryManager(storage);
      LZMAOutputState state = (LZMAOutputState)this.obtainStateObject(storage);
      if (!state.isInitialized()) {
         this.initializeOutput(state);
      }

      Buffer encodedBuffer = null;
      if (input != null && input.hasRemaining()) {
         try {
            state.setMemoryManager(memoryManager);
            encodedBuffer = this.encodeBuffer(input, state);
         } catch (IOException var7) {
            throw new TransformationException(var7);
         }
      }

      return encodedBuffer == null ? TransformationResult.createIncompletedResult((Object)null) : TransformationResult.createCompletedResult(encodedBuffer, (Object)null);
   }

   protected AbstractTransformer.LastResultAwareState createStateObject() {
      return create();
   }

   public static LZMAOutputState create() {
      LZMAOutputState state = (LZMAOutputState)ThreadCache.takeFromCache(CACHE_IDX);
      return state != null ? state : new LZMAOutputState();
   }

   public void finish(AttributeStorage storage) {
      LZMAOutputState state = (LZMAOutputState)this.obtainStateObject(storage);
      state.recycle();
   }

   private void initializeOutput(LZMAOutputState state) {
      Encoder encoder = state.getEncoder();
      encoder.setAlgorithm(this.lzmaProperties.getAlgorithm());
      encoder.setDictionarySize(this.lzmaProperties.getDictionarySize());
      encoder.setNumFastBytes(this.lzmaProperties.getNumFastBytes());
      encoder.setMatchFinder(this.lzmaProperties.getMatchFinder());
      encoder.setLcLpPb(this.lzmaProperties.getLc(), this.lzmaProperties.getLp(), this.lzmaProperties.getPb());
      encoder.setEndMarkerMode(true);
      state.setInitialized(true);
   }

   private Buffer encodeBuffer(Buffer input, LZMAOutputState state) throws IOException {
      Buffer resultBuffer = null;
      state.setSrc(input);
      Buffer encoded = this.encode(state);
      if (encoded != null) {
         resultBuffer = Buffers.appendBuffers(state.getMemoryManager(), resultBuffer, encoded);
      }

      input.position(input.limit());
      return resultBuffer;
   }

   private Buffer encode(LZMAOutputState outputState) throws IOException {
      Encoder encoder = outputState.getEncoder();
      Buffer dst = outputState.getMemoryManager().allocate(512);
      outputState.setDst(dst);
      if (!outputState.isHeaderWritten()) {
         encoder.writeCoderProperties(outputState.getDst());
         outputState.setHeaderWritten(true);
      }

      encoder.code(outputState, -1L, -1L);
      dst = outputState.getDst();
      int len = dst.position();
      if (len <= 0) {
         dst.dispose();
         return null;
      } else {
         dst.trim();
         return dst;
      }
   }

   public static class LZMAProperties {
      private int algorithm = 2;
      private int dictionarySize = 65536;
      private int numFastBytes = 128;
      private int matchFinder = 1;
      private int lc = 3;
      private int lp = 0;
      private int pb = 2;

      public LZMAProperties() {
         loadProperties(this);
      }

      public LZMAProperties(int algorithm, int dictionarySize, int numFastBytes, int matchFinder, int lc, int lp, int pb) {
         this.algorithm = algorithm;
         this.dictionarySize = dictionarySize;
         this.numFastBytes = numFastBytes;
         this.matchFinder = matchFinder;
         this.lc = lc;
         this.lp = lp;
         this.pb = pb;
      }

      public int getLc() {
         return this.lc;
      }

      public void setLc(int Lc) {
         this.lc = Lc;
      }

      public int getLp() {
         return this.lp;
      }

      public void setLp(int Lp) {
         this.lp = Lp;
      }

      public int getPb() {
         return this.pb;
      }

      public void setPb(int Pb) {
         this.pb = Pb;
      }

      public int getAlgorithm() {
         return this.algorithm;
      }

      public void setAlgorithm(int algorithm) {
         this.algorithm = algorithm;
      }

      public int getDictionarySize() {
         return this.dictionarySize;
      }

      public void setDictionarySize(int dictionarySize) {
         this.dictionarySize = dictionarySize;
      }

      public int getMatchFinder() {
         return this.matchFinder;
      }

      public void setMatchFinder(int matchFinder) {
         this.matchFinder = matchFinder;
      }

      public int getNumFastBytes() {
         return this.numFastBytes;
      }

      public void setNumFastBytes(int numFastBytes) {
         this.numFastBytes = numFastBytes;
      }

      public static void loadProperties(LZMAProperties properties) {
         properties.algorithm = Integer.getInteger("lzma-filter.algorithm", 2);
         properties.dictionarySize = 1 << Integer.getInteger("lzma-filter.dictionary-size", 16);
         properties.numFastBytes = Integer.getInteger("lzma-filter.num-fast-bytes", 128);
         properties.matchFinder = Integer.getInteger("lzma-filter.match-finder", 1);
         properties.lc = Integer.getInteger("lzma-filter.lc", 3);
         properties.lp = Integer.getInteger("lzma-filter.lp", 0);
         properties.pb = Integer.getInteger("lzma-filter.pb", 2);
      }
   }

   public static class LZMAOutputState extends AbstractTransformer.LastResultAwareState implements Cacheable {
      private boolean initialized;
      private final Encoder encoder = new Encoder();
      private Buffer dst;
      private Buffer src;
      private boolean headerWritten = false;
      private MemoryManager mm;

      public boolean isInitialized() {
         return this.initialized;
      }

      public void setInitialized(boolean initialized) {
         this.initialized = initialized;
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

      public Encoder getEncoder() {
         return this.encoder;
      }

      public boolean isHeaderWritten() {
         return this.headerWritten;
      }

      public void setHeaderWritten(boolean headerWritten) {
         this.headerWritten = headerWritten;
      }

      public MemoryManager getMemoryManager() {
         return this.mm;
      }

      public void setMemoryManager(MemoryManager mm) {
         this.mm = mm;
      }

      public void recycle() {
         this.lastResult = null;
         this.initialized = false;
         this.headerWritten = false;
         this.mm = null;
         this.dst = null;
         this.src = null;
         ThreadCache.putToCache(LZMAEncoder.CACHE_IDX, this);
      }
   }
}
