package monfox.toolkit.snmp.v3.usm;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import monfox.toolkit.snmp.engine.SnmpEngineID;

public class USMEngineInfo implements Serializable {
   private int a = -1;
   private SnmpEngineID b = null;
   private int c = 0;
   private int d = 0;
   private int e = 0;
   private long f = 0L;
   private int g = 0;
   private int h = 0;
   private Hashtable i = new Hashtable();
   private static final String j = "$Id: USMEngineInfo.java,v 1.14 2011/02/09 01:58:15 sking Exp $";

   public USMEngineInfo(SnmpEngineID var1) {
      this.b = var1;
   }

   public SnmpEngineID getEngineID() {
      return this.b;
   }

   public int getEngineBoots() {
      return this.c;
   }

   public USMLocalizedUserData getUserData(String var1) {
      return var1 == null ? null : (USMLocalizedUserData)this.i.get(var1);
   }

   public void addUserData(USMUserTable var1) {
      if (var1 != null) {
         Enumeration var2 = var1.elements();

         while(var2.hasMoreElements()) {
            USMUser var3 = (USMUser)var2.nextElement();
            this.addUserData(new USMLocalizedUserData(var3, this.b));
            if (USMLocalizedUserData.k) {
               break;
            }
         }

      }
   }

   public void addUserData(USMLocalizedUserData var1) {
      this.i.put(var1.getName(), var1);
   }

   public USMLocalizedUserData removeUserData(String var1) {
      return var1 == null ? null : (USMLocalizedUserData)this.i.remove(var1);
   }

   public int getEngineTime() {
      if (this.f == 0L) {
         return 0;
      } else {
         long var1 = System.currentTimeMillis();
         long var3 = var1 - this.f;
         return (int)(var3 / 1000L);
      }
   }

   public int getLastEngineTime() {
      return this.e;
   }

   public void setEngineBoots(int var1) {
      this.c = var1;
   }

   void a() {
      this.c = 0;
      this.d = 0;
      this.e = 0;
   }

   public void setLastEngineTime(int var1) {
      this.d = var1;
      long var2 = System.currentTimeMillis();
      this.e = var1;
      long var4 = (long)this.e * 1000L;
      this.f = var2 - var4;
   }

   public String toString() {
      return a("\u001e\u001ep$\u0019\u000b\u001eW\u0007M") + this.getEngineID() + a("I\u001ep$\u0019\u000b\u001e\\,\u001f\u0011\b#") + this.getEngineBoots() + a("I\u001ep$\u0019\u000b\u001eJ*\u001d\u0000F") + this.getEngineTime() + a("I\u000em&\u0002(\u001an~") + this.i.toString() + "}";
   }

   void a(int var1) {
      this.a = var1;
   }

   long b() {
      return (long)this.a;
   }

   public Iterator getAllUserData() {
      Set var1 = Collections.unmodifiableSet(this.i.entrySet());
      return var1.iterator();
   }

   int c() {
      return this.g;
   }

   void b(int var1) {
      this.g = var1;
   }

   public int getAutoTimeResyncThreshold() {
      return this.h;
   }

   public void setAutoTimeResyncThreshold(int var1) {
      this.h = var1;
   }

   Hashtable d() {
      return this.i;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 101;
               break;
            case 1:
               var10003 = 123;
               break;
            case 2:
               var10003 = 30;
               break;
            case 3:
               var10003 = 67;
               break;
            default:
               var10003 = 112;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
