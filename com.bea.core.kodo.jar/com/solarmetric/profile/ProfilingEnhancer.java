package com.solarmetric.profile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.lang.exception.NestableRuntimeException;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.BytecodeWriter;
import org.apache.openjpa.lib.util.Files;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import serp.bytecode.BCClass;
import serp.bytecode.BCField;
import serp.bytecode.BCMember;
import serp.bytecode.BCMethod;
import serp.bytecode.Code;
import serp.bytecode.IfInstruction;
import serp.bytecode.Instruction;
import serp.bytecode.JumpInstruction;
import serp.bytecode.LocalVariable;
import serp.bytecode.LocalVariableTable;
import serp.bytecode.Project;
import serp.bytecode.ReturnInstruction;

public class ProfilingEnhancer {
   public static final int ENHANCE_NONE = 0;
   public static final int ENHANCE_PC = 2;
   private static final Localizer _loc = Localizer.forPackage(ProfilingEnhancer.class);
   private final Log _log;
   private final BCClass _bcclass;
   private final ProfilingClassMetaData _meta;
   private File _dir = null;
   private BytecodeWriter _writer = null;
   private String _descSep = "\n";

   public ProfilingEnhancer(Log log, ProfilingClassMetaData meta, ClassLoader loader) {
      this._meta = meta;
      this._log = log;
      this._bcclass = (new Project()).loadClass(this._meta.getType().getName(), loader);
   }

   public File getDirectory() {
      return this._dir;
   }

   public void setDirectory(File dir) {
      this._dir = dir;
   }

   public BCClass getBytecode() {
      return this._bcclass;
   }

   public Class getType() {
      return this._bcclass.getType();
   }

   public ProfilingClassMetaData getMetaData() {
      return this._meta;
   }

   public BytecodeWriter getBytecodeWriter() {
      return this._writer;
   }

   public void setBytecodeWriter(BytecodeWriter writer) {
      this._writer = writer;
   }

   public int enhance() {
      if (this._log.isInfoEnabled()) {
         this._log.info(_loc.get("enhance-start", this._bcclass.getType()));
      }

      Class[] interfaces = this._bcclass.getDeclaredInterfaceTypes();

      int i;
      for(i = 0; i < interfaces.length; ++i) {
         if (interfaces[i].getName().equals(ProfilingCapable.class.getName())) {
            if (this._log.isTraceEnabled()) {
               this._log.trace(_loc.get("pc-type", this._bcclass.getType()));
            }

            return 0;
         }
      }

      try {
         this.enhanceClass();

         for(i = 0; i < this._meta.methodCount(); ++i) {
            ProfilingMethodMetaData methodMeta = this._meta.getMethodMetaData(i);
            if (this._log.isTraceEnabled()) {
               this._log.trace(_loc.get("enhance-method", methodMeta.getName()));
            }

            this.enhanceMethod(methodMeta);
         }

         return 2;
      } catch (ProfilingEnhancerException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new ProfilingEnhancerException(this._bcclass.getName() + ": " + var5.toString(), var5);
      }
   }

   public static boolean implementsInterface(BCClass bcclass, Class interfaceType) {
      if (bcclass.getType().getName().equals(interfaceType.getName())) {
         return true;
      } else {
         BCClass[] bcinterfaces = bcclass.getDeclaredInterfaceBCs();

         for(int i = 0; i < bcinterfaces.length; ++i) {
            if (implementsInterface(bcinterfaces[i], interfaceType)) {
               return true;
            }
         }

         return false;
      }
   }

