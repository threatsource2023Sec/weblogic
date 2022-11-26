package org.python.compiler;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import javax.xml.bind.DatatypeConverter;
import org.python.antlr.ParseException;
import org.python.antlr.PythonTree;
import org.python.antlr.ast.Num;
import org.python.antlr.ast.Str;
import org.python.antlr.base.mod;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.CompilerFlags;
import org.python.core.ContainsPyBytecode;
import org.python.core.Py;
import org.python.core.PyBytecode;
import org.python.core.PyCode;
import org.python.core.PyComplex;
import org.python.core.PyException;
import org.python.core.PyFile;
import org.python.core.PyFloat;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;
import org.python.modules._marshal;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.Opcodes;
import org.python.objectweb.asm.Type;
import org.python.util.CodegenUtils;

public class Module implements Opcodes, ClassConstants, CompilationContext {
   ClassFile classfile;
   Constant filename;
   String sfilename;
   Constant mainCode;
   boolean linenumbers;
   Future futures;
   Hashtable scopes;
   List codes;
   long mtime;
   private int setter_count;
   private static final int USE_SETTERS_LIMIT = 100;
   private static final int MAX_SETTINGS_PER_SETTER = 4096;
   Hashtable constants;
   protected Hashtable oversized_methods;
   private static final int maxLiteral = 65535;

   public Module(String name, String filename, boolean linenumbers) {
      this(name, filename, linenumbers, -1L);
   }

   public Module(String name, String filename, boolean linenumbers, long mtime) {
      this.setter_count = 0;
      this.oversized_methods = null;
      this.linenumbers = linenumbers;
      this.mtime = mtime;
      this.classfile = new ClassFile(name, CodegenUtils.p(PyFunctionTable.class), 33, mtime);
      this.constants = new Hashtable();
      this.sfilename = filename;
      if (filename != null) {
         this.filename = this.stringConstant(filename);
      } else {
         this.filename = null;
      }

      this.codes = new ArrayList();
      this.futures = new Future();
      this.scopes = new Hashtable();
   }

   public Module(String name) {
      this(name, name + ".py", true, -1L);
   }

   private Constant findConstant(Constant c) {
      Constant ret = (Constant)this.constants.get(c);
      if (ret != null) {
         return ret;
      } else {
         c.module = this;
         c.name = "_" + this.constants.size();
         this.constants.put(c, c);
         return c;
      }
   }

   Constant integerConstant(int value) {
      return this.findConstant(new PyIntegerConstant(value));
   }

   Constant floatConstant(double value) {
      return this.findConstant(new PyFloatConstant(value));
   }

   Constant complexConstant(double value) {
      return this.findConstant(new PyComplexConstant(value));
   }

   Constant stringConstant(String value) {
      return this.findConstant(new PyStringConstant(value));
   }

   Constant unicodeConstant(String value) {
      return this.findConstant(new PyUnicodeConstant(value));
   }

   Constant longConstant(String value) {
      return this.findConstant(new PyLongConstant(value));
   }

   Constant codeConstant(mod tree, String name, boolean fast_locals, String className, boolean classBody, boolean printResults, int firstlineno, ScopeInfo scope, CompilerFlags cflags) throws Exception {
      return this.codeConstant(tree, name, fast_locals, className, (Str)null, classBody, printResults, firstlineno, scope, cflags);
   }

   Constant codeConstant(mod tree, String name, boolean fast_locals, String className, Str classDoc, boolean classBody, boolean printResults, int firstlineno, ScopeInfo scope, CompilerFlags cflags) throws Exception {
      if (this.oversized_methods != null && this.oversized_methods.containsKey(name + firstlineno)) {
         PyBytecodeConstant bcode = new PyBytecodeConstant((String)this.oversized_methods.get(name + firstlineno), className, cflags, this);
         this.classfile.addField(bcode.name, CodegenUtils.ci(PyCode.class), 9);
         return bcode;
      } else {
         PyCodeConstant code = new PyCodeConstant(tree, name, fast_locals, className, classBody, printResults, firstlineno, scope, cflags, this);
         this.codes.add(code);
         CodeCompiler compiler = new CodeCompiler(this, printResults);
         Code c = this.classfile.addMethod(code.fname, CodegenUtils.sig(PyObject.class, PyFrame.class, ThreadState.class), 1);
         compiler.parse(tree, c, fast_locals, className, classDoc, classBody, scope, cflags);
         return code;
      }
   }

