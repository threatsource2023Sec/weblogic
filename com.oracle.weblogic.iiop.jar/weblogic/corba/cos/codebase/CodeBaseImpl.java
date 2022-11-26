package weblogic.corba.cos.codebase;

import java.io.ObjectStreamClass;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.omg.CORBA.AttributeDescription;
import org.omg.CORBA.Initializer;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.Object;
import org.omg.CORBA.OperationDescription;
import org.omg.CORBA.Repository;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.ValueMember;
import org.omg.CORBA.ValueDefPackage.FullValueDescription;
import org.omg.SendingContext._CodeBaseImplBase;
import weblogic.corba.idl.TypeCodeImpl;
import weblogic.corba.repository.SentClassesRepository;
import weblogic.corba.utils.ClassInfo;
import weblogic.corba.utils.CorbaUtils;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.IDLUtils;
import weblogic.iiop.Utils;
import weblogic.iiop.ValueHandlerImpl;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.server.ior.ServerIORFactory;
import weblogic.rmi.utils.io.Codebase;
import weblogic.utils.collections.ConcurrentHashMap;

public final class CodeBaseImpl extends _CodeBaseImplBase {
   private static final long serialVersionUID = 7568888264800197561L;
   public static final String TYPE_ID = "IDL:omg.org/SendingContext/CodeBase:1.0";
   private static final boolean DEBUG = false;
   private static final CodeBaseImpl codebase = new CodeBaseImpl();
   private static final IOR ior = ServerIORFactory.createWellKnownIor("IDL:omg.org/SendingContext/CodeBase:1.0", 12);
   private static final short PRIVATE_ACCESS = 0;
   private static final short PUBLIC_ACCESS = 1;
   private static ConcurrentHashMap metaMap = new ConcurrentHashMap();

   private CodeBaseImpl() {
   }

   public static CodeBaseImpl getCodeBase() {
      return codebase;
   }

   public IOR getIOR() {
      return ior;
   }

   public Repository get_ir() {
      return null;
   }

   public String implementation(String x) {
      return Codebase.getDefaultCodebase();
   }

   public String implementationx(String x) {
      return Codebase.getDefaultCodebase();
   }

   public String[] implementations(String[] x) {
      String[] result = new String[x.length];

      for(int i = 0; i < x.length; ++i) {
         result[i] = this.implementation(x[i]);
      }

      return result;
   }

   public FullValueDescription meta(String repositoryId) {
      FullValueDescription fvd;
      if ((fvd = (FullValueDescription)metaMap.get(repositoryId)) == null) {
         fvd = createFVD(SentClassesRepository.findClassInfo(repositoryId));
         metaMap.put(repositoryId, fvd);
         this.addParentClassDescriptors(SentClassesRepository.findClassInfo(repositoryId).forClass());
      }

      return fvd;
   }

   private void addParentClassDescriptors(Class c) {
      if (c != null && c.getSuperclass() != null && !c.getSuperclass().isInterface() && Serializable.class.isAssignableFrom(c.getSuperclass())) {
         while(c != null && !c.isInterface() && Serializable.class.isAssignableFrom(c)) {
            SentClassesRepository.findClassInfo(c);
            c = c.getSuperclass();
         }

      }
   }

   public FullValueDescription[] metas(String[] x) {
      FullValueDescription[] result = new FullValueDescription[x.length];

      for(int i = 0; i < x.length; ++i) {
         result[i] = this.meta(x[i]);
      }

      return result;
   }

   public String[] bases(String x) {
      throw new NO_IMPLEMENT();
   }

   private static TypeCode getTypeCode(Class c) {
      if (c == Byte.TYPE) {
         return TypeCodeImpl.get_primitive_tc(10);
      } else if (c == Character.TYPE) {
         return TypeCodeImpl.get_primitive_tc(26);
      } else if (c == Float.TYPE) {
         return TypeCodeImpl.get_primitive_tc(6);
      } else if (c == Double.TYPE) {
         return TypeCodeImpl.get_primitive_tc(7);
      } else if (c == Integer.TYPE) {
         return TypeCodeImpl.get_primitive_tc(3);
      } else if (c == Long.TYPE) {
         return TypeCodeImpl.get_primitive_tc(23);
      } else if (c == Short.TYPE) {
         return TypeCodeImpl.get_primitive_tc(2);
      } else if (c == Boolean.TYPE) {
         return TypeCodeImpl.get_primitive_tc(8);
      } else if (c.isArray()) {
         Class comp = c.getComponentType();
         TypeCode nestedType = getTypeCode(comp);
         return new TypeCodeImpl(30, new RepositoryId(ValueHandlerImpl.getRepositoryID(c)), "Sequence", TypeCodeImpl.create_sequence_tc(0, nestedType));
      } else if (c == String.class) {
         return new TypeCodeImpl(30, RepositoryId.STRING, "WStringValue", TypeCodeImpl.create_wstring_tc(0));
      } else {
         return (TypeCode)(!CorbaUtils.isARemote(c) && !Object.class.isAssignableFrom(c) ? new TypeCodeImpl(30, new RepositoryId(ValueHandlerImpl.getRepositoryID(c)), "ValueBox", TypeCodeImpl.get_primitive_tc(29)) : TypeCodeImpl.get_primitive_tc(14));
      }
   }

