package org.glassfish.grizzly;

public class StandaloneProcessorSelector implements ProcessorSelector {
   public static final StandaloneProcessorSelector INSTANCE = new StandaloneProcessorSelector();

   public Processor select(IOEvent ioEvent, Connection connection) {
      return null;
   }
}
