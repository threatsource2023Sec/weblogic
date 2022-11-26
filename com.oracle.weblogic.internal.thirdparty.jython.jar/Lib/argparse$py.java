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
@Filename("argparse.py")
public class argparse$py extends PyFunctionTable implements PyRunnable {
   static argparse$py self;
   static final PyCode f$0;
   static final PyCode _callable$1;
   static final PyCode _AttributeHolder$2;
   static final PyCode __repr__$3;
   static final PyCode _get_kwargs$4;
   static final PyCode _get_args$5;
   static final PyCode _ensure_value$6;
   static final PyCode HelpFormatter$7;
   static final PyCode __init__$8;
   static final PyCode _indent$9;
   static final PyCode _dedent$10;
   static final PyCode _Section$11;
   static final PyCode __init__$12;
   static final PyCode format_help$13;
   static final PyCode _add_item$14;
   static final PyCode start_section$15;
   static final PyCode end_section$16;
   static final PyCode add_text$17;
   static final PyCode add_usage$18;
   static final PyCode add_argument$19;
   static final PyCode add_arguments$20;
   static final PyCode format_help$21;
   static final PyCode _join_parts$22;
   static final PyCode _format_usage$23;
   static final PyCode get_lines$24;
   static final PyCode _format_actions_usage$25;
   static final PyCode _format_text$26;
   static final PyCode _format_action$27;
   static final PyCode _format_action_invocation$28;
   static final PyCode _metavar_formatter$29;
   static final PyCode format$30;
   static final PyCode _format_args$31;
   static final PyCode _expand_help$32;
   static final PyCode _iter_indented_subactions$33;
   static final PyCode _split_lines$34;
   static final PyCode _fill_text$35;
   static final PyCode _get_help_string$36;
   static final PyCode RawDescriptionHelpFormatter$37;
   static final PyCode _fill_text$38;
   static final PyCode RawTextHelpFormatter$39;
   static final PyCode _split_lines$40;
   static final PyCode ArgumentDefaultsHelpFormatter$41;
   static final PyCode _get_help_string$42;
   static final PyCode _get_action_name$43;
   static final PyCode ArgumentError$44;
   static final PyCode __init__$45;
   static final PyCode __str__$46;
   static final PyCode ArgumentTypeError$47;
   static final PyCode Action$48;
   static final PyCode __init__$49;
   static final PyCode _get_kwargs$50;
   static final PyCode __call__$51;
   static final PyCode _StoreAction$52;
   static final PyCode __init__$53;
   static final PyCode __call__$54;
   static final PyCode _StoreConstAction$55;
   static final PyCode __init__$56;
   static final PyCode __call__$57;
   static final PyCode _StoreTrueAction$58;
   static final PyCode __init__$59;
   static final PyCode _StoreFalseAction$60;
   static final PyCode __init__$61;
   static final PyCode _AppendAction$62;
   static final PyCode __init__$63;
   static final PyCode __call__$64;
   static final PyCode _AppendConstAction$65;
   static final PyCode __init__$66;
   static final PyCode __call__$67;
   static final PyCode _CountAction$68;
   static final PyCode __init__$69;
   static final PyCode __call__$70;
   static final PyCode _HelpAction$71;
   static final PyCode __init__$72;
   static final PyCode __call__$73;
   static final PyCode _VersionAction$74;
   static final PyCode __init__$75;
   static final PyCode __call__$76;
   static final PyCode _SubParsersAction$77;
   static final PyCode _ChoicesPseudoAction$78;
   static final PyCode __init__$79;
   static final PyCode __init__$80;
   static final PyCode add_parser$81;
   static final PyCode _get_subactions$82;
   static final PyCode __call__$83;
   static final PyCode FileType$84;
   static final PyCode __init__$85;
   static final PyCode __call__$86;
   static final PyCode __repr__$87;
   static final PyCode f$88;
   static final PyCode Namespace$89;
   static final PyCode __init__$90;
   static final PyCode __eq__$91;
   static final PyCode __ne__$92;
   static final PyCode __contains__$93;
   static final PyCode _ActionsContainer$94;
   static final PyCode __init__$95;
   static final PyCode register$96;
   static final PyCode _registry_get$97;
   static final PyCode set_defaults$98;
   static final PyCode get_default$99;
   static final PyCode add_argument$100;
   static final PyCode add_argument_group$101;
   static final PyCode add_mutually_exclusive_group$102;
   static final PyCode _add_action$103;
   static final PyCode _remove_action$104;
   static final PyCode _add_container_actions$105;
   static final PyCode _get_positional_kwargs$106;
   static final PyCode _get_optional_kwargs$107;
   static final PyCode _pop_action_class$108;
   static final PyCode _get_handler$109;
   static final PyCode _check_conflict$110;
   static final PyCode _handle_conflict_error$111;
   static final PyCode _handle_conflict_resolve$112;
   static final PyCode _ArgumentGroup$113;
   static final PyCode __init__$114;
   static final PyCode _add_action$115;
   static final PyCode _remove_action$116;
   static final PyCode _MutuallyExclusiveGroup$117;
   static final PyCode __init__$118;
   static final PyCode _add_action$119;
   static final PyCode _remove_action$120;
   static final PyCode ArgumentParser$121;
   static final PyCode __init__$122;
   static final PyCode identity$123;
   static final PyCode _get_kwargs$124;
   static final PyCode add_subparsers$125;
   static final PyCode _add_action$126;
   static final PyCode _get_optional_actions$127;
   static final PyCode _get_positional_actions$128;
   static final PyCode parse_args$129;
   static final PyCode parse_known_args$130;
   static final PyCode _parse_known_args$131;
   static final PyCode take_action$132;
   static final PyCode consume_optional$133;
   static final PyCode consume_positionals$134;
   static final PyCode _read_args_from_files$135;
   static final PyCode convert_arg_line_to_args$136;
   static final PyCode _match_argument$137;
   static final PyCode _match_arguments_partial$138;
   static final PyCode _parse_optional$139;
   static final PyCode _get_option_tuples$140;
   static final PyCode _get_nargs_pattern$141;
   static final PyCode _get_values$142;
   static final PyCode _get_value$143;
   static final PyCode _check_value$144;
   static final PyCode format_usage$145;
   static final PyCode format_help$146;
   static final PyCode format_version$147;
   static final PyCode _get_formatter$148;
   static final PyCode print_usage$149;
   static final PyCode print_help$150;
   static final PyCode print_version$151;
   static final PyCode _print_message$152;
   static final PyCode exit$153;
   static final PyCode error$154;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Command-line parsing library\n\nThis module is an optparse-inspired command-line parsing library that:\n\n    - handles both optional and positional arguments\n    - produces highly informative usage messages\n    - supports parsers that dispatch to sub-parsers\n\nThe following is a simple usage example that sums integers from the\ncommand-line and writes the result to a file::\n\n    parser = argparse.ArgumentParser(\n        description='sum the integers at the command line')\n    parser.add_argument(\n        'integers', metavar='int', nargs='+', type=int,\n        help='an integer to be summed')\n    parser.add_argument(\n        '--log', default=sys.stdout, type=argparse.FileType('w'),\n        help='the file where the sum should be written')\n    args = parser.parse_args()\n    args.log.write('%s' % sum(args.integers))\n    args.log.close()\n\nThe module contains the following public classes:\n\n    - ArgumentParser -- The main entry point for command-line parsing. As the\n        example above shows, the add_argument() method is used to populate\n        the parser with actions for optional and positional arguments. Then\n        the parse_args() method is invoked to convert the args at the\n        command-line into an object with attributes.\n\n    - ArgumentError -- The exception raised by ArgumentParser objects when\n        there are errors with the parser's actions. Errors raised while\n        parsing the command-line are caught by ArgumentParser and emitted\n        as command-line messages.\n\n    - FileType -- A factory for defining types of files to be created. As the\n        example above shows, instances of FileType are typically passed as\n        the type= argument of add_argument() calls.\n\n    - Action -- The base class for parser actions. Typically actions are\n        selected by passing strings like 'store_true' or 'append_const' to\n        the action= argument of add_argument(). However, for greater\n        customization of ArgumentParser actions, subclasses of Action may\n        be defined and passed as the action= argument.\n\n    - HelpFormatter, RawDescriptionHelpFormatter, RawTextHelpFormatter,\n        ArgumentDefaultsHelpFormatter -- Formatter classes which\n        may be passed as the formatter_class= argument to the\n        ArgumentParser constructor. HelpFormatter is the default,\n        RawDescriptionHelpFormatter and RawTextHelpFormatter tell the parser\n        not to change the formatting for help text, and\n        ArgumentDefaultsHelpFormatter adds information about argument defaults\n        to the help.\n\nAll other classes in this module are considered implementation details.\n(Also note that HelpFormatter and RawDescriptionHelpFormatter are only\nconsidered public as object names -- the API of the formatter objects is\nstill considered an implementation detail.)\n"));
      var1.setline(62);
      PyString.fromInterned("Command-line parsing library\n\nThis module is an optparse-inspired command-line parsing library that:\n\n    - handles both optional and positional arguments\n    - produces highly informative usage messages\n    - supports parsers that dispatch to sub-parsers\n\nThe following is a simple usage example that sums integers from the\ncommand-line and writes the result to a file::\n\n    parser = argparse.ArgumentParser(\n        description='sum the integers at the command line')\n    parser.add_argument(\n        'integers', metavar='int', nargs='+', type=int,\n        help='an integer to be summed')\n    parser.add_argument(\n        '--log', default=sys.stdout, type=argparse.FileType('w'),\n        help='the file where the sum should be written')\n    args = parser.parse_args()\n    args.log.write('%s' % sum(args.integers))\n    args.log.close()\n\nThe module contains the following public classes:\n\n    - ArgumentParser -- The main entry point for command-line parsing. As the\n        example above shows, the add_argument() method is used to populate\n        the parser with actions for optional and positional arguments. Then\n        the parse_args() method is invoked to convert the args at the\n        command-line into an object with attributes.\n\n    - ArgumentError -- The exception raised by ArgumentParser objects when\n        there are errors with the parser's actions. Errors raised while\n        parsing the command-line are caught by ArgumentParser and emitted\n        as command-line messages.\n\n    - FileType -- A factory for defining types of files to be created. As the\n        example above shows, instances of FileType are typically passed as\n        the type= argument of add_argument() calls.\n\n    - Action -- The base class for parser actions. Typically actions are\n        selected by passing strings like 'store_true' or 'append_const' to\n        the action= argument of add_argument(). However, for greater\n        customization of ArgumentParser actions, subclasses of Action may\n        be defined and passed as the action= argument.\n\n    - HelpFormatter, RawDescriptionHelpFormatter, RawTextHelpFormatter,\n        ArgumentDefaultsHelpFormatter -- Formatter classes which\n        may be passed as the formatter_class= argument to the\n        ArgumentParser constructor. HelpFormatter is the default,\n        RawDescriptionHelpFormatter and RawTextHelpFormatter tell the parser\n        not to change the formatting for help text, and\n        ArgumentDefaultsHelpFormatter adds information about argument defaults\n        to the help.\n\nAll other classes in this module are considered implementation details.\n(Also note that HelpFormatter and RawDescriptionHelpFormatter are only\nconsidered public as object names -- the API of the formatter objects is\nstill considered an implementation detail.)\n");
      var1.setline(64);
      PyString var3 = PyString.fromInterned("1.1");
      var1.setlocal("__version__", var3);
      var3 = null;
      var1.setline(65);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("ArgumentParser"), PyString.fromInterned("ArgumentError"), PyString.fromInterned("ArgumentTypeError"), PyString.fromInterned("FileType"), PyString.fromInterned("HelpFormatter"), PyString.fromInterned("ArgumentDefaultsHelpFormatter"), PyString.fromInterned("RawDescriptionHelpFormatter"), PyString.fromInterned("RawTextHelpFormatter"), PyString.fromInterned("Namespace"), PyString.fromInterned("Action"), PyString.fromInterned("ONE_OR_MORE"), PyString.fromInterned("OPTIONAL"), PyString.fromInterned("PARSER"), PyString.fromInterned("REMAINDER"), PyString.fromInterned("SUPPRESS"), PyString.fromInterned("ZERO_OR_MORE")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(85);
      PyObject var6 = imp.importOneAs("collections", var1, -1);
      var1.setlocal("_collections", var6);
      var3 = null;
      var1.setline(86);
      var6 = imp.importOneAs("copy", var1, -1);
      var1.setlocal("_copy", var6);
      var3 = null;
      var1.setline(87);
      var6 = imp.importOneAs("os", var1, -1);
      var1.setlocal("_os", var6);
      var3 = null;
      var1.setline(88);
      var6 = imp.importOneAs("re", var1, -1);
      var1.setlocal("_re", var6);
      var3 = null;
      var1.setline(89);
      var6 = imp.importOneAs("sys", var1, -1);
      var1.setlocal("_sys", var6);
      var3 = null;
      var1.setline(90);
      var6 = imp.importOneAs("textwrap", var1, -1);
      var1.setlocal("_textwrap", var6);
      var3 = null;
      var1.setline(92);
      String[] var7 = new String[]{"gettext"};
      PyObject[] var8 = imp.importFrom("gettext", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("_", var4);
      var4 = null;
      var1.setline(95);
      var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, _callable$1, (PyObject)null);
      var1.setlocal("_callable", var9);
      var3 = null;
      var1.setline(99);
      var3 = PyString.fromInterned("==SUPPRESS==");
      var1.setlocal("SUPPRESS", var3);
      var3 = null;
      var1.setline(101);
      var3 = PyString.fromInterned("?");
      var1.setlocal("OPTIONAL", var3);
      var3 = null;
      var1.setline(102);
      var3 = PyString.fromInterned("*");
      var1.setlocal("ZERO_OR_MORE", var3);
      var3 = null;
      var1.setline(103);
      var3 = PyString.fromInterned("+");
      var1.setlocal("ONE_OR_MORE", var3);
      var3 = null;
      var1.setline(104);
      var3 = PyString.fromInterned("A...");
      var1.setlocal("PARSER", var3);
      var3 = null;
      var1.setline(105);
      var3 = PyString.fromInterned("...");
      var1.setlocal("REMAINDER", var3);
      var3 = null;
      var1.setline(106);
      var3 = PyString.fromInterned("_unrecognized_args");
      var1.setlocal("_UNRECOGNIZED_ARGS_ATTR", var3);
      var3 = null;
      var1.setline(112);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_AttributeHolder", var8, _AttributeHolder$2);
      var1.setlocal("_AttributeHolder", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(137);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _ensure_value$6, (PyObject)null);
      var1.setlocal("_ensure_value", var9);
      var3 = null;
      var1.setline(147);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("HelpFormatter", var8, HelpFormatter$7);
      var1.setlocal("HelpFormatter", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(629);
      var8 = new PyObject[]{var1.getname("HelpFormatter")};
      var4 = Py.makeClass("RawDescriptionHelpFormatter", var8, RawDescriptionHelpFormatter$37);
      var1.setlocal("RawDescriptionHelpFormatter", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(640);
      var8 = new PyObject[]{var1.getname("RawDescriptionHelpFormatter")};
      var4 = Py.makeClass("RawTextHelpFormatter", var8, RawTextHelpFormatter$39);
      var1.setlocal("RawTextHelpFormatter", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(651);
      var8 = new PyObject[]{var1.getname("HelpFormatter")};
      var4 = Py.makeClass("ArgumentDefaultsHelpFormatter", var8, ArgumentDefaultsHelpFormatter$41);
      var1.setlocal("ArgumentDefaultsHelpFormatter", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(672);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _get_action_name$43, (PyObject)null);
      var1.setlocal("_get_action_name", var9);
      var3 = null;
      var1.setline(685);
      var8 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("ArgumentError", var8, ArgumentError$44);
      var1.setlocal("ArgumentError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(705);
      var8 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("ArgumentTypeError", var8, ArgumentTypeError$47);
      var1.setlocal("ArgumentTypeError", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(714);
      var8 = new PyObject[]{var1.getname("_AttributeHolder")};
      var4 = Py.makeClass("Action", var8, Action$48);
      var1.setlocal("Action", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(805);
      var8 = new PyObject[]{var1.getname("Action")};
      var4 = Py.makeClass("_StoreAction", var8, _StoreAction$52);
      var1.setlocal("_StoreAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(840);
      var8 = new PyObject[]{var1.getname("Action")};
      var4 = Py.makeClass("_StoreConstAction", var8, _StoreConstAction$55);
      var1.setlocal("_StoreConstAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(863);
      var8 = new PyObject[]{var1.getname("_StoreConstAction")};
      var4 = Py.makeClass("_StoreTrueAction", var8, _StoreTrueAction$58);
      var1.setlocal("_StoreTrueAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(880);
      var8 = new PyObject[]{var1.getname("_StoreConstAction")};
      var4 = Py.makeClass("_StoreFalseAction", var8, _StoreFalseAction$60);
      var1.setlocal("_StoreFalseAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(897);
      var8 = new PyObject[]{var1.getname("Action")};
      var4 = Py.makeClass("_AppendAction", var8, _AppendAction$62);
      var1.setlocal("_AppendAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(934);
      var8 = new PyObject[]{var1.getname("Action")};
      var4 = Py.makeClass("_AppendConstAction", var8, _AppendConstAction$65);
      var1.setlocal("_AppendConstAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(960);
      var8 = new PyObject[]{var1.getname("Action")};
      var4 = Py.makeClass("_CountAction", var8, _CountAction$68);
      var1.setlocal("_CountAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(981);
      var8 = new PyObject[]{var1.getname("Action")};
      var4 = Py.makeClass("_HelpAction", var8, _HelpAction$71);
      var1.setlocal("_HelpAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1000);
      var8 = new PyObject[]{var1.getname("Action")};
      var4 = Py.makeClass("_VersionAction", var8, _VersionAction$74);
      var1.setlocal("_VersionAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1025);
      var8 = new PyObject[]{var1.getname("Action")};
      var4 = Py.makeClass("_SubParsersAction", var8, _SubParsersAction$77);
      var1.setlocal("_SubParsersAction", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1109);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("FileType", var8, FileType$84);
      var1.setlocal("FileType", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1153);
      var8 = new PyObject[]{var1.getname("_AttributeHolder")};
      var4 = Py.makeClass("Namespace", var8, Namespace$89);
      var1.setlocal("Namespace", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1180);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_ActionsContainer", var8, _ActionsContainer$94);
      var1.setlocal("_ActionsContainer", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1484);
      var8 = new PyObject[]{var1.getname("_ActionsContainer")};
      var4 = Py.makeClass("_ArgumentGroup", var8, _ArgumentGroup$113);
      var1.setlocal("_ArgumentGroup", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1518);
      var8 = new PyObject[]{var1.getname("_ArgumentGroup")};
      var4 = Py.makeClass("_MutuallyExclusiveGroup", var8, _MutuallyExclusiveGroup$117);
      var1.setlocal("_MutuallyExclusiveGroup", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(1538);
      var8 = new PyObject[]{var1.getname("_AttributeHolder"), var1.getname("_ActionsContainer")};
      var4 = Py.makeClass("ArgumentParser", var8, ArgumentParser$121);
      var1.setlocal("ArgumentParser", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _callable$1(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__call__"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__bases__"));
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _AttributeHolder$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Abstract base class that provides __repr__.\n\n    The __repr__ method returns a string in the format::\n        ClassName(attr=name, attr=name, ...)\n    The attributes are determined either by a class-level attribute,\n    '_kwarg_names', or by inspecting the instance __dict__.\n    "));
      var1.setline(119);
      PyString.fromInterned("Abstract base class that provides __repr__.\n\n    The __repr__ method returns a string in the format::\n        ClassName(attr=name, attr=name, ...)\n    The attributes are determined either by a class-level attribute,\n    '_kwarg_names', or by inspecting the instance __dict__.\n    ");
      var1.setline(121);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __repr__$3, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_kwargs$4, (PyObject)null);
      var1.setlocal("_get_kwargs", var4);
      var3 = null;
      var1.setline(133);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_args$5, (PyObject)null);
      var1.setlocal("_get_args", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __repr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0)).__getattr__("__name__");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(123);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(124);
      var3 = var1.getlocal(0).__getattr__("_get_args").__call__(var2).__iter__();

      while(true) {
         var1.setline(124);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(126);
            var3 = var1.getlocal(0).__getattr__("_get_kwargs").__call__(var2).__iter__();

            while(true) {
               var1.setline(126);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(128);
                  var3 = PyString.fromInterned("%s(%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(2))}));
                  var1.f_lasti = -1;
                  return var3;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(4, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(5, var6);
               var6 = null;
               var1.setline(127);
               var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("%s=%r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
            }
         }

         var1.setlocal(3, var4);
         var1.setline(125);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(3)));
      }
   }

   public PyObject _get_kwargs$4(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getglobal("sorted").__call__(var2, var1.getlocal(0).__getattr__("__dict__").__getattr__("items").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_args$5(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ensure_value$6(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("None"));
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(139);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      }

      var1.setline(140);
      var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject HelpFormatter$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Formatter for generating usage messages and argument help strings.\n\n    Only the name of this class is considered a public API. All the methods\n    provided by the class are considered an implementation detail.\n    "));
      var1.setline(152);
      PyString.fromInterned("Formatter for generating usage messages and argument help strings.\n\n    Only the name of this class is considered a public API. All the methods\n    provided by the class are considered an implementation detail.\n    ");
      var1.setline(154);
      PyObject[] var3 = new PyObject[]{Py.newInteger(2), Py.newInteger(24), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var3, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(188);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _indent$9, (PyObject)null);
      var1.setlocal("_indent", var5);
      var3 = null;
      var1.setline(192);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _dedent$10, (PyObject)null);
      var1.setlocal("_dedent", var5);
      var3 = null;
      var1.setline(197);
      var3 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("_Section", var3, _Section$11);
      var1.setlocal("_Section", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(230);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _add_item$14, (PyObject)null);
      var1.setlocal("_add_item", var5);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, start_section$15, (PyObject)null);
      var1.setlocal("start_section", var5);
      var3 = null;
      var1.setline(242);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, end_section$16, (PyObject)null);
      var1.setlocal("end_section", var5);
      var3 = null;
      var1.setline(246);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, add_text$17, (PyObject)null);
      var1.setlocal("add_text", var5);
      var3 = null;
      var1.setline(250);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, add_usage$18, (PyObject)null);
      var1.setlocal("add_usage", var5);
      var3 = null;
      var1.setline(255);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, add_argument$19, (PyObject)null);
      var1.setlocal("add_argument", var5);
      var3 = null;
      var1.setline(273);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, add_arguments$20, (PyObject)null);
      var1.setlocal("add_arguments", var5);
      var3 = null;
      var1.setline(280);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, format_help$21, (PyObject)null);
      var1.setlocal("format_help", var5);
      var3 = null;
      var1.setline(287);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _join_parts$22, (PyObject)null);
      var1.setlocal("_join_parts", var5);
      var3 = null;
      var1.setline(292);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _format_usage$23, (PyObject)null);
      var1.setlocal("_format_usage", var5);
      var3 = null;
      var1.setline(384);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _format_actions_usage$25, (PyObject)null);
      var1.setlocal("_format_actions_usage", var5);
      var3 = null;
      var1.setline(480);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _format_text$26, (PyObject)null);
      var1.setlocal("_format_text", var5);
      var3 = null;
      var1.setline(487);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _format_action$27, (PyObject)null);
      var1.setlocal("_format_action", var5);
      var3 = null;
      var1.setline(534);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _format_action_invocation$28, (PyObject)null);
      var1.setlocal("_format_action_invocation", var5);
      var3 = null;
      var1.setline(557);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _metavar_formatter$29, (PyObject)null);
      var1.setlocal("_metavar_formatter", var5);
      var3 = null;
      var1.setline(573);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _format_args$31, (PyObject)null);
      var1.setlocal("_format_args", var5);
      var3 = null;
      var1.setline(592);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _expand_help$32, (PyObject)null);
      var1.setlocal("_expand_help", var5);
      var3 = null;
      var1.setline(605);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _iter_indented_subactions$33, (PyObject)null);
      var1.setlocal("_iter_indented_subactions", var5);
      var3 = null;
      var1.setline(616);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _split_lines$34, (PyObject)null);
      var1.setlocal("_split_lines", var5);
      var3 = null;
      var1.setline(620);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _fill_text$35, (PyObject)null);
      var1.setlocal("_fill_text", var5);
      var3 = null;
      var1.setline(625);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _get_help_string$36, (PyObject)null);
      var1.setlocal("_get_help_string", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(163);
            var3 = var1.getglobal("int").__call__(var2, var1.getglobal("_os").__getattr__("environ").__getitem__(PyString.fromInterned("COLUMNS")));
            var1.setlocal(4, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(new PyTuple(new PyObject[]{var1.getglobal("KeyError"), var1.getglobal("ValueError")}))) {
               throw var6;
            }

            var1.setline(165);
            PyInteger var4 = Py.newInteger(80);
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(166);
         var3 = var1.getlocal(4);
         var3 = var3._isub(Py.newInteger(2));
         var1.setlocal(4, var3);
      }

      var1.setline(168);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_prog", var3);
      var3 = null;
      var1.setline(169);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_indent_increment", var3);
      var3 = null;
      var1.setline(170);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_max_help_position", var3);
      var3 = null;
      var1.setline(171);
      var3 = var1.getglobal("min").__call__(var2, var1.getlocal(3), var1.getglobal("max").__call__(var2, var1.getlocal(4)._sub(Py.newInteger(20)), var1.getlocal(2)._mul(Py.newInteger(2))));
      var1.getlocal(0).__setattr__("_max_help_position", var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_width", var3);
      var3 = null;
      var1.setline(175);
      PyInteger var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_current_indent", var7);
      var3 = null;
      var1.setline(176);
      var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_level", var7);
      var3 = null;
      var1.setline(177);
      var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"_action_max_length", var7);
      var3 = null;
      var1.setline(179);
      var3 = var1.getlocal(0).__getattr__("_Section").__call__(var2, var1.getlocal(0), var1.getglobal("None"));
      var1.getlocal(0).__setattr__("_root_section", var3);
      var3 = null;
      var1.setline(180);
      var3 = var1.getlocal(0).__getattr__("_root_section");
      var1.getlocal(0).__setattr__("_current_section", var3);
      var3 = null;
      var1.setline(182);
      var3 = var1.getglobal("_re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+"));
      var1.getlocal(0).__setattr__("_whitespace_matcher", var3);
      var3 = null;
      var1.setline(183);
      var3 = var1.getglobal("_re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\n\\n\\n+"));
      var1.getlocal(0).__setattr__("_long_break_matcher", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _indent$9(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "_current_indent";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(var1.getlocal(0).__getattr__("_indent_increment"));
      var4.__setattr__(var3, var5);
      var1.setline(190);
      var10000 = var1.getlocal(0);
      var3 = "_level";
      var4 = var10000;
      var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _dedent$10(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "_current_indent";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._isub(var1.getlocal(0).__getattr__("_indent_increment"));
      var4.__setattr__(var3, var5);
      var1.setline(194);
      if (var1.getglobal("__debug__").__nonzero__()) {
         PyObject var6 = var1.getlocal(0).__getattr__("_current_indent");
         var10000 = var6._ge(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Indent decreased below 0."));
         }
      }

      var1.setline(195);
      var10000 = var1.getlocal(0);
      var3 = "_level";
      var4 = var10000;
      var5 = var4.__getattr__(var3);
      var5 = var5._isub(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Section$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(199);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$12, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(205);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_help$13, (PyObject)null);
      var1.setlocal("format_help", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$12(PyFrame var1, ThreadState var2) {
      var1.setline(200);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("formatter", var3);
      var3 = null;
      var1.setline(201);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("parent", var3);
      var3 = null;
      var1.setline(202);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("heading", var3);
      var3 = null;
      var1.setline(203);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"items", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_help$13(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      PyObject var3 = var1.getlocal(0).__getattr__("parent");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(208);
         var1.getlocal(0).__getattr__("formatter").__getattr__("_indent").__call__(var2);
      }

      var1.setline(209);
      var3 = var1.getlocal(0).__getattr__("formatter").__getattr__("_join_parts");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(210);
      var3 = var1.getlocal(0).__getattr__("items").__iter__();

      while(true) {
         var1.setline(210);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var6;
         String[] var7;
         if (var4 == null) {
            var1.setline(212);
            var10000 = var1.getlocal(1);
            PyList var10002 = new PyList();
            var3 = var10002.__getattr__("append");
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(212);
            var3 = var1.getlocal(0).__getattr__("items").__iter__();

            while(true) {
               var1.setline(212);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(212);
                  var1.dellocal(5);
                  var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(213);
                  var3 = var1.getlocal(0).__getattr__("parent");
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(214);
                     var1.getlocal(0).__getattr__("formatter").__getattr__("_dedent").__call__(var2);
                  }

                  var1.setline(217);
                  if (var1.getlocal(4).__not__().__nonzero__()) {
                     var1.setline(218);
                     PyString var8 = PyString.fromInterned("");
                     var1.f_lasti = -1;
                     return var8;
                  } else {
                     var1.setline(221);
                     var4 = var1.getlocal(0).__getattr__("heading");
                     var10000 = var4._isnot(var1.getglobal("SUPPRESS"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getlocal(0).__getattr__("heading");
                        var10000 = var4._isnot(var1.getglobal("None"));
                        var4 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(222);
                        var4 = var1.getlocal(0).__getattr__("formatter").__getattr__("_current_indent");
                        var1.setlocal(6, var4);
                        var4 = null;
                        var1.setline(223);
                        var4 = PyString.fromInterned("%*s%s:\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), PyString.fromInterned(""), var1.getlocal(0).__getattr__("heading")}));
                        var1.setlocal(7, var4);
                        var4 = null;
                     } else {
                        var1.setline(225);
                        PyString var9 = PyString.fromInterned("");
                        var1.setlocal(7, var9);
                        var4 = null;
                     }

                     var1.setline(228);
                     var3 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("\n"), var1.getlocal(7), var1.getlocal(4), PyString.fromInterned("\n")})));
                     var1.f_lasti = -1;
                     return var3;
                  }
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(212);
               PyObject var10003 = var1.getlocal(5);
               PyObject var10005 = var1.getlocal(2);
               var5 = Py.EmptyObjects;
               var7 = new String[0];
               var10005 = var10005._callextra(var5, var7, var1.getlocal(3), (PyObject)null);
               var5 = null;
               var10003.__call__(var2, var10005);
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(211);
         var10000 = var1.getlocal(2);
         var5 = Py.EmptyObjects;
         var7 = new String[0];
         var10000._callextra(var5, var7, var1.getlocal(3), (PyObject)null);
         var5 = null;
      }
   }

   public PyObject _add_item$14(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      var1.getlocal(0).__getattr__("_current_section").__getattr__("items").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_section$15(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      var1.getlocal(0).__getattr__("_indent").__call__(var2);
      var1.setline(238);
      PyObject var3 = var1.getlocal(0).__getattr__("_Section").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("_current_section"), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(239);
      var1.getlocal(0).__getattr__("_add_item").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("format_help"), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(240);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_current_section", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_section$16(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      PyObject var3 = var1.getlocal(0).__getattr__("_current_section").__getattr__("parent");
      var1.getlocal(0).__setattr__("_current_section", var3);
      var3 = null;
      var1.setline(244);
      var1.getlocal(0).__getattr__("_dedent").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_text$17(PyFrame var1, ThreadState var2) {
      var1.setline(247);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("SUPPRESS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(248);
         var1.getlocal(0).__getattr__("_add_item").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_format_text"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_usage$18(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("SUPPRESS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(252);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(253);
         var1.getlocal(0).__getattr__("_add_item").__call__(var2, var1.getlocal(0).__getattr__("_format_usage"), var1.getlocal(5));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_argument$19(PyFrame var1, ThreadState var2) {
      var1.setline(256);
      PyObject var3 = var1.getlocal(1).__getattr__("help");
      PyObject var10000 = var3._isnot(var1.getglobal("SUPPRESS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(259);
         var3 = var1.getlocal(0).__getattr__("_format_action_invocation");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(260);
         PyList var5 = new PyList(new PyObject[]{var1.getlocal(2).__call__(var2, var1.getlocal(1))});
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(261);
         var3 = var1.getlocal(0).__getattr__("_iter_indented_subactions").__call__(var2, var1.getlocal(1)).__iter__();

         label22:
         while(true) {
            var1.setline(261);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(265);
               var10000 = var1.getglobal("max");
               PyList var10002 = new PyList();
               var3 = var10002.__getattr__("append");
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(265);
               var3 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(265);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(265);
                     var1.dellocal(6);
                     var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                     var1.setlocal(5, var3);
                     var3 = null;
                     var1.setline(266);
                     var3 = var1.getlocal(5)._add(var1.getlocal(0).__getattr__("_current_indent"));
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(267);
                     var3 = var1.getglobal("max").__call__(var2, var1.getlocal(0).__getattr__("_action_max_length"), var1.getlocal(8));
                     var1.getlocal(0).__setattr__("_action_max_length", var3);
                     var3 = null;
                     var1.setline(271);
                     var1.getlocal(0).__getattr__("_add_item").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_format_action"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
                     break label22;
                  }

                  var1.setlocal(7, var4);
                  var1.setline(265);
                  var1.getlocal(6).__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(7)));
               }
            }

            var1.setlocal(4, var4);
            var1.setline(262);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(2).__call__(var2, var1.getlocal(4)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_arguments$20(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(274);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(275);
         var1.getlocal(0).__getattr__("add_argument").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject format_help$21(PyFrame var1, ThreadState var2) {
      var1.setline(281);
      PyObject var3 = var1.getlocal(0).__getattr__("_root_section").__getattr__("format_help").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(282);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(283);
         var3 = var1.getlocal(0).__getattr__("_long_break_matcher").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n\n"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(284);
         var3 = var1.getlocal(1).__getattr__("strip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"))._add(PyString.fromInterned("\n"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(285);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _join_parts$22(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyObject var10000 = PyString.fromInterned("").__getattr__("join");
      PyList var10002 = new PyList();
      PyObject var3 = var10002.__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(289);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(289);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(289);
            var1.dellocal(2);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(290);
         PyObject var10003 = var1.getlocal(3);
         if (var10003.__nonzero__()) {
            PyObject var5 = var1.getlocal(3);
            var10003 = var5._isnot(var1.getglobal("SUPPRESS"));
            var5 = null;
         }

         if (var10003.__nonzero__()) {
            var1.setline(288);
            var1.getlocal(2).__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject _format_usage$23(PyFrame var1, ThreadState var2) {
      var1.setline(293);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(294);
         var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("usage: "));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(297);
      var3 = var1.getlocal(1);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      String[] var4;
      PyObject[] var5;
      PyObject var10001;
      if (var10000.__nonzero__()) {
         var1.setline(298);
         var10000 = var1.getlocal(1);
         var10001 = var1.getglobal("dict");
         var5 = new PyObject[]{var1.getlocal(0).__getattr__("_prog")};
         var4 = new String[]{"prog"};
         var10001 = var10001.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000._mod(var10001);
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(301);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__not__();
         }

         PyString var10;
         if (var10000.__nonzero__()) {
            var1.setline(302);
            var10 = PyString.fromInterned("%(prog)s");
            var10001 = var1.getglobal("dict");
            var5 = new PyObject[]{var1.getlocal(0).__getattr__("_prog")};
            var4 = new String[]{"prog"};
            var10001 = var10001.__call__(var2, var5, var4);
            var3 = null;
            var3 = var10._mod(var10001);
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(305);
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(306);
               var10 = PyString.fromInterned("%(prog)s");
               var10001 = var1.getglobal("dict");
               var5 = new PyObject[]{var1.getlocal(0).__getattr__("_prog")};
               var4 = new String[]{"prog"};
               var10001 = var10001.__call__(var2, var5, var4);
               var3 = null;
               var3 = var10._mod(var10001);
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(309);
               PyList var7 = new PyList(Py.EmptyObjects);
               var1.setlocal(6, var7);
               var3 = null;
               var1.setline(310);
               var7 = new PyList(Py.EmptyObjects);
               var1.setlocal(7, var7);
               var3 = null;
               var1.setline(311);
               var3 = var1.getlocal(2).__iter__();

               label68:
               while(true) {
                  var1.setline(311);
                  PyObject var6 = var3.__iternext__();
                  if (var6 == null) {
                     var1.setline(318);
                     var3 = var1.getlocal(0).__getattr__("_format_actions_usage");
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(319);
                     var3 = var1.getlocal(9).__call__(var2, var1.getlocal(6)._add(var1.getlocal(7)), var1.getlocal(3));
                     var1.setlocal(10, var3);
                     var3 = null;
                     var1.setline(320);
                     var10000 = PyString.fromInterned(" ").__getattr__("join");
                     PyList var10002 = new PyList();
                     var3 = var10002.__getattr__("append");
                     var1.setlocal(11, var3);
                     var3 = null;
                     var1.setline(320);
                     var3 = (new PyList(new PyObject[]{var1.getlocal(5), var1.getlocal(10)})).__iter__();

                     while(true) {
                        var1.setline(320);
                        var6 = var3.__iternext__();
                        if (var6 == null) {
                           var1.setline(320);
                           var1.dellocal(11);
                           var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                           var1.setlocal(1, var3);
                           var3 = null;
                           var1.setline(323);
                           var3 = var1.getlocal(0).__getattr__("_width")._sub(var1.getlocal(0).__getattr__("_current_indent"));
                           var1.setderef(0, var3);
                           var3 = null;
                           var1.setline(324);
                           var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4))._add(var1.getglobal("len").__call__(var2, var1.getlocal(1)));
                           var10000 = var3._gt(var1.getderef(0));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(327);
                              PyString var8 = PyString.fromInterned("\\(.*?\\)+|\\[.*?\\]+|\\S+");
                              var1.setlocal(13, var8);
                              var3 = null;
                              var1.setline(328);
                              var3 = var1.getlocal(9).__call__(var2, var1.getlocal(6), var1.getlocal(3));
                              var1.setlocal(14, var3);
                              var3 = null;
                              var1.setline(329);
                              var3 = var1.getlocal(9).__call__(var2, var1.getlocal(7), var1.getlocal(3));
                              var1.setlocal(15, var3);
                              var3 = null;
                              var1.setline(330);
                              var3 = var1.getglobal("_re").__getattr__("findall").__call__(var2, var1.getlocal(13), var1.getlocal(14));
                              var1.setlocal(16, var3);
                              var3 = null;
                              var1.setline(331);
                              var3 = var1.getglobal("_re").__getattr__("findall").__call__(var2, var1.getlocal(13), var1.getlocal(15));
                              var1.setlocal(17, var3);
                              var3 = null;
                              var1.setline(332);
                              if (var1.getglobal("__debug__").__nonzero__()) {
                                 var3 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(16));
                                 var10000 = var3._eq(var1.getlocal(14));
                                 var3 = null;
                                 if (!var10000.__nonzero__()) {
                                    var10000 = Py.None;
                                    throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                                 }
                              }

                              var1.setline(333);
                              if (var1.getglobal("__debug__").__nonzero__()) {
                                 var3 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(17));
                                 var10000 = var3._eq(var1.getlocal(15));
                                 var3 = null;
                                 if (!var10000.__nonzero__()) {
                                    var10000 = Py.None;
                                    throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                                 }
                              }

                              var1.setline(336);
                              var5 = new PyObject[]{var1.getglobal("None")};
                              PyObject var11 = var1.f_globals;
                              PyObject[] var10003 = var5;
                              PyCode var10004 = get_lines$24;
                              var5 = new PyObject[]{var1.getclosure(0)};
                              PyFunction var9 = new PyFunction(var11, var10003, var10004, (PyObject)null, var5);
                              var1.setlocal(18, var9);
                              var3 = null;
                              var1.setline(357);
                              var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4))._add(var1.getglobal("len").__call__(var2, var1.getlocal(5)));
                              var10000 = var3._le(Py.newFloat(0.75)._mul(var1.getderef(0)));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(358);
                                 var3 = PyString.fromInterned(" ")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(4))._add(var1.getglobal("len").__call__(var2, var1.getlocal(5)))._add(Py.newInteger(1)));
                                 var1.setlocal(19, var3);
                                 var3 = null;
                                 var1.setline(359);
                                 if (var1.getlocal(16).__nonzero__()) {
                                    var1.setline(360);
                                    var3 = var1.getlocal(18).__call__(var2, (new PyList(new PyObject[]{var1.getlocal(5)}))._add(var1.getlocal(16)), var1.getlocal(19), var1.getlocal(4));
                                    var1.setlocal(20, var3);
                                    var3 = null;
                                    var1.setline(361);
                                    var1.getlocal(20).__getattr__("extend").__call__(var2, var1.getlocal(18).__call__(var2, var1.getlocal(17), var1.getlocal(19)));
                                 } else {
                                    var1.setline(362);
                                    if (var1.getlocal(17).__nonzero__()) {
                                       var1.setline(363);
                                       var3 = var1.getlocal(18).__call__(var2, (new PyList(new PyObject[]{var1.getlocal(5)}))._add(var1.getlocal(17)), var1.getlocal(19), var1.getlocal(4));
                                       var1.setlocal(20, var3);
                                       var3 = null;
                                    } else {
                                       var1.setline(365);
                                       var7 = new PyList(new PyObject[]{var1.getlocal(5)});
                                       var1.setlocal(20, var7);
                                       var3 = null;
                                    }
                                 }
                              } else {
                                 var1.setline(369);
                                 var3 = PyString.fromInterned(" ")._mul(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
                                 var1.setlocal(19, var3);
                                 var3 = null;
                                 var1.setline(370);
                                 var3 = var1.getlocal(16)._add(var1.getlocal(17));
                                 var1.setlocal(21, var3);
                                 var3 = null;
                                 var1.setline(371);
                                 var3 = var1.getlocal(18).__call__(var2, var1.getlocal(21), var1.getlocal(19));
                                 var1.setlocal(20, var3);
                                 var3 = null;
                                 var1.setline(372);
                                 var3 = var1.getglobal("len").__call__(var2, var1.getlocal(20));
                                 var10000 = var3._gt(Py.newInteger(1));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(373);
                                    var7 = new PyList(Py.EmptyObjects);
                                    var1.setlocal(20, var7);
                                    var3 = null;
                                    var1.setline(374);
                                    var1.getlocal(20).__getattr__("extend").__call__(var2, var1.getlocal(18).__call__(var2, var1.getlocal(16), var1.getlocal(19)));
                                    var1.setline(375);
                                    var1.getlocal(20).__getattr__("extend").__call__(var2, var1.getlocal(18).__call__(var2, var1.getlocal(17), var1.getlocal(19)));
                                 }

                                 var1.setline(376);
                                 var3 = (new PyList(new PyObject[]{var1.getlocal(5)}))._add(var1.getlocal(20));
                                 var1.setlocal(20, var3);
                                 var3 = null;
                              }

                              var1.setline(379);
                              var3 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(20));
                              var1.setlocal(1, var3);
                              var3 = null;
                           }
                           break label68;
                        }

                        var1.setlocal(12, var6);
                        var1.setline(320);
                        if (var1.getlocal(12).__nonzero__()) {
                           var1.setline(320);
                           var1.getlocal(11).__call__(var2, var1.getlocal(12));
                        }
                     }
                  }

                  var1.setlocal(8, var6);
                  var1.setline(312);
                  if (var1.getlocal(8).__getattr__("option_strings").__nonzero__()) {
                     var1.setline(313);
                     var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(8));
                  } else {
                     var1.setline(315);
                     var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(8));
                  }
               }
            }
         }
      }

      var1.setline(382);
      var3 = PyString.fromInterned("%s%s\n\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(1)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_lines$24(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(338);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(339);
      PyObject var6 = var1.getlocal(2);
      PyObject var10000 = var6._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(340);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(2))._sub(Py.newInteger(1));
         var1.setlocal(5, var6);
         var3 = null;
      } else {
         var1.setline(342);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1));
         var1.setlocal(5, var6);
         var3 = null;
      }

      var1.setline(343);
      var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(343);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(350);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(351);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(4))));
            }

            var1.setline(352);
            var6 = var1.getlocal(2);
            var10000 = var6._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(353);
               var6 = var1.getlocal(3).__getitem__(Py.newInteger(0)).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)null, (PyObject)null);
               var1.getlocal(3).__setitem__((PyObject)Py.newInteger(0), var6);
               var3 = null;
            }

            var1.setline(354);
            var6 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(6, var4);
         var1.setline(344);
         PyObject var5 = var1.getlocal(5)._add(Py.newInteger(1))._add(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
         var10000 = var5._gt(var1.getderef(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(4);
         }

         if (var10000.__nonzero__()) {
            var1.setline(345);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(4))));
            var1.setline(346);
            PyList var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(4, var7);
            var5 = null;
            var1.setline(347);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(1))._sub(Py.newInteger(1));
            var1.setlocal(5, var5);
            var5 = null;
         }

         var1.setline(348);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
         var1.setline(349);
         var5 = var1.getlocal(5);
         var5 = var5._iadd(var1.getglobal("len").__call__(var2, var1.getlocal(6))._add(Py.newInteger(1)));
         var1.setlocal(5, var5);
      }
   }

   public PyObject _format_actions_usage$25(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyObject var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(387);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(388);
      var3 = var1.getlocal(2).__iter__();

      label122:
      while(true) {
         var1.setline(388);
         PyObject var4 = var3.__iternext__();
         PyException var5;
         PyObject var6;
         PyObject var13;
         PyObject var10000;
         if (var4 == null) {
            var1.setline(414);
            PyList var11 = new PyList(Py.EmptyObjects);
            var1.setlocal(10, var11);
            var3 = null;
            var1.setline(415);
            var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

            while(true) {
               var1.setline(415);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(462);
                  var10000 = var1.getglobal("sorted");
                  PyObject[] var14 = new PyObject[]{var1.getlocal(4), var1.getglobal("True")};
                  String[] var12 = new String[]{"reverse"};
                  var10000 = var10000.__call__(var2, var14, var12);
                  var3 = null;
                  var3 = var10000.__iter__();

                  while(true) {
                     var1.setline(462);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(466);
                        var10000 = PyString.fromInterned(" ").__getattr__("join");
                        PyList var10002 = new PyList();
                        var3 = var10002.__getattr__("append");
                        var1.setlocal(16, var3);
                        var3 = null;
                        var1.setline(466);
                        var3 = var1.getlocal(10).__iter__();

                        while(true) {
                           var1.setline(466);
                           var4 = var3.__iternext__();
                           if (var4 == null) {
                              var1.setline(466);
                              var1.dellocal(16);
                              var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                              var1.setlocal(15, var3);
                              var3 = null;
                              var1.setline(469);
                              PyString var16 = PyString.fromInterned("[\\[(]");
                              var1.setlocal(18, var16);
                              var3 = null;
                              var1.setline(470);
                              var16 = PyString.fromInterned("[\\])]");
                              var1.setlocal(19, var16);
                              var3 = null;
                              var1.setline(471);
                              var3 = var1.getglobal("_re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("(%s) ")._mod(var1.getlocal(18)), (PyObject)PyString.fromInterned("\\1"), (PyObject)var1.getlocal(15));
                              var1.setlocal(15, var3);
                              var3 = null;
                              var1.setline(472);
                              var3 = var1.getglobal("_re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned(" (%s)")._mod(var1.getlocal(19)), (PyObject)PyString.fromInterned("\\1"), (PyObject)var1.getlocal(15));
                              var1.setlocal(15, var3);
                              var3 = null;
                              var1.setline(473);
                              var3 = var1.getglobal("_re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("%s *%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(18), var1.getlocal(19)})), (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(15));
                              var1.setlocal(15, var3);
                              var3 = null;
                              var1.setline(474);
                              var3 = var1.getglobal("_re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("\\(([^|]*)\\)"), (PyObject)PyString.fromInterned("\\1"), (PyObject)var1.getlocal(15));
                              var1.setlocal(15, var3);
                              var3 = null;
                              var1.setline(475);
                              var3 = var1.getlocal(15).__getattr__("strip").__call__(var2);
                              var1.setlocal(15, var3);
                              var3 = null;
                              var1.setline(478);
                              var3 = var1.getlocal(15);
                              var1.f_lasti = -1;
                              return var3;
                           }

                           var1.setlocal(17, var4);
                           var1.setline(466);
                           var13 = var1.getlocal(17);
                           PyObject var10003 = var13._isnot(var1.getglobal("None"));
                           var5 = null;
                           if (var10003.__nonzero__()) {
                              var1.setline(466);
                              var1.getlocal(16).__call__(var2, var1.getlocal(17));
                           }
                        }
                     }

                     var1.setlocal(9, var4);
                     var1.setline(463);
                     PyList var19 = new PyList(new PyObject[]{var1.getlocal(4).__getitem__(var1.getlocal(9))});
                     var1.getlocal(10).__setslice__(var1.getlocal(9), var1.getlocal(9), (PyObject)null, var19);
                     var5 = null;
                  }
               }

               PyObject[] var15 = Py.unpackSequence(var4, 2);
               var6 = var15[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var15[1];
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(419);
               var13 = var1.getlocal(8).__getattr__("help");
               var10000 = var13._is(var1.getglobal("SUPPRESS"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(420);
                  var1.getlocal(10).__getattr__("append").__call__(var2, var1.getglobal("None"));
                  var1.setline(421);
                  var13 = var1.getlocal(4).__getattr__("get").__call__(var2, var1.getlocal(9));
                  var10000 = var13._eq(PyString.fromInterned("|"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(422);
                     var1.getlocal(4).__getattr__("pop").__call__(var2, var1.getlocal(9));
                  } else {
                     var1.setline(423);
                     var13 = var1.getlocal(4).__getattr__("get").__call__(var2, var1.getlocal(9)._add(Py.newInteger(1)));
                     var10000 = var13._eq(PyString.fromInterned("|"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(424);
                        var1.getlocal(4).__getattr__("pop").__call__(var2, var1.getlocal(9)._add(Py.newInteger(1)));
                     }
                  }
               } else {
                  var1.setline(427);
                  if (var1.getlocal(8).__getattr__("option_strings").__not__().__nonzero__()) {
                     var1.setline(428);
                     var13 = var1.getlocal(0).__getattr__("_format_args").__call__(var2, var1.getlocal(8), var1.getlocal(8).__getattr__("dest"));
                     var1.setlocal(11, var13);
                     var5 = null;
                     var1.setline(431);
                     var13 = var1.getlocal(8);
                     var10000 = var13._in(var1.getlocal(3));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(432);
                        var13 = var1.getlocal(11).__getitem__(Py.newInteger(0));
                        var10000 = var13._eq(PyString.fromInterned("["));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var13 = var1.getlocal(11).__getitem__(Py.newInteger(-1));
                           var10000 = var13._eq(PyString.fromInterned("]"));
                           var5 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(433);
                           var13 = var1.getlocal(11).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null);
                           var1.setlocal(11, var13);
                           var5 = null;
                        }
                     }

                     var1.setline(436);
                     var1.getlocal(10).__getattr__("append").__call__(var2, var1.getlocal(11));
                  } else {
                     var1.setline(440);
                     var13 = var1.getlocal(8).__getattr__("option_strings").__getitem__(Py.newInteger(0));
                     var1.setlocal(12, var13);
                     var5 = null;
                     var1.setline(444);
                     var13 = var1.getlocal(8).__getattr__("nargs");
                     var10000 = var13._eq(Py.newInteger(0));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(445);
                        var13 = PyString.fromInterned("%s")._mod(var1.getlocal(12));
                        var1.setlocal(11, var13);
                        var5 = null;
                     } else {
                        var1.setline(450);
                        var13 = var1.getlocal(8).__getattr__("dest").__getattr__("upper").__call__(var2);
                        var1.setlocal(13, var13);
                        var5 = null;
                        var1.setline(451);
                        var13 = var1.getlocal(0).__getattr__("_format_args").__call__(var2, var1.getlocal(8), var1.getlocal(13));
                        var1.setlocal(14, var13);
                        var5 = null;
                        var1.setline(452);
                        var13 = PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(14)}));
                        var1.setlocal(11, var13);
                        var5 = null;
                     }

                     var1.setline(455);
                     var10000 = var1.getlocal(8).__getattr__("required").__not__();
                     if (var10000.__nonzero__()) {
                        var13 = var1.getlocal(8);
                        var10000 = var13._notin(var1.getlocal(3));
                        var5 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(456);
                        var13 = PyString.fromInterned("[%s]")._mod(var1.getlocal(11));
                        var1.setlocal(11, var13);
                        var5 = null;
                     }

                     var1.setline(459);
                     var1.getlocal(10).__getattr__("append").__call__(var2, var1.getlocal(11));
                  }
               }
            }
         }

         var1.setlocal(5, var4);

         try {
            var1.setline(390);
            var13 = var1.getlocal(1).__getattr__("index").__call__(var2, var1.getlocal(5).__getattr__("_group_actions").__getitem__(Py.newInteger(0)));
            var1.setlocal(6, var13);
            var5 = null;
         } catch (Throwable var9) {
            var5 = Py.setException(var9, var1);
            if (var5.match(var1.getglobal("ValueError"))) {
               continue;
            }

            throw var5;
         }

         var1.setline(394);
         var6 = var1.getlocal(6)._add(var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("_group_actions")));
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(395);
         var6 = var1.getlocal(1).__getslice__(var1.getlocal(6), var1.getlocal(7), (PyObject)null);
         var10000 = var6._eq(var1.getlocal(5).__getattr__("_group_actions"));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(396);
            var6 = var1.getlocal(5).__getattr__("_group_actions").__iter__();

            while(true) {
               var1.setline(396);
               PyObject var7 = var6.__iternext__();
               if (var7 == null) {
                  var1.setline(398);
                  PyObject var8;
                  PyString var17;
                  if (var1.getlocal(5).__getattr__("required").__not__().__nonzero__()) {
                     var1.setline(399);
                     var6 = var1.getlocal(6);
                     var10000 = var6._in(var1.getlocal(4));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(400);
                        var10000 = var1.getlocal(4);
                        var6 = var1.getlocal(6);
                        var7 = var10000;
                        var8 = var7.__getitem__(var6);
                        var8 = var8._iadd(PyString.fromInterned(" ["));
                        var7.__setitem__(var6, var8);
                     } else {
                        var1.setline(402);
                        var17 = PyString.fromInterned("[");
                        var1.getlocal(4).__setitem__((PyObject)var1.getlocal(6), var17);
                        var6 = null;
                     }

                     var1.setline(403);
                     var17 = PyString.fromInterned("]");
                     var1.getlocal(4).__setitem__((PyObject)var1.getlocal(7), var17);
                     var6 = null;
                  } else {
                     var1.setline(405);
                     var6 = var1.getlocal(6);
                     var10000 = var6._in(var1.getlocal(4));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(406);
                        var10000 = var1.getlocal(4);
                        var6 = var1.getlocal(6);
                        var7 = var10000;
                        var8 = var7.__getitem__(var6);
                        var8 = var8._iadd(PyString.fromInterned(" ("));
                        var7.__setitem__(var6, var8);
                     } else {
                        var1.setline(408);
                        var17 = PyString.fromInterned("(");
                        var1.getlocal(4).__setitem__((PyObject)var1.getlocal(6), var17);
                        var6 = null;
                     }

                     var1.setline(409);
                     var17 = PyString.fromInterned(")");
                     var1.getlocal(4).__setitem__((PyObject)var1.getlocal(7), var17);
                     var6 = null;
                  }

                  var1.setline(410);
                  var6 = var1.getglobal("range").__call__(var2, var1.getlocal(6)._add(Py.newInteger(1)), var1.getlocal(7)).__iter__();

                  while(true) {
                     var1.setline(410);
                     var7 = var6.__iternext__();
                     if (var7 == null) {
                        continue label122;
                     }

                     var1.setlocal(9, var7);
                     var1.setline(411);
                     PyString var18 = PyString.fromInterned("|");
                     var1.getlocal(4).__setitem__((PyObject)var1.getlocal(9), var18);
                     var8 = null;
                  }
               }

               var1.setlocal(8, var7);
               var1.setline(397);
               var1.getlocal(3).__getattr__("add").__call__(var2, var1.getlocal(8));
            }
         }
      }
   }

   public PyObject _format_text$26(PyFrame var1, ThreadState var2) {
      var1.setline(481);
      PyString var3 = PyString.fromInterned("%(prog)");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      PyObject var6;
      if (var10000.__nonzero__()) {
         var1.setline(482);
         var10000 = var1.getlocal(1);
         PyObject var10001 = var1.getglobal("dict");
         PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("_prog")};
         String[] var4 = new String[]{"prog"};
         var10001 = var10001.__call__(var2, var5, var4);
         var3 = null;
         var6 = var10000._mod(var10001);
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(483);
      var6 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_width")._sub(var1.getlocal(0).__getattr__("_current_indent")), (PyObject)Py.newInteger(11));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(484);
      var6 = PyString.fromInterned(" ")._mul(var1.getlocal(0).__getattr__("_current_indent"));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(485);
      var6 = var1.getlocal(0).__getattr__("_fill_text").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3))._add(PyString.fromInterned("\n\n"));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _format_action$27(PyFrame var1, ThreadState var2) {
      var1.setline(489);
      PyObject var3 = var1.getglobal("min").__call__(var2, var1.getlocal(0).__getattr__("_action_max_length")._add(Py.newInteger(2)), var1.getlocal(0).__getattr__("_max_help_position"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(491);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_width")._sub(var1.getlocal(2)), (PyObject)Py.newInteger(11));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(492);
      var3 = var1.getlocal(2)._sub(var1.getlocal(0).__getattr__("_current_indent"))._sub(Py.newInteger(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(493);
      var3 = var1.getlocal(0).__getattr__("_format_action_invocation").__call__(var2, var1.getlocal(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(496);
      PyTuple var5;
      if (var1.getlocal(1).__getattr__("help").__not__().__nonzero__()) {
         var1.setline(497);
         var5 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_current_indent"), PyString.fromInterned(""), var1.getlocal(5)});
         var1.setlocal(6, var5);
         var3 = null;
         var1.setline(498);
         var3 = PyString.fromInterned("%*s%s\n")._mod(var1.getlocal(6));
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(501);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
         PyObject var10000 = var3._le(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(502);
            var5 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_current_indent"), PyString.fromInterned(""), var1.getlocal(4), var1.getlocal(5)});
            var1.setlocal(6, var5);
            var3 = null;
            var1.setline(503);
            var3 = PyString.fromInterned("%*s%-*s  ")._mod(var1.getlocal(6));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(504);
            PyInteger var6 = Py.newInteger(0);
            var1.setlocal(7, var6);
            var3 = null;
         } else {
            var1.setline(508);
            var5 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_current_indent"), PyString.fromInterned(""), var1.getlocal(5)});
            var1.setlocal(6, var5);
            var3 = null;
            var1.setline(509);
            var3 = PyString.fromInterned("%*s%s\n")._mod(var1.getlocal(6));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(510);
            var3 = var1.getlocal(2);
            var1.setlocal(7, var3);
            var3 = null;
         }
      }

      var1.setline(513);
      PyList var7 = new PyList(new PyObject[]{var1.getlocal(5)});
      var1.setlocal(8, var7);
      var3 = null;
      var1.setline(516);
      PyObject var4;
      if (var1.getlocal(1).__getattr__("help").__nonzero__()) {
         var1.setline(517);
         var3 = var1.getlocal(0).__getattr__("_expand_help").__call__(var2, var1.getlocal(1));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(518);
         var3 = var1.getlocal(0).__getattr__("_split_lines").__call__(var2, var1.getlocal(9), var1.getlocal(3));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(519);
         var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("%*s%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), PyString.fromInterned(""), var1.getlocal(10).__getitem__(Py.newInteger(0))})));
         var1.setline(520);
         var3 = var1.getlocal(10).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(520);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(11, var4);
            var1.setline(521);
            var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("%*s%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned(""), var1.getlocal(11)})));
         }
      } else {
         var1.setline(524);
         if (var1.getlocal(5).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__().__nonzero__()) {
            var1.setline(525);
            var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         }
      }

      var1.setline(528);
      var3 = var1.getlocal(0).__getattr__("_iter_indented_subactions").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(528);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(532);
            var3 = var1.getlocal(0).__getattr__("_join_parts").__call__(var2, var1.getlocal(8));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(12, var4);
         var1.setline(529);
         var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("_format_action").__call__(var2, var1.getlocal(12)));
      }
   }

   public PyObject _format_action_invocation$28(PyFrame var1, ThreadState var2) {
      var1.setline(535);
      PyObject var3;
      PyObject var5;
      if (var1.getlocal(1).__getattr__("option_strings").__not__().__nonzero__()) {
         var1.setline(536);
         var3 = var1.getlocal(0).__getattr__("_metavar_formatter").__call__(var2, var1.getlocal(1), var1.getlocal(1).__getattr__("dest")).__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         PyObject[] var7 = Py.unpackSequence(var3, 1);
         var5 = var7[0];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(537);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(540);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(544);
         PyObject var6 = var1.getlocal(1).__getattr__("nargs");
         PyObject var10000 = var6._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(545);
            var1.getlocal(3).__getattr__("extend").__call__(var2, var1.getlocal(1).__getattr__("option_strings"));
         } else {
            var1.setline(550);
            var6 = var1.getlocal(1).__getattr__("dest").__getattr__("upper").__call__(var2);
            var1.setlocal(4, var6);
            var4 = null;
            var1.setline(551);
            var6 = var1.getlocal(0).__getattr__("_format_args").__call__(var2, var1.getlocal(1), var1.getlocal(4));
            var1.setlocal(5, var6);
            var4 = null;
            var1.setline(552);
            var6 = var1.getlocal(1).__getattr__("option_strings").__iter__();

            while(true) {
               var1.setline(552);
               var5 = var6.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(6, var5);
               var1.setline(553);
               var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5)})));
            }
         }

         var1.setline(555);
         var3 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _metavar_formatter$29(PyFrame var1, ThreadState var2) {
      var1.setline(558);
      PyObject var3 = var1.getlocal(1).__getattr__("metavar");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(559);
         var3 = var1.getlocal(1).__getattr__("metavar");
         var1.setderef(0, var3);
         var3 = null;
      } else {
         var1.setline(560);
         var3 = var1.getlocal(1).__getattr__("choices");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(561);
            PyList var8 = new PyList();
            var3 = var8.__getattr__("append");
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(561);
            var3 = var1.getlocal(1).__getattr__("choices").__iter__();

            while(true) {
               var1.setline(561);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(561);
                  var1.dellocal(4);
                  PyList var5 = var8;
                  var1.setlocal(3, var5);
                  var3 = null;
                  var1.setline(562);
                  var3 = PyString.fromInterned("{%s}")._mod(PyString.fromInterned(",").__getattr__("join").__call__(var2, var1.getlocal(3)));
                  var1.setderef(0, var3);
                  var3 = null;
                  break;
               }

               var1.setlocal(5, var4);
               var1.setline(561);
               var1.getlocal(4).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(5)));
            }
         } else {
            var1.setline(564);
            var3 = var1.getlocal(2);
            var1.setderef(0, var3);
            var3 = null;
         }
      }

      var1.setline(566);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var6;
      PyCode var10004 = format$30;
      var6 = new PyObject[]{var1.getclosure(0)};
      PyFunction var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var6);
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(571);
      var3 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format$30(PyFrame var1, ThreadState var2) {
      var1.setline(567);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getderef(0), var1.getglobal("tuple")).__nonzero__()) {
         var1.setline(568);
         var3 = var1.getderef(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(570);
         var3 = (new PyTuple(new PyObject[]{var1.getderef(0)}))._mul(var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _format_args$31(PyFrame var1, ThreadState var2) {
      var1.setline(574);
      PyObject var3 = var1.getlocal(0).__getattr__("_metavar_formatter").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(575);
      var3 = var1.getlocal(1).__getattr__("nargs");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(576);
         var3 = PyString.fromInterned("%s")._mod(var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(577);
         var3 = var1.getlocal(1).__getattr__("nargs");
         var10000 = var3._eq(var1.getglobal("OPTIONAL"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(578);
            var3 = PyString.fromInterned("[%s]")._mod(var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(579);
            var3 = var1.getlocal(1).__getattr__("nargs");
            var10000 = var3._eq(var1.getglobal("ZERO_OR_MORE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(580);
               var3 = PyString.fromInterned("[%s [%s ...]]")._mod(var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(2)));
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(581);
               var3 = var1.getlocal(1).__getattr__("nargs");
               var10000 = var3._eq(var1.getglobal("ONE_OR_MORE"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(582);
                  var3 = PyString.fromInterned("%s [%s ...]")._mod(var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(2)));
                  var1.setlocal(4, var3);
                  var3 = null;
               } else {
                  var1.setline(583);
                  var3 = var1.getlocal(1).__getattr__("nargs");
                  var10000 = var3._eq(var1.getglobal("REMAINDER"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(584);
                     PyString var5 = PyString.fromInterned("...");
                     var1.setlocal(4, var5);
                     var3 = null;
                  } else {
                     var1.setline(585);
                     var3 = var1.getlocal(1).__getattr__("nargs");
                     var10000 = var3._eq(var1.getglobal("PARSER"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(586);
                        var3 = PyString.fromInterned("%s ...")._mod(var1.getlocal(3).__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
                        var1.setlocal(4, var3);
                        var3 = null;
                     } else {
                        var1.setline(588);
                        PyList var7 = new PyList();
                        var3 = var7.__getattr__("append");
                        var1.setlocal(6, var3);
                        var3 = null;
                        var1.setline(588);
                        var3 = var1.getglobal("range").__call__(var2, var1.getlocal(1).__getattr__("nargs")).__iter__();

                        while(true) {
                           var1.setline(588);
                           PyObject var4 = var3.__iternext__();
                           if (var4 == null) {
                              var1.setline(588);
                              var1.dellocal(6);
                              PyList var6 = var7;
                              var1.setlocal(5, var6);
                              var3 = null;
                              var1.setline(589);
                              var3 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(5))._mod(var1.getlocal(3).__call__(var2, var1.getlocal(1).__getattr__("nargs")));
                              var1.setlocal(4, var3);
                              var3 = null;
                              break;
                           }

                           var1.setlocal(7, var4);
                           var1.setline(588);
                           var1.getlocal(6).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s"));
                        }
                     }
                  }
               }
            }
         }
      }

      var1.setline(590);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _expand_help$32(PyFrame var1, ThreadState var2) {
      var1.setline(593);
      PyObject var10000 = var1.getglobal("dict");
      PyObject[] var3 = new PyObject[]{var1.getglobal("vars").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("_prog")};
      String[] var4 = new String[]{"prog"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(594);
      var6 = var1.getglobal("list").__call__(var2, var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(594);
         PyObject var7 = var6.__iternext__();
         PyObject var5;
         if (var7 == null) {
            var1.setline(597);
            var6 = var1.getglobal("list").__call__(var2, var1.getlocal(2)).__iter__();

            while(true) {
               var1.setline(597);
               var7 = var6.__iternext__();
               if (var7 == null) {
                  var1.setline(600);
                  var6 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("choices"));
                  var10000 = var6._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(601);
                     var10000 = PyString.fromInterned(", ").__getattr__("join");
                     PyList var10002 = new PyList();
                     var6 = var10002.__getattr__("append");
                     var1.setlocal(5, var6);
                     var3 = null;
                     var1.setline(601);
                     var6 = var1.getlocal(2).__getitem__(PyString.fromInterned("choices")).__iter__();

                     while(true) {
                        var1.setline(601);
                        var7 = var6.__iternext__();
                        if (var7 == null) {
                           var1.setline(601);
                           var1.dellocal(5);
                           var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                           var1.setlocal(4, var6);
                           var3 = null;
                           var1.setline(602);
                           var6 = var1.getlocal(4);
                           var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("choices"), var6);
                           var3 = null;
                           break;
                        }

                        var1.setlocal(6, var7);
                        var1.setline(601);
                        var1.getlocal(5).__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(6)));
                     }
                  }

                  var1.setline(603);
                  var6 = var1.getlocal(0).__getattr__("_get_help_string").__call__(var2, var1.getlocal(1))._mod(var1.getlocal(2));
                  var1.f_lasti = -1;
                  return var6;
               }

               var1.setlocal(3, var7);
               var1.setline(598);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(var1.getlocal(3)), (PyObject)PyString.fromInterned("__name__")).__nonzero__()) {
                  var1.setline(599);
                  var5 = var1.getlocal(2).__getitem__(var1.getlocal(3)).__getattr__("__name__");
                  var1.getlocal(2).__setitem__(var1.getlocal(3), var5);
                  var5 = null;
               }
            }
         }

         var1.setlocal(3, var7);
         var1.setline(595);
         var5 = var1.getlocal(2).__getitem__(var1.getlocal(3));
         var10000 = var5._is(var1.getglobal("SUPPRESS"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(596);
            var1.getlocal(2).__delitem__(var1.getlocal(3));
         }
      }
   }

   public PyObject _iter_indented_subactions$33(PyFrame var1, ThreadState var2) {
      label34: {
         PyObject var4;
         PyObject var5;
         Object[] var6;
         PyObject var9;
         switch (var1.f_lasti) {
            case 0:
            default:
               PyException var3;
               try {
                  var1.setline(607);
                  PyObject var8 = var1.getlocal(1).__getattr__("_get_subactions");
                  var1.setlocal(2, var8);
                  var3 = null;
               } catch (Throwable var7) {
                  var3 = Py.setException(var7, var1);
                  if (var3.match(var1.getglobal("AttributeError"))) {
                     var1.setline(609);
                     break label34;
                  }

                  throw var3;
               }

               var1.setline(611);
               var1.getlocal(0).__getattr__("_indent").__call__(var2);
               var1.setline(612);
               var4 = var1.getlocal(2).__call__(var2).__iter__();
               break;
            case 1:
               var6 = var1.f_savedlocals;
               var4 = (PyObject)var6[4];
               var5 = (PyObject)var6[5];
               Object var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var9 = (PyObject)var10000;
         }

         var1.setline(612);
         var5 = var4.__iternext__();
         if (var5 != null) {
            var1.setlocal(3, var5);
            var1.setline(613);
            var1.setline(613);
            var9 = var1.getlocal(3);
            var1.f_lasti = 1;
            var6 = new Object[]{null, null, null, null, var4, var5};
            var1.f_savedlocals = var6;
            return var9;
         }

         var1.setline(614);
         var1.getlocal(0).__getattr__("_dedent").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _split_lines$34(PyFrame var1, ThreadState var2) {
      var1.setline(617);
      PyObject var3 = var1.getlocal(0).__getattr__("_whitespace_matcher").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)var1.getlocal(1)).__getattr__("strip").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(618);
      var3 = var1.getglobal("_textwrap").__getattr__("wrap").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _fill_text$35(PyFrame var1, ThreadState var2) {
      var1.setline(621);
      PyObject var3 = var1.getlocal(0).__getattr__("_whitespace_matcher").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)var1.getlocal(1)).__getattr__("strip").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(622);
      PyObject var10000 = var1.getglobal("_textwrap").__getattr__("fill");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(3)};
      String[] var4 = new String[]{"initial_indent", "subsequent_indent"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_help_string$36(PyFrame var1, ThreadState var2) {
      var1.setline(626);
      PyObject var3 = var1.getlocal(1).__getattr__("help");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject RawDescriptionHelpFormatter$37(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Help message formatter which retains any formatting in descriptions.\n\n    Only the name of this class is considered a public API. All the methods\n    provided by the class are considered an implementation detail.\n    "));
      var1.setline(634);
      PyString.fromInterned("Help message formatter which retains any formatting in descriptions.\n\n    Only the name of this class is considered a public API. All the methods\n    provided by the class are considered an implementation detail.\n    ");
      var1.setline(636);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _fill_text$38, (PyObject)null);
      var1.setlocal("_fill_text", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _fill_text$38(PyFrame var1, ThreadState var2) {
      var1.setline(637);
      PyObject var10000 = PyString.fromInterned("").__getattr__("join");
      PyList var10002 = new PyList();
      PyObject var3 = var10002.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(637);
      var3 = var1.getlocal(1).__getattr__("splitlines").__call__(var2, var1.getglobal("True")).__iter__();

      while(true) {
         var1.setline(637);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(637);
            var1.dellocal(4);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(637);
         var1.getlocal(4).__call__(var2, var1.getlocal(3)._add(var1.getlocal(5)));
      }
   }

   public PyObject RawTextHelpFormatter$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Help message formatter which retains formatting of all help text.\n\n    Only the name of this class is considered a public API. All the methods\n    provided by the class are considered an implementation detail.\n    "));
      var1.setline(645);
      PyString.fromInterned("Help message formatter which retains formatting of all help text.\n\n    Only the name of this class is considered a public API. All the methods\n    provided by the class are considered an implementation detail.\n    ");
      var1.setline(647);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _split_lines$40, (PyObject)null);
      var1.setlocal("_split_lines", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _split_lines$40(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyObject var3 = var1.getlocal(1).__getattr__("splitlines").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ArgumentDefaultsHelpFormatter$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Help message formatter which adds default values to argument help.\n\n    Only the name of this class is considered a public API. All the methods\n    provided by the class are considered an implementation detail.\n    "));
      var1.setline(656);
      PyString.fromInterned("Help message formatter which adds default values to argument help.\n\n    Only the name of this class is considered a public API. All the methods\n    provided by the class are considered an implementation detail.\n    ");
      var1.setline(658);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _get_help_string$42, (PyObject)null);
      var1.setlocal("_get_help_string", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _get_help_string$42(PyFrame var1, ThreadState var2) {
      var1.setline(659);
      PyObject var3 = var1.getlocal(1).__getattr__("help");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(660);
      PyString var4 = PyString.fromInterned("%(default)");
      PyObject var10000 = var4._notin(var1.getlocal(1).__getattr__("help"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(661);
         var3 = var1.getlocal(1).__getattr__("default");
         var10000 = var3._isnot(var1.getglobal("SUPPRESS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(662);
            PyList var5 = new PyList(new PyObject[]{var1.getglobal("OPTIONAL"), var1.getglobal("ZERO_OR_MORE")});
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(663);
            var10000 = var1.getlocal(1).__getattr__("option_strings");
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(1).__getattr__("nargs");
               var10000 = var3._in(var1.getlocal(3));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(664);
               var3 = var1.getlocal(2);
               var3 = var3._iadd(PyString.fromInterned(" (default: %(default)s)"));
               var1.setlocal(2, var3);
            }
         }
      }

      var1.setline(665);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_action_name$43(PyFrame var1, ThreadState var2) {
      var1.setline(673);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(674);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(675);
         if (var1.getlocal(0).__getattr__("option_strings").__nonzero__()) {
            var1.setline(676);
            var3 = PyString.fromInterned("/").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("option_strings"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(677);
            PyObject var4 = var1.getlocal(0).__getattr__("metavar");
            var10000 = var4._notin(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("SUPPRESS")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(678);
               var3 = var1.getlocal(0).__getattr__("metavar");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(679);
               var4 = var1.getlocal(0).__getattr__("dest");
               var10000 = var4._notin(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("SUPPRESS")}));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(680);
                  var3 = var1.getlocal(0).__getattr__("dest");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(682);
                  var3 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject ArgumentError$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An error from creating or using an argument (optional or positional).\n\n    The string value of this exception is the message, augmented with\n    information about the argument that caused it.\n    "));
      var1.setline(690);
      PyString.fromInterned("An error from creating or using an argument (optional or positional).\n\n    The string value of this exception is the message, augmented with\n    information about the argument that caused it.\n    ");
      var1.setline(692);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$45, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(696);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$46, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$45(PyFrame var1, ThreadState var2) {
      var1.setline(693);
      PyObject var3 = var1.getglobal("_get_action_name").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("argument_name", var3);
      var3 = null;
      var1.setline(694);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("message", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$46(PyFrame var1, ThreadState var2) {
      var1.setline(697);
      PyObject var3 = var1.getlocal(0).__getattr__("argument_name");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(698);
         var5 = PyString.fromInterned("%(message)s");
         var1.setlocal(1, var5);
         var3 = null;
      } else {
         var1.setline(700);
         var5 = PyString.fromInterned("argument %(argument_name)s: %(message)s");
         var1.setlocal(1, var5);
         var3 = null;
      }

      var1.setline(701);
      var10000 = var1.getlocal(1);
      PyObject var10001 = var1.getglobal("dict");
      PyObject[] var6 = new PyObject[]{var1.getlocal(0).__getattr__("message"), var1.getlocal(0).__getattr__("argument_name")};
      String[] var4 = new String[]{"message", "argument_name"};
      var10001 = var10001.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000._mod(var10001);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ArgumentTypeError$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An error from trying to convert a command line string to a type."));
      var1.setline(706);
      PyString.fromInterned("An error from trying to convert a command line string to a type.");
      var1.setline(707);
      return var1.getf_locals();
   }

   public PyObject Action$48(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Information about how to convert command line strings to Python objects.\n\n    Action objects are used by an ArgumentParser to represent the information\n    needed to parse a single argument from one or more strings from the\n    command line. The keyword arguments to the Action constructor are also\n    all attributes of Action instances.\n\n    Keyword Arguments:\n\n        - option_strings -- A list of command-line option strings which\n            should be associated with this action.\n\n        - dest -- The name of the attribute to hold the created object(s)\n\n        - nargs -- The number of command-line arguments that should be\n            consumed. By default, one argument will be consumed and a single\n            value will be produced.  Other values include:\n                - N (an integer) consumes N arguments (and produces a list)\n                - '?' consumes zero or one arguments\n                - '*' consumes zero or more arguments (and produces a list)\n                - '+' consumes one or more arguments (and produces a list)\n            Note that the difference between the default and nargs=1 is that\n            with the default, a single value will be produced, while with\n            nargs=1, a list containing a single value will be produced.\n\n        - const -- The value to be produced if the option is specified and the\n            option uses an action that takes no values.\n\n        - default -- The value to be produced if the option is not specified.\n\n        - type -- A callable that accepts a single string argument, and\n            returns the converted value.  The standard Python types str, int,\n            float, and complex are useful examples of such callables.  If None,\n            str is used.\n\n        - choices -- A container of values that should be allowed. If not None,\n            after a command-line argument has been converted to the appropriate\n            type, an exception will be raised if it is not a member of this\n            collection.\n\n        - required -- True if the action must always be specified at the\n            command line. This is only meaningful for optional command-line\n            arguments.\n\n        - help -- The help string describing the argument.\n\n        - metavar -- The name to be used for the option's argument with the\n            help string. If None, the 'dest' value will be used as the name.\n    "));
      var1.setline(763);
      PyString.fromInterned("Information about how to convert command line strings to Python objects.\n\n    Action objects are used by an ArgumentParser to represent the information\n    needed to parse a single argument from one or more strings from the\n    command line. The keyword arguments to the Action constructor are also\n    all attributes of Action instances.\n\n    Keyword Arguments:\n\n        - option_strings -- A list of command-line option strings which\n            should be associated with this action.\n\n        - dest -- The name of the attribute to hold the created object(s)\n\n        - nargs -- The number of command-line arguments that should be\n            consumed. By default, one argument will be consumed and a single\n            value will be produced.  Other values include:\n                - N (an integer) consumes N arguments (and produces a list)\n                - '?' consumes zero or one arguments\n                - '*' consumes zero or more arguments (and produces a list)\n                - '+' consumes one or more arguments (and produces a list)\n            Note that the difference between the default and nargs=1 is that\n            with the default, a single value will be produced, while with\n            nargs=1, a list containing a single value will be produced.\n\n        - const -- The value to be produced if the option is specified and the\n            option uses an action that takes no values.\n\n        - default -- The value to be produced if the option is not specified.\n\n        - type -- A callable that accepts a single string argument, and\n            returns the converted value.  The standard Python types str, int,\n            float, and complex are useful examples of such callables.  If None,\n            str is used.\n\n        - choices -- A container of values that should be allowed. If not None,\n            after a command-line argument has been converted to the appropriate\n            type, an exception will be raised if it is not a member of this\n            collection.\n\n        - required -- True if the action must always be specified at the\n            command line. This is only meaningful for optional command-line\n            arguments.\n\n        - help -- The help string describing the argument.\n\n        - metavar -- The name to be used for the option's argument with the\n            help string. If None, the 'dest' value will be used as the name.\n    ");
      var1.setline(765);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("False"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$49, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(787);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_kwargs$50, (PyObject)null);
      var1.setlocal("_get_kwargs", var4);
      var3 = null;
      var1.setline(801);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$51, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$49(PyFrame var1, ThreadState var2) {
      var1.setline(776);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("option_strings", var3);
      var3 = null;
      var1.setline(777);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("dest", var3);
      var3 = null;
      var1.setline(778);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("nargs", var3);
      var3 = null;
      var1.setline(779);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("const", var3);
      var3 = null;
      var1.setline(780);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("default", var3);
      var3 = null;
      var1.setline(781);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("type", var3);
      var3 = null;
      var1.setline(782);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("choices", var3);
      var3 = null;
      var1.setline(783);
      var3 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("required", var3);
      var3 = null;
      var1.setline(784);
      var3 = var1.getlocal(9);
      var1.getlocal(0).__setattr__("help", var3);
      var3 = null;
      var1.setline(785);
      var3 = var1.getlocal(10);
      var1.getlocal(0).__setattr__("metavar", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_kwargs$50(PyFrame var1, ThreadState var2) {
      var1.setline(788);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("option_strings"), PyString.fromInterned("dest"), PyString.fromInterned("nargs"), PyString.fromInterned("const"), PyString.fromInterned("default"), PyString.fromInterned("type"), PyString.fromInterned("choices"), PyString.fromInterned("help"), PyString.fromInterned("metavar")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(799);
      PyList var10000 = new PyList();
      PyObject var5 = var10000.__getattr__("append");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(799);
      var5 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(799);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(799);
            var1.dellocal(2);
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(799);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(3))})));
      }
   }

   public PyObject __call__$51(PyFrame var1, ThreadState var2) {
      var1.setline(802);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".__call__() not defined"))));
   }

   public PyObject _StoreAction$52(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(807);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("False"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$53, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(836);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$54, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$53(PyFrame var1, ThreadState var2) {
      var1.setline(818);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(819);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("nargs for store actions must be > 0; if you have nothing to store, actions such as store true or store const may be more appropriate")));
      } else {
         var1.setline(822);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._ne(var1.getglobal("OPTIONAL"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(823);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("nargs must be %r to supply const")._mod(var1.getglobal("OPTIONAL"))));
         } else {
            var1.setline(824);
            var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_StoreAction"), var1.getlocal(0)).__getattr__("__init__");
            PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10)};
            String[] var4 = new String[]{"option_strings", "dest", "nargs", "const", "default", "type", "choices", "required", "help", "metavar"};
            var10000.__call__(var2, var5, var4);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __call__$54(PyFrame var1, ThreadState var2) {
      var1.setline(837);
      var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("dest"), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _StoreConstAction$55(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(842);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$56, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(859);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$57, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$56(PyFrame var1, ThreadState var2) {
      var1.setline(850);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_StoreConstAction"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), Py.newInteger(0), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      String[] var4 = new String[]{"option_strings", "dest", "nargs", "const", "default", "required", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$57(PyFrame var1, ThreadState var2) {
      var1.setline(860);
      var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("dest"), var1.getlocal(0).__getattr__("const"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _StoreTrueAction$58(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(865);
      PyObject[] var3 = new PyObject[]{var1.getname("False"), var1.getname("False"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$59, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$59(PyFrame var1, ThreadState var2) {
      var1.setline(871);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_StoreTrueAction"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getglobal("True"), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
      String[] var4 = new String[]{"option_strings", "dest", "const", "default", "required", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _StoreFalseAction$60(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(882);
      PyObject[] var3 = new PyObject[]{var1.getname("True"), var1.getname("False"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$61, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$61(PyFrame var1, ThreadState var2) {
      var1.setline(888);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_StoreFalseAction"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getglobal("False"), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
      String[] var4 = new String[]{"option_strings", "dest", "const", "default", "required", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _AppendAction$62(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(899);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("False"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$63, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(928);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$64, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$63(PyFrame var1, ThreadState var2) {
      var1.setline(910);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(911);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("nargs for append actions must be > 0; if arg strings are not supplying the value to append, the append const action may be more appropriate")));
      } else {
         var1.setline(914);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._ne(var1.getglobal("OPTIONAL"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(915);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("nargs must be %r to supply const")._mod(var1.getglobal("OPTIONAL"))));
         } else {
            var1.setline(916);
            var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_AppendAction"), var1.getlocal(0)).__getattr__("__init__");
            PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10)};
            String[] var4 = new String[]{"option_strings", "dest", "nargs", "const", "default", "type", "choices", "required", "help", "metavar"};
            var10000.__call__(var2, var5, var4);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __call__$64(PyFrame var1, ThreadState var2) {
      var1.setline(929);
      PyObject var3 = var1.getglobal("_copy").__getattr__("copy").__call__(var2, var1.getglobal("_ensure_value").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(0).__getattr__("dest"), (PyObject)(new PyList(Py.EmptyObjects))));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(930);
      var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(3));
      var1.setline(931);
      var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("dest"), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _AppendConstAction$65(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(936);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$66, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(954);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$67, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$66(PyFrame var1, ThreadState var2) {
      var1.setline(944);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_AppendConstAction"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), Py.newInteger(0), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
      String[] var4 = new String[]{"option_strings", "dest", "nargs", "const", "default", "required", "help", "metavar"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$67(PyFrame var1, ThreadState var2) {
      var1.setline(955);
      PyObject var3 = var1.getglobal("_copy").__getattr__("copy").__call__(var2, var1.getglobal("_ensure_value").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(0).__getattr__("dest"), (PyObject)(new PyList(Py.EmptyObjects))));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(956);
      var1.getlocal(5).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("const"));
      var1.setline(957);
      var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("dest"), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _CountAction$68(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(962);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$69, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(976);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$70, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$69(PyFrame var1, ThreadState var2) {
      var1.setline(968);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_CountAction"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), Py.newInteger(0), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
      String[] var4 = new String[]{"option_strings", "dest", "nargs", "default", "required", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$70(PyFrame var1, ThreadState var2) {
      var1.setline(977);
      PyObject var3 = var1.getglobal("_ensure_value").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(0).__getattr__("dest"), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(978);
      var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("dest"), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _HelpAction$71(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(983);
      PyObject[] var3 = new PyObject[]{var1.getname("SUPPRESS"), var1.getname("SUPPRESS"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$72, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(995);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$73, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$72(PyFrame var1, ThreadState var2) {
      var1.setline(988);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_HelpAction"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), Py.newInteger(0), var1.getlocal(4)};
      String[] var4 = new String[]{"option_strings", "dest", "default", "nargs", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$73(PyFrame var1, ThreadState var2) {
      var1.setline(996);
      var1.getlocal(1).__getattr__("print_help").__call__(var2);
      var1.setline(997);
      var1.getlocal(1).__getattr__("exit").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _VersionAction$74(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1002);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("SUPPRESS"), var1.getname("SUPPRESS"), PyString.fromInterned("show program's version number and exit")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$75, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1016);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$76, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$75(PyFrame var1, ThreadState var2) {
      var1.setline(1008);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_VersionAction"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(3), var1.getlocal(4), Py.newInteger(0), var1.getlocal(5)};
      String[] var4 = new String[]{"option_strings", "dest", "default", "nargs", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(1014);
      PyObject var5 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("version", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$76(PyFrame var1, ThreadState var2) {
      var1.setline(1017);
      PyObject var3 = var1.getlocal(0).__getattr__("version");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1018);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1019);
         var3 = var1.getlocal(1).__getattr__("version");
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(1020);
      var3 = var1.getlocal(1).__getattr__("_get_formatter").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1021);
      var1.getlocal(6).__getattr__("add_text").__call__(var2, var1.getlocal(5));
      var1.setline(1022);
      var10000 = var1.getlocal(1).__getattr__("exit");
      PyObject[] var5 = new PyObject[]{var1.getlocal(6).__getattr__("format_help").__call__(var2)};
      String[] var4 = new String[]{"message"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _SubParsersAction$77(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1027);
      PyObject[] var3 = new PyObject[]{var1.getname("Action")};
      PyObject var4 = Py.makeClass("_ChoicesPseudoAction", var3, _ChoicesPseudoAction$78);
      var1.setlocal("_ChoicesPseudoAction", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1033);
      var3 = new PyObject[]{var1.getname("SUPPRESS"), var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var3, __init__$80, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(1054);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, add_parser$81, (PyObject)null);
      var1.setlocal("add_parser", var5);
      var3 = null;
      var1.setline(1070);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _get_subactions$82, (PyObject)null);
      var1.setlocal("_get_subactions", var5);
      var3 = null;
      var1.setline(1073);
      var3 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var3, __call__$83, (PyObject)null);
      var1.setlocal("__call__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _ChoicesPseudoAction$78(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1029);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$79, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$79(PyFrame var1, ThreadState var2) {
      var1.setline(1030);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("_SubParsersAction").__getattr__("_ChoicesPseudoAction"), var1.getlocal(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1031);
      PyObject var10000 = var1.getlocal(3).__getattr__("__init__");
      PyObject[] var5 = new PyObject[]{new PyList(Py.EmptyObjects), var1.getlocal(1), var1.getlocal(2)};
      String[] var4 = new String[]{"option_strings", "dest", "help"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __init__$80(PyFrame var1, ThreadState var2) {
      var1.setline(1041);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_prog_prefix", var3);
      var3 = null;
      var1.setline(1042);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_parser_class", var3);
      var3 = null;
      var1.setline(1043);
      var3 = var1.getglobal("_collections").__getattr__("OrderedDict").__call__(var2);
      var1.getlocal(0).__setattr__("_name_parser_map", var3);
      var3 = null;
      var1.setline(1044);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_choices_actions", var5);
      var3 = null;
      var1.setline(1046);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("_SubParsersAction"), var1.getlocal(0)).__getattr__("__init__");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(4), var1.getglobal("PARSER"), var1.getlocal(0).__getattr__("_name_parser_map"), var1.getlocal(5), var1.getlocal(6)};
      String[] var4 = new String[]{"option_strings", "dest", "nargs", "choices", "help", "metavar"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_parser$81(PyFrame var1, ThreadState var2) {
      var1.setline(1056);
      PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prog"));
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1057);
         var3 = PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_prog_prefix"), var1.getlocal(1)}));
         var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("prog"), var3);
         var3 = null;
      }

      var1.setline(1060);
      PyString var5 = PyString.fromInterned("help");
      var10000 = var5._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1061);
         var3 = var1.getlocal(2).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("help"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1062);
         var3 = var1.getlocal(0).__getattr__("_ChoicesPseudoAction").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1063);
         var1.getlocal(0).__getattr__("_choices_actions").__getattr__("append").__call__(var2, var1.getlocal(4));
      }

      var1.setline(1066);
      var10000 = var1.getlocal(0).__getattr__("_parser_class");
      PyObject[] var6 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var6, var4, (PyObject)null, var1.getlocal(2));
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1067);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__getattr__("_name_parser_map").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(1068);
      var3 = var1.getlocal(5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_subactions$82(PyFrame var1, ThreadState var2) {
      var1.setline(1071);
      PyObject var3 = var1.getlocal(0).__getattr__("_choices_actions");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __call__$83(PyFrame var1, ThreadState var2) {
      var1.setline(1074);
      PyObject var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1075);
      var3 = var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1078);
      var3 = var1.getlocal(0).__getattr__("dest");
      PyObject var10000 = var3._isnot(var1.getglobal("SUPPRESS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1079);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("dest"), var1.getlocal(5));
      }

      PyObject var8;
      try {
         var1.setline(1083);
         var3 = var1.getlocal(0).__getattr__("_name_parser_map").__getitem__(var1.getlocal(5));
         var1.setlocal(1, var3);
         var3 = null;
      } catch (Throwable var7) {
         PyException var9 = Py.setException(var7, var1);
         if (var9.match(var1.getglobal("KeyError"))) {
            var1.setline(1085);
            PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(5), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_name_parser_map"))});
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(1086);
            var8 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unknown parser %r (choices: %s)"))._mod(var1.getlocal(7));
            var1.setlocal(8, var8);
            var4 = null;
            var1.setline(1087);
            throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(0), var1.getlocal(8)));
         }

         throw var9;
      }

      var1.setline(1096);
      var3 = var1.getlocal(1).__getattr__("parse_known_args").__call__(var2, var1.getlocal(6), var1.getglobal("None"));
      PyObject[] var10 = Py.unpackSequence(var3, 2);
      PyObject var5 = var10[0];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var10[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(1097);
      var3 = var1.getglobal("vars").__call__(var2, var1.getlocal(9)).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(1097);
         var8 = var3.__iternext__();
         if (var8 == null) {
            var1.setline(1100);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(1101);
               var1.getglobal("vars").__call__(var2, var1.getlocal(2)).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getglobal("_UNRECOGNIZED_ARGS_ATTR"), (PyObject)(new PyList(Py.EmptyObjects)));
               var1.setline(1102);
               var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getglobal("_UNRECOGNIZED_ARGS_ATTR")).__getattr__("extend").__call__(var2, var1.getlocal(6));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var11 = Py.unpackSequence(var8, 2);
         PyObject var6 = var11[0];
         var1.setlocal(10, var6);
         var6 = null;
         var6 = var11[1];
         var1.setlocal(11, var6);
         var6 = null;
         var1.setline(1098);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(10), var1.getlocal(11));
      }
   }

   public PyObject FileType$84(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Factory for creating file object types\n\n    Instances of FileType are typically passed as type= arguments to the\n    ArgumentParser add_argument() method.\n\n    Keyword Arguments:\n        - mode -- A string indicating how the file is to be opened. Accepts the\n            same values as the builtin open() function.\n        - bufsize -- The file's desired buffer size. Accepts the same values as\n            the builtin open() function.\n    "));
      var1.setline(1120);
      PyString.fromInterned("Factory for creating file object types\n\n    Instances of FileType are typically passed as type= arguments to the\n    ArgumentParser add_argument() method.\n\n    Keyword Arguments:\n        - mode -- A string indicating how the file is to be opened. Accepts the\n            same values as the builtin open() function.\n        - bufsize -- The file's desired buffer size. Accepts the same values as\n            the builtin open() function.\n    ");
      var1.setline(1122);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("r"), Py.newInteger(-1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$85, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1126);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$86, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      var1.setline(1144);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$87, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$85(PyFrame var1, ThreadState var2) {
      var1.setline(1123);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_mode", var3);
      var3 = null;
      var1.setline(1124);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_bufsize", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$86(PyFrame var1, ThreadState var2) {
      var1.setline(1128);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("-"));
      var3 = null;
      PyException var4;
      if (var10000.__nonzero__()) {
         var1.setline(1129);
         PyString var7 = PyString.fromInterned("r");
         var10000 = var7._in(var1.getlocal(0).__getattr__("_mode"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1130);
            var3 = var1.getglobal("_sys").__getattr__("stdin");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1131);
            PyString var8 = PyString.fromInterned("w");
            var10000 = var8._in(var1.getlocal(0).__getattr__("_mode"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1132);
               var3 = var1.getglobal("_sys").__getattr__("stdout");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1134);
               PyObject var9 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("argument \"-\" with mode %r"))._mod(var1.getlocal(0).__getattr__("_mode"));
               var1.setlocal(2, var9);
               var4 = null;
               var1.setline(1135);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(2)));
            }
         }
      } else {
         try {
            var1.setline(1139);
            var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_mode"), var1.getlocal(0).__getattr__("_bufsize"));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var6) {
            var4 = Py.setException(var6, var1);
            if (var4.match(var1.getglobal("IOError"))) {
               PyObject var5 = var4.value;
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(1141);
               var5 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("can't open '%s': %s"));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(1142);
               throw Py.makeException(var1.getglobal("ArgumentTypeError").__call__(var2, var1.getlocal(4)._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(3)}))));
            } else {
               throw var4;
            }
         }
      }
   }

   public PyObject __repr__$87(PyFrame var1, ThreadState var2) {
      var1.setline(1145);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_mode"), var1.getlocal(0).__getattr__("_bufsize")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1146);
      PyObject var10000 = PyString.fromInterned(", ").__getattr__("join");
      var1.setline(1146);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var5, f$88, (PyObject)null);
      PyObject var10002 = var4.__call__(var2, var1.getlocal(1).__iter__());
      Arrays.fill(var5, (Object)null);
      PyObject var6 = var10000.__call__(var2, var10002);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1147);
      var6 = PyString.fromInterned("%s(%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(0)).__getattr__("__name__"), var1.getlocal(2)}));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject f$88(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(1146);
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

            var7 = (PyObject)var10000;
      }

      do {
         var1.setline(1146);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(1146);
         PyObject var6 = var1.getlocal(1);
         var7 = var6._ne(Py.newInteger(-1));
         var5 = null;
      } while(!var7.__nonzero__());

      var1.setline(1146);
      var1.setline(1146);
      var7 = var1.getglobal("repr").__call__(var2, var1.getlocal(1));
      var1.f_lasti = 1;
      var5 = new Object[7];
      var5[3] = var3;
      var5[4] = var4;
      var1.f_savedlocals = var5;
      return var7;
   }

   public PyObject Namespace$89(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Simple object for storing attributes.\n\n    Implements equality by attribute names and values, and provides a simple\n    string representation.\n    "));
      var1.setline(1158);
      PyString.fromInterned("Simple object for storing attributes.\n\n    Implements equality by attribute names and values, and provides a simple\n    string representation.\n    ");
      var1.setline(1160);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$90, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1164);
      PyObject var5 = var1.getname("None");
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(1166);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$91, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(1171);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$92, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(1176);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __contains__$93, (PyObject)null);
      var1.setlocal("__contains__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$90(PyFrame var1, ThreadState var2) {
      var1.setline(1161);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1161);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1162);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(1).__getitem__(var1.getlocal(2)));
      }
   }

   public PyObject __eq__$91(PyFrame var1, ThreadState var2) {
      var1.setline(1167);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Namespace")).__not__().__nonzero__()) {
         var1.setline(1168);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1169);
         PyObject var4 = var1.getglobal("vars").__call__(var2, var1.getlocal(0));
         PyObject var10000 = var4._eq(var1.getglobal("vars").__call__(var2, var1.getlocal(1)));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$92(PyFrame var1, ThreadState var2) {
      var1.setline(1172);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Namespace")).__not__().__nonzero__()) {
         var1.setline(1173);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1174);
         PyObject var4 = var1.getlocal(0);
         PyObject var10000 = var4._eq(var1.getlocal(1));
         var4 = null;
         var3 = var10000.__not__();
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __contains__$93(PyFrame var1, ThreadState var2) {
      var1.setline(1177);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("__dict__"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _ActionsContainer$94(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1182);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$95, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1234);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, register$96, (PyObject)null);
      var1.setlocal("register", var4);
      var3 = null;
      var1.setline(1238);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _registry_get$97, (PyObject)null);
      var1.setlocal("_registry_get", var4);
      var3 = null;
      var1.setline(1244);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_defaults$98, (PyObject)null);
      var1.setlocal("set_defaults", var4);
      var3 = null;
      var1.setline(1253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_default$99, (PyObject)null);
      var1.setlocal("get_default", var4);
      var3 = null;
      var1.setline(1263);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_argument$100, PyString.fromInterned("\n        add_argument(dest, ..., name=value, ...)\n        add_argument(option_string, option_string, ..., name=value, ...)\n        "));
      var1.setlocal("add_argument", var4);
      var3 = null;
      var1.setline(1310);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_argument_group$101, (PyObject)null);
      var1.setlocal("add_argument_group", var4);
      var3 = null;
      var1.setline(1315);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_mutually_exclusive_group$102, (PyObject)null);
      var1.setlocal("add_mutually_exclusive_group", var4);
      var3 = null;
      var1.setline(1320);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _add_action$103, (PyObject)null);
      var1.setlocal("_add_action", var4);
      var3 = null;
      var1.setline(1341);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _remove_action$104, (PyObject)null);
      var1.setlocal("_remove_action", var4);
      var3 = null;
      var1.setline(1344);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _add_container_actions$105, (PyObject)null);
      var1.setlocal("_add_container_actions", var4);
      var3 = null;
      var1.setline(1384);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_positional_kwargs$106, (PyObject)null);
      var1.setlocal("_get_positional_kwargs", var4);
      var3 = null;
      var1.setline(1400);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_optional_kwargs$107, (PyObject)null);
      var1.setlocal("_get_optional_kwargs", var4);
      var3 = null;
      var1.setline(1435);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _pop_action_class$108, (PyObject)null);
      var1.setlocal("_pop_action_class", var4);
      var3 = null;
      var1.setline(1439);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_handler$109, (PyObject)null);
      var1.setlocal("_get_handler", var4);
      var3 = null;
      var1.setline(1448);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _check_conflict$110, (PyObject)null);
      var1.setlocal("_check_conflict", var4);
      var3 = null;
      var1.setline(1462);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_conflict_error$111, (PyObject)null);
      var1.setlocal("_handle_conflict_error", var4);
      var3 = null;
      var1.setline(1469);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handle_conflict_resolve$112, (PyObject)null);
      var1.setlocal("_handle_conflict_resolve", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$95(PyFrame var1, ThreadState var2) {
      var1.setline(1187);
      var1.getglobal("super").__call__(var2, var1.getglobal("_ActionsContainer"), var1.getlocal(0)).__getattr__("__init__").__call__(var2);
      var1.setline(1189);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("description", var3);
      var3 = null;
      var1.setline(1190);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("argument_default", var3);
      var3 = null;
      var1.setline(1191);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("prefix_chars", var3);
      var3 = null;
      var1.setline(1192);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("conflict_handler", var3);
      var3 = null;
      var1.setline(1195);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_registries", var4);
      var3 = null;
      var1.setline(1198);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)var1.getglobal("None"), (PyObject)var1.getglobal("_StoreAction"));
      var1.setline(1199);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("store"), (PyObject)var1.getglobal("_StoreAction"));
      var1.setline(1200);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("store_const"), (PyObject)var1.getglobal("_StoreConstAction"));
      var1.setline(1201);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("store_true"), (PyObject)var1.getglobal("_StoreTrueAction"));
      var1.setline(1202);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("store_false"), (PyObject)var1.getglobal("_StoreFalseAction"));
      var1.setline(1203);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("append"), (PyObject)var1.getglobal("_AppendAction"));
      var1.setline(1204);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("append_const"), (PyObject)var1.getglobal("_AppendConstAction"));
      var1.setline(1205);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("count"), (PyObject)var1.getglobal("_CountAction"));
      var1.setline(1206);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("help"), (PyObject)var1.getglobal("_HelpAction"));
      var1.setline(1207);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("version"), (PyObject)var1.getglobal("_VersionAction"));
      var1.setline(1208);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)PyString.fromInterned("parsers"), (PyObject)var1.getglobal("_SubParsersAction"));
      var1.setline(1211);
      var1.getlocal(0).__getattr__("_get_handler").__call__(var2);
      var1.setline(1214);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_actions", var5);
      var3 = null;
      var1.setline(1215);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_option_string_actions", var4);
      var3 = null;
      var1.setline(1218);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_action_groups", var5);
      var3 = null;
      var1.setline(1219);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_mutually_exclusive_groups", var5);
      var3 = null;
      var1.setline(1222);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_defaults", var4);
      var3 = null;
      var1.setline(1225);
      var3 = var1.getglobal("_re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^-\\d+$|^-\\d*\\.\\d+$"));
      var1.getlocal(0).__setattr__("_negative_number_matcher", var3);
      var3 = null;
      var1.setline(1229);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_has_negative_number_optionals", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject register$96(PyFrame var1, ThreadState var2) {
      var1.setline(1235);
      PyObject var3 = var1.getlocal(0).__getattr__("_registries").__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyDictionary(Py.EmptyObjects)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1236);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setitem__(var1.getlocal(2), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _registry_get$97(PyFrame var1, ThreadState var2) {
      var1.setline(1239);
      PyObject var3 = var1.getlocal(0).__getattr__("_registries").__getitem__(var1.getlocal(1)).__getattr__("get").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_defaults$98(PyFrame var1, ThreadState var2) {
      var1.setline(1245);
      var1.getlocal(0).__getattr__("_defaults").__getattr__("update").__call__(var2, var1.getlocal(1));
      var1.setline(1249);
      PyObject var3 = var1.getlocal(0).__getattr__("_actions").__iter__();

      while(true) {
         var1.setline(1249);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1250);
         PyObject var5 = var1.getlocal(2).__getattr__("dest");
         PyObject var10000 = var5._in(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1251);
            var5 = var1.getlocal(1).__getitem__(var1.getlocal(2).__getattr__("dest"));
            var1.getlocal(2).__setattr__("default", var5);
            var5 = null;
         }
      }
   }

   public PyObject get_default$99(PyFrame var1, ThreadState var2) {
      var1.setline(1254);
      PyObject var3 = var1.getlocal(0).__getattr__("_actions").__iter__();

      PyObject var10000;
      PyObject var5;
      do {
         var1.setline(1254);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1257);
            var5 = var1.getlocal(0).__getattr__("_defaults").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(1255);
         var5 = var1.getlocal(2).__getattr__("dest");
         var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(2).__getattr__("default");
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(1256);
      var5 = var1.getlocal(2).__getattr__("default");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject add_argument$100(PyFrame var1, ThreadState var2) {
      var1.setline(1267);
      PyString.fromInterned("\n        add_argument(dest, ..., name=value, ...)\n        add_argument(option_string, option_string, ..., name=value, ...)\n        ");
      var1.setline(1272);
      PyObject var3 = var1.getlocal(0).__getattr__("prefix_chars");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1273);
      PyObject var10000 = var1.getlocal(1).__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
            var10000 = var3._notin(var1.getlocal(3));
            var3 = null;
         }
      }

      String[] var4;
      PyString var6;
      PyObject[] var7;
      if (var10000.__nonzero__()) {
         var1.setline(1274);
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var6 = PyString.fromInterned("dest");
            var10000 = var6._in(var1.getlocal(2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1275);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dest supplied twice for positional argument")));
         }

         var1.setline(1276);
         var10000 = var1.getlocal(0).__getattr__("_get_positional_kwargs");
         var7 = Py.EmptyObjects;
         var4 = new String[0];
         var10000 = var10000._callextra(var7, var4, var1.getlocal(1), var1.getlocal(2));
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(1280);
         var10000 = var1.getlocal(0).__getattr__("_get_optional_kwargs");
         var7 = Py.EmptyObjects;
         var4 = new String[0];
         var10000 = var10000._callextra(var7, var4, var1.getlocal(1), var1.getlocal(2));
         var3 = null;
         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1283);
      var6 = PyString.fromInterned("default");
      var10000 = var6._notin(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1284);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("dest"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1285);
         var3 = var1.getlocal(4);
         var10000 = var3._in(var1.getlocal(0).__getattr__("_defaults"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1286);
            var3 = var1.getlocal(0).__getattr__("_defaults").__getitem__(var1.getlocal(4));
            var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("default"), var3);
            var3 = null;
         } else {
            var1.setline(1287);
            var3 = var1.getlocal(0).__getattr__("argument_default");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1288);
               var3 = var1.getlocal(0).__getattr__("argument_default");
               var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("default"), var3);
               var3 = null;
            }
         }
      }

      var1.setline(1291);
      var3 = var1.getlocal(0).__getattr__("_pop_action_class").__call__(var2, var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1292);
      if (var1.getglobal("_callable").__call__(var2, var1.getlocal(5)).__not__().__nonzero__()) {
         var1.setline(1293);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unknown action \"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(5)}))));
      } else {
         var1.setline(1294);
         var10000 = var1.getlocal(5);
         var7 = Py.EmptyObjects;
         var4 = new String[0];
         var10000 = var10000._callextra(var7, var4, (PyObject)null, var1.getlocal(2));
         var3 = null;
         var3 = var10000;
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1297);
         var3 = var1.getlocal(0).__getattr__("_registry_get").__call__((ThreadState)var2, PyString.fromInterned("type"), (PyObject)var1.getlocal(6).__getattr__("type"), (PyObject)var1.getlocal(6).__getattr__("type"));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(1298);
         if (var1.getglobal("_callable").__call__(var2, var1.getlocal(7)).__not__().__nonzero__()) {
            var1.setline(1299);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%r is not callable")._mod(new PyTuple(new PyObject[]{var1.getlocal(7)}))));
         } else {
            var1.setline(1302);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_get_formatter")).__nonzero__()) {
               try {
                  var1.setline(1304);
                  var1.getlocal(0).__getattr__("_get_formatter").__call__(var2).__getattr__("_format_args").__call__(var2, var1.getlocal(6), var1.getglobal("None"));
               } catch (Throwable var5) {
                  PyException var8 = Py.setException(var5, var1);
                  if (var8.match(var1.getglobal("TypeError"))) {
                     var1.setline(1306);
                     throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("length of metavar tuple does not match nargs")));
                  }

                  throw var8;
               }
            }

            var1.setline(1308);
            var3 = var1.getlocal(0).__getattr__("_add_action").__call__(var2, var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject add_argument_group$101(PyFrame var1, ThreadState var2) {
      var1.setline(1311);
      PyObject var10000 = var1.getglobal("_ArgumentGroup");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(1312);
      var1.getlocal(0).__getattr__("_action_groups").__getattr__("append").__call__(var2, var1.getlocal(3));
      var1.setline(1313);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject add_mutually_exclusive_group$102(PyFrame var1, ThreadState var2) {
      var1.setline(1316);
      PyObject var10000 = var1.getglobal("_MutuallyExclusiveGroup");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, (PyObject)null, var1.getlocal(1));
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1317);
      var1.getlocal(0).__getattr__("_mutually_exclusive_groups").__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.setline(1318);
      var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _add_action$103(PyFrame var1, ThreadState var2) {
      var1.setline(1322);
      var1.getlocal(0).__getattr__("_check_conflict").__call__(var2, var1.getlocal(1));
      var1.setline(1325);
      var1.getlocal(0).__getattr__("_actions").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(1326);
      PyObject var3 = var1.getlocal(0);
      var1.getlocal(1).__setattr__("container", var3);
      var3 = null;
      var1.setline(1329);
      var3 = var1.getlocal(1).__getattr__("option_strings").__iter__();

      while(true) {
         var1.setline(1329);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1333);
            var3 = var1.getlocal(1).__getattr__("option_strings").__iter__();

            while(true) {
               var1.setline(1333);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(1339);
                  var3 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(2, var4);
               var1.setline(1334);
               if (var1.getlocal(0).__getattr__("_negative_number_matcher").__getattr__("match").__call__(var2, var1.getlocal(2)).__nonzero__()) {
                  var1.setline(1335);
                  if (var1.getlocal(0).__getattr__("_has_negative_number_optionals").__not__().__nonzero__()) {
                     var1.setline(1336);
                     var1.getlocal(0).__getattr__("_has_negative_number_optionals").__getattr__("append").__call__(var2, var1.getglobal("True"));
                  }
               }
            }
         }

         var1.setlocal(2, var4);
         var1.setline(1330);
         PyObject var5 = var1.getlocal(1);
         var1.getlocal(0).__getattr__("_option_string_actions").__setitem__(var1.getlocal(2), var5);
         var5 = null;
      }
   }

   public PyObject _remove_action$104(PyFrame var1, ThreadState var2) {
      var1.setline(1342);
      var1.getlocal(0).__getattr__("_actions").__getattr__("remove").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _add_container_actions$105(PyFrame var1, ThreadState var2) {
      var1.setline(1346);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1347);
      PyObject var8 = var1.getlocal(0).__getattr__("_action_groups").__iter__();

      while(true) {
         var1.setline(1347);
         PyObject var4 = var8.__iternext__();
         PyObject var10000;
         PyObject var5;
         if (var4 == null) {
            var1.setline(1354);
            var3 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(1355);
            var8 = var1.getlocal(1).__getattr__("_action_groups").__iter__();

            while(true) {
               var1.setline(1355);
               var4 = var8.__iternext__();
               String[] var6;
               PyObject var7;
               PyObject var9;
               PyObject[] var10;
               if (var4 == null) {
                  var1.setline(1372);
                  var8 = var1.getlocal(1).__getattr__("_mutually_exclusive_groups").__iter__();

                  while(true) {
                     var1.setline(1372);
                     var4 = var8.__iternext__();
                     if (var4 == null) {
                        var1.setline(1381);
                        var8 = var1.getlocal(1).__getattr__("_actions").__iter__();

                        while(true) {
                           var1.setline(1381);
                           var4 = var8.__iternext__();
                           if (var4 == null) {
                              var1.f_lasti = -1;
                              return Py.None;
                           }

                           var1.setlocal(6, var4);
                           var1.setline(1382);
                           var1.getlocal(5).__getattr__("get").__call__(var2, var1.getlocal(6), var1.getlocal(0)).__getattr__("_add_action").__call__(var2, var1.getlocal(6));
                        }
                     }

                     var1.setlocal(3, var4);
                     var1.setline(1373);
                     var10000 = var1.getlocal(0).__getattr__("add_mutually_exclusive_group");
                     var10 = new PyObject[]{var1.getlocal(3).__getattr__("required")};
                     var6 = new String[]{"required"};
                     var10000 = var10000.__call__(var2, var10, var6);
                     var5 = null;
                     var5 = var10000;
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(1377);
                     var5 = var1.getlocal(3).__getattr__("_group_actions").__iter__();

                     while(true) {
                        var1.setline(1377);
                        var9 = var5.__iternext__();
                        if (var9 == null) {
                           break;
                        }

                        var1.setlocal(6, var9);
                        var1.setline(1378);
                        var7 = var1.getlocal(7);
                        var1.getlocal(5).__setitem__(var1.getlocal(6), var7);
                        var7 = null;
                     }
                  }
               }

               var1.setlocal(3, var4);
               var1.setline(1359);
               var5 = var1.getlocal(3).__getattr__("title");
               var10000 = var5._notin(var1.getlocal(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1360);
                  var10000 = var1.getlocal(0).__getattr__("add_argument_group");
                  var10 = new PyObject[]{var1.getlocal(3).__getattr__("title"), var1.getlocal(3).__getattr__("description"), var1.getlocal(3).__getattr__("conflict_handler")};
                  var6 = new String[]{"title", "description", "conflict_handler"};
                  var10000 = var10000.__call__(var2, var10, var6);
                  var5 = null;
                  var5 = var10000;
                  var1.getlocal(2).__setitem__(var1.getlocal(3).__getattr__("title"), var5);
                  var5 = null;
               }

               var1.setline(1366);
               var5 = var1.getlocal(3).__getattr__("_group_actions").__iter__();

               while(true) {
                  var1.setline(1366);
                  var9 = var5.__iternext__();
                  if (var9 == null) {
                     break;
                  }

                  var1.setlocal(6, var9);
                  var1.setline(1367);
                  var7 = var1.getlocal(2).__getitem__(var1.getlocal(3).__getattr__("title"));
                  var1.getlocal(5).__setitem__(var1.getlocal(6), var7);
                  var7 = null;
               }
            }
         }

         var1.setlocal(3, var4);
         var1.setline(1348);
         var5 = var1.getlocal(3).__getattr__("title");
         var10000 = var5._in(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1349);
            var5 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot merge actions - two groups are named %r"));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(1350);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(4)._mod(var1.getlocal(3).__getattr__("title"))));
         }

         var1.setline(1351);
         var5 = var1.getlocal(3);
         var1.getlocal(2).__setitem__(var1.getlocal(3).__getattr__("title"), var5);
         var5 = null;
      }
   }

   public PyObject _get_positional_kwargs$106(PyFrame var1, ThreadState var2) {
      var1.setline(1386);
      PyString var3 = PyString.fromInterned("required");
      PyObject var10000 = var3._in(var1.getlocal(2));
      var3 = null;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(1387);
         var5 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'required' is an invalid argument for positionals"));
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(1388);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, var1.getlocal(3)));
      } else {
         var1.setline(1392);
         var5 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("nargs"));
         var10000 = var5._notin(new PyList(new PyObject[]{var1.getglobal("OPTIONAL"), var1.getglobal("ZERO_OR_MORE")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1393);
            var5 = var1.getglobal("True");
            var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("required"), var5);
            var3 = null;
         }

         var1.setline(1394);
         var5 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("nargs"));
         var10000 = var5._eq(var1.getglobal("ZERO_OR_MORE"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = PyString.fromInterned("default");
            var10000 = var3._notin(var1.getlocal(2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1395);
            var5 = var1.getglobal("True");
            var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("required"), var5);
            var3 = null;
         }

         var1.setline(1398);
         var10000 = var1.getglobal("dict");
         PyObject[] var6 = new PyObject[]{var1.getlocal(2), var1.getlocal(1), new PyList(Py.EmptyObjects)};
         String[] var4 = new String[]{"dest", "option_strings"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var5 = var10000;
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject _get_optional_kwargs$107(PyFrame var1, ThreadState var2) {
      var1.setline(1402);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1403);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1404);
      PyObject var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1404);
         PyObject var4 = var6.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(1420);
            var6 = var1.getlocal(2).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dest"), (PyObject)var1.getglobal("None"));
            var1.setlocal(8, var6);
            var3 = null;
            var1.setline(1421);
            var6 = var1.getlocal(8);
            var10000 = var6._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1422);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(1423);
                  var6 = var1.getlocal(4).__getitem__(Py.newInteger(0));
                  var1.setlocal(9, var6);
                  var3 = null;
               } else {
                  var1.setline(1425);
                  var6 = var1.getlocal(3).__getitem__(Py.newInteger(0));
                  var1.setlocal(9, var6);
                  var3 = null;
               }

               var1.setline(1426);
               var6 = var1.getlocal(9).__getattr__("lstrip").__call__(var2, var1.getlocal(0).__getattr__("prefix_chars"));
               var1.setlocal(8, var6);
               var3 = null;
               var1.setline(1427);
               if (var1.getlocal(8).__not__().__nonzero__()) {
                  var1.setline(1428);
                  var6 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dest= is required for options like %r"));
                  var1.setlocal(6, var6);
                  var3 = null;
                  var1.setline(1429);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(6)._mod(var1.getlocal(5))));
               }

               var1.setline(1430);
               var6 = var1.getlocal(8).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"));
               var1.setlocal(8, var6);
               var3 = null;
            }

            var1.setline(1433);
            var10000 = var1.getglobal("dict");
            PyObject[] var9 = new PyObject[]{var1.getlocal(2), var1.getlocal(8), var1.getlocal(3)};
            String[] var7 = new String[]{"dest", "option_strings"};
            var10000 = var10000.__call__(var2, var9, var7);
            var3 = null;
            var6 = var10000;
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(5, var4);
         var1.setline(1406);
         PyObject var5 = var1.getlocal(5).__getitem__(Py.newInteger(0));
         var10000 = var5._in(var1.getlocal(0).__getattr__("prefix_chars"));
         var5 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(1407);
            var5 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid option string %r: must start with a character %r"));
            var1.setlocal(6, var5);
            var5 = null;
            var1.setline(1409);
            PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(0).__getattr__("prefix_chars")});
            var1.setlocal(7, var8);
            var5 = null;
            var1.setline(1410);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(6)._mod(var1.getlocal(7))));
         }

         var1.setline(1413);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
         var1.setline(1414);
         var5 = var1.getlocal(5).__getitem__(Py.newInteger(0));
         var10000 = var5._in(var1.getlocal(0).__getattr__("prefix_chars"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1415);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
            var10000 = var5._gt(Py.newInteger(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1416);
               var5 = var1.getlocal(5).__getitem__(Py.newInteger(1));
               var10000 = var5._in(var1.getlocal(0).__getattr__("prefix_chars"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1417);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));
               }
            }
         }
      }
   }

   public PyObject _pop_action_class$108(PyFrame var1, ThreadState var2) {
      var1.setline(1436);
      PyObject var3 = var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("action"), (PyObject)var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1437);
      var3 = var1.getlocal(0).__getattr__("_registry_get").__call__((ThreadState)var2, PyString.fromInterned("action"), (PyObject)var1.getlocal(3), (PyObject)var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_handler$109(PyFrame var1, ThreadState var2) {
      var1.setline(1441);
      PyObject var3 = PyString.fromInterned("_handle_conflict_%s")._mod(var1.getlocal(0).__getattr__("conflict_handler"));
      var1.setlocal(1, var3);
      var3 = null;

      try {
         var1.setline(1443);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("AttributeError"))) {
            var1.setline(1445);
            PyObject var5 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid conflict_resolution value: %r"));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(1446);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(2)._mod(var1.getlocal(0).__getattr__("conflict_handler"))));
         } else {
            throw var4;
         }
      }
   }

   public PyObject _check_conflict$110(PyFrame var1, ThreadState var2) {
      var1.setline(1451);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1452);
      PyObject var6 = var1.getlocal(1).__getattr__("option_strings").__iter__();

      while(true) {
         var1.setline(1452);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(1458);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1459);
               var6 = var1.getlocal(0).__getattr__("_get_handler").__call__(var2);
               var1.setlocal(5, var6);
               var3 = null;
               var1.setline(1460);
               var1.getlocal(5).__call__(var2, var1.getlocal(1), var1.getlocal(2));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(1453);
         PyObject var5 = var1.getlocal(3);
         PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("_option_string_actions"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1454);
            var5 = var1.getlocal(0).__getattr__("_option_string_actions").__getitem__(var1.getlocal(3));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(1455);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
         }
      }
   }

   public PyObject _handle_conflict_error$111(PyFrame var1, ThreadState var2) {
      var1.setline(1463);
      PyObject var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("conflicting option string(s): %s"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1464);
      PyObject var10000 = PyString.fromInterned(", ").__getattr__("join");
      PyList var10002 = new PyList();
      var3 = var10002.__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1465);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(1465);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1465);
            var1.dellocal(5);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(1467);
            throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(1), var1.getlocal(3)._mod(var1.getlocal(4))));
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(1, var6);
         var6 = null;
         var1.setline(1464);
         var1.getlocal(5).__call__(var2, var1.getlocal(6));
      }
   }

   public PyObject _handle_conflict_resolve$112(PyFrame var1, ThreadState var2) {
      var1.setline(1472);
      PyObject var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(1472);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(1, var6);
         var6 = null;
         var1.setline(1475);
         var1.getlocal(1).__getattr__("option_strings").__getattr__("remove").__call__(var2, var1.getlocal(3));
         var1.setline(1476);
         var1.getlocal(0).__getattr__("_option_string_actions").__getattr__("pop").__call__(var2, var1.getlocal(3), var1.getglobal("None"));
         var1.setline(1480);
         if (var1.getlocal(1).__getattr__("option_strings").__not__().__nonzero__()) {
            var1.setline(1481);
            var1.getlocal(1).__getattr__("container").__getattr__("_remove_action").__call__(var2, var1.getlocal(1));
         }
      }
   }

   public PyObject _ArgumentGroup$113(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1486);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$114, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1508);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _add_action$115, (PyObject)null);
      var1.setlocal("_add_action", var4);
      var3 = null;
      var1.setline(1513);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _remove_action$116, (PyObject)null);
      var1.setlocal("_remove_action", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$114(PyFrame var1, ThreadState var2) {
      var1.setline(1488);
      PyObject var3 = var1.getlocal(4).__getattr__("setdefault");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1489);
      var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("conflict_handler"), (PyObject)var1.getlocal(1).__getattr__("conflict_handler"));
      var1.setline(1490);
      var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prefix_chars"), (PyObject)var1.getlocal(1).__getattr__("prefix_chars"));
      var1.setline(1491);
      var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("argument_default"), (PyObject)var1.getlocal(1).__getattr__("argument_default"));
      var1.setline(1492);
      var3 = var1.getglobal("super").__call__(var2, var1.getglobal("_ArgumentGroup"), var1.getlocal(0)).__getattr__("__init__");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1493);
      PyObject var10000 = var1.getlocal(6);
      PyObject[] var5 = new PyObject[]{var1.getlocal(3)};
      String[] var4 = new String[]{"description"};
      var10000._callextra(var5, var4, (PyObject)null, var1.getlocal(4));
      var3 = null;
      var1.setline(1496);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("title", var3);
      var3 = null;
      var1.setline(1497);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_group_actions", var6);
      var3 = null;
      var1.setline(1500);
      var3 = var1.getlocal(1).__getattr__("_registries");
      var1.getlocal(0).__setattr__("_registries", var3);
      var3 = null;
      var1.setline(1501);
      var3 = var1.getlocal(1).__getattr__("_actions");
      var1.getlocal(0).__setattr__("_actions", var3);
      var3 = null;
      var1.setline(1502);
      var3 = var1.getlocal(1).__getattr__("_option_string_actions");
      var1.getlocal(0).__setattr__("_option_string_actions", var3);
      var3 = null;
      var1.setline(1503);
      var3 = var1.getlocal(1).__getattr__("_defaults");
      var1.getlocal(0).__setattr__("_defaults", var3);
      var3 = null;
      var1.setline(1504);
      var3 = var1.getlocal(1).__getattr__("_has_negative_number_optionals");
      var1.getlocal(0).__setattr__("_has_negative_number_optionals", var3);
      var3 = null;
      var1.setline(1506);
      var3 = var1.getlocal(1).__getattr__("_mutually_exclusive_groups");
      var1.getlocal(0).__setattr__("_mutually_exclusive_groups", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _add_action$115(PyFrame var1, ThreadState var2) {
      var1.setline(1509);
      PyObject var3 = var1.getglobal("super").__call__(var2, var1.getglobal("_ArgumentGroup"), var1.getlocal(0)).__getattr__("_add_action").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1510);
      var1.getlocal(0).__getattr__("_group_actions").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(1511);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _remove_action$116(PyFrame var1, ThreadState var2) {
      var1.setline(1514);
      var1.getglobal("super").__call__(var2, var1.getglobal("_ArgumentGroup"), var1.getlocal(0)).__getattr__("_remove_action").__call__(var2, var1.getlocal(1));
      var1.setline(1515);
      var1.getlocal(0).__getattr__("_group_actions").__getattr__("remove").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _MutuallyExclusiveGroup$117(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1520);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$118, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1525);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _add_action$119, (PyObject)null);
      var1.setlocal("_add_action", var4);
      var3 = null;
      var1.setline(1533);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _remove_action$120, (PyObject)null);
      var1.setlocal("_remove_action", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$118(PyFrame var1, ThreadState var2) {
      var1.setline(1521);
      var1.getglobal("super").__call__(var2, var1.getglobal("_MutuallyExclusiveGroup"), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getlocal(1));
      var1.setline(1522);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("required", var3);
      var3 = null;
      var1.setline(1523);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_container", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _add_action$119(PyFrame var1, ThreadState var2) {
      var1.setline(1526);
      PyObject var3;
      if (var1.getlocal(1).__getattr__("required").__nonzero__()) {
         var1.setline(1527);
         var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mutually exclusive arguments must be optional"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1528);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getlocal(2)));
      } else {
         var1.setline(1529);
         var3 = var1.getlocal(0).__getattr__("_container").__getattr__("_add_action").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1530);
         var1.getlocal(0).__getattr__("_group_actions").__getattr__("append").__call__(var2, var1.getlocal(1));
         var1.setline(1531);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _remove_action$120(PyFrame var1, ThreadState var2) {
      var1.setline(1534);
      var1.getlocal(0).__getattr__("_container").__getattr__("_remove_action").__call__(var2, var1.getlocal(1));
      var1.setline(1535);
      var1.getlocal(0).__getattr__("_group_actions").__getattr__("remove").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ArgumentParser$121(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Object for parsing command line strings into Python objects.\n\n    Keyword Arguments:\n        - prog -- The name of the program (default: sys.argv[0])\n        - usage -- A usage message (default: auto-generated from arguments)\n        - description -- A description of what the program does\n        - epilog -- Text following the argument descriptions\n        - parents -- Parsers whose arguments should be copied into this one\n        - formatter_class -- HelpFormatter class for printing help messages\n        - prefix_chars -- Characters that prefix optional arguments\n        - fromfile_prefix_chars -- Characters that prefix files containing\n            additional arguments\n        - argument_default -- The default value for all arguments\n        - conflict_handler -- String indicating how to handle conflicts\n        - add_help -- Add a -h/-help option\n    "));
      var1.setline(1554);
      PyString.fromInterned("Object for parsing command line strings into Python objects.\n\n    Keyword Arguments:\n        - prog -- The name of the program (default: sys.argv[0])\n        - usage -- A usage message (default: auto-generated from arguments)\n        - description -- A description of what the program does\n        - epilog -- Text following the argument descriptions\n        - parents -- Parsers whose arguments should be copied into this one\n        - formatter_class -- HelpFormatter class for printing help messages\n        - prefix_chars -- Characters that prefix optional arguments\n        - fromfile_prefix_chars -- Characters that prefix files containing\n            additional arguments\n        - argument_default -- The default value for all arguments\n        - conflict_handler -- String indicating how to handle conflicts\n        - add_help -- Add a -h/-help option\n    ");
      var1.setline(1556);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), new PyList(Py.EmptyObjects), var1.getname("HelpFormatter"), PyString.fromInterned("-"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("error"), var1.getname("True")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$122, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1634);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_kwargs$124, (PyObject)null);
      var1.setlocal("_get_kwargs", var4);
      var3 = null;
      var1.setline(1649);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_subparsers$125, (PyObject)null);
      var1.setlocal("add_subparsers", var4);
      var3 = null;
      var1.setline(1680);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _add_action$126, (PyObject)null);
      var1.setlocal("_add_action", var4);
      var3 = null;
      var1.setline(1687);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_optional_actions$127, (PyObject)null);
      var1.setlocal("_get_optional_actions", var4);
      var3 = null;
      var1.setline(1692);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_positional_actions$128, (PyObject)null);
      var1.setlocal("_get_positional_actions", var4);
      var3 = null;
      var1.setline(1700);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, parse_args$129, (PyObject)null);
      var1.setlocal("parse_args", var4);
      var3 = null;
      var1.setline(1707);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, parse_known_args$130, (PyObject)null);
      var1.setlocal("parse_known_args", var4);
      var3 = null;
      var1.setline(1742);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse_known_args$131, (PyObject)null);
      var1.setlocal("_parse_known_args", var4);
      var3 = null;
      var1.setline(1988);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read_args_from_files$135, (PyObject)null);
      var1.setlocal("_read_args_from_files", var4);
      var3 = null;
      var1.setline(2017);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, convert_arg_line_to_args$136, (PyObject)null);
      var1.setlocal("convert_arg_line_to_args", var4);
      var3 = null;
      var1.setline(2020);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _match_argument$137, (PyObject)null);
      var1.setlocal("_match_argument", var4);
      var3 = null;
      var1.setline(2039);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _match_arguments_partial$138, (PyObject)null);
      var1.setlocal("_match_arguments_partial", var4);
      var3 = null;
      var1.setline(2055);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _parse_optional$139, (PyObject)null);
      var1.setlocal("_parse_optional", var4);
      var3 = null;
      var1.setline(2112);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_option_tuples$140, (PyObject)null);
      var1.setlocal("_get_option_tuples", var4);
      var3 = null;
      var1.setline(2156);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_nargs_pattern$141, (PyObject)null);
      var1.setlocal("_get_nargs_pattern", var4);
      var3 = null;
      var1.setline(2200);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_values$142, (PyObject)null);
      var1.setlocal("_get_values", var4);
      var3 = null;
      var1.setline(2252);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_value$143, (PyObject)null);
      var1.setlocal("_get_value", var4);
      var3 = null;
      var1.setline(2277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _check_value$144, (PyObject)null);
      var1.setlocal("_check_value", var4);
      var3 = null;
      var1.setline(2287);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_usage$145, (PyObject)null);
      var1.setlocal("format_usage", var4);
      var3 = null;
      var1.setline(2293);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_help$146, (PyObject)null);
      var1.setlocal("format_help", var4);
      var3 = null;
      var1.setline(2316);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_version$147, (PyObject)null);
      var1.setlocal("format_version", var4);
      var3 = null;
      var1.setline(2326);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_formatter$148, (PyObject)null);
      var1.setlocal("_get_formatter", var4);
      var3 = null;
      var1.setline(2332);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, print_usage$149, (PyObject)null);
      var1.setlocal("print_usage", var4);
      var3 = null;
      var1.setline(2337);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, print_help$150, (PyObject)null);
      var1.setlocal("print_help", var4);
      var3 = null;
      var1.setline(2342);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, print_version$151, (PyObject)null);
      var1.setlocal("print_version", var4);
      var3 = null;
      var1.setline(2350);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, _print_message$152, (PyObject)null);
      var1.setlocal("_print_message", var4);
      var3 = null;
      var1.setline(2359);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, exit$153, (PyObject)null);
      var1.setlocal("exit", var4);
      var3 = null;
      var1.setline(2364);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, error$154, PyString.fromInterned("error(message: string)\n\n        Prints a usage message incorporating the message to stderr and\n        exits.\n\n        If you override this in a subclass, it should not return -- it\n        should either exit or raise an exception.\n        "));
      var1.setlocal("error", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$122(PyFrame var1, ThreadState var2) {
      var1.setline(1570);
      PyObject var3 = var1.getlocal(5);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1571);
         var3 = imp.importOne("warnings", var1, -1);
         var1.setlocal(13, var3);
         var3 = null;
         var1.setline(1572);
         var1.getlocal(13).__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("The \"version\" argument to ArgumentParser is deprecated. Please use \"add_argument(..., action='version', version=\"N\", ...)\" instead"), (PyObject)var1.getglobal("DeprecationWarning"));
      }

      var1.setline(1578);
      var3 = var1.getglobal("super").__call__(var2, var1.getglobal("ArgumentParser"), var1.getlocal(0)).__getattr__("__init__");
      var1.setlocal(14, var3);
      var3 = null;
      var1.setline(1579);
      var10000 = var1.getlocal(14);
      PyObject[] var7 = new PyObject[]{var1.getlocal(3), var1.getlocal(8), var1.getlocal(10), var1.getlocal(11)};
      String[] var4 = new String[]{"description", "prefix_chars", "argument_default", "conflict_handler"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(1585);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1586);
         var3 = var1.getglobal("_os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getglobal("_sys").__getattr__("argv").__getitem__(Py.newInteger(0)));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1588);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("prog", var3);
      var3 = null;
      var1.setline(1589);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("usage", var3);
      var3 = null;
      var1.setline(1590);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("epilog", var3);
      var3 = null;
      var1.setline(1591);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("version", var3);
      var3 = null;
      var1.setline(1592);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("formatter_class", var3);
      var3 = null;
      var1.setline(1593);
      var3 = var1.getlocal(9);
      var1.getlocal(0).__setattr__("fromfile_prefix_chars", var3);
      var3 = null;
      var1.setline(1594);
      var3 = var1.getlocal(12);
      var1.getlocal(0).__setattr__("add_help", var3);
      var3 = null;
      var1.setline(1596);
      var3 = var1.getlocal(0).__getattr__("add_argument_group");
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(1597);
      var3 = var1.getlocal(15).__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("positional arguments")));
      var1.getlocal(0).__setattr__("_positionals", var3);
      var3 = null;
      var1.setline(1598);
      var3 = var1.getlocal(15).__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("optional arguments")));
      var1.getlocal(0).__setattr__("_optionals", var3);
      var3 = null;
      var1.setline(1599);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_subparsers", var3);
      var3 = null;
      var1.setline(1602);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, identity$123, (PyObject)null);
      var1.setlocal(16, var10);
      var3 = null;
      var1.setline(1604);
      var1.getlocal(0).__getattr__("register").__call__((ThreadState)var2, PyString.fromInterned("type"), (PyObject)var1.getglobal("None"), (PyObject)var1.getlocal(16));
      var1.setline(1608);
      var1.setline(1608);
      PyString var11 = PyString.fromInterned("-");
      var10000 = var11._in(var1.getlocal(8));
      var3 = null;
      Object var12 = var10000.__nonzero__() ? PyString.fromInterned("-") : var1.getlocal(8).__getitem__(Py.newInteger(0));
      var1.setlocal(17, (PyObject)var12);
      var3 = null;
      var1.setline(1609);
      if (var1.getlocal(0).__getattr__("add_help").__nonzero__()) {
         var1.setline(1610);
         var10000 = var1.getlocal(0).__getattr__("add_argument");
         var7 = new PyObject[]{var1.getlocal(17)._add(PyString.fromInterned("h")), var1.getlocal(17)._mul(Py.newInteger(2))._add(PyString.fromInterned("help")), PyString.fromInterned("help"), var1.getglobal("SUPPRESS"), var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("show this help message and exit"))};
         var4 = new String[]{"action", "default", "help"};
         var10000.__call__(var2, var7, var4);
         var3 = null;
      }

      var1.setline(1614);
      if (var1.getlocal(0).__getattr__("version").__nonzero__()) {
         var1.setline(1615);
         var10000 = var1.getlocal(0).__getattr__("add_argument");
         var7 = new PyObject[]{var1.getlocal(17)._add(PyString.fromInterned("v")), var1.getlocal(17)._mul(Py.newInteger(2))._add(PyString.fromInterned("version")), PyString.fromInterned("version"), var1.getglobal("SUPPRESS"), var1.getlocal(0).__getattr__("version"), var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("show program's version number and exit"))};
         var4 = new String[]{"action", "default", "version", "help"};
         var10000.__call__(var2, var7, var4);
         var3 = null;
      }

      var1.setline(1622);
      var3 = var1.getlocal(6).__iter__();

      while(true) {
         var1.setline(1622);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(18, var8);
         var1.setline(1623);
         var1.getlocal(0).__getattr__("_add_container_actions").__call__(var2, var1.getlocal(18));

         PyException var5;
         try {
            var1.setline(1625);
            PyObject var9 = var1.getlocal(18).__getattr__("_defaults");
            var1.setlocal(19, var9);
            var5 = null;
         } catch (Throwable var6) {
            var5 = Py.setException(var6, var1);
            if (var5.match(var1.getglobal("AttributeError"))) {
               var1.setline(1627);
               continue;
            }

            throw var5;
         }

         var1.setline(1629);
         var1.getlocal(0).__getattr__("_defaults").__getattr__("update").__call__(var2, var1.getlocal(19));
      }
   }

   public PyObject identity$123(PyFrame var1, ThreadState var2) {
      var1.setline(1603);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_kwargs$124(PyFrame var1, ThreadState var2) {
      var1.setline(1635);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("prog"), PyString.fromInterned("usage"), PyString.fromInterned("description"), PyString.fromInterned("version"), PyString.fromInterned("formatter_class"), PyString.fromInterned("conflict_handler"), PyString.fromInterned("add_help")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1644);
      PyList var10000 = new PyList();
      PyObject var5 = var10000.__getattr__("append");
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1644);
      var5 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1644);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1644);
            var1.dellocal(2);
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(1644);
         var1.getlocal(2).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(3))})));
      }
   }

   public PyObject add_subparsers$125(PyFrame var1, ThreadState var2) {
      var1.setline(1650);
      PyObject var3 = var1.getlocal(0).__getattr__("_subparsers");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1651);
         var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot have multiple subparser arguments")));
      }

      var1.setline(1654);
      var1.getlocal(1).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("parser_class"), (PyObject)var1.getglobal("type").__call__(var2, var1.getlocal(0)));
      var1.setline(1656);
      PyString var5 = PyString.fromInterned("title");
      var10000 = var5._in(var1.getlocal(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var5 = PyString.fromInterned("description");
         var10000 = var5._in(var1.getlocal(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1657);
         var3 = var1.getglobal("_").__call__(var2, var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("title"), (PyObject)PyString.fromInterned("subcommands")));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1658);
         var3 = var1.getglobal("_").__call__(var2, var1.getlocal(1).__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("description"), (PyObject)var1.getglobal("None")));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1659);
         var3 = var1.getlocal(0).__getattr__("add_argument_group").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.getlocal(0).__setattr__("_subparsers", var3);
         var3 = null;
      } else {
         var1.setline(1661);
         var3 = var1.getlocal(0).__getattr__("_positionals");
         var1.getlocal(0).__setattr__("_subparsers", var3);
         var3 = null;
      }

      var1.setline(1665);
      var3 = var1.getlocal(1).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prog"));
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1666);
         var3 = var1.getlocal(0).__getattr__("_get_formatter").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1667);
         var3 = var1.getlocal(0).__getattr__("_get_positional_actions").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(1668);
         var3 = var1.getlocal(0).__getattr__("_mutually_exclusive_groups");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1669);
         var1.getlocal(4).__getattr__("add_usage").__call__(var2, var1.getlocal(0).__getattr__("usage"), var1.getlocal(5), var1.getlocal(6), PyString.fromInterned(""));
         var1.setline(1670);
         var3 = var1.getlocal(4).__getattr__("format_help").__call__(var2).__getattr__("strip").__call__(var2);
         var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("prog"), var3);
         var3 = null;
      }

      var1.setline(1673);
      var3 = var1.getlocal(0).__getattr__("_pop_action_class").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("parsers"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1674);
      var10000 = var1.getlocal(7);
      PyObject[] var6 = new PyObject[]{new PyList(Py.EmptyObjects)};
      String[] var4 = new String[]{"option_strings"};
      var10000 = var10000._callextra(var6, var4, (PyObject)null, var1.getlocal(1));
      var3 = null;
      var3 = var10000;
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1675);
      var1.getlocal(0).__getattr__("_subparsers").__getattr__("_add_action").__call__(var2, var1.getlocal(8));
      var1.setline(1678);
      var3 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _add_action$126(PyFrame var1, ThreadState var2) {
      var1.setline(1681);
      if (var1.getlocal(1).__getattr__("option_strings").__nonzero__()) {
         var1.setline(1682);
         var1.getlocal(0).__getattr__("_optionals").__getattr__("_add_action").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(1684);
         var1.getlocal(0).__getattr__("_positionals").__getattr__("_add_action").__call__(var2, var1.getlocal(1));
      }

      var1.setline(1685);
      PyObject var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_optional_actions$127(PyFrame var1, ThreadState var2) {
      var1.setline(1688);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1689);
      var3 = var1.getlocal(0).__getattr__("_actions").__iter__();

      while(true) {
         var1.setline(1689);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1689);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(1690);
         if (var1.getlocal(2).__getattr__("option_strings").__nonzero__()) {
            var1.setline(1688);
            var1.getlocal(1).__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject _get_positional_actions$128(PyFrame var1, ThreadState var2) {
      var1.setline(1693);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1694);
      var3 = var1.getlocal(0).__getattr__("_actions").__iter__();

      while(true) {
         var1.setline(1694);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1694);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(1695);
         if (var1.getlocal(2).__getattr__("option_strings").__not__().__nonzero__()) {
            var1.setline(1693);
            var1.getlocal(1).__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject parse_args$129(PyFrame var1, ThreadState var2) {
      var1.setline(1701);
      PyObject var3 = var1.getlocal(0).__getattr__("parse_known_args").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1702);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1703);
         var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unrecognized arguments: %s"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(1704);
         var1.getlocal(0).__getattr__("error").__call__(var2, var1.getlocal(4)._mod(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(3))));
      }

      var1.setline(1705);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse_known_args$130(PyFrame var1, ThreadState var2) {
      var1.setline(1708);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1710);
         var3 = var1.getglobal("_sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(1713);
         var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1716);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1717);
         var3 = var1.getglobal("Namespace").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1720);
      var3 = var1.getlocal(0).__getattr__("_actions").__iter__();

      while(true) {
         var1.setline(1720);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(1727);
            var3 = var1.getlocal(0).__getattr__("_defaults").__iter__();

            while(true) {
               var1.setline(1727);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  try {
                     var1.setline(1733);
                     var3 = var1.getlocal(0).__getattr__("_parse_known_args").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                     PyObject[] var8 = Py.unpackSequence(var3, 2);
                     var5 = var8[0];
                     var1.setlocal(2, var5);
                     var5 = null;
                     var5 = var8[1];
                     var1.setlocal(1, var5);
                     var5 = null;
                     var3 = null;
                     var1.setline(1734);
                     if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(2), var1.getglobal("_UNRECOGNIZED_ARGS_ATTR")).__nonzero__()) {
                        var1.setline(1735);
                        var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getglobal("_UNRECOGNIZED_ARGS_ATTR")));
                        var1.setline(1736);
                        var1.getglobal("delattr").__call__(var2, var1.getlocal(2), var1.getglobal("_UNRECOGNIZED_ARGS_ATTR"));
                     }

                     var1.setline(1737);
                     PyTuple var9 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
                     var1.f_lasti = -1;
                     return var9;
                  } catch (Throwable var6) {
                     PyException var7 = Py.setException(var6, var1);
                     if (var7.match(var1.getglobal("ArgumentError"))) {
                        var1.setline(1739);
                        var5 = var1.getglobal("_sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(1));
                        var1.setlocal(5, var5);
                        var5 = null;
                        var1.setline(1740);
                        var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(5)));
                        var1.f_lasti = -1;
                        return Py.None;
                     } else {
                        throw var7;
                     }
                  }
               }

               var1.setlocal(4, var4);
               var1.setline(1728);
               if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(2), var1.getlocal(4)).__not__().__nonzero__()) {
                  var1.setline(1729);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(4), var1.getlocal(0).__getattr__("_defaults").__getitem__(var1.getlocal(4)));
               }
            }
         }

         var1.setlocal(3, var4);
         var1.setline(1721);
         var5 = var1.getlocal(3).__getattr__("dest");
         var10000 = var5._isnot(var1.getglobal("SUPPRESS"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1722);
            if (var1.getglobal("hasattr").__call__(var2, var1.getlocal(2), var1.getlocal(3).__getattr__("dest")).__not__().__nonzero__()) {
               var1.setline(1723);
               var5 = var1.getlocal(3).__getattr__("default");
               var10000 = var5._isnot(var1.getglobal("SUPPRESS"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1724);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(2), var1.getlocal(3).__getattr__("dest"), var1.getlocal(3).__getattr__("default"));
               }
            }
         }
      }
   }

   public PyObject _parse_known_args$131(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.to_cell(2, 4);
      var1.to_cell(0, 5);
      var1.setline(1744);
      PyObject var3 = var1.getderef(5).__getattr__("fromfile_prefix_chars");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1745);
         var3 = var1.getderef(5).__getattr__("_read_args_from_files").__call__(var2, var1.getderef(0));
         var1.setderef(0, var3);
         var3 = null;
      }

      var1.setline(1749);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setderef(7, var10);
      var3 = null;
      var1.setline(1750);
      var3 = var1.getderef(5).__getattr__("_mutually_exclusive_groups").__iter__();

      while(true) {
         var1.setline(1750);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         PyObject var6;
         PyObject[] var7;
         PyObject var8;
         PyObject var13;
         if (var4 == null) {
            var1.setline(1760);
            var10 = new PyDictionary(Py.EmptyObjects);
            var1.setderef(6, var10);
            var3 = null;
            var1.setline(1761);
            PyList var11 = new PyList(Py.EmptyObjects);
            var1.setlocal(8, var11);
            var3 = null;
            var1.setline(1762);
            var3 = var1.getglobal("iter").__call__(var2, var1.getderef(0));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(1763);
            var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(9)).__iter__();

            while(true) {
               while(true) {
                  var1.setline(1763);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(1783);
                     var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(8));
                     var1.setderef(2, var3);
                     var3 = null;
                     var1.setline(1786);
                     var3 = var1.getglobal("set").__call__(var2);
                     var1.setderef(8, var3);
                     var3 = null;
                     var1.setline(1787);
                     var3 = var1.getglobal("set").__call__(var2);
                     var1.setderef(3, var3);
                     var3 = null;
                     var1.setline(1789);
                     PyObject[] var15 = new PyObject[]{var1.getglobal("None")};
                     PyObject var10002 = var1.f_globals;
                     PyObject[] var10003 = var15;
                     PyCode var10004 = take_action$132;
                     var15 = new PyObject[]{var1.getclosure(8), var1.getclosure(5), var1.getclosure(3), var1.getclosure(7), var1.getclosure(4)};
                     PyFunction var17 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var15);
                     var1.setderef(10, var17);
                     var3 = null;
                     var1.setline(1810);
                     var15 = Py.EmptyObjects;
                     var10002 = var1.f_globals;
                     var10003 = var15;
                     var10004 = consume_optional$133;
                     var15 = new PyObject[]{var1.getclosure(6), var1.getclosure(5), var1.getclosure(1), var1.getclosure(0), var1.getclosure(2), var1.getclosure(10)};
                     var17 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var15);
                     var1.setlocal(13, var17);
                     var3 = null;
                     var1.setline(1884);
                     var3 = var1.getderef(5).__getattr__("_get_positional_actions").__call__(var2);
                     var1.setderef(9, var3);
                     var3 = null;
                     var1.setline(1887);
                     var15 = Py.EmptyObjects;
                     var10002 = var1.f_globals;
                     var10003 = var15;
                     var10004 = consume_positionals$134;
                     var15 = new PyObject[]{var1.getclosure(5), var1.getclosure(2), var1.getclosure(9), var1.getclosure(0), var1.getclosure(10)};
                     var17 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var15);
                     var1.setlocal(14, var17);
                     var3 = null;
                     var1.setline(1907);
                     var11 = new PyList(Py.EmptyObjects);
                     var1.setderef(1, var11);
                     var3 = null;
                     var1.setline(1908);
                     PyInteger var18 = Py.newInteger(0);
                     var1.setlocal(15, var18);
                     var3 = null;
                     var1.setline(1909);
                     if (var1.getderef(6).__nonzero__()) {
                        var1.setline(1910);
                        var3 = var1.getglobal("max").__call__(var2, var1.getderef(6));
                        var1.setlocal(16, var3);
                        var3 = null;
                     } else {
                        var1.setline(1912);
                        var18 = Py.newInteger(-1);
                        var1.setlocal(16, var18);
                        var3 = null;
                     }

                     while(true) {
                        while(true) {
                           var1.setline(1913);
                           var3 = var1.getlocal(15);
                           var10000 = var3._le(var1.getlocal(16));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              var1.setline(1942);
                              var3 = var1.getlocal(14).__call__(var2, var1.getlocal(15));
                              var1.setlocal(22, var3);
                              var3 = null;
                              var1.setline(1945);
                              var1.getderef(1).__getattr__("extend").__call__(var2, var1.getderef(0).__getslice__(var1.getlocal(22), (PyObject)null, (PyObject)null));
                              var1.setline(1949);
                              if (var1.getderef(9).__nonzero__()) {
                                 var1.setline(1950);
                                 var1.getderef(5).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("too few arguments")));
                              }

                              var1.setline(1953);
                              var3 = var1.getderef(5).__getattr__("_actions").__iter__();

                              while(true) {
                                 var1.setline(1953);
                                 var4 = var3.__iternext__();
                                 if (var4 == null) {
                                    var1.setline(1971);
                                    var3 = var1.getderef(5).__getattr__("_mutually_exclusive_groups").__iter__();

                                    while(true) {
                                       label97:
                                       while(true) {
                                          do {
                                             var1.setline(1971);
                                             var4 = var3.__iternext__();
                                             if (var4 == null) {
                                                var1.setline(1986);
                                                PyTuple var19 = new PyTuple(new PyObject[]{var1.getderef(4), var1.getderef(1)});
                                                var1.f_lasti = -1;
                                                return var19;
                                             }

                                             var1.setlocal(25, var4);
                                             var1.setline(1972);
                                          } while(!var1.getlocal(25).__getattr__("required").__nonzero__());

                                          var1.setline(1973);
                                          var5 = var1.getlocal(25).__getattr__("_group_actions").__iter__();

                                          while(true) {
                                             var1.setline(1973);
                                             var6 = var5.__iternext__();
                                             if (var6 == null) {
                                                var1.setline(1979);
                                                PyList var22 = new PyList();
                                                var13 = var22.__getattr__("append");
                                                var1.setlocal(27, var13);
                                                var7 = null;
                                                var1.setline(1980);
                                                var13 = var1.getlocal(25).__getattr__("_group_actions").__iter__();

                                                while(true) {
                                                   var1.setline(1980);
                                                   var8 = var13.__iternext__();
                                                   if (var8 == null) {
                                                      var1.setline(1980);
                                                      var1.dellocal(27);
                                                      PyList var16 = var22;
                                                      var1.setlocal(26, var16);
                                                      var7 = null;
                                                      var1.setline(1982);
                                                      var13 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("one of the arguments %s is required"));
                                                      var1.setlocal(28, var13);
                                                      var7 = null;
                                                      var1.setline(1983);
                                                      var1.getderef(5).__getattr__("error").__call__(var2, var1.getlocal(28)._mod(PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(26))));
                                                      continue label97;
                                                   }

                                                   var1.setlocal(23, var8);
                                                   var1.setline(1981);
                                                   PyObject var9 = var1.getlocal(23).__getattr__("help");
                                                   PyObject var10001 = var9._isnot(var1.getglobal("SUPPRESS"));
                                                   var9 = null;
                                                   if (var10001.__nonzero__()) {
                                                      var1.setline(1979);
                                                      var1.getlocal(27).__call__(var2, var1.getglobal("_get_action_name").__call__(var2, var1.getlocal(23)));
                                                   }
                                                }
                                             }

                                             var1.setlocal(23, var6);
                                             var1.setline(1974);
                                             var13 = var1.getlocal(23);
                                             var10000 = var13._in(var1.getderef(3));
                                             var7 = null;
                                             if (var10000.__nonzero__()) {
                                                break;
                                             }
                                          }
                                       }
                                    }
                                 }

                                 var1.setlocal(23, var4);
                                 var1.setline(1954);
                                 var5 = var1.getlocal(23);
                                 var10000 = var5._notin(var1.getderef(8));
                                 var5 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(1955);
                                    if (var1.getlocal(23).__getattr__("required").__nonzero__()) {
                                       var1.setline(1956);
                                       var5 = var1.getglobal("_get_action_name").__call__(var2, var1.getlocal(23));
                                       var1.setlocal(24, var5);
                                       var5 = null;
                                       var1.setline(1957);
                                       var1.getderef(5).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("argument %s is required"))._mod(var1.getlocal(24)));
                                    } else {
                                       var1.setline(1963);
                                       var5 = var1.getlocal(23).__getattr__("default");
                                       var10000 = var5._isnot(var1.getglobal("None"));
                                       var5 = null;
                                       if (var10000.__nonzero__()) {
                                          var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(23).__getattr__("default"), var1.getglobal("basestring"));
                                          if (var10000.__nonzero__()) {
                                             var10000 = var1.getglobal("hasattr").__call__(var2, var1.getderef(4), var1.getlocal(23).__getattr__("dest"));
                                             if (var10000.__nonzero__()) {
                                                var5 = var1.getlocal(23).__getattr__("default");
                                                var10000 = var5._is(var1.getglobal("getattr").__call__(var2, var1.getderef(4), var1.getlocal(23).__getattr__("dest")));
                                                var5 = null;
                                             }
                                          }
                                       }

                                       if (var10000.__nonzero__()) {
                                          var1.setline(1967);
                                          var1.getglobal("setattr").__call__(var2, var1.getderef(4), var1.getlocal(23).__getattr__("dest"), var1.getderef(5).__getattr__("_get_value").__call__(var2, var1.getlocal(23), var1.getlocal(23).__getattr__("default")));
                                       }
                                    }
                                 }
                              }
                           }

                           var1.setline(1916);
                           var10000 = var1.getglobal("min");
                           PyList var20 = new PyList();
                           var3 = var20.__getattr__("append");
                           var1.setlocal(18, var3);
                           var3 = null;
                           var1.setline(1918);
                           var3 = var1.getderef(6).__iter__();

                           while(true) {
                              var1.setline(1918);
                              var4 = var3.__iternext__();
                              if (var4 == null) {
                                 var1.setline(1918);
                                 var1.dellocal(18);
                                 var3 = var10000.__call__((ThreadState)var2, (PyObject)var20);
                                 var1.setlocal(17, var3);
                                 var3 = null;
                                 var1.setline(1920);
                                 var3 = var1.getlocal(15);
                                 var10000 = var3._ne(var1.getlocal(17));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(1921);
                                    var3 = var1.getlocal(14).__call__(var2, var1.getlocal(15));
                                    var1.setlocal(20, var3);
                                    var3 = null;
                                    var1.setline(1925);
                                    var3 = var1.getlocal(20);
                                    var10000 = var3._gt(var1.getlocal(15));
                                    var3 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(1926);
                                       var3 = var1.getlocal(20);
                                       var1.setlocal(15, var3);
                                       var3 = null;
                                       break;
                                    }

                                    var1.setline(1929);
                                    var3 = var1.getlocal(20);
                                    var1.setlocal(15, var3);
                                    var3 = null;
                                 }

                                 var1.setline(1933);
                                 var3 = var1.getlocal(15);
                                 var10000 = var3._notin(var1.getderef(6));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(1934);
                                    var3 = var1.getderef(0).__getslice__(var1.getlocal(15), var1.getlocal(17), (PyObject)null);
                                    var1.setlocal(21, var3);
                                    var3 = null;
                                    var1.setline(1935);
                                    var1.getderef(1).__getattr__("extend").__call__(var2, var1.getlocal(21));
                                    var1.setline(1936);
                                    var3 = var1.getlocal(17);
                                    var1.setlocal(15, var3);
                                    var3 = null;
                                 }

                                 var1.setline(1939);
                                 var3 = var1.getlocal(13).__call__(var2, var1.getlocal(15));
                                 var1.setlocal(15, var3);
                                 var3 = null;
                                 break;
                              }

                              var1.setlocal(19, var4);
                              var1.setline(1919);
                              var5 = var1.getlocal(19);
                              PyObject var21 = var5._ge(var1.getlocal(15));
                              var5 = null;
                              if (var21.__nonzero__()) {
                                 var1.setline(1917);
                                 var1.getlocal(18).__call__(var2, var1.getlocal(19));
                              }
                           }
                        }
                     }
                  }

                  PyObject[] var12 = Py.unpackSequence(var4, 2);
                  var6 = var12[0];
                  var1.setlocal(5, var6);
                  var6 = null;
                  var6 = var12[1];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var1.setline(1766);
                  var5 = var1.getlocal(10);
                  var10000 = var5._eq(PyString.fromInterned("--"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1767);
                     var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"));
                     var1.setline(1768);
                     var5 = var1.getlocal(9).__iter__();

                     while(true) {
                        var1.setline(1768);
                        var6 = var5.__iternext__();
                        if (var6 == null) {
                           break;
                        }

                        var1.setlocal(10, var6);
                        var1.setline(1769);
                        var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A"));
                     }
                  } else {
                     var1.setline(1774);
                     var5 = var1.getderef(5).__getattr__("_parse_optional").__call__(var2, var1.getlocal(10));
                     var1.setlocal(11, var5);
                     var5 = null;
                     var1.setline(1775);
                     var5 = var1.getlocal(11);
                     var10000 = var5._is(var1.getglobal("None"));
                     var5 = null;
                     PyString var14;
                     if (var10000.__nonzero__()) {
                        var1.setline(1776);
                        var14 = PyString.fromInterned("A");
                        var1.setlocal(12, var14);
                        var5 = null;
                     } else {
                        var1.setline(1778);
                        var5 = var1.getlocal(11);
                        var1.getderef(6).__setitem__(var1.getlocal(5), var5);
                        var5 = null;
                        var1.setline(1779);
                        var14 = PyString.fromInterned("O");
                        var1.setlocal(12, var14);
                        var5 = null;
                     }

                     var1.setline(1780);
                     var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(12));
                  }
               }
            }
         }

         var1.setlocal(3, var4);
         var1.setline(1751);
         var5 = var1.getlocal(3).__getattr__("_group_actions");
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(1752);
         var5 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(3).__getattr__("_group_actions")).__iter__();

         while(true) {
            var1.setline(1752);
            var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var7 = Py.unpackSequence(var6, 2);
            var8 = var7[0];
            var1.setlocal(5, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(6, var8);
            var8 = null;
            var1.setline(1753);
            var13 = var1.getderef(7).__getattr__("setdefault").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)(new PyList(Py.EmptyObjects)));
            var1.setlocal(7, var13);
            var7 = null;
            var1.setline(1754);
            var1.getlocal(7).__getattr__("extend").__call__(var2, var1.getlocal(4).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null));
            var1.setline(1755);
            var1.getlocal(7).__getattr__("extend").__call__(var2, var1.getlocal(4).__getslice__(var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
         }
      }
   }

   public PyObject take_action$132(PyFrame var1, ThreadState var2) {
      var1.setline(1790);
      var1.getderef(0).__getattr__("add").__call__(var2, var1.getlocal(0));
      var1.setline(1791);
      PyObject var3 = var1.getderef(1).__getattr__("_get_values").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1796);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getlocal(0).__getattr__("default"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1797);
         var1.getderef(2).__getattr__("add").__call__(var2, var1.getlocal(0));
         var1.setline(1798);
         var3 = var1.getderef(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyList(Py.EmptyObjects))).__iter__();

         while(true) {
            var1.setline(1798);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(1799);
            PyObject var5 = var1.getlocal(4);
            var10000 = var5._in(var1.getderef(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1800);
               var5 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not allowed with argument %s"));
               var1.setlocal(5, var5);
               var5 = null;
               var1.setline(1801);
               var5 = var1.getglobal("_get_action_name").__call__(var2, var1.getlocal(4));
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(1802);
               throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(0), var1.getlocal(5)._mod(var1.getlocal(6))));
            }
         }
      }

      var1.setline(1806);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("SUPPRESS"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1807);
         var1.getlocal(0).__call__(var2, var1.getderef(1), var1.getderef(4), var1.getlocal(3), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject consume_optional$133(PyFrame var1, ThreadState var2) {
      var1.setline(1813);
      PyObject var3 = var1.getderef(0).__getitem__(var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1814);
      var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1818);
      var3 = var1.getderef(1).__getattr__("_match_argument");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1819);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var9);
      var3 = null;

      PyObject var10000;
      PyObject var8;
      while(true) {
         var1.setline(1820);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(1823);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1824);
            var1.getderef(2).__getattr__("append").__call__(var2, var1.getderef(3).__getitem__(var1.getlocal(0)));
            var1.setline(1825);
            var3 = var1.getlocal(0)._add(Py.newInteger(1));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1829);
         var8 = var1.getlocal(4);
         var10000 = var8._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1830);
            var8 = var1.getlocal(5).__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("A"));
            var1.setlocal(7, var8);
            var4 = null;
            var1.setline(1835);
            var8 = var1.getderef(1).__getattr__("prefix_chars");
            var1.setlocal(8, var8);
            var4 = null;
            var1.setline(1836);
            var8 = var1.getlocal(7);
            var10000 = var8._eq(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var8 = var1.getlocal(3).__getitem__(Py.newInteger(1));
               var10000 = var8._notin(var1.getlocal(8));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1837);
               var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), new PyList(Py.EmptyObjects), var1.getlocal(3)})));
               var1.setline(1838);
               var8 = var1.getlocal(3).__getitem__(Py.newInteger(0));
               var1.setlocal(9, var8);
               var4 = null;
               var1.setline(1839);
               var8 = var1.getlocal(9)._add(var1.getlocal(4).__getitem__(Py.newInteger(0)));
               var1.setlocal(3, var8);
               var4 = null;
               var1.setline(1840);
               var10000 = var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getglobal("None");
               }

               var8 = var10000;
               var1.setlocal(10, var8);
               var4 = null;
               var1.setline(1841);
               var8 = var1.getderef(1).__getattr__("_option_string_actions");
               var1.setlocal(11, var8);
               var4 = null;
               var1.setline(1842);
               var8 = var1.getlocal(3);
               var10000 = var8._in(var1.getlocal(11));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(1846);
                  var8 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ignored explicit argument %r"));
                  var1.setlocal(12, var8);
                  var4 = null;
                  var1.setline(1847);
                  throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(2), var1.getlocal(12)._mod(var1.getlocal(4))));
               }

               var1.setline(1843);
               var8 = var1.getlocal(11).__getitem__(var1.getlocal(3));
               var1.setlocal(2, var8);
               var4 = null;
               var1.setline(1844);
               var8 = var1.getlocal(10);
               var1.setlocal(4, var8);
               var4 = null;
               continue;
            }

            var1.setline(1851);
            var8 = var1.getlocal(7);
            var10000 = var8._eq(Py.newInteger(1));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(1860);
               var8 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ignored explicit argument %r"));
               var1.setlocal(12, var8);
               var4 = null;
               var1.setline(1861);
               throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(2), var1.getlocal(12)._mod(var1.getlocal(4))));
            }

            var1.setline(1852);
            var8 = var1.getlocal(0)._add(Py.newInteger(1));
            var1.setlocal(13, var8);
            var4 = null;
            var1.setline(1853);
            PyList var10 = new PyList(new PyObject[]{var1.getlocal(4)});
            var1.setlocal(14, var10);
            var4 = null;
            var1.setline(1854);
            var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(14), var1.getlocal(3)})));
            break;
         }

         var1.setline(1867);
         var8 = var1.getlocal(0)._add(Py.newInteger(1));
         var1.setlocal(15, var8);
         var4 = null;
         var1.setline(1868);
         var8 = var1.getderef(4).__getslice__(var1.getlocal(15), (PyObject)null, (PyObject)null);
         var1.setlocal(16, var8);
         var4 = null;
         var1.setline(1869);
         var8 = var1.getlocal(5).__call__(var2, var1.getlocal(2), var1.getlocal(16));
         var1.setlocal(7, var8);
         var4 = null;
         var1.setline(1870);
         var8 = var1.getlocal(15)._add(var1.getlocal(7));
         var1.setlocal(13, var8);
         var4 = null;
         var1.setline(1871);
         var8 = var1.getderef(3).__getslice__(var1.getlocal(15), var1.getlocal(13), (PyObject)null);
         var1.setlocal(14, var8);
         var4 = null;
         var1.setline(1872);
         var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(14), var1.getlocal(3)})));
         break;
      }

      var1.setline(1877);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(6).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(1878);
         var8 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(1878);
            var5 = var8.__iternext__();
            if (var5 == null) {
               var1.setline(1880);
               var3 = var1.getlocal(13);
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 3);
            PyObject var7 = var6[0];
            var1.setlocal(2, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(14, var7);
            var7 = null;
            var7 = var6[2];
            var1.setlocal(3, var7);
            var7 = null;
            var1.setline(1879);
            var1.getderef(5).__call__(var2, var1.getlocal(2), var1.getlocal(14), var1.getlocal(3));
         }
      }
   }

   public PyObject consume_positionals$134(PyFrame var1, ThreadState var2) {
      var1.setline(1889);
      PyObject var3 = var1.getderef(0).__getattr__("_match_arguments_partial");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1890);
      var3 = var1.getderef(1).__getslice__(var1.getlocal(0), (PyObject)null, (PyObject)null);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1891);
      var3 = var1.getlocal(1).__call__(var2, var1.getderef(2), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1895);
      var3 = var1.getglobal("zip").__call__(var2, var1.getderef(2), var1.getlocal(3)).__iter__();

      while(true) {
         var1.setline(1895);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1902);
            var3 = var1.getderef(2).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)null, (PyObject)null);
            var1.getderef(2).__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
            var3 = null;
            var1.setline(1903);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(1896);
         PyObject var7 = var1.getderef(3).__getslice__(var1.getlocal(0), var1.getlocal(0)._add(var1.getlocal(5)), (PyObject)null);
         var1.setlocal(6, var7);
         var5 = null;
         var1.setline(1897);
         var7 = var1.getlocal(0);
         var7 = var7._iadd(var1.getlocal(5));
         var1.setlocal(0, var7);
         var1.setline(1898);
         var1.getderef(4).__call__(var2, var1.getlocal(4), var1.getlocal(6));
      }
   }

   public PyObject _read_args_from_files$135(PyFrame var1, ThreadState var2) {
      var1.setline(1990);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1991);
      PyObject var12 = var1.getlocal(1).__iter__();

      while(true) {
         while(true) {
            var1.setline(1991);
            PyObject var4 = var12.__iternext__();
            if (var4 == null) {
               var1.setline(2015);
               var12 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var12;
            }

            var1.setlocal(3, var4);
            var1.setline(1994);
            PyObject var10000 = var1.getlocal(3).__not__();
            PyObject var5;
            if (!var10000.__nonzero__()) {
               var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
               var10000 = var5._notin(var1.getlocal(0).__getattr__("fromfile_prefix_chars"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1995);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
            } else {
               PyObject var6;
               try {
                  var1.setline(2000);
                  var5 = var1.getglobal("open").__call__(var2, var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
                  var1.setlocal(4, var5);
                  var5 = null;
                  var5 = null;

                  try {
                     var1.setline(2002);
                     PyList var14 = new PyList(Py.EmptyObjects);
                     var1.setlocal(1, var14);
                     var6 = null;
                     var1.setline(2003);
                     var6 = var1.getlocal(4).__getattr__("read").__call__(var2).__getattr__("splitlines").__call__(var2).__iter__();

                     while(true) {
                        var1.setline(2003);
                        PyObject var7 = var6.__iternext__();
                        if (var7 == null) {
                           var1.setline(2006);
                           var6 = var1.getlocal(0).__getattr__("_read_args_from_files").__call__(var2, var1.getlocal(1));
                           var1.setlocal(1, var6);
                           var6 = null;
                           var1.setline(2007);
                           var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(1));
                           break;
                        }

                        var1.setlocal(5, var7);
                        var1.setline(2004);
                        PyObject var8 = var1.getlocal(0).__getattr__("convert_arg_line_to_args").__call__(var2, var1.getlocal(5)).__iter__();

                        while(true) {
                           var1.setline(2004);
                           PyObject var9 = var8.__iternext__();
                           if (var9 == null) {
                              break;
                           }

                           var1.setlocal(6, var9);
                           var1.setline(2005);
                           var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(6));
                        }
                     }
                  } catch (Throwable var10) {
                     Py.addTraceback(var10, var1);
                     var1.setline(2009);
                     var1.getlocal(4).__getattr__("close").__call__(var2);
                     throw (Throwable)var10;
                  }

                  var1.setline(2009);
                  var1.getlocal(4).__getattr__("close").__call__(var2);
               } catch (Throwable var11) {
                  PyException var13 = Py.setException(var11, var1);
                  if (!var13.match(var1.getglobal("IOError"))) {
                     throw var13;
                  }

                  var1.setline(2011);
                  var6 = var1.getglobal("_sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(1));
                  var1.setlocal(7, var6);
                  var6 = null;
                  var1.setline(2012);
                  var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(7)));
               }
            }
         }
      }
   }

   public PyObject convert_arg_line_to_args$136(PyFrame var1, ThreadState var2) {
      var1.setline(2018);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _match_argument$137(PyFrame var1, ThreadState var2) {
      var1.setline(2022);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_nargs_pattern").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2023);
      var3 = var1.getglobal("_re").__getattr__("match").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(2026);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2027);
         PyDictionary var4 = new PyDictionary(new PyObject[]{var1.getglobal("None"), var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expected one argument")), var1.getglobal("OPTIONAL"), var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expected at most one argument")), var1.getglobal("ONE_OR_MORE"), var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expected at least one argument"))});
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(2032);
         var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expected %s argument(s)"))._mod(var1.getlocal(1).__getattr__("nargs"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(2033);
         var3 = var1.getlocal(5).__getattr__("get").__call__(var2, var1.getlocal(1).__getattr__("nargs"), var1.getlocal(6));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(2034);
         throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(1), var1.getlocal(7)));
      } else {
         var1.setline(2037);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _match_arguments_partial$138(PyFrame var1, ThreadState var2) {
      var1.setline(2042);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2043);
      PyObject var7 = var1.getglobal("range").__call__((ThreadState)var2, var1.getglobal("len").__call__(var2, var1.getlocal(1)), (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(-1)).__iter__();

      label33:
      while(true) {
         var1.setline(2043);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(4, var4);
         var1.setline(2044);
         PyObject var5 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null);
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(2045);
         PyObject var10000 = PyString.fromInterned("").__getattr__("join");
         PyList var10002 = new PyList();
         var5 = var10002.__getattr__("append");
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(2046);
         var5 = var1.getlocal(5).__iter__();

         while(true) {
            var1.setline(2046);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(2046);
               var1.dellocal(7);
               var5 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(2047);
               var5 = var1.getglobal("_re").__getattr__("match").__call__(var2, var1.getlocal(6), var1.getlocal(2));
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(2048);
               var5 = var1.getlocal(9);
               var10000 = var5._isnot(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2049);
                  var10000 = var1.getlocal(3).__getattr__("extend");
                  var10002 = new PyList();
                  var5 = var10002.__getattr__("append");
                  var1.setlocal(10, var5);
                  var5 = null;
                  var1.setline(2049);
                  var5 = var1.getlocal(9).__getattr__("groups").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(2049);
                     var6 = var5.__iternext__();
                     if (var6 == null) {
                        var1.setline(2049);
                        var1.dellocal(10);
                        var10000.__call__((ThreadState)var2, (PyObject)var10002);
                        break label33;
                     }

                     var1.setlocal(11, var6);
                     var1.setline(2049);
                     var1.getlocal(10).__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(11)));
                  }
               }
               break;
            }

            var1.setlocal(8, var6);
            var1.setline(2045);
            var1.getlocal(7).__call__(var2, var1.getlocal(0).__getattr__("_get_nargs_pattern").__call__(var2, var1.getlocal(8)));
         }
      }

      var1.setline(2053);
      var7 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject _parse_optional$139(PyFrame var1, ThreadState var2) {
      var1.setline(2057);
      PyObject var8;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(2058);
         var8 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(2061);
         PyObject var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         PyObject var10000 = var4._in(var1.getlocal(0).__getattr__("prefix_chars"));
         var4 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(2062);
            var8 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var8;
         } else {
            var1.setline(2065);
            var4 = var1.getlocal(1);
            var10000 = var4._in(var1.getlocal(0).__getattr__("_option_string_actions"));
            var4 = null;
            PyTuple var3;
            if (var10000.__nonzero__()) {
               var1.setline(2066);
               var4 = var1.getlocal(0).__getattr__("_option_string_actions").__getitem__(var1.getlocal(1));
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(2067);
               var3 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1), var1.getglobal("None")});
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(2070);
               var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
               var10000 = var4._eq(Py.newInteger(1));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2071);
                  var8 = var1.getglobal("None");
                  var1.f_lasti = -1;
                  return var8;
               } else {
                  var1.setline(2074);
                  PyString var10 = PyString.fromInterned("=");
                  var10000 = var10._in(var1.getlocal(1));
                  var4 = null;
                  PyObject[] var5;
                  PyObject var6;
                  if (var10000.__nonzero__()) {
                     var1.setline(2075);
                     var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1));
                     var5 = Py.unpackSequence(var4, 2);
                     var6 = var5[0];
                     var1.setlocal(3, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var4 = null;
                     var1.setline(2076);
                     var4 = var1.getlocal(3);
                     var10000 = var4._in(var1.getlocal(0).__getattr__("_option_string_actions"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2077);
                        var4 = var1.getlocal(0).__getattr__("_option_string_actions").__getitem__(var1.getlocal(3));
                        var1.setlocal(2, var4);
                        var4 = null;
                        var1.setline(2078);
                        var3 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
                        var1.f_lasti = -1;
                        return var3;
                     }
                  }

                  var1.setline(2082);
                  var4 = var1.getlocal(0).__getattr__("_get_option_tuples").__call__(var2, var1.getlocal(1));
                  var1.setlocal(5, var4);
                  var4 = null;
                  var1.setline(2085);
                  var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
                  var10000 = var4._gt(Py.newInteger(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2086);
                     var10000 = PyString.fromInterned(", ").__getattr__("join");
                     PyList var10002 = new PyList();
                     var4 = var10002.__getattr__("append");
                     var1.setlocal(7, var4);
                     var4 = null;
                     var1.setline(2087);
                     var4 = var1.getlocal(5).__iter__();

                     while(true) {
                        var1.setline(2087);
                        PyObject var9 = var4.__iternext__();
                        if (var9 == null) {
                           var1.setline(2087);
                           var1.dellocal(7);
                           var4 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                           var1.setlocal(6, var4);
                           var4 = null;
                           var1.setline(2088);
                           PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6)});
                           var1.setlocal(8, var12);
                           var4 = null;
                           var1.setline(2089);
                           var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ambiguous option: %s could match %s"))._mod(var1.getlocal(8)));
                           break;
                        }

                        PyObject[] var11 = Py.unpackSequence(var9, 3);
                        PyObject var7 = var11[0];
                        var1.setlocal(2, var7);
                        var7 = null;
                        var7 = var11[1];
                        var1.setlocal(3, var7);
                        var7 = null;
                        var7 = var11[2];
                        var1.setlocal(4, var7);
                        var7 = null;
                        var1.setline(2086);
                        var1.getlocal(7).__call__(var2, var1.getlocal(3));
                     }
                  } else {
                     var1.setline(2093);
                     var4 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
                     var10000 = var4._eq(Py.newInteger(1));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2094);
                        var4 = var1.getlocal(5);
                        var5 = Py.unpackSequence(var4, 1);
                        var6 = var5[0];
                        var1.setlocal(9, var6);
                        var6 = null;
                        var4 = null;
                        var1.setline(2095);
                        var8 = var1.getlocal(9);
                        var1.f_lasti = -1;
                        return var8;
                     }
                  }

                  var1.setline(2100);
                  if (var1.getlocal(0).__getattr__("_negative_number_matcher").__getattr__("match").__call__(var2, var1.getlocal(1)).__nonzero__()) {
                     var1.setline(2101);
                     if (var1.getlocal(0).__getattr__("_has_negative_number_optionals").__not__().__nonzero__()) {
                        var1.setline(2102);
                        var8 = var1.getglobal("None");
                        var1.f_lasti = -1;
                        return var8;
                     }
                  }

                  var1.setline(2105);
                  var10 = PyString.fromInterned(" ");
                  var10000 = var10._in(var1.getlocal(1));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2106);
                     var8 = var1.getglobal("None");
                     var1.f_lasti = -1;
                     return var8;
                  } else {
                     var1.setline(2110);
                     var3 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(1), var1.getglobal("None")});
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      }
   }

   public PyObject _get_option_tuples$140(PyFrame var1, ThreadState var2) {
      var1.setline(2113);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2117);
      PyObject var6 = var1.getlocal(0).__getattr__("prefix_chars");
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(2118);
      var6 = var1.getlocal(1).__getitem__(Py.newInteger(0));
      PyObject var10000 = var6._in(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var6 = var1.getlocal(1).__getitem__(Py.newInteger(1));
         var10000 = var6._in(var1.getlocal(3));
         var3 = null;
      }

      PyObject var5;
      PyObject var7;
      PyTuple var9;
      if (var10000.__nonzero__()) {
         var1.setline(2119);
         PyString var8 = PyString.fromInterned("=");
         var10000 = var8._in(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2120);
            var6 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="), (PyObject)Py.newInteger(1));
            PyObject[] var4 = Py.unpackSequence(var6, 2);
            var5 = var4[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
         } else {
            var1.setline(2122);
            var6 = var1.getlocal(1);
            var1.setlocal(4, var6);
            var3 = null;
            var1.setline(2123);
            var6 = var1.getglobal("None");
            var1.setlocal(5, var6);
            var3 = null;
         }

         var1.setline(2124);
         var6 = var1.getlocal(0).__getattr__("_option_string_actions").__iter__();

         while(true) {
            var1.setline(2124);
            var7 = var6.__iternext__();
            if (var7 == null) {
               break;
            }

            var1.setlocal(1, var7);
            var1.setline(2125);
            if (var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(4)).__nonzero__()) {
               var1.setline(2126);
               var5 = var1.getlocal(0).__getattr__("_option_string_actions").__getitem__(var1.getlocal(1));
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(2127);
               var9 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(1), var1.getlocal(5)});
               var1.setlocal(7, var9);
               var5 = null;
               var1.setline(2128);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(7));
            }
         }
      } else {
         var1.setline(2133);
         var6 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var10000 = var6._in(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var6 = var1.getlocal(1).__getitem__(Py.newInteger(1));
            var10000 = var6._notin(var1.getlocal(3));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(2134);
            var6 = var1.getlocal(1);
            var1.setlocal(4, var6);
            var3 = null;
            var1.setline(2135);
            var6 = var1.getglobal("None");
            var1.setlocal(5, var6);
            var3 = null;
            var1.setline(2136);
            var6 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
            var1.setlocal(8, var6);
            var3 = null;
            var1.setline(2137);
            var6 = var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
            var1.setlocal(9, var6);
            var3 = null;
            var1.setline(2139);
            var6 = var1.getlocal(0).__getattr__("_option_string_actions").__iter__();

            while(true) {
               var1.setline(2139);
               var7 = var6.__iternext__();
               if (var7 == null) {
                  break;
               }

               var1.setlocal(1, var7);
               var1.setline(2140);
               var5 = var1.getlocal(1);
               var10000 = var5._eq(var1.getlocal(8));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2141);
                  var5 = var1.getlocal(0).__getattr__("_option_string_actions").__getitem__(var1.getlocal(1));
                  var1.setlocal(6, var5);
                  var5 = null;
                  var1.setline(2142);
                  var9 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(1), var1.getlocal(9)});
                  var1.setlocal(7, var9);
                  var5 = null;
                  var1.setline(2143);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(7));
               } else {
                  var1.setline(2144);
                  if (var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(4)).__nonzero__()) {
                     var1.setline(2145);
                     var5 = var1.getlocal(0).__getattr__("_option_string_actions").__getitem__(var1.getlocal(1));
                     var1.setlocal(6, var5);
                     var5 = null;
                     var1.setline(2146);
                     var9 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(1), var1.getlocal(5)});
                     var1.setlocal(7, var9);
                     var5 = null;
                     var1.setline(2147);
                     var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(7));
                  }
               }
            }
         } else {
            var1.setline(2151);
            var1.getlocal(0).__getattr__("error").__call__(var2, var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected option string: %s"))._mod(var1.getlocal(1)));
         }
      }

      var1.setline(2154);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _get_nargs_pattern$141(PyFrame var1, ThreadState var2) {
      var1.setline(2159);
      PyObject var3 = var1.getlocal(1).__getattr__("nargs");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2162);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyString var4;
      if (var10000.__nonzero__()) {
         var1.setline(2163);
         var4 = PyString.fromInterned("(-*A-*)");
         var1.setlocal(3, var4);
         var3 = null;
      } else {
         var1.setline(2166);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(var1.getglobal("OPTIONAL"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2167);
            var4 = PyString.fromInterned("(-*A?-*)");
            var1.setlocal(3, var4);
            var3 = null;
         } else {
            var1.setline(2170);
            var3 = var1.getlocal(2);
            var10000 = var3._eq(var1.getglobal("ZERO_OR_MORE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2171);
               var4 = PyString.fromInterned("(-*[A-]*)");
               var1.setlocal(3, var4);
               var3 = null;
            } else {
               var1.setline(2174);
               var3 = var1.getlocal(2);
               var10000 = var3._eq(var1.getglobal("ONE_OR_MORE"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(2175);
                  var4 = PyString.fromInterned("(-*A[A-]*)");
                  var1.setlocal(3, var4);
                  var3 = null;
               } else {
                  var1.setline(2178);
                  var3 = var1.getlocal(2);
                  var10000 = var3._eq(var1.getglobal("REMAINDER"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2179);
                     var4 = PyString.fromInterned("([-AO]*)");
                     var1.setlocal(3, var4);
                     var3 = null;
                  } else {
                     var1.setline(2182);
                     var3 = var1.getlocal(2);
                     var10000 = var3._eq(var1.getglobal("PARSER"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(2183);
                        var4 = PyString.fromInterned("(-*A[-AO]*)");
                        var1.setlocal(3, var4);
                        var3 = null;
                     } else {
                        var1.setline(2187);
                        var3 = PyString.fromInterned("(-*%s-*)")._mod(PyString.fromInterned("-*").__getattr__("join").__call__(var2, PyString.fromInterned("A")._mul(var1.getlocal(2))));
                        var1.setlocal(3, var3);
                        var3 = null;
                     }
                  }
               }
            }
         }
      }

      var1.setline(2190);
      if (var1.getlocal(1).__getattr__("option_strings").__nonzero__()) {
         var1.setline(2191);
         var3 = var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-*"), (PyObject)PyString.fromInterned(""));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(2192);
         var3 = var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned(""));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(2195);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_values$142(PyFrame var1, ThreadState var2) {
      var1.setline(2202);
      PyObject var3 = var1.getlocal(1).__getattr__("nargs");
      PyObject var10000 = var3._notin(new PyList(new PyObject[]{var1.getglobal("PARSER"), var1.getglobal("REMAINDER")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(2204);
            var1.getlocal(2).__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--"));
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (!var7.match(var1.getglobal("ValueError"))) {
               throw var7;
            }

            var1.setline(2206);
         }
      }

      var1.setline(2209);
      var10000 = var1.getlocal(2).__not__();
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getattr__("nargs");
         var10000 = var3._eq(var1.getglobal("OPTIONAL"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(2210);
         if (var1.getlocal(1).__getattr__("option_strings").__nonzero__()) {
            var1.setline(2211);
            var3 = var1.getlocal(1).__getattr__("const");
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(2213);
            var3 = var1.getlocal(1).__getattr__("default");
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(2214);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(2215);
            var3 = var1.getlocal(0).__getattr__("_get_value").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(2216);
            var1.getlocal(0).__getattr__("_check_value").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         }
      } else {
         var1.setline(2220);
         var10000 = var1.getlocal(2).__not__();
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getattr__("nargs");
            var10000 = var3._eq(var1.getglobal("ZERO_OR_MORE"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(1).__getattr__("option_strings").__not__();
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(2222);
            var3 = var1.getlocal(1).__getattr__("default");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2223);
               var3 = var1.getlocal(1).__getattr__("default");
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(2225);
               var3 = var1.getlocal(2);
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(2226);
            var1.getlocal(0).__getattr__("_check_value").__call__(var2, var1.getlocal(1), var1.getlocal(3));
         } else {
            var1.setline(2229);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(1).__getattr__("nargs");
               var10000 = var3._in(new PyList(new PyObject[]{var1.getglobal("None"), var1.getglobal("OPTIONAL")}));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(2230);
               var3 = var1.getlocal(2);
               PyObject[] var4 = Py.unpackSequence(var3, 1);
               PyObject var5 = var4[0];
               var1.setlocal(4, var5);
               var5 = null;
               var3 = null;
               var1.setline(2231);
               var3 = var1.getlocal(0).__getattr__("_get_value").__call__(var2, var1.getlocal(1), var1.getlocal(4));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(2232);
               var1.getlocal(0).__getattr__("_check_value").__call__(var2, var1.getlocal(1), var1.getlocal(3));
            } else {
               var1.setline(2235);
               var3 = var1.getlocal(1).__getattr__("nargs");
               var10000 = var3._eq(var1.getglobal("REMAINDER"));
               var3 = null;
               PyObject var8;
               PyList var9;
               PyList var10;
               if (var10000.__nonzero__()) {
                  var1.setline(2236);
                  var10 = new PyList();
                  var3 = var10.__getattr__("append");
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(2236);
                  var3 = var1.getlocal(2).__iter__();

                  while(true) {
                     var1.setline(2236);
                     var8 = var3.__iternext__();
                     if (var8 == null) {
                        var1.setline(2236);
                        var1.dellocal(5);
                        var9 = var10;
                        var1.setlocal(3, var9);
                        var3 = null;
                        break;
                     }

                     var1.setlocal(6, var8);
                     var1.setline(2236);
                     var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("_get_value").__call__(var2, var1.getlocal(1), var1.getlocal(6)));
                  }
               } else {
                  var1.setline(2239);
                  var3 = var1.getlocal(1).__getattr__("nargs");
                  var10000 = var3._eq(var1.getglobal("PARSER"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(2240);
                     var10 = new PyList();
                     var3 = var10.__getattr__("append");
                     var1.setlocal(7, var3);
                     var3 = null;
                     var1.setline(2240);
                     var3 = var1.getlocal(2).__iter__();

                     while(true) {
                        var1.setline(2240);
                        var8 = var3.__iternext__();
                        if (var8 == null) {
                           var1.setline(2240);
                           var1.dellocal(7);
                           var9 = var10;
                           var1.setlocal(3, var9);
                           var3 = null;
                           var1.setline(2241);
                           var1.getlocal(0).__getattr__("_check_value").__call__(var2, var1.getlocal(1), var1.getlocal(3).__getitem__(Py.newInteger(0)));
                           break;
                        }

                        var1.setlocal(6, var8);
                        var1.setline(2240);
                        var1.getlocal(7).__call__(var2, var1.getlocal(0).__getattr__("_get_value").__call__(var2, var1.getlocal(1), var1.getlocal(6)));
                     }
                  } else {
                     var1.setline(2245);
                     var10 = new PyList();
                     var3 = var10.__getattr__("append");
                     var1.setlocal(8, var3);
                     var3 = null;
                     var1.setline(2245);
                     var3 = var1.getlocal(2).__iter__();

                     label68:
                     while(true) {
                        var1.setline(2245);
                        var8 = var3.__iternext__();
                        if (var8 == null) {
                           var1.setline(2245);
                           var1.dellocal(8);
                           var9 = var10;
                           var1.setlocal(3, var9);
                           var3 = null;
                           var1.setline(2246);
                           var3 = var1.getlocal(3).__iter__();

                           while(true) {
                              var1.setline(2246);
                              var8 = var3.__iternext__();
                              if (var8 == null) {
                                 break label68;
                              }

                              var1.setlocal(6, var8);
                              var1.setline(2247);
                              var1.getlocal(0).__getattr__("_check_value").__call__(var2, var1.getlocal(1), var1.getlocal(6));
                           }
                        }

                        var1.setlocal(6, var8);
                        var1.setline(2245);
                        var1.getlocal(8).__call__(var2, var1.getlocal(0).__getattr__("_get_value").__call__(var2, var1.getlocal(1), var1.getlocal(6)));
                     }
                  }
               }
            }
         }
      }

      var1.setline(2250);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_value$143(PyFrame var1, ThreadState var2) {
      var1.setline(2253);
      PyObject var3 = var1.getlocal(0).__getattr__("_registry_get").__call__((ThreadState)var2, PyString.fromInterned("type"), (PyObject)var1.getlocal(1).__getattr__("type"), (PyObject)var1.getlocal(1).__getattr__("type"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(2254);
      if (var1.getglobal("_callable").__call__(var2, var1.getlocal(3)).__not__().__nonzero__()) {
         var1.setline(2255);
         var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%r is not callable"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(2256);
         throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(1), var1.getlocal(4)._mod(var1.getlocal(3))));
      } else {
         try {
            var1.setline(2260);
            var3 = var1.getlocal(3).__call__(var2, var1.getlocal(2));
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            PyObject var4;
            if (var6.match(var1.getglobal("ArgumentTypeError"))) {
               var1.setline(2264);
               var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("type"), (PyObject)PyString.fromInterned("__name__"), (PyObject)var1.getglobal("repr").__call__(var2, var1.getlocal(1).__getattr__("type")));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(2265);
               var4 = var1.getglobal("str").__call__(var2, var1.getglobal("_sys").__getattr__("exc_info").__call__(var2).__getitem__(Py.newInteger(1)));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(2266);
               throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(1), var1.getlocal(4)));
            }

            if (var6.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("ValueError")}))) {
               var1.setline(2270);
               var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1).__getattr__("type"), (PyObject)PyString.fromInterned("__name__"), (PyObject)var1.getglobal("repr").__call__(var2, var1.getlocal(1).__getattr__("type")));
               var1.setlocal(6, var4);
               var4 = null;
               var1.setline(2271);
               var4 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid %s value: %r"));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(2272);
               throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(1), var1.getlocal(4)._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(2)}))));
            }

            throw var6;
         }

         var1.setline(2275);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _check_value$144(PyFrame var1, ThreadState var2) {
      var1.setline(2279);
      PyObject var3 = var1.getlocal(1).__getattr__("choices");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._notin(var1.getlocal(1).__getattr__("choices"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(2280);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("repr"), var1.getlocal(1).__getattr__("choices")))});
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(2281);
         var3 = var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid choice: %r (choose from %s)"))._mod(var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(2282);
         throw Py.makeException(var1.getglobal("ArgumentError").__call__(var2, var1.getlocal(1), var1.getlocal(4)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject format_usage$145(PyFrame var1, ThreadState var2) {
      var1.setline(2288);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_formatter").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2289);
      var1.getlocal(1).__getattr__("add_usage").__call__(var2, var1.getlocal(0).__getattr__("usage"), var1.getlocal(0).__getattr__("_actions"), var1.getlocal(0).__getattr__("_mutually_exclusive_groups"));
      var1.setline(2291);
      var3 = var1.getlocal(1).__getattr__("format_help").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format_help$146(PyFrame var1, ThreadState var2) {
      var1.setline(2294);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_formatter").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2297);
      var1.getlocal(1).__getattr__("add_usage").__call__(var2, var1.getlocal(0).__getattr__("usage"), var1.getlocal(0).__getattr__("_actions"), var1.getlocal(0).__getattr__("_mutually_exclusive_groups"));
      var1.setline(2301);
      var1.getlocal(1).__getattr__("add_text").__call__(var2, var1.getlocal(0).__getattr__("description"));
      var1.setline(2304);
      var3 = var1.getlocal(0).__getattr__("_action_groups").__iter__();

      while(true) {
         var1.setline(2304);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(2311);
            var1.getlocal(1).__getattr__("add_text").__call__(var2, var1.getlocal(0).__getattr__("epilog"));
            var1.setline(2314);
            var3 = var1.getlocal(1).__getattr__("format_help").__call__(var2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(2305);
         var1.getlocal(1).__getattr__("start_section").__call__(var2, var1.getlocal(2).__getattr__("title"));
         var1.setline(2306);
         var1.getlocal(1).__getattr__("add_text").__call__(var2, var1.getlocal(2).__getattr__("description"));
         var1.setline(2307);
         var1.getlocal(1).__getattr__("add_arguments").__call__(var2, var1.getlocal(2).__getattr__("_group_actions"));
         var1.setline(2308);
         var1.getlocal(1).__getattr__("end_section").__call__(var2);
      }
   }

   public PyObject format_version$147(PyFrame var1, ThreadState var2) {
      var1.setline(2317);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2318);
      var1.getlocal(1).__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("The format_version method is deprecated -- the \"version\" argument to ArgumentParser is no longer supported."), (PyObject)var1.getglobal("DeprecationWarning"));
      var1.setline(2322);
      var3 = var1.getlocal(0).__getattr__("_get_formatter").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2323);
      var1.getlocal(2).__getattr__("add_text").__call__(var2, var1.getlocal(0).__getattr__("version"));
      var1.setline(2324);
      var3 = var1.getlocal(2).__getattr__("format_help").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_formatter$148(PyFrame var1, ThreadState var2) {
      var1.setline(2327);
      PyObject var10000 = var1.getlocal(0).__getattr__("formatter_class");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("prog")};
      String[] var4 = new String[]{"prog"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject print_usage$149(PyFrame var1, ThreadState var2) {
      var1.setline(2333);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2334);
         var3 = var1.getglobal("_sys").__getattr__("stdout");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2335);
      var1.getlocal(0).__getattr__("_print_message").__call__(var2, var1.getlocal(0).__getattr__("format_usage").__call__(var2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_help$150(PyFrame var1, ThreadState var2) {
      var1.setline(2338);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2339);
         var3 = var1.getglobal("_sys").__getattr__("stdout");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(2340);
      var1.getlocal(0).__getattr__("_print_message").__call__(var2, var1.getlocal(0).__getattr__("format_help").__call__(var2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_version$151(PyFrame var1, ThreadState var2) {
      var1.setline(2343);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(2344);
      var1.getlocal(2).__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("The print_version method is deprecated -- the \"version\" argument to ArgumentParser is no longer supported."), (PyObject)var1.getglobal("DeprecationWarning"));
      var1.setline(2348);
      var1.getlocal(0).__getattr__("_print_message").__call__(var2, var1.getlocal(0).__getattr__("format_version").__call__(var2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _print_message$152(PyFrame var1, ThreadState var2) {
      var1.setline(2351);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(2352);
         PyObject var3 = var1.getlocal(2);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2353);
            var3 = var1.getglobal("_sys").__getattr__("stderr");
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(2354);
         var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject exit$153(PyFrame var1, ThreadState var2) {
      var1.setline(2360);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(2361);
         var1.getlocal(0).__getattr__("_print_message").__call__(var2, var1.getlocal(2), var1.getglobal("_sys").__getattr__("stderr"));
      }

      var1.setline(2362);
      var1.getglobal("_sys").__getattr__("exit").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$154(PyFrame var1, ThreadState var2) {
      var1.setline(2372);
      PyString.fromInterned("error(message: string)\n\n        Prints a usage message incorporating the message to stderr and\n        exits.\n\n        If you override this in a subclass, it should not return -- it\n        should either exit or raise an exception.\n        ");
      var1.setline(2373);
      var1.getlocal(0).__getattr__("print_usage").__call__(var2, var1.getglobal("_sys").__getattr__("stderr"));
      var1.setline(2374);
      var1.getlocal(0).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)var1.getglobal("_").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%s: error: %s\n"))._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("prog"), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public argparse$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"obj"};
      _callable$1 = Py.newCode(1, var2, var1, "_callable", 95, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _AttributeHolder$2 = Py.newCode(0, var2, var1, "_AttributeHolder", 112, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "type_name", "arg_strings", "arg", "name", "value"};
      __repr__$3 = Py.newCode(1, var2, var1, "__repr__", 121, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_kwargs$4 = Py.newCode(1, var2, var1, "_get_kwargs", 130, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_args$5 = Py.newCode(1, var2, var1, "_get_args", 133, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"namespace", "name", "value"};
      _ensure_value$6 = Py.newCode(3, var2, var1, "_ensure_value", 137, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HelpFormatter$7 = Py.newCode(0, var2, var1, "HelpFormatter", 147, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "prog", "indent_increment", "max_help_position", "width"};
      __init__$8 = Py.newCode(5, var2, var1, "__init__", 154, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _indent$9 = Py.newCode(1, var2, var1, "_indent", 188, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _dedent$10 = Py.newCode(1, var2, var1, "_dedent", 192, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Section$11 = Py.newCode(0, var2, var1, "_Section", 197, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "formatter", "parent", "heading"};
      __init__$12 = Py.newCode(4, var2, var1, "__init__", 199, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "join", "func", "args", "item_help", "_[212_30]", "current_indent", "heading"};
      format_help$13 = Py.newCode(1, var2, var1, "format_help", 205, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "func", "args"};
      _add_item$14 = Py.newCode(3, var2, var1, "_add_item", 230, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "heading", "section"};
      start_section$15 = Py.newCode(2, var2, var1, "start_section", 236, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_section$16 = Py.newCode(1, var2, var1, "end_section", 242, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text"};
      add_text$17 = Py.newCode(2, var2, var1, "add_text", 246, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "usage", "actions", "groups", "prefix", "args"};
      add_usage$18 = Py.newCode(5, var2, var1, "add_usage", 250, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "get_invocation", "invocations", "subaction", "invocation_length", "_[265_37]", "s", "action_length"};
      add_argument$19 = Py.newCode(2, var2, var1, "add_argument", 255, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "actions", "action"};
      add_arguments$20 = Py.newCode(2, var2, var1, "add_arguments", 273, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "help"};
      format_help$21 = Py.newCode(1, var2, var1, "format_help", 280, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "part_strings", "_[288_24]", "part"};
      _join_parts$22 = Py.newCode(2, var2, var1, "_join_parts", 287, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "usage", "actions", "groups", "prefix", "prog", "optionals", "positionals", "action", "format", "action_usage", "_[320_30]", "s", "part_regexp", "opt_usage", "pos_usage", "opt_parts", "pos_parts", "get_lines", "indent", "lines", "parts", "text_width"};
      String[] var10001 = var2;
      argparse$py var10007 = self;
      var2 = new String[]{"text_width"};
      _format_usage$23 = Py.newCode(5, var10001, var1, "_format_usage", 292, false, false, var10007, 23, var2, (String[])null, 1, 4097);
      var2 = new String[]{"parts", "indent", "prefix", "lines", "line", "line_len", "part"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"text_width"};
      get_lines$24 = Py.newCode(3, var10001, var1, "get_lines", 336, false, false, var10007, 24, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "actions", "groups", "group_actions", "inserts", "group", "start", "end", "action", "i", "parts", "part", "option_string", "default", "args_string", "text", "_[466_25]", "item", "open", "close"};
      _format_actions_usage$25 = Py.newCode(3, var2, var1, "_format_actions_usage", 384, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "text_width", "indent"};
      _format_text$26 = Py.newCode(2, var2, var1, "_format_text", 480, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "help_position", "help_width", "action_width", "action_header", "tup", "indent_first", "parts", "help_text", "help_lines", "line", "subaction"};
      _format_action$27 = Py.newCode(2, var2, var1, "_format_action", 487, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "metavar", "parts", "default", "args_string", "option_string"};
      _format_action_invocation$28 = Py.newCode(2, var2, var1, "_format_action_invocation", 534, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "default_metavar", "choice_strs", "_[561_27]", "choice", "format", "result"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result"};
      _metavar_formatter$29 = Py.newCode(3, var10001, var1, "_metavar_formatter", 557, false, false, var10007, 29, var2, (String[])null, 1, 4097);
      var2 = new String[]{"tuple_size"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result"};
      format$30 = Py.newCode(1, var10001, var1, "format", 566, false, false, var10007, 30, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "action", "default_metavar", "get_metavar", "result", "formats", "_[588_23]", "_"};
      _format_args$31 = Py.newCode(3, var2, var1, "_format_args", 573, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "params", "name", "choices_str", "_[601_37]", "c"};
      _expand_help$32 = Py.newCode(2, var2, var1, "_expand_help", 592, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "get_subactions", "subaction"};
      _iter_indented_subactions$33 = Py.newCode(2, var2, var1, "_iter_indented_subactions", 605, false, false, self, 33, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "text", "width"};
      _split_lines$34 = Py.newCode(3, var2, var1, "_split_lines", 616, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "width", "indent"};
      _fill_text$35 = Py.newCode(4, var2, var1, "_fill_text", 620, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action"};
      _get_help_string$36 = Py.newCode(2, var2, var1, "_get_help_string", 625, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RawDescriptionHelpFormatter$37 = Py.newCode(0, var2, var1, "RawDescriptionHelpFormatter", 629, false, false, self, 37, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "text", "width", "indent", "_[637_24]", "line"};
      _fill_text$38 = Py.newCode(4, var2, var1, "_fill_text", 636, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RawTextHelpFormatter$39 = Py.newCode(0, var2, var1, "RawTextHelpFormatter", 640, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "text", "width"};
      _split_lines$40 = Py.newCode(3, var2, var1, "_split_lines", 647, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ArgumentDefaultsHelpFormatter$41 = Py.newCode(0, var2, var1, "ArgumentDefaultsHelpFormatter", 651, false, false, self, 41, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "action", "help", "defaulting_nargs"};
      _get_help_string$42 = Py.newCode(2, var2, var1, "_get_help_string", 658, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"argument"};
      _get_action_name$43 = Py.newCode(1, var2, var1, "_get_action_name", 672, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ArgumentError$44 = Py.newCode(0, var2, var1, "ArgumentError", 685, false, false, self, 44, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "argument", "message"};
      __init__$45 = Py.newCode(3, var2, var1, "__init__", 692, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format"};
      __str__$46 = Py.newCode(1, var2, var1, "__str__", 696, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ArgumentTypeError$47 = Py.newCode(0, var2, var1, "ArgumentTypeError", 705, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Action$48 = Py.newCode(0, var2, var1, "Action", 714, false, false, self, 48, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "dest", "nargs", "const", "default", "type", "choices", "required", "help", "metavar"};
      __init__$49 = Py.newCode(11, var2, var1, "__init__", 765, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names", "_[799_16]", "name"};
      _get_kwargs$50 = Py.newCode(1, var2, var1, "_get_kwargs", 787, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "namespace", "values", "option_string"};
      __call__$51 = Py.newCode(5, var2, var1, "__call__", 801, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _StoreAction$52 = Py.newCode(0, var2, var1, "_StoreAction", 805, false, false, self, 52, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "dest", "nargs", "const", "default", "type", "choices", "required", "help", "metavar"};
      __init__$53 = Py.newCode(11, var2, var1, "__init__", 807, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "namespace", "values", "option_string"};
      __call__$54 = Py.newCode(5, var2, var1, "__call__", 836, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _StoreConstAction$55 = Py.newCode(0, var2, var1, "_StoreConstAction", 840, false, false, self, 55, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "dest", "const", "default", "required", "help", "metavar"};
      __init__$56 = Py.newCode(8, var2, var1, "__init__", 842, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "namespace", "values", "option_string"};
      __call__$57 = Py.newCode(5, var2, var1, "__call__", 859, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _StoreTrueAction$58 = Py.newCode(0, var2, var1, "_StoreTrueAction", 863, false, false, self, 58, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "dest", "default", "required", "help"};
      __init__$59 = Py.newCode(6, var2, var1, "__init__", 865, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _StoreFalseAction$60 = Py.newCode(0, var2, var1, "_StoreFalseAction", 880, false, false, self, 60, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "dest", "default", "required", "help"};
      __init__$61 = Py.newCode(6, var2, var1, "__init__", 882, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _AppendAction$62 = Py.newCode(0, var2, var1, "_AppendAction", 897, false, false, self, 62, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "dest", "nargs", "const", "default", "type", "choices", "required", "help", "metavar"};
      __init__$63 = Py.newCode(11, var2, var1, "__init__", 899, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "namespace", "values", "option_string", "items"};
      __call__$64 = Py.newCode(5, var2, var1, "__call__", 928, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _AppendConstAction$65 = Py.newCode(0, var2, var1, "_AppendConstAction", 934, false, false, self, 65, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "dest", "const", "default", "required", "help", "metavar"};
      __init__$66 = Py.newCode(8, var2, var1, "__init__", 936, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "namespace", "values", "option_string", "items"};
      __call__$67 = Py.newCode(5, var2, var1, "__call__", 954, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _CountAction$68 = Py.newCode(0, var2, var1, "_CountAction", 960, false, false, self, 68, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "dest", "default", "required", "help"};
      __init__$69 = Py.newCode(6, var2, var1, "__init__", 962, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "namespace", "values", "option_string", "new_count"};
      __call__$70 = Py.newCode(5, var2, var1, "__call__", 976, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _HelpAction$71 = Py.newCode(0, var2, var1, "_HelpAction", 981, false, false, self, 71, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "dest", "default", "help"};
      __init__$72 = Py.newCode(5, var2, var1, "__init__", 983, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "namespace", "values", "option_string"};
      __call__$73 = Py.newCode(5, var2, var1, "__call__", 995, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _VersionAction$74 = Py.newCode(0, var2, var1, "_VersionAction", 1000, false, false, self, 74, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option_strings", "version", "dest", "default", "help"};
      __init__$75 = Py.newCode(6, var2, var1, "__init__", 1002, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "namespace", "values", "option_string", "version", "formatter"};
      __call__$76 = Py.newCode(5, var2, var1, "__call__", 1016, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _SubParsersAction$77 = Py.newCode(0, var2, var1, "_SubParsersAction", 1025, false, false, self, 77, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _ChoicesPseudoAction$78 = Py.newCode(0, var2, var1, "_ChoicesPseudoAction", 1027, false, false, self, 78, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "help", "sup"};
      __init__$79 = Py.newCode(3, var2, var1, "__init__", 1029, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option_strings", "prog", "parser_class", "dest", "help", "metavar"};
      __init__$80 = Py.newCode(7, var2, var1, "__init__", 1033, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "kwargs", "help", "choice_action", "parser"};
      add_parser$81 = Py.newCode(3, var2, var1, "add_parser", 1054, false, true, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_subactions$82 = Py.newCode(1, var2, var1, "_get_subactions", 1070, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "parser", "namespace", "values", "option_string", "parser_name", "arg_strings", "tup", "msg", "subnamespace", "key", "value"};
      __call__$83 = Py.newCode(5, var2, var1, "__call__", 1073, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FileType$84 = Py.newCode(0, var2, var1, "FileType", 1109, false, false, self, 84, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "mode", "bufsize"};
      __init__$85 = Py.newCode(3, var2, var1, "__init__", 1122, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "string", "msg", "e", "message"};
      __call__$86 = Py.newCode(2, var2, var1, "__call__", 1126, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "args_str", "_(1146_29)"};
      __repr__$87 = Py.newCode(1, var2, var1, "__repr__", 1144, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "arg"};
      f$88 = Py.newCode(1, var2, var1, "<genexpr>", 1146, false, false, self, 88, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      Namespace$89 = Py.newCode(0, var2, var1, "Namespace", 1153, false, false, self, 89, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "kwargs", "name"};
      __init__$90 = Py.newCode(2, var2, var1, "__init__", 1160, false, true, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$91 = Py.newCode(2, var2, var1, "__eq__", 1166, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$92 = Py.newCode(2, var2, var1, "__ne__", 1171, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key"};
      __contains__$93 = Py.newCode(2, var2, var1, "__contains__", 1176, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ActionsContainer$94 = Py.newCode(0, var2, var1, "_ActionsContainer", 1180, false, false, self, 94, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "description", "prefix_chars", "argument_default", "conflict_handler"};
      __init__$95 = Py.newCode(5, var2, var1, "__init__", 1182, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "registry_name", "value", "object", "registry"};
      register$96 = Py.newCode(4, var2, var1, "register", 1234, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "registry_name", "value", "default"};
      _registry_get$97 = Py.newCode(4, var2, var1, "_registry_get", 1238, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "kwargs", "action"};
      set_defaults$98 = Py.newCode(2, var2, var1, "set_defaults", 1244, false, true, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dest", "action"};
      get_default$99 = Py.newCode(2, var2, var1, "get_default", 1253, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwargs", "chars", "dest", "action_class", "action", "type_func"};
      add_argument$100 = Py.newCode(3, var2, var1, "add_argument", 1263, true, true, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwargs", "group"};
      add_argument_group$101 = Py.newCode(3, var2, var1, "add_argument_group", 1310, true, true, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "kwargs", "group"};
      add_mutually_exclusive_group$102 = Py.newCode(2, var2, var1, "add_mutually_exclusive_group", 1315, false, true, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "option_string"};
      _add_action$103 = Py.newCode(2, var2, var1, "_add_action", 1320, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action"};
      _remove_action$104 = Py.newCode(2, var2, var1, "_remove_action", 1341, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "container", "title_group_map", "group", "msg", "group_map", "action", "mutex_group"};
      _add_container_actions$105 = Py.newCode(2, var2, var1, "_add_container_actions", 1344, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dest", "kwargs", "msg"};
      _get_positional_kwargs$106 = Py.newCode(3, var2, var1, "_get_positional_kwargs", 1384, false, true, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwargs", "option_strings", "long_option_strings", "option_string", "msg", "tup", "dest", "dest_option_string"};
      _get_optional_kwargs$107 = Py.newCode(3, var2, var1, "_get_optional_kwargs", 1400, true, true, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "kwargs", "default", "action"};
      _pop_action_class$108 = Py.newCode(3, var2, var1, "_pop_action_class", 1435, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "handler_func_name", "msg"};
      _get_handler$109 = Py.newCode(1, var2, var1, "_get_handler", 1439, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "confl_optionals", "option_string", "confl_optional", "conflict_handler"};
      _check_conflict$110 = Py.newCode(2, var2, var1, "_check_conflict", 1448, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "conflicting_actions", "message", "conflict_string", "_[1464_37]", "option_string"};
      _handle_conflict_error$111 = Py.newCode(3, var2, var1, "_handle_conflict_error", 1462, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "conflicting_actions", "option_string"};
      _handle_conflict_resolve$112 = Py.newCode(3, var2, var1, "_handle_conflict_resolve", 1469, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ArgumentGroup$113 = Py.newCode(0, var2, var1, "_ArgumentGroup", 1484, false, false, self, 113, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "container", "title", "description", "kwargs", "update", "super_init"};
      __init__$114 = Py.newCode(5, var2, var1, "__init__", 1486, false, true, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action"};
      _add_action$115 = Py.newCode(2, var2, var1, "_add_action", 1508, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action"};
      _remove_action$116 = Py.newCode(2, var2, var1, "_remove_action", 1513, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _MutuallyExclusiveGroup$117 = Py.newCode(0, var2, var1, "_MutuallyExclusiveGroup", 1518, false, false, self, 117, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "container", "required"};
      __init__$118 = Py.newCode(3, var2, var1, "__init__", 1520, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "msg"};
      _add_action$119 = Py.newCode(2, var2, var1, "_add_action", 1525, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action"};
      _remove_action$120 = Py.newCode(2, var2, var1, "_remove_action", 1533, false, false, self, 120, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ArgumentParser$121 = Py.newCode(0, var2, var1, "ArgumentParser", 1538, false, false, self, 121, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "prog", "usage", "description", "epilog", "version", "parents", "formatter_class", "prefix_chars", "fromfile_prefix_chars", "argument_default", "conflict_handler", "add_help", "warnings", "superinit", "add_group", "identity", "default_prefix", "parent", "defaults"};
      __init__$122 = Py.newCode(13, var2, var1, "__init__", 1556, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"string"};
      identity$123 = Py.newCode(1, var2, var1, "identity", 1602, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names", "_[1644_16]", "name"};
      _get_kwargs$124 = Py.newCode(1, var2, var1, "_get_kwargs", 1634, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "kwargs", "title", "description", "formatter", "positionals", "groups", "parsers_class", "action"};
      add_subparsers$125 = Py.newCode(2, var2, var1, "add_subparsers", 1649, false, true, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action"};
      _add_action$126 = Py.newCode(2, var2, var1, "_add_action", 1680, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[1688_16]", "action"};
      _get_optional_actions$127 = Py.newCode(1, var2, var1, "_get_optional_actions", 1687, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[1693_16]", "action"};
      _get_positional_actions$128 = Py.newCode(1, var2, var1, "_get_positional_actions", 1692, false, false, self, 128, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "namespace", "argv", "msg"};
      parse_args$129 = Py.newCode(3, var2, var1, "parse_args", 1700, false, false, self, 129, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "namespace", "action", "dest", "err"};
      parse_known_args$130 = Py.newCode(3, var2, var1, "parse_known_args", 1707, false, false, self, 130, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg_strings", "namespace", "mutex_group", "group_actions", "i", "mutex_action", "conflicts", "arg_string_pattern_parts", "arg_strings_iter", "arg_string", "option_tuple", "pattern", "consume_optional", "consume_positionals", "start_index", "max_option_string_index", "next_option_string_index", "_[1917_16]", "index", "positionals_end_index", "strings", "stop_index", "action", "name", "group", "names", "_[1979_29]", "msg", "extras", "arg_strings_pattern", "seen_non_default_actions", "option_string_indices", "action_conflicts", "seen_actions", "positionals", "take_action"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"arg_strings", "extras", "arg_strings_pattern", "seen_non_default_actions", "namespace", "self", "option_string_indices", "action_conflicts", "seen_actions", "positionals", "take_action"};
      _parse_known_args$131 = Py.newCode(3, var10001, var1, "_parse_known_args", 1742, false, false, var10007, 131, var2, (String[])null, 8, 4097);
      var2 = new String[]{"action", "argument_strings", "option_string", "argument_values", "conflict_action", "msg", "action_name"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"seen_actions", "self", "seen_non_default_actions", "action_conflicts", "namespace"};
      take_action$132 = Py.newCode(3, var10001, var1, "take_action", 1789, false, false, var10007, 132, (String[])null, var2, 0, 4097);
      var2 = new String[]{"start_index", "option_tuple", "action", "option_string", "explicit_arg", "match_argument", "action_tuples", "arg_count", "chars", "char", "new_explicit_arg", "optionals_map", "msg", "stop", "args", "start", "selected_patterns"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"option_string_indices", "self", "extras", "arg_strings", "arg_strings_pattern", "take_action"};
      consume_optional$133 = Py.newCode(1, var10001, var1, "consume_optional", 1810, false, false, var10007, 133, (String[])null, var2, 0, 4097);
      var2 = new String[]{"start_index", "match_partial", "selected_pattern", "arg_counts", "action", "arg_count", "args"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "arg_strings_pattern", "positionals", "arg_strings", "take_action"};
      consume_positionals$134 = Py.newCode(1, var10001, var1, "consume_positionals", 1887, false, false, var10007, 134, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "arg_strings", "new_arg_strings", "arg_string", "args_file", "arg_line", "arg", "err"};
      _read_args_from_files$135 = Py.newCode(2, var2, var1, "_read_args_from_files", 1988, false, false, self, 135, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg_line"};
      convert_arg_line_to_args$136 = Py.newCode(2, var2, var1, "convert_arg_line_to_args", 2017, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "arg_strings_pattern", "nargs_pattern", "match", "nargs_errors", "default", "msg"};
      _match_argument$137 = Py.newCode(3, var2, var1, "_match_argument", 2020, false, false, self, 137, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "actions", "arg_strings_pattern", "result", "i", "actions_slice", "pattern", "_[2045_31]", "action", "match", "_[2049_31]", "string"};
      _match_arguments_partial$138 = Py.newCode(3, var2, var1, "_match_arguments_partial", 2039, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg_string", "action", "option_string", "explicit_arg", "option_tuples", "options", "_[2086_33]", "tup", "option_tuple"};
      _parse_optional$139 = Py.newCode(2, var2, var1, "_parse_optional", 2055, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option_string", "result", "chars", "option_prefix", "explicit_arg", "action", "tup", "short_option_prefix", "short_explicit_arg"};
      _get_option_tuples$140 = Py.newCode(2, var2, var1, "_get_option_tuples", 2112, false, false, self, 140, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "nargs", "nargs_pattern"};
      _get_nargs_pattern$141 = Py.newCode(2, var2, var1, "_get_nargs_pattern", 2156, false, false, self, 141, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "arg_strings", "value", "arg_string", "_[2236_21]", "v", "_[2240_21]", "_[2245_21]"};
      _get_values$142 = Py.newCode(3, var2, var1, "_get_values", 2200, false, false, self, 142, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "arg_string", "type_func", "msg", "result", "name"};
      _get_value$143 = Py.newCode(3, var2, var1, "_get_value", 2252, false, false, self, 143, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "action", "value", "tup", "msg"};
      _check_value$144 = Py.newCode(3, var2, var1, "_check_value", 2277, false, false, self, 144, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "formatter"};
      format_usage$145 = Py.newCode(1, var2, var1, "format_usage", 2287, false, false, self, 145, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "formatter", "action_group"};
      format_help$146 = Py.newCode(1, var2, var1, "format_help", 2293, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "warnings", "formatter"};
      format_version$147 = Py.newCode(1, var2, var1, "format_version", 2316, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _get_formatter$148 = Py.newCode(1, var2, var1, "_get_formatter", 2326, false, false, self, 148, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      print_usage$149 = Py.newCode(2, var2, var1, "print_usage", 2332, false, false, self, 149, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file"};
      print_help$150 = Py.newCode(2, var2, var1, "print_help", 2337, false, false, self, 150, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "warnings"};
      print_version$151 = Py.newCode(2, var2, var1, "print_version", 2342, false, false, self, 151, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "file"};
      _print_message$152 = Py.newCode(3, var2, var1, "_print_message", 2350, false, false, self, 152, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "status", "message"};
      exit$153 = Py.newCode(3, var2, var1, "exit", 2359, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      error$154 = Py.newCode(2, var2, var1, "error", 2364, false, false, self, 154, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new argparse$py("argparse$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(argparse$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._callable$1(var2, var3);
         case 2:
            return this._AttributeHolder$2(var2, var3);
         case 3:
            return this.__repr__$3(var2, var3);
         case 4:
            return this._get_kwargs$4(var2, var3);
         case 5:
            return this._get_args$5(var2, var3);
         case 6:
            return this._ensure_value$6(var2, var3);
         case 7:
            return this.HelpFormatter$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this._indent$9(var2, var3);
         case 10:
            return this._dedent$10(var2, var3);
         case 11:
            return this._Section$11(var2, var3);
         case 12:
            return this.__init__$12(var2, var3);
         case 13:
            return this.format_help$13(var2, var3);
         case 14:
            return this._add_item$14(var2, var3);
         case 15:
            return this.start_section$15(var2, var3);
         case 16:
            return this.end_section$16(var2, var3);
         case 17:
            return this.add_text$17(var2, var3);
         case 18:
            return this.add_usage$18(var2, var3);
         case 19:
            return this.add_argument$19(var2, var3);
         case 20:
            return this.add_arguments$20(var2, var3);
         case 21:
            return this.format_help$21(var2, var3);
         case 22:
            return this._join_parts$22(var2, var3);
         case 23:
            return this._format_usage$23(var2, var3);
         case 24:
            return this.get_lines$24(var2, var3);
         case 25:
            return this._format_actions_usage$25(var2, var3);
         case 26:
            return this._format_text$26(var2, var3);
         case 27:
            return this._format_action$27(var2, var3);
         case 28:
            return this._format_action_invocation$28(var2, var3);
         case 29:
            return this._metavar_formatter$29(var2, var3);
         case 30:
            return this.format$30(var2, var3);
         case 31:
            return this._format_args$31(var2, var3);
         case 32:
            return this._expand_help$32(var2, var3);
         case 33:
            return this._iter_indented_subactions$33(var2, var3);
         case 34:
            return this._split_lines$34(var2, var3);
         case 35:
            return this._fill_text$35(var2, var3);
         case 36:
            return this._get_help_string$36(var2, var3);
         case 37:
            return this.RawDescriptionHelpFormatter$37(var2, var3);
         case 38:
            return this._fill_text$38(var2, var3);
         case 39:
            return this.RawTextHelpFormatter$39(var2, var3);
         case 40:
            return this._split_lines$40(var2, var3);
         case 41:
            return this.ArgumentDefaultsHelpFormatter$41(var2, var3);
         case 42:
            return this._get_help_string$42(var2, var3);
         case 43:
            return this._get_action_name$43(var2, var3);
         case 44:
            return this.ArgumentError$44(var2, var3);
         case 45:
            return this.__init__$45(var2, var3);
         case 46:
            return this.__str__$46(var2, var3);
         case 47:
            return this.ArgumentTypeError$47(var2, var3);
         case 48:
            return this.Action$48(var2, var3);
         case 49:
            return this.__init__$49(var2, var3);
         case 50:
            return this._get_kwargs$50(var2, var3);
         case 51:
            return this.__call__$51(var2, var3);
         case 52:
            return this._StoreAction$52(var2, var3);
         case 53:
            return this.__init__$53(var2, var3);
         case 54:
            return this.__call__$54(var2, var3);
         case 55:
            return this._StoreConstAction$55(var2, var3);
         case 56:
            return this.__init__$56(var2, var3);
         case 57:
            return this.__call__$57(var2, var3);
         case 58:
            return this._StoreTrueAction$58(var2, var3);
         case 59:
            return this.__init__$59(var2, var3);
         case 60:
            return this._StoreFalseAction$60(var2, var3);
         case 61:
            return this.__init__$61(var2, var3);
         case 62:
            return this._AppendAction$62(var2, var3);
         case 63:
            return this.__init__$63(var2, var3);
         case 64:
            return this.__call__$64(var2, var3);
         case 65:
            return this._AppendConstAction$65(var2, var3);
         case 66:
            return this.__init__$66(var2, var3);
         case 67:
            return this.__call__$67(var2, var3);
         case 68:
            return this._CountAction$68(var2, var3);
         case 69:
            return this.__init__$69(var2, var3);
         case 70:
            return this.__call__$70(var2, var3);
         case 71:
            return this._HelpAction$71(var2, var3);
         case 72:
            return this.__init__$72(var2, var3);
         case 73:
            return this.__call__$73(var2, var3);
         case 74:
            return this._VersionAction$74(var2, var3);
         case 75:
            return this.__init__$75(var2, var3);
         case 76:
            return this.__call__$76(var2, var3);
         case 77:
            return this._SubParsersAction$77(var2, var3);
         case 78:
            return this._ChoicesPseudoAction$78(var2, var3);
         case 79:
            return this.__init__$79(var2, var3);
         case 80:
            return this.__init__$80(var2, var3);
         case 81:
            return this.add_parser$81(var2, var3);
         case 82:
            return this._get_subactions$82(var2, var3);
         case 83:
            return this.__call__$83(var2, var3);
         case 84:
            return this.FileType$84(var2, var3);
         case 85:
            return this.__init__$85(var2, var3);
         case 86:
            return this.__call__$86(var2, var3);
         case 87:
            return this.__repr__$87(var2, var3);
         case 88:
            return this.f$88(var2, var3);
         case 89:
            return this.Namespace$89(var2, var3);
         case 90:
            return this.__init__$90(var2, var3);
         case 91:
            return this.__eq__$91(var2, var3);
         case 92:
            return this.__ne__$92(var2, var3);
         case 93:
            return this.__contains__$93(var2, var3);
         case 94:
            return this._ActionsContainer$94(var2, var3);
         case 95:
            return this.__init__$95(var2, var3);
         case 96:
            return this.register$96(var2, var3);
         case 97:
            return this._registry_get$97(var2, var3);
         case 98:
            return this.set_defaults$98(var2, var3);
         case 99:
            return this.get_default$99(var2, var3);
         case 100:
            return this.add_argument$100(var2, var3);
         case 101:
            return this.add_argument_group$101(var2, var3);
         case 102:
            return this.add_mutually_exclusive_group$102(var2, var3);
         case 103:
            return this._add_action$103(var2, var3);
         case 104:
            return this._remove_action$104(var2, var3);
         case 105:
            return this._add_container_actions$105(var2, var3);
         case 106:
            return this._get_positional_kwargs$106(var2, var3);
         case 107:
            return this._get_optional_kwargs$107(var2, var3);
         case 108:
            return this._pop_action_class$108(var2, var3);
         case 109:
            return this._get_handler$109(var2, var3);
         case 110:
            return this._check_conflict$110(var2, var3);
         case 111:
            return this._handle_conflict_error$111(var2, var3);
         case 112:
            return this._handle_conflict_resolve$112(var2, var3);
         case 113:
            return this._ArgumentGroup$113(var2, var3);
         case 114:
            return this.__init__$114(var2, var3);
         case 115:
            return this._add_action$115(var2, var3);
         case 116:
            return this._remove_action$116(var2, var3);
         case 117:
            return this._MutuallyExclusiveGroup$117(var2, var3);
         case 118:
            return this.__init__$118(var2, var3);
         case 119:
            return this._add_action$119(var2, var3);
         case 120:
            return this._remove_action$120(var2, var3);
         case 121:
            return this.ArgumentParser$121(var2, var3);
         case 122:
            return this.__init__$122(var2, var3);
         case 123:
            return this.identity$123(var2, var3);
         case 124:
            return this._get_kwargs$124(var2, var3);
         case 125:
            return this.add_subparsers$125(var2, var3);
         case 126:
            return this._add_action$126(var2, var3);
         case 127:
            return this._get_optional_actions$127(var2, var3);
         case 128:
            return this._get_positional_actions$128(var2, var3);
         case 129:
            return this.parse_args$129(var2, var3);
         case 130:
            return this.parse_known_args$130(var2, var3);
         case 131:
            return this._parse_known_args$131(var2, var3);
         case 132:
            return this.take_action$132(var2, var3);
         case 133:
            return this.consume_optional$133(var2, var3);
         case 134:
            return this.consume_positionals$134(var2, var3);
         case 135:
            return this._read_args_from_files$135(var2, var3);
         case 136:
            return this.convert_arg_line_to_args$136(var2, var3);
         case 137:
            return this._match_argument$137(var2, var3);
         case 138:
            return this._match_arguments_partial$138(var2, var3);
         case 139:
            return this._parse_optional$139(var2, var3);
         case 140:
            return this._get_option_tuples$140(var2, var3);
         case 141:
            return this._get_nargs_pattern$141(var2, var3);
         case 142:
            return this._get_values$142(var2, var3);
         case 143:
            return this._get_value$143(var2, var3);
         case 144:
            return this._check_value$144(var2, var3);
         case 145:
            return this.format_usage$145(var2, var3);
         case 146:
            return this.format_help$146(var2, var3);
         case 147:
            return this.format_version$147(var2, var3);
         case 148:
            return this._get_formatter$148(var2, var3);
         case 149:
            return this.print_usage$149(var2, var3);
         case 150:
            return this.print_help$150(var2, var3);
         case 151:
            return this.print_version$151(var2, var3);
         case 152:
            return this._print_message$152(var2, var3);
         case 153:
            return this.exit$153(var2, var3);
         case 154:
            return this.error$154(var2, var3);
         default:
            return null;
      }
   }
}
