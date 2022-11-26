package jnr.ffi.provider.jffi;

import com.kenai.jffi.LastError;
import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jnr.ffi.NativeType;
import jnr.ffi.ObjectReferenceManager;
import jnr.ffi.Platform;
import jnr.ffi.Runtime;
import jnr.ffi.Type;
import jnr.ffi.TypeAlias;
import jnr.ffi.mapper.DefaultTypeMapper;
import jnr.ffi.mapper.SignatureTypeMapperAdapter;
import jnr.ffi.provider.AbstractRuntime;
import jnr.ffi.provider.BadType;
import jnr.ffi.provider.DefaultObjectReferenceManager;

public final class NativeRuntime extends AbstractRuntime {
   private final NativeMemoryManager mm;
   private final NativeClosureManager closureManager;
   private final Type[] aliases;

   private static ClassLoader getParentClassLoader() {
      ClassLoader cl = NativeRuntime.class.getClassLoader();
      if (cl == null) {
         cl = ClassLoader.getSystemClassLoader();
      }

      return cl;
   }

   public static NativeRuntime getInstance() {
      return NativeRuntime.SingletonHolder.INSTANCE;
   }

   private NativeRuntime() {
      super(ByteOrder.nativeOrder(), buildTypeMap());
      this.mm = new NativeMemoryManager(this);
      this.closureManager = new NativeClosureManager(this, new SignatureTypeMapperAdapter(new DefaultTypeMapper()), new AsmClassLoader(getParentClassLoader()));
      NativeType[] nativeAliases = buildNativeTypeAliases();
      EnumSet typeAliasSet = EnumSet.allOf(TypeAlias.class);
      this.aliases = new Type[typeAliasSet.size()];
      Iterator var3 = typeAliasSet.iterator();

      while(true) {
         while(var3.hasNext()) {
            TypeAlias alias = (TypeAlias)var3.next();
            if (nativeAliases.length > alias.ordinal() && nativeAliases[alias.ordinal()] != NativeType.VOID) {
               this.aliases[alias.ordinal()] = this.findType(nativeAliases[alias.ordinal()]);
            } else {
               this.aliases[alias.ordinal()] = new BadType(alias.name());
            }
         }

         return;
      }
   }

   private static EnumMap buildTypeMap() {
      EnumMap typeMap = new EnumMap(NativeType.class);
      EnumSet nativeTypes = EnumSet.allOf(NativeType.class);
      Iterator var2 = nativeTypes.iterator();

      while(var2.hasNext()) {
         NativeType t = (NativeType)var2.next();
         typeMap.put(t, jafflType(t));
      }

      return typeMap;
   }

   private static NativeType[] buildNativeTypeAliases() {
      Platform platform = Platform.getNativePlatform();
      Package pkg = NativeRuntime.class.getPackage();
      String cpu = platform.getCPU().toString();
      String os = platform.getOS().toString();
      EnumSet typeAliases = EnumSet.allOf(TypeAlias.class);
      NativeType[] aliases = new NativeType[0];

      try {
         Class cls = Class.forName(pkg.getName() + ".platform." + cpu + "." + os + ".TypeAliases");
         Field aliasesField = cls.getField("ALIASES");
         Map aliasMap = (Map)Map.class.cast(aliasesField.get(cls));
         aliases = new NativeType[typeAliases.size()];
         Iterator var9 = typeAliases.iterator();

         while(var9.hasNext()) {
            TypeAlias t = (TypeAlias)var9.next();
            aliases[t.ordinal()] = (NativeType)aliasMap.get(t);
            if (aliases[t.ordinal()] == null) {
               aliases[t.ordinal()] = NativeType.VOID;
            }
         }
      } catch (ClassNotFoundException var11) {
         Logger.getLogger(NativeRuntime.class.getName()).log(Level.SEVERE, "failed to load type aliases: " + var11);
      } catch (NoSuchFieldException var12) {
         Logger.getLogger(NativeRuntime.class.getName()).log(Level.SEVERE, "failed to load type aliases: " + var12);
      } catch (IllegalAccessException var13) {
         Logger.getLogger(NativeRuntime.class.getName()).log(Level.SEVERE, "failed to load type aliases: " + var13);
      }

      return aliases;
   }

