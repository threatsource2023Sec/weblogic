package org.mozilla.javascript;

public abstract class IdScriptable extends ScriptableObject implements IdFunctionMaster {
   protected static final Object NULL_TAG = new Object();
   private int maxId;
   private Object[] idMapData;
   private byte[] attributesArray;
   private static final boolean CACHE_NAMES = true;
   private int lastIdCache;
   private static final int USE_DYNAMIC_SCOPE_FLAG = 1;
   private static final int SEAL_FUNCTIONS_FLAG = 2;
   private byte setupFlags;
   private byte extraIdAttributes;

   public IdScriptable() {
      this.activateIdMap(this.maxInstanceId());
   }

   protected void activateIdMap(int var1) {
      this.maxId = var1;
   }

   public void addAsPrototype(int var1, Context var2, Scriptable var3, boolean var4) {
      this.activateIdMap(var1);
      this.setSealFunctionsFlag(var4);
      this.setFunctionParametrs(var2);
      int var5 = this.mapNameToId("constructor");
      if (var5 == 0) {
         throw new RuntimeException("No id for constructor property");
      } else {
         IdFunction var6 = this.newIdFunction(var5);
         var6.initAsConstructor(var3, this);
         this.fillConstructorProperties(var2, var6, var4);
         if (var4) {
            var6.sealObject();
            var6.addPropertyAttribute(1);
         }

         this.setParentScope(var6);
         this.setPrototype(ScriptableObject.getObjectPrototype(var3));
         this.cacheIdValue(var5, var6);
         if (var4) {
            this.sealObject();
         }

         ScriptableObject.defineProperty(var3, this.getClassName(), var6, 2);
      }
   }

   protected void addIdFunctionProperty(Scriptable var1, int var2, boolean var3) {
      IdFunction var4 = this.newIdFunction(var2);
      if (var3) {
         var4.sealObject();
      }

      ScriptableObject.defineProperty(var1, this.getIdName(var2), var4, 2);
   }

   synchronized void addPropertyAttribute(int var1) {
      this.extraIdAttributes |= (byte)var1;
      super.addPropertyAttribute(var1);
   }

   protected Object cacheIdValue(int var1, Object var2) {
      synchronized(this){}

      try {
         Object[] var5 = this.ensureIdData();
         Object var6 = var5[var1 - 1];
         if (var6 == null) {
            var5[var1 - 1] = var2 != null ? var2 : NULL_TAG;
         } else {
            var2 = var6;
         }
      } catch (Throwable var8) {
         throw var8;
      }

      return var2;
   }

   public void defineProperty(String var1, Object var2, int var3) {
      if (this.maxId != 0) {
         int var4 = this.mapNameToId(var1);
         if (var4 != 0) {
            int var5 = this.getIdDefaultAttributes(var4);
            if ((var5 & 1) != 0) {
               throw new RuntimeException("Attempt to redefine read-only id " + var1);
            }

            this.setAttributes(var4, var3);
            this.setIdValue(var4, var2);
            return;
         }
      }

      super.defineProperty(var1, var2, var3);
   }

   public void delete(String var1) {
      if (this.maxId != 0) {
         int var2 = this.mapNameToId(var1);
         if (var2 != 0 && !this.isSealed()) {
            int var3 = this.getAttributes(var2);
            if ((var3 & 4) == 0) {
               this.deleteIdValue(var2);
            }

            return;
         }
      }

      super.delete(var1);
   }

   protected void deleteIdValue(int var1) {
      synchronized(this){}

      try {
         this.ensureIdData()[var1 - 1] = Scriptable.NOT_FOUND;
      } catch (Throwable var4) {
         throw var4;
      }

   }

   private Object[] ensureIdData() {
      Object[] var1 = this.idMapData;
      if (var1 == null) {
         this.idMapData = var1 = new Object[this.maxId * 2];
      }

      return var1;
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      throw IdFunction.onBadMethodId(this, var1);
   }

   protected void fillConstructorProperties(Context var1, IdFunction var2, boolean var3) {
   }

   public Object get(String var1, Scriptable var2) {
      Object[] var4;
      int var5;
      label27: {
         int var3 = this.maxId;
         if (var3 != 0) {
            var4 = this.idMapData;
            if (var4 == null) {
               var5 = this.mapNameToId(var1);
               if (var5 != 0) {
                  return this.getIdValue(var5);
               }
            } else {
               var5 = this.lastIdCache;
               if (var4[var5 - 1 + var3] == var1) {
                  break label27;
               }

               var5 = this.mapNameToId(var1);
               if (var5 != 0) {
                  var4[var5 - 1 + var3] = var1;
                  this.lastIdCache = var5;
                  break label27;
               }
            }
         }

         return super.get(var1, var2);
      }

      Object var6 = var4[var5 - 1];
      if (var6 == null) {
         var6 = this.getIdValue(var5);
      } else if (var6 == NULL_TAG) {
         var6 = null;
      }

      return var6;
   }

   private int getAttributes(int var1) {
      int var2 = this.getIdDefaultAttributes(var1) | this.extraIdAttributes;
      byte[] var3 = this.attributesArray;
      if (var3 != null) {
         var2 |= 255 & var3[var1 - 1];
      }

      return var2;
   }

   public int getAttributes(String var1, Scriptable var2) throws PropertyException {
      if (this.maxId != 0) {
         int var3 = this.mapNameToId(var1);
         if (var3 != 0 && this.hasValue(var3)) {
            return this.getAttributes(var3);
         }
      }

      return super.getAttributes(var1, var2);
   }

