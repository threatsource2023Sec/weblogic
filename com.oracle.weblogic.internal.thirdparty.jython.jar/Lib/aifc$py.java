import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFloat;
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
@MTime(1498849383000L)
@Filename("aifc.py")
public class aifc$py extends PyFunctionTable implements PyRunnable {
   static aifc$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode _read_long$2;
   static final PyCode _read_ulong$3;
   static final PyCode _read_short$4;
   static final PyCode _read_ushort$5;
   static final PyCode _read_string$6;
   static final PyCode _read_float$7;
   static final PyCode _write_short$8;
   static final PyCode _write_ushort$9;
   static final PyCode _write_long$10;
   static final PyCode _write_ulong$11;
   static final PyCode _write_string$12;
   static final PyCode _write_float$13;
   static final PyCode Aifc_read$14;
   static final PyCode initfp$15;
   static final PyCode __init__$16;
   static final PyCode getfp$17;
   static final PyCode rewind$18;
   static final PyCode close$19;
   static final PyCode tell$20;
   static final PyCode getnchannels$21;
   static final PyCode getnframes$22;
   static final PyCode getsampwidth$23;
   static final PyCode getframerate$24;
   static final PyCode getcomptype$25;
   static final PyCode getcompname$26;
   static final PyCode getparams$27;
   static final PyCode getmarkers$28;
   static final PyCode getmark$29;
   static final PyCode setpos$30;
   static final PyCode readframes$31;
   static final PyCode _decomp_data$32;
   static final PyCode _ulaw2lin$33;
   static final PyCode _adpcm2lin$34;
   static final PyCode _read_comm_chunk$35;
   static final PyCode _readmark$36;
   static final PyCode Aifc_write$37;
   static final PyCode __init__$38;
   static final PyCode initfp$39;
   static final PyCode __del__$40;
   static final PyCode aiff$41;
   static final PyCode aifc$42;
   static final PyCode setnchannels$43;
   static final PyCode getnchannels$44;
   static final PyCode setsampwidth$45;
   static final PyCode getsampwidth$46;
   static final PyCode setframerate$47;
   static final PyCode getframerate$48;
   static final PyCode setnframes$49;
   static final PyCode getnframes$50;
   static final PyCode setcomptype$51;
   static final PyCode getcomptype$52;
   static final PyCode getcompname$53;
   static final PyCode setparams$54;
   static final PyCode getparams$55;
   static final PyCode setmark$56;
   static final PyCode getmark$57;
   static final PyCode getmarkers$58;
   static final PyCode tell$59;
   static final PyCode writeframesraw$60;
   static final PyCode writeframes$61;
   static final PyCode close$62;
   static final PyCode _comp_data$63;
   static final PyCode _lin2ulaw$64;
   static final PyCode _lin2adpcm$65;
   static final PyCode _ensure_header_written$66;
   static final PyCode _init_compression$67;
   static final PyCode _write_header$68;
   static final PyCode _write_form_length$69;
   static final PyCode _patchheader$70;
   static final PyCode _writemarkers$71;
   static final PyCode open$72;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Stuff to parse AIFF-C and AIFF files.\n\nUnless explicitly stated otherwise, the description below is true\nboth for AIFF-C files and AIFF files.\n\nAn AIFF-C file has the following structure.\n\n  +-----------------+\n  | FORM            |\n  +-----------------+\n  | <size>          |\n  +----+------------+\n  |    | AIFC       |\n  |    +------------+\n  |    | <chunks>   |\n  |    |    .       |\n  |    |    .       |\n  |    |    .       |\n  +----+------------+\n\nAn AIFF file has the string \"AIFF\" instead of \"AIFC\".\n\nA chunk consists of an identifier (4 bytes) followed by a size (4 bytes,\nbig endian order), followed by the data.  The size field does not include\nthe size of the 8 byte header.\n\nThe following chunk types are recognized.\n\n  FVER\n      <version number of AIFF-C defining document> (AIFF-C only).\n  MARK\n      <# of markers> (2 bytes)\n      list of markers:\n          <marker ID> (2 bytes, must be > 0)\n          <position> (4 bytes)\n          <marker name> (\"pstring\")\n  COMM\n      <# of channels> (2 bytes)\n      <# of sound frames> (4 bytes)\n      <size of the samples> (2 bytes)\n      <sampling frequency> (10 bytes, IEEE 80-bit extended\n          floating point)\n      in AIFF-C files only:\n      <compression type> (4 bytes)\n      <human-readable version of compression type> (\"pstring\")\n  SSND\n      <offset> (4 bytes, not used by this program)\n      <blocksize> (4 bytes, not used by this program)\n      <sound data>\n\nA pstring consists of 1 byte length, a string of characters, and 0 or 1\nbyte pad to make the total length even.\n\nUsage.\n\nReading AIFF files:\n  f = aifc.open(file, 'r')\nwhere file is either the name of a file or an open file pointer.\nThe open file pointer must have methods read(), seek(), and close().\nIn some types of audio files, if the setpos() method is not used,\nthe seek() method is not necessary.\n\nThis returns an instance of a class with the following public methods:\n  getnchannels()  -- returns number of audio channels (1 for\n             mono, 2 for stereo)\n  getsampwidth()  -- returns sample width in bytes\n  getframerate()  -- returns sampling frequency\n  getnframes()    -- returns number of audio frames\n  getcomptype()   -- returns compression type ('NONE' for AIFF files)\n  getcompname()   -- returns human-readable version of\n             compression type ('not compressed' for AIFF files)\n  getparams() -- returns a tuple consisting of all of the\n             above in the above order\n  getmarkers()    -- get the list of marks in the audio file or None\n             if there are no marks\n  getmark(id) -- get mark with the specified id (raises an error\n             if the mark does not exist)\n  readframes(n)   -- returns at most n frames of audio\n  rewind()    -- rewind to the beginning of the audio stream\n  setpos(pos) -- seek to the specified position\n  tell()      -- return the current position\n  close()     -- close the instance (make it unusable)\nThe position returned by tell(), the position given to setpos() and\nthe position of marks are all compatible and have nothing to do with\nthe actual position in the file.\nThe close() method is called automatically when the class instance\nis destroyed.\n\nWriting AIFF files:\n  f = aifc.open(file, 'w')\nwhere file is either the name of a file or an open file pointer.\nThe open file pointer must have methods write(), tell(), seek(), and\nclose().\n\nThis returns an instance of a class with the following public methods:\n  aiff()      -- create an AIFF file (AIFF-C default)\n  aifc()      -- create an AIFF-C file\n  setnchannels(n) -- set the number of channels\n  setsampwidth(n) -- set the sample width\n  setframerate(n) -- set the frame rate\n  setnframes(n)   -- set the number of frames\n  setcomptype(type, name)\n          -- set the compression type and the\n             human-readable compression type\n  setparams(tuple)\n          -- set all parameters at once\n  setmark(id, pos, name)\n          -- add specified mark to the list of marks\n  tell()      -- return current position in output file (useful\n             in combination with setmark())\n  writeframesraw(data)\n          -- write audio frames without pathing up the\n             file header\n  writeframes(data)\n          -- write audio frames and patch up the file header\n  close()     -- patch up the file header and close the\n             output file\nYou should set the parameters before the first writeframesraw or\nwriteframes.  The total number of frames does not need to be set,\nbut when it is set to the correct value, the header does not have to\nbe patched up.\nIt is best to first set all parameters, perhaps possibly the\ncompression type, and then write audio frames using writeframesraw.\nWhen all frames have been written, either call writeframes('') or\nclose() to patch up the sizes in the header.\nMarks can be added anytime.  If there are any marks, ypu must call\nclose() after all frames have been written.\nThe close() method is called automatically when the class instance\nis destroyed.\n\nWhen a file is opened with the extension '.aiff', an AIFF file is\nwritten, otherwise an AIFF-C file is written.  This default can be\nchanged by calling aiff() or aifc() before the first writeframes or\nwriteframesraw.\n"));
      var1.setline(135);
      PyString.fromInterned("Stuff to parse AIFF-C and AIFF files.\n\nUnless explicitly stated otherwise, the description below is true\nboth for AIFF-C files and AIFF files.\n\nAn AIFF-C file has the following structure.\n\n  +-----------------+\n  | FORM            |\n  +-----------------+\n  | <size>          |\n  +----+------------+\n  |    | AIFC       |\n  |    +------------+\n  |    | <chunks>   |\n  |    |    .       |\n  |    |    .       |\n  |    |    .       |\n  +----+------------+\n\nAn AIFF file has the string \"AIFF\" instead of \"AIFC\".\n\nA chunk consists of an identifier (4 bytes) followed by a size (4 bytes,\nbig endian order), followed by the data.  The size field does not include\nthe size of the 8 byte header.\n\nThe following chunk types are recognized.\n\n  FVER\n      <version number of AIFF-C defining document> (AIFF-C only).\n  MARK\n      <# of markers> (2 bytes)\n      list of markers:\n          <marker ID> (2 bytes, must be > 0)\n          <position> (4 bytes)\n          <marker name> (\"pstring\")\n  COMM\n      <# of channels> (2 bytes)\n      <# of sound frames> (4 bytes)\n      <size of the samples> (2 bytes)\n      <sampling frequency> (10 bytes, IEEE 80-bit extended\n          floating point)\n      in AIFF-C files only:\n      <compression type> (4 bytes)\n      <human-readable version of compression type> (\"pstring\")\n  SSND\n      <offset> (4 bytes, not used by this program)\n      <blocksize> (4 bytes, not used by this program)\n      <sound data>\n\nA pstring consists of 1 byte length, a string of characters, and 0 or 1\nbyte pad to make the total length even.\n\nUsage.\n\nReading AIFF files:\n  f = aifc.open(file, 'r')\nwhere file is either the name of a file or an open file pointer.\nThe open file pointer must have methods read(), seek(), and close().\nIn some types of audio files, if the setpos() method is not used,\nthe seek() method is not necessary.\n\nThis returns an instance of a class with the following public methods:\n  getnchannels()  -- returns number of audio channels (1 for\n             mono, 2 for stereo)\n  getsampwidth()  -- returns sample width in bytes\n  getframerate()  -- returns sampling frequency\n  getnframes()    -- returns number of audio frames\n  getcomptype()   -- returns compression type ('NONE' for AIFF files)\n  getcompname()   -- returns human-readable version of\n             compression type ('not compressed' for AIFF files)\n  getparams() -- returns a tuple consisting of all of the\n             above in the above order\n  getmarkers()    -- get the list of marks in the audio file or None\n             if there are no marks\n  getmark(id) -- get mark with the specified id (raises an error\n             if the mark does not exist)\n  readframes(n)   -- returns at most n frames of audio\n  rewind()    -- rewind to the beginning of the audio stream\n  setpos(pos) -- seek to the specified position\n  tell()      -- return the current position\n  close()     -- close the instance (make it unusable)\nThe position returned by tell(), the position given to setpos() and\nthe position of marks are all compatible and have nothing to do with\nthe actual position in the file.\nThe close() method is called automatically when the class instance\nis destroyed.\n\nWriting AIFF files:\n  f = aifc.open(file, 'w')\nwhere file is either the name of a file or an open file pointer.\nThe open file pointer must have methods write(), tell(), seek(), and\nclose().\n\nThis returns an instance of a class with the following public methods:\n  aiff()      -- create an AIFF file (AIFF-C default)\n  aifc()      -- create an AIFF-C file\n  setnchannels(n) -- set the number of channels\n  setsampwidth(n) -- set the sample width\n  setframerate(n) -- set the frame rate\n  setnframes(n)   -- set the number of frames\n  setcomptype(type, name)\n          -- set the compression type and the\n             human-readable compression type\n  setparams(tuple)\n          -- set all parameters at once\n  setmark(id, pos, name)\n          -- add specified mark to the list of marks\n  tell()      -- return current position in output file (useful\n             in combination with setmark())\n  writeframesraw(data)\n          -- write audio frames without pathing up the\n             file header\n  writeframes(data)\n          -- write audio frames and patch up the file header\n  close()     -- patch up the file header and close the\n             output file\nYou should set the parameters before the first writeframesraw or\nwriteframes.  The total number of frames does not need to be set,\nbut when it is set to the correct value, the header does not have to\nbe patched up.\nIt is best to first set all parameters, perhaps possibly the\ncompression type, and then write audio frames using writeframesraw.\nWhen all frames have been written, either call writeframes('') or\nclose() to patch up the sizes in the header.\nMarks can be added anytime.  If there are any marks, ypu must call\nclose() after all frames have been written.\nThe close() method is called automatically when the class instance\nis destroyed.\n\nWhen a file is opened with the extension '.aiff', an AIFF file is\nwritten, otherwise an AIFF-C file is written.  This default can be\nchanged by calling aiff() or aifc() before the first writeframes or\nwriteframesraw.\n");
      var1.setline(137);
      PyObject var3 = imp.importOne("struct", var1, -1);
      var1.setlocal("struct", var3);
      var3 = null;
      var1.setline(138);
      var3 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var3);
      var3 = null;
      var1.setline(140);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("Error"), PyString.fromInterned("open"), PyString.fromInterned("openfp")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(142);
      PyObject[] var6 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("Error", var6, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(145);
      PyLong var7 = Py.newLong("2726318400");
      var1.setlocal("_AIFC_version", var7);
      var3 = null;
      var1.setline(147);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, _read_long$2, (PyObject)null);
      var1.setlocal("_read_long", var8);
      var3 = null;
      var1.setline(153);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _read_ulong$3, (PyObject)null);
      var1.setlocal("_read_ulong", var8);
      var3 = null;
      var1.setline(159);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _read_short$4, (PyObject)null);
      var1.setlocal("_read_short", var8);
      var3 = null;
      var1.setline(165);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _read_ushort$5, (PyObject)null);
      var1.setlocal("_read_ushort", var8);
      var3 = null;
      var1.setline(171);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _read_string$6, (PyObject)null);
      var1.setlocal("_read_string", var8);
      var3 = null;
      var1.setline(181);
      PyFloat var9 = Py.newFloat(1.79769313486231E308);
      var1.setlocal("_HUGE_VAL", var9);
      var3 = null;
      var1.setline(183);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _read_float$7, (PyObject)null);
      var1.setlocal("_read_float", var8);
      var3 = null;
      var1.setline(200);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _write_short$8, (PyObject)null);
      var1.setlocal("_write_short", var8);
      var3 = null;
      var1.setline(203);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _write_ushort$9, (PyObject)null);
      var1.setlocal("_write_ushort", var8);
      var3 = null;
      var1.setline(206);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _write_long$10, (PyObject)null);
      var1.setlocal("_write_long", var8);
      var3 = null;
      var1.setline(209);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _write_ulong$11, (PyObject)null);
      var1.setlocal("_write_ulong", var8);
      var3 = null;
      var1.setline(212);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _write_string$12, (PyObject)null);
      var1.setlocal("_write_string", var8);
      var3 = null;
      var1.setline(220);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _write_float$13, (PyObject)null);
      var1.setlocal("_write_float", var8);
      var3 = null;
      var1.setline(253);
      String[] var10 = new String[]{"Chunk"};
      var6 = imp.importFrom("chunk", var10, var1, -1);
      var4 = var6[0];
      var1.setlocal("Chunk", var4);
      var4 = null;
      var1.setline(255);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Aifc_read", var6, Aifc_read$14);
      var1.setlocal("Aifc_read", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(533);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Aifc_write", var6, Aifc_write$37);
      var1.setlocal("Aifc_write", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(935);
      var6 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var6, open$72, (PyObject)null);
      var1.setlocal("open", var8);
      var3 = null;
      var1.setline(948);
      var3 = var1.getname("open");
      var1.setlocal("openfp", var3);
      var3 = null;
      var1.setline(950);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(951);
         var3 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var3);
         var3 = null;
         var1.setline(952);
         if (var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__not__().__nonzero__()) {
            var1.setline(953);
            var1.getname("sys").__getattr__("argv").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/usr/demos/data/audio/bach.aiff"));
         }

         var1.setline(954);
         var3 = var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
         var1.setlocal("fn", var3);
         var3 = null;
         var1.setline(955);
         var3 = var1.getname("open").__call__((ThreadState)var2, (PyObject)var1.getname("fn"), (PyObject)PyString.fromInterned("r"));
         var1.setlocal("f", var3);
         var3 = null;
         var1.setline(956);
         Py.printComma(PyString.fromInterned("Reading"));
         Py.println(var1.getname("fn"));
         var1.setline(957);
         Py.printComma(PyString.fromInterned("nchannels ="));
         Py.println(var1.getname("f").__getattr__("getnchannels").__call__(var2));
         var1.setline(958);
         Py.printComma(PyString.fromInterned("nframes   ="));
         Py.println(var1.getname("f").__getattr__("getnframes").__call__(var2));
         var1.setline(959);
         Py.printComma(PyString.fromInterned("sampwidth ="));
         Py.println(var1.getname("f").__getattr__("getsampwidth").__call__(var2));
         var1.setline(960);
         Py.printComma(PyString.fromInterned("framerate ="));
         Py.println(var1.getname("f").__getattr__("getframerate").__call__(var2));
         var1.setline(961);
         Py.printComma(PyString.fromInterned("comptype  ="));
         Py.println(var1.getname("f").__getattr__("getcomptype").__call__(var2));
         var1.setline(962);
         Py.printComma(PyString.fromInterned("compname  ="));
         Py.println(var1.getname("f").__getattr__("getcompname").__call__(var2));
         var1.setline(963);
         if (var1.getname("sys").__getattr__("argv").__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null).__nonzero__()) {
            var1.setline(964);
            var3 = var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(2));
            var1.setlocal("gn", var3);
            var3 = null;
            var1.setline(965);
            Py.printComma(PyString.fromInterned("Writing"));
            Py.println(var1.getname("gn"));
            var1.setline(966);
            var3 = var1.getname("open").__call__((ThreadState)var2, (PyObject)var1.getname("gn"), (PyObject)PyString.fromInterned("w"));
            var1.setlocal("g", var3);
            var3 = null;
            var1.setline(967);
            var1.getname("g").__getattr__("setparams").__call__(var2, var1.getname("f").__getattr__("getparams").__call__(var2));

            while(true) {
               var1.setline(968);
               if (!Py.newInteger(1).__nonzero__()) {
                  break;
               }

               var1.setline(969);
               var3 = var1.getname("f").__getattr__("readframes").__call__((ThreadState)var2, (PyObject)Py.newInteger(1024));
               var1.setlocal("data", var3);
               var3 = null;
               var1.setline(970);
               if (var1.getname("data").__not__().__nonzero__()) {
                  break;
               }

               var1.setline(972);
               var1.getname("g").__getattr__("writeframes").__call__(var2, var1.getname("data"));
            }

            var1.setline(973);
            var1.getname("g").__getattr__("close").__call__(var2);
            var1.setline(974);
            var1.getname("f").__getattr__("close").__call__(var2);
            var1.setline(975);
            Py.println(PyString.fromInterned("Done."));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(143);
      return var1.getf_locals();
   }

   public PyObject _read_long$2(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(149);
         PyObject var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">l"), (PyObject)var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("struct").__getattr__("error"))) {
            var1.setline(151);
            throw Py.makeException(var1.getglobal("EOFError"));
         } else {
            throw var4;
         }
      }
   }

   public PyObject _read_ulong$3(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(155);
         PyObject var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">L"), (PyObject)var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4))).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("struct").__getattr__("error"))) {
            var1.setline(157);
            throw Py.makeException(var1.getglobal("EOFError"));
         } else {
            throw var4;
         }
      }
   }

   public PyObject _read_short$4(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(161);
         PyObject var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">h"), (PyObject)var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2))).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("struct").__getattr__("error"))) {
            var1.setline(163);
            throw Py.makeException(var1.getglobal("EOFError"));
         } else {
            throw var4;
         }
      }
   }

   public PyObject _read_ushort$5(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(167);
         PyObject var3 = var1.getglobal("struct").__getattr__("unpack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">H"), (PyObject)var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(2))).__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("struct").__getattr__("error"))) {
            var1.setline(169);
            throw Py.makeException(var1.getglobal("EOFError"));
         } else {
            throw var4;
         }
      }
   }

   public PyObject _read_string$6(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(174);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(2, var4);
         var3 = null;
      } else {
         var1.setline(176);
         var3 = var1.getlocal(0).__getattr__("read").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(177);
      var3 = var1.getlocal(1)._and(Py.newInteger(1));
      var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(178);
         var3 = var1.getlocal(0).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(179);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _read_float$7(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var3 = var1.getglobal("_read_short").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(185);
      PyInteger var5 = Py.newInteger(1);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(186);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(187);
         var5 = Py.newInteger(-1);
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(188);
         var3 = var1.getlocal(1)._add(Py.newInteger(32768));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(189);
      var3 = var1.getglobal("_read_ulong").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(190);
      var3 = var1.getglobal("_read_ulong").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(191);
      var3 = var1.getlocal(1);
      PyObject var10001 = var1.getlocal(3);
      var10000 = var3;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._eq(var10001)).__nonzero__()) {
         var10001 = var1.getlocal(4);
         var10000 = var3;
         var3 = var10001;
         if ((var4 = var10000._eq(var10001)).__nonzero__()) {
            var4 = var3._eq(Py.newInteger(0));
         }
      }

      var3 = null;
      if (var4.__nonzero__()) {
         var1.setline(192);
         PyFloat var6 = Py.newFloat(0.0);
         var1.setlocal(0, var6);
         var3 = null;
      } else {
         var1.setline(193);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(Py.newInteger(32767));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(194);
            var3 = var1.getglobal("_HUGE_VAL");
            var1.setlocal(0, var3);
            var3 = null;
         } else {
            var1.setline(196);
            var3 = var1.getlocal(1)._sub(Py.newInteger(16383));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(197);
            var3 = var1.getlocal(3)._mul(Py.newLong("4294967296"))._add(var1.getlocal(4))._mul(var1.getglobal("pow").__call__((ThreadState)var2, (PyObject)Py.newFloat(2.0), (PyObject)var1.getlocal(1)._sub(Py.newInteger(63))));
            var1.setlocal(0, var3);
            var3 = null;
         }
      }

      var1.setline(198);
      var3 = var1.getlocal(2)._mul(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _write_short$8(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">h"), (PyObject)var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write_ushort$9(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">H"), (PyObject)var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write_long$10(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">l"), (PyObject)var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write_ulong$11(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">L"), (PyObject)var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write_string$12(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(255));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(214);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("string exceeds maximum pstring length")));
      } else {
         var1.setline(215);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("struct").__getattr__("pack").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("B"), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1))));
         var1.setline(216);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1));
         var1.setline(217);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._and(Py.newInteger(1));
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(218);
            var1.getlocal(0).__getattr__("write").__call__(var2, var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _write_float$13(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyObject var3 = imp.importOne("math", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(222);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      PyInteger var6;
      if (var10000.__nonzero__()) {
         var1.setline(223);
         var6 = Py.newInteger(32768);
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(224);
         var3 = var1.getlocal(1)._mul(Py.newInteger(-1));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(226);
         var6 = Py.newInteger(0);
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(227);
      var3 = var1.getlocal(1);
      var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(228);
         var6 = Py.newInteger(0);
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(229);
         var6 = Py.newInteger(0);
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(230);
         var6 = Py.newInteger(0);
         var1.setlocal(6, var6);
         var3 = null;
      } else {
         var1.setline(232);
         var3 = var1.getlocal(2).__getattr__("frexp").__call__(var2, var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(233);
         var3 = var1.getlocal(4);
         var10000 = var3._gt(Py.newInteger(16384));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(7);
            var10000 = var3._ge(Py.newInteger(1));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(7);
               var10000 = var3._ne(var1.getlocal(7));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(234);
            var3 = var1.getlocal(3)._or(Py.newInteger(32767));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(235);
            var6 = Py.newInteger(0);
            var1.setlocal(5, var6);
            var3 = null;
            var1.setline(236);
            var6 = Py.newInteger(0);
            var1.setlocal(6, var6);
            var3 = null;
         } else {
            var1.setline(238);
            var3 = var1.getlocal(4)._add(Py.newInteger(16382));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(239);
            var3 = var1.getlocal(4);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(240);
               var3 = var1.getlocal(2).__getattr__("ldexp").__call__(var2, var1.getlocal(7), var1.getlocal(4));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(241);
               var6 = Py.newInteger(0);
               var1.setlocal(4, var6);
               var3 = null;
            }

            var1.setline(242);
            var3 = var1.getlocal(4)._or(var1.getlocal(3));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(243);
            var3 = var1.getlocal(2).__getattr__("ldexp").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)Py.newInteger(32));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(244);
            var3 = var1.getlocal(2).__getattr__("floor").__call__(var2, var1.getlocal(7));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(245);
            var3 = var1.getglobal("long").__call__(var2, var1.getlocal(8));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(246);
            var3 = var1.getlocal(2).__getattr__("ldexp").__call__((ThreadState)var2, (PyObject)var1.getlocal(7)._sub(var1.getlocal(8)), (PyObject)Py.newInteger(32));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(247);
            var3 = var1.getlocal(2).__getattr__("floor").__call__(var2, var1.getlocal(7));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(248);
            var3 = var1.getglobal("long").__call__(var2, var1.getlocal(8));
            var1.setlocal(6, var3);
            var3 = null;
         }
      }

      var1.setline(249);
      var1.getglobal("_write_ushort").__call__(var2, var1.getlocal(0), var1.getlocal(4));
      var1.setline(250);
      var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0), var1.getlocal(5));
      var1.setline(251);
      var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0), var1.getlocal(6));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Aifc_read$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(291);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, initfp$15, (PyObject)null);
      var1.setlocal("initfp", var4);
      var3 = null;
      var1.setline(343);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __init__$16, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(352);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getfp$17, (PyObject)null);
      var1.setlocal("getfp", var4);
      var3 = null;
      var1.setline(355);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rewind$18, (PyObject)null);
      var1.setlocal("rewind", var4);
      var3 = null;
      var1.setline(359);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$19, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(365);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$20, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(368);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getnchannels$21, (PyObject)null);
      var1.setlocal("getnchannels", var4);
      var3 = null;
      var1.setline(371);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getnframes$22, (PyObject)null);
      var1.setlocal("getnframes", var4);
      var3 = null;
      var1.setline(374);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getsampwidth$23, (PyObject)null);
      var1.setlocal("getsampwidth", var4);
      var3 = null;
      var1.setline(377);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getframerate$24, (PyObject)null);
      var1.setlocal("getframerate", var4);
      var3 = null;
      var1.setline(380);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcomptype$25, (PyObject)null);
      var1.setlocal("getcomptype", var4);
      var3 = null;
      var1.setline(383);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcompname$26, (PyObject)null);
      var1.setlocal("getcompname", var4);
      var3 = null;
      var1.setline(389);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getparams$27, (PyObject)null);
      var1.setlocal("getparams", var4);
      var3 = null;
      var1.setline(394);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getmarkers$28, (PyObject)null);
      var1.setlocal("getmarkers", var4);
      var3 = null;
      var1.setline(399);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getmark$29, (PyObject)null);
      var1.setlocal("getmark", var4);
      var3 = null;
      var1.setline(405);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setpos$30, (PyObject)null);
      var1.setlocal("setpos", var4);
      var3 = null;
      var1.setline(411);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, readframes$31, (PyObject)null);
      var1.setlocal("readframes", var4);
      var3 = null;
      var1.setline(431);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _decomp_data$32, (PyObject)null);
      var1.setlocal("_decomp_data", var4);
      var3 = null;
      var1.setline(438);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _ulaw2lin$33, (PyObject)null);
      var1.setlocal("_ulaw2lin", var4);
      var3 = null;
      var1.setline(442);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _adpcm2lin$34, (PyObject)null);
      var1.setlocal("_adpcm2lin", var4);
      var3 = null;
      var1.setline(451);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read_comm_chunk$35, (PyObject)null);
      var1.setlocal("_read_comm_chunk", var4);
      var3 = null;
      var1.setline(512);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _readmark$36, (PyObject)null);
      var1.setlocal("_readmark", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initfp$15(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_version", var3);
      var3 = null;
      var1.setline(293);
      PyObject var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_decomp", var5);
      var3 = null;
      var1.setline(294);
      var5 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_convert", var5);
      var3 = null;
      var1.setline(295);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_markers", var6);
      var3 = null;
      var1.setline(296);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_soundpos", var3);
      var3 = null;
      var1.setline(297);
      var5 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_file", var5);
      var3 = null;
      var1.setline(298);
      var5 = var1.getglobal("Chunk").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(299);
      var5 = var1.getlocal(2).__getattr__("getname").__call__(var2);
      PyObject var10000 = var5._ne(PyString.fromInterned("FORM"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(300);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("file does not start with FORM id"));
      } else {
         var1.setline(301);
         var5 = var1.getlocal(2).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(302);
         var5 = var1.getlocal(3);
         var10000 = var5._eq(PyString.fromInterned("AIFF"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(303);
            var3 = Py.newInteger(0);
            var1.getlocal(0).__setattr__((String)"_aifc", var3);
            var3 = null;
         } else {
            var1.setline(304);
            var5 = var1.getlocal(3);
            var10000 = var5._eq(PyString.fromInterned("AIFC"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(307);
               throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("not an AIFF or AIFF-C file"));
            }

            var1.setline(305);
            var3 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"_aifc", var3);
            var3 = null;
         }

         var1.setline(308);
         var3 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_comm_chunk_read", var3);
         var3 = null;

         while(true) {
            var1.setline(309);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(310);
            var3 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"_ssnd_seek_needed", var3);
            var3 = null;

            try {
               var1.setline(312);
               var5 = var1.getglobal("Chunk").__call__(var2, var1.getlocal(0).__getattr__("_file"));
               var1.setlocal(2, var5);
               var3 = null;
            } catch (Throwable var4) {
               PyException var7 = Py.setException(var4, var1);
               if (var7.match(var1.getglobal("EOFError"))) {
                  break;
               }

               throw var7;
            }

            var1.setline(315);
            var5 = var1.getlocal(2).__getattr__("getname").__call__(var2);
            var1.setlocal(4, var5);
            var3 = null;
            var1.setline(316);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(PyString.fromInterned("COMM"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(317);
               var1.getlocal(0).__getattr__("_read_comm_chunk").__call__(var2, var1.getlocal(2));
               var1.setline(318);
               var3 = Py.newInteger(1);
               var1.getlocal(0).__setattr__((String)"_comm_chunk_read", var3);
               var3 = null;
            } else {
               var1.setline(319);
               var5 = var1.getlocal(4);
               var10000 = var5._eq(PyString.fromInterned("SSND"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(320);
                  var5 = var1.getlocal(2);
                  var1.getlocal(0).__setattr__("_ssnd_chunk", var5);
                  var3 = null;
                  var1.setline(321);
                  var5 = var1.getlocal(2).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(8));
                  var1.setlocal(5, var5);
                  var3 = null;
                  var1.setline(322);
                  var3 = Py.newInteger(0);
                  var1.getlocal(0).__setattr__((String)"_ssnd_seek_needed", var3);
                  var3 = null;
               } else {
                  var1.setline(323);
                  var5 = var1.getlocal(4);
                  var10000 = var5._eq(PyString.fromInterned("FVER"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(324);
                     var5 = var1.getglobal("_read_ulong").__call__(var2, var1.getlocal(2));
                     var1.getlocal(0).__setattr__("_version", var5);
                     var3 = null;
                  } else {
                     var1.setline(325);
                     var5 = var1.getlocal(4);
                     var10000 = var5._eq(PyString.fromInterned("MARK"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(326);
                        var1.getlocal(0).__getattr__("_readmark").__call__(var2, var1.getlocal(2));
                     }
                  }
               }
            }

            var1.setline(327);
            var1.getlocal(2).__getattr__("skip").__call__(var2);
         }

         var1.setline(328);
         var10000 = var1.getlocal(0).__getattr__("_comm_chunk_read").__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_ssnd_chunk").__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(329);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("COMM chunk and/or SSND chunk missing"));
         } else {
            var1.setline(330);
            var10000 = var1.getlocal(0).__getattr__("_aifc");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("_decomp");
            }

            if (var10000.__nonzero__()) {
               var1.setline(331);
               var5 = imp.importOne("cl", var1, -1);
               var1.setlocal(6, var5);
               var3 = null;
               var1.setline(332);
               var6 = new PyList(new PyObject[]{var1.getlocal(6).__getattr__("ORIGINAL_FORMAT"), Py.newInteger(0), var1.getlocal(6).__getattr__("BITS_PER_COMPONENT"), var1.getlocal(0).__getattr__("_sampwidth")._mul(Py.newInteger(8)), var1.getlocal(6).__getattr__("FRAME_RATE"), var1.getlocal(0).__getattr__("_framerate")});
               var1.setlocal(7, var6);
               var3 = null;
               var1.setline(335);
               var5 = var1.getlocal(0).__getattr__("_nchannels");
               var10000 = var5._eq(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(336);
                  var5 = var1.getlocal(6).__getattr__("MONO");
                  var1.getlocal(7).__setitem__((PyObject)Py.newInteger(1), var5);
                  var3 = null;
               } else {
                  var1.setline(337);
                  var5 = var1.getlocal(0).__getattr__("_nchannels");
                  var10000 = var5._eq(Py.newInteger(2));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(340);
                     throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot compress more than 2 channels"));
                  }

                  var1.setline(338);
                  var5 = var1.getlocal(6).__getattr__("STEREO_INTERLEAVED");
                  var1.getlocal(7).__setitem__((PyObject)Py.newInteger(1), var5);
                  var3 = null;
               }

               var1.setline(341);
               var1.getlocal(0).__getattr__("_decomp").__getattr__("SetParams").__call__(var2, var1.getlocal(7));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __init__$16(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(345);
         var3 = var1.getglobal("__builtin__").__getattr__("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(347);
      var1.getlocal(0).__getattr__("initfp").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getfp$17(PyFrame var1, ThreadState var2) {
      var1.setline(353);
      PyObject var3 = var1.getlocal(0).__getattr__("_file");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rewind$18(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_ssnd_seek_needed", var3);
      var3 = null;
      var1.setline(357);
      var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_soundpos", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$19(PyFrame var1, ThreadState var2) {
      var1.setline(360);
      if (var1.getlocal(0).__getattr__("_decomp").__nonzero__()) {
         var1.setline(361);
         var1.getlocal(0).__getattr__("_decomp").__getattr__("CloseDecompressor").__call__(var2);
         var1.setline(362);
         PyObject var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_decomp", var3);
         var3 = null;
      }

      var1.setline(363);
      var1.getlocal(0).__getattr__("_file").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tell$20(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyObject var3 = var1.getlocal(0).__getattr__("_soundpos");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getnchannels$21(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      PyObject var3 = var1.getlocal(0).__getattr__("_nchannels");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getnframes$22(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyObject var3 = var1.getlocal(0).__getattr__("_nframes");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getsampwidth$23(PyFrame var1, ThreadState var2) {
      var1.setline(375);
      PyObject var3 = var1.getlocal(0).__getattr__("_sampwidth");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getframerate$24(PyFrame var1, ThreadState var2) {
      var1.setline(378);
      PyObject var3 = var1.getlocal(0).__getattr__("_framerate");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getcomptype$25(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyObject var3 = var1.getlocal(0).__getattr__("_comptype");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getcompname$26(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      PyObject var3 = var1.getlocal(0).__getattr__("_compname");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getparams$27(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("getnchannels").__call__(var2), var1.getlocal(0).__getattr__("getsampwidth").__call__(var2), var1.getlocal(0).__getattr__("getframerate").__call__(var2), var1.getlocal(0).__getattr__("getnframes").__call__(var2), var1.getlocal(0).__getattr__("getcomptype").__call__(var2), var1.getlocal(0).__getattr__("getcompname").__call__(var2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getmarkers$28(PyFrame var1, ThreadState var2) {
      var1.setline(395);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_markers"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(396);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(397);
         var3 = var1.getlocal(0).__getattr__("_markers");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getmark$29(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      PyObject var3 = var1.getlocal(0).__getattr__("_markers").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(400);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(403);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("marker %r does not exist")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
         }

         var1.setlocal(2, var4);
         var1.setline(401);
         var5 = var1.getlocal(1);
         var10000 = var5._eq(var1.getlocal(2).__getitem__(Py.newInteger(0)));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(402);
      var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject setpos$30(PyFrame var1, ThreadState var2) {
      var1.setline(406);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._gt(var1.getlocal(0).__getattr__("_nframes"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(407);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("position not in range"));
      } else {
         var1.setline(408);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_soundpos", var3);
         var3 = null;
         var1.setline(409);
         PyInteger var4 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"_ssnd_seek_needed", var4);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject readframes$31(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_ssnd_seek_needed").__nonzero__()) {
         var1.setline(413);
         var1.getlocal(0).__getattr__("_ssnd_chunk").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(414);
         var3 = var1.getlocal(0).__getattr__("_ssnd_chunk").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(8));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(415);
         var3 = var1.getlocal(0).__getattr__("_soundpos")._mul(var1.getlocal(0).__getattr__("_framesize"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(416);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(417);
            var1.getlocal(0).__getattr__("_ssnd_chunk").__getattr__("seek").__call__(var2, var1.getlocal(3)._add(Py.newInteger(8)));
         }

         var1.setline(418);
         PyInteger var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_ssnd_seek_needed", var5);
         var3 = null;
      }

      var1.setline(419);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(420);
         PyString var6 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(421);
         PyObject var4 = var1.getlocal(0).__getattr__("_ssnd_chunk").__getattr__("read").__call__(var2, var1.getlocal(1)._mul(var1.getlocal(0).__getattr__("_framesize")));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(422);
         var10000 = var1.getlocal(0).__getattr__("_convert");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         if (var10000.__nonzero__()) {
            var1.setline(423);
            var4 = var1.getlocal(0).__getattr__("_convert").__call__(var2, var1.getlocal(4));
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(424);
         var4 = var1.getlocal(0).__getattr__("_soundpos")._add(var1.getglobal("len").__call__(var2, var1.getlocal(4))._floordiv(var1.getlocal(0).__getattr__("_nchannels")._mul(var1.getlocal(0).__getattr__("_sampwidth"))));
         var1.getlocal(0).__setattr__("_soundpos", var4);
         var4 = null;
         var1.setline(425);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _decomp_data$32(PyFrame var1, ThreadState var2) {
      var1.setline(432);
      PyObject var3 = imp.importOne("cl", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(433);
      var3 = var1.getlocal(0).__getattr__("_decomp").__getattr__("SetParam").__call__(var2, var1.getlocal(2).__getattr__("FRAME_BUFFER_SIZE"), var1.getglobal("len").__call__(var2, var1.getlocal(1))._mul(Py.newInteger(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(435);
      var3 = var1.getlocal(0).__getattr__("_decomp").__getattr__("Decompress").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1))._floordiv(var1.getlocal(0).__getattr__("_nchannels")), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ulaw2lin$33(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      PyObject var3 = imp.importOne("audioop", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(440);
      var3 = var1.getlocal(2).__getattr__("ulaw2lin").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _adpcm2lin$34(PyFrame var1, ThreadState var2) {
      var1.setline(443);
      PyObject var3 = imp.importOne("audioop", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(444);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_adpcmstate")).__not__().__nonzero__()) {
         var1.setline(446);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_adpcmstate", var3);
         var3 = null;
      }

      var1.setline(447);
      var3 = var1.getlocal(2).__getattr__("adpcm2lin").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)Py.newInteger(2), (PyObject)var1.getlocal(0).__getattr__("_adpcmstate"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("_adpcmstate", var5);
      var5 = null;
      var3 = null;
      var1.setline(449);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _read_comm_chunk$35(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyObject var3 = var1.getglobal("_read_short").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_nchannels", var3);
      var3 = null;
      var1.setline(453);
      var3 = var1.getglobal("_read_long").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_nframes", var3);
      var3 = null;
      var1.setline(454);
      var3 = var1.getglobal("_read_short").__call__(var2, var1.getlocal(1))._add(Py.newInteger(7))._floordiv(Py.newInteger(8));
      var1.getlocal(0).__setattr__("_sampwidth", var3);
      var3 = null;
      var1.setline(455);
      var3 = var1.getglobal("int").__call__(var2, var1.getglobal("_read_float").__call__(var2, var1.getlocal(1)));
      var1.getlocal(0).__setattr__("_framerate", var3);
      var3 = null;
      var1.setline(456);
      var3 = var1.getlocal(0).__getattr__("_nchannels")._mul(var1.getlocal(0).__getattr__("_sampwidth"));
      var1.getlocal(0).__setattr__("_framesize", var3);
      var3 = null;
      var1.setline(457);
      if (var1.getlocal(0).__getattr__("_aifc").__nonzero__()) {
         var1.setline(459);
         PyInteger var9 = Py.newInteger(0);
         var1.setlocal(2, var9);
         var3 = null;
         var1.setline(460);
         var3 = var1.getlocal(1).__getattr__("chunksize");
         PyObject var10000 = var3._eq(Py.newInteger(18));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(461);
            var9 = Py.newInteger(1);
            var1.setlocal(2, var9);
            var3 = null;
            var1.setline(462);
            Py.println(PyString.fromInterned("Warning: bad COMM chunk size"));
            var1.setline(463);
            var9 = Py.newInteger(23);
            var1.getlocal(1).__setattr__((String)"chunksize", var9);
            var3 = null;
         }

         var1.setline(465);
         var3 = var1.getlocal(1).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4));
         var1.getlocal(0).__setattr__("_comptype", var3);
         var3 = null;
         var1.setline(467);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(468);
            var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getattr__("file").__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(469);
            var3 = var1.getlocal(3)._and(Py.newInteger(1));
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(470);
               var3 = var1.getlocal(3)._add(Py.newInteger(1));
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(471);
            var3 = var1.getlocal(1).__getattr__("chunksize")._add(var1.getlocal(3));
            var1.getlocal(1).__setattr__("chunksize", var3);
            var3 = null;
            var1.setline(472);
            var1.getlocal(1).__getattr__("file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1), (PyObject)Py.newInteger(1));
         }

         var1.setline(474);
         var3 = var1.getglobal("_read_string").__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("_compname", var3);
         var3 = null;
         var1.setline(475);
         var3 = var1.getlocal(0).__getattr__("_comptype");
         var10000 = var3._ne(PyString.fromInterned("NONE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(476);
            var3 = var1.getlocal(0).__getattr__("_comptype");
            var10000 = var3._eq(PyString.fromInterned("G722"));
            var3 = null;
            PyObject var4;
            PyException var10;
            if (var10000.__nonzero__()) {
               label73: {
                  try {
                     var1.setline(478);
                     var3 = imp.importOne("audioop", var1, -1);
                     var1.setlocal(4, var3);
                     var3 = null;
                  } catch (Throwable var7) {
                     var10 = Py.setException(var7, var1);
                     if (var10.match(var1.getglobal("ImportError"))) {
                        var1.setline(480);
                        break label73;
                     }

                     throw var10;
                  }

                  var1.setline(482);
                  var4 = var1.getlocal(0).__getattr__("_adpcm2lin");
                  var1.getlocal(0).__setattr__("_convert", var4);
                  var4 = null;
                  var1.setline(483);
                  var4 = var1.getlocal(0).__getattr__("_framesize")._floordiv(Py.newInteger(4));
                  var1.getlocal(0).__setattr__("_framesize", var4);
                  var4 = null;
                  var1.setline(484);
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }

            try {
               var1.setline(487);
               var3 = imp.importOne("cl", var1, -1);
               var1.setlocal(5, var3);
               var3 = null;
            } catch (Throwable var6) {
               var10 = Py.setException(var6, var1);
               if (var10.match(var1.getglobal("ImportError"))) {
                  var1.setline(489);
                  var4 = var1.getlocal(0).__getattr__("_comptype");
                  var10000 = var4._eq(PyString.fromInterned("ULAW"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     try {
                        var1.setline(491);
                        var4 = imp.importOne("audioop", var1, -1);
                        var1.setlocal(4, var4);
                        var4 = null;
                        var1.setline(492);
                        var4 = var1.getlocal(0).__getattr__("_ulaw2lin");
                        var1.getlocal(0).__setattr__("_convert", var4);
                        var4 = null;
                        var1.setline(493);
                        var4 = var1.getlocal(0).__getattr__("_framesize")._floordiv(Py.newInteger(2));
                        var1.getlocal(0).__setattr__("_framesize", var4);
                        var4 = null;
                        var1.setline(494);
                        var1.f_lasti = -1;
                        return Py.None;
                     } catch (Throwable var5) {
                        PyException var8 = Py.setException(var5, var1);
                        if (!var8.match(var1.getglobal("ImportError"))) {
                           throw var8;
                        }
                     }

                     var1.setline(496);
                  }

                  var1.setline(497);
                  throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot read compressed AIFF-C files"));
               }

               throw var10;
            }

            var1.setline(498);
            var3 = var1.getlocal(0).__getattr__("_comptype");
            var10000 = var3._eq(PyString.fromInterned("ULAW"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(499);
               var3 = var1.getlocal(5).__getattr__("G711_ULAW");
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(500);
               var3 = var1.getlocal(0).__getattr__("_framesize")._floordiv(Py.newInteger(2));
               var1.getlocal(0).__setattr__("_framesize", var3);
               var3 = null;
            } else {
               var1.setline(501);
               var3 = var1.getlocal(0).__getattr__("_comptype");
               var10000 = var3._eq(PyString.fromInterned("ALAW"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(505);
                  throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("unsupported compression type"));
               }

               var1.setline(502);
               var3 = var1.getlocal(5).__getattr__("G711_ALAW");
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(503);
               var3 = var1.getlocal(0).__getattr__("_framesize")._floordiv(Py.newInteger(2));
               var1.getlocal(0).__setattr__("_framesize", var3);
               var3 = null;
            }

            var1.setline(506);
            var3 = var1.getlocal(5).__getattr__("OpenDecompressor").__call__(var2, var1.getlocal(6));
            var1.getlocal(0).__setattr__("_decomp", var3);
            var3 = null;
            var1.setline(507);
            var3 = var1.getlocal(0).__getattr__("_decomp_data");
            var1.getlocal(0).__setattr__("_convert", var3);
            var3 = null;
         }
      } else {
         var1.setline(509);
         PyString var11 = PyString.fromInterned("NONE");
         var1.getlocal(0).__setattr__((String)"_comptype", var11);
         var3 = null;
         var1.setline(510);
         var11 = PyString.fromInterned("not compressed");
         var1.getlocal(0).__setattr__((String)"_compname", var11);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _readmark$36(PyFrame var1, ThreadState var2) {
      var1.setline(513);
      PyObject var3 = var1.getglobal("_read_short").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;

      PyObject var10000;
      PyObject var4;
      try {
         var1.setline(517);
         var3 = var1.getglobal("range").__call__(var2, var1.getlocal(2)).__iter__();

         while(true) {
            var1.setline(517);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(518);
            PyObject var5 = var1.getglobal("_read_short").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(519);
            var5 = var1.getglobal("_read_long").__call__(var2, var1.getlocal(1));
            var1.setlocal(5, var5);
            var5 = null;
            var1.setline(520);
            var5 = var1.getglobal("_read_string").__call__(var2, var1.getlocal(1));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(521);
            var10000 = var1.getlocal(5);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(6);
            }

            if (var10000.__nonzero__()) {
               var1.setline(525);
               var1.getlocal(0).__getattr__("_markers").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)})));
            }
         }
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getglobal("EOFError"))) {
            throw var7;
         }

         var1.setline(527);
         Py.printComma(PyString.fromInterned("Warning: MARK chunk contains only"));
         var1.setline(528);
         Py.printComma(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_markers")));
         var1.setline(529);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_markers"));
         var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(529);
            Py.printComma(PyString.fromInterned("marker"));
         } else {
            var1.setline(530);
            Py.printComma(PyString.fromInterned("markers"));
         }

         var1.setline(531);
         Py.printComma(PyString.fromInterned("instead of"));
         Py.println(var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Aifc_write$37(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(563);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$38, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(576);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, initfp$39, (PyObject)null);
      var1.setlocal("initfp", var4);
      var3 = null;
      var1.setline(594);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __del__$40, (PyObject)null);
      var1.setlocal("__del__", var4);
      var3 = null;
      var1.setline(601);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, aiff$41, (PyObject)null);
      var1.setlocal("aiff", var4);
      var3 = null;
      var1.setline(606);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, aifc$42, (PyObject)null);
      var1.setlocal("aifc", var4);
      var3 = null;
      var1.setline(611);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setnchannels$43, (PyObject)null);
      var1.setlocal("setnchannels", var4);
      var3 = null;
      var1.setline(618);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getnchannels$44, (PyObject)null);
      var1.setlocal("getnchannels", var4);
      var3 = null;
      var1.setline(623);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setsampwidth$45, (PyObject)null);
      var1.setlocal("setsampwidth", var4);
      var3 = null;
      var1.setline(630);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getsampwidth$46, (PyObject)null);
      var1.setlocal("getsampwidth", var4);
      var3 = null;
      var1.setline(635);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setframerate$47, (PyObject)null);
      var1.setlocal("setframerate", var4);
      var3 = null;
      var1.setline(642);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getframerate$48, (PyObject)null);
      var1.setlocal("getframerate", var4);
      var3 = null;
      var1.setline(647);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setnframes$49, (PyObject)null);
      var1.setlocal("setnframes", var4);
      var3 = null;
      var1.setline(652);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getnframes$50, (PyObject)null);
      var1.setlocal("getnframes", var4);
      var3 = null;
      var1.setline(655);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setcomptype$51, (PyObject)null);
      var1.setlocal("setcomptype", var4);
      var3 = null;
      var1.setline(663);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcomptype$52, (PyObject)null);
      var1.setlocal("getcomptype", var4);
      var3 = null;
      var1.setline(666);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcompname$53, (PyObject)null);
      var1.setlocal("getcompname", var4);
      var3 = null;
      var1.setline(674);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setparams$54, (PyObject)null);
      var1.setlocal("setparams", var4);
      var3 = null;
      var1.setline(686);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getparams$55, (PyObject)null);
      var1.setlocal("getparams", var4);
      var3 = null;
      var1.setline(692);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setmark$56, (PyObject)null);
      var1.setlocal("setmark", var4);
      var3 = null;
      var1.setline(705);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getmark$57, (PyObject)null);
      var1.setlocal("getmark", var4);
      var3 = null;
      var1.setline(711);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getmarkers$58, (PyObject)null);
      var1.setlocal("getmarkers", var4);
      var3 = null;
      var1.setline(716);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$59, (PyObject)null);
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(719);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writeframesraw$60, (PyObject)null);
      var1.setlocal("writeframesraw", var4);
      var3 = null;
      var1.setline(728);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, writeframes$61, (PyObject)null);
      var1.setlocal("writeframes", var4);
      var3 = null;
      var1.setline(734);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$62, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(762);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _comp_data$63, (PyObject)null);
      var1.setlocal("_comp_data", var4);
      var3 = null;
      var1.setline(768);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _lin2ulaw$64, (PyObject)null);
      var1.setlocal("_lin2ulaw", var4);
      var3 = null;
      var1.setline(772);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _lin2adpcm$65, (PyObject)null);
      var1.setlocal("_lin2adpcm", var4);
      var3 = null;
      var1.setline(780);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _ensure_header_written$66, (PyObject)null);
      var1.setlocal("_ensure_header_written", var4);
      var3 = null;
      var1.setline(800);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _init_compression$67, (PyObject)null);
      var1.setlocal("_init_compression", var4);
      var3 = null;
      var1.setline(838);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _write_header$68, (PyObject)null);
      var1.setlocal("_write_header", var4);
      var3 = null;
      var1.setline(881);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _write_form_length$69, (PyObject)null);
      var1.setlocal("_write_form_length", var4);
      var3 = null;
      var1.setline(894);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _patchheader$70, (PyObject)null);
      var1.setlocal("_patchheader", var4);
      var3 = null;
      var1.setline(916);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _writemarkers$71, (PyObject)null);
      var1.setlocal("_writemarkers", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$38(PyFrame var1, ThreadState var2) {
      var1.setline(564);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(565);
         var3 = var1.getlocal(1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(566);
         var3 = var1.getglobal("__builtin__").__getattr__("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(569);
         PyString var4 = PyString.fromInterned("???");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(570);
      var1.getlocal(0).__getattr__("initfp").__call__(var2, var1.getlocal(1));
      var1.setline(571);
      var3 = var1.getlocal(2).__getslice__(Py.newInteger(-5), (PyObject)null, (PyObject)null);
      var10000 = var3._eq(PyString.fromInterned(".aiff"));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(572);
         var5 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_aifc", var5);
         var3 = null;
      } else {
         var1.setline(574);
         var5 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"_aifc", var5);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initfp$39(PyFrame var1, ThreadState var2) {
      var1.setline(577);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_file", var3);
      var3 = null;
      var1.setline(578);
      var3 = var1.getglobal("_AIFC_version");
      var1.getlocal(0).__setattr__("_version", var3);
      var3 = null;
      var1.setline(579);
      PyString var4 = PyString.fromInterned("NONE");
      var1.getlocal(0).__setattr__((String)"_comptype", var4);
      var3 = null;
      var1.setline(580);
      var4 = PyString.fromInterned("not compressed");
      var1.getlocal(0).__setattr__((String)"_compname", var4);
      var3 = null;
      var1.setline(581);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_comp", var3);
      var3 = null;
      var1.setline(582);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_convert", var3);
      var3 = null;
      var1.setline(583);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_nchannels", var5);
      var3 = null;
      var1.setline(584);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_sampwidth", var5);
      var3 = null;
      var1.setline(585);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_framerate", var5);
      var3 = null;
      var1.setline(586);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_nframes", var5);
      var3 = null;
      var1.setline(587);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_nframeswritten", var5);
      var3 = null;
      var1.setline(588);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_datawritten", var5);
      var3 = null;
      var1.setline(589);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_datalength", var5);
      var3 = null;
      var1.setline(590);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_markers", var6);
      var3 = null;
      var1.setline(591);
      var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_marklength", var5);
      var3 = null;
      var1.setline(592);
      var5 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"_aifc", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __del__$40(PyFrame var1, ThreadState var2) {
      var1.setline(595);
      if (var1.getlocal(0).__getattr__("_file").__nonzero__()) {
         var1.setline(596);
         var1.getlocal(0).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject aiff$41(PyFrame var1, ThreadState var2) {
      var1.setline(602);
      if (var1.getlocal(0).__getattr__("_nframeswritten").__nonzero__()) {
         var1.setline(603);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot change parameters after starting to write"));
      } else {
         var1.setline(604);
         PyInteger var3 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"_aifc", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject aifc$42(PyFrame var1, ThreadState var2) {
      var1.setline(607);
      if (var1.getlocal(0).__getattr__("_nframeswritten").__nonzero__()) {
         var1.setline(608);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot change parameters after starting to write"));
      } else {
         var1.setline(609);
         PyInteger var3 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"_aifc", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject setnchannels$43(PyFrame var1, ThreadState var2) {
      var1.setline(612);
      if (var1.getlocal(0).__getattr__("_nframeswritten").__nonzero__()) {
         var1.setline(613);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot change parameters after starting to write"));
      } else {
         var1.setline(614);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._lt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(615);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("bad # of channels"));
         } else {
            var1.setline(616);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("_nchannels", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject getnchannels$44(PyFrame var1, ThreadState var2) {
      var1.setline(619);
      if (var1.getlocal(0).__getattr__("_nchannels").__not__().__nonzero__()) {
         var1.setline(620);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("number of channels not set"));
      } else {
         var1.setline(621);
         PyObject var3 = var1.getlocal(0).__getattr__("_nchannels");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setsampwidth$45(PyFrame var1, ThreadState var2) {
      var1.setline(624);
      if (var1.getlocal(0).__getattr__("_nframeswritten").__nonzero__()) {
         var1.setline(625);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot change parameters after starting to write"));
      } else {
         var1.setline(626);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._lt(Py.newInteger(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._gt(Py.newInteger(4));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(627);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("bad sample width"));
         } else {
            var1.setline(628);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("_sampwidth", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject getsampwidth$46(PyFrame var1, ThreadState var2) {
      var1.setline(631);
      if (var1.getlocal(0).__getattr__("_sampwidth").__not__().__nonzero__()) {
         var1.setline(632);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("sample width not set"));
      } else {
         var1.setline(633);
         PyObject var3 = var1.getlocal(0).__getattr__("_sampwidth");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setframerate$47(PyFrame var1, ThreadState var2) {
      var1.setline(636);
      if (var1.getlocal(0).__getattr__("_nframeswritten").__nonzero__()) {
         var1.setline(637);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot change parameters after starting to write"));
      } else {
         var1.setline(638);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(639);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("bad frame rate"));
         } else {
            var1.setline(640);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("_framerate", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject getframerate$48(PyFrame var1, ThreadState var2) {
      var1.setline(643);
      if (var1.getlocal(0).__getattr__("_framerate").__not__().__nonzero__()) {
         var1.setline(644);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("frame rate not set"));
      } else {
         var1.setline(645);
         PyObject var3 = var1.getlocal(0).__getattr__("_framerate");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setnframes$49(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      if (var1.getlocal(0).__getattr__("_nframeswritten").__nonzero__()) {
         var1.setline(649);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot change parameters after starting to write"));
      } else {
         var1.setline(650);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_nframes", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject getnframes$50(PyFrame var1, ThreadState var2) {
      var1.setline(653);
      PyObject var3 = var1.getlocal(0).__getattr__("_nframeswritten");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setcomptype$51(PyFrame var1, ThreadState var2) {
      var1.setline(656);
      if (var1.getlocal(0).__getattr__("_nframeswritten").__nonzero__()) {
         var1.setline(657);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot change parameters after starting to write"));
      } else {
         var1.setline(658);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("NONE"), PyString.fromInterned("ULAW"), PyString.fromInterned("ALAW"), PyString.fromInterned("G722")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(659);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("unsupported compression type"));
         } else {
            var1.setline(660);
            var3 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("_comptype", var3);
            var3 = null;
            var1.setline(661);
            var3 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("_compname", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject getcomptype$52(PyFrame var1, ThreadState var2) {
      var1.setline(664);
      PyObject var3 = var1.getlocal(0).__getattr__("_comptype");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getcompname$53(PyFrame var1, ThreadState var2) {
      var1.setline(667);
      PyObject var3 = var1.getlocal(0).__getattr__("_compname");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setparams$54(PyFrame var1, ThreadState var2) {
      var1.setline(675);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 6);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(676);
      if (var1.getlocal(0).__getattr__("_nframeswritten").__nonzero__()) {
         var1.setline(677);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot change parameters after starting to write"));
      } else {
         var1.setline(678);
         var3 = var1.getlocal(6);
         PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("NONE"), PyString.fromInterned("ULAW"), PyString.fromInterned("ALAW"), PyString.fromInterned("G722")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(679);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("unsupported compression type"));
         } else {
            var1.setline(680);
            var1.getlocal(0).__getattr__("setnchannels").__call__(var2, var1.getlocal(2));
            var1.setline(681);
            var1.getlocal(0).__getattr__("setsampwidth").__call__(var2, var1.getlocal(3));
            var1.setline(682);
            var1.getlocal(0).__getattr__("setframerate").__call__(var2, var1.getlocal(4));
            var1.setline(683);
            var1.getlocal(0).__getattr__("setnframes").__call__(var2, var1.getlocal(5));
            var1.setline(684);
            var1.getlocal(0).__getattr__("setcomptype").__call__(var2, var1.getlocal(6), var1.getlocal(7));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject getparams$55(PyFrame var1, ThreadState var2) {
      var1.setline(687);
      PyObject var10000 = var1.getlocal(0).__getattr__("_nchannels").__not__();
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_sampwidth").__not__();
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_framerate").__not__();
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(688);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("not all parameters set"));
      } else {
         var1.setline(689);
         PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_nchannels"), var1.getlocal(0).__getattr__("_sampwidth"), var1.getlocal(0).__getattr__("_framerate"), var1.getlocal(0).__getattr__("_nframes"), var1.getlocal(0).__getattr__("_comptype"), var1.getlocal(0).__getattr__("_compname")});
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject setmark$56(PyFrame var1, ThreadState var2) {
      var1.setline(693);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(694);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("marker ID must be > 0"));
      } else {
         var1.setline(695);
         var3 = var1.getlocal(2);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(696);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("marker position must be >= 0"));
         } else {
            var1.setline(697);
            var3 = var1.getglobal("type").__call__(var2, var1.getlocal(3));
            var10000 = var3._ne(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(698);
               throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("marker name must be a string"));
            } else {
               var1.setline(699);
               var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_markers"))).__iter__();

               PyObject var5;
               do {
                  var1.setline(699);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(703);
                     var1.getlocal(0).__getattr__("_markers").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(700);
                  var5 = var1.getlocal(1);
                  var10000 = var5._eq(var1.getlocal(0).__getattr__("_markers").__getitem__(var1.getlocal(4)).__getitem__(Py.newInteger(0)));
                  var5 = null;
               } while(!var10000.__nonzero__());

               var1.setline(701);
               PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
               var1.getlocal(0).__getattr__("_markers").__setitem__((PyObject)var1.getlocal(4), var6);
               var5 = null;
               var1.setline(702);
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject getmark$57(PyFrame var1, ThreadState var2) {
      var1.setline(706);
      PyObject var3 = var1.getlocal(0).__getattr__("_markers").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(706);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(709);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("marker %r does not exist")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
         }

         var1.setlocal(2, var4);
         var1.setline(707);
         var5 = var1.getlocal(1);
         var10000 = var5._eq(var1.getlocal(2).__getitem__(Py.newInteger(0)));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(708);
      var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject getmarkers$58(PyFrame var1, ThreadState var2) {
      var1.setline(712);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_markers"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(713);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(714);
         var3 = var1.getlocal(0).__getattr__("_markers");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject tell$59(PyFrame var1, ThreadState var2) {
      var1.setline(717);
      PyObject var3 = var1.getlocal(0).__getattr__("_nframeswritten");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject writeframesraw$60(PyFrame var1, ThreadState var2) {
      var1.setline(720);
      var1.getlocal(0).__getattr__("_ensure_header_written").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.setline(721);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._floordiv(var1.getlocal(0).__getattr__("_sampwidth")._mul(var1.getlocal(0).__getattr__("_nchannels")));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(722);
      if (var1.getlocal(0).__getattr__("_convert").__nonzero__()) {
         var1.setline(723);
         var3 = var1.getlocal(0).__getattr__("_convert").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(724);
      var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.setline(725);
      var3 = var1.getlocal(0).__getattr__("_nframeswritten")._add(var1.getlocal(2));
      var1.getlocal(0).__setattr__("_nframeswritten", var3);
      var3 = null;
      var1.setline(726);
      var3 = var1.getlocal(0).__getattr__("_datawritten")._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.getlocal(0).__setattr__("_datawritten", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject writeframes$61(PyFrame var1, ThreadState var2) {
      var1.setline(729);
      var1.getlocal(0).__getattr__("writeframesraw").__call__(var2, var1.getlocal(1));
      var1.setline(730);
      PyObject var3 = var1.getlocal(0).__getattr__("_nframeswritten");
      PyObject var10000 = var3._ne(var1.getlocal(0).__getattr__("_nframes"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_datalength");
         var10000 = var3._ne(var1.getlocal(0).__getattr__("_datawritten"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(732);
         var1.getlocal(0).__getattr__("_patchheader").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$62(PyFrame var1, ThreadState var2) {
      var1.setline(735);
      PyObject var3 = var1.getlocal(0).__getattr__("_file");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(736);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var3 = null;

         PyObject var4;
         try {
            var1.setline(738);
            var1.getlocal(0).__getattr__("_ensure_header_written").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setline(739);
            if (var1.getlocal(0).__getattr__("_datawritten")._and(Py.newInteger(1)).__nonzero__()) {
               var1.setline(741);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
               var1.setline(742);
               var4 = var1.getlocal(0).__getattr__("_datawritten")._add(Py.newInteger(1));
               var1.getlocal(0).__setattr__("_datawritten", var4);
               var4 = null;
            }

            var1.setline(743);
            var1.getlocal(0).__getattr__("_writemarkers").__call__(var2);
            var1.setline(744);
            var4 = var1.getlocal(0).__getattr__("_nframeswritten");
            var10000 = var4._ne(var1.getlocal(0).__getattr__("_nframes"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(0).__getattr__("_datalength");
               var10000 = var4._ne(var1.getlocal(0).__getattr__("_datawritten"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("_marklength");
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(747);
               var1.getlocal(0).__getattr__("_patchheader").__call__(var2);
            }

            var1.setline(748);
            if (var1.getlocal(0).__getattr__("_comp").__nonzero__()) {
               var1.setline(749);
               var1.getlocal(0).__getattr__("_comp").__getattr__("CloseCompressor").__call__(var2);
               var1.setline(750);
               var4 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("_comp", var4);
               var4 = null;
            }
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(753);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_convert", var4);
            var4 = null;
            var1.setline(754);
            var4 = var1.getlocal(0).__getattr__("_file");
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(755);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("_file", var4);
            var4 = null;
            var1.setline(756);
            var1.getlocal(1).__getattr__("close").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(753);
         var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_convert", var4);
         var4 = null;
         var1.setline(754);
         var4 = var1.getlocal(0).__getattr__("_file");
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(755);
         var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_file", var4);
         var4 = null;
         var1.setline(756);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _comp_data$63(PyFrame var1, ThreadState var2) {
      var1.setline(763);
      PyObject var3 = imp.importOne("cl", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(764);
      var3 = var1.getlocal(0).__getattr__("_comp").__getattr__("SetParam").__call__(var2, var1.getlocal(2).__getattr__("FRAME_BUFFER_SIZE"), var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(765);
      var3 = var1.getlocal(0).__getattr__("_comp").__getattr__("SetParam").__call__(var2, var1.getlocal(2).__getattr__("COMPRESSED_BUFFER_SIZE"), var1.getglobal("len").__call__(var2, var1.getlocal(1)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(766);
      var3 = var1.getlocal(0).__getattr__("_comp").__getattr__("Compress").__call__(var2, var1.getlocal(0).__getattr__("_nframes"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _lin2ulaw$64(PyFrame var1, ThreadState var2) {
      var1.setline(769);
      PyObject var3 = imp.importOne("audioop", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(770);
      var3 = var1.getlocal(2).__getattr__("lin2ulaw").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _lin2adpcm$65(PyFrame var1, ThreadState var2) {
      var1.setline(773);
      PyObject var3 = imp.importOne("audioop", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(774);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_adpcmstate")).__not__().__nonzero__()) {
         var1.setline(775);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("_adpcmstate", var3);
         var3 = null;
      }

      var1.setline(776);
      var3 = var1.getlocal(2).__getattr__("lin2adpcm").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)Py.newInteger(2), (PyObject)var1.getlocal(0).__getattr__("_adpcmstate"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("_adpcmstate", var5);
      var5 = null;
      var3 = null;
      var1.setline(778);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ensure_header_written$66(PyFrame var1, ThreadState var2) {
      var1.setline(781);
      if (var1.getlocal(0).__getattr__("_nframeswritten").__not__().__nonzero__()) {
         var1.setline(782);
         PyObject var3 = var1.getlocal(0).__getattr__("_comptype");
         PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("ULAW"), PyString.fromInterned("ALAW")}));
         var3 = null;
         PyInteger var4;
         if (var10000.__nonzero__()) {
            var1.setline(783);
            if (var1.getlocal(0).__getattr__("_sampwidth").__not__().__nonzero__()) {
               var1.setline(784);
               var4 = Py.newInteger(2);
               var1.getlocal(0).__setattr__((String)"_sampwidth", var4);
               var3 = null;
            }

            var1.setline(785);
            var3 = var1.getlocal(0).__getattr__("_sampwidth");
            var10000 = var3._ne(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(786);
               throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("sample width must be 2 when compressing with ULAW or ALAW"));
            }
         }

         var1.setline(787);
         var3 = var1.getlocal(0).__getattr__("_comptype");
         var10000 = var3._eq(PyString.fromInterned("G722"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(788);
            if (var1.getlocal(0).__getattr__("_sampwidth").__not__().__nonzero__()) {
               var1.setline(789);
               var4 = Py.newInteger(2);
               var1.getlocal(0).__setattr__((String)"_sampwidth", var4);
               var3 = null;
            }

            var1.setline(790);
            var3 = var1.getlocal(0).__getattr__("_sampwidth");
            var10000 = var3._ne(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(791);
               throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("sample width must be 2 when compressing with G7.22 (ADPCM)"));
            }
         }

         var1.setline(792);
         if (var1.getlocal(0).__getattr__("_nchannels").__not__().__nonzero__()) {
            var1.setline(793);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("# channels not specified"));
         }

         var1.setline(794);
         if (var1.getlocal(0).__getattr__("_sampwidth").__not__().__nonzero__()) {
            var1.setline(795);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("sample width not specified"));
         }

         var1.setline(796);
         if (var1.getlocal(0).__getattr__("_framerate").__not__().__nonzero__()) {
            var1.setline(797);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("sampling rate not specified"));
         }

         var1.setline(798);
         var1.getlocal(0).__getattr__("_write_header").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_compression$67(PyFrame var1, ThreadState var2) {
      var1.setline(801);
      PyObject var3 = var1.getlocal(0).__getattr__("_comptype");
      PyObject var10000 = var3._eq(PyString.fromInterned("G722"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(802);
         var3 = var1.getlocal(0).__getattr__("_lin2adpcm");
         var1.getlocal(0).__setattr__("_convert", var3);
         var3 = null;
         var1.setline(803);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         try {
            var1.setline(805);
            var3 = imp.importOne("cl", var1, -1);
            var1.setlocal(1, var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("ImportError"))) {
               var1.setline(807);
               PyObject var4 = var1.getlocal(0).__getattr__("_comptype");
               var10000 = var4._eq(PyString.fromInterned("ULAW"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  try {
                     var1.setline(809);
                     var4 = imp.importOne("audioop", var1, -1);
                     var1.setlocal(2, var4);
                     var4 = null;
                     var1.setline(810);
                     var4 = var1.getlocal(0).__getattr__("_lin2ulaw");
                     var1.getlocal(0).__setattr__("_convert", var4);
                     var4 = null;
                     var1.setline(811);
                     var1.f_lasti = -1;
                     return Py.None;
                  } catch (Throwable var5) {
                     PyException var8 = Py.setException(var5, var1);
                     if (!var8.match(var1.getglobal("ImportError"))) {
                        throw var8;
                     }
                  }

                  var1.setline(813);
               }

               var1.setline(814);
               throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot write compressed AIFF-C files"));
            }

            throw var7;
         }

         var1.setline(815);
         var3 = var1.getlocal(0).__getattr__("_comptype");
         var10000 = var3._eq(PyString.fromInterned("ULAW"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(816);
            var3 = var1.getlocal(1).__getattr__("G711_ULAW");
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(817);
            var3 = var1.getlocal(0).__getattr__("_comptype");
            var10000 = var3._eq(PyString.fromInterned("ALAW"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(820);
               throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("unsupported compression type"));
            }

            var1.setline(818);
            var3 = var1.getlocal(1).__getattr__("G711_ALAW");
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(821);
         var3 = var1.getlocal(1).__getattr__("OpenCompressor").__call__(var2, var1.getlocal(3));
         var1.getlocal(0).__setattr__("_comp", var3);
         var3 = null;
         var1.setline(822);
         PyList var9 = new PyList(new PyObject[]{var1.getlocal(1).__getattr__("ORIGINAL_FORMAT"), Py.newInteger(0), var1.getlocal(1).__getattr__("BITS_PER_COMPONENT"), var1.getlocal(0).__getattr__("_sampwidth")._mul(Py.newInteger(8)), var1.getlocal(1).__getattr__("FRAME_RATE"), var1.getlocal(0).__getattr__("_framerate"), var1.getlocal(1).__getattr__("FRAME_BUFFER_SIZE"), Py.newInteger(100), var1.getlocal(1).__getattr__("COMPRESSED_BUFFER_SIZE"), Py.newInteger(100)});
         var1.setlocal(4, var9);
         var3 = null;
         var1.setline(827);
         var3 = var1.getlocal(0).__getattr__("_nchannels");
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(828);
            var3 = var1.getlocal(1).__getattr__("MONO");
            var1.getlocal(4).__setitem__((PyObject)Py.newInteger(1), var3);
            var3 = null;
         } else {
            var1.setline(829);
            var3 = var1.getlocal(0).__getattr__("_nchannels");
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(832);
               throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("cannot compress more than 2 channels"));
            }

            var1.setline(830);
            var3 = var1.getlocal(1).__getattr__("STEREO_INTERLEAVED");
            var1.getlocal(4).__setitem__((PyObject)Py.newInteger(1), var3);
            var3 = null;
         }

         var1.setline(833);
         var1.getlocal(0).__getattr__("_comp").__getattr__("SetParams").__call__(var2, var1.getlocal(4));
         var1.setline(835);
         var3 = var1.getlocal(0).__getattr__("_comp").__getattr__("Compress").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned(""));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(836);
         var3 = var1.getlocal(0).__getattr__("_comp_data");
         var1.getlocal(0).__setattr__("_convert", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _write_header$68(PyFrame var1, ThreadState var2) {
      var1.setline(839);
      PyObject var10000 = var1.getlocal(0).__getattr__("_aifc");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_comptype");
         var10000 = var3._ne(PyString.fromInterned("NONE"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(840);
         var1.getlocal(0).__getattr__("_init_compression").__call__(var2);
      }

      var1.setline(841);
      var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FORM"));
      var1.setline(842);
      if (var1.getlocal(0).__getattr__("_nframes").__not__().__nonzero__()) {
         var1.setline(843);
         var3 = var1.getlocal(1)._floordiv(var1.getlocal(0).__getattr__("_nchannels")._mul(var1.getlocal(0).__getattr__("_sampwidth")));
         var1.getlocal(0).__setattr__("_nframes", var3);
         var3 = null;
      }

      var1.setline(844);
      var3 = var1.getlocal(0).__getattr__("_nframes")._mul(var1.getlocal(0).__getattr__("_nchannels"))._mul(var1.getlocal(0).__getattr__("_sampwidth"));
      var1.getlocal(0).__setattr__("_datalength", var3);
      var3 = null;
      var1.setline(845);
      if (var1.getlocal(0).__getattr__("_datalength")._and(Py.newInteger(1)).__nonzero__()) {
         var1.setline(846);
         var3 = var1.getlocal(0).__getattr__("_datalength")._add(Py.newInteger(1));
         var1.getlocal(0).__setattr__("_datalength", var3);
         var3 = null;
      }

      var1.setline(847);
      if (var1.getlocal(0).__getattr__("_aifc").__nonzero__()) {
         var1.setline(848);
         var3 = var1.getlocal(0).__getattr__("_comptype");
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("ULAW"), PyString.fromInterned("ALAW")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(849);
            var3 = var1.getlocal(0).__getattr__("_datalength")._floordiv(Py.newInteger(2));
            var1.getlocal(0).__setattr__("_datalength", var3);
            var3 = null;
            var1.setline(850);
            if (var1.getlocal(0).__getattr__("_datalength")._and(Py.newInteger(1)).__nonzero__()) {
               var1.setline(851);
               var3 = var1.getlocal(0).__getattr__("_datalength")._add(Py.newInteger(1));
               var1.getlocal(0).__setattr__("_datalength", var3);
               var3 = null;
            }
         } else {
            var1.setline(852);
            var3 = var1.getlocal(0).__getattr__("_comptype");
            var10000 = var3._eq(PyString.fromInterned("G722"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(853);
               var3 = var1.getlocal(0).__getattr__("_datalength")._add(Py.newInteger(3))._floordiv(Py.newInteger(4));
               var1.getlocal(0).__setattr__("_datalength", var3);
               var3 = null;
               var1.setline(854);
               if (var1.getlocal(0).__getattr__("_datalength")._and(Py.newInteger(1)).__nonzero__()) {
                  var1.setline(855);
                  var3 = var1.getlocal(0).__getattr__("_datalength")._add(Py.newInteger(1));
                  var1.getlocal(0).__setattr__("_datalength", var3);
                  var3 = null;
               }
            }
         }
      }

      var1.setline(856);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("_form_length_pos", var3);
      var3 = null;
      var1.setline(857);
      var3 = var1.getlocal(0).__getattr__("_write_form_length").__call__(var2, var1.getlocal(0).__getattr__("_datalength"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(858);
      if (var1.getlocal(0).__getattr__("_aifc").__nonzero__()) {
         var1.setline(859);
         var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AIFC"));
         var1.setline(860);
         var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FVER"));
         var1.setline(861);
         var1.getglobal("_write_ulong").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_file"), (PyObject)Py.newInteger(4));
         var1.setline(862);
         var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_version"));
      } else {
         var1.setline(864);
         var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AIFF"));
      }

      var1.setline(865);
      var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("COMM"));
      var1.setline(866);
      var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(2));
      var1.setline(867);
      var1.getglobal("_write_short").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_nchannels"));
      var1.setline(868);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("_nframes_pos", var3);
      var3 = null;
      var1.setline(869);
      var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_nframes"));
      var1.setline(870);
      var1.getglobal("_write_short").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_sampwidth")._mul(Py.newInteger(8)));
      var1.setline(871);
      var1.getglobal("_write_float").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_framerate"));
      var1.setline(872);
      if (var1.getlocal(0).__getattr__("_aifc").__nonzero__()) {
         var1.setline(873);
         var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("_comptype"));
         var1.setline(874);
         var1.getglobal("_write_string").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_compname"));
      }

      var1.setline(875);
      var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SSND"));
      var1.setline(876);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("_ssnd_length_pos", var3);
      var3 = null;
      var1.setline(877);
      var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_datalength")._add(Py.newInteger(8)));
      var1.setline(878);
      var1.getglobal("_write_ulong").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_file"), (PyObject)Py.newInteger(0));
      var1.setline(879);
      var1.getglobal("_write_ulong").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_file"), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _write_form_length$69(PyFrame var1, ThreadState var2) {
      var1.setline(882);
      PyObject var3;
      PyInteger var4;
      if (var1.getlocal(0).__getattr__("_aifc").__nonzero__()) {
         var1.setline(883);
         var3 = Py.newInteger(18)._add(Py.newInteger(5))._add(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_compname")));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(884);
         if (var1.getlocal(2)._and(Py.newInteger(1)).__nonzero__()) {
            var1.setline(885);
            var3 = var1.getlocal(2)._add(Py.newInteger(1));
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(886);
         var4 = Py.newInteger(12);
         var1.setlocal(3, var4);
         var3 = null;
      } else {
         var1.setline(888);
         var4 = Py.newInteger(18);
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(889);
         var4 = Py.newInteger(0);
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(890);
      var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0).__getattr__("_file"), Py.newInteger(4)._add(var1.getlocal(3))._add(var1.getlocal(0).__getattr__("_marklength"))._add(Py.newInteger(8))._add(var1.getlocal(2))._add(Py.newInteger(16))._add(var1.getlocal(1)));
      var1.setline(892);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _patchheader$70(PyFrame var1, ThreadState var2) {
      var1.setline(895);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(896);
      if (var1.getlocal(0).__getattr__("_datawritten")._and(Py.newInteger(1)).__nonzero__()) {
         var1.setline(897);
         var3 = var1.getlocal(0).__getattr__("_datawritten")._add(Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(898);
         var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getglobal("chr").__call__((ThreadState)var2, (PyObject)Py.newInteger(0)));
      } else {
         var1.setline(900);
         var3 = var1.getlocal(0).__getattr__("_datawritten");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(901);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("_datalength"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_nframes");
         var10000 = var3._eq(var1.getlocal(0).__getattr__("_nframeswritten"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_marklength");
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(904);
         var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
         var1.setline(905);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(906);
         var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_form_length_pos"), (PyObject)Py.newInteger(0));
         var1.setline(907);
         var3 = var1.getlocal(0).__getattr__("_write_form_length").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(908);
         var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_nframes_pos"), (PyObject)Py.newInteger(0));
         var1.setline(909);
         var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_nframeswritten"));
         var1.setline(910);
         var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_ssnd_length_pos"), (PyObject)Py.newInteger(0));
         var1.setline(911);
         var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(2)._add(Py.newInteger(8)));
         var1.setline(912);
         var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(0));
         var1.setline(913);
         var3 = var1.getlocal(0).__getattr__("_nframeswritten");
         var1.getlocal(0).__setattr__("_nframes", var3);
         var3 = null;
         var1.setline(914);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("_datalength", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _writemarkers$71(PyFrame var1, ThreadState var2) {
      var1.setline(917);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_markers"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(918);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(919);
         var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MARK"));
         var1.setline(920);
         PyInteger var8 = Py.newInteger(2);
         var1.setlocal(1, var8);
         var3 = null;
         var1.setline(921);
         var3 = var1.getlocal(0).__getattr__("_markers").__iter__();

         while(true) {
            var1.setline(921);
            PyObject var4 = var3.__iternext__();
            PyObject var5;
            PyObject[] var6;
            PyObject var7;
            if (var4 == null) {
               var1.setline(926);
               var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(1));
               var1.setline(927);
               var3 = var1.getlocal(1)._add(Py.newInteger(8));
               var1.getlocal(0).__setattr__("_marklength", var3);
               var3 = null;
               var1.setline(928);
               var1.getglobal("_write_short").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_markers")));
               var1.setline(929);
               var3 = var1.getlocal(0).__getattr__("_markers").__iter__();

               while(true) {
                  var1.setline(929);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(2, var4);
                  var1.setline(930);
                  var5 = var1.getlocal(2);
                  var6 = Py.unpackSequence(var5, 3);
                  var7 = var6[0];
                  var1.setlocal(3, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(4, var7);
                  var7 = null;
                  var7 = var6[2];
                  var1.setlocal(5, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(931);
                  var1.getglobal("_write_short").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(3));
                  var1.setline(932);
                  var1.getglobal("_write_ulong").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(4));
                  var1.setline(933);
                  var1.getglobal("_write_string").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(5));
               }
            }

            var1.setlocal(2, var4);
            var1.setline(922);
            var5 = var1.getlocal(2);
            var6 = Py.unpackSequence(var5, 3);
            var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var7 = var6[2];
            var1.setlocal(5, var7);
            var7 = null;
            var5 = null;
            var1.setline(923);
            var5 = var1.getlocal(1)._add(var1.getglobal("len").__call__(var2, var1.getlocal(5)))._add(Py.newInteger(1))._add(Py.newInteger(6));
            var1.setlocal(1, var5);
            var5 = null;
            var1.setline(924);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(5))._and(Py.newInteger(1));
            var10000 = var5._eq(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(925);
               var5 = var1.getlocal(1)._add(Py.newInteger(1));
               var1.setlocal(1, var5);
               var5 = null;
            }
         }
      }
   }

   public PyObject open$72(PyFrame var1, ThreadState var2) {
      var1.setline(936);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(937);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("mode")).__nonzero__()) {
            var1.setline(938);
            var3 = var1.getlocal(0).__getattr__("mode");
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(940);
            PyString var5 = PyString.fromInterned("rb");
            var1.setlocal(1, var5);
            var3 = null;
         }
      }

      var1.setline(941);
      var3 = var1.getlocal(1);
      var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("r"), PyString.fromInterned("rb")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(942);
         var3 = var1.getglobal("Aifc_read").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(943);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("w"), PyString.fromInterned("wb")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(944);
            var3 = var1.getglobal("Aifc_write").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(946);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("mode must be 'r', 'rb', 'w', or 'wb'"));
         }
      }
   }

   public aifc$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 142, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"file"};
      _read_long$2 = Py.newCode(1, var2, var1, "_read_long", 147, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file"};
      _read_ulong$3 = Py.newCode(1, var2, var1, "_read_ulong", 153, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file"};
      _read_short$4 = Py.newCode(1, var2, var1, "_read_short", 159, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file"};
      _read_ushort$5 = Py.newCode(1, var2, var1, "_read_ushort", 165, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "length", "data", "dummy"};
      _read_string$6 = Py.newCode(1, var2, var1, "_read_string", 171, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "expon", "sign", "himant", "lomant"};
      _read_float$7 = Py.newCode(1, var2, var1, "_read_float", 183, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "x"};
      _write_short$8 = Py.newCode(2, var2, var1, "_write_short", 200, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "x"};
      _write_ushort$9 = Py.newCode(2, var2, var1, "_write_ushort", 203, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "x"};
      _write_long$10 = Py.newCode(2, var2, var1, "_write_long", 206, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "x"};
      _write_ulong$11 = Py.newCode(2, var2, var1, "_write_ulong", 209, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "s"};
      _write_string$12 = Py.newCode(2, var2, var1, "_write_string", 212, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "x", "math", "sign", "expon", "himant", "lomant", "fmant", "fsmant"};
      _write_float$13 = Py.newCode(2, var2, var1, "_write_float", 220, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Aifc_read$14 = Py.newCode(0, var2, var1, "Aifc_read", 255, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "chunk", "formdata", "chunkname", "dummy", "cl", "params"};
      initfp$15 = Py.newCode(2, var2, var1, "initfp", 291, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      __init__$16 = Py.newCode(2, var2, var1, "__init__", 343, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getfp$17 = Py.newCode(1, var2, var1, "getfp", 352, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      rewind$18 = Py.newCode(1, var2, var1, "rewind", 355, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$19 = Py.newCode(1, var2, var1, "close", 359, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$20 = Py.newCode(1, var2, var1, "tell", 365, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getnchannels$21 = Py.newCode(1, var2, var1, "getnchannels", 368, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getnframes$22 = Py.newCode(1, var2, var1, "getnframes", 371, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getsampwidth$23 = Py.newCode(1, var2, var1, "getsampwidth", 374, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getframerate$24 = Py.newCode(1, var2, var1, "getframerate", 377, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getcomptype$25 = Py.newCode(1, var2, var1, "getcomptype", 380, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getcompname$26 = Py.newCode(1, var2, var1, "getcompname", 383, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getparams$27 = Py.newCode(1, var2, var1, "getparams", 389, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getmarkers$28 = Py.newCode(1, var2, var1, "getmarkers", 394, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id", "marker"};
      getmark$29 = Py.newCode(2, var2, var1, "getmark", 399, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos"};
      setpos$30 = Py.newCode(2, var2, var1, "setpos", 405, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nframes", "dummy", "pos", "data"};
      readframes$31 = Py.newCode(2, var2, var1, "readframes", 411, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "cl", "dummy"};
      _decomp_data$32 = Py.newCode(2, var2, var1, "_decomp_data", 431, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "audioop"};
      _ulaw2lin$33 = Py.newCode(2, var2, var1, "_ulaw2lin", 438, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "audioop"};
      _adpcm2lin$34 = Py.newCode(2, var2, var1, "_adpcm2lin", 442, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chunk", "kludge", "length", "audioop", "cl", "scheme"};
      _read_comm_chunk$35 = Py.newCode(2, var2, var1, "_read_comm_chunk", 451, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "chunk", "nmarkers", "i", "id", "pos", "name"};
      _readmark$36 = Py.newCode(2, var2, var1, "_readmark", 512, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Aifc_write$37 = Py.newCode(0, var2, var1, "Aifc_write", 533, false, false, self, 37, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "f", "filename"};
      __init__$38 = Py.newCode(2, var2, var1, "__init__", 563, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      initfp$39 = Py.newCode(2, var2, var1, "initfp", 576, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __del__$40 = Py.newCode(1, var2, var1, "__del__", 594, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      aiff$41 = Py.newCode(1, var2, var1, "aiff", 601, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      aifc$42 = Py.newCode(1, var2, var1, "aifc", 606, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nchannels"};
      setnchannels$43 = Py.newCode(2, var2, var1, "setnchannels", 611, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getnchannels$44 = Py.newCode(1, var2, var1, "getnchannels", 618, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sampwidth"};
      setsampwidth$45 = Py.newCode(2, var2, var1, "setsampwidth", 623, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getsampwidth$46 = Py.newCode(1, var2, var1, "getsampwidth", 630, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "framerate"};
      setframerate$47 = Py.newCode(2, var2, var1, "setframerate", 635, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getframerate$48 = Py.newCode(1, var2, var1, "getframerate", 642, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "nframes"};
      setnframes$49 = Py.newCode(2, var2, var1, "setnframes", 647, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getnframes$50 = Py.newCode(1, var2, var1, "getnframes", 652, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "comptype", "compname"};
      setcomptype$51 = Py.newCode(3, var2, var1, "setcomptype", 655, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getcomptype$52 = Py.newCode(1, var2, var1, "getcomptype", 663, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getcompname$53 = Py.newCode(1, var2, var1, "getcompname", 666, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "info", "nchannels", "sampwidth", "framerate", "nframes", "comptype", "compname"};
      setparams$54 = Py.newCode(2, var2, var1, "setparams", 674, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getparams$55 = Py.newCode(1, var2, var1, "getparams", 686, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id", "pos", "name", "i"};
      setmark$56 = Py.newCode(4, var2, var1, "setmark", 692, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "id", "marker"};
      getmark$57 = Py.newCode(2, var2, var1, "getmark", 705, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getmarkers$58 = Py.newCode(1, var2, var1, "getmarkers", 711, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$59 = Py.newCode(1, var2, var1, "tell", 716, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "nframes"};
      writeframesraw$60 = Py.newCode(2, var2, var1, "writeframesraw", 719, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      writeframes$61 = Py.newCode(2, var2, var1, "writeframes", 728, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      close$62 = Py.newCode(1, var2, var1, "close", 734, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "cl", "dummy"};
      _comp_data$63 = Py.newCode(2, var2, var1, "_comp_data", 762, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "audioop"};
      _lin2ulaw$64 = Py.newCode(2, var2, var1, "_lin2ulaw", 768, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "audioop"};
      _lin2adpcm$65 = Py.newCode(2, var2, var1, "_lin2adpcm", 772, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "datasize"};
      _ensure_header_written$66 = Py.newCode(2, var2, var1, "_ensure_header_written", 780, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cl", "audioop", "scheme", "params", "dummy"};
      _init_compression$67 = Py.newCode(1, var2, var1, "_init_compression", 800, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "initlength", "commlength"};
      _write_header$68 = Py.newCode(2, var2, var1, "_write_header", 838, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "datalength", "commlength", "verslength"};
      _write_form_length$69 = Py.newCode(2, var2, var1, "_write_form_length", 881, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "curpos", "datalength", "dummy"};
      _patchheader$70 = Py.newCode(1, var2, var1, "_patchheader", 894, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "length", "marker", "id", "pos", "name"};
      _writemarkers$71 = Py.newCode(1, var2, var1, "_writemarkers", 916, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "mode"};
      open$72 = Py.newCode(2, var2, var1, "open", 935, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new aifc$py("aifc$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(aifc$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this._read_long$2(var2, var3);
         case 3:
            return this._read_ulong$3(var2, var3);
         case 4:
            return this._read_short$4(var2, var3);
         case 5:
            return this._read_ushort$5(var2, var3);
         case 6:
            return this._read_string$6(var2, var3);
         case 7:
            return this._read_float$7(var2, var3);
         case 8:
            return this._write_short$8(var2, var3);
         case 9:
            return this._write_ushort$9(var2, var3);
         case 10:
            return this._write_long$10(var2, var3);
         case 11:
            return this._write_ulong$11(var2, var3);
         case 12:
            return this._write_string$12(var2, var3);
         case 13:
            return this._write_float$13(var2, var3);
         case 14:
            return this.Aifc_read$14(var2, var3);
         case 15:
            return this.initfp$15(var2, var3);
         case 16:
            return this.__init__$16(var2, var3);
         case 17:
            return this.getfp$17(var2, var3);
         case 18:
            return this.rewind$18(var2, var3);
         case 19:
            return this.close$19(var2, var3);
         case 20:
            return this.tell$20(var2, var3);
         case 21:
            return this.getnchannels$21(var2, var3);
         case 22:
            return this.getnframes$22(var2, var3);
         case 23:
            return this.getsampwidth$23(var2, var3);
         case 24:
            return this.getframerate$24(var2, var3);
         case 25:
            return this.getcomptype$25(var2, var3);
         case 26:
            return this.getcompname$26(var2, var3);
         case 27:
            return this.getparams$27(var2, var3);
         case 28:
            return this.getmarkers$28(var2, var3);
         case 29:
            return this.getmark$29(var2, var3);
         case 30:
            return this.setpos$30(var2, var3);
         case 31:
            return this.readframes$31(var2, var3);
         case 32:
            return this._decomp_data$32(var2, var3);
         case 33:
            return this._ulaw2lin$33(var2, var3);
         case 34:
            return this._adpcm2lin$34(var2, var3);
         case 35:
            return this._read_comm_chunk$35(var2, var3);
         case 36:
            return this._readmark$36(var2, var3);
         case 37:
            return this.Aifc_write$37(var2, var3);
         case 38:
            return this.__init__$38(var2, var3);
         case 39:
            return this.initfp$39(var2, var3);
         case 40:
            return this.__del__$40(var2, var3);
         case 41:
            return this.aiff$41(var2, var3);
         case 42:
            return this.aifc$42(var2, var3);
         case 43:
            return this.setnchannels$43(var2, var3);
         case 44:
            return this.getnchannels$44(var2, var3);
         case 45:
            return this.setsampwidth$45(var2, var3);
         case 46:
            return this.getsampwidth$46(var2, var3);
         case 47:
            return this.setframerate$47(var2, var3);
         case 48:
            return this.getframerate$48(var2, var3);
         case 49:
            return this.setnframes$49(var2, var3);
         case 50:
            return this.getnframes$50(var2, var3);
         case 51:
            return this.setcomptype$51(var2, var3);
         case 52:
            return this.getcomptype$52(var2, var3);
         case 53:
            return this.getcompname$53(var2, var3);
         case 54:
            return this.setparams$54(var2, var3);
         case 55:
            return this.getparams$55(var2, var3);
         case 56:
            return this.setmark$56(var2, var3);
         case 57:
            return this.getmark$57(var2, var3);
         case 58:
            return this.getmarkers$58(var2, var3);
         case 59:
            return this.tell$59(var2, var3);
         case 60:
            return this.writeframesraw$60(var2, var3);
         case 61:
            return this.writeframes$61(var2, var3);
         case 62:
            return this.close$62(var2, var3);
         case 63:
            return this._comp_data$63(var2, var3);
         case 64:
            return this._lin2ulaw$64(var2, var3);
         case 65:
            return this._lin2adpcm$65(var2, var3);
         case 66:
            return this._ensure_header_written$66(var2, var3);
         case 67:
            return this._init_compression$67(var2, var3);
         case 68:
            return this._write_header$68(var2, var3);
         case 69:
            return this._write_form_length$69(var2, var3);
         case 70:
            return this._patchheader$70(var2, var3);
         case 71:
            return this._writemarkers$71(var2, var3);
         case 72:
            return this.open$72(var2, var3);
         default:
            return null;
      }
   }
}
