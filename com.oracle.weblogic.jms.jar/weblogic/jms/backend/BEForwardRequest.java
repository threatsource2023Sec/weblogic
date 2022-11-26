package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.SecureRandom;
import java.util.zip.Adler32;
import java.util.zip.Checksum;
import javax.jms.JMSException;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSProducerSendResponse;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.utils.Hex;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedInputStream;
import weblogic.utils.io.ChunkedObjectInputStream;
import weblogic.utils.io.ChunkedObjectOutputStream;
import weblogic.utils.io.ChunkedOutputStream;

public final class BEForwardRequest extends Request implements Externalizable {
   static final long serialVersionUID = -3794283731017311093L;
   private static final int EXTVERSION = 1;
   private static final int EXTVERSION2 = 2;
   private static final int VERSION_MASK = 255;
   private BEProducerSendRequest[] requests;
   private int size;
   private transient int position;
   private byte[] signature;
   private transient byte[] signedData;
   private int securityMode;
   private byte[] signatureSecret;
   private long signedChecksum;
   private long calculatedChecksum;
   private static long requestNum = (new SecureRandom()).nextLong();
   String debugStr = null;

   public BEForwardRequest(JMSID destinationId, BEProducerSendRequest[] requests, byte[] signatureSecret) {
      super(destinationId, 17684);
      this.requests = requests;
      this.size = requests.length;
      this.signatureSecret = signatureSecret;
   }

   BEProducerSendRequest[] getRequests() {
      return this.requests;
   }

   BEProducerSendRequest getCurrentRequest() {
      return this.requests[this.position];
   }

   int getSize() {
      return this.size;
   }

   int getPosition() {
      return this.position;
   }

   void incrementPosition() {
      ++this.position;
   }

   public int remoteSignature() {
      return 35;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSProducerSendResponse();
   }

   void setSecurityMode(int mode) {
      this.securityMode = mode;
   }

   int getSecurityMode() {
      return this.securityMode;
   }

