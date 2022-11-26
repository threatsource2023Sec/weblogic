package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.AnnotationGen;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeInvisParamAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.annotation.RuntimeVisParamAnnos;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.apache.bcel.util.ByteSequence;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class Utility {
   private static boolean wide = false;

   public static final String accessToString(int access_flags) {
      return accessToString(access_flags, false);
   }

   public static final String accessToString(int access_flags, boolean for_class) {
      StringBuffer buf = new StringBuffer();
      int p = 0;

      for(int i = 0; p < 2048; ++i) {
         p = pow2(i);
         if ((access_flags & p) != 0 && (!for_class || p != 32 && p != 512)) {
            buf.append(Constants.ACCESS_NAMES[i]).append(" ");
         }
      }

      return buf.toString().trim();
   }

   public static final String classOrInterface(int access_flags) {
      return (access_flags & 512) != 0 ? "interface" : "class";
   }

   public static final String codeToString(byte[] code, ConstantPool constant_pool, int index, int length, boolean verbose) {
      StringBuffer buf = new StringBuffer(code.length * 20);
      ByteSequence stream = new ByteSequence(code);

      try {
         int i;
         for(i = 0; i < index; ++i) {
            codeToString(stream, constant_pool, verbose);
         }

         for(i = 0; stream.available() > 0; ++i) {
            if (length < 0 || i < length) {
               String indices = fillup(stream.getIndex() + ":", 6, true, ' ');
               buf.append(indices + codeToString(stream, constant_pool, verbose) + '\n');
            }
         }

         return buf.toString();
      } catch (IOException var9) {
         System.out.println(buf.toString());
         var9.printStackTrace();
         throw new ClassFormatException("Byte code error: " + var9);
      }
   }

   public static final String codeToString(byte[] code, ConstantPool constant_pool, int index, int length) {
      return codeToString(code, constant_pool, index, length, true);
   }

   public static final String codeToString(ByteSequence bytes, ConstantPool constant_pool) throws IOException {
      return codeToString(bytes, constant_pool, true);
   }

   public static final String compactClassName(String str) {
      return compactClassName(str, true);
   }

   public static final String compactClassName(String str, String prefix, boolean chopit) {
      str = str.replace('/', '.');
      if (chopit) {
         int len = prefix.length();
         if (str.startsWith(prefix)) {
            String result = str.substring(len);
            if (result.indexOf(46) == -1) {
               str = result;
            }
         }
      }

      return str;
   }

   public static final String compactClassName(String str, boolean chopit) {
      return compactClassName(str, "java.lang.", chopit);
   }

   public static final String methodSignatureToString(String signature, String name, String access) {
      return methodSignatureToString(signature, name, access, true);
   }

   public static final String methodSignatureToString(String signature, String name, String access, boolean chopit) {
      return methodSignatureToString(signature, name, access, chopit, (LocalVariableTable)null);
   }

   public static final String methodSignatureToString(String signature, String name, String access, boolean chopit, LocalVariableTable vars) throws ClassFormatException {
      StringBuffer buf = new StringBuffer("(");
      int var_index = access.indexOf("static") >= 0 ? 0 : 1;

      String type;
      try {
         if (signature.charAt(0) != '(') {
            throw new ClassFormatException("Invalid method signature: " + signature);
         }

         int index;
         ResultHolder rh;
         for(index = 1; signature.charAt(index) != ')'; index += rh.getConsumedChars()) {
            rh = signatureToStringInternal(signature.substring(index), chopit);
            String param_type = rh.getResult();
            buf.append(param_type);
            if (vars != null) {
               LocalVariable l = vars.getLocalVariable(var_index);
               if (l != null) {
                  buf.append(" " + l.getName());
               }
            } else {
               buf.append(" arg" + var_index);
            }

            if (!"double".equals(param_type) && !"long".equals(param_type)) {
               ++var_index;
            } else {
               var_index += 2;
            }

            buf.append(", ");
         }

         ++index;
         type = signatureToString(signature.substring(index), chopit);
      } catch (StringIndexOutOfBoundsException var12) {
         throw new ClassFormatException("Invalid method signature: " + signature);
      }

      if (buf.length() > 1) {
         buf.setLength(buf.length() - 2);
      }

      buf.append(")");
      return access + (access.length() > 0 ? " " : "") + type + " " + name + buf.toString();
   }

   public static final String replace(String str, String old, String new_) {
      StringBuffer buf = new StringBuffer();

      try {
         int index = str.indexOf(old);
         if (index != -1) {
            int old_index;
            for(old_index = 0; (index = str.indexOf(old, old_index)) != -1; old_index = index + old.length()) {
               buf.append(str.substring(old_index, index));
               buf.append(new_);
            }

            buf.append(str.substring(old_index));
            str = buf.toString();
         }
      } catch (StringIndexOutOfBoundsException var7) {
         System.err.println(var7);
      }

      return str;
   }

   public static final String signatureToString(String signature) {
      return signatureToString(signature, true);
   }

   public static final String signatureToString(String signature, boolean chopit) {
      ResultHolder rh = signatureToStringInternal(signature, chopit);
      return rh.getResult();
   }

   public static final ResultHolder signatureToStringInternal(String signature, boolean chopit) {
      int processedChars = true;

      try {
         int genericStart;
         int genericEnd;
         ResultHolder rh;
         switch (signature.charAt(0)) {
            case 'B':
               return Utility.ResultHolder.BYTE;
            case 'C':
               return Utility.ResultHolder.CHAR;
            case 'D':
               return Utility.ResultHolder.DOUBLE;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
               throw new ClassFormatException("Invalid signature: `" + signature + "'");
            case 'F':
               return Utility.ResultHolder.FLOAT;
            case 'I':
               return Utility.ResultHolder.INT;
            case 'J':
               return Utility.ResultHolder.LONG;
            case 'L':
               int index = signature.indexOf(59);
               if (index < 0) {
                  throw new ClassFormatException("Invalid signature: " + signature);
               } else {
                  if (signature.length() > index + 1 && signature.charAt(index + 1) == '>') {
                     index += 2;
                  }

                  genericStart = signature.indexOf(60);
                  if (genericStart != -1) {
                     genericEnd = signature.indexOf(62);
                     rh = signatureToStringInternal(signature.substring(genericStart + 1, genericEnd), chopit);
                     StringBuffer sb = new StringBuffer();
                     sb.append(signature.substring(1, genericStart));
                     sb.append("<").append(rh.getResult()).append(">");
                     ResultHolder retval = new ResultHolder(compactClassName(sb.toString(), chopit), genericEnd + 1);
                     return retval;
                  }

                  int processedChars = index + 1;
                  ResultHolder retval = new ResultHolder(compactClassName(signature.substring(1, index), chopit), processedChars);
                  return retval;
               }
            case 'S':
               return Utility.ResultHolder.SHORT;
            case 'V':
               return Utility.ResultHolder.VOID;
            case 'Z':
               return Utility.ResultHolder.BOOLEAN;
            case '[':
               StringBuffer brackets = new StringBuffer();

               for(genericEnd = 0; signature.charAt(genericEnd) == '['; ++genericEnd) {
                  brackets.append("[]");
               }

               rh = signatureToStringInternal(signature.substring(genericEnd), chopit);
               genericStart = genericEnd + rh.getConsumedChars();
               brackets.insert(0, rh.getResult());
               return new ResultHolder(brackets.toString(), genericStart);
         }
      } catch (StringIndexOutOfBoundsException var9) {
         throw new ClassFormatException("Invalid signature: " + var9 + ":" + signature);
      }
   }

   public static final byte typeOfMethodSignature(String signature) throws ClassFormatException {
      try {
         if (signature.charAt(0) != '(') {
            throw new ClassFormatException("Invalid method signature: " + signature);
         } else {
            int index = signature.lastIndexOf(41) + 1;
            return typeOfSignature(signature.substring(index));
         }
      } catch (StringIndexOutOfBoundsException var3) {
         throw new ClassFormatException("Invalid method signature: " + signature);
      }
   }

   private static final short byteToShort(byte b) {
      return b < 0 ? (short)(256 + b) : (short)b;
   }

   public static final String toHexString(byte[] bytes) {
      StringBuffer buf = new StringBuffer();

      for(int i = 0; i < bytes.length; ++i) {
         short b = byteToShort(bytes[i]);
         String hex = Integer.toString(b, 16);
         if (b < 16) {
            buf.append('0');
         }

         buf.append(hex);
         if (i < bytes.length - 1) {
            buf.append(' ');
         }
      }

      return buf.toString();
   }

   public static final String format(int i, int length, boolean left_justify, char fill) {
      return fillup(Integer.toString(i), length, left_justify, fill);
   }

   public static final String fillup(String str, int length, boolean left_justify, char fill) {
      int len = length - str.length();
      char[] buf = new char[len < 0 ? 0 : len];

      for(int j = 0; j < buf.length; ++j) {
         buf[j] = fill;
      }

      return left_justify ? str + new String(buf) : new String(buf) + str;
   }

   public static final String convertString(String label) {
      char[] ch = label.toCharArray();
      StringBuffer buf = new StringBuffer();

      for(int i = 0; i < ch.length; ++i) {
         switch (ch[i]) {
            case '\n':
               buf.append("\\n");
               break;
            case '\r':
               buf.append("\\r");
               break;
            case '"':
               buf.append("\\\"");
               break;
            case '\'':
               buf.append("\\'");
               break;
            case '\\':
               buf.append("\\\\");
               break;
            default:
               buf.append(ch[i]);
         }
      }

      return buf.toString();
   }

   public static Collection getAnnotationAttributes(ConstantPool cp, List annotations) {
      if (annotations.size() == 0) {
         return null;
      } else {
         try {
            int countVisible = 0;
            int countInvisible = 0;
            Iterator var4 = annotations.iterator();

            while(var4.hasNext()) {
               AnnotationGen a = (AnnotationGen)var4.next();
               if (a.isRuntimeVisible()) {
                  ++countVisible;
               } else {
                  ++countInvisible;
               }
            }

            ByteArrayOutputStream rvaBytes = new ByteArrayOutputStream();
            ByteArrayOutputStream riaBytes = new ByteArrayOutputStream();
            DataOutputStream rvaDos = new DataOutputStream(rvaBytes);
            DataOutputStream riaDos = new DataOutputStream(riaBytes);
            rvaDos.writeShort(countVisible);
            riaDos.writeShort(countInvisible);
            Iterator var8 = annotations.iterator();

            while(var8.hasNext()) {
               AnnotationGen a = (AnnotationGen)var8.next();
               if (a.isRuntimeVisible()) {
                  a.dump(rvaDos);
               } else {
                  a.dump(riaDos);
               }
            }

            rvaDos.close();
            riaDos.close();
            byte[] rvaData = rvaBytes.toByteArray();
            byte[] riaData = riaBytes.toByteArray();
            int rvaIndex = -1;
            int riaIndex = -1;
            if (rvaData.length > 2) {
               rvaIndex = cp.addUtf8("RuntimeVisibleAnnotations");
            }

            if (riaData.length > 2) {
               riaIndex = cp.addUtf8("RuntimeInvisibleAnnotations");
            }

            List newAttributes = new ArrayList();
            if (rvaData.length > 2) {
               newAttributes.add(new RuntimeVisAnnos(rvaIndex, rvaData.length, rvaData, cp));
            }

            if (riaData.length > 2) {
               newAttributes.add(new RuntimeInvisAnnos(riaIndex, riaData.length, riaData, cp));
            }

            return newAttributes;
         } catch (IOException var13) {
            System.err.println("IOException whilst processing annotations");
            var13.printStackTrace();
            return null;
         }
      }
   }

   public static Attribute[] getParameterAnnotationAttributes(ConstantPool cp, List[] vec) {
      int[] visCount = new int[vec.length];
      int totalVisCount = 0;
      int[] invisCount = new int[vec.length];
      int totalInvisCount = 0;

      try {
         for(int i = 0; i < vec.length; ++i) {
            List l = vec[i];
            if (l != null) {
               Iterator var8 = l.iterator();

               while(var8.hasNext()) {
                  AnnotationGen element = (AnnotationGen)var8.next();
                  int var10002;
                  if (element.isRuntimeVisible()) {
                     var10002 = visCount[i]++;
                     ++totalVisCount;
                  } else {
                     var10002 = invisCount[i]++;
                     ++totalInvisCount;
                  }
               }
            }
         }

         ByteArrayOutputStream rvaBytes = new ByteArrayOutputStream();
         DataOutputStream rvaDos = new DataOutputStream(rvaBytes);
         rvaDos.writeByte(vec.length);

         for(int i = 0; i < vec.length; ++i) {
            rvaDos.writeShort(visCount[i]);
            if (visCount[i] > 0) {
               List l = vec[i];
               Iterator var10 = l.iterator();

               while(var10.hasNext()) {
                  AnnotationGen element = (AnnotationGen)var10.next();
                  if (element.isRuntimeVisible()) {
                     element.dump(rvaDos);
                  }
               }
            }
         }

         rvaDos.close();
         ByteArrayOutputStream riaBytes = new ByteArrayOutputStream();
         DataOutputStream riaDos = new DataOutputStream(riaBytes);
         riaDos.writeByte(vec.length);

         for(int i = 0; i < vec.length; ++i) {
            riaDos.writeShort(invisCount[i]);
            if (invisCount[i] > 0) {
               List l = vec[i];
               Iterator var12 = l.iterator();

               while(var12.hasNext()) {
                  AnnotationGen element = (AnnotationGen)var12.next();
                  if (!element.isRuntimeVisible()) {
                     element.dump(riaDos);
                  }
               }
            }
         }

         riaDos.close();
         byte[] rvaData = rvaBytes.toByteArray();
         byte[] riaData = riaBytes.toByteArray();
         int rvaIndex = -1;
         int riaIndex = -1;
         if (totalVisCount > 0) {
            rvaIndex = cp.addUtf8("RuntimeVisibleParameterAnnotations");
         }

         if (totalInvisCount > 0) {
            riaIndex = cp.addUtf8("RuntimeInvisibleParameterAnnotations");
         }

         List newAttributes = new ArrayList();
         if (totalVisCount > 0) {
            newAttributes.add(new RuntimeVisParamAnnos(rvaIndex, rvaData.length, rvaData, cp));
         }

         if (totalInvisCount > 0) {
            newAttributes.add(new RuntimeInvisParamAnnos(riaIndex, riaData.length, riaData, cp));
         }

         return (Attribute[])newAttributes.toArray(new Attribute[0]);
      } catch (IOException var15) {
         System.err.println("IOException whilst processing parameter annotations");
         var15.printStackTrace();
         return null;
      }
   }

   public static final byte typeOfSignature(String signature) throws ClassFormatException {
      try {
         switch (signature.charAt(0)) {
            case 'B':
               return 8;
            case 'C':
               return 5;
            case 'D':
               return 7;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'W':
            case 'X':
            case 'Y':
            default:
               throw new ClassFormatException("Invalid method signature: " + signature);
            case 'F':
               return 6;
            case 'I':
               return 10;
            case 'J':
               return 11;
            case 'L':
               return 14;
            case 'S':
               return 9;
            case 'V':
               return 12;
            case 'Z':
               return 4;
            case '[':
               return 13;
         }
      } catch (StringIndexOutOfBoundsException var2) {
         throw new ClassFormatException("Invalid method signature: " + signature);
      }
   }

   public static final byte typeOfSignature(char c) throws ClassFormatException {
      switch (c) {
         case 'B':
            return 8;
         case 'C':
            return 5;
         case 'D':
            return 7;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new ClassFormatException("Invalid type of signature: " + c);
         case 'F':
            return 6;
         case 'I':
            return 10;
         case 'J':
            return 11;
         case 'L':
            return 14;
         case 'S':
            return 9;
         case 'V':
            return 12;
         case 'Z':
            return 4;
         case '[':
            return 13;
      }
   }

   public static final String codeToString(ByteSequence bytes, ConstantPool constant_pool, boolean verbose) throws IOException {
      short opcode = (short)bytes.readUnsignedByte();
      int default_offset = 0;
      int no_pad_bytes = 0;
      StringBuffer buf = new StringBuffer(Constants.OPCODE_NAMES[opcode]);
      int i;
      int i;
      if (opcode == 170 || opcode == 171) {
         i = bytes.getIndex() % 4;
         no_pad_bytes = i == 0 ? 0 : 4 - i;

         for(i = 0; i < no_pad_bytes; ++i) {
            byte b = bytes.readByte();
            if (b != 0) {
               System.err.println("Warning: Padding byte != 0 in " + Constants.OPCODE_NAMES[opcode] + ":" + b);
            }
         }

         default_offset = bytes.readInt();
      }

      int offset;
      int[] jump_table;
      int vindex;
      int index;
      switch (opcode) {
         case 18:
            index = bytes.readUnsignedByte();
            buf.append("\t\t" + constant_pool.constantToString(index) + (verbose ? " (" + index + ")" : ""));
            break;
         case 19:
         case 20:
            index = bytes.readUnsignedShort();
            buf.append("\t\t" + constant_pool.constantToString(index) + (verbose ? " (" + index + ")" : ""));
            break;
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 169:
            if (wide) {
               vindex = bytes.readUnsignedShort();
               wide = false;
            } else {
               vindex = bytes.readUnsignedByte();
            }

            buf.append("\t\t%" + vindex);
            break;
         case 132:
            short constant;
            if (wide) {
               vindex = bytes.readUnsignedShort();
               constant = bytes.readShort();
               wide = false;
            } else {
               vindex = bytes.readUnsignedByte();
               constant = bytes.readByte();
            }

            buf.append("\t\t%" + vindex + "\t" + constant);
            break;
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 160:
         case 161:
         case 162:
         case 163:
         case 164:
         case 165:
         case 166:
         case 167:
         case 168:
         case 198:
         case 199:
            buf.append("\t\t#" + (bytes.getIndex() - 1 + bytes.readShort()));
            break;
         case 170:
            int low = bytes.readInt();
            int high = bytes.readInt();
            offset = bytes.getIndex() - 12 - no_pad_bytes - 1;
            default_offset += offset;
            buf.append("\tdefault = " + default_offset + ", low = " + low + ", high = " + high + "(");
            jump_table = new int[high - low + 1];

            for(i = 0; i < jump_table.length; ++i) {
               jump_table[i] = offset + bytes.readInt();
               buf.append(jump_table[i]);
               if (i < jump_table.length - 1) {
                  buf.append(", ");
               }
            }

            buf.append(")");
            break;
         case 171:
            int npairs = bytes.readInt();
            offset = bytes.getIndex() - 8 - no_pad_bytes - 1;
            int[] match = new int[npairs];
            jump_table = new int[npairs];
            default_offset += offset;
            buf.append("\tdefault = " + default_offset + ", npairs = " + npairs + " (");

            for(i = 0; i < npairs; ++i) {
               match[i] = bytes.readInt();
               jump_table[i] = offset + bytes.readInt();
               buf.append("(" + match[i] + ", " + jump_table[i] + ")");
               if (i < npairs - 1) {
                  buf.append(", ");
               }
            }

            buf.append(")");
            break;
         case 178:
         case 179:
         case 180:
         case 181:
            index = bytes.readUnsignedShort();
            buf.append("\t\t" + constant_pool.constantToString(index, (byte)9) + (verbose ? " (" + index + ")" : ""));
            break;
         case 182:
         case 183:
         case 184:
            index = bytes.readUnsignedShort();
            buf.append("\t" + constant_pool.constantToString(index) + (verbose ? " (" + index + ")" : ""));
            break;
         case 185:
            index = bytes.readUnsignedShort();
            i = bytes.readUnsignedByte();
            buf.append("\t" + constant_pool.constantToString(index) + (verbose ? " (" + index + ")\t" : "") + i + "\t" + bytes.readUnsignedByte());
            break;
         case 186:
            index = bytes.readUnsignedShort();
            bytes.readUnsignedShort();
            buf.append("\t" + constant_pool.constantToString(index) + (verbose ? " (" + index + ")" : ""));
            break;
         case 187:
         case 192:
            buf.append("\t");
         case 193:
            index = bytes.readUnsignedShort();
            buf.append("\t<" + constant_pool.constantToString(index) + ">" + (verbose ? " (" + index + ")" : ""));
            break;
         case 188:
            buf.append("\t\t<" + Constants.TYPE_NAMES[bytes.readByte()] + ">");
            break;
         case 189:
            index = bytes.readUnsignedShort();
            buf.append("\t\t<" + compactClassName(constant_pool.getConstantString(index, (byte)7), false) + ">" + (verbose ? " (" + index + ")" : ""));
            break;
         case 196:
            wide = true;
            buf.append("\t(wide)");
            break;
         case 197:
            index = bytes.readUnsignedShort();
            i = bytes.readUnsignedByte();
            buf.append("\t<" + compactClassName(constant_pool.getConstantString(index, (byte)7), false) + ">\t" + i + (verbose ? " (" + index + ")" : ""));
            break;
         case 200:
         case 201:
            buf.append("\t\t#" + (bytes.getIndex() - 1 + bytes.readInt()));
            break;
         default:
            if (Constants.iLen[opcode] - 1 > 0) {
               for(i = 0; i < Constants.TYPE_OF_OPERANDS[opcode].length; ++i) {
                  buf.append("\t\t");
                  switch (Constants.TYPE_OF_OPERANDS[opcode][i]) {
                     case 8:
                        buf.append(bytes.readByte());
                        break;
                     case 9:
                        buf.append(bytes.readShort());
                        break;
                     case 10:
                        buf.append(bytes.readInt());
                        break;
                     default:
                        System.err.println("Unreachable default case reached!");
                        System.exit(-1);
                  }
               }
            }
      }

      return buf.toString();
   }

   private static final int pow2(int n) {
      return 1 << n;
   }

   public static String toMethodSignature(Type returnType, Type[] argTypes) {
      StringBuffer buf = new StringBuffer("(");
      int length = argTypes == null ? 0 : argTypes.length;

      for(int i = 0; i < length; ++i) {
         buf.append(argTypes[i].getSignature());
      }

      buf.append(')');
      buf.append(returnType.getSignature());
      return buf.toString();
   }

   public static class ResultHolder {
      private String result;
      private int consumed;
      public static final ResultHolder BYTE = new ResultHolder("byte", 1);
      public static final ResultHolder CHAR = new ResultHolder("char", 1);
      public static final ResultHolder DOUBLE = new ResultHolder("double", 1);
      public static final ResultHolder FLOAT = new ResultHolder("float", 1);
      public static final ResultHolder INT = new ResultHolder("int", 1);
      public static final ResultHolder LONG = new ResultHolder("long", 1);
      public static final ResultHolder SHORT = new ResultHolder("short", 1);
      public static final ResultHolder BOOLEAN = new ResultHolder("boolean", 1);
      public static final ResultHolder VOID = new ResultHolder("void", 1);

      public ResultHolder(String s, int c) {
         this.result = s;
         this.consumed = c;
      }

      public String getResult() {
         return this.result;
      }

      public int getConsumedChars() {
         return this.consumed;
      }
   }
}
