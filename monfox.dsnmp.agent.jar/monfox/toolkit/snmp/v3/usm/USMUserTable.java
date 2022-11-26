package monfox.toolkit.snmp.v3.usm;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;

public class USMUserTable extends Hashtable implements Serializable {
   private static final String a = "$Id: USMUserTable.java,v 1.8 2004/11/08 03:19:48 sking Exp $";

   public USMUser addUser(String var1, String var2) throws NoSuchAlgorithmException {
      USMUser var3 = new USMUser(var1, var2, (String)null, 0);
      this.put(var1, var3);
      return var3;
   }

   public USMUser addUser(String var1, String var2, String var3) throws NoSuchAlgorithmException {
      USMUser var4 = new USMUser(var1, var2, var3, 0);
      this.put(var1, var4);
      return var4;
   }

   public USMUser addUser(String var1, String var2, int var3) throws NoSuchAlgorithmException {
      USMUser var4 = new USMUser(var1, var2, (String)null, var3);
      this.put(var1, var4);
      return var4;
   }

   public USMUser getUser(String var1) {
      return (USMUser)this.get(var1);
   }

   public USMUser addUser(String var1, String var2, String var3, int var4) throws NoSuchAlgorithmException {
      USMUser var5 = new USMUser(var1, var2, var3, var4);
      this.put(var1, var5);
      return var5;
   }

   public USMUser addUser(String var1, String var2, String var3, int var4, int var5) throws NoSuchAlgorithmException {
      USMUser var6 = new USMUser(var1, var2, var3, var4, var5);
      this.put(var1, var6);
      return var6;
   }

   public USMUser addUser(USMUser var1) {
      this.put(var1.getName(), var1);
      return var1;
   }

   public USMUser removeUser(String var1) {
      return (USMUser)this.remove(var1);
   }
}
