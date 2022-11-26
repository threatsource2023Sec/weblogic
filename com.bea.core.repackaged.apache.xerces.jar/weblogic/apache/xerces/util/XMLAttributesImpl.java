package weblogic.apache.xerces.util;

import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xni.XMLAttributes;

public class XMLAttributesImpl implements XMLAttributes {
   protected static final int TABLE_SIZE = 101;
   protected static final int MAX_HASH_COLLISIONS = 40;
   protected static final int MULTIPLIERS_SIZE = 32;
   protected static final int MULTIPLIERS_MASK = 31;
   protected static final int SIZE_LIMIT = 20;
   protected boolean fNamespaces;
   protected int fLargeCount;
   protected int fLength;
   protected Attribute[] fAttributes;
   protected Attribute[] fAttributeTableView;
   protected int[] fAttributeTableViewChainState;
   protected int fTableViewBuckets;
   protected boolean fIsTableViewConsistent;
   protected int[] fHashMultipliers;

   public XMLAttributesImpl() {
      this(101);
   }

   public XMLAttributesImpl(int var1) {
      this.fNamespaces = true;
      this.fLargeCount = 1;
      this.fAttributes = new Attribute[4];
      this.fTableViewBuckets = var1;

      for(int var2 = 0; var2 < this.fAttributes.length; ++var2) {
         this.fAttributes[var2] = new Attribute();
      }

   }

   public void setNamespaces(boolean var1) {
      this.fNamespaces = var1;
   }

   public int addAttribute(QName var1, String var2, String var3) {
      int var4;
      int var6;
      if (this.fLength < 20) {
         var4 = var1.uri != null && var1.uri.length() != 0 ? this.getIndexFast(var1.uri, var1.localpart) : this.getIndexFast(var1.rawname);
         if (var4 == -1) {
            var4 = this.fLength;
            if (this.fLength++ == this.fAttributes.length) {
               Attribute[] var5 = new Attribute[this.fAttributes.length + 4];
               System.arraycopy(this.fAttributes, 0, var5, 0, this.fAttributes.length);

               for(var6 = this.fAttributes.length; var6 < var5.length; ++var6) {
                  var5[var6] = new Attribute();
               }

               this.fAttributes = var5;
            }
         }
      } else if (var1.uri == null || var1.uri.length() == 0 || (var4 = this.getIndexFast(var1.uri, var1.localpart)) == -1) {
         if (!this.fIsTableViewConsistent || this.fLength == 20 || this.fLength > 20 && this.fLength > this.fTableViewBuckets) {
            this.prepareAndPopulateTableView();
            this.fIsTableViewConsistent = true;
         }

         int var10 = this.getTableViewBucket(var1.rawname);
         if (this.fAttributeTableViewChainState[var10] != this.fLargeCount) {
            var4 = this.fLength;
            if (this.fLength++ == this.fAttributes.length) {
               Attribute[] var12 = new Attribute[this.fAttributes.length << 1];
               System.arraycopy(this.fAttributes, 0, var12, 0, this.fAttributes.length);

               for(int var7 = this.fAttributes.length; var7 < var12.length; ++var7) {
                  var12[var7] = new Attribute();
               }

               this.fAttributes = var12;
            }

            this.fAttributeTableViewChainState[var10] = this.fLargeCount;
            this.fAttributes[var4].next = null;
            this.fAttributeTableView[var10] = this.fAttributes[var4];
         } else {
            var6 = 0;

            Attribute var13;
            for(var13 = this.fAttributeTableView[var10]; var13 != null && var13.name.rawname != var1.rawname; ++var6) {
               var13 = var13.next;
            }

            if (var13 == null) {
               var4 = this.fLength;
               if (this.fLength++ == this.fAttributes.length) {
                  Attribute[] var8 = new Attribute[this.fAttributes.length << 1];
                  System.arraycopy(this.fAttributes, 0, var8, 0, this.fAttributes.length);

                  for(int var9 = this.fAttributes.length; var9 < var8.length; ++var9) {
                     var8[var9] = new Attribute();
                  }

                  this.fAttributes = var8;
               }

               if (var6 >= 40) {
                  this.fAttributes[var4].name.setValues(var1);
                  this.rebalanceTableView(this.fLength);
               } else {
                  this.fAttributes[var4].next = this.fAttributeTableView[var10];
                  this.fAttributeTableView[var10] = this.fAttributes[var4];
               }
            } else {
               var4 = this.getIndexFast(var1.rawname);
            }
         }
      }

      Attribute var11 = this.fAttributes[var4];
      var11.name.setValues(var1);
      var11.type = var2;
      var11.value = var3;
      var11.nonNormalizedValue = var3;
      var11.specified = false;
      var11.augs.removeAllItems();
      return var4;
   }

