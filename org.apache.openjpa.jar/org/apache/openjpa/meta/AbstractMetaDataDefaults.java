package org.apache.openjpa.meta;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.List;
import org.apache.openjpa.enhance.PCRegistry;
import org.apache.openjpa.enhance.Reflection;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UserException;

public abstract class AbstractMetaDataDefaults implements MetaDataDefaults {
   private static final Localizer _loc = Localizer.forPackage(AbstractMetaDataDefaults.class);
   private int _access = 2;
   private int _identity = 0;
   private boolean _ignore = true;
   private boolean _interface = true;
   private boolean _pcRegistry = true;
   private int _callback = 16;
   private boolean _unwrapped = false;

   public boolean getUsePCRegistry() {
      return this._pcRegistry;
   }

   public void setUsePCRegistry(boolean pcRegistry) {
      this._pcRegistry = pcRegistry;
   }

   public int getDefaultAccessType() {
      return this._access;
   }

   public void setDefaultAccessType(int access) {
      this._access = access;
   }

   public int getDefaultIdentityType() {
      return this._identity;
   }

   public void setDefaultIdentityType(int identity) {
      this._identity = identity;
   }

   public int getCallbackMode() {
      return this._callback;
   }

   public void setCallbackMode(int mode) {
      this._callback = mode;
   }

   public void setCallbackMode(int mode, boolean on) {
      if (on) {
         this._callback |= mode;
      } else {
         this._callback &= ~mode;
      }

   }

   public boolean getCallbacksBeforeListeners(int type) {
      return false;
   }

   public boolean isDeclaredInterfacePersistent() {
      return this._interface;
   }

   public void setDeclaredInterfacePersistent(boolean pers) {
      this._interface = pers;
   }

   public boolean isDataStoreObjectIdFieldUnwrapped() {
      return this._unwrapped;
   }

   public void setDataStoreObjectIdFieldUnwrapped(boolean unwrapped) {
      this._unwrapped = unwrapped;
   }

   public boolean getIgnoreNonPersistent() {
      return this._ignore;
   }

   public void setIgnoreNonPersistent(boolean ignore) {
      this._ignore = ignore;
   }

   public void populate(ClassMetaData meta, int access) {
      if (meta.getDescribedType() != Object.class) {
         if (access == 0) {
            access = this.getAccessType(meta);
            if ((access & 2) != 0 && (access & 4) != 0) {
               List fields = this.getFieldAccessNames(meta);
               List props = this.getPropertyAccessNames(meta);
               if (fields == null && props == null) {
                  throw new UserException(_loc.get("access-field-and-prop", (Object)meta.getDescribedType().getName()));
               }

               throw new UserException(_loc.get("access-field-and-prop-hints", meta.getDescribedType().getName(), fields, props));
            }
         }

         meta.setAccessType(access);
         Log log = meta.getRepository().getLog();
         if (log.isTraceEnabled()) {
            log.trace(_loc.get("gen-meta", (Object)meta));
         }

         if (!this._pcRegistry || !this.populateFromPCRegistry(meta)) {
            if (log.isTraceEnabled()) {
               log.trace(_loc.get("meta-reflect"));
            }

            this.populateFromReflection(meta);
         }

      }
   }

   protected void populate(FieldMetaData fmd) {
   }

