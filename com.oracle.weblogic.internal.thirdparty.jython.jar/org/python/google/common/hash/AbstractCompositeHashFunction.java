package org.python.google.common.hash;

import java.nio.charset.Charset;
import org.python.google.common.base.Preconditions;

abstract class AbstractCompositeHashFunction extends AbstractStreamingHashFunction {
   final HashFunction[] functions;
   private static final long serialVersionUID = 0L;

   AbstractCompositeHashFunction(HashFunction... functions) {
      HashFunction[] var2 = functions;
      int var3 = functions.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         HashFunction function = var2[var4];
         Preconditions.checkNotNull(function);
      }

      this.functions = functions;
   }

   abstract HashCode makeHash(Hasher[] var1);

   public Hasher newHasher() {
      final Hasher[] hashers = new Hasher[this.functions.length];

      for(int i = 0; i < hashers.length; ++i) {
         hashers[i] = this.functions[i].newHasher();
      }

      return new Hasher() {
         public Hasher putByte(byte b) {
            Hasher[] var2 = hashers;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Hasher hasher = var2[var4];
               hasher.putByte(b);
            }

            return this;
         }

         public Hasher putBytes(byte[] bytes) {
            Hasher[] var2 = hashers;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Hasher hasher = var2[var4];
               hasher.putBytes(bytes);
            }

            return this;
         }

         public Hasher putBytes(byte[] bytes, int off, int len) {
            Hasher[] var4 = hashers;
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Hasher hasher = var4[var6];
               hasher.putBytes(bytes, off, len);
            }

            return this;
         }

         public Hasher putShort(short s) {
            Hasher[] var2 = hashers;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Hasher hasher = var2[var4];
               hasher.putShort(s);
            }

            return this;
         }

         public Hasher putInt(int i) {
            Hasher[] var2 = hashers;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Hasher hasher = var2[var4];
               hasher.putInt(i);
            }

            return this;
         }

         public Hasher putLong(long l) {
            Hasher[] var3 = hashers;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Hasher hasher = var3[var5];
               hasher.putLong(l);
            }

            return this;
         }

         public Hasher putFloat(float f) {
            Hasher[] var2 = hashers;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Hasher hasher = var2[var4];
               hasher.putFloat(f);
            }

            return this;
         }

         public Hasher putDouble(double d) {
            Hasher[] var3 = hashers;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Hasher hasher = var3[var5];
               hasher.putDouble(d);
            }

            return this;
         }

         public Hasher putBoolean(boolean b) {
            Hasher[] var2 = hashers;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Hasher hasher = var2[var4];
               hasher.putBoolean(b);
            }

            return this;
         }

         public Hasher putChar(char c) {
            Hasher[] var2 = hashers;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Hasher hasher = var2[var4];
               hasher.putChar(c);
            }

            return this;
         }

         public Hasher putUnencodedChars(CharSequence chars) {
            Hasher[] var2 = hashers;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               Hasher hasher = var2[var4];
               hasher.putUnencodedChars(chars);
            }

            return this;
         }

         public Hasher putString(CharSequence chars, Charset charset) {
            Hasher[] var3 = hashers;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Hasher hasher = var3[var5];
               hasher.putString(chars, charset);
            }

            return this;
         }

         public Hasher putObject(Object instance, Funnel funnel) {
            Hasher[] var3 = hashers;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Hasher hasher = var3[var5];
               hasher.putObject(instance, funnel);
            }

            return this;
         }

         public HashCode hash() {
            return AbstractCompositeHashFunction.this.makeHash(hashers);
         }
      };
   }
}
