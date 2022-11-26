package serp.bytecode.lowlevel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class ConstantPoolTable {
   private byte[] _bytecode;
   private int[] _table;
   private int _idx;

   public ConstantPoolTable(byte[] b) {
      this._bytecode = null;
      this._table = null;
      this._idx = 0;
      this._bytecode = b;
      this._table = new int[readUnsignedShort(b, 8)];
      this._idx = parse(b, this._table);
   }

   public ConstantPoolTable(InputStream in) throws IOException {
      this(toByteArray(in));
   }

   public static int getEndIndex(byte[] b) {
      return parse(b, (int[])null);
   }

   private static int parse(byte[] b, int[] table) {
      int entries = table == null ? readUnsignedShort(b, 8) : table.length;
      int idx = 10;

      for(int i = 1; i < entries; ++i) {
         if (table != null) {
            table[i] = idx + 1;
         }

         switch (b[idx]) {
            case 1:
               idx += 3 + readUnsignedShort(b, idx + 1);
               break;
            case 2:
            case 7:
            case 8:
            case 13:
            case 14:
            case 16:
            case 17:
            default:
               idx += 3;
               break;
            case 3:
            case 4:
            case 9:
            case 10:
            case 11:
            case 12:
            case 18:
               idx += 5;
               break;
            case 5:
            case 6:
               idx += 9;
               ++i;
               break;
            case 15:
               idx += 4;
         }
      }

      return idx;
   }

   public static int readByte(byte[] b, int idx) {
      return b[idx] & 255;
   }

   public static int readUnsignedShort(byte[] b, int idx) {
      return readByte(b, idx) << 8 | readByte(b, idx + 1);
   }

   public static int readInt(byte[] b, int idx) {
      return readByte(b, idx) << 24 | readByte(b, idx + 1) << 16 | readByte(b, idx + 2) << 8 | readByte(b, idx + 3);
   }

   public static long readLong(byte[] b, int idx) {
      return (long)(readInt(b, idx) << 32 | readInt(b, idx + 4));
   }

   public static String readString(byte[] b, int idx) {
      int len = readUnsignedShort(b, idx);

      try {
         return new String(b, idx + 2, len, "UTF-8");
      } catch (UnsupportedEncodingException var4) {
         throw new ClassFormatError(var4.toString());
      }
   }

   private static byte[] toByteArray(InputStream in) throws IOException {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];

      int r;
      while((r = in.read(buf)) != -1) {
         bout.write(buf, 0, r);
      }

      return bout.toByteArray();
   }

   public int getEndIndex() {
      return this._idx;
   }

   public int get(int idx) {
      return this._table[idx];
   }

   public int readByte(int idx) {
      return readByte(this._bytecode, idx);
   }

   public int readUnsignedShort(int idx) {
      return readUnsignedShort(this._bytecode, idx);
   }

   public int readInt(int idx) {
      return readInt(this._bytecode, idx);
   }

   public long readLong(int idx) {
      return readLong(this._bytecode, idx);
   }

   public String readString(int idx) {
      return readString(this._bytecode, idx);
   }
}
