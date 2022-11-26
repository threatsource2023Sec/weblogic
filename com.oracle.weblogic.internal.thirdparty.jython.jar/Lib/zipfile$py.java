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
@Filename("zipfile.py")
public class zipfile$py extends PyFunctionTable implements PyRunnable {
   static zipfile$py self;
   static final PyCode f$0;
   static final PyCode BadZipfile$1;
   static final PyCode LargeZipFile$2;
   static final PyCode _check_zipfile$3;
   static final PyCode is_zipfile$4;
   static final PyCode _EndRecData64$5;
   static final PyCode _EndRecData$6;
   static final PyCode ZipInfo$7;
   static final PyCode __init__$8;
   static final PyCode FileHeader$9;
   static final PyCode _encodeFilenameFlags$10;
   static final PyCode _decodeFilename$11;
   static final PyCode _decodeExtra$12;
   static final PyCode _ZipDecrypter$13;
   static final PyCode _GenerateCRCTable$14;
   static final PyCode _crc32$15;
   static final PyCode __init__$16;
   static final PyCode _UpdateKeys$17;
   static final PyCode __call__$18;
   static final PyCode ZipExtFile$19;
   static final PyCode __init__$20;
   static final PyCode readline$21;
   static final PyCode peek$22;
   static final PyCode readable$23;
   static final PyCode read$24;
   static final PyCode _update_crc$25;
   static final PyCode read1$26;
   static final PyCode close$27;
   static final PyCode ZipFile$28;
   static final PyCode __init__$29;
   static final PyCode __enter__$30;
   static final PyCode __exit__$31;
   static final PyCode _RealGetContents$32;
   static final PyCode namelist$33;
   static final PyCode infolist$34;
   static final PyCode printdir$35;
   static final PyCode testzip$36;
   static final PyCode getinfo$37;
   static final PyCode setpassword$38;
   static final PyCode comment$39;
   static final PyCode comment$40;
   static final PyCode read$41;
   static final PyCode open$42;
   static final PyCode extract$43;
   static final PyCode extractall$44;
   static final PyCode _extract_member$45;
   static final PyCode f$46;
   static final PyCode f$47;
   static final PyCode f$48;
   static final PyCode _writecheck$49;
   static final PyCode write$50;
   static final PyCode writestr$51;
   static final PyCode __del__$52;
   static final PyCode close$53;
   static final PyCode PyZipFile$54;
   static final PyCode writepy$55;
   static final PyCode _get_codename$56;
   static final PyCode main$57;
   static final PyCode addToZip$58;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nRead and write ZIP files.\n"));
      var1.setline(3);
      PyString.fromInterned("\nRead and write ZIP files.\n");
      var1.setline(4);
      PyObject var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var3 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("binascii", var1, -1);
      var1.setlocal("binascii", var3);
      var3 = null;
      var3 = imp.importOne("cStringIO", var1, -1);
      var1.setlocal("cStringIO", var3);
      var3 = null;
      var3 = imp.importOne("stat", var1, -1);
      var1.setlocal("stat", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("io", var1, -1);
      var1.setlocal("io", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var3);
      var3 = null;
      var1.setline(10);
      var3 = var1.getname("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("java"));
      var3 = null;
      var3 = var10000;
      var1.setlocal("_is_jython", var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(13);
         var3 = imp.importOne("zlib", var1, -1);
         var1.setlocal("zlib", var3);
         var3 = null;
         var1.setline(14);
         var3 = var1.getname("zlib").__getattr__("crc32");
         var1.setlocal("crc32", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getname("ImportError"))) {
            throw var6;
         }

         var1.setline(16);
         var4 = var1.getname("None");
         var1.setlocal("zlib", var4);
         var4 = null;
         var1.setline(17);
         var4 = var1.getname("binascii").__getattr__("crc32");
         var1.setlocal("crc32", var4);
         var4 = null;
      }

      var1.setline(19);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("BadZipfile"), PyString.fromInterned("error"), PyString.fromInterned("ZIP_STORED"), PyString.fromInterned("ZIP_DEFLATED"), PyString.fromInterned("is_zipfile"), PyString.fromInterned("ZipInfo"), PyString.fromInterned("ZipFile"), PyString.fromInterned("PyZipFile"), PyString.fromInterned("LargeZipFile")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(22);
      PyObject[] var8 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("BadZipfile", var8, BadZipfile$1);
      var1.setlocal("BadZipfile", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(26);
      var8 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("LargeZipFile", var8, LargeZipFile$2);
      var1.setlocal("LargeZipFile", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(32);
      var3 = var1.getname("BadZipfile");
      var1.setlocal("error", var3);
      var3 = null;
      var1.setline(34);
      var3 = Py.newInteger(1)._lshift(Py.newInteger(31))._sub(Py.newInteger(1));
      var1.setlocal("ZIP64_LIMIT", var3);
      var3 = null;
      var1.setline(35);
      var3 = Py.newInteger(1)._lshift(Py.newInteger(16));
      var1.setlocal("ZIP_FILECOUNT_LIMIT", var3);
      var3 = null;
      var1.setline(36);
      var3 = Py.newInteger(1)._lshift(Py.newInteger(16))._sub(Py.newInteger(1));
      var1.setlocal("ZIP_MAX_COMMENT", var3);
      var3 = null;
      var1.setline(39);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal("ZIP_STORED", var9);
      var3 = null;
      var1.setline(40);
      var9 = Py.newInteger(8);
      var1.setlocal("ZIP_DEFLATED", var9);
      var3 = null;
      var1.setline(51);
      PyString var10 = PyString.fromInterned("<4s4H2LH");
      var1.setlocal("structEndArchive", var10);
      var3 = null;
      var1.setline(52);
      var10 = PyString.fromInterned("PK\u0005\u0006");
      var1.setlocal("stringEndArchive", var10);
      var3 = null;
      var1.setline(53);
      var3 = var1.getname("struct").__getattr__("calcsize").__call__(var2, var1.getname("structEndArchive"));
      var1.setlocal("sizeEndCentDir", var3);
      var3 = null;
      var1.setline(55);
      var9 = Py.newInteger(0);
      var1.setlocal("_ECD_SIGNATURE", var9);
      var3 = null;
      var1.setline(56);
      var9 = Py.newInteger(1);
      var1.setlocal("_ECD_DISK_NUMBER", var9);
      var3 = null;
      var1.setline(57);
      var9 = Py.newInteger(2);
      var1.setlocal("_ECD_DISK_START", var9);
      var3 = null;
      var1.setline(58);
      var9 = Py.newInteger(3);
      var1.setlocal("_ECD_ENTRIES_THIS_DISK", var9);
      var3 = null;
      var1.setline(59);
      var9 = Py.newInteger(4);
      var1.setlocal("_ECD_ENTRIES_TOTAL", var9);
      var3 = null;
      var1.setline(60);
      var9 = Py.newInteger(5);
      var1.setlocal("_ECD_SIZE", var9);
      var3 = null;
      var1.setline(61);
      var9 = Py.newInteger(6);
      var1.setlocal("_ECD_OFFSET", var9);
      var3 = null;
      var1.setline(62);
      var9 = Py.newInteger(7);
      var1.setlocal("_ECD_COMMENT_SIZE", var9);
      var3 = null;
      var1.setline(65);
      var9 = Py.newInteger(8);
      var1.setlocal("_ECD_COMMENT", var9);
      var3 = null;
      var1.setline(66);
      var9 = Py.newInteger(9);
      var1.setlocal("_ECD_LOCATION", var9);
      var3 = null;
      var1.setline(70);
      var10 = PyString.fromInterned("<4s4B4HL2L5H2L");
      var1.setlocal("structCentralDir", var10);
      var3 = null;
      var1.setline(71);
      var10 = PyString.fromInterned("PK\u0001\u0002");
      var1.setlocal("stringCentralDir", var10);
      var3 = null;
      var1.setline(72);
      var3 = var1.getname("struct").__getattr__("calcsize").__call__(var2, var1.getname("structCentralDir"));
      var1.setlocal("sizeCentralDir", var3);
      var3 = null;
      var1.setline(75);
      var9 = Py.newInteger(0);
      var1.setlocal("_CD_SIGNATURE", var9);
      var3 = null;
      var1.setline(76);
      var9 = Py.newInteger(1);
      var1.setlocal("_CD_CREATE_VERSION", var9);
      var3 = null;
      var1.setline(77);
      var9 = Py.newInteger(2);
      var1.setlocal("_CD_CREATE_SYSTEM", var9);
      var3 = null;
      var1.setline(78);
      var9 = Py.newInteger(3);
      var1.setlocal("_CD_EXTRACT_VERSION", var9);
      var3 = null;
      var1.setline(79);
      var9 = Py.newInteger(4);
      var1.setlocal("_CD_EXTRACT_SYSTEM", var9);
      var3 = null;
      var1.setline(80);
      var9 = Py.newInteger(5);
      var1.setlocal("_CD_FLAG_BITS", var9);
      var3 = null;
      var1.setline(81);
      var9 = Py.newInteger(6);
      var1.setlocal("_CD_COMPRESS_TYPE", var9);
      var3 = null;
      var1.setline(82);
      var9 = Py.newInteger(7);
      var1.setlocal("_CD_TIME", var9);
      var3 = null;
      var1.setline(83);
      var9 = Py.newInteger(8);
      var1.setlocal("_CD_DATE", var9);
      var3 = null;
      var1.setline(84);
      var9 = Py.newInteger(9);
      var1.setlocal("_CD_CRC", var9);
      var3 = null;
      var1.setline(85);
      var9 = Py.newInteger(10);
      var1.setlocal("_CD_COMPRESSED_SIZE", var9);
      var3 = null;
      var1.setline(86);
      var9 = Py.newInteger(11);
      var1.setlocal("_CD_UNCOMPRESSED_SIZE", var9);
      var3 = null;
      var1.setline(87);
      var9 = Py.newInteger(12);
      var1.setlocal("_CD_FILENAME_LENGTH", var9);
      var3 = null;
      var1.setline(88);
      var9 = Py.newInteger(13);
      var1.setlocal("_CD_EXTRA_FIELD_LENGTH", var9);
      var3 = null;
      var1.setline(89);
      var9 = Py.newInteger(14);
      var1.setlocal("_CD_COMMENT_LENGTH", var9);
      var3 = null;
      var1.setline(90);
      var9 = Py.newInteger(15);
      var1.setlocal("_CD_DISK_NUMBER_START", var9);
      var3 = null;
      var1.setline(91);
      var9 = Py.newInteger(16);
      var1.setlocal("_CD_INTERNAL_FILE_ATTRIBUTES", var9);
      var3 = null;
      var1.setline(92);
      var9 = Py.newInteger(17);
      var1.setlocal("_CD_EXTERNAL_FILE_ATTRIBUTES", var9);
      var3 = null;
      var1.setline(93);
      var9 = Py.newInteger(18);
      var1.setlocal("_CD_LOCAL_HEADER_OFFSET", var9);
      var3 = null;
      var1.setline(97);
      var10 = PyString.fromInterned("<4s2B4HL2L2H");
      var1.setlocal("structFileHeader", var10);
      var3 = null;
      var1.setline(98);
      var10 = PyString.fromInterned("PK\u0003\u0004");
      var1.setlocal("stringFileHeader", var10);
      var3 = null;
      var1.setline(99);
      var3 = var1.getname("struct").__getattr__("calcsize").__call__(var2, var1.getname("structFileHeader"));
      var1.setlocal("sizeFileHeader", var3);
      var3 = null;
      var1.setline(101);
      var9 = Py.newInteger(0);
      var1.setlocal("_FH_SIGNATURE", var9);
      var3 = null;
      var1.setline(102);
      var9 = Py.newInteger(1);
      var1.setlocal("_FH_EXTRACT_VERSION", var9);
      var3 = null;
      var1.setline(103);
      var9 = Py.newInteger(2);
      var1.setlocal("_FH_EXTRACT_SYSTEM", var9);
      var3 = null;
      var1.setline(104);
      var9 = Py.newInteger(3);
      var1.setlocal("_FH_GENERAL_PURPOSE_FLAG_BITS", var9);
      var3 = null;
      var1.setline(105);
      var9 = Py.newInteger(4);
      var1.setlocal("_FH_COMPRESSION_METHOD", var9);
      var3 = null;
      var1.setline(106);
      var9 = Py.newInteger(5);
      var1.setlocal("_FH_LAST_MOD_TIME", var9);
      var3 = null;
      var1.setline(107);
      var9 = Py.newInteger(6);
      var1.setlocal("_FH_LAST_MOD_DATE", var9);
      var3 = null;
      var1.setline(108);
      var9 = Py.newInteger(7);
      var1.setlocal("_FH_CRC", var9);
      var3 = null;
      var1.setline(109);
      var9 = Py.newInteger(8);
      var1.setlocal("_FH_COMPRESSED_SIZE", var9);
      var3 = null;
      var1.setline(110);
      var9 = Py.newInteger(9);
      var1.setlocal("_FH_UNCOMPRESSED_SIZE", var9);
      var3 = null;
      var1.setline(111);
      var9 = Py.newInteger(10);
      var1.setlocal("_FH_FILENAME_LENGTH", var9);
      var3 = null;
      var1.setline(112);
      var9 = Py.newInteger(11);
      var1.setlocal("_FH_EXTRA_FIELD_LENGTH", var9);
      var3 = null;
      var1.setline(115);
      var10 = PyString.fromInterned("<4sLQL");
      var1.setlocal("structEndArchive64Locator", var10);
      var3 = null;
      var1.setline(116);
      var10 = PyString.fromInterned("PK\u0006\u0007");
      var1.setlocal("stringEndArchive64Locator", var10);
      var3 = null;
      var1.setline(117);
      var3 = var1.getname("struct").__getattr__("calcsize").__call__(var2, var1.getname("structEndArchive64Locator"));
      var1.setlocal("sizeEndCentDir64Locator", var3);
      var3 = null;
      var1.setline(121);
      var10 = PyString.fromInterned("<4sQ2H2L4Q");
      var1.setlocal("structEndArchive64", var10);
      var3 = null;
      var1.setline(122);
      var10 = PyString.fromInterned("PK\u0006\u0006");
      var1.setlocal("stringEndArchive64", var10);
      var3 = null;
      var1.setline(123);
      var3 = var1.getname("struct").__getattr__("calcsize").__call__(var2, var1.getname("structEndArchive64"));
      var1.setlocal("sizeEndCentDir64", var3);
      var3 = null;
      var1.setline(125);
      var9 = Py.newInteger(0);
      var1.setlocal("_CD64_SIGNATURE", var9);
      var3 = null;
      var1.setline(126);
      var9 = Py.newInteger(1);
      var1.setlocal("_CD64_DIRECTORY_RECSIZE", var9);
      var3 = null;
      var1.setline(127);
      var9 = Py.newInteger(2);
      var1.setlocal("_CD64_CREATE_VERSION", var9);
      var3 = null;
      var1.setline(128);
      var9 = Py.newInteger(3);
      var1.setlocal("_CD64_EXTRACT_VERSION", var9);
      var3 = null;
      var1.setline(129);
      var9 = Py.newInteger(4);
      var1.setlocal("_CD64_DISK_NUMBER", var9);
      var3 = null;
      var1.setline(130);
      var9 = Py.newInteger(5);
      var1.setlocal("_CD64_DISK_NUMBER_START", var9);
      var3 = null;
      var1.setline(131);
      var9 = Py.newInteger(6);
      var1.setlocal("_CD64_NUMBER_ENTRIES_THIS_DISK", var9);
      var3 = null;
      var1.setline(132);
      var9 = Py.newInteger(7);
      var1.setlocal("_CD64_NUMBER_ENTRIES_TOTAL", var9);
      var3 = null;
      var1.setline(133);
      var9 = Py.newInteger(8);
      var1.setlocal("_CD64_DIRECTORY_SIZE", var9);
      var3 = null;
      var1.setline(134);
      var9 = Py.newInteger(9);
      var1.setlocal("_CD64_OFFSET_START_CENTDIR", var9);
      var3 = null;
      var1.setline(136);
      var8 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var8, _check_zipfile$3, (PyObject)null);
      var1.setlocal("_check_zipfile", var11);
      var3 = null;
      var1.setline(144);
      var8 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var8, is_zipfile$4, PyString.fromInterned("Quickly see if a file is a ZIP file by checking the magic number.\n\n    The filename argument may be a file or file-like object too.\n    "));
      var1.setlocal("is_zipfile", var11);
      var3 = null;
      var1.setline(160);
      var8 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var8, _EndRecData64$5, PyString.fromInterned("\n    Read the ZIP64 end-of-archive records and use that to update endrec\n    "));
      var1.setlocal("_EndRecData64", var11);
      var3 = null;
      var1.setline(203);
      var8 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var8, _EndRecData$6, PyString.fromInterned("Return data from the \"End of Central Directory\" record, or None.\n\n    The data is a list of the nine items in the ZIP \"End of central dir\"\n    record followed by a tenth item, the file seek offset of this record."));
      var1.setlocal("_EndRecData", var11);
      var3 = null;
      var1.setline(264);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ZipInfo", var8, ZipInfo$7);
      var1.setlocal("ZipInfo", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(422);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("_ZipDecrypter", var8, _ZipDecrypter$13);
      var1.setlocal("_ZipDecrypter", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(482);
      PyDictionary var12 = new PyDictionary(new PyObject[]{Py.newInteger(0), PyString.fromInterned("store"), Py.newInteger(1), PyString.fromInterned("shrink"), Py.newInteger(2), PyString.fromInterned("reduce"), Py.newInteger(3), PyString.fromInterned("reduce"), Py.newInteger(4), PyString.fromInterned("reduce"), Py.newInteger(5), PyString.fromInterned("reduce"), Py.newInteger(6), PyString.fromInterned("implode"), Py.newInteger(7), PyString.fromInterned("tokenize"), Py.newInteger(8), PyString.fromInterned("deflate"), Py.newInteger(9), PyString.fromInterned("deflate64"), Py.newInteger(10), PyString.fromInterned("implode"), Py.newInteger(12), PyString.fromInterned("bzip2"), Py.newInteger(14), PyString.fromInterned("lzma"), Py.newInteger(18), PyString.fromInterned("terse"), Py.newInteger(19), PyString.fromInterned("lz77"), Py.newInteger(97), PyString.fromInterned("wavpack"), Py.newInteger(98), PyString.fromInterned("ppmd")});
      var1.setlocal("compressor_names", var12);
      var3 = null;
      var1.setline(503);
      var8 = new PyObject[]{var1.getname("io").__getattr__("BufferedIOBase")};
      var4 = Py.makeClass("ZipExtFile", var8, ZipExtFile$19);
      var1.setlocal("ZipExtFile", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(707);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ZipFile", var8, ZipFile$28);
      var1.setlocal("ZipFile", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1352);
      var8 = new PyObject[]{var1.getname("ZipFile")};
      var4 = Py.makeClass("PyZipFile", var8, PyZipFile$54);
      var1.setlocal("PyZipFile", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1455);
      var8 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var8, main$57, (PyObject)null);
      var1.setlocal("main", var11);
      var3 = null;
      var1.setline(1525);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1526);
         var1.getname("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BadZipfile$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(23);
      return var1.getf_locals();
   }

   public PyObject LargeZipFile$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raised when writing a zipfile, the zipfile requires ZIP64 extensions\n    and those extensions are disabled.\n    "));
      var1.setline(30);
      PyString.fromInterned("\n    Raised when writing a zipfile, the zipfile requires ZIP64 extensions\n    and those extensions are disabled.\n    ");
      return var1.getf_locals();
   }

   public PyObject _check_zipfile$3(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(138);
         if (var1.getglobal("_EndRecData").__call__(var2, var1.getlocal(0)).__nonzero__()) {
            var1.setline(139);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (!var4.match(var1.getglobal("IOError"))) {
            throw var4;
         }

         var1.setline(141);
      }

      var1.setline(142);
      var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_zipfile$4(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(148);
      PyString.fromInterned("Quickly see if a file is a ZIP file by checking the magic number.\n\n    The filename argument may be a file or file-like object too.\n    ");
      var1.setline(149);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(151);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("read")).__nonzero__()) {
            var1.setline(152);
            PyObject var10000 = var1.getglobal("_check_zipfile");
            PyObject[] var9 = new PyObject[]{var1.getlocal(0)};
            String[] var10 = new String[]{"fp"};
            var10000 = var10000.__call__(var2, var9, var10);
            var3 = null;
            var3 = var10000;
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            label37: {
               ContextManager var8;
               PyObject var4 = (var8 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb")))).__enter__(var2);

               try {
                  var1.setlocal(2, var4);
                  var1.setline(155);
                  var4 = var1.getglobal("_check_zipfile").__call__(var2, var1.getlocal(2));
                  var1.setlocal(1, var4);
                  var4 = null;
               } catch (Throwable var5) {
                  if (var8.__exit__(var2, Py.setException(var5, var1))) {
                     break label37;
                  }

                  throw (Throwable)Py.makeException();
               }

               var8.__exit__(var2, (PyException)null);
            }
         }
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getglobal("IOError"))) {
            throw var7;
         }

         var1.setline(157);
      }

      var1.setline(158);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _EndRecData64$5(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyString.fromInterned("\n    Read the ZIP64 end-of-archive records and use that to update endrec\n    ");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(165);
         var1.getlocal(0).__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(1)._sub(var1.getglobal("sizeEndCentDir64Locator")), (PyObject)Py.newInteger(2));
      } catch (Throwable var7) {
         var3 = Py.setException(var7, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var1.setline(169);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(171);
      PyObject var8 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getglobal("sizeEndCentDir64Locator"));
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(172);
      var8 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var8._ne(var1.getglobal("sizeEndCentDir64Locator"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(173);
         var4 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(174);
         var8 = var1.getglobal("struct").__getattr__("unpack").__call__(var2, var1.getglobal("structEndArchive64Locator"), var1.getlocal(3));
         PyObject[] var5 = Py.unpackSequence(var8, 4);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(7, var6);
         var6 = null;
         var3 = null;
         var1.setline(175);
         var8 = var1.getlocal(4);
         var10000 = var8._ne(var1.getglobal("stringEndArchive64Locator"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(176);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(178);
            var8 = var1.getlocal(5);
            var10000 = var8._ne(Py.newInteger(0));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var8 = var1.getlocal(7);
               var10000 = var8._ne(Py.newInteger(1));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(179);
               throw Py.makeException(var1.getglobal("BadZipfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("zipfiles that span multiple disks are not supported")));
            } else {
               var1.setline(182);
               var1.getlocal(0).__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(1)._sub(var1.getglobal("sizeEndCentDir64Locator"))._sub(var1.getglobal("sizeEndCentDir64")), (PyObject)Py.newInteger(2));
               var1.setline(183);
               var8 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getglobal("sizeEndCentDir64"));
               var1.setlocal(3, var8);
               var3 = null;
               var1.setline(184);
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
               var10000 = var8._ne(var1.getglobal("sizeEndCentDir64"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(185);
                  var4 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var4;
               } else {
                  var1.setline(186);
                  var8 = var1.getglobal("struct").__getattr__("unpack").__call__(var2, var1.getglobal("structEndArchive64"), var1.getlocal(3));
                  var5 = Py.unpackSequence(var8, 10);
                  var6 = var5[0];
                  var1.setlocal(4, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(8, var6);
                  var6 = null;
                  var6 = var5[2];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var5[3];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var6 = var5[4];
                  var1.setlocal(11, var6);
                  var6 = null;
                  var6 = var5[5];
                  var1.setlocal(12, var6);
                  var6 = null;
                  var6 = var5[6];
                  var1.setlocal(13, var6);
                  var6 = null;
                  var6 = var5[7];
                  var1.setlocal(14, var6);
                  var6 = null;
                  var6 = var5[8];
                  var1.setlocal(15, var6);
                  var6 = null;
                  var6 = var5[9];
                  var1.setlocal(16, var6);
                  var6 = null;
                  var3 = null;
                  var1.setline(189);
                  var8 = var1.getlocal(4);
                  var10000 = var8._ne(var1.getglobal("stringEndArchive64"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(190);
                     var4 = var1.getlocal(2);
                     var1.f_lasti = -1;
                     return var4;
                  } else {
                     var1.setline(193);
                     var8 = var1.getlocal(4);
                     var1.getlocal(2).__setitem__(var1.getglobal("_ECD_SIGNATURE"), var8);
                     var3 = null;
                     var1.setline(194);
                     var8 = var1.getlocal(11);
                     var1.getlocal(2).__setitem__(var1.getglobal("_ECD_DISK_NUMBER"), var8);
                     var3 = null;
                     var1.setline(195);
                     var8 = var1.getlocal(12);
                     var1.getlocal(2).__setitem__(var1.getglobal("_ECD_DISK_START"), var8);
                     var3 = null;
                     var1.setline(196);
                     var8 = var1.getlocal(13);
                     var1.getlocal(2).__setitem__(var1.getglobal("_ECD_ENTRIES_THIS_DISK"), var8);
                     var3 = null;
                     var1.setline(197);
                     var8 = var1.getlocal(14);
                     var1.getlocal(2).__setitem__(var1.getglobal("_ECD_ENTRIES_TOTAL"), var8);
                     var3 = null;
                     var1.setline(198);
                     var8 = var1.getlocal(15);
                     var1.getlocal(2).__setitem__(var1.getglobal("_ECD_SIZE"), var8);
                     var3 = null;
                     var1.setline(199);
                     var8 = var1.getlocal(16);
                     var1.getlocal(2).__setitem__(var1.getglobal("_ECD_OFFSET"), var8);
                     var3 = null;
                     var1.setline(200);
                     var4 = var1.getlocal(2);
                     var1.f_lasti = -1;
                     return var4;
                  }
               }
            }
         }
      }
   }

   public PyObject _EndRecData$6(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyString.fromInterned("Return data from the \"End of Central Directory\" record, or None.\n\n    The data is a list of the nine items in the ZIP \"End of central dir\"\n    record followed by a tenth item, the file seek offset of this record.");
      var1.setline(210);
      var1.getlocal(0).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(211);
      PyObject var3 = var1.getlocal(0).__getattr__("tell").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(217);
         var1.getlocal(0).__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getglobal("sizeEndCentDir").__neg__(), (PyObject)Py.newInteger(2));
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(var1.getglobal("IOError"))) {
            var1.setline(219);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var6;
      }

      var1.setline(220);
      var3 = var1.getlocal(0).__getattr__("read").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(221);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(var1.getglobal("sizeEndCentDir"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(4), (PyObject)null);
         var10000 = var3._eq(var1.getglobal("stringEndArchive"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(2).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("\u0000\u0000"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(225);
         var3 = var1.getglobal("struct").__getattr__("unpack").__call__(var2, var1.getglobal("structEndArchive"), var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(226);
         var3 = var1.getglobal("list").__call__(var2, var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(229);
         var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
         var1.setline(230);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(1)._sub(var1.getglobal("sizeEndCentDir")));
         var1.setline(233);
         var4 = var1.getglobal("_EndRecData64").__call__(var2, var1.getlocal(0), var1.getglobal("sizeEndCentDir").__neg__(), var1.getlocal(3));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(240);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(1)._sub(Py.newInteger(1)._lshift(Py.newInteger(16)))._sub(var1.getglobal("sizeEndCentDir")), (PyObject)Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(241);
         var1.getlocal(0).__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0));
         var1.setline(242);
         var3 = var1.getlocal(0).__getattr__("read").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(243);
         var3 = var1.getlocal(2).__getattr__("rfind").__call__(var2, var1.getglobal("stringEndArchive"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(244);
         var3 = var1.getlocal(5);
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(246);
            var3 = var1.getlocal(2).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(var1.getglobal("sizeEndCentDir")), (PyObject)null);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(247);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
            var10000 = var3._ne(var1.getglobal("sizeEndCentDir"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(249);
               var4 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var4;
            } else {
               var1.setline(250);
               var3 = var1.getglobal("list").__call__(var2, var1.getglobal("struct").__getattr__("unpack").__call__(var2, var1.getglobal("structEndArchive"), var1.getlocal(6)));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(251);
               var3 = var1.getlocal(3).__getitem__(var1.getglobal("_ECD_COMMENT_SIZE"));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(252);
               var3 = var1.getlocal(2).__getslice__(var1.getlocal(5)._add(var1.getglobal("sizeEndCentDir")), var1.getlocal(5)._add(var1.getglobal("sizeEndCentDir"))._add(var1.getlocal(7)), (PyObject)null);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(253);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(8));
               var1.setline(254);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4)._add(var1.getlocal(5)));
               var1.setline(257);
               var4 = var1.getglobal("_EndRecData64").__call__(var2, var1.getlocal(0), var1.getlocal(4)._add(var1.getlocal(5))._sub(var1.getlocal(1)), var1.getlocal(3));
               var1.f_lasti = -1;
               return var4;
            }
         } else {
            var1.setline(261);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }
      }
   }

   public PyObject ZipInfo$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class with attributes describing each file in the ZIP archive."));
      var1.setline(265);
      PyString.fromInterned("Class with attributes describing each file in the ZIP archive.");
      var1.setline(267);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("orig_filename"), PyString.fromInterned("filename"), PyString.fromInterned("date_time"), PyString.fromInterned("compress_type"), PyString.fromInterned("comment"), PyString.fromInterned("extra"), PyString.fromInterned("create_system"), PyString.fromInterned("create_version"), PyString.fromInterned("extract_version"), PyString.fromInterned("reserved"), PyString.fromInterned("flag_bits"), PyString.fromInterned("volume"), PyString.fromInterned("internal_attr"), PyString.fromInterned("external_attr"), PyString.fromInterned("header_offset"), PyString.fromInterned("CRC"), PyString.fromInterned("compress_size"), PyString.fromInterned("file_size"), PyString.fromInterned("_raw_time")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(289);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("NoName"), new PyTuple(new PyObject[]{Py.newInteger(1980), Py.newInteger(1), Py.newInteger(1), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)})};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(331);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, FileHeader$9, PyString.fromInterned("Return the per-file header as a string."));
      var1.setlocal("FileHeader", var5);
      var3 = null;
      var1.setline(370);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _encodeFilenameFlags$10, (PyObject)null);
      var1.setlocal("_encodeFilenameFlags", var5);
      var3 = null;
      var1.setline(379);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _decodeFilename$11, (PyObject)null);
      var1.setlocal("_decodeFilename", var5);
      var3 = null;
      var1.setline(385);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _decodeExtra$12, (PyObject)null);
      var1.setlocal("_decodeExtra", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("orig_filename", var3);
      var3 = null;
      var1.setline(294);
      var3 = var1.getlocal(1).__getattr__("find").__call__(var2, var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(295);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(296);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(0), var1.getlocal(3), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(300);
      var3 = var1.getglobal("os").__getattr__("sep");
      var10000 = var3._ne(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("os").__getattr__("sep");
         var10000 = var3._in(var1.getlocal(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(301);
         var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("sep"), (PyObject)PyString.fromInterned("/"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(303);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(304);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("date_time", var3);
      var3 = null;
      var1.setline(306);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      var10000 = var3._lt(Py.newInteger(1980));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(307);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ZIP does not support timestamps before 1980")));
      } else {
         var1.setline(310);
         var3 = var1.getglobal("ZIP_STORED");
         var1.getlocal(0).__setattr__("compress_type", var3);
         var3 = null;
         var1.setline(311);
         PyString var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"comment", var4);
         var3 = null;
         var1.setline(312);
         var4 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"extra", var4);
         var3 = null;
         var1.setline(313);
         var3 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var3._eq(PyString.fromInterned("win32"));
         var3 = null;
         PyInteger var5;
         if (var10000.__nonzero__()) {
            var1.setline(314);
            var5 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"create_system", var5);
            var3 = null;
         } else {
            var1.setline(317);
            var5 = Py.newInteger(3);
            var1.getlocal(0).__setattr__((String)"create_system", var5);
            var3 = null;
         }

         var1.setline(318);
         var5 = Py.newInteger(20);
         var1.getlocal(0).__setattr__((String)"create_version", var5);
         var3 = null;
         var1.setline(319);
         var5 = Py.newInteger(20);
         var1.getlocal(0).__setattr__((String)"extract_version", var5);
         var3 = null;
         var1.setline(320);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"reserved", var5);
         var3 = null;
         var1.setline(321);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"flag_bits", var5);
         var3 = null;
         var1.setline(322);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"volume", var5);
         var3 = null;
         var1.setline(323);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"internal_attr", var5);
         var3 = null;
         var1.setline(324);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"external_attr", var5);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject FileHeader$9(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyString.fromInterned("Return the per-file header as a string.");
      var1.setline(333);
      PyObject var3 = var1.getlocal(0).__getattr__("date_time");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(334);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(0))._sub(Py.newInteger(1980))._lshift(Py.newInteger(9))._or(var1.getlocal(2).__getitem__(Py.newInteger(1))._lshift(Py.newInteger(5)))._or(var1.getlocal(2).__getitem__(Py.newInteger(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(335);
      var3 = var1.getlocal(2).__getitem__(Py.newInteger(3))._lshift(Py.newInteger(11))._or(var1.getlocal(2).__getitem__(Py.newInteger(4))._lshift(Py.newInteger(5)))._or(var1.getlocal(2).__getitem__(Py.newInteger(5))._floordiv(Py.newInteger(2)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(336);
      if (var1.getlocal(0).__getattr__("flag_bits")._and(Py.newInteger(8)).__nonzero__()) {
         var1.setline(338);
         PyInteger var6 = Py.newInteger(0);
         var1.setlocal(5, var6);
         var1.setlocal(6, var6);
         var1.setlocal(7, var6);
      } else {
         var1.setline(340);
         var3 = var1.getlocal(0).__getattr__("CRC");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(341);
         var3 = var1.getlocal(0).__getattr__("compress_size");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(342);
         var3 = var1.getlocal(0).__getattr__("file_size");
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(344);
      var3 = var1.getlocal(0).__getattr__("extra");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(346);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(347);
         var3 = var1.getlocal(7);
         var10000 = var3._gt(var1.getglobal("ZIP64_LIMIT"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(6);
            var10000 = var3._gt(var1.getglobal("ZIP64_LIMIT"));
            var3 = null;
         }

         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(348);
      PyObject[] var8;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(349);
         PyString var7 = PyString.fromInterned("<HHQQ");
         var1.setlocal(9, var7);
         var3 = null;
         var1.setline(350);
         var10000 = var1.getlocal(8);
         PyObject var10001 = var1.getglobal("struct").__getattr__("pack");
         var8 = new PyObject[]{var1.getlocal(9), Py.newInteger(1), var1.getglobal("struct").__getattr__("calcsize").__call__(var2, var1.getlocal(9))._sub(Py.newInteger(4)), var1.getlocal(7), var1.getlocal(6)};
         var3 = var10000._add(var10001.__call__(var2, var8));
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(352);
      var3 = var1.getlocal(7);
      var10000 = var3._gt(var1.getglobal("ZIP64_LIMIT"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(6);
         var10000 = var3._gt(var1.getglobal("ZIP64_LIMIT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(353);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(354);
            throw Py.makeException(var1.getglobal("LargeZipFile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Filesize would require ZIP64 extensions")));
         }

         var1.setline(357);
         PyLong var9 = Py.newLong("4294967295");
         var1.setlocal(7, var9);
         var3 = null;
         var1.setline(358);
         var9 = Py.newLong("4294967295");
         var1.setlocal(6, var9);
         var3 = null;
         var1.setline(359);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(45), (PyObject)var1.getlocal(0).__getattr__("extract_version"));
         var1.getlocal(0).__setattr__("extract_version", var3);
         var3 = null;
         var1.setline(360);
         var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(45), (PyObject)var1.getlocal(0).__getattr__("extract_version"));
         var1.getlocal(0).__setattr__("create_version", var3);
         var3 = null;
      }

      var1.setline(362);
      var3 = var1.getlocal(0).__getattr__("_encodeFilenameFlags").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(11, var5);
      var5 = null;
      var3 = null;
      var1.setline(363);
      var10000 = var1.getglobal("struct").__getattr__("pack");
      var8 = new PyObject[]{var1.getglobal("structFileHeader"), var1.getglobal("stringFileHeader"), var1.getlocal(0).__getattr__("extract_version"), var1.getlocal(0).__getattr__("reserved"), var1.getlocal(11), var1.getlocal(0).__getattr__("compress_type"), var1.getlocal(4), var1.getlocal(3), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getglobal("len").__call__(var2, var1.getlocal(10)), var1.getglobal("len").__call__(var2, var1.getlocal(8))};
      var3 = var10000.__call__(var2, var8);
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(368);
      var3 = var1.getlocal(12)._add(var1.getlocal(10))._add(var1.getlocal(8));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _encodeFilenameFlags$10(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      PyTuple var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("filename"), var1.getglobal("unicode")).__nonzero__()) {
         try {
            var1.setline(373);
            var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("filename").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ascii")), var1.getlocal(0).__getattr__("flag_bits")});
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("UnicodeEncodeError"))) {
               var1.setline(375);
               var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("filename").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8")), var1.getlocal(0).__getattr__("flag_bits")._or(Py.newInteger(2048))});
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var4;
            }
         }
      } else {
         var1.setline(377);
         var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("flag_bits")});
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _decodeFilename$11(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("flag_bits")._and(Py.newInteger(2048)).__nonzero__()) {
         var1.setline(381);
         var3 = var1.getlocal(0).__getattr__("filename").__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(383);
         var3 = var1.getlocal(0).__getattr__("filename");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _decodeExtra$12(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      PyObject var3 = var1.getlocal(0).__getattr__("extra");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(388);
      var3 = var1.getglobal("struct").__getattr__("unpack");
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(389);
         if (!var1.getlocal(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(390);
         var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<HH"), (PyObject)var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(391);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(392);
            var3 = var1.getlocal(4);
            var10000 = var3._ge(Py.newInteger(24));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(393);
               var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<QQQ"), (PyObject)var1.getlocal(1).__getslice__(Py.newInteger(4), Py.newInteger(28), (PyObject)null));
               var1.setlocal(5, var3);
               var3 = null;
            } else {
               var1.setline(394);
               var3 = var1.getlocal(4);
               var10000 = var3._eq(Py.newInteger(16));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(395);
                  var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<QQ"), (PyObject)var1.getlocal(1).__getslice__(Py.newInteger(4), Py.newInteger(20), (PyObject)null));
                  var1.setlocal(5, var3);
                  var3 = null;
               } else {
                  var1.setline(396);
                  var3 = var1.getlocal(4);
                  var10000 = var3._eq(Py.newInteger(8));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(397);
                     var3 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<Q"), (PyObject)var1.getlocal(1).__getslice__(Py.newInteger(4), Py.newInteger(12), (PyObject)null));
                     var1.setlocal(5, var3);
                     var3 = null;
                  } else {
                     var1.setline(398);
                     var3 = var1.getlocal(4);
                     var10000 = var3._eq(Py.newInteger(0));
                     var3 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(401);
                        throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Corrupt extra field %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4)})));
                     }

                     var1.setline(399);
                     PyTuple var6 = new PyTuple(Py.EmptyObjects);
                     var1.setlocal(5, var6);
                     var3 = null;
                  }
               }
            }

            var1.setline(403);
            PyInteger var7 = Py.newInteger(0);
            var1.setlocal(6, var7);
            var3 = null;
            var1.setline(406);
            var3 = var1.getlocal(0).__getattr__("file_size");
            var10000 = var3._in(new PyTuple(new PyObject[]{Py.newLong("18446744073709551615"), Py.newLong("4294967295")}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(407);
               var3 = var1.getlocal(5).__getitem__(var1.getlocal(6));
               var1.getlocal(0).__setattr__("file_size", var3);
               var3 = null;
               var1.setline(408);
               var3 = var1.getlocal(6);
               var3 = var3._iadd(Py.newInteger(1));
               var1.setlocal(6, var3);
            }

            var1.setline(410);
            var3 = var1.getlocal(0).__getattr__("compress_size");
            var10000 = var3._eq(Py.newLong("4294967295"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(411);
               var3 = var1.getlocal(5).__getitem__(var1.getlocal(6));
               var1.getlocal(0).__setattr__("compress_size", var3);
               var3 = null;
               var1.setline(412);
               var3 = var1.getlocal(6);
               var3 = var3._iadd(Py.newInteger(1));
               var1.setlocal(6, var3);
            }

            var1.setline(414);
            var3 = var1.getlocal(0).__getattr__("header_offset");
            var10000 = var3._eq(Py.newLong("4294967295"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(415);
               var3 = var1.getlocal(0).__getattr__("header_offset");
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(416);
               var3 = var1.getlocal(5).__getitem__(var1.getlocal(6));
               var1.getlocal(0).__setattr__("header_offset", var3);
               var3 = null;
               var1.setline(417);
               var3 = var1.getlocal(6);
               var3 = var3._iadd(Py.newInteger(1));
               var1.setlocal(6, var3);
            }
         }

         var1.setline(419);
         var3 = var1.getlocal(1).__getslice__(var1.getlocal(4)._add(Py.newInteger(4)), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject _ZipDecrypter$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class to handle decryption of files stored within a ZIP archive.\n\n    ZIP supports a password-based form of encryption. Even though known\n    plaintext attacks have been found against it, it is still useful\n    to be able to get data out of such a file.\n\n    Usage:\n        zd = _ZipDecrypter(mypwd)\n        plain_char = zd(cypher_char)\n        plain_text = map(zd, cypher_text)\n    "));
      var1.setline(433);
      PyString.fromInterned("Class to handle decryption of files stored within a ZIP archive.\n\n    ZIP supports a password-based form of encryption. Even though known\n    plaintext attacks have been found against it, it is still useful\n    to be able to get data out of such a file.\n\n    Usage:\n        zd = _ZipDecrypter(mypwd)\n        plain_char = zd(cypher_char)\n        plain_text = map(zd, cypher_text)\n    ");
      var1.setline(435);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _GenerateCRCTable$14, PyString.fromInterned("Generate a CRC-32 table.\n\n        ZIP encryption uses the CRC32 one-byte primitive for scrambling some\n        internal keys. We noticed that a direct implementation is faster than\n        relying on binascii.crc32().\n        "));
      var1.setlocal("_GenerateCRCTable", var4);
      var3 = null;
      var1.setline(453);
      PyObject var5 = var1.getname("_GenerateCRCTable").__call__(var2);
      var1.setlocal("crctable", var5);
      var3 = null;
      var1.setline(455);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _crc32$15, PyString.fromInterned("Compute the CRC32 primitive on one byte."));
      var1.setlocal("_crc32", var4);
      var3 = null;
      var1.setline(459);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __init__$16, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(466);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _UpdateKeys$17, (PyObject)null);
      var1.setlocal("_UpdateKeys", var4);
      var3 = null;
      var1.setline(472);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$18, PyString.fromInterned("Decrypt a single character."));
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _GenerateCRCTable$14(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      PyString.fromInterned("Generate a CRC-32 table.\n\n        ZIP encryption uses the CRC32 one-byte primitive for scrambling some\n        internal keys. We noticed that a direct implementation is faster than\n        relying on binascii.crc32().\n        ");
      var1.setline(442);
      PyLong var3 = Py.newLong("3988292384");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(443);
      PyObject var8 = (new PyList(new PyObject[]{Py.newInteger(0)}))._mul(Py.newInteger(256));
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(444);
      var8 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

      while(true) {
         var1.setline(444);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(452);
            var8 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(2, var4);
         var1.setline(445);
         PyObject var5 = var1.getlocal(2);
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(446);
         var5 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(8)).__iter__();

         while(true) {
            var1.setline(446);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(451);
               var5 = var1.getlocal(3);
               var1.getlocal(1).__setitem__(var1.getlocal(2), var5);
               var5 = null;
               break;
            }

            var1.setlocal(4, var6);
            var1.setline(447);
            PyObject var7;
            if (var1.getlocal(3)._and(Py.newInteger(1)).__nonzero__()) {
               var1.setline(448);
               var7 = var1.getlocal(3)._rshift(Py.newInteger(1))._and(Py.newInteger(Integer.MAX_VALUE))._xor(var1.getlocal(0));
               var1.setlocal(3, var7);
               var7 = null;
            } else {
               var1.setline(450);
               var7 = var1.getlocal(3)._rshift(Py.newInteger(1))._and(Py.newInteger(Integer.MAX_VALUE));
               var1.setlocal(3, var7);
               var7 = null;
            }
         }
      }
   }

   public PyObject _crc32$15(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      PyString.fromInterned("Compute the CRC32 primitive on one byte.");
      var1.setline(457);
      PyObject var3 = var1.getlocal(2)._rshift(Py.newInteger(8))._and(Py.newInteger(16777215))._xor(var1.getlocal(0).__getattr__("crctable").__getitem__(var1.getlocal(2)._xor(var1.getglobal("ord").__call__(var2, var1.getlocal(1)))._and(Py.newInteger(255))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __init__$16(PyFrame var1, ThreadState var2) {
      var1.setline(460);
      PyInteger var3 = Py.newInteger(305419896);
      var1.getlocal(0).__setattr__((String)"key0", var3);
      var3 = null;
      var1.setline(461);
      var3 = Py.newInteger(591751049);
      var1.getlocal(0).__setattr__((String)"key1", var3);
      var3 = null;
      var1.setline(462);
      var3 = Py.newInteger(878082192);
      var1.getlocal(0).__setattr__((String)"key2", var3);
      var3 = null;
      var1.setline(463);
      PyObject var5 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(463);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(464);
         var1.getlocal(0).__getattr__("_UpdateKeys").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject _UpdateKeys$17(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyObject var3 = var1.getlocal(0).__getattr__("_crc32").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("key0"));
      var1.getlocal(0).__setattr__("key0", var3);
      var3 = null;
      var1.setline(468);
      var3 = var1.getlocal(0).__getattr__("key1")._add(var1.getlocal(0).__getattr__("key0")._and(Py.newInteger(255)))._and(Py.newLong("4294967295"));
      var1.getlocal(0).__setattr__("key1", var3);
      var3 = null;
      var1.setline(469);
      var3 = var1.getlocal(0).__getattr__("key1")._mul(Py.newInteger(134775813))._add(Py.newInteger(1))._and(Py.newLong("4294967295"));
      var1.getlocal(0).__setattr__("key1", var3);
      var3 = null;
      var1.setline(470);
      var3 = var1.getlocal(0).__getattr__("_crc32").__call__(var2, var1.getglobal("chr").__call__(var2, var1.getlocal(0).__getattr__("key1")._rshift(Py.newInteger(24))._and(Py.newInteger(255))), var1.getlocal(0).__getattr__("key2"));
      var1.getlocal(0).__setattr__("key2", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$18(PyFrame var1, ThreadState var2) {
      var1.setline(473);
      PyString.fromInterned("Decrypt a single character.");
      var1.setline(474);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(475);
      var3 = var1.getlocal(0).__getattr__("key2")._or(Py.newInteger(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(476);
      var3 = var1.getlocal(1)._xor(var1.getlocal(2)._mul(var1.getlocal(2)._xor(Py.newInteger(1)))._rshift(Py.newInteger(8))._and(Py.newInteger(255)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(477);
      var3 = var1.getglobal("chr").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(478);
      var1.getlocal(0).__getattr__("_UpdateKeys").__call__(var2, var1.getlocal(1));
      var1.setline(479);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ZipExtFile$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("File-like object for reading an archive member.\n       Is returned by ZipFile.open().\n    "));
      var1.setline(506);
      PyString.fromInterned("File-like object for reading an archive member.\n       Is returned by ZipFile.open().\n    ");
      var1.setline(509);
      PyObject var3 = Py.newInteger(1)._lshift(Py.newInteger(31)._sub(Py.newInteger(1)));
      var1.setlocal("MAX_N", var3);
      var3 = null;
      var1.setline(512);
      PyInteger var4 = Py.newInteger(4096);
      var1.setlocal("MIN_READ_SIZE", var4);
      var3 = null;
      var1.setline(515);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(?P<chunk>[^\\r\\n]+)|(?P<newline>\\n|\\r\\n?)"));
      var1.setlocal("PATTERN", var3);
      var3 = null;
      var1.setline(517);
      PyObject[] var5 = new PyObject[]{var1.getname("None"), var1.getname("False")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(557);
      var5 = new PyObject[]{Py.newInteger(-1)};
      var6 = new PyFunction(var1.f_globals, var5, readline$21, PyString.fromInterned("Read and return a line from the stream.\n\n        If limit is specified, at most limit bytes will be read.\n        "));
      var1.setlocal("readline", var6);
      var3 = null;
      var1.setline(607);
      var5 = new PyObject[]{Py.newInteger(1)};
      var6 = new PyFunction(var1.f_globals, var5, peek$22, PyString.fromInterned("Returns buffered bytes without advancing the position."));
      var1.setlocal("peek", var6);
      var3 = null;
      var1.setline(616);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, readable$23, (PyObject)null);
      var1.setlocal("readable", var6);
      var3 = null;
      var1.setline(619);
      var5 = new PyObject[]{Py.newInteger(-1)};
      var6 = new PyFunction(var1.f_globals, var5, read$24, PyString.fromInterned("Read and return up to n bytes.\n        If the argument is omitted, None, or negative, data is read and returned until EOF is reached..\n        "));
      var1.setlocal("read", var6);
      var3 = null;
      var1.setline(637);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _update_crc$25, (PyObject)null);
      var1.setlocal("_update_crc", var6);
      var3 = null;
      var1.setline(647);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, read1$26, PyString.fromInterned("Read up to n bytes with at most one read() system call."));
      var1.setlocal("read1", var6);
      var3 = null;
      var1.setline(699);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, close$27, (PyObject)null);
      var1.setlocal("close", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(519);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_fileobj", var3);
      var3 = null;
      var1.setline(520);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_decrypter", var3);
      var3 = null;
      var1.setline(521);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("_close_fileobj", var3);
      var3 = null;
      var1.setline(523);
      var3 = var1.getlocal(3).__getattr__("compress_type");
      var1.getlocal(0).__setattr__("_compress_type", var3);
      var3 = null;
      var1.setline(524);
      var3 = var1.getlocal(3).__getattr__("compress_size");
      var1.getlocal(0).__setattr__("_compress_size", var3);
      var3 = null;
      var1.setline(525);
      var3 = var1.getlocal(3).__getattr__("compress_size");
      var1.getlocal(0).__setattr__("_compress_left", var3);
      var3 = null;
      var1.setline(527);
      var3 = var1.getlocal(0).__getattr__("_compress_type");
      PyObject var10000 = var3._eq(var1.getglobal("ZIP_DEFLATED"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(528);
         var3 = var1.getglobal("zlib").__getattr__("decompressobj").__call__((ThreadState)var2, (PyObject)Py.newInteger(-15));
         var1.getlocal(0).__setattr__("_decompressor", var3);
         var3 = null;
      } else {
         var1.setline(529);
         var3 = var1.getlocal(0).__getattr__("_compress_type");
         var10000 = var3._ne(var1.getglobal("ZIP_STORED"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(530);
            var3 = var1.getglobal("compressor_names").__getattr__("get").__call__(var2, var1.getlocal(0).__getattr__("_compress_type"));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(531);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(532);
               throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2, PyString.fromInterned("compression type %d (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_compress_type"), var1.getlocal(6)}))));
            }

            var1.setline(534);
            throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2, PyString.fromInterned("compression type %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_compress_type")}))));
         }
      }

      var1.setline(535);
      PyString var6 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"_unconsumed", var6);
      var3 = null;
      var1.setline(537);
      var6 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"_readbuffer", var6);
      var3 = null;
      var1.setline(538);
      PyInteger var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_offset", var7);
      var3 = null;
      var1.setline(540);
      var6 = PyString.fromInterned("U");
      var10000 = var6._in(var1.getlocal(2));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("_universal", var3);
      var3 = null;
      var1.setline(541);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("newlines", var3);
      var3 = null;
      var1.setline(545);
      var3 = var1.getlocal(0).__getattr__("_decrypter");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(546);
         var10000 = var1.getlocal(0);
         String var8 = "_compress_left";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var8);
         var5 = var5._isub(Py.newInteger(12));
         var4.__setattr__(var8, var5);
      }

      var1.setline(548);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("mode", var3);
      var3 = null;
      var1.setline(549);
      var3 = var1.getlocal(3).__getattr__("filename");
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(551);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("CRC")).__nonzero__()) {
         var1.setline(552);
         var3 = var1.getlocal(3).__getattr__("CRC");
         var1.getlocal(0).__setattr__("_expected_crc", var3);
         var3 = null;
         var1.setline(553);
         var3 = var1.getglobal("crc32").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""))._and(Py.newLong("4294967295"));
         var1.getlocal(0).__setattr__("_running_crc", var3);
         var3 = null;
      } else {
         var1.setline(555);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_expected_crc", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject readline$21(PyFrame var1, ThreadState var2) {
      var1.setline(561);
      PyString.fromInterned("Read and return a line from the stream.\n\n        If limit is specified, at most limit bytes will be read.\n        ");
      var1.setline(563);
      PyObject var10000 = var1.getlocal(0).__getattr__("_universal").__not__();
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(565);
         var3 = var1.getlocal(0).__getattr__("_readbuffer").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getlocal(0).__getattr__("_offset"))._add(Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(566);
         var3 = var1.getlocal(2);
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(567);
            var3 = var1.getlocal(0).__getattr__("_readbuffer").__getslice__(var1.getlocal(0).__getattr__("_offset"), var1.getlocal(2), (PyObject)null);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(568);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("_offset", var3);
            var3 = null;
            var1.setline(569);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(571);
      if (var1.getlocal(0).__getattr__("_universal").__not__().__nonzero__()) {
         var1.setline(572);
         var3 = var1.getglobal("io").__getattr__("BufferedIOBase").__getattr__("readline").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(574);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(3, var4);
         var4 = null;

         while(true) {
            var1.setline(575);
            PyObject var7 = var1.getlocal(1);
            var10000 = var7._lt(Py.newInteger(0));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var7 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
               var10000 = var7._lt(var1.getlocal(1));
               var4 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(605);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(576);
            var7 = var1.getlocal(0).__getattr__("peek").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
            var1.setlocal(4, var7);
            var4 = null;
            var1.setline(577);
            var7 = var1.getlocal(4);
            var10000 = var7._eq(PyString.fromInterned(""));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(578);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(588);
            var7 = var1.getlocal(0).__getattr__("PATTERN").__getattr__("search").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var7);
            var4 = null;
            var1.setline(589);
            var7 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("newline"));
            var1.setlocal(6, var7);
            var4 = null;
            var1.setline(590);
            var7 = var1.getlocal(6);
            var10000 = var7._isnot(var1.getglobal("None"));
            var4 = null;
            PyObject var5;
            PyObject var6;
            String var8;
            if (var10000.__nonzero__()) {
               var1.setline(591);
               var7 = var1.getlocal(0).__getattr__("newlines");
               var10000 = var7._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(592);
                  PyList var9 = new PyList(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"newlines", var9);
                  var4 = null;
               }

               var1.setline(593);
               var7 = var1.getlocal(6);
               var10000 = var7._notin(var1.getlocal(0).__getattr__("newlines"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(594);
                  var1.getlocal(0).__getattr__("newlines").__getattr__("append").__call__(var2, var1.getlocal(6));
               }

               var1.setline(595);
               var10000 = var1.getlocal(0);
               var8 = "_offset";
               var5 = var10000;
               var6 = var5.__getattr__(var8);
               var6 = var6._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
               var5.__setattr__(var8, var6);
               var1.setline(596);
               var3 = var1.getlocal(3)._add(PyString.fromInterned("\n"));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(598);
            var7 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("chunk"));
            var1.setlocal(7, var7);
            var4 = null;
            var1.setline(599);
            var7 = var1.getlocal(1);
            var10000 = var7._ge(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(600);
               var7 = var1.getlocal(7).__getslice__((PyObject)null, var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(3))), (PyObject)null);
               var1.setlocal(7, var7);
               var4 = null;
            }

            var1.setline(602);
            var10000 = var1.getlocal(0);
            var8 = "_offset";
            var5 = var10000;
            var6 = var5.__getattr__(var8);
            var6 = var6._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(7)));
            var5.__setattr__(var8, var6);
            var1.setline(603);
            var7 = var1.getlocal(3);
            var7 = var7._iadd(var1.getlocal(7));
            var1.setlocal(3, var7);
         }
      }
   }

   public PyObject peek$22(PyFrame var1, ThreadState var2) {
      var1.setline(608);
      PyString.fromInterned("Returns buffered bytes without advancing the position.");
      var1.setline(609);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._gt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_readbuffer"))._sub(var1.getlocal(0).__getattr__("_offset")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(610);
         var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(611);
         var10000 = var1.getlocal(0);
         String var6 = "_offset";
         PyObject var4 = var10000;
         PyObject var5 = var4.__getattr__(var6);
         var5 = var5._isub(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
         var4.__setattr__(var6, var5);
      }

      var1.setline(614);
      var3 = var1.getlocal(0).__getattr__("_readbuffer").__getslice__(var1.getlocal(0).__getattr__("_offset"), var1.getlocal(0).__getattr__("_offset")._add(Py.newInteger(512)), (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readable$23(PyFrame var1, ThreadState var2) {
      var1.setline(617);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject read$24(PyFrame var1, ThreadState var2) {
      var1.setline(622);
      PyString.fromInterned("Read and return up to n bytes.\n        If the argument is omitted, None, or negative, data is read and returned until EOF is reached..\n        ");
      var1.setline(623);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(624);
      PyObject var5 = var1.getlocal(1);
      PyObject var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(625);
         PyInteger var6 = Py.newInteger(-1);
         var1.setlocal(1, var6);
         var3 = null;
      }

      while(true) {
         var1.setline(626);
         if (!var1.getglobal("True").__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(627);
         var5 = var1.getlocal(1);
         var10000 = var5._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(628);
            var5 = var1.getlocal(0).__getattr__("read1").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var5);
            var3 = null;
         } else {
            var1.setline(629);
            var5 = var1.getlocal(1);
            var10000 = var5._gt(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(632);
               var5 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var5;
            }

            var1.setline(630);
            var5 = var1.getlocal(0).__getattr__("read1").__call__(var2, var1.getlocal(1)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
            var1.setlocal(3, var5);
            var3 = null;
         }

         var1.setline(633);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var4._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(634);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(635);
         var4 = var1.getlocal(2);
         var4 = var4._iadd(var1.getlocal(3));
         var1.setlocal(2, var4);
      }
   }

   public PyObject _update_crc$25(PyFrame var1, ThreadState var2) {
      var1.setline(639);
      PyObject var3 = var1.getlocal(0).__getattr__("_expected_crc");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(641);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(642);
         var3 = var1.getglobal("crc32").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_running_crc"))._and(Py.newLong("4294967295"));
         var1.getlocal(0).__setattr__("_running_crc", var3);
         var3 = null;
         var1.setline(644);
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_running_crc");
            var10000 = var3._ne(var1.getlocal(0).__getattr__("_expected_crc"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(645);
            throw Py.makeException(var1.getglobal("BadZipfile").__call__(var2, PyString.fromInterned("Bad CRC-32 for file %r")._mod(var1.getlocal(0).__getattr__("name"))));
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject read1$26(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyString.fromInterned("Read up to n bytes with at most one read() system call.");
      var1.setline(651);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(652);
         var3 = var1.getlocal(0).__getattr__("MAX_N");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(655);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_readbuffer"))._sub(var1.getlocal(0).__getattr__("_offset"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(658);
      var3 = var1.getlocal(0).__getattr__("_compress_left");
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._gt(var1.getlocal(2)._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_unconsumed"))));
         var3 = null;
      }

      PyObject var4;
      PyObject var5;
      String[] var6;
      String var7;
      PyObject[] var8;
      PyInteger var9;
      if (var10000.__nonzero__()) {
         var1.setline(659);
         var3 = var1.getlocal(1)._sub(var1.getlocal(2))._sub(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_unconsumed")));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(660);
         var3 = var1.getglobal("max").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("MIN_READ_SIZE"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(661);
         var3 = var1.getglobal("min").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("_compress_left"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(663);
         var3 = var1.getlocal(0).__getattr__("_fileobj").__getattr__("read").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(664);
         var10000 = var1.getlocal(0);
         var7 = "_compress_left";
         var4 = var10000;
         var5 = var4.__getattr__(var7);
         var5 = var5._isub(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
         var4.__setattr__(var7, var5);
         var1.setline(666);
         var10000 = var1.getlocal(4);
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_decrypter");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(667);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getlocal(0).__getattr__("_decrypter"), var1.getlocal(4)));
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(669);
         var3 = var1.getlocal(0).__getattr__("_compress_type");
         var10000 = var3._eq(var1.getglobal("ZIP_STORED"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(670);
            var10000 = var1.getlocal(0).__getattr__("_update_crc");
            var8 = new PyObject[]{var1.getlocal(4), null};
            var4 = var1.getlocal(0).__getattr__("_compress_left");
            PyObject var10002 = var4._eq(Py.newInteger(0));
            var4 = null;
            var8[1] = var10002;
            var6 = new String[]{"eof"};
            var10000.__call__(var2, var8, var6);
            var3 = null;
            var1.setline(671);
            var3 = var1.getlocal(0).__getattr__("_readbuffer").__getslice__(var1.getlocal(0).__getattr__("_offset"), (PyObject)null, (PyObject)null)._add(var1.getlocal(4));
            var1.getlocal(0).__setattr__("_readbuffer", var3);
            var3 = null;
            var1.setline(672);
            var9 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_offset", var9);
            var3 = null;
         } else {
            var1.setline(675);
            var10000 = var1.getlocal(0);
            var7 = "_unconsumed";
            var4 = var10000;
            var5 = var4.__getattr__(var7);
            var5 = var5._iadd(var1.getlocal(4));
            var4.__setattr__(var7, var5);
         }
      }

      var1.setline(678);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_unconsumed"));
      var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._gt(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_compress_type");
            var10000 = var3._eq(var1.getglobal("ZIP_DEFLATED"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(680);
         var3 = var1.getlocal(0).__getattr__("_decompressor").__getattr__("decompress").__call__(var2, var1.getlocal(0).__getattr__("_unconsumed"), var1.getglobal("max").__call__(var2, var1.getlocal(1)._sub(var1.getlocal(2)), var1.getlocal(0).__getattr__("MIN_READ_SIZE")));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(685);
         var3 = var1.getlocal(0).__getattr__("_decompressor").__getattr__("unconsumed_tail");
         var1.getlocal(0).__setattr__("_unconsumed", var3);
         var3 = null;
         var1.setline(686);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_unconsumed"));
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_compress_left");
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
         }

         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(687);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(688);
            var3 = var1.getlocal(4);
            var3 = var3._iadd(var1.getlocal(0).__getattr__("_decompressor").__getattr__("flush").__call__(var2));
            var1.setlocal(4, var3);
         }

         var1.setline(690);
         var10000 = var1.getlocal(0).__getattr__("_update_crc");
         var8 = new PyObject[]{var1.getlocal(4), var1.getlocal(5)};
         var6 = new String[]{"eof"};
         var10000.__call__(var2, var8, var6);
         var3 = null;
         var1.setline(691);
         var3 = var1.getlocal(0).__getattr__("_readbuffer").__getslice__(var1.getlocal(0).__getattr__("_offset"), (PyObject)null, (PyObject)null)._add(var1.getlocal(4));
         var1.getlocal(0).__setattr__("_readbuffer", var3);
         var3 = null;
         var1.setline(692);
         var9 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_offset", var9);
         var3 = null;
      }

      var1.setline(695);
      var3 = var1.getlocal(0).__getattr__("_readbuffer").__getslice__(var1.getlocal(0).__getattr__("_offset"), var1.getlocal(0).__getattr__("_offset")._add(var1.getlocal(1)), (PyObject)null);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(696);
      var10000 = var1.getlocal(0);
      var7 = "_offset";
      var4 = var10000;
      var5 = var4.__getattr__(var7);
      var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
      var4.__setattr__(var7, var5);
      var1.setline(697);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject close$27(PyFrame var1, ThreadState var2) {
      Object var3 = null;

      try {
         var1.setline(701);
         if (var1.getlocal(0).__getattr__("_close_fileobj").__nonzero__()) {
            var1.setline(702);
            var1.getlocal(0).__getattr__("_fileobj").__getattr__("close").__call__(var2);
         }
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(704);
         var1.getglobal("super").__call__(var2, var1.getglobal("ZipExtFile"), var1.getlocal(0)).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(704);
      var1.getglobal("super").__call__(var2, var1.getglobal("ZipExtFile"), var1.getlocal(0)).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ZipFile$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned(" Class with methods to open, read, write, close, list zip files.\n\n    z = ZipFile(file, mode=\"r\", compression=ZIP_STORED, allowZip64=False)\n\n    file: Either the path to the file, or a file-like object.\n          If it is a path, the file will be opened and closed by ZipFile.\n    mode: The mode can be either read \"r\", write \"w\" or append \"a\".\n    compression: ZIP_STORED (no compression) or ZIP_DEFLATED (requires zlib).\n    allowZip64: if True ZipFile will create files with ZIP64 extensions when\n                needed, otherwise it will raise an exception when this would\n                be necessary.\n\n    "));
      var1.setline(720);
      PyString.fromInterned(" Class with methods to open, read, write, close, list zip files.\n\n    z = ZipFile(file, mode=\"r\", compression=ZIP_STORED, allowZip64=False)\n\n    file: Either the path to the file, or a file-like object.\n          If it is a path, the file will be opened and closed by ZipFile.\n    mode: The mode can be either read \"r\", write \"w\" or append \"a\".\n    compression: ZIP_STORED (no compression) or ZIP_DEFLATED (requires zlib).\n    allowZip64: if True ZipFile will create files with ZIP64 extensions when\n                needed, otherwise it will raise an exception when this would\n                be necessary.\n\n    ");
      var1.setline(722);
      PyObject var3 = var1.getname("None");
      var1.setlocal("fp", var3);
      var3 = null;
      var1.setline(724);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("r"), var1.getname("ZIP_STORED"), var1.getname("False")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$29, PyString.fromInterned("Open the ZIP file with mode read \"r\", write \"w\" or append \"a\"."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(795);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __enter__$30, (PyObject)null);
      var1.setlocal("__enter__", var5);
      var3 = null;
      var1.setline(798);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __exit__$31, (PyObject)null);
      var1.setlocal("__exit__", var5);
      var3 = null;
      var1.setline(801);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _RealGetContents$32, PyString.fromInterned("Read in the table of contents for the ZIP file."));
      var1.setlocal("_RealGetContents", var5);
      var3 = null;
      var1.setline(870);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, namelist$33, PyString.fromInterned("Return a list of file names in the archive."));
      var1.setlocal("namelist", var5);
      var3 = null;
      var1.setline(877);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, infolist$34, PyString.fromInterned("Return a list of class ZipInfo instances for files in the\n        archive."));
      var1.setlocal("infolist", var5);
      var3 = null;
      var1.setline(882);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, printdir$35, PyString.fromInterned("Print a table of contents for the zip file."));
      var1.setlocal("printdir", var5);
      var3 = null;
      var1.setline(889);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testzip$36, PyString.fromInterned("Read all the files and check the CRC."));
      var1.setlocal("testzip", var5);
      var3 = null;
      var1.setline(902);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getinfo$37, PyString.fromInterned("Return the instance of ZipInfo given 'name'."));
      var1.setlocal("getinfo", var5);
      var3 = null;
      var1.setline(911);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setpassword$38, PyString.fromInterned("Set default password for encrypted files."));
      var1.setlocal("setpassword", var5);
      var3 = null;
      var1.setline(915);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, comment$39, PyString.fromInterned("The comment text associated with the ZIP file."));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("comment", var3);
      var3 = null;
      var1.setline(920);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, comment$40, (PyObject)null);
      var3 = var1.getname("comment").__getattr__("setter").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("comment", var3);
      var3 = null;
      var1.setline(931);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, read$41, PyString.fromInterned("Return file bytes (as a string) for name."));
      var1.setlocal("read", var5);
      var3 = null;
      var1.setline(935);
      var4 = new PyObject[]{PyString.fromInterned("r"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, open$42, PyString.fromInterned("Return file-like object for 'name'."));
      var1.setlocal("open", var5);
      var3 = null;
      var1.setline(1014);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, extract$43, PyString.fromInterned("Extract a member from the archive to the current working directory,\n           using its full name. Its file information is extracted as accurately\n           as possible. `member' may be a filename or a ZipInfo object. You can\n           specify a different directory using `path'.\n        "));
      var1.setlocal("extract", var5);
      var3 = null;
      var1.setline(1028);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, extractall$44, PyString.fromInterned("Extract all members from the archive to the current working\n           directory. `path' specifies a different directory to extract to.\n           `members' is optional and must be a subset of the list returned\n           by namelist().\n        "));
      var1.setlocal("extractall", var5);
      var3 = null;
      var1.setline(1040);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _extract_member$45, PyString.fromInterned("Extract the ZipInfo object 'member' to a physical\n           file on the path targetpath.\n        "));
      var1.setlocal("_extract_member", var5);
      var3 = null;
      var1.setline(1083);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _writecheck$49, PyString.fromInterned("Check for errors before writing a file to the archive."));
      var1.setlocal("_writecheck", var5);
      var3 = null;
      var1.setline(1106);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, write$50, PyString.fromInterned("Put the bytes from filename into the archive under the name\n        arcname."));
      var1.setlocal("write", var5);
      var3 = null;
      var1.setline(1195);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, writestr$51, PyString.fromInterned("Write a file into the archive.  The contents is the string\n        'bytes'.  'zinfo_or_arcname' is either a ZipInfo instance or\n        the name of the file in the archive."));
      var1.setlocal("writestr", var5);
      var3 = null;
      var1.setline(1242);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __del__$52, PyString.fromInterned("Call the \"close()\" method in case the user forgot."));
      var1.setlocal("__del__", var5);
      var3 = null;
      var1.setline(1246);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$53, PyString.fromInterned("Close the file, and for mode \"w\" and \"a\" write the ending\n        records."));
      var1.setlocal("close", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$29(PyFrame var1, ThreadState var2) {
      var1.setline(725);
      PyString.fromInterned("Open the ZIP file with mode read \"r\", write \"w\" or append \"a\".");
      var1.setline(726);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("r"), PyString.fromInterned("w"), PyString.fromInterned("a")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(727);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ZipFile() requires mode \"r\", \"w\", or \"a\"")));
      } else {
         var1.setline(729);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(var1.getglobal("ZIP_STORED"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(730);
         } else {
            var1.setline(731);
            var3 = var1.getlocal(3);
            var10000 = var3._eq(var1.getglobal("ZIP_DEFLATED"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(736);
               throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("That compression method is not supported"));
            }

            var1.setline(732);
            if (var1.getglobal("zlib").__not__().__nonzero__()) {
               var1.setline(733);
               throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Compression requires the (missing) zlib module"));
            }
         }

         var1.setline(738);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("_allowZip64", var3);
         var3 = null;
         var1.setline(739);
         var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_didModify", var3);
         var3 = null;
         var1.setline(740);
         PyInteger var9 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"debug", var9);
         var3 = null;
         var1.setline(741);
         PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"NameToInfo", var10);
         var3 = null;
         var1.setline(742);
         PyList var11 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"filelist", var11);
         var3 = null;
         var1.setline(743);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("compression", var3);
         var3 = null;
         var1.setline(744);
         var3 = var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("b"), (PyObject)PyString.fromInterned("")).__getitem__(Py.newInteger(0));
         var1.getlocal(0).__setattr__("mode", var3);
         var1.setlocal(5, var3);
         var1.setline(745);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("pwd", var3);
         var3 = null;
         var1.setline(746);
         PyString var12 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"_comment", var12);
         var3 = null;
         var1.setline(749);
         PyObject var4;
         PyException var13;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(750);
            var9 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_filePassed", var9);
            var3 = null;
            var1.setline(751);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("filename", var3);
            var3 = null;
            var1.setline(752);
            var10 = new PyDictionary(new PyObject[]{PyString.fromInterned("r"), PyString.fromInterned("rb"), PyString.fromInterned("w"), PyString.fromInterned("wb"), PyString.fromInterned("a"), PyString.fromInterned("r+b")});
            var1.setlocal(6, var10);
            var3 = null;

            try {
               var1.setline(754);
               var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1), var1.getlocal(6).__getitem__(var1.getlocal(2)));
               var1.getlocal(0).__setattr__("fp", var3);
               var3 = null;
            } catch (Throwable var7) {
               var13 = Py.setException(var7, var1);
               if (!var13.match(var1.getglobal("IOError"))) {
                  throw var13;
               }

               var1.setline(756);
               var4 = var1.getlocal(2);
               var10000 = var4._eq(PyString.fromInterned("a"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(760);
                  throw Py.makeException();
               }

               var1.setline(757);
               PyString var8 = PyString.fromInterned("w");
               var1.setlocal(2, var8);
               var1.setlocal(5, var8);
               var1.setline(758);
               var4 = var1.getglobal("open").__call__(var2, var1.getlocal(1), var1.getlocal(6).__getitem__(var1.getlocal(2)));
               var1.getlocal(0).__setattr__("fp", var4);
               var4 = null;
            }
         } else {
            var1.setline(762);
            var9 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"_filePassed", var9);
            var3 = null;
            var1.setline(763);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("fp", var3);
            var3 = null;
            var1.setline(764);
            var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("name"), (PyObject)var1.getglobal("None"));
            var1.getlocal(0).__setattr__("filename", var3);
            var3 = null;
         }

         try {
            var1.setline(767);
            var3 = var1.getlocal(5);
            var10000 = var3._eq(PyString.fromInterned("r"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(768);
               var1.getlocal(0).__getattr__("_RealGetContents").__call__(var2);
            } else {
               var1.setline(769);
               var3 = var1.getlocal(5);
               var10000 = var3._eq(PyString.fromInterned("w"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(772);
                  var3 = var1.getglobal("True");
                  var1.getlocal(0).__setattr__("_didModify", var3);
                  var3 = null;
               } else {
                  var1.setline(773);
                  var3 = var1.getlocal(5);
                  var10000 = var3._eq(PyString.fromInterned("a"));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(787);
                     throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Mode must be \"r\", \"w\" or \"a\"")));
                  }

                  try {
                     var1.setline(776);
                     var1.getlocal(0).__getattr__("_RealGetContents").__call__(var2);
                     var1.setline(778);
                     var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("start_dir"), (PyObject)Py.newInteger(0));
                  } catch (Throwable var5) {
                     var13 = Py.setException(var5, var1);
                     if (!var13.match(var1.getglobal("BadZipfile"))) {
                        throw var13;
                     }

                     var1.setline(781);
                     var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
                     var1.setline(785);
                     var4 = var1.getglobal("True");
                     var1.getlocal(0).__setattr__("_didModify", var4);
                     var4 = null;
                  }
               }
            }
         } catch (Throwable var6) {
            Py.setException(var6, var1);
            var1.setline(789);
            var4 = var1.getlocal(0).__getattr__("fp");
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(790);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("fp", var4);
            var4 = null;
            var1.setline(791);
            if (var1.getlocal(0).__getattr__("_filePassed").__not__().__nonzero__()) {
               var1.setline(792);
               var1.getlocal(7).__getattr__("close").__call__(var2);
            }

            var1.setline(793);
            throw Py.makeException();
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __enter__$30(PyFrame var1, ThreadState var2) {
      var1.setline(796);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$31(PyFrame var1, ThreadState var2) {
      var1.setline(799);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _RealGetContents$32(PyFrame var1, ThreadState var2) {
      var1.setline(802);
      PyString.fromInterned("Read in the table of contents for the ZIP file.");
      var1.setline(803);
      PyObject var3 = var1.getlocal(0).__getattr__("fp");
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(805);
         var3 = var1.getglobal("_EndRecData").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (var7.match(var1.getglobal("IOError"))) {
            var1.setline(807);
            throw Py.makeException(var1.getglobal("BadZipfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("File is not a zip file")));
         }

         throw var7;
      }

      var1.setline(808);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(809);
         throw Py.makeException(var1.getglobal("BadZipfile"), PyString.fromInterned("File is not a zip file"));
      } else {
         var1.setline(810);
         var3 = var1.getlocal(0).__getattr__("debug");
         PyObject var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(811);
            Py.println(var1.getlocal(2));
         }

         var1.setline(812);
         var3 = var1.getlocal(2).__getitem__(var1.getglobal("_ECD_SIZE"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(813);
         var3 = var1.getlocal(2).__getitem__(var1.getglobal("_ECD_OFFSET"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(814);
         var3 = var1.getlocal(2).__getitem__(var1.getglobal("_ECD_COMMENT"));
         var1.getlocal(0).__setattr__("_comment", var3);
         var3 = null;
         var1.setline(817);
         var3 = var1.getlocal(2).__getitem__(var1.getglobal("_ECD_LOCATION"))._sub(var1.getlocal(3))._sub(var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(818);
         var3 = var1.getlocal(2).__getitem__(var1.getglobal("_ECD_SIGNATURE"));
         var10000 = var3._eq(var1.getglobal("stringEndArchive64"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(820);
            var3 = var1.getlocal(5);
            var3 = var3._isub(var1.getglobal("sizeEndCentDir64")._add(var1.getglobal("sizeEndCentDir64Locator")));
            var1.setlocal(5, var3);
         }

         var1.setline(822);
         var3 = var1.getlocal(0).__getattr__("debug");
         var10000 = var3._gt(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(823);
            var3 = var1.getlocal(5)._add(var1.getlocal(4));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(824);
            Py.printComma(PyString.fromInterned("given, inferred, offset"));
            Py.printComma(var1.getlocal(4));
            Py.printComma(var1.getlocal(6));
            Py.println(var1.getlocal(5));
         }

         var1.setline(826);
         var3 = var1.getlocal(4)._add(var1.getlocal(5));
         var1.getlocal(0).__setattr__("start_dir", var3);
         var3 = null;
         var1.setline(827);
         var1.getlocal(1).__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("start_dir"), (PyObject)Py.newInteger(0));
         var1.setline(828);
         var3 = var1.getlocal(1).__getattr__("read").__call__(var2, var1.getlocal(3));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(829);
         var3 = var1.getglobal("cStringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(7));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(830);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(8, var8);
         var3 = null;

         while(true) {
            var1.setline(831);
            var3 = var1.getlocal(8);
            var10000 = var3._lt(var1.getlocal(3));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(832);
            var3 = var1.getlocal(1).__getattr__("read").__call__(var2, var1.getglobal("sizeCentralDir"));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(833);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
            var10000 = var3._ne(var1.getglobal("sizeCentralDir"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(834);
               throw Py.makeException(var1.getglobal("BadZipfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Truncated central directory")));
            }

            var1.setline(835);
            var3 = var1.getglobal("struct").__getattr__("unpack").__call__(var2, var1.getglobal("structCentralDir"), var1.getlocal(9));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(836);
            var3 = var1.getlocal(9).__getitem__(var1.getglobal("_CD_SIGNATURE"));
            var10000 = var3._ne(var1.getglobal("stringCentralDir"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(837);
               throw Py.makeException(var1.getglobal("BadZipfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Bad magic number for central directory")));
            }

            var1.setline(838);
            var3 = var1.getlocal(0).__getattr__("debug");
            var10000 = var3._gt(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(839);
               Py.println(var1.getlocal(9));
            }

            var1.setline(840);
            var3 = var1.getlocal(1).__getattr__("read").__call__(var2, var1.getlocal(9).__getitem__(var1.getglobal("_CD_FILENAME_LENGTH")));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(842);
            var3 = var1.getglobal("ZipInfo").__call__(var2, var1.getlocal(10));
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(843);
            var3 = var1.getlocal(1).__getattr__("read").__call__(var2, var1.getlocal(9).__getitem__(var1.getglobal("_CD_EXTRA_FIELD_LENGTH")));
            var1.getlocal(11).__setattr__("extra", var3);
            var3 = null;
            var1.setline(844);
            var3 = var1.getlocal(1).__getattr__("read").__call__(var2, var1.getlocal(9).__getitem__(var1.getglobal("_CD_COMMENT_LENGTH")));
            var1.getlocal(11).__setattr__("comment", var3);
            var3 = null;
            var1.setline(845);
            var3 = var1.getlocal(9).__getitem__(var1.getglobal("_CD_LOCAL_HEADER_OFFSET"));
            var1.getlocal(11).__setattr__("header_offset", var3);
            var3 = null;
            var1.setline(846);
            var3 = var1.getlocal(9).__getslice__(Py.newInteger(1), Py.newInteger(12), (PyObject)null);
            PyObject[] var4 = Py.unpackSequence(var3, 11);
            PyObject var5 = var4[0];
            var1.getlocal(11).__setattr__("create_version", var5);
            var5 = null;
            var5 = var4[1];
            var1.getlocal(11).__setattr__("create_system", var5);
            var5 = null;
            var5 = var4[2];
            var1.getlocal(11).__setattr__("extract_version", var5);
            var5 = null;
            var5 = var4[3];
            var1.getlocal(11).__setattr__("reserved", var5);
            var5 = null;
            var5 = var4[4];
            var1.getlocal(11).__setattr__("flag_bits", var5);
            var5 = null;
            var5 = var4[5];
            var1.getlocal(11).__setattr__("compress_type", var5);
            var5 = null;
            var5 = var4[6];
            var1.setlocal(12, var5);
            var5 = null;
            var5 = var4[7];
            var1.setlocal(13, var5);
            var5 = null;
            var5 = var4[8];
            var1.getlocal(11).__setattr__("CRC", var5);
            var5 = null;
            var5 = var4[9];
            var1.getlocal(11).__setattr__("compress_size", var5);
            var5 = null;
            var5 = var4[10];
            var1.getlocal(11).__setattr__("file_size", var5);
            var5 = null;
            var3 = null;
            var1.setline(849);
            var3 = var1.getlocal(9).__getslice__(Py.newInteger(15), Py.newInteger(18), (PyObject)null);
            var4 = Py.unpackSequence(var3, 3);
            var5 = var4[0];
            var1.getlocal(11).__setattr__("volume", var5);
            var5 = null;
            var5 = var4[1];
            var1.getlocal(11).__setattr__("internal_attr", var5);
            var5 = null;
            var5 = var4[2];
            var1.getlocal(11).__setattr__("external_attr", var5);
            var5 = null;
            var3 = null;
            var1.setline(851);
            var3 = var1.getlocal(12);
            var1.getlocal(11).__setattr__("_raw_time", var3);
            var3 = null;
            var1.setline(852);
            PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(13)._rshift(Py.newInteger(9))._add(Py.newInteger(1980)), var1.getlocal(13)._rshift(Py.newInteger(5))._and(Py.newInteger(15)), var1.getlocal(13)._and(Py.newInteger(31)), var1.getlocal(12)._rshift(Py.newInteger(11)), var1.getlocal(12)._rshift(Py.newInteger(5))._and(Py.newInteger(63)), var1.getlocal(12)._and(Py.newInteger(31))._mul(Py.newInteger(2))});
            var1.getlocal(11).__setattr__((String)"date_time", var9);
            var3 = null;
            var1.setline(855);
            var1.getlocal(11).__getattr__("_decodeExtra").__call__(var2);
            var1.setline(856);
            var3 = var1.getlocal(11).__getattr__("header_offset")._add(var1.getlocal(5));
            var1.getlocal(11).__setattr__("header_offset", var3);
            var3 = null;
            var1.setline(857);
            var3 = var1.getlocal(11).__getattr__("_decodeFilename").__call__(var2);
            var1.getlocal(11).__setattr__("filename", var3);
            var3 = null;
            var1.setline(858);
            var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getlocal(11));
            var1.setline(859);
            var3 = var1.getlocal(11);
            var1.getlocal(0).__getattr__("NameToInfo").__setitem__(var1.getlocal(11).__getattr__("filename"), var3);
            var3 = null;
            var1.setline(862);
            var3 = var1.getlocal(8)._add(var1.getglobal("sizeCentralDir"))._add(var1.getlocal(9).__getitem__(var1.getglobal("_CD_FILENAME_LENGTH")))._add(var1.getlocal(9).__getitem__(var1.getglobal("_CD_EXTRA_FIELD_LENGTH")))._add(var1.getlocal(9).__getitem__(var1.getglobal("_CD_COMMENT_LENGTH")));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(866);
            var3 = var1.getlocal(0).__getattr__("debug");
            var10000 = var3._gt(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(867);
               Py.printComma(PyString.fromInterned("total"));
               Py.println(var1.getlocal(8));
            }
         }
      }
   }

   public PyObject namelist$33(PyFrame var1, ThreadState var2) {
      var1.setline(871);
      PyString.fromInterned("Return a list of file names in the archive.");
      var1.setline(872);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(873);
      PyObject var5 = var1.getlocal(0).__getattr__("filelist").__iter__();

      while(true) {
         var1.setline(873);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(875);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(874);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getattr__("filename"));
      }
   }

   public PyObject infolist$34(PyFrame var1, ThreadState var2) {
      var1.setline(879);
      PyString.fromInterned("Return a list of class ZipInfo instances for files in the\n        archive.");
      var1.setline(880);
      PyObject var3 = var1.getlocal(0).__getattr__("filelist");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject printdir$35(PyFrame var1, ThreadState var2) {
      var1.setline(883);
      PyString.fromInterned("Print a table of contents for the zip file.");
      var1.setline(884);
      Py.println(PyString.fromInterned("%-46s %19s %12s")._mod(new PyTuple(new PyObject[]{PyString.fromInterned("File Name"), PyString.fromInterned("Modified    "), PyString.fromInterned("Size")})));
      var1.setline(885);
      PyObject var3 = var1.getlocal(0).__getattr__("filelist").__iter__();

      while(true) {
         var1.setline(885);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(886);
         PyObject var5 = PyString.fromInterned("%d-%02d-%02d %02d:%02d:%02d")._mod(var1.getlocal(1).__getattr__("date_time").__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(887);
         Py.println(PyString.fromInterned("%-46s %s %12d")._mod(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("filename"), var1.getlocal(2), var1.getlocal(1).__getattr__("file_size")})));
      }
   }

   public PyObject testzip$36(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(890);
      PyString.fromInterned("Read all the files and check the CRC.");
      var1.setline(891);
      PyObject var3 = Py.newInteger(2)._pow(Py.newInteger(20));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(892);
      var3 = var1.getlocal(0).__getattr__("filelist").__iter__();

      while(true) {
         var1.setline(892);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);

         PyObject var6;
         try {
            ContextManager var9;
            var6 = (var9 = ContextGuard.getManager(var1.getlocal(0).__getattr__("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("filename"), (PyObject)PyString.fromInterned("r")))).__enter__(var2);

            try {
               var1.setlocal(3, var6);

               while(true) {
                  var1.setline(897);
                  if (!var1.getlocal(3).__getattr__("read").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                     break;
                  }

                  var1.setline(898);
               }
            } catch (Throwable var7) {
               if (var9.__exit__(var2, Py.setException(var7, var1))) {
                  continue;
               }

               throw (Throwable)Py.makeException();
            }

            var9.__exit__(var2, (PyException)null);
         } catch (Throwable var8) {
            PyException var5 = Py.setException(var8, var1);
            if (var5.match(var1.getglobal("BadZipfile"))) {
               var1.setline(900);
               var6 = var1.getlocal(2).__getattr__("filename");
               var1.f_lasti = -1;
               return var6;
            }

            throw var5;
         }
      }
   }

   public PyObject getinfo$37(PyFrame var1, ThreadState var2) {
      var1.setline(903);
      PyString.fromInterned("Return the instance of ZipInfo given 'name'.");
      var1.setline(904);
      PyObject var3 = var1.getlocal(0).__getattr__("NameToInfo").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(905);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(906);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("There is no item named %r in the archive")._mod(var1.getlocal(1))));
      } else {
         var1.setline(909);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setpassword$38(PyFrame var1, ThreadState var2) {
      var1.setline(912);
      PyString.fromInterned("Set default password for encrypted files.");
      var1.setline(913);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("pwd", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject comment$39(PyFrame var1, ThreadState var2) {
      var1.setline(917);
      PyString.fromInterned("The comment text associated with the ZIP file.");
      var1.setline(918);
      PyObject var3 = var1.getlocal(0).__getattr__("_comment");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject comment$40(PyFrame var1, ThreadState var2) {
      var1.setline(923);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._ge(var1.getglobal("ZIP_MAX_COMMENT"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(924);
         if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
            var1.setline(925);
            Py.println(PyString.fromInterned("Archive comment is too long; truncating to %d bytes")._mod(var1.getglobal("ZIP_MAX_COMMENT")));
         }

         var1.setline(927);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getglobal("ZIP_MAX_COMMENT"), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(928);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_comment", var3);
      var3 = null;
      var1.setline(929);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_didModify", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$41(PyFrame var1, ThreadState var2) {
      var1.setline(932);
      PyString.fromInterned("Return file bytes (as a string) for name.");
      var1.setline(933);
      PyObject var3 = var1.getlocal(0).__getattr__("open").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("r"), (PyObject)var1.getlocal(2)).__getattr__("read").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject open$42(PyFrame var1, ThreadState var2) {
      var1.setline(936);
      PyString.fromInterned("Return file-like object for 'name'.");
      var1.setline(937);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("r"), PyString.fromInterned("U"), PyString.fromInterned("rU")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(938);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("open() requires mode \"r\", \"U\", or \"rU\""));
      } else {
         var1.setline(939);
         if (var1.getlocal(0).__getattr__("fp").__not__().__nonzero__()) {
            var1.setline(940);
            throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Attempt to read ZIP archive that was already closed"));
         } else {
            var1.setline(945);
            if (var1.getlocal(0).__getattr__("_filePassed").__nonzero__()) {
               var1.setline(946);
               var3 = var1.getlocal(0).__getattr__("fp");
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(947);
               var3 = var1.getglobal("False");
               var1.setlocal(5, var3);
               var3 = null;
            } else {
               var1.setline(949);
               var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("filename"), (PyObject)PyString.fromInterned("rb"));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(950);
               var3 = var1.getglobal("True");
               var1.setlocal(5, var3);
               var3 = null;
            }

            try {
               var1.setline(954);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("ZipInfo")).__nonzero__()) {
                  var1.setline(956);
                  var3 = var1.getlocal(1);
                  var1.setlocal(6, var3);
                  var3 = null;
               } else {
                  var1.setline(959);
                  var3 = var1.getlocal(0).__getattr__("getinfo").__call__(var2, var1.getlocal(1));
                  var1.setlocal(6, var3);
                  var3 = null;
               }

               var1.setline(961);
               var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("header_offset"), (PyObject)Py.newInteger(0));
               var1.setline(964);
               var3 = var1.getlocal(4).__getattr__("read").__call__(var2, var1.getglobal("sizeFileHeader"));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(965);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
               var10000 = var3._ne(var1.getglobal("sizeFileHeader"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(966);
                  throw Py.makeException(var1.getglobal("BadZipfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Truncated file header")));
               } else {
                  var1.setline(967);
                  var3 = var1.getglobal("struct").__getattr__("unpack").__call__(var2, var1.getglobal("structFileHeader"), var1.getlocal(7));
                  var1.setlocal(7, var3);
                  var3 = null;
                  var1.setline(968);
                  var3 = var1.getlocal(7).__getitem__(var1.getglobal("_FH_SIGNATURE"));
                  var10000 = var3._ne(var1.getglobal("stringFileHeader"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(969);
                     throw Py.makeException(var1.getglobal("BadZipfile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Bad magic number for file header")));
                  } else {
                     var1.setline(971);
                     var3 = var1.getlocal(4).__getattr__("read").__call__(var2, var1.getlocal(7).__getitem__(var1.getglobal("_FH_FILENAME_LENGTH")));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(972);
                     if (var1.getlocal(7).__getitem__(var1.getglobal("_FH_EXTRA_FIELD_LENGTH")).__nonzero__()) {
                        var1.setline(973);
                        var1.getlocal(4).__getattr__("read").__call__(var2, var1.getlocal(7).__getitem__(var1.getglobal("_FH_EXTRA_FIELD_LENGTH")));
                     }

                     var1.setline(975);
                     var3 = var1.getlocal(8);
                     var10000 = var3._ne(var1.getlocal(6).__getattr__("orig_filename"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(976);
                        throw Py.makeException(var1.getglobal("BadZipfile"), PyString.fromInterned("File name in directory \"%s\" and header \"%s\" differ.")._mod(new PyTuple(new PyObject[]{var1.getlocal(6).__getattr__("orig_filename"), var1.getlocal(8)})));
                     } else {
                        var1.setline(981);
                        var3 = var1.getlocal(6).__getattr__("flag_bits")._and(Py.newInteger(1));
                        var1.setlocal(9, var3);
                        var3 = null;
                        var1.setline(982);
                        var3 = var1.getglobal("None");
                        var1.setlocal(10, var3);
                        var3 = null;
                        var1.setline(983);
                        if (var1.getlocal(9).__nonzero__()) {
                           var1.setline(984);
                           if (var1.getlocal(3).__not__().__nonzero__()) {
                              var1.setline(985);
                              var3 = var1.getlocal(0).__getattr__("pwd");
                              var1.setlocal(3, var3);
                              var3 = null;
                           }

                           var1.setline(986);
                           if (var1.getlocal(3).__not__().__nonzero__()) {
                              var1.setline(987);
                              throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("File %s is encrypted, password required for extraction")._mod(var1.getlocal(1)));
                           }

                           var1.setline(990);
                           var3 = var1.getglobal("_ZipDecrypter").__call__(var2, var1.getlocal(3));
                           var1.setlocal(10, var3);
                           var3 = null;
                           var1.setline(996);
                           var3 = var1.getlocal(4).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(12));
                           var1.setlocal(11, var3);
                           var3 = null;
                           var1.setline(997);
                           var3 = var1.getglobal("map").__call__(var2, var1.getlocal(10), var1.getlocal(11).__getslice__(Py.newInteger(0), Py.newInteger(12), (PyObject)null));
                           var1.setlocal(12, var3);
                           var3 = null;
                           var1.setline(998);
                           if (var1.getlocal(6).__getattr__("flag_bits")._and(Py.newInteger(8)).__nonzero__()) {
                              var1.setline(1000);
                              var3 = var1.getlocal(6).__getattr__("_raw_time")._rshift(Py.newInteger(8))._and(Py.newInteger(255));
                              var1.setlocal(13, var3);
                              var3 = null;
                           } else {
                              var1.setline(1003);
                              var3 = var1.getlocal(6).__getattr__("CRC")._rshift(Py.newInteger(24))._and(Py.newInteger(255));
                              var1.setlocal(13, var3);
                              var3 = null;
                           }

                           var1.setline(1004);
                           var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(12).__getitem__(Py.newInteger(11)));
                           var10000 = var3._ne(var1.getlocal(13));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(1005);
                              throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Bad password for file"), (PyObject)var1.getlocal(1)));
                           }
                        }

                        var1.setline(1007);
                        var10000 = var1.getglobal("ZipExtFile");
                        PyObject[] var6 = new PyObject[]{var1.getlocal(4), var1.getlocal(2), var1.getlocal(6), var1.getlocal(10), var1.getlocal(5)};
                        String[] var4 = new String[]{"close_fileobj"};
                        var10000 = var10000.__call__(var2, var6, var4);
                        var3 = null;
                        var3 = var10000;
                        var1.f_lasti = -1;
                        return var3;
                     }
                  }
               }
            } catch (Throwable var5) {
               Py.setException(var5, var1);
               var1.setline(1010);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(1011);
                  var1.getlocal(4).__getattr__("close").__call__(var2);
               }

               var1.setline(1012);
               throw Py.makeException();
            }
         }
      }
   }

   public PyObject extract$43(PyFrame var1, ThreadState var2) {
      var1.setline(1019);
      PyString.fromInterned("Extract a member from the archive to the current working directory,\n           using its full name. Its file information is extracted as accurately\n           as possible. `member' may be a filename or a ZipInfo object. You can\n           specify a different directory using `path'.\n        ");
      var1.setline(1020);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("ZipInfo")).__not__().__nonzero__()) {
         var1.setline(1021);
         var3 = var1.getlocal(0).__getattr__("getinfo").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1023);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1024);
         var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1026);
      var3 = var1.getlocal(0).__getattr__("_extract_member").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject extractall$44(PyFrame var1, ThreadState var2) {
      var1.setline(1033);
      PyString.fromInterned("Extract all members from the archive to the current working\n           directory. `path' specifies a different directory to extract to.\n           `members' is optional and must be a subset of the list returned\n           by namelist().\n        ");
      var1.setline(1034);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1035);
         var3 = var1.getlocal(0).__getattr__("namelist").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1037);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(1037);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(1038);
         var1.getlocal(0).__getattr__("extract").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getlocal(3));
      }
   }

   public PyObject _extract_member$45(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(1043);
      PyString.fromInterned("Extract the ZipInfo object 'member' to a physical\n           file on the path targetpath.\n        ");
      var1.setline(1046);
      PyObject var3 = var1.getlocal(1).__getattr__("filename").__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("sep"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1048);
      if (var1.getglobal("os").__getattr__("path").__getattr__("altsep").__nonzero__()) {
         var1.setline(1049);
         var3 = var1.getlocal(4).__getattr__("replace").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("altsep"), var1.getglobal("os").__getattr__("path").__getattr__("sep"));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(1052);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(4)).__getitem__(Py.newInteger(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1053);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("sep").__getattr__("join");
      var1.setline(1053);
      PyObject[] var9 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var9, f$46, (PyObject)null);
      PyObject var10002 = var4.__call__(var2, var1.getlocal(4).__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("sep")).__iter__());
      Arrays.fill(var9, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1055);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("sep");
      var10000 = var3._eq(PyString.fromInterned("\\"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1057);
         PyString var14 = PyString.fromInterned(":<>|\"?*");
         var1.setlocal(6, var14);
         var3 = null;
         var1.setline(1058);
         var3 = var1.getglobal("string").__getattr__("maketrans").__call__(var2, var1.getlocal(6), PyString.fromInterned("_")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(6))));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1059);
         var3 = var1.getlocal(4).__getattr__("translate").__call__(var2, var1.getlocal(7));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1061);
         var1.setline(1061);
         var9 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var9, f$47, (PyObject)null);
         var10000 = var4.__call__(var2, var1.getlocal(4).__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("sep")).__iter__());
         Arrays.fill(var9, (Object)null);
         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1062);
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("sep").__getattr__("join");
         var1.setline(1062);
         var9 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var9, f$48, (PyObject)null);
         var10002 = var4.__call__(var2, var1.getlocal(4).__iter__());
         Arrays.fill(var9, (Object)null);
         var3 = var10000.__call__(var2, var10002);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(1064);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(4));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1065);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1068);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(1069);
      var10000 = var1.getlocal(10);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(10)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1070);
         var1.getglobal("os").__getattr__("makedirs").__call__(var2, var1.getlocal(10));
      }

      var1.setline(1072);
      var3 = var1.getlocal(1).__getattr__("filename").__getitem__(Py.newInteger(-1));
      var10000 = var3._eq(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1073);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
            var1.setline(1074);
            var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(2));
         }

         var1.setline(1075);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var10000 = var1.getlocal(0).__getattr__("open");
         PyObject[] var10 = new PyObject[]{var1.getlocal(1), var1.getlocal(3)};
         String[] var5 = new String[]{"pwd"};
         var10000 = var10000.__call__(var2, var10, var5);
         var4 = null;
         ContextManager var12;
         PyObject var11 = (var12 = ContextGuard.getManager(var10000)).__enter__(var2);

         label43: {
            try {
               label55: {
                  var1.setlocal(11, var11);
                  ContextManager var13;
                  PyObject var6 = (var13 = ContextGuard.getManager(var1.getglobal("file").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("wb")))).__enter__(var2);

                  try {
                     var1.setlocal(12, var6);
                     var1.setline(1079);
                     var1.getglobal("shutil").__getattr__("copyfileobj").__call__(var2, var1.getlocal(11), var1.getlocal(12));
                  } catch (Throwable var7) {
                     if (var13.__exit__(var2, Py.setException(var7, var1))) {
                        break label55;
                     }

                     throw (Throwable)Py.makeException();
                  }

                  var13.__exit__(var2, (PyException)null);
               }
            } catch (Throwable var8) {
               if (var12.__exit__(var2, Py.setException(var8, var1))) {
                  break label43;
               }

               throw (Throwable)Py.makeException();
            }

            var12.__exit__(var2, (PyException)null);
         }

         var1.setline(1081);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject f$46(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1053);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var9 = (PyObject)var10000;
      }

      do {
         var1.setline(1053);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(1054);
         PyObject var8 = var1.getlocal(1);
         PyObject[] var7 = new PyObject[]{PyString.fromInterned(""), var1.getglobal("os").__getattr__("path").__getattr__("curdir"), var1.getglobal("os").__getattr__("path").__getattr__("pardir")};
         PyTuple var10 = new PyTuple(var7);
         Arrays.fill(var7, (Object)null);
         var9 = var8._notin(var10);
         var5 = null;
      } while(!var9.__nonzero__());

      var1.setline(1053);
      var1.setline(1053);
      var9 = var1.getlocal(1);
      var1.f_lasti = 1;
      var5 = new Object[8];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var9;
   }

   public PyObject f$47(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1061);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(1061);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(1061);
         var1.setline(1061);
         var6 = var1.getlocal(1).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject f$48(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1062);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      do {
         var1.setline(1062);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(1062);
      } while(!var1.getlocal(1).__nonzero__());

      var1.setline(1062);
      var1.setline(1062);
      var6 = var1.getlocal(1);
      var1.f_lasti = 1;
      var5 = new Object[]{null, null, null, var3, var4};
      var1.f_savedlocals = var5;
      return var6;
   }

   public PyObject _writecheck$49(PyFrame var1, ThreadState var2) {
      var1.setline(1084);
      PyString.fromInterned("Check for errors before writing a file to the archive.");
      var1.setline(1085);
      PyObject var3 = var1.getlocal(1).__getattr__("filename");
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("NameToInfo"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1086);
         if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
            var1.setline(1087);
            Py.printComma(PyString.fromInterned("Duplicate name:"));
            Py.println(var1.getlocal(1).__getattr__("filename"));
         }
      }

      var1.setline(1088);
      var3 = var1.getlocal(0).__getattr__("mode");
      var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("w"), PyString.fromInterned("a")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1089);
         throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("write() requires mode \"w\" or \"a\""));
      } else {
         var1.setline(1090);
         if (var1.getlocal(0).__getattr__("fp").__not__().__nonzero__()) {
            var1.setline(1091);
            throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Attempt to write ZIP archive that was already closed"));
         } else {
            var1.setline(1093);
            var3 = var1.getlocal(1).__getattr__("compress_type");
            var10000 = var3._eq(var1.getglobal("ZIP_DEFLATED"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("zlib").__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(1094);
               throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Compression requires the (missing) zlib module"));
            } else {
               var1.setline(1096);
               var3 = var1.getlocal(1).__getattr__("compress_type");
               var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("ZIP_STORED"), var1.getglobal("ZIP_DEFLATED")}));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1097);
                  throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("That compression method is not supported"));
               } else {
                  var1.setline(1099);
                  var3 = var1.getlocal(1).__getattr__("file_size");
                  var10000 = var3._gt(var1.getglobal("ZIP64_LIMIT"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1100);
                     if (var1.getlocal(0).__getattr__("_allowZip64").__not__().__nonzero__()) {
                        var1.setline(1101);
                        throw Py.makeException(var1.getglobal("LargeZipFile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Filesize would require ZIP64 extensions")));
                     }
                  }

                  var1.setline(1102);
                  var3 = var1.getlocal(1).__getattr__("header_offset");
                  var10000 = var3._gt(var1.getglobal("ZIP64_LIMIT"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1103);
                     if (var1.getlocal(0).__getattr__("_allowZip64").__not__().__nonzero__()) {
                        var1.setline(1104);
                        throw Py.makeException(var1.getglobal("LargeZipFile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Zipfile size would require ZIP64 extensions")));
                     }
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject write$50(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1108);
      PyString.fromInterned("Put the bytes from filename into the archive under the name\n        arcname.");
      var1.setline(1109);
      if (var1.getlocal(0).__getattr__("fp").__not__().__nonzero__()) {
         var1.setline(1110);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Attempt to write to ZIP archive that was already closed")));
      } else {
         var1.setline(1113);
         PyObject var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1114);
         var3 = var1.getglobal("stat").__getattr__("S_ISDIR").__call__(var2, var1.getlocal(4).__getattr__("st_mode"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1115);
         var3 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getlocal(4).__getattr__("st_mtime"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1116);
         var3 = var1.getlocal(6).__getslice__(Py.newInteger(0), Py.newInteger(6), (PyObject)null);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1118);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1119);
            var3 = var1.getlocal(1);
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(1120);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(2)).__getitem__(Py.newInteger(1)));
         var1.setlocal(2, var3);
         var3 = null;

         while(true) {
            var1.setline(1121);
            var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            var10000 = var3._in(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("sep"), var1.getglobal("os").__getattr__("altsep")}));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(1123);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(1124);
                  var3 = var1.getlocal(2);
                  var3 = var3._iadd(PyString.fromInterned("/"));
                  var1.setlocal(2, var3);
               }

               var1.setline(1125);
               var3 = var1.getglobal("ZipInfo").__call__(var2, var1.getlocal(2), var1.getlocal(7));
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(1126);
               var3 = var1.getlocal(4).__getitem__(Py.newInteger(0))._and(Py.newInteger(65535))._lshift(Py.newLong("16"));
               var1.getlocal(8).__setattr__("external_attr", var3);
               var3 = null;
               var1.setline(1127);
               var3 = var1.getlocal(3);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1128);
                  var3 = var1.getlocal(0).__getattr__("compression");
                  var1.getlocal(8).__setattr__("compress_type", var3);
                  var3 = null;
               } else {
                  var1.setline(1130);
                  var3 = var1.getlocal(3);
                  var1.getlocal(8).__setattr__("compress_type", var3);
                  var3 = null;
               }

               var1.setline(1132);
               var3 = var1.getlocal(4).__getattr__("st_size");
               var1.getlocal(8).__setattr__("file_size", var3);
               var3 = null;
               var1.setline(1133);
               PyInteger var7 = Py.newInteger(0);
               var1.getlocal(8).__setattr__((String)"flag_bits", var7);
               var3 = null;
               var1.setline(1134);
               var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
               var1.getlocal(8).__setattr__("header_offset", var3);
               var3 = null;
               var1.setline(1136);
               var1.getlocal(0).__getattr__("_writecheck").__call__(var2, var1.getlocal(8));
               var1.setline(1137);
               var3 = var1.getglobal("True");
               var1.getlocal(0).__setattr__("_didModify", var3);
               var3 = null;
               var1.setline(1139);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(1140);
                  var7 = Py.newInteger(0);
                  var1.getlocal(8).__setattr__((String)"file_size", var7);
                  var3 = null;
                  var1.setline(1141);
                  var7 = Py.newInteger(0);
                  var1.getlocal(8).__setattr__((String)"compress_size", var7);
                  var3 = null;
                  var1.setline(1142);
                  var7 = Py.newInteger(0);
                  var1.getlocal(8).__setattr__((String)"CRC", var7);
                  var3 = null;
                  var1.setline(1143);
                  var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getlocal(8));
                  var1.setline(1144);
                  var3 = var1.getlocal(8);
                  var1.getlocal(0).__getattr__("NameToInfo").__setitem__(var1.getlocal(8).__getattr__("filename"), var3);
                  var3 = null;
                  var1.setline(1145);
                  var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(8).__getattr__("FileHeader").__call__(var2, var1.getglobal("False")));
                  var1.setline(1146);
                  var1.f_lasti = -1;
                  return Py.None;
               } else {
                  ContextManager var8;
                  PyObject var4 = (var8 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb")))).__enter__(var2);

                  label81: {
                     try {
                        var1.setlocal(9, var4);
                        var1.setline(1150);
                        PyInteger var6 = Py.newInteger(0);
                        var1.getlocal(8).__setattr__((String)"CRC", var6);
                        var1.setlocal(10, var6);
                        var1.setline(1151);
                        var6 = Py.newInteger(0);
                        var1.getlocal(8).__setattr__((String)"compress_size", var6);
                        var1.setlocal(11, var6);
                        var1.setline(1153);
                        var10000 = var1.getlocal(0).__getattr__("_allowZip64");
                        if (var10000.__nonzero__()) {
                           var4 = var1.getlocal(8).__getattr__("file_size")._mul(Py.newFloat(1.05));
                           var10000 = var4._gt(var1.getglobal("ZIP64_LIMIT"));
                           var4 = null;
                        }

                        var4 = var10000;
                        var1.setlocal(12, var4);
                        var4 = null;
                        var1.setline(1155);
                        var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(8).__getattr__("FileHeader").__call__(var2, var1.getlocal(12)));
                        var1.setline(1156);
                        var4 = var1.getlocal(8).__getattr__("compress_type");
                        var10000 = var4._eq(var1.getglobal("ZIP_DEFLATED"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1157);
                           var4 = var1.getglobal("zlib").__getattr__("compressobj").__call__((ThreadState)var2, var1.getglobal("zlib").__getattr__("Z_DEFAULT_COMPRESSION"), (PyObject)var1.getglobal("zlib").__getattr__("DEFLATED"), (PyObject)Py.newInteger(-15));
                           var1.setlocal(13, var4);
                           var4 = null;
                        } else {
                           var1.setline(1160);
                           var4 = var1.getglobal("None");
                           var1.setlocal(13, var4);
                           var4 = null;
                        }

                        var1.setline(1161);
                        var6 = Py.newInteger(0);
                        var1.setlocal(14, var6);
                        var4 = null;

                        while(true) {
                           var1.setline(1162);
                           if (!Py.newInteger(1).__nonzero__()) {
                              break;
                           }

                           var1.setline(1163);
                           var4 = var1.getlocal(9).__getattr__("read").__call__(var2, Py.newInteger(1024)._mul(Py.newInteger(8)));
                           var1.setlocal(15, var4);
                           var4 = null;
                           var1.setline(1164);
                           if (var1.getlocal(15).__not__().__nonzero__()) {
                              break;
                           }

                           var1.setline(1166);
                           var4 = var1.getlocal(14)._add(var1.getglobal("len").__call__(var2, var1.getlocal(15)));
                           var1.setlocal(14, var4);
                           var4 = null;
                           var1.setline(1167);
                           var4 = var1.getglobal("crc32").__call__(var2, var1.getlocal(15), var1.getlocal(10))._and(Py.newLong("4294967295"));
                           var1.setlocal(10, var4);
                           var4 = null;
                           var1.setline(1168);
                           if (var1.getlocal(13).__nonzero__()) {
                              var1.setline(1169);
                              var4 = var1.getlocal(13).__getattr__("compress").__call__(var2, var1.getlocal(15));
                              var1.setlocal(15, var4);
                              var4 = null;
                              var1.setline(1170);
                              var4 = var1.getlocal(11)._add(var1.getglobal("len").__call__(var2, var1.getlocal(15)));
                              var1.setlocal(11, var4);
                              var4 = null;
                           }

                           var1.setline(1171);
                           var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(15));
                        }
                     } catch (Throwable var5) {
                        if (var8.__exit__(var2, Py.setException(var5, var1))) {
                           break label81;
                        }

                        throw (Throwable)Py.makeException();
                     }

                     var8.__exit__(var2, (PyException)null);
                  }

                  var1.setline(1172);
                  if (var1.getlocal(13).__nonzero__()) {
                     var1.setline(1173);
                     var3 = var1.getlocal(13).__getattr__("flush").__call__(var2);
                     var1.setlocal(15, var3);
                     var3 = null;
                     var1.setline(1174);
                     var3 = var1.getlocal(11)._add(var1.getglobal("len").__call__(var2, var1.getlocal(15)));
                     var1.setlocal(11, var3);
                     var3 = null;
                     var1.setline(1175);
                     var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(15));
                     var1.setline(1176);
                     var3 = var1.getlocal(11);
                     var1.getlocal(8).__setattr__("compress_size", var3);
                     var3 = null;
                  } else {
                     var1.setline(1178);
                     var3 = var1.getlocal(14);
                     var1.getlocal(8).__setattr__("compress_size", var3);
                     var3 = null;
                  }

                  var1.setline(1179);
                  var3 = var1.getlocal(10);
                  var1.getlocal(8).__setattr__("CRC", var3);
                  var3 = null;
                  var1.setline(1180);
                  var3 = var1.getlocal(14);
                  var1.getlocal(8).__setattr__("file_size", var3);
                  var3 = null;
                  var1.setline(1181);
                  var10000 = var1.getlocal(12).__not__();
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(0).__getattr__("_allowZip64");
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1182);
                     var3 = var1.getlocal(14);
                     var10000 = var3._gt(var1.getglobal("ZIP64_LIMIT"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1183);
                        throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("File size has increased during compressing")));
                     }

                     var1.setline(1184);
                     var3 = var1.getlocal(11);
                     var10000 = var3._gt(var1.getglobal("ZIP64_LIMIT"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1185);
                        throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Compressed size larger than uncompressed size")));
                     }
                  }

                  var1.setline(1188);
                  var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
                  var1.setlocal(16, var3);
                  var3 = null;
                  var1.setline(1189);
                  var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(8).__getattr__("header_offset"), (PyObject)Py.newInteger(0));
                  var1.setline(1190);
                  var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(8).__getattr__("FileHeader").__call__(var2, var1.getlocal(12)));
                  var1.setline(1191);
                  var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(16), (PyObject)Py.newInteger(0));
                  var1.setline(1192);
                  var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getlocal(8));
                  var1.setline(1193);
                  var3 = var1.getlocal(8);
                  var1.getlocal(0).__getattr__("NameToInfo").__setitem__(var1.getlocal(8).__getattr__("filename"), var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }

            var1.setline(1122);
            var3 = var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(2, var3);
            var3 = null;
         }
      }
   }

   public PyObject writestr$51(PyFrame var1, ThreadState var2) {
      var1.setline(1198);
      PyString.fromInterned("Write a file into the archive.  The contents is the string\n        'bytes'.  'zinfo_or_arcname' is either a ZipInfo instance or\n        the name of the file in the archive.");
      var1.setline(1199);
      PyObject var10000;
      PyObject[] var3;
      PyObject var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("ZipInfo")).__not__().__nonzero__()) {
         var1.setline(1200);
         var10000 = var1.getglobal("ZipInfo");
         var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null)};
         String[] var4 = new String[]{"filename", "date_time"};
         var10000 = var10000.__call__(var2, var3, var4);
         var3 = null;
         var5 = var10000;
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(1203);
         var5 = var1.getlocal(0).__getattr__("compression");
         var1.getlocal(4).__setattr__("compress_type", var5);
         var3 = null;
         var1.setline(1204);
         var5 = Py.newInteger(384)._lshift(Py.newInteger(16));
         var1.getlocal(4).__setattr__("external_attr", var5);
         var3 = null;
      } else {
         var1.setline(1206);
         var5 = var1.getlocal(1);
         var1.setlocal(4, var5);
         var3 = null;
      }

      var1.setline(1208);
      if (var1.getlocal(0).__getattr__("fp").__not__().__nonzero__()) {
         var1.setline(1209);
         throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Attempt to write to ZIP archive that was already closed")));
      } else {
         var1.setline(1212);
         var5 = var1.getlocal(3);
         var10000 = var5._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1213);
            var5 = var1.getlocal(3);
            var1.getlocal(4).__setattr__("compress_type", var5);
            var3 = null;
         }

         var1.setline(1215);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var1.getlocal(4).__setattr__("file_size", var5);
         var3 = null;
         var1.setline(1216);
         var5 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
         var1.getlocal(4).__setattr__("header_offset", var5);
         var3 = null;
         var1.setline(1217);
         var1.getlocal(0).__getattr__("_writecheck").__call__(var2, var1.getlocal(4));
         var1.setline(1218);
         var5 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_didModify", var5);
         var3 = null;
         var1.setline(1219);
         var5 = var1.getglobal("crc32").__call__(var2, var1.getlocal(2))._and(Py.newLong("4294967295"));
         var1.getlocal(4).__setattr__("CRC", var5);
         var3 = null;
         var1.setline(1220);
         var5 = var1.getlocal(4).__getattr__("compress_type");
         var10000 = var5._eq(var1.getglobal("ZIP_DEFLATED"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1221);
            var5 = var1.getglobal("zlib").__getattr__("compressobj").__call__((ThreadState)var2, var1.getglobal("zlib").__getattr__("Z_DEFAULT_COMPRESSION"), (PyObject)var1.getglobal("zlib").__getattr__("DEFLATED"), (PyObject)Py.newInteger(-15));
            var1.setlocal(5, var5);
            var3 = null;
            var1.setline(1223);
            var5 = var1.getlocal(5).__getattr__("compress").__call__(var2, var1.getlocal(2))._add(var1.getlocal(5).__getattr__("flush").__call__(var2));
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(1224);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var1.getlocal(4).__setattr__("compress_size", var5);
            var3 = null;
         } else {
            var1.setline(1226);
            var5 = var1.getlocal(4).__getattr__("file_size");
            var1.getlocal(4).__setattr__("compress_size", var5);
            var3 = null;
         }

         var1.setline(1227);
         var5 = var1.getlocal(4).__getattr__("file_size");
         var10000 = var5._gt(var1.getglobal("ZIP64_LIMIT"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var5 = var1.getlocal(4).__getattr__("compress_size");
            var10000 = var5._gt(var1.getglobal("ZIP64_LIMIT"));
            var3 = null;
         }

         var5 = var10000;
         var1.setlocal(6, var5);
         var3 = null;
         var1.setline(1229);
         var10000 = var1.getlocal(6);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_allowZip64").__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1230);
            throw Py.makeException(var1.getglobal("LargeZipFile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Filesize would require ZIP64 extensions")));
         } else {
            var1.setline(1231);
            var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(4).__getattr__("FileHeader").__call__(var2, var1.getlocal(6)));
            var1.setline(1232);
            var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(2));
            var1.setline(1233);
            if (var1.getlocal(4).__getattr__("flag_bits")._and(Py.newInteger(8)).__nonzero__()) {
               var1.setline(1235);
               var1.setline(1235);
               PyString var6 = var1.getlocal(6).__nonzero__() ? PyString.fromInterned("<LQQ") : PyString.fromInterned("<LLL");
               var1.setlocal(7, var6);
               var3 = null;
               var1.setline(1236);
               var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__(var2, var1.getlocal(7), var1.getlocal(4).__getattr__("CRC"), var1.getlocal(4).__getattr__("compress_size"), var1.getlocal(4).__getattr__("file_size")));
            }

            var1.setline(1238);
            var1.getlocal(0).__getattr__("fp").__getattr__("flush").__call__(var2);
            var1.setline(1239);
            var1.getlocal(0).__getattr__("filelist").__getattr__("append").__call__(var2, var1.getlocal(4));
            var1.setline(1240);
            var5 = var1.getlocal(4);
            var1.getlocal(0).__getattr__("NameToInfo").__setitem__(var1.getlocal(4).__getattr__("filename"), var5);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __del__$52(PyFrame var1, ThreadState var2) {
      var1.setline(1243);
      PyString.fromInterned("Call the \"close()\" method in case the user forgot.");
      var1.setline(1244);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$53(PyFrame var1, ThreadState var2) {
      var1.setline(1248);
      PyString.fromInterned("Close the file, and for mode \"w\" and \"a\" write the ending\n        records.");
      var1.setline(1249);
      PyObject var3 = var1.getlocal(0).__getattr__("fp");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1250);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var3 = null;

         PyObject var4;
         try {
            var1.setline(1253);
            var4 = var1.getlocal(0).__getattr__("mode");
            var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("w"), PyString.fromInterned("a")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_didModify");
            }

            if (var10000.__nonzero__()) {
               var1.setline(1254);
               PyInteger var11 = Py.newInteger(0);
               var1.setlocal(1, var11);
               var4 = null;
               var1.setline(1255);
               var4 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(1256);
               var4 = var1.getlocal(0).__getattr__("filelist").__iter__();

               while(true) {
                  var1.setline(1256);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(1316);
                     var4 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
                     var1.setlocal(17, var4);
                     var4 = null;
                     var1.setline(1318);
                     var4 = var1.getlocal(1);
                     var1.setlocal(18, var4);
                     var4 = null;
                     var1.setline(1319);
                     var4 = var1.getlocal(17)._sub(var1.getlocal(2));
                     var1.setlocal(19, var4);
                     var4 = null;
                     var1.setline(1320);
                     var4 = var1.getlocal(2);
                     var1.setlocal(20, var4);
                     var4 = null;
                     var1.setline(1321);
                     var4 = var1.getlocal(18);
                     var10000 = var4._ge(var1.getglobal("ZIP_FILECOUNT_LIMIT"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        var4 = var1.getlocal(20);
                        var10000 = var4._gt(var1.getglobal("ZIP64_LIMIT"));
                        var4 = null;
                        if (!var10000.__nonzero__()) {
                           var4 = var1.getlocal(19);
                           var10000 = var4._gt(var1.getglobal("ZIP64_LIMIT"));
                           var4 = null;
                        }
                     }

                     PyObject[] var16;
                     if (var10000.__nonzero__()) {
                        var1.setline(1325);
                        var10000 = var1.getglobal("struct").__getattr__("pack");
                        var16 = new PyObject[]{var1.getglobal("structEndArchive64"), var1.getglobal("stringEndArchive64"), Py.newInteger(44), Py.newInteger(45), Py.newInteger(45), Py.newInteger(0), Py.newInteger(0), var1.getlocal(18), var1.getlocal(18), var1.getlocal(19), var1.getlocal(20)};
                        var4 = var10000.__call__(var2, var16);
                        var1.setlocal(21, var4);
                        var4 = null;
                        var1.setline(1329);
                        var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(21));
                        var1.setline(1331);
                        var10000 = var1.getglobal("struct").__getattr__("pack");
                        var16 = new PyObject[]{var1.getglobal("structEndArchive64Locator"), var1.getglobal("stringEndArchive64Locator"), Py.newInteger(0), var1.getlocal(17), Py.newInteger(1)};
                        var4 = var10000.__call__(var2, var16);
                        var1.setlocal(22, var4);
                        var4 = null;
                        var1.setline(1334);
                        var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(22));
                        var1.setline(1335);
                        var4 = var1.getglobal("min").__call__((ThreadState)var2, (PyObject)var1.getlocal(18), (PyObject)Py.newInteger(65535));
                        var1.setlocal(18, var4);
                        var4 = null;
                        var1.setline(1336);
                        var4 = var1.getglobal("min").__call__((ThreadState)var2, (PyObject)var1.getlocal(19), (PyObject)Py.newLong("4294967295"));
                        var1.setlocal(19, var4);
                        var4 = null;
                        var1.setline(1337);
                        var4 = var1.getglobal("min").__call__((ThreadState)var2, (PyObject)var1.getlocal(20), (PyObject)Py.newLong("4294967295"));
                        var1.setlocal(20, var4);
                        var4 = null;
                     }

                     var1.setline(1339);
                     var10000 = var1.getglobal("struct").__getattr__("pack");
                     var16 = new PyObject[]{var1.getglobal("structEndArchive"), var1.getglobal("stringEndArchive"), Py.newInteger(0), Py.newInteger(0), var1.getlocal(18), var1.getlocal(18), var1.getlocal(19), var1.getlocal(20), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_comment"))};
                     var4 = var10000.__call__(var2, var16);
                     var1.setlocal(23, var4);
                     var4 = null;
                     var1.setline(1342);
                     var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(23));
                     var1.setline(1343);
                     var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("_comment"));
                     var1.setline(1344);
                     var1.getlocal(0).__getattr__("fp").__getattr__("flush").__call__(var2);
                     break;
                  }

                  var1.setlocal(3, var5);
                  var1.setline(1257);
                  PyObject var6 = var1.getlocal(1)._add(Py.newInteger(1));
                  var1.setlocal(1, var6);
                  var6 = null;
                  var1.setline(1258);
                  var6 = var1.getlocal(3).__getattr__("date_time");
                  var1.setlocal(4, var6);
                  var6 = null;
                  var1.setline(1259);
                  var6 = var1.getlocal(4).__getitem__(Py.newInteger(0))._sub(Py.newInteger(1980))._lshift(Py.newInteger(9))._or(var1.getlocal(4).__getitem__(Py.newInteger(1))._lshift(Py.newInteger(5)))._or(var1.getlocal(4).__getitem__(Py.newInteger(2)));
                  var1.setlocal(5, var6);
                  var6 = null;
                  var1.setline(1260);
                  var6 = var1.getlocal(4).__getitem__(Py.newInteger(3))._lshift(Py.newInteger(11))._or(var1.getlocal(4).__getitem__(Py.newInteger(4))._lshift(Py.newInteger(5)))._or(var1.getlocal(4).__getitem__(Py.newInteger(5))._floordiv(Py.newInteger(2)));
                  var1.setlocal(6, var6);
                  var6 = null;
                  var1.setline(1261);
                  PyList var14 = new PyList(Py.EmptyObjects);
                  var1.setlocal(7, var14);
                  var6 = null;
                  var1.setline(1262);
                  var6 = var1.getlocal(3).__getattr__("file_size");
                  var10000 = var6._gt(var1.getglobal("ZIP64_LIMIT"));
                  var6 = null;
                  if (!var10000.__nonzero__()) {
                     var6 = var1.getlocal(3).__getattr__("compress_size");
                     var10000 = var6._gt(var1.getglobal("ZIP64_LIMIT"));
                     var6 = null;
                  }

                  PyLong var15;
                  if (var10000.__nonzero__()) {
                     var1.setline(1264);
                     var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(3).__getattr__("file_size"));
                     var1.setline(1265);
                     var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(3).__getattr__("compress_size"));
                     var1.setline(1266);
                     var15 = Py.newLong("4294967295");
                     var1.setlocal(8, var15);
                     var6 = null;
                     var1.setline(1267);
                     var15 = Py.newLong("4294967295");
                     var1.setlocal(9, var15);
                     var6 = null;
                  } else {
                     var1.setline(1269);
                     var6 = var1.getlocal(3).__getattr__("file_size");
                     var1.setlocal(8, var6);
                     var6 = null;
                     var1.setline(1270);
                     var6 = var1.getlocal(3).__getattr__("compress_size");
                     var1.setlocal(9, var6);
                     var6 = null;
                  }

                  var1.setline(1272);
                  var6 = var1.getlocal(3).__getattr__("header_offset");
                  var10000 = var6._gt(var1.getglobal("ZIP64_LIMIT"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1273);
                     var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(3).__getattr__("header_offset"));
                     var1.setline(1274);
                     var15 = Py.newLong("4294967295");
                     var1.setlocal(10, var15);
                     var6 = null;
                  } else {
                     var1.setline(1276);
                     var6 = var1.getlocal(3).__getattr__("header_offset");
                     var1.setlocal(10, var6);
                     var6 = null;
                  }

                  var1.setline(1278);
                  var6 = var1.getlocal(3).__getattr__("extra");
                  var1.setlocal(11, var6);
                  var6 = null;
                  var1.setline(1279);
                  PyObject[] var17;
                  if (var1.getlocal(7).__nonzero__()) {
                     var1.setline(1281);
                     var10000 = var1.getglobal("struct").__getattr__("pack");
                     var17 = new PyObject[]{PyString.fromInterned("<HH")._add(PyString.fromInterned("Q")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(7)))), Py.newInteger(1), Py.newInteger(8)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(7)))};
                     String[] var7 = new String[0];
                     var10000 = var10000._callextra(var17, var7, var1.getlocal(7), (PyObject)null);
                     var6 = null;
                     var6 = var10000._add(var1.getlocal(11));
                     var1.setlocal(11, var6);
                     var6 = null;
                     var1.setline(1285);
                     var6 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(45), (PyObject)var1.getlocal(3).__getattr__("extract_version"));
                     var1.setlocal(12, var6);
                     var6 = null;
                     var1.setline(1286);
                     var6 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(45), (PyObject)var1.getlocal(3).__getattr__("create_version"));
                     var1.setlocal(13, var6);
                     var6 = null;
                  } else {
                     var1.setline(1288);
                     var6 = var1.getlocal(3).__getattr__("extract_version");
                     var1.setlocal(12, var6);
                     var6 = null;
                     var1.setline(1289);
                     var6 = var1.getlocal(3).__getattr__("create_version");
                     var1.setlocal(13, var6);
                     var6 = null;
                  }

                  try {
                     var1.setline(1292);
                     var6 = var1.getlocal(3).__getattr__("_encodeFilenameFlags").__call__(var2);
                     PyObject[] var13 = Py.unpackSequence(var6, 2);
                     PyObject var8 = var13[0];
                     var1.setlocal(14, var8);
                     var8 = null;
                     var8 = var13[1];
                     var1.setlocal(15, var8);
                     var8 = null;
                     var6 = null;
                     var1.setline(1293);
                     var10000 = var1.getglobal("struct").__getattr__("pack");
                     var17 = new PyObject[]{var1.getglobal("structCentralDir"), var1.getglobal("stringCentralDir"), var1.getlocal(13), var1.getlocal(3).__getattr__("create_system"), var1.getlocal(12), var1.getlocal(3).__getattr__("reserved"), var1.getlocal(15), var1.getlocal(3).__getattr__("compress_type"), var1.getlocal(6), var1.getlocal(5), var1.getlocal(3).__getattr__("CRC"), var1.getlocal(9), var1.getlocal(8), var1.getglobal("len").__call__(var2, var1.getlocal(14)), var1.getglobal("len").__call__(var2, var1.getlocal(11)), var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("comment")), Py.newInteger(0), var1.getlocal(3).__getattr__("internal_attr"), var1.getlocal(3).__getattr__("external_attr"), var1.getlocal(10)};
                     var6 = var10000.__call__(var2, var17);
                     var1.setlocal(16, var6);
                     var6 = null;
                  } catch (Throwable var9) {
                     PyException var18 = Py.setException(var9, var1);
                     if (var18.match(var1.getglobal("DeprecationWarning"))) {
                        var1.setline(1302);
                        PyObject var12 = var1.getglobal("sys").__getattr__("stderr");
                        Py.println(var12, new PyTuple(new PyObject[]{var1.getglobal("structCentralDir"), var1.getglobal("stringCentralDir"), var1.getlocal(13), var1.getlocal(3).__getattr__("create_system"), var1.getlocal(12), var1.getlocal(3).__getattr__("reserved"), var1.getlocal(3).__getattr__("flag_bits"), var1.getlocal(3).__getattr__("compress_type"), var1.getlocal(6), var1.getlocal(5), var1.getlocal(3).__getattr__("CRC"), var1.getlocal(9), var1.getlocal(8), var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("filename")), var1.getglobal("len").__call__(var2, var1.getlocal(11)), var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("comment")), Py.newInteger(0), var1.getlocal(3).__getattr__("internal_attr"), var1.getlocal(3).__getattr__("external_attr"), var1.getlocal(10)}));
                        var1.setline(1310);
                        throw Py.makeException();
                     }

                     throw var18;
                  }

                  var1.setline(1311);
                  var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(16));
                  var1.setline(1312);
                  var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(14));
                  var1.setline(1313);
                  var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(11));
                  var1.setline(1314);
                  var1.getlocal(0).__getattr__("fp").__getattr__("write").__call__(var2, var1.getlocal(3).__getattr__("comment"));
               }
            }
         } catch (Throwable var10) {
            Py.addTraceback(var10, var1);
            var1.setline(1346);
            var4 = var1.getlocal(0).__getattr__("fp");
            var1.setlocal(24, var4);
            var4 = null;
            var1.setline(1347);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("fp", var4);
            var4 = null;
            var1.setline(1348);
            if (var1.getlocal(0).__getattr__("_filePassed").__not__().__nonzero__()) {
               var1.setline(1349);
               var1.getlocal(24).__getattr__("close").__call__(var2);
            }

            throw (Throwable)var10;
         }

         var1.setline(1346);
         var4 = var1.getlocal(0).__getattr__("fp");
         var1.setlocal(24, var4);
         var4 = null;
         var1.setline(1347);
         var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("fp", var4);
         var4 = null;
         var1.setline(1348);
         if (var1.getlocal(0).__getattr__("_filePassed").__not__().__nonzero__()) {
            var1.setline(1349);
            var1.getlocal(24).__getattr__("close").__call__(var2);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject PyZipFile$54(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class to create ZIP archives with Python library files and packages."));
      var1.setline(1353);
      PyString.fromInterned("Class to create ZIP archives with Python library files and packages.");
      var1.setline(1355);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, writepy$55, PyString.fromInterned("Add all files from \"pathname\" to the ZIP archive.\n\n        If pathname is a package directory, search the directory and\n        all package subdirectories recursively for all *.py and enter\n        the modules into the archive.  If pathname is a plain\n        directory, listdir *.py and enter all modules.  Else, pathname\n        must be a Python *.py file and the module will be put into the\n        archive.  Added modules are always module.pyo or module.pyc.\n        This method will compile the module.py into module.pyc if\n        necessary.\n        "));
      var1.setlocal("writepy", var4);
      var3 = null;
      var1.setline(1420);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_codename$56, PyString.fromInterned("Return (filename, archivename) for the path.\n\n        Given a module name path, return the correct file path and\n        archive name, compiling if necessary.  For example, given\n        /python/lib/string, return (/python/lib/string.pyc, string).\n        "));
      var1.setlocal("_get_codename", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject writepy$55(PyFrame var1, ThreadState var2) {
      var1.setline(1366);
      PyString.fromInterned("Add all files from \"pathname\" to the ZIP archive.\n\n        If pathname is a package directory, search the directory and\n        all package subdirectories recursively for all *.py and enter\n        the modules into the archive.  If pathname is a plain\n        directory, listdir *.py and enter all modules.  Else, pathname\n        must be a Python *.py file and the module will be put into the\n        archive.  Added modules are always module.pyo or module.pyc.\n        This method will compile the module.py into module.pyc if\n        necessary.\n        ");
      var1.setline(1367);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1368);
      PyObject var10000;
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1369);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__init__.py"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1370);
         PyObject[] var6;
         PyObject var7;
         PyObject var8;
         if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(5)).__nonzero__()) {
            var1.setline(1372);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1373);
               var3 = PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4)}));
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(1375);
               var3 = var1.getlocal(4);
               var1.setlocal(2, var3);
               var3 = null;
            }

            var1.setline(1376);
            if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
               var1.setline(1377);
               Py.printComma(PyString.fromInterned("Adding package in"));
               Py.printComma(var1.getlocal(1));
               Py.printComma(PyString.fromInterned("as"));
               Py.println(var1.getlocal(2));
            }

            var1.setline(1378);
            var3 = var1.getlocal(0).__getattr__("_get_codename").__call__(var2, var1.getlocal(5).__getslice__(Py.newInteger(0), Py.newInteger(-3), (PyObject)null), var1.getlocal(2));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
            var1.setline(1379);
            if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
               var1.setline(1380);
               Py.printComma(PyString.fromInterned("Adding"));
               Py.println(var1.getlocal(7));
            }

            var1.setline(1381);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(6), var1.getlocal(7));
            var1.setline(1382);
            var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(1));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(1383);
            var1.getlocal(8).__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__init__.py"));
            var1.setline(1385);
            var3 = var1.getlocal(8).__iter__();

            while(true) {
               var1.setline(1385);
               var8 = var3.__iternext__();
               if (var8 == null) {
                  break;
               }

               var1.setlocal(9, var8);
               var1.setline(1386);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(9));
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(1387);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(9));
               var6 = Py.unpackSequence(var5, 2);
               var7 = var6[0];
               var1.setlocal(11, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(12, var7);
               var7 = null;
               var5 = null;
               var1.setline(1388);
               if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(10)).__nonzero__()) {
                  var1.setline(1389);
                  if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(10), (PyObject)PyString.fromInterned("__init__.py"))).__nonzero__()) {
                     var1.setline(1391);
                     var1.getlocal(0).__getattr__("writepy").__call__(var2, var1.getlocal(10), var1.getlocal(2));
                  }
               } else {
                  var1.setline(1392);
                  var5 = var1.getlocal(12);
                  var10000 = var5._eq(PyString.fromInterned(".py"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1393);
                     var5 = var1.getlocal(0).__getattr__("_get_codename").__call__(var2, var1.getlocal(10).__getslice__(Py.newInteger(0), Py.newInteger(-3), (PyObject)null), var1.getlocal(2));
                     var6 = Py.unpackSequence(var5, 2);
                     var7 = var6[0];
                     var1.setlocal(6, var7);
                     var7 = null;
                     var7 = var6[1];
                     var1.setlocal(7, var7);
                     var7 = null;
                     var5 = null;
                     var1.setline(1395);
                     if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
                        var1.setline(1396);
                        Py.printComma(PyString.fromInterned("Adding"));
                        Py.println(var1.getlocal(7));
                     }

                     var1.setline(1397);
                     var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(6), var1.getlocal(7));
                  }
               }
            }
         } else {
            var1.setline(1400);
            if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
               var1.setline(1401);
               Py.printComma(PyString.fromInterned("Adding files from directory"));
               Py.println(var1.getlocal(1));
            }

            var1.setline(1402);
            var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(1)).__iter__();

            while(true) {
               var1.setline(1402);
               var8 = var3.__iternext__();
               if (var8 == null) {
                  break;
               }

               var1.setlocal(9, var8);
               var1.setline(1403);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(9));
               var1.setlocal(10, var5);
               var5 = null;
               var1.setline(1404);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(9));
               var6 = Py.unpackSequence(var5, 2);
               var7 = var6[0];
               var1.setlocal(11, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(12, var7);
               var7 = null;
               var5 = null;
               var1.setline(1405);
               var5 = var1.getlocal(12);
               var10000 = var5._eq(PyString.fromInterned(".py"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1406);
                  var5 = var1.getlocal(0).__getattr__("_get_codename").__call__(var2, var1.getlocal(10).__getslice__(Py.newInteger(0), Py.newInteger(-3), (PyObject)null), var1.getlocal(2));
                  var6 = Py.unpackSequence(var5, 2);
                  var7 = var6[0];
                  var1.setlocal(6, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(7, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(1408);
                  if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
                     var1.setline(1409);
                     Py.printComma(PyString.fromInterned("Adding"));
                     Py.println(var1.getlocal(7));
                  }

                  var1.setline(1410);
                  var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(6), var1.getlocal(7));
               }
            }
         }
      } else {
         var1.setline(1412);
         var3 = var1.getlocal(1).__getslice__(Py.newInteger(-3), (PyObject)null, (PyObject)null);
         var10000 = var3._ne(PyString.fromInterned(".py"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1413);
            throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("Files added with writepy() must end with \".py\""));
         }

         var1.setline(1415);
         var3 = var1.getlocal(0).__getattr__("_get_codename").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(0), Py.newInteger(-3), (PyObject)null), var1.getlocal(2));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(1416);
         if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
            var1.setline(1417);
            Py.printComma(PyString.fromInterned("Adding file"));
            Py.println(var1.getlocal(7));
         }

         var1.setline(1418);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(6), var1.getlocal(7));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_codename$56(PyFrame var1, ThreadState var2) {
      var1.setline(1426);
      PyString.fromInterned("Return (filename, archivename) for the path.\n\n        Given a module name path, return the correct file path and\n        archive name, compiling if necessary.  For example, given\n        /python/lib/string, return (/python/lib/string.pyc, string).\n        ");
      var1.setline(1427);
      PyObject var3 = var1.getlocal(1)._add(PyString.fromInterned(".py"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1428);
      if (var1.getglobal("_is_jython").__nonzero__()) {
         var1.setline(1429);
         var3 = imp.importOne("imp", var1, -1);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1430);
         var3 = var1.getlocal(4).__getattr__("_makeCompiledFilename").__call__(var2, var1.getlocal(3));
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(1432);
         var3 = var1.getlocal(1)._add(PyString.fromInterned(".pyc"));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(1433);
      var3 = var1.getlocal(1)._add(PyString.fromInterned(".pyo"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1434);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(6));
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(6)).__getattr__("st_mtime");
         var10000 = var3._ge(var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(3)).__getattr__("st_mtime"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1436);
         var3 = var1.getlocal(6);
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(1437);
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(5)).__not__();
         if (!var10000.__nonzero__()) {
            var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(5)).__getattr__("st_mtime");
            var10000 = var3._lt(var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(3)).__getattr__("st_mtime"));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(1448);
            var3 = var1.getlocal(5);
            var1.setlocal(7, var3);
            var3 = null;
         } else {
            var1.setline(1439);
            var3 = imp.importOne("py_compile", var1, -1);
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(1440);
            if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
               var1.setline(1441);
               Py.printComma(PyString.fromInterned("Compiling"));
               Py.println(var1.getlocal(3));
            }

            try {
               var1.setline(1443);
               var1.getlocal(8).__getattr__("compile").__call__(var2, var1.getlocal(3), var1.getlocal(5), var1.getglobal("None"), var1.getglobal("True"));
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (!var6.match(var1.getlocal(8).__getattr__("PyCompileError"))) {
                  throw var6;
               }

               PyObject var4 = var6.value;
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(1445);
               Py.println(var1.getlocal(9).__getattr__("msg"));
            }

            var1.setline(1446);
            var3 = var1.getlocal(5);
            var1.setlocal(7, var3);
            var3 = null;
         }
      }

      var1.setline(1449);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(7)).__getitem__(Py.newInteger(1));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(1450);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1451);
         var3 = PyString.fromInterned("%s/%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(10)}));
         var1.setlocal(10, var3);
         var3 = null;
      }

      var1.setline(1452);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(10)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject main$57(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[5];
      var1.setline(1456);
      PyObject var3 = imp.importOne("textwrap", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1457);
      var3 = var1.getlocal(1).__getattr__("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("        Usage:\n            zipfile.py -l zipfile.zip        # Show listing of a zipfile\n            zipfile.py -t zipfile.zip        # Test if a zipfile is valid\n            zipfile.py -e zipfile.zip target # Extract zipfile into target dir\n            zipfile.py -c zipfile.zip src ... # Create zipfile from sources\n        "));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1464);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1465);
         var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(1467);
      var10000 = var1.getlocal(0).__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("-l"), PyString.fromInterned("-c"), PyString.fromInterned("-e"), PyString.fromInterned("-t")}));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1468);
         Py.println(var1.getlocal(2));
         var1.setline(1469);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      var1.setline(1471);
      var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var10000 = var3._eq(PyString.fromInterned("-l"));
      var3 = null;
      PyObject var4;
      ContextManager var15;
      if (var10000.__nonzero__()) {
         label137: {
            var1.setline(1472);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var10000 = var3._ne(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1473);
               Py.println(var1.getlocal(2));
               var1.setline(1474);
               var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }

            var4 = (var15 = ContextGuard.getManager(var1.getglobal("ZipFile").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("r")))).__enter__(var2);

            try {
               var1.setlocal(3, var4);
               var1.setline(1476);
               var1.getlocal(3).__getattr__("printdir").__call__(var2);
            } catch (Throwable var9) {
               if (var15.__exit__(var2, Py.setException(var9, var1))) {
                  break label137;
               }

               throw (Throwable)Py.makeException();
            }

            var15.__exit__(var2, (PyException)null);
         }
      } else {
         var1.setline(1478);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("-t"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1488);
            var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
            var10000 = var3._eq(PyString.fromInterned("-e"));
            var3 = null;
            PyObject var5;
            if (var10000.__nonzero__()) {
               label139: {
                  var1.setline(1489);
                  var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
                  var10000 = var3._ne(Py.newInteger(3));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1490);
                     Py.println(var1.getlocal(2));
                     var1.setline(1491);
                     var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  }

                  var4 = (var15 = ContextGuard.getManager(var1.getglobal("ZipFile").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("r")))).__enter__(var2);

                  try {
                     var1.setlocal(3, var4);
                     var1.setline(1494);
                     var4 = var1.getlocal(0).__getitem__(Py.newInteger(2));
                     var1.setlocal(5, var4);
                     var4 = null;
                     var1.setline(1495);
                     var4 = var1.getlocal(3).__getattr__("namelist").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(1495);
                        var5 = var4.__iternext__();
                        if (var5 == null) {
                           break;
                        }

                        var1.setlocal(6, var5);
                        var1.setline(1496);
                        PyObject var6;
                        if (var1.getlocal(6).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("./")).__nonzero__()) {
                           var1.setline(1497);
                           var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(6).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
                           var1.setlocal(7, var6);
                           var6 = null;
                        } else {
                           var1.setline(1499);
                           var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(6));
                           var1.setlocal(7, var6);
                           var6 = null;
                        }

                        var1.setline(1501);
                        var6 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(7));
                        var1.setlocal(8, var6);
                        var6 = null;
                        var1.setline(1502);
                        if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(8)).__not__().__nonzero__()) {
                           var1.setline(1503);
                           var1.getglobal("os").__getattr__("makedirs").__call__(var2, var1.getlocal(8));
                        }

                        ContextManager var14;
                        PyObject var7 = (var14 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("wb")))).__enter__(var2);

                        try {
                           var1.setlocal(9, var7);
                           var1.setline(1505);
                           var1.getlocal(9).__getattr__("write").__call__(var2, var1.getlocal(3).__getattr__("read").__call__(var2, var1.getlocal(6)));
                        } catch (Throwable var8) {
                           if (var14.__exit__(var2, Py.setException(var8, var1))) {
                              continue;
                           }

                           throw (Throwable)Py.makeException();
                        }

                        var14.__exit__(var2, (PyException)null);
                     }
                  } catch (Throwable var10) {
                     if (var15.__exit__(var2, Py.setException(var10, var1))) {
                        break label139;
                     }

                     throw (Throwable)Py.makeException();
                  }

                  var15.__exit__(var2, (PyException)null);
               }
            } else {
               var1.setline(1507);
               var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
               var10000 = var3._eq(PyString.fromInterned("-c"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  label144: {
                     var1.setline(1508);
                     var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
                     var10000 = var3._lt(Py.newInteger(3));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1509);
                        Py.println(var1.getlocal(2));
                        var1.setline(1510);
                        var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                     }

                     var1.setline(1512);
                     PyObject[] var16 = Py.EmptyObjects;
                     PyObject var10002 = var1.f_globals;
                     PyObject[] var10003 = var16;
                     PyCode var10004 = addToZip$58;
                     var16 = new PyObject[]{var1.getclosure(0)};
                     PyFunction var17 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var16);
                     var1.setderef(0, var17);
                     var3 = null;
                     var10000 = var1.getglobal("ZipFile");
                     var16 = new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(1)), PyString.fromInterned("w"), var1.getglobal("True")};
                     String[] var13 = new String[]{"allowZip64"};
                     var10000 = var10000.__call__(var2, var16, var13);
                     var3 = null;
                     var4 = (var15 = ContextGuard.getManager(var10000)).__enter__(var2);

                     try {
                        var1.setlocal(3, var4);
                        var1.setline(1522);
                        var4 = var1.getlocal(0).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null).__iter__();

                        while(true) {
                           var1.setline(1522);
                           var5 = var4.__iternext__();
                           if (var5 == null) {
                              break;
                           }

                           var1.setlocal(10, var5);
                           var1.setline(1523);
                           var1.getderef(0).__call__(var2, var1.getlocal(3), var1.getlocal(10), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(10)));
                        }
                     } catch (Throwable var11) {
                        if (var15.__exit__(var2, Py.setException(var11, var1))) {
                           break label144;
                        }

                        throw (Throwable)Py.makeException();
                     }

                     var15.__exit__(var2, (PyException)null);
                  }
               }
            }
         } else {
            var1.setline(1479);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var10000 = var3._ne(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1480);
               Py.println(var1.getlocal(2));
               var1.setline(1481);
               var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }

            var4 = (var15 = ContextGuard.getManager(var1.getglobal("ZipFile").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getitem__(Py.newInteger(1)), (PyObject)PyString.fromInterned("r")))).__enter__(var2);

            label122: {
               try {
                  var1.setlocal(3, var4);
                  var1.setline(1483);
                  var4 = var1.getlocal(3).__getattr__("testzip").__call__(var2);
                  var1.setlocal(4, var4);
                  var4 = null;
               } catch (Throwable var12) {
                  if (var15.__exit__(var2, Py.setException(var12, var1))) {
                     break label122;
                  }

                  throw (Throwable)Py.makeException();
               }

               var15.__exit__(var2, (PyException)null);
            }

            var1.setline(1484);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(1485);
               Py.println(PyString.fromInterned("The following enclosed file is corrupted: {!r}").__getattr__("format").__call__(var2, var1.getlocal(4)));
            }

            var1.setline(1486);
            Py.println(PyString.fromInterned("Done testing"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addToZip$58(PyFrame var1, ThreadState var2) {
      var1.setline(1513);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(1514);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("ZIP_DEFLATED"));
      } else {
         var1.setline(1515);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(1516);
            PyObject var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(1)).__iter__();

            while(true) {
               var1.setline(1516);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(3, var4);
               var1.setline(1517);
               var1.getderef(0).__call__(var2, var1.getlocal(0), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(3)), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public zipfile$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BadZipfile$1 = Py.newCode(0, var2, var1, "BadZipfile", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      LargeZipFile$2 = Py.newCode(0, var2, var1, "LargeZipFile", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"fp"};
      _check_zipfile$3 = Py.newCode(1, var2, var1, "_check_zipfile", 136, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "result", "fp"};
      is_zipfile$4 = Py.newCode(1, var2, var1, "is_zipfile", 144, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fpin", "offset", "endrec", "data", "sig", "diskno", "reloff", "disks", "sz", "create_version", "read_version", "disk_num", "disk_dir", "dircount", "dircount2", "dirsize", "diroffset"};
      _EndRecData64$5 = Py.newCode(3, var2, var1, "_EndRecData64", 160, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fpin", "filesize", "data", "endrec", "maxCommentStart", "start", "recData", "commentSize", "comment"};
      _EndRecData$6 = Py.newCode(1, var2, var1, "_EndRecData", 203, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ZipInfo$7 = Py.newCode(0, var2, var1, "ZipInfo", 264, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "date_time", "null_byte"};
      __init__$8 = Py.newCode(3, var2, var1, "__init__", 289, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "zip64", "dt", "dosdate", "dostime", "CRC", "compress_size", "file_size", "extra", "fmt", "filename", "flag_bits", "header"};
      FileHeader$9 = Py.newCode(2, var2, var1, "FileHeader", 331, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _encodeFilenameFlags$10 = Py.newCode(1, var2, var1, "_encodeFilenameFlags", 370, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _decodeFilename$11 = Py.newCode(1, var2, var1, "_decodeFilename", 379, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "extra", "unpack", "tp", "ln", "counts", "idx", "old"};
      _decodeExtra$12 = Py.newCode(1, var2, var1, "_decodeExtra", 385, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ZipDecrypter$13 = Py.newCode(0, var2, var1, "_ZipDecrypter", 422, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"poly", "table", "i", "crc", "j"};
      _GenerateCRCTable$14 = Py.newCode(0, var2, var1, "_GenerateCRCTable", 435, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ch", "crc"};
      _crc32$15 = Py.newCode(3, var2, var1, "_crc32", 455, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pwd", "p"};
      __init__$16 = Py.newCode(2, var2, var1, "__init__", 459, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c"};
      _UpdateKeys$17 = Py.newCode(2, var2, var1, "_UpdateKeys", 466, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "c", "k"};
      __call__$18 = Py.newCode(2, var2, var1, "__call__", 472, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ZipExtFile$19 = Py.newCode(0, var2, var1, "ZipExtFile", 503, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fileobj", "mode", "zipinfo", "decrypter", "close_fileobj", "descr"};
      __init__$20 = Py.newCode(6, var2, var1, "__init__", 517, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "limit", "i", "line", "readahead", "match", "newline", "chunk"};
      readline$21 = Py.newCode(2, var2, var1, "readline", 557, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "chunk"};
      peek$22 = Py.newCode(2, var2, var1, "peek", 607, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      readable$23 = Py.newCode(1, var2, var1, "readable", 616, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "buf", "data"};
      read$24 = Py.newCode(2, var2, var1, "read", 619, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "newdata", "eof"};
      _update_crc$25 = Py.newCode(3, var2, var1, "_update_crc", 637, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "len_readbuffer", "nbytes", "data", "eof"};
      read1$26 = Py.newCode(2, var2, var1, "read1", 647, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$27 = Py.newCode(1, var2, var1, "close", 699, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ZipFile$28 = Py.newCode(0, var2, var1, "ZipFile", 707, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "mode", "compression", "allowZip64", "key", "modeDict", "fp"};
      __init__$29 = Py.newCode(5, var2, var1, "__init__", 724, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$30 = Py.newCode(1, var2, var1, "__enter__", 795, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "type", "value", "traceback"};
      __exit__$31 = Py.newCode(4, var2, var1, "__exit__", 798, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "endrec", "size_cd", "offset_cd", "concat", "inferred", "data", "total", "centdir", "filename", "x", "t", "d"};
      _RealGetContents$32 = Py.newCode(1, var2, var1, "_RealGetContents", 801, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "l", "data"};
      namelist$33 = Py.newCode(1, var2, var1, "namelist", 870, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      infolist$34 = Py.newCode(1, var2, var1, "infolist", 877, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "zinfo", "date"};
      printdir$35 = Py.newCode(1, var2, var1, "printdir", 882, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chunk_size", "zinfo", "f"};
      testzip$36 = Py.newCode(1, var2, var1, "testzip", 889, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "info"};
      getinfo$37 = Py.newCode(2, var2, var1, "getinfo", 902, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pwd"};
      setpassword$38 = Py.newCode(2, var2, var1, "setpassword", 911, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      comment$39 = Py.newCode(1, var2, var1, "comment", 915, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "comment"};
      comment$40 = Py.newCode(2, var2, var1, "comment", 920, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "pwd"};
      read$41 = Py.newCode(3, var2, var1, "read", 931, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "mode", "pwd", "zef_file", "should_close", "zinfo", "fheader", "fname", "is_encrypted", "zd", "bytes", "h", "check_byte"};
      open$42 = Py.newCode(4, var2, var1, "open", 935, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "member", "path", "pwd"};
      extract$43 = Py.newCode(4, var2, var1, "extract", 1014, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "members", "pwd", "zipinfo"};
      extractall$44 = Py.newCode(4, var2, var1, "extractall", 1028, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "member", "targetpath", "pwd", "arcname", "_(1053_35)", "illegal", "table", "_(1061_23)", "_(1062_39)", "upperdirs", "source", "target"};
      _extract_member$45 = Py.newCode(4, var2, var1, "_extract_member", 1040, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "x"};
      f$46 = Py.newCode(1, var2, var1, "<genexpr>", 1053, false, false, self, 46, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"_(x)", "x"};
      f$47 = Py.newCode(1, var2, var1, "<genexpr>", 1061, false, false, self, 47, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"_(x)", "x"};
      f$48 = Py.newCode(1, var2, var1, "<genexpr>", 1062, false, false, self, 48, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "zinfo"};
      _writecheck$49 = Py.newCode(2, var2, var1, "_writecheck", 1083, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "arcname", "compress_type", "st", "isdir", "mtime", "date_time", "zinfo", "fp", "CRC", "compress_size", "zip64", "cmpr", "file_size", "buf", "position"};
      write$50 = Py.newCode(4, var2, var1, "write", 1106, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "zinfo_or_arcname", "bytes", "compress_type", "zinfo", "co", "zip64", "fmt"};
      writestr$51 = Py.newCode(4, var2, var1, "writestr", 1195, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$52 = Py.newCode(1, var2, var1, "__del__", 1242, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "count", "pos1", "zinfo", "dt", "dosdate", "dostime", "extra", "file_size", "compress_size", "header_offset", "extra_data", "extract_version", "create_version", "filename", "flag_bits", "centdir", "pos2", "centDirCount", "centDirSize", "centDirOffset", "zip64endrec", "zip64locrec", "endrec", "fp"};
      close$53 = Py.newCode(1, var2, var1, "close", 1246, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PyZipFile$54 = Py.newCode(0, var2, var1, "PyZipFile", 1352, false, false, self, 54, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "pathname", "basename", "dir", "name", "initname", "fname", "arcname", "dirlist", "filename", "path", "root", "ext"};
      writepy$55 = Py.newCode(3, var2, var1, "writepy", 1355, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pathname", "basename", "file_py", "imp", "file_pyc", "file_pyo", "fname", "py_compile", "err", "archivename"};
      _get_codename$56 = Py.newCode(3, var2, var1, "_get_codename", 1420, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "textwrap", "USAGE", "zf", "badfile", "out", "path", "tgt", "tgtdir", "fp", "src", "addToZip"};
      String[] var10001 = var2;
      zipfile$py var10007 = self;
      var2 = new String[]{"addToZip"};
      main$57 = Py.newCode(1, var10001, var1, "main", 1455, false, false, var10007, 57, var2, (String[])null, 1, 4097);
      var2 = new String[]{"zf", "path", "zippath", "nm"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"addToZip"};
      addToZip$58 = Py.newCode(3, var10001, var1, "addToZip", 1512, false, false, var10007, 58, (String[])null, var2, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new zipfile$py("zipfile$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(zipfile$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BadZipfile$1(var2, var3);
         case 2:
            return this.LargeZipFile$2(var2, var3);
         case 3:
            return this._check_zipfile$3(var2, var3);
         case 4:
            return this.is_zipfile$4(var2, var3);
         case 5:
            return this._EndRecData64$5(var2, var3);
         case 6:
            return this._EndRecData$6(var2, var3);
         case 7:
            return this.ZipInfo$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.FileHeader$9(var2, var3);
         case 10:
            return this._encodeFilenameFlags$10(var2, var3);
         case 11:
            return this._decodeFilename$11(var2, var3);
         case 12:
            return this._decodeExtra$12(var2, var3);
         case 13:
            return this._ZipDecrypter$13(var2, var3);
         case 14:
            return this._GenerateCRCTable$14(var2, var3);
         case 15:
            return this._crc32$15(var2, var3);
         case 16:
            return this.__init__$16(var2, var3);
         case 17:
            return this._UpdateKeys$17(var2, var3);
         case 18:
            return this.__call__$18(var2, var3);
         case 19:
            return this.ZipExtFile$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.readline$21(var2, var3);
         case 22:
            return this.peek$22(var2, var3);
         case 23:
            return this.readable$23(var2, var3);
         case 24:
            return this.read$24(var2, var3);
         case 25:
            return this._update_crc$25(var2, var3);
         case 26:
            return this.read1$26(var2, var3);
         case 27:
            return this.close$27(var2, var3);
         case 28:
            return this.ZipFile$28(var2, var3);
         case 29:
            return this.__init__$29(var2, var3);
         case 30:
            return this.__enter__$30(var2, var3);
         case 31:
            return this.__exit__$31(var2, var3);
         case 32:
            return this._RealGetContents$32(var2, var3);
         case 33:
            return this.namelist$33(var2, var3);
         case 34:
            return this.infolist$34(var2, var3);
         case 35:
            return this.printdir$35(var2, var3);
         case 36:
            return this.testzip$36(var2, var3);
         case 37:
            return this.getinfo$37(var2, var3);
         case 38:
            return this.setpassword$38(var2, var3);
         case 39:
            return this.comment$39(var2, var3);
         case 40:
            return this.comment$40(var2, var3);
         case 41:
            return this.read$41(var2, var3);
         case 42:
            return this.open$42(var2, var3);
         case 43:
            return this.extract$43(var2, var3);
         case 44:
            return this.extractall$44(var2, var3);
         case 45:
            return this._extract_member$45(var2, var3);
         case 46:
            return this.f$46(var2, var3);
         case 47:
            return this.f$47(var2, var3);
         case 48:
            return this.f$48(var2, var3);
         case 49:
            return this._writecheck$49(var2, var3);
         case 50:
            return this.write$50(var2, var3);
         case 51:
            return this.writestr$51(var2, var3);
         case 52:
            return this.__del__$52(var2, var3);
         case 53:
            return this.close$53(var2, var3);
         case 54:
            return this.PyZipFile$54(var2, var3);
         case 55:
            return this.writepy$55(var2, var3);
         case 56:
            return this._get_codename$56(var2, var3);
         case 57:
            return this.main$57(var2, var3);
         case 58:
            return this.addToZip$58(var2, var3);
         default:
            return null;
      }
   }
}
