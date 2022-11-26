package org.apache.openjpa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Date;
import org.apache.openjpa.enhance.FieldManager;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.PersistenceCapable;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.enhance.StateManager;
import org.apache.openjpa.kernel.ObjectIdStateManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StateManagerImpl;
import org.apache.openjpa.kernel.StoreManager;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import serp.util.Numbers;

public class ApplicationIds {
   private static final Localizer _loc = Localizer.forPackage(ApplicationIds.class);
   private static final Localizer _loc2 = Localizer.forPackage(StateManagerImpl.class);

   public static Object[] toPKValues(Object oid, ClassMetaData meta) {
      if (meta == null) {
         return null;
      } else {
         Object[] pks;
         if (meta.isOpenJPAIdentity()) {
            pks = new Object[1];
            if (oid != null) {
               pks[0] = ((OpenJPAId)oid).getIdObject();
            }

            return pks;
         } else {
            FieldMetaData[] fmds = meta.getPrimaryKeyFields();
            meta = fmds[fmds.length - 1].getDeclaringMetaData();
            pks = new Object[fmds.length];
            if (oid == null) {
               return pks;
            } else if (!Modifier.isAbstract(meta.getDescribedType().getModifiers())) {
               PrimaryKeyFieldManager consumer = new PrimaryKeyFieldManager();
               consumer.setStore(pks);
               PCRegistry.copyKeyFieldsFromObjectId(meta.getDescribedType(), consumer, oid);
               return consumer.getStore();
            } else {
               if (meta.isObjectIdTypeShared()) {
                  oid = ((ObjectId)oid).getId();
               }

               Class oidType = oid.getClass();

               for(int i = 0; i < fmds.length; ++i) {
                  if (meta.getAccessType() == 2) {
                     pks[i] = Reflection.get(oid, Reflection.findField(oidType, fmds[i].getName(), true));
                  } else {
                     pks[i] = Reflection.get(oid, Reflection.findGetter(oidType, fmds[i].getName(), true));
                  }
               }

               return pks;
            }
         }
      }
   }

