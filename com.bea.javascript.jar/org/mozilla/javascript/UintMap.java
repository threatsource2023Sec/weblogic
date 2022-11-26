package org.mozilla.javascript;

class UintMap {
   private static final boolean checkWorld = true;
   private static final boolean checkSelf = false;
   private static final int A = -1640531527;
   private static final int EMPTY = -1;
   private static final int DELETED = -2;
   private int[] keys;
   private Object[] values;
   private int minimalPower;
   private int power;
   private int keyCount;
   private int occupiedCount;
   private int ivaluesShift;

   public UintMap() {
      this(4);
   }

   public UintMap(int var1) {
      check(var1 >= 0);
      int var2 = var1 * 4 / 3;

      int var3;
      for(var3 = 2; 1 << var3 < var2; ++var3) {
      }

      this.minimalPower = var3;
   }

   private static void check(boolean var0) {
      if (!var0) {
         throw new RuntimeException();
      }
   }

   public void clear() {
      this.power = 0;
      this.keys = null;
      this.values = null;
      this.ivaluesShift = 0;
      this.keyCount = 0;
      this.occupiedCount = 0;
   }

   private int ensureIndex(int var1, boolean var2) {
      int var3 = -1;
      int var4 = -1;
      int[] var5 = this.keys;
      if (var5 != null) {
         int var6 = var1 * -1640531527;
         var3 = var6 >>> 32 - this.power;
         int var7 = var5[var3];
         if (var7 == var1) {
            return var3;
         }

         if (var7 != -1) {
            if (var7 == -2) {
               var4 = var3;
            }

            int var8 = (1 << this.power) - 1;
            int var9 = tableLookupStep(var6, var8, this.power);
            boolean var10 = false;

            do {
               var3 = var3 + var9 & var8;
               var7 = var5[var3];
               if (var7 == var1) {
                  return var3;
               }

               if (var7 == -2 && var4 < 0) {
                  var4 = var3;
               }
            } while(var7 != -1);
         }
      }

      if (var4 >= 0) {
         var3 = var4;
      } else {
         if (var5 == null || this.occupiedCount * 4 >= (1 << this.power) * 3) {
            this.rehashTable(var2);
            var5 = this.keys;
            var3 = this.getFreeIndex(var1);
         }

         ++this.occupiedCount;
      }

      var5[var3] = var1;
      ++this.keyCount;
      return var3;
   }

   private int findIndex(int var1) {
      int[] var2 = this.keys;
      if (var2 != null) {
         int var3 = var1 * -1640531527;
         int var4 = var3 >>> 32 - this.power;
         int var5 = var2[var4];
         if (var5 == var1) {
            return var4;
         }

         if (var5 != -1) {
            int var6 = (1 << this.power) - 1;
            int var7 = tableLookupStep(var3, var6, this.power);
            boolean var8 = false;

            do {
               var4 = var4 + var7 & var6;
               var5 = var2[var4];
               if (var5 == var1) {
                  return var4;
               }
            } while(var5 != -1);
         }
      }

      return -1;
   }

   public int getExistingInt(int var1) {
      check(var1 >= 0);
      if (this.ivaluesShift != 0) {
         int var2 = this.findIndex(var1);
         if (var2 >= 0 && !this.isObjectTypeValue(var2)) {
            return this.keys[this.ivaluesShift + var2];
         }
      }

      check(false);
      return 0;
   }

   private int getFreeIndex(int var1) {
      int[] var2 = this.keys;
      int var3 = var1 * -1640531527;
      int var4 = var3 >>> 32 - this.power;
      if (var2[var4] != -1) {
         int var5 = (1 << this.power) - 1;
         int var6 = tableLookupStep(var3, var5, this.power);

         do {
            var4 = var4 + var6 & var5;
         } while(var2[var4] != -1);
      }

      return var4;
   }

   public int getInt(int var1, int var2) {
      check(var1 >= 0);
      if (this.ivaluesShift != 0) {
         int var3 = this.findIndex(var1);
         if (var3 >= 0 && !this.isObjectTypeValue(var3)) {
            return this.keys[this.ivaluesShift + var3];
         }
      }

      return var2;
   }

