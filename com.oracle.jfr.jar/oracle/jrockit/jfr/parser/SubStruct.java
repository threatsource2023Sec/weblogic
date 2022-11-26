package oracle.jrockit.jfr.parser;

class SubStruct extends AbstractStructProxy {
   private final int producerID;
   private final long timestamp;
   private final int index;

   public SubStruct(ChunkParser chunkParser, Object[] values, int producerID, long timestamp, int index) {
      super(chunkParser, values);
      this.producerID = producerID;
      this.timestamp = timestamp;
      this.index = index;
   }

   protected ProducerData producer() {
      return (ProducerData)this.chunkParser.producers.get(this.producerID);
   }

   protected long timestamp() {
      return this.timestamp;
   }

   protected ValueData[] valueData() {
      return (ValueData[])this.producer().structs.get(this.index);
   }
}
