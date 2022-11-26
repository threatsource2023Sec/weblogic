package org.apache.openjpa.util;

import java.io.ObjectStreamException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class java$util$GregorianCalendar$proxy extends GregorianCalendar implements ProxyCalendar {
   private transient OpenJPAStateManager sm;
   private transient int field;

   public java$util$GregorianCalendar$proxy() {
   }

   public java$util$GregorianCalendar$proxy(TimeZone var1) {
      super(var1);
   }

   public java$util$GregorianCalendar$proxy(Locale var1) {
      super(var1);
   }

   public java$util$GregorianCalendar$proxy(TimeZone var1, Locale var2) {
      super(var1, var2);
   }

   public java$util$GregorianCalendar$proxy(int var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public java$util$GregorianCalendar$proxy(int var1, int var2, int var3, int var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   public java$util$GregorianCalendar$proxy(int var1, int var2, int var3, int var4, int var5, int var6) {
      super(var1, var2, var3, var4, var5, var6);
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

   public Object copy(Object var1) {
      GregorianCalendar var10000 = new GregorianCalendar();
      var10000.setTimeInMillis(((Calendar)var1).getTimeInMillis());
      var10000.setLenient(((Calendar)var1).isLenient());
      var10000.setFirstDayOfWeek(((Calendar)var1).getFirstDayOfWeek());
      var10000.setMinimalDaysInFirstWeek(((Calendar)var1).getMinimalDaysInFirstWeek());
      var10000.setTimeZone(((Calendar)var1).getTimeZone());
      return var10000;
   }

   public ProxyCalendar newInstance() {
      return new java$util$GregorianCalendar$proxy();
   }

   protected void computeFields() {
      Proxies.dirty(this, true);
      super.computeFields();
   }

   public void add(int var1, int var2) {
      Proxies.dirty(this, true);
      super.add(var1, var2);
   }

   public void setTimeZone(TimeZone var1) {
      Proxies.dirty(this, true);
      super.setTimeZone(var1);
   }

   public void roll(int var1, boolean var2) {
      Proxies.dirty(this, true);
      super.roll(var1, var2);
   }

   public void roll(int var1, int var2) {
      Proxies.dirty(this, true);
      super.roll(var1, var2);
   }

   public void setGregorianChange(Date var1) {
      Proxies.dirty(this, true);
      super.setGregorianChange(var1);
   }

   public void set(int var1, int var2) {
      Proxies.dirty(this, true);
      super.set(var1, var2);
   }

   public void setLenient(boolean var1) {
      Proxies.dirty(this, true);
      super.setLenient(var1);
   }

   public void setFirstDayOfWeek(int var1) {
      Proxies.dirty(this, true);
      super.setFirstDayOfWeek(var1);
   }

   public void setMinimalDaysInFirstWeek(int var1) {
      Proxies.dirty(this, true);
      super.setMinimalDaysInFirstWeek(var1);
   }

   public void setTimeInMillis(long var1) {
      Proxies.dirty(this, true);
      super.setTimeInMillis(var1);
   }

   protected Object writeReplace() throws ObjectStreamException {
      return Proxies.writeReplace(this, true);
   }
}
