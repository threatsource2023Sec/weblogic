package serp.bytecode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.JSRInlinerAdapter;
import serp.bytecode.lowlevel.ClassEntry;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;
import serp.util.Numbers;
import serp.util.Strings;

public class BCClass extends Annotated implements VisitAcceptor {
   private Project _project = null;
   private State _state = null;
   private ClassLoader _loader = null;

   BCClass(Project project) {
      this._project = project;
   }

   void setState(State state) {
      this._state = state;
   }

   void invalidate() {
      this._project = null;
      this._state = State.INVALID;
   }

   void read(File classFile, ClassLoader loader) throws IOException {
      InputStream in = new FileInputStream(classFile);

      try {
         this.read((InputStream)in, loader);
      } finally {
         in.close();
      }

   }

   void read(InputStream instream, ClassLoader loader) throws IOException {
      DataInput in = new DataInputStream(instream);
      this._state.setMagic(in.readInt());
      this._state.setMinorVersion(in.readUnsignedShort());
      this._state.setMajorVersion(in.readUnsignedShort());
      this._state.getPool().read(in);
      this._state.setAccessFlags(in.readUnsignedShort());
      this._state.setIndex(in.readUnsignedShort());
      this._state.setSuperclassIndex(in.readUnsignedShort());
      List interfaces = this._state.getInterfacesHolder();
      interfaces.clear();
      int interfaceCount = in.readUnsignedShort();

      for(int i = 0; i < interfaceCount; ++i) {
         interfaces.add(Numbers.valueOf(in.readUnsignedShort()));
      }

      List fields = this._state.getFieldsHolder();
      fields.clear();
      int fieldCount = in.readUnsignedShort();

      for(int i = 0; i < fieldCount; ++i) {
         BCField field = new BCField(this);
         fields.add(field);
         field.read(in);
      }

      List methods = this._state.getMethodsHolder();
      methods.clear();
      int methodCount = in.readUnsignedShort();

      for(int i = 0; i < methodCount; ++i) {
         BCMethod method = new BCMethod(this);
         methods.add(method);
         method.read(in);
      }

      this.readAttributes(in);
      this._loader = loader;
   }

   void read(Class type) throws IOException {
      int dotIndex = type.getName().lastIndexOf(46) + 1;
      String className = type.getName().substring(dotIndex);
      InputStream in = type.getResourceAsStream(className + ".class");

      try {
         this.read(in, type.getClassLoader());
      } finally {
         in.close();
      }

   }

   void read(BCClass orig) {
      try {
         ByteArrayInputStream in = new ByteArrayInputStream(orig.toByteArray());
         this.read((InputStream)in, orig.getClassLoader());
         in.close();
      } catch (IOException var3) {
         throw new RuntimeException(var3.toString());
      }
   }

   public void write() throws IOException {
      String name = this.getName();
      int dotIndex = name.lastIndexOf(46) + 1;
      name = name.substring(dotIndex);
      Class type = this.getType();
      OutputStream out = new FileOutputStream(URLDecoder.decode(type.getResource(name + ".class").getFile()));

      try {
         this.write((OutputStream)out);
      } finally {
         out.close();
      }

   }

   public void write(File classFile) throws IOException {
      OutputStream out = new FileOutputStream(classFile);

      try {
         this.write((OutputStream)out);
      } finally {
         out.close();
      }

   }

   public void write(OutputStream outstream) throws IOException {
      byte[] bytes = this.toByteArray();
      if (bytes != null) {
         DataOutput out = new DataOutputStream(outstream);
         out.write(bytes, 0, bytes.length);
      }
   }

   private void writeInternal(OutputStream outstream) throws IOException {
      DataOutput out = new DataOutputStream(outstream);
      out.writeInt(this._state.getMagic());
      out.writeShort(this._state.getMinorVersion());
      out.writeShort(this._state.getMajorVersion());
      this._state.getPool().write(out);
      out.writeShort(this._state.getAccessFlags());
      out.writeShort(this._state.getIndex());
      out.writeShort(this._state.getSuperclassIndex());
      List interfaces = this._state.getInterfacesHolder();
      out.writeShort(interfaces.size());
      Iterator itr = interfaces.iterator();

      while(itr.hasNext()) {
         out.writeShort(((Number)itr.next()).intValue());
      }

      List fields = this._state.getFieldsHolder();
      out.writeShort(fields.size());
      Iterator itr = fields.iterator();

      while(itr.hasNext()) {
         ((BCField)itr.next()).write(out);
      }

      List methods = this._state.getMethodsHolder();
      out.writeShort(methods.size());
      Iterator itr = methods.iterator();

      while(itr.hasNext()) {
         ((BCMethod)itr.next()).write(out);
      }

      this.writeAttributes(out);
   }

