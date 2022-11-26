package kodo.beans;

import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.util.Exceptions;
import org.apache.openjpa.util.UserException;
import serp.util.Strings;

public class StateClassAccessor implements ClassAccessor {
   private final ClassMetaData meta;
   private final StoreContext ctx;
   private final StateFieldAccessor[] fields;

   public StateClassAccessor(Class forClass, StoreContext ctx) {
      this.ctx = ctx;
      this.meta = ctx.getConfiguration().getMetaDataRepositoryInstance().getMetaData(forClass, ctx.getClassLoader(), true);
      FieldMetaData[] fmd = this.meta.getFields();
      this.fields = new StateFieldAccessor[fmd.length];

      for(int i = 0; i < this.fields.length; ++i) {
         this.fields[i] = new StateFieldAccessor(fmd[i]);
      }

   }

   public Class getType() {
      return this.meta.getDescribedType();
   }

   public FieldAccessor[] getFieldAccessors() {
      return this.fields;
   }

   public Object newInstance() {
      try {
         return this.getType().newInstance();
      } catch (InstantiationException var2) {
         throw (new UserException(this.getType().toString(), var2)).setFatal(true);
      } catch (IllegalAccessException var3) {
         throw (new UserException(this.getType().toString(), var3)).setFatal(true);
      }
   }

   public int hashCode() {
      return this.meta == null ? 0 : this.meta.hashCode();
   }

   public boolean equals(Object other) {
      boolean var10000;
      label25: {
         if (other instanceof StateClassAccessor) {
            if (((StateClassAccessor)other).meta == null) {
               if (this.meta == null) {
                  break label25;
               }
            } else if (((StateClassAccessor)other).meta.equals(this.meta)) {
               break label25;
            }
         }

         var10000 = false;
         return var10000;
      }

      var10000 = true;
      return var10000;
   }

   public String toString() {
      return super.toString() + "[" + this.meta + "]";
   }

   public class StateFieldAccessor implements FieldAccessor {
      private final FieldMetaData meta;

      public StateFieldAccessor(FieldMetaData meta) {
         this.meta = meta;
      }

      public Class getType() {
         return this.meta.getType();
      }

      public String getName() {
         return this.meta.getName();
      }

      public ClassAccessor getClassAccessor() {
         return StateClassAccessor.this;
      }

      public boolean declaresField(Object forObject) {
         return forObject != null && this.meta.getDeclaringType().isAssignableFrom(forObject.getClass());
      }

      public Object getValue(Object forObject) {
         OpenJPAStateManager sm = StateClassAccessor.this.ctx.getStateManager(forObject);
         return sm != null && !sm.isDeleted() ? sm.fetchField(this.meta.getIndex(), true) : null;
      }

      public void setValue(Object forObject, Object value) {
         OpenJPAStateManager sm = StateClassAccessor.this.ctx.getStateManager(forObject);
         if (sm == null) {
            throw (new UserException(Exceptions.toString(forObject))).setFailedObject(forObject);
         } else {
            sm.setRemote(this.meta.getIndex(), value);
         }
      }

      public boolean isEditable(Object forObject) {
         OpenJPAStateManager sm = StateClassAccessor.this.ctx.getStateManager(forObject);
         if (sm != null && StateClassAccessor.this.ctx.isActive() && Strings.canParse(this.meta.getType())) {
            if (sm.isDeleted()) {
               return false;
            } else if (!this.meta.isPrimaryKey()) {
               return true;
            } else {
               return sm.isNew() && !sm.isFlushed();
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         return this.meta == null ? 0 : this.meta.hashCode();
      }

      public boolean equals(Object other) {
         boolean var10000;
         label25: {
            if (other instanceof StateFieldAccessor) {
               if (((StateFieldAccessor)other).meta == null) {
                  if (this.meta == null) {
                     break label25;
                  }
               } else if (((StateFieldAccessor)other).meta.equals(this.meta)) {
                  break label25;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }

      public String toString() {
         return super.toString() + "[" + this.meta + "]";
      }
   }
}
