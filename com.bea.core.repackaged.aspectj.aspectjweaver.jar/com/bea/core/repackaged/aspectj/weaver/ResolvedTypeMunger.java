package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.SourceLocation;
import com.bea.core.repackaged.aspectj.util.TypeSafeEnum;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ResolvedTypeMunger {
   protected Kind kind;
   protected ResolvedMember signature;
   protected ResolvedMember declaredSignature;
   protected List typeVariableAliases;
   private Set superMethodsCalled = Collections.emptySet();
   private ISourceLocation location;
   private ResolvedType onType = null;
   public static final Kind Field = new Kind("Field", 1);
   public static final Kind Method = new Kind("Method", 2);
   public static final Kind Constructor = new Kind("Constructor", 5);
   public static final Kind PerObjectInterface = new Kind("PerObjectInterface", 3);
   public static final Kind PrivilegedAccess = new Kind("PrivilegedAccess", 4);
   public static final Kind Parent = new Kind("Parent", 6);
   public static final Kind PerTypeWithinInterface = new Kind("PerTypeWithinInterface", 7);
   public static final Kind AnnotationOnType = new Kind("AnnotationOnType", 8);
   public static final Kind MethodDelegate = new Kind("MethodDelegate", 9);
   public static final Kind FieldHost = new Kind("FieldHost", 10);
   public static final Kind MethodDelegate2 = new Kind("MethodDelegate2", 11);
   public static final Kind InnerClass = new Kind("InnerClass", 12);
   public static final String SUPER_DISPATCH_NAME = "superDispatch";

   public ResolvedTypeMunger(Kind kind, ResolvedMember signature) {
      this.kind = kind;
      this.signature = signature;
      UnresolvedType declaringType = signature != null ? signature.getDeclaringType() : null;
      if (declaringType != null) {
         if (declaringType.isRawType()) {
            throw new IllegalStateException("Use generic type, not raw type");
         }

         if (declaringType.isParameterizedType()) {
            throw new IllegalStateException("Use generic type, not parameterized type");
         }
      }

   }

   public void setSourceLocation(ISourceLocation isl) {
      this.location = isl;
   }

   public ISourceLocation getSourceLocation() {
      return this.location;
   }

   public boolean matches(ResolvedType matchType, ResolvedType aspectType) {
      if (this.onType == null) {
         this.onType = matchType.getWorld().resolve(this.getDeclaringType());
         if (this.onType.isRawType()) {
            this.onType = this.onType.getGenericType();
         }
      }

      if (!matchType.equals(this.onType)) {
         return this.onType.isInterface() ? matchType.isTopmostImplementor(this.onType) : false;
      } else {
         if (!this.onType.isExposedToWeaver()) {
            boolean ok = this.onType.isInterface() && this.onType.lookupMemberWithSupersAndITDs(this.getSignature()) != null;
            if (!ok && this.onType.getWeaverState() == null && matchType.getWorld().getLint().typeNotExposedToWeaver.isEnabled()) {
               matchType.getWorld().getLint().typeNotExposedToWeaver.signal(matchType.getName(), this.signature.getSourceLocation());
            }
         }

         return true;
      }
   }

   public String toString() {
      return "ResolvedTypeMunger(" + this.getKind() + ", " + this.getSignature() + ")";
   }

   public static ResolvedTypeMunger read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      Kind kind = ResolvedTypeMunger.Kind.read(s);
      if (kind == Field) {
         return NewFieldTypeMunger.readField(s, context);
      } else if (kind == Method) {
         return NewMethodTypeMunger.readMethod(s, context);
      } else if (kind == Constructor) {
         return NewConstructorTypeMunger.readConstructor(s, context);
      } else if (kind == MethodDelegate) {
         return MethodDelegateTypeMunger.readMethod(s, context, false);
      } else if (kind == FieldHost) {
         return MethodDelegateTypeMunger.FieldHostTypeMunger.readFieldHost(s, context);
      } else if (kind == MethodDelegate2) {
         return MethodDelegateTypeMunger.readMethod(s, context, true);
      } else if (kind == InnerClass) {
         return NewMemberClassTypeMunger.readInnerClass(s, context);
      } else {
         throw new RuntimeException("unimplemented");
      }
   }

   protected static Set readSuperMethodsCalled(VersionedDataInputStream s) throws IOException {
      Set ret = new HashSet();
      int n = true;
      int n;
      if (s.isAtLeast169()) {
         n = s.readByte();
      } else {
         n = s.readInt();
      }

      if (n < 0) {
         throw new BCException("Problem deserializing type munger");
      } else {
         for(int i = 0; i < n; ++i) {
            ret.add(ResolvedMemberImpl.readResolvedMember(s, (ISourceContext)null));
         }

         return ret;
      }
   }

   protected final void writeSuperMethodsCalled(CompressingDataOutputStream s) throws IOException {
      if (this.superMethodsCalled != null && this.superMethodsCalled.size() != 0) {
         List ret = new ArrayList(this.superMethodsCalled);
         Collections.sort(ret);
         int n = ret.size();
         s.writeByte(n);
         Iterator i$ = ret.iterator();

         while(i$.hasNext()) {
            ResolvedMember m = (ResolvedMember)i$.next();
            m.write(s);
         }

      } else {
         s.writeByte(0);
      }
   }

   protected static ISourceLocation readSourceLocation(VersionedDataInputStream s) throws IOException {
      if (s.getMajorVersion() < 2) {
         return null;
      } else {
         SourceLocation ret = null;
         ObjectInputStream ois = null;

         Object var4;
         try {
            byte b = false;
            byte b;
            boolean validLocation;
            if (s.isAtLeast169() && (b = s.readByte()) != 0) {
               validLocation = b == 2;
               if (validLocation) {
                  String path = s.readUtf8(s.readShort());
                  File f = new File(path);
                  ret = new SourceLocation(f, s.readInt());
                  int offset = s.readInt();
                  ret.setOffset(offset);
                  return ret;
               }
            } else {
               ois = new ObjectInputStream(s);
               validLocation = (Boolean)ois.readObject();
               if (validLocation) {
                  File f = (File)ois.readObject();
                  Integer ii = (Integer)ois.readObject();
                  Integer offset = (Integer)ois.readObject();
                  ret = new SourceLocation(f, ii);
                  ret.setOffset(offset);
                  return ret;
               }
            }

            return ret;
         } catch (EOFException var13) {
            var4 = null;
         } catch (IOException var14) {
            var14.printStackTrace();
            var4 = null;
            return (ISourceLocation)var4;
         } catch (ClassNotFoundException var15) {
            return ret;
         } finally {
            if (ois != null) {
               ois.close();
            }

         }

         return (ISourceLocation)var4;
      }
   }

   protected final void writeSourceLocation(CompressingDataOutputStream s) throws IOException {
      if (s.canCompress()) {
         s.writeByte(1 + (this.location == null ? 0 : 1));
         if (this.location != null) {
            s.writeCompressedPath(this.location.getSourceFile().getPath());
            s.writeInt(this.location.getLine());
            s.writeInt(this.location.getOffset());
         }
      } else {
         s.writeByte(0);
         ObjectOutputStream oos = new ObjectOutputStream(s);
         oos.writeObject(new Boolean(this.location != null));
         if (this.location != null) {
            oos.writeObject(this.location.getSourceFile());
            oos.writeObject(new Integer(this.location.getLine()));
            oos.writeObject(new Integer(this.location.getOffset()));
         }

         oos.flush();
         oos.close();
      }

   }

   public abstract void write(CompressingDataOutputStream var1) throws IOException;

   public Kind getKind() {
      return this.kind;
   }

   public void setSuperMethodsCalled(Set c) {
      this.superMethodsCalled = c;
   }

   public Set getSuperMethodsCalled() {
      return this.superMethodsCalled;
   }

   public ResolvedMember getSignature() {
      return this.signature;
   }

   public ResolvedMember getMatchingSyntheticMember(Member member, ResolvedType aspectType) {
      return this.getSignature() != null && this.getSignature().isPublic() && member.equals(this.getSignature()) ? this.getSignature() : null;
   }

   public boolean changesPublicSignature() {
      return this.kind == Field || this.kind == Method || this.kind == Constructor;
   }

   public boolean needsAccessToTopmostImplementor() {
      if (this.kind == Field) {
         return true;
      } else if (this.kind == Method) {
         return !this.signature.isAbstract();
      } else {
         return false;
      }
   }

   protected static List readInTypeAliases(VersionedDataInputStream s) throws IOException {
      if (s.getMajorVersion() >= 2) {
         int count = true;
         int count;
         if (s.isAtLeast169()) {
            count = s.readByte();
         } else {
            count = s.readInt();
         }

         if (count != 0) {
            List aliases = new ArrayList();

            for(int i = 0; i < count; ++i) {
               aliases.add(s.readUTF());
            }

            return aliases;
         }
      }

      return null;
   }

   protected final void writeOutTypeAliases(DataOutputStream s) throws IOException {
      if (this.typeVariableAliases != null && this.typeVariableAliases.size() != 0) {
         s.writeByte(this.typeVariableAliases.size());
         Iterator i$ = this.typeVariableAliases.iterator();

         while(i$.hasNext()) {
            String element = (String)i$.next();
            s.writeUTF(element);
         }
      } else {
         s.writeByte(0);
      }

   }

   public List getTypeVariableAliases() {
      return this.typeVariableAliases;
   }

   protected void setTypeVariableAliases(List typeVariableAliases) {
      this.typeVariableAliases = typeVariableAliases;
   }

   public boolean hasTypeVariableAliases() {
      return this.typeVariableAliases != null && this.typeVariableAliases.size() > 0;
   }

   public boolean sharesTypeVariablesWithGenericType() {
      return this.typeVariableAliases != null && this.typeVariableAliases.size() > 0;
   }

   public ResolvedTypeMunger parameterizedFor(ResolvedType target) {
      throw new BCException("Dont call parameterizedFor on a type munger of this kind: " + this.getClass());
   }

   public void setDeclaredSignature(ResolvedMember rm) {
      this.declaredSignature = rm;
   }

   public ResolvedMember getDeclaredSignature() {
      return this.declaredSignature;
   }

   public boolean isLateMunger() {
      return false;
   }

   public boolean existsToSupportShadowMunging() {
      return false;
   }

   public ResolvedTypeMunger parameterizeWith(Map m, World w) {
      throw new BCException("Dont call parameterizeWith() on a type munger of this kind: " + this.getClass());
   }

   public UnresolvedType getDeclaringType() {
      return this.getSignature().getDeclaringType();
   }

   public static class Kind extends TypeSafeEnum {
      Kind(String name, int key) {
         super(name, key);
      }

      public static Kind read(DataInputStream s) throws IOException {
         int key = s.readByte();
         switch (key) {
            case 1:
               return ResolvedTypeMunger.Field;
            case 2:
               return ResolvedTypeMunger.Method;
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            default:
               throw new BCException("bad kind: " + key);
            case 5:
               return ResolvedTypeMunger.Constructor;
            case 9:
               return ResolvedTypeMunger.MethodDelegate;
            case 10:
               return ResolvedTypeMunger.FieldHost;
            case 11:
               return ResolvedTypeMunger.MethodDelegate2;
            case 12:
               return ResolvedTypeMunger.InnerClass;
         }
      }

      public String toString() {
         return this.getName().startsWith(ResolvedTypeMunger.MethodDelegate.getName()) ? ResolvedTypeMunger.Method.toString() : super.toString();
      }
   }
}
