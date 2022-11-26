package com.bea.core.repackaged.springframework.core.io.buffer;

import com.bea.core.repackaged.springframework.util.Assert;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.function.IntPredicate;

public interface DataBuffer {
   DataBufferFactory factory();

   int indexOf(IntPredicate var1, int var2);

   int lastIndexOf(IntPredicate var1, int var2);

   int readableByteCount();

   int writableByteCount();

   int capacity();

   DataBuffer capacity(int var1);

   default DataBuffer ensureCapacity(int capacity) {
      return this;
   }

   int readPosition();

   DataBuffer readPosition(int var1);

   int writePosition();

   DataBuffer writePosition(int var1);

   byte getByte(int var1);

   byte read();

   DataBuffer read(byte[] var1);

   DataBuffer read(byte[] var1, int var2, int var3);

   DataBuffer write(byte var1);

   DataBuffer write(byte[] var1);

   DataBuffer write(byte[] var1, int var2, int var3);

   DataBuffer write(DataBuffer... var1);

   DataBuffer write(ByteBuffer... var1);

   default DataBuffer write(CharSequence charSequence, Charset charset) {
      Assert.notNull(charSequence, (String)"CharSequence must not be null");
      Assert.notNull(charset, (String)"Charset must not be null");
      if (charSequence.length() != 0) {
         CharsetEncoder charsetEncoder = charset.newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
         CharBuffer inBuffer = CharBuffer.wrap(charSequence);
         int estimatedSize = (int)((float)inBuffer.remaining() * charsetEncoder.averageBytesPerChar());
         ByteBuffer outBuffer = this.ensureCapacity(estimatedSize).asByteBuffer(this.writePosition(), this.writableByteCount());

         while(true) {
            CoderResult cr = inBuffer.hasRemaining() ? charsetEncoder.encode(inBuffer, outBuffer, true) : CoderResult.UNDERFLOW;
            if (cr.isUnderflow()) {
               cr = charsetEncoder.flush(outBuffer);
            }

            if (cr.isUnderflow()) {
               this.writePosition(this.writePosition() + outBuffer.position());
               break;
            }

            if (cr.isOverflow()) {
               this.writePosition(this.writePosition() + outBuffer.position());
               int maximumSize = (int)((float)inBuffer.remaining() * charsetEncoder.maxBytesPerChar());
               this.ensureCapacity(maximumSize);
               outBuffer = this.asByteBuffer(this.writePosition(), this.writableByteCount());
            }
         }
      }

      return this;
   }

   DataBuffer slice(int var1, int var2);

   ByteBuffer asByteBuffer();

   ByteBuffer asByteBuffer(int var1, int var2);

   InputStream asInputStream();

   InputStream asInputStream(boolean var1);

   OutputStream asOutputStream();
}
