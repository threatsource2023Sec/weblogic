package oracle.jrockit.jfr;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentHashMap;

public class StringConstantPool {
   private final ConcurrentHashMap mappings = new ConcurrentHashMap();
   private final ByteBuffer data;
   private final JFR jfr;
   private final DataOutputStream out;
   private final int constantIndex;
   private final boolean emptyOnRotation;
   private volatile boolean enabled = false;
   private static final int POSITION = 0;
   private static final int OFFSET = 4;
   private static final int DATA = 8;

   public StringConstantPool(JFR jfr, int constantIndex, int maxSize, boolean emptyOnRoation) {
      this.jfr = jfr;
      this.constantIndex = constantIndex;
      this.emptyOnRotation = emptyOnRoation;
      if (maxSize == 0) {
         maxSize = 2097152;
      }

      this.data = ByteBuffer.allocateDirect(maxSize);
      this.data.putInt(0, 0);
      this.data.putInt(4, 1);
      this.out = new DataOutputStream(new OutputStream() {
         public void write(byte[] b, int off, int len) throws IOException {
            if (len > StringConstantPool.this.data.capacity() - 12) {
               throw new OutOfMemoryError("Too large string");
            } else {
               int pos;
               int next;
               int newPos;
               do {
                  pos = StringConstantPool.this.data.getInt(0);
                  next = StringConstantPool.this.data.getInt(pos);
                  if (pos == 0) {
                     StringConstantPool.this.data.position(8);
                     next = StringConstantPool.this.data.getInt(4);
                  } else {
                     StringConstantPool.this.data.position(pos);
                  }

                  StringConstantPool.this.data.putInt(next);
                  StringConstantPool.this.data.put(b, off, len);
                  newPos = StringConstantPool.this.data.position();
               } while(StringConstantPool.this.data.getInt(0) != pos);

               StringConstantPool.this.data.putInt(next + 1);
               StringConstantPool.this.data.putInt(0, newPos);
            }
         }

         public void write(int b) throws IOException {
         }
      });
   }

   public synchronized void enable() {
      if (!this.enabled) {
         this.enabled = true;
         this.jfr.addConstpool(this);
      }

   }

   public synchronized void disable() {
      if (this.enabled) {
         this.enabled = false;
         this.jfr.removeConstpool(this);
      }

   }

   public ByteBuffer getConstantData() {
      return this.data;
   }

   public int getConstantIndex() {
      return this.constantIndex;
   }

   public boolean emptyOnRotation() {
      return this.emptyOnRotation;
   }

   public int asConstant(String s) {
      if (s == null) {
         return 0;
      } else {
         while(true) {
            int pos = this.data.getInt(0);
            Integer i;
            if (pos > 0) {
               i = (Integer)this.mappings.get(s);
               if (i != null) {
                  return i;
               }
            }

            synchronized(this.data) {
               pos = this.data.getInt(0);
               if (pos == 0) {
                  this.mappings.clear();
               } else {
                  i = (Integer)this.mappings.get(s);
                  if (i != null) {
                     return i;
                  }
               }

               try {
                  this.out.writeUTF(s);
                  pos = this.data.getInt(0);
                  if (pos != 0) {
                     int res = this.data.getInt(pos) - 1;

                     assert res != 0;

                     this.mappings.put(s, res);
                     int var10000 = res;
                     return var10000;
                  }
               } catch (BufferOverflowException var7) {
                  this.jfr.storeConstpool(this);
               } catch (IOException var8) {
                  throw new InternalError();
               }
            }
         }
      }
   }

   protected void finalize() throws Throwable {
      this.disable();
   }
}
