package weblogic.utils.classfile;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.expr.AssignStatement;
import weblogic.utils.classfile.expr.CatchExceptionExpression;
import weblogic.utils.classfile.expr.CompoundStatement;
import weblogic.utils.classfile.expr.Expression;
import weblogic.utils.classfile.expr.InvokeExpression;
import weblogic.utils.classfile.expr.InvokeStaticExpression;
import weblogic.utils.classfile.expr.LocalVariableExpression;
import weblogic.utils.classfile.expr.NewExpression;
import weblogic.utils.classfile.expr.ReturnStatement;
import weblogic.utils.classfile.expr.ThrowStatement;
import weblogic.utils.classfile.expr.TryCatchStatement;
import weblogic.utils.classfile.utils.CodeGenerator;

public class ClassFile extends CodeGenerator implements AttributeParent {
   private static final int ACC_SUPER = 32;
   private static final boolean DEBUG = false;
   private static final int DEFAULT_ACCESS = 49;
   private static final int VALID_ACCESS_FLAGS = 1585;
   private static final int MAGIC = -889275714;
   private static final int MAJOR_VERSION = 45;
   private static final int MINOR_VERSION = 3;
   private static final boolean debug = false;
   private static final boolean verbose = false;
   public static final boolean verboseRead = false;
   private int magic;
   private int minorVersion;
   private int majorVersion;
   protected ConstantPool cp;
   private int accessFlags;
   private CPClass thisClass;
   private CPClass superClass;
   private InterfaceTable interfaces;
   private FieldTable fields;
   private MethodTable methods;
   private AttributeTable attributes;
   private boolean classForNameMethodAlreadyAdded;

   public ClassFile() {
      this.classForNameMethodAlreadyAdded = false;
      this.magic = -889275714;
      this.minorVersion = 3;
      this.majorVersion = 45;
      this.accessFlags = 49;
      this.cp = new ConstantPool();
      this.interfaces = new InterfaceTable(this);
      this.fields = new FieldTable(this);
      this.methods = new MethodTable(this);
      this.attributes = new AttributeTable(this, this);
   }

   public ClassFile(String filename) throws IOException, BadBytecodesException {
      this(new File(filename));
   }

   public ClassFile(File f) throws IOException, BadBytecodesException {
      this.classForNameMethodAlreadyAdded = false;
      FileInputStream fis = new FileInputStream(f);
      DataInputStream dis = new DataInputStream(fis);
      this.read(dis);
      fis.close();
   }

   public ClassFile(InputStream in) throws IOException, BadBytecodesException {
      this.classForNameMethodAlreadyAdded = false;
      DataInputStream dis = new DataInputStream(in);
      this.read(dis);
      dis.close();
   }

   public ConstantPool getConstantPool() {
      return this.cp;
   }

   public int getAccessFlags() {
      return this.accessFlags;
   }

   public void setAccessFlags(int accessFlags) {
      if ((accessFlags & 512) != 0) {
         accessFlags |= 1024;
      }

      if ((accessFlags & 16) != 0 && (accessFlags & 1024) != 0) {
         throw new IllegalArgumentException("A class cannot be both final and abstract");
      } else {
         accessFlags &= 1585;
         this.accessFlags = accessFlags | 32;
      }
   }

   public boolean isInterface() {
      return (this.accessFlags & 512) != 0;
   }

   public CPClass getThisClass() {
      return this.thisClass;
   }

   public String getClassName() {
      return this.thisClass.getName();
   }

   public void setClassName(String name) {
      if (this.thisClass == null) {
         this.thisClass = this.cp.getClass(name.replace('.', '/'));
      }

      this.thisClass.setName(name);
   }

   public CPClass getSuperClass() {
      return this.superClass;
   }

   public String getSuperClassName() {
      return this.superClass.getName();
   }

   public void setSuperClassName(String superClassName) {
      if (this.superClass == null) {
         this.superClass = this.cp.getClass(superClassName.replace('.', '/'));
      }

      this.superClass.setName(superClassName);
   }

   public void addInterface(String name) {
      this.interfaces.addInterface(name);
   }

   public InterfaceTable getInterfaces() {
      return this.interfaces;
   }

   public void removeInterface(String name) {
      this.interfaces.removeInterface(name);
   }

   public boolean hasInterface(String name) {
      return this.interfaces.hasInterface(name);
   }

   public FieldInfo addField(String name, String descriptor, int modifiers) {
      String f = name + ":" + descriptor;
      FieldInfo fi = this.fields.getField(f);
      if (fi != null) {
         return fi;
      } else {
         fi = new FieldInfo(this, name, descriptor, modifiers);
         this.fields.addField(name, fi);
         return fi;
      }
   }

   public Iterator getFields() {
      return this.fields.getFields();
   }

   protected MethodInfo addMethod(String name, String descriptor, int modifiers) {
      MethodInfo mi = new MethodInfo(this, name, descriptor, modifiers);
      this.methods.addMethod(mi);
      return mi;
   }

   protected MethodInfo addMethod(Method method, int modifiers) {
      MethodInfo mi = new MethodInfo(this, method, modifiers);
      this.methods.addMethod(mi);
      return mi;
   }

   public Iterator getMethods() {
      return this.methods.getMethods();
   }

