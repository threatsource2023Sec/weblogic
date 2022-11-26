package oracle.jrockit.jfr.events;

import com.oracle.jrockit.jfr.DataType;
import com.oracle.jrockit.jfr.InvalidEventDefinitionException;
import com.sun.org.apache.bcel.internal.generic.ALOAD;
import com.sun.org.apache.bcel.internal.generic.ASTORE;
import com.sun.org.apache.bcel.internal.generic.ArrayType;
import com.sun.org.apache.bcel.internal.generic.BIPUSH;
import com.sun.org.apache.bcel.internal.generic.ClassGen;
import com.sun.org.apache.bcel.internal.generic.ConstantPoolGen;
import com.sun.org.apache.bcel.internal.generic.DLOAD;
import com.sun.org.apache.bcel.internal.generic.DSTORE;
import com.sun.org.apache.bcel.internal.generic.FLOAD;
import com.sun.org.apache.bcel.internal.generic.FSTORE;
import com.sun.org.apache.bcel.internal.generic.ILOAD;
import com.sun.org.apache.bcel.internal.generic.ISTORE;
import com.sun.org.apache.bcel.internal.generic.Instruction;
import com.sun.org.apache.bcel.internal.generic.InstructionConstants;
import com.sun.org.apache.bcel.internal.generic.InstructionFactory;
import com.sun.org.apache.bcel.internal.generic.InstructionHandle;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.org.apache.bcel.internal.generic.InvokeInstruction;
import com.sun.org.apache.bcel.internal.generic.LDC;
import com.sun.org.apache.bcel.internal.generic.LLOAD;
import com.sun.org.apache.bcel.internal.generic.LSTORE;
import com.sun.org.apache.bcel.internal.generic.MethodGen;
import com.sun.org.apache.bcel.internal.generic.ObjectType;
import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import com.sun.org.apache.bcel.internal.generic.Type;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;
import oracle.jrockit.jfr.JFRImpl;
import oracle.jrockit.jfr.StringConstantPool;

public class EventHandlerCreator {
   protected final ObjectType bufferType = new ObjectType(Buffer.class.getName());
   protected final ObjectType byteBufferType = new ObjectType(ByteBuffer.class.getName());
   protected final ObjectType jfrImplType = new ObjectType(JFRImpl.class.getName());
   protected final ObjectType eventDescType = new ObjectType(JavaEventDescriptor.class.getName());
   protected final ObjectType threadType = new ObjectType(Thread.class.getName());
   protected final ObjectType stringType = new ObjectType(String.class.getName());
   protected final ObjectType classType = new ObjectType(Class.class.getName());
   protected final ObjectType objectType = new ObjectType(Object.class.getName());
   protected final ArrayType poolsType = new ArrayType(StringConstantPool.class.getName(), 1);
   protected final String utilName = Bits.class.getName();
   protected final JFRImpl jfr;
   protected final String name;
   protected final JavaEventDescriptor descriptor;
   protected final Class receiverClass;
   protected final ObjectType receiverType;
   protected final Map pools;
   private final ArrayList usedPools = new ArrayList();
   private static final File classDir;
   private static final Object workaroundSync;
   InvokeInstruction put;
   InvokeInstruction putInt;
   InvokeInstruction putChar;
   InvokeInstruction putShort;
   InvokeInstruction putDouble;
   InvokeInstruction putFloat;
   InvokeInstruction putLong;

   public EventHandlerCreator(JFRImpl jfr, JavaEventDescriptor descriptor, Class receiverClass, Map pools) {
      this.name = "oracle.jrockit.jfr.events.GeneratedEventHandler" + descriptor.getId();
      this.descriptor = descriptor;
      this.receiverClass = receiverClass;
      this.receiverType = new ObjectType(receiverClass.getName());
      this.jfr = jfr;
      this.pools = pools;
   }

   public final byte[] generateBytes() {
      ClassGen cg = new ClassGen(this.name, EventHandlerImpl.class.getName(), "<generated>", 33, (String[])null);
      InstructionFactory factory = new InstructionFactory(cg);
      synchronized(workaroundSync) {
         this.generateConstructor(cg, factory);
         this.generateWrite(cg, factory);
      }

      return cg.getJavaClass().getBytes();
   }