   public void addInit() throws IOException {
      Code c = this.classfile.addMethod("<init>", CodegenUtils.sig(Void.TYPE, String.class), 1);
      c.aload(0);
      c.invokespecial(CodegenUtils.p(PyFunctionTable.class), "<init>", CodegenUtils.sig(Void.TYPE));
      this.addConstants(c);
   }

   public void addRunnable() throws IOException {
      Code c = this.classfile.addMethod("getMain", CodegenUtils.sig(PyCode.class), 1);
      this.mainCode.get(c);
      c.areturn();
   }

   public void addMain() throws IOException {
      Code c = this.classfile.addMethod("main", CodegenUtils.sig(Void.TYPE, String[].class), 9);
      c.new_(this.classfile.name);
      c.dup();
      c.ldc(this.classfile.name);
      c.invokespecial(this.classfile.name, "<init>", CodegenUtils.sig(Void.TYPE, String.class));
      c.invokevirtual(this.classfile.name, "getMain", CodegenUtils.sig(PyCode.class));
      c.invokestatic(CodegenUtils.p(CodeLoader.class), "createSimpleBootstrap", CodegenUtils.sig(CodeBootstrap.class, PyCode.class));
      c.aload(0);
      c.invokestatic(CodegenUtils.p(Py.class), "runMain", CodegenUtils.sig(Void.TYPE, CodeBootstrap.class, String[].class));
      c.return_();
   }

   public void addBootstrap() throws IOException {
      Code c = this.classfile.addMethod("getCodeBootstrap", CodegenUtils.sig(CodeBootstrap.class), 9);
      c.ldc(Type.getType("L" + this.classfile.name + ";"));
      c.invokestatic(CodegenUtils.p(PyRunnableBootstrap.class), "getFilenameConstructorReflectionBootstrap", CodegenUtils.sig(CodeBootstrap.class, Class.class));
      c.areturn();
   }

   void addConstants(Code c) throws IOException {
      this.classfile.addField("self", "L" + this.classfile.name + ";", 8);
      c.aload(0);
      c.putstatic(this.classfile.name, "self", "L" + this.classfile.name + ";");
      Enumeration e = this.constants.elements();

      while(e.hasMoreElements()) {
         Constant constant = (Constant)e.nextElement();
         constant.put(c);
      }

      Iterator var5 = this.codes.iterator();

      while(var5.hasNext()) {
         PyCodeConstant pyc = (PyCodeConstant)var5.next();
         pyc.put(c);
      }

      c.return_();
   }

   public void addFunctions() throws IOException {
      Code code = this.classfile.addMethod("call_function", CodegenUtils.sig(PyObject.class, Integer.TYPE, PyFrame.class, ThreadState.class), 1);
      if (!this.codes.isEmpty()) {
         code.aload(0);
         code.aload(2);
         code.aload(3);
         Label def = new Label();
         Label[] labels = new Label[this.codes.size()];

         int i;
         for(i = 0; i < labels.length; ++i) {
            labels[i] = new Label();
         }

         code.iload(1);
         code.tableswitch(0, labels.length - 1, def, labels);

         for(i = 0; i < labels.length; ++i) {
            code.label(labels[i]);
            code.invokevirtual(this.classfile.name, ((PyCodeConstant)this.codes.get(i)).fname, CodegenUtils.sig(PyObject.class, PyFrame.class, ThreadState.class));
            code.areturn();
         }

         code.label(def);
      }

      code.aconst_null();
      code.areturn();
   }

   public void write(OutputStream stream) throws IOException {
      this.addInit();
      this.addRunnable();
      this.addMain();
      this.addBootstrap();
      this.addFunctions();
      this.classfile.addInterface(CodegenUtils.p(PyRunnable.class));
      if (this.sfilename != null) {
         this.classfile.setSource(this.sfilename);
      }

      this.classfile.write(stream);
   }

