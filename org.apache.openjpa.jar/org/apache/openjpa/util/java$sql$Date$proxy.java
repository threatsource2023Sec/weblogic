package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.sql.Date;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$sql$Date$proxy extends Date implements ProxyDate {
   private transient OpenJPAStateManager sm;
   private transient int field;

   public java$sql$Date$proxy(int var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public java$sql$Date$proxy(long var1) {
      super(var1);
   }

   public void setOwner(OpenJPAStateManager var1, int var2) {
      this.sm = var1;
      this.field = var2;
   }

   public OpenJPAStateManager getOwner() {
      return this.sm;
   }

   public int getOwnerField() {
      return this.field;
   }

   public Object clone() {
      Proxy var1 = (Proxy)super.clone();
      var1.setOwner((OpenJPAStateManager)null, 0);
      return var1;
   }

   public ChangeTracker getChangeTracker() {
      return null;
   }

   public java$sql$Date$proxy() {
      super(System.currentTimeMillis());
   }

   public Object copy(Object var1) {
      return new Date(((java.util.Date)var1).getTime());
   }

   public ProxyDate newInstance() {
      return new java$sql$Date$proxy();
   }

   public void setTime(long var1) {
      Proxies.dirty(this, true);
      super.setTime(var1);
   }

   public void setHours(int var1) {
      Proxies.dirty(this, true);
      super.setHours(var1);
   }

   public void setMinutes(int var1) {
      Proxies.dirty(this, true);
      super.setMinutes(var1);
   }

   public void setSeconds(int var1) {
      Proxies.dirty(this, true);
      super.setSeconds(var1);
   }

   public void setDate(int var1) {
      Proxies.dirty(this, true);
      super.setDate(var1);
   }

   public void setMonth(int var1) {
      Proxies.dirty(this, true);
      super.setMonth(var1);
   }

   public void setYear(int var1) {
      Proxies.dirty(this, true);
      super.setYear(var1);
   }

   protected Object writeReplace() throws ObjectStreamException {
      return Proxies.writeReplace(this, true);
   }
}
