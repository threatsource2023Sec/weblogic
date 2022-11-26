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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/__init__.py")
public class json$py extends PyFunctionTable implements PyRunnable {
   static json$py self;
   static final PyCode f$0;
   static final PyCode dump$1;
   static final PyCode dumps$2;
   static final PyCode load$3;
   static final PyCode loads$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("JSON (JavaScript Object Notation) <http://json.org> is a subset of\nJavaScript syntax (ECMA-262 3rd edition) used as a lightweight data\ninterchange format.\n\n:mod:`json` exposes an API familiar to users of the standard library\n:mod:`marshal` and :mod:`pickle` modules. It is the externally maintained\nversion of the :mod:`json` library contained in Python 2.6, but maintains\ncompatibility with Python 2.4 and Python 2.5 and (currently) has\nsignificant performance advantages, even without using the optional C\nextension for speedups.\n\nEncoding basic Python object hierarchies::\n\n    >>> import json\n    >>> json.dumps(['foo', {'bar': ('baz', None, 1.0, 2)}])\n    '[\"foo\", {\"bar\": [\"baz\", null, 1.0, 2]}]'\n    >>> print json.dumps(\"\\\"foo\\bar\")\n    \"\\\"foo\\bar\"\n    >>> print json.dumps(u'\\u1234')\n    \"\\u1234\"\n    >>> print json.dumps('\\\\')\n    \"\\\\\"\n    >>> print json.dumps({\"c\": 0, \"b\": 0, \"a\": 0}, sort_keys=True)\n    {\"a\": 0, \"b\": 0, \"c\": 0}\n    >>> from StringIO import StringIO\n    >>> io = StringIO()\n    >>> json.dump(['streaming API'], io)\n    >>> io.getvalue()\n    '[\"streaming API\"]'\n\nCompact encoding::\n\n    >>> import json\n    >>> json.dumps([1,2,3,{'4': 5, '6': 7}], sort_keys=True, separators=(',',':'))\n    '[1,2,3,{\"4\":5,\"6\":7}]'\n\nPretty printing::\n\n    >>> import json\n    >>> print json.dumps({'4': 5, '6': 7}, sort_keys=True,\n    ...                  indent=4, separators=(',', ': '))\n    {\n        \"4\": 5,\n        \"6\": 7\n    }\n\nDecoding JSON::\n\n    >>> import json\n    >>> obj = [u'foo', {u'bar': [u'baz', None, 1.0, 2]}]\n    >>> json.loads('[\"foo\", {\"bar\":[\"baz\", null, 1.0, 2]}]') == obj\n    True\n    >>> json.loads('\"\\\\\"foo\\\\bar\"') == u'\"foo\\x08ar'\n    True\n    >>> from StringIO import StringIO\n    >>> io = StringIO('[\"streaming API\"]')\n    >>> json.load(io)[0] == 'streaming API'\n    True\n\nSpecializing JSON object decoding::\n\n    >>> import json\n    >>> def as_complex(dct):\n    ...     if '__complex__' in dct:\n    ...         return complex(dct['real'], dct['imag'])\n    ...     return dct\n    ...\n    >>> json.loads('{\"__complex__\": true, \"real\": 1, \"imag\": 2}',\n    ...     object_hook=as_complex)\n    (1+2j)\n    >>> from decimal import Decimal\n    >>> json.loads('1.1', parse_float=Decimal) == Decimal('1.1')\n    True\n\nSpecializing JSON object encoding::\n\n    >>> import json\n    >>> def encode_complex(obj):\n    ...     if isinstance(obj, complex):\n    ...         return [obj.real, obj.imag]\n    ...     raise TypeError(repr(o) + \" is not JSON serializable\")\n    ...\n    >>> json.dumps(2 + 1j, default=encode_complex)\n    '[2.0, 1.0]'\n    >>> json.JSONEncoder(default=encode_complex).encode(2 + 1j)\n    '[2.0, 1.0]'\n    >>> ''.join(json.JSONEncoder(default=encode_complex).iterencode(2 + 1j))\n    '[2.0, 1.0]'\n\n\nUsing json.tool from the shell to validate and pretty-print::\n\n    $ echo '{\"json\":\"obj\"}' | python -m json.tool\n    {\n        \"json\": \"obj\"\n    }\n    $ echo '{ 1.2:3.4}' | python -m json.tool\n    Expecting property name enclosed in double quotes: line 1 column 3 (char 2)\n"));
      var1.setline(99);
      PyString.fromInterned("JSON (JavaScript Object Notation) <http://json.org> is a subset of\nJavaScript syntax (ECMA-262 3rd edition) used as a lightweight data\ninterchange format.\n\n:mod:`json` exposes an API familiar to users of the standard library\n:mod:`marshal` and :mod:`pickle` modules. It is the externally maintained\nversion of the :mod:`json` library contained in Python 2.6, but maintains\ncompatibility with Python 2.4 and Python 2.5 and (currently) has\nsignificant performance advantages, even without using the optional C\nextension for speedups.\n\nEncoding basic Python object hierarchies::\n\n    >>> import json\n    >>> json.dumps(['foo', {'bar': ('baz', None, 1.0, 2)}])\n    '[\"foo\", {\"bar\": [\"baz\", null, 1.0, 2]}]'\n    >>> print json.dumps(\"\\\"foo\\bar\")\n    \"\\\"foo\\bar\"\n    >>> print json.dumps(u'\\u1234')\n    \"\\u1234\"\n    >>> print json.dumps('\\\\')\n    \"\\\\\"\n    >>> print json.dumps({\"c\": 0, \"b\": 0, \"a\": 0}, sort_keys=True)\n    {\"a\": 0, \"b\": 0, \"c\": 0}\n    >>> from StringIO import StringIO\n    >>> io = StringIO()\n    >>> json.dump(['streaming API'], io)\n    >>> io.getvalue()\n    '[\"streaming API\"]'\n\nCompact encoding::\n\n    >>> import json\n    >>> json.dumps([1,2,3,{'4': 5, '6': 7}], sort_keys=True, separators=(',',':'))\n    '[1,2,3,{\"4\":5,\"6\":7}]'\n\nPretty printing::\n\n    >>> import json\n    >>> print json.dumps({'4': 5, '6': 7}, sort_keys=True,\n    ...                  indent=4, separators=(',', ': '))\n    {\n        \"4\": 5,\n        \"6\": 7\n    }\n\nDecoding JSON::\n\n    >>> import json\n    >>> obj = [u'foo', {u'bar': [u'baz', None, 1.0, 2]}]\n    >>> json.loads('[\"foo\", {\"bar\":[\"baz\", null, 1.0, 2]}]') == obj\n    True\n    >>> json.loads('\"\\\\\"foo\\\\bar\"') == u'\"foo\\x08ar'\n    True\n    >>> from StringIO import StringIO\n    >>> io = StringIO('[\"streaming API\"]')\n    >>> json.load(io)[0] == 'streaming API'\n    True\n\nSpecializing JSON object decoding::\n\n    >>> import json\n    >>> def as_complex(dct):\n    ...     if '__complex__' in dct:\n    ...         return complex(dct['real'], dct['imag'])\n    ...     return dct\n    ...\n    >>> json.loads('{\"__complex__\": true, \"real\": 1, \"imag\": 2}',\n    ...     object_hook=as_complex)\n    (1+2j)\n    >>> from decimal import Decimal\n    >>> json.loads('1.1', parse_float=Decimal) == Decimal('1.1')\n    True\n\nSpecializing JSON object encoding::\n\n    >>> import json\n    >>> def encode_complex(obj):\n    ...     if isinstance(obj, complex):\n    ...         return [obj.real, obj.imag]\n    ...     raise TypeError(repr(o) + \" is not JSON serializable\")\n    ...\n    >>> json.dumps(2 + 1j, default=encode_complex)\n    '[2.0, 1.0]'\n    >>> json.JSONEncoder(default=encode_complex).encode(2 + 1j)\n    '[2.0, 1.0]'\n    >>> ''.join(json.JSONEncoder(default=encode_complex).iterencode(2 + 1j))\n    '[2.0, 1.0]'\n\n\nUsing json.tool from the shell to validate and pretty-print::\n\n    $ echo '{\"json\":\"obj\"}' | python -m json.tool\n    {\n        \"json\": \"obj\"\n    }\n    $ echo '{ 1.2:3.4}' | python -m json.tool\n    Expecting property name enclosed in double quotes: line 1 column 3 (char 2)\n");
      var1.setline(100);
      PyString var3 = PyString.fromInterned("2.0.9");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(101);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("dump"), PyString.fromInterned("dumps"), PyString.fromInterned("load"), PyString.fromInterned("loads"), PyString.fromInterned("JSONDecoder"), PyString.fromInterned("JSONEncoder")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(106);
      var3 = PyString.fromInterned("Bob Ippolito <bob@redivi.com>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(108);
      String[] var6 = new String[]{"JSONDecoder"};
      PyObject[] var7 = imp.importFrom("decoder", var6, var1, 1);
      PyObject var4 = var7[0];
      var1.setlocal("JSONDecoder", var4);
      var4 = null;
      var1.setline(109);
      var6 = new String[]{"JSONEncoder"};
      var7 = imp.importFrom("encoder", var6, var1, 1);
      var4 = var7[0];
      var1.setlocal("JSONEncoder", var4);
      var4 = null;
      var1.setline(111);
      PyObject var10000 = var1.getname("JSONEncoder");
      var7 = new PyObject[]{var1.getname("False"), var1.getname("True"), var1.getname("True"), var1.getname("True"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("utf-8"), var1.getname("None")};
      String[] var8 = new String[]{"skipkeys", "ensure_ascii", "check_circular", "allow_nan", "indent", "separators", "encoding", "default"};
      var10000 = var10000.__call__(var2, var7, var8);
      var3 = null;
      PyObject var9 = var10000;
      var1.setlocal("_default_encoder", var9);
      var3 = null;
      var1.setline(122);
      var7 = new PyObject[]{var1.getname("False"), var1.getname("True"), var1.getname("True"), var1.getname("True"), var1.getname("None"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("utf-8"), var1.getname("None"), var1.getname("False")};
      PyFunction var10 = new PyFunction(var1.f_globals, var7, dump$1, PyString.fromInterned("Serialize ``obj`` as a JSON formatted stream to ``fp`` (a\n    ``.write()``-supporting file-like object).\n\n    If ``skipkeys`` is true then ``dict`` keys that are not basic types\n    (``str``, ``unicode``, ``int``, ``long``, ``float``, ``bool``, ``None``)\n    will be skipped instead of raising a ``TypeError``.\n\n    If ``ensure_ascii`` is true (the default), all non-ASCII characters in the\n    output are escaped with ``\\uXXXX`` sequences, and the result is a ``str``\n    instance consisting of ASCII characters only.  If ``ensure_ascii`` is\n    ``False``, some chunks written to ``fp`` may be ``unicode`` instances.\n    This usually happens because the input contains unicode strings or the\n    ``encoding`` parameter is used. Unless ``fp.write()`` explicitly\n    understands ``unicode`` (as in ``codecs.getwriter``) this is likely to\n    cause an error.\n\n    If ``check_circular`` is false, then the circular reference check\n    for container types will be skipped and a circular reference will\n    result in an ``OverflowError`` (or worse).\n\n    If ``allow_nan`` is false, then it will be a ``ValueError`` to\n    serialize out of range ``float`` values (``nan``, ``inf``, ``-inf``)\n    in strict compliance of the JSON specification, instead of using the\n    JavaScript equivalents (``NaN``, ``Infinity``, ``-Infinity``).\n\n    If ``indent`` is a non-negative integer, then JSON array elements and\n    object members will be pretty-printed with that indent level. An indent\n    level of 0 will only insert newlines. ``None`` is the most compact\n    representation.  Since the default item separator is ``', '``,  the\n    output might include trailing whitespace when ``indent`` is specified.\n    You can use ``separators=(',', ': ')`` to avoid this.\n\n    If ``separators`` is an ``(item_separator, dict_separator)`` tuple\n    then it will be used instead of the default ``(', ', ': ')`` separators.\n    ``(',', ':')`` is the most compact JSON representation.\n\n    ``encoding`` is the character encoding for str instances, default is UTF-8.\n\n    ``default(obj)`` is a function that should return a serializable version\n    of obj or raise TypeError. The default simply raises TypeError.\n\n    If *sort_keys* is ``True`` (default: ``False``), then the output of\n    dictionaries will be sorted by key.\n\n    To use a custom ``JSONEncoder`` subclass (e.g. one that overrides the\n    ``.default()`` method to serialize additional types), specify it with\n    the ``cls`` kwarg; otherwise ``JSONEncoder`` is used.\n\n    "));
      var1.setlocal("dump", var10);
      var3 = null;
      var1.setline(193);
      var7 = new PyObject[]{var1.getname("False"), var1.getname("True"), var1.getname("True"), var1.getname("True"), var1.getname("None"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("utf-8"), var1.getname("None"), var1.getname("False")};
      var10 = new PyFunction(var1.f_globals, var7, dumps$2, PyString.fromInterned("Serialize ``obj`` to a JSON formatted ``str``.\n\n    If ``skipkeys`` is false then ``dict`` keys that are not basic types\n    (``str``, ``unicode``, ``int``, ``long``, ``float``, ``bool``, ``None``)\n    will be skipped instead of raising a ``TypeError``.\n\n    If ``ensure_ascii`` is false, all non-ASCII characters are not escaped, and\n    the return value may be a ``unicode`` instance. See ``dump`` for details.\n\n    If ``check_circular`` is false, then the circular reference check\n    for container types will be skipped and a circular reference will\n    result in an ``OverflowError`` (or worse).\n\n    If ``allow_nan`` is false, then it will be a ``ValueError`` to\n    serialize out of range ``float`` values (``nan``, ``inf``, ``-inf``) in\n    strict compliance of the JSON specification, instead of using the\n    JavaScript equivalents (``NaN``, ``Infinity``, ``-Infinity``).\n\n    If ``indent`` is a non-negative integer, then JSON array elements and\n    object members will be pretty-printed with that indent level. An indent\n    level of 0 will only insert newlines. ``None`` is the most compact\n    representation.  Since the default item separator is ``', '``,  the\n    output might include trailing whitespace when ``indent`` is specified.\n    You can use ``separators=(',', ': ')`` to avoid this.\n\n    If ``separators`` is an ``(item_separator, dict_separator)`` tuple\n    then it will be used instead of the default ``(', ', ': ')`` separators.\n    ``(',', ':')`` is the most compact JSON representation.\n\n    ``encoding`` is the character encoding for str instances, default is UTF-8.\n\n    ``default(obj)`` is a function that should return a serializable version\n    of obj or raise TypeError. The default simply raises TypeError.\n\n    If *sort_keys* is ``True`` (default: ``False``), then the output of\n    dictionaries will be sorted by key.\n\n    To use a custom ``JSONEncoder`` subclass (e.g. one that overrides the\n    ``.default()`` method to serialize additional types), specify it with\n    the ``cls`` kwarg; otherwise ``JSONEncoder`` is used.\n\n    "));
      var1.setlocal("dumps", var10);
      var3 = null;
      var1.setline(253);
      var10000 = var1.getname("JSONDecoder");
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new String[]{"encoding", "object_hook", "object_pairs_hook"};
      var10000 = var10000.__call__(var2, var7, var8);
      var3 = null;
      var9 = var10000;
      var1.setlocal("_default_decoder", var9);
      var3 = null;
      var1.setline(257);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var7, load$3, PyString.fromInterned("Deserialize ``fp`` (a ``.read()``-supporting file-like object containing\n    a JSON document) to a Python object.\n\n    If the contents of ``fp`` is encoded with an ASCII based encoding other\n    than utf-8 (e.g. latin-1), then an appropriate ``encoding`` name must\n    be specified. Encodings that are not ASCII based (such as UCS-2) are\n    not allowed, and should be wrapped with\n    ``codecs.getreader(fp)(encoding)``, or simply decoded to a ``unicode``\n    object and passed to ``loads()``\n\n    ``object_hook`` is an optional function that will be called with the\n    result of any object literal decode (a ``dict``). The return value of\n    ``object_hook`` will be used instead of the ``dict``. This feature\n    can be used to implement custom decoders (e.g. JSON-RPC class hinting).\n\n    ``object_pairs_hook`` is an optional function that will be called with the\n    result of any object literal decoded with an ordered list of pairs.  The\n    return value of ``object_pairs_hook`` will be used instead of the ``dict``.\n    This feature can be used to implement custom decoders that rely on the\n    order that the key and value pairs are decoded (for example,\n    collections.OrderedDict will remember the order of insertion). If\n    ``object_hook`` is also defined, the ``object_pairs_hook`` takes priority.\n\n    To use a custom ``JSONDecoder`` subclass, specify it with the ``cls``\n    kwarg; otherwise ``JSONDecoder`` is used.\n\n    "));
      var1.setlocal("load", var10);
      var3 = null;
      var1.setline(293);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var10 = new PyFunction(var1.f_globals, var7, loads$4, PyString.fromInterned("Deserialize ``s`` (a ``str`` or ``unicode`` instance containing a JSON\n    document) to a Python object.\n\n    If ``s`` is a ``str`` instance and is encoded with an ASCII based encoding\n    other than utf-8 (e.g. latin-1) then an appropriate ``encoding`` name\n    must be specified. Encodings that are not ASCII based (such as UCS-2)\n    are not allowed and should be decoded to ``unicode`` first.\n\n    ``object_hook`` is an optional function that will be called with the\n    result of any object literal decode (a ``dict``). The return value of\n    ``object_hook`` will be used instead of the ``dict``. This feature\n    can be used to implement custom decoders (e.g. JSON-RPC class hinting).\n\n    ``object_pairs_hook`` is an optional function that will be called with the\n    result of any object literal decoded with an ordered list of pairs.  The\n    return value of ``object_pairs_hook`` will be used instead of the ``dict``.\n    This feature can be used to implement custom decoders that rely on the\n    order that the key and value pairs are decoded (for example,\n    collections.OrderedDict will remember the order of insertion). If\n    ``object_hook`` is also defined, the ``object_pairs_hook`` takes priority.\n\n    ``parse_float``, if specified, will be called with the string\n    of every JSON float to be decoded. By default this is equivalent to\n    float(num_str). This can be used to use another datatype or parser\n    for JSON floats (e.g. decimal.Decimal).\n\n    ``parse_int``, if specified, will be called with the string\n    of every JSON int to be decoded. By default this is equivalent to\n    int(num_str). This can be used to use another datatype or parser\n    for JSON integers (e.g. float).\n\n    ``parse_constant``, if specified, will be called with one of the\n    following strings: -Infinity, Infinity, NaN, null, true, false.\n    This can be used to raise an exception if invalid JSON numbers\n    are encountered.\n\n    To use a custom ``JSONDecoder`` subclass, specify it with the ``cls``\n    kwarg; otherwise ``JSONDecoder`` is used.\n\n    "));
      var1.setlocal("loads", var10);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump$1(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyString.fromInterned("Serialize ``obj`` as a JSON formatted stream to ``fp`` (a\n    ``.write()``-supporting file-like object).\n\n    If ``skipkeys`` is true then ``dict`` keys that are not basic types\n    (``str``, ``unicode``, ``int``, ``long``, ``float``, ``bool``, ``None``)\n    will be skipped instead of raising a ``TypeError``.\n\n    If ``ensure_ascii`` is true (the default), all non-ASCII characters in the\n    output are escaped with ``\\uXXXX`` sequences, and the result is a ``str``\n    instance consisting of ASCII characters only.  If ``ensure_ascii`` is\n    ``False``, some chunks written to ``fp`` may be ``unicode`` instances.\n    This usually happens because the input contains unicode strings or the\n    ``encoding`` parameter is used. Unless ``fp.write()`` explicitly\n    understands ``unicode`` (as in ``codecs.getwriter``) this is likely to\n    cause an error.\n\n    If ``check_circular`` is false, then the circular reference check\n    for container types will be skipped and a circular reference will\n    result in an ``OverflowError`` (or worse).\n\n    If ``allow_nan`` is false, then it will be a ``ValueError`` to\n    serialize out of range ``float`` values (``nan``, ``inf``, ``-inf``)\n    in strict compliance of the JSON specification, instead of using the\n    JavaScript equivalents (``NaN``, ``Infinity``, ``-Infinity``).\n\n    If ``indent`` is a non-negative integer, then JSON array elements and\n    object members will be pretty-printed with that indent level. An indent\n    level of 0 will only insert newlines. ``None`` is the most compact\n    representation.  Since the default item separator is ``', '``,  the\n    output might include trailing whitespace when ``indent`` is specified.\n    You can use ``separators=(',', ': ')`` to avoid this.\n\n    If ``separators`` is an ``(item_separator, dict_separator)`` tuple\n    then it will be used instead of the default ``(', ', ': ')`` separators.\n    ``(',', ':')`` is the most compact JSON representation.\n\n    ``encoding`` is the character encoding for str instances, default is UTF-8.\n\n    ``default(obj)`` is a function that should return a serializable version\n    of obj or raise TypeError. The default simply raises TypeError.\n\n    If *sort_keys* is ``True`` (default: ``False``), then the output of\n    dictionaries will be sorted by key.\n\n    To use a custom ``JSONEncoder`` subclass (e.g. one that overrides the\n    ``.default()`` method to serialize additional types), specify it with\n    the ``cls`` kwarg; otherwise ``JSONEncoder`` is used.\n\n    ");
      var1.setline(175);
      PyObject var10000 = var1.getlocal(2).__not__();
      PyObject var3;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(5);
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(6);
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(7);
                     var10000 = var3._is(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(8);
                        var10000 = var3._is(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(9);
                           var10000 = var3._eq(PyString.fromInterned("utf-8"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var3 = var1.getlocal(10);
                              var10000 = var3._is(var1.getglobal("None"));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(11).__not__();
                                 if (var10000.__nonzero__()) {
                                    var10000 = var1.getlocal(12).__not__();
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(179);
         var3 = var1.getglobal("_default_encoder").__getattr__("iterencode").__call__(var2, var1.getlocal(0));
         var1.setlocal(13, var3);
         var3 = null;
      } else {
         var1.setline(181);
         var3 = var1.getlocal(6);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(182);
            var3 = var1.getglobal("JSONEncoder");
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(183);
         var10000 = var1.getlocal(6);
         PyObject[] var6 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11)};
         String[] var4 = new String[]{"skipkeys", "ensure_ascii", "check_circular", "allow_nan", "indent", "separators", "encoding", "default", "sort_keys"};
         var10000 = var10000._callextra(var6, var4, (PyObject)null, var1.getlocal(12));
         var3 = null;
         var3 = var10000.__getattr__("iterencode").__call__(var2, var1.getlocal(0));
         var1.setlocal(13, var3);
         var3 = null;
      }

      var1.setline(189);
      var3 = var1.getlocal(13).__iter__();

      while(true) {
         var1.setline(189);
         PyObject var5 = var3.__iternext__();
         if (var5 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(14, var5);
         var1.setline(190);
         var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(14));
      }
   }

   public PyObject dumps$2(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyString.fromInterned("Serialize ``obj`` to a JSON formatted ``str``.\n\n    If ``skipkeys`` is false then ``dict`` keys that are not basic types\n    (``str``, ``unicode``, ``int``, ``long``, ``float``, ``bool``, ``None``)\n    will be skipped instead of raising a ``TypeError``.\n\n    If ``ensure_ascii`` is false, all non-ASCII characters are not escaped, and\n    the return value may be a ``unicode`` instance. See ``dump`` for details.\n\n    If ``check_circular`` is false, then the circular reference check\n    for container types will be skipped and a circular reference will\n    result in an ``OverflowError`` (or worse).\n\n    If ``allow_nan`` is false, then it will be a ``ValueError`` to\n    serialize out of range ``float`` values (``nan``, ``inf``, ``-inf``) in\n    strict compliance of the JSON specification, instead of using the\n    JavaScript equivalents (``NaN``, ``Infinity``, ``-Infinity``).\n\n    If ``indent`` is a non-negative integer, then JSON array elements and\n    object members will be pretty-printed with that indent level. An indent\n    level of 0 will only insert newlines. ``None`` is the most compact\n    representation.  Since the default item separator is ``', '``,  the\n    output might include trailing whitespace when ``indent`` is specified.\n    You can use ``separators=(',', ': ')`` to avoid this.\n\n    If ``separators`` is an ``(item_separator, dict_separator)`` tuple\n    then it will be used instead of the default ``(', ', ': ')`` separators.\n    ``(',', ':')`` is the most compact JSON representation.\n\n    ``encoding`` is the character encoding for str instances, default is UTF-8.\n\n    ``default(obj)`` is a function that should return a serializable version\n    of obj or raise TypeError. The default simply raises TypeError.\n\n    If *sort_keys* is ``True`` (default: ``False``), then the output of\n    dictionaries will be sorted by key.\n\n    To use a custom ``JSONEncoder`` subclass (e.g. one that overrides the\n    ``.default()`` method to serialize additional types), specify it with\n    the ``cls`` kwarg; otherwise ``JSONEncoder`` is used.\n\n    ");
      var1.setline(239);
      PyObject var10000 = var1.getlocal(1).__not__();
      PyObject var3;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(4);
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(5);
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(6);
                     var10000 = var3._is(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(7);
                        var10000 = var3._is(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(8);
                           var10000 = var3._eq(PyString.fromInterned("utf-8"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var3 = var1.getlocal(9);
                              var10000 = var3._is(var1.getglobal("None"));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(10).__not__();
                                 if (var10000.__nonzero__()) {
                                    var10000 = var1.getlocal(11).__not__();
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(243);
         var3 = var1.getglobal("_default_encoder").__getattr__("encode").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(244);
         PyObject var4 = var1.getlocal(5);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(245);
            var4 = var1.getglobal("JSONEncoder");
            var1.setlocal(5, var4);
            var4 = null;
         }

         var1.setline(246);
         var10000 = var1.getlocal(5);
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10)};
         String[] var5 = new String[]{"skipkeys", "ensure_ascii", "check_circular", "allow_nan", "indent", "separators", "encoding", "default", "sort_keys"};
         var10000 = var10000._callextra(var6, var5, (PyObject)null, var1.getlocal(11));
         var4 = null;
         var3 = var10000.__getattr__("encode").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject load$3(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      PyString.fromInterned("Deserialize ``fp`` (a ``.read()``-supporting file-like object containing\n    a JSON document) to a Python object.\n\n    If the contents of ``fp`` is encoded with an ASCII based encoding other\n    than utf-8 (e.g. latin-1), then an appropriate ``encoding`` name must\n    be specified. Encodings that are not ASCII based (such as UCS-2) are\n    not allowed, and should be wrapped with\n    ``codecs.getreader(fp)(encoding)``, or simply decoded to a ``unicode``\n    object and passed to ``loads()``\n\n    ``object_hook`` is an optional function that will be called with the\n    result of any object literal decode (a ``dict``). The return value of\n    ``object_hook`` will be used instead of the ``dict``. This feature\n    can be used to implement custom decoders (e.g. JSON-RPC class hinting).\n\n    ``object_pairs_hook`` is an optional function that will be called with the\n    result of any object literal decoded with an ordered list of pairs.  The\n    return value of ``object_pairs_hook`` will be used instead of the ``dict``.\n    This feature can be used to implement custom decoders that rely on the\n    order that the key and value pairs are decoded (for example,\n    collections.OrderedDict will remember the order of insertion). If\n    ``object_hook`` is also defined, the ``object_pairs_hook`` takes priority.\n\n    To use a custom ``JSONDecoder`` subclass, specify it with the ``cls``\n    kwarg; otherwise ``JSONDecoder`` is used.\n\n    ");
      var1.setline(286);
      PyObject var10000 = var1.getglobal("loads");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("read").__call__(var2), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
      String[] var4 = new String[]{"encoding", "cls", "object_hook", "parse_float", "parse_int", "parse_constant", "object_pairs_hook"};
      var10000 = var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(8));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject loads$4(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyString.fromInterned("Deserialize ``s`` (a ``str`` or ``unicode`` instance containing a JSON\n    document) to a Python object.\n\n    If ``s`` is a ``str`` instance and is encoded with an ASCII based encoding\n    other than utf-8 (e.g. latin-1) then an appropriate ``encoding`` name\n    must be specified. Encodings that are not ASCII based (such as UCS-2)\n    are not allowed and should be decoded to ``unicode`` first.\n\n    ``object_hook`` is an optional function that will be called with the\n    result of any object literal decode (a ``dict``). The return value of\n    ``object_hook`` will be used instead of the ``dict``. This feature\n    can be used to implement custom decoders (e.g. JSON-RPC class hinting).\n\n    ``object_pairs_hook`` is an optional function that will be called with the\n    result of any object literal decoded with an ordered list of pairs.  The\n    return value of ``object_pairs_hook`` will be used instead of the ``dict``.\n    This feature can be used to implement custom decoders that rely on the\n    order that the key and value pairs are decoded (for example,\n    collections.OrderedDict will remember the order of insertion). If\n    ``object_hook`` is also defined, the ``object_pairs_hook`` takes priority.\n\n    ``parse_float``, if specified, will be called with the string\n    of every JSON float to be decoded. By default this is equivalent to\n    float(num_str). This can be used to use another datatype or parser\n    for JSON floats (e.g. decimal.Decimal).\n\n    ``parse_int``, if specified, will be called with the string\n    of every JSON int to be decoded. By default this is equivalent to\n    int(num_str). This can be used to use another datatype or parser\n    for JSON integers (e.g. float).\n\n    ``parse_constant``, if specified, will be called with one of the\n    following strings: -Infinity, Infinity, NaN, null, true, false.\n    This can be used to raise an exception if invalid JSON numbers\n    are encountered.\n\n    To use a custom ``JSONDecoder`` subclass, specify it with the ``cls``\n    kwarg; otherwise ``JSONDecoder`` is used.\n\n    ");
      var1.setline(335);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(5);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(4);
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(6);
                     var10000 = var3._is(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var3 = var1.getlocal(7);
                        var10000 = var3._is(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getlocal(8).__not__();
                        }
                     }
                  }
               }
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(338);
         var3 = var1.getglobal("_default_decoder").__getattr__("decode").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(339);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(340);
            var4 = var1.getglobal("JSONDecoder");
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(341);
         var4 = var1.getlocal(3);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(342);
            var4 = var1.getlocal(3);
            var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("object_hook"), var4);
            var4 = null;
         }

         var1.setline(343);
         var4 = var1.getlocal(7);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(344);
            var4 = var1.getlocal(7);
            var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("object_pairs_hook"), var4);
            var4 = null;
         }

         var1.setline(345);
         var4 = var1.getlocal(4);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(346);
            var4 = var1.getlocal(4);
            var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("parse_float"), var4);
            var4 = null;
         }

         var1.setline(347);
         var4 = var1.getlocal(5);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(348);
            var4 = var1.getlocal(5);
            var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("parse_int"), var4);
            var4 = null;
         }

         var1.setline(349);
         var4 = var1.getlocal(6);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(350);
            var4 = var1.getlocal(6);
            var1.getlocal(8).__setitem__((PyObject)PyString.fromInterned("parse_constant"), var4);
            var4 = null;
         }

         var1.setline(351);
         var10000 = var1.getlocal(2);
         PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
         String[] var5 = new String[]{"encoding"};
         var10000 = var10000._callextra(var6, var5, (PyObject)null, var1.getlocal(8));
         var4 = null;
         var3 = var10000.__getattr__("decode").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public json$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"obj", "fp", "skipkeys", "ensure_ascii", "check_circular", "allow_nan", "cls", "indent", "separators", "encoding", "default", "sort_keys", "kw", "iterable", "chunk"};
      dump$1 = Py.newCode(13, var2, var1, "dump", 122, false, true, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj", "skipkeys", "ensure_ascii", "check_circular", "allow_nan", "cls", "indent", "separators", "encoding", "default", "sort_keys", "kw"};
      dumps$2 = Py.newCode(12, var2, var1, "dumps", 193, false, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fp", "encoding", "cls", "object_hook", "parse_float", "parse_int", "parse_constant", "object_pairs_hook", "kw"};
      load$3 = Py.newCode(9, var2, var1, "load", 257, false, true, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "encoding", "cls", "object_hook", "parse_float", "parse_int", "parse_constant", "object_pairs_hook", "kw"};
      loads$4 = Py.newCode(9, var2, var1, "loads", 293, false, true, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new json$py("json$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(json$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.dump$1(var2, var3);
         case 2:
            return this.dumps$2(var2, var3);
         case 3:
            return this.load$3(var2, var3);
         case 4:
            return this.loads$4(var2, var3);
         default:
            return null;
      }
   }
}
