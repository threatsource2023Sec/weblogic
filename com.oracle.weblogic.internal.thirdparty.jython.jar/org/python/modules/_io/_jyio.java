package org.python.modules._io;

import org.python.core.ArgParser;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyStringMap;
import org.python.core.PyType;
import org.python.core.imp;

public class _jyio implements ClassDictInit {
   public static PyType UnsupportedOperation;
   private static final int _DEFAULT_BUFFER_SIZE = 8192;
   public static final PyInteger DEFAULT_BUFFER_SIZE = new PyInteger(8192);
   private static final String[] openKwds = new String[]{"file", "mode", "buffering", "encoding", "errors", "newline", "closefd"};
   public static final String __doc__ = "The io module provides the Python interfaces to stream handling. The\nbuiltin open function is defined in this module.\n\nAt the top of the I/O hierarchy is the abstract base class IOBase. It\ndefines the basic interface to a stream. Note, however, that there is no\nseperation between reading and writing to streams; implementations are\nallowed to throw an IOError if they do not support a given operation.\n\nExtending IOBase is RawIOBase which deals simply with the reading and\nwriting of raw bytes to a stream. FileIO subclasses RawIOBase to provide\nan interface to OS files.\n\nBufferedIOBase deals with buffering on a raw byte stream (RawIOBase). Its\nsubclasses, BufferedWriter, BufferedReader, and BufferedRWPair buffer\nstreams that are readable, writable, and both respectively.\nBufferedRandom provides a buffered interface to random access\nstreams. BytesIO is a simple stream of in-memory bytes.\n\nAnother IOBase subclass, TextIOBase, deals with the encoding and decoding\nof streams into text. TextIOWrapper, which extends it, is a buffered text\ninterface to a buffered raw stream (`BufferedIOBase`). Finally, StringIO\nis a in-memory stream for text.\n\nArgument names are not part of the specification, and only the arguments\nof open() are intended to be used as keyword arguments.\n";
   public static final String __doc__open = "Open file and return a stream.  Raise IOError upon failure.\n\nfile is either a text or byte string giving the name (and the path\nif the file isn't in the current working directory) of the file to\nbe opened or an integer file descriptor of the file to be\nwrapped. (If a file descriptor is given, it is closed when the\nreturned I/O object is closed, unless closefd is set to False.)\n\nmode is an optional string that specifies the mode in which the file\nis opened. It defaults to 'r' which means open for reading in text\nmode.  Other common values are 'w' for writing (truncating the file if\nit already exists), and 'a' for appending (which on some Unix systems,\nmeans that all writes append to the end of the file regardless of the\ncurrent seek position). In text mode, if encoding is not specified the\nencoding used is platform dependent. (For reading and writing raw\nbytes use binary mode and leave encoding unspecified.) The available\nmodes are:\n\n========= ===============================================================\nCharacter Meaning\n--------- ---------------------------------------------------------------\n'r'       open for reading (default)\n'w'       open for writing, truncating the file first\n'a'       open for writing, appending to the end of the file if it exists\n'b'       binary mode\n't'       text mode (default)\n'+'       open a disk file for updating (reading and writing)\n'U'       universal newline mode (for backwards compatibility; unneeded\n          for new code)\n========= ===============================================================\n\nThe default mode is 'rt' (open for reading text). For binary random\naccess, the mode 'w+b' opens and truncates the file to 0 bytes, while\n'r+b' opens the file without truncation.\n\nPython distinguishes between files opened in binary and text modes,\neven when the underlying operating system doesn't. Files opened in\nbinary mode (appending 'b' to the mode argument) return contents as\nbytes objects without any decoding. In text mode (the default, or when\n't' is appended to the mode argument), the contents of the file are\nreturned as strings, the bytes having been first decoded using a\nplatform-dependent encoding or using the specified encoding if given.\n\nbuffering is an optional integer used to set the buffering policy.\nPass 0 to switch buffering off (only allowed in binary mode), 1 to select\nline buffering (only usable in text mode), and an integer > 1 to indicate\nthe size of a fixed-size chunk buffer.  When no buffering argument is\ngiven, the default buffering policy works as follows:\n\n* Binary files are buffered in fixed-size chunks; the size of the buffer\n  is chosen using a heuristic trying to determine the underlying device's\n  \"block size\" and falling back on `io.DEFAULT_BUFFER_SIZE`.\n  On many systems, the buffer will typically be 4096 or 8192 bytes long.\n\n* \"Interactive\" text files (files for which isatty() returns True)\n  use line buffering.  Other text files use the policy described above\n  for binary files.\n\nencoding is the name of the encoding used to decode or encode the\nfile. This should only be used in text mode. The default encoding is\nplatform dependent, but any encoding supported by Python can be\npassed.  See the codecs module for the list of supported encodings.\n\nerrors is an optional string that specifies how encoding errors are to\nbe handled---this argument should not be used in binary mode. Pass\n'strict' to raise a ValueError exception if there is an encoding error\n(the default of None has the same effect), or pass 'ignore' to ignore\nerrors. (Note that ignoring encoding errors can lead to data loss.)\nSee the documentation for codecs.register for a list of the permitted\nencoding error strings.\n\nnewline controls how universal newlines works (it only applies to text\nmode). It can be None, '', '\\n', '\\r', and '\\r\\n'.  It works as\nfollows:\n\n* On input, if newline is None, universal newlines mode is\n  enabled. Lines in the input can end in '\\n', '\\r', or '\\r\\n', and\n  these are translated into '\\n' before being returned to the\n  caller. If it is '', universal newline mode is enabled, but line\n  endings are returned to the caller untranslated. If it has any of\n  the other legal values, input lines are only terminated by the given\n  string, and the line ending is returned to the caller untranslated.\n\n* On output, if newline is None, any '\\n' characters written are\n  translated to the system default line separator, os.linesep. If\n  newline is '', no translation takes place. If newline is any of the\n  other legal values, any '\\n' characters written are translated to\n  the given string.\n\nIf closefd is False, the underlying file descriptor will be kept open\nwhen the file is closed. This does not work when a file name is given\nand must be True in that case.\n\nopen() returns a file object whose type depends on the mode, and\nthrough which the standard file operations such as reading and writing\nare performed. When open() is used to open a file in a text mode ('w',\n'r', 'wt', 'rt', etc.), it returns a TextIOWrapper. When used to open\na file in a binary mode, the returned class varies: in read binary\nmode, it returns a BufferedReader; in write binary and append binary\nmodes, it returns a BufferedWriter, and in read/write mode, it returns\na BufferedRandom.\n\nIt is also possible to use a string or bytearray as a file for both\nreading and writing. For strings StringIO can be used like a file\nopened in a text mode, and for bytes a BytesIO can be used like a file\nopened in a binary mode.\n";

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"__name__", new PyString("_jyio"));
      dict.__setitem__((String)"__doc__", new PyString("The io module provides the Python interfaces to stream handling. The\nbuiltin open function is defined in this module.\n\nAt the top of the I/O hierarchy is the abstract base class IOBase. It\ndefines the basic interface to a stream. Note, however, that there is no\nseperation between reading and writing to streams; implementations are\nallowed to throw an IOError if they do not support a given operation.\n\nExtending IOBase is RawIOBase which deals simply with the reading and\nwriting of raw bytes to a stream. FileIO subclasses RawIOBase to provide\nan interface to OS files.\n\nBufferedIOBase deals with buffering on a raw byte stream (RawIOBase). Its\nsubclasses, BufferedWriter, BufferedReader, and BufferedRWPair buffer\nstreams that are readable, writable, and both respectively.\nBufferedRandom provides a buffered interface to random access\nstreams. BytesIO is a simple stream of in-memory bytes.\n\nAnother IOBase subclass, TextIOBase, deals with the encoding and decoding\nof streams into text. TextIOWrapper, which extends it, is a buffered text\ninterface to a buffered raw stream (`BufferedIOBase`). Finally, StringIO\nis a in-memory stream for text.\n\nArgument names are not part of the specification, and only the arguments\nof open() are intended to be used as keyword arguments.\n"));
      dict.__setitem__((String)"DEFAULT_BUFFER_SIZE", DEFAULT_BUFFER_SIZE);
      dict.__setitem__((String)"_IOBase", PyIOBase.TYPE);
      dict.__setitem__((String)"_RawIOBase", PyRawIOBase.TYPE);
      dict.__setitem__((String)"FileIO", PyFileIO.TYPE);
      PyObject exceptions = imp.load("exceptions");
      PyObject ValueError = exceptions.__getattr__("ValueError");
      PyObject IOError = exceptions.__getattr__("IOError");
      UnsupportedOperation = makeException(dict, "UnsupportedOperation", ValueError, IOError);
      dict.__setitem__((String)"classDictInit", (PyObject)null);
      dict.__setitem__((String)"makeException", (PyObject)null);
   }

   public static PyException UnsupportedOperation(String message) {
      return new PyException(UnsupportedOperation, message);
   }

   private static PyType makeException(PyObject dict, String excname, PyObject... bases) {
      PyStringMap classDict = new PyStringMap();
      classDict.__setitem__((String)"__module__", Py.newString("_io"));
      PyType type = (PyType)Py.makeClass(excname, (PyObject[])bases, (PyObject)classDict);
      dict.__setitem__((String)excname, type);
      return type;
   }

   public static PyObject open(PyObject[] args, String[] kwds) {
      ArgParser ap = new ArgParser("open", args, kwds, openKwds, 1);
      PyObject file = ap.getPyObject(0);
      String m = ap.getString(1, "r");
      int buffering = ap.getInt(2, -1);
      final String encoding = ap.getString(3, (String)null);
      final String errors = ap.getString(4, (String)null);
      final String newline = ap.getString(5, (String)null);
      boolean closefd = Py.py2boolean(ap.getPyObject(6, Py.True));
      OpenMode mode = new OpenMode(m) {
         public void validate() {
            super.validate();
            this.validate(encoding, errors, newline);
         }
      };
      mode.checkValid();
      PyFileIO raw = new PyFileIO(file, mode, closefd);
      boolean line_buffering = false;
      if (buffering == 0) {
         if (!mode.binary) {
            throw Py.ValueError("can't have unbuffered text I/O");
         } else {
            return raw;
         }
      } else {
         if (buffering == 1) {
            line_buffering = true;
            buffering = -1;
         } else if (buffering < 0 && raw.isatty()) {
            line_buffering = true;
         }

         if (buffering < 0) {
            buffering = 8192;
         }

         if (buffering == 0) {
            return raw;
         } else {
            PyObject bufferType = null;
            PyObject io = imp.load("io");
            if (mode.updating) {
               bufferType = io.__getattr__("BufferedRandom");
            } else if (!mode.writing && !mode.appending) {
               bufferType = io.__getattr__("BufferedReader");
            } else {
               bufferType = io.__getattr__("BufferedWriter");
            }

            PyInteger pyBuffering = new PyInteger(buffering);
            PyObject buffer = bufferType.__call__((PyObject)raw, (PyObject)pyBuffering);
            if (mode.binary) {
               return buffer;
            } else {
               PyObject textType = io.__getattr__("TextIOWrapper");
               PyObject[] textArgs = new PyObject[]{buffer, ap.getPyObject(3, Py.None), ap.getPyObject(4, Py.None), ap.getPyObject(5, Py.None), Py.newBoolean(line_buffering)};
               PyObject wrapper = textType.__call__(textArgs);
               wrapper.__setattr__((String)"mode", new PyString(m));
               return wrapper;
            }
         }
      }
   }
}
