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
@Filename("MimeWriter.py")
public class MimeWriter$py extends PyFunctionTable implements PyRunnable {
   static MimeWriter$py self;
   static final PyCode f$0;
   static final PyCode MimeWriter$1;
   static final PyCode __init__$2;
   static final PyCode addheader$3;
   static final PyCode flushheaders$4;
   static final PyCode startbody$5;
   static final PyCode startmultipartbody$6;
   static final PyCode nextpart$7;
   static final PyCode lastpart$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Generic MIME writer.\n\nThis module defines the class MimeWriter.  The MimeWriter class implements\na basic formatter for creating MIME multi-part files.  It doesn't seek around\nthe output file nor does it use large amounts of buffer space. You must write\nthe parts out in the order that they should occur in the final file.\nMimeWriter does buffer the headers you add, allowing you to rearrange their\norder.\n\n"));
      var1.setline(10);
      PyString.fromInterned("Generic MIME writer.\n\nThis module defines the class MimeWriter.  The MimeWriter class implements\na basic formatter for creating MIME multi-part files.  It doesn't seek around\nthe output file nor does it use large amounts of buffer space. You must write\nthe parts out in the order that they should occur in the final file.\nMimeWriter does buffer the headers you add, allowing you to rearrange their\norder.\n\n");
      var1.setline(13);
      PyObject var3 = imp.importOne("mimetools", var1, -1);
      var1.setlocal("mimetools", var3);
      var3 = null;
      var1.setline(15);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("MimeWriter")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(17);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(19);
      var1.getname("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("the MimeWriter module is deprecated; use the email package instead"), (PyObject)var1.getname("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(22);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("MimeWriter", var6, MimeWriter$1);
      var1.setlocal("MimeWriter", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(185);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(186);
         var3 = imp.importOne("test.test_MimeWriter", var1, -1);
         var1.setlocal("test", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MimeWriter$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Generic MIME writer.\n\n    Methods:\n\n    __init__()\n    addheader()\n    flushheaders()\n    startbody()\n    startmultipartbody()\n    nextpart()\n    lastpart()\n\n    A MIME writer is much more primitive than a MIME parser.  It\n    doesn't seek around on the output file, and it doesn't use large\n    amounts of buffer space, so you have to write the parts in the\n    order they should occur on the output file.  It does buffer the\n    headers you add, allowing you to rearrange their order.\n\n    General usage is:\n\n    f = <open the output file>\n    w = MimeWriter(f)\n    ...call w.addheader(key, value) 0 or more times...\n\n    followed by either:\n\n    f = w.startbody(content_type)\n    ...call f.write(data) for body data...\n\n    or:\n\n    w.startmultipartbody(subtype)\n    for each part:\n        subwriter = w.nextpart()\n        ...use the subwriter's methods to create the subpart...\n    w.lastpart()\n\n    The subwriter is another MimeWriter instance, and should be\n    treated in the same way as the toplevel MimeWriter.  This way,\n    writing recursive body parts is easy.\n\n    Warning: don't forget to call lastpart()!\n\n    XXX There should be more state so calls made in the wrong order\n    are detected.\n\n    Some special cases:\n\n    - startbody() just returns the file passed to the constructor;\n      but don't use this knowledge, as it may be changed.\n\n    - startmultipartbody() actually returns a file as well;\n      this can be used to write the initial 'if you can read this your\n      mailer is not MIME-aware' message.\n\n    - If you call flushheaders(), the headers accumulated so far are\n      written out (and forgotten); this is useful if you don't need a\n      body part at all, e.g. for a subpart of type message/rfc822\n      that's (mis)used to store some header-like information.\n\n    - Passing a keyword argument 'prefix=<flag>' to addheader(),\n      start*body() affects where the header is inserted; 0 means\n      append at the end, 1 means insert at the start; default is\n      append for addheader(), but insert for start*body(), which use\n      it to determine where the Content-Type header goes.\n\n    "));
      var1.setline(90);
      PyString.fromInterned("Generic MIME writer.\n\n    Methods:\n\n    __init__()\n    addheader()\n    flushheaders()\n    startbody()\n    startmultipartbody()\n    nextpart()\n    lastpart()\n\n    A MIME writer is much more primitive than a MIME parser.  It\n    doesn't seek around on the output file, and it doesn't use large\n    amounts of buffer space, so you have to write the parts in the\n    order they should occur on the output file.  It does buffer the\n    headers you add, allowing you to rearrange their order.\n\n    General usage is:\n\n    f = <open the output file>\n    w = MimeWriter(f)\n    ...call w.addheader(key, value) 0 or more times...\n\n    followed by either:\n\n    f = w.startbody(content_type)\n    ...call f.write(data) for body data...\n\n    or:\n\n    w.startmultipartbody(subtype)\n    for each part:\n        subwriter = w.nextpart()\n        ...use the subwriter's methods to create the subpart...\n    w.lastpart()\n\n    The subwriter is another MimeWriter instance, and should be\n    treated in the same way as the toplevel MimeWriter.  This way,\n    writing recursive body parts is easy.\n\n    Warning: don't forget to call lastpart()!\n\n    XXX There should be more state so calls made in the wrong order\n    are detected.\n\n    Some special cases:\n\n    - startbody() just returns the file passed to the constructor;\n      but don't use this knowledge, as it may be changed.\n\n    - startmultipartbody() actually returns a file as well;\n      this can be used to write the initial 'if you can read this your\n      mailer is not MIME-aware' message.\n\n    - If you call flushheaders(), the headers accumulated so far are\n      written out (and forgotten); this is useful if you don't need a\n      body part at all, e.g. for a subpart of type message/rfc822\n      that's (mis)used to store some header-like information.\n\n    - Passing a keyword argument 'prefix=<flag>' to addheader(),\n      start*body() affects where the header is inserted; 0 means\n      append at the end, 1 means insert at the start; default is\n      append for addheader(), but insert for start*body(), which use\n      it to determine where the Content-Type header goes.\n\n    ");
      var1.setline(92);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(96);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, addheader$3, PyString.fromInterned("Add a header line to the MIME message.\n\n        The key is the name of the header, where the value obviously provides\n        the value of the header. The optional argument prefix determines\n        where the header is inserted; 0 means append at the end, 1 means\n        insert at the start. The default is to append.\n\n        "));
      var1.setlocal("addheader", var4);
      var3 = null;
      var1.setline(117);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flushheaders$4, PyString.fromInterned("Writes out and forgets all headers accumulated so far.\n\n        This is useful if you don't need a body part at all; for example,\n        for a subpart of type message/rfc822 that's (mis)used to store some\n        header-like information.\n\n        "));
      var1.setlocal("flushheaders", var4);
      var3 = null;
      var1.setline(128);
      var3 = new PyObject[]{new PyList(Py.EmptyObjects), Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, startbody$5, PyString.fromInterned("Returns a file-like object for writing the body of the message.\n\n        The content-type is set to the provided ctype, and the optional\n        parameter, plist, provides additional parameters for the\n        content-type declaration.  The optional argument prefix determines\n        where the header is inserted; 0 means append at the end, 1 means\n        insert at the start. The default is to insert at the start.\n\n        "));
      var1.setlocal("startbody", var4);
      var3 = null;
      var1.setline(145);
      var3 = new PyObject[]{var1.getname("None"), new PyList(Py.EmptyObjects), Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, startmultipartbody$6, PyString.fromInterned("Returns a file-like object for writing the body of the message.\n\n        Additionally, this method initializes the multi-part code, where the\n        subtype parameter provides the multipart subtype, the boundary\n        parameter may provide a user-defined boundary specification, and the\n        plist parameter provides optional parameters for the subtype.  The\n        optional argument, prefix, determines where the header is inserted;\n        0 means append at the end, 1 means insert at the start. The default\n        is to insert at the start.  Subparts should be created using the\n        nextpart() method.\n\n        "));
      var1.setlocal("startmultipartbody", var4);
      var3 = null;
      var1.setline(163);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, nextpart$7, PyString.fromInterned("Returns a new instance of MimeWriter which represents an\n        individual part in a multipart message.\n\n        This may be used to write the part as well as used for creating\n        recursively complex multipart messages. The message must first be\n        initialized with the startmultipartbody() method before using the\n        nextpart() method.\n\n        "));
      var1.setlocal("nextpart", var4);
      var3 = null;
      var1.setline(176);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lastpart$8, PyString.fromInterned("This is used to designate the last part of a multipart message.\n\n        It should always be used when writing multipart messages.\n\n        "));
      var1.setlocal("lastpart", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_fp", var3);
      var3 = null;
      var1.setline(94);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_headers", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addheader$3(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyString.fromInterned("Add a header line to the MIME message.\n\n        The key is the name of the header, where the value obviously provides\n        the value of the header. The optional argument prefix determines\n        where the header is inserted; 0 means append at the end, 1 means\n        insert at the start. The default is to append.\n\n        ");
      var1.setline(105);
      PyObject var3 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(106);
         PyObject var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4).__getitem__(Py.newInteger(-1)).__not__();
         }

         if (!var10000.__nonzero__()) {
            while(true) {
               var1.setline(107);
               var10000 = var1.getlocal(4);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(4).__getitem__(Py.newInteger(0)).__not__();
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(108);
                  var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4))).__iter__();

                  while(true) {
                     var1.setline(108);
                     PyObject var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(110);
                        var3 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(4))._add(PyString.fromInterned("\n"));
                        var1.setlocal(2, var3);
                        var3 = null;
                        var1.setline(111);
                        var3 = var1.getlocal(1)._add(PyString.fromInterned(": "))._add(var1.getlocal(2));
                        var1.setlocal(6, var3);
                        var3 = null;
                        var1.setline(112);
                        if (var1.getlocal(3).__nonzero__()) {
                           var1.setline(113);
                           var1.getlocal(0).__getattr__("_headers").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(6));
                        } else {
                           var1.setline(115);
                           var1.getlocal(0).__getattr__("_headers").__getattr__("append").__call__(var2, var1.getlocal(6));
                        }

                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(5, var4);
                     var1.setline(109);
                     PyObject var5 = PyString.fromInterned("    ")._add(var1.getlocal(4).__getitem__(var1.getlocal(5)).__getattr__("strip").__call__(var2));
                     var1.getlocal(4).__setitem__(var1.getlocal(5), var5);
                     var5 = null;
                  }
               }

               var1.setline(107);
               var1.getlocal(4).__delitem__((PyObject)Py.newInteger(0));
            }
         }

         var1.setline(106);
         var1.getlocal(4).__delitem__((PyObject)Py.newInteger(-1));
      }
   }

   public PyObject flushheaders$4(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyString.fromInterned("Writes out and forgets all headers accumulated so far.\n\n        This is useful if you don't need a body part at all; for example,\n        for a subpart of type message/rfc822 that's (mis)used to store some\n        header-like information.\n\n        ");
      var1.setline(125);
      var1.getlocal(0).__getattr__("_fp").__getattr__("writelines").__call__(var2, var1.getlocal(0).__getattr__("_headers"));
      var1.setline(126);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_headers", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startbody$5(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyString.fromInterned("Returns a file-like object for writing the body of the message.\n\n        The content-type is set to the provided ctype, and the optional\n        parameter, plist, provides additional parameters for the\n        content-type declaration.  The optional argument prefix determines\n        where the header is inserted; 0 means append at the end, 1 means\n        insert at the start. The default is to insert at the start.\n\n        ");
      var1.setline(138);
      PyObject var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(138);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(140);
            PyObject var10000 = var1.getlocal(0).__getattr__("addheader");
            PyObject[] var7 = new PyObject[]{PyString.fromInterned("Content-Type"), var1.getlocal(1), var1.getlocal(3)};
            String[] var8 = new String[]{"prefix"};
            var10000.__call__(var2, var7, var8);
            var3 = null;
            var1.setline(141);
            var1.getlocal(0).__getattr__("flushheaders").__call__(var2);
            var1.setline(142);
            var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            var1.setline(143);
            var3 = var1.getlocal(0).__getattr__("_fp");
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(139);
         PyObject var9 = var1.getlocal(1)._add(PyString.fromInterned(";\n %s=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
         var1.setlocal(1, var9);
         var5 = null;
      }
   }

   public PyObject startmultipartbody$6(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyString.fromInterned("Returns a file-like object for writing the body of the message.\n\n        Additionally, this method initializes the multi-part code, where the\n        subtype parameter provides the multipart subtype, the boundary\n        parameter may provide a user-defined boundary specification, and the\n        plist parameter provides optional parameters for the subtype.  The\n        optional argument, prefix, determines where the header is inserted;\n        0 means append at the end, 1 means insert at the start. The default\n        is to insert at the start.  Subparts should be created using the\n        nextpart() method.\n\n        ");
      var1.setline(158);
      PyObject var10000 = var1.getlocal(2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("mimetools").__getattr__("choose_boundary").__call__(var2);
      }

      PyObject var3 = var10000;
      var1.getlocal(0).__setattr__("_boundary", var3);
      var3 = null;
      var1.setline(159);
      var10000 = var1.getlocal(0).__getattr__("startbody");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("multipart/")._add(var1.getlocal(1)), (new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("boundary"), var1.getlocal(0).__getattr__("_boundary")})}))._add(var1.getlocal(3)), var1.getlocal(4)};
      String[] var4 = new String[]{"prefix"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject nextpart$7(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyString.fromInterned("Returns a new instance of MimeWriter which represents an\n        individual part in a multipart message.\n\n        This may be used to write the part as well as used for creating\n        recursively complex multipart messages. The message must first be\n        initialized with the startmultipartbody() method before using the\n        nextpart() method.\n\n        ");
      var1.setline(173);
      var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, PyString.fromInterned("\n--")._add(var1.getlocal(0).__getattr__("_boundary"))._add(PyString.fromInterned("\n")));
      var1.setline(174);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(0).__getattr__("_fp"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lastpart$8(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyString.fromInterned("This is used to designate the last part of a multipart message.\n\n        It should always be used when writing multipart messages.\n\n        ");
      var1.setline(182);
      var1.getlocal(0).__getattr__("_fp").__getattr__("write").__call__(var2, PyString.fromInterned("\n--")._add(var1.getlocal(0).__getattr__("_boundary"))._add(PyString.fromInterned("--\n")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public MimeWriter$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MimeWriter$1 = Py.newCode(0, var2, var1, "MimeWriter", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 92, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value", "prefix", "lines", "i", "line"};
      addheader$3 = Py.newCode(4, var2, var1, "addheader", 96, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flushheaders$4 = Py.newCode(1, var2, var1, "flushheaders", 117, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ctype", "plist", "prefix", "name", "value"};
      startbody$5 = Py.newCode(4, var2, var1, "startbody", 128, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "subtype", "boundary", "plist", "prefix"};
      startmultipartbody$6 = Py.newCode(5, var2, var1, "startmultipartbody", 145, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      nextpart$7 = Py.newCode(1, var2, var1, "nextpart", 163, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      lastpart$8 = Py.newCode(1, var2, var1, "lastpart", 176, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new MimeWriter$py("MimeWriter$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(MimeWriter$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.MimeWriter$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.addheader$3(var2, var3);
         case 4:
            return this.flushheaders$4(var2, var3);
         case 5:
            return this.startbody$5(var2, var3);
         case 6:
            return this.startmultipartbody$6(var2, var3);
         case 7:
            return this.nextpart$7(var2, var3);
         case 8:
            return this.lastpart$8(var2, var3);
         default:
            return null;
      }
   }
}
