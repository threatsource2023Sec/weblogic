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
import org.python.core.PyLong;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("_google_ipaddr_r234.py")
public class _google_ipaddr_r234$py extends PyFunctionTable implements PyRunnable {
   static _google_ipaddr_r234$py self;
   static final PyCode f$0;
   static final PyCode AddressValueError$1;
   static final PyCode NetmaskValueError$2;
   static final PyCode IPAddress$3;
   static final PyCode IPNetwork$4;
   static final PyCode v4_int_to_packed$5;
   static final PyCode v6_int_to_packed$6;
   static final PyCode _find_address_range$7;
   static final PyCode _get_prefix_length$8;
   static final PyCode _count_righthand_zero_bits$9;
   static final PyCode summarize_address_range$10;
   static final PyCode _collapse_address_list_recursive$11;
   static final PyCode collapse_address_list$12;
   static final PyCode get_mixed_type_key$13;
   static final PyCode _IPAddrBase$14;
   static final PyCode __index__$15;
   static final PyCode __int__$16;
   static final PyCode __hex__$17;
   static final PyCode exploded$18;
   static final PyCode compressed$19;
   static final PyCode _BaseIP$20;
   static final PyCode __init__$21;
   static final PyCode __eq__$22;
   static final PyCode __ne__$23;
   static final PyCode __le__$24;
   static final PyCode __ge__$25;
   static final PyCode __lt__$26;
   static final PyCode __gt__$27;
   static final PyCode __add__$28;
   static final PyCode __sub__$29;
   static final PyCode __repr__$30;
   static final PyCode __str__$31;
   static final PyCode __hash__$32;
   static final PyCode _get_address_key$33;
   static final PyCode version$34;
   static final PyCode _BaseNet$35;
   static final PyCode __init__$36;
   static final PyCode __repr__$37;
   static final PyCode iterhosts$38;
   static final PyCode __iter__$39;
   static final PyCode __getitem__$40;
   static final PyCode __lt__$41;
   static final PyCode __gt__$42;
   static final PyCode __le__$43;
   static final PyCode __ge__$44;
   static final PyCode __eq__$45;
   static final PyCode __ne__$46;
   static final PyCode __str__$47;
   static final PyCode __hash__$48;
   static final PyCode __contains__$49;
   static final PyCode overlaps$50;
   static final PyCode network$51;
   static final PyCode broadcast$52;
   static final PyCode hostmask$53;
   static final PyCode with_prefixlen$54;
   static final PyCode with_netmask$55;
   static final PyCode with_hostmask$56;
   static final PyCode numhosts$57;
   static final PyCode version$58;
   static final PyCode prefixlen$59;
   static final PyCode address_exclude$60;
   static final PyCode compare_networks$61;
   static final PyCode _get_networks_key$62;
   static final PyCode _ip_int_from_prefix$63;
   static final PyCode _prefix_from_ip_int$64;
   static final PyCode _ip_string_from_prefix$65;
   static final PyCode iter_subnets$66;
   static final PyCode masked$67;
   static final PyCode subnet$68;
   static final PyCode supernet$69;
   static final PyCode _BaseV4$70;
   static final PyCode __init__$71;
   static final PyCode _explode_shorthand_ip_string$72;
   static final PyCode _ip_int_from_string$73;
   static final PyCode _parse_octet$74;
   static final PyCode _string_from_ip_int$75;
   static final PyCode max_prefixlen$76;
   static final PyCode packed$77;
   static final PyCode version$78;
   static final PyCode is_reserved$79;
   static final PyCode is_private$80;
   static final PyCode is_multicast$81;
   static final PyCode is_unspecified$82;
   static final PyCode is_loopback$83;
   static final PyCode is_link_local$84;
   static final PyCode IPv4Address$85;
   static final PyCode __init__$86;
   static final PyCode IPv4Network$87;
   static final PyCode __init__$88;
   static final PyCode _is_hostmask$89;
   static final PyCode _is_valid_netmask$90;
   static final PyCode f$91;
   static final PyCode f$92;
   static final PyCode f$93;
   static final PyCode f$94;
   static final PyCode _BaseV6$95;
   static final PyCode __init__$96;
   static final PyCode _ip_int_from_string$97;
   static final PyCode _parse_hextet$98;
   static final PyCode _compress_hextets$99;
   static final PyCode _string_from_ip_int$100;
   static final PyCode _explode_shorthand_ip_string$101;
   static final PyCode max_prefixlen$102;
   static final PyCode packed$103;
   static final PyCode version$104;
   static final PyCode is_multicast$105;
   static final PyCode is_reserved$106;
   static final PyCode is_unspecified$107;
   static final PyCode is_loopback$108;
   static final PyCode is_link_local$109;
   static final PyCode is_site_local$110;
   static final PyCode is_private$111;
   static final PyCode ipv4_mapped$112;
   static final PyCode teredo$113;
   static final PyCode sixtofour$114;
   static final PyCode IPv6Address$115;
   static final PyCode __init__$116;
   static final PyCode IPv6Network$117;
   static final PyCode __init__$118;
   static final PyCode _is_valid_netmask$119;
   static final PyCode with_netmask$120;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A fast, lightweight IPv4/IPv6 manipulation library in Python.\n\nThis library is used to create/poke/manipulate IPv4 and IPv6 addresses\nand networks.\n\n"));
      var1.setline(23);
      PyString.fromInterned("A fast, lightweight IPv4/IPv6 manipulation library in Python.\n\nThis library is used to create/poke/manipulate IPv4 and IPv6 addresses\nand networks.\n\n");
      var1.setline(25);
      PyString var3 = PyString.fromInterned("trunk");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(27);
      PyObject var6 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var6);
      var3 = null;
      var1.setline(29);
      PyInteger var7 = Py.newInteger(32);
      var1.setlocal("IPV4LENGTH", var7);
      var3 = null;
      var1.setline(30);
      var7 = Py.newInteger(128);
      var1.setlocal("IPV6LENGTH", var7);
      var3 = null;
      var1.setline(33);
      PyObject[] var8 = new PyObject[]{var1.getname("ValueError")};
      PyObject var4 = Py.makeClass("AddressValueError", var8, AddressValueError$1);
      var1.setlocal("AddressValueError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(37);
      var8 = new PyObject[]{var1.getname("ValueError")};
      var4 = Py.makeClass("NetmaskValueError", var8, NetmaskValueError$2);
      var1.setlocal("NetmaskValueError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(41);
      var8 = new PyObject[]{var1.getname("None")};
      PyFunction var9 = new PyFunction(var1.f_globals, var8, IPAddress$3, PyString.fromInterned("Take an IP string/int and return an object of the correct type.\n\n    Args:\n        address: A string or integer, the IP address.  Either IPv4 or\n          IPv6 addresses may be supplied; integers less than 2**32 will\n          be considered to be IPv4 by default.\n        version: An Integer, 4 or 6. If set, don't try to automatically\n          determine what the IP address type is. important for things\n          like IPAddress(1), which could be IPv4, '0.0.0.1',  or IPv6,\n          '::1'.\n\n    Returns:\n        An IPv4Address or IPv6Address object.\n\n    Raises:\n        ValueError: if the string passed isn't either a v4 or a v6\n          address.\n\n    "));
      var1.setlocal("IPAddress", var9);
      var3 = null;
      var1.setline(81);
      var8 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      var9 = new PyFunction(var1.f_globals, var8, IPNetwork$4, PyString.fromInterned("Take an IP string/int and return an object of the correct type.\n\n    Args:\n        address: A string or integer, the IP address.  Either IPv4 or\n          IPv6 addresses may be supplied; integers less than 2**32 will\n          be considered to be IPv4 by default.\n        version: An Integer, if set, don't try to automatically\n          determine what the IP address type is. important for things\n          like IPNetwork(1), which could be IPv4, '0.0.0.1/32', or IPv6,\n          '::1/128'.\n\n    Returns:\n        An IPv4Network or IPv6Network object.\n\n    Raises:\n        ValueError: if the string passed isn't either a v4 or a v6\n          address. Or if a strict network was requested and a strict\n          network wasn't given.\n\n    "));
      var1.setlocal("IPNetwork", var9);
      var3 = null;
      var1.setline(122);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, v4_int_to_packed$5, PyString.fromInterned("The binary representation of this address.\n\n    Args:\n        address: An integer representation of an IPv4 IP address.\n\n    Returns:\n        The binary representation of this address.\n\n    Raises:\n        ValueError: If the integer is too large to be an IPv4 IP\n          address.\n    "));
      var1.setlocal("v4_int_to_packed", var9);
      var3 = null;
      var1.setline(140);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, v6_int_to_packed$6, PyString.fromInterned("The binary representation of this address.\n\n    Args:\n        address: An integer representation of an IPv4 IP address.\n\n    Returns:\n        The binary representation of this address.\n    "));
      var1.setlocal("v6_int_to_packed", var9);
      var3 = null;
      var1.setline(152);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _find_address_range$7, PyString.fromInterned("Find a sequence of addresses.\n\n    Args:\n        addresses: a list of IPv4 or IPv6 addresses.\n\n    Returns:\n        A tuple containing the first and last IP addresses in the sequence.\n\n    "));
      var1.setlocal("_find_address_range", var9);
      var3 = null;
      var1.setline(170);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _get_prefix_length$8, PyString.fromInterned("Get the number of leading bits that are same for two numbers.\n\n    Args:\n        number1: an integer.\n        number2: another integer.\n        bits: the maximum number of bits to compare.\n\n    Returns:\n        The number of leading bits that are the same for two numbers.\n\n    "));
      var1.setlocal("_get_prefix_length", var9);
      var3 = null;
      var1.setline(187);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _count_righthand_zero_bits$9, PyString.fromInterned("Count the number of zero bits on the right hand side.\n\n    Args:\n        number: an integer.\n        bits: maximum number of bits to count.\n\n    Returns:\n        The number of zero bits on the right hand side of the number.\n\n    "));
      var1.setlocal("_count_righthand_zero_bits", var9);
      var3 = null;
      var1.setline(204);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, summarize_address_range$10, PyString.fromInterned("Summarize a network range given the first and last IP addresses.\n\n    Example:\n        >>> summarize_address_range(IPv4Address('1.1.1.0'),\n            IPv4Address('1.1.1.130'))\n        [IPv4Network('1.1.1.0/25'), IPv4Network('1.1.1.128/31'),\n        IPv4Network('1.1.1.130/32')]\n\n    Args:\n        first: the first IPv4Address or IPv6Address in the range.\n        last: the last IPv4Address or IPv6Address in the range.\n\n    Returns:\n        The address range collapsed to a list of IPv4Network's or\n        IPv6Network's.\n\n    Raise:\n        TypeError:\n            If the first and last objects are not IP addresses.\n            If the first and last objects are not the same version.\n        ValueError:\n            If the last object is not greater than the first.\n            If the version is not 4 or 6.\n\n    "));
      var1.setlocal("summarize_address_range", var9);
      var3 = null;
      var1.setline(268);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _collapse_address_list_recursive$11, PyString.fromInterned("Loops through the addresses, collapsing concurrent netblocks.\n\n    Example:\n\n        ip1 = IPv4Network('1.1.0.0/24')\n        ip2 = IPv4Network('1.1.1.0/24')\n        ip3 = IPv4Network('1.1.2.0/24')\n        ip4 = IPv4Network('1.1.3.0/24')\n        ip5 = IPv4Network('1.1.4.0/24')\n        ip6 = IPv4Network('1.1.0.1/22')\n\n        _collapse_address_list_recursive([ip1, ip2, ip3, ip4, ip5, ip6]) ->\n          [IPv4Network('1.1.0.0/22'), IPv4Network('1.1.4.0/24')]\n\n        This shouldn't be called directly; it is called via\n          collapse_address_list([]).\n\n    Args:\n        addresses: A list of IPv4Network's or IPv6Network's\n\n    Returns:\n        A list of IPv4Network's or IPv6Network's depending on what we were\n        passed.\n\n    "));
      var1.setlocal("_collapse_address_list_recursive", var9);
      var3 = null;
      var1.setline(315);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, collapse_address_list$12, PyString.fromInterned("Collapse a list of IP objects.\n\n    Example:\n        collapse_address_list([IPv4('1.1.0.0/24'), IPv4('1.1.1.0/24')]) ->\n          [IPv4('1.1.0.0/23')]\n\n    Args:\n        addresses: A list of IPv4Network or IPv6Network objects.\n\n    Returns:\n        A list of IPv4Network or IPv6Network objects depending on what we\n        were passed.\n\n    Raises:\n        TypeError: If passed a list of mixed version objects.\n\n    "));
      var1.setlocal("collapse_address_list", var9);
      var3 = null;
      var1.setline(369);
      var6 = var1.getname("collapse_address_list");
      var1.setlocal("CollapseAddrList", var6);
      var3 = null;

      try {
         var1.setline(377);
         var6 = var1.getname("bytes");
         PyObject var10000 = var6._isnot(var1.getname("str"));
         var3 = null;
         var6 = var10000;
         var1.setlocal("_compat_has_real_bytes", var6);
         var3 = null;
      } catch (Throwable var5) {
         PyException var10 = Py.setException(var5, var1);
         if (!var10.match(var1.getname("NameError"))) {
            throw var10;
         }

         var1.setline(379);
         var4 = var1.getname("False");
         var1.setlocal("_compat_has_real_bytes", var4);
         var4 = null;
      }

      var1.setline(381);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, get_mixed_type_key$13, PyString.fromInterned("Return a key suitable for sorting between networks and addresses.\n\n    Address and Network objects are not sortable by default; they're\n    fundamentally different so the expression\n\n        IPv4Address('1.1.1.1') <= IPv4Network('1.1.1.1/24')\n\n    doesn't make any sense.  There are some times however, where you may wish\n    to have ipaddr sort these for you anyway. If you need to do this, you\n    can use this function as the key= argument to sorted().\n\n    Args:\n      obj: either a Network or Address object.\n    Returns:\n      appropriate key.\n\n    "));
      var1.setlocal("get_mixed_type_key", var9);
      var3 = null;
      var1.setline(405);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_IPAddrBase", var8, _IPAddrBase$14);
      var1.setlocal("_IPAddrBase", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(429);
      var8 = new PyObject[]{var1.getname("_IPAddrBase")};
      var4 = Py.makeClass("_BaseIP", var8, _BaseIP$20);
      var1.setlocal("_BaseIP", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(519);
      var8 = new PyObject[]{var1.getname("_IPAddrBase")};
      var4 = Py.makeClass("_BaseNet", var8, _BaseNet$35);
      var1.setlocal("_BaseNet", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1001);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_BaseV4", var8, _BaseV4$70);
      var1.setlocal("_BaseV4", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1166);
      var8 = new PyObject[]{var1.getname("_BaseV4"), var1.getname("_BaseIP")};
      var4 = Py.makeClass("IPv4Address", var8, IPv4Address$85);
      var1.setlocal("IPv4Address", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1209);
      var8 = new PyObject[]{var1.getname("_BaseV4"), var1.getname("_BaseNet")};
      var4 = Py.makeClass("IPv4Network", var8, IPv4Network$87);
      var1.setlocal("IPv4Network", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1384);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_BaseV6", var8, _BaseV6$95);
      var1.setlocal("_BaseV6", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1745);
      var8 = new PyObject[]{var1.getname("_BaseV6"), var1.getname("_BaseIP")};
      var4 = Py.makeClass("IPv6Address", var8, IPv6Address$115);
      var1.setlocal("IPv6Address", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1793);
      var8 = new PyObject[]{var1.getname("_BaseV6"), var1.getname("_BaseNet")};
      var4 = Py.makeClass("IPv6Network", var8, IPv6Network$117);
      var1.setlocal("IPv6Network", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject AddressValueError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A Value Error related to the address."));
      var1.setline(34);
      PyString.fromInterned("A Value Error related to the address.");
      return var1.getf_locals();
   }

   public PyObject NetmaskValueError$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A Value Error related to the netmask."));
      var1.setline(38);
      PyString.fromInterned("A Value Error related to the netmask.");
      return var1.getf_locals();
   }

   public PyObject IPAddress$3(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Take an IP string/int and return an object of the correct type.\n\n    Args:\n        address: A string or integer, the IP address.  Either IPv4 or\n          IPv6 addresses may be supplied; integers less than 2**32 will\n          be considered to be IPv4 by default.\n        version: An Integer, 4 or 6. If set, don't try to automatically\n          determine what the IP address type is. important for things\n          like IPAddress(1), which could be IPv4, '0.0.0.1',  or IPv6,\n          '::1'.\n\n    Returns:\n        An IPv4Address or IPv6Address object.\n\n    Raises:\n        ValueError: if the string passed isn't either a v4 or a v6\n          address.\n\n    ");
      var1.setline(61);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(62);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._eq(Py.newInteger(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(63);
            var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(64);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(Py.newInteger(6));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(65);
            var3 = var1.getglobal("IPv6Address").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      }

      try {
         var1.setline(68);
         var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("AddressValueError"), var1.getglobal("NetmaskValueError")}))) {
            var1.setline(70);

            try {
               var1.setline(73);
               var3 = var1.getglobal("IPv6Address").__call__(var2, var1.getlocal(0));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var5) {
               var7 = Py.setException(var5, var1);
               if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("AddressValueError"), var1.getglobal("NetmaskValueError")}))) {
                  var1.setline(75);
                  var1.setline(77);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%r does not appear to be an IPv4 or IPv6 address")._mod(var1.getlocal(0))));
               } else {
                  throw var7;
               }
            }
         } else {
            throw var7;
         }
      }
   }

   public PyObject IPNetwork$4(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyString.fromInterned("Take an IP string/int and return an object of the correct type.\n\n    Args:\n        address: A string or integer, the IP address.  Either IPv4 or\n          IPv6 addresses may be supplied; integers less than 2**32 will\n          be considered to be IPv4 by default.\n        version: An Integer, if set, don't try to automatically\n          determine what the IP address type is. important for things\n          like IPNetwork(1), which could be IPv4, '0.0.0.1/32', or IPv6,\n          '::1/128'.\n\n    Returns:\n        An IPv4Network or IPv6Network object.\n\n    Raises:\n        ValueError: if the string passed isn't either a v4 or a v6\n          address. Or if a strict network was requested and a strict\n          network wasn't given.\n\n    ");
      var1.setline(102);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(103);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._eq(Py.newInteger(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(104);
            var3 = var1.getglobal("IPv4Network").__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(105);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(Py.newInteger(6));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(106);
            var3 = var1.getglobal("IPv6Network").__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }

      try {
         var1.setline(109);
         var3 = var1.getglobal("IPv4Network").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("AddressValueError"), var1.getglobal("NetmaskValueError")}))) {
            var1.setline(111);

            try {
               var1.setline(114);
               var3 = var1.getglobal("IPv6Network").__call__(var2, var1.getlocal(0), var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var5) {
               var7 = Py.setException(var5, var1);
               if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("AddressValueError"), var1.getglobal("NetmaskValueError")}))) {
                  var1.setline(116);
                  var1.setline(118);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%r does not appear to be an IPv4 or IPv6 network")._mod(var1.getlocal(0))));
               } else {
                  throw var7;
               }
            }
         } else {
            throw var7;
         }
      }
   }

   public PyObject v4_int_to_packed$5(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyString.fromInterned("The binary representation of this address.\n\n    Args:\n        address: An integer representation of an IPv4 IP address.\n\n    Returns:\n        The binary representation of this address.\n\n    Raises:\n        ValueError: If the integer is too large to be an IPv4 IP\n          address.\n    ");
      var1.setline(135);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._gt(var1.getglobal("_BaseV4").__getattr__("_ALL_ONES"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(136);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Address too large for IPv4")));
      } else {
         var1.setline(137);
         var3 = var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("!I"), (PyObject)var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject v6_int_to_packed$6(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      PyString.fromInterned("The binary representation of this address.\n\n    Args:\n        address: An integer representation of an IPv4 IP address.\n\n    Returns:\n        The binary representation of this address.\n    ");
      var1.setline(149);
      PyObject var3 = var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, PyString.fromInterned("!QQ"), (PyObject)var1.getlocal(0)._rshift(Py.newInteger(64)), (PyObject)var1.getlocal(0)._and(Py.newInteger(2)._pow(Py.newInteger(64))._sub(Py.newInteger(1))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _find_address_range$7(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyString.fromInterned("Find a sequence of addresses.\n\n    Args:\n        addresses: a list of IPv4 or IPv6 addresses.\n\n    Returns:\n        A tuple containing the first and last IP addresses in the sequence.\n\n    ");
      var1.setline(162);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var1.setlocal(2, var3);
      var1.setline(163);
      var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         var1.setline(163);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(3, var4);
         var1.setline(164);
         PyObject var5 = var1.getlocal(3).__getattr__("_ip");
         PyObject var10000 = var5._eq(var1.getlocal(2).__getattr__("_ip")._add(Py.newInteger(1)));
         var5 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(165);
         var5 = var1.getlocal(3);
         var1.setlocal(2, var5);
         var5 = null;
      }

      var1.setline(168);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _get_prefix_length$8(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyString.fromInterned("Get the number of leading bits that are same for two numbers.\n\n    Args:\n        number1: an integer.\n        number2: another integer.\n        bits: the maximum number of bits to compare.\n\n    Returns:\n        The number of leading bits that are the same for two numbers.\n\n    ");
      var1.setline(182);
      PyObject var3 = var1.getglobal("range").__call__(var2, var1.getlocal(2)).__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(182);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(185);
            PyInteger var6 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(183);
         var5 = var1.getlocal(0)._rshift(var1.getlocal(3));
         var10000 = var5._eq(var1.getlocal(1)._rshift(var1.getlocal(3)));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(184);
      var5 = var1.getlocal(2)._sub(var1.getlocal(3));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _count_righthand_zero_bits$9(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyString.fromInterned("Count the number of zero bits on the right hand side.\n\n    Args:\n        number: an integer.\n        bits: maximum number of bits to count.\n\n    Returns:\n        The number of zero bits on the right hand side of the number.\n\n    ");
      var1.setline(198);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(199);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(200);
         PyObject var4 = var1.getglobal("range").__call__(var2, var1.getlocal(1)).__iter__();

         do {
            var1.setline(200);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var5);
            var1.setline(201);
         } while(!var1.getlocal(0)._rshift(var1.getlocal(2))._mod(Py.newInteger(2)).__nonzero__());

         var1.setline(202);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject summarize_address_range$10(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyString.fromInterned("Summarize a network range given the first and last IP addresses.\n\n    Example:\n        >>> summarize_address_range(IPv4Address('1.1.1.0'),\n            IPv4Address('1.1.1.130'))\n        [IPv4Network('1.1.1.0/25'), IPv4Network('1.1.1.128/31'),\n        IPv4Network('1.1.1.130/32')]\n\n    Args:\n        first: the first IPv4Address or IPv6Address in the range.\n        last: the last IPv4Address or IPv6Address in the range.\n\n    Returns:\n        The address range collapsed to a list of IPv4Network's or\n        IPv6Network's.\n\n    Raise:\n        TypeError:\n            If the first and last objects are not IP addresses.\n            If the first and last objects are not the same version.\n        ValueError:\n            If the last object is not greater than the first.\n            If the version is not 4 or 6.\n\n    ");
      var1.setline(230);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_BaseIP"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_BaseIP"));
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(231);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("first and last must be IP addresses, not networks")));
      } else {
         var1.setline(232);
         PyObject var3 = var1.getlocal(0).__getattr__("version");
         var10000 = var3._ne(var1.getlocal(1).__getattr__("version"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(233);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same version")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
         } else {
            var1.setline(235);
            var3 = var1.getlocal(0);
            var10000 = var3._gt(var1.getlocal(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(236);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("last IP address must be greater than first")));
            } else {
               var1.setline(238);
               PyList var5 = new PyList(Py.EmptyObjects);
               var1.setlocal(2, var5);
               var3 = null;
               var1.setline(240);
               var3 = var1.getlocal(0).__getattr__("version");
               var10000 = var3._eq(Py.newInteger(4));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(241);
                  var3 = var1.getglobal("IPv4Network");
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(242);
                  var3 = var1.getlocal(0).__getattr__("version");
                  var10000 = var3._eq(Py.newInteger(6));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(245);
                     throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unknown IP version")));
                  }

                  var1.setline(243);
                  var3 = var1.getglobal("IPv6Network");
                  var1.setlocal(3, var3);
                  var3 = null;
               }

               var1.setline(247);
               var3 = var1.getlocal(0).__getattr__("_max_prefixlen");
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(248);
               var3 = var1.getlocal(0).__getattr__("_ip");
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(249);
               var3 = var1.getlocal(1).__getattr__("_ip");
               var1.setlocal(6, var3);
               var3 = null;

               while(true) {
                  var1.setline(250);
                  var3 = var1.getlocal(5);
                  var10000 = var3._le(var1.getlocal(6));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(251);
                  var3 = var1.getglobal("_count_righthand_zero_bits").__call__(var2, var1.getlocal(5), var1.getlocal(4));
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(252);
                  var3 = var1.getglobal("None");
                  var1.setlocal(8, var3);
                  var3 = null;

                  do {
                     var1.setline(253);
                     var3 = var1.getlocal(7);
                     var10000 = var3._ge(Py.newInteger(0));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(254);
                     var3 = Py.newInteger(2)._pow(var1.getlocal(7))._sub(Py.newInteger(1));
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(255);
                     var3 = var1.getlocal(5)._add(var1.getlocal(9));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(256);
                     var3 = var1.getlocal(7);
                     var3 = var3._isub(Py.newInteger(1));
                     var1.setlocal(7, var3);
                     var1.setline(257);
                     var3 = var1.getlocal(8);
                     var10000 = var3._le(var1.getlocal(6));
                     var3 = null;
                  } while(!var10000.__nonzero__());

                  var1.setline(259);
                  var3 = var1.getglobal("_get_prefix_length").__call__(var2, var1.getlocal(5), var1.getlocal(8), var1.getlocal(4));
                  var1.setlocal(10, var3);
                  var3 = null;
                  var1.setline(260);
                  var3 = var1.getlocal(3).__call__(var2, PyString.fromInterned("%s/%d")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getlocal(10)})));
                  var1.setlocal(11, var3);
                  var3 = null;
                  var1.setline(261);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(11));
                  var1.setline(262);
                  var3 = var1.getlocal(8);
                  var10000 = var3._eq(var1.getlocal(3).__getattr__("_ALL_ONES"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }

                  var1.setline(264);
                  var3 = var1.getlocal(8)._add(Py.newInteger(1));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(265);
                  var10000 = var1.getglobal("IPAddress");
                  PyObject[] var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(0).__getattr__("_version")};
                  String[] var4 = new String[]{"version"};
                  var10000 = var10000.__call__(var2, var6, var4);
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(0, var3);
                  var3 = null;
               }

               var1.setline(266);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _collapse_address_list_recursive$11(PyFrame var1, ThreadState var2) {
      var1.setline(293);
      PyString.fromInterned("Loops through the addresses, collapsing concurrent netblocks.\n\n    Example:\n\n        ip1 = IPv4Network('1.1.0.0/24')\n        ip2 = IPv4Network('1.1.1.0/24')\n        ip3 = IPv4Network('1.1.2.0/24')\n        ip4 = IPv4Network('1.1.3.0/24')\n        ip5 = IPv4Network('1.1.4.0/24')\n        ip6 = IPv4Network('1.1.0.1/22')\n\n        _collapse_address_list_recursive([ip1, ip2, ip3, ip4, ip5, ip6]) ->\n          [IPv4Network('1.1.0.0/22'), IPv4Network('1.1.4.0/24')]\n\n        This shouldn't be called directly; it is called via\n          collapse_address_list([]).\n\n    Args:\n        addresses: A list of IPv4Network's or IPv6Network's\n\n    Returns:\n        A list of IPv4Network's or IPv6Network's depending on what we were\n        passed.\n\n    ");
      var1.setline(294);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(295);
      PyObject var6 = var1.getglobal("False");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(297);
      var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(297);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(309);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(310);
               var6 = var1.getglobal("_collapse_address_list_recursive").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var6;
            }

            var1.setline(312);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(298);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(299);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
         } else {
            var1.setline(301);
            PyObject var5 = var1.getlocal(3);
            PyObject var10000 = var5._in(var1.getlocal(1).__getitem__(Py.newInteger(-1)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(302);
               var5 = var1.getglobal("True");
               var1.setlocal(2, var5);
               var5 = null;
            } else {
               var1.setline(303);
               var5 = var1.getlocal(3);
               var10000 = var5._eq(var1.getlocal(1).__getitem__(Py.newInteger(-1)).__getattr__("supernet").__call__(var2).__getattr__("subnet").__call__(var2).__getitem__(Py.newInteger(1)));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(304);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("pop").__call__(var2).__getattr__("supernet").__call__(var2));
                  var1.setline(305);
                  var5 = var1.getglobal("True");
                  var1.setlocal(2, var5);
                  var5 = null;
               } else {
                  var1.setline(307);
                  var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
               }
            }
         }
      }
   }

   public PyObject collapse_address_list$12(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyString.fromInterned("Collapse a list of IP objects.\n\n    Example:\n        collapse_address_list([IPv4('1.1.0.0/24'), IPv4('1.1.1.0/24')]) ->\n          [IPv4('1.1.0.0/23')]\n\n    Args:\n        addresses: A list of IPv4Network or IPv6Network objects.\n\n    Returns:\n        A list of IPv4Network or IPv6Network objects depending on what we\n        were passed.\n\n    Raises:\n        TypeError: If passed a list of mixed version objects.\n\n    ");
      var1.setline(333);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(334);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(335);
      var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(336);
      var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(339);
      PyObject var9 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(339);
         PyObject var4 = var9.__iternext__();
         PyObject var10000;
         PyObject var5;
         if (var4 == null) {
            var1.setline(357);
            var9 = var1.getglobal("sorted").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(3)));
            var1.setlocal(3, var9);
            var3 = null;
            var1.setline(358);
            var9 = var1.getglobal("sorted").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(4)));
            var1.setlocal(4, var9);
            var3 = null;

            while(true) {
               var1.setline(360);
               var9 = var1.getlocal(1);
               var10000 = var9._lt(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(365);
                  var10000 = var1.getglobal("_collapse_address_list_recursive");
                  PyObject var10002 = var1.getglobal("sorted");
                  PyObject[] var10 = new PyObject[]{var1.getlocal(2)._add(var1.getlocal(4)), var1.getglobal("_BaseNet").__getattr__("_get_networks_key")};
                  String[] var8 = new String[]{"key"};
                  var10002 = var10002.__call__(var2, var10, var8);
                  var3 = null;
                  var9 = var10000.__call__(var2, var10002);
                  var1.f_lasti = -1;
                  return var9;
               }

               var1.setline(361);
               var9 = var1.getglobal("_find_address_range").__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null));
               PyObject[] var7 = Py.unpackSequence(var9, 2);
               var5 = var7[0];
               var1.setlocal(6, var5);
               var5 = null;
               var5 = var7[1];
               var1.setlocal(7, var5);
               var5 = null;
               var3 = null;
               var1.setline(362);
               var9 = var1.getlocal(3).__getattr__("index").__call__(var2, var1.getlocal(7))._add(Py.newInteger(1));
               var1.setlocal(1, var9);
               var3 = null;
               var1.setline(363);
               var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getglobal("summarize_address_range").__call__(var2, var1.getlocal(6), var1.getlocal(7)));
            }
         }

         var1.setlocal(5, var4);
         var1.setline(340);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("_BaseIP")).__nonzero__()) {
            var1.setline(341);
            var10000 = var1.getlocal(3);
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(3).__getitem__(Py.newInteger(-1)).__getattr__("_version");
               var10000 = var5._ne(var1.getlocal(5).__getattr__("_version"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(342);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same version")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(5)), var1.getglobal("str").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(-1)))}))));
            }

            var1.setline(344);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         } else {
            var1.setline(345);
            var5 = var1.getlocal(5).__getattr__("_prefixlen");
            var10000 = var5._eq(var1.getlocal(5).__getattr__("_max_prefixlen"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(346);
               var10000 = var1.getlocal(3);
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(3).__getitem__(Py.newInteger(-1)).__getattr__("_version");
                  var10000 = var5._ne(var1.getlocal(5).__getattr__("_version"));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(347);
                  throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same version")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(5)), var1.getglobal("str").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(-1)))}))));
               }

               var1.setline(349);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5).__getattr__("ip"));
            } else {
               var1.setline(351);
               var10000 = var1.getlocal(4);
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(4).__getitem__(Py.newInteger(-1)).__getattr__("_version");
                  var10000 = var5._ne(var1.getlocal(5).__getattr__("_version"));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(352);
                  throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same version")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(5)), var1.getglobal("str").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(-1)))}))));
               }

               var1.setline(354);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      }
   }

   public PyObject get_mixed_type_key$13(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      PyString.fromInterned("Return a key suitable for sorting between networks and addresses.\n\n    Address and Network objects are not sortable by default; they're\n    fundamentally different so the expression\n\n        IPv4Address('1.1.1.1') <= IPv4Network('1.1.1.1/24')\n\n    doesn't make any sense.  There are some times however, where you may wish\n    to have ipaddr sort these for you anyway. If you need to do this, you\n    can use this function as the key= argument to sorted().\n\n    Args:\n      obj: either a Network or Address object.\n    Returns:\n      appropriate key.\n\n    ");
      var1.setline(399);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_BaseNet")).__nonzero__()) {
         var1.setline(400);
         var3 = var1.getlocal(0).__getattr__("_get_networks_key").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(401);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_BaseIP")).__nonzero__()) {
            var1.setline(402);
            var3 = var1.getlocal(0).__getattr__("_get_address_key").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(403);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _IPAddrBase$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The mother class."));
      var1.setline(407);
      PyString.fromInterned("The mother class.");
      var1.setline(409);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __index__$15, (PyObject)null);
      var1.setlocal("__index__", var4);
      var3 = null;
      var1.setline(412);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __int__$16, (PyObject)null);
      var1.setlocal("__int__", var4);
      var3 = null;
      var1.setline(415);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hex__$17, (PyObject)null);
      var1.setlocal("__hex__", var4);
      var3 = null;
      var1.setline(418);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, exploded$18, PyString.fromInterned("Return the longhand version of the IP address as a string."));
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("exploded", var5);
      var3 = null;
      var1.setline(423);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compressed$19, PyString.fromInterned("Return the shorthand version of the IP address as a string."));
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("compressed", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __index__$15(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      PyObject var3 = var1.getlocal(0).__getattr__("_ip");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __int__$16(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      PyObject var3 = var1.getlocal(0).__getattr__("_ip");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hex__$17(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyObject var3 = var1.getglobal("hex").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject exploded$18(PyFrame var1, ThreadState var2) {
      var1.setline(420);
      PyString.fromInterned("Return the longhand version of the IP address as a string.");
      var1.setline(421);
      PyObject var3 = var1.getlocal(0).__getattr__("_explode_shorthand_ip_string").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject compressed$19(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      PyString.fromInterned("Return the shorthand version of the IP address as a string.");
      var1.setline(426);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _BaseIP$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A generic IP object.\n\n    This IP class contains the version independent methods which are\n    used by single IP addresses.\n\n    "));
      var1.setline(436);
      PyString.fromInterned("A generic IP object.\n\n    This IP class contains the version independent methods which are\n    used by single IP addresses.\n\n    ");
      var1.setline(438);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(443);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$22, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(450);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$23, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(456);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __le__$24, (PyObject)null);
      var1.setlocal("__le__", var4);
      var3 = null;
      var1.setline(462);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ge__$25, (PyObject)null);
      var1.setlocal("__ge__", var4);
      var3 = null;
      var1.setline(468);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __lt__$26, (PyObject)null);
      var1.setlocal("__lt__", var4);
      var3 = null;
      var1.setline(479);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __gt__$27, (PyObject)null);
      var1.setlocal("__gt__", var4);
      var3 = null;
      var1.setline(492);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __add__$28, (PyObject)null);
      var1.setlocal("__add__", var4);
      var3 = null;
      var1.setline(497);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __sub__$29, (PyObject)null);
      var1.setlocal("__sub__", var4);
      var3 = null;
      var1.setline(502);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$30, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(505);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$31, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(508);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$32, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      var1.setline(511);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_address_key$33, (PyObject)null);
      var1.setlocal("_get_address_key", var4);
      var3 = null;
      var1.setline(514);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, version$34, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("version", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      PyObject var10000 = var1.getglobal("_compat_has_real_bytes");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("bytes"));
      }

      var10000 = var10000.__not__();
      if (var10000.__nonzero__()) {
         PyString var3 = PyString.fromInterned("/");
         var10000 = var3._in(var1.getglobal("str").__call__(var2, var1.getlocal(1)));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(441);
         throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __eq__$22(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(445);
         var3 = var1.getlocal(0).__getattr__("_ip");
         PyObject var10000 = var3._eq(var1.getlocal(1).__getattr__("_ip"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_version");
            var10000 = var3._eq(var1.getlocal(1).__getattr__("_version"));
            var3 = null;
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(448);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject __ne__$23(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      PyObject var3 = var1.getlocal(0).__getattr__("__eq__").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(452);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(453);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(454);
         var3 = var1.getlocal(2).__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __le__$24(PyFrame var1, ThreadState var2) {
      var1.setline(457);
      PyObject var3 = var1.getlocal(0).__getattr__("__gt__").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(458);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(459);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(460);
         var3 = var1.getlocal(2).__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ge__$25(PyFrame var1, ThreadState var2) {
      var1.setline(463);
      PyObject var3 = var1.getlocal(0).__getattr__("__lt__").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(464);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(465);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(466);
         var3 = var1.getlocal(2).__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __lt__$26(PyFrame var1, ThreadState var2) {
      var1.setline(469);
      PyObject var3 = var1.getlocal(0).__getattr__("_version");
      PyObject var10000 = var3._ne(var1.getlocal(1).__getattr__("_version"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(470);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same version")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
      } else {
         var1.setline(472);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_BaseIP")).__not__().__nonzero__()) {
            var1.setline(473);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same type")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
         } else {
            var1.setline(475);
            var3 = var1.getlocal(0).__getattr__("_ip");
            var10000 = var3._ne(var1.getlocal(1).__getattr__("_ip"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(476);
               var3 = var1.getlocal(0).__getattr__("_ip");
               var10000 = var3._lt(var1.getlocal(1).__getattr__("_ip"));
               var3 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(477);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject __gt__$27(PyFrame var1, ThreadState var2) {
      var1.setline(480);
      PyObject var3 = var1.getlocal(0).__getattr__("_version");
      PyObject var10000 = var3._ne(var1.getlocal(1).__getattr__("_version"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(481);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same version")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
      } else {
         var1.setline(483);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_BaseIP")).__not__().__nonzero__()) {
            var1.setline(484);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same type")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
         } else {
            var1.setline(486);
            var3 = var1.getlocal(0).__getattr__("_ip");
            var10000 = var3._ne(var1.getlocal(1).__getattr__("_ip"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(487);
               var3 = var1.getlocal(0).__getattr__("_ip");
               var10000 = var3._gt(var1.getlocal(1).__getattr__("_ip"));
               var3 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(488);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject __add__$28(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("int")).__not__().__nonzero__()) {
         var1.setline(494);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(495);
         PyObject var10000 = var1.getglobal("IPAddress");
         PyObject[] var4 = new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(0))._add(var1.getlocal(1)), var1.getlocal(0).__getattr__("_version")};
         String[] var5 = new String[]{"version"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __sub__$29(PyFrame var1, ThreadState var2) {
      var1.setline(498);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("int")).__not__().__nonzero__()) {
         var1.setline(499);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(500);
         PyObject var10000 = var1.getglobal("IPAddress");
         PyObject[] var4 = new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(0))._sub(var1.getlocal(1)), var1.getlocal(0).__getattr__("_version")};
         String[] var5 = new String[]{"version"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __repr__$30(PyFrame var1, ThreadState var2) {
      var1.setline(503);
      PyObject var3 = PyString.fromInterned("%s(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("str").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$31(PyFrame var1, ThreadState var2) {
      var1.setline(506);
      PyObject var3 = PyString.fromInterned("%s")._mod(var1.getlocal(0).__getattr__("_string_from_ip_int").__call__(var2, var1.getlocal(0).__getattr__("_ip")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$32(PyFrame var1, ThreadState var2) {
      var1.setline(509);
      PyObject var3 = var1.getglobal("hash").__call__(var2, var1.getglobal("hex").__call__(var2, var1.getglobal("long").__call__(var2, var1.getlocal(0).__getattr__("_ip"))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_address_key$33(PyFrame var1, ThreadState var2) {
      var1.setline(512);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_version"), var1.getlocal(0)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject version$34(PyFrame var1, ThreadState var2) {
      var1.setline(516);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BaseIP has no version")));
   }

   public PyObject _BaseNet$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A generic IP object.\n\n    This IP class contains the version independent methods which are\n    used by networks.\n\n    "));
      var1.setline(526);
      PyString.fromInterned("A generic IP object.\n\n    This IP class contains the version independent methods which are\n    used by networks.\n\n    ");
      var1.setline(528);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$36, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(531);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$37, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(534);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterhosts$38, PyString.fromInterned("Generate Iterator over usable hosts in a network.\n\n           This is like __iter__ except it doesn't return the network\n           or broadcast addresses.\n\n        "));
      var1.setlocal("iterhosts", var4);
      var3 = null;
      var1.setline(547);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$39, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(554);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$40, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(567);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __lt__$41, (PyObject)null);
      var1.setlocal("__lt__", var4);
      var3 = null;
      var1.setline(580);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __gt__$42, (PyObject)null);
      var1.setlocal("__gt__", var4);
      var3 = null;
      var1.setline(593);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __le__$43, (PyObject)null);
      var1.setlocal("__le__", var4);
      var3 = null;
      var1.setline(599);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ge__$44, (PyObject)null);
      var1.setlocal("__ge__", var4);
      var3 = null;
      var1.setline(605);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$45, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(615);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$46, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(621);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$47, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(625);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$48, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      var1.setline(628);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$49, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(641);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, overlaps$50, PyString.fromInterned("Tell if self is partly contained in other."));
      var1.setlocal("overlaps", var4);
      var3 = null;
      var1.setline(646);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, network$51, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("network", var5);
      var3 = null;
      var1.setline(654);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, broadcast$52, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("broadcast", var5);
      var3 = null;
      var1.setline(662);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, hostmask$53, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("hostmask", var5);
      var3 = null;
      var1.setline(671);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, with_prefixlen$54, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("with_prefixlen", var5);
      var3 = null;
      var1.setline(675);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, with_netmask$55, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("with_netmask", var5);
      var3 = null;
      var1.setline(679);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, with_hostmask$56, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("with_hostmask", var5);
      var3 = null;
      var1.setline(683);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, numhosts$57, PyString.fromInterned("Number of hosts in the current subnet."));
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("numhosts", var5);
      var3 = null;
      var1.setline(688);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, version$58, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("version", var5);
      var3 = null;
      var1.setline(692);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, prefixlen$59, (PyObject)null);
      var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("prefixlen", var5);
      var3 = null;
      var1.setline(696);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, address_exclude$60, PyString.fromInterned("Remove an address from a larger block.\n\n        For example:\n\n            addr1 = IPNetwork('10.1.1.0/24')\n            addr2 = IPNetwork('10.1.1.0/26')\n            addr1.address_exclude(addr2) =\n                [IPNetwork('10.1.1.64/26'), IPNetwork('10.1.1.128/25')]\n\n        or IPv6:\n\n            addr1 = IPNetwork('::1/32')\n            addr2 = IPNetwork('::1/128')\n            addr1.address_exclude(addr2) = [IPNetwork('::0/128'),\n                IPNetwork('::2/127'),\n                IPNetwork('::4/126'),\n                IPNetwork('::8/125'),\n                ...\n                IPNetwork('0:0:8000::/33')]\n\n        Args:\n            other: An IPvXNetwork object of the same type.\n\n        Returns:\n            A sorted list of IPvXNetwork objects addresses which is self\n            minus other.\n\n        Raises:\n            TypeError: If self and other are of difffering address\n              versions, or if other is not a network object.\n            ValueError: If other is not completely contained by self.\n\n        "));
      var1.setlocal("address_exclude", var4);
      var3 = null;
      var1.setline(774);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compare_networks$61, PyString.fromInterned("Compare two IP objects.\n\n        This is only concerned about the comparison of the integer\n        representation of the network addresses.  This means that the\n        host bits aren't considered at all in this method.  If you want\n        to compare host bits, you can easily enough do a\n        'HostA._ip < HostB._ip'\n\n        Args:\n            other: An IP object.\n\n        Returns:\n            If the IP versions of self and other are the same, returns:\n\n            -1 if self < other:\n              eg: IPv4('1.1.1.0/24') < IPv4('1.1.2.0/24')\n              IPv6('1080::200C:417A') < IPv6('1080::200B:417B')\n            0 if self == other\n              eg: IPv4('1.1.1.1/24') == IPv4('1.1.1.2/24')\n              IPv6('1080::200C:417A/96') == IPv6('1080::200C:417B/96')\n            1 if self > other\n              eg: IPv4('1.1.1.0/24') > IPv4('1.1.0.0/24')\n              IPv6('1080::1:200C:417A/112') >\n              IPv6('1080::0:200C:417A/112')\n\n            If the IP versions of self and other are different, returns:\n\n            -1 if self._version < other._version\n              eg: IPv4('10.0.0.1/24') < IPv6('::1/128')\n            1 if self._version > other._version\n              eg: IPv6('::1/128') > IPv4('255.255.255.0/24')\n\n        "));
      var1.setlocal("compare_networks", var4);
      var3 = null;
      var1.setline(825);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_networks_key$62, PyString.fromInterned("Network-only key function.\n\n        Returns an object that identifies this address' network and\n        netmask. This function is a suitable \"key\" argument for sorted()\n        and list.sort().\n\n        "));
      var1.setlocal("_get_networks_key", var4);
      var3 = null;
      var1.setline(835);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _ip_int_from_prefix$63, PyString.fromInterned("Turn the prefix length netmask into a int for comparison.\n\n        Args:\n            prefixlen: An integer, the prefix length.\n\n        Returns:\n            An integer.\n\n        "));
      var1.setlocal("_ip_int_from_prefix", var4);
      var3 = null;
      var1.setline(849);
      var3 = new PyObject[]{Py.newInteger(32)};
      var4 = new PyFunction(var1.f_globals, var3, _prefix_from_ip_int$64, PyString.fromInterned("Return prefix length from the decimal netmask.\n\n        Args:\n            ip_int: An integer, the IP address.\n            mask: The netmask.  Defaults to 32.\n\n        Returns:\n            An integer, the prefix length.\n\n        "));
      var1.setlocal("_prefix_from_ip_int", var4);
      var3 = null;
      var1.setline(868);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _ip_string_from_prefix$65, PyString.fromInterned("Turn a prefix length into a dotted decimal string.\n\n        Args:\n            prefixlen: An integer, the netmask prefix length.\n\n        Returns:\n            A string, the dotted decimal netmask string.\n\n        "));
      var1.setlocal("_ip_string_from_prefix", var4);
      var3 = null;
      var1.setline(882);
      var3 = new PyObject[]{Py.newInteger(1), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, iter_subnets$66, PyString.fromInterned("The subnets which join to make the current subnet.\n\n        In the case that self contains only one IP\n        (self._prefixlen == 32 for IPv4 or self._prefixlen == 128\n        for IPv6), return a list with just ourself.\n\n        Args:\n            prefixlen_diff: An integer, the amount the prefix length\n              should be increased by. This should not be set if\n              new_prefix is also set.\n            new_prefix: The desired new prefix length. This must be a\n              larger number (smaller prefix) than the existing prefix.\n              This should not be set if prefixlen_diff is also set.\n\n        Returns:\n            An iterator of IPv(4|6) objects.\n\n        Raises:\n            ValueError: The prefixlen_diff is too small or too large.\n                OR\n            prefixlen_diff and new_prefix are both set or new_prefix\n              is a smaller number than the current prefix (smaller\n              number means a larger network)\n\n        "));
      var1.setlocal("iter_subnets", var4);
      var3 = null;
      var1.setline(944);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, masked$67, PyString.fromInterned("Return the network object with the host bits masked out."));
      var1.setlocal("masked", var4);
      var3 = null;
      var1.setline(949);
      var3 = new PyObject[]{Py.newInteger(1), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, subnet$68, PyString.fromInterned("Return a list of subnets, rather than an iterator."));
      var1.setlocal("subnet", var4);
      var3 = null;
      var1.setline(953);
      var3 = new PyObject[]{Py.newInteger(1), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, supernet$69, PyString.fromInterned("The supernet containing the current network.\n\n        Args:\n            prefixlen_diff: An integer, the amount the prefix length of\n              the network should be decreased by.  For example, given a\n              /24 network and a prefixlen_diff of 3, a supernet with a\n              /21 netmask is returned.\n\n        Returns:\n            An IPv4 network object.\n\n        Raises:\n            ValueError: If self.prefixlen - prefixlen_diff < 0. I.e., you have a\n              negative prefix length.\n                OR\n            If prefixlen_diff and new_prefix are both set or new_prefix is a\n              larger number than the current prefix (larger number means a\n              smaller network)\n\n        "));
      var1.setlocal("supernet", var4);
      var3 = null;
      var1.setline(994);
      var5 = var1.getname("subnet");
      var1.setlocal("Subnet", var5);
      var3 = null;
      var1.setline(995);
      var5 = var1.getname("supernet");
      var1.setlocal("Supernet", var5);
      var3 = null;
      var1.setline(996);
      var5 = var1.getname("address_exclude");
      var1.setlocal("AddressExclude", var5);
      var3 = null;
      var1.setline(997);
      var5 = var1.getname("compare_networks");
      var1.setlocal("CompareNetworks", var5);
      var3 = null;
      var1.setline(998);
      var5 = var1.getname("__contains__");
      var1.setlocal("Contains", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$36(PyFrame var1, ThreadState var2) {
      var1.setline(529);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_cache", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$37(PyFrame var1, ThreadState var2) {
      var1.setline(532);
      PyObject var3 = PyString.fromInterned("%s(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("str").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iterhosts$38(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(540);
            PyString.fromInterned("Generate Iterator over usable hosts in a network.\n\n           This is like __iter__ except it doesn't return the network\n           or broadcast addresses.\n\n        ");
            var1.setline(541);
            var5 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("network"))._add(Py.newInteger(1));
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(542);
            var5 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("broadcast"))._sub(Py.newInteger(1));
            var1.setlocal(2, var5);
            var3 = null;
            break;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
      }

      var1.setline(543);
      var5 = var1.getlocal(1);
      var7 = var5._le(var1.getlocal(2));
      var3 = null;
      if (!var7.__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(544);
         var5 = var1.getlocal(1);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(1, var5);
         var1.setline(545);
         var1.setline(545);
         var7 = var1.getglobal("IPAddress");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1)._sub(Py.newInteger(1)), var1.getlocal(0).__getattr__("_version")};
         String[] var4 = new String[]{"version"};
         var7 = var7.__call__(var2, var6, var4);
         var3 = null;
         var1.f_lasti = 1;
         var3 = new Object[5];
         var1.f_savedlocals = var3;
         return var7;
      }
   }

   public PyObject __iter__$39(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(548);
            var5 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("network"));
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(549);
            var5 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("broadcast"));
            var1.setlocal(2, var5);
            var3 = null;
            break;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
      }

      var1.setline(550);
      var5 = var1.getlocal(1);
      var7 = var5._le(var1.getlocal(2));
      var3 = null;
      if (!var7.__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(551);
         var5 = var1.getlocal(1);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(1, var5);
         var1.setline(552);
         var1.setline(552);
         var7 = var1.getglobal("IPAddress");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1)._sub(Py.newInteger(1)), var1.getlocal(0).__getattr__("_version")};
         String[] var4 = new String[]{"version"};
         var7 = var7.__call__(var2, var6, var4);
         var3 = null;
         var1.f_lasti = 1;
         var3 = new Object[5];
         var1.f_savedlocals = var3;
         return var7;
      }
   }

   public PyObject __getitem__$40(PyFrame var1, ThreadState var2) {
      var1.setline(555);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("network"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(556);
      var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("broadcast"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(557);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(558);
         var3 = var1.getlocal(2)._add(var1.getlocal(1));
         var10000 = var3._gt(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(559);
            throw Py.makeException(var1.getglobal("IndexError"));
         } else {
            var1.setline(560);
            var10000 = var1.getglobal("IPAddress");
            PyObject[] var7 = new PyObject[]{var1.getlocal(2)._add(var1.getlocal(1)), var1.getlocal(0).__getattr__("_version")};
            String[] var8 = new String[]{"version"};
            var10000 = var10000.__call__(var2, var7, var8);
            var3 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(562);
         PyObject var4 = var1.getlocal(1);
         var4 = var4._iadd(Py.newInteger(1));
         var1.setlocal(1, var4);
         var1.setline(563);
         var4 = var1.getlocal(3)._add(var1.getlocal(1));
         var10000 = var4._lt(var1.getlocal(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(564);
            throw Py.makeException(var1.getglobal("IndexError"));
         } else {
            var1.setline(565);
            var10000 = var1.getglobal("IPAddress");
            PyObject[] var6 = new PyObject[]{var1.getlocal(3)._add(var1.getlocal(1)), var1.getlocal(0).__getattr__("_version")};
            String[] var5 = new String[]{"version"};
            var10000 = var10000.__call__(var2, var6, var5);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __lt__$41(PyFrame var1, ThreadState var2) {
      var1.setline(568);
      PyObject var3 = var1.getlocal(0).__getattr__("_version");
      PyObject var10000 = var3._ne(var1.getlocal(1).__getattr__("_version"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(569);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same version")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
      } else {
         var1.setline(571);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_BaseNet")).__not__().__nonzero__()) {
            var1.setline(572);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same type")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
         } else {
            var1.setline(574);
            var3 = var1.getlocal(0).__getattr__("network");
            var10000 = var3._ne(var1.getlocal(1).__getattr__("network"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(575);
               var3 = var1.getlocal(0).__getattr__("network");
               var10000 = var3._lt(var1.getlocal(1).__getattr__("network"));
               var3 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(576);
               PyObject var4 = var1.getlocal(0).__getattr__("netmask");
               var10000 = var4._ne(var1.getlocal(1).__getattr__("netmask"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(577);
                  var4 = var1.getlocal(0).__getattr__("netmask");
                  var10000 = var4._lt(var1.getlocal(1).__getattr__("netmask"));
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(578);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject __gt__$42(PyFrame var1, ThreadState var2) {
      var1.setline(581);
      PyObject var3 = var1.getlocal(0).__getattr__("_version");
      PyObject var10000 = var3._ne(var1.getlocal(1).__getattr__("_version"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(582);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same version")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
      } else {
         var1.setline(584);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_BaseNet")).__not__().__nonzero__()) {
            var1.setline(585);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same type")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
         } else {
            var1.setline(587);
            var3 = var1.getlocal(0).__getattr__("network");
            var10000 = var3._ne(var1.getlocal(1).__getattr__("network"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(588);
               var3 = var1.getlocal(0).__getattr__("network");
               var10000 = var3._gt(var1.getlocal(1).__getattr__("network"));
               var3 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(589);
               PyObject var4 = var1.getlocal(0).__getattr__("netmask");
               var10000 = var4._ne(var1.getlocal(1).__getattr__("netmask"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(590);
                  var4 = var1.getlocal(0).__getattr__("netmask");
                  var10000 = var4._gt(var1.getlocal(1).__getattr__("netmask"));
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(591);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject __le__$43(PyFrame var1, ThreadState var2) {
      var1.setline(594);
      PyObject var3 = var1.getlocal(0).__getattr__("__gt__").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(595);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(596);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(597);
         var3 = var1.getlocal(2).__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ge__$44(PyFrame var1, ThreadState var2) {
      var1.setline(600);
      PyObject var3 = var1.getlocal(0).__getattr__("__lt__").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(601);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(602);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(603);
         var3 = var1.getlocal(2).__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __eq__$45(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      PyObject var3;
      try {
         var1.setline(607);
         var3 = var1.getlocal(0).__getattr__("_version");
         var10000 = var3._eq(var1.getlocal(1).__getattr__("_version"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("network");
            var10000 = var3._eq(var1.getlocal(1).__getattr__("network"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("netmask"));
               var10000 = var3._eq(var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("netmask")));
               var3 = null;
            }
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(611);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_BaseIP")).__nonzero__()) {
               var1.setline(612);
               PyObject var5 = var1.getlocal(0).__getattr__("_version");
               var10000 = var5._eq(var1.getlocal(1).__getattr__("_version"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(0).__getattr__("_ip");
                  var10000 = var5._eq(var1.getlocal(1).__getattr__("_ip"));
                  var5 = null;
               }

               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject __ne__$46(PyFrame var1, ThreadState var2) {
      var1.setline(616);
      PyObject var3 = var1.getlocal(0).__getattr__("__eq__").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(617);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("NotImplemented"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(618);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(619);
         var3 = var1.getlocal(2).__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __str__$47(PyFrame var1, ThreadState var2) {
      var1.setline(622);
      PyObject var3 = PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("ip")), var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("_prefixlen"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$48(PyFrame var1, ThreadState var2) {
      var1.setline(626);
      PyObject var3 = var1.getglobal("hash").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("network"))._xor(var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("netmask"))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __contains__$49(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyObject var3 = var1.getlocal(0).__getattr__("_version");
      PyObject var10000 = var3._ne(var1.getlocal(1).__getattr__("_version"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(631);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(633);
         PyObject var4;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_BaseNet")).__nonzero__()) {
            var1.setline(634);
            var4 = var1.getlocal(0).__getattr__("network");
            var10000 = var4._le(var1.getlocal(1).__getattr__("network"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(0).__getattr__("broadcast");
               var10000 = var4._ge(var1.getlocal(1).__getattr__("broadcast"));
               var4 = null;
            }

            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(638);
            var4 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("network"));
            PyObject var10001 = var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("_ip"));
            var10000 = var4;
            var4 = var10001;
            PyObject var5;
            if ((var5 = var10000._le(var10001)).__nonzero__()) {
               var5 = var4._le(var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("broadcast")));
            }

            var4 = null;
            var1.f_lasti = -1;
            return var5;
         }
      }
   }

   public PyObject overlaps$50(PyFrame var1, ThreadState var2) {
      var1.setline(642);
      PyString.fromInterned("Tell if self is partly contained in other.");
      var1.setline(643);
      PyObject var3 = var1.getlocal(0).__getattr__("network");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("broadcast");
         var10000 = var3._in(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getattr__("network");
            var10000 = var3._in(var1.getlocal(0));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(1).__getattr__("broadcast");
               var10000 = var3._in(var1.getlocal(0));
               var3 = null;
            }
         }
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject network$51(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyObject var3 = var1.getlocal(0).__getattr__("_cache").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("network"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(649);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(650);
         var10000 = var1.getglobal("IPAddress");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("_ip")._and(var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("netmask"))), var1.getlocal(0).__getattr__("_version")};
         String[] var4 = new String[]{"version"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(651);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("_cache").__setitem__((PyObject)PyString.fromInterned("network"), var3);
         var3 = null;
      }

      var1.setline(652);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject broadcast$52(PyFrame var1, ThreadState var2) {
      var1.setline(656);
      PyObject var3 = var1.getlocal(0).__getattr__("_cache").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("broadcast"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(657);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(658);
         var10000 = var1.getglobal("IPAddress");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("_ip")._or(var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("hostmask"))), var1.getlocal(0).__getattr__("_version")};
         String[] var4 = new String[]{"version"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(659);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("_cache").__setitem__((PyObject)PyString.fromInterned("broadcast"), var3);
         var3 = null;
      }

      var1.setline(660);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject hostmask$53(PyFrame var1, ThreadState var2) {
      var1.setline(664);
      PyObject var3 = var1.getlocal(0).__getattr__("_cache").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hostmask"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(665);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(666);
         var10000 = var1.getglobal("IPAddress");
         PyObject[] var5 = new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("netmask"))._xor(var1.getlocal(0).__getattr__("_ALL_ONES")), var1.getlocal(0).__getattr__("_version")};
         String[] var4 = new String[]{"version"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(668);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("_cache").__setitem__((PyObject)PyString.fromInterned("hostmask"), var3);
         var3 = null;
      }

      var1.setline(669);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject with_prefixlen$54(PyFrame var1, ThreadState var2) {
      var1.setline(673);
      PyObject var3 = PyString.fromInterned("%s/%d")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("ip")), var1.getlocal(0).__getattr__("_prefixlen")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject with_netmask$55(PyFrame var1, ThreadState var2) {
      var1.setline(677);
      PyObject var3 = PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("ip")), var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("netmask"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject with_hostmask$56(PyFrame var1, ThreadState var2) {
      var1.setline(681);
      PyObject var3 = PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("ip")), var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("hostmask"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject numhosts$57(PyFrame var1, ThreadState var2) {
      var1.setline(685);
      PyString.fromInterned("Number of hosts in the current subnet.");
      var1.setline(686);
      PyObject var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("broadcast"))._sub(var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("network")))._add(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject version$58(PyFrame var1, ThreadState var2) {
      var1.setline(690);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BaseNet has no version")));
   }

   public PyObject prefixlen$59(PyFrame var1, ThreadState var2) {
      var1.setline(694);
      PyObject var3 = var1.getlocal(0).__getattr__("_prefixlen");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject address_exclude$60(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      PyString.fromInterned("Remove an address from a larger block.\n\n        For example:\n\n            addr1 = IPNetwork('10.1.1.0/24')\n            addr2 = IPNetwork('10.1.1.0/26')\n            addr1.address_exclude(addr2) =\n                [IPNetwork('10.1.1.64/26'), IPNetwork('10.1.1.128/25')]\n\n        or IPv6:\n\n            addr1 = IPNetwork('::1/32')\n            addr2 = IPNetwork('::1/128')\n            addr1.address_exclude(addr2) = [IPNetwork('::0/128'),\n                IPNetwork('::2/127'),\n                IPNetwork('::4/126'),\n                IPNetwork('::8/125'),\n                ...\n                IPNetwork('0:0:8000::/33')]\n\n        Args:\n            other: An IPvXNetwork object of the same type.\n\n        Returns:\n            A sorted list of IPvXNetwork objects addresses which is self\n            minus other.\n\n        Raises:\n            TypeError: If self and other are of difffering address\n              versions, or if other is not a network object.\n            ValueError: If other is not completely contained by self.\n\n        ");
      var1.setline(730);
      PyObject var3 = var1.getlocal(0).__getattr__("_version");
      PyObject var10000 = var3._eq(var1.getlocal(1).__getattr__("_version"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(731);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s and %s are not of the same version")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0)), var1.getglobal("str").__call__(var2, var1.getlocal(1))}))));
      } else {
         var1.setline(734);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_BaseNet")).__not__().__nonzero__()) {
            var1.setline(735);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("%s is not a network object")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(1)))));
         } else {
            var1.setline(737);
            var3 = var1.getlocal(1);
            var10000 = var3._notin(var1.getlocal(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(738);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%s not contained in %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(1)), var1.getglobal("str").__call__(var2, var1.getlocal(0))}))));
            } else {
               var1.setline(740);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(var1.getlocal(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(741);
                  PyList var8 = new PyList(Py.EmptyObjects);
                  var1.f_lasti = -1;
                  return var8;
               } else {
                  var1.setline(743);
                  PyList var4 = new PyList(Py.EmptyObjects);
                  var1.setlocal(2, var4);
                  var4 = null;
                  var1.setline(746);
                  var10000 = var1.getglobal("IPNetwork");
                  PyObject[] var7 = new PyObject[]{PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("network")), var1.getglobal("str").__call__(var2, var1.getlocal(1).__getattr__("prefixlen"))})), var1.getlocal(1).__getattr__("_version")};
                  String[] var5 = new String[]{"version"};
                  var10000 = var10000.__call__(var2, var7, var5);
                  var4 = null;
                  PyObject var9 = var10000;
                  var1.setlocal(1, var9);
                  var4 = null;
                  var1.setline(749);
                  var9 = var1.getlocal(0).__getattr__("subnet").__call__(var2);
                  PyObject[] var10 = Py.unpackSequence(var9, 2);
                  PyObject var6 = var10[0];
                  var1.setlocal(3, var6);
                  var6 = null;
                  var6 = var10[1];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var4 = null;

                  while(true) {
                     var1.setline(750);
                     var9 = var1.getlocal(3);
                     var10000 = var9._ne(var1.getlocal(1));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var9 = var1.getlocal(4);
                        var10000 = var9._ne(var1.getlocal(1));
                        var4 = null;
                     }

                     if (!var10000.__nonzero__()) {
                        var1.setline(762);
                        var9 = var1.getlocal(3);
                        var10000 = var9._eq(var1.getlocal(1));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(763);
                           var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
                        } else {
                           var1.setline(764);
                           var9 = var1.getlocal(4);
                           var10000 = var9._eq(var1.getlocal(1));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(765);
                              var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
                           } else {
                              var1.setline(768);
                              if (var1.getglobal("__debug__").__nonzero__()) {
                                 var9 = var1.getglobal("True");
                                 var10000 = var9._eq(var1.getglobal("False"));
                                 var4 = null;
                                 if (!var10000.__nonzero__()) {
                                    throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Error performing exclusion: s1: %s s2: %s other: %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(3)), var1.getglobal("str").__call__(var2, var1.getlocal(4)), var1.getglobal("str").__call__(var2, var1.getlocal(1))})));
                                 }
                              }
                           }
                        }

                        var1.setline(772);
                        var10000 = var1.getglobal("sorted");
                        var7 = new PyObject[]{var1.getlocal(2), var1.getglobal("_BaseNet").__getattr__("_get_networks_key")};
                        var5 = new String[]{"key"};
                        var10000 = var10000.__call__(var2, var7, var5);
                        var4 = null;
                        var3 = var10000;
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setline(751);
                     var9 = var1.getlocal(1);
                     var10000 = var9._in(var1.getlocal(3));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(752);
                        var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
                        var1.setline(753);
                        var9 = var1.getlocal(3).__getattr__("subnet").__call__(var2);
                        var10 = Py.unpackSequence(var9, 2);
                        var6 = var10[0];
                        var1.setlocal(3, var6);
                        var6 = null;
                        var6 = var10[1];
                        var1.setlocal(4, var6);
                        var6 = null;
                        var4 = null;
                     } else {
                        var1.setline(754);
                        var9 = var1.getlocal(1);
                        var10000 = var9._in(var1.getlocal(4));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(755);
                           var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
                           var1.setline(756);
                           var9 = var1.getlocal(4).__getattr__("subnet").__call__(var2);
                           var10 = Py.unpackSequence(var9, 2);
                           var6 = var10[0];
                           var1.setlocal(3, var6);
                           var6 = null;
                           var6 = var10[1];
                           var1.setlocal(4, var6);
                           var6 = null;
                           var4 = null;
                        } else {
                           var1.setline(759);
                           if (var1.getglobal("__debug__").__nonzero__()) {
                              var9 = var1.getglobal("True");
                              var10000 = var9._eq(var1.getglobal("False"));
                              var4 = null;
                              if (!var10000.__nonzero__()) {
                                 throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Error performing exclusion: s1: %s s2: %s other: %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(3)), var1.getglobal("str").__call__(var2, var1.getlocal(4)), var1.getglobal("str").__call__(var2, var1.getlocal(1))})));
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

   public PyObject compare_networks$61(PyFrame var1, ThreadState var2) {
      var1.setline(807);
      PyString.fromInterned("Compare two IP objects.\n\n        This is only concerned about the comparison of the integer\n        representation of the network addresses.  This means that the\n        host bits aren't considered at all in this method.  If you want\n        to compare host bits, you can easily enough do a\n        'HostA._ip < HostB._ip'\n\n        Args:\n            other: An IP object.\n\n        Returns:\n            If the IP versions of self and other are the same, returns:\n\n            -1 if self < other:\n              eg: IPv4('1.1.1.0/24') < IPv4('1.1.2.0/24')\n              IPv6('1080::200C:417A') < IPv6('1080::200B:417B')\n            0 if self == other\n              eg: IPv4('1.1.1.1/24') == IPv4('1.1.1.2/24')\n              IPv6('1080::200C:417A/96') == IPv6('1080::200C:417B/96')\n            1 if self > other\n              eg: IPv4('1.1.1.0/24') > IPv4('1.1.0.0/24')\n              IPv6('1080::1:200C:417A/112') >\n              IPv6('1080::0:200C:417A/112')\n\n            If the IP versions of self and other are different, returns:\n\n            -1 if self._version < other._version\n              eg: IPv4('10.0.0.1/24') < IPv6('::1/128')\n            1 if self._version > other._version\n              eg: IPv6('::1/128') > IPv4('255.255.255.0/24')\n\n        ");
      var1.setline(808);
      PyObject var3 = var1.getlocal(0).__getattr__("_version");
      PyObject var10000 = var3._lt(var1.getlocal(1).__getattr__("_version"));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(809);
         var5 = Py.newInteger(-1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(810);
         PyObject var4 = var1.getlocal(0).__getattr__("_version");
         var10000 = var4._gt(var1.getlocal(1).__getattr__("_version"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(811);
            var5 = Py.newInteger(1);
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(813);
            var4 = var1.getlocal(0).__getattr__("network");
            var10000 = var4._lt(var1.getlocal(1).__getattr__("network"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(814);
               var5 = Py.newInteger(-1);
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(815);
               var4 = var1.getlocal(0).__getattr__("network");
               var10000 = var4._gt(var1.getlocal(1).__getattr__("network"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(816);
                  var5 = Py.newInteger(1);
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(818);
                  var4 = var1.getlocal(0).__getattr__("netmask");
                  var10000 = var4._lt(var1.getlocal(1).__getattr__("netmask"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(819);
                     var5 = Py.newInteger(-1);
                     var1.f_lasti = -1;
                     return var5;
                  } else {
                     var1.setline(820);
                     var4 = var1.getlocal(0).__getattr__("netmask");
                     var10000 = var4._gt(var1.getlocal(1).__getattr__("netmask"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(821);
                        var5 = Py.newInteger(1);
                        var1.f_lasti = -1;
                        return var5;
                     } else {
                        var1.setline(823);
                        var5 = Py.newInteger(0);
                        var1.f_lasti = -1;
                        return var5;
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject _get_networks_key$62(PyFrame var1, ThreadState var2) {
      var1.setline(832);
      PyString.fromInterned("Network-only key function.\n\n        Returns an object that identifies this address' network and\n        netmask. This function is a suitable \"key\" argument for sorted()\n        and list.sort().\n\n        ");
      var1.setline(833);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_version"), var1.getlocal(0).__getattr__("network"), var1.getlocal(0).__getattr__("netmask")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ip_int_from_prefix$63(PyFrame var1, ThreadState var2) {
      var1.setline(844);
      PyString.fromInterned("Turn the prefix length netmask into a int for comparison.\n\n        Args:\n            prefixlen: An integer, the prefix length.\n\n        Returns:\n            An integer.\n\n        ");
      var1.setline(845);
      PyObject var10000 = var1.getlocal(1).__not__();
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(846);
         var3 = var1.getlocal(0).__getattr__("_prefixlen");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(847);
      var3 = var1.getlocal(0).__getattr__("_ALL_ONES")._xor(var1.getlocal(0).__getattr__("_ALL_ONES")._rshift(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _prefix_from_ip_int$64(PyFrame var1, ThreadState var2) {
      var1.setline(859);
      PyString.fromInterned("Return prefix length from the decimal netmask.\n\n        Args:\n            ip_int: An integer, the IP address.\n            mask: The netmask.  Defaults to 32.\n\n        Returns:\n            An integer, the prefix length.\n\n        ");

      PyObject var3;
      while(true) {
         var1.setline(860);
         if (!var1.getlocal(2).__nonzero__()) {
            break;
         }

         var1.setline(861);
         var3 = var1.getlocal(1)._and(Py.newInteger(1));
         PyObject var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(863);
         var3 = var1.getlocal(1);
         var3 = var3._irshift(Py.newInteger(1));
         var1.setlocal(1, var3);
         var1.setline(864);
         var3 = var1.getlocal(2);
         var3 = var3._isub(Py.newInteger(1));
         var1.setlocal(2, var3);
      }

      var1.setline(866);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ip_string_from_prefix$65(PyFrame var1, ThreadState var2) {
      var1.setline(877);
      PyString.fromInterned("Turn a prefix length into a dotted decimal string.\n\n        Args:\n            prefixlen: An integer, the netmask prefix length.\n\n        Returns:\n            A string, the dotted decimal netmask string.\n\n        ");
      var1.setline(878);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(879);
         var3 = var1.getlocal(0).__getattr__("_prefixlen");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(880);
      var3 = var1.getlocal(0).__getattr__("_string_from_ip_int").__call__(var2, var1.getlocal(0).__getattr__("_ip_int_from_prefix").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iter_subnets$66(PyFrame var1, ThreadState var2) {
      Object var10000;
      PyString var10002;
      Object[] var3;
      PyTuple var10003;
      String[] var4;
      PyObject var5;
      PyObject[] var6;
      PyObject[] var7;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(907);
            PyString.fromInterned("The subnets which join to make the current subnet.\n\n        In the case that self contains only one IP\n        (self._prefixlen == 32 for IPv4 or self._prefixlen == 128\n        for IPv6), return a list with just ourself.\n\n        Args:\n            prefixlen_diff: An integer, the amount the prefix length\n              should be increased by. This should not be set if\n              new_prefix is also set.\n            new_prefix: The desired new prefix length. This must be a\n              larger number (smaller prefix) than the existing prefix.\n              This should not be set if prefixlen_diff is also set.\n\n        Returns:\n            An iterator of IPv(4|6) objects.\n\n        Raises:\n            ValueError: The prefixlen_diff is too small or too large.\n                OR\n            prefixlen_diff and new_prefix are both set or new_prefix\n              is a smaller number than the current prefix (smaller\n              number means a larger network)\n\n        ");
            var1.setline(908);
            var5 = var1.getlocal(0).__getattr__("_prefixlen");
            var8 = var5._eq(var1.getlocal(0).__getattr__("_max_prefixlen"));
            var3 = null;
            if (var8.__nonzero__()) {
               var1.setline(909);
               var1.setline(909);
               var8 = var1.getlocal(0);
               var1.f_lasti = 1;
               var3 = new Object[5];
               var1.f_savedlocals = var3;
               return var8;
            }

            var1.setline(912);
            var5 = var1.getlocal(2);
            var8 = var5._isnot(var1.getglobal("None"));
            var3 = null;
            if (var8.__nonzero__()) {
               var1.setline(913);
               var5 = var1.getlocal(2);
               var8 = var5._lt(var1.getlocal(0).__getattr__("_prefixlen"));
               var3 = null;
               if (var8.__nonzero__()) {
                  var1.setline(914);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("new prefix must be longer")));
               }

               var1.setline(915);
               var5 = var1.getlocal(1);
               var8 = var5._ne(Py.newInteger(1));
               var3 = null;
               if (var8.__nonzero__()) {
                  var1.setline(916);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot set prefixlen_diff and new_prefix")));
               }

               var1.setline(917);
               var5 = var1.getlocal(2)._sub(var1.getlocal(0).__getattr__("_prefixlen"));
               var1.setlocal(1, var5);
               var3 = null;
            }

            var1.setline(919);
            var5 = var1.getlocal(1);
            var8 = var5._lt(Py.newInteger(0));
            var3 = null;
            if (var8.__nonzero__()) {
               var1.setline(920);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prefix length diff must be > 0")));
            }

            var1.setline(921);
            var5 = var1.getlocal(0).__getattr__("_prefixlen")._add(var1.getlocal(1));
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(923);
            if (var1.getlocal(0).__getattr__("_is_valid_netmask").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3))).__not__().__nonzero__()) {
               var1.setline(924);
               var8 = var1.getglobal("ValueError");
               var10002 = PyString.fromInterned("prefix length diff %d is invalid for netblock %s");
               var7 = new PyObject[]{var1.getlocal(3), var1.getglobal("str").__call__(var2, var1.getlocal(0))};
               var10003 = new PyTuple(var7);
               Arrays.fill(var7, (Object)null);
               throw Py.makeException(var8.__call__(var2, var10002._mod(var10003)));
            }

            var1.setline(928);
            var8 = var1.getglobal("IPNetwork");
            var7 = new PyObject[2];
            var10002 = PyString.fromInterned("%s/%s");
            var6 = new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("network")), var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("_prefixlen")._add(var1.getlocal(1)))};
            var10003 = new PyTuple(var6);
            Arrays.fill(var6, (Object)null);
            var7[0] = var10002._mod(var10003);
            var7[1] = var1.getlocal(0).__getattr__("_version");
            var4 = new String[]{"version"};
            var8 = var8.__call__(var2, var7, var4);
            var3 = null;
            var5 = var8;
            var1.setlocal(4, var5);
            var3 = null;
            var1.setline(932);
            var1.setline(932);
            var8 = var1.getlocal(4);
            var1.f_lasti = 2;
            var3 = new Object[5];
            var1.f_savedlocals = var3;
            return var8;
         case 1:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
            var1.setline(910);
            var1.f_lasti = -1;
            return Py.None;
         case 2:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
            var1.setline(933);
            var5 = var1.getlocal(4);
            var1.setlocal(5, var5);
            var3 = null;
            break;
         case 3:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      var1.setline(934);
      if (!var1.getglobal("True").__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(935);
         var5 = var1.getlocal(5).__getattr__("broadcast");
         var1.setlocal(6, var5);
         var3 = null;
         var1.setline(936);
         var5 = var1.getlocal(6);
         var8 = var5._eq(var1.getlocal(0).__getattr__("broadcast"));
         var3 = null;
         if (var8.__nonzero__()) {
            var1.setline(937);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(938);
            var8 = var1.getglobal("IPAddress");
            var7 = new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(6))._add(Py.newInteger(1)), var1.getlocal(0).__getattr__("_version")};
            var4 = new String[]{"version"};
            var8 = var8.__call__(var2, var7, var4);
            var3 = null;
            var5 = var8;
            var1.setlocal(7, var5);
            var3 = null;
            var1.setline(939);
            var8 = var1.getglobal("IPNetwork");
            var7 = new PyObject[2];
            var10002 = PyString.fromInterned("%s/%s");
            var6 = new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(7)), var1.getglobal("str").__call__(var2, var1.getlocal(3))};
            var10003 = new PyTuple(var6);
            Arrays.fill(var6, (Object)null);
            var7[0] = var10002._mod(var10003);
            var7[1] = var1.getlocal(0).__getattr__("_version");
            var4 = new String[]{"version"};
            var8 = var8.__call__(var2, var7, var4);
            var3 = null;
            var5 = var8;
            var1.setlocal(5, var5);
            var3 = null;
            var1.setline(942);
            var1.setline(942);
            var8 = var1.getlocal(5);
            var1.f_lasti = 3;
            var3 = new Object[5];
            var1.f_savedlocals = var3;
            return var8;
         }
      }
   }

   public PyObject masked$67(PyFrame var1, ThreadState var2) {
      var1.setline(945);
      PyString.fromInterned("Return the network object with the host bits masked out.");
      var1.setline(946);
      PyObject var10000 = var1.getglobal("IPNetwork");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("%s/%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("network"), var1.getlocal(0).__getattr__("_prefixlen")})), var1.getlocal(0).__getattr__("_version")};
      String[] var4 = new String[]{"version"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject subnet$68(PyFrame var1, ThreadState var2) {
      var1.setline(950);
      PyString.fromInterned("Return a list of subnets, rather than an iterator.");
      var1.setline(951);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("iter_subnets").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject supernet$69(PyFrame var1, ThreadState var2) {
      var1.setline(973);
      PyString.fromInterned("The supernet containing the current network.\n\n        Args:\n            prefixlen_diff: An integer, the amount the prefix length of\n              the network should be decreased by.  For example, given a\n              /24 network and a prefixlen_diff of 3, a supernet with a\n              /21 netmask is returned.\n\n        Returns:\n            An IPv4 network object.\n\n        Raises:\n            ValueError: If self.prefixlen - prefixlen_diff < 0. I.e., you have a\n              negative prefix length.\n                OR\n            If prefixlen_diff and new_prefix are both set or new_prefix is a\n              larger number than the current prefix (larger number means a\n              smaller network)\n\n        ");
      var1.setline(974);
      PyObject var3 = var1.getlocal(0).__getattr__("_prefixlen");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(975);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(977);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(978);
            var4 = var1.getlocal(2);
            var10000 = var4._gt(var1.getlocal(0).__getattr__("_prefixlen"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(979);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("new prefix must be shorter")));
            }

            var1.setline(980);
            var4 = var1.getlocal(1);
            var10000 = var4._ne(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(981);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot set prefixlen_diff and new_prefix")));
            }

            var1.setline(982);
            var4 = var1.getlocal(0).__getattr__("_prefixlen")._sub(var1.getlocal(2));
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(985);
         var4 = var1.getlocal(0).__getattr__("prefixlen")._sub(var1.getlocal(1));
         var10000 = var4._lt(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(986);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("current prefixlen is %d, cannot have a prefixlen_diff of %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("prefixlen"), var1.getlocal(1)}))));
         } else {
            var1.setline(989);
            var10000 = var1.getglobal("IPNetwork");
            PyObject[] var6 = new PyObject[]{PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("network")), var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("prefixlen")._sub(var1.getlocal(1)))})), var1.getlocal(0).__getattr__("_version")};
            String[] var5 = new String[]{"version"};
            var10000 = var10000.__call__(var2, var6, var5);
            var4 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _BaseV4$70(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base IPv4 object.\n\n    The following methods are used by IPv4 objects in both single IP\n    addresses and networks.\n\n    "));
      var1.setline(1008);
      PyString.fromInterned("Base IPv4 object.\n\n    The following methods are used by IPv4 objects in both single IP\n    addresses and networks.\n\n    ");
      var1.setline(1011);
      PyObject var3 = Py.newInteger(2)._pow(var1.getname("IPV4LENGTH"))._sub(Py.newInteger(1));
      var1.setlocal("_ALL_ONES", var3);
      var3 = null;
      var1.setline(1012);
      var3 = var1.getname("frozenset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0123456789"));
      var1.setlocal("_DECIMAL_DIGITS", var3);
      var3 = null;
      var1.setline(1014);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$71, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1018);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _explode_shorthand_ip_string$72, (PyObject)null);
      var1.setlocal("_explode_shorthand_ip_string", var5);
      var3 = null;
      var1.setline(1023);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _ip_int_from_string$73, PyString.fromInterned("Turn the given IP string into an integer for comparison.\n\n        Args:\n            ip_str: A string, the IP ip_str.\n\n        Returns:\n            The IP ip_str as an integer.\n\n        Raises:\n            AddressValueError: if ip_str isn't a valid IPv4 Address.\n\n        "));
      var1.setlocal("_ip_int_from_string", var5);
      var3 = null;
      var1.setline(1048);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _parse_octet$74, PyString.fromInterned("Convert a decimal octet into an integer.\n\n        Args:\n            octet_str: A string, the number to parse.\n\n        Returns:\n            The octet as an integer.\n\n        Raises:\n            ValueError: if the octet isn't strictly a decimal from [0..255].\n\n        "));
      var1.setlocal("_parse_octet", var5);
      var3 = null;
      var1.setline(1071);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _string_from_ip_int$75, PyString.fromInterned("Turns a 32-bit integer into dotted decimal notation.\n\n        Args:\n            ip_int: An integer, the IP address.\n\n        Returns:\n            The IP address as a string in dotted decimal notation.\n\n        "));
      var1.setlocal("_string_from_ip_int", var5);
      var3 = null;
      var1.setline(1087);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, max_prefixlen$76, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("max_prefixlen", var3);
      var3 = null;
      var1.setline(1091);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, packed$77, PyString.fromInterned("The binary representation of this address."));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("packed", var3);
      var3 = null;
      var1.setline(1096);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, version$78, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("version", var3);
      var3 = null;
      var1.setline(1100);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_reserved$79, PyString.fromInterned("Test if the address is otherwise IETF reserved.\n\n        Returns:\n            A boolean, True if the address is within the\n            reserved IPv4 Network range.\n\n       "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("is_reserved", var3);
      var3 = null;
      var1.setline(1111);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_private$80, PyString.fromInterned("Test if this address is allocated for private networks.\n\n        Returns:\n            A boolean, True if the address is reserved per RFC 1918.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("is_private", var3);
      var3 = null;
      var1.setline(1123);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_multicast$81, PyString.fromInterned("Test if the address is reserved for multicast use.\n\n        Returns:\n            A boolean, True if the address is multicast.\n            See RFC 3171 for details.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("is_multicast", var3);
      var3 = null;
      var1.setline(1134);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_unspecified$82, PyString.fromInterned("Test if the address is unspecified.\n\n        Returns:\n            A boolean, True if this is the unspecified address as defined in\n            RFC 5735 3.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("is_unspecified", var3);
      var3 = null;
      var1.setline(1145);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_loopback$83, PyString.fromInterned("Test if the address is a loopback address.\n\n        Returns:\n            A boolean, True if the address is a loopback per RFC 3330.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("is_loopback", var3);
      var3 = null;
      var1.setline(1155);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, is_link_local$84, PyString.fromInterned("Test if the address is reserved for link-local.\n\n        Returns:\n            A boolean, True if the address is link-local per RFC 3927.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("is_link_local", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$71(PyFrame var1, ThreadState var2) {
      var1.setline(1015);
      PyInteger var3 = Py.newInteger(4);
      var1.getlocal(0).__setattr__((String)"_version", var3);
      var3 = null;
      var1.setline(1016);
      PyObject var4 = var1.getglobal("IPV4LENGTH");
      var1.getlocal(0).__setattr__("_max_prefixlen", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _explode_shorthand_ip_string$72(PyFrame var1, ThreadState var2) {
      var1.setline(1019);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1020);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1021);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ip_int_from_string$73(PyFrame var1, ThreadState var2) {
      var1.setline(1035);
      PyString.fromInterned("Turn the given IP string into an integer for comparison.\n\n        Args:\n            ip_str: A string, the IP ip_str.\n\n        Returns:\n            The IP ip_str as an integer.\n\n        Raises:\n            AddressValueError: if ip_str isn't a valid IPv4 Address.\n\n        ");
      var1.setline(1036);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1037);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._ne(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1038);
         throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(1040);
         PyInteger var7 = Py.newInteger(0);
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(1041);
         var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(1041);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1046);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(4, var4);

            PyException var5;
            try {
               var1.setline(1043);
               PyObject var8 = var1.getlocal(3)._lshift(Py.newInteger(8))._or(var1.getlocal(0).__getattr__("_parse_octet").__call__(var2, var1.getlocal(4)));
               var1.setlocal(3, var8);
               var5 = null;
            } catch (Throwable var6) {
               var5 = Py.setException(var6, var1);
               if (var5.match(var1.getglobal("ValueError"))) {
                  var1.setline(1045);
                  throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
               }

               throw var5;
            }
         }
      }
   }

   public PyObject _parse_octet$74(PyFrame var1, ThreadState var2) {
      var1.setline(1060);
      PyString.fromInterned("Convert a decimal octet into an integer.\n\n        Args:\n            octet_str: A string, the number to parse.\n\n        Returns:\n            The octet as an integer.\n\n        Raises:\n            ValueError: if the octet isn't strictly a decimal from [0..255].\n\n        ");
      var1.setline(1062);
      if (var1.getlocal(0).__getattr__("_DECIMAL_DIGITS").__getattr__("issuperset").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(1063);
         throw Py.makeException(var1.getglobal("ValueError"));
      } else {
         var1.setline(1064);
         PyObject var3 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(10));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1067);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._gt(Py.newInteger(255));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(PyString.fromInterned("0"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var3._gt(Py.newInteger(1));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(1068);
            throw Py.makeException(var1.getglobal("ValueError"));
         } else {
            var1.setline(1069);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _string_from_ip_int$75(PyFrame var1, ThreadState var2) {
      var1.setline(1080);
      PyString.fromInterned("Turns a 32-bit integer into dotted decimal notation.\n\n        Args:\n            ip_int: An integer, the IP address.\n\n        Returns:\n            The IP address as a string in dotted decimal notation.\n\n        ");
      var1.setline(1081);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1082);
      PyObject var6 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(4)).__iter__();

      while(true) {
         var1.setline(1082);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(1085);
            var6 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(1083);
         var1.getlocal(2).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(1)._and(Py.newInteger(255))));
         var1.setline(1084);
         PyObject var5 = var1.getlocal(1);
         var5 = var5._irshift(Py.newInteger(8));
         var1.setlocal(1, var5);
      }
   }

   public PyObject max_prefixlen$76(PyFrame var1, ThreadState var2) {
      var1.setline(1089);
      PyObject var3 = var1.getlocal(0).__getattr__("_max_prefixlen");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject packed$77(PyFrame var1, ThreadState var2) {
      var1.setline(1093);
      PyString.fromInterned("The binary representation of this address.");
      var1.setline(1094);
      PyObject var3 = var1.getglobal("v4_int_to_packed").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject version$78(PyFrame var1, ThreadState var2) {
      var1.setline(1098);
      PyObject var3 = var1.getlocal(0).__getattr__("_version");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_reserved$79(PyFrame var1, ThreadState var2) {
      var1.setline(1108);
      PyString.fromInterned("Test if the address is otherwise IETF reserved.\n\n        Returns:\n            A boolean, True if the address is within the\n            reserved IPv4 Network range.\n\n       ");
      var1.setline(1109);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv4Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("240.0.0.0/4")));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_private$80(PyFrame var1, ThreadState var2) {
      var1.setline(1118);
      PyString.fromInterned("Test if this address is allocated for private networks.\n\n        Returns:\n            A boolean, True if the address is reserved per RFC 1918.\n\n        ");
      var1.setline(1119);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv4Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("10.0.0.0/8")));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._in(var1.getglobal("IPv4Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("172.16.0.0/12")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._in(var1.getglobal("IPv4Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("192.168.0.0/16")));
            var3 = null;
         }
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_multicast$81(PyFrame var1, ThreadState var2) {
      var1.setline(1131);
      PyString.fromInterned("Test if the address is reserved for multicast use.\n\n        Returns:\n            A boolean, True if the address is multicast.\n            See RFC 3171 for details.\n\n        ");
      var1.setline(1132);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv4Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("224.0.0.0/4")));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_unspecified$82(PyFrame var1, ThreadState var2) {
      var1.setline(1142);
      PyString.fromInterned("Test if the address is unspecified.\n\n        Returns:\n            A boolean, True if this is the unspecified address as defined in\n            RFC 5735 3.\n\n        ");
      var1.setline(1143);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv4Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0.0.0.0")));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_loopback$83(PyFrame var1, ThreadState var2) {
      var1.setline(1152);
      PyString.fromInterned("Test if the address is a loopback address.\n\n        Returns:\n            A boolean, True if the address is a loopback per RFC 3330.\n\n        ");
      var1.setline(1153);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv4Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("127.0.0.0/8")));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_link_local$84(PyFrame var1, ThreadState var2) {
      var1.setline(1162);
      PyString.fromInterned("Test if the address is reserved for link-local.\n\n        Returns:\n            A boolean, True if the address is link-local per RFC 3927.\n\n        ");
      var1.setline(1163);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv4Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("169.254.0.0/16")));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IPv4Address$85(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Represent and manipulate single IPv4 Addresses."));
      var1.setline(1168);
      PyString.fromInterned("Represent and manipulate single IPv4 Addresses.");
      var1.setline(1170);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$86, PyString.fromInterned("\n        Args:\n            address: A string or integer representing the IP\n              '192.168.1.1'\n\n              Additionally, an integer can be passed, so\n              IPv4Address('192.168.1.1') == IPv4Address(3232235777).\n              or, more generally\n              IPv4Address(int(IPv4Address('192.168.1.1'))) ==\n                IPv4Address('192.168.1.1')\n\n        Raises:\n            AddressValueError: If ipaddr isn't a valid IPv4 address.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$86(PyFrame var1, ThreadState var2) {
      var1.setline(1186);
      PyString.fromInterned("\n        Args:\n            address: A string or integer representing the IP\n              '192.168.1.1'\n\n              Additionally, an integer can be passed, so\n              IPv4Address('192.168.1.1') == IPv4Address(3232235777).\n              or, more generally\n              IPv4Address(int(IPv4Address('192.168.1.1'))) ==\n                IPv4Address('192.168.1.1')\n\n        Raises:\n            AddressValueError: If ipaddr isn't a valid IPv4 address.\n\n        ");
      var1.setline(1187);
      var1.getglobal("_BaseIP").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1188);
      var1.getglobal("_BaseV4").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1191);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
         var1.setline(1192);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_ip", var3);
         var3 = null;
         var1.setline(1193);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._gt(var1.getlocal(0).__getattr__("_ALL_ONES"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1194);
            throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(1195);
            var1.f_lasti = -1;
            return Py.None;
         }
      } else {
         var1.setline(1198);
         if (var1.getglobal("_compat_has_real_bytes").__nonzero__()) {
            var1.setline(1199);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("bytes"));
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var3._eq(Py.newInteger(4));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1200);
               var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("!I"), (PyObject)var1.getlocal(1)).__getitem__(Py.newInteger(0));
               var1.getlocal(0).__setattr__("_ip", var3);
               var3 = null;
               var1.setline(1201);
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setline(1205);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1206);
         var3 = var1.getlocal(0).__getattr__("_ip_int_from_string").__call__(var2, var1.getlocal(2));
         var1.getlocal(0).__setattr__("_ip", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject IPv4Network$87(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class represents and manipulates 32-bit IPv4 networks.\n\n    Attributes: [examples for IPv4Network('1.2.3.4/27')]\n        ._ip: 16909060\n        .ip: IPv4Address('1.2.3.4')\n        .network: IPv4Address('1.2.3.0')\n        .hostmask: IPv4Address('0.0.0.31')\n        .broadcast: IPv4Address('1.2.3.31')\n        .netmask: IPv4Address('255.255.255.224')\n        .prefixlen: 27\n\n    "));
      var1.setline(1222);
      PyString.fromInterned("This class represents and manipulates 32-bit IPv4 networks.\n\n    Attributes: [examples for IPv4Network('1.2.3.4/27')]\n        ._ip: 16909060\n        .ip: IPv4Address('1.2.3.4')\n        .network: IPv4Address('1.2.3.0')\n        .hostmask: IPv4Address('0.0.0.31')\n        .broadcast: IPv4Address('1.2.3.31')\n        .netmask: IPv4Address('255.255.255.224')\n        .prefixlen: 27\n\n    ");
      var1.setline(1225);
      PyObject var3 = var1.getname("set").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(255), Py.newInteger(254), Py.newInteger(252), Py.newInteger(248), Py.newInteger(240), Py.newInteger(224), Py.newInteger(192), Py.newInteger(128), Py.newInteger(0)})));
      var1.setlocal("_valid_mask_octets", var3);
      var3 = null;
      var1.setline(1227);
      PyObject[] var4 = new PyObject[]{var1.getname("False")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$88, PyString.fromInterned("Instantiate a new IPv4 network object.\n\n        Args:\n            address: A string or integer representing the IP [& network].\n              '192.168.1.1/24'\n              '192.168.1.1/255.255.255.0'\n              '192.168.1.1/0.0.0.255'\n              are all functionally the same in IPv4. Similarly,\n              '192.168.1.1'\n              '192.168.1.1/255.255.255.255'\n              '192.168.1.1/32'\n              are also functionaly equivalent. That is to say, failing to\n              provide a subnetmask will create an object with a mask of /32.\n\n              If the mask (portion after the / in the argument) is given in\n              dotted quad form, it is treated as a netmask if it starts with a\n              non-zero field (e.g. /255.0.0.0 == /8) and as a hostmask if it\n              starts with a zero field (e.g. 0.255.255.255 == /8), with the\n              single exception of an all-zero mask which is treated as a\n              netmask == /0. If no mask is given, a default of /32 is used.\n\n              Additionally, an integer can be passed, so\n              IPv4Network('192.168.1.1') == IPv4Network(3232235777).\n              or, more generally\n              IPv4Network(int(IPv4Network('192.168.1.1'))) ==\n                IPv4Network('192.168.1.1')\n\n            strict: A boolean. If true, ensure that we have been passed\n              A true network address, eg, 192.168.1.0/24 and not an\n              IP address on a network, eg, 192.168.1.1/24.\n\n        Raises:\n            AddressValueError: If ipaddr isn't a valid IPv4 address.\n            NetmaskValueError: If the netmask isn't valid for\n              an IPv4 address.\n            ValueError: If strict was True and a network address was not\n              supplied.\n\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1330);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _is_hostmask$89, PyString.fromInterned("Test if the IP string is a hostmask (rather than a netmask).\n\n        Args:\n            ip_str: A string, the potential hostmask.\n\n        Returns:\n            A boolean, True if the IP string is a hostmask.\n\n        "));
      var1.setlocal("_is_hostmask", var5);
      var3 = null;
      var1.setline(1351);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _is_valid_netmask$90, PyString.fromInterned("Verify that the netmask is valid.\n\n        Args:\n            netmask: A string, either a prefix or dotted decimal\n              netmask.\n\n        Returns:\n            A boolean, True if the prefix represents a valid IPv4\n            netmask.\n\n        "));
      var1.setlocal("_is_valid_netmask", var5);
      var3 = null;
      var1.setline(1378);
      var1.setline(1378);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, f$91);
      var1.setlocal("IsRFC1918", var5);
      var3 = null;
      var1.setline(1379);
      var1.setline(1379);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, f$92);
      var1.setlocal("IsMulticast", var5);
      var3 = null;
      var1.setline(1380);
      var1.setline(1380);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, f$93);
      var1.setlocal("IsLoopback", var5);
      var3 = null;
      var1.setline(1381);
      var1.setline(1381);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, f$94);
      var1.setlocal("IsLinkLocal", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$88(PyFrame var1, ThreadState var2) {
      var1.setline(1266);
      PyString.fromInterned("Instantiate a new IPv4 network object.\n\n        Args:\n            address: A string or integer representing the IP [& network].\n              '192.168.1.1/24'\n              '192.168.1.1/255.255.255.0'\n              '192.168.1.1/0.0.0.255'\n              are all functionally the same in IPv4. Similarly,\n              '192.168.1.1'\n              '192.168.1.1/255.255.255.255'\n              '192.168.1.1/32'\n              are also functionaly equivalent. That is to say, failing to\n              provide a subnetmask will create an object with a mask of /32.\n\n              If the mask (portion after the / in the argument) is given in\n              dotted quad form, it is treated as a netmask if it starts with a\n              non-zero field (e.g. /255.0.0.0 == /8) and as a hostmask if it\n              starts with a zero field (e.g. 0.255.255.255 == /8), with the\n              single exception of an all-zero mask which is treated as a\n              netmask == /0. If no mask is given, a default of /32 is used.\n\n              Additionally, an integer can be passed, so\n              IPv4Network('192.168.1.1') == IPv4Network(3232235777).\n              or, more generally\n              IPv4Network(int(IPv4Network('192.168.1.1'))) ==\n                IPv4Network('192.168.1.1')\n\n            strict: A boolean. If true, ensure that we have been passed\n              A true network address, eg, 192.168.1.0/24 and not an\n              IP address on a network, eg, 192.168.1.1/24.\n\n        Raises:\n            AddressValueError: If ipaddr isn't a valid IPv4 address.\n            NetmaskValueError: If the netmask isn't valid for\n              an IPv4 address.\n            ValueError: If strict was True and a network address was not\n              supplied.\n\n        ");
      var1.setline(1267);
      var1.getglobal("_BaseNet").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1268);
      var1.getglobal("_BaseV4").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1271);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
         var1.setline(1272);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_ip", var3);
         var3 = null;
         var1.setline(1273);
         var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
         var1.getlocal(0).__setattr__("ip", var3);
         var3 = null;
         var1.setline(1274);
         var3 = var1.getlocal(0).__getattr__("_max_prefixlen");
         var1.getlocal(0).__setattr__("_prefixlen", var3);
         var3 = null;
         var1.setline(1275);
         var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ALL_ONES"));
         var1.getlocal(0).__setattr__("netmask", var3);
         var3 = null;
         var1.setline(1276);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._gt(var1.getlocal(0).__getattr__("_ALL_ONES"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1277);
            throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(1278);
            var1.f_lasti = -1;
            return Py.None;
         }
      } else {
         var1.setline(1281);
         if (var1.getglobal("_compat_has_real_bytes").__nonzero__()) {
            var1.setline(1282);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("bytes"));
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var3._eq(Py.newInteger(4));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1283);
               var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("!I"), (PyObject)var1.getlocal(1)).__getitem__(Py.newInteger(0));
               var1.getlocal(0).__setattr__("_ip", var3);
               var3 = null;
               var1.setline(1284);
               var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
               var1.getlocal(0).__setattr__("ip", var3);
               var3 = null;
               var1.setline(1285);
               var3 = var1.getlocal(0).__getattr__("_max_prefixlen");
               var1.getlocal(0).__setattr__("_prefixlen", var3);
               var3 = null;
               var1.setline(1286);
               var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ALL_ONES"));
               var1.getlocal(0).__setattr__("netmask", var3);
               var3 = null;
               var1.setline(1287);
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setline(1291);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1293);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var3._gt(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1294);
            throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(1296);
            var3 = var1.getlocal(0).__getattr__("_ip_int_from_string").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)));
            var1.getlocal(0).__setattr__("_ip", var3);
            var3 = null;
            var1.setline(1297);
            var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
            var1.getlocal(0).__setattr__("ip", var3);
            var3 = null;
            var1.setline(1299);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1300);
               var3 = var1.getlocal(3).__getitem__(Py.newInteger(1)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(1301);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               var10000 = var3._eq(Py.newInteger(4));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1303);
                  if (var1.getlocal(0).__getattr__("_is_valid_netmask").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1))).__nonzero__()) {
                     var1.setline(1304);
                     var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip_int_from_string").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1))));
                     var1.getlocal(0).__setattr__("netmask", var3);
                     var3 = null;
                  } else {
                     var1.setline(1306);
                     if (!var1.getlocal(0).__getattr__("_is_hostmask").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1))).__nonzero__()) {
                        var1.setline(1310);
                        throw Py.makeException(var1.getglobal("NetmaskValueError").__call__(var2, PyString.fromInterned("%s is not a valid netmask")._mod(var1.getlocal(3).__getitem__(Py.newInteger(1)))));
                     }

                     var1.setline(1307);
                     var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip_int_from_string").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1)))._xor(var1.getlocal(0).__getattr__("_ALL_ONES")));
                     var1.getlocal(0).__setattr__("netmask", var3);
                     var3 = null;
                  }

                  var1.setline(1313);
                  var3 = var1.getlocal(0).__getattr__("_prefix_from_ip_int").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("netmask")));
                  var1.getlocal(0).__setattr__("_prefixlen", var3);
                  var3 = null;
               } else {
                  var1.setline(1316);
                  if (var1.getlocal(0).__getattr__("_is_valid_netmask").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1))).__not__().__nonzero__()) {
                     var1.setline(1317);
                     throw Py.makeException(var1.getglobal("NetmaskValueError").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1))));
                  }

                  var1.setline(1318);
                  var3 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(1)));
                  var1.getlocal(0).__setattr__("_prefixlen", var3);
                  var3 = null;
                  var1.setline(1319);
                  var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip_int_from_prefix").__call__(var2, var1.getlocal(0).__getattr__("_prefixlen")));
                  var1.getlocal(0).__setattr__("netmask", var3);
                  var3 = null;
               }
            } else {
               var1.setline(1322);
               var3 = var1.getlocal(0).__getattr__("_max_prefixlen");
               var1.getlocal(0).__setattr__("_prefixlen", var3);
               var3 = null;
               var1.setline(1323);
               var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip_int_from_prefix").__call__(var2, var1.getlocal(0).__getattr__("_prefixlen")));
               var1.getlocal(0).__setattr__("netmask", var3);
               var3 = null;
            }

            var1.setline(1325);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1326);
               var3 = var1.getlocal(0).__getattr__("ip");
               var10000 = var3._ne(var1.getlocal(0).__getattr__("network"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1327);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%s has host bits set")._mod(var1.getlocal(0).__getattr__("ip"))));
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _is_hostmask$89(PyFrame var1, ThreadState var2) {
      var1.setline(1339);
      PyString.fromInterned("Test if the IP string is a hostmask (rather than a netmask).\n\n        Args:\n            ip_str: A string, the potential hostmask.\n\n        Returns:\n            A boolean, True if the IP string is a hostmask.\n\n        ");
      var1.setline(1340);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(2, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(1342);
         PyList var10000 = new PyList();
         var3 = var10000.__getattr__("append");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1342);
         var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(1342);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1342);
               var1.dellocal(4);
               PyList var8 = var10000;
               var1.setlocal(3, var8);
               var3 = null;
               break;
            }

            var1.setlocal(5, var4);
            var1.setline(1342);
            PyObject var5 = var1.getglobal("int").__call__(var2, var1.getlocal(5));
            PyObject var10001 = var5._in(var1.getlocal(0).__getattr__("_valid_mask_octets"));
            var5 = null;
            if (var10001.__nonzero__()) {
               var1.setline(1342);
               var1.getlocal(4).__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(5)));
            }
         }
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (var7.match(var1.getglobal("ValueError"))) {
            var1.setline(1344);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var7;
      }

      var1.setline(1345);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var9 = var3._ne(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
      var3 = null;
      if (var9.__nonzero__()) {
         var1.setline(1346);
         var4 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1347);
         var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         var9 = var3._lt(var1.getlocal(3).__getitem__(Py.newInteger(-1)));
         var3 = null;
         if (var9.__nonzero__()) {
            var1.setline(1348);
            var4 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(1349);
            var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }
      }
   }

   public PyObject _is_valid_netmask$90(PyFrame var1, ThreadState var2) {
      var1.setline(1362);
      PyString.fromInterned("Verify that the netmask is valid.\n\n        Args:\n            netmask: A string, either a prefix or dotted decimal\n              netmask.\n\n        Returns:\n            A boolean, True if the prefix represents a valid IPv4\n            netmask.\n\n        ");
      var1.setline(1363);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1364);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(Py.newInteger(4));
      var3 = null;
      PyException var4;
      PyObject var5;
      PyObject var9;
      PyObject var10001;
      if (var10000.__nonzero__()) {
         var1.setline(1365);
         PyList var13 = new PyList();
         var3 = var13.__getattr__("append");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1365);
         var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(1365);
            var9 = var3.__iternext__();
            if (var9 == null) {
               var1.setline(1365);
               var1.dellocal(3);
               if (var13.__nonzero__()) {
                  var1.setline(1366);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(1367);
               var13 = new PyList();
               var9 = var13.__getattr__("append");
               var1.setlocal(5, var9);
               var4 = null;
               var1.setline(1367);
               var9 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(2)).__iter__();

               while(true) {
                  var1.setline(1367);
                  var5 = var9.__iternext__();
                  if (var5 == null) {
                     var1.setline(1367);
                     var1.dellocal(5);
                     if (var13.__nonzero__()) {
                        var1.setline(1369);
                        var3 = var1.getglobal("False");
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setline(1370);
                     var3 = var1.getglobal("True");
                     var1.f_lasti = -1;
                     return var3;
                  }

                  PyObject[] var6 = Py.unpackSequence(var5, 2);
                  PyObject var7 = var6[0];
                  var1.setlocal(7, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(6, var7);
                  var7 = null;
                  var1.setline(1367);
                  PyObject var11 = var1.getlocal(7);
                  var10001 = var11._gt(Py.newInteger(0));
                  var6 = null;
                  if (var10001.__nonzero__()) {
                     var11 = var1.getlocal(6);
                     var10001 = var11._gt(var1.getlocal(2).__getitem__(var1.getlocal(7)._sub(Py.newInteger(1))));
                     var6 = null;
                  }

                  if (var10001.__nonzero__()) {
                     var1.setline(1367);
                     var1.getlocal(5).__call__(var2, var1.getlocal(6));
                  }
               }
            }

            var1.setlocal(4, var9);
            var1.setline(1365);
            var5 = var1.getglobal("int").__call__(var2, var1.getlocal(4));
            var10001 = var5._notin(var1.getlocal(0).__getattr__("_valid_mask_octets"));
            var5 = null;
            if (var10001.__nonzero__()) {
               var1.setline(1365);
               var1.getlocal(3).__call__(var2, var1.getlocal(4));
            }
         }
      } else {
         try {
            var1.setline(1372);
            var9 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var9);
            var4 = null;
         } catch (Throwable var8) {
            var4 = Py.setException(var8, var1);
            if (var4.match(var1.getglobal("ValueError"))) {
               var1.setline(1374);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }

            throw var4;
         }

         var1.setline(1375);
         PyInteger var10 = Py.newInteger(0);
         var10001 = var1.getlocal(1);
         PyInteger var12 = var10;
         var9 = var10001;
         if ((var5 = var12._le(var10001)).__nonzero__()) {
            var5 = var9._le(var1.getlocal(0).__getattr__("_max_prefixlen"));
         }

         var4 = null;
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject f$91(PyFrame var1, ThreadState var2) {
      var1.setline(1378);
      PyObject var3 = var1.getlocal(0).__getattr__("is_private");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$92(PyFrame var1, ThreadState var2) {
      var1.setline(1379);
      PyObject var3 = var1.getlocal(0).__getattr__("is_multicast");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$93(PyFrame var1, ThreadState var2) {
      var1.setline(1380);
      PyObject var3 = var1.getlocal(0).__getattr__("is_loopback");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$94(PyFrame var1, ThreadState var2) {
      var1.setline(1381);
      PyObject var3 = var1.getlocal(0).__getattr__("is_link_local");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _BaseV6$95(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base IPv6 object.\n\n    The following methods are used by IPv6 objects in both single IP\n    addresses and networks.\n\n    "));
      var1.setline(1391);
      PyString.fromInterned("Base IPv6 object.\n\n    The following methods are used by IPv6 objects in both single IP\n    addresses and networks.\n\n    ");
      var1.setline(1393);
      PyObject var3 = Py.newInteger(2)._pow(var1.getname("IPV6LENGTH"))._sub(Py.newInteger(1));
      var1.setlocal("_ALL_ONES", var3);
      var3 = null;
      var1.setline(1394);
      PyInteger var4 = Py.newInteger(8);
      var1.setlocal("_HEXTET_COUNT", var4);
      var3 = null;
      var1.setline(1395);
      var3 = var1.getname("frozenset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0123456789ABCDEFabcdef"));
      var1.setlocal("_HEX_DIGITS", var3);
      var3 = null;
      var1.setline(1397);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$96, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(1401);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _ip_int_from_string$97, PyString.fromInterned("Turn an IPv6 ip_str into an integer.\n\n        Args:\n            ip_str: A string, the IPv6 ip_str.\n\n        Returns:\n            A long, the IPv6 ip_str.\n\n        Raises:\n            AddressValueError: if ip_str isn't a valid IPv6 Address.\n\n        "));
      var1.setlocal("_ip_int_from_string", var6);
      var3 = null;
      var1.setline(1480);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _parse_hextet$98, PyString.fromInterned("Convert an IPv6 hextet string into an integer.\n\n        Args:\n            hextet_str: A string, the number to parse.\n\n        Returns:\n            The hextet as an integer.\n\n        Raises:\n            ValueError: if the input isn't strictly a hex number from [0..FFFF].\n\n        "));
      var1.setlocal("_parse_hextet", var6);
      var3 = null;
      var1.setline(1501);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _compress_hextets$99, PyString.fromInterned("Compresses a list of hextets.\n\n        Compresses a list of strings, replacing the longest continuous\n        sequence of \"0\" in the list with \"\" and adding empty strings at\n        the beginning or at the end of the string such that subsequently\n        calling \":\".join(hextets) will produce the compressed version of\n        the IPv6 address.\n\n        Args:\n            hextets: A list of strings, the hextets to compress.\n\n        Returns:\n            A list of strings.\n\n        "));
      var1.setlocal("_compress_hextets", var6);
      var3 = null;
      var1.setline(1548);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, _string_from_ip_int$100, PyString.fromInterned("Turns a 128-bit integer into hexadecimal notation.\n\n        Args:\n            ip_int: An integer, the IP address.\n\n        Returns:\n            A string, the hexadecimal representation of the address.\n\n        Raises:\n            ValueError: The address is bigger than 128 bits of all ones.\n\n        "));
      var1.setlocal("_string_from_ip_int", var6);
      var3 = null;
      var1.setline(1575);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, _explode_shorthand_ip_string$101, PyString.fromInterned("Expand a shortened IPv6 address.\n\n        Args:\n            ip_str: A string, the IPv6 address.\n\n        Returns:\n            A string, the expanded IPv6 address.\n\n        "));
      var1.setlocal("_explode_shorthand_ip_string", var6);
      var3 = null;
      var1.setline(1598);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, max_prefixlen$102, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("max_prefixlen", var3);
      var3 = null;
      var1.setline(1602);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, packed$103, PyString.fromInterned("The binary representation of this address."));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("packed", var3);
      var3 = null;
      var1.setline(1607);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, version$104, (PyObject)null);
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("version", var3);
      var3 = null;
      var1.setline(1611);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_multicast$105, PyString.fromInterned("Test if the address is reserved for multicast use.\n\n        Returns:\n            A boolean, True if the address is a multicast address.\n            See RFC 2373 2.7 for details.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("is_multicast", var3);
      var3 = null;
      var1.setline(1622);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_reserved$106, PyString.fromInterned("Test if the address is otherwise IETF reserved.\n\n        Returns:\n            A boolean, True if the address is within one of the\n            reserved IPv6 Network ranges.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("is_reserved", var3);
      var3 = null;
      var1.setline(1647);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_unspecified$107, PyString.fromInterned("Test if the address is unspecified.\n\n        Returns:\n            A boolean, True if this is the unspecified address as defined in\n            RFC 2373 2.5.2.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("is_unspecified", var3);
      var3 = null;
      var1.setline(1658);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_loopback$108, PyString.fromInterned("Test if the address is a loopback address.\n\n        Returns:\n            A boolean, True if the address is a loopback address as defined in\n            RFC 2373 2.5.3.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("is_loopback", var3);
      var3 = null;
      var1.setline(1669);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_link_local$109, PyString.fromInterned("Test if the address is reserved for link-local.\n\n        Returns:\n            A boolean, True if the address is reserved per RFC 4291.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("is_link_local", var3);
      var3 = null;
      var1.setline(1679);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_site_local$110, PyString.fromInterned("Test if the address is reserved for site-local.\n\n        Note that the site-local address space has been deprecated by RFC 3879.\n        Use is_private to test if this address is in the space of unique local\n        addresses as defined by RFC 4193.\n\n        Returns:\n            A boolean, True if the address is reserved per RFC 3513 2.5.6.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("is_site_local", var3);
      var3 = null;
      var1.setline(1693);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_private$111, PyString.fromInterned("Test if this address is allocated for private networks.\n\n        Returns:\n            A boolean, True if the address is reserved per RFC 4193.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("is_private", var3);
      var3 = null;
      var1.setline(1703);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, ipv4_mapped$112, PyString.fromInterned("Return the IPv4 mapped address.\n\n        Returns:\n            If the IPv6 address is a v4 mapped address, return the\n            IPv4 mapped address. Return None otherwise.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("ipv4_mapped", var3);
      var3 = null;
      var1.setline(1716);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, teredo$113, PyString.fromInterned("Tuple of embedded teredo IPs.\n\n        Returns:\n            Tuple of the (server, client) IPs or None if the address\n            doesn't appear to be a teredo address (doesn't start with\n            2001::/32)\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("teredo", var3);
      var3 = null;
      var1.setline(1731);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, sixtofour$114, PyString.fromInterned("Return the IPv4 6to4 embedded address.\n\n        Returns:\n            The IPv4 6to4-embedded address if present or None if the\n            address doesn't appear to contain a 6to4 embedded address.\n\n        "));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("sixtofour", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$96(PyFrame var1, ThreadState var2) {
      var1.setline(1398);
      PyInteger var3 = Py.newInteger(6);
      var1.getlocal(0).__setattr__((String)"_version", var3);
      var3 = null;
      var1.setline(1399);
      PyObject var4 = var1.getglobal("IPV6LENGTH");
      var1.getlocal(0).__setattr__("_max_prefixlen", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _ip_int_from_string$97(PyFrame var1, ThreadState var2) {
      var1.setline(1413);
      PyString.fromInterned("Turn an IPv6 ip_str into an integer.\n\n        Args:\n            ip_str: A string, the IPv6 ip_str.\n\n        Returns:\n            A long, the IPv6 ip_str.\n\n        Raises:\n            AddressValueError: if ip_str isn't a valid IPv6 Address.\n\n        ");
      var1.setline(1414);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1417);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._lt(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1418);
         throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(1421);
         PyString var8 = PyString.fromInterned(".");
         var10000 = var8._in(var1.getlocal(2).__getitem__(Py.newInteger(-1)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1422);
            var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(2).__getattr__("pop").__call__(var2)).__getattr__("_ip");
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(1423);
            var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("%x")._mod(var1.getlocal(3)._rshift(Py.newInteger(16))._and(Py.newInteger(65535))));
            var1.setline(1424);
            var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("%x")._mod(var1.getlocal(3)._and(Py.newInteger(65535))));
         }

         var1.setline(1427);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._gt(var1.getlocal(0).__getattr__("_HEXTET_COUNT")._add(Py.newInteger(1)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1428);
            throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
         } else {
            PyObject var4;
            PyObject var5;
            try {
               var1.setline(1433);
               PyList var15 = new PyList();
               var3 = var15.__getattr__("append");
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(1434);
               var3 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2))._sub(Py.newInteger(1))).__iter__();

               while(true) {
                  var1.setline(1434);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(1434);
                     var1.dellocal(5);
                     if (!var15.__nonzero__()) {
                        var15 = new PyList(new PyObject[]{var1.getglobal("None")});
                     }

                     PyList var12 = var15;
                     PyObject[] var9 = Py.unpackSequence(var12, 1);
                     var5 = var9[0];
                     var1.setlocal(4, var5);
                     var5 = null;
                     var3 = null;
                     break;
                  }

                  var1.setlocal(6, var4);
                  var1.setline(1434);
                  if (var1.getlocal(2).__getitem__(var1.getlocal(6)).__not__().__nonzero__()) {
                     var1.setline(1434);
                     var1.getlocal(5).__call__(var2, var1.getlocal(6));
                  }
               }
            } catch (Throwable var7) {
               PyException var11 = Py.setException(var7, var1);
               if (var11.match(var1.getglobal("ValueError"))) {
                  var1.setline(1438);
                  throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
               }

               throw var11;
            }

            var1.setline(1442);
            var3 = var1.getlocal(4);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1444);
               var3 = var1.getlocal(4);
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(1445);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2))._sub(var1.getlocal(4))._sub(Py.newInteger(1));
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(1446);
               if (var1.getlocal(2).__getitem__(Py.newInteger(0)).__not__().__nonzero__()) {
                  var1.setline(1447);
                  var3 = var1.getlocal(7);
                  var3 = var3._isub(Py.newInteger(1));
                  var1.setlocal(7, var3);
                  var1.setline(1448);
                  if (var1.getlocal(7).__nonzero__()) {
                     var1.setline(1449);
                     throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
                  }
               }

               var1.setline(1450);
               if (var1.getlocal(2).__getitem__(Py.newInteger(-1)).__not__().__nonzero__()) {
                  var1.setline(1451);
                  var3 = var1.getlocal(8);
                  var3 = var3._isub(Py.newInteger(1));
                  var1.setlocal(8, var3);
                  var1.setline(1452);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(1453);
                     throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
                  }
               }

               var1.setline(1454);
               var3 = var1.getlocal(0).__getattr__("_HEXTET_COUNT")._sub(var1.getlocal(7)._add(var1.getlocal(8)));
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(1455);
               var3 = var1.getlocal(9);
               var10000 = var3._lt(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1456);
                  throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
               }
            } else {
               var1.setline(1460);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var3._ne(var1.getlocal(0).__getattr__("_HEXTET_COUNT"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1461);
                  throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
               }

               var1.setline(1462);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(1463);
               PyInteger var13 = Py.newInteger(0);
               var1.setlocal(8, var13);
               var3 = null;
               var1.setline(1464);
               var13 = Py.newInteger(0);
               var1.setlocal(9, var13);
               var3 = null;
            }

            try {
               var1.setline(1468);
               PyLong var14 = Py.newLong("0");
               var1.setlocal(10, var14);
               var3 = null;
               var1.setline(1469);
               var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(7)).__iter__();

               while(true) {
                  var1.setline(1469);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(1472);
                     var3 = var1.getlocal(10);
                     var3 = var3._ilshift(Py.newInteger(16)._mul(var1.getlocal(9)));
                     var1.setlocal(10, var3);
                     var1.setline(1473);
                     var3 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__neg__(), (PyObject)Py.newInteger(0)).__iter__();

                     while(true) {
                        var1.setline(1473);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           var1.setline(1476);
                           var3 = var1.getlocal(10);
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setlocal(6, var4);
                        var1.setline(1474);
                        var5 = var1.getlocal(10);
                        var5 = var5._ilshift(Py.newInteger(16));
                        var1.setlocal(10, var5);
                        var1.setline(1475);
                        var5 = var1.getlocal(10);
                        var5 = var5._ior(var1.getlocal(0).__getattr__("_parse_hextet").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(6))));
                        var1.setlocal(10, var5);
                     }
                  }

                  var1.setlocal(6, var4);
                  var1.setline(1470);
                  var5 = var1.getlocal(10);
                  var5 = var5._ilshift(Py.newInteger(16));
                  var1.setlocal(10, var5);
                  var1.setline(1471);
                  var5 = var1.getlocal(10);
                  var5 = var5._ior(var1.getlocal(0).__getattr__("_parse_hextet").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(6))));
                  var1.setlocal(10, var5);
               }
            } catch (Throwable var6) {
               PyException var10 = Py.setException(var6, var1);
               if (var10.match(var1.getglobal("ValueError"))) {
                  var1.setline(1478);
                  throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
               } else {
                  throw var10;
               }
            }
         }
      }
   }

   public PyObject _parse_hextet$98(PyFrame var1, ThreadState var2) {
      var1.setline(1492);
      PyString.fromInterned("Convert an IPv6 hextet string into an integer.\n\n        Args:\n            hextet_str: A string, the number to parse.\n\n        Returns:\n            The hextet as an integer.\n\n        Raises:\n            ValueError: if the input isn't strictly a hex number from [0..FFFF].\n\n        ");
      var1.setline(1494);
      if (var1.getlocal(0).__getattr__("_HEX_DIGITS").__getattr__("issuperset").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(1495);
         throw Py.makeException(var1.getglobal("ValueError"));
      } else {
         var1.setline(1496);
         PyObject var3 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(16));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1497);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._gt(Py.newInteger(65535));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1498);
            throw Py.makeException(var1.getglobal("ValueError"));
         } else {
            var1.setline(1499);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _compress_hextets$99(PyFrame var1, ThreadState var2) {
      var1.setline(1516);
      PyString.fromInterned("Compresses a list of hextets.\n\n        Compresses a list of strings, replacing the longest continuous\n        sequence of \"0\" in the list with \"\" and adding empty strings at\n        the beginning or at the end of the string such that subsequently\n        calling \":\".join(hextets) will produce the compressed version of\n        the IPv6 address.\n\n        Args:\n            hextets: A list of strings, the hextets to compress.\n\n        Returns:\n            A list of strings.\n\n        ");
      var1.setline(1517);
      PyInteger var3 = Py.newInteger(-1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1518);
      var3 = Py.newInteger(0);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1519);
      var3 = Py.newInteger(-1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1520);
      var3 = Py.newInteger(0);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1521);
      PyObject var6 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))).__iter__();

      while(true) {
         var1.setline(1521);
         PyObject var4 = var6.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(1535);
            var6 = var1.getlocal(3);
            var10000 = var6._gt(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1536);
               var6 = var1.getlocal(2)._add(var1.getlocal(3));
               var1.setlocal(7, var6);
               var3 = null;
               var1.setline(1539);
               var6 = var1.getlocal(7);
               var10000 = var6._eq(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1540);
                  var6 = var1.getlocal(1);
                  var6 = var6._iadd(new PyList(new PyObject[]{PyString.fromInterned("")}));
                  var1.setlocal(1, var6);
               }

               var1.setline(1541);
               PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("")});
               var1.getlocal(1).__setslice__(var1.getlocal(2), var1.getlocal(7), (PyObject)null, var7);
               var3 = null;
               var1.setline(1543);
               var6 = var1.getlocal(2);
               var10000 = var6._eq(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1544);
                  var6 = (new PyList(new PyObject[]{PyString.fromInterned("")}))._add(var1.getlocal(1));
                  var1.setlocal(1, var6);
                  var3 = null;
               }
            }

            var1.setline(1546);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(6, var4);
         var1.setline(1522);
         PyObject var5 = var1.getlocal(1).__getitem__(var1.getlocal(6));
         var10000 = var5._eq(PyString.fromInterned("0"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1523);
            var5 = var1.getlocal(5);
            var5 = var5._iadd(Py.newInteger(1));
            var1.setlocal(5, var5);
            var1.setline(1524);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(Py.newInteger(-1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1526);
               var5 = var1.getlocal(6);
               var1.setlocal(4, var5);
               var5 = null;
            }

            var1.setline(1527);
            var5 = var1.getlocal(5);
            var10000 = var5._gt(var1.getlocal(3));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1529);
               var5 = var1.getlocal(5);
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(1530);
               var5 = var1.getlocal(4);
               var1.setlocal(2, var5);
               var5 = null;
            }
         } else {
            var1.setline(1532);
            PyInteger var8 = Py.newInteger(0);
            var1.setlocal(5, var8);
            var5 = null;
            var1.setline(1533);
            var8 = Py.newInteger(-1);
            var1.setlocal(4, var8);
            var5 = null;
         }
      }
   }

   public PyObject _string_from_ip_int$100(PyFrame var1, ThreadState var2) {
      var1.setline(1560);
      PyString.fromInterned("Turns a 128-bit integer into hexadecimal notation.\n\n        Args:\n            ip_int: An integer, the IP address.\n\n        Returns:\n            A string, the hexadecimal representation of the address.\n\n        Raises:\n            ValueError: The address is bigger than 128 bits of all ones.\n\n        ");
      var1.setline(1561);
      PyObject var10000 = var1.getlocal(1).__not__();
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1562);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1564);
      var3 = var1.getlocal(1);
      var10000 = var3._gt(var1.getlocal(0).__getattr__("_ALL_ONES"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1565);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IPv6 address is too large")));
      } else {
         var1.setline(1567);
         var3 = PyString.fromInterned("%032x")._mod(var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1568);
         PyList var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(1569);
         var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)Py.newInteger(32), (PyObject)Py.newInteger(4)).__iter__();

         while(true) {
            var1.setline(1569);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1572);
               var3 = var1.getlocal(0).__getattr__("_compress_hextets").__call__(var2, var1.getlocal(3));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(1573);
               var3 = PyString.fromInterned(":").__getattr__("join").__call__(var2, var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(4, var4);
            var1.setline(1570);
            var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("%x")._mod(var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(4)), (PyObject)null), (PyObject)Py.newInteger(16))));
         }
      }
   }

   public PyObject _explode_shorthand_ip_string$101(PyFrame var1, ThreadState var2) {
      var1.setline(1584);
      PyString.fromInterned("Expand a shortened IPv6 address.\n\n        Args:\n            ip_str: A string, the IPv6 address.\n\n        Returns:\n            A string, the expanded IPv6 address.\n\n        ");
      var1.setline(1585);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1586);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1587);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_BaseNet")).__nonzero__()) {
            var1.setline(1588);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("ip"));
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.setline(1590);
      var3 = var1.getlocal(0).__getattr__("_ip_int_from_string").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1591);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1592);
      var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(0).__getattr__("_HEXTET_COUNT")).__iter__();

      while(true) {
         var1.setline(1592);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1595);
            var1.getlocal(3).__getattr__("reverse").__call__(var2);
            var1.setline(1596);
            var3 = PyString.fromInterned(":").__getattr__("join").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(1593);
         var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("%04x")._mod(var1.getlocal(2)._and(Py.newInteger(65535))));
         var1.setline(1594);
         PyObject var5 = var1.getlocal(2);
         var5 = var5._irshift(Py.newInteger(16));
         var1.setlocal(2, var5);
      }
   }

   public PyObject max_prefixlen$102(PyFrame var1, ThreadState var2) {
      var1.setline(1600);
      PyObject var3 = var1.getlocal(0).__getattr__("_max_prefixlen");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject packed$103(PyFrame var1, ThreadState var2) {
      var1.setline(1604);
      PyString.fromInterned("The binary representation of this address.");
      var1.setline(1605);
      PyObject var3 = var1.getglobal("v6_int_to_packed").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject version$104(PyFrame var1, ThreadState var2) {
      var1.setline(1609);
      PyObject var3 = var1.getlocal(0).__getattr__("_version");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_multicast$105(PyFrame var1, ThreadState var2) {
      var1.setline(1619);
      PyString.fromInterned("Test if the address is reserved for multicast use.\n\n        Returns:\n            A boolean, True if the address is a multicast address.\n            See RFC 2373 2.7 for details.\n\n        ");
      var1.setline(1620);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ff00::/8")));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_reserved$106(PyFrame var1, ThreadState var2) {
      var1.setline(1630);
      PyString.fromInterned("Test if the address is otherwise IETF reserved.\n\n        Returns:\n            A boolean, True if the address is within one of the\n            reserved IPv6 Network ranges.\n\n        ");
      var1.setline(1631);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("::/8")));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("100::/8")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("200::/7")));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(0);
               var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("400::/6")));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(0);
                  var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("800::/5")));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(0);
                     var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("1000::/4")));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var3 = var1.getlocal(0);
                        var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("4000::/3")));
                        var3 = null;
                        if (!var10000.__nonzero__()) {
                           var3 = var1.getlocal(0);
                           var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("6000::/3")));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              var3 = var1.getlocal(0);
                              var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("8000::/3")));
                              var3 = null;
                              if (!var10000.__nonzero__()) {
                                 var3 = var1.getlocal(0);
                                 var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A000::/3")));
                                 var3 = null;
                                 if (!var10000.__nonzero__()) {
                                    var3 = var1.getlocal(0);
                                    var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("C000::/3")));
                                    var3 = null;
                                    if (!var10000.__nonzero__()) {
                                       var3 = var1.getlocal(0);
                                       var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("E000::/4")));
                                       var3 = null;
                                       if (!var10000.__nonzero__()) {
                                          var3 = var1.getlocal(0);
                                          var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("F000::/5")));
                                          var3 = null;
                                          if (!var10000.__nonzero__()) {
                                             var3 = var1.getlocal(0);
                                             var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("F800::/6")));
                                             var3 = null;
                                             if (!var10000.__nonzero__()) {
                                                var3 = var1.getlocal(0);
                                                var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FE00::/9")));
                                                var3 = null;
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
               }
            }
         }
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_unspecified$107(PyFrame var1, ThreadState var2) {
      var1.setline(1655);
      PyString.fromInterned("Test if the address is unspecified.\n\n        Returns:\n            A boolean, True if this is the unspecified address as defined in\n            RFC 2373 2.5.2.\n\n        ");
      var1.setline(1656);
      PyObject var3 = var1.getlocal(0).__getattr__("_ip");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("_prefixlen"), (PyObject)Py.newInteger(128));
         var10000 = var3._eq(Py.newInteger(128));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_loopback$108(PyFrame var1, ThreadState var2) {
      var1.setline(1666);
      PyString.fromInterned("Test if the address is a loopback address.\n\n        Returns:\n            A boolean, True if the address is a loopback address as defined in\n            RFC 2373 2.5.3.\n\n        ");
      var1.setline(1667);
      PyObject var3 = var1.getlocal(0).__getattr__("_ip");
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("_prefixlen"), (PyObject)Py.newInteger(128));
         var10000 = var3._eq(Py.newInteger(128));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_link_local$109(PyFrame var1, ThreadState var2) {
      var1.setline(1676);
      PyString.fromInterned("Test if the address is reserved for link-local.\n\n        Returns:\n            A boolean, True if the address is reserved per RFC 4291.\n\n        ");
      var1.setline(1677);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fe80::/10")));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_site_local$110(PyFrame var1, ThreadState var2) {
      var1.setline(1690);
      PyString.fromInterned("Test if the address is reserved for site-local.\n\n        Note that the site-local address space has been deprecated by RFC 3879.\n        Use is_private to test if this address is in the space of unique local\n        addresses as defined by RFC 4193.\n\n        Returns:\n            A boolean, True if the address is reserved per RFC 3513 2.5.6.\n\n        ");
      var1.setline(1691);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fec0::/10")));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_private$111(PyFrame var1, ThreadState var2) {
      var1.setline(1700);
      PyString.fromInterned("Test if this address is allocated for private networks.\n\n        Returns:\n            A boolean, True if the address is reserved per RFC 4193.\n\n        ");
      var1.setline(1701);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("IPv6Network").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fc00::/7")));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ipv4_mapped$112(PyFrame var1, ThreadState var2) {
      var1.setline(1711);
      PyString.fromInterned("Return the IPv4 mapped address.\n\n        Returns:\n            If the IPv6 address is a v4 mapped address, return the\n            IPv4 mapped address. Return None otherwise.\n\n        ");
      var1.setline(1712);
      PyObject var3 = var1.getlocal(0).__getattr__("_ip")._rshift(Py.newInteger(32));
      PyObject var10000 = var3._ne(Py.newInteger(65535));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1713);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1714);
         var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip")._and(Py.newLong("4294967295")));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject teredo$113(PyFrame var1, ThreadState var2) {
      var1.setline(1725);
      PyString.fromInterned("Tuple of embedded teredo IPs.\n\n        Returns:\n            Tuple of the (server, client) IPs or None if the address\n            doesn't appear to be a teredo address (doesn't start with\n            2001::/32)\n\n        ");
      var1.setline(1726);
      PyObject var3 = var1.getlocal(0).__getattr__("_ip")._rshift(Py.newInteger(96));
      PyObject var10000 = var3._ne(Py.newInteger(536936448));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1727);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1728);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip")._rshift(Py.newInteger(64))._and(Py.newLong("4294967295"))), var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip").__invert__()._and(Py.newLong("4294967295")))});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject sixtofour$114(PyFrame var1, ThreadState var2) {
      var1.setline(1739);
      PyString.fromInterned("Return the IPv4 6to4 embedded address.\n\n        Returns:\n            The IPv4 6to4-embedded address if present or None if the\n            address doesn't appear to contain a 6to4 embedded address.\n\n        ");
      var1.setline(1740);
      PyObject var3 = var1.getlocal(0).__getattr__("_ip")._rshift(Py.newInteger(112));
      PyObject var10000 = var3._ne(Py.newInteger(8194));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1741);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1742);
         var3 = var1.getglobal("IPv4Address").__call__(var2, var1.getlocal(0).__getattr__("_ip")._rshift(Py.newInteger(80))._and(Py.newLong("4294967295")));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject IPv6Address$115(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Represent and manipulate single IPv6 Addresses.\n    "));
      var1.setline(1748);
      PyString.fromInterned("Represent and manipulate single IPv6 Addresses.\n    ");
      var1.setline(1750);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$116, PyString.fromInterned("Instantiate a new IPv6 address object.\n\n        Args:\n            address: A string or integer representing the IP\n\n              Additionally, an integer can be passed, so\n              IPv6Address('2001:4860::') ==\n                IPv6Address(42541956101370907050197289607612071936L).\n              or, more generally\n              IPv6Address(IPv6Address('2001:4860::')._ip) ==\n                IPv6Address('2001:4860::')\n\n        Raises:\n            AddressValueError: If address isn't a valid IPv6 address.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$116(PyFrame var1, ThreadState var2) {
      var1.setline(1766);
      PyString.fromInterned("Instantiate a new IPv6 address object.\n\n        Args:\n            address: A string or integer representing the IP\n\n              Additionally, an integer can be passed, so\n              IPv6Address('2001:4860::') ==\n                IPv6Address(42541956101370907050197289607612071936L).\n              or, more generally\n              IPv6Address(IPv6Address('2001:4860::')._ip) ==\n                IPv6Address('2001:4860::')\n\n        Raises:\n            AddressValueError: If address isn't a valid IPv6 address.\n\n        ");
      var1.setline(1767);
      var1.getglobal("_BaseIP").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1768);
      var1.getglobal("_BaseV6").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1771);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
         var1.setline(1772);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_ip", var3);
         var3 = null;
         var1.setline(1773);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._gt(var1.getlocal(0).__getattr__("_ALL_ONES"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1774);
            throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(1775);
            var1.f_lasti = -1;
            return Py.None;
         }
      } else {
         var1.setline(1778);
         if (var1.getglobal("_compat_has_real_bytes").__nonzero__()) {
            var1.setline(1779);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("bytes"));
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var3._eq(Py.newInteger(16));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1780);
               var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("!QQ"), (PyObject)var1.getlocal(1));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(1781);
               var3 = var1.getlocal(2).__getitem__(Py.newInteger(0))._lshift(Py.newInteger(64))._or(var1.getlocal(2).__getitem__(Py.newInteger(1)));
               var1.getlocal(0).__setattr__("_ip", var3);
               var3 = null;
               var1.setline(1782);
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setline(1786);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1787);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(1788);
            throw Py.makeException(var1.getglobal("AddressValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         } else {
            var1.setline(1790);
            var3 = var1.getlocal(0).__getattr__("_ip_int_from_string").__call__(var2, var1.getlocal(3));
            var1.getlocal(0).__setattr__("_ip", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject IPv6Network$117(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class represents and manipulates 128-bit IPv6 networks.\n\n    Attributes: [examples for IPv6('2001:658:22A:CAFE:200::1/64')]\n        .ip: IPv6Address('2001:658:22a:cafe:200::1')\n        .network: IPv6Address('2001:658:22a:cafe::')\n        .hostmask: IPv6Address('::ffff:ffff:ffff:ffff')\n        .broadcast: IPv6Address('2001:658:22a:cafe:ffff:ffff:ffff:ffff')\n        .netmask: IPv6Address('ffff:ffff:ffff:ffff::')\n        .prefixlen: 64\n\n    "));
      var1.setline(1805);
      PyString.fromInterned("This class represents and manipulates 128-bit IPv6 networks.\n\n    Attributes: [examples for IPv6('2001:658:22A:CAFE:200::1/64')]\n        .ip: IPv6Address('2001:658:22a:cafe:200::1')\n        .network: IPv6Address('2001:658:22a:cafe::')\n        .hostmask: IPv6Address('::ffff:ffff:ffff:ffff')\n        .broadcast: IPv6Address('2001:658:22a:cafe:ffff:ffff:ffff:ffff')\n        .netmask: IPv6Address('ffff:ffff:ffff:ffff::')\n        .prefixlen: 64\n\n    ");
      var1.setline(1808);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$118, PyString.fromInterned("Instantiate a new IPv6 Network object.\n\n        Args:\n            address: A string or integer representing the IPv6 network or the IP\n              and prefix/netmask.\n              '2001:4860::/128'\n              '2001:4860:0000:0000:0000:0000:0000:0000/128'\n              '2001:4860::'\n              are all functionally the same in IPv6.  That is to say,\n              failing to provide a subnetmask will create an object with\n              a mask of /128.\n\n              Additionally, an integer can be passed, so\n              IPv6Network('2001:4860::') ==\n                IPv6Network(42541956101370907050197289607612071936L).\n              or, more generally\n              IPv6Network(IPv6Network('2001:4860::')._ip) ==\n                IPv6Network('2001:4860::')\n\n            strict: A boolean. If true, ensure that we have been passed\n              A true network address, eg, 192.168.1.0/24 and not an\n              IP address on a network, eg, 192.168.1.1/24.\n\n        Raises:\n            AddressValueError: If address isn't a valid IPv6 address.\n            NetmaskValueError: If the netmask isn't valid for\n              an IPv6 address.\n            ValueError: If strict was True and a network address was not\n              supplied.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1888);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _is_valid_netmask$119, PyString.fromInterned("Verify that the netmask/prefixlen is valid.\n\n        Args:\n            prefixlen: A string, the netmask in prefix length format.\n\n        Returns:\n            A boolean, True if the prefix represents a valid IPv6\n            netmask.\n\n        "));
      var1.setlocal("_is_valid_netmask", var4);
      var3 = null;
      var1.setline(1905);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, with_netmask$120, (PyObject)null);
      PyObject var5 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("with_netmask", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$118(PyFrame var1, ThreadState var2) {
      var1.setline(1839);
      PyString.fromInterned("Instantiate a new IPv6 Network object.\n\n        Args:\n            address: A string or integer representing the IPv6 network or the IP\n              and prefix/netmask.\n              '2001:4860::/128'\n              '2001:4860:0000:0000:0000:0000:0000:0000/128'\n              '2001:4860::'\n              are all functionally the same in IPv6.  That is to say,\n              failing to provide a subnetmask will create an object with\n              a mask of /128.\n\n              Additionally, an integer can be passed, so\n              IPv6Network('2001:4860::') ==\n                IPv6Network(42541956101370907050197289607612071936L).\n              or, more generally\n              IPv6Network(IPv6Network('2001:4860::')._ip) ==\n                IPv6Network('2001:4860::')\n\n            strict: A boolean. If true, ensure that we have been passed\n              A true network address, eg, 192.168.1.0/24 and not an\n              IP address on a network, eg, 192.168.1.1/24.\n\n        Raises:\n            AddressValueError: If address isn't a valid IPv6 address.\n            NetmaskValueError: If the netmask isn't valid for\n              an IPv6 address.\n            ValueError: If strict was True and a network address was not\n              supplied.\n\n        ");
      var1.setline(1840);
      var1.getglobal("_BaseNet").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1841);
      var1.getglobal("_BaseV6").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1844);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
         var1.setline(1845);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_ip", var3);
         var3 = null;
         var1.setline(1846);
         var3 = var1.getglobal("IPv6Address").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
         var1.getlocal(0).__setattr__("ip", var3);
         var3 = null;
         var1.setline(1847);
         var3 = var1.getlocal(0).__getattr__("_max_prefixlen");
         var1.getlocal(0).__setattr__("_prefixlen", var3);
         var3 = null;
         var1.setline(1848);
         var3 = var1.getglobal("IPv6Address").__call__(var2, var1.getlocal(0).__getattr__("_ALL_ONES"));
         var1.getlocal(0).__setattr__("netmask", var3);
         var3 = null;
         var1.setline(1849);
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._gt(var1.getlocal(0).__getattr__("_ALL_ONES"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1850);
            throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(1851);
            var1.f_lasti = -1;
            return Py.None;
         }
      } else {
         var1.setline(1854);
         if (var1.getglobal("_compat_has_real_bytes").__nonzero__()) {
            var1.setline(1855);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("bytes"));
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var3._eq(Py.newInteger(16));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1856);
               var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("!QQ"), (PyObject)var1.getlocal(1));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(1857);
               var3 = var1.getlocal(3).__getitem__(Py.newInteger(0))._lshift(Py.newInteger(64))._or(var1.getlocal(3).__getitem__(Py.newInteger(1)));
               var1.getlocal(0).__setattr__("_ip", var3);
               var3 = null;
               var1.setline(1858);
               var3 = var1.getglobal("IPv6Address").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
               var1.getlocal(0).__setattr__("ip", var3);
               var3 = null;
               var1.setline(1859);
               var3 = var1.getlocal(0).__getattr__("_max_prefixlen");
               var1.getlocal(0).__setattr__("_prefixlen", var3);
               var3 = null;
               var1.setline(1860);
               var3 = var1.getglobal("IPv6Address").__call__(var2, var1.getlocal(0).__getattr__("_ALL_ONES"));
               var1.getlocal(0).__setattr__("netmask", var3);
               var3 = null;
               var1.setline(1861);
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setline(1865);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1867);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var10000 = var3._gt(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1868);
            throw Py.makeException(var1.getglobal("AddressValueError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(1870);
            var3 = var1.getlocal(0).__getattr__("_ip_int_from_string").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(0)));
            var1.getlocal(0).__setattr__("_ip", var3);
            var3 = null;
            var1.setline(1871);
            var3 = var1.getglobal("IPv6Address").__call__(var2, var1.getlocal(0).__getattr__("_ip"));
            var1.getlocal(0).__setattr__("ip", var3);
            var3 = null;
            var1.setline(1873);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1874);
               if (!var1.getlocal(0).__getattr__("_is_valid_netmask").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(1))).__nonzero__()) {
                  var1.setline(1877);
                  throw Py.makeException(var1.getglobal("NetmaskValueError").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(1))));
               }

               var1.setline(1875);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(1)));
               var1.getlocal(0).__setattr__("_prefixlen", var3);
               var3 = null;
            } else {
               var1.setline(1879);
               var3 = var1.getlocal(0).__getattr__("_max_prefixlen");
               var1.getlocal(0).__setattr__("_prefixlen", var3);
               var3 = null;
            }

            var1.setline(1881);
            var3 = var1.getglobal("IPv6Address").__call__(var2, var1.getlocal(0).__getattr__("_ip_int_from_prefix").__call__(var2, var1.getlocal(0).__getattr__("_prefixlen")));
            var1.getlocal(0).__setattr__("netmask", var3);
            var3 = null;
            var1.setline(1883);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1884);
               var3 = var1.getlocal(0).__getattr__("ip");
               var10000 = var3._ne(var1.getlocal(0).__getattr__("network"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1885);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%s has host bits set")._mod(var1.getlocal(0).__getattr__("ip"))));
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _is_valid_netmask$119(PyFrame var1, ThreadState var2) {
      var1.setline(1898);
      PyString.fromInterned("Verify that the netmask/prefixlen is valid.\n\n        Args:\n            prefixlen: A string, the netmask in prefix length format.\n\n        Returns:\n            A boolean, True if the prefix represents a valid IPv6\n            netmask.\n\n        ");

      PyException var3;
      PyObject var7;
      try {
         var1.setline(1900);
         var7 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("ValueError"))) {
            var1.setline(1902);
            PyObject var4 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(1903);
      PyInteger var8 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(1);
      PyInteger var10000 = var8;
      var7 = var10001;
      PyObject var5;
      if ((var5 = var10000._le(var10001)).__nonzero__()) {
         var5 = var7._le(var1.getlocal(0).__getattr__("_max_prefixlen"));
      }

      var3 = null;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject with_netmask$120(PyFrame var1, ThreadState var2) {
      var1.setline(1907);
      PyObject var3 = var1.getlocal(0).__getattr__("with_prefixlen");
      var1.f_lasti = -1;
      return var3;
   }

   public _google_ipaddr_r234$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      AddressValueError$1 = Py.newCode(0, var2, var1, "AddressValueError", 33, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NetmaskValueError$2 = Py.newCode(0, var2, var1, "NetmaskValueError", 37, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"address", "version"};
      IPAddress$3 = Py.newCode(2, var2, var1, "IPAddress", 41, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"address", "version", "strict"};
      IPNetwork$4 = Py.newCode(3, var2, var1, "IPNetwork", 81, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"address"};
      v4_int_to_packed$5 = Py.newCode(1, var2, var1, "v4_int_to_packed", 122, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"address"};
      v6_int_to_packed$6 = Py.newCode(1, var2, var1, "v6_int_to_packed", 140, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addresses", "first", "last", "ip"};
      _find_address_range$7 = Py.newCode(1, var2, var1, "_find_address_range", 152, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"number1", "number2", "bits", "i"};
      _get_prefix_length$8 = Py.newCode(3, var2, var1, "_get_prefix_length", 170, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"number", "bits", "i"};
      _count_righthand_zero_bits$9 = Py.newCode(2, var2, var1, "_count_righthand_zero_bits", 187, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"first", "last", "networks", "ip", "ip_bits", "first_int", "last_int", "nbits", "current", "addend", "prefix", "net"};
      summarize_address_range$10 = Py.newCode(2, var2, var1, "summarize_address_range", 204, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addresses", "ret_array", "optimized", "cur_addr"};
      _collapse_address_list_recursive$11 = Py.newCode(1, var2, var1, "_collapse_address_list_recursive", 268, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"addresses", "i", "addrs", "ips", "nets", "ip", "first", "last"};
      collapse_address_list$12 = Py.newCode(1, var2, var1, "collapse_address_list", 315, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"obj"};
      get_mixed_type_key$13 = Py.newCode(1, var2, var1, "get_mixed_type_key", 381, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _IPAddrBase$14 = Py.newCode(0, var2, var1, "_IPAddrBase", 405, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __index__$15 = Py.newCode(1, var2, var1, "__index__", 409, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __int__$16 = Py.newCode(1, var2, var1, "__int__", 412, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hex__$17 = Py.newCode(1, var2, var1, "__hex__", 415, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      exploded$18 = Py.newCode(1, var2, var1, "exploded", 418, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      compressed$19 = Py.newCode(1, var2, var1, "compressed", 423, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _BaseIP$20 = Py.newCode(0, var2, var1, "_BaseIP", 429, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "address"};
      __init__$21 = Py.newCode(2, var2, var1, "__init__", 438, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$22 = Py.newCode(2, var2, var1, "__eq__", 443, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "eq"};
      __ne__$23 = Py.newCode(2, var2, var1, "__ne__", 450, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "gt"};
      __le__$24 = Py.newCode(2, var2, var1, "__le__", 456, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "lt"};
      __ge__$25 = Py.newCode(2, var2, var1, "__ge__", 462, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __lt__$26 = Py.newCode(2, var2, var1, "__lt__", 468, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __gt__$27 = Py.newCode(2, var2, var1, "__gt__", 479, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __add__$28 = Py.newCode(2, var2, var1, "__add__", 492, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __sub__$29 = Py.newCode(2, var2, var1, "__sub__", 497, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$30 = Py.newCode(1, var2, var1, "__repr__", 502, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$31 = Py.newCode(1, var2, var1, "__str__", 505, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$32 = Py.newCode(1, var2, var1, "__hash__", 508, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_address_key$33 = Py.newCode(1, var2, var1, "_get_address_key", 511, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      version$34 = Py.newCode(1, var2, var1, "version", 514, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _BaseNet$35 = Py.newCode(0, var2, var1, "_BaseNet", 519, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "address"};
      __init__$36 = Py.newCode(2, var2, var1, "__init__", 528, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$37 = Py.newCode(1, var2, var1, "__repr__", 531, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cur", "bcast"};
      iterhosts$38 = Py.newCode(1, var2, var1, "iterhosts", 534, false, false, self, 38, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "cur", "bcast"};
      __iter__$39 = Py.newCode(1, var2, var1, "__iter__", 547, false, false, self, 39, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "n", "network", "broadcast"};
      __getitem__$40 = Py.newCode(2, var2, var1, "__getitem__", 554, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __lt__$41 = Py.newCode(2, var2, var1, "__lt__", 567, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __gt__$42 = Py.newCode(2, var2, var1, "__gt__", 580, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "gt"};
      __le__$43 = Py.newCode(2, var2, var1, "__le__", 593, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "lt"};
      __ge__$44 = Py.newCode(2, var2, var1, "__ge__", 599, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$45 = Py.newCode(2, var2, var1, "__eq__", 605, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "eq"};
      __ne__$46 = Py.newCode(2, var2, var1, "__ne__", 615, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$47 = Py.newCode(1, var2, var1, "__str__", 621, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$48 = Py.newCode(1, var2, var1, "__hash__", 625, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __contains__$49 = Py.newCode(2, var2, var1, "__contains__", 628, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      overlaps$50 = Py.newCode(2, var2, var1, "overlaps", 641, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      network$51 = Py.newCode(1, var2, var1, "network", 646, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      broadcast$52 = Py.newCode(1, var2, var1, "broadcast", 654, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x"};
      hostmask$53 = Py.newCode(1, var2, var1, "hostmask", 662, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      with_prefixlen$54 = Py.newCode(1, var2, var1, "with_prefixlen", 671, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      with_netmask$55 = Py.newCode(1, var2, var1, "with_netmask", 675, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      with_hostmask$56 = Py.newCode(1, var2, var1, "with_hostmask", 679, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      numhosts$57 = Py.newCode(1, var2, var1, "numhosts", 683, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      version$58 = Py.newCode(1, var2, var1, "version", 688, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      prefixlen$59 = Py.newCode(1, var2, var1, "prefixlen", 692, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "ret_addrs", "s1", "s2"};
      address_exclude$60 = Py.newCode(2, var2, var1, "address_exclude", 696, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      compare_networks$61 = Py.newCode(2, var2, var1, "compare_networks", 774, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_networks_key$62 = Py.newCode(1, var2, var1, "_get_networks_key", 825, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefixlen"};
      _ip_int_from_prefix$63 = Py.newCode(2, var2, var1, "_ip_int_from_prefix", 835, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ip_int", "mask"};
      _prefix_from_ip_int$64 = Py.newCode(3, var2, var1, "_prefix_from_ip_int", 849, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefixlen"};
      _ip_string_from_prefix$65 = Py.newCode(2, var2, var1, "_ip_string_from_prefix", 868, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefixlen_diff", "new_prefix", "new_prefixlen", "first", "current", "broadcast", "new_addr"};
      iter_subnets$66 = Py.newCode(3, var2, var1, "iter_subnets", 882, false, false, self, 66, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      masked$67 = Py.newCode(1, var2, var1, "masked", 944, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefixlen_diff", "new_prefix"};
      subnet$68 = Py.newCode(3, var2, var1, "subnet", 949, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefixlen_diff", "new_prefix"};
      supernet$69 = Py.newCode(3, var2, var1, "supernet", 953, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _BaseV4$70 = Py.newCode(0, var2, var1, "_BaseV4", 1001, false, false, self, 70, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "address"};
      __init__$71 = Py.newCode(2, var2, var1, "__init__", 1014, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ip_str"};
      _explode_shorthand_ip_string$72 = Py.newCode(2, var2, var1, "_explode_shorthand_ip_string", 1018, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ip_str", "octets", "packed_ip", "oc"};
      _ip_int_from_string$73 = Py.newCode(2, var2, var1, "_ip_int_from_string", 1023, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "octet_str", "octet_int"};
      _parse_octet$74 = Py.newCode(2, var2, var1, "_parse_octet", 1048, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ip_int", "octets", "_"};
      _string_from_ip_int$75 = Py.newCode(2, var2, var1, "_string_from_ip_int", 1071, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      max_prefixlen$76 = Py.newCode(1, var2, var1, "max_prefixlen", 1087, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      packed$77 = Py.newCode(1, var2, var1, "packed", 1091, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      version$78 = Py.newCode(1, var2, var1, "version", 1096, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_reserved$79 = Py.newCode(1, var2, var1, "is_reserved", 1100, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_private$80 = Py.newCode(1, var2, var1, "is_private", 1111, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_multicast$81 = Py.newCode(1, var2, var1, "is_multicast", 1123, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_unspecified$82 = Py.newCode(1, var2, var1, "is_unspecified", 1134, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_loopback$83 = Py.newCode(1, var2, var1, "is_loopback", 1145, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_link_local$84 = Py.newCode(1, var2, var1, "is_link_local", 1155, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IPv4Address$85 = Py.newCode(0, var2, var1, "IPv4Address", 1166, false, false, self, 85, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "address", "addr_str"};
      __init__$86 = Py.newCode(2, var2, var1, "__init__", 1170, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IPv4Network$87 = Py.newCode(0, var2, var1, "IPv4Network", 1209, false, false, self, 87, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "address", "strict", "addr", "mask"};
      __init__$88 = Py.newCode(3, var2, var1, "__init__", 1227, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ip_str", "bits", "parts", "_[1342_21]", "x"};
      _is_hostmask$89 = Py.newCode(2, var2, var1, "_is_hostmask", 1330, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "netmask", "mask", "_[1365_16]", "x", "_[1367_16]", "y", "idx"};
      _is_valid_netmask$90 = Py.newCode(2, var2, var1, "_is_valid_netmask", 1351, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$91 = Py.newCode(1, var2, var1, "<lambda>", 1378, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$92 = Py.newCode(1, var2, var1, "<lambda>", 1379, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$93 = Py.newCode(1, var2, var1, "<lambda>", 1380, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$94 = Py.newCode(1, var2, var1, "<lambda>", 1381, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _BaseV6$95 = Py.newCode(0, var2, var1, "_BaseV6", 1384, false, false, self, 95, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "address"};
      __init__$96 = Py.newCode(2, var2, var1, "__init__", 1397, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ip_str", "parts", "ipv4_int", "skip_index", "_[1434_17]", "i", "parts_hi", "parts_lo", "parts_skipped", "ip_int"};
      _ip_int_from_string$97 = Py.newCode(2, var2, var1, "_ip_int_from_string", 1401, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hextet_str", "hextet_int"};
      _parse_hextet$98 = Py.newCode(2, var2, var1, "_parse_hextet", 1480, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "hextets", "best_doublecolon_start", "best_doublecolon_len", "doublecolon_start", "doublecolon_len", "index", "best_doublecolon_end"};
      _compress_hextets$99 = Py.newCode(2, var2, var1, "_compress_hextets", 1501, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ip_int", "hex_str", "hextets", "x"};
      _string_from_ip_int$100 = Py.newCode(2, var2, var1, "_string_from_ip_int", 1548, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ip_str", "ip_int", "parts", "i"};
      _explode_shorthand_ip_string$101 = Py.newCode(2, var2, var1, "_explode_shorthand_ip_string", 1575, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      max_prefixlen$102 = Py.newCode(1, var2, var1, "max_prefixlen", 1598, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      packed$103 = Py.newCode(1, var2, var1, "packed", 1602, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      version$104 = Py.newCode(1, var2, var1, "version", 1607, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_multicast$105 = Py.newCode(1, var2, var1, "is_multicast", 1611, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_reserved$106 = Py.newCode(1, var2, var1, "is_reserved", 1622, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_unspecified$107 = Py.newCode(1, var2, var1, "is_unspecified", 1647, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_loopback$108 = Py.newCode(1, var2, var1, "is_loopback", 1658, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_link_local$109 = Py.newCode(1, var2, var1, "is_link_local", 1669, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_site_local$110 = Py.newCode(1, var2, var1, "is_site_local", 1679, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      is_private$111 = Py.newCode(1, var2, var1, "is_private", 1693, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      ipv4_mapped$112 = Py.newCode(1, var2, var1, "ipv4_mapped", 1703, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      teredo$113 = Py.newCode(1, var2, var1, "teredo", 1716, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      sixtofour$114 = Py.newCode(1, var2, var1, "sixtofour", 1731, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IPv6Address$115 = Py.newCode(0, var2, var1, "IPv6Address", 1745, false, false, self, 115, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "address", "tmp", "addr_str"};
      __init__$116 = Py.newCode(2, var2, var1, "__init__", 1750, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IPv6Network$117 = Py.newCode(0, var2, var1, "IPv6Network", 1793, false, false, self, 117, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "address", "strict", "tmp", "addr"};
      __init__$118 = Py.newCode(3, var2, var1, "__init__", 1808, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prefixlen"};
      _is_valid_netmask$119 = Py.newCode(2, var2, var1, "_is_valid_netmask", 1888, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      with_netmask$120 = Py.newCode(1, var2, var1, "with_netmask", 1905, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _google_ipaddr_r234$py("_google_ipaddr_r234$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_google_ipaddr_r234$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.AddressValueError$1(var2, var3);
         case 2:
            return this.NetmaskValueError$2(var2, var3);
         case 3:
            return this.IPAddress$3(var2, var3);
         case 4:
            return this.IPNetwork$4(var2, var3);
         case 5:
            return this.v4_int_to_packed$5(var2, var3);
         case 6:
            return this.v6_int_to_packed$6(var2, var3);
         case 7:
            return this._find_address_range$7(var2, var3);
         case 8:
            return this._get_prefix_length$8(var2, var3);
         case 9:
            return this._count_righthand_zero_bits$9(var2, var3);
         case 10:
            return this.summarize_address_range$10(var2, var3);
         case 11:
            return this._collapse_address_list_recursive$11(var2, var3);
         case 12:
            return this.collapse_address_list$12(var2, var3);
         case 13:
            return this.get_mixed_type_key$13(var2, var3);
         case 14:
            return this._IPAddrBase$14(var2, var3);
         case 15:
            return this.__index__$15(var2, var3);
         case 16:
            return this.__int__$16(var2, var3);
         case 17:
            return this.__hex__$17(var2, var3);
         case 18:
            return this.exploded$18(var2, var3);
         case 19:
            return this.compressed$19(var2, var3);
         case 20:
            return this._BaseIP$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.__eq__$22(var2, var3);
         case 23:
            return this.__ne__$23(var2, var3);
         case 24:
            return this.__le__$24(var2, var3);
         case 25:
            return this.__ge__$25(var2, var3);
         case 26:
            return this.__lt__$26(var2, var3);
         case 27:
            return this.__gt__$27(var2, var3);
         case 28:
            return this.__add__$28(var2, var3);
         case 29:
            return this.__sub__$29(var2, var3);
         case 30:
            return this.__repr__$30(var2, var3);
         case 31:
            return this.__str__$31(var2, var3);
         case 32:
            return this.__hash__$32(var2, var3);
         case 33:
            return this._get_address_key$33(var2, var3);
         case 34:
            return this.version$34(var2, var3);
         case 35:
            return this._BaseNet$35(var2, var3);
         case 36:
            return this.__init__$36(var2, var3);
         case 37:
            return this.__repr__$37(var2, var3);
         case 38:
            return this.iterhosts$38(var2, var3);
         case 39:
            return this.__iter__$39(var2, var3);
         case 40:
            return this.__getitem__$40(var2, var3);
         case 41:
            return this.__lt__$41(var2, var3);
         case 42:
            return this.__gt__$42(var2, var3);
         case 43:
            return this.__le__$43(var2, var3);
         case 44:
            return this.__ge__$44(var2, var3);
         case 45:
            return this.__eq__$45(var2, var3);
         case 46:
            return this.__ne__$46(var2, var3);
         case 47:
            return this.__str__$47(var2, var3);
         case 48:
            return this.__hash__$48(var2, var3);
         case 49:
            return this.__contains__$49(var2, var3);
         case 50:
            return this.overlaps$50(var2, var3);
         case 51:
            return this.network$51(var2, var3);
         case 52:
            return this.broadcast$52(var2, var3);
         case 53:
            return this.hostmask$53(var2, var3);
         case 54:
            return this.with_prefixlen$54(var2, var3);
         case 55:
            return this.with_netmask$55(var2, var3);
         case 56:
            return this.with_hostmask$56(var2, var3);
         case 57:
            return this.numhosts$57(var2, var3);
         case 58:
            return this.version$58(var2, var3);
         case 59:
            return this.prefixlen$59(var2, var3);
         case 60:
            return this.address_exclude$60(var2, var3);
         case 61:
            return this.compare_networks$61(var2, var3);
         case 62:
            return this._get_networks_key$62(var2, var3);
         case 63:
            return this._ip_int_from_prefix$63(var2, var3);
         case 64:
            return this._prefix_from_ip_int$64(var2, var3);
         case 65:
            return this._ip_string_from_prefix$65(var2, var3);
         case 66:
            return this.iter_subnets$66(var2, var3);
         case 67:
            return this.masked$67(var2, var3);
         case 68:
            return this.subnet$68(var2, var3);
         case 69:
            return this.supernet$69(var2, var3);
         case 70:
            return this._BaseV4$70(var2, var3);
         case 71:
            return this.__init__$71(var2, var3);
         case 72:
            return this._explode_shorthand_ip_string$72(var2, var3);
         case 73:
            return this._ip_int_from_string$73(var2, var3);
         case 74:
            return this._parse_octet$74(var2, var3);
         case 75:
            return this._string_from_ip_int$75(var2, var3);
         case 76:
            return this.max_prefixlen$76(var2, var3);
         case 77:
            return this.packed$77(var2, var3);
         case 78:
            return this.version$78(var2, var3);
         case 79:
            return this.is_reserved$79(var2, var3);
         case 80:
            return this.is_private$80(var2, var3);
         case 81:
            return this.is_multicast$81(var2, var3);
         case 82:
            return this.is_unspecified$82(var2, var3);
         case 83:
            return this.is_loopback$83(var2, var3);
         case 84:
            return this.is_link_local$84(var2, var3);
         case 85:
            return this.IPv4Address$85(var2, var3);
         case 86:
            return this.__init__$86(var2, var3);
         case 87:
            return this.IPv4Network$87(var2, var3);
         case 88:
            return this.__init__$88(var2, var3);
         case 89:
            return this._is_hostmask$89(var2, var3);
         case 90:
            return this._is_valid_netmask$90(var2, var3);
         case 91:
            return this.f$91(var2, var3);
         case 92:
            return this.f$92(var2, var3);
         case 93:
            return this.f$93(var2, var3);
         case 94:
            return this.f$94(var2, var3);
         case 95:
            return this._BaseV6$95(var2, var3);
         case 96:
            return this.__init__$96(var2, var3);
         case 97:
            return this._ip_int_from_string$97(var2, var3);
         case 98:
            return this._parse_hextet$98(var2, var3);
         case 99:
            return this._compress_hextets$99(var2, var3);
         case 100:
            return this._string_from_ip_int$100(var2, var3);
         case 101:
            return this._explode_shorthand_ip_string$101(var2, var3);
         case 102:
            return this.max_prefixlen$102(var2, var3);
         case 103:
            return this.packed$103(var2, var3);
         case 104:
            return this.version$104(var2, var3);
         case 105:
            return this.is_multicast$105(var2, var3);
         case 106:
            return this.is_reserved$106(var2, var3);
         case 107:
            return this.is_unspecified$107(var2, var3);
         case 108:
            return this.is_loopback$108(var2, var3);
         case 109:
            return this.is_link_local$109(var2, var3);
         case 110:
            return this.is_site_local$110(var2, var3);
         case 111:
            return this.is_private$111(var2, var3);
         case 112:
            return this.ipv4_mapped$112(var2, var3);
         case 113:
            return this.teredo$113(var2, var3);
         case 114:
            return this.sixtofour$114(var2, var3);
         case 115:
            return this.IPv6Address$115(var2, var3);
         case 116:
            return this.__init__$116(var2, var3);
         case 117:
            return this.IPv6Network$117(var2, var3);
         case 118:
            return this.__init__$118(var2, var3);
         case 119:
            return this._is_valid_netmask$119(var2, var3);
         case 120:
            return this.with_netmask$120(var2, var3);
         default:
            return null;
      }
   }
}