   public final Class generateClass() {
      byte[] bytes = this.generateBytes();
      if (classDir != null) {
         try {
            File f = new File(classDir, this.getName().concat(".class"));
            DataOutputStream os = new DataOutputStream(new FileOutputStream(f));
            os.write(bytes);
            os.flush();
            os.close();
         } catch (IOException var4) {
         }
      }

      Class c = (new EventInfoClassLoader(this.descriptor.getEventClass().getClassLoader())).defineClass(this.getName(), bytes);
      return c.asSubclass(EventHandlerImpl.class);
   }

   public EventHandler createHandler() throws InvalidEventDefinitionException {
      Class ec = this.generateClass();

      Constructor cc;
      try {
         cc = ec.getConstructor(JFRImpl.class, JavaEventDescriptor.class, StringConstantPool[].class);
      } catch (Exception var5) {
         throw (Error)(new InternalError(this.descriptor.getName())).initCause(var5);
      }

      try {
         StringConstantPool[] p = this.usedPools.isEmpty() ? null : (StringConstantPool[])this.usedPools.toArray(new StringConstantPool[this.usedPools.size()]);
         return (EventHandler)cc.newInstance(this.jfr, this.descriptor, p);
      } catch (Throwable var4) {
         throw new InvalidEventDefinitionException(this.descriptor.getName(), var4);
      }
   }

   protected void generateConstructor(ClassGen cg, InstructionFactory factory) {
      ConstantPoolGen cp = cg.getConstantPool();
      InstructionList il = new InstructionList();
      MethodGen mg = new MethodGen(1, Type.VOID, new Type[]{this.jfrImplType, this.eventDescType, this.poolsType}, (String[])null, "<init>", cg.getClassName(), il, cp);
      il.append(new ALOAD(0));
      il.append(new ALOAD(1));
      il.append(new ALOAD(2));
      il.append(new ALOAD(3));
      il.append(factory.createInvoke(EventHandlerImpl.class.getName(), "<init>", Type.VOID, new Type[]{this.jfrImplType, this.eventDescType, this.poolsType}, (short)183));
      il.append(InstructionConstants.RETURN);
      mg.setMaxStack();
      mg.setMaxLocals();
      cg.addMethod(mg.getMethod());
      il.dispose();
   }

   private Instruction pushInt(ConstantPoolGen cp, int i) {
      if (i <= 5 && i >= -1) {
         return new BIPUSH((byte)i);
      } else {
         return (Instruction)(i >= -32768 && i < 32767 ? new SIPUSH((short)i) : new LDC(cp.addInteger(i)));
      }
   }

