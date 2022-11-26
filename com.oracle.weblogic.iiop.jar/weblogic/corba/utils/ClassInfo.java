package weblogic.corba.utils;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotNull;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.Streamable;
import org.omg.CORBA.portable.ValueBase;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.PortableReplaceable;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.collections.ConcurrentHashSet;
import weblogic.utils.collections.ConcurrentWeakHashMap;
import weblogic.utils.io.ObjectStreamClass;

public final class ClassInfo {
   private static final boolean DEBUG = false;
   private RepositoryId repId;
   private RepositoryId localRepId;
   private RepositoryId[] repIdList;
   private Class theClass;
   private TypeDetails typeDetails;
   private ObjectStreamClass osc;
   private static Map classInfoMap = new ConcurrentWeakHashMap();
   private static Map repositoryIdMap = new ConcurrentHashMap();
   private static Set loggedNotFoundClasses = new ConcurrentHashSet();
   private static boolean useFullRepositoryIdList = false;

   public ClassInfo(RepositoryId repId, String codebase) {
      this.repId = repId;
      this.theClass = CorbaUtils.getClassFromID(repId, codebase);
      if (this.theClass != null) {
         this.computeLocalRepositoryId(repId);
         this.initStreamInfo();
      } else if (repId == null) {
         IIOPLogger.logClassNotFound("none", "<null>", codebase);
      } else if (!loggedNotFoundClasses.contains(repId)) {
         IIOPLogger.logClassNotFound(repId.getClassName(), repId.toString(), codebase);
         loggedNotFoundClasses.add(repId);
      }

   }

   private void computeLocalRepositoryId(RepositoryId repId) {
      repId.setClassLoader(this.theClass.getClassLoader());
      this.localRepId = new RepositoryId(this.theClass);
      if (this.localRepId.equals((Object)repId)) {
         this.localRepId = repId;
      }

   }

   ClassInfo(@NotNull Class theClass) {
      this.theClass = theClass;
      this.localRepId = this.repId = new RepositoryId(theClass);
      this.initStreamInfo();
   }

   private void initStreamInfo() {
      this.osc = ObjectStreamClass.lookup(this.theClass);
      if (this.isCreateFullRepositoryIdLists()) {
         this.repIdList = RepositoryId.getRepositoryIdList(this.theClass);
      }

   }

   private boolean isCreateFullRepositoryIdLists() {
      return this.osc != null && this.osc.isCustomMarshaled() && useFullRepositoryIdList;
   }

   public ObjectStreamClass getDescriptor() {
      return this.osc;
   }

   public Serializable writeReplace(Object obj) {
      ObjectStreamClass osc = this.osc;
      Class c = this.theClass;

      try {
         while(osc != null && osc.hasWriteReplace() && (obj = osc.writeReplace(obj)) != null && obj.getClass() != c) {
            c = obj.getClass();
            osc = ObjectStreamClass.lookup(c);
         }

         return (Serializable)obj;
      } catch (IOException var5) {
         throw (MARSHAL)(new MARSHAL(var5.getMessage())).initCause(var5);
      }
   }

   public Class forClass() {
      return this.theClass;
   }

   public RepositoryId getRepositoryId() {
      return this.repId;
   }

   public RepositoryId getLocalRepositoryId() {
      return this.localRepId;
   }

   public RepositoryId[] getRepositoryIdList() {
      return this.repIdList;
   }

   public static RepositoryId getRepositoryId(String repid) {
      ClassInfo info = findClassInfo(new RepositoryId(repid));
      return info.getRepositoryId();
   }

   public boolean isAbstractInterface() {
      return this.getTypeDetails().isAbstractInterface();
   }

   public boolean isPortableReplaceable() {
      return this.getTypeDetails().isPortableReplaceable();
   }

   public boolean isIDLEntity() {
      return this.getTypeDetails().isIDLEntity();
   }

   public boolean isStreamable() {
      return this.getTypeDetails().isStreamable();
   }

   public boolean isValueBase() {
      return this.getTypeDetails().isValueBase();
   }

   public boolean isString() {
      return this.getTypeDetails().isString();
   }

   public boolean equals(Object other) {
      return other instanceof ClassInfo && this.equals((ClassInfo)other);
   }

   private boolean equals(ClassInfo other) {
      return other.theClass == this.theClass && other.repId.equals((Object)this.repId);
   }

   public int hashCode() {
      return this.repId == null ? 0 : this.repId.hashCode();
   }

   public String toString() {
      return "ClassInfo[" + this.repId.toPrettyString() + " => " + this.theClass + ", " + this.localRepId + "]";
   }

   public static ClassInfo findClassInfo(RepositoryId repId) {
      return findClassInfo(repId, (String)null);
   }

