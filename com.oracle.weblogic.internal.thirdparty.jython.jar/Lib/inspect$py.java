import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
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
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("inspect.py")
public class inspect$py extends PyFunctionTable implements PyRunnable {
   static inspect$py self;
   static final PyCode f$0;
   static final PyCode ismodule$1;
   static final PyCode isclass$2;
   static final PyCode ismethod$3;
   static final PyCode ismethoddescriptor$4;
   static final PyCode isdatadescriptor$5;
   static final PyCode ismemberdescriptor$6;
   static final PyCode ismemberdescriptor$7;
   static final PyCode isgetsetdescriptor$8;
   static final PyCode isgetsetdescriptor$9;
   static final PyCode isfunction$10;
   static final PyCode isgeneratorfunction$11;
   static final PyCode isgenerator$12;
   static final PyCode istraceback$13;
   static final PyCode isframe$14;
   static final PyCode iscode$15;
   static final PyCode isbuiltin$16;
   static final PyCode isroutine$17;
   static final PyCode isabstract$18;
   static final PyCode getmembers$19;
   static final PyCode classify_class_attrs$20;
   static final PyCode _searchbases$21;
   static final PyCode getmro$22;
   static final PyCode indentsize$23;
   static final PyCode getdoc$24;
   static final PyCode cleandoc$25;
   static final PyCode getfile$26;
   static final PyCode getmoduleinfo$27;
   static final PyCode f$28;
   static final PyCode getmodulename$29;
   static final PyCode getsourcefile$30;
   static final PyCode getabsfile$31;
   static final PyCode getmodule$32;
   static final PyCode findsource$33;
   static final PyCode getcomments$34;
   static final PyCode EndOfBlock$35;
   static final PyCode BlockFinder$36;
   static final PyCode __init__$37;
   static final PyCode tokeneater$38;
   static final PyCode getblock$39;
   static final PyCode getsourcelines$40;
   static final PyCode getsource$41;
   static final PyCode walktree$42;
   static final PyCode getclasstree$43;
   static final PyCode getargs$44;
   static final PyCode _parse_anonymous_tuple_arg$45;
   static final PyCode getargspec$46;
   static final PyCode getargvalues$47;
   static final PyCode joinseq$48;
   static final PyCode strseq$49;
   static final PyCode f$50;
   static final PyCode f$51;
   static final PyCode f$52;
   static final PyCode f$53;
   static final PyCode formatargspec$54;
   static final PyCode f$55;
   static final PyCode f$56;
   static final PyCode f$57;
   static final PyCode formatargvalues$58;
   static final PyCode convert$59;
   static final PyCode getcallargs$60;
   static final PyCode assign$61;
   static final PyCode is_assigned$62;
   static final PyCode getframeinfo$63;
   static final PyCode getlineno$64;
   static final PyCode getouterframes$65;
   static final PyCode getinnerframes$66;
   static final PyCode f$67;
   static final PyCode stack$68;
   static final PyCode trace$69;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Get useful information from live Python objects.\n\nThis module encapsulates the interface provided by the internal special\nattributes (func_*, co_*, im_*, tb_*, etc.) in a friendlier fashion.\nIt also provides some help for examining source code and class layout.\n\nHere are some of the useful functions provided by this module:\n\n    ismodule(), isclass(), ismethod(), isfunction(), isgeneratorfunction(),\n        isgenerator(), istraceback(), isframe(), iscode(), isbuiltin(),\n        isroutine() - check object types\n    getmembers() - get members of an object that satisfy a given condition\n\n    getfile(), getsourcefile(), getsource() - find an object's source code\n    getdoc(), getcomments() - get documentation on an object\n    getmodule() - determine the module that an object came from\n    getclasstree() - arrange classes so as to represent their hierarchy\n\n    getargspec(), getargvalues(), getcallargs() - get info about function arguments\n    formatargspec(), formatargvalues() - format an argument spec\n    getouterframes(), getinnerframes() - get info about frames\n    currentframe() - get the current stack frame\n    stack(), trace() - get info about frames on the stack or in a traceback\n"));
      var1.setline(25);
      PyString.fromInterned("Get useful information from live Python objects.\n\nThis module encapsulates the interface provided by the internal special\nattributes (func_*, co_*, im_*, tb_*, etc.) in a friendlier fashion.\nIt also provides some help for examining source code and class layout.\n\nHere are some of the useful functions provided by this module:\n\n    ismodule(), isclass(), ismethod(), isfunction(), isgeneratorfunction(),\n        isgenerator(), istraceback(), isframe(), iscode(), isbuiltin(),\n        isroutine() - check object types\n    getmembers() - get members of an object that satisfy a given condition\n\n    getfile(), getsourcefile(), getsource() - find an object's source code\n    getdoc(), getcomments() - get documentation on an object\n    getmodule() - determine the module that an object came from\n    getclasstree() - arrange classes so as to represent their hierarchy\n\n    getargspec(), getargvalues(), getcallargs() - get info about function arguments\n    formatargspec(), formatargvalues() - format an argument spec\n    getouterframes(), getinnerframes() - get info about frames\n    currentframe() - get the current stack frame\n    stack(), trace() - get info about frames on the stack or in a traceback\n");
      var1.setline(29);
      PyString var3 = PyString.fromInterned("Ka-Ping Yee <ping@lfw.org>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(30);
      var3 = PyString.fromInterned("1 Jan 2001");
      var1.setlocal("__date__", var3);
      var3 = null;
      var1.setline(32);
      PyObject var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(33);
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(34);
      var6 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var6);
      var3 = null;
      var1.setline(35);
      var6 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var6);
      var3 = null;
      var1.setline(36);
      var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(37);
      var6 = imp.importOne("dis", var1, -1);
      var1.setlocal("dis", var6);
      var3 = null;
      var1.setline(38);
      var6 = imp.importOne("imp", var1, -1);
      var1.setlocal("imp", var6);
      var3 = null;
      var1.setline(39);
      var6 = imp.importOne("tokenize", var1, -1);
      var1.setlocal("tokenize", var6);
      var3 = null;
      var1.setline(40);
      var6 = imp.importOne("linecache", var1, -1);
      var1.setlocal("linecache", var6);
      var3 = null;
      var1.setline(41);
      String[] var8 = new String[]{"attrgetter"};
      PyObject[] var9 = imp.importFrom("operator", var8, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal("attrgetter", var4);
      var4 = null;
      var1.setline(42);
      var8 = new String[]{"namedtuple"};
      var9 = imp.importFrom("collections", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("namedtuple", var4);
      var4 = null;
      var1.setline(43);
      var6 = var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"));
      var1.setlocal("_jython", var6);
      var3 = null;
      var1.setline(44);
      if (var1.getname("_jython").__nonzero__()) {
         var1.setline(45);
         var6 = var1.getname("type").__call__(var2, var1.getname("os").__getattr__("listdir"));
         var1.setlocal("_ReflectedFunctionType", var6);
         var3 = null;
      }

      var1.setline(48);
      PyTuple var10 = new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(4), Py.newInteger(8)});
      PyObject[] var7 = Py.unpackSequence(var10, 4);
      PyObject var5 = var7[0];
      var1.setlocal("CO_OPTIMIZED", var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal("CO_NEWLOCALS", var5);
      var5 = null;
      var5 = var7[2];
      var1.setlocal("CO_VARARGS", var5);
      var5 = null;
      var5 = var7[3];
      var1.setlocal("CO_VARKEYWORDS", var5);
      var5 = null;
      var3 = null;
      var1.setline(49);
      var10 = new PyTuple(new PyObject[]{Py.newInteger(16), Py.newInteger(32), Py.newInteger(64)});
      var7 = Py.unpackSequence(var10, 3);
      var5 = var7[0];
      var1.setlocal("CO_NESTED", var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal("CO_GENERATOR", var5);
      var5 = null;
      var5 = var7[2];
      var1.setlocal("CO_NOFREE", var5);
      var5 = null;
      var3 = null;
      var1.setline(51);
      var6 = Py.newInteger(1)._lshift(Py.newInteger(20));
      var1.setlocal("TPFLAGS_IS_ABSTRACT", var6);
      var3 = null;
      var1.setline(54);
      var9 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var9, ismodule$1, PyString.fromInterned("Return true if the object is a module.\n\n    Module objects provide these attributes:\n        __doc__         documentation string\n        __file__        filename (missing for built-in modules)"));
      var1.setlocal("ismodule", var11);
      var3 = null;
      var1.setline(62);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, isclass$2, PyString.fromInterned("Return true if the object is a class.\n\n    Class objects provide these attributes:\n        __doc__         documentation string\n        __module__      name of module in which this class was defined"));
      var1.setlocal("isclass", var11);
      var3 = null;
      var1.setline(70);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, ismethod$3, PyString.fromInterned("Return true if the object is an instance method.\n\n    Instance method objects provide these attributes:\n        __doc__         documentation string\n        __name__        name with which this method was defined\n        im_class        class object in which this method belongs\n        im_func         function object containing implementation of method\n        im_self         instance to which this method is bound, or None"));
      var1.setlocal("ismethod", var11);
      var3 = null;
      var1.setline(81);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, ismethoddescriptor$4, PyString.fromInterned("Return true if the object is a method descriptor.\n\n    But not if ismethod() or isclass() or isfunction() are true.\n\n    This is new in Python 2.2, and, for example, is true of int.__add__.\n    An object passing this test has a __get__ attribute but not a __set__\n    attribute, but beyond that the set of attributes varies.  __name__ is\n    usually sensible, and __doc__ often is.\n\n    Methods implemented via descriptors that also pass one of the other\n    tests return false from the ismethoddescriptor() test, simply because\n    the other tests promise more -- you can, e.g., count on having the\n    im_func attribute (etc) when an object passes ismethod()."));
      var1.setlocal("ismethoddescriptor", var11);
      var3 = null;
      var1.setline(101);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, isdatadescriptor$5, PyString.fromInterned("Return true if the object is a data descriptor.\n\n    Data descriptors have both a __get__ and a __set__ attribute.  Examples are\n    properties (defined in Python) and getsets and members (defined in C).\n    Typically, data descriptors will also have __name__ and __doc__ attributes\n    (properties, getsets, and members have both of these attributes), but this\n    is not guaranteed."));
      var1.setlocal("isdatadescriptor", var11);
      var3 = null;
      var1.setline(111);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("types"), (PyObject)PyString.fromInterned("MemberDescriptorType")).__nonzero__()) {
         var1.setline(113);
         var9 = Py.EmptyObjects;
         var11 = new PyFunction(var1.f_globals, var9, ismemberdescriptor$6, PyString.fromInterned("Return true if the object is a member descriptor.\n\n        Member descriptors are specialized descriptors defined in extension\n        modules."));
         var1.setlocal("ismemberdescriptor", var11);
         var3 = null;
      } else {
         var1.setline(121);
         var9 = Py.EmptyObjects;
         var11 = new PyFunction(var1.f_globals, var9, ismemberdescriptor$7, PyString.fromInterned("Return true if the object is a member descriptor.\n\n        Member descriptors are specialized descriptors defined in extension\n        modules."));
         var1.setlocal("ismemberdescriptor", var11);
         var3 = null;
      }

      var1.setline(128);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("types"), (PyObject)PyString.fromInterned("GetSetDescriptorType")).__nonzero__()) {
         var1.setline(130);
         var9 = Py.EmptyObjects;
         var11 = new PyFunction(var1.f_globals, var9, isgetsetdescriptor$8, PyString.fromInterned("Return true if the object is a getset descriptor.\n\n        getset descriptors are specialized descriptors defined in extension\n        modules."));
         var1.setlocal("isgetsetdescriptor", var11);
         var3 = null;
      } else {
         var1.setline(138);
         var9 = Py.EmptyObjects;
         var11 = new PyFunction(var1.f_globals, var9, isgetsetdescriptor$9, PyString.fromInterned("Return true if the object is a getset descriptor.\n\n        getset descriptors are specialized descriptors defined in extension\n        modules."));
         var1.setlocal("isgetsetdescriptor", var11);
         var3 = null;
      }

      var1.setline(145);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, isfunction$10, PyString.fromInterned("Return true if the object is a user-defined function.\n\n    Function objects provide these attributes:\n        __doc__         documentation string\n        __name__        name with which this function was defined\n        func_code       code object containing compiled function bytecode\n        func_defaults   tuple of any default values for arguments\n        func_doc        (same as __doc__)\n        func_globals    global namespace in which this function was defined\n        func_name       (same as __name__)"));
      var1.setlocal("isfunction", var11);
      var3 = null;
      var1.setline(158);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, isgeneratorfunction$11, PyString.fromInterned("Return true if the object is a user-defined generator function.\n\n    Generator function objects provides same attributes as functions.\n\n    See help(isfunction) for attributes listing."));
      var1.setlocal("isgeneratorfunction", var11);
      var3 = null;
      var1.setline(167);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, isgenerator$12, PyString.fromInterned("Return true if the object is a generator.\n\n    Generator objects provide these attributes:\n        __iter__        defined to support interation over container\n        close           raises a new GeneratorExit exception inside the\n                        generator to terminate the iteration\n        gi_code         code object\n        gi_frame        frame object or possibly None once the generator has\n                        been exhausted\n        gi_running      set to 1 when generator is executing, 0 otherwise\n        next            return the next item from the container\n        send            resumes the generator and \"sends\" a value that becomes\n                        the result of the current yield-expression\n        throw           used to raise an exception inside the generator"));
      var1.setlocal("isgenerator", var11);
      var3 = null;
      var1.setline(184);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, istraceback$13, PyString.fromInterned("Return true if the object is a traceback.\n\n    Traceback objects provide these attributes:\n        tb_frame        frame object at this level\n        tb_lasti        index of last attempted instruction in bytecode\n        tb_lineno       current line number in Python source code\n        tb_next         next inner traceback object (called by this level)"));
      var1.setlocal("istraceback", var11);
      var3 = null;
      var1.setline(194);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, isframe$14, PyString.fromInterned("Return true if the object is a frame object.\n\n    Frame objects provide these attributes:\n        f_back          next outer frame object (this frame's caller)\n        f_builtins      built-in namespace seen by this frame\n        f_code          code object being executed in this frame\n        f_exc_traceback traceback if raised in this frame, or None\n        f_exc_type      exception type if raised in this frame, or None\n        f_exc_value     exception value if raised in this frame, or None\n        f_globals       global namespace seen by this frame\n        f_lasti         index of last attempted instruction in bytecode\n        f_lineno        current line number in Python source code\n        f_locals        local namespace seen by this frame\n        f_restricted    0 or 1 if frame is in restricted execution mode\n        f_trace         tracing function for this frame, or None"));
      var1.setlocal("isframe", var11);
      var3 = null;
      var1.setline(212);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, iscode$15, PyString.fromInterned("Return true if the object is a code object.\n\n    Code objects provide these attributes:\n        co_argcount     number of arguments (not including * or ** args)\n        co_code         string of raw compiled bytecode\n        co_consts       tuple of constants used in the bytecode\n        co_filename     name of file in which this code object was created\n        co_firstlineno  number of first line in Python source code\n        co_flags        bitmap: 1=optimized | 2=newlocals | 4=*arg | 8=**arg\n        co_lnotab       encoded mapping of line numbers to bytecode indices\n        co_name         name with which this code object was defined\n        co_names        tuple of names of local variables\n        co_nlocals      number of local variables\n        co_stacksize    virtual machine stack space required\n        co_varnames     tuple of names of arguments and local variables"));
      var1.setlocal("iscode", var11);
      var3 = null;
      var1.setline(230);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, isbuiltin$16, PyString.fromInterned("Return true if the object is a built-in function or method.\n\n    Built-in functions and methods provide these attributes:\n        __doc__         documentation string\n        __name__        original name of this function or method\n        __self__        instance to which a method is bound, or None"));
      var1.setlocal("isbuiltin", var11);
      var3 = null;
      var1.setline(239);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, isroutine$17, PyString.fromInterned("Return true if the object is any kind of function or method."));
      var1.setlocal("isroutine", var11);
      var3 = null;
      var1.setline(247);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, isabstract$18, PyString.fromInterned("Return true if the object is an abstract base class (ABC)."));
      var1.setlocal("isabstract", var11);
      var3 = null;
      var1.setline(251);
      var9 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var9, getmembers$19, PyString.fromInterned("Return all members of an object as (name, value) pairs sorted by name.\n    Optionally, only return members that satisfy a given predicate."));
      var1.setlocal("getmembers", var11);
      var3 = null;
      var1.setline(265);
      var6 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Attribute"), (PyObject)PyString.fromInterned("name kind defining_class object"));
      var1.setlocal("Attribute", var6);
      var3 = null;
      var1.setline(267);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, classify_class_attrs$20, PyString.fromInterned("Return list of attribute-descriptor tuples.\n\n    For each name in dir(cls), the return list contains a 4-tuple\n    with these elements:\n\n        0. The name (a string).\n\n        1. The kind of attribute this is, one of these strings:\n               'class method'    created via classmethod()\n               'static method'   created via staticmethod()\n               'property'        created via property()\n               'method'          any other flavor of method\n               'data'            not a method\n\n        2. The class which defined this attribute (a class).\n\n        3. The object as obtained directly from the defining class's\n           __dict__, not via getattr.  This is especially important for\n           data attributes:  C.data is just a data object, but\n           C.__dict__['data'] may be a data descriptor with additional\n           info, like a __doc__ string.\n    "));
      var1.setlocal("classify_class_attrs", var11);
      var3 = null;
      var1.setline(336);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _searchbases$21, (PyObject)null);
      var1.setlocal("_searchbases", var11);
      var3 = null;
      var1.setline(344);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getmro$22, PyString.fromInterned("Return tuple of base classes (including cls) in method resolution order."));
      var1.setlocal("getmro", var11);
      var3 = null;
      var1.setline(354);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, indentsize$23, PyString.fromInterned("Return the indent size, in spaces, at the start of a line of text."));
      var1.setlocal("indentsize", var11);
      var3 = null;
      var1.setline(359);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getdoc$24, PyString.fromInterned("Get the documentation string for an object.\n\n    All tabs are expanded to spaces.  To clean up docstrings that are\n    indented to line up with blocks of code, any whitespace than can be\n    uniformly removed from the second line onwards is removed."));
      var1.setlocal("getdoc", var11);
      var3 = null;
      var1.setline(373);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, cleandoc$25, PyString.fromInterned("Clean up indentation from docstrings.\n\n    Any whitespace that can be uniformly removed from the second line\n    onwards is removed."));
      var1.setlocal("cleandoc", var11);
      var3 = null;
      var1.setline(402);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getfile$26, PyString.fromInterned("Work out which source or compiled file an object was defined in."));
      var1.setlocal("getfile", var11);
      var3 = null;
      var1.setline(426);
      var6 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ModuleInfo"), (PyObject)PyString.fromInterned("name suffix mode module_type"));
      var1.setlocal("ModuleInfo", var6);
      var3 = null;
      var1.setline(428);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getmoduleinfo$27, PyString.fromInterned("Get the module name, suffix, mode, and module type for a given file."));
      var1.setlocal("getmoduleinfo", var11);
      var3 = null;
      var1.setline(439);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getmodulename$29, PyString.fromInterned("Return the module name for a given file, or None."));
      var1.setlocal("getmodulename", var11);
      var3 = null;
      var1.setline(444);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getsourcefile$30, PyString.fromInterned("Return the filename that can be used to locate an object's source.\n    Return None if no way can be identified to get the source.\n    "));
      var1.setlocal("getsourcefile", var11);
      var3 = null;
      var1.setline(466);
      var9 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var9, getabsfile$31, PyString.fromInterned("Return an absolute path to the source or compiled file for an object.\n\n    The idea is for each object to have a unique origin, so this routine\n    normalizes the result as much as possible."));
      var1.setlocal("getabsfile", var11);
      var3 = null;
      var1.setline(475);
      PyDictionary var12 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("modulesbyfile", var12);
      var3 = null;
      var1.setline(476);
      var12 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_filesbymodname", var12);
      var3 = null;
      var1.setline(478);
      var9 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var9, getmodule$32, PyString.fromInterned("Return the module an object was defined in, or None if not found."));
      var1.setlocal("getmodule", var11);
      var3 = null;
      var1.setline(524);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, findsource$33, PyString.fromInterned("Return the entire source file and starting line number for an object.\n\n    The argument may be a module, class, method, function, traceback, frame,\n    or code object.  The source code is returned as a list of all the lines\n    in the file and the line number indexes a line in that list.  An IOError\n    is raised if the source code cannot be retrieved."));
      var1.setlocal("findsource", var11);
      var3 = null;
      var1.setline(591);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getcomments$34, PyString.fromInterned("Get lines of comments immediately preceding an object's source code.\n\n    Returns None when source can't be found.\n    "));
      var1.setlocal("getcomments", var11);
      var3 = null;
      var1.setline(636);
      var9 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("EndOfBlock", var9, EndOfBlock$35);
      var1.setlocal("EndOfBlock", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(638);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("BlockFinder", var9, BlockFinder$36);
      var1.setlocal("BlockFinder", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(679);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getblock$39, PyString.fromInterned("Extract the block of code at the top of the given list of lines."));
      var1.setlocal("getblock", var11);
      var3 = null;
      var1.setline(688);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getsourcelines$40, PyString.fromInterned("Return a list of source lines and starting line number for an object.\n\n    The argument may be a module, class, method, function, traceback, frame,\n    or code object.  The source code is returned as a list of the lines\n    corresponding to the object and the line number indicates where in the\n    original source file the first line of code was found.  An IOError is\n    raised if the source code cannot be retrieved."));
      var1.setlocal("getsourcelines", var11);
      var3 = null;
      var1.setline(701);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getsource$41, PyString.fromInterned("Return the text of the source code for an object.\n\n    The argument may be a module, class, method, function, traceback, frame,\n    or code object.  The source code is returned as a single string.  An\n    IOError is raised if the source code cannot be retrieved."));
      var1.setlocal("getsource", var11);
      var3 = null;
      var1.setline(711);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, walktree$42, PyString.fromInterned("Recursive helper function for getclasstree()."));
      var1.setlocal("walktree", var11);
      var3 = null;
      var1.setline(721);
      var9 = new PyObject[]{Py.newInteger(0)};
      var11 = new PyFunction(var1.f_globals, var9, getclasstree$43, PyString.fromInterned("Arrange the given list of classes into a hierarchy of nested lists.\n\n    Where a nested list appears, it contains classes derived from the class\n    whose entry immediately precedes the list.  Each entry is a 2-tuple\n    containing a class and a tuple of its base classes.  If the 'unique'\n    argument is true, exactly one entry appears in the returned structure\n    for each class in the given list.  Otherwise, classes using multiple\n    inheritance and their descendants will appear multiple times."));
      var1.setlocal("getclasstree", var11);
      var3 = null;
      var1.setline(747);
      var6 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Arguments"), (PyObject)PyString.fromInterned("args varargs keywords"));
      var1.setlocal("Arguments", var6);
      var3 = null;
      var1.setline(749);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getargs$44, PyString.fromInterned("Get information about the arguments accepted by a code object.\n\n    Three things are returned: (args, varargs, varkw), where 'args' is\n    a list of argument names (possibly containing nested lists), and\n    'varargs' and 'varkw' are the names of the * and ** arguments or None."));
      var1.setlocal("getargs", var11);
      var3 = null;
      var1.setline(789);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _parse_anonymous_tuple_arg$45, (PyObject)null);
      var1.setlocal("_parse_anonymous_tuple_arg", var11);
      var3 = null;
      var1.setline(813);
      var6 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ArgSpec"), (PyObject)PyString.fromInterned("args varargs keywords defaults"));
      var1.setlocal("ArgSpec", var6);
      var3 = null;
      var1.setline(815);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getargspec$46, PyString.fromInterned("Get the names and default values of a function's arguments.\n\n    A tuple of four things is returned: (args, varargs, varkw, defaults).\n    'args' is a list of the argument names (it may contain nested lists).\n    'varargs' and 'varkw' are the names of the * and ** arguments or None.\n    'defaults' is an n-tuple of the default values of the last n arguments.\n    "));
      var1.setlocal("getargspec", var11);
      var3 = null;
      var1.setline(831);
      var6 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ArgInfo"), (PyObject)PyString.fromInterned("args varargs keywords locals"));
      var1.setlocal("ArgInfo", var6);
      var3 = null;
      var1.setline(833);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getargvalues$47, PyString.fromInterned("Get information about arguments passed into a particular frame.\n\n    A tuple of four things is returned: (args, varargs, varkw, locals).\n    'args' is a list of the argument names (it may contain nested lists).\n    'varargs' and 'varkw' are the names of the * and ** arguments or None.\n    'locals' is the locals dictionary of the given frame."));
      var1.setlocal("getargvalues", var11);
      var3 = null;
      var1.setline(843);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, joinseq$48, (PyObject)null);
      var1.setlocal("joinseq", var11);
      var3 = null;
      var1.setline(849);
      var9 = new PyObject[]{var1.getname("joinseq")};
      var11 = new PyFunction(var1.f_globals, var9, strseq$49, PyString.fromInterned("Recursively walk a sequence, stringifying each element."));
      var1.setlocal("strseq", var11);
      var3 = null;
      var1.setline(856);
      var9 = new PyObject[8];
      var9[0] = var1.getname("None");
      var9[1] = var1.getname("None");
      var9[2] = var1.getname("None");
      var9[3] = var1.getname("str");
      var1.setline(858);
      var7 = Py.EmptyObjects;
      var9[4] = new PyFunction(var1.f_globals, var7, f$51);
      var1.setline(859);
      var7 = Py.EmptyObjects;
      var9[5] = new PyFunction(var1.f_globals, var7, f$52);
      var1.setline(860);
      var7 = Py.EmptyObjects;
      var9[6] = new PyFunction(var1.f_globals, var7, f$53);
      var9[7] = var1.getname("joinseq");
      var11 = new PyFunction(var1.f_globals, var9, formatargspec$54, PyString.fromInterned("Format an argument spec from the 4 values returned by getargspec.\n\n    The first four arguments are (args, varargs, varkw, defaults).  The\n    other four arguments are the corresponding optional formatting functions\n    that are called to turn names and values into strings.  The ninth\n    argument is an optional function to format the sequence of arguments."));
      var1.setlocal("formatargspec", var11);
      var3 = null;
      var1.setline(882);
      var9 = new PyObject[5];
      var9[0] = var1.getname("str");
      var1.setline(884);
      var7 = Py.EmptyObjects;
      var9[1] = new PyFunction(var1.f_globals, var7, f$55);
      var1.setline(885);
      var7 = Py.EmptyObjects;
      var9[2] = new PyFunction(var1.f_globals, var7, f$56);
      var1.setline(886);
      var7 = Py.EmptyObjects;
      var9[3] = new PyFunction(var1.f_globals, var7, f$57);
      var9[4] = var1.getname("joinseq");
      var11 = new PyFunction(var1.f_globals, var9, formatargvalues$58, PyString.fromInterned("Format an argument spec from the 4 values returned by getargvalues.\n\n    The first four arguments are (args, varargs, varkw, locals).  The\n    next four arguments are the corresponding optional formatting functions\n    that are called to turn names and values into strings.  The ninth\n    argument is an optional function to format the sequence of arguments."));
      var1.setlocal("formatargvalues", var11);
      var3 = null;
      var1.setline(906);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getcallargs$60, PyString.fromInterned("Get the mapping of arguments to values.\n\n    A dict is returned, with keys the function argument names (including the\n    names of the * and ** arguments, if any), and values the respective bound\n    values from 'positional' and 'named'."));
      var1.setlocal("getcallargs", var11);
      var3 = null;
      var1.setline(997);
      var6 = var1.getname("namedtuple").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Traceback"), (PyObject)PyString.fromInterned("filename lineno function code_context index"));
      var1.setlocal("Traceback", var6);
      var3 = null;
      var1.setline(999);
      var9 = new PyObject[]{Py.newInteger(1)};
      var11 = new PyFunction(var1.f_globals, var9, getframeinfo$63, PyString.fromInterned("Get information about a frame or traceback object.\n\n    A tuple of five things is returned: the filename, the line number of\n    the current line, the function name, a list of lines of context from\n    the source code, and the index of the current line within that list.\n    The optional second argument specifies the number of lines of context\n    to return, which are centered around the current line."));
      var1.setlocal("getframeinfo", var11);
      var3 = null;
      var1.setline(1032);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, getlineno$64, PyString.fromInterned("Get the line number from a frame object, allowing for optimization."));
      var1.setlocal("getlineno", var11);
      var3 = null;
      var1.setline(1037);
      var9 = new PyObject[]{Py.newInteger(1)};
      var11 = new PyFunction(var1.f_globals, var9, getouterframes$65, PyString.fromInterned("Get a list of records for a frame and all higher (calling) frames.\n\n    Each record contains a frame object, filename, line number, function\n    name, a list of lines of context, and index within the context."));
      var1.setlocal("getouterframes", var11);
      var3 = null;
      var1.setline(1048);
      var9 = new PyObject[]{Py.newInteger(1)};
      var11 = new PyFunction(var1.f_globals, var9, getinnerframes$66, PyString.fromInterned("Get a list of records for a traceback's frame and all lower frames.\n\n    Each record contains a frame object, filename, line number, function\n    name, a list of lines of context, and index within the context."));
      var1.setlocal("getinnerframes", var11);
      var3 = null;
      var1.setline(1059);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("sys"), (PyObject)PyString.fromInterned("_getframe")).__nonzero__()) {
         var1.setline(1060);
         var6 = var1.getname("sys").__getattr__("_getframe");
         var1.setlocal("currentframe", var6);
         var3 = null;
      } else {
         var1.setline(1062);
         var1.setline(1062);
         var9 = new PyObject[]{var1.getname("None")};
         var11 = new PyFunction(var1.f_globals, var9, f$67);
         var1.setlocal("currentframe", var11);
         var3 = null;
      }

      var1.setline(1064);
      var9 = new PyObject[]{Py.newInteger(1)};
      var11 = new PyFunction(var1.f_globals, var9, stack$68, PyString.fromInterned("Return a list of records for the stack above the caller's frame."));
      var1.setlocal("stack", var11);
      var3 = null;
      var1.setline(1068);
      var9 = new PyObject[]{Py.newInteger(1)};
      var11 = new PyFunction(var1.f_globals, var9, trace$69, PyString.fromInterned("Return a list of records for the stack below the current exception."));
      var1.setlocal("trace", var11);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ismodule$1(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyString.fromInterned("Return true if the object is a module.\n\n    Module objects provide these attributes:\n        __doc__         documentation string\n        __file__        filename (missing for built-in modules)");
      var1.setline(60);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("ModuleType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isclass$2(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyString.fromInterned("Return true if the object is a class.\n\n    Class objects provide these attributes:\n        __doc__         documentation string\n        __module__      name of module in which this class was defined");
      var1.setline(68);
      PyObject var3 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("type"), var1.getglobal("types").__getattr__("ClassType")})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ismethod$3(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyString.fromInterned("Return true if the object is an instance method.\n\n    Instance method objects provide these attributes:\n        __doc__         documentation string\n        __name__        name with which this method was defined\n        im_class        class object in which this method belongs\n        im_func         function object containing implementation of method\n        im_self         instance to which this method is bound, or None");
      var1.setline(79);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("MethodType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ismethoddescriptor$4(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyString.fromInterned("Return true if the object is a method descriptor.\n\n    But not if ismethod() or isclass() or isfunction() are true.\n\n    This is new in Python 2.2, and, for example, is true of int.__add__.\n    An object passing this test has a __get__ attribute but not a __set__\n    attribute, but beyond that the set of attributes varies.  __name__ is\n    usually sensible, and __doc__ often is.\n\n    Methods implemented via descriptors that also pass one of the other\n    tests return false from the ismethoddescriptor() test, simply because\n    the other tests promise more -- you can, e.g., count on having the\n    im_func attribute (etc) when an object passes ismethod().");
      var1.setline(95);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__get__"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__set__")).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("ismethod").__call__(var2, var1.getlocal(0)).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isfunction").__call__(var2, var1.getlocal(0)).__not__();
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isclass").__call__(var2, var1.getlocal(0)).__not__();
               }
            }
         }
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isdatadescriptor$5(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyString.fromInterned("Return true if the object is a data descriptor.\n\n    Data descriptors have both a __get__ and a __set__ attribute.  Examples are\n    properties (defined in Python) and getsets and members (defined in C).\n    Typically, data descriptors will also have __name__ and __doc__ attributes\n    (properties, getsets, and members have both of these attributes), but this\n    is not guaranteed.");
      var1.setline(109);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__set__"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__get__"));
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ismemberdescriptor$6(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyString.fromInterned("Return true if the object is a member descriptor.\n\n        Member descriptors are specialized descriptors defined in extension\n        modules.");
      var1.setline(118);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("MemberDescriptorType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ismemberdescriptor$7(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyString.fromInterned("Return true if the object is a member descriptor.\n\n        Member descriptors are specialized descriptors defined in extension\n        modules.");
      var1.setline(126);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isgetsetdescriptor$8(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyString.fromInterned("Return true if the object is a getset descriptor.\n\n        getset descriptors are specialized descriptors defined in extension\n        modules.");
      var1.setline(135);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("GetSetDescriptorType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isgetsetdescriptor$9(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyString.fromInterned("Return true if the object is a getset descriptor.\n\n        getset descriptors are specialized descriptors defined in extension\n        modules.");
      var1.setline(143);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isfunction$10(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyString.fromInterned("Return true if the object is a user-defined function.\n\n    Function objects provide these attributes:\n        __doc__         documentation string\n        __name__        name with which this function was defined\n        func_code       code object containing compiled function bytecode\n        func_defaults   tuple of any default values for arguments\n        func_doc        (same as __doc__)\n        func_globals    global namespace in which this function was defined\n        func_name       (same as __name__)");
      var1.setline(156);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("FunctionType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isgeneratorfunction$11(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyString.fromInterned("Return true if the object is a user-defined generator function.\n\n    Generator function objects provides same attributes as functions.\n\n    See help(isfunction) for attributes listing.");
      var1.setline(164);
      PyObject var10000 = var1.getglobal("bool");
      PyObject var10002 = var1.getglobal("isfunction").__call__(var2, var1.getlocal(0));
      if (!var10002.__nonzero__()) {
         var10002 = var1.getglobal("ismethod").__call__(var2, var1.getlocal(0));
      }

      if (var10002.__nonzero__()) {
         var10002 = var1.getlocal(0).__getattr__("func_code").__getattr__("co_flags")._and(var1.getglobal("CO_GENERATOR"));
      }

      PyObject var3 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isgenerator$12(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyString.fromInterned("Return true if the object is a generator.\n\n    Generator objects provide these attributes:\n        __iter__        defined to support interation over container\n        close           raises a new GeneratorExit exception inside the\n                        generator to terminate the iteration\n        gi_code         code object\n        gi_frame        frame object or possibly None once the generator has\n                        been exhausted\n        gi_running      set to 1 when generator is executing, 0 otherwise\n        next            return the next item from the container\n        send            resumes the generator and \"sends\" a value that becomes\n                        the result of the current yield-expression\n        throw           used to raise an exception inside the generator");
      var1.setline(182);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("GeneratorType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject istraceback$13(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyString.fromInterned("Return true if the object is a traceback.\n\n    Traceback objects provide these attributes:\n        tb_frame        frame object at this level\n        tb_lasti        index of last attempted instruction in bytecode\n        tb_lineno       current line number in Python source code\n        tb_next         next inner traceback object (called by this level)");
      var1.setline(192);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("TracebackType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isframe$14(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyString.fromInterned("Return true if the object is a frame object.\n\n    Frame objects provide these attributes:\n        f_back          next outer frame object (this frame's caller)\n        f_builtins      built-in namespace seen by this frame\n        f_code          code object being executed in this frame\n        f_exc_traceback traceback if raised in this frame, or None\n        f_exc_type      exception type if raised in this frame, or None\n        f_exc_value     exception value if raised in this frame, or None\n        f_globals       global namespace seen by this frame\n        f_lasti         index of last attempted instruction in bytecode\n        f_lineno        current line number in Python source code\n        f_locals        local namespace seen by this frame\n        f_restricted    0 or 1 if frame is in restricted execution mode\n        f_trace         tracing function for this frame, or None");
      var1.setline(210);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("FrameType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iscode$15(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      PyString.fromInterned("Return true if the object is a code object.\n\n    Code objects provide these attributes:\n        co_argcount     number of arguments (not including * or ** args)\n        co_code         string of raw compiled bytecode\n        co_consts       tuple of constants used in the bytecode\n        co_filename     name of file in which this code object was created\n        co_firstlineno  number of first line in Python source code\n        co_flags        bitmap: 1=optimized | 2=newlocals | 4=*arg | 8=**arg\n        co_lnotab       encoded mapping of line numbers to bytecode indices\n        co_name         name with which this code object was defined\n        co_names        tuple of names of local variables\n        co_nlocals      number of local variables\n        co_stacksize    virtual machine stack space required\n        co_varnames     tuple of names of arguments and local variables");
      var1.setline(228);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("CodeType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isbuiltin$16(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      PyString.fromInterned("Return true if the object is a built-in function or method.\n\n    Built-in functions and methods provide these attributes:\n        __doc__         documentation string\n        __name__        original name of this function or method\n        __self__        instance to which a method is bound, or None");
      var1.setline(237);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("types").__getattr__("BuiltinFunctionType"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isroutine$17(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyString.fromInterned("Return true if the object is any kind of function or method.");
      var1.setline(241);
      PyObject var10000 = var1.getglobal("isbuiltin").__call__(var2, var1.getlocal(0));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("isfunction").__call__(var2, var1.getlocal(0));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("ismethod").__call__(var2, var1.getlocal(0));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("ismethoddescriptor").__call__(var2, var1.getlocal(0));
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("_jython");
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_ReflectedFunctionType"));
                  }
               }
            }
         }
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isabstract$18(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("Return true if the object is an abstract base class (ABC).");
      var1.setline(249);
      PyObject var10000 = var1.getglobal("bool");
      PyObject var10002 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("type"));
      if (var10002.__nonzero__()) {
         var10002 = var1.getlocal(0).__getattr__("__flags__")._and(var1.getglobal("TPFLAGS_IS_ABSTRACT"));
      }

      PyObject var3 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getmembers$19(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyString.fromInterned("Return all members of an object as (name, value) pairs sorted by name.\n    Optionally, only return members that satisfy a given predicate.");
      var1.setline(254);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(255);
      PyObject var7 = var1.getglobal("dir").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(255);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(262);
            var1.getlocal(2).__getattr__("sort").__call__(var2);
            var1.setline(263);
            var7 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(3, var4);

         PyException var5;
         try {
            var1.setline(257);
            PyObject var8 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(3));
            var1.setlocal(4, var8);
            var5 = null;
         } catch (Throwable var6) {
            var5 = Py.setException(var6, var1);
            if (var5.match(var1.getglobal("AttributeError"))) {
               continue;
            }

            throw var5;
         }

         var1.setline(260);
         PyObject var10000 = var1.getlocal(1).__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__call__(var2, var1.getlocal(4));
         }

         if (var10000.__nonzero__()) {
            var1.setline(261);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
         }
      }
   }

   public PyObject classify_class_attrs$20(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      PyString.fromInterned("Return list of attribute-descriptor tuples.\n\n    For each name in dir(cls), the return list contains a 4-tuple\n    with these elements:\n\n        0. The name (a string).\n\n        1. The kind of attribute this is, one of these strings:\n               'class method'    created via classmethod()\n               'static method'   created via staticmethod()\n               'property'        created via property()\n               'method'          any other flavor of method\n               'data'            not a method\n\n        2. The class which defined this attribute (a class).\n\n        3. The object as obtained directly from the defining class's\n           __dict__, not via getattr.  This is especially important for\n           data attributes:  C.data is just a data object, but\n           C.__dict__['data'] may be a data descriptor with additional\n           info, like a __doc__ string.\n    ");
      var1.setline(291);
      PyObject var3 = var1.getglobal("getmro").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(292);
      var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(293);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(294);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(294);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(333);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(301);
         PyObject var5 = var1.getglobal("None");
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(302);
         var5 = (new PyTuple(new PyObject[]{var1.getlocal(0)}))._add(var1.getlocal(1)).__iter__();

         PyObject var10000;
         while(true) {
            var1.setline(302);
            PyObject var6 = var5.__iternext__();
            PyObject var7;
            if (var6 == null) {
               var1.setline(308);
               var7 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4));
               var1.setlocal(7, var7);
               var7 = null;
               var1.setline(309);
               var7 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)PyString.fromInterned("__objclass__"), (PyObject)var1.getlocal(5));
               var1.setlocal(5, var7);
               var7 = null;
               break;
            }

            var1.setlocal(6, var6);
            var1.setline(303);
            var7 = var1.getlocal(4);
            var10000 = var7._in(var1.getlocal(6).__getattr__("__dict__"));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(304);
               var7 = var1.getlocal(6).__getattr__("__dict__").__getitem__(var1.getlocal(4));
               var1.setlocal(7, var7);
               var7 = null;
               var1.setline(305);
               var7 = var1.getlocal(6);
               var1.setlocal(5, var7);
               var7 = null;
               break;
            }
         }

         var1.setline(312);
         PyString var9;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("staticmethod")).__nonzero__()) {
            var1.setline(313);
            var9 = PyString.fromInterned("static method");
            var1.setlocal(8, var9);
            var5 = null;
         } else {
            var1.setline(314);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("classmethod")).__nonzero__()) {
               var1.setline(315);
               var9 = PyString.fromInterned("class method");
               var1.setlocal(8, var9);
               var5 = null;
            } else {
               var1.setline(316);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("property")).__nonzero__()) {
                  var1.setline(317);
                  var9 = PyString.fromInterned("property");
                  var1.setlocal(8, var9);
                  var5 = null;
               } else {
                  var1.setline(318);
                  if (var1.getglobal("ismethoddescriptor").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                     var1.setline(319);
                     var9 = PyString.fromInterned("method");
                     var1.setlocal(8, var9);
                     var5 = null;
                  } else {
                     var1.setline(320);
                     if (var1.getglobal("isdatadescriptor").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                        var1.setline(321);
                        var9 = PyString.fromInterned("data");
                        var1.setlocal(8, var9);
                        var5 = null;
                     } else {
                        var1.setline(323);
                        var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4));
                        var1.setlocal(9, var5);
                        var5 = null;
                        var1.setline(324);
                        var10000 = var1.getglobal("ismethod").__call__(var2, var1.getlocal(9));
                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getglobal("ismethoddescriptor").__call__(var2, var1.getlocal(9));
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(326);
                           var9 = PyString.fromInterned("method");
                           var1.setlocal(8, var9);
                           var5 = null;
                        } else {
                           var1.setline(328);
                           var9 = PyString.fromInterned("data");
                           var1.setlocal(8, var9);
                           var5 = null;
                        }

                        var1.setline(329);
                        var5 = var1.getlocal(9);
                        var1.setlocal(7, var5);
                        var5 = null;
                     }
                  }
               }
            }
         }

         var1.setline(331);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("Attribute").__call__(var2, var1.getlocal(4), var1.getlocal(8), var1.getlocal(5), var1.getlocal(7)));
      }
   }

   public PyObject _searchbases$21(PyFrame var1, ThreadState var2) {
      var1.setline(338);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(339);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(340);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0));
         var1.setline(341);
         var3 = var1.getlocal(0).__getattr__("__bases__").__iter__();

         while(true) {
            var1.setline(341);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(342);
            var1.getglobal("_searchbases").__call__(var2, var1.getlocal(2), var1.getlocal(1));
         }
      }
   }

   public PyObject getmro$22(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      PyString.fromInterned("Return tuple of base classes (including cls) in method resolution order.");
      var1.setline(346);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__mro__")).__nonzero__()) {
         var1.setline(347);
         var3 = var1.getlocal(0).__getattr__("__mro__");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(349);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(350);
         var1.getglobal("_searchbases").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(351);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject indentsize$23(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyString.fromInterned("Return the indent size, in spaces, at the start of a line of text.");
      var1.setline(356);
      PyObject var3 = var1.getglobal("string").__getattr__("expandtabs").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(357);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(var1.getglobal("len").__call__(var2, var1.getglobal("string").__getattr__("lstrip").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getdoc$24(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      PyString.fromInterned("Get the documentation string for an object.\n\n    All tabs are expanded to spaces.  To clean up docstrings that are\n    indented to line up with blocks of code, any whitespace than can be\n    uniformly removed from the second line onwards is removed.");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(366);
         PyObject var6 = var1.getlocal(0).__getattr__("__doc__");
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("AttributeError"))) {
            var1.setline(368);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(369);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("types").__getattr__("StringTypes")).__not__().__nonzero__()) {
         var1.setline(370);
         var4 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(371);
         var4 = var1.getglobal("cleandoc").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject cleandoc$25(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyString.fromInterned("Clean up indentation from docstrings.\n\n    Any whitespace that can be uniformly removed from the second line\n    onwards is removed.");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(379);
         PyObject var9 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("string").__getattr__("expandtabs").__call__(var2, var1.getlocal(0)), (PyObject)PyString.fromInterned("\n"));
         var1.setlocal(1, var9);
         var3 = null;
      } catch (Throwable var8) {
         var3 = Py.setException(var8, var1);
         if (var3.match(var1.getglobal("UnicodeError"))) {
            var1.setline(381);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(384);
      PyObject var5 = var1.getglobal("sys").__getattr__("maxint");
      var1.setlocal(2, var5);
      var5 = null;
      var1.setline(385);
      var5 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         var1.setline(385);
         PyObject var6 = var5.__iternext__();
         PyObject var7;
         if (var6 == null) {
            var1.setline(391);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(392);
               var5 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getattr__("lstrip").__call__(var2);
               var1.getlocal(1).__setitem__((PyObject)Py.newInteger(0), var5);
               var5 = null;
            }

            var1.setline(393);
            var5 = var1.getlocal(2);
            PyObject var10000 = var5._lt(var1.getglobal("sys").__getattr__("maxint"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(394);
               var5 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

               while(true) {
                  var1.setline(394);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(6, var6);
                  var1.setline(394);
                  var7 = var1.getlocal(1).__getitem__(var1.getlocal(6)).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
                  var1.getlocal(1).__setitem__(var1.getlocal(6), var7);
                  var7 = null;
               }
            }

            while(true) {
               var1.setline(396);
               var10000 = var1.getlocal(1);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(1).__getitem__(Py.newInteger(-1)).__not__();
               }

               if (!var10000.__nonzero__()) {
                  while(true) {
                     var1.setline(398);
                     var10000 = var1.getlocal(1);
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__not__();
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(400);
                        var4 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("\n"));
                        var1.f_lasti = -1;
                        return var4;
                     }

                     var1.setline(399);
                     var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                  }
               }

               var1.setline(397);
               var1.getlocal(1).__getattr__("pop").__call__(var2);
            }
         }

         var1.setlocal(3, var6);
         var1.setline(386);
         var7 = var1.getglobal("len").__call__(var2, var1.getglobal("string").__getattr__("lstrip").__call__(var2, var1.getlocal(3)));
         var1.setlocal(4, var7);
         var7 = null;
         var1.setline(387);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(388);
            var7 = var1.getglobal("len").__call__(var2, var1.getlocal(3))._sub(var1.getlocal(4));
            var1.setlocal(5, var7);
            var7 = null;
            var1.setline(389);
            var7 = var1.getglobal("min").__call__(var2, var1.getlocal(2), var1.getlocal(5));
            var1.setlocal(2, var7);
            var7 = null;
         }
      }
   }

   public PyObject getfile$26(PyFrame var1, ThreadState var2) {
      var1.setline(403);
      PyString.fromInterned("Work out which source or compiled file an object was defined in.");
      var1.setline(404);
      PyObject var3;
      if (var1.getglobal("ismodule").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(405);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__file__")).__nonzero__()) {
            var1.setline(406);
            var3 = var1.getlocal(0).__getattr__("__file__");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(407);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("{!r} is a built-in module").__getattr__("format").__call__(var2, var1.getlocal(0))));
         }
      } else {
         var1.setline(408);
         PyObject var4;
         if (var1.getglobal("isclass").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(409);
            var4 = var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__(var2, var1.getlocal(0).__getattr__("__module__"));
            var1.setlocal(0, var4);
            var4 = null;
            var1.setline(410);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__file__")).__nonzero__()) {
               var1.setline(411);
               var3 = var1.getlocal(0).__getattr__("__file__");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(412);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("{!r} is a built-in class").__getattr__("format").__call__(var2, var1.getlocal(0))));
            }
         } else {
            var1.setline(413);
            if (var1.getglobal("ismethod").__call__(var2, var1.getlocal(0)).__nonzero__()) {
               var1.setline(414);
               var4 = var1.getlocal(0).__getattr__("im_func");
               var1.setlocal(0, var4);
               var4 = null;
            }

            var1.setline(415);
            if (var1.getglobal("isfunction").__call__(var2, var1.getlocal(0)).__nonzero__()) {
               var1.setline(416);
               var4 = var1.getlocal(0).__getattr__("func_code");
               var1.setlocal(0, var4);
               var4 = null;
            }

            var1.setline(417);
            if (var1.getglobal("istraceback").__call__(var2, var1.getlocal(0)).__nonzero__()) {
               var1.setline(418);
               var4 = var1.getlocal(0).__getattr__("tb_frame");
               var1.setlocal(0, var4);
               var4 = null;
            }

            var1.setline(419);
            if (var1.getglobal("isframe").__call__(var2, var1.getlocal(0)).__nonzero__()) {
               var1.setline(420);
               var4 = var1.getlocal(0).__getattr__("f_code");
               var1.setlocal(0, var4);
               var4 = null;
            }

            var1.setline(421);
            if (var1.getglobal("iscode").__call__(var2, var1.getlocal(0)).__nonzero__()) {
               var1.setline(422);
               var3 = var1.getlocal(0).__getattr__("co_filename");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(423);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("{!r} is not a module, class, method, function, traceback, frame, or code object").__getattr__("format").__call__(var2, var1.getlocal(0))));
            }
         }
      }
   }

   public PyObject getmoduleinfo$27(PyFrame var1, ThreadState var2) {
      var1.setline(429);
      PyString.fromInterned("Get the module name, suffix, mode, and module type for a given file.");
      var1.setline(430);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(431);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(431);
      PyObject[] var7 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var7, f$28)), (PyObject)var1.getglobal("imp").__getattr__("get_suffixes").__call__(var2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(434);
      var1.getlocal(2).__getattr__("sort").__call__(var2);
      var1.setline(435);
      var3 = var1.getlocal(2).__iter__();

      PyObject var8;
      do {
         var1.setline(435);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 4);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(436);
         var8 = var1.getlocal(1).__getslice__(var1.getlocal(3), (PyObject)null, (PyObject)null);
         var10000 = var8._eq(var1.getlocal(4));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(437);
      var8 = var1.getglobal("ModuleInfo").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject f$28(PyFrame var1, ThreadState var2) {
      var1.setline(431);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0))).__neg__(), var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getitem__(Py.newInteger(1)), var1.getlocal(0).__getitem__(Py.newInteger(2))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getmodulename$29(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyString.fromInterned("Return the module name for a given file, or None.");
      var1.setline(441);
      PyObject var3 = var1.getglobal("getmoduleinfo").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(442);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(442);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject getsourcefile$30(PyFrame var1, ThreadState var2) {
      var1.setline(447);
      PyString.fromInterned("Return the filename that can be used to locate an object's source.\n    Return None if no way can be identified to get the source.\n    ");
      var1.setline(448);
      PyObject var3 = var1.getglobal("getfile").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(449);
      var3 = var1.getglobal("string").__getattr__("lower").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(-4), (PyObject)null, (PyObject)null));
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned(".pyc"), PyString.fromInterned(".pyo")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(450);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-4), (PyObject)null)._add(PyString.fromInterned(".py"));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(451);
         if (var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$py.class")).__nonzero__()) {
            var1.setline(452);
            var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-9), (PyObject)null)._add(PyString.fromInterned(".py"));
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(453);
      var3 = var1.getglobal("imp").__getattr__("get_suffixes").__call__(var2).__iter__();

      PyObject var8;
      do {
         var1.setline(453);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(457);
            if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(458);
               var8 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var8;
            }

            var1.setline(460);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("getmodule").__call__(var2, var1.getlocal(0), var1.getlocal(1)), (PyObject)PyString.fromInterned("__loader__")).__nonzero__()) {
               var1.setline(461);
               var8 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var8;
            }

            var1.setline(463);
            var3 = var1.getlocal(1);
            var10000 = var3._in(var1.getglobal("linecache").__getattr__("cache"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(464);
               var8 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var8;
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(454);
         PyString var7 = PyString.fromInterned("b");
         var10000 = var7._in(var1.getlocal(3));
         var5 = null;
         if (var10000.__nonzero__()) {
            var8 = var1.getglobal("string").__getattr__("lower").__call__(var2, var1.getlocal(1).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(2)).__neg__(), (PyObject)null, (PyObject)null));
            var10000 = var8._eq(var1.getlocal(2));
            var5 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(456);
      var8 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject getabsfile$31(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyString.fromInterned("Return an absolute path to the source or compiled file for an object.\n\n    The idea is for each object to have a unique origin, so this routine\n    normalizes the result as much as possible.");
      var1.setline(471);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(472);
         var10000 = var1.getglobal("getsourcefile").__call__(var2, var1.getlocal(0));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("getfile").__call__(var2, var1.getlocal(0));
         }

         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(473);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getmodule$32(PyFrame var1, ThreadState var2) {
      var1.setline(479);
      PyString.fromInterned("Return the module an object was defined in, or None if not found.");
      var1.setline(480);
      PyObject var3;
      if (var1.getglobal("ismodule").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(481);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(482);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__module__")).__nonzero__()) {
            var1.setline(483);
            var3 = var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__(var2, var1.getlocal(0).__getattr__("__module__"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(485);
            PyObject var4 = var1.getlocal(1);
            PyObject var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(1);
               var10000 = var4._in(var1.getglobal("modulesbyfile"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(486);
               var3 = var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__(var2, var1.getglobal("modulesbyfile").__getitem__(var1.getlocal(1)));
               var1.f_lasti = -1;
               return var3;
            } else {
               try {
                  var1.setline(489);
                  var4 = var1.getglobal("getabsfile").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                  var1.setlocal(2, var4);
                  var4 = null;
               } catch (Throwable var8) {
                  PyException var9 = Py.setException(var8, var1);
                  if (var9.match(var1.getglobal("TypeError"))) {
                     var1.setline(491);
                     var3 = var1.getglobal("None");
                     var1.f_lasti = -1;
                     return var3;
                  }

                  throw var9;
               }

               var1.setline(492);
               var4 = var1.getlocal(2);
               var10000 = var4._in(var1.getglobal("modulesbyfile"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(493);
                  var3 = var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__(var2, var1.getglobal("modulesbyfile").__getitem__(var1.getlocal(2)));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(496);
                  var4 = var1.getglobal("sys").__getattr__("modules").__getattr__("items").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(496);
                     PyObject var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(507);
                        var4 = var1.getlocal(2);
                        var10000 = var4._in(var1.getglobal("modulesbyfile"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(508);
                           var3 = var1.getglobal("sys").__getattr__("modules").__getattr__("get").__call__(var2, var1.getglobal("modulesbyfile").__getitem__(var1.getlocal(2)));
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setline(510);
                        var4 = var1.getglobal("sys").__getattr__("modules").__getitem__(PyString.fromInterned("__main__"));
                        var1.setlocal(6, var4);
                        var4 = null;
                        var1.setline(511);
                        if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__name__")).__not__().__nonzero__()) {
                           var1.setline(512);
                           var3 = var1.getglobal("None");
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setline(513);
                        if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(6), var1.getlocal(0).__getattr__("__name__")).__nonzero__()) {
                           var1.setline(514);
                           var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(6), var1.getlocal(0).__getattr__("__name__"));
                           var1.setlocal(7, var4);
                           var4 = null;
                           var1.setline(515);
                           var4 = var1.getlocal(7);
                           var10000 = var4._is(var1.getlocal(0));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(516);
                              var3 = var1.getlocal(6);
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }

                        var1.setline(518);
                        var4 = var1.getglobal("sys").__getattr__("modules").__getitem__(PyString.fromInterned("__builtin__"));
                        var1.setlocal(8, var4);
                        var4 = null;
                        var1.setline(519);
                        if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(8), var1.getlocal(0).__getattr__("__name__")).__nonzero__()) {
                           var1.setline(520);
                           var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(8), var1.getlocal(0).__getattr__("__name__"));
                           var1.setlocal(9, var4);
                           var4 = null;
                           var1.setline(521);
                           var4 = var1.getlocal(9);
                           var10000 = var4._is(var1.getlocal(0));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(522);
                              var3 = var1.getlocal(8);
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     PyObject[] var6 = Py.unpackSequence(var5, 2);
                     PyObject var7 = var6[0];
                     var1.setlocal(3, var7);
                     var7 = null;
                     var7 = var6[1];
                     var1.setlocal(4, var7);
                     var7 = null;
                     var1.setline(497);
                     var10000 = var1.getglobal("ismodule").__call__(var2, var1.getlocal(4));
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("__file__"));
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(498);
                        PyObject var10 = var1.getlocal(4).__getattr__("__file__");
                        var1.setlocal(5, var10);
                        var6 = null;
                        var1.setline(499);
                        var10 = var1.getlocal(5);
                        var10000 = var10._eq(var1.getglobal("_filesbymodname").__getattr__("get").__call__(var2, var1.getlocal(3), var1.getglobal("None")));
                        var6 = null;
                        if (!var10000.__nonzero__()) {
                           var1.setline(502);
                           var10 = var1.getlocal(5);
                           var1.getglobal("_filesbymodname").__setitem__(var1.getlocal(3), var10);
                           var6 = null;
                           var1.setline(503);
                           var10 = var1.getglobal("getabsfile").__call__(var2, var1.getlocal(4));
                           var1.setlocal(5, var10);
                           var6 = null;
                           var1.setline(505);
                           var10 = var1.getlocal(4).__getattr__("__name__");
                           var1.getglobal("modulesbyfile").__setitem__(var1.getlocal(5), var10);
                           var1.getglobal("modulesbyfile").__setitem__(var1.getglobal("os").__getattr__("path").__getattr__("realpath").__call__(var2, var1.getlocal(5)), var10);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject findsource$33(PyFrame var1, ThreadState var2) {
      var1.setline(530);
      PyString.fromInterned("Return the entire source file and starting line number for an object.\n\n    The argument may be a module, class, method, function, traceback, frame,\n    or code object.  The source code is returned as a list of all the lines\n    in the file and the line number indexes a line in that list.  An IOError\n    is raised if the source code cannot be retrieved.");
      var1.setline(532);
      PyObject var3 = var1.getglobal("getfile").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(533);
      var3 = var1.getglobal("getsourcefile").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(534);
      PyObject var10000 = var1.getlocal(2).__not__();
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0))._add(var1.getlocal(1).__getitem__(Py.newInteger(-1)));
         var10000 = var3._ne(PyString.fromInterned("<>"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(535);
         throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("source code not available")));
      } else {
         var1.setline(536);
         var1.setline(536);
         var3 = var1.getlocal(2).__nonzero__() ? var1.getlocal(2) : var1.getlocal(1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(538);
         var3 = var1.getglobal("getmodule").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(539);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(540);
            var3 = var1.getglobal("linecache").__getattr__("getlines").__call__(var2, var1.getlocal(1), var1.getlocal(3).__getattr__("__dict__"));
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(542);
            var3 = var1.getglobal("linecache").__getattr__("getlines").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(543);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(544);
            throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("could not get source code")));
         } else {
            var1.setline(546);
            PyTuple var7;
            if (var1.getglobal("ismodule").__call__(var2, var1.getlocal(0)).__nonzero__()) {
               var1.setline(547);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(4), Py.newInteger(0)});
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(549);
               PyObject var4;
               if (var1.getglobal("isclass").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                  var1.setline(550);
                  var4 = var1.getlocal(0).__getattr__("__name__");
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(551);
                  var4 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("^(\\s*)class\\s*")._add(var1.getlocal(5))._add(PyString.fromInterned("\\b")));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(555);
                  PyList var8 = new PyList(Py.EmptyObjects);
                  var1.setlocal(7, var8);
                  var4 = null;
                  var1.setline(556);
                  var4 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(4))).__iter__();

                  while(true) {
                     var1.setline(556);
                     PyObject var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(564);
                        if (var1.getlocal(7).__nonzero__()) {
                           var1.setline(567);
                           var1.getlocal(7).__getattr__("sort").__call__(var2);
                           var1.setline(568);
                           var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(7).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1))});
                           var1.f_lasti = -1;
                           return var7;
                        }

                        var1.setline(570);
                        throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("could not find class definition")));
                     }

                     var1.setlocal(8, var5);
                     var1.setline(557);
                     PyObject var6 = var1.getlocal(6).__getattr__("match").__call__(var2, var1.getlocal(4).__getitem__(var1.getlocal(8)));
                     var1.setlocal(9, var6);
                     var6 = null;
                     var1.setline(558);
                     if (var1.getlocal(9).__nonzero__()) {
                        var1.setline(560);
                        var6 = var1.getlocal(4).__getitem__(var1.getlocal(8)).__getitem__(Py.newInteger(0));
                        var10000 = var6._eq(PyString.fromInterned("c"));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(561);
                           var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(8)});
                           var1.f_lasti = -1;
                           return var7;
                        }

                        var1.setline(563);
                        var1.getlocal(7).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getlocal(8)})));
                     }
                  }
               } else {
                  var1.setline(572);
                  if (var1.getglobal("ismethod").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                     var1.setline(573);
                     var4 = var1.getlocal(0).__getattr__("im_func");
                     var1.setlocal(0, var4);
                     var4 = null;
                  }

                  var1.setline(574);
                  if (var1.getglobal("isfunction").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                     var1.setline(575);
                     var4 = var1.getlocal(0).__getattr__("func_code");
                     var1.setlocal(0, var4);
                     var4 = null;
                  }

                  var1.setline(576);
                  if (var1.getglobal("istraceback").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                     var1.setline(577);
                     var4 = var1.getlocal(0).__getattr__("tb_frame");
                     var1.setlocal(0, var4);
                     var4 = null;
                  }

                  var1.setline(578);
                  if (var1.getglobal("isframe").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                     var1.setline(579);
                     var4 = var1.getlocal(0).__getattr__("f_code");
                     var1.setlocal(0, var4);
                     var4 = null;
                  }

                  var1.setline(580);
                  if (!var1.getglobal("iscode").__call__(var2, var1.getlocal(0)).__nonzero__()) {
                     var1.setline(589);
                     throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("could not find code object")));
                  } else {
                     var1.setline(581);
                     if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("co_firstlineno")).__not__().__nonzero__()) {
                        var1.setline(582);
                        throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("could not find function definition")));
                     } else {
                        var1.setline(583);
                        var4 = var1.getlocal(0).__getattr__("co_firstlineno")._sub(Py.newInteger(1));
                        var1.setlocal(10, var4);
                        var4 = null;
                        var1.setline(584);
                        var4 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(\\s*def\\s)|(.*(?<!\\w)lambda(:|\\s))|^(\\s*@)"));
                        var1.setlocal(6, var4);
                        var4 = null;

                        while(true) {
                           var1.setline(585);
                           var4 = var1.getlocal(10);
                           var10000 = var4._gt(Py.newInteger(0));
                           var4 = null;
                           if (!var10000.__nonzero__()) {
                              break;
                           }

                           var1.setline(586);
                           if (var1.getlocal(6).__getattr__("match").__call__(var2, var1.getlocal(4).__getitem__(var1.getlocal(10))).__nonzero__()) {
                              break;
                           }

                           var1.setline(587);
                           var4 = var1.getlocal(10)._sub(Py.newInteger(1));
                           var1.setlocal(10, var4);
                           var4 = null;
                        }

                        var1.setline(588);
                        var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(10)});
                        var1.f_lasti = -1;
                        return var7;
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject getcomments$34(PyFrame var1, ThreadState var2) {
      var1.setline(595);
      PyString.fromInterned("Get lines of comments immediately preceding an object's source code.\n\n    Returns None when source can't be found.\n    ");

      PyException var3;
      PyObject var4;
      PyObject var7;
      try {
         var1.setline(597);
         var7 = var1.getglobal("findsource").__call__(var2, var1.getlocal(0));
         PyObject[] var9 = Py.unpackSequence(var7, 2);
         PyObject var5 = var9[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var9[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(new PyTuple(new PyObject[]{var1.getglobal("IOError"), var1.getglobal("TypeError")}))) {
            var1.setline(599);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(601);
      PyObject var10000;
      PyList var10;
      if (var1.getglobal("ismodule").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(603);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(3, var8);
         var3 = null;
         var1.setline(604);
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var7 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
            var10000 = var7._eq(PyString.fromInterned("#!"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(604);
            var8 = Py.newInteger(1);
            var1.setlocal(3, var8);
            var3 = null;
         }

         while(true) {
            var1.setline(605);
            var7 = var1.getlocal(3);
            var10000 = var7._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var3 = null;
            if (var10000.__nonzero__()) {
               var7 = var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(3)));
               var10000 = var7._in(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("#")}));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(607);
               var7 = var1.getlocal(3);
               var10000 = var7._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var7 = var1.getlocal(1).__getitem__(var1.getlocal(3)).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
                  var10000 = var7._eq(PyString.fromInterned("#"));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(608);
                  var10 = new PyList(Py.EmptyObjects);
                  var1.setlocal(4, var10);
                  var3 = null;
                  var1.setline(609);
                  var7 = var1.getlocal(3);
                  var1.setlocal(5, var7);
                  var3 = null;

                  while(true) {
                     var1.setline(610);
                     var7 = var1.getlocal(5);
                     var10000 = var7._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var7 = var1.getlocal(1).__getitem__(var1.getlocal(5)).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
                        var10000 = var7._eq(PyString.fromInterned("#"));
                        var3 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(613);
                        var4 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned(""));
                        var1.f_lasti = -1;
                        return var4;
                     }

                     var1.setline(611);
                     var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("string").__getattr__("expandtabs").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5))));
                     var1.setline(612);
                     var7 = var1.getlocal(5)._add(Py.newInteger(1));
                     var1.setlocal(5, var7);
                     var3 = null;
                  }
               }
               break;
            }

            var1.setline(606);
            var7 = var1.getlocal(3)._add(Py.newInteger(1));
            var1.setlocal(3, var7);
            var3 = null;
         }
      } else {
         var1.setline(616);
         var7 = var1.getlocal(2);
         var10000 = var7._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(617);
            var7 = var1.getglobal("indentsize").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(2)));
            var1.setlocal(6, var7);
            var3 = null;
            var1.setline(618);
            var7 = var1.getlocal(2)._sub(Py.newInteger(1));
            var1.setlocal(5, var7);
            var3 = null;
            var1.setline(619);
            var7 = var1.getlocal(5);
            var10000 = var7._ge(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var7 = var1.getglobal("string").__getattr__("lstrip").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5))).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
               var10000 = var7._eq(PyString.fromInterned("#"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var7 = var1.getglobal("indentsize").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)));
                  var10000 = var7._eq(var1.getlocal(6));
                  var3 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(621);
               var10 = new PyList(new PyObject[]{var1.getglobal("string").__getattr__("lstrip").__call__(var2, var1.getglobal("string").__getattr__("expandtabs").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5))))});
               var1.setlocal(4, var10);
               var3 = null;
               var1.setline(622);
               var7 = var1.getlocal(5);
               var10000 = var7._gt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(623);
                  var7 = var1.getlocal(5)._sub(Py.newInteger(1));
                  var1.setlocal(5, var7);
                  var3 = null;
                  var1.setline(624);
                  var7 = var1.getglobal("string").__getattr__("lstrip").__call__(var2, var1.getglobal("string").__getattr__("expandtabs").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5))));
                  var1.setlocal(7, var7);
                  var3 = null;

                  while(true) {
                     var1.setline(625);
                     var7 = var1.getlocal(7).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
                     var10000 = var7._eq(PyString.fromInterned("#"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var7 = var1.getglobal("indentsize").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)));
                        var10000 = var7._eq(var1.getlocal(6));
                        var3 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(626);
                     var10 = new PyList(new PyObject[]{var1.getlocal(7)});
                     var1.getlocal(4).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var10);
                     var3 = null;
                     var1.setline(627);
                     var7 = var1.getlocal(5)._sub(Py.newInteger(1));
                     var1.setlocal(5, var7);
                     var3 = null;
                     var1.setline(628);
                     var7 = var1.getlocal(5);
                     var10000 = var7._lt(Py.newInteger(0));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(629);
                     var7 = var1.getglobal("string").__getattr__("lstrip").__call__(var2, var1.getglobal("string").__getattr__("expandtabs").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5))));
                     var1.setlocal(7, var7);
                     var3 = null;
                  }
               }

               while(true) {
                  var1.setline(630);
                  var10000 = var1.getlocal(4);
                  if (var10000.__nonzero__()) {
                     var7 = var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(0)));
                     var10000 = var7._eq(PyString.fromInterned("#"));
                     var3 = null;
                  }

                  if (!var10000.__nonzero__()) {
                     while(true) {
                        var1.setline(632);
                        var10000 = var1.getlocal(4);
                        if (var10000.__nonzero__()) {
                           var7 = var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(-1)));
                           var10000 = var7._eq(PyString.fromInterned("#"));
                           var3 = null;
                        }

                        if (!var10000.__nonzero__()) {
                           var1.setline(634);
                           var4 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned(""));
                           var1.f_lasti = -1;
                           return var4;
                        }

                        var1.setline(633);
                        var10 = new PyList(Py.EmptyObjects);
                        var1.getlocal(4).__setslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null, var10);
                        var3 = null;
                     }
                  }

                  var1.setline(631);
                  var10 = new PyList(Py.EmptyObjects);
                  var1.getlocal(4).__setslice__((PyObject)null, Py.newInteger(1), (PyObject)null, var10);
                  var3 = null;
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject EndOfBlock$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(636);
      return var1.getf_locals();
   }

   public PyObject BlockFinder$36(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Provide a tokeneater() method to detect the end of a code block."));
      var1.setline(639);
      PyString.fromInterned("Provide a tokeneater() method to detect the end of a code block.");
      var1.setline(640);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$37, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(647);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tokeneater$38, (PyObject)null);
      var1.setlocal("tokeneater", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$37(PyFrame var1, ThreadState var2) {
      var1.setline(641);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"indent", var3);
      var3 = null;
      var1.setline(642);
      PyObject var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("islambda", var4);
      var3 = null;
      var1.setline(643);
      var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("started", var4);
      var3 = null;
      var1.setline(644);
      var4 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("passline", var4);
      var3 = null;
      var1.setline(645);
      var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"last", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tokeneater$38(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyObject var3 = var1.getlocal(3);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(649);
      var3 = var1.getlocal(4);
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(9, var5);
      var5 = null;
      var3 = null;
      var1.setline(650);
      PyObject var10000;
      if (var1.getlocal(0).__getattr__("started").__not__().__nonzero__()) {
         var1.setline(652);
         var3 = var1.getlocal(2);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("def"), PyString.fromInterned("class"), PyString.fromInterned("lambda")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(653);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(PyString.fromInterned("lambda"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(654);
               var3 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("islambda", var3);
               var3 = null;
            }

            var1.setline(655);
            var3 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("started", var3);
            var3 = null;
         }

         var1.setline(656);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("passline", var3);
         var3 = null;
      } else {
         var1.setline(657);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getglobal("tokenize").__getattr__("NEWLINE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(658);
            var3 = var1.getglobal("False");
            var1.getlocal(0).__setattr__("passline", var3);
            var3 = null;
            var1.setline(659);
            var3 = var1.getlocal(6);
            var1.getlocal(0).__setattr__("last", var3);
            var3 = null;
            var1.setline(660);
            if (var1.getlocal(0).__getattr__("islambda").__nonzero__()) {
               var1.setline(661);
               throw Py.makeException(var1.getglobal("EndOfBlock"));
            }
         } else {
            var1.setline(662);
            if (var1.getlocal(0).__getattr__("passline").__nonzero__()) {
               var1.setline(663);
            } else {
               var1.setline(664);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(var1.getglobal("tokenize").__getattr__("INDENT"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(665);
                  var3 = var1.getlocal(0).__getattr__("indent")._add(Py.newInteger(1));
                  var1.getlocal(0).__setattr__("indent", var3);
                  var3 = null;
                  var1.setline(666);
                  var3 = var1.getglobal("True");
                  var1.getlocal(0).__setattr__("passline", var3);
                  var3 = null;
               } else {
                  var1.setline(667);
                  var3 = var1.getlocal(1);
                  var10000 = var3._eq(var1.getglobal("tokenize").__getattr__("DEDENT"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(668);
                     var3 = var1.getlocal(0).__getattr__("indent")._sub(Py.newInteger(1));
                     var1.getlocal(0).__setattr__("indent", var3);
                     var3 = null;
                     var1.setline(672);
                     var3 = var1.getlocal(0).__getattr__("indent");
                     var10000 = var3._le(Py.newInteger(0));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(673);
                        throw Py.makeException(var1.getglobal("EndOfBlock"));
                     }
                  } else {
                     var1.setline(674);
                     var3 = var1.getlocal(0).__getattr__("indent");
                     var10000 = var3._eq(Py.newInteger(0));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(1);
                        var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("tokenize").__getattr__("COMMENT"), var1.getglobal("tokenize").__getattr__("NL")}));
                        var3 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(677);
                        throw Py.makeException(var1.getglobal("EndOfBlock"));
                     }
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getblock$39(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyString.fromInterned("Extract the block of code at the top of the given list of lines.");
      var1.setline(681);
      PyObject var3 = var1.getglobal("BlockFinder").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(683);
         var1.getglobal("tokenize").__getattr__("tokenize").__call__(var2, var1.getglobal("iter").__call__(var2, var1.getlocal(0)).__getattr__("next"), var1.getlocal(1).__getattr__("tokeneater"));
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(new PyTuple(new PyObject[]{var1.getglobal("EndOfBlock"), var1.getglobal("IndentationError")}))) {
            throw var5;
         }

         var1.setline(685);
      }

      var1.setline(686);
      var3 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(1).__getattr__("last"), (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getsourcelines$40(PyFrame var1, ThreadState var2) {
      var1.setline(695);
      PyString.fromInterned("Return a list of source lines and starting line number for an object.\n\n    The argument may be a module, class, method, function, traceback, frame,\n    or code object.  The source code is returned as a list of the lines\n    corresponding to the object and the line number indicates where in the\n    original source file the first line of code was found.  An IOError is\n    raised if the source code cannot be retrieved.");
      var1.setline(696);
      PyObject var3 = var1.getglobal("findsource").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(698);
      PyTuple var6;
      if (var1.getglobal("ismodule").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(698);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(1), Py.newInteger(0)});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(699);
         var6 = new PyTuple(new PyObject[]{var1.getglobal("getblock").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null)), var1.getlocal(2)._add(Py.newInteger(1))});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject getsource$41(PyFrame var1, ThreadState var2) {
      var1.setline(706);
      PyString.fromInterned("Return the text of the source code for an object.\n\n    The argument may be a module, class, method, function, traceback, frame,\n    or code object.  The source code is returned as a single string.  An\n    IOError is raised if the source code cannot be retrieved.");
      var1.setline(707);
      PyObject var3 = var1.getglobal("getsourcelines").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(708);
      var3 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject walktree$42(PyFrame var1, ThreadState var2) {
      var1.setline(712);
      PyString.fromInterned("Recursive helper function for getclasstree().");
      var1.setline(713);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(714);
      PyObject var10000 = var1.getlocal(0).__getattr__("sort");
      PyObject[] var6 = new PyObject[]{var1.getglobal("attrgetter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__module__"), (PyObject)PyString.fromInterned("__name__"))};
      String[] var4 = new String[]{"key"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(715);
      PyObject var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(715);
         PyObject var8 = var7.__iternext__();
         if (var8 == null) {
            var1.setline(719);
            var7 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(4, var8);
         var1.setline(716);
         var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(4).__getattr__("__bases__")})));
         var1.setline(717);
         PyObject var5 = var1.getlocal(4);
         var10000 = var5._in(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(718);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("walktree").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(4)), var1.getlocal(1), var1.getlocal(4)));
         }
      }
   }

   public PyObject getclasstree$43(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      PyString.fromInterned("Arrange the given list of classes into a hierarchy of nested lists.\n\n    Where a nested list appears, it contains classes derived from the class\n    whose entry immediately precedes the list.  Each entry is a 2-tuple\n    containing a class and a tuple of its base classes.  If the 'unique'\n    argument is true, exactly one entry appears in the returned structure\n    for each class in the given list.  Otherwise, classes using multiple\n    inheritance and their descendants will appear multiple times.");
      var1.setline(730);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(731);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(732);
      PyObject var9 = var1.getlocal(0).__iter__();

      while(true) {
         while(true) {
            var1.setline(732);
            PyObject var4 = var9.__iternext__();
            PyObject var10000;
            PyObject var5;
            if (var4 == null) {
               var1.setline(741);
               var9 = var1.getlocal(2).__iter__();

               while(true) {
                  var1.setline(741);
                  var4 = var9.__iternext__();
                  if (var4 == null) {
                     var1.setline(744);
                     var9 = var1.getglobal("walktree").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getglobal("None"));
                     var1.f_lasti = -1;
                     return var9;
                  }

                  var1.setlocal(5, var4);
                  var1.setline(742);
                  var5 = var1.getlocal(5);
                  var10000 = var5._notin(var1.getlocal(0));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(743);
                     var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
                  }
               }
            }

            var1.setlocal(4, var4);
            var1.setline(733);
            if (var1.getlocal(4).__getattr__("__bases__").__nonzero__()) {
               var1.setline(734);
               var5 = var1.getlocal(4).__getattr__("__bases__").__iter__();

               while(true) {
                  var1.setline(734);
                  PyObject var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(5, var6);
                  var1.setline(735);
                  PyObject var7 = var1.getlocal(5);
                  var10000 = var7._in(var1.getlocal(2));
                  var7 = null;
                  if (var10000.__not__().__nonzero__()) {
                     var1.setline(736);
                     PyList var10 = new PyList(Py.EmptyObjects);
                     var1.getlocal(2).__setitem__((PyObject)var1.getlocal(5), var10);
                     var7 = null;
                  }

                  var1.setline(737);
                  var1.getlocal(2).__getitem__(var1.getlocal(5)).__getattr__("append").__call__(var2, var1.getlocal(4));
                  var1.setline(738);
                  var10000 = var1.getlocal(1);
                  if (var10000.__nonzero__()) {
                     var7 = var1.getlocal(5);
                     var10000 = var7._in(var1.getlocal(0));
                     var7 = null;
                  }

                  if (var10000.__nonzero__()) {
                     break;
                  }
               }
            } else {
               var1.setline(739);
               var5 = var1.getlocal(4);
               var10000 = var5._notin(var1.getlocal(3));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(740);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
               }
            }
         }
      }
   }

   public PyObject getargs$44(PyFrame var1, ThreadState var2) {
      var1.setline(754);
      PyString.fromInterned("Get information about the arguments accepted by a code object.\n\n    Three things are returned: (args, varargs, varkw), where 'args' is\n    a list of argument names (possibly containing nested lists), and\n    'varargs' and 'varkw' are the names of the * and ** arguments or None.");
      var1.setline(756);
      if (var1.getglobal("iscode").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(757);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("{!r} is not a code object").__getattr__("format").__call__(var2, var1.getlocal(0))));
      } else {
         var1.setline(759);
         PyObject var3 = var1.getlocal(0).__getattr__("co_argcount");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(760);
         var3 = var1.getlocal(0).__getattr__("co_varnames");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(768);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(769);
         if (var1.getlocal(0).__getattr__("co_flags")._and(var1.getglobal("CO_VARARGS")).__nonzero__()) {
            var1.setline(770);
            var3 = var1.getlocal(0).__getattr__("co_varnames").__getitem__(var1.getlocal(1));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(771);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(772);
         PyObject var10000;
         if (var1.getlocal(0).__getattr__("co_flags")._and(var1.getglobal("CO_VARKEYWORDS")).__nonzero__()) {
            var1.setline(773);
            var10000 = var1.getlocal(0).__getattr__("co_varnames");
            PyObject var10001 = var1.getlocal(1);
            var1.setline(773);
            var3 = var10000.__getitem__(var10001._add(var1.getlocal(3).__nonzero__() ? Py.newInteger(1) : Py.newInteger(0)));
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(781);
         PyList var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var5);
         var3 = null;
         var1.setline(782);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null).__iter__();

         while(true) {
            var1.setline(782);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(787);
               var3 = var1.getglobal("Arguments").__call__(var2, var1.getlocal(5), var1.getlocal(3), var1.getlocal(4));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(6, var4);
            var1.setline(783);
            var10000 = var1.getlocal(6).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("("));
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(6).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(784);
               var1.getlocal(5).__getattr__("append").__call__(var2, var1.getglobal("_parse_anonymous_tuple_arg").__call__(var2, var1.getlocal(6).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null)));
            } else {
               var1.setline(786);
               var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(6));
            }
         }
      }
   }

   public PyObject _parse_anonymous_tuple_arg$45(PyFrame var1, ThreadState var2) {
      var1.setline(791);
      PyObject var3 = var1.getlocal(0).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(", "));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(792);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(793);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(3, var7);
      var3 = null;

      while(true) {
         while(true) {
            label22:
            while(true) {
               var1.setline(794);
               var3 = var1.getlocal(3);
               PyObject var10000 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(811);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(795);
               var3 = var1.getlocal(1).__getitem__(var1.getlocal(3));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(796);
               if (var1.getlocal(4).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(")).__nonzero__()) {
                  var1.setline(797);
                  if (var1.getlocal(4).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")")).__nonzero__()) {
                     var1.setline(798);
                     var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(4).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null)));
                     var1.setline(799);
                     var3 = var1.getlocal(3);
                     var3 = var3._iadd(Py.newInteger(1));
                     var1.setlocal(3, var3);
                  } else {
                     var1.setline(802);
                     var3 = var1.getglobal("xrange").__call__((ThreadState)var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1)), (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(-1)).__iter__();

                     do {
                        var1.setline(802);
                        PyObject var4 = var3.__iternext__();
                        if (var4 == null) {
                           continue label22;
                        }

                        var1.setlocal(5, var4);
                        var1.setline(803);
                     } while(!var1.getlocal(1).__getitem__(var1.getlocal(5)).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")")).__nonzero__());

                     var1.setline(804);
                     PyObject var5 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(3), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null)).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
                     var1.setlocal(6, var5);
                     var5 = null;
                     var1.setline(805);
                     var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("_parse_anonymous_tuple_arg").__call__(var2, var1.getlocal(6)));
                     var1.setline(806);
                     var5 = var1.getlocal(5)._add(Py.newInteger(1));
                     var1.setlocal(3, var5);
                     var5 = null;
                  }
               } else {
                  var1.setline(809);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
                  var1.setline(810);
                  var3 = var1.getlocal(3);
                  var3 = var3._iadd(Py.newInteger(1));
                  var1.setlocal(3, var3);
               }
            }
         }
      }
   }

   public PyObject getargspec$46(PyFrame var1, ThreadState var2) {
      var1.setline(822);
      PyString.fromInterned("Get the names and default values of a function's arguments.\n\n    A tuple of four things is returned: (args, varargs, varkw, defaults).\n    'args' is a list of the argument names (it may contain nested lists).\n    'varargs' and 'varkw' are the names of the * and ** arguments or None.\n    'defaults' is an n-tuple of the default values of the last n arguments.\n    ");
      var1.setline(824);
      PyObject var3;
      if (var1.getglobal("ismethod").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(825);
         var3 = var1.getlocal(0).__getattr__("im_func");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(826);
      if (var1.getglobal("isfunction").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(827);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("{!r} is not a Python function").__getattr__("format").__call__(var2, var1.getlocal(0))));
      } else {
         var1.setline(828);
         var3 = var1.getglobal("getargs").__call__(var2, var1.getlocal(0).__getattr__("func_code"));
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(829);
         var3 = var1.getglobal("ArgSpec").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("func_defaults"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getargvalues$47(PyFrame var1, ThreadState var2) {
      var1.setline(839);
      PyString.fromInterned("Get information about arguments passed into a particular frame.\n\n    A tuple of four things is returned: (args, varargs, varkw, locals).\n    'args' is a list of the argument names (it may contain nested lists).\n    'varargs' and 'varkw' are the names of the * and ** arguments or None.\n    'locals' is the locals dictionary of the given frame.");
      var1.setline(840);
      PyObject var3 = var1.getglobal("getargs").__call__(var2, var1.getlocal(0).__getattr__("f_code"));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(841);
      var3 = var1.getglobal("ArgInfo").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("f_locals"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject joinseq$48(PyFrame var1, ThreadState var2) {
      var1.setline(844);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(845);
         var3 = PyString.fromInterned("(")._add(var1.getlocal(0).__getitem__(Py.newInteger(0)))._add(PyString.fromInterned(",)"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(847);
         var3 = PyString.fromInterned("(")._add(var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned(", ")))._add(PyString.fromInterned(")"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject strseq$49(PyFrame var1, ThreadState var2) {
      var1.setline(850);
      PyString.fromInterned("Recursively walk a sequence, stringifying each element.");
      var1.setline(851);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(852);
         var10000 = var1.getlocal(2);
         PyObject var10002 = var1.getglobal("map");
         var1.setline(852);
         PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
         var3 = var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$50)), (PyObject)var1.getlocal(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(854);
         var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$50(PyFrame var1, ThreadState var2) {
      var1.setline(852);
      PyObject var3 = var1.getglobal("strseq").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$51(PyFrame var1, ThreadState var2) {
      var1.setline(858);
      PyObject var3 = PyString.fromInterned("*")._add(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$52(PyFrame var1, ThreadState var2) {
      var1.setline(859);
      PyObject var3 = PyString.fromInterned("**")._add(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$53(PyFrame var1, ThreadState var2) {
      var1.setline(860);
      PyObject var3 = PyString.fromInterned("=")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatargspec$54(PyFrame var1, ThreadState var2) {
      var1.setline(867);
      PyString.fromInterned("Format an argument spec from the 4 values returned by getargspec.\n\n    The first four arguments are (args, varargs, varkw, defaults).  The\n    other four arguments are the corresponding optional formatting functions\n    that are called to turn names and values into strings.  The ninth\n    argument is an optional function to format the sequence of arguments.");
      var1.setline(868);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(869);
      PyObject var7;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(870);
         var7 = var1.getglobal("len").__call__(var2, var1.getlocal(0))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
         var1.setlocal(10, var7);
         var3 = null;
      }

      var1.setline(871);
      var7 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(871);
         PyObject var4 = var7.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(876);
            var7 = var1.getlocal(1);
            var10000 = var7._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(877);
               var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(5).__call__(var2, var1.getlocal(1)));
            }

            var1.setline(878);
            var7 = var1.getlocal(2);
            var10000 = var7._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(879);
               var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(6).__call__(var2, var1.getlocal(2)));
            }

            var1.setline(880);
            var7 = PyString.fromInterned("(")._add(var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned(", ")))._add(PyString.fromInterned(")"));
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(11, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(12, var6);
         var6 = null;
         var1.setline(872);
         PyObject var8 = var1.getglobal("strseq").__call__(var2, var1.getlocal(12), var1.getlocal(4), var1.getlocal(8));
         var1.setlocal(13, var8);
         var5 = null;
         var1.setline(873);
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var8 = var1.getlocal(11);
            var10000 = var8._ge(var1.getlocal(10));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(874);
            var8 = var1.getlocal(13)._add(var1.getlocal(7).__call__(var2, var1.getlocal(3).__getitem__(var1.getlocal(11)._sub(var1.getlocal(10)))));
            var1.setlocal(13, var8);
            var5 = null;
         }

         var1.setline(875);
         var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(13));
      }
   }

   public PyObject f$55(PyFrame var1, ThreadState var2) {
      var1.setline(884);
      PyObject var3 = PyString.fromInterned("*")._add(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$56(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      PyObject var3 = PyString.fromInterned("**")._add(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$57(PyFrame var1, ThreadState var2) {
      var1.setline(886);
      PyObject var3 = PyString.fromInterned("=")._add(var1.getglobal("repr").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatargvalues$58(PyFrame var1, ThreadState var2) {
      var1.setline(893);
      PyString.fromInterned("Format an argument spec from the 4 values returned by getargvalues.\n\n    The first four arguments are (args, varargs, varkw, locals).  The\n    next four arguments are the corresponding optional formatting functions\n    that are called to turn names and values into strings.  The ninth\n    argument is an optional function to format the sequence of arguments.");
      var1.setline(894);
      PyObject[] var3 = new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(7)};
      PyFunction var5 = new PyFunction(var1.f_globals, var3, convert$59, (PyObject)null);
      var1.setlocal(9, var5);
      var3 = null;
      var1.setline(897);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(10, var6);
      var3 = null;
      var1.setline(898);
      PyObject var7 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))).__iter__();

      while(true) {
         var1.setline(898);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(900);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(901);
               var1.getlocal(10).__getattr__("append").__call__(var2, var1.getlocal(5).__call__(var2, var1.getlocal(1))._add(var1.getlocal(7).__call__(var2, var1.getlocal(3).__getitem__(var1.getlocal(1)))));
            }

            var1.setline(902);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(903);
               var1.getlocal(10).__getattr__("append").__call__(var2, var1.getlocal(6).__call__(var2, var1.getlocal(2))._add(var1.getlocal(7).__call__(var2, var1.getlocal(3).__getitem__(var1.getlocal(2)))));
            }

            var1.setline(904);
            var7 = PyString.fromInterned("(")._add(var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(10), (PyObject)PyString.fromInterned(", ")))._add(PyString.fromInterned(")"));
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(11, var4);
         var1.setline(899);
         var1.getlocal(10).__getattr__("append").__call__(var2, var1.getglobal("strseq").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(11)), var1.getlocal(9), var1.getlocal(8)));
      }
   }

   public PyObject convert$59(PyFrame var1, ThreadState var2) {
      var1.setline(896);
      PyObject var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0))._add(var1.getlocal(3).__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(0))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getcallargs$60(PyFrame var1, ThreadState var2) {
      var1.setline(911);
      PyString.fromInterned("Get the mapping of arguments to values.\n\n    A dict is returned, with keys the function argument names (including the\n    names of the * and ** arguments, if any), and values the respective bound\n    values from 'positional' and 'named'.");
      var1.setline(912);
      PyObject var3 = var1.getglobal("getargspec").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(913);
      var3 = var1.getlocal(0).__getattr__("__name__");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(914);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setderef(0, var7);
      var3 = null;
      var1.setline(917);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setderef(1, var9);
      var3 = null;
      var1.setline(918);
      PyObject[] var10 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var10;
      PyCode var10004 = assign$61;
      var10 = new PyObject[]{var1.getclosure(0), var1.getclosure(1), var1.getclosure(2)};
      PyFunction var11 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var10);
      var1.setderef(2, var11);
      var3 = null;
      var1.setline(937);
      var10 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var10;
      var10004 = is_assigned$62;
      var10 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      var11 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var10);
      var1.setlocal(8, var11);
      var3 = null;
      var1.setline(941);
      PyObject var10000 = var1.getglobal("ismethod").__call__(var2, var1.getlocal(0));
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("im_self");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(943);
         var3 = (new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("im_self")}))._add(var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(944);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(945);
      var3 = var1.getlocal(9)._add(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(946);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(947);
      var1.setline(947);
      Object var13 = var1.getlocal(6).__nonzero__() ? var1.getglobal("len").__call__(var2, var1.getlocal(6)) : Py.newInteger(0);
      var1.setlocal(12, (PyObject)var13);
      var3 = null;
      var1.setline(948);
      var3 = var1.getglobal("zip").__call__(var2, var1.getlocal(3), var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(948);
         PyObject var8 = var3.__iternext__();
         PyObject var6;
         PyObject[] var12;
         if (var8 == null) {
            var1.setline(950);
            PyString var16;
            PyObject var10001;
            PyObject[] var10005;
            PyObject var10008;
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(951);
               var3 = var1.getlocal(9);
               var10000 = var3._gt(var1.getlocal(11));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(952);
                  var1.getderef(2).__call__(var2, var1.getlocal(4), var1.getlocal(1).__getslice__(var1.getlocal(9)._sub(var1.getlocal(11)).__neg__(), (PyObject)null, (PyObject)null));
               } else {
                  var1.setline(954);
                  var1.getderef(2).__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyTuple(Py.EmptyObjects)));
               }
            } else {
               var1.setline(955);
               PyInteger var14 = Py.newInteger(0);
               var10001 = var1.getlocal(11);
               PyInteger var15 = var14;
               var3 = var10001;
               if ((var8 = var15._lt(var10001)).__nonzero__()) {
                  var8 = var3._lt(var1.getlocal(9));
               }

               var3 = null;
               if (var8.__nonzero__()) {
                  var1.setline(956);
                  var10000 = var1.getglobal("TypeError");
                  var16 = PyString.fromInterned("%s() takes %s %d %s (%d given)");
                  var10005 = new PyObject[]{var1.getlocal(7), null, null, null, null};
                  var1.setline(957);
                  var10005[1] = var1.getlocal(6).__nonzero__() ? PyString.fromInterned("at most") : PyString.fromInterned("exactly");
                  var10005[2] = var1.getlocal(11);
                  var1.setline(958);
                  var3 = var1.getlocal(11);
                  var10008 = var3._gt(Py.newInteger(1));
                  var3 = null;
                  var10005[3] = var10008.__nonzero__() ? PyString.fromInterned("arguments") : PyString.fromInterned("argument");
                  var10005[4] = var1.getlocal(10);
                  throw Py.makeException(var10000.__call__(var2, var16._mod(new PyTuple(var10005))));
               }

               var1.setline(959);
               var3 = var1.getlocal(11);
               var10000 = var3._eq(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(10);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(960);
                  if (!var1.getlocal(5).__nonzero__()) {
                     var1.setline(966);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s() takes no arguments (%d given)")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(10)}))));
                  }

                  var1.setline(961);
                  if (var1.getlocal(9).__nonzero__()) {
                     var1.setline(963);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s() takes exactly 0 arguments (%d given)")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(10)}))));
                  }
               }
            }

            var1.setline(968);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(968);
               var8 = var3.__iternext__();
               if (var8 == null) {
                  var1.setline(975);
                  if (var1.getlocal(6).__nonzero__()) {
                     var1.setline(976);
                     var3 = var1.getglobal("zip").__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(12).__neg__(), (PyObject)null, (PyObject)null), var1.getlocal(6)).__iter__();

                     while(true) {
                        var1.setline(976);
                        var8 = var3.__iternext__();
                        if (var8 == null) {
                           break;
                        }

                        var12 = Py.unpackSequence(var8, 2);
                        var6 = var12[0];
                        var1.setlocal(13, var6);
                        var6 = null;
                        var6 = var12[1];
                        var1.setlocal(14, var6);
                        var6 = null;
                        var1.setline(977);
                        if (var1.getlocal(8).__call__(var2, var1.getlocal(13)).__not__().__nonzero__()) {
                           var1.setline(978);
                           var1.getderef(2).__call__(var2, var1.getlocal(13), var1.getlocal(14));
                        }
                     }
                  }

                  var1.setline(979);
                  if (var1.getlocal(5).__nonzero__()) {
                     var1.setline(980);
                     var1.getderef(2).__call__(var2, var1.getlocal(5), var1.getlocal(2));
                  } else {
                     var1.setline(981);
                     if (var1.getlocal(2).__nonzero__()) {
                        var1.setline(982);
                        var3 = var1.getglobal("next").__call__(var2, var1.getglobal("iter").__call__(var2, var1.getlocal(2)));
                        var1.setlocal(15, var3);
                        var3 = null;
                        var1.setline(983);
                        if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(15), var1.getglobal("unicode")).__nonzero__()) {
                           var1.setline(984);
                           var3 = var1.getlocal(15).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("getdefaultencoding").__call__(var2), (PyObject)PyString.fromInterned("replace"));
                           var1.setlocal(15, var3);
                           var3 = null;
                        }

                        var1.setline(985);
                        throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s() got an unexpected keyword argument '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(15)}))));
                     }
                  }

                  var1.setline(987);
                  var10000 = var1.getlocal(11);
                  var10001 = var1.getglobal("len");
                  PyList var17 = new PyList();
                  var3 = var17.__getattr__("append");
                  var1.setlocal(17, var3);
                  var3 = null;
                  var1.setline(987);
                  var3 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(987);
                     var8 = var3.__iternext__();
                     if (var8 == null) {
                        var1.setline(987);
                        var1.dellocal(17);
                        var3 = var10000._sub(var10001.__call__((ThreadState)var2, (PyObject)var17));
                        var1.setlocal(16, var3);
                        var3 = null;
                        var1.setline(988);
                        if (var1.getlocal(16).__nonzero__()) {
                           var1.setline(989);
                           var3 = var1.getlocal(11)._sub(var1.getlocal(12));
                           var1.setlocal(18, var3);
                           var3 = null;
                           var1.setline(990);
                           var10000 = var1.getglobal("TypeError");
                           var16 = PyString.fromInterned("%s() takes %s %d %s (%d given)");
                           var10005 = new PyObject[]{var1.getlocal(7), null, null, null, null};
                           var1.setline(991);
                           var10005[1] = var1.getlocal(6).__nonzero__() ? PyString.fromInterned("at least") : PyString.fromInterned("exactly");
                           var10005[2] = var1.getlocal(18);
                           var1.setline(992);
                           var3 = var1.getlocal(18);
                           var10008 = var3._gt(Py.newInteger(1));
                           var3 = null;
                           var10005[3] = var10008.__nonzero__() ? PyString.fromInterned("arguments") : PyString.fromInterned("argument");
                           var10005[4] = var1.getlocal(10);
                           throw Py.makeException(var10000.__call__(var2, var16._mod(new PyTuple(var10005))));
                        }

                        var1.setline(993);
                        var3 = var1.getderef(0);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setlocal(13, var8);
                     var1.setline(987);
                     if (var1.getlocal(8).__call__(var2, var1.getlocal(13)).__nonzero__()) {
                        var1.setline(987);
                        var1.getlocal(17).__call__(var2, var1.getlocal(13));
                     }
                  }
               }

               var1.setlocal(13, var8);
               var1.setline(969);
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(13), var1.getglobal("str"));
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(13);
                  var10000 = var5._in(var1.getlocal(2));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(970);
                  if (var1.getlocal(8).__call__(var2, var1.getlocal(13)).__nonzero__()) {
                     var1.setline(971);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s() got multiple values for keyword argument '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(13)}))));
                  }

                  var1.setline(974);
                  var1.getderef(2).__call__(var2, var1.getlocal(13), var1.getlocal(2).__getattr__("pop").__call__(var2, var1.getlocal(13)));
               }
            }
         }

         var12 = Py.unpackSequence(var8, 2);
         var6 = var12[0];
         var1.setlocal(13, var6);
         var6 = null;
         var6 = var12[1];
         var1.setlocal(14, var6);
         var6 = null;
         var1.setline(949);
         var1.getderef(2).__call__(var2, var1.getlocal(13), var1.getlocal(14));
      }
   }

   public PyObject assign$61(PyFrame var1, ThreadState var2) {
      var1.setline(919);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__()) {
         var1.setline(920);
         var3 = var1.getlocal(1);
         var1.getderef(0).__setitem__(var1.getlocal(0), var3);
         var3 = null;
      } else {
         var1.setline(922);
         var1.getderef(1).__getattr__("append").__call__(var2, var1.getlocal(0));
         var1.setline(923);
         var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(924);
         var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(0)).__iter__();

         while(true) {
            var1.setline(924);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               try {
                  var1.setline(932);
                  var1.getglobal("next").__call__(var2, var1.getlocal(1));
               } catch (Throwable var8) {
                  PyException var9 = Py.setException(var8, var1);
                  if (var9.match(var1.getglobal("StopIteration"))) {
                     var1.setline(934);
                     break;
                  }

                  throw var9;
               }

               var1.setline(936);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("too many values to unpack")));
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;

            try {
               var1.setline(926);
               PyObject var11 = var1.getglobal("next").__call__(var2, var1.getlocal(1));
               var1.setlocal(4, var11);
               var5 = null;
            } catch (Throwable var7) {
               PyException var10 = Py.setException(var7, var1);
               if (var10.match(var1.getglobal("StopIteration"))) {
                  var1.setline(928);
                  PyObject var10000 = var1.getglobal("ValueError");
                  PyString var10002 = PyString.fromInterned("need more than %d %s to unpack");
                  PyObject[] var10005 = new PyObject[]{var1.getlocal(2), null};
                  var1.setline(929);
                  var6 = var1.getlocal(2);
                  PyObject var10008 = var6._gt(Py.newInteger(1));
                  var6 = null;
                  var10005[1] = var10008.__nonzero__() ? PyString.fromInterned("values") : PyString.fromInterned("value");
                  throw Py.makeException(var10000.__call__(var2, var10002._mod(new PyTuple(var10005))));
               }

               throw var10;
            }

            var1.setline(930);
            var1.getderef(2).__call__(var2, var1.getlocal(3), var1.getlocal(4));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_assigned$62(PyFrame var1, ThreadState var2) {
      var1.setline(938);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__nonzero__()) {
         var1.setline(939);
         var3 = var1.getlocal(0);
         var10000 = var3._in(var1.getderef(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(940);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._in(var1.getderef(1));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getframeinfo$63(PyFrame var1, ThreadState var2) {
      var1.setline(1006);
      PyString.fromInterned("Get information about a frame or traceback object.\n\n    A tuple of five things is returned: the filename, the line number of\n    the current line, the function name, a list of lines of context from\n    the source code, and the index of the current line within that list.\n    The optional second argument specifies the number of lines of context\n    to return, which are centered around the current line.");
      var1.setline(1007);
      PyObject var3;
      if (var1.getglobal("istraceback").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(1008);
         var3 = var1.getlocal(0).__getattr__("tb_lineno");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1009);
         var3 = var1.getlocal(0).__getattr__("tb_frame");
         var1.setlocal(0, var3);
         var3 = null;
      } else {
         var1.setline(1011);
         var3 = var1.getlocal(0).__getattr__("f_lineno");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1012);
      if (var1.getglobal("isframe").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(1013);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("{!r} is not a frame or traceback object").__getattr__("format").__call__(var2, var1.getlocal(0))));
      } else {
         var1.setline(1015);
         PyObject var10000 = var1.getglobal("getsourcefile").__call__(var2, var1.getlocal(0));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("getfile").__call__(var2, var1.getlocal(0));
         }

         var3 = var10000;
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1016);
         var3 = var1.getlocal(1);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            label41: {
               var1.setline(1017);
               var3 = var1.getlocal(2)._sub(Py.newInteger(1))._sub(var1.getlocal(1)._floordiv(Py.newInteger(2)));
               var1.setlocal(4, var3);
               var3 = null;

               PyObject var4;
               try {
                  var1.setline(1019);
                  var3 = var1.getglobal("findsource").__call__(var2, var1.getlocal(0));
                  PyObject[] var7 = Py.unpackSequence(var3, 2);
                  PyObject var5 = var7[0];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var5 = var7[1];
                  var1.setlocal(6, var5);
                  var5 = null;
                  var3 = null;
               } catch (Throwable var6) {
                  PyException var8 = Py.setException(var6, var1);
                  if (var8.match(var1.getglobal("IOError"))) {
                     var1.setline(1021);
                     var4 = var1.getglobal("None");
                     var1.setlocal(5, var4);
                     var1.setlocal(7, var4);
                     break label41;
                  }

                  throw var8;
               }

               var1.setline(1023);
               var4 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(1));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(1024);
               var4 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("min").__call__(var2, var1.getlocal(4), var1.getglobal("len").__call__(var2, var1.getlocal(5))._sub(var1.getlocal(1))));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(1025);
               var4 = var1.getlocal(5).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(var1.getlocal(1)), (PyObject)null);
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(1026);
               var4 = var1.getlocal(2)._sub(Py.newInteger(1))._sub(var1.getlocal(4));
               var1.setlocal(7, var4);
               var4 = null;
            }
         } else {
            var1.setline(1028);
            var3 = var1.getglobal("None");
            var1.setlocal(5, var3);
            var1.setlocal(7, var3);
         }

         var1.setline(1030);
         var10000 = var1.getglobal("Traceback");
         PyObject[] var9 = new PyObject[]{var1.getlocal(3), var1.getlocal(2), var1.getlocal(0).__getattr__("f_code").__getattr__("co_name"), var1.getlocal(5), var1.getlocal(7)};
         var3 = var10000.__call__(var2, var9);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getlineno$64(PyFrame var1, ThreadState var2) {
      var1.setline(1033);
      PyString.fromInterned("Get the line number from a frame object, allowing for optimization.");
      var1.setline(1035);
      PyObject var3 = var1.getlocal(0).__getattr__("f_lineno");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getouterframes$65(PyFrame var1, ThreadState var2) {
      var1.setline(1041);
      PyString.fromInterned("Get a list of records for a frame and all higher (calling) frames.\n\n    Each record contains a frame object, filename, line number, function\n    name, a list of lines of context, and index within the context.");
      var1.setline(1042);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(1043);
         PyObject var4;
         if (!var1.getlocal(0).__nonzero__()) {
            var1.setline(1046);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(1044);
         var1.getlocal(2).__getattr__("append").__call__(var2, (new PyTuple(new PyObject[]{var1.getlocal(0)}))._add(var1.getglobal("getframeinfo").__call__(var2, var1.getlocal(0), var1.getlocal(1))));
         var1.setline(1045);
         var4 = var1.getlocal(0).__getattr__("f_back");
         var1.setlocal(0, var4);
         var3 = null;
      }
   }

   public PyObject getinnerframes$66(PyFrame var1, ThreadState var2) {
      var1.setline(1052);
      PyString.fromInterned("Get a list of records for a traceback's frame and all lower frames.\n\n    Each record contains a frame object, filename, line number, function\n    name, a list of lines of context, and index within the context.");
      var1.setline(1053);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(1054);
         PyObject var4;
         if (!var1.getlocal(0).__nonzero__()) {
            var1.setline(1057);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(1055);
         var1.getlocal(2).__getattr__("append").__call__(var2, (new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tb_frame")}))._add(var1.getglobal("getframeinfo").__call__(var2, var1.getlocal(0), var1.getlocal(1))));
         var1.setline(1056);
         var4 = var1.getlocal(0).__getattr__("tb_next");
         var1.setlocal(0, var4);
         var3 = null;
      }
   }

   public PyObject f$67(PyFrame var1, ThreadState var2) {
      var1.setline(1062);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject stack$68(PyFrame var1, ThreadState var2) {
      var1.setline(1065);
      PyString.fromInterned("Return a list of records for the stack above the caller's frame.");
      var1.setline(1066);
      PyObject var3 = var1.getglobal("getouterframes").__call__(var2, var1.getglobal("sys").__getattr__("_getframe").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject trace$69(PyFrame var1, ThreadState var2) {
      var1.setline(1069);
      PyString.fromInterned("Return a list of records for the stack below the current exception.");
      var1.setline(1070);
      PyObject var3 = var1.getglobal("getinnerframes").__call__(var2, var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public inspect$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"object"};
      ismodule$1 = Py.newCode(1, var2, var1, "ismodule", 54, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isclass$2 = Py.newCode(1, var2, var1, "isclass", 62, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      ismethod$3 = Py.newCode(1, var2, var1, "ismethod", 70, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      ismethoddescriptor$4 = Py.newCode(1, var2, var1, "ismethoddescriptor", 81, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isdatadescriptor$5 = Py.newCode(1, var2, var1, "isdatadescriptor", 101, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      ismemberdescriptor$6 = Py.newCode(1, var2, var1, "ismemberdescriptor", 113, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      ismemberdescriptor$7 = Py.newCode(1, var2, var1, "ismemberdescriptor", 121, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isgetsetdescriptor$8 = Py.newCode(1, var2, var1, "isgetsetdescriptor", 130, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isgetsetdescriptor$9 = Py.newCode(1, var2, var1, "isgetsetdescriptor", 138, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isfunction$10 = Py.newCode(1, var2, var1, "isfunction", 145, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isgeneratorfunction$11 = Py.newCode(1, var2, var1, "isgeneratorfunction", 158, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isgenerator$12 = Py.newCode(1, var2, var1, "isgenerator", 167, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      istraceback$13 = Py.newCode(1, var2, var1, "istraceback", 184, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isframe$14 = Py.newCode(1, var2, var1, "isframe", 194, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      iscode$15 = Py.newCode(1, var2, var1, "iscode", 212, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isbuiltin$16 = Py.newCode(1, var2, var1, "isbuiltin", 230, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isroutine$17 = Py.newCode(1, var2, var1, "isroutine", 239, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      isabstract$18 = Py.newCode(1, var2, var1, "isabstract", 247, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "predicate", "results", "key", "value"};
      getmembers$19 = Py.newCode(2, var2, var1, "getmembers", 251, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "mro", "names", "result", "name", "homecls", "base", "obj", "kind", "obj_via_getattr"};
      classify_class_attrs$20 = Py.newCode(1, var2, var1, "classify_class_attrs", 267, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "accum", "base"};
      _searchbases$21 = Py.newCode(2, var2, var1, "_searchbases", 336, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "result"};
      getmro$22 = Py.newCode(1, var2, var1, "getmro", 344, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"line", "expline"};
      indentsize$23 = Py.newCode(1, var2, var1, "indentsize", 354, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "doc"};
      getdoc$24 = Py.newCode(1, var2, var1, "getdoc", 359, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"doc", "lines", "margin", "line", "content", "indent", "i"};
      cleandoc$25 = Py.newCode(1, var2, var1, "cleandoc", 373, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object"};
      getfile$26 = Py.newCode(1, var2, var1, "getfile", 402, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "filename", "suffixes", "neglen", "suffix", "mode", "mtype"};
      getmoduleinfo$27 = Py.newCode(1, var2, var1, "getmoduleinfo", 428, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"info"};
      f$28 = Py.newCode(1, var2, var1, "<lambda>", 431, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "info"};
      getmodulename$29 = Py.newCode(1, var2, var1, "getmodulename", 439, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "filename", "suffix", "mode", "kind"};
      getsourcefile$30 = Py.newCode(1, var2, var1, "getsourcefile", 444, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "_filename"};
      getabsfile$31 = Py.newCode(2, var2, var1, "getabsfile", 466, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "_filename", "file", "modname", "module", "f", "main", "mainobject", "builtin", "builtinobject"};
      getmodule$32 = Py.newCode(2, var2, var1, "getmodule", 478, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "file", "sourcefile", "module", "lines", "name", "pat", "candidates", "i", "match", "lnum"};
      findsource$33 = Py.newCode(1, var2, var1, "findsource", 524, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "lines", "lnum", "start", "comments", "end", "indent", "comment"};
      getcomments$34 = Py.newCode(1, var2, var1, "getcomments", 591, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      EndOfBlock$35 = Py.newCode(0, var2, var1, "EndOfBlock", 636, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BlockFinder$36 = Py.newCode(0, var2, var1, "BlockFinder", 638, false, false, self, 36, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$37 = Py.newCode(1, var2, var1, "__init__", 640, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "token", "srow_scol", "erow_ecol", "line", "srow", "scol", "erow", "ecol"};
      tokeneater$38 = Py.newCode(6, var2, var1, "tokeneater", 647, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"lines", "blockfinder"};
      getblock$39 = Py.newCode(1, var2, var1, "getblock", 679, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "lines", "lnum"};
      getsourcelines$40 = Py.newCode(1, var2, var1, "getsourcelines", 688, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "lines", "lnum"};
      getsource$41 = Py.newCode(1, var2, var1, "getsource", 701, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"classes", "children", "parent", "results", "c"};
      walktree$42 = Py.newCode(3, var2, var1, "walktree", 711, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"classes", "unique", "children", "roots", "c", "parent"};
      getclasstree$43 = Py.newCode(2, var2, var1, "getclasstree", 721, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"co", "nargs", "names", "varargs", "varkw", "args", "name"};
      getargs$44 = Py.newCode(1, var2, var1, "getargs", 749, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tuple_arg", "names", "args", "i", "name", "j", "joined"};
      _parse_anonymous_tuple_arg$45 = Py.newCode(1, var2, var1, "_parse_anonymous_tuple_arg", 789, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "args", "varargs", "varkw"};
      getargspec$46 = Py.newCode(1, var2, var1, "getargspec", 815, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"frame", "args", "varargs", "varkw"};
      getargvalues$47 = Py.newCode(1, var2, var1, "getargvalues", 833, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"seq"};
      joinseq$48 = Py.newCode(1, var2, var1, "joinseq", 843, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"object", "convert", "join"};
      strseq$49 = Py.newCode(3, var2, var1, "strseq", 849, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"o", "c", "j"};
      f$50 = Py.newCode(3, var2, var1, "<lambda>", 852, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      f$51 = Py.newCode(1, var2, var1, "<lambda>", 858, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      f$52 = Py.newCode(1, var2, var1, "<lambda>", 859, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value"};
      f$53 = Py.newCode(1, var2, var1, "<lambda>", 860, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "varargs", "varkw", "defaults", "formatarg", "formatvarargs", "formatvarkw", "formatvalue", "join", "specs", "firstdefault", "i", "arg", "spec"};
      formatargspec$54 = Py.newCode(9, var2, var1, "formatargspec", 856, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      f$55 = Py.newCode(1, var2, var1, "<lambda>", 884, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      f$56 = Py.newCode(1, var2, var1, "<lambda>", 885, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value"};
      f$57 = Py.newCode(1, var2, var1, "<lambda>", 886, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "varargs", "varkw", "locals", "formatarg", "formatvarargs", "formatvarkw", "formatvalue", "join", "convert", "specs", "i"};
      formatargvalues$58 = Py.newCode(9, var2, var1, "formatargvalues", 882, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "locals", "formatarg", "formatvalue"};
      convert$59 = Py.newCode(4, var2, var1, "convert", 894, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "positional", "named", "args", "varargs", "varkw", "defaults", "f_name", "is_assigned", "num_pos", "num_total", "num_args", "num_defaults", "arg", "value", "unexpected", "unassigned", "_[987_33]", "num_required", "arg2value", "assigned_tuple_params", "assign"};
      String[] var10001 = var2;
      inspect$py var10007 = self;
      var2 = new String[]{"arg2value", "assigned_tuple_params", "assign"};
      getcallargs$60 = Py.newCode(3, var10001, var1, "getcallargs", 906, true, true, var10007, 60, var2, (String[])null, 3, 4097);
      var2 = new String[]{"arg", "value", "i", "subarg", "subvalue"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"arg2value", "assigned_tuple_params", "assign"};
      assign$61 = Py.newCode(2, var10001, var1, "assign", 918, false, false, var10007, 61, (String[])null, var2, 0, 4097);
      var2 = new String[]{"arg"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"arg2value", "assigned_tuple_params"};
      is_assigned$62 = Py.newCode(1, var10001, var1, "is_assigned", 937, false, false, var10007, 62, (String[])null, var2, 0, 4097);
      var2 = new String[]{"frame", "context", "lineno", "filename", "start", "lines", "lnum", "index"};
      getframeinfo$63 = Py.newCode(2, var2, var1, "getframeinfo", 999, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"frame"};
      getlineno$64 = Py.newCode(1, var2, var1, "getlineno", 1032, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"frame", "context", "framelist"};
      getouterframes$65 = Py.newCode(2, var2, var1, "getouterframes", 1037, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tb", "context", "framelist"};
      getinnerframes$66 = Py.newCode(2, var2, var1, "getinnerframes", 1048, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_"};
      f$67 = Py.newCode(1, var2, var1, "<lambda>", 1062, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"context"};
      stack$68 = Py.newCode(1, var2, var1, "stack", 1064, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"context"};
      trace$69 = Py.newCode(1, var2, var1, "trace", 1068, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new inspect$py("inspect$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(inspect$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ismodule$1(var2, var3);
         case 2:
            return this.isclass$2(var2, var3);
         case 3:
            return this.ismethod$3(var2, var3);
         case 4:
            return this.ismethoddescriptor$4(var2, var3);
         case 5:
            return this.isdatadescriptor$5(var2, var3);
         case 6:
            return this.ismemberdescriptor$6(var2, var3);
         case 7:
            return this.ismemberdescriptor$7(var2, var3);
         case 8:
            return this.isgetsetdescriptor$8(var2, var3);
         case 9:
            return this.isgetsetdescriptor$9(var2, var3);
         case 10:
            return this.isfunction$10(var2, var3);
         case 11:
            return this.isgeneratorfunction$11(var2, var3);
         case 12:
            return this.isgenerator$12(var2, var3);
         case 13:
            return this.istraceback$13(var2, var3);
         case 14:
            return this.isframe$14(var2, var3);
         case 15:
            return this.iscode$15(var2, var3);
         case 16:
            return this.isbuiltin$16(var2, var3);
         case 17:
            return this.isroutine$17(var2, var3);
         case 18:
            return this.isabstract$18(var2, var3);
         case 19:
            return this.getmembers$19(var2, var3);
         case 20:
            return this.classify_class_attrs$20(var2, var3);
         case 21:
            return this._searchbases$21(var2, var3);
         case 22:
            return this.getmro$22(var2, var3);
         case 23:
            return this.indentsize$23(var2, var3);
         case 24:
            return this.getdoc$24(var2, var3);
         case 25:
            return this.cleandoc$25(var2, var3);
         case 26:
            return this.getfile$26(var2, var3);
         case 27:
            return this.getmoduleinfo$27(var2, var3);
         case 28:
            return this.f$28(var2, var3);
         case 29:
            return this.getmodulename$29(var2, var3);
         case 30:
            return this.getsourcefile$30(var2, var3);
         case 31:
            return this.getabsfile$31(var2, var3);
         case 32:
            return this.getmodule$32(var2, var3);
         case 33:
            return this.findsource$33(var2, var3);
         case 34:
            return this.getcomments$34(var2, var3);
         case 35:
            return this.EndOfBlock$35(var2, var3);
         case 36:
            return this.BlockFinder$36(var2, var3);
         case 37:
            return this.__init__$37(var2, var3);
         case 38:
            return this.tokeneater$38(var2, var3);
         case 39:
            return this.getblock$39(var2, var3);
         case 40:
            return this.getsourcelines$40(var2, var3);
         case 41:
            return this.getsource$41(var2, var3);
         case 42:
            return this.walktree$42(var2, var3);
         case 43:
            return this.getclasstree$43(var2, var3);
         case 44:
            return this.getargs$44(var2, var3);
         case 45:
            return this._parse_anonymous_tuple_arg$45(var2, var3);
         case 46:
            return this.getargspec$46(var2, var3);
         case 47:
            return this.getargvalues$47(var2, var3);
         case 48:
            return this.joinseq$48(var2, var3);
         case 49:
            return this.strseq$49(var2, var3);
         case 50:
            return this.f$50(var2, var3);
         case 51:
            return this.f$51(var2, var3);
         case 52:
            return this.f$52(var2, var3);
         case 53:
            return this.f$53(var2, var3);
         case 54:
            return this.formatargspec$54(var2, var3);
         case 55:
            return this.f$55(var2, var3);
         case 56:
            return this.f$56(var2, var3);
         case 57:
            return this.f$57(var2, var3);
         case 58:
            return this.formatargvalues$58(var2, var3);
         case 59:
            return this.convert$59(var2, var3);
         case 60:
            return this.getcallargs$60(var2, var3);
         case 61:
            return this.assign$61(var2, var3);
         case 62:
            return this.is_assigned$62(var2, var3);
         case 63:
            return this.getframeinfo$63(var2, var3);
         case 64:
            return this.getlineno$64(var2, var3);
         case 65:
            return this.getouterframes$65(var2, var3);
         case 66:
            return this.getinnerframes$66(var2, var3);
         case 67:
            return this.f$67(var2, var3);
         case 68:
            return this.stack$68(var2, var3);
         case 69:
            return this.trace$69(var2, var3);
         default:
            return null;
      }
   }
}
