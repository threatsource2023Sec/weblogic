package org.python.indexer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.python.indexer.ast.NNode;
import org.python.indexer.ast.NUrl;
import org.python.indexer.types.NClassType;
import org.python.indexer.types.NDictType;
import org.python.indexer.types.NFuncType;
import org.python.indexer.types.NListType;
import org.python.indexer.types.NModuleType;
import org.python.indexer.types.NTupleType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnionType;
import org.python.indexer.types.NUnknownType;

public class Builtins {
   public static final String LIBRARY_URL = "http://docs.python.org/library/";
   public static final String TUTORIAL_URL = "http://docs.python.org/tutorial/";
   public static final String REFERENCE_URL = "http://docs.python.org/reference/";
   public static final String DATAMODEL_URL = "http://docs.python.org/reference/datamodel#";
   public NModuleType Builtin;
   public NClassType Object;
   public NClassType Type;
   public NClassType None;
   public NClassType BaseNum;
   public NClassType BaseComplex;
   public NClassType BaseBool;
   public NClassType BaseStr;
   public NClassType BaseList;
   public NClassType BaseArray;
   public NClassType BaseDict;
   public NClassType BaseTuple;
   public NClassType BaseModule;
   public NClassType BaseFile;
   public NClassType BaseException;
   public NClassType BaseStruct;
   public NClassType BaseFunction;
   public NClassType BaseClass;
   public NClassType Datetime_datetime;
   public NClassType Datetime_date;
   public NClassType Datetime_time;
   public NClassType Datetime_timedelta;
   public NClassType Datetime_tzinfo;
   public NClassType Time_struct_time;
   Scope globaltable;
   Scope moduleTable;
   String[] builtin_exception_types = new String[]{"ArithmeticError", "AssertionError", "AttributeError", "BaseException", "BytesWarning", "Exception", "DeprecationWarning", "EOFError", "EnvironmentError", "FloatingPointError", "FutureWarning", "GeneratorExit", "IOError", "ImportError", "ImportWarning", "IndentationError", "IndexError", "KeyError", "KeyboardInterrupt", "LookupError", "MemoryError", "NameError", "NotImplemented", "NotImplementedError", "OSError", "OverflowError", "PendingDeprecationWarning", "ReferenceError", "RuntimeError", "RuntimeWarning", "StandardError", "StopIteration", "SyntaxError", "SyntaxWarning", "SystemError", "SystemExit", "TabError", "TypeError", "UnboundLocalError", "UnicodeDecodeError", "UnicodeEncodeError", "UnicodeError", "UnicodeTranslateError", "UnicodeWarning", "UserWarning", "ValueError", "Warning", "ZeroDivisionError"};
   Set nativeTypes = new HashSet();
   private Map modules = new HashMap();

   public static NUrl newLibUrl(String module, String name) {
      return newLibUrl(module + ".html#" + name);
   }

   public static NUrl newLibUrl(String path) {
      if (!path.endsWith(".html")) {
         path = path + ".html";
      }

      return new NUrl("http://docs.python.org/library/" + path);
   }

   public static NUrl newRefUrl(String path) {
      return new NUrl("http://docs.python.org/reference/" + path);
   }

   public static NUrl newDataModelUrl(String path) {
      return new NUrl("http://docs.python.org/reference/datamodel#" + path);
   }

   public static NUrl newTutUrl(String path) {
      return new NUrl("http://docs.python.org/tutorial/" + path);
   }

   NClassType newClass(String name, Scope table) {
      return this.newClass(name, table, (NClassType)null);
   }

   NClassType newClass(String name, Scope table, NClassType superClass, NClassType... moreSupers) {
      NClassType t = new NClassType(name, table, superClass);
      NClassType[] var6 = moreSupers;
      int var7 = moreSupers.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         NClassType c = var6[var8];
         t.addSuper(c);
      }