   public Future getFutures() {
      return this.futures;
   }

   public String getFilename() {
      return this.sfilename;
   }

   public ScopeInfo getScopeInfo(PythonTree node) {
      return (ScopeInfo)this.scopes.get(node);
   }

   public void error(String msg, boolean err, PythonTree node) throws Exception {
      if (!err) {
         try {
            Py.warning(Py.SyntaxWarning, msg, this.sfilename != null ? this.sfilename : "?", node.getLine(), (String)null, Py.None);
            return;
         } catch (PyException var5) {
            if (!var5.match(Py.SyntaxWarning)) {
               throw var5;
            }
         }
      }

      throw new ParseException(msg, node);
   }

   public static void compile(mod node, OutputStream ostream, String name, String filename, boolean linenumbers, boolean printResults, CompilerFlags cflags) throws Exception {
      compile(node, ostream, name, filename, linenumbers, printResults, cflags, -1L);
   }

   protected static void _module_init(mod node, Module module, boolean printResults, CompilerFlags cflags) throws Exception {
      if (cflags == null) {
         cflags = new CompilerFlags();
      }

      module.futures.preprocessFutures(node, cflags);
      (new ScopesCompiler(module, module.scopes)).parse(node);
      Constant main = module.codeConstant(node, "<module>", false, (String)null, false, printResults, 0, module.getScopeInfo(node), cflags);
      module.mainCode = main;
   }

   private static PyBytecode loadPyBytecode(String filename, boolean try_cpython) throws RuntimeException {
      if (filename.startsWith("__pyclasspath__/")) {
         ClassLoader cld = Py.getSystemState().getClassLoader();
         if (cld == null) {
            cld = imp.getParentClassLoader();
         }

         URL py_url = cld.getResource(filename.replace("__pyclasspath__/", ""));
         if (py_url == null) {
            throw new RuntimeException("\nEncountered too large method code in \n" + filename + ",\n" + "but couldn't resolve that filename within classpath.\n" + "Make sure the source file is at a proper location.");
         }

         filename = py_url.getPath();
      }

      String cpython_cmd_msg = "\n\nAlternatively provide proper CPython 2.7 execute command via\ncpython_cmd property, e.g. call \n    jython -J-Dcpython_cmd=python\nor if running pip on Jython:\n    pip install --global-option=\"-J-Dcpython_cmd=python\" <package>";
      String large_method_msg = "\nEncountered too large method code in \n" + filename + "\n";
      String please_provide_msg = "\nPlease provide a CPython 2.7 bytecode file (.pyc) to proceed, e.g. run\npython -m py_compile " + filename + "\nand try again.";
      String pyc_filename = filename + "c";
      File pyc_file = new File(pyc_filename);
      if (pyc_file.exists()) {
         PyFile f = new PyFile(pyc_filename, "rb", 4096);
         byte[] bts = f.read(8).toBytes();
         int magic = bts[1] << 8 & '\uff00' | bts[0] << 0 & 255;
         if (magic != 62211) {
            throw new RuntimeException(large_method_msg + "\n" + pyc_filename + "\ncontains wrong bytecode version, not CPython 2.7 bytecode." + please_provide_msg);
         } else {
            _marshal.Unmarshaller un = new _marshal.Unmarshaller(f);
            PyObject code = un.load();
            f.close();
            if (code instanceof PyBytecode) {
               return (PyBytecode)code;
            } else {
               throw new RuntimeException(large_method_msg + "\n" + pyc_filename + "\ncontains invalid bytecode." + please_provide_msg);
            }
         }
      } else {
         String CPython_command = System.getProperty("cpython_cmd");
         if (try_cpython && CPython_command != null) {
            String command_ver = CPython_command + " --version";
            String command = CPython_command + " -m py_compile " + filename;
            String tried_create_pyc_msg = "\nTried to create pyc-file by executing\n" + command + "\nThis failed because of\n";
            Exception exc = null;
            int result = 0;

            Process p;
            try {
               p = Runtime.getRuntime().exec(command_ver);
               BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
               String cp_version = br.readLine();

               while(true) {
                  if (br.readLine() == null) {
                     br.close();
                     if (cp_version == null) {
                        br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        cp_version = br.readLine();

                        while(true) {
                           if (br.readLine() == null) {
                              br.close();
                              break;
                           }
                        }
                     }

                     result = p.waitFor();
                     if (!cp_version.startsWith("Python 2.7.")) {
                        throw new RuntimeException(large_method_msg + tried_create_pyc_msg + "wrong Python version: " + cp_version + "." + "\nRequired is Python 2.7.x.\n" + please_provide_msg + cpython_cmd_msg);
                     }
                     break;
                  }
               }
            } catch (InterruptedException var18) {
               exc = var18;
            } catch (IOException var19) {
               exc = var19;
            }

            if (exc == null && result == 0) {
               try {
                  p = Runtime.getRuntime().exec(command);
                  result = p.waitFor();
                  if (result == 0) {
                     return loadPyBytecode(filename, false);
                  }
               } catch (InterruptedException var16) {
                  exc = var16;
               } catch (IOException var17) {
                  exc = var17;
               }
            }

            String exc_msg = large_method_msg + tried_create_pyc_msg + (exc != null ? ((Exception)exc).toString() : "bad return: " + result) + ".\n" + please_provide_msg + cpython_cmd_msg;
            throw exc != null ? new RuntimeException(exc_msg, (Throwable)exc) : new RuntimeException(exc_msg);
         } else {
            throw new RuntimeException(large_method_msg + please_provide_msg + cpython_cmd_msg);
         }
      }
   }

