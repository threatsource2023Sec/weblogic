package weblogic.jms.multicast;

import java.io.IOException;
import java.util.HashMap;
import javax.jms.Destination;
import weblogic.jms.client.JMSSession;
import weblogic.jms.common.BufferDataInputStream;
import weblogic.jms.extensions.SequenceGapException;
import weblogic.utils.io.Chunk;

public final class JMSFragmentStash {
   private static final int INVALID_FRAGMENT_NUMBER = -1;
   private final JMSSession session;
   private long currentSeqNo;
   private int lastFragNum;
   private HashMap chunkMap = null;
   private int numFragmentsReceived;
   private final Destination destination;

   JMSFragmentStash(JMSSession session, long seqNo, Destination destination) {
      this.session = session;
      this.currentSeqNo = seqNo - 1L;
      this.destination = destination;
   }

   Chunk processFragment(long seqNo, int messageSize, int fragNum, int offset, BufferDataInputStream bdisFrag, int payloadSize) throws SequenceGapException, IOException {
      if (seqNo < this.currentSeqNo) {
         return null;
      } else {
         if (seqNo > this.currentSeqNo) {
            int count = (int)(seqNo - this.currentSeqNo);
            if (count > 1) {
               this.session.onException(new SequenceGapException("Missing message(s)", this.destination, count - 1));
            }

            this.currentSeqNo = seqNo;
            this.lastFragNum = -1;
            this.chunkMap = null;
            this.numFragmentsReceived = 0;
         }

         Chunk head;
         if (this.chunkMap == null || !this.chunkMap.containsKey(offset)) {
            head = Chunk.createOneSharedChunk(bdisFrag, payloadSize);
            if (offset + payloadSize >= messageSize) {
               this.lastFragNum = fragNum;
               if (this.lastFragNum == 0) {
                  return head;
               }
            }

            if (this.chunkMap == null) {
               this.chunkMap = new HashMap();
            }

            this.chunkMap.put(offset, head);
            ++this.numFragmentsReceived;
         }

         if (this.numFragmentsReceived != this.lastFragNum + 1) {
            return null;
         } else {
            Chunk tail;
            head = tail = (Chunk)this.chunkMap.remove(0);

            for(int currentOffset = head.buf.length; this.numFragmentsReceived > 1; --this.numFragmentsReceived) {
               tail.next = (Chunk)this.chunkMap.remove(currentOffset);
               tail = tail.next;
            }

            return head;
         }
      }
   }
}
