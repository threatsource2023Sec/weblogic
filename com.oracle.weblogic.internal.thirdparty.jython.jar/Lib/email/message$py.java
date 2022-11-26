package email;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
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
@Filename("email/message.py")
public class message$py extends PyFunctionTable implements PyRunnable {
   static message$py self;
   static final PyCode f$0;
   static final PyCode _splitparam$1;
   static final PyCode _formatparam$2;
   static final PyCode _parseparam$3;
   static final PyCode _unquotevalue$4;
   static final PyCode Message$5;
   static final PyCode __init__$6;
   static final PyCode __str__$7;
   static final PyCode as_string$8;
   static final PyCode is_multipart$9;
   static final PyCode set_unixfrom$10;
   static final PyCode get_unixfrom$11;
   static final PyCode attach$12;
   static final PyCode get_payload$13;
   static final PyCode set_payload$14;
   static final PyCode set_charset$15;
   static final PyCode get_charset$16;
   static final PyCode __len__$17;
   static final PyCode __getitem__$18;
   static final PyCode __setitem__$19;
   static final PyCode __delitem__$20;
   static final PyCode __contains__$21;
   static final PyCode has_key$22;
   static final PyCode keys$23;
   static final PyCode values$24;
   static final PyCode items$25;
   static final PyCode get$26;
   static final PyCode get_all$27;
   static final PyCode add_header$28;
   static final PyCode replace_header$29;
   static final PyCode get_content_type$30;
   static final PyCode get_content_maintype$31;
   static final PyCode get_content_subtype$32;
   static final PyCode get_default_type$33;
   static final PyCode set_default_type$34;
   static final PyCode _get_params_preserve$35;
   static final PyCode get_params$36;
   static final PyCode get_param$37;
   static final PyCode set_param$38;
   static final PyCode del_param$39;
   static final PyCode set_type$40;
   static final PyCode get_filename$41;
   static final PyCode get_boundary$42;
   static final PyCode set_boundary$43;
   static final PyCode get_content_charset$44;
   static final PyCode get_charsets$45;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Basic message object for the email package object model."));
      var1.setline(5);
      PyString.fromInterned("Basic message object for the email package object model.");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Message")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(10);
      var5 = imp.importOne("uu", var1, -1);
      var1.setlocal("uu", var5);
      var3 = null;
      var1.setline(11);
      var5 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var5);
      var3 = null;
      var1.setline(12);
      var5 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var5);
      var3 = null;
      var1.setline(13);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(16);
      var5 = imp.importOne("email.charset", var1, -1);
      var1.setlocal("email", var5);
      var3 = null;
      var1.setline(17);
      var6 = new String[]{"utils"};
      var7 = imp.importFrom("email", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("utils", var4);
      var4 = null;
      var1.setline(18);
      var6 = new String[]{"errors"};
      var7 = imp.importFrom("email", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("errors", var4);
      var4 = null;
      var1.setline(20);
      PyString var8 = PyString.fromInterned("; ");
      var1.setlocal("SEMISPACE", var8);
      var3 = null;
      var1.setline(24);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[ \\(\\)<>@,;:\\\\\"/\\[\\]\\?=]"));
      var1.setlocal("tspecials", var5);
      var3 = null;
      var1.setline(28);
      var7 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var7, _splitparam$1, (PyObject)null);
      var1.setlocal("_splitparam", var9);
      var3 = null;
      var1.setline(38);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      var9 = new PyFunction(var1.f_globals, var7, _formatparam$2, PyString.fromInterned("Convenience function to format and return a key=value pair.\n\n    This will quote the value if needed or if quote is true.  If value is a\n    three tuple (charset, language, value), it will be encoded according\n    to RFC2231 rules.\n    "));
      var1.setlocal("_formatparam", var9);
      var3 = null;
      var1.setline(62);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, _parseparam$3, (PyObject)null);
      var1.setlocal("_parseparam", var9);
      var3 = null;
      var1.setline(80);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, _unquotevalue$4, (PyObject)null);
      var1.setlocal("_unquotevalue", var9);
      var3 = null;
      var1.setline(92);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Message", var7, Message$5);
      var1.setlocal("Message", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _splitparam$1(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getlocal(0).__getattr__("partition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
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
      var1.setline(34);
      PyTuple var6;
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(35);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("strip").__call__(var2), var1.getglobal("None")});
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(36);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("strip").__call__(var2), var1.getlocal(3).__getattr__("strip").__call__(var2)});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject _formatparam$2(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyString.fromInterned("Convenience function to format and return a key=value pair.\n\n    This will quote the value if needed or if quote is true.  If value is a\n    three tuple (charset, language, value), it will be encoded according\n    to RFC2231 rules.\n    ");
      var1.setline(45);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(49);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tuple")).__nonzero__()) {
            var1.setline(51);
            var3 = var1.getlocal(0);
            var3 = var3._iadd(PyString.fromInterned("*"));
            var1.setlocal(0, var3);
            var1.setline(52);
            var3 = var1.getglobal("utils").__getattr__("encode_rfc2231").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2)), var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(1)));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(55);
         var10000 = var1.getlocal(2);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("tspecials").__getattr__("search").__call__(var2, var1.getlocal(1));
         }

         if (var10000.__nonzero__()) {
            var1.setline(56);
            var3 = PyString.fromInterned("%s=\"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("utils").__getattr__("quote").__call__(var2, var1.getlocal(1))}));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(58);
            var3 = PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(60);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _parseparam$3(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(64);
         PyObject var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
         PyObject var10000 = var4._eq(PyString.fromInterned(";"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(77);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(65);
         var4 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var4);
         var3 = null;
         var1.setline(66);
         var4 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
         var1.setlocal(2, var4);
         var3 = null;

         while(true) {
            var1.setline(67);
            var4 = var1.getlocal(2);
            var10000 = var4._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("count").__call__((ThreadState)var2, PyString.fromInterned("\""), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(2))._sub(var1.getlocal(0).__getattr__("count").__call__((ThreadState)var2, PyString.fromInterned("\\\""), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(2)))._mod(Py.newInteger(2));
            }

            if (!var10000.__nonzero__()) {
               var1.setline(69);
               var4 = var1.getlocal(2);
               var10000 = var4._lt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(70);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
                  var1.setlocal(2, var4);
                  var3 = null;
               }

               var1.setline(71);
               var4 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null);
               var1.setlocal(3, var4);
               var3 = null;
               var1.setline(72);
               PyString var5 = PyString.fromInterned("=");
               var10000 = var5._in(var1.getlocal(3));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(73);
                  var4 = var1.getlocal(3).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
                  var1.setlocal(4, var4);
                  var3 = null;
                  var1.setline(74);
                  var4 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null).__getattr__("strip").__call__(var2).__getattr__("lower").__call__(var2)._add(PyString.fromInterned("="))._add(var1.getlocal(3).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null).__getattr__("strip").__call__(var2));
                  var1.setlocal(3, var4);
                  var3 = null;
               }

               var1.setline(75);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3).__getattr__("strip").__call__(var2));
               var1.setline(76);
               var4 = var1.getlocal(0).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
               var1.setlocal(0, var4);
               var3 = null;
               break;
            }

            var1.setline(68);
            var4 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"), (PyObject)var1.getlocal(2)._add(Py.newInteger(1)));
            var1.setlocal(2, var4);
            var3 = null;
         }
      }
   }

   public PyObject _unquotevalue$4(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("tuple")).__nonzero__()) {
         var1.setline(86);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getitem__(Py.newInteger(1)), var1.getglobal("utils").__getattr__("unquote").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(2)))});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(88);
         PyObject var3 = var1.getglobal("utils").__getattr__("unquote").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject Message$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Basic message object.\n\n    A message object is defined as something that has a bunch of RFC 2822\n    headers and a payload.  It may optionally have an envelope header\n    (a.k.a. Unix-From or From_ header).  If the message is a container (i.e. a\n    multipart or a message/rfc822), then the payload is a list of Message\n    objects, otherwise it is a string.\n\n    Message objects implement part of the `mapping' interface, which assumes\n    there is exactly one occurrence of the header per message.  Some headers\n    do in fact appear multiple times (e.g. Received) and for those headers,\n    you must use the explicit API to set or get all the headers.  Not all of\n    the mapping methods are implemented.\n    "));
      var1.setline(106);
      PyString.fromInterned("Basic message object.\n\n    A message object is defined as something that has a bunch of RFC 2822\n    headers and a payload.  It may optionally have an envelope header\n    (a.k.a. Unix-From or From_ header).  If the message is a container (i.e. a\n    multipart or a message/rfc822), then the payload is a list of Message\n    objects, otherwise it is a string.\n\n    Message objects implement part of the `mapping' interface, which assumes\n    there is exactly one occurrence of the header per message.  Some headers\n    do in fact appear multiple times (e.g. Received) and for those headers,\n    you must use the explicit API to set or get all the headers.  Not all of\n    the mapping methods are implemented.\n    ");
      var1.setline(107);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(118);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, __str__$7, PyString.fromInterned("Return the entire formatted message as a string.\n        This includes the headers, body, and envelope header.\n        "));
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(124);
      var3 = new PyObject[]{var1.getname("False")};
      var5 = new PyFunction(var1.f_globals, var3, as_string$8, PyString.fromInterned("Return the entire formatted message as a string.\n        Optional `unixfrom' when True, means include the Unix From_ envelope\n        header.\n\n        This is a convenience method and may not generate the message exactly\n        as you intend because by default it mangles lines that begin with\n        \"From \".  For more flexibility, use the flatten() method of a\n        Generator instance.\n        "));
      var1.setlocal("as_string", var5);
      var3 = null;
      var1.setline(140);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, is_multipart$9, PyString.fromInterned("Return True if the message consists of multiple parts."));
      var1.setlocal("is_multipart", var5);
      var3 = null;
      var1.setline(147);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, set_unixfrom$10, (PyObject)null);
      var1.setlocal("set_unixfrom", var5);
      var3 = null;
      var1.setline(150);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, get_unixfrom$11, (PyObject)null);
      var1.setlocal("get_unixfrom", var5);
      var3 = null;
      var1.setline(156);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, attach$12, PyString.fromInterned("Add the given payload to the current payload.\n\n        The current payload will always be a list of objects after this method\n        is called.  If you want to set the payload to a scalar object, use\n        set_payload() instead.\n        "));
      var1.setlocal("attach", var5);
      var3 = null;
      var1.setline(168);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      var5 = new PyFunction(var1.f_globals, var3, get_payload$13, PyString.fromInterned("Return a reference to the payload.\n\n        The payload will either be a list object or a string.  If you mutate\n        the list object, you modify the message's payload in place.  Optional\n        i returns that index into the payload.\n\n        Optional decode is a flag indicating whether the payload should be\n        decoded or not, according to the Content-Transfer-Encoding header\n        (default is False).\n\n        When True and the message is not a multipart, the payload will be\n        decoded if this header's value is `quoted-printable' or `base64'.  If\n        some other encoding is used, or the header is missing, or if the\n        payload has bogus data (i.e. bogus base64 or uuencoded data), the\n        payload is returned as-is.\n\n        If the message is a multipart and the decode flag is True, then None\n        is returned.\n        "));
      var1.setlocal("get_payload", var5);
      var3 = null;
      var1.setline(218);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, set_payload$14, PyString.fromInterned("Set the payload to the given value.\n\n        Optional charset sets the message's default character set.  See\n        set_charset() for details.\n        "));
      var1.setlocal("set_payload", var5);
      var3 = null;
      var1.setline(228);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, set_charset$15, PyString.fromInterned("Set the charset of the payload to a given character set.\n\n        charset can be a Charset instance, a string naming a character set, or\n        None.  If it is a string it will be converted to a Charset instance.\n        If charset is None, the charset parameter will be removed from the\n        Content-Type field.  Anything else will generate a TypeError.\n\n        The message will be assumed to be of type text/* encoded with\n        charset.input_charset.  It will be converted to charset.output_charset\n        and encoded properly, if needed, when generating the plain text\n        representation of the message.  MIME headers (MIME-Version,\n        Content-Type, Content-Transfer-Encoding) will be added as needed.\n\n        "));
      var1.setlocal("set_charset", var5);
      var3 = null;
      var1.setline(273);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, get_charset$16, PyString.fromInterned("Return the Charset instance associated with the message's payload.\n        "));
      var1.setlocal("get_charset", var5);
      var3 = null;
      var1.setline(281);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, __len__$17, PyString.fromInterned("Return the total number of headers, including duplicates."));
      var1.setlocal("__len__", var5);
      var3 = null;
      var1.setline(285);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, __getitem__$18, PyString.fromInterned("Get a header value.\n\n        Return None if the header is missing instead of raising an exception.\n\n        Note that if the header appeared multiple times, exactly which\n        occurrence gets returned is undefined.  Use get_all() to get all\n        the values matching a header field name.\n        "));
      var1.setlocal("__getitem__", var5);
      var3 = null;
      var1.setline(296);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, __setitem__$19, PyString.fromInterned("Set the value of a header.\n\n        Note: this does not overwrite an existing header with the same field\n        name.  Use __delitem__() first to delete any existing headers.\n        "));
      var1.setlocal("__setitem__", var5);
      var3 = null;
      var1.setline(304);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, __delitem__$20, PyString.fromInterned("Delete all occurrences of a header, if present.\n\n        Does not raise an exception if the header is missing.\n        "));
      var1.setlocal("__delitem__", var5);
      var3 = null;
      var1.setline(316);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, __contains__$21, (PyObject)null);
      var1.setlocal("__contains__", var5);
      var3 = null;
      var1.setline(319);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, has_key$22, PyString.fromInterned("Return true if the message contains the header."));
      var1.setlocal("has_key", var5);
      var3 = null;
      var1.setline(324);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, keys$23, PyString.fromInterned("Return a list of all the message's header field names.\n\n        These will be sorted in the order they appeared in the original\n        message, or were added to the message, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        "));
      var1.setlocal("keys", var5);
      var3 = null;
      var1.setline(334);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, values$24, PyString.fromInterned("Return a list of all the message's header values.\n\n        These will be sorted in the order they appeared in the original\n        message, or were added to the message, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        "));
      var1.setlocal("values", var5);
      var3 = null;
      var1.setline(344);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, items$25, PyString.fromInterned("Get all the message's header fields and values.\n\n        These will be sorted in the order they appeared in the original\n        message, or were added to the message, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        "));
      var1.setlocal("items", var5);
      var3 = null;
      var1.setline(354);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, get$26, PyString.fromInterned("Get a header value.\n\n        Like __getitem__() but return failobj instead of None when the field\n        is missing.\n        "));
      var1.setlocal("get", var5);
      var3 = null;
      var1.setline(370);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, get_all$27, PyString.fromInterned("Return a list of all the values for the named field.\n\n        These will be sorted in the order they appeared in the original\n        message, and may contain duplicates.  Any fields deleted and\n        re-inserted are always appended to the header list.\n\n        If no such fields exist, failobj is returned (defaults to None).\n        "));
      var1.setlocal("get_all", var5);
      var3 = null;
      var1.setline(388);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, add_header$28, PyString.fromInterned("Extended header setting.\n\n        name is the header field to add.  keyword arguments can be used to set\n        additional parameters for the header field, with underscores converted\n        to dashes.  Normally the parameter will be added as key=\"value\" unless\n        value is None, in which case only the key will be added.  If a\n        parameter value contains non-ASCII characters it must be specified as a\n        three-tuple of (charset, language, value), in which case it will be\n        encoded according to RFC2231 rules.\n\n        Example:\n\n        msg.add_header('content-disposition', 'attachment', filename='bud.gif')\n        "));
      var1.setlocal("add_header", var5);
      var3 = null;
      var1.setline(413);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, replace_header$29, PyString.fromInterned("Replace a header.\n\n        Replace the first matching header found in the message, retaining\n        header order and case.  If no matching header was found, a KeyError is\n        raised.\n        "));
      var1.setlocal("replace_header", var5);
      var3 = null;
      var1.setline(432);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, get_content_type$30, PyString.fromInterned("Return the message's content type.\n\n        The returned string is coerced to lower case of the form\n        `maintype/subtype'.  If there was no Content-Type header in the\n        message, the default type as given by get_default_type() will be\n        returned.  Since according to RFC 2045, messages always have a default\n        type this will always return a value.\n\n        RFC 2045 defines a message's default type to be text/plain unless it\n        appears inside a multipart/digest container, in which case it would be\n        message/rfc822.\n        "));
      var1.setlocal("get_content_type", var5);
      var3 = null;
      var1.setline(456);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, get_content_maintype$31, PyString.fromInterned("Return the message's main content type.\n\n        This is the `maintype' part of the string returned by\n        get_content_type().\n        "));
      var1.setlocal("get_content_maintype", var5);
      var3 = null;
      var1.setline(465);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, get_content_subtype$32, PyString.fromInterned("Returns the message's sub-content type.\n\n        This is the `subtype' part of the string returned by\n        get_content_type().\n        "));
      var1.setlocal("get_content_subtype", var5);
      var3 = null;
      var1.setline(474);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, get_default_type$33, PyString.fromInterned("Return the `default' content type.\n\n        Most messages have a default content type of text/plain, except for\n        messages that are subparts of multipart/digest containers.  Such\n        subparts have a default content type of message/rfc822.\n        "));
      var1.setlocal("get_default_type", var5);
      var3 = null;
      var1.setline(483);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, set_default_type$34, PyString.fromInterned("Set the `default' content type.\n\n        ctype should be either \"text/plain\" or \"message/rfc822\", although this\n        is not enforced.  The default content type is not stored in the\n        Content-Type header.\n        "));
      var1.setlocal("set_default_type", var5);
      var3 = null;
      var1.setline(492);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _get_params_preserve$35, (PyObject)null);
      var1.setlocal("_get_params_preserve", var5);
      var3 = null;
      var1.setline(513);
      var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned("content-type"), var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var3, get_params$36, PyString.fromInterned("Return the message's Content-Type parameters, as a list.\n\n        The elements of the returned list are 2-tuples of key/value pairs, as\n        split on the `=' sign.  The left hand side of the `=' is the key,\n        while the right hand side is the value.  If there is no `=' sign in\n        the parameter the value is the empty string.  The value is as\n        described in the get_param() method.\n\n        Optional failobj is the object to return if there is no Content-Type\n        header.  Optional header is the header to search instead of\n        Content-Type.  If unquote is True, the value is unquoted.\n        "));
      var1.setlocal("get_params", var5);
      var3 = null;
      var1.setline(535);
      var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned("content-type"), var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var3, get_param$37, PyString.fromInterned("Return the parameter value if found in the Content-Type header.\n\n        Optional failobj is the object to return if there is no Content-Type\n        header, or the Content-Type header has no such parameter.  Optional\n        header is the header to search instead of Content-Type.\n\n        Parameter keys are always compared case insensitively.  The return\n        value can either be a string, or a 3-tuple if the parameter was RFC\n        2231 encoded.  When it's a 3-tuple, the elements of the value are of\n        the form (CHARSET, LANGUAGE, VALUE).  Note that both CHARSET and\n        LANGUAGE can be None, in which case you should consider VALUE to be\n        encoded in the us-ascii charset.  You can usually ignore LANGUAGE.\n\n        Your application should be prepared to deal with 3-tuple return\n        values, and can convert the parameter to a Unicode string like so:\n\n            param = msg.get_param('foo')\n            if isinstance(param, tuple):\n                param = unicode(param[2], param[0] or 'us-ascii')\n\n        In any case, the parameter value (either the returned string, or the\n        VALUE item in the 3-tuple) is always unquoted, unless unquote is set\n        to False.\n        "));
      var1.setlocal("get_param", var5);
      var3 = null;
      var1.setline(571);
      var3 = new PyObject[]{PyString.fromInterned("Content-Type"), var1.getname("True"), var1.getname("None"), PyString.fromInterned("")};
      var5 = new PyFunction(var1.f_globals, var3, set_param$38, PyString.fromInterned("Set a parameter in the Content-Type header.\n\n        If the parameter already exists in the header, its value will be\n        replaced with the new value.\n\n        If header is Content-Type and has not yet been defined for this\n        message, it will be set to \"text/plain\" and the new parameter and\n        value will be appended as per RFC 2045.\n\n        An alternate header can specified in the header argument, and all\n        parameters will be quoted as necessary unless requote is False.\n\n        If charset is specified, the parameter will be encoded according to RFC\n        2231.  Optional language specifies the RFC 2231 language, defaulting\n        to the empty string.  Both charset and language should be strings.\n        "));
      var1.setlocal("set_param", var5);
      var3 = null;
      var1.setline(619);
      var3 = new PyObject[]{PyString.fromInterned("content-type"), var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var3, del_param$39, PyString.fromInterned("Remove the given parameter completely from the Content-Type header.\n\n        The header will be re-written in place without the parameter or its\n        value. All values will be quoted as necessary unless requote is\n        False.  Optional header specifies an alternative to the Content-Type\n        header.\n        "));
      var1.setlocal("del_param", var5);
      var3 = null;
      var1.setline(641);
      var3 = new PyObject[]{PyString.fromInterned("Content-Type"), var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var3, set_type$40, PyString.fromInterned("Set the main type and subtype for the Content-Type header.\n\n        type must be a string in the form \"maintype/subtype\", otherwise a\n        ValueError is raised.\n\n        This method replaces the Content-Type header, keeping all the\n        parameters in place.  If requote is False, this leaves the existing\n        header's quoting as is.  Otherwise, the parameters will be quoted (the\n        default).\n\n        An alternative header can be specified in the header argument.  When\n        the Content-Type header is set, we'll always also add a MIME-Version\n        header.\n        "));
      var1.setlocal("set_type", var5);
      var3 = null;
      var1.setline(673);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, get_filename$41, PyString.fromInterned("Return the filename associated with the payload if present.\n\n        The filename is extracted from the Content-Disposition header's\n        `filename' parameter, and it is unquoted.  If that header is missing\n        the `filename' parameter, this method falls back to looking for the\n        `name' parameter.\n        "));
      var1.setlocal("get_filename", var5);
      var3 = null;
      var1.setline(689);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, get_boundary$42, PyString.fromInterned("Return the boundary associated with the payload if present.\n\n        The boundary is extracted from the Content-Type header's `boundary'\n        parameter, and it is unquoted.\n        "));
      var1.setlocal("get_boundary", var5);
      var3 = null;
      var1.setline(702);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, set_boundary$43, PyString.fromInterned("Set the boundary parameter in Content-Type to 'boundary'.\n\n        This is subtly different than deleting the Content-Type header and\n        adding a new one with a new boundary parameter via add_header().  The\n        main difference is that using the set_boundary() method preserves the\n        order of the Content-Type header in the original message.\n\n        HeaderParseError is raised if the message has no Content-Type header.\n        "));
      var1.setlocal("set_boundary", var5);
      var3 = null;
      var1.setline(747);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, get_content_charset$44, PyString.fromInterned("Return the charset parameter of the Content-Type header.\n\n        The returned string is always coerced to lower case.  If there is no\n        Content-Type header, or if that header has no charset parameter,\n        failobj is returned.\n        "));
      var1.setlocal("get_content_charset", var5);
      var3 = null;
      var1.setline(778);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, get_charsets$45, PyString.fromInterned("Return a list containing the charset(s) used in this message.\n\n        The returned list of items describes the Content-Type headers'\n        charset parameter for this message and all the subparts in its\n        payload.\n\n        Each item will either be a string (the value of the charset parameter\n        in the Content-Type header of that part) or the value of the\n        'failobj' parameter (defaults to None), if the part does not have a\n        main MIME type of \"text\", or the charset is not defined.\n\n        The list will contain one string for each part of the message, plus\n        one for the container message (i.e. self), so that a non-multipart\n        message will still return a list of length 1.\n        "));
      var1.setlocal("get_charsets", var5);
      var3 = null;
      var1.setline(797);
      String[] var6 = new String[]{"walk"};
      var3 = imp.importFrom("email.iterators", var6, var1, -1);
      PyObject var4 = var3[0];
      var1.setlocal("walk", var4);
      var4 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_headers", var3);
      var3 = null;
      var1.setline(109);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_unixfrom", var4);
      var3 = null;
      var1.setline(110);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_payload", var4);
      var3 = null;
      var1.setline(111);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_charset", var4);
      var3 = null;
      var1.setline(113);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("preamble", var4);
      var1.getlocal(0).__setattr__("epilogue", var4);
      var1.setline(114);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"defects", var3);
      var3 = null;
      var1.setline(116);
      PyString var5 = PyString.fromInterned("text/plain");
      var1.getlocal(0).__setattr__((String)"_default_type", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$7(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyString.fromInterned("Return the entire formatted message as a string.\n        This includes the headers, body, and envelope header.\n        ");
      var1.setline(122);
      PyObject var10000 = var1.getlocal(0).__getattr__("as_string");
      PyObject[] var3 = new PyObject[]{var1.getglobal("True")};
      String[] var4 = new String[]{"unixfrom"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject as_string$8(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("Return the entire formatted message as a string.\n        Optional `unixfrom' when True, means include the Unix From_ envelope\n        header.\n\n        This is a convenience method and may not generate the message exactly\n        as you intend because by default it mangles lines that begin with\n        \"From \".  For more flexibility, use the flatten() method of a\n        Generator instance.\n        ");
      var1.setline(134);
      String[] var3 = new String[]{"Generator"};
      PyObject[] var5 = imp.importFrom("email.generator", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(135);
      PyObject var6 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(136);
      var6 = var1.getlocal(2).__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(137);
      PyObject var10000 = var1.getlocal(4).__getattr__("flatten");
      var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1)};
      String[] var7 = new String[]{"unixfrom"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(138);
      var6 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject is_multipart$9(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString.fromInterned("Return True if the message consists of multiple parts.");
      var1.setline(142);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("_payload"), var1.getglobal("list"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_unixfrom$10(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_unixfrom", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_unixfrom$11(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyObject var3 = var1.getlocal(0).__getattr__("_unixfrom");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject attach$12(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyString.fromInterned("Add the given payload to the current payload.\n\n        The current payload will always be a list of objects after this method\n        is called.  If you want to set the payload to a scalar object, use\n        set_payload() instead.\n        ");
      var1.setline(163);
      PyObject var3 = var1.getlocal(0).__getattr__("_payload");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(164);
         PyList var4 = new PyList(new PyObject[]{var1.getlocal(1)});
         var1.getlocal(0).__setattr__((String)"_payload", var4);
         var3 = null;
      } else {
         var1.setline(166);
         var1.getlocal(0).__getattr__("_payload").__getattr__("append").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_payload$13(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyString.fromInterned("Return a reference to the payload.\n\n        The payload will either be a list object or a string.  If you mutate\n        the list object, you modify the message's payload in place.  Optional\n        i returns that index into the payload.\n\n        Optional decode is a flag indicating whether the payload should be\n        decoded or not, according to the Content-Transfer-Encoding header\n        (default is False).\n\n        When True and the message is not a multipart, the payload will be\n        decoded if this header's value is `quoted-printable' or `base64'.  If\n        some other encoding is used, or the header is missing, or if the\n        payload has bogus data (i.e. bogus base64 or uuencoded data), the\n        payload is returned as-is.\n\n        If the message is a multipart and the decode flag is True, then None\n        is returned.\n        ");
      var1.setline(188);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(189);
         var3 = var1.getlocal(0).__getattr__("_payload");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(190);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("_payload"), var1.getglobal("list")).__not__().__nonzero__()) {
            var1.setline(191);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Expected list, got %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("_payload")))));
         }

         var1.setline(193);
         var3 = var1.getlocal(0).__getattr__("_payload").__getitem__(var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(194);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(195);
         if (var1.getlocal(0).__getattr__("is_multipart").__call__(var2).__nonzero__()) {
            var1.setline(196);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(197);
         PyObject var4 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-transfer-encoding"), (PyObject)PyString.fromInterned("")).__getattr__("lower").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(198);
         var4 = var1.getlocal(4);
         var10000 = var4._eq(PyString.fromInterned("quoted-printable"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(199);
            var3 = var1.getglobal("utils").__getattr__("_qdecode").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(200);
         var4 = var1.getlocal(4);
         var10000 = var4._eq(PyString.fromInterned("base64"));
         var4 = null;
         PyException var8;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(202);
               var3 = var1.getglobal("utils").__getattr__("_bdecode").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var6) {
               var8 = Py.setException(var6, var1);
               if (var8.match(var1.getglobal("binascii").__getattr__("Error"))) {
                  var1.setline(205);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               throw var8;
            }
         }

         var1.setline(206);
         var4 = var1.getlocal(4);
         var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("x-uuencode"), PyString.fromInterned("uuencode"), PyString.fromInterned("uue"), PyString.fromInterned("x-uue")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(207);
            var4 = var1.getglobal("StringIO").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;

            try {
               var1.setline(209);
               var10000 = var1.getglobal("uu").__getattr__("decode");
               PyObject[] var9 = new PyObject[]{var1.getglobal("StringIO").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned("\n"))), var1.getlocal(5), var1.getglobal("True")};
               String[] var5 = new String[]{"quiet"};
               var10000.__call__(var2, var9, var5);
               var4 = null;
               var1.setline(210);
               var4 = var1.getlocal(5).__getattr__("getvalue").__call__(var2);
               var1.setlocal(3, var4);
               var4 = null;
            } catch (Throwable var7) {
               var8 = Py.setException(var7, var1);
               if (var8.match(var1.getglobal("uu").__getattr__("Error"))) {
                  var1.setline(213);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               throw var8;
            }
         }
      }

      var1.setline(216);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_payload$14(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyString.fromInterned("Set the payload to the given value.\n\n        Optional charset sets the message's default character set.  See\n        set_charset() for details.\n        ");
      var1.setline(224);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_payload", var3);
      var3 = null;
      var1.setline(225);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(226);
         var1.getlocal(0).__getattr__("set_charset").__call__(var2, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_charset$15(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyString.fromInterned("Set the charset of the payload to a given character set.\n\n        charset can be a Charset instance, a string naming a character set, or\n        None.  If it is a string it will be converted to a Charset instance.\n        If charset is None, the charset parameter will be removed from the\n        Content-Type field.  Anything else will generate a TypeError.\n\n        The message will be assumed to be of type text/* encoded with\n        charset.input_charset.  It will be converted to charset.output_charset\n        and encoded properly, if needed, when generating the plain text\n        representation of the message.  MIME headers (MIME-Version,\n        Content-Type, Content-Transfer-Encoding) will be added as needed.\n\n        ");
      var1.setline(243);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(244);
         var1.getlocal(0).__getattr__("del_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset"));
         var1.setline(245);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_charset", var3);
         var3 = null;
         var1.setline(246);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(247);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(248);
            var3 = var1.getglobal("email").__getattr__("charset").__getattr__("Charset").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(249);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("email").__getattr__("charset").__getattr__("Charset")).__not__().__nonzero__()) {
            var1.setline(250);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(253);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("_charset", var3);
            var3 = null;
            var1.setline(254);
            PyString var7 = PyString.fromInterned("MIME-Version");
            var10000 = var7._notin(var1.getlocal(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(255);
               var1.getlocal(0).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MIME-Version"), (PyObject)PyString.fromInterned("1.0"));
            }

            var1.setline(256);
            var7 = PyString.fromInterned("Content-Type");
            var10000 = var7._notin(var1.getlocal(0));
            var3 = null;
            String[] var4;
            if (var10000.__nonzero__()) {
               var1.setline(257);
               var10000 = var1.getlocal(0).__getattr__("add_header");
               PyObject[] var8 = new PyObject[]{PyString.fromInterned("Content-Type"), PyString.fromInterned("text/plain"), var1.getlocal(1).__getattr__("get_output_charset").__call__(var2)};
               var4 = new String[]{"charset"};
               var10000.__call__(var2, var8, var4);
               var3 = null;
            } else {
               var1.setline(260);
               var1.getlocal(0).__getattr__("set_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset"), (PyObject)var1.getlocal(1).__getattr__("get_output_charset").__call__(var2));
            }

            var1.setline(261);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("_payload"), var1.getglobal("unicode")).__nonzero__()) {
               var1.setline(262);
               var3 = var1.getlocal(0).__getattr__("_payload").__getattr__("encode").__call__(var2, var1.getlocal(1).__getattr__("output_charset"));
               var1.getlocal(0).__setattr__("_payload", var3);
               var3 = null;
            }

            var1.setline(263);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
            var10000 = var3._ne(var1.getlocal(1).__getattr__("get_output_charset").__call__(var2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(264);
               var3 = var1.getlocal(1).__getattr__("body_encode").__call__(var2, var1.getlocal(0).__getattr__("_payload"));
               var1.getlocal(0).__setattr__("_payload", var3);
               var3 = null;
            }

            var1.setline(265);
            var7 = PyString.fromInterned("Content-Transfer-Encoding");
            var10000 = var7._notin(var1.getlocal(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(266);
               var3 = var1.getlocal(1).__getattr__("get_body_encoding").__call__(var2);
               var1.setlocal(2, var3);
               var3 = null;

               try {
                  var1.setline(268);
                  var1.getlocal(2).__call__(var2, var1.getlocal(0));
               } catch (Throwable var5) {
                  PyException var9 = Py.setException(var5, var1);
                  if (!var9.match(var1.getglobal("TypeError"))) {
                     throw var9;
                  }

                  var1.setline(270);
                  PyObject var6 = var1.getlocal(1).__getattr__("body_encode").__call__(var2, var1.getlocal(0).__getattr__("_payload"));
                  var1.getlocal(0).__setattr__("_payload", var6);
                  var4 = null;
                  var1.setline(271);
                  var1.getlocal(0).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Content-Transfer-Encoding"), (PyObject)var1.getlocal(2));
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject get_charset$16(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyString.fromInterned("Return the Charset instance associated with the message's payload.\n        ");
      var1.setline(276);
      PyObject var3 = var1.getlocal(0).__getattr__("_charset");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$17(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyString.fromInterned("Return the total number of headers, including duplicates.");
      var1.setline(283);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_headers"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$18(PyFrame var1, ThreadState var2) {
      var1.setline(293);
      PyString.fromInterned("Get a header value.\n\n        Return None if the header is missing instead of raising an exception.\n\n        Note that if the header appeared multiple times, exactly which\n        occurrence gets returned is undefined.  Use get_all() to get all\n        the values matching a header field name.\n        ");
      var1.setline(294);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setitem__$19(PyFrame var1, ThreadState var2) {
      var1.setline(301);
      PyString.fromInterned("Set the value of a header.\n\n        Note: this does not overwrite an existing header with the same field\n        name.  Use __delitem__() first to delete any existing headers.\n        ");
      var1.setline(302);
      var1.getlocal(0).__getattr__("_headers").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __delitem__$20(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      PyString.fromInterned("Delete all occurrences of a header, if present.\n\n        Does not raise an exception if the header is missing.\n        ");
      var1.setline(309);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(310);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(311);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(311);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(314);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("_headers", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(312);
         PyObject var8 = var1.getlocal(3).__getattr__("lower").__call__(var2);
         PyObject var10000 = var8._ne(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(313);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
         }
      }
   }

   public PyObject __contains__$21(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      PyList var10000 = new PyList();
      PyObject var5 = var10000.__getattr__("append");
      var1.setlocal(2, var5);
      var5 = null;
      var1.setline(317);
      var5 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(317);
         PyObject var6 = var5.__iternext__();
         if (var6 == null) {
            var1.setline(317);
            var1.dellocal(2);
            PyObject var9 = var3._in(var10000);
            var3 = null;
            var3 = var9;
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var7 = Py.unpackSequence(var6, 2);
         PyObject var8 = var7[0];
         var1.setlocal(3, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(4, var8);
         var8 = null;
         var1.setline(317);
         var1.getlocal(2).__call__(var2, var1.getlocal(3).__getattr__("lower").__call__(var2));
      }
   }

   public PyObject has_key$22(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyString.fromInterned("Return true if the message contains the header.");
      var1.setline(321);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(322);
      var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject var10000 = var3._isnot(var1.getlocal(2));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject keys$23(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyString.fromInterned("Return a list of all the message's header field names.\n\n        These will be sorted in the order they appeared in the original\n        message, or were added to the message, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        ");
      var1.setline(332);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(332);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(332);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(332);
            var1.dellocal(1);
            PyList var7 = var10000;
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(332);
         var1.getlocal(1).__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject values$24(PyFrame var1, ThreadState var2) {
      var1.setline(341);
      PyString.fromInterned("Return a list of all the message's header values.\n\n        These will be sorted in the order they appeared in the original\n        message, or were added to the message, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        ");
      var1.setline(342);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(342);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(342);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(342);
            var1.dellocal(1);
            PyList var7 = var10000;
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(342);
         var1.getlocal(1).__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject items$25(PyFrame var1, ThreadState var2) {
      var1.setline(351);
      PyString.fromInterned("Get all the message's header fields and values.\n\n        These will be sorted in the order they appeared in the original\n        message, or were added to the message, and may contain duplicates.\n        Any fields deleted and re-inserted are always appended to the header\n        list.\n        ");
      var1.setline(352);
      PyObject var3 = var1.getlocal(0).__getattr__("_headers").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get$26(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyString.fromInterned("Get a header value.\n\n        Like __getitem__() but return failobj instead of None when the field\n        is missing.\n        ");
      var1.setline(360);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(361);
      var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

      PyObject var10000;
      PyObject var7;
      do {
         var1.setline(361);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(364);
            var7 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(362);
         var7 = var1.getlocal(3).__getattr__("lower").__call__(var2);
         var10000 = var7._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(363);
      var7 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject get_all$27(PyFrame var1, ThreadState var2) {
      var1.setline(378);
      PyString.fromInterned("Return a list of all the values for the named field.\n\n        These will be sorted in the order they appeared in the original\n        message, and may contain duplicates.  Any fields deleted and\n        re-inserted are always appended to the header list.\n\n        If no such fields exist, failobj is returned (defaults to None).\n        ");
      var1.setline(379);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(380);
      PyObject var7 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(381);
      var7 = var1.getlocal(0).__getattr__("_headers").__iter__();

      while(true) {
         var1.setline(381);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(384);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(385);
               var7 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(386);
            var7 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(382);
         PyObject var8 = var1.getlocal(4).__getattr__("lower").__call__(var2);
         PyObject var10000 = var8._eq(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(383);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject add_header$28(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyString.fromInterned("Extended header setting.\n\n        name is the header field to add.  keyword arguments can be used to set\n        additional parameters for the header field, with underscores converted\n        to dashes.  Normally the parameter will be added as key=\"value\" unless\n        value is None, in which case only the key will be added.  If a\n        parameter value contains non-ASCII characters it must be specified as a\n        three-tuple of (charset, language, value), in which case it will be\n        encoded according to RFC2231 rules.\n\n        Example:\n\n        msg.add_header('content-disposition', 'attachment', filename='bud.gif')\n        ");
      var1.setline(403);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(404);
      PyObject var7 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(404);
         PyObject var4 = var7.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(409);
            var7 = var1.getlocal(2);
            var10000 = var7._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(410);
               var1.getlocal(4).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(2));
            }

            var1.setline(411);
            var1.getlocal(0).__getattr__("_headers").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("SEMISPACE").__getattr__("join").__call__(var2, var1.getlocal(4))})));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(405);
         PyObject var8 = var1.getlocal(6);
         var10000 = var8._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(406);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"), (PyObject)PyString.fromInterned("-")));
         } else {
            var1.setline(408);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("_formatparam").__call__(var2, var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_"), (PyObject)PyString.fromInterned("-")), var1.getlocal(6)));
         }
      }
   }

   public PyObject replace_header$29(PyFrame var1, ThreadState var2) {
      var1.setline(419);
      PyString.fromInterned("Replace a header.\n\n        Replace the first matching header found in the message, retaining\n        header order and case.  If no matching header was found, a KeyError is\n        raised.\n        ");
      var1.setline(420);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(421);
      var3 = var1.getglobal("zip").__call__(var2, var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_headers"))), var1.getlocal(0).__getattr__("_headers")).__iter__();

      PyObject var10000;
      PyObject[] var5;
      do {
         var1.setline(421);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(426);
            throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(1)));
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         PyObject[] var7 = Py.unpackSequence(var6, 2);
         PyObject var8 = var7[0];
         var1.setlocal(4, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(5, var8);
         var8 = null;
         var6 = null;
         var1.setline(422);
         PyObject var9 = var1.getlocal(4).__getattr__("lower").__call__(var2);
         var10000 = var9._eq(var1.getlocal(1));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(423);
      PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(2)});
      var1.getlocal(0).__getattr__("_headers").__setitem__((PyObject)var1.getlocal(3), var10);
      var5 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_content_type$30(PyFrame var1, ThreadState var2) {
      var1.setline(444);
      PyString.fromInterned("Return the message's content type.\n\n        The returned string is coerced to lower case of the form\n        `maintype/subtype'.  If there was no Content-Type header in the\n        message, the default type as given by get_default_type() will be\n        returned.  Since according to RFC 2045, messages always have a default\n        type this will always return a value.\n\n        RFC 2045 defines a message's default type to be text/plain unless it\n        appears inside a multipart/digest container, in which case it would be\n        message/rfc822.\n        ");
      var1.setline(445);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(446);
      var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("content-type"), (PyObject)var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(447);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(449);
         var3 = var1.getlocal(0).__getattr__("get_default_type").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(450);
         PyObject var4 = var1.getglobal("_splitparam").__call__(var2, var1.getlocal(2)).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(452);
         var4 = var1.getlocal(3).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var10000 = var4._ne(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(453);
            PyString var5 = PyString.fromInterned("text/plain");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(454);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject get_content_maintype$31(PyFrame var1, ThreadState var2) {
      var1.setline(461);
      PyString.fromInterned("Return the message's main content type.\n\n        This is the `maintype' part of the string returned by\n        get_content_type().\n        ");
      var1.setline(462);
      PyObject var3 = var1.getlocal(0).__getattr__("get_content_type").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(463);
      var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_content_subtype$32(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyString.fromInterned("Returns the message's sub-content type.\n\n        This is the `subtype' part of the string returned by\n        get_content_type().\n        ");
      var1.setline(471);
      PyObject var3 = var1.getlocal(0).__getattr__("get_content_type").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(472);
      var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_default_type$33(PyFrame var1, ThreadState var2) {
      var1.setline(480);
      PyString.fromInterned("Return the `default' content type.\n\n        Most messages have a default content type of text/plain, except for\n        messages that are subparts of multipart/digest containers.  Such\n        subparts have a default content type of message/rfc822.\n        ");
      var1.setline(481);
      PyObject var3 = var1.getlocal(0).__getattr__("_default_type");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_default_type$34(PyFrame var1, ThreadState var2) {
      var1.setline(489);
      PyString.fromInterned("Set the `default' content type.\n\n        ctype should be either \"text/plain\" or \"message/rfc822\", although this\n        is not enforced.  The default content type is not stored in the\n        Content-Type header.\n        ");
      var1.setline(490);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_default_type", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_params_preserve$35(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(496);
      var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(497);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(498);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(499);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(500);
         PyObject var10 = var1.getglobal("_parseparam").__call__(var2, PyString.fromInterned(";")._add(var1.getlocal(4))).__iter__();

         while(true) {
            var1.setline(500);
            PyObject var5 = var10.__iternext__();
            if (var5 == null) {
               var1.setline(510);
               var10 = var1.getglobal("utils").__getattr__("decode_params").__call__(var2, var1.getlocal(5));
               var1.setlocal(5, var10);
               var4 = null;
               var1.setline(511);
               var3 = var1.getlocal(5);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(6, var5);

            PyException var6;
            try {
               var1.setline(502);
               PyObject var11 = var1.getlocal(6).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1));
               PyObject[] var13 = Py.unpackSequence(var11, 2);
               PyObject var8 = var13[0];
               var1.setlocal(7, var8);
               var8 = null;
               var8 = var13[1];
               var1.setlocal(8, var8);
               var8 = null;
               var6 = null;
               var1.setline(503);
               var11 = var1.getlocal(7).__getattr__("strip").__call__(var2);
               var1.setlocal(7, var11);
               var6 = null;
               var1.setline(504);
               var11 = var1.getlocal(8).__getattr__("strip").__call__(var2);
               var1.setlocal(8, var11);
               var6 = null;
            } catch (Throwable var9) {
               var6 = Py.setException(var9, var1);
               if (!var6.match(var1.getglobal("ValueError"))) {
                  throw var6;
               }

               var1.setline(507);
               PyObject var7 = var1.getlocal(6).__getattr__("strip").__call__(var2);
               var1.setlocal(7, var7);
               var7 = null;
               var1.setline(508);
               PyString var12 = PyString.fromInterned("");
               var1.setlocal(8, var12);
               var7 = null;
            }

            var1.setline(509);
            var1.getlocal(5).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8)})));
         }
      }
   }

   public PyObject get_params$36(PyFrame var1, ThreadState var2) {
      var1.setline(525);
      PyString.fromInterned("Return the message's Content-Type parameters, as a list.\n\n        The elements of the returned list are 2-tuples of key/value pairs, as\n        split on the `=' sign.  The left hand side of the `=' is the key,\n        while the right hand side is the value.  If there is no `=' sign in\n        the parameter the value is the empty string.  The value is as\n        described in the get_param() method.\n\n        Optional failobj is the object to return if there is no Content-Type\n        header.  Optional header is the header to search instead of\n        Content-Type.  If unquote is True, the value is unquoted.\n        ");
      var1.setline(526);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(527);
      var3 = var1.getlocal(0).__getattr__("_get_params_preserve").__call__(var2, var1.getlocal(4), var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(528);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._is(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(529);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(530);
         if (!var1.getlocal(3).__nonzero__()) {
            var1.setline(533);
            var3 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(531);
            PyList var9 = new PyList();
            PyObject var4 = var9.__getattr__("append");
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(531);
            var4 = var1.getlocal(5).__iter__();

            while(true) {
               var1.setline(531);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(531);
                  var1.dellocal(6);
                  PyList var8 = var9;
                  var1.f_lasti = -1;
                  return var8;
               }

               PyObject[] var6 = Py.unpackSequence(var5, 2);
               PyObject var7 = var6[0];
               var1.setlocal(7, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(8, var7);
               var7 = null;
               var1.setline(531);
               var1.getlocal(6).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getglobal("_unquotevalue").__call__(var2, var1.getlocal(8))})));
            }
         }
      }
   }

   public PyObject get_param$37(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      PyString.fromInterned("Return the parameter value if found in the Content-Type header.\n\n        Optional failobj is the object to return if there is no Content-Type\n        header, or the Content-Type header has no such parameter.  Optional\n        header is the header to search instead of Content-Type.\n\n        Parameter keys are always compared case insensitively.  The return\n        value can either be a string, or a 3-tuple if the parameter was RFC\n        2231 encoded.  When it's a 3-tuple, the elements of the value are of\n        the form (CHARSET, LANGUAGE, VALUE).  Note that both CHARSET and\n        LANGUAGE can be None, in which case you should consider VALUE to be\n        encoded in the us-ascii charset.  You can usually ignore LANGUAGE.\n\n        Your application should be prepared to deal with 3-tuple return\n        values, and can convert the parameter to a Unicode string like so:\n\n            param = msg.get_param('foo')\n            if isinstance(param, tuple):\n                param = unicode(param[2], param[0] or 'us-ascii')\n\n        In any case, the parameter value (either the returned string, or the\n        VALUE item in the 3-tuple) is always unquoted, unless unquote is set\n        to False.\n        ");
      var1.setline(561);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(562);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(563);
         PyObject var4 = var1.getlocal(0).__getattr__("_get_params_preserve").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__iter__();

         do {
            var1.setline(563);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(569);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(5, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(6, var7);
            var7 = null;
            var1.setline(564);
            PyObject var8 = var1.getlocal(5).__getattr__("lower").__call__(var2);
            var10000 = var8._eq(var1.getlocal(1).__getattr__("lower").__call__(var2));
            var6 = null;
         } while(!var10000.__nonzero__());

         var1.setline(565);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(566);
            var3 = var1.getglobal("_unquotevalue").__call__(var2, var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(568);
            var3 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject set_param$38(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyString.fromInterned("Set a parameter in the Content-Type header.\n\n        If the parameter already exists in the header, its value will be\n        replaced with the new value.\n\n        If header is Content-Type and has not yet been defined for this\n        message, it will be set to \"text/plain\" and the new parameter and\n        value will be appended as per RFC 2045.\n\n        An alternate header can specified in the header argument, and all\n        parameters will be quoted as necessary unless requote is False.\n\n        If charset is specified, the parameter will be encoded according to RFC\n        2231.  Optional language specifies the RFC 2231 language, defaulting\n        to the empty string.  Both charset and language should be strings.\n        ");
      var1.setline(589);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("tuple")).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(5);
      }

      PyTuple var3;
      if (var10000.__nonzero__()) {
         var1.setline(590);
         var3 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(2)});
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(592);
      PyObject var7 = var1.getlocal(3);
      var10000 = var7._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var7 = var1.getlocal(3).__getattr__("lower").__call__(var2);
         var10000 = var7._eq(PyString.fromInterned("content-type"));
         var3 = null;
      }

      PyString var9;
      if (var10000.__nonzero__()) {
         var1.setline(593);
         var9 = PyString.fromInterned("text/plain");
         var1.setlocal(7, var9);
         var3 = null;
      } else {
         var1.setline(595);
         var7 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(3));
         var1.setlocal(7, var7);
         var3 = null;
      }

      var1.setline(596);
      var10000 = var1.getlocal(0).__getattr__("get_param");
      PyObject[] var12 = new PyObject[]{var1.getlocal(1), var1.getlocal(3)};
      String[] var4 = new String[]{"header"};
      var10000 = var10000.__call__(var2, var12, var4);
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(597);
         if (var1.getlocal(7).__not__().__nonzero__()) {
            var1.setline(598);
            var7 = var1.getglobal("_formatparam").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(4));
            var1.setlocal(7, var7);
            var3 = null;
         } else {
            var1.setline(600);
            var7 = var1.getglobal("SEMISPACE").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(7), var1.getglobal("_formatparam").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(4))})));
            var1.setlocal(7, var7);
            var3 = null;
         }
      } else {
         var1.setline(603);
         var9 = PyString.fromInterned("");
         var1.setlocal(7, var9);
         var3 = null;
         var1.setline(604);
         var10000 = var1.getlocal(0).__getattr__("get_params");
         var12 = new PyObject[]{var1.getlocal(3), var1.getlocal(4)};
         var4 = new String[]{"header", "unquote"};
         var10000 = var10000.__call__(var2, var12, var4);
         var3 = null;
         var7 = var10000.__iter__();

         while(true) {
            var1.setline(604);
            PyObject var8 = var7.__iternext__();
            if (var8 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var8, 2);
            PyObject var6 = var5[0];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(9, var6);
            var6 = null;
            var1.setline(606);
            PyString var10 = PyString.fromInterned("");
            var1.setlocal(10, var10);
            var5 = null;
            var1.setline(607);
            PyObject var11 = var1.getlocal(8).__getattr__("lower").__call__(var2);
            var10000 = var11._eq(var1.getlocal(1).__getattr__("lower").__call__(var2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(608);
               var11 = var1.getglobal("_formatparam").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(4));
               var1.setlocal(10, var11);
               var5 = null;
            } else {
               var1.setline(610);
               var11 = var1.getglobal("_formatparam").__call__(var2, var1.getlocal(8), var1.getlocal(9), var1.getlocal(4));
               var1.setlocal(10, var11);
               var5 = null;
            }

            var1.setline(611);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               var1.setline(612);
               var11 = var1.getlocal(10);
               var1.setlocal(7, var11);
               var5 = null;
            } else {
               var1.setline(614);
               var11 = var1.getglobal("SEMISPACE").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(7), var1.getlocal(10)})));
               var1.setlocal(7, var11);
               var5 = null;
            }
         }
      }

      var1.setline(615);
      var7 = var1.getlocal(7);
      var10000 = var7._ne(var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(3)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(616);
         var1.getlocal(0).__delitem__(var1.getlocal(3));
         var1.setline(617);
         var7 = var1.getlocal(7);
         var1.getlocal(0).__setitem__(var1.getlocal(3), var7);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject del_param$39(PyFrame var1, ThreadState var2) {
      var1.setline(626);
      PyString.fromInterned("Remove the given parameter completely from the Content-Type header.\n\n        The header will be re-written in place without the parameter or its\n        value. All values will be quoted as necessary unless requote is\n        False.  Optional header specifies an alternative to the Content-Type\n        header.\n        ");
      var1.setline(627);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._notin(var1.getlocal(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(628);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(629);
         PyString var7 = PyString.fromInterned("");
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(630);
         var10000 = var1.getlocal(0).__getattr__("get_params");
         PyObject[] var8 = new PyObject[]{var1.getlocal(2), var1.getlocal(3)};
         String[] var4 = new String[]{"header", "unquote"};
         var10000 = var10000.__call__(var2, var8, var4);
         var3 = null;
         var3 = var10000.__iter__();

         while(true) {
            var1.setline(630);
            PyObject var9 = var3.__iternext__();
            if (var9 == null) {
               var1.setline(637);
               var3 = var1.getlocal(4);
               var10000 = var3._ne(var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(2)));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(638);
                  var1.getlocal(0).__delitem__(var1.getlocal(2));
                  var1.setline(639);
                  var3 = var1.getlocal(4);
                  var1.getlocal(0).__setitem__(var1.getlocal(2), var3);
                  var3 = null;
               }

               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var9, 2);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(631);
            PyObject var10 = var1.getlocal(5).__getattr__("lower").__call__(var2);
            var10000 = var10._ne(var1.getlocal(1).__getattr__("lower").__call__(var2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(632);
               if (var1.getlocal(4).__not__().__nonzero__()) {
                  var1.setline(633);
                  var10 = var1.getglobal("_formatparam").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(3));
                  var1.setlocal(4, var10);
                  var5 = null;
               } else {
                  var1.setline(635);
                  var10 = var1.getglobal("SEMISPACE").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(4), var1.getglobal("_formatparam").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(3))})));
                  var1.setlocal(4, var10);
                  var5 = null;
               }
            }
         }
      }
   }

   public PyObject set_type$40(PyFrame var1, ThreadState var2) {
      var1.setline(655);
      PyString.fromInterned("Set the main type and subtype for the Content-Type header.\n\n        type must be a string in the form \"maintype/subtype\", otherwise a\n        ValueError is raised.\n\n        This method replaces the Content-Type header, keeping all the\n        parameters in place.  If requote is False, this leaves the existing\n        header's quoting as is.  Otherwise, the parameters will be quoted (the\n        default).\n\n        An alternative header can be specified in the header argument.  When\n        the Content-Type header is set, we'll always also add a MIME-Version\n        header.\n        ");
      var1.setline(657);
      PyObject var3 = var1.getlocal(1).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(658);
         throw Py.makeException(var1.getglobal("ValueError"));
      } else {
         var1.setline(660);
         var3 = var1.getlocal(2).__getattr__("lower").__call__(var2);
         var10000 = var3._eq(PyString.fromInterned("content-type"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(661);
            var1.getlocal(0).__delitem__((PyObject)PyString.fromInterned("mime-version"));
            var1.setline(662);
            PyString var7 = PyString.fromInterned("1.0");
            var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("MIME-Version"), var7);
            var3 = null;
         }

         var1.setline(663);
         var3 = var1.getlocal(2);
         var10000 = var3._notin(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(664);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setitem__(var1.getlocal(2), var3);
            var3 = null;
            var1.setline(665);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(666);
            var10000 = var1.getlocal(0).__getattr__("get_params");
            PyObject[] var9 = new PyObject[]{var1.getlocal(2), var1.getlocal(3)};
            String[] var4 = new String[]{"header", "unquote"};
            var10000 = var10000.__call__(var2, var9, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(667);
            var1.getlocal(0).__delitem__(var1.getlocal(2));
            var1.setline(668);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setitem__(var1.getlocal(2), var3);
            var3 = null;
            var1.setline(670);
            var3 = var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

            while(true) {
               var1.setline(670);
               PyObject var8 = var3.__iternext__();
               if (var8 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               PyObject[] var5 = Py.unpackSequence(var8, 2);
               PyObject var6 = var5[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(6, var6);
               var6 = null;
               var1.setline(671);
               var1.getlocal(0).__getattr__("set_param").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(2), var1.getlocal(3));
            }
         }
      }
   }

   public PyObject get_filename$41(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyString.fromInterned("Return the filename associated with the payload if present.\n\n        The filename is extracted from the Content-Disposition header's\n        `filename' parameter, and it is unquoted.  If that header is missing\n        the `filename' parameter, this method falls back to looking for the\n        `name' parameter.\n        ");
      var1.setline(681);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(682);
      var3 = var1.getlocal(0).__getattr__("get_param").__call__((ThreadState)var2, PyString.fromInterned("filename"), (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("content-disposition"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(683);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(684);
         var3 = var1.getlocal(0).__getattr__("get_param").__call__((ThreadState)var2, PyString.fromInterned("name"), (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("content-type"));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(685);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(686);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(687);
         var3 = var1.getglobal("utils").__getattr__("collapse_rfc2231_value").__call__(var2, var1.getlocal(3)).__getattr__("strip").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_boundary$42(PyFrame var1, ThreadState var2) {
      var1.setline(694);
      PyString.fromInterned("Return the boundary associated with the payload if present.\n\n        The boundary is extracted from the Content-Type header's `boundary'\n        parameter, and it is unquoted.\n        ");
      var1.setline(695);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(696);
      var3 = var1.getlocal(0).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("boundary"), (PyObject)var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(697);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(698);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(700);
         var3 = var1.getglobal("utils").__getattr__("collapse_rfc2231_value").__call__(var2, var1.getlocal(3)).__getattr__("rstrip").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject set_boundary$43(PyFrame var1, ThreadState var2) {
      var1.setline(711);
      PyString.fromInterned("Set the boundary parameter in Content-Type to 'boundary'.\n\n        This is subtly different than deleting the Content-Type header and\n        adding a new one with a new boundary parameter via add_header().  The\n        main difference is that using the set_boundary() method preserves the\n        order of the Content-Type header in the original message.\n\n        HeaderParseError is raised if the message has no Content-Type header.\n        ");
      var1.setline(712);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(713);
      var3 = var1.getlocal(0).__getattr__("_get_params_preserve").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("content-type"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(714);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(717);
         throw Py.makeException(var1.getglobal("errors").__getattr__("HeaderParseError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No Content-Type header found")));
      } else {
         var1.setline(718);
         PyList var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var9);
         var3 = null;
         var1.setline(719);
         var3 = var1.getglobal("False");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(720);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(720);
            PyObject var4 = var3.__iternext__();
            PyObject[] var5;
            PyObject var6;
            PyObject var10;
            if (var4 == null) {
               var1.setline(726);
               if (var1.getlocal(5).__not__().__nonzero__()) {
                  var1.setline(730);
                  var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("boundary"), PyString.fromInterned("\"%s\"")._mod(var1.getlocal(1))})));
               }

               var1.setline(732);
               var9 = new PyList(Py.EmptyObjects);
               var1.setlocal(8, var9);
               var3 = null;
               var1.setline(733);
               var3 = var1.getlocal(0).__getattr__("_headers").__iter__();

               while(true) {
                  while(true) {
                     var1.setline(733);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(745);
                        var3 = var1.getlocal(8);
                        var1.getlocal(0).__setattr__("_headers", var3);
                        var3 = null;
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var5 = Py.unpackSequence(var4, 2);
                     var6 = var5[0];
                     var1.setlocal(9, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(10, var6);
                     var6 = null;
                     var1.setline(734);
                     var10 = var1.getlocal(9).__getattr__("lower").__call__(var2);
                     var10000 = var10._eq(PyString.fromInterned("content-type"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(735);
                        PyList var12 = new PyList(Py.EmptyObjects);
                        var1.setlocal(11, var12);
                        var5 = null;
                        var1.setline(736);
                        var10 = var1.getlocal(4).__iter__();

                        while(true) {
                           var1.setline(736);
                           var6 = var10.__iternext__();
                           if (var6 == null) {
                              var1.setline(741);
                              var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getglobal("SEMISPACE").__getattr__("join").__call__(var2, var1.getlocal(11))})));
                              break;
                           }

                           PyObject[] var7 = Py.unpackSequence(var6, 2);
                           PyObject var8 = var7[0];
                           var1.setlocal(12, var8);
                           var8 = null;
                           var8 = var7[1];
                           var1.setlocal(10, var8);
                           var8 = null;
                           var1.setline(737);
                           PyObject var11 = var1.getlocal(10);
                           var10000 = var11._eq(PyString.fromInterned(""));
                           var7 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(738);
                              var1.getlocal(11).__getattr__("append").__call__(var2, var1.getlocal(12));
                           } else {
                              var1.setline(740);
                              var1.getlocal(11).__getattr__("append").__call__(var2, PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(10)})));
                           }
                        }
                     } else {
                        var1.setline(744);
                        var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10)})));
                     }
                  }
               }
            }

            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(721);
            var10 = var1.getlocal(6).__getattr__("lower").__call__(var2);
            var10000 = var10._eq(PyString.fromInterned("boundary"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(722);
               var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("boundary"), PyString.fromInterned("\"%s\"")._mod(var1.getlocal(1))})));
               var1.setline(723);
               var10 = var1.getglobal("True");
               var1.setlocal(5, var10);
               var5 = null;
            } else {
               var1.setline(725);
               var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7)})));
            }
         }
      }
   }

   public PyObject get_content_charset$44(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      PyString.fromInterned("Return the charset parameter of the Content-Type header.\n\n        The returned string is always coerced to lower case.  If there is no\n        Content-Type header, or if that header has no charset parameter,\n        failobj is returned.\n        ");
      var1.setline(754);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(755);
      var3 = var1.getlocal(0).__getattr__("get_param").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("charset"), (PyObject)var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(756);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(757);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(758);
         Object var4;
         PyException var8;
         PyObject var9;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple")).__nonzero__()) {
            var1.setline(760);
            Object var10 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            if (!((PyObject)var10).__nonzero__()) {
               var10 = PyString.fromInterned("us-ascii");
            }

            var4 = var10;
            var1.setlocal(4, (PyObject)var4);
            var4 = null;

            try {
               var1.setline(765);
               var9 = var1.getglobal("unicode").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(2)), var1.getlocal(4)).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
               var1.setlocal(3, var9);
               var4 = null;
            } catch (Throwable var7) {
               var8 = Py.setException(var7, var1);
               if (!var8.match(new PyTuple(new PyObject[]{var1.getglobal("LookupError"), var1.getglobal("UnicodeError")}))) {
                  throw var8;
               }

               var1.setline(767);
               PyObject var5 = var1.getlocal(3).__getitem__(Py.newInteger(2));
               var1.setlocal(3, var5);
               var5 = null;
            }
         }

         try {
            var1.setline(770);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("str")).__nonzero__()) {
               var1.setline(771);
               var9 = var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("us-ascii"));
               var1.setlocal(3, var9);
               var4 = null;
            }

            var1.setline(772);
            var9 = var1.getlocal(3).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("us-ascii"));
            var1.setlocal(3, var9);
            var4 = null;
         } catch (Throwable var6) {
            var8 = Py.setException(var6, var1);
            if (var8.match(var1.getglobal("UnicodeError"))) {
               var1.setline(774);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            throw var8;
         }

         var1.setline(776);
         var3 = var1.getlocal(3).__getattr__("lower").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_charsets$45(PyFrame var1, ThreadState var2) {
      var1.setline(793);
      PyString.fromInterned("Return a list containing the charset(s) used in this message.\n\n        The returned list of items describes the Content-Type headers'\n        charset parameter for this message and all the subparts in its\n        payload.\n\n        Each item will either be a string (the value of the charset parameter\n        in the Content-Type header of that part) or the value of the\n        'failobj' parameter (defaults to None), if the part does not have a\n        main MIME type of \"text\", or the charset is not defined.\n\n        The list will contain one string for each part of the message, plus\n        one for the container message (i.e. self), so that a non-multipart\n        message will still return a list of length 1.\n        ");
      var1.setline(794);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(794);
      var3 = var1.getlocal(0).__getattr__("walk").__call__(var2).__iter__();

      while(true) {
         var1.setline(794);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(794);
            var1.dellocal(2);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(794);
         var1.getlocal(2).__call__(var2, var1.getlocal(3).__getattr__("get_content_charset").__call__(var2, var1.getlocal(1)));
      }
   }

   public message$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"param", "a", "sep", "b"};
      _splitparam$1 = Py.newCode(1, var2, var1, "_splitparam", 28, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"param", "value", "quote"};
      _formatparam$2 = Py.newCode(3, var2, var1, "_formatparam", 38, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "plist", "end", "f", "i"};
      _parseparam$3 = Py.newCode(1, var2, var1, "_parseparam", 62, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"value"};
      _unquotevalue$4 = Py.newCode(1, var2, var1, "_unquotevalue", 80, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Message$5 = Py.newCode(0, var2, var1, "Message", 92, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$6 = Py.newCode(1, var2, var1, "__init__", 107, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$7 = Py.newCode(1, var2, var1, "__str__", 118, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unixfrom", "Generator", "fp", "g"};
      as_string$8 = Py.newCode(2, var2, var1, "as_string", 124, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_multipart$9 = Py.newCode(1, var2, var1, "is_multipart", 140, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "unixfrom"};
      set_unixfrom$10 = Py.newCode(2, var2, var1, "set_unixfrom", 147, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_unixfrom$11 = Py.newCode(1, var2, var1, "get_unixfrom", 150, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "payload"};
      attach$12 = Py.newCode(2, var2, var1, "attach", 156, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "decode", "payload", "cte", "sfp"};
      get_payload$13 = Py.newCode(3, var2, var1, "get_payload", 168, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "payload", "charset"};
      set_payload$14 = Py.newCode(3, var2, var1, "set_payload", 218, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "charset", "cte"};
      set_charset$15 = Py.newCode(2, var2, var1, "set_charset", 228, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_charset$16 = Py.newCode(1, var2, var1, "get_charset", 273, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$17 = Py.newCode(1, var2, var1, "__len__", 281, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getitem__$18 = Py.newCode(2, var2, var1, "__getitem__", 285, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "val"};
      __setitem__$19 = Py.newCode(3, var2, var1, "__setitem__", 296, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "newheaders", "k", "v"};
      __delitem__$20 = Py.newCode(2, var2, var1, "__delitem__", 304, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "_[317_32]", "k", "v"};
      __contains__$21 = Py.newCode(2, var2, var1, "__contains__", 316, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "missing"};
      has_key$22 = Py.newCode(2, var2, var1, "has_key", 319, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[332_16]", "k", "v"};
      keys$23 = Py.newCode(1, var2, var1, "keys", 324, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[342_16]", "v", "k"};
      values$24 = Py.newCode(1, var2, var1, "values", 334, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      items$25 = Py.newCode(1, var2, var1, "items", 344, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "failobj", "k", "v"};
      get$26 = Py.newCode(3, var2, var1, "get", 354, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "failobj", "values", "k", "v"};
      get_all$27 = Py.newCode(3, var2, var1, "get_all", 370, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_name", "_value", "_params", "parts", "k", "v"};
      add_header$28 = Py.newCode(4, var2, var1, "add_header", 388, false, true, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_name", "_value", "i", "k", "v"};
      replace_header$29 = Py.newCode(3, var2, var1, "replace_header", 413, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "missing", "value", "ctype"};
      get_content_type$30 = Py.newCode(1, var2, var1, "get_content_type", 432, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ctype"};
      get_content_maintype$31 = Py.newCode(1, var2, var1, "get_content_maintype", 456, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ctype"};
      get_content_subtype$32 = Py.newCode(1, var2, var1, "get_content_subtype", 465, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_default_type$33 = Py.newCode(1, var2, var1, "get_default_type", 474, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ctype"};
      set_default_type$34 = Py.newCode(2, var2, var1, "set_default_type", 483, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "failobj", "header", "missing", "value", "params", "p", "name", "val"};
      _get_params_preserve$35 = Py.newCode(3, var2, var1, "_get_params_preserve", 492, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "failobj", "header", "unquote", "missing", "params", "_[531_20]", "k", "v"};
      get_params$36 = Py.newCode(4, var2, var1, "get_params", 513, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "param", "failobj", "header", "unquote", "k", "v"};
      get_param$37 = Py.newCode(5, var2, var1, "get_param", 535, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "param", "value", "header", "requote", "charset", "language", "ctype", "old_param", "old_value", "append_param"};
      set_param$38 = Py.newCode(7, var2, var1, "set_param", 571, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "param", "header", "requote", "new_ctype", "p", "v"};
      del_param$39 = Py.newCode(4, var2, var1, "del_param", 619, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "header", "requote", "params", "p", "v"};
      set_type$40 = Py.newCode(4, var2, var1, "set_type", 641, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "failobj", "missing", "filename"};
      get_filename$41 = Py.newCode(2, var2, var1, "get_filename", 673, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "failobj", "missing", "boundary"};
      get_boundary$42 = Py.newCode(2, var2, var1, "get_boundary", 689, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "boundary", "missing", "params", "newparams", "foundp", "pk", "pv", "newheaders", "h", "v", "parts", "k"};
      set_boundary$43 = Py.newCode(2, var2, var1, "set_boundary", 702, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "failobj", "missing", "charset", "pcharset"};
      get_content_charset$44 = Py.newCode(2, var2, var1, "get_content_charset", 747, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "failobj", "_[794_16]", "part"};
      get_charsets$45 = Py.newCode(2, var2, var1, "get_charsets", 778, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new message$py("email/message$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(message$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._splitparam$1(var2, var3);
         case 2:
            return this._formatparam$2(var2, var3);
         case 3:
            return this._parseparam$3(var2, var3);
         case 4:
            return this._unquotevalue$4(var2, var3);
         case 5:
            return this.Message$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.__str__$7(var2, var3);
         case 8:
            return this.as_string$8(var2, var3);
         case 9:
            return this.is_multipart$9(var2, var3);
         case 10:
            return this.set_unixfrom$10(var2, var3);
         case 11:
            return this.get_unixfrom$11(var2, var3);
         case 12:
            return this.attach$12(var2, var3);
         case 13:
            return this.get_payload$13(var2, var3);
         case 14:
            return this.set_payload$14(var2, var3);
         case 15:
            return this.set_charset$15(var2, var3);
         case 16:
            return this.get_charset$16(var2, var3);
         case 17:
            return this.__len__$17(var2, var3);
         case 18:
            return this.__getitem__$18(var2, var3);
         case 19:
            return this.__setitem__$19(var2, var3);
         case 20:
            return this.__delitem__$20(var2, var3);
         case 21:
            return this.__contains__$21(var2, var3);
         case 22:
            return this.has_key$22(var2, var3);
         case 23:
            return this.keys$23(var2, var3);
         case 24:
            return this.values$24(var2, var3);
         case 25:
            return this.items$25(var2, var3);
         case 26:
            return this.get$26(var2, var3);
         case 27:
            return this.get_all$27(var2, var3);
         case 28:
            return this.add_header$28(var2, var3);
         case 29:
            return this.replace_header$29(var2, var3);
         case 30:
            return this.get_content_type$30(var2, var3);
         case 31:
            return this.get_content_maintype$31(var2, var3);
         case 32:
            return this.get_content_subtype$32(var2, var3);
         case 33:
            return this.get_default_type$33(var2, var3);
         case 34:
            return this.set_default_type$34(var2, var3);
         case 35:
            return this._get_params_preserve$35(var2, var3);
         case 36:
            return this.get_params$36(var2, var3);
         case 37:
            return this.get_param$37(var2, var3);
         case 38:
            return this.set_param$38(var2, var3);
         case 39:
            return this.del_param$39(var2, var3);
         case 40:
            return this.set_type$40(var2, var3);
         case 41:
            return this.get_filename$41(var2, var3);
         case 42:
            return this.get_boundary$42(var2, var3);
         case 43:
            return this.set_boundary$43(var2, var3);
         case 44:
            return this.get_content_charset$44(var2, var3);
         case 45:
            return this.get_charsets$45(var2, var3);
         default:
            return null;
      }
   }
}