   private static FullValueDescription createFVD(ClassInfo cinfo) {
      Class c = cinfo.forClass();
      ObjectStreamClass osc = cinfo.getDescriptor().getObjectStreamClass();
      FullValueDescription fvd = new FullValueDescription();
      fvd.name = c.getName();
      int idx = fvd.name.lastIndexOf(46);
      if (idx >= 0) {
         fvd.name = fvd.name.substring(idx + 1);
      }

      fvd.id = cinfo.getRepositoryId().toString();
      fvd.is_abstract = cinfo.isAbstractInterface();
      fvd.is_custom = cinfo.getDescriptor().hasWriteObject();
      if (idx >= 0) {
         fvd.defined_in = "IDL:" + c.getName().substring(0, idx).replace('.', '/') + ":1.0";
      } else {
         fvd.defined_in = "IDL::1.0";
      }

      fvd.version = RepositoryId.toHexString(osc.getSerialVersionUID());
      fvd.operations = new OperationDescription[0];
      fvd.attributes = new AttributeDescription[0];
      fvd.initializers = new Initializer[0];
      ObjectStreamField[] fields = osc.getFields();
      fvd.members = new ValueMember[fields.length];

      for(int i = 0; i < fields.length; ++i) {
         ValueMember vm = new ValueMember();
         ObjectStreamField field = fields[i];
         Class type = field.getType();
         String typeName = type.getName();
         Field f = null;

         try {
            f = c.getField(field.getName());
         } catch (NoSuchFieldException | SecurityException var13) {
         }

         vm.name = field.getName();
         vm.id = ValueHandlerImpl.getRepositoryID(type);
         if ((idx = typeName.lastIndexOf(46)) >= 0) {
            vm.defined_in = "IDL:" + typeName.substring(0, idx).replace('.', '/') + ":1.0";
         } else {
            vm.defined_in = "IDL::1.0";
         }

         vm.version = "1.0";
         vm.type_def = null;
         if (f != null && Modifier.isPublic(f.getModifiers())) {
            vm.access = 1;
         } else {
            vm.access = 0;
         }

         vm.type = getTypeCode(type);
         fvd.members[i] = vm;
      }

      Class[] ifs = c.getInterfaces();
      int abstracts = 0;
      fvd.supported_interfaces = new String[ifs.length];

      int i;
      for(i = 0; i < ifs.length; ++i) {
         fvd.supported_interfaces[i] = ValueHandlerImpl.getRepositoryID(ifs[i]);
         if (Utils.isAbstractInterface(ifs[i])) {
            ++abstracts;
         }
      }

      fvd.abstract_base_values = new String[abstracts];
      i = ifs.length;

      while(true) {
         --i;
         if (i < 0) {
            Class sc = c.getSuperclass();
            if (IDLUtils.isValueType(sc)) {
               fvd.base_value = ValueHandlerImpl.getRepositoryID(sc);
               fvd.is_truncatable = true;
            } else {
               fvd.base_value = "";
            }

            fvd.type = TypeCodeImpl.get_primitive_tc(29);
            return fvd;
         }

         if (Utils.isAbstractInterface(ifs[i])) {
            --abstracts;
            fvd.abstract_base_values[abstracts] = ValueHandlerImpl.getRepositoryID(ifs[i]);
         }
      }
   }

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         System.out.println("weblogic.corba.cos.codebase.CodeBaseImpl <classname>");
      }

      String[] var1 = args;
      int var2 = args.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String arg = var1[var3];
         Class c = Class.forName(arg);
         FullValueDescription fvd = createFVD(ClassInfo.findClassInfo(c));
         System.out.println("FullValueDescription for " + c.getName() + ": ");
         System.out.println(" name:\t" + fvd.name);
         System.out.println(" id:\t" + fvd.id);
         System.out.println(" abstract:\t" + fvd.is_abstract);
         System.out.println(" custom:\t" + fvd.is_custom);
         System.out.println(" defined in:\t" + fvd.defined_in);
         System.out.println(" version:\t" + fvd.version);
         System.out.println(" base:\t" + fvd.base_value);
         System.out.println(" interfaces:\t");

         int i;
         for(i = 0; i < fvd.supported_interfaces.length; ++i) {
            System.out.println("   " + fvd.supported_interfaces[i]);
         }

         System.out.println(" abstract bases: ");

         for(i = 0; i < fvd.abstract_base_values.length; ++i) {
            System.out.println("   " + fvd.abstract_base_values[i]);
         }

         System.out.println(" fields: ");

         for(i = 0; i < fvd.members.length; ++i) {
            System.out.println("   name:\t" + fvd.members[i].name);
            System.out.println("   id:\t" + fvd.members[i].id);
            System.out.println("   access:\t" + fvd.members[i].access);
            System.out.println("   type:\t" + fvd.members[i].type);
         }
      }

   }

   protected static void p(String s) {
      System.err.println("<CodeBaseImpl> " + s);
   }
}
