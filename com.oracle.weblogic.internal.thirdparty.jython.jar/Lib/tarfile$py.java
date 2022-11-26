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
@Filename("tarfile.py")
public class tarfile$py extends PyFunctionTable implements PyRunnable {
   static tarfile$py self;
   static final PyCode f$0;
   static final PyCode stn$1;
   static final PyCode nts$2;
   static final PyCode nti$3;
   static final PyCode itn$4;
   static final PyCode uts$5;
   static final PyCode calc_chksums$6;
   static final PyCode copyfileobj$7;
   static final PyCode filemode$8;
   static final PyCode TarError$9;
   static final PyCode ExtractError$10;
   static final PyCode ReadError$11;
   static final PyCode CompressionError$12;
   static final PyCode StreamError$13;
   static final PyCode HeaderError$14;
   static final PyCode EmptyHeaderError$15;
   static final PyCode TruncatedHeaderError$16;
   static final PyCode EOFHeaderError$17;
   static final PyCode InvalidHeaderError$18;
   static final PyCode SubsequentHeaderError$19;
   static final PyCode _LowLevelFile$20;
   static final PyCode __init__$21;
   static final PyCode close$22;
   static final PyCode read$23;
   static final PyCode write$24;
   static final PyCode _Stream$25;
   static final PyCode __init__$26;
   static final PyCode __del__$27;
   static final PyCode _init_write_gz$28;
   static final PyCode write$29;
   static final PyCode _Stream__write$30;
   static final PyCode close$31;
   static final PyCode _init_read_gz$32;
   static final PyCode tell$33;
   static final PyCode seek$34;
   static final PyCode read$35;
   static final PyCode _read$36;
   static final PyCode _Stream__read$37;
   static final PyCode _StreamProxy$38;
   static final PyCode __init__$39;
   static final PyCode read$40;
   static final PyCode getcomptype$41;
   static final PyCode close$42;
   static final PyCode _BZ2Proxy$43;
   static final PyCode __init__$44;
   static final PyCode init$45;
   static final PyCode read$46;
   static final PyCode seek$47;
   static final PyCode tell$48;
   static final PyCode write$49;
   static final PyCode close$50;
   static final PyCode _FileInFile$51;
   static final PyCode __init__$52;
   static final PyCode tell$53;
   static final PyCode seek$54;
   static final PyCode read$55;
   static final PyCode readnormal$56;
   static final PyCode readsparse$57;
   static final PyCode readsparsesection$58;
   static final PyCode ExFileObject$59;
   static final PyCode __init__$60;
   static final PyCode read$61;
   static final PyCode readline$62;
   static final PyCode readlines$63;
   static final PyCode tell$64;
   static final PyCode seek$65;
   static final PyCode close$66;
   static final PyCode __iter__$67;
   static final PyCode TarInfo$68;
   static final PyCode __init__$69;
   static final PyCode _getpath$70;
   static final PyCode _setpath$71;
   static final PyCode _getlinkpath$72;
   static final PyCode _setlinkpath$73;
   static final PyCode __repr__$74;
   static final PyCode get_info$75;
   static final PyCode tobuf$76;
   static final PyCode create_ustar_header$77;
   static final PyCode create_gnu_header$78;
   static final PyCode create_pax_header$79;
   static final PyCode create_pax_global_header$80;
   static final PyCode _posix_split_name$81;
   static final PyCode _create_header$82;
   static final PyCode _create_payload$83;
   static final PyCode _create_gnu_long_header$84;
   static final PyCode _create_pax_generic_header$85;
   static final PyCode frombuf$86;
   static final PyCode fromtarfile$87;
   static final PyCode _proc_member$88;
   static final PyCode _proc_builtin$89;
   static final PyCode _proc_gnulong$90;
   static final PyCode _proc_sparse$91;
   static final PyCode _proc_pax$92;
   static final PyCode _apply_pax_info$93;
   static final PyCode _block$94;
   static final PyCode isreg$95;
   static final PyCode isfile$96;
   static final PyCode isdir$97;
   static final PyCode issym$98;
   static final PyCode islnk$99;
   static final PyCode ischr$100;
   static final PyCode isblk$101;
   static final PyCode isfifo$102;
   static final PyCode issparse$103;
   static final PyCode isdev$104;
   static final PyCode TarFile$105;
   static final PyCode __init__$106;
   static final PyCode _getposix$107;
   static final PyCode _setposix$108;
   static final PyCode open$109;
   static final PyCode taropen$110;
   static final PyCode gzopen$111;
   static final PyCode bz2open$112;
   static final PyCode close$113;
   static final PyCode getmember$114;
   static final PyCode getmembers$115;
   static final PyCode getnames$116;
   static final PyCode gettarinfo$117;
   static final PyCode list$118;
   static final PyCode add$119;
   static final PyCode addfile$120;
   static final PyCode extractall$121;
   static final PyCode extract$122;
   static final PyCode extractfile$123;
   static final PyCode _extract_member$124;
   static final PyCode makedir$125;
   static final PyCode makefile$126;
   static final PyCode makeunknown$127;
   static final PyCode makefifo$128;
   static final PyCode makedev$129;
   static final PyCode makelink$130;
   static final PyCode chown$131;
   static final PyCode chmod$132;
   static final PyCode utime$133;
   static final PyCode next$134;
   static final PyCode _getmember$135;
   static final PyCode _load$136;
   static final PyCode _check$137;
   static final PyCode _find_link_target$138;
   static final PyCode __iter__$139;
   static final PyCode _dbg$140;
   static final PyCode __enter__$141;
   static final PyCode __exit__$142;
   static final PyCode TarIter$143;
   static final PyCode __init__$144;
   static final PyCode __iter__$145;
   static final PyCode next$146;
   static final PyCode _section$147;
   static final PyCode __init__$148;
   static final PyCode __contains__$149;
   static final PyCode _data$150;
   static final PyCode __init__$151;
   static final PyCode _hole$152;
   static final PyCode _ringbuffer$153;
   static final PyCode __init__$154;
   static final PyCode find$155;
   static final PyCode TarFileCompat$156;
   static final PyCode __init__$157;
   static final PyCode namelist$158;
   static final PyCode f$159;
   static final PyCode infolist$160;
   static final PyCode f$161;
   static final PyCode printdir$162;
   static final PyCode testzip$163;
   static final PyCode getinfo$164;
   static final PyCode read$165;
   static final PyCode write$166;
   static final PyCode writestr$167;
   static final PyCode close$168;
   static final PyCode is_tarfile$169;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Read from and write to tar format archives.\n"));
      var1.setline(31);
      PyString.fromInterned("Read from and write to tar format archives.\n");
      var1.setline(33);
      PyString var3 = PyString.fromInterned("$Revision: 85213 $");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(36);
      var3 = PyString.fromInterned("0.9.0");
      var1.setlocal("version", var3);
      var3 = null;
      var1.setline(37);
      var3 = PyString.fromInterned("Lars Gustäbel (lars@gustaebel.de)");
      var1.setlocal("__author__", var3);
      var3 = null;
      var1.setline(38);
      var3 = PyString.fromInterned("$Date$");
      var1.setlocal("__date__", var3);
      var3 = null;
      var1.setline(39);
      var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__cvsid__", var3);
      var3 = null;
      var1.setline(40);
      var3 = PyString.fromInterned("Gustavo Niemeyer, Niels Gustäbel, Richard Townsend.");
      var1.setlocal("__credits__", var3);
      var3 = null;
      var1.setline(45);
      PyObject var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(46);
      var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(47);
      var6 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var6);
      var3 = null;
      var1.setline(48);
      var6 = imp.importOne("stat", var1, -1);
      var1.setlocal("stat", var6);
      var3 = null;
      var1.setline(49);
      var6 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var6);
      var3 = null;
      var1.setline(50);
      var6 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var6);
      var3 = null;
      var1.setline(51);
      var6 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var6);
      var3 = null;
      var1.setline(52);
      var6 = imp.importOne("copy", var1, -1);
      var1.setlocal("copy", var6);
      var3 = null;
      var1.setline(53);
      var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(54);
      var6 = imp.importOne("operator", var1, -1);
      var1.setlocal("operator", var6);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(57);
         var6 = imp.importOne("grp", var1, -1);
         var1.setlocal("grp", var6);
         var3 = null;
         var6 = imp.importOne("pwd", var1, -1);
         var1.setlocal("pwd", var6);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(59);
         var4 = var1.getname("None");
         var1.setlocal("grp", var4);
         var1.setlocal("pwd", var4);
      }

      var1.setline(62);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("TarFile"), PyString.fromInterned("TarInfo"), PyString.fromInterned("is_tarfile"), PyString.fromInterned("TarError")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(67);
      var3 = PyString.fromInterned("\u0000");
      var1.setlocal("NUL", var3);
      var3 = null;
      var1.setline(68);
      PyInteger var9 = Py.newInteger(512);
      var1.setlocal("BLOCKSIZE", var9);
      var3 = null;
      var1.setline(69);
      var6 = var1.getname("BLOCKSIZE")._mul(Py.newInteger(20));
      var1.setlocal("RECORDSIZE", var6);
      var3 = null;
      var1.setline(70);
      var3 = PyString.fromInterned("ustar  \u0000");
      var1.setlocal("GNU_MAGIC", var3);
      var3 = null;
      var1.setline(71);
      var3 = PyString.fromInterned("ustar\u000000");
      var1.setlocal("POSIX_MAGIC", var3);
      var3 = null;
      var1.setline(73);
      var9 = Py.newInteger(100);
      var1.setlocal("LENGTH_NAME", var9);
      var3 = null;
      var1.setline(74);
      var9 = Py.newInteger(100);
      var1.setlocal("LENGTH_LINK", var9);
      var3 = null;
      var1.setline(75);
      var9 = Py.newInteger(155);
      var1.setlocal("LENGTH_PREFIX", var9);
      var3 = null;
      var1.setline(77);
      var3 = PyString.fromInterned("0");
      var1.setlocal("REGTYPE", var3);
      var3 = null;
      var1.setline(78);
      var3 = PyString.fromInterned("\u0000");
      var1.setlocal("AREGTYPE", var3);
      var3 = null;
      var1.setline(79);
      var3 = PyString.fromInterned("1");
      var1.setlocal("LNKTYPE", var3);
      var3 = null;
      var1.setline(80);
      var3 = PyString.fromInterned("2");
      var1.setlocal("SYMTYPE", var3);
      var3 = null;
      var1.setline(81);
      var3 = PyString.fromInterned("3");
      var1.setlocal("CHRTYPE", var3);
      var3 = null;
      var1.setline(82);
      var3 = PyString.fromInterned("4");
      var1.setlocal("BLKTYPE", var3);
      var3 = null;
      var1.setline(83);
      var3 = PyString.fromInterned("5");
      var1.setlocal("DIRTYPE", var3);
      var3 = null;
      var1.setline(84);
      var3 = PyString.fromInterned("6");
      var1.setlocal("FIFOTYPE", var3);
      var3 = null;
      var1.setline(85);
      var3 = PyString.fromInterned("7");
      var1.setlocal("CONTTYPE", var3);
      var3 = null;
      var1.setline(87);
      var3 = PyString.fromInterned("L");
      var1.setlocal("GNUTYPE_LONGNAME", var3);
      var3 = null;
      var1.setline(88);
      var3 = PyString.fromInterned("K");
      var1.setlocal("GNUTYPE_LONGLINK", var3);
      var3 = null;
      var1.setline(89);
      var3 = PyString.fromInterned("S");
      var1.setlocal("GNUTYPE_SPARSE", var3);
      var3 = null;
      var1.setline(91);
      var3 = PyString.fromInterned("x");
      var1.setlocal("XHDTYPE", var3);
      var3 = null;
      var1.setline(92);
      var3 = PyString.fromInterned("g");
      var1.setlocal("XGLTYPE", var3);
      var3 = null;
      var1.setline(93);
      var3 = PyString.fromInterned("X");
      var1.setlocal("SOLARIS_XHDTYPE", var3);
      var3 = null;
      var1.setline(95);
      var9 = Py.newInteger(0);
      var1.setlocal("USTAR_FORMAT", var9);
      var3 = null;
      var1.setline(96);
      var9 = Py.newInteger(1);
      var1.setlocal("GNU_FORMAT", var9);
      var3 = null;
      var1.setline(97);
      var9 = Py.newInteger(2);
      var1.setlocal("PAX_FORMAT", var9);
      var3 = null;
      var1.setline(98);
      var6 = var1.getname("GNU_FORMAT");
      var1.setlocal("DEFAULT_FORMAT", var6);
      var3 = null;
      var1.setline(104);
      PyTuple var10 = new PyTuple(new PyObject[]{var1.getname("REGTYPE"), var1.getname("AREGTYPE"), var1.getname("LNKTYPE"), var1.getname("SYMTYPE"), var1.getname("DIRTYPE"), var1.getname("FIFOTYPE"), var1.getname("CONTTYPE"), var1.getname("CHRTYPE"), var1.getname("BLKTYPE"), var1.getname("GNUTYPE_LONGNAME"), var1.getname("GNUTYPE_LONGLINK"), var1.getname("GNUTYPE_SPARSE")});
      var1.setlocal("SUPPORTED_TYPES", var10);
      var3 = null;
      var1.setline(111);
      var10 = new PyTuple(new PyObject[]{var1.getname("REGTYPE"), var1.getname("AREGTYPE"), var1.getname("CONTTYPE"), var1.getname("GNUTYPE_SPARSE")});
      var1.setlocal("REGULAR_TYPES", var10);
      var3 = null;
      var1.setline(115);
      var10 = new PyTuple(new PyObject[]{var1.getname("GNUTYPE_LONGNAME"), var1.getname("GNUTYPE_LONGLINK"), var1.getname("GNUTYPE_SPARSE")});
      var1.setlocal("GNU_TYPES", var10);
      var3 = null;
      var1.setline(119);
      var10 = new PyTuple(new PyObject[]{PyString.fromInterned("path"), PyString.fromInterned("linkpath"), PyString.fromInterned("size"), PyString.fromInterned("mtime"), PyString.fromInterned("uid"), PyString.fromInterned("gid"), PyString.fromInterned("uname"), PyString.fromInterned("gname")});
      var1.setlocal("PAX_FIELDS", var10);
      var3 = null;
      var1.setline(124);
      PyDictionary var11 = new PyDictionary(new PyObject[]{PyString.fromInterned("atime"), var1.getname("float"), PyString.fromInterned("ctime"), var1.getname("float"), PyString.fromInterned("mtime"), var1.getname("float"), PyString.fromInterned("uid"), var1.getname("int"), PyString.fromInterned("gid"), var1.getname("int"), PyString.fromInterned("size"), var1.getname("int")});
      var1.setlocal("PAX_NUMBER_FIELDS", var11);
      var3 = null;
      var1.setline(136);
      var9 = Py.newInteger(40960);
      var1.setlocal("S_IFLNK", var9);
      var3 = null;
      var1.setline(137);
      var9 = Py.newInteger(32768);
      var1.setlocal("S_IFREG", var9);
      var3 = null;
      var1.setline(138);
      var9 = Py.newInteger(24576);
      var1.setlocal("S_IFBLK", var9);
      var3 = null;
      var1.setline(139);
      var9 = Py.newInteger(16384);
      var1.setlocal("S_IFDIR", var9);
      var3 = null;
      var1.setline(140);
      var9 = Py.newInteger(8192);
      var1.setlocal("S_IFCHR", var9);
      var3 = null;
      var1.setline(141);
      var9 = Py.newInteger(4096);
      var1.setlocal("S_IFIFO", var9);
      var3 = null;
      var1.setline(143);
      var9 = Py.newInteger(2048);
      var1.setlocal("TSUID", var9);
      var3 = null;
      var1.setline(144);
      var9 = Py.newInteger(1024);
      var1.setlocal("TSGID", var9);
      var3 = null;
      var1.setline(145);
      var9 = Py.newInteger(512);
      var1.setlocal("TSVTX", var9);
      var3 = null;
      var1.setline(147);
      var9 = Py.newInteger(256);
      var1.setlocal("TUREAD", var9);
      var3 = null;
      var1.setline(148);
      var9 = Py.newInteger(128);
      var1.setlocal("TUWRITE", var9);
      var3 = null;
      var1.setline(149);
      var9 = Py.newInteger(64);
      var1.setlocal("TUEXEC", var9);
      var3 = null;
      var1.setline(150);
      var9 = Py.newInteger(32);
      var1.setlocal("TGREAD", var9);
      var3 = null;
      var1.setline(151);
      var9 = Py.newInteger(16);
      var1.setlocal("TGWRITE", var9);
      var3 = null;
      var1.setline(152);
      var9 = Py.newInteger(8);
      var1.setlocal("TGEXEC", var9);
      var3 = null;
      var1.setline(153);
      var9 = Py.newInteger(4);
      var1.setlocal("TOREAD", var9);
      var3 = null;
      var1.setline(154);
      var9 = Py.newInteger(2);
      var1.setlocal("TOWRITE", var9);
      var3 = null;
      var1.setline(155);
      var9 = Py.newInteger(1);
      var1.setlocal("TOEXEC", var9);
      var3 = null;
      var1.setline(160);
      var6 = var1.getname("sys").__getattr__("getfilesystemencoding").__call__(var2);
      var1.setlocal("ENCODING", var6);
      var3 = null;
      var1.setline(161);
      var6 = var1.getname("ENCODING");
      PyObject var10000 = var6._is(var1.getname("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(162);
         var6 = var1.getname("sys").__getattr__("getdefaultencoding").__call__(var2);
         var1.setlocal("ENCODING", var6);
         var3 = null;
      }

      var1.setline(168);
      PyObject[] var12 = Py.EmptyObjects;
      PyFunction var13 = new PyFunction(var1.f_globals, var12, stn$1, PyString.fromInterned("Convert a python string to a null-terminated string buffer.\n    "));
      var1.setlocal("stn", var13);
      var3 = null;
      var1.setline(173);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, nts$2, PyString.fromInterned("Convert a null-terminated string field to a python string.\n    "));
      var1.setlocal("nts", var13);
      var3 = null;
      var1.setline(182);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, nti$3, PyString.fromInterned("Convert a number field to a python number.\n    "));
      var1.setlocal("nti", var13);
      var3 = null;
      var1.setline(199);
      var12 = new PyObject[]{Py.newInteger(8), var1.getname("DEFAULT_FORMAT")};
      var13 = new PyFunction(var1.f_globals, var12, itn$4, PyString.fromInterned("Convert a python number to a number field.\n    "));
      var1.setlocal("itn", var13);
      var3 = null;
      var1.setline(226);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, uts$5, PyString.fromInterned("Convert a unicode object to a string.\n    "));
      var1.setlocal("uts", var13);
      var3 = null;
      var1.setline(246);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, calc_chksums$6, PyString.fromInterned("Calculate the checksum for a member's header by summing up all\n       characters except for the chksum field which is treated as if\n       it was filled with spaces. According to the GNU tar sources,\n       some tars (Sun and NeXT) calculate chksum with signed char,\n       which will be different if there are chars in the buffer with\n       the high bit set. So we calculate two checksums, unsigned and\n       signed.\n    "));
      var1.setlocal("calc_chksums", var13);
      var3 = null;
      var1.setline(259);
      var12 = new PyObject[]{var1.getname("None")};
      var13 = new PyFunction(var1.f_globals, var12, copyfileobj$7, PyString.fromInterned("Copy length bytes from fileobj src to fileobj dst.\n       If length is None, copy the entire content.\n    "));
      var1.setlocal("copyfileobj", var13);
      var3 = null;
      var1.setline(284);
      var10 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("S_IFLNK"), PyString.fromInterned("l")}), new PyTuple(new PyObject[]{var1.getname("S_IFREG"), PyString.fromInterned("-")}), new PyTuple(new PyObject[]{var1.getname("S_IFBLK"), PyString.fromInterned("b")}), new PyTuple(new PyObject[]{var1.getname("S_IFDIR"), PyString.fromInterned("d")}), new PyTuple(new PyObject[]{var1.getname("S_IFCHR"), PyString.fromInterned("c")}), new PyTuple(new PyObject[]{var1.getname("S_IFIFO"), PyString.fromInterned("p")})}), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("TUREAD"), PyString.fromInterned("r")})}), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("TUWRITE"), PyString.fromInterned("w")})}), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("TUEXEC")._or(var1.getname("TSUID")), PyString.fromInterned("s")}), new PyTuple(new PyObject[]{var1.getname("TSUID"), PyString.fromInterned("S")}), new PyTuple(new PyObject[]{var1.getname("TUEXEC"), PyString.fromInterned("x")})}), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("TGREAD"), PyString.fromInterned("r")})}), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("TGWRITE"), PyString.fromInterned("w")})}), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("TGEXEC")._or(var1.getname("TSGID")), PyString.fromInterned("s")}), new PyTuple(new PyObject[]{var1.getname("TSGID"), PyString.fromInterned("S")}), new PyTuple(new PyObject[]{var1.getname("TGEXEC"), PyString.fromInterned("x")})}), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("TOREAD"), PyString.fromInterned("r")})}), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("TOWRITE"), PyString.fromInterned("w")})}), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("TOEXEC")._or(var1.getname("TSVTX")), PyString.fromInterned("t")}), new PyTuple(new PyObject[]{var1.getname("TSVTX"), PyString.fromInterned("T")}), new PyTuple(new PyObject[]{var1.getname("TOEXEC"), PyString.fromInterned("x")})})});
      var1.setlocal("filemode_table", var10);
      var3 = null;
      var1.setline(311);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, filemode$8, PyString.fromInterned("Convert a file's mode to a string of the form\n       -rwxrwxrwx.\n       Used by TarFile.list()\n    "));
      var1.setlocal("filemode", var13);
      var3 = null;
      var1.setline(326);
      var12 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("TarError", var12, TarError$9);
      var1.setlocal("TarError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(329);
      var12 = new PyObject[]{var1.getname("TarError")};
      var4 = Py.makeClass("ExtractError", var12, ExtractError$10);
      var1.setlocal("ExtractError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(332);
      var12 = new PyObject[]{var1.getname("TarError")};
      var4 = Py.makeClass("ReadError", var12, ReadError$11);
      var1.setlocal("ReadError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(335);
      var12 = new PyObject[]{var1.getname("TarError")};
      var4 = Py.makeClass("CompressionError", var12, CompressionError$12);
      var1.setlocal("CompressionError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(338);
      var12 = new PyObject[]{var1.getname("TarError")};
      var4 = Py.makeClass("StreamError", var12, StreamError$13);
      var1.setlocal("StreamError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(341);
      var12 = new PyObject[]{var1.getname("TarError")};
      var4 = Py.makeClass("HeaderError", var12, HeaderError$14);
      var1.setlocal("HeaderError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(344);
      var12 = new PyObject[]{var1.getname("HeaderError")};
      var4 = Py.makeClass("EmptyHeaderError", var12, EmptyHeaderError$15);
      var1.setlocal("EmptyHeaderError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(347);
      var12 = new PyObject[]{var1.getname("HeaderError")};
      var4 = Py.makeClass("TruncatedHeaderError", var12, TruncatedHeaderError$16);
      var1.setlocal("TruncatedHeaderError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(350);
      var12 = new PyObject[]{var1.getname("HeaderError")};
      var4 = Py.makeClass("EOFHeaderError", var12, EOFHeaderError$17);
      var1.setlocal("EOFHeaderError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(353);
      var12 = new PyObject[]{var1.getname("HeaderError")};
      var4 = Py.makeClass("InvalidHeaderError", var12, InvalidHeaderError$18);
      var1.setlocal("InvalidHeaderError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(356);
      var12 = new PyObject[]{var1.getname("HeaderError")};
      var4 = Py.makeClass("SubsequentHeaderError", var12, SubsequentHeaderError$19);
      var1.setlocal("SubsequentHeaderError", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(363);
      var12 = Py.EmptyObjects;
      var4 = Py.makeClass("_LowLevelFile", var12, _LowLevelFile$20);
      var1.setlocal("_LowLevelFile", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(387);
      var12 = Py.EmptyObjects;
      var4 = Py.makeClass("_Stream", var12, _Stream$25);
      var1.setlocal("_Stream", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(616);
      var12 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_StreamProxy", var12, _StreamProxy$38);
      var1.setlocal("_StreamProxy", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(640);
      var12 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_BZ2Proxy", var12, _BZ2Proxy$43);
      var1.setlocal("_BZ2Proxy", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(705);
      var12 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_FileInFile", var12, _FileInFile$51);
      var1.setlocal("_FileInFile", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(781);
      var12 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ExFileObject", var12, ExFileObject$59);
      var1.setlocal("ExFileObject", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(912);
      var12 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TarInfo", var12, TarInfo$68);
      var1.setlocal("TarInfo", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(1475);
      var12 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TarFile", var12, TarFile$105);
      var1.setlocal("TarFile", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(2439);
      var12 = Py.EmptyObjects;
      var4 = Py.makeClass("TarIter", var12, TarIter$143);
      var1.setlocal("TarIter", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(2476);
      var12 = Py.EmptyObjects;
      var4 = Py.makeClass("_section", var12, _section$147);
      var1.setlocal("_section", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(2485);
      var12 = new PyObject[]{var1.getname("_section")};
      var4 = Py.makeClass("_data", var12, _data$150);
      var1.setlocal("_data", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(2492);
      var12 = new PyObject[]{var1.getname("_section")};
      var4 = Py.makeClass("_hole", var12, _hole$152);
      var1.setlocal("_hole", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(2497);
      var12 = new PyObject[]{var1.getname("list")};
      var4 = Py.makeClass("_ringbuffer", var12, _ringbuffer$153);
      var1.setlocal("_ringbuffer", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(2521);
      var9 = Py.newInteger(0);
      var1.setlocal("TAR_PLAIN", var9);
      var3 = null;
      var1.setline(2522);
      var9 = Py.newInteger(8);
      var1.setlocal("TAR_GZIPPED", var9);
      var3 = null;
      var1.setline(2523);
      var12 = Py.EmptyObjects;
      var4 = Py.makeClass("TarFileCompat", var12, TarFileCompat$156);
      var1.setlocal("TarFileCompat", var4);
      var4 = null;
      Arrays.fill(var12, (Object)null);
      var1.setline(2575);
      var12 = Py.EmptyObjects;
      var13 = new PyFunction(var1.f_globals, var12, is_tarfile$169, PyString.fromInterned("Return True if name points to a tar archive that we\n       are able to handle, else return False.\n    "));
      var1.setlocal("is_tarfile", var13);
      var3 = null;
      var1.setline(2586);
      var6 = var1.getname("open");
      var1.setlocal("bltn_open", var6);
      var3 = null;
      var1.setline(2587);
      var6 = var1.getname("TarFile").__getattr__("open");
      var1.setlocal("open", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stn$1(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyString.fromInterned("Convert a python string to a null-terminated string buffer.\n    ");
      var1.setline(171);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null)._add(var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0)))._mul(var1.getglobal("NUL")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject nts$2(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyString.fromInterned("Convert a null-terminated string field to a python string.\n    ");
      var1.setline(177);
      PyObject var3 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u0000"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(178);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(179);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(180);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject nti$3(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyString.fromInterned("Convert a number field to a python number.\n    ");
      var1.setline(187);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      PyObject var10000 = var3._ne(var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(128)));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(189);
            var10000 = var1.getglobal("int");
            Object var10002 = var1.getglobal("nts").__call__(var2, var1.getlocal(0));
            if (!((PyObject)var10002).__nonzero__()) {
               var10002 = PyString.fromInterned("0");
            }

            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)Py.newInteger(8));
            var1.setlocal(1, var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("ValueError"))) {
               var1.setline(191);
               throw Py.makeException(var1.getglobal("InvalidHeaderError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid header")));
            }

            throw var7;
         }
      } else {
         var1.setline(193);
         PyLong var8 = Py.newLong("0");
         var1.setlocal(1, var8);
         var3 = null;
         var1.setline(194);
         var3 = var1.getglobal("xrange").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0))._sub(Py.newInteger(1))).__iter__();

         while(true) {
            var1.setline(194);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(195);
            PyObject var5 = var1.getlocal(1);
            var5 = var5._ilshift(Py.newInteger(8));
            var1.setlocal(1, var5);
            var1.setline(196);
            var5 = var1.getlocal(1);
            var5 = var5._iadd(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getitem__(var1.getlocal(2)._add(Py.newInteger(1)))));
            var1.setlocal(1, var5);
         }
      }

      var1.setline(197);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject itn$4(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyString.fromInterned("Convert a python number to a number field.\n    ");
      var1.setline(208);
      PyInteger var3 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(0);
      PyInteger var10000 = var3;
      PyObject var6 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var6._lt(Py.newInteger(8)._pow(var1.getlocal(1)._sub(Py.newInteger(1))));
      }

      var3 = null;
      if (var4.__nonzero__()) {
         var1.setline(209);
         var6 = PyString.fromInterned("%0*o")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)._sub(Py.newInteger(1)), var1.getlocal(0)}))._add(var1.getglobal("NUL"));
         var1.setlocal(3, var6);
         var3 = null;
      } else {
         var1.setline(211);
         var6 = var1.getlocal(2);
         PyObject var8 = var6._ne(var1.getglobal("GNU_FORMAT"));
         var3 = null;
         if (!var8.__nonzero__()) {
            var6 = var1.getlocal(0);
            var8 = var6._ge(Py.newInteger(256)._pow(var1.getlocal(1)._sub(Py.newInteger(1))));
            var3 = null;
         }

         if (var8.__nonzero__()) {
            var1.setline(212);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("overflow in number field")));
         }

         var1.setline(214);
         var6 = var1.getlocal(0);
         var8 = var6._lt(Py.newInteger(0));
         var3 = null;
         if (var8.__nonzero__()) {
            var1.setline(217);
            var6 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("L"), (PyObject)var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("l"), (PyObject)var1.getlocal(0))).__getitem__(Py.newInteger(0));
            var1.setlocal(0, var6);
            var3 = null;
         }

         var1.setline(219);
         PyString var7 = PyString.fromInterned("");
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(220);
         var6 = var1.getglobal("xrange").__call__(var2, var1.getlocal(1)._sub(Py.newInteger(1))).__iter__();

         while(true) {
            var1.setline(220);
            var4 = var6.__iternext__();
            if (var4 == null) {
               var1.setline(223);
               var6 = var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(128))._add(var1.getlocal(3));
               var1.setlocal(3, var6);
               var3 = null;
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(221);
            PyObject var5 = var1.getglobal("chr").__call__(var2, var1.getlocal(0)._and(Py.newInteger(255)))._add(var1.getlocal(3));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(222);
            var5 = var1.getlocal(0);
            var5 = var5._irshift(Py.newInteger(8));
            var1.setlocal(0, var5);
         }
      }

      var1.setline(224);
      var6 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject uts$5(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyString.fromInterned("Convert a unicode object to a string.\n    ");
      var1.setline(229);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("utf-8"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(234);
            var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("strict"));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var9) {
            PyException var4 = Py.setException(var9, var1);
            if (var4.match(var1.getglobal("UnicodeEncodeError"))) {
               var1.setline(236);
               PyList var5 = new PyList(Py.EmptyObjects);
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(237);
               PyObject var10 = var1.getlocal(0).__iter__();

               while(true) {
                  var1.setline(237);
                  PyObject var6 = var10.__iternext__();
                  if (var6 == null) {
                     var1.setline(242);
                     var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setlocal(4, var6);

                  try {
                     var1.setline(239);
                     var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("strict")));
                  } catch (Throwable var8) {
                     PyException var7 = Py.setException(var8, var1);
                     if (!var7.match(var1.getglobal("UnicodeEncodeError"))) {
                        throw var7;
                     }

                     var1.setline(241);
                     var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf8")));
                  }
               }
            } else {
               throw var4;
            }
         }
      } else {
         var1.setline(244);
         var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject calc_chksums$6(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      PyString.fromInterned("Calculate the checksum for a member's header by summing up all\n       characters except for the chksum field which is treated as if\n       it was filled with spaces. According to the GNU tar sources,\n       some tars (Sun and NeXT) calculate chksum with signed char,\n       which will be different if there are chars in the buffer with\n       the high bit set. So we calculate two checksums, unsigned and\n       signed.\n    ");
      var1.setline(255);
      PyObject var3 = Py.newInteger(256)._add(var1.getglobal("sum").__call__(var2, var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("148B"), (PyObject)var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(148), (PyObject)null))._add(var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("356B"), (PyObject)var1.getlocal(0).__getslice__(Py.newInteger(156), Py.newInteger(512), (PyObject)null)))));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(256);
      var3 = Py.newInteger(256)._add(var1.getglobal("sum").__call__(var2, var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("148b"), (PyObject)var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(148), (PyObject)null))._add(var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("356b"), (PyObject)var1.getlocal(0).__getslice__(Py.newInteger(156), Py.newInteger(512), (PyObject)null)))));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(257);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject copyfileobj$7(PyFrame var1, ThreadState var2) {
      var1.setline(262);
      PyString.fromInterned("Copy length bytes from fileobj src to fileobj dst.\n       If length is None, copy the entire content.\n    ");
      var1.setline(263);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(264);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(265);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(266);
            var1.getglobal("shutil").__getattr__("copyfileobj").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setline(267);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(269);
            var3 = Py.newInteger(16)._mul(Py.newInteger(1024));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(270);
            var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
            var1.setline(271);
            var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(4)).__iter__();

            while(true) {
               var1.setline(271);
               PyObject var6 = var3.__iternext__();
               if (var6 == null) {
                  var1.setline(277);
                  var3 = var1.getlocal(5);
                  var10000 = var3._ne(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(278);
                     var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(5));
                     var1.setlocal(7, var3);
                     var3 = null;
                     var1.setline(279);
                     var3 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
                     var10000 = var3._lt(var1.getlocal(5));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(280);
                        throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("end of file reached")));
                     }

                     var1.setline(281);
                     var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(7));
                  }

                  var1.setline(282);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(6, var6);
               var1.setline(272);
               var5 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(3));
               var1.setlocal(7, var5);
               var5 = null;
               var1.setline(273);
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
               var10000 = var5._lt(var1.getlocal(3));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(274);
                  throw Py.makeException(var1.getglobal("IOError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("end of file reached")));
               }

               var1.setline(275);
               var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(7));
            }
         }
      }
   }

   public PyObject filemode$8(PyFrame var1, ThreadState var2) {
      var1.setline(315);
      PyString.fromInterned("Convert a file's mode to a string of the form\n       -rwxrwxrwx.\n       Used by TarFile.list()\n    ");
      var1.setline(316);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(317);
      PyObject var9 = var1.getglobal("filemode_table").__iter__();

      while(true) {
         label21:
         while(true) {
            var1.setline(317);
            PyObject var4 = var9.__iternext__();
            if (var4 == null) {
               var1.setline(324);
               var9 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var9;
            }

            var1.setlocal(2, var4);
            var1.setline(318);
            PyObject var5 = var1.getlocal(2).__iter__();

            PyObject var10000;
            do {
               var1.setline(318);
               PyObject var6 = var5.__iternext__();
               if (var6 == null) {
                  var1.setline(323);
                  var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"));
                  continue label21;
               }

               PyObject[] var7 = Py.unpackSequence(var6, 2);
               PyObject var8 = var7[0];
               var1.setlocal(3, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(4, var8);
               var8 = null;
               var1.setline(319);
               PyObject var10 = var1.getlocal(0)._and(var1.getlocal(3));
               var10000 = var10._eq(var1.getlocal(3));
               var7 = null;
            } while(!var10000.__nonzero__());

            var1.setline(320);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject TarError$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base exception."));
      var1.setline(327);
      PyString.fromInterned("Base exception.");
      var1.setline(328);
      return var1.getf_locals();
   }

   public PyObject ExtractError$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("General exception for extract errors."));
      var1.setline(330);
      PyString.fromInterned("General exception for extract errors.");
      var1.setline(331);
      return var1.getf_locals();
   }

   public PyObject ReadError$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception for unreadble tar archives."));
      var1.setline(333);
      PyString.fromInterned("Exception for unreadble tar archives.");
      var1.setline(334);
      return var1.getf_locals();
   }

   public PyObject CompressionError$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception for unavailable compression methods."));
      var1.setline(336);
      PyString.fromInterned("Exception for unavailable compression methods.");
      var1.setline(337);
      return var1.getf_locals();
   }

   public PyObject StreamError$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception for unsupported operations on stream-like TarFiles."));
      var1.setline(339);
      PyString.fromInterned("Exception for unsupported operations on stream-like TarFiles.");
      var1.setline(340);
      return var1.getf_locals();
   }

   public PyObject HeaderError$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base exception for header errors."));
      var1.setline(342);
      PyString.fromInterned("Base exception for header errors.");
      var1.setline(343);
      return var1.getf_locals();
   }

   public PyObject EmptyHeaderError$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception for empty headers."));
      var1.setline(345);
      PyString.fromInterned("Exception for empty headers.");
      var1.setline(346);
      return var1.getf_locals();
   }

   public PyObject TruncatedHeaderError$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception for truncated headers."));
      var1.setline(348);
      PyString.fromInterned("Exception for truncated headers.");
      var1.setline(349);
      return var1.getf_locals();
   }

   public PyObject EOFHeaderError$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception for end of file headers."));
      var1.setline(351);
      PyString.fromInterned("Exception for end of file headers.");
      var1.setline(352);
      return var1.getf_locals();
   }

   public PyObject InvalidHeaderError$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception for invalid headers."));
      var1.setline(354);
      PyString.fromInterned("Exception for invalid headers.");
      var1.setline(355);
      return var1.getf_locals();
   }

   public PyObject SubsequentHeaderError$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception for missing and invalid extended headers."));
      var1.setline(357);
      PyString.fromInterned("Exception for missing and invalid extended headers.");
      var1.setline(358);
      return var1.getf_locals();
   }

   public PyObject _LowLevelFile$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Low-level file object. Supports reading and writing.\n       It is used instead of a regular file object for streaming\n       access.\n    "));
      var1.setline(367);
      PyString.fromInterned("Low-level file object. Supports reading and writing.\n       It is used instead of a regular file object for streaming\n       access.\n    ");
      var1.setline(369);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(378);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$22, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(381);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$23, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(384);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$24, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyObject var3 = (new PyDictionary(new PyObject[]{PyString.fromInterned("r"), var1.getglobal("os").__getattr__("O_RDONLY"), PyString.fromInterned("w"), var1.getglobal("os").__getattr__("O_WRONLY")._or(var1.getglobal("os").__getattr__("O_CREAT"))._or(var1.getglobal("os").__getattr__("O_TRUNC"))})).__getitem__(var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(374);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("O_BINARY")).__nonzero__()) {
         var1.setline(375);
         var3 = var1.getlocal(2);
         var3 = var3._ior(var1.getglobal("os").__getattr__("O_BINARY"));
         var1.setlocal(2, var3);
      }

      var1.setline(376);
      var3 = var1.getglobal("os").__getattr__("open").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(438));
      var1.getlocal(0).__setattr__("fd", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$22(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(0).__getattr__("fd"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$23(PyFrame var1, ThreadState var2) {
      var1.setline(382);
      PyObject var3 = var1.getglobal("os").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("fd"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$24(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      var1.getglobal("os").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("fd"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Stream$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class that serves as an adapter between TarFile and\n       a stream-like object.  The stream-like object only\n       needs to have a read() or write() method and is accessed\n       blockwise.  Use of gzip or bzip2 compression is possible.\n       A stream-like object could be for example: sys.stdin,\n       sys.stdout, a socket, a tape device etc.\n\n       _Stream is intended to be used only internally.\n    "));
      var1.setline(396);
      PyString.fromInterned("Class that serves as an adapter between TarFile and\n       a stream-like object.  The stream-like object only\n       needs to have a read() or write() method and is accessed\n       blockwise.  Use of gzip or bzip2 compression is possible.\n       A stream-like object could be for example: sys.stdin,\n       sys.stdout, a socket, a tape device etc.\n\n       _Stream is intended to be used only internally.\n    ");
      var1.setline(398);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$26, PyString.fromInterned("Construct a _Stream object.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __del__$27, (PyObject)null);
      var1.setlocal("__del__", var4);
      var3 = null;
      var1.setline(448);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _init_write_gz$28, PyString.fromInterned("Initialize for writing with gzip compression.\n        "));
      var1.setlocal("_init_write_gz", var4);
      var3 = null;
      var1.setline(463);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$29, PyString.fromInterned("Write string s to the stream.\n        "));
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(473);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _Stream__write$30, PyString.fromInterned("Write string s to the stream if a whole new block\n           is ready to be written.\n        "));
      var1.setlocal("_Stream__write", var4);
      var3 = null;
      var1.setline(482);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$31, PyString.fromInterned("Close the _Stream object. No operation should be\n           done on it afterwards.\n        "));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(510);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _init_read_gz$32, PyString.fromInterned("Initialize for reading a gzip compressed fileobj.\n        "));
      var1.setlocal("_init_read_gz", var4);
      var3 = null;
      var1.setline(541);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$33, PyString.fromInterned("Return the stream's file pointer position.\n        "));
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(546);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$34, PyString.fromInterned("Set the stream's file pointer to pos. Negative seeking\n           is forbidden.\n        "));
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(559);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$35, PyString.fromInterned("Return the next size number of bytes from the stream.\n           If size is not defined, return all bytes of the stream\n           up to EOF.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(577);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read$36, PyString.fromInterned("Return size bytes from the stream.\n        "));
      var1.setlocal("_read", var4);
      var3 = null;
      var1.setline(599);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _Stream__read$37, PyString.fromInterned("Return size bytes from stream. If internal buffer is empty,\n           read another block from the stream.\n        "));
      var1.setlocal("_Stream__read", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$26(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      PyString.fromInterned("Construct a _Stream object.\n        ");
      var1.setline(401);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_extfileobj", var3);
      var3 = null;
      var1.setline(402);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(403);
         var3 = var1.getglobal("_LowLevelFile").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(404);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_extfileobj", var3);
         var3 = null;
      }

      var1.setline(406);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(PyString.fromInterned("*"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(409);
         var3 = var1.getglobal("_StreamProxy").__call__(var2, var1.getlocal(4));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(410);
         var3 = var1.getlocal(4).__getattr__("getcomptype").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(412);
      Object var10 = var1.getlocal(1);
      if (!((PyObject)var10).__nonzero__()) {
         var10 = PyString.fromInterned("");
      }

      Object var6 = var10;
      var1.getlocal(0).__setattr__((String)"name", (PyObject)var6);
      var3 = null;
      var1.setline(413);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("mode", var3);
      var3 = null;
      var1.setline(414);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("comptype", var3);
      var3 = null;
      var1.setline(415);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("fileobj", var3);
      var3 = null;
      var1.setline(416);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("bufsize", var3);
      var3 = null;
      var1.setline(417);
      PyString var7 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buf", var7);
      var3 = null;
      var1.setline(418);
      PyLong var8 = Py.newLong("0");
      var1.getlocal(0).__setattr__((String)"pos", var8);
      var3 = null;
      var1.setline(419);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("closed", var3);
      var3 = null;
      var1.setline(421);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(PyString.fromInterned("gz"));
      var3 = null;
      PyException var9;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(423);
            var3 = imp.importOne("zlib", var1, -1);
            var1.setlocal(6, var3);
            var3 = null;
         } catch (Throwable var5) {
            var9 = Py.setException(var5, var1);
            if (var9.match(var1.getglobal("ImportError"))) {
               var1.setline(425);
               throw Py.makeException(var1.getglobal("CompressionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("zlib module is not available")));
            }

            throw var9;
         }

         var1.setline(426);
         var3 = var1.getlocal(6);
         var1.getlocal(0).__setattr__("zlib", var3);
         var3 = null;
         var1.setline(427);
         var3 = var1.getlocal(6).__getattr__("crc32").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""))._and(Py.newLong("4294967295"));
         var1.getlocal(0).__setattr__("crc", var3);
         var3 = null;
         var1.setline(428);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("r"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(429);
            var1.getlocal(0).__getattr__("_init_read_gz").__call__(var2);
         } else {
            var1.setline(431);
            var1.getlocal(0).__getattr__("_init_write_gz").__call__(var2);
         }
      }

      var1.setline(433);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(PyString.fromInterned("bz2"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(435);
            var3 = imp.importOne("bz2", var1, -1);
            var1.setlocal(7, var3);
            var3 = null;
         } catch (Throwable var4) {
            var9 = Py.setException(var4, var1);
            if (var9.match(var1.getglobal("ImportError"))) {
               var1.setline(437);
               throw Py.makeException(var1.getglobal("CompressionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bz2 module is not available")));
            }

            throw var9;
         }

         var1.setline(438);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("r"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(439);
            var7 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"dbuf", var7);
            var3 = null;
            var1.setline(440);
            var3 = var1.getlocal(7).__getattr__("BZ2Decompressor").__call__(var2);
            var1.getlocal(0).__setattr__("cmp", var3);
            var3 = null;
         } else {
            var1.setline(442);
            var3 = var1.getlocal(7).__getattr__("BZ2Compressor").__call__(var2);
            var1.getlocal(0).__setattr__("cmp", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$27(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("closed"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("closed").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(446);
         var1.getlocal(0).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_write_gz$28(PyFrame var1, ThreadState var2) {
      var1.setline(450);
      PyString.fromInterned("Initialize for writing with gzip compression.\n        ");
      var1.setline(451);
      PyObject var10000 = var1.getlocal(0).__getattr__("zlib").__getattr__("compressobj");
      PyObject[] var3 = new PyObject[]{Py.newInteger(9), var1.getlocal(0).__getattr__("zlib").__getattr__("DEFLATED"), var1.getlocal(0).__getattr__("zlib").__getattr__("MAX_WBITS").__neg__(), var1.getlocal(0).__getattr__("zlib").__getattr__("DEF_MEM_LEVEL"), Py.newInteger(0)};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.getlocal(0).__setattr__("cmp", var4);
      var3 = null;
      var1.setline(455);
      var4 = var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<L"), (PyObject)var1.getglobal("long").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2)));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(456);
      var1.getlocal(0).__getattr__("_Stream__write").__call__(var2, PyString.fromInterned("\u001f\u008b\b\b%s\u0002ÿ")._mod(var1.getlocal(1)));
      var1.setline(457);
      var4 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("name"));
      var10000 = var4._is(var1.getglobal("unicode"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(458);
         var4 = var1.getlocal(0).__getattr__("name").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("iso-8859-1"), (PyObject)PyString.fromInterned("replace"));
         var1.getlocal(0).__setattr__("name", var4);
         var3 = null;
      }

      var1.setline(459);
      if (var1.getlocal(0).__getattr__("name").__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".gz")).__nonzero__()) {
         var1.setline(460);
         var4 = var1.getlocal(0).__getattr__("name").__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null);
         var1.getlocal(0).__setattr__("name", var4);
         var3 = null;
      }

      var1.setline(461);
      var1.getlocal(0).__getattr__("_Stream__write").__call__(var2, var1.getlocal(0).__getattr__("name")._add(var1.getglobal("NUL")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$29(PyFrame var1, ThreadState var2) {
      var1.setline(465);
      PyString.fromInterned("Write string s to the stream.\n        ");
      var1.setline(466);
      PyObject var3 = var1.getlocal(0).__getattr__("comptype");
      PyObject var10000 = var3._eq(PyString.fromInterned("gz"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(467);
         var3 = var1.getlocal(0).__getattr__("zlib").__getattr__("crc32").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("crc"))._and(Py.newLong("4294967295"));
         var1.getlocal(0).__setattr__("crc", var3);
         var3 = null;
      }

      var1.setline(468);
      var10000 = var1.getlocal(0);
      String var6 = "pos";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var4.__setattr__(var6, var5);
      var1.setline(469);
      var3 = var1.getlocal(0).__getattr__("comptype");
      var10000 = var3._ne(PyString.fromInterned("tar"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(470);
         var3 = var1.getlocal(0).__getattr__("cmp").__getattr__("compress").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(471);
      var1.getlocal(0).__getattr__("_Stream__write").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Stream__write$30(PyFrame var1, ThreadState var2) {
      var1.setline(476);
      PyString.fromInterned("Write string s to the stream if a whole new block\n           is ready to be written.\n        ");
      var1.setline(477);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "buf";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(var1.getlocal(1));
      var4.__setattr__(var3, var5);

      while(true) {
         var1.setline(478);
         PyObject var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("buf"));
         var10000 = var6._gt(var1.getlocal(0).__getattr__("bufsize"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(479);
         var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("buf").__getslice__((PyObject)null, var1.getlocal(0).__getattr__("bufsize"), (PyObject)null));
         var1.setline(480);
         var6 = var1.getlocal(0).__getattr__("buf").__getslice__(var1.getlocal(0).__getattr__("bufsize"), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("buf", var6);
         var3 = null;
      }
   }

   public PyObject close$31(PyFrame var1, ThreadState var2) {
      var1.setline(485);
      PyString.fromInterned("Close the _Stream object. No operation should be\n           done on it afterwards.\n        ");
      var1.setline(486);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(487);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(489);
         PyObject var3 = var1.getlocal(0).__getattr__("mode");
         PyObject var10000 = var3._eq(PyString.fromInterned("w"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("comptype");
            var10000 = var3._ne(PyString.fromInterned("tar"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(490);
            var10000 = var1.getlocal(0);
            String var6 = "buf";
            PyObject var4 = var10000;
            PyObject var5 = var4.__getattr__(var6);
            var5 = var5._iadd(var1.getlocal(0).__getattr__("cmp").__getattr__("flush").__call__(var2));
            var4.__setattr__(var6, var5);
         }

         var1.setline(492);
         var3 = var1.getlocal(0).__getattr__("mode");
         var10000 = var3._eq(PyString.fromInterned("w"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("buf");
         }

         if (var10000.__nonzero__()) {
            var1.setline(493);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("buf"));
            var1.setline(494);
            PyString var7 = PyString.fromInterned("");
            var1.getlocal(0).__setattr__((String)"buf", var7);
            var3 = null;
            var1.setline(495);
            var3 = var1.getlocal(0).__getattr__("comptype");
            var10000 = var3._eq(PyString.fromInterned("gz"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(502);
               var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<L"), (PyObject)var1.getlocal(0).__getattr__("crc")._and(Py.newLong("4294967295"))));
               var1.setline(503);
               var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<L"), (PyObject)var1.getlocal(0).__getattr__("pos")._and(Py.newLong("4294967295"))));
            }
         }

         var1.setline(505);
         if (var1.getlocal(0).__getattr__("_extfileobj").__not__().__nonzero__()) {
            var1.setline(506);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("close").__call__(var2);
         }

         var1.setline(508);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("closed", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _init_read_gz$32(PyFrame var1, ThreadState var2) {
      var1.setline(512);
      PyString.fromInterned("Initialize for reading a gzip compressed fileobj.\n        ");
      var1.setline(513);
      PyObject var3 = var1.getlocal(0).__getattr__("zlib").__getattr__("decompressobj").__call__(var2, var1.getlocal(0).__getattr__("zlib").__getattr__("MAX_WBITS").__neg__());
      var1.getlocal(0).__setattr__("cmp", var3);
      var3 = null;
      var1.setline(514);
      PyString var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"dbuf", var4);
      var3 = null;
      var1.setline(517);
      var3 = var1.getlocal(0).__getattr__("_Stream__read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      PyObject var10000 = var3._ne(PyString.fromInterned("\u001f\u008b"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(518);
         throw Py.makeException(var1.getglobal("ReadError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not a gzip file")));
      } else {
         var1.setline(519);
         var3 = var1.getlocal(0).__getattr__("_Stream__read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var10000 = var3._ne(PyString.fromInterned("\b"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(520);
            throw Py.makeException(var1.getglobal("CompressionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unsupported compression method")));
         } else {
            var1.setline(522);
            var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("_Stream__read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(523);
            var1.getlocal(0).__getattr__("_Stream__read").__call__((ThreadState)var2, (PyObject)Py.newInteger(6));
            var1.setline(525);
            if (var1.getlocal(1)._and(Py.newInteger(4)).__nonzero__()) {
               var1.setline(526);
               var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("_Stream__read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)))._add(Py.newInteger(256)._mul(var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("_Stream__read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)))));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(527);
               var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(2));
            }

            var1.setline(528);
            if (var1.getlocal(1)._and(Py.newInteger(8)).__nonzero__()) {
               do {
                  var1.setline(529);
                  if (!var1.getglobal("True").__nonzero__()) {
                     break;
                  }

                  var1.setline(530);
                  var3 = var1.getlocal(0).__getattr__("_Stream__read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(531);
                  var10000 = var1.getlocal(3).__not__();
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(3);
                     var10000 = var3._eq(var1.getglobal("NUL"));
                     var3 = null;
                  }
               } while(!var10000.__nonzero__());
            }

            var1.setline(533);
            if (var1.getlocal(1)._and(Py.newInteger(16)).__nonzero__()) {
               do {
                  var1.setline(534);
                  if (!var1.getglobal("True").__nonzero__()) {
                     break;
                  }

                  var1.setline(535);
                  var3 = var1.getlocal(0).__getattr__("_Stream__read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(536);
                  var10000 = var1.getlocal(3).__not__();
                  if (!var10000.__nonzero__()) {
                     var3 = var1.getlocal(3);
                     var10000 = var3._eq(var1.getglobal("NUL"));
                     var3 = null;
                  }
               } while(!var10000.__nonzero__());
            }

            var1.setline(538);
            if (var1.getlocal(1)._and(Py.newInteger(2)).__nonzero__()) {
               var1.setline(539);
               var1.getlocal(0).__getattr__("_Stream__read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject tell$33(PyFrame var1, ThreadState var2) {
      var1.setline(543);
      PyString.fromInterned("Return the stream's file pointer position.\n        ");
      var1.setline(544);
      PyObject var3 = var1.getlocal(0).__getattr__("pos");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$34(PyFrame var1, ThreadState var2) {
      var1.setline(549);
      PyString.fromInterned("Set the stream's file pointer to pos. Negative seeking\n           is forbidden.\n        ");
      var1.setline(550);
      PyObject var3 = var1.getlocal(1)._sub(var1.getlocal(0).__getattr__("pos"));
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var1.setline(556);
         throw Py.makeException(var1.getglobal("StreamError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("seeking backwards is not allowed")));
      } else {
         var1.setline(551);
         var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(1)._sub(var1.getlocal(0).__getattr__("pos")), var1.getlocal(0).__getattr__("bufsize"));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(552);
         var3 = var1.getglobal("xrange").__call__(var2, var1.getlocal(2)).__iter__();

         while(true) {
            var1.setline(552);
            PyObject var6 = var3.__iternext__();
            if (var6 == null) {
               var1.setline(554);
               var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(3));
               var1.setline(557);
               var3 = var1.getlocal(0).__getattr__("pos");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(4, var6);
            var1.setline(553);
            var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("bufsize"));
         }
      }
   }

   public PyObject read$35(PyFrame var1, ThreadState var2) {
      var1.setline(563);
      PyString.fromInterned("Return the next size number of bytes from the stream.\n           If size is not defined, return all bytes of the stream\n           up to EOF.\n        ");
      var1.setline(564);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(565);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var6);
         var3 = null;

         while(true) {
            var1.setline(566);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(567);
            var3 = var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(0).__getattr__("bufsize"));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(568);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               break;
            }

            var1.setline(570);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
         }

         var1.setline(571);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(573);
         var3 = var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(574);
      var10000 = var1.getlocal(0);
      String var7 = "pos";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var7);
      var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var4.__setattr__(var7, var5);
      var1.setline(575);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _read$36(PyFrame var1, ThreadState var2) {
      var1.setline(579);
      PyString.fromInterned("Return size bytes from the stream.\n        ");
      var1.setline(580);
      PyObject var3 = var1.getlocal(0).__getattr__("comptype");
      PyObject var10000 = var3._eq(PyString.fromInterned("tar"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(581);
         var3 = var1.getlocal(0).__getattr__("_Stream__read").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(583);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("dbuf"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(584);
         PyList var6 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("dbuf")});
         var1.setlocal(3, var6);
         var4 = null;

         while(true) {
            var1.setline(585);
            var4 = var1.getlocal(2);
            var10000 = var4._lt(var1.getlocal(1));
            var4 = null;
            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(586);
            var4 = var1.getlocal(0).__getattr__("_Stream__read").__call__(var2, var1.getlocal(0).__getattr__("bufsize"));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(587);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               break;
            }

            try {
               var1.setline(590);
               var4 = var1.getlocal(0).__getattr__("cmp").__getattr__("decompress").__call__(var2, var1.getlocal(4));
               var1.setlocal(4, var4);
               var4 = null;
            } catch (Throwable var5) {
               PyException var7 = Py.setException(var5, var1);
               if (var7.match(var1.getglobal("IOError"))) {
                  var1.setline(592);
                  throw Py.makeException(var1.getglobal("ReadError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid compressed data")));
               }

               throw var7;
            }

            var1.setline(593);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
            var1.setline(594);
            var4 = var1.getlocal(2);
            var4 = var4._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
            var1.setlocal(2, var4);
         }

         var1.setline(595);
         var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(596);
         var4 = var1.getlocal(3).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("dbuf", var4);
         var4 = null;
         var1.setline(597);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _Stream__read$37(PyFrame var1, ThreadState var2) {
      var1.setline(602);
      PyString.fromInterned("Return size bytes from stream. If internal buffer is empty,\n           read another block from the stream.\n        ");
      var1.setline(603);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("buf"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(604);
      PyList var4 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("buf")});
      var1.setlocal(3, var4);
      var3 = null;

      while(true) {
         var1.setline(605);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._lt(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(606);
         var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("bufsize"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(607);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(609);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
         var1.setline(610);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
         var1.setlocal(2, var3);
      }

      var1.setline(611);
      var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(612);
      var3 = var1.getlocal(3).__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("buf", var3);
      var3 = null;
      var1.setline(613);
      var3 = var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _StreamProxy$38(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Small proxy class that enables transparent compression\n       detection for the Stream interface (mode 'r|*').\n    "));
      var1.setline(619);
      PyString.fromInterned("Small proxy class that enables transparent compression\n       detection for the Stream interface (mode 'r|*').\n    ");
      var1.setline(621);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$39, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(625);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$40, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(629);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcomptype$41, (PyObject)null);
      var1.setlocal("getcomptype", var4);
      var3 = null;
      var1.setline(636);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$42, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$39(PyFrame var1, ThreadState var2) {
      var1.setline(622);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fileobj", var3);
      var3 = null;
      var1.setline(623);
      var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getglobal("BLOCKSIZE"));
      var1.getlocal(0).__setattr__("buf", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$40(PyFrame var1, ThreadState var2) {
      var1.setline(626);
      PyObject var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read");
      var1.getlocal(0).__setattr__("read", var3);
      var3 = null;
      var1.setline(627);
      var3 = var1.getlocal(0).__getattr__("buf");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getcomptype$41(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyString var3;
      if (var1.getlocal(0).__getattr__("buf").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\u001f\u008b\b")).__nonzero__()) {
         var1.setline(631);
         var3 = PyString.fromInterned("gz");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(632);
         PyObject var4 = var1.getlocal(0).__getattr__("buf").__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null);
         PyObject var10000 = var4._eq(PyString.fromInterned("BZh"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("buf").__getslice__(Py.newInteger(4), Py.newInteger(10), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("1AY&SY"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(633);
            var3 = PyString.fromInterned("bz2");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(634);
            var3 = PyString.fromInterned("tar");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject close$42(PyFrame var1, ThreadState var2) {
      var1.setline(637);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _BZ2Proxy$43(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Small proxy class that enables external file object\n       support for \"r:bz2\" and \"w:bz2\" modes. This is actually\n       a workaround for a limitation in bz2 module's BZ2File\n       class which (unlike gzip.GzipFile) has no support for\n       a file object argument.\n    "));
      var1.setline(646);
      PyString.fromInterned("Small proxy class that enables external file object\n       support for \"r:bz2\" and \"w:bz2\" modes. This is actually\n       a workaround for a limitation in bz2 module's BZ2File\n       class which (unlike gzip.GzipFile) has no support for\n       a file object argument.\n    ");
      var1.setline(648);
      PyObject var3 = Py.newInteger(16)._mul(Py.newInteger(1024));
      var1.setlocal("blocksize", var3);
      var3 = null;
      var1.setline(650);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$44, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(656);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, init$45, (PyObject)null);
      var1.setlocal("init", var5);
      var3 = null;
      var1.setline(666);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, read$46, (PyObject)null);
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(683);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, seek$47, (PyObject)null);
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(688);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$48, (PyObject)null);
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(691);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, write$49, (PyObject)null);
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(696);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$50, (PyObject)null);
      var1.setlocal("close", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$44(PyFrame var1, ThreadState var2) {
      var1.setline(651);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fileobj", var3);
      var3 = null;
      var1.setline(652);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("mode", var3);
      var3 = null;
      var1.setline(653);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("fileobj"), (PyObject)PyString.fromInterned("name"), (PyObject)var1.getglobal("None"));
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(654);
      var1.getlocal(0).__getattr__("init").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject init$45(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      PyObject var3 = imp.importOne("bz2", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(658);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"pos", var4);
      var3 = null;
      var1.setline(659);
      var3 = var1.getlocal(0).__getattr__("mode");
      PyObject var10000 = var3._eq(PyString.fromInterned("r"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(660);
         var3 = var1.getlocal(1).__getattr__("BZ2Decompressor").__call__(var2);
         var1.getlocal(0).__setattr__("bz2obj", var3);
         var3 = null;
         var1.setline(661);
         var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(662);
         PyString var5 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"buf", var5);
         var3 = null;
      } else {
         var1.setline(664);
         var3 = var1.getlocal(1).__getattr__("BZ2Compressor").__call__(var2);
         var1.getlocal(0).__setattr__("bz2obj", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$46(PyFrame var1, ThreadState var2) {
      var1.setline(667);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("buf")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(668);
      PyObject var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("buf"));
      var1.setlocal(3, var6);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(669);
         var6 = var1.getlocal(3);
         var10000 = var6._lt(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(670);
         var6 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("blocksize"));
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(671);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(673);
         var6 = var1.getlocal(0).__getattr__("bz2obj").__getattr__("decompress").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(674);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5));
         var1.setline(675);
         var6 = var1.getlocal(3);
         var6 = var6._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
         var1.setlocal(3, var6);
      }

      var1.setline(676);
      var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("buf", var6);
      var3 = null;
      var1.setline(678);
      var6 = var1.getlocal(0).__getattr__("buf").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(679);
      var6 = var1.getlocal(0).__getattr__("buf").__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("buf", var6);
      var3 = null;
      var1.setline(680);
      var10000 = var1.getlocal(0);
      String var7 = "pos";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var7);
      var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
      var4.__setattr__(var7, var5);
      var1.setline(681);
      var6 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject seek$47(PyFrame var1, ThreadState var2) {
      var1.setline(684);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(var1.getlocal(0).__getattr__("pos"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(685);
         var1.getlocal(0).__getattr__("init").__call__(var2);
      }

      var1.setline(686);
      var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1)._sub(var1.getlocal(0).__getattr__("pos")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tell$48(PyFrame var1, ThreadState var2) {
      var1.setline(689);
      PyObject var3 = var1.getlocal(0).__getattr__("pos");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$49(PyFrame var1, ThreadState var2) {
      var1.setline(692);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "pos";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var4.__setattr__(var3, var5);
      var1.setline(693);
      PyObject var6 = var1.getlocal(0).__getattr__("bz2obj").__getattr__("compress").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(694);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$50(PyFrame var1, ThreadState var2) {
      var1.setline(697);
      PyObject var3 = var1.getlocal(0).__getattr__("mode");
      PyObject var10000 = var3._eq(PyString.fromInterned("w"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(698);
         var3 = var1.getlocal(0).__getattr__("bz2obj").__getattr__("flush").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(699);
         var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _FileInFile$51(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A thin wrapper around an existing file object that\n       provides a part of its data as an individual file\n       object.\n    "));
      var1.setline(709);
      PyString.fromInterned("A thin wrapper around an existing file object that\n       provides a part of its data as an individual file\n       object.\n    ");
      var1.setline(711);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$52, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(718);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$53, PyString.fromInterned("Return the current file position.\n        "));
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(723);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, seek$54, PyString.fromInterned("Seek to a position in the file.\n        "));
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(728);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$55, PyString.fromInterned("Read data from the file.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(741);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readnormal$56, PyString.fromInterned("Read operation for regular files.\n        "));
      var1.setlocal("readnormal", var4);
      var3 = null;
      var1.setline(748);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readsparse$57, PyString.fromInterned("Read operation for sparse files.\n        "));
      var1.setlocal("readsparse", var4);
      var3 = null;
      var1.setline(760);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readsparsesection$58, PyString.fromInterned("Read a single section of a sparse file.\n        "));
      var1.setlocal("readsparsesection", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$52(PyFrame var1, ThreadState var2) {
      var1.setline(712);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fileobj", var3);
      var3 = null;
      var1.setline(713);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("offset", var3);
      var3 = null;
      var1.setline(714);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("size", var3);
      var3 = null;
      var1.setline(715);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("sparse", var3);
      var3 = null;
      var1.setline(716);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"position", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tell$53(PyFrame var1, ThreadState var2) {
      var1.setline(720);
      PyString.fromInterned("Return the current file position.\n        ");
      var1.setline(721);
      PyObject var3 = var1.getlocal(0).__getattr__("position");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$54(PyFrame var1, ThreadState var2) {
      var1.setline(725);
      PyString.fromInterned("Seek to a position in the file.\n        ");
      var1.setline(726);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("position", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$55(PyFrame var1, ThreadState var2) {
      var1.setline(730);
      PyString.fromInterned("Read data from the file.\n        ");
      var1.setline(731);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(732);
         var3 = var1.getlocal(0).__getattr__("size")._sub(var1.getlocal(0).__getattr__("position"));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(734);
         var3 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("size")._sub(var1.getlocal(0).__getattr__("position")));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(736);
      var3 = var1.getlocal(0).__getattr__("sparse");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(737);
         var3 = var1.getlocal(0).__getattr__("readnormal").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(739);
         var3 = var1.getlocal(0).__getattr__("readsparse").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject readnormal$56(PyFrame var1, ThreadState var2) {
      var1.setline(743);
      PyString.fromInterned("Read operation for regular files.\n        ");
      var1.setline(744);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("offset")._add(var1.getlocal(0).__getattr__("position")));
      var1.setline(745);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "position";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(var1.getlocal(1));
      var4.__setattr__(var3, var5);
      var1.setline(746);
      PyObject var6 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject readsparse$57(PyFrame var1, ThreadState var2) {
      var1.setline(750);
      PyString.fromInterned("Read operation for sparse files.\n        ");
      var1.setline(751);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;

      PyObject var4;
      while(true) {
         var1.setline(752);
         var4 = var1.getlocal(1);
         PyObject var10000 = var4._gt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(753);
         var4 = var1.getlocal(0).__getattr__("readsparsesection").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(754);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            break;
         }

         var1.setline(756);
         var4 = var1.getlocal(1);
         var4 = var4._isub(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
         var1.setlocal(1, var4);
         var1.setline(757);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
      }

      var1.setline(758);
      var4 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject readsparsesection$58(PyFrame var1, ThreadState var2) {
      var1.setline(762);
      PyString.fromInterned("Read a single section of a sparse file.\n        ");
      var1.setline(763);
      PyObject var3 = var1.getlocal(0).__getattr__("sparse").__getattr__("find").__call__(var2, var1.getlocal(0).__getattr__("position"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(765);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(766);
         PyString var8 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(768);
         PyObject var4 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(2).__getattr__("offset")._add(var1.getlocal(2).__getattr__("size"))._sub(var1.getlocal(0).__getattr__("position")));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(770);
         PyObject var5;
         PyObject var6;
         String var7;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("_data")).__nonzero__()) {
            var1.setline(771);
            var4 = var1.getlocal(2).__getattr__("realpos")._add(var1.getlocal(0).__getattr__("position"))._sub(var1.getlocal(2).__getattr__("offset"));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(772);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("offset")._add(var1.getlocal(3)));
            var1.setline(773);
            var10000 = var1.getlocal(0);
            var7 = "position";
            var5 = var10000;
            var6 = var5.__getattr__(var7);
            var6 = var6._iadd(var1.getlocal(1));
            var5.__setattr__(var7, var6);
            var1.setline(774);
            var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(776);
            var10000 = var1.getlocal(0);
            var7 = "position";
            var5 = var10000;
            var6 = var5.__getattr__(var7);
            var6 = var6._iadd(var1.getlocal(1));
            var5.__setattr__(var7, var6);
            var1.setline(777);
            var3 = var1.getglobal("NUL")._mul(var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject ExFileObject$59(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("File-like object for reading an archive member.\n       Is returned by TarFile.extractfile().\n    "));
      var1.setline(784);
      PyString.fromInterned("File-like object for reading an archive member.\n       Is returned by TarFile.extractfile().\n    ");
      var1.setline(785);
      PyInteger var3 = Py.newInteger(1024);
      var1.setlocal("blocksize", var3);
      var3 = null;
      var1.setline(787);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$60, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(800);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, read$61, PyString.fromInterned("Read at most size bytes from the file. If size is not\n           present or None, read all data until EOF is reached.\n        "));
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(824);
      var4 = new PyObject[]{Py.newInteger(-1)};
      var5 = new PyFunction(var1.f_globals, var4, readline$62, PyString.fromInterned("Read one entire line from the file. If size is present\n           and non-negative, return a string with at most that\n           size, which may be an incomplete line.\n        "));
      var1.setlocal("readline", var5);
      var3 = null;
      var1.setline(855);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, readlines$63, PyString.fromInterned("Return a list with all remaining lines.\n        "));
      var1.setlocal("readlines", var5);
      var3 = null;
      var1.setline(865);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tell$64, PyString.fromInterned("Return the current file position.\n        "));
      var1.setlocal("tell", var5);
      var3 = null;
      var1.setline(873);
      var4 = new PyObject[]{var1.getname("os").__getattr__("SEEK_SET")};
      var5 = new PyFunction(var1.f_globals, var4, seek$65, PyString.fromInterned("Seek to a position in the file.\n        "));
      var1.setlocal("seek", var5);
      var3 = null;
      var1.setline(894);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$66, PyString.fromInterned("Close the file object.\n        "));
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(899);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __iter__$67, PyString.fromInterned("Get an iterator over the file's lines.\n        "));
      var1.setlocal("__iter__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$60(PyFrame var1, ThreadState var2) {
      var1.setline(788);
      PyObject var3 = var1.getglobal("_FileInFile").__call__(var2, var1.getlocal(1).__getattr__("fileobj"), var1.getlocal(2).__getattr__("offset_data"), var1.getlocal(2).__getattr__("size"), var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("sparse"), (PyObject)var1.getglobal("None")));
      var1.getlocal(0).__setattr__("fileobj", var3);
      var3 = null;
      var1.setline(792);
      var3 = var1.getlocal(2).__getattr__("name");
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(793);
      PyString var4 = PyString.fromInterned("r");
      var1.getlocal(0).__setattr__((String)"mode", var4);
      var3 = null;
      var1.setline(794);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("closed", var3);
      var3 = null;
      var1.setline(795);
      var3 = var1.getlocal(2).__getattr__("size");
      var1.getlocal(0).__setattr__("size", var3);
      var3 = null;
      var1.setline(797);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"position", var5);
      var3 = null;
      var1.setline(798);
      var4 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"buffer", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$61(PyFrame var1, ThreadState var2) {
      var1.setline(803);
      PyString.fromInterned("Read at most size bytes from the file. If size is not\n           present or None, read all data until EOF is reached.\n        ");
      var1.setline(804);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(805);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("I/O operation on closed file")));
      } else {
         var1.setline(807);
         PyString var3 = PyString.fromInterned("");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(808);
         PyObject var10000;
         PyObject var6;
         if (var1.getlocal(0).__getattr__("buffer").__nonzero__()) {
            var1.setline(809);
            var6 = var1.getlocal(1);
            var10000 = var6._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(810);
               var6 = var1.getlocal(0).__getattr__("buffer");
               var1.setlocal(2, var6);
               var3 = null;
               var1.setline(811);
               var3 = PyString.fromInterned("");
               var1.getlocal(0).__setattr__((String)"buffer", var3);
               var3 = null;
            } else {
               var1.setline(813);
               var6 = var1.getlocal(0).__getattr__("buffer").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null);
               var1.setlocal(2, var6);
               var3 = null;
               var1.setline(814);
               var6 = var1.getlocal(0).__getattr__("buffer").__getslice__(var1.getlocal(1), (PyObject)null, (PyObject)null);
               var1.getlocal(0).__setattr__("buffer", var6);
               var3 = null;
            }
         }

         var1.setline(816);
         var6 = var1.getlocal(1);
         var10000 = var6._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(817);
            var6 = var1.getlocal(2);
            var6 = var6._iadd(var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2));
            var1.setlocal(2, var6);
         } else {
            var1.setline(819);
            var6 = var1.getlocal(2);
            var6 = var6._iadd(var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2)))));
            var1.setlocal(2, var6);
         }

         var1.setline(821);
         var10000 = var1.getlocal(0);
         String var7 = "position";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var7);
         var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
         var4.__setattr__(var7, var5);
         var1.setline(822);
         var6 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject readline$62(PyFrame var1, ThreadState var2) {
      var1.setline(828);
      PyString.fromInterned("Read one entire line from the file. If size is present\n           and non-negative, return a string with at most that\n           size, which may be an incomplete line.\n        ");
      var1.setline(829);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(830);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("I/O operation on closed file")));
      } else {
         var1.setline(832);
         PyString var3 = PyString.fromInterned("\n");
         PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("buffer"));
         var3 = null;
         PyObject var6;
         if (var10000.__nonzero__()) {
            var1.setline(833);
            var6 = var1.getlocal(0).__getattr__("buffer").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"))._add(Py.newInteger(1));
            var1.setlocal(2, var6);
            var3 = null;
         } else {
            var1.setline(835);
            PyList var7 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("buffer")});
            var1.setlocal(3, var7);
            var3 = null;

            while(true) {
               var1.setline(836);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(837);
               var6 = var1.getlocal(0).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("blocksize"));
               var1.setlocal(4, var6);
               var3 = null;
               var1.setline(838);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
               var1.setline(839);
               var10000 = var1.getlocal(4).__not__();
               if (!var10000.__nonzero__()) {
                  var3 = PyString.fromInterned("\n");
                  var10000 = var3._in(var1.getlocal(4));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(840);
                  var6 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
                  var1.getlocal(0).__setattr__("buffer", var6);
                  var3 = null;
                  var1.setline(841);
                  var6 = var1.getlocal(0).__getattr__("buffer").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"))._add(Py.newInteger(1));
                  var1.setlocal(2, var6);
                  var3 = null;
                  var1.setline(842);
                  var6 = var1.getlocal(2);
                  var10000 = var6._eq(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(844);
                     var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("buffer"));
                     var1.setlocal(2, var6);
                     var3 = null;
                  }
                  break;
               }
            }
         }

         var1.setline(847);
         var6 = var1.getlocal(1);
         var10000 = var6._ne(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(848);
            var6 = var1.getglobal("min").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setlocal(2, var6);
            var3 = null;
         }

         var1.setline(850);
         var6 = var1.getlocal(0).__getattr__("buffer").__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null);
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(851);
         var6 = var1.getlocal(0).__getattr__("buffer").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null);
         var1.getlocal(0).__setattr__("buffer", var6);
         var3 = null;
         var1.setline(852);
         var10000 = var1.getlocal(0);
         String var8 = "position";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var8);
         var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
         var4.__setattr__(var8, var5);
         var1.setline(853);
         var6 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject readlines$63(PyFrame var1, ThreadState var2) {
      var1.setline(857);
      PyString.fromInterned("Return a list with all remaining lines.\n        ");
      var1.setline(858);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      while(true) {
         var1.setline(859);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(860);
         var4 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(861);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            break;
         }

         var1.setline(862);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
      }

      var1.setline(863);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject tell$64(PyFrame var1, ThreadState var2) {
      var1.setline(867);
      PyString.fromInterned("Return the current file position.\n        ");
      var1.setline(868);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(869);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("I/O operation on closed file")));
      } else {
         var1.setline(871);
         PyObject var3 = var1.getlocal(0).__getattr__("position");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject seek$65(PyFrame var1, ThreadState var2) {
      var1.setline(875);
      PyString.fromInterned("Seek to a position in the file.\n        ");
      var1.setline(876);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(877);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("I/O operation on closed file")));
      } else {
         var1.setline(879);
         PyObject var3 = var1.getlocal(2);
         PyObject var10000 = var3._eq(var1.getglobal("os").__getattr__("SEEK_SET"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(880);
            var3 = var1.getglobal("min").__call__(var2, var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0)), var1.getlocal(0).__getattr__("size"));
            var1.getlocal(0).__setattr__("position", var3);
            var3 = null;
         } else {
            var1.setline(881);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("os").__getattr__("SEEK_CUR"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(882);
               var3 = var1.getlocal(1);
               var10000 = var3._lt(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(883);
                  var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("position")._add(var1.getlocal(1)), (PyObject)Py.newInteger(0));
                  var1.getlocal(0).__setattr__("position", var3);
                  var3 = null;
               } else {
                  var1.setline(885);
                  var3 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("position")._add(var1.getlocal(1)), var1.getlocal(0).__getattr__("size"));
                  var1.getlocal(0).__setattr__("position", var3);
                  var3 = null;
               }
            } else {
               var1.setline(886);
               var3 = var1.getlocal(2);
               var10000 = var3._eq(var1.getglobal("os").__getattr__("SEEK_END"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(889);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Invalid argument")));
               }

               var1.setline(887);
               var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("size")._add(var1.getlocal(1)), var1.getlocal(0).__getattr__("size")), (PyObject)Py.newInteger(0));
               var1.getlocal(0).__setattr__("position", var3);
               var3 = null;
            }
         }

         var1.setline(891);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"buffer", var4);
         var3 = null;
         var1.setline(892);
         var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("position"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject close$66(PyFrame var1, ThreadState var2) {
      var1.setline(896);
      PyString.fromInterned("Close the file object.\n        ");
      var1.setline(897);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("closed", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$67(PyFrame var1, ThreadState var2) {
      Object[] var3;
      PyObject var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(901);
            PyString.fromInterned("Get an iterator over the file's lines.\n        ");
            break;
         case 1:
            var3 = var1.f_savedlocals;
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var5 = (PyObject)var10000;
      }

      var1.setline(902);
      if (var1.getglobal("True").__nonzero__()) {
         var1.setline(903);
         PyObject var4 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(1, var4);
         var3 = null;
         var1.setline(904);
         if (!var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(906);
            var1.setline(906);
            var5 = var1.getlocal(1);
            var1.f_lasti = 1;
            var3 = new Object[4];
            var1.f_savedlocals = var3;
            return var5;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TarInfo$68(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Informational class which holds the details about an\n       archive member given by a tar header block.\n       TarInfo objects are returned by TarFile.getmember(),\n       TarFile.getmembers() and TarFile.gettarinfo() and are\n       usually created internally.\n    "));
      var1.setline(918);
      PyString.fromInterned("Informational class which holds the details about an\n       archive member given by a tar header block.\n       TarInfo objects are returned by TarFile.getmember(),\n       TarFile.getmembers() and TarFile.gettarinfo() and are\n       usually created internally.\n    ");
      var1.setline(920);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$69, PyString.fromInterned("Construct a TarInfo object. name is the optional name\n           of the member.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(945);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getpath$70, (PyObject)null);
      var1.setlocal("_getpath", var4);
      var3 = null;
      var1.setline(947);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _setpath$71, (PyObject)null);
      var1.setlocal("_setpath", var4);
      var3 = null;
      var1.setline(949);
      PyObject var5 = var1.getname("property").__call__(var2, var1.getname("_getpath"), var1.getname("_setpath"));
      var1.setlocal("path", var5);
      var3 = null;
      var1.setline(951);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _getlinkpath$72, (PyObject)null);
      var1.setlocal("_getlinkpath", var4);
      var3 = null;
      var1.setline(953);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _setlinkpath$73, (PyObject)null);
      var1.setlocal("_setlinkpath", var4);
      var3 = null;
      var1.setline(955);
      var5 = var1.getname("property").__call__(var2, var1.getname("_getlinkpath"), var1.getname("_setlinkpath"));
      var1.setlocal("linkpath", var5);
      var3 = null;
      var1.setline(957);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$74, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(960);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_info$75, PyString.fromInterned("Return the TarInfo's attributes as a dictionary.\n        "));
      var1.setlocal("get_info", var4);
      var3 = null;
      var1.setline(988);
      var3 = new PyObject[]{var1.getname("DEFAULT_FORMAT"), var1.getname("ENCODING"), PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, tobuf$76, PyString.fromInterned("Return a tar header as a string of 512 byte blocks.\n        "));
      var1.setlocal("tobuf", var4);
      var3 = null;
      var1.setline(1002);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, create_ustar_header$77, PyString.fromInterned("Return the object as a ustar header block.\n        "));
      var1.setlocal("create_ustar_header", var4);
      var3 = null;
      var1.setline(1015);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, create_gnu_header$78, PyString.fromInterned("Return the object as a GNU header block sequence.\n        "));
      var1.setlocal("create_gnu_header", var4);
      var3 = null;
      var1.setline(1029);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, create_pax_header$79, PyString.fromInterned("Return the object as a ustar header block. If it cannot be\n           represented this way, prepend a pax extended header sequence\n           with supplement information.\n        "));
      var1.setlocal("create_pax_header", var4);
      var3 = null;
      var1.setline(1080);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, create_pax_global_header$80, PyString.fromInterned("Return the object as a pax global header block sequence.\n        "));
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("create_pax_global_header", var5);
      var3 = null;
      var1.setline(1086);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _posix_split_name$81, PyString.fromInterned("Split a name longer than 100 chars into a prefix\n           and a name part.\n        "));
      var1.setlocal("_posix_split_name", var4);
      var3 = null;
      var1.setline(1101);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _create_header$82, PyString.fromInterned("Return a header block. info is a dictionary with file\n           information, format must be one of the *_FORMAT constants.\n        "));
      var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_create_header", var5);
      var3 = null;
      var1.setline(1129);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _create_payload$83, PyString.fromInterned("Return the string payload filled with zero bytes\n           up to the next 512 byte border.\n        "));
      var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_create_payload", var5);
      var3 = null;
      var1.setline(1139);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _create_gnu_long_header$84, PyString.fromInterned("Return a GNUTYPE_LONGNAME or GNUTYPE_LONGLINK sequence\n           for name.\n        "));
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_create_gnu_long_header", var5);
      var3 = null;
      var1.setline(1156);
      var3 = new PyObject[]{var1.getname("XHDTYPE")};
      var4 = new PyFunction(var1.f_globals, var3, _create_pax_generic_header$85, PyString.fromInterned("Return a POSIX.1-2001 extended or global header sequence\n           that contains a list of keyword, value pairs. The values\n           must be unicode objects.\n        "));
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("_create_pax_generic_header", var5);
      var3 = null;
      var1.setline(1188);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, frombuf$86, PyString.fromInterned("Construct a TarInfo object from a 512 byte string buffer.\n        "));
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("frombuf", var5);
      var3 = null;
      var1.setline(1234);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fromtarfile$87, PyString.fromInterned("Return the next TarInfo object from TarFile object\n           tarfile.\n        "));
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("fromtarfile", var5);
      var3 = null;
      var1.setline(1255);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _proc_member$88, PyString.fromInterned("Choose the right processing method depending on\n           the type and call it.\n        "));
      var1.setlocal("_proc_member", var4);
      var3 = null;
      var1.setline(1268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _proc_builtin$89, PyString.fromInterned("Process a builtin type or an unknown type which\n           will be treated as a regular file.\n        "));
      var1.setlocal("_proc_builtin", var4);
      var3 = null;
      var1.setline(1285);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _proc_gnulong$90, PyString.fromInterned("Process the blocks that hold a GNU longname\n           or longlink member.\n        "));
      var1.setlocal("_proc_gnulong", var4);
      var3 = null;
      var1.setline(1307);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _proc_sparse$91, PyString.fromInterned("Process a GNU sparse header plus extra headers.\n        "));
      var1.setlocal("_proc_sparse", var4);
      var3 = null;
      var1.setline(1363);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _proc_pax$92, PyString.fromInterned("Process an extended or global header as described in\n           POSIX.1-2001.\n        "));
      var1.setlocal("_proc_pax", var4);
      var3 = null;
      var1.setline(1421);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _apply_pax_info$93, PyString.fromInterned("Replace fields with supplemental information from a previous\n           pax extended or global header.\n        "));
      var1.setlocal("_apply_pax_info", var4);
      var3 = null;
      var1.setline(1444);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _block$94, PyString.fromInterned("Round up a byte count by BLOCKSIZE and return it,\n           e.g. _block(834) => 1024.\n        "));
      var1.setlocal("_block", var4);
      var3 = null;
      var1.setline(1453);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isreg$95, (PyObject)null);
      var1.setlocal("isreg", var4);
      var3 = null;
      var1.setline(1455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isfile$96, (PyObject)null);
      var1.setlocal("isfile", var4);
      var3 = null;
      var1.setline(1457);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isdir$97, (PyObject)null);
      var1.setlocal("isdir", var4);
      var3 = null;
      var1.setline(1459);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, issym$98, (PyObject)null);
      var1.setlocal("issym", var4);
      var3 = null;
      var1.setline(1461);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, islnk$99, (PyObject)null);
      var1.setlocal("islnk", var4);
      var3 = null;
      var1.setline(1463);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ischr$100, (PyObject)null);
      var1.setlocal("ischr", var4);
      var3 = null;
      var1.setline(1465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isblk$101, (PyObject)null);
      var1.setlocal("isblk", var4);
      var3 = null;
      var1.setline(1467);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isfifo$102, (PyObject)null);
      var1.setlocal("isfifo", var4);
      var3 = null;
      var1.setline(1469);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, issparse$103, (PyObject)null);
      var1.setlocal("issparse", var4);
      var3 = null;
      var1.setline(1471);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, isdev$104, (PyObject)null);
      var1.setlocal("isdev", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$69(PyFrame var1, ThreadState var2) {
      var1.setline(923);
      PyString.fromInterned("Construct a TarInfo object. name is the optional name\n           of the member.\n        ");
      var1.setline(924);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(925);
      PyInteger var4 = Py.newInteger(420);
      var1.getlocal(0).__setattr__((String)"mode", var4);
      var3 = null;
      var1.setline(926);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"uid", var4);
      var3 = null;
      var1.setline(927);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"gid", var4);
      var3 = null;
      var1.setline(928);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"size", var4);
      var3 = null;
      var1.setline(929);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"mtime", var4);
      var3 = null;
      var1.setline(930);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"chksum", var4);
      var3 = null;
      var1.setline(931);
      var3 = var1.getglobal("REGTYPE");
      var1.getlocal(0).__setattr__("type", var3);
      var3 = null;
      var1.setline(932);
      PyString var5 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"linkname", var5);
      var3 = null;
      var1.setline(933);
      var5 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"uname", var5);
      var3 = null;
      var1.setline(934);
      var5 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"gname", var5);
      var3 = null;
      var1.setline(935);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"devmajor", var4);
      var3 = null;
      var1.setline(936);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"devminor", var4);
      var3 = null;
      var1.setline(938);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"offset", var4);
      var3 = null;
      var1.setline(939);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"offset_data", var4);
      var3 = null;
      var1.setline(941);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"pax_headers", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _getpath$70(PyFrame var1, ThreadState var2) {
      var1.setline(946);
      PyObject var3 = var1.getlocal(0).__getattr__("name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _setpath$71(PyFrame var1, ThreadState var2) {
      var1.setline(948);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _getlinkpath$72(PyFrame var1, ThreadState var2) {
      var1.setline(952);
      PyObject var3 = var1.getlocal(0).__getattr__("linkname");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _setlinkpath$73(PyFrame var1, ThreadState var2) {
      var1.setline(954);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("linkname", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$74(PyFrame var1, ThreadState var2) {
      var1.setline(958);
      PyObject var3 = PyString.fromInterned("<%s %r at %#x>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getlocal(0).__getattr__("name"), var1.getglobal("id").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_info$75(PyFrame var1, ThreadState var2) {
      var1.setline(962);
      PyString.fromInterned("Return the TarInfo's attributes as a dictionary.\n        ");
      var1.setline(963);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), var1.getlocal(0).__getattr__("name"), PyString.fromInterned("mode"), var1.getlocal(0).__getattr__("mode")._and(Py.newInteger(4095)), PyString.fromInterned("uid"), var1.getlocal(0).__getattr__("uid"), PyString.fromInterned("gid"), var1.getlocal(0).__getattr__("gid"), PyString.fromInterned("size"), var1.getlocal(0).__getattr__("size"), PyString.fromInterned("mtime"), var1.getlocal(0).__getattr__("mtime"), PyString.fromInterned("chksum"), var1.getlocal(0).__getattr__("chksum"), PyString.fromInterned("type"), var1.getlocal(0).__getattr__("type"), PyString.fromInterned("linkname"), var1.getlocal(0).__getattr__("linkname"), PyString.fromInterned("uname"), var1.getlocal(0).__getattr__("uname"), PyString.fromInterned("gname"), var1.getlocal(0).__getattr__("gname"), PyString.fromInterned("devmajor"), var1.getlocal(0).__getattr__("devmajor"), PyString.fromInterned("devminor"), var1.getlocal(0).__getattr__("devminor")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(979);
      PyObject var6 = var1.getlocal(3).__getitem__(PyString.fromInterned("type"));
      PyObject var10000 = var6._eq(var1.getglobal("DIRTYPE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3).__getitem__(PyString.fromInterned("name")).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__not__();
      }

      PyObject var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(980);
         var10000 = var1.getlocal(3);
         PyString var7 = PyString.fromInterned("name");
         var4 = var10000;
         var5 = var4.__getitem__(var7);
         var5 = var5._iadd(PyString.fromInterned("/"));
         var4.__setitem__((PyObject)var7, var5);
      }

      var1.setline(982);
      var6 = (new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("linkname"), PyString.fromInterned("uname"), PyString.fromInterned("gname")})).__iter__();

      while(true) {
         var1.setline(982);
         var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(986);
            var6 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var4);
         var1.setline(983);
         var5 = var1.getglobal("type").__call__(var2, var1.getlocal(3).__getitem__(var1.getlocal(4)));
         var10000 = var5._is(var1.getglobal("unicode"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(984);
            var5 = var1.getlocal(3).__getitem__(var1.getlocal(4)).__getattr__("encode").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.getlocal(3).__setitem__(var1.getlocal(4), var5);
            var5 = null;
         }
      }
   }

   public PyObject tobuf$76(PyFrame var1, ThreadState var2) {
      var1.setline(990);
      PyString.fromInterned("Return a tar header as a string of 512 byte blocks.\n        ");
      var1.setline(991);
      PyObject var3 = var1.getlocal(0).__getattr__("get_info").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(993);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("USTAR_FORMAT"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(994);
         var3 = var1.getlocal(0).__getattr__("create_ustar_header").__call__(var2, var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(995);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(var1.getglobal("GNU_FORMAT"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(996);
            var3 = var1.getlocal(0).__getattr__("create_gnu_header").__call__(var2, var1.getlocal(4));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(997);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(var1.getglobal("PAX_FORMAT"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(998);
               var3 = var1.getlocal(0).__getattr__("create_pax_header").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1000);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid format")));
            }
         }
      }
   }

   public PyObject create_ustar_header$77(PyFrame var1, ThreadState var2) {
      var1.setline(1004);
      PyString.fromInterned("Return the object as a ustar header block.\n        ");
      var1.setline(1005);
      PyObject var3 = var1.getglobal("POSIX_MAGIC");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("magic"), var3);
      var3 = null;
      var1.setline(1007);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("linkname")));
      PyObject var10000 = var3._gt(var1.getglobal("LENGTH_LINK"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1008);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("linkname is too long")));
      } else {
         var1.setline(1010);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("name")));
         var10000 = var3._gt(var1.getglobal("LENGTH_NAME"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1011);
            var3 = var1.getlocal(0).__getattr__("_posix_split_name").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("name")));
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("prefix"), var5);
            var5 = null;
            var5 = var4[1];
            var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("name"), var5);
            var5 = null;
            var3 = null;
         }

         var1.setline(1013);
         var3 = var1.getlocal(0).__getattr__("_create_header").__call__(var2, var1.getlocal(1), var1.getglobal("USTAR_FORMAT"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject create_gnu_header$78(PyFrame var1, ThreadState var2) {
      var1.setline(1017);
      PyString.fromInterned("Return the object as a GNU header block sequence.\n        ");
      var1.setline(1018);
      PyObject var3 = var1.getglobal("GNU_MAGIC");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("magic"), var3);
      var3 = null;
      var1.setline(1020);
      PyString var4 = PyString.fromInterned("");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1021);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("linkname")));
      PyObject var10000 = var3._gt(var1.getglobal("LENGTH_LINK"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1022);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(var1.getlocal(0).__getattr__("_create_gnu_long_header").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("linkname")), var1.getglobal("GNUTYPE_LONGLINK")));
         var1.setlocal(2, var3);
      }

      var1.setline(1024);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("name")));
      var10000 = var3._gt(var1.getglobal("LENGTH_NAME"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1025);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(var1.getlocal(0).__getattr__("_create_gnu_long_header").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("name")), var1.getglobal("GNUTYPE_LONGNAME")));
         var1.setlocal(2, var3);
      }

      var1.setline(1027);
      var3 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("_create_header").__call__(var2, var1.getlocal(1), var1.getglobal("GNU_FORMAT")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject create_pax_header$79(PyFrame var1, ThreadState var2) {
      var1.setline(1033);
      PyString.fromInterned("Return the object as a ustar header block. If it cannot be\n           represented this way, prepend a pax extended header sequence\n           with supplement information.\n        ");
      var1.setline(1034);
      PyObject var3 = var1.getglobal("POSIX_MAGIC");
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("magic"), var3);
      var3 = null;
      var1.setline(1035);
      var3 = var1.getlocal(0).__getattr__("pax_headers").__getattr__("copy").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1039);
      var3 = (new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("path"), var1.getglobal("LENGTH_NAME")}), new PyTuple(new PyObject[]{PyString.fromInterned("linkname"), PyString.fromInterned("linkpath"), var1.getglobal("LENGTH_LINK")}), new PyTuple(new PyObject[]{PyString.fromInterned("uname"), PyString.fromInterned("uname"), Py.newInteger(32)}), new PyTuple(new PyObject[]{PyString.fromInterned("gname"), PyString.fromInterned("gname"), Py.newInteger(32)})})).__iter__();

      while(true) {
         PyObject[] var5;
         PyObject var8;
         PyObject var10000;
         while(true) {
            PyObject var6;
            do {
               var1.setline(1039);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1061);
                  var3 = (new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("uid"), Py.newInteger(8)}), new PyTuple(new PyObject[]{PyString.fromInterned("gid"), Py.newInteger(8)}), new PyTuple(new PyObject[]{PyString.fromInterned("size"), Py.newInteger(12)}), new PyTuple(new PyObject[]{PyString.fromInterned("mtime"), Py.newInteger(12)})})).__iter__();

                  while(true) {
                     var1.setline(1061);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(1073);
                        if (var1.getlocal(4).__nonzero__()) {
                           var1.setline(1074);
                           var3 = var1.getlocal(0).__getattr__("_create_pax_generic_header").__call__(var2, var1.getlocal(4));
                           var1.setlocal(10, var3);
                           var3 = null;
                        } else {
                           var1.setline(1076);
                           PyString var9 = PyString.fromInterned("");
                           var1.setlocal(10, var9);
                           var3 = null;
                        }

                        var1.setline(1078);
                        var3 = var1.getlocal(10)._add(var1.getlocal(0).__getattr__("_create_header").__call__(var2, var1.getlocal(1), var1.getglobal("USTAR_FORMAT")));
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var5 = Py.unpackSequence(var4, 2);
                     var6 = var5[0];
                     var1.setlocal(5, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(9, var6);
                     var6 = null;
                     var1.setline(1062);
                     var8 = var1.getlocal(5);
                     var10000 = var8._in(var1.getlocal(4));
                     var5 = null;
                     PyInteger var11;
                     if (var10000.__nonzero__()) {
                        var1.setline(1064);
                        var11 = Py.newInteger(0);
                        var1.getlocal(1).__setitem__((PyObject)var1.getlocal(5), var11);
                        var5 = null;
                     } else {
                        var1.setline(1067);
                        var8 = var1.getlocal(1).__getitem__(var1.getlocal(5));
                        var1.setlocal(8, var8);
                        var5 = null;
                        var1.setline(1068);
                        var11 = Py.newInteger(0);
                        PyObject var10001 = var1.getlocal(8);
                        PyInteger var12 = var11;
                        var8 = var10001;
                        if ((var6 = var12._le(var10001)).__nonzero__()) {
                           var6 = var8._lt(Py.newInteger(8)._pow(var1.getlocal(9)._sub(Py.newInteger(1))));
                        }

                        var5 = null;
                        var10000 = var6.__not__();
                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("float"));
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(1069);
                           var8 = var1.getglobal("unicode").__call__(var2, var1.getlocal(8));
                           var1.getlocal(4).__setitem__(var1.getlocal(5), var8);
                           var5 = null;
                           var1.setline(1070);
                           var11 = Py.newInteger(0);
                           var1.getlocal(1).__setitem__((PyObject)var1.getlocal(5), var11);
                           var5 = null;
                        }
                     }
                  }
               }

               var5 = Py.unpackSequence(var4, 3);
               var6 = var5[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var5[2];
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(1043);
               var8 = var1.getlocal(6);
               var10000 = var8._in(var1.getlocal(4));
               var5 = null;
            } while(var10000.__nonzero__());

            var1.setline(1047);
            var8 = var1.getlocal(1).__getitem__(var1.getlocal(5)).__getattr__("decode").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(8, var8);
            var5 = null;

            try {
               var1.setline(1051);
               var1.getlocal(8).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii"));
               break;
            } catch (Throwable var7) {
               PyException var10 = Py.setException(var7, var1);
               if (!var10.match(var1.getglobal("UnicodeEncodeError"))) {
                  throw var10;
               }

               var1.setline(1053);
               var6 = var1.getlocal(8);
               var1.getlocal(4).__setitem__(var1.getlocal(6), var6);
               var6 = null;
            }
         }

         var1.setline(1056);
         var8 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)));
         var10000 = var8._gt(var1.getlocal(7));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1057);
            var8 = var1.getlocal(8);
            var1.getlocal(4).__setitem__(var1.getlocal(6), var8);
            var5 = null;
         }
      }
   }

   public PyObject create_pax_global_header$80(PyFrame var1, ThreadState var2) {
      var1.setline(1083);
      PyString.fromInterned("Return the object as a pax global header block sequence.\n        ");
      var1.setline(1084);
      PyObject var10000 = var1.getlocal(0).__getattr__("_create_pax_generic_header");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("XGLTYPE")};
      String[] var4 = new String[]{"type"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _posix_split_name$81(PyFrame var1, ThreadState var2) {
      var1.setline(1089);
      PyString.fromInterned("Split a name longer than 100 chars into a prefix\n           and a name part.\n        ");
      var1.setline(1090);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getglobal("LENGTH_PREFIX")._add(Py.newInteger(1)), (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(1091);
         PyObject var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
            var10000 = var3._ne(PyString.fromInterned("/"));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(1094);
            var3 = var1.getlocal(1).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(2)), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(1095);
            var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1097);
            var10000 = var1.getlocal(2).__not__();
            if (!var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var3._gt(var1.getglobal("LENGTH_NAME"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1098);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name is too long")));
            }

            var1.setline(1099);
            PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(1092);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(2, var3);
         var3 = null;
      }
   }

   public PyObject _create_header$82(PyFrame var1, ThreadState var2) {
      var1.setline(1105);
      PyString.fromInterned("Return a header block. info is a dictionary with file\n           information, format must be one of the *_FORMAT constants.\n        ");
      var1.setline(1106);
      PyList var3 = new PyList(new PyObject[]{var1.getglobal("stn").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"), (PyObject)PyString.fromInterned("")), (PyObject)Py.newInteger(100)), var1.getglobal("itn").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mode"), (PyObject)Py.newInteger(0))._and(Py.newInteger(4095)), (PyObject)Py.newInteger(8), (PyObject)var1.getlocal(1)), var1.getglobal("itn").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("uid"), (PyObject)Py.newInteger(0)), (PyObject)Py.newInteger(8), (PyObject)var1.getlocal(1)), var1.getglobal("itn").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gid"), (PyObject)Py.newInteger(0)), (PyObject)Py.newInteger(8), (PyObject)var1.getlocal(1)), var1.getglobal("itn").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("size"), (PyObject)Py.newInteger(0)), (PyObject)Py.newInteger(12), (PyObject)var1.getlocal(1)), var1.getglobal("itn").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mtime"), (PyObject)Py.newInteger(0)), (PyObject)Py.newInteger(12), (PyObject)var1.getlocal(1)), PyString.fromInterned("        "), var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("type"), (PyObject)var1.getglobal("REGTYPE")), var1.getglobal("stn").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("linkname"), (PyObject)PyString.fromInterned("")), (PyObject)Py.newInteger(100)), var1.getglobal("stn").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("magic"), (PyObject)var1.getglobal("POSIX_MAGIC")), (PyObject)Py.newInteger(8)), var1.getglobal("stn").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("uname"), (PyObject)PyString.fromInterned("")), (PyObject)Py.newInteger(32)), var1.getglobal("stn").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gname"), (PyObject)PyString.fromInterned("")), (PyObject)Py.newInteger(32)), var1.getglobal("itn").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("devmajor"), (PyObject)Py.newInteger(0)), (PyObject)Py.newInteger(8), (PyObject)var1.getlocal(1)), var1.getglobal("itn").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("devminor"), (PyObject)Py.newInteger(0)), (PyObject)Py.newInteger(8), (PyObject)var1.getlocal(1)), var1.getglobal("stn").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prefix"), (PyObject)PyString.fromInterned("")), (PyObject)Py.newInteger(155))});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1124);
      PyObject var4 = var1.getglobal("struct").__getattr__("pack").__call__(var2, PyString.fromInterned("%ds")._mod(var1.getglobal("BLOCKSIZE")), PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2)));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(1125);
      var4 = var1.getglobal("calc_chksums").__call__(var2, var1.getlocal(3).__getslice__(var1.getglobal("BLOCKSIZE").__neg__(), (PyObject)null, (PyObject)null)).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(1126);
      var4 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-364), (PyObject)null)._add(PyString.fromInterned("%06o\u0000")._mod(var1.getlocal(4)))._add(var1.getlocal(3).__getslice__(Py.newInteger(-357), (PyObject)null, (PyObject)null));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(1127);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _create_payload$83(PyFrame var1, ThreadState var2) {
      var1.setline(1133);
      PyString.fromInterned("Return the string payload filled with zero bytes\n           up to the next 512 byte border.\n        ");
      var1.setline(1134);
      PyObject var3 = var1.getglobal("divmod").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0)), var1.getglobal("BLOCKSIZE"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1135);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1136);
         var3 = var1.getlocal(0);
         var3 = var3._iadd(var1.getglobal("BLOCKSIZE")._sub(var1.getlocal(2))._mul(var1.getglobal("NUL")));
         var1.setlocal(0, var3);
      }

      var1.setline(1137);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _create_gnu_long_header$84(PyFrame var1, ThreadState var2) {
      var1.setline(1143);
      PyString.fromInterned("Return a GNUTYPE_LONGNAME or GNUTYPE_LONGLINK sequence\n           for name.\n        ");
      var1.setline(1144);
      PyObject var3 = var1.getlocal(1);
      var3 = var3._iadd(var1.getglobal("NUL"));
      var1.setlocal(1, var3);
      var1.setline(1146);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(1147);
      PyString var5 = PyString.fromInterned("././@LongLink");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("name"), var5);
      var3 = null;
      var1.setline(1148);
      var3 = var1.getlocal(2);
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("type"), var3);
      var3 = null;
      var1.setline(1149);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("size"), var3);
      var3 = null;
      var1.setline(1150);
      var3 = var1.getglobal("GNU_MAGIC");
      var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("magic"), var3);
      var3 = null;
      var1.setline(1153);
      var3 = var1.getlocal(0).__getattr__("_create_header").__call__(var2, var1.getlocal(3), var1.getglobal("USTAR_FORMAT"))._add(var1.getlocal(0).__getattr__("_create_payload").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _create_pax_generic_header$85(PyFrame var1, ThreadState var2) {
      var1.setline(1161);
      PyString.fromInterned("Return a POSIX.1-2001 extended or global header sequence\n           that contains a list of keyword, value pairs. The values\n           must be unicode objects.\n        ");
      var1.setline(1162);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1163);
      PyObject var7 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(1163);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(1174);
            var7 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(1178);
            PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(9, var8);
            var3 = null;
            var1.setline(1179);
            PyString var9 = PyString.fromInterned("././@PaxHeader");
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("name"), var9);
            var3 = null;
            var1.setline(1180);
            var7 = var1.getlocal(2);
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("type"), var7);
            var3 = null;
            var1.setline(1181);
            var7 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("size"), var7);
            var3 = null;
            var1.setline(1182);
            var7 = var1.getglobal("POSIX_MAGIC");
            var1.getlocal(9).__setitem__((PyObject)PyString.fromInterned("magic"), var7);
            var3 = null;
            var1.setline(1185);
            var7 = var1.getlocal(0).__getattr__("_create_header").__call__(var2, var1.getlocal(9), var1.getglobal("USTAR_FORMAT"))._add(var1.getlocal(0).__getattr__("_create_payload").__call__(var2, var1.getlocal(3)));
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
         var1.setline(1164);
         PyObject var10 = var1.getlocal(4).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf8"));
         var1.setlocal(4, var10);
         var5 = null;
         var1.setline(1165);
         var10 = var1.getlocal(5).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf8"));
         var1.setlocal(5, var10);
         var5 = null;
         var1.setline(1166);
         var10 = var1.getglobal("len").__call__(var2, var1.getlocal(4))._add(var1.getglobal("len").__call__(var2, var1.getlocal(5)))._add(Py.newInteger(3));
         var1.setlocal(6, var10);
         var5 = null;
         var1.setline(1167);
         PyInteger var11 = Py.newInteger(0);
         var1.setlocal(7, var11);
         var1.setlocal(8, var11);

         while(true) {
            var1.setline(1168);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(1169);
            var10 = var1.getlocal(6)._add(var1.getglobal("len").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(8))));
            var1.setlocal(7, var10);
            var5 = null;
            var1.setline(1170);
            var10 = var1.getlocal(7);
            PyObject var10000 = var10._eq(var1.getlocal(8));
            var5 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(1172);
            var10 = var1.getlocal(7);
            var1.setlocal(8, var10);
            var5 = null;
         }

         var1.setline(1173);
         var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("%d %s=%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(4), var1.getlocal(5)})));
      }
   }

   public PyObject frombuf$86(PyFrame var1, ThreadState var2) {
      var1.setline(1191);
      PyString.fromInterned("Construct a TarInfo object from a 512 byte string buffer.\n        ");
      var1.setline(1192);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1193);
         throw Py.makeException(var1.getglobal("EmptyHeaderError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("empty header")));
      } else {
         var1.setline(1194);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._ne(var1.getglobal("BLOCKSIZE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1195);
            throw Py.makeException(var1.getglobal("TruncatedHeaderError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("truncated header")));
         } else {
            var1.setline(1196);
            var3 = var1.getlocal(1).__getattr__("count").__call__(var2, var1.getglobal("NUL"));
            var10000 = var3._eq(var1.getglobal("BLOCKSIZE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1197);
               throw Py.makeException(var1.getglobal("EOFHeaderError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("end of file header")));
            } else {
               var1.setline(1199);
               var3 = var1.getglobal("nti").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(148), Py.newInteger(156), (PyObject)null));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(1200);
               var3 = var1.getlocal(2);
               var10000 = var3._notin(var1.getglobal("calc_chksums").__call__(var2, var1.getlocal(1)));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1201);
                  throw Py.makeException(var1.getglobal("InvalidHeaderError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bad checksum")));
               } else {
                  var1.setline(1203);
                  var3 = var1.getlocal(0).__call__(var2);
                  var1.setlocal(3, var3);
                  var3 = null;
                  var1.setline(1204);
                  var3 = var1.getlocal(1);
                  var1.getlocal(3).__setattr__("buf", var3);
                  var3 = null;
                  var1.setline(1205);
                  var3 = var1.getglobal("nts").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(0), Py.newInteger(100), (PyObject)null));
                  var1.getlocal(3).__setattr__("name", var3);
                  var3 = null;
                  var1.setline(1206);
                  var3 = var1.getglobal("nti").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(100), Py.newInteger(108), (PyObject)null));
                  var1.getlocal(3).__setattr__("mode", var3);
                  var3 = null;
                  var1.setline(1207);
                  var3 = var1.getglobal("nti").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(108), Py.newInteger(116), (PyObject)null));
                  var1.getlocal(3).__setattr__("uid", var3);
                  var3 = null;
                  var1.setline(1208);
                  var3 = var1.getglobal("nti").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(116), Py.newInteger(124), (PyObject)null));
                  var1.getlocal(3).__setattr__("gid", var3);
                  var3 = null;
                  var1.setline(1209);
                  var3 = var1.getglobal("nti").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(124), Py.newInteger(136), (PyObject)null));
                  var1.getlocal(3).__setattr__("size", var3);
                  var3 = null;
                  var1.setline(1210);
                  var3 = var1.getglobal("nti").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(136), Py.newInteger(148), (PyObject)null));
                  var1.getlocal(3).__setattr__("mtime", var3);
                  var3 = null;
                  var1.setline(1211);
                  var3 = var1.getlocal(2);
                  var1.getlocal(3).__setattr__("chksum", var3);
                  var3 = null;
                  var1.setline(1212);
                  var3 = var1.getlocal(1).__getslice__(Py.newInteger(156), Py.newInteger(157), (PyObject)null);
                  var1.getlocal(3).__setattr__("type", var3);
                  var3 = null;
                  var1.setline(1213);
                  var3 = var1.getglobal("nts").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(157), Py.newInteger(257), (PyObject)null));
                  var1.getlocal(3).__setattr__("linkname", var3);
                  var3 = null;
                  var1.setline(1214);
                  var3 = var1.getglobal("nts").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(265), Py.newInteger(297), (PyObject)null));
                  var1.getlocal(3).__setattr__("uname", var3);
                  var3 = null;
                  var1.setline(1215);
                  var3 = var1.getglobal("nts").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(297), Py.newInteger(329), (PyObject)null));
                  var1.getlocal(3).__setattr__("gname", var3);
                  var3 = null;
                  var1.setline(1216);
                  var3 = var1.getglobal("nti").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(329), Py.newInteger(337), (PyObject)null));
                  var1.getlocal(3).__setattr__("devmajor", var3);
                  var3 = null;
                  var1.setline(1217);
                  var3 = var1.getglobal("nti").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(337), Py.newInteger(345), (PyObject)null));
                  var1.getlocal(3).__setattr__("devminor", var3);
                  var3 = null;
                  var1.setline(1218);
                  var3 = var1.getglobal("nts").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(345), Py.newInteger(500), (PyObject)null));
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(1222);
                  var3 = var1.getlocal(3).__getattr__("type");
                  var10000 = var3._eq(var1.getglobal("AREGTYPE"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(3).__getattr__("name").__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1223);
                     var3 = var1.getglobal("DIRTYPE");
                     var1.getlocal(3).__setattr__("type", var3);
                     var3 = null;
                  }

                  var1.setline(1226);
                  if (var1.getlocal(3).__getattr__("isdir").__call__(var2).__nonzero__()) {
                     var1.setline(1227);
                     var3 = var1.getlocal(3).__getattr__("name").__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
                     var1.getlocal(3).__setattr__("name", var3);
                     var3 = null;
                  }

                  var1.setline(1230);
                  var10000 = var1.getlocal(4);
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(3).__getattr__("type");
                     var10000 = var3._notin(var1.getglobal("GNU_TYPES"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1231);
                     var3 = var1.getlocal(4)._add(PyString.fromInterned("/"))._add(var1.getlocal(3).__getattr__("name"));
                     var1.getlocal(3).__setattr__("name", var3);
                     var3 = null;
                  }

                  var1.setline(1232);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject fromtarfile$87(PyFrame var1, ThreadState var2) {
      var1.setline(1238);
      PyString.fromInterned("Return the next TarInfo object from TarFile object\n           tarfile.\n        ");
      var1.setline(1239);
      PyObject var3 = var1.getlocal(1).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getglobal("BLOCKSIZE"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1240);
      var3 = var1.getlocal(0).__getattr__("frombuf").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1241);
      var3 = var1.getlocal(1).__getattr__("fileobj").__getattr__("tell").__call__(var2)._sub(var1.getglobal("BLOCKSIZE"));
      var1.getlocal(3).__setattr__("offset", var3);
      var3 = null;
      var1.setline(1242);
      var3 = var1.getlocal(3).__getattr__("_proc_member").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _proc_member$88(PyFrame var1, ThreadState var2) {
      var1.setline(1258);
      PyString.fromInterned("Choose the right processing method depending on\n           the type and call it.\n        ");
      var1.setline(1259);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("GNUTYPE_LONGNAME"), var1.getglobal("GNUTYPE_LONGLINK")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1260);
         var3 = var1.getlocal(0).__getattr__("_proc_gnulong").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1261);
         PyObject var4 = var1.getlocal(0).__getattr__("type");
         var10000 = var4._eq(var1.getglobal("GNUTYPE_SPARSE"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1262);
            var3 = var1.getlocal(0).__getattr__("_proc_sparse").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1263);
            var4 = var1.getlocal(0).__getattr__("type");
            var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("XHDTYPE"), var1.getglobal("XGLTYPE"), var1.getglobal("SOLARIS_XHDTYPE")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1264);
               var3 = var1.getlocal(0).__getattr__("_proc_pax").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1266);
               var3 = var1.getlocal(0).__getattr__("_proc_builtin").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _proc_builtin$89(PyFrame var1, ThreadState var2) {
      var1.setline(1271);
      PyString.fromInterned("Process a builtin type or an unknown type which\n           will be treated as a regular file.\n        ");
      var1.setline(1272);
      PyObject var3 = var1.getlocal(1).__getattr__("fileobj").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("offset_data", var3);
      var3 = null;
      var1.setline(1273);
      var3 = var1.getlocal(0).__getattr__("offset_data");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1274);
      PyObject var10000 = var1.getlocal(0).__getattr__("isreg").__call__(var2);
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("type");
         var10000 = var3._notin(var1.getglobal("SUPPORTED_TYPES"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1276);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(var1.getlocal(0).__getattr__("_block").__call__(var2, var1.getlocal(0).__getattr__("size")));
         var1.setlocal(2, var3);
      }

      var1.setline(1277);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("offset", var3);
      var3 = null;
      var1.setline(1281);
      var1.getlocal(0).__getattr__("_apply_pax_info").__call__(var2, var1.getlocal(1).__getattr__("pax_headers"), var1.getlocal(1).__getattr__("encoding"), var1.getlocal(1).__getattr__("errors"));
      var1.setline(1283);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _proc_gnulong$90(PyFrame var1, ThreadState var2) {
      var1.setline(1288);
      PyString.fromInterned("Process the blocks that hold a GNU longname\n           or longlink member.\n        ");
      var1.setline(1289);
      PyObject var3 = var1.getlocal(1).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("_block").__call__(var2, var1.getlocal(0).__getattr__("size")));
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(1293);
         var3 = var1.getlocal(0).__getattr__("fromtarfile").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (var5.match(var1.getglobal("HeaderError"))) {
            var1.setline(1295);
            throw Py.makeException(var1.getglobal("SubsequentHeaderError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("missing or bad subsequent header")));
         }

         throw var5;
      }

      var1.setline(1299);
      var3 = var1.getlocal(0).__getattr__("offset");
      var1.getlocal(3).__setattr__("offset", var3);
      var3 = null;
      var1.setline(1300);
      var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("GNUTYPE_LONGNAME"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1301);
         var3 = var1.getglobal("nts").__call__(var2, var1.getlocal(2));
         var1.getlocal(3).__setattr__("name", var3);
         var3 = null;
      } else {
         var1.setline(1302);
         var3 = var1.getlocal(0).__getattr__("type");
         var10000 = var3._eq(var1.getglobal("GNUTYPE_LONGLINK"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1303);
            var3 = var1.getglobal("nts").__call__(var2, var1.getlocal(2));
            var1.getlocal(3).__setattr__("linkname", var3);
            var3 = null;
         }
      }

      var1.setline(1305);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _proc_sparse$91(PyFrame var1, ThreadState var2) {
      var1.setline(1309);
      PyString.fromInterned("Process a GNU sparse header plus extra headers.\n        ");
      var1.setline(1310);
      PyObject var3 = var1.getlocal(0).__getattr__("buf");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1311);
      var3 = var1.getglobal("_ringbuffer").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1312);
      PyInteger var8 = Py.newInteger(386);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(1313);
      PyLong var9 = Py.newLong("0");
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(1314);
      var9 = Py.newLong("0");
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(1317);
      var3 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(4)).__iter__();

      PyObject var10000;
      PyObject var4;
      PyException var5;
      PyObject var10;
      while(true) {
         var1.setline(1317);
         var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(7, var4);

         try {
            var1.setline(1319);
            var10 = var1.getglobal("nti").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(12)), (PyObject)null));
            var1.setlocal(8, var10);
            var5 = null;
            var1.setline(1320);
            var10 = var1.getglobal("nti").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(4)._add(Py.newInteger(12)), var1.getlocal(4)._add(Py.newInteger(24)), (PyObject)null));
            var1.setlocal(9, var10);
            var5 = null;
         } catch (Throwable var7) {
            var5 = Py.setException(var7, var1);
            if (var5.match(var1.getglobal("ValueError"))) {
               break;
            }

            throw var5;
         }

         var1.setline(1323);
         var10 = var1.getlocal(8);
         var10000 = var10._gt(var1.getlocal(5));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1324);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("_hole").__call__(var2, var1.getlocal(5), var1.getlocal(8)._sub(var1.getlocal(5))));
         }

         var1.setline(1325);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("_data").__call__(var2, var1.getlocal(8), var1.getlocal(9), var1.getlocal(6)));
         var1.setline(1326);
         var10 = var1.getlocal(6);
         var10 = var10._iadd(var1.getlocal(9));
         var1.setlocal(6, var10);
         var1.setline(1327);
         var10 = var1.getlocal(8)._add(var1.getlocal(9));
         var1.setlocal(5, var10);
         var5 = null;
         var1.setline(1328);
         var10 = var1.getlocal(4);
         var10 = var10._iadd(Py.newInteger(24));
         var1.setlocal(4, var10);
      }

      var1.setline(1330);
      var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(482)));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(1331);
      var3 = var1.getglobal("nti").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(483), Py.newInteger(495), (PyObject)null));
      var1.setlocal(11, var3);
      var3 = null;

      while(true) {
         var1.setline(1335);
         var3 = var1.getlocal(10);
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1352);
            var3 = var1.getlocal(5);
            var10000 = var3._lt(var1.getlocal(11));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1353);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("_hole").__call__(var2, var1.getlocal(5), var1.getlocal(11)._sub(var1.getlocal(5))));
            }

            var1.setline(1355);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("sparse", var3);
            var3 = null;
            var1.setline(1357);
            var3 = var1.getlocal(1).__getattr__("fileobj").__getattr__("tell").__call__(var2);
            var1.getlocal(0).__setattr__("offset_data", var3);
            var3 = null;
            var1.setline(1358);
            var3 = var1.getlocal(0).__getattr__("offset_data")._add(var1.getlocal(0).__getattr__("_block").__call__(var2, var1.getlocal(0).__getattr__("size")));
            var1.getlocal(1).__setattr__("offset", var3);
            var3 = null;
            var1.setline(1359);
            var3 = var1.getlocal(11);
            var1.getlocal(0).__setattr__("size", var3);
            var3 = null;
            var1.setline(1361);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1336);
         var3 = var1.getlocal(1).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getglobal("BLOCKSIZE"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1337);
         var8 = Py.newInteger(0);
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(1338);
         var3 = var1.getglobal("xrange").__call__((ThreadState)var2, (PyObject)Py.newInteger(21)).__iter__();

         while(true) {
            var1.setline(1338);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(7, var4);

            try {
               var1.setline(1340);
               var10 = var1.getglobal("nti").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(4), var1.getlocal(4)._add(Py.newInteger(12)), (PyObject)null));
               var1.setlocal(8, var10);
               var5 = null;
               var1.setline(1341);
               var10 = var1.getglobal("nti").__call__(var2, var1.getlocal(2).__getslice__(var1.getlocal(4)._add(Py.newInteger(12)), var1.getlocal(4)._add(Py.newInteger(24)), (PyObject)null));
               var1.setlocal(9, var10);
               var5 = null;
            } catch (Throwable var6) {
               var5 = Py.setException(var6, var1);
               if (var5.match(var1.getglobal("ValueError"))) {
                  break;
               }

               throw var5;
            }

            var1.setline(1344);
            var10 = var1.getlocal(8);
            var10000 = var10._gt(var1.getlocal(5));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1345);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("_hole").__call__(var2, var1.getlocal(5), var1.getlocal(8)._sub(var1.getlocal(5))));
            }

            var1.setline(1346);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("_data").__call__(var2, var1.getlocal(8), var1.getlocal(9), var1.getlocal(6)));
            var1.setline(1347);
            var10 = var1.getlocal(6);
            var10 = var10._iadd(var1.getlocal(9));
            var1.setlocal(6, var10);
            var1.setline(1348);
            var10 = var1.getlocal(8)._add(var1.getlocal(9));
            var1.setlocal(5, var10);
            var5 = null;
            var1.setline(1349);
            var10 = var1.getlocal(4);
            var10 = var10._iadd(Py.newInteger(24));
            var1.setlocal(4, var10);
         }

         var1.setline(1350);
         var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(504)));
         var1.setlocal(10, var3);
         var3 = null;
      }
   }

   public PyObject _proc_pax$92(PyFrame var1, ThreadState var2) {
      var1.setline(1366);
      PyString.fromInterned("Process an extended or global header as described in\n           POSIX.1-2001.\n        ");
      var1.setline(1368);
      PyObject var3 = var1.getlocal(1).__getattr__("fileobj").__getattr__("read").__call__(var2, var1.getlocal(0).__getattr__("_block").__call__(var2, var1.getlocal(0).__getattr__("size")));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1373);
      var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("XGLTYPE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1374);
         var3 = var1.getlocal(1).__getattr__("pax_headers");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(1376);
         var3 = var1.getlocal(1).__getattr__("pax_headers").__getattr__("copy").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1382);
      var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\d+) ([^=]+)="), (PyObject)var1.getglobal("re").__getattr__("U"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1383);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(5, var7);
      var3 = null;

      while(true) {
         var1.setline(1384);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(1385);
         var3 = var1.getlocal(4).__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(5));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1386);
         if (var1.getlocal(6).__not__().__nonzero__()) {
            break;
         }

         var1.setline(1389);
         var3 = var1.getlocal(6).__getattr__("groups").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
         var1.setline(1390);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(7));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1391);
         var3 = var1.getlocal(2).__getslice__(var1.getlocal(6).__getattr__("end").__call__((ThreadState)var2, (PyObject)Py.newInteger(2))._add(Py.newInteger(1)), var1.getlocal(6).__getattr__("start").__call__((ThreadState)var2, (PyObject)Py.newInteger(1))._add(var1.getlocal(7))._sub(Py.newInteger(1)), (PyObject)null);
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(1393);
         var3 = var1.getlocal(8).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf8"));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(1394);
         var3 = var1.getlocal(9).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf8"));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(1396);
         var3 = var1.getlocal(9);
         var1.getlocal(3).__setitem__(var1.getlocal(8), var3);
         var3 = null;
         var1.setline(1397);
         var3 = var1.getlocal(5);
         var3 = var3._iadd(var1.getlocal(7));
         var1.setlocal(5, var3);
      }

      try {
         var1.setline(1401);
         var3 = var1.getlocal(0).__getattr__("fromtarfile").__call__(var2, var1.getlocal(1));
         var1.setlocal(10, var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var8 = Py.setException(var6, var1);
         if (var8.match(var1.getglobal("HeaderError"))) {
            var1.setline(1403);
            throw Py.makeException(var1.getglobal("SubsequentHeaderError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("missing or bad subsequent header")));
         }

         throw var8;
      }

      var1.setline(1405);
      var3 = var1.getlocal(0).__getattr__("type");
      var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("XHDTYPE"), var1.getglobal("SOLARIS_XHDTYPE")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1407);
         var1.getlocal(10).__getattr__("_apply_pax_info").__call__(var2, var1.getlocal(3), var1.getlocal(1).__getattr__("encoding"), var1.getlocal(1).__getattr__("errors"));
         var1.setline(1408);
         var3 = var1.getlocal(0).__getattr__("offset");
         var1.getlocal(10).__setattr__("offset", var3);
         var3 = null;
         var1.setline(1410);
         PyString var9 = PyString.fromInterned("size");
         var10000 = var9._in(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1414);
            var3 = var1.getlocal(10).__getattr__("offset_data");
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(1415);
            var10000 = var1.getlocal(10).__getattr__("isreg").__call__(var2);
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(10).__getattr__("type");
               var10000 = var3._notin(var1.getglobal("SUPPORTED_TYPES"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1416);
               var3 = var1.getlocal(11);
               var3 = var3._iadd(var1.getlocal(10).__getattr__("_block").__call__(var2, var1.getlocal(10).__getattr__("size")));
               var1.setlocal(11, var3);
            }

            var1.setline(1417);
            var3 = var1.getlocal(11);
            var1.getlocal(1).__setattr__("offset", var3);
            var3 = null;
         }
      }

      var1.setline(1419);
      var3 = var1.getlocal(10);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _apply_pax_info$93(PyFrame var1, ThreadState var2) {
      var1.setline(1424);
      PyString.fromInterned("Replace fields with supplemental information from a previous\n           pax extended or global header.\n        ");
      var1.setline(1425);
      PyObject var3 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         PyObject var10000;
         PyObject[] var5;
         PyObject var6;
         PyObject var8;
         do {
            var1.setline(1425);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1442);
               var3 = var1.getlocal(1).__getattr__("copy").__call__(var2);
               var1.getlocal(0).__setattr__("pax_headers", var3);
               var3 = null;
               var1.f_lasti = -1;
               return Py.None;
            }

            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(1426);
            var8 = var1.getlocal(4);
            var10000 = var8._notin(var1.getglobal("PAX_FIELDS"));
            var5 = null;
         } while(var10000.__nonzero__());

         var1.setline(1429);
         var8 = var1.getlocal(4);
         var10000 = var8._eq(PyString.fromInterned("path"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1430);
            var8 = var1.getlocal(5).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
            var1.setlocal(5, var8);
            var5 = null;
         }

         var1.setline(1432);
         var8 = var1.getlocal(4);
         var10000 = var8._in(var1.getglobal("PAX_NUMBER_FIELDS"));
         var5 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(1434);
               var8 = var1.getglobal("PAX_NUMBER_FIELDS").__getitem__(var1.getlocal(4)).__call__(var2, var1.getlocal(5));
               var1.setlocal(5, var8);
               var5 = null;
            } catch (Throwable var7) {
               PyException var10 = Py.setException(var7, var1);
               if (!var10.match(var1.getglobal("ValueError"))) {
                  throw var10;
               }

               var1.setline(1436);
               PyInteger var9 = Py.newInteger(0);
               var1.setlocal(5, var9);
               var6 = null;
            }
         } else {
            var1.setline(1438);
            var8 = var1.getglobal("uts").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(5, var8);
            var5 = null;
         }

         var1.setline(1440);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(4), var1.getlocal(5));
      }
   }

   public PyObject _block$94(PyFrame var1, ThreadState var2) {
      var1.setline(1447);
      PyString.fromInterned("Round up a byte count by BLOCKSIZE and return it,\n           e.g. _block(834) => 1024.\n        ");
      var1.setline(1448);
      PyObject var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(1), var1.getglobal("BLOCKSIZE"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1449);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1450);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(2, var3);
      }

      var1.setline(1451);
      var3 = var1.getlocal(2)._mul(var1.getglobal("BLOCKSIZE"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isreg$95(PyFrame var1, ThreadState var2) {
      var1.setline(1454);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._in(var1.getglobal("REGULAR_TYPES"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isfile$96(PyFrame var1, ThreadState var2) {
      var1.setline(1456);
      PyObject var3 = var1.getlocal(0).__getattr__("isreg").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isdir$97(PyFrame var1, ThreadState var2) {
      var1.setline(1458);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("DIRTYPE"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject issym$98(PyFrame var1, ThreadState var2) {
      var1.setline(1460);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("SYMTYPE"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject islnk$99(PyFrame var1, ThreadState var2) {
      var1.setline(1462);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("LNKTYPE"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ischr$100(PyFrame var1, ThreadState var2) {
      var1.setline(1464);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("CHRTYPE"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isblk$101(PyFrame var1, ThreadState var2) {
      var1.setline(1466);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("BLKTYPE"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isfifo$102(PyFrame var1, ThreadState var2) {
      var1.setline(1468);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("FIFOTYPE"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject issparse$103(PyFrame var1, ThreadState var2) {
      var1.setline(1470);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("GNUTYPE_SPARSE"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isdev$104(PyFrame var1, ThreadState var2) {
      var1.setline(1472);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("CHRTYPE"), var1.getglobal("BLKTYPE"), var1.getglobal("FIFOTYPE")}));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TarFile$105(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The TarFile Class provides an interface to tar archives.\n    "));
      var1.setline(1477);
      PyString.fromInterned("The TarFile Class provides an interface to tar archives.\n    ");
      var1.setline(1479);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("debug", var3);
      var3 = null;
      var1.setline(1481);
      PyObject var4 = var1.getname("False");
      var1.setlocal("dereference", var4);
      var3 = null;
      var1.setline(1484);
      var4 = var1.getname("False");
      var1.setlocal("ignore_zeros", var4);
      var3 = null;
      var1.setline(1487);
      var3 = Py.newInteger(1);
      var1.setlocal("errorlevel", var3);
      var3 = null;
      var1.setline(1491);
      var4 = var1.getname("DEFAULT_FORMAT");
      var1.setlocal("format", var4);
      var3 = null;
      var1.setline(1493);
      var4 = var1.getname("ENCODING");
      var1.setlocal("encoding", var4);
      var3 = null;
      var1.setline(1495);
      var4 = var1.getname("None");
      var1.setlocal("errors", var4);
      var3 = null;
      var1.setline(1497);
      var4 = var1.getname("TarInfo");
      var1.setlocal("tarinfo", var4);
      var3 = null;
      var1.setline(1499);
      var4 = var1.getname("ExFileObject");
      var1.setlocal("fileobject", var4);
      var3 = null;
      var1.setline(1501);
      PyObject[] var5 = new PyObject[]{var1.getname("None"), PyString.fromInterned("r"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$106, PyString.fromInterned("Open an (uncompressed) tar archive `name'. `mode' is either 'r' to\n           read from an existing archive, 'a' to append data to an existing\n           file or 'w' to create a new file overwriting an existing one. `mode'\n           defaults to 'r'.\n           If `fileobj' is given, it is used for reading or writing data. If it\n           can be determined, `mode' is overridden by `fileobj's mode.\n           `fileobj' is not closed, when TarFile is closed.\n        "));
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(1603);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _getposix$107, (PyObject)null);
      var1.setlocal("_getposix", var6);
      var3 = null;
      var1.setline(1605);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _setposix$108, (PyObject)null);
      var1.setlocal("_setposix", var6);
      var3 = null;
      var1.setline(1613);
      var4 = var1.getname("property").__call__(var2, var1.getname("_getposix"), var1.getname("_setposix"));
      var1.setlocal("posix", var4);
      var3 = null;
      var1.setline(1626);
      var5 = new PyObject[]{var1.getname("None"), PyString.fromInterned("r"), var1.getname("None"), var1.getname("RECORDSIZE")};
      var6 = new PyFunction(var1.f_globals, var5, open$109, PyString.fromInterned("Open a tar archive for reading, writing or appending. Return\n           an appropriate TarFile class.\n\n           mode:\n           'r' or 'r:*' open for reading with transparent compression\n           'r:'         open for reading exclusively uncompressed\n           'r:gz'       open for reading with gzip compression\n           'r:bz2'      open for reading with bzip2 compression\n           'a' or 'a:'  open for appending, creating the file if necessary\n           'w' or 'w:'  open for writing without compression\n           'w:gz'       open for writing with gzip compression\n           'w:bz2'      open for writing with bzip2 compression\n\n           'r|*'        open a stream of tar blocks with transparent compression\n           'r|'         open an uncompressed stream of tar blocks for reading\n           'r|gz'       open a gzip compressed stream of tar blocks\n           'r|bz2'      open a bzip2 compressed stream of tar blocks\n           'w|'         open an uncompressed stream for writing\n           'w|gz'       open a gzip compressed stream for writing\n           'w|bz2'      open a bzip2 compressed stream for writing\n        "));
      var4 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("open", var4);
      var3 = null;
      var1.setline(1699);
      var5 = new PyObject[]{PyString.fromInterned("r"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, taropen$110, PyString.fromInterned("Open uncompressed tar archive name for reading or writing.\n        "));
      var4 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("taropen", var4);
      var3 = null;
      var1.setline(1707);
      var5 = new PyObject[]{PyString.fromInterned("r"), var1.getname("None"), Py.newInteger(9)};
      var6 = new PyFunction(var1.f_globals, var5, gzopen$111, PyString.fromInterned("Open gzip compressed tar archive name for reading or writing.\n           Appending is not allowed.\n        "));
      var4 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("gzopen", var4);
      var3 = null;
      var1.setline(1730);
      var5 = new PyObject[]{PyString.fromInterned("r"), var1.getname("None"), Py.newInteger(9)};
      var6 = new PyFunction(var1.f_globals, var5, bz2open$112, PyString.fromInterned("Open bzip2 compressed tar archive name for reading or writing.\n           Appending is not allowed.\n        "));
      var4 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("bz2open", var4);
      var3 = null;
      var1.setline(1756);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("tar"), PyString.fromInterned("taropen"), PyString.fromInterned("gz"), PyString.fromInterned("gzopen"), PyString.fromInterned("bz2"), PyString.fromInterned("bz2open")});
      var1.setlocal("OPEN_METH", var7);
      var3 = null;
      var1.setline(1765);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, close$113, PyString.fromInterned("Close the TarFile. In write-mode, two finishing zero blocks are\n           appended to the archive.\n        "));
      var1.setlocal("close", var6);
      var3 = null;
      var1.setline(1785);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getmember$114, PyString.fromInterned("Return a TarInfo object for member `name'. If `name' can not be\n           found in the archive, KeyError is raised. If a member occurs more\n           than once in the archive, its last occurrence is assumed to be the\n           most up-to-date version.\n        "));
      var1.setlocal("getmember", var6);
      var3 = null;
      var1.setline(1796);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getmembers$115, PyString.fromInterned("Return the members of the archive as a list of TarInfo objects. The\n           list has the same order as the members in the archive.\n        "));
      var1.setlocal("getmembers", var6);
      var3 = null;
      var1.setline(1806);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getnames$116, PyString.fromInterned("Return the members of the archive as a list of their names. It has\n           the same order as the list returned by getmembers().\n        "));
      var1.setlocal("getnames", var6);
      var3 = null;
      var1.setline(1812);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, gettarinfo$117, PyString.fromInterned("Create a TarInfo object for either the file `name' or the file\n           object `fileobj' (using os.fstat on its file descriptor). You can\n           modify some of the TarInfo's attributes before you add it using\n           addfile(). If given, `arcname' specifies an alternative name for the\n           file in the archive.\n        "));
      var1.setlocal("gettarinfo", var6);
      var3 = null;
      var1.setline(1910);
      var5 = new PyObject[]{var1.getname("True")};
      var6 = new PyFunction(var1.f_globals, var5, list$118, PyString.fromInterned("Print a table of contents to sys.stdout. If `verbose' is False, only\n           the names of the members are printed. If it is True, an `ls -l'-like\n           output is produced.\n        "));
      var1.setlocal("list", var6);
      var3 = null;
      var1.setline(1939);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("True"), var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, add$119, PyString.fromInterned("Add the file `name' to the archive. `name' may be any type of file\n           (directory, fifo, symbolic link, etc.). If given, `arcname'\n           specifies an alternative name for the file in the archive.\n           Directories are added recursively by default. This can be avoided by\n           setting `recursive' to False. `exclude' is a function that should\n           return True for each filename to be excluded. `filter' is a function\n           that expects a TarInfo object argument and returns the changed\n           TarInfo object, if it returns None the TarInfo object will be\n           excluded from the archive.\n        "));
      var1.setlocal("add", var6);
      var3 = null;
      var1.setline(2000);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, addfile$120, PyString.fromInterned("Add the TarInfo object `tarinfo' to the archive. If `fileobj' is\n           given, tarinfo.size bytes are read from it and added to the archive.\n           You can create TarInfo objects using gettarinfo().\n           On Windows platforms, `fileobj' should always be opened with mode\n           'rb' to avoid irritation about the file size.\n        "));
      var1.setlocal("addfile", var6);
      var3 = null;
      var1.setline(2026);
      var5 = new PyObject[]{PyString.fromInterned("."), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, extractall$121, PyString.fromInterned("Extract all members from the archive to the current working\n           directory and set owner, modification time and permissions on\n           directories afterwards. `path' specifies a different directory\n           to extract to. `members' is optional and must be a subset of the\n           list returned by getmembers().\n        "));
      var1.setlocal("extractall", var6);
      var3 = null;
      var1.setline(2063);
      var5 = new PyObject[]{PyString.fromInterned("")};
      var6 = new PyFunction(var1.f_globals, var5, extract$122, PyString.fromInterned("Extract a member from the archive to the current working directory,\n           using its full name. Its file information is extracted as accurately\n           as possible. `member' may be a filename or a TarInfo object. You can\n           specify a different directory using `path'.\n        "));
      var1.setlocal("extract", var6);
      var3 = null;
      var1.setline(2096);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, extractfile$123, PyString.fromInterned("Extract a member from the archive as a file object. `member' may be\n           a filename or a TarInfo object. If `member' is a regular file, a\n           file-like object is returned. If `member' is a link, a file-like\n           object is constructed from the link's target. If `member' is none of\n           the above, None is returned.\n           The file-like object is read-only and provides the following\n           methods: read(), readline(), readlines(), seek() and tell()\n        "));
      var1.setlocal("extractfile", var6);
      var3 = null;
      var1.setline(2134);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _extract_member$124, PyString.fromInterned("Extract the TarInfo object tarinfo to a physical\n           file called targetpath.\n        "));
      var1.setlocal("_extract_member", var6);
      var3 = null;
      var1.setline(2181);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, makedir$125, PyString.fromInterned("Make a directory called targetpath.\n        "));
      var1.setlocal("makedir", var6);
      var3 = null;
      var1.setline(2192);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, makefile$126, PyString.fromInterned("Make a file called targetpath.\n        "));
      var1.setlocal("makefile", var6);
      var3 = null;
      var1.setline(2202);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, makeunknown$127, PyString.fromInterned("Make a file from a TarInfo object with an unknown type\n           at targetpath.\n        "));
      var1.setlocal("makeunknown", var6);
      var3 = null;
      var1.setline(2210);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, makefifo$128, PyString.fromInterned("Make a fifo called targetpath.\n        "));
      var1.setlocal("makefifo", var6);
      var3 = null;
      var1.setline(2218);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, makedev$129, PyString.fromInterned("Make a character or block device called targetpath.\n        "));
      var1.setlocal("makedev", var6);
      var3 = null;
      var1.setline(2233);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, makelink$130, PyString.fromInterned("Make a (symbolic) link called targetpath. If it cannot be created\n          (platform limitation), we try to make a copy of the referenced file\n          instead of a link.\n        "));
      var1.setlocal("makelink", var6);
      var3 = null;
      var1.setline(2258);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, chown$131, PyString.fromInterned("Set owner of targetpath according to tarinfo.\n        "));
      var1.setlocal("chown", var6);
      var3 = null;
      var1.setline(2280);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, chmod$132, PyString.fromInterned("Set file permissions of targetpath according to tarinfo.\n        "));
      var1.setlocal("chmod", var6);
      var3 = null;
      var1.setline(2289);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, utime$133, PyString.fromInterned("Set modification time of targetpath according to tarinfo.\n        "));
      var1.setlocal("utime", var6);
      var3 = null;
      var1.setline(2300);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, next$134, PyString.fromInterned("Return the next member of the archive as a TarInfo object, when\n           TarFile is opened for reading. Return None if there is no more\n           available.\n        "));
      var1.setlocal("next", var6);
      var3 = null;
      var1.setline(2349);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      var6 = new PyFunction(var1.f_globals, var5, _getmember$135, PyString.fromInterned("Find an archive member by name from bottom to top.\n           If tarinfo is given, it is used as the starting point.\n        "));
      var1.setlocal("_getmember", var6);
      var3 = null;
      var1.setline(2372);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _load$136, PyString.fromInterned("Read through the entire archive file and look for readable\n           members.\n        "));
      var1.setlocal("_load", var6);
      var3 = null;
      var1.setline(2382);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, _check$137, PyString.fromInterned("Check if TarFile is still open, and if the operation's mode\n           corresponds to TarFile's mode.\n        "));
      var1.setlocal("_check", var6);
      var3 = null;
      var1.setline(2391);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _find_link_target$138, PyString.fromInterned("Find the target member of a symlink or hardlink member in the\n           archive.\n        "));
      var1.setlocal("_find_link_target", var6);
      var3 = null;
      var1.setline(2410);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __iter__$139, PyString.fromInterned("Provide an iterator object.\n        "));
      var1.setlocal("__iter__", var6);
      var3 = null;
      var1.setline(2418);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _dbg$140, PyString.fromInterned("Write debugging output to sys.stderr.\n        "));
      var1.setlocal("_dbg", var6);
      var3 = null;
      var1.setline(2424);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __enter__$141, (PyObject)null);
      var1.setlocal("__enter__", var6);
      var3 = null;
      var1.setline(2428);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __exit__$142, (PyObject)null);
      var1.setlocal("__exit__", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$106(PyFrame var1, ThreadState var2) {
      var1.setline(1511);
      PyString.fromInterned("Open an (uncompressed) tar archive `name'. `mode' is either 'r' to\n           read from an existing archive, 'a' to append data to an existing\n           file or 'w' to create a new file overwriting an existing one. `mode'\n           defaults to 'r'.\n           If `fileobj' is given, it is used for reading or writing data. If it\n           can be determined, `mode' is overridden by `fileobj's mode.\n           `fileobj' is not closed, when TarFile is closed.\n        ");
      var1.setline(1512);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._notin(PyString.fromInterned("raw"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1513);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mode must be 'r', 'a' or 'w'")));
      } else {
         var1.setline(1514);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("mode", var3);
         var3 = null;
         var1.setline(1515);
         var3 = (new PyDictionary(new PyObject[]{PyString.fromInterned("r"), PyString.fromInterned("rb"), PyString.fromInterned("a"), PyString.fromInterned("r+b"), PyString.fromInterned("w"), PyString.fromInterned("wb")})).__getitem__(var1.getlocal(2));
         var1.getlocal(0).__setattr__("_mode", var3);
         var3 = null;
         var1.setline(1517);
         PyString var8;
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(1518);
            var3 = var1.getlocal(0).__getattr__("mode");
            var10000 = var3._eq(PyString.fromInterned("a"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(1520);
               var8 = PyString.fromInterned("w");
               var1.getlocal(0).__setattr__((String)"mode", var8);
               var3 = null;
               var1.setline(1521);
               var8 = PyString.fromInterned("wb");
               var1.getlocal(0).__setattr__((String)"_mode", var8);
               var3 = null;
            }

            var1.setline(1522);
            var3 = var1.getglobal("bltn_open").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_mode"));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(1523);
            var3 = var1.getglobal("False");
            var1.getlocal(0).__setattr__("_extfileobj", var3);
            var3 = null;
         } else {
            var1.setline(1525);
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("name"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(1526);
               var3 = var1.getlocal(3).__getattr__("name");
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(1527);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("mode")).__nonzero__()) {
               var1.setline(1528);
               var3 = var1.getlocal(3).__getattr__("mode");
               var1.getlocal(0).__setattr__("_mode", var3);
               var3 = null;
            }

            var1.setline(1529);
            var3 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("_extfileobj", var3);
            var3 = null;
         }

         var1.setline(1530);
         var1.setline(1530);
         var3 = var1.getlocal(1).__nonzero__() ? var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1)) : var1.getglobal("None");
         var1.getlocal(0).__setattr__("name", var3);
         var3 = null;
         var1.setline(1531);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("fileobj", var3);
         var3 = null;
         var1.setline(1534);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1535);
            var3 = var1.getlocal(4);
            var1.getlocal(0).__setattr__("format", var3);
            var3 = null;
         }

         var1.setline(1536);
         var3 = var1.getlocal(5);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1537);
            var3 = var1.getlocal(5);
            var1.getlocal(0).__setattr__("tarinfo", var3);
            var3 = null;
         }

         var1.setline(1538);
         var3 = var1.getlocal(6);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1539);
            var3 = var1.getlocal(6);
            var1.getlocal(0).__setattr__("dereference", var3);
            var3 = null;
         }

         var1.setline(1540);
         var3 = var1.getlocal(7);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1541);
            var3 = var1.getlocal(7);
            var1.getlocal(0).__setattr__("ignore_zeros", var3);
            var3 = null;
         }

         var1.setline(1542);
         var3 = var1.getlocal(8);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1543);
            var3 = var1.getlocal(8);
            var1.getlocal(0).__setattr__("encoding", var3);
            var3 = null;
         }

         var1.setline(1545);
         var3 = var1.getlocal(9);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1546);
            var3 = var1.getlocal(9);
            var1.getlocal(0).__setattr__("errors", var3);
            var3 = null;
         } else {
            var1.setline(1547);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(PyString.fromInterned("r"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1548);
               var8 = PyString.fromInterned("utf-8");
               var1.getlocal(0).__setattr__((String)"errors", var8);
               var3 = null;
            } else {
               var1.setline(1550);
               var8 = PyString.fromInterned("strict");
               var1.getlocal(0).__setattr__((String)"errors", var8);
               var3 = null;
            }
         }

         var1.setline(1552);
         var3 = var1.getlocal(10);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("format");
            var10000 = var3._eq(var1.getglobal("PAX_FORMAT"));
            var3 = null;
         }

         PyDictionary var9;
         if (var10000.__nonzero__()) {
            var1.setline(1553);
            var3 = var1.getlocal(10);
            var1.getlocal(0).__setattr__("pax_headers", var3);
            var3 = null;
         } else {
            var1.setline(1555);
            var9 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"pax_headers", var9);
            var3 = null;
         }

         var1.setline(1557);
         var3 = var1.getlocal(11);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1558);
            var3 = var1.getlocal(11);
            var1.getlocal(0).__setattr__("debug", var3);
            var3 = null;
         }

         var1.setline(1559);
         var3 = var1.getlocal(12);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1560);
            var3 = var1.getlocal(12);
            var1.getlocal(0).__setattr__("errorlevel", var3);
            var3 = null;
         }

         var1.setline(1563);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("closed", var3);
         var3 = null;
         var1.setline(1564);
         PyList var10 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"members", var10);
         var3 = null;
         var1.setline(1565);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_loaded", var3);
         var3 = null;
         var1.setline(1566);
         var3 = var1.getlocal(0).__getattr__("fileobj").__getattr__("tell").__call__(var2);
         var1.getlocal(0).__setattr__("offset", var3);
         var3 = null;
         var1.setline(1568);
         var9 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"inodes", var9);
         var3 = null;

         PyObject var4;
         try {
            var1.setline(1572);
            var3 = var1.getlocal(0).__getattr__("mode");
            var10000 = var3._eq(PyString.fromInterned("r"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1573);
               var3 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("firstmember", var3);
               var3 = null;
               var1.setline(1574);
               var3 = var1.getlocal(0).__getattr__("next").__call__(var2);
               var1.getlocal(0).__setattr__("firstmember", var3);
               var3 = null;
            }

            var1.setline(1576);
            var3 = var1.getlocal(0).__getattr__("mode");
            var10000 = var3._eq(PyString.fromInterned("a"));
            var3 = null;
            if (var10000.__nonzero__()) {
               while(true) {
                  var1.setline(1579);
                  if (!var1.getglobal("True").__nonzero__()) {
                     break;
                  }

                  var1.setline(1580);
                  var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("offset"));

                  try {
                     var1.setline(1582);
                     var3 = var1.getlocal(0).__getattr__("tarinfo").__getattr__("fromtarfile").__call__(var2, var1.getlocal(0));
                     var1.setlocal(5, var3);
                     var3 = null;
                     var1.setline(1583);
                     var1.getlocal(0).__getattr__("members").__getattr__("append").__call__(var2, var1.getlocal(5));
                  } catch (Throwable var6) {
                     PyException var11 = Py.setException(var6, var1);
                     if (var11.match(var1.getglobal("EOFHeaderError"))) {
                        var1.setline(1585);
                        var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("offset"));
                        break;
                     }

                     if (var11.match(var1.getglobal("HeaderError"))) {
                        var4 = var11.value;
                        var1.setlocal(13, var4);
                        var4 = null;
                        var1.setline(1588);
                        throw Py.makeException(var1.getglobal("ReadError").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(13))));
                     }

                     throw var11;
                  }
               }
            }

            var1.setline(1590);
            var3 = var1.getlocal(0).__getattr__("mode");
            var10000 = var3._in(PyString.fromInterned("aw"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1591);
               var3 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("_loaded", var3);
               var3 = null;
               var1.setline(1593);
               if (var1.getlocal(0).__getattr__("pax_headers").__nonzero__()) {
                  var1.setline(1594);
                  var3 = var1.getlocal(0).__getattr__("tarinfo").__getattr__("create_pax_global_header").__call__(var2, var1.getlocal(0).__getattr__("pax_headers").__getattr__("copy").__call__(var2));
                  var1.setlocal(14, var3);
                  var3 = null;
                  var1.setline(1595);
                  var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getlocal(14));
                  var1.setline(1596);
                  var10000 = var1.getlocal(0);
                  String var12 = "offset";
                  var4 = var10000;
                  PyObject var5 = var4.__getattr__(var12);
                  var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(14)));
                  var4.__setattr__(var12, var5);
               }
            }
         } catch (Throwable var7) {
            Py.setException(var7, var1);
            var1.setline(1598);
            if (var1.getlocal(0).__getattr__("_extfileobj").__not__().__nonzero__()) {
               var1.setline(1599);
               var1.getlocal(0).__getattr__("fileobj").__getattr__("close").__call__(var2);
            }

            var1.setline(1600);
            var4 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("closed", var4);
            var4 = null;
            var1.setline(1601);
            throw Py.makeException();
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _getposix$107(PyFrame var1, ThreadState var2) {
      var1.setline(1604);
      PyObject var3 = var1.getlocal(0).__getattr__("format");
      PyObject var10000 = var3._eq(var1.getglobal("USTAR_FORMAT"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _setposix$108(PyFrame var1, ThreadState var2) {
      var1.setline(1606);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1607);
      var1.getlocal(2).__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("use the format attribute instead"), (PyObject)var1.getglobal("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(1609);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1610);
         var3 = var1.getglobal("USTAR_FORMAT");
         var1.getlocal(0).__setattr__("format", var3);
         var3 = null;
      } else {
         var1.setline(1612);
         var3 = var1.getglobal("GNU_FORMAT");
         var1.getlocal(0).__setattr__("format", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject open$109(PyFrame var1, ThreadState var2) {
      var1.setline(1648);
      PyString.fromInterned("Open a tar archive for reading, writing or appending. Return\n           an appropriate TarFile class.\n\n           mode:\n           'r' or 'r:*' open for reading with transparent compression\n           'r:'         open for reading exclusively uncompressed\n           'r:gz'       open for reading with gzip compression\n           'r:bz2'      open for reading with bzip2 compression\n           'a' or 'a:'  open for appending, creating the file if necessary\n           'w' or 'w:'  open for writing without compression\n           'w:gz'       open for writing with gzip compression\n           'w:bz2'      open for writing with bzip2 compression\n\n           'r|*'        open a stream of tar blocks with transparent compression\n           'r|'         open an uncompressed stream of tar blocks for reading\n           'r|gz'       open a gzip compressed stream of tar blocks\n           'r|bz2'      open a bzip2 compressed stream of tar blocks\n           'w|'         open an uncompressed stream for writing\n           'w|gz'       open a gzip compressed stream for writing\n           'w|bz2'      open a bzip2 compressed stream for writing\n        ");
      var1.setline(1650);
      PyObject var10000 = var1.getlocal(1).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1651);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("nothing to open")));
      } else {
         var1.setline(1653);
         PyObject var3 = var1.getlocal(2);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("r"), PyString.fromInterned("r:*")}));
         var3 = null;
         PyObject var5;
         PyException var6;
         if (!var10000.__nonzero__()) {
            var1.setline(1667);
            PyString var9 = PyString.fromInterned(":");
            var10000 = var9._in(var1.getlocal(2));
            var3 = null;
            String[] var10;
            PyObject[] var11;
            PyObject[] var12;
            PyObject var14;
            Object var16;
            Object var17;
            if (var10000.__nonzero__()) {
               var1.setline(1668);
               var3 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1));
               var11 = Py.unpackSequence(var3, 2);
               var14 = var11[0];
               var1.setlocal(10, var14);
               var6 = null;
               var14 = var11[1];
               var1.setlocal(6, var14);
               var6 = null;
               var3 = null;
               var1.setline(1669);
               var17 = var1.getlocal(10);
               if (!((PyObject)var17).__nonzero__()) {
                  var17 = PyString.fromInterned("r");
               }

               var16 = var17;
               var1.setlocal(10, (PyObject)var16);
               var3 = null;
               var1.setline(1670);
               var17 = var1.getlocal(6);
               if (!((PyObject)var17).__nonzero__()) {
                  var17 = PyString.fromInterned("tar");
               }

               var16 = var17;
               var1.setlocal(6, (PyObject)var16);
               var3 = null;
               var1.setline(1674);
               var3 = var1.getlocal(6);
               var10000 = var3._in(var1.getlocal(0).__getattr__("OPEN_METH"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1675);
                  var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("OPEN_METH").__getitem__(var1.getlocal(6)));
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(1678);
                  var10000 = var1.getlocal(7);
                  var12 = new PyObject[]{var1.getlocal(1), var1.getlocal(10), var1.getlocal(3)};
                  var10 = new String[0];
                  var10000 = var10000._callextra(var12, var10, (PyObject)null, var1.getlocal(5));
                  var3 = null;
                  var5 = var10000;
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(1677);
                  throw Py.makeException(var1.getglobal("CompressionError").__call__(var2, PyString.fromInterned("unknown compression type %r")._mod(var1.getlocal(6))));
               }
            } else {
               var1.setline(1680);
               var9 = PyString.fromInterned("|");
               var10000 = var9._in(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1681);
                  var3 = var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("|"), (PyObject)Py.newInteger(1));
                  var11 = Py.unpackSequence(var3, 2);
                  var14 = var11[0];
                  var1.setlocal(10, var14);
                  var6 = null;
                  var14 = var11[1];
                  var1.setlocal(6, var14);
                  var6 = null;
                  var3 = null;
                  var1.setline(1682);
                  var17 = var1.getlocal(10);
                  if (!((PyObject)var17).__nonzero__()) {
                     var17 = PyString.fromInterned("r");
                  }

                  var16 = var17;
                  var1.setlocal(10, (PyObject)var16);
                  var3 = null;
                  var1.setline(1683);
                  var17 = var1.getlocal(6);
                  if (!((PyObject)var17).__nonzero__()) {
                     var17 = PyString.fromInterned("tar");
                  }

                  var16 = var17;
                  var1.setlocal(6, (PyObject)var16);
                  var3 = null;
                  var1.setline(1685);
                  var3 = var1.getlocal(10);
                  var10000 = var3._notin(PyString.fromInterned("rw"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1686);
                     throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mode must be 'r' or 'w'")));
                  } else {
                     var1.setline(1688);
                     var10000 = var1.getlocal(0);
                     var12 = new PyObject[]{var1.getlocal(1), var1.getlocal(10), null};
                     PyObject var10001 = var1.getglobal("_Stream");
                     var11 = new PyObject[]{var1.getlocal(1), var1.getlocal(10), var1.getlocal(6), var1.getlocal(3), var1.getlocal(4)};
                     var12[2] = var10001.__call__(var2, var11);
                     var10 = new String[0];
                     var10000 = var10000._callextra(var12, var10, (PyObject)null, var1.getlocal(5));
                     var3 = null;
                     var3 = var10000;
                     var1.setlocal(11, var3);
                     var3 = null;
                     var1.setline(1691);
                     var3 = var1.getglobal("False");
                     var1.getlocal(11).__setattr__("_extfileobj", var3);
                     var3 = null;
                     var1.setline(1692);
                     var5 = var1.getlocal(11);
                     var1.f_lasti = -1;
                     return var5;
                  }
               } else {
                  var1.setline(1694);
                  var3 = var1.getlocal(2);
                  var10000 = var3._in(PyString.fromInterned("aw"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1695);
                     var10000 = var1.getlocal(0).__getattr__("taropen");
                     var12 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
                     var10 = new String[0];
                     var10000 = var10000._callextra(var12, var10, (PyObject)null, var1.getlocal(5));
                     var3 = null;
                     var5 = var10000;
                     var1.f_lasti = -1;
                     return var5;
                  } else {
                     var1.setline(1697);
                     throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("undiscernible mode")));
                  }
               }
            }
         } else {
            var1.setline(1655);
            var3 = var1.getlocal(0).__getattr__("OPEN_METH").__iter__();

            while(true) {
               var1.setline(1655);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1665);
                  throw Py.makeException(var1.getglobal("ReadError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("file could not be opened successfully")));
               }

               var1.setlocal(6, var4);
               var1.setline(1656);
               var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("OPEN_METH").__getitem__(var1.getlocal(6)));
               var1.setlocal(7, var5);
               var5 = null;
               var1.setline(1657);
               var5 = var1.getlocal(3);
               var10000 = var5._isnot(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1658);
                  var5 = var1.getlocal(3).__getattr__("tell").__call__(var2);
                  var1.setlocal(8, var5);
                  var5 = null;
               }

               try {
                  var1.setline(1660);
                  var10000 = var1.getlocal(7);
                  PyObject[] var15 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("r"), var1.getlocal(3)};
                  String[] var13 = new String[0];
                  var10000 = var10000._callextra(var15, var13, (PyObject)null, var1.getlocal(5));
                  var5 = null;
                  var5 = var10000;
                  var1.f_lasti = -1;
                  return var5;
               } catch (Throwable var8) {
                  var6 = Py.setException(var8, var1);
                  if (!var6.match(new PyTuple(new PyObject[]{var1.getglobal("ReadError"), var1.getglobal("CompressionError")}))) {
                     throw var6;
                  }

                  PyObject var7 = var6.value;
                  var1.setlocal(9, var7);
                  var7 = null;
                  var1.setline(1662);
                  var7 = var1.getlocal(3);
                  var10000 = var7._isnot(var1.getglobal("None"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1663);
                     var1.getlocal(3).__getattr__("seek").__call__(var2, var1.getlocal(8));
                  }
               }
            }
         }
      }
   }

   public PyObject taropen$110(PyFrame var1, ThreadState var2) {
      var1.setline(1702);
      PyString.fromInterned("Open uncompressed tar archive name for reading or writing.\n        ");
      var1.setline(1703);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._notin(PyString.fromInterned("raw"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1704);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mode must be 'r', 'a' or 'w'")));
      } else {
         var1.setline(1705);
         var10000 = var1.getlocal(0);
         PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var5, var4, (PyObject)null, var1.getlocal(4));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject gzopen$111(PyFrame var1, ThreadState var2) {
      var1.setline(1711);
      PyString.fromInterned("Open gzip compressed tar archive name for reading or writing.\n           Appending is not allowed.\n        ");
      var1.setline(1712);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._notin(PyString.fromInterned("rw"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1713);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mode must be 'r' or 'w'")));
      } else {
         PyException var7;
         try {
            var1.setline(1716);
            var3 = imp.importOne("gzip", var1, -1);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(1717);
            var1.getlocal(6).__getattr__("GzipFile");
         } catch (Throwable var6) {
            var7 = Py.setException(var6, var1);
            if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("ImportError"), var1.getglobal("AttributeError")}))) {
               var1.setline(1719);
               throw Py.makeException(var1.getglobal("CompressionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gzip module is not available")));
            }

            throw var7;
         }

         var1.setline(1721);
         var3 = var1.getlocal(6).__getattr__("GzipFile").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(4), var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;

         try {
            var1.setline(1724);
            var10000 = var1.getlocal(0).__getattr__("taropen");
            PyObject[] var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
            String[] var4 = new String[0];
            var10000 = var10000._callextra(var8, var4, (PyObject)null, var1.getlocal(5));
            var3 = null;
            var3 = var10000;
            var1.setlocal(7, var3);
            var3 = null;
         } catch (Throwable var5) {
            var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("IOError"))) {
               var1.setline(1726);
               throw Py.makeException(var1.getglobal("ReadError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not a gzip file")));
            }

            throw var7;
         }

         var1.setline(1727);
         var3 = var1.getglobal("False");
         var1.getlocal(7).__setattr__("_extfileobj", var3);
         var3 = null;
         var1.setline(1728);
         var3 = var1.getlocal(7);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject bz2open$112(PyFrame var1, ThreadState var2) {
      var1.setline(1734);
      PyString.fromInterned("Open bzip2 compressed tar archive name for reading or writing.\n           Appending is not allowed.\n        ");
      var1.setline(1735);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._notin(PyString.fromInterned("rw"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1736);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mode must be 'r' or 'w'.")));
      } else {
         PyException var7;
         try {
            var1.setline(1739);
            var3 = imp.importOne("bz2", var1, -1);
            var1.setlocal(6, var3);
            var3 = null;
         } catch (Throwable var6) {
            var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("ImportError"))) {
               var1.setline(1741);
               throw Py.makeException(var1.getglobal("CompressionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bz2 module is not available")));
            }

            throw var7;
         }

         var1.setline(1743);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         String[] var4;
         PyObject[] var8;
         if (var10000.__nonzero__()) {
            var1.setline(1744);
            var3 = var1.getglobal("_BZ2Proxy").__call__(var2, var1.getlocal(3), var1.getlocal(2));
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(1746);
            var10000 = var1.getlocal(6).__getattr__("BZ2File");
            var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(4)};
            var4 = new String[]{"compresslevel"};
            var10000 = var10000.__call__(var2, var8, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(3, var3);
            var3 = null;
         }

         try {
            var1.setline(1749);
            var10000 = var1.getlocal(0).__getattr__("taropen");
            var8 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
            var4 = new String[0];
            var10000 = var10000._callextra(var8, var4, (PyObject)null, var1.getlocal(5));
            var3 = null;
            var3 = var10000;
            var1.setlocal(7, var3);
            var3 = null;
         } catch (Throwable var5) {
            var7 = Py.setException(var5, var1);
            if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("IOError"), var1.getglobal("EOFError")}))) {
               var1.setline(1751);
               throw Py.makeException(var1.getglobal("ReadError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not a bzip2 file")));
            }

            throw var7;
         }

         var1.setline(1752);
         var3 = var1.getglobal("False");
         var1.getlocal(7).__setattr__("_extfileobj", var3);
         var3 = null;
         var1.setline(1753);
         var3 = var1.getlocal(7);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject close$113(PyFrame var1, ThreadState var2) {
      var1.setline(1768);
      PyString.fromInterned("Close the TarFile. In write-mode, two finishing zero blocks are\n           appended to the archive.\n        ");
      var1.setline(1769);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(1770);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1772);
         PyObject var3 = var1.getlocal(0).__getattr__("mode");
         PyObject var10000 = var3._in(PyString.fromInterned("aw"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1773);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getglobal("NUL")._mul(var1.getglobal("BLOCKSIZE")._mul(Py.newInteger(2))));
            var1.setline(1774);
            var10000 = var1.getlocal(0);
            String var6 = "offset";
            PyObject var4 = var10000;
            PyObject var5 = var4.__getattr__(var6);
            var5 = var5._iadd(var1.getglobal("BLOCKSIZE")._mul(Py.newInteger(2)));
            var4.__setattr__(var6, var5);
            var1.setline(1777);
            var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(0).__getattr__("offset"), var1.getglobal("RECORDSIZE"));
            PyObject[] var7 = Py.unpackSequence(var3, 2);
            var5 = var7[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var7[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;
            var1.setline(1778);
            var3 = var1.getlocal(2);
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1779);
               var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getglobal("NUL")._mul(var1.getglobal("RECORDSIZE")._sub(var1.getlocal(2))));
            }
         }

         var1.setline(1781);
         if (var1.getlocal(0).__getattr__("_extfileobj").__not__().__nonzero__()) {
            var1.setline(1782);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("close").__call__(var2);
         }

         var1.setline(1783);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("closed", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject getmember$114(PyFrame var1, ThreadState var2) {
      var1.setline(1790);
      PyString.fromInterned("Return a TarInfo object for member `name'. If `name' can not be\n           found in the archive, KeyError is raised. If a member occurs more\n           than once in the archive, its last occurrence is assumed to be the\n           most up-to-date version.\n        ");
      var1.setline(1791);
      PyObject var3 = var1.getlocal(0).__getattr__("_getmember").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1792);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1793);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("filename %r not found")._mod(var1.getlocal(1))));
      } else {
         var1.setline(1794);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getmembers$115(PyFrame var1, ThreadState var2) {
      var1.setline(1799);
      PyString.fromInterned("Return the members of the archive as a list of TarInfo objects. The\n           list has the same order as the members in the archive.\n        ");
      var1.setline(1800);
      var1.getlocal(0).__getattr__("_check").__call__(var2);
      var1.setline(1801);
      if (var1.getlocal(0).__getattr__("_loaded").__not__().__nonzero__()) {
         var1.setline(1802);
         var1.getlocal(0).__getattr__("_load").__call__(var2);
      }

      var1.setline(1804);
      PyObject var3 = var1.getlocal(0).__getattr__("members");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getnames$116(PyFrame var1, ThreadState var2) {
      var1.setline(1809);
      PyString.fromInterned("Return the members of the archive as a list of their names. It has\n           the same order as the list returned by getmembers().\n        ");
      var1.setline(1810);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1810);
      var3 = var1.getlocal(0).__getattr__("getmembers").__call__(var2).__iter__();

      while(true) {
         var1.setline(1810);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1810);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(1810);
         var1.getlocal(1).__call__(var2, var1.getlocal(2).__getattr__("name"));
      }
   }

   public PyObject gettarinfo$117(PyFrame var1, ThreadState var2) {
      var1.setline(1818);
      PyString.fromInterned("Create a TarInfo object for either the file `name' or the file\n           object `fileobj' (using os.fstat on its file descriptor). You can\n           modify some of the TarInfo's attributes before you add it using\n           addfile(). If given, `arcname' specifies an alternative name for the\n           file in the archive.\n        ");
      var1.setline(1819);
      var1.getlocal(0).__getattr__("_check").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aw"));
      var1.setline(1823);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1824);
         var3 = var1.getlocal(3).__getattr__("name");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1829);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1830);
         var3 = var1.getlocal(1);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1831);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1832);
      var3 = var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("sep"), (PyObject)PyString.fromInterned("/"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1833);
      var3 = var1.getlocal(2).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1837);
      var3 = var1.getlocal(0).__getattr__("tarinfo").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1838);
      var3 = var1.getlocal(0);
      var1.getlocal(5).__setattr__("tarfile", var3);
      var3 = null;
      var1.setline(1842);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1843);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("lstat"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("dereference").__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1844);
            var3 = var1.getglobal("os").__getattr__("lstat").__call__(var2, var1.getlocal(1));
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(1846);
            var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1));
            var1.setlocal(6, var3);
            var3 = null;
         }
      } else {
         var1.setline(1848);
         var3 = var1.getglobal("os").__getattr__("fstat").__call__(var2, var1.getlocal(3).__getattr__("fileno").__call__(var2));
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(1849);
      PyString var11 = PyString.fromInterned("");
      var1.setlocal(7, var11);
      var3 = null;
      var1.setline(1851);
      var3 = var1.getlocal(6).__getattr__("st_mode");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1852);
      if (var1.getglobal("stat").__getattr__("S_ISREG").__call__(var2, var1.getlocal(8)).__nonzero__()) {
         var1.setline(1853);
         PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(6).__getattr__("st_ino"), var1.getlocal(6).__getattr__("st_dev")});
         var1.setlocal(9, var12);
         var3 = null;
         var1.setline(1854);
         var10000 = var1.getlocal(0).__getattr__("dereference").__not__();
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(6).__getattr__("st_nlink");
            var10000 = var3._gt(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(9);
               var10000 = var3._in(var1.getlocal(0).__getattr__("inodes"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(2);
                  var10000 = var3._ne(var1.getlocal(0).__getattr__("inodes").__getitem__(var1.getlocal(9)));
                  var3 = null;
               }
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(1858);
            var3 = var1.getglobal("LNKTYPE");
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(1859);
            var3 = var1.getlocal(0).__getattr__("inodes").__getitem__(var1.getlocal(9));
            var1.setlocal(7, var3);
            var3 = null;
         } else {
            var1.setline(1863);
            var3 = var1.getglobal("REGTYPE");
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(1864);
            if (var1.getlocal(9).__getitem__(Py.newInteger(0)).__nonzero__()) {
               var1.setline(1865);
               var3 = var1.getlocal(2);
               var1.getlocal(0).__getattr__("inodes").__setitem__(var1.getlocal(9), var3);
               var3 = null;
            }
         }
      } else {
         var1.setline(1866);
         if (var1.getglobal("stat").__getattr__("S_ISDIR").__call__(var2, var1.getlocal(8)).__nonzero__()) {
            var1.setline(1867);
            var3 = var1.getglobal("DIRTYPE");
            var1.setlocal(10, var3);
            var3 = null;
         } else {
            var1.setline(1868);
            if (var1.getglobal("stat").__getattr__("S_ISFIFO").__call__(var2, var1.getlocal(8)).__nonzero__()) {
               var1.setline(1869);
               var3 = var1.getglobal("FIFOTYPE");
               var1.setlocal(10, var3);
               var3 = null;
            } else {
               var1.setline(1870);
               if (var1.getglobal("stat").__getattr__("S_ISLNK").__call__(var2, var1.getlocal(8)).__nonzero__()) {
                  var1.setline(1871);
                  var3 = var1.getglobal("SYMTYPE");
                  var1.setlocal(10, var3);
                  var3 = null;
                  var1.setline(1872);
                  var3 = var1.getglobal("os").__getattr__("readlink").__call__(var2, var1.getlocal(1));
                  var1.setlocal(7, var3);
                  var3 = null;
               } else {
                  var1.setline(1873);
                  if (var1.getglobal("stat").__getattr__("S_ISCHR").__call__(var2, var1.getlocal(8)).__nonzero__()) {
                     var1.setline(1874);
                     var3 = var1.getglobal("CHRTYPE");
                     var1.setlocal(10, var3);
                     var3 = null;
                  } else {
                     var1.setline(1875);
                     if (!var1.getglobal("stat").__getattr__("S_ISBLK").__call__(var2, var1.getlocal(8)).__nonzero__()) {
                        var1.setline(1878);
                        var3 = var1.getglobal("None");
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setline(1876);
                     var3 = var1.getglobal("BLKTYPE");
                     var1.setlocal(10, var3);
                     var3 = null;
                  }
               }
            }
         }
      }

      var1.setline(1882);
      PyObject var8 = var1.getlocal(2);
      var1.getlocal(5).__setattr__("name", var8);
      var4 = null;
      var1.setline(1883);
      var8 = var1.getlocal(8);
      var1.getlocal(5).__setattr__("mode", var8);
      var4 = null;
      var1.setline(1884);
      var8 = var1.getlocal(6).__getattr__("st_uid");
      var1.getlocal(5).__setattr__("uid", var8);
      var4 = null;
      var1.setline(1885);
      var8 = var1.getlocal(6).__getattr__("st_gid");
      var1.getlocal(5).__setattr__("gid", var8);
      var4 = null;
      var1.setline(1886);
      var8 = var1.getlocal(10);
      var10000 = var8._eq(var1.getglobal("REGTYPE"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1887);
         var8 = var1.getlocal(6).__getattr__("st_size");
         var1.getlocal(5).__setattr__("size", var8);
         var4 = null;
      } else {
         var1.setline(1889);
         PyLong var9 = Py.newLong("0");
         var1.getlocal(5).__setattr__((String)"size", var9);
         var4 = null;
      }

      var1.setline(1890);
      var8 = var1.getlocal(6).__getattr__("st_mtime");
      var1.getlocal(5).__setattr__("mtime", var8);
      var4 = null;
      var1.setline(1891);
      var8 = var1.getlocal(10);
      var1.getlocal(5).__setattr__("type", var8);
      var4 = null;
      var1.setline(1892);
      var8 = var1.getlocal(7);
      var1.getlocal(5).__setattr__("linkname", var8);
      var4 = null;
      var1.setline(1893);
      PyException var10;
      if (var1.getglobal("pwd").__nonzero__()) {
         try {
            var1.setline(1895);
            var8 = var1.getglobal("pwd").__getattr__("getpwuid").__call__(var2, var1.getlocal(5).__getattr__("uid")).__getitem__(Py.newInteger(0));
            var1.getlocal(5).__setattr__("uname", var8);
            var4 = null;
         } catch (Throwable var7) {
            var10 = Py.setException(var7, var1);
            if (!var10.match(var1.getglobal("KeyError"))) {
               throw var10;
            }

            var1.setline(1897);
         }
      }

      var1.setline(1898);
      if (var1.getglobal("grp").__nonzero__()) {
         try {
            var1.setline(1900);
            var8 = var1.getglobal("grp").__getattr__("getgrgid").__call__(var2, var1.getlocal(5).__getattr__("gid")).__getitem__(Py.newInteger(0));
            var1.getlocal(5).__setattr__("gname", var8);
            var4 = null;
         } catch (Throwable var6) {
            var10 = Py.setException(var6, var1);
            if (!var10.match(var1.getglobal("KeyError"))) {
               throw var10;
            }

            var1.setline(1902);
         }
      }

      var1.setline(1904);
      var8 = var1.getlocal(10);
      var10000 = var8._in(new PyTuple(new PyObject[]{var1.getglobal("CHRTYPE"), var1.getglobal("BLKTYPE")}));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1905);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("major"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("minor"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(1906);
            var8 = var1.getglobal("os").__getattr__("major").__call__(var2, var1.getlocal(6).__getattr__("st_rdev"));
            var1.getlocal(5).__setattr__("devmajor", var8);
            var4 = null;
            var1.setline(1907);
            var8 = var1.getglobal("os").__getattr__("minor").__call__(var2, var1.getlocal(6).__getattr__("st_rdev"));
            var1.getlocal(5).__setattr__("devminor", var8);
            var4 = null;
         }
      }

      var1.setline(1908);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject list$118(PyFrame var1, ThreadState var2) {
      var1.setline(1914);
      PyString.fromInterned("Print a table of contents to sys.stdout. If `verbose' is False, only\n           the names of the members are printed. If it is True, an `ls -l'-like\n           output is produced.\n        ");
      var1.setline(1915);
      var1.getlocal(0).__getattr__("_check").__call__(var2);
      var1.setline(1917);
      PyObject var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1917);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1918);
         PyObject var5;
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1919);
            Py.printComma(var1.getglobal("filemode").__call__(var2, var1.getlocal(2).__getattr__("mode")));
            var1.setline(1920);
            PyString var10000 = PyString.fromInterned("%s/%s");
            PyTuple var10001 = new PyTuple;
            PyObject[] var10003 = new PyObject[2];
            PyObject var10006 = var1.getlocal(2).__getattr__("uname");
            if (!var10006.__nonzero__()) {
               var10006 = var1.getlocal(2).__getattr__("uid");
            }

            var10003[0] = var10006;
            var10006 = var1.getlocal(2).__getattr__("gname");
            if (!var10006.__nonzero__()) {
               var10006 = var1.getlocal(2).__getattr__("gid");
            }

            var10003[1] = var10006;
            var10001.<init>(var10003);
            Py.printComma(var10000._mod(var10001));
            var1.setline(1922);
            var5 = var1.getlocal(2).__getattr__("ischr").__call__(var2);
            if (!var5.__nonzero__()) {
               var5 = var1.getlocal(2).__getattr__("isblk").__call__(var2);
            }

            if (var5.__nonzero__()) {
               var1.setline(1923);
               Py.printComma(PyString.fromInterned("%10s")._mod(PyString.fromInterned("%d,%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("devmajor"), var1.getlocal(2).__getattr__("devminor")}))));
            } else {
               var1.setline(1926);
               Py.printComma(PyString.fromInterned("%10d")._mod(var1.getlocal(2).__getattr__("size")));
            }

            var1.setline(1927);
            Py.printComma(PyString.fromInterned("%d-%02d-%02d %02d:%02d:%02d")._mod(var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(2).__getattr__("mtime")).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null)));
         }

         var1.setline(1930);
         var5 = var1.getlocal(2).__getattr__("name");
         var1.setline(1930);
         Py.printComma(var5._add(var1.getlocal(2).__getattr__("isdir").__call__(var2).__nonzero__() ? PyString.fromInterned("/") : PyString.fromInterned("")));
         var1.setline(1932);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1933);
            if (var1.getlocal(2).__getattr__("issym").__call__(var2).__nonzero__()) {
               var1.setline(1934);
               Py.printComma(PyString.fromInterned("->"));
               Py.printComma(var1.getlocal(2).__getattr__("linkname"));
            }

            var1.setline(1935);
            if (var1.getlocal(2).__getattr__("islnk").__call__(var2).__nonzero__()) {
               var1.setline(1936);
               Py.printComma(PyString.fromInterned("link to"));
               Py.printComma(var1.getlocal(2).__getattr__("linkname"));
            }
         }

         var1.setline(1937);
         Py.println();
      }
   }

   public PyObject add$119(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1949);
      PyString.fromInterned("Add the file `name' to the archive. `name' may be any type of file\n           (directory, fifo, symbolic link, etc.). If given, `arcname'\n           specifies an alternative name for the file in the archive.\n           Directories are added recursively by default. This can be avoided by\n           setting `recursive' to False. `exclude' is a function that should\n           return True for each filename to be excluded. `filter' is a function\n           that expects a TarInfo object argument and returns the changed\n           TarInfo object, if it returns None the TarInfo object will be\n           excluded from the archive.\n        ");
      var1.setline(1950);
      var1.getlocal(0).__getattr__("_check").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aw"));
      var1.setline(1952);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1953);
         var3 = var1.getlocal(1);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1956);
      var3 = var1.getlocal(4);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1957);
         var3 = imp.importOne("warnings", var1, -1);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1958);
         var1.getlocal(6).__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("use the filter argument instead"), (PyObject)var1.getglobal("DeprecationWarning"), (PyObject)Py.newInteger(2));
         var1.setline(1960);
         if (var1.getlocal(4).__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(1961);
            var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)PyString.fromInterned("tarfile: Excluded %r")._mod(var1.getlocal(1)));
            var1.setline(1962);
            var1.f_lasti = -1;
            return Py.None;
         }
      }

      var1.setline(1965);
      var3 = var1.getlocal(0).__getattr__("name");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(var1.getlocal(0).__getattr__("name"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1966);
         var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)PyString.fromInterned("tarfile: Skipped %r")._mod(var1.getlocal(1)));
         var1.setline(1967);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1969);
         var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getlocal(1));
         var1.setline(1972);
         var3 = var1.getlocal(0).__getattr__("gettarinfo").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1974);
         var3 = var1.getlocal(7);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1975);
            var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("tarfile: Unsupported type %r")._mod(var1.getlocal(1)));
            var1.setline(1976);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(1979);
            var3 = var1.getlocal(5);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1980);
               var3 = var1.getlocal(5).__call__(var2, var1.getlocal(7));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(1981);
               var3 = var1.getlocal(7);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1982);
                  var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)PyString.fromInterned("tarfile: Excluded %r")._mod(var1.getlocal(1)));
                  var1.setline(1983);
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }

            var1.setline(1986);
            PyObject var4;
            if (var1.getlocal(7).__getattr__("isreg").__call__(var2).__nonzero__()) {
               label69: {
                  ContextManager var7;
                  var4 = (var7 = ContextGuard.getManager(var1.getglobal("bltn_open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb")))).__enter__(var2);

                  try {
                     var1.setlocal(8, var4);
                     var1.setline(1988);
                     var1.getlocal(0).__getattr__("addfile").__call__(var2, var1.getlocal(7), var1.getlocal(8));
                  } catch (Throwable var6) {
                     if (var7.__exit__(var2, Py.setException(var6, var1))) {
                        break label69;
                     }

                     throw (Throwable)Py.makeException();
                  }

                  var7.__exit__(var2, (PyException)null);
               }
            } else {
               var1.setline(1990);
               if (var1.getlocal(7).__getattr__("isdir").__call__(var2).__nonzero__()) {
                  var1.setline(1991);
                  var1.getlocal(0).__getattr__("addfile").__call__(var2, var1.getlocal(7));
                  var1.setline(1992);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(1993);
                     var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(1)).__iter__();

                     while(true) {
                        var1.setline(1993);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var1.setlocal(8, var4);
                        var1.setline(1994);
                        var10000 = var1.getlocal(0).__getattr__("add");
                        PyObject[] var5 = new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(8)), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(8)), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
                        var10000.__call__(var2, var5);
                     }
                  }
               } else {
                  var1.setline(1998);
                  var1.getlocal(0).__getattr__("addfile").__call__(var2, var1.getlocal(7));
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject addfile$120(PyFrame var1, ThreadState var2) {
      var1.setline(2006);
      PyString.fromInterned("Add the TarInfo object `tarinfo' to the archive. If `fileobj' is\n           given, tarinfo.size bytes are read from it and added to the archive.\n           You can create TarInfo objects using gettarinfo().\n           On Windows platforms, `fileobj' should always be opened with mode\n           'rb' to avoid irritation about the file size.\n        ");
      var1.setline(2007);
      var1.getlocal(0).__getattr__("_check").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aw"));
      var1.setline(2009);
      PyObject var3 = var1.getglobal("copy").__getattr__("copy").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2011);
      var3 = var1.getlocal(1).__getattr__("tobuf").__call__(var2, var1.getlocal(0).__getattr__("format"), var1.getlocal(0).__getattr__("encoding"), var1.getlocal(0).__getattr__("errors"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2012);
      var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getlocal(3));
      var1.setline(2013);
      PyObject var10000 = var1.getlocal(0);
      String var6 = "offset";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var4.__setattr__(var6, var5);
      var1.setline(2016);
      var3 = var1.getlocal(2);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2017);
         var1.getglobal("copyfileobj").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("fileobj"), var1.getlocal(1).__getattr__("size"));
         var1.setline(2018);
         var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(1).__getattr__("size"), var1.getglobal("BLOCKSIZE"));
         PyObject[] var7 = Py.unpackSequence(var3, 2);
         var5 = var7[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(2019);
         var3 = var1.getlocal(5);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2020);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("write").__call__(var2, var1.getglobal("NUL")._mul(var1.getglobal("BLOCKSIZE")._sub(var1.getlocal(5))));
            var1.setline(2021);
            var3 = var1.getlocal(4);
            var3 = var3._iadd(Py.newInteger(1));
            var1.setlocal(4, var3);
         }

         var1.setline(2022);
         var10000 = var1.getlocal(0);
         var6 = "offset";
         var4 = var10000;
         var5 = var4.__getattr__(var6);
         var5 = var5._iadd(var1.getlocal(4)._mul(var1.getglobal("BLOCKSIZE")));
         var4.__setattr__(var6, var5);
      }

      var1.setline(2024);
      var1.getlocal(0).__getattr__("members").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject extractall$121(PyFrame var1, ThreadState var2) {
      var1.setline(2032);
      PyString.fromInterned("Extract all members from the archive to the current working\n           directory and set owner, modification time and permissions on\n           directories afterwards. `path' specifies a different directory\n           to extract to. `members' is optional and must be a subset of the\n           list returned by getmembers().\n        ");
      var1.setline(2033);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2035);
      PyObject var8 = var1.getlocal(2);
      PyObject var10000 = var8._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2036);
         var8 = var1.getlocal(0);
         var1.setlocal(2, var8);
         var3 = null;
      }

      var1.setline(2038);
      var8 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(2038);
         PyObject var4 = var8.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(2047);
            var10000 = var1.getlocal(3).__getattr__("sort");
            PyObject[] var10 = new PyObject[]{var1.getglobal("operator").__getattr__("attrgetter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("name"))};
            String[] var9 = new String[]{"key"};
            var10000.__call__(var2, var10, var9);
            var3 = null;
            var1.setline(2048);
            var1.getlocal(3).__getattr__("reverse").__call__(var2);
            var1.setline(2051);
            var8 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(2051);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(4, var4);
               var1.setline(2052);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(4).__getattr__("name"));
               var1.setlocal(5, var5);
               var5 = null;

               try {
                  var1.setline(2054);
                  var1.getlocal(0).__getattr__("chown").__call__(var2, var1.getlocal(4), var1.getlocal(5));
                  var1.setline(2055);
                  var1.getlocal(0).__getattr__("utime").__call__(var2, var1.getlocal(4), var1.getlocal(5));
                  var1.setline(2056);
                  var1.getlocal(0).__getattr__("chmod").__call__(var2, var1.getlocal(4), var1.getlocal(5));
               } catch (Throwable var7) {
                  PyException var12 = Py.setException(var7, var1);
                  if (!var12.match(var1.getglobal("ExtractError"))) {
                     throw var12;
                  }

                  PyObject var6 = var12.value;
                  var1.setlocal(6, var6);
                  var6 = null;
                  var1.setline(2058);
                  var6 = var1.getlocal(0).__getattr__("errorlevel");
                  var10000 = var6._gt(Py.newInteger(1));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2059);
                     throw Py.makeException();
                  }

                  var1.setline(2061);
                  var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("tarfile: %s")._mod(var1.getlocal(6)));
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(2039);
         if (var1.getlocal(4).__getattr__("isdir").__call__(var2).__nonzero__()) {
            var1.setline(2041);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
            var1.setline(2042);
            var5 = var1.getglobal("copy").__getattr__("copy").__call__(var2, var1.getlocal(4));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(2043);
            PyInteger var11 = Py.newInteger(448);
            var1.getlocal(4).__setattr__((String)"mode", var11);
            var5 = null;
         }

         var1.setline(2044);
         var1.getlocal(0).__getattr__("extract").__call__(var2, var1.getlocal(4), var1.getlocal(1));
      }
   }

   public PyObject extract$122(PyFrame var1, ThreadState var2) {
      var1.setline(2068);
      PyString.fromInterned("Extract a member from the archive to the current working directory,\n           using its full name. Its file information is extracted as accurately\n           as possible. `member' may be a filename or a TarInfo object. You can\n           specify a different directory using `path'.\n        ");
      var1.setline(2069);
      var1.getlocal(0).__getattr__("_check").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("r"));
      var1.setline(2071);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(2072);
         var3 = var1.getlocal(0).__getattr__("getmember").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(2074);
         var3 = var1.getlocal(1);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(2077);
      if (var1.getlocal(3).__getattr__("islnk").__call__(var2).__nonzero__()) {
         var1.setline(2078);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(3).__getattr__("linkname"));
         var1.getlocal(3).__setattr__("_link_target", var3);
         var3 = null;
      }

      try {
         var1.setline(2081);
         var1.getlocal(0).__getattr__("_extract_member").__call__(var2, var1.getlocal(3), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(3).__getattr__("name")));
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         PyObject var10000;
         PyObject var4;
         if (var6.match(var1.getglobal("EnvironmentError"))) {
            var4 = var6.value;
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(2083);
            var4 = var1.getlocal(0).__getattr__("errorlevel");
            var10000 = var4._gt(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2084);
               throw Py.makeException();
            }

            var1.setline(2086);
            var4 = var1.getlocal(4).__getattr__("filename");
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2087);
               var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("tarfile: %s")._mod(var1.getlocal(4).__getattr__("strerror")));
            } else {
               var1.setline(2089);
               var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("tarfile: %s %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4).__getattr__("strerror"), var1.getlocal(4).__getattr__("filename")})));
            }
         } else {
            if (!var6.match(var1.getglobal("ExtractError"))) {
               throw var6;
            }

            var4 = var6.value;
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(2091);
            var4 = var1.getlocal(0).__getattr__("errorlevel");
            var10000 = var4._gt(Py.newInteger(1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2092);
               throw Py.makeException();
            }

            var1.setline(2094);
            var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("tarfile: %s")._mod(var1.getlocal(4)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject extractfile$123(PyFrame var1, ThreadState var2) {
      var1.setline(2104);
      PyString.fromInterned("Extract a member from the archive as a file object. `member' may be\n           a filename or a TarInfo object. If `member' is a regular file, a\n           file-like object is returned. If `member' is a link, a file-like\n           object is constructed from the link's target. If `member' is none of\n           the above, None is returned.\n           The file-like object is read-only and provides the following\n           methods: read(), readline(), readlines(), seek() and tell()\n        ");
      var1.setline(2105);
      var1.getlocal(0).__getattr__("_check").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("r"));
      var1.setline(2107);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(2108);
         var3 = var1.getlocal(0).__getattr__("getmember").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(2110);
         var3 = var1.getlocal(1);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(2112);
      if (var1.getlocal(2).__getattr__("isreg").__call__(var2).__nonzero__()) {
         var1.setline(2113);
         var3 = var1.getlocal(0).__getattr__("fileobject").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2115);
         PyObject var4 = var1.getlocal(2).__getattr__("type");
         PyObject var10000 = var4._notin(var1.getglobal("SUPPORTED_TYPES"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2118);
            var3 = var1.getlocal(0).__getattr__("fileobject").__call__(var2, var1.getlocal(0), var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2120);
            var10000 = var1.getlocal(2).__getattr__("islnk").__call__(var2);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(2).__getattr__("issym").__call__(var2);
            }

            if (var10000.__nonzero__()) {
               var1.setline(2121);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("fileobj"), var1.getglobal("_Stream")).__nonzero__()) {
                  var1.setline(2125);
                  throw Py.makeException(var1.getglobal("StreamError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot extract (sym)link as file object")));
               } else {
                  var1.setline(2128);
                  var3 = var1.getlocal(0).__getattr__("extractfile").__call__(var2, var1.getlocal(0).__getattr__("_find_link_target").__call__(var2, var1.getlocal(2)));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(2132);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _extract_member$124(PyFrame var1, ThreadState var2) {
      var1.setline(2137);
      PyString.fromInterned("Extract the TarInfo object tarinfo to a physical\n           file called targetpath.\n        ");
      var1.setline(2141);
      PyObject var3 = var1.getlocal(2).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2142);
      var3 = var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)var1.getglobal("os").__getattr__("sep"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2145);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2146);
      PyObject var10000 = var1.getlocal(3);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(3)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(2149);
         var1.getglobal("os").__getattr__("makedirs").__call__(var2, var1.getlocal(3));
      }

      var1.setline(2151);
      var10000 = var1.getlocal(1).__getattr__("islnk").__call__(var2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("issym").__call__(var2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(2152);
         var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("%s -> %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("name"), var1.getlocal(1).__getattr__("linkname")})));
      } else {
         var1.setline(2154);
         var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getlocal(1).__getattr__("name"));
      }

      var1.setline(2156);
      if (var1.getlocal(1).__getattr__("isreg").__call__(var2).__nonzero__()) {
         var1.setline(2157);
         var1.getlocal(0).__getattr__("makefile").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      } else {
         var1.setline(2158);
         if (var1.getlocal(1).__getattr__("isdir").__call__(var2).__nonzero__()) {
            var1.setline(2159);
            var1.getlocal(0).__getattr__("makedir").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         } else {
            var1.setline(2160);
            if (var1.getlocal(1).__getattr__("isfifo").__call__(var2).__nonzero__()) {
               var1.setline(2161);
               var1.getlocal(0).__getattr__("makefifo").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            } else {
               var1.setline(2162);
               var10000 = var1.getlocal(1).__getattr__("ischr").__call__(var2);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(1).__getattr__("isblk").__call__(var2);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(2163);
                  var1.getlocal(0).__getattr__("makedev").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               } else {
                  var1.setline(2164);
                  var10000 = var1.getlocal(1).__getattr__("islnk").__call__(var2);
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(1).__getattr__("issym").__call__(var2);
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(2165);
                     var1.getlocal(0).__getattr__("makelink").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                  } else {
                     var1.setline(2166);
                     var3 = var1.getlocal(1).__getattr__("type");
                     var10000 = var3._notin(var1.getglobal("SUPPORTED_TYPES"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2167);
                        var1.getlocal(0).__getattr__("makeunknown").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                     } else {
                        var1.setline(2169);
                        var1.getlocal(0).__getattr__("makefile").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                     }
                  }
               }
            }
         }
      }

      var1.setline(2171);
      var1.getlocal(0).__getattr__("chown").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(2172);
      if (var1.getlocal(1).__getattr__("issym").__call__(var2).__not__().__nonzero__()) {
         var1.setline(2173);
         var1.getlocal(0).__getattr__("chmod").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setline(2174);
         var1.getlocal(0).__getattr__("utime").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject makedir$125(PyFrame var1, ThreadState var2) {
      var1.setline(2183);
      PyString.fromInterned("Make a directory called targetpath.\n        ");

      try {
         var1.setline(2187);
         var1.getglobal("os").__getattr__("mkdir").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(448));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("EnvironmentError"))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(2189);
         var4 = var1.getlocal(3).__getattr__("errno");
         PyObject var10000 = var4._ne(var1.getglobal("errno").__getattr__("EEXIST"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2190);
            throw Py.makeException();
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject makefile$126(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(2194);
      PyString.fromInterned("Make a file called targetpath.\n        ");
      var1.setline(2195);
      PyObject var3 = var1.getlocal(0).__getattr__("extractfile").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         label26: {
            ContextManager var4;
            PyObject var5 = (var4 = ContextGuard.getManager(var1.getglobal("bltn_open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("wb")))).__enter__(var2);

            try {
               var1.setlocal(4, var5);
               var1.setline(2198);
               var1.getglobal("copyfileobj").__call__(var2, var1.getlocal(3), var1.getlocal(4));
            } catch (Throwable var6) {
               if (var4.__exit__(var2, Py.setException(var6, var1))) {
                  break label26;
               }

               throw (Throwable)Py.makeException();
            }

            var4.__exit__(var2, (PyException)null);
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(2200);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(2200);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject makeunknown$127(PyFrame var1, ThreadState var2) {
      var1.setline(2205);
      PyString.fromInterned("Make a file from a TarInfo object with an unknown type\n           at targetpath.\n        ");
      var1.setline(2206);
      var1.getlocal(0).__getattr__("makefile").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(2207);
      var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("tarfile: Unknown file type %r, extracted as regular file.")._mod(var1.getlocal(1).__getattr__("type")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject makefifo$128(PyFrame var1, ThreadState var2) {
      var1.setline(2212);
      PyString.fromInterned("Make a fifo called targetpath.\n        ");
      var1.setline(2213);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("mkfifo")).__nonzero__()) {
         var1.setline(2214);
         var1.getglobal("os").__getattr__("mkfifo").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(2216);
         throw Py.makeException(var1.getglobal("ExtractError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fifo not supported by system")));
      }
   }

   public PyObject makedev$129(PyFrame var1, ThreadState var2) {
      var1.setline(2220);
      PyString.fromInterned("Make a character or block device called targetpath.\n        ");
      var1.setline(2221);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("mknod")).__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("makedev")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(2222);
         throw Py.makeException(var1.getglobal("ExtractError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("special devices not supported by system")));
      } else {
         var1.setline(2224);
         PyObject var3 = var1.getlocal(1).__getattr__("mode");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(2225);
         if (var1.getlocal(1).__getattr__("isblk").__call__(var2).__nonzero__()) {
            var1.setline(2226);
            var3 = var1.getlocal(3);
            var3 = var3._ior(var1.getglobal("stat").__getattr__("S_IFBLK"));
            var1.setlocal(3, var3);
         } else {
            var1.setline(2228);
            var3 = var1.getlocal(3);
            var3 = var3._ior(var1.getglobal("stat").__getattr__("S_IFCHR"));
            var1.setlocal(3, var3);
         }

         var1.setline(2230);
         var1.getglobal("os").__getattr__("mknod").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getglobal("os").__getattr__("makedev").__call__(var2, var1.getlocal(1).__getattr__("devmajor"), var1.getlocal(1).__getattr__("devminor")));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject makelink$130(PyFrame var1, ThreadState var2) {
      var1.setline(2237);
      PyString.fromInterned("Make a (symbolic) link called targetpath. If it cannot be created\n          (platform limitation), we try to make a copy of the referenced file\n          instead of a link.\n        ");
      var1.setline(2238);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("symlink"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("link"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(2240);
         if (var1.getlocal(1).__getattr__("issym").__call__(var2).__nonzero__()) {
            var1.setline(2241);
            if (var1.getglobal("os").__getattr__("path").__getattr__("lexists").__call__(var2, var1.getlocal(2)).__nonzero__()) {
               var1.setline(2242);
               var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(2));
            }

            var1.setline(2243);
            var1.getglobal("os").__getattr__("symlink").__call__(var2, var1.getlocal(1).__getattr__("linkname"), var1.getlocal(2));
         } else {
            var1.setline(2246);
            if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1).__getattr__("_link_target")).__nonzero__()) {
               var1.setline(2247);
               if (var1.getglobal("os").__getattr__("path").__getattr__("lexists").__call__(var2, var1.getlocal(2)).__nonzero__()) {
                  var1.setline(2248);
                  var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(2));
               }

               var1.setline(2249);
               var1.getglobal("os").__getattr__("link").__call__(var2, var1.getlocal(1).__getattr__("_link_target"), var1.getlocal(2));
            } else {
               var1.setline(2251);
               var1.getlocal(0).__getattr__("_extract_member").__call__(var2, var1.getlocal(0).__getattr__("_find_link_target").__call__(var2, var1.getlocal(1)), var1.getlocal(2));
            }
         }
      } else {
         try {
            var1.setline(2254);
            var1.getlocal(0).__getattr__("_extract_member").__call__(var2, var1.getlocal(0).__getattr__("_find_link_target").__call__(var2, var1.getlocal(1)), var1.getlocal(2));
         } catch (Throwable var4) {
            PyException var3 = Py.setException(var4, var1);
            if (var3.match(var1.getglobal("KeyError"))) {
               var1.setline(2256);
               throw Py.makeException(var1.getglobal("ExtractError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unable to resolve link inside archive")));
            }

            throw var3;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject chown$131(PyFrame var1, ThreadState var2) {
      var1.setline(2260);
      PyString.fromInterned("Set owner of targetpath according to tarinfo.\n        ");
      var1.setline(2261);
      PyObject var10000 = var1.getglobal("pwd");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("geteuid"));
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("os").__getattr__("geteuid").__call__(var2);
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         PyObject var4;
         PyException var8;
         try {
            var1.setline(2264);
            var3 = var1.getglobal("grp").__getattr__("getgrnam").__call__(var2, var1.getlocal(1).__getattr__("gname")).__getitem__(Py.newInteger(2));
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var7) {
            var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getglobal("KeyError"))) {
               throw var8;
            }

            var1.setline(2266);
            var4 = var1.getlocal(1).__getattr__("gid");
            var1.setlocal(3, var4);
            var4 = null;
         }

         try {
            var1.setline(2268);
            var3 = var1.getglobal("pwd").__getattr__("getpwnam").__call__(var2, var1.getlocal(1).__getattr__("uname")).__getitem__(Py.newInteger(2));
            var1.setlocal(4, var3);
            var3 = null;
         } catch (Throwable var6) {
            var8 = Py.setException(var6, var1);
            if (!var8.match(var1.getglobal("KeyError"))) {
               throw var8;
            }

            var1.setline(2270);
            var4 = var1.getlocal(1).__getattr__("uid");
            var1.setlocal(4, var4);
            var4 = null;
         }

         try {
            var1.setline(2272);
            var10000 = var1.getlocal(1).__getattr__("issym").__call__(var2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("lchown"));
            }

            if (var10000.__nonzero__()) {
               var1.setline(2273);
               var1.getglobal("os").__getattr__("lchown").__call__(var2, var1.getlocal(2), var1.getlocal(4), var1.getlocal(3));
            } else {
               var1.setline(2275);
               var3 = var1.getglobal("sys").__getattr__("platform");
               var10000 = var3._ne(PyString.fromInterned("os2emx"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2276);
                  var1.getglobal("os").__getattr__("chown").__call__(var2, var1.getlocal(2), var1.getlocal(4), var1.getlocal(3));
               }
            }
         } catch (Throwable var5) {
            var8 = Py.setException(var5, var1);
            if (var8.match(var1.getglobal("EnvironmentError"))) {
               var4 = var8.value;
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(2278);
               throw Py.makeException(var1.getglobal("ExtractError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("could not change owner")));
            }

            throw var8;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject chmod$132(PyFrame var1, ThreadState var2) {
      var1.setline(2282);
      PyString.fromInterned("Set file permissions of targetpath according to tarinfo.\n        ");
      var1.setline(2283);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("chmod")).__nonzero__()) {
         try {
            var1.setline(2285);
            var1.getglobal("os").__getattr__("chmod").__call__(var2, var1.getlocal(2), var1.getlocal(1).__getattr__("mode"));
         } catch (Throwable var5) {
            PyException var3 = Py.setException(var5, var1);
            if (var3.match(var1.getglobal("EnvironmentError"))) {
               PyObject var4 = var3.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(2287);
               throw Py.makeException(var1.getglobal("ExtractError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("could not change mode")));
            }

            throw var3;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject utime$133(PyFrame var1, ThreadState var2) {
      var1.setline(2291);
      PyString.fromInterned("Set modification time of targetpath according to tarinfo.\n        ");
      var1.setline(2292);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("utime")).__not__().__nonzero__()) {
         var1.setline(2293);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         try {
            var1.setline(2295);
            var1.getglobal("os").__getattr__("utime").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("mtime"), var1.getlocal(1).__getattr__("mtime")})));
         } catch (Throwable var5) {
            PyException var3 = Py.setException(var5, var1);
            if (var3.match(var1.getglobal("EnvironmentError"))) {
               PyObject var4 = var3.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(2297);
               throw Py.makeException(var1.getglobal("ExtractError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("could not change modification time")));
            }

            throw var3;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject next$134(PyFrame var1, ThreadState var2) {
      var1.setline(2304);
      PyString.fromInterned("Return the next member of the archive as a TarInfo object, when\n           TarFile is opened for reading. Return None if there is no more\n           available.\n        ");
      var1.setline(2305);
      var1.getlocal(0).__getattr__("_check").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ra"));
      var1.setline(2306);
      PyObject var3 = var1.getlocal(0).__getattr__("firstmember");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2307);
         var3 = var1.getlocal(0).__getattr__("firstmember");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2308);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("firstmember", var3);
         var3 = null;
         var1.setline(2309);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2312);
         var1.getlocal(0).__getattr__("fileobj").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("offset"));
         var1.setline(2313);
         PyObject var4 = var1.getglobal("None");
         var1.setlocal(2, var4);
         var4 = null;

         while(true) {
            var1.setline(2314);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            try {
               var1.setline(2316);
               var4 = var1.getlocal(0).__getattr__("tarinfo").__getattr__("fromtarfile").__call__(var2, var1.getlocal(0));
               var1.setlocal(2, var4);
               var4 = null;
               break;
            } catch (Throwable var8) {
               PyException var9 = Py.setException(var8, var1);
               PyObject var5;
               PyObject var6;
               PyObject var7;
               String var10;
               if (var9.match(var1.getglobal("EOFHeaderError"))) {
                  var5 = var9.value;
                  var1.setlocal(3, var5);
                  var5 = null;
                  var1.setline(2318);
                  if (!var1.getlocal(0).__getattr__("ignore_zeros").__nonzero__()) {
                     break;
                  }

                  var1.setline(2319);
                  var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)PyString.fromInterned("0x%X: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("offset"), var1.getlocal(3)})));
                  var1.setline(2320);
                  var10000 = var1.getlocal(0);
                  var10 = "offset";
                  var6 = var10000;
                  var7 = var6.__getattr__(var10);
                  var7 = var7._iadd(var1.getglobal("BLOCKSIZE"));
                  var6.__setattr__(var10, var7);
               } else {
                  if (var9.match(var1.getglobal("InvalidHeaderError"))) {
                     var5 = var9.value;
                     var1.setlocal(3, var5);
                     var5 = null;
                     var1.setline(2323);
                     if (var1.getlocal(0).__getattr__("ignore_zeros").__nonzero__()) {
                        var1.setline(2324);
                        var1.getlocal(0).__getattr__("_dbg").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)PyString.fromInterned("0x%X: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("offset"), var1.getlocal(3)})));
                        var1.setline(2325);
                        var10000 = var1.getlocal(0);
                        var10 = "offset";
                        var6 = var10000;
                        var7 = var6.__getattr__(var10);
                        var7 = var7._iadd(var1.getglobal("BLOCKSIZE"));
                        var6.__setattr__(var10, var7);
                        continue;
                     }

                     var1.setline(2327);
                     var5 = var1.getlocal(0).__getattr__("offset");
                     var10000 = var5._eq(Py.newInteger(0));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2328);
                        throw Py.makeException(var1.getglobal("ReadError").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3))));
                     }
                     break;
                  }

                  if (var9.match(var1.getglobal("EmptyHeaderError"))) {
                     var1.setline(2330);
                     var5 = var1.getlocal(0).__getattr__("offset");
                     var10000 = var5._eq(Py.newInteger(0));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2331);
                        throw Py.makeException(var1.getglobal("ReadError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("empty file")));
                     }
                  } else {
                     if (!var9.match(var1.getglobal("TruncatedHeaderError"))) {
                        if (var9.match(var1.getglobal("SubsequentHeaderError"))) {
                           var5 = var9.value;
                           var1.setlocal(3, var5);
                           var5 = null;
                           var1.setline(2336);
                           throw Py.makeException(var1.getglobal("ReadError").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3))));
                        }

                        throw var9;
                     }

                     var5 = var9.value;
                     var1.setlocal(3, var5);
                     var5 = null;
                     var1.setline(2333);
                     var5 = var1.getlocal(0).__getattr__("offset");
                     var10000 = var5._eq(Py.newInteger(0));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2334);
                        throw Py.makeException(var1.getglobal("ReadError").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3))));
                     }
                  }
                  break;
               }
            }
         }

         var1.setline(2339);
         var4 = var1.getlocal(2);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2340);
            var1.getlocal(0).__getattr__("members").__getattr__("append").__call__(var2, var1.getlocal(2));
         } else {
            var1.setline(2342);
            var4 = var1.getglobal("True");
            var1.getlocal(0).__setattr__("_loaded", var4);
            var4 = null;
         }

         var1.setline(2344);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _getmember$135(PyFrame var1, ThreadState var2) {
      var1.setline(2352);
      PyString.fromInterned("Find an archive member by name from bottom to top.\n           If tarinfo is given, it is used as the starting point.\n        ");
      var1.setline(2354);
      PyObject var3 = var1.getlocal(0).__getattr__("getmembers").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2357);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2358);
         var3 = var1.getlocal(4).__getslice__((PyObject)null, var1.getlocal(4).__getattr__("index").__call__(var2, var1.getlocal(2)), (PyObject)null);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(2360);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(2361);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2363);
      var3 = var1.getglobal("reversed").__call__(var2, var1.getlocal(4)).__iter__();

      PyObject var5;
      do {
         var1.setline(2363);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(2364);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(2365);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(5).__getattr__("name"));
            var1.setlocal(6, var5);
            var5 = null;
         } else {
            var1.setline(2367);
            var5 = var1.getlocal(5).__getattr__("name");
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(2369);
         var5 = var1.getlocal(1);
         var10000 = var5._eq(var1.getlocal(6));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(2370);
      var5 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _load$136(PyFrame var1, ThreadState var2) {
      var1.setline(2375);
      PyString.fromInterned("Read through the entire archive file and look for readable\n           members.\n        ");

      PyObject var10000;
      PyObject var3;
      do {
         var1.setline(2376);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(2377);
         var3 = var1.getlocal(0).__getattr__("next").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2378);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      } while(!var10000.__nonzero__());

      var1.setline(2380);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_loaded", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check$137(PyFrame var1, ThreadState var2) {
      var1.setline(2385);
      PyString.fromInterned("Check if TarFile is still open, and if the operation's mode\n           corresponds to TarFile's mode.\n        ");
      var1.setline(2386);
      if (var1.getlocal(0).__getattr__("closed").__nonzero__()) {
         var1.setline(2387);
         throw Py.makeException(var1.getglobal("IOError").__call__(var2, PyString.fromInterned("%s is closed")._mod(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"))));
      } else {
         var1.setline(2388);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("mode");
            var10000 = var3._notin(var1.getlocal(1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(2389);
            throw Py.makeException(var1.getglobal("IOError").__call__(var2, PyString.fromInterned("bad operation for mode %r")._mod(var1.getlocal(0).__getattr__("mode"))));
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _find_link_target$138(PyFrame var1, ThreadState var2) {
      var1.setline(2394);
      PyString.fromInterned("Find the target member of a symlink or hardlink member in the\n           archive.\n        ");
      var1.setline(2395);
      PyObject var3;
      if (var1.getlocal(1).__getattr__("issym").__call__(var2).__nonzero__()) {
         var1.setline(2397);
         var3 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getglobal("filter").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(1).__getattr__("name")), var1.getlocal(1).__getattr__("linkname")}))));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2398);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(2402);
         var3 = var1.getlocal(1).__getattr__("linkname");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2403);
         var3 = var1.getlocal(1);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(2405);
      PyObject var10000 = var1.getlocal(0).__getattr__("_getmember");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getglobal("True")};
      String[] var4 = new String[]{"tarinfo", "normalize"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2406);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2407);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("linkname %r not found")._mod(var1.getlocal(2))));
      } else {
         var1.setline(2408);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __iter__$139(PyFrame var1, ThreadState var2) {
      var1.setline(2412);
      PyString.fromInterned("Provide an iterator object.\n        ");
      var1.setline(2413);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_loaded").__nonzero__()) {
         var1.setline(2414);
         var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0).__getattr__("members"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2416);
         var3 = var1.getglobal("TarIter").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _dbg$140(PyFrame var1, ThreadState var2) {
      var1.setline(2420);
      PyString.fromInterned("Write debugging output to sys.stderr.\n        ");
      var1.setline(2421);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._le(var1.getlocal(0).__getattr__("debug"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2422);
         var3 = var1.getglobal("sys").__getattr__("stderr");
         Py.println(var3, var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __enter__$141(PyFrame var1, ThreadState var2) {
      var1.setline(2425);
      var1.getlocal(0).__getattr__("_check").__call__(var2);
      var1.setline(2426);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$142(PyFrame var1, ThreadState var2) {
      var1.setline(2429);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2430);
         var1.getlocal(0).__getattr__("close").__call__(var2);
      } else {
         var1.setline(2434);
         if (var1.getlocal(0).__getattr__("_extfileobj").__not__().__nonzero__()) {
            var1.setline(2435);
            var1.getlocal(0).__getattr__("fileobj").__getattr__("close").__call__(var2);
         }

         var1.setline(2436);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("closed", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TarIter$143(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Iterator Class.\n\n       for tarinfo in TarFile(...):\n           suite...\n    "));
      var1.setline(2444);
      PyString.fromInterned("Iterator Class.\n\n       for tarinfo in TarFile(...):\n           suite...\n    ");
      var1.setline(2446);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$144, PyString.fromInterned("Construct a TarIter object.\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2451);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$145, PyString.fromInterned("Return iterator object.\n        "));
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(2455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$146, PyString.fromInterned("Return the next item using TarFile's next() method.\n           When all members have been read, set TarFile as _loaded.\n        "));
      var1.setlocal("next", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$144(PyFrame var1, ThreadState var2) {
      var1.setline(2448);
      PyString.fromInterned("Construct a TarIter object.\n        ");
      var1.setline(2449);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("tarfile", var3);
      var3 = null;
      var1.setline(2450);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"index", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$145(PyFrame var1, ThreadState var2) {
      var1.setline(2453);
      PyString.fromInterned("Return iterator object.\n        ");
      var1.setline(2454);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$146(PyFrame var1, ThreadState var2) {
      var1.setline(2458);
      PyString.fromInterned("Return the next item using TarFile's next() method.\n           When all members have been read, set TarFile as _loaded.\n        ");
      var1.setline(2462);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("tarfile").__getattr__("_loaded").__not__().__nonzero__()) {
         var1.setline(2463);
         var3 = var1.getlocal(0).__getattr__("tarfile").__getattr__("next").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2464);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(2465);
            var3 = var1.getglobal("True");
            var1.getlocal(0).__getattr__("tarfile").__setattr__("_loaded", var3);
            var3 = null;
            var1.setline(2466);
            throw Py.makeException(var1.getglobal("StopIteration"));
         }
      } else {
         try {
            var1.setline(2469);
            var3 = var1.getlocal(0).__getattr__("tarfile").__getattr__("members").__getitem__(var1.getlocal(0).__getattr__("index"));
            var1.setlocal(1, var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("IndexError"))) {
               var1.setline(2471);
               throw Py.makeException(var1.getglobal("StopIteration"));
            }

            throw var7;
         }
      }

      var1.setline(2472);
      PyObject var10000 = var1.getlocal(0);
      String var8 = "index";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var8);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var8, var5);
      var1.setline(2473);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _section$147(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for _data and _hole.\n    "));
      var1.setline(2478);
      PyString.fromInterned("Base class for _data and _hole.\n    ");
      var1.setline(2479);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$148, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2482);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$149, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$148(PyFrame var1, ThreadState var2) {
      var1.setline(2480);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("offset", var3);
      var3 = null;
      var1.setline(2481);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("size", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __contains__$149(PyFrame var1, ThreadState var2) {
      var1.setline(2483);
      PyObject var3 = var1.getlocal(0).__getattr__("offset");
      PyObject var10001 = var1.getlocal(1);
      PyObject var10000 = var3;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var3._lt(var1.getlocal(0).__getattr__("offset")._add(var1.getlocal(0).__getattr__("size")));
      }

      var3 = null;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _data$150(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Represent a data section in a sparse file.\n    "));
      var1.setline(2487);
      PyString.fromInterned("Represent a data section in a sparse file.\n    ");
      var1.setline(2488);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$151, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$151(PyFrame var1, ThreadState var2) {
      var1.setline(2489);
      var1.getglobal("_section").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(2490);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("realpos", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _hole$152(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Represent a hole section in a sparse file.\n    "));
      var1.setline(2494);
      PyString.fromInterned("Represent a hole section in a sparse file.\n    ");
      var1.setline(2495);
      return var1.getf_locals();
   }

   public PyObject _ringbuffer$153(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Ringbuffer class which increases performance\n       over a regular list.\n    "));
      var1.setline(2500);
      PyString.fromInterned("Ringbuffer class which increases performance\n       over a regular list.\n    ");
      var1.setline(2501);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$154, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2503);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, find$155, (PyObject)null);
      var1.setlocal("find", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$154(PyFrame var1, ThreadState var2) {
      var1.setline(2502);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"idx", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject find$155(PyFrame var1, ThreadState var2) {
      var1.setline(2504);
      PyObject var3 = var1.getlocal(0).__getattr__("idx");
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(2505);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(2506);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(2507);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._in(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(2509);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(2, var3);
         var1.setline(2510);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2511);
            PyInteger var5 = Py.newInteger(0);
            var1.setlocal(2, var5);
            var3 = null;
         }

         var1.setline(2512);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getlocal(0).__getattr__("idx"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2514);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(2515);
      PyObject var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("idx", var4);
      var4 = null;
      var1.setline(2516);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TarFileCompat$156(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("TarFile class compatible with standard module zipfile's\n       ZipFile class.\n    "));
      var1.setline(2526);
      PyString.fromInterned("TarFile class compatible with standard module zipfile's\n       ZipFile class.\n    ");
      var1.setline(2527);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("r"), var1.getname("TAR_PLAIN")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$157, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2543);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, namelist$158, (PyObject)null);
      var1.setlocal("namelist", var4);
      var3 = null;
      var1.setline(2545);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, infolist$160, (PyObject)null);
      var1.setlocal("infolist", var4);
      var3 = null;
      var1.setline(2548);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, printdir$162, (PyObject)null);
      var1.setlocal("printdir", var4);
      var3 = null;
      var1.setline(2550);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testzip$163, (PyObject)null);
      var1.setlocal("testzip", var4);
      var3 = null;
      var1.setline(2552);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getinfo$164, (PyObject)null);
      var1.setlocal("getinfo", var4);
      var3 = null;
      var1.setline(2554);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$165, (PyObject)null);
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(2556);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, write$166, (PyObject)null);
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(2558);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writestr$167, (PyObject)null);
      var1.setlocal("writestr", var4);
      var3 = null;
      var1.setline(2568);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$168, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$157(PyFrame var1, ThreadState var2) {
      var1.setline(2528);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var6 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(2529);
      PyObject var10000 = var1.getlocal(4);
      var6 = new PyObject[]{PyString.fromInterned("the TarFileCompat class has been removed in Python 3.0"), Py.newInteger(2)};
      String[] var8 = new String[]{"stacklevel"};
      var10000.__call__(var2, var6, var8);
      var3 = null;
      var1.setline(2531);
      PyObject var7 = var1.getlocal(3);
      var10000 = var7._eq(var1.getglobal("TAR_PLAIN"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2532);
         var7 = var1.getglobal("TarFile").__getattr__("taropen").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.getlocal(0).__setattr__("tarfile", var7);
         var3 = null;
      } else {
         var1.setline(2533);
         var7 = var1.getlocal(3);
         var10000 = var7._eq(var1.getglobal("TAR_GZIPPED"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(2536);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unknown compression constant")));
         }

         var1.setline(2534);
         var7 = var1.getglobal("TarFile").__getattr__("gzopen").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.getlocal(0).__setattr__("tarfile", var7);
         var3 = null;
      }

      var1.setline(2537);
      var7 = var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(1), (PyObject)null);
      var10000 = var7._eq(PyString.fromInterned("r"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2538);
         var7 = var1.getlocal(0).__getattr__("tarfile").__getattr__("getmembers").__call__(var2);
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(2539);
         var7 = var1.getlocal(5).__iter__();

         while(true) {
            var1.setline(2539);
            var4 = var7.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(6, var4);
            var1.setline(2540);
            PyObject var5 = var1.getlocal(6).__getattr__("name");
            var1.getlocal(6).__setattr__("filename", var5);
            var5 = null;
            var1.setline(2541);
            var5 = var1.getlocal(6).__getattr__("size");
            var1.getlocal(6).__setattr__("file_size", var5);
            var5 = null;
            var1.setline(2542);
            var5 = var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(6).__getattr__("mtime")).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
            var1.getlocal(6).__setattr__("date_time", var5);
            var5 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject namelist$158(PyFrame var1, ThreadState var2) {
      var1.setline(2544);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(2544);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$159)), (PyObject)var1.getlocal(0).__getattr__("infolist").__call__(var2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$159(PyFrame var1, ThreadState var2) {
      var1.setline(2544);
      PyObject var3 = var1.getlocal(0).__getattr__("name");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject infolist$160(PyFrame var1, ThreadState var2) {
      var1.setline(2546);
      PyObject var10000 = var1.getglobal("filter");
      var1.setline(2546);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$161)), (PyObject)var1.getlocal(0).__getattr__("tarfile").__getattr__("getmembers").__call__(var2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$161(PyFrame var1, ThreadState var2) {
      var1.setline(2546);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._in(var1.getglobal("REGULAR_TYPES"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject printdir$162(PyFrame var1, ThreadState var2) {
      var1.setline(2549);
      var1.getlocal(0).__getattr__("tarfile").__getattr__("list").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testzip$163(PyFrame var1, ThreadState var2) {
      var1.setline(2551);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getinfo$164(PyFrame var1, ThreadState var2) {
      var1.setline(2553);
      PyObject var3 = var1.getlocal(0).__getattr__("tarfile").__getattr__("getmember").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read$165(PyFrame var1, ThreadState var2) {
      var1.setline(2555);
      PyObject var3 = var1.getlocal(0).__getattr__("tarfile").__getattr__("extractfile").__call__(var2, var1.getlocal(0).__getattr__("tarfile").__getattr__("getmember").__call__(var2, var1.getlocal(1))).__getattr__("read").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write$166(PyFrame var1, ThreadState var2) {
      var1.setline(2557);
      var1.getlocal(0).__getattr__("tarfile").__getattr__("add").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writestr$167(PyFrame var1, ThreadState var2) {
      PyException var3;
      String[] var4;
      try {
         var1.setline(2560);
         String[] var7 = new String[]{"StringIO"};
         PyObject[] var8 = imp.importFrom("cStringIO", var7, var1, -1);
         PyObject var11 = var8[0];
         var1.setlocal(3, var11);
         var4 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("ImportError"))) {
            throw var3;
         }

         var1.setline(2562);
         var4 = new String[]{"StringIO"};
         PyObject[] var10 = imp.importFrom("StringIO", var4, var1, -1);
         PyObject var5 = var10[0];
         var1.setlocal(3, var5);
         var5 = null;
      }

      var1.setline(2563);
      PyObject var9 = imp.importOne("calendar", var1, -1);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(2564);
      var9 = var1.getglobal("TarInfo").__call__(var2, var1.getlocal(1).__getattr__("filename"));
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(2565);
      var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var1.getlocal(5).__setattr__("size", var9);
      var3 = null;
      var1.setline(2566);
      var9 = var1.getlocal(4).__getattr__("timegm").__call__(var2, var1.getlocal(1).__getattr__("date_time"));
      var1.getlocal(5).__setattr__("mtime", var9);
      var3 = null;
      var1.setline(2567);
      var1.getlocal(0).__getattr__("tarfile").__getattr__("addfile").__call__(var2, var1.getlocal(5), var1.getlocal(3).__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$168(PyFrame var1, ThreadState var2) {
      var1.setline(2569);
      var1.getlocal(0).__getattr__("tarfile").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_tarfile$169(PyFrame var1, ThreadState var2) {
      var1.setline(2578);
      PyString.fromInterned("Return True if name points to a tar archive that we\n       are able to handle, else return False.\n    ");

      PyObject var3;
      try {
         var1.setline(2580);
         var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2581);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         var1.setline(2582);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("TarError"))) {
            var1.setline(2584);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public tarfile$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s", "length"};
      stn$1 = Py.newCode(2, var2, var1, "stn", 168, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "p"};
      nts$2 = Py.newCode(1, var2, var1, "nts", 173, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "n", "i"};
      nti$3 = Py.newCode(1, var2, var1, "nti", 182, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "digits", "format", "s", "i"};
      itn$4 = Py.newCode(3, var2, var1, "itn", 199, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "encoding", "errors", "x", "c"};
      uts$5 = Py.newCode(3, var2, var1, "uts", 226, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"buf", "unsigned_chksum", "signed_chksum"};
      calc_chksums$6 = Py.newCode(1, var2, var1, "calc_chksums", 246, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"src", "dst", "length", "BUFSIZE", "blocks", "remainder", "b", "buf"};
      copyfileobj$7 = Py.newCode(3, var2, var1, "copyfileobj", 259, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mode", "perm", "table", "bit", "char"};
      filemode$8 = Py.newCode(1, var2, var1, "filemode", 311, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TarError$9 = Py.newCode(0, var2, var1, "TarError", 326, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ExtractError$10 = Py.newCode(0, var2, var1, "ExtractError", 329, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ReadError$11 = Py.newCode(0, var2, var1, "ReadError", 332, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CompressionError$12 = Py.newCode(0, var2, var1, "CompressionError", 335, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamError$13 = Py.newCode(0, var2, var1, "StreamError", 338, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      HeaderError$14 = Py.newCode(0, var2, var1, "HeaderError", 341, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      EmptyHeaderError$15 = Py.newCode(0, var2, var1, "EmptyHeaderError", 344, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TruncatedHeaderError$16 = Py.newCode(0, var2, var1, "TruncatedHeaderError", 347, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      EOFHeaderError$17 = Py.newCode(0, var2, var1, "EOFHeaderError", 350, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InvalidHeaderError$18 = Py.newCode(0, var2, var1, "InvalidHeaderError", 353, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SubsequentHeaderError$19 = Py.newCode(0, var2, var1, "SubsequentHeaderError", 356, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _LowLevelFile$20 = Py.newCode(0, var2, var1, "_LowLevelFile", 363, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "mode"};
      __init__$21 = Py.newCode(3, var2, var1, "__init__", 369, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$22 = Py.newCode(1, var2, var1, "close", 378, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      read$23 = Py.newCode(2, var2, var1, "read", 381, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      write$24 = Py.newCode(2, var2, var1, "write", 384, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Stream$25 = Py.newCode(0, var2, var1, "_Stream", 387, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "mode", "comptype", "fileobj", "bufsize", "zlib", "bz2"};
      __init__$26 = Py.newCode(6, var2, var1, "__init__", 398, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$27 = Py.newCode(1, var2, var1, "__del__", 444, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "timestamp"};
      _init_write_gz$28 = Py.newCode(1, var2, var1, "_init_write_gz", 448, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      write$29 = Py.newCode(2, var2, var1, "write", 463, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      _Stream__write$30 = Py.newCode(2, var2, var1, "_Stream__write", 473, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$31 = Py.newCode(1, var2, var1, "close", 482, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag", "xlen", "s"};
      _init_read_gz$32 = Py.newCode(1, var2, var1, "_init_read_gz", 510, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$33 = Py.newCode(1, var2, var1, "tell", 541, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "blocks", "remainder", "i"};
      seek$34 = Py.newCode(2, var2, var1, "seek", 546, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "t", "buf"};
      read$35 = Py.newCode(2, var2, var1, "read", 559, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "c", "t", "buf"};
      _read$36 = Py.newCode(2, var2, var1, "_read", 577, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "c", "t", "buf"};
      _Stream__read$37 = Py.newCode(2, var2, var1, "_Stream__read", 599, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _StreamProxy$38 = Py.newCode(0, var2, var1, "_StreamProxy", 616, false, false, self, 38, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fileobj"};
      __init__$39 = Py.newCode(2, var2, var1, "__init__", 621, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      read$40 = Py.newCode(2, var2, var1, "read", 625, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getcomptype$41 = Py.newCode(1, var2, var1, "getcomptype", 629, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$42 = Py.newCode(1, var2, var1, "close", 636, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _BZ2Proxy$43 = Py.newCode(0, var2, var1, "_BZ2Proxy", 640, false, false, self, 43, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fileobj", "mode"};
      __init__$44 = Py.newCode(3, var2, var1, "__init__", 650, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bz2"};
      init$45 = Py.newCode(1, var2, var1, "init", 656, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "b", "x", "raw", "data", "buf"};
      read$46 = Py.newCode(2, var2, var1, "read", 666, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      seek$47 = Py.newCode(2, var2, var1, "seek", 683, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$48 = Py.newCode(1, var2, var1, "tell", 688, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "raw"};
      write$49 = Py.newCode(2, var2, var1, "write", 691, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "raw"};
      close$50 = Py.newCode(1, var2, var1, "close", 696, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _FileInFile$51 = Py.newCode(0, var2, var1, "_FileInFile", 705, false, false, self, 51, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fileobj", "offset", "size", "sparse"};
      __init__$52 = Py.newCode(5, var2, var1, "__init__", 711, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$53 = Py.newCode(1, var2, var1, "tell", 718, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "position"};
      seek$54 = Py.newCode(2, var2, var1, "seek", 723, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      read$55 = Py.newCode(2, var2, var1, "read", 728, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      readnormal$56 = Py.newCode(2, var2, var1, "readnormal", 741, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "data", "buf"};
      readsparse$57 = Py.newCode(2, var2, var1, "readsparse", 748, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "section", "realpos"};
      readsparsesection$58 = Py.newCode(2, var2, var1, "readsparsesection", 760, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ExFileObject$59 = Py.newCode(0, var2, var1, "ExFileObject", 781, false, false, self, 59, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tarfile", "tarinfo"};
      __init__$60 = Py.newCode(3, var2, var1, "__init__", 787, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "buf"};
      read$61 = Py.newCode(2, var2, var1, "read", 800, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "pos", "buffers", "buf"};
      readline$62 = Py.newCode(2, var2, var1, "readline", 824, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "line"};
      readlines$63 = Py.newCode(1, var2, var1, "readlines", 855, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$64 = Py.newCode(1, var2, var1, "tell", 865, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "whence"};
      seek$65 = Py.newCode(3, var2, var1, "seek", 873, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$66 = Py.newCode(1, var2, var1, "close", 894, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      __iter__$67 = Py.newCode(1, var2, var1, "__iter__", 899, false, false, self, 67, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      TarInfo$68 = Py.newCode(0, var2, var1, "TarInfo", 912, false, false, self, 68, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name"};
      __init__$69 = Py.newCode(2, var2, var1, "__init__", 920, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _getpath$70 = Py.newCode(1, var2, var1, "_getpath", 945, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      _setpath$71 = Py.newCode(2, var2, var1, "_setpath", 947, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _getlinkpath$72 = Py.newCode(1, var2, var1, "_getlinkpath", 951, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "linkname"};
      _setlinkpath$73 = Py.newCode(2, var2, var1, "_setlinkpath", 953, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$74 = Py.newCode(1, var2, var1, "__repr__", 957, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "encoding", "errors", "info", "key"};
      get_info$75 = Py.newCode(3, var2, var1, "get_info", 960, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format", "encoding", "errors", "info"};
      tobuf$76 = Py.newCode(4, var2, var1, "tobuf", 988, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "info"};
      create_ustar_header$77 = Py.newCode(2, var2, var1, "create_ustar_header", 1002, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "info", "buf"};
      create_gnu_header$78 = Py.newCode(2, var2, var1, "create_gnu_header", 1015, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "info", "encoding", "errors", "pax_headers", "name", "hname", "length", "val", "digits", "buf"};
      create_pax_header$79 = Py.newCode(4, var2, var1, "create_pax_header", 1029, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "pax_headers"};
      create_pax_global_header$80 = Py.newCode(2, var2, var1, "create_pax_global_header", 1080, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "prefix"};
      _posix_split_name$81 = Py.newCode(2, var2, var1, "_posix_split_name", 1086, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"info", "format", "parts", "buf", "chksum"};
      _create_header$82 = Py.newCode(2, var2, var1, "_create_header", 1101, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"payload", "blocks", "remainder"};
      _create_payload$83 = Py.newCode(1, var2, var1, "_create_payload", 1129, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "name", "type", "info"};
      _create_gnu_long_header$84 = Py.newCode(3, var2, var1, "_create_gnu_long_header", 1139, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "pax_headers", "type", "records", "keyword", "value", "l", "n", "p", "info"};
      _create_pax_generic_header$85 = Py.newCode(3, var2, var1, "_create_pax_generic_header", 1156, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "buf", "chksum", "obj", "prefix"};
      frombuf$86 = Py.newCode(2, var2, var1, "frombuf", 1188, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "tarfile", "buf", "obj"};
      fromtarfile$87 = Py.newCode(2, var2, var1, "fromtarfile", 1234, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarfile"};
      _proc_member$88 = Py.newCode(2, var2, var1, "_proc_member", 1255, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarfile", "offset"};
      _proc_builtin$89 = Py.newCode(2, var2, var1, "_proc_builtin", 1268, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarfile", "buf", "next"};
      _proc_gnulong$90 = Py.newCode(2, var2, var1, "_proc_gnulong", 1285, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarfile", "buf", "sp", "pos", "lastpos", "realpos", "i", "offset", "numbytes", "isextended", "origsize"};
      _proc_sparse$91 = Py.newCode(2, var2, var1, "_proc_sparse", 1307, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarfile", "buf", "pax_headers", "regex", "pos", "match", "length", "keyword", "value", "next", "offset"};
      _proc_pax$92 = Py.newCode(2, var2, var1, "_proc_pax", 1363, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pax_headers", "encoding", "errors", "keyword", "value"};
      _apply_pax_info$93 = Py.newCode(4, var2, var1, "_apply_pax_info", 1421, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "count", "blocks", "remainder"};
      _block$94 = Py.newCode(2, var2, var1, "_block", 1444, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isreg$95 = Py.newCode(1, var2, var1, "isreg", 1453, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isfile$96 = Py.newCode(1, var2, var1, "isfile", 1455, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isdir$97 = Py.newCode(1, var2, var1, "isdir", 1457, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      issym$98 = Py.newCode(1, var2, var1, "issym", 1459, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      islnk$99 = Py.newCode(1, var2, var1, "islnk", 1461, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      ischr$100 = Py.newCode(1, var2, var1, "ischr", 1463, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isblk$101 = Py.newCode(1, var2, var1, "isblk", 1465, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isfifo$102 = Py.newCode(1, var2, var1, "isfifo", 1467, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      issparse$103 = Py.newCode(1, var2, var1, "issparse", 1469, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      isdev$104 = Py.newCode(1, var2, var1, "isdev", 1471, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TarFile$105 = Py.newCode(0, var2, var1, "TarFile", 1475, false, false, self, 105, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "mode", "fileobj", "format", "tarinfo", "dereference", "ignore_zeros", "encoding", "errors", "pax_headers", "debug", "errorlevel", "e", "buf"};
      __init__$106 = Py.newCode(13, var2, var1, "__init__", 1501, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _getposix$107 = Py.newCode(1, var2, var1, "_getposix", 1603, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value", "warnings"};
      _setposix$108 = Py.newCode(2, var2, var1, "_setposix", 1605, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "name", "mode", "fileobj", "bufsize", "kwargs", "comptype", "func", "saved_pos", "e", "filemode", "t"};
      open$109 = Py.newCode(6, var2, var1, "open", 1626, false, true, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "name", "mode", "fileobj", "kwargs"};
      taropen$110 = Py.newCode(5, var2, var1, "taropen", 1699, false, true, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "name", "mode", "fileobj", "compresslevel", "kwargs", "gzip", "t"};
      gzopen$111 = Py.newCode(6, var2, var1, "gzopen", 1707, false, true, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls", "name", "mode", "fileobj", "compresslevel", "kwargs", "bz2", "t"};
      bz2open$112 = Py.newCode(6, var2, var1, "bz2open", 1730, false, true, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blocks", "remainder"};
      close$113 = Py.newCode(1, var2, var1, "close", 1765, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "tarinfo"};
      getmember$114 = Py.newCode(2, var2, var1, "getmember", 1785, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getmembers$115 = Py.newCode(1, var2, var1, "getmembers", 1796, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[1810_16]", "tarinfo"};
      getnames$116 = Py.newCode(1, var2, var1, "getnames", 1806, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "arcname", "fileobj", "drv", "tarinfo", "statres", "linkname", "stmd", "inode", "type"};
      gettarinfo$117 = Py.newCode(4, var2, var1, "gettarinfo", 1812, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "verbose", "tarinfo"};
      list$118 = Py.newCode(2, var2, var1, "list", 1910, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "arcname", "recursive", "exclude", "filter", "warnings", "tarinfo", "f"};
      add$119 = Py.newCode(6, var2, var1, "add", 1939, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "fileobj", "buf", "blocks", "remainder"};
      addfile$120 = Py.newCode(3, var2, var1, "addfile", 2000, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "members", "directories", "tarinfo", "dirpath", "e"};
      extractall$121 = Py.newCode(3, var2, var1, "extractall", 2026, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "member", "path", "tarinfo", "e"};
      extract$122 = Py.newCode(3, var2, var1, "extract", 2063, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "member", "tarinfo"};
      extractfile$123 = Py.newCode(2, var2, var1, "extractfile", 2096, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath", "upperdirs"};
      _extract_member$124 = Py.newCode(3, var2, var1, "_extract_member", 2134, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath", "e"};
      makedir$125 = Py.newCode(3, var2, var1, "makedir", 2181, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath", "source", "target"};
      makefile$126 = Py.newCode(3, var2, var1, "makefile", 2192, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath"};
      makeunknown$127 = Py.newCode(3, var2, var1, "makeunknown", 2202, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath"};
      makefifo$128 = Py.newCode(3, var2, var1, "makefifo", 2210, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath", "mode"};
      makedev$129 = Py.newCode(3, var2, var1, "makedev", 2218, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath"};
      makelink$130 = Py.newCode(3, var2, var1, "makelink", 2233, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath", "g", "u", "e"};
      chown$131 = Py.newCode(3, var2, var1, "chown", 2258, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath", "e"};
      chmod$132 = Py.newCode(3, var2, var1, "chmod", 2280, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "targetpath", "e"};
      utime$133 = Py.newCode(3, var2, var1, "utime", 2289, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "tarinfo", "e"};
      next$134 = Py.newCode(1, var2, var1, "next", 2300, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "tarinfo", "normalize", "members", "member", "member_name"};
      _getmember$135 = Py.newCode(4, var2, var1, "_getmember", 2349, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo"};
      _load$136 = Py.newCode(1, var2, var1, "_load", 2372, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "mode"};
      _check$137 = Py.newCode(2, var2, var1, "_check", 2382, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo", "linkname", "limit", "member"};
      _find_link_target$138 = Py.newCode(2, var2, var1, "_find_link_target", 2391, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$139 = Py.newCode(1, var2, var1, "__iter__", 2410, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "msg"};
      _dbg$140 = Py.newCode(3, var2, var1, "_dbg", 2418, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$141 = Py.newCode(1, var2, var1, "__enter__", 2424, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "value", "traceback"};
      __exit__$142 = Py.newCode(4, var2, var1, "__exit__", 2428, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TarIter$143 = Py.newCode(0, var2, var1, "TarIter", 2439, false, false, self, 143, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tarfile"};
      __init__$144 = Py.newCode(2, var2, var1, "__init__", 2446, false, false, self, 144, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$145 = Py.newCode(1, var2, var1, "__iter__", 2451, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tarinfo"};
      next$146 = Py.newCode(1, var2, var1, "next", 2455, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _section$147 = Py.newCode(0, var2, var1, "_section", 2476, false, false, self, 147, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "offset", "size"};
      __init__$148 = Py.newCode(3, var2, var1, "__init__", 2479, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset"};
      __contains__$149 = Py.newCode(2, var2, var1, "__contains__", 2482, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _data$150 = Py.newCode(0, var2, var1, "_data", 2485, false, false, self, 150, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "offset", "size", "realpos"};
      __init__$151 = Py.newCode(4, var2, var1, "__init__", 2488, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _hole$152 = Py.newCode(0, var2, var1, "_hole", 2492, false, false, self, 152, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _ringbuffer$153 = Py.newCode(0, var2, var1, "_ringbuffer", 2497, false, false, self, 153, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$154 = Py.newCode(1, var2, var1, "__init__", 2501, false, false, self, 154, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "idx", "item"};
      find$155 = Py.newCode(2, var2, var1, "find", 2503, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TarFileCompat$156 = Py.newCode(0, var2, var1, "TarFileCompat", 2523, false, false, self, 156, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "mode", "compression", "warnpy3k", "members", "m"};
      __init__$157 = Py.newCode(4, var2, var1, "__init__", 2527, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      namelist$158 = Py.newCode(1, var2, var1, "namelist", 2543, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"m"};
      f$159 = Py.newCode(1, var2, var1, "<lambda>", 2544, false, false, self, 159, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      infolist$160 = Py.newCode(1, var2, var1, "infolist", 2545, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"m"};
      f$161 = Py.newCode(1, var2, var1, "<lambda>", 2546, false, false, self, 161, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      printdir$162 = Py.newCode(1, var2, var1, "printdir", 2548, false, false, self, 162, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testzip$163 = Py.newCode(1, var2, var1, "testzip", 2550, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      getinfo$164 = Py.newCode(2, var2, var1, "getinfo", 2552, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      read$165 = Py.newCode(2, var2, var1, "read", 2554, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "arcname", "compress_type"};
      write$166 = Py.newCode(4, var2, var1, "write", 2556, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "zinfo", "bytes", "StringIO", "calendar", "tinfo"};
      writestr$167 = Py.newCode(3, var2, var1, "writestr", 2558, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$168 = Py.newCode(1, var2, var1, "close", 2568, false, false, self, 168, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "t"};
      is_tarfile$169 = Py.newCode(1, var2, var1, "is_tarfile", 2575, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new tarfile$py("tarfile$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(tarfile$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.stn$1(var2, var3);
         case 2:
            return this.nts$2(var2, var3);
         case 3:
            return this.nti$3(var2, var3);
         case 4:
            return this.itn$4(var2, var3);
         case 5:
            return this.uts$5(var2, var3);
         case 6:
            return this.calc_chksums$6(var2, var3);
         case 7:
            return this.copyfileobj$7(var2, var3);
         case 8:
            return this.filemode$8(var2, var3);
         case 9:
            return this.TarError$9(var2, var3);
         case 10:
            return this.ExtractError$10(var2, var3);
         case 11:
            return this.ReadError$11(var2, var3);
         case 12:
            return this.CompressionError$12(var2, var3);
         case 13:
            return this.StreamError$13(var2, var3);
         case 14:
            return this.HeaderError$14(var2, var3);
         case 15:
            return this.EmptyHeaderError$15(var2, var3);
         case 16:
            return this.TruncatedHeaderError$16(var2, var3);
         case 17:
            return this.EOFHeaderError$17(var2, var3);
         case 18:
            return this.InvalidHeaderError$18(var2, var3);
         case 19:
            return this.SubsequentHeaderError$19(var2, var3);
         case 20:
            return this._LowLevelFile$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.close$22(var2, var3);
         case 23:
            return this.read$23(var2, var3);
         case 24:
            return this.write$24(var2, var3);
         case 25:
            return this._Stream$25(var2, var3);
         case 26:
            return this.__init__$26(var2, var3);
         case 27:
            return this.__del__$27(var2, var3);
         case 28:
            return this._init_write_gz$28(var2, var3);
         case 29:
            return this.write$29(var2, var3);
         case 30:
            return this._Stream__write$30(var2, var3);
         case 31:
            return this.close$31(var2, var3);
         case 32:
            return this._init_read_gz$32(var2, var3);
         case 33:
            return this.tell$33(var2, var3);
         case 34:
            return this.seek$34(var2, var3);
         case 35:
            return this.read$35(var2, var3);
         case 36:
            return this._read$36(var2, var3);
         case 37:
            return this._Stream__read$37(var2, var3);
         case 38:
            return this._StreamProxy$38(var2, var3);
         case 39:
            return this.__init__$39(var2, var3);
         case 40:
            return this.read$40(var2, var3);
         case 41:
            return this.getcomptype$41(var2, var3);
         case 42:
            return this.close$42(var2, var3);
         case 43:
            return this._BZ2Proxy$43(var2, var3);
         case 44:
            return this.__init__$44(var2, var3);
         case 45:
            return this.init$45(var2, var3);
         case 46:
            return this.read$46(var2, var3);
         case 47:
            return this.seek$47(var2, var3);
         case 48:
            return this.tell$48(var2, var3);
         case 49:
            return this.write$49(var2, var3);
         case 50:
            return this.close$50(var2, var3);
         case 51:
            return this._FileInFile$51(var2, var3);
         case 52:
            return this.__init__$52(var2, var3);
         case 53:
            return this.tell$53(var2, var3);
         case 54:
            return this.seek$54(var2, var3);
         case 55:
            return this.read$55(var2, var3);
         case 56:
            return this.readnormal$56(var2, var3);
         case 57:
            return this.readsparse$57(var2, var3);
         case 58:
            return this.readsparsesection$58(var2, var3);
         case 59:
            return this.ExFileObject$59(var2, var3);
         case 60:
            return this.__init__$60(var2, var3);
         case 61:
            return this.read$61(var2, var3);
         case 62:
            return this.readline$62(var2, var3);
         case 63:
            return this.readlines$63(var2, var3);
         case 64:
            return this.tell$64(var2, var3);
         case 65:
            return this.seek$65(var2, var3);
         case 66:
            return this.close$66(var2, var3);
         case 67:
            return this.__iter__$67(var2, var3);
         case 68:
            return this.TarInfo$68(var2, var3);
         case 69:
            return this.__init__$69(var2, var3);
         case 70:
            return this._getpath$70(var2, var3);
         case 71:
            return this._setpath$71(var2, var3);
         case 72:
            return this._getlinkpath$72(var2, var3);
         case 73:
            return this._setlinkpath$73(var2, var3);
         case 74:
            return this.__repr__$74(var2, var3);
         case 75:
            return this.get_info$75(var2, var3);
         case 76:
            return this.tobuf$76(var2, var3);
         case 77:
            return this.create_ustar_header$77(var2, var3);
         case 78:
            return this.create_gnu_header$78(var2, var3);
         case 79:
            return this.create_pax_header$79(var2, var3);
         case 80:
            return this.create_pax_global_header$80(var2, var3);
         case 81:
            return this._posix_split_name$81(var2, var3);
         case 82:
            return this._create_header$82(var2, var3);
         case 83:
            return this._create_payload$83(var2, var3);
         case 84:
            return this._create_gnu_long_header$84(var2, var3);
         case 85:
            return this._create_pax_generic_header$85(var2, var3);
         case 86:
            return this.frombuf$86(var2, var3);
         case 87:
            return this.fromtarfile$87(var2, var3);
         case 88:
            return this._proc_member$88(var2, var3);
         case 89:
            return this._proc_builtin$89(var2, var3);
         case 90:
            return this._proc_gnulong$90(var2, var3);
         case 91:
            return this._proc_sparse$91(var2, var3);
         case 92:
            return this._proc_pax$92(var2, var3);
         case 93:
            return this._apply_pax_info$93(var2, var3);
         case 94:
            return this._block$94(var2, var3);
         case 95:
            return this.isreg$95(var2, var3);
         case 96:
            return this.isfile$96(var2, var3);
         case 97:
            return this.isdir$97(var2, var3);
         case 98:
            return this.issym$98(var2, var3);
         case 99:
            return this.islnk$99(var2, var3);
         case 100:
            return this.ischr$100(var2, var3);
         case 101:
            return this.isblk$101(var2, var3);
         case 102:
            return this.isfifo$102(var2, var3);
         case 103:
            return this.issparse$103(var2, var3);
         case 104:
            return this.isdev$104(var2, var3);
         case 105:
            return this.TarFile$105(var2, var3);
         case 106:
            return this.__init__$106(var2, var3);
         case 107:
            return this._getposix$107(var2, var3);
         case 108:
            return this._setposix$108(var2, var3);
         case 109:
            return this.open$109(var2, var3);
         case 110:
            return this.taropen$110(var2, var3);
         case 111:
            return this.gzopen$111(var2, var3);
         case 112:
            return this.bz2open$112(var2, var3);
         case 113:
            return this.close$113(var2, var3);
         case 114:
            return this.getmember$114(var2, var3);
         case 115:
            return this.getmembers$115(var2, var3);
         case 116:
            return this.getnames$116(var2, var3);
         case 117:
            return this.gettarinfo$117(var2, var3);
         case 118:
            return this.list$118(var2, var3);
         case 119:
            return this.add$119(var2, var3);
         case 120:
            return this.addfile$120(var2, var3);
         case 121:
            return this.extractall$121(var2, var3);
         case 122:
            return this.extract$122(var2, var3);
         case 123:
            return this.extractfile$123(var2, var3);
         case 124:
            return this._extract_member$124(var2, var3);
         case 125:
            return this.makedir$125(var2, var3);
         case 126:
            return this.makefile$126(var2, var3);
         case 127:
            return this.makeunknown$127(var2, var3);
         case 128:
            return this.makefifo$128(var2, var3);
         case 129:
            return this.makedev$129(var2, var3);
         case 130:
            return this.makelink$130(var2, var3);
         case 131:
            return this.chown$131(var2, var3);
         case 132:
            return this.chmod$132(var2, var3);
         case 133:
            return this.utime$133(var2, var3);
         case 134:
            return this.next$134(var2, var3);
         case 135:
            return this._getmember$135(var2, var3);
         case 136:
            return this._load$136(var2, var3);
         case 137:
            return this._check$137(var2, var3);
         case 138:
            return this._find_link_target$138(var2, var3);
         case 139:
            return this.__iter__$139(var2, var3);
         case 140:
            return this._dbg$140(var2, var3);
         case 141:
            return this.__enter__$141(var2, var3);
         case 142:
            return this.__exit__$142(var2, var3);
         case 143:
            return this.TarIter$143(var2, var3);
         case 144:
            return this.__init__$144(var2, var3);
         case 145:
            return this.__iter__$145(var2, var3);
         case 146:
            return this.next$146(var2, var3);
         case 147:
            return this._section$147(var2, var3);
         case 148:
            return this.__init__$148(var2, var3);
         case 149:
            return this.__contains__$149(var2, var3);
         case 150:
            return this._data$150(var2, var3);
         case 151:
            return this.__init__$151(var2, var3);
         case 152:
            return this._hole$152(var2, var3);
         case 153:
            return this._ringbuffer$153(var2, var3);
         case 154:
            return this.__init__$154(var2, var3);
         case 155:
            return this.find$155(var2, var3);
         case 156:
            return this.TarFileCompat$156(var2, var3);
         case 157:
            return this.__init__$157(var2, var3);
         case 158:
            return this.namelist$158(var2, var3);
         case 159:
            return this.f$159(var2, var3);
         case 160:
            return this.infolist$160(var2, var3);
         case 161:
            return this.f$161(var2, var3);
         case 162:
            return this.printdir$162(var2, var3);
         case 163:
            return this.testzip$163(var2, var3);
         case 164:
            return this.getinfo$164(var2, var3);
         case 165:
            return this.read$165(var2, var3);
         case 166:
            return this.write$166(var2, var3);
         case 167:
            return this.writestr$167(var2, var3);
         case 168:
            return this.close$168(var2, var3);
         case 169:
            return this.is_tarfile$169(var2, var3);
         default:
            return null;
      }
   }
}