   public void enhanceMethod(ProfilingMethodMetaData methodMeta) throws Exception {
      BCMethod method = this._bcclass.getDeclaredMethod(methodMeta.getName(), methodMeta.getSignatureElements());
      if (method == null) {
         throw new ProfilingEnhancerException(_loc.get("bad-method-signature", methodMeta.getName(), Arrays.asList(methodMeta.getSignatureElements())));
      } else {
         Code code = method.getCode(false);
         if (code != null) {
            code.beforeFirst();
            if (code.hasNext()) {
               Instruction firstInstruction = code.next();
               code.afterLast();
               Instruction lastInstruction = code.previous();
               code.beforeFirst();
               int methodInfo = code.getNextLocalsIndex();
               code.constant().setNull();
               code.astore().setLocal(methodInfo);
               int localAgent = -1;
               int var10000 = this._meta.getAgentSource();
               ProfilingClassMetaData var10001 = this._meta;
               if (var10000 == 1) {
                  BCField agent = this._bcclass.getDeclaredField(this._meta.getAgentSourceSymbol());
                  if (!implementsInterface(agent.getTypeBC(), ProfilingAgent.class)) {
                     throw new ProfilingEnhancerException(_loc.get("bad-agent", this._meta.getAgentSourceSymbol()));
                  }
               } else {
                  localAgent = code.getNextLocalsIndex();
                  code.constant().setNull();
                  code.astore().setLocal(localAgent);
                  var10000 = this._meta.getAgentSource();
                  var10001 = this._meta;
                  IfInstruction ifins;
                  if (var10000 == 0) {
                     this.loadField(code, this._meta.getAgentSourceSymbol());
                     code.isinstance().setType(ProfilingAgentProvider.class);
                     ifins = code.ifeq();
                     ifins.setTarget(firstInstruction);
                     this.loadField(code, this._meta.getAgentSourceSymbol());
                     code.checkcast().setType(ProfilingAgentProvider.class);
                     code.invokeinterface().setMethod(ProfilingAgentProvider.class, "getProfilingAgent", ProfilingAgent.class, new Class[0]);
                     code.astore().setLocal(localAgent);
                  } else {
                     this.loadArg(code, methodMeta.getAgentArg());
                     code.isinstance().setType(ProfilingAgentProvider.class);
                     ifins = code.ifeq();
                     ifins.setTarget(firstInstruction);
                     this.loadArg(code, methodMeta.getAgentArg());
                     code.checkcast().setType(ProfilingAgentProvider.class);
                     code.invokeinterface().setMethod(ProfilingAgentProvider.class, "getProfilingAgent", ProfilingAgent.class, new Class[0]);
                     code.astore().setLocal(localAgent);
                  }
               }

               var10000 = this._meta.getAgentSource();
               var10001 = this._meta;
               if (var10000 == 1) {
                  code.aload().setThis();
                  code.getfield().setField(this._bcclass.getDeclaredField(this._meta.getAgentSourceSymbol()));
               } else {
                  code.aload().setLocal(localAgent);
               }

               code.ifnull().setTarget(firstInstruction);
               boolean dynamicDescription = false;
               int descriptionLocal = -1;
               ProfilingPropertyMetaData[] props = methodMeta.getProperties();
               if (props.length > 0) {
                  descriptionLocal = code.getNextLocalsIndex();
                  dynamicDescription = true;
                  code.anew().setType(StringBuffer.class);
                  code.dup();
                  code.invokespecial().setMethod(StringBuffer.class, "<init>", Void.TYPE, new Class[0]);
                  if (methodMeta.getDescription() != null && methodMeta.getDescription().length() > 0) {
                     code.constant().setValue(methodMeta.getDescription() + this._descSep);
                     code.invokevirtual().setMethod(StringBuffer.class, "append", StringBuffer.class, new Class[]{String.class});
                  }

                  this.appendStrBuffer(code, props[0]);

                  for(int i = 1; i < props.length; ++i) {
                     code.constant().setValue(this._descSep);
                     code.invokevirtual().setMethod(StringBuffer.class, "append", StringBuffer.class, new Class[]{String.class});
                     this.appendStrBuffer(code, props[i]);
                  }
               }

               ProfilingArgMetaData[] describerArgs = methodMeta.getDescriberArgs();
               int throwableIndex;
               if (describerArgs.length > 0) {
                  if (!dynamicDescription) {
                     descriptionLocal = code.getNextLocalsIndex();
                     dynamicDescription = true;
                     code.anew().setType(StringBuffer.class);
                     code.dup();
                     code.invokespecial().setMethod(StringBuffer.class, "<init>", Void.TYPE, new Class[0]);
                     if (methodMeta.getDescription() != null && methodMeta.getDescription().length() > 0) {
                        code.constant().setValue(methodMeta.getDescription() + this._descSep);
                        code.invokevirtual().setMethod(StringBuffer.class, "append", StringBuffer.class, new Class[]{String.class});
                     }
                  } else {
                     code.constant().setValue(this._descSep);
                     code.invokevirtual().setMethod(StringBuffer.class, "append", StringBuffer.class, new Class[]{String.class});
                  }

                  this.appendStrBuffer(code, describerArgs[0]);

                  for(throwableIndex = 1; throwableIndex < describerArgs.length; ++throwableIndex) {
                     code.constant().setValue(this._descSep);
                     code.invokevirtual().setMethod(StringBuffer.class, "append", StringBuffer.class, new Class[]{String.class});
                     this.appendStrBuffer(code, describerArgs[throwableIndex]);
                  }
               }

               if (dynamicDescription) {
                  code.invokevirtual().setMethod(StringBuffer.class, "toString", String.class, new Class[0]);
                  code.astore().setLocal(descriptionLocal);
               }

               code.anew().setType(MethodInfoImpl.class);
               code.dup();
               code.constant().setValue(methodMeta.getProfileName());
               if (dynamicDescription) {
                  code.aload().setLocal(descriptionLocal);
               } else {
                  code.constant().setValue(methodMeta.getDescription());
               }

               code.invokespecial().setMethod(MethodInfoImpl.class, "<init>", Void.TYPE, new Class[]{String.class, String.class});
               code.astore().setLocal(methodInfo);
               if (methodMeta.getCategory() != null) {
                  code.aload().setLocal(methodInfo);
                  code.constant().setValue(methodMeta.getCategory());
                  code.invokevirtual().setMethod(MethodInfoImpl.class, "setCategory", Void.TYPE, new Class[]{String.class});
               }

               var10000 = this._meta.getAgentSource();
               var10001 = this._meta;
               if (var10000 == 1) {
                  code.aload().setThis();
                  code.getfield().setField(this._bcclass.getDeclaredField(this._meta.getAgentSourceSymbol()));
               } else {
                  code.aload().setLocal(localAgent);
               }

               code.anew().setType(MethodEnterEvent.class);
               code.dup();
               var10000 = this._meta.getEnvSource();
               var10001 = this._meta;
               if (var10000 == 1) {
                  if (this._meta.getEnvSourceSymbol().equals("this")) {
                     code.aload().setThis();
                  } else {
                     this.loadField(code, this._meta.getEnvSourceSymbol());
                  }
               } else {
                  this.loadArg(code, methodMeta.getEnvArg());
               }

               code.checkcast().setType(ProfilingEnvironment.class);
               code.aload().setLocal(methodInfo);
               code.invokespecial().setMethod(MethodEnterEvent.class, "<init>", Void.TYPE, new Class[]{ProfilingEnvironment.class, MethodInfo.class});
               code.invokeinterface().setMethod(ProfilingAgent.class, "handleEvent", Void.TYPE, new Class[]{ProfilingEvent.class});
               code.afterLast();
               throwableIndex = code.getNextLocalsIndex();
               code.astore().setLocal(throwableIndex);
               JumpInstruction jsrInstr = code.jsr();
               code.aload().setLocal(throwableIndex);
               code.athrow();
               int retAddr = code.getNextLocalsIndex();
               Instruction finallyInstr = code.astore().setLocal(retAddr);
               jsrInstr.setTarget(finallyInstr);
               var10000 = this._meta.getAgentSource();
               var10001 = this._meta;
               if (var10000 == 1) {
                  code.aload().setThis();
                  code.getfield().setField(this._bcclass.getDeclaredField(this._meta.getAgentSourceSymbol()));
               } else {
                  code.aload().setLocal(localAgent);
               }

               JumpInstruction ifnullInstr = code.ifnull();
               var10000 = this._meta.getAgentSource();
               var10001 = this._meta;
               if (var10000 == 1) {
                  code.aload().setThis();
                  code.getfield().setField(this._bcclass.getDeclaredField(this._meta.getAgentSourceSymbol()));
               } else {
                  code.aload().setLocal(localAgent);
               }

               code.anew().setType(MethodExitEvent.class);
               code.dup();
               var10000 = this._meta.getEnvSource();
               var10001 = this._meta;
               if (var10000 == 1) {
                  if (this._meta.getEnvSourceSymbol().equals("this")) {
                     code.aload().setThis();
                  } else {
                     this.loadField(code, this._meta.getEnvSourceSymbol());
                  }
               } else {
                  this.loadArg(code, methodMeta.getEnvArg());
               }

               code.checkcast().setType(ProfilingEnvironment.class);
               code.aload().setLocal(methodInfo);
               code.invokespecial().setMethod(MethodExitEvent.class, "<init>", Void.TYPE, new Class[]{ProfilingEnvironment.class, MethodInfo.class});
               code.invokeinterface().setMethod(ProfilingAgent.class, "handleEvent", Void.TYPE, new Class[]{ProfilingEvent.class});
               ifnullInstr.setTarget(code.ret().setLocal(retAddr));
               code.after(lastInstruction);
               code.addExceptionHandler(firstInstruction, lastInstruction, code.next(), (Class)null);
               code.before(firstInstruction);

               Instruction instr;
               do {
                  instr = code.next();
                  if (instr instanceof ReturnInstruction) {
                     ReturnInstruction retInstr = (ReturnInstruction)instr;
                     code.nop();
                     code.previous();
                     code.next();
                     ReturnInstruction newRetInstr = (ReturnInstruction)code.set(retInstr);
                     code.previous();
                     code.previous();
                     code.next();
                     if (retInstr.getType() == Void.TYPE) {
                        JumpInstruction newJsrInstr = (JumpInstruction)code.set((new Code()).jsr());
                        newJsrInstr.setTarget(finallyInstr);
                     } else {
                        int retVal = code.getNextLocalsIndex();
                        code.set((new Code()).xstore().setLocal(retVal).setType(newRetInstr.getType()));
                        code.jsr().setTarget(finallyInstr);
                        code.xload().setLocal(retVal).setType(newRetInstr.getType());
                     }

                     code.next();
                  }
               } while(instr != lastInstruction);

               code.calculateMaxLocals();
               code.calculateMaxStack();
            }
         }
      }
   }

