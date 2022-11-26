package weblogic.corba.idl;

import org.omg.CORBA.Any;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.UnionMember;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.TypeCodePackage.BadKind;
import org.omg.CORBA.TypeCodePackage.Bounds;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class TypeCodeImpl extends TypeCode {
   public static final int VM_NONE = 0;
   public static final int VM_CUSTOM = 1;
   public static final int VM_ABSTRACT = 2;
   public static final int VM_TRUNCATABLE = 3;
   private static final boolean DEBUG = false;
   private TCKind type;
   private RepositoryId repid;
   private String name;
   private TypeCode content_type;
   private MemberInfo[] members;
   private int length;
   private short mod;
   private short digits;
   private short scale;
   public static final TypeCodeImpl NULL_TC = new TypeCodeImpl(0);
   public static final TypeCodeImpl NULL;
   public static final TypeCodeImpl OCTET;
   public static final TypeCodeImpl STRING;
   public static final TypeCodeImpl OBJECT;
   public static final TypeCodeImpl VALUE;
   private static final TypeCodeImpl[] simpleTypes;

   public TypeCodeImpl(int tc, RepositoryId repid, String name, TypeCode content_type) {
      this.length = 0;
      this.mod = 0;
      this.digits = -1;
      this.scale = -1;
      this.repid = repid;
      this.name = name;
      this.type = TCKind.from_int(tc);
      this.content_type = content_type;
   }

   public TypeCodeImpl(int tc, RepositoryId repid, String name) {
      this(tc, repid, name, (TypeCode)null);
   }

   public TypeCodeImpl(int tc) {
      this.length = 0;
      this.mod = 0;
      this.digits = -1;
      this.scale = -1;
      this.repid = null;
      this.name = null;
      this.type = TCKind.from_int(tc);
   }

   public boolean equal(TypeCode tc) {
      if (!(tc instanceof TypeCodeImpl)) {
         return false;
      } else {
         TypeCodeImpl tcimpl = (TypeCodeImpl)tc;
         return tcimpl.type.value() == this.type.value() && tcimpl.length == this.length && tcimpl.mod == this.mod && tcimpl.digits == this.digits && tcimpl.scale == this.scale && safe_equals(tcimpl.repid, this.repid) && safe_equals(tcimpl.name, this.name) && safe_equals(tcimpl.content_type, this.content_type) && this.members_equal(tcimpl);
      }
   }

   public int hashCode() {
      int retVal = this.type == null ? 0 : this.type.hashCode();
      retVal ^= this.length;
      retVal ^= this.mod;
      retVal ^= this.digits;
      retVal ^= this.scale;
      retVal ^= this.repid == null ? 0 : this.repid.hashCode();
      retVal ^= this.name == null ? 0 : this.name.hashCode();
      retVal ^= this.content_type == null ? 0 : this.content_type.hashCode();
      if (this.members == null) {
         return retVal;
      } else {
         MemberInfo[] var2 = this.members;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            MemberInfo info = var2[var4];
            if (info == null) {
               retVal ^= 0;
            } else {
               retVal ^= info.hashCode();
            }
         }

         return retVal;
      }
   }

   public boolean equals(Object other) {
      return other instanceof TypeCode ? this.equal((TypeCode)other) : false;
   }

   private static final boolean safe_equals(Object obj1, Object obj2) {
      return obj1 == null && obj2 == null || obj1 != null && obj1.equals(obj2);
   }

   private final boolean members_equal(TypeCodeImpl tc) {
      if (tc.members == null && this.members == null) {
         return true;
      } else if (this.members != null && tc.members != null && this.members.length == tc.members.length) {
         for(int i = 0; i < this.members.length; ++i) {
            if (!safe_equals(this.members[i].name, tc.members[i].name) || !safe_equals(this.members[i].type, tc.members[i].type) || !safe_equals(this.members[i].label, tc.members[i].label) || this.members[i].access != tc.members[i].access) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean equivalent(TypeCode tc) {
      return this.equal(tc);
   }

   public TypeCode get_compact_typecode() {
      return simpleTypes[this.type.value()];
   }

   public TCKind kind() {
      return this.type;
   }

   public String id() throws BadKind {
      return this.repid == null ? "" : this.repid.toString();
   }

   public final RepositoryId getRepositoryId() {
      return this.repid;
   }

   public static final RepositoryId getRepositoryId(TypeCode type) {
      if (type instanceof TypeCodeImpl) {
         return ((TypeCodeImpl)type).getRepositoryId();
      } else {
         try {
            return new RepositoryId(type.id());
         } catch (BadKind var2) {
            return null;
         }
      }
   }

   public String name() throws BadKind {
      return this.name;
   }

   public int member_count() throws BadKind {
      return this.members == null ? 0 : this.members.length;
   }

   public String member_name(int index) throws BadKind, Bounds {
      if (this.members == null) {
         throw new BadKind();
      } else if (index >= this.members.length) {
         throw new Bounds();
      } else {
         return this.members[index].name;
      }
   }

   public TypeCode member_type(int index) throws BadKind, Bounds {
      if (this.members == null) {
         throw new BadKind();
      } else if (index >= this.members.length) {
         throw new Bounds();
      } else {
         return this.members[index].type;
      }
   }

   public Any member_label(int index) throws BadKind, Bounds {
      if (this.members == null) {
         throw new BadKind();
      } else if (index >= this.members.length) {
         throw new Bounds();
      } else {
         return this.members[index].label;
      }
   }

   public TypeCode discriminator_type() throws BadKind {
      if (this.content_type == null) {
         throw new BadKind();
      } else {
         return this.content_type;
      }
   }

   public int default_index() throws BadKind {
      if (this.type.value() != 16) {
         throw new BadKind();
      } else {
         return this.length;
      }
   }

   public int length() throws BadKind {
      if (this.length < 0) {
         throw new BadKind();
      } else {
         return this.length;
      }
   }

   public TypeCode content_type() throws BadKind {
      if (this.content_type == null) {
         throw new BadKind();
      } else {
         return this.content_type;
      }
   }

   public short fixed_digits() throws BadKind {
      if (this.digits == -1) {
         throw new BadKind();
      } else {
         return this.digits;
      }
   }

   public short fixed_scale() throws BadKind {
      if (this.scale == -1) {
         throw new BadKind();
      } else {
         return this.scale;
      }
   }

   public short member_visibility(int index) throws BadKind, Bounds {
      if (this.members == null) {
         throw new BadKind();
      } else if (index >= this.members.length) {
         throw new Bounds();
      } else {
         return this.members[index].access;
      }
   }

   public short type_modifier() throws BadKind {
      return this.mod;
   }

   public TypeCode concrete_base_type() throws BadKind {
      if (this.content_type == null) {
         throw new BadKind();
      } else {
         return this.content_type;
      }
   }

   public void read(CorbaInputStream in) {
      long handle;
      int count;
      int i;
      switch (this.type.value()) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 23:
         case 24:
         case 25:
         case 26:
            break;
         case 14:
         case 32:
            handle = in.startEncapsulation();
            this.repid = in.read_repository_id();
            this.name = in.read_string();
            in.endEncapsulation(handle);
            break;
         case 15:
         case 22:
            handle = in.startEncapsulation();
            this.repid = in.read_repository_id();
            this.name = in.read_string();
            count = in.read_ulong();
            this.members = new MemberInfo[count];

            for(i = 0; i < count; ++i) {
               this.members[i] = new MemberInfo();
               this.members[i].name = in.read_string();
               this.members[i].type = in.read_TypeCode();
            }

            in.endEncapsulation(handle);
            break;
         case 16:
            handle = in.startEncapsulation();
            this.repid = in.read_repository_id();
            this.name = in.read_string();
            this.content_type = in.read_TypeCode();
            this.length = in.read_long();
            count = in.read_ulong();
            this.members = new MemberInfo[count];

            for(i = 0; i < count; ++i) {
               this.members[i] = new MemberInfo();
               if (i == this.length) {
                  this.members[i].label = in.read_any(OCTET);
               } else {
                  this.members[i].label = in.read_any((TypeCodeImpl)this.content_type);
               }

               this.members[i].name = in.read_string();
               this.members[i].type = in.read_TypeCode();
            }

            in.endEncapsulation(handle);
            break;
         case 17:
            handle = in.startEncapsulation();
            this.repid = in.read_repository_id();
            this.name = in.read_string();
            count = in.read_ulong();
            this.members = new MemberInfo[count];

            for(i = 0; i < count; ++i) {
               this.members[i] = new MemberInfo();
               this.members[i].name = in.read_string();
            }

            in.endEncapsulation(handle);
            break;
         case 18:
         case 27:
            this.length = in.read_ulong();
            break;
         case 19:
         case 20:
            handle = in.startEncapsulation();
            this.content_type = in.read_TypeCode();
            this.length = in.read_ulong();
            in.endEncapsulation(handle);
            break;
         case 21:
            handle = in.startEncapsulation();
            this.repid = in.read_repository_id();
            this.name = in.read_string();
            this.content_type = in.read_TypeCode();
            in.endEncapsulation(handle);
            break;
         case 28:
            this.digits = in.read_short();
            this.scale = in.read_short();
            break;
         case 29:
            handle = in.startEncapsulation();
            this.repid = in.read_repository_id();
            this.name = in.read_string();
            this.mod = in.read_short();
            this.content_type = in.read_TypeCode();
            count = in.read_ulong();
            this.members = new MemberInfo[count];

            for(i = 0; i < count; ++i) {
               this.members[i] = new MemberInfo();
               this.members[i].name = in.read_string();
               this.members[i].type = in.read_TypeCode();
               this.members[i].access = in.read_short();
            }

            in.endEncapsulation(handle);
            break;
         case 30:
            handle = in.startEncapsulation();
            this.repid = in.read_repository_id();
            this.name = in.read_string();
            this.content_type = in.read_TypeCode();
            in.endEncapsulation(handle);
            break;
         case 31:
         default:
            throw new MARSHAL("Unknown tc: " + this.type.value());
      }

   }

   public void write(CorbaOutputStream out) {
      write(this, out);
   }

   public static void write(TypeCode type, CorbaOutputStream out) {
      out.write_long(type.kind().value());

      try {
         long handle;
         int count;
         int def;
         int i;
         switch (type.kind().value()) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 23:
            case 24:
            case 25:
            case 26:
               break;
            case 14:
            case 32:
               handle = out.startEncapsulation();
               out.write_string(type.id());
               out.write_string(type.name());
               out.endEncapsulation(handle);
               break;
            case 15:
            case 22:
               handle = out.startEncapsulation();
               out.write_string(type.id());
               out.write_string(type.name());
               count = type.member_count();
               out.write_ulong(count);

               for(i = 0; i < count; ++i) {
                  out.write_string(type.member_name(i));
                  out.write_TypeCode(type.member_type(i));
               }

               out.endEncapsulation(handle);
               break;
            case 16:
               handle = out.startEncapsulation();
               out.write_string(type.id());
               out.write_string(type.name());
               out.write_TypeCode(type.discriminator_type());
               def = type.default_index();
               out.write_long(def);
               count = type.member_count();
               out.write_ulong(count);

               for(i = 0; i < count; ++i) {
                  if (i == def) {
                     out.write_octet((byte)0);
                  } else {
                     out.write_any(type.member_label(i), type.discriminator_type());
                  }

                  out.write_string(type.member_name(i));
                  out.write_TypeCode(type.member_type(i));
               }

               out.endEncapsulation(handle);
               break;
            case 17:
               handle = out.startEncapsulation();
               out.write_string(type.id());
               out.write_string(type.name());
               count = type.member_count();
               out.write_ulong(count);

               for(i = 0; i < count; ++i) {
                  out.write_string(type.member_name(i));
               }

               out.endEncapsulation(handle);
               break;
            case 18:
            case 27:
               out.write_ulong(0);
               break;
            case 19:
            case 20:
               handle = out.startEncapsulation();
               out.write_TypeCode(type.content_type());
               out.write_ulong(type.length());
               out.endEncapsulation(handle);
               break;
            case 21:
               handle = out.startEncapsulation();
               out.write_string(type.id());
               out.write_string(type.name());
               out.write_TypeCode(type.content_type());
               out.endEncapsulation(handle);
               break;
            case 28:
               out.write_unsigned_short(type.fixed_digits());
               out.write_short(type.fixed_scale());
               break;
            case 29:
               handle = out.startEncapsulation();
               out.write_string(type.id());
               out.write_string(type.name());
               out.write_short(type.type_modifier());
               out.write_TypeCode(type.concrete_base_type());
               count = type.member_count();
               out.write_ulong(count);

               for(def = 0; def < count; ++def) {
                  out.write_string(type.member_name(def));
                  out.write_TypeCode(type.member_type(def));
                  out.write_short(type.member_visibility(def));
               }

               out.endEncapsulation(handle);
               break;
            case 30:
               handle = out.startEncapsulation();
               out.write_string(type.id());
               out.write_string(type.name());
               out.write_TypeCode(type.content_type());
               out.endEncapsulation(handle);
               break;
            case 31:
            default:
               throw new NO_IMPLEMENT("Unsupported TypeCode: " + type);
         }

      } catch (BadKind var7) {
         throw new MARSHAL("Invalid TypeCode: " + type);
      } catch (Bounds var8) {
         throw new MARSHAL("Invalid TypeCode: " + type);
      }
   }

   public static TypeCode get_primitive_tc(TCKind tcKind) {
      return simpleTypes[tcKind.value()];
   }

   public static TypeCode get_primitive_tc(int tcKind) {
      return simpleTypes[tcKind];
   }

   public static TypeCode create_struct_tc(int kind, String id, String name, StructMember[] members) {
      TypeCodeImpl tc = new TypeCodeImpl(kind, new RepositoryId(id), name);
      tc.members = new MemberInfo[members.length];

      for(int i = 0; i < tc.members.length; ++i) {
         tc.members[i] = new MemberInfo();
         tc.members[i].name = members[i].name;
         tc.members[i].type = members[i].type;
      }

      return tc;
   }

   public static TypeCode create_union_tc(String id, String name, TypeCode discriminator_type, UnionMember[] members) {
      TypeCodeImpl tc = new TypeCodeImpl(16, new RepositoryId(id), name, discriminator_type);
      tc.members = new MemberInfo[members.length];

      for(int i = 0; i < tc.members.length; ++i) {
         tc.members[i] = new MemberInfo();
         tc.members[i].label = members[i].label;
         tc.members[i].name = members[i].name;
         tc.members[i].type = members[i].type;
      }

      return tc;
   }

   public static TypeCode create_enum_tc(String id, String name, String[] members) {
      TypeCodeImpl tc = new TypeCodeImpl(17, new RepositoryId(id), name);
      tc.members = new MemberInfo[members.length];

      for(int i = 0; i < tc.members.length; ++i) {
         tc.members[i] = new MemberInfo();
         tc.members[i].name = members[i];
      }

      return tc;
   }

   public static TypeCode create_string_tc(int bound) {
      TypeCodeImpl tc = new TypeCodeImpl(18);
      tc.length = bound;
      return tc;
   }

   public static TypeCode create_wstring_tc(int bound) {
      TypeCodeImpl tc = new TypeCodeImpl(27);
      tc.length = bound;
      return tc;
   }

   public static TypeCode create_sequence_tc(int bound, TypeCode element_type) {
      TypeCodeImpl tc = new TypeCodeImpl(19);
      tc.length = bound;
      tc.content_type = element_type;
      return tc;
   }

   public static TypeCode create_array_tc(int length, TypeCode element_type) {
      TypeCodeImpl tc = new TypeCodeImpl(20);
      tc.length = length;
      tc.content_type = element_type;
      return tc;
   }

   public static TypeCode create_fixed_tc(short digits, short scale) {
      TypeCodeImpl tc = new TypeCodeImpl(28);
      tc.digits = digits;
      tc.scale = scale;
      return tc;
   }

   public static TypeCode create_value_tc(String id, String name, short type_modifier, TypeCode concrete_base, ValueMember[] members) {
      TypeCodeImpl tc = new TypeCodeImpl(29, new RepositoryId(id), name, concrete_base);
      tc.members = new MemberInfo[members.length];

      for(int i = 0; i < tc.members.length; ++i) {
         tc.members[i] = new MemberInfo();
         tc.members[i].access = members[i].access;
         tc.members[i].name = members[i].name;
         tc.members[i].type = members[i].type;
      }

      return tc;
   }

   public String toString() {
      return toString(this.type) + (this.repid == null ? "<simple>" : this.repid.toString());
   }

   public static String toString(TCKind kind) {
      String typename;
      switch (kind.value()) {
         case 0:
            typename = "_tk_null";
            break;
         case 1:
         case 2:
         case 4:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 12:
         case 13:
         case 17:
         case 25:
         case 26:
         case 28:
         case 31:
         default:
            typename = "" + kind.value();
            break;
         case 3:
            typename = "_tk_long";
            break;
         case 5:
            typename = "_tk_ulong";
            break;
         case 11:
            typename = "_tk_any";
            break;
         case 14:
            typename = "_tk_objref";
            break;
         case 15:
            typename = "_tk_struct";
            break;
         case 16:
            typename = "_tk_union";
            break;
         case 18:
            typename = "_tk_string";
            break;
         case 19:
            typename = "_tk_sequence";
            break;
         case 20:
            typename = "_tk_array";
            break;
         case 21:
            typename = "_tk_alias";
            break;
         case 22:
            typename = "_tk_except";
            break;
         case 23:
            typename = "_tk_longlong";
            break;
         case 24:
            typename = "_tk_ulonglong";
            break;
         case 27:
            typename = "_tk_wstring";
            break;
         case 29:
            typename = "_tk_value";
            break;
         case 30:
            typename = "_tk_value_box";
            break;
         case 32:
            typename = "_tk_abstract_interface";
      }

      return "TCKind<" + typename + ">: ";
   }

   private static void p(String s) {
      System.out.println("<TypeCodeImpl> " + s);
   }

   static {
      NULL = new TypeCodeImpl(32, RepositoryId.NULL, "");
      OCTET = new TypeCodeImpl(10);
      STRING = new TypeCodeImpl(30, RepositoryId.STRING, "", new TypeCodeImpl(27));
      OBJECT = new TypeCodeImpl(14, RepositoryId.OBJECT, "");
      VALUE = new TypeCodeImpl(29, RepositoryId.EMPTY, "", NULL_TC);
      simpleTypes = new TypeCodeImpl[]{NULL_TC, new TypeCodeImpl(1), new TypeCodeImpl(2), new TypeCodeImpl(3), new TypeCodeImpl(4), new TypeCodeImpl(5), new TypeCodeImpl(6), new TypeCodeImpl(7), new TypeCodeImpl(8), new TypeCodeImpl(9), OCTET, new TypeCodeImpl(11), new TypeCodeImpl(12), new TypeCodeImpl(13), OBJECT, new TypeCodeImpl(15), new TypeCodeImpl(16), new TypeCodeImpl(17), new TypeCodeImpl(18), new TypeCodeImpl(19), new TypeCodeImpl(20), new TypeCodeImpl(21), new TypeCodeImpl(22), new TypeCodeImpl(23), new TypeCodeImpl(24), new TypeCodeImpl(25), new TypeCodeImpl(26), new TypeCodeImpl(27), new TypeCodeImpl(28), VALUE, new TypeCodeImpl(30), new TypeCodeImpl(31), new TypeCodeImpl(32)};
   }

   private static final class MemberInfo {
      private String name;
      private TypeCode type;
      private short access;
      private Any label;

      private MemberInfo() {
      }

      // $FF: synthetic method
      MemberInfo(Object x0) {
         this();
      }
   }
}
