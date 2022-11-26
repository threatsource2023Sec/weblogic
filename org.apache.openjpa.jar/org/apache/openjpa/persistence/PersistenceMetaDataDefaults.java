package org.apache.openjpa.persistence;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.J2DoPriv5Helper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.AbstractMetaDataDefaults;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.JavaTypes;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.MetaDataException;

public class PersistenceMetaDataDefaults extends AbstractMetaDataDefaults {
   private boolean _allowsMultipleMethodsForSameCallback = false;
   private static final Localizer _loc = Localizer.forPackage(PersistenceMetaDataDefaults.class);
   private static final Map _strats = new HashMap();
   private static final Set _ignoredAnnos = new HashSet();

   public PersistenceMetaDataDefaults() {
      this.setCallbackMode(50);
      this.setDataStoreObjectIdFieldUnwrapped(true);
   }

   public static PersistenceStrategy getPersistenceStrategy(FieldMetaData fmd, Member member) {
      if (member == null) {
         return null;
      } else {
         AnnotatedElement el = (AnnotatedElement)member;
         if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(el, Transient.class))) {
            return PersistenceStrategy.TRANSIENT;
         } else if (fmd != null && fmd.getManagement() != 3) {
            return null;
         } else {
            PersistenceStrategy pstrat = null;
            Annotation[] arr$ = el.getDeclaredAnnotations();
            int code = arr$.length;

            for(int i$ = 0; i$ < code; ++i$) {
               Annotation anno = arr$[i$];
               if (pstrat != null && _strats.containsKey(anno.annotationType())) {
                  throw new MetaDataException(_loc.get("already-pers", (Object)member));
               }

               if (pstrat == null) {
                  pstrat = (PersistenceStrategy)_strats.get(anno.annotationType());
               }
            }

            if (pstrat != null) {
               return pstrat;
            } else {
               Class type;
               if (fmd != null) {
                  type = fmd.getType();
                  code = fmd.getTypeCode();
               } else if (member instanceof Field) {
                  type = ((Field)member).getType();
                  code = JavaTypes.getTypeCode(type);
               } else {
                  type = ((Method)member).getReturnType();
                  code = JavaTypes.getTypeCode(type);
               }

               switch (code) {
                  case 0:
                  case 1:
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                  case 6:
                  case 7:
                  case 9:
                  case 14:
                  case 16:
                  case 17:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 22:
                  case 23:
                  case 24:
                  case 25:
                     return PersistenceStrategy.BASIC;
                  case 8:
                     if (Enum.class.isAssignableFrom(type)) {
                        return PersistenceStrategy.BASIC;
                     }
                  case 10:
                  case 12:
                  case 13:
                  case 15:
                  default:
                     break;
                  case 11:
                     if (type == byte[].class || type == char[].class || type == Byte[].class || type == Character[].class) {
                        return PersistenceStrategy.BASIC;
                     }
               }

               if ((Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction(type, Embeddable.class))) {
                  return PersistenceStrategy.EMBEDDED;
               } else {
                  return Serializable.class.isAssignableFrom(type) ? PersistenceStrategy.BASIC : null;
               }
            }
         }
      }
   }

   public boolean getAllowsMultipleMethodsForSameCallback() {
      return this._allowsMultipleMethodsForSameCallback;
   }

   public void setAllowsMultipleMethodsForSameCallback(boolean flag) {
      this._allowsMultipleMethodsForSameCallback = flag;
   }

   public void setDefaultAccessType(String type) {
      if (type != null) {
         if ("PROPERTY".equals(type.toUpperCase())) {
            this.setDefaultAccessType(4);
         } else {
            this.setDefaultAccessType(2);
         }

      }
   }

   public void populate(ClassMetaData meta, int access) {
      super.populate(meta, access);
      meta.setDetachable(true);
   }

   protected void populate(FieldMetaData fmd) {
      setCascadeNone(fmd);
      setCascadeNone(fmd.getKey());
      setCascadeNone(fmd.getElement());
   }

   static void setCascadeNone(ValueMetaData vmd) {
      vmd.setCascadePersist(0);
      vmd.setCascadeRefresh(0);
      vmd.setCascadeAttach(0);
   }

   protected int getAccessType(ClassMetaData meta) {
      return this.getAccessType(meta.getDescribedType());
   }

   private int getAccessType(Class cls) {
      if (cls != null && cls != Object.class) {
         int access = 0;
         if (annotated((Field[])((Field[])AccessController.doPrivileged(J2DoPriv5Helper.getDeclaredFieldsAction(cls)))).size() > 0) {
            access |= 2;
         }

         if (annotated((Method[])((Method[])AccessController.doPrivileged(J2DoPriv5Helper.getDeclaredMethodsAction(cls)))).size() > 0 || cls.isInterface()) {
            access |= 4;
         }

         return this.getAccessType(cls.getSuperclass()) | access;
      } else {
         return 0;
      }
   }

   protected List getFieldAccessNames(ClassMetaData meta) {
      return annotated((Field[])((Field[])AccessController.doPrivileged(J2DoPriv5Helper.getDeclaredFieldsAction(meta.getDescribedType()))));
   }

   protected List getPropertyAccessNames(ClassMetaData meta) {
      return annotated((Method[])((Method[])AccessController.doPrivileged(J2DoPriv5Helper.getDeclaredMethodsAction(meta.getDescribedType()))));
   }

   private static List annotated(AnnotatedElement[] members) {
      List annotated = new ArrayList(members.length);

      for(int i = 0; i < members.length; ++i) {
         Annotation[] annos = (Annotation[])((Annotation[])AccessController.doPrivileged(J2DoPriv5Helper.getAnnotationsAction(members[i])));

         for(int j = 0; j < annos.length; ++j) {
            String name = annos[j].annotationType().getName();
            if ((name.startsWith("javax.persistence.") || name.startsWith("org.apache.openjpa.persistence.")) && !_ignoredAnnos.contains(name)) {
               annotated.add(members[i]);
            }
         }
      }

      return annotated;
   }

   protected boolean isDefaultPersistent(ClassMetaData meta, Member member, String name) {
      int mods = member.getModifiers();
      if (Modifier.isTransient(mods)) {
         return false;
      } else {
         if (member instanceof Method) {
            try {
               Method setter = (Method)AccessController.doPrivileged(J2DoPriv5Helper.getDeclaredMethodAction(meta.getDescribedType(), "set" + StringUtils.capitalize(name), new Class[]{((Method)member).getReturnType()}));
               if (setter == null && !this.isAnnotatedTransient(member)) {
                  this.logNoSetter(meta, name, (Exception)null);
                  return false;
               }
            } catch (Exception var6) {
               if (!this.isAnnotatedTransient(member)) {
                  this.logNoSetter(meta, name, var6);
               }

               return false;
            }
         }

         PersistenceStrategy strat = getPersistenceStrategy((FieldMetaData)null, member);
         return strat != null && strat != PersistenceStrategy.TRANSIENT;
      }
   }

   private boolean isAnnotatedTransient(Member member) {
      return member instanceof AnnotatedElement && (Boolean)AccessController.doPrivileged(J2DoPriv5Helper.isAnnotationPresentAction((AnnotatedElement)member, Transient.class));
   }

   private void logNoSetter(ClassMetaData meta, String name, Exception e) {
      Log log = meta.getRepository().getConfiguration().getLog("openjpa.MetaData");
      if (log.isWarnEnabled()) {
         log.warn(_loc.get("no-setter-for-getter", name, meta.getDescribedType().getName()));
      } else if (log.isTraceEnabled()) {
         log.warn(_loc.get("no-setter-for-getter", name, meta.getDescribedType().getName()), e);
      }

   }

   static {
      _strats.put(Basic.class, PersistenceStrategy.BASIC);
      _strats.put(ManyToOne.class, PersistenceStrategy.MANY_ONE);
      _strats.put(OneToOne.class, PersistenceStrategy.ONE_ONE);
      _strats.put(Embedded.class, PersistenceStrategy.EMBEDDED);
      _strats.put(EmbeddedId.class, PersistenceStrategy.EMBEDDED);
      _strats.put(OneToMany.class, PersistenceStrategy.ONE_MANY);
      _strats.put(ManyToMany.class, PersistenceStrategy.MANY_MANY);
      _strats.put(Persistent.class, PersistenceStrategy.PERS);
      _strats.put(PersistentCollection.class, PersistenceStrategy.PERS_COLL);
      _strats.put(PersistentMap.class, PersistenceStrategy.PERS_MAP);
      _ignoredAnnos.add(DetachedState.class.getName());
      _ignoredAnnos.add(PostLoad.class.getName());
      _ignoredAnnos.add(PostPersist.class.getName());
      _ignoredAnnos.add(PostRemove.class.getName());
      _ignoredAnnos.add(PostUpdate.class.getName());
      _ignoredAnnos.add(PrePersist.class.getName());
      _ignoredAnnos.add(PreRemove.class.getName());
      _ignoredAnnos.add(PreUpdate.class.getName());
   }
}