   private void generateWrite(ClassGen cg, InstructionFactory factory) {
      ConstantPoolGen cp = cg.getConstantPool();
      InstructionList il = new InstructionList();
      MethodGen mg = new MethodGen(17, Type.VOID, new Type[]{this.objectType, Type.LONG, Type.LONG}, (String[])null, "write", cg.getClassName(), il, cp);
      this.initPuts(factory);
      boolean hasVar = false;
      int value = 0;
      ALOAD loadThis = new ALOAD(value++);
      ALOAD loadEvent = new ALOAD(value++);
      LLOAD loadStart = new LLOAD(value++);
      ++value;
      LLOAD loadEnd = new LLOAD(value++);
      ++value;
      ILOAD loadSize = new ILOAD(value);
      ISTORE storeSize = new ISTORE(value++);
      ALOAD loadReceiver = new ALOAD(value);
      ASTORE storeReceiver = new ASTORE(value++);
      ALOAD loadJRA = new ALOAD(value);
      ASTORE storeJRA = new ASTORE(value++);
      ALOAD loadBuffer = new ALOAD(value);
      ASTORE storeBuffer = new ASTORE(value++);
      int staticLoad = 16;
      if (this.descriptor.hasStartTime()) {
         staticLoad += 8;
      }

      if (this.descriptor.hasThread()) {
         staticLoad += ContentTypeImpl.OSTHREAD.getType().getSize();
      }

      if (this.descriptor.hasStackTrace()) {
         staticLoad += ContentTypeImpl.STACKTRACE.getType().getSize();
      }

      il.append(loadEvent);
      il.append(factory.createCast(this.objectType, this.receiverType));
      il.append(storeReceiver);
      il.append(loadThis);
      il.append(factory.createGetField(EventHandlerImpl.class.getName(), "jfr", this.jfrImplType));
      il.append(factory.createCast(this.jfrImplType, new ObjectType(this.jfr.getClass().getName())));
      il.append(storeJRA);
      ValueDescriptor[] arr$ = this.descriptor.getValues();
      int trace = arr$.length;

      for(int i$ = 0; i$ < trace; ++i$) {
         ValueDescriptor v = arr$[i$];
         Class c = v.getValueType();
         boolean haspool = false;
         if (c == String.class) {
            String ps = v.getConstantPool();
            if (ps != null) {
               StringConstantPool p = (StringConstantPool)this.pools.get(ps);
               if (p != null) {
                  int cpindex;
                  for(cpindex = 0; cpindex < this.usedPools.size(); ++cpindex) {
                     StringConstantPool op = (StringConstantPool)this.usedPools.get(cpindex);
                     if (op == p) {
                        break;
                     }
                  }

                  if (cpindex == this.usedPools.size()) {
                     this.usedPools.add(p);
                  }

                  haspool = true;
                  il.append(loadThis);
                  il.append(factory.createGetField(EventHandlerImpl.class.getName(), "pools", this.poolsType));
                  il.append(this.pushInt(cp, cpindex));
                  il.append(InstructionConstants.AALOAD);
               }
            }
         }

         this.generateGetValue(cg, cp, il, factory, v, loadReceiver);
         if (c == String.class && haspool) {
            il.append(factory.createInvoke(StringConstantPool.class.getName(), "asConstant", Type.INT, new Type[]{Type.STRING}, (short)182));
            c = Integer.TYPE;
         }

         if (c == String.class) {
            il.append(InstructionConstants.DUP);
            il.append(new ASTORE(value++));
            il.append(factory.createInvoke(this.utilName, "length", Type.INT, new Type[]{this.stringType}, (short)184));
            if (hasVar) {
               il.append(loadSize);
               il.append(InstructionFactory.createBinaryOperation("+", Type.INT));
            }

            il.append(storeSize);
            staticLoad += 4;
            hasVar = true;
         } else if (c == Thread.class) {
            assert ContentTypeImpl.JAVATHREAD.getType() == DataType.LONG;

            il.append(factory.createInvoke(this.utilName, "threadID", Type.LONG, new Type[]{this.threadType}, (short)184));
            il.append(new LSTORE(value++));
            ++value;
            staticLoad += 8;
         } else if (c == Class.class) {
            il.append(loadJRA);
            il.append(InstructionConstants.SWAP);

            assert ContentTypeImpl.CLASS.getType() == DataType.U8;

            il.append(factory.createInvoke(this.jfr.getClass().getName(), "classID", Type.LONG, new Type[]{this.classType}, (short)182));
            il.append(new LSTORE(value++));
            ++value;
            staticLoad += 8;
         } else {
            Type t = this.typeOf(c);
            switch (t.getType()) {
               case 4:
               case 8:
                  il.append(new ISTORE(value++));
                  ++staticLoad;
                  break;
               case 5:
               case 9:
                  il.append(new ISTORE(value++));
                  staticLoad += 2;
                  break;
               case 6:
                  il.append(new FSTORE(value++));
                  staticLoad += 4;
                  break;
               case 7:
                  il.append(new DSTORE(value++));
                  ++value;
                  staticLoad += 8;
                  break;
               case 10:
                  il.append(new ISTORE(value++));
                  staticLoad += 4;
                  break;
               case 11:
                  il.append(new LSTORE(value++));
                  ++value;
                  staticLoad += 8;
                  break;
               default:
                  throw new InternalError(t.getSignature());
            }
         }
      }

      il.append(this.pushInt(cp, staticLoad));
      if (hasVar) {
         il.append(loadSize);
         il.append(InstructionFactory.createBinaryOperation("+", Type.INT));
      }

      il.append(storeSize);
      int thread = 0;
      trace = 0;
      if (this.descriptor.hasThread()) {
         thread = value++;
         il.append(loadJRA);
         il.append(factory.createInvoke(this.jfr.getClass().getName(), "threadID", Type.INT, Type.NO_ARGS, (short)182));
         il.append(new ISTORE(thread));
      }

      if (this.descriptor.hasStackTrace()) {
         trace = value++;
         ++value;
         il.append(loadThis);
         il.append(factory.createInvoke(EventHandlerImpl.class.getName(), "stackTraceID", Type.LONG, Type.NO_ARGS, (short)182));
         il.append(new LSTORE(trace));
      }

      il.append(loadJRA);
      il.append(loadSize);
      il.append(factory.createInvoke(JFRImpl.class.getName(), "getThreadBuffer", this.byteBufferType, new Type[]{Type.INT}, (short)182));
      il.append(InstructionConstants.DUP);
      InstructionHandle bufStart = il.append(storeBuffer);
      this.put(il, factory, Type.INT, loadSize);
      this.put(il, factory, Type.INT, this.pushInt(cp, this.descriptor.getId()));
      this.put(il, factory, Type.LONG, loadEnd);
      if (this.descriptor.hasStartTime()) {
         this.put(il, factory, Type.LONG, loadStart);
      }

      if (thread != 0) {
         this.put(il, factory, Type.INT, new ILOAD(thread));
      }

      if (trace != 0) {
         this.put(il, factory, Type.LONG, new LLOAD(trace));
      }

      value = value;
      ValueDescriptor[] arr$ = this.descriptor.getValues();
      int len$ = arr$.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         ValueDescriptor v = arr$[i$];
         Class c = v.getValueType();
         if (c == String.class) {
            String ps = v.getConstantPool();
            if (ps != null && this.pools.get(ps) != null) {
               value = this.put(il, factory, this.typeOf(Integer.TYPE), value);
            } else {
               this.putString(il, factory, new ALOAD(value++));
            }
         } else if (c != Thread.class && c != Class.class) {
            value = this.put(il, factory, this.typeOf(c), value);
         } else {
            this.put(il, factory, Type.LONG, new LLOAD(value++));
            ++value;
         }
      }