   private void loadField(Code code, String symbol) {
      int idx = symbol.indexOf(46);
      BCField field;
      Method getter;
      if (idx == -1) {
         field = this._bcclass.getDeclaredField(symbol);
         getter = null;
      } else {
         field = this._bcclass.getDeclaredField(symbol.substring(0, idx));

         try {
            getter = field.getType().getMethod(symbol.substring(idx + 1), (Class[])null);
         } catch (Exception var7) {
            throw new ProfilingEnhancerException(symbol, var7);
         }
      }

      code.aload().setThis();
      code.getfield().setField(field);
      if (getter != null) {
         if (field.getType().isInterface()) {
            code.invokeinterface().setMethod(getter);
         } else {
            code.invokevirtual().setMethod(getter);
         }
      }

   }

   private void loadArg(Code code, ProfilingArgMetaData loadArg) throws Exception {
      LocalVariableTable locTable = code.getLocalVariableTable(true);
      int localIdx = code.getLocalsIndex(loadArg.getIndex());
      LocalVariable curParam = locTable.getLocalVariable(localIdx);
      code.xload().setLocal(localIdx).setType(curParam.getType());
   }

   private void appendStrBuffer(Code code, ProfilingArgMetaData describerArg) throws Exception {
      LocalVariableTable locTable = code.getLocalVariableTable(true);
      if (describerArg.getName() != null && describerArg.getName().length() > 0) {
         code.constant().setValue(describerArg.getName() + ": ");
         code.invokevirtual().setMethod(StringBuffer.class, "append", StringBuffer.class, new Class[]{String.class});
      }

      int localIdx = code.getLocalsIndex(describerArg.getIndex());
      LocalVariable curParam = locTable.getLocalVariable(localIdx);
      if (curParam == null) {
         String msg = null;
         if (code.getOwner() instanceof BCMember) {
            BCMember m = (BCMember)code.getOwner();
            msg = m.getDeclarer().getName() + "." + m.getName();
         }

         throw new NullPointerException(_loc.get("enhance-no-local", localIdx + "", msg, "" + locTable.getLocalVariables().length).getMessage());
      } else {
         ProfilingFormatterMetaData formatter = describerArg.getFormatter();
         if (formatter != null) {
            this.formatterPre(code, formatter);
            this.boxTypePre(code, curParam.getType());
         }

         code.xload().setLocal(localIdx).setType(curParam.getType());
         if (formatter != null) {
            this.boxTypePost(code, curParam.getType());
            this.formatterPost(code, formatter);
         } else {
            this.appendStrBuffer(code, curParam.getType());
         }

      }
   }

