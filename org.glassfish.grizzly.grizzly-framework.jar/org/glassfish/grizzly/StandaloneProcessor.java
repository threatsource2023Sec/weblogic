package org.glassfish.grizzly;

import org.glassfish.grizzly.asyncqueue.AsyncQueueEnabledTransport;
import org.glassfish.grizzly.asyncqueue.AsyncQueueReader;
import org.glassfish.grizzly.asyncqueue.AsyncQueueWriter;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.asyncqueue.PushBackHandler;
import org.glassfish.grizzly.nio.transport.DefaultStreamReader;
import org.glassfish.grizzly.nio.transport.DefaultStreamWriter;
import org.glassfish.grizzly.streams.StreamReader;
import org.glassfish.grizzly.streams.StreamWriter;

public class StandaloneProcessor implements Processor {
   public static final StandaloneProcessor INSTANCE = new StandaloneProcessor();

   public ProcessorResult process(Context context) {
      IOEvent iOEvent = context.getIoEvent();
      Connection connection;
      if (iOEvent == IOEvent.READ) {
         connection = context.getConnection();
         AsyncQueueReader reader = ((AsyncQueueEnabledTransport)connection.getTransport()).getAsyncQueueIO().getReader();
         return reader.processAsync(context).toProcessorResult();
      } else if (iOEvent == IOEvent.WRITE) {
         connection = context.getConnection();
         AsyncQueueWriter writer = ((AsyncQueueEnabledTransport)connection.getTransport()).getAsyncQueueIO().getWriter();
         return writer.processAsync(context).toProcessorResult();
      } else {
         throw new IllegalStateException("Unexpected IOEvent=" + iOEvent);
      }
   }

   public boolean isInterested(IOEvent ioEvent) {
      return ioEvent == IOEvent.READ || ioEvent == IOEvent.WRITE;
   }

   public void setInterested(IOEvent ioEvent, boolean isInterested) {
   }

   public Context obtainContext(Connection connection) {
      Context context = Context.create(connection);
      context.setProcessor(this);
      return context;
   }

   public StreamReader getStreamReader(Connection connection) {
      return new DefaultStreamReader(connection);
   }

   public StreamWriter getStreamWriter(Connection connection) {
      return new DefaultStreamWriter(connection);
   }

   public void read(Connection connection, CompletionHandler completionHandler) {
      Transport transport = connection.getTransport();
      transport.getReader(connection).read(connection, (Buffer)null, completionHandler);
   }

   public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler) {
      this.write(connection, dstAddress, message, completionHandler, (MessageCloner)null);
   }

   public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, MessageCloner messageCloner) {
      Transport transport = connection.getTransport();
      transport.getWriter(connection).write(connection, dstAddress, (Buffer)message, completionHandler, (MessageCloner)messageCloner);
   }

   /** @deprecated */
   @Deprecated
   public void write(Connection connection, Object dstAddress, Object message, CompletionHandler completionHandler, PushBackHandler pushBackHandler) {
      Transport transport = connection.getTransport();
      transport.getWriter(connection).write(connection, dstAddress, (Buffer)message, completionHandler, (PushBackHandler)pushBackHandler);
   }
}
