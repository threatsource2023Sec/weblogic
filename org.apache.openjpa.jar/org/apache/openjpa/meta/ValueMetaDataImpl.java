package org.apache.openjpa.meta;

import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.MetaDataException;
import org.apache.openjpa.util.UserException;

public class ValueMetaDataImpl implements ValueMetaData {
   private static final Localizer _loc = Localizer.forPackage(ValueMetaDataImpl.class);
   private FieldMetaData _owner;
   private Class _decType = Object.class;
   private int _decCode = 8;
   private ClassMetaData _decTypeMeta = null;
   private Class _type = null;
   private int _code = 8;
   private ClassMetaData _typeMeta = null;
   private Class _typeOverride = null;
   private int _delete = 0;
   private int _persist = 2;
   private int _attach = 1;
   private int _refresh = 2;
   private boolean _serialized = false;
   private Boolean _embedded = null;
   private ClassMetaData _embeddedMeta = null;
   private int _resMode = 0;
   private String _mappedBy = null;
   private FieldMetaData _mappedByMeta = null;

   protected ValueMetaDataImpl(FieldMetaData owner) {
      this._owner = owner;
   }

   protected ValueMetaDataImpl() {
   }

   public FieldMetaData getFieldMetaData() {
      return this._owner;
   }

   public MetaDataRepository getRepository() {
      return this._owner.getRepository();
   }

   public Class getType() {
      return this._type == null ? this._decType : this._type;
   }

   public void setType(Class type) {
      this._type = type;
      this._typeMeta = null;
      if (type != null) {
         this.setTypeCode(JavaTypes.getTypeCode(type));
      }

   }

   public int getTypeCode() {
      return this._type == null ? this._decCode : this._code;
   }

   public void setTypeCode(int code) {
      this._code = code;
   }

   public boolean isTypePC() {
      return this.getTypeCode() == 15 || this.getTypeCode() == 27;
   }

   public ClassMetaData getTypeMetaData() {
      if (this._type == null) {
         return this.getDeclaredTypeMetaData();
      } else {
         if (this._typeMeta == null && this._code == 15) {
            ClassMetaData meta = this._owner.getDefiningMetaData();
            this._typeMeta = meta.getRepository().getMetaData(this._type, meta.getEnvClassLoader(), true);
         }

         return this._typeMeta;
      }
   }

   public Class getDeclaredType() {
      return this._decType;
   }

   public void setDeclaredType(Class type) {
      this._decType = type;
      this._decTypeMeta = null;
      this._decCode = JavaTypes.getTypeCode(type);
      if (this._embeddedMeta != null) {
         this._embeddedMeta.setDescribedType(type);
      }

   }

   public int getDeclaredTypeCode() {
      return this._decCode;
   }

   public void setDeclaredTypeCode(int code) {
      this._decCode = code;
   }

   public boolean isDeclaredTypePC() {
      return this._decCode == 15 || this._decCode == 27;
   }

   public ClassMetaData getDeclaredTypeMetaData() {
      if (this._decTypeMeta == null && this._decCode == 15) {
         if (this.isEmbedded()) {
            this._decTypeMeta = this.getEmbeddedMetaData();
         } else {
            ClassMetaData meta = this._owner.getDefiningMetaData();
            this._decTypeMeta = meta.getRepository().getMetaData(this._decType, meta.getEnvClassLoader(), true);
         }
      }

      return this._decTypeMeta;
   }

   public boolean isEmbedded() {
      int var10000 = this._owner.getManagement();
      FieldMetaData var10001 = this._owner;
      if (var10000 != 3) {
         return false;
      } else {
         if (this._embedded == null) {
            switch (this._decCode) {
               case 12:
               case 13:
               case 15:
               case 27:
                  this._embedded = Boolean.FALSE;
                  break;
               default:
                  this._embedded = Boolean.TRUE;
            }
         }

         return this._embedded;
      }
   }

   public void setEmbedded(boolean embedded) {
      if (embedded && this._embedded != Boolean.TRUE) {
         this._decTypeMeta = null;
         this._typeMeta = null;
      }

      this._embedded = embedded ? Boolean.TRUE : Boolean.FALSE;
   }