   public int[] getKeys() {
      int[] var1 = this.keys;
      int var2 = this.keyCount;
      int[] var3 = new int[var2];

      for(int var4 = 0; var2 != 0; ++var4) {
         int var5 = var1[var4];
         if (var5 != -1 && var5 != -2) {
            --var2;
            var3[var2] = var5;
         }
      }

      return var3;
   }

   public Object getObject(int var1) {
      check(var1 >= 0);
      if (this.values != null) {
         int var2 = this.findIndex(var1);
         if (var2 >= 0) {
            return this.values[var2];
         }
      }

      return null;
   }

   public boolean has(int var1) {
      check(var1 >= 0);
      return this.findIndex(var1) >= 0;
   }

   public boolean isEmpty() {
      return this.keyCount == 0;
   }

   public boolean isIntType(int var1) {
      check(var1 >= 0);
      int var2 = this.findIndex(var1);
      return var2 >= 0 && !this.isObjectTypeValue(var2);
   }

   public boolean isObjectType(int var1) {
      check(var1 >= 0);
      int var2 = this.findIndex(var1);
      return var2 >= 0 && this.isObjectTypeValue(var2);
   }

   private boolean isObjectTypeValue(int var1) {
      return this.values != null && this.values[var1] != null;
   }

   public void put(int var1, int var2) {
      check(var1 >= 0);
      int var3 = this.ensureIndex(var1, true);
      if (this.ivaluesShift == 0) {
         int var4 = 1 << this.power;
         int[] var5 = new int[var4 * 2];
         System.arraycopy(this.keys, 0, var5, 0, var4);
         this.keys = var5;
         this.ivaluesShift = var4;
      }

      this.keys[this.ivaluesShift + var3] = var2;
      if (this.values != null) {
         this.values[var3] = null;
      }

   }

   public void put(int var1, Object var2) {
      check(var1 >= 0 && var2 != null);
      int var3 = this.ensureIndex(var1, false);
      if (this.values == null) {
         this.values = new Object[1 << this.power];
      }

      this.values[var3] = var2;
   }

   private void rehashTable(boolean var1) {
      if (this.keys == null) {
         this.power = this.minimalPower;
      } else if (this.keyCount * 2 >= this.occupiedCount) {
         ++this.power;
      }

      int var2 = 1 << this.power;
      int[] var3 = this.keys;
      int var4 = this.ivaluesShift;
      if (var4 == 0 && !var1) {
         this.keys = new int[var2];
      } else {
         this.ivaluesShift = var2;
         this.keys = new int[var2 * 2];
      }

      for(int var5 = 0; var5 != var2; ++var5) {
         this.keys[var5] = -1;
      }

      Object[] var6 = this.values;
      if (var6 != null) {
         this.values = new Object[var2];
      }

      if (var3 != null) {
         int var7 = 0;

         for(int var8 = this.keyCount; var8 != 0; ++var7) {
            int var9 = var3[var7];
            if (var9 != -1 && var9 != -2) {
               int var10 = this.getFreeIndex(var9);
               this.keys[var10] = var9;
               if (var6 != null) {
                  this.values[var10] = var6[var7];
               }

               if (var4 != 0) {
                  this.keys[this.ivaluesShift + var10] = var3[var4 + var7];
               }

               --var8;
            }
         }
      }

      this.occupiedCount = this.keyCount;
   }

   public void remove(int var1) {
      check(var1 >= 0);
      int var2 = this.findIndex(var1);
      if (var2 >= 0) {
         this.keys[var2] = -2;
         --this.keyCount;
         if (this.values != null) {
            this.values[var2] = null;
         }
      }

   }

   public int size() {
      return this.keyCount;
   }

   private static int tableLookupStep(int var0, int var1, int var2) {
      int var3 = 32 - 2 * var2;
      return var3 >= 0 ? var0 >>> var3 & var1 | 1 : var0 & var1 >>> -var3 | 1;
   }
}
