package oracle.jrockit.jfr;

public interface JFRStats {
   long bytesWritten();

   long bytesLost();

   long bytesWrittenDirectlyToDisk();

   long threadBufferBytesAllocated();

   long threadBufferBytesReleased();

   long chunksWritten();

   long threadBuffersCopied();

   long threadBuffersCopiedDirectToDisk();

   long threadBufffersLost();

   long globalBuffersCopied();
}
