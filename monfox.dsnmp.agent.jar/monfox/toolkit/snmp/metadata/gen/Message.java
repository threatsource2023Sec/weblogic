package monfox.toolkit.snmp.metadata.gen;

public class Message {
   String a = null;
   String b = null;
   int c = -1;
   public static int d;

   public Message(String var1) {
      this.a = var1;
   }

   public String getMessage() {
      return this.a;
   }

   public String toString() {
      return this.a;
   }

   public String getFilename() {
      return this.b;
   }

   public int getLineNumber() {
      return this.c;
   }

   public void setFilename(String var1) {
      this.b = var1;
   }

   public void setLineNumber(int var1) {
      this.c = var1;
   }
}
