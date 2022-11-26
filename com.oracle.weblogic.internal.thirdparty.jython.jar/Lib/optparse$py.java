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
@Filename("optparse.py")
public class optparse$py extends PyFunctionTable implements PyRunnable {
   static optparse$py self;
   static final PyCode f$0;
   static final PyCode _repr$1;
   static final PyCode gettext$2;
   static final PyCode OptParseError$3;
   static final PyCode __init__$4;
   static final PyCode __str__$5;
   static final PyCode OptionError$6;
   static final PyCode __init__$7;
   static final PyCode __str__$8;
   static final PyCode OptionConflictError$9;
   static final PyCode OptionValueError$10;
   static final PyCode BadOptionError$11;
   static final PyCode __init__$12;
   static final PyCode __str__$13;
   static final PyCode AmbiguousOptionError$14;
   static final PyCode __init__$15;
   static final PyCode __str__$16;
   static final PyCode HelpFormatter$17;
   static final PyCode __init__$18;
   static final PyCode set_parser$19;
   static final PyCode set_short_opt_delimiter$20;
   static final PyCode set_long_opt_delimiter$21;
   static final PyCode indent$22;
   static final PyCode dedent$23;
   static final PyCode format_usage$24;
   static final PyCode format_heading$25;
   static final PyCode _format_text$26;
   static final PyCode format_description$27;
   static final PyCode format_epilog$28;
   static final PyCode expand_default$29;
   static final PyCode format_option$30;
   static final PyCode store_option_strings$31;
   static final PyCode format_option_strings$32;
   static final PyCode IndentedHelpFormatter$33;
   static final PyCode __init__$34;
   static final PyCode format_usage$35;
   static final PyCode format_heading$36;
   static final PyCode TitledHelpFormatter$37;
   static final PyCode __init__$38;
   static final PyCode format_usage$39;
   static final PyCode format_heading$40;
   static final PyCode _parse_num$41;
   static final PyCode _parse_int$42;
   static final PyCode _parse_long$43;
   static final PyCode check_builtin$44;
   static final PyCode check_choice$45;
   static final PyCode Option$46;
   static final PyCode __init__$47;
   static final PyCode _check_opt_strings$48;
   static final PyCode _set_opt_strings$49;
   static final PyCode _set_attrs$50;
   static final PyCode _check_action$51;
   static final PyCode _check_type$52;
   static final PyCode _check_choice$53;
   static final PyCode _check_dest$54;
   static final PyCode _check_const$55;
   static final PyCode _check_nargs$56;
   static final PyCode _check_callback$57;
   static final PyCode __str__$58;
   static final PyCode takes_value$59;
   static final PyCode get_opt_string$60;
   static final PyCode check_value$61;
   static final PyCode convert_value$62;
   static final PyCode process$63;
   static final PyCode take_action$64;
   static final PyCode isbasestring$65;
   static final PyCode isbasestring$66;
   static final PyCode Values$67;
   static final PyCode __init__$68;
   static final PyCode __str__$69;
   static final PyCode __cmp__$70;
   static final PyCode _update_careful$71;
   static final PyCode _update_loose$72;
   static final PyCode _update$73;
   static final PyCode read_module$74;
   static final PyCode read_file$75;
   static final PyCode ensure_value$76;
   static final PyCode OptionContainer$77;
   static final PyCode __init__$78;
   static final PyCode _create_option_mappings$79;
   static final PyCode _share_option_mappings$80;
   static final PyCode set_conflict_handler$81;
   static final PyCode set_description$82;
   static final PyCode get_description$83;
   static final PyCode destroy$84;
   static final PyCode _check_conflict$85;
   static final PyCode add_option$86;
   static final PyCode add_options$87;
   static final PyCode get_option$88;
   static final PyCode has_option$89;
   static final PyCode remove_option$90;
   static final PyCode format_option_help$91;
   static final PyCode format_description$92;
   static final PyCode format_help$93;
   static final PyCode OptionGroup$94;
   static final PyCode __init__$95;
   static final PyCode _create_option_list$96;
   static final PyCode set_title$97;
   static final PyCode destroy$98;
   static final PyCode format_help$99;
   static final PyCode OptionParser$100;
   static final PyCode __init__$101;
   static final PyCode destroy$102;
   static final PyCode _create_option_list$103;
   static final PyCode _add_help_option$104;
   static final PyCode _add_version_option$105;
   static final PyCode _populate_option_list$106;
   static final PyCode _init_parsing_state$107;
   static final PyCode set_usage$108;
   static final PyCode enable_interspersed_args$109;
   static final PyCode disable_interspersed_args$110;
   static final PyCode set_process_default_values$111;
   static final PyCode set_default$112;
   static final PyCode set_defaults$113;
   static final PyCode _get_all_options$114;
   static final PyCode get_default_values$115;
   static final PyCode add_option_group$116;
   static final PyCode get_option_group$117;
   static final PyCode _get_args$118;
   static final PyCode parse_args$119;
   static final PyCode check_values$120;
   static final PyCode _process_args$121;
   static final PyCode _match_long_opt$122;
   static final PyCode _process_long_opt$123;
   static final PyCode _process_short_opts$124;
   static final PyCode get_prog_name$125;
   static final PyCode expand_prog_name$126;
   static final PyCode get_description$127;
   static final PyCode exit$128;
   static final PyCode error$129;
   static final PyCode get_usage$130;
   static final PyCode print_usage$131;
   static final PyCode get_version$132;
   static final PyCode print_version$133;
   static final PyCode format_option_help$134;
   static final PyCode format_epilog$135;
   static final PyCode format_help$136;
   static final PyCode _get_encoding$137;
   static final PyCode print_help$138;
   static final PyCode _match_abbrev$139;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("A powerful, extensible, and easy-to-use option parser.\n\nBy Greg Ward <gward@python.net>\n\nOriginally distributed as Optik.\n\nFor support, use the optik-users@lists.sourceforge.net mailing list\n(http://lists.sourceforge.net/lists/listinfo/optik-users).\n\nSimple usage example:\n\n   from optparse import OptionParser\n\n   parser = OptionParser()\n   parser.add_option(\"-f\", \"--file\", dest=\"filename\",\n                     help=\"write report to FILE\", metavar=\"FILE\")\n   parser.add_option(\"-q\", \"--quiet\",\n                     action=\"store_false\", dest=\"verbose\", default=True,\n                     help=\"don't print status messages to stdout\")\n\n   (options, args) = parser.parse_args()\n"));
      var1.setline(22);
      PyString.fromInterned("A powerful, extensible, and easy-to-use option parser.\n\nBy Greg Ward <gward@python.net>\n\nOriginally distributed as Optik.\n\nFor support, use the optik-users@lists.sourceforge.net mailing list\n(http://lists.sourceforge.net/lists/listinfo/optik-users).\n\nSimple usage example:\n\n   from optparse import OptionParser\n\n   parser = OptionParser()\n   parser.add_option(\"-f\", \"--file\", dest=\"filename\",\n                     help=\"write report to FILE\", metavar=\"FILE\")\n   parser.add_option(\"-q\", \"--quiet\",\n                     action=\"store_false\", dest=\"verbose\", default=True,\n                     help=\"don't print status messages to stdout\")\n\n   (options, args) = parser.parse_args()\n");
      var1.setline(24);
      PyString var3 = PyString.fromInterned("1.5.3");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(26);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("Option"), PyString.fromInterned("make_option"), PyString.fromInterned("SUPPRESS_HELP"), PyString.fromInterned("SUPPRESS_USAGE"), PyString.fromInterned("Values"), PyString.fromInterned("OptionContainer"), PyString.fromInterned("OptionGroup"), PyString.fromInterned("OptionParser"), PyString.fromInterned("HelpFormatter"), PyString.fromInterned("IndentedHelpFormatter"), PyString.fromInterned("TitledHelpFormatter"), PyString.fromInterned("OptParseError"), PyString.fromInterned("OptionError"), PyString.fromInterned("OptionConflictError"), PyString.fromInterned("OptionValueError"), PyString.fromInterned("BadOptionError")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(43);
      var3 = PyString.fromInterned("\nCopyright (c) 2001-2006 Gregory P. Ward.  All rights reserved.\nCopyright (c) 2002-2006 Python Software Foundation.  All rights reserved.\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions are\nmet:\n\n  * Redistributions of source code must retain the above copyright\n    notice, this list of conditions and the following disclaimer.\n\n  * Redistributions in binary form must reproduce the above copyright\n    notice, this list of conditions and the following disclaimer in the\n    documentation and/or other materials provided with the distribution.\n\n  * Neither the name of the author nor the names of its\n    contributors may be used to endorse or promote products derived from\n    this software without specific prior written permission.\n\nTHIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS \"AS\nIS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED\nTO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A\nPARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR\nCONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,\nEXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,\nPROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR\nPROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF\nLIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING\nNEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS\nSOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n");
      var1.setlocal("__copyright__", var3);
      var3 = null;
      var1.setline(75);
      PyObject var9 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var9);
      var3 = null;
      var9 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var9);
      var3 = null;
      var1.setline(76);
      var9 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var9);
      var3 = null;
      var1.setline(77);
      var9 = imp.importOne("textwrap", var1, -1);
      var1.setlocal("textwrap", var9);
      var3 = null;
      var1.setline(79);
      PyObject[] var11 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var11, _repr$1, (PyObject)null);
      var1.setlocal("_repr", var12);
      var3 = null;

      PyObject[] var4;
      PyFunction var8;
      PyObject var10;
      PyException var13;
      try {
         var1.setline(90);
         String[] var14 = new String[]{"gettext"};
         var11 = imp.importFrom("gettext", var14, var1, -1);
         var10 = var11[0];
         var1.setlocal("gettext", var10);
         var4 = null;
      } catch (Throwable var6) {
         var13 = Py.setException(var6, var1);
         if (!var13.match(var1.getname("ImportError"))) {
            throw var13;
         }

         var1.setline(92);
         var4 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var4, gettext$2, (PyObject)null);
         var1.setlocal("gettext", var8);
         var4 = null;
      }

      var1.setline(94);
      var9 = var1.getname("gettext");
      var1.setlocal("_", var9);
      var3 = null;
      var1.setline(97);
      var11 = new PyObject[]{var1.getname("Exception")};
      var10 = Py.makeClass("OptParseError", var11, OptParseError$3);
      var1.setlocal("OptParseError", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(105);
      var11 = new PyObject[]{var1.getname("OptParseError")};
      var10 = Py.makeClass("OptionError", var11, OptionError$6);
      var1.setlocal("OptionError", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(121);
      var11 = new PyObject[]{var1.getname("OptionError")};
      var10 = Py.makeClass("OptionConflictError", var11, OptionConflictError$9);
      var1.setlocal("OptionConflictError", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(126);
      var11 = new PyObject[]{var1.getname("OptParseError")};
      var10 = Py.makeClass("OptionValueError", var11, OptionValueError$10);
      var1.setlocal("OptionValueError", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(132);
      var11 = new PyObject[]{var1.getname("OptParseError")};
      var10 = Py.makeClass("BadOptionError", var11, BadOptionError$11);
      var1.setlocal("BadOptionError", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(142);
      var11 = new PyObject[]{var1.getname("BadOptionError")};
      var10 = Py.makeClass("AmbiguousOptionError", var11, AmbiguousOptionError$14);
      var1.setlocal("AmbiguousOptionError", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(155);
      var11 = Py.EmptyObjects;
      var10 = Py.makeClass("HelpFormatter", var11, HelpFormatter$17);
      var1.setlocal("HelpFormatter", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(361);
      var11 = new PyObject[]{var1.getname("HelpFormatter")};
      var10 = Py.makeClass("IndentedHelpFormatter", var11, IndentedHelpFormatter$33);
      var1.setlocal("IndentedHelpFormatter", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(380);
      var11 = new PyObject[]{var1.getname("HelpFormatter")};
      var10 = Py.makeClass("TitledHelpFormatter", var11, TitledHelpFormatter$37);
      var1.setlocal("TitledHelpFormatter", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(399);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _parse_num$41, (PyObject)null);
      var1.setlocal("_parse_num", var12);
      var3 = null;
      var1.setline(412);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _parse_int$42, (PyObject)null);
      var1.setlocal("_parse_int", var12);
      var3 = null;
      var1.setline(415);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _parse_long$43, (PyObject)null);
      var1.setlocal("_parse_long", var12);
      var3 = null;
      var1.setline(418);
      PyDictionary var15 = new PyDictionary(new PyObject[]{PyString.fromInterned("int"), new PyTuple(new PyObject[]{var1.getname("_parse_int"), var1.getname("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("integer"))}), PyString.fromInterned("long"), new PyTuple(new PyObject[]{var1.getname("_parse_long"), var1.getname("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("long integer"))}), PyString.fromInterned("float"), new PyTuple(new PyObject[]{var1.getname("float"), var1.getname("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("floating-point"))}), PyString.fromInterned("complex"), new PyTuple(new PyObject[]{var1.getname("complex"), var1.getname("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("complex"))})});
      var1.setlocal("_builtin_cvt", var15);
      var3 = null;
      var1.setline(423);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, check_builtin$44, (PyObject)null);
      var1.setlocal("check_builtin", var12);
      var3 = null;
      var1.setline(431);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, check_choice$45, (PyObject)null);
      var1.setlocal("check_choice", var12);
      var3 = null;
      var1.setline(442);
      PyTuple var16 = new PyTuple(new PyObject[]{PyString.fromInterned("NO"), PyString.fromInterned("DEFAULT")});
      var1.setlocal("NO_DEFAULT", var16);
      var3 = null;
      var1.setline(445);
      var11 = Py.EmptyObjects;
      var10 = Py.makeClass("Option", var11, Option$46);
      var1.setlocal("Option", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(823);
      var9 = PyString.fromInterned("SUPPRESS")._add(PyString.fromInterned("HELP"));
      var1.setlocal("SUPPRESS_HELP", var9);
      var3 = null;
      var1.setline(824);
      var9 = PyString.fromInterned("SUPPRESS")._add(PyString.fromInterned("USAGE"));
      var1.setlocal("SUPPRESS_USAGE", var9);
      var3 = null;

      label26: {
         try {
            var1.setline(827);
            var1.getname("basestring");
         } catch (Throwable var5) {
            var13 = Py.setException(var5, var1);
            if (var13.match(var1.getname("NameError"))) {
               var1.setline(829);
               var4 = Py.EmptyObjects;
               var8 = new PyFunction(var1.f_globals, var4, isbasestring$65, (PyObject)null);
               var1.setlocal("isbasestring", var8);
               var4 = null;
               break label26;
            }

            throw var13;
         }

         var1.setline(832);
         var4 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var4, isbasestring$66, (PyObject)null);
         var1.setlocal("isbasestring", var8);
         var4 = null;
      }

      var1.setline(835);
      var11 = Py.EmptyObjects;
      var10 = Py.makeClass("Values", var11, Values$67);
      var1.setlocal("Values", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(900);
      var11 = Py.EmptyObjects;
      var10 = Py.makeClass("OptionContainer", var11, OptionContainer$77);
      var1.setlocal("OptionContainer", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1088);
      var11 = new PyObject[]{var1.getname("OptionContainer")};
      var10 = Py.makeClass("OptionGroup", var11, OptionGroup$94);
      var1.setlocal("OptionGroup", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1118);
      var11 = new PyObject[]{var1.getname("OptionContainer")};
      var10 = Py.makeClass("OptionParser", var11, OptionParser$100);
      var1.setlocal("OptionParser", var10);
      var4 = null;
      Arrays.fill(var11, (Object)null);
      var1.setline(1674);
      var11 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var11, _match_abbrev$139, PyString.fromInterned("_match_abbrev(s : string, wordmap : {string : Option}) -> string\n\n    Return the string key in 'wordmap' for which 's' is an unambiguous\n    abbreviation.  If 's' is found to be ambiguous or doesn't match any of\n    'words', raise BadOptionError.\n    "));
      var1.setlocal("_match_abbrev", var12);
      var3 = null;
      var1.setline(1703);
      var9 = var1.getname("Option");
      var1.setlocal("make_option", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _repr$1(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyObject var3 = PyString.fromInterned("<%s at 0x%x: %s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__").__getattr__("__name__"), var1.getglobal("id").__call__(var2, var1.getlocal(0)), var1.getlocal(0)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gettext$2(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OptParseError$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(98);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(101);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$5, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$5(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getlocal(0).__getattr__("msg");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OptionError$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raised if an Option instance is created with invalid or\n    inconsistent arguments.\n    "));
      var1.setline(109);
      PyString.fromInterned("\n    Raised if an Option instance is created with invalid or\n    inconsistent arguments.\n    ");
      var1.setline(111);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$8, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("msg", var3);
      var3 = null;
      var1.setline(113);
      var3 = var1.getglobal("str").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("option_id", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$8(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("option_id").__nonzero__()) {
         var1.setline(117);
         var3 = PyString.fromInterned("option %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("option_id"), var1.getlocal(0).__getattr__("msg")}));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(119);
         var3 = var1.getlocal(0).__getattr__("msg");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject OptionConflictError$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raised if conflicting options are added to an OptionParser.\n    "));
      var1.setline(124);
      PyString.fromInterned("\n    Raised if conflicting options are added to an OptionParser.\n    ");
      return var1.getf_locals();
   }

   public PyObject OptionValueError$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raised if an invalid option value is encountered on the command\n    line.\n    "));
      var1.setline(130);
      PyString.fromInterned("\n    Raised if an invalid option value is encountered on the command\n    line.\n    ");
      return var1.getf_locals();
   }

   public PyObject BadOptionError$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raised if an invalid option is seen on the command line.\n    "));
      var1.setline(135);
      PyString.fromInterned("\n    Raised if an invalid option is seen on the command line.\n    ");
      var1.setline(136);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$12, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(139);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$13, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$12(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("opt_str", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$13(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyObject var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no such option: %s"))._mod(var1.getlocal(0).__getattr__("opt_str"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject AmbiguousOptionError$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raised if an ambiguous option is seen on the command line.\n    "));
      var1.setline(145);
      PyString.fromInterned("\n    Raised if an ambiguous option is seen on the command line.\n    ");
      var1.setline(146);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$15, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(150);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$16, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$15(PyFrame var1, ThreadState var2) {
      var1.setline(147);
      var1.getglobal("BadOptionError").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(148);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("possibilities", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$16(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyObject var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ambiguous option: %s (%s?)"))._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("opt_str"), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("possibilities"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject HelpFormatter$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Abstract base class for formatting option help.  OptionParser\n    instances should use one of the HelpFormatter subclasses for\n    formatting help; by default IndentedHelpFormatter is used.\n\n    Instance attributes:\n      parser : OptionParser\n        the controlling OptionParser instance\n      indent_increment : int\n        the number of columns to indent per nesting level\n      max_help_position : int\n        the maximum starting column for option help text\n      help_position : int\n        the calculated starting column for option help text;\n        initially the same as the maximum\n      width : int\n        total number of columns for output (pass None to constructor for\n        this value to be taken from the $COLUMNS environment variable)\n      level : int\n        current indentation level\n      current_indent : int\n        current indentation level (in columns)\n      help_width : int\n        number of columns available for option help text (calculated)\n      default_tag : str\n        text to replace with each option's default value, \"%default\"\n        by default.  Set to false value to disable default value expansion.\n      option_strings : { Option : str }\n        maps Option instances to the snippet of help text explaining\n        the syntax of that option, e.g. \"-h, --help\" or\n        \"-fFILE, --file=FILE\"\n      _short_opt_fmt : str\n        format string controlling how short options with values are\n        printed in help text.  Must be either \"%s%s\" (\"-fFILE\") or\n        \"%s %s\" (\"-f FILE\"), because those are the two syntaxes that\n        Optik supports.\n      _long_opt_fmt : str\n        similar but for long options; must be either \"%s %s\" (\"--file FILE\")\n        or \"%s=%s\" (\"--file=FILE\").\n    "));
      var1.setline(196);
      PyString.fromInterned("\n    Abstract base class for formatting option help.  OptionParser\n    instances should use one of the HelpFormatter subclasses for\n    formatting help; by default IndentedHelpFormatter is used.\n\n    Instance attributes:\n      parser : OptionParser\n        the controlling OptionParser instance\n      indent_increment : int\n        the number of columns to indent per nesting level\n      max_help_position : int\n        the maximum starting column for option help text\n      help_position : int\n        the calculated starting column for option help text;\n        initially the same as the maximum\n      width : int\n        total number of columns for output (pass None to constructor for\n        this value to be taken from the $COLUMNS environment variable)\n      level : int\n        current indentation level\n      current_indent : int\n        current indentation level (in columns)\n      help_width : int\n        number of columns available for option help text (calculated)\n      default_tag : str\n        text to replace with each option's default value, \"%default\"\n        by default.  Set to false value to disable default value expansion.\n      option_strings : { Option : str }\n        maps Option instances to the snippet of help text explaining\n        the syntax of that option, e.g. \"-h, --help\" or\n        \"-fFILE, --file=FILE\"\n      _short_opt_fmt : str\n        format string controlling how short options with values are\n        printed in help text.  Must be either \"%s%s\" (\"-fFILE\") or\n        \"%s %s\" (\"-f FILE\"), because those are the two syntaxes that\n        Optik supports.\n      _long_opt_fmt : str\n        similar but for long options; must be either \"%s %s\" (\"--file FILE\")\n        or \"%s=%s\" (\"--file=FILE\").\n    ");
      var1.setline(198);
      PyString var3 = PyString.fromInterned("none");
      var1.setlocal("NO_DEFAULT_VALUE", var3);
      var3 = null;
      var1.setline(200);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(224);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_parser$19, (PyObject)null);
      var1.setlocal("set_parser", var5);
      var3 = null;
      var1.setline(227);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_short_opt_delimiter$20, (PyObject)null);
      var1.setlocal("set_short_opt_delimiter", var5);
      var3 = null;
      var1.setline(233);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_long_opt_delimiter$21, (PyObject)null);
      var1.setlocal("set_long_opt_delimiter", var5);
      var3 = null;
      var1.setline(239);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, indent$22, (PyObject)null);
      var1.setlocal("indent", var5);
      var3 = null;
      var1.setline(243);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, dedent$23, (PyObject)null);
      var1.setlocal("dedent", var5);
      var3 = null;
      var1.setline(248);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, format_usage$24, (PyObject)null);
      var1.setlocal("format_usage", var5);
      var3 = null;
      var1.setline(251);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, format_heading$25, (PyObject)null);
      var1.setlocal("format_heading", var5);
      var3 = null;
      var1.setline(254);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _format_text$26, PyString.fromInterned("\n        Format a paragraph of free-form text for inclusion in the\n        help output at the current indentation level.\n        "));
      var1.setlocal("_format_text", var5);
      var3 = null;
      var1.setline(266);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, format_description$27, (PyObject)null);
      var1.setlocal("format_description", var5);
      var3 = null;
      var1.setline(272);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, format_epilog$28, (PyObject)null);
      var1.setlocal("format_epilog", var5);
      var3 = null;
      var1.setline(279);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, expand_default$29, (PyObject)null);
      var1.setlocal("expand_default", var5);
      var3 = null;
      var1.setline(289);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, format_option$30, (PyObject)null);
      var1.setlocal("format_option", var5);
      var3 = null;
      var1.setline(324);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, store_option_strings$31, (PyObject)null);
      var1.setlocal("store_option_strings", var5);
      var3 = null;
      var1.setline(342);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, format_option_strings$32, PyString.fromInterned("Return a comma-separated list of option strings & metavariables."));
      var1.setlocal("format_option_strings", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("parser", var3);
      var3 = null;
      var1.setline(206);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("indent_increment", var3);
      var3 = null;
      var1.setline(207);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("help_position", var3);
      var1.getlocal(0).__setattr__("max_help_position", var3);
      var1.setline(208);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(210);
            var3 = var1.getglobal("int").__call__(var2, var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("COLUMNS")));
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(new PyTuple(new PyObject[]{var1.getglobal("KeyError"), var1.getglobal("ValueError")}))) {
               throw var6;
            }

            var1.setline(212);
            PyInteger var4 = Py.newInteger(80);
            var1.setlocal(3, var4);
            var4 = null;
         }

         var1.setline(213);
         var3 = var1.getlocal(3);
         var3 = var3._isub(Py.newInteger(2));
         var1.setlocal(3, var3);
      }

      var1.setline(214);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("width", var3);
      var3 = null;
      var1.setline(215);
      PyInteger var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"current_indent", var7);
      var3 = null;
      var1.setline(216);
      var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"level", var7);
      var3 = null;
      var1.setline(217);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("help_width", var3);
      var3 = null;
      var1.setline(218);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("short_first", var3);
      var3 = null;
      var1.setline(219);
      PyString var8 = PyString.fromInterned("%default");
      var1.getlocal(0).__setattr__((String)"default_tag", var8);
      var3 = null;
      var1.setline(220);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"option_strings", var9);
      var3 = null;
      var1.setline(221);
      var8 = PyString.fromInterned("%s %s");
      var1.getlocal(0).__setattr__((String)"_short_opt_fmt", var8);
      var3 = null;
      var1.setline(222);
      var8 = PyString.fromInterned("%s=%s");
      var1.getlocal(0).__setattr__((String)"_long_opt_fmt", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_parser$19(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("parser", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_short_opt_delimiter$20(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned(" ")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(229);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("invalid metavar delimiter for short options: %r")._mod(var1.getlocal(1))));
      } else {
         var1.setline(231);
         var3 = PyString.fromInterned("%s")._add(var1.getlocal(1))._add(PyString.fromInterned("%s"));
         var1.getlocal(0).__setattr__("_short_opt_fmt", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject set_long_opt_delimiter$21(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("="), PyString.fromInterned(" ")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(235);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("invalid metavar delimiter for long options: %r")._mod(var1.getlocal(1))));
      } else {
         var1.setline(237);
         var3 = PyString.fromInterned("%s")._add(var1.getlocal(1))._add(PyString.fromInterned("%s"));
         var1.getlocal(0).__setattr__("_long_opt_fmt", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject indent$22(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "current_indent";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(var1.getlocal(0).__getattr__("indent_increment"));
      var4.__setattr__(var3, var5);
      var1.setline(241);
      var10000 = var1.getlocal(0);
      var3 = "level";
      var4 = var10000;
      var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dedent$23(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "current_indent";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._isub(var1.getlocal(0).__getattr__("indent_increment"));
      var4.__setattr__(var3, var5);
      var1.setline(245);
      if (var1.getglobal("__debug__").__nonzero__()) {
         PyObject var6 = var1.getlocal(0).__getattr__("current_indent");
         var10000 = var6._ge(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Indent decreased below 0."));
         }
      }

      var1.setline(246);
      var10000 = var1.getlocal(0);
      var3 = "level";
      var4 = var10000;
      var5 = var4.__getattr__(var3);
      var5 = var5._isub(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_usage$24(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      throw Py.makeException(var1.getglobal("NotImplementedError"), PyString.fromInterned("subclasses must implement"));
   }

   public PyObject format_heading$25(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      throw Py.makeException(var1.getglobal("NotImplementedError"), PyString.fromInterned("subclasses must implement"));
   }

   public PyObject _format_text$26(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyString.fromInterned("\n        Format a paragraph of free-form text for inclusion in the\n        help output at the current indentation level.\n        ");
      var1.setline(259);
      PyObject var3 = var1.getlocal(0).__getattr__("width")._sub(var1.getlocal(0).__getattr__("current_indent"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(260);
      var3 = PyString.fromInterned(" ")._mul(var1.getlocal(0).__getattr__("current_indent"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(261);
      PyObject var10000 = var1.getglobal("textwrap").__getattr__("fill");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(3)};
      String[] var4 = new String[]{"initial_indent", "subsequent_indent"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format_description$27(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(268);
         PyObject var4 = var1.getlocal(0).__getattr__("_format_text").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned("\n"));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(270);
         PyString var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject format_epilog$28(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(274);
         PyObject var4 = PyString.fromInterned("\n")._add(var1.getlocal(0).__getattr__("_format_text").__call__(var2, var1.getlocal(1)))._add(PyString.fromInterned("\n"));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(276);
         PyString var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject expand_default$29(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyObject var3 = var1.getlocal(0).__getattr__("parser");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("default_tag").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(281);
         var3 = var1.getlocal(1).__getattr__("help");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(283);
         PyObject var4 = var1.getlocal(0).__getattr__("parser").__getattr__("defaults").__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("dest"));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(284);
         var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("NO_DEFAULT"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(2);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(285);
            var4 = var1.getlocal(0).__getattr__("NO_DEFAULT_VALUE");
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(287);
         var3 = var1.getlocal(1).__getattr__("help").__getattr__("replace").__call__(var2, var1.getlocal(0).__getattr__("default_tag"), var1.getglobal("str").__call__(var2, var1.getlocal(2)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject format_option$30(PyFrame var1, ThreadState var2) {
      var1.setline(304);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(305);
      PyObject var5 = var1.getlocal(0).__getattr__("option_strings").__getitem__(var1.getlocal(1));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(306);
      var5 = var1.getlocal(0).__getattr__("help_position")._sub(var1.getlocal(0).__getattr__("current_indent"))._sub(Py.newInteger(2));
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(307);
      var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      PyObject var10000 = var5._gt(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(308);
         var5 = PyString.fromInterned("%*s%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("current_indent"), PyString.fromInterned(""), var1.getlocal(3)}));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(309);
         var5 = var1.getlocal(0).__getattr__("help_position");
         var1.setlocal(5, var5);
         var3 = null;
      } else {
         var1.setline(311);
         var5 = PyString.fromInterned("%*s%-*s  ")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("current_indent"), PyString.fromInterned(""), var1.getlocal(4), var1.getlocal(3)}));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(312);
         PyInteger var6 = Py.newInteger(0);
         var1.setlocal(5, var6);
         var3 = null;
      }

      var1.setline(313);
      var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
      var1.setline(314);
      if (var1.getlocal(1).__getattr__("help").__nonzero__()) {
         var1.setline(315);
         var5 = var1.getlocal(0).__getattr__("expand_default").__call__(var2, var1.getlocal(1));
         var1.setlocal(6, var5);
         var3 = null;
         var1.setline(316);
         var5 = var1.getglobal("textwrap").__getattr__("wrap").__call__(var2, var1.getlocal(6), var1.getlocal(0).__getattr__("help_width"));
         var1.setlocal(7, var5);
         var3 = null;
         var1.setline(317);
         var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("%*s%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), PyString.fromInterned(""), var1.getlocal(7).__getitem__(Py.newInteger(0))})));
         var1.setline(318);
         var10000 = var1.getlocal(2).__getattr__("extend");
         PyList var10002 = new PyList();
         var5 = var10002.__getattr__("append");
         var1.setlocal(8, var5);
         var3 = null;
         var1.setline(319);
         var5 = var1.getlocal(7).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(319);
            PyObject var4 = var5.__iternext__();
            if (var4 == null) {
               var1.setline(319);
               var1.dellocal(8);
               var10000.__call__((ThreadState)var2, (PyObject)var10002);
               break;
            }

            var1.setlocal(9, var4);
            var1.setline(318);
            var1.getlocal(8).__call__(var2, PyString.fromInterned("%*s%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("help_position"), PyString.fromInterned(""), var1.getlocal(9)})));
         }
      } else {
         var1.setline(320);
         var5 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
         var10000 = var5._ne(PyString.fromInterned("\n"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(321);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         }
      }

      var1.setline(322);
      var5 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject store_option_strings$31(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      var1.getlocal(0).__getattr__("indent").__call__(var2);
      var1.setline(326);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(327);
      PyObject var8 = var1.getlocal(1).__getattr__("option_list").__iter__();

      while(true) {
         var1.setline(327);
         PyObject var4 = var8.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(331);
            var1.getlocal(0).__getattr__("indent").__call__(var2);
            var1.setline(332);
            var8 = var1.getlocal(1).__getattr__("option_groups").__iter__();

            while(true) {
               var1.setline(332);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  var1.setline(337);
                  var1.getlocal(0).__getattr__("dedent").__call__(var2);
                  var1.setline(338);
                  var1.getlocal(0).__getattr__("dedent").__call__(var2);
                  var1.setline(339);
                  var8 = var1.getglobal("min").__call__(var2, var1.getlocal(2)._add(Py.newInteger(2)), var1.getlocal(0).__getattr__("max_help_position"));
                  var1.getlocal(0).__setattr__("help_position", var8);
                  var3 = null;
                  var1.setline(340);
                  var8 = var1.getlocal(0).__getattr__("width")._sub(var1.getlocal(0).__getattr__("help_position"));
                  var1.getlocal(0).__setattr__("help_width", var8);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(5, var4);
               var1.setline(333);
               var5 = var1.getlocal(5).__getattr__("option_list").__iter__();

               while(true) {
                  var1.setline(333);
                  PyObject var6 = var5.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(3, var6);
                  var1.setline(334);
                  PyObject var7 = var1.getlocal(0).__getattr__("format_option_strings").__call__(var2, var1.getlocal(3));
                  var1.setlocal(4, var7);
                  var7 = null;
                  var1.setline(335);
                  var7 = var1.getlocal(4);
                  var1.getlocal(0).__getattr__("option_strings").__setitem__(var1.getlocal(3), var7);
                  var7 = null;
                  var1.setline(336);
                  var7 = var1.getglobal("max").__call__(var2, var1.getlocal(2), var1.getglobal("len").__call__(var2, var1.getlocal(4))._add(var1.getlocal(0).__getattr__("current_indent")));
                  var1.setlocal(2, var7);
                  var7 = null;
               }
            }
         }

         var1.setlocal(3, var4);
         var1.setline(328);
         var5 = var1.getlocal(0).__getattr__("format_option_strings").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(329);
         var5 = var1.getlocal(4);
         var1.getlocal(0).__getattr__("option_strings").__setitem__(var1.getlocal(3), var5);
         var5 = null;
         var1.setline(330);
         var5 = var1.getglobal("max").__call__(var2, var1.getlocal(2), var1.getglobal("len").__call__(var2, var1.getlocal(4))._add(var1.getlocal(0).__getattr__("current_indent")));
         var1.setlocal(2, var5);
         var5 = null;
      }
   }

   public PyObject format_option_strings$32(PyFrame var1, ThreadState var2) {
      var1.setline(343);
      PyString.fromInterned("Return a comma-separated list of option strings & metavariables.");
      var1.setline(344);
      PyObject var3;
      if (var1.getlocal(1).__getattr__("takes_value").__call__(var2).__nonzero__()) {
         var1.setline(345);
         PyObject var10000 = var1.getlocal(1).__getattr__("metavar");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("dest").__getattr__("upper").__call__(var2);
         }

         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(346);
         PyList var6 = new PyList();
         var3 = var6.__getattr__("append");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(347);
         var3 = var1.getlocal(1).__getattr__("_short_opts").__iter__();

         label32:
         while(true) {
            var1.setline(347);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(347);
               var1.dellocal(4);
               PyList var5 = var6;
               var1.setlocal(3, var5);
               var3 = null;
               var1.setline(348);
               var6 = new PyList();
               var3 = var6.__getattr__("append");
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(349);
               var3 = var1.getlocal(1).__getattr__("_long_opts").__iter__();

               while(true) {
                  var1.setline(349);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(349);
                     var1.dellocal(7);
                     var5 = var6;
                     var1.setlocal(6, var5);
                     var3 = null;
                     break label32;
                  }

                  var1.setlocal(8, var4);
                  var1.setline(348);
                  var1.getlocal(7).__call__(var2, var1.getlocal(0).__getattr__("_long_opt_fmt")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(2)})));
               }
            }

            var1.setlocal(5, var4);
            var1.setline(346);
            var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("_short_opt_fmt")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(2)})));
         }
      } else {
         var1.setline(351);
         var3 = var1.getlocal(1).__getattr__("_short_opts");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(352);
         var3 = var1.getlocal(1).__getattr__("_long_opts");
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(354);
      if (var1.getlocal(0).__getattr__("short_first").__nonzero__()) {
         var1.setline(355);
         var3 = var1.getlocal(3)._add(var1.getlocal(6));
         var1.setlocal(9, var3);
         var3 = null;
      } else {
         var1.setline(357);
         var3 = var1.getlocal(6)._add(var1.getlocal(3));
         var1.setlocal(9, var3);
         var3 = null;
      }

      var1.setline(359);
      var3 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(9));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IndentedHelpFormatter$33(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Format help with indented section bodies.\n    "));
      var1.setline(363);
      PyString.fromInterned("Format help with indented section bodies.\n    ");
      var1.setline(365);
      PyObject[] var3 = new PyObject[]{Py.newInteger(2), Py.newInteger(24), var1.getname("None"), Py.newInteger(1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$34, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(373);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_usage$35, (PyObject)null);
      var1.setlocal("format_usage", var4);
      var3 = null;
      var1.setline(376);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_heading$36, (PyObject)null);
      var1.setlocal("format_heading", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$34(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyObject var10000 = var1.getglobal("HelpFormatter").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_usage$35(PyFrame var1, ThreadState var2) {
      var1.setline(374);
      PyObject var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Usage: %s\n"))._mod(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format_heading$36(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyObject var3 = PyString.fromInterned("%*s%s:\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("current_indent"), PyString.fromInterned(""), var1.getlocal(1)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TitledHelpFormatter$37(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Format help with underlined section headers.\n    "));
      var1.setline(382);
      PyString.fromInterned("Format help with underlined section headers.\n    ");
      var1.setline(384);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(24), var1.getname("None"), Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$38, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(392);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_usage$39, (PyObject)null);
      var1.setlocal("format_usage", var4);
      var3 = null;
      var1.setline(395);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_heading$40, (PyObject)null);
      var1.setlocal("format_heading", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$38(PyFrame var1, ThreadState var2) {
      var1.setline(389);
      PyObject var10000 = var1.getglobal("HelpFormatter").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_usage$39(PyFrame var1, ThreadState var2) {
      var1.setline(393);
      PyObject var3 = PyString.fromInterned("%s  %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("format_heading").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Usage"))), var1.getlocal(1)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format_heading$40(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyObject var3 = PyString.fromInterned("%s\n%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned("=-").__getitem__(var1.getlocal(0).__getattr__("level"))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(1)))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _parse_num$41(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._eq(PyString.fromInterned("0x"));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(401);
         var4 = Py.newInteger(16);
         var1.setlocal(2, var4);
         var3 = null;
      } else {
         var1.setline(402);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null).__getattr__("lower").__call__(var2);
         var10000 = var3._eq(PyString.fromInterned("0b"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(403);
            var4 = Py.newInteger(2);
            var1.setlocal(2, var4);
            var3 = null;
            var1.setline(404);
            Object var6 = var1.getlocal(0).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            if (!((PyObject)var6).__nonzero__()) {
               var6 = PyString.fromInterned("0");
            }

            Object var5 = var6;
            var1.setlocal(0, (PyObject)var5);
            var3 = null;
         } else {
            var1.setline(405);
            var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("0"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(406);
               var4 = Py.newInteger(8);
               var1.setlocal(2, var4);
               var3 = null;
            } else {
               var1.setline(408);
               var4 = Py.newInteger(10);
               var1.setlocal(2, var4);
               var3 = null;
            }
         }
      }

      var1.setline(410);
      var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _parse_int$42(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      PyObject var3 = var1.getglobal("_parse_num").__call__(var2, var1.getlocal(0), var1.getglobal("int"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _parse_long$43(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyObject var3 = var1.getglobal("_parse_num").__call__(var2, var1.getlocal(0), var1.getglobal("long"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject check_builtin$44(PyFrame var1, ThreadState var2) {
      var1.setline(424);
      PyObject var3 = var1.getglobal("_builtin_cvt").__getitem__(var1.getlocal(0).__getattr__("type"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;

      try {
         var1.setline(426);
         var3 = var1.getlocal(3).__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (var7.match(var1.getglobal("ValueError"))) {
            var1.setline(428);
            throw Py.makeException(var1.getglobal("OptionValueError").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("option %s: invalid %s value: %r"))._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(4), var1.getlocal(2)}))));
         } else {
            throw var7;
         }
      }
   }

   public PyObject check_choice$45(PyFrame var1, ThreadState var2) {
      var1.setline(432);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("choices"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(433);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(435);
         PyObject var4 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("repr"), var1.getlocal(0).__getattr__("choices")));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(436);
         throw Py.makeException(var1.getglobal("OptionValueError").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("option %s: invalid choice: %r (choose from %s)"))._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)}))));
      }
   }

   public PyObject Option$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Instance attributes:\n      _short_opts : [string]\n      _long_opts : [string]\n\n      action : string\n      type : string\n      dest : string\n      default : any\n      nargs : int\n      const : any\n      choices : [string]\n      callback : function\n      callback_args : (any*)\n      callback_kwargs : { string : any }\n      help : string\n      metavar : string\n    "));
      var1.setline(463);
      PyString.fromInterned("\n    Instance attributes:\n      _short_opts : [string]\n      _long_opts : [string]\n\n      action : string\n      type : string\n      dest : string\n      default : any\n      nargs : int\n      const : any\n      choices : [string]\n      callback : function\n      callback_args : (any*)\n      callback_kwargs : { string : any }\n      help : string\n      metavar : string\n    ");
      var1.setline(467);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("action"), PyString.fromInterned("type"), PyString.fromInterned("dest"), PyString.fromInterned("default"), PyString.fromInterned("nargs"), PyString.fromInterned("const"), PyString.fromInterned("choices"), PyString.fromInterned("callback"), PyString.fromInterned("callback_args"), PyString.fromInterned("callback_kwargs"), PyString.fromInterned("help"), PyString.fromInterned("metavar")});
      var1.setlocal("ATTRS", var3);
      var3 = null;
      var1.setline(482);
      PyTuple var4 = new PyTuple(new PyObject[]{PyString.fromInterned("store"), PyString.fromInterned("store_const"), PyString.fromInterned("store_true"), PyString.fromInterned("store_false"), PyString.fromInterned("append"), PyString.fromInterned("append_const"), PyString.fromInterned("count"), PyString.fromInterned("callback"), PyString.fromInterned("help"), PyString.fromInterned("version")});
      var1.setlocal("ACTIONS", var4);
      var3 = null;
      var1.setline(496);
      var4 = new PyTuple(new PyObject[]{PyString.fromInterned("store"), PyString.fromInterned("store_const"), PyString.fromInterned("store_true"), PyString.fromInterned("store_false"), PyString.fromInterned("append"), PyString.fromInterned("append_const"), PyString.fromInterned("count")});
      var1.setlocal("STORE_ACTIONS", var4);
      var3 = null;
      var1.setline(506);
      var4 = new PyTuple(new PyObject[]{PyString.fromInterned("store"), PyString.fromInterned("append"), PyString.fromInterned("callback")});
      var1.setlocal("TYPED_ACTIONS", var4);
      var3 = null;
      var1.setline(512);
      var4 = new PyTuple(new PyObject[]{PyString.fromInterned("store"), PyString.fromInterned("append")});
      var1.setlocal("ALWAYS_TYPED_ACTIONS", var4);
      var3 = null;
      var1.setline(516);
      var4 = new PyTuple(new PyObject[]{PyString.fromInterned("store_const"), PyString.fromInterned("append_const")});
      var1.setlocal("CONST_ACTIONS", var4);
      var3 = null;
      var1.setline(521);
      var4 = new PyTuple(new PyObject[]{PyString.fromInterned("string"), PyString.fromInterned("int"), PyString.fromInterned("long"), PyString.fromInterned("float"), PyString.fromInterned("complex"), PyString.fromInterned("choice")});
      var1.setlocal("TYPES", var4);
      var3 = null;
      var1.setline(539);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("int"), var1.getname("check_builtin"), PyString.fromInterned("long"), var1.getname("check_builtin"), PyString.fromInterned("float"), var1.getname("check_builtin"), PyString.fromInterned("complex"), var1.getname("check_builtin"), PyString.fromInterned("choice"), var1.getname("check_choice")});
      var1.setlocal("TYPE_CHECKER", var5);
      var3 = null;
      var1.setline(555);
      PyObject var6 = var1.getname("None");
      var1.setlocal("CHECK_METHODS", var6);
      var3 = null;
      var1.setline(560);
      PyObject[] var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, __init__$47, (PyObject)null);
      var1.setlocal("__init__", var8);
      var3 = null;
      var1.setline(579);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _check_opt_strings$48, (PyObject)null);
      var1.setlocal("_check_opt_strings", var8);
      var3 = null;
      var1.setline(588);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _set_opt_strings$49, (PyObject)null);
      var1.setlocal("_set_opt_strings", var8);
      var3 = null;
      var1.setline(609);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _set_attrs$50, (PyObject)null);
      var1.setlocal("_set_attrs", var8);
      var3 = null;
      var1.setline(629);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _check_action$51, (PyObject)null);
      var1.setlocal("_check_action", var8);
      var3 = null;
      var1.setline(635);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _check_type$52, (PyObject)null);
      var1.setlocal("_check_type", var8);
      var3 = null;
      var1.setline(665);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _check_choice$53, (PyObject)null);
      var1.setlocal("_check_choice", var8);
      var3 = null;
      var1.setline(678);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _check_dest$54, (PyObject)null);
      var1.setlocal("_check_dest", var8);
      var3 = null;
      var1.setline(693);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _check_const$55, (PyObject)null);
      var1.setlocal("_check_const", var8);
      var3 = null;
      var1.setline(699);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _check_nargs$56, (PyObject)null);
      var1.setlocal("_check_nargs", var8);
      var3 = null;
      var1.setline(708);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _check_callback$57, (PyObject)null);
      var1.setlocal("_check_callback", var8);
      var3 = null;
      var1.setline(736);
      var3 = new PyList(new PyObject[]{var1.getname("_check_action"), var1.getname("_check_type"), var1.getname("_check_choice"), var1.getname("_check_dest"), var1.getname("_check_const"), var1.getname("_check_nargs"), var1.getname("_check_callback")});
      var1.setlocal("CHECK_METHODS", var3);
      var3 = null;
      var1.setline(747);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, __str__$58, (PyObject)null);
      var1.setlocal("__str__", var8);
      var3 = null;
      var1.setline(750);
      var6 = var1.getname("_repr");
      var1.setlocal("__repr__", var6);
      var3 = null;
      var1.setline(752);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, takes_value$59, (PyObject)null);
      var1.setlocal("takes_value", var8);
      var3 = null;
      var1.setline(755);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_opt_string$60, (PyObject)null);
      var1.setlocal("get_opt_string", var8);
      var3 = null;
      var1.setline(764);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_value$61, (PyObject)null);
      var1.setlocal("check_value", var8);
      var3 = null;
      var1.setline(771);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, convert_value$62, (PyObject)null);
      var1.setlocal("convert_value", var8);
      var3 = null;
      var1.setline(778);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, process$63, (PyObject)null);
      var1.setlocal("process", var8);
      var3 = null;
      var1.setline(790);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, take_action$64, (PyObject)null);
      var1.setlocal("take_action", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$47(PyFrame var1, ThreadState var2) {
      var1.setline(563);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_short_opts", var3);
      var3 = null;
      var1.setline(564);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_long_opts", var3);
      var3 = null;
      var1.setline(565);
      PyObject var5 = var1.getlocal(0).__getattr__("_check_opt_strings").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(566);
      var1.getlocal(0).__getattr__("_set_opt_strings").__call__(var2, var1.getlocal(1));
      var1.setline(569);
      var1.getlocal(0).__getattr__("_set_attrs").__call__(var2, var1.getlocal(2));
      var1.setline(576);
      var5 = var1.getlocal(0).__getattr__("CHECK_METHODS").__iter__();

      while(true) {
         var1.setline(576);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(577);
         var1.getlocal(3).__call__(var2, var1.getlocal(0));
      }
   }

   public PyObject _check_opt_strings$48(PyFrame var1, ThreadState var2) {
      var1.setline(583);
      PyObject var3 = var1.getglobal("filter").__call__(var2, var1.getglobal("None"), var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(584);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(585);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("at least one option string must be supplied")));
      } else {
         var1.setline(586);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _set_opt_strings$49(PyFrame var1, ThreadState var2) {
      var1.setline(589);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(589);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(590);
         PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         PyObject var10000 = var5._lt(Py.newInteger(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(591);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("invalid option string %r: must be at least two characters long")._mod(var1.getlocal(2)), var1.getlocal(0)));
         }

         var1.setline(594);
         var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var5._eq(Py.newInteger(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(595);
            var5 = var1.getlocal(2).__getitem__(Py.newInteger(0));
            var10000 = var5._eq(PyString.fromInterned("-"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(2).__getitem__(Py.newInteger(1));
               var10000 = var5._ne(PyString.fromInterned("-"));
               var5 = null;
            }

            if (var10000.__not__().__nonzero__()) {
               var1.setline(596);
               throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("invalid short option string %r: must be of the form -x, (x any non-dash char)")._mod(var1.getlocal(2)), var1.getlocal(0)));
            }

            var1.setline(600);
            var1.getlocal(0).__getattr__("_short_opts").__getattr__("append").__call__(var2, var1.getlocal(2));
         } else {
            var1.setline(602);
            var5 = var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null);
            var10000 = var5._eq(PyString.fromInterned("--"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(2).__getitem__(Py.newInteger(2));
               var10000 = var5._ne(PyString.fromInterned("-"));
               var5 = null;
            }

            if (var10000.__not__().__nonzero__()) {
               var1.setline(603);
               throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("invalid long option string %r: must start with --, followed by non-dash")._mod(var1.getlocal(2)), var1.getlocal(0)));
            }

            var1.setline(607);
            var1.getlocal(0).__getattr__("_long_opts").__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject _set_attrs$50(PyFrame var1, ThreadState var2) {
      var1.setline(610);
      PyObject var3 = var1.getlocal(0).__getattr__("ATTRS").__iter__();

      while(true) {
         var1.setline(610);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(619);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(620);
               var3 = var1.getlocal(1).__getattr__("keys").__call__(var2);
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(621);
               var1.getlocal(1).__getattr__("sort").__call__(var2);
               var1.setline(622);
               throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("invalid keyword arguments: %s")._mod(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1))), var1.getlocal(0)));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(611);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._in(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(612);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(1).__getitem__(var1.getlocal(2)));
            var1.setline(613);
            var1.getlocal(1).__delitem__(var1.getlocal(2));
         } else {
            var1.setline(615);
            var5 = var1.getlocal(2);
            var10000 = var5._eq(PyString.fromInterned("default"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(616);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getglobal("NO_DEFAULT"));
            } else {
               var1.setline(618);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getglobal("None"));
            }
         }
      }
   }

   public PyObject _check_action$51(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyObject var3 = var1.getlocal(0).__getattr__("action");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(631);
         PyString var4 = PyString.fromInterned("store");
         var1.getlocal(0).__setattr__((String)"action", var4);
         var3 = null;
      } else {
         var1.setline(632);
         var3 = var1.getlocal(0).__getattr__("action");
         var10000 = var3._notin(var1.getlocal(0).__getattr__("ACTIONS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(633);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("invalid action: %r")._mod(var1.getlocal(0).__getattr__("action")), var1.getlocal(0)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check_type$52(PyFrame var1, ThreadState var2) {
      var1.setline(636);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(637);
         var3 = var1.getlocal(0).__getattr__("action");
         var10000 = var3._in(var1.getlocal(0).__getattr__("ALWAYS_TYPED_ACTIONS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(638);
            var3 = var1.getlocal(0).__getattr__("choices");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(640);
               var4 = PyString.fromInterned("choice");
               var1.getlocal(0).__setattr__((String)"type", var4);
               var3 = null;
            } else {
               var1.setline(643);
               var4 = PyString.fromInterned("string");
               var1.getlocal(0).__setattr__((String)"type", var4);
               var3 = null;
            }
         }
      } else {
         var1.setline(650);
         var3 = imp.importOne("__builtin__", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(651);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("type"));
         var10000 = var3._is(var1.getglobal("types").__getattr__("TypeType"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("type"), (PyObject)PyString.fromInterned("__name__"));
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("type").__getattr__("__name__"), var1.getglobal("None"));
               var10000 = var3._is(var1.getlocal(0).__getattr__("type"));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(654);
            var3 = var1.getlocal(0).__getattr__("type").__getattr__("__name__");
            var1.getlocal(0).__setattr__("type", var3);
            var3 = null;
         }

         var1.setline(656);
         var3 = var1.getlocal(0).__getattr__("type");
         var10000 = var3._eq(PyString.fromInterned("str"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(657);
            var4 = PyString.fromInterned("string");
            var1.getlocal(0).__setattr__((String)"type", var4);
            var3 = null;
         }

         var1.setline(659);
         var3 = var1.getlocal(0).__getattr__("type");
         var10000 = var3._notin(var1.getlocal(0).__getattr__("TYPES"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(660);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("invalid option type: %r")._mod(var1.getlocal(0).__getattr__("type")), var1.getlocal(0)));
         }

         var1.setline(661);
         var3 = var1.getlocal(0).__getattr__("action");
         var10000 = var3._notin(var1.getlocal(0).__getattr__("TYPED_ACTIONS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(662);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("must not supply a type for action %r")._mod(var1.getlocal(0).__getattr__("action")), var1.getlocal(0)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check_choice$53(PyFrame var1, ThreadState var2) {
      var1.setline(666);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._eq(PyString.fromInterned("choice"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(667);
         var3 = var1.getlocal(0).__getattr__("choices");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(668);
            throw Py.makeException(var1.getglobal("OptionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("must supply a list of choices for type 'choice'"), (PyObject)var1.getlocal(0)));
         }

         var1.setline(670);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("choices"));
         var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("types").__getattr__("TupleType"), var1.getglobal("types").__getattr__("ListType")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(671);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("choices must be a list of strings ('%s' supplied)")._mod(var1.getglobal("str").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("choices"))).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'")).__getitem__(Py.newInteger(1))), var1.getlocal(0)));
         }
      } else {
         var1.setline(674);
         var3 = var1.getlocal(0).__getattr__("choices");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(675);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("must not supply choices for type %r")._mod(var1.getlocal(0).__getattr__("type")), var1.getlocal(0)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check_dest$54(PyFrame var1, ThreadState var2) {
      var1.setline(681);
      PyObject var3 = var1.getlocal(0).__getattr__("action");
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("STORE_ACTIONS"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("type");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(683);
      var3 = var1.getlocal(0).__getattr__("dest");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
      }

      if (var10000.__nonzero__()) {
         var1.setline(687);
         if (var1.getlocal(0).__getattr__("_long_opts").__nonzero__()) {
            var1.setline(689);
            var3 = var1.getlocal(0).__getattr__("_long_opts").__getitem__(Py.newInteger(0)).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"));
            var1.getlocal(0).__setattr__("dest", var3);
            var3 = null;
         } else {
            var1.setline(691);
            var3 = var1.getlocal(0).__getattr__("_short_opts").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1));
            var1.getlocal(0).__setattr__("dest", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check_const$55(PyFrame var1, ThreadState var2) {
      var1.setline(694);
      PyObject var3 = var1.getlocal(0).__getattr__("action");
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("CONST_ACTIONS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("const");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(695);
         throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("'const' must not be supplied for action %r")._mod(var1.getlocal(0).__getattr__("action")), var1.getlocal(0)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _check_nargs$56(PyFrame var1, ThreadState var2) {
      var1.setline(700);
      PyObject var3 = var1.getlocal(0).__getattr__("action");
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("TYPED_ACTIONS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(701);
         var3 = var1.getlocal(0).__getattr__("nargs");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(702);
            PyInteger var4 = Py.newInteger(1);
            var1.getlocal(0).__setattr__((String)"nargs", var4);
            var3 = null;
         }
      } else {
         var1.setline(703);
         var3 = var1.getlocal(0).__getattr__("nargs");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(704);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("'nargs' must not be supplied for action %r")._mod(var1.getlocal(0).__getattr__("action")), var1.getlocal(0)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check_callback$57(PyFrame var1, ThreadState var2) {
      var1.setline(709);
      PyObject var3 = var1.getlocal(0).__getattr__("action");
      PyObject var10000 = var3._eq(PyString.fromInterned("callback"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(710);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("callback"), (PyObject)PyString.fromInterned("__call__")).__not__().__nonzero__()) {
            var1.setline(711);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("callback not callable: %r")._mod(var1.getlocal(0).__getattr__("callback")), var1.getlocal(0)));
         }

         var1.setline(713);
         var3 = var1.getlocal(0).__getattr__("callback_args");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("callback_args"));
            var10000 = var3._isnot(var1.getglobal("types").__getattr__("TupleType"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(715);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("callback_args, if supplied, must be a tuple: not %r")._mod(var1.getlocal(0).__getattr__("callback_args")), var1.getlocal(0)));
         }

         var1.setline(718);
         var3 = var1.getlocal(0).__getattr__("callback_kwargs");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("callback_kwargs"));
            var10000 = var3._isnot(var1.getglobal("types").__getattr__("DictType"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(720);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("callback_kwargs, if supplied, must be a dict: not %r")._mod(var1.getlocal(0).__getattr__("callback_kwargs")), var1.getlocal(0)));
         }
      } else {
         var1.setline(724);
         var3 = var1.getlocal(0).__getattr__("callback");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(725);
            throw Py.makeException(var1.getglobal("OptionError").__call__(var2, PyString.fromInterned("callback supplied (%r) for non-callback option")._mod(var1.getlocal(0).__getattr__("callback")), var1.getlocal(0)));
         }

         var1.setline(728);
         var3 = var1.getlocal(0).__getattr__("callback_args");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(729);
            throw Py.makeException(var1.getglobal("OptionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("callback_args supplied for non-callback option"), (PyObject)var1.getlocal(0)));
         }

         var1.setline(731);
         var3 = var1.getlocal(0).__getattr__("callback_kwargs");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(732);
            throw Py.makeException(var1.getglobal("OptionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("callback_kwargs supplied for non-callback option"), (PyObject)var1.getlocal(0)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$58(PyFrame var1, ThreadState var2) {
      var1.setline(748);
      PyObject var3 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_short_opts")._add(var1.getlocal(0).__getattr__("_long_opts")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject takes_value$59(PyFrame var1, ThreadState var2) {
      var1.setline(753);
      PyObject var3 = var1.getlocal(0).__getattr__("type");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_opt_string$60(PyFrame var1, ThreadState var2) {
      var1.setline(756);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_long_opts").__nonzero__()) {
         var1.setline(757);
         var3 = var1.getlocal(0).__getattr__("_long_opts").__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(759);
         var3 = var1.getlocal(0).__getattr__("_short_opts").__getitem__(Py.newInteger(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject check_value$61(PyFrame var1, ThreadState var2) {
      var1.setline(765);
      PyObject var3 = var1.getlocal(0).__getattr__("TYPE_CHECKER").__getattr__("get").__call__(var2, var1.getlocal(0).__getattr__("type"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(766);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(767);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(769);
         var3 = var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject convert_value$62(PyFrame var1, ThreadState var2) {
      var1.setline(772);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(773);
         var3 = var1.getlocal(0).__getattr__("nargs");
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(774);
            var3 = var1.getlocal(0).__getattr__("check_value").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(776);
            var10000 = var1.getglobal("tuple");
            PyList var10002 = new PyList();
            PyObject var4 = var10002.__getattr__("append");
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(776);
            var4 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(776);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(776);
                  var1.dellocal(3);
                  var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var5);
               var1.setline(776);
               var1.getlocal(3).__call__(var2, var1.getlocal(0).__getattr__("check_value").__call__(var2, var1.getlocal(1), var1.getlocal(4)));
            }
         }
      }
   }

   public PyObject process$63(PyFrame var1, ThreadState var2) {
      var1.setline(782);
      PyObject var3 = var1.getlocal(0).__getattr__("convert_value").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(787);
      PyObject var10000 = var1.getlocal(0).__getattr__("take_action");
      PyObject[] var4 = new PyObject[]{var1.getlocal(0).__getattr__("action"), var1.getlocal(0).__getattr__("dest"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      var3 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject take_action$64(PyFrame var1, ThreadState var2) {
      var1.setline(791);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("store"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(792);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(4));
      } else {
         var1.setline(793);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("store_const"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(794);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(0).__getattr__("const"));
         } else {
            var1.setline(795);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(PyString.fromInterned("store_true"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(796);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getglobal("True"));
            } else {
               var1.setline(797);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(PyString.fromInterned("store_false"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(798);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getglobal("False"));
               } else {
                  var1.setline(799);
                  var3 = var1.getlocal(1);
                  var10000 = var3._eq(PyString.fromInterned("append"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(800);
                     var1.getlocal(5).__getattr__("ensure_value").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(Py.EmptyObjects))).__getattr__("append").__call__(var2, var1.getlocal(4));
                  } else {
                     var1.setline(801);
                     var3 = var1.getlocal(1);
                     var10000 = var3._eq(PyString.fromInterned("append_const"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(802);
                        var1.getlocal(5).__getattr__("ensure_value").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(Py.EmptyObjects))).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("const"));
                     } else {
                        var1.setline(803);
                        var3 = var1.getlocal(1);
                        var10000 = var3._eq(PyString.fromInterned("count"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(804);
                           var1.getglobal("setattr").__call__(var2, var1.getlocal(5), var1.getlocal(2), var1.getlocal(5).__getattr__("ensure_value").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(0))._add(Py.newInteger(1)));
                        } else {
                           var1.setline(805);
                           var3 = var1.getlocal(1);
                           var10000 = var3._eq(PyString.fromInterned("callback"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(806);
                              Object var8 = var1.getlocal(0).__getattr__("callback_args");
                              if (!((PyObject)var8).__nonzero__()) {
                                 var8 = new PyTuple(Py.EmptyObjects);
                              }

                              Object var5 = var8;
                              var1.setlocal(7, (PyObject)var5);
                              var3 = null;
                              var1.setline(807);
                              var8 = var1.getlocal(0).__getattr__("callback_kwargs");
                              if (!((PyObject)var8).__nonzero__()) {
                                 var8 = new PyDictionary(Py.EmptyObjects);
                              }

                              var5 = var8;
                              var1.setlocal(8, (PyObject)var5);
                              var3 = null;
                              var1.setline(808);
                              var10000 = var1.getlocal(0).__getattr__("callback");
                              PyObject[] var6 = new PyObject[]{var1.getlocal(0), var1.getlocal(3), var1.getlocal(4), var1.getlocal(6)};
                              String[] var4 = new String[0];
                              var10000._callextra(var6, var4, var1.getlocal(7), var1.getlocal(8));
                              var3 = null;
                           } else {
                              var1.setline(809);
                              var3 = var1.getlocal(1);
                              var10000 = var3._eq(PyString.fromInterned("help"));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(810);
                                 var1.getlocal(6).__getattr__("print_help").__call__(var2);
                                 var1.setline(811);
                                 var1.getlocal(6).__getattr__("exit").__call__(var2);
                              } else {
                                 var1.setline(812);
                                 var3 = var1.getlocal(1);
                                 var10000 = var3._eq(PyString.fromInterned("version"));
                                 var3 = null;
                                 if (!var10000.__nonzero__()) {
                                    var1.setline(816);
                                    throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unknown action %r")._mod(var1.getlocal(0).__getattr__("action"))));
                                 }

                                 var1.setline(813);
                                 var1.getlocal(6).__getattr__("print_version").__call__(var2);
                                 var1.setline(814);
                                 var1.getlocal(6).__getattr__("exit").__call__(var2);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      var1.setline(818);
      PyInteger var7 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject isbasestring$65(PyFrame var1, ThreadState var2) {
      var1.setline(830);
      PyObject var3 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("types").__getattr__("StringType"), var1.getglobal("types").__getattr__("UnicodeType")})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isbasestring$66(PyFrame var1, ThreadState var2) {
      var1.setline(833);
      PyObject var3 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Values$67(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(837);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$68, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(842);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$69, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(845);
      PyObject var5 = var1.getname("_repr");
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(847);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __cmp__$70, (PyObject)null);
      var1.setlocal("__cmp__", var4);
      var3 = null;
      var1.setline(855);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _update_careful$71, PyString.fromInterned("\n        Update the option values from an arbitrary dictionary, but only\n        use keys from dict that already have a corresponding attribute\n        in self.  Any keys in dict without a corresponding attribute\n        are silently ignored.\n        "));
      var1.setlocal("_update_careful", var4);
      var3 = null;
      var1.setline(868);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _update_loose$72, PyString.fromInterned("\n        Update the option values from an arbitrary dictionary,\n        using all keys from the dictionary regardless of whether\n        they have a corresponding attribute in self or not.\n        "));
      var1.setlocal("_update_loose", var4);
      var3 = null;
      var1.setline(876);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _update$73, (PyObject)null);
      var1.setlocal("_update", var4);
      var3 = null;
      var1.setline(884);
      var3 = new PyObject[]{PyString.fromInterned("careful")};
      var4 = new PyFunction(var1.f_globals, var3, read_module$74, (PyObject)null);
      var1.setlocal("read_module", var4);
      var3 = null;
      var1.setline(889);
      var3 = new PyObject[]{PyString.fromInterned("careful")};
      var4 = new PyFunction(var1.f_globals, var3, read_file$75, (PyObject)null);
      var1.setlocal("read_file", var4);
      var3 = null;
      var1.setline(894);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ensure_value$76, (PyObject)null);
      var1.setlocal("ensure_value", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$68(PyFrame var1, ThreadState var2) {
      var1.setline(838);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(839);
         PyObject var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(839);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(840);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(3));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$69(PyFrame var1, ThreadState var2) {
      var1.setline(843);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("__dict__"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __cmp__$70(PyFrame var1, ThreadState var2) {
      var1.setline(848);
      PyObject var4;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Values")).__nonzero__()) {
         var1.setline(849);
         var4 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("__dict__"), var1.getlocal(1).__getattr__("__dict__"));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(850);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("types").__getattr__("DictType")).__nonzero__()) {
            var1.setline(851);
            var4 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getattr__("__dict__"), var1.getlocal(1));
            var1.f_lasti = -1;
            return var4;
         } else {
            var1.setline(853);
            PyInteger var3 = Py.newInteger(-1);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _update_careful$71(PyFrame var1, ThreadState var2) {
      var1.setline(861);
      PyString.fromInterned("\n        Update the option values from an arbitrary dictionary, but only\n        use keys from dict that already have a corresponding attribute\n        in self.  Any keys in dict without a corresponding attribute\n        are silently ignored.\n        ");
      var1.setline(862);
      PyObject var3 = var1.getglobal("dir").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(862);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(863);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._in(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(864);
            var5 = var1.getlocal(1).__getitem__(var1.getlocal(2));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(865);
            var5 = var1.getlocal(3);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(866);
               var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(3));
            }
         }
      }
   }

   public PyObject _update_loose$72(PyFrame var1, ThreadState var2) {
      var1.setline(873);
      PyString.fromInterned("\n        Update the option values from an arbitrary dictionary,\n        using all keys from the dictionary regardless of whether\n        they have a corresponding attribute in self or not.\n        ");
      var1.setline(874);
      var1.getlocal(0).__getattr__("__dict__").__getattr__("update").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _update$73(PyFrame var1, ThreadState var2) {
      var1.setline(877);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("careful"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(878);
         var1.getlocal(0).__getattr__("_update_careful").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(879);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(PyString.fromInterned("loose"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(882);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("invalid update mode: %r")._mod(var1.getlocal(2)));
         }

         var1.setline(880);
         var1.getlocal(0).__getattr__("_update_loose").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_module$74(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      var1.getglobal("__import__").__call__(var2, var1.getlocal(1));
      var1.setline(886);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(887);
      var1.getlocal(0).__getattr__("_update").__call__(var2, var1.getglobal("vars").__call__(var2, var1.getlocal(3)), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_file$75(PyFrame var1, ThreadState var2) {
      var1.setline(890);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(891);
      var1.getglobal("execfile").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.setline(892);
      var1.getlocal(0).__getattr__("_update").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ensure_value$76(PyFrame var1, ThreadState var2) {
      var1.setline(895);
      PyObject var10000 = var1.getglobal("hasattr").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__not__();
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(896);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      }

      var1.setline(897);
      var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OptionContainer$77(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Abstract base class.\n\n    Class attributes:\n      standard_option_list : [Option]\n        list of standard options that will be accepted by all instances\n        of this parser class (intended to be overridden by subclasses).\n\n    Instance attributes:\n      option_list : [Option]\n        the list of Option objects contained by this OptionContainer\n      _short_opt : { string : Option }\n        dictionary mapping short option strings, eg. \"-f\" or \"-X\",\n        to the Option instances that implement them.  If an Option\n        has multiple short option strings, it will appears in this\n        dictionary multiple times. [1]\n      _long_opt : { string : Option }\n        dictionary mapping long option strings, eg. \"--file\" or\n        \"--exclude\", to the Option instances that implement them.\n        Again, a given Option can occur multiple times in this\n        dictionary. [1]\n      defaults : { string : any }\n        dictionary mapping option destination names to default\n        values for each destination [1]\n\n    [1] These mappings are common to (shared by) all components of the\n        controlling OptionParser, where they are initially created.\n\n    "));
      var1.setline(930);
      PyString.fromInterned("\n    Abstract base class.\n\n    Class attributes:\n      standard_option_list : [Option]\n        list of standard options that will be accepted by all instances\n        of this parser class (intended to be overridden by subclasses).\n\n    Instance attributes:\n      option_list : [Option]\n        the list of Option objects contained by this OptionContainer\n      _short_opt : { string : Option }\n        dictionary mapping short option strings, eg. \"-f\" or \"-X\",\n        to the Option instances that implement them.  If an Option\n        has multiple short option strings, it will appears in this\n        dictionary multiple times. [1]\n      _long_opt : { string : Option }\n        dictionary mapping long option strings, eg. \"--file\" or\n        \"--exclude\", to the Option instances that implement them.\n        Again, a given Option can occur multiple times in this\n        dictionary. [1]\n      defaults : { string : any }\n        dictionary mapping option destination names to default\n        values for each destination [1]\n\n    [1] These mappings are common to (shared by) all components of the\n        controlling OptionParser, where they are initially created.\n\n    ");
      var1.setline(932);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$78, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(943);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _create_option_mappings$79, (PyObject)null);
      var1.setlocal("_create_option_mappings", var4);
      var3 = null;
      var1.setline(952);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _share_option_mappings$80, (PyObject)null);
      var1.setlocal("_share_option_mappings", var4);
      var3 = null;
      var1.setline(959);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_conflict_handler$81, (PyObject)null);
      var1.setlocal("set_conflict_handler", var4);
      var3 = null;
      var1.setline(964);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_description$82, (PyObject)null);
      var1.setlocal("set_description", var4);
      var3 = null;
      var1.setline(967);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_description$83, (PyObject)null);
      var1.setlocal("get_description", var4);
      var3 = null;
      var1.setline(971);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, destroy$84, PyString.fromInterned("see OptionParser.destroy()."));
      var1.setlocal("destroy", var4);
      var3 = null;
      var1.setline(980);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _check_conflict$85, (PyObject)null);
      var1.setlocal("_check_conflict", var4);
      var3 = null;
      var1.setline(1007);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_option$86, PyString.fromInterned("add_option(Option)\n           add_option(opt_str, ..., kwarg=val, ...)\n        "));
      var1.setlocal("add_option", var4);
      var3 = null;
      var1.setline(1037);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_options$87, (PyObject)null);
      var1.setlocal("add_options", var4);
      var3 = null;
      var1.setline(1043);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_option$88, (PyObject)null);
      var1.setlocal("get_option", var4);
      var3 = null;
      var1.setline(1047);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_option$89, (PyObject)null);
      var1.setlocal("has_option", var4);
      var3 = null;
      var1.setline(1051);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove_option$90, (PyObject)null);
      var1.setlocal("remove_option", var4);
      var3 = null;
      var1.setline(1067);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_option_help$91, (PyObject)null);
      var1.setlocal("format_option_help", var4);
      var3 = null;
      var1.setline(1076);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_description$92, (PyObject)null);
      var1.setlocal("format_description", var4);
      var3 = null;
      var1.setline(1079);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_help$93, (PyObject)null);
      var1.setlocal("format_help", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$78(PyFrame var1, ThreadState var2) {
      var1.setline(937);
      var1.getlocal(0).__getattr__("_create_option_list").__call__(var2);
      var1.setline(939);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("option_class", var3);
      var3 = null;
      var1.setline(940);
      var1.getlocal(0).__getattr__("set_conflict_handler").__call__(var2, var1.getlocal(2));
      var1.setline(941);
      var1.getlocal(0).__getattr__("set_description").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _create_option_mappings$79(PyFrame var1, ThreadState var2) {
      var1.setline(947);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_short_opt", var3);
      var3 = null;
      var1.setline(948);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_long_opt", var3);
      var3 = null;
      var1.setline(949);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"defaults", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _share_option_mappings$80(PyFrame var1, ThreadState var2) {
      var1.setline(955);
      PyObject var3 = var1.getlocal(1).__getattr__("_short_opt");
      var1.getlocal(0).__setattr__("_short_opt", var3);
      var3 = null;
      var1.setline(956);
      var3 = var1.getlocal(1).__getattr__("_long_opt");
      var1.getlocal(0).__setattr__("_long_opt", var3);
      var3 = null;
      var1.setline(957);
      var3 = var1.getlocal(1).__getattr__("defaults");
      var1.getlocal(0).__setattr__("defaults", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_conflict_handler$81(PyFrame var1, ThreadState var2) {
      var1.setline(960);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("error"), PyString.fromInterned("resolve")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(961);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("invalid conflict_resolution value %r")._mod(var1.getlocal(1)));
      } else {
         var1.setline(962);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("conflict_handler", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject set_description$82(PyFrame var1, ThreadState var2) {
      var1.setline(965);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("description", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_description$83(PyFrame var1, ThreadState var2) {
      var1.setline(968);
      PyObject var3 = var1.getlocal(0).__getattr__("description");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject destroy$84(PyFrame var1, ThreadState var2) {
      var1.setline(972);
      PyString.fromInterned("see OptionParser.destroy().");
      var1.setline(973);
      var1.getlocal(0).__delattr__("_short_opt");
      var1.setline(974);
      var1.getlocal(0).__delattr__("_long_opt");
      var1.setline(975);
      var1.getlocal(0).__delattr__("defaults");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check_conflict$85(PyFrame var1, ThreadState var2) {
      var1.setline(981);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(982);
      PyObject var7 = var1.getlocal(1).__getattr__("_short_opts").__iter__();

      while(true) {
         var1.setline(982);
         PyObject var4 = var7.__iternext__();
         PyObject var10000;
         PyObject var5;
         if (var4 == null) {
            var1.setline(985);
            var7 = var1.getlocal(1).__getattr__("_long_opts").__iter__();

            while(true) {
               var1.setline(985);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(989);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(990);
                     var7 = var1.getlocal(0).__getattr__("conflict_handler");
                     var1.setlocal(4, var7);
                     var3 = null;
                     var1.setline(991);
                     var7 = var1.getlocal(4);
                     var10000 = var7._eq(PyString.fromInterned("error"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(992);
                        var10000 = var1.getglobal("OptionConflictError");
                        PyString var10002 = PyString.fromInterned("conflicting option string(s): %s");
                        PyObject var10003 = PyString.fromInterned(", ").__getattr__("join");
                        PyList var10005 = new PyList();
                        var7 = var10005.__getattr__("append");
                        var1.setlocal(5, var7);
                        var3 = null;
                        var1.setline(994);
                        var7 = var1.getlocal(2).__iter__();

                        while(true) {
                           var1.setline(994);
                           var4 = var7.__iternext__();
                           if (var4 == null) {
                              var1.setline(994);
                              var1.dellocal(5);
                              throw Py.makeException(var10000.__call__(var2, var10002._mod(var10003.__call__((ThreadState)var2, (PyObject)var10005)), var1.getlocal(1)));
                           }

                           var1.setlocal(6, var4);
                           var1.setline(994);
                           var1.getlocal(5).__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0)));
                        }
                     }

                     var1.setline(996);
                     var7 = var1.getlocal(4);
                     var10000 = var7._eq(PyString.fromInterned("resolve"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(997);
                        var7 = var1.getlocal(2).__iter__();

                        while(true) {
                           var1.setline(997);
                           var4 = var7.__iternext__();
                           if (var4 == null) {
                              break;
                           }

                           PyObject[] var8 = Py.unpackSequence(var4, 2);
                           PyObject var6 = var8[0];
                           var1.setlocal(3, var6);
                           var6 = null;
                           var6 = var8[1];
                           var1.setlocal(7, var6);
                           var6 = null;
                           var1.setline(998);
                           if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--")).__nonzero__()) {
                              var1.setline(999);
                              var1.getlocal(7).__getattr__("_long_opts").__getattr__("remove").__call__(var2, var1.getlocal(3));
                              var1.setline(1000);
                              var1.getlocal(0).__getattr__("_long_opt").__delitem__(var1.getlocal(3));
                           } else {
                              var1.setline(1002);
                              var1.getlocal(7).__getattr__("_short_opts").__getattr__("remove").__call__(var2, var1.getlocal(3));
                              var1.setline(1003);
                              var1.getlocal(0).__getattr__("_short_opt").__delitem__(var1.getlocal(3));
                           }

                           var1.setline(1004);
                           var10000 = var1.getlocal(7).__getattr__("_short_opts");
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(7).__getattr__("_long_opts");
                           }

                           if (var10000.__not__().__nonzero__()) {
                              var1.setline(1005);
                              var1.getlocal(7).__getattr__("container").__getattr__("option_list").__getattr__("remove").__call__(var2, var1.getlocal(7));
                           }
                        }
                     }
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);
               var1.setline(986);
               var5 = var1.getlocal(3);
               var10000 = var5._in(var1.getlocal(0).__getattr__("_long_opt"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(987);
                  var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getattr__("_long_opt").__getitem__(var1.getlocal(3))})));
               }
            }
         }

         var1.setlocal(3, var4);
         var1.setline(983);
         var5 = var1.getlocal(3);
         var10000 = var5._in(var1.getlocal(0).__getattr__("_short_opt"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(984);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getattr__("_short_opt").__getitem__(var1.getlocal(3))})));
         }
      }
   }

   public PyObject add_option$86(PyFrame var1, ThreadState var2) {
      var1.setline(1010);
      PyString.fromInterned("add_option(Option)\n           add_option(opt_str, ..., kwarg=val, ...)\n        ");
      var1.setline(1011);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      PyObject var10000 = var3._in(var1.getglobal("types").__getattr__("StringTypes"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1012);
         var10000 = var1.getlocal(0).__getattr__("option_class");
         PyObject[] var6 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var6, var4, var1.getlocal(1), var1.getlocal(2));
         var3 = null;
         var3 = var10000;
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(1013);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__not__();
         }

         if (!var10000.__nonzero__()) {
            var1.setline(1018);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("invalid arguments"));
         }

         var1.setline(1014);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1015);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("Option")).__not__().__nonzero__()) {
            var1.setline(1016);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("not an Option instance: %r")._mod(var1.getlocal(3)));
         }
      }

      var1.setline(1020);
      var1.getlocal(0).__getattr__("_check_conflict").__call__(var2, var1.getlocal(3));
      var1.setline(1022);
      var1.getlocal(0).__getattr__("option_list").__getattr__("append").__call__(var2, var1.getlocal(3));
      var1.setline(1023);
      var3 = var1.getlocal(0);
      var1.getlocal(3).__setattr__("container", var3);
      var3 = null;
      var1.setline(1024);
      var3 = var1.getlocal(3).__getattr__("_short_opts").__iter__();

      while(true) {
         var1.setline(1024);
         PyObject var7 = var3.__iternext__();
         PyObject var5;
         if (var7 == null) {
            var1.setline(1026);
            var3 = var1.getlocal(3).__getattr__("_long_opts").__iter__();

            while(true) {
               var1.setline(1026);
               var7 = var3.__iternext__();
               if (var7 == null) {
                  var1.setline(1029);
                  var3 = var1.getlocal(3).__getattr__("dest");
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1030);
                     var3 = var1.getlocal(3).__getattr__("default");
                     var10000 = var3._isnot(var1.getglobal("NO_DEFAULT"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1031);
                        var3 = var1.getlocal(3).__getattr__("default");
                        var1.getlocal(0).__getattr__("defaults").__setitem__(var1.getlocal(3).__getattr__("dest"), var3);
                        var3 = null;
                     } else {
                        var1.setline(1032);
                        var3 = var1.getlocal(3).__getattr__("dest");
                        var10000 = var3._notin(var1.getlocal(0).__getattr__("defaults"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1033);
                           var3 = var1.getglobal("None");
                           var1.getlocal(0).__getattr__("defaults").__setitem__(var1.getlocal(3).__getattr__("dest"), var3);
                           var3 = null;
                        }
                     }
                  }

                  var1.setline(1035);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var7);
               var1.setline(1027);
               var5 = var1.getlocal(3);
               var1.getlocal(0).__getattr__("_long_opt").__setitem__(var1.getlocal(4), var5);
               var5 = null;
            }
         }

         var1.setlocal(4, var7);
         var1.setline(1025);
         var5 = var1.getlocal(3);
         var1.getlocal(0).__getattr__("_short_opt").__setitem__(var1.getlocal(4), var5);
         var5 = null;
      }
   }

   public PyObject add_options$87(PyFrame var1, ThreadState var2) {
      var1.setline(1038);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1038);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1039);
         var1.getlocal(0).__getattr__("add_option").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject get_option$88(PyFrame var1, ThreadState var2) {
      var1.setline(1044);
      PyObject var10000 = var1.getlocal(0).__getattr__("_short_opt").__getattr__("get").__call__(var2, var1.getlocal(1));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_long_opt").__getattr__("get").__call__(var2, var1.getlocal(1));
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_option$89(PyFrame var1, ThreadState var2) {
      var1.setline(1048);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_short_opt"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._in(var1.getlocal(0).__getattr__("_long_opt"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove_option$90(PyFrame var1, ThreadState var2) {
      var1.setline(1052);
      PyObject var3 = var1.getlocal(0).__getattr__("_short_opt").__getattr__("get").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1053);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1054);
         var3 = var1.getlocal(0).__getattr__("_long_opt").__getattr__("get").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1055);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1056);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("no such option %r")._mod(var1.getlocal(1))));
      } else {
         var1.setline(1058);
         var3 = var1.getlocal(2).__getattr__("_short_opts").__iter__();

         while(true) {
            var1.setline(1058);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1060);
               var3 = var1.getlocal(2).__getattr__("_long_opts").__iter__();

               while(true) {
                  var1.setline(1060);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(1062);
                     var1.getlocal(2).__getattr__("container").__getattr__("option_list").__getattr__("remove").__call__(var2, var1.getlocal(2));
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  var1.setlocal(3, var4);
                  var1.setline(1061);
                  var1.getlocal(0).__getattr__("_long_opt").__delitem__(var1.getlocal(3));
               }
            }

            var1.setlocal(3, var4);
            var1.setline(1059);
            var1.getlocal(0).__getattr__("_short_opt").__delitem__(var1.getlocal(3));
         }
      }
   }

   public PyObject format_option_help$91(PyFrame var1, ThreadState var2) {
      var1.setline(1068);
      if (var1.getlocal(0).__getattr__("option_list").__not__().__nonzero__()) {
         var1.setline(1069);
         PyString var7 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(1070);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1071);
         PyObject var8 = var1.getlocal(0).__getattr__("option_list").__iter__();

         while(true) {
            var1.setline(1071);
            PyObject var5 = var8.__iternext__();
            if (var5 == null) {
               var1.setline(1074);
               PyObject var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var5);
            var1.setline(1072);
            PyObject var6 = var1.getlocal(3).__getattr__("help");
            PyObject var10000 = var6._is(var1.getglobal("SUPPRESS_HELP"));
            var6 = null;
            if (var10000.__not__().__nonzero__()) {
               var1.setline(1073);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("format_option").__call__(var2, var1.getlocal(3)));
            }
         }
      }
   }

   public PyObject format_description$92(PyFrame var1, ThreadState var2) {
      var1.setline(1077);
      PyObject var3 = var1.getlocal(1).__getattr__("format_description").__call__(var2, var1.getlocal(0).__getattr__("get_description").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format_help$93(PyFrame var1, ThreadState var2) {
      var1.setline(1080);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1081);
      if (var1.getlocal(0).__getattr__("description").__nonzero__()) {
         var1.setline(1082);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("format_description").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(1083);
      if (var1.getlocal(0).__getattr__("option_list").__nonzero__()) {
         var1.setline(1084);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("format_option_help").__call__(var2, var1.getlocal(1)));
      }

      var1.setline(1085);
      PyObject var4 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject OptionGroup$94(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1090);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$95, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1096);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _create_option_list$96, (PyObject)null);
      var1.setlocal("_create_option_list", var4);
      var3 = null;
      var1.setline(1100);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_title$97, (PyObject)null);
      var1.setlocal("set_title", var4);
      var3 = null;
      var1.setline(1103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, destroy$98, PyString.fromInterned("see OptionParser.destroy()."));
      var1.setlocal("destroy", var4);
      var3 = null;
      var1.setline(1110);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_help$99, (PyObject)null);
      var1.setlocal("format_help", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$95(PyFrame var1, ThreadState var2) {
      var1.setline(1091);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("parser", var3);
      var3 = null;
      var1.setline(1092);
      var1.getglobal("OptionContainer").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1).__getattr__("option_class"), var1.getlocal(1).__getattr__("conflict_handler"), var1.getlocal(3));
      var1.setline(1094);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("title", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _create_option_list$96(PyFrame var1, ThreadState var2) {
      var1.setline(1097);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"option_list", var3);
      var3 = null;
      var1.setline(1098);
      var1.getlocal(0).__getattr__("_share_option_mappings").__call__(var2, var1.getlocal(0).__getattr__("parser"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_title$97(PyFrame var1, ThreadState var2) {
      var1.setline(1101);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("title", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject destroy$98(PyFrame var1, ThreadState var2) {
      var1.setline(1104);
      PyString.fromInterned("see OptionParser.destroy().");
      var1.setline(1105);
      var1.getglobal("OptionContainer").__getattr__("destroy").__call__(var2, var1.getlocal(0));
      var1.setline(1106);
      var1.getlocal(0).__delattr__("option_list");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_help$99(PyFrame var1, ThreadState var2) {
      var1.setline(1111);
      PyObject var3 = var1.getlocal(1).__getattr__("format_heading").__call__(var2, var1.getlocal(0).__getattr__("title"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1112);
      var1.getlocal(1).__getattr__("indent").__call__(var2);
      var1.setline(1113);
      var3 = var1.getlocal(2);
      var3 = var3._iadd(var1.getglobal("OptionContainer").__getattr__("format_help").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
      var1.setlocal(2, var3);
      var1.setline(1114);
      var1.getlocal(1).__getattr__("dedent").__call__(var2);
      var1.setline(1115);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject OptionParser$100(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Class attributes:\n      standard_option_list : [Option]\n        list of standard options that will be accepted by all instances\n        of this parser class (intended to be overridden by subclasses).\n\n    Instance attributes:\n      usage : string\n        a usage string for your program.  Before it is displayed\n        to the user, \"%prog\" will be expanded to the name of\n        your program (self.prog or os.path.basename(sys.argv[0])).\n      prog : string\n        the name of the current program (to override\n        os.path.basename(sys.argv[0])).\n      description : string\n        A paragraph of text giving a brief overview of your program.\n        optparse reformats this paragraph to fit the current terminal\n        width and prints it when the user requests help (after usage,\n        but before the list of options).\n      epilog : string\n        paragraph of help text to print after option help\n\n      option_groups : [OptionGroup]\n        list of option groups in this parser (option groups are\n        irrelevant for parsing the command-line, but very useful\n        for generating help)\n\n      allow_interspersed_args : bool = true\n        if true, positional arguments may be interspersed with options.\n        Assuming -a and -b each take a single argument, the command-line\n          -ablah foo bar -bboo baz\n        will be interpreted the same as\n          -ablah -bboo -- foo bar baz\n        If this flag were false, that command line would be interpreted as\n          -ablah -- foo bar -bboo baz\n        -- ie. we stop processing options as soon as we see the first\n        non-option argument.  (This is the tradition followed by\n        Python's getopt module, Perl's Getopt::Std, and other argument-\n        parsing libraries, but it is generally annoying to users.)\n\n      process_default_values : bool = true\n        if true, option default values are processed similarly to option\n        values from the command line: that is, they are passed to the\n        type-checking function for the option's type (as long as the\n        default value is a string).  (This really only matters if you\n        have defined custom types; see SF bug #955889.)  Set it to false\n        to restore the behaviour of Optik 1.4.1 and earlier.\n\n      rargs : [string]\n        the argument list currently being parsed.  Only set when\n        parse_args() is active, and continually trimmed down as\n        we consume arguments.  Mainly there for the benefit of\n        callback options.\n      largs : [string]\n        the list of leftover arguments that we have skipped while\n        parsing options.  If allow_interspersed_args is false, this\n        list is always empty.\n      values : Values\n        the set of option values currently being accumulated.  Only\n        set when parse_args() is active.  Also mainly for callbacks.\n\n    Because of the 'rargs', 'largs', and 'values' attributes,\n    OptionParser is not thread-safe.  If, for some perverse reason, you\n    need to parse command-line arguments simultaneously in different\n    threads, use different OptionParser instances.\n\n    "));
      var1.setline(1186);
      PyString.fromInterned("\n    Class attributes:\n      standard_option_list : [Option]\n        list of standard options that will be accepted by all instances\n        of this parser class (intended to be overridden by subclasses).\n\n    Instance attributes:\n      usage : string\n        a usage string for your program.  Before it is displayed\n        to the user, \"%prog\" will be expanded to the name of\n        your program (self.prog or os.path.basename(sys.argv[0])).\n      prog : string\n        the name of the current program (to override\n        os.path.basename(sys.argv[0])).\n      description : string\n        A paragraph of text giving a brief overview of your program.\n        optparse reformats this paragraph to fit the current terminal\n        width and prints it when the user requests help (after usage,\n        but before the list of options).\n      epilog : string\n        paragraph of help text to print after option help\n\n      option_groups : [OptionGroup]\n        list of option groups in this parser (option groups are\n        irrelevant for parsing the command-line, but very useful\n        for generating help)\n\n      allow_interspersed_args : bool = true\n        if true, positional arguments may be interspersed with options.\n        Assuming -a and -b each take a single argument, the command-line\n          -ablah foo bar -bboo baz\n        will be interpreted the same as\n          -ablah -bboo -- foo bar baz\n        If this flag were false, that command line would be interpreted as\n          -ablah -- foo bar -bboo baz\n        -- ie. we stop processing options as soon as we see the first\n        non-option argument.  (This is the tradition followed by\n        Python's getopt module, Perl's Getopt::Std, and other argument-\n        parsing libraries, but it is generally annoying to users.)\n\n      process_default_values : bool = true\n        if true, option default values are processed similarly to option\n        values from the command line: that is, they are passed to the\n        type-checking function for the option's type (as long as the\n        default value is a string).  (This really only matters if you\n        have defined custom types; see SF bug #955889.)  Set it to false\n        to restore the behaviour of Optik 1.4.1 and earlier.\n\n      rargs : [string]\n        the argument list currently being parsed.  Only set when\n        parse_args() is active, and continually trimmed down as\n        we consume arguments.  Mainly there for the benefit of\n        callback options.\n      largs : [string]\n        the list of leftover arguments that we have skipped while\n        parsing options.  If allow_interspersed_args is false, this\n        list is always empty.\n      values : Values\n        the set of option values currently being accumulated.  Only\n        set when parse_args() is active.  Also mainly for callbacks.\n\n    Because of the 'rargs', 'largs', and 'values' attributes,\n    OptionParser is not thread-safe.  If, for some perverse reason, you\n    need to parse command-line arguments simultaneously in different\n    threads, use different OptionParser instances.\n\n    ");
      var1.setline(1188);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("standard_option_list", var3);
      var3 = null;
      var1.setline(1190);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("Option"), var1.getname("None"), PyString.fromInterned("error"), var1.getname("None"), var1.getname("None"), var1.getname("True"), var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$101, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1224);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, destroy$102, PyString.fromInterned("\n        Declare that you are done with this OptionParser.  This cleans up\n        reference cycles so the OptionParser (and all objects referenced by\n        it) can be garbage-collected promptly.  After calling destroy(), the\n        OptionParser is unusable.\n        "));
      var1.setlocal("destroy", var5);
      var3 = null;
      var1.setline(1242);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _create_option_list$103, (PyObject)null);
      var1.setlocal("_create_option_list", var5);
      var3 = null;
      var1.setline(1247);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _add_help_option$104, (PyObject)null);
      var1.setlocal("_add_help_option", var5);
      var3 = null;
      var1.setline(1252);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _add_version_option$105, (PyObject)null);
      var1.setlocal("_add_version_option", var5);
      var3 = null;
      var1.setline(1257);
      var4 = new PyObject[]{var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var4, _populate_option_list$106, (PyObject)null);
      var1.setlocal("_populate_option_list", var5);
      var3 = null;
      var1.setline(1267);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _init_parsing_state$107, (PyObject)null);
      var1.setlocal("_init_parsing_state", var5);
      var3 = null;
      var1.setline(1276);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_usage$108, (PyObject)null);
      var1.setlocal("set_usage", var5);
      var3 = null;
      var1.setline(1287);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, enable_interspersed_args$109, PyString.fromInterned("Set parsing to not stop on the first non-option, allowing\n        interspersing switches with command arguments. This is the\n        default behavior. See also disable_interspersed_args() and the\n        class documentation description of the attribute\n        allow_interspersed_args."));
      var1.setlocal("enable_interspersed_args", var5);
      var3 = null;
      var1.setline(1295);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, disable_interspersed_args$110, PyString.fromInterned("Set parsing to stop on the first non-option. Use this if\n        you have a command processor which runs another command that\n        has options of its own and you want to make sure these options\n        don't get confused.\n        "));
      var1.setlocal("disable_interspersed_args", var5);
      var3 = null;
      var1.setline(1303);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_process_default_values$111, (PyObject)null);
      var1.setlocal("set_process_default_values", var5);
      var3 = null;
      var1.setline(1306);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_default$112, (PyObject)null);
      var1.setlocal("set_default", var5);
      var3 = null;
      var1.setline(1309);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, set_defaults$113, (PyObject)null);
      var1.setlocal("set_defaults", var5);
      var3 = null;
      var1.setline(1312);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_all_options$114, (PyObject)null);
      var1.setlocal("_get_all_options", var5);
      var3 = null;
      var1.setline(1318);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_default_values$115, (PyObject)null);
      var1.setlocal("get_default_values", var5);
      var3 = null;
      var1.setline(1335);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, add_option_group$116, (PyObject)null);
      var1.setlocal("add_option_group", var5);
      var3 = null;
      var1.setline(1351);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_option_group$117, (PyObject)null);
      var1.setlocal("get_option_group", var5);
      var3 = null;
      var1.setline(1361);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_args$118, (PyObject)null);
      var1.setlocal("_get_args", var5);
      var3 = null;
      var1.setline(1367);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, parse_args$119, PyString.fromInterned("\n        parse_args(args : [string] = sys.argv[1:],\n                   values : Values = None)\n        -> (values : Values, args : [string])\n\n        Parse the command-line options found in 'args' (default:\n        sys.argv[1:]).  Any errors result in a call to 'error()', which\n        by default prints the usage message to stderr and calls\n        sys.exit() with an error message.  On success returns a pair\n        (values, args) where 'values' is an Values instance (with all\n        your option values) and 'args' is the list of arguments left\n        over after parsing options.\n        "));
      var1.setlocal("parse_args", var5);
      var3 = null;
      var1.setline(1406);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, check_values$120, PyString.fromInterned("\n        check_values(values : Values, args : [string])\n        -> (values : Values, args : [string])\n\n        Check that the supplied option values and leftover arguments are\n        valid.  Returns the option values and leftover arguments\n        (possibly adjusted, possibly completely new -- whatever you\n        like).  Default implementation just returns the passed-in\n        values; subclasses may override as desired.\n        "));
      var1.setlocal("check_values", var5);
      var3 = null;
      var1.setline(1419);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _process_args$121, PyString.fromInterned("_process_args(largs : [string],\n                         rargs : [string],\n                         values : Values)\n\n        Process command-line arguments and populate 'values', consuming\n        options and arguments from 'rargs'.  If 'allow_interspersed_args' is\n        false, stop at the first non-option argument.  If true, accumulate any\n        interspersed non-option arguments in 'largs'.\n        "));
      var1.setlocal("_process_args", var5);
      var3 = null;
      var1.setline(1470);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _match_long_opt$122, PyString.fromInterned("_match_long_opt(opt : string) -> string\n\n        Determine which long option string 'opt' matches, ie. which one\n        it is an unambiguous abbrevation for.  Raises BadOptionError if\n        'opt' doesn't unambiguously match any long option string.\n        "));
      var1.setlocal("_match_long_opt", var5);
      var3 = null;
      var1.setline(1479);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _process_long_opt$123, (PyObject)null);
      var1.setlocal("_process_long_opt", var5);
      var3 = null;
      var1.setline(1516);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _process_short_opts$124, (PyObject)null);
      var1.setlocal("_process_short_opts", var5);
      var3 = null;
      var1.setline(1558);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_prog_name$125, (PyObject)null);
      var1.setlocal("get_prog_name", var5);
      var3 = null;
      var1.setline(1564);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, expand_prog_name$126, (PyObject)null);
      var1.setlocal("expand_prog_name", var5);
      var3 = null;
      var1.setline(1567);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_description$127, (PyObject)null);
      var1.setlocal("get_description", var5);
      var3 = null;
      var1.setline(1570);
      var4 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, exit$128, (PyObject)null);
      var1.setlocal("exit", var5);
      var3 = null;
      var1.setline(1575);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, error$129, PyString.fromInterned("error(msg : string)\n\n        Print a usage message incorporating 'msg' to stderr and exit.\n        If you override this in a subclass, it should not return -- it\n        should either exit or raise an exception.\n        "));
      var1.setlocal("error", var5);
      var3 = null;
      var1.setline(1585);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_usage$130, (PyObject)null);
      var1.setlocal("get_usage", var5);
      var3 = null;
      var1.setline(1592);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, print_usage$131, PyString.fromInterned("print_usage(file : file = stdout)\n\n        Print the usage message for the current program (self.usage) to\n        'file' (default stdout).  Any occurrence of the string \"%prog\" in\n        self.usage is replaced with the name of the current program\n        (basename of sys.argv[0]).  Does nothing if self.usage is empty\n        or not defined.\n        "));
      var1.setlocal("print_usage", var5);
      var3 = null;
      var1.setline(1604);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, get_version$132, (PyObject)null);
      var1.setlocal("get_version", var5);
      var3 = null;
      var1.setline(1610);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, print_version$133, PyString.fromInterned("print_version(file : file = stdout)\n\n        Print the version message for this program (self.version) to\n        'file' (default stdout).  As with print_usage(), any occurrence\n        of \"%prog\" in self.version is replaced by the current program's\n        name.  Does nothing if self.version is empty or undefined.\n        "));
      var1.setlocal("print_version", var5);
      var3 = null;
      var1.setline(1621);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, format_option_help$134, (PyObject)null);
      var1.setlocal("format_option_help", var5);
      var3 = null;
      var1.setline(1638);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, format_epilog$135, (PyObject)null);
      var1.setlocal("format_epilog", var5);
      var3 = null;
      var1.setline(1641);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, format_help$136, (PyObject)null);
      var1.setlocal("format_help", var5);
      var3 = null;
      var1.setline(1654);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _get_encoding$137, (PyObject)null);
      var1.setlocal("_get_encoding", var5);
      var3 = null;
      var1.setline(1660);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, print_help$138, PyString.fromInterned("print_help(file : file = stdout)\n\n        Print an extended help message, listing all options and any\n        help text provided with them, to 'file' (default stdout).\n        "));
      var1.setlocal("print_help", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$101(PyFrame var1, ThreadState var2) {
      var1.setline(1201);
      var1.getglobal("OptionContainer").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(3), var1.getlocal(5), var1.getlocal(6));
      var1.setline(1203);
      var1.getlocal(0).__getattr__("set_usage").__call__(var2, var1.getlocal(1));
      var1.setline(1204);
      PyObject var3 = var1.getlocal(9);
      var1.getlocal(0).__setattr__("prog", var3);
      var3 = null;
      var1.setline(1205);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("version", var3);
      var3 = null;
      var1.setline(1206);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("allow_interspersed_args", var3);
      var3 = null;
      var1.setline(1207);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("process_default_values", var3);
      var3 = null;
      var1.setline(1208);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1209);
         var3 = var1.getglobal("IndentedHelpFormatter").__call__(var2);
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(1210);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("formatter", var3);
      var3 = null;
      var1.setline(1211);
      var1.getlocal(0).__getattr__("formatter").__getattr__("set_parser").__call__(var2, var1.getlocal(0));
      var1.setline(1212);
      var3 = var1.getlocal(10);
      var1.getlocal(0).__setattr__("epilog", var3);
      var3 = null;
      var1.setline(1218);
      var10000 = var1.getlocal(0).__getattr__("_populate_option_list");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(8)};
      String[] var4 = new String[]{"add_help"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.setline(1221);
      var1.getlocal(0).__getattr__("_init_parsing_state").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject destroy$102(PyFrame var1, ThreadState var2) {
      var1.setline(1230);
      PyString.fromInterned("\n        Declare that you are done with this OptionParser.  This cleans up\n        reference cycles so the OptionParser (and all objects referenced by\n        it) can be garbage-collected promptly.  After calling destroy(), the\n        OptionParser is unusable.\n        ");
      var1.setline(1231);
      var1.getglobal("OptionContainer").__getattr__("destroy").__call__(var2, var1.getlocal(0));
      var1.setline(1232);
      PyObject var3 = var1.getlocal(0).__getattr__("option_groups").__iter__();

      while(true) {
         var1.setline(1232);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1234);
            var1.getlocal(0).__delattr__("option_list");
            var1.setline(1235);
            var1.getlocal(0).__delattr__("option_groups");
            var1.setline(1236);
            var1.getlocal(0).__delattr__("formatter");
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(1233);
         var1.getlocal(1).__getattr__("destroy").__call__(var2);
      }
   }

   public PyObject _create_option_list$103(PyFrame var1, ThreadState var2) {
      var1.setline(1243);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"option_list", var3);
      var3 = null;
      var1.setline(1244);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"option_groups", var3);
      var3 = null;
      var1.setline(1245);
      var1.getlocal(0).__getattr__("_create_option_mappings").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _add_help_option$104(PyFrame var1, ThreadState var2) {
      var1.setline(1248);
      PyObject var10000 = var1.getlocal(0).__getattr__("add_option");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("-h"), PyString.fromInterned("--help"), PyString.fromInterned("help"), var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("show this help message and exit"))};
      String[] var4 = new String[]{"action", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _add_version_option$105(PyFrame var1, ThreadState var2) {
      var1.setline(1253);
      PyObject var10000 = var1.getlocal(0).__getattr__("add_option");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("--version"), PyString.fromInterned("version"), var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("show program's version number and exit"))};
      String[] var4 = new String[]{"action", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _populate_option_list$106(PyFrame var1, ThreadState var2) {
      var1.setline(1258);
      if (var1.getlocal(0).__getattr__("standard_option_list").__nonzero__()) {
         var1.setline(1259);
         var1.getlocal(0).__getattr__("add_options").__call__(var2, var1.getlocal(0).__getattr__("standard_option_list"));
      }

      var1.setline(1260);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1261);
         var1.getlocal(0).__getattr__("add_options").__call__(var2, var1.getlocal(1));
      }

      var1.setline(1262);
      if (var1.getlocal(0).__getattr__("version").__nonzero__()) {
         var1.setline(1263);
         var1.getlocal(0).__getattr__("_add_version_option").__call__(var2);
      }

      var1.setline(1264);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1265);
         var1.getlocal(0).__getattr__("_add_help_option").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_parsing_state$107(PyFrame var1, ThreadState var2) {
      var1.setline(1269);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("rargs", var3);
      var3 = null;
      var1.setline(1270);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("largs", var3);
      var3 = null;
      var1.setline(1271);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("values", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_usage$108(PyFrame var1, ThreadState var2) {
      var1.setline(1277);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1278);
         var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%prog [options]"));
         var1.getlocal(0).__setattr__("usage", var3);
         var3 = null;
      } else {
         var1.setline(1279);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("SUPPRESS_USAGE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1280);
            var3 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("usage", var3);
            var3 = null;
         } else {
            var1.setline(1282);
            if (var1.getlocal(1).__getattr__("lower").__call__(var2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("usage: ")).__nonzero__()) {
               var1.setline(1283);
               var3 = var1.getlocal(1).__getslice__(Py.newInteger(7), (PyObject)null, (PyObject)null);
               var1.getlocal(0).__setattr__("usage", var3);
               var3 = null;
            } else {
               var1.setline(1285);
               var3 = var1.getlocal(1);
               var1.getlocal(0).__setattr__("usage", var3);
               var3 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject enable_interspersed_args$109(PyFrame var1, ThreadState var2) {
      var1.setline(1292);
      PyString.fromInterned("Set parsing to not stop on the first non-option, allowing\n        interspersing switches with command arguments. This is the\n        default behavior. See also disable_interspersed_args() and the\n        class documentation description of the attribute\n        allow_interspersed_args.");
      var1.setline(1293);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("allow_interspersed_args", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject disable_interspersed_args$110(PyFrame var1, ThreadState var2) {
      var1.setline(1300);
      PyString.fromInterned("Set parsing to stop on the first non-option. Use this if\n        you have a command processor which runs another command that\n        has options of its own and you want to make sure these options\n        don't get confused.\n        ");
      var1.setline(1301);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("allow_interspersed_args", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_process_default_values$111(PyFrame var1, ThreadState var2) {
      var1.setline(1304);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("process_default_values", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_default$112(PyFrame var1, ThreadState var2) {
      var1.setline(1307);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("defaults").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_defaults$113(PyFrame var1, ThreadState var2) {
      var1.setline(1310);
      var1.getlocal(0).__getattr__("defaults").__getattr__("update").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_all_options$114(PyFrame var1, ThreadState var2) {
      var1.setline(1313);
      PyObject var3 = var1.getlocal(0).__getattr__("option_list").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1314);
      var3 = var1.getlocal(0).__getattr__("option_groups").__iter__();

      while(true) {
         var1.setline(1314);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1316);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(1315);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(2).__getattr__("option_list"));
      }
   }

   public PyObject get_default_values$115(PyFrame var1, ThreadState var2) {
      var1.setline(1319);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("process_default_values").__not__().__nonzero__()) {
         var1.setline(1321);
         var3 = var1.getglobal("Values").__call__(var2, var1.getlocal(0).__getattr__("defaults"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1323);
         PyObject var4 = var1.getlocal(0).__getattr__("defaults").__getattr__("copy").__call__(var2);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1324);
         var4 = var1.getlocal(0).__getattr__("_get_all_options").__call__(var2).__iter__();

         while(true) {
            var1.setline(1324);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(1330);
               var3 = var1.getglobal("Values").__call__(var2, var1.getlocal(1));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(2, var5);
            var1.setline(1325);
            PyObject var6 = var1.getlocal(1).__getattr__("get").__call__(var2, var1.getlocal(2).__getattr__("dest"));
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(1326);
            if (var1.getglobal("isbasestring").__call__(var2, var1.getlocal(3)).__nonzero__()) {
               var1.setline(1327);
               var6 = var1.getlocal(2).__getattr__("get_opt_string").__call__(var2);
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(1328);
               var6 = var1.getlocal(2).__getattr__("check_value").__call__(var2, var1.getlocal(4), var1.getlocal(3));
               var1.getlocal(1).__setitem__(var1.getlocal(2).__getattr__("dest"), var6);
               var6 = null;
            }
         }
      }
   }

   public PyObject add_option_group$116(PyFrame var1, ThreadState var2) {
      var1.setline(1337);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
      PyObject var10000 = var3._is(var1.getglobal("types").__getattr__("StringType"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1338);
         var10000 = var1.getglobal("OptionGroup");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
         var3 = null;
         var3 = var10000;
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(1339);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__not__();
         }

         if (!var10000.__nonzero__()) {
            var1.setline(1346);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("invalid arguments"));
         }

         var1.setline(1340);
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1341);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("OptionGroup")).__not__().__nonzero__()) {
            var1.setline(1342);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("not an OptionGroup instance: %r")._mod(var1.getlocal(3)));
         }

         var1.setline(1343);
         var3 = var1.getlocal(3).__getattr__("parser");
         var10000 = var3._isnot(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1344);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("invalid OptionGroup (wrong parser)"));
         }
      }

      var1.setline(1348);
      var1.getlocal(0).__getattr__("option_groups").__getattr__("append").__call__(var2, var1.getlocal(3));
      var1.setline(1349);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_option_group$117(PyFrame var1, ThreadState var2) {
      var1.setline(1352);
      PyObject var10000 = var1.getlocal(0).__getattr__("_short_opt").__getattr__("get").__call__(var2, var1.getlocal(1));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("_long_opt").__getattr__("get").__call__(var2, var1.getlocal(1));
      }

      PyObject var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1354);
      var10000 = var1.getlocal(2);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2).__getattr__("container");
         var10000 = var3._isnot(var1.getlocal(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1355);
         var3 = var1.getlocal(2).__getattr__("container");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1356);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _get_args$118(PyFrame var1, ThreadState var2) {
      var1.setline(1362);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1363);
         var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1365);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parse_args$119(PyFrame var1, ThreadState var2) {
      var1.setline(1380);
      PyString.fromInterned("\n        parse_args(args : [string] = sys.argv[1:],\n                   values : Values = None)\n        -> (values : Values, args : [string])\n\n        Parse the command-line options found in 'args' (default:\n        sys.argv[1:]).  Any errors result in a call to 'error()', which\n        by default prints the usage message to stderr and calls\n        sys.exit() with an error message.  On success returns a pair\n        (values, args) where 'values' is an Values instance (with all\n        your option values) and 'args' is the list of arguments left\n        over after parsing options.\n        ");
      var1.setline(1381);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_args").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1382);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1383);
         var3 = var1.getlocal(0).__getattr__("get_default_values").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1394);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("rargs", var3);
      var3 = null;
      var1.setline(1395);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"largs", var6);
      var1.setlocal(4, var6);
      var1.setline(1396);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("values", var3);
      var3 = null;

      try {
         var1.setline(1399);
         var3 = var1.getlocal(0).__getattr__("_process_args").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getlocal(2));
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(new PyTuple(new PyObject[]{var1.getglobal("BadOptionError"), var1.getglobal("OptionValueError")}))) {
            throw var7;
         }

         PyObject var4 = var7.value;
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(1401);
         var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(6)));
      }

      var1.setline(1403);
      var3 = var1.getlocal(4)._add(var1.getlocal(3));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1404);
      var3 = var1.getlocal(0).__getattr__("check_values").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject check_values$120(PyFrame var1, ThreadState var2) {
      var1.setline(1416);
      PyString.fromInterned("\n        check_values(values : Values, args : [string])\n        -> (values : Values, args : [string])\n\n        Check that the supplied option values and leftover arguments are\n        valid.  Returns the option values and leftover arguments\n        (possibly adjusted, possibly completely new -- whatever you\n        like).  Default implementation just returns the passed-in\n        values; subclasses may override as desired.\n        ");
      var1.setline(1417);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _process_args$121(PyFrame var1, ThreadState var2) {
      var1.setline(1428);
      PyString.fromInterned("_process_args(largs : [string],\n                         rargs : [string],\n                         values : Values)\n\n        Process command-line arguments and populate 'values', consuming\n        options and arguments from 'rargs'.  If 'allow_interspersed_args' is\n        false, stop at the first non-option argument.  If true, accumulate any\n        interspersed non-option arguments in 'largs'.\n        ");

      while(true) {
         var1.setline(1429);
         if (!var1.getlocal(2).__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(1430);
         PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1434);
         var3 = var1.getlocal(4);
         PyObject var10000 = var3._eq(PyString.fromInterned("--"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1435);
            var1.getlocal(2).__delitem__((PyObject)Py.newInteger(0));
            var1.setline(1436);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(1437);
         var3 = var1.getlocal(4).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned("--"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1439);
            var1.getlocal(0).__getattr__("_process_long_opt").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         } else {
            var1.setline(1440);
            var3 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(1), (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned("-"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
               var10000 = var3._gt(Py.newInteger(1));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1443);
               var1.getlocal(0).__getattr__("_process_short_opts").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            } else {
               var1.setline(1444);
               if (!var1.getlocal(0).__getattr__("allow_interspersed_args").__nonzero__()) {
                  var1.setline(1448);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(1445);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
               var1.setline(1446);
               var1.getlocal(2).__delitem__((PyObject)Py.newInteger(0));
            }
         }
      }
   }

   public PyObject _match_long_opt$122(PyFrame var1, ThreadState var2) {
      var1.setline(1476);
      PyString.fromInterned("_match_long_opt(opt : string) -> string\n\n        Determine which long option string 'opt' matches, ie. which one\n        it is an unambiguous abbrevation for.  Raises BadOptionError if\n        'opt' doesn't unambiguously match any long option string.\n        ");
      var1.setline(1477);
      PyObject var3 = var1.getglobal("_match_abbrev").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_long_opt"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _process_long_opt$123(PyFrame var1, ThreadState var2) {
      var1.setline(1480);
      PyObject var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1484);
      PyString var6 = PyString.fromInterned("=");
      PyObject var10000 = var6._in(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1485);
         var3 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(1486);
         var1.getlocal(1).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(5));
         var1.setline(1487);
         var3 = var1.getglobal("True");
         var1.setlocal(6, var3);
         var3 = null;
      } else {
         var1.setline(1489);
         var3 = var1.getlocal(3);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1490);
         var3 = var1.getglobal("False");
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(1492);
      var3 = var1.getlocal(0).__getattr__("_match_long_opt").__call__(var2, var1.getlocal(4));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1493);
      var3 = var1.getlocal(0).__getattr__("_long_opt").__getitem__(var1.getlocal(4));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1494);
      if (var1.getlocal(7).__getattr__("takes_value").__call__(var2).__nonzero__()) {
         var1.setline(1495);
         var3 = var1.getlocal(7).__getattr__("nargs");
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(1496);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._lt(var1.getlocal(8));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1497);
            var3 = var1.getlocal(8);
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1498);
               var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s option requires an argument"))._mod(var1.getlocal(4)));
            } else {
               var1.setline(1500);
               var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s option requires %d arguments"))._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(8)})));
            }
         } else {
            var1.setline(1502);
            var3 = var1.getlocal(8);
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1503);
               var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               var1.setlocal(9, var3);
               var3 = null;
            } else {
               var1.setline(1505);
               var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(0), var1.getlocal(8), (PyObject)null));
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(1506);
               var1.getlocal(1).__delslice__(Py.newInteger(0), var1.getlocal(8), (PyObject)null);
            }
         }
      } else {
         var1.setline(1508);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(1509);
            var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s option does not take a value"))._mod(var1.getlocal(4)));
         } else {
            var1.setline(1512);
            var3 = var1.getglobal("None");
            var1.setlocal(9, var3);
            var3 = null;
         }
      }

      var1.setline(1514);
      var1.getlocal(7).__getattr__("process").__call__(var2, var1.getlocal(4), var1.getlocal(9), var1.getlocal(2), var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _process_short_opts$124(PyFrame var1, ThreadState var2) {
      var1.setline(1517);
      PyObject var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1518);
      var3 = var1.getglobal("False");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1519);
      PyInteger var6 = Py.newInteger(1);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(1520);
      var3 = var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         var1.setline(1520);
         PyObject var4 = var3.__iternext__();
         if (var4 != null) {
            var1.setlocal(6, var4);
            var1.setline(1521);
            PyObject var5 = PyString.fromInterned("-")._add(var1.getlocal(6));
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(1522);
            var5 = var1.getlocal(0).__getattr__("_short_opt").__getattr__("get").__call__(var2, var1.getlocal(7));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(1523);
            var5 = var1.getlocal(5);
            var5 = var5._iadd(Py.newInteger(1));
            var1.setlocal(5, var5);
            var1.setline(1525);
            if (var1.getlocal(8).__not__().__nonzero__()) {
               var1.setline(1526);
               throw Py.makeException(var1.getglobal("BadOptionError").__call__(var2, var1.getlocal(7)));
            }

            var1.setline(1527);
            if (var1.getlocal(8).__getattr__("takes_value").__call__(var2).__nonzero__()) {
               var1.setline(1530);
               var5 = var1.getlocal(5);
               PyObject var10000 = var5._lt(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1531);
                  var1.getlocal(1).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(3).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null));
                  var1.setline(1532);
                  var5 = var1.getglobal("True");
                  var1.setlocal(4, var5);
                  var5 = null;
               }

               var1.setline(1534);
               var5 = var1.getlocal(8).__getattr__("nargs");
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(1535);
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var5._lt(var1.getlocal(9));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1536);
                  var5 = var1.getlocal(9);
                  var10000 = var5._eq(Py.newInteger(1));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1537);
                     var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s option requires an argument"))._mod(var1.getlocal(7)));
                  } else {
                     var1.setline(1539);
                     var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s option requires %d arguments"))._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(9)})));
                  }
               } else {
                  var1.setline(1541);
                  var5 = var1.getlocal(9);
                  var10000 = var5._eq(Py.newInteger(1));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1542);
                     var5 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
                     var1.setlocal(10, var5);
                     var5 = null;
                  } else {
                     var1.setline(1544);
                     var5 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(0), var1.getlocal(9), (PyObject)null));
                     var1.setlocal(10, var5);
                     var5 = null;
                     var1.setline(1545);
                     var1.getlocal(1).__delslice__(Py.newInteger(0), var1.getlocal(9), (PyObject)null);
                  }
               }
            } else {
               var1.setline(1548);
               var5 = var1.getglobal("None");
               var1.setlocal(10, var5);
               var5 = null;
            }

            var1.setline(1550);
            var1.getlocal(8).__getattr__("process").__call__(var2, var1.getlocal(7), var1.getlocal(10), var1.getlocal(2), var1.getlocal(0));
            var1.setline(1552);
            if (!var1.getlocal(4).__nonzero__()) {
               continue;
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject get_prog_name$125(PyFrame var1, ThreadState var2) {
      var1.setline(1559);
      PyObject var3 = var1.getlocal(0).__getattr__("prog");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1560);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1562);
         var3 = var1.getlocal(0).__getattr__("prog");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject expand_prog_name$126(PyFrame var1, ThreadState var2) {
      var1.setline(1565);
      PyObject var3 = var1.getlocal(1).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%prog"), (PyObject)var1.getlocal(0).__getattr__("get_prog_name").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_description$127(PyFrame var1, ThreadState var2) {
      var1.setline(1568);
      PyObject var3 = var1.getlocal(0).__getattr__("expand_prog_name").__call__(var2, var1.getlocal(0).__getattr__("description"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject exit$128(PyFrame var1, ThreadState var2) {
      var1.setline(1571);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1572);
         var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, var1.getlocal(2));
      }

      var1.setline(1573);
      var1.getglobal("sys").__getattr__("exit").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$129(PyFrame var1, ThreadState var2) {
      var1.setline(1581);
      PyString.fromInterned("error(msg : string)\n\n        Print a usage message incorporating 'msg' to stderr and exit.\n        If you override this in a subclass, it should not return -- it\n        should either exit or raise an exception.\n        ");
      var1.setline(1582);
      var1.getlocal(0).__getattr__("print_usage").__call__(var2, var1.getglobal("sys").__getattr__("stderr"));
      var1.setline(1583);
      var1.getlocal(0).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)PyString.fromInterned("%s: error: %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("get_prog_name").__call__(var2), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_usage$130(PyFrame var1, ThreadState var2) {
      var1.setline(1586);
      if (var1.getlocal(0).__getattr__("usage").__nonzero__()) {
         var1.setline(1587);
         PyObject var4 = var1.getlocal(0).__getattr__("formatter").__getattr__("format_usage").__call__(var2, var1.getlocal(0).__getattr__("expand_prog_name").__call__(var2, var1.getlocal(0).__getattr__("usage")));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1590);
         PyString var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject print_usage$131(PyFrame var1, ThreadState var2) {
      var1.setline(1600);
      PyString.fromInterned("print_usage(file : file = stdout)\n\n        Print the usage message for the current program (self.usage) to\n        'file' (default stdout).  Any occurrence of the string \"%prog\" in\n        self.usage is replaced with the name of the current program\n        (basename of sys.argv[0]).  Does nothing if self.usage is empty\n        or not defined.\n        ");
      var1.setline(1601);
      if (var1.getlocal(0).__getattr__("usage").__nonzero__()) {
         var1.setline(1602);
         PyObject var3 = var1.getlocal(1);
         Py.println(var3, var1.getlocal(0).__getattr__("get_usage").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_version$132(PyFrame var1, ThreadState var2) {
      var1.setline(1605);
      if (var1.getlocal(0).__getattr__("version").__nonzero__()) {
         var1.setline(1606);
         PyObject var4 = var1.getlocal(0).__getattr__("expand_prog_name").__call__(var2, var1.getlocal(0).__getattr__("version"));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1608);
         PyString var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject print_version$133(PyFrame var1, ThreadState var2) {
      var1.setline(1617);
      PyString.fromInterned("print_version(file : file = stdout)\n\n        Print the version message for this program (self.version) to\n        'file' (default stdout).  As with print_usage(), any occurrence\n        of \"%prog\" in self.version is replaced by the current program's\n        name.  Does nothing if self.version is empty or undefined.\n        ");
      var1.setline(1618);
      if (var1.getlocal(0).__getattr__("version").__nonzero__()) {
         var1.setline(1619);
         PyObject var3 = var1.getlocal(1);
         Py.println(var3, var1.getlocal(0).__getattr__("get_version").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_option_help$134(PyFrame var1, ThreadState var2) {
      var1.setline(1622);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1623);
         var3 = var1.getlocal(0).__getattr__("formatter");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1624);
      var1.getlocal(1).__getattr__("store_option_strings").__call__(var2, var1.getlocal(0));
      var1.setline(1625);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1626);
      var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("format_heading").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Options"))));
      var1.setline(1627);
      var1.getlocal(1).__getattr__("indent").__call__(var2);
      var1.setline(1628);
      if (var1.getlocal(0).__getattr__("option_list").__nonzero__()) {
         var1.setline(1629);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("OptionContainer").__getattr__("format_option_help").__call__(var2, var1.getlocal(0), var1.getlocal(1)));
         var1.setline(1630);
         var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }

      var1.setline(1631);
      var3 = var1.getlocal(0).__getattr__("option_groups").__iter__();

      while(true) {
         var1.setline(1631);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1634);
            var1.getlocal(1).__getattr__("dedent").__call__(var2);
            var1.setline(1636);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(1632);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3).__getattr__("format_help").__call__(var2, var1.getlocal(1)));
         var1.setline(1633);
         var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }
   }

   public PyObject format_epilog$135(PyFrame var1, ThreadState var2) {
      var1.setline(1639);
      PyObject var3 = var1.getlocal(1).__getattr__("format_epilog").__call__(var2, var1.getlocal(0).__getattr__("epilog"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format_help$136(PyFrame var1, ThreadState var2) {
      var1.setline(1642);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1643);
         var3 = var1.getlocal(0).__getattr__("formatter");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1644);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1645);
      if (var1.getlocal(0).__getattr__("usage").__nonzero__()) {
         var1.setline(1646);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("get_usage").__call__(var2)._add(PyString.fromInterned("\n")));
      }

      var1.setline(1647);
      if (var1.getlocal(0).__getattr__("description").__nonzero__()) {
         var1.setline(1648);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("format_description").__call__(var2, var1.getlocal(1))._add(PyString.fromInterned("\n")));
      }

      var1.setline(1649);
      var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("format_option_help").__call__(var2, var1.getlocal(1)));
      var1.setline(1650);
      var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("format_epilog").__call__(var2, var1.getlocal(1)));
      var1.setline(1651);
      var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_encoding$137(PyFrame var1, ThreadState var2) {
      var1.setline(1655);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("encoding"), (PyObject)var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1656);
      if (var1.getlocal(2).__not__().__nonzero__()) {
         var1.setline(1657);
         var3 = var1.getglobal("sys").__getattr__("getdefaultencoding").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1658);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject print_help$138(PyFrame var1, ThreadState var2) {
      var1.setline(1665);
      PyString.fromInterned("print_help(file : file = stdout)\n\n        Print an extended help message, listing all options and any\n        help text provided with them, to 'file' (default stdout).\n        ");
      var1.setline(1666);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1667);
         var3 = var1.getglobal("sys").__getattr__("stdout");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1668);
      var3 = var1.getlocal(0).__getattr__("_get_encoding").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1669);
      var1.getlocal(1).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("format_help").__call__(var2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("replace")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _match_abbrev$139(PyFrame var1, ThreadState var2) {
      var1.setline(1680);
      PyString.fromInterned("_match_abbrev(s : string, wordmap : {string : Option}) -> string\n\n    Return the string key in 'wordmap' for which 's' is an unambiguous\n    abbreviation.  If 's' is found to be ambiguous or doesn't match any of\n    'words', raise BadOptionError.\n    ");
      var1.setline(1682);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1683);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1686);
         PyList var7 = new PyList();
         PyObject var4 = var7.__getattr__("append");
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1686);
         var4 = var1.getlocal(1).__getattr__("keys").__call__(var2).__iter__();

         while(true) {
            var1.setline(1686);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(1686);
               var1.dellocal(3);
               PyList var6 = var7;
               var1.setlocal(2, var6);
               var4 = null;
               var1.setline(1689);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var4._eq(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1690);
                  var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(1691);
               if (var1.getlocal(2).__not__().__nonzero__()) {
                  var1.setline(1692);
                  throw Py.makeException(var1.getglobal("BadOptionError").__call__(var2, var1.getlocal(0)));
               }

               var1.setline(1695);
               var1.getlocal(2).__getattr__("sort").__call__(var2);
               var1.setline(1696);
               throw Py.makeException(var1.getglobal("AmbiguousOptionError").__call__(var2, var1.getlocal(0), var1.getlocal(2)));
            }

            var1.setlocal(4, var5);
            var1.setline(1687);
            if (var1.getlocal(4).__getattr__("startswith").__call__(var2, var1.getlocal(0)).__nonzero__()) {
               var1.setline(1686);
               var1.getlocal(3).__call__(var2, var1.getlocal(4));
            }
         }
      }
   }

   public optparse$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      _repr$1 = Py.newCode(1, var2, var1, "_repr", 79, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"message"};
      gettext$2 = Py.newCode(1, var2, var1, "gettext", 92, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OptParseError$3 = Py.newCode(0, var2, var1, "OptParseError", 97, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg"};
      __init__$4 = Py.newCode(2, var2, var1, "__init__", 98, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$5 = Py.newCode(1, var2, var1, "__str__", 101, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OptionError$6 = Py.newCode(0, var2, var1, "OptionError", 105, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg", "option"};
      __init__$7 = Py.newCode(3, var2, var1, "__init__", 111, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$8 = Py.newCode(1, var2, var1, "__str__", 115, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OptionConflictError$9 = Py.newCode(0, var2, var1, "OptionConflictError", 121, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      OptionValueError$10 = Py.newCode(0, var2, var1, "OptionValueError", 126, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BadOptionError$11 = Py.newCode(0, var2, var1, "BadOptionError", 132, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "opt_str"};
      __init__$12 = Py.newCode(2, var2, var1, "__init__", 136, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$13 = Py.newCode(1, var2, var1, "__str__", 139, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      AmbiguousOptionError$14 = Py.newCode(0, var2, var1, "AmbiguousOptionError", 142, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "opt_str", "possibilities"};
      __init__$15 = Py.newCode(3, var2, var1, "__init__", 146, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$16 = Py.newCode(1, var2, var1, "__str__", 150, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HelpFormatter$17 = Py.newCode(0, var2, var1, "HelpFormatter", 155, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "indent_increment", "max_help_position", "width", "short_first"};
      __init__$18 = Py.newCode(5, var2, var1, "__init__", 200, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser"};
      set_parser$19 = Py.newCode(2, var2, var1, "set_parser", 224, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "delim"};
      set_short_opt_delimiter$20 = Py.newCode(2, var2, var1, "set_short_opt_delimiter", 227, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "delim"};
      set_long_opt_delimiter$21 = Py.newCode(2, var2, var1, "set_long_opt_delimiter", 233, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      indent$22 = Py.newCode(1, var2, var1, "indent", 239, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      dedent$23 = Py.newCode(1, var2, var1, "dedent", 243, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "usage"};
      format_usage$24 = Py.newCode(2, var2, var1, "format_usage", 248, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "heading"};
      format_heading$25 = Py.newCode(2, var2, var1, "format_heading", 251, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "text_width", "indent"};
      _format_text$26 = Py.newCode(2, var2, var1, "_format_text", 254, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "description"};
      format_description$27 = Py.newCode(2, var2, var1, "format_description", 266, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "epilog"};
      format_epilog$28 = Py.newCode(2, var2, var1, "format_epilog", 272, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "default_value"};
      expand_default$29 = Py.newCode(2, var2, var1, "expand_default", 279, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "result", "opts", "opt_width", "indent_first", "help_text", "help_lines", "_[318_27]", "line"};
      format_option$30 = Py.newCode(2, var2, var1, "format_option", 289, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "max_len", "opt", "strings", "group"};
      store_option_strings$31 = Py.newCode(2, var2, var1, "store_option_strings", 324, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "metavar", "short_opts", "_[346_26]", "sopt", "long_opts", "_[348_25]", "lopt", "opts"};
      format_option_strings$32 = Py.newCode(2, var2, var1, "format_option_strings", 342, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IndentedHelpFormatter$33 = Py.newCode(0, var2, var1, "IndentedHelpFormatter", 361, false, false, self, 33, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "indent_increment", "max_help_position", "width", "short_first"};
      __init__$34 = Py.newCode(5, var2, var1, "__init__", 365, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "usage"};
      format_usage$35 = Py.newCode(2, var2, var1, "format_usage", 373, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "heading"};
      format_heading$36 = Py.newCode(2, var2, var1, "format_heading", 376, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TitledHelpFormatter$37 = Py.newCode(0, var2, var1, "TitledHelpFormatter", 380, false, false, self, 37, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "indent_increment", "max_help_position", "width", "short_first"};
      __init__$38 = Py.newCode(5, var2, var1, "__init__", 384, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "usage"};
      format_usage$39 = Py.newCode(2, var2, var1, "format_usage", 392, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "heading"};
      format_heading$40 = Py.newCode(2, var2, var1, "format_heading", 395, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"val", "type", "radix"};
      _parse_num$41 = Py.newCode(2, var2, var1, "_parse_num", 399, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"val"};
      _parse_int$42 = Py.newCode(1, var2, var1, "_parse_int", 412, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"val"};
      _parse_long$43 = Py.newCode(1, var2, var1, "_parse_long", 415, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"option", "opt", "value", "cvt", "what"};
      check_builtin$44 = Py.newCode(3, var2, var1, "check_builtin", 423, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"option", "opt", "value", "choices"};
      check_choice$45 = Py.newCode(3, var2, var1, "check_choice", 431, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Option$46 = Py.newCode(0, var2, var1, "Option", 445, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "opts", "attrs", "checker"};
      __init__$47 = Py.newCode(3, var2, var1, "__init__", 560, true, true, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opts"};
      _check_opt_strings$48 = Py.newCode(2, var2, var1, "_check_opt_strings", 579, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opts", "opt"};
      _set_opt_strings$49 = Py.newCode(2, var2, var1, "_set_opt_strings", 588, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "attr"};
      _set_attrs$50 = Py.newCode(2, var2, var1, "_set_attrs", 609, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _check_action$51 = Py.newCode(1, var2, var1, "_check_action", 629, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "__builtin__"};
      _check_type$52 = Py.newCode(1, var2, var1, "_check_type", 635, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _check_choice$53 = Py.newCode(1, var2, var1, "_check_choice", 665, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "takes_value"};
      _check_dest$54 = Py.newCode(1, var2, var1, "_check_dest", 678, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _check_const$55 = Py.newCode(1, var2, var1, "_check_const", 693, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _check_nargs$56 = Py.newCode(1, var2, var1, "_check_nargs", 699, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _check_callback$57 = Py.newCode(1, var2, var1, "_check_callback", 708, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$58 = Py.newCode(1, var2, var1, "__str__", 747, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      takes_value$59 = Py.newCode(1, var2, var1, "takes_value", 752, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_opt_string$60 = Py.newCode(1, var2, var1, "get_opt_string", 755, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opt", "value", "checker"};
      check_value$61 = Py.newCode(3, var2, var1, "check_value", 764, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opt", "value", "_[776_30]", "v"};
      convert_value$62 = Py.newCode(3, var2, var1, "convert_value", 771, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opt", "value", "values", "parser"};
      process$63 = Py.newCode(5, var2, var1, "process", 778, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "dest", "opt", "value", "values", "parser", "args", "kwargs"};
      take_action$64 = Py.newCode(7, var2, var1, "take_action", 790, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      isbasestring$65 = Py.newCode(1, var2, var1, "isbasestring", 829, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      isbasestring$66 = Py.newCode(1, var2, var1, "isbasestring", 832, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Values$67 = Py.newCode(0, var2, var1, "Values", 835, false, false, self, 67, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "defaults", "attr", "val"};
      __init__$68 = Py.newCode(2, var2, var1, "__init__", 837, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$69 = Py.newCode(1, var2, var1, "__str__", 842, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __cmp__$70 = Py.newCode(2, var2, var1, "__cmp__", 847, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "attr", "dval"};
      _update_careful$71 = Py.newCode(2, var2, var1, "_update_careful", 855, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict"};
      _update_loose$72 = Py.newCode(2, var2, var1, "_update_loose", 868, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "mode"};
      _update$73 = Py.newCode(3, var2, var1, "_update", 876, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "modname", "mode", "mod"};
      read_module$74 = Py.newCode(3, var2, var1, "read_module", 884, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "mode", "vars"};
      read_file$75 = Py.newCode(3, var2, var1, "read_file", 889, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr", "value"};
      ensure_value$76 = Py.newCode(3, var2, var1, "ensure_value", 894, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OptionContainer$77 = Py.newCode(0, var2, var1, "OptionContainer", 900, false, false, self, 77, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_class", "conflict_handler", "description"};
      __init__$78 = Py.newCode(4, var2, var1, "__init__", 932, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _create_option_mappings$79 = Py.newCode(1, var2, var1, "_create_option_mappings", 943, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser"};
      _share_option_mappings$80 = Py.newCode(2, var2, var1, "_share_option_mappings", 952, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler"};
      set_conflict_handler$81 = Py.newCode(2, var2, var1, "set_conflict_handler", 959, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "description"};
      set_description$82 = Py.newCode(2, var2, var1, "set_description", 964, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_description$83 = Py.newCode(1, var2, var1, "get_description", 967, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      destroy$84 = Py.newCode(1, var2, var1, "destroy", 971, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "conflict_opts", "opt", "handler", "_[994_33]", "co", "c_option"};
      _check_conflict$85 = Py.newCode(2, var2, var1, "_check_conflict", 980, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwargs", "option", "opt"};
      add_option$86 = Py.newCode(3, var2, var1, "add_option", 1007, true, true, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option_list", "option"};
      add_options$87 = Py.newCode(2, var2, var1, "add_options", 1037, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opt_str"};
      get_option$88 = Py.newCode(2, var2, var1, "get_option", 1043, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opt_str"};
      has_option$89 = Py.newCode(2, var2, var1, "has_option", 1047, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opt_str", "option", "opt"};
      remove_option$90 = Py.newCode(2, var2, var1, "remove_option", 1051, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "formatter", "result", "option"};
      format_option_help$91 = Py.newCode(2, var2, var1, "format_option_help", 1067, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "formatter"};
      format_description$92 = Py.newCode(2, var2, var1, "format_description", 1076, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "formatter", "result"};
      format_help$93 = Py.newCode(2, var2, var1, "format_help", 1079, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OptionGroup$94 = Py.newCode(0, var2, var1, "OptionGroup", 1088, false, false, self, 94, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "parser", "title", "description"};
      __init__$95 = Py.newCode(4, var2, var1, "__init__", 1090, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _create_option_list$96 = Py.newCode(1, var2, var1, "_create_option_list", 1096, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "title"};
      set_title$97 = Py.newCode(2, var2, var1, "set_title", 1100, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      destroy$98 = Py.newCode(1, var2, var1, "destroy", 1103, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "formatter", "result"};
      format_help$99 = Py.newCode(2, var2, var1, "format_help", 1110, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      OptionParser$100 = Py.newCode(0, var2, var1, "OptionParser", 1118, false, false, self, 100, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "usage", "option_list", "option_class", "version", "conflict_handler", "description", "formatter", "add_help_option", "prog", "epilog"};
      __init__$101 = Py.newCode(11, var2, var1, "__init__", 1190, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "group"};
      destroy$102 = Py.newCode(1, var2, var1, "destroy", 1224, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _create_option_list$103 = Py.newCode(1, var2, var1, "_create_option_list", 1242, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _add_help_option$104 = Py.newCode(1, var2, var1, "_add_help_option", 1247, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _add_version_option$105 = Py.newCode(1, var2, var1, "_add_version_option", 1252, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option_list", "add_help"};
      _populate_option_list$106 = Py.newCode(3, var2, var1, "_populate_option_list", 1257, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _init_parsing_state$107 = Py.newCode(1, var2, var1, "_init_parsing_state", 1267, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "usage"};
      set_usage$108 = Py.newCode(2, var2, var1, "set_usage", 1276, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      enable_interspersed_args$109 = Py.newCode(1, var2, var1, "enable_interspersed_args", 1287, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      disable_interspersed_args$110 = Py.newCode(1, var2, var1, "disable_interspersed_args", 1295, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "process"};
      set_process_default_values$111 = Py.newCode(2, var2, var1, "set_process_default_values", 1303, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dest", "value"};
      set_default$112 = Py.newCode(3, var2, var1, "set_default", 1306, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "kwargs"};
      set_defaults$113 = Py.newCode(2, var2, var1, "set_defaults", 1309, false, true, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "options", "group"};
      _get_all_options$114 = Py.newCode(1, var2, var1, "_get_all_options", 1312, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "defaults", "option", "default", "opt_str"};
      get_default_values$115 = Py.newCode(1, var2, var1, "get_default_values", 1318, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwargs", "group"};
      add_option_group$116 = Py.newCode(3, var2, var1, "add_option_group", 1335, true, true, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opt_str", "option"};
      get_option_group$117 = Py.newCode(2, var2, var1, "get_option_group", 1351, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      _get_args$118 = Py.newCode(2, var2, var1, "_get_args", 1361, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "values", "rargs", "largs", "stop", "err"};
      parse_args$119 = Py.newCode(3, var2, var1, "parse_args", 1367, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "values", "args"};
      check_values$120 = Py.newCode(3, var2, var1, "check_values", 1406, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "largs", "rargs", "values", "arg"};
      _process_args$121 = Py.newCode(4, var2, var1, "_process_args", 1419, false, false, self, 121, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "opt"};
      _match_long_opt$122 = Py.newCode(2, var2, var1, "_match_long_opt", 1470, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rargs", "values", "arg", "opt", "next_arg", "had_explicit_value", "option", "nargs", "value"};
      _process_long_opt$123 = Py.newCode(3, var2, var1, "_process_long_opt", 1479, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rargs", "values", "arg", "stop", "i", "ch", "opt", "option", "nargs", "value"};
      _process_short_opts$124 = Py.newCode(3, var2, var1, "_process_short_opts", 1516, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_prog_name$125 = Py.newCode(1, var2, var1, "get_prog_name", 1558, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s"};
      expand_prog_name$126 = Py.newCode(2, var2, var1, "expand_prog_name", 1564, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_description$127 = Py.newCode(1, var2, var1, "get_description", 1567, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "status", "msg"};
      exit$128 = Py.newCode(3, var2, var1, "exit", 1570, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      error$129 = Py.newCode(2, var2, var1, "error", 1575, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_usage$130 = Py.newCode(1, var2, var1, "get_usage", 1585, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      print_usage$131 = Py.newCode(2, var2, var1, "print_usage", 1592, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_version$132 = Py.newCode(1, var2, var1, "get_version", 1604, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      print_version$133 = Py.newCode(2, var2, var1, "print_version", 1610, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "formatter", "result", "group"};
      format_option_help$134 = Py.newCode(2, var2, var1, "format_option_help", 1621, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "formatter"};
      format_epilog$135 = Py.newCode(2, var2, var1, "format_epilog", 1638, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "formatter", "result"};
      format_help$136 = Py.newCode(2, var2, var1, "format_help", 1641, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "encoding"};
      _get_encoding$137 = Py.newCode(2, var2, var1, "_get_encoding", 1654, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "encoding"};
      print_help$138 = Py.newCode(2, var2, var1, "print_help", 1660, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "wordmap", "possibilities", "_[1686_25]", "word"};
      _match_abbrev$139 = Py.newCode(2, var2, var1, "_match_abbrev", 1674, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new optparse$py("optparse$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(optparse$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._repr$1(var2, var3);
         case 2:
            return this.gettext$2(var2, var3);
         case 3:
            return this.OptParseError$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.__str__$5(var2, var3);
         case 6:
            return this.OptionError$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.__str__$8(var2, var3);
         case 9:
            return this.OptionConflictError$9(var2, var3);
         case 10:
            return this.OptionValueError$10(var2, var3);
         case 11:
            return this.BadOptionError$11(var2, var3);
         case 12:
            return this.__init__$12(var2, var3);
         case 13:
            return this.__str__$13(var2, var3);
         case 14:
            return this.AmbiguousOptionError$14(var2, var3);
         case 15:
            return this.__init__$15(var2, var3);
         case 16:
            return this.__str__$16(var2, var3);
         case 17:
            return this.HelpFormatter$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.set_parser$19(var2, var3);
         case 20:
            return this.set_short_opt_delimiter$20(var2, var3);
         case 21:
            return this.set_long_opt_delimiter$21(var2, var3);
         case 22:
            return this.indent$22(var2, var3);
         case 23:
            return this.dedent$23(var2, var3);
         case 24:
            return this.format_usage$24(var2, var3);
         case 25:
            return this.format_heading$25(var2, var3);
         case 26:
            return this._format_text$26(var2, var3);
         case 27:
            return this.format_description$27(var2, var3);
         case 28:
            return this.format_epilog$28(var2, var3);
         case 29:
            return this.expand_default$29(var2, var3);
         case 30:
            return this.format_option$30(var2, var3);
         case 31:
            return this.store_option_strings$31(var2, var3);
         case 32:
            return this.format_option_strings$32(var2, var3);
         case 33:
            return this.IndentedHelpFormatter$33(var2, var3);
         case 34:
            return this.__init__$34(var2, var3);
         case 35:
            return this.format_usage$35(var2, var3);
         case 36:
            return this.format_heading$36(var2, var3);
         case 37:
            return this.TitledHelpFormatter$37(var2, var3);
         case 38:
            return this.__init__$38(var2, var3);
         case 39:
            return this.format_usage$39(var2, var3);
         case 40:
            return this.format_heading$40(var2, var3);
         case 41:
            return this._parse_num$41(var2, var3);
         case 42:
            return this._parse_int$42(var2, var3);
         case 43:
            return this._parse_long$43(var2, var3);
         case 44:
            return this.check_builtin$44(var2, var3);
         case 45:
            return this.check_choice$45(var2, var3);
         case 46:
            return this.Option$46(var2, var3);
         case 47:
            return this.__init__$47(var2, var3);
         case 48:
            return this._check_opt_strings$48(var2, var3);
         case 49:
            return this._set_opt_strings$49(var2, var3);
         case 50:
            return this._set_attrs$50(var2, var3);
         case 51:
            return this._check_action$51(var2, var3);
         case 52:
            return this._check_type$52(var2, var3);
         case 53:
            return this._check_choice$53(var2, var3);
         case 54:
            return this._check_dest$54(var2, var3);
         case 55:
            return this._check_const$55(var2, var3);
         case 56:
            return this._check_nargs$56(var2, var3);
         case 57:
            return this._check_callback$57(var2, var3);
         case 58:
            return this.__str__$58(var2, var3);
         case 59:
            return this.takes_value$59(var2, var3);
         case 60:
            return this.get_opt_string$60(var2, var3);
         case 61:
            return this.check_value$61(var2, var3);
         case 62:
            return this.convert_value$62(var2, var3);
         case 63:
            return this.process$63(var2, var3);
         case 64:
            return this.take_action$64(var2, var3);
         case 65:
            return this.isbasestring$65(var2, var3);
         case 66:
            return this.isbasestring$66(var2, var3);
         case 67:
            return this.Values$67(var2, var3);
         case 68:
            return this.__init__$68(var2, var3);
         case 69:
            return this.__str__$69(var2, var3);
         case 70:
            return this.__cmp__$70(var2, var3);
         case 71:
            return this._update_careful$71(var2, var3);
         case 72:
            return this._update_loose$72(var2, var3);
         case 73:
            return this._update$73(var2, var3);
         case 74:
            return this.read_module$74(var2, var3);
         case 75:
            return this.read_file$75(var2, var3);
         case 76:
            return this.ensure_value$76(var2, var3);
         case 77:
            return this.OptionContainer$77(var2, var3);
         case 78:
            return this.__init__$78(var2, var3);
         case 79:
            return this._create_option_mappings$79(var2, var3);
         case 80:
            return this._share_option_mappings$80(var2, var3);
         case 81:
            return this.set_conflict_handler$81(var2, var3);
         case 82:
            return this.set_description$82(var2, var3);
         case 83:
            return this.get_description$83(var2, var3);
         case 84:
            return this.destroy$84(var2, var3);
         case 85:
            return this._check_conflict$85(var2, var3);
         case 86:
            return this.add_option$86(var2, var3);
         case 87:
            return this.add_options$87(var2, var3);
         case 88:
            return this.get_option$88(var2, var3);
         case 89:
            return this.has_option$89(var2, var3);
         case 90:
            return this.remove_option$90(var2, var3);
         case 91:
            return this.format_option_help$91(var2, var3);
         case 92:
            return this.format_description$92(var2, var3);
         case 93:
            return this.format_help$93(var2, var3);
         case 94:
            return this.OptionGroup$94(var2, var3);
         case 95:
            return this.__init__$95(var2, var3);
         case 96:
            return this._create_option_list$96(var2, var3);
         case 97:
            return this.set_title$97(var2, var3);
         case 98:
            return this.destroy$98(var2, var3);
         case 99:
            return this.format_help$99(var2, var3);
         case 100:
            return this.OptionParser$100(var2, var3);
         case 101:
            return this.__init__$101(var2, var3);
         case 102:
            return this.destroy$102(var2, var3);
         case 103:
            return this._create_option_list$103(var2, var3);
         case 104:
            return this._add_help_option$104(var2, var3);
         case 105:
            return this._add_version_option$105(var2, var3);
         case 106:
            return this._populate_option_list$106(var2, var3);
         case 107:
            return this._init_parsing_state$107(var2, var3);
         case 108:
            return this.set_usage$108(var2, var3);
         case 109:
            return this.enable_interspersed_args$109(var2, var3);
         case 110:
            return this.disable_interspersed_args$110(var2, var3);
         case 111:
            return this.set_process_default_values$111(var2, var3);
         case 112:
            return this.set_default$112(var2, var3);
         case 113:
            return this.set_defaults$113(var2, var3);
         case 114:
            return this._get_all_options$114(var2, var3);
         case 115:
            return this.get_default_values$115(var2, var3);
         case 116:
            return this.add_option_group$116(var2, var3);
         case 117:
            return this.get_option_group$117(var2, var3);
         case 118:
            return this._get_args$118(var2, var3);
         case 119:
            return this.parse_args$119(var2, var3);
         case 120:
            return this.check_values$120(var2, var3);
         case 121:
            return this._process_args$121(var2, var3);
         case 122:
            return this._match_long_opt$122(var2, var3);
         case 123:
            return this._process_long_opt$123(var2, var3);
         case 124:
            return this._process_short_opts$124(var2, var3);
         case 125:
            return this.get_prog_name$125(var2, var3);
         case 126:
            return this.expand_prog_name$126(var2, var3);
         case 127:
            return this.get_description$127(var2, var3);
         case 128:
            return this.exit$128(var2, var3);
         case 129:
            return this.error$129(var2, var3);
         case 130:
            return this.get_usage$130(var2, var3);
         case 131:
            return this.print_usage$131(var2, var3);
         case 132:
            return this.get_version$132(var2, var3);
         case 133:
            return this.print_version$133(var2, var3);
         case 134:
            return this.format_option_help$134(var2, var3);
         case 135:
            return this.format_epilog$135(var2, var3);
         case 136:
            return this.format_help$136(var2, var3);
         case 137:
            return this._get_encoding$137(var2, var3);
         case 138:
            return this.print_help$138(var2, var3);
         case 139:
            return this._match_abbrev$139(var2, var3);
         default:
            return null;
      }
   }
}