   private static String serializePyBytecode(PyBytecode btcode) throws IOException {
      ByteArrayOutputStream bo = new ByteArrayOutputStream();
      ObjectOutputStream so = new ObjectOutputStream(bo);
      so.writeObject(btcode);
      so.flush();
      String code_str = DatatypeConverter.printBase64Binary(bo.toByteArray());
      so.close();
      bo.close();
      return code_str;
   }

   private static void insert_code_str_to_classfile(String name, String code_str, Module module) throws IOException {
      if (code_str.length() > 65535) {
         int splits = code_str.length() / '\uffff';
         if (code_str.length() % '\uffff' > 0) {
            ++splits;
         }

         int pos = 0;

         int i;
         for(i = 0; pos + '\uffff' <= code_str.length(); ++i) {
            module.classfile.addFinalStringLiteral("___" + splits + "_" + i + "_" + name, code_str.substring(pos, pos + '\uffff'));
            pos += 65535;
         }

         if (i < splits) {
            module.classfile.addFinalStringLiteral("___" + splits + "_" + i + "_" + name, code_str.substring(pos));
         }
      } else {
         module.classfile.addFinalStringLiteral("___0_" + name, code_str);
      }

   }

   public static void compile(mod node, OutputStream ostream, String name, String filename, boolean linenumbers, boolean printResults, CompilerFlags cflags, long mtime) throws Exception {
      try {
         Module module = new Module(name, filename, linenumbers, mtime);
         _module_init(node, module, printResults, cflags);
         module.write(ostream);
      } catch (RuntimeException var21) {
         RuntimeException re = var21;
         if (var21.getMessage() != null && var21.getMessage().equals("Method code too large!")) {
            PyBytecode btcode = loadPyBytecode(filename, true);
            int thresh = 22000;

            while(true) {
               try {
                  List largest_m_codes = new ArrayList();
                  Stack buffer = new Stack();
                  buffer.push(btcode);

                  while(true) {
                     while(!buffer.isEmpty()) {
                        PyBytecode bcode = (PyBytecode)buffer.pop();
                        if (bcode.co_code.length > thresh) {
                           largest_m_codes.add(bcode);
                        } else {
                           PyObject[] var15 = bcode.co_consts;
                           int var16 = var15.length;

                           for(int var17 = 0; var17 < var16; ++var17) {
                              PyObject item = var15[var17];
                              if (item instanceof PyBytecode) {
                                 PyBytecode mpbc = (PyBytecode)item;
                                 buffer.push(mpbc);
                              }
                           }
                        }
                     }

                     Module module = new Module(name, filename, linenumbers, mtime);
                     module.oversized_methods = new Hashtable(largest_m_codes.size());
                     int ov_id = 0;
                     Iterator var26 = largest_m_codes.iterator();

                     while(var26.hasNext()) {
                        PyBytecode largest_m_code = (PyBytecode)var26.next();
                        String name_id;
                        if (!PyCodeConstant.isJavaIdentifier(largest_m_code.co_name)) {
                           name_id = "f$_" + ov_id++;
                        } else {
                           name_id = largest_m_code.co_name + "$_" + ov_id++;
                        }

                        if (largest_m_code.co_name.equals("<module>")) {
                           module.oversized_methods.put(largest_m_code.co_name + 0, name_id);
                        } else {
                           module.oversized_methods.put(largest_m_code.co_name + largest_m_code.co_firstlineno, name_id);
                        }

                        String code_str = serializePyBytecode(largest_m_code);
                        insert_code_str_to_classfile(name_id, code_str, module);
                     }

                     module.classfile.addInterface(CodegenUtils.p(ContainsPyBytecode.class));
                     _module_init(node, module, printResults, cflags);
                     module.write(ostream);
                     return;
                  }
               } catch (RuntimeException var20) {
                  if (re.getMessage() == null || !var20.getMessage().equals("Method code too large!")) {
                     throw var20;
                  }

                  thresh -= 100;
                  if (thresh == 10000) {
                     throw new RuntimeException("For unknown reason, too large method code couldn't be resolved\nby PyBytecode-approach:\n" + filename);
                  }
               }
            }
         }

         throw var21;
      }

   }