   public static ClassInfo findClassInfo(RepositoryId repId, String codebase) {
      if (repId == null) {
         return null;
      } else {
         ClassInfoReference cir = (ClassInfoReference)repositoryIdMap.get(repId);
         ClassInfo value = getFromReference(cir);
         if (value != null) {
            return value;
         } else {
            String annotation = CorbaUtils.getAnnotation((ClassLoader)null);
            ClassInfo classInfo;
            if (repId.getAnnotation() == null && annotation != null) {
               repId = new RepositoryId(repId, annotation);
               cir = (ClassInfoReference)repositoryIdMap.get(repId);
               classInfo = getFromReference(cir);
               if (classInfo != null) {
                  if (classInfo.theClass == null) {
                     return classInfo;
                  }

                  if (classInfo.theClass.getClassLoader() == Thread.currentThread().getContextClassLoader()) {
                     return classInfo;
                  }

                  classInfoMap.remove(classInfo.theClass);
               }
            }

            classInfo = new ClassInfo(repId, codebase);
            if (classInfo.theClass != null) {
               repositoryIdMap.put(repId, new ClassInfoReference(classInfo));
            }

            if (classInfo.theClass != null && classInfoMap.get(classInfo.theClass) == null && (repId == classInfo.getLocalRepositoryId() || !classInfo.isValueType())) {
               classInfo.addToMap(classInfo.theClass);
            }

            return classInfo;
         }
      }
   }

   private static ClassInfo getFromReference(ClassInfoReference cir) {
      return cir == null ? null : (ClassInfo)cir.get();
   }

   private boolean isValueType() {
      if (!this.theClass.isPrimitive() && !CorbaUtils.isRemote(this.theClass) && !CorbaUtils.isARemote(this.theClass) && !IDLEntity.class.equals(this.theClass)) {
         if (this.theClass.isInterface() && !this.isAbstractInterface()) {
            return true;
         } else {
            return this.theClass.getComponentType() != null ? false : Serializable.class.isAssignableFrom(this.theClass);
         }
      } else {
         return false;
      }
   }

   public static @NotNull ClassInfo findClassInfo(Class theClass) {
      ClassInfoReference cir = (ClassInfoReference)classInfoMap.get(theClass);
      ClassInfo cinfo = getFromReference(cir);
      if (cinfo == null) {
         cinfo = new ClassInfo(theClass);
         repositoryIdMap.put(cinfo.repId, new ClassInfoReference(cinfo));
         cinfo.addToMap(theClass);
      }

      return cinfo;
   }

   private void addToMap(Class theClass) {
      classInfoMap.put(theClass, new ClassInfoReference(this));
   }

   public static void initialize(boolean fullRepid) {
      useFullRepositoryIdList = fullRepid;
   }

   public boolean isEnum() {
      return this.theClass.isEnum();
   }

   private TypeDetails getTypeDetails() {
      if (this.typeDetails == null) {
         this.typeDetails = this.createTypeDetails();
      }

      return this.typeDetails;
   }

   private synchronized TypeDetails createTypeDetails() {
      return this.typeDetails != null ? this.typeDetails : new TypeDetails(this.theClass);
   }

   private static void p(String msg) {
      System.out.println("<ClassInfo>: " + msg);
   }

   private static class ClassInfoReference extends WeakReference {
      private int hash;

      ClassInfoReference(ClassInfo key) {
         super(key);
         this.hash = key.hashCode();
      }

      public boolean equals(Object o) {
         if (!(o instanceof ClassInfoReference)) {
            return false;
         } else {
            boolean var10000;
            label31: {
               ClassInfoReference e = (ClassInfoReference)o;
               Object k1 = this.get();
               Object k2 = e.get();
               if (this.hash == e.hash) {
                  if (k1 == null) {
                     if (k2 == null) {
                        break label31;
                     }
                  } else if (k1.equals(k2)) {
                     break label31;
                  }
               }

               var10000 = false;
               return var10000;
            }

            var10000 = true;
            return var10000;
         }
      }

      public int hashCode() {
         return this.hash;
      }
   }

   private class TypeDetails {
      private boolean string;
      private boolean abstractInterface;
      private boolean portableReplaceable;
      private boolean idlEntity;
      private boolean streamable;
      private boolean valueBase;

      TypeDetails(Class theClass) {
         this.string = String.class.isAssignableFrom(theClass);
         this.abstractInterface = CorbaUtils.isAbstractInterface(theClass);
         this.portableReplaceable = PortableReplaceable.class.isAssignableFrom(theClass);
         this.idlEntity = IDLEntity.class.isAssignableFrom(theClass);
         this.streamable = Streamable.class.isAssignableFrom(theClass);
         this.valueBase = ValueBase.class.isAssignableFrom(theClass);
      }

      public boolean isString() {
         return this.string;
      }

      public boolean isAbstractInterface() {
         return this.abstractInterface;
      }

      public boolean isIDLEntity() {
         return this.idlEntity;
      }

      boolean isPortableReplaceable() {
         return this.portableReplaceable;
      }

      boolean isStreamable() {
         return this.streamable;
      }

      boolean isValueBase() {
         return this.valueBase;
      }
   }
}