   public void addClassForNameMethod() {
      if (!this.classForNameMethodAlreadyAdded) {
         this.classForNameMethodAlreadyAdded = true;
         MethodInfo mi = this.addMethod("class$", "(Ljava/lang/String;)Ljava/lang/Class;", 8);
         mi.setSynthetic();
         CPMethodref meth = this.cp.getMethodref("java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
         CodeAttribute ca = mi.getCodeAttribute();
         Scope scope = ca.getMethodInfo().getScope();
         TryCatchStatement tryCatch = new TryCatchStatement();
         tryCatch.setBody(new ReturnStatement(new InvokeStaticExpression(meth, new Expression[]{scope.getParameter(1)})));
         CPClass clsNoClassDefFoundError = this.cp.getClass(NoClassDefFoundError.class);
         CPMethodref getMessage = this.cp.getMethodref("java/lang/Throwable", "getMessage", "()Ljava/lang/String;");
         CPMethodref constructor = this.cp.getMethodref(clsNoClassDefFoundError, "<init>", "(Ljava/lang/String;)V");
         LocalVariableExpression tmp = scope.createLocalVar(Type.OBJECT);
         CompoundStatement handler = new CompoundStatement();
         handler.add(new AssignStatement(tmp, new CatchExceptionExpression()));
         handler.add(new ThrowStatement(new NewExpression(constructor, new Expression[]{new InvokeExpression(getMessage, tmp, new Expression[0])})));
         tryCatch.addHandler("java/lang/ClassNotFoundException", handler);
         ca.setCode(tryCatch);
      }
   }

   public String getSourceFile() {
      attribute_info attrs = this.attributes.getAttribute("SourceFile");
      if (attrs != null) {
         SourceFile_attribute sourceFileAttr = (SourceFile_attribute)attrs;
         if (sourceFileAttr.sourcefile != null) {
            return sourceFileAttr.sourcefile.toString();
         }
      }

      return null;
   }

   public boolean equals(Object other) {
      if (!this.getClass().isInstance(other)) {
         return false;
      } else {
         String myName = this.thisClass.name.toString();
         String otherName = ((ClassFile)other).thisClass.name.toString();
         return myName.equals(otherName);
      }
   }

   public int hashCode() {
      return this.thisClass.name.toString().hashCode();
   }

   public String toString() {
      return "class " + this.thisClass.name;
   }

   public AttributeTable getAttributes() {
      return this.attributes;
   }

   public void read(DataInput in) throws IOException, BadBytecodesException {
      try {
         this.magic = in.readInt();
         this.minorVersion = in.readUnsignedShort();
         this.majorVersion = in.readUnsignedShort();
         this.cp = new ConstantPool();
         this.cp.read(in);
         this.accessFlags = in.readUnsignedShort();
         int thisClassIdx = in.readUnsignedShort();
         this.thisClass = this.cp.classAt(thisClassIdx);
         int superClassIdx = in.readUnsignedShort();
         this.superClass = this.cp.classAt(superClassIdx);
         if (this.superClass != null) {
         }

         this.interfaces = new InterfaceTable(this);
         this.interfaces.read(in);
         this.fields = new FieldTable(this);
         this.fields.read(in);
         this.methods = new MethodTable(this);
         this.methods.read(in);
         this.attributes = new AttributeTable(this, this);
         this.attributes.read(in);
      } catch (MalformedClassException var4) {
         throw new IOException(String.valueOf(var4));
      }
   }

   public int write(OutputStream out) throws IOException, BadBytecodesException {
      DataOutput dout = out instanceof DataOutput ? (DataOutput)out : new DataOutputStream(out);
      ((DataOutput)dout).writeInt(this.magic);
      ((DataOutput)dout).writeShort(this.minorVersion);
      ((DataOutput)dout).writeShort(this.majorVersion);
      this.cp.write((DataOutput)dout);
      ((DataOutput)dout).writeShort(this.accessFlags);
      ((DataOutput)dout).writeShort(this.thisClass.getIndex());
      ((DataOutput)dout).writeShort(this.superClass.getIndex());
      this.interfaces.write((DataOutput)dout);
      this.fields.write((DataOutput)dout);
      this.methods.write((DataOutput)dout);
      this.attributes.write((DataOutput)dout);
      return dout instanceof DataOutputStream ? ((DataOutputStream)dout).size() : -1;
   }

   public void dump() throws BadBytecodesException {
      this.dump(System.out);
   }

   public void dump(PrintStream out) throws BadBytecodesException {
      out.println("magic         = " + Integer.toHexString(this.magic));
      out.println("minorVersion = " + this.minorVersion);
      out.println("majorVersion = " + this.majorVersion);
      this.cp.dump(out);
      out.println("access_flags  = " + Modifier.toString(this.accessFlags));
      out.println("is_interface  = " + this.isInterface());
      out.println("this_class    = " + this.thisClass.name.getValue());
      out.println("super_class   = " + this.superClass.name.getValue());
      this.interfaces.dump(out);
      this.fields.dump(out);
      this.methods.dump(out);
      this.attributes.dump(out);
   }

   public static void main(String[] argv) {
      DataInputStream dis = null;

      try {
         if (argv.length == 0) {
            System.err.println("args: filename");
            System.exit(-1);
         }

         String filename = argv[0];
         dis = new DataInputStream(new FileInputStream(filename));
         ClassFile classFile = new ClassFile();
         classFile.read(dis);
         System.out.println("************ START DUMP ************");
         classFile.dump(System.out);
         System.out.println("************  END DUMP  ************");
      } catch (Exception var12) {
         var12.printStackTrace();
      } finally {
         try {
            if (dis != null) {
               dis.close();
            }
         } catch (IOException var11) {
            var11.printStackTrace();
         }

      }

   }

   public static void say(String msg) {
      System.out.println(msg);
   }
}
