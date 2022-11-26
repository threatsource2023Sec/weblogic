package com.bea.core.repackaged.springframework.objenesis.instantiator.basic;

import com.bea.core.repackaged.springframework.objenesis.ObjenesisException;
import com.bea.core.repackaged.springframework.objenesis.instantiator.ObjectInstantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Instantiator;
import com.bea.core.repackaged.springframework.objenesis.instantiator.annotations.Typology;
import com.bea.core.repackaged.springframework.objenesis.instantiator.util.ClassDefinitionUtils;
import com.bea.core.repackaged.springframework.objenesis.instantiator.util.ClassUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Instantiator(Typology.STANDARD)
public class ProxyingInstantiator implements ObjectInstantiator {
   private static final int INDEX_CLASS_THIS = 1;
   private static final int INDEX_CLASS_SUPERCLASS = 2;
   private static final int INDEX_UTF8_CONSTRUCTOR_NAME = 3;
   private static final int INDEX_UTF8_CONSTRUCTOR_DESC = 4;
   private static final int INDEX_UTF8_CODE_ATTRIBUTE = 5;
   private static final int INDEX_UTF8_CLASS = 7;
   private static final int INDEX_UTF8_SUPERCLASS = 8;
   private static final int CONSTANT_POOL_COUNT = 9;
   private static final byte[] CODE = new byte[]{42, -79};
   private static final int CODE_ATTRIBUTE_LENGTH;
   private static final String SUFFIX = "$$$Objenesis";
   private static final String CONSTRUCTOR_NAME = "<init>";
   private static final String CONSTRUCTOR_DESC = "()V";
   private final Class newType;

   public ProxyingInstantiator(Class type) {
      byte[] classBytes = writeExtendingClass(type);

      try {
         this.newType = ClassDefinitionUtils.defineClass(type.getName() + "$$$Objenesis", classBytes, type, type.getClassLoader());
      } catch (Exception var4) {
         throw new ObjenesisException(var4);
      }
   }

   public Object newInstance() {
      return ClassUtils.newInstance(this.newType);
   }

   private static byte[] writeExtendingClass(Class type) {
      String parentClazz = ClassUtils.classNameToInternalClassName(type.getName());
      String clazz = parentClazz + "$$$Objenesis";
      ByteArrayOutputStream bIn = new ByteArrayOutputStream(1000);

      try {
         DataOutputStream in = new DataOutputStream(bIn);
         Throwable var5 = null;

         try {
            in.write(ClassDefinitionUtils.MAGIC);
            in.write(ClassDefinitionUtils.VERSION);
            in.writeShort(9);
            in.writeByte(7);
            in.writeShort(7);
            in.writeByte(7);
            in.writeShort(8);
            in.writeByte(1);
            in.writeUTF("<init>");
            in.writeByte(1);
            in.writeUTF("()V");
            in.writeByte(1);
            in.writeUTF("Code");
            in.writeByte(1);
            in.writeUTF("L" + clazz + ";");
            in.writeByte(1);
            in.writeUTF(clazz);
            in.writeByte(1);
            in.writeUTF(parentClazz);
            in.writeShort(33);
            in.writeShort(1);
            in.writeShort(2);
            in.writeShort(0);
            in.writeShort(0);
            in.writeShort(1);
            in.writeShort(1);
            in.writeShort(3);
            in.writeShort(4);
            in.writeShort(1);
            in.writeShort(5);
            in.writeInt(CODE_ATTRIBUTE_LENGTH);
            in.writeShort(1);
            in.writeShort(1);
            in.writeInt(CODE.length);
            in.write(CODE);
            in.writeShort(0);
            in.writeShort(0);
            in.writeShort(0);
         } catch (Throwable var15) {
            var5 = var15;
            throw var15;
         } finally {
            if (in != null) {
               if (var5 != null) {
                  try {
                     in.close();
                  } catch (Throwable var14) {
                     var5.addSuppressed(var14);
                  }
               } else {
                  in.close();
               }
            }

         }
      } catch (IOException var17) {
         throw new ObjenesisException(var17);
      }

      return bIn.toByteArray();
   }

   static {
      CODE_ATTRIBUTE_LENGTH = 12 + CODE.length;
   }
}
