package weblogic.jms.dotnet.transport.t3client;

import java.io.IOException;
import java.io.InputStream;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedDataInputStream;

public class T3Message {
   private T3Header hdr;
   private T3Abbrev[] abbrevs;
   private MarshalReaderImpl payload;
   private byte[] abbrevAsByte;
   private static final boolean DEBUG_READ_ABBREVS = false;

   private T3Message() {
   }

   T3Message(T3Header hdr, T3Abbrev[] abbrevs) {
      this.hdr = hdr;
      this.abbrevs = abbrevs;
   }

   void cleanup() {
      if (this.payload != null) {
         this.payload.internalClose();
      }

   }

   T3Header getMessageHeader() {
      return this.hdr;
   }

   MarshalReaderImpl getPayload() {
      return this.payload;
   }

   T3Abbrev[] getAbbrevs() {
      return this.abbrevs;
   }

   int getBodyLength() {
      return this.hdr.getOffset() - T3.PROTOCOL_HDR_SIZE;
   }

   void write(MarshalWriterImpl mwi) throws Exception {
      int abbrevCount = this.abbrevs != null ? this.abbrevs.length : 0;
      int abbrevSize = 0;
      int i;
      if (this.abbrevs != null) {
         abbrevSize = T3.getLengthNumBytes(this.abbrevs.length);
         T3Abbrev[] var4 = this.abbrevs;
         i = var4.length;

         for(int var6 = 0; var6 < i; ++var6) {
            T3Abbrev abb = var4[var6];
            abbrevSize += abb.size();
         }
      }

      this.hdr.setMessageLength(mwi.getPosition() + abbrevSize);
      this.hdr.setOffset(mwi.getPosition());
      int oldPos = mwi.getPosition();
      mwi.setPosition(0);
      this.hdr.write(mwi);
      mwi.setPosition(oldPos);
      T3.writeLength(mwi, abbrevCount);
      if (this.abbrevs != null) {
         for(i = 0; i < this.abbrevs.length; ++i) {
            this.abbrevs[i].write(mwi);
         }
      }

   }

   static T3Message readT3Message(InputStream input, Transport transport) throws Exception {
      T3Message msg = new T3Message();
      Chunk head = Chunk.getChunk();
      head.end = T3.PROTOCOL_HDR_SIZE;

      for(int mypos = 0; mypos < T3.PROTOCOL_HDR_SIZE; mypos += input.read(head.buf, mypos, T3.PROTOCOL_HDR_SIZE - mypos)) {
      }

      ChunkedDataInputStream cdis = new ChunkedDataInputStream(head, 0);
      cdis.mark(0);
      MarshalReaderImpl mri = new MarshalReaderImpl(cdis, transport);
      msg.hdr = new T3Header(mri);
      int len = msg.hdr.getMessageLength();
      len -= T3.PROTOCOL_HDR_SIZE;
      if (len != Chunk.chunk(head, input, len)) {
         throw new IOException("EOF reached");
      } else {
         cdis.reset();
         cdis.skipBytes(T3.PROTOCOL_HDR_SIZE);
         msg.payload = mri;
         return msg;
      }
   }

   void print() {
      System.out.println(this.hdr);
      System.out.println("{  ***** abbrev *****");
      int i;
      if (this.abbrevAsByte != null) {
         for(i = 1; i <= this.abbrevAsByte.length; ++i) {
            System.out.print("" + this.abbrevAsByte[i - 1] + " ");
            if (i % 10 == 0) {
               System.out.print("\n");
            }
         }

         System.out.println("");
      } else if (this.abbrevs != null) {
         for(i = 0; i < this.abbrevs.length; ++i) {
            System.out.println("id: " + this.abbrevs[i].getId());
            byte[] content = this.abbrevs[i].getContent();
            if (content != null && content.length != 0) {
               for(int j = 1; j <= content.length; ++j) {
                  System.out.print("" + content[j - 1] + " ");
                  if (j % 10 == 0) {
                     System.out.print("\n");
                  }
               }

               System.out.println("");
            }
         }

         System.out.println("");
      }

      System.out.println("}\n");
   }

   void printAbbrevs() throws Exception {
   }

   private static byte readByte(InputStream in) throws IOException {
      int ch = in.read();
      if (ch < 0) {
         throw new IOException("EOF");
      } else {
         return (byte)ch;
      }
   }

   private static int readLength(InputStream input) throws Exception {
      byte d = readByte(input);
      int res = d & 255;
      if (res < 254) {
         return res;
      } else {
         int ch1;
         int ch2;
         if (res == 254) {
            ch1 = readByte(input) & 255;
            ch2 = readByte(input) & 255;
            return (ch1 << 8) + ch2;
         } else {
            ch1 = readByte(input) & 255;
            ch2 = readByte(input) & 255;
            int ch3 = readByte(input) & 255;
            int ch4 = readByte(input) & 255;
            return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0);
         }
      }
   }
}