      InvokeInstruction release = factory.createInvoke(JFRImpl.class.getName(), "releaseThreadBuffer", Type.VOID, new Type[]{this.byteBufferType, Type.BOOLEAN}, (short)182);
      InstructionHandle end = il.getEnd();
      il.append(InstructionConstants.POP);
      il.append(loadJRA);
      il.append(loadBuffer);
      il.append(this.pushInt(cp, 1));
      il.append(release);
      il.append(InstructionConstants.RETURN);
      InstructionHandle handler = il.append(loadJRA);
      il.append(loadBuffer);
      il.append(this.pushInt(cp, 0));
      il.append(release);
      il.append(InstructionConstants.ATHROW);
      mg.addExceptionHandler(bufStart.getNext(), end, handler, (ObjectType)null);
      mg.setMaxStack();
      mg.setMaxLocals();
      cg.addMethod(mg.getMethod());
      il.dispose();
   }

   private void initPuts(InstructionFactory factory) {
      this.put = factory.createInvoke(ByteBuffer.class.getName(), "put", this.byteBufferType, new Type[]{Type.BYTE}, (short)182);
      this.putInt = factory.createInvoke(ByteBuffer.class.getName(), "putInt", this.byteBufferType, new Type[]{Type.INT}, (short)182);
      this.putLong = factory.createInvoke(ByteBuffer.class.getName(), "putLong", this.byteBufferType, new Type[]{Type.LONG}, (short)182);
      this.putChar = factory.createInvoke(ByteBuffer.class.getName(), "putChar", this.byteBufferType, new Type[]{Type.CHAR}, (short)182);
      this.putDouble = factory.createInvoke(ByteBuffer.class.getName(), "putDouble", this.byteBufferType, new Type[]{Type.DOUBLE}, (short)182);
      this.putFloat = factory.createInvoke(ByteBuffer.class.getName(), "putFloat", this.byteBufferType, new Type[]{Type.FLOAT}, (short)182);
      this.putShort = factory.createInvoke(ByteBuffer.class.getName(), "putShort", this.byteBufferType, new Type[]{Type.SHORT}, (short)182);
   }

   protected void put(InstructionList il, InstructionFactory factory, Type type, Instruction load) {
      il.append(load);
      switch (type.getType()) {
         case 4:
         case 8:
            il.append(this.put);
            break;
         case 5:
            il.append(this.putChar);
            break;
         case 6:
            il.append(this.putFloat);
            break;
         case 7:
            il.append(this.putDouble);
            break;
         case 9:
            il.append(this.putShort);
            break;
         case 10:
            il.append(this.putInt);
            break;
         case 11:
            il.append(this.putLong);
            break;
         default:
            throw new InternalError(type.getSignature());
      }

   }

   protected void putString(InstructionList il, InstructionFactory factory, Instruction load) {
      il.append(InstructionConstants.DUP);
      il.append(load);
      il.append(factory.createInvoke(this.utilName, "write", Type.VOID, new Type[]{this.byteBufferType, this.stringType}, (short)184));
   }

   private int put(InstructionList il, InstructionFactory factory, Type type, int var) {
      switch (type.getType()) {
         case 4:
         case 5:
         case 8:
         case 9:
         case 10:
            this.put(il, factory, type, new ILOAD(var));
            return var + 1;
         case 6:
            this.put(il, factory, type, new FLOAD(var));
            return var + 1;
         case 7:
            this.put(il, factory, type, new DLOAD(var));
            return var + 2;
         case 11:
            this.put(il, factory, type, new LLOAD(var));
            return var + 2;
         default:
            throw new InternalError(type.getSignature());
      }
   }

   private Type typeOf(Class c) {
      if (!c.isPrimitive()) {
         assert c == String.class || c == Thread.class || c == Class.class;

         return new ObjectType(c.getName());
      } else if (c == Integer.TYPE) {
         return Type.INT;
      } else if (c == Short.TYPE) {
         return Type.SHORT;
      } else if (c == Character.TYPE) {
         return Type.CHAR;
      } else if (c == Boolean.TYPE) {
         return Type.BOOLEAN;
      } else if (c == Long.TYPE) {
         return Type.LONG;
      } else if (c == Float.TYPE) {
         return Type.FLOAT;
      } else if (c == Double.TYPE) {
         return Type.DOUBLE;
      } else if (c == Byte.TYPE) {
         return Type.BYTE;
      } else {
         throw new InternalError(c.getName());
      }
   }

   private Type generateGetValue(ClassGen cg, ConstantPoolGen cp, InstructionList il, InstructionFactory factory, ValueDescriptor valueDesc, ALOAD loadReceiver) {
      Class valueType = valueDesc.getValueType();
      Field f = valueDesc.getField();
      Type t = this.typeOf(valueType);
      il.append(loadReceiver);
      if (f != null) {
         String name = this.receiverClass.getName();
         if (Modifier.isPublic(f.getModifiers())) {
            il.append(factory.createGetField(name, f.getName(), t));
            return t;
         }

         String fname = f.getName();
         fname = fname.substring(0, 1).toUpperCase() + fname.substring(1);

         Method reader;
         try {
            reader = f.getDeclaringClass().getMethod("get" + fname);
         } catch (Exception var14) {
            throw new IllegalArgumentException(f.getName(), var14);
         }

         il.append(factory.createInvoke(name, reader.getName(), t, Type.NO_ARGS, (short)182));
      } else {
         if (!(valueDesc instanceof DynamicValueDescriptor)) {
            throw new InternalError("Bad value type " + valueDesc);
         }

         DynamicValueDescriptor dv = (DynamicValueDescriptor)valueDesc;
         int index = dv.getIndex();
         il.append(this.pushInt(cp, index));
         il.append(InstructionConstants.AALOAD);
         if (valueType.isPrimitive()) {
            switch (t.getType()) {
               case 4:
                  il.append(factory.createInvoke(this.utilName, "booleanValue", Type.BOOLEAN, new Type[]{this.objectType}, (short)184));
                  break;
               case 5:
                  il.append(factory.createInvoke(this.utilName, "charValue", Type.CHAR, new Type[]{this.objectType}, (short)184));
                  break;
               case 6:
                  il.append(factory.createInvoke(this.utilName, "floatValue", Type.FLOAT, new Type[]{this.objectType}, (short)184));
                  break;
               case 7:
                  il.append(factory.createInvoke(this.utilName, "doubleValue", Type.DOUBLE, new Type[]{this.objectType}, (short)184));
                  break;
               case 8:
               case 9:
               case 10:
               default:
                  il.append(factory.createInvoke(this.utilName, "intValue", Type.INT, new Type[]{this.objectType}, (short)184));
                  break;
               case 11:
                  il.append(factory.createInvoke(this.utilName, "longValue", Type.LONG, new Type[]{this.objectType}, (short)184));
            }
         } else {
            il.append(factory.createCheckCast(new ObjectType(valueType.getName())));
         }
      }

      return t;
   }

   public String getName() {
      return this.name;
   }

   static {
      String s = System.getProperty("oracle.jrockit.jra.ClassGenDir");
      File f = null;
      if (s != null) {
         f = new File(s);
         if (!f.exists()) {
            f.mkdirs();
         }

         if (f.exists() && !f.isDirectory()) {
            throw new ExceptionInInitializerError(f + " is not a directory");
         }
      }

      classDir = f;
      workaroundSync = ClassGen.class;
   }

   private static final class EventInfoClassLoader extends ClassLoader {
      public EventInfoClassLoader(ClassLoader parent) {
         super(parent);
      }

      public Class defineClass(String name, byte[] bytes) {
         return super.defineClass(name, bytes, 0, bytes.length);
      }
   }
}