   public byte[] toByteArray() {
      ByteArrayOutputStream out = new ByteArrayOutputStream();

      byte[] var2;
      try {
         this.writeInternal(out);
         out.flush();
         var2 = this.addStackMapFrames(out.toByteArray());
      } catch (IOException var11) {
         throw new RuntimeException(var11.toString());
      } finally {
         try {
            out.close();
         } catch (IOException var10) {
         }

      }

      return var2;
   }

   public int getMagic() {
      return this._state.getMagic();
   }

   public void setMagic(int magic) {
      this._state.setMagic(magic);
   }

   public int getMajorVersion() {
      return this._state.getMajorVersion();
   }

   public void setMajorVersion(int majorVersion) {
      this._state.setMajorVersion(majorVersion);
   }

   public int getMinorVersion() {
      return this._state.getMinorVersion();
   }

   public void setMinorVersion(int minorVersion) {
      this._state.setMinorVersion(minorVersion);
   }

   public int getAccessFlags() {
      return this._state.getAccessFlags();
   }

   public void setAccessFlags(int access) {
      this._state.setAccessFlags(access);
   }

   public boolean isPublic() {
      return (this.getAccessFlags() & 1) > 0;
   }

   public void makePublic() {
      this.setAccessFlags(this.getAccessFlags() | 1);
   }

   public boolean isPackage() {
      return !this.isPublic();
   }

   public void makePackage() {
      this.setAccessFlags(this.getAccessFlags() & -2);
   }

   public boolean isFinal() {
      return (this.getAccessFlags() & 16) > 0;
   }