   boolean verify(byte[] signatureSecret) {
      if (signatureSecret != null && this.signature != null && this.signedData != null) {
         boolean sigOK = JMSServerUtilities.verifySignature(this.signature, this.signedData, signatureSecret);
         boolean ckOK = this.signedChecksum == this.calculatedChecksum;
         if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
            System.out.println("verify sig=" + sigOK);
            System.out.println("verify cksum=" + ckOK);
            this.dump(this.signedData, this.signature, this.signedChecksum, this.calculatedChecksum);
         }

         return sigOK && ckOK;
      } else {
         return false;
      }
   }

   public BEForwardRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      this.debugStr = null;
      int version = false;
      byte version;
      switch (this.securityMode) {
         case 11:
         case 12:
         case 13:
            version = 2;
            break;
         case 14:
            version = 1;
            break;
         default:
            throw new AssertionError();
      }

      out.writeInt(version);
      ChunkedObjectOutputStream coos = (ChunkedObjectOutputStream)out;
      int coosStart = coos.getPosition();
      if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
         this.debugStr = this.debugStr + "\n BEForwardingConsumer.DD_FORWARDING_DEBUG writeExternal: coosStart = " + coosStart;
      }

      ChunkListReader clr = new ChunkListReader(coos);
      super.writeExternal(coos);
      coos.writeInt(this.size);
      if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
         this.debugStr = this.debugStr + "\n writeExternal: before requests position = " + coos.getPosition();
      }

      int len;
      for(len = 0; len < this.size; ++len) {
         this.requests[len].writeExternal(coos);
         if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
            this.debugStr = this.debugStr + "\n writeExternal: position(" + len + ") = " + coos.getPosition();
         }
      }

      if (version == 2) {
         coos.writeInt(this.securityMode);
         if (this.securityMode != 12) {
            synchronized(this.getClass()) {
               coos.writeLong((long)(requestNum++));
            }

            if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
               this.debugStr = this.debugStr + "\n writeExternal: security mode = " + this.securityMode + " requestNum =" + requestNum + " position = " + coos.getPosition();
            }

            len = coos.getPosition() - coosStart;
            this.calculatedChecksum = clr.getChecksum((long)len);
            this.signedChecksum = this.calculatedChecksum;
            coos.writeLong(this.calculatedChecksum);
            len += 8;
            if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
               this.debugStr = this.debugStr + "\n writeExternal: len = " + len + " checksum =" + this.calculatedChecksum + " position = " + coos.getPosition();
            }

            byte[] signedData = clr.getSignedData(len, this.securityMode);
            byte[] signature = JMSServerUtilities.digest(signedData, this.signatureSecret);
            coos.writeInt(signature.length);
            coos.write(signature);
            if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
               this.debugStr = this.debugStr + "\n writeExternal: after signature position = " + coos.getPosition();
               System.out.println(this.debugStr + "\nwriteExternal() len = " + len + " securityMode = " + this.securityMode + " version = " + version);
               this.dump(signedData, signature, this.signedChecksum, this.calculatedChecksum);
            }

         }
      }
   }

   public void readExternal(ObjectInput originalIStream) throws IOException, ClassNotFoundException {
      this.debugStr = null;
      int mask = originalIStream.readInt();
      int version = mask & 255;
      if (version != 1 && version != 2) {
         throw JMSUtilities.versionIOException(version, 1, 2);
      } else {
         ChunkedObjectInputStream cois = (ChunkedObjectInputStream)originalIStream;
         int coisStart = cois.pos();
         ChunkListReader clr = new ChunkListReader(cois);
         if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
            this.debugStr = this.debugStr + "\n BEForwardingConsumer.DD_FORWARDING_DEBUG readExternal: coisStart = " + coisStart;
         }

         boolean freeChunksLater = false;
         if (!cois.isMarked() && version != 1) {
            cois.mark(Integer.MAX_VALUE);
            freeChunksLater = true;
         }

         super.readExternal(cois);
         this.size = cois.readInt();
         this.requests = new BEProducerSendRequest[this.size];
         if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
            this.debugStr = this.debugStr + "\n readExternal: before requests position = " + cois.pos();
         }

         for(int i = 0; i < this.size; ++i) {
            this.requests[i] = new BEProducerSendRequest();
            this.requests[i].readExternal(cois);
            if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
               this.debugStr = this.debugStr + "\n readExternal: position(" + i + ") = " + cois.pos();
            }
         }

         this.securityMode = 14;
         if (version == 2) {
            this.securityMode = cois.readInt();
            if (this.securityMode == 15) {
               this.securityMode = 14;
            }

            int len;
            if (this.securityMode != 12) {
               long requestNum = cois.readLong();
               if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
                  this.debugStr = this.debugStr + "\n readExternal: security mode = " + this.securityMode + " requestNum =" + requestNum + " position = " + cois.pos();
               }

               len = cois.pos() - coisStart;
               if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
                  try {
                     String s = (String)this.requests[0].getMessage().getObjectProperty("CORRUPT");
                     if (s != null) {
                        System.out.println("  **** INSTR CORRUPT " + s);
                        if (s.equals("first")) {
                           clr.corrupt(0);
                        } else if (s.equals("middle")) {
                           clr.corrupt(len / 2);
                        } else if (s.equals("last")) {
                           clr.corrupt(len + 4);
                        }
                     }
                  } catch (JMSException var14) {
                  }
               }

               this.calculatedChecksum = clr.getChecksum((long)len);
               this.signedChecksum = cois.readLong();
               len += 8;
               if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
                  this.debugStr = this.debugStr + "\n readExternal: len = " + len + " calculatedChecksum =" + this.calculatedChecksum + " position = " + cois.pos();
               }

               this.signedData = clr.getSignedData(len, this.securityMode);
               int signLen = cois.readInt();
               this.signature = new byte[signLen];
               cois.readFully(this.signature);
               if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
                  this.debugStr = this.debugStr + "\n readExternal: after signature position = " + cois.pos();
                  System.out.println(this.debugStr);

                  try {
                     String s = (String)this.requests[0].getMessage().getObjectProperty("CORRUPT");
                     if (s != null && s.equals("sig")) {
                        System.out.println("explicit test increments sig[0] on request=" + requestNum);
                        ++this.signature[0];
                     }
                  } catch (JMSException var13) {
                  }
               }
            }

            if (freeChunksLater) {
               len = cois.pos() - coisStart;
               cois.reset();
               cois.skip((long)len);
            }

         }
      }
   }

   private void dump(byte[] signedData, byte[] signature, long signedChecksum, long calcChecksum) {
      System.out.println("\nsd=\n" + Hex.asHex(signedData) + "\nsg=\n" + Hex.asHex(signature) + "\nck_signed=" + signedChecksum + "\nck_calc=" + calcChecksum);
   }

   private class ChunkListReader {
      private Chunk head;
      private Chunk headMark;
      private int chunkPos;
      private int chunkPosMark;
      private static final int READ = 1;
      private static final int SKIP = 2;
      private static final int CHECKSUM = 3;
      private static final int CORRUPT = 4;

      ChunkListReader(ChunkedInputStream cis) {
         this.head = this.headMark = cis.getChunks();
         this.chunkPos = this.chunkPosMark = cis.getChunkPos();
      }

      ChunkListReader(ChunkedOutputStream cos) {
         this.head = this.headMark = cos.getCurrentChunk();
         this.chunkPos = this.chunkPosMark = cos.getChunkPos();
      }

      long getChecksum(long len) {
         Adler32 cs = new Adler32();
         long l = this.checksum(cs, len);
         this.reset();
         if (l != len) {
            throw new AssertionError(l + "," + len);
         } else {
            return cs.getValue();
         }
      }

      byte[] getSignedData(int len, int secPolicy) {
         if (secPolicy == 13) {
            BEForwardRequest.this.signedData = new byte[len];
            this.read(BEForwardRequest.this.signedData, 0, len);
            this.reset();
            return BEForwardRequest.this.signedData;
         } else {
            StringBuilder var10000;
            BEForwardRequest var10002;
            if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
               var10000 = new StringBuilder();
               var10002 = BEForwardRequest.this;
               var10002.debugStr = var10000.append(var10002.debugStr).append("\n getSignedData: len = ").append(len).toString();
            }

            int SIGNSIZE = 128;
            BEForwardRequest.this.signedData = new byte[SIGNSIZE * 2];
            this.read(BEForwardRequest.this.signedData, 0, Math.min(SIGNSIZE, len));
            this.reset();
            if (len > SIGNSIZE) {
               this.skip((long)(len - SIGNSIZE));
            }

            if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
               var10000 = new StringBuilder();
               var10002 = BEForwardRequest.this;
               var10002.debugStr = var10000.append(var10002.debugStr).append("\n getSignedData: len = ").append(len).toString();
            }

            this.read(BEForwardRequest.this.signedData, SIGNSIZE, Math.min(SIGNSIZE, len));
            this.reset();
            return BEForwardRequest.this.signedData;
         }
      }

      void corrupt(int off) {
         this.corruptInternal((long)off);
         this.reset();
      }

      public void reset() {
         this.head = this.headMark;
         this.chunkPos = this.chunkPosMark;
      }

      private int read(byte[] buf, int off, int len) {
         return (int)this.scanOp(buf, off, (long)len, (Checksum)null, 1);
      }

      private long checksum(Checksum cs, long len) {
         return this.scanOp((byte[])null, -1, len, cs, 3);
      }

      private long skip(long skip) {
         return (long)((int)this.scanOp((byte[])null, -1, skip, (Checksum)null, 2));
      }

      private long corruptInternal(long pos) {
         return this.scanOp((byte[])null, -1, pos + 1L, (Checksum)null, 4);
      }

      private long scanOp(byte[] buf, int off, long len, Checksum cs, int mode) {
         StringBuilder var10000;
         BEForwardRequest var10002;
         if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
            var10000 = new StringBuilder();
            var10002 = BEForwardRequest.this;
            var10002.debugStr = var10000.append(var10002.debugStr).append("\n scanOp: mode = ").append(mode).append(" len = ").append(len).toString();
         }

         long requested;
         int copyLen;
         for(requested = len; len > 0L; len -= (long)copyLen) {
            if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
               var10000 = new StringBuilder();
               var10002 = BEForwardRequest.this;
               var10002.debugStr = var10000.append(var10002.debugStr).append("\n scanOp: chunkPos = ").append(this.chunkPos).append(" head.end = ").append(this.head.end).append(" head.next is null? ").append(this.head.next == null).append(" len = ").append(len).toString();
            }

            if (this.chunkPos == this.head.end && this.head.next != null) {
               this.chunkPos = 0;
               this.head = this.head.next;
            }

            copyLen = (int)Math.min((long)(this.head.end - this.chunkPos), len);
            if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
               var10000 = new StringBuilder();
               var10002 = BEForwardRequest.this;
               var10002.debugStr = var10000.append(var10002.debugStr).append("\n scanOp: copyLen = ").append(copyLen).append(" head.end = ").append(this.head.end).append(" head.next is null? ").append(this.head.next == null).append(" len = ").append(len).toString();
            }

            if (this.head.next == null && (long)copyLen < len) {
               copyLen = (int)len;
            }

            if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
               var10000 = new StringBuilder();
               var10002 = BEForwardRequest.this;
               var10002.debugStr = var10000.append(var10002.debugStr).append("\n scanOp: real copyLen = ").append(copyLen).toString();
            }

            switch (mode) {
               case 1:
                  System.arraycopy(this.head.buf, this.chunkPos, buf, off, copyLen);
               case 2:
               default:
                  break;
               case 3:
                  cs.update(this.head.buf, this.chunkPos, copyLen);
                  break;
               case 4:
                  ++this.head.buf[this.chunkPos + copyLen - 1];
            }

            this.chunkPos += copyLen;
            off += copyLen;
         }

         if (BEForwardingConsumer.DD_FORWARDING_DEBUG) {
            var10000 = new StringBuilder();
            var10002 = BEForwardRequest.this;
            var10002.debugStr = var10000.append(var10002.debugStr).append("\n scanOp: chunkPos = ").append(this.chunkPos).toString();
         }

         return requested;
      }
   }
}
