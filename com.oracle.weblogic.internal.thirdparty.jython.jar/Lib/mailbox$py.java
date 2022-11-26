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
import org.python.core.PyFloat;
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
@Filename("mailbox.py")
public class mailbox$py extends PyFunctionTable implements PyRunnable {
   static mailbox$py self;
   static final PyCode f$0;
   static final PyCode Mailbox$1;
   static final PyCode __init__$2;
   static final PyCode add$3;
   static final PyCode remove$4;
   static final PyCode __delitem__$5;
   static final PyCode discard$6;
   static final PyCode __setitem__$7;
   static final PyCode get$8;
   static final PyCode __getitem__$9;
   static final PyCode get_message$10;
   static final PyCode get_string$11;
   static final PyCode get_file$12;
   static final PyCode iterkeys$13;
   static final PyCode keys$14;
   static final PyCode itervalues$15;
   static final PyCode __iter__$16;
   static final PyCode values$17;
   static final PyCode iteritems$18;
   static final PyCode items$19;
   static final PyCode has_key$20;
   static final PyCode __contains__$21;
   static final PyCode __len__$22;
   static final PyCode clear$23;
   static final PyCode pop$24;
   static final PyCode popitem$25;
   static final PyCode update$26;
   static final PyCode flush$27;
   static final PyCode lock$28;
   static final PyCode unlock$29;
   static final PyCode close$30;
   static final PyCode _dump_message$31;
   static final PyCode Maildir$32;
   static final PyCode __init__$33;
   static final PyCode add$34;
   static final PyCode remove$35;
   static final PyCode discard$36;
   static final PyCode __setitem__$37;
   static final PyCode get_message$38;
   static final PyCode get_string$39;
   static final PyCode get_file$40;
   static final PyCode iterkeys$41;
   static final PyCode has_key$42;
   static final PyCode __len__$43;
   static final PyCode flush$44;
   static final PyCode lock$45;
   static final PyCode unlock$46;
   static final PyCode close$47;
   static final PyCode list_folders$48;
   static final PyCode get_folder$49;
   static final PyCode add_folder$50;
   static final PyCode remove_folder$51;
   static final PyCode clean$52;
   static final PyCode _create_tmp$53;
   static final PyCode _refresh$54;
   static final PyCode _lookup$55;
   static final PyCode next$56;
   static final PyCode _singlefileMailbox$57;
   static final PyCode __init__$58;
   static final PyCode add$59;
   static final PyCode remove$60;
   static final PyCode __setitem__$61;
   static final PyCode iterkeys$62;
   static final PyCode has_key$63;
   static final PyCode __len__$64;
   static final PyCode lock$65;
   static final PyCode unlock$66;
   static final PyCode flush$67;
   static final PyCode _pre_mailbox_hook$68;
   static final PyCode _pre_message_hook$69;
   static final PyCode _post_message_hook$70;
   static final PyCode close$71;
   static final PyCode _lookup$72;
   static final PyCode _append_message$73;
   static final PyCode _mboxMMDF$74;
   static final PyCode get_message$75;
   static final PyCode get_string$76;
   static final PyCode get_file$77;
   static final PyCode _install_message$78;
   static final PyCode mbox$79;
   static final PyCode __init__$80;
   static final PyCode _post_message_hook$81;
   static final PyCode _generate_toc$82;
   static final PyCode MMDF$83;
   static final PyCode __init__$84;
   static final PyCode _pre_message_hook$85;
   static final PyCode _post_message_hook$86;
   static final PyCode _generate_toc$87;
   static final PyCode MH$88;
   static final PyCode __init__$89;
   static final PyCode add$90;
   static final PyCode remove$91;
   static final PyCode __setitem__$92;
   static final PyCode get_message$93;
   static final PyCode get_string$94;
   static final PyCode get_file$95;
   static final PyCode iterkeys$96;
   static final PyCode f$97;
   static final PyCode has_key$98;
   static final PyCode __len__$99;
   static final PyCode lock$100;
   static final PyCode unlock$101;
   static final PyCode flush$102;
   static final PyCode close$103;
   static final PyCode list_folders$104;
   static final PyCode get_folder$105;
   static final PyCode add_folder$106;
   static final PyCode remove_folder$107;
   static final PyCode get_sequences$108;
   static final PyCode f$109;
   static final PyCode set_sequences$110;
   static final PyCode pack$111;
   static final PyCode _dump_sequences$112;
   static final PyCode Babyl$113;
   static final PyCode __init__$114;
   static final PyCode add$115;
   static final PyCode remove$116;
   static final PyCode __setitem__$117;
   static final PyCode get_message$118;
   static final PyCode get_string$119;
   static final PyCode get_file$120;
   static final PyCode get_labels$121;
   static final PyCode _generate_toc$122;
   static final PyCode _pre_mailbox_hook$123;
   static final PyCode _pre_message_hook$124;
   static final PyCode _post_message_hook$125;
   static final PyCode _install_message$126;
   static final PyCode Message$127;
   static final PyCode __init__$128;
   static final PyCode _become_message$129;
   static final PyCode _explain_to$130;
   static final PyCode MaildirMessage$131;
   static final PyCode __init__$132;
   static final PyCode get_subdir$133;
   static final PyCode set_subdir$134;
   static final PyCode get_flags$135;
   static final PyCode set_flags$136;
   static final PyCode add_flag$137;
   static final PyCode remove_flag$138;
   static final PyCode get_date$139;
   static final PyCode set_date$140;
   static final PyCode get_info$141;
   static final PyCode set_info$142;
   static final PyCode _explain_to$143;
   static final PyCode _mboxMMDFMessage$144;
   static final PyCode __init__$145;
   static final PyCode get_from$146;
   static final PyCode set_from$147;
   static final PyCode get_flags$148;
   static final PyCode set_flags$149;
   static final PyCode add_flag$150;
   static final PyCode remove_flag$151;
   static final PyCode _explain_to$152;
   static final PyCode mboxMessage$153;
   static final PyCode MHMessage$154;
   static final PyCode __init__$155;
   static final PyCode get_sequences$156;
   static final PyCode set_sequences$157;
   static final PyCode add_sequence$158;
   static final PyCode remove_sequence$159;
   static final PyCode _explain_to$160;
   static final PyCode BabylMessage$161;
   static final PyCode __init__$162;
   static final PyCode get_labels$163;
   static final PyCode set_labels$164;
   static final PyCode add_label$165;
   static final PyCode remove_label$166;
   static final PyCode get_visible$167;
   static final PyCode set_visible$168;
   static final PyCode update_visible$169;
   static final PyCode _explain_to$170;
   static final PyCode MMDFMessage$171;
   static final PyCode _ProxyFile$172;
   static final PyCode __init__$173;
   static final PyCode read$174;
   static final PyCode readline$175;
   static final PyCode readlines$176;
   static final PyCode __iter__$177;
   static final PyCode tell$178;
   static final PyCode seek$179;
   static final PyCode close$180;
   static final PyCode _read$181;
   static final PyCode _PartialFile$182;
   static final PyCode __init__$183;
   static final PyCode tell$184;
   static final PyCode seek$185;
   static final PyCode _read$186;
   static final PyCode close$187;
   static final PyCode _lock_file$188;
   static final PyCode _unlock_file$189;
   static final PyCode _create_carefully$190;
   static final PyCode _create_temporary$191;
   static final PyCode _sync_flush$192;
   static final PyCode _sync_close$193;
   static final PyCode _Mailbox$194;
   static final PyCode __init__$195;
   static final PyCode __iter__$196;
   static final PyCode next$197;
   static final PyCode UnixMailbox$198;
   static final PyCode _search_start$199;
   static final PyCode _search_end$200;
   static final PyCode _strict_isrealfromline$201;
   static final PyCode _portable_isrealfromline$202;
   static final PyCode PortableUnixMailbox$203;
   static final PyCode MmdfMailbox$204;
   static final PyCode _search_start$205;
   static final PyCode _search_end$206;
   static final PyCode MHMailbox$207;
   static final PyCode __init__$208;
   static final PyCode __iter__$209;
   static final PyCode next$210;
   static final PyCode BabylMailbox$211;
   static final PyCode _search_start$212;
   static final PyCode _search_end$213;
   static final PyCode Error$214;
   static final PyCode NoSuchMailboxError$215;
   static final PyCode NotEmptyError$216;
   static final PyCode ExternalClashError$217;
   static final PyCode FormatError$218;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setglobal("__doc__", PyString.fromInterned("Read/write support for Maildir, mbox, MH, Babyl, and MMDF mailboxes."));
      var1.setline(3);
      PyString.fromInterned("Read/write support for Maildir, mbox, MH, Babyl, and MMDF mailboxes.");
      var1.setline(11);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(12);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(13);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("calendar", var1, -1);
      var1.setlocal("calendar", var3);
      var3 = null;
      var1.setline(15);
      var3 = imp.importOne("socket", var1, -1);
      var1.setlocal("socket", var3);
      var3 = null;
      var1.setline(16);
      var3 = imp.importOne("errno", var1, -1);
      var1.setlocal("errno", var3);
      var3 = null;
      var1.setline(17);
      var3 = imp.importOne("copy", var1, -1);
      var1.setlocal("copy", var3);
      var3 = null;
      var1.setline(18);
      var3 = imp.importOne("email", var1, -1);
      var1.setlocal("email", var3);
      var3 = null;
      var1.setline(19);
      var3 = imp.importOne("email.message", var1, -1);
      var1.setlocal("email", var3);
      var3 = null;
      var1.setline(20);
      var3 = imp.importOne("email.generator", var1, -1);
      var1.setlocal("email", var3);
      var3 = null;
      var1.setline(21);
      var3 = imp.importOne("StringIO", var1, -1);
      var1.setlocal("StringIO", var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(23);
         var3 = var1.getname("sys").__getattr__("platform");
         PyObject var10000 = var3._eq(PyString.fromInterned("os2emx"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(25);
            throw Py.makeException(var1.getname("ImportError"));
         }

         var1.setline(26);
         var3 = imp.importOne("fcntl", var1, -1);
         var1.setlocal("fcntl", var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(28);
         var4 = var1.getname("None");
         var1.setlocal("fcntl", var4);
         var4 = null;
      }

      var1.setline(30);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      ContextManager var8;
      var4 = (var8 = ContextGuard.getManager(var1.getname("warnings").__getattr__("catch_warnings").__call__(var2))).__enter__(var2);

      label33: {
         try {
            var1.setline(32);
            if (var1.getname("sys").__getattr__("py3kwarning").__nonzero__()) {
               var1.setline(33);
               var1.getname("warnings").__getattr__("filterwarnings").__call__((ThreadState)var2, PyString.fromInterned("ignore"), (PyObject)PyString.fromInterned(".*rfc822 has been removed"), (PyObject)var1.getname("DeprecationWarning"));
            }

            var1.setline(35);
            var4 = imp.importOne("rfc822", var1, -1);
            var1.setlocal("rfc822", var4);
            var4 = null;
         } catch (Throwable var5) {
            if (var8.__exit__(var2, Py.setException(var5, var1))) {
               break label33;
            }

            throw (Throwable)Py.makeException();
         }

         var8.__exit__(var2, (PyException)null);
      }

      var1.setline(37);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("Mailbox"), PyString.fromInterned("Maildir"), PyString.fromInterned("mbox"), PyString.fromInterned("MH"), PyString.fromInterned("Babyl"), PyString.fromInterned("MMDF"), PyString.fromInterned("Message"), PyString.fromInterned("MaildirMessage"), PyString.fromInterned("mboxMessage"), PyString.fromInterned("MHMessage"), PyString.fromInterned("BabylMessage"), PyString.fromInterned("MMDFMessage"), PyString.fromInterned("UnixMailbox"), PyString.fromInterned("PortableUnixMailbox"), PyString.fromInterned("MmdfMailbox"), PyString.fromInterned("MHMailbox"), PyString.fromInterned("BabylMailbox")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(42);
      PyObject[] var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Mailbox", var10, Mailbox$1);
      var1.setlocal("Mailbox", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(244);
      var10 = new PyObject[]{var1.getname("Mailbox")};
      var4 = Py.makeClass("Maildir", var10, Maildir$32);
      var1.setlocal("Maildir", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(558);
      var10 = new PyObject[]{var1.getname("Mailbox")};
      var4 = Py.makeClass("_singlefileMailbox", var10, _singlefileMailbox$57);
      var1.setlocal("_singlefileMailbox", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(754);
      var10 = new PyObject[]{var1.getname("_singlefileMailbox")};
      var4 = Py.makeClass("_mboxMMDF", var10, _mboxMMDF$74);
      var1.setlocal("_mboxMMDF", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(810);
      var10 = new PyObject[]{var1.getname("_mboxMMDF")};
      var4 = Py.makeClass("mbox", var10, mbox$79);
      var1.setlocal("mbox", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(862);
      var10 = new PyObject[]{var1.getname("_mboxMMDF")};
      var4 = Py.makeClass("MMDF", var10, MMDF$83);
      var1.setlocal("MMDF", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(907);
      var10 = new PyObject[]{var1.getname("Mailbox")};
      var4 = Py.makeClass("MH", var10, MH$88);
      var1.setlocal("MH", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1218);
      var10 = new PyObject[]{var1.getname("_singlefileMailbox")};
      var4 = Py.makeClass("Babyl", var10, Babyl$113);
      var1.setlocal("Babyl", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1439);
      var10 = new PyObject[]{var1.getname("email").__getattr__("message").__getattr__("Message")};
      var4 = Py.makeClass("Message", var10, Message$127);
      var1.setlocal("Message", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1471);
      var10 = new PyObject[]{var1.getname("Message")};
      var4 = Py.makeClass("MaildirMessage", var10, MaildirMessage$131);
      var1.setlocal("MaildirMessage", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1578);
      var10 = new PyObject[]{var1.getname("Message")};
      var4 = Py.makeClass("_mboxMMDFMessage", var10, _mboxMMDFMessage$144);
      var1.setlocal("_mboxMMDFMessage", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1689);
      var10 = new PyObject[]{var1.getname("_mboxMMDFMessage")};
      var4 = Py.makeClass("mboxMessage", var10, mboxMessage$153);
      var1.setlocal("mboxMessage", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1693);
      var10 = new PyObject[]{var1.getname("Message")};
      var4 = Py.makeClass("MHMessage", var10, MHMessage$154);
      var1.setlocal("MHMessage", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1763);
      var10 = new PyObject[]{var1.getname("Message")};
      var4 = Py.makeClass("BabylMessage", var10, BabylMessage$161);
      var1.setlocal("BabylMessage", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1856);
      var10 = new PyObject[]{var1.getname("_mboxMMDFMessage")};
      var4 = Py.makeClass("MMDFMessage", var10, MMDFMessage$171);
      var1.setlocal("MMDFMessage", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1860);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("_ProxyFile", var10, _ProxyFile$172);
      var1.setlocal("_ProxyFile", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1922);
      var10 = new PyObject[]{var1.getname("_ProxyFile")};
      var4 = Py.makeClass("_PartialFile", var10, _PartialFile$182);
      var1.setlocal("_PartialFile", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(1961);
      var10 = new PyObject[]{var1.getname("True")};
      PyFunction var11 = new PyFunction(var1.f_globals, var10, _lock_file$188, PyString.fromInterned("Lock file f using lockf and dot locking."));
      var1.setlocal("_lock_file", var11);
      var3 = null;
      var1.setline(2006);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, _unlock_file$189, PyString.fromInterned("Unlock file f using lockf and dot locking."));
      var1.setlocal("_unlock_file", var11);
      var3 = null;
      var1.setline(2013);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, _create_carefully$190, PyString.fromInterned("Create a file if it doesn't exist and open for reading and writing."));
      var1.setlocal("_create_carefully", var11);
      var3 = null;
      var1.setline(2021);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, _create_temporary$191, PyString.fromInterned("Create a temp file based on path and open for reading and writing."));
      var1.setlocal("_create_temporary", var11);
      var3 = null;
      var1.setline(2027);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, _sync_flush$192, PyString.fromInterned("Ensure changes to file f are physically on disk."));
      var1.setlocal("_sync_flush", var11);
      var3 = null;
      var1.setline(2033);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, _sync_close$193, PyString.fromInterned("Close file f, ensuring all changes are physically on disk."));
      var1.setlocal("_sync_close", var11);
      var3 = null;
      var1.setline(2043);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("_Mailbox", var10, _Mailbox$194);
      var1.setlocal("_Mailbox", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2069);
      var10 = new PyObject[]{var1.getname("_Mailbox")};
      var4 = Py.makeClass("UnixMailbox", var10, UnixMailbox$198);
      var1.setlocal("UnixMailbox", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2135);
      var10 = new PyObject[]{var1.getname("UnixMailbox")};
      var4 = Py.makeClass("PortableUnixMailbox", var10, PortableUnixMailbox$203);
      var1.setlocal("PortableUnixMailbox", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2139);
      var10 = new PyObject[]{var1.getname("_Mailbox")};
      var4 = Py.makeClass("MmdfMailbox", var10, MmdfMailbox$204);
      var1.setlocal("MmdfMailbox", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2160);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("MHMailbox", var10, MHMailbox$207);
      var1.setlocal("MHMailbox", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2194);
      var10 = new PyObject[]{var1.getname("_Mailbox")};
      var4 = Py.makeClass("BabylMailbox", var10, BabylMailbox$211);
      var1.setlocal("BabylMailbox", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2217);
      var10 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Error", var10, Error$214);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2220);
      var10 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("NoSuchMailboxError", var10, NoSuchMailboxError$215);
      var1.setlocal("NoSuchMailboxError", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2223);
      var10 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("NotEmptyError", var10, NotEmptyError$216);
      var1.setlocal("NotEmptyError", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2226);
      var10 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("ExternalClashError", var10, ExternalClashError$217);
      var1.setlocal("ExternalClashError", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(2229);
      var10 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("FormatError", var10, FormatError$218);
      var1.setlocal("FormatError", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Mailbox$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A group of messages in a particular place."));
      var1.setline(43);
      PyString.fromInterned("A group of messages in a particular place.");
      var1.setline(45);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Initialize a Mailbox instance."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(50);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add$3, PyString.fromInterned("Add message and return assigned key."));
      var1.setlocal("add", var4);
      var3 = null;
      var1.setline(54);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove$4, PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("remove", var4);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __delitem__$5, (PyObject)null);
      var1.setlocal("__delitem__", var4);
      var3 = null;
      var1.setline(61);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, discard$6, PyString.fromInterned("If the keyed message exists, remove it."));
      var1.setlocal("discard", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$7, PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(72);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get$8, PyString.fromInterned("Return the keyed message, or default if it doesn't exist."));
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$9, PyString.fromInterned("Return the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_message$10, PyString.fromInterned("Return a Message representation or raise a KeyError."));
      var1.setlocal("get_message", var4);
      var3 = null;
      var1.setline(90);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_string$11, PyString.fromInterned("Return a string representation or raise a KeyError."));
      var1.setlocal("get_string", var4);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_file$12, PyString.fromInterned("Return a file-like representation or raise a KeyError."));
      var1.setlocal("get_file", var4);
      var3 = null;
      var1.setline(98);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterkeys$13, PyString.fromInterned("Return an iterator over keys."));
      var1.setlocal("iterkeys", var4);
      var3 = null;
      var1.setline(102);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$14, PyString.fromInterned("Return a list of keys."));
      var1.setlocal("keys", var4);
      var3 = null;
      var1.setline(106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, itervalues$15, PyString.fromInterned("Return an iterator over all messages."));
      var1.setlocal("itervalues", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$16, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(118);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, values$17, PyString.fromInterned("Return a list of messages. Memory intensive."));
      var1.setlocal("values", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iteritems$18, PyString.fromInterned("Return an iterator over (key, message) tuples."));
      var1.setlocal("iteritems", var4);
      var3 = null;
      var1.setline(131);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$19, PyString.fromInterned("Return a list of (key, message) tuples. Memory intensive."));
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(135);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$20, PyString.fromInterned("Return True if the keyed message exists, False otherwise."));
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(139);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$21, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$22, PyString.fromInterned("Return a count of messages in the mailbox."));
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(146);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear$23, PyString.fromInterned("Delete all messages."));
      var1.setlocal("clear", var4);
      var3 = null;
      var1.setline(151);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, pop$24, PyString.fromInterned("Delete the keyed message and return it, or default."));
      var1.setlocal("pop", var4);
      var3 = null;
      var1.setline(160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, popitem$25, PyString.fromInterned("Delete an arbitrary (key, message) pair and return it."));
      var1.setlocal("popitem", var4);
      var3 = null;
      var1.setline(167);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, update$26, PyString.fromInterned("Change the messages that correspond to certain keys."));
      var1.setlocal("update", var4);
      var3 = null;
      var1.setline(184);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$27, PyString.fromInterned("Write any pending changes to the disk."));
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(188);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lock$28, PyString.fromInterned("Lock the mailbox."));
      var1.setlocal("lock", var4);
      var3 = null;
      var1.setline(192);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unlock$29, PyString.fromInterned("Unlock the mailbox if it is locked."));
      var1.setlocal("unlock", var4);
      var3 = null;
      var1.setline(196);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$30, PyString.fromInterned("Flush and close the mailbox."));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(201);
      PyObject var5 = var1.getname("False");
      var1.setlocal("_append_newline", var5);
      var3 = null;
      var1.setline(203);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, _dump_message$31, PyString.fromInterned("Dump message contents to target file."));
      var1.setlocal("_dump_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyString.fromInterned("Initialize a Mailbox instance.");
      var1.setline(47);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__(var2, var1.getlocal(1)));
      var1.getlocal(0).__setattr__("_path", var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_factory", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add$3(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyString.fromInterned("Add message and return assigned key.");
      var1.setline(52);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject remove$4(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(56);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject __delitem__$5(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      var1.getlocal(0).__getattr__("remove").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject discard$6(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyString.fromInterned("If the keyed message exists, remove it.");

      try {
         var1.setline(64);
         var1.getlocal(0).__getattr__("remove").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(66);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$7(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(70);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject get$8(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyString.fromInterned("Return the keyed message, or default if it doesn't exist.");

      PyObject var3;
      try {
         var1.setline(75);
         var3 = var1.getlocal(0).__getattr__("__getitem__").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(77);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject __getitem__$9(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyString.fromInterned("Return the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(81);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_factory").__not__().__nonzero__()) {
         var1.setline(82);
         var3 = var1.getlocal(0).__getattr__("get_message").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(84);
         var3 = var1.getlocal(0).__getattr__("_factory").__call__(var2, var1.getlocal(0).__getattr__("get_file").__call__(var2, var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_message$10(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyString.fromInterned("Return a Message representation or raise a KeyError.");
      var1.setline(88);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject get_string$11(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyString.fromInterned("Return a string representation or raise a KeyError.");
      var1.setline(92);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject get_file$12(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyString.fromInterned("Return a file-like representation or raise a KeyError.");
      var1.setline(96);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject iterkeys$13(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyString.fromInterned("Return an iterator over keys.");
      var1.setline(100);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject keys$14(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyString.fromInterned("Return a list of keys.");
      var1.setline(104);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("iterkeys").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject itervalues$15(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var9;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(107);
            PyString.fromInterned("Return an iterator over all messages.");
            var1.setline(108);
            var3 = var1.getlocal(0).__getattr__("iterkeys").__call__(var2).__iter__();
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

      while(true) {
         var1.setline(108);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);

         try {
            var1.setline(110);
            PyObject var8 = var1.getlocal(0).__getitem__(var1.getlocal(1));
            var1.setlocal(2, var8);
            var5 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("KeyError"))) {
               continue;
            }

            throw var7;
         }

         var1.setline(113);
         var1.setline(113);
         var9 = var1.getlocal(2);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var5;
         return var9;
      }
   }

   public PyObject __iter__$16(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyObject var3 = var1.getlocal(0).__getattr__("itervalues").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject values$17(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyString.fromInterned("Return a list of messages. Memory intensive.");
      var1.setline(120);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("itervalues").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iteritems$18(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(123);
            PyString.fromInterned("Return an iterator over (key, message) tuples.");
            var1.setline(124);
            var3 = var1.getlocal(0).__getattr__("iterkeys").__call__(var2).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            PyObject var10 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(124);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);

         try {
            var1.setline(126);
            PyObject var8 = var1.getlocal(0).__getitem__(var1.getlocal(1));
            var1.setlocal(2, var8);
            var5 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("KeyError"))) {
               continue;
            }

            throw var7;
         }

         var1.setline(129);
         var1.setline(129);
         PyObject[] var9 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
         PyTuple var11 = new PyTuple(var9);
         Arrays.fill(var9, (Object)null);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var5;
         return var11;
      }
   }

   public PyObject items$19(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyString.fromInterned("Return a list of (key, message) tuples. Memory intensive.");
      var1.setline(133);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("iteritems").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_key$20(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyString.fromInterned("Return True if the keyed message exists, False otherwise.");
      var1.setline(137);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject __contains__$21(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyObject var3 = var1.getlocal(0).__getattr__("has_key").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$22(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString.fromInterned("Return a count of messages in the mailbox.");
      var1.setline(144);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject clear$23(PyFrame var1, ThreadState var2) {
      var1.setline(147);
      PyString.fromInterned("Delete all messages.");
      var1.setline(148);
      PyObject var3 = var1.getlocal(0).__getattr__("iterkeys").__call__(var2).__iter__();

      while(true) {
         var1.setline(148);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(149);
         var1.getlocal(0).__getattr__("discard").__call__(var2, var1.getlocal(1));
      }
   }

   public PyObject pop$24(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyString.fromInterned("Delete the keyed message and return it, or default.");

      PyException var3;
      PyObject var4;
      try {
         var1.setline(154);
         PyObject var6 = var1.getlocal(0).__getitem__(var1.getlocal(1));
         var1.setlocal(3, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(156);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(157);
      var1.getlocal(0).__getattr__("discard").__call__(var2, var1.getlocal(1));
      var1.setline(158);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject popitem$25(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyString.fromInterned("Delete an arbitrary (key, message) pair and return it.");
      var1.setline(162);
      PyObject var3 = var1.getlocal(0).__getattr__("iterkeys").__call__(var2).__iter__();
      var1.setline(162);
      PyObject var4 = var3.__iternext__();
      if (var4 == null) {
         var1.setline(165);
         throw Py.makeException(var1.getglobal("KeyError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No messages in mailbox")));
      } else {
         var1.setlocal(1, var4);
         var1.setline(163);
         PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("pop").__call__(var2, var1.getlocal(1))});
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject update$26(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyString.fromInterned("Change the messages that correspond to certain keys.");
      var1.setline(169);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("iteritems")).__nonzero__()) {
         var1.setline(170);
         var3 = var1.getlocal(1).__getattr__("iteritems").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(171);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("items")).__nonzero__()) {
            var1.setline(172);
            var3 = var1.getlocal(1).__getattr__("items").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(174);
            var3 = var1.getlocal(1);
            var1.setlocal(2, var3);
            var3 = null;
         }
      }

      var1.setline(175);
      var3 = var1.getglobal("False");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(176);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(176);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(181);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(182);
               throw Py.makeException(var1.getglobal("KeyError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("No message with key(s)")));
            } else {
               var1.f_lasti = -1;
               return Py.None;
            }
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;

         try {
            var1.setline(178);
            PyObject var9 = var1.getlocal(5);
            var1.getlocal(0).__setitem__(var1.getlocal(4), var9);
            var5 = null;
         } catch (Throwable var7) {
            PyException var8 = Py.setException(var7, var1);
            if (!var8.match(var1.getglobal("KeyError"))) {
               throw var8;
            }

            var1.setline(180);
            var6 = var1.getglobal("True");
            var1.setlocal(3, var6);
            var6 = null;
         }
      }
   }

   public PyObject flush$27(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyString.fromInterned("Write any pending changes to the disk.");
      var1.setline(186);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject lock$28(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyString.fromInterned("Lock the mailbox.");
      var1.setline(190);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject unlock$29(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyString.fromInterned("Unlock the mailbox if it is locked.");
      var1.setline(194);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject close$30(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyString.fromInterned("Flush and close the mailbox.");
      var1.setline(198);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Method must be implemented by subclass")));
   }

   public PyObject _dump_message$31(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyString.fromInterned("Dump message contents to target file.");
      var1.setline(208);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("email").__getattr__("message").__getattr__("Message")).__nonzero__()) {
         var1.setline(209);
         var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(210);
         var3 = var1.getglobal("email").__getattr__("generator").__getattr__("Generator").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(0));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(211);
         var1.getlocal(5).__getattr__("flatten").__call__(var2, var1.getlocal(1));
         var1.setline(212);
         var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(213);
         var3 = var1.getlocal(4).__getattr__("read").__call__(var2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(214);
         var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(6));
         var1.setline(215);
         var10000 = var1.getlocal(0).__getattr__("_append_newline");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(6).__getattr__("endswith").__call__(var2, var1.getglobal("os").__getattr__("linesep")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(217);
            var1.getlocal(2).__getattr__("write").__call__(var2, var1.getglobal("os").__getattr__("linesep"));
         }
      } else {
         var1.setline(218);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
            var1.setline(219);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(220);
               var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nFrom "), (PyObject)PyString.fromInterned("\n>From "));
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(221);
            var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep"));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(222);
            var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(1));
            var1.setline(223);
            var10000 = var1.getlocal(0).__getattr__("_append_newline");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(1).__getattr__("endswith").__call__(var2, var1.getglobal("os").__getattr__("linesep")).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(225);
               var1.getlocal(2).__getattr__("write").__call__(var2, var1.getglobal("os").__getattr__("linesep"));
            }
         } else {
            var1.setline(226);
            if (!var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("read")).__nonzero__()) {
               var1.setline(241);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Invalid message type: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
            }

            var1.setline(227);
            var3 = var1.getglobal("None");
            var1.setlocal(7, var3);
            var3 = null;

            while(true) {
               var1.setline(228);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(229);
               var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(230);
               var3 = var1.getlocal(8);
               var10000 = var3._eq(PyString.fromInterned(""));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(232);
               var10000 = var1.getlocal(3);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(8).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From "));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(233);
                  var3 = PyString.fromInterned(">From ")._add(var1.getlocal(8).__getslice__(Py.newInteger(5), (PyObject)null, (PyObject)null));
                  var1.setlocal(8, var3);
                  var3 = null;
               }

               var1.setline(234);
               var3 = var1.getlocal(8).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep"));
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(235);
               var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(8));
               var1.setline(236);
               var3 = var1.getlocal(8);
               var1.setlocal(7, var3);
               var3 = null;
            }

            var1.setline(237);
            var10000 = var1.getlocal(0).__getattr__("_append_newline");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(7);
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(7).__getattr__("endswith").__call__(var2, var1.getglobal("os").__getattr__("linesep")).__not__();
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(239);
               var1.getlocal(2).__getattr__("write").__call__(var2, var1.getglobal("os").__getattr__("linesep"));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Maildir$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A qmail-style Maildir mailbox."));
      var1.setline(245);
      PyString.fromInterned("A qmail-style Maildir mailbox.");
      var1.setline(247);
      PyString var3 = PyString.fromInterned(":");
      var1.setlocal("colon", var3);
      var3 = null;
      var1.setline(249);
      PyObject[] var4 = new PyObject[]{var1.getname("rfc822").__getattr__("Message"), var1.getname("True")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$33, PyString.fromInterned("Initialize a Maildir instance."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(269);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, add$34, PyString.fromInterned("Add message and return assigned key."));
      var1.setlocal("add", var5);
      var3 = null;
      var1.setline(306);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, remove$35, PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("remove", var5);
      var3 = null;
      var1.setline(310);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, discard$36, PyString.fromInterned("If the keyed message exists, remove it."));
      var1.setlocal("discard", var5);
      var3 = null;
      var1.setline(321);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setitem__$37, PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("__setitem__", var5);
      var3 = null;
      var1.setline(344);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_message$38, PyString.fromInterned("Return a Message representation or raise a KeyError."));
      var1.setlocal("get_message", var5);
      var3 = null;
      var1.setline(362);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_string$39, PyString.fromInterned("Return a string representation or raise a KeyError."));
      var1.setlocal("get_string", var5);
      var3 = null;
      var1.setline(370);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_file$40, PyString.fromInterned("Return a file-like representation or raise a KeyError."));
      var1.setlocal("get_file", var5);
      var3 = null;
      var1.setline(375);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, iterkeys$41, PyString.fromInterned("Return an iterator over keys."));
      var1.setlocal("iterkeys", var5);
      var3 = null;
      var1.setline(385);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, has_key$42, PyString.fromInterned("Return True if the keyed message exists, False otherwise."));
      var1.setlocal("has_key", var5);
      var3 = null;
      var1.setline(390);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __len__$43, PyString.fromInterned("Return a count of messages in the mailbox."));
      var1.setlocal("__len__", var5);
      var3 = null;
      var1.setline(395);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, flush$44, PyString.fromInterned("Write any pending changes to disk."));
      var1.setlocal("flush", var5);
      var3 = null;
      var1.setline(401);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, lock$45, PyString.fromInterned("Lock the mailbox."));
      var1.setlocal("lock", var5);
      var3 = null;
      var1.setline(405);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, unlock$46, PyString.fromInterned("Unlock the mailbox if it is locked."));
      var1.setlocal("unlock", var5);
      var3 = null;
      var1.setline(409);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, close$47, PyString.fromInterned("Flush and close the mailbox."));
      var1.setlocal("close", var5);
      var3 = null;
      var1.setline(413);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, list_folders$48, PyString.fromInterned("Return a list of folder names."));
      var1.setlocal("list_folders", var5);
      var3 = null;
      var1.setline(422);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_folder$49, PyString.fromInterned("Return a Maildir instance for the named folder."));
      var1.setlocal("get_folder", var5);
      var3 = null;
      var1.setline(428);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, add_folder$50, PyString.fromInterned("Create a folder and return a Maildir instance representing it."));
      var1.setlocal("add_folder", var5);
      var3 = null;
      var1.setline(438);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, remove_folder$51, PyString.fromInterned("Delete the named folder, which must be empty."));
      var1.setlocal("remove_folder", var5);
      var3 = null;
      var1.setline(457);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, clean$52, PyString.fromInterned("Delete old files in \"tmp\"."));
      var1.setlocal("clean", var5);
      var3 = null;
      var1.setline(465);
      PyInteger var6 = Py.newInteger(1);
      var1.setlocal("_count", var6);
      var3 = null;
      var1.setline(467);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _create_tmp$53, PyString.fromInterned("Create a file in the tmp subdirectory and open and return it."));
      var1.setlocal("_create_tmp", var5);
      var3 = null;
      var1.setline(495);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _refresh$54, PyString.fromInterned("Update table of contents mapping."));
      var1.setlocal("_refresh", var5);
      var3 = null;
      var1.setline(531);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _lookup$55, PyString.fromInterned("Use TOC to return subpath for given key, or raise a KeyError."));
      var1.setlocal("_lookup", var5);
      var3 = null;
      var1.setline(545);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, next$56, PyString.fromInterned("Return the next message in a one-time iteration."));
      var1.setlocal("next", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$33(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      PyString.fromInterned("Initialize a Maildir instance.");
      var1.setline(251);
      var1.getglobal("Mailbox").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(252);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("tmp"), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("tmp")), PyString.fromInterned("new"), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("new")), PyString.fromInterned("cur"), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("cur"))});
      var1.getlocal(0).__setattr__((String)"_paths", var3);
      var3 = null;
      var1.setline(257);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0).__getattr__("_path")).__not__().__nonzero__()) {
         var1.setline(258);
         if (!var1.getlocal(3).__nonzero__()) {
            var1.setline(263);
            throw Py.makeException(var1.getglobal("NoSuchMailboxError").__call__(var2, var1.getlocal(0).__getattr__("_path")));
         }

         var1.setline(259);
         var1.getglobal("os").__getattr__("mkdir").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)Py.newInteger(448));
         var1.setline(260);
         PyObject var5 = var1.getlocal(0).__getattr__("_paths").__getattr__("values").__call__(var2).__iter__();

         while(true) {
            var1.setline(260);
            PyObject var4 = var5.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(261);
            var1.getglobal("os").__getattr__("mkdir").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(448));
         }
      }

      var1.setline(264);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_toc", var3);
      var3 = null;
      var1.setline(265);
      var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("cur"), Py.newInteger(0), PyString.fromInterned("new"), Py.newInteger(0)});
      var1.getlocal(0).__setattr__((String)"_toc_mtimes", var3);
      var3 = null;
      var1.setline(266);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_last_read", var6);
      var3 = null;
      var1.setline(267);
      PyFloat var7 = Py.newFloat(0.1);
      var1.getlocal(0).__setattr__((String)"_skewfactor", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add$34(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      PyString.fromInterned("Add message and return assigned key.");
      var1.setline(271);
      PyObject var3 = var1.getlocal(0).__getattr__("_create_tmp").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;

      PyException var7;
      try {
         var1.setline(273);
         var1.getlocal(0).__getattr__("_dump_message").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      } catch (Throwable var6) {
         var7 = Py.setException(var6, var1);
         if (var7.match(var1.getglobal("BaseException"))) {
            var1.setline(275);
            var1.getlocal(2).__getattr__("close").__call__(var2);
            var1.setline(276);
            var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(2).__getattr__("name"));
            var1.setline(277);
            throw Py.makeException();
         }

         throw var7;
      }

      var1.setline(278);
      var1.getglobal("_sync_close").__call__(var2, var1.getlocal(2));
      var1.setline(279);
      PyObject var10000;
      PyString var8;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MaildirMessage")).__nonzero__()) {
         var1.setline(280);
         var3 = var1.getlocal(1).__getattr__("get_subdir").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(281);
         var3 = var1.getlocal(0).__getattr__("colon")._add(var1.getlocal(1).__getattr__("get_info").__call__(var2));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(282);
         var3 = var1.getlocal(4);
         var10000 = var3._eq(var1.getlocal(0).__getattr__("colon"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(283);
            var8 = PyString.fromInterned("");
            var1.setlocal(4, var8);
            var3 = null;
         }
      } else {
         var1.setline(285);
         var8 = PyString.fromInterned("new");
         var1.setlocal(3, var8);
         var3 = null;
         var1.setline(286);
         var8 = PyString.fromInterned("");
         var1.setlocal(4, var8);
         var3 = null;
      }

      var1.setline(287);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(2).__getattr__("name")).__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("colon")).__getitem__(Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(288);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(3), var1.getlocal(5)._add(var1.getlocal(4)));
      var1.setlocal(6, var3);
      var3 = null;

      try {
         var1.setline(290);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("link")).__nonzero__()) {
            var1.setline(291);
            var1.getglobal("os").__getattr__("link").__call__(var2, var1.getlocal(2).__getattr__("name"), var1.getlocal(6));
            var1.setline(292);
            var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(2).__getattr__("name"));
         } else {
            var1.setline(294);
            var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(2).__getattr__("name"), var1.getlocal(6));
         }
      } catch (Throwable var5) {
         var7 = Py.setException(var5, var1);
         if (var7.match(var1.getglobal("OSError"))) {
            PyObject var4 = var7.value;
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(296);
            var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(2).__getattr__("name"));
            var1.setline(297);
            var4 = var1.getlocal(7).__getattr__("errno");
            var10000 = var4._eq(var1.getglobal("errno").__getattr__("EEXIST"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(298);
               throw Py.makeException(var1.getglobal("ExternalClashError").__call__(var2, PyString.fromInterned("Name clash with existing message: %s")._mod(var1.getlocal(6))));
            }

            var1.setline(301);
            throw Py.makeException();
         }

         throw var7;
      }

      var1.setline(302);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MaildirMessage")).__nonzero__()) {
         var1.setline(303);
         var1.getglobal("os").__getattr__("utime").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("getatime").__call__(var2, var1.getlocal(6)), var1.getlocal(1).__getattr__("get_date").__call__(var2)})));
      }

      var1.setline(304);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove$35(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(308);
      var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject discard$36(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyString.fromInterned("If the keyed message exists, remove it.");

      try {
         var1.setline(314);
         var1.getlocal(0).__getattr__("remove").__call__(var2, var1.getlocal(1));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(316);
         } else {
            if (!var3.match(var1.getglobal("OSError"))) {
               throw var3;
            }

            PyObject var4 = var3.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(318);
            var4 = var1.getlocal(2).__getattr__("errno");
            PyObject var10000 = var4._ne(var1.getglobal("errno").__getattr__("ENOENT"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(319);
               throw Py.makeException();
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$37(PyFrame var1, ThreadState var2) {
      var1.setline(322);
      PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(323);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(324);
      var3 = var1.getlocal(0).__getattr__("add").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(325);
      var3 = var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(326);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("MaildirMessage")).__nonzero__()) {
         var1.setline(328);
         var3 = var1.getlocal(5);
         var1.setlocal(6, var3);
         var3 = null;
      } else {
         var1.setline(331);
         var3 = var1.getlocal(3);
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(332);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(6));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(333);
      var3 = var1.getlocal(0).__getattr__("colon");
      PyObject var10000 = var3._in(var1.getlocal(6));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(334);
         var3 = var1.getlocal(0).__getattr__("colon")._add(var1.getlocal(6).__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("colon")).__getitem__(Py.newInteger(-1)));
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(336);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(8, var4);
         var3 = null;
      }

      var1.setline(337);
      var1.getlocal(0).__getattr__("discard").__call__(var2, var1.getlocal(1));
      var1.setline(338);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(7), var1.getlocal(1)._add(var1.getlocal(8)));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(339);
      var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(5)), var1.getlocal(9));
      var1.setline(340);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("MaildirMessage")).__nonzero__()) {
         var1.setline(341);
         var1.getglobal("os").__getattr__("utime").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("getatime").__call__(var2, var1.getlocal(9)), var1.getlocal(2).__getattr__("get_date").__call__(var2)})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_message$38(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      PyString.fromInterned("Return a Message representation or raise a KeyError.");
      var1.setline(346);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(347);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(2)), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(349);
         PyObject var4;
         if (var1.getlocal(0).__getattr__("_factory").__nonzero__()) {
            var1.setline(350);
            var4 = var1.getlocal(0).__getattr__("_factory").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var4);
            var4 = null;
         } else {
            var1.setline(352);
            var4 = var1.getglobal("MaildirMessage").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var4);
            var4 = null;
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(354);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(354);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(355);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(2));
      PyObject[] var7 = Py.unpackSequence(var3, 2);
      PyObject var5 = var7[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(356);
      var1.getlocal(4).__getattr__("set_subdir").__call__(var2, var1.getlocal(5));
      var1.setline(357);
      var3 = var1.getlocal(0).__getattr__("colon");
      PyObject var10000 = var3._in(var1.getlocal(6));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(358);
         var1.getlocal(4).__getattr__("set_info").__call__(var2, var1.getlocal(6).__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("colon")).__getitem__(Py.newInteger(-1)));
      }

      var1.setline(359);
      var1.getlocal(4).__getattr__("set_date").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("getmtime").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(2))));
      var1.setline(360);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_string$39(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      PyString.fromInterned("Return a string representation or raise a KeyError.");
      var1.setline(364);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1))), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      Throwable var10000;
      label25: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(366);
            var4 = var1.getlocal(2).__getattr__("read").__call__(var2);
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label25;
         }

         var1.setline(368);
         var1.getlocal(2).__getattr__("close").__call__(var2);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      Throwable var7 = var10000;
      Py.addTraceback(var7, var1);
      var1.setline(368);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      throw (Throwable)var7;
   }

   public PyObject get_file$40(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      PyString.fromInterned("Return a file-like representation or raise a KeyError.");
      var1.setline(372);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1))), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(373);
      var3 = var1.getglobal("_ProxyFile").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject iterkeys$41(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(376);
            PyString.fromInterned("Return an iterator over keys.");
            var1.setline(377);
            var1.getlocal(0).__getattr__("_refresh").__call__(var2);
            var1.setline(378);
            var3 = var1.getlocal(0).__getattr__("_toc").__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(378);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);

         try {
            var1.setline(380);
            var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("KeyError"))) {
               continue;
            }

            throw var7;
         }

         var1.setline(383);
         var1.setline(383);
         var8 = var1.getlocal(1);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4, null};
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject has_key$42(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyString.fromInterned("Return True if the keyed message exists, False otherwise.");
      var1.setline(387);
      var1.getlocal(0).__getattr__("_refresh").__call__(var2);
      var1.setline(388);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_toc"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$43(PyFrame var1, ThreadState var2) {
      var1.setline(391);
      PyString.fromInterned("Return a count of messages in the mailbox.");
      var1.setline(392);
      var1.getlocal(0).__getattr__("_refresh").__call__(var2);
      var1.setline(393);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_toc"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject flush$44(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyString.fromInterned("Write any pending changes to disk.");
      var1.setline(399);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject lock$45(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyString.fromInterned("Lock the mailbox.");
      var1.setline(403);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unlock$46(PyFrame var1, ThreadState var2) {
      var1.setline(406);
      PyString.fromInterned("Unlock the mailbox if it is locked.");
      var1.setline(407);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$47(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      PyString.fromInterned("Flush and close the mailbox.");
      var1.setline(411);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject list_folders$48(PyFrame var1, ThreadState var2) {
      var1.setline(414);
      PyString.fromInterned("Return a list of folder names.");
      var1.setline(415);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(416);
      PyObject var6 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0).__getattr__("_path")).__iter__();

      while(true) {
         var1.setline(416);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(420);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(417);
         PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         PyObject var10000 = var5._gt(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(PyString.fromInterned("."));
            var5 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(2)));
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(419);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
         }
      }
   }

   public PyObject get_folder$49(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyString.fromInterned("Return a Maildir instance for the named folder.");
      var1.setline(424);
      PyObject var10000 = var1.getglobal("Maildir");
      PyObject[] var3 = new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), PyString.fromInterned(".")._add(var1.getlocal(1))), var1.getlocal(0).__getattr__("_factory"), var1.getglobal("False")};
      String[] var4 = new String[]{"factory", "create"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject add_folder$50(PyFrame var1, ThreadState var2) {
      var1.setline(429);
      PyString.fromInterned("Create a folder and return a Maildir instance representing it.");
      var1.setline(430);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), PyString.fromInterned(".")._add(var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(431);
      PyObject var10000 = var1.getglobal("Maildir");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("_factory")};
      String[] var4 = new String[]{"factory"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(432);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("maildirfolder"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(433);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
         var1.setline(434);
         var1.getglobal("os").__getattr__("close").__call__(var2, var1.getglobal("os").__getattr__("open").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)var1.getglobal("os").__getattr__("O_CREAT")._or(var1.getglobal("os").__getattr__("O_WRONLY")), (PyObject)Py.newInteger(438)));
      }

      var1.setline(436);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove_folder$51(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      PyString.fromInterned("Delete the named folder, which must be empty.");
      var1.setline(440);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), PyString.fromInterned(".")._add(var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(441);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("new")))._add(var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("cur")))).__iter__();

      PyObject var10000;
      do {
         var1.setline(441);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(445);
            var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2)).__iter__();

            do {
               var1.setline(445);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(450);
                  var10000 = var1.getglobal("os").__getattr__("walk");
                  PyObject[] var7 = new PyObject[]{var1.getlocal(2), var1.getglobal("False")};
                  String[] var8 = new String[]{"topdown"};
                  var10000 = var10000.__call__(var2, var7, var8);
                  var3 = null;
                  var3 = var10000.__iter__();

                  label48:
                  while(true) {
                     var1.setline(450);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(455);
                        var1.getglobal("os").__getattr__("rmdir").__call__(var2, var1.getlocal(2));
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     PyObject[] var9 = Py.unpackSequence(var4, 3);
                     PyObject var6 = var9[0];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var6 = var9[1];
                     var1.setlocal(5, var6);
                     var6 = null;
                     var6 = var9[2];
                     var1.setlocal(6, var6);
                     var6 = null;
                     var1.setline(451);
                     var5 = var1.getlocal(6).__iter__();

                     while(true) {
                        var1.setline(451);
                        var6 = var5.__iternext__();
                        if (var6 == null) {
                           var1.setline(453);
                           var5 = var1.getlocal(5).__iter__();

                           while(true) {
                              var1.setline(453);
                              var6 = var5.__iternext__();
                              if (var6 == null) {
                                 continue label48;
                              }

                              var1.setlocal(3, var6);
                              var1.setline(454);
                              var1.getglobal("os").__getattr__("rmdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(3)));
                           }
                        }

                        var1.setlocal(3, var6);
                        var1.setline(452);
                        var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(3)));
                     }
                  }
               }

               var1.setlocal(3, var4);
               var1.setline(446);
               var5 = var1.getlocal(3);
               var10000 = var5._ne(PyString.fromInterned("new"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(3);
                  var10000 = var5._ne(PyString.fromInterned("cur"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var5 = var1.getlocal(3);
                     var10000 = var5._ne(PyString.fromInterned("tmp"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
                     }
                  }
               }
            } while(!var10000.__nonzero__());

            var1.setline(448);
            throw Py.makeException(var1.getglobal("NotEmptyError").__call__(var2, PyString.fromInterned("Folder contains subdirectory '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3)}))));
         }

         var1.setlocal(3, var4);
         var1.setline(443);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var5._lt(Py.newInteger(1));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var10000 = var5._ne(PyString.fromInterned("."));
            var5 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(444);
      throw Py.makeException(var1.getglobal("NotEmptyError").__call__(var2, PyString.fromInterned("Folder contains message(s): %s")._mod(var1.getlocal(1))));
   }

   public PyObject clean$52(PyFrame var1, ThreadState var2) {
      var1.setline(458);
      PyString.fromInterned("Delete old files in \"tmp\".");
      var1.setline(459);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(460);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("tmp"))).__iter__();

      while(true) {
         var1.setline(460);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(461);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("tmp"), (PyObject)var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(462);
         var5 = var1.getlocal(1)._sub(var1.getglobal("os").__getattr__("path").__getattr__("getatime").__call__(var2, var1.getlocal(3)));
         PyObject var10000 = var5._gt(Py.newInteger(129600));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(463);
            var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject _create_tmp$53(PyFrame var1, ThreadState var2) {
      var1.setline(468);
      PyString.fromInterned("Create a file in the tmp subdirectory and open and return it.");
      var1.setline(469);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(470);
      var3 = var1.getglobal("socket").__getattr__("gethostname").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(471);
      PyString var9 = PyString.fromInterned("/");
      PyObject var10000 = var9._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(472);
         var3 = var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)PyString.fromInterned("\\057"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(473);
      var9 = PyString.fromInterned(":");
      var10000 = var9._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(474);
         var3 = var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)PyString.fromInterned("\\072"));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(475);
      var3 = PyString.fromInterned("%s.M%sP%sQ%s.%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(1)), var1.getglobal("int").__call__(var2, var1.getlocal(1)._mod(Py.newInteger(1))._mul(Py.newFloat(1000000.0))), var1.getglobal("os").__getattr__("getpid").__call__(var2), var1.getglobal("Maildir").__getattr__("_count"), var1.getlocal(2)}));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(477);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("tmp"), (PyObject)var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;

      try {
         var1.setline(479);
         var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(4));
      } catch (Throwable var8) {
         label43: {
            PyException var12 = Py.setException(var8, var1);
            if (var12.match(var1.getglobal("OSError"))) {
               PyObject var4 = var12.value;
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(481);
               var4 = var1.getlocal(5).__getattr__("errno");
               var10000 = var4._eq(var1.getglobal("errno").__getattr__("ENOENT"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(482);
                  var10000 = var1.getglobal("Maildir");
                  String var10 = "_count";
                  PyObject var5 = var10000;
                  PyObject var6 = var5.__getattr__(var10);
                  var6 = var6._iadd(Py.newInteger(1));
                  var5.__setattr__(var10, var6);

                  try {
                     var1.setline(484);
                     var4 = var1.getglobal("_create_carefully").__call__(var2, var1.getlocal(4));
                     var1.f_lasti = -1;
                     return var4;
                  } catch (Throwable var7) {
                     PyException var11 = Py.setException(var7, var1);
                     if (var11.match(var1.getglobal("OSError"))) {
                        var6 = var11.value;
                        var1.setlocal(5, var6);
                        var6 = null;
                        var1.setline(486);
                        var6 = var1.getlocal(5).__getattr__("errno");
                        var10000 = var6._ne(var1.getglobal("errno").__getattr__("EEXIST"));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(487);
                           throw Py.makeException();
                        }
                        break label43;
                     }

                     throw var11;
                  }
               }

               var1.setline(489);
               throw Py.makeException();
            }

            throw var12;
         }
      }

      var1.setline(492);
      throw Py.makeException(var1.getglobal("ExternalClashError").__call__(var2, PyString.fromInterned("Name clash prevented file creation: %s")._mod(var1.getlocal(4))));
   }

   public PyObject _refresh$54(PyFrame var1, ThreadState var2) {
      var1.setline(496);
      PyString.fromInterned("Update table of contents mapping.");
      var1.setline(510);
      PyObject var3 = var1.getglobal("time").__getattr__("time").__call__(var2)._sub(var1.getlocal(0).__getattr__("_last_read"));
      PyObject var10000 = var3._gt(Py.newInteger(2)._add(var1.getlocal(0).__getattr__("_skewfactor")));
      var3 = null;
      PyObject var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(511);
         var3 = var1.getglobal("False");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(512);
         var3 = var1.getlocal(0).__getattr__("_toc_mtimes").__iter__();

         while(true) {
            var1.setline(512);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(517);
               if (var1.getlocal(1).__not__().__nonzero__()) {
                  var1.setline(518);
                  var1.f_lasti = -1;
                  return Py.None;
               }
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(513);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("getmtime").__call__(var2, var1.getlocal(0).__getattr__("_paths").__getitem__(var1.getlocal(2)));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(514);
            var5 = var1.getlocal(3);
            var10000 = var5._gt(var1.getlocal(0).__getattr__("_toc_mtimes").__getitem__(var1.getlocal(2)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(515);
               var5 = var1.getglobal("True");
               var1.setlocal(1, var5);
               var5 = null;
            }

            var1.setline(516);
            var5 = var1.getlocal(3);
            var1.getlocal(0).__getattr__("_toc_mtimes").__setitem__(var1.getlocal(2), var5);
            var5 = null;
         }
      }

      var1.setline(520);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_toc", var8);
      var3 = null;
      var1.setline(521);
      var3 = var1.getlocal(0).__getattr__("_toc_mtimes").__iter__();

      while(true) {
         var1.setline(521);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(529);
            var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
            var1.getlocal(0).__setattr__("_last_read", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(522);
         var5 = var1.getlocal(0).__getattr__("_paths").__getitem__(var1.getlocal(2));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(523);
         var5 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(4)).__iter__();

         while(true) {
            var1.setline(523);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(5, var6);
            var1.setline(524);
            PyObject var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(5));
            var1.setlocal(6, var7);
            var7 = null;
            var1.setline(525);
            if (!var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(6)).__nonzero__()) {
               var1.setline(527);
               var7 = var1.getlocal(5).__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("colon")).__getitem__(Py.newInteger(0));
               var1.setlocal(7, var7);
               var7 = null;
               var1.setline(528);
               var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(5));
               var1.getlocal(0).__getattr__("_toc").__setitem__(var1.getlocal(7), var7);
               var7 = null;
            }
         }
      }
   }

   public PyObject _lookup$55(PyFrame var1, ThreadState var2) {
      var1.setline(532);
      PyString.fromInterned("Use TOC to return subpath for given key, or raise a KeyError.");

      PyObject var3;
      PyException var4;
      try {
         var1.setline(534);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(0).__getattr__("_toc").__getitem__(var1.getlocal(1)))).__nonzero__()) {
            var1.setline(535);
            var3 = var1.getlocal(0).__getattr__("_toc").__getitem__(var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      } catch (Throwable var6) {
         var4 = Py.setException(var6, var1);
         if (!var4.match(var1.getglobal("KeyError"))) {
            throw var4;
         }

         var1.setline(537);
      }

      var1.setline(538);
      var1.getlocal(0).__getattr__("_refresh").__call__(var2);

      try {
         var1.setline(540);
         var3 = var1.getlocal(0).__getattr__("_toc").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            var1.setline(542);
            throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("No message with key: %s")._mod(var1.getlocal(1))));
         } else {
            throw var4;
         }
      }
   }

   public PyObject next$56(PyFrame var1, ThreadState var2) {
      var1.setline(546);
      PyString.fromInterned("Return the next message in a one-time iteration.");
      var1.setline(547);
      PyObject var3;
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_onetime_keys")).__not__().__nonzero__()) {
         var1.setline(548);
         var3 = var1.getlocal(0).__getattr__("iterkeys").__call__(var2);
         var1.getlocal(0).__setattr__("_onetime_keys", var3);
         var3 = null;
      }

      while(true) {
         var1.setline(549);
         if (!var1.getglobal("True").__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         try {
            var1.setline(551);
            var3 = var1.getlocal(0).__getitem__(var1.getlocal(0).__getattr__("_onetime_keys").__getattr__("next").__call__(var2));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("StopIteration"))) {
               var1.setline(553);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }

            if (!var4.match(var1.getglobal("KeyError"))) {
               throw var4;
            }
         }
      }
   }

   public PyObject _singlefileMailbox$57(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A single-file mailbox."));
      var1.setline(559);
      PyString.fromInterned("A single-file mailbox.");
      var1.setline(561);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$58, PyString.fromInterned("Initialize a single-file mailbox."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(584);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add$59, PyString.fromInterned("Add message and return assigned key."));
      var1.setlocal("add", var4);
      var3 = null;
      var1.setline(594);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove$60, PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("remove", var4);
      var3 = null;
      var1.setline(600);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$61, PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(606);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterkeys$62, PyString.fromInterned("Return an iterator over keys."));
      var1.setlocal("iterkeys", var4);
      var3 = null;
      var1.setline(612);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$63, PyString.fromInterned("Return True if the keyed message exists, False otherwise."));
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(617);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$64, PyString.fromInterned("Return a count of messages in the mailbox."));
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(622);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lock$65, PyString.fromInterned("Lock the mailbox."));
      var1.setlocal("lock", var4);
      var3 = null;
      var1.setline(628);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unlock$66, PyString.fromInterned("Unlock the mailbox if it is locked."));
      var1.setlocal("unlock", var4);
      var3 = null;
      var1.setline(634);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$67, PyString.fromInterned("Write any pending changes to disk."));
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(702);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _pre_mailbox_hook$68, PyString.fromInterned("Called before writing the mailbox to file f."));
      var1.setlocal("_pre_mailbox_hook", var4);
      var3 = null;
      var1.setline(706);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _pre_message_hook$69, PyString.fromInterned("Called before writing each message to file f."));
      var1.setlocal("_pre_message_hook", var4);
      var3 = null;
      var1.setline(710);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _post_message_hook$70, PyString.fromInterned("Called after writing each message to file f."));
      var1.setlocal("_post_message_hook", var4);
      var3 = null;
      var1.setline(714);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$71, PyString.fromInterned("Flush and close the mailbox."));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(721);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _lookup$72, PyString.fromInterned("Return (start, stop) or raise KeyError."));
      var1.setlocal("_lookup", var4);
      var3 = null;
      var1.setline(731);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _append_message$73, PyString.fromInterned("Append message to mailbox and return (start, stop) offsets."));
      var1.setlocal("_append_message", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$58(PyFrame var1, ThreadState var2) {
      var1.setline(562);
      PyString.fromInterned("Initialize a single-file mailbox.");
      var1.setline(563);
      var1.getglobal("Mailbox").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));

      PyException var3;
      PyObject var6;
      try {
         var1.setline(565);
         var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("rb+"));
         var1.setlocal(4, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("IOError"))) {
            throw var3;
         }

         PyObject var4 = var3.value;
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(567);
         var4 = var1.getlocal(5).__getattr__("errno");
         PyObject var10000 = var4._eq(var1.getglobal("errno").__getattr__("ENOENT"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(568);
            if (!var1.getlocal(3).__nonzero__()) {
               var1.setline(571);
               throw Py.makeException(var1.getglobal("NoSuchMailboxError").__call__(var2, var1.getlocal(0).__getattr__("_path")));
            }

            var1.setline(569);
            var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("wb+"));
            var1.setlocal(4, var4);
            var4 = null;
         } else {
            var1.setline(572);
            var4 = var1.getlocal(5).__getattr__("errno");
            var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("errno").__getattr__("EACCES"), var1.getglobal("errno").__getattr__("EROFS")}));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(575);
               throw Py.makeException();
            }

            var1.setline(573);
            var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("rb"));
            var1.setlocal(4, var4);
            var4 = null;
         }
      }

      var1.setline(576);
      var6 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_file", var6);
      var3 = null;
      var1.setline(577);
      var6 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_toc", var6);
      var3 = null;
      var1.setline(578);
      PyInteger var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_next_key", var7);
      var3 = null;
      var1.setline(579);
      var6 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_pending", var6);
      var3 = null;
      var1.setline(580);
      var6 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_pending_sync", var6);
      var3 = null;
      var1.setline(581);
      var6 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_locked", var6);
      var3 = null;
      var1.setline(582);
      var6 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_file_length", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add$59(PyFrame var1, ThreadState var2) {
      var1.setline(585);
      PyString.fromInterned("Add message and return assigned key.");
      var1.setline(586);
      var1.getlocal(0).__getattr__("_lookup").__call__(var2);
      var1.setline(587);
      PyObject var3 = var1.getlocal(0).__getattr__("_append_message").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__getattr__("_toc").__setitem__(var1.getlocal(0).__getattr__("_next_key"), var3);
      var3 = null;
      var1.setline(588);
      PyObject var10000 = var1.getlocal(0);
      String var6 = "_next_key";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var6, var5);
      var1.setline(591);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_pending_sync", var3);
      var3 = null;
      var1.setline(592);
      var3 = var1.getlocal(0).__getattr__("_next_key")._sub(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove$60(PyFrame var1, ThreadState var2) {
      var1.setline(595);
      PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(596);
      var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
      var1.setline(597);
      var1.getlocal(0).__getattr__("_toc").__delitem__(var1.getlocal(1));
      var1.setline(598);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_pending", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$61(PyFrame var1, ThreadState var2) {
      var1.setline(601);
      PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(602);
      var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
      var1.setline(603);
      PyObject var3 = var1.getlocal(0).__getattr__("_append_message").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__getattr__("_toc").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(604);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_pending", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject iterkeys$62(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(607);
            PyString.fromInterned("Return an iterator over keys.");
            var1.setline(608);
            var1.getlocal(0).__getattr__("_lookup").__call__(var2);
            var1.setline(609);
            var3 = var1.getlocal(0).__getattr__("_toc").__getattr__("keys").__call__(var2).__iter__();
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

      var1.setline(609);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(610);
         var1.setline(610);
         var6 = var1.getlocal(1);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject has_key$63(PyFrame var1, ThreadState var2) {
      var1.setline(613);
      PyString.fromInterned("Return True if the keyed message exists, False otherwise.");
      var1.setline(614);
      var1.getlocal(0).__getattr__("_lookup").__call__(var2);
      var1.setline(615);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_toc"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$64(PyFrame var1, ThreadState var2) {
      var1.setline(618);
      PyString.fromInterned("Return a count of messages in the mailbox.");
      var1.setline(619);
      var1.getlocal(0).__getattr__("_lookup").__call__(var2);
      var1.setline(620);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_toc"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lock$65(PyFrame var1, ThreadState var2) {
      var1.setline(623);
      PyString.fromInterned("Lock the mailbox.");
      var1.setline(624);
      if (var1.getlocal(0).__getattr__("_locked").__not__().__nonzero__()) {
         var1.setline(625);
         var1.getglobal("_lock_file").__call__(var2, var1.getlocal(0).__getattr__("_file"));
         var1.setline(626);
         PyObject var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_locked", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unlock$66(PyFrame var1, ThreadState var2) {
      var1.setline(629);
      PyString.fromInterned("Unlock the mailbox if it is locked.");
      var1.setline(630);
      if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
         var1.setline(631);
         var1.getglobal("_unlock_file").__call__(var2, var1.getlocal(0).__getattr__("_file"));
         var1.setline(632);
         PyObject var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_locked", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$67(PyFrame var1, ThreadState var2) {
      var1.setline(635);
      PyString.fromInterned("Write any pending changes to disk.");
      var1.setline(636);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_pending").__not__().__nonzero__()) {
         var1.setline(637);
         if (var1.getlocal(0).__getattr__("_pending_sync").__nonzero__()) {
            var1.setline(640);
            var1.getglobal("_sync_flush").__call__(var2, var1.getlocal(0).__getattr__("_file"));
            var1.setline(641);
            var3 = var1.getglobal("False");
            var1.getlocal(0).__setattr__("_pending_sync", var3);
            var3 = null;
         }

         var1.setline(642);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(647);
         PyObject var10000;
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_toc");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(651);
         var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
         var1.setline(652);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(653);
         var3 = var1.getlocal(1);
         var10000 = var3._ne(var1.getlocal(0).__getattr__("_file_length"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(654);
            throw Py.makeException(var1.getglobal("ExternalClashError").__call__(var2, PyString.fromInterned("Size of mailbox file changed (expected %i, found %i)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_file_length"), var1.getlocal(1)}))));
         } else {
            var1.setline(658);
            var3 = var1.getglobal("_create_temporary").__call__(var2, var1.getlocal(0).__getattr__("_path"));
            var1.setlocal(2, var3);
            var3 = null;

            PyObject var4;
            try {
               var1.setline(660);
               PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
               var1.setlocal(3, var10);
               var3 = null;
               var1.setline(661);
               var1.getlocal(0).__getattr__("_pre_mailbox_hook").__call__(var2, var1.getlocal(2));
               var1.setline(662);
               var3 = var1.getglobal("sorted").__call__(var2, var1.getlocal(0).__getattr__("_toc").__getattr__("keys").__call__(var2)).__iter__();

               while(true) {
                  var1.setline(662);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(675);
                     var3 = var1.getlocal(2).__getattr__("tell").__call__(var2);
                     var1.getlocal(0).__setattr__("_file_length", var3);
                     var3 = null;
                     break;
                  }

                  var1.setlocal(4, var4);
                  var1.setline(663);
                  PyObject var5 = var1.getlocal(0).__getattr__("_toc").__getitem__(var1.getlocal(4));
                  PyObject[] var6 = Py.unpackSequence(var5, 2);
                  PyObject var7 = var6[0];
                  var1.setlocal(5, var7);
                  var7 = null;
                  var7 = var6[1];
                  var1.setlocal(6, var7);
                  var7 = null;
                  var5 = null;
                  var1.setline(664);
                  var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__(var2, var1.getlocal(5));
                  var1.setline(665);
                  var1.getlocal(0).__getattr__("_pre_message_hook").__call__(var2, var1.getlocal(2));
                  var1.setline(666);
                  var5 = var1.getlocal(2).__getattr__("tell").__call__(var2);
                  var1.setlocal(7, var5);
                  var5 = null;

                  while(true) {
                     var1.setline(667);
                     if (!var1.getglobal("True").__nonzero__()) {
                        break;
                     }

                     var1.setline(668);
                     var5 = var1.getlocal(0).__getattr__("_file").__getattr__("read").__call__(var2, var1.getglobal("min").__call__((ThreadState)var2, (PyObject)Py.newInteger(4096), (PyObject)var1.getlocal(6)._sub(var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2))));
                     var1.setlocal(8, var5);
                     var5 = null;
                     var1.setline(670);
                     var5 = var1.getlocal(8);
                     var10000 = var5._eq(PyString.fromInterned(""));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        break;
                     }

                     var1.setline(672);
                     var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(8));
                  }

                  var1.setline(673);
                  PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(2).__getattr__("tell").__call__(var2)});
                  var1.getlocal(3).__setitem__((PyObject)var1.getlocal(4), var12);
                  var5 = null;
                  var1.setline(674);
                  var1.getlocal(0).__getattr__("_post_message_hook").__call__(var2, var1.getlocal(2));
               }
            } catch (Throwable var9) {
               Py.setException(var9, var1);
               var1.setline(677);
               var1.getlocal(2).__getattr__("close").__call__(var2);
               var1.setline(678);
               var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(2).__getattr__("name"));
               var1.setline(679);
               throw Py.makeException();
            }

            var1.setline(680);
            var1.getglobal("_sync_close").__call__(var2, var1.getlocal(2));
            var1.setline(682);
            var1.getlocal(0).__getattr__("_file").__getattr__("close").__call__(var2);
            var1.setline(684);
            var3 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0).__getattr__("_path")).__getattr__("st_mode");
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(685);
            var1.getglobal("os").__getattr__("chmod").__call__(var2, var1.getlocal(2).__getattr__("name"), var1.getlocal(9));

            try {
               var1.setline(687);
               var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(2).__getattr__("name"), var1.getlocal(0).__getattr__("_path"));
            } catch (Throwable var8) {
               PyException var13 = Py.setException(var8, var1);
               if (!var13.match(var1.getglobal("OSError"))) {
                  throw var13;
               }

               var4 = var13.value;
               var1.setlocal(10, var4);
               var4 = null;
               var1.setline(689);
               var4 = var1.getlocal(10).__getattr__("errno");
               var10000 = var4._eq(var1.getglobal("errno").__getattr__("EEXIST"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getglobal("os").__getattr__("name");
                  var10000 = var4._eq(PyString.fromInterned("os2"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var4 = var1.getlocal(10).__getattr__("errno");
                     var10000 = var4._eq(var1.getglobal("errno").__getattr__("EACCES"));
                     var4 = null;
                  }
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(694);
                  throw Py.makeException();
               }

               var1.setline(691);
               var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(0).__getattr__("_path"));
               var1.setline(692);
               var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(2).__getattr__("name"), var1.getlocal(0).__getattr__("_path"));
            }

            var1.setline(695);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned("rb+"));
            var1.getlocal(0).__setattr__("_file", var3);
            var3 = null;
            var1.setline(696);
            var3 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("_toc", var3);
            var3 = null;
            var1.setline(697);
            var3 = var1.getglobal("False");
            var1.getlocal(0).__setattr__("_pending", var3);
            var3 = null;
            var1.setline(698);
            var3 = var1.getglobal("False");
            var1.getlocal(0).__setattr__("_pending_sync", var3);
            var3 = null;
            var1.setline(699);
            if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
               var1.setline(700);
               var10000 = var1.getglobal("_lock_file");
               PyObject[] var14 = new PyObject[]{var1.getlocal(0).__getattr__("_file"), var1.getglobal("False")};
               String[] var11 = new String[]{"dotlock"};
               var10000.__call__(var2, var14, var11);
               var3 = null;
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _pre_mailbox_hook$68(PyFrame var1, ThreadState var2) {
      var1.setline(703);
      PyString.fromInterned("Called before writing the mailbox to file f.");
      var1.setline(704);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _pre_message_hook$69(PyFrame var1, ThreadState var2) {
      var1.setline(707);
      PyString.fromInterned("Called before writing each message to file f.");
      var1.setline(708);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _post_message_hook$70(PyFrame var1, ThreadState var2) {
      var1.setline(711);
      PyString.fromInterned("Called after writing each message to file f.");
      var1.setline(712);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$71(PyFrame var1, ThreadState var2) {
      var1.setline(715);
      PyString.fromInterned("Flush and close the mailbox.");
      var1.setline(716);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(717);
      if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
         var1.setline(718);
         var1.getlocal(0).__getattr__("unlock").__call__(var2);
      }

      var1.setline(719);
      var1.getlocal(0).__getattr__("_file").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _lookup$72(PyFrame var1, ThreadState var2) {
      var1.setline(722);
      PyString.fromInterned("Return (start, stop) or raise KeyError.");
      var1.setline(723);
      PyObject var3 = var1.getlocal(0).__getattr__("_toc");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(724);
         var1.getlocal(0).__getattr__("_generate_toc").__call__(var2);
      }

      var1.setline(725);
      var3 = var1.getlocal(1);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(727);
            var3 = var1.getlocal(0).__getattr__("_toc").__getitem__(var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            PyException var4 = Py.setException(var5, var1);
            if (var4.match(var1.getglobal("KeyError"))) {
               var1.setline(729);
               throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("No message with key: %s")._mod(var1.getlocal(1))));
            } else {
               throw var4;
            }
         }
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _append_message$73(PyFrame var1, ThreadState var2) {
      var1.setline(732);
      PyString.fromInterned("Append message to mailbox and return (start, stop) offsets.");
      var1.setline(733);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(734);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(735);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_toc"));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_pending").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(740);
         var1.getlocal(0).__getattr__("_pre_mailbox_hook").__call__(var2, var1.getlocal(0).__getattr__("_file"));
      }

      try {
         var1.setline(742);
         var1.getlocal(0).__getattr__("_pre_message_hook").__call__(var2, var1.getlocal(0).__getattr__("_file"));
         var1.setline(743);
         var3 = var1.getlocal(0).__getattr__("_install_message").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(744);
         var1.getlocal(0).__getattr__("_post_message_hook").__call__(var2, var1.getlocal(0).__getattr__("_file"));
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (var5.match(var1.getglobal("BaseException"))) {
            var1.setline(746);
            var1.getlocal(0).__getattr__("_file").__getattr__("truncate").__call__(var2, var1.getlocal(2));
            var1.setline(747);
            throw Py.makeException();
         }

         throw var5;
      }

      var1.setline(748);
      var1.getlocal(0).__getattr__("_file").__getattr__("flush").__call__(var2);
      var1.setline(749);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("_file_length", var3);
      var3 = null;
      var1.setline(750);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _mboxMMDF$74(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An mbox or MMDF mailbox."));
      var1.setline(755);
      PyString.fromInterned("An mbox or MMDF mailbox.");
      var1.setline(757);
      PyObject var3 = var1.getname("True");
      var1.setlocal("_mangle_from_", var3);
      var3 = null;
      var1.setline(759);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, get_message$75, PyString.fromInterned("Return a Message representation or raise a KeyError."));
      var1.setlocal("get_message", var5);
      var3 = null;
      var1.setline(769);
      var4 = new PyObject[]{var1.getname("False")};
      var5 = new PyFunction(var1.f_globals, var4, get_string$76, PyString.fromInterned("Return a string representation or raise a KeyError."));
      var1.setlocal("get_string", var5);
      var3 = null;
      var1.setline(778);
      var4 = new PyObject[]{var1.getname("False")};
      var5 = new PyFunction(var1.f_globals, var4, get_file$77, PyString.fromInterned("Return a file-like representation or raise a KeyError."));
      var1.setlocal("get_file", var5);
      var3 = null;
      var1.setline(786);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _install_message$78, PyString.fromInterned("Format a message and blindly write to self._file."));
      var1.setlocal("_install_message", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject get_message$75(PyFrame var1, ThreadState var2) {
      var1.setline(760);
      PyString.fromInterned("Return a Message representation or raise a KeyError.");
      var1.setline(761);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(762);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__(var2, var1.getlocal(2));
      var1.setline(763);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("linesep"), (PyObject)PyString.fromInterned(""));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(764);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("read").__call__(var2, var1.getlocal(3)._sub(var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(765);
      var3 = var1.getlocal(0).__getattr__("_message_factory").__call__(var2, var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("linesep"), (PyObject)PyString.fromInterned("\n")));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(766);
      var1.getlocal(6).__getattr__("set_from").__call__(var2, var1.getlocal(4).__getslice__(Py.newInteger(5), (PyObject)null, (PyObject)null));
      var1.setline(767);
      var3 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_string$76(PyFrame var1, ThreadState var2) {
      var1.setline(770);
      PyString.fromInterned("Return a string representation or raise a KeyError.");
      var1.setline(771);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(772);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__(var2, var1.getlocal(3));
      var1.setline(773);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(774);
         var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
      }

      var1.setline(775);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("read").__call__(var2, var1.getlocal(4)._sub(var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(776);
      var3 = var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("linesep"), (PyObject)PyString.fromInterned("\n"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_file$77(PyFrame var1, ThreadState var2) {
      var1.setline(779);
      PyString.fromInterned("Return a file-like representation or raise a KeyError.");
      var1.setline(780);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(781);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__(var2, var1.getlocal(3));
      var1.setline(782);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(783);
         var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
      }

      var1.setline(784);
      var3 = var1.getglobal("_PartialFile").__call__(var2, var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _install_message$78(PyFrame var1, ThreadState var2) {
      var1.setline(787);
      PyString.fromInterned("Format a message and blindly write to self._file.");
      var1.setline(788);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(789);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From "));
      }

      if (var10000.__nonzero__()) {
         var1.setline(790);
         var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(791);
         var3 = var1.getlocal(3);
         var10000 = var3._ne(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(792);
            var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(3), (PyObject)null);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(793);
            var3 = var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(795);
            var3 = var1.getlocal(1);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(796);
            PyString var4 = PyString.fromInterned("");
            var1.setlocal(1, var4);
            var3 = null;
         }
      } else {
         var1.setline(797);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_mboxMMDFMessage")).__nonzero__()) {
            var1.setline(798);
            var3 = PyString.fromInterned("From ")._add(var1.getlocal(1).__getattr__("get_from").__call__(var2));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(799);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("email").__getattr__("message").__getattr__("Message")).__nonzero__()) {
               var1.setline(800);
               var3 = var1.getlocal(1).__getattr__("get_unixfrom").__call__(var2);
               var1.setlocal(2, var3);
               var3 = null;
            }
         }
      }

      var1.setline(801);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(802);
         var3 = PyString.fromInterned("From MAILER-DAEMON %s")._mod(var1.getglobal("time").__getattr__("asctime").__call__(var2, var1.getglobal("time").__getattr__("gmtime").__call__(var2)));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(803);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(804);
      var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(2)._add(var1.getglobal("os").__getattr__("linesep")));
      var1.setline(805);
      var1.getlocal(0).__getattr__("_dump_message").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_file"), var1.getlocal(0).__getattr__("_mangle_from_"));
      var1.setline(806);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(807);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject mbox$79(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A classic mbox mailbox."));
      var1.setline(811);
      PyString.fromInterned("A classic mbox mailbox.");
      var1.setline(813);
      PyObject var3 = var1.getname("True");
      var1.setlocal("_mangle_from_", var3);
      var3 = null;
      var1.setline(817);
      var3 = var1.getname("True");
      var1.setlocal("_append_newline", var3);
      var3 = null;
      var1.setline(819);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$80, PyString.fromInterned("Initialize an mbox mailbox."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(824);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _post_message_hook$81, PyString.fromInterned("Called after writing each message to file f."));
      var1.setlocal("_post_message_hook", var5);
      var3 = null;
      var1.setline(828);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _generate_toc$82, PyString.fromInterned("Generate key-to-(start, stop) table of contents."));
      var1.setlocal("_generate_toc", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$80(PyFrame var1, ThreadState var2) {
      var1.setline(820);
      PyString.fromInterned("Initialize an mbox mailbox.");
      var1.setline(821);
      PyObject var3 = var1.getglobal("mboxMessage");
      var1.getlocal(0).__setattr__("_message_factory", var3);
      var3 = null;
      var1.setline(822);
      var1.getglobal("_mboxMMDF").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _post_message_hook$81(PyFrame var1, ThreadState var2) {
      var1.setline(825);
      PyString.fromInterned("Called after writing each message to file f.");
      var1.setline(826);
      var1.getlocal(1).__getattr__("write").__call__(var2, var1.getglobal("os").__getattr__("linesep"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _generate_toc$82(PyFrame var1, ThreadState var2) {
      var1.setline(829);
      PyString.fromInterned("Generate key-to-(start, stop) table of contents.");
      var1.setline(830);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(831);
      PyObject var6 = var1.getglobal("False");
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(832);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));

      while(true) {
         var1.setline(833);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(834);
         var6 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(835);
         var6 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(836);
         PyObject var10000;
         if (var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From ")).__nonzero__()) {
            var1.setline(837);
            var6 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var6._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(838);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(839);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4)._sub(var1.getglobal("len").__call__(var2, var1.getglobal("os").__getattr__("linesep"))));
               } else {
                  var1.setline(844);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
               }
            }

            var1.setline(845);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
            var1.setline(846);
            var6 = var1.getglobal("False");
            var1.setlocal(3, var6);
            var3 = null;
         } else {
            var1.setline(847);
            if (var1.getlocal(5).__not__().__nonzero__()) {
               var1.setline(848);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(849);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4)._sub(var1.getglobal("len").__call__(var2, var1.getglobal("os").__getattr__("linesep"))));
               } else {
                  var1.setline(851);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
               }
               break;
            }

            var1.setline(853);
            var6 = var1.getlocal(5);
            var10000 = var6._eq(var1.getglobal("os").__getattr__("linesep"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(854);
               var6 = var1.getglobal("True");
               var1.setlocal(3, var6);
               var3 = null;
            } else {
               var1.setline(856);
               var6 = var1.getglobal("False");
               var1.setlocal(3, var6);
               var3 = null;
            }
         }
      }

      var1.setline(857);
      var6 = var1.getglobal("dict").__call__(var2, var1.getglobal("enumerate").__call__(var2, var1.getglobal("zip").__call__(var2, var1.getlocal(1), var1.getlocal(2))));
      var1.getlocal(0).__setattr__("_toc", var6);
      var3 = null;
      var1.setline(858);
      var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_toc"));
      var1.getlocal(0).__setattr__("_next_key", var6);
      var3 = null;
      var1.setline(859);
      var6 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("_file_length", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MMDF$83(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An MMDF mailbox."));
      var1.setline(863);
      PyString.fromInterned("An MMDF mailbox.");
      var1.setline(865);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$84, PyString.fromInterned("Initialize an MMDF mailbox."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(870);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _pre_message_hook$85, PyString.fromInterned("Called before writing each message to file f."));
      var1.setlocal("_pre_message_hook", var4);
      var3 = null;
      var1.setline(874);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _post_message_hook$86, PyString.fromInterned("Called after writing each message to file f."));
      var1.setlocal("_post_message_hook", var4);
      var3 = null;
      var1.setline(878);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _generate_toc$87, PyString.fromInterned("Generate key-to-(start, stop) table of contents."));
      var1.setlocal("_generate_toc", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$84(PyFrame var1, ThreadState var2) {
      var1.setline(866);
      PyString.fromInterned("Initialize an MMDF mailbox.");
      var1.setline(867);
      PyObject var3 = var1.getglobal("MMDFMessage");
      var1.getlocal(0).__setattr__("_message_factory", var3);
      var3 = null;
      var1.setline(868);
      var1.getglobal("_mboxMMDF").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _pre_message_hook$85(PyFrame var1, ThreadState var2) {
      var1.setline(871);
      PyString.fromInterned("Called before writing each message to file f.");
      var1.setline(872);
      var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("\u0001\u0001\u0001\u0001")._add(var1.getglobal("os").__getattr__("linesep")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _post_message_hook$86(PyFrame var1, ThreadState var2) {
      var1.setline(875);
      PyString.fromInterned("Called after writing each message to file f.");
      var1.setline(876);
      var1.getlocal(1).__getattr__("write").__call__(var2, var1.getglobal("os").__getattr__("linesep")._add(PyString.fromInterned("\u0001\u0001\u0001\u0001"))._add(var1.getglobal("os").__getattr__("linesep")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _generate_toc$87(PyFrame var1, ThreadState var2) {
      var1.setline(879);
      PyString.fromInterned("Generate key-to-(start, stop) table of contents.");
      var1.setline(880);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(881);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(882);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(3, var6);
      var3 = null;

      PyObject var7;
      label33:
      while(true) {
         var1.setline(883);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(884);
         var7 = var1.getlocal(3);
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(885);
         var7 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(886);
         var7 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
         var1.setlocal(3, var7);
         var3 = null;
         var1.setline(887);
         PyObject var10000;
         if (var1.getlocal(5).__getattr__("startswith").__call__(var2, PyString.fromInterned("\u0001\u0001\u0001\u0001")._add(var1.getglobal("os").__getattr__("linesep"))).__nonzero__()) {
            var1.setline(888);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));

            do {
               var1.setline(889);
               if (!var1.getglobal("True").__nonzero__()) {
                  continue label33;
               }

               var1.setline(890);
               var7 = var1.getlocal(3);
               var1.setlocal(4, var7);
               var3 = null;
               var1.setline(891);
               var7 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
               var1.setlocal(5, var7);
               var3 = null;
               var1.setline(892);
               var7 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
               var1.setlocal(3, var7);
               var3 = null;
               var1.setline(893);
               var7 = var1.getlocal(5);
               var10000 = var7._eq(PyString.fromInterned("\u0001\u0001\u0001\u0001")._add(var1.getglobal("os").__getattr__("linesep")));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(894);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4)._sub(var1.getglobal("len").__call__(var2, var1.getglobal("os").__getattr__("linesep"))));
                  continue label33;
               }

               var1.setline(896);
               var7 = var1.getlocal(5);
               var10000 = var7._eq(PyString.fromInterned(""));
               var3 = null;
            } while(!var10000.__nonzero__());

            var1.setline(897);
            var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
         } else {
            var1.setline(899);
            var7 = var1.getlocal(5);
            var10000 = var7._eq(PyString.fromInterned(""));
            var3 = null;
            if (var10000.__nonzero__()) {
               break;
            }
         }
      }

      var1.setline(901);
      var7 = var1.getglobal("dict").__call__(var2, var1.getglobal("enumerate").__call__(var2, var1.getglobal("zip").__call__(var2, var1.getlocal(1), var1.getlocal(2))));
      var1.getlocal(0).__setattr__("_toc", var7);
      var3 = null;
      var1.setline(902);
      var7 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_toc"));
      var1.getlocal(0).__setattr__("_next_key", var7);
      var3 = null;
      var1.setline(903);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(904);
      var7 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("_file_length", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MH$88(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An MH mailbox."));
      var1.setline(908);
      PyString.fromInterned("An MH mailbox.");
      var1.setline(910);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$89, PyString.fromInterned("Initialize an MH instance."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(922);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add$90, PyString.fromInterned("Add message and return assigned key."));
      var1.setlocal("add", var4);
      var3 = null;
      var1.setline(956);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove$91, PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("remove", var4);
      var3 = null;
      var1.setline(970);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __setitem__$92, PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("__setitem__", var4);
      var3 = null;
      var1.setline(994);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_message$93, PyString.fromInterned("Return a Message representation or raise a KeyError."));
      var1.setlocal("get_message", var4);
      var3 = null;
      var1.setline(1021);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_string$94, PyString.fromInterned("Return a string representation or raise a KeyError."));
      var1.setlocal("get_string", var4);
      var3 = null;
      var1.setline(1044);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_file$95, PyString.fromInterned("Return a file-like representation or raise a KeyError."));
      var1.setlocal("get_file", var4);
      var3 = null;
      var1.setline(1055);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterkeys$96, PyString.fromInterned("Return an iterator over keys."));
      var1.setlocal("iterkeys", var4);
      var3 = null;
      var1.setline(1060);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_key$98, PyString.fromInterned("Return True if the keyed message exists, False otherwise."));
      var1.setlocal("has_key", var4);
      var3 = null;
      var1.setline(1064);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$99, PyString.fromInterned("Return a count of messages in the mailbox."));
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(1068);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, lock$100, PyString.fromInterned("Lock the mailbox."));
      var1.setlocal("lock", var4);
      var3 = null;
      var1.setline(1075);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, unlock$101, PyString.fromInterned("Unlock the mailbox if it is locked."));
      var1.setlocal("unlock", var4);
      var3 = null;
      var1.setline(1083);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, flush$102, PyString.fromInterned("Write any pending changes to the disk."));
      var1.setlocal("flush", var4);
      var3 = null;
      var1.setline(1087);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$103, PyString.fromInterned("Flush and close the mailbox."));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(1092);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, list_folders$104, PyString.fromInterned("Return a list of folder names."));
      var1.setlocal("list_folders", var4);
      var3 = null;
      var1.setline(1100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_folder$105, PyString.fromInterned("Return an MH instance for the named folder."));
      var1.setlocal("get_folder", var4);
      var3 = null;
      var1.setline(1105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_folder$106, PyString.fromInterned("Create a folder and return an MH instance representing it."));
      var1.setlocal("add_folder", var4);
      var3 = null;
      var1.setline(1110);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove_folder$107, PyString.fromInterned("Delete the named folder, which must be empty."));
      var1.setlocal("remove_folder", var4);
      var3 = null;
      var1.setline(1122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_sequences$108, PyString.fromInterned("Return a name-to-key-list dictionary to define each sequence."));
      var1.setlocal("get_sequences", var4);
      var3 = null;
      var1.setline(1149);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_sequences$110, PyString.fromInterned("Set sequences using the given name-to-key-list dictionary."));
      var1.setlocal("set_sequences", var4);
      var3 = null;
      var1.setline(1178);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pack$111, PyString.fromInterned("Re-name messages to eliminate numbering gaps. Invalidates keys."));
      var1.setlocal("pack", var4);
      var3 = null;
      var1.setline(1203);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _dump_sequences$112, PyString.fromInterned("Inspect a new MHMessage and update sequences appropriately."));
      var1.setlocal("_dump_sequences", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$89(PyFrame var1, ThreadState var2) {
      var1.setline(911);
      PyString.fromInterned("Initialize an MH instance.");
      var1.setline(912);
      var1.getglobal("Mailbox").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(913);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0).__getattr__("_path")).__not__().__nonzero__()) {
         var1.setline(914);
         if (!var1.getlocal(3).__nonzero__()) {
            var1.setline(919);
            throw Py.makeException(var1.getglobal("NoSuchMailboxError").__call__(var2, var1.getlocal(0).__getattr__("_path")));
         }

         var1.setline(915);
         var1.getglobal("os").__getattr__("mkdir").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)Py.newInteger(448));
         var1.setline(916);
         var1.getglobal("os").__getattr__("close").__call__(var2, var1.getglobal("os").__getattr__("open").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned(".mh_sequences")), (PyObject)var1.getglobal("os").__getattr__("O_CREAT")._or(var1.getglobal("os").__getattr__("O_EXCL"))._or(var1.getglobal("os").__getattr__("O_WRONLY")), (PyObject)Py.newInteger(384)));
      }

      var1.setline(920);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_locked", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add$90(PyFrame var1, ThreadState var2) {
      var1.setline(923);
      PyString.fromInterned("Add message and return assigned key.");
      var1.setline(924);
      PyObject var3 = var1.getlocal(0).__getattr__("keys").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(925);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(926);
         PyInteger var10 = Py.newInteger(1);
         var1.setlocal(3, var10);
         var3 = null;
      } else {
         var1.setline(928);
         var3 = var1.getglobal("max").__call__(var2, var1.getlocal(2))._add(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(929);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(3)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(930);
      var3 = var1.getglobal("_create_carefully").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(931);
      var3 = var1.getglobal("False");
      var1.setlocal(6, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(933);
         if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
            var1.setline(934);
            var1.getglobal("_lock_file").__call__(var2, var1.getlocal(5));
         }

         Object var4 = null;

         try {
            try {
               var1.setline(937);
               var1.getlocal(0).__getattr__("_dump_message").__call__(var2, var1.getlocal(1), var1.getlocal(5));
            } catch (Throwable var7) {
               PyException var5 = Py.setException(var7, var1);
               if (var5.match(var1.getglobal("BaseException"))) {
                  var1.setline(940);
                  if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
                     var1.setline(941);
                     var1.getglobal("_unlock_file").__call__(var2, var1.getlocal(5));
                  }

                  var1.setline(942);
                  var1.getglobal("_sync_close").__call__(var2, var1.getlocal(5));
                  var1.setline(943);
                  PyObject var6 = var1.getglobal("True");
                  var1.setlocal(6, var6);
                  var6 = null;
                  var1.setline(944);
                  var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(4));
                  var1.setline(945);
                  throw Py.makeException();
               }

               throw var5;
            }

            var1.setline(946);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MHMessage")).__nonzero__()) {
               var1.setline(947);
               var1.getlocal(0).__getattr__("_dump_sequences").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            }
         } catch (Throwable var8) {
            Py.addTraceback(var8, var1);
            var1.setline(949);
            if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
               var1.setline(950);
               var1.getglobal("_unlock_file").__call__(var2, var1.getlocal(5));
            }

            throw (Throwable)var8;
         }

         var1.setline(949);
         if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
            var1.setline(950);
            var1.getglobal("_unlock_file").__call__(var2, var1.getlocal(5));
         }
      } catch (Throwable var9) {
         Py.addTraceback(var9, var1);
         var1.setline(952);
         if (var1.getlocal(6).__not__().__nonzero__()) {
            var1.setline(953);
            var1.getglobal("_sync_close").__call__(var2, var1.getlocal(5));
         }

         throw (Throwable)var9;
      }

      var1.setline(952);
      if (var1.getlocal(6).__not__().__nonzero__()) {
         var1.setline(953);
         var1.getglobal("_sync_close").__call__(var2, var1.getlocal(5));
      }

      var1.setline(954);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove$91(PyFrame var1, ThreadState var2) {
      var1.setline(957);
      PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(958);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(960);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("rb+"));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(var1.getglobal("IOError"))) {
            PyObject var4 = var6.value;
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(962);
            var4 = var1.getlocal(4).__getattr__("errno");
            PyObject var10000 = var4._eq(var1.getglobal("errno").__getattr__("ENOENT"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(963);
               throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("No message with key: %s")._mod(var1.getlocal(1))));
            }

            var1.setline(965);
            throw Py.makeException();
         }

         throw var6;
      }

      var1.setline(967);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(968);
      var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$92(PyFrame var1, ThreadState var2) {
      var1.setline(971);
      PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(972);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(1)));
      var1.setlocal(3, var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(974);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("rb+"));
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var8 = Py.setException(var6, var1);
         if (var8.match(var1.getglobal("IOError"))) {
            var4 = var8.value;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(976);
            var4 = var1.getlocal(5).__getattr__("errno");
            PyObject var10000 = var4._eq(var1.getglobal("errno").__getattr__("ENOENT"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(977);
               throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("No message with key: %s")._mod(var1.getlocal(1))));
            }

            var1.setline(979);
            throw Py.makeException();
         }

         throw var8;
      }

      var3 = null;

      try {
         var1.setline(981);
         if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
            var1.setline(982);
            var1.getglobal("_lock_file").__call__(var2, var1.getlocal(4));
         }

         var4 = null;

         try {
            var1.setline(984);
            var1.getglobal("os").__getattr__("close").__call__(var2, var1.getglobal("os").__getattr__("open").__call__(var2, var1.getlocal(3), var1.getglobal("os").__getattr__("O_WRONLY")._or(var1.getglobal("os").__getattr__("O_TRUNC"))));
            var1.setline(985);
            var1.getlocal(0).__getattr__("_dump_message").__call__(var2, var1.getlocal(2), var1.getlocal(4));
            var1.setline(986);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("MHMessage")).__nonzero__()) {
               var1.setline(987);
               var1.getlocal(0).__getattr__("_dump_sequences").__call__(var2, var1.getlocal(2), var1.getlocal(1));
            }
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(989);
            if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
               var1.setline(990);
               var1.getglobal("_unlock_file").__call__(var2, var1.getlocal(4));
            }

            throw (Throwable)var5;
         }

         var1.setline(989);
         if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
            var1.setline(990);
            var1.getglobal("_unlock_file").__call__(var2, var1.getlocal(4));
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(992);
         var1.getglobal("_sync_close").__call__(var2, var1.getlocal(4));
         throw (Throwable)var7;
      }

      var1.setline(992);
      var1.getglobal("_sync_close").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_message$93(PyFrame var1, ThreadState var2) {
      var1.setline(995);
      PyString.fromInterned("Return a Message representation or raise a KeyError.");

      PyObject var10000;
      PyException var3;
      PyObject var4;
      PyObject var10;
      try {
         var1.setline(997);
         if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
            var1.setline(998);
            var10 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(1))), (PyObject)PyString.fromInterned("r+"));
            var1.setlocal(2, var10);
            var3 = null;
         } else {
            var1.setline(1000);
            var10 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(1))), (PyObject)PyString.fromInterned("r"));
            var1.setlocal(2, var10);
            var3 = null;
         }
      } catch (Throwable var8) {
         var3 = Py.setException(var8, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var4 = var3.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1002);
            var4 = var1.getlocal(3).__getattr__("errno");
            var10000 = var4._eq(var1.getglobal("errno").__getattr__("ENOENT"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1003);
               throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("No message with key: %s")._mod(var1.getlocal(1))));
            }

            var1.setline(1005);
            throw Py.makeException();
         }

         throw var3;
      }

      var3 = null;

      PyObject var5;
      try {
         var1.setline(1007);
         if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
            var1.setline(1008);
            var1.getglobal("_lock_file").__call__(var2, var1.getlocal(2));
         }

         var4 = null;

         try {
            var1.setline(1010);
            var5 = var1.getglobal("MHMessage").__call__(var2, var1.getlocal(2));
            var1.setlocal(4, var5);
            var5 = null;
         } catch (Throwable var7) {
            Py.addTraceback(var7, var1);
            var1.setline(1012);
            if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
               var1.setline(1013);
               var1.getglobal("_unlock_file").__call__(var2, var1.getlocal(2));
            }

            throw (Throwable)var7;
         }

         var1.setline(1012);
         if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
            var1.setline(1013);
            var1.getglobal("_unlock_file").__call__(var2, var1.getlocal(2));
         }
      } catch (Throwable var9) {
         Py.addTraceback(var9, var1);
         var1.setline(1015);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var9;
      }

      var1.setline(1015);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1016);
      var10 = var1.getlocal(0).__getattr__("get_sequences").__call__(var2).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(1016);
         var4 = var10.__iternext__();
         if (var4 == null) {
            var1.setline(1019);
            var10 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var10;
         }

         PyObject[] var11 = Py.unpackSequence(var4, 2);
         PyObject var6 = var11[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var11[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(1017);
         var5 = var1.getlocal(1);
         var10000 = var5._in(var1.getlocal(6));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1018);
            var1.getlocal(4).__getattr__("add_sequence").__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject get_string$94(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject get_file$95(PyFrame var1, ThreadState var2) {
      var1.setline(1045);
      PyString.fromInterned("Return a file-like representation or raise a KeyError.");

      PyException var3;
      PyObject var6;
      try {
         var1.setline(1047);
         var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(1))), (PyObject)PyString.fromInterned("rb"));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            PyObject var4 = var3.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1049);
            var4 = var1.getlocal(3).__getattr__("errno");
            PyObject var10000 = var4._eq(var1.getglobal("errno").__getattr__("ENOENT"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1050);
               throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("No message with key: %s")._mod(var1.getlocal(1))));
            }

            var1.setline(1052);
            throw Py.makeException();
         }

         throw var3;
      }

      var1.setline(1053);
      var6 = var1.getglobal("_ProxyFile").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject iterkeys$96(PyFrame var1, ThreadState var2) {
      var1.setline(1056);
      PyString.fromInterned("Return an iterator over keys.");
      var1.setline(1057);
      PyObject var10000 = var1.getglobal("iter");
      PyObject var10002 = var1.getglobal("sorted");
      var1.setline(1057);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, f$97, (PyObject)null);
      PyObject var10004 = var4.__call__(var2, var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0).__getattr__("_path")).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var5 = var10000.__call__(var2, var10002.__call__(var2, var10004));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$97(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1057);
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
         var1.setline(1057);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(1058);
      } while(!var1.getlocal(1).__getattr__("isdigit").__call__(var2).__nonzero__());

      var1.setline(1057);
      var1.setline(1057);
      var6 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
      var1.f_lasti = 1;
      var5 = new Object[]{null, null, null, var3, var4};
      var1.f_savedlocals = var5;
      return var6;
   }

   public PyObject has_key$98(PyFrame var1, ThreadState var2) {
      var1.setline(1061);
      PyString.fromInterned("Return True if the keyed message exists, False otherwise.");
      var1.setline(1062);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(1))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$99(PyFrame var1, ThreadState var2) {
      var1.setline(1065);
      PyString.fromInterned("Return a count of messages in the mailbox.");
      var1.setline(1066);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("iterkeys").__call__(var2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lock$100(PyFrame var1, ThreadState var2) {
      var1.setline(1069);
      PyString.fromInterned("Lock the mailbox.");
      var1.setline(1070);
      if (var1.getlocal(0).__getattr__("_locked").__not__().__nonzero__()) {
         var1.setline(1071);
         PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned(".mh_sequences")), (PyObject)PyString.fromInterned("rb+"));
         var1.getlocal(0).__setattr__("_file", var3);
         var3 = null;
         var1.setline(1072);
         var1.getglobal("_lock_file").__call__(var2, var1.getlocal(0).__getattr__("_file"));
         var1.setline(1073);
         var3 = var1.getglobal("True");
         var1.getlocal(0).__setattr__("_locked", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unlock$101(PyFrame var1, ThreadState var2) {
      var1.setline(1076);
      PyString.fromInterned("Unlock the mailbox if it is locked.");
      var1.setline(1077);
      if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
         var1.setline(1078);
         var1.getglobal("_unlock_file").__call__(var2, var1.getlocal(0).__getattr__("_file"));
         var1.setline(1079);
         var1.getglobal("_sync_close").__call__(var2, var1.getlocal(0).__getattr__("_file"));
         var1.setline(1080);
         var1.getlocal(0).__delattr__("_file");
         var1.setline(1081);
         PyObject var3 = var1.getglobal("False");
         var1.getlocal(0).__setattr__("_locked", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject flush$102(PyFrame var1, ThreadState var2) {
      var1.setline(1084);
      PyString.fromInterned("Write any pending changes to the disk.");
      var1.setline(1085);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$103(PyFrame var1, ThreadState var2) {
      var1.setline(1088);
      PyString.fromInterned("Flush and close the mailbox.");
      var1.setline(1089);
      if (var1.getlocal(0).__getattr__("_locked").__nonzero__()) {
         var1.setline(1090);
         var1.getlocal(0).__getattr__("unlock").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject list_folders$104(PyFrame var1, ThreadState var2) {
      var1.setline(1093);
      PyString.fromInterned("Return a list of folder names.");
      var1.setline(1094);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1095);
      PyObject var5 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0).__getattr__("_path")).__iter__();

      while(true) {
         var1.setline(1095);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1098);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(1096);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(2))).__nonzero__()) {
            var1.setline(1097);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject get_folder$105(PyFrame var1, ThreadState var2) {
      var1.setline(1101);
      PyString.fromInterned("Return an MH instance for the named folder.");
      var1.setline(1102);
      PyObject var10000 = var1.getglobal("MH");
      PyObject[] var3 = new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(1)), var1.getlocal(0).__getattr__("_factory"), var1.getglobal("False")};
      String[] var4 = new String[]{"factory", "create"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject add_folder$106(PyFrame var1, ThreadState var2) {
      var1.setline(1106);
      PyString.fromInterned("Create a folder and return an MH instance representing it.");
      var1.setline(1107);
      PyObject var10000 = var1.getglobal("MH");
      PyObject[] var3 = new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(1)), var1.getlocal(0).__getattr__("_factory")};
      String[] var4 = new String[]{"factory"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject remove_folder$107(PyFrame var1, ThreadState var2) {
      var1.setline(1111);
      PyString.fromInterned("Delete the named folder, which must be empty.");
      var1.setline(1112);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1113);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1114);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(new PyList(new PyObject[]{PyString.fromInterned(".mh_sequences")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1115);
         var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned(".mh_sequences")));
      } else {
         var1.setline(1116);
         var3 = var1.getlocal(3);
         var10000 = var3._eq(new PyList(Py.EmptyObjects));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1119);
            throw Py.makeException(var1.getglobal("NotEmptyError").__call__(var2, PyString.fromInterned("Folder not empty: %s")._mod(var1.getlocal(0).__getattr__("_path"))));
         }

         var1.setline(1117);
      }

      var1.setline(1120);
      var1.getglobal("os").__getattr__("rmdir").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_sequences$108(PyFrame var1, ThreadState var2) {
      var1.setline(1123);
      PyString.fromInterned("Return a name-to-key-list dictionary to define each sequence.");
      var1.setline(1124);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1125);
      PyObject var13 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned(".mh_sequences")), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(2, var13);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1127);
         PyObject var4 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("keys").__call__(var2));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1128);
         var4 = var1.getlocal(2).__iter__();

         label54:
         while(true) {
            var1.setline(1128);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(4, var5);

            PyException var6;
            try {
               var1.setline(1130);
               PyObject var14 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
               PyObject[] var7 = Py.unpackSequence(var14, 2);
               PyObject var8 = var7[0];
               var1.setlocal(5, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(6, var8);
               var8 = null;
               var6 = null;
               var1.setline(1131);
               var14 = var1.getglobal("set").__call__(var2);
               var1.setlocal(7, var14);
               var6 = null;
               var1.setline(1132);
               var14 = var1.getlocal(6).__getattr__("split").__call__(var2).__iter__();

               while(true) {
                  var1.setline(1132);
                  PyObject var15 = var14.__iternext__();
                  PyObject var10000;
                  if (var15 == null) {
                     var1.setline(1138);
                     PyList var19 = new PyList();
                     var14 = var19.__getattr__("append");
                     var1.setlocal(12, var14);
                     var6 = null;
                     var1.setline(1138);
                     var14 = var1.getglobal("sorted").__call__(var2, var1.getlocal(7)).__iter__();

                     while(true) {
                        var1.setline(1138);
                        var15 = var14.__iternext__();
                        if (var15 == null) {
                           var1.setline(1138);
                           var1.dellocal(12);
                           PyList var16 = var19;
                           var1.getlocal(1).__setitem__((PyObject)var1.getlocal(5), var16);
                           var6 = null;
                           var1.setline(1140);
                           var14 = var1.getglobal("len").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(5)));
                           var10000 = var14._eq(Py.newInteger(0));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(1141);
                              var1.getlocal(1).__delitem__(var1.getlocal(5));
                           }
                           continue label54;
                        }

                        var1.setlocal(13, var15);
                        var1.setline(1139);
                        var8 = var1.getlocal(13);
                        PyObject var10001 = var8._in(var1.getlocal(3));
                        var8 = null;
                        if (var10001.__nonzero__()) {
                           var1.setline(1138);
                           var1.getlocal(12).__call__(var2, var1.getlocal(13));
                        }
                     }
                  }

                  var1.setlocal(8, var15);
                  var1.setline(1133);
                  if (var1.getlocal(8).__getattr__("isdigit").__call__(var2).__nonzero__()) {
                     var1.setline(1134);
                     var1.getlocal(7).__getattr__("add").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(8)));
                  } else {
                     var1.setline(1136);
                     var1.setline(1136);
                     PyObject[] var17 = Py.EmptyObjects;
                     PyFunction var9 = new PyFunction(var1.f_globals, var17, f$109, (PyObject)null);
                     var10000 = var9.__call__(var2, var1.getlocal(8).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-")).__iter__());
                     Arrays.fill(var17, (Object)null);
                     var8 = var10000;
                     PyObject[] var18 = Py.unpackSequence(var8, 2);
                     PyObject var10 = var18[0];
                     var1.setlocal(9, var10);
                     var10 = null;
                     var10 = var18[1];
                     var1.setlocal(10, var10);
                     var10 = null;
                     var8 = null;
                     var1.setline(1137);
                     var1.getlocal(7).__getattr__("update").__call__(var2, var1.getglobal("range").__call__(var2, var1.getlocal(9), var1.getlocal(10)._add(Py.newInteger(1))));
                  }
               }
            } catch (Throwable var11) {
               var6 = Py.setException(var11, var1);
               if (var6.match(var1.getglobal("ValueError"))) {
                  var1.setline(1143);
                  throw Py.makeException(var1.getglobal("FormatError").__call__(var2, PyString.fromInterned("Invalid sequence specification: %s")._mod(var1.getlocal(4).__getattr__("rstrip").__call__(var2))));
               }

               throw var6;
            }
         }
      } catch (Throwable var12) {
         Py.addTraceback(var12, var1);
         var1.setline(1146);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var12;
      }

      var1.setline(1146);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(1147);
      var13 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var13;
   }

   public PyObject f$109(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1136);
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

      var1.setline(1136);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(1136);
         var1.setline(1136);
         var6 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject set_sequences$110(PyFrame var1, ThreadState var2) {
      var1.setline(1150);
      PyString.fromInterned("Set sequences using the given name-to-key-list dictionary.");
      var1.setline(1151);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_path"), (PyObject)PyString.fromInterned(".mh_sequences")), (PyObject)PyString.fromInterned("r+"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1153);
         var1.getglobal("os").__getattr__("close").__call__(var2, var1.getglobal("os").__getattr__("open").__call__(var2, var1.getlocal(2).__getattr__("name"), var1.getglobal("os").__getattr__("O_WRONLY")._or(var1.getglobal("os").__getattr__("O_TRUNC"))));
         var1.setline(1154);
         PyObject var4 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(1154);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var1.setline(1155);
            PyObject var10 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
            PyObject var10000 = var10._eq(Py.newInteger(0));
            var6 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(1157);
               var1.getlocal(2).__getattr__("write").__call__(var2, PyString.fromInterned("%s:")._mod(var1.getlocal(3)));
               var1.setline(1158);
               var10 = var1.getglobal("None");
               var1.setlocal(5, var10);
               var6 = null;
               var1.setline(1159);
               var10 = var1.getglobal("False");
               var1.setlocal(6, var10);
               var6 = null;
               var1.setline(1160);
               var10 = var1.getglobal("sorted").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(4))).__iter__();

               while(true) {
                  var1.setline(1160);
                  var7 = var10.__iternext__();
                  if (var7 == null) {
                     var1.setline(1171);
                     if (var1.getlocal(6).__nonzero__()) {
                        var1.setline(1172);
                        var1.getlocal(2).__getattr__("write").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(5))._add(PyString.fromInterned("\n")));
                     } else {
                        var1.setline(1174);
                        var1.getlocal(2).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
                     }
                     break;
                  }

                  var1.setlocal(7, var7);
                  var1.setline(1161);
                  PyObject var8 = var1.getlocal(7)._sub(Py.newInteger(1));
                  var10000 = var8._eq(var1.getlocal(5));
                  var8 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1162);
                     if (var1.getlocal(6).__not__().__nonzero__()) {
                        var1.setline(1163);
                        var8 = var1.getglobal("True");
                        var1.setlocal(6, var8);
                        var8 = null;
                        var1.setline(1164);
                        var1.getlocal(2).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"));
                     }
                  } else {
                     var1.setline(1165);
                     if (var1.getlocal(6).__nonzero__()) {
                        var1.setline(1166);
                        var8 = var1.getglobal("False");
                        var1.setlocal(6, var8);
                        var8 = null;
                        var1.setline(1167);
                        var1.getlocal(2).__getattr__("write").__call__(var2, PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(7)})));
                     } else {
                        var1.setline(1169);
                        var1.getlocal(2).__getattr__("write").__call__(var2, PyString.fromInterned(" %s")._mod(var1.getlocal(7)));
                     }
                  }

                  var1.setline(1170);
                  var8 = var1.getlocal(7);
                  var1.setlocal(5, var8);
                  var8 = null;
               }
            }
         }
      } catch (Throwable var9) {
         Py.addTraceback(var9, var1);
         var1.setline(1176);
         var1.getglobal("_sync_close").__call__(var2, var1.getlocal(2));
         throw (Throwable)var9;
      }

      var1.setline(1176);
      var1.getglobal("_sync_close").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pack$111(PyFrame var1, ThreadState var2) {
      var1.setline(1179);
      PyString.fromInterned("Re-name messages to eliminate numbering gaps. Invalidates keys.");
      var1.setline(1180);
      PyObject var3 = var1.getlocal(0).__getattr__("get_sequences").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1181);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(1182);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var10);
      var3 = null;
      var1.setline(1183);
      var3 = var1.getlocal(0).__getattr__("iterkeys").__call__(var2).__iter__();

      while(true) {
         var1.setline(1183);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         PyObject var10000;
         if (var4 == null) {
            var1.setline(1194);
            var3 = var1.getlocal(2)._add(Py.newInteger(1));
            var1.getlocal(0).__setattr__("_next_key", var3);
            var3 = null;
            var1.setline(1195);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1196);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(1197);
               var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

               while(true) {
                  var1.setline(1197);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(1201);
                     var1.getlocal(0).__getattr__("set_sequences").__call__(var2, var1.getlocal(1));
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  PyObject[] var11 = Py.unpackSequence(var4, 2);
                  PyObject var6 = var11[0];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var6 = var11[1];
                  var1.setlocal(6, var6);
                  var6 = null;
                  var1.setline(1198);
                  var5 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(1198);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        break;
                     }

                     PyObject[] var7 = Py.unpackSequence(var6, 2);
                     PyObject var8 = var7[0];
                     var1.setlocal(7, var8);
                     var8 = null;
                     var8 = var7[1];
                     var1.setlocal(8, var8);
                     var8 = null;
                     var1.setline(1199);
                     PyObject var12 = var1.getlocal(7);
                     var10000 = var12._in(var1.getlocal(6));
                     var7 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1200);
                        var12 = var1.getlocal(8);
                        var1.getlocal(6).__setitem__(var1.getlocal(6).__getattr__("index").__call__(var2, var1.getlocal(7)), var12);
                        var7 = null;
                     }
                  }
               }
            }
         }

         var1.setlocal(4, var4);
         var1.setline(1184);
         var5 = var1.getlocal(4)._sub(Py.newInteger(1));
         var10000 = var5._ne(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1185);
            var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(2)._add(Py.newInteger(1))})));
            var1.setline(1186);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("link")).__nonzero__()) {
               var1.setline(1187);
               var1.getglobal("os").__getattr__("link").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(4))), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(2)._add(Py.newInteger(1)))));
               var1.setline(1189);
               var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(4))));
            } else {
               var1.setline(1191);
               var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(4))), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_path"), var1.getglobal("str").__call__(var2, var1.getlocal(2)._add(Py.newInteger(1)))));
            }
         }

         var1.setline(1193);
         var5 = var1.getlocal(2);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(2, var5);
      }
   }

   public PyObject _dump_sequences$112(PyFrame var1, ThreadState var2) {
      var1.setline(1204);
      PyString.fromInterned("Inspect a new MHMessage and update sequences appropriately.");
      var1.setline(1205);
      PyObject var3 = var1.getlocal(1).__getattr__("get_sequences").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1206);
      var3 = var1.getlocal(0).__getattr__("get_sequences").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1207);
      var3 = var1.getlocal(4).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(1207);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         PyObject[] var5;
         PyObject var7;
         if (var4 == null) {
            var1.setline(1212);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(1212);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1215);
                  var1.getlocal(0).__getattr__("set_sequences").__call__(var2, var1.getlocal(4));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(7, var4);
               var1.setline(1213);
               var7 = var1.getlocal(7);
               var10000 = var7._notin(var1.getlocal(4));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1214);
                  PyList var8 = new PyList(new PyObject[]{var1.getlocal(2)});
                  var1.getlocal(4).__setitem__((PyObject)var1.getlocal(7), var8);
                  var5 = null;
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(1208);
         var7 = var1.getlocal(5);
         var10000 = var7._in(var1.getlocal(3));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1209);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(2));
         } else {
            var1.setline(1210);
            var7 = var1.getlocal(2);
            var10000 = var7._in(var1.getlocal(6));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1211);
               var1.getlocal(6).__delitem__(var1.getlocal(6).__getattr__("index").__call__(var2, var1.getlocal(2)));
            }
         }
      }
   }

   public PyObject Babyl$113(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An Rmail-style Babyl mailbox."));
      var1.setline(1219);
      PyString.fromInterned("An Rmail-style Babyl mailbox.");
      var1.setline(1221);
      PyObject var3 = var1.getname("frozenset").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("unseen"), PyString.fromInterned("deleted"), PyString.fromInterned("filed"), PyString.fromInterned("answered"), PyString.fromInterned("forwarded"), PyString.fromInterned("edited"), PyString.fromInterned("resent")})));
      var1.setlocal("_special_labels", var3);
      var3 = null;
      var1.setline(1224);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("True")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$114, PyString.fromInterned("Initialize a Babyl mailbox."));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1229);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, add$115, PyString.fromInterned("Add message and return assigned key."));
      var1.setlocal("add", var5);
      var3 = null;
      var1.setline(1236);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, remove$116, PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("remove", var5);
      var3 = null;
      var1.setline(1242);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __setitem__$117, PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist."));
      var1.setlocal("__setitem__", var5);
      var3 = null;
      var1.setline(1248);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_message$118, PyString.fromInterned("Return a Message representation or raise a KeyError."));
      var1.setlocal("get_message", var5);
      var3 = null;
      var1.setline(1273);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_string$119, PyString.fromInterned("Return a string representation or raise a KeyError."));
      var1.setlocal("get_string", var5);
      var3 = null;
      var1.setline(1292);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_file$120, PyString.fromInterned("Return a file-like representation or raise a KeyError."));
      var1.setlocal("get_file", var5);
      var3 = null;
      var1.setline(1297);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_labels$121, PyString.fromInterned("Return a list of user-defined labels in the mailbox."));
      var1.setlocal("get_labels", var5);
      var3 = null;
      var1.setline(1306);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _generate_toc$122, PyString.fromInterned("Generate key-to-(start, stop) table of contents."));
      var1.setlocal("_generate_toc", var5);
      var3 = null;
      var1.setline(1336);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _pre_mailbox_hook$123, PyString.fromInterned("Called before writing the mailbox to file f."));
      var1.setlocal("_pre_mailbox_hook", var5);
      var3 = null;
      var1.setline(1342);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _pre_message_hook$124, PyString.fromInterned("Called before writing each message to file f."));
      var1.setlocal("_pre_message_hook", var5);
      var3 = null;
      var1.setline(1346);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _post_message_hook$125, PyString.fromInterned("Called after writing each message to file f."));
      var1.setlocal("_post_message_hook", var5);
      var3 = null;
      var1.setline(1350);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _install_message$126, PyString.fromInterned("Write message contents and return (start, stop)."));
      var1.setlocal("_install_message", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$114(PyFrame var1, ThreadState var2) {
      var1.setline(1225);
      PyString.fromInterned("Initialize a Babyl mailbox.");
      var1.setline(1226);
      var1.getglobal("_singlefileMailbox").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(1227);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_labels", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add$115(PyFrame var1, ThreadState var2) {
      var1.setline(1230);
      PyString.fromInterned("Add message and return assigned key.");
      var1.setline(1231);
      PyObject var3 = var1.getglobal("_singlefileMailbox").__getattr__("add").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1232);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BabylMessage")).__nonzero__()) {
         var1.setline(1233);
         var3 = var1.getlocal(1).__getattr__("get_labels").__call__(var2);
         var1.getlocal(0).__getattr__("_labels").__setitem__(var1.getlocal(2), var3);
         var3 = null;
      }

      var1.setline(1234);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove$116(PyFrame var1, ThreadState var2) {
      var1.setline(1237);
      PyString.fromInterned("Remove the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(1238);
      var1.getglobal("_singlefileMailbox").__getattr__("remove").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(1239);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_labels"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1240);
         var1.getlocal(0).__getattr__("_labels").__delitem__(var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __setitem__$117(PyFrame var1, ThreadState var2) {
      var1.setline(1243);
      PyString.fromInterned("Replace the keyed message; raise KeyError if it doesn't exist.");
      var1.setline(1244);
      var1.getglobal("_singlefileMailbox").__getattr__("__setitem__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(1245);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("BabylMessage")).__nonzero__()) {
         var1.setline(1246);
         PyObject var3 = var1.getlocal(2).__getattr__("get_labels").__call__(var2);
         var1.getlocal(0).__getattr__("_labels").__setitem__(var1.getlocal(1), var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_message$118(PyFrame var1, ThreadState var2) {
      var1.setline(1249);
      PyString.fromInterned("Return a Message representation or raise a KeyError.");
      var1.setline(1250);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1251);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__(var2, var1.getlocal(2));
      var1.setline(1252);
      var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
      var1.setline(1253);
      var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(1254);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(1255);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1256);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(PyString.fromInterned("*** EOOH ***")._add(var1.getglobal("os").__getattr__("linesep")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(5);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(1258);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("linesep"), (PyObject)PyString.fromInterned("\n")));
      }

      var1.setline(1259);
      var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;

      while(true) {
         var1.setline(1260);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(1261);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1262);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(var1.getglobal("os").__getattr__("linesep"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(5);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(1264);
         var1.getlocal(6).__getattr__("write").__call__(var2, var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("linesep"), (PyObject)PyString.fromInterned("\n")));
      }

      var1.setline(1265);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("read").__call__(var2, var1.getlocal(3)._sub(var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2))).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("linesep"), (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1267);
      var3 = var1.getglobal("BabylMessage").__call__(var2, var1.getlocal(4).__getattr__("getvalue").__call__(var2)._add(var1.getlocal(7)));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1268);
      var1.getlocal(8).__getattr__("set_visible").__call__(var2, var1.getlocal(6).__getattr__("getvalue").__call__(var2));
      var1.setline(1269);
      var3 = var1.getlocal(1);
      var10000 = var3._in(var1.getlocal(0).__getattr__("_labels"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1270);
         var1.getlocal(8).__getattr__("set_labels").__call__(var2, var1.getlocal(0).__getattr__("_labels").__getitem__(var1.getlocal(1)));
      }

      var1.setline(1271);
      var3 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_string$119(PyFrame var1, ThreadState var2) {
      var1.setline(1274);
      PyString.fromInterned("Return a string representation or raise a KeyError.");
      var1.setline(1275);
      PyObject var3 = var1.getlocal(0).__getattr__("_lookup").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1276);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__(var2, var1.getlocal(2));
      var1.setline(1277);
      var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
      var1.setline(1278);
      var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;

      PyObject var10000;
      while(true) {
         var1.setline(1279);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(1280);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1281);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(PyString.fromInterned("*** EOOH ***")._add(var1.getglobal("os").__getattr__("linesep")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(5);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(1283);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(5).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("linesep"), (PyObject)PyString.fromInterned("\n")));
      }

      do {
         var1.setline(1284);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(1285);
         var3 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1286);
         var3 = var1.getlocal(5);
         var10000 = var3._eq(var1.getglobal("os").__getattr__("linesep"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(5);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(1288);
      var3 = var1.getlocal(4).__getattr__("getvalue").__call__(var2)._add(var1.getlocal(0).__getattr__("_file").__getattr__("read").__call__(var2, var1.getlocal(3)._sub(var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2))).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("linesep"), (PyObject)PyString.fromInterned("\n")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_file$120(PyFrame var1, ThreadState var2) {
      var1.setline(1293);
      PyString.fromInterned("Return a file-like representation or raise a KeyError.");
      var1.setline(1294);
      PyObject var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2, var1.getlocal(0).__getattr__("get_string").__call__(var2, var1.getlocal(1)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_labels$121(PyFrame var1, ThreadState var2) {
      var1.setline(1298);
      PyString.fromInterned("Return a list of user-defined labels in the mailbox.");
      var1.setline(1299);
      var1.getlocal(0).__getattr__("_lookup").__call__(var2);
      var1.setline(1300);
      PyObject var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1301);
      var3 = var1.getlocal(0).__getattr__("_labels").__getattr__("values").__call__(var2).__iter__();

      while(true) {
         var1.setline(1301);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1303);
            var1.getlocal(1).__getattr__("difference_update").__call__(var2, var1.getlocal(0).__getattr__("_special_labels"));
            var1.setline(1304);
            var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(1302);
         var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject _generate_toc$122(PyFrame var1, ThreadState var2) {
      var1.setline(1307);
      PyString.fromInterned("Generate key-to-(start, stop) table of contents.");
      var1.setline(1308);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1309);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(1310);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(1311);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var7);
      var3 = null;

      PyObject var9;
      while(true) {
         var1.setline(1312);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(1313);
         var9 = var1.getlocal(3);
         var1.setlocal(5, var9);
         var3 = null;
         var1.setline(1314);
         var9 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2);
         var1.setlocal(6, var9);
         var3 = null;
         var1.setline(1315);
         var9 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
         var1.setlocal(3, var9);
         var3 = null;
         var1.setline(1316);
         var9 = var1.getlocal(6);
         PyObject var10000 = var9._eq(PyString.fromInterned("\u001f\f")._add(var1.getglobal("os").__getattr__("linesep")));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1324);
            var9 = var1.getlocal(6);
            var10000 = var9._eq(PyString.fromInterned("\u001f"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var9 = var1.getlocal(6);
               var10000 = var9._eq(PyString.fromInterned("\u001f")._add(var1.getglobal("os").__getattr__("linesep")));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1325);
               var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var9._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1326);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5)._sub(var1.getglobal("len").__call__(var2, var1.getglobal("os").__getattr__("linesep"))));
               }
            } else {
               var1.setline(1327);
               var9 = var1.getlocal(6);
               var10000 = var9._eq(PyString.fromInterned(""));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1328);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5)._sub(var1.getglobal("len").__call__(var2, var1.getglobal("os").__getattr__("linesep"))));
                  break;
               }
            }
         } else {
            var1.setline(1317);
            var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var9._lt(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1318);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(5)._sub(var1.getglobal("len").__call__(var2, var1.getglobal("os").__getattr__("linesep"))));
            }

            var1.setline(1319);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(3));
            var1.setline(1320);
            PyList var10 = new PyList();
            var9 = var10.__getattr__("append");
            var1.setlocal(8, var9);
            var3 = null;
            var1.setline(1320);
            var9 = var1.getlocal(0).__getattr__("_file").__getattr__("readline").__call__(var2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

            while(true) {
               var1.setline(1320);
               PyObject var8 = var9.__iternext__();
               if (var8 == null) {
                  var1.setline(1320);
                  var1.dellocal(8);
                  var7 = var10;
                  var1.setlocal(7, var7);
                  var3 = null;
                  var1.setline(1323);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(7));
                  break;
               }

               var1.setlocal(9, var8);
               var1.setline(1322);
               var5 = var1.getlocal(9).__getattr__("strip").__call__(var2);
               PyObject var10001 = var5._ne(PyString.fromInterned(""));
               var5 = null;
               if (var10001.__nonzero__()) {
                  var1.setline(1320);
                  var1.getlocal(8).__call__(var2, var1.getlocal(9).__getattr__("strip").__call__(var2));
               }
            }
         }
      }

      var1.setline(1330);
      var9 = var1.getglobal("dict").__call__(var2, var1.getglobal("enumerate").__call__(var2, var1.getglobal("zip").__call__(var2, var1.getlocal(1), var1.getlocal(2))));
      var1.getlocal(0).__setattr__("_toc", var9);
      var3 = null;
      var1.setline(1331);
      var9 = var1.getglobal("dict").__call__(var2, var1.getglobal("enumerate").__call__(var2, var1.getlocal(4)));
      var1.getlocal(0).__setattr__("_labels", var9);
      var3 = null;
      var1.setline(1332);
      var9 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_toc"));
      var1.getlocal(0).__setattr__("_next_key", var9);
      var3 = null;
      var1.setline(1333);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(2));
      var1.setline(1334);
      var9 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("_file_length", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _pre_mailbox_hook$123(PyFrame var1, ThreadState var2) {
      var1.setline(1337);
      PyString.fromInterned("Called before writing the mailbox to file f.");
      var1.setline(1338);
      var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("BABYL OPTIONS:%sVersion: 5%sLabels:%s%s\u001f")._mod(new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("linesep"), var1.getglobal("os").__getattr__("linesep"), PyString.fromInterned(",").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("get_labels").__call__(var2)), var1.getglobal("os").__getattr__("linesep")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _pre_message_hook$124(PyFrame var1, ThreadState var2) {
      var1.setline(1343);
      PyString.fromInterned("Called before writing each message to file f.");
      var1.setline(1344);
      var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("\f")._add(var1.getglobal("os").__getattr__("linesep")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _post_message_hook$125(PyFrame var1, ThreadState var2) {
      var1.setline(1347);
      PyString.fromInterned("Called after writing each message to file f.");
      var1.setline(1348);
      var1.getlocal(1).__getattr__("write").__call__(var2, var1.getglobal("os").__getattr__("linesep")._add(PyString.fromInterned("\u001f")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _install_message$126(PyFrame var1, ThreadState var2) {
      var1.setline(1351);
      PyString.fromInterned("Write message contents and return (start, stop).");
      var1.setline(1352);
      PyObject var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1353);
      PyObject var10000;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BabylMessage")).__nonzero__()) {
         var1.setline(1354);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(1355);
         var6 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(1356);
         var3 = var1.getlocal(1).__getattr__("get_labels").__call__(var2).__iter__();

         label145:
         while(true) {
            var1.setline(1356);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1361);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("1"));
               var1.setline(1362);
               var3 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(1362);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(1364);
                     var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",,"));
                     var1.setline(1365);
                     var3 = var1.getlocal(4).__iter__();

                     while(true) {
                        var1.setline(1365);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           var1.setline(1367);
                           var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getglobal("os").__getattr__("linesep"));
                           break label145;
                        }

                        var1.setlocal(5, var4);
                        var1.setline(1366);
                        var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, PyString.fromInterned(" ")._add(var1.getlocal(5))._add(PyString.fromInterned(",")));
                     }
                  }

                  var1.setlocal(5, var4);
                  var1.setline(1363);
                  var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, PyString.fromInterned(", ")._add(var1.getlocal(5)));
               }
            }

            var1.setlocal(5, var4);
            var1.setline(1357);
            PyObject var5 = var1.getlocal(5);
            var10000 = var5._in(var1.getlocal(0).__getattr__("_special_labels"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1358);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
            } else {
               var1.setline(1360);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      } else {
         var1.setline(1369);
         var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, PyString.fromInterned("1,,")._add(var1.getglobal("os").__getattr__("linesep")));
      }

      var1.setline(1370);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("email").__getattr__("message").__getattr__("Message")).__nonzero__()) {
         var1.setline(1371);
         var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1372);
         var3 = var1.getglobal("email").__getattr__("generator").__getattr__("Generator").__call__((ThreadState)var2, var1.getlocal(6), (PyObject)var1.getglobal("False"), (PyObject)Py.newInteger(0));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1373);
         var1.getlocal(7).__getattr__("flatten").__call__(var2, var1.getlocal(1));
         var1.setline(1374);
         var1.getlocal(6).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));

         do {
            var1.setline(1375);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(1376);
            var3 = var1.getlocal(6).__getattr__("readline").__call__(var2);
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(1377);
            var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(8).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
            var1.setline(1378);
            var3 = var1.getlocal(8);
            var10000 = var3._eq(PyString.fromInterned("\n"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(8);
               var10000 = var3._eq(PyString.fromInterned(""));
               var3 = null;
            }
         } while(!var10000.__nonzero__());

         var1.setline(1380);
         var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, PyString.fromInterned("*** EOOH ***")._add(var1.getglobal("os").__getattr__("linesep")));
         var1.setline(1381);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BabylMessage")).__nonzero__()) {
            var1.setline(1382);
            var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(1383);
            var3 = var1.getglobal("email").__getattr__("generator").__getattr__("Generator").__call__((ThreadState)var2, var1.getlocal(9), (PyObject)var1.getglobal("False"), (PyObject)Py.newInteger(0));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(1384);
            var1.getlocal(10).__getattr__("flatten").__call__(var2, var1.getlocal(1).__getattr__("get_visible").__call__(var2));

            do {
               var1.setline(1385);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(1386);
               var3 = var1.getlocal(9).__getattr__("readline").__call__(var2);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(1387);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(8).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
               var1.setline(1388);
               var3 = var1.getlocal(8);
               var10000 = var3._eq(PyString.fromInterned("\n"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(8);
                  var10000 = var3._eq(PyString.fromInterned(""));
                  var3 = null;
               }
            } while(!var10000.__nonzero__());
         } else {
            var1.setline(1391);
            var1.getlocal(6).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));

            do {
               var1.setline(1392);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(1393);
               var3 = var1.getlocal(6).__getattr__("readline").__call__(var2);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(1394);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(8).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
               var1.setline(1395);
               var3 = var1.getlocal(8);
               var10000 = var3._eq(PyString.fromInterned("\n"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(8);
                  var10000 = var3._eq(PyString.fromInterned(""));
                  var3 = null;
               }
            } while(!var10000.__nonzero__());
         }

         while(true) {
            var1.setline(1397);
            if (!var1.getglobal("True").__nonzero__()) {
               break;
            }

            var1.setline(1398);
            var3 = var1.getlocal(6).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4096));
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(1399);
            var3 = var1.getlocal(11);
            var10000 = var3._eq(PyString.fromInterned(""));
            var3 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(1401);
            var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(11).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
         }
      } else {
         var1.setline(1402);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
            var1.setline(1403);
            var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n\n"))._add(Py.newInteger(2));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(1404);
            var3 = var1.getlocal(12)._sub(Py.newInteger(2));
            var10000 = var3._ne(Py.newInteger(-1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1405);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(12), (PyObject)null).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
               var1.setline(1407);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, PyString.fromInterned("*** EOOH ***")._add(var1.getglobal("os").__getattr__("linesep")));
               var1.setline(1408);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(12), (PyObject)null).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
               var1.setline(1410);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(1).__getslice__(var1.getlocal(12), (PyObject)null, (PyObject)null).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
            } else {
               var1.setline(1413);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, PyString.fromInterned("*** EOOH ***")._add(var1.getglobal("os").__getattr__("linesep"))._add(var1.getglobal("os").__getattr__("linesep")));
               var1.setline(1414);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
            }
         } else {
            var1.setline(1415);
            if (!var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("readline")).__nonzero__()) {
               var1.setline(1434);
               throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Invalid message type: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
            }

            var1.setline(1416);
            var3 = var1.getlocal(1).__getattr__("tell").__call__(var2);
            var1.setlocal(13, var3);
            var3 = null;
            var1.setline(1417);
            var3 = var1.getglobal("True");
            var1.setlocal(14, var3);
            var3 = null;

            while(true) {
               var1.setline(1418);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(1419);
               var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(1420);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(8).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
               var1.setline(1421);
               var3 = var1.getlocal(8);
               var10000 = var3._eq(PyString.fromInterned("\n"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(8);
                  var10000 = var3._eq(PyString.fromInterned(""));
                  var3 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1422);
                  if (!var1.getlocal(14).__nonzero__()) {
                     break;
                  }

                  var1.setline(1423);
                  var3 = var1.getglobal("False");
                  var1.setlocal(14, var3);
                  var3 = null;
                  var1.setline(1424);
                  var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, PyString.fromInterned("*** EOOH ***")._add(var1.getglobal("os").__getattr__("linesep")));
                  var1.setline(1425);
                  var1.getlocal(1).__getattr__("seek").__call__(var2, var1.getlocal(13));
               }
            }

            while(true) {
               var1.setline(1428);
               if (!var1.getglobal("True").__nonzero__()) {
                  break;
               }

               var1.setline(1429);
               var3 = var1.getlocal(1).__getattr__("read").__call__((ThreadState)var2, (PyObject)Py.newInteger(4096));
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(1430);
               var3 = var1.getlocal(11);
               var10000 = var3._eq(PyString.fromInterned(""));
               var3 = null;
               if (var10000.__nonzero__()) {
                  break;
               }

               var1.setline(1432);
               var1.getlocal(0).__getattr__("_file").__getattr__("write").__call__(var2, var1.getlocal(11).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)var1.getglobal("os").__getattr__("linesep")));
            }
         }
      }

      var1.setline(1435);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(1436);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(15)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject Message$127(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Message with mailbox-format-specific properties."));
      var1.setline(1440);
      PyString.fromInterned("Message with mailbox-format-specific properties.");
      var1.setline(1442);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$128, PyString.fromInterned("Initialize a Message instance."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1457);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _become_message$129, PyString.fromInterned("Assume the non-format-specific state of message."));
      var1.setlocal("_become_message", var4);
      var3 = null;
      var1.setline(1463);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _explain_to$130, PyString.fromInterned("Copy format-specific state to message insofar as possible."));
      var1.setlocal("_explain_to", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$128(PyFrame var1, ThreadState var2) {
      var1.setline(1443);
      PyString.fromInterned("Initialize a Message instance.");
      var1.setline(1444);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("email").__getattr__("message").__getattr__("Message")).__nonzero__()) {
         var1.setline(1445);
         var1.getlocal(0).__getattr__("_become_message").__call__(var2, var1.getglobal("copy").__getattr__("deepcopy").__call__(var2, var1.getlocal(1)));
         var1.setline(1446);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Message")).__nonzero__()) {
            var1.setline(1447);
            var1.getlocal(1).__getattr__("_explain_to").__call__(var2, var1.getlocal(0));
         }
      } else {
         var1.setline(1448);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
            var1.setline(1449);
            var1.getlocal(0).__getattr__("_become_message").__call__(var2, var1.getglobal("email").__getattr__("message_from_string").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(1450);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("read")).__nonzero__()) {
               var1.setline(1451);
               var1.getlocal(0).__getattr__("_become_message").__call__(var2, var1.getglobal("email").__getattr__("message_from_file").__call__(var2, var1.getlocal(1)));
            } else {
               var1.setline(1452);
               PyObject var3 = var1.getlocal(1);
               PyObject var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(1455);
                  throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Invalid message type: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
               }

               var1.setline(1453);
               var1.getglobal("email").__getattr__("message").__getattr__("Message").__getattr__("__init__").__call__(var2, var1.getlocal(0));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _become_message$129(PyFrame var1, ThreadState var2) {
      var1.setline(1458);
      PyString.fromInterned("Assume the non-format-specific state of message.");
      var1.setline(1459);
      PyObject var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("_headers"), PyString.fromInterned("_unixfrom"), PyString.fromInterned("_payload"), PyString.fromInterned("_charset"), PyString.fromInterned("preamble"), PyString.fromInterned("epilogue"), PyString.fromInterned("defects"), PyString.fromInterned("_default_type")})).__iter__();

      while(true) {
         var1.setline(1459);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1461);
         PyObject var5 = var1.getlocal(1).__getattr__("__dict__").__getitem__(var1.getlocal(2));
         var1.getlocal(0).__getattr__("__dict__").__setitem__(var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject _explain_to$130(PyFrame var1, ThreadState var2) {
      var1.setline(1464);
      PyString.fromInterned("Copy format-specific state to message insofar as possible.");
      var1.setline(1465);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Message")).__nonzero__()) {
         var1.setline(1466);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1468);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cannot convert to specified type")));
      }
   }

   public PyObject MaildirMessage$131(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Message with Maildir-specific properties."));
      var1.setline(1472);
      PyString.fromInterned("Message with Maildir-specific properties.");
      var1.setline(1474);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$132, PyString.fromInterned("Initialize a MaildirMessage instance."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1481);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_subdir$133, PyString.fromInterned("Return 'new' or 'cur'."));
      var1.setlocal("get_subdir", var4);
      var3 = null;
      var1.setline(1485);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_subdir$134, PyString.fromInterned("Set subdir to 'new' or 'cur'."));
      var1.setlocal("set_subdir", var4);
      var3 = null;
      var1.setline(1492);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_flags$135, PyString.fromInterned("Return as a string the flags that are set."));
      var1.setlocal("get_flags", var4);
      var3 = null;
      var1.setline(1499);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_flags$136, PyString.fromInterned("Set the given flags and unset all others."));
      var1.setlocal("set_flags", var4);
      var3 = null;
      var1.setline(1503);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_flag$137, PyString.fromInterned("Set the given flag(s) without changing others."));
      var1.setlocal("add_flag", var4);
      var3 = null;
      var1.setline(1507);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove_flag$138, PyString.fromInterned("Unset the given string flag(s) without changing others."));
      var1.setlocal("remove_flag", var4);
      var3 = null;
      var1.setline(1512);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_date$139, PyString.fromInterned("Return delivery date of message, in seconds since the epoch."));
      var1.setlocal("get_date", var4);
      var3 = null;
      var1.setline(1516);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_date$140, PyString.fromInterned("Set delivery date of message, in seconds since the epoch."));
      var1.setlocal("set_date", var4);
      var3 = null;
      var1.setline(1523);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_info$141, PyString.fromInterned("Get the message's \"info\" as a string."));
      var1.setlocal("get_info", var4);
      var3 = null;
      var1.setline(1527);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_info$142, PyString.fromInterned("Set the message's \"info\" string."));
      var1.setlocal("set_info", var4);
      var3 = null;
      var1.setline(1534);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _explain_to$143, PyString.fromInterned("Copy Maildir-specific state to message insofar as possible."));
      var1.setlocal("_explain_to", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$132(PyFrame var1, ThreadState var2) {
      var1.setline(1475);
      PyString.fromInterned("Initialize a MaildirMessage instance.");
      var1.setline(1476);
      PyString var3 = PyString.fromInterned("new");
      var1.getlocal(0).__setattr__((String)"_subdir", var3);
      var3 = null;
      var1.setline(1477);
      var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"_info", var3);
      var3 = null;
      var1.setline(1478);
      PyObject var4 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.getlocal(0).__setattr__("_date", var4);
      var3 = null;
      var1.setline(1479);
      var1.getglobal("Message").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_subdir$133(PyFrame var1, ThreadState var2) {
      var1.setline(1482);
      PyString.fromInterned("Return 'new' or 'cur'.");
      var1.setline(1483);
      PyObject var3 = var1.getlocal(0).__getattr__("_subdir");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_subdir$134(PyFrame var1, ThreadState var2) {
      var1.setline(1486);
      PyString.fromInterned("Set subdir to 'new' or 'cur'.");
      var1.setline(1487);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("new"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("cur"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1488);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_subdir", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1490);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("subdir must be 'new' or 'cur': %s")._mod(var1.getlocal(1))));
      }
   }

   public PyObject get_flags$135(PyFrame var1, ThreadState var2) {
      var1.setline(1493);
      PyString.fromInterned("Return as a string the flags that are set.");
      var1.setline(1494);
      if (var1.getlocal(0).__getattr__("_info").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("2,")).__nonzero__()) {
         var1.setline(1495);
         PyObject var4 = var1.getlocal(0).__getattr__("_info").__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1497);
         PyString var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject set_flags$136(PyFrame var1, ThreadState var2) {
      var1.setline(1500);
      PyString.fromInterned("Set the given flags and unset all others.");
      var1.setline(1501);
      PyObject var3 = PyString.fromInterned("2,")._add(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("sorted").__call__(var2, var1.getlocal(1))));
      var1.getlocal(0).__setattr__("_info", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_flag$137(PyFrame var1, ThreadState var2) {
      var1.setline(1504);
      PyString.fromInterned("Set the given flag(s) without changing others.");
      var1.setline(1505);
      var1.getlocal(0).__getattr__("set_flags").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2))._or(var1.getglobal("set").__call__(var2, var1.getlocal(1)))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject remove_flag$138(PyFrame var1, ThreadState var2) {
      var1.setline(1508);
      PyString.fromInterned("Unset the given string flag(s) without changing others.");
      var1.setline(1509);
      PyObject var3 = var1.getlocal(0).__getattr__("get_flags").__call__(var2);
      PyObject var10000 = var3._ne(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1510);
         var1.getlocal(0).__getattr__("set_flags").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2))._sub(var1.getglobal("set").__call__(var2, var1.getlocal(1)))));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_date$139(PyFrame var1, ThreadState var2) {
      var1.setline(1513);
      PyString.fromInterned("Return delivery date of message, in seconds since the epoch.");
      var1.setline(1514);
      PyObject var3 = var1.getlocal(0).__getattr__("_date");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_date$140(PyFrame var1, ThreadState var2) {
      var1.setline(1517);
      PyString.fromInterned("Set delivery date of message, in seconds since the epoch.");

      PyException var3;
      try {
         var1.setline(1519);
         PyObject var5 = var1.getglobal("float").__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("_date", var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("ValueError"))) {
            var1.setline(1521);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("can't convert to float: %s")._mod(var1.getlocal(1))));
         }

         throw var3;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_info$141(PyFrame var1, ThreadState var2) {
      var1.setline(1524);
      PyString.fromInterned("Get the message's \"info\" as a string.");
      var1.setline(1525);
      PyObject var3 = var1.getlocal(0).__getattr__("_info");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_info$142(PyFrame var1, ThreadState var2) {
      var1.setline(1528);
      PyString.fromInterned("Set the message's \"info\" string.");
      var1.setline(1529);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(1530);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("_info", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1532);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("info must be a string: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
      }
   }

   public PyObject _explain_to$143(PyFrame var1, ThreadState var2) {
      var1.setline(1535);
      PyString.fromInterned("Copy Maildir-specific state to message insofar as possible.");
      var1.setline(1536);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MaildirMessage")).__nonzero__()) {
         var1.setline(1537);
         var1.getlocal(1).__getattr__("set_flags").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2));
         var1.setline(1538);
         var1.getlocal(1).__getattr__("set_subdir").__call__(var2, var1.getlocal(0).__getattr__("get_subdir").__call__(var2));
         var1.setline(1539);
         var1.getlocal(1).__getattr__("set_date").__call__(var2, var1.getlocal(0).__getattr__("get_date").__call__(var2));
      } else {
         var1.setline(1540);
         PyObject var10000;
         PyObject var3;
         PyString var4;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_mboxMMDFMessage")).__nonzero__()) {
            var1.setline(1541);
            var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1542);
            var4 = PyString.fromInterned("S");
            var10000 = var4._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1543);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("R"));
            }

            var1.setline(1544);
            var3 = var1.getlocal(0).__getattr__("get_subdir").__call__(var2);
            var10000 = var3._eq(PyString.fromInterned("cur"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1545);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("O"));
            }

            var1.setline(1546);
            var4 = PyString.fromInterned("T");
            var10000 = var4._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1547);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("D"));
            }

            var1.setline(1548);
            var4 = PyString.fromInterned("F");
            var10000 = var4._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1549);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("F"));
            }

            var1.setline(1550);
            var4 = PyString.fromInterned("R");
            var10000 = var4._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1551);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A"));
            }

            var1.setline(1552);
            var1.getlocal(1).__getattr__("set_from").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MAILER-DAEMON"), (PyObject)var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(0).__getattr__("get_date").__call__(var2)));
         } else {
            var1.setline(1553);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MHMessage")).__nonzero__()) {
               var1.setline(1554);
               var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(1555);
               var4 = PyString.fromInterned("S");
               var10000 = var4._notin(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1556);
                  var1.getlocal(1).__getattr__("add_sequence").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unseen"));
               }

               var1.setline(1557);
               var4 = PyString.fromInterned("R");
               var10000 = var4._in(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1558);
                  var1.getlocal(1).__getattr__("add_sequence").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("replied"));
               }

               var1.setline(1559);
               var4 = PyString.fromInterned("F");
               var10000 = var4._in(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1560);
                  var1.getlocal(1).__getattr__("add_sequence").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("flagged"));
               }
            } else {
               var1.setline(1561);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BabylMessage")).__nonzero__()) {
                  var1.setline(1562);
                  var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2));
                  var1.setlocal(2, var3);
                  var3 = null;
                  var1.setline(1563);
                  var4 = PyString.fromInterned("S");
                  var10000 = var4._notin(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1564);
                     var1.getlocal(1).__getattr__("add_label").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unseen"));
                  }

                  var1.setline(1565);
                  var4 = PyString.fromInterned("T");
                  var10000 = var4._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1566);
                     var1.getlocal(1).__getattr__("add_label").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("deleted"));
                  }

                  var1.setline(1567);
                  var4 = PyString.fromInterned("R");
                  var10000 = var4._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1568);
                     var1.getlocal(1).__getattr__("add_label").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("answered"));
                  }

                  var1.setline(1569);
                  var4 = PyString.fromInterned("P");
                  var10000 = var4._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1570);
                     var1.getlocal(1).__getattr__("add_label").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("forwarded"));
                  }
               } else {
                  var1.setline(1571);
                  if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Message")).__nonzero__()) {
                     var1.setline(1574);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Cannot convert to specified type: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
                  }

                  var1.setline(1572);
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _mboxMMDFMessage$144(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Message with mbox- or MMDF-specific properties."));
      var1.setline(1579);
      PyString.fromInterned("Message with mbox- or MMDF-specific properties.");
      var1.setline(1581);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$145, PyString.fromInterned("Initialize an mboxMMDFMessage instance."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1590);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_from$146, PyString.fromInterned("Return contents of \"From \" line."));
      var1.setlocal("get_from", var4);
      var3 = null;
      var1.setline(1594);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, set_from$147, PyString.fromInterned("Set \"From \" line, formatting and appending time_ if specified."));
      var1.setlocal("set_from", var4);
      var3 = null;
      var1.setline(1602);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_flags$148, PyString.fromInterned("Return as a string the flags that are set."));
      var1.setlocal("get_flags", var4);
      var3 = null;
      var1.setline(1606);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_flags$149, PyString.fromInterned("Set the given flags and unset all others."));
      var1.setlocal("set_flags", var4);
      var3 = null;
      var1.setline(1628);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_flag$150, PyString.fromInterned("Set the given flag(s) without changing others."));
      var1.setlocal("add_flag", var4);
      var3 = null;
      var1.setline(1632);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove_flag$151, PyString.fromInterned("Unset the given string flag(s) without changing others."));
      var1.setlocal("remove_flag", var4);
      var3 = null;
      var1.setline(1637);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _explain_to$152, PyString.fromInterned("Copy mbox- or MMDF-specific state to message insofar as possible."));
      var1.setlocal("_explain_to", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$145(PyFrame var1, ThreadState var2) {
      var1.setline(1582);
      PyString.fromInterned("Initialize an mboxMMDFMessage instance.");
      var1.setline(1583);
      var1.getlocal(0).__getattr__("set_from").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MAILER-DAEMON"), (PyObject)var1.getglobal("True"));
      var1.setline(1584);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("email").__getattr__("message").__getattr__("Message")).__nonzero__()) {
         var1.setline(1585);
         PyObject var3 = var1.getlocal(1).__getattr__("get_unixfrom").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1586);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("From "));
         }

         if (var10000.__nonzero__()) {
            var1.setline(1587);
            var1.getlocal(0).__getattr__("set_from").__call__(var2, var1.getlocal(2).__getslice__(Py.newInteger(5), (PyObject)null, (PyObject)null));
         }
      }

      var1.setline(1588);
      var1.getglobal("Message").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_from$146(PyFrame var1, ThreadState var2) {
      var1.setline(1591);
      PyString.fromInterned("Return contents of \"From \" line.");
      var1.setline(1592);
      PyObject var3 = var1.getlocal(0).__getattr__("_from");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_from$147(PyFrame var1, ThreadState var2) {
      var1.setline(1595);
      PyString.fromInterned("Set \"From \" line, formatting and appending time_ if specified.");
      var1.setline(1596);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1597);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("True"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1598);
            var3 = var1.getglobal("time").__getattr__("gmtime").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(1599);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(PyString.fromInterned(" ")._add(var1.getglobal("time").__getattr__("asctime").__call__(var2, var1.getlocal(2))));
         var1.setlocal(1, var3);
      }

      var1.setline(1600);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_from", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_flags$148(PyFrame var1, ThreadState var2) {
      var1.setline(1603);
      PyString.fromInterned("Return as a string the flags that are set.");
      var1.setline(1604);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Status"), (PyObject)PyString.fromInterned(""))._add(var1.getlocal(0).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Status"), (PyObject)PyString.fromInterned("")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_flags$149(PyFrame var1, ThreadState var2) {
      var1.setline(1607);
      PyString.fromInterned("Set the given flags and unset all others.");
      var1.setline(1608);
      PyObject var3 = var1.getglobal("set").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1609);
      PyTuple var8 = new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("")});
      PyObject[] var4 = Py.unpackSequence(var8, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1610);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("R"), PyString.fromInterned("O")})).__iter__();

      while(true) {
         var1.setline(1610);
         PyObject var9 = var3.__iternext__();
         PyObject var10000;
         if (var9 == null) {
            var1.setline(1614);
            var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("D"), PyString.fromInterned("F"), PyString.fromInterned("A")})).__iter__();

            while(true) {
               var1.setline(1614);
               var9 = var3.__iternext__();
               if (var9 == null) {
                  var1.setline(1618);
                  var3 = var1.getlocal(3);
                  var3 = var3._iadd(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("sorted").__call__(var2, var1.getlocal(1))));
                  var1.setlocal(3, var3);

                  PyException var10;
                  try {
                     var1.setline(1620);
                     var1.getlocal(0).__getattr__("replace_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Status"), (PyObject)var1.getlocal(2));
                  } catch (Throwable var7) {
                     var10 = Py.setException(var7, var1);
                     if (!var10.match(var1.getglobal("KeyError"))) {
                        throw var10;
                     }

                     var1.setline(1622);
                     var1.getlocal(0).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Status"), (PyObject)var1.getlocal(2));
                  }

                  try {
                     var1.setline(1624);
                     var1.getlocal(0).__getattr__("replace_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Status"), (PyObject)var1.getlocal(3));
                  } catch (Throwable var6) {
                     var10 = Py.setException(var6, var1);
                     if (!var10.match(var1.getglobal("KeyError"))) {
                        throw var10;
                     }

                     var1.setline(1626);
                     var1.getlocal(0).__getattr__("add_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X-Status"), (PyObject)var1.getlocal(3));
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(4, var9);
               var1.setline(1615);
               var5 = var1.getlocal(4);
               var10000 = var5._in(var1.getlocal(1));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1616);
                  var5 = var1.getlocal(3);
                  var5 = var5._iadd(var1.getlocal(4));
                  var1.setlocal(3, var5);
                  var1.setline(1617);
                  var1.getlocal(1).__getattr__("remove").__call__(var2, var1.getlocal(4));
               }
            }
         }

         var1.setlocal(4, var9);
         var1.setline(1611);
         var5 = var1.getlocal(4);
         var10000 = var5._in(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1612);
            var5 = var1.getlocal(2);
            var5 = var5._iadd(var1.getlocal(4));
            var1.setlocal(2, var5);
            var1.setline(1613);
            var1.getlocal(1).__getattr__("remove").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject add_flag$150(PyFrame var1, ThreadState var2) {
      var1.setline(1629);
      PyString.fromInterned("Set the given flag(s) without changing others.");
      var1.setline(1630);
      var1.getlocal(0).__getattr__("set_flags").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2))._or(var1.getglobal("set").__call__(var2, var1.getlocal(1)))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject remove_flag$151(PyFrame var1, ThreadState var2) {
      var1.setline(1633);
      PyString.fromInterned("Unset the given string flag(s) without changing others.");
      var1.setline(1634);
      PyString var3 = PyString.fromInterned("Status");
      PyObject var10000 = var3._in(var1.getlocal(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = PyString.fromInterned("X-Status");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1635);
         var1.getlocal(0).__getattr__("set_flags").__call__(var2, PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2))._sub(var1.getglobal("set").__call__(var2, var1.getlocal(1)))));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _explain_to$152(PyFrame var1, ThreadState var2) {
      var1.setline(1638);
      PyString.fromInterned("Copy mbox- or MMDF-specific state to message insofar as possible.");
      var1.setline(1639);
      PyObject var10000;
      PyObject var3;
      PyString var5;
      if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MaildirMessage")).__nonzero__()) {
         var1.setline(1659);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_mboxMMDFMessage")).__nonzero__()) {
            var1.setline(1660);
            var1.getlocal(1).__getattr__("set_flags").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2));
            var1.setline(1661);
            var1.getlocal(1).__getattr__("set_from").__call__(var2, var1.getlocal(0).__getattr__("get_from").__call__(var2));
         } else {
            var1.setline(1662);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MHMessage")).__nonzero__()) {
               var1.setline(1663);
               var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(1664);
               var5 = PyString.fromInterned("R");
               var10000 = var5._notin(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1665);
                  var1.getlocal(1).__getattr__("add_sequence").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unseen"));
               }

               var1.setline(1666);
               var5 = PyString.fromInterned("A");
               var10000 = var5._in(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1667);
                  var1.getlocal(1).__getattr__("add_sequence").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("replied"));
               }

               var1.setline(1668);
               var5 = PyString.fromInterned("F");
               var10000 = var5._in(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1669);
                  var1.getlocal(1).__getattr__("add_sequence").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("flagged"));
               }

               var1.setline(1670);
               var1.getlocal(1).__delitem__((PyObject)PyString.fromInterned("status"));
               var1.setline(1671);
               var1.getlocal(1).__delitem__((PyObject)PyString.fromInterned("x-status"));
            } else {
               var1.setline(1672);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BabylMessage")).__nonzero__()) {
                  var1.setline(1673);
                  var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2));
                  var1.setlocal(2, var3);
                  var3 = null;
                  var1.setline(1674);
                  var5 = PyString.fromInterned("R");
                  var10000 = var5._notin(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1675);
                     var1.getlocal(1).__getattr__("add_label").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unseen"));
                  }

                  var1.setline(1676);
                  var5 = PyString.fromInterned("D");
                  var10000 = var5._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1677);
                     var1.getlocal(1).__getattr__("add_label").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("deleted"));
                  }

                  var1.setline(1678);
                  var5 = PyString.fromInterned("A");
                  var10000 = var5._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1679);
                     var1.getlocal(1).__getattr__("add_label").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("answered"));
                  }

                  var1.setline(1680);
                  var1.getlocal(1).__delitem__((PyObject)PyString.fromInterned("status"));
                  var1.setline(1681);
                  var1.getlocal(1).__delitem__((PyObject)PyString.fromInterned("x-status"));
               } else {
                  var1.setline(1682);
                  if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Message")).__nonzero__()) {
                     var1.setline(1685);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Cannot convert to specified type: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
                  }

                  var1.setline(1683);
               }
            }
         }
      } else {
         var1.setline(1640);
         var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_flags").__call__(var2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1641);
         var5 = PyString.fromInterned("O");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1642);
            var1.getlocal(1).__getattr__("set_subdir").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cur"));
         }

         var1.setline(1643);
         var5 = PyString.fromInterned("F");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1644);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("F"));
         }

         var1.setline(1645);
         var5 = PyString.fromInterned("A");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1646);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("R"));
         }

         var1.setline(1647);
         var5 = PyString.fromInterned("R");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1648);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("S"));
         }

         var1.setline(1649);
         var5 = PyString.fromInterned("D");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1650);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("T"));
         }

         var1.setline(1651);
         var1.getlocal(1).__delitem__((PyObject)PyString.fromInterned("status"));
         var1.setline(1652);
         var1.getlocal(1).__delitem__((PyObject)PyString.fromInterned("x-status"));
         var1.setline(1653);
         var3 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("get_from").__call__(var2).__getattr__("split").__call__(var2).__getslice__(Py.newInteger(-5), (PyObject)null, (PyObject)null));
         var1.setlocal(3, var3);
         var3 = null;

         try {
            var1.setline(1655);
            var1.getlocal(1).__getattr__("set_date").__call__(var2, var1.getglobal("calendar").__getattr__("timegm").__call__(var2, var1.getglobal("time").__getattr__("strptime").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("%a %b %d %H:%M:%S %Y"))));
         } catch (Throwable var4) {
            PyException var6 = Py.setException(var4, var1);
            if (!var6.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("OverflowError")}))) {
               throw var6;
            }

            var1.setline(1658);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject mboxMessage$153(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Message with mbox-specific properties."));
      var1.setline(1690);
      PyString.fromInterned("Message with mbox-specific properties.");
      return var1.getf_locals();
   }

   public PyObject MHMessage$154(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Message with MH-specific properties."));
      var1.setline(1694);
      PyString.fromInterned("Message with MH-specific properties.");
      var1.setline(1696);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$155, PyString.fromInterned("Initialize an MHMessage instance."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1701);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_sequences$156, PyString.fromInterned("Return a list of sequences that include the message."));
      var1.setlocal("get_sequences", var4);
      var3 = null;
      var1.setline(1705);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_sequences$157, PyString.fromInterned("Set the list of sequences that include the message."));
      var1.setlocal("set_sequences", var4);
      var3 = null;
      var1.setline(1709);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_sequence$158, PyString.fromInterned("Add sequence to list of sequences including the message."));
      var1.setlocal("add_sequence", var4);
      var3 = null;
      var1.setline(1717);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove_sequence$159, PyString.fromInterned("Remove sequence from the list of sequences including the message."));
      var1.setlocal("remove_sequence", var4);
      var3 = null;
      var1.setline(1724);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _explain_to$160, PyString.fromInterned("Copy MH-specific state to message insofar as possible."));
      var1.setlocal("_explain_to", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$155(PyFrame var1, ThreadState var2) {
      var1.setline(1697);
      PyString.fromInterned("Initialize an MHMessage instance.");
      var1.setline(1698);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_sequences", var3);
      var3 = null;
      var1.setline(1699);
      var1.getglobal("Message").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_sequences$156(PyFrame var1, ThreadState var2) {
      var1.setline(1702);
      PyString.fromInterned("Return a list of sequences that include the message.");
      var1.setline(1703);
      PyObject var3 = var1.getlocal(0).__getattr__("_sequences").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_sequences$157(PyFrame var1, ThreadState var2) {
      var1.setline(1706);
      PyString.fromInterned("Set the list of sequences that include the message.");
      var1.setline(1707);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_sequences", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_sequence$158(PyFrame var1, ThreadState var2) {
      var1.setline(1710);
      PyString.fromInterned("Add sequence to list of sequences including the message.");
      var1.setline(1711);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(1712);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_sequences"));
         var3 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(1713);
            var1.getlocal(0).__getattr__("_sequences").__getattr__("append").__call__(var2, var1.getlocal(1));
         }

         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1715);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("sequence must be a string: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
      }
   }

   public PyObject remove_sequence$159(PyFrame var1, ThreadState var2) {
      var1.setline(1718);
      PyString.fromInterned("Remove sequence from the list of sequences including the message.");

      try {
         var1.setline(1720);
         var1.getlocal(0).__getattr__("_sequences").__getattr__("remove").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("ValueError"))) {
            throw var3;
         }

         var1.setline(1722);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _explain_to$160(PyFrame var1, ThreadState var2) {
      var1.setline(1725);
      PyString.fromInterned("Copy MH-specific state to message insofar as possible.");
      var1.setline(1726);
      PyObject var10000;
      PyObject var3;
      PyString var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MaildirMessage")).__nonzero__()) {
         var1.setline(1727);
         var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_sequences").__call__(var2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1728);
         var5 = PyString.fromInterned("unseen");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1729);
            var1.getlocal(1).__getattr__("set_subdir").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cur"));
         } else {
            var1.setline(1731);
            var1.getlocal(1).__getattr__("set_subdir").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cur"));
            var1.setline(1732);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("S"));
         }

         var1.setline(1733);
         var5 = PyString.fromInterned("flagged");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1734);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("F"));
         }

         var1.setline(1735);
         var5 = PyString.fromInterned("replied");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1736);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("R"));
         }
      } else {
         var1.setline(1737);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_mboxMMDFMessage")).__nonzero__()) {
            var1.setline(1738);
            var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_sequences").__call__(var2));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1739);
            var5 = PyString.fromInterned("unseen");
            var10000 = var5._notin(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1740);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RO"));
            } else {
               var1.setline(1742);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("O"));
            }

            var1.setline(1743);
            var5 = PyString.fromInterned("flagged");
            var10000 = var5._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1744);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("F"));
            }

            var1.setline(1745);
            var5 = PyString.fromInterned("replied");
            var10000 = var5._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1746);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A"));
            }
         } else {
            var1.setline(1747);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MHMessage")).__nonzero__()) {
               var1.setline(1748);
               var3 = var1.getlocal(0).__getattr__("get_sequences").__call__(var2).__iter__();

               while(true) {
                  var1.setline(1748);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(3, var4);
                  var1.setline(1749);
                  var1.getlocal(1).__getattr__("add_sequence").__call__(var2, var1.getlocal(3));
               }
            } else {
               var1.setline(1750);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BabylMessage")).__nonzero__()) {
                  var1.setline(1751);
                  var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_sequences").__call__(var2));
                  var1.setlocal(2, var3);
                  var3 = null;
                  var1.setline(1752);
                  var5 = PyString.fromInterned("unseen");
                  var10000 = var5._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1753);
                     var1.getlocal(1).__getattr__("add_label").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unseen"));
                  }

                  var1.setline(1754);
                  var5 = PyString.fromInterned("replied");
                  var10000 = var5._in(var1.getlocal(2));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1755);
                     var1.getlocal(1).__getattr__("add_label").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("answered"));
                  }
               } else {
                  var1.setline(1756);
                  if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Message")).__nonzero__()) {
                     var1.setline(1759);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Cannot convert to specified type: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
                  }

                  var1.setline(1757);
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BabylMessage$161(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Message with Babyl-specific properties."));
      var1.setline(1764);
      PyString.fromInterned("Message with Babyl-specific properties.");
      var1.setline(1766);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$162, PyString.fromInterned("Initialize an BabylMessage instance."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1772);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_labels$163, PyString.fromInterned("Return a list of labels on the message."));
      var1.setlocal("get_labels", var4);
      var3 = null;
      var1.setline(1776);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_labels$164, PyString.fromInterned("Set the list of labels on the message."));
      var1.setlocal("set_labels", var4);
      var3 = null;
      var1.setline(1780);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_label$165, PyString.fromInterned("Add label to list of labels on the message."));
      var1.setlocal("add_label", var4);
      var3 = null;
      var1.setline(1788);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove_label$166, PyString.fromInterned("Remove label from the list of labels on the message."));
      var1.setlocal("remove_label", var4);
      var3 = null;
      var1.setline(1795);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_visible$167, PyString.fromInterned("Return a Message representation of visible headers."));
      var1.setlocal("get_visible", var4);
      var3 = null;
      var1.setline(1799);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_visible$168, PyString.fromInterned("Set the Message representation of visible headers."));
      var1.setlocal("set_visible", var4);
      var3 = null;
      var1.setline(1803);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, update_visible$169, PyString.fromInterned("Update and/or sensibly generate a set of visible headers."));
      var1.setlocal("update_visible", var4);
      var3 = null;
      var1.setline(1814);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _explain_to$170, PyString.fromInterned("Copy Babyl-specific state to message insofar as possible."));
      var1.setlocal("_explain_to", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$162(PyFrame var1, ThreadState var2) {
      var1.setline(1767);
      PyString.fromInterned("Initialize an BabylMessage instance.");
      var1.setline(1768);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_labels", var3);
      var3 = null;
      var1.setline(1769);
      PyObject var4 = var1.getglobal("Message").__call__(var2);
      var1.getlocal(0).__setattr__("_visible", var4);
      var3 = null;
      var1.setline(1770);
      var1.getglobal("Message").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_labels$163(PyFrame var1, ThreadState var2) {
      var1.setline(1773);
      PyString.fromInterned("Return a list of labels on the message.");
      var1.setline(1774);
      PyObject var3 = var1.getlocal(0).__getattr__("_labels").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_labels$164(PyFrame var1, ThreadState var2) {
      var1.setline(1777);
      PyString.fromInterned("Set the list of labels on the message.");
      var1.setline(1778);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_labels", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_label$165(PyFrame var1, ThreadState var2) {
      var1.setline(1781);
      PyString.fromInterned("Add label to list of labels on the message.");
      var1.setline(1782);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(1783);
         PyObject var3 = var1.getlocal(1);
         PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("_labels"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1784);
            var1.getlocal(0).__getattr__("_labels").__getattr__("append").__call__(var2, var1.getlocal(1));
         }

         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(1786);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("label must be a string: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
      }
   }

   public PyObject remove_label$166(PyFrame var1, ThreadState var2) {
      var1.setline(1789);
      PyString.fromInterned("Remove label from the list of labels on the message.");

      try {
         var1.setline(1791);
         var1.getlocal(0).__getattr__("_labels").__getattr__("remove").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("ValueError"))) {
            throw var3;
         }

         var1.setline(1793);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_visible$167(PyFrame var1, ThreadState var2) {
      var1.setline(1796);
      PyString.fromInterned("Return a Message representation of visible headers.");
      var1.setline(1797);
      PyObject var3 = var1.getglobal("Message").__call__(var2, var1.getlocal(0).__getattr__("_visible"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_visible$168(PyFrame var1, ThreadState var2) {
      var1.setline(1800);
      PyString.fromInterned("Set the Message representation of visible headers.");
      var1.setline(1801);
      PyObject var3 = var1.getglobal("Message").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_visible", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject update_visible$169(PyFrame var1, ThreadState var2) {
      var1.setline(1804);
      PyString.fromInterned("Update and/or sensibly generate a set of visible headers.");
      var1.setline(1805);
      PyObject var3 = var1.getlocal(0).__getattr__("_visible").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(1805);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         PyObject var5;
         if (var4 == null) {
            var1.setline(1810);
            var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("Date"), PyString.fromInterned("From"), PyString.fromInterned("Reply-To"), PyString.fromInterned("To"), PyString.fromInterned("CC"), PyString.fromInterned("Subject")})).__iter__();

            while(true) {
               var1.setline(1810);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(1, var4);
               var1.setline(1811);
               var5 = var1.getlocal(1);
               var10000 = var5._in(var1.getlocal(0));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getlocal(1);
                  var10000 = var5._notin(var1.getlocal(0).__getattr__("_visible"));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1812);
                  var5 = var1.getlocal(0).__getitem__(var1.getlocal(1));
                  var1.getlocal(0).__getattr__("_visible").__setitem__(var1.getlocal(1), var5);
                  var5 = null;
               }
            }
         }

         var1.setlocal(1, var4);
         var1.setline(1806);
         var5 = var1.getlocal(1);
         var10000 = var5._in(var1.getlocal(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1807);
            var1.getlocal(0).__getattr__("_visible").__getattr__("replace_header").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getitem__(var1.getlocal(1)));
         } else {
            var1.setline(1809);
            var1.getlocal(0).__getattr__("_visible").__delitem__(var1.getlocal(1));
         }
      }
   }

   public PyObject _explain_to$170(PyFrame var1, ThreadState var2) {
      var1.setline(1815);
      PyString.fromInterned("Copy Babyl-specific state to message insofar as possible.");
      var1.setline(1816);
      PyObject var10000;
      PyObject var3;
      PyString var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MaildirMessage")).__nonzero__()) {
         var1.setline(1817);
         var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_labels").__call__(var2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1818);
         var5 = PyString.fromInterned("unseen");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1819);
            var1.getlocal(1).__getattr__("set_subdir").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cur"));
         } else {
            var1.setline(1821);
            var1.getlocal(1).__getattr__("set_subdir").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cur"));
            var1.setline(1822);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("S"));
         }

         var1.setline(1823);
         var5 = PyString.fromInterned("forwarded");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var5 = PyString.fromInterned("resent");
            var10000 = var5._in(var1.getlocal(2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1824);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("P"));
         }

         var1.setline(1825);
         var5 = PyString.fromInterned("answered");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1826);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("R"));
         }

         var1.setline(1827);
         var5 = PyString.fromInterned("deleted");
         var10000 = var5._in(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1828);
            var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("T"));
         }
      } else {
         var1.setline(1829);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_mboxMMDFMessage")).__nonzero__()) {
            var1.setline(1830);
            var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_labels").__call__(var2));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1831);
            var5 = PyString.fromInterned("unseen");
            var10000 = var5._notin(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1832);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RO"));
            } else {
               var1.setline(1834);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("O"));
            }

            var1.setline(1835);
            var5 = PyString.fromInterned("deleted");
            var10000 = var5._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1836);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("D"));
            }

            var1.setline(1837);
            var5 = PyString.fromInterned("answered");
            var10000 = var5._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1838);
               var1.getlocal(1).__getattr__("add_flag").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A"));
            }
         } else {
            var1.setline(1839);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("MHMessage")).__nonzero__()) {
               var1.setline(1840);
               var3 = var1.getglobal("set").__call__(var2, var1.getlocal(0).__getattr__("get_labels").__call__(var2));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(1841);
               var5 = PyString.fromInterned("unseen");
               var10000 = var5._in(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1842);
                  var1.getlocal(1).__getattr__("add_sequence").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unseen"));
               }

               var1.setline(1843);
               var5 = PyString.fromInterned("answered");
               var10000 = var5._in(var1.getlocal(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1844);
                  var1.getlocal(1).__getattr__("add_sequence").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("replied"));
               }
            } else {
               var1.setline(1845);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("BabylMessage")).__nonzero__()) {
                  var1.setline(1846);
                  var1.getlocal(1).__getattr__("set_visible").__call__(var2, var1.getlocal(0).__getattr__("get_visible").__call__(var2));
                  var1.setline(1847);
                  var3 = var1.getlocal(0).__getattr__("get_labels").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(1847);
                     PyObject var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var1.setlocal(3, var4);
                     var1.setline(1848);
                     var1.getlocal(1).__getattr__("add_label").__call__(var2, var1.getlocal(3));
                  }
               } else {
                  var1.setline(1849);
                  if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Message")).__nonzero__()) {
                     var1.setline(1852);
                     throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Cannot convert to specified type: %s")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(1)))));
                  }

                  var1.setline(1850);
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MMDFMessage$171(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Message with MMDF-specific properties."));
      var1.setline(1857);
      PyString.fromInterned("Message with MMDF-specific properties.");
      return var1.getf_locals();
   }

   public PyObject _ProxyFile$172(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A read-only wrapper of a file."));
      var1.setline(1861);
      PyString.fromInterned("A read-only wrapper of a file.");
      var1.setline(1863);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$173, PyString.fromInterned("Initialize a _ProxyFile."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1871);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, read$174, PyString.fromInterned("Read bytes."));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(1875);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, readline$175, PyString.fromInterned("Read a line."));
      var1.setlocal("readline", var4);
      var3 = null;
      var1.setline(1879);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, readlines$176, PyString.fromInterned("Read multiple lines."));
      var1.setlocal("readlines", var4);
      var3 = null;
      var1.setline(1890);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$177, PyString.fromInterned("Iterate over lines."));
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(1894);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$178, PyString.fromInterned("Return the position."));
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(1898);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$179, PyString.fromInterned("Change position."));
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(1905);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$180, PyString.fromInterned("Close the file."));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(1912);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read$181, PyString.fromInterned("Read size bytes using read_method."));
      var1.setlocal("_read", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$173(PyFrame var1, ThreadState var2) {
      var1.setline(1864);
      PyString.fromInterned("Initialize a _ProxyFile.");
      var1.setline(1865);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_file", var3);
      var3 = null;
      var1.setline(1866);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1867);
         var3 = var1.getlocal(1).__getattr__("tell").__call__(var2);
         var1.getlocal(0).__setattr__("_pos", var3);
         var3 = null;
      } else {
         var1.setline(1869);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("_pos", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read$174(PyFrame var1, ThreadState var2) {
      var1.setline(1872);
      PyString.fromInterned("Read bytes.");
      var1.setline(1873);
      PyObject var3 = var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_file").__getattr__("read"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readline$175(PyFrame var1, ThreadState var2) {
      var1.setline(1876);
      PyString.fromInterned("Read a line.");
      var1.setline(1877);
      PyObject var3 = var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_file").__getattr__("readline"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject readlines$176(PyFrame var1, ThreadState var2) {
      var1.setline(1880);
      PyString.fromInterned("Read multiple lines.");
      var1.setline(1881);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1882);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1882);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(3, var4);
         var1.setline(1883);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
         var1.setline(1884);
         PyObject var5 = var1.getlocal(1);
         PyObject var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1885);
            var5 = var1.getlocal(1);
            var5 = var5._isub(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
            var1.setlocal(1, var5);
            var1.setline(1886);
            var5 = var1.getlocal(1);
            var10000 = var5._le(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               break;
            }
         }
      }

      var1.setline(1888);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject __iter__$177(PyFrame var1, ThreadState var2) {
      var1.setline(1891);
      PyString.fromInterned("Iterate over lines.");
      var1.setline(1892);
      PyObject var3 = var1.getglobal("iter").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("readline"), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject tell$178(PyFrame var1, ThreadState var2) {
      var1.setline(1895);
      PyString.fromInterned("Return the position.");
      var1.setline(1896);
      PyObject var3 = var1.getlocal(0).__getattr__("_pos");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$179(PyFrame var1, ThreadState var2) {
      var1.setline(1899);
      PyString.fromInterned("Change position.");
      var1.setline(1900);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1901);
         var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("_pos"));
      }

      var1.setline(1902);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(1903);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("_pos", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject close$180(PyFrame var1, ThreadState var2) {
      var1.setline(1906);
      PyString.fromInterned("Close the file.");
      var1.setline(1907);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_file")).__nonzero__()) {
         var1.setline(1908);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_file"), (PyObject)PyString.fromInterned("close")).__nonzero__()) {
            var1.setline(1909);
            var1.getlocal(0).__getattr__("_file").__getattr__("close").__call__(var2);
         }

         var1.setline(1910);
         var1.getlocal(0).__delattr__("_file");
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _read$181(PyFrame var1, ThreadState var2) {
      var1.setline(1913);
      PyString.fromInterned("Read size bytes using read_method.");
      var1.setline(1914);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1915);
         PyInteger var4 = Py.newInteger(-1);
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(1916);
      var1.getlocal(0).__getattr__("_file").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("_pos"));
      var1.setline(1917);
      var3 = var1.getlocal(2).__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1918);
      var3 = var1.getlocal(0).__getattr__("_file").__getattr__("tell").__call__(var2);
      var1.getlocal(0).__setattr__("_pos", var3);
      var3 = null;
      var1.setline(1919);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _PartialFile$182(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A read-only wrapper of part of a file."));
      var1.setline(1923);
      PyString.fromInterned("A read-only wrapper of part of a file.");
      var1.setline(1925);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$183, PyString.fromInterned("Initialize a _PartialFile."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1931);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tell$184, PyString.fromInterned("Return the position with respect to start."));
      var1.setlocal("tell", var4);
      var3 = null;
      var1.setline(1935);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, seek$185, PyString.fromInterned("Change position, possibly with respect to start or stop."));
      var1.setlocal("seek", var4);
      var3 = null;
      var1.setline(1945);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read$186, PyString.fromInterned("Read size bytes using read_method, honoring start and stop."));
      var1.setlocal("_read", var4);
      var3 = null;
      var1.setline(1954);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$187, (PyObject)null);
      var1.setlocal("close", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$183(PyFrame var1, ThreadState var2) {
      var1.setline(1926);
      PyString.fromInterned("Initialize a _PartialFile.");
      var1.setline(1927);
      var1.getglobal("_ProxyFile").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.setline(1928);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_start", var3);
      var3 = null;
      var1.setline(1929);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_stop", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tell$184(PyFrame var1, ThreadState var2) {
      var1.setline(1932);
      PyString.fromInterned("Return the position with respect to start.");
      var1.setline(1933);
      PyObject var3 = var1.getglobal("_ProxyFile").__getattr__("tell").__call__(var2, var1.getlocal(0))._sub(var1.getlocal(0).__getattr__("_start"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seek$185(PyFrame var1, ThreadState var2) {
      var1.setline(1936);
      PyString.fromInterned("Change position, possibly with respect to start or stop.");
      var1.setline(1937);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(1938);
         var3 = var1.getlocal(0).__getattr__("_start");
         var1.getlocal(0).__setattr__("_pos", var3);
         var3 = null;
         var1.setline(1939);
         var4 = Py.newInteger(1);
         var1.setlocal(2, var4);
         var3 = null;
      } else {
         var1.setline(1940);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1941);
            var3 = var1.getlocal(0).__getattr__("_stop");
            var1.getlocal(0).__setattr__("_pos", var3);
            var3 = null;
            var1.setline(1942);
            var4 = Py.newInteger(1);
            var1.setlocal(2, var4);
            var3 = null;
         }
      }

      var1.setline(1943);
      var1.getglobal("_ProxyFile").__getattr__("seek").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _read$186(PyFrame var1, ThreadState var2) {
      var1.setline(1946);
      PyString.fromInterned("Read size bytes using read_method, honoring start and stop.");
      var1.setline(1947);
      PyObject var3 = var1.getlocal(0).__getattr__("_stop")._sub(var1.getlocal(0).__getattr__("_pos"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1948);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._le(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1949);
         PyString var5 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(1950);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(1);
            var10000 = var4._lt(Py.newInteger(0));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(1);
               var10000 = var4._gt(var1.getlocal(3));
               var4 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(1951);
            var4 = var1.getlocal(3);
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(1952);
         var3 = var1.getglobal("_ProxyFile").__getattr__("_read").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject close$187(PyFrame var1, ThreadState var2) {
      var1.setline(1957);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_file")).__nonzero__()) {
         var1.setline(1958);
         var1.getlocal(0).__delattr__("_file");
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _lock_file$188(PyFrame var1, ThreadState var2) {
      var1.setline(1962);
      PyString.fromInterned("Lock file f using lockf and dot locking.");
      var1.setline(1963);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(2, var3);
      var3 = null;

      try {
         var1.setline(1965);
         PyObject var10000;
         PyObject var4;
         PyException var9;
         if (var1.getglobal("fcntl").__nonzero__()) {
            try {
               var1.setline(1967);
               var1.getglobal("fcntl").__getattr__("lockf").__call__(var2, var1.getlocal(0), var1.getglobal("fcntl").__getattr__("LOCK_EX")._or(var1.getglobal("fcntl").__getattr__("LOCK_NB")));
            } catch (Throwable var7) {
               var9 = Py.setException(var7, var1);
               if (var9.match(var1.getglobal("IOError"))) {
                  var4 = var9.value;
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1969);
                  var4 = var1.getlocal(3).__getattr__("errno");
                  var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("errno").__getattr__("EAGAIN"), var1.getglobal("errno").__getattr__("EACCES"), var1.getglobal("errno").__getattr__("EROFS")}));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1970);
                     throw Py.makeException(var1.getglobal("ExternalClashError").__call__(var2, PyString.fromInterned("lockf: lock unavailable: %s")._mod(var1.getlocal(0).__getattr__("name"))));
                  }

                  var1.setline(1973);
                  throw Py.makeException();
               }

               throw var9;
            }
         }

         var1.setline(1974);
         if (var1.getlocal(1).__nonzero__()) {
            try {
               var1.setline(1976);
               var3 = var1.getglobal("_create_temporary").__call__(var2, var1.getlocal(0).__getattr__("name")._add(PyString.fromInterned(".lock")));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(1977);
               var1.getlocal(4).__getattr__("close").__call__(var2);
            } catch (Throwable var5) {
               var9 = Py.setException(var5, var1);
               if (var9.match(var1.getglobal("IOError"))) {
                  var4 = var9.value;
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1979);
                  var4 = var1.getlocal(3).__getattr__("errno");
                  var10000 = var4._in(new PyTuple(new PyObject[]{var1.getglobal("errno").__getattr__("EACCES"), var1.getglobal("errno").__getattr__("EROFS")}));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1980);
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setline(1982);
                  throw Py.makeException();
               }

               throw var9;
            }

            try {
               var1.setline(1984);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("link")).__nonzero__()) {
                  var1.setline(1985);
                  var1.getglobal("os").__getattr__("link").__call__(var2, var1.getlocal(4).__getattr__("name"), var1.getlocal(0).__getattr__("name")._add(PyString.fromInterned(".lock")));
                  var1.setline(1986);
                  var3 = var1.getglobal("True");
                  var1.setlocal(2, var3);
                  var3 = null;
                  var1.setline(1987);
                  var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(4).__getattr__("name"));
               } else {
                  var1.setline(1989);
                  var1.getglobal("os").__getattr__("rename").__call__(var2, var1.getlocal(4).__getattr__("name"), var1.getlocal(0).__getattr__("name")._add(PyString.fromInterned(".lock")));
                  var1.setline(1990);
                  var3 = var1.getglobal("True");
                  var1.setlocal(2, var3);
                  var3 = null;
               }
            } catch (Throwable var6) {
               var9 = Py.setException(var6, var1);
               if (var9.match(var1.getglobal("OSError"))) {
                  var4 = var9.value;
                  var1.setlocal(3, var4);
                  var4 = null;
                  var1.setline(1992);
                  var4 = var1.getlocal(3).__getattr__("errno");
                  var10000 = var4._eq(var1.getglobal("errno").__getattr__("EEXIST"));
                  var4 = null;
                  if (!var10000.__nonzero__()) {
                     var4 = var1.getglobal("os").__getattr__("name");
                     var10000 = var4._eq(PyString.fromInterned("os2"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(3).__getattr__("errno");
                        var10000 = var4._eq(var1.getglobal("errno").__getattr__("EACCES"));
                        var4 = null;
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1994);
                     var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(4).__getattr__("name"));
                     var1.setline(1995);
                     throw Py.makeException(var1.getglobal("ExternalClashError").__call__(var2, PyString.fromInterned("dot lock unavailable: %s")._mod(var1.getlocal(0).__getattr__("name"))));
                  }

                  var1.setline(1998);
                  throw Py.makeException();
               }

               throw var9;
            }
         }
      } catch (Throwable var8) {
         Py.setException(var8, var1);
         var1.setline(2000);
         if (var1.getglobal("fcntl").__nonzero__()) {
            var1.setline(2001);
            var1.getglobal("fcntl").__getattr__("lockf").__call__(var2, var1.getlocal(0), var1.getglobal("fcntl").__getattr__("LOCK_UN"));
         }

         var1.setline(2002);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(2003);
            var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(0).__getattr__("name")._add(PyString.fromInterned(".lock")));
         }

         var1.setline(2004);
         throw Py.makeException();
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unlock_file$189(PyFrame var1, ThreadState var2) {
      var1.setline(2007);
      PyString.fromInterned("Unlock file f using lockf and dot locking.");
      var1.setline(2008);
      if (var1.getglobal("fcntl").__nonzero__()) {
         var1.setline(2009);
         var1.getglobal("fcntl").__getattr__("lockf").__call__(var2, var1.getlocal(0), var1.getglobal("fcntl").__getattr__("LOCK_UN"));
      }

      var1.setline(2010);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0).__getattr__("name")._add(PyString.fromInterned(".lock"))).__nonzero__()) {
         var1.setline(2011);
         var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(0).__getattr__("name")._add(PyString.fromInterned(".lock")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _create_carefully$190(PyFrame var1, ThreadState var2) {
      var1.setline(2014);
      PyString.fromInterned("Create a file if it doesn't exist and open for reading and writing.");
      var1.setline(2015);
      PyObject var3 = var1.getglobal("os").__getattr__("open").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getglobal("os").__getattr__("O_CREAT")._or(var1.getglobal("os").__getattr__("O_EXCL"))._or(var1.getglobal("os").__getattr__("O_RDWR")), (PyObject)Py.newInteger(438));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      Throwable var10000;
      label25: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(2017);
            var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rb+"));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label25;
         }

         var1.setline(2019);
         var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(1));

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      Throwable var7 = var10000;
      Py.addTraceback(var7, var1);
      var1.setline(2019);
      var1.getglobal("os").__getattr__("close").__call__(var2, var1.getlocal(1));
      throw (Throwable)var7;
   }

   public PyObject _create_temporary$191(PyFrame var1, ThreadState var2) {
      var1.setline(2022);
      PyString.fromInterned("Create a temp file based on path and open for reading and writing.");
      var1.setline(2023);
      PyObject var3 = var1.getglobal("_create_carefully").__call__(var2, PyString.fromInterned("%s.%s.%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2)), var1.getglobal("socket").__getattr__("gethostname").__call__(var2), var1.getglobal("os").__getattr__("getpid").__call__(var2)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _sync_flush$192(PyFrame var1, ThreadState var2) {
      var1.setline(2028);
      PyString.fromInterned("Ensure changes to file f are physically on disk.");
      var1.setline(2029);
      var1.getlocal(0).__getattr__("flush").__call__(var2);
      var1.setline(2030);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("fsync")).__nonzero__()) {
         var1.setline(2031);
         var1.getglobal("os").__getattr__("fsync").__call__(var2, var1.getlocal(0).__getattr__("fileno").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _sync_close$193(PyFrame var1, ThreadState var2) {
      var1.setline(2034);
      PyString.fromInterned("Close file f, ensuring all changes are physically on disk.");
      var1.setline(2035);
      var1.getglobal("_sync_flush").__call__(var2, var1.getlocal(0));
      var1.setline(2036);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Mailbox$194(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2045);
      PyObject[] var3 = new PyObject[]{var1.getname("rfc822").__getattr__("Message")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$195, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2050);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$196, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(2053);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$197, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$195(PyFrame var1, ThreadState var2) {
      var1.setline(2046);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("fp", var3);
      var3 = null;
      var1.setline(2047);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"seekp", var4);
      var3 = null;
      var1.setline(2048);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("factory", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$196(PyFrame var1, ThreadState var2) {
      var1.setline(2051);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0).__getattr__("next"), var1.getglobal("None"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$197(PyFrame var1, ThreadState var2) {
      while(true) {
         var1.setline(2054);
         PyObject var4;
         if (Py.newInteger(1).__nonzero__()) {
            var1.setline(2055);
            var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(0).__getattr__("seekp"));

            PyException var3;
            try {
               var1.setline(2057);
               var1.getlocal(0).__getattr__("_search_start").__call__(var2);
            } catch (Throwable var5) {
               var3 = Py.setException(var5, var1);
               if (var3.match(var1.getglobal("EOFError"))) {
                  var1.setline(2059);
                  var4 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
                  var1.getlocal(0).__setattr__("seekp", var4);
                  var4 = null;
                  var1.setline(2060);
                  var4 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var4;
               }

               throw var3;
            }

            var1.setline(2061);
            PyObject var6 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
            var1.setlocal(1, var6);
            var3 = null;
            var1.setline(2062);
            var1.getlocal(0).__getattr__("_search_end").__call__(var2);
            var1.setline(2063);
            var6 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
            var1.getlocal(0).__setattr__("seekp", var6);
            var1.setlocal(2, var6);
            var1.setline(2064);
            var6 = var1.getlocal(1);
            PyObject var10000 = var6._ne(var1.getlocal(2));
            var3 = null;
            if (!var10000.__nonzero__()) {
               continue;
            }
         }

         var1.setline(2066);
         var4 = var1.getlocal(0).__getattr__("factory").__call__(var2, var1.getglobal("_PartialFile").__call__(var2, var1.getlocal(0).__getattr__("fp"), var1.getlocal(1), var1.getlocal(2)));
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject UnixMailbox$198(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2071);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _search_start$199, (PyObject)null);
      var1.setlocal("_search_start", var4);
      var3 = null;
      var1.setline(2081);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _search_end$200, (PyObject)null);
      var1.setlocal("_search_end", var4);
      var3 = null;
      var1.setline(2117);
      PyString var5 = PyString.fromInterned("From \\s*[^\\s]+\\s+\\w\\w\\w\\s+\\w\\w\\w\\s+\\d?\\d\\s+\\d?\\d:\\d\\d(:\\d\\d)?(\\s+[^\\s]+)?\\s+\\d\\d\\d\\d\\s*[^\\s]*\\s*$");
      var1.setlocal("_fromlinepattern", var5);
      var3 = null;
      var1.setline(2121);
      PyObject var6 = var1.getname("None");
      var1.setlocal("_regexp", var6);
      var3 = null;
      var1.setline(2123);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _strict_isrealfromline$201, (PyObject)null);
      var1.setlocal("_strict_isrealfromline", var4);
      var3 = null;
      var1.setline(2129);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _portable_isrealfromline$202, (PyObject)null);
      var1.setlocal("_portable_isrealfromline", var4);
      var3 = null;
      var1.setline(2132);
      var6 = var1.getname("_strict_isrealfromline");
      var1.setlocal("_isrealfromline", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _search_start$199(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      do {
         var1.setline(2072);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2073);
         PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2074);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2075);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(2076);
            throw Py.makeException(var1.getglobal("EOFError"));
         }

         var1.setline(2077);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("From "));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_isrealfromline").__call__(var2, var1.getlocal(2));
         }
      } while(!var10000.__nonzero__());

      var1.setline(2078);
      var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(1));
      var1.setline(2079);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _search_end$200(PyFrame var1, ThreadState var2) {
      var1.setline(2082);
      var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2);

      PyObject var10000;
      do {
         var1.setline(2083);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2084);
         PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2085);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2086);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(2087);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2088);
         var3 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("From "));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_isrealfromline").__call__(var2, var1.getlocal(2));
         }
      } while(!var10000.__nonzero__());

      var1.setline(2089);
      var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(1));
      var1.setline(2090);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _strict_isrealfromline$201(PyFrame var1, ThreadState var2) {
      var1.setline(2124);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_regexp").__not__().__nonzero__()) {
         var1.setline(2125);
         var3 = imp.importOne("re", var1, -1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2126);
         var3 = var1.getlocal(2).__getattr__("compile").__call__(var2, var1.getlocal(0).__getattr__("_fromlinepattern"));
         var1.getlocal(0).__setattr__("_regexp", var3);
         var3 = null;
      }

      var1.setline(2127);
      var3 = var1.getlocal(0).__getattr__("_regexp").__getattr__("match").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _portable_isrealfromline$202(PyFrame var1, ThreadState var2) {
      var1.setline(2130);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject PortableUnixMailbox$203(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2136);
      PyObject var3 = var1.getname("UnixMailbox").__getattr__("_portable_isrealfromline");
      var1.setlocal("_isrealfromline", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject MmdfMailbox$204(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2141);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _search_start$205, (PyObject)null);
      var1.setlocal("_search_start", var4);
      var3 = null;
      var1.setline(2149);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _search_end$206, (PyObject)null);
      var1.setlocal("_search_end", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _search_start$205(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      do {
         var1.setline(2142);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2143);
         PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2144);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(2145);
            throw Py.makeException(var1.getglobal("EOFError"));
         }

         var1.setline(2146);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("\u0001\u0001\u0001\u0001\n"));
         var3 = null;
      } while(!var10000.__nonzero__());

      var1.setline(2147);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _search_end$206(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      do {
         var1.setline(2150);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2151);
         PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2152);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2153);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(2154);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2155);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("\u0001\u0001\u0001\u0001\n"));
         var3 = null;
      } while(!var10000.__nonzero__());

      var1.setline(2156);
      var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(1));
      var1.setline(2157);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MHMailbox$207(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2162);
      PyObject[] var3 = new PyObject[]{var1.getname("rfc822").__getattr__("Message")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$208, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(2178);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$209, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(2181);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, next$210, (PyObject)null);
      var1.setlocal("next", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$208(PyFrame var1, ThreadState var2) {
      var1.setline(2163);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2164);
      var3 = var1.getlocal(3).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[1-9][0-9]*$"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2165);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("dirname", var3);
      var3 = null;
      var1.setline(2168);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0).__getattr__("dirname"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2169);
      var3 = var1.getglobal("filter").__call__(var2, var1.getlocal(4).__getattr__("match"), var1.getlocal(5));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2170);
      var3 = var1.getglobal("map").__call__(var2, var1.getglobal("long"), var1.getlocal(5));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(2171);
      var1.getlocal(5).__getattr__("sort").__call__(var2);
      var1.setline(2174);
      var3 = var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(5));
      var1.getlocal(0).__setattr__("boxes", var3);
      var3 = null;
      var1.setline(2175);
      var1.getlocal(0).__getattr__("boxes").__getattr__("reverse").__call__(var2);
      var1.setline(2176);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("factory", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$209(PyFrame var1, ThreadState var2) {
      var1.setline(2179);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0).__getattr__("next"), var1.getglobal("None"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject next$210(PyFrame var1, ThreadState var2) {
      var1.setline(2182);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("boxes").__not__().__nonzero__()) {
         var1.setline(2183);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2184);
         PyObject var4 = var1.getlocal(0).__getattr__("boxes").__getattr__("pop").__call__(var2);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(2185);
         var4 = var1.getglobal("open").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dirname"), var1.getlocal(1)));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(2186);
         var4 = var1.getlocal(0).__getattr__("factory").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;

         try {
            var1.setline(2188);
            var4 = var1.getlocal(1);
            var1.getlocal(3).__setattr__("_mh_msgno", var4);
            var4 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(new PyTuple(new PyObject[]{var1.getglobal("AttributeError"), var1.getglobal("TypeError")}))) {
               throw var6;
            }

            var1.setline(2190);
         }

         var1.setline(2191);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject BabylMailbox$211(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(2196);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _search_start$212, (PyObject)null);
      var1.setlocal("_search_start", var4);
      var3 = null;
      var1.setline(2204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _search_end$213, (PyObject)null);
      var1.setlocal("_search_end", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _search_start$212(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      do {
         var1.setline(2197);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2198);
         PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2199);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(2200);
            throw Py.makeException(var1.getglobal("EOFError"));
         }

         var1.setline(2201);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("*** EOOH ***\n"));
         var3 = null;
      } while(!var10000.__nonzero__());

      var1.setline(2202);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _search_end$213(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      do {
         var1.setline(2205);
         if (!Py.newInteger(1).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2206);
         PyObject var3 = var1.getlocal(0).__getattr__("fp").__getattr__("tell").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(2207);
         var3 = var1.getlocal(0).__getattr__("fp").__getattr__("readline").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(2208);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(2209);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(2210);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("\u001f\f\n"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(2);
            var10000 = var3._eq(PyString.fromInterned("\u001f"));
            var3 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(2211);
      var1.getlocal(0).__getattr__("fp").__getattr__("seek").__call__(var2, var1.getlocal(1));
      var1.setline(2212);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$214(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised for module-specific errors."));
      var1.setline(2218);
      PyString.fromInterned("Raised for module-specific errors.");
      return var1.getf_locals();
   }

   public PyObject NoSuchMailboxError$215(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The specified mailbox does not exist and won't be created."));
      var1.setline(2221);
      PyString.fromInterned("The specified mailbox does not exist and won't be created.");
      return var1.getf_locals();
   }

   public PyObject NotEmptyError$216(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The specified mailbox is not empty and deletion was requested."));
      var1.setline(2224);
      PyString.fromInterned("The specified mailbox is not empty and deletion was requested.");
      return var1.getf_locals();
   }

   public PyObject ExternalClashError$217(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Another process caused an action to fail."));
      var1.setline(2227);
      PyString.fromInterned("Another process caused an action to fail.");
      return var1.getf_locals();
   }

   public PyObject FormatError$218(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A file appears to have an invalid format."));
      var1.setline(2230);
      PyString.fromInterned("A file appears to have an invalid format.");
      return var1.getf_locals();
   }

   public mailbox$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Mailbox$1 = Py.newCode(0, var2, var1, "Mailbox", 42, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path", "factory", "create"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 45, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      add$3 = Py.newCode(2, var2, var1, "add", 50, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      remove$4 = Py.newCode(2, var2, var1, "remove", 54, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __delitem__$5 = Py.newCode(2, var2, var1, "__delitem__", 58, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      discard$6 = Py.newCode(2, var2, var1, "discard", 61, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "message"};
      __setitem__$7 = Py.newCode(3, var2, var1, "__setitem__", 68, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default"};
      get$8 = Py.newCode(3, var2, var1, "get", 72, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __getitem__$9 = Py.newCode(2, var2, var1, "__getitem__", 79, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      get_message$10 = Py.newCode(2, var2, var1, "get_message", 86, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      get_string$11 = Py.newCode(2, var2, var1, "get_string", 90, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      get_file$12 = Py.newCode(2, var2, var1, "get_file", 94, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      iterkeys$13 = Py.newCode(1, var2, var1, "iterkeys", 98, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      keys$14 = Py.newCode(1, var2, var1, "keys", 102, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value"};
      itervalues$15 = Py.newCode(1, var2, var1, "itervalues", 106, false, false, self, 15, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      __iter__$16 = Py.newCode(1, var2, var1, "__iter__", 115, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      values$17 = Py.newCode(1, var2, var1, "values", 118, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value"};
      iteritems$18 = Py.newCode(1, var2, var1, "iteritems", 122, false, false, self, 18, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self"};
      items$19 = Py.newCode(1, var2, var1, "items", 131, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      has_key$20 = Py.newCode(2, var2, var1, "has_key", 135, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$21 = Py.newCode(2, var2, var1, "__contains__", 139, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$22 = Py.newCode(1, var2, var1, "__len__", 142, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      clear$23 = Py.newCode(1, var2, var1, "clear", 146, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "default", "result"};
      pop$24 = Py.newCode(3, var2, var1, "pop", 151, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      popitem$25 = Py.newCode(1, var2, var1, "popitem", 160, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "source", "bad_key", "key", "message"};
      update$26 = Py.newCode(2, var2, var1, "update", 167, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$27 = Py.newCode(1, var2, var1, "flush", 184, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      lock$28 = Py.newCode(1, var2, var1, "lock", 188, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      unlock$29 = Py.newCode(1, var2, var1, "unlock", 192, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$30 = Py.newCode(1, var2, var1, "close", 196, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "target", "mangle_from_", "buffer", "gen", "data", "lastline", "line"};
      _dump_message$31 = Py.newCode(4, var2, var1, "_dump_message", 203, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Maildir$32 = Py.newCode(0, var2, var1, "Maildir", 244, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dirname", "factory", "create", "path"};
      __init__$33 = Py.newCode(4, var2, var1, "__init__", 249, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "tmp_file", "subdir", "suffix", "uniq", "dest", "e"};
      add$34 = Py.newCode(2, var2, var1, "add", 269, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      remove$35 = Py.newCode(2, var2, var1, "remove", 306, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "e"};
      discard$36 = Py.newCode(2, var2, var1, "discard", 310, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "message", "old_subpath", "temp_key", "temp_subpath", "dominant_subpath", "subdir", "suffix", "new_path"};
      __setitem__$37 = Py.newCode(3, var2, var1, "__setitem__", 321, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "subpath", "f", "msg", "subdir", "name"};
      get_message$38 = Py.newCode(2, var2, var1, "get_message", 344, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "f"};
      get_string$39 = Py.newCode(2, var2, var1, "get_string", 362, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "f"};
      get_file$40 = Py.newCode(2, var2, var1, "get_file", 370, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      iterkeys$41 = Py.newCode(1, var2, var1, "iterkeys", 375, false, false, self, 41, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "key"};
      has_key$42 = Py.newCode(2, var2, var1, "has_key", 385, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$43 = Py.newCode(1, var2, var1, "__len__", 390, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$44 = Py.newCode(1, var2, var1, "flush", 395, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      lock$45 = Py.newCode(1, var2, var1, "lock", 401, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      unlock$46 = Py.newCode(1, var2, var1, "unlock", 405, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$47 = Py.newCode(1, var2, var1, "close", 409, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "entry"};
      list_folders$48 = Py.newCode(1, var2, var1, "list_folders", 413, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "folder"};
      get_folder$49 = Py.newCode(2, var2, var1, "get_folder", 422, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "folder", "path", "result", "maildirfolder_path"};
      add_folder$50 = Py.newCode(2, var2, var1, "add_folder", 428, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "folder", "path", "entry", "root", "dirs", "files"};
      remove_folder$51 = Py.newCode(2, var2, var1, "remove_folder", 438, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now", "entry", "path"};
      clean$52 = Py.newCode(1, var2, var1, "clean", 457, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now", "hostname", "uniq", "path", "e"};
      _create_tmp$53 = Py.newCode(1, var2, var1, "_create_tmp", 467, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "refresh", "subdir", "mtime", "path", "entry", "p", "uniq"};
      _refresh$54 = Py.newCode(1, var2, var1, "_refresh", 495, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      _lookup$55 = Py.newCode(2, var2, var1, "_lookup", 531, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      next$56 = Py.newCode(1, var2, var1, "next", 545, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _singlefileMailbox$57 = Py.newCode(0, var2, var1, "_singlefileMailbox", 558, false, false, self, 57, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path", "factory", "create", "f", "e"};
      __init__$58 = Py.newCode(4, var2, var1, "__init__", 561, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      add$59 = Py.newCode(2, var2, var1, "add", 584, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      remove$60 = Py.newCode(2, var2, var1, "remove", 594, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "message"};
      __setitem__$61 = Py.newCode(3, var2, var1, "__setitem__", 600, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      iterkeys$62 = Py.newCode(1, var2, var1, "iterkeys", 606, false, false, self, 62, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "key"};
      has_key$63 = Py.newCode(2, var2, var1, "has_key", 612, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$64 = Py.newCode(1, var2, var1, "__len__", 617, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      lock$65 = Py.newCode(1, var2, var1, "lock", 622, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      unlock$66 = Py.newCode(1, var2, var1, "unlock", 628, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cur_len", "new_file", "new_toc", "key", "start", "stop", "new_start", "buffer", "mode", "e"};
      flush$67 = Py.newCode(1, var2, var1, "flush", 634, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      _pre_mailbox_hook$68 = Py.newCode(2, var2, var1, "_pre_mailbox_hook", 702, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      _pre_message_hook$69 = Py.newCode(2, var2, var1, "_pre_message_hook", 706, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      _post_message_hook$70 = Py.newCode(2, var2, var1, "_post_message_hook", 710, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$71 = Py.newCode(1, var2, var1, "close", 714, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      _lookup$72 = Py.newCode(2, var2, var1, "_lookup", 721, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "before", "offsets"};
      _append_message$73 = Py.newCode(2, var2, var1, "_append_message", 731, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _mboxMMDF$74 = Py.newCode(0, var2, var1, "_mboxMMDF", 754, false, false, self, 74, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "key", "start", "stop", "from_line", "string", "msg"};
      get_message$75 = Py.newCode(2, var2, var1, "get_message", 759, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "from_", "start", "stop", "string"};
      get_string$76 = Py.newCode(3, var2, var1, "get_string", 769, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "from_", "start", "stop"};
      get_file$77 = Py.newCode(3, var2, var1, "get_file", 778, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "from_line", "newline", "start", "stop"};
      _install_message$78 = Py.newCode(2, var2, var1, "_install_message", 786, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      mbox$79 = Py.newCode(0, var2, var1, "mbox", 810, false, false, self, 79, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path", "factory", "create"};
      __init__$80 = Py.newCode(4, var2, var1, "__init__", 819, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      _post_message_hook$81 = Py.newCode(2, var2, var1, "_post_message_hook", 824, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "starts", "stops", "last_was_empty", "line_pos", "line"};
      _generate_toc$82 = Py.newCode(1, var2, var1, "_generate_toc", 828, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MMDF$83 = Py.newCode(0, var2, var1, "MMDF", 862, false, false, self, 83, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path", "factory", "create"};
      __init__$84 = Py.newCode(4, var2, var1, "__init__", 865, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      _pre_message_hook$85 = Py.newCode(2, var2, var1, "_pre_message_hook", 870, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      _post_message_hook$86 = Py.newCode(2, var2, var1, "_post_message_hook", 874, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "starts", "stops", "next_pos", "line_pos", "line"};
      _generate_toc$87 = Py.newCode(1, var2, var1, "_generate_toc", 878, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MH$88 = Py.newCode(0, var2, var1, "MH", 907, false, false, self, 88, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path", "factory", "create"};
      __init__$89 = Py.newCode(4, var2, var1, "__init__", 910, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "keys", "new_key", "new_path", "f", "closed"};
      add$90 = Py.newCode(2, var2, var1, "add", 922, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "path", "f", "e"};
      remove$91 = Py.newCode(2, var2, var1, "remove", 956, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "message", "path", "f", "e"};
      __setitem__$92 = Py.newCode(3, var2, var1, "__setitem__", 970, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "f", "e", "msg", "name", "key_list"};
      get_message$93 = Py.newCode(2, var2, var1, "get_message", 994, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "f", "e"};
      get_string$94 = Py.newCode(2, var2, var1, "get_string", 1021, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "f", "e"};
      get_file$95 = Py.newCode(2, var2, var1, "get_file", 1044, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_(1057_27)"};
      iterkeys$96 = Py.newCode(1, var2, var1, "iterkeys", 1055, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "entry"};
      f$97 = Py.newCode(1, var2, var1, "<genexpr>", 1057, false, false, self, 97, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "key"};
      has_key$98 = Py.newCode(2, var2, var1, "has_key", 1060, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$99 = Py.newCode(1, var2, var1, "__len__", 1064, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      lock$100 = Py.newCode(1, var2, var1, "lock", 1068, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      unlock$101 = Py.newCode(1, var2, var1, "unlock", 1075, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      flush$102 = Py.newCode(1, var2, var1, "flush", 1083, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$103 = Py.newCode(1, var2, var1, "close", 1087, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "entry"};
      list_folders$104 = Py.newCode(1, var2, var1, "list_folders", 1092, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "folder"};
      get_folder$105 = Py.newCode(2, var2, var1, "get_folder", 1100, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "folder"};
      add_folder$106 = Py.newCode(2, var2, var1, "add_folder", 1105, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "folder", "path", "entries"};
      remove_folder$107 = Py.newCode(2, var2, var1, "remove_folder", 1110, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "results", "f", "all_keys", "line", "name", "contents", "keys", "spec", "start", "stop", "_(1136_43)", "_[1138_37]", "key"};
      get_sequences$108 = Py.newCode(1, var2, var1, "get_sequences", 1122, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "x"};
      f$109 = Py.newCode(1, var2, var1, "<genexpr>", 1136, false, false, self, 109, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "sequences", "f", "name", "keys", "prev", "completing", "key"};
      set_sequences$110 = Py.newCode(2, var2, var1, "set_sequences", 1149, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sequences", "prev", "changes", "key", "name", "key_list", "old", "new"};
      pack$111 = Py.newCode(1, var2, var1, "pack", 1178, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "key", "pending_sequences", "all_sequences", "name", "key_list", "sequence"};
      _dump_sequences$112 = Py.newCode(3, var2, var1, "_dump_sequences", 1203, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Babyl$113 = Py.newCode(0, var2, var1, "Babyl", 1218, false, false, self, 113, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path", "factory", "create"};
      __init__$114 = Py.newCode(4, var2, var1, "__init__", 1224, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "key"};
      add$115 = Py.newCode(2, var2, var1, "add", 1229, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      remove$116 = Py.newCode(2, var2, var1, "remove", 1236, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "message"};
      __setitem__$117 = Py.newCode(3, var2, var1, "__setitem__", 1242, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "start", "stop", "original_headers", "line", "visible_headers", "body", "msg"};
      get_message$118 = Py.newCode(2, var2, var1, "get_message", 1248, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "start", "stop", "original_headers", "line"};
      get_string$119 = Py.newCode(2, var2, var1, "get_string", 1273, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      get_file$120 = Py.newCode(2, var2, var1, "get_file", 1292, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "labels", "label_list"};
      get_labels$121 = Py.newCode(1, var2, var1, "get_labels", 1297, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "starts", "stops", "next_pos", "label_lists", "line_pos", "line", "labels", "_[1320_26]", "label"};
      _generate_toc$122 = Py.newCode(1, var2, var1, "_generate_toc", 1306, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      _pre_mailbox_hook$123 = Py.newCode(2, var2, var1, "_pre_mailbox_hook", 1336, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      _pre_message_hook$124 = Py.newCode(2, var2, var1, "_pre_message_hook", 1342, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f"};
      _post_message_hook$125 = Py.newCode(2, var2, var1, "_post_message_hook", 1346, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "start", "special_labels", "labels", "label", "orig_buffer", "orig_generator", "line", "vis_buffer", "vis_generator", "buffer", "body_start", "original_pos", "first_pass", "stop"};
      _install_message$126 = Py.newCode(2, var2, var1, "_install_message", 1350, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Message$127 = Py.newCode(0, var2, var1, "Message", 1439, false, false, self, 127, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "message"};
      __init__$128 = Py.newCode(2, var2, var1, "__init__", 1442, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "name"};
      _become_message$129 = Py.newCode(2, var2, var1, "_become_message", 1457, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      _explain_to$130 = Py.newCode(2, var2, var1, "_explain_to", 1463, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MaildirMessage$131 = Py.newCode(0, var2, var1, "MaildirMessage", 1471, false, false, self, 131, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "message"};
      __init__$132 = Py.newCode(2, var2, var1, "__init__", 1474, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_subdir$133 = Py.newCode(1, var2, var1, "get_subdir", 1481, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "subdir"};
      set_subdir$134 = Py.newCode(2, var2, var1, "set_subdir", 1485, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_flags$135 = Py.newCode(1, var2, var1, "get_flags", 1492, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flags"};
      set_flags$136 = Py.newCode(2, var2, var1, "set_flags", 1499, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      add_flag$137 = Py.newCode(2, var2, var1, "add_flag", 1503, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      remove_flag$138 = Py.newCode(2, var2, var1, "remove_flag", 1507, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_date$139 = Py.newCode(1, var2, var1, "get_date", 1512, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "date"};
      set_date$140 = Py.newCode(2, var2, var1, "set_date", 1516, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_info$141 = Py.newCode(1, var2, var1, "get_info", 1523, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "info"};
      set_info$142 = Py.newCode(2, var2, var1, "set_info", 1527, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "flags"};
      _explain_to$143 = Py.newCode(2, var2, var1, "_explain_to", 1534, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _mboxMMDFMessage$144 = Py.newCode(0, var2, var1, "_mboxMMDFMessage", 1578, false, false, self, 144, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "message", "unixfrom"};
      __init__$145 = Py.newCode(2, var2, var1, "__init__", 1581, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_from$146 = Py.newCode(1, var2, var1, "get_from", 1590, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "from_", "time_"};
      set_from$147 = Py.newCode(3, var2, var1, "set_from", 1594, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_flags$148 = Py.newCode(1, var2, var1, "get_flags", 1602, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flags", "status_flags", "xstatus_flags", "flag"};
      set_flags$149 = Py.newCode(2, var2, var1, "set_flags", 1606, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      add_flag$150 = Py.newCode(2, var2, var1, "add_flag", 1628, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flag"};
      remove_flag$151 = Py.newCode(2, var2, var1, "remove_flag", 1632, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "flags", "maybe_date"};
      _explain_to$152 = Py.newCode(2, var2, var1, "_explain_to", 1637, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      mboxMessage$153 = Py.newCode(0, var2, var1, "mboxMessage", 1689, false, false, self, 153, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MHMessage$154 = Py.newCode(0, var2, var1, "MHMessage", 1693, false, false, self, 154, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "message"};
      __init__$155 = Py.newCode(2, var2, var1, "__init__", 1696, false, false, self, 155, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_sequences$156 = Py.newCode(1, var2, var1, "get_sequences", 1701, false, false, self, 156, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sequences"};
      set_sequences$157 = Py.newCode(2, var2, var1, "set_sequences", 1705, false, false, self, 157, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sequence"};
      add_sequence$158 = Py.newCode(2, var2, var1, "add_sequence", 1709, false, false, self, 158, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sequence"};
      remove_sequence$159 = Py.newCode(2, var2, var1, "remove_sequence", 1717, false, false, self, 159, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "sequences", "sequence"};
      _explain_to$160 = Py.newCode(2, var2, var1, "_explain_to", 1724, false, false, self, 160, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BabylMessage$161 = Py.newCode(0, var2, var1, "BabylMessage", 1763, false, false, self, 161, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "message"};
      __init__$162 = Py.newCode(2, var2, var1, "__init__", 1766, false, false, self, 162, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_labels$163 = Py.newCode(1, var2, var1, "get_labels", 1772, false, false, self, 163, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "labels"};
      set_labels$164 = Py.newCode(2, var2, var1, "set_labels", 1776, false, false, self, 164, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "label"};
      add_label$165 = Py.newCode(2, var2, var1, "add_label", 1780, false, false, self, 165, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "label"};
      remove_label$166 = Py.newCode(2, var2, var1, "remove_label", 1788, false, false, self, 166, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_visible$167 = Py.newCode(1, var2, var1, "get_visible", 1795, false, false, self, 167, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "visible"};
      set_visible$168 = Py.newCode(2, var2, var1, "set_visible", 1799, false, false, self, 168, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header"};
      update_visible$169 = Py.newCode(1, var2, var1, "update_visible", 1803, false, false, self, 169, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "labels", "label"};
      _explain_to$170 = Py.newCode(2, var2, var1, "_explain_to", 1814, false, false, self, 170, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MMDFMessage$171 = Py.newCode(0, var2, var1, "MMDFMessage", 1856, false, false, self, 171, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _ProxyFile$172 = Py.newCode(0, var2, var1, "_ProxyFile", 1860, false, false, self, 172, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "f", "pos"};
      __init__$173 = Py.newCode(3, var2, var1, "__init__", 1863, false, false, self, 173, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      read$174 = Py.newCode(2, var2, var1, "read", 1871, false, false, self, 174, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size"};
      readline$175 = Py.newCode(2, var2, var1, "readline", 1875, false, false, self, 175, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sizehint", "result", "line"};
      readlines$176 = Py.newCode(2, var2, var1, "readlines", 1879, false, false, self, 176, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$177 = Py.newCode(1, var2, var1, "__iter__", 1890, false, false, self, 177, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$178 = Py.newCode(1, var2, var1, "tell", 1894, false, false, self, 178, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "whence"};
      seek$179 = Py.newCode(3, var2, var1, "seek", 1898, false, false, self, 179, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$180 = Py.newCode(1, var2, var1, "close", 1905, false, false, self, 180, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "read_method", "result"};
      _read$181 = Py.newCode(3, var2, var1, "_read", 1912, false, false, self, 181, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _PartialFile$182 = Py.newCode(0, var2, var1, "_PartialFile", 1922, false, false, self, 182, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "f", "start", "stop"};
      __init__$183 = Py.newCode(4, var2, var1, "__init__", 1925, false, false, self, 183, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tell$184 = Py.newCode(1, var2, var1, "tell", 1931, false, false, self, 184, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "offset", "whence"};
      seek$185 = Py.newCode(3, var2, var1, "seek", 1935, false, false, self, 185, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "size", "read_method", "remaining"};
      _read$186 = Py.newCode(3, var2, var1, "_read", 1945, false, false, self, 186, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$187 = Py.newCode(1, var2, var1, "close", 1954, false, false, self, 187, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f", "dotlock", "dotlock_done", "e", "pre_lock"};
      _lock_file$188 = Py.newCode(2, var2, var1, "_lock_file", 1961, false, false, self, 188, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f"};
      _unlock_file$189 = Py.newCode(1, var2, var1, "_unlock_file", 2006, false, false, self, 189, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "fd"};
      _create_carefully$190 = Py.newCode(1, var2, var1, "_create_carefully", 2013, false, false, self, 190, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      _create_temporary$191 = Py.newCode(1, var2, var1, "_create_temporary", 2021, false, false, self, 191, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f"};
      _sync_flush$192 = Py.newCode(1, var2, var1, "_sync_flush", 2027, false, false, self, 192, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"f"};
      _sync_close$193 = Py.newCode(1, var2, var1, "_sync_close", 2033, false, false, self, 193, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Mailbox$194 = Py.newCode(0, var2, var1, "_Mailbox", 2043, false, false, self, 194, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fp", "factory"};
      __init__$195 = Py.newCode(3, var2, var1, "__init__", 2045, false, false, self, 195, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$196 = Py.newCode(1, var2, var1, "__iter__", 2050, false, false, self, 196, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start", "stop"};
      next$197 = Py.newCode(1, var2, var1, "next", 2053, false, false, self, 197, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      UnixMailbox$198 = Py.newCode(0, var2, var1, "UnixMailbox", 2069, false, false, self, 198, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "pos", "line"};
      _search_start$199 = Py.newCode(1, var2, var1, "_search_start", 2071, false, false, self, 199, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "line"};
      _search_end$200 = Py.newCode(1, var2, var1, "_search_end", 2081, false, false, self, 200, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "re"};
      _strict_isrealfromline$201 = Py.newCode(2, var2, var1, "_strict_isrealfromline", 2123, false, false, self, 201, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      _portable_isrealfromline$202 = Py.newCode(2, var2, var1, "_portable_isrealfromline", 2129, false, false, self, 202, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      PortableUnixMailbox$203 = Py.newCode(0, var2, var1, "PortableUnixMailbox", 2135, false, false, self, 203, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      MmdfMailbox$204 = Py.newCode(0, var2, var1, "MmdfMailbox", 2139, false, false, self, 204, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "line"};
      _search_start$205 = Py.newCode(1, var2, var1, "_search_start", 2141, false, false, self, 205, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "line"};
      _search_end$206 = Py.newCode(1, var2, var1, "_search_end", 2149, false, false, self, 206, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MHMailbox$207 = Py.newCode(0, var2, var1, "MHMailbox", 2160, false, false, self, 207, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dirname", "factory", "re", "pat", "list"};
      __init__$208 = Py.newCode(3, var2, var1, "__init__", 2162, false, false, self, 208, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$209 = Py.newCode(1, var2, var1, "__iter__", 2178, false, false, self, 209, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fn", "fp", "msg"};
      next$210 = Py.newCode(1, var2, var1, "next", 2181, false, false, self, 210, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BabylMailbox$211 = Py.newCode(0, var2, var1, "BabylMailbox", 2194, false, false, self, 211, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "line"};
      _search_start$212 = Py.newCode(1, var2, var1, "_search_start", 2196, false, false, self, 212, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pos", "line"};
      _search_end$213 = Py.newCode(1, var2, var1, "_search_end", 2204, false, false, self, 213, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Error$214 = Py.newCode(0, var2, var1, "Error", 2217, false, false, self, 214, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NoSuchMailboxError$215 = Py.newCode(0, var2, var1, "NoSuchMailboxError", 2220, false, false, self, 215, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      NotEmptyError$216 = Py.newCode(0, var2, var1, "NotEmptyError", 2223, false, false, self, 216, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ExternalClashError$217 = Py.newCode(0, var2, var1, "ExternalClashError", 2226, false, false, self, 217, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FormatError$218 = Py.newCode(0, var2, var1, "FormatError", 2229, false, false, self, 218, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new mailbox$py("mailbox$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(mailbox$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Mailbox$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.add$3(var2, var3);
         case 4:
            return this.remove$4(var2, var3);
         case 5:
            return this.__delitem__$5(var2, var3);
         case 6:
            return this.discard$6(var2, var3);
         case 7:
            return this.__setitem__$7(var2, var3);
         case 8:
            return this.get$8(var2, var3);
         case 9:
            return this.__getitem__$9(var2, var3);
         case 10:
            return this.get_message$10(var2, var3);
         case 11:
            return this.get_string$11(var2, var3);
         case 12:
            return this.get_file$12(var2, var3);
         case 13:
            return this.iterkeys$13(var2, var3);
         case 14:
            return this.keys$14(var2, var3);
         case 15:
            return this.itervalues$15(var2, var3);
         case 16:
            return this.__iter__$16(var2, var3);
         case 17:
            return this.values$17(var2, var3);
         case 18:
            return this.iteritems$18(var2, var3);
         case 19:
            return this.items$19(var2, var3);
         case 20:
            return this.has_key$20(var2, var3);
         case 21:
            return this.__contains__$21(var2, var3);
         case 22:
            return this.__len__$22(var2, var3);
         case 23:
            return this.clear$23(var2, var3);
         case 24:
            return this.pop$24(var2, var3);
         case 25:
            return this.popitem$25(var2, var3);
         case 26:
            return this.update$26(var2, var3);
         case 27:
            return this.flush$27(var2, var3);
         case 28:
            return this.lock$28(var2, var3);
         case 29:
            return this.unlock$29(var2, var3);
         case 30:
            return this.close$30(var2, var3);
         case 31:
            return this._dump_message$31(var2, var3);
         case 32:
            return this.Maildir$32(var2, var3);
         case 33:
            return this.__init__$33(var2, var3);
         case 34:
            return this.add$34(var2, var3);
         case 35:
            return this.remove$35(var2, var3);
         case 36:
            return this.discard$36(var2, var3);
         case 37:
            return this.__setitem__$37(var2, var3);
         case 38:
            return this.get_message$38(var2, var3);
         case 39:
            return this.get_string$39(var2, var3);
         case 40:
            return this.get_file$40(var2, var3);
         case 41:
            return this.iterkeys$41(var2, var3);
         case 42:
            return this.has_key$42(var2, var3);
         case 43:
            return this.__len__$43(var2, var3);
         case 44:
            return this.flush$44(var2, var3);
         case 45:
            return this.lock$45(var2, var3);
         case 46:
            return this.unlock$46(var2, var3);
         case 47:
            return this.close$47(var2, var3);
         case 48:
            return this.list_folders$48(var2, var3);
         case 49:
            return this.get_folder$49(var2, var3);
         case 50:
            return this.add_folder$50(var2, var3);
         case 51:
            return this.remove_folder$51(var2, var3);
         case 52:
            return this.clean$52(var2, var3);
         case 53:
            return this._create_tmp$53(var2, var3);
         case 54:
            return this._refresh$54(var2, var3);
         case 55:
            return this._lookup$55(var2, var3);
         case 56:
            return this.next$56(var2, var3);
         case 57:
            return this._singlefileMailbox$57(var2, var3);
         case 58:
            return this.__init__$58(var2, var3);
         case 59:
            return this.add$59(var2, var3);
         case 60:
            return this.remove$60(var2, var3);
         case 61:
            return this.__setitem__$61(var2, var3);
         case 62:
            return this.iterkeys$62(var2, var3);
         case 63:
            return this.has_key$63(var2, var3);
         case 64:
            return this.__len__$64(var2, var3);
         case 65:
            return this.lock$65(var2, var3);
         case 66:
            return this.unlock$66(var2, var3);
         case 67:
            return this.flush$67(var2, var3);
         case 68:
            return this._pre_mailbox_hook$68(var2, var3);
         case 69:
            return this._pre_message_hook$69(var2, var3);
         case 70:
            return this._post_message_hook$70(var2, var3);
         case 71:
            return this.close$71(var2, var3);
         case 72:
            return this._lookup$72(var2, var3);
         case 73:
            return this._append_message$73(var2, var3);
         case 74:
            return this._mboxMMDF$74(var2, var3);
         case 75:
            return this.get_message$75(var2, var3);
         case 76:
            return this.get_string$76(var2, var3);
         case 77:
            return this.get_file$77(var2, var3);
         case 78:
            return this._install_message$78(var2, var3);
         case 79:
            return this.mbox$79(var2, var3);
         case 80:
            return this.__init__$80(var2, var3);
         case 81:
            return this._post_message_hook$81(var2, var3);
         case 82:
            return this._generate_toc$82(var2, var3);
         case 83:
            return this.MMDF$83(var2, var3);
         case 84:
            return this.__init__$84(var2, var3);
         case 85:
            return this._pre_message_hook$85(var2, var3);
         case 86:
            return this._post_message_hook$86(var2, var3);
         case 87:
            return this._generate_toc$87(var2, var3);
         case 88:
            return this.MH$88(var2, var3);
         case 89:
            return this.__init__$89(var2, var3);
         case 90:
            return this.add$90(var2, var3);
         case 91:
            return this.remove$91(var2, var3);
         case 92:
            return this.__setitem__$92(var2, var3);
         case 93:
            return this.get_message$93(var2, var3);
         case 94:
            return this.get_string$94(var2, var3);
         case 95:
            return this.get_file$95(var2, var3);
         case 96:
            return this.iterkeys$96(var2, var3);
         case 97:
            return this.f$97(var2, var3);
         case 98:
            return this.has_key$98(var2, var3);
         case 99:
            return this.__len__$99(var2, var3);
         case 100:
            return this.lock$100(var2, var3);
         case 101:
            return this.unlock$101(var2, var3);
         case 102:
            return this.flush$102(var2, var3);
         case 103:
            return this.close$103(var2, var3);
         case 104:
            return this.list_folders$104(var2, var3);
         case 105:
            return this.get_folder$105(var2, var3);
         case 106:
            return this.add_folder$106(var2, var3);
         case 107:
            return this.remove_folder$107(var2, var3);
         case 108:
            return this.get_sequences$108(var2, var3);
         case 109:
            return this.f$109(var2, var3);
         case 110:
            return this.set_sequences$110(var2, var3);
         case 111:
            return this.pack$111(var2, var3);
         case 112:
            return this._dump_sequences$112(var2, var3);
         case 113:
            return this.Babyl$113(var2, var3);
         case 114:
            return this.__init__$114(var2, var3);
         case 115:
            return this.add$115(var2, var3);
         case 116:
            return this.remove$116(var2, var3);
         case 117:
            return this.__setitem__$117(var2, var3);
         case 118:
            return this.get_message$118(var2, var3);
         case 119:
            return this.get_string$119(var2, var3);
         case 120:
            return this.get_file$120(var2, var3);
         case 121:
            return this.get_labels$121(var2, var3);
         case 122:
            return this._generate_toc$122(var2, var3);
         case 123:
            return this._pre_mailbox_hook$123(var2, var3);
         case 124:
            return this._pre_message_hook$124(var2, var3);
         case 125:
            return this._post_message_hook$125(var2, var3);
         case 126:
            return this._install_message$126(var2, var3);
         case 127:
            return this.Message$127(var2, var3);
         case 128:
            return this.__init__$128(var2, var3);
         case 129:
            return this._become_message$129(var2, var3);
         case 130:
            return this._explain_to$130(var2, var3);
         case 131:
            return this.MaildirMessage$131(var2, var3);
         case 132:
            return this.__init__$132(var2, var3);
         case 133:
            return this.get_subdir$133(var2, var3);
         case 134:
            return this.set_subdir$134(var2, var3);
         case 135:
            return this.get_flags$135(var2, var3);
         case 136:
            return this.set_flags$136(var2, var3);
         case 137:
            return this.add_flag$137(var2, var3);
         case 138:
            return this.remove_flag$138(var2, var3);
         case 139:
            return this.get_date$139(var2, var3);
         case 140:
            return this.set_date$140(var2, var3);
         case 141:
            return this.get_info$141(var2, var3);
         case 142:
            return this.set_info$142(var2, var3);
         case 143:
            return this._explain_to$143(var2, var3);
         case 144:
            return this._mboxMMDFMessage$144(var2, var3);
         case 145:
            return this.__init__$145(var2, var3);
         case 146:
            return this.get_from$146(var2, var3);
         case 147:
            return this.set_from$147(var2, var3);
         case 148:
            return this.get_flags$148(var2, var3);
         case 149:
            return this.set_flags$149(var2, var3);
         case 150:
            return this.add_flag$150(var2, var3);
         case 151:
            return this.remove_flag$151(var2, var3);
         case 152:
            return this._explain_to$152(var2, var3);
         case 153:
            return this.mboxMessage$153(var2, var3);
         case 154:
            return this.MHMessage$154(var2, var3);
         case 155:
            return this.__init__$155(var2, var3);
         case 156:
            return this.get_sequences$156(var2, var3);
         case 157:
            return this.set_sequences$157(var2, var3);
         case 158:
            return this.add_sequence$158(var2, var3);
         case 159:
            return this.remove_sequence$159(var2, var3);
         case 160:
            return this._explain_to$160(var2, var3);
         case 161:
            return this.BabylMessage$161(var2, var3);
         case 162:
            return this.__init__$162(var2, var3);
         case 163:
            return this.get_labels$163(var2, var3);
         case 164:
            return this.set_labels$164(var2, var3);
         case 165:
            return this.add_label$165(var2, var3);
         case 166:
            return this.remove_label$166(var2, var3);
         case 167:
            return this.get_visible$167(var2, var3);
         case 168:
            return this.set_visible$168(var2, var3);
         case 169:
            return this.update_visible$169(var2, var3);
         case 170:
            return this._explain_to$170(var2, var3);
         case 171:
            return this.MMDFMessage$171(var2, var3);
         case 172:
            return this._ProxyFile$172(var2, var3);
         case 173:
            return this.__init__$173(var2, var3);
         case 174:
            return this.read$174(var2, var3);
         case 175:
            return this.readline$175(var2, var3);
         case 176:
            return this.readlines$176(var2, var3);
         case 177:
            return this.__iter__$177(var2, var3);
         case 178:
            return this.tell$178(var2, var3);
         case 179:
            return this.seek$179(var2, var3);
         case 180:
            return this.close$180(var2, var3);
         case 181:
            return this._read$181(var2, var3);
         case 182:
            return this._PartialFile$182(var2, var3);
         case 183:
            return this.__init__$183(var2, var3);
         case 184:
            return this.tell$184(var2, var3);
         case 185:
            return this.seek$185(var2, var3);
         case 186:
            return this._read$186(var2, var3);
         case 187:
            return this.close$187(var2, var3);
         case 188:
            return this._lock_file$188(var2, var3);
         case 189:
            return this._unlock_file$189(var2, var3);
         case 190:
            return this._create_carefully$190(var2, var3);
         case 191:
            return this._create_temporary$191(var2, var3);
         case 192:
            return this._sync_flush$192(var2, var3);
         case 193:
            return this._sync_close$193(var2, var3);
         case 194:
            return this._Mailbox$194(var2, var3);
         case 195:
            return this.__init__$195(var2, var3);
         case 196:
            return this.__iter__$196(var2, var3);
         case 197:
            return this.next$197(var2, var3);
         case 198:
            return this.UnixMailbox$198(var2, var3);
         case 199:
            return this._search_start$199(var2, var3);
         case 200:
            return this._search_end$200(var2, var3);
         case 201:
            return this._strict_isrealfromline$201(var2, var3);
         case 202:
            return this._portable_isrealfromline$202(var2, var3);
         case 203:
            return this.PortableUnixMailbox$203(var2, var3);
         case 204:
            return this.MmdfMailbox$204(var2, var3);
         case 205:
            return this._search_start$205(var2, var3);
         case 206:
            return this._search_end$206(var2, var3);
         case 207:
            return this.MHMailbox$207(var2, var3);
         case 208:
            return this.__init__$208(var2, var3);
         case 209:
            return this.__iter__$209(var2, var3);
         case 210:
            return this.next$210(var2, var3);
         case 211:
            return this.BabylMailbox$211(var2, var3);
         case 212:
            return this._search_start$212(var2, var3);
         case 213:
            return this._search_end$213(var2, var3);
         case 214:
            return this.Error$214(var2, var3);
         case 215:
            return this.NoSuchMailboxError$215(var2, var3);
         case 216:
            return this.NotEmptyError$216(var2, var3);
         case 217:
            return this.ExternalClashError$217(var2, var3);
         case 218:
            return this.FormatError$218(var2, var3);
         default:
            return null;
      }
   }
}
