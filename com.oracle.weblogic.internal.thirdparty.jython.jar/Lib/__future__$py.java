import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("__future__.py")
public class __future__$py extends PyFunctionTable implements PyRunnable {
   static __future__$py self;
   static final PyCode f$0;
   static final PyCode _Feature$1;
   static final PyCode __init__$2;
   static final PyCode getOptionalRelease$3;
   static final PyCode getMandatoryRelease$4;
   static final PyCode __repr__$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Record of phased-in incompatible language changes.\n\nEach line is of the form:\n\n    FeatureName = \"_Feature(\" OptionalRelease \",\" MandatoryRelease \",\"\n                              CompilerFlag \")\"\n\nwhere, normally, OptionalRelease < MandatoryRelease, and both are 5-tuples\nof the same form as sys.version_info:\n\n    (PY_MAJOR_VERSION, # the 2 in 2.1.0a3; an int\n     PY_MINOR_VERSION, # the 1; an int\n     PY_MICRO_VERSION, # the 0; an int\n     PY_RELEASE_LEVEL, # \"alpha\", \"beta\", \"candidate\" or \"final\"; string\n     PY_RELEASE_SERIAL # the 3; an int\n    )\n\nOptionalRelease records the first release in which\n\n    from __future__ import FeatureName\n\nwas accepted.\n\nIn the case of MandatoryReleases that have not yet occurred,\nMandatoryRelease predicts the release in which the feature will become part\nof the language.\n\nElse MandatoryRelease records when the feature became part of the language;\nin releases at or after that, modules no longer need\n\n    from __future__ import FeatureName\n\nto use the feature in question, but may continue to use such imports.\n\nMandatoryRelease may also be None, meaning that a planned feature got\ndropped.\n\nInstances of class _Feature have two corresponding methods,\n.getOptionalRelease() and .getMandatoryRelease().\n\nCompilerFlag is the (bitfield) flag that should be passed in the fourth\nargument to the builtin function compile() to enable the feature in\ndynamically compiled code.  This flag is stored in the .compiler_flag\nattribute on _Future instances.  These values must match the appropriate\n#defines of CO_xxx flags in Include/compile.h.\n\nNo feature line is ever to be deleted from this file.\n"));
      var1.setline(48);
      PyString.fromInterned("Record of phased-in incompatible language changes.\n\nEach line is of the form:\n\n    FeatureName = \"_Feature(\" OptionalRelease \",\" MandatoryRelease \",\"\n                              CompilerFlag \")\"\n\nwhere, normally, OptionalRelease < MandatoryRelease, and both are 5-tuples\nof the same form as sys.version_info:\n\n    (PY_MAJOR_VERSION, # the 2 in 2.1.0a3; an int\n     PY_MINOR_VERSION, # the 1; an int\n     PY_MICRO_VERSION, # the 0; an int\n     PY_RELEASE_LEVEL, # \"alpha\", \"beta\", \"candidate\" or \"final\"; string\n     PY_RELEASE_SERIAL # the 3; an int\n    )\n\nOptionalRelease records the first release in which\n\n    from __future__ import FeatureName\n\nwas accepted.\n\nIn the case of MandatoryReleases that have not yet occurred,\nMandatoryRelease predicts the release in which the feature will become part\nof the language.\n\nElse MandatoryRelease records when the feature became part of the language;\nin releases at or after that, modules no longer need\n\n    from __future__ import FeatureName\n\nto use the feature in question, but may continue to use such imports.\n\nMandatoryRelease may also be None, meaning that a planned feature got\ndropped.\n\nInstances of class _Feature have two corresponding methods,\n.getOptionalRelease() and .getMandatoryRelease().\n\nCompilerFlag is the (bitfield) flag that should be passed in the fourth\nargument to the builtin function compile() to enable the feature in\ndynamically compiled code.  This flag is stored in the .compiler_flag\nattribute on _Future instances.  These values must match the appropriate\n#defines of CO_xxx flags in Include/compile.h.\n\nNo feature line is ever to be deleted from this file.\n");
      var1.setline(50);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("nested_scopes"), PyString.fromInterned("generators"), PyString.fromInterned("division"), PyString.fromInterned("absolute_import"), PyString.fromInterned("with_statement"), PyString.fromInterned("print_function"), PyString.fromInterned("unicode_literals")});
      var1.setlocal("all_feature_names", var3);
      var3 = null;
      var1.setline(60);
      PyObject var5 = (new PyList(new PyObject[]{PyString.fromInterned("all_feature_names")}))._add(var1.getname("all_feature_names"));
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(66);
      PyInteger var6 = Py.newInteger(16);
      var1.setlocal("CO_NESTED", var6);
      var3 = null;
      var1.setline(67);
      var6 = Py.newInteger(0);
      var1.setlocal("CO_GENERATOR_ALLOWED", var6);
      var3 = null;
      var1.setline(68);
      var6 = Py.newInteger(8192);
      var1.setlocal("CO_FUTURE_DIVISION", var6);
      var3 = null;
      var1.setline(69);
      var6 = Py.newInteger(16384);
      var1.setlocal("CO_FUTURE_ABSOLUTE_IMPORT", var6);
      var3 = null;
      var1.setline(70);
      var6 = Py.newInteger(32768);
      var1.setlocal("CO_FUTURE_WITH_STATEMENT", var6);
      var3 = null;
      var1.setline(71);
      var6 = Py.newInteger(65536);
      var1.setlocal("CO_FUTURE_PRINT_FUNCTION", var6);
      var3 = null;
      var1.setline(72);
      var6 = Py.newInteger(131072);
      var1.setlocal("CO_FUTURE_UNICODE_LITERALS", var6);
      var3 = null;
      var1.setline(74);
      PyObject[] var7 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("_Feature", var7, _Feature$1);
      var1.setlocal("_Feature", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(102);
      var5 = var1.getname("_Feature").__call__((ThreadState)var2, new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(1), Py.newInteger(0), PyString.fromInterned("beta"), Py.newInteger(1)}), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(2), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(0)})), (PyObject)var1.getname("CO_NESTED"));
      var1.setlocal("nested_scopes", var5);
      var3 = null;
      var1.setline(106);
      var5 = var1.getname("_Feature").__call__((ThreadState)var2, new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(2), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(1)}), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(3), Py.newInteger(0), PyString.fromInterned("final"), Py.newInteger(0)})), (PyObject)var1.getname("CO_GENERATOR_ALLOWED"));
      var1.setlocal("generators", var5);
      var3 = null;
      var1.setline(110);
      var5 = var1.getname("_Feature").__call__((ThreadState)var2, new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(2), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(2)}), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(0), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(0)})), (PyObject)var1.getname("CO_FUTURE_DIVISION"));
      var1.setlocal("division", var5);
      var3 = null;
      var1.setline(114);
      var5 = var1.getname("_Feature").__call__((ThreadState)var2, new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(5), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(1)}), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(0), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(0)})), (PyObject)var1.getname("CO_FUTURE_ABSOLUTE_IMPORT"));
      var1.setlocal("absolute_import", var5);
      var3 = null;
      var1.setline(118);
      var5 = var1.getname("_Feature").__call__((ThreadState)var2, new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(5), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(1)}), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(6), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(0)})), (PyObject)var1.getname("CO_FUTURE_WITH_STATEMENT"));
      var1.setlocal("with_statement", var5);
      var3 = null;
      var1.setline(122);
      var5 = var1.getname("_Feature").__call__((ThreadState)var2, new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(6), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(2)}), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(0), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(0)})), (PyObject)var1.getname("CO_FUTURE_PRINT_FUNCTION"));
      var1.setlocal("print_function", var5);
      var3 = null;
      var1.setline(126);
      var5 = var1.getname("_Feature").__call__((ThreadState)var2, new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(6), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(2)}), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(0), Py.newInteger(0), PyString.fromInterned("alpha"), Py.newInteger(0)})), (PyObject)var1.getname("CO_FUTURE_UNICODE_LITERALS"));
      var1.setlocal("unicode_literals", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Feature$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(75);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getOptionalRelease$3, PyString.fromInterned("Return first release in which this feature was recognized.\n\n        This is a 5-tuple, of the same form as sys.version_info.\n        "));
      var1.setlocal("getOptionalRelease", var4);
      var3 = null;
      var1.setline(88);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getMandatoryRelease$4, PyString.fromInterned("Return release in which this feature will become mandatory.\n\n        This is a 5-tuple, of the same form as sys.version_info, or, if\n        the feature was dropped, is None.\n        "));
      var1.setlocal("getMandatoryRelease", var4);
      var3 = null;
      var1.setline(97);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$5, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("optional", var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("mandatory", var3);
      var3 = null;
      var1.setline(78);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("compiler_flag", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getOptionalRelease$3(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyString.fromInterned("Return first release in which this feature was recognized.\n\n        This is a 5-tuple, of the same form as sys.version_info.\n        ");
      var1.setline(86);
      PyObject var3 = var1.getlocal(0).__getattr__("optional");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getMandatoryRelease$4(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyString.fromInterned("Return release in which this feature will become mandatory.\n\n        This is a 5-tuple, of the same form as sys.version_info, or, if\n        the feature was dropped, is None.\n        ");
      var1.setline(95);
      PyObject var3 = var1.getlocal(0).__getattr__("mandatory");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$5(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var3 = PyString.fromInterned("_Feature")._add(var1.getglobal("repr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("optional"), var1.getlocal(0).__getattr__("mandatory"), var1.getlocal(0).__getattr__("compiler_flag")}))));
      var1.f_lasti = -1;
      return var3;
   }

   public __future__$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _Feature$1 = Py.newCode(0, var2, var1, "_Feature", 74, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "optionalRelease", "mandatoryRelease", "compiler_flag"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 75, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getOptionalRelease$3 = Py.newCode(1, var2, var1, "getOptionalRelease", 80, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getMandatoryRelease$4 = Py.newCode(1, var2, var1, "getMandatoryRelease", 88, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$5 = Py.newCode(1, var2, var1, "__repr__", 97, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new __future__$py("__future__$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(__future__$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._Feature$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.getOptionalRelease$3(var2, var3);
         case 4:
            return this.getMandatoryRelease$4(var2, var3);
         case 5:
            return this.__repr__$5(var2, var3);
         default:
            return null;
      }
   }
}