   public void emitNum(Num node, Code code) throws Exception {
      if (node.getInternalN() instanceof PyInteger) {
         this.integerConstant(((PyInteger)node.getInternalN()).getValue()).get(code);
      } else if (node.getInternalN() instanceof PyLong) {
         this.longConstant(((PyObject)node.getInternalN()).__str__().toString()).get(code);
      } else if (node.getInternalN() instanceof PyFloat) {
         this.floatConstant(((PyFloat)node.getInternalN()).getValue()).get(code);
      } else if (node.getInternalN() instanceof PyComplex) {
         this.complexConstant(((PyComplex)node.getInternalN()).imag).get(code);
      }

   }

   public void emitStr(Str node, Code code) throws Exception {
      PyString s = (PyString)node.getInternalS();
      if (s instanceof PyUnicode) {
         this.unicodeConstant(s.asString()).get(code);
      } else {
         this.stringConstant(s.asString()).get(code);
      }

   }

   public boolean emitPrimitiveArraySetters(List nodes, Code code) throws Exception {
      int n = nodes.size();
      if (n < 100) {
         return false;
      } else {
         boolean primitive_literals = true;

         int num_setters;
         for(num_setters = 0; num_setters < n; ++num_setters) {
            PythonTree node = (PythonTree)nodes.get(num_setters);
            if (!(node instanceof Num) && !(node instanceof Str)) {
               primitive_literals = false;
            }
         }

         if (!primitive_literals) {
            return false;
         } else {
            num_setters = n / 4096 + 1;
            code.iconst(n);
            code.anewarray(CodegenUtils.p(PyObject.class));

            for(int i = 0; i < num_setters; ++i) {
               Code setter = this.classfile.addMethod("set$$" + this.setter_count, CodegenUtils.sig(Void.TYPE, PyObject[].class), 10);

               for(int j = 0; j < 4096 && i * 4096 + j < n; ++j) {
                  setter.aload(0);
                  setter.iconst(i * 4096 + j);
                  PythonTree node = (PythonTree)nodes.get(i * 4096 + j);
                  if (node instanceof Num) {
                     this.emitNum((Num)node, setter);
                  } else if (node instanceof Str) {
                     this.emitStr((Str)node, setter);
                  }

                  setter.aastore();
               }

               setter.return_();
               code.dup();
               code.invokestatic(this.classfile.name, "set$$" + this.setter_count, CodegenUtils.sig(Void.TYPE, PyObject[].class));
               ++this.setter_count;
            }

            return true;
         }
      }
   }
}
