package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;

public final class MessageBytes implements Cloneable, Serializable {
   private int type = 0;
   public static final int T_NULL = 0;
   public static final int T_STR = 1;
   public static final int T_BYTES = 2;
   public static final int T_CHARS = 3;
   private int hashCode = 0;
   private boolean hasHashCode = false;
   private boolean caseSensitive = true;
   private final ByteChunk byteC = new ByteChunk();
   private final CharChunk charC = new CharChunk();
   private String strValue;
   private boolean hasStrValue = false;
   private int intValue;
   private boolean hasIntValue = false;
   private long longValue;
   private boolean hasLongValue = false;
   private static MessageBytesFactory factory = new MessageBytesFactory();

   public static MessageBytes newInstance() {
      return factory.newInstance();
   }

   public void setCaseSenitive(boolean b) {
      this.caseSensitive = b;
   }

   public MessageBytes getClone() {
      try {
         return (MessageBytes)this.clone();
      } catch (Exception var2) {
         return null;
      }
   }

   public boolean isNull() {
      return this.byteC.isNull() && this.charC.isNull() && !this.hasStrValue;
   }

   public void recycle() {
      this.type = 0;
      this.byteC.recycle();
      this.charC.recycle();
      this.strValue = null;
      this.caseSensitive = true;
      this.hasStrValue = false;
      this.hasHashCode = false;
      this.hasIntValue = false;
      this.hasLongValue = false;
   }

   public void setBytes(byte[] b, int off, int len) {
      this.byteC.setBytes(b, off, len);
      this.type = 2;
      this.hasStrValue = false;
      this.hasHashCode = false;
      this.hasIntValue = false;
      this.hasLongValue = false;
   }

   public void setCharset(Charset enc) {
      if (!this.byteC.isNull()) {
         this.charC.recycle();
         this.hasStrValue = false;
      }

      this.byteC.setCharset(enc);
   }

   public void setChars(char[] c, int off, int len) {
      this.charC.setChars(c, off, len);
      this.type = 3;
      this.hasStrValue = false;
      this.hasHashCode = false;
      this.hasIntValue = false;
      this.hasLongValue = false;
   }

   public void resetStringValue() {
      if (this.type != 1) {
         this.hasStrValue = false;
         this.strValue = null;
      }

   }

   public void setString(String s) {
      this.strValue = s;
      this.hasHashCode = false;
      this.hasIntValue = false;
      this.hasLongValue = false;
      if (s == null) {
         this.hasStrValue = false;
         this.type = 0;
      } else {
         this.hasStrValue = true;
         this.type = 1;
      }

   }

   public String toString() {
      if (this.hasStrValue) {
         return this.strValue;
      } else {
         this.hasStrValue = true;
         switch (this.type) {
            case 2:
               this.strValue = this.byteC.toString();
               return this.strValue;
            case 3:
               this.strValue = this.charC.toString();
               return this.strValue;
            default:
               return "";
         }
      }
   }

   public int getType() {
      return this.type;
   }

   public ByteChunk getByteChunk() {
      return this.byteC;
   }

   public CharChunk getCharChunk() {
      return this.charC;
   }

   public String getString() {
      return this.strValue;
   }

   public void toBytes() {
      if (!this.byteC.isNull()) {
         this.type = 2;
      } else {
         this.toString();
         this.type = 2;
         byte[] bb = this.strValue.getBytes(this.byteC.getCharset());
         this.byteC.setBytes(bb, 0, bb.length);
      }
   }

   public void toChars() {
      if (!this.charC.isNull()) {
         this.type = 3;
      } else {
         this.toString();
         this.type = 3;
         char[] cc = this.strValue.toCharArray();
         this.charC.setChars(cc, 0, cc.length);
      }
   }

   public int getLength() {
      if (this.type == 2) {
         return this.byteC.getLength();
      } else if (this.type == 3) {
         return this.charC.getLength();
      } else if (this.type == 1) {
         return this.strValue.length();
      } else {
         this.toString();
         return this.strValue == null ? 0 : this.strValue.length();
      }
   }