   private boolean populateFromPCRegistry(ClassMetaData meta) {
      Class cls = meta.getDescribedType();
      if (!PCRegistry.isRegistered(cls)) {
         return false;
      } else {
         try {
            String[] fieldNames = PCRegistry.getFieldNames(cls);
            Class[] fieldTypes = PCRegistry.getFieldTypes(cls);

            for(int i = 0; i < fieldNames.length; ++i) {
               Object member;
               if (meta.getAccessType() == 2) {
                  member = (Field)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredFieldAction(cls, fieldNames[i]));
               } else {
                  member = Reflection.findGetter(meta.getDescribedType(), fieldNames[i], true);
               }

               FieldMetaData fmd = meta.addDeclaredField(fieldNames[i], fieldTypes[i]);
               fmd.backingMember((Member)member);
               this.populate(fmd);
            }

            return true;
         } catch (OpenJPAException var8) {
            throw var8;
         } catch (Exception var9) {
            Exception e = var9;
            if (var9 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var9).getException();
            }

            throw new UserException(e);
         }
      }
   }

   private void populateFromReflection(ClassMetaData meta) {
      boolean iface = meta.getDescribedType().isInterface();
      Object members;
      if (meta.getAccessType() == 2 && !iface) {
         members = (Field[])((Field[])AccessController.doPrivileged(J2DoPrivHelper.getDeclaredFieldsAction(meta.getDescribedType())));
      } else {
         members = (Method[])((Method[])AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodsAction(meta.getDescribedType())));
      }

      for(int i = 0; i < ((Object[])members).length; ++i) {
         int mods = ((Member)((Object[])members)[i]).getModifiers();
         if (!Modifier.isStatic(mods) && !Modifier.isFinal(mods)) {
            String name = this.getFieldName((Member)((Object[])members)[i]);
            if (name != null && !this.isReservedFieldName(name)) {
               boolean def = this.isDefaultPersistent(meta, (Member)((Object[])members)[i], name);
               if (def || !this._ignore) {
                  FieldMetaData fmd = meta.addDeclaredField(name, Object.class);
                  fmd.backingMember((Member)((Object[])members)[i]);
                  if (!def) {
                     fmd.setExplicit(true);
                     fmd.setManagement(0);
                  }

                  this.populate(fmd);
               }
            }
         }
      }

   }

   protected int getAccessType(ClassMetaData meta) {
      return meta.getDescribedType().isInterface() ? 4 : 2;
   }

   protected List getFieldAccessNames(ClassMetaData meta) {
      return null;
   }

   protected List getPropertyAccessNames(ClassMetaData meta) {
      return null;
   }

   protected String getFieldName(Member member) {
      if (member instanceof Field) {
         return member.getName();
      } else {
         Method meth = (Method)member;
         if (meth.getReturnType() != Void.TYPE && meth.getParameterTypes().length == 0) {
            String name = meth.getName();
            if (name.startsWith("get") && name.length() > 3) {
               name = name.substring(3);
            } else {
               if (meth.getReturnType() != Boolean.TYPE && meth.getReturnType() != Boolean.class || !name.startsWith("is") || name.length() <= 2) {
                  return null;
               }

               name = name.substring(2);
            }

            return name.length() == 1 ? name.toLowerCase() : Character.toLowerCase(name.charAt(0)) + name.substring(1);
         } else {
            return null;
         }
      }
   }

   protected boolean isReservedFieldName(String name) {
      return name.startsWith("openjpa") || name.startsWith("jdo");
   }

   protected abstract boolean isDefaultPersistent(ClassMetaData var1, Member var2, String var3);

   public Member getBackingMember(FieldMetaData fmd) {
      if (fmd == null) {
         return null;
      } else {
         try {
            return (Member)(fmd.getDefiningMetaData().getAccessType() == 2 ? (Field)AccessController.doPrivileged(J2DoPrivHelper.getDeclaredFieldAction(fmd.getDeclaringType(), fmd.getName())) : Reflection.findGetter(fmd.getDeclaringType(), fmd.getName(), true));
         } catch (OpenJPAException var3) {
            throw var3;
         } catch (Exception var4) {
            Exception e = var4;
            if (var4 instanceof PrivilegedActionException) {
               e = ((PrivilegedActionException)var4).getException();
            }

            throw new InternalException(e);
         }
      }
   }

   public Class getUnimplementedExceptionType() {
      return UnsupportedOperationException.class;
   }

   protected static boolean isUserDefined(Class cls) {
      return cls != null && !cls.getName().startsWith("java.") && !cls.getName().startsWith("javax.");
   }
}
