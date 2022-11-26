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
@Filename("uuid.py")
public class uuid$py extends PyFunctionTable implements PyRunnable {
   static uuid$py self;
   static final PyCode f$0;
   static final PyCode UUID$1;
   static final PyCode __init__$2;
   static final PyCode __cmp__$3;
   static final PyCode __hash__$4;
   static final PyCode __int__$5;
   static final PyCode __repr__$6;
   static final PyCode __setattr__$7;
   static final PyCode __str__$8;
   static final PyCode get_bytes$9;
   static final PyCode get_bytes_le$10;
   static final PyCode get_fields$11;
   static final PyCode get_time_low$12;
   static final PyCode get_time_mid$13;
   static final PyCode get_time_hi_version$14;
   static final PyCode get_clock_seq_hi_variant$15;
   static final PyCode get_clock_seq_low$16;
   static final PyCode get_time$17;
   static final PyCode get_clock_seq$18;
   static final PyCode get_node$19;
   static final PyCode get_hex$20;
   static final PyCode get_urn$21;
   static final PyCode get_variant$22;
   static final PyCode get_version$23;
   static final PyCode _find_mac$24;
   static final PyCode _ifconfig_getnode$25;
   static final PyCode f$26;
   static final PyCode f$27;
   static final PyCode f$28;
   static final PyCode _ipconfig_getnode$29;
   static final PyCode _netbios_getnode$30;
   static final PyCode _unixdll_getnode$31;
   static final PyCode _windll_getnode$32;
   static final PyCode _random_getnode$33;
   static final PyCode getnode$34;
   static final PyCode uuid1$35;
   static final PyCode uuid3$36;
   static final PyCode uuid4$37;
   static final PyCode uuid5$38;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("UUID objects (universally unique identifiers) according to RFC 4122.\n\nThis module provides immutable UUID objects (class UUID) and the functions\nuuid1(), uuid3(), uuid4(), uuid5() for generating version 1, 3, 4, and 5\nUUIDs as specified in RFC 4122.\n\nIf all you want is a unique ID, you should probably call uuid1() or uuid4().\nNote that uuid1() may compromise privacy since it creates a UUID containing\nthe computer's network address.  uuid4() creates a random UUID.\n\nTypical usage:\n\n    >>> import uuid\n\n    # make a UUID based on the host ID and current time\n    >>> uuid.uuid1()\n    UUID('a8098c1a-f86e-11da-bd1a-00112444be1e')\n\n    # make a UUID using an MD5 hash of a namespace UUID and a name\n    >>> uuid.uuid3(uuid.NAMESPACE_DNS, 'python.org')\n    UUID('6fa459ea-ee8a-3ca4-894e-db77e160355e')\n\n    # make a random UUID\n    >>> uuid.uuid4()\n    UUID('16fd2706-8baf-433b-82eb-8c7fada847da')\n\n    # make a UUID using a SHA-1 hash of a namespace UUID and a name\n    >>> uuid.uuid5(uuid.NAMESPACE_DNS, 'python.org')\n    UUID('886313e1-3b8a-5372-9b90-0c9aee199e5d')\n\n    # make a UUID from a string of hex digits (braces and hyphens ignored)\n    >>> x = uuid.UUID('{00010203-0405-0607-0809-0a0b0c0d0e0f}')\n\n    # convert a UUID to a string of hex digits in standard form\n    >>> str(x)\n    '00010203-0405-0607-0809-0a0b0c0d0e0f'\n\n    # get the raw 16 bytes of the UUID\n    >>> x.bytes\n    '\\x00\\x01\\x02\\x03\\x04\\x05\\x06\\x07\\x08\\t\\n\\x0b\\x0c\\r\\x0e\\x0f'\n\n    # make a UUID from a 16-byte string\n    >>> uuid.UUID(bytes=x.bytes)\n    UUID('00010203-0405-0607-0809-0a0b0c0d0e0f')\n"));
      var1.setline(45);
      PyString.fromInterned("UUID objects (universally unique identifiers) according to RFC 4122.\n\nThis module provides immutable UUID objects (class UUID) and the functions\nuuid1(), uuid3(), uuid4(), uuid5() for generating version 1, 3, 4, and 5\nUUIDs as specified in RFC 4122.\n\nIf all you want is a unique ID, you should probably call uuid1() or uuid4().\nNote that uuid1() may compromise privacy since it creates a UUID containing\nthe computer's network address.  uuid4() creates a random UUID.\n\nTypical usage:\n\n    >>> import uuid\n\n    # make a UUID based on the host ID and current time\n    >>> uuid.uuid1()\n    UUID('a8098c1a-f86e-11da-bd1a-00112444be1e')\n\n    # make a UUID using an MD5 hash of a namespace UUID and a name\n    >>> uuid.uuid3(uuid.NAMESPACE_DNS, 'python.org')\n    UUID('6fa459ea-ee8a-3ca4-894e-db77e160355e')\n\n    # make a random UUID\n    >>> uuid.uuid4()\n    UUID('16fd2706-8baf-433b-82eb-8c7fada847da')\n\n    # make a UUID using a SHA-1 hash of a namespace UUID and a name\n    >>> uuid.uuid5(uuid.NAMESPACE_DNS, 'python.org')\n    UUID('886313e1-3b8a-5372-9b90-0c9aee199e5d')\n\n    # make a UUID from a string of hex digits (braces and hyphens ignored)\n    >>> x = uuid.UUID('{00010203-0405-0607-0809-0a0b0c0d0e0f}')\n\n    # convert a UUID to a string of hex digits in standard form\n    >>> str(x)\n    '00010203-0405-0607-0809-0a0b0c0d0e0f'\n\n    # get the raw 16 bytes of the UUID\n    >>> x.bytes\n    '\\x00\\x01\\x02\\x03\\x04\\x05\\x06\\x07\\x08\\t\\n\\x0b\\x0c\\r\\x0e\\x0f'\n\n    # make a UUID from a 16-byte string\n    >>> uuid.UUID(bytes=x.bytes)\n    UUID('00010203-0405-0607-0809-0a0b0c0d0e0f')\n");
      var1.setline(47);
      PyString var3 = PyString.fromInterned("Ka-Ping Yee <ping@zesty.ca>");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(49);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("reserved for NCS compatibility"), PyString.fromInterned("specified in RFC 4122"), PyString.fromInterned("reserved for Microsoft compatibility"), PyString.fromInterned("reserved for future definition")});
      PyObject[] var4 = Py.unpackSequence(var9, 4);
      PyObject var5 = var4[0];
      var1.setlocal("RESERVED_NCS", var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal("RFC_4122", var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal("RESERVED_MICROSOFT", var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal("RESERVED_FUTURE", var5);
      var5 = null;
      var3 = null;
      var1.setline(53);
      PyObject[] var10 = new PyObject[]{var1.getname("object")};
      PyObject var11 = Py.makeClass("UUID", var10, UUID$1);
      var1.setlocal("UUID", var11);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(294);
      var10 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var10, _find_mac$24, (PyObject)null);
      var1.setlocal("_find_mac", var12);
      var3 = null;
      var1.setline(316);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, _ifconfig_getnode$25, PyString.fromInterned("Get the hardware address on Unix by running ifconfig."));
      var1.setlocal("_ifconfig_getnode", var12);
      var3 = null;
      var1.setline(340);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, _ipconfig_getnode$29, PyString.fromInterned("Get the hardware address on Windows by running ipconfig.exe."));
      var1.setlocal("_ipconfig_getnode", var12);
      var3 = null;
      var1.setline(364);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, _netbios_getnode$30, PyString.fromInterned("Get the hardware address on Windows using NetBIOS calls.\n    See http://support.microsoft.com/kb/118623 for details."));
      var1.setlocal("_netbios_getnode", var12);
      var3 = null;
      var1.setline(396);
      PyObject var13 = var1.getname("None");
      var1.setlocal("_uuid_generate_random", var13);
      var1.setlocal("_uuid_generate_time", var13);
      var1.setlocal("_UuidCreate", var13);

      try {
         var1.setline(398);
         var13 = imp.importOne("ctypes", var1, -1);
         var1.setlocal("ctypes", var13);
         var3 = null;
         var13 = imp.importOne("ctypes.util", var1, -1);
         var1.setlocal("ctypes", var13);
         var3 = null;
         var1.setline(402);
         var13 = (new PyList(new PyObject[]{PyString.fromInterned("uuid"), PyString.fromInterned("c")})).__iter__();

         while(true) {
            var1.setline(402);
            var11 = var13.__iternext__();
            if (var11 == null) {
               var1.setline(419);
               var13 = imp.importOne("sys", var1, -1);
               var1.setlocal("sys", var13);
               var3 = null;
               var1.setline(420);
               var13 = var1.getname("sys").__getattr__("platform");
               PyObject var10000 = var13._eq(PyString.fromInterned("darwin"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(421);
                  var13 = imp.importOne("os", var1, -1);
                  var1.setlocal("os", var13);
                  var3 = null;
                  var1.setline(422);
                  var13 = var1.getname("int").__call__(var2, var1.getname("os").__getattr__("uname").__call__(var2).__getitem__(Py.newInteger(2)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(0)));
                  var10000 = var13._ge(Py.newInteger(9));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(423);
                     var13 = var1.getname("None");
                     var1.setlocal("_uuid_generate_random", var13);
                     var1.setlocal("_uuid_generate_time", var13);
                  }
               }

               try {
                  var1.setline(434);
                  var13 = var1.getname("ctypes").__getattr__("windll").__getattr__("rpcrt4");
                  var1.setlocal("lib", var13);
                  var3 = null;
               } catch (Throwable var6) {
                  Py.setException(var6, var1);
                  var1.setline(436);
                  var11 = var1.getname("None");
                  var1.setlocal("lib", var11);
                  var4 = null;
               }

               var1.setline(437);
               var13 = var1.getname("getattr").__call__((ThreadState)var2, var1.getname("lib"), (PyObject)PyString.fromInterned("UuidCreateSequential"), (PyObject)var1.getname("getattr").__call__((ThreadState)var2, var1.getname("lib"), (PyObject)PyString.fromInterned("UuidCreate"), (PyObject)var1.getname("None")));
               var1.setlocal("_UuidCreate", var13);
               var3 = null;
               break;
            }

            var1.setlocal("libname", var11);

            try {
               var1.setline(404);
               var5 = var1.getname("ctypes").__getattr__("CDLL").__call__(var2, var1.getname("ctypes").__getattr__("util").__getattr__("find_library").__call__(var2, var1.getname("libname")));
               var1.setlocal("lib", var5);
               var5 = null;
            } catch (Throwable var7) {
               Py.setException(var7, var1);
               continue;
            }

            var1.setline(407);
            if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("lib"), (PyObject)PyString.fromInterned("uuid_generate_random")).__nonzero__()) {
               var1.setline(408);
               var5 = var1.getname("lib").__getattr__("uuid_generate_random");
               var1.setlocal("_uuid_generate_random", var5);
               var5 = null;
            }

            var1.setline(409);
            if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("lib"), (PyObject)PyString.fromInterned("uuid_generate_time")).__nonzero__()) {
               var1.setline(410);
               var5 = var1.getname("lib").__getattr__("uuid_generate_time");
               var1.setlocal("_uuid_generate_time", var5);
               var5 = null;
            }
         }
      } catch (Throwable var8) {
         Py.setException(var8, var1);
         var1.setline(440);
      }

      var1.setline(442);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, _unixdll_getnode$31, PyString.fromInterned("Get the hardware address on Unix using ctypes."));
      var1.setlocal("_unixdll_getnode", var12);
      var3 = null;
      var1.setline(448);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, _windll_getnode$32, PyString.fromInterned("Get the hardware address on Windows using ctypes."));
      var1.setlocal("_windll_getnode", var12);
      var3 = null;
      var1.setline(454);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, _random_getnode$33, PyString.fromInterned("Get a random node ID, with eighth bit set as suggested by RFC 4122."));
      var1.setlocal("_random_getnode", var12);
      var3 = null;
      var1.setline(459);
      var13 = var1.getname("None");
      var1.setlocal("_node", var13);
      var3 = null;
      var1.setline(461);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, getnode$34, PyString.fromInterned("Get the hardware address as a 48-bit positive integer.\n\n    The first time this runs, it may launch a separate program, which could\n    be quite slow.  If all attempts to obtain the hardware address fail, we\n    choose a random 48-bit number with its eighth bit set to 1 as recommended\n    in RFC 4122.\n    "));
      var1.setlocal("getnode", var12);
      var3 = null;
      var1.setline(488);
      var13 = var1.getname("None");
      var1.setlocal("_last_timestamp", var13);
      var3 = null;
      var1.setline(490);
      var10 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var12 = new PyFunction(var1.f_globals, var10, uuid1$35, PyString.fromInterned("Generate a UUID from a host ID, sequence number, and the current time.\n    If 'node' is not given, getnode() is used to obtain the hardware\n    address.  If 'clock_seq' is given, it is used as the sequence number;\n    otherwise a random 14-bit sequence number is chosen."));
      var1.setlocal("uuid1", var12);
      var3 = null;
      var1.setline(525);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, uuid3$36, PyString.fromInterned("Generate a UUID from the MD5 hash of a namespace UUID and a name."));
      var1.setlocal("uuid3", var12);
      var3 = null;
      var1.setline(531);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, uuid4$37, PyString.fromInterned("Generate a random UUID."));
      var1.setlocal("uuid4", var12);
      var3 = null;
      var1.setline(549);
      var10 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var10, uuid5$38, PyString.fromInterned("Generate a UUID from the SHA-1 hash of a namespace UUID and a name."));
      var1.setlocal("uuid5", var12);
      var3 = null;
      var1.setline(557);
      var13 = var1.getname("UUID").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("6ba7b810-9dad-11d1-80b4-00c04fd430c8"));
      var1.setlocal("NAMESPACE_DNS", var13);
      var3 = null;
      var1.setline(558);
      var13 = var1.getname("UUID").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("6ba7b811-9dad-11d1-80b4-00c04fd430c8"));
      var1.setlocal("NAMESPACE_URL", var13);
      var3 = null;
      var1.setline(559);
      var13 = var1.getname("UUID").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("6ba7b812-9dad-11d1-80b4-00c04fd430c8"));
      var1.setlocal("NAMESPACE_OID", var13);
      var3 = null;
      var1.setline(560);
      var13 = var1.getname("UUID").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("6ba7b814-9dad-11d1-80b4-00c04fd430c8"));
      var1.setlocal("NAMESPACE_X500", var13);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UUID$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Instances of the UUID class represent UUIDs as specified in RFC 4122.\n    UUID objects are immutable, hashable, and usable as dictionary keys.\n    Converting a UUID to a string with str() yields something in the form\n    '12345678-1234-1234-1234-123456789abc'.  The UUID constructor accepts\n    five possible forms: a similar string of hexadecimal digits, or a tuple\n    of six integer fields (with 32-bit, 16-bit, 16-bit, 8-bit, 8-bit, and\n    48-bit values respectively) as an argument named 'fields', or a string\n    of 16 bytes (with all the integer fields in big-endian order) as an\n    argument named 'bytes', or a string of 16 bytes (with the first three\n    fields in little-endian order) as an argument named 'bytes_le', or a\n    single 128-bit integer as an argument named 'int'.\n\n    UUIDs have these read-only attributes:\n\n        bytes       the UUID as a 16-byte string (containing the six\n                    integer fields in big-endian byte order)\n\n        bytes_le    the UUID as a 16-byte string (with time_low, time_mid,\n                    and time_hi_version in little-endian byte order)\n\n        fields      a tuple of the six integer fields of the UUID,\n                    which are also available as six individual attributes\n                    and two derived attributes:\n\n            time_low                the first 32 bits of the UUID\n            time_mid                the next 16 bits of the UUID\n            time_hi_version         the next 16 bits of the UUID\n            clock_seq_hi_variant    the next 8 bits of the UUID\n            clock_seq_low           the next 8 bits of the UUID\n            node                    the last 48 bits of the UUID\n\n            time                    the 60-bit timestamp\n            clock_seq               the 14-bit sequence number\n\n        hex         the UUID as a 32-character hexadecimal string\n\n        int         the UUID as a 128-bit integer\n\n        urn         the UUID as a URN as specified in RFC 4122\n\n        variant     the UUID variant (one of the constants RESERVED_NCS,\n                    RFC_4122, RESERVED_MICROSOFT, or RESERVED_FUTURE)\n\n        version     the UUID version number (1 through 5, meaningful only\n                    when the variant is RFC_4122)\n    "));
      var1.setline(99);
      PyString.fromInterned("Instances of the UUID class represent UUIDs as specified in RFC 4122.\n    UUID objects are immutable, hashable, and usable as dictionary keys.\n    Converting a UUID to a string with str() yields something in the form\n    '12345678-1234-1234-1234-123456789abc'.  The UUID constructor accepts\n    five possible forms: a similar string of hexadecimal digits, or a tuple\n    of six integer fields (with 32-bit, 16-bit, 16-bit, 8-bit, 8-bit, and\n    48-bit values respectively) as an argument named 'fields', or a string\n    of 16 bytes (with all the integer fields in big-endian order) as an\n    argument named 'bytes', or a string of 16 bytes (with the first three\n    fields in little-endian order) as an argument named 'bytes_le', or a\n    single 128-bit integer as an argument named 'int'.\n\n    UUIDs have these read-only attributes:\n\n        bytes       the UUID as a 16-byte string (containing the six\n                    integer fields in big-endian byte order)\n\n        bytes_le    the UUID as a 16-byte string (with time_low, time_mid,\n                    and time_hi_version in little-endian byte order)\n\n        fields      a tuple of the six integer fields of the UUID,\n                    which are also available as six individual attributes\n                    and two derived attributes:\n\n            time_low                the first 32 bits of the UUID\n            time_mid                the next 16 bits of the UUID\n            time_hi_version         the next 16 bits of the UUID\n            clock_seq_hi_variant    the next 8 bits of the UUID\n            clock_seq_low           the next 8 bits of the UUID\n            node                    the last 48 bits of the UUID\n\n            time                    the 60-bit timestamp\n            clock_seq               the 14-bit sequence number\n\n        hex         the UUID as a 32-character hexadecimal string\n\n        int         the UUID as a 128-bit integer\n\n        urn         the UUID as a URN as specified in RFC 4122\n\n        variant     the UUID variant (one of the constants RESERVED_NCS,\n                    RFC_4122, RESERVED_MICROSOFT, or RESERVED_FUTURE)\n\n        version     the UUID version number (1 through 5, meaningful only\n                    when the variant is RFC_4122)\n    ");
      var1.setline(101);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Create a UUID from either a string of 32 hexadecimal digits,\n        a string of 16 bytes as the 'bytes' argument, a string of 16 bytes\n        in little-endian order as the 'bytes_le' argument, a tuple of six\n        integers (32-bit time_low, 16-bit time_mid, 16-bit time_hi_version,\n        8-bit clock_seq_hi_variant, 8-bit clock_seq_low, 48-bit node) as\n        the 'fields' argument, or a single 128-bit integer as the 'int'\n        argument.  When a string of hex digits is given, curly braces,\n        hyphens, and a URN prefix are all optional.  For example, these\n        expressions all yield the same UUID:\n\n        UUID('{12345678-1234-5678-1234-567812345678}')\n        UUID('12345678123456781234567812345678')\n        UUID('urn:uuid:12345678-1234-5678-1234-567812345678')\n        UUID(bytes='\\x12\\x34\\x56\\x78'*4)\n        UUID(bytes_le='\\x78\\x56\\x34\\x12\\x34\\x12\\x78\\x56' +\n                      '\\x12\\x34\\x56\\x78\\x12\\x34\\x56\\x78')\n        UUID(fields=(0x12345678, 0x1234, 0x5678, 0x12, 0x34, 0x567812345678))\n        UUID(int=0x12345678123456781234567812345678)\n\n        Exactly one of 'hex', 'bytes', 'bytes_le', 'fields', or 'int' must\n        be given.  The 'version' argument is optional; if given, the resulting\n        UUID will have its variant and version set according to RFC 4122,\n        overriding the given 'hex', 'bytes', 'bytes_le', 'fields', or 'int'.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(180);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$3, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$4, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      var1.setline(188);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __int__$5, (PyObject)null);
      var1.setlocal("__int__", var4);
      var3 = null;
      var1.setline(191);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$6, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(194);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setattr__$7, (PyObject)null);
      var1.setlocal("__setattr__", var4);
      var3 = null;
      var1.setline(197);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$8, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_bytes$9, (PyObject)null);
      var1.setlocal("get_bytes", var4);
      var3 = null;
      var1.setline(208);
      PyObject var5 = var1.getname("property").__call__(var2, var1.getname("get_bytes"));
      var1.setlocal("bytes", var5);
      var3 = null;
      var1.setline(210);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_bytes_le$10, (PyObject)null);
      var1.setlocal("get_bytes_le", var4);
      var3 = null;
      var1.setline(215);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_bytes_le"));
      var1.setlocal("bytes_le", var5);
      var3 = null;
      var1.setline(217);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_fields$11, (PyObject)null);
      var1.setlocal("get_fields", var4);
      var3 = null;
      var1.setline(221);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_fields"));
      var1.setlocal("fields", var5);
      var3 = null;
      var1.setline(223);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_time_low$12, (PyObject)null);
      var1.setlocal("get_time_low", var4);
      var3 = null;
      var1.setline(226);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_time_low"));
      var1.setlocal("time_low", var5);
      var3 = null;
      var1.setline(228);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_time_mid$13, (PyObject)null);
      var1.setlocal("get_time_mid", var4);
      var3 = null;
      var1.setline(231);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_time_mid"));
      var1.setlocal("time_mid", var5);
      var3 = null;
      var1.setline(233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_time_hi_version$14, (PyObject)null);
      var1.setlocal("get_time_hi_version", var4);
      var3 = null;
      var1.setline(236);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_time_hi_version"));
      var1.setlocal("time_hi_version", var5);
      var3 = null;
      var1.setline(238);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_clock_seq_hi_variant$15, (PyObject)null);
      var1.setlocal("get_clock_seq_hi_variant", var4);
      var3 = null;
      var1.setline(241);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_clock_seq_hi_variant"));
      var1.setlocal("clock_seq_hi_variant", var5);
      var3 = null;
      var1.setline(243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_clock_seq_low$16, (PyObject)null);
      var1.setlocal("get_clock_seq_low", var4);
      var3 = null;
      var1.setline(246);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_clock_seq_low"));
      var1.setlocal("clock_seq_low", var5);
      var3 = null;
      var1.setline(248);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_time$17, (PyObject)null);
      var1.setlocal("get_time", var4);
      var3 = null;
      var1.setline(252);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_time"));
      var1.setlocal("time", var5);
      var3 = null;
      var1.setline(254);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_clock_seq$18, (PyObject)null);
      var1.setlocal("get_clock_seq", var4);
      var3 = null;
      var1.setline(258);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_clock_seq"));
      var1.setlocal("clock_seq", var5);
      var3 = null;
      var1.setline(260);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_node$19, (PyObject)null);
      var1.setlocal("get_node", var4);
      var3 = null;
      var1.setline(263);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_node"));
      var1.setlocal("node", var5);
      var3 = null;
      var1.setline(265);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_hex$20, (PyObject)null);
      var1.setlocal("get_hex", var4);
      var3 = null;
      var1.setline(268);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_hex"));
      var1.setlocal("hex", var5);
      var3 = null;
      var1.setline(270);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_urn$21, (PyObject)null);
      var1.setlocal("get_urn", var4);
      var3 = null;
      var1.setline(273);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_urn"));
      var1.setlocal("urn", var5);
      var3 = null;
      var1.setline(275);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_variant$22, (PyObject)null);
      var1.setlocal("get_variant", var4);
      var3 = null;
      var1.setline(285);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_variant"));
      var1.setlocal("variant", var5);
      var3 = null;
      var1.setline(287);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_version$23, (PyObject)null);
      var1.setlocal("get_version", var4);
      var3 = null;
      var1.setline(292);
      var5 = var1.getname("property").__call__(var2, var1.getname("get_version"));
      var1.setlocal("version", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyString.fromInterned("Create a UUID from either a string of 32 hexadecimal digits,\n        a string of 16 bytes as the 'bytes' argument, a string of 16 bytes\n        in little-endian order as the 'bytes_le' argument, a tuple of six\n        integers (32-bit time_low, 16-bit time_mid, 16-bit time_hi_version,\n        8-bit clock_seq_hi_variant, 8-bit clock_seq_low, 48-bit node) as\n        the 'fields' argument, or a single 128-bit integer as the 'int'\n        argument.  When a string of hex digits is given, curly braces,\n        hyphens, and a URN prefix are all optional.  For example, these\n        expressions all yield the same UUID:\n\n        UUID('{12345678-1234-5678-1234-567812345678}')\n        UUID('12345678123456781234567812345678')\n        UUID('urn:uuid:12345678-1234-5678-1234-567812345678')\n        UUID(bytes='\\x12\\x34\\x56\\x78'*4)\n        UUID(bytes_le='\\x78\\x56\\x34\\x12\\x34\\x12\\x78\\x56' +\n                      '\\x12\\x34\\x56\\x78\\x12\\x34\\x56\\x78')\n        UUID(fields=(0x12345678, 0x1234, 0x5678, 0x12, 0x34, 0x567812345678))\n        UUID(int=0x12345678123456781234567812345678)\n\n        Exactly one of 'hex', 'bytes', 'bytes_le', 'fields', or 'int' must\n        be given.  The 'version' argument is optional; if given, the resulting\n        UUID will have its variant and version set according to RFC 4122,\n        overriding the given 'hex', 'bytes', 'bytes_le', 'fields', or 'int'.\n        ");
      var1.setline(128);
      PyObject var3 = (new PyList(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)})).__getattr__("count").__call__(var2, var1.getglobal("None"));
      PyObject var10000 = var3._ne(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(129);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("need one of hex, bytes, bytes_le, fields, or int")));
      } else {
         var1.setline(130);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(131);
            var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("urn:"), (PyObject)PyString.fromInterned("")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("uuid:"), (PyObject)PyString.fromInterned(""));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(132);
            var3 = var1.getlocal(1).__getattr__("strip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("{}")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned(""));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(133);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10000 = var3._ne(Py.newInteger(32));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(134);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("badly formed hexadecimal UUID string")));
            }

            var1.setline(135);
            var3 = var1.getglobal("long").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(16));
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(136);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(137);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var3._ne(Py.newInteger(16));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(138);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bytes_le is not a 16-char string")));
            }

            var1.setline(139);
            var3 = var1.getlocal(3).__getitem__(Py.newInteger(3))._add(var1.getlocal(3).__getitem__(Py.newInteger(2)))._add(var1.getlocal(3).__getitem__(Py.newInteger(1)))._add(var1.getlocal(3).__getitem__(Py.newInteger(0)))._add(var1.getlocal(3).__getitem__(Py.newInteger(5)))._add(var1.getlocal(3).__getitem__(Py.newInteger(4)))._add(var1.getlocal(3).__getitem__(Py.newInteger(7)))._add(var1.getlocal(3).__getitem__(Py.newInteger(6)))._add(var1.getlocal(3).__getslice__(Py.newInteger(8), (PyObject)null, (PyObject)null));
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(142);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(143);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._ne(Py.newInteger(16));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(144);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bytes is not a 16-char string")));
            }

            var1.setline(145);
            var3 = var1.getglobal("long").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%02x")._mul(Py.newInteger(16))._mod(var1.getglobal("tuple").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("ord"), var1.getlocal(2)))), (PyObject)Py.newInteger(16));
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(146);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         PyObject var10001;
         PyObject var6;
         PyInteger var7;
         PyInteger var8;
         if (var10000.__nonzero__()) {
            var1.setline(147);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
            var10000 = var3._ne(Py.newInteger(6));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(148);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fields is not a 6-tuple")));
            }

            var1.setline(149);
            var3 = var1.getlocal(4);
            PyObject[] var4 = Py.unpackSequence(var3, 6);
            PyObject var5 = var4[0];
            var1.setlocal(7, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(8, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(9, var5);
            var5 = null;
            var5 = var4[3];
            var1.setlocal(10, var5);
            var5 = null;
            var5 = var4[4];
            var1.setlocal(11, var5);
            var5 = null;
            var5 = var4[5];
            var1.setlocal(12, var5);
            var5 = null;
            var3 = null;
            var1.setline(151);
            var7 = Py.newInteger(0);
            var10001 = var1.getlocal(7);
            var8 = var7;
            var3 = var10001;
            if ((var6 = var8._le(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(1)._lshift(Py.newLong("32")));
            }

            var3 = null;
            if (var6.__not__().__nonzero__()) {
               var1.setline(152);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("field 1 out of range (need a 32-bit value)")));
            }

            var1.setline(153);
            var7 = Py.newInteger(0);
            var10001 = var1.getlocal(8);
            var8 = var7;
            var3 = var10001;
            if ((var6 = var8._le(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(1)._lshift(Py.newLong("16")));
            }

            var3 = null;
            if (var6.__not__().__nonzero__()) {
               var1.setline(154);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("field 2 out of range (need a 16-bit value)")));
            }

            var1.setline(155);
            var7 = Py.newInteger(0);
            var10001 = var1.getlocal(9);
            var8 = var7;
            var3 = var10001;
            if ((var6 = var8._le(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(1)._lshift(Py.newLong("16")));
            }

            var3 = null;
            if (var6.__not__().__nonzero__()) {
               var1.setline(156);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("field 3 out of range (need a 16-bit value)")));
            }

            var1.setline(157);
            var7 = Py.newInteger(0);
            var10001 = var1.getlocal(10);
            var8 = var7;
            var3 = var10001;
            if ((var6 = var8._le(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(1)._lshift(Py.newLong("8")));
            }

            var3 = null;
            if (var6.__not__().__nonzero__()) {
               var1.setline(158);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("field 4 out of range (need an 8-bit value)")));
            }

            var1.setline(159);
            var7 = Py.newInteger(0);
            var10001 = var1.getlocal(11);
            var8 = var7;
            var3 = var10001;
            if ((var6 = var8._le(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(1)._lshift(Py.newLong("8")));
            }

            var3 = null;
            if (var6.__not__().__nonzero__()) {
               var1.setline(160);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("field 5 out of range (need an 8-bit value)")));
            }

            var1.setline(161);
            var7 = Py.newInteger(0);
            var10001 = var1.getlocal(12);
            var8 = var7;
            var3 = var10001;
            if ((var6 = var8._le(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(1)._lshift(Py.newLong("48")));
            }

            var3 = null;
            if (var6.__not__().__nonzero__()) {
               var1.setline(162);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("field 6 out of range (need a 48-bit value)")));
            }

            var1.setline(163);
            var3 = var1.getlocal(10)._lshift(Py.newLong("8"))._or(var1.getlocal(11));
            var1.setlocal(13, var3);
            var3 = null;
            var1.setline(164);
            var3 = var1.getlocal(7)._lshift(Py.newLong("96"))._or(var1.getlocal(8)._lshift(Py.newLong("80")))._or(var1.getlocal(9)._lshift(Py.newLong("64")))._or(var1.getlocal(13)._lshift(Py.newLong("48")))._or(var1.getlocal(12));
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(166);
         var3 = var1.getlocal(5);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(167);
            var7 = Py.newInteger(0);
            var10001 = var1.getlocal(5);
            var8 = var7;
            var3 = var10001;
            if ((var6 = var8._le(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(1)._lshift(Py.newLong("128")));
            }

            var3 = null;
            if (var6.__not__().__nonzero__()) {
               var1.setline(168);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("int is out of range (need a 128-bit value)")));
            }
         }

         var1.setline(169);
         var3 = var1.getlocal(6);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(170);
            var7 = Py.newInteger(1);
            var10001 = var1.getlocal(6);
            var8 = var7;
            var3 = var10001;
            if ((var6 = var8._le(var10001)).__nonzero__()) {
               var6 = var3._le(Py.newInteger(5));
            }

            var3 = null;
            if (var6.__not__().__nonzero__()) {
               var1.setline(171);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("illegal version number")));
            }

            var1.setline(173);
            var3 = var1.getlocal(5);
            var3 = var3._iand(Py.newInteger(49152)._lshift(Py.newLong("48")).__invert__());
            var1.setlocal(5, var3);
            var1.setline(174);
            var3 = var1.getlocal(5);
            var3 = var3._ior(Py.newInteger(32768)._lshift(Py.newLong("48")));
            var1.setlocal(5, var3);
            var1.setline(176);
            var3 = var1.getlocal(5);
            var3 = var3._iand(Py.newInteger(61440)._lshift(Py.newLong("64")).__invert__());
            var1.setlocal(5, var3);
            var1.setline(177);
            var3 = var1.getlocal(5);
            var3 = var3._ior(var1.getlocal(6)._lshift(Py.newLong("76")));
            var1.setlocal(5, var3);
         }

         var1.setline(178);
         var3 = var1.getlocal(5);
         var1.getlocal(0).__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("int"), var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __cmp__$3(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("UUID")).__nonzero__()) {
         var1.setline(182);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("int"), var1.getlocal(1).__getattr__("int"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(183);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __hash__$4(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(0).__getattr__("int"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __int__$5(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var3 = var1.getlocal(0).__getattr__("int");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$6(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      PyObject var3 = PyString.fromInterned("UUID(%r)")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __setattr__$7(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UUID objects are immutable")));
   }

   public PyObject __str__$8(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyObject var3 = PyString.fromInterned("%032x")._mod(var1.getlocal(0).__getattr__("int"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(199);
      var3 = PyString.fromInterned("%s-%s-%s-%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(8), (PyObject)null), var1.getlocal(1).__getslice__(Py.newInteger(8), Py.newInteger(12), (PyObject)null), var1.getlocal(1).__getslice__(Py.newInteger(12), Py.newInteger(16), (PyObject)null), var1.getlocal(1).__getslice__(Py.newInteger(16), Py.newInteger(20), (PyObject)null), var1.getlocal(1).__getslice__(Py.newInteger(20), (PyObject)null, (PyObject)null)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_bytes$9(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(204);
      PyObject var6 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)Py.newInteger(128), (PyObject)Py.newInteger(8)).__iter__();

      while(true) {
         var1.setline(204);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(206);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(205);
         PyObject var5 = var1.getglobal("chr").__call__(var2, var1.getlocal(0).__getattr__("int")._rshift(var1.getlocal(2))._and(Py.newInteger(255)))._add(var1.getlocal(1));
         var1.setlocal(1, var5);
         var5 = null;
      }
   }

   public PyObject get_bytes_le$10(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyObject var3 = var1.getlocal(0).__getattr__("bytes");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(212);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(3))._add(var1.getlocal(1).__getitem__(Py.newInteger(2)))._add(var1.getlocal(1).__getitem__(Py.newInteger(1)))._add(var1.getlocal(1).__getitem__(Py.newInteger(0)))._add(var1.getlocal(1).__getitem__(Py.newInteger(5)))._add(var1.getlocal(1).__getitem__(Py.newInteger(4)))._add(var1.getlocal(1).__getitem__(Py.newInteger(7)))._add(var1.getlocal(1).__getitem__(Py.newInteger(6)))._add(var1.getlocal(1).__getslice__(Py.newInteger(8), (PyObject)null, (PyObject)null));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_fields$11(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("time_low"), var1.getlocal(0).__getattr__("time_mid"), var1.getlocal(0).__getattr__("time_hi_version"), var1.getlocal(0).__getattr__("clock_seq_hi_variant"), var1.getlocal(0).__getattr__("clock_seq_low"), var1.getlocal(0).__getattr__("node")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_time_low$12(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyObject var3 = var1.getlocal(0).__getattr__("int")._rshift(Py.newLong("96"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_time_mid$13(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyObject var3 = var1.getlocal(0).__getattr__("int")._rshift(Py.newLong("80"))._and(Py.newInteger(65535));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_time_hi_version$14(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyObject var3 = var1.getlocal(0).__getattr__("int")._rshift(Py.newLong("64"))._and(Py.newInteger(65535));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_clock_seq_hi_variant$15(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyObject var3 = var1.getlocal(0).__getattr__("int")._rshift(Py.newLong("56"))._and(Py.newInteger(255));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_clock_seq_low$16(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyObject var3 = var1.getlocal(0).__getattr__("int")._rshift(Py.newLong("48"))._and(Py.newInteger(255));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_time$17(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyObject var3 = var1.getlocal(0).__getattr__("time_hi_version")._and(Py.newLong("4095"))._lshift(Py.newLong("48"))._or(var1.getlocal(0).__getattr__("time_mid")._lshift(Py.newLong("32")))._or(var1.getlocal(0).__getattr__("time_low"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_clock_seq$18(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyObject var3 = var1.getlocal(0).__getattr__("clock_seq_hi_variant")._and(Py.newLong("63"))._lshift(Py.newLong("8"))._or(var1.getlocal(0).__getattr__("clock_seq_low"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_node$19(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyObject var3 = var1.getlocal(0).__getattr__("int")._and(Py.newLong("281474976710655"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_hex$20(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyObject var3 = PyString.fromInterned("%032x")._mod(var1.getlocal(0).__getattr__("int"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_urn$21(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var3 = PyString.fromInterned("urn:uuid:")._add(var1.getglobal("str").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_variant$22(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("int")._and(Py.newInteger(32768)._lshift(Py.newLong("48"))).__not__().__nonzero__()) {
         var1.setline(277);
         var3 = var1.getglobal("RESERVED_NCS");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(278);
         if (var1.getlocal(0).__getattr__("int")._and(Py.newInteger(16384)._lshift(Py.newLong("48"))).__not__().__nonzero__()) {
            var1.setline(279);
            var3 = var1.getglobal("RFC_4122");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(280);
            if (var1.getlocal(0).__getattr__("int")._and(Py.newInteger(8192)._lshift(Py.newLong("48"))).__not__().__nonzero__()) {
               var1.setline(281);
               var3 = var1.getglobal("RESERVED_MICROSOFT");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(283);
               var3 = var1.getglobal("RESERVED_FUTURE");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject get_version$23(PyFrame var1, ThreadState var2) {
      var1.setline(289);
      PyObject var3 = var1.getlocal(0).__getattr__("variant");
      PyObject var10000 = var3._eq(var1.getglobal("RFC_4122"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("int")._rshift(Py.newLong("76"))._and(Py.newInteger(15)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _find_mac$24(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject _ifconfig_getnode$25(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      PyString.fromInterned("Get the hardware address on Unix by running ifconfig.");
      var1.setline(320);
      PyObject var3 = (new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("-a"), PyString.fromInterned("-av")})).__iter__();

      PyObject var7;
      do {
         var1.setline(320);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         PyString var10002;
         PyList var10004;
         if (var4 == null) {
            var1.setline(325);
            var3 = imp.importOne("socket", var1, -1);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(326);
            var3 = var1.getlocal(2).__getattr__("gethostbyname").__call__(var2, var1.getlocal(2).__getattr__("gethostname").__call__(var2));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(329);
            var10000 = var1.getglobal("_find_mac");
            var10002 = PyString.fromInterned("arp");
            PyString var8 = PyString.fromInterned("-an");
            var10004 = new PyList(new PyObject[]{var1.getlocal(3)});
            var1.setline(329);
            PyObject[] var6 = Py.EmptyObjects;
            var3 = var10000.__call__(var2, var10002, var8, var10004, new PyFunction(var1.f_globals, var6, f$27));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(330);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(331);
               var7 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(334);
               var10000 = var1.getglobal("_find_mac");
               var10002 = PyString.fromInterned("lanscan");
               var8 = PyString.fromInterned("-ai");
               var10004 = new PyList(new PyObject[]{PyString.fromInterned("lan0")});
               var1.setline(334);
               var6 = Py.EmptyObjects;
               var3 = var10000.__call__(var2, var10002, var8, var10004, new PyFunction(var1.f_globals, var6, f$28));
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(335);
               if (var1.getlocal(1).__nonzero__()) {
                  var1.setline(336);
                  var7 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var7;
               } else {
                  var1.setline(338);
                  var7 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var7;
               }
            }
         }

         var1.setlocal(0, var4);
         var1.setline(321);
         var10000 = var1.getglobal("_find_mac");
         var10002 = PyString.fromInterned("ifconfig");
         PyObject var10003 = var1.getlocal(0);
         var10004 = new PyList(new PyObject[]{PyString.fromInterned("hwaddr"), PyString.fromInterned("ether")});
         var1.setline(321);
         PyObject[] var5 = Py.EmptyObjects;
         var7 = var10000.__call__(var2, var10002, var10003, var10004, new PyFunction(var1.f_globals, var5, f$26));
         var1.setlocal(1, var7);
         var5 = null;
         var1.setline(322);
      } while(!var1.getlocal(1).__nonzero__());

      var1.setline(323);
      var7 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject f$26(PyFrame var1, ThreadState var2) {
      var1.setline(321);
      PyObject var3 = var1.getlocal(0)._add(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$27(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      PyInteger var3 = Py.newInteger(-1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$28(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ipconfig_getnode$29(PyFrame var1, ThreadState var2) {
      var1.setline(341);
      PyString.fromInterned("Get the hardware address on Windows by running ipconfig.exe.");
      var1.setline(342);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(343);
      PyList var18 = new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("c:\\windows\\system32"), PyString.fromInterned("c:\\winnt\\system32")});
      var1.setlocal(2, var18);
      var3 = null;

      try {
         var1.setline(345);
         var3 = imp.importOne("ctypes", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(346);
         var3 = var1.getlocal(3).__getattr__("create_string_buffer").__call__((ThreadState)var2, (PyObject)Py.newInteger(300));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(347);
         var1.getlocal(3).__getattr__("windll").__getattr__("kernel32").__getattr__("GetSystemDirectoryA").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(300));
         var1.setline(348);
         var1.getlocal(2).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(4).__getattr__("value").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mbcs")));
      } catch (Throwable var10) {
         Py.setException(var10, var1);
         var1.setline(350);
      }

      var1.setline(351);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(351);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         Throwable var5 = null;

         label72: {
            Throwable var10000;
            label81: {
               boolean var10001;
               label70: {
                  label69: {
                     PyException var6;
                     try {
                        try {
                           var1.setline(353);
                           PyObject var19 = var1.getlocal(0).__getattr__("popen").__call__(var2, var1.getlocal(0).__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("ipconfig"))._add(PyString.fromInterned(" /all")));
                           var1.setlocal(6, var19);
                           var6 = null;
                           break label70;
                        } catch (Throwable var16) {
                           var6 = Py.setException(var16, var1);
                           if (var6.match(var1.getglobal("IOError"))) {
                              break label69;
                           }
                        }
                     } catch (Throwable var17) {
                        var10000 = var17;
                        var10001 = false;
                        break label81;
                     }

                     try {
                        throw var6;
                     } catch (Throwable var11) {
                        var10000 = var11;
                        var10001 = false;
                        break label81;
                     }
                  }

                  var1.setline(362);
                  var1.getlocal(6).__getattr__("close").__call__(var2);
                  continue;
               }

               PyObject var7;
               try {
                  var1.setline(357);
                  var7 = var1.getlocal(6).__iter__();
               } catch (Throwable var14) {
                  var10000 = var14;
                  var10001 = false;
                  break label81;
               }

               while(true) {
                  PyObject var8;
                  try {
                     var1.setline(357);
                     var8 = var7.__iternext__();
                     if (var8 == null) {
                        break label72;
                     }
                  } catch (Throwable var13) {
                     var10000 = var13;
                     var10001 = false;
                     break;
                  }

                  PyObject var9;
                  try {
                     var1.setlocal(7, var8);
                     var1.setline(358);
                     var9 = var1.getlocal(7).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":")).__getitem__(Py.newInteger(-1)).__getattr__("strip").__call__(var2).__getattr__("lower").__call__(var2);
                     var1.setlocal(8, var9);
                     var9 = null;
                     var1.setline(359);
                     if (!var1.getlocal(1).__getattr__("match").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([0-9a-f][0-9a-f]-){5}[0-9a-f][0-9a-f]"), (PyObject)var1.getlocal(8)).__nonzero__()) {
                        continue;
                     }

                     var1.setline(360);
                     var9 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("")), (PyObject)Py.newInteger(16));
                  } catch (Throwable var15) {
                     var10000 = var15;
                     var10001 = false;
                     break;
                  }

                  var1.setline(362);
                  var1.getlocal(6).__getattr__("close").__call__(var2);

                  try {
                     var1.f_lasti = -1;
                     return var9;
                  } catch (Throwable var12) {
                     var10000 = var12;
                     var10001 = false;
                     break;
                  }
               }
            }

            var5 = var10000;
            Py.addTraceback(var5, var1);
            var1.setline(362);
            var1.getlocal(6).__getattr__("close").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(362);
         var1.getlocal(6).__getattr__("close").__call__(var2);
      }
   }

   public PyObject _netbios_getnode$30(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyString.fromInterned("Get the hardware address on Windows using NetBIOS calls.\n    See http://support.microsoft.com/kb/118623 for details.");
      var1.setline(367);
      PyObject var3 = imp.importOne("win32wnet", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var3 = imp.importOne("netbios", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(368);
      var3 = var1.getlocal(1).__getattr__("NCB").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(369);
      var3 = var1.getlocal(1).__getattr__("NCBENUM");
      var1.getlocal(2).__setattr__("Command", var3);
      var3 = null;
      var1.setline(370);
      var3 = var1.getlocal(1).__getattr__("LANA_ENUM").__call__(var2);
      var1.getlocal(2).__setattr__("Buffer", var3);
      var1.setlocal(3, var3);
      var1.setline(371);
      var1.getlocal(3).__getattr__("_pack").__call__(var2);
      var1.setline(372);
      var3 = var1.getlocal(0).__getattr__("Netbios").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(373);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(374);
         var1.getlocal(3).__getattr__("_unpack").__call__(var2);
         var1.setline(375);
         var3 = var1.getglobal("range").__call__(var2, var1.getlocal(3).__getattr__("length")).__iter__();

         while(true) {
            var1.setline(375);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(4, var4);
            var1.setline(376);
            var1.getlocal(2).__getattr__("Reset").__call__(var2);
            var1.setline(377);
            PyObject var5 = var1.getlocal(1).__getattr__("NCBRESET");
            var1.getlocal(2).__setattr__("Command", var5);
            var5 = null;
            var1.setline(378);
            var5 = var1.getglobal("ord").__call__(var2, var1.getlocal(3).__getattr__("lana").__getitem__(var1.getlocal(4)));
            var1.getlocal(2).__setattr__("Lana_num", var5);
            var5 = null;
            var1.setline(379);
            var5 = var1.getlocal(0).__getattr__("Netbios").__call__(var2, var1.getlocal(2));
            var10000 = var5._ne(Py.newInteger(0));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(381);
               var1.getlocal(2).__getattr__("Reset").__call__(var2);
               var1.setline(382);
               var5 = var1.getlocal(1).__getattr__("NCBASTAT");
               var1.getlocal(2).__setattr__("Command", var5);
               var5 = null;
               var1.setline(383);
               var5 = var1.getglobal("ord").__call__(var2, var1.getlocal(3).__getattr__("lana").__getitem__(var1.getlocal(4)));
               var1.getlocal(2).__setattr__("Lana_num", var5);
               var5 = null;
               var1.setline(384);
               var5 = PyString.fromInterned("*").__getattr__("ljust").__call__((ThreadState)var2, (PyObject)Py.newInteger(16));
               var1.getlocal(2).__setattr__("Callname", var5);
               var5 = null;
               var1.setline(385);
               var5 = var1.getlocal(1).__getattr__("ADAPTER_STATUS").__call__(var2);
               var1.getlocal(2).__setattr__("Buffer", var5);
               var1.setlocal(5, var5);
               var1.setline(386);
               var5 = var1.getlocal(0).__getattr__("Netbios").__call__(var2, var1.getlocal(2));
               var10000 = var5._ne(Py.newInteger(0));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(388);
                  var1.getlocal(5).__getattr__("_unpack").__call__(var2);
                  var1.setline(389);
                  var5 = var1.getglobal("map").__call__(var2, var1.getglobal("ord"), var1.getlocal(5).__getattr__("adapter_address"));
                  var1.setlocal(6, var5);
                  var5 = null;
                  var1.setline(390);
                  var5 = var1.getlocal(6).__getitem__(Py.newInteger(0))._lshift(Py.newLong("40"))._add(var1.getlocal(6).__getitem__(Py.newInteger(1))._lshift(Py.newLong("32")))._add(var1.getlocal(6).__getitem__(Py.newInteger(2))._lshift(Py.newLong("24")))._add(var1.getlocal(6).__getitem__(Py.newInteger(3))._lshift(Py.newLong("16")))._add(var1.getlocal(6).__getitem__(Py.newInteger(4))._lshift(Py.newLong("8")))._add(var1.getlocal(6).__getitem__(Py.newInteger(5)));
                  var1.f_lasti = -1;
                  return var5;
               }
            }
         }
      }
   }

   public PyObject _unixdll_getnode$31(PyFrame var1, ThreadState var2) {
      var1.setline(443);
      PyString.fromInterned("Get the hardware address on Unix using ctypes.");
      var1.setline(444);
      PyObject var3 = var1.getglobal("ctypes").__getattr__("create_string_buffer").__call__((ThreadState)var2, (PyObject)Py.newInteger(16));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(445);
      var1.getglobal("_uuid_generate_time").__call__(var2, var1.getlocal(0));
      var1.setline(446);
      PyObject var10000 = var1.getglobal("UUID");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("raw")};
      String[] var4 = new String[]{"bytes"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000.__getattr__("node");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _windll_getnode$32(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyString.fromInterned("Get the hardware address on Windows using ctypes.");
      var1.setline(450);
      PyObject var3 = var1.getglobal("ctypes").__getattr__("create_string_buffer").__call__((ThreadState)var2, (PyObject)Py.newInteger(16));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(451);
      var3 = var1.getglobal("_UuidCreate").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(452);
         var10000 = var1.getglobal("UUID");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("raw")};
         String[] var4 = new String[]{"bytes"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000.__getattr__("node");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _random_getnode$33(PyFrame var1, ThreadState var2) {
      var1.setline(455);
      PyString.fromInterned("Get a random node ID, with eighth bit set as suggested by RFC 4122.");
      var1.setline(456);
      PyObject var3 = imp.importOne("random", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(457);
      var3 = var1.getlocal(0).__getattr__("randrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(1)._lshift(Py.newLong("48")))._or(Py.newLong("1099511627776"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getnode$34(PyFrame var1, ThreadState var2) {
      var1.setline(468);
      PyString.fromInterned("Get the hardware address as a 48-bit positive integer.\n\n    The first time this runs, it may launch a separate program, which could\n    be quite slow.  If all attempts to obtain the hardware address fail, we\n    choose a random 48-bit number with its eighth bit set to 1 as recommended\n    in RFC 4122.\n    ");
      var1.setline(471);
      PyObject var3 = var1.getglobal("_node");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(472);
         var3 = var1.getglobal("_node");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(474);
         PyObject var4 = imp.importOne("sys", var1, -1);
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(475);
         var4 = var1.getlocal(0).__getattr__("platform");
         var10000 = var4._eq(PyString.fromInterned("win32"));
         var4 = null;
         PyList var8;
         if (var10000.__nonzero__()) {
            var1.setline(476);
            var8 = new PyList(new PyObject[]{var1.getglobal("_windll_getnode"), var1.getglobal("_netbios_getnode"), var1.getglobal("_ipconfig_getnode")});
            var1.setlocal(1, var8);
            var4 = null;
         } else {
            var1.setline(478);
            var8 = new PyList(new PyObject[]{var1.getglobal("_unixdll_getnode"), var1.getglobal("_ifconfig_getnode")});
            var1.setlocal(1, var8);
            var4 = null;
         }

         var1.setline(480);
         var4 = var1.getlocal(1)._add(new PyList(new PyObject[]{var1.getglobal("_random_getnode")})).__iter__();

         while(true) {
            var1.setline(480);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var5);

            PyObject var6;
            try {
               var1.setline(482);
               var6 = var1.getlocal(2).__call__(var2);
               var1.setglobal("_node", var6);
               var6 = null;
            } catch (Throwable var7) {
               Py.setException(var7, var1);
               continue;
            }

            var1.setline(485);
            var6 = var1.getglobal("_node");
            var10000 = var6._isnot(var1.getglobal("None"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(486);
               var3 = var1.getglobal("_node");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject uuid1$35(PyFrame var1, ThreadState var2) {
      var1.setline(494);
      PyString.fromInterned("Generate a UUID from a host ID, sequence number, and the current time.\n    If 'node' is not given, getnode() is used to obtain the hardware\n    address.  If 'clock_seq' is given, it is used as the sequence number;\n    otherwise a random 14-bit sequence number is chosen.");
      var1.setline(498);
      PyObject var10000 = var1.getglobal("_uuid_generate_time");
      PyObject var3;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         PyObject var10001 = var1.getlocal(1);
         var10000 = var3;
         var3 = var10001;
         if ((var4 = var10000._is(var10001)).__nonzero__()) {
            var4 = var3._is(var1.getglobal("None"));
         }

         var10000 = var4;
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(499);
         var3 = var1.getglobal("ctypes").__getattr__("create_string_buffer").__call__((ThreadState)var2, (PyObject)Py.newInteger(16));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(500);
         var1.getglobal("_uuid_generate_time").__call__(var2, var1.getlocal(2));
         var1.setline(501);
         var10000 = var1.getglobal("UUID");
         PyObject[] var6 = new PyObject[]{var1.getlocal(2).__getattr__("raw")};
         String[] var8 = new String[]{"bytes"};
         var10000 = var10000.__call__(var2, var6, var8);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(504);
         var4 = imp.importOne("time", var1, -1);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(505);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getattr__("time").__call__(var2)._mul(Py.newFloat(1.0E9)));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(508);
         var4 = var1.getglobal("int").__call__(var2, var1.getlocal(4)._floordiv(Py.newInteger(100)))._add(Py.newLong("122192928000000000"));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(509);
         var4 = var1.getglobal("_last_timestamp");
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(5);
            var10000 = var4._le(var1.getglobal("_last_timestamp"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(510);
            var4 = var1.getglobal("_last_timestamp")._add(Py.newInteger(1));
            var1.setlocal(5, var4);
            var4 = null;
         }

         var1.setline(511);
         var4 = var1.getlocal(5);
         var1.setglobal("_last_timestamp", var4);
         var4 = null;
         var1.setline(512);
         var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(513);
            var4 = imp.importOne("random", var1, -1);
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(514);
            var4 = var1.getlocal(6).__getattr__("randrange").__call__(var2, Py.newInteger(1)._lshift(Py.newLong("14")));
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(515);
         var4 = var1.getlocal(5)._and(Py.newLong("4294967295"));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(516);
         var4 = var1.getlocal(5)._rshift(Py.newLong("32"))._and(Py.newLong("65535"));
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(517);
         var4 = var1.getlocal(5)._rshift(Py.newLong("48"))._and(Py.newLong("4095"));
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(518);
         var4 = var1.getlocal(1)._and(Py.newLong("255"));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(519);
         var4 = var1.getlocal(1)._rshift(Py.newLong("8"))._and(Py.newLong("63"));
         var1.setlocal(11, var4);
         var4 = null;
         var1.setline(520);
         var4 = var1.getlocal(0);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(521);
            var4 = var1.getglobal("getnode").__call__(var2);
            var1.setlocal(0, var4);
            var4 = null;
         }

         var1.setline(522);
         var10000 = var1.getglobal("UUID");
         PyObject[] var7 = new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(11), var1.getlocal(10), var1.getlocal(0)}), Py.newInteger(1)};
         String[] var5 = new String[]{"fields", "version"};
         var10000 = var10000.__call__(var2, var7, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject uuid3$36(PyFrame var1, ThreadState var2) {
      var1.setline(526);
      PyString.fromInterned("Generate a UUID from the MD5 hash of a namespace UUID and a name.");
      var1.setline(527);
      String[] var3 = new String[]{"md5"};
      PyObject[] var5 = imp.importFrom("hashlib", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(528);
      PyObject var6 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("bytes")._add(var1.getlocal(1))).__getattr__("digest").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(529);
      PyObject var10000 = var1.getglobal("UUID");
      var5 = new PyObject[]{var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(16), (PyObject)null), Py.newInteger(3)};
      String[] var7 = new String[]{"bytes", "version"};
      var10000 = var10000.__call__(var2, var5, var7);
      var3 = null;
      var6 = var10000;
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject uuid4$37(PyFrame var1, ThreadState var2) {
      var1.setline(532);
      PyString.fromInterned("Generate a random UUID.");
      var1.setline(535);
      PyObject var3;
      PyObject var15;
      if (var1.getglobal("_uuid_generate_random").__nonzero__()) {
         var1.setline(536);
         var3 = var1.getglobal("ctypes").__getattr__("create_string_buffer").__call__((ThreadState)var2, (PyObject)Py.newInteger(16));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(537);
         var1.getglobal("_uuid_generate_random").__call__(var2, var1.getlocal(0));
         var1.setline(538);
         var15 = var1.getglobal("UUID");
         PyObject[] var8 = new PyObject[]{var1.getlocal(0).__getattr__("raw")};
         String[] var10 = new String[]{"bytes"};
         var15 = var15.__call__(var2, var8, var10);
         var3 = null;
         var3 = var15;
         var1.f_lasti = -1;
         return var3;
      } else {
         try {
            var1.setline(542);
            PyObject var4 = imp.importOne("os", var1, -1);
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(543);
            var15 = var1.getglobal("UUID");
            PyObject[] var9 = new PyObject[]{var1.getlocal(1).__getattr__("urandom").__call__((ThreadState)var2, (PyObject)Py.newInteger(16)), Py.newInteger(4)};
            String[] var14 = new String[]{"bytes", "version"};
            var15 = var15.__call__(var2, var9, var14);
            var4 = null;
            var3 = var15;
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var7) {
            Py.setException(var7, var1);
            var1.setline(545);
            PyObject var5 = imp.importOne("random", var1, -1);
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(546);
            PyList var10000 = new PyList();
            var5 = var10000.__getattr__("append");
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(546);
            var5 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(16)).__iter__();

            while(true) {
               var1.setline(546);
               PyObject var6 = var5.__iternext__();
               if (var6 == null) {
                  var1.setline(546);
                  var1.dellocal(4);
                  PyList var12 = var10000;
                  var1.setlocal(3, var12);
                  var5 = null;
                  var1.setline(547);
                  var15 = var1.getglobal("UUID");
                  PyObject[] var13 = new PyObject[]{var1.getlocal(3), Py.newInteger(4)};
                  String[] var11 = new String[]{"bytes", "version"};
                  var15 = var15.__call__(var2, var13, var11);
                  var5 = null;
                  var3 = var15;
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(5, var6);
               var1.setline(546);
               var1.getlocal(4).__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(2).__getattr__("randrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(256))));
            }
         }
      }
   }

   public PyObject uuid5$38(PyFrame var1, ThreadState var2) {
      var1.setline(550);
      PyString.fromInterned("Generate a UUID from the SHA-1 hash of a namespace UUID and a name.");
      var1.setline(551);
      String[] var3 = new String[]{"sha1"};
      PyObject[] var5 = imp.importFrom("hashlib", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(552);
      PyObject var6 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("bytes")._add(var1.getlocal(1))).__getattr__("digest").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(553);
      PyObject var10000 = var1.getglobal("UUID");
      var5 = new PyObject[]{var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(16), (PyObject)null), Py.newInteger(5)};
      String[] var7 = new String[]{"bytes", "version"};
      var10000 = var10000.__call__(var2, var5, var7);
      var3 = null;
      var6 = var10000;
      var1.f_lasti = -1;
      return var6;
   }

   public uuid$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UUID$1 = Py.newCode(0, var2, var1, "UUID", 53, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "hex", "bytes", "bytes_le", "fields", "int", "version", "time_low", "time_mid", "time_hi_version", "clock_seq_hi_variant", "clock_seq_low", "node", "clock_seq"};
      __init__$2 = Py.newCode(7, var2, var1, "__init__", 101, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$3 = Py.newCode(2, var2, var1, "__cmp__", 180, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$4 = Py.newCode(1, var2, var1, "__hash__", 185, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __int__$5 = Py.newCode(1, var2, var1, "__int__", 188, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$6 = Py.newCode(1, var2, var1, "__repr__", 191, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      __setattr__$7 = Py.newCode(3, var2, var1, "__setattr__", 194, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hex"};
      __str__$8 = Py.newCode(1, var2, var1, "__str__", 197, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bytes", "shift"};
      get_bytes$9 = Py.newCode(1, var2, var1, "get_bytes", 202, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bytes"};
      get_bytes_le$10 = Py.newCode(1, var2, var1, "get_bytes_le", 210, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_fields$11 = Py.newCode(1, var2, var1, "get_fields", 217, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_time_low$12 = Py.newCode(1, var2, var1, "get_time_low", 223, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_time_mid$13 = Py.newCode(1, var2, var1, "get_time_mid", 228, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_time_hi_version$14 = Py.newCode(1, var2, var1, "get_time_hi_version", 233, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_clock_seq_hi_variant$15 = Py.newCode(1, var2, var1, "get_clock_seq_hi_variant", 238, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_clock_seq_low$16 = Py.newCode(1, var2, var1, "get_clock_seq_low", 243, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_time$17 = Py.newCode(1, var2, var1, "get_time", 248, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_clock_seq$18 = Py.newCode(1, var2, var1, "get_clock_seq", 254, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_node$19 = Py.newCode(1, var2, var1, "get_node", 260, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_hex$20 = Py.newCode(1, var2, var1, "get_hex", 265, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_urn$21 = Py.newCode(1, var2, var1, "get_urn", 270, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_variant$22 = Py.newCode(1, var2, var1, "get_variant", 275, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_version$23 = Py.newCode(1, var2, var1, "get_version", 287, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"command", "args", "hw_identifiers", "get_index", "os", "dir", "executable", "cmd", "pipe", "line", "words", "i"};
      _find_mac$24 = Py.newCode(4, var2, var1, "_find_mac", 294, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "mac", "socket", "ip_addr"};
      _ifconfig_getnode$25 = Py.newCode(0, var2, var1, "_ifconfig_getnode", 316, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"i"};
      f$26 = Py.newCode(1, var2, var1, "<lambda>", 321, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"i"};
      f$27 = Py.newCode(1, var2, var1, "<lambda>", 329, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"i"};
      f$28 = Py.newCode(1, var2, var1, "<lambda>", 334, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"os", "re", "dirs", "ctypes", "buffer", "dir", "pipe", "line", "value"};
      _ipconfig_getnode$29 = Py.newCode(0, var2, var1, "_ipconfig_getnode", 340, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"win32wnet", "netbios", "ncb", "adapters", "i", "status", "bytes"};
      _netbios_getnode$30 = Py.newCode(0, var2, var1, "_netbios_getnode", 364, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_buffer"};
      _unixdll_getnode$31 = Py.newCode(0, var2, var1, "_unixdll_getnode", 442, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_buffer"};
      _windll_getnode$32 = Py.newCode(0, var2, var1, "_windll_getnode", 448, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"random"};
      _random_getnode$33 = Py.newCode(0, var2, var1, "_random_getnode", 454, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sys", "getters", "getter"};
      getnode$34 = Py.newCode(0, var2, var1, "getnode", 461, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"node", "clock_seq", "_buffer", "time", "nanoseconds", "timestamp", "random", "time_low", "time_mid", "time_hi_version", "clock_seq_low", "clock_seq_hi_variant"};
      uuid1$35 = Py.newCode(2, var2, var1, "uuid1", 490, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"namespace", "name", "md5", "hash"};
      uuid3$36 = Py.newCode(2, var2, var1, "uuid3", 525, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_buffer", "os", "random", "bytes", "_[546_17]", "i"};
      uuid4$37 = Py.newCode(0, var2, var1, "uuid4", 531, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"namespace", "name", "sha1", "hash"};
      uuid5$38 = Py.newCode(2, var2, var1, "uuid5", 549, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new uuid$py("uuid$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(uuid$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.UUID$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__cmp__$3(var2, var3);
         case 4:
            return this.__hash__$4(var2, var3);
         case 5:
            return this.__int__$5(var2, var3);
         case 6:
            return this.__repr__$6(var2, var3);
         case 7:
            return this.__setattr__$7(var2, var3);
         case 8:
            return this.__str__$8(var2, var3);
         case 9:
            return this.get_bytes$9(var2, var3);
         case 10:
            return this.get_bytes_le$10(var2, var3);
         case 11:
            return this.get_fields$11(var2, var3);
         case 12:
            return this.get_time_low$12(var2, var3);
         case 13:
            return this.get_time_mid$13(var2, var3);
         case 14:
            return this.get_time_hi_version$14(var2, var3);
         case 15:
            return this.get_clock_seq_hi_variant$15(var2, var3);
         case 16:
            return this.get_clock_seq_low$16(var2, var3);
         case 17:
            return this.get_time$17(var2, var3);
         case 18:
            return this.get_clock_seq$18(var2, var3);
         case 19:
            return this.get_node$19(var2, var3);
         case 20:
            return this.get_hex$20(var2, var3);
         case 21:
            return this.get_urn$21(var2, var3);
         case 22:
            return this.get_variant$22(var2, var3);
         case 23:
            return this.get_version$23(var2, var3);
         case 24:
            return this._find_mac$24(var2, var3);
         case 25:
            return this._ifconfig_getnode$25(var2, var3);
         case 26:
            return this.f$26(var2, var3);
         case 27:
            return this.f$27(var2, var3);
         case 28:
            return this.f$28(var2, var3);
         case 29:
            return this._ipconfig_getnode$29(var2, var3);
         case 30:
            return this._netbios_getnode$30(var2, var3);
         case 31:
            return this._unixdll_getnode$31(var2, var3);
         case 32:
            return this._windll_getnode$32(var2, var3);
         case 33:
            return this._random_getnode$33(var2, var3);
         case 34:
            return this.getnode$34(var2, var3);
         case 35:
            return this.uuid1$35(var2, var3);
         case 36:
            return this.uuid3$36(var2, var3);
         case 37:
            return this.uuid4$37(var2, var3);
         case 38:
            return this.uuid5$38(var2, var3);
         default:
            return null;
      }
   }
}
