package org.python.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkEncoder;
import com.ning.compress.lzf.LZFEncoder;
import com.ning.compress.lzf.util.ChunkEncoderFactory;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.handler.codec.MessageToByteEncoder;

public class LzfEncoder extends MessageToByteEncoder {
   private static final int MIN_BLOCK_TO_COMPRESS = 16;
   private final ChunkEncoder encoder;
   private final BufferRecycler recycler;

   public LzfEncoder() {
      this(false, 65535);
   }

   public LzfEncoder(boolean safeInstance) {
      this(safeInstance, 65535);
   }

   public LzfEncoder(int totalLength) {
      this(false, totalLength);
   }

   public LzfEncoder(boolean safeInstance, int totalLength) {
      super(false);
      if (totalLength >= 16 && totalLength <= 65535) {
         this.encoder = safeInstance ? ChunkEncoderFactory.safeNonAllocatingInstance(totalLength) : ChunkEncoderFactory.optimalNonAllocatingInstance(totalLength);
         this.recycler = BufferRecycler.instance();
      } else {
         throw new IllegalArgumentException("totalLength: " + totalLength + " (expected: " + 16 + '-' + '\uffff' + ')');
      }
   }

   protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
      int length = in.readableBytes();
      int idx = in.readerIndex();
      byte[] input;
      int inputPtr;
      if (in.hasArray()) {
         input = in.array();
         inputPtr = in.arrayOffset() + idx;
      } else {
         input = this.recycler.allocInputBuffer(length);
         in.getBytes(idx, (byte[])input, 0, length);
         inputPtr = 0;
      }

      int maxOutputLength = LZFEncoder.estimateMaxWorkspaceSize(length);
      out.ensureWritable(maxOutputLength);
      byte[] output = out.array();
      int outputPtr = out.arrayOffset() + out.writerIndex();
      int outputLength = LZFEncoder.appendEncoded(this.encoder, input, inputPtr, length, output, outputPtr) - outputPtr;
      out.writerIndex(out.writerIndex() + outputLength);
      in.skipBytes(length);
      if (!in.hasArray()) {
         this.recycler.releaseInputBuffer(input);
      }

   }
}
