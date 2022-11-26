package monfox.toolkit.snmp.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;

public final class TextBuffer {
   public static final int NEXT = 1;
   public static final String FORMATTED = "eZ\u0018-*wA\u000f$";
   public static final String COMPACT = "`Z\u00070*`A";
   protected Hashtable _properties = new Hashtable();
   protected int _indentSize = 3;
   protected StringBuffer _buff = null;
   protected Vector _indentStack = new Vector();
   protected int _pos = 0;
   protected int _indent = 0;
   protected boolean _useNewlines = true;
   protected PrintStream _printStream = null;
   protected PrintWriter _printWriter = null;
   protected boolean _changed = false;
   private static String[] a = new String[2048];
   private static final String b = "$Id: TextBuffer.java,v 1.4 2003/02/25 22:17:14 sking Exp $";
   // $FF: synthetic field
   static Class c;

   public TextBuffer(PrintStream var1) {
      this._buff = new StringBuffer();
      this._pos = 0;
      this._indent = 0;
      this.setProp(b("eZ\u0018-*wA\u000f$"));
      this._printStream = var1;
   }

   public TextBuffer(PrintWriter var1) {
      this._buff = new StringBuffer();
      this._pos = 0;
      this._indent = 0;
      this.setProp(b("eZ\u0018-*wA\u000f$"));
      this._printWriter = var1;
   }

   public TextBuffer() {
      this._buff = new StringBuffer();
      this._pos = 0;
      this._indent = 0;
      this.setProp(b("eZ\u0018-*wA\u000f$"));
   }

   public TextBuffer(int var1) {
      this._buff = new StringBuffer(var1);
      this._pos = 0;
      this._indent = 0;
      this.setProp(b("eZ\u0018-*wA\u000f$"));
   }

   public TextBuffer(String var1) {
      if (var1.equals(b("`Z\u00070*`A"))) {
         this._useNewlines = false;
      }

      this._buff = new StringBuffer();
      this._pos = 0;
      this._indent = 0;
      this.setProp(var1);
   }

   public TextBuffer(PrintStream var1, String var2) {
      if (var2.equals(b("`Z\u00070*`A"))) {
         this._useNewlines = false;
      }

      this._buff = new StringBuffer();
      this._pos = 0;
      this._indent = 0;
      this.setProp(var2);
      this._printStream = var1;
   }

   public TextBuffer(PrintWriter var1, String var2) {
      if (var2.equals(b("`Z\u00070*`A"))) {
         this._useNewlines = false;
      }

      this._buff = new StringBuffer();
      this._pos = 0;
      this._indent = 0;
      this.setProp(var2);
      this._printWriter = var1;
   }

   public TextBuffer pushIndent() {
      return this.pushRelative(this._indentSize);
   }

   public TextBuffer pushIndent(int var1) {
      return this.pushRelative(this._indentSize * var1);
   }

   public TextBuffer popIndent() {
      Integer var1 = (Integer)this._indentStack.elementAt(0);
      this._indent = var1;
      this._indentStack.removeElementAt(0);
      this._changed = true;
      return this;
   }

   public TextBuffer pushAbsolute(int var1) {
      this._indentStack.insertElementAt(new Integer(this._indent), 0);
      this._indent = var1;
      this._changed = true;
      return this;
   }

   public TextBuffer pushRelative(int var1) {
      return this.pushAbsolute(this._indent + var1);
   }

   public TextBuffer pushMark() {
      return this.pushAbsolute(this._pos);
   }

   public TextBuffer appendNewline() {
      if (this._useNewlines) {
         this.append((Object)"\n");
      }

      return this;
   }

   public TextBuffer append(Object var1, int var2) {
      this.pushIndent(var2).append((Object)"\n");
      this.append(var1);
      this.popIndent();
      return this;
   }

   public TextBuffer append(int var1) {
      this.append((Object)String.valueOf(var1));
      return this;
   }

   public TextBuffer append(boolean var1) {
      this.append((Object)String.valueOf(var1));
      return this;
   }

   public TextBuffer append(float var1) {
      this.append((Object)String.valueOf(var1));
      return this;
   }

   public TextBuffer append(char var1) {
      this.append((Object)String.valueOf(var1));
      return this;
   }