   protected int getIdDefaultAttributes(int var1) {
      return 2;
   }

   protected abstract String getIdName(int var1);

   protected Object getIdValue(int var1) {
      IdFunction var2 = this.newIdFunction(var1);
      var2.setParentScope(this.getParentScope());
      return this.cacheIdValue(var1, var2);
   }

   Object[] getIds(boolean var1) {
      Object[] var2 = super.getIds(var1);
      if (this.maxId != 0) {
         Object[] var3 = null;
         int var4 = 0;

         for(int var5 = this.maxId; var5 != 0; --var5) {
            if (this.hasValue(var5) && (var1 || (this.getAttributes(var5) & 2) == 0)) {
               if (var4 == 0) {
                  var3 = new Object[var5];
               }

               var3[var4++] = this.getIdName(var5);
            }
         }

         if (var4 != 0) {
            if (var2.length == 0 && var3.length == var4) {
               var2 = var3;
            } else {
               Object[] var6 = new Object[var2.length + var4];
               System.arraycopy(var2, 0, var6, 0, var2.length);
               System.arraycopy(var3, 0, var6, var2.length, var4);
               var2 = var6;
            }
         }
      }

      return var2;
   }

   public boolean has(String var1, Scriptable var2) {
      if (this.maxId != 0) {
         int var3 = this.mapNameToId(var1);
         if (var3 != 0) {
            return this.hasValue(var3);
         }
      }

      return super.has(var1, var2);
   }

   protected boolean hasIdValue(int var1) {
      return true;
   }

   private boolean hasValue(int var1) {
      Object[] var3 = this.idMapData;
      Object var2;
      if (var3 != null && (var2 = var3[var1 - 1]) != null) {
         return var2 != Scriptable.NOT_FOUND;
      } else {
         return this.hasIdValue(var1);
      }
   }

   protected abstract int mapNameToId(String var1);

   protected int maxInstanceId() {
      return 0;
   }

   public int methodArity(int var1) {
      return -1;
   }

   protected IdFunction newIdFunction(int var1) {
      IdFunction var2 = new IdFunction(this, this.getIdName(var1), var1);
      if ((this.setupFlags & 2) != 0) {
         var2.sealObject();
      }

      return var2;
   }

   protected Scriptable nextInstanceCheck(Scriptable var1, IdFunction var2, boolean var3) {
      if (var3 && (this.setupFlags & 1) != 0) {
         var1 = var1.getPrototype();
         if (var1 != null) {
            return var1;
         }
      }

      throw NativeGlobal.typeError1("msg.incompat.call", var2.getFunctionName(), var2);
   }

   public void put(String var1, Scriptable var2, Object var3) {
      if (this.maxId != 0) {
         int var4 = this.mapNameToId(var1);
         if (var4 != 0) {
            int var5 = this.getAttributes(var4);
            if ((var5 & 1) == 0) {
               if (var2 == this) {
                  this.setIdValue(var4, var3);
               } else {
                  var2.put(var1, var2, var3);
               }
            }

            return;
         }
      }

      super.put(var1, var2, var3);
   }

   private void setAttributes(int var1, int var2) {
      int var3 = this.getIdDefaultAttributes(var1);
      if ((var2 & var3) != var3) {
         throw new RuntimeException("Attempt to unset default attributes");
      } else {
         var2 &= ~var3;
         byte[] var4 = this.attributesArray;
         if (var4 == null && var2 != 0) {
            synchronized(this){}

            try {
               var4 = this.attributesArray;
               if (var4 == null) {
                  this.attributesArray = var4 = new byte[this.maxId];
               }
            } catch (Throwable var7) {
               throw var7;
            }
         }

         if (var4 != null) {
            var4[var1 - 1] = (byte)var2;
         }

      }
   }

   public void setAttributes(String var1, Scriptable var2, int var3) throws PropertyException {
      if (this.maxId != 0) {
         int var4 = this.mapNameToId(var1);
         if (var4 != 0 && this.hasValue(var4)) {
            synchronized(this){}

            try {
               this.setAttributes(var4, var3);
            } catch (Throwable var7) {
               throw var7;
            }

            return;
         }
      }

      super.setAttributes(var1, var2, var3);
   }

   protected void setFunctionParametrs(Context var1) {
      this.setSetupFlag(1, var1.hasCompileFunctionsWithDynamicScope());
   }

   protected void setIdValue(int var1, Object var2) {
      synchronized(this){}

      try {
         this.ensureIdData()[var1 - 1] = var2 != null ? var2 : NULL_TAG;
      } catch (Throwable var5) {
         throw var5;
      }

   }

   protected void setSealFunctionsFlag(boolean var1) {
      this.setSetupFlag(2, var1);
   }

   private void setSetupFlag(int var1, boolean var2) {
      this.setupFlags = (byte)(var2 ? this.setupFlags | var1 : this.setupFlags & ~var1);
   }

   protected final Object wrap_boolean(boolean var1) {
      return var1 ? Boolean.TRUE : Boolean.FALSE;
   }

   protected final Object wrap_double(double var1) {
      return var1 == var1 ? new Double(var1) : ScriptRuntime.NaNobj;
   }

   protected final Object wrap_int(int var1) {
      byte var2 = (byte)var1;
      return var2 == var1 ? new Byte(var2) : new Integer(var1);
   }

   protected final Object wrap_long(long var1) {
      int var3 = (int)var1;
      return (long)var3 == var1 ? this.wrap_int(var3) : new Long(var1);
   }
}
