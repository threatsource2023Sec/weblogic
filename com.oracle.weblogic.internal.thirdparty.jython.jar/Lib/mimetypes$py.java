import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
@MTime(1498849383000L)
@Filename("mimetypes.py")
public class mimetypes$py extends PyFunctionTable implements PyRunnable {
   static mimetypes$py self;
   static final PyCode f$0;
   static final PyCode MimeTypes$1;
   static final PyCode __init__$2;
   static final PyCode add_type$3;
   static final PyCode guess_type$4;
   static final PyCode guess_all_extensions$5;
   static final PyCode guess_extension$6;
   static final PyCode read$7;
   static final PyCode readfp$8;
   static final PyCode read_windows_registry$9;
   static final PyCode enum_types$10;
   static final PyCode guess_type$11;
   static final PyCode guess_all_extensions$12;
   static final PyCode guess_extension$13;
   static final PyCode add_type$14;
   static final PyCode init$15;
   static final PyCode read_mime_types$16;
   static final PyCode _default_mime_types$17;
   static final PyCode usage$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Guess the MIME type of a file.\n\nThis module defines two useful functions:\n\nguess_type(url, strict=1) -- guess the MIME type and encoding of a URL.\n\nguess_extension(type, strict=1) -- guess the extension for a given MIME type.\n\nIt also contains the following, for tuning the behavior:\n\nData:\n\nknownfiles -- list of files to parse\ninited -- flag set when init() has been called\nsuffix_map -- dictionary mapping suffixes to suffixes\nencodings_map -- dictionary mapping suffixes to encodings\ntypes_map -- dictionary mapping suffixes to types\n\nFunctions:\n\ninit([files]) -- parse a list of files, default knownfiles (on Windows, the\n  default values are taken from the registry)\nread_mime_types(file) -- parse one file, return a dictionary or None\n"));
      var1.setline(24);
      PyString.fromInterned("Guess the MIME type of a file.\n\nThis module defines two useful functions:\n\nguess_type(url, strict=1) -- guess the MIME type and encoding of a URL.\n\nguess_extension(type, strict=1) -- guess the extension for a given MIME type.\n\nIt also contains the following, for tuning the behavior:\n\nData:\n\nknownfiles -- list of files to parse\ninited -- flag set when init() has been called\nsuffix_map -- dictionary mapping suffixes to suffixes\nencodings_map -- dictionary mapping suffixes to encodings\ntypes_map -- dictionary mapping suffixes to types\n\nFunctions:\n\ninit([files]) -- parse a list of files, default knownfiles (on Windows, the\n  default values are taken from the registry)\nread_mime_types(file) -- parse one file, return a dictionary or None\n");
      var1.setline(26);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(27);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(28);
      var3 = imp.importOne("posixpath", var1, -1);
      var1.setlocal("posixpath", var3);
      var3 = null;
      var1.setline(29);
      var3 = imp.importOne("urllib", var1, -1);
      var1.setlocal("urllib", var3);
      var3 = null;

      PyObject var4;
      PyException var10;
      try {
         var1.setline(31);
         var3 = imp.importOne("_winreg", var1, -1);
         var1.setlocal("_winreg", var3);
         var3 = null;
      } catch (Throwable var9) {
         var10 = Py.setException(var9, var1);
         if (!var10.match(var1.getname("ImportError"))) {
            throw var10;
         }

         var1.setline(33);
         var4 = var1.getname("None");
         var1.setlocal("_winreg", var4);
         var4 = null;
      }

      var1.setline(35);
      PyList var13 = new PyList(new PyObject[]{PyString.fromInterned("guess_type"), PyString.fromInterned("guess_extension"), PyString.fromInterned("guess_all_extensions"), PyString.fromInterned("add_type"), PyString.fromInterned("read_mime_types"), PyString.fromInterned("init")});
      var1.setlocal("__all__", var13);
      var3 = null;
      var1.setline(40);
      var13 = new PyList(new PyObject[]{PyString.fromInterned("/etc/mime.types"), PyString.fromInterned("/etc/httpd/mime.types"), PyString.fromInterned("/etc/httpd/conf/mime.types"), PyString.fromInterned("/etc/apache/mime.types"), PyString.fromInterned("/etc/apache2/mime.types"), PyString.fromInterned("/usr/local/etc/httpd/conf/mime.types"), PyString.fromInterned("/usr/local/lib/netscape/mime.types"), PyString.fromInterned("/usr/local/etc/httpd/conf/mime.types"), PyString.fromInterned("/usr/local/etc/mime.types")});
      var1.setlocal("knownfiles", var13);
      var3 = null;
      var1.setline(52);
      var3 = var1.getname("False");
      var1.setlocal("inited", var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getname("None");
      var1.setlocal("_db", var3);
      var3 = null;
      var1.setline(56);
      PyObject[] var16 = Py.EmptyObjects;
      var4 = Py.makeClass("MimeTypes", var16, MimeTypes$1);
      var1.setlocal("MimeTypes", var4);
      var4 = null;
      Arrays.fill(var16, (Object)null);
      var1.setline(275);
      var16 = new PyObject[]{var1.getname("True")};
      PyFunction var17 = new PyFunction(var1.f_globals, var16, guess_type$11, PyString.fromInterned("Guess the type of a file based on its URL.\n\n    Return value is a tuple (type, encoding) where type is None if the\n    type can't be guessed (no or unknown suffix) or a string of the\n    form type/subtype, usable for a MIME Content-type header; and\n    encoding is None for no encoding or the name of the program used\n    to encode (e.g. compress or gzip).  The mappings are table\n    driven.  Encoding suffixes are case sensitive; type suffixes are\n    first tried case sensitive, then case insensitive.\n\n    The suffixes .tgz, .taz and .tz (case sensitive!) are all mapped\n    to \".tar.gz\".  (This is table-driven too, using the dictionary\n    suffix_map).\n\n    Optional `strict' argument when false adds a bunch of commonly found, but\n    non-standard types.\n    "));
      var1.setlocal("guess_type", var17);
      var3 = null;
      var1.setline(298);
      var16 = new PyObject[]{var1.getname("True")};
      var17 = new PyFunction(var1.f_globals, var16, guess_all_extensions$12, PyString.fromInterned("Guess the extensions for a file based on its MIME type.\n\n    Return value is a list of strings giving the possible filename\n    extensions, including the leading dot ('.').  The extension is not\n    guaranteed to have been associated with any particular data\n    stream, but would be mapped to the MIME type `type' by\n    guess_type().  If no extension can be guessed for `type', None\n    is returned.\n\n    Optional `strict' argument when false adds a bunch of commonly found,\n    but non-standard types.\n    "));
      var1.setlocal("guess_all_extensions", var17);
      var3 = null;
      var1.setline(315);
      var16 = new PyObject[]{var1.getname("True")};
      var17 = new PyFunction(var1.f_globals, var16, guess_extension$13, PyString.fromInterned("Guess the extension for a file based on its MIME type.\n\n    Return value is a string giving a filename extension, including the\n    leading dot ('.').  The extension is not guaranteed to have been\n    associated with any particular data stream, but would be mapped to the\n    MIME type `type' by guess_type().  If no extension can be guessed for\n    `type', None is returned.\n\n    Optional `strict' argument when false adds a bunch of commonly found,\n    but non-standard types.\n    "));
      var1.setlocal("guess_extension", var17);
      var3 = null;
      var1.setline(331);
      var16 = new PyObject[]{var1.getname("True")};
      var17 = new PyFunction(var1.f_globals, var16, add_type$14, PyString.fromInterned("Add a mapping between a type and an extension.\n\n    When the extension is already known, the new\n    type will replace the old one. When the type\n    is already known the extension will be added\n    to the list of known extensions.\n\n    If strict is true, information will be added to\n    list of standard types, else to the list of non-standard\n    types.\n    "));
      var1.setlocal("add_type", var17);
      var3 = null;
      var1.setline(348);
      var16 = new PyObject[]{var1.getname("None")};
      var17 = new PyFunction(var1.f_globals, var16, init$15, (PyObject)null);
      var1.setlocal("init", var17);
      var3 = null;
      var1.setline(368);
      var16 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var16, read_mime_types$16, (PyObject)null);
      var1.setlocal("read_mime_types", var17);
      var3 = null;
      var1.setline(378);
      var16 = Py.EmptyObjects;
      var17 = new PyFunction(var1.f_globals, var16, _default_mime_types$17, (PyObject)null);
      var1.setlocal("_default_mime_types", var17);
      var3 = null;
      var1.setline(546);
      var1.getname("_default_mime_types").__call__(var2);
      var1.setline(549);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(550);
         var3 = imp.importOne("getopt", var1, -1);
         var1.setlocal("getopt", var3);
         var3 = null;
         var1.setline(552);
         PyString var18 = PyString.fromInterned("Usage: mimetypes.py [options] type\n\nOptions:\n    --help / -h       -- print this message and exit\n    --lenient / -l    -- additionally search of some common, but non-standard\n                         types.\n    --extension / -e  -- guess extension instead of type\n\nMore than one type argument may be given.\n");
         var1.setlocal("USAGE", var18);
         var3 = null;
         var1.setline(564);
         var16 = new PyObject[]{PyString.fromInterned("")};
         var17 = new PyFunction(var1.f_globals, var16, usage$18, (PyObject)null);
         var1.setlocal("usage", var17);
         var3 = null;

