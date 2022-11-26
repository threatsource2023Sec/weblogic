package weblogic.application.compiler.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ByteBasedXMLScanner {
   private Getter getter;
   private ByteBuffer bytes;
   private int depth = 0;
   State state;
   private static final byte lt = 60;
   private static final byte gt = 62;
   private static final byte colon = 58;
   private static final byte[] cdata = "CDATA".getBytes();
   private static final byte[] octype = "OCTYPE".getBytes();
   private int endTagCharIdx;
   private int endTagNameCharIdx;
   private int firstTagCharIdx;
   private boolean isEmpty;
   private int stopper;
   private int maxStopper;
   boolean debug;
   boolean foundMatch;
   boolean shouldSeekMatch;
   int matchDepth;
   byte[][] patterns;

   public ByteBasedXMLScanner(ByteBuffer xmlBytes, boolean debug) {
      this.state = ByteBasedXMLScanner.State.Content;
      this.stopper = 0;
      this.maxStopper = Integer.MAX_VALUE;
      this.matchDepth = 0;
      this.bytes = xmlBytes;
      this.debug = debug;
      this.getter = new ByteBufferGetter();
   }

   public ByteBasedXMLScanner(InputStream inputStream, int size) {
      this.state = ByteBasedXMLScanner.State.Content;
      this.stopper = 0;
      this.maxStopper = Integer.MAX_VALUE;
      this.matchDepth = 0;
      this.bytes = ByteBuffer.allocate(size);
      this.getter = new StreamGetter(inputStream);
   }

   public boolean seek(String... pathElements) {
      return this.seek(this.toByteArrays(pathElements));
   }

   private byte[][] toByteArrays(String[] pathElements) {
      byte[][] results = new byte[pathElements.length][];

      for(int i = 0; i < pathElements.length; ++i) {
         results[i] = pathElements[i].getBytes();
      }

      return results;
   }

   public boolean seek(byte[]... pathElements) {
      this.shouldSeekMatch = true;
      this.foundMatch = false;
      this.patterns = pathElements;
      this.firstElementStart();
      this.parseRoot();
      return this.foundMatch;
   }

   public String readText() {
      if (this.isEmpty) {
         return "";
      } else {
         int start = this.endTagCharIdx + 1;
         int end = this.lookfor('<');
         byte[] textBytes = new byte[end - start];
         this.bytes.position(start);
         this.getter.get(textBytes);
         this.bytes.position(end);
         return new String(textBytes);
      }
   }

   private void incrementDepth() {
      ++this.depth;
      if (this.debug) {
         System.out.print(" [" + this.depth + "]");
      }

   }

   private void checkEmptyMatch() {
      this.incrementDepth();
      this.checkMatch();
      this.decrementDepth();
   }

   private void checkMatch() {
      int depth1 = this.depth - 1;
      if (this.shouldSeekMatch && depth1 < this.patterns.length && depth1 == this.matchDepth) {
         boolean isMatch = true;
         byte[] match = this.patterns[depth1];
         if (match.length != this.endTagNameCharIdx - this.firstTagCharIdx) {
            isMatch = false;
         } else {
            int savedPosition = this.bytes.position();
            this.bytes.position(this.firstTagCharIdx);
            byte[] var5 = match;
            int var6 = match.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               byte b = var5[var7];
               if (b != this.getter.get()) {
                  isMatch = false;
                  break;
               }
            }

            this.bytes.position(savedPosition);
         }

         if (isMatch) {
            if (this.debug) {
               this.printTag(" partial match ( " + new String(match) + "): ");
            }

            ++this.matchDepth;
            if (this.matchDepth == this.patterns.length) {
               this.shouldSeekMatch = false;
               this.foundMatch = true;
               if (this.debug) {
                  System.out.println("found match: " + new String(match));
               }
            }
         }
      }

   }

   private void parseRoot() {
      for(int i = this.bytes.position(); this.shouldSeekMatch && this.bytes.remaining() > 0; ++i) {
         switch (this.state) {
            case Content:
               if (this.getter.get() == 60) {
                  this.state = ByteBasedXMLScanner.State.Markup;
               }
               break;
            case Markup:
               if (this.debug && this.stopper++ > this.maxStopper) {
                  return;
               }

               this.parseMarkup();
               this.state = ByteBasedXMLScanner.State.Content;
         }
      }

   }

   private void parseMarkup() {
      label64:
      switch (this.getter.get()) {
         case 33:
            switch (this.getter.get()) {
               case 45:
                  if (this.debug) {
                     System.out.print("comment");
                  }

                  this.expect(45);
                  this.endTagCharIdx = this.lookfor('-', '-', '>');
                  break label64;
               case 68:
                  if (this.debug) {
                     System.out.print("doctype");
                  }

                  this.expect(octype);
                  this.endTagCharIdx = this.lookfor('>');
                  break label64;
               case 91:
                  if (this.debug) {
                     System.out.print("cdata");
                  }

                  this.expect(cdata);
                  this.endTagCharIdx = this.lookfor(']', ']', '>');
               default:
                  break label64;
            }
         case 47:
            if (this.debug) {
               System.out.print("end");
            }

            this.expect();
            this.endTagCharIdx = this.lookfor('>');
            this.decrementDepth();
            break;
         case 63:
            if (this.debug) {
               System.out.print("?");
            }

            this.endTagCharIdx = this.lookfor('?', '>');
            break;
         default:
            if (this.debug) {
               System.out.print("tag:");
            }

            this.expect();
            --this.firstTagCharIdx;
            this.endTagCharIdx = this.endTagNameCharIdx = this.lookforAny(58, 32, 13, 9, 10, 62);
            if (this.getter.get(this.endTagCharIdx) == 58) {
               this.firstTagCharIdx = this.endTagCharIdx + 1;
               this.endTagCharIdx = this.endTagNameCharIdx = this.lookforAny(32, 13, 9, 10, 62);
            }

            if (this.getter.get(this.endTagCharIdx) != 62) {
               this.endTagCharIdx = this.lookfor('>');
            }

            if (this.getter.get(this.endTagCharIdx - 1) == 47) {
               if (this.endTagCharIdx == this.endTagNameCharIdx) {
                  --this.endTagNameCharIdx;
               }

               --this.endTagCharIdx;
               if (this.debug) {
                  System.out.print(" (empty)");
               }

               this.isEmpty = true;
               this.checkEmptyMatch();
            } else {
               this.incrementDepth();
               this.isEmpty = false;
               this.checkMatch();
            }
      }

      if (this.debug) {
         System.out.println("");
      }

   }

   private void decrementDepth() {
      --this.depth;
      if (this.matchDepth > this.depth) {
         this.matchDepth = this.depth;
      }

      if (this.depth == 0) {
         this.shouldSeekMatch = false;
         this.foundMatch = false;
      }

      if (this.debug) {
         System.out.print(" [" + this.depth + "]");
      }

   }

   private void expect(byte... bs) {
      this.firstTagCharIdx = this.bytes.position() + (bs == null ? 0 : bs.length);
      if (this.debug) {
         this.bytes.mark();
         byte[] var2 = bs;
         int var3 = bs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];

            assert b == this.getter.get();
         }

         this.bytes.reset();
      }

   }

   private int lookforAny(byte... bs) {
      while(true) {
         byte b = this.getter.get();
         byte[] var3 = bs;
         int var4 = bs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte possibleB = var3[var5];
            if (b == possibleB) {
               return this.bytes.position() - 1;
            }
         }
      }
   }

   private int lookfor(char... cs) {
      int i = 0;
      int l = cs.length;

      do {
         while(this.getter.get() != (byte)cs[i++]) {
            i = 0;
         }
      } while(i != l);

      return this.bytes.position() - l;
   }

   public void firstElementStart() {
      for(int i = this.bytes.position(); this.bytes.remaining() > 0; ++i) {
         if (this.getter.get() == 60) {
            this.bytes.mark();
            byte b = this.getter.get();
            if (b != 33 && b != 63 && b != 47) {
               this.bytes.position(i);
               return;
            }

            this.bytes.reset();
         }
      }

      throw new RuntimeException("Could not find element start");
   }

   public void firstElementStartold() {
      for(int i = this.bytes.position(); this.bytes.remaining() > 0; ++i) {
         if (this.getter.get() == 60) {
            this.bytes.mark();
            byte b = this.getter.get();
            if (b != 33 && b != 63 && b != 47) {
               this.firstTagCharIdx = this.bytes.position() - 1;

               while(this.getter.get() != 62) {
               }

               this.endTagCharIdx = this.bytes.position() - 1;
               if (this.debug) {
                  StringBuffer sb = new StringBuffer();

                  for(int j = this.firstTagCharIdx; j < this.endTagCharIdx; ++j) {
                     sb.append((char)this.getter.get(j));
                  }

                  System.out.println("Tag: " + sb);
               }

               return;
            }

            this.bytes.reset();
         }
      }

      throw new RuntimeException("Could not find element start");
   }

   private void printTag(String msg) {
      StringBuffer sb = new StringBuffer();

      for(int j = this.firstTagCharIdx; j < this.endTagCharIdx; ++j) {
         sb.append((char)this.getter.get(j));
      }

      System.out.print(msg + sb);
   }

   private class StreamGetter implements Getter {
      private InputStream is;
      private int position;

      private StreamGetter(InputStream inputStream) {
         this.position = 0;
         this.is = inputStream;
      }

      public byte get() {
         if (this.position > ByteBasedXMLScanner.this.bytes.position()) {
            return ByteBasedXMLScanner.this.bytes.get();
         } else {
            ++this.position;

            try {
               byte b = (byte)this.is.read();
               ByteBasedXMLScanner.this.bytes.put(b);
               return b;
            } catch (IOException var2) {
               throw new RuntimeException(var2);
            }
         }
      }

      public byte get(int index) {
         if (this.position > index) {
            return ByteBasedXMLScanner.this.bytes.get(index);
         } else {
            throw new AssertionError("Attempt to read for an index greater than the stream position: " + index + " / " + this.position);
         }
      }

      public ByteBuffer get(byte[] dest) {
         int maxIndex = ByteBasedXMLScanner.this.bytes.position() + dest.length;
         if (this.position > maxIndex) {
            return ByteBasedXMLScanner.this.bytes.get(dest);
         } else {
            throw new AssertionError("Attempt to read for an index greater than the stream position: " + maxIndex + " / " + this.position);
         }
      }

      // $FF: synthetic method
      StreamGetter(InputStream x1, Object x2) {
         this(x1);
      }
   }

   private class ByteBufferGetter implements Getter {
      private ByteBufferGetter() {
      }

      public byte get() {
         return ByteBasedXMLScanner.this.bytes.get();
      }

      public byte get(int index) {
         return ByteBasedXMLScanner.this.bytes.get(index);
      }

      public ByteBuffer get(byte[] dest) {
         return ByteBasedXMLScanner.this.bytes.get(dest);
      }

      // $FF: synthetic method
      ByteBufferGetter(Object x1) {
         this();
      }
   }

   private interface Getter {
      byte get();

      byte get(int var1);

      ByteBuffer get(byte[] var1);
   }

   private static enum State {
      Markup,
      Content;
   }
}
