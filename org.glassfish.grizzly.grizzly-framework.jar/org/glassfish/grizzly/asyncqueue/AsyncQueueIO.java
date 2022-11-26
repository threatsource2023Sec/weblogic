package org.glassfish.grizzly.asyncqueue;

public interface AsyncQueueIO {
   AsyncQueueReader getReader();

   AsyncQueueWriter getWriter();

   public static final class MutableAsyncQueueIO implements AsyncQueueIO {
      private volatile AsyncQueueReader reader;
      private volatile AsyncQueueWriter writer;

      private MutableAsyncQueueIO(AsyncQueueReader reader, AsyncQueueWriter writer) {
         this.reader = reader;
         this.writer = writer;
      }

      public AsyncQueueReader getReader() {
         return this.reader;
      }

      public void setReader(AsyncQueueReader reader) {
         this.reader = reader;
      }

      public AsyncQueueWriter getWriter() {
         return this.writer;
      }

      public void setWriter(AsyncQueueWriter writer) {
         this.writer = writer;
      }

      // $FF: synthetic method
      MutableAsyncQueueIO(AsyncQueueReader x0, AsyncQueueWriter x1, Object x2) {
         this(x0, x1);
      }
   }

   public static final class ImmutableAsyncQueueIO implements AsyncQueueIO {
      private final AsyncQueueReader reader;
      private final AsyncQueueWriter writer;

      private ImmutableAsyncQueueIO(AsyncQueueReader reader, AsyncQueueWriter writer) {
         this.reader = reader;
         this.writer = writer;
      }

      public AsyncQueueReader getReader() {
         return this.reader;
      }

      public AsyncQueueWriter getWriter() {
         return this.writer;
      }

      // $FF: synthetic method
      ImmutableAsyncQueueIO(AsyncQueueReader x0, AsyncQueueWriter x1, Object x2) {
         this(x0, x1);
      }
   }

   public static class Factory {
      public static AsyncQueueIO createImmutable(AsyncQueueReader reader, AsyncQueueWriter writer) {
         return new ImmutableAsyncQueueIO(reader, writer);
      }

      public static MutableAsyncQueueIO createMutable(AsyncQueueReader reader, AsyncQueueWriter writer) {
         return new MutableAsyncQueueIO(reader, writer);
      }
   }
}