   public TextBuffer append(long var1) {
      this.append((Object)String.valueOf(var1));
      return this;
   }

   public TextBuffer append(double var1) {
      this.append((Object)String.valueOf(var1));
      return this;
   }

   public TextBuffer append(char[] var1) {
      this.append((Object)String.valueOf(var1));
      return this;
   }

   public boolean hasProp(String var1) {
      return this._properties.containsKey(var1);
   }

   public String getProp(String var1) {
      return this._properties.get(var1).toString();
   }

   public int getPropInt(String var1) {
      return Integer.parseInt(this._properties.get(var1).toString());
   }

   public void setProp(String var1) {
      this._properties.put(var1, new Boolean(true));
   }

   public void unsetProp(String var1) {
      this._properties.remove(var1);
   }

   public void setProp(String var1, int var2) {
      this._properties.put(var1, new Integer(var2));
   }

   public void setProp(String var1, Object var2) {
      this._properties.put(var1, var2);
   }

   public TextBuffer append(Object var1) {
      int var5 = WorkItem.d;
      if (var1 == null) {
         this.appendOrPrint(b("M`&\f"));
         return this;
      } else {
         try {
            Class[] var8 = new Class[]{c == null ? (c = a(b("Nz$\u0006\u0004[;>\u000f\u0004O~#\u0014EP{'\u0010EVa#\fEwp2\u0014)Vs,\u0005\u0019"))) : c};
            Method var9 = var1.getClass().getMethod(b("Wz\u0019\u0014\u0019J{-"), var8);
            Object[] var10 = new Object[]{this};
            var9.invoke(var1, var10);
            return this;
         } catch (Exception var6) {
            if (this._changed) {
               this.appendOrPrint('\n');
               int var2 = 0;

               do {
                  if (var2 >= this._indent) {
                     this._pos = this._indent;
                     break;
                  }

                  this.appendOrPrint(" ");
                  ++var2;
               } while(var5 == 0 || var5 == 0);

               this._changed = false;
            }

            String var7 = var1.toString();
            char[] var3 = var7.toCharArray();
            int var4 = 0;

            TextBuffer var10000;
            while(true) {
               if (var4 < var3.length) {
                  this.appendOrPrint(var3[var4]);
                  var10000 = this;
                  if (var5 != 0) {
                     break;
                  }

                  ++this._pos;
                  if (var3[var4] == '\n') {
                     this.a();
                     this._pos = this._indent;
                  }

                  ++var4;
                  if (var5 == 0) {
                     continue;
                  }
               }

               var10000 = this;
               break;
            }

            return var10000;
         }
      }
   }

   private void a() {
      int var4 = WorkItem.d;
      String var1 = null;
      if (this._indent < a.length && a[this._indent] != null) {
         var1 = a[this._indent];
      } else {
         StringBuffer var2 = new StringBuffer();
         int var3 = 0;

         StringBuffer var10000;
         label25: {
            while(var3 < this._indent) {
               var10000 = var2.append(' ');
               if (var4 != 0) {
                  break label25;
               }

               ++var3;
               if (var4 != 0) {
                  break;
               }
            }

            var10000 = var2;
         }

         var1 = var10000.toString();
         if (this._indent < a.length) {
            a[this._indent] = var1;
         }
      }

      this.appendOrPrint(var1);
   }

   protected void appendOrPrint(String var1) {
      int var2 = WorkItem.d;
      if (this._printStream != null) {
         this._printStream.print(var1);
         if (var2 == 0) {
            return;
         }
      }

      if (this._printWriter != null) {
         this._printWriter.print(var1);
         if (var2 == 0) {
            return;
         }
      }

      this._buff.append(var1);
   }

   protected void appendOrPrint(char var1) {
      int var2 = WorkItem.d;
      if (this._printStream != null) {
         this._printStream.print(var1);
         if (var2 == 0) {
            return;
         }
      }

      if (this._printWriter != null) {
         this._printWriter.print(var1);
         if (var2 == 0) {
            return;
         }
      }

      this._buff.append(var1);
   }

   public String toString() {
      return this._buff.toString();
   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 35;
               break;
            case 1:
               var10003 = 21;
               break;
            case 2:
               var10003 = 74;
               break;
            case 3:
               var10003 = 96;
               break;
            default:
               var10003 = 107;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