   public void removeAllAttributes() {
      this.fLength = 0;
   }

   public void removeAttributeAt(int var1) {
      this.fIsTableViewConsistent = false;
      if (var1 < this.fLength - 1) {
         Attribute var2 = this.fAttributes[var1];
         System.arraycopy(this.fAttributes, var1 + 1, this.fAttributes, var1, this.fLength - var1 - 1);
         this.fAttributes[this.fLength - 1] = var2;
      }

      --this.fLength;
   }

   public void setName(int var1, QName var2) {
      this.fAttributes[var1].name.setValues(var2);
   }

   public void getName(int var1, QName var2) {
      var2.setValues(this.fAttributes[var1].name);
   }

   public void setType(int var1, String var2) {
      this.fAttributes[var1].type = var2;
   }

   public void setValue(int var1, String var2) {
      Attribute var3 = this.fAttributes[var1];
      var3.value = var2;
      var3.nonNormalizedValue = var2;
   }

   public void setNonNormalizedValue(int var1, String var2) {
      if (var2 == null) {
         var2 = this.fAttributes[var1].value;
      }

      this.fAttributes[var1].nonNormalizedValue = var2;
   }

   public String getNonNormalizedValue(int var1) {
      String var2 = this.fAttributes[var1].nonNormalizedValue;
      return var2;
   }

   public void setSpecified(int var1, boolean var2) {
      this.fAttributes[var1].specified = var2;
   }

   public boolean isSpecified(int var1) {
      return this.fAttributes[var1].specified;
   }

   public int getLength() {
      return this.fLength;
   }

   public String getType(int var1) {
      return var1 >= 0 && var1 < this.fLength ? this.getReportableType(this.fAttributes[var1].type) : null;
   }

   public String getType(String var1) {
      int var2 = this.getIndex(var1);
      return var2 != -1 ? this.getReportableType(this.fAttributes[var2].type) : null;
   }

   public String getValue(int var1) {
      return var1 >= 0 && var1 < this.fLength ? this.fAttributes[var1].value : null;
   }

   public String getValue(String var1) {
      int var2 = this.getIndex(var1);
      return var2 != -1 ? this.fAttributes[var2].value : null;
   }

   public String getName(int var1) {
      return var1 >= 0 && var1 < this.fLength ? this.fAttributes[var1].name.rawname : null;
   }

   public int getIndex(String var1) {
      for(int var2 = 0; var2 < this.fLength; ++var2) {
         Attribute var3 = this.fAttributes[var2];
         if (var3.name.rawname != null && var3.name.rawname.equals(var1)) {
            return var2;
         }
      }

      return -1;
   }

   public int getIndex(String var1, String var2) {
      for(int var3 = 0; var3 < this.fLength; ++var3) {
         Attribute var4 = this.fAttributes[var3];
         if (var4.name.localpart != null && var4.name.localpart.equals(var2) && (var1 == var4.name.uri || var1 != null && var4.name.uri != null && var4.name.uri.equals(var1))) {
            return var3;
         }
      }

      return -1;
   }

   public String getLocalName(int var1) {
      if (!this.fNamespaces) {
         return "";
      } else {
         return var1 >= 0 && var1 < this.fLength ? this.fAttributes[var1].name.localpart : null;
      }
   }

   public String getQName(int var1) {
      if (var1 >= 0 && var1 < this.fLength) {
         String var2 = this.fAttributes[var1].name.rawname;
         return var2 != null ? var2 : "";
      } else {
         return null;
      }
   }

   public String getType(String var1, String var2) {
      if (!this.fNamespaces) {
         return null;
      } else {
         int var3 = this.getIndex(var1, var2);
         return var3 != -1 ? this.getReportableType(this.fAttributes[var3].type) : null;
      }
   }

   public String getPrefix(int var1) {
      if (var1 >= 0 && var1 < this.fLength) {
         String var2 = this.fAttributes[var1].name.prefix;
         return var2 != null ? var2 : "";
      } else {
         return null;
      }
   }