   public boolean isEmbeddedPC() {
      return this._decCode == 15 && this.isEmbedded();
   }

   public ClassMetaData getEmbeddedMetaData() {
      if (this._embeddedMeta == null && this.isEmbeddedPC()) {
         this.addEmbeddedMetaData();
      }

      return this._embeddedMeta;
   }

   public ClassMetaData addEmbeddedMetaData() {
      MetaDataRepository repos = this._owner.getRepository();
      this._embeddedMeta = repos.newEmbeddedClassMetaData(this);
      this._embeddedMeta.setDescribedType(this._decType);
      repos.getMetaDataFactory().getDefaults().populate(this._embeddedMeta, 0);
      this.setEmbedded(true);
      return this._embeddedMeta;
   }

   public int getCascadeDelete() {
      if (this._owner.getManagement() != 3) {
         return 0;
      } else if (this.isEmbeddedPC()) {
         return 1;
      } else {
         switch (this._delete) {
            case 0:
               if (this != this._owner.getValue() && this.isTypePC() && ((ValueMetaDataImpl)this._owner.getValue())._delete == 2) {
                  return 2;
               }
               break;
            case 1:
               if (this.isDeclaredTypePC()) {
                  return 1;
               }
               break;
            case 2:
               if (this.isTypePC()) {
                  return 2;
               }
         }

         return 0;
      }
   }

   public void setCascadeDelete(int delete) {
      this._delete = delete;
   }

   public int getCascadePersist() {
      if (this._owner.getManagement() != 3) {
         return 0;
      } else if (this.isDeclaredTypePC()) {
         return this._persist;
      } else if (!this.isTypePC()) {
         return 0;
      } else {
         return this._persist == 1 ? 2 : this._persist;
      }
   }

   public void setCascadePersist(int persist) {
      this._persist = persist;
   }

   public int getCascadeAttach() {
      if (this._owner.getManagement() == 3 && this.isDeclaredTypePC()) {
         return this.isEmbeddedPC() ? 1 : this._attach;
      } else {
         return 0;
      }
   }

   public void setCascadeAttach(int attach) {
      if (attach == 2) {
         throw new IllegalArgumentException("CASCADE_AUTO");
      } else {
         this._attach = attach;
      }
   }

   public int getCascadeRefresh() {
      return this._owner.getManagement() == 3 && this.isDeclaredTypePC() ? this._refresh : 0;
   }

   public void setCascadeRefresh(int refresh) {
      this._refresh = refresh;
   }

   public boolean isSerialized() {
      return this._serialized;
   }

   public void setSerialized(boolean serialized) {
      this._serialized = serialized;
   }

   public String getValueMappedBy() {
      if (this._mappedBy == "`pk`") {
         ClassMetaData meta = this.getRepository().getMetaData((Class)this._owner.getElement().getType(), (ClassLoader)null, false);
         if (meta == null) {
            throw new MetaDataException(_loc.get("val-not-pc", (Object)this._owner));
         }

         if (meta.getPrimaryKeyFields().length != 1) {
            throw new MetaDataException(_loc.get("val-not-one-pk", (Object)this._owner));
         }

         this._mappedByMeta = meta.getPrimaryKeyFields()[0];
         this._mappedBy = this._mappedByMeta.getName();
      }

      return this._mappedBy;
   }

   public void setValueMappedBy(String mapped) {
      if (this._owner.getKey() != this && mapped != null) {
         throw new UserException(_loc.get("mapped-by-not-key", (Object)this));
      } else {
         this._mappedBy = mapped;
         this._mappedByMeta = null;
      }
   }

   public FieldMetaData getValueMappedByMetaData() {
      if (this.getValueMappedBy() != null && this._mappedByMeta == null) {
         ClassMetaData meta = this._owner.getElement().getTypeMetaData();
         FieldMetaData field = meta == null ? null : meta.getField(this.getValueMappedBy());
         if (field == null) {
            throw new MetaDataException(_loc.get("no-mapped-by", this, this.getValueMappedBy()));
         }

         if (field.getMappedBy() != null) {
            throw new MetaDataException(_loc.get("circ-mapped-by", this, this.getValueMappedBy()));
         }

         this._mappedByMeta = field;
      }

      return this._mappedByMeta;
   }