   public boolean equals(String s) {
      if (s == null) {
         return false;
      } else if (!this.caseSensitive) {
         return this.equalsIgnoreCase(s);
      } else {
         switch (this.type) {
            case 1:
               return this.strValue != null && this.strValue.equals(s);
            case 2:
               return this.byteC.equals(s);
            case 3:
               return this.charC.equals((CharSequence)s);
            default:
               return false;
         }
      }
   }

   public boolean equalsIgnoreCase(String s) {
      if (s == null) {
         return false;
      } else {
         switch (this.type) {
            case 1:
               return this.strValue != null && this.strValue.equalsIgnoreCase(s);
            case 2:
               return this.byteC.equalsIgnoreCase(s);
            case 3:
               return this.charC.equalsIgnoreCase((CharSequence)s);
            default:
               return false;
         }
      }
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof MessageBytes) {
         MessageBytes mb = (MessageBytes)obj;
         switch (this.type) {
            case 1:
               return mb.equals(this.strValue);
            default:
               if (mb.type != 3 && mb.type != 2) {
                  return this.equals(mb.toString());
               } else if (mb.type == 3 && this.type == 3) {
                  return this.charC.equals(mb.charC);
               } else if (mb.type == 2 && this.type == 2) {
                  return this.byteC.equals(mb.byteC);
               } else if (mb.type == 3 && this.type == 2) {
                  return this.byteC.equals(mb.charC);
               } else {
                  return mb.type == 2 && this.type == 3 ? mb.byteC.equals(this.charC) : true;
               }
         }
      } else {
         return false;
      }
   }

   public boolean startsWith(String s) {
      switch (this.type) {
         case 1:
            return this.strValue.startsWith(s);
         case 2:
            return this.byteC.startsWith(s);
         case 3:
            return this.charC.startsWith(s);
         default:
            return false;
      }
   }

   public boolean startsWithIgnoreCase(String s, int pos) {
      switch (this.type) {
         case 1:
            if (this.strValue == null) {
               return false;
            } else if (this.strValue.length() < pos + s.length()) {
               return false;
            } else {
               for(int i = 0; i < s.length(); ++i) {
                  if (Ascii.toLower(s.charAt(i)) != Ascii.toLower(this.strValue.charAt(pos + i))) {
                     return false;
                  }
               }

               return true;
            }
         case 2:
            return this.byteC.startsWithIgnoreCase(s, pos);
         case 3:
            return this.charC.startsWithIgnoreCase(s, pos);
         default:
            return false;
      }
   }

   public int hashCode() {
      if (this.hasHashCode) {
         return this.hashCode;
      } else {
         int code;
         if (this.caseSensitive) {
            code = this.hash();
         } else {
            code = this.hashIgnoreCase();
         }

         this.hashCode = code;
         this.hasHashCode = true;
         return code;
      }
   }

   private int hash() {
      int code = 0;
      switch (this.type) {
         case 1:
            for(int i = 0; i < this.strValue.length(); ++i) {
               code = code * 37 + this.strValue.charAt(i);
            }

            return code;
         case 2:
            return this.byteC.hash();
         case 3:
            return this.charC.hash();
         default:
            return 0;
      }
   }

   private int hashIgnoreCase() {
      int code = 0;
      switch (this.type) {
         case 1:
            for(int i = 0; i < this.strValue.length(); ++i) {
               code = code * 37 + Ascii.toLower(this.strValue.charAt(i));
            }

            return code;
         case 2:
            return this.byteC.hashIgnoreCase();
         case 3:
            return this.charC.hashIgnoreCase();
         default:
            return 0;
      }
   }

   public int indexOf(char c) {
      return this.indexOf(c, 0);
   }

   public int indexOf(String s, int starting) {
      this.toString();
      return this.strValue.indexOf(s, starting);
   }

   public int indexOf(String s) {
      return this.indexOf(s, 0);
   }

   public int indexOfIgnoreCase(String s, int starting) {
      this.toString();
      String upper = this.strValue.toUpperCase();
      String sU = s.toUpperCase();
      return upper.indexOf(sU, starting);
   }

   public int indexOf(char c, int starting) {
      switch (this.type) {
         case 1:
            return this.strValue.indexOf(c, starting);
         case 2:
            return this.byteC.indexOf(c, starting);
         case 3:
            return this.charC.indexOf(c, starting);
         default:
            return -1;
      }
   }

   public void duplicate(MessageBytes src) throws IOException {
      this.recycle();
      switch (src.getType()) {
         case 1:
            this.type = 1;
            String sc = src.getString();
            this.setString(sc);
            break;
         case 2:
            this.type = 2;
            ByteChunk bc = src.getByteChunk();
            this.byteC.allocate(2 * bc.getLength(), -1);
            this.byteC.append(bc);
            break;
         case 3:
            this.type = 3;
            CharChunk cc = src.getCharChunk();
            this.charC.allocate(2 * cc.getLength(), -1);
            this.charC.append(cc);
      }

   }

   public void setInt(int i) {
      this.byteC.allocate(16, 32);
      int current = i;
      byte[] buf = this.byteC.getBuffer();
      int start = 0;
      int end = 0;
      if (i == 0) {
         buf[end++] = 48;
      }

      if (i < 0) {
         current = -i;
         buf[end++] = 45;
      }

      while(current > 0) {
         int digit = current % 10;
         current /= 10;
         buf[end++] = HexUtils.HEX[digit];
      }

      this.byteC.setStart(0);
      this.byteC.setEnd(end);
      --end;
      if (i < 0) {
         ++start;
      }

      while(end > start) {
         byte temp = buf[start];
         buf[start] = buf[end];
         buf[end] = temp;
         ++start;
         --end;
      }

      this.intValue = i;
      this.hasStrValue = false;
      this.hasHashCode = false;
      this.hasIntValue = true;
      this.hasLongValue = false;
      this.type = 2;
   }

   public void setLong(long l) {
      this.byteC.allocate(32, 64);
      long current = l;
      byte[] buf = this.byteC.getBuffer();
      int start = 0;
      int end = 0;
      if (l == 0L) {
         buf[end++] = 48;
      }

      if (l < 0L) {
         current = -l;
         buf[end++] = 45;
      }

      while(current > 0L) {
         int digit = (int)(current % 10L);
         current /= 10L;
         buf[end++] = HexUtils.HEX[digit];
      }

      this.byteC.setStart(0);
      this.byteC.setEnd(end);
      --end;
      if (l < 0L) {
         ++start;
      }

      while(end > start) {
         byte temp = buf[start];
         buf[start] = buf[end];
         buf[end] = temp;
         ++start;
         --end;
      }

      this.longValue = l;
      this.hasStrValue = false;
      this.hasHashCode = false;
      this.hasIntValue = false;
      this.hasLongValue = true;
      this.type = 2;
   }

   public int getInt() {
      if (this.hasIntValue) {
         return this.intValue;
      } else {
         switch (this.type) {
            case 2:
               this.intValue = this.byteC.getInt();
               break;
            default:
               this.intValue = Integer.parseInt(this.toString());
         }

         this.hasIntValue = true;
         return this.intValue;
      }
   }

   public long getLong() {
      if (this.hasLongValue) {
         return this.longValue;
      } else {
         switch (this.type) {
            case 2:
               this.longValue = this.byteC.getLong();
               break;
            default:
               this.longValue = Long.parseLong(this.toString());
         }

         this.hasLongValue = true;
         return this.longValue;
      }
   }

   public static void setFactory(MessageBytesFactory mbf) {
      factory = mbf;
   }

   public static class MessageBytesFactory {
      protected MessageBytesFactory() {
      }

      public MessageBytes newInstance() {
         return new MessageBytes();
      }
   }
}