   private void appendStrBuffer(Code code, ProfilingPropertyMetaData prop) throws Exception {
      if (prop.getProfileName() != null && prop.getProfileName().length() > 0) {
         code.constant().setValue(prop.getProfileName() + ": ");
         code.invokevirtual().setMethod(StringBuffer.class, "append", StringBuffer.class, new Class[]{String.class});
      }

      ProfilingFormatterMetaData formatter = prop.getFormatter();
      if ("this".equals(prop.getName())) {
         if (formatter != null) {
            this.formatterPre(code, formatter);
         }

         code.aload().setThis();
         if (formatter != null) {
            this.formatterPost(code, formatter);
         } else {
            this.appendStrBuffer(code, Object.class);
         }
      } else {
         BCField propField = this._bcclass.getDeclaredField(prop.getName());
         if (propField == null) {
            throw new ProfilingEnhancerException(_loc.get("no-prop", prop.getName(), this._bcclass.getName()));
         }

         if (formatter != null) {
            this.formatterPre(code, formatter);
            this.boxTypePre(code, propField.getType());
         }

         code.aload().setThis();
         code.getfield().setField(propField);
         if (formatter != null) {
            this.boxTypePost(code, propField.getType());
            this.formatterPost(code, formatter);
         } else {
            this.appendStrBuffer(code, propField.getType());
         }
      }

   }

