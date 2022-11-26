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
@Filename("mhlib.py")
public class mhlib$py extends PyFunctionTable implements PyRunnable {
   static mhlib$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode MH$2;
   static final PyCode __init__$3;
   static final PyCode __repr__$4;
   static final PyCode error$5;
   static final PyCode getprofile$6;
   static final PyCode getpath$7;
   static final PyCode getcontext$8;
   static final PyCode setcontext$9;
   static final PyCode listfolders$10;
   static final PyCode listsubfolders$11;
   static final PyCode listallfolders$12;
   static final PyCode listallsubfolders$13;
   static final PyCode openfolder$14;
   static final PyCode makefolder$15;
   static final PyCode deletefolder$16;
   static final PyCode isnumeric$17;
   static final PyCode Folder$18;
   static final PyCode __init__$19;
   static final PyCode __repr__$20;
   static final PyCode error$21;
   static final PyCode getfullname$22;
   static final PyCode getsequencesfilename$23;
   static final PyCode getmessagefilename$24;
   static final PyCode listsubfolders$25;
   static final PyCode listallsubfolders$26;
   static final PyCode listmessages$27;
   static final PyCode getsequences$28;
   static final PyCode putsequences$29;
   static final PyCode getcurrent$30;
   static final PyCode setcurrent$31;
   static final PyCode parsesequence$32;
   static final PyCode _parseindex$33;
   static final PyCode openmessage$34;
   static final PyCode removemessages$35;
   static final PyCode refilemessages$36;
   static final PyCode _copysequences$37;
   static final PyCode movemessage$38;
   static final PyCode copymessage$39;
   static final PyCode createmessage$40;
   static final PyCode removefromallsequences$41;
   static final PyCode getlast$42;
   static final PyCode setlast$43;
   static final PyCode Message$44;
   static final PyCode __init__$45;
   static final PyCode __repr__$46;
   static final PyCode getheadertext$47;
   static final PyCode getbodytext$48;
   static final PyCode getbodyparts$49;
   static final PyCode getbody$50;
   static final PyCode SubMessage$51;
   static final PyCode __init__$52;
   static final PyCode __repr__$53;
   static final PyCode getbodytext$54;
   static final PyCode getbodyparts$55;
   static final PyCode getbody$56;
   static final PyCode IntSet$57;
   static final PyCode __init__$58;
   static final PyCode reset$59;
   static final PyCode __cmp__$60;
   static final PyCode __hash__$61;
   static final PyCode __repr__$62;
   static final PyCode normalize$63;
   static final PyCode tostring$64;
   static final PyCode tolist$65;
   static final PyCode fromlist$66;
   static final PyCode clone$67;
   static final PyCode min$68;
   static final PyCode max$69;
   static final PyCode contains$70;
   static final PyCode append$71;
   static final PyCode addpair$72;
   static final PyCode fromstring$73;
   static final PyCode pickline$74;
   static final PyCode updateline$75;
   static final PyCode test$76;
   static final PyCode do$77;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("MH interface -- purely object-oriented (well, almost)\n\nExecutive summary:\n\nimport mhlib\n\nmh = mhlib.MH()         # use default mailbox directory and profile\nmh = mhlib.MH(mailbox)  # override mailbox location (default from profile)\nmh = mhlib.MH(mailbox, profile) # override mailbox and profile\n\nmh.error(format, ...)   # print error message -- can be overridden\ns = mh.getprofile(key)  # profile entry (None if not set)\npath = mh.getpath()     # mailbox pathname\nname = mh.getcontext()  # name of current folder\nmh.setcontext(name)     # set name of current folder\n\nlist = mh.listfolders() # names of top-level folders\nlist = mh.listallfolders() # names of all folders, including subfolders\nlist = mh.listsubfolders(name) # direct subfolders of given folder\nlist = mh.listallsubfolders(name) # all subfolders of given folder\n\nmh.makefolder(name)     # create new folder\nmh.deletefolder(name)   # delete folder -- must have no subfolders\n\nf = mh.openfolder(name) # new open folder object\n\nf.error(format, ...)    # same as mh.error(format, ...)\npath = f.getfullname()  # folder's full pathname\npath = f.getsequencesfilename() # full pathname of folder's sequences file\npath = f.getmessagefilename(n)  # full pathname of message n in folder\n\nlist = f.listmessages() # list of messages in folder (as numbers)\nn = f.getcurrent()      # get current message\nf.setcurrent(n)         # set current message\nlist = f.parsesequence(seq)     # parse msgs syntax into list of messages\nn = f.getlast()         # get last message (0 if no messagse)\nf.setlast(n)            # set last message (internal use only)\n\ndict = f.getsequences() # dictionary of sequences in folder {name: list}\nf.putsequences(dict)    # write sequences back to folder\n\nf.createmessage(n, fp)  # add message from file f as number n\nf.removemessages(list)  # remove messages in list from folder\nf.refilemessages(list, tofolder) # move messages in list to other folder\nf.movemessage(n, tofolder, ton)  # move one message to a given destination\nf.copymessage(n, tofolder, ton)  # copy one message to a given destination\n\nm = f.openmessage(n)    # new open message object (costs a file descriptor)\nm is a derived class of mimetools.Message(rfc822.Message), with:\ns = m.getheadertext()   # text of message's headers\ns = m.getheadertext(pred) # text of message's headers, filtered by pred\ns = m.getbodytext()     # text of message's body, decoded\ns = m.getbodytext(0)    # text of message's body, not decoded\n"));
      var1.setline(54);
      PyString.fromInterned("MH interface -- purely object-oriented (well, almost)\n\nExecutive summary:\n\nimport mhlib\n\nmh = mhlib.MH()         # use default mailbox directory and profile\nmh = mhlib.MH(mailbox)  # override mailbox location (default from profile)\nmh = mhlib.MH(mailbox, profile) # override mailbox and profile\n\nmh.error(format, ...)   # print error message -- can be overridden\ns = mh.getprofile(key)  # profile entry (None if not set)\npath = mh.getpath()     # mailbox pathname\nname = mh.getcontext()  # name of current folder\nmh.setcontext(name)     # set name of current folder\n\nlist = mh.listfolders() # names of top-level folders\nlist = mh.listallfolders() # names of all folders, including subfolders\nlist = mh.listsubfolders(name) # direct subfolders of given folder\nlist = mh.listallsubfolders(name) # all subfolders of given folder\n\nmh.makefolder(name)     # create new folder\nmh.deletefolder(name)   # delete folder -- must have no subfolders\n\nf = mh.openfolder(name) # new open folder object\n\nf.error(format, ...)    # same as mh.error(format, ...)\npath = f.getfullname()  # folder's full pathname\npath = f.getsequencesfilename() # full pathname of folder's sequences file\npath = f.getmessagefilename(n)  # full pathname of message n in folder\n\nlist = f.listmessages() # list of messages in folder (as numbers)\nn = f.getcurrent()      # get current message\nf.setcurrent(n)         # set current message\nlist = f.parsesequence(seq)     # parse msgs syntax into list of messages\nn = f.getlast()         # get last message (0 if no messagse)\nf.setlast(n)            # set last message (internal use only)\n\ndict = f.getsequences() # dictionary of sequences in folder {name: list}\nf.putsequences(dict)    # write sequences back to folder\n\nf.createmessage(n, fp)  # add message from file f as number n\nf.removemessages(list)  # remove messages in list from folder\nf.refilemessages(list, tofolder) # move messages in list to other folder\nf.movemessage(n, tofolder, ton)  # move one message to a given destination\nf.copymessage(n, tofolder, ton)  # copy one message to a given destination\n\nm = f.openmessage(n)    # new open message object (costs a file descriptor)\nm is a derived class of mimetools.Message(rfc822.Message), with:\ns = m.getheadertext()   # text of message's headers\ns = m.getheadertext(pred) # text of message's headers, filtered by pred\ns = m.getbodytext()     # text of message's body, decoded\ns = m.getbodytext(0)    # text of message's body, not decoded\n");
      var1.setline(55);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(56);
      PyObject var10000 = var1.getname("warnpy3k");
      var5 = new PyObject[]{PyString.fromInterned("the mhlib module has been removed in Python 3.0; use the mailbox module instead"), Py.newInteger(2)};
      String[] var7 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(58);
      var1.dellocal("warnpy3k");
      var1.setline(71);
      PyString var6 = PyString.fromInterned("~/.mh_profile");
      var1.setlocal("MH_PROFILE", var6);
      var3 = null;
      var1.setline(72);
      var6 = PyString.fromInterned("~/Mail");
      var1.setlocal("PATH", var6);
      var3 = null;
      var1.setline(73);
      var6 = PyString.fromInterned(".mh_sequences");
      var1.setlocal("MH_SEQUENCES", var6);
      var3 = null;
      var1.setline(74);
      PyInteger var8 = Py.newInteger(448);
      var1.setlocal("FOLDER_PROTECT", var8);
      var3 = null;
      var1.setline(79);
      PyObject var9 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var9);
      var3 = null;
      var1.setline(80);
      var9 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var9);
      var3 = null;
      var1.setline(81);
      var9 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var9);
      var3 = null;
      var1.setline(82);
      var9 = imp.importOne("mimetools", var1, -1);
      var1.setlocal("mimetools", var9);
      var3 = null;
      var1.setline(83);
      var9 = imp.importOne("multifile", var1, -1);
      var1.setlocal("multifile", var9);
      var3 = null;
      var1.setline(84);
      var9 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var9);
      var3 = null;
      var1.setline(85);
      var3 = new String[]{"bisect"};
      var5 = imp.importFrom("bisect", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("bisect", var4);
      var4 = null;
      var1.setline(87);
      PyList var10 = new PyList(new PyObject[]{PyString.fromInterned("MH"), PyString.fromInterned("Error"), PyString.fromInterned("Folder"), PyString.fromInterned("Message")});
      var1.setlocal("__all__", var10);
      var3 = null;
      var1.setline(91);
      var5 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Error", var5, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(95);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("MH", var5, MH$2);
      var1.setlocal("MH", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(238);
      var9 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[1-9][0-9]*$"));
      var1.setlocal("numericprog", var9);
      var3 = null;
      var1.setline(239);
      var5 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var5, isnumeric$17, (PyObject)null);
      var1.setlocal("isnumeric", var11);
      var3 = null;
      var1.setline(242);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("Folder", var5, Folder$18);
      var1.setlocal("Folder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(663);
      var5 = new PyObject[]{var1.getname("mimetools").__getattr__("Message")};
      var4 = Py.makeClass("Message", var5, Message$44);
      var1.setlocal("Message", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(740);
      var5 = new PyObject[]{var1.getname("Message")};
      var4 = Py.makeClass("SubMessage", var5, SubMessage$51);
      var1.setlocal("SubMessage", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(771);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("IntSet", var5, IntSet$57);
      var1.setlocal("IntSet", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(908);
      var5 = new PyObject[]{Py.newInteger(1)};
      var11 = new PyFunction(var1.f_globals, var5, pickline$74, (PyObject)null);
      var1.setlocal("pickline", var11);
      var3 = null;
      var1.setline(928);
      var5 = new PyObject[]{Py.newInteger(1)};
      var11 = new PyFunction(var1.f_globals, var5, updateline$75, (PyObject)null);
      var1.setlocal("updateline", var11);
      var3 = null;
      var1.setline(962);
      var5 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var5, test$76, (PyObject)null);
      var1.setlocal("test", var11);
      var3 = null;
      var1.setline(1004);
      var9 = var1.getname("__name__");
      var10000 = var9._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1005);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(92);
      return var1.getf_locals();
   }

   public PyObject MH$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class representing a particular collection of folders.\n    Optional constructor arguments are the pathname for the directory\n    containing the collection, and the MH profile to use.\n    If either is omitted or empty a default is used; the default\n    directory is taken from the MH profile if it is specified there."));
      var1.setline(100);
      PyString.fromInterned("Class representing a particular collection of folders.\n    Optional constructor arguments are the pathname for the directory\n    containing the collection, and the MH profile to use.\n    If either is omitted or empty a default is used; the default\n    directory is taken from the MH profile if it is specified there.");
      var1.setline(102);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("Constructor."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$4, PyString.fromInterned("String representation."));
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(118);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$5, PyString.fromInterned("Routine to print an error.  May be overridden by a derived class."));
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getprofile$6, PyString.fromInterned("Return a profile entry, None if not found."));
      var1.setlocal("getprofile", var4);
      var3 = null;
      var1.setline(126);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getpath$7, PyString.fromInterned("Return the path (the name of the collection's directory)."));
      var1.setlocal("getpath", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcontext$8, PyString.fromInterned("Return the name of the current folder."));
      var1.setlocal("getcontext", var4);
      var3 = null;
      var1.setline(137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setcontext$9, PyString.fromInterned("Set the name of the current folder."));
      var1.setlocal("setcontext", var4);
      var3 = null;
      var1.setline(144);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listfolders$10, PyString.fromInterned("Return the names of the top-level folders."));
      var1.setlocal("listfolders", var4);
      var3 = null;
      var1.setline(155);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listsubfolders$11, PyString.fromInterned("Return the names of the subfolders in a given folder\n        (prefixed with the given folder name)."));
      var1.setlocal("listsubfolders", var4);
      var3 = null;
      var1.setline(179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listallfolders$12, PyString.fromInterned("Return the names of all folders and subfolders, recursively."));
      var1.setlocal("listallfolders", var4);
      var3 = null;
      var1.setline(183);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listallsubfolders$13, PyString.fromInterned("Return the names of subfolders in a given folder, recursively."));
      var1.setlocal("listallsubfolders", var4);
      var3 = null;
      var1.setline(211);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, openfolder$14, PyString.fromInterned("Return a new Folder object for the named folder."));
      var1.setlocal("openfolder", var4);
      var3 = null;
      var1.setline(215);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, makefolder$15, PyString.fromInterned("Create a new folder (or raise os.error if it cannot be created)."));
      var1.setlocal("makefolder", var4);
      var3 = null;
      var1.setline(224);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, deletefolder$16, PyString.fromInterned("Delete a folder.  This removes files in the folder but not\n        subdirectories.  Raise os.error if deleting the folder itself fails."));
      var1.setlocal("deletefolder", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyString.fromInterned("Constructor.");
      var1.setline(104);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(104);
         var3 = var1.getglobal("MH_PROFILE");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(105);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("profile", var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(106);
         var3 = var1.getlocal(0).__getattr__("getprofile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Path"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(107);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(107);
         var3 = var1.getglobal("PATH");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(108);
      var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(1)).__not__();
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var10000 = var3._ne(PyString.fromInterned("~"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(109);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("~"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(110);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(111);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(111);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("MH() path not found"));
      } else {
         var1.setline(112);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("path", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __repr__$4(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyString.fromInterned("String representation.");
      var1.setline(116);
      PyObject var3 = PyString.fromInterned("MH(%r, %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("path"), var1.getlocal(0).__getattr__("profile")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject error$5(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyString.fromInterned("Routine to print an error.  May be overridden by a derived class.");
      var1.setline(120);
      var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("MH error: %s\n")._mod(var1.getlocal(1)._mod(var1.getlocal(2))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getprofile$6(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("Return a profile entry, None if not found.");
      var1.setline(124);
      PyObject var3 = var1.getglobal("pickline").__call__(var2, var1.getlocal(0).__getattr__("profile"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getpath$7(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyString.fromInterned("Return the path (the name of the collection's directory).");
      var1.setline(128);
      PyObject var3 = var1.getlocal(0).__getattr__("path");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getcontext$8(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyString.fromInterned("Return the name of the current folder.");
      var1.setline(132);
      PyObject var3 = var1.getglobal("pickline").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("getpath").__call__(var2), (PyObject)PyString.fromInterned("context")), (PyObject)PyString.fromInterned("Current-Folder"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(134);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(134);
         PyString var4 = PyString.fromInterned("inbox");
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(135);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setcontext$9(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyString.fromInterned("Set the name of the current folder.");
      var1.setline(139);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("getpath").__call__(var2), (PyObject)PyString.fromInterned("context"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(140);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(141);
      var1.getlocal(3).__getattr__("write").__call__(var2, PyString.fromInterned("Current-Folder: %s\n")._mod(var1.getlocal(1)));
      var1.setline(142);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject listfolders$10(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      PyString.fromInterned("Return the names of the top-level folders.");
      var1.setline(146);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(147);
      PyObject var6 = var1.getlocal(0).__getattr__("getpath").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(148);
      var6 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(148);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(152);
            var1.getlocal(1).__getattr__("sort").__call__(var2);
            var1.setline(153);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(149);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(150);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(4)).__nonzero__()) {
            var1.setline(151);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject listsubfolders$11(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyString.fromInterned("Return the names of the subfolders in a given folder\n        (prefixed with the given folder name).");
      var1.setline(158);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("path"), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(161);
      var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(2)).__getattr__("st_nlink");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(162);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._le(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(163);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(164);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(165);
         PyObject var7 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2));
         var1.setlocal(5, var7);
         var4 = null;
         var1.setline(166);
         var7 = var1.getlocal(5).__iter__();

         while(true) {
            var1.setline(166);
            PyObject var5 = var7.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(167);
            PyObject var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(6));
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(168);
            if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(7)).__nonzero__()) {
               var1.setline(169);
               var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(6));
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(170);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
               var1.setline(173);
               var6 = var1.getlocal(3)._sub(Py.newInteger(1));
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(174);
               var6 = var1.getlocal(3);
               var10000 = var6._le(Py.newInteger(2));
               var6 = null;
               if (var10000.__nonzero__()) {
                  break;
               }
            }
         }

         var1.setline(176);
         var1.getlocal(4).__getattr__("sort").__call__(var2);
         var1.setline(177);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject listallfolders$12(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyString.fromInterned("Return the names of all folders and subfolders, recursively.");
      var1.setline(181);
      PyObject var3 = var1.getlocal(0).__getattr__("listallsubfolders").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject listallsubfolders$13(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyString.fromInterned("Return the names of subfolders in a given folder, recursively.");
      var1.setline(185);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("path"), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(188);
      var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(2)).__getattr__("st_nlink");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(189);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._le(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(190);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(191);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(192);
         PyObject var7 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2));
         var1.setlocal(5, var7);
         var4 = null;
         var1.setline(193);
         var7 = var1.getlocal(5).__iter__();

         while(true) {
            var1.setline(193);
            PyObject var5 = var7.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(194);
            PyObject var6 = var1.getlocal(6).__getitem__(Py.newInteger(0));
            var10000 = var6._eq(PyString.fromInterned(","));
            var6 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("isnumeric").__call__(var2, var1.getlocal(6));
            }

            if (!var10000.__nonzero__()) {
               var1.setline(195);
               var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(6));
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(196);
               if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(7)).__nonzero__()) {
                  var1.setline(197);
                  var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(6));
                  var1.setlocal(8, var6);
                  var6 = null;
                  var1.setline(198);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(8));
                  var1.setline(199);
                  if (var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(7)).__not__().__nonzero__()) {
                     var1.setline(200);
                     var6 = var1.getlocal(0).__getattr__("listallsubfolders").__call__(var2, var1.getlocal(8));
                     var1.setlocal(9, var6);
                     var6 = null;
                     var1.setline(202);
                     var6 = var1.getlocal(4)._add(var1.getlocal(9));
                     var1.setlocal(4, var6);
                     var6 = null;
                  }

                  var1.setline(205);
                  var6 = var1.getlocal(3)._sub(Py.newInteger(1));
                  var1.setlocal(3, var6);
                  var6 = null;
                  var1.setline(206);
                  var6 = var1.getlocal(3);
                  var10000 = var6._le(Py.newInteger(2));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     break;
                  }
               }
            }
         }

         var1.setline(208);
         var1.getlocal(4).__getattr__("sort").__call__(var2);
         var1.setline(209);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject openfolder$14(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyString.fromInterned("Return a new Folder object for the named folder.");
      var1.setline(213);
      PyObject var3 = var1.getglobal("Folder").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makefolder$15(PyFrame var1, ThreadState var2) {
      var1.setline(216);
      PyString.fromInterned("Create a new folder (or raise os.error if it cannot be created).");
      var1.setline(217);
      PyObject var3 = var1.getglobal("pickline").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("profile"), (PyObject)PyString.fromInterned("Folder-Protect"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(218);
      PyObject var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isnumeric").__call__(var2, var1.getlocal(2));
      }

      if (var10000.__nonzero__()) {
         var1.setline(219);
         var3 = var1.getglobal("int").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(8));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(221);
         var3 = var1.getglobal("FOLDER_PROTECT");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(222);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("getpath").__call__(var2), var1.getlocal(1)), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject deletefolder$16(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyString.fromInterned("Delete a folder.  This removes files in the folder but not\n        subdirectories.  Raise os.error if deleting the folder itself fails.");
      var1.setline(227);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("getpath").__call__(var2), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(228);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(228);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(235);
            var1.getglobal("os").__getattr__("rmdir").__call__(var2, var1.getlocal(2));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(229);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;

         try {
            var1.setline(231);
            var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(4));
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getglobal("os").__getattr__("error"))) {
               throw var7;
            }

            var1.setline(233);
            var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("%s not deleted, continuing...")._mod(var1.getlocal(4)));
         }
      }
   }

   public PyObject isnumeric$17(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyObject var3 = var1.getglobal("numericprog").__getattr__("match").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Folder$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class representing a particular folder."));
      var1.setline(243);
      PyString.fromInterned("Class representing a particular folder.");
      var1.setline(245);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$19, PyString.fromInterned("Constructor."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(252);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$20, PyString.fromInterned("String representation."));
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(256);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$21, PyString.fromInterned("Error message handler."));
      var1.setlocal("error", var4);
      var3 = null;
      var1.setline(260);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getfullname$22, PyString.fromInterned("Return the full pathname of the folder."));
      var1.setlocal("getfullname", var4);
      var3 = null;
      var1.setline(264);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getsequencesfilename$23, PyString.fromInterned("Return the full pathname of the folder's sequences file."));
      var1.setlocal("getsequencesfilename", var4);
      var3 = null;
      var1.setline(268);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getmessagefilename$24, PyString.fromInterned("Return the full pathname of a message in the folder."));
      var1.setlocal("getmessagefilename", var4);
      var3 = null;
      var1.setline(272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listsubfolders$25, PyString.fromInterned("Return list of direct subfolders."));
      var1.setlocal("listsubfolders", var4);
      var3 = null;
      var1.setline(276);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listallsubfolders$26, PyString.fromInterned("Return list of all subfolders."));
      var1.setlocal("listallsubfolders", var4);
      var3 = null;
      var1.setline(280);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, listmessages$27, PyString.fromInterned("Return the list of messages currently present in the folder.\n        As a side effect, set self.last to the last message (or 0)."));
      var1.setlocal("listmessages", var4);
      var3 = null;
      var1.setline(297);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getsequences$28, PyString.fromInterned("Return the set of sequences for the folder."));
      var1.setlocal("getsequences", var4);
      var3 = null;
      var1.setline(317);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, putsequences$29, PyString.fromInterned("Write the set of sequences back to the folder."));
      var1.setlocal("putsequences", var4);
      var3 = null;
      var1.setline(334);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getcurrent$30, PyString.fromInterned("Return the current message.  Raise Error when there is none."));
      var1.setlocal("getcurrent", var4);
      var3 = null;
      var1.setline(342);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setcurrent$31, PyString.fromInterned("Set the current message."));
      var1.setlocal("setcurrent", var4);
      var3 = null;
      var1.setline(346);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parsesequence$32, PyString.fromInterned("Parse an MH sequence specification into a message list.\n        Attempt to mimic mh-sequence(5) as close as possible.\n        Also attempt to mimic observed behavior regarding which\n        conditions cause which error messages."));
      var1.setlocal("parsesequence", var4);
      var3 = null;
      var1.setline(430);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parseindex$33, PyString.fromInterned("Internal: parse a message number (or cur, first, etc.)."));
      var1.setlocal("_parseindex", var4);
      var3 = null;
      var1.setline(461);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, openmessage$34, PyString.fromInterned("Open a message -- returns a Message object."));
      var1.setlocal("openmessage", var4);
      var3 = null;
      var1.setline(465);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, removemessages$35, PyString.fromInterned("Remove one or more messages -- may raise os.error."));
      var1.setlocal("removemessages", var4);
      var3 = null;
      var1.setline(490);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, refilemessages$36, PyString.fromInterned("Refile one or more messages -- may raise os.error.\n        'tofolder' is an open folder object."));
      var1.setlocal("refilemessages", var4);
      var3 = null;
      var1.setline(525);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _copysequences$37, PyString.fromInterned("Helper for refilemessages() to copy sequences."));
      var1.setlocal("_copysequences", var4);
      var3 = null;
      var1.setline(546);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, movemessage$38, PyString.fromInterned("Move one message over a specific destination message,\n        which may or may not already exist."));
      var1.setlocal("movemessage", var4);
      var3 = null;
      var1.setline(578);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copymessage$39, PyString.fromInterned("Copy one message over a specific destination message,\n        which may or may not already exist."));
      var1.setlocal("copymessage", var4);
      var3 = null;
      var1.setline(604);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, createmessage$40, PyString.fromInterned("Create a message, with text from the open file txt."));
      var1.setlocal("createmessage", var4);
      var3 = null;
      var1.setline(630);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, removefromallsequences$41, PyString.fromInterned("Remove one or more messages from all sequences (including last)\n        -- but not from 'cur'!!!"));
      var1.setlocal("removefromallsequences", var4);
      var3 = null;
      var1.setline(649);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getlast$42, PyString.fromInterned("Return the last message number."));
      var1.setlocal("getlast", var4);
      var3 = null;
      var1.setline(655);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setlast$43, PyString.fromInterned("Set the last message number."));
      var1.setlocal("setlast", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(246);
      PyString.fromInterned("Constructor.");
      var1.setline(247);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("mh", var3);
      var3 = null;
      var1.setline(248);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(249);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0).__getattr__("getfullname").__call__(var2)).__not__().__nonzero__()) {
         var1.setline(250);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("no folder %s")._mod(var1.getlocal(2)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __repr__$20(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyString.fromInterned("String representation.");
      var1.setline(254);
      PyObject var3 = PyString.fromInterned("Folder(%r, %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("mh"), var1.getlocal(0).__getattr__("name")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject error$21(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      PyString.fromInterned("Error message handler.");
      var1.setline(258);
      PyObject var10000 = var1.getlocal(0).__getattr__("mh").__getattr__("error");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getfullname$22(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyString.fromInterned("Return the full pathname of the folder.");
      var1.setline(262);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("mh").__getattr__("path"), var1.getlocal(0).__getattr__("name"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getsequencesfilename$23(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyString.fromInterned("Return the full pathname of the folder's sequences file.");
      var1.setline(266);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("getfullname").__call__(var2), var1.getglobal("MH_SEQUENCES"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getmessagefilename$24(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyString.fromInterned("Return the full pathname of a message in the folder.");
      var1.setline(270);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("getfullname").__call__(var2), var1.getglobal("str").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject listsubfolders$25(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyString.fromInterned("Return list of direct subfolders.");
      var1.setline(274);
      PyObject var3 = var1.getlocal(0).__getattr__("mh").__getattr__("listsubfolders").__call__(var2, var1.getlocal(0).__getattr__("name"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject listallsubfolders$26(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyString.fromInterned("Return list of all subfolders.");
      var1.setline(278);
      PyObject var3 = var1.getlocal(0).__getattr__("mh").__getattr__("listallsubfolders").__call__(var2, var1.getlocal(0).__getattr__("name"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject listmessages$27(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyString.fromInterned("Return the list of messages currently present in the folder.\n        As a side effect, set self.last to the last message (or 0).");
      var1.setline(283);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(284);
      PyObject var5 = var1.getglobal("numericprog").__getattr__("match");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(285);
      var5 = var1.getlocal(1).__getattr__("append");
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(286);
      var5 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0).__getattr__("getfullname").__call__(var2)).__iter__();

      while(true) {
         var1.setline(286);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(289);
            var5 = var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(1));
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(290);
            var1.getlocal(1).__getattr__("sort").__call__(var2);
            var1.setline(291);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(292);
               var5 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
               var1.getlocal(0).__setattr__("last", var5);
               var3 = null;
            } else {
               var1.setline(294);
               PyInteger var6 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"last", var6);
               var3 = null;
            }

            var1.setline(295);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(287);
         if (var1.getlocal(2).__call__(var2, var1.getlocal(4)).__nonzero__()) {
            var1.setline(288);
            var1.getlocal(3).__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject getsequences$28(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyString.fromInterned("Return the set of sequences for the folder.");
      var1.setline(299);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(300);
      PyObject var6 = var1.getlocal(0).__getattr__("getsequencesfilename").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(302);
         var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("r"));
         var1.setlocal(3, var6);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (var7.match(var1.getglobal("IOError"))) {
            var1.setline(304);
            var4 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var4;
         }

         throw var7;
      }

      while(true) {
         var1.setline(305);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(306);
         var6 = var1.getlocal(3).__getattr__("readline").__call__(var2);
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(307);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(308);
         var6 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(309);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
         PyObject var10000 = var6._ne(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(310);
            var1.getlocal(0).__getattr__("error").__call__(var2, PyString.fromInterned("bad sequence in %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4).__getattr__("strip").__call__(var2)})));
         }

         var1.setline(312);
         var6 = var1.getlocal(5).__getitem__(Py.newInteger(0)).__getattr__("strip").__call__(var2);
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(313);
         var6 = var1.getglobal("IntSet").__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getitem__(Py.newInteger(1)).__getattr__("strip").__call__(var2), (PyObject)PyString.fromInterned(" ")).__getattr__("tolist").__call__(var2);
         var1.setlocal(7, var6);
         var3 = null;
         var1.setline(314);
         var6 = var1.getlocal(7);
         var1.getlocal(1).__setitem__(var1.getlocal(6), var6);
         var3 = null;
      }

      var1.setline(315);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject putsequences$29(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyString.fromInterned("Write the set of sequences back to the folder.");
      var1.setline(319);
      PyObject var3 = var1.getlocal(0).__getattr__("getsequencesfilename").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(320);
      var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(321);
      var3 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(321);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(326);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               try {
                  var1.setline(328);
                  var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(2));
               } catch (Throwable var7) {
                  PyException var8 = Py.setException(var7, var1);
                  if (!var8.match(var1.getglobal("os").__getattr__("error"))) {
                     throw var8;
                  }

                  var1.setline(330);
               }
            } else {
               var1.setline(332);
               var1.getlocal(3).__getattr__("close").__call__(var2);
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(322);
         PyObject var9 = var1.getglobal("IntSet").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)PyString.fromInterned(" "));
         var1.setlocal(6, var9);
         var5 = null;
         var1.setline(323);
         var1.getlocal(6).__getattr__("fromlist").__call__(var2, var1.getlocal(5));
         var1.setline(324);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(324);
            var9 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("w"));
            var1.setlocal(3, var9);
            var5 = null;
         }

         var1.setline(325);
         var1.getlocal(3).__getattr__("write").__call__(var2, PyString.fromInterned("%s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(6).__getattr__("tostring").__call__(var2)})));
      }
   }

   public PyObject getcurrent$30(PyFrame var1, ThreadState var2) {
      var1.setline(335);
      PyString.fromInterned("Return the current message.  Raise Error when there is none.");
      var1.setline(336);
      PyObject var3 = var1.getlocal(0).__getattr__("getsequences").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(338);
         var3 = var1.getglobal("max").__call__(var2, var1.getlocal(1).__getitem__(PyString.fromInterned("cur")));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("KeyError")}))) {
            var1.setline(340);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("no cur message"));
         } else {
            throw var4;
         }
      }
   }

   public PyObject setcurrent$31(PyFrame var1, ThreadState var2) {
      var1.setline(343);
      PyString.fromInterned("Set the current message.");
      var1.setline(344);
      var1.getglobal("updateline").__call__(var2, var1.getlocal(0).__getattr__("getsequencesfilename").__call__(var2), PyString.fromInterned("cur"), var1.getglobal("str").__call__(var2, var1.getlocal(1)), Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parsesequence$32(PyFrame var1, ThreadState var2) {
      var1.setline(350);
      PyString.fromInterned("Parse an MH sequence specification into a message list.\n        Attempt to mimic mh-sequence(5) as close as possible.\n        Also attempt to mimic observed behavior regarding which\n        conditions cause which error messages.");
      var1.setline(355);
      PyObject var3 = var1.getlocal(0).__getattr__("listmessages").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(357);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(358);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("no messages in %s")._mod(var1.getlocal(0).__getattr__("name")));
      } else {
         var1.setline(360);
         var3 = var1.getlocal(1);
         PyObject var10000 = var3._eq(PyString.fromInterned("all"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(361);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(363);
            PyObject var4 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(364);
            var4 = var1.getlocal(3);
            var10000 = var4._ge(Py.newInteger(0));
            var4 = null;
            PyObject var5;
            PyException var11;
            if (var10000.__nonzero__()) {
               var1.setline(365);
               PyTuple var13 = new PyTuple(new PyObject[]{var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null), PyString.fromInterned(""), var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null)});
               PyObject[] var12 = Py.unpackSequence(var13, 3);
               PyObject var6 = var12[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var12[1];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var12[2];
               var1.setlocal(6, var6);
               var6 = null;
               var4 = null;
               var1.setline(366);
               var4 = var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
               var10000 = var4._in(PyString.fromInterned("-+"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(367);
                  var13 = new PyTuple(new PyObject[]{var1.getlocal(6).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null), var1.getlocal(6).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)});
                  var12 = Py.unpackSequence(var13, 2);
                  var6 = var12[0];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var6 = var12[1];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var4 = null;
               }

               var1.setline(368);
               if (var1.getglobal("isnumeric").__call__(var2, var1.getlocal(6)).__not__().__nonzero__()) {
                  var1.setline(369);
                  throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("bad message list %s")._mod(var1.getlocal(1)));
               } else {
                  try {
                     var1.setline(371);
                     var4 = var1.getglobal("int").__call__(var2, var1.getlocal(6));
                     var1.setlocal(7, var4);
                     var4 = null;
                  } catch (Throwable var9) {
                     var11 = Py.setException(var9, var1);
                     if (!var11.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("OverflowError")}))) {
                        throw var11;
                     }

                     var1.setline(374);
                     var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
                     var1.setlocal(7, var5);
                     var5 = null;
                  }

                  try {
                     var1.setline(376);
                     var4 = var1.getlocal(0).__getattr__("_parseindex").__call__(var2, var1.getlocal(4), var1.getlocal(2));
                     var1.setlocal(8, var4);
                     var4 = null;
                  } catch (Throwable var8) {
                     var11 = Py.setException(var8, var1);
                     if (var11.match(var1.getglobal("Error"))) {
                        var5 = var11.value;
                        var1.setlocal(9, var5);
                        var5 = null;
                        var1.setline(378);
                        var5 = var1.getlocal(0).__getattr__("getsequences").__call__(var2);
                        var1.setlocal(10, var5);
                        var5 = null;
                        var1.setline(379);
                        var5 = var1.getlocal(4);
                        var10000 = var5._in(var1.getlocal(10));
                        var5 = null;
                        if (var10000.__not__().__nonzero__()) {
                           var1.setline(380);
                           if (var1.getlocal(9).__not__().__nonzero__()) {
                              var1.setline(381);
                              var5 = PyString.fromInterned("bad message list %s")._mod(var1.getlocal(1));
                              var1.setlocal(9, var5);
                              var5 = null;
                           }

                           var1.setline(382);
                           throw Py.makeException(var1.getglobal("Error"), var1.getlocal(9), var1.getglobal("sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(2)));
                        }

                        var1.setline(383);
                        var5 = var1.getlocal(10).__getitem__(var1.getlocal(4));
                        var1.setlocal(11, var5);
                        var5 = null;
                        var1.setline(384);
                        if (var1.getlocal(11).__not__().__nonzero__()) {
                           var1.setline(385);
                           throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("sequence %s empty")._mod(var1.getlocal(4)));
                        }

                        var1.setline(386);
                        var5 = var1.getlocal(5);
                        var10000 = var5._eq(PyString.fromInterned("-"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(387);
                           var3 = var1.getlocal(11).__getslice__(var1.getlocal(7).__neg__(), (PyObject)null, (PyObject)null);
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setline(389);
                        var3 = var1.getlocal(11).__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     throw var11;
                  }

                  var1.setline(391);
                  if (var1.getlocal(5).__not__().__nonzero__()) {
                     var1.setline(392);
                     var5 = var1.getlocal(4);
                     var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("prev"), PyString.fromInterned("last")}));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(393);
                        PyString var14 = PyString.fromInterned("-");
                        var1.setlocal(5, var14);
                        var5 = null;
                     }
                  }

                  var1.setline(394);
                  var5 = var1.getlocal(5);
                  var10000 = var5._eq(PyString.fromInterned("-"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(395);
                     var5 = var1.getglobal("bisect").__call__(var2, var1.getlocal(2), var1.getlocal(8));
                     var1.setlocal(3, var5);
                     var5 = null;
                     var1.setline(396);
                     var3 = var1.getlocal(2).__getslice__(var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(3)._sub(var1.getlocal(7))), var1.getlocal(3), (PyObject)null);
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(398);
                     var5 = var1.getglobal("bisect").__call__(var2, var1.getlocal(2), var1.getlocal(8)._sub(Py.newInteger(1)));
                     var1.setlocal(3, var5);
                     var5 = null;
                     var1.setline(399);
                     var3 = var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(3)._add(var1.getlocal(7)), (PyObject)null);
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            } else {
               var1.setline(401);
               var4 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(402);
               var4 = var1.getlocal(3);
               var10000 = var4._ge(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(403);
                  var4 = var1.getlocal(0).__getattr__("_parseindex").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null), var1.getlocal(2));
                  var1.setlocal(12, var4);
                  var4 = null;
                  var1.setline(404);
                  var4 = var1.getlocal(0).__getattr__("_parseindex").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null), var1.getlocal(2));
                  var1.setlocal(13, var4);
                  var4 = null;
                  var1.setline(405);
                  var4 = var1.getglobal("bisect").__call__(var2, var1.getlocal(2), var1.getlocal(12)._sub(Py.newInteger(1)));
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(406);
                  var4 = var1.getglobal("bisect").__call__(var2, var1.getlocal(2), var1.getlocal(13));
                  var1.setlocal(14, var4);
                  var4 = null;
                  var1.setline(407);
                  var4 = var1.getlocal(2).__getslice__(var1.getlocal(3), var1.getlocal(14), (PyObject)null);
                  var1.setlocal(15, var4);
                  var4 = null;
                  var1.setline(408);
                  if (var1.getlocal(15).__not__().__nonzero__()) {
                     var1.setline(409);
                     throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("bad message list %s")._mod(var1.getlocal(1)));
                  } else {
                     var1.setline(410);
                     var3 = var1.getlocal(15);
                     var1.f_lasti = -1;
                     return var3;
                  }
               } else {
                  try {
                     var1.setline(413);
                     var4 = var1.getlocal(0).__getattr__("_parseindex").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                     var1.setlocal(16, var4);
                     var4 = null;
                  } catch (Throwable var7) {
                     var11 = Py.setException(var7, var1);
                     if (var11.match(var1.getglobal("Error"))) {
                        var5 = var11.value;
                        var1.setlocal(9, var5);
                        var5 = null;
                        var1.setline(415);
                        var5 = var1.getlocal(0).__getattr__("getsequences").__call__(var2);
                        var1.setlocal(10, var5);
                        var5 = null;
                        var1.setline(416);
                        var5 = var1.getlocal(1);
                        var10000 = var5._in(var1.getlocal(10));
                        var5 = null;
                        if (var10000.__not__().__nonzero__()) {
                           var1.setline(417);
                           if (var1.getlocal(9).__not__().__nonzero__()) {
                              var1.setline(418);
                              var5 = PyString.fromInterned("bad message list %s")._mod(var1.getlocal(1));
                              var1.setlocal(9, var5);
                              var5 = null;
                           }

                           var1.setline(419);
                           throw Py.makeException(var1.getglobal("Error"), var1.getlocal(9));
                        }

                        var1.setline(420);
                        var3 = var1.getlocal(10).__getitem__(var1.getlocal(1));
                        var1.f_lasti = -1;
                        return var3;
                     }

                     throw var11;
                  }

                  var1.setline(422);
                  var5 = var1.getlocal(16);
                  var10000 = var5._notin(var1.getlocal(2));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(423);
                     if (var1.getglobal("isnumeric").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                        var1.setline(424);
                        throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("message %d doesn't exist")._mod(var1.getlocal(16)));
                     } else {
                        var1.setline(426);
                        throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("no %s message")._mod(var1.getlocal(1)));
                     }
                  } else {
                     var1.setline(428);
                     PyList var10 = new PyList(new PyObject[]{var1.getlocal(16)});
                     var1.f_lasti = -1;
                     return var10;
                  }
               }
            }
         }
      }
   }

   public PyObject _parseindex$33(PyFrame var1, ThreadState var2) {
      var1.setline(431);
      PyString.fromInterned("Internal: parse a message number (or cur, first, etc.).");
      var1.setline(432);
      PyObject var3;
      PyException var8;
      if (var1.getglobal("isnumeric").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         try {
            var1.setline(434);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var6) {
            var8 = Py.setException(var6, var1);
            if (var8.match(new PyTuple(new PyObject[]{var1.getglobal("OverflowError"), var1.getglobal("ValueError")}))) {
               var1.setline(436);
               var3 = var1.getglobal("sys").__getattr__("maxint");
               var1.f_lasti = -1;
               return var3;
            } else {
               throw var8;
            }
         }
      } else {
         var1.setline(437);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("cur"), PyString.fromInterned(".")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(438);
            var3 = var1.getlocal(0).__getattr__("getcurrent").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(439);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(PyString.fromInterned("first"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(440);
               var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(441);
               var4 = var1.getlocal(1);
               var10000 = var4._eq(PyString.fromInterned("last"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(442);
                  var3 = var1.getlocal(2).__getitem__(Py.newInteger(-1));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(443);
                  var4 = var1.getlocal(1);
                  var10000 = var4._eq(PyString.fromInterned("next"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(444);
                     var4 = var1.getlocal(0).__getattr__("getcurrent").__call__(var2);
                     var1.setlocal(3, var4);
                     var4 = null;
                     var1.setline(445);
                     var4 = var1.getglobal("bisect").__call__(var2, var1.getlocal(2), var1.getlocal(3));
                     var1.setlocal(4, var4);
                     var4 = null;

                     try {
                        var1.setline(447);
                        var3 = var1.getlocal(2).__getitem__(var1.getlocal(4));
                        var1.f_lasti = -1;
                        return var3;
                     } catch (Throwable var7) {
                        var8 = Py.setException(var7, var1);
                        if (var8.match(var1.getglobal("IndexError"))) {
                           var1.setline(449);
                           throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("no next message"));
                        } else {
                           throw var8;
                        }
                     }
                  } else {
                     var1.setline(450);
                     var4 = var1.getlocal(1);
                     var10000 = var4._eq(PyString.fromInterned("prev"));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(459);
                        throw Py.makeException(var1.getglobal("Error"), var1.getglobal("None"));
                     } else {
                        var1.setline(451);
                        var4 = var1.getlocal(0).__getattr__("getcurrent").__call__(var2);
                        var1.setlocal(3, var4);
                        var4 = null;
                        var1.setline(452);
                        var4 = var1.getglobal("bisect").__call__(var2, var1.getlocal(2), var1.getlocal(3)._sub(Py.newInteger(1)));
                        var1.setlocal(4, var4);
                        var4 = null;
                        var1.setline(453);
                        var4 = var1.getlocal(4);
                        var10000 = var4._eq(Py.newInteger(0));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(454);
                           throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("no prev message"));
                        } else {
                           try {
                              var1.setline(456);
                              var3 = var1.getlocal(2).__getitem__(var1.getlocal(4)._sub(Py.newInteger(1)));
                              var1.f_lasti = -1;
                              return var3;
                           } catch (Throwable var5) {
                              var8 = Py.setException(var5, var1);
                              if (var8.match(var1.getglobal("IndexError"))) {
                                 var1.setline(458);
                                 throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("no prev message"));
                              } else {
                                 throw var8;
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

   public PyObject openmessage$34(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyString.fromInterned("Open a message -- returns a Message object.");
      var1.setline(463);
      PyObject var3 = var1.getglobal("Message").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject removemessages$35(PyFrame var1, ThreadState var2) {
      var1.setline(466);
      PyString.fromInterned("Remove one or more messages -- may raise os.error.");
      var1.setline(467);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(468);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(469);
      PyObject var9 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(469);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(482);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(483);
               var1.getlocal(0).__getattr__("removefromallsequences").__call__(var2, var1.getlocal(3));
            }

            var1.setline(484);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(485);
               var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               PyObject var10000 = var9._eq(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(486);
                  throw Py.makeException(var1.getglobal("os").__getattr__("error"), var1.getlocal(2).__getitem__(Py.newInteger(0)));
               } else {
                  var1.setline(488);
                  throw Py.makeException(var1.getglobal("os").__getattr__("error"), new PyTuple(new PyObject[]{PyString.fromInterned("multiple errors:"), var1.getlocal(2)}));
               }
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setlocal(4, var4);
         var1.setline(470);
         PyObject var5 = var1.getlocal(0).__getattr__("getmessagefilename").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(471);
         var5 = var1.getlocal(0).__getattr__("getmessagefilename").__call__(var2, PyString.fromInterned(",")._add(var1.getglobal("str").__call__(var2, var1.getlocal(4))));
         var1.setlocal(6, var5);
         var5 = null;

         PyException var10;
         try {
            var1.setline(473);
            var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(6));
         } catch (Throwable var8) {
            var10 = Py.setException(var8, var1);
            if (!var10.match(var1.getglobal("os").__getattr__("error"))) {
               throw var10;
            }

            var1.setline(475);
         }

         try {
            var1.setline(477);
            var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(5), var1.getlocal(6));
         } catch (Throwable var7) {
            var10 = Py.setException(var7, var1);
            if (var10.match(var1.getglobal("os").__getattr__("error"))) {
               PyObject var6 = var10.value;
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(479);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(7));
               continue;
            }

            throw var10;
         }

         var1.setline(481);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
      }
   }

   public PyObject refilemessages$36(PyFrame var1, ThreadState var2) {
      var1.setline(492);
      PyString.fromInterned("Refile one or more messages -- may raise os.error.\n        'tofolder' is an open folder object.");
      var1.setline(493);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(494);
      PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var11);
      var3 = null;
      var1.setline(495);
      PyObject var12 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(495);
         PyObject var4 = var12.__iternext__();
         if (var4 == null) {
            var1.setline(515);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(516);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(517);
                  var1.getlocal(2).__getattr__("_copysequences").__call__(var2, var1.getlocal(0), var1.getlocal(5).__getattr__("items").__call__(var2));
               }

               var1.setline(518);
               var1.getlocal(0).__getattr__("removefromallsequences").__call__(var2, var1.getlocal(5).__getattr__("keys").__call__(var2));
            }

            var1.setline(519);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(520);
               var12 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               PyObject var10000 = var12._eq(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(521);
                  throw Py.makeException(var1.getglobal("os").__getattr__("error"), var1.getlocal(4).__getitem__(Py.newInteger(0)));
               } else {
                  var1.setline(523);
                  throw Py.makeException(var1.getglobal("os").__getattr__("error"), new PyTuple(new PyObject[]{PyString.fromInterned("multiple errors:"), var1.getlocal(4)}));
               }
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         var1.setlocal(6, var4);
         var1.setline(496);
         PyObject var5 = var1.getlocal(2).__getattr__("getlast").__call__(var2)._add(Py.newInteger(1));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(497);
         var5 = var1.getlocal(0).__getattr__("getmessagefilename").__call__(var2, var1.getlocal(6));
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(498);
         var5 = var1.getlocal(2).__getattr__("getmessagefilename").__call__(var2, var1.getlocal(7));
         var1.setlocal(9, var5);
         var5 = null;

         try {
            var1.setline(500);
            var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(8), var1.getlocal(9));
         } catch (Throwable var10) {
            PyException var13 = Py.setException(var10, var1);
            if (!var13.match(var1.getglobal("os").__getattr__("error"))) {
               throw var13;
            }

            try {
               var1.setline(504);
               var1.getglobal("shutil").__getattr__("copy2").__call__(var2, var1.getlocal(8), var1.getlocal(9));
               var1.setline(505);
               var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(8));
            } catch (Throwable var9) {
               PyException var6 = Py.setException(var9, var1);
               if (var6.match(new PyTuple(new PyObject[]{var1.getglobal("IOError"), var1.getglobal("os").__getattr__("error")}))) {
                  PyObject var7 = var6.value;
                  var1.setlocal(10, var7);
                  var7 = null;
                  var1.setline(507);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(10));

                  try {
                     var1.setline(509);
                     var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(9));
                     continue;
                  } catch (Throwable var8) {
                     PyException var14 = Py.setException(var8, var1);
                     if (var14.match(var1.getglobal("os").__getattr__("error"))) {
                        var1.setline(511);
                        continue;
                     }

                     throw var14;
                  }
               }

               throw var6;
            }
         }

         var1.setline(513);
         var1.getlocal(2).__getattr__("setlast").__call__(var2, var1.getlocal(7));
         var1.setline(514);
         var5 = var1.getlocal(7);
         var1.getlocal(5).__setitem__(var1.getlocal(6), var5);
         var5 = null;
      }
   }

   public PyObject _copysequences$37(PyFrame var1, ThreadState var2) {
      var1.setline(526);
      PyString.fromInterned("Helper for refilemessages() to copy sequences.");
      var1.setline(527);
      PyObject var3 = var1.getlocal(1).__getattr__("getsequences").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(528);
      var3 = var1.getlocal(0).__getattr__("getsequences").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(529);
      PyInteger var10 = Py.newInteger(0);
      var1.setlocal(5, var10);
      var3 = null;
      var1.setline(530);
      var3 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(530);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(543);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(544);
               var1.getlocal(0).__getattr__("putsequences").__call__(var2, var1.getlocal(4));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(7, var6);
         var6 = null;

         PyObject var12;
         try {
            var1.setline(532);
            var12 = var1.getlocal(4).__getitem__(var1.getlocal(6));
            var1.setlocal(8, var12);
            var5 = null;
            var1.setline(533);
            PyInteger var13 = Py.newInteger(0);
            var1.setlocal(9, var13);
            var5 = null;
         } catch (Throwable var9) {
            PyException var11 = Py.setException(var9, var1);
            if (!var11.match(var1.getglobal("KeyError"))) {
               throw var11;
            }

            var1.setline(535);
            PyList var14 = new PyList(Py.EmptyObjects);
            var1.setlocal(8, var14);
            var6 = null;
            var1.setline(536);
            PyInteger var16 = Py.newInteger(1);
            var1.setlocal(9, var16);
            var6 = null;
         }

         var1.setline(537);
         var12 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(537);
            var6 = var12.__iternext__();
            PyObject var10000;
            if (var6 == null) {
               var1.setline(541);
               var10000 = var1.getlocal(9);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(8);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(542);
                  var12 = var1.getlocal(8);
                  var1.getlocal(4).__setitem__(var1.getlocal(6), var12);
                  var5 = null;
               }
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(10, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(11, var8);
            var8 = null;
            var1.setline(538);
            PyObject var15 = var1.getlocal(10);
            var10000 = var15._in(var1.getlocal(7));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(539);
               var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(11));
               var1.setline(540);
               PyInteger var17 = Py.newInteger(1);
               var1.setlocal(5, var17);
               var7 = null;
            }
         }
      }
   }

   public PyObject movemessage$38(PyFrame var1, ThreadState var2) {
      var1.setline(548);
      PyString.fromInterned("Move one message over a specific destination message,\n        which may or may not already exist.");
      var1.setline(549);
      PyObject var3 = var1.getlocal(0).__getattr__("getmessagefilename").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(551);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(552);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(553);
      var1.dellocal(5);
      var1.setline(554);
      var3 = var1.getlocal(2).__getattr__("getmessagefilename").__call__(var2, var1.getlocal(3));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(555);
      var3 = var1.getlocal(2).__getattr__("getmessagefilename").__call__(var2, PyString.fromInterned(",%d")._mod(var1.getlocal(3)));
      var1.setlocal(7, var3);
      var3 = null;

      PyException var12;
      try {
         var1.setline(557);
         var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(6), var1.getlocal(7));
      } catch (Throwable var10) {
         var12 = Py.setException(var10, var1);
         if (!var12.match(var1.getglobal("os").__getattr__("error"))) {
            throw var12;
         }

         var1.setline(559);
      }

      try {
         var1.setline(561);
         var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(4), var1.getlocal(6));
      } catch (Throwable var9) {
         var12 = Py.setException(var9, var1);
         if (!var12.match(var1.getglobal("os").__getattr__("error"))) {
            throw var12;
         }

         var1.setline(564);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(8, var4);
         var4 = null;
         var4 = null;

         PyException var5;
         try {
            var1.setline(566);
            var1.getlocal(2).__getattr__("setlast").__call__(var2, var1.getglobal("None"));
            var1.setline(567);
            var1.getglobal("shutil").__getattr__("copy2").__call__(var2, var1.getlocal(4), var1.getlocal(6));
            var1.setline(568);
            PyInteger var11 = Py.newInteger(1);
            var1.setlocal(8, var11);
            var5 = null;
         } catch (Throwable var7) {
            Py.addTraceback(var7, var1);
            var1.setline(570);
            if (var1.getlocal(8).__not__().__nonzero__()) {
               try {
                  var1.setline(572);
                  var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(6));
               } catch (Throwable var6) {
                  var5 = Py.setException(var6, var1);
                  if (!var5.match(var1.getglobal("os").__getattr__("error"))) {
                     throw var5;
                  }

                  var1.setline(574);
               }
            }

            throw (Throwable)var7;
         }

         var1.setline(570);
         if (var1.getlocal(8).__not__().__nonzero__()) {
            try {
               var1.setline(572);
               var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(6));
            } catch (Throwable var8) {
               var5 = Py.setException(var8, var1);
               if (!var5.match(var1.getglobal("os").__getattr__("error"))) {
                  throw var5;
               }

               var1.setline(574);
            }
         }

         var1.setline(575);
         var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(4));
      }

      var1.setline(576);
      var1.getlocal(0).__getattr__("removefromallsequences").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copymessage$39(PyFrame var1, ThreadState var2) {
      var1.setline(580);
      PyString.fromInterned("Copy one message over a specific destination message,\n        which may or may not already exist.");
      var1.setline(581);
      PyObject var3 = var1.getlocal(0).__getattr__("getmessagefilename").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(583);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(584);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(585);
      var1.dellocal(5);
      var1.setline(586);
      var3 = var1.getlocal(2).__getattr__("getmessagefilename").__call__(var2, var1.getlocal(3));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(587);
      var3 = var1.getlocal(2).__getattr__("getmessagefilename").__call__(var2, PyString.fromInterned(",%d")._mod(var1.getlocal(3)));
      var1.setlocal(7, var3);
      var3 = null;

      try {
         var1.setline(589);
         var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(6), var1.getlocal(7));
      } catch (Throwable var8) {
         PyException var10 = Py.setException(var8, var1);
         if (!var10.match(var1.getglobal("os").__getattr__("error"))) {
            throw var10;
         }

         var1.setline(591);
      }

      var1.setline(592);
      PyInteger var11 = Py.newInteger(0);
      var1.setlocal(8, var11);
      var3 = null;
      var3 = null;

      PyException var4;
      try {
         var1.setline(594);
         var1.getlocal(2).__getattr__("setlast").__call__(var2, var1.getglobal("None"));
         var1.setline(595);
         var1.getglobal("shutil").__getattr__("copy2").__call__(var2, var1.getlocal(4), var1.getlocal(6));
         var1.setline(596);
         PyInteger var9 = Py.newInteger(1);
         var1.setlocal(8, var9);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(598);
         if (var1.getlocal(8).__not__().__nonzero__()) {
            try {
               var1.setline(600);
               var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(6));
            } catch (Throwable var5) {
               var4 = Py.setException(var5, var1);
               if (!var4.match(var1.getglobal("os").__getattr__("error"))) {
                  throw var4;
               }

               var1.setline(602);
            }
         }

         throw (Throwable)var6;
      }

      var1.setline(598);
      if (var1.getlocal(8).__not__().__nonzero__()) {
         try {
            var1.setline(600);
            var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(6));
         } catch (Throwable var7) {
            var4 = Py.setException(var7, var1);
            if (!var4.match(var1.getglobal("os").__getattr__("error"))) {
               throw var4;
            }

            var1.setline(602);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject createmessage$40(PyFrame var1, ThreadState var2) {
      var1.setline(605);
      PyString.fromInterned("Create a message, with text from the open file txt.");
      var1.setline(606);
      PyObject var3 = var1.getlocal(0).__getattr__("getmessagefilename").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(607);
      var3 = var1.getlocal(0).__getattr__("getmessagefilename").__call__(var2, PyString.fromInterned(",%d")._mod(var1.getlocal(1)));
      var1.setlocal(4, var3);
      var3 = null;

      try {
         var1.setline(609);
         var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      } catch (Throwable var8) {
         PyException var9 = Py.setException(var8, var1);
         if (!var9.match(var1.getglobal("os").__getattr__("error"))) {
            throw var9;
         }

         var1.setline(611);
      }

      var1.setline(612);
      PyInteger var11 = Py.newInteger(0);
      var1.setlocal(5, var11);
      var3 = null;
      var1.setline(613);
      var3 = Py.newInteger(16)._mul(Py.newInteger(1024));
      var1.setlocal(6, var3);
      var3 = null;
      var3 = null;

      PyException var4;
      try {
         var1.setline(615);
         PyObject var10 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(7, var10);
         var4 = null;

         while(true) {
            var1.setline(616);
            if (Py.newInteger(1).__nonzero__()) {
               var1.setline(617);
               var10 = var1.getlocal(2).__getattr__("read").__call__(var2, var1.getlocal(6));
               var1.setlocal(8, var10);
               var4 = null;
               var1.setline(618);
               if (!var1.getlocal(8).__not__().__nonzero__()) {
                  var1.setline(620);
                  var1.getlocal(7).__getattr__("write").__call__(var2, var1.getlocal(8));
                  continue;
               }
            }

            var1.setline(621);
            var1.getlocal(7).__getattr__("close").__call__(var2);
            var1.setline(622);
            PyInteger var12 = Py.newInteger(1);
            var1.setlocal(5, var12);
            var4 = null;
            break;
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(624);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            try {
               var1.setline(626);
               var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(3));
            } catch (Throwable var5) {
               var4 = Py.setException(var5, var1);
               if (!var4.match(var1.getglobal("os").__getattr__("error"))) {
                  throw var4;
               }

               var1.setline(628);
            }
         }

         throw (Throwable)var7;
      }

      var1.setline(624);
      if (var1.getlocal(5).__not__().__nonzero__()) {
         try {
            var1.setline(626);
            var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(3));
         } catch (Throwable var6) {
            var4 = Py.setException(var6, var1);
            if (!var4.match(var1.getglobal("os").__getattr__("error"))) {
               throw var4;
            }

            var1.setline(628);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject removefromallsequences$41(PyFrame var1, ThreadState var2) {
      var1.setline(632);
      PyString.fromInterned("Remove one or more messages from all sequences (including last)\n        -- but not from 'cur'!!!");
      var1.setline(633);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("last"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("last");
         var10000 = var3._in(var1.getlocal(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(634);
         var1.getlocal(0).__delattr__("last");
      }

      var1.setline(635);
      var3 = var1.getlocal(0).__getattr__("getsequences").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(636);
      PyInteger var8 = Py.newInteger(0);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(637);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         PyObject var6;
         PyObject var9;
         do {
            var1.setline(637);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(646);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(647);
                  var1.getlocal(0).__getattr__("putsequences").__call__(var2, var1.getlocal(2));
               }

               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(638);
            var9 = var1.getlocal(4);
            var10000 = var9._eq(PyString.fromInterned("cur"));
            var5 = null;
         } while(var10000.__nonzero__());

         var1.setline(640);
         var9 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(640);
            var6 = var9.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(6, var6);
            var1.setline(641);
            PyObject var7 = var1.getlocal(6);
            var10000 = var7._in(var1.getlocal(5));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(642);
               var1.getlocal(5).__getattr__("remove").__call__(var2, var1.getlocal(6));
               var1.setline(643);
               PyInteger var10 = Py.newInteger(1);
               var1.setlocal(3, var10);
               var7 = null;
               var1.setline(644);
               if (var1.getlocal(5).__not__().__nonzero__()) {
                  var1.setline(645);
                  var1.getlocal(2).__delitem__(var1.getlocal(4));
               }
            }
         }
      }
   }

   public PyObject getlast$42(PyFrame var1, ThreadState var2) {
      var1.setline(650);
      PyString.fromInterned("Return the last message number.");
      var1.setline(651);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("last")).__not__().__nonzero__()) {
         var1.setline(652);
         var1.getlocal(0).__getattr__("listmessages").__call__(var2);
      }

      var1.setline(653);
      PyObject var3 = var1.getlocal(0).__getattr__("last");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setlast$43(PyFrame var1, ThreadState var2) {
      var1.setline(656);
      PyString.fromInterned("Set the last message number.");
      var1.setline(657);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(658);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("last")).__nonzero__()) {
            var1.setline(659);
            var1.getlocal(0).__delattr__("last");
         }
      } else {
         var1.setline(661);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("last", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Message$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(665);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$45, PyString.fromInterned("Constructor."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(674);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$46, PyString.fromInterned("String representation."));
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(678);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, getheadertext$47, PyString.fromInterned("Return the message's header text as a string.  If an\n        argument is specified, it is used as a filter predicate to\n        decide which headers to return (its argument is the header\n        name converted to lower case)."));
      var1.setlocal("getheadertext", var4);
      var3 = null;
      var1.setline(695);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, getbodytext$48, PyString.fromInterned("Return the message's body text as string.  This undoes a\n        Content-Transfer-Encoding, but does not interpret other MIME\n        features (e.g. multipart messages).  To suppress decoding,\n        pass 0 as an argument."));
      var1.setlocal("getbodytext", var4);
      var3 = null;
      var1.setline(712);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getbodyparts$49, PyString.fromInterned("Only for multipart messages: return the message's body as a\n        list of SubMessage objects.  Each submessage object behaves\n        (almost) as a Message object."));
      var1.setlocal("getbodyparts", var4);
      var3 = null;
      var1.setline(732);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getbody$50, PyString.fromInterned("Return body, either a string or a list of messages."));
      var1.setlocal("getbody", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$45(PyFrame var1, ThreadState var2) {
      var1.setline(666);
      PyString.fromInterned("Constructor.");
      var1.setline(667);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("folder", var3);
      var3 = null;
      var1.setline(668);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("number", var3);
      var3 = null;
      var1.setline(669);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(670);
         var3 = var1.getlocal(1).__getattr__("getmessagefilename").__call__(var2, var1.getlocal(2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(671);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("r"));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(672);
      var1.getglobal("mimetools").__getattr__("Message").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$46(PyFrame var1, ThreadState var2) {
      var1.setline(675);
      PyString.fromInterned("String representation.");
      var1.setline(676);
      PyObject var3 = PyString.fromInterned("Message(%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("folder")), var1.getlocal(0).__getattr__("number")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getheadertext$47(PyFrame var1, ThreadState var2) {
      var1.setline(682);
      PyString.fromInterned("Return the message's header text as a string.  If an\n        argument is specified, it is used as a filter predicate to\n        decide which headers to return (its argument is the header\n        name converted to lower case).");
      var1.setline(683);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(684);
         var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("headers"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(685);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(686);
         PyInteger var7 = Py.newInteger(0);
         var1.setlocal(3, var7);
         var4 = null;
         var1.setline(687);
         PyObject var8 = var1.getlocal(0).__getattr__("headers").__iter__();

         while(true) {
            var1.setline(687);
            PyObject var5 = var8.__iternext__();
            if (var5 == null) {
               var1.setline(693);
               var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(4, var5);
            var1.setline(688);
            if (var1.getlocal(4).__getitem__(Py.newInteger(0)).__getattr__("isspace").__call__(var2).__not__().__nonzero__()) {
               var1.setline(689);
               PyObject var6 = var1.getlocal(4).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(690);
               var6 = var1.getlocal(5);
               var10000 = var6._gt(Py.newInteger(0));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(691);
                  var6 = var1.getlocal(1).__call__(var2, var1.getlocal(4).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null).__getattr__("lower").__call__(var2));
                  var1.setlocal(3, var6);
                  var6 = null;
               }
            }

            var1.setline(692);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(692);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
            }
         }
      }
   }

   public PyObject getbodytext$48(PyFrame var1, ThreadState var2) {
      var1.setline(699);
      PyString.fromInterned("Return the message's body text as string.  This undoes a\n        Content-Transfer-Encoding, but does not interpret other MIME\n        features (e.g. multipart messages).  To suppress decoding,\n        pass 0 as an argument.");
      var1.setline(700);
      var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("startofbody"));
      var1.setline(701);
      PyObject var3 = var1.getlocal(0).__getattr__("getencoding").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(702);
      PyObject var10000 = var1.getlocal(1).__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("7bit"), PyString.fromInterned("8bit"), PyString.fromInterned("binary")}));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(703);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("read").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         PyException var4;
         String[] var5;
         try {
            var1.setline(705);
            String[] var8 = new String[]{"StringIO"};
            PyObject[] var9 = imp.importFrom("cStringIO", var8, var1, -1);
            PyObject var12 = var9[0];
            var1.setlocal(3, var12);
            var5 = null;
         } catch (Throwable var7) {
            var4 = Py.setException(var7, var1);
            if (!var4.match(var1.getglobal("ImportError"))) {
               throw var4;
            }

            var1.setline(707);
            var5 = new String[]{"StringIO"};
            PyObject[] var11 = imp.importFrom("StringIO", var5, var1, -1);
            PyObject var6 = var11[0];
            var1.setlocal(3, var6);
            var6 = null;
         }

         var1.setline(708);
         PyObject var10 = var1.getlocal(3).__call__(var2);
         var1.setlocal(4, var10);
         var4 = null;
         var1.setline(709);
         var1.getglobal("mimetools").__getattr__("decode").__call__(var2, var1.getlocal(0).__getattr__("fp"), var1.getlocal(4), var1.getlocal(2));
         var1.setline(710);
         var3 = var1.getlocal(4).__getattr__("getvalue").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getbodyparts$49(PyFrame var1, ThreadState var2) {
      var1.setline(715);
      PyString.fromInterned("Only for multipart messages: return the message's body as a\n        list of SubMessage objects.  Each submessage object behaves\n        (almost) as a Message object.");
      var1.setline(716);
      PyObject var3 = var1.getlocal(0).__getattr__("getmaintype").__call__(var2);
      PyObject var10000 = var3._ne(PyString.fromInterned("multipart"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(717);
         throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("Content-Type is not multipart/*"));
      } else {
         var1.setline(718);
         var3 = var1.getlocal(0).__getattr__("getparam").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("boundary"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(719);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(720);
            throw Py.makeException(var1.getglobal("Error"), PyString.fromInterned("multipart/* without boundary param"));
         } else {
            var1.setline(721);
            var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("startofbody"));
            var1.setline(722);
            var3 = var1.getglobal("multifile").__getattr__("MultiFile").__call__(var2, var1.getlocal(0).__getattr__("fp"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(723);
            var1.getlocal(2).__getattr__("push").__call__(var2, var1.getlocal(1));
            var1.setline(724);
            PyList var4 = new PyList(Py.EmptyObjects);
            var1.setlocal(3, var4);
            var3 = null;

            while(true) {
               var1.setline(725);
               if (!var1.getlocal(2).__getattr__("next").__call__(var2).__nonzero__()) {
                  var1.setline(729);
                  var1.getlocal(2).__getattr__("pop").__call__(var2);
                  var1.setline(730);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(726);
               var3 = PyString.fromInterned("%s.%r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("number"), Py.newInteger(1)._add(var1.getglobal("len").__call__(var2, var1.getlocal(3)))}));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(727);
               var3 = var1.getglobal("SubMessage").__call__(var2, var1.getlocal(0).__getattr__("folder"), var1.getlocal(4), var1.getlocal(2));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(728);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      }
   }

   public PyObject getbody$50(PyFrame var1, ThreadState var2) {
      var1.setline(733);
      PyString.fromInterned("Return body, either a string or a list of messages.");
      var1.setline(734);
      PyObject var3 = var1.getlocal(0).__getattr__("getmaintype").__call__(var2);
      PyObject var10000 = var3._eq(PyString.fromInterned("multipart"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(735);
         var3 = var1.getlocal(0).__getattr__("getbodyparts").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(737);
         var3 = var1.getlocal(0).__getattr__("getbodytext").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject SubMessage$51(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(742);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$52, PyString.fromInterned("Constructor."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(752);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$53, PyString.fromInterned("String representation."));
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(757);
      var3 = new PyObject[]{Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, getbodytext$54, (PyObject)null);
      var1.setlocal("getbodytext", var4);
      var3 = null;
      var1.setline(763);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getbodyparts$55, (PyObject)null);
      var1.setlocal("getbodyparts", var4);
      var3 = null;
      var1.setline(767);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getbody$56, (PyObject)null);
      var1.setlocal("getbody", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$52(PyFrame var1, ThreadState var2) {
      var1.setline(743);
      PyString.fromInterned("Constructor.");
      var1.setline(744);
      var1.getglobal("Message").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(745);
      PyObject var3 = var1.getlocal(0).__getattr__("getmaintype").__call__(var2);
      PyObject var10000 = var3._eq(PyString.fromInterned("multipart"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(746);
         var3 = var1.getglobal("Message").__getattr__("getbodyparts").__call__(var2, var1.getlocal(0));
         var1.getlocal(0).__setattr__("body", var3);
         var3 = null;
      } else {
         var1.setline(748);
         var3 = var1.getglobal("Message").__getattr__("getbodytext").__call__(var2, var1.getlocal(0));
         var1.getlocal(0).__setattr__("body", var3);
         var3 = null;
      }

      var1.setline(749);
      var10000 = var1.getglobal("Message").__getattr__("getbodytext");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), Py.newInteger(0)};
      String[] var4 = new String[]{"decode"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("bodyencoded", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$53(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      PyString.fromInterned("String representation.");
      var1.setline(754);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("folder"), var1.getlocal(0).__getattr__("number"), var1.getlocal(0).__getattr__("fp")});
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
      var1.setline(755);
      PyObject var6 = PyString.fromInterned("SubMessage(%s, %s, %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)}));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject getbodytext$54(PyFrame var1, ThreadState var2) {
      var1.setline(758);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(759);
         var3 = var1.getlocal(0).__getattr__("bodyencoded");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(760);
         PyObject var4 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("body"));
         PyObject var10000 = var4._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(761);
            var3 = var1.getlocal(0).__getattr__("body");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject getbodyparts$55(PyFrame var1, ThreadState var2) {
      var1.setline(764);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("body"));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(765);
         var3 = var1.getlocal(0).__getattr__("body");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject getbody$56(PyFrame var1, ThreadState var2) {
      var1.setline(768);
      PyObject var3 = var1.getlocal(0).__getattr__("body");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IntSet$57(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class implementing sets of integers.\n\n    This is an efficient representation for sets consisting of several\n    continuous ranges, e.g. 1-100,200-400,402-1000 is represented\n    internally as a list of three pairs: [(1,100), (200,400),\n    (402,1000)].  The internal representation is always kept normalized.\n\n    The constructor has up to three arguments:\n    - the string used to initialize the set (default ''),\n    - the separator between ranges (default ',')\n    - the separator between begin and end of a range (default '-')\n    The separators must be strings (not regexprs) and should be different.\n\n    The tostring() function yields a string that can be passed to another\n    IntSet constructor; __repr__() is a valid IntSet constructor itself.\n    "));
      var1.setline(787);
      PyString.fromInterned("Class implementing sets of integers.\n\n    This is an efficient representation for sets consisting of several\n    continuous ranges, e.g. 1-100,200-400,402-1000 is represented\n    internally as a list of three pairs: [(1,100), (200,400),\n    (402,1000)].  The internal representation is always kept normalized.\n\n    The constructor has up to three arguments:\n    - the string used to initialize the set (default ''),\n    - the separator between ranges (default ',')\n    - the separator between begin and end of a range (default '-')\n    The separators must be strings (not regexprs) and should be different.\n\n    The tostring() function yields a string that can be passed to another\n    IntSet constructor; __repr__() is a valid IntSet constructor itself.\n    ");
      var1.setline(794);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), PyString.fromInterned(","), PyString.fromInterned("-")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$58, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(800);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$59, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(803);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$60, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(806);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$61, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      var1.setline(809);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$62, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(812);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, normalize$63, (PyObject)null);
      var1.setlocal("normalize", var4);
      var3 = null;
      var1.setline(823);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tostring$64, (PyObject)null);
      var1.setlocal("tostring", var4);
      var3 = null;
      var1.setline(832);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tolist$65, (PyObject)null);
      var1.setlocal("tolist", var4);
      var3 = null;
      var1.setline(839);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fromlist$66, (PyObject)null);
      var1.setlocal("fromlist", var4);
      var3 = null;
      var1.setline(843);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clone$67, (PyObject)null);
      var1.setlocal("clone", var4);
      var3 = null;
      var1.setline(848);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, min$68, (PyObject)null);
      var1.setlocal("min", var4);
      var3 = null;
      var1.setline(851);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, max$69, (PyObject)null);
      var1.setlocal("max", var4);
      var3 = null;
      var1.setline(854);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, contains$70, (PyObject)null);
      var1.setlocal("contains", var4);
      var3 = null;
      var1.setline(859);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, append$71, (PyObject)null);
      var1.setlocal("append", var4);
      var3 = null;
      var1.setline(884);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addpair$72, (PyObject)null);
      var1.setlocal("addpair", var4);
      var3 = null;
      var1.setline(889);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, fromstring$73, (PyObject)null);
      var1.setlocal("fromstring", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$58(PyFrame var1, ThreadState var2) {
      var1.setline(795);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"pairs", var3);
      var3 = null;
      var1.setline(796);
      PyObject var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("sep", var4);
      var3 = null;
      var1.setline(797);
      var4 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("rng", var4);
      var3 = null;
      var1.setline(798);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(798);
         var1.getlocal(0).__getattr__("fromstring").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reset$59(PyFrame var1, ThreadState var2) {
      var1.setline(801);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"pairs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __cmp__$60(PyFrame var1, ThreadState var2) {
      var1.setline(804);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("pairs"), var1.getlocal(1).__getattr__("pairs"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$61(PyFrame var1, ThreadState var2) {
      var1.setline(807);
      PyObject var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(0).__getattr__("pairs"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$62(PyFrame var1, ThreadState var2) {
      var1.setline(810);
      PyObject var3 = PyString.fromInterned("IntSet(%r, %r, %r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("tostring").__call__(var2), var1.getlocal(0).__getattr__("sep"), var1.getlocal(0).__getattr__("rng")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject normalize$63(PyFrame var1, ThreadState var2) {
      var1.setline(813);
      var1.getlocal(0).__getattr__("pairs").__getattr__("sort").__call__(var2);
      var1.setline(814);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal(1, var3);
      var3 = null;

      while(true) {
         var1.setline(815);
         PyObject var6 = var1.getlocal(1);
         PyObject var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("pairs")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(816);
         var6 = var1.getlocal(0).__getattr__("pairs").__getitem__(var1.getlocal(1)._sub(Py.newInteger(1)));
         PyObject[] var4 = Py.unpackSequence(var6, 2);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(817);
         var6 = var1.getlocal(0).__getattr__("pairs").__getitem__(var1.getlocal(1));
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(818);
         var6 = var1.getlocal(3);
         var10000 = var6._ge(var1.getlocal(4)._sub(Py.newInteger(1)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(819);
            PyList var7 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("max").__call__(var2, var1.getlocal(3), var1.getlocal(5))})});
            var1.getlocal(0).__getattr__("pairs").__setslice__(var1.getlocal(1)._sub(Py.newInteger(1)), var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, var7);
            var3 = null;
         } else {
            var1.setline(821);
            var6 = var1.getlocal(1)._add(Py.newInteger(1));
            var1.setlocal(1, var6);
            var3 = null;
         }
      }
   }

   public PyObject tostring$64(PyFrame var1, ThreadState var2) {
      var1.setline(824);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(825);
      PyObject var7 = var1.getlocal(0).__getattr__("pairs").__iter__();

      while(true) {
         var1.setline(825);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(830);
            var7 = var1.getlocal(1);
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
         var1.setline(826);
         PyObject var8 = var1.getlocal(2);
         PyObject var10000 = var8._eq(var1.getlocal(3));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(826);
            var8 = var1.getglobal("repr").__call__(var2, var1.getlocal(2));
            var1.setlocal(4, var8);
            var5 = null;
         } else {
            var1.setline(827);
            var8 = var1.getglobal("repr").__call__(var2, var1.getlocal(2))._add(var1.getlocal(0).__getattr__("rng"))._add(var1.getglobal("repr").__call__(var2, var1.getlocal(3)));
            var1.setlocal(4, var8);
            var5 = null;
         }

         var1.setline(828);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(828);
            var8 = var1.getlocal(1)._add(var1.getlocal(0).__getattr__("sep")._add(var1.getlocal(4)));
            var1.setlocal(1, var8);
            var5 = null;
         } else {
            var1.setline(829);
            var8 = var1.getlocal(4);
            var1.setlocal(1, var8);
            var5 = null;
         }
      }
   }

   public PyObject tolist$65(PyFrame var1, ThreadState var2) {
      var1.setline(833);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(834);
      PyObject var7 = var1.getlocal(0).__getattr__("pairs").__iter__();

      while(true) {
         var1.setline(834);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(837);
            var7 = var1.getlocal(1);
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
         var1.setline(835);
         PyObject var8 = var1.getglobal("range").__call__(var2, var1.getlocal(2), var1.getlocal(3)._add(Py.newInteger(1)));
         var1.setlocal(4, var8);
         var5 = null;
         var1.setline(836);
         var8 = var1.getlocal(1)._add(var1.getlocal(4));
         var1.setlocal(1, var8);
         var5 = null;
      }
   }

   public PyObject fromlist$66(PyFrame var1, ThreadState var2) {
      var1.setline(840);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(840);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(841);
         var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject clone$67(PyFrame var1, ThreadState var2) {
      var1.setline(844);
      PyObject var3 = var1.getglobal("IntSet").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(845);
      var3 = var1.getlocal(0).__getattr__("pairs").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(1).__setattr__("pairs", var3);
      var3 = null;
      var1.setline(846);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject min$68(PyFrame var1, ThreadState var2) {
      var1.setline(849);
      PyObject var3 = var1.getlocal(0).__getattr__("pairs").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject max$69(PyFrame var1, ThreadState var2) {
      var1.setline(852);
      PyObject var3 = var1.getlocal(0).__getattr__("pairs").__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(-1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject contains$70(PyFrame var1, ThreadState var2) {
      var1.setline(855);
      PyObject var3 = var1.getlocal(0).__getattr__("pairs").__iter__();

      PyObject var6;
      PyObject var7;
      do {
         var1.setline(855);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(857);
            var7 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(856);
         var7 = var1.getlocal(2);
         PyObject var10001 = var1.getlocal(1);
         PyObject var10000 = var7;
         var7 = var10001;
         if ((var6 = var10000._le(var10001)).__nonzero__()) {
            var6 = var7._le(var1.getlocal(3));
         }

         var5 = null;
      } while(!var6.__nonzero__());

      var1.setline(856);
      var7 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject append$71(PyFrame var1, ThreadState var2) {
      var1.setline(860);
      PyObject var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("pairs"))).__iter__();

      PyObject var10000;
      do {
         var1.setline(860);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(876);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("pairs"))._sub(Py.newInteger(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(877);
            var3 = var1.getlocal(2);
            var10000 = var3._ge(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(878);
               var3 = var1.getlocal(0).__getattr__("pairs").__getitem__(var1.getlocal(2));
               PyObject[] var8 = Py.unpackSequence(var3, 2);
               var5 = var8[0];
               var1.setlocal(3, var5);
               var5 = null;
               var5 = var8[1];
               var1.setlocal(4, var5);
               var5 = null;
               var3 = null;
               var1.setline(879);
               var3 = var1.getlocal(1)._sub(Py.newInteger(1));
               var10000 = var3._eq(var1.getlocal(4));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(880);
                  PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1)});
                  var1.getlocal(0).__getattr__("pairs").__setitem__((PyObject)var1.getlocal(2), var9);
                  var3 = null;
                  var1.setline(881);
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }

            var1.setline(882);
            var1.getlocal(0).__getattr__("pairs").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(1)})));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(861);
         var5 = var1.getlocal(0).__getattr__("pairs").__getitem__(var1.getlocal(2));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(3, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(4, var7);
         var7 = null;
         var5 = null;
         var1.setline(862);
         var5 = var1.getlocal(1);
         var10000 = var5._lt(var1.getlocal(3));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(863);
            var5 = var1.getlocal(1)._add(Py.newInteger(1));
            var10000 = var5._eq(var1.getlocal(3));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(864);
               PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(4)});
               var1.getlocal(0).__getattr__("pairs").__setitem__((PyObject)var1.getlocal(2), var10);
               var5 = null;
            } else {
               var1.setline(866);
               var1.getlocal(0).__getattr__("pairs").__getattr__("insert").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(1)})));
            }

            var1.setline(867);
            var5 = var1.getlocal(2);
            var10000 = var5._gt(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(1)._sub(Py.newInteger(1));
               var10000 = var5._eq(var1.getlocal(0).__getattr__("pairs").__getitem__(var1.getlocal(2)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(1)));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(869);
               PyList var11 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("pairs").__getitem__(var1.getlocal(2)._sub(Py.newInteger(1))).__getitem__(Py.newInteger(0)), var1.getlocal(0).__getattr__("pairs").__getitem__(var1.getlocal(2)).__getitem__(Py.newInteger(1))})});
               var1.getlocal(0).__getattr__("pairs").__setslice__(var1.getlocal(2)._sub(Py.newInteger(1)), var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null, var11);
               var5 = null;
            }

            var1.setline(873);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(874);
         var5 = var1.getlocal(1);
         var10000 = var5._le(var1.getlocal(4));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(875);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addpair$72(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._gt(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(885);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(886);
         var1.getlocal(0).__getattr__("pairs").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
         var1.setline(887);
         var1.getlocal(0).__getattr__("normalize").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject fromstring$73(PyFrame var1, ThreadState var2) {
      var1.setline(890);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(891);
      PyObject var8 = var1.getlocal(1).__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("sep")).__iter__();

      while(true) {
         var1.setline(891);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(902);
            var8 = var1.getlocal(0).__getattr__("pairs")._add(var1.getlocal(2));
            var1.getlocal(0).__setattr__("pairs", var8);
            var3 = null;
            var1.setline(903);
            var1.getlocal(0).__getattr__("normalize").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(892);
         PyList var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(893);
         PyObject var9 = var1.getlocal(3).__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("rng")).__iter__();

         while(true) {
            var1.setline(893);
            PyObject var6 = var9.__iternext__();
            if (var6 == null) {
               var1.setline(896);
               var9 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               PyObject var10000 = var9._eq(Py.newInteger(1));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(897);
                  var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4).__getitem__(Py.newInteger(0)), var1.getlocal(4).__getitem__(Py.newInteger(0))})));
               } else {
                  var1.setline(898);
                  var9 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
                  var10000 = var9._eq(Py.newInteger(2));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var9 = var1.getlocal(4).__getitem__(Py.newInteger(0));
                     var10000 = var9._le(var1.getlocal(4).__getitem__(Py.newInteger(1)));
                     var5 = null;
                  }

                  if (!var10000.__nonzero__()) {
                     var1.setline(901);
                     throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("bad data passed to IntSet"));
                  }

                  var1.setline(899);
                  var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4).__getitem__(Py.newInteger(0)), var1.getlocal(4).__getitem__(Py.newInteger(1))})));
               }
               break;
            }

            var1.setlocal(5, var6);
            var1.setline(894);
            PyObject var7 = var1.getlocal(5).__getattr__("strip").__call__(var2);
            var1.setlocal(6, var7);
            var7 = null;
            var1.setline(895);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(6)));
         }
      }
   }

   public PyObject pickline$74(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var4;
      PyObject var6;
      try {
         var1.setline(910);
         var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("r"));
         var1.setlocal(3, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var1.setline(912);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(913);
      var6 = var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned(":"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(914);
      PyObject var10000 = var1.getglobal("re").__getattr__("compile");
      PyObject var10002 = var1.getlocal(4);
      PyObject var10003 = var1.getlocal(2);
      if (var10003.__nonzero__()) {
         var10003 = var1.getglobal("re").__getattr__("IGNORECASE");
      }

      var6 = var10000.__call__(var2, var10002, var10003);
      var1.setlocal(5, var6);
      var3 = null;

      while(true) {
         var1.setline(915);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(916);
         var6 = var1.getlocal(3).__getattr__("readline").__call__(var2);
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(917);
         if (var1.getlocal(6).__not__().__nonzero__()) {
            break;
         }

         var1.setline(918);
         if (var1.getlocal(5).__getattr__("match").__call__(var2, var1.getlocal(6)).__nonzero__()) {
            var1.setline(919);
            var6 = var1.getlocal(6).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(1))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.setlocal(7, var6);
            var3 = null;

            while(true) {
               var1.setline(920);
               if (!Py.newInteger(1).__nonzero__()) {
                  break;
               }

               var1.setline(921);
               var6 = var1.getlocal(3).__getattr__("readline").__call__(var2);
               var1.setlocal(6, var6);
               var3 = null;
               var1.setline(922);
               var10000 = var1.getlocal(6).__not__();
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(6).__getitem__(Py.newInteger(0)).__getattr__("isspace").__call__(var2).__not__();
               }

               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(924);
               var6 = var1.getlocal(7)._add(var1.getlocal(6));
               var1.setlocal(7, var6);
               var3 = null;
            }

            var1.setline(925);
            var4 = var1.getlocal(7).__getattr__("strip").__call__(var2);
            var1.f_lasti = -1;
            return var4;
         }
      }

      var1.setline(926);
      var4 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject updateline$75(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var7;
      try {
         var1.setline(930);
         var7 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("r"));
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(931);
         var7 = var1.getlocal(4).__getattr__("readlines").__call__(var2);
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(932);
         var1.getlocal(4).__getattr__("close").__call__(var2);
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (!var3.match(var1.getglobal("IOError"))) {
            throw var3;
         }

         var1.setline(934);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var4);
         var4 = null;
      }

      var1.setline(935);
      var7 = var1.getglobal("re").__getattr__("escape").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned(":(.*)\n"));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(936);
      PyObject var10000 = var1.getglobal("re").__getattr__("compile");
      PyObject var10002 = var1.getlocal(6);
      PyObject var10003 = var1.getlocal(3);
      if (var10003.__nonzero__()) {
         var10003 = var1.getglobal("re").__getattr__("IGNORECASE");
      }

      var7 = var10000.__call__(var2, var10002, var10003);
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(937);
      var7 = var1.getlocal(2);
      var10000 = var7._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(938);
         var7 = var1.getglobal("None");
         var1.setlocal(8, var7);
         var3 = null;
      } else {
         var1.setline(940);
         var7 = PyString.fromInterned("%s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
         var1.setlocal(8, var7);
         var3 = null;
      }

      var1.setline(941);
      var7 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(5))).__iter__();

      PyObject var8;
      while(true) {
         var1.setline(941);
         var8 = var7.__iternext__();
         PyObject var5;
         if (var8 == null) {
            var1.setline(950);
            var5 = var1.getlocal(8);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(951);
               var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(8));
            }
            break;
         }

         var1.setlocal(9, var8);
         var1.setline(942);
         var5 = var1.getlocal(5).__getitem__(var1.getlocal(9));
         var1.setlocal(10, var5);
         var5 = null;
         var1.setline(943);
         if (var1.getlocal(7).__getattr__("match").__call__(var2, var1.getlocal(10)).__nonzero__()) {
            var1.setline(944);
            var5 = var1.getlocal(8);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(945);
               var1.getlocal(5).__delitem__(var1.getlocal(9));
            } else {
               var1.setline(947);
               var5 = var1.getlocal(8);
               var1.getlocal(5).__setitem__(var1.getlocal(9), var5);
               var5 = null;
            }
            break;
         }
      }

      var1.setline(952);
      var7 = var1.getlocal(0)._add(PyString.fromInterned("~"));
      var1.setlocal(11, var7);
      var3 = null;
      var1.setline(953);
      var7 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(954);
      var7 = var1.getlocal(5).__iter__();

      while(true) {
         var1.setline(954);
         var8 = var7.__iternext__();
         if (var8 == null) {
            var1.setline(956);
            var1.getlocal(4).__getattr__("close").__call__(var2);
            var1.setline(957);
            var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(11), var1.getlocal(0));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(10, var8);
         var1.setline(955);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(10));
      }
   }

   public PyObject test$76(PyFrame var1, ThreadState var2) {
      var1.setline(964);
      var1.getglobal("os").__getattr__("system").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rm -rf $HOME/Mail/@test"));
      var1.setline(965);
      PyObject var3 = var1.getglobal("MH").__call__(var2);
      var1.setglobal("mh", var3);
      var3 = null;
      var1.setline(966);
      PyObject[] var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, do$77, (PyObject)null);
      var1.setlocal(0, var9);
      var3 = null;
      var1.setline(967);
      var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mh.listfolders()"));
      var1.setline(968);
      var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mh.listallfolders()"));
      var1.setline(969);
      PyList var10 = new PyList(new PyObject[]{PyString.fromInterned("@test"), PyString.fromInterned("@test/test1"), PyString.fromInterned("@test/test2"), PyString.fromInterned("@test/test1/test11"), PyString.fromInterned("@test/test1/test12"), PyString.fromInterned("@test/test1/test11/test111")});
      var1.setlocal(1, var10);
      var3 = null;
      var1.setline(972);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(972);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(973);
            var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mh.listsubfolders('@test')"));
            var1.setline(974);
            var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mh.listallsubfolders('@test')"));
            var1.setline(975);
            var3 = var1.getglobal("mh").__getattr__("openfolder").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("@test"));
            var1.setglobal("f", var3);
            var3 = null;
            var1.setline(976);
            var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f.listsubfolders()"));
            var1.setline(977);
            var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f.listallsubfolders()"));
            var1.setline(978);
            var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f.getsequences()"));
            var1.setline(979);
            var3 = var1.getglobal("f").__getattr__("getsequences").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(980);
            var3 = var1.getglobal("IntSet").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("1-10 12-20"), (PyObject)PyString.fromInterned(" ")).__getattr__("tolist").__call__(var2);
            var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("foo"), var3);
            var3 = null;
            var1.setline(981);
            Py.println(var1.getlocal(3));
            var1.setline(982);
            var1.getglobal("f").__getattr__("putsequences").__call__(var2, var1.getlocal(3));
            var1.setline(983);
            var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f.getsequences()"));
            var1.setline(984);
            var3 = var1.getglobal("reversed").__call__(var2, var1.getlocal(1)).__iter__();

            while(true) {
               var1.setline(984);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(985);
                  var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mh.getcontext()"));
                  var1.setline(986);
                  var3 = var1.getglobal("mh").__getattr__("getcontext").__call__(var2);
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(987);
                  var3 = var1.getglobal("mh").__getattr__("openfolder").__call__(var2, var1.getlocal(4));
                  var1.setglobal("f", var3);
                  var3 = null;
                  var1.setline(988);
                  var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f.getcurrent()"));
                  var1.setline(989);
                  var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("first"), PyString.fromInterned("last"), PyString.fromInterned("cur"), PyString.fromInterned("."), PyString.fromInterned("prev"), PyString.fromInterned("next"), PyString.fromInterned("first:3"), PyString.fromInterned("last:3"), PyString.fromInterned("cur:3"), PyString.fromInterned("cur:-3"), PyString.fromInterned("prev:3"), PyString.fromInterned("next:3"), PyString.fromInterned("1:3"), PyString.fromInterned("1:-3"), PyString.fromInterned("100:3"), PyString.fromInterned("100:-3"), PyString.fromInterned("10000:3"), PyString.fromInterned("10000:-3"), PyString.fromInterned("all")})).__iter__();

                  while(true) {
                     var1.setline(989);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(1001);
                        var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("f.listmessages()"));
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(5, var4);

                     PyException var5;
                     try {
                        var1.setline(995);
                        var1.getlocal(0).__call__(var2, PyString.fromInterned("f.parsesequence(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(5)})));
                     } catch (Throwable var7) {
                        var5 = Py.setException(var7, var1);
                        if (!var5.match(var1.getglobal("Error"))) {
                           throw var5;
                        }

                        PyObject var6 = var5.value;
                        var1.setlocal(6, var6);
                        var6 = null;
                        var1.setline(997);
                        Py.printComma(PyString.fromInterned("Error:"));
                        Py.println(var1.getlocal(6));
                     }

                     var1.setline(998);
                     PyObject var11 = var1.getglobal("os").__getattr__("popen").__call__(var2, PyString.fromInterned("pick %r 2>/dev/null")._mod(new PyTuple(new PyObject[]{var1.getlocal(5)}))).__getattr__("read").__call__(var2);
                     var1.setlocal(7, var11);
                     var5 = null;
                     var1.setline(999);
                     var11 = var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(7).__getattr__("split").__call__(var2));
                     var1.setlocal(8, var11);
                     var5 = null;
                     var1.setline(1000);
                     Py.printComma(var1.getlocal(8));
                     Py.println(PyString.fromInterned("<-- pick"));
                  }
               }

               var1.setlocal(2, var4);
               var1.setline(984);
               var1.getlocal(0).__call__(var2, PyString.fromInterned("mh.deletefolder(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)})));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(972);
         var1.getlocal(0).__call__(var2, PyString.fromInterned("mh.makefolder(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)})));
      }
   }

   public PyObject do$77(PyFrame var1, ThreadState var2) {
      var1.setline(966);
      Py.println(var1.getlocal(0));
      var1.setline(966);
      Py.println(var1.getglobal("eval").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public mhlib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 91, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MH$2 = Py.newCode(0, var2, var1, "MH", 95, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path", "profile"};
      __init__$3 = Py.newCode(3, var2, var1, "__init__", 102, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$4 = Py.newCode(1, var2, var1, "__repr__", 114, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "args"};
      error$5 = Py.newCode(3, var2, var1, "error", 118, true, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      getprofile$6 = Py.newCode(2, var2, var1, "getprofile", 122, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getpath$7 = Py.newCode(1, var2, var1, "getpath", 126, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context"};
      getcontext$8 = Py.newCode(1, var2, var1, "getcontext", 130, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "context", "fn", "f"};
      setcontext$9 = Py.newCode(2, var2, var1, "setcontext", 137, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "folders", "path", "name", "fullname"};
      listfolders$10 = Py.newCode(1, var2, var1, "listfolders", 144, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "fullname", "nlinks", "subfolders", "subnames", "subname", "fullsubname", "name_subname"};
      listsubfolders$11 = Py.newCode(2, var2, var1, "listsubfolders", 155, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      listallfolders$12 = Py.newCode(1, var2, var1, "listallfolders", 179, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "fullname", "nlinks", "subfolders", "subnames", "subname", "fullsubname", "name_subname", "subsubfolders"};
      listallsubfolders$13 = Py.newCode(2, var2, var1, "listallsubfolders", 183, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      openfolder$14 = Py.newCode(2, var2, var1, "openfolder", 211, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "protect", "mode"};
      makefolder$15 = Py.newCode(2, var2, var1, "makefolder", 215, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "fullname", "subname", "fullsubname"};
      deletefolder$16 = Py.newCode(2, var2, var1, "deletefolder", 224, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"str"};
      isnumeric$17 = Py.newCode(1, var2, var1, "isnumeric", 239, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Folder$18 = Py.newCode(0, var2, var1, "Folder", 242, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "mh", "name"};
      __init__$19 = Py.newCode(3, var2, var1, "__init__", 245, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$20 = Py.newCode(1, var2, var1, "__repr__", 252, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      error$21 = Py.newCode(2, var2, var1, "error", 256, true, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getfullname$22 = Py.newCode(1, var2, var1, "getfullname", 260, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getsequencesfilename$23 = Py.newCode(1, var2, var1, "getsequencesfilename", 264, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      getmessagefilename$24 = Py.newCode(2, var2, var1, "getmessagefilename", 268, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      listsubfolders$25 = Py.newCode(1, var2, var1, "listsubfolders", 272, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      listallsubfolders$26 = Py.newCode(1, var2, var1, "listallsubfolders", 276, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "messages", "match", "append", "name"};
      listmessages$27 = Py.newCode(1, var2, var1, "listmessages", 280, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sequences", "fullname", "f", "line", "fields", "key", "value"};
      getsequences$28 = Py.newCode(1, var2, var1, "getsequences", 297, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sequences", "fullname", "f", "key", "seq", "s"};
      putsequences$29 = Py.newCode(2, var2, var1, "putsequences", 317, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "seqs"};
      getcurrent$30 = Py.newCode(1, var2, var1, "getcurrent", 334, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      setcurrent$31 = Py.newCode(2, var2, var1, "setcurrent", 342, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "seq", "all", "i", "head", "dir", "tail", "count", "anchor", "msg", "seqs", "msgs", "begin", "end", "j", "r", "n"};
      parsesequence$32 = Py.newCode(2, var2, var1, "parsesequence", 346, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "seq", "all", "n", "i"};
      _parseindex$33 = Py.newCode(3, var2, var1, "_parseindex", 430, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n"};
      openmessage$34 = Py.newCode(2, var2, var1, "openmessage", 461, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "errors", "deleted", "n", "path", "commapath", "msg"};
      removemessages$35 = Py.newCode(2, var2, var1, "removemessages", 465, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "tofolder", "keepsequences", "errors", "refiled", "n", "ton", "path", "topath", "msg"};
      refilemessages$36 = Py.newCode(4, var2, var1, "refilemessages", 490, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fromfolder", "refileditems", "fromsequences", "tosequences", "changed", "name", "seq", "toseq", "new", "fromn", "ton"};
      _copysequences$37 = Py.newCode(3, var2, var1, "_copysequences", 525, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "tofolder", "ton", "path", "f", "topath", "backuptopath", "ok"};
      movemessage$38 = Py.newCode(4, var2, var1, "movemessage", 546, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "tofolder", "ton", "path", "f", "topath", "backuptopath", "ok"};
      copymessage$39 = Py.newCode(4, var2, var1, "copymessage", 578, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "n", "txt", "path", "backuppath", "ok", "BUFSIZE", "f", "buf"};
      createmessage$40 = Py.newCode(3, var2, var1, "createmessage", 604, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "sequences", "changed", "name", "seq", "n"};
      removefromallsequences$41 = Py.newCode(2, var2, var1, "removefromallsequences", 630, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getlast$42 = Py.newCode(1, var2, var1, "getlast", 649, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "last"};
      setlast$43 = Py.newCode(2, var2, var1, "setlast", 655, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Message$44 = Py.newCode(0, var2, var1, "Message", 663, false, false, self, 44, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "f", "n", "fp", "path"};
      __init__$45 = Py.newCode(4, var2, var1, "__init__", 665, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$46 = Py.newCode(1, var2, var1, "__repr__", 674, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pred", "headers", "hit", "line", "i"};
      getheadertext$47 = Py.newCode(2, var2, var1, "getheadertext", 678, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "decode", "encoding", "StringIO", "output"};
      getbodytext$48 = Py.newCode(2, var2, var1, "getbodytext", 695, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bdry", "mf", "parts", "n", "part"};
      getbodyparts$49 = Py.newCode(1, var2, var1, "getbodyparts", 712, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getbody$50 = Py.newCode(1, var2, var1, "getbody", 732, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SubMessage$51 = Py.newCode(0, var2, var1, "SubMessage", 740, false, false, self, 51, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "f", "n", "fp"};
      __init__$52 = Py.newCode(4, var2, var1, "__init__", 742, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "n", "fp"};
      __repr__$53 = Py.newCode(1, var2, var1, "__repr__", 752, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "decode"};
      getbodytext$54 = Py.newCode(2, var2, var1, "getbodytext", 757, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getbodyparts$55 = Py.newCode(1, var2, var1, "getbodyparts", 763, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getbody$56 = Py.newCode(1, var2, var1, "getbody", 767, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IntSet$57 = Py.newCode(0, var2, var1, "IntSet", 771, false, false, self, 57, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "data", "sep", "rng"};
      __init__$58 = Py.newCode(4, var2, var1, "__init__", 794, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$59 = Py.newCode(1, var2, var1, "reset", 800, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$60 = Py.newCode(2, var2, var1, "__cmp__", 803, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$61 = Py.newCode(1, var2, var1, "__hash__", 806, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$62 = Py.newCode(1, var2, var1, "__repr__", 809, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "alo", "ahi", "blo", "bhi"};
      normalize$63 = Py.newCode(1, var2, var1, "normalize", 812, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "lo", "hi", "t"};
      tostring$64 = Py.newCode(1, var2, var1, "tostring", 823, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "l", "lo", "hi", "m"};
      tolist$65 = Py.newCode(1, var2, var1, "tolist", 832, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list", "i"};
      fromlist$66 = Py.newCode(2, var2, var1, "fromlist", 839, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "new"};
      clone$67 = Py.newCode(1, var2, var1, "clone", 843, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      min$68 = Py.newCode(1, var2, var1, "min", 848, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      max$69 = Py.newCode(1, var2, var1, "max", 851, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "lo", "hi"};
      contains$70 = Py.newCode(2, var2, var1, "contains", 854, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "x", "i", "lo", "hi"};
      append$71 = Py.newCode(2, var2, var1, "append", 859, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "xlo", "xhi"};
      addpair$72 = Py.newCode(3, var2, var1, "addpair", 884, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "new", "part", "list", "subp", "s"};
      fromstring$73 = Py.newCode(2, var2, var1, "fromstring", 889, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "key", "casefold", "f", "pat", "prog", "line", "text"};
      pickline$74 = Py.newCode(3, var2, var1, "pickline", 908, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "key", "value", "casefold", "f", "lines", "pat", "prog", "newline", "i", "line", "tempfile"};
      updateline$75 = Py.newCode(4, var2, var1, "updateline", 928, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"do", "testfolders", "t", "seqs", "context", "seq", "msg", "stuff", "list"};
      test$76 = Py.newCode(0, var2, var1, "test", 962, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      do$77 = Py.newCode(1, var2, var1, "do", 966, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new mhlib$py("mhlib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(mhlib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this.MH$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.__repr__$4(var2, var3);
         case 5:
            return this.error$5(var2, var3);
         case 6:
            return this.getprofile$6(var2, var3);
         case 7:
            return this.getpath$7(var2, var3);
         case 8:
            return this.getcontext$8(var2, var3);
         case 9:
            return this.setcontext$9(var2, var3);
         case 10:
            return this.listfolders$10(var2, var3);
         case 11:
            return this.listsubfolders$11(var2, var3);
         case 12:
            return this.listallfolders$12(var2, var3);
         case 13:
            return this.listallsubfolders$13(var2, var3);
         case 14:
            return this.openfolder$14(var2, var3);
         case 15:
            return this.makefolder$15(var2, var3);
         case 16:
            return this.deletefolder$16(var2, var3);
         case 17:
            return this.isnumeric$17(var2, var3);
         case 18:
            return this.Folder$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.__repr__$20(var2, var3);
         case 21:
            return this.error$21(var2, var3);
         case 22:
            return this.getfullname$22(var2, var3);
         case 23:
            return this.getsequencesfilename$23(var2, var3);
         case 24:
            return this.getmessagefilename$24(var2, var3);
         case 25:
            return this.listsubfolders$25(var2, var3);
         case 26:
            return this.listallsubfolders$26(var2, var3);
         case 27:
            return this.listmessages$27(var2, var3);
         case 28:
            return this.getsequences$28(var2, var3);
         case 29:
            return this.putsequences$29(var2, var3);
         case 30:
            return this.getcurrent$30(var2, var3);
         case 31:
            return this.setcurrent$31(var2, var3);
         case 32:
            return this.parsesequence$32(var2, var3);
         case 33:
            return this._parseindex$33(var2, var3);
         case 34:
            return this.openmessage$34(var2, var3);
         case 35:
            return this.removemessages$35(var2, var3);
         case 36:
            return this.refilemessages$36(var2, var3);
         case 37:
            return this._copysequences$37(var2, var3);
         case 38:
            return this.movemessage$38(var2, var3);
         case 39:
            return this.copymessage$39(var2, var3);
         case 40:
            return this.createmessage$40(var2, var3);
         case 41:
            return this.removefromallsequences$41(var2, var3);
         case 42:
            return this.getlast$42(var2, var3);
         case 43:
            return this.setlast$43(var2, var3);
         case 44:
            return this.Message$44(var2, var3);
         case 45:
            return this.__init__$45(var2, var3);
         case 46:
            return this.__repr__$46(var2, var3);
         case 47:
            return this.getheadertext$47(var2, var3);
         case 48:
            return this.getbodytext$48(var2, var3);
         case 49:
            return this.getbodyparts$49(var2, var3);
         case 50:
            return this.getbody$50(var2, var3);
         case 51:
            return this.SubMessage$51(var2, var3);
         case 52:
            return this.__init__$52(var2, var3);
         case 53:
            return this.__repr__$53(var2, var3);
         case 54:
            return this.getbodytext$54(var2, var3);
         case 55:
            return this.getbodyparts$55(var2, var3);
         case 56:
            return this.getbody$56(var2, var3);
         case 57:
            return this.IntSet$57(var2, var3);
         case 58:
            return this.__init__$58(var2, var3);
         case 59:
            return this.reset$59(var2, var3);
         case 60:
            return this.__cmp__$60(var2, var3);
         case 61:
            return this.__hash__$61(var2, var3);
         case 62:
            return this.__repr__$62(var2, var3);
         case 63:
            return this.normalize$63(var2, var3);
         case 64:
            return this.tostring$64(var2, var3);
         case 65:
            return this.tolist$65(var2, var3);
         case 66:
            return this.fromlist$66(var2, var3);
         case 67:
            return this.clone$67(var2, var3);
         case 68:
            return this.min$68(var2, var3);
         case 69:
            return this.max$69(var2, var3);
         case 70:
            return this.contains$70(var2, var3);
         case 71:
            return this.append$71(var2, var3);
         case 72:
            return this.addpair$72(var2, var3);
         case 73:
            return this.fromstring$73(var2, var3);
         case 74:
            return this.pickline$74(var2, var3);
         case 75:
            return this.updateline$75(var2, var3);
         case 76:
            return this.test$76(var2, var3);
         case 77:
            return this.do$77(var2, var3);
         default:
            return null;
      }
   }
}
