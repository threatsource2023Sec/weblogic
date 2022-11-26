package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import org.glassfish.grizzly.Buffer;

class ReadOnlyHeapBuffer extends HeapBuffer {
   ReadOnlyHeapBuffer(byte[] heap, int offset, int cap) {
      super(heap, offset, cap);
   }

   public HeapBuffer asReadOnlyBuffer() {
      return this;
   }

   public boolean isReadOnly() {
      return true;
   }

   public HeapBuffer prepend(Buffer header) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer put(int index, byte b) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer put(Buffer src) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer put(Buffer src, int position, int length) {
      throw new ReadOnlyBufferException();
   }

   public Buffer put(ByteBuffer src) {
      throw new ReadOnlyBufferException();
   }

   public Buffer put(ByteBuffer src, int position, int length) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer put(byte b) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer put(byte[] src) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer put(byte[] src, int offset, int length) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putChar(char value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putChar(int index, char value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putShort(short value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putShort(int index, short value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putInt(int value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putInt(int index, int value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putLong(long value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putLong(int index, long value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putFloat(float value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putFloat(int index, float value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putDouble(double value) {
      throw new ReadOnlyBufferException();
   }

   public HeapBuffer putDouble(int index, double value) {
      throw new ReadOnlyBufferException();
   }

   protected HeapBuffer createHeapBuffer(int offset, int capacity) {
      return new ReadOnlyHeapBuffer(this.heap, offset, capacity);
   }

   public ByteBuffer toByteBuffer() {
      return super.toByteBuffer().asReadOnlyBuffer();
   }

   public ByteBuffer toByteBuffer(int position, int limit) {
      return super.toByteBuffer(position, limit).asReadOnlyBuffer();
   }
}
