package email;

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
@MTime(1498849383000L)
@Filename("email/charset.py")
public class charset$py extends PyFunctionTable implements PyRunnable {
   static charset$py self;
   static final PyCode f$0;
   static final PyCode add_charset$1;
   static final PyCode add_alias$2;
   static final PyCode add_codec$3;
   static final PyCode Charset$4;
   static final PyCode __init__$5;
   static final PyCode __str__$6;
   static final PyCode __eq__$7;
   static final PyCode __ne__$8;
   static final PyCode get_body_encoding$9;
   static final PyCode convert$10;
   static final PyCode to_splittable$11;
   static final PyCode from_splittable$12;
   static final PyCode get_output_charset$13;
   static final PyCode encoded_header_len$14;
   static final PyCode header_encode$15;
   static final PyCode body_encode$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Charset"), PyString.fromInterned("add_alias"), PyString.fromInterned("add_charset"), PyString.fromInterned("add_codec")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(12);
      PyObject var5 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var5);
      var3 = null;
      var1.setline(13);
      var5 = imp.importOne("email.base64mime", var1, -1);
      var1.setlocal("email", var5);
      var3 = null;
      var1.setline(14);
      var5 = imp.importOne("email.quoprimime", var1, -1);
      var1.setlocal("email", var5);
      var3 = null;
      var1.setline(16);
      String[] var6 = new String[]{"errors"};
      PyObject[] var7 = imp.importFrom("email", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("errors", var4);
      var4 = null;
      var1.setline(17);
      var6 = new String[]{"encode_7or8bit"};
      var7 = imp.importFrom("email.encoders", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("encode_7or8bit", var4);
      var4 = null;
      var1.setline(22);
      PyInteger var8 = Py.newInteger(1);
      var1.setlocal("QP", var8);
      var3 = null;
      var1.setline(23);
      var8 = Py.newInteger(2);
      var1.setlocal("BASE64", var8);
      var3 = null;
      var1.setline(24);
      var8 = Py.newInteger(3);
      var1.setlocal("SHORTEST", var8);
      var3 = null;
      var1.setline(27);
      var8 = Py.newInteger(7);
      var1.setlocal("MISC_LEN", var8);
      var3 = null;
      var1.setline(29);
      PyString var9 = PyString.fromInterned("us-ascii");
      var1.setlocal("DEFAULT_CHARSET", var9);
      var3 = null;
      var1.setline(34);
      PyDictionary var10 = new PyDictionary(new PyObject[]{PyString.fromInterned("iso-8859-1"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("iso-8859-2"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("iso-8859-3"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("iso-8859-4"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("iso-8859-9"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("iso-8859-10"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("iso-8859-13"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("iso-8859-14"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("iso-8859-15"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("iso-8859-16"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("windows-1252"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("viscii"), new PyTuple(new PyObject[]{var1.getname("QP"), var1.getname("QP"), var1.getname("None")}), PyString.fromInterned("us-ascii"), new PyTuple(new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")}), PyString.fromInterned("big5"), new PyTuple(new PyObject[]{var1.getname("BASE64"), var1.getname("BASE64"), var1.getname("None")}), PyString.fromInterned("gb2312"), new PyTuple(new PyObject[]{var1.getname("BASE64"), var1.getname("BASE64"), var1.getname("None")}), PyString.fromInterned("euc-jp"), new PyTuple(new PyObject[]{var1.getname("BASE64"), var1.getname("None"), PyString.fromInterned("iso-2022-jp")}), PyString.fromInterned("shift_jis"), new PyTuple(new PyObject[]{var1.getname("BASE64"), var1.getname("None"), PyString.fromInterned("iso-2022-jp")}), PyString.fromInterned("iso-2022-jp"), new PyTuple(new PyObject[]{var1.getname("BASE64"), var1.getname("None"), var1.getname("None")}), PyString.fromInterned("koi8-r"), new PyTuple(new PyObject[]{var1.getname("BASE64"), var1.getname("BASE64"), var1.getname("None")}), PyString.fromInterned("utf-8"), new PyTuple(new PyObject[]{var1.getname("SHORTEST"), var1.getname("BASE64"), PyString.fromInterned("utf-8")}), PyString.fromInterned("8bit"), new PyTuple(new PyObject[]{var1.getname("None"), var1.getname("BASE64"), PyString.fromInterned("utf-8")})});
      var1.setlocal("CHARSETS", var10);
      var3 = null;
      var1.setline(67);
      var10 = new PyDictionary(new PyObject[]{PyString.fromInterned("latin_1"), PyString.fromInterned("iso-8859-1"), PyString.fromInterned("latin-1"), PyString.fromInterned("iso-8859-1"), PyString.fromInterned("latin_2"), PyString.fromInterned("iso-8859-2"), PyString.fromInterned("latin-2"), PyString.fromInterned("iso-8859-2"), PyString.fromInterned("latin_3"), PyString.fromInterned("iso-8859-3"), PyString.fromInterned("latin-3"), PyString.fromInterned("iso-8859-3"), PyString.fromInterned("latin_4"), PyString.fromInterned("iso-8859-4"), PyString.fromInterned("latin-4"), PyString.fromInterned("iso-8859-4"), PyString.fromInterned("latin_5"), PyString.fromInterned("iso-8859-9"), PyString.fromInterned("latin-5"), PyString.fromInterned("iso-8859-9"), PyString.fromInterned("latin_6"), PyString.fromInterned("iso-8859-10"), PyString.fromInterned("latin-6"), PyString.fromInterned("iso-8859-10"), PyString.fromInterned("latin_7"), PyString.fromInterned("iso-8859-13"), PyString.fromInterned("latin-7"), PyString.fromInterned("iso-8859-13"), PyString.fromInterned("latin_8"), PyString.fromInterned("iso-8859-14"), PyString.fromInterned("latin-8"), PyString.fromInterned("iso-8859-14"), PyString.fromInterned("latin_9"), PyString.fromInterned("iso-8859-15"), PyString.fromInterned("latin-9"), PyString.fromInterned("iso-8859-15"), PyString.fromInterned("latin_10"), PyString.fromInterned("iso-8859-16"), PyString.fromInterned("latin-10"), PyString.fromInterned("iso-8859-16"), PyString.fromInterned("cp949"), PyString.fromInterned("ks_c_5601-1987"), PyString.fromInterned("euc_jp"), PyString.fromInterned("euc-jp"), PyString.fromInterned("euc_kr"), PyString.fromInterned("euc-kr"), PyString.fromInterned("ascii"), PyString.fromInterned("us-ascii")});
      var1.setlocal("ALIASES", var10);
      var3 = null;
      var1.setline(96);
      var10 = new PyDictionary(new PyObject[]{PyString.fromInterned("gb2312"), PyString.fromInterned("eucgb2312_cn"), PyString.fromInterned("big5"), PyString.fromInterned("big5_tw"), PyString.fromInterned("us-ascii"), var1.getname("None")});
      var1.setlocal("CODEC_MAP", var10);
      var3 = null;
      var1.setline(108);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var11 = new PyFunction(var1.f_globals, var7, add_charset$1, PyString.fromInterned("Add character set properties to the global registry.\n\n    charset is the input character set, and must be the canonical name of a\n    character set.\n\n    Optional header_enc and body_enc is either Charset.QP for\n    quoted-printable, Charset.BASE64 for base64 encoding, Charset.SHORTEST for\n    the shortest of qp or base64 encoding, or None for no encoding.  SHORTEST\n    is only valid for header_enc.  It describes how message headers and\n    message bodies in the input charset are to be encoded.  Default is no\n    encoding.\n\n    Optional output_charset is the character set that the output should be\n    in.  Conversions will proceed from input charset, to Unicode, to the\n    output charset when the method Charset.convert() is called.  The default\n    is to output in the same character set as the input.\n\n    Both input_charset and output_charset must have Unicode codec entries in\n    the module's charset-to-codec mapping; use add_codec(charset, codecname)\n    to add codecs the module does not know about.  See the codecs module's\n    documentation for more information.\n    "));
      var1.setlocal("add_charset", var11);
      var3 = null;
      var1.setline(136);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, add_alias$2, PyString.fromInterned("Add a character set alias.\n\n    alias is the alias name, e.g. latin-1\n    canonical is the character set's canonical name, e.g. iso-8859-1\n    "));
      var1.setlocal("add_alias", var11);
      var3 = null;
      var1.setline(145);
      var7 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var7, add_codec$3, PyString.fromInterned("Add a codec that map characters in the given charset to/from Unicode.\n\n    charset is the canonical name of a character set.  codecname is the name\n    of a Python codec, as appropriate for the second argument to the unicode()\n    built-in, or to the encode() method of a Unicode string.\n    "));
      var1.setlocal("add_codec", var11);
      var3 = null;
      var1.setline(156);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Charset", var7, Charset$4);
      var1.setlocal("Charset", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_charset$1(PyFrame var1, ThreadState var2) {
      var1.setline(130);
      PyString.fromInterned("Add character set properties to the global registry.\n\n    charset is the input character set, and must be the canonical name of a\n    character set.\n\n    Optional header_enc and body_enc is either Charset.QP for\n    quoted-printable, Charset.BASE64 for base64 encoding, Charset.SHORTEST for\n    the shortest of qp or base64 encoding, or None for no encoding.  SHORTEST\n    is only valid for header_enc.  It describes how message headers and\n    message bodies in the input charset are to be encoded.  Default is no\n    encoding.\n\n    Optional output_charset is the character set that the output should be\n    in.  Conversions will proceed from input charset, to Unicode, to the\n    output charset when the method Charset.convert() is called.  The default\n    is to output in the same character set as the input.\n\n    Both input_charset and output_charset must have Unicode codec entries in\n    the module's charset-to-codec mapping; use add_codec(charset, codecname)\n    to add codecs the module does not know about.  See the codecs module's\n    documentation for more information.\n    ");
      var1.setline(131);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(var1.getglobal("SHORTEST"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(132);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SHORTEST not allowed for body_enc")));
      } else {
         var1.setline(133);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
         var1.getglobal("CHARSETS").__setitem__((PyObject)var1.getlocal(0), var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject add_alias$2(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString.fromInterned("Add a character set alias.\n\n    alias is the alias name, e.g. latin-1\n    canonical is the character set's canonical name, e.g. iso-8859-1\n    ");
      var1.setline(142);
      PyObject var3 = var1.getlocal(1);
      var1.getglobal("ALIASES").__setitem__(var1.getlocal(0), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_codec$3(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyString.fromInterned("Add a codec that map characters in the given charset to/from Unicode.\n\n    charset is the canonical name of a character set.  codecname is the name\n    of a Python codec, as appropriate for the second argument to the unicode()\n    built-in, or to the encode() method of a Unicode string.\n    ");
      var1.setline(152);
      PyObject var3 = var1.getlocal(1);
      var1.getglobal("CODEC_MAP").__setitem__(var1.getlocal(0), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Charset$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Map character sets to their email properties.\n\n    This class provides information about the requirements imposed on email\n    for a specific character set.  It also provides convenience routines for\n    converting between character sets, given the availability of the\n    applicable codecs.  Given a character set, it will do its best to provide\n    information on how to use that character set in an email in an\n    RFC-compliant way.\n\n    Certain character sets must be encoded with quoted-printable or base64\n    when used in email headers or bodies.  Certain character sets must be\n    converted outright, and are not allowed in email.  Instances of this\n    module expose the following information about a character set:\n\n    input_charset: The initial character set specified.  Common aliases\n                   are converted to their `official' email names (e.g. latin_1\n                   is converted to iso-8859-1).  Defaults to 7-bit us-ascii.\n\n    header_encoding: If the character set must be encoded before it can be\n                     used in an email header, this attribute will be set to\n                     Charset.QP (for quoted-printable), Charset.BASE64 (for\n                     base64 encoding), or Charset.SHORTEST for the shortest of\n                     QP or BASE64 encoding.  Otherwise, it will be None.\n\n    body_encoding: Same as header_encoding, but describes the encoding for the\n                   mail message's body, which indeed may be different than the\n                   header encoding.  Charset.SHORTEST is not allowed for\n                   body_encoding.\n\n    output_charset: Some character sets must be converted before the can be\n                    used in email headers or bodies.  If the input_charset is\n                    one of them, this attribute will contain the name of the\n                    charset output will be converted to.  Otherwise, it will\n                    be None.\n\n    input_codec: The name of the Python codec used to convert the\n                 input_charset to Unicode.  If no conversion codec is\n                 necessary, this attribute will be None.\n\n    output_codec: The name of the Python codec used to convert Unicode\n                  to the output_charset.  If no conversion codec is necessary,\n                  this attribute will have the same value as the input_codec.\n    "));
      var1.setline(199);
      PyString.fromInterned("Map character sets to their email properties.\n\n    This class provides information about the requirements imposed on email\n    for a specific character set.  It also provides convenience routines for\n    converting between character sets, given the availability of the\n    applicable codecs.  Given a character set, it will do its best to provide\n    information on how to use that character set in an email in an\n    RFC-compliant way.\n\n    Certain character sets must be encoded with quoted-printable or base64\n    when used in email headers or bodies.  Certain character sets must be\n    converted outright, and are not allowed in email.  Instances of this\n    module expose the following information about a character set:\n\n    input_charset: The initial character set specified.  Common aliases\n                   are converted to their `official' email names (e.g. latin_1\n                   is converted to iso-8859-1).  Defaults to 7-bit us-ascii.\n\n    header_encoding: If the character set must be encoded before it can be\n                     used in an email header, this attribute will be set to\n                     Charset.QP (for quoted-printable), Charset.BASE64 (for\n                     base64 encoding), or Charset.SHORTEST for the shortest of\n                     QP or BASE64 encoding.  Otherwise, it will be None.\n\n    body_encoding: Same as header_encoding, but describes the encoding for the\n                   mail message's body, which indeed may be different than the\n                   header encoding.  Charset.SHORTEST is not allowed for\n                   body_encoding.\n\n    output_charset: Some character sets must be converted before the can be\n                    used in email headers or bodies.  If the input_charset is\n                    one of them, this attribute will contain the name of the\n                    charset output will be converted to.  Otherwise, it will\n                    be None.\n\n    input_codec: The name of the Python codec used to convert the\n                 input_charset to Unicode.  If no conversion codec is\n                 necessary, this attribute will be None.\n\n    output_codec: The name of the Python codec used to convert Unicode\n                  to the output_charset.  If no conversion codec is necessary,\n                  this attribute will have the same value as the input_codec.\n    ");
      var1.setline(200);
      PyObject[] var3 = new PyObject[]{var1.getname("DEFAULT_CHARSET")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(238);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$6, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(241);
      PyObject var5 = var1.getname("__str__");
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$7, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(246);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$8, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(249);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_body_encoding$9, PyString.fromInterned("Return the content-transfer-encoding used for body encoding.\n\n        This is either the string `quoted-printable' or `base64' depending on\n        the encoding used, or it is a function in which case you should call\n        the function with a single argument, the Message object being\n        encoded.  The function should then set the Content-Transfer-Encoding\n        header itself to whatever is appropriate.\n\n        Returns \"quoted-printable\" if self.body_encoding is QP.\n        Returns \"base64\" if self.body_encoding is BASE64.\n        Returns \"7bit\" otherwise.\n        "));
      var1.setlocal("get_body_encoding", var4);
      var3 = null;
      var1.setline(270);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, convert$10, PyString.fromInterned("Convert a string from the input_codec to the output_codec."));
      var1.setlocal("convert", var4);
      var3 = null;
      var1.setline(277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, to_splittable$11, PyString.fromInterned("Convert a possibly multibyte string to a safely splittable format.\n\n        Uses the input_codec to try and convert the string to Unicode, so it\n        can be safely split on character boundaries (even for multibyte\n        characters).\n\n        Returns the string as-is if it isn't known how to convert it to\n        Unicode with the input_charset.\n\n        Characters that could not be converted to Unicode will be replaced\n        with the Unicode replacement character U+FFFD.\n        "));
      var1.setlocal("to_splittable", var4);
      var3 = null;
      var1.setline(299);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, from_splittable$12, PyString.fromInterned("Convert a splittable string back into an encoded string.\n\n        Uses the proper codec to try and convert the string from Unicode back\n        into an encoded format.  Return the string as-is if it is not Unicode,\n        or if it could not be converted from Unicode.\n\n        Characters that could not be converted from Unicode will be replaced\n        with an appropriate character (usually '?').\n\n        If to_output is True (the default), uses output_codec to convert to an\n        encoded format.  If to_output is False, uses input_codec.\n        "));
      var1.setlocal("from_splittable", var4);
      var3 = null;
      var1.setline(324);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_output_charset$13, PyString.fromInterned("Return the output character set.\n\n        This is self.output_charset if that is not None, otherwise it is\n        self.input_charset.\n        "));
      var1.setlocal("get_output_charset", var4);
      var3 = null;
      var1.setline(332);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, encoded_header_len$14, PyString.fromInterned("Return the length of the encoded header string."));
      var1.setlocal("encoded_header_len", var4);
      var3 = null;
      var1.setline(347);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, header_encode$15, PyString.fromInterned("Header-encode a string, optionally converting it to output_charset.\n\n        If convert is True, the string will be converted from the input\n        charset to the output charset automatically.  This is not useful for\n        multibyte character sets, which have line length issues (multibyte\n        characters must be split on a character, not a byte boundary); use the\n        high-level Header class to deal with these issues.  convert defaults\n        to False.\n\n        The type of encoding (base64 or quoted-printable) will be based on\n        self.header_encoding.\n        "));
      var1.setlocal("header_encode", var4);
      var3 = null;
      var1.setline(378);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, body_encode$16, PyString.fromInterned("Body-encode a string and convert it to output_charset.\n\n        If convert is True (the default), the string will be converted from\n        the input charset to output charset automatically.  Unlike\n        header_encode(), there are no issues with byte boundaries and\n        multibyte charsets in email bodies, so this is usually pretty safe.\n\n        The type of encoding (base64 or quoted-printable) will be based on\n        self.body_encoding.\n        "));
      var1.setlocal("body_encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var8;
      try {
         var1.setline(206);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(207);
            var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
         } else {
            var1.setline(209);
            var8 = var1.getglobal("unicode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("ascii"));
            var1.setlocal(1, var8);
            var3 = null;
         }
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("UnicodeError"))) {
            var1.setline(211);
            throw Py.makeException(var1.getglobal("errors").__getattr__("CharsetError").__call__(var2, var1.getlocal(1)));
         }

         throw var3;
      }

      var1.setline(212);
      var8 = var1.getlocal(1).__getattr__("lower").__call__(var2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(214);
      var8 = var1.getlocal(1);
      PyObject var10000 = var8._in(var1.getglobal("ALIASES"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var8 = var1.getlocal(1);
         var10000 = var8._in(var1.getglobal("CHARSETS"));
         var3 = null;
      }

      if (var10000.__not__().__nonzero__()) {
         try {
            var1.setline(216);
            var8 = var1.getglobal("codecs").__getattr__("lookup").__call__(var2, var1.getlocal(1)).__getattr__("name");
            var1.setlocal(1, var8);
            var3 = null;
         } catch (Throwable var7) {
            var3 = Py.setException(var7, var1);
            if (!var3.match(var1.getglobal("LookupError"))) {
               throw var3;
            }

            var1.setline(218);
         }
      }

      var1.setline(219);
      var8 = var1.getglobal("ALIASES").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(1));
      var1.getlocal(0).__setattr__("input_charset", var8);
      var3 = null;
      var1.setline(223);
      var8 = var1.getglobal("CHARSETS").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("input_charset"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("SHORTEST"), var1.getglobal("BASE64"), var1.getglobal("None")})));
      PyObject[] var4 = Py.unpackSequence(var8, 3);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(225);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(226);
         var8 = var1.getlocal(0).__getattr__("input_charset");
         var1.setlocal(4, var8);
         var3 = null;
      }

      var1.setline(228);
      var8 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("header_encoding", var8);
      var3 = null;
      var1.setline(229);
      var8 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("body_encoding", var8);
      var3 = null;
      var1.setline(230);
      var8 = var1.getglobal("ALIASES").__getattr__("get").__call__(var2, var1.getlocal(4), var1.getlocal(4));
      var1.getlocal(0).__setattr__("output_charset", var8);
      var3 = null;
      var1.setline(233);
      var8 = var1.getglobal("CODEC_MAP").__getattr__("get").__call__(var2, var1.getlocal(0).__getattr__("input_charset"), var1.getlocal(0).__getattr__("input_charset"));
      var1.getlocal(0).__setattr__("input_codec", var8);
      var3 = null;
      var1.setline(235);
      var8 = var1.getglobal("CODEC_MAP").__getattr__("get").__call__(var2, var1.getlocal(0).__getattr__("output_charset"), var1.getlocal(0).__getattr__("output_charset"));
      var1.getlocal(0).__setattr__("output_codec", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$6(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyObject var3 = var1.getlocal(0).__getattr__("input_charset").__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$7(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(var1.getglobal("str").__call__(var2, var1.getlocal(1)).__getattr__("lower").__call__(var2));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __ne__$8(PyFrame var1, ThreadState var2) {
      var1.setline(247);
      PyObject var3 = var1.getlocal(0).__getattr__("__eq__").__call__(var2, var1.getlocal(1)).__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_body_encoding$9(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyString.fromInterned("Return the content-transfer-encoding used for body encoding.\n\n        This is either the string `quoted-printable' or `base64' depending on\n        the encoding used, or it is a function in which case you should call\n        the function with a single argument, the Message object being\n        encoded.  The function should then set the Content-Transfer-Encoding\n        header itself to whatever is appropriate.\n\n        Returns \"quoted-printable\" if self.body_encoding is QP.\n        Returns \"base64\" if self.body_encoding is BASE64.\n        Returns \"7bit\" otherwise.\n        ");
      var1.setline(262);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("body_encoding");
         var10000 = var3._ne(var1.getglobal("SHORTEST"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(263);
      var3 = var1.getlocal(0).__getattr__("body_encoding");
      var10000 = var3._eq(var1.getglobal("QP"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(264);
         var5 = PyString.fromInterned("quoted-printable");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(265);
         PyObject var4 = var1.getlocal(0).__getattr__("body_encoding");
         var10000 = var4._eq(var1.getglobal("BASE64"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(266);
            var5 = PyString.fromInterned("base64");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(268);
            var3 = var1.getglobal("encode_7or8bit");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject convert$10(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyString.fromInterned("Convert a string from the input_codec to the output_codec.");
      var1.setline(272);
      PyObject var3 = var1.getlocal(0).__getattr__("input_codec");
      PyObject var10000 = var3._ne(var1.getlocal(0).__getattr__("output_codec"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(273);
         var3 = var1.getglobal("unicode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("input_codec")).__getattr__("encode").__call__(var2, var1.getlocal(0).__getattr__("output_codec"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(275);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject to_splittable$11(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      PyString.fromInterned("Convert a possibly multibyte string to a safely splittable format.\n\n        Uses the input_codec to try and convert the string to Unicode, so it\n        can be safely split on character boundaries (even for multibyte\n        characters).\n\n        Returns the string as-is if it isn't known how to convert it to\n        Unicode with the input_charset.\n\n        Characters that could not be converted to Unicode will be replaced\n        with the Unicode replacement character U+FFFD.\n        ");
      var1.setline(290);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode"));
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("input_codec");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(291);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         try {
            var1.setline(293);
            var3 = var1.getglobal("unicode").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(0).__getattr__("input_codec"), (PyObject)PyString.fromInterned("replace"));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("LookupError"))) {
               var1.setline(297);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var4;
            }
         }
      }
   }

   public PyObject from_splittable$12(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyString.fromInterned("Convert a splittable string back into an encoded string.\n\n        Uses the proper codec to try and convert the string from Unicode back\n        into an encoded format.  Return the string as-is if it is not Unicode,\n        or if it could not be converted from Unicode.\n\n        Characters that could not be converted from Unicode will be replaced\n        with an appropriate character (usually '?').\n\n        If to_output is True (the default), uses output_codec to convert to an\n        encoded format.  If to_output is False, uses input_codec.\n        ");
      var1.setline(312);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(313);
         var3 = var1.getlocal(0).__getattr__("output_codec");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(315);
         var3 = var1.getlocal(0).__getattr__("input_codec");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(316);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("unicode")).__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(317);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         try {
            var1.setline(319);
            var3 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("replace"));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("LookupError"))) {
               var1.setline(322);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var4;
            }
         }
      }
   }

   public PyObject get_output_charset$13(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      PyString.fromInterned("Return the output character set.\n\n        This is self.output_charset if that is not None, otherwise it is\n        self.input_charset.\n        ");
      var1.setline(330);
      PyObject var10000 = var1.getlocal(0).__getattr__("output_charset");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("input_charset");
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject encoded_header_len$14(PyFrame var1, ThreadState var2) {
      var1.setline(333);
      PyString.fromInterned("Return the length of the encoded header string.");
      var1.setline(334);
      PyObject var3 = var1.getlocal(0).__getattr__("get_output_charset").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(336);
      var3 = var1.getlocal(0).__getattr__("header_encoding");
      PyObject var10000 = var3._eq(var1.getglobal("BASE64"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(337);
         var3 = var1.getglobal("email").__getattr__("base64mime").__getattr__("base64_len").__call__(var2, var1.getlocal(1))._add(var1.getglobal("len").__call__(var2, var1.getlocal(2)))._add(var1.getglobal("MISC_LEN"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(338);
         PyObject var4 = var1.getlocal(0).__getattr__("header_encoding");
         var10000 = var4._eq(var1.getglobal("QP"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(339);
            var3 = var1.getglobal("email").__getattr__("quoprimime").__getattr__("header_quopri_len").__call__(var2, var1.getlocal(1))._add(var1.getglobal("len").__call__(var2, var1.getlocal(2)))._add(var1.getglobal("MISC_LEN"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(340);
            var4 = var1.getlocal(0).__getattr__("header_encoding");
            var10000 = var4._eq(var1.getglobal("SHORTEST"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(341);
               var4 = var1.getglobal("email").__getattr__("base64mime").__getattr__("base64_len").__call__(var2, var1.getlocal(1));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(342);
               var4 = var1.getglobal("email").__getattr__("quoprimime").__getattr__("header_quopri_len").__call__(var2, var1.getlocal(1));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(343);
               var3 = var1.getglobal("min").__call__(var2, var1.getlocal(3), var1.getlocal(4))._add(var1.getglobal("len").__call__(var2, var1.getlocal(2)))._add(var1.getglobal("MISC_LEN"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(345);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject header_encode$15(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyString.fromInterned("Header-encode a string, optionally converting it to output_charset.\n\n        If convert is True, the string will be converted from the input\n        charset to the output charset automatically.  This is not useful for\n        multibyte character sets, which have line length issues (multibyte\n        characters must be split on a character, not a byte boundary); use the\n        high-level Header class to deal with these issues.  convert defaults\n        to False.\n\n        The type of encoding (base64 or quoted-printable) will be based on\n        self.header_encoding.\n        ");
      var1.setline(360);
      PyObject var3 = var1.getlocal(0).__getattr__("get_output_charset").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(361);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(362);
         var3 = var1.getlocal(0).__getattr__("convert").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(364);
      var3 = var1.getlocal(0).__getattr__("header_encoding");
      PyObject var10000 = var3._eq(var1.getglobal("BASE64"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(365);
         var3 = var1.getglobal("email").__getattr__("base64mime").__getattr__("header_encode").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(366);
         PyObject var4 = var1.getlocal(0).__getattr__("header_encoding");
         var10000 = var4._eq(var1.getglobal("QP"));
         var4 = null;
         String[] var5;
         PyObject[] var6;
         if (var10000.__nonzero__()) {
            var1.setline(367);
            var10000 = var1.getglobal("email").__getattr__("quoprimime").__getattr__("header_encode");
            var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getglobal("None")};
            var5 = new String[]{"maxlinelen"};
            var10000 = var10000.__call__(var2, var6, var5);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(368);
            var4 = var1.getlocal(0).__getattr__("header_encoding");
            var10000 = var4._eq(var1.getglobal("SHORTEST"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(369);
               var4 = var1.getglobal("email").__getattr__("base64mime").__getattr__("base64_len").__call__(var2, var1.getlocal(1));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(370);
               var4 = var1.getglobal("email").__getattr__("quoprimime").__getattr__("header_quopri_len").__call__(var2, var1.getlocal(1));
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(371);
               var4 = var1.getlocal(4);
               var10000 = var4._lt(var1.getlocal(5));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(372);
                  var3 = var1.getglobal("email").__getattr__("base64mime").__getattr__("header_encode").__call__(var2, var1.getlocal(1), var1.getlocal(3));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(374);
                  var10000 = var1.getglobal("email").__getattr__("quoprimime").__getattr__("header_encode");
                  var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getglobal("None")};
                  var5 = new String[]{"maxlinelen"};
                  var10000 = var10000.__call__(var2, var6, var5);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(376);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject body_encode$16(PyFrame var1, ThreadState var2) {
      var1.setline(388);
      PyString.fromInterned("Body-encode a string and convert it to output_charset.\n\n        If convert is True (the default), the string will be converted from\n        the input charset to output charset automatically.  Unlike\n        header_encode(), there are no issues with byte boundaries and\n        multibyte charsets in email bodies, so this is usually pretty safe.\n\n        The type of encoding (base64 or quoted-printable) will be based on\n        self.body_encoding.\n        ");
      var1.setline(389);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(390);
         var3 = var1.getlocal(0).__getattr__("convert").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(392);
      var3 = var1.getlocal(0).__getattr__("body_encoding");
      PyObject var10000 = var3._is(var1.getglobal("BASE64"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(393);
         var3 = var1.getglobal("email").__getattr__("base64mime").__getattr__("body_encode").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(394);
         PyObject var4 = var1.getlocal(0).__getattr__("body_encoding");
         var10000 = var4._is(var1.getglobal("QP"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(395);
            var3 = var1.getglobal("email").__getattr__("quoprimime").__getattr__("body_encode").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(397);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public charset$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"charset", "header_enc", "body_enc", "output_charset"};
      add_charset$1 = Py.newCode(4, var2, var1, "add_charset", 108, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"alias", "canonical"};
      add_alias$2 = Py.newCode(2, var2, var1, "add_alias", 136, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"charset", "codecname"};
      add_codec$3 = Py.newCode(2, var2, var1, "add_codec", 145, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Charset$4 = Py.newCode(0, var2, var1, "Charset", 156, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input_charset", "henc", "benc", "conv"};
      __init__$5 = Py.newCode(2, var2, var1, "__init__", 200, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$6 = Py.newCode(1, var2, var1, "__str__", 238, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$7 = Py.newCode(2, var2, var1, "__eq__", 243, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$8 = Py.newCode(2, var2, var1, "__ne__", 246, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_body_encoding$9 = Py.newCode(1, var2, var1, "get_body_encoding", 249, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      convert$10 = Py.newCode(2, var2, var1, "convert", 270, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      to_splittable$11 = Py.newCode(2, var2, var1, "to_splittable", 277, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ustr", "to_output", "codec"};
      from_splittable$12 = Py.newCode(3, var2, var1, "from_splittable", 299, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_output_charset$13 = Py.newCode(1, var2, var1, "get_output_charset", 324, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "cset", "lenb64", "lenqp"};
      encoded_header_len$14 = Py.newCode(2, var2, var1, "encoded_header_len", 332, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "convert", "cset", "lenb64", "lenqp"};
      header_encode$15 = Py.newCode(3, var2, var1, "header_encode", 347, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "convert"};
      body_encode$16 = Py.newCode(3, var2, var1, "body_encode", 378, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new charset$py("email/charset$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(charset$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.add_charset$1(var2, var3);
         case 2:
            return this.add_alias$2(var2, var3);
         case 3:
            return this.add_codec$3(var2, var3);
         case 4:
            return this.Charset$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.__str__$6(var2, var3);
         case 7:
            return this.__eq__$7(var2, var3);
         case 8:
            return this.__ne__$8(var2, var3);
         case 9:
            return this.get_body_encoding$9(var2, var3);
         case 10:
            return this.convert$10(var2, var3);
         case 11:
            return this.to_splittable$11(var2, var3);
         case 12:
            return this.from_splittable$12(var2, var3);
         case 13:
            return this.get_output_charset$13(var2, var3);
         case 14:
            return this.encoded_header_len$14(var2, var3);
         case 15:
            return this.header_encode$15(var2, var3);
         case 16:
            return this.body_encode$16(var2, var3);
         default:
            return null;
      }
   }
}
