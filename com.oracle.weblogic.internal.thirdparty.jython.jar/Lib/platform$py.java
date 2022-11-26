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
@Filename("platform.py")
public class platform$py extends PyFunctionTable implements PyRunnable {
   static platform$py self;
   static final PyCode f$0;
   static final PyCode libc_ver$1;
   static final PyCode _dist_try_harder$2;
   static final PyCode _parse_release_file$3;
   static final PyCode linux_distribution$4;
   static final PyCode dist$5;
   static final PyCode _popen$6;
   static final PyCode __init__$7;
   static final PyCode read$8;
   static final PyCode readlines$9;
   static final PyCode close$10;
   static final PyCode popen$11;
   static final PyCode _norm_version$12;
   static final PyCode _syscmd_ver$13;
   static final PyCode _win32_getvalue$14;
   static final PyCode win32_ver$15;
   static final PyCode _mac_ver_lookup$16;
   static final PyCode _bcd2str$17;
   static final PyCode _mac_ver_gestalt$18;
   static final PyCode _mac_ver_xml$19;
   static final PyCode mac_ver$20;
   static final PyCode _java_getprop$21;
   static final PyCode _java_getenv$22;
   static final PyCode java_ver$23;
   static final PyCode system_alias$24;
   static final PyCode _platform$25;
   static final PyCode _node$26;
   static final PyCode _abspath$27;
   static final PyCode _follow_symlinks$28;
   static final PyCode _syscmd_uname$29;
   static final PyCode _syscmd_file$30;
   static final PyCode architecture$31;
   static final PyCode uname$32;
   static final PyCode _uname$33;
   static final PyCode system$34;
   static final PyCode node$35;
   static final PyCode release$36;
   static final PyCode version$37;
   static final PyCode machine$38;
   static final PyCode processor$39;
   static final PyCode _sys_version$40;
   static final PyCode python_implementation$41;
   static final PyCode python_version$42;
   static final PyCode python_version_tuple$43;
   static final PyCode python_branch$44;
   static final PyCode python_revision$45;
   static final PyCode python_build$46;
   static final PyCode python_compiler$47;
   static final PyCode platform$48;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" This module tries to retrieve as much platform-identifying data as\n    possible. It makes this information available via function APIs.\n\n    If called from the command line, it prints the platform\n    information concatenated as single string to stdout. The output\n    format is useable as part of a filename.\n\n"));
      var1.setline(10);
      PyString.fromInterned(" This module tries to retrieve as much platform-identifying data as\n    possible. It makes this information available via function APIs.\n\n    If called from the command line, it prints the platform\n    information concatenated as single string to stdout. The output\n    format is useable as part of a filename.\n\n");
      var1.setline(93);
      PyString var3 = PyString.fromInterned("\n    Copyright (c) 1999-2000, Marc-Andre Lemburg; mailto:mal@lemburg.com\n    Copyright (c) 2000-2010, eGenix.com Software GmbH; mailto:info@egenix.com\n\n    Permission to use, copy, modify, and distribute this software and its\n    documentation for any purpose and without fee or royalty is hereby granted,\n    provided that the above copyright notice appear in all copies and that\n    both that copyright notice and this permission notice appear in\n    supporting documentation or portions thereof, including modifications,\n    that you make.\n\n    EGENIX.COM SOFTWARE GMBH DISCLAIMS ALL WARRANTIES WITH REGARD TO\n    THIS SOFTWARE, INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND\n    FITNESS, IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL,\n    INDIRECT OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING\n    FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT,\n    NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION\n    WITH THE USE OR PERFORMANCE OF THIS SOFTWARE !\n\n");
      var1.setlocal("__copyright__", var3);
      var3 = null;
      var1.setline(114);
      var3 = PyString.fromInterned("1.0.7");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(116);
      PyObject var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var6 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var6);
      var3 = null;
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(119);
      PyObject var4;
      PyObject[] var9;
      if (var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(120);
         String[] var8 = new String[]{"System"};
         var9 = imp.importFrom("java.lang", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("System", var4);
         var4 = null;
         var1.setline(121);
         var8 = new String[]{"newString"};
         var9 = imp.importFrom("org.python.core.Py", var8, var1, -1);
         var4 = var9[0];
         var1.setlocal("newString", var4);
         var4 = null;
      }

      PyObject var10000;
      try {
         var1.setline(125);
         var6 = var1.getname("os").__getattr__("devnull");
         var1.setlocal("DEV_NULL", var6);
         var3 = null;
      } catch (Throwable var5) {
         PyException var10 = Py.setException(var5, var1);
         if (!var10.match(var1.getname("AttributeError"))) {
            throw var10;
         }

         var1.setline(129);
         var4 = var1.getname("sys").__getattr__("platform");
         var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("dos"), PyString.fromInterned("win32"), PyString.fromInterned("win16"), PyString.fromInterned("os2")}));
         var4 = null;
         PyString var7;
         if (var10000.__nonzero__()) {
            var1.setline(131);
            var7 = PyString.fromInterned("NUL");
            var1.setlocal("DEV_NULL", var7);
            var4 = null;
         } else {
            var1.setline(134);
            var7 = PyString.fromInterned("/dev/null");
            var1.setlocal("DEV_NULL", var7);
            var4 = null;
         }
      }

      var1.setline(138);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(__libc_init)|(GLIBC_([0-9.]+))|(libc(_\\w+)?\\.so(?:\\.(\\d[0-9.]*))?)"));
      var1.setlocal("_libc_search", var6);
      var3 = null;
      var1.setline(144);
      var9 = new PyObject[]{var1.getname("sys").__getattr__("executable"), PyString.fromInterned(""), PyString.fromInterned(""), Py.newInteger(2048)};
      PyFunction var11 = new PyFunction(var1.f_globals, var9, libc_ver$1, PyString.fromInterned(" Tries to determine the libc version that the file executable\n        (which defaults to the Python interpreter) is linked against.\n\n        Returns a tuple of strings (lib,version) which default to the\n        given parameters in case the lookup fails.\n\n        Note that the function has intimate knowledge of how different\n        libc versions add symbols to the executable and thus is probably\n        only useable for executables compiled using gcc.\n\n        The file is read and scanned in chunks of chunksize bytes.\n\n    "));
      var1.setlocal("libc_ver", var11);
      var3 = null;
      var1.setline(197);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _dist_try_harder$2, PyString.fromInterned(" Tries some special tricks to get the distribution\n        information in case the default method fails.\n\n        Currently supports older SuSE Linux, Caldera OpenLinux and\n        Slackware Linux distributions.\n\n    "));
      var1.setlocal("_dist_try_harder", var11);
      var3 = null;
      var1.setline(247);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\w+)[-_](release|version)"));
      var1.setlocal("_release_filename", var6);
      var3 = null;
      var1.setline(248);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(.+) release ([\\d.]+)[^(]*(?:\\((.+)\\))?"));
      var1.setlocal("_lsb_release_version", var6);
      var3 = null;
      var1.setline(252);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([^0-9]+)(?: release )?([\\d.]+)[^(]*(?:\\((.+)\\))?"));
      var1.setlocal("_release_version", var6);
      var3 = null;
      var1.setline(262);
      PyTuple var12 = new PyTuple(new PyObject[]{PyString.fromInterned("SuSE"), PyString.fromInterned("debian"), PyString.fromInterned("fedora"), PyString.fromInterned("redhat"), PyString.fromInterned("centos"), PyString.fromInterned("mandrake"), PyString.fromInterned("mandriva"), PyString.fromInterned("rocks"), PyString.fromInterned("slackware"), PyString.fromInterned("yellowdog"), PyString.fromInterned("gentoo"), PyString.fromInterned("UnitedLinux"), PyString.fromInterned("turbolinux")});
      var1.setlocal("_supported_dists", var12);
      var3 = null;
      var1.setline(267);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _parse_release_file$3, (PyObject)null);
      var1.setlocal("_parse_release_file", var11);
      var3 = null;
      var1.setline(294);
      var9 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("_supported_dists"), Py.newInteger(1)};
      var11 = new PyFunction(var1.f_globals, var9, linux_distribution$4, PyString.fromInterned(" Tries to determine the name of the Linux OS distribution name.\n\n        The function first looks for a distribution release file in\n        /etc and then reverts to _dist_try_harder() in case no\n        suitable files are found.\n\n        supported_dists may be given to define the set of Linux\n        distributions to look for. It defaults to a list of currently\n        supported Linux distributions identified by their release file\n        name.\n\n        If full_distribution_name is true (default), the full\n        distribution read from the OS is returned. Otherwise the short\n        name taken from supported_dists is used.\n\n        Returns a tuple (distname,version,id) which default to the\n        args given as parameters.\n\n    "));
      var1.setlocal("linux_distribution", var11);
      var3 = null;
      var1.setline(350);
      var9 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), var1.getname("_supported_dists")};
      var11 = new PyFunction(var1.f_globals, var9, dist$5, PyString.fromInterned(" Tries to determine the name of the Linux OS distribution name.\n\n        The function first looks for a distribution release file in\n        /etc and then reverts to _dist_try_harder() in case no\n        suitable files are found.\n\n        Returns a tuple (distname,version,id) which default to the\n        args given as parameters.\n\n    "));
      var1.setlocal("dist", var11);
      var3 = null;
      var1.setline(368);
      var9 = Py.EmptyObjects;
      var4 = Py.makeClass("_popen", var9, _popen$6);
      var1.setlocal("_popen", var4);
      var4 = null;
      Arrays.fill(var9, (Object)null);
      var1.setline(422);
      var9 = new PyObject[]{PyString.fromInterned("r"), var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var9, popen$11, PyString.fromInterned(" Portable popen() interface.\n    "));
      var1.setlocal("popen", var11);
      var3 = null;
      var1.setline(455);
      var9 = new PyObject[]{PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var9, _norm_version$12, PyString.fromInterned(" Normalize the version and build strings and return a single\n        version string using the format major.minor.build (or patchlevel).\n    "));
      var1.setlocal("_norm_version", var11);
      var3 = null;
      var1.setline(472);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?:([\\w ]+) ([\\w.]+) .*\\[.* ([\\d.]+)\\])"));
      var1.setlocal("_ver_output", var6);
      var3 = null;
      var1.setline(485);
      var9 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), new PyTuple(new PyObject[]{PyString.fromInterned("win32"), PyString.fromInterned("win16"), PyString.fromInterned("dos"), PyString.fromInterned("os2")})};
      var11 = new PyFunction(var1.f_globals, var9, _syscmd_ver$13, PyString.fromInterned(" Tries to figure out the OS version used and returns\n        a tuple (system,release,version).\n\n        It uses the \"ver\" shell command for this which is known\n        to exists on Windows, DOS and OS/2. XXX Others too ?\n\n        In case this fails, the given parameters are used as\n        defaults.\n\n    "));
      var1.setlocal("_syscmd_ver", var11);
      var3 = null;
      var1.setline(537);
      var9 = new PyObject[]{PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var9, _win32_getvalue$14, PyString.fromInterned(" Read a value for name from the registry key.\n\n        In case this fails, default is returned.\n\n    "));
      var1.setlocal("_win32_getvalue", var11);
      var3 = null;
      var1.setline(556);
      var9 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var9, win32_ver$15, PyString.fromInterned(" Get additional version information from the Windows Registry\n        and return a tuple (version,csd,ptype) referring to version\n        number, CSD level (service pack), and OS type (multi/single\n        processor).\n\n        As a hint: ptype returns 'Uniprocessor Free' on single\n        processor NT machines and 'Multiprocessor Free' on multi\n        processor machines. The 'Free' refers to the OS version being\n        free of debugging code. It could also state 'Checked' which\n        means the OS version uses debugging code, i.e. code that\n        checks arguments, ranges, etc. (Thomas Heller).\n\n        Note: this function works best with Mark Hammond's win32\n        package installed, but also on Python 2.3 and later. It\n        obviously only runs on Win32 compatible platforms.\n\n    "));
      var1.setlocal("win32_ver", var11);
      var3 = null;
      var1.setline(727);
      var9 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var9, _mac_ver_lookup$16, (PyObject)null);
      var1.setlocal("_mac_ver_lookup", var11);
      var3 = null;
      var1.setline(740);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _bcd2str$17, (PyObject)null);
      var1.setlocal("_bcd2str", var11);
      var3 = null;
      var1.setline(744);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _mac_ver_gestalt$18, PyString.fromInterned("\n        Thanks to Mark R. Levinson for mailing documentation links and\n        code examples for this function. Documentation for the\n        gestalt() API is available online at:\n\n           http://www.rgaros.nl/gestalt/\n    "));
      var1.setlocal("_mac_ver_gestalt", var11);
      var3 = null;
      var1.setline(785);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _mac_ver_xml$19, (PyObject)null);
      var1.setlocal("_mac_ver_xml", var11);
      var3 = null;
      var1.setline(807);
      var9 = new PyObject[]{PyString.fromInterned(""), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")}), PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var9, mac_ver$20, PyString.fromInterned(" Get MacOS version information and return it as tuple (release,\n        versioninfo, machine) with versioninfo being a tuple (version,\n        dev_stage, non_release_version).\n\n        Entries which cannot be determined are set to the paramter values\n        which default to ''. All tuple entries are strings.\n    "));
      var1.setlocal("mac_ver", var11);
      var3 = null;
      var1.setline(836);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _java_getprop$21, (PyObject)null);
      var1.setlocal("_java_getprop", var11);
      var3 = null;
      var1.setline(846);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _java_getenv$22, (PyObject)null);
      var1.setlocal("_java_getenv", var11);
      var3 = null;
      var1.setline(855);
      var9 = new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")}), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")})};
      var11 = new PyFunction(var1.f_globals, var9, java_ver$23, PyString.fromInterned(" Version interface for Jython.\n\n        Returns a tuple (release,vendor,vminfo,osinfo) with vminfo being\n        a tuple (vm_name,vm_release,vm_vendor) and osinfo being a\n        tuple (os_name,os_version,os_arch).\n\n        Values which cannot be determined are set to the defaults\n        given as parameters (which all default to '').\n\n    "));
      var1.setlocal("java_ver", var11);
      var3 = null;
      var1.setline(890);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, system_alias$24, PyString.fromInterned(" Returns (system,release,version) aliased to common\n        marketing names used for some systems.\n\n        It also does some reordering of the information in some cases\n        where it would otherwise cause confusion.\n\n    "));
      var1.setlocal("system_alias", var11);
      var3 = null;
      var1.setline(944);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _platform$25, PyString.fromInterned(" Helper to format the platform string in a filename\n        compatible format e.g. \"system-version-machine\".\n    "));
      var1.setlocal("_platform", var11);
      var3 = null;
      var1.setline(980);
      var9 = new PyObject[]{PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var9, _node$26, PyString.fromInterned(" Helper to determine the node name of this machine.\n    "));
      var1.setlocal("_node", var11);
      var3 = null;
      var1.setline(996);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os").__getattr__("path"), (PyObject)PyString.fromInterned("abspath")).__not__().__nonzero__()) {
         var1.setline(998);
         var9 = new PyObject[]{var1.getname("os").__getattr__("path").__getattr__("isabs"), var1.getname("os").__getattr__("path").__getattr__("join"), var1.getname("os").__getattr__("getcwd"), var1.getname("os").__getattr__("path").__getattr__("normpath")};
         var11 = new PyFunction(var1.f_globals, var9, _abspath$27, (PyObject)null);
         var1.setlocal("_abspath", var11);
         var3 = null;
      } else {
         var1.setline(1009);
         var6 = var1.getname("os").__getattr__("path").__getattr__("abspath");
         var1.setlocal("_abspath", var6);
         var3 = null;
      }

      var1.setline(1011);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _follow_symlinks$28, PyString.fromInterned(" In case filepath is a symlink, follow it until a\n        real file is reached.\n    "));
      var1.setlocal("_follow_symlinks", var11);
      var3 = null;
      var1.setline(1022);
      var9 = new PyObject[]{PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var9, _syscmd_uname$29, PyString.fromInterned(" Interface to the system's uname command.\n    "));
      var1.setlocal("_syscmd_uname", var11);
      var3 = null;
      var1.setline(1041);
      var9 = new PyObject[]{PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var9, _syscmd_file$30, PyString.fromInterned(" Interface to the system's file command.\n\n        The function uses the -b option of the file command to have it\n        ommit the filename in its output and if possible the -L option\n        to have the command follow symlinks. It returns default in\n        case the command should fail.\n\n    "));
      var1.setlocal("_syscmd_file", var11);
      var3 = null;
      var1.setline(1092);
      PyDictionary var13 = new PyDictionary(new PyObject[]{PyString.fromInterned("win32"), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("WindowsPE")}), PyString.fromInterned("win16"), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("Windows")}), PyString.fromInterned("dos"), new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("MSDOS")})});
      var1.setlocal("_default_architecture", var13);
      var3 = null;
      var1.setline(1098);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\\s,]")).__getattr__("split");
      var1.setlocal("_architecture_split", var6);
      var3 = null;
      var1.setline(1100);
      var9 = new PyObject[]{var1.getname("sys").__getattr__("executable"), PyString.fromInterned(""), PyString.fromInterned("")};
      var11 = new PyFunction(var1.f_globals, var9, architecture$31, PyString.fromInterned(" Queries the given executable (defaults to the Python interpreter\n        binary) for various architecture information.\n\n        Returns a tuple (bits,linkage) which contains information about\n        the bit architecture and the linkage format used for the\n        executable. Both values are returned as strings.\n\n        Values that cannot be determined are returned as given by the\n        parameter presets. If bits is given as '', the sizeof(pointer)\n        (or sizeof(long) on Python version < 1.5.2) is used as\n        indicator for the supported pointer size.\n\n        The function relies on the system's \"file\" command to do the\n        actual work. This is available on most if not all Unix\n        platforms. On some non-Unix platforms where the \"file\" command\n        does not exist and the executable is set to the Python interpreter\n        binary defaults from _default_architecture are used.\n\n    "));
      var1.setlocal("architecture", var11);
      var3 = null;
      var1.setline(1187);
      var6 = var1.getname("None");
      var1.setlocal("_uname_cache", var6);
      var3 = null;
      var1.setline(1189);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, uname$32, PyString.fromInterned(" Fairly portable uname interface. Returns a tuple\n        of strings (system,node,release,version,machine,processor)\n        identifying the underlying platform.\n\n        Note that unlike the os.uname function this also returns\n        possible processor information as an additional tuple entry.\n\n        Entries which cannot be determined are set to ''.\n\n\n        Jython-note:\n        platform.uname returns JVM-info.\n        For native platform info use os.uname or platform._uname.\n    "));
      var1.setlocal("uname", var11);
      var3 = null;
      var1.setline(1245);
      var6 = var1.getname("None");
      var1.setlocal("_uname_cache2", var6);
      var3 = null;
      var1.setline(1247);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, _uname$33, PyString.fromInterned(" Fairly portable uname interface. Returns a tuple\n        of strings (system,node,release,version,machine,processor)\n        identifying the underlying platform.\n\n        Note that unlike the os.uname function this also returns\n        possible processor information as an additional tuple entry.\n\n        Entries which cannot be determined are set to ''.\n\n        Jython-note:\n        _uname resembles CPython behavior for debugging purposes etc.\n    "));
      var1.setlocal("_uname", var11);
      var3 = null;
      var1.setline(1389);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, system$34, PyString.fromInterned(" Returns the system/OS name, e.g. 'Linux', 'Windows' or 'Java'.\n\n        An empty string is returned if the value cannot be determined.\n\n    "));
      var1.setlocal("system", var11);
      var3 = null;
      var1.setline(1398);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, node$35, PyString.fromInterned(" Returns the computer's network name (which may not be fully\n        qualified)\n\n        An empty string is returned if the value cannot be determined.\n\n    "));
      var1.setlocal("node", var11);
      var3 = null;
      var1.setline(1408);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, release$36, PyString.fromInterned(" Returns the system's release, e.g. '2.2.0' or 'NT'\n\n        An empty string is returned if the value cannot be determined.\n\n    "));
      var1.setlocal("release", var11);
      var3 = null;
      var1.setline(1417);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, version$37, PyString.fromInterned(" Returns the system's release version, e.g. '#3 on degas'\n\n        An empty string is returned if the value cannot be determined.\n\n    "));
      var1.setlocal("version", var11);
      var3 = null;
      var1.setline(1426);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, machine$38, PyString.fromInterned(" Returns the machine type, e.g. 'i386'\n\n        An empty string is returned if the value cannot be determined.\n\n    "));
      var1.setlocal("machine", var11);
      var3 = null;
      var1.setline(1435);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, processor$39, PyString.fromInterned(" Returns the (true) processor name, e.g. 'amdk6'\n\n        An empty string is returned if the value cannot be\n        determined. Note that many platforms do not provide this\n        information or simply return the same value as for machine(),\n        e.g.  NetBSD does this.\n\n    "));
      var1.setlocal("processor", var11);
      var3 = null;
      var1.setline(1449);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([\\w.+]+)\\s*\\(#?([^,]+),\\s*([\\w ]+),\\s*([\\w :]+)\\)\\s*\\[([^\\]]+)\\]?"));
      var1.setlocal("_sys_version_parser", var6);
      var3 = null;
      var1.setline(1454);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([\\d\\.]+)"));
      var1.setlocal("_jython_sys_version_parser", var6);
      var3 = null;
      var1.setline(1457);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IronPython\\s*([\\d\\.]+)(?: \\(([\\d\\.]+)\\))? on (.NET [\\d\\.]+)"));
      var1.setlocal("_ironpython_sys_version_parser", var6);
      var3 = null;
      var1.setline(1463);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([\\w.+]+)\\s*\\(#?([^,]+),\\s*([\\w ]+),\\s*([\\w :]+)\\)\\s*\\[PyPy [^\\]]+\\]?"));
      var1.setlocal("_pypy_sys_version_parser", var6);
      var3 = null;
      var1.setline(1468);
      var13 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_sys_version_cache", var13);
      var3 = null;
      var1.setline(1470);
      var9 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var9, _sys_version$40, PyString.fromInterned(" Returns a parsed version of Python's sys.version as tuple\n        (name, version, branch, revision, buildno, builddate, compiler)\n        referring to the Python implementation name, version, branch,\n        revision, build number, build date/time as string and the compiler\n        identification string.\n\n        Note that unlike the Python sys.version, the returned value\n        for the Python version will always include the patchlevel (it\n        defaults to '.0').\n\n        The function returns empty strings for tuple entries that\n        cannot be determined.\n\n        sys_version may be given to parse an alternative version\n        string, e.g. if the version was read from a different Python\n        interpreter.\n\n    "));
      var1.setlocal("_sys_version", var11);
      var3 = null;
      var1.setline(1569);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, python_implementation$41, PyString.fromInterned(" Returns a string identifying the Python implementation.\n\n        Currently, the following implementations are identified:\n          'CPython' (C implementation of Python),\n          'IronPython' (.NET implementation of Python),\n          'Jython' (Java implementation of Python),\n          'PyPy' (Python implementation of Python).\n\n    "));
      var1.setlocal("python_implementation", var11);
      var3 = null;
      var1.setline(1582);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, python_version$42, PyString.fromInterned(" Returns the Python version as string 'major.minor.patchlevel'\n\n        Note that unlike the Python sys.version, the returned value\n        will always include the patchlevel (it defaults to 0).\n\n    "));
      var1.setlocal("python_version", var11);
      var3 = null;
      var1.setline(1592);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, python_version_tuple$43, PyString.fromInterned(" Returns the Python version as tuple (major, minor, patchlevel)\n        of strings.\n\n        Note that unlike the Python sys.version, the returned value\n        will always include the patchlevel (it defaults to 0).\n\n    "));
      var1.setlocal("python_version_tuple", var11);
      var3 = null;
      var1.setline(1603);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, python_branch$44, PyString.fromInterned(" Returns a string identifying the Python implementation\n        branch.\n\n        For CPython this is the Subversion branch from which the\n        Python binary was built.\n\n        If not available, an empty string is returned.\n\n    "));
      var1.setlocal("python_branch", var11);
      var3 = null;
      var1.setline(1617);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, python_revision$45, PyString.fromInterned(" Returns a string identifying the Python implementation\n        revision.\n\n        For CPython this is the Subversion revision from which the\n        Python binary was built.\n\n        If not available, an empty string is returned.\n\n    "));
      var1.setlocal("python_revision", var11);
      var3 = null;
      var1.setline(1630);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, python_build$46, PyString.fromInterned(" Returns a tuple (buildno, builddate) stating the Python\n        build number and date as strings.\n\n    "));
      var1.setlocal("python_build", var11);
      var3 = null;
      var1.setline(1638);
      var9 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var9, python_compiler$47, PyString.fromInterned(" Returns a string identifying the compiler used for compiling\n        Python.\n\n    "));
      var1.setlocal("python_compiler", var11);
      var3 = null;
      var1.setline(1648);
      var13 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_platform_cache", var13);
      var3 = null;
      var1.setline(1650);
      var9 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var11 = new PyFunction(var1.f_globals, var9, platform$48, PyString.fromInterned(" Returns a single string identifying the underlying platform\n        with as much useful information as possible (but no more :).\n\n        The output is intended to be human readable rather than\n        machine parseable. It may look different on different\n        platforms and this is intended.\n\n        If \"aliased\" is true, the function will use aliases for\n        various platforms that report system names which differ from\n        their common names, e.g. SunOS will be reported as\n        Solaris. The system_alias() function is used to implement\n        this.\n\n        Setting terse to true causes the function to return only the\n        absolute minimum information needed to identify the platform.\n\n    "));
      var1.setlocal("platform", var11);
      var3 = null;
      var1.setline(1732);
      var6 = var1.getname("__name__");
      var10000 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1734);
         var3 = PyString.fromInterned("terse");
         var10000 = var3._in(var1.getname("sys").__getattr__("argv"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = PyString.fromInterned("--terse");
            var10000 = var3._in(var1.getname("sys").__getattr__("argv"));
            var3 = null;
         }

         var6 = var10000;
         var1.setlocal("terse", var6);
         var3 = null;
         var1.setline(1735);
         var3 = PyString.fromInterned("nonaliased");
         var10000 = var3._in(var1.getname("sys").__getattr__("argv"));
         var3 = null;
         var10000 = var10000.__not__();
         if (var10000.__nonzero__()) {
            var3 = PyString.fromInterned("--nonaliased");
            var10000 = var3._in(var1.getname("sys").__getattr__("argv"));
            var3 = null;
            var10000 = var10000.__not__();
         }

         var6 = var10000;
         var1.setlocal("aliased", var6);
         var3 = null;
         var1.setline(1736);
         Py.println(var1.getname("platform").__call__(var2, var1.getname("aliased"), var1.getname("terse")));
         var1.setline(1737);
         var1.getname("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject libc_ver$1(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyString.fromInterned(" Tries to determine the libc version that the file executable\n        (which defaults to the Python interpreter) is linked against.\n\n        Returns a tuple of strings (lib,version) which default to the\n        given parameters in case the lookup fails.\n\n        Note that the function has intimate knowledge of how different\n        libc versions add symbols to the executable and thus is probably\n        only useable for executables compiled using gcc.\n\n        The file is read and scanned in chunks of chunksize bytes.\n\n    ");
      var1.setline(161);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path"), (PyObject)PyString.fromInterned("realpath")).__nonzero__()) {
         var1.setline(165);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("realpath").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(166);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(167);
      var3 = var1.getlocal(4).__getattr__("read").__call__(var2, var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(168);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(6, var6);
      var3 = null;

      while(true) {
         var1.setline(169);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(170);
         var3 = var1.getglobal("_libc_search").__getattr__("search").__call__(var2, var1.getlocal(5), var1.getlocal(6));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(171);
         if (var1.getlocal(7).__not__().__nonzero__()) {
            var1.setline(172);
            var3 = var1.getlocal(4).__getattr__("read").__call__(var2, var1.getlocal(3));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(173);
            if (var1.getlocal(5).__not__().__nonzero__()) {
               break;
            }

            var1.setline(175);
            var6 = Py.newInteger(0);
            var1.setlocal(6, var6);
            var3 = null;
         } else {
            var1.setline(177);
            var3 = var1.getlocal(7).__getattr__("groups").__call__(var2);
            PyObject[] var4 = Py.unpackSequence(var3, 6);
            PyObject var5 = var4[0];
            var1.setlocal(8, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(9, var5);
            var5 = null;
            var5 = var4[2];
            var1.setlocal(10, var5);
            var5 = null;
            var5 = var4[3];
            var1.setlocal(11, var5);
            var5 = null;
            var5 = var4[4];
            var1.setlocal(12, var5);
            var5 = null;
            var5 = var4[5];
            var1.setlocal(13, var5);
            var5 = null;
            var3 = null;
            var1.setline(178);
            PyObject var10000 = var1.getlocal(8);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(1).__not__();
            }

            PyString var7;
            if (var10000.__nonzero__()) {
               var1.setline(179);
               var7 = PyString.fromInterned("libc");
               var1.setlocal(1, var7);
               var3 = null;
            } else {
               var1.setline(180);
               if (var1.getlocal(9).__nonzero__()) {
                  var1.setline(181);
                  var3 = var1.getlocal(1);
                  var10000 = var3._ne(PyString.fromInterned("glibc"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(182);
                     var7 = PyString.fromInterned("glibc");
                     var1.setlocal(1, var7);
                     var3 = null;
                     var1.setline(183);
                     var3 = var1.getlocal(10);
                     var1.setlocal(2, var3);
                     var3 = null;
                  } else {
                     var1.setline(184);
                     var3 = var1.getlocal(10);
                     var10000 = var3._gt(var1.getlocal(2));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(185);
                        var3 = var1.getlocal(10);
                        var1.setlocal(2, var3);
                        var3 = null;
                     }
                  }
               } else {
                  var1.setline(186);
                  if (var1.getlocal(11).__nonzero__()) {
                     var1.setline(187);
                     var3 = var1.getlocal(1);
                     var10000 = var3._ne(PyString.fromInterned("glibc"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(188);
                        var7 = PyString.fromInterned("libc");
                        var1.setlocal(1, var7);
                        var3 = null;
                        var1.setline(189);
                        var10000 = var1.getlocal(13);
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(13);
                           var10000 = var3._gt(var1.getlocal(2));
                           var3 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(190);
                           var3 = var1.getlocal(13);
                           var1.setlocal(2, var3);
                           var3 = null;
                        }

                        var1.setline(191);
                        var10000 = var1.getlocal(12);
                        if (var10000.__nonzero__()) {
                           var3 = var1.getlocal(2).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(12)).__neg__(), (PyObject)null, (PyObject)null);
                           var10000 = var3._ne(var1.getlocal(12));
                           var3 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(192);
                           var3 = var1.getlocal(2)._add(var1.getlocal(12));
                           var1.setlocal(2, var3);
                           var3 = null;
                        }
                     }
                  }
               }
            }

            var1.setline(193);
            var3 = var1.getlocal(7).__getattr__("end").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
         }
      }

      var1.setline(194);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.setline(195);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject _dist_try_harder$2(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyString.fromInterned(" Tries some special tricks to get the distribution\n        information in case the default method fails.\n\n        Currently supports older SuSE Linux, Caldera OpenLinux and\n        Slackware Linux distributions.\n\n    ");
      var1.setline(206);
      PyObject var10000;
      PyTuple var3;
      PyObject var4;
      PyObject var5;
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/var/adm/inst-log/info")).__nonzero__()) {
         var1.setline(208);
         PyObject var8 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/var/adm/inst-log/info")).__getattr__("readlines").__call__(var2);
         var1.setlocal(3, var8);
         var3 = null;
         var1.setline(209);
         PyString var9 = PyString.fromInterned("SuSE");
         var1.setlocal(0, var9);
         var3 = null;
         var1.setline(210);
         var8 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(210);
            var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(221);
               var3 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(4, var4);
            var1.setline(211);
            var5 = var1.getglobal("string").__getattr__("split").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(212);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            var10000 = var5._eq(Py.newInteger(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(213);
               var5 = var1.getlocal(5);
               PyObject[] var11 = Py.unpackSequence(var5, 2);
               PyObject var7 = var11[0];
               var1.setlocal(6, var7);
               var7 = null;
               var7 = var11[1];
               var1.setlocal(7, var7);
               var7 = null;
               var5 = null;
               var1.setline(216);
               var5 = var1.getlocal(6);
               var10000 = var5._eq(PyString.fromInterned("MIN_DIST_VERSION"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(217);
                  var5 = var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(7));
                  var1.setlocal(1, var5);
                  var5 = null;
               } else {
                  var1.setline(218);
                  var5 = var1.getlocal(6);
                  var10000 = var5._eq(PyString.fromInterned("DIST_IDENT"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(219);
                     var5 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("-"));
                     var1.setlocal(8, var5);
                     var5 = null;
                     var1.setline(220);
                     var5 = var1.getlocal(8).__getitem__(Py.newInteger(2));
                     var1.setlocal(2, var5);
                     var5 = null;
                  }
               }
            }
         }
      } else {
         var1.setline(223);
         PyObject var6;
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/etc/.installed")).__nonzero__()) {
            var1.setline(225);
            var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/etc/.installed")).__getattr__("readlines").__call__(var2);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(226);
            var4 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(226);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(4, var5);
               var1.setline(227);
               var6 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("-"));
               var1.setlocal(9, var6);
               var6 = null;
               var1.setline(228);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
               var10000 = var6._ge(Py.newInteger(2));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var6 = var1.getlocal(9).__getitem__(Py.newInteger(0));
                  var10000 = var6._eq(PyString.fromInterned("OpenLinux"));
                  var6 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(231);
                  var3 = new PyTuple(new PyObject[]{PyString.fromInterned("OpenLinux"), var1.getlocal(9).__getitem__(Py.newInteger(1)), var1.getlocal(2)});
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }

         var1.setline(233);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/usr/lib/setup")).__nonzero__()) {
            var1.setline(235);
            var4 = var1.getglobal("os").__getattr__("listdir").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/usr/lib/setup"));
            var1.setlocal(10, var4);
            var4 = null;
            var1.setline(236);
            var4 = var1.getglobal("range").__call__((ThreadState)var2, var1.getglobal("len").__call__(var2, var1.getlocal(10))._sub(Py.newInteger(1)), (PyObject)Py.newInteger(-1), (PyObject)Py.newInteger(-1)).__iter__();

            while(true) {
               var1.setline(236);
               var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(239);
                  if (var1.getlocal(10).__nonzero__()) {
                     var1.setline(240);
                     var1.getlocal(10).__getattr__("sort").__call__(var2);
                     var1.setline(241);
                     PyString var10 = PyString.fromInterned("slackware");
                     var1.setlocal(0, var10);
                     var4 = null;
                     var1.setline(242);
                     var4 = var1.getlocal(10).__getitem__(Py.newInteger(-1)).__getslice__(Py.newInteger(14), (PyObject)null, (PyObject)null);
                     var1.setlocal(1, var4);
                     var4 = null;
                     var1.setline(243);
                     var3 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
                     var1.f_lasti = -1;
                     return var3;
                  }
                  break;
               }

               var1.setlocal(11, var5);
               var1.setline(237);
               var6 = var1.getlocal(10).__getitem__(var1.getlocal(11)).__getslice__((PyObject)null, Py.newInteger(14), (PyObject)null);
               var10000 = var6._ne(PyString.fromInterned("slack-version-"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(238);
                  var1.getlocal(10).__delitem__(var1.getlocal(11));
               }
            }
         }

         var1.setline(245);
         var3 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _parse_release_file$3(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(273);
      var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(276);
      PyObject var5 = var1.getglobal("_lsb_release_version").__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(277);
      var5 = var1.getlocal(3);
      PyObject var10000 = var5._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(279);
         var5 = var1.getglobal("tuple").__call__(var2, var1.getlocal(3).__getattr__("groups").__call__(var2));
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(282);
         PyObject var4 = var1.getglobal("_release_version").__getattr__("match").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(283);
         var4 = var1.getlocal(3);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(284);
            var5 = var1.getglobal("tuple").__call__(var2, var1.getlocal(3).__getattr__("groups").__call__(var2));
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(287);
            var4 = var1.getglobal("string").__getattr__("split").__call__(var2, var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(0)));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(288);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(289);
               var4 = var1.getlocal(4).__getitem__(Py.newInteger(0));
               var1.setlocal(1, var4);
               var4 = null;
               var1.setline(290);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               var10000 = var4._gt(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(291);
                  var4 = var1.getlocal(4).__getitem__(Py.newInteger(1));
                  var1.setlocal(2, var4);
                  var4 = null;
               }
            }

            var1.setline(292);
            PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var6;
         }
      }
   }

   public PyObject linux_distribution$4(PyFrame var1, ThreadState var2) {
      var1.setline(317);
      PyString.fromInterned(" Tries to determine the name of the Linux OS distribution name.\n\n        The function first looks for a distribution release file in\n        /etc and then reverts to _dist_try_harder() in case no\n        suitable files are found.\n\n        supported_dists may be given to define the set of Linux\n        distributions to look for. It defaults to a list of currently\n        supported Linux distributions identified by their release file\n        name.\n\n        If full_distribution_name is true (default), the full\n        distribution read from the OS is returned. Otherwise the short\n        name taken from supported_dists is used.\n\n        Returns a tuple (distname,version,id) which default to the\n        args given as parameters.\n\n    ");

      PyException var3;
      PyTuple var4;
      PyObject var10;
      try {
         var1.setline(319);
         var10 = var1.getglobal("os").__getattr__("listdir").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/etc"));
         var1.setlocal(5, var10);
         var3 = null;
      } catch (Throwable var9) {
         var3 = Py.setException(var9, var1);
         if (var3.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(322);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(323);
      var1.getlocal(5).__getattr__("sort").__call__(var2);
      var1.setline(324);
      var10 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(324);
         PyObject var5 = var10.__iternext__();
         if (var5 == null) {
            var1.setline(332);
            PyObject var11 = var1.getglobal("_dist_try_harder").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var11;
         }

         var1.setlocal(6, var5);
         var1.setline(325);
         PyObject var6 = var1.getglobal("_release_filename").__getattr__("match").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(326);
         var6 = var1.getlocal(7);
         PyObject var10000 = var6._isnot(var1.getglobal("None"));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(327);
            var6 = var1.getlocal(7).__getattr__("groups").__call__(var2);
            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(8, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(9, var8);
            var8 = null;
            var6 = null;
            var1.setline(328);
            var6 = var1.getlocal(8);
            var10000 = var6._in(var1.getlocal(3));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(329);
               var6 = var1.getlocal(8);
               var1.setlocal(0, var6);
               var6 = null;
               var1.setline(335);
               var10 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/etc/")._add(var1.getlocal(6)), (PyObject)PyString.fromInterned("r"));
               var1.setlocal(10, var10);
               var3 = null;
               var1.setline(336);
               var10 = var1.getlocal(10).__getattr__("readline").__call__(var2);
               var1.setlocal(11, var10);
               var3 = null;
               var1.setline(337);
               var1.getlocal(10).__getattr__("close").__call__(var2);
               var1.setline(338);
               var10 = var1.getglobal("_parse_release_file").__call__(var2, var1.getlocal(11));
               PyObject[] var12 = Py.unpackSequence(var10, 3);
               var6 = var12[0];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var12[1];
               var1.setlocal(12, var6);
               var6 = null;
               var6 = var12[2];
               var1.setlocal(13, var6);
               var6 = null;
               var3 = null;
               var1.setline(340);
               var10000 = var1.getlocal(8);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(4);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(341);
                  var10 = var1.getlocal(8);
                  var1.setlocal(0, var10);
                  var3 = null;
               }

               var1.setline(342);
               if (var1.getlocal(12).__nonzero__()) {
                  var1.setline(343);
                  var10 = var1.getlocal(12);
                  var1.setlocal(1, var10);
                  var3 = null;
               }

               var1.setline(344);
               if (var1.getlocal(13).__nonzero__()) {
                  var1.setline(345);
                  var10 = var1.getlocal(13);
                  var1.setlocal(2, var10);
                  var3 = null;
               }

               var1.setline(346);
               var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
               var1.f_lasti = -1;
               return var4;
            }
         }
      }
   }

   public PyObject dist$5(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      PyString.fromInterned(" Tries to determine the name of the Linux OS distribution name.\n\n        The function first looks for a distribution release file in\n        /etc and then reverts to _dist_try_harder() in case no\n        suitable files are found.\n\n        Returns a tuple (distname,version,id) which default to the\n        args given as parameters.\n\n    ");
      var1.setline(364);
      PyObject var10000 = var1.getglobal("linux_distribution");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), Py.newInteger(0)};
      String[] var4 = new String[]{"supported_dists", "full_distribution_name"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _popen$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" Fairly portable (alternative) popen implementation.\n\n        This is mostly needed in case os.popen() is not available, or\n        doesn't work as advertised, e.g. in Win9X GUI programs like\n        PythonWin or IDLE.\n\n        Writing to the pipe is currently not supported.\n\n    "));
      var1.setline(378);
      PyString.fromInterned(" Fairly portable (alternative) popen implementation.\n\n        This is mostly needed in case os.popen() is not available, or\n        doesn't work as advertised, e.g. in Win9X GUI programs like\n        PythonWin or IDLE.\n\n        Writing to the pipe is currently not supported.\n\n    ");
      var1.setline(379);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal("tmpfile", var3);
      var3 = null;
      var1.setline(380);
      PyObject var4 = var1.getname("None");
      var1.setlocal("pipe", var4);
      var3 = null;
      var1.setline(381);
      var4 = var1.getname("None");
      var1.setlocal("bufsize", var4);
      var3 = null;
      var1.setline(382);
      var3 = PyString.fromInterned("r");
      var1.setlocal("mode", var3);
      var3 = null;
      var1.setline(384);
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("r"), var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(395);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, read$8, (PyObject)null);
      var1.setlocal("read", var6);
      var3 = null;
      var1.setline(399);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, readlines$9, (PyObject)null);
      var1.setlocal("readlines", var6);
      var3 = null;
      var1.setline(404);
      var5 = new PyObject[]{var1.getname("os").__getattr__("unlink"), var1.getname("os").__getattr__("error")};
      var6 = new PyFunction(var1.f_globals, var5, close$10, (PyObject)null);
      var1.setlocal("close", var6);
      var3 = null;
      var1.setline(420);
      var4 = var1.getname("close");
      var1.setlocal("__del__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._ne(PyString.fromInterned("r"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(387);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("popen()-emulation only supports read mode"));
      } else {
         var1.setline(388);
         var3 = imp.importOne("tempfile", var1, -1);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(389);
         var3 = var1.getlocal(4).__getattr__("mktemp").__call__(var2);
         var1.getlocal(0).__setattr__("tmpfile", var3);
         var1.setlocal(5, var3);
         var1.setline(390);
         var1.getglobal("os").__getattr__("system").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned(" > %s")._mod(var1.getlocal(5))));
         var1.setline(391);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("rb"));
         var1.getlocal(0).__setattr__("pipe", var3);
         var3 = null;
         var1.setline(392);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("bufsize", var3);
         var3 = null;
         var1.setline(393);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("mode", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject read$8(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      PyObject var3 = var1.getlocal(0).__getattr__("pipe").__getattr__("read").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readlines$9(PyFrame var1, ThreadState var2) {
      var1.setline(401);
      PyObject var3 = var1.getlocal(0).__getattr__("bufsize");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(402);
         var3 = var1.getlocal(0).__getattr__("pipe").__getattr__("readlines").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close$10(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("pipe").__nonzero__()) {
         var1.setline(409);
         var3 = var1.getlocal(0).__getattr__("pipe").__getattr__("close").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(411);
         PyInteger var5 = Py.newInteger(255);
         var1.setlocal(3, var5);
         var3 = null;
      }

      var1.setline(412);
      if (var1.getlocal(0).__getattr__("tmpfile").__nonzero__()) {
         try {
            var1.setline(414);
            var1.getlocal(1).__call__(var2, var1.getlocal(0).__getattr__("tmpfile"));
         } catch (Throwable var4) {
            PyException var6 = Py.setException(var4, var1);
            if (!var6.match(var1.getlocal(2))) {
               throw var6;
            }

            var1.setline(416);
         }
      }

      var1.setline(417);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject popen$11(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      PyString.fromInterned(" Portable popen() interface.\n    ");
      var1.setline(428);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(429);
      var3 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("OS"), (PyObject)PyString.fromInterned(""));
      PyObject var10000 = var3._eq(PyString.fromInterned("Windows_NT"));
      var3 = null;
      PyObject var4;
      PyException var7;
      if (var10000.__nonzero__()) {
         label56: {
            try {
               var1.setline(433);
               var3 = imp.importOne("win32pipe", var1, -1);
               var1.setlocal(4, var3);
               var3 = null;
            } catch (Throwable var6) {
               var7 = Py.setException(var6, var1);
               if (var7.match(var1.getglobal("ImportError"))) {
                  var1.setline(435);
                  break label56;
               }

               throw var7;
            }

            var1.setline(437);
            var4 = var1.getlocal(4).__getattr__("popen");
            var1.setlocal(3, var4);
            var4 = null;
         }
      }

      var1.setline(438);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(439);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("popen")).__nonzero__()) {
            var1.setline(440);
            var3 = var1.getglobal("os").__getattr__("popen");
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(443);
            var3 = var1.getglobal("sys").__getattr__("platform");
            var10000 = var3._eq(PyString.fromInterned("win32"));
            var3 = null;
            if (var10000.__nonzero__()) {
               try {
                  var1.setline(445);
                  var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
               } catch (Throwable var5) {
                  var7 = Py.setException(var5, var1);
                  if (!var7.match(var1.getglobal("os").__getattr__("error"))) {
                     throw var7;
                  }

                  var1.setline(447);
                  var4 = var1.getglobal("_popen");
                  var1.setlocal(3, var4);
                  var4 = null;
               }
            }
         } else {
            var1.setline(449);
            var3 = var1.getglobal("_popen");
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(450);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(451);
         var3 = var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(453);
         var3 = var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _norm_version$12(PyFrame var1, ThreadState var2) {
      var1.setline(459);
      PyString.fromInterned(" Normalize the version and build strings and return a single\n        version string using the format major.minor.build (or patchlevel).\n    ");
      var1.setline(460);
      PyObject var3 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("."));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(461);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(462);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1));
      }

      label20: {
         PyObject var4;
         try {
            var1.setline(464);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(2));
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("ValueError"))) {
               var1.setline(466);
               var4 = var1.getlocal(2);
               var1.setlocal(4, var4);
               var4 = null;
               break label20;
            }

            throw var6;
         }

         var1.setline(468);
         var4 = var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(3));
         var1.setlocal(4, var4);
         var4 = null;
      }

      var1.setline(469);
      var3 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null), (PyObject)PyString.fromInterned("."));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(470);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _syscmd_ver$13(PyFrame var1, ThreadState var2) {
      var1.setline(498);
      PyString.fromInterned(" Tries to figure out the OS version used and returns\n        a tuple (system,release,version).\n\n        It uses the \"ver\" shell command for this which is known\n        to exists on Windows, DOS and OS/2. XXX Others too ?\n\n        In case this fails, the given parameters are used as\n        defaults.\n\n    ");
      var1.setline(499);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._notin(var1.getlocal(3));
      var3 = null;
      PyTuple var9;
      if (var10000.__nonzero__()) {
         var1.setline(500);
         var9 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var9;
      } else {
         var1.setline(503);
         PyObject var4 = (new PyTuple(new PyObject[]{PyString.fromInterned("ver"), PyString.fromInterned("command /c ver"), PyString.fromInterned("cmd /c ver")})).__iter__();

         while(true) {
            var1.setline(503);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(520);
               var9 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
               var1.f_lasti = -1;
               return var9;
            }

            var1.setlocal(4, var5);

            PyException var6;
            PyObject var11;
            try {
               var1.setline(505);
               var11 = var1.getglobal("popen").__call__(var2, var1.getlocal(4));
               var1.setlocal(5, var11);
               var6 = null;
               var1.setline(506);
               var11 = var1.getlocal(5).__getattr__("read").__call__(var2);
               var1.setlocal(6, var11);
               var6 = null;
               var1.setline(507);
               if (var1.getlocal(5).__getattr__("close").__call__(var2).__nonzero__()) {
                  var1.setline(508);
                  throw Py.makeException(var1.getglobal("os").__getattr__("error"), PyString.fromInterned("command failed"));
               }
            } catch (Throwable var8) {
               var6 = Py.setException(var8, var1);
               PyObject var7;
               if (var6.match(var1.getglobal("os").__getattr__("error"))) {
                  var7 = var6.value;
                  var1.setlocal(7, var7);
                  var7 = null;
                  continue;
               }

               if (!var6.match(var1.getglobal("IOError"))) {
                  throw var6;
               }

               var7 = var6.value;
               var1.setlocal(7, var7);
               var7 = null;
               continue;
            }

            var1.setline(523);
            var4 = var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(524);
            var4 = var1.getglobal("_ver_output").__getattr__("match").__call__(var2, var1.getlocal(6));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(525);
            var4 = var1.getlocal(8);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(526);
               var4 = var1.getlocal(8).__getattr__("groups").__call__(var2);
               PyObject[] var10 = Py.unpackSequence(var4, 3);
               var11 = var10[0];
               var1.setlocal(0, var11);
               var6 = null;
               var11 = var10[1];
               var1.setlocal(1, var11);
               var6 = null;
               var11 = var10[2];
               var1.setlocal(2, var11);
               var6 = null;
               var4 = null;
               var1.setline(528);
               var4 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
               var10000 = var4._eq(PyString.fromInterned("."));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(529);
                  var4 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(1, var4);
                  var4 = null;
               }

               var1.setline(530);
               var4 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
               var10000 = var4._eq(PyString.fromInterned("."));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(531);
                  var4 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(2, var4);
                  var4 = null;
               }

               var1.setline(534);
               var4 = var1.getglobal("_norm_version").__call__(var2, var1.getlocal(2));
               var1.setlocal(2, var4);
               var4 = null;
            }

            var1.setline(535);
            var9 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var9;
         }
      }
   }

   public PyObject _win32_getvalue$14(PyFrame var1, ThreadState var2) {
      var1.setline(543);
      PyString.fromInterned(" Read a value for name from the registry key.\n\n        In case this fails, default is returned.\n\n    ");

      PyObject var4;
      try {
         var1.setline(546);
         String[] var7 = new String[]{"RegQueryValueEx"};
         PyObject[] var8 = imp.importFrom("win32api", var7, var1, -1);
         var4 = var8[0];
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var6) {
         PyException var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         var1.setline(549);
         var4 = imp.importOne("_winreg", var1, -1);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(550);
         var4 = var1.getlocal(4).__getattr__("QueryValueEx");
         var1.setlocal(3, var4);
         var4 = null;
      }

      PyObject var9;
      try {
         var1.setline(552);
         var9 = var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var9;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(554);
         var9 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var9;
      }
   }

   public PyObject win32_ver$15(PyFrame var1, ThreadState var2) {
      var1.setline(574);
      PyString.fromInterned(" Get additional version information from the Windows Registry\n        and return a tuple (version,csd,ptype) referring to version\n        number, CSD level (service pack), and OS type (multi/single\n        processor).\n\n        As a hint: ptype returns 'Uniprocessor Free' on single\n        processor NT machines and 'Multiprocessor Free' on multi\n        processor machines. The 'Free' refers to the OS version being\n        free of debugging code. It could also state 'Checked' which\n        means the OS version uses debugging code, i.e. code that\n        checks arguments, ranges, etc. (Thomas Heller).\n\n        Note: this function works best with Mark Hammond's win32\n        package installed, but also on Python 2.3 and later. It\n        obviously only runs on Win32 compatible platforms.\n\n    ");

      PyException var3;
      PyTuple var4;
      PyObject var6;
      PyObject var11;
      PyObject var14;
      PyObject var10000;
      try {
         var1.setline(585);
         var11 = imp.importOne("win32api", var1, -1);
         var1.setlocal(4, var11);
         var3 = null;
         var1.setline(586);
         String[] var12 = new String[]{"RegQueryValueEx", "RegOpenKeyEx", "RegCloseKey", "GetVersionEx"};
         PyObject[] var13 = imp.importFrom("win32api", var12, var1, -1);
         var14 = var13[0];
         var1.setlocal(5, var14);
         var4 = null;
         var14 = var13[1];
         var1.setlocal(6, var14);
         var4 = null;
         var14 = var13[2];
         var1.setlocal(7, var14);
         var4 = null;
         var14 = var13[3];
         var1.setlocal(8, var14);
         var4 = null;
         var1.setline(588);
         var12 = new String[]{"HKEY_LOCAL_MACHINE", "VER_PLATFORM_WIN32_NT", "VER_PLATFORM_WIN32_WINDOWS", "VER_NT_WORKSTATION"};
         var13 = imp.importFrom("win32con", var12, var1, -1);
         var14 = var13[0];
         var1.setlocal(9, var14);
         var4 = null;
         var14 = var13[1];
         var1.setlocal(10, var14);
         var4 = null;
         var14 = var13[2];
         var1.setlocal(11, var14);
         var4 = null;
         var14 = var13[3];
         var1.setlocal(12, var14);
         var4 = null;
      } catch (Throwable var10) {
         var3 = Py.setException(var10, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         var1.setline(591);
         if (var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
            var1.setline(592);
            var14 = var1.getglobal("os").__getattr__("_name");
            var10000 = var14._eq(PyString.fromInterned("nt"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(593);
               var14 = var1.getglobal("os").__getattr__("uname").__call__(var2);
               var1.setlocal(13, var14);
               var4 = null;
               var1.setline(594);
               var4 = new PyTuple(new PyObject[]{var1.getlocal(13).__getitem__(Py.newInteger(2)), var1.getlocal(13).__getitem__(Py.newInteger(3)), var1.getlocal(2), var1.getlocal(3)});
               var1.f_lasti = -1;
               return var4;
            }

            var1.setline(596);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var4;
         }

         try {
            var1.setline(599);
            var1.getglobal("sys").__getattr__("getwindowsversion");
         } catch (Throwable var8) {
            PyException var5 = Py.setException(var8, var1);
            if (var5.match(var1.getglobal("AttributeError"))) {
               var1.setline(602);
               var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
               var1.f_lasti = -1;
               return var4;
            }

            throw var5;
         }

         var1.setline(606);
         var6 = imp.importOne("_winreg", var1, -1);
         var1.setlocal(14, var6);
         var6 = null;
         var1.setline(607);
         var6 = var1.getglobal("sys").__getattr__("getwindowsversion");
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(608);
         var6 = var1.getlocal(14).__getattr__("QueryValueEx");
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(609);
         var6 = var1.getlocal(14).__getattr__("OpenKeyEx");
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(610);
         var6 = var1.getlocal(14).__getattr__("CloseKey");
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(611);
         var6 = var1.getlocal(14).__getattr__("HKEY_LOCAL_MACHINE");
         var1.setlocal(9, var6);
         var6 = null;
         var1.setline(612);
         PyInteger var16 = Py.newInteger(1);
         var1.setlocal(11, var16);
         var6 = null;
         var1.setline(613);
         var16 = Py.newInteger(2);
         var1.setlocal(10, var16);
         var6 = null;
         var1.setline(614);
         var16 = Py.newInteger(1);
         var1.setlocal(12, var16);
         var6 = null;
         var1.setline(615);
         var16 = Py.newInteger(3);
         var1.setlocal(15, var16);
         var6 = null;
         var1.setline(616);
         var16 = Py.newInteger(1);
         var1.setlocal(16, var16);
         var6 = null;
      }

      var1.setline(619);
      var11 = var1.getlocal(8).__call__(var2);
      var1.setlocal(17, var11);
      var3 = null;
      var1.setline(620);
      var11 = var1.getlocal(17);
      PyObject[] var15 = Py.unpackSequence(var11, 5);
      var6 = var15[0];
      var1.setlocal(18, var6);
      var6 = null;
      var6 = var15[1];
      var1.setlocal(19, var6);
      var6 = null;
      var6 = var15[2];
      var1.setlocal(20, var6);
      var6 = null;
      var6 = var15[3];
      var1.setlocal(21, var6);
      var6 = null;
      var6 = var15[4];
      var1.setlocal(2, var6);
      var6 = null;
      var3 = null;
      var1.setline(621);
      var11 = PyString.fromInterned("%i.%i.%i")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(19), var1.getlocal(20)._and(Py.newInteger(65535))}));
      var1.setlocal(1, var11);
      var3 = null;
      var1.setline(622);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(17), (PyObject)PyString.fromInterned("service_pack")).__nonzero__()) {
         var1.setline(623);
         var11 = var1.getlocal(17).__getattr__("service_pack");
         var10000 = var11._ne(PyString.fromInterned(""));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(624);
            var11 = PyString.fromInterned("SP%s")._mod(var1.getlocal(17).__getattr__("service_pack_major"));
            var1.setlocal(2, var11);
            var3 = null;
         }
      } else {
         var1.setline(626);
         var11 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(13), (PyObject)null);
         var10000 = var11._eq(PyString.fromInterned("Service Pack "));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(627);
            var11 = PyString.fromInterned("SP")._add(var1.getlocal(2).__getslice__(Py.newInteger(13), (PyObject)null, (PyObject)null));
            var1.setlocal(2, var11);
            var3 = null;
         }
      }

      var1.setline(629);
      var11 = var1.getlocal(21);
      var10000 = var11._eq(var1.getlocal(11));
      var3 = null;
      PyString var17;
      if (var10000.__nonzero__()) {
         var1.setline(630);
         var17 = PyString.fromInterned("SOFTWARE\\Microsoft\\Windows\\CurrentVersion");
         var1.setlocal(22, var17);
         var3 = null;
         var1.setline(632);
         var11 = var1.getlocal(18);
         var10000 = var11._eq(Py.newInteger(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(633);
            var11 = var1.getlocal(19);
            var10000 = var11._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(634);
               var17 = PyString.fromInterned("95");
               var1.setlocal(0, var17);
               var3 = null;
            } else {
               var1.setline(635);
               var11 = var1.getlocal(19);
               var10000 = var11._eq(Py.newInteger(10));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(636);
                  var17 = PyString.fromInterned("98");
                  var1.setlocal(0, var17);
                  var3 = null;
               } else {
                  var1.setline(637);
                  var11 = var1.getlocal(19);
                  var10000 = var11._eq(Py.newInteger(90));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(638);
                     var17 = PyString.fromInterned("Me");
                     var1.setlocal(0, var17);
                     var3 = null;
                  } else {
                     var1.setline(640);
                     var17 = PyString.fromInterned("postMe");
                     var1.setlocal(0, var17);
                     var3 = null;
                  }
               }
            }
         } else {
            var1.setline(641);
            var11 = var1.getlocal(18);
            var10000 = var11._eq(Py.newInteger(5));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(642);
               var17 = PyString.fromInterned("2000");
               var1.setlocal(0, var17);
               var3 = null;
            }
         }
      } else {
         var1.setline(644);
         var11 = var1.getlocal(21);
         var10000 = var11._eq(var1.getlocal(10));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(694);
            if (var1.getlocal(0).__not__().__nonzero__()) {
               var1.setline(696);
               var11 = PyString.fromInterned("%i.%i")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(19)}));
               var1.setlocal(0, var11);
               var3 = null;
            }

            var1.setline(697);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(645);
         var17 = PyString.fromInterned("SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion");
         var1.setlocal(22, var17);
         var3 = null;
         var1.setline(646);
         var11 = var1.getlocal(18);
         var10000 = var11._le(Py.newInteger(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(647);
            var17 = PyString.fromInterned("NT");
            var1.setlocal(0, var17);
            var3 = null;
         } else {
            var1.setline(648);
            var11 = var1.getlocal(18);
            var10000 = var11._eq(Py.newInteger(5));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(649);
               var11 = var1.getlocal(19);
               var10000 = var11._eq(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(650);
                  var17 = PyString.fromInterned("2000");
                  var1.setlocal(0, var17);
                  var3 = null;
               } else {
                  var1.setline(651);
                  var11 = var1.getlocal(19);
                  var10000 = var11._eq(Py.newInteger(1));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(652);
                     var17 = PyString.fromInterned("XP");
                     var1.setlocal(0, var17);
                     var3 = null;
                  } else {
                     var1.setline(653);
                     var11 = var1.getlocal(19);
                     var10000 = var11._eq(Py.newInteger(2));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(654);
                        var17 = PyString.fromInterned("2003Server");
                        var1.setlocal(0, var17);
                        var3 = null;
                     } else {
                        var1.setline(656);
                        var17 = PyString.fromInterned("post2003");
                        var1.setlocal(0, var17);
                        var3 = null;
                     }
                  }
               }
            } else {
               var1.setline(657);
               var11 = var1.getlocal(18);
               var10000 = var11._eq(Py.newInteger(6));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(658);
                  if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(17), (PyObject)PyString.fromInterned("product_type")).__nonzero__()) {
                     var1.setline(659);
                     var11 = var1.getlocal(17).__getattr__("product_type");
                     var1.setlocal(23, var11);
                     var3 = null;
                  } else {
                     var1.setline(661);
                     var11 = var1.getlocal(12);
                     var1.setlocal(23, var11);
                     var3 = null;

                     try {
                        var1.setline(666);
                        var11 = var1.getlocal(6).__call__(var2, var1.getlocal(9), var1.getlocal(22));
                        var1.setlocal(24, var11);
                        var3 = null;
                        var1.setline(667);
                        var11 = var1.getlocal(5).__call__((ThreadState)var2, (PyObject)var1.getlocal(24), (PyObject)PyString.fromInterned("ProductName"));
                        var15 = Py.unpackSequence(var11, 2);
                        var6 = var15[0];
                        var1.setlocal(25, var6);
                        var6 = null;
                        var6 = var15[1];
                        var1.setlocal(26, var6);
                        var6 = null;
                        var3 = null;
                        var1.setline(669);
                        var11 = var1.getlocal(26);
                        var10000 = var11._eq(var1.getlocal(16));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var11 = var1.getlocal(25).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Server"));
                           var10000 = var11._ne(Py.newInteger(-1));
                           var3 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(670);
                           var11 = var1.getlocal(15);
                           var1.setlocal(23, var11);
                           var3 = null;
                        }
                     } catch (Throwable var9) {
                        var3 = Py.setException(var9, var1);
                        if (!var3.match(var1.getglobal("WindowsError"))) {
                           throw var3;
                        }

                        var1.setline(673);
                     }
                  }

                  var1.setline(675);
                  var11 = var1.getlocal(19);
                  var10000 = var11._eq(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(676);
                     var11 = var1.getlocal(23);
                     var10000 = var11._eq(var1.getlocal(12));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(677);
                        var17 = PyString.fromInterned("Vista");
                        var1.setlocal(0, var17);
                        var3 = null;
                     } else {
                        var1.setline(679);
                        var17 = PyString.fromInterned("2008Server");
                        var1.setlocal(0, var17);
                        var3 = null;
                     }
                  } else {
                     var1.setline(680);
                     var11 = var1.getlocal(19);
                     var10000 = var11._eq(Py.newInteger(1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(681);
                        var11 = var1.getlocal(23);
                        var10000 = var11._eq(var1.getlocal(12));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(682);
                           var17 = PyString.fromInterned("7");
                           var1.setlocal(0, var17);
                           var3 = null;
                        } else {
                           var1.setline(684);
                           var17 = PyString.fromInterned("2008ServerR2");
                           var1.setlocal(0, var17);
                           var3 = null;
                        }
                     } else {
                        var1.setline(685);
                        var11 = var1.getlocal(19);
                        var10000 = var11._eq(Py.newInteger(2));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(686);
                           var11 = var1.getlocal(23);
                           var10000 = var11._eq(var1.getlocal(12));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(687);
                              var17 = PyString.fromInterned("8");
                              var1.setlocal(0, var17);
                              var3 = null;
                           } else {
                              var1.setline(689);
                              var17 = PyString.fromInterned("2012Server");
                              var1.setlocal(0, var17);
                              var3 = null;
                           }
                        } else {
                           var1.setline(691);
                           var17 = PyString.fromInterned("post2012Server");
                           var1.setlocal(0, var17);
                           var3 = null;
                        }
                     }
                  }
               }
            }
         }
      }

      try {
         var1.setline(701);
         var11 = var1.getlocal(6).__call__(var2, var1.getlocal(9), var1.getlocal(22));
         var1.setlocal(27, var11);
         var3 = null;
         var1.setline(703);
         var1.getlocal(5).__call__((ThreadState)var2, (PyObject)var1.getlocal(27), (PyObject)PyString.fromInterned("SystemRoot"));
      } catch (Throwable var7) {
         Py.setException(var7, var1);
         var1.setline(705);
         var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var4;
      }

      var1.setline(713);
      var11 = var1.getglobal("_win32_getvalue").__call__((ThreadState)var2, var1.getlocal(27), (PyObject)PyString.fromInterned("CurrentBuildNumber"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(1)}))).__getitem__(Py.newInteger(0));
      var1.setlocal(28, var11);
      var3 = null;
      var1.setline(716);
      var11 = var1.getglobal("_win32_getvalue").__call__((ThreadState)var2, var1.getlocal(27), (PyObject)PyString.fromInterned("CurrentType"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), Py.newInteger(1)}))).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var11);
      var3 = null;
      var1.setline(721);
      var11 = var1.getglobal("_norm_version").__call__(var2, var1.getlocal(1), var1.getlocal(28));
      var1.setlocal(1, var11);
      var3 = null;
      var1.setline(724);
      var1.getlocal(7).__call__(var2, var1.getlocal(27));
      var1.setline(725);
      var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _mac_ver_lookup$16(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      String[] var3 = new String[]{"gestalt"};
      PyObject[] var7 = imp.importFrom("gestalt", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(730);
      PyObject var8 = imp.importOne("MacOS", var1, -1);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(731);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(732);
      var8 = var1.getlocal(4).__getattr__("append");
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(733);
      var8 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(733);
         var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(738);
            var8 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(6, var4);

         try {
            var1.setline(735);
            var1.getlocal(5).__call__(var2, var1.getlocal(2).__call__(var2, var1.getlocal(6)));
         } catch (Throwable var6) {
            PyException var5 = Py.setException(var6, var1);
            if (!var5.match(new PyTuple(new PyObject[]{var1.getglobal("RuntimeError"), var1.getlocal(3).__getattr__("Error")}))) {
               throw var5;
            }

            var1.setline(737);
            var1.getlocal(5).__call__(var2, var1.getlocal(1));
         }
      }
   }

   public PyObject _bcd2str$17(PyFrame var1, ThreadState var2) {
      var1.setline(742);
      PyObject var3 = var1.getglobal("hex").__call__(var2, var1.getlocal(0)).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _mac_ver_gestalt$18(PyFrame var1, ThreadState var2) {
      var1.setline(751);
      PyString.fromInterned("\n        Thanks to Mark R. Levinson for mailing documentation links and\n        code examples for this function. Documentation for the\n        gestalt() API is available online at:\n\n           http://www.rgaros.nl/gestalt/\n    ");

      PyException var3;
      PyObject var8;
      try {
         var1.setline(754);
         var8 = imp.importOne("gestalt", var1, -1);
         var1.setlocal(0, var8);
         var3 = null;
         var1.setline(755);
         var8 = imp.importOne("MacOS", var1, -1);
         var1.setlocal(1, var8);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("ImportError"))) {
            var1.setline(757);
            PyObject var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(759);
      var8 = var1.getglobal("_mac_ver_lookup").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("sysv"), PyString.fromInterned("sysa")})));
      PyObject[] var5 = Py.unpackSequence(var8, 2);
      PyObject var6 = var5[0];
      var1.setlocal(2, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(3, var6);
      var6 = null;
      var3 = null;
      var1.setline(761);
      PyTuple var10;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(762);
         var8 = var1.getlocal(2)._and(Py.newInteger(65280))._rshift(Py.newInteger(8));
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(763);
         var8 = var1.getlocal(2)._and(Py.newInteger(240))._rshift(Py.newInteger(4));
         var1.setlocal(5, var8);
         var3 = null;
         var1.setline(764);
         var8 = var1.getlocal(2)._and(Py.newInteger(15));
         var1.setlocal(6, var8);
         var3 = null;
         var1.setline(766);
         var10 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         PyObject var10000 = var10._ge(new PyTuple(new PyObject[]{Py.newInteger(10), Py.newInteger(4)}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(772);
            var8 = var1.getglobal("_mac_ver_lookup").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("sys1"), PyString.fromInterned("sys2"), PyString.fromInterned("sys3")})));
            var5 = Py.unpackSequence(var8, 3);
            var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(6, var6);
            var6 = null;
            var3 = null;
            var1.setline(773);
            var8 = PyString.fromInterned("%i.%i.%i")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)}));
            var1.setlocal(7, var8);
            var3 = null;
         } else {
            var1.setline(775);
            var8 = PyString.fromInterned("%s.%i.%i")._mod(new PyTuple(new PyObject[]{var1.getglobal("_bcd2str").__call__(var2, var1.getlocal(4)), var1.getlocal(5), var1.getlocal(6)}));
            var1.setlocal(7, var8);
            var3 = null;
         }
      }

      var1.setline(777);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(778);
         var8 = (new PyDictionary(new PyObject[]{Py.newInteger(1), PyString.fromInterned("68k"), Py.newInteger(2), PyString.fromInterned("PowerPC"), Py.newInteger(10), PyString.fromInterned("i386")})).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned(""));
         var1.setlocal(8, var8);
         var3 = null;
      }

      var1.setline(782);
      var10 = new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(""), PyString.fromInterned("")});
      var1.setlocal(9, var10);
      var3 = null;
      var1.setline(783);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(9), var1.getlocal(8)});
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject _mac_ver_xml$19(PyFrame var1, ThreadState var2) {
      var1.setline(786);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mac_ver$20(PyFrame var1, ThreadState var2) {
      var1.setline(815);
      PyString.fromInterned(" Get MacOS version information and return it as tuple (release,\n        versioninfo, machine) with versioninfo being a tuple (version,\n        dev_stage, non_release_version).\n\n        Entries which cannot be determined are set to the paramter values\n        which default to ''. All tuple entries are strings.\n    ");
      var1.setline(819);
      PyObject var3 = var1.getglobal("_mac_ver_xml").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(820);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(821);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(825);
         PyObject var4 = var1.getglobal("_mac_ver_gestalt").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(826);
         var4 = var1.getlocal(3);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(827);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(829);
            var10000 = var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"));
            if (var10000.__nonzero__()) {
               var4 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("os.name"), (PyObject)PyString.fromInterned(""));
               var10000 = var4._eq(PyString.fromInterned("Mac OS X"));
               var4 = null;
            }

            PyTuple var5;
            if (var10000.__nonzero__()) {
               var1.setline(830);
               var4 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("os.version"), (PyObject)var1.getlocal(0));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(831);
               var5 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(1), var1.getglobal("os").__getattr__("uname").__call__(var2).__getitem__(Py.newInteger(-1))});
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(834);
               var5 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
               var1.f_lasti = -1;
               return var5;
            }
         }
      }
   }

   public PyObject _java_getprop$21(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(839);
         var3 = var1.getglobal("System").__getattr__("getProperty").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(840);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(841);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(842);
            var3 = var1.getglobal("newString").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(844);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject _java_getenv$22(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(848);
         var3 = var1.getglobal("System").__getattr__("getenv").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(849);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(850);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(851);
            var3 = var1.getglobal("newString").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(853);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject java_ver$23(PyFrame var1, ThreadState var2) {
      var1.setline(866);
      PyString.fromInterned(" Version interface for Jython.\n\n        Returns a tuple (release,vendor,vminfo,osinfo) with vminfo being\n        a tuple (vm_name,vm_release,vm_vendor) and osinfo being a\n        tuple (os_name,os_version,os_arch).\n\n        Values which cannot be determined are set to the defaults\n        given as parameters (which all default to '').\n\n    ");

      PyException var3;
      PyTuple var4;
      PyObject var8;
      try {
         var1.setline(869);
         var8 = imp.importOne("java.lang", var1, -1);
         var1.setlocal(4, var8);
         var3 = null;
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("ImportError"))) {
            var1.setline(871);
            var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(873);
      var8 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java.vendor"), (PyObject)var1.getlocal(1));
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(874);
      var8 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java.version"), (PyObject)var1.getlocal(0));
      var1.setlocal(0, var8);
      var3 = null;
      var1.setline(875);
      var8 = var1.getlocal(2);
      PyObject[] var5 = Py.unpackSequence(var8, 3);
      PyObject var6 = var5[0];
      var1.setlocal(5, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(6, var6);
      var6 = null;
      var6 = var5[2];
      var1.setlocal(7, var6);
      var6 = null;
      var3 = null;
      var1.setline(876);
      var8 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java.vm.name"), (PyObject)var1.getlocal(5));
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(877);
      var8 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java.vm.vendor"), (PyObject)var1.getlocal(7));
      var1.setlocal(7, var8);
      var3 = null;
      var1.setline(878);
      var8 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java.vm.version"), (PyObject)var1.getlocal(6));
      var1.setlocal(6, var8);
      var3 = null;
      var1.setline(879);
      PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)});
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(880);
      var8 = var1.getlocal(3);
      var5 = Py.unpackSequence(var8, 3);
      var6 = var5[0];
      var1.setlocal(8, var6);
      var6 = null;
      var6 = var5[1];
      var1.setlocal(9, var6);
      var6 = null;
      var6 = var5[2];
      var1.setlocal(10, var6);
      var6 = null;
      var3 = null;
      var1.setline(881);
      var8 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("os.arch"), (PyObject)var1.getlocal(10));
      var1.setlocal(10, var8);
      var3 = null;
      var1.setline(882);
      var8 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("os.name"), (PyObject)var1.getlocal(8));
      var1.setlocal(8, var8);
      var3 = null;
      var1.setline(883);
      var8 = var1.getglobal("_java_getprop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("os.version"), (PyObject)var1.getlocal(9));
      var1.setlocal(9, var8);
      var3 = null;
      var1.setline(884);
      var9 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9), var1.getlocal(10)});
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(886);
      var4 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject system_alias$24(PyFrame var1, ThreadState var2) {
      var1.setline(898);
      PyString.fromInterned(" Returns (system,release,version) aliased to common\n        marketing names used for some systems.\n\n        It also does some reordering of the information in some cases\n        where it would otherwise cause confusion.\n\n    ");
      var1.setline(899);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(PyString.fromInterned("Rhapsody"));
      var3 = null;
      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(902);
         var7 = new PyTuple(new PyObject[]{PyString.fromInterned("MacOS X Server"), var1.getlocal(0)._add(var1.getlocal(1)), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(904);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._eq(PyString.fromInterned("SunOS"));
         var4 = null;
         PyString var8;
         if (var10000.__nonzero__()) {
            var1.setline(906);
            var4 = var1.getlocal(1);
            var10000 = var4._lt(PyString.fromInterned("5"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(908);
               var7 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(910);
            var4 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("."));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(911);
            if (var1.getlocal(3).__nonzero__()) {
               label56: {
                  try {
                     var1.setline(913);
                     var4 = var1.getglobal("int").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)));
                     var1.setlocal(4, var4);
                     var4 = null;
                  } catch (Throwable var6) {
                     PyException var9 = Py.setException(var6, var1);
                     if (var9.match(var1.getglobal("ValueError"))) {
                        var1.setline(915);
                        break label56;
                     }

                     throw var9;
                  }

                  var1.setline(917);
                  PyObject var5 = var1.getlocal(4)._sub(Py.newInteger(3));
                  var1.setlocal(4, var5);
                  var5 = null;
                  var1.setline(918);
                  var5 = var1.getglobal("str").__call__(var2, var1.getlocal(4));
                  var1.getlocal(3).__setitem__((PyObject)Py.newInteger(0), var5);
                  var5 = null;
                  var1.setline(919);
                  var5 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("."));
                  var1.setlocal(1, var5);
                  var5 = null;
               }
            }

            var1.setline(920);
            var4 = var1.getlocal(1);
            var10000 = var4._lt(PyString.fromInterned("6"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(921);
               var8 = PyString.fromInterned("Solaris");
               var1.setlocal(0, var8);
               var4 = null;
            } else {
               var1.setline(924);
               var8 = PyString.fromInterned("Solaris");
               var1.setlocal(0, var8);
               var4 = null;
            }
         } else {
            var1.setline(926);
            var4 = var1.getlocal(0);
            var10000 = var4._eq(PyString.fromInterned("IRIX64"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(930);
               var8 = PyString.fromInterned("IRIX");
               var1.setlocal(0, var8);
               var4 = null;
               var1.setline(931);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(932);
                  var4 = var1.getlocal(2)._add(PyString.fromInterned(" (64bit)"));
                  var1.setlocal(2, var4);
                  var4 = null;
               } else {
                  var1.setline(934);
                  var8 = PyString.fromInterned("64bit");
                  var1.setlocal(2, var8);
                  var4 = null;
               }
            } else {
               var1.setline(936);
               var4 = var1.getlocal(0);
               var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("win32"), PyString.fromInterned("win16")}));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(938);
                  var8 = PyString.fromInterned("Windows");
                  var1.setlocal(0, var8);
                  var4 = null;
               }
            }
         }

         var1.setline(940);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject _platform$25(PyFrame var1, ThreadState var2) {
      var1.setline(948);
      PyString.fromInterned(" Helper to format the platform string in a filename\n        compatible format e.g. \"system-version-machine\".\n    ");
      var1.setline(950);
      PyObject var3 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("map").__call__(var2, var1.getglobal("string").__getattr__("strip"), var1.getglobal("filter").__call__(var2, var1.getglobal("len"), var1.getlocal(0))), (PyObject)PyString.fromInterned("-"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(956);
      var3 = var1.getglobal("string").__getattr__("replace");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(957);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("_"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(958);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("/"), (PyObject)PyString.fromInterned("-"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(959);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("\\"), (PyObject)PyString.fromInterned("-"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(960);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned(":"), (PyObject)PyString.fromInterned("-"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(961);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned(";"), (PyObject)PyString.fromInterned("-"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(962);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("\""), (PyObject)PyString.fromInterned("-"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(963);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("("), (PyObject)PyString.fromInterned("-"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(964);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned(")"), (PyObject)PyString.fromInterned("-"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(967);
      var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("unknown"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(970);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(971);
         var3 = var1.getlocal(2).__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("--"), (PyObject)PyString.fromInterned("-"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(972);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(974);
         var3 = var1.getlocal(3);
         var1.setlocal(1, var3);
         var3 = null;
      }

      while(true) {
         var1.setline(975);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
         var10000 = var3._eq(PyString.fromInterned("-"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(978);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(976);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject _node$26(PyFrame var1, ThreadState var2) {
      var1.setline(983);
      PyString.fromInterned(" Helper to determine the node name of this machine.\n    ");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(985);
         PyObject var7 = imp.importOne("socket", var1, -1);
         var1.setlocal(1, var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("ImportError"))) {
            var1.setline(988);
            var4 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      try {
         var1.setline(990);
         var4 = var1.getlocal(1).__getattr__("gethostname").__call__(var2);
         var1.f_lasti = -1;
         return var4;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getlocal(1).__getattr__("error"))) {
            var1.setline(993);
            var4 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var4;
         } else {
            throw var3;
         }
      }
   }

   public PyObject _abspath$27(PyFrame var1, ThreadState var2) {
      var1.setline(1003);
      PyObject var3;
      if (var1.getlocal(1).__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
         var1.setline(1004);
         var3 = var1.getlocal(2).__call__(var2, var1.getlocal(3).__call__(var2), var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(1005);
      var3 = var1.getlocal(4).__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _follow_symlinks$28(PyFrame var1, ThreadState var2) {
      var1.setline(1015);
      PyString.fromInterned(" In case filepath is a symlink, follow it until a\n        real file is reached.\n    ");
      var1.setline(1016);
      PyObject var3 = var1.getglobal("_abspath").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;

      while(true) {
         var1.setline(1017);
         if (!var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(1020);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1018);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(0)), var1.getglobal("os").__getattr__("readlink").__call__(var2, var1.getlocal(0))));
         var1.setlocal(0, var3);
         var3 = null;
      }
   }

   public PyObject _syscmd_uname$29(PyFrame var1, ThreadState var2) {
      var1.setline(1025);
      PyString.fromInterned(" Interface to the system's uname command.\n    ");
      var1.setline(1026);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("dos"), PyString.fromInterned("win32"), PyString.fromInterned("win16"), PyString.fromInterned("os2")}));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"));
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("os").__getattr__("_name");
            var10000 = var3._eq(PyString.fromInterned("nt"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1029);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         PyObject var6;
         try {
            var1.setline(1031);
            var6 = var1.getglobal("os").__getattr__("popen").__call__(var2, PyString.fromInterned("uname %s 2> %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("DEV_NULL")})));
            var1.setlocal(2, var6);
            var4 = null;
         } catch (Throwable var5) {
            var4 = Py.setException(var5, var1);
            if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("os").__getattr__("error")}))) {
               var1.setline(1033);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            throw var4;
         }

         var1.setline(1034);
         var6 = var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(2).__getattr__("read").__call__(var2));
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(1035);
         var6 = var1.getlocal(2).__getattr__("close").__call__(var2);
         var1.setlocal(4, var6);
         var4 = null;
         var1.setline(1036);
         var10000 = var1.getlocal(3).__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         if (var10000.__nonzero__()) {
            var1.setline(1037);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1039);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _syscmd_file$30(PyFrame var1, ThreadState var2) {
      var1.setline(1050);
      PyString.fromInterned(" Interface to the system's file command.\n\n        The function uses the -b option of the file command to have it\n        ommit the filename in its output and if possible the -L option\n        to have the command follow symlinks. It returns default in\n        case the command should fail.\n\n    ");
      var1.setline(1069);
      PyObject var3 = imp.importOne("subprocess", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1071);
      var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("dos"), PyString.fromInterned("win32"), PyString.fromInterned("win16"), PyString.fromInterned("os2")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1073);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1074);
         PyObject var4 = var1.getglobal("_follow_symlinks").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var4);
         var4 = null;

         try {
            var1.setline(1076);
            var10000 = var1.getlocal(2).__getattr__("Popen");
            PyObject[] var8 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("file"), var1.getlocal(0)}), var1.getlocal(2).__getattr__("PIPE"), var1.getlocal(2).__getattr__("STDOUT")};
            String[] var5 = new String[]{"stdout", "stderr"};
            var10000 = var10000.__call__(var2, var8, var5);
            var4 = null;
            var4 = var10000;
            var1.setlocal(3, var4);
            var4 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("os").__getattr__("error")}))) {
               var1.setline(1080);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            throw var7;
         }

         var1.setline(1081);
         var4 = var1.getlocal(3).__getattr__("communicate").__call__(var2).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1082);
         var4 = var1.getlocal(3).__getattr__("wait").__call__(var2);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(1083);
         var10000 = var1.getlocal(4).__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(5);
         }

         if (var10000.__nonzero__()) {
            var1.setline(1084);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1086);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject architecture$31(PyFrame var1, ThreadState var2) {
      var1.setline(1120);
      PyString.fromInterned(" Queries the given executable (defaults to the Python interpreter\n        binary) for various architecture information.\n\n        Returns a tuple (bits,linkage) which contains information about\n        the bit architecture and the linkage format used for the\n        executable. Both values are returned as strings.\n\n        Values that cannot be determined are returned as given by the\n        parameter presets. If bits is given as '', the sizeof(pointer)\n        (or sizeof(long) on Python version < 1.5.2) is used as\n        indicator for the supported pointer size.\n\n        The function relies on the system's \"file\" command to do the\n        actual work. This is available on most if not all Unix\n        platforms. On some non-Unix platforms where the \"file\" command\n        does not exist and the executable is set to the Python interpreter\n        binary defaults from _default_architecture are used.\n\n    ");
      var1.setline(1123);
      PyObject var3;
      PyObject var4;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(1124);
         var3 = imp.importOne("struct", var1, -1);
         var1.setlocal(3, var3);
         var3 = null;

         try {
            var1.setline(1126);
            var3 = var1.getlocal(3).__getattr__("calcsize").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("P"));
            var1.setlocal(4, var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getlocal(3).__getattr__("error"))) {
               throw var7;
            }

            var1.setline(1129);
            var4 = var1.getlocal(3).__getattr__("calcsize").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("l"));
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(1130);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(4)._mul(Py.newInteger(8)))._add(PyString.fromInterned("bit"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1133);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(1134);
         var3 = var1.getglobal("_syscmd_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned(""));
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(1136);
         PyString var9 = PyString.fromInterned("");
         var1.setlocal(5, var9);
         var3 = null;
      }

      var1.setline(1138);
      PyObject var10000 = var1.getlocal(5).__not__();
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getglobal("sys").__getattr__("executable"));
         var3 = null;
      }

      PyTuple var10;
      if (var10000.__nonzero__()) {
         var1.setline(1142);
         var3 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var3._in(var1.getglobal("_default_architecture"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1143);
            var3 = var1.getglobal("_default_architecture").__getitem__(var1.getglobal("sys").__getattr__("platform"));
            PyObject[] var11 = Py.unpackSequence(var3, 2);
            PyObject var5 = var11[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var11[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
            var1.setline(1144);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(1145);
               var3 = var1.getlocal(6);
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(1146);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(1147);
               var3 = var1.getlocal(7);
               var1.setlocal(2, var3);
               var3 = null;
            }
         }

         var1.setline(1148);
         var10 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(1151);
         var4 = var1.getglobal("_architecture_split").__call__(var2, var1.getlocal(5)).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(1153);
         PyString var8 = PyString.fromInterned("executable");
         var10000 = var8._notin(var1.getlocal(8));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1155);
            var10 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(1158);
            var8 = PyString.fromInterned("32-bit");
            var10000 = var8._in(var1.getlocal(8));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1159);
               var8 = PyString.fromInterned("32bit");
               var1.setlocal(1, var8);
               var4 = null;
            } else {
               var1.setline(1160);
               var8 = PyString.fromInterned("N32");
               var10000 = var8._in(var1.getlocal(8));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1162);
                  var8 = PyString.fromInterned("n32bit");
                  var1.setlocal(1, var8);
                  var4 = null;
               } else {
                  var1.setline(1163);
                  var8 = PyString.fromInterned("64-bit");
                  var10000 = var8._in(var1.getlocal(8));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1164);
                     var8 = PyString.fromInterned("64bit");
                     var1.setlocal(1, var8);
                     var4 = null;
                  }
               }
            }

            var1.setline(1167);
            var8 = PyString.fromInterned("ELF");
            var10000 = var8._in(var1.getlocal(8));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1168);
               var8 = PyString.fromInterned("ELF");
               var1.setlocal(2, var8);
               var4 = null;
            } else {
               var1.setline(1169);
               var8 = PyString.fromInterned("PE");
               var10000 = var8._in(var1.getlocal(8));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1171);
                  var8 = PyString.fromInterned("Windows");
                  var10000 = var8._in(var1.getlocal(8));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1172);
                     var8 = PyString.fromInterned("WindowsPE");
                     var1.setlocal(2, var8);
                     var4 = null;
                  } else {
                     var1.setline(1174);
                     var8 = PyString.fromInterned("PE");
                     var1.setlocal(2, var8);
                     var4 = null;
                  }
               } else {
                  var1.setline(1175);
                  var8 = PyString.fromInterned("COFF");
                  var10000 = var8._in(var1.getlocal(8));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1176);
                     var8 = PyString.fromInterned("COFF");
                     var1.setlocal(2, var8);
                     var4 = null;
                  } else {
                     var1.setline(1177);
                     var8 = PyString.fromInterned("MS-DOS");
                     var10000 = var8._in(var1.getlocal(8));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1178);
                        var8 = PyString.fromInterned("MSDOS");
                        var1.setlocal(2, var8);
                        var4 = null;
                     } else {
                        var1.setline(1181);
                     }
                  }
               }
            }

            var1.setline(1183);
            var10 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
            var1.f_lasti = -1;
            return var10;
         }
      }
   }

   public PyObject uname$32(PyFrame var1, ThreadState var2) {
      var1.setline(1204);
      PyString.fromInterned(" Fairly portable uname interface. Returns a tuple\n        of strings (system,node,release,version,machine,processor)\n        identifying the underlying platform.\n\n        Note that unlike the os.uname function this also returns\n        possible processor information as an additional tuple entry.\n\n        Entries which cannot be determined are set to ''.\n\n\n        Jython-note:\n        platform.uname returns JVM-info.\n        For native platform info use os.uname or platform._uname.\n    ");
      var1.setline(1207);
      PyObject var3 = var1.getglobal("_uname_cache");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1208);
         var3 = var1.getglobal("_uname_cache");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1210);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(1212);
         PyObject var7 = var1.getglobal("_node").__call__(var2);
         var1.setlocal(1, var7);
         var4 = null;
         var1.setline(1213);
         var7 = var1.getglobal("os").__getattr__("uname").__call__(var2).__getitem__(Py.newInteger(4));
         var1.setlocal(2, var7);
         var4 = null;
         var1.setline(1216);
         var7 = var1.getglobal("java_ver").__call__(var2);
         PyObject[] var5 = Py.unpackSequence(var7, 4);
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
         var4 = null;
         var1.setline(1217);
         var4 = PyString.fromInterned("Java");
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(1218);
         var7 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned(", "));
         var1.setlocal(8, var7);
         var4 = null;
         var1.setline(1219);
         if (var1.getlocal(8).__not__().__nonzero__()) {
            var1.setline(1220);
            var7 = var1.getlocal(4);
            var1.setlocal(8, var7);
            var4 = null;
         }

         var1.setline(1222);
         if (var1.getlocal(0).__not__().__nonzero__()) {
            var1.setline(1224);
            var7 = var1.getglobal("_uname").__call__(var2).__getitem__(Py.newInteger(-1));
            var1.setlocal(0, var7);
            var4 = null;
         }

         var1.setline(1227);
         var7 = var1.getlocal(7);
         var10000 = var7._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1228);
            var4 = PyString.fromInterned("");
            var1.setlocal(7, var4);
            var4 = null;
         }

         var1.setline(1229);
         var7 = var1.getlocal(1);
         var10000 = var7._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1230);
            var4 = PyString.fromInterned("");
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(1231);
         var7 = var1.getlocal(3);
         var10000 = var7._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1232);
            var4 = PyString.fromInterned("");
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(1233);
         var7 = var1.getlocal(8);
         var10000 = var7._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1234);
            var4 = PyString.fromInterned("");
            var1.setlocal(8, var4);
            var4 = null;
         }

         var1.setline(1235);
         var7 = var1.getlocal(2);
         var10000 = var7._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1236);
            var4 = PyString.fromInterned("");
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(1237);
         var7 = var1.getlocal(0);
         var10000 = var7._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1238);
            var4 = PyString.fromInterned("");
            var1.setlocal(0, var4);
            var4 = null;
         }

         var1.setline(1240);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(1), var1.getlocal(3), var1.getlocal(8), var1.getlocal(2), var1.getlocal(0)});
         var1.setglobal("_uname_cache", var8);
         var4 = null;
         var1.setline(1241);
         var3 = var1.getglobal("_uname_cache");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _uname$33(PyFrame var1, ThreadState var2) {
      var1.setline(1260);
      PyString.fromInterned(" Fairly portable uname interface. Returns a tuple\n        of strings (system,node,release,version,machine,processor)\n        identifying the underlying platform.\n\n        Note that unlike the os.uname function this also returns\n        possible processor information as an additional tuple entry.\n\n        Entries which cannot be determined are set to ''.\n\n        Jython-note:\n        _uname resembles CPython behavior for debugging purposes etc.\n    ");
      var1.setline(1262);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(1264);
      PyObject var10 = var1.getglobal("_uname_cache2");
      PyObject var10000 = var10._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1265);
         var10 = var1.getglobal("_uname_cache2");
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(1267);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(1, var4);
         var4 = null;

         PyInteger var5;
         PyObject var6;
         PyException var11;
         PyObject var12;
         PyObject[] var13;
         try {
            var1.setline(1271);
            var12 = var1.getglobal("os").__getattr__("uname").__call__(var2);
            var13 = Py.unpackSequence(var12, 5);
            var6 = var13[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var13[1];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var13[2];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var13[3];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var13[4];
            var1.setlocal(6, var6);
            var6 = null;
            var4 = null;
         } catch (Throwable var9) {
            var11 = Py.setException(var9, var1);
            if (!var11.match(var1.getglobal("AttributeError"))) {
               throw var11;
            }

            var1.setline(1273);
            var5 = Py.newInteger(1);
            var1.setlocal(0, var5);
            var5 = null;
         }

         var1.setline(1275);
         var10000 = var1.getlocal(0);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("filter").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)}))).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1278);
            if (var1.getlocal(0).__nonzero__()) {
               var1.setline(1279);
               var12 = var1.getglobal("sys").__getattr__("platform");
               var1.setlocal(2, var12);
               var4 = null;
               var1.setline(1280);
               var4 = PyString.fromInterned("");
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(1281);
               var4 = PyString.fromInterned("");
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(1282);
               var12 = var1.getglobal("_node").__call__(var2);
               var1.setlocal(3, var12);
               var4 = null;
               var1.setline(1283);
               var4 = PyString.fromInterned("");
               var1.setlocal(6, var4);
               var4 = null;
            }

            var1.setline(1285);
            PyInteger var16 = Py.newInteger(1);
            var1.setlocal(7, var16);
            var4 = null;
            var1.setline(1288);
            var12 = var1.getlocal(2);
            var10000 = var12._eq(PyString.fromInterned("win32"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1289);
               var12 = var1.getglobal("win32_ver").__call__(var2);
               var13 = Py.unpackSequence(var12, 4);
               var6 = var13[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var13[1];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var13[2];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var13[3];
               var1.setlocal(9, var6);
               var6 = null;
               var4 = null;
               var1.setline(1290);
               var10000 = var1.getlocal(4);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(5);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1291);
                  var16 = Py.newInteger(0);
                  var1.setlocal(7, var16);
                  var4 = null;
               }

               var1.setline(1296);
               if (var1.getlocal(6).__not__().__nonzero__()) {
                  var1.setline(1298);
                  var4 = PyString.fromInterned("PROCESSOR_ARCHITEW6432");
                  var10000 = var4._in(var1.getglobal("os").__getattr__("environ"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1299);
                     var12 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PROCESSOR_ARCHITEW6432"), (PyObject)PyString.fromInterned(""));
                     var1.setlocal(6, var12);
                     var4 = null;
                  } else {
                     var1.setline(1301);
                     var12 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PROCESSOR_ARCHITECTURE"), (PyObject)PyString.fromInterned(""));
                     var1.setlocal(6, var12);
                     var4 = null;
                  }
               }

               var1.setline(1302);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(1303);
                  var12 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PROCESSOR_IDENTIFIER"), (PyObject)var1.getlocal(6));
                  var1.setlocal(1, var12);
                  var4 = null;
               }
            }

            var1.setline(1307);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(1308);
               var12 = var1.getglobal("_syscmd_ver").__call__(var2, var1.getlocal(2));
               var13 = Py.unpackSequence(var12, 3);
               var6 = var13[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var13[1];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var13[2];
               var1.setlocal(5, var6);
               var6 = null;
               var4 = null;
               var1.setline(1311);
               var12 = var1.getlocal(2);
               var10000 = var12._eq(PyString.fromInterned("Microsoft Windows"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1312);
                  var4 = PyString.fromInterned("Windows");
                  var1.setlocal(2, var4);
                  var4 = null;
               } else {
                  var1.setline(1313);
                  var12 = var1.getlocal(2);
                  var10000 = var12._eq(PyString.fromInterned("Microsoft"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var12 = var1.getlocal(4);
                     var10000 = var12._eq(PyString.fromInterned("Windows"));
                     var4 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1318);
                     var4 = PyString.fromInterned("Windows");
                     var1.setlocal(2, var4);
                     var4 = null;
                     var1.setline(1319);
                     var4 = PyString.fromInterned("6.0");
                     var10000 = var4._eq(var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1320);
                        var4 = PyString.fromInterned("Vista");
                        var1.setlocal(4, var4);
                        var4 = null;
                     } else {
                        var1.setline(1322);
                        var4 = PyString.fromInterned("");
                        var1.setlocal(4, var4);
                        var4 = null;
                     }
                  }
               }
            }

            var1.setline(1326);
            var12 = var1.getlocal(2);
            var10000 = var12._in(new PyTuple(new PyObject[]{PyString.fromInterned("win32"), PyString.fromInterned("win16")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1327);
               if (var1.getlocal(5).__not__().__nonzero__()) {
                  var1.setline(1328);
                  var12 = var1.getlocal(2);
                  var10000 = var12._eq(PyString.fromInterned("win32"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1329);
                     var4 = PyString.fromInterned("32bit");
                     var1.setlocal(5, var4);
                     var4 = null;
                  } else {
                     var1.setline(1331);
                     var4 = PyString.fromInterned("16bit");
                     var1.setlocal(5, var4);
                     var4 = null;
                  }
               }

               var1.setline(1332);
               var4 = PyString.fromInterned("Windows");
               var1.setlocal(2, var4);
               var4 = null;
            } else {
               var1.setline(1334);
               var12 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
               var10000 = var12._eq(PyString.fromInterned("java"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1335);
                  var12 = var1.getglobal("java_ver").__call__(var2);
                  var13 = Py.unpackSequence(var12, 4);
                  var6 = var13[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var13[1];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var6 = var13[2];
                  var1.setlocal(11, var6);
                  var6 = null;
                  var6 = var13[3];
                  var1.setlocal(12, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(1336);
                  var4 = PyString.fromInterned("Java");
                  var1.setlocal(2, var4);
                  var4 = null;
                  var1.setline(1337);
                  var12 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)PyString.fromInterned(", "));
                  var1.setlocal(5, var12);
                  var4 = null;
                  var1.setline(1338);
                  if (var1.getlocal(5).__not__().__nonzero__()) {
                     var1.setline(1339);
                     var12 = var1.getlocal(10);
                     var1.setlocal(5, var12);
                     var4 = null;
                  }
               }
            }
         }

         var1.setline(1342);
         var12 = var1.getlocal(2);
         var10000 = var12._eq(PyString.fromInterned("OpenVMS"));
         var4 = null;
         if (var10000.__nonzero__()) {
            label172: {
               var1.setline(1344);
               var10000 = var1.getlocal(4).__not__();
               if (!var10000.__nonzero__()) {
                  var12 = var1.getlocal(4);
                  var10000 = var12._eq(PyString.fromInterned("0"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1345);
                  var12 = var1.getlocal(5);
                  var1.setlocal(4, var12);
                  var4 = null;
                  var1.setline(1346);
                  var4 = PyString.fromInterned("");
                  var1.setlocal(5, var4);
                  var4 = null;
               }

               try {
                  var1.setline(1349);
                  var12 = imp.importOne("vms_lib", var1, -1);
                  var1.setlocal(13, var12);
                  var4 = null;
               } catch (Throwable var8) {
                  var11 = Py.setException(var8, var1);
                  if (var11.match(var1.getglobal("ImportError"))) {
                     var1.setline(1351);
                     break label172;
                  }

                  throw var11;
               }

               var1.setline(1353);
               PyObject var14 = var1.getlocal(13).__getattr__("getsyi").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SYI$_CPU"), (PyObject)Py.newInteger(0));
               PyObject[] var17 = Py.unpackSequence(var14, 2);
               PyObject var7 = var17[0];
               var1.setlocal(14, var7);
               var7 = null;
               var7 = var17[1];
               var1.setlocal(15, var7);
               var7 = null;
               var5 = null;
               var1.setline(1354);
               var14 = var1.getlocal(15);
               var10000 = var14._ge(Py.newInteger(128));
               var5 = null;
               PyString var15;
               if (var10000.__nonzero__()) {
                  var1.setline(1355);
                  var15 = PyString.fromInterned("Alpha");
                  var1.setlocal(1, var15);
                  var5 = null;
               } else {
                  var1.setline(1357);
                  var15 = PyString.fromInterned("VAX");
                  var1.setlocal(1, var15);
                  var5 = null;
               }
            }
         }

         var1.setline(1358);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(1360);
            var12 = var1.getglobal("_syscmd_uname").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-p"), (PyObject)PyString.fromInterned(""));
            var1.setlocal(1, var12);
            var4 = null;
         }

         var1.setline(1363);
         var12 = var1.getlocal(2);
         var10000 = var12._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1364);
            var4 = PyString.fromInterned("");
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(1365);
         var12 = var1.getlocal(3);
         var10000 = var12._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1366);
            var4 = PyString.fromInterned("");
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(1367);
         var12 = var1.getlocal(4);
         var10000 = var12._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1368);
            var4 = PyString.fromInterned("");
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(1369);
         var12 = var1.getlocal(5);
         var10000 = var12._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1370);
            var4 = PyString.fromInterned("");
            var1.setlocal(5, var4);
            var4 = null;
         }

         var1.setline(1371);
         var12 = var1.getlocal(6);
         var10000 = var12._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1372);
            var4 = PyString.fromInterned("");
            var1.setlocal(6, var4);
            var4 = null;
         }

         var1.setline(1373);
         var12 = var1.getlocal(1);
         var10000 = var12._eq(PyString.fromInterned("unknown"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1374);
            var4 = PyString.fromInterned("");
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(1377);
         var12 = var1.getlocal(2);
         var10000 = var12._eq(PyString.fromInterned("Microsoft"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var12 = var1.getlocal(4);
            var10000 = var12._eq(PyString.fromInterned("Windows"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1378);
            var4 = PyString.fromInterned("Windows");
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1379);
            var4 = PyString.fromInterned("Vista");
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(1381);
         var12 = var1.getlocal(2);
         var10000 = var12._eq(PyString.fromInterned("Windows"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var12 = var1.getlocal(1);
            var10000 = var12._eq(PyString.fromInterned(""));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1382);
            var12 = var1.getglobal("_java_getenv").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PROCESSOR_IDENTIFIER"), (PyObject)var1.getlocal(1));
            var1.setlocal(1, var12);
            var4 = null;
         }

         var1.setline(1384);
         PyTuple var18 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(1)});
         var1.setglobal("_uname_cache2", var18);
         var4 = null;
         var1.setline(1385);
         var10 = var1.getglobal("_uname_cache2");
         var1.f_lasti = -1;
         return var10;
      }
   }

   public PyObject system$34(PyFrame var1, ThreadState var2) {
      var1.setline(1395);
      PyString.fromInterned(" Returns the system/OS name, e.g. 'Linux', 'Windows' or 'Java'.\n\n        An empty string is returned if the value cannot be determined.\n\n    ");
      var1.setline(1396);
      PyObject var3 = var1.getglobal("uname").__call__(var2).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject node$35(PyFrame var1, ThreadState var2) {
      var1.setline(1405);
      PyString.fromInterned(" Returns the computer's network name (which may not be fully\n        qualified)\n\n        An empty string is returned if the value cannot be determined.\n\n    ");
      var1.setline(1406);
      PyObject var3 = var1.getglobal("uname").__call__(var2).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject release$36(PyFrame var1, ThreadState var2) {
      var1.setline(1414);
      PyString.fromInterned(" Returns the system's release, e.g. '2.2.0' or 'NT'\n\n        An empty string is returned if the value cannot be determined.\n\n    ");
      var1.setline(1415);
      PyObject var3 = var1.getglobal("uname").__call__(var2).__getitem__(Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject version$37(PyFrame var1, ThreadState var2) {
      var1.setline(1423);
      PyString.fromInterned(" Returns the system's release version, e.g. '#3 on degas'\n\n        An empty string is returned if the value cannot be determined.\n\n    ");
      var1.setline(1424);
      PyObject var3 = var1.getglobal("uname").__call__(var2).__getitem__(Py.newInteger(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject machine$38(PyFrame var1, ThreadState var2) {
      var1.setline(1432);
      PyString.fromInterned(" Returns the machine type, e.g. 'i386'\n\n        An empty string is returned if the value cannot be determined.\n\n    ");
      var1.setline(1433);
      PyObject var3 = var1.getglobal("uname").__call__(var2).__getitem__(Py.newInteger(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject processor$39(PyFrame var1, ThreadState var2) {
      var1.setline(1444);
      PyString.fromInterned(" Returns the (true) processor name, e.g. 'amdk6'\n\n        An empty string is returned if the value cannot be\n        determined. Note that many platforms do not provide this\n        information or simply return the same value as for machine(),\n        e.g.  NetBSD does this.\n\n    ");
      var1.setline(1445);
      PyObject var3 = var1.getglobal("uname").__call__(var2).__getitem__(Py.newInteger(5));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _sys_version$40(PyFrame var1, ThreadState var2) {
      var1.setline(1489);
      PyString.fromInterned(" Returns a parsed version of Python's sys.version as tuple\n        (name, version, branch, revision, buildno, builddate, compiler)\n        referring to the Python implementation name, version, branch,\n        revision, build number, build date/time as string and the compiler\n        identification string.\n\n        Note that unlike the Python sys.version, the returned value\n        for the Python version will always include the patchlevel (it\n        defaults to '.0').\n\n        The function returns empty strings for tuple entries that\n        cannot be determined.\n\n        sys_version may be given to parse an alternative version\n        string, e.g. if the version was read from a different Python\n        interpreter.\n\n    ");
      var1.setline(1491);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1492);
         var3 = var1.getglobal("sys").__getattr__("version");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(1495);
      var3 = var1.getglobal("_sys_version_cache").__getattr__("get").__call__(var2, var1.getlocal(0), var1.getglobal("None"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1496);
      var3 = var1.getlocal(1);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1497);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1500);
         PyObject var4 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(10), (PyObject)null);
         var10000 = var4._eq(PyString.fromInterned("IronPython"));
         var4 = null;
         PyObject[] var5;
         PyObject var6;
         PyString var7;
         if (var10000.__nonzero__()) {
            var1.setline(1502);
            var7 = PyString.fromInterned("IronPython");
            var1.setlocal(2, var7);
            var4 = null;
            var1.setline(1503);
            var4 = var1.getglobal("_ironpython_sys_version_parser").__getattr__("match").__call__(var2, var1.getlocal(0));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1504);
            var4 = var1.getlocal(3);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1505);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("failed to parse IronPython sys.version: %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(0)))));
            }

            var1.setline(1508);
            var4 = var1.getlocal(3).__getattr__("groups").__call__(var2);
            var5 = Py.unpackSequence(var4, 3);
            var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(6, var6);
            var6 = null;
            var4 = null;
            var1.setline(1509);
            var7 = PyString.fromInterned("");
            var1.setlocal(7, var7);
            var4 = null;
            var1.setline(1510);
            var7 = PyString.fromInterned("");
            var1.setlocal(8, var7);
            var4 = null;
            var1.setline(1511);
            var7 = PyString.fromInterned("");
            var1.setlocal(9, var7);
            var4 = null;
            var1.setline(1512);
            var7 = PyString.fromInterned("");
            var1.setlocal(10, var7);
            var4 = null;
         } else {
            var1.setline(1514);
            var4 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("java"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1516);
               var7 = PyString.fromInterned("Jython");
               var1.setlocal(2, var7);
               var4 = null;
               var1.setline(1517);
               var4 = var1.getglobal("_jython_sys_version_parser").__getattr__("match").__call__(var2, var1.getlocal(0));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(1518);
               var4 = var1.getlocal(3);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1519);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("failed to parse Jython sys.version: %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(0)))));
               }

               var1.setline(1522);
               var4 = var1.getlocal(3).__getattr__("groups").__call__(var2);
               var5 = Py.unpackSequence(var4, 1);
               var6 = var5[0];
               var1.setlocal(4, var6);
               var6 = null;
               var4 = null;
               var1.setline(1523);
               var7 = PyString.fromInterned("");
               var1.setlocal(7, var7);
               var4 = null;
               var1.setline(1524);
               var7 = PyString.fromInterned("");
               var1.setlocal(8, var7);
               var4 = null;
               var1.setline(1525);
               var4 = var1.getglobal("sys").__getattr__("platform");
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(1526);
               var7 = PyString.fromInterned("");
               var1.setlocal(9, var7);
               var4 = null;
               var1.setline(1527);
               var7 = PyString.fromInterned("");
               var1.setlocal(10, var7);
               var4 = null;
            } else {
               var1.setline(1529);
               var7 = PyString.fromInterned("PyPy");
               var10000 = var7._in(var1.getlocal(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1531);
                  var7 = PyString.fromInterned("PyPy");
                  var1.setlocal(2, var7);
                  var4 = null;
                  var1.setline(1532);
                  var4 = var1.getglobal("_pypy_sys_version_parser").__getattr__("match").__call__(var2, var1.getlocal(0));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1533);
                  var4 = var1.getlocal(3);
                  var10000 = var4._is(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1534);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("failed to parse PyPy sys.version: %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(0)))));
                  }

                  var1.setline(1536);
                  var4 = var1.getlocal(3).__getattr__("groups").__call__(var2);
                  var5 = Py.unpackSequence(var4, 4);
                  var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var5[2];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var6 = var5[3];
                  var1.setlocal(11, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(1537);
                  var7 = PyString.fromInterned("");
                  var1.setlocal(6, var7);
                  var4 = null;
               } else {
                  var1.setline(1541);
                  var4 = var1.getglobal("_sys_version_parser").__getattr__("match").__call__(var2, var1.getlocal(0));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1542);
                  var4 = var1.getlocal(3);
                  var10000 = var4._is(var1.getglobal("None"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1543);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("failed to parse CPython sys.version: %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(0)))));
                  }

                  var1.setline(1546);
                  var4 = var1.getlocal(3).__getattr__("groups").__call__(var2);
                  var5 = Py.unpackSequence(var4, 5);
                  var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var5[2];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var6 = var5[3];
                  var1.setlocal(11, var6);
                  var6 = null;
                  var6 = var5[4];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(1548);
                  var7 = PyString.fromInterned("CPython");
                  var1.setlocal(2, var7);
                  var4 = null;
                  var1.setline(1549);
                  var4 = var1.getlocal(10)._add(PyString.fromInterned(" "))._add(var1.getlocal(11));
                  var1.setlocal(10, var4);
                  var4 = null;
               }
            }
         }

         var1.setline(1551);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys"), (PyObject)PyString.fromInterned("subversion")).__nonzero__()) {
            var1.setline(1553);
            var4 = var1.getglobal("sys").__getattr__("subversion");
            var5 = Py.unpackSequence(var4, 3);
            var6 = var5[0];
            var1.setlocal(12, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
         } else {
            var1.setline(1555);
            var7 = PyString.fromInterned("");
            var1.setlocal(7, var7);
            var4 = null;
            var1.setline(1556);
            var7 = PyString.fromInterned("");
            var1.setlocal(8, var7);
            var4 = null;
         }

         var1.setline(1559);
         var4 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("."));
         var1.setlocal(13, var4);
         var4 = null;
         var1.setline(1560);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(13));
         var10000 = var4._eq(Py.newInteger(2));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1561);
            var1.getlocal(13).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"));
            var1.setline(1562);
            var4 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(13), (PyObject)PyString.fromInterned("."));
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(1565);
         PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(6)});
         var1.setlocal(1, var8);
         var4 = null;
         var1.setline(1566);
         var4 = var1.getlocal(1);
         var1.getglobal("_sys_version_cache").__setitem__(var1.getlocal(0), var4);
         var4 = null;
         var1.setline(1567);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject python_implementation$41(PyFrame var1, ThreadState var2) {
      var1.setline(1579);
      PyString.fromInterned(" Returns a string identifying the Python implementation.\n\n        Currently, the following implementations are identified:\n          'CPython' (C implementation of Python),\n          'IronPython' (.NET implementation of Python),\n          'Jython' (Java implementation of Python),\n          'PyPy' (Python implementation of Python).\n\n    ");
      var1.setline(1580);
      PyObject var3 = var1.getglobal("_sys_version").__call__(var2).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject python_version$42(PyFrame var1, ThreadState var2) {
      var1.setline(1589);
      PyString.fromInterned(" Returns the Python version as string 'major.minor.patchlevel'\n\n        Note that unlike the Python sys.version, the returned value\n        will always include the patchlevel (it defaults to 0).\n\n    ");
      var1.setline(1590);
      PyObject var3 = var1.getglobal("_sys_version").__call__(var2).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject python_version_tuple$43(PyFrame var1, ThreadState var2) {
      var1.setline(1600);
      PyString.fromInterned(" Returns the Python version as tuple (major, minor, patchlevel)\n        of strings.\n\n        Note that unlike the Python sys.version, the returned value\n        will always include the patchlevel (it defaults to 0).\n\n    ");
      var1.setline(1601);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("_sys_version").__call__(var2).__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned(".")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject python_branch$44(PyFrame var1, ThreadState var2) {
      var1.setline(1613);
      PyString.fromInterned(" Returns a string identifying the Python implementation\n        branch.\n\n        For CPython this is the Subversion branch from which the\n        Python binary was built.\n\n        If not available, an empty string is returned.\n\n    ");
      var1.setline(1615);
      PyObject var3 = var1.getglobal("_sys_version").__call__(var2).__getitem__(Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject python_revision$45(PyFrame var1, ThreadState var2) {
      var1.setline(1627);
      PyString.fromInterned(" Returns a string identifying the Python implementation\n        revision.\n\n        For CPython this is the Subversion revision from which the\n        Python binary was built.\n\n        If not available, an empty string is returned.\n\n    ");
      var1.setline(1628);
      PyObject var3 = var1.getglobal("_sys_version").__call__(var2).__getitem__(Py.newInteger(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject python_build$46(PyFrame var1, ThreadState var2) {
      var1.setline(1635);
      PyString.fromInterned(" Returns a tuple (buildno, builddate) stating the Python\n        build number and date as strings.\n\n    ");
      var1.setline(1636);
      PyObject var3 = var1.getglobal("_sys_version").__call__(var2).__getslice__(Py.newInteger(4), Py.newInteger(6), (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject python_compiler$47(PyFrame var1, ThreadState var2) {
      var1.setline(1643);
      PyString.fromInterned(" Returns a string identifying the compiler used for compiling\n        Python.\n\n    ");
      var1.setline(1644);
      PyObject var3 = var1.getglobal("_sys_version").__call__(var2).__getitem__(Py.newInteger(6));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject platform$48(PyFrame var1, ThreadState var2) {
      var1.setline(1668);
      PyString.fromInterned(" Returns a single string identifying the underlying platform\n        with as much useful information as possible (but no more :).\n\n        The output is intended to be human readable rather than\n        machine parseable. It may look different on different\n        platforms and this is intended.\n\n        If \"aliased\" is true, the function will use aliases for\n        various platforms that report system names which differ from\n        their common names, e.g. SunOS will be reported as\n        Solaris. The system_alias() function is used to implement\n        this.\n\n        Setting terse to true causes the function to return only the\n        absolute minimum information needed to identify the platform.\n\n    ");
      var1.setline(1669);
      PyObject var3 = var1.getglobal("_platform_cache").__getattr__("get").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})), (PyObject)var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1670);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1671);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1675);
         PyObject var4 = var1.getglobal("uname").__call__(var2);
         PyObject[] var5 = Py.unpackSequence(var4, 6);
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
         var6 = var5[4];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var5[5];
         var1.setlocal(8, var6);
         var6 = null;
         var4 = null;
         var1.setline(1676);
         var4 = var1.getlocal(7);
         var10000 = var4._eq(var1.getlocal(8));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1677);
            PyString var9 = PyString.fromInterned("");
            var1.setlocal(8, var9);
            var4 = null;
         }

         var1.setline(1678);
         if (var1.getlocal(0).__nonzero__()) {
            var1.setline(1679);
            var4 = var1.getglobal("system_alias").__call__(var2, var1.getlocal(3), var1.getlocal(5), var1.getlocal(6));
            var5 = Py.unpackSequence(var4, 3);
            var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(6, var6);
            var6 = null;
            var4 = null;
         }

         var1.setline(1681);
         var4 = var1.getlocal(3);
         var10000 = var4._eq(PyString.fromInterned("Windows"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1683);
            var4 = var1.getglobal("win32_ver").__call__(var2, var1.getlocal(6));
            var5 = Py.unpackSequence(var4, 4);
            var6 = var5[0];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(10, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(11, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(12, var6);
            var6 = null;
            var4 = null;
            var1.setline(1684);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(1685);
               var4 = var1.getglobal("_platform").__call__(var2, var1.getlocal(3), var1.getlocal(5));
               var1.setlocal(13, var4);
               var4 = null;
            } else {
               var1.setline(1687);
               var4 = var1.getglobal("_platform").__call__(var2, var1.getlocal(3), var1.getlocal(5), var1.getlocal(6), var1.getlocal(11));
               var1.setlocal(13, var4);
               var4 = null;
            }
         } else {
            var1.setline(1689);
            var4 = var1.getlocal(3);
            var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("Linux")}));
            var4 = null;
            PyObject[] var10;
            if (var10000.__nonzero__()) {
               var1.setline(1691);
               var4 = var1.getglobal("dist").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
               var5 = Py.unpackSequence(var4, 3);
               var6 = var5[0];
               var1.setlocal(14, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(15, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(16, var6);
               var6 = null;
               var4 = null;
               var1.setline(1692);
               var10000 = var1.getlocal(14);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(1).__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1693);
                  var10000 = var1.getglobal("_platform");
                  var10 = new PyObject[]{var1.getlocal(3), var1.getlocal(5), var1.getlocal(7), var1.getlocal(8), PyString.fromInterned("with"), var1.getlocal(14), var1.getlocal(15), var1.getlocal(16)};
                  var4 = var10000.__call__(var2, var10);
                  var1.setlocal(13, var4);
                  var4 = null;
               } else {
                  var1.setline(1698);
                  var4 = var1.getglobal("libc_ver").__call__(var2, var1.getglobal("sys").__getattr__("executable"));
                  var5 = Py.unpackSequence(var4, 2);
                  var6 = var5[0];
                  var1.setlocal(17, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(18, var6);
                  var6 = null;
                  var4 = null;
                  var1.setline(1699);
                  var10000 = var1.getglobal("_platform");
                  var10 = new PyObject[]{var1.getlocal(3), var1.getlocal(5), var1.getlocal(7), var1.getlocal(8), PyString.fromInterned("with"), var1.getlocal(17)._add(var1.getlocal(18))};
                  var4 = var10000.__call__(var2, var10);
                  var1.setlocal(13, var4);
                  var4 = null;
               }
            } else {
               var1.setline(1702);
               var4 = var1.getlocal(3);
               var10000 = var4._eq(PyString.fromInterned("Java"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1704);
                  var4 = var1.getglobal("java_ver").__call__(var2);
                  var5 = Py.unpackSequence(var4, 4);
                  var6 = var5[0];
                  var1.setlocal(19, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(20, var6);
                  var6 = null;
                  var6 = var5[2];
                  var1.setlocal(21, var6);
                  var6 = null;
                  var6 = var5[3];
                  PyObject[] var7 = Py.unpackSequence(var6, 3);
                  PyObject var8 = var7[0];
                  var1.setlocal(22, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(23, var8);
                  var8 = null;
                  var8 = var7[2];
                  var1.setlocal(24, var8);
                  var8 = null;
                  var6 = null;
                  var4 = null;
                  var1.setline(1705);
                  var10000 = var1.getlocal(1);
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(22).__not__();
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1706);
                     var4 = var1.getglobal("_platform").__call__(var2, var1.getlocal(3), var1.getlocal(5), var1.getlocal(6));
                     var1.setlocal(13, var4);
                     var4 = null;
                  } else {
                     var1.setline(1708);
                     var10000 = var1.getglobal("_platform");
                     var10 = new PyObject[]{var1.getlocal(3), var1.getlocal(5), var1.getlocal(6), PyString.fromInterned("on"), var1.getlocal(22), var1.getlocal(23), var1.getlocal(24)};
                     var4 = var10000.__call__(var2, var10);
                     var1.setlocal(13, var4);
                     var4 = null;
                  }
               } else {
                  var1.setline(1712);
                  var4 = var1.getlocal(3);
                  var10000 = var4._eq(PyString.fromInterned("MacOS"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1714);
                     if (var1.getlocal(1).__nonzero__()) {
                        var1.setline(1715);
                        var4 = var1.getglobal("_platform").__call__(var2, var1.getlocal(3), var1.getlocal(5));
                        var1.setlocal(13, var4);
                        var4 = null;
                     } else {
                        var1.setline(1717);
                        var4 = var1.getglobal("_platform").__call__(var2, var1.getlocal(3), var1.getlocal(5), var1.getlocal(7));
                        var1.setlocal(13, var4);
                        var4 = null;
                     }
                  } else {
                     var1.setline(1721);
                     if (var1.getlocal(1).__nonzero__()) {
                        var1.setline(1722);
                        var4 = var1.getglobal("_platform").__call__(var2, var1.getlocal(3), var1.getlocal(5));
                        var1.setlocal(13, var4);
                        var4 = null;
                     } else {
                        var1.setline(1724);
                        var4 = var1.getglobal("architecture").__call__(var2, var1.getglobal("sys").__getattr__("executable"));
                        var5 = Py.unpackSequence(var4, 2);
                        var6 = var5[0];
                        var1.setlocal(25, var6);
                        var6 = null;
                        var6 = var5[1];
                        var1.setlocal(26, var6);
                        var6 = null;
                        var4 = null;
                        var1.setline(1725);
                        var10000 = var1.getglobal("_platform");
                        var10 = new PyObject[]{var1.getlocal(3), var1.getlocal(5), var1.getlocal(7), var1.getlocal(8), var1.getlocal(25), var1.getlocal(26)};
                        var4 = var10000.__call__(var2, var10);
                        var1.setlocal(13, var4);
                        var4 = null;
                     }
                  }
               }
            }
         }

         var1.setline(1727);
         var4 = var1.getlocal(13);
         var1.getglobal("_platform_cache").__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})), var4);
         var4 = null;
         var1.setline(1728);
         var3 = var1.getlocal(13);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public platform$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"executable", "lib", "version", "chunksize", "f", "binary", "pos", "m", "libcinit", "glibc", "glibcversion", "so", "threads", "soversion"};
      libc_ver$1 = Py.newCode(4, var2, var1, "libc_ver", 144, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"distname", "version", "id", "info", "line", "tv", "tag", "value", "values", "pkg", "verfiles", "n"};
      _dist_try_harder$2 = Py.newCode(3, var2, var1, "_dist_try_harder", 197, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"firstline", "version", "id", "m", "l"};
      _parse_release_file$3 = Py.newCode(1, var2, var1, "_parse_release_file", 267, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"distname", "version", "id", "supported_dists", "full_distribution_name", "etc", "file", "m", "_distname", "dummy", "f", "firstline", "_version", "_id"};
      linux_distribution$4 = Py.newCode(5, var2, var1, "linux_distribution", 294, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"distname", "version", "id", "supported_dists"};
      dist$5 = Py.newCode(4, var2, var1, "dist", 350, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _popen$6 = Py.newCode(0, var2, var1, "_popen", 368, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "cmd", "mode", "bufsize", "tempfile", "tmpfile"};
      __init__$7 = Py.newCode(4, var2, var1, "__init__", 384, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      read$8 = Py.newCode(1, var2, var1, "read", 395, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readlines$9 = Py.newCode(1, var2, var1, "readlines", 399, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "remove", "error", "rc"};
      close$10 = Py.newCode(3, var2, var1, "close", 404, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "mode", "bufsize", "popen", "win32pipe"};
      popen$11 = Py.newCode(3, var2, var1, "popen", 422, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"version", "build", "l", "ints", "strings"};
      _norm_version$12 = Py.newCode(2, var2, var1, "_norm_version", 455, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"system", "release", "version", "supported_platforms", "cmd", "pipe", "info", "why", "m"};
      _syscmd_ver$13 = Py.newCode(4, var2, var1, "_syscmd_ver", 485, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"key", "name", "default", "RegQueryValueEx", "_winreg"};
      _win32_getvalue$14 = Py.newCode(3, var2, var1, "_win32_getvalue", 537, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"release", "version", "csd", "ptype", "win32api", "RegQueryValueEx", "RegOpenKeyEx", "RegCloseKey", "GetVersionEx", "HKEY_LOCAL_MACHINE", "VER_PLATFORM_WIN32_NT", "VER_PLATFORM_WIN32_WINDOWS", "VER_NT_WORKSTATION", "unm", "_winreg", "VER_NT_SERVER", "REG_SZ", "winver", "maj", "min", "buildno", "plat", "regkey", "product_type", "key", "name", "type", "keyCurVer", "build"};
      win32_ver$15 = Py.newCode(4, var2, var1, "win32_ver", 556, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"selectors", "default", "gestalt", "MacOS", "l", "append", "selector"};
      _mac_ver_lookup$16 = Py.newCode(2, var2, var1, "_mac_ver_lookup", 727, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"bcd"};
      _bcd2str$17 = Py.newCode(1, var2, var1, "_bcd2str", 740, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"gestalt", "MacOS", "sysv", "sysa", "major", "minor", "patch", "release", "machine", "versioninfo"};
      _mac_ver_gestalt$18 = Py.newCode(0, var2, var1, "_mac_ver_gestalt", 744, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fn", "plistlib", "pl", "release", "versioninfo", "machine"};
      _mac_ver_xml$19 = Py.newCode(0, var2, var1, "_mac_ver_xml", 785, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"release", "versioninfo", "machine", "info", "res_release"};
      mac_ver$20 = Py.newCode(3, var2, var1, "mac_ver", 807, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "default", "value"};
      _java_getprop$21 = Py.newCode(2, var2, var1, "_java_getprop", 836, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "default", "value"};
      _java_getenv$22 = Py.newCode(2, var2, var1, "_java_getenv", 846, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"release", "vendor", "vminfo", "osinfo", "java", "vm_name", "vm_release", "vm_vendor", "os_name", "os_version", "os_arch"};
      java_ver$23 = Py.newCode(4, var2, var1, "java_ver", 855, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"system", "release", "version", "l", "major"};
      system_alias$24 = Py.newCode(3, var2, var1, "system_alias", 890, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "platform", "replace", "cleaned"};
      _platform$25 = Py.newCode(1, var2, var1, "_platform", 944, true, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"default", "socket"};
      _node$26 = Py.newCode(1, var2, var1, "_node", 980, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "isabs", "join", "getcwd", "normpath"};
      _abspath$27 = Py.newCode(5, var2, var1, "_abspath", 998, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filepath"};
      _follow_symlinks$28 = Py.newCode(1, var2, var1, "_follow_symlinks", 1011, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"option", "default", "f", "output", "rc"};
      _syscmd_uname$29 = Py.newCode(2, var2, var1, "_syscmd_uname", 1022, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"target", "default", "subprocess", "proc", "output", "rc"};
      _syscmd_file$30 = Py.newCode(2, var2, var1, "_syscmd_file", 1041, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"executable", "bits", "linkage", "struct", "size", "output", "b", "l", "fileout"};
      architecture$31 = Py.newCode(3, var2, var1, "architecture", 1100, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"processor", "node", "machine", "release", "vendor", "vminfo", "osinfo", "system", "version"};
      uname$32 = Py.newCode(0, var2, var1, "uname", 1189, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"no_os_uname", "processor", "system", "node", "release", "version", "machine", "use_syscmd_ver", "csd", "ptype", "vendor", "vminfo", "osinfo", "vms_lib", "csid", "cpu_number"};
      _uname$33 = Py.newCode(0, var2, var1, "_uname", 1247, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      system$34 = Py.newCode(0, var2, var1, "system", 1389, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      node$35 = Py.newCode(0, var2, var1, "node", 1398, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      release$36 = Py.newCode(0, var2, var1, "release", 1408, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      version$37 = Py.newCode(0, var2, var1, "version", 1417, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      machine$38 = Py.newCode(0, var2, var1, "machine", 1426, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      processor$39 = Py.newCode(0, var2, var1, "processor", 1435, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sys_version", "result", "name", "match", "version", "alt_version", "compiler", "branch", "revision", "buildno", "builddate", "buildtime", "_", "l"};
      _sys_version$40 = Py.newCode(1, var2, var1, "_sys_version", 1470, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      python_implementation$41 = Py.newCode(0, var2, var1, "python_implementation", 1569, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      python_version$42 = Py.newCode(0, var2, var1, "python_version", 1582, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      python_version_tuple$43 = Py.newCode(0, var2, var1, "python_version_tuple", 1592, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      python_branch$44 = Py.newCode(0, var2, var1, "python_branch", 1603, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      python_revision$45 = Py.newCode(0, var2, var1, "python_revision", 1617, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      python_build$46 = Py.newCode(0, var2, var1, "python_build", 1630, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      python_compiler$47 = Py.newCode(0, var2, var1, "python_compiler", 1638, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"aliased", "terse", "result", "system", "node", "release", "version", "machine", "processor", "rel", "vers", "csd", "ptype", "platform", "distname", "distversion", "distid", "libcname", "libcversion", "r", "v", "vminfo", "os_name", "os_version", "os_arch", "bits", "linkage"};
      platform$48 = Py.newCode(2, var2, var1, "platform", 1650, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new platform$py("platform$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(platform$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.libc_ver$1(var2, var3);
         case 2:
            return this._dist_try_harder$2(var2, var3);
         case 3:
            return this._parse_release_file$3(var2, var3);
         case 4:
            return this.linux_distribution$4(var2, var3);
         case 5:
            return this.dist$5(var2, var3);
         case 6:
            return this._popen$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.read$8(var2, var3);
         case 9:
            return this.readlines$9(var2, var3);
         case 10:
            return this.close$10(var2, var3);
         case 11:
            return this.popen$11(var2, var3);
         case 12:
            return this._norm_version$12(var2, var3);
         case 13:
            return this._syscmd_ver$13(var2, var3);
         case 14:
            return this._win32_getvalue$14(var2, var3);
         case 15:
            return this.win32_ver$15(var2, var3);
         case 16:
            return this._mac_ver_lookup$16(var2, var3);
         case 17:
            return this._bcd2str$17(var2, var3);
         case 18:
            return this._mac_ver_gestalt$18(var2, var3);
         case 19:
            return this._mac_ver_xml$19(var2, var3);
         case 20:
            return this.mac_ver$20(var2, var3);
         case 21:
            return this._java_getprop$21(var2, var3);
         case 22:
            return this._java_getenv$22(var2, var3);
         case 23:
            return this.java_ver$23(var2, var3);
         case 24:
            return this.system_alias$24(var2, var3);
         case 25:
            return this._platform$25(var2, var3);
         case 26:
            return this._node$26(var2, var3);
         case 27:
            return this._abspath$27(var2, var3);
         case 28:
            return this._follow_symlinks$28(var2, var3);
         case 29:
            return this._syscmd_uname$29(var2, var3);
         case 30:
            return this._syscmd_file$30(var2, var3);
         case 31:
            return this.architecture$31(var2, var3);
         case 32:
            return this.uname$32(var2, var3);
         case 33:
            return this._uname$33(var2, var3);
         case 34:
            return this.system$34(var2, var3);
         case 35:
            return this.node$35(var2, var3);
         case 36:
            return this.release$36(var2, var3);
         case 37:
            return this.version$37(var2, var3);
         case 38:
            return this.machine$38(var2, var3);
         case 39:
            return this.processor$39(var2, var3);
         case 40:
            return this._sys_version$40(var2, var3);
         case 41:
            return this.python_implementation$41(var2, var3);
         case 42:
            return this.python_version$42(var2, var3);
         case 43:
            return this.python_version_tuple$43(var2, var3);
         case 44:
            return this.python_branch$44(var2, var3);
         case 45:
            return this.python_revision$45(var2, var3);
         case 46:
            return this.python_build$46(var2, var3);
         case 47:
            return this.python_compiler$47(var2, var3);
         case 48:
            return this.platform$48(var2, var3);
         default:
            return null;
      }
   }
}