   private void formatterPre(Code code, ProfilingFormatterMetaData formatter) throws Exception {
      code.anew().setType(formatter.getClassName());
      code.dup();
      code.invokespecial().setMethod(formatter.getClassName(), "<init>", "void", (String[])null);
   }

   private void formatterPost(Code code, ProfilingFormatterMetaData formatter) throws Exception {
      code.invokevirtual().setMethod(formatter.getClassName(), formatter.getMethodName(), Object.class.getName(), new String[]{Object.class.getName()});
      this.appendStrBuffer(code, Object.class);
   }

   private void boxTypePre(Code code, Class type) {
      Class encapType = this.boxType(type);
      if (encapType != null) {
         code.anew().setType(encapType);
         code.dup();
      }

   }

   private void boxTypePost(Code code, Class type) {
      Class encapType = this.boxType(type);
      if (encapType != null) {
         code.invokespecial().setMethod(this.boxType(type), "<init>", Void.TYPE, new Class[]{type});
      }

   }

   private Class boxType(Class type) {
      if (type == Boolean.TYPE) {
         return Boolean.class;
      } else if (type == Character.TYPE) {
         return Character.class;
      } else if (type == Double.TYPE) {
         return Double.class;
      } else if (type == Float.TYPE) {
         return Float.class;
      } else if (type == Integer.TYPE) {
         return Integer.class;
      } else {
         return type == Long.TYPE ? Long.class : null;
      }
   }

   private void appendStrBuffer(Code code, Class type) {
      if (type != Boolean.TYPE && type != Character.TYPE && type != Double.TYPE && type != Float.TYPE && type != Integer.TYPE && type != Long.TYPE && type != String.class) {
         code.invokevirtual().setMethod(StringBuffer.class, "append", StringBuffer.class, new Class[]{Object.class});
      } else {
         code.invokevirtual().setMethod(StringBuffer.class, "append", StringBuffer.class, new Class[]{type});
      }

   }