   public String getURI(int var1) {
      if (var1 >= 0 && var1 < this.fLength) {
         String var2 = this.fAttributes[var1].name.uri;
         return var2;
      } else {
         return null;
      }
   }

   public String getValue(String var1, String var2) {
      int var3 = this.getIndex(var1, var2);
      return var3 != -1 ? this.getValue(var3) : null;
   }

   public Augmentations getAugmentations(String var1, String var2) {
      int var3 = this.getIndex(var1, var2);
      return var3 != -1 ? this.fAttributes[var3].augs : null;
   }

   public Augmentations getAugmentations(String var1) {
      int var2 = this.getIndex(var1);
      return var2 != -1 ? this.fAttributes[var2].augs : null;
   }

   public Augmentations getAugmentations(int var1) {
      return var1 >= 0 && var1 < this.fLength ? this.fAttributes[var1].augs : null;
   }

   public void setAugmentations(int var1, Augmentations var2) {
      this.fAttributes[var1].augs = var2;
   }

   public void setURI(int var1, String var2) {
      this.fAttributes[var1].name.uri = var2;
   }

   public int getIndexFast(String var1) {
      for(int var2 = 0; var2 < this.fLength; ++var2) {
         Attribute var3 = this.fAttributes[var2];
         if (var3.name.rawname == var1) {
            return var2;
         }
      }

      return -1;
   }

   public void addAttributeNS(QName var1, String var2, String var3) {
      int var4 = this.fLength;
      if (this.fLength++ == this.fAttributes.length) {
         Attribute[] var5;
         if (this.fLength < 20) {
            var5 = new Attribute[this.fAttributes.length + 4];
         } else {
            var5 = new Attribute[this.fAttributes.length << 1];
         }

         System.arraycopy(this.fAttributes, 0, var5, 0, this.fAttributes.length);

         for(int var6 = this.fAttributes.length; var6 < var5.length; ++var6) {
            var5[var6] = new Attribute();
         }

         this.fAttributes = var5;
      }

      Attribute var7 = this.fAttributes[var4];
      var7.name.setValues(var1);
      var7.type = var2;
      var7.value = var3;
      var7.nonNormalizedValue = var3;
      var7.specified = false;
      var7.augs.removeAllItems();
   }

   public QName checkDuplicatesNS() {
      int var1 = this.fLength;
      if (var1 > 20) {
         return this.checkManyDuplicatesNS();
      } else {
         Attribute[] var2 = this.fAttributes;

         for(int var3 = 0; var3 < var1 - 1; ++var3) {
            Attribute var4 = var2[var3];

            for(int var5 = var3 + 1; var5 < var1; ++var5) {
               Attribute var6 = var2[var5];
               if (var4.name.localpart == var6.name.localpart && var4.name.uri == var6.name.uri) {
                  return var6.name;
               }
            }
         }

         return null;
      }
   }

   private QName checkManyDuplicatesNS() {
      this.fIsTableViewConsistent = false;
      this.prepareTableView();
      int var3 = this.fLength;
      Attribute[] var4 = this.fAttributes;
      Attribute[] var5 = this.fAttributeTableView;
      int[] var6 = this.fAttributeTableViewChainState;
      int var7 = this.fLargeCount;

      for(int var8 = 0; var8 < var3; ++var8) {
         Attribute var1 = var4[var8];
         int var2 = this.getTableViewBucket(var1.name.localpart, var1.name.uri);
         if (var6[var2] != var7) {
            var6[var2] = var7;
            var1.next = null;
            var5[var2] = var1;
         } else {
            int var9 = 0;

            for(Attribute var10 = var5[var2]; var10 != null; ++var9) {
               if (var10.name.localpart == var1.name.localpart && var10.name.uri == var1.name.uri) {
                  return var1.name;
               }

               var10 = var10.next;
            }

            if (var9 >= 40) {
               this.rebalanceTableViewNS(var8 + 1);
               var7 = this.fLargeCount;
            } else {
               var1.next = var5[var2];
               var5[var2] = var1;
            }
         }
      }

      return null;
   }

   public int getIndexFast(String var1, String var2) {
      for(int var3 = 0; var3 < this.fLength; ++var3) {
         Attribute var4 = this.fAttributes[var3];
         if (var4.name.localpart == var2 && var4.name.uri == var1) {
            return var3;
         }
      }

      return -1;
   }