   public Type findType(TypeAlias type) {
      return this.aliases[type.ordinal()];
   }

   public final NativeMemoryManager getMemoryManager() {
      return this.mm;
   }

   public NativeClosureManager getClosureManager() {
      return this.closureManager;
   }

   public ObjectReferenceManager newObjectReferenceManager() {
      return new DefaultObjectReferenceManager(this);
   }

   public int getLastError() {
      return LastError.getInstance().get();
   }

   public void setLastError(int error) {
      LastError.getInstance().set(error);
   }

   public boolean isCompatible(Runtime other) {
      return other instanceof NativeRuntime;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         NativeRuntime that = (NativeRuntime)o;
         return Arrays.equals(this.aliases, that.aliases) && this.closureManager.equals(that.closureManager) && this.mm.equals(that.mm);
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.mm.hashCode();
      result = 31 * result + this.closureManager.hashCode();
      result = 31 * result + Arrays.hashCode(this.aliases);
      return result;
   }

   private static Type jafflType(NativeType type) {
      switch (type) {
         case VOID:
            return new TypeDelegate(com.kenai.jffi.Type.VOID, NativeType.VOID);
         case SCHAR:
            return new TypeDelegate(com.kenai.jffi.Type.SCHAR, NativeType.SCHAR);
         case UCHAR:
            return new TypeDelegate(com.kenai.jffi.Type.UCHAR, NativeType.UCHAR);
         case SSHORT:
            return new TypeDelegate(com.kenai.jffi.Type.SSHORT, NativeType.SSHORT);
         case USHORT:
            return new TypeDelegate(com.kenai.jffi.Type.USHORT, NativeType.USHORT);
         case SINT:
            return new TypeDelegate(com.kenai.jffi.Type.SINT, NativeType.SINT);
         case UINT:
            return new TypeDelegate(com.kenai.jffi.Type.UINT, NativeType.UINT);
         case SLONG:
            return new TypeDelegate(com.kenai.jffi.Type.SLONG, NativeType.SLONG);
         case ULONG:
            return new TypeDelegate(com.kenai.jffi.Type.ULONG, NativeType.ULONG);
         case SLONGLONG:
            return new TypeDelegate(com.kenai.jffi.Type.SINT64, NativeType.SLONGLONG);
         case ULONGLONG:
            return new TypeDelegate(com.kenai.jffi.Type.UINT64, NativeType.ULONGLONG);
         case FLOAT:
            return new TypeDelegate(com.kenai.jffi.Type.FLOAT, NativeType.FLOAT);
         case DOUBLE:
            return new TypeDelegate(com.kenai.jffi.Type.DOUBLE, NativeType.DOUBLE);
         case ADDRESS:
            return new TypeDelegate(com.kenai.jffi.Type.POINTER, NativeType.ADDRESS);
         default:
            return new BadType(type.toString());
      }
   }

   // $FF: synthetic method
   NativeRuntime(Object x0) {
      this();
   }

   private static final class TypeDelegate extends Type {
      private final com.kenai.jffi.Type type;
      private final NativeType nativeType;

      public TypeDelegate(com.kenai.jffi.Type type, NativeType nativeType) {
         this.type = type;
         this.nativeType = nativeType;
      }

      public int alignment() {
         return this.type.alignment();
      }

      public int size() {
         return this.type.size();
      }

      public NativeType getNativeType() {
         return this.nativeType;
      }

      public String toString() {
         return this.type.toString();
      }
   }

   private static final class SingletonHolder {
      public static final NativeRuntime INSTANCE = new NativeRuntime();
   }
}
