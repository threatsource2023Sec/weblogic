package weblogic.protocol;

import java.io.IOException;
import weblogic.utils.io.Chunk;

public interface AsyncMessageSender {
   int QUEUE_SIZE = 32;
   int MAX_QUEUED_SEND_SIZE = Chunk.CHUNK_SIZE;
   int WS_IDLE = 0;
   int WS_WRITING = 1;
   int WS_NEED_A_BREAK = 2;
   int WS_THREAD_WAITING = 3;
   int WS_GOT_IOEXCEPTION = 4;

   void send(AsyncOutgoingMessage var1) throws IOException;
}