   public void setFinal(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 16);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -17);
      }

   }

   public boolean isInterface() {
      return (this.getAccessFlags() & 512) > 0;
   }

   public void setInterface(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 512);
         this.setAbstract(true);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -513);
      }

   }

   public boolean isAbstract() {
      return (this.getAccessFlags() & 1024) > 0;
   }

   public void setAbstract(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 1024);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -1025);
      }

   }

   public boolean isSynthetic() {
      return (this.getAccessFlags() & 4096) > 0;
   }

   public void setSynthetic(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 4096);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -4097);
      }

   }

   public boolean isAnnotation() {
      return (this.getAccessFlags() & 8192) > 0;
   }

   public void setAnnotation(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 8192);
         this.setAccessFlags(this.getAccessFlags() | 512);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -8193);
      }

   }

   public boolean isEnum() {
      return (this.getAccessFlags() & 16384) > 0;
   }

   public void setEnum(boolean on) {
      if (on) {
         this.setAccessFlags(this.getAccessFlags() | 16384);
      } else {
         this.setAccessFlags(this.getAccessFlags() & -16385);
      }

   }

   public boolean isPrimitive() {
      return this._state.isPrimitive();
   }

   public boolean isArray() {
      return this._state.isArray();
   }

   public int getIndex() {
      return this._state.getIndex();
   }

   public void setIndex(int index) {
      String oldName = this.getName();
      String newName = ((ClassEntry)this.getPool().getEntry(index)).getNameEntry().getValue();
      this.beforeRename(oldName, newName);
      this._state.setIndex(index);
   }

   public String getName() {
      return this._state.getName();
   }

   public String getClassName() {
      String name = this._project.getNameCache().getExternalForm(this.getName(), true);
      return name.substring(name.lastIndexOf(46) + 1);
   }

   public String getPackageName() {
      String name = this._project.getNameCache().getExternalForm(this.getName(), true);
      int index = name.lastIndexOf(46);
      return index == -1 ? null : name.substring(0, index);
   }

   public void setName(String name) {
      name = this._project.getNameCache().getExternalForm(name, false);
      String oldName = this.getName();
      int index = this.getIndex();
      if (index == 0) {
         index = this.getPool().findClassEntry(name, true);
      }

      ClassEntry entry = (ClassEntry)this.getPool().getEntry(index);
      this.beforeRename(oldName, name);
      int nameIndex = this.getPool().findUTF8Entry(this._project.getNameCache().getInternalForm(name, false), true);
      entry.setNameIndex(nameIndex);
      this._state.setIndex(index);
   }

   public Class getType() {
      return Strings.toClass(this.getName(), this.getClassLoader());
   }

   public String getComponentName() {
      return this._state.getComponentName();
   }

   public Class getComponentType() {
      String componentName = this.getComponentName();
      return componentName == null ? null : Strings.toClass(componentName, this.getClassLoader());
   }

   public BCClass getComponentBC() {
      String componentName = this.getComponentName();
      return componentName == null ? null : this.getProject().loadClass(componentName, this.getClassLoader());
   }

   public int getSuperclassIndex() {
      return this._state.getSuperclassIndex();
   }

   public void setSuperclassIndex(int index) {
      this._state.setSuperclassIndex(index);
   }

   public String getSuperclassName() {
      return this._state.getSuperclassName();
   }

   public Class getSuperclassType() {
      String name = this.getSuperclassName();
      return name == null ? null : Strings.toClass(name, this.getClassLoader());
   }

   public BCClass getSuperclassBC() {
      String name = this.getSuperclassName();
      return name != null && !this.getName().equals("java.lang.Object") ? this.getProject().loadClass(name, this.getClassLoader()) : null;
   }

   public void setSuperclass(String name) {
      if (name == null) {
         this.setSuperclassIndex(0);
      } else {
         this.setSuperclassIndex(this.getPool().findClassEntry(this._project.getNameCache().getInternalForm(name, false), true));
      }

   }

   public void setSuperclass(Class type) {
      if (type == null) {
         this.setSuperclass((String)null);
      } else {
         this.setSuperclass(type.getName());
      }

   }

   public void setSuperclass(BCClass type) {
      if (type == null) {
         this.setSuperclass((String)null);
      } else {
         this.setSuperclass(type.getName());
      }

   }

   public int[] getDeclaredInterfaceIndexes() {
      List interfaces = this._state.getInterfacesHolder();
      int[] indexes = new int[interfaces.size()];

      for(int i = 0; i < interfaces.size(); ++i) {
         indexes[i] = ((Number)interfaces.get(i)).intValue();
      }

      return indexes;
   }

   public void setDeclaredInterfaceIndexes(int[] interfaceIndexes) {
      List stateIndexes = this._state.getInterfacesHolder();
      stateIndexes.clear();

      for(int i = 0; i < interfaceIndexes.length; ++i) {
         Integer idx = Numbers.valueOf(interfaceIndexes[i]);
         if (!stateIndexes.contains(idx)) {
            stateIndexes.add(idx);
         }
      }

   }

   public String[] getDeclaredInterfaceNames() {
      int[] indexes = this.getDeclaredInterfaceIndexes();
      String[] names = new String[indexes.length];

      for(int i = 0; i < indexes.length; ++i) {
         ClassEntry entry = (ClassEntry)this.getPool().getEntry(indexes[i]);
         names[i] = this._project.getNameCache().getExternalForm(entry.getNameEntry().getValue(), false);
      }

      return names;
   }

   public Class[] getDeclaredInterfaceTypes() {
      String[] names = this.getDeclaredInterfaceNames();
      Class[] types = new Class[names.length];

      for(int i = 0; i < names.length; ++i) {
         types[i] = Strings.toClass(names[i], this.getClassLoader());
      }

      return types;
   }

   public BCClass[] getDeclaredInterfaceBCs() {
      String[] names = this.getDeclaredInterfaceNames();
      BCClass[] types = new BCClass[names.length];

      for(int i = 0; i < names.length; ++i) {
         types[i] = this.getProject().loadClass(names[i], this.getClassLoader());
      }

      return types;
   }

   public void setDeclaredInterfaces(String[] interfaces) {
      this.clearDeclaredInterfaces();
      if (interfaces != null) {
         for(int i = 0; i < interfaces.length; ++i) {
            this.declareInterface(interfaces[i]);
         }
      }

   }

   public void setDeclaredInterfaces(Class[] interfaces) {
      String[] names = null;
      if (interfaces != null) {
         names = new String[interfaces.length];

         for(int i = 0; i < interfaces.length; ++i) {
            names[i] = interfaces[i].getName();
         }
      }

      this.setDeclaredInterfaces(names);
   }

   public void setDeclaredInterfaces(BCClass[] interfaces) {
      String[] names = null;
      if (interfaces != null) {
         names = new String[interfaces.length];

         for(int i = 0; i < interfaces.length; ++i) {
            names[i] = interfaces[i].getName();
         }
      }

      this.setDeclaredInterfaces(names);
   }

   public String[] getInterfaceNames() {
      Collection allNames = new LinkedList();

      for(BCClass type = this; type != null; type = type.getSuperclassBC()) {
         String[] names = type.getDeclaredInterfaceNames();

         for(int i = 0; i < names.length; ++i) {
            allNames.add(names[i]);
         }
      }

      return (String[])((String[])allNames.toArray(new String[allNames.size()]));
   }

   public Class[] getInterfaceTypes() {
      Collection allTypes = new LinkedList();

      for(BCClass type = this; type != null; type = type.getSuperclassBC()) {
         Class[] types = type.getDeclaredInterfaceTypes();

         for(int i = 0; i < types.length; ++i) {
            allTypes.add(types[i]);
         }
      }

      return (Class[])((Class[])allTypes.toArray(new Class[allTypes.size()]));
   }

   public BCClass[] getInterfaceBCs() {
      Collection allTypes = new LinkedList();

      for(BCClass type = this; type != null; type = type.getSuperclassBC()) {
         BCClass[] types = type.getDeclaredInterfaceBCs();

         for(int i = 0; i < types.length; ++i) {
            allTypes.add(types[i]);
         }
      }

      return (BCClass[])((BCClass[])allTypes.toArray(new BCClass[allTypes.size()]));
   }

   public void clearDeclaredInterfaces() {
      this._state.getInterfacesHolder().clear();
   }

   public boolean removeDeclaredInterface(String name) {
      String[] names = this.getDeclaredInterfaceNames();
      Iterator itr = this._state.getInterfacesHolder().iterator();

      for(int i = 0; i < names.length; ++i) {
         itr.next();
         if (names[i].equals(name)) {
            itr.remove();
            return true;
         }
      }

      return false;
   }

   public boolean removeDeclaredInterface(Class type) {
      return type == null ? false : this.removeDeclaredInterface(type.getName());
   }

   public boolean removeDeclaredInterface(BCClass type) {
      return type == null ? false : this.removeDeclaredInterface(type.getName());
   }

   public void moveDeclaredInterface(int fromIdx, int toIdx) {
      if (fromIdx != toIdx) {
         List interfaces = this._state.getInterfacesHolder();
         Object o = interfaces.remove(fromIdx);
         interfaces.add(toIdx, o);
      }
   }

   public void declareInterface(String name) {
      Integer index = Numbers.valueOf(this.getPool().findClassEntry(this._project.getNameCache().getInternalForm(name, false), true));
      List interfaces = this._state.getInterfacesHolder();
      if (!interfaces.contains(index)) {
         interfaces.add(index);
      }

   }

   public void declareInterface(Class type) {
      this.declareInterface(type.getName());
   }

   public void declareInterface(BCClass type) {
      this.declareInterface(type.getName());
   }

   public boolean isInstanceOf(String name) {
      name = this._project.getNameCache().getExternalForm(name, false);
      String[] interfaces = this.getInterfaceNames();

      for(int i = 0; i < interfaces.length; ++i) {
         if (interfaces[i].equals(name)) {
            return true;
         }
      }

      for(BCClass type = this; type != null; type = type.getSuperclassBC()) {
         if (type.getName().equals(name)) {
            return true;
         }
      }

      return false;
   }

   public boolean isInstanceOf(Class type) {
      return type == null ? false : this.isInstanceOf(type.getName());
   }

   public boolean isInstanceOf(BCClass type) {
      return type == null ? false : this.isInstanceOf(type.getName());
   }

   public BCField[] getDeclaredFields() {
      List fields = this._state.getFieldsHolder();
      return (BCField[])((BCField[])fields.toArray(new BCField[fields.size()]));
   }

   public BCField getDeclaredField(String name) {
      BCField[] fields = this.getDeclaredFields();

      for(int i = 0; i < fields.length; ++i) {
         if (fields[i].getName().equals(name)) {
            return fields[i];
         }
      }

      return null;
   }

   public BCField[] getFields() {
      Collection allFields = new LinkedList();

      for(BCClass type = this; type != null; type = type.getSuperclassBC()) {
         BCField[] fields = type.getDeclaredFields();

         for(int i = 0; i < fields.length; ++i) {
            allFields.add(fields[i]);
         }
      }

      return (BCField[])((BCField[])allFields.toArray(new BCField[allFields.size()]));
   }

   public BCField[] getFields(String name) {
      List matches = new LinkedList();
      BCField[] fields = this.getFields();

      for(int i = 0; i < fields.length; ++i) {
         if (fields[i].getName().equals(name)) {
            matches.add(fields[i]);
         }
      }

      return (BCField[])((BCField[])matches.toArray(new BCField[matches.size()]));
   }

   public void setDeclaredFields(BCField[] fields) {
      this.clearDeclaredFields();
      if (fields != null) {
         for(int i = 0; i < fields.length; ++i) {
            this.declareField(fields[i]);
         }
      }

   }

   public BCField declareField(BCField field) {
      BCField newField = this.declareField(field.getName(), field.getTypeName());
      newField.setAccessFlags(field.getAccessFlags());
      newField.setAttributes(field.getAttributes());
      return newField;
   }

   public BCField declareField(String name, String type) {
      BCField field = new BCField(this);
      this._state.getFieldsHolder().add(field);
      field.initialize(name, this._project.getNameCache().getInternalForm(type, true));
      return field;
   }

   public BCField declareField(String name, Class type) {
      String typeName = type == null ? null : type.getName();
      return this.declareField(name, typeName);
   }

   public BCField declareField(String name, BCClass type) {
      String typeName = type == null ? null : type.getName();
      return this.declareField(name, typeName);
   }

   public void clearDeclaredFields() {
      List fields = this._state.getFieldsHolder();
      Iterator itr = fields.iterator();

      while(itr.hasNext()) {
         BCField field = (BCField)itr.next();
         itr.remove();
         field.invalidate();
      }

   }

   public boolean removeDeclaredField(String name) {
      List fields = this._state.getFieldsHolder();
      Iterator itr = fields.iterator();

      BCField field;
      do {
         if (!itr.hasNext()) {
            return false;
         }

         field = (BCField)itr.next();
      } while(!field.getName().equals(name));

      itr.remove();
      field.invalidate();
      return true;
   }

   public boolean removeDeclaredField(BCField field) {
      return field == null ? false : this.removeDeclaredField(field.getName());
   }

   public void moveDeclaredField(int fromIdx, int toIdx) {
      if (fromIdx != toIdx) {
         List fields = this._state.getFieldsHolder();
         Object o = fields.remove(fromIdx);
         fields.add(toIdx, o);
      }
   }

   public BCMethod[] getDeclaredMethods() {
      List methods = this._state.getMethodsHolder();
      return (BCMethod[])((BCMethod[])methods.toArray(new BCMethod[methods.size()]));
   }

   public BCMethod getDeclaredMethod(String name) {
      BCMethod[] methods = this.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equals(name)) {
            return methods[i];
         }
      }

      return null;
   }

   public BCMethod[] getDeclaredMethods(String name) {
      Collection matches = new LinkedList();
      BCMethod[] methods = this.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equals(name)) {
            matches.add(methods[i]);
         }
      }

      return (BCMethod[])((BCMethod[])matches.toArray(new BCMethod[matches.size()]));
   }

   public BCMethod getDeclaredMethod(String name, String[] paramTypes) {
      if (paramTypes == null) {
         paramTypes = new String[0];
      }

      BCMethod[] methods = this.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equals(name) && this.paramsMatch(methods[i], paramTypes)) {
            return methods[i];
         }
      }

      return null;
   }

   private boolean paramsMatch(BCMethod meth, String[] params) {
      String[] mparams = meth.getParamNames();
      if (mparams.length != params.length) {
         return false;
      } else {
         for(int i = 0; i < params.length; ++i) {
            if (!mparams[i].equals(this._project.getNameCache().getExternalForm(params[i], false))) {
               return false;
            }
         }

         return true;
      }
   }

   public BCMethod getDeclaredMethod(String name, Class[] paramTypes) {
      if (paramTypes == null) {
         return this.getDeclaredMethod(name, (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.getDeclaredMethod(name, paramNames);
      }
   }

   public BCMethod getDeclaredMethod(String name, BCClass[] paramTypes) {
      if (paramTypes == null) {
         return this.getDeclaredMethod(name, (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.getDeclaredMethod(name, paramNames);
      }
   }

   public BCMethod[] getDeclaredMethods(String name, String[] paramTypes) {
      if (paramTypes == null) {
         paramTypes = new String[0];
      }

      BCMethod[] methods = this.getDeclaredMethods();
      List matches = null;

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equals(name) && this.paramsMatch(methods[i], paramTypes)) {
            if (matches == null) {
               matches = new ArrayList(3);
            }

            matches.add(methods[i]);
         }
      }

      if (matches == null) {
         return new BCMethod[0];
      } else {
         return (BCMethod[])((BCMethod[])matches.toArray(new BCMethod[matches.size()]));
      }
   }

   public BCMethod[] getDeclaredMethods(String name, Class[] paramTypes) {
      if (paramTypes == null) {
         return this.getDeclaredMethods(name, (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.getDeclaredMethods(name, paramNames);
      }
   }

   public BCMethod[] getDeclaredMethods(String name, BCClass[] paramTypes) {
      if (paramTypes == null) {
         return this.getDeclaredMethods(name, (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.getDeclaredMethods(name, paramNames);
      }
   }

   public BCMethod getDeclaredMethod(String name, String returnType, String[] paramTypes) {
      if (paramTypes == null) {
         paramTypes = new String[0];
      }

      BCMethod[] methods = this.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equals(name) && methods[i].getReturnName().equals(this._project.getNameCache().getExternalForm(returnType, false)) && this.paramsMatch(methods[i], paramTypes)) {
            return methods[i];
         }
      }

      return null;
   }

   public BCMethod getDeclaredMethod(String name, Class returnType, Class[] paramTypes) {
      if (paramTypes == null) {
         return this.getDeclaredMethod(name, returnType.getName(), (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.getDeclaredMethod(name, returnType.getName(), paramNames);
      }
   }

   public BCMethod getDeclaredMethod(String name, BCClass returnType, BCClass[] paramTypes) {
      if (paramTypes == null) {
         return this.getDeclaredMethod(name, returnType.getName(), (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.getDeclaredMethod(name, returnType.getName(), paramNames);
      }
   }

   public BCMethod[] getMethods() {
      Collection allMethods = new LinkedList();

      for(BCClass type = this; type != null; type = type.getSuperclassBC()) {
         BCMethod[] methods = type.getDeclaredMethods();

         for(int i = 0; i < methods.length; ++i) {
            allMethods.add(methods[i]);
         }
      }

      return (BCMethod[])((BCMethod[])allMethods.toArray(new BCMethod[allMethods.size()]));
   }

   public BCMethod[] getMethods(String name) {
      Collection matches = new LinkedList();
      BCMethod[] methods = this.getMethods();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equals(name)) {
            matches.add(methods[i]);
         }
      }

      return (BCMethod[])((BCMethod[])matches.toArray(new BCMethod[matches.size()]));
   }

   public BCMethod[] getMethods(String name, String[] paramTypes) {
      if (paramTypes == null) {
         paramTypes = new String[0];
      }

      BCMethod[] methods = this.getMethods();
      Collection matches = new LinkedList();

      for(int i = 0; i < methods.length; ++i) {
         if (methods[i].getName().equals(name)) {
            String[] curParams = methods[i].getParamNames();
            if (curParams.length == paramTypes.length) {
               boolean match = true;

               for(int j = 0; j < paramTypes.length; ++j) {
                  if (!curParams[j].equals(this._project.getNameCache().getExternalForm(paramTypes[j], false))) {
                     match = false;
                     break;
                  }
               }

               if (match) {
                  matches.add(methods[i]);
               }
            }
         }
      }

      return (BCMethod[])((BCMethod[])matches.toArray(new BCMethod[matches.size()]));
   }

   public BCMethod[] getMethods(String name, Class[] paramTypes) {
      if (paramTypes == null) {
         return this.getMethods(name, (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.getMethods(name, paramNames);
      }
   }

   public BCMethod[] getMethods(String name, BCClass[] paramTypes) {
      if (paramTypes == null) {
         return this.getMethods(name, (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.getMethods(name, paramNames);
      }
   }

   public void setDeclaredMethods(BCMethod[] methods) {
      this.clearDeclaredMethods();
      if (methods != null) {
         for(int i = 0; i < methods.length; ++i) {
            this.declareMethod(methods[i]);
         }
      }

   }

   public BCMethod declareMethod(BCMethod method) {
      BCMethod newMethod = this.declareMethod(method.getName(), method.getReturnName(), method.getParamNames());
      newMethod.setAccessFlags(method.getAccessFlags());
      newMethod.setAttributes(method.getAttributes());
      return newMethod;
   }

   public BCMethod declareMethod(String name, String returnType, String[] paramTypes) {
      BCMethod method = new BCMethod(this);
      this._state.getMethodsHolder().add(method);
      method.initialize(name, this._project.getNameCache().getDescriptor(returnType, paramTypes));
      return method;
   }

   public BCMethod declareMethod(String name, Class returnType, Class[] paramTypes) {
      String[] paramNames = null;
      if (paramTypes != null) {
         paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }
      }

      String returnName = returnType == null ? null : returnType.getName();
      return this.declareMethod(name, returnName, paramNames);
   }

   public BCMethod declareMethod(String name, BCClass returnType, BCClass[] paramTypes) {
      String[] paramNames = null;
      if (paramTypes != null) {
         paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }
      }

      String returnName = returnType == null ? null : returnType.getName();
      return this.declareMethod(name, returnName, paramNames);
   }

   public void clearDeclaredMethods() {
      List methods = this._state.getMethodsHolder();
      Iterator itr = methods.iterator();

      while(itr.hasNext()) {
         BCMethod method = (BCMethod)itr.next();
         itr.remove();
         method.invalidate();
      }

   }

   public boolean removeDeclaredMethod(String name) {
      List methods = this._state.getMethodsHolder();
      Iterator itr = methods.iterator();

      BCMethod method;
      do {
         if (!itr.hasNext()) {
            return false;
         }

         method = (BCMethod)itr.next();
      } while(!method.getName().equals(name));

      itr.remove();
      method.invalidate();
      return true;
   }

   public boolean removeDeclaredMethod(BCMethod method) {
      return method == null ? false : this.removeDeclaredMethod(method.getName(), method.getParamNames());
   }

   public boolean removeDeclaredMethod(String name, String[] paramTypes) {
      if (paramTypes == null) {
         paramTypes = new String[0];
      }

      List methods = this._state.getMethodsHolder();
      Iterator itr = methods.iterator();

      boolean match;
      BCMethod method;
      do {
         String[] curParams;
         do {
            do {
               if (!itr.hasNext()) {
                  return false;
               }

               method = (BCMethod)itr.next();
            } while(!method.getName().equals(name));

            curParams = method.getParamNames();
         } while(curParams.length != paramTypes.length);

         match = true;

         for(int j = 0; j < paramTypes.length; ++j) {
            if (!curParams[j].equals(this._project.getNameCache().getExternalForm(paramTypes[j], false))) {
               match = false;
               break;
            }
         }
      } while(!match);

      itr.remove();
      method.invalidate();
      return true;
   }

   public boolean removeDeclaredMethod(String name, Class[] paramTypes) {
      if (paramTypes == null) {
         return this.removeDeclaredMethod(name, (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.removeDeclaredMethod(name, paramNames);
      }
   }

   public boolean removeDeclaredMethod(String name, BCClass[] paramTypes) {
      if (paramTypes == null) {
         return this.removeDeclaredMethod(name, (String[])null);
      } else {
         String[] paramNames = new String[paramTypes.length];

         for(int i = 0; i < paramTypes.length; ++i) {
            paramNames[i] = paramTypes[i].getName();
         }

         return this.removeDeclaredMethod(name, paramNames);
      }
   }

   public void moveDeclaredMethod(int fromIdx, int toIdx) {
      if (fromIdx != toIdx) {
         List methods = this._state.getMethodsHolder();
         Object o = methods.remove(fromIdx);
         methods.add(toIdx, o);
      }
   }

   public BCMethod addDefaultConstructor() {
      BCMethod method = this.getDeclaredMethod("<init>", (String[])null);
      if (method != null) {
         return method;
      } else {
         method = this.declareMethod("<init>", (Class)Void.TYPE, (Class[])null);
         Code code = method.getCode(true);
         code.setMaxStack(1);
         code.setMaxLocals(1);
         code.xload().setThis();
         code.invokespecial().setMethod((String)this.getSuperclassName(), "<init>", (String)"void", (String[])null);
         code.vreturn();
         return method;
      }
   }

   public SourceFile getSourceFile(boolean add) {
      SourceFile source = (SourceFile)this.getAttribute("SourceFile");
      return add && source == null ? (SourceFile)this.addAttribute("SourceFile") : source;
   }

   public boolean removeSourceFile() {
      return this.removeAttribute("SourceFile");
   }

   public NestHost getNestHost(boolean add) {
      NestHost host = (NestHost)this.getAttribute("NestHost");
      return add && host == null ? (NestHost)this.addAttribute("NestHost") : host;
   }

   public InnerClasses getInnerClasses(boolean add) {
      InnerClasses inner = (InnerClasses)this.getAttribute("InnerClasses");
      return add && inner == null ? (InnerClasses)this.addAttribute("InnerClasses") : inner;
   }

   public boolean removeInnerClasses() {
      return this.removeAttribute("InnerClasses");
   }

   public boolean isDeprecated() {
      return this.getAttribute("Deprecated") != null;
   }

   public void setDeprecated(boolean on) {
      if (!on) {
         this.removeAttribute("Deprecated");
      } else if (!this.isDeprecated()) {
         this.addAttribute("Deprecated");
      }

   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterBCClass(this);
      ConstantPool pool = null;

      try {
         pool = this._state.getPool();
      } catch (UnsupportedOperationException var6) {
      }

      if (pool != null) {
         pool.acceptVisit(visit);
      }

      BCField[] fields = this.getDeclaredFields();

      for(int i = 0; i < fields.length; ++i) {
         visit.enterBCMember(fields[i]);
         fields[i].acceptVisit(visit);
         visit.exitBCMember(fields[i]);
      }

      BCMethod[] methods = this.getDeclaredMethods();

      for(int i = 0; i < methods.length; ++i) {
         visit.enterBCMember(methods[i]);
         methods[i].acceptVisit(visit);
         visit.exitBCMember(methods[i]);
      }

      this.visitAttributes(visit);
      visit.exitBCClass(this);
   }

   public Project getProject() {
      return this._project;
   }

   public ConstantPool getPool() {
      return this._state.getPool();
   }

   public ClassLoader getClassLoader() {
      return this._loader != null ? this._loader : Thread.currentThread().getContextClassLoader();
   }

   public boolean isValid() {
      return this._project != null;
   }

   Collection getAttributesHolder() {
      return this._state.getAttributesHolder();
   }

   BCClass getBCClass() {
      return this;
   }

   private void beforeRename(String oldName, String newName) {
      if (this._project != null && oldName != null) {
         this._project.renameClass(oldName, newName, this);
      }

   }

   byte[] addStackMapFrames(byte[] inputClass) {
      if (inputClass != null && this.getMajorVersion() >= 51) {
         try {
            ClassReader cr = new ClassReader(inputClass);
            ClassWriter cw = new ClassWriter(2);
            ClassVisitor cv = new InliningClassVisitor(cw);
            cr.accept(cv, 4);
            return cw.toByteArray();
         } catch (Exception var5) {
            var5.printStackTrace();
            return inputClass;
         }
      } else {
         return inputClass;
      }
   }

   private static class InliningClassVisitor extends ClassVisitor implements Opcodes {
      private String className = null;

      InliningClassVisitor(ClassVisitor cv) {
         super(458752, cv);
      }

      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
         this.className = name;
         super.visit(version, access, name, signature, superName, interfaces);
      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         MethodVisitor mv = this.cv.visitMethod(access, name, desc, signature, exceptions);
         return mv == null ? null : new JSRInlinerAdapter(mv, access, name, desc, signature, exceptions);
      }
   }
}
