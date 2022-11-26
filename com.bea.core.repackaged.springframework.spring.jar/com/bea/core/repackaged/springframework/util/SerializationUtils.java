package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class SerializationUtils {
   @Nullable
   public static byte[] serialize(@Nullable Object object) {
      if (object == null) {
         return null;
      } else {
         ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);

         try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            Throwable var3 = null;

            try {
               oos.writeObject(object);
               oos.flush();
            } catch (Throwable var13) {
               var3 = var13;
               throw var13;
            } finally {
               if (oos != null) {
                  if (var3 != null) {
                     try {
                        oos.close();
                     } catch (Throwable var12) {
                        var3.addSuppressed(var12);
                     }
                  } else {
                     oos.close();
                  }
               }

            }
         } catch (IOException var15) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), var15);
         }

         return baos.toByteArray();
      }
   }

   @Nullable
   public static Object deserialize(@Nullable byte[] bytes) {
      if (bytes == null) {
         return null;
      } else {
         try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Throwable var2 = null;

            Object var3;
            try {
               var3 = ois.readObject();
            } catch (Throwable var14) {
               var2 = var14;
               throw var14;
            } finally {
               if (ois != null) {
                  if (var2 != null) {
                     try {
                        ois.close();
                     } catch (Throwable var13) {
                        var2.addSuppressed(var13);
                     }
                  } else {
                     ois.close();
                  }
               }

            }

            return var3;
         } catch (IOException var16) {
            throw new IllegalArgumentException("Failed to deserialize object", var16);
         } catch (ClassNotFoundException var17) {
            throw new IllegalStateException("Failed to deserialize object type", var17);
         }
      }
   }
}