   public void writeBytecode() throws IOException {
      if (this._writer != null) {
         this._writer.write(this._bcclass);
      } else if (this._dir == null) {
         this._bcclass.write();
      } else {
         File dir = Files.getPackageFile(this._dir, this._bcclass.getPackageName(), true);
         this._bcclass.write(new File(dir, this._bcclass.getClassName() + ".class"));
      }

   }

   private void enhanceClass() {
      this._bcclass.declareInterface(ProfilingCapable.class);
   }

   public static void main(String[] args) throws IOException {
      Options opts = new Options();
      args = opts.setFromCmdLine(args);
      if (!opts.containsKey("help") && !opts.containsKey("-help")) {
         File dir = Files.getFile(opts.removeProperty("directory", "d", (String)null), (ClassLoader)null);
         Configuration conf = new ConfigurationImpl();

         try {
            Configurations.populateConfiguration(conf, opts);
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            if (!run(conf, args, dir, loader, (BytecodeWriter)null)) {
               System.err.println(_loc.get("enhance-usage"));
            }
         } finally {
            conf.close();
         }

      } else {
         System.out.println(_loc.get("enhance-usage"));
      }
   }

   public static boolean run(Configuration conf, String[] args, File dir, ClassLoader parent, BytecodeWriter writer) throws IOException {
      if (args.length == 0) {
         return false;
      } else {
         Log log = ProfilingLog.get(conf);
         ClassLoader loader = new TemporaryClassLoader(parent);
         ProfilingMetaDataParser pmdp = new ProfilingMetaDataParser(log);
         pmdp.setClassLoader(loader);

         for(int i = 0; i < args.length; ++i) {
            File file = Files.getFile(args[i], loader);
            if (file.exists()) {
               try {
                  pmdp.setValidating(false);
                  pmdp.parse(file);
                  Collection metas = pmdp.getResults();
                  Iterator iter = metas.iterator();

                  while(iter.hasNext()) {
                     ProfilingClassMetaData meta = (ProfilingClassMetaData)iter.next();
                     log.info(_loc.get("enhance-running", meta.getType().getName()));
                     ProfilingEnhancer enhancer = new ProfilingEnhancer(log, meta, loader);
                     if (writer != null) {
                        enhancer.setBytecodeWriter(writer);
                     }

                     enhancer.setDirectory(dir);
                     int status = enhancer.enhance();
                     if (status == 2) {
                        enhancer.writeBytecode();
                     }
                  }
               } catch (IOException var15) {
                  throw new NestableRuntimeException(var15);
               }
            }
         }

         return true;
      }
   }

   private static class TemporaryClassLoader extends ClassLoader {
      public TemporaryClassLoader(ClassLoader parent) {
         super(parent);
      }

      public Class loadClass(String name) throws ClassNotFoundException {
         return this.loadClass(name, false);
      }

      protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
         if (!name.startsWith("java.") && !name.startsWith("sun.")) {
            String resourceName = name.replace('.', '/') + ".class";
            InputStream resource = this.getResourceAsStream(resourceName);
            if (resource == null) {
               throw new ClassNotFoundException(name);
            } else {
               ByteArrayOutputStream bout = new ByteArrayOutputStream();
               byte[] b = new byte[1024];

               try {
                  int n = false;

                  int n;
                  while((n = resource.read(b, 0, b.length)) != -1) {
                     bout.write(b, 0, n);
                  }

                  byte[] classBytes = bout.toByteArray();

                  try {
                     Class c = this.defineClass(name, classBytes, 0, classBytes.length);
                     return c;
                  } catch (SecurityException var9) {
                     return super.loadClass(name, resolve);
                  }
               } catch (IOException var10) {
                  return super.loadClass(name, resolve);
               }
            }
         } else {
            return Class.forName(name, false, this.getClass().getClassLoader());
         }
      }
   }
}