         PyObject var5;
         try {
            var1.setline(570);
            var3 = var1.getname("getopt").__getattr__("getopt").__call__((ThreadState)var2, var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("hle"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("help"), PyString.fromInterned("lenient"), PyString.fromInterned("extension")})));
            PyObject[] var11 = Py.unpackSequence(var3, 2);
            var5 = var11[0];
            var1.setlocal("opts", var5);
            var5 = null;
            var5 = var11[1];
            var1.setlocal("args", var5);
            var5 = null;
            var3 = null;
         } catch (Throwable var8) {
            var10 = Py.setException(var8, var1);
            if (!var10.match(var1.getname("getopt").__getattr__("error"))) {
               throw var10;
            }

            var4 = var10.value;
            var1.setlocal("msg", var4);
            var4 = null;
            var1.setline(573);
            var1.getname("usage").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getname("msg"));
         }

         var1.setline(575);
         PyInteger var19 = Py.newInteger(1);
         var1.setlocal("strict", var19);
         var3 = null;
         var1.setline(576);
         var19 = Py.newInteger(0);
         var1.setlocal("extension", var19);
         var3 = null;
         var1.setline(577);
         var3 = var1.getname("opts").__iter__();

         label54:
         while(true) {
            var1.setline(577);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(584);
               var3 = var1.getname("args").__iter__();

               while(true) {
                  var1.setline(584);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break label54;
                  }

                  var1.setlocal("gtype", var4);
                  var1.setline(585);
                  if (var1.getname("extension").__nonzero__()) {
                     var1.setline(586);
                     var5 = var1.getname("guess_extension").__call__(var2, var1.getname("gtype"), var1.getname("strict"));
                     var1.setlocal("guess", var5);
                     var5 = null;
                     var1.setline(587);
                     if (var1.getname("guess").__not__().__nonzero__()) {
                        var1.setline(587);
                        Py.printComma(PyString.fromInterned("I don't know anything about type"));
                        Py.println(var1.getname("gtype"));
                     } else {
                        var1.setline(588);
                        Py.println(var1.getname("guess"));
                     }
                  } else {
                     var1.setline(590);
                     var5 = var1.getname("guess_type").__call__(var2, var1.getname("gtype"), var1.getname("strict"));
                     PyObject[] var14 = Py.unpackSequence(var5, 2);
                     PyObject var7 = var14[0];
                     var1.setlocal("guess", var7);
                     var7 = null;
                     var7 = var14[1];
                     var1.setlocal("encoding", var7);
                     var7 = null;
                     var5 = null;
                     var1.setline(591);
                     if (var1.getname("guess").__not__().__nonzero__()) {
                        var1.setline(591);
                        Py.printComma(PyString.fromInterned("I don't know anything about type"));
                        Py.println(var1.getname("gtype"));
                     } else {
                        var1.setline(592);
                        Py.printComma(PyString.fromInterned("type:"));
                        Py.printComma(var1.getname("guess"));
                        Py.printComma(PyString.fromInterned("encoding:"));
                        Py.println(var1.getname("encoding"));
                     }
                  }
               }
            }

            PyObject[] var12 = Py.unpackSequence(var4, 2);
            PyObject var6 = var12[0];
            var1.setlocal("opt", var6);
            var6 = null;
            var6 = var12[1];
            var1.setlocal("arg", var6);
            var6 = null;
            var1.setline(578);
            var5 = var1.getname("opt");
            var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-h"), PyString.fromInterned("--help")}));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(579);
               var1.getname("usage").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            } else {
               var1.setline(580);
               var5 = var1.getname("opt");
               var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-l"), PyString.fromInterned("--lenient")}));
               var5 = null;
               PyInteger var15;
               if (var10000.__nonzero__()) {
                  var1.setline(581);
                  var15 = Py.newInteger(0);
                  var1.setlocal("strict", var15);
                  var5 = null;
               } else {
                  var1.setline(582);
                  var5 = var1.getname("opt");
                  var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-e"), PyString.fromInterned("--extension")}));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(583);
                     var15 = Py.newInteger(1);
                     var1.setlocal("extension", var15);
                     var5 = null;
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MimeTypes$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("MIME-types datastore.\n\n    This datastore can handle information from mime.types-style files\n    and supports basic determination of MIME type from a filename or\n    URL, and can guess a reasonable extension given a MIME type.\n    "));
      var1.setline(62);
      PyString.fromInterned("MIME-types datastore.\n\n    This datastore can handle information from mime.types-style files\n    and supports basic determination of MIME type from a filename or\n    URL, and can guess a reasonable extension given a MIME type.\n    ");
      var1.setline(64);
      PyObject[] var3 = new PyObject[]{new PyTuple(Py.EmptyObjects), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(78);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, add_type$3, PyString.fromInterned("Add a mapping between a type and an extension.\n\n        When the extension is already known, the new\n        type will replace the old one. When the type\n        is already known the extension will be added\n        to the list of known extensions.\n\n        If strict is true, information will be added to\n        list of standard types, else to the list of non-standard\n        types.\n        "));
      var1.setlocal("add_type", var4);
      var3 = null;
      var1.setline(95);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, guess_type$4, PyString.fromInterned("Guess the type of a file based on its URL.\n\n        Return value is a tuple (type, encoding) where type is None if\n        the type can't be guessed (no or unknown suffix) or a string\n        of the form type/subtype, usable for a MIME Content-type\n        header; and encoding is None for no encoding or the name of\n        the program used to encode (e.g. compress or gzip).  The\n        mappings are table driven.  Encoding suffixes are case\n        sensitive; type suffixes are first tried case sensitive, then\n        case insensitive.\n\n        The suffixes .tgz, .taz and .tz (case sensitive!) are all\n        mapped to '.tar.gz'.  (This is table-driven too, using the\n        dictionary suffix_map.)\n\n        Optional `strict' argument when False adds a bunch of commonly found,\n        but non-standard types.\n        "));
      var1.setlocal("guess_type", var4);
      var3 = null;
      var1.setline(157);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, guess_all_extensions$5, PyString.fromInterned("Guess the extensions for a file based on its MIME type.\n\n        Return value is a list of strings giving the possible filename\n        extensions, including the leading dot ('.').  The extension is not\n        guaranteed to have been associated with any particular data stream,\n        but would be mapped to the MIME type `type' by guess_type().\n\n        Optional `strict' argument when false adds a bunch of commonly found,\n        but non-standard types.\n        "));
      var1.setlocal("guess_all_extensions", var4);
      var3 = null;
      var1.setline(176);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, guess_extension$6, PyString.fromInterned("Guess the extension for a file based on its MIME type.\n\n        Return value is a string giving a filename extension,\n        including the leading dot ('.').  The extension is not\n        guaranteed to have been associated with any particular data\n        stream, but would be mapped to the MIME type `type' by\n        guess_type().  If no extension can be guessed for `type', None\n        is returned.\n\n        Optional `strict' argument when false adds a bunch of commonly found,\n        but non-standard types.\n        "));
      var1.setlocal("guess_extension", var4);
      var3 = null;
      var1.setline(194);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, read$7, PyString.fromInterned("\n        Read a single mime.types-format file, specified by pathname.\n\n        If strict is true, information will be added to\n        list of standard types, else to the list of non-standard\n        types.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(205);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, readfp$8, PyString.fromInterned("\n        Read a single mime.types-format file.\n\n        If strict is true, information will be added to\n        list of standard types, else to the list of non-standard\n        types.\n        "));
      var1.setlocal("readfp", var4);
      var3 = null;
      var1.setline(228);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, read_windows_registry$9, PyString.fromInterned("\n        Load the MIME types database from Windows registry.\n\n        If strict is true, information will be added to\n        list of standard types, else to the list of non-standard\n        types.\n        "));
      var1.setlocal("read_windows_registry", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      if (var1.getglobal("inited").__not__().__nonzero__()) {
         var1.setline(66);
         var1.getglobal("init").__call__(var2);
      }

      var1.setline(67);
      PyObject var3 = var1.getglobal("encodings_map").__getattr__("copy").__call__(var2);
      var1.getlocal(0).__setattr__("encodings_map", var3);
      var3 = null;
      var1.setline(68);
      var3 = var1.getglobal("suffix_map").__getattr__("copy").__call__(var2);
      var1.getlocal(0).__setattr__("suffix_map", var3);
      var3 = null;
      var1.setline(69);
      PyTuple var7 = new PyTuple(new PyObject[]{new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)});
      var1.getlocal(0).__setattr__((String)"types_map", var7);
      var3 = null;
      var1.setline(70);
      var7 = new PyTuple(new PyObject[]{new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)});
      var1.getlocal(0).__setattr__((String)"types_map_inv", var7);
      var3 = null;
      var1.setline(71);
      var3 = var1.getglobal("types_map").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(71);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var6;
         if (var4 == null) {
            var1.setline(73);
            var3 = var1.getglobal("common_types").__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(73);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(75);
                  var3 = var1.getlocal(1).__iter__();

                  while(true) {
                     var1.setline(75);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(5, var4);
                     var1.setline(76);
                     var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(5), var1.getlocal(2));
                  }
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(74);
               var1.getlocal(0).__getattr__("add_type").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getglobal("False"));
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(72);
         var1.getlocal(0).__getattr__("add_type").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getglobal("True"));
      }
   }

   public PyObject add_type$3(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyString.fromInterned("Add a mapping between a type and an extension.\n\n        When the extension is already known, the new\n        type will replace the old one. When the type\n        is already known the extension will be added\n        to the list of known extensions.\n\n        If strict is true, information will be added to\n        list of standard types, else to the list of non-standard\n        types.\n        ");
      var1.setline(90);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__getattr__("types_map").__getitem__(var1.getlocal(3)).__setitem__(var1.getlocal(2), var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getlocal(0).__getattr__("types_map_inv").__getitem__(var1.getlocal(3)).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(92);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._notin(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(93);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject guess_type$4(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyString.fromInterned("Guess the type of a file based on its URL.\n\n        Return value is a tuple (type, encoding) where type is None if\n        the type can't be guessed (no or unknown suffix) or a string\n        of the form type/subtype, usable for a MIME Content-type\n        header; and encoding is None for no encoding or the name of\n        the program used to encode (e.g. compress or gzip).  The\n        mappings are table driven.  Encoding suffixes are case\n        sensitive; type suffixes are first tried case sensitive, then\n        case insensitive.\n\n        The suffixes .tgz, .taz and .tz (case sensitive!) are all\n        mapped to '.tar.gz'.  (This is table-driven too, using the\n        dictionary suffix_map.)\n\n        Optional `strict' argument when False adds a bunch of commonly found,\n        but non-standard types.\n        ");
      var1.setline(114);
      PyObject var3 = var1.getglobal("urllib").__getattr__("splittype").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(115);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(PyString.fromInterned("data"));
      var3 = null;
      PyObject var7;
      PyTuple var8;
      if (var10000.__nonzero__()) {
         var1.setline(122);
         var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(123);
         var3 = var1.getlocal(4);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(125);
            var8 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var8;
         } else {
            var1.setline(126);
            var7 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, PyString.fromInterned(";"), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(4));
            var1.setlocal(5, var7);
            var4 = null;
            var1.setline(127);
            var7 = var1.getlocal(5);
            var10000 = var7._ge(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(128);
               var7 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null);
               var1.setlocal(6, var7);
               var4 = null;
            } else {
               var1.setline(130);
               var7 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
               var1.setlocal(6, var7);
               var4 = null;
            }

            var1.setline(131);
            PyString var10 = PyString.fromInterned("=");
            var10000 = var10._in(var1.getlocal(6));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var10 = PyString.fromInterned("/");
               var10000 = var10._notin(var1.getlocal(6));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(132);
               var10 = PyString.fromInterned("text/plain");
               var1.setlocal(6, var10);
               var4 = null;
            }

            var1.setline(133);
            var8 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var8;
         }
      } else {
         var1.setline(134);
         var7 = var1.getglobal("posixpath").__getattr__("splitext").__call__(var2, var1.getlocal(1));
         PyObject[] var9 = Py.unpackSequence(var7, 2);
         PyObject var6 = var9[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var9[1];
         var1.setlocal(8, var6);
         var6 = null;
         var4 = null;

         while(true) {
            var1.setline(135);
            var7 = var1.getlocal(8);
            var10000 = var7._in(var1.getlocal(0).__getattr__("suffix_map"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(137);
               var7 = var1.getlocal(8);
               var10000 = var7._in(var1.getlocal(0).__getattr__("encodings_map"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(138);
                  var7 = var1.getlocal(0).__getattr__("encodings_map").__getitem__(var1.getlocal(8));
                  var1.setlocal(9, var7);
                  var4 = null;
                  var1.setline(139);
                  var7 = var1.getglobal("posixpath").__getattr__("splitext").__call__(var2, var1.getlocal(7));
                  var9 = Py.unpackSequence(var7, 2);
                  var6 = var9[0];
                  var1.setlocal(7, var6);
                  var6 = null;
                  var6 = var9[1];
                  var1.setlocal(8, var6);
                  var6 = null;
                  var4 = null;
               } else {
                  var1.setline(141);
                  var7 = var1.getglobal("None");
                  var1.setlocal(9, var7);
                  var4 = null;
               }

               var1.setline(142);
               var7 = var1.getlocal(0).__getattr__("types_map").__getitem__(var1.getglobal("True"));
               var1.setlocal(10, var7);
               var4 = null;
               var1.setline(143);
               var7 = var1.getlocal(8);
               var10000 = var7._in(var1.getlocal(10));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(144);
                  var8 = new PyTuple(new PyObject[]{var1.getlocal(10).__getitem__(var1.getlocal(8)), var1.getlocal(9)});
                  var1.f_lasti = -1;
                  return var8;
               } else {
                  var1.setline(145);
                  var7 = var1.getlocal(8).__getattr__("lower").__call__(var2);
                  var10000 = var7._in(var1.getlocal(10));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(146);
                     var8 = new PyTuple(new PyObject[]{var1.getlocal(10).__getitem__(var1.getlocal(8).__getattr__("lower").__call__(var2)), var1.getlocal(9)});
                     var1.f_lasti = -1;
                     return var8;
                  } else {
                     var1.setline(147);
                     if (var1.getlocal(2).__nonzero__()) {
                        var1.setline(148);
                        var8 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(9)});
                        var1.f_lasti = -1;
                        return var8;
                     } else {
                        var1.setline(149);
                        var7 = var1.getlocal(0).__getattr__("types_map").__getitem__(var1.getglobal("False"));
                        var1.setlocal(10, var7);
                        var4 = null;
                        var1.setline(150);
                        var7 = var1.getlocal(8);
                        var10000 = var7._in(var1.getlocal(10));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(151);
                           var8 = new PyTuple(new PyObject[]{var1.getlocal(10).__getitem__(var1.getlocal(8)), var1.getlocal(9)});
                           var1.f_lasti = -1;
                           return var8;
                        } else {
                           var1.setline(152);
                           var7 = var1.getlocal(8).__getattr__("lower").__call__(var2);
                           var10000 = var7._in(var1.getlocal(10));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(153);
                              var8 = new PyTuple(new PyObject[]{var1.getlocal(10).__getitem__(var1.getlocal(8).__getattr__("lower").__call__(var2)), var1.getlocal(9)});
                              var1.f_lasti = -1;
                              return var8;
                           } else {
                              var1.setline(155);
                              var8 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(9)});
                              var1.f_lasti = -1;
                              return var8;
                           }
                        }
                     }
                  }
               }
            }

            var1.setline(136);
            var7 = var1.getglobal("posixpath").__getattr__("splitext").__call__(var2, var1.getlocal(7)._add(var1.getlocal(0).__getattr__("suffix_map").__getitem__(var1.getlocal(8))));
            var9 = Py.unpackSequence(var7, 2);
            var6 = var9[0];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var9[1];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
         }
      }
   }

   public PyObject guess_all_extensions$5(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyString.fromInterned("Guess the extensions for a file based on its MIME type.\n\n        Return value is a list of strings giving the possible filename\n        extensions, including the leading dot ('.').  The extension is not\n        guaranteed to have been associated with any particular data stream,\n        but would be mapped to the MIME type `type' by guess_type().\n\n        Optional `strict' argument when false adds a bunch of commonly found,\n        but non-standard types.\n        ");
      var1.setline(168);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(169);
      var3 = var1.getlocal(0).__getattr__("types_map_inv").__getitem__(var1.getglobal("True")).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(170);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(171);
         var3 = var1.getlocal(0).__getattr__("types_map_inv").__getitem__(var1.getglobal("False")).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyList(Py.EmptyObjects))).__iter__();

         while(true) {
            var1.setline(171);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(172);
            PyObject var5 = var1.getlocal(4);
            PyObject var10000 = var5._notin(var1.getlocal(3));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(173);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
            }
         }
      }

      var1.setline(174);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject guess_extension$6(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyString.fromInterned("Guess the extension for a file based on its MIME type.\n\n        Return value is a string giving a filename extension,\n        including the leading dot ('.').  The extension is not\n        guaranteed to have been associated with any particular data\n        stream, but would be mapped to the MIME type `type' by\n        guess_type().  If no extension can be guessed for `type', None\n        is returned.\n\n        Optional `strict' argument when false adds a bunch of commonly found,\n        but non-standard types.\n        ");
      var1.setline(189);
      PyObject var3 = var1.getlocal(0).__getattr__("guess_all_extensions").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(190);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(191);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(192);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject read$7(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(201);
      PyString.fromInterned("\n        Read a single mime.types-format file, specified by pathname.\n\n        If strict is true, information will be added to\n        list of standard types, else to the list of non-standard\n        types.\n        ");
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(1)))).__enter__(var2);

      label16: {
         try {
            var1.setlocal(3, var4);
            var1.setline(203);
            var1.getlocal(0).__getattr__("readfp").__call__(var2, var1.getlocal(3), var1.getlocal(2));
         } catch (Throwable var5) {
            if (var3.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readfp$8(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyString.fromInterned("\n        Read a single mime.types-format file.\n\n        If strict is true, information will be added to\n        list of standard types, else to the list of non-standard\n        types.\n        ");

      while(true) {
         var1.setline(213);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(214);
         PyObject var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(215);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            break;
         }

         var1.setline(217);
         var3 = var1.getlocal(3).__getattr__("split").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(218);
         var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(4))).__iter__();

         PyObject var4;
         PyObject var5;
         while(true) {
            var1.setline(218);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(219);
            var5 = var1.getlocal(4).__getitem__(var1.getlocal(5)).__getitem__(Py.newInteger(0));
            PyObject var10000 = var5._eq(PyString.fromInterned("#"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(220);
               var1.getlocal(4).__delslice__(var1.getlocal(5), (PyObject)null, (PyObject)null);
               break;
            }
         }

         var1.setline(222);
         if (!var1.getlocal(4).__not__().__nonzero__()) {
            var1.setline(224);
            PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(4).__getitem__(Py.newInteger(0)), var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)});
            PyObject[] var6 = Py.unpackSequence(var7, 2);
            var5 = var6[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var6[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
            var1.setline(225);
            var3 = var1.getlocal(7).__iter__();

            while(true) {
               var1.setline(225);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(8, var4);
               var1.setline(226);
               var1.getlocal(0).__getattr__("add_type").__call__(var2, var1.getlocal(6), PyString.fromInterned(".")._add(var1.getlocal(8)), var1.getlocal(2));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_windows_registry$9(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(235);
      PyString.fromInterned("\n        Load the MIME types database from Windows registry.\n\n        If strict is true, information will be added to\n        list of standard types, else to the list of non-standard\n        types.\n        ");
      var1.setline(238);
      if (var1.getglobal("_winreg").__not__().__nonzero__()) {
         var1.setline(239);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(241);
         PyObject[] var3 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var3;
         PyCode var10004 = enum_types$10;
         var3 = new PyObject[]{var1.getclosure(0)};
         PyFunction var14 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
         var1.setlocal(2, var14);
         var3 = null;
         var1.setline(256);
         PyObject var15 = var1.getglobal("sys").__getattr__("getdefaultencoding").__call__(var2);
         var1.setderef(0, var15);
         var3 = null;
         ContextManager var16;
         PyObject var4 = (var16 = ContextGuard.getManager(var1.getglobal("_winreg").__getattr__("OpenKey").__call__((ThreadState)var2, (PyObject)var1.getglobal("_winreg").__getattr__("HKEY_CLASSES_ROOT"), (PyObject)PyString.fromInterned("MIME\\Database\\Content Type")))).__enter__(var2);

         label66: {
            try {
               var1.setlocal(3, var4);
               var1.setline(259);
               var4 = var1.getlocal(2).__call__(var2, var1.getlocal(3)).__iter__();

               while(true) {
                  var1.setline(259);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     break;
                  }

                  var1.setlocal(4, var5);

                  PyException var6;
                  try {
                     label73: {
                        ContextManager var17;
                        PyObject var7 = (var17 = ContextGuard.getManager(var1.getglobal("_winreg").__getattr__("OpenKey").__call__(var2, var1.getlocal(3), var1.getlocal(4)))).__enter__(var2);

                        try {
                           var1.setlocal(5, var7);
                           var1.setline(262);
                           var7 = var1.getglobal("_winreg").__getattr__("QueryValueEx").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("Extension"));
                           PyObject[] var8 = Py.unpackSequence(var7, 2);
                           PyObject var9 = var8[0];
                           var1.setlocal(6, var9);
                           var9 = null;
                           var9 = var8[1];
                           var1.setlocal(7, var9);
                           var9 = null;
                           var7 = null;
                        } catch (Throwable var11) {
                           if (var17.__exit__(var2, Py.setException(var11, var1))) {
                              break label73;
                           }

                           throw (Throwable)Py.makeException();
                        }

                        var17.__exit__(var2, (PyException)null);
                     }
                  } catch (Throwable var12) {
                     var6 = Py.setException(var12, var1);
                     if (var6.match(var1.getglobal("EnvironmentError"))) {
                        continue;
                     }

                     throw var6;
                  }

                  var1.setline(266);
                  PyObject var18 = var1.getlocal(7);
                  PyObject var10000 = var18._ne(var1.getglobal("_winreg").__getattr__("REG_SZ"));
                  var6 = null;
                  if (!var10000.__nonzero__()) {
                     try {
                        var1.setline(269);
                        var18 = var1.getlocal(6).__getattr__("encode").__call__(var2, var1.getderef(0));
                        var1.setlocal(6, var18);
                        var6 = null;
                     } catch (Throwable var10) {
                        var6 = Py.setException(var10, var1);
                        if (var6.match(var1.getglobal("UnicodeEncodeError"))) {
                           continue;
                        }

                        throw var6;
                     }

                     var1.setline(272);
                     var1.getlocal(0).__getattr__("add_type").__call__(var2, var1.getlocal(4), var1.getlocal(6), var1.getlocal(1));
                  }
               }
            } catch (Throwable var13) {
               if (var16.__exit__(var2, Py.setException(var13, var1))) {
                  break label66;
               }

               throw (Throwable)Py.makeException();
            }

            var16.__exit__(var2, (PyException)null);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject enum_types$10(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject guess_type$11(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyString.fromInterned("Guess the type of a file based on its URL.\n\n    Return value is a tuple (type, encoding) where type is None if the\n    type can't be guessed (no or unknown suffix) or a string of the\n    form type/subtype, usable for a MIME Content-type header; and\n    encoding is None for no encoding or the name of the program used\n    to encode (e.g. compress or gzip).  The mappings are table\n    driven.  Encoding suffixes are case sensitive; type suffixes are\n    first tried case sensitive, then case insensitive.\n\n    The suffixes .tgz, .taz and .tz (case sensitive!) are all mapped\n    to \".tar.gz\".  (This is table-driven too, using the dictionary\n    suffix_map).\n\n    Optional `strict' argument when false adds a bunch of commonly found, but\n    non-standard types.\n    ");
      var1.setline(293);
      PyObject var3 = var1.getglobal("_db");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(294);
         var1.getglobal("init").__call__(var2);
      }

      var1.setline(295);
      var3 = var1.getglobal("_db").__getattr__("guess_type").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject guess_all_extensions$12(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      PyString.fromInterned("Guess the extensions for a file based on its MIME type.\n\n    Return value is a list of strings giving the possible filename\n    extensions, including the leading dot ('.').  The extension is not\n    guaranteed to have been associated with any particular data\n    stream, but would be mapped to the MIME type `type' by\n    guess_type().  If no extension can be guessed for `type', None\n    is returned.\n\n    Optional `strict' argument when false adds a bunch of commonly found,\n    but non-standard types.\n    ");
      var1.setline(311);
      PyObject var3 = var1.getglobal("_db");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(312);
         var1.getglobal("init").__call__(var2);
      }

      var1.setline(313);
      var3 = var1.getglobal("_db").__getattr__("guess_all_extensions").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject guess_extension$13(PyFrame var1, ThreadState var2) {
      var1.setline(326);
      PyString.fromInterned("Guess the extension for a file based on its MIME type.\n\n    Return value is a string giving a filename extension, including the\n    leading dot ('.').  The extension is not guaranteed to have been\n    associated with any particular data stream, but would be mapped to the\n    MIME type `type' by guess_type().  If no extension can be guessed for\n    `type', None is returned.\n\n    Optional `strict' argument when false adds a bunch of commonly found,\n    but non-standard types.\n    ");
      var1.setline(327);
      PyObject var3 = var1.getglobal("_db");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(328);
         var1.getglobal("init").__call__(var2);
      }

      var1.setline(329);
      var3 = var1.getglobal("_db").__getattr__("guess_extension").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject add_type$14(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      PyString.fromInterned("Add a mapping between a type and an extension.\n\n    When the extension is already known, the new\n    type will replace the old one. When the type\n    is already known the extension will be added\n    to the list of known extensions.\n\n    If strict is true, information will be added to\n    list of standard types, else to the list of non-standard\n    types.\n    ");
      var1.setline(343);
      PyObject var3 = var1.getglobal("_db");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(344);
         var1.getglobal("init").__call__(var2);
      }

      var1.setline(345);
      var3 = var1.getglobal("_db").__getattr__("add_type").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject init$15(PyFrame var1, ThreadState var2) {
      var1.setline(351);
      PyObject var3 = var1.getglobal("True");
      var1.setglobal("inited", var3);
      var3 = null;
      var1.setline(352);
      var3 = var1.getglobal("MimeTypes").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(353);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(354);
         if (var1.getglobal("_winreg").__nonzero__()) {
            var1.setline(355);
            var1.getlocal(1).__getattr__("read_windows_registry").__call__(var2);
         }

         var1.setline(356);
         var3 = var1.getglobal("knownfiles");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(357);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(357);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(360);
            var3 = var1.getlocal(1).__getattr__("encodings_map");
            var1.setglobal("encodings_map", var3);
            var3 = null;
            var1.setline(361);
            var3 = var1.getlocal(1).__getattr__("suffix_map");
            var1.setglobal("suffix_map", var3);
            var3 = null;
            var1.setline(362);
            var3 = var1.getlocal(1).__getattr__("types_map").__getitem__(var1.getglobal("True"));
            var1.setglobal("types_map", var3);
            var3 = null;
            var1.setline(363);
            var3 = var1.getlocal(1).__getattr__("types_map").__getitem__(var1.getglobal("False"));
            var1.setglobal("common_types", var3);
            var3 = null;
            var1.setline(365);
            var3 = var1.getlocal(1);
            var1.setglobal("_db", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(358);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(2)).__nonzero__()) {
            var1.setline(359);
            var1.getlocal(1).__getattr__("read").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject read_mime_types$16(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      PyObject var6;
      try {
         var1.setline(370);
         var6 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var1.setline(372);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(373);
      var6 = var1.getglobal("MimeTypes").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(374);
      var1.getlocal(2).__getattr__("readfp").__call__(var2, var1.getlocal(1), var1.getglobal("True"));
      var1.setline(375);
      var4 = var1.getlocal(2).__getattr__("types_map").__getitem__(var1.getglobal("True"));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _default_mime_types$17(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned(".tgz"), PyString.fromInterned(".tar.gz"), PyString.fromInterned(".taz"), PyString.fromInterned(".tar.gz"), PyString.fromInterned(".tz"), PyString.fromInterned(".tar.gz"), PyString.fromInterned(".tbz2"), PyString.fromInterned(".tar.bz2")});
      var1.setglobal("suffix_map", var3);
      var3 = null;
      var1.setline(391);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned(".gz"), PyString.fromInterned("gzip"), PyString.fromInterned(".Z"), PyString.fromInterned("compress"), PyString.fromInterned(".bz2"), PyString.fromInterned("bzip2")});
      var1.setglobal("encodings_map", var3);
      var3 = null;
      var1.setline(402);
      PyObject[] var10002 = new PyObject[246];
      set$$0(var10002);
      var3 = new PyDictionary(var10002);
      var1.setglobal("types_map", var3);
      var3 = null;
      var1.setline(534);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned(".jpg"), PyString.fromInterned("image/jpg"), PyString.fromInterned(".mid"), PyString.fromInterned("audio/midi"), PyString.fromInterned(".midi"), PyString.fromInterned("audio/midi"), PyString.fromInterned(".pct"), PyString.fromInterned("image/pict"), PyString.fromInterned(".pic"), PyString.fromInterned("image/pict"), PyString.fromInterned(".pict"), PyString.fromInterned("image/pict"), PyString.fromInterned(".rtf"), PyString.fromInterned("application/rtf"), PyString.fromInterned(".xul"), PyString.fromInterned("text/xul")});
      var1.setglobal("common_types", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   private static void set$$0(PyObject[] var0) {
      var0[0] = PyString.fromInterned(".a");
      var0[1] = PyString.fromInterned("application/octet-stream");
      var0[2] = PyString.fromInterned(".ai");
      var0[3] = PyString.fromInterned("application/postscript");
      var0[4] = PyString.fromInterned(".aif");
      var0[5] = PyString.fromInterned("audio/x-aiff");
      var0[6] = PyString.fromInterned(".aifc");
      var0[7] = PyString.fromInterned("audio/x-aiff");
      var0[8] = PyString.fromInterned(".aiff");
      var0[9] = PyString.fromInterned("audio/x-aiff");
      var0[10] = PyString.fromInterned(".au");
      var0[11] = PyString.fromInterned("audio/basic");
      var0[12] = PyString.fromInterned(".avi");
      var0[13] = PyString.fromInterned("video/x-msvideo");
      var0[14] = PyString.fromInterned(".bat");
      var0[15] = PyString.fromInterned("text/plain");
      var0[16] = PyString.fromInterned(".bcpio");
      var0[17] = PyString.fromInterned("application/x-bcpio");
      var0[18] = PyString.fromInterned(".bin");
      var0[19] = PyString.fromInterned("application/octet-stream");
      var0[20] = PyString.fromInterned(".bmp");
      var0[21] = PyString.fromInterned("image/x-ms-bmp");
      var0[22] = PyString.fromInterned(".c");
      var0[23] = PyString.fromInterned("text/plain");
      var0[24] = PyString.fromInterned(".cdf");
      var0[25] = PyString.fromInterned("application/x-cdf");
      var0[26] = PyString.fromInterned(".cdf");
      var0[27] = PyString.fromInterned("application/x-netcdf");
      var0[28] = PyString.fromInterned(".cpio");
      var0[29] = PyString.fromInterned("application/x-cpio");
      var0[30] = PyString.fromInterned(".csh");
      var0[31] = PyString.fromInterned("application/x-csh");
      var0[32] = PyString.fromInterned(".css");
      var0[33] = PyString.fromInterned("text/css");
      var0[34] = PyString.fromInterned(".dll");
      var0[35] = PyString.fromInterned("application/octet-stream");
      var0[36] = PyString.fromInterned(".doc");
      var0[37] = PyString.fromInterned("application/msword");
      var0[38] = PyString.fromInterned(".dot");
      var0[39] = PyString.fromInterned("application/msword");
      var0[40] = PyString.fromInterned(".dvi");
      var0[41] = PyString.fromInterned("application/x-dvi");
      var0[42] = PyString.fromInterned(".eml");
      var0[43] = PyString.fromInterned("message/rfc822");
      var0[44] = PyString.fromInterned(".eps");
      var0[45] = PyString.fromInterned("application/postscript");
      var0[46] = PyString.fromInterned(".etx");
      var0[47] = PyString.fromInterned("text/x-setext");
      var0[48] = PyString.fromInterned(".exe");
      var0[49] = PyString.fromInterned("application/octet-stream");
      var0[50] = PyString.fromInterned(".gif");
      var0[51] = PyString.fromInterned("image/gif");
      var0[52] = PyString.fromInterned(".gtar");
      var0[53] = PyString.fromInterned("application/x-gtar");
      var0[54] = PyString.fromInterned(".h");
      var0[55] = PyString.fromInterned("text/plain");
      var0[56] = PyString.fromInterned(".hdf");
      var0[57] = PyString.fromInterned("application/x-hdf");
      var0[58] = PyString.fromInterned(".htm");
      var0[59] = PyString.fromInterned("text/html");
      var0[60] = PyString.fromInterned(".html");
      var0[61] = PyString.fromInterned("text/html");
      var0[62] = PyString.fromInterned(".ico");
      var0[63] = PyString.fromInterned("image/vnd.microsoft.icon");
      var0[64] = PyString.fromInterned(".ief");
      var0[65] = PyString.fromInterned("image/ief");
      var0[66] = PyString.fromInterned(".jpe");
      var0[67] = PyString.fromInterned("image/jpeg");
      var0[68] = PyString.fromInterned(".jpeg");
      var0[69] = PyString.fromInterned("image/jpeg");
      var0[70] = PyString.fromInterned(".jpg");
      var0[71] = PyString.fromInterned("image/jpeg");
      var0[72] = PyString.fromInterned(".js");
      var0[73] = PyString.fromInterned("application/javascript");
      var0[74] = PyString.fromInterned(".ksh");
      var0[75] = PyString.fromInterned("text/plain");
      var0[76] = PyString.fromInterned(".latex");
      var0[77] = PyString.fromInterned("application/x-latex");
      var0[78] = PyString.fromInterned(".m1v");
      var0[79] = PyString.fromInterned("video/mpeg");
      var0[80] = PyString.fromInterned(".man");
      var0[81] = PyString.fromInterned("application/x-troff-man");
      var0[82] = PyString.fromInterned(".me");
      var0[83] = PyString.fromInterned("application/x-troff-me");
      var0[84] = PyString.fromInterned(".mht");
      var0[85] = PyString.fromInterned("message/rfc822");
      var0[86] = PyString.fromInterned(".mhtml");
      var0[87] = PyString.fromInterned("message/rfc822");
      var0[88] = PyString.fromInterned(".mif");
      var0[89] = PyString.fromInterned("application/x-mif");
      var0[90] = PyString.fromInterned(".mov");
      var0[91] = PyString.fromInterned("video/quicktime");
      var0[92] = PyString.fromInterned(".movie");
      var0[93] = PyString.fromInterned("video/x-sgi-movie");
      var0[94] = PyString.fromInterned(".mp2");
      var0[95] = PyString.fromInterned("audio/mpeg");
      var0[96] = PyString.fromInterned(".mp3");
      var0[97] = PyString.fromInterned("audio/mpeg");
      var0[98] = PyString.fromInterned(".mp4");
      var0[99] = PyString.fromInterned("video/mp4");
      var0[100] = PyString.fromInterned(".mpa");
      var0[101] = PyString.fromInterned("video/mpeg");
      var0[102] = PyString.fromInterned(".mpe");
      var0[103] = PyString.fromInterned("video/mpeg");
      var0[104] = PyString.fromInterned(".mpeg");
      var0[105] = PyString.fromInterned("video/mpeg");
      var0[106] = PyString.fromInterned(".mpg");
      var0[107] = PyString.fromInterned("video/mpeg");
      var0[108] = PyString.fromInterned(".ms");
      var0[109] = PyString.fromInterned("application/x-troff-ms");
      var0[110] = PyString.fromInterned(".nc");
      var0[111] = PyString.fromInterned("application/x-netcdf");
      var0[112] = PyString.fromInterned(".nws");
      var0[113] = PyString.fromInterned("message/rfc822");
      var0[114] = PyString.fromInterned(".o");
      var0[115] = PyString.fromInterned("application/octet-stream");
      var0[116] = PyString.fromInterned(".obj");
      var0[117] = PyString.fromInterned("application/octet-stream");
      var0[118] = PyString.fromInterned(".oda");
      var0[119] = PyString.fromInterned("application/oda");
      var0[120] = PyString.fromInterned(".p12");
      var0[121] = PyString.fromInterned("application/x-pkcs12");
      var0[122] = PyString.fromInterned(".p7c");
      var0[123] = PyString.fromInterned("application/pkcs7-mime");
      var0[124] = PyString.fromInterned(".pbm");
      var0[125] = PyString.fromInterned("image/x-portable-bitmap");
      var0[126] = PyString.fromInterned(".pdf");
      var0[127] = PyString.fromInterned("application/pdf");
      var0[128] = PyString.fromInterned(".pfx");
      var0[129] = PyString.fromInterned("application/x-pkcs12");
      var0[130] = PyString.fromInterned(".pgm");
      var0[131] = PyString.fromInterned("image/x-portable-graymap");
      var0[132] = PyString.fromInterned(".pl");
      var0[133] = PyString.fromInterned("text/plain");
      var0[134] = PyString.fromInterned(".png");
      var0[135] = PyString.fromInterned("image/png");
      var0[136] = PyString.fromInterned(".pnm");
      var0[137] = PyString.fromInterned("image/x-portable-anymap");
      var0[138] = PyString.fromInterned(".pot");
      var0[139] = PyString.fromInterned("application/vnd.ms-powerpoint");
      var0[140] = PyString.fromInterned(".ppa");
      var0[141] = PyString.fromInterned("application/vnd.ms-powerpoint");
      var0[142] = PyString.fromInterned(".ppm");
      var0[143] = PyString.fromInterned("image/x-portable-pixmap");
      var0[144] = PyString.fromInterned(".pps");
      var0[145] = PyString.fromInterned("application/vnd.ms-powerpoint");
      var0[146] = PyString.fromInterned(".ppt");
      var0[147] = PyString.fromInterned("application/vnd.ms-powerpoint");
      var0[148] = PyString.fromInterned(".ps");
      var0[149] = PyString.fromInterned("application/postscript");
      var0[150] = PyString.fromInterned(".pwz");
      var0[151] = PyString.fromInterned("application/vnd.ms-powerpoint");
      var0[152] = PyString.fromInterned(".py");
      var0[153] = PyString.fromInterned("text/x-python");
      var0[154] = PyString.fromInterned(".pyc");
      var0[155] = PyString.fromInterned("application/x-python-code");
      var0[156] = PyString.fromInterned(".pyo");
      var0[157] = PyString.fromInterned("application/x-python-code");
      var0[158] = PyString.fromInterned(".qt");
      var0[159] = PyString.fromInterned("video/quicktime");
      var0[160] = PyString.fromInterned(".ra");
      var0[161] = PyString.fromInterned("audio/x-pn-realaudio");
      var0[162] = PyString.fromInterned(".ram");
      var0[163] = PyString.fromInterned("application/x-pn-realaudio");
      var0[164] = PyString.fromInterned(".ras");
      var0[165] = PyString.fromInterned("image/x-cmu-raster");
      var0[166] = PyString.fromInterned(".rdf");
      var0[167] = PyString.fromInterned("application/xml");
      var0[168] = PyString.fromInterned(".rgb");
      var0[169] = PyString.fromInterned("image/x-rgb");
      var0[170] = PyString.fromInterned(".roff");
      var0[171] = PyString.fromInterned("application/x-troff");
      var0[172] = PyString.fromInterned(".rtx");
      var0[173] = PyString.fromInterned("text/richtext");
      var0[174] = PyString.fromInterned(".sgm");
      var0[175] = PyString.fromInterned("text/x-sgml");
      var0[176] = PyString.fromInterned(".sgml");
      var0[177] = PyString.fromInterned("text/x-sgml");
      var0[178] = PyString.fromInterned(".sh");
      var0[179] = PyString.fromInterned("application/x-sh");
      var0[180] = PyString.fromInterned(".shar");
      var0[181] = PyString.fromInterned("application/x-shar");
      var0[182] = PyString.fromInterned(".snd");
      var0[183] = PyString.fromInterned("audio/basic");
      var0[184] = PyString.fromInterned(".so");
      var0[185] = PyString.fromInterned("application/octet-stream");
      var0[186] = PyString.fromInterned(".src");
      var0[187] = PyString.fromInterned("application/x-wais-source");
      var0[188] = PyString.fromInterned(".sv4cpio");
      var0[189] = PyString.fromInterned("application/x-sv4cpio");
      var0[190] = PyString.fromInterned(".sv4crc");
      var0[191] = PyString.fromInterned("application/x-sv4crc");
      var0[192] = PyString.fromInterned(".swf");
      var0[193] = PyString.fromInterned("application/x-shockwave-flash");
      var0[194] = PyString.fromInterned(".t");
      var0[195] = PyString.fromInterned("application/x-troff");
      var0[196] = PyString.fromInterned(".tar");
      var0[197] = PyString.fromInterned("application/x-tar");
      var0[198] = PyString.fromInterned(".tcl");
      var0[199] = PyString.fromInterned("application/x-tcl");
      var0[200] = PyString.fromInterned(".tex");
      var0[201] = PyString.fromInterned("application/x-tex");
      var0[202] = PyString.fromInterned(".texi");
      var0[203] = PyString.fromInterned("application/x-texinfo");
      var0[204] = PyString.fromInterned(".texinfo");
      var0[205] = PyString.fromInterned("application/x-texinfo");
      var0[206] = PyString.fromInterned(".tif");
      var0[207] = PyString.fromInterned("image/tiff");
      var0[208] = PyString.fromInterned(".tiff");
      var0[209] = PyString.fromInterned("image/tiff");
      var0[210] = PyString.fromInterned(".tr");
      var0[211] = PyString.fromInterned("application/x-troff");
      var0[212] = PyString.fromInterned(".tsv");
      var0[213] = PyString.fromInterned("text/tab-separated-values");
      var0[214] = PyString.fromInterned(".txt");
      var0[215] = PyString.fromInterned("text/plain");
      var0[216] = PyString.fromInterned(".ustar");
      var0[217] = PyString.fromInterned("application/x-ustar");
      var0[218] = PyString.fromInterned(".vcf");
      var0[219] = PyString.fromInterned("text/x-vcard");
      var0[220] = PyString.fromInterned(".wav");
      var0[221] = PyString.fromInterned("audio/x-wav");
      var0[222] = PyString.fromInterned(".wiz");
      var0[223] = PyString.fromInterned("application/msword");
      var0[224] = PyString.fromInterned(".wsdl");
      var0[225] = PyString.fromInterned("application/xml");
      var0[226] = PyString.fromInterned(".xbm");
      var0[227] = PyString.fromInterned("image/x-xbitmap");
      var0[228] = PyString.fromInterned(".xlb");
      var0[229] = PyString.fromInterned("application/vnd.ms-excel");
      var0[230] = PyString.fromInterned(".xls");
      var0[231] = PyString.fromInterned("application/excel");
      var0[232] = PyString.fromInterned(".xls");
      var0[233] = PyString.fromInterned("application/vnd.ms-excel");
      var0[234] = PyString.fromInterned(".xml");
      var0[235] = PyString.fromInterned("text/xml");
      var0[236] = PyString.fromInterned(".xpdl");
      var0[237] = PyString.fromInterned("application/xml");
      var0[238] = PyString.fromInterned(".xpm");
      var0[239] = PyString.fromInterned("image/x-xpixmap");
      var0[240] = PyString.fromInterned(".xsl");
      var0[241] = PyString.fromInterned("application/xml");
      var0[242] = PyString.fromInterned(".xwd");
      var0[243] = PyString.fromInterned("image/x-xwindowdump");
      var0[244] = PyString.fromInterned(".zip");
      var0[245] = PyString.fromInterned("application/zip");
   }

   public PyObject usage$18(PyFrame var1, ThreadState var2) {
      var1.setline(565);
      Py.println(var1.getglobal("USAGE"));
      var1.setline(566);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(566);
         Py.println(var1.getlocal(1));
      }

      var1.setline(567);
      var1.getglobal("sys").__getattr__("exit").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public mimetypes$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MimeTypes$1 = Py.newCode(0, var2, var1, "MimeTypes", 56, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filenames", "strict", "ext", "type", "name"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 64, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "ext", "strict", "exts"};
      add_type$3 = Py.newCode(4, var2, var1, "add_type", 78, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "url", "strict", "scheme", "comma", "semi", "type", "base", "ext", "encoding", "types_map"};
      guess_type$4 = Py.newCode(3, var2, var1, "guess_type", 95, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "strict", "extensions", "ext"};
      guess_all_extensions$5 = Py.newCode(3, var2, var1, "guess_all_extensions", 157, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "strict", "extensions"};
      guess_extension$6 = Py.newCode(3, var2, var1, "guess_extension", 176, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "strict", "fp"};
      read$7 = Py.newCode(3, var2, var1, "read", 194, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "strict", "line", "words", "i", "type", "suffixes", "suff"};
      readfp$8 = Py.newCode(3, var2, var1, "readfp", 205, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "strict", "enum_types", "mimedb", "ctype", "key", "suffix", "datatype", "default_encoding"};
      String[] var10001 = var2;
      mimetypes$py var10007 = self;
      var2 = new String[]{"default_encoding"};
      read_windows_registry$9 = Py.newCode(2, var10001, var1, "read_windows_registry", 228, false, false, var10007, 9, var2, (String[])null, 1, 4097);
      var2 = new String[]{"mimedb", "i", "ctype"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"default_encoding"};
      enum_types$10 = Py.newCode(1, var10001, var1, "enum_types", 241, false, false, var10007, 10, (String[])null, var2, 0, 4129);
      var2 = new String[]{"url", "strict"};
      guess_type$11 = Py.newCode(2, var2, var1, "guess_type", 275, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"type", "strict"};
      guess_all_extensions$12 = Py.newCode(2, var2, var1, "guess_all_extensions", 298, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"type", "strict"};
      guess_extension$13 = Py.newCode(2, var2, var1, "guess_extension", 315, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"type", "ext", "strict"};
      add_type$14 = Py.newCode(3, var2, var1, "add_type", 331, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"files", "db", "file"};
      init$15 = Py.newCode(1, var2, var1, "init", 348, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "f", "db"};
      read_mime_types$16 = Py.newCode(1, var2, var1, "read_mime_types", 368, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _default_mime_types$17 = Py.newCode(0, var2, var1, "_default_mime_types", 378, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "msg"};
      usage$18 = Py.newCode(2, var2, var1, "usage", 564, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new mimetypes$py("mimetypes$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(mimetypes$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MimeTypes$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.add_type$3(var2, var3);
         case 4:
            return this.guess_type$4(var2, var3);
         case 5:
            return this.guess_all_extensions$5(var2, var3);
         case 6:
            return this.guess_extension$6(var2, var3);
         case 7:
            return this.read$7(var2, var3);
         case 8:
            return this.readfp$8(var2, var3);
         case 9:
            return this.read_windows_registry$9(var2, var3);
         case 10:
            return this.enum_types$10(var2, var3);
         case 11:
            return this.guess_type$11(var2, var3);
         case 12:
            return this.guess_all_extensions$12(var2, var3);
         case 13:
            return this.guess_extension$13(var2, var3);
         case 14:
            return this.add_type$14(var2, var3);
         case 15:
            return this.init$15(var2, var3);
         case 16:
            return this.read_mime_types$16(var2, var3);
         case 17:
            return this._default_mime_types$17(var2, var3);
         case 18:
            return this.usage$18(var2, var3);
         default:
            return null;
      }
   }
}
