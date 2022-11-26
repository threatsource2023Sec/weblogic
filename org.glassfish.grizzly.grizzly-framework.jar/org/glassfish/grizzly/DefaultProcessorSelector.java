package org.glassfish.grizzly;

public class DefaultProcessorSelector implements ProcessorSelector {
   protected final Transport transport;

   public DefaultProcessorSelector(Transport transport) {
      this.transport = transport;
   }

   public Processor select(IOEvent ioEvent, Connection connection) {
      Processor eventProcessor = connection.getProcessor();
      if (eventProcessor != null && eventProcessor.isInterested(ioEvent)) {
         return eventProcessor;
      } else {
         ProcessorSelector processorSelector = connection.getProcessorSelector();
         return processorSelector != null ? processorSelector.select(ioEvent, connection) : null;
      }
   }
}
