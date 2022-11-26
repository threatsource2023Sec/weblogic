package monfox.jdom;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class ProcessingInstruction implements Serializable, Cloneable {
   protected String target;
   protected String rawData;
   protected Map mapData;
   protected Element parent;
   protected Document document;

   protected ProcessingInstruction() {
   }

   public ProcessingInstruction(String var1, Map var2) {
      String var3;
      if ((var3 = Verifier.checkProcessingInstructionTarget(var1)) != null) {
         throw new IllegalTargetException(var1, var3);
      } else {
         this.target = var1;
         this.setData(var2);
      }
   }

   public ProcessingInstruction(String var1, String var2) {
      String var3;
      if ((var3 = Verifier.checkProcessingInstructionTarget(var1)) != null) {
         throw new IllegalTargetException(var1, var3);
      } else {
         this.target = var1;
         this.setData(var2);
      }
   }

   public Element getParent() {
      return this.parent;
   }

   protected ProcessingInstruction setParent(Element var1) {
      this.parent = var1;
      return this;
   }

   public Document getDocument() {
      if (this.document != null) {
         return this.document;
      } else {
         Element var1 = this.getParent();
         return var1 != null ? var1.getDocument() : null;
      }
   }

   protected ProcessingInstruction setDocument(Document var1) {
      this.document = var1;
      return this;
   }

   public String getTarget() {
      return this.target;
   }

   public String getData() {
      return this.rawData;
   }

   public ProcessingInstruction setData(String var1) {
      this.rawData = var1;
      this.mapData = this.a(var1);
      return this;
   }

   public ProcessingInstruction setData(Map var1) {
      this.rawData = this.a(var1);
      this.mapData = var1;
      return this;
   }

   public String getValue(String var1) {
      return this.mapData.containsKey(var1) ? (String)this.mapData.get(var1) : "";
   }

   public ProcessingInstruction setValue(String var1, String var2) {
      this.mapData.put(var1, var2);
      this.rawData = this.a(this.mapData);
      return this;
   }

   public boolean removeValue(String var1) {
      if (this.mapData.remove(var1) != null) {
         this.rawData = this.a(this.mapData);
         return true;
      } else {
         return false;
      }
   }

   private String a(Map var1) {
      boolean var6 = Element.b;
      StringBuffer var2 = new StringBuffer();
      Iterator var3 = var1.keySet().iterator();

      while(true) {
         if (var3.hasNext()) {
            String var4 = (String)var3.next();
            String var5 = (String)var1.get(var4);
            var2.append(var4).append(b("\u0006\n")).append(var5).append(b("\u0019\b"));
            if (var6) {
               break;
            }

            if (!var6) {
               continue;
            }
         }

         var2.setLength(var2.length() - 1);
         break;
      }

      return var2.toString();
   }

   private Map a(String var1) {
      HashMap var2 = new HashMap();
      StringTokenizer var3 = new StringTokenizer(var1);

      while(var3.hasMoreTokens()) {
         StringTokenizer var4 = new StringTokenizer(var3.nextToken(), b("\u0006\u000f\u0006"));
         if (var4.countTokens() >= 2) {
            String var5 = var4.nextToken();
            String var6 = var4.nextToken();
            var2.put(var5, var6);
         }

         if (Element.b) {
            break;
         }
      }

      return var2;
   }

   public String toString() {
      return b("`xVa)^[Wg$\\\bm`9OZQm>RGJ4j") + this.getSerializedForm() + "]";
   }

   public final String getSerializedForm() {
      return !"".equals(this.rawData) ? b("\u0007\u0017") + this.target + " " + this.rawData + b("\u0004\u0016") : b("\u0007\u0017") + this.target + b("\u0004\u0016");
   }

   public final boolean equals(Object var1) {
      return var1 == this;
   }

   public final int hashCode() {
      return super.hashCode();
   }

   public Object clone() {
      return new ProcessingInstruction(this.target, this.rawData);
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 59;
               break;
            case 1:
               var10003 = 40;
               break;
            case 2:
               var10003 = 36;
               break;
            case 3:
               var10003 = 14;
               break;
            default:
               var10003 = 74;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
