package monfox.toolkit.snmp.mgr;

import java.io.Serializable;
import java.util.Hashtable;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;

public class SnmpParameters implements Cloneable, Serializable {
   private static final SnmpMessageProfile a = new SnmpMessageProfile();
   public static final SnmpMessageProfile DISCOVERY_PROFILE = new SnmpMessageProfile(3, 3, 0, "");
   private static SnmpMessageProfile b;
   private SnmpMessageProfile c;
   private SnmpMessageProfile d;
   private SnmpMessageProfile e;
   private SnmpMessageProfile f;
   private SnmpContext g;
   private Hashtable h;
   private static final String i = "$Id: SnmpParameters.java,v 1.14 2003/02/06 14:37:14 sking Exp $";

   public SnmpParameters() {
      this.c = b;
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
   }

   public SnmpParameters(SnmpMessageProfile var1) {
      this.c = b;
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      this.c = var1;
   }

   public SnmpParameters(int var1, int var2, int var3, String var4) {
      this.c = b;
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      this.c = new SnmpMessageProfile(var1, var2, var3, var4);
   }

   public SnmpParameters(int var1, String var2) {
      this.c = b;
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      this.c = new SnmpMessageProfile(var1, 1, 0, var2);
   }

   public SnmpParameters(String var1) {
      this.c = b;
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      this.c = new SnmpMessageProfile(var1);
   }

   public SnmpParameters(String var1, String var2, String var3) {
      this.c = b;
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = null;
      this.setReadProfile(new SnmpMessageProfile(var1));
      this.setWriteProfile(new SnmpMessageProfile(var2));
      this.setInformProfile(new SnmpMessageProfile(var3));
   }

   public void setVersion(int var1) {
      if (this.c != null) {
         this.c.setSnmpVersion(var1);
      }

      if (this.d != null) {
         this.d.setSnmpVersion(var1);
      }

      if (this.e != null) {
         this.e.setSnmpVersion(var1);
      }

      if (this.f != null) {
         this.f.setSnmpVersion(var1);
      }

   }

   public SnmpMessageProfile getDefaultProfile() {
      return this.c;
   }

   public SnmpMessageProfile getReadProfile() {
      return this.d != null ? this.d : this.c;
   }

   public SnmpMessageProfile getWriteProfile() {
      return this.e != null ? this.e : this.c;
   }

   public SnmpMessageProfile getInformProfile() {
      return this.f != null ? this.f : this.c;
   }

   public void setReadProfile(SnmpMessageProfile var1) {
      this.d = var1;
   }

   public void setWriteProfile(SnmpMessageProfile var1) {
      this.e = var1;
   }

   public void setInformProfile(SnmpMessageProfile var1) {
      this.f = var1;
   }

   public void setDefaultProfile(SnmpMessageProfile var1) {
      this.c = var1;
   }

   public Hashtable getUserTable() {
      return this.h;
   }

   public void setUserTable(Hashtable var1) {
      this.h = var1;
   }

   public SnmpContext getContext() {
      return this.g;
   }

   public void setContext(SnmpContext var1) {
      this.g = var1;
   }

   public void setContext(String var1) {
      this.g = new SnmpContext((SnmpEngineID)null, var1);
   }

   public static void setGlobalDefaultProfile(SnmpMessageProfile var0) {
      b = var0;
   }

   public static SnmpMessageProfile getGlobalDefaultProfile() {
      return b;
   }

   static {
      b = a;
   }
}