   private String getReportableType(String var1) {
      return var1.charAt(0) == '(' ? "NMTOKEN" : var1;
   }

   protected int getTableViewBucket(String var1) {
      return (this.hash(var1) & Integer.MAX_VALUE) % this.fTableViewBuckets;
   }

   protected int getTableViewBucket(String var1, String var2) {
      return var2 == null ? (this.hash(var1) & Integer.MAX_VALUE) % this.fTableViewBuckets : (this.hash(var1, var2) & Integer.MAX_VALUE) % this.fTableViewBuckets;
   }

   private int hash(String var1) {
      return this.fHashMultipliers == null ? var1.hashCode() : this.hash0(var1);
   }

   private int hash(String var1, String var2) {
      return this.fHashMultipliers == null ? var1.hashCode() + var2.hashCode() * 31 : this.hash0(var1) + this.hash0(var2) * this.fHashMultipliers[32];
   }

   private int hash0(String var1) {
      int var2 = 0;
      int var3 = var1.length();
      int[] var4 = this.fHashMultipliers;

      for(int var5 = 0; var5 < var3; ++var5) {
         var2 = var2 * var4[var5 & 31] + var1.charAt(var5);
      }

      return var2;
   }

   protected void cleanTableView() {
      if (++this.fLargeCount < 0) {
         if (this.fAttributeTableViewChainState != null) {
            for(int var1 = this.fTableViewBuckets - 1; var1 >= 0; --var1) {
               this.fAttributeTableViewChainState[var1] = 0;
            }
         }

         this.fLargeCount = 1;
      }

   }

   private void growTableView() {
      int var1 = this.fLength;
      int var2 = this.fTableViewBuckets;

      do {
         var2 = (var2 << 1) + 1;
         if (var2 < 0) {
            var2 = Integer.MAX_VALUE;
            break;
         }
      } while(var1 > var2);

      this.fTableViewBuckets = var2;
      this.fAttributeTableView = null;
      this.fLargeCount = 1;
   }

   protected void prepareTableView() {
      if (this.fLength > this.fTableViewBuckets) {
         this.growTableView();
      }

      if (this.fAttributeTableView == null) {
         this.fAttributeTableView = new Attribute[this.fTableViewBuckets];
         this.fAttributeTableViewChainState = new int[this.fTableViewBuckets];
      } else {
         this.cleanTableView();
      }

   }

   protected void prepareAndPopulateTableView() {
      this.prepareAndPopulateTableView(this.fLength);
   }

   private void prepareAndPopulateTableView(int var1) {
      this.prepareTableView();

      for(int var4 = 0; var4 < var1; ++var4) {
         Attribute var2 = this.fAttributes[var4];
         int var3 = this.getTableViewBucket(var2.name.rawname);
         if (this.fAttributeTableViewChainState[var3] != this.fLargeCount) {
            this.fAttributeTableViewChainState[var3] = this.fLargeCount;
            var2.next = null;
            this.fAttributeTableView[var3] = var2;
         } else {
            var2.next = this.fAttributeTableView[var3];
            this.fAttributeTableView[var3] = var2;
         }
      }

   }

   private void prepareAndPopulateTableViewNS(int var1) {
      this.prepareTableView();

      for(int var4 = 0; var4 < var1; ++var4) {
         Attribute var2 = this.fAttributes[var4];
         int var3 = this.getTableViewBucket(var2.name.localpart, var2.name.uri);
         if (this.fAttributeTableViewChainState[var3] != this.fLargeCount) {
            this.fAttributeTableViewChainState[var3] = this.fLargeCount;
            var2.next = null;
            this.fAttributeTableView[var3] = var2;
         } else {
            var2.next = this.fAttributeTableView[var3];
            this.fAttributeTableView[var3] = var2;
         }
      }

   }

   private void rebalanceTableView(int var1) {
      if (this.fHashMultipliers == null) {
         this.fHashMultipliers = new int[33];
      }

      PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
      this.prepareAndPopulateTableView(var1);
   }

   private void rebalanceTableViewNS(int var1) {
      if (this.fHashMultipliers == null) {
         this.fHashMultipliers = new int[33];
      }

      PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
      this.prepareAndPopulateTableViewNS(var1);
   }

   static class Attribute {
      public final QName name = new QName();
      public String type;
      public String value;
      public String nonNormalizedValue;
      public boolean specified;
      public Augmentations augs = new AugmentationsImpl();
      public Attribute next;
   }
}
