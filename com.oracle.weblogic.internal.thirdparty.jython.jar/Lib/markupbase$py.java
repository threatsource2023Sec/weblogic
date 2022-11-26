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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("markupbase.py")
public class markupbase$py extends PyFunctionTable implements PyRunnable {
   static markupbase$py self;
   static final PyCode f$0;
   static final PyCode ParserBase$1;
   static final PyCode __init__$2;
   static final PyCode error$3;
   static final PyCode reset$4;
   static final PyCode getpos$5;
   static final PyCode updatepos$6;
   static final PyCode parse_declaration$7;
   static final PyCode parse_marked_section$8;
   static final PyCode parse_comment$9;
   static final PyCode _parse_doctype_subset$10;
   static final PyCode _parse_doctype_element$11;
   static final PyCode _parse_doctype_attlist$12;
   static final PyCode _parse_doctype_notation$13;
   static final PyCode _parse_doctype_entity$14;
   static final PyCode _scan_name$15;
   static final PyCode unknown_decl$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Shared support for scanning document type declarations in HTML and XHTML.\n\nThis module is used as a foundation for the HTMLParser and sgmllib\nmodules (indirectly, for htmllib as well).  It has no documented\npublic API and should not be used directly.\n\n"));
      var1.setline(7);
      PyString.fromInterned("Shared support for scanning document type declarations in HTML and XHTML.\n\nThis module is used as a foundation for the HTMLParser and sgmllib\nmodules (indirectly, for htmllib as well).  It has no documented\npublic API and should not be used directly.\n\n");
      var1.setline(9);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(11);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[a-zA-Z][-_.a-zA-Z0-9]*\\s*")).__getattr__("match");
      var1.setlocal("_declname_match", var3);
      var3 = null;
      var1.setline(12);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\'[^\\']*\\'|\"[^\"]*\")\\s*")).__getattr__("match");
      var1.setlocal("_declstringlit_match", var3);
      var3 = null;
      var1.setline(13);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--\\s*>"));
      var1.setlocal("_commentclose", var3);
      var3 = null;
      var1.setline(14);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("]\\s*]\\s*>"));
      var1.setlocal("_markedsectionclose", var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("]\\s*>"));
      var1.setlocal("_msmarkedsectionclose", var3);
      var3 = null;
      var1.setline(21);
      var1.dellocal("re");
      var1.setline(24);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("ParserBase", var5, ParserBase$1);
      var1.setlocal("ParserBase", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ParserBase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Parser base class which provides some common support methods used\n    by the SGML/HTML and XHTML parsers."));
      var1.setline(26);
      PyString.fromInterned("Parser base class which provides some common support methods used\n    by the SGML/HTML and XHTML parsers.");
      var1.setline(28);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$3, (PyObject)null);
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$4, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(41);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getpos$5, PyString.fromInterned("Return current line number and offset."));
      var1.setlocal("getpos", var4);
      var3 = null;
      var1.setline(49);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, updatepos$6, (PyObject)null);
      var1.setlocal("updatepos", var4);
      var3 = null;
      var1.setline(62);
      PyString var5 = PyString.fromInterned("");
      var1.setlocal("_decl_otherchars", var5);
      var3 = null;
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse_declaration$7, (PyObject)null);
      var1.setlocal("parse_declaration", var4);
      var3 = null;
      var1.setline(147);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, parse_marked_section$8, (PyObject)null);
      var1.setlocal("parse_marked_section", var4);
      var3 = null;
      var1.setline(169);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, parse_comment$9, (PyObject)null);
      var1.setlocal("parse_comment", var4);
      var3 = null;
      var1.setline(183);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse_doctype_subset$10, (PyObject)null);
      var1.setlocal("_parse_doctype_subset", var4);
      var3 = null;
      var1.setline(250);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse_doctype_element$11, (PyObject)null);
      var1.setlocal("_parse_doctype_element", var4);
      var3 = null;
      var1.setline(261);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse_doctype_attlist$12, (PyObject)null);
      var1.setlocal("_parse_doctype_attlist", var4);
      var3 = null;
      var1.setline(318);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse_doctype_notation$13, (PyObject)null);
      var1.setlocal("_parse_doctype_notation", var4);
      var3 = null;
      var1.setline(341);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse_doctype_entity$14, (PyObject)null);
      var1.setlocal("_parse_doctype_entity", var4);
      var3 = null;
      var1.setline(377);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _scan_name$15, (PyObject)null);
      var1.setlocal("_scan_name", var4);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unknown_decl$16, (PyObject)null);
      var1.setlocal("unknown_decl", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10000 = var3._is(var1.getglobal("ParserBase"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(30);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("markupbase.ParserBase must be subclassed")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject error$3(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("subclasses of ParserBase must override error()")));
   }

   public PyObject reset$4(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"lineno", var3);
      var3 = null;
      var1.setline(39);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"offset", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getpos$5(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyString.fromInterned("Return current line number and offset.");
      var1.setline(43);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("lineno"), var1.getlocal(0).__getattr__("offset")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject updatepos$6(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(51);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(52);
         PyObject var4 = var1.getlocal(0).__getattr__("rawdata");
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(53);
         var4 = var1.getlocal(3).__getattr__("count").__call__((ThreadState)var2, PyString.fromInterned("\n"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(2));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(54);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(55);
            var4 = var1.getlocal(0).__getattr__("lineno")._add(var1.getlocal(4));
            var1.getlocal(0).__setattr__("lineno", var4);
            var4 = null;
            var1.setline(56);
            var4 = var1.getlocal(3).__getattr__("rindex").__call__((ThreadState)var2, PyString.fromInterned("\n"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(2));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(57);
            var4 = var1.getlocal(2)._sub(var1.getlocal(5)._add(Py.newInteger(1)));
            var1.getlocal(0).__setattr__("offset", var4);
            var4 = null;
         } else {
            var1.setline(59);
            var4 = var1.getlocal(0).__getattr__("offset")._add(var1.getlocal(2))._sub(var1.getlocal(1));
            var1.getlocal(0).__setattr__("offset", var4);
            var4 = null;
         }

         var1.setline(60);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parse_declaration$7(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getlocal(1)._add(Py.newInteger(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(78);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(2).__getslice__(var1.getlocal(1), var1.getlocal(3), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("<!"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("unexpected call to parse_declaration"));
         }
      }

      var1.setline(79);
      var3 = var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned(">"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(81);
         var3 = var1.getlocal(3)._add(Py.newInteger(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(82);
         PyObject var4 = var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null);
         var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("-"), PyString.fromInterned("")}));
         var4 = null;
         PyInteger var7;
         if (var10000.__nonzero__()) {
            var1.setline(85);
            var7 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(87);
            var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(88);
            var4 = var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(3)._add(Py.newInteger(2)), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("--"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(90);
               var3 = var1.getlocal(0).__getattr__("parse_comment").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(91);
               var4 = var1.getlocal(2).__getitem__(var1.getlocal(3));
               var10000 = var4._eq(PyString.fromInterned("["));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(96);
                  var3 = var1.getlocal(0).__getattr__("parse_marked_section").__call__(var2, var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(98);
                  var4 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(3), var1.getlocal(1));
                  PyObject[] var5 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var5[0];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(3, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(99);
                  var4 = var1.getlocal(3);
                  var10000 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(100);
                     var3 = var1.getlocal(3);
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(101);
                     var4 = var1.getlocal(5);
                     var10000 = var4._eq(PyString.fromInterned("doctype"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(102);
                        PyString var8 = PyString.fromInterned("");
                        var1.getlocal(0).__setattr__((String)"_decl_otherchars", var8);
                        var4 = null;
                     }

                     do {
                        var1.setline(103);
                        var4 = var1.getlocal(3);
                        var10000 = var4._lt(var1.getlocal(4));
                        var4 = null;
                        if (!var10000.__nonzero__()) {
                           var1.setline(143);
                           var7 = Py.newInteger(-1);
                           var1.f_lasti = -1;
                           return var7;
                        }

                        var1.setline(104);
                        var4 = var1.getlocal(2).__getitem__(var1.getlocal(3));
                        var1.setlocal(6, var4);
                        var4 = null;
                        var1.setline(105);
                        var4 = var1.getlocal(6);
                        var10000 = var4._eq(PyString.fromInterned(">"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(107);
                           var4 = var1.getlocal(2).__getslice__(var1.getlocal(1)._add(Py.newInteger(2)), var1.getlocal(3), (PyObject)null);
                           var1.setlocal(7, var4);
                           var4 = null;
                           var1.setline(108);
                           var4 = var1.getlocal(5);
                           var10000 = var4._eq(PyString.fromInterned("doctype"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(109);
                              var1.getlocal(0).__getattr__("handle_decl").__call__(var2, var1.getlocal(7));
                           } else {
                              var1.setline(115);
                              var1.getlocal(0).__getattr__("unknown_decl").__call__(var2, var1.getlocal(7));
                           }

                           var1.setline(116);
                           var3 = var1.getlocal(3)._add(Py.newInteger(1));
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setline(117);
                        var4 = var1.getlocal(6);
                        var10000 = var4._in(PyString.fromInterned("\"'"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(118);
                           var4 = var1.getglobal("_declstringlit_match").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                           var1.setlocal(8, var4);
                           var4 = null;
                           var1.setline(119);
                           if (var1.getlocal(8).__not__().__nonzero__()) {
                              var1.setline(120);
                              var7 = Py.newInteger(-1);
                              var1.f_lasti = -1;
                              return var7;
                           }

                           var1.setline(121);
                           var4 = var1.getlocal(8).__getattr__("end").__call__(var2);
                           var1.setlocal(3, var4);
                           var4 = null;
                        } else {
                           var1.setline(122);
                           var4 = var1.getlocal(6);
                           var10000 = var4._in(PyString.fromInterned("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(123);
                              var4 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(3), var1.getlocal(1));
                              var5 = Py.unpackSequence(var4, 2);
                              var6 = var5[0];
                              var1.setlocal(9, var6);
                              var6 = null;
                              var6 = var5[1];
                              var1.setlocal(3, var6);
                              var6 = null;
                              var4 = null;
                           } else {
                              var1.setline(124);
                              var4 = var1.getlocal(6);
                              var10000 = var4._in(var1.getlocal(0).__getattr__("_decl_otherchars"));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(125);
                                 var4 = var1.getlocal(3)._add(Py.newInteger(1));
                                 var1.setlocal(3, var4);
                                 var4 = null;
                              } else {
                                 var1.setline(126);
                                 var4 = var1.getlocal(6);
                                 var10000 = var4._eq(PyString.fromInterned("["));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(128);
                                    var4 = var1.getlocal(5);
                                    var10000 = var4._eq(PyString.fromInterned("doctype"));
                                    var4 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(129);
                                       var4 = var1.getlocal(0).__getattr__("_parse_doctype_subset").__call__(var2, var1.getlocal(3)._add(Py.newInteger(1)), var1.getlocal(1));
                                       var1.setlocal(3, var4);
                                       var4 = null;
                                    } else {
                                       var1.setline(130);
                                       var4 = var1.getlocal(5);
                                       var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("attlist"), PyString.fromInterned("linktype"), PyString.fromInterned("link"), PyString.fromInterned("element")}));
                                       var4 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(135);
                                          var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("unsupported '[' char in %s declaration")._mod(var1.getlocal(5)));
                                       } else {
                                          var1.setline(137);
                                          var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected '[' char in declaration"));
                                       }
                                    }
                                 } else {
                                    var1.setline(139);
                                    var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("unexpected %r char in declaration")._mod(var1.getlocal(2).__getitem__(var1.getlocal(3))));
                                 }
                              }
                           }
                        }

                        var1.setline(141);
                        var4 = var1.getlocal(3);
                        var10000 = var4._lt(Py.newInteger(0));
                        var4 = null;
                     } while(!var10000.__nonzero__());

                     var1.setline(142);
                     var3 = var1.getlocal(3);
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      }
   }

   public PyObject parse_marked_section$8(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(149);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(3).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(3)), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("<!["));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("unexpected call to parse_marked_section()"));
         }
      }

      var1.setline(150);
      var3 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(1)._add(Py.newInteger(3)), var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(151);
      var3 = var1.getlocal(5);
      var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(152);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(153);
         PyObject var6 = var1.getlocal(4);
         var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned("temp"), PyString.fromInterned("cdata"), PyString.fromInterned("ignore"), PyString.fromInterned("include"), PyString.fromInterned("rcdata")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(155);
            var6 = var1.getglobal("_markedsectionclose").__getattr__("search").__call__(var2, var1.getlocal(3), var1.getlocal(1)._add(Py.newInteger(3)));
            var1.setlocal(6, var6);
            var4 = null;
         } else {
            var1.setline(156);
            var6 = var1.getlocal(4);
            var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned("if"), PyString.fromInterned("else"), PyString.fromInterned("endif")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(158);
               var6 = var1.getglobal("_msmarkedsectionclose").__getattr__("search").__call__(var2, var1.getlocal(3), var1.getlocal(1)._add(Py.newInteger(3)));
               var1.setlocal(6, var6);
               var4 = null;
            } else {
               var1.setline(160);
               var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("unknown status keyword %r in marked section")._mod(var1.getlocal(3).__getslice__(var1.getlocal(1)._add(Py.newInteger(3)), var1.getlocal(5), (PyObject)null)));
            }
         }

         var1.setline(161);
         if (var1.getlocal(6).__not__().__nonzero__()) {
            var1.setline(162);
            PyInteger var7 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var7;
         } else {
            var1.setline(163);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(164);
               var6 = var1.getlocal(6).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var1.setlocal(5, var6);
               var4 = null;
               var1.setline(165);
               var1.getlocal(0).__getattr__("unknown_decl").__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(1)._add(Py.newInteger(3)), var1.getlocal(5), (PyObject)null));
            }

            var1.setline(166);
            var3 = var1.getlocal(6).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject parse_comment$9(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(171);
      var3 = var1.getlocal(3).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(4)), (PyObject)null);
      PyObject var10000 = var3._ne(PyString.fromInterned("<!--"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(172);
         var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected call to parse_comment()"));
      }

      var1.setline(173);
      var3 = var1.getglobal("_commentclose").__getattr__("search").__call__(var2, var1.getlocal(3), var1.getlocal(1)._add(Py.newInteger(4)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(174);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(175);
         PyInteger var5 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(176);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(177);
            PyObject var4 = var1.getlocal(4).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(178);
            var1.getlocal(0).__getattr__("handle_comment").__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(1)._add(Py.newInteger(4)), var1.getlocal(5), (PyObject)null));
         }

         var1.setline(179);
         var3 = var1.getlocal(4).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _parse_doctype_subset$10(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(185);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(186);
      var3 = var1.getlocal(1);
      var1.setlocal(5, var3);
      var3 = null;

      while(true) {
         while(true) {
            var1.setline(187);
            PyObject var4 = var1.getlocal(5);
            PyObject var10000 = var4._lt(var1.getlocal(4));
            var4 = null;
            PyInteger var8;
            if (!var10000.__nonzero__()) {
               var1.setline(247);
               var8 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var8;
            }

            var1.setline(188);
            var3 = var1.getlocal(3).__getitem__(var1.getlocal(5));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(189);
            var3 = var1.getlocal(6);
            var10000 = var3._eq(PyString.fromInterned("<"));
            var3 = null;
            PyObject[] var5;
            PyObject var6;
            if (var10000.__nonzero__()) {
               var1.setline(190);
               var3 = var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(2)), (PyObject)null);
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(191);
               var3 = var1.getlocal(7);
               var10000 = var3._eq(PyString.fromInterned("<"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(193);
                  var8 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setline(194);
               var4 = var1.getlocal(7);
               var10000 = var4._ne(PyString.fromInterned("<!"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(195);
                  var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(2), var1.getlocal(5)._add(Py.newInteger(1)));
                  var1.setline(196);
                  var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("unexpected char in internal subset (in %r)")._mod(var1.getlocal(7)));
               }

               var1.setline(197);
               var4 = var1.getlocal(5)._add(Py.newInteger(2));
               var10000 = var4._eq(var1.getlocal(4));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(199);
                  var8 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setline(200);
               var4 = var1.getlocal(5)._add(Py.newInteger(4));
               var10000 = var4._gt(var1.getlocal(4));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(202);
                  var8 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setline(203);
               var4 = var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(4)), (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("<!--"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(204);
                  var10000 = var1.getlocal(0).__getattr__("parse_comment");
                  PyObject[] var9 = new PyObject[]{var1.getlocal(5), Py.newInteger(0)};
                  String[] var7 = new String[]{"report"};
                  var10000 = var10000.__call__(var2, var9, var7);
                  var4 = null;
                  var4 = var10000;
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(205);
                  var4 = var1.getlocal(5);
                  var10000 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(206);
                     var3 = var1.getlocal(5);
                     var1.f_lasti = -1;
                     return var3;
                  }
               } else {
                  var1.setline(208);
                  var4 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(5)._add(Py.newInteger(2)), var1.getlocal(2));
                  var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(8, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(209);
                  var4 = var1.getlocal(5);
                  var10000 = var4._eq(Py.newInteger(-1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(210);
                     var8 = Py.newInteger(-1);
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setline(211);
                  var4 = var1.getlocal(8);
                  var10000 = var4._notin(new PyTuple(new PyObject[]{PyString.fromInterned("attlist"), PyString.fromInterned("element"), PyString.fromInterned("entity"), PyString.fromInterned("notation")}));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(212);
                     var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(2), var1.getlocal(5)._add(Py.newInteger(2)));
                     var1.setline(213);
                     var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("unknown declaration %r in internal subset")._mod(var1.getlocal(8)));
                  }

                  var1.setline(216);
                  var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), PyString.fromInterned("_parse_doctype_")._add(var1.getlocal(8)));
                  var1.setlocal(9, var4);
                  var4 = null;
                  var1.setline(217);
                  var4 = var1.getlocal(9).__call__(var2, var1.getlocal(5), var1.getlocal(2));
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(218);
                  var4 = var1.getlocal(5);
                  var10000 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(219);
                     var3 = var1.getlocal(5);
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            } else {
               var1.setline(220);
               var4 = var1.getlocal(6);
               var10000 = var4._eq(PyString.fromInterned("%"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(222);
                  var4 = var1.getlocal(5)._add(Py.newInteger(1));
                  var10000 = var4._eq(var1.getlocal(4));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(224);
                     var8 = Py.newInteger(-1);
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setline(225);
                  var4 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(5)._add(Py.newInteger(1)), var1.getlocal(2));
                  var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(7, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(226);
                  var4 = var1.getlocal(5);
                  var10000 = var4._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(227);
                     var3 = var1.getlocal(5);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(228);
                  var4 = var1.getlocal(3).__getitem__(var1.getlocal(5));
                  var10000 = var4._eq(PyString.fromInterned(";"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(229);
                     var4 = var1.getlocal(5)._add(Py.newInteger(1));
                     var1.setlocal(5, var4);
                     var4 = null;
                  }
               } else {
                  var1.setline(230);
                  var4 = var1.getlocal(6);
                  var10000 = var4._eq(PyString.fromInterned("]"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(231);
                     var4 = var1.getlocal(5)._add(Py.newInteger(1));
                     var1.setlocal(5, var4);
                     var4 = null;

                     while(true) {
                        var1.setline(232);
                        var4 = var1.getlocal(5);
                        var10000 = var4._lt(var1.getlocal(4));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(3).__getitem__(var1.getlocal(5)).__getattr__("isspace").__call__(var2);
                        }

                        if (!var10000.__nonzero__()) {
                           var1.setline(234);
                           var4 = var1.getlocal(5);
                           var10000 = var4._lt(var1.getlocal(4));
                           var4 = null;
                           if (!var10000.__nonzero__()) {
                              var1.setline(240);
                              var8 = Py.newInteger(-1);
                              var1.f_lasti = -1;
                              return var8;
                           }

                           var1.setline(235);
                           var4 = var1.getlocal(3).__getitem__(var1.getlocal(5));
                           var10000 = var4._eq(PyString.fromInterned(">"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(236);
                              var3 = var1.getlocal(5);
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setline(237);
                           var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(2), var1.getlocal(5));
                           var1.setline(238);
                           var1.getlocal(0).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected char after internal subset"));
                           break;
                        }

                        var1.setline(233);
                        var4 = var1.getlocal(5)._add(Py.newInteger(1));
                        var1.setlocal(5, var4);
                        var4 = null;
                     }
                  } else {
                     var1.setline(241);
                     if (var1.getlocal(6).__getattr__("isspace").__call__(var2).__nonzero__()) {
                        var1.setline(242);
                        var4 = var1.getlocal(5)._add(Py.newInteger(1));
                        var1.setlocal(5, var4);
                        var4 = null;
                     } else {
                        var1.setline(244);
                        var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(2), var1.getlocal(5));
                        var1.setline(245);
                        var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("unexpected char %r in internal subset")._mod(var1.getlocal(6)));
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject _parse_doctype_element$11(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(252);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      PyInteger var7;
      if (var10000.__nonzero__()) {
         var1.setline(253);
         var7 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(255);
         PyObject var6 = var1.getlocal(0).__getattr__("rawdata");
         var1.setlocal(5, var6);
         var4 = null;
         var1.setline(256);
         PyString var8 = PyString.fromInterned(">");
         var10000 = var8._in(var1.getlocal(5).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(257);
            var3 = var1.getlocal(5).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"), (PyObject)var1.getlocal(4))._add(Py.newInteger(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(258);
            var7 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var7;
         }
      }
   }

   public PyObject _parse_doctype_attlist$12(PyFrame var1, ThreadState var2) {
      var1.setline(262);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(263);
      var3 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(264);
      var3 = var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(265);
      var3 = var1.getlocal(6);
      PyObject var10000 = var3._eq(PyString.fromInterned(""));
      var3 = null;
      PyInteger var8;
      if (var10000.__nonzero__()) {
         var1.setline(266);
         var8 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(267);
         PyObject var7 = var1.getlocal(6);
         var10000 = var7._eq(PyString.fromInterned(">"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(268);
            var3 = var1.getlocal(5)._add(Py.newInteger(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            do {
               var1.setline(269);
               if (!Py.newInteger(1).__nonzero__()) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(272);
               var7 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(5), var1.getlocal(2));
               PyObject[] var9 = Py.unpackSequence(var7, 2);
               PyObject var6 = var9[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var9[1];
               var1.setlocal(5, var6);
               var6 = null;
               var4 = null;
               var1.setline(273);
               var7 = var1.getlocal(5);
               var10000 = var7._lt(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(274);
                  var3 = var1.getlocal(5);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(275);
               var7 = var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
               var1.setlocal(6, var7);
               var4 = null;
               var1.setline(276);
               var7 = var1.getlocal(6);
               var10000 = var7._eq(PyString.fromInterned(""));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(277);
                  var8 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setline(278);
               var7 = var1.getlocal(6);
               var10000 = var7._eq(PyString.fromInterned("("));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(280);
                  PyString var10 = PyString.fromInterned(")");
                  var10000 = var10._in(var1.getlocal(3).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(283);
                     var8 = Py.newInteger(-1);
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setline(281);
                  var7 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")"), (PyObject)var1.getlocal(5))._add(Py.newInteger(1));
                  var1.setlocal(5, var7);
                  var4 = null;

                  while(true) {
                     var1.setline(284);
                     if (!var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null).__getattr__("isspace").__call__(var2).__nonzero__()) {
                        var1.setline(286);
                        if (var1.getlocal(3).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null).__not__().__nonzero__()) {
                           var1.setline(288);
                           var8 = Py.newInteger(-1);
                           var1.f_lasti = -1;
                           return var8;
                        }
                        break;
                     }

                     var1.setline(285);
                     var7 = var1.getlocal(5)._add(Py.newInteger(1));
                     var1.setlocal(5, var7);
                     var4 = null;
                  }
               } else {
                  var1.setline(290);
                  var7 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(5), var1.getlocal(2));
                  var9 = Py.unpackSequence(var7, 2);
                  var6 = var9[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var9[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var4 = null;
               }

               var1.setline(291);
               var7 = var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
               var1.setlocal(6, var7);
               var4 = null;
               var1.setline(292);
               if (var1.getlocal(6).__not__().__nonzero__()) {
                  var1.setline(293);
                  var8 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setline(294);
               var7 = var1.getlocal(6);
               var10000 = var7._in(PyString.fromInterned("'\""));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(295);
                  var7 = var1.getglobal("_declstringlit_match").__call__(var2, var1.getlocal(3), var1.getlocal(5));
                  var1.setlocal(7, var7);
                  var4 = null;
                  var1.setline(296);
                  if (!var1.getlocal(7).__nonzero__()) {
                     var1.setline(299);
                     var8 = Py.newInteger(-1);
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setline(297);
                  var7 = var1.getlocal(7).__getattr__("end").__call__(var2);
                  var1.setlocal(5, var7);
                  var4 = null;
                  var1.setline(300);
                  var7 = var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
                  var1.setlocal(6, var7);
                  var4 = null;
                  var1.setline(301);
                  if (var1.getlocal(6).__not__().__nonzero__()) {
                     var1.setline(302);
                     var8 = Py.newInteger(-1);
                     var1.f_lasti = -1;
                     return var8;
                  }
               }

               var1.setline(303);
               var7 = var1.getlocal(6);
               var10000 = var7._eq(PyString.fromInterned("#"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(304);
                  var7 = var1.getlocal(3).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null);
                  var10000 = var7._eq(PyString.fromInterned("#"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(306);
                     var8 = Py.newInteger(-1);
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setline(307);
                  var7 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(5)._add(Py.newInteger(1)), var1.getlocal(2));
                  var9 = Py.unpackSequence(var7, 2);
                  var6 = var9[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var9[1];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(308);
                  var7 = var1.getlocal(5);
                  var10000 = var7._lt(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(309);
                     var3 = var1.getlocal(5);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(310);
                  var7 = var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null);
                  var1.setlocal(6, var7);
                  var4 = null;
                  var1.setline(311);
                  if (var1.getlocal(6).__not__().__nonzero__()) {
                     var1.setline(312);
                     var8 = Py.newInteger(-1);
                     var1.f_lasti = -1;
                     return var8;
                  }
               }

               var1.setline(313);
               var7 = var1.getlocal(6);
               var10000 = var7._eq(PyString.fromInterned(">"));
               var4 = null;
            } while(!var10000.__nonzero__());

            var1.setline(315);
            var3 = var1.getlocal(5)._add(Py.newInteger(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _parse_doctype_notation$13(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyObject var3 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(320);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(321);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(322);
         PyObject var7 = var1.getlocal(0).__getattr__("rawdata");
         var1.setlocal(5, var7);
         var4 = null;

         while(true) {
            var1.setline(323);
            if (!Py.newInteger(1).__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(324);
            var7 = var1.getlocal(5).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null);
            var1.setlocal(6, var7);
            var4 = null;
            var1.setline(325);
            PyInteger var8;
            if (var1.getlocal(6).__not__().__nonzero__()) {
               var1.setline(327);
               var8 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var8;
            }

            var1.setline(328);
            var7 = var1.getlocal(6);
            var10000 = var7._eq(PyString.fromInterned(">"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(329);
               var3 = var1.getlocal(4)._add(Py.newInteger(1));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(330);
            var7 = var1.getlocal(6);
            var10000 = var7._in(PyString.fromInterned("'\""));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(331);
               var7 = var1.getglobal("_declstringlit_match").__call__(var2, var1.getlocal(5), var1.getlocal(4));
               var1.setlocal(7, var7);
               var4 = null;
               var1.setline(332);
               if (var1.getlocal(7).__not__().__nonzero__()) {
                  var1.setline(333);
                  var8 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setline(334);
               var7 = var1.getlocal(7).__getattr__("end").__call__(var2);
               var1.setlocal(4, var7);
               var4 = null;
            } else {
               var1.setline(336);
               var7 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(4), var1.getlocal(2));
               PyObject[] var9 = Py.unpackSequence(var7, 2);
               PyObject var6 = var9[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var9[1];
               var1.setlocal(4, var6);
               var6 = null;
               var4 = null;
               var1.setline(337);
               var7 = var1.getlocal(4);
               var10000 = var7._lt(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(338);
                  var3 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject _parse_doctype_entity$14(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(343);
      var3 = var1.getlocal(3).__getslice__(var1.getlocal(1), var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("%"));
      var3 = null;
      PyObject var4;
      PyInteger var7;
      if (var10000.__nonzero__()) {
         var1.setline(344);
         var3 = var1.getlocal(1)._add(Py.newInteger(1));
         var1.setlocal(4, var3);
         var3 = null;

         while(true) {
            var1.setline(345);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(346);
            var3 = var1.getlocal(3).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(347);
            if (var1.getlocal(5).__not__().__nonzero__()) {
               var1.setline(348);
               var7 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(349);
            if (!var1.getlocal(5).__getattr__("isspace").__call__(var2).__nonzero__()) {
               break;
            }

            var1.setline(350);
            var4 = var1.getlocal(4)._add(Py.newInteger(1));
            var1.setlocal(4, var4);
            var4 = null;
         }
      } else {
         var1.setline(354);
         var4 = var1.getlocal(1);
         var1.setlocal(4, var4);
         var4 = null;
      }

      var1.setline(355);
      var4 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(4), var1.getlocal(2));
      PyObject[] var5 = Py.unpackSequence(var4, 2);
      PyObject var6 = var5[0];
      var1.setlocal(6, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(4, var6);
      var6 = null;
      var4 = null;
      var1.setline(356);
      var4 = var1.getlocal(4);
      var10000 = var4._lt(Py.newInteger(0));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(357);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      } else {
         while(true) {
            var1.setline(358);
            if (!Py.newInteger(1).__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(359);
            var4 = var1.getlocal(0).__getattr__("rawdata").__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(360);
            if (var1.getlocal(5).__not__().__nonzero__()) {
               var1.setline(361);
               var7 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(362);
            var4 = var1.getlocal(5);
            var10000 = var4._in(PyString.fromInterned("'\""));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(363);
               var4 = var1.getglobal("_declstringlit_match").__call__(var2, var1.getlocal(3), var1.getlocal(4));
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(364);
               if (!var1.getlocal(7).__nonzero__()) {
                  var1.setline(367);
                  var7 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var7;
               }

               var1.setline(365);
               var4 = var1.getlocal(7).__getattr__("end").__call__(var2);
               var1.setlocal(4, var4);
               var4 = null;
            } else {
               var1.setline(368);
               var4 = var1.getlocal(5);
               var10000 = var4._eq(PyString.fromInterned(">"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(369);
                  var3 = var1.getlocal(4)._add(Py.newInteger(1));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(371);
               var4 = var1.getlocal(0).__getattr__("_scan_name").__call__(var2, var1.getlocal(4), var1.getlocal(2));
               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var4 = null;
               var1.setline(372);
               var4 = var1.getlocal(4);
               var10000 = var4._lt(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(373);
                  var3 = var1.getlocal(4);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject _scan_name$15(PyFrame var1, ThreadState var2) {
      var1.setline(378);
      PyObject var3 = var1.getlocal(0).__getattr__("rawdata");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(379);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(380);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getlocal(4));
      var3 = null;
      PyTuple var5;
      if (var10000.__nonzero__()) {
         var1.setline(381);
         var5 = new PyTuple(new PyObject[]{var1.getglobal("None"), Py.newInteger(-1)});
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(382);
         PyObject var4 = var1.getglobal("_declname_match").__call__(var2, var1.getlocal(3), var1.getlocal(1));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(383);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(384);
            var4 = var1.getlocal(5).__getattr__("group").__call__(var2);
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(385);
            var4 = var1.getlocal(6).__getattr__("strip").__call__(var2);
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(386);
            var4 = var1.getlocal(1)._add(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
            var10000 = var4._eq(var1.getlocal(4));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(387);
               var5 = new PyTuple(new PyObject[]{var1.getglobal("None"), Py.newInteger(-1)});
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(388);
               var5 = new PyTuple(new PyObject[]{var1.getlocal(7).__getattr__("lower").__call__(var2), var1.getlocal(5).__getattr__("end").__call__(var2)});
               var1.f_lasti = -1;
               return var5;
            }
         } else {
            var1.setline(390);
            var1.getlocal(0).__getattr__("updatepos").__call__(var2, var1.getlocal(2), var1.getlocal(1));
            var1.setline(391);
            var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("expected name token at %r")._mod(var1.getlocal(3).__getslice__(var1.getlocal(2), var1.getlocal(2)._add(Py.newInteger(20)), (PyObject)null)));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject unknown_decl$16(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      var1.f_lasti = -1;
      return Py.None;
   }

   public markupbase$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ParserBase$1 = Py.newCode(0, var2, var1, "ParserBase", 24, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$2 = Py.newCode(1, var2, var1, "__init__", 28, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      error$3 = Py.newCode(2, var2, var1, "error", 33, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$4 = Py.newCode(1, var2, var1, "reset", 37, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getpos$5 = Py.newCode(1, var2, var1, "getpos", 41, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j", "rawdata", "nlines", "pos"};
      updatepos$6 = Py.newCode(3, var2, var1, "updatepos", 49, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "rawdata", "j", "n", "decltype", "c", "data", "m", "name"};
      parse_declaration$7 = Py.newCode(2, var2, var1, "parse_declaration", 65, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "report", "rawdata", "sectName", "j", "match"};
      parse_marked_section$8 = Py.newCode(3, var2, var1, "parse_marked_section", 147, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "report", "rawdata", "match", "j"};
      parse_comment$9 = Py.newCode(3, var2, var1, "parse_comment", 169, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "declstartpos", "rawdata", "n", "j", "c", "s", "name", "meth"};
      _parse_doctype_subset$10 = Py.newCode(3, var2, var1, "_parse_doctype_subset", 183, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "declstartpos", "name", "j", "rawdata"};
      _parse_doctype_element$11 = Py.newCode(3, var2, var1, "_parse_doctype_element", 250, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "declstartpos", "rawdata", "name", "j", "c", "m"};
      _parse_doctype_attlist$12 = Py.newCode(3, var2, var1, "_parse_doctype_attlist", 261, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "declstartpos", "name", "j", "rawdata", "c", "m"};
      _parse_doctype_notation$13 = Py.newCode(3, var2, var1, "_parse_doctype_notation", 318, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "declstartpos", "rawdata", "j", "c", "name", "m"};
      _parse_doctype_entity$14 = Py.newCode(3, var2, var1, "_parse_doctype_entity", 341, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "declstartpos", "rawdata", "n", "m", "s", "name"};
      _scan_name$15 = Py.newCode(3, var2, var1, "_scan_name", 377, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      unknown_decl$16 = Py.newCode(2, var2, var1, "unknown_decl", 395, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new markupbase$py("markupbase$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(markupbase$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ParserBase$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.error$3(var2, var3);
         case 4:
            return this.reset$4(var2, var3);
         case 5:
            return this.getpos$5(var2, var3);
         case 6:
            return this.updatepos$6(var2, var3);
         case 7:
            return this.parse_declaration$7(var2, var3);
         case 8:
            return this.parse_marked_section$8(var2, var3);
         case 9:
            return this.parse_comment$9(var2, var3);
         case 10:
            return this._parse_doctype_subset$10(var2, var3);
         case 11:
            return this._parse_doctype_element$11(var2, var3);
         case 12:
            return this._parse_doctype_attlist$12(var2, var3);
         case 13:
            return this._parse_doctype_notation$13(var2, var3);
         case 14:
            return this._parse_doctype_entity$14(var2, var3);
         case 15:
            return this._scan_name$15(var2, var3);
         case 16:
            return this.unknown_decl$16(var2, var3);
         default:
            return null;
      }
   }
}