   public Class getTypeOverride() {
      return this._typeOverride;
   }

   public void setTypeOverride(Class val) {
      this._typeOverride = val;
   }

   public String toString() {
      String ret = this._owner.getFullName(true);
      if (this == this._owner.getKey()) {
         return ret + "<key:" + this._decType + ">";
      } else if (this == this._owner.getElement()) {
         return this._owner.getTypeCode() == 13 ? ret + "<value:" + this._decType + ">" : ret + "<element:" + this._decType + ">";
      } else {
         return ret + "<" + this._decType + ">";
      }
   }

   public int getResolve() {
      return this._resMode;
   }

   public void setResolve(int mode) {
      this._resMode = mode;
   }

   public void setResolve(int mode, boolean on) {
      if (mode == 0) {
         this._resMode = mode;
      } else if (on) {
         this._resMode |= mode;
      } else {
         this._resMode &= ~mode;
      }

   }

   public boolean resolve(int mode) {
      if ((this._resMode & mode) == mode) {
         return true;
      } else {
         int cur = this._resMode;
         this._resMode |= mode;
         if ((mode & 1) != 0 && (cur & 1) == 0) {
            int codeOverride = true;
            if (this._typeOverride != null) {
               int codeOverride = JavaTypes.getTypeCode(this._typeOverride);
               if (this._owner.getExternalizerMethod() != null && this._owner.getValue() == this) {
                  this._decCode = codeOverride;
                  if (JavaTypes.maybePC(codeOverride, this._typeOverride)) {
                     this.resolveDeclaredType(this._typeOverride);
                  }
               } else {
                  this._type = this._typeOverride;
                  this._code = codeOverride;
               }
            }

            if (JavaTypes.maybePC(this._code, this._type)) {
               this._typeMeta = this._owner.getRepository().getMetaData(this._type, this._owner.getDefiningMetaData().getEnvClassLoader(), false);
               if (this._typeMeta != null) {
                  this._code = 15;
               }
            }

            if (this._typeOverride != null && this._owner.getExternalizerMethod() == null && this._owner.getExternalValues() == null) {
               this._decCode = this._code;
               this._decTypeMeta = this._typeMeta;
            } else if (JavaTypes.maybePC(this._decCode, this._decType)) {
               this.resolveDeclaredType(this._decType);
            }

            this.getValueMappedBy();
            ClassMetaData embed = this.getEmbeddedMetaData();
            if (embed != null) {
               embed.resolve(1);
            }

            if (this._decCode == 15 && this.isEmbedded() && this._owner.isPrimaryKey() && this._owner.getValue() == this) {
               this._code = this._decCode = 29;
            }

            return false;
         } else {
            return false;
         }
      }
   }

   private void resolveDeclaredType(Class type) {
      ClassMetaData meta = this._owner.getRepository().getMetaData(type, this._owner.getDefiningMetaData().getEnvClassLoader(), false);
      if (meta != null) {
         this._decCode = 15;
      }

      if (!this.isEmbedded()) {
         this._decTypeMeta = meta;
      }

   }

   public void copy(ValueMetaData vmd) {
      this._decType = vmd.getDeclaredType();
      this._decCode = vmd.getDeclaredTypeCode();
      if (this._decCode == 29) {
         this._decCode = 15;
      }

      this._delete = vmd.getCascadeDelete();
      this._persist = vmd.getCascadePersist();
      this._attach = vmd.getCascadeAttach();
      this._refresh = vmd.getCascadeRefresh();
      this._typeOverride = vmd.getTypeOverride();
      this._serialized = vmd.isSerialized();
      if (this._embeddedMeta != null) {
         this._embeddedMeta.setDescribedType(vmd.getDeclaredType());
      }

      if (this._embedded == null) {
         this.setEmbedded(vmd.isEmbedded());
      }

   }
}
