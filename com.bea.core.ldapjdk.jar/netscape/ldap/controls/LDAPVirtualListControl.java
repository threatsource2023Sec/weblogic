package netscape.ldap.controls;

import netscape.ldap.LDAPControl;
import netscape.ldap.ber.stream.BERInteger;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class LDAPVirtualListControl extends LDAPControl {
   public static final String VIRTUALLIST = "2.16.840.1.113730.3.4.9";
   private static final int TAG_BYINDEX = 0;
   private static final int TAG_BYFILTER = 1;
   private int m_beforeCount;
   private int m_afterCount;
   private int m_listIndex;
   private int m_listSize;
   private String m_context;

   LDAPVirtualListControl() {
      super("2.16.840.1.113730.3.4.9", true, (byte[])null);
      this.m_beforeCount = 0;
      this.m_afterCount = 0;
      this.m_listIndex = -1;
      this.m_listSize = 0;
      this.m_context = null;
   }

   public LDAPVirtualListControl(String var1, int var2, int var3) {
      super("2.16.840.1.113730.3.4.9", true, (byte[])null);
      this.m_beforeCount = 0;
      this.m_afterCount = 0;
      this.m_listIndex = -1;
      this.m_listSize = 0;
      this.m_context = null;
      this.setRange(var1, var2, var3);
   }

   public LDAPVirtualListControl(String var1, int var2, int var3, String var4) {
      this(var1, var2, var3);
      this.m_context = var4;
   }

   public LDAPVirtualListControl(int var1, int var2, int var3, int var4) {
      super("2.16.840.1.113730.3.4.9", true, (byte[])null);
      this.m_beforeCount = 0;
      this.m_afterCount = 0;
      this.m_listIndex = -1;
      this.m_listSize = 0;
      this.m_context = null;
      this.m_listSize = var4;
      this.setRange(var1, var2, var3);
   }

   public LDAPVirtualListControl(int var1, int var2, int var3, int var4, String var5) {
      this(var1, var2, var3, var4);
      this.m_context = var5;
   }

   public void setRange(int var1, int var2, int var3) {
      this.m_beforeCount = var2;
      this.m_afterCount = var3;
      this.m_listIndex = var1;
      this.m_value = this.createPageSpecification(this.m_listIndex, this.m_listSize, this.m_beforeCount, this.m_afterCount);
   }

   public void setRange(String var1, int var2, int var3) {
      this.m_beforeCount = var2;
      this.m_afterCount = var3;
      this.m_value = this.createPageSpecification(var1, this.m_beforeCount, this.m_afterCount);
   }

   public int getIndex() {
      return this.m_listIndex;
   }

   public int getListSize() {
      return this.m_listSize;
   }

   public void setListSize(int var1) {
      this.m_listSize = var1;
   }

   public int getBeforeCount() {
      return this.m_beforeCount;
   }

   public int getAfterCount() {
      return this.m_afterCount;
   }

   public String getContext() {
      return this.m_context;
   }

   public void setContext(String var1) {
      this.m_context = var1;
   }

   private byte[] createPageSpecification(String var1, int var2, int var3) {
      BERSequence var4 = new BERSequence();
      var4.addElement(new BERInteger(var2));
      var4.addElement(new BERInteger(var3));
      var4.addElement(new BERTag(129, new BEROctetString(var1), true));
      if (this.m_context != null) {
         var4.addElement(new BEROctetString(this.m_context));
      }

      return this.flattenBER(var4);
   }

   private byte[] createPageSpecification(int var1, int var2, int var3, int var4) {
      BERSequence var5 = new BERSequence();
      var5.addElement(new BERInteger(var3));
      var5.addElement(new BERInteger(var4));
      BERSequence var6 = new BERSequence();
      var6.addElement(new BERInteger(var1));
      var6.addElement(new BERInteger(var2));
      var5.addElement(new BERTag(160, var6, true));
      if (this.m_context != null) {
         var5.addElement(new BEROctetString(this.m_context));
      }

      return this.flattenBER(var5);
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{VirtListCtrl:");
      var1.append(" isCritical=");
      var1.append(this.isCritical());
      var1.append(" beforeCount=");
      var1.append(this.m_beforeCount);
      var1.append(" afterCount=");
      var1.append(this.m_afterCount);
      var1.append(" listIndex=");
      var1.append(this.m_listIndex);
      var1.append(" listSize=");
      var1.append(this.m_listSize);
      if (this.m_context != null) {
         var1.append(" conext=");
         var1.append(this.m_context);
      }

      var1.append("}");
      return var1.toString();
   }
}
