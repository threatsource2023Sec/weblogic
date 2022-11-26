package antlr;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PrintWriterWithSMAP extends PrintWriter {
   private int currentOutputLine = 1;
   private int currentSourceLine = 0;
   private Map sourceMap = new HashMap();
   private boolean lastPrintCharacterWasCR = false;
   private boolean mapLines = false;
   private boolean mapSingleSourceLine = false;
   private boolean anythingWrittenSinceMapping = false;

   public PrintWriterWithSMAP(OutputStream var1) {
      super(var1);
   }

   public PrintWriterWithSMAP(OutputStream var1, boolean var2) {
      super(var1, var2);
   }

   public PrintWriterWithSMAP(Writer var1) {
      super(var1);
   }

   public PrintWriterWithSMAP(Writer var1, boolean var2) {
      super(var1, var2);
   }

   public void startMapping(int var1) {
      this.mapLines = true;
      if (var1 != -888) {
         this.currentSourceLine = var1;
      }

   }

   public void startSingleSourceLineMapping(int var1) {
      this.mapSingleSourceLine = true;
      this.mapLines = true;
      if (var1 != -888) {
         this.currentSourceLine = var1;
      }

   }

   public void endMapping() {
      this.mapLine(false);
      this.mapLines = false;
      this.mapSingleSourceLine = false;
   }

   protected void mapLine(boolean var1) {
      if (this.mapLines && this.anythingWrittenSinceMapping) {
         Integer var2 = new Integer(this.currentSourceLine);
         Integer var3 = new Integer(this.currentOutputLine);
         Object var4 = (List)this.sourceMap.get(var2);
         if (var4 == null) {
            var4 = new ArrayList();
            this.sourceMap.put(var2, var4);
         }

         if (!((List)var4).contains(var3)) {
            ((List)var4).add(var3);
         }
      }

      if (var1) {
         ++this.currentOutputLine;
      }

      if (!this.mapSingleSourceLine) {
         ++this.currentSourceLine;
      }

      this.anythingWrittenSinceMapping = false;
   }

   public void dump(PrintWriter var1, String var2, String var3) {
      var1.println("SMAP");
      var1.println(var2 + ".java");
      var1.println("G");
      var1.println("*S G");
      var1.println("*F");
      var1.println("+ 0 " + var3);
      var1.println(var3);
      var1.println("*L");
      ArrayList var4 = new ArrayList(this.sourceMap.keySet());
      Collections.sort(var4);
      Iterator var5 = var4.iterator();

      while(var5.hasNext()) {
         Integer var6 = (Integer)var5.next();
         List var7 = (List)this.sourceMap.get(var6);
         Iterator var8 = var7.iterator();

         while(var8.hasNext()) {
            Integer var9 = (Integer)var8.next();
            var1.println(var6 + ":" + var9);
         }
      }

      var1.println("*E");
      var1.close();
   }

   public void write(char[] var1, int var2, int var3) {
      int var4 = var2 + var3;

      for(int var5 = var2; var5 < var4; ++var5) {
         this.checkChar(var1[var5]);
      }

      super.write(var1, var2, var3);
   }

   public void checkChar(int var1) {
      if (this.lastPrintCharacterWasCR && var1 != 10) {
         this.mapLine(true);
      } else if (var1 == 10) {
         this.mapLine(true);
      } else if (!Character.isWhitespace((char)var1)) {
         this.anythingWrittenSinceMapping = true;
      }

      this.lastPrintCharacterWasCR = var1 == 13;
   }

   public void write(int var1) {
      this.checkChar(var1);
      super.write(var1);
   }

   public void write(String var1, int var2, int var3) {
      int var4 = var2 + var3;

      for(int var5 = var2; var5 < var4; ++var5) {
         this.checkChar(var1.charAt(var5));
      }

      super.write(var1, var2, var3);
   }

   public void println() {
      this.mapLine(true);
      super.println();
      this.lastPrintCharacterWasCR = false;
   }

   public Map getSourceMap() {
      return this.sourceMap;
   }

   public int getCurrentOutputLine() {
      return this.currentOutputLine;
   }
}
