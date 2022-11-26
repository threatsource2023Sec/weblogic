package monfox.toolkit.snmp;

public class SnmpFixedString extends SnmpString {
   static final long serialVersionUID = 1201249046718699387L;
   private int b = 0;
   private static final String c = "$Id: SnmpFixedString.java,v 1.9 2002/10/31 16:36:40 sking Exp $";

   public SnmpFixedString(SnmpFixedString var1) {
      super((SnmpString)var1);
      this.b = var1.b;
   }

   public SnmpFixedString(int var1, String var2) throws IllegalArgumentException, SnmpValueException {
      super(var2);
      this.b = var1;
   }

   public SnmpFixedString() {
      super(new byte[0]);
      this.b = 0;
   }

   public SnmpFixedString(int var1, byte[] var2) throws IllegalArgumentException {
      super(var2);
      this.b = var1;
   }

   public SnmpFixedString(String var1) throws SnmpValueException {
      super(var1);
      this.b = var1.length();
   }

   public SnmpFixedString(byte[] var1) {
      super(var1);
      this.b = var1.length;
   }

   public Object clone() {
      return new SnmpFixedString(this);
   }

   public SnmpOid toIndexOid(boolean var1) {
      boolean var4 = SnmpValue.b;
      long[] var2 = new long[this.b];
      int var3 = 0;

      while(var3 < this.b) {
         label17: {
            if (var3 < this.a.length) {
               var2[var3] = (long)this.a[var3];
               if (!var4) {
                  break label17;
               }
            }

            var2[var3] = 0L;
         }

         ++var3;
         if (var4) {
            break;
         }
      }

      return new SnmpOid(var2);
   }

   public void appendIndexOid(SnmpOid var1, boolean var2) {
      boolean var4 = SnmpValue.b;
      int var3 = 0;

      while(var3 < this.b) {
         label17: {
            if (var3 < this.a.length) {
               var1.append((long)this.a[var3]);
               if (!var4) {
                  break label17;
               }
            }

            var1.append(0L);
         }

         ++var3;
         if (var4) {
            break;
         }
      }

   }

   public int fromIndexOid(SnmpOid var1, int var2, boolean var3) throws SnmpValueException {
      boolean var6;
      label27: {
         var6 = SnmpValue.b;
         if (this.b == 0) {
            this.b = var1.getLength() - var2;
            if (!var6) {
               break label27;
            }
         }

         if (this.b + var2 > var1.getLength()) {
            throw new SnmpValueException(a("M\u0003s\"Ym\t%*[`\b}czM)%/Pj\nq+\u000f$") + this.b + a("*MK,A$\bk,@c\u0005%&Ya\u0000`-AwC"));
         }
      }

      byte[] var4 = new byte[this.b];
      int var5 = 0;

      while(true) {
         if (var5 < this.b) {
            var4[var5] = (byte)((int)var1.get(var5 + var2));
            ++var5;
            if (var6) {
               break;
            }

            if (!var6) {
               continue;
            }
         }

         this.a = var4;
         break;
      }

      return var2 + this.b;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 4;
               break;
            case 1:
               var10003 = 109;
               break;
            case 2:
               var10003 = 5;
               break;
            case 3:
               var10003 = 67;
               break;
            default:
               var10003 = 53;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