   public static Object fromPKValues(Object[] pks, ClassMetaData meta) {
      if (meta != null && pks != null) {
         boolean convert = !meta.getRepository().getConfiguration().getCompatibilityInstance().getStrictIdentityValues();
         Object copy;
         if (meta.isOpenJPAIdentity()) {
            int type = meta.getPrimaryKeyFields()[0].getObjectIdFieldTypeCode();
            copy = convert ? JavaTypes.convert(pks[0], type) : pks[0];
            switch (type) {
               case 1:
               case 17:
                  if (!convert && !(copy instanceof Byte)) {
                     throw new ClassCastException("!(x instanceof Byte)");
                  }

                  return new ByteId(meta.getDescribedType(), copy == null ? 0 : ((Number)copy).byteValue());
               case 2:
               case 18:
                  return new CharId(meta.getDescribedType(), copy == null ? '\u0000' : (Character)copy);
               case 3:
               case 19:
                  if (!convert && !(copy instanceof Double)) {
                     throw new ClassCastException("!(x instanceof Double)");
                  }

                  return new DoubleId(meta.getDescribedType(), copy == null ? 0.0 : ((Number)copy).doubleValue());
               case 4:
               case 20:
                  if (!convert && !(copy instanceof Float)) {
                     throw new ClassCastException("!(x instanceof Float)");
                  }

                  return new FloatId(meta.getDescribedType(), copy == null ? 0.0F : ((Number)copy).floatValue());
               case 5:
               case 21:
                  if (!convert && !(copy instanceof Integer)) {
                     throw new ClassCastException("!(x instanceof Integer)");
                  }

                  return new IntId(meta.getDescribedType(), copy == null ? 0 : ((Number)copy).intValue());
               case 6:
               case 22:
                  if (!convert && !(copy instanceof Long)) {
                     throw new ClassCastException("!(x instanceof Long)");
                  }

                  return new LongId(meta.getDescribedType(), copy == null ? 0L : ((Number)copy).longValue());
               case 7:
               case 23:
                  if (!convert && !(copy instanceof Short)) {
                     throw new ClassCastException("!(x instanceof Short)");
                  }

                  return new ShortId(meta.getDescribedType(), copy == null ? 0 : ((Number)copy).shortValue());
               case 8:
               case 29:
                  return new ObjectId(meta.getDescribedType(), copy);
               case 9:
                  return new StringId(meta.getDescribedType(), (String)copy);
               case 10:
               case 11:
               case 12:
               case 13:
               case 15:
               case 16:
               case 26:
               case 27:
               case 28:
               default:
                  throw new InternalException();
               case 14:
                  return new DateId(meta.getDescribedType(), (Date)copy);
               case 24:
                  if (!convert && !(copy instanceof BigDecimal)) {
                     throw new ClassCastException("!(x instanceof BigDecimal)");
                  }

                  return new BigDecimalId(meta.getDescribedType(), (BigDecimal)copy);
               case 25:
                  if (!convert && !(copy instanceof BigInteger)) {
                     throw new ClassCastException("!(x instanceof BigInteger)");
                  } else {
                     return new BigIntegerId(meta.getDescribedType(), (BigInteger)copy);
                  }
            }
         } else if (!Modifier.isAbstract(meta.getDescribedType().getModifiers())) {
            Object oid = PCRegistry.newObjectId(meta.getDescribedType());
            PrimaryKeyFieldManager producer = new PrimaryKeyFieldManager();
            producer.setStore(pks);
            if (convert) {
               producer.setMetaData(meta);
            }

            PCRegistry.copyKeyFieldsToObjectId(meta.getDescribedType(), producer, oid);
            return oid;
         } else {
            Class oidType = meta.getObjectIdType();
            if (Modifier.isAbstract(oidType.getModifiers())) {
               throw new UserException(_loc.get("objectid-abstract", (Object)meta));
            } else {
               copy = null;

               try {
                  copy = AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(oidType));
               } catch (Throwable var8) {
                  Throwable t = var8;
                  if (var8 instanceof PrivilegedActionException) {
                     t = ((PrivilegedActionException)var8).getException();
                  }

                  throw new GeneralException((Throwable)t);
               }

               FieldMetaData[] fmds = meta.getPrimaryKeyFields();

               for(int i = 0; i < fmds.length; ++i) {
                  Object val = convert ? JavaTypes.convert(pks[i], fmds[i].getObjectIdFieldTypeCode()) : pks[i];
                  if (meta.getAccessType() == 2) {
                     Reflection.set(copy, Reflection.findField(oidType, fmds[i].getName(), true), val);
                  } else {
                     Reflection.set(copy, Reflection.findSetter(oidType, fmds[i].getName(), fmds[i].getDeclaredType(), true), val);
                  }
               }

               if (meta.isObjectIdTypeShared()) {
                  copy = new ObjectId(meta.getDescribedType(), copy);
               }

               return copy;
            }
         }
      } else {
         return null;
      }
   }

   public static Object copy(Object oid, ClassMetaData meta) {
      if (meta != null && oid != null) {
         Class cls;
         if (meta.isOpenJPAIdentity()) {
            cls = meta.getDescribedType();
            OpenJPAId koid = (OpenJPAId)oid;
            FieldMetaData pk = meta.getPrimaryKeyFields()[0];
            switch (pk.getObjectIdFieldTypeCode()) {
               case 1:
               case 17:
                  return new ByteId(cls, ((ByteId)oid).getId(), koid.hasSubclasses());
               case 2:
               case 18:
                  return new CharId(cls, ((CharId)oid).getId(), koid.hasSubclasses());
               case 3:
               case 19:
                  return new DoubleId(cls, ((DoubleId)oid).getId(), koid.hasSubclasses());
               case 4:
               case 20:
                  return new FloatId(cls, ((FloatId)oid).getId(), koid.hasSubclasses());
               case 5:
               case 21:
                  return new IntId(cls, ((IntId)oid).getId(), koid.hasSubclasses());
               case 6:
               case 22:
                  return new LongId(cls, ((LongId)oid).getId(), koid.hasSubclasses());
               case 7:
               case 23:
                  return new ShortId(cls, ((ShortId)oid).getId(), koid.hasSubclasses());
               case 8:
                  return new ObjectId(cls, koid.getIdObject(), koid.hasSubclasses());
               case 9:
                  return new StringId(cls, oid.toString(), koid.hasSubclasses());
               case 10:
               case 11:
               case 12:
               case 13:
               case 15:
               case 16:
               case 26:
               case 27:
               case 28:
               default:
                  throw new InternalException();
               case 14:
                  return new DateId(cls, ((DateId)oid).getId(), koid.hasSubclasses());
               case 24:
                  return new BigDecimalId(cls, ((BigDecimalId)oid).getId(), koid.hasSubclasses());
               case 25:
                  return new BigIntegerId(cls, ((BigIntegerId)oid).getId(), koid.hasSubclasses());
               case 29:
                  ClassMetaData embed = pk.getEmbeddedMetaData();
                  Object inner = koid.getIdObject();
                  if (embed != null) {
                     inner = copy(inner, embed, embed.getFields());
                  }

                  return new ObjectId(cls, inner, koid.hasSubclasses());
            }
         } else if (!Modifier.isAbstract(meta.getDescribedType().getModifiers()) && !hasPCPrimaryKeyFields(meta)) {
            cls = meta.getDescribedType();
            PersistenceCapable pc = PCRegistry.newInstance(cls, (StateManager)null, oid, false);
            Object copy = pc.pcNewObjectIdInstance();
            pc.pcCopyKeyFieldsToObjectId(copy);
            return copy;
         } else {
            Object copy = !meta.isObjectIdTypeShared() ? oid : ((ObjectId)oid).getId();
            copy = copy(copy, meta, meta.getPrimaryKeyFields());
            if (meta.isObjectIdTypeShared()) {
               copy = new ObjectId(meta.getDescribedType(), copy, ((OpenJPAId)oid).hasSubclasses());
            }

            return copy;
         }
      } else {
         return null;
      }
   }

   private static boolean hasPCPrimaryKeyFields(ClassMetaData meta) {
      FieldMetaData[] fmds = meta.getPrimaryKeyFields();

      for(int i = 0; i < fmds.length; ++i) {
         if (fmds[i].getDeclaredTypeCode() == 15) {
            return true;
         }
      }

      return false;
   }

   private static Object copy(Object oid, ClassMetaData meta, FieldMetaData[] fmds) {
      if (oid == null) {
         return null;
      } else {
         Class oidType = oid.getClass();
         Object copy = null;

         try {
            copy = AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(oidType));
         } catch (Throwable var8) {
            Throwable t = var8;
            if (var8 instanceof PrivilegedActionException) {
               t = ((PrivilegedActionException)var8).getException();
            }

            throw new GeneralException((Throwable)t);
         }

         for(int i = 0; i < fmds.length; ++i) {
            if (fmds[i].getManagement() == 3) {
               if (meta.getAccessType() == 2) {
                  Field field = Reflection.findField(oidType, fmds[i].getName(), true);
                  Reflection.set(copy, field, Reflection.get(oid, field));
               } else {
                  Object val = Reflection.get(oid, Reflection.findGetter(oidType, fmds[i].getName(), true));
                  Reflection.set(copy, Reflection.findSetter(oidType, fmds[i].getName(), fmds[i].getObjectIdFieldType(), true), val);
               }
            }
         }

         return copy;
      }
   }

   public static Object get(Object oid, FieldMetaData fmd) {
      if (oid == null) {
         return null;
      } else if (oid instanceof OpenJPAId) {
         return ((OpenJPAId)oid).getIdObject();
      } else {
         ClassMetaData meta = fmd.getDefiningMetaData();
         Class oidType = oid.getClass();
         return meta.getAccessType() == 2 ? Reflection.get(oid, Reflection.findField(oidType, fmd.getName(), true)) : Reflection.get(oid, Reflection.findGetter(oidType, fmd.getName(), true));
      }
   }

   public static Object create(PersistenceCapable pc, ClassMetaData meta) {
      if (pc == null) {
         return null;
      } else {
         Object oid = pc.pcNewObjectIdInstance();
         if (oid == null) {
            return null;
         } else if (!meta.isOpenJPAIdentity()) {
            pc.pcCopyKeyFieldsToObjectId(oid);
            return oid;
         } else {
            FieldMetaData pk = meta.getPrimaryKeyFields()[0];
            if (pk.getDeclaredTypeCode() != 29) {
               return oid;
            } else {
               ObjectId objid = (ObjectId)oid;
               ClassMetaData embed = pk.getEmbeddedMetaData();
               objid.setId(copy(objid.getId(), embed, embed.getFields()));
               return objid;
            }
         }
      }
   }

   public static boolean assign(OpenJPAStateManager sm, StoreManager store, boolean preFlush) {
      ClassMetaData meta = sm.getMetaData();
      if (meta.getIdentityType() != 2) {
         throw new InternalException();
      } else {
         FieldMetaData[] pks = meta.getPrimaryKeyFields();
         boolean ret;
         if (meta.isOpenJPAIdentity() && pks[0].getDeclaredTypeCode() == 29) {
            OpenJPAStateManager oidsm = new ObjectIdStateManager(sm.fetchObjectField(pks[0].getIndex()), sm, pks[0]);
            ret = assign(oidsm, store, pks[0].getEmbeddedMetaData().getFields(), preFlush);
            sm.storeObjectField(pks[0].getIndex(), oidsm.getManagedInstance());
         } else {
            ret = assign(sm, store, meta.getPrimaryKeyFields(), preFlush);
         }

         if (!ret) {
            return false;
         } else {
            sm.setObjectId(create(sm.getPersistenceCapable(), meta));
            return true;
         }
      }
   }

   private static boolean assign(OpenJPAStateManager sm, StoreManager store, FieldMetaData[] pks, boolean preFlush) {
      for(int i = 0; i < pks.length; ++i) {
         if (pks[i].getValueStrategy() != 0) {
            if (!sm.isDefaultValue(pks[i].getIndex())) {
               throw new InvalidStateException(_loc2.get("existing-value-override-excep", (Object)pks[i].getFullName(false)));
            }

            if (!store.assignField(sm, pks[i].getIndex(), preFlush)) {
               return false;
            }

            pks[i].setValueGenerated(true);
         }
      }

      return true;
   }

   private static class PrimaryKeyFieldManager implements FieldManager {
      private Object[] _store;
      private int _index;
      private ClassMetaData _meta;

      private PrimaryKeyFieldManager() {
         this._store = null;
         this._index = 0;
         this._meta = null;
      }

      public void setMetaData(ClassMetaData meta) {
         this._meta = meta;
      }

      public Object[] getStore() {
         return this._store;
      }

      public void setStore(Object[] store) {
         this._store = store;
      }

      public void storeBooleanField(int field, boolean val) {
         this.store(val ? Boolean.TRUE : Boolean.FALSE);
      }

      public void storeByteField(int field, byte val) {
         this.store(new Byte(val));
      }

      public void storeCharField(int field, char val) {
         this.store(new Character(val));
      }

      public void storeShortField(int field, short val) {
         this.store(new Short(val));
      }

      public void storeIntField(int field, int val) {
         this.store(Numbers.valueOf(val));
      }

      public void storeLongField(int field, long val) {
         this.store(Numbers.valueOf(val));
      }

      public void storeFloatField(int field, float val) {
         this.store(new Float(val));
      }

      public void storeDoubleField(int field, double val) {
         this.store(new Double(val));
      }

      public void storeStringField(int field, String val) {
         this.store(val);
      }

      public void storeObjectField(int field, Object val) {
         this.store(val);
      }

      public boolean fetchBooleanField(int field) {
         return this.retrieve(field) == Boolean.TRUE;
      }

      public char fetchCharField(int field) {
         return (Character)this.retrieve(field);
      }

      public byte fetchByteField(int field) {
         return ((Number)this.retrieve(field)).byteValue();
      }

      public short fetchShortField(int field) {
         return ((Number)this.retrieve(field)).shortValue();
      }

      public int fetchIntField(int field) {
         return ((Number)this.retrieve(field)).intValue();
      }

      public long fetchLongField(int field) {
         return ((Number)this.retrieve(field)).longValue();
      }

      public float fetchFloatField(int field) {
         return ((Number)this.retrieve(field)).floatValue();
      }

      public double fetchDoubleField(int field) {
         return ((Number)this.retrieve(field)).doubleValue();
      }

      public String fetchStringField(int field) {
         return (String)this.retrieve(field);
      }

      public Object fetchObjectField(int field) {
         return this.retrieve(field);
      }

      private void store(Object val) {
         this._store[this._index++] = val;
      }

      private Object retrieve(int field) {
         Object val = this._store[this._index++];
         if (this._meta != null) {
            FieldMetaData fmd = this._meta.getField(field);
            if (fmd.getDeclaredTypeCode() != 15) {
               val = JavaTypes.convert(val, fmd.getDeclaredTypeCode());
            } else {
               val = JavaTypes.convert(val, JavaTypes.getTypeCode(fmd.getObjectIdFieldType()));
            }
         }

         return val;
      }

      // $FF: synthetic method
      PrimaryKeyFieldManager(Object x0) {
         this();
      }
   }
}