      this.nativeTypes.add(t);
      return t;
   }

   NModuleType newModule(String name) {
      NModuleType mt = new NModuleType(name, (String)null, this.globaltable);
      this.nativeTypes.add(mt);
      return mt;
   }

   NUnknownType unknown() {
      NUnknownType t = new NUnknownType();
      this.nativeTypes.add(t);
      return t;
   }

   NClassType newException(String name, Scope t) {
      return this.newClass(name, t, this.BaseException);
   }

   NFuncType newFunc() {
      NFuncType t = new NFuncType();
      this.nativeTypes.add(t);
      return t;
   }

   NFuncType newFunc(NType type) {
      NFuncType t = new NFuncType(type);
      this.nativeTypes.add(t);
      return t;
   }

   NListType newList() {
      return this.newList(this.unknown());
   }

   NListType newList(NType type) {
      NListType t = new NListType(type);
      this.nativeTypes.add(t);
      return t;
   }

   NDictType newDict(NType ktype, NType vtype) {
      NDictType t = new NDictType(ktype, vtype);
      this.nativeTypes.add(t);
      return t;
   }

   NTupleType newTuple(NType... types) {
      NTupleType t = new NTupleType(types);
      this.nativeTypes.add(t);
      return t;
   }

   NUnionType newUnion(NType... types) {
      NUnionType t = new NUnionType(types);
      this.nativeTypes.add(t);
      return t;
   }

   String[] list(String... names) {
      return names;
   }

   public Builtins(Scope globals, Scope modules) {
      this.globaltable = globals;
      this.moduleTable = modules;
      this.buildTypes();
   }

   private void buildTypes() {
      new BuiltinsModule();
      Scope bt = this.Builtin.getTable();
      this.Object = this.newClass("object", bt);
      this.None = this.newClass("None", bt);
      this.Type = this.newClass("type", bt, this.Object);
      this.BaseTuple = this.newClass("tuple", bt, this.Object);
      this.BaseList = this.newClass("list", bt, this.Object);
      this.BaseArray = this.newClass("array", bt);
      this.BaseDict = this.newClass("dict", bt, this.Object);
      this.BaseNum = this.newClass("float", bt, this.Object);
      this.BaseComplex = this.newClass("complex", bt, this.Object);
      this.BaseBool = this.newClass("bool", bt, this.BaseNum);
      this.BaseStr = this.newClass("str", bt, this.Object);
      this.BaseModule = this.newClass("module", bt);
      this.BaseFile = this.newClass("file", bt, this.Object);
      this.BaseFunction = this.newClass("function", bt, this.Object);
      this.BaseClass = this.newClass("classobj", bt, this.Object);
   }

   void init() {
      this.buildObjectType();
      this.buildTupleType();
      this.buildArrayType();
      this.buildListType();
      this.buildDictType();
      this.buildNumTypes();
      this.buildStrType();
      this.buildModuleType();
      this.buildFileType();
      this.buildFunctionType();
      this.buildClassType();
      ((NativeModule)this.modules.get("__builtin__")).initBindings();
      new ArrayModule();
      new AudioopModule();
      new BinasciiModule();
      new Bz2Module();
      new CPickleModule();
      new CStringIOModule();
      new CMathModule();
      new CollectionsModule();
      new CryptModule();
      new CTypesModule();
      new DatetimeModule();
      new DbmModule();
      new ErrnoModule();
      new ExceptionsModule();
      new FcntlModule();
      new FpectlModule();
      new GcModule();
      new GdbmModule();
      new GrpModule();
      new ImpModule();
      new ItertoolsModule();
      new MarshalModule();
      new MathModule();
      new Md5Module();
      new MmapModule();
      new NisModule();
      new OperatorModule();
      new OsModule();
      new ParserModule();
      new PosixModule();
      new PwdModule();
      new PyexpatModule();
      new ReadlineModule();
      new ResourceModule();
      new SelectModule();
      new SignalModule();
      new ShaModule();
      new SpwdModule();
      new StropModule();
      new StructModule();
      new SysModule();
      new SyslogModule();
      new TermiosModule();
      new ThreadModule();
      new TimeModule();
      new UnicodedataModule();
      new ZipimportModule();
      new ZlibModule();
   }

   public NModuleType get(String name) {
      if (name.indexOf(".") == -1) {
         return this.getModule(name);
      } else {
         String[] mods = name.split("\\.");
         NType type = this.getModule(mods[0]);
         if (type == null) {
            return null;
         } else {
            for(int i = 1; i < mods.length; ++i) {
               type = ((NType)type).getTable().lookupType(mods[i]);
               if (!(type instanceof NModuleType)) {
                  return null;
               }
            }

            return (NModuleType)type;
         }
      }
   }

   private NModuleType getModule(String name) {
      NativeModule wrap = (NativeModule)this.modules.get(name);
      return wrap == null ? null : wrap.getModule();
   }

   public boolean isNative(NType type) {
      return this.nativeTypes.contains(type);
   }

   void buildObjectType() {
      String[] obj_methods = new String[]{"__delattr__", "__format__", "__getattribute__", "__hash__", "__init__", "__new__", "__reduce__", "__reduce_ex__", "__repr__", "__setattr__", "__sizeof__", "__str__", "__subclasshook__"};
      String[] var2 = obj_methods;
      int var3 = obj_methods.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String m = var2[var4];
         this.Object.getTable().update(m, (NNode)newLibUrl("stdtypes"), this.newFunc(), NBinding.Kind.METHOD);
      }

      this.Object.getTable().update("__doc__", (NNode)newLibUrl("stdtypes"), this.BaseStr, NBinding.Kind.CLASS);
      this.Object.getTable().update("__class__", (NNode)newLibUrl("stdtypes"), this.unknown(), NBinding.Kind.CLASS);
   }

   void buildTupleType() {
      Scope bt = this.BaseTuple.getTable();
      String[] tuple_methods = new String[]{"__add__", "__contains__", "__eq__", "__ge__", "__getnewargs__", "__gt__", "__iter__", "__le__", "__len__", "__lt__", "__mul__", "__ne__", "__new__", "__rmul__", "count", "index"};
      String[] var3 = tuple_methods;
      int var4 = tuple_methods.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String m = var3[var5];
         bt.update(m, (NNode)newLibUrl("stdtypes"), this.newFunc(), NBinding.Kind.METHOD);
      }

      NBinding b = bt.update("__getslice__", (NNode)newDataModelUrl("object.__getslice__"), this.newFunc(), NBinding.Kind.METHOD);
      b.markDeprecated();
      bt.update("__getitem__", (NNode)newDataModelUrl("object.__getitem__"), this.newFunc(), NBinding.Kind.METHOD);
      bt.update("__iter__", (NNode)newDataModelUrl("object.__iter__"), this.newFunc(), NBinding.Kind.METHOD);
   }

   void buildArrayType() {
      String[] array_methods_none = new String[]{"append", "buffer_info", "byteswap", "extend", "fromfile", "fromlist", "fromstring", "fromunicode", "index", "insert", "pop", "read", "remove", "reverse", "tofile", "tolist", "typecode", "write"};
      String[] array_methods_num = array_methods_none;
      int var3 = array_methods_none.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         String m = array_methods_num[var4];
         this.BaseArray.getTable().update(m, (NNode)newLibUrl("array"), this.newFunc(this.None), NBinding.Kind.METHOD);
      }

      array_methods_num = new String[]{"count", "itemsize"};
      String[] array_methods_str = array_methods_num;
      var4 = array_methods_num.length;

      int var10;
      for(var10 = 0; var10 < var4; ++var10) {
         String m = array_methods_str[var10];
         this.BaseArray.getTable().update(m, (NNode)newLibUrl("array"), this.newFunc(this.BaseNum), NBinding.Kind.METHOD);
      }

      array_methods_str = new String[]{"tostring", "tounicode"};
      String[] var9 = array_methods_str;
      var10 = array_methods_str.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         String m = var9[var11];
         this.BaseArray.getTable().update(m, (NNode)newLibUrl("array"), this.newFunc(this.BaseStr), NBinding.Kind.METHOD);
      }

   }

   void buildListType() {
      this.BaseList.getTable().update("__getslice__", (NNode)newDataModelUrl("object.__getslice__"), this.newFunc(this.BaseList), NBinding.Kind.METHOD);
      this.BaseList.getTable().update("__getitem__", (NNode)newDataModelUrl("object.__getitem__"), this.newFunc(this.BaseList), NBinding.Kind.METHOD);
      this.BaseList.getTable().update("__iter__", (NNode)newDataModelUrl("object.__iter__"), this.newFunc(this.BaseList), NBinding.Kind.METHOD);
      String[] list_methods_none = new String[]{"append", "extend", "index", "insert", "pop", "remove", "reverse", "sort"};
      String[] list_methods_num = list_methods_none;
      int var3 = list_methods_none.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         String m = list_methods_num[var4];
         this.BaseList.getTable().update(m, (NNode)newLibUrl("stdtypes"), this.newFunc(this.None), NBinding.Kind.METHOD);
      }

      list_methods_num = new String[]{"count"};
      String[] var7 = list_methods_num;
      var4 = list_methods_num.length;

      for(int var8 = 0; var8 < var4; ++var8) {
         String m = var7[var8];
         this.BaseList.getTable().update(m, (NNode)newLibUrl("stdtypes"), this.newFunc(this.BaseNum), NBinding.Kind.METHOD);
      }

   }

   NUrl numUrl() {
      return newLibUrl("stdtypes", "typesnumeric");
   }

   void buildNumTypes() {
      Scope bnt = this.BaseNum.getTable();
      String[] num_methods_num = new String[]{"__abs__", "__add__", "__coerce__", "__div__", "__divmod__", "__eq__", "__float__", "__floordiv__", "__format__", "__ge__", "__getformat__", "__gt__", "__int__", "__le__", "__long__", "__lt__", "__mod__", "__mul__", "__ne__", "__neg__", "__new__", "__nonzero__", "__pos__", "__pow__", "__radd__", "__rdiv__", "__rdivmod__", "__rfloordiv__", "__rmod__", "__rmul__", "__rpow__", "__rsub__", "__rtruediv__", "__setformat__", "__sub__", "__truediv__", "__trunc__", "as_integer_ratio", "fromhex", "is_integer"};
      String[] var3 = num_methods_num;
      int var4 = num_methods_num.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String m = var3[var5];
         bnt.update(m, (NNode)this.numUrl(), this.newFunc(this.BaseNum), NBinding.Kind.METHOD);
      }

      bnt.update("__getnewargs__", (NNode)this.numUrl(), this.newFunc(this.newTuple(this.BaseNum)), NBinding.Kind.METHOD);
      bnt.update("hex", (NNode)this.numUrl(), this.newFunc(this.BaseStr), NBinding.Kind.METHOD);
      bnt.update("conjugate", (NNode)this.numUrl(), this.newFunc(this.BaseComplex), NBinding.Kind.METHOD);
      Scope bct = this.BaseComplex.getTable();
      String[] complex_methods = new String[]{"__abs__", "__add__", "__div__", "__divmod__", "__float__", "__floordiv__", "__format__", "__getformat__", "__int__", "__long__", "__mod__", "__mul__", "__neg__", "__new__", "__pos__", "__pow__", "__radd__", "__rdiv__", "__rdivmod__", "__rfloordiv__", "__rmod__", "__rmul__", "__rpow__", "__rsub__", "__rtruediv__", "__sub__", "__truediv__", "conjugate"};
      String[] complex_methods_num = complex_methods;
      int var13 = complex_methods.length;

      int var7;
      for(var7 = 0; var7 < var13; ++var7) {
         String c = complex_methods_num[var7];
         bct.update(c, (NNode)this.numUrl(), this.newFunc(this.BaseComplex), NBinding.Kind.METHOD);
      }

      complex_methods_num = new String[]{"__eq__", "__ge__", "__gt__", "__le__", "__lt__", "__ne__", "__nonzero__", "__coerce__"};
      String[] var14 = complex_methods_num;
      var7 = complex_methods_num.length;

      for(int var15 = 0; var15 < var7; ++var15) {
         String cn = var14[var15];
         bct.update(cn, (NNode)this.numUrl(), this.newFunc(this.BaseNum), NBinding.Kind.METHOD);
      }

      bct.update("__getnewargs__", (NNode)this.numUrl(), this.newFunc(this.newTuple(this.BaseComplex)), NBinding.Kind.METHOD);
      bct.update("imag", (NNode)this.numUrl(), this.BaseNum, NBinding.Kind.ATTRIBUTE);
      bct.update("real", (NNode)this.numUrl(), this.BaseNum, NBinding.Kind.ATTRIBUTE);
   }

   void buildStrType() {
      this.BaseStr.getTable().update("__getslice__", (NNode)newDataModelUrl("object.__getslice__"), this.newFunc(this.BaseStr), NBinding.Kind.METHOD);
      this.BaseStr.getTable().update("__getitem__", (NNode)newDataModelUrl("object.__getitem__"), this.newFunc(this.BaseStr), NBinding.Kind.METHOD);
      this.BaseStr.getTable().update("__iter__", (NNode)newDataModelUrl("object.__iter__"), this.newFunc(this.BaseStr), NBinding.Kind.METHOD);
      String[] str_methods_str = new String[]{"capitalize", "center", "decode", "encode", "expandtabs", "format", "index", "join", "ljust", "lower", "lstrip", "partition", "replace", "rfind", "rindex", "rjust", "rpartition", "rsplit", "rstrip", "strip", "swapcase", "title", "translate", "upper", "zfill"};
      String[] str_methods_num = str_methods_str;
      int var3 = str_methods_str.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         String m = str_methods_num[var4];
         this.BaseStr.getTable().update(m, (NNode)newLibUrl("stdtypes.html#str." + m), this.newFunc(this.BaseStr), NBinding.Kind.METHOD);
      }

      str_methods_num = new String[]{"count", "isalnum", "isalpha", "isdigit", "islower", "isspace", "istitle", "isupper", "find", "startswith", "endswith"};
      String[] str_methods_list = str_methods_num;
      var4 = str_methods_num.length;

      int var10;
      for(var10 = 0; var10 < var4; ++var10) {
         String m = str_methods_list[var10];
         this.BaseStr.getTable().update(m, (NNode)newLibUrl("stdtypes.html#str." + m), this.newFunc(this.BaseNum), NBinding.Kind.METHOD);
      }

      str_methods_list = new String[]{"split", "splitlines"};
      String[] var9 = str_methods_list;
      var10 = str_methods_list.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         String m = var9[var11];
         this.BaseStr.getTable().update(m, (NNode)newLibUrl("stdtypes.html#str." + m), this.newFunc(this.newList(this.BaseStr)), NBinding.Kind.METHOD);
      }

      this.BaseStr.getTable().update("partition", (NNode)newLibUrl("stdtypes"), this.newFunc(this.newTuple(this.BaseStr)), NBinding.Kind.METHOD);
   }

   void buildModuleType() {
      String[] attrs = new String[]{"__doc__", "__file__", "__name__", "__package__"};
      String[] var2 = attrs;
      int var3 = attrs.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String m = var2[var4];
         this.BaseModule.getTable().update(m, (NNode)newTutUrl("modules.html"), this.BaseStr, NBinding.Kind.ATTRIBUTE);
      }

      this.BaseModule.getTable().update("__dict__", (NNode)newLibUrl("stdtypes", "modules"), this.newDict(this.BaseStr, this.unknown()), NBinding.Kind.ATTRIBUTE);
   }

   void buildDictType() {
      String url = "datastructures.html#dictionaries";
      Scope bt = this.BaseDict.getTable();
      bt.update("__getitem__", (NNode)newTutUrl(url), this.newFunc(), NBinding.Kind.METHOD);
      bt.update("__iter__", (NNode)newTutUrl(url), this.newFunc(), NBinding.Kind.METHOD);
      bt.update("get", (NNode)newTutUrl(url), this.newFunc(), NBinding.Kind.METHOD);
      bt.update("items", (NNode)newTutUrl(url), this.newFunc(this.newList(this.newTuple(this.unknown(), this.unknown()))), NBinding.Kind.METHOD);
      bt.update("keys", (NNode)newTutUrl(url), this.newFunc(this.BaseList), NBinding.Kind.METHOD);
      bt.update("values", (NNode)newTutUrl(url), this.newFunc(this.BaseList), NBinding.Kind.METHOD);
      String[] dict_method_unknown = new String[]{"clear", "copy", "fromkeys", "get", "iteritems", "iterkeys", "itervalues", "pop", "popitem", "setdefault", "update"};
      String[] dict_method_num = dict_method_unknown;
      int var5 = dict_method_unknown.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         String m = dict_method_num[var6];
         bt.update(m, (NNode)newTutUrl(url), this.newFunc(), NBinding.Kind.METHOD);
      }

      dict_method_num = new String[]{"has_key"};
      String[] var9 = dict_method_num;
      var6 = dict_method_num.length;

      for(int var10 = 0; var10 < var6; ++var10) {
         String m = var9[var10];
         bt.update(m, (NNode)newTutUrl(url), this.newFunc(this.BaseNum), NBinding.Kind.METHOD);
      }

   }

   void buildFileType() {
      String url = "stdtypes.html#bltin-file-objects";
      Scope table = this.BaseFile.getTable();
      String[] methods_unknown = new String[]{"__enter__", "__exit__", "__iter__", "flush", "readinto", "truncate"};
      String[] methods_str = methods_unknown;
      int var5 = methods_unknown.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         String m = methods_str[var6];
         table.update(m, (NNode)newLibUrl(url), this.newFunc(), NBinding.Kind.METHOD);
      }

      methods_str = new String[]{"next", "read", "readline"};
      String[] num = methods_str;
      var6 = methods_str.length;

      int var13;
      for(var13 = 0; var13 < var6; ++var13) {
         String m = num[var13];
         table.update(m, (NNode)newLibUrl(url), this.newFunc(this.BaseStr), NBinding.Kind.METHOD);
      }

      num = new String[]{"fileno", "isatty", "tell"};
      String[] methods_none = num;
      var13 = num.length;

      int var14;
      for(var14 = 0; var14 < var13; ++var14) {
         String m = methods_none[var14];
         table.update(m, (NNode)newLibUrl(url), this.newFunc(this.BaseNum), NBinding.Kind.METHOD);
      }

      methods_none = new String[]{"close", "seek", "write", "writelines"};
      String[] var15 = methods_none;
      var14 = methods_none.length;

      for(int var16 = 0; var16 < var14; ++var16) {
         String m = var15[var16];
         table.update(m, (NNode)newLibUrl(url), this.newFunc(this.None), NBinding.Kind.METHOD);
      }

      table.update("readlines", (NNode)newLibUrl(url), this.newFunc(this.newList(this.BaseStr)), NBinding.Kind.METHOD);
      table.update("xreadlines", (NNode)newLibUrl(url), this.newFunc(this.BaseFile), NBinding.Kind.METHOD);
      table.update("closed", (NNode)newLibUrl(url), this.BaseNum, NBinding.Kind.ATTRIBUTE);
      table.update("encoding", (NNode)newLibUrl(url), this.BaseStr, NBinding.Kind.ATTRIBUTE);
      table.update("errors", (NNode)newLibUrl(url), this.unknown(), NBinding.Kind.ATTRIBUTE);
      table.update("mode", (NNode)newLibUrl(url), this.BaseNum, NBinding.Kind.ATTRIBUTE);
      table.update("name", (NNode)newLibUrl(url), this.BaseStr, NBinding.Kind.ATTRIBUTE);
      table.update("softspace", (NNode)newLibUrl(url), this.BaseNum, NBinding.Kind.ATTRIBUTE);
      table.update("newlines", (NNode)newLibUrl(url), this.newUnion(this.BaseStr, this.newTuple(this.BaseStr)), NBinding.Kind.ATTRIBUTE);
   }

   private NBinding synthetic(Scope table, String n, NUrl url, NType type, NBinding.Kind k) {
      NBinding b = table.update(n, (NNode)url, type, k);
      b.markSynthetic();
      return b;
   }

   void buildFunctionType() {
      Scope t = this.BaseFunction.getTable();
      String[] var2 = this.list("func_doc", "__doc__", "func_name", "__name__", "__module__");
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         String s = var2[var4];
         t.update(s, (NNode)(new NUrl("http://docs.python.org/reference/datamodel#")), this.BaseStr, NBinding.Kind.ATTRIBUTE);
      }

      NBinding b = this.synthetic(t, "func_closure", new NUrl("http://docs.python.org/reference/datamodel#"), this.newTuple(), NBinding.Kind.ATTRIBUTE);
      b.markReadOnly();
      this.synthetic(t, "func_code", new NUrl("http://docs.python.org/reference/datamodel#"), this.unknown(), NBinding.Kind.ATTRIBUTE);
      this.synthetic(t, "func_defaults", new NUrl("http://docs.python.org/reference/datamodel#"), this.newTuple(), NBinding.Kind.ATTRIBUTE);
      this.synthetic(t, "func_globals", new NUrl("http://docs.python.org/reference/datamodel#"), new NDictType(this.BaseStr, new NUnknownType()), NBinding.Kind.ATTRIBUTE);
      this.synthetic(t, "func_dict", new NUrl("http://docs.python.org/reference/datamodel#"), new NDictType(this.BaseStr, new NUnknownType()), NBinding.Kind.ATTRIBUTE);
      String[] var8 = this.list("__func__", "im_func");
      var4 = var8.length;

      for(int var9 = 0; var9 < var4; ++var9) {
         String s = var8[var9];
         this.synthetic(t, s, new NUrl("http://docs.python.org/reference/datamodel#"), new NFuncType(), NBinding.Kind.METHOD);
      }

   }

   void buildClassType() {
      Scope t = this.BaseClass.getTable();
      String[] var2 = this.list("__name__", "__doc__", "__module__");
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String s = var2[var4];
         this.synthetic(t, s, new NUrl("http://docs.python.org/reference/datamodel#"), this.BaseStr, NBinding.Kind.ATTRIBUTE);
      }

      this.synthetic(t, "__dict__", new NUrl("http://docs.python.org/reference/datamodel#"), new NDictType(this.BaseStr, this.unknown()), NBinding.Kind.ATTRIBUTE);
   }

   class ZlibModule extends NativeModule {
      public ZlibModule() {
         super("zlib");
      }

      public void initBindings() {
         NClassType Compress = Builtins.this.newClass("Compress", this.table, Builtins.this.Object);
         String[] var2 = Builtins.this.list("compress", "flush");
         int var3 = var2.length;

         int var4;
         for(var4 = 0; var4 < var3; ++var4) {
            String sx = var2[var4];
            Compress.getTable().update(sx, (NNode)Builtins.newLibUrl("zlib"), Builtins.this.BaseStr, NBinding.Kind.METHOD);
         }

         Compress.getTable().update("copy", (NNode)Builtins.newLibUrl("zlib"), Compress, NBinding.Kind.METHOD);
         this.addClass("Compress", this.liburl(), Compress);
         NClassType Decompress = Builtins.this.newClass("Decompress", this.table, Builtins.this.Object);
         String[] var8 = Builtins.this.list("unused_data", "unconsumed_tail");
         var4 = var8.length;

         String s;
         int var9;
         for(var9 = 0; var9 < var4; ++var9) {
            s = var8[var9];
            Decompress.getTable().update(s, (NNode)Builtins.newLibUrl("zlib"), Builtins.this.BaseStr, NBinding.Kind.ATTRIBUTE);
         }

         var8 = Builtins.this.list("decompress", "flush");
         var4 = var8.length;

         for(var9 = 0; var9 < var4; ++var9) {
            s = var8[var9];
            Decompress.getTable().update(s, (NNode)Builtins.newLibUrl("zlib"), Builtins.this.BaseStr, NBinding.Kind.METHOD);
         }

         Decompress.getTable().update("copy", (NNode)Builtins.newLibUrl("zlib"), Decompress, NBinding.Kind.METHOD);
         this.addClass("Decompress", this.liburl(), Decompress);
         this.addFunction("adler32", this.liburl(), Builtins.this.BaseNum);
         this.addFunction("compress", this.liburl(), Builtins.this.BaseStr);
         this.addFunction("compressobj", this.liburl(), Compress);
         this.addFunction("crc32", this.liburl(), Builtins.this.BaseNum);
         this.addFunction("decompress", this.liburl(), Builtins.this.BaseStr);
         this.addFunction("decompressobj", this.liburl(), Decompress);
      }
   }

   class ZipimportModule extends NativeModule {
      public ZipimportModule() {
         super("zipimport");
      }

      public void initBindings() {
         this.addClass("ZipImportError", this.liburl(), Builtins.this.newException("ZipImportError", this.table));
         NClassType zipimporter = Builtins.this.newClass("zipimporter", this.table, Builtins.this.Object);
         Scope t = zipimporter.getTable();
         t.update("find_module", (NNode)this.liburl(), zipimporter, NBinding.Kind.METHOD);
         t.update("get_code", (NNode)this.liburl(), Builtins.this.unknown(), NBinding.Kind.METHOD);
         t.update("get_data", (NNode)this.liburl(), Builtins.this.unknown(), NBinding.Kind.METHOD);
         t.update("get_source", (NNode)this.liburl(), Builtins.this.BaseStr, NBinding.Kind.METHOD);
         t.update("is_package", (NNode)this.liburl(), Builtins.this.BaseNum, NBinding.Kind.METHOD);
         t.update("load_module", (NNode)this.liburl(), Builtins.this.newModule("<?>"), NBinding.Kind.METHOD);
         t.update("archive", (NNode)this.liburl(), Builtins.this.BaseStr, NBinding.Kind.ATTRIBUTE);
         t.update("prefix", (NNode)this.liburl(), Builtins.this.BaseStr, NBinding.Kind.ATTRIBUTE);
         this.addClass("zipimporter", this.liburl(), zipimporter);
         this.addAttr("_zip_directory_cache", this.liburl(), Builtins.this.newDict(Builtins.this.BaseStr, Builtins.this.unknown()));
      }
   }

   class UnicodedataModule extends NativeModule {
      public UnicodedataModule() {
         super("unicodedata");
      }

      public void initBindings() {
         this.addNumFuncs(new String[]{"decimal", "digit", "numeric", "combining", "east_asian_width", "mirrored"});
         this.addStrFuncs(new String[]{"lookup", "name", "category", "bidirectional", "decomposition", "normalize"});
         this.addNumAttrs(new String[]{"unidata_version"});
         this.addUnknownAttrs(new String[]{"ucd_3_2_0"});
      }
   }

   class TimeModule extends NativeModule {
      public TimeModule() {
         super("time");
      }

      public void initBindings() {
         NClassType struct_time = Builtins.this.Time_struct_time = Builtins.this.newClass("datetime", this.table, Builtins.this.Object);
         this.addAttr("struct_time", this.liburl(), struct_time);
         String[] struct_time_attrs = new String[]{"n_fields", "n_sequence_fields", "n_unnamed_fields", "tm_hour", "tm_isdst", "tm_mday", "tm_min", "tm_mon", "tm_wday", "tm_yday", "tm_year"};
         String[] var3 = struct_time_attrs;
         int var4 = struct_time_attrs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            struct_time.getTable().update(s, (NNode)this.liburl("struct_time"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         }

         this.addNumAttrs(new String[]{"accept2dyear", "altzone", "daylight", "timezone"});
         this.addAttr("tzname", this.liburl(), Builtins.this.newTuple(Builtins.this.BaseStr, Builtins.this.BaseStr));
         this.addNoneFuncs(new String[]{"sleep", "tzset"});
         this.addNumFuncs(new String[]{"clock", "mktime", "time", "tzname"});
         this.addStrFuncs(new String[]{"asctime", "ctime", "strftime"});
         this.addFunctions_beCareful(struct_time, new String[]{"gmtime", "localtime", "strptime"});
      }
   }

   class ThreadModule extends NativeModule {
      public ThreadModule() {
         super("thread");
      }

      public void initBindings() {
         this.addClass("error", this.liburl(), Builtins.this.newException("error", this.table));
         NClassType lock = Builtins.this.newClass("lock", this.table, Builtins.this.Object);
         lock.getTable().update("acquire", (NNode)this.liburl(), Builtins.this.BaseNum, NBinding.Kind.METHOD);
         lock.getTable().update("locked", (NNode)this.liburl(), Builtins.this.BaseNum, NBinding.Kind.METHOD);
         lock.getTable().update("release", (NNode)this.liburl(), Builtins.this.None, NBinding.Kind.METHOD);
         this.addAttr("LockType", this.liburl(), Builtins.this.Type);
         this.addNoneFuncs(new String[]{"interrupt_main", "exit", "exit_thread"});
         this.addNumFuncs(new String[]{"start_new", "start_new_thread", "get_ident", "stack_size"});
         this.addFunction("allocate", this.liburl(), lock);
         this.addFunction("allocate_lock", this.liburl(), lock);
         this.addAttr("_local", this.liburl(), Builtins.this.Type);
      }
   }

   class TermiosModule extends NativeModule {
      public TermiosModule() {
         super("termios");
      }

      public void initBindings() {
         this.addFunction("tcgetattr", this.liburl(), Builtins.this.newList());
         this.addUnknownFuncs(new String[]{"tcsetattr", "tcsendbreak", "tcdrain", "tcflush", "tcflow"});
      }
   }

   class SyslogModule extends NativeModule {
      public SyslogModule() {
         super("syslog");
      }

      public void initBindings() {
         this.addNoneFuncs(new String[]{"syslog", "openlog", "closelog", "setlogmask"});
         this.addNumAttrs(new String[]{"LOG_ALERT", "LOG_AUTH", "LOG_CONS", "LOG_CRIT", "LOG_CRON", "LOG_DAEMON", "LOG_DEBUG", "LOG_EMERG", "LOG_ERR", "LOG_INFO", "LOG_KERN", "LOG_LOCAL0", "LOG_LOCAL1", "LOG_LOCAL2", "LOG_LOCAL3", "LOG_LOCAL4", "LOG_LOCAL5", "LOG_LOCAL6", "LOG_LOCAL7", "LOG_LPR", "LOG_MAIL", "LOG_MASK", "LOG_NDELAY", "LOG_NEWS", "LOG_NOTICE", "LOG_NOWAIT", "LOG_PERROR", "LOG_PID", "LOG_SYSLOG", "LOG_UPTO", "LOG_USER", "LOG_UUCP", "LOG_WARNING"});
      }
   }

   class SysModule extends NativeModule {
      public SysModule() {
         super("sys");
      }

      public void initBindings() {
         this.addUnknownFuncs(new String[]{"_clear_type_cache", "call_tracing", "callstats", "_current_frames", "_getframe", "displayhook", "dont_write_bytecode", "exitfunc", "exc_clear", "exc_info", "excepthook", "exit", "last_traceback", "last_type", "last_value", "modules", "path_hooks", "path_importer_cache", "getprofile", "gettrace", "setcheckinterval", "setprofile", "setrecursionlimit", "settrace"});
         this.addAttr("exc_type", this.liburl(), Builtins.this.None);
         this.addUnknownAttrs(new String[]{"__stderr__", "__stdin__", "__stdout__", "stderr", "stdin", "stdout", "version_info"});
         this.addNumAttrs(new String[]{"api_version", "hexversion", "winver", "maxint", "maxsize", "maxunicode", "py3kwarning", "dllhandle"});
         this.addStrAttrs(new String[]{"platform", "byteorder", "copyright", "prefix", "version", "exec_prefix", "executable"});
         this.addNumFuncs(new String[]{"getrecursionlimit", "getwindowsversion", "getrefcount", "getsizeof", "getcheckinterval"});
         this.addStrFuncs(new String[]{"getdefaultencoding", "getfilesystemencoding"});
         String[] var1 = Builtins.this.list("argv", "builtin_module_names", "path", "meta_path", "subversion");
         int var2 = var1.length;

         int var3;
         String s;
         for(var3 = 0; var3 < var2; ++var3) {
            s = var1[var3];
            this.addAttr(s, this.liburl(), Builtins.this.newList(Builtins.this.BaseStr));
         }

         var1 = Builtins.this.list("flags", "warnoptions", "float_info");
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            s = var1[var3];
            this.addAttr(s, this.liburl(), Builtins.this.newDict(Builtins.this.BaseStr, Builtins.this.BaseNum));
         }

      }
   }

   class StructModule extends NativeModule {
      public StructModule() {
         super("struct");
      }

      public void initBindings() {
         this.addClass("error", this.liburl(), Builtins.this.newException("error", this.table));
         this.addStrFuncs(new String[]{"pack"});
         this.addUnknownFuncs(new String[]{"pack_into"});
         this.addNumFuncs(new String[]{"calcsize"});
         this.addFunction("unpack", this.liburl(), Builtins.this.newTuple());
         this.addFunction("unpack_from", this.liburl(), Builtins.this.newTuple());
         Builtins.this.BaseStruct = Builtins.this.newClass("Struct", this.table, Builtins.this.Object);
         this.addClass("Struct", this.liburl("struct-objects"), Builtins.this.BaseStruct);
         Scope t = Builtins.this.BaseStruct.getTable();
         t.update("pack", (NNode)this.liburl("struct-objects"), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         t.update("pack_into", (NNode)this.liburl("struct-objects"), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         t.update("unpack", (NNode)this.liburl("struct-objects"), Builtins.this.newFunc(Builtins.this.newTuple()), NBinding.Kind.METHOD);
         t.update("unpack_from", (NNode)this.liburl("struct-objects"), Builtins.this.newFunc(Builtins.this.newTuple()), NBinding.Kind.METHOD);
         t.update("format", (NNode)this.liburl("struct-objects"), Builtins.this.BaseStr, NBinding.Kind.ATTRIBUTE);
         t.update("size", (NNode)this.liburl("struct-objects"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
      }
   }

   class StropModule extends NativeModule {
      public StropModule() {
         super("strop");
      }

      public void initBindings() {
         this.table.merge(Builtins.this.BaseStr.getTable());
      }
   }

   class SpwdModule extends NativeModule {
      public SpwdModule() {
         super("spwd");
      }

      public void initBindings() {
         NClassType struct_spwd = Builtins.this.newClass("struct_spwd", this.table, Builtins.this.Object);
         String[] var2 = Builtins.this.list("sp_nam", "sp_pwd", "sp_lstchg", "sp_min", "sp_max", "sp_warn", "sp_inact", "sp_expire", "sp_flag");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            struct_spwd.getTable().update(s, (NNode)this.liburl(), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         }

         this.addAttr("struct_spwd", this.liburl(), struct_spwd);
         this.addFunction("getspnam", this.liburl(), struct_spwd);
         this.addFunction("getspall", this.liburl(), Builtins.this.newList(struct_spwd));
      }
   }

   class ShaModule extends NativeModule {
      public ShaModule() {
         super("sha");
      }

      public void initBindings() {
         this.addNumAttrs(new String[]{"blocksize", "digest_size"});
         NClassType sha = Builtins.this.newClass("sha", this.table, Builtins.this.Object);
         sha.getTable().update("update", (NNode)this.liburl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         sha.getTable().update("digest", (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         sha.getTable().update("hexdigest", (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         sha.getTable().update("copy", (NNode)this.liburl(), Builtins.this.newFunc(sha), NBinding.Kind.METHOD);
         this.addClass("sha", this.liburl(), sha);
         this.update("new", this.liburl(), Builtins.this.newFunc(sha), NBinding.Kind.CONSTRUCTOR);
      }
   }

   class SignalModule extends NativeModule {
      public SignalModule() {
         super("signal");
      }

      public void initBindings() {
         this.addNumAttrs(new String[]{"NSIG", "SIGABRT", "SIGALRM", "SIGBUS", "SIGCHLD", "SIGCLD", "SIGCONT", "SIGFPE", "SIGHUP", "SIGILL", "SIGINT", "SIGIO", "SIGIOT", "SIGKILL", "SIGPIPE", "SIGPOLL", "SIGPROF", "SIGPWR", "SIGQUIT", "SIGRTMAX", "SIGRTMIN", "SIGSEGV", "SIGSTOP", "SIGSYS", "SIGTERM", "SIGTRAP", "SIGTSTP", "SIGTTIN", "SIGTTOU", "SIGURG", "SIGUSR1", "SIGUSR2", "SIGVTALRM", "SIGWINCH", "SIGXCPU", "SIGXFSZ", "SIG_DFL", "SIG_IGN"});
         this.addUnknownFuncs(new String[]{"default_int_handler", "getsignal", "set_wakeup_fd", "signal"});
      }
   }

   class SelectModule extends NativeModule {
      public SelectModule() {
         super("select");
      }

      public void initBindings() {
         this.addClass("error", this.liburl(), Builtins.this.newException("error", this.table));
         this.addFunction("select", this.liburl(), Builtins.this.newTuple(Builtins.this.newList(), Builtins.this.newList(), Builtins.this.newList()));
         String a = "edge-and-level-trigger-polling-epoll-objects";
         NClassType epoll = Builtins.this.newClass("epoll", this.table, Builtins.this.Object);
         epoll.getTable().update("close", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(Builtins.this.None), NBinding.Kind.METHOD);
         epoll.getTable().update("fileno", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         epoll.getTable().update("fromfd", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(epoll), NBinding.Kind.METHOD);
         String[] var3 = Builtins.this.list("register", "modify", "unregister", "poll");
         int var4 = var3.length;

         int var5;
         String s;
         for(var5 = 0; var5 < var4; ++var5) {
            s = var3[var5];
            epoll.getTable().update(s, (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         }

         this.addClass("epoll", this.liburl(a), epoll);
         var3 = Builtins.this.list("EPOLLERR", "EPOLLET", "EPOLLHUP", "EPOLLIN", "EPOLLMSG", "EPOLLONESHOT", "EPOLLOUT", "EPOLLPRI", "EPOLLRDBAND", "EPOLLRDNORM", "EPOLLWRBAND", "EPOLLWRNORM");
         var4 = var3.length;

         for(var5 = 0; var5 < var4; ++var5) {
            s = var3[var5];
            this.addAttr(s, this.liburl(a), Builtins.this.BaseNum);
         }

         a = "polling-objects";
         NClassType poll = Builtins.this.newClass("poll", this.table, Builtins.this.Object);
         poll.getTable().update("register", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         poll.getTable().update("modify", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         poll.getTable().update("unregister", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         poll.getTable().update("poll", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(Builtins.this.newList(Builtins.this.newTuple(Builtins.this.BaseNum, Builtins.this.BaseNum))), NBinding.Kind.METHOD);
         this.addClass("poll", this.liburl(a), poll);
         String[] var11 = Builtins.this.list("POLLERR", "POLLHUP", "POLLIN", "POLLMSG", "POLLNVAL", "POLLOUT", "POLLPRI", "POLLRDBAND", "POLLRDNORM", "POLLWRBAND", "POLLWRNORM");
         var5 = var11.length;

         for(int var13 = 0; var13 < var5; ++var13) {
            String sx = var11[var13];
            this.addAttr(sx, this.liburl(a), Builtins.this.BaseNum);
         }

         a = "kqueue-objects";
         NClassType kqueue = Builtins.this.newClass("kqueue", this.table, Builtins.this.Object);
         kqueue.getTable().update("close", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(Builtins.this.None), NBinding.Kind.METHOD);
         kqueue.getTable().update("fileno", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         kqueue.getTable().update("fromfd", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(kqueue), NBinding.Kind.METHOD);
         kqueue.getTable().update("control", (NNode)Builtins.newLibUrl("select", a), Builtins.this.newFunc(Builtins.this.newList(Builtins.this.newTuple(Builtins.this.BaseNum, Builtins.this.BaseNum))), NBinding.Kind.METHOD);
         this.addClass("kqueue", this.liburl(a), kqueue);
         a = "kevent-objects";
         NClassType kevent = Builtins.this.newClass("kevent", this.table, Builtins.this.Object);
         String[] var15 = Builtins.this.list("ident", "filter", "flags", "fflags", "data", "udata");
         int var16 = var15.length;

         for(int var8 = 0; var8 < var16; ++var8) {
            String sxx = var15[var8];
            kevent.getTable().update(sxx, (NNode)Builtins.newLibUrl("select", a), Builtins.this.unknown(), NBinding.Kind.ATTRIBUTE);
         }

         this.addClass("kevent", this.liburl(a), kevent);
      }
   }

   class ResourceModule extends NativeModule {
      public ResourceModule() {
         super("resource");
      }

      public void initBindings() {
         this.addFunction("getrlimit", this.liburl(), Builtins.this.newTuple(Builtins.this.BaseNum, Builtins.this.BaseNum));
         this.addFunction("getrlimit", this.liburl(), Builtins.this.unknown());
         String[] constants = new String[]{"RLIMIT_CORE", "RLIMIT_CPU", "RLIMIT_FSIZE", "RLIMIT_DATA", "RLIMIT_STACK", "RLIMIT_RSS", "RLIMIT_NPROC", "RLIMIT_NOFILE", "RLIMIT_OFILE", "RLIMIT_MEMLOCK", "RLIMIT_VMEM", "RLIMIT_AS"};
         String[] var2 = constants;
         int var3 = constants.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String c = var2[var4];
            this.addAttr(c, this.liburl("resource-limits"), Builtins.this.BaseNum);
         }

         NClassType ru = Builtins.this.newClass("struct_rusage", this.table, Builtins.this.Object);
         String[] ru_fields = new String[]{"ru_utime", "ru_stime", "ru_maxrss", "ru_ixrss", "ru_idrss", "ru_isrss", "ru_minflt", "ru_majflt", "ru_nswap", "ru_inblock", "ru_oublock", "ru_msgsnd", "ru_msgrcv", "ru_nsignals", "ru_nvcsw", "ru_nivcsw"};
         String[] var10 = ru_fields;
         int var11 = ru_fields.length;

         int var6;
         String s;
         for(var6 = 0; var6 < var11; ++var6) {
            s = var10[var6];
            ru.getTable().update(s, (NNode)this.liburl("resource-usage"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         }

         this.addFunction("getrusage", this.liburl("resource-usage"), ru);
         this.addFunction("getpagesize", this.liburl("resource-usage"), Builtins.this.BaseNum);
         var10 = Builtins.this.list("RUSAGE_SELF", "RUSAGE_CHILDREN", "RUSAGE_BOTH");
         var11 = var10.length;

         for(var6 = 0; var6 < var11; ++var6) {
            s = var10[var6];
            this.addAttr(s, this.liburl("resource-usage"), Builtins.this.BaseNum);
         }

      }
   }

   class ReadlineModule extends NativeModule {
      public ReadlineModule() {
         super("readline");
      }

      public void initBindings() {
         this.addNoneFuncs(new String[]{"parse_and_bind", "insert_text", "read_init_file", "read_history_file", "write_history_file", "clear_history", "set_history_length", "remove_history_item", "replace_history_item", "redisplay", "set_startup_hook", "set_pre_input_hook", "set_completer", "set_completer_delims", "set_completion_display_matches_hook", "add_history"});
         this.addNumFuncs(new String[]{"get_history_length", "get_current_history_length", "get_begidx", "get_endidx"});
         this.addStrFuncs(new String[]{"get_line_buffer", "get_history_item"});
         this.addUnknownFuncs(new String[]{"get_completion_type"});
         this.addFunction("get_completer", this.liburl(), Builtins.this.newFunc());
         this.addFunction("get_completer_delims", this.liburl(), Builtins.this.newList(Builtins.this.BaseStr));
      }
   }

   class PyexpatModule extends NativeModule {
      public PyexpatModule() {
         super("pyexpat");
      }

      public void initBindings() {
      }
   }

   class PwdModule extends NativeModule {
      public PwdModule() {
         super("pwd");
      }

      public void initBindings() {
         NClassType struct_pwd = Builtins.this.newClass("struct_pwd", this.table, Builtins.this.Object);
         String[] var2 = Builtins.this.list("pw_nam", "pw_passwd", "pw_uid", "pw_gid", "pw_gecos", "pw_dir", "pw_shell");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            struct_pwd.getTable().update(s, (NNode)this.liburl(), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         }

         this.addAttr("struct_pwd", this.liburl(), struct_pwd);
         this.addFunction("getpwuid", this.liburl(), struct_pwd);
         this.addFunction("getpwnam", this.liburl(), struct_pwd);
         this.addFunction("getpwall", this.liburl(), Builtins.this.newList(struct_pwd));
      }
   }

   class PosixModule extends NativeModule {
      public PosixModule() {
         super("posix");
      }

      public void initBindings() {
         this.addAttr("environ", this.liburl(), Builtins.this.newDict(Builtins.this.BaseStr, Builtins.this.BaseStr));
      }
   }

   class ParserModule extends NativeModule {
      public ParserModule() {
         super("parser");
      }

      public void initBindings() {
         NClassType st = Builtins.this.newClass("st", this.table, Builtins.this.Object);
         st.getTable().update("compile", (NNode)Builtins.newLibUrl("parser", "st-objects"), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         st.getTable().update("isexpr", (NNode)Builtins.newLibUrl("parser", "st-objects"), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         st.getTable().update("issuite", (NNode)Builtins.newLibUrl("parser", "st-objects"), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         st.getTable().update("tolist", (NNode)Builtins.newLibUrl("parser", "st-objects"), Builtins.this.newFunc(Builtins.this.newList()), NBinding.Kind.METHOD);
         st.getTable().update("totuple", (NNode)Builtins.newLibUrl("parser", "st-objects"), Builtins.this.newFunc(Builtins.this.newTuple()), NBinding.Kind.METHOD);
         this.addAttr("STType", this.liburl("st-objects"), Builtins.this.Type);
         String[] var2 = Builtins.this.list("expr", "suite", "sequence2st", "tuple2st");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            this.addFunction(s, this.liburl("creating-st-objects"), st);
         }

         this.addFunction("st2list", this.liburl("converting-st-objects"), Builtins.this.newList());
         this.addFunction("st2tuple", this.liburl("converting-st-objects"), Builtins.this.newTuple());
         this.addFunction("compilest", this.liburl("converting-st-objects"), Builtins.this.unknown());
         this.addFunction("isexpr", this.liburl("queries-on-st-objects"), Builtins.this.BaseBool);
         this.addFunction("issuite", this.liburl("queries-on-st-objects"), Builtins.this.BaseBool);
         this.addClass("ParserError", this.liburl("exceptions-and-error-handling"), Builtins.this.newException("ParserError", this.table));
      }
   }

   class OperatorModule extends NativeModule {
      public OperatorModule() {
         super("operator");
      }

      public void initBindings() {
         this.addNumFuncs(new String[]{"__abs__", "__add__", "__and__", "__concat__", "__contains__", "__div__", "__doc__", "__eq__", "__floordiv__", "__ge__", "__getitem__", "__getslice__", "__gt__", "__iadd__", "__iand__", "__iconcat__", "__idiv__", "__ifloordiv__", "__ilshift__", "__imod__", "__imul__", "__index__", "__inv__", "__invert__", "__ior__", "__ipow__", "__irepeat__", "__irshift__", "__isub__", "__itruediv__", "__ixor__", "__le__", "__lshift__", "__lt__", "__mod__", "__mul__", "__name__", "__ne__", "__neg__", "__not__", "__or__", "__package__", "__pos__", "__pow__", "__repeat__", "__rshift__", "__setitem__", "__setslice__", "__sub__", "__truediv__", "__xor__", "abs", "add", "and_", "concat", "contains", "countOf", "div", "eq", "floordiv", "ge", "getitem", "getslice", "gt", "iadd", "iand", "iconcat", "idiv", "ifloordiv", "ilshift", "imod", "imul", "index", "indexOf", "inv", "invert", "ior", "ipow", "irepeat", "irshift", "isCallable", "isMappingType", "isNumberType", "isSequenceType", "is_", "is_not", "isub", "itruediv", "ixor", "le", "lshift", "lt", "mod", "mul", "ne", "neg", "not_", "or_", "pos", "pow", "repeat", "rshift", "sequenceIncludes", "setitem", "setslice", "sub", "truediv", "truth", "xor"});
         this.addUnknownFuncs(new String[]{"attrgetter", "itemgetter", "methodcaller"});
         this.addNoneFuncs(new String[]{"__delitem__", "__delslice__", "delitem", "delclice"});
      }
   }

   class OsModule extends NativeModule {
      public OsModule() {
         super("os");
      }

      public void initBindings() {
         this.addAttr("name", this.liburl(), Builtins.this.BaseStr);
         this.addClass("error", this.liburl(), Builtins.this.newException("error", this.table));
         this.initProcBindings();
         this.initProcMgmtBindings();
         this.initFileBindings();
         this.initFileAndDirBindings();
         this.initMiscSystemInfo();
         this.initOsPathModule();
         this.addAttr("errno", this.liburl(), Builtins.this.newModule("errno"));
         this.addFunction("urandom", this.liburl("miscellaneous-functions"), Builtins.this.BaseStr);
         this.addAttr("NGROUPS_MAX", this.liburl(), Builtins.this.BaseNum);
         String[] var1 = Builtins.this.list("_Environ", "_copy_reg", "_execvpe", "_exists", "_get_exports_list", "_make_stat_result", "_make_statvfs_result", "_pickle_stat_result", "_pickle_statvfs_result", "_spawnvef");
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            this.addFunction(s, this.liburl(), Builtins.this.unknown());
         }

      }

      private void initProcBindings() {
         String a = "process-parameters";
         this.addAttr("environ", this.liburl(a), Builtins.this.newDict(Builtins.this.BaseStr, Builtins.this.BaseStr));
         String[] var2 = Builtins.this.list("chdir", "fchdir", "putenv", "setegid", "seteuid", "setgid", "setgroups", "setpgrp", "setpgid", "setreuid", "setregid", "setuid", "unsetenv");
         int var3 = var2.length;

         int var4;
         String s;
         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.None);
         }

         var2 = Builtins.this.list("getegid", "getgid", "getpgid", "getpgrp", "getppid", "getuid", "getsid", "umask");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.BaseNum);
         }

         var2 = Builtins.this.list("getcwd", "ctermid", "getlogin", "getenv", "strerror");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.BaseStr);
         }

         this.addFunction("getgroups", this.liburl(a), Builtins.this.newList(Builtins.this.BaseStr));
         this.addFunction("uname", this.liburl(a), Builtins.this.newTuple(Builtins.this.BaseStr, Builtins.this.BaseStr, Builtins.this.BaseStr, Builtins.this.BaseStr, Builtins.this.BaseStr));
      }

      private void initProcMgmtBindings() {
         String a = "process-management";
         String[] var2 = Builtins.this.list("EX_CANTCREAT", "EX_CONFIG", "EX_DATAERR", "EX_IOERR", "EX_NOHOST", "EX_NOINPUT", "EX_NOPERM", "EX_NOUSER", "EX_OK", "EX_OSERR", "EX_OSFILE", "EX_PROTOCOL", "EX_SOFTWARE", "EX_TEMPFAIL", "EX_UNAVAILABLE", "EX_USAGE", "P_NOWAIT", "P_NOWAITO", "P_WAIT", "P_DETACH", "P_OVERLAY", "WCONTINUED", "WCOREDUMP", "WEXITSTATUS", "WIFCONTINUED", "WIFEXITED", "WIFSIGNALED", "WIFSTOPPED", "WNOHANG", "WSTOPSIG", "WTERMSIG", "WUNTRACED");
         int var3 = var2.length;

         int var4;
         String s;
         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addAttr(s, this.liburl(a), Builtins.this.BaseNum);
         }

         var2 = Builtins.this.list("abort", "execl", "execle", "execlp", "execlpe", "execv", "execve", "execvp", "execvpe", "_exit", "kill", "killpg", "plock", "startfile");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.None);
         }

         var2 = Builtins.this.list("nice", "spawnl", "spawnle", "spawnlp", "spawnlpe", "spawnv", "spawnve", "spawnvp", "spawnvpe", "system");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.BaseNum);
         }

         this.addFunction("fork", this.liburl(a), Builtins.this.newUnion(Builtins.this.BaseFile, Builtins.this.BaseNum));
         this.addFunction("times", this.liburl(a), Builtins.this.newTuple(Builtins.this.BaseNum, Builtins.this.BaseNum, Builtins.this.BaseNum, Builtins.this.BaseNum, Builtins.this.BaseNum));
         var2 = Builtins.this.list("forkpty", "wait", "waitpid");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.newTuple(Builtins.this.BaseNum, Builtins.this.BaseNum));
         }

         var2 = Builtins.this.list("wait3", "wait4");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.newTuple(Builtins.this.BaseNum, Builtins.this.BaseNum, Builtins.this.BaseNum));
         }

      }

      private void initFileBindings() {
         String a = "file-object-creation";
         String[] var2 = Builtins.this.list("fdopen", "popen", "tmpfile");
         int var3 = var2.length;

         int var4;
         String s;
         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.BaseFile);
         }

         this.addFunction("popen2", this.liburl(a), Builtins.this.newTuple(Builtins.this.BaseFile, Builtins.this.BaseFile));
         this.addFunction("popen3", this.liburl(a), Builtins.this.newTuple(Builtins.this.BaseFile, Builtins.this.BaseFile, Builtins.this.BaseFile));
         this.addFunction("popen4", this.liburl(a), Builtins.this.newTuple(Builtins.this.BaseFile, Builtins.this.BaseFile));
         a = "file-descriptor-operations";
         this.addFunction("open", this.liburl(a), Builtins.this.BaseFile);
         var2 = Builtins.this.list("close", "closerange", "dup2", "fchmod", "fchown", "fdatasync", "fsync", "ftruncate", "lseek", "tcsetpgrp", "write");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.None);
         }

         var2 = Builtins.this.list("dup2", "fpathconf", "fstat", "fstatvfs", "isatty", "tcgetpgrp");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.BaseNum);
         }

         var2 = Builtins.this.list("read", "ttyname");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.BaseStr);
         }

         var2 = Builtins.this.list("openpty", "pipe", "fstat", "fstatvfs", "isatty");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.newTuple(Builtins.this.BaseNum, Builtins.this.BaseNum));
         }

         var2 = Builtins.this.list("O_APPEND", "O_CREAT", "O_DIRECT", "O_DIRECTORY", "O_DSYNC", "O_EXCL", "O_LARGEFILE", "O_NDELAY", "O_NOCTTY", "O_NOFOLLOW", "O_NONBLOCK", "O_RDONLY", "O_RDWR", "O_RSYNC", "O_SYNC", "O_TRUNC", "O_WRONLY", "SEEK_CUR", "SEEK_END", "SEEK_SET");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addAttr(s, this.liburl(a), Builtins.this.BaseNum);
         }

      }

      private void initFileAndDirBindings() {
         String a = "files-and-directories";
         String[] var2 = Builtins.this.list("F_OK", "R_OK", "W_OK", "X_OK");
         int var3 = var2.length;

         int var4;
         String s;
         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addAttr(s, this.liburl(a), Builtins.this.BaseNum);
         }

         var2 = Builtins.this.list("chflags", "chroot", "chmod", "chown", "lchflags", "lchmod", "lchown", "link", "mknod", "mkdir", "mkdirs", "remove", "removedirs", "rename", "renames", "rmdir", "symlink", "unlink", "utime");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addAttr(s, this.liburl(a), Builtins.this.None);
         }

         var2 = Builtins.this.list("access", "lstat", "major", "minor", "makedev", "pathconf", "stat_float_times");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.BaseNum);
         }

         var2 = Builtins.this.list("getcwdu", "readlink", "tempnam", "tmpnam");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.BaseStr);
         }

         var2 = Builtins.this.list("listdir");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.newList(Builtins.this.BaseStr));
         }

         this.addFunction("mkfifo", this.liburl(a), Builtins.this.BaseFile);
         this.addFunction("stat", this.liburl(a), Builtins.this.newList(Builtins.this.BaseNum));
         this.addFunction("statvfs", this.liburl(a), Builtins.this.newList(Builtins.this.BaseNum));
         this.addAttr("pathconf_names", this.liburl(a), Builtins.this.newDict(Builtins.this.BaseStr, Builtins.this.BaseNum));
         this.addAttr("TMP_MAX", this.liburl(a), Builtins.this.BaseNum);
         this.addFunction("walk", this.liburl(a), Builtins.this.newList(Builtins.this.newTuple(Builtins.this.BaseStr, Builtins.this.BaseStr, Builtins.this.BaseStr)));
      }

      private void initMiscSystemInfo() {
         String a = "miscellaneous-system-information";
         this.addAttr("confstr_names", this.liburl(a), Builtins.this.newDict(Builtins.this.BaseStr, Builtins.this.BaseNum));
         this.addAttr("sysconf_names", this.liburl(a), Builtins.this.newDict(Builtins.this.BaseStr, Builtins.this.BaseNum));
         String[] var2 = Builtins.this.list("curdir", "pardir", "sep", "altsep", "extsep", "pathsep", "defpath", "linesep", "devnull");
         int var3 = var2.length;

         int var4;
         String s;
         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addAttr(s, this.liburl(a), Builtins.this.BaseStr);
         }

         var2 = Builtins.this.list("getloadavg", "sysconf");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            s = var2[var4];
            this.addFunction(s, this.liburl(a), Builtins.this.BaseNum);
         }

         this.addFunction("confstr", this.liburl(a), Builtins.this.BaseStr);
      }

      private void initOsPathModule() {
         NModuleType m = Builtins.this.newModule("path");
         Scope ospath = m.getTable();
         ospath.setPath("os.path");
         this.update("path", Builtins.newLibUrl("os.path.html#module-os.path"), m, NBinding.Kind.MODULE);
         String[] str_funcs = new String[]{"_resolve_link", "abspath", "basename", "commonprefix", "dirname", "expanduser", "expandvars", "join", "normcase", "normpath", "realpath", "relpath"};
         String[] num_funcs = str_funcs;
         int var5 = str_funcs.length;

         int var6;
         for(var6 = 0; var6 < var5; ++var6) {
            String s = num_funcs[var6];
            ospath.update(s, (NNode)Builtins.newLibUrl("os.path", s), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.FUNCTION);
         }

         num_funcs = new String[]{"exists", "lexists", "getatime", "getctime", "getmtime", "getsize", "isabs", "isdir", "isfile", "islink", "ismount", "samefile", "sameopenfile", "samestat", "supports_unicode_filenames"};
         String[] var11 = num_funcs;
         var6 = num_funcs.length;

         String sx;
         int var13;
         for(var13 = 0; var13 < var6; ++var13) {
            sx = var11[var13];
            ospath.update(sx, (NNode)Builtins.newLibUrl("os.path", sx), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.FUNCTION);
         }

         var11 = Builtins.this.list("split", "splitdrive", "splitext", "splitunc");
         var6 = var11.length;

         for(var13 = 0; var13 < var6; ++var13) {
            sx = var11[var13];
            ospath.update(sx, (NNode)Builtins.newLibUrl("os.path", sx), Builtins.this.newFunc(Builtins.this.newTuple(Builtins.this.BaseStr, Builtins.this.BaseStr)), NBinding.Kind.FUNCTION);
         }

         NBinding b = ospath.update("walk", (NNode)Builtins.newLibUrl("os.path"), Builtins.this.newFunc(Builtins.this.None), NBinding.Kind.FUNCTION);
         b.markDeprecated();
         String[] str_attrs = new String[]{"altsep", "curdir", "devnull", "defpath", "pardir", "pathsep", "sep"};
         String[] var15 = str_attrs;
         int var16 = str_attrs.length;

         for(int var9 = 0; var9 < var16; ++var9) {
            String sxx = var15[var9];
            ospath.update(sxx, (NNode)Builtins.newLibUrl("os.path", sxx), Builtins.this.BaseStr, NBinding.Kind.ATTRIBUTE);
         }

         ospath.update("os", (NNode)this.liburl(), this.module, NBinding.Kind.ATTRIBUTE);
         ospath.update("stat", (NNode)Builtins.newLibUrl("stat"), Builtins.this.newModule("<stat-fixme>"), NBinding.Kind.ATTRIBUTE);
         ospath.update("_varprog", (NNode)Builtins.newLibUrl("os.path"), Builtins.this.unknown(), NBinding.Kind.ATTRIBUTE);
      }
   }

   class NisModule extends NativeModule {
      public NisModule() {
         super("nis");
      }

      public void initBindings() {
         this.addStrFuncs(new String[]{"match", "cat", "get_default_domain"});
         this.addFunction("maps", this.liburl(), Builtins.this.newList(Builtins.this.BaseStr));
         this.addClass("error", this.liburl(), Builtins.this.newException("error", this.table));
      }
   }

   class MmapModule extends NativeModule {
      public MmapModule() {
         super("mmap");
      }

      public void initBindings() {
         NClassType mmap = Builtins.this.newClass("mmap", this.table, Builtins.this.Object);
         String[] var2 = Builtins.this.list("ACCESS_COPY", "ACCESS_READ", "ACCESS_WRITE", "ALLOCATIONGRANULARITY", "MAP_ANON", "MAP_ANONYMOUS", "MAP_DENYWRITE", "MAP_EXECUTABLE", "MAP_PRIVATE", "MAP_SHARED", "PAGESIZE", "PROT_EXEC", "PROT_READ", "PROT_WRITE");
         int var3 = var2.length;

         int var4;
         String fnone;
         for(var4 = 0; var4 < var3; ++var4) {
            fnone = var2[var4];
            mmap.getTable().update(fnone, (NNode)this.liburl(), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         }

         var2 = Builtins.this.list("read", "read_byte", "readline");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            fnone = var2[var4];
            mmap.getTable().update(fnone, (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         }

         var2 = Builtins.this.list("find", "rfind", "tell");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            fnone = var2[var4];
            mmap.getTable().update(fnone, (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         }

         var2 = Builtins.this.list("close", "flush", "move", "resize", "seek", "write", "write_byte");
         var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            fnone = var2[var4];
            mmap.getTable().update(fnone, (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.None), NBinding.Kind.METHOD);
         }

         this.addClass("mmap", this.liburl(), mmap);
      }
   }

   class Md5Module extends NativeModule {
      public Md5Module() {
         super("md5");
      }

      public void initBindings() {
         this.addNumAttrs(new String[]{"blocksize", "digest_size"});
         NClassType md5 = Builtins.this.newClass("md5", this.table, Builtins.this.Object);
         md5.getTable().update("update", (NNode)this.liburl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         md5.getTable().update("digest", (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         md5.getTable().update("hexdigest", (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         md5.getTable().update("copy", (NNode)this.liburl(), Builtins.this.newFunc(md5), NBinding.Kind.METHOD);
         this.update("new", this.liburl(), Builtins.this.newFunc(md5), NBinding.Kind.CONSTRUCTOR);
         this.update("md5", this.liburl(), Builtins.this.newFunc(md5), NBinding.Kind.CONSTRUCTOR);
      }
   }

   class MathModule extends NativeModule {
      public MathModule() {
         super("math");
      }

      public void initBindings() {
         this.addNumFuncs(new String[]{"acos", "acosh", "asin", "asinh", "atan", "atan2", "atanh", "ceil", "copysign", "cos", "cosh", "degrees", "exp", "fabs", "factorial", "floor", "fmod", "frexp", "fsum", "hypot", "isinf", "isnan", "ldexp", "log", "log10", "log1p", "modf", "pow", "radians", "sin", "sinh", "sqrt", "tan", "tanh", "trunc"});
         this.addNumAttrs(new String[]{"pi", "e"});
      }
   }

   class MarshalModule extends NativeModule {
      public MarshalModule() {
         super("marshal");
      }

      public void initBindings() {
         this.addNumAttrs(new String[]{"version"});
         this.addStrFuncs(new String[]{"dumps"});
         this.addUnknownFuncs(new String[]{"dump", "load", "loads"});
      }
   }

   class ItertoolsModule extends NativeModule {
      public ItertoolsModule() {
         super("itertools");
      }

      public void initBindings() {
         NClassType iterator = Builtins.this.newClass("iterator", this.table, Builtins.this.Object);
         iterator.getTable().update("from_iterable", (NNode)this.liburl("itertool-functions"), Builtins.this.newFunc(iterator), NBinding.Kind.METHOD);
         iterator.getTable().update("next", (NNode)this.liburl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         String[] var2 = Builtins.this.list("chain", "combinations", "count", "cycle", "dropwhile", "groupby", "ifilter", "ifilterfalse", "imap", "islice", "izip", "izip_longest", "permutations", "product", "repeat", "starmap", "takewhile", "tee");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            this.addClass(s, this.liburl("itertool-functions"), iterator);
         }

      }
   }

   class ImpModule extends NativeModule {
      public ImpModule() {
         super("imp");
      }

      public void initBindings() {
         this.addStrFuncs(new String[]{"get_magic"});
         this.addFunction("get_suffixes", this.liburl(), Builtins.this.newList(Builtins.this.newTuple(Builtins.this.BaseStr, Builtins.this.BaseStr, Builtins.this.BaseNum)));
         this.addFunction("find_module", this.liburl(), Builtins.this.newTuple(Builtins.this.BaseStr, Builtins.this.BaseStr, Builtins.this.BaseNum));
         String[] module_methods = new String[]{"load_module", "new_module", "init_builtin", "init_frozen", "load_compiled", "load_dynamic", "load_source"};
         String[] var2 = module_methods;
         int var3 = module_methods.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String mm = var2[var4];
            this.addFunction(mm, this.liburl(), Builtins.this.newModule("<?>"));
         }

         this.addUnknownFuncs(new String[]{"acquire_lock", "release_lock"});
         this.addNumAttrs(new String[]{"PY_SOURCE", "PY_COMPILED", "C_EXTENSION", "PKG_DIRECTORY", "C_BUILTIN", "PY_FROZEN", "SEARCH_ERROR"});
         this.addNumFuncs(new String[]{"lock_held", "is_builtin", "is_frozen"});
         NClassType impNullImporter = Builtins.this.newClass("NullImporter", this.table, Builtins.this.Object);
         impNullImporter.getTable().update("find_module", (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.None), NBinding.Kind.FUNCTION);
         this.addClass("NullImporter", this.liburl(), impNullImporter);
      }
   }

   class GrpModule extends NativeModule {
      public GrpModule() {
         super("grp");
      }

      public void initBindings() {
         Builtins.this.get("struct");
         NClassType struct_group = Builtins.this.newClass("struct_group", this.table, Builtins.this.BaseStruct);
         struct_group.getTable().update("gr_name", (NNode)this.liburl(), Builtins.this.BaseStr, NBinding.Kind.ATTRIBUTE);
         struct_group.getTable().update("gr_passwd", (NNode)this.liburl(), Builtins.this.BaseStr, NBinding.Kind.ATTRIBUTE);
         struct_group.getTable().update("gr_gid", (NNode)this.liburl(), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         struct_group.getTable().update("gr_mem", (NNode)this.liburl(), Builtins.this.newList(Builtins.this.BaseStr), NBinding.Kind.ATTRIBUTE);
         this.addClass("struct_group", this.liburl(), struct_group);
         String[] var2 = Builtins.this.list("getgrgid", "getgrnam");
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            this.addFunction(s, this.liburl(), struct_group);
         }

         this.addFunction("getgrall", this.liburl(), new NListType(struct_group));
      }
   }

   class GdbmModule extends NativeModule {
      public GdbmModule() {
         super("gdbm");
      }

      public void initBindings() {
         this.addClass("error", this.liburl(), Builtins.this.newException("error", this.table));
         NClassType gdbm = new NClassType("gdbm", this.table, Builtins.this.BaseDict);
         gdbm.getTable().update("firstkey", (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         gdbm.getTable().update("nextkey", (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         gdbm.getTable().update("reorganize", (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.None), NBinding.Kind.METHOD);
         gdbm.getTable().update("sync", (NNode)this.liburl(), Builtins.this.newFunc(Builtins.this.None), NBinding.Kind.METHOD);
         this.addFunction("open", this.liburl(), gdbm);
      }
   }

   class GcModule extends NativeModule {
      public GcModule() {
         super("gc");
      }

      public void initBindings() {
         this.addNoneFuncs(new String[]{"enable", "disable", "set_debug", "set_threshold"});
         this.addNumFuncs(new String[]{"isenabled", "collect", "get_debug", "get_count", "get_threshold"});
         String[] var1 = Builtins.this.list("get_objects", "get_referrers", "get_referents");
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            this.addFunction(s, this.liburl(), Builtins.this.newList());
         }

         this.addAttr("garbage", this.liburl(), Builtins.this.newList());
         this.addNumAttrs(new String[]{"DEBUG_STATS", "DEBUG_COLLECTABLE", "DEBUG_UNCOLLECTABLE", "DEBUG_INSTANCES", "DEBUG_OBJECTS", "DEBUG_SAVEALL", "DEBUG_LEAK"});
      }
   }

   class FpectlModule extends NativeModule {
      public FpectlModule() {
         super("fpectl");
      }

      public void initBindings() {
         this.addNoneFuncs(new String[]{"turnon_sigfpe", "turnoff_sigfpe"});
         this.addClass("FloatingPointError", this.liburl(), Builtins.this.newException("FloatingPointError", this.table));
      }
   }

   class FcntlModule extends NativeModule {
      public FcntlModule() {
         super("fcntl");
      }

      public void initBindings() {
         String[] var1 = Builtins.this.list("fcntl", "ioctl");
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            this.addFunction(s, this.liburl(), Builtins.this.newUnion(Builtins.this.BaseNum, Builtins.this.BaseStr));
         }

         this.addNumFuncs(new String[]{"flock"});
         this.addUnknownFuncs(new String[]{"lockf"});
         this.addNumAttrs(new String[]{"DN_ACCESS", "DN_ATTRIB", "DN_CREATE", "DN_DELETE", "DN_MODIFY", "DN_MULTISHOT", "DN_RENAME", "FASYNC", "FD_CLOEXEC", "F_DUPFD", "F_EXLCK", "F_GETFD", "F_GETFL", "F_GETLEASE", "F_GETLK", "F_GETLK64", "F_GETOWN", "F_GETSIG", "F_NOTIFY", "F_RDLCK", "F_SETFD", "F_SETFL", "F_SETLEASE", "F_SETLK", "F_SETLK64", "F_SETLKW", "F_SETLKW64", "F_SETOWN", "F_SETSIG", "F_SHLCK", "F_UNLCK", "F_WRLCK", "I_ATMARK", "I_CANPUT", "I_CKBAND", "I_FDINSERT", "I_FIND", "I_FLUSH", "I_FLUSHBAND", "I_GETBAND", "I_GETCLTIME", "I_GETSIG", "I_GRDOPT", "I_GWROPT", "I_LINK", "I_LIST", "I_LOOK", "I_NREAD", "I_PEEK", "I_PLINK", "I_POP", "I_PUNLINK", "I_PUSH", "I_RECVFD", "I_SENDFD", "I_SETCLTIME", "I_SETSIG", "I_SRDOPT", "I_STR", "I_SWROPT", "I_UNLINK", "LOCK_EX", "LOCK_MAND", "LOCK_NB", "LOCK_READ", "LOCK_RW", "LOCK_SH", "LOCK_UN", "LOCK_WRITE"});
      }
   }

   class ExceptionsModule extends NativeModule {
      public ExceptionsModule() {
         super("exceptions");
      }

      public void initBindings() {
         NModuleType builtins = Builtins.this.get("__builtin__");
         String[] var2 = Builtins.this.builtin_exception_types;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            NBinding b = builtins.getTable().lookup(s);
            this.table.update(b.getName(), b.getSignatureNode(), b.getType(), b.getKind());
         }

      }
   }

   class ErrnoModule extends NativeModule {
      public ErrnoModule() {
         super("errno");
      }

      public void initBindings() {
         this.addNumAttrs(new String[]{"E2BIG", "EACCES", "EADDRINUSE", "EADDRNOTAVAIL", "EAFNOSUPPORT", "EAGAIN", "EALREADY", "EBADF", "EBUSY", "ECHILD", "ECONNABORTED", "ECONNREFUSED", "ECONNRESET", "EDEADLK", "EDEADLOCK", "EDESTADDRREQ", "EDOM", "EDQUOT", "EEXIST", "EFAULT", "EFBIG", "EHOSTDOWN", "EHOSTUNREACH", "EILSEQ", "EINPROGRESS", "EINTR", "EINVAL", "EIO", "EISCONN", "EISDIR", "ELOOP", "EMFILE", "EMLINK", "EMSGSIZE", "ENAMETOOLONG", "ENETDOWN", "ENETRESET", "ENETUNREACH", "ENFILE", "ENOBUFS", "ENODEV", "ENOENT", "ENOEXEC", "ENOLCK", "ENOMEM", "ENOPROTOOPT", "ENOSPC", "ENOSYS", "ENOTCONN", "ENOTDIR", "ENOTEMPTY", "ENOTSOCK", "ENOTTY", "ENXIO", "EOPNOTSUPP", "EPERM", "EPFNOSUPPORT", "EPIPE", "EPROTONOSUPPORT", "EPROTOTYPE", "ERANGE", "EREMOTE", "EROFS", "ESHUTDOWN", "ESOCKTNOSUPPORT", "ESPIPE", "ESRCH", "ESTALE", "ETIMEDOUT", "ETOOMANYREFS", "EUSERS", "EWOULDBLOCK", "EXDEV", "WSABASEERR", "WSAEACCES", "WSAEADDRINUSE", "WSAEADDRNOTAVAIL", "WSAEAFNOSUPPORT", "WSAEALREADY", "WSAEBADF", "WSAECONNABORTED", "WSAECONNREFUSED", "WSAECONNRESET", "WSAEDESTADDRREQ", "WSAEDISCON", "WSAEDQUOT", "WSAEFAULT", "WSAEHOSTDOWN", "WSAEHOSTUNREACH", "WSAEINPROGRESS", "WSAEINTR", "WSAEINVAL", "WSAEISCONN", "WSAELOOP", "WSAEMFILE", "WSAEMSGSIZE", "WSAENAMETOOLONG", "WSAENETDOWN", "WSAENETRESET", "WSAENETUNREACH", "WSAENOBUFS", "WSAENOPROTOOPT", "WSAENOTCONN", "WSAENOTEMPTY", "WSAENOTSOCK", "WSAEOPNOTSUPP", "WSAEPFNOSUPPORT", "WSAEPROCLIM", "WSAEPROTONOSUPPORT", "WSAEPROTOTYPE", "WSAEREMOTE", "WSAESHUTDOWN", "WSAESOCKTNOSUPPORT", "WSAESTALE", "WSAETIMEDOUT", "WSAETOOMANYREFS", "WSAEUSERS", "WSAEWOULDBLOCK", "WSANOTINITIALISED", "WSASYSNOTREADY", "WSAVERNOTSUPPORTED"});
         this.addAttr("errorcode", this.liburl("errorcode"), Builtins.this.newDict(Builtins.this.BaseNum, Builtins.this.BaseStr));
      }
   }

   class DbmModule extends NativeModule {
      public DbmModule() {
         super("dbm");
      }

      public void initBindings() {
         NClassType dbm = new NClassType("dbm", this.table, Builtins.this.BaseDict);
         this.addClass("dbm", this.liburl(), dbm);
         this.addClass("error", this.liburl(), Builtins.this.newException("error", this.table));
         this.addStrAttrs(new String[]{"library"});
         this.addFunction("open", this.liburl(), dbm);
      }
   }

   class DatetimeModule extends NativeModule {
      public DatetimeModule() {
         super("datetime");
      }

      private NUrl dtUrl(String anchor) {
         return this.liburl("datetime." + anchor);
      }

      public void initBindings() {
         this.addNumAttrs(new String[]{"MINYEAR", "MAXYEAR"});
         NClassType timedelta = Builtins.this.Datetime_timedelta = Builtins.this.newClass("timedelta", this.table, Builtins.this.Object);
         this.addClass("timedelta", this.dtUrl("timedelta"), timedelta);
         Scope tdtable = Builtins.this.Datetime_timedelta.getTable();
         tdtable.update("min", (NNode)this.dtUrl("timedelta"), timedelta, NBinding.Kind.ATTRIBUTE);
         tdtable.update("max", (NNode)this.dtUrl("timedelta"), timedelta, NBinding.Kind.ATTRIBUTE);
         tdtable.update("resolution", (NNode)this.dtUrl("timedelta"), timedelta, NBinding.Kind.ATTRIBUTE);
         tdtable.update("days", (NNode)this.dtUrl("timedelta"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         tdtable.update("seconds", (NNode)this.dtUrl("timedelta"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         tdtable.update("microseconds", (NNode)this.dtUrl("timedelta"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         NClassType tzinfo = Builtins.this.Datetime_tzinfo = Builtins.this.newClass("tzinfo", this.table, Builtins.this.Object);
         this.addClass("tzinfo", this.dtUrl("tzinfo"), tzinfo);
         Scope tztable = Builtins.this.Datetime_tzinfo.getTable();
         tztable.update("utcoffset", (NNode)this.dtUrl("tzinfo"), Builtins.this.newFunc(timedelta), NBinding.Kind.METHOD);
         tztable.update("dst", (NNode)this.dtUrl("tzinfo"), Builtins.this.newFunc(timedelta), NBinding.Kind.METHOD);
         tztable.update("tzname", (NNode)this.dtUrl("tzinfo"), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         tztable.update("fromutc", (NNode)this.dtUrl("tzinfo"), Builtins.this.newFunc(tzinfo), NBinding.Kind.METHOD);
         NClassType date = Builtins.this.Datetime_date = Builtins.this.newClass("date", this.table, Builtins.this.Object);
         this.addClass("date", this.dtUrl("date"), date);
         Scope dtable = Builtins.this.Datetime_date.getTable();
         dtable.update("min", (NNode)this.dtUrl("date"), date, NBinding.Kind.ATTRIBUTE);
         dtable.update("max", (NNode)this.dtUrl("date"), date, NBinding.Kind.ATTRIBUTE);
         dtable.update("resolution", (NNode)this.dtUrl("date"), timedelta, NBinding.Kind.ATTRIBUTE);
         dtable.update("today", (NNode)this.dtUrl("date"), Builtins.this.newFunc(date), NBinding.Kind.METHOD);
         dtable.update("fromtimestamp", (NNode)this.dtUrl("date"), Builtins.this.newFunc(date), NBinding.Kind.METHOD);
         dtable.update("fromordinal", (NNode)this.dtUrl("date"), Builtins.this.newFunc(date), NBinding.Kind.METHOD);
         dtable.update("year", (NNode)this.dtUrl("date"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         dtable.update("month", (NNode)this.dtUrl("date"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         dtable.update("day", (NNode)this.dtUrl("date"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         dtable.update("replace", (NNode)this.dtUrl("date"), Builtins.this.newFunc(date), NBinding.Kind.METHOD);
         dtable.update("timetuple", (NNode)this.dtUrl("date"), Builtins.this.newFunc(Builtins.this.Time_struct_time), NBinding.Kind.METHOD);
         String[] var7 = Builtins.this.list("toordinal", "weekday", "isoweekday");
         int var8 = var7.length;

         int var9;
         String r;
         for(var9 = 0; var9 < var8; ++var9) {
            r = var7[var9];
            dtable.update(r, (NNode)this.dtUrl("date"), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         }

         var7 = Builtins.this.list("ctime", "strftime", "isoformat");
         var8 = var7.length;

         for(var9 = 0; var9 < var8; ++var9) {
            r = var7[var9];
            dtable.update(r, (NNode)this.dtUrl("date"), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         }

         dtable.update("isocalendar", (NNode)this.dtUrl("date"), Builtins.this.newFunc(Builtins.this.newTuple(Builtins.this.BaseNum, Builtins.this.BaseNum, Builtins.this.BaseNum)), NBinding.Kind.METHOD);
         NClassType time = Builtins.this.Datetime_time = Builtins.this.newClass("time", this.table, Builtins.this.Object);
         this.addClass("time", this.dtUrl("time"), date);
         Scope ttable = Builtins.this.Datetime_time.getTable();
         ttable.update("min", (NNode)this.dtUrl("time"), time, NBinding.Kind.ATTRIBUTE);
         ttable.update("max", (NNode)this.dtUrl("time"), time, NBinding.Kind.ATTRIBUTE);
         ttable.update("resolution", (NNode)this.dtUrl("time"), timedelta, NBinding.Kind.ATTRIBUTE);
         ttable.update("hour", (NNode)this.dtUrl("time"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         ttable.update("minute", (NNode)this.dtUrl("time"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         ttable.update("second", (NNode)this.dtUrl("time"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         ttable.update("microsecond", (NNode)this.dtUrl("time"), Builtins.this.BaseNum, NBinding.Kind.ATTRIBUTE);
         ttable.update("tzinfo", (NNode)this.dtUrl("time"), tzinfo, NBinding.Kind.ATTRIBUTE);
         ttable.update("replace", (NNode)this.dtUrl("time"), Builtins.this.newFunc(time), NBinding.Kind.METHOD);
         String[] var17 = Builtins.this.list("isoformat", "strftime", "tzname");
         int var18 = var17.length;

         int var11;
         String f;
         for(var11 = 0; var11 < var18; ++var11) {
            f = var17[var11];
            ttable.update(f, (NNode)this.dtUrl("time"), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         }

         var17 = Builtins.this.list("utcoffset", "dst");
         var18 = var17.length;

         for(var11 = 0; var11 < var18; ++var11) {
            f = var17[var11];
            ttable.update(f, (NNode)this.dtUrl("time"), Builtins.this.newFunc(timedelta), NBinding.Kind.METHOD);
         }

         NClassType datetime = Builtins.this.Datetime_datetime = Builtins.this.newClass("datetime", this.table, date, time);
         this.addClass("datetime", this.dtUrl("datetime"), datetime);
         Scope dttable = Builtins.this.Datetime_datetime.getTable();
         String[] var21 = Builtins.this.list("combine", "fromordinal", "fromtimestamp", "now", "strptime", "today", "utcfromtimestamp", "utcnow");
         int var22 = var21.length;

         int var13;
         String y;
         for(var13 = 0; var13 < var22; ++var13) {
            y = var21[var13];
            dttable.update(y, (NNode)this.dtUrl("datetime"), Builtins.this.newFunc(datetime), NBinding.Kind.METHOD);
         }

         dttable.update("min", (NNode)this.dtUrl("datetime"), datetime, NBinding.Kind.ATTRIBUTE);
         dttable.update("max", (NNode)this.dtUrl("datetime"), datetime, NBinding.Kind.ATTRIBUTE);
         dttable.update("resolution", (NNode)this.dtUrl("datetime"), timedelta, NBinding.Kind.ATTRIBUTE);
         dttable.update("date", (NNode)this.dtUrl("datetime"), Builtins.this.newFunc(date), NBinding.Kind.METHOD);
         var21 = Builtins.this.list("time", "timetz");
         var22 = var21.length;

         for(var13 = 0; var13 < var22; ++var13) {
            y = var21[var13];
            dttable.update(y, (NNode)this.dtUrl("datetime"), Builtins.this.newFunc(time), NBinding.Kind.METHOD);
         }

         var21 = Builtins.this.list("replace", "astimezone");
         var22 = var21.length;

         for(var13 = 0; var13 < var22; ++var13) {
            y = var21[var13];
            dttable.update(y, (NNode)this.dtUrl("datetime"), Builtins.this.newFunc(datetime), NBinding.Kind.METHOD);
         }

         dttable.update("utctimetuple", (NNode)this.dtUrl("datetime"), Builtins.this.newFunc(Builtins.this.Time_struct_time), NBinding.Kind.METHOD);
      }
   }

   class CryptModule extends NativeModule {
      public CryptModule() {
         super("crypt");
      }

      public void initBindings() {
         this.addStrFuncs(new String[]{"crypt"});
      }
   }

   class CTypesModule extends NativeModule {
      public CTypesModule() {
         super("ctypes");
      }

      public void initBindings() {
         String[] ctypes_attrs = new String[]{"ARRAY", "ArgumentError", "Array", "BigEndianStructure", "CDLL", "CFUNCTYPE", "DEFAULT_MODE", "DllCanUnloadNow", "DllGetClassObject", "FormatError", "GetLastError", "HRESULT", "LibraryLoader", "LittleEndianStructure", "OleDLL", "POINTER", "PYFUNCTYPE", "PyDLL", "RTLD_GLOBAL", "RTLD_LOCAL", "SetPointerType", "Structure", "Union", "WINFUNCTYPE", "WinDLL", "WinError", "_CFuncPtr", "_FUNCFLAG_CDECL", "_FUNCFLAG_PYTHONAPI", "_FUNCFLAG_STDCALL", "_FUNCFLAG_USE_ERRNO", "_FUNCFLAG_USE_LASTERROR", "_Pointer", "_SimpleCData", "_c_functype_cache", "_calcsize", "_cast", "_cast_addr", "_check_HRESULT", "_check_size", "_ctypes_version", "_dlopen", "_endian", "_memmove_addr", "_memset_addr", "_os", "_pointer_type_cache", "_string_at", "_string_at_addr", "_sys", "_win_functype_cache", "_wstring_at", "_wstring_at_addr", "addressof", "alignment", "byref", "c_bool", "c_buffer", "c_byte", "c_char", "c_char_p", "c_double", "c_float", "c_int", "c_int16", "c_int32", "c_int64", "c_int8", "c_long", "c_longdouble", "c_longlong", "c_short", "c_size_t", "c_ubyte", "c_uint", "c_uint16", "c_uint32", "c_uint64", "c_uint8", "c_ulong", "c_ulonglong", "c_ushort", "c_void_p", "c_voidp", "c_wchar", "c_wchar_p", "cast", "cdll", "create_string_buffer", "create_unicode_buffer", "get_errno", "get_last_error", "memmove", "memset", "oledll", "pointer", "py_object", "pydll", "pythonapi", "resize", "set_conversion_mode", "set_errno", "set_last_error", "sizeof", "string_at", "windll", "wstring_at"};
         String[] var2 = ctypes_attrs;
         int var3 = ctypes_attrs.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String attr = var2[var4];
            this.addAttr(attr, this.liburl(attr), Builtins.this.unknown());
         }

      }
   }

   class CollectionsModule extends NativeModule {
      public CollectionsModule() {
         super("collections");
      }

      private NUrl abcUrl() {
         return this.liburl("abcs-abstract-base-classes");
      }

      private NUrl dequeUrl() {
         return this.liburl("deque-objects");
      }

      public void initBindings() {
         NClassType Callable = Builtins.this.newClass("Callable", this.table, Builtins.this.Object);
         Callable.getTable().update("__call__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("Callable", this.abcUrl(), Callable);
         NClassType Iterable = Builtins.this.newClass("Iterable", this.table, Builtins.this.Object);
         Iterable.getTable().update("__next__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         Iterable.getTable().update("__iter__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("Iterable", this.abcUrl(), Iterable);
         NClassType Hashable = Builtins.this.newClass("Hashable", this.table, Builtins.this.Object);
         Hashable.getTable().update("__hash__", (NNode)this.abcUrl(), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         this.addClass("Hashable", this.abcUrl(), Hashable);
         NClassType Sized = Builtins.this.newClass("Sized", this.table, Builtins.this.Object);
         Sized.getTable().update("__len__", (NNode)this.abcUrl(), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         this.addClass("Sized", this.abcUrl(), Sized);
         NClassType Container = Builtins.this.newClass("Container", this.table, Builtins.this.Object);
         Container.getTable().update("__contains__", (NNode)this.abcUrl(), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         this.addClass("Container", this.abcUrl(), Container);
         NClassType Iterator = Builtins.this.newClass("Iterator", this.table, Iterable);
         this.addClass("Iterator", this.abcUrl(), Iterator);
         NClassType Sequence = Builtins.this.newClass("Sequence", this.table, Sized, Iterable, Container);
         Sequence.getTable().update("__getitem__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         Sequence.getTable().update("reversed", (NNode)this.abcUrl(), Builtins.this.newFunc(Sequence), NBinding.Kind.METHOD);
         Sequence.getTable().update("index", (NNode)this.abcUrl(), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         Sequence.getTable().update("count", (NNode)this.abcUrl(), Builtins.this.newFunc(Builtins.this.BaseNum), NBinding.Kind.METHOD);
         this.addClass("Sequence", this.abcUrl(), Sequence);
         NClassType MutableSequence = Builtins.this.newClass("MutableSequence", this.table, Sequence);
         MutableSequence.getTable().update("__setitem__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         MutableSequence.getTable().update("__delitem__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("MutableSequence", this.abcUrl(), MutableSequence);
         NClassType Set = Builtins.this.newClass("Set", this.table, Sized, Iterable, Container);
         Set.getTable().update("__getitem__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("Set", this.abcUrl(), Set);
         NClassType MutableSet = Builtins.this.newClass("MutableSet", this.table, Set);
         MutableSet.getTable().update("add", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         MutableSet.getTable().update("discard", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("MutableSet", this.abcUrl(), MutableSet);
         NClassType Mapping = Builtins.this.newClass("Mapping", this.table, Sized, Iterable, Container);
         Mapping.getTable().update("__getitem__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("Mapping", this.abcUrl(), Mapping);
         NClassType MutableMapping = Builtins.this.newClass("MutableMapping", this.table, Mapping);
         MutableMapping.getTable().update("__setitem__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         MutableMapping.getTable().update("__delitem__", (NNode)this.abcUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("MutableMapping", this.abcUrl(), MutableMapping);
         NClassType MappingView = Builtins.this.newClass("MappingView", this.table, Sized);
         this.addClass("MappingView", this.abcUrl(), MappingView);
         NClassType KeysView = Builtins.this.newClass("KeysView", this.table, Sized);
         this.addClass("KeysView", this.abcUrl(), KeysView);
         NClassType ItemsView = Builtins.this.newClass("ItemsView", this.table, Sized);
         this.addClass("ItemsView", this.abcUrl(), ItemsView);
         NClassType ValuesView = Builtins.this.newClass("ValuesView", this.table, Sized);
         this.addClass("ValuesView", this.abcUrl(), ValuesView);
         NClassType deque = Builtins.this.newClass("deque", this.table, Builtins.this.Object);
         String[] var18 = Builtins.this.list("append", "appendLeft", "clear", "extend", "extendLeft", "rotate");
         int var19 = var18.length;

         int var20;
         String u;
         for(var20 = 0; var20 < var19; ++var20) {
            u = var18[var20];
            deque.getTable().update(u, (NNode)this.dequeUrl(), Builtins.this.newFunc(Builtins.this.None), NBinding.Kind.METHOD);
         }

         var18 = Builtins.this.list("__getitem__", "__iter__", "pop", "popleft", "remove");
         var19 = var18.length;

         for(var20 = 0; var20 < var19; ++var20) {
            u = var18[var20];
            deque.getTable().update(u, (NNode)this.dequeUrl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         }

         this.addClass("deque", this.dequeUrl(), deque);
         NClassType defaultdict = Builtins.this.newClass("defaultdict", this.table, Builtins.this.Object);
         defaultdict.getTable().update("__missing__", (NNode)this.liburl("defaultdict-objects"), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         defaultdict.getTable().update("default_factory", (NNode)this.liburl("defaultdict-objects"), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("defaultdict", this.liburl("defaultdict-objects"), defaultdict);
         String argh = "namedtuple-factory-function-for-tuples-with-named-fields";
         NClassType namedtuple = Builtins.this.newClass("(namedtuple)", this.table, Builtins.this.BaseTuple);
         namedtuple.getTable().update("_fields", (NNode)this.liburl(argh), new NListType(Builtins.this.BaseStr), NBinding.Kind.ATTRIBUTE);
         this.addFunction("namedtuple", this.liburl(argh), namedtuple);
      }
   }

   class CMathModule extends NativeModule {
      public CMathModule() {
         super("cmath");
      }

      public void initBindings() {
         this.addFunction("phase", this.liburl("conversions-to-and-from-polar-coordinates"), Builtins.this.BaseNum);
         this.addFunction("polar", this.liburl("conversions-to-and-from-polar-coordinates"), Builtins.this.newTuple(Builtins.this.BaseNum, Builtins.this.BaseNum));
         this.addFunction("rect", this.liburl("conversions-to-and-from-polar-coordinates"), Builtins.this.BaseComplex);
         String[] var1 = Builtins.this.list("exp", "log", "log10", "sqrt");
         int var2 = var1.length;

         int var3;
         String c;
         for(var3 = 0; var3 < var2; ++var3) {
            c = var1[var3];
            this.addFunction(c, this.liburl("power-and-logarithmic-functions"), Builtins.this.BaseNum);
         }

         var1 = Builtins.this.list("acos", "asin", "atan", "cos", "sin", "tan");
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            c = var1[var3];
            this.addFunction(c, this.liburl("trigonometric-functions"), Builtins.this.BaseNum);
         }

         var1 = Builtins.this.list("acosh", "asinh", "atanh", "cosh", "sinh", "tanh");
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            c = var1[var3];
            this.addFunction(c, this.liburl("hyperbolic-functions"), Builtins.this.BaseComplex);
         }

         var1 = Builtins.this.list("isinf", "isnan");
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            c = var1[var3];
            this.addFunction(c, this.liburl("classification-functions"), Builtins.this.BaseBool);
         }

         var1 = Builtins.this.list("pi", "e");
         var2 = var1.length;

         for(var3 = 0; var3 < var2; ++var3) {
            c = var1[var3];
            this.addAttr(c, this.liburl("constants"), Builtins.this.BaseNum);
         }

      }
   }

   class CStringIOModule extends NativeModule {
      public CStringIOModule() {
         super("cStringIO");
      }

      protected NUrl liburl() {
         return Builtins.newLibUrl("stringio");
      }

      protected NUrl liburl(String anchor) {
         return Builtins.newLibUrl("stringio", anchor);
      }

      public void initBindings() {
         NClassType StringIO = Builtins.this.newClass("StringIO", this.table, Builtins.this.BaseFile);
         this.addFunction("StringIO", this.liburl(), StringIO);
         this.addAttr("InputType", this.liburl(), Builtins.this.Type);
         this.addAttr("OutputType", this.liburl(), Builtins.this.Type);
         this.addAttr("cStringIO_CAPI", this.liburl(), Builtins.this.unknown());
      }
   }

   class CPickleModule extends NativeModule {
      public CPickleModule() {
         super("cPickle");
      }

      protected NUrl liburl() {
         return Builtins.newLibUrl("pickle", "module-cPickle");
      }

      public void initBindings() {
         this.addUnknownFuncs(new String[]{"dump", "load", "dumps", "loads"});
         this.addClass("PickleError", this.liburl(), Builtins.this.newException("PickleError", this.table));
         NClassType picklingError = Builtins.this.newException("PicklingError", this.table);
         this.addClass("PicklingError", this.liburl(), picklingError);
         this.update("UnpickleableError", this.liburl(), Builtins.this.newClass("UnpickleableError", this.table, picklingError), NBinding.Kind.CLASS);
         NClassType unpicklingError = Builtins.this.newException("UnpicklingError", this.table);
         this.addClass("UnpicklingError", this.liburl(), unpicklingError);
         this.update("BadPickleGet", this.liburl(), Builtins.this.newClass("BadPickleGet", this.table, unpicklingError), NBinding.Kind.CLASS);
         NClassType pickler = Builtins.this.newClass("Pickler", this.table, Builtins.this.Object);
         pickler.getTable().update("dump", (NNode)this.liburl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         pickler.getTable().update("clear_memo", (NNode)this.liburl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("Pickler", this.liburl(), pickler);
         NClassType unpickler = Builtins.this.newClass("Unpickler", this.table, Builtins.this.Object);
         unpickler.getTable().update("load", (NNode)this.liburl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         unpickler.getTable().update("noload", (NNode)this.liburl(), Builtins.this.newFunc(), NBinding.Kind.METHOD);
         this.addClass("Unpickler", this.liburl(), unpickler);
      }
   }

   class Bz2Module extends NativeModule {
      public Bz2Module() {
         super("bz2");
      }

      public void initBindings() {
         NClassType bz2 = Builtins.this.newClass("BZ2File", this.table, Builtins.this.BaseFile);
         this.addClass("BZ2File", this.liburl(), bz2);
         NClassType bz2c = Builtins.this.newClass("BZ2Compressor", this.table, Builtins.this.Object);
         bz2c.getTable().update("compress", (NNode)Builtins.newLibUrl("bz2", "sequential-de-compression"), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         bz2c.getTable().update("flush", (NNode)Builtins.newLibUrl("bz2", "sequential-de-compression"), Builtins.this.newFunc(Builtins.this.None), NBinding.Kind.METHOD);
         this.addClass("BZ2Compressor", Builtins.newLibUrl("bz2", "sequential-de-compression"), bz2c);
         NClassType bz2d = Builtins.this.newClass("BZ2Decompressor", this.table, Builtins.this.Object);
         bz2d.getTable().update("decompress", (NNode)Builtins.newLibUrl("bz2", "sequential-de-compression"), Builtins.this.newFunc(Builtins.this.BaseStr), NBinding.Kind.METHOD);
         this.addClass("BZ2Decompressor", Builtins.newLibUrl("bz2", "sequential-de-compression"), bz2d);
         this.addFunction("compress", Builtins.newLibUrl("bz2", "one-shot-de-compression"), Builtins.this.BaseStr);
         this.addFunction("decompress", Builtins.newLibUrl("bz2", "one-shot-de-compression"), Builtins.this.BaseStr);
      }
   }

   class BinasciiModule extends NativeModule {
      public BinasciiModule() {
         super("binascii");
      }

      public void initBindings() {
         this.addStrFuncs(new String[]{"a2b_uu", "b2a_uu", "a2b_base64", "b2a_base64", "a2b_qp", "b2a_qp", "a2b_hqx", "rledecode_hqx", "rlecode_hqx", "b2a_hqx", "b2a_hex", "hexlify", "a2b_hex", "unhexlify"});
         this.addNumFuncs(new String[]{"crc_hqx", "crc32"});
         this.addClass("Error", this.liburl(), Builtins.this.newException("Error", this.table));
         this.addClass("Incomplete", this.liburl(), Builtins.this.newException("Incomplete", this.table));
      }
   }

   class AudioopModule extends NativeModule {
      public AudioopModule() {
         super("audioop");
      }

      public void initBindings() {
         this.addClass("error", this.liburl(), Builtins.this.newException("error", this.table));
         this.addStrFuncs(new String[]{"add", "adpcm2lin", "alaw2lin", "bias", "lin2alaw", "lin2lin", "lin2ulaw", "mul", "reverse", "tomono", "ulaw2lin"});
         this.addNumFuncs(new String[]{"avg", "avgpp", "cross", "findfactor", "findmax", "getsample", "max", "maxpp", "rms"});
         String[] var1 = Builtins.this.list("adpcm2lin", "findfit", "lin2adpcm", "minmax", "ratecv");
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            String s = var1[var3];
            this.addFunction(s, this.liburl(), Builtins.this.newTuple());
         }

      }
   }

   class ArrayModule extends NativeModule {
      public ArrayModule() {
         super("array");
      }

      public void initBindings() {
         this.addClass("array", Builtins.newLibUrl("array", "array"), Builtins.this.BaseArray);
         this.addClass("ArrayType", Builtins.newLibUrl("array", "ArrayType"), Builtins.this.BaseArray);
      }
   }

   class BuiltinsModule extends NativeModule {
      public BuiltinsModule() {
         super("__builtin__");
         Builtins.this.Builtin = this.module = Builtins.this.newModule(this.name);
         this.table = this.module.getTable();
      }

      public void initBindings() {
         Builtins.this.moduleTable.put(this.name, this.liburl(), this.module, NBinding.Kind.MODULE);
         this.table.addSuper(Builtins.this.BaseModule.getTable());
         this.addClass("None", Builtins.newLibUrl("constants"), Builtins.this.None);
         this.addClass("bool", Builtins.newLibUrl("functions", "bool"), Builtins.this.BaseBool);
         this.addClass("complex", Builtins.newLibUrl("functions", "complex"), Builtins.this.BaseComplex);
         this.addClass("dict", Builtins.newLibUrl("stdtypes", "typesmapping"), Builtins.this.BaseDict);
         this.addClass("file", Builtins.newLibUrl("functions", "file"), Builtins.this.BaseFile);
         this.addClass("float", Builtins.newLibUrl("functions", "float"), Builtins.this.BaseNum);
         this.addClass("int", Builtins.newLibUrl("functions", "int"), Builtins.this.BaseNum);
         this.addClass("list", Builtins.newLibUrl("functions", "list"), Builtins.this.BaseList);
         this.addClass("long", Builtins.newLibUrl("functions", "long"), Builtins.this.BaseNum);
         this.addClass("object", Builtins.newLibUrl("functions", "object"), Builtins.this.Object);
         this.addClass("str", Builtins.newLibUrl("functions", "str"), Builtins.this.BaseStr);
         this.addClass("tuple", Builtins.newLibUrl("functions", "tuple"), Builtins.this.BaseTuple);
         this.addClass("type", Builtins.newLibUrl("functions", "type"), Builtins.this.Type);
         String[] builtin_func_unknown = new String[]{"apply", "basestring", "callable", "classmethod", "coerce", "compile", "copyright", "credits", "delattr", "enumerate", "eval", "execfile", "exit", "filter", "frozenset", "getattr", "help", "input", "int", "intern", "iter", "license", "long", "property", "quit", "raw_input", "reduce", "reload", "reversed", "set", "setattr", "slice", "sorted", "staticmethod", "super", "type", "unichr", "unicode"};
         String[] builtin_func_num = builtin_func_unknown;
         int var3 = builtin_func_unknown.length;

         int var4;
         for(var4 = 0; var4 < var3; ++var4) {
            String fx = builtin_func_num[var4];
            this.addFunction(fx, Builtins.newLibUrl("functions.html#" + fx), Builtins.this.unknown());
         }

         builtin_func_num = new String[]{"abs", "all", "any", "cmp", "coerce", "divmod", "hasattr", "hash", "id", "isinstance", "issubclass", "len", "max", "min", "ord", "pow", "round", "sum"};
         String[] var7 = builtin_func_num;
         var4 = builtin_func_num.length;

         String f;
         int var8;
         for(var8 = 0; var8 < var4; ++var8) {
            f = var7[var8];
            this.addFunction(f, Builtins.newLibUrl("functions.html#" + f), Builtins.this.BaseNum);
         }

         var7 = Builtins.this.list("hex", "oct", "repr", "chr");
         var4 = var7.length;

         for(var8 = 0; var8 < var4; ++var8) {
            f = var7[var8];
            this.addFunction(f, Builtins.newLibUrl("functions.html#" + f), Builtins.this.BaseStr);
         }

         this.addFunction("dir", Builtins.newLibUrl("functions", "dir"), Builtins.this.newList(Builtins.this.BaseStr));
         this.addFunction("map", Builtins.newLibUrl("functions", "map"), Builtins.this.newList(Builtins.this.unknown()));
         this.addFunction("range", Builtins.newLibUrl("functions", "range"), Builtins.this.newList(Builtins.this.BaseNum));
         this.addFunction("xrange", Builtins.newLibUrl("functions", "range"), Builtins.this.newList(Builtins.this.BaseNum));
         this.addFunction("buffer", Builtins.newLibUrl("functions", "buffer"), Builtins.this.newList(Builtins.this.unknown()));
         this.addFunction("zip", Builtins.newLibUrl("functions", "zip"), Builtins.this.newList(Builtins.this.newTuple(Builtins.this.unknown())));
         var7 = Builtins.this.list("globals", "vars", "locals");
         var4 = var7.length;

         for(var8 = 0; var8 < var4; ++var8) {
            f = var7[var8];
            this.addFunction(f, Builtins.newLibUrl("functions.html#" + f), Builtins.this.newDict(Builtins.this.BaseStr, Builtins.this.unknown()));
         }

         var7 = Builtins.this.builtin_exception_types;
         var4 = var7.length;

         for(var8 = 0; var8 < var4; ++var8) {
            f = var7[var8];
            this.addClass(f, Builtins.newDataModelUrl("types"), Builtins.this.newClass(f, Builtins.this.globaltable, Builtins.this.Object));
         }

         Builtins.this.BaseException = (NClassType)this.table.lookup("BaseException").getType();
         var7 = Builtins.this.list("True", "False", "None", "Ellipsis");
         var4 = var7.length;

         for(var8 = 0; var8 < var4; ++var8) {
            f = var7[var8];
            this.addAttr(f, Builtins.newDataModelUrl("types"), Builtins.this.unknown());
         }

         this.addFunction("open", Builtins.newTutUrl("inputoutput.html#reading-and-writing-files"), Builtins.this.BaseFile);
         this.addFunction("__import__", Builtins.newLibUrl("functions"), Builtins.this.newModule("<?>"));
         Builtins.this.globaltable.put("__builtins__", this.liburl(), this.module, NBinding.Kind.ATTRIBUTE);
         Builtins.this.globaltable.merge(this.table);
      }
   }

   private abstract class NativeModule {
      protected String name;
      protected NModuleType module;
      protected Scope table;

      NativeModule(String name) {
         this.name = name;
         Builtins.this.modules.put(name, this);
      }

      NModuleType getModule() {
         if (this.module == null) {
            this.createModuleType();
            this.initBindings();
         }

         return this.module;
      }

      protected abstract void initBindings();

      protected void createModuleType() {
         if (this.module == null) {
            this.module = Builtins.this.newModule(this.name);
            this.table = this.module.getTable();
            Builtins.this.moduleTable.put(this.name, this.liburl(), this.module, NBinding.Kind.MODULE);
         }

      }

      protected NBinding update(String name, NUrl url, NType type, NBinding.Kind kind) {
         return this.table.update(name, (NNode)url, type, kind);
      }

      protected NBinding addClass(String name, NUrl url, NType type) {
         return this.table.update(name, (NNode)url, type, NBinding.Kind.CLASS);
      }

      protected NBinding addMethod(String name, NUrl url, NType type) {
         return this.table.update(name, (NNode)url, type, NBinding.Kind.METHOD);
      }

      protected NBinding addFunction(String name, NUrl url, NType type) {
         return this.table.update(name, (NNode)url, Builtins.this.newFunc(type), NBinding.Kind.FUNCTION);
      }

      protected void addFunctions_beCareful(NType type, String... names) {
         String[] var3 = names;
         int var4 = names.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String name = var3[var5];
            this.addFunction(name, this.liburl(), type);
         }

      }

      protected void addNoneFuncs(String... names) {
         this.addFunctions_beCareful(Builtins.this.None, names);
      }

      protected void addNumFuncs(String... names) {
         this.addFunctions_beCareful(Builtins.this.BaseNum, names);
      }

      protected void addStrFuncs(String... names) {
         this.addFunctions_beCareful(Builtins.this.BaseStr, names);
      }

      protected void addUnknownFuncs(String... names) {
         String[] var2 = names;
         int var3 = names.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String name = var2[var4];
            this.addFunction(name, this.liburl(), Builtins.this.unknown());
         }

      }

      protected NBinding addAttr(String name, NUrl url, NType type) {
         return this.table.update(name, (NNode)url, type, NBinding.Kind.ATTRIBUTE);
      }

      protected void addAttributes_beCareful(NType type, String... names) {
         String[] var3 = names;
         int var4 = names.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String name = var3[var5];
            this.addAttr(name, this.liburl(), type);
         }

      }

      protected void addNumAttrs(String... names) {
         this.addAttributes_beCareful(Builtins.this.BaseNum, names);
      }

      protected void addStrAttrs(String... names) {
         this.addAttributes_beCareful(Builtins.this.BaseStr, names);
      }

      protected void addUnknownAttrs(String... names) {
         String[] var2 = names;
         int var3 = names.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String name = var2[var4];
            this.addAttr(name, this.liburl(), Builtins.this.unknown());
         }

      }

      protected NUrl liburl() {
         return Builtins.newLibUrl(this.name);
      }

      protected NUrl liburl(String anchor) {
         return Builtins.newLibUrl(this.name, anchor);
      }

      public String toString() {
         return this.module == null ? "<Non-loaded builtin module '" + this.name + "'>" : "<NativeModule:" + this.module + ">";
      }
   }
}
