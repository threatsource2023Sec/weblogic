package org.glassfish.grizzly.utils;

import java.util.logging.Logger;
import org.glassfish.grizzly.AbstractTransformer;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.attributes.AttributeStorage;
import org.glassfish.grizzly.filterchain.AbstractCodecFilter;
import org.glassfish.grizzly.memory.Buffers;

public class ChunkingFilter extends AbstractCodecFilter {
   private static final Logger LOGGER = Grizzly.logger(ChunkingFilter.class);
   private final int chunkSize;

   public ChunkingFilter(int chunkSize) {
      super(new ChunkingDecoder(chunkSize), new ChunkingEncoder(chunkSize));
      this.chunkSize = chunkSize;
   }

   public int getChunkSize() {
      return this.chunkSize;
   }

   public abstract static class ChunkingTransformer extends AbstractTransformer {
      private final int chunk;

      public ChunkingTransformer(int chunk) {
         this.chunk = chunk;
      }

      public String getName() {
         return "ChunkingTransformer";
      }

      protected TransformationResult transformImpl(AttributeStorage storage, Buffer input) throws TransformationException {
         if (!input.hasRemaining()) {
            return TransformationResult.createIncompletedResult(input);
         } else {
            int chunkSize = Math.min(this.chunk, input.remaining());
            int oldInputPos = input.position();
            int oldInputLimit = input.limit();
            Buffers.setPositionLimit(input, oldInputPos, oldInputPos + chunkSize);
            Buffer output = this.obtainMemoryManager(storage).allocate(chunkSize);
            output.put(input).flip();
            Buffers.setPositionLimit(input, oldInputPos + chunkSize, oldInputLimit);
            return TransformationResult.createCompletedResult(output, input);
         }
      }

      public boolean hasInputRemaining(AttributeStorage storage, Buffer input) {
         return input != null && input.hasRemaining();
      }
   }

   public static final class ChunkingEncoder extends ChunkingTransformer {
      public ChunkingEncoder(int chunk) {
         super(chunk);
      }
   }

   public static final class ChunkingDecoder extends ChunkingTransformer {
      public ChunkingDecoder(int chunk) {
         super(chunk);
      }
   }
}
