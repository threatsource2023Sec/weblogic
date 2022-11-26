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
@Filename("ConfigParser.py")
public class ConfigParser$py extends PyFunctionTable implements PyRunnable {
   static ConfigParser$py self;
   static final PyCode f$0;
   static final PyCode Error$1;
   static final PyCode _get_message$2;
   static final PyCode _set_message$3;
   static final PyCode __init__$4;
   static final PyCode __repr__$5;
   static final PyCode NoSectionError$6;
   static final PyCode __init__$7;
   static final PyCode DuplicateSectionError$8;
   static final PyCode __init__$9;
   static final PyCode NoOptionError$10;
   static final PyCode __init__$11;
   static final PyCode InterpolationError$12;
   static final PyCode __init__$13;
   static final PyCode InterpolationMissingOptionError$14;
   static final PyCode __init__$15;
   static final PyCode InterpolationSyntaxError$16;
   static final PyCode InterpolationDepthError$17;
   static final PyCode __init__$18;
   static final PyCode ParsingError$19;
   static final PyCode __init__$20;
   static final PyCode append$21;
   static final PyCode MissingSectionHeaderError$22;
   static final PyCode __init__$23;
   static final PyCode RawConfigParser$24;
   static final PyCode __init__$25;
   static final PyCode defaults$26;
   static final PyCode sections$27;
   static final PyCode add_section$28;
   static final PyCode has_section$29;
   static final PyCode options$30;
   static final PyCode read$31;
   static final PyCode readfp$32;
   static final PyCode get$33;
   static final PyCode items$34;
   static final PyCode _get$35;
   static final PyCode getint$36;
   static final PyCode getfloat$37;
   static final PyCode getboolean$38;
   static final PyCode optionxform$39;
   static final PyCode has_option$40;
   static final PyCode set$41;
   static final PyCode write$42;
   static final PyCode remove_option$43;
   static final PyCode remove_section$44;
   static final PyCode _read$45;
   static final PyCode _Chainmap$46;
   static final PyCode __init__$47;
   static final PyCode __getitem__$48;
   static final PyCode keys$49;
   static final PyCode ConfigParser$50;
   static final PyCode get$51;
   static final PyCode items$52;
   static final PyCode _interpolate$53;
   static final PyCode _interpolation_replace$54;
   static final PyCode SafeConfigParser$55;
   static final PyCode _interpolate$56;
   static final PyCode _interpolate_some$57;
   static final PyCode set$58;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Configuration file parser.\n\nA setup file consists of sections, lead by a \"[section]\" header,\nand followed by \"name: value\" entries, with continuations and such in\nthe style of RFC 822.\n\nThe option values can contain format strings which refer to other values in\nthe same section, or values in a special [DEFAULT] section.\n\nFor example:\n\n    something: %(dir)s/whatever\n\nwould resolve the \"%(dir)s\" to the value of dir.  All reference\nexpansions are done late, on demand.\n\nIntrinsic defaults can be specified by passing them into the\nConfigParser constructor as a dictionary.\n\nclass:\n\nConfigParser -- responsible for parsing a list of\n                configuration files, and managing the parsed database.\n\n    methods:\n\n    __init__(defaults=None)\n        create the parser and specify a dictionary of intrinsic defaults.  The\n        keys must be strings, the values must be appropriate for %()s string\n        interpolation.  Note that `__name__' is always an intrinsic default;\n        its value is the section's name.\n\n    sections()\n        return all the configuration section names, sans DEFAULT\n\n    has_section(section)\n        return whether the given section exists\n\n    has_option(section, option)\n        return whether the given option exists in the given section\n\n    options(section)\n        return list of configuration options for the named section\n\n    read(filenames)\n        read and parse the list of named configuration files, given by\n        name.  A single filename is also allowed.  Non-existing files\n        are ignored.  Return list of successfully read files.\n\n    readfp(fp, filename=None)\n        read and parse one configuration file, given as a file object.\n        The filename defaults to fp.name; it is only used in error\n        messages (if fp has no `name' attribute, the string `<???>' is used).\n\n    get(section, option, raw=False, vars=None)\n        return a string value for the named option.  All % interpolations are\n        expanded in the return values, based on the defaults passed into the\n        constructor and the DEFAULT section.  Additional substitutions may be\n        provided using the `vars' argument, which must be a dictionary whose\n        contents override any pre-existing defaults.\n\n    getint(section, options)\n        like get(), but convert value to an integer\n\n    getfloat(section, options)\n        like get(), but convert value to a float\n\n    getboolean(section, options)\n        like get(), but convert value to a boolean (currently case\n        insensitively defined as 0, false, no, off for False, and 1, true,\n        yes, on for True).  Returns False or True.\n\n    items(section, raw=False, vars=None)\n        return a list of tuples with (name, value) for each option\n        in the section.\n\n    remove_section(section)\n        remove the given file section and all its options\n\n    remove_option(section, option)\n        remove the given option from the given section\n\n    set(section, option, value)\n        set the given option\n\n    write(fp)\n        write the configuration state in .ini format\n"));
      var1.setline(88);
      PyString.fromInterned("Configuration file parser.\n\nA setup file consists of sections, lead by a \"[section]\" header,\nand followed by \"name: value\" entries, with continuations and such in\nthe style of RFC 822.\n\nThe option values can contain format strings which refer to other values in\nthe same section, or values in a special [DEFAULT] section.\n\nFor example:\n\n    something: %(dir)s/whatever\n\nwould resolve the \"%(dir)s\" to the value of dir.  All reference\nexpansions are done late, on demand.\n\nIntrinsic defaults can be specified by passing them into the\nConfigParser constructor as a dictionary.\n\nclass:\n\nConfigParser -- responsible for parsing a list of\n                configuration files, and managing the parsed database.\n\n    methods:\n\n    __init__(defaults=None)\n        create the parser and specify a dictionary of intrinsic defaults.  The\n        keys must be strings, the values must be appropriate for %()s string\n        interpolation.  Note that `__name__' is always an intrinsic default;\n        its value is the section's name.\n\n    sections()\n        return all the configuration section names, sans DEFAULT\n\n    has_section(section)\n        return whether the given section exists\n\n    has_option(section, option)\n        return whether the given option exists in the given section\n\n    options(section)\n        return list of configuration options for the named section\n\n    read(filenames)\n        read and parse the list of named configuration files, given by\n        name.  A single filename is also allowed.  Non-existing files\n        are ignored.  Return list of successfully read files.\n\n    readfp(fp, filename=None)\n        read and parse one configuration file, given as a file object.\n        The filename defaults to fp.name; it is only used in error\n        messages (if fp has no `name' attribute, the string `<???>' is used).\n\n    get(section, option, raw=False, vars=None)\n        return a string value for the named option.  All % interpolations are\n        expanded in the return values, based on the defaults passed into the\n        constructor and the DEFAULT section.  Additional substitutions may be\n        provided using the `vars' argument, which must be a dictionary whose\n        contents override any pre-existing defaults.\n\n    getint(section, options)\n        like get(), but convert value to an integer\n\n    getfloat(section, options)\n        like get(), but convert value to a float\n\n    getboolean(section, options)\n        like get(), but convert value to a boolean (currently case\n        insensitively defined as 0, false, no, off for False, and 1, true,\n        yes, on for True).  Returns False or True.\n\n    items(section, raw=False, vars=None)\n        return a list of tuples with (name, value) for each option\n        in the section.\n\n    remove_section(section)\n        remove the given file section and all its options\n\n    remove_option(section, option)\n        remove the given option from the given section\n\n    set(section, option, value)\n        set the given option\n\n    write(fp)\n        write the configuration state in .ini format\n");

      PyException var3;
      PyObject var4;
      PyObject[] var7;
      try {
         var1.setline(91);
         String[] var6 = new String[]{"OrderedDict"};
         var7 = imp.importFrom("collections", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("_default_dict", var4);
         var4 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getname("ImportError"))) {
            throw var3;
         }

         var1.setline(94);
         var4 = var1.getname("dict");
         var1.setlocal("_default_dict", var4);
         var4 = null;
      }

      var1.setline(96);
      PyObject var8 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var8);
      var3 = null;
      var1.setline(98);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("NoSectionError"), PyString.fromInterned("DuplicateSectionError"), PyString.fromInterned("NoOptionError"), PyString.fromInterned("InterpolationError"), PyString.fromInterned("InterpolationDepthError"), PyString.fromInterned("InterpolationSyntaxError"), PyString.fromInterned("ParsingError"), PyString.fromInterned("MissingSectionHeaderError"), PyString.fromInterned("ConfigParser"), PyString.fromInterned("SafeConfigParser"), PyString.fromInterned("RawConfigParser"), PyString.fromInterned("DEFAULTSECT"), PyString.fromInterned("MAX_INTERPOLATION_DEPTH")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(105);
      PyString var10 = PyString.fromInterned("DEFAULT");
      var1.setlocal("DEFAULTSECT", var10);
      var3 = null;
      var1.setline(107);
      PyInteger var11 = Py.newInteger(10);
      var1.setlocal("MAX_INTERPOLATION_DEPTH", var11);
      var3 = null;
      var1.setline(112);
      var7 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("Error", var7, Error$1);
      var1.setlocal("Error", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(139);
      var7 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("NoSectionError", var7, NoSectionError$6);
      var1.setlocal("NoSectionError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(147);
      var7 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("DuplicateSectionError", var7, DuplicateSectionError$8);
      var1.setlocal("DuplicateSectionError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(155);
      var7 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("NoOptionError", var7, NoOptionError$10);
      var1.setlocal("NoOptionError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(165);
      var7 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("InterpolationError", var7, InterpolationError$12);
      var1.setlocal("InterpolationError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(174);
      var7 = new PyObject[]{var1.getname("InterpolationError")};
      var4 = Py.makeClass("InterpolationMissingOptionError", var7, InterpolationMissingOptionError$14);
      var1.setlocal("InterpolationMissingOptionError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(188);
      var7 = new PyObject[]{var1.getname("InterpolationError")};
      var4 = Py.makeClass("InterpolationSyntaxError", var7, InterpolationSyntaxError$16);
      var1.setlocal("InterpolationSyntaxError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(192);
      var7 = new PyObject[]{var1.getname("InterpolationError")};
      var4 = Py.makeClass("InterpolationDepthError", var7, InterpolationDepthError$17);
      var1.setlocal("InterpolationDepthError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(204);
      var7 = new PyObject[]{var1.getname("Error")};
      var4 = Py.makeClass("ParsingError", var7, ParsingError$19);
      var1.setlocal("ParsingError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(217);
      var7 = new PyObject[]{var1.getname("ParsingError")};
      var4 = Py.makeClass("MissingSectionHeaderError", var7, MissingSectionHeaderError$22);
      var1.setlocal("MissingSectionHeaderError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(231);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("RawConfigParser", var7, RawConfigParser$24);
      var1.setlocal("RawConfigParser", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(556);
      var8 = imp.importOneAs("UserDict", var1, -1);
      var1.setlocal("_UserDict", var8);
      var3 = null;
      var1.setline(558);
      var7 = new PyObject[]{var1.getname("_UserDict").__getattr__("DictMixin")};
      var4 = Py.makeClass("_Chainmap", var7, _Chainmap$46);
      var1.setlocal("_Chainmap", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(588);
      var7 = new PyObject[]{var1.getname("RawConfigParser")};
      var4 = Py.makeClass("ConfigParser", var7, ConfigParser$50);
      var1.setlocal("ConfigParser", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(686);
      var7 = new PyObject[]{var1.getname("ConfigParser")};
      var4 = Py.makeClass("SafeConfigParser", var7, SafeConfigParser$55);
      var1.setlocal("SafeConfigParser", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Error$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for ConfigParser exceptions."));
      var1.setline(113);
      PyString.fromInterned("Base class for ConfigParser exceptions.");
      var1.setline(115);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _get_message$2, PyString.fromInterned("Getter for 'message'; needed only to override deprecation in\n        BaseException."));
      var1.setlocal("_get_message", var4);
      var3 = null;
      var1.setline(120);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _set_message$3, PyString.fromInterned("Setter for 'message'; needed only to override deprecation in\n        BaseException."));
      var1.setlocal("_set_message", var4);
      var3 = null;
      var1.setline(128);
      PyObject var5 = var1.getname("property").__call__(var2, var1.getname("_get_message"), var1.getname("_set_message"));
      var1.setlocal("message", var5);
      var3 = null;
      var1.setline(130);
      var3 = new PyObject[]{PyString.fromInterned("")};
      var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(134);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$5, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(137);
      var5 = var1.getname("__repr__");
      var1.setlocal("__str__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _get_message$2(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyString.fromInterned("Getter for 'message'; needed only to override deprecation in\n        BaseException.");
      var1.setline(118);
      PyObject var3 = var1.getlocal(0).__getattr__("_Error__message");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _set_message$3(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyString.fromInterned("Setter for 'message'; needed only to override deprecation in\n        BaseException.");
      var1.setline(123);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_Error__message", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("message", var3);
      var3 = null;
      var1.setline(132);
      var1.getglobal("Exception").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$5(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyObject var3 = var1.getlocal(0).__getattr__("message");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject NoSectionError$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised when no section matches a requested option."));
      var1.setline(140);
      PyString.fromInterned("Raised when no section matches a requested option.");
      var1.setline(142);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      var1.getglobal("Error").__getattr__("__init__").__call__(var2, var1.getlocal(0), PyString.fromInterned("No section: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1)})));
      var1.setline(144);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("section", var3);
      var3 = null;
      var1.setline(145);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DuplicateSectionError$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised when a section is multiply-created."));
      var1.setline(148);
      PyString.fromInterned("Raised when a section is multiply-created.");
      var1.setline(150);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$9, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      var1.getglobal("Error").__getattr__("__init__").__call__(var2, var1.getlocal(0), PyString.fromInterned("Section %r already exists")._mod(var1.getlocal(1)));
      var1.setline(152);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("section", var3);
      var3 = null;
      var1.setline(153);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject NoOptionError$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A requested option was not found."));
      var1.setline(156);
      PyString.fromInterned("A requested option was not found.");
      var1.setline(158);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(159);
      var1.getglobal("Error").__getattr__("__init__").__call__(var2, var1.getlocal(0), PyString.fromInterned("No option %r in section: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.setline(161);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("option", var3);
      var3 = null;
      var1.setline(162);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("section", var3);
      var3 = null;
      var1.setline(163);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject InterpolationError$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Base class for interpolation-related exceptions."));
      var1.setline(166);
      PyString.fromInterned("Base class for interpolation-related exceptions.");
      var1.setline(168);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$13, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      var1.getglobal("Error").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(3));
      var1.setline(170);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("option", var3);
      var3 = null;
      var1.setline(171);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("section", var3);
      var3 = null;
      var1.setline(172);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject InterpolationMissingOptionError$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A string substitution required a setting which was not available."));
      var1.setline(175);
      PyString.fromInterned("A string substitution required a setting which was not available.");
      var1.setline(177);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$15, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$15(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      PyObject var3 = PyString.fromInterned("Bad value substitution:\n\tsection: [%s]\n\toption : %s\n\tkey    : %s\n\trawval : %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1), var1.getlocal(4), var1.getlocal(3)}));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(184);
      var1.getglobal("InterpolationError").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(5));
      var1.setline(185);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("reference", var3);
      var3 = null;
      var1.setline(186);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject InterpolationSyntaxError$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised when the source text into which substitutions are made\n    does not conform to the required syntax."));
      var1.setline(190);
      PyString.fromInterned("Raised when the source text into which substitutions are made\n    does not conform to the required syntax.");
      return var1.getf_locals();
   }

   public PyObject InterpolationDepthError$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised when substitutions are nested too deeply."));
      var1.setline(193);
      PyString.fromInterned("Raised when substitutions are nested too deeply.");
      var1.setline(195);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyObject var3 = PyString.fromInterned("Value interpolation too deeply recursive:\n\tsection: [%s]\n\toption : %s\n\trawval : %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1), var1.getlocal(3)}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(201);
      var1.getglobal("InterpolationError").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(4));
      var1.setline(202);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ParsingError$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised when a configuration file does not follow legal syntax."));
      var1.setline(205);
      PyString.fromInterned("Raised when a configuration file does not follow legal syntax.");
      var1.setline(207);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(213);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, append$21, (PyObject)null);
      var1.setlocal("append", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      var1.getglobal("Error").__getattr__("__init__").__call__(var2, var1.getlocal(0), PyString.fromInterned("File contains parsing errors: %s")._mod(var1.getlocal(1)));
      var1.setline(209);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(210);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"errors", var4);
      var3 = null;
      var1.setline(211);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(0).__setattr__((String)"args", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject append$21(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      var1.getlocal(0).__getattr__("errors").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.setline(215);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "message";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(PyString.fromInterned("\n\t[line %2d]: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var4.__setattr__(var3, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MissingSectionHeaderError$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Raised when a key-value pair is found before any section header."));
      var1.setline(218);
      PyString.fromInterned("Raised when a key-value pair is found before any section header.");
      var1.setline(220);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$23, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      var1.getglobal("Error").__getattr__("__init__").__call__(var2, var1.getlocal(0), PyString.fromInterned("File contains no section headers.\nfile: %s, line: %d\n%r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
      var1.setline(225);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(226);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("lineno", var3);
      var3 = null;
      var1.setline(227);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("line", var3);
      var3 = null;
      var1.setline(228);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var1.getlocal(0).__setattr__((String)"args", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject RawConfigParser$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(232);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("_default_dict"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$25, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(245);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, defaults$26, (PyObject)null);
      var1.setlocal("defaults", var4);
      var3 = null;
      var1.setline(248);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sections$27, PyString.fromInterned("Return a list of section names, excluding [DEFAULT]"));
      var1.setlocal("sections", var4);
      var3 = null;
      var1.setline(253);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add_section$28, PyString.fromInterned("Create a new section in the configuration.\n\n        Raise DuplicateSectionError if a section by the specified name\n        already exists. Raise ValueError if name is DEFAULT or any of it's\n        case-insensitive variants.\n        "));
      var1.setlocal("add_section", var4);
      var3 = null;
      var1.setline(267);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_section$29, PyString.fromInterned("Indicate whether the named section is present in the configuration.\n\n        The DEFAULT section is not acknowledged.\n        "));
      var1.setlocal("has_section", var4);
      var3 = null;
      var1.setline(274);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, options$30, PyString.fromInterned("Return a list of option names for the given section name."));
      var1.setlocal("options", var4);
      var3 = null;
      var1.setline(285);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, read$31, PyString.fromInterned("Read and parse a filename or a list of filenames.\n\n        Files that cannot be opened are silently ignored; this is\n        designed so that you can specify a list of potential\n        configuration file locations (e.g. current directory, user's\n        home directory, systemwide directory), and all existing\n        configuration files in the list will be read.  A single\n        filename may also be given.\n\n        Return list of successfully read files.\n        "));
      var1.setlocal("read", var4);
      var3 = null;
      var1.setline(310);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, readfp$32, PyString.fromInterned("Like read() but the argument must be a file-like object.\n\n        The `fp' argument must have a `readline' method.  Optional\n        second argument is the `filename', which if not given, is\n        taken from fp.name.  If fp has no `name' attribute, `<???>' is\n        used.\n\n        "));
      var1.setlocal("readfp", var4);
      var3 = null;
      var1.setline(326);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get$33, (PyObject)null);
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(342);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, items$34, (PyObject)null);
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(355);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get$35, (PyObject)null);
      var1.setlocal("_get", var4);
      var3 = null;
      var1.setline(358);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getint$36, (PyObject)null);
      var1.setlocal("getint", var4);
      var3 = null;
      var1.setline(361);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getfloat$37, (PyObject)null);
      var1.setlocal("getfloat", var4);
      var3 = null;
      var1.setline(364);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("1"), var1.getname("True"), PyString.fromInterned("yes"), var1.getname("True"), PyString.fromInterned("true"), var1.getname("True"), PyString.fromInterned("on"), var1.getname("True"), PyString.fromInterned("0"), var1.getname("False"), PyString.fromInterned("no"), var1.getname("False"), PyString.fromInterned("false"), var1.getname("False"), PyString.fromInterned("off"), var1.getname("False")});
      var1.setlocal("_boolean_states", var5);
      var3 = null;
      var1.setline(367);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getboolean$38, (PyObject)null);
      var1.setlocal("getboolean", var4);
      var3 = null;
      var1.setline(373);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, optionxform$39, (PyObject)null);
      var1.setlocal("optionxform", var4);
      var3 = null;
      var1.setline(376);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_option$40, PyString.fromInterned("Check for the existence of a given option in a given section."));
      var1.setlocal("has_option", var4);
      var3 = null;
      var1.setline(388);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, set$41, PyString.fromInterned("Set an option."));
      var1.setlocal("set", var4);
      var3 = null;
      var1.setline(399);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write$42, PyString.fromInterned("Write an .ini-format representation of the configuration state."));
      var1.setlocal("write", var4);
      var3 = null;
      var1.setline(416);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove_option$43, PyString.fromInterned("Remove an option."));
      var1.setlocal("remove_option", var4);
      var3 = null;
      var1.setline(431);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, remove_section$44, PyString.fromInterned("Remove a file section."));
      var1.setlocal("remove_section", var4);
      var3 = null;
      var1.setline(441);
      PyObject var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\[(?P<header>[^]]+)\\]"));
      var1.setlocal("SECTCRE", var6);
      var3 = null;
      var1.setline(446);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?P<option>[^:=\\s][^:=]*)\\s*(?P<vi>[:=])\\s*(?P<value>.*)$"));
      var1.setlocal("OPTCRE", var6);
      var3 = null;
      var1.setline(454);
      var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(?P<option>[^:=\\s][^:=]*)\\s*(?:(?P<vi>[:=])\\s*(?P<value>.*))?$"));
      var1.setlocal("OPTCRE_NV", var6);
      var3 = null;
      var1.setline(464);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _read$45, PyString.fromInterned("Parse a sectioned setup file.\n\n        The sections in setup file contains a title line at the top,\n        indicated by a name in square brackets (`[]'), plus key/value\n        options lines, indicated by `name: value' format lines.\n        Continuations are represented by an embedded newline then\n        leading whitespace.  Blank lines, lines beginning with a '#',\n        and just about everything else are ignored.\n        "));
      var1.setlocal("_read", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$25(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_dict", var3);
      var3 = null;
      var1.setline(235);
      var3 = var1.getlocal(0).__getattr__("_dict").__call__(var2);
      var1.getlocal(0).__setattr__("_sections", var3);
      var3 = null;
      var1.setline(236);
      var3 = var1.getlocal(0).__getattr__("_dict").__call__(var2);
      var1.getlocal(0).__setattr__("_defaults", var3);
      var3 = null;
      var1.setline(237);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(238);
         var3 = var1.getlocal(0).__getattr__("OPTCRE_NV");
         var1.getlocal(0).__setattr__("_optcre", var3);
         var3 = null;
      } else {
         var1.setline(240);
         var3 = var1.getlocal(0).__getattr__("OPTCRE");
         var1.getlocal(0).__setattr__("_optcre", var3);
         var3 = null;
      }

      var1.setline(241);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(242);
         var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(242);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(243);
            PyObject var7 = var1.getlocal(5);
            var1.getlocal(0).__getattr__("_defaults").__setitem__(var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(4)), var7);
            var5 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject defaults$26(PyFrame var1, ThreadState var2) {
      var1.setline(246);
      PyObject var3 = var1.getlocal(0).__getattr__("_defaults");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sections$27(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyString.fromInterned("Return a list of section names, excluding [DEFAULT]");
      var1.setline(251);
      PyObject var3 = var1.getlocal(0).__getattr__("_sections").__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject add_section$28(PyFrame var1, ThreadState var2) {
      var1.setline(259);
      PyString.fromInterned("Create a new section in the configuration.\n\n        Raise DuplicateSectionError if a section by the specified name\n        already exists. Raise ValueError if name is DEFAULT or any of it's\n        case-insensitive variants.\n        ");
      var1.setline(260);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._eq(PyString.fromInterned("default"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(261);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Invalid section name: %s")._mod(var1.getlocal(1)));
      } else {
         var1.setline(263);
         var3 = var1.getlocal(1);
         var10000 = var3._in(var1.getlocal(0).__getattr__("_sections"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(264);
            throw Py.makeException(var1.getglobal("DuplicateSectionError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(265);
            var3 = var1.getlocal(0).__getattr__("_dict").__call__(var2);
            var1.getlocal(0).__getattr__("_sections").__setitem__(var1.getlocal(1), var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject has_section$29(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyString.fromInterned("Indicate whether the named section is present in the configuration.\n\n        The DEFAULT section is not acknowledged.\n        ");
      var1.setline(272);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_sections"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject options$30(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      PyString.fromInterned("Return a list of option names for the given section name.");

      PyException var3;
      PyObject var5;
      try {
         var1.setline(277);
         var5 = var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(1)).__getattr__("copy").__call__(var2);
         var1.setlocal(2, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(279);
            throw Py.makeException(var1.getglobal("NoSectionError").__call__(var2, var1.getlocal(1)));
         }

         throw var3;
      }

      var1.setline(280);
      var1.getlocal(2).__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("_defaults"));
      var1.setline(281);
      PyString var6 = PyString.fromInterned("__name__");
      PyObject var10000 = var6._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(282);
         var1.getlocal(2).__delitem__((PyObject)PyString.fromInterned("__name__"));
      }

      var1.setline(283);
      var5 = var1.getlocal(2).__getattr__("keys").__call__(var2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject read$31(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyString.fromInterned("Read and parse a filename or a list of filenames.\n\n        Files that cannot be opened are silently ignored; this is\n        designed so that you can specify a list of potential\n        configuration file locations (e.g. current directory, user's\n        home directory, systemwide directory), and all existing\n        configuration files in the list will be read.  A single\n        filename may also be given.\n\n        Return list of successfully read files.\n        ");
      var1.setline(297);
      PyList var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(298);
         var3 = new PyList(new PyObject[]{var1.getlocal(1)});
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(299);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(300);
      PyObject var7 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(300);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(308);
            var7 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(3, var4);

         PyException var5;
         try {
            var1.setline(302);
            PyObject var8 = var1.getglobal("open").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var8);
            var5 = null;
         } catch (Throwable var6) {
            var5 = Py.setException(var6, var1);
            if (var5.match(var1.getglobal("IOError"))) {
               continue;
            }

            throw var5;
         }

         var1.setline(305);
         var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(4), var1.getlocal(3));
         var1.setline(306);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         var1.setline(307);
         var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject readfp$32(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyString.fromInterned("Like read() but the argument must be a file-like object.\n\n        The `fp' argument must have a `readline' method.  Optional\n        second argument is the `filename', which if not given, is\n        taken from fp.name.  If fp has no `name' attribute, `<???>' is\n        used.\n\n        ");
      var1.setline(319);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(321);
            var3 = var1.getlocal(1).__getattr__("name");
            var1.setlocal(2, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("AttributeError"))) {
               throw var6;
            }

            var1.setline(323);
            PyString var4 = PyString.fromInterned("<???>");
            var1.setlocal(2, var4);
            var4 = null;
         }
      }

      var1.setline(324);
      var1.getlocal(0).__getattr__("_read").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get$33(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyObject var3 = var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(328);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("_sections"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(329);
         var3 = var1.getlocal(1);
         var10000 = var3._ne(var1.getglobal("DEFAULTSECT"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(330);
            throw Py.makeException(var1.getglobal("NoSectionError").__call__(var2, var1.getlocal(1)));
         } else {
            var1.setline(331);
            var3 = var1.getlocal(3);
            var10000 = var3._in(var1.getlocal(0).__getattr__("_defaults"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(332);
               var3 = var1.getlocal(0).__getattr__("_defaults").__getitem__(var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(334);
               throw Py.makeException(var1.getglobal("NoOptionError").__call__(var2, var1.getlocal(2), var1.getlocal(1)));
            }
         }
      } else {
         var1.setline(335);
         PyObject var4 = var1.getlocal(3);
         var10000 = var4._in(var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(1)));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(336);
            var3 = var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(1)).__getitem__(var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(337);
            var4 = var1.getlocal(3);
            var10000 = var4._in(var1.getlocal(0).__getattr__("_defaults"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(338);
               var3 = var1.getlocal(0).__getattr__("_defaults").__getitem__(var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(340);
               throw Py.makeException(var1.getglobal("NoOptionError").__call__(var2, var1.getlocal(2), var1.getlocal(1)));
            }
         }
      }
   }

   public PyObject items$34(PyFrame var1, ThreadState var2) {
      PyObject var10000;
      PyException var3;
      PyObject var6;
      try {
         var1.setline(344);
         var6 = var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (!var3.match(var1.getglobal("KeyError"))) {
            throw var3;
         }

         var1.setline(346);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._ne(var1.getglobal("DEFAULTSECT"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(347);
            throw Py.makeException(var1.getglobal("NoSectionError").__call__(var2, var1.getlocal(1)));
         }

         var1.setline(348);
         var4 = var1.getlocal(0).__getattr__("_dict").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
      }

      var1.setline(349);
      var6 = var1.getlocal(0).__getattr__("_defaults").__getattr__("copy").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(350);
      var1.getlocal(3).__getattr__("update").__call__(var2, var1.getlocal(2));
      var1.setline(351);
      PyString var7 = PyString.fromInterned("__name__");
      var10000 = var7._in(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(352);
         var1.getlocal(3).__delitem__((PyObject)PyString.fromInterned("__name__"));
      }

      var1.setline(353);
      var6 = var1.getlocal(3).__getattr__("items").__call__(var2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject _get$35(PyFrame var1, ThreadState var2) {
      var1.setline(356);
      PyObject var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(3)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getint$36(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyObject var3 = var1.getlocal(0).__getattr__("_get").__call__(var2, var1.getlocal(1), var1.getglobal("int"), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getfloat$37(PyFrame var1, ThreadState var2) {
      var1.setline(362);
      PyObject var3 = var1.getlocal(0).__getattr__("_get").__call__(var2, var1.getlocal(1), var1.getglobal("float"), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getboolean$38(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      PyObject var3 = var1.getlocal(0).__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(369);
      var3 = var1.getlocal(3).__getattr__("lower").__call__(var2);
      PyObject var10000 = var3._notin(var1.getlocal(0).__getattr__("_boolean_states"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(370);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("Not a boolean: %s")._mod(var1.getlocal(3)));
      } else {
         var1.setline(371);
         var3 = var1.getlocal(0).__getattr__("_boolean_states").__getitem__(var1.getlocal(3).__getattr__("lower").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject optionxform$39(PyFrame var1, ThreadState var2) {
      var1.setline(374);
      PyObject var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_option$40(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyString.fromInterned("Check for the existence of a given option in a given section.");
      var1.setline(378);
      PyObject var10000 = var1.getlocal(1).__not__();
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getglobal("DEFAULTSECT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(379);
         var3 = var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(380);
         var3 = var1.getlocal(2);
         var10000 = var3._in(var1.getlocal(0).__getattr__("_defaults"));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(381);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._notin(var1.getlocal(0).__getattr__("_sections"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(382);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(384);
            var4 = var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(385);
            var4 = var1.getlocal(2);
            var10000 = var4._in(var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(1)));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(2);
               var10000 = var4._in(var1.getlocal(0).__getattr__("_defaults"));
               var4 = null;
            }

            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject set$41(PyFrame var1, ThreadState var2) {
      var1.setline(389);
      PyString.fromInterned("Set an option.");
      var1.setline(390);
      PyObject var10000 = var1.getlocal(1).__not__();
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getglobal("DEFAULTSECT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(391);
         var3 = var1.getlocal(0).__getattr__("_defaults");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         try {
            var1.setline(394);
            var3 = var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(1));
            var1.setlocal(4, var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("KeyError"))) {
               var1.setline(396);
               throw Py.makeException(var1.getglobal("NoSectionError").__call__(var2, var1.getlocal(1)));
            }

            throw var5;
         }
      }

      var1.setline(397);
      var3 = var1.getlocal(3);
      var1.getlocal(4).__setitem__(var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(2)), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write$42(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      PyString.fromInterned("Write an .ini-format representation of the configuration state.");
      var1.setline(401);
      PyObject var3;
      PyObject var4;
      PyObject var6;
      if (var1.getlocal(0).__getattr__("_defaults").__nonzero__()) {
         var1.setline(402);
         var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("[%s]\n")._mod(var1.getglobal("DEFAULTSECT")));
         var1.setline(403);
         var3 = var1.getlocal(0).__getattr__("_defaults").__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(403);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(405);
               var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(404);
            var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("%s = %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("str").__call__(var2, var1.getlocal(3)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)PyString.fromInterned("\n\t"))})));
         }
      }

      var1.setline(406);
      var3 = var1.getlocal(0).__getattr__("_sections").__iter__();

      while(true) {
         var1.setline(406);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(407);
         var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("[%s]\n")._mod(var1.getlocal(4)));
         var1.setline(408);
         PyObject var9 = var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(4)).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(408);
            var6 = var9.__iternext__();
            if (var6 == null) {
               var1.setline(414);
               var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(2, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(3, var8);
            var8 = null;
            var1.setline(409);
            PyObject var10 = var1.getlocal(2);
            PyObject var10000 = var10._eq(PyString.fromInterned("__name__"));
            var7 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(411);
               var10 = var1.getlocal(3);
               var10000 = var10._isnot(var1.getglobal("None"));
               var7 = null;
               if (!var10000.__nonzero__()) {
                  var10 = var1.getlocal(0).__getattr__("_optcre");
                  var10000 = var10._eq(var1.getlocal(0).__getattr__("OPTCRE"));
                  var7 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(412);
                  var10 = PyString.fromInterned(" = ").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("str").__call__(var2, var1.getlocal(3)).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)PyString.fromInterned("\n\t"))})));
                  var1.setlocal(2, var10);
                  var7 = null;
               }

               var1.setline(413);
               var1.getlocal(1).__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getlocal(2)));
            }
         }
      }
   }

   public PyObject remove_option$43(PyFrame var1, ThreadState var2) {
      var1.setline(417);
      PyString.fromInterned("Remove an option.");
      var1.setline(418);
      PyObject var10000 = var1.getlocal(1).__not__();
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getglobal("DEFAULTSECT"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(419);
         var3 = var1.getlocal(0).__getattr__("_defaults");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         try {
            var1.setline(422);
            var3 = var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(1));
            var1.setlocal(3, var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("KeyError"))) {
               var1.setline(424);
               throw Py.makeException(var1.getglobal("NoSectionError").__call__(var2, var1.getlocal(1)));
            }

            throw var5;
         }
      }

      var1.setline(425);
      var3 = var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(426);
      var3 = var1.getlocal(2);
      var10000 = var3._in(var1.getlocal(3));
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(427);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(428);
         var1.getlocal(3).__delitem__(var1.getlocal(2));
      }

      var1.setline(429);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject remove_section$44(PyFrame var1, ThreadState var2) {
      var1.setline(432);
      PyString.fromInterned("Remove a file section.");
      var1.setline(433);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_sections"));
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(434);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(435);
         var1.getlocal(0).__getattr__("_sections").__delitem__(var1.getlocal(1));
      }

      var1.setline(436);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _read$45(PyFrame var1, ThreadState var2) {
      var1.setline(473);
      PyString.fromInterned("Parse a sectioned setup file.\n\n        The sections in setup file contains a title line at the top,\n        indicated by a name in square brackets (`[]'), plus key/value\n        options lines, indicated by `name: value' format lines.\n        Continuations are represented by an embedded newline then\n        leading whitespace.  Blank lines, lines beginning with a '#',\n        and just about everything else are ignored.\n        ");
      var1.setline(474);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(475);
      var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(476);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(477);
      var3 = var1.getglobal("None");
      var1.setlocal(6, var3);
      var3 = null;

      PyObject var5;
      PyList var13;
      while(true) {
         var1.setline(478);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(479);
         var3 = var1.getlocal(1).__getattr__("readline").__call__(var2);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(480);
         if (var1.getlocal(7).__not__().__nonzero__()) {
            break;
         }

         var1.setline(482);
         var3 = var1.getlocal(5)._add(Py.newInteger(1));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(484);
         var3 = var1.getlocal(7).__getattr__("strip").__call__(var2);
         PyObject var10000 = var3._eq(PyString.fromInterned(""));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(7).__getitem__(Py.newInteger(0));
            var10000 = var3._in(PyString.fromInterned("#;"));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(486);
            var3 = var1.getlocal(7).__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2);
            var10000 = var3._eq(PyString.fromInterned("rem"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(7).__getitem__(Py.newInteger(0));
               var10000 = var3._in(PyString.fromInterned("rR"));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               var1.setline(490);
               var10000 = var1.getlocal(7).__getitem__(Py.newInteger(0)).__getattr__("isspace").__call__(var2);
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(3);
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(4);
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(491);
                  var3 = var1.getlocal(7).__getattr__("strip").__call__(var2);
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(492);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(493);
                     var1.getlocal(3).__getitem__(var1.getlocal(4)).__getattr__("append").__call__(var2, var1.getlocal(8));
                  }
               } else {
                  var1.setline(497);
                  var3 = var1.getlocal(0).__getattr__("SECTCRE").__getattr__("match").__call__(var2, var1.getlocal(7));
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(498);
                  if (var1.getlocal(9).__nonzero__()) {
                     var1.setline(499);
                     var3 = var1.getlocal(9).__getattr__("group").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("header"));
                     var1.setlocal(10, var3);
                     var3 = null;
                     var1.setline(500);
                     var3 = var1.getlocal(10);
                     var10000 = var3._in(var1.getlocal(0).__getattr__("_sections"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(501);
                        var3 = var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(10));
                        var1.setlocal(3, var3);
                        var3 = null;
                     } else {
                        var1.setline(502);
                        var3 = var1.getlocal(10);
                        var10000 = var3._eq(var1.getglobal("DEFAULTSECT"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(503);
                           var3 = var1.getlocal(0).__getattr__("_defaults");
                           var1.setlocal(3, var3);
                           var3 = null;
                        } else {
                           var1.setline(505);
                           var3 = var1.getlocal(0).__getattr__("_dict").__call__(var2);
                           var1.setlocal(3, var3);
                           var3 = null;
                           var1.setline(506);
                           var3 = var1.getlocal(10);
                           var1.getlocal(3).__setitem__((PyObject)PyString.fromInterned("__name__"), var3);
                           var3 = null;
                           var1.setline(507);
                           var3 = var1.getlocal(3);
                           var1.getlocal(0).__getattr__("_sections").__setitem__(var1.getlocal(10), var3);
                           var3 = null;
                        }
                     }

                     var1.setline(509);
                     var3 = var1.getglobal("None");
                     var1.setlocal(4, var3);
                     var3 = null;
                  } else {
                     var1.setline(511);
                     var3 = var1.getlocal(3);
                     var10000 = var3._is(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(512);
                        throw Py.makeException(var1.getglobal("MissingSectionHeaderError").__call__(var2, var1.getlocal(2), var1.getlocal(5), var1.getlocal(7)));
                     }

                     var1.setline(515);
                     var3 = var1.getlocal(0).__getattr__("_optcre").__getattr__("match").__call__(var2, var1.getlocal(7));
                     var1.setlocal(9, var3);
                     var3 = null;
                     var1.setline(516);
                     if (var1.getlocal(9).__nonzero__()) {
                        var1.setline(517);
                        var3 = var1.getlocal(9).__getattr__("group").__call__((ThreadState)var2, PyString.fromInterned("option"), (PyObject)PyString.fromInterned("vi"), (PyObject)PyString.fromInterned("value"));
                        PyObject[] var4 = Py.unpackSequence(var3, 3);
                        var5 = var4[0];
                        var1.setlocal(4, var5);
                        var5 = null;
                        var5 = var4[1];
                        var1.setlocal(11, var5);
                        var5 = null;
                        var5 = var4[2];
                        var1.setlocal(12, var5);
                        var5 = null;
                        var3 = null;
                        var1.setline(518);
                        var3 = var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(4).__getattr__("rstrip").__call__(var2));
                        var1.setlocal(4, var3);
                        var3 = null;
                        var1.setline(521);
                        var3 = var1.getlocal(12);
                        var10000 = var3._isnot(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(522);
                           var3 = var1.getlocal(11);
                           var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("="), PyString.fromInterned(":")}));
                           var3 = null;
                           PyString var12;
                           if (var10000.__nonzero__()) {
                              var12 = PyString.fromInterned(";");
                              var10000 = var12._in(var1.getlocal(12));
                              var3 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(525);
                              var3 = var1.getlocal(12).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
                              var1.setlocal(13, var3);
                              var3 = null;
                              var1.setline(526);
                              var3 = var1.getlocal(13);
                              var10000 = var3._ne(Py.newInteger(-1));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var10000 = var1.getlocal(12).__getitem__(var1.getlocal(13)._sub(Py.newInteger(1))).__getattr__("isspace").__call__(var2);
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(527);
                                 var3 = var1.getlocal(12).__getslice__((PyObject)null, var1.getlocal(13), (PyObject)null);
                                 var1.setlocal(12, var3);
                                 var3 = null;
                              }
                           }

                           var1.setline(528);
                           var3 = var1.getlocal(12).__getattr__("strip").__call__(var2);
                           var1.setlocal(12, var3);
                           var3 = null;
                           var1.setline(530);
                           var3 = var1.getlocal(12);
                           var10000 = var3._eq(PyString.fromInterned("\"\""));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(531);
                              var12 = PyString.fromInterned("");
                              var1.setlocal(12, var12);
                              var3 = null;
                           }

                           var1.setline(532);
                           var13 = new PyList(new PyObject[]{var1.getlocal(12)});
                           var1.getlocal(3).__setitem__((PyObject)var1.getlocal(4), var13);
                           var3 = null;
                        } else {
                           var1.setline(535);
                           var3 = var1.getlocal(12);
                           var1.getlocal(3).__setitem__(var1.getlocal(4), var3);
                           var3 = null;
                        }
                     } else {
                        var1.setline(541);
                        if (var1.getlocal(6).__not__().__nonzero__()) {
                           var1.setline(542);
                           var3 = var1.getglobal("ParsingError").__call__(var2, var1.getlocal(2));
                           var1.setlocal(6, var3);
                           var3 = null;
                        }

                        var1.setline(543);
                        var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(5), var1.getglobal("repr").__call__(var2, var1.getlocal(7)));
                     }
                  }
               }
            }
         }
      }

      var1.setline(545);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(546);
         throw Py.makeException(var1.getlocal(6));
      } else {
         var1.setline(549);
         var13 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("_defaults")});
         var1.setlocal(14, var13);
         var3 = null;
         var1.setline(550);
         var1.getlocal(14).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("_sections").__getattr__("values").__call__(var2));
         var1.setline(551);
         var3 = var1.getlocal(14).__iter__();

         while(true) {
            var1.setline(551);
            PyObject var10 = var3.__iternext__();
            if (var10 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(15, var10);
            var1.setline(552);
            var5 = var1.getlocal(15).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(552);
               PyObject var6 = var5.__iternext__();
               if (var6 == null) {
                  break;
               }

               PyObject[] var7 = Py.unpackSequence(var6, 2);
               PyObject var8 = var7[0];
               var1.setlocal(16, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(17, var8);
               var8 = null;
               var1.setline(553);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(17), var1.getglobal("list")).__nonzero__()) {
                  var1.setline(554);
                  PyObject var11 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(17));
                  var1.getlocal(15).__setitem__(var1.getlocal(16), var11);
                  var7 = null;
               }
            }
         }
      }
   }

   public PyObject _Chainmap$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Combine multiple mappings for successive lookups.\n\n    For example, to emulate Python's normal lookup sequence:\n\n        import __builtin__\n        pylookup = _Chainmap(locals(), globals(), vars(__builtin__))\n    "));
      var1.setline(565);
      PyString.fromInterned("Combine multiple mappings for successive lookups.\n\n    For example, to emulate Python's normal lookup sequence:\n\n        import __builtin__\n        pylookup = _Chainmap(locals(), globals(), vars(__builtin__))\n    ");
      var1.setline(567);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$47, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(570);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$48, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(578);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, keys$49, (PyObject)null);
      var1.setlocal("keys", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$47(PyFrame var1, ThreadState var2) {
      var1.setline(568);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_maps", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$48(PyFrame var1, ThreadState var2) {
      var1.setline(571);
      PyObject var3 = var1.getlocal(0).__getattr__("_maps").__iter__();

      while(true) {
         var1.setline(571);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(576);
            throw Py.makeException(var1.getglobal("KeyError").__call__(var2, var1.getlocal(1)));
         }

         var1.setlocal(2, var4);

         try {
            var1.setline(573);
            PyObject var5 = var1.getlocal(2).__getitem__(var1.getlocal(1));
            var1.f_lasti = -1;
            return var5;
         } catch (Throwable var7) {
            PyException var6 = Py.setException(var7, var1);
            if (!var6.match(var1.getglobal("KeyError"))) {
               throw var6;
            }

            var1.setline(575);
         }
      }
   }

   public PyObject keys$49(PyFrame var1, ThreadState var2) {
      var1.setline(579);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(580);
      PyObject var8 = var1.getglobal("set").__call__(var2);
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(581);
      var8 = var1.getlocal(0).__getattr__("_maps").__iter__();

      while(true) {
         var1.setline(581);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(586);
            var8 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(3, var4);
         var1.setline(582);
         PyObject var5 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(582);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(4, var6);
            var1.setline(583);
            PyObject var7 = var1.getlocal(4);
            PyObject var10000 = var7._notin(var1.getlocal(2));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(584);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
               var1.setline(585);
               var1.getlocal(2).__getattr__("add").__call__(var2, var1.getlocal(4));
            }
         }
      }
   }

   public PyObject ConfigParser$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(590);
      PyObject[] var3 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, get$51, PyString.fromInterned("Get an option value for a given section.\n\n        If `vars' is provided, it must be a dictionary. The option is looked up\n        in `vars' (if provided), `section', and in `defaults' in that order.\n\n        All % interpolations are expanded in the return values, unless the\n        optional argument `raw' is true. Values for interpolation keys are\n        looked up in the same manner as the option.\n\n        The section DEFAULT is special.\n        "));
      var1.setlocal("get", var4);
      var3 = null;
      var1.setline(625);
      var3 = new PyObject[]{var1.getname("False"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, items$52, PyString.fromInterned("Return a list of tuples with (name, value) for each option\n        in the section.\n\n        All % interpolations are expanded in the return values, based on the\n        defaults passed into the constructor, unless the optional argument\n        `raw' is true.  Additional substitutions may be provided using the\n        `vars' argument, which must be a dictionary whose contents overrides\n        any pre-existing defaults.\n\n        The section DEFAULT is special.\n        "));
      var1.setlocal("items", var4);
      var3 = null;
      var1.setline(657);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _interpolate$53, (PyObject)null);
      var1.setlocal("_interpolate", var4);
      var3 = null;
      var1.setline(676);
      PyObject var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%\\(([^)]*)\\)s|."));
      var1.setlocal("_KEYCRE", var5);
      var3 = null;
      var1.setline(678);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _interpolation_replace$54, (PyObject)null);
      var1.setlocal("_interpolation_replace", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject get$51(PyFrame var1, ThreadState var2) {
      var1.setline(601);
      PyString.fromInterned("Get an option value for a given section.\n\n        If `vars' is provided, it must be a dictionary. The option is looked up\n        in `vars' (if provided), `section', and in `defaults' in that order.\n\n        All % interpolations are expanded in the return values, unless the\n        optional argument `raw' is true. Values for interpolation keys are\n        looked up in the same manner as the option.\n\n        The section DEFAULT is special.\n        ");
      var1.setline(602);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var3);
      var3 = null;

      PyObject var10000;
      PyObject var4;
      PyException var9;
      PyObject var10;
      try {
         var1.setline(604);
         var10 = var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(1));
         var1.setlocal(5, var10);
         var3 = null;
      } catch (Throwable var8) {
         var9 = Py.setException(var8, var1);
         if (!var9.match(var1.getglobal("KeyError"))) {
            throw var9;
         }

         var1.setline(606);
         var4 = var1.getlocal(1);
         var10000 = var4._ne(var1.getglobal("DEFAULTSECT"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(607);
            throw Py.makeException(var1.getglobal("NoSectionError").__call__(var2, var1.getlocal(1)));
         }
      }

      var1.setline(609);
      var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(610);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(611);
         var10 = var1.getlocal(4).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(611);
            var4 = var10.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(8, var6);
            var6 = null;
            var1.setline(612);
            PyObject var11 = var1.getlocal(8);
            var1.getlocal(6).__setitem__(var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(7)), var11);
            var5 = null;
         }
      }

      var1.setline(613);
      var10 = var1.getglobal("_Chainmap").__call__(var2, var1.getlocal(6), var1.getlocal(5), var1.getlocal(0).__getattr__("_defaults"));
      var1.setlocal(9, var10);
      var3 = null;
      var1.setline(614);
      var10 = var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var10);
      var3 = null;

      try {
         var1.setline(616);
         var10 = var1.getlocal(9).__getitem__(var1.getlocal(2));
         var1.setlocal(8, var10);
         var3 = null;
      } catch (Throwable var7) {
         var9 = Py.setException(var7, var1);
         if (var9.match(var1.getglobal("KeyError"))) {
            var1.setline(618);
            throw Py.makeException(var1.getglobal("NoOptionError").__call__(var2, var1.getlocal(2), var1.getlocal(1)));
         }

         throw var9;
      }

      var1.setline(620);
      var10000 = var1.getlocal(3);
      if (!var10000.__nonzero__()) {
         var10 = var1.getlocal(8);
         var10000 = var10._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(621);
         var10 = var1.getlocal(8);
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(623);
         var10 = var1.getlocal(0).__getattr__("_interpolate").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(8), var1.getlocal(9));
         var1.f_lasti = -1;
         return var10;
      }
   }

   public PyObject items$52(PyFrame var1, ThreadState var2) {
      var1.setline(636);
      PyString.fromInterned("Return a list of tuples with (name, value) for each option\n        in the section.\n\n        All % interpolations are expanded in the return values, based on the\n        defaults passed into the constructor, unless the optional argument\n        `raw' is true.  Additional substitutions may be provided using the\n        `vars' argument, which must be a dictionary whose contents overrides\n        any pre-existing defaults.\n\n        The section DEFAULT is special.\n        ");
      var1.setline(637);
      PyObject var3 = var1.getlocal(0).__getattr__("_defaults").__getattr__("copy").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;

      PyObject var4;
      PyObject var10000;
      try {
         var1.setline(639);
         var1.getlocal(4).__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("_sections").__getitem__(var1.getlocal(1)));
      } catch (Throwable var7) {
         PyException var8 = Py.setException(var7, var1);
         if (!var8.match(var1.getglobal("KeyError"))) {
            throw var8;
         }

         var1.setline(641);
         var4 = var1.getlocal(1);
         var10000 = var4._ne(var1.getglobal("DEFAULTSECT"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(642);
            throw Py.makeException(var1.getglobal("NoSectionError").__call__(var2, var1.getlocal(1)));
         }
      }

      var1.setline(644);
      PyObject var10;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(645);
         var3 = var1.getlocal(3).__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(645);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(646);
            var10 = var1.getlocal(6);
            var1.getlocal(4).__setitem__(var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(5)), var10);
            var5 = null;
         }
      }

      var1.setline(647);
      var3 = var1.getlocal(4).__getattr__("keys").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(648);
      PyString var9 = PyString.fromInterned("__name__");
      var10000 = var9._in(var1.getlocal(7));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(649);
         var1.getlocal(7).__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__name__"));
      }

      var1.setline(650);
      PyList var11;
      PyList var12;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(651);
         var12 = new PyList();
         var3 = var12.__getattr__("append");
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(652);
         var3 = var1.getlocal(7).__iter__();

         while(true) {
            var1.setline(652);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(652);
               var1.dellocal(8);
               var11 = var12;
               var1.f_lasti = -1;
               return var11;
            }

            var1.setlocal(9, var4);
            var1.setline(651);
            var1.getlocal(8).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(4).__getitem__(var1.getlocal(9))})));
         }
      } else {
         var1.setline(654);
         var12 = new PyList();
         var4 = var12.__getattr__("append");
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(655);
         var4 = var1.getlocal(7).__iter__();

         while(true) {
            var1.setline(655);
            var10 = var4.__iternext__();
            if (var10 == null) {
               var1.setline(655);
               var1.dellocal(10);
               var11 = var12;
               var1.f_lasti = -1;
               return var11;
            }

            var1.setlocal(9, var10);
            var1.setline(654);
            var1.getlocal(10).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(0).__getattr__("_interpolate").__call__(var2, var1.getlocal(1), var1.getlocal(9), var1.getlocal(4).__getitem__(var1.getlocal(9)), var1.getlocal(4))})));
         }
      }
   }

   public PyObject _interpolate$53(PyFrame var1, ThreadState var2) {
      var1.setline(659);
      PyObject var3 = var1.getlocal(3);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(660);
      var3 = var1.getglobal("MAX_INTERPOLATION_DEPTH");
      var1.setlocal(6, var3);
      var3 = null;

      PyObject var10000;
      PyString var6;
      while(true) {
         var1.setline(661);
         if (!var1.getlocal(6).__nonzero__()) {
            break;
         }

         var1.setline(662);
         var3 = var1.getlocal(6);
         var3 = var3._isub(Py.newInteger(1));
         var1.setlocal(6, var3);
         var1.setline(663);
         var10000 = var1.getlocal(5);
         if (var10000.__nonzero__()) {
            var6 = PyString.fromInterned("%(");
            var10000 = var6._in(var1.getlocal(5));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(664);
         var3 = var1.getlocal(0).__getattr__("_KEYCRE").__getattr__("sub").__call__(var2, var1.getlocal(0).__getattr__("_interpolation_replace"), var1.getlocal(5));
         var1.setlocal(5, var3);
         var3 = null;

         try {
            var1.setline(666);
            var3 = var1.getlocal(5)._mod(var1.getlocal(4));
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("KeyError"))) {
               PyObject var4 = var7.value;
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(668);
               throw Py.makeException(var1.getglobal("InterpolationMissingOptionError").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(3), var1.getlocal(7).__getattr__("args").__getitem__(Py.newInteger(0))));
            }

            throw var7;
         }
      }

      var1.setline(672);
      var10000 = var1.getlocal(5);
      if (var10000.__nonzero__()) {
         var6 = PyString.fromInterned("%(");
         var10000 = var6._in(var1.getlocal(5));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(673);
         throw Py.makeException(var1.getglobal("InterpolationDepthError").__call__(var2, var1.getlocal(2), var1.getlocal(1), var1.getlocal(3)));
      } else {
         var1.setline(674);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _interpolation_replace$54(PyFrame var1, ThreadState var2) {
      var1.setline(679);
      PyObject var3 = var1.getlocal(1).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(680);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(681);
         var3 = var1.getlocal(1).__getattr__("group").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(683);
         var3 = PyString.fromInterned("%%(%s)s")._mod(var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(2)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject SafeConfigParser$55(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(688);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _interpolate$56, (PyObject)null);
      var1.setlocal("_interpolate", var4);
      var3 = null;
      var1.setline(694);
      PyObject var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%\\(([^)]+)\\)s"));
      var1.setlocal("_interpvar_re", var5);
      var3 = null;
      var1.setline(696);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _interpolate_some$57, (PyObject)null);
      var1.setlocal("_interpolate_some", var4);
      var3 = null;
      var1.setline(734);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, set$58, PyString.fromInterned("Set an option.  Extend ConfigParser.set: check for string values."));
      var1.setlocal("set", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _interpolate$56(PyFrame var1, ThreadState var2) {
      var1.setline(690);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(691);
      PyObject var10000 = var1.getlocal(0).__getattr__("_interpolate_some");
      PyObject[] var4 = new PyObject[]{var1.getlocal(2), var1.getlocal(5), var1.getlocal(3), var1.getlocal(1), var1.getlocal(4), Py.newInteger(1)};
      var10000.__call__(var2, var4);
      var1.setline(692);
      PyObject var5 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(5));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _interpolate_some$57(PyFrame var1, ThreadState var2) {
      var1.setline(697);
      PyObject var3 = var1.getlocal(6);
      PyObject var10000 = var3._gt(var1.getglobal("MAX_INTERPOLATION_DEPTH"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(698);
         throw Py.makeException(var1.getglobal("InterpolationDepthError").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(3)));
      } else {
         while(true) {
            var1.setline(699);
            if (!var1.getlocal(3).__nonzero__()) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(700);
            var3 = var1.getlocal(3).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(701);
            var3 = var1.getlocal(7);
            var10000 = var3._lt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(702);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
               var1.setline(703);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(704);
            var3 = var1.getlocal(7);
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(705);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3).__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null));
               var1.setline(706);
               var3 = var1.getlocal(3).__getslice__(var1.getlocal(7), (PyObject)null, (PyObject)null);
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(708);
            var3 = var1.getlocal(3).__getslice__(Py.newInteger(1), Py.newInteger(2), (PyObject)null);
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(709);
            var3 = var1.getlocal(8);
            var10000 = var3._eq(PyString.fromInterned("%"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(710);
               var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
               var1.setline(711);
               var3 = var1.getlocal(3).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(712);
               var3 = var1.getlocal(8);
               var10000 = var3._eq(PyString.fromInterned("("));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(730);
                  throw Py.makeException(var1.getglobal("InterpolationSyntaxError").__call__(var2, var1.getlocal(1), var1.getlocal(4), PyString.fromInterned("'%%' must be followed by '%%' or '(', found: %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)}))));
               }

               var1.setline(713);
               var3 = var1.getlocal(0).__getattr__("_interpvar_re").__getattr__("match").__call__(var2, var1.getlocal(3));
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(714);
               var3 = var1.getlocal(9);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(715);
                  throw Py.makeException(var1.getglobal("InterpolationSyntaxError").__call__(var2, var1.getlocal(1), var1.getlocal(4), PyString.fromInterned("bad interpolation variable reference %r")._mod(var1.getlocal(3))));
               }

               var1.setline(717);
               var3 = var1.getlocal(0).__getattr__("optionxform").__call__(var2, var1.getlocal(9).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(718);
               var3 = var1.getlocal(3).__getslice__(var1.getlocal(9).__getattr__("end").__call__(var2), (PyObject)null, (PyObject)null);
               var1.setlocal(3, var3);
               var3 = null;

               try {
                  var1.setline(720);
                  var3 = var1.getlocal(5).__getitem__(var1.getlocal(10));
                  var1.setlocal(11, var3);
                  var3 = null;
               } catch (Throwable var4) {
                  PyException var5 = Py.setException(var4, var1);
                  if (var5.match(var1.getglobal("KeyError"))) {
                     var1.setline(722);
                     throw Py.makeException(var1.getglobal("InterpolationMissingOptionError").__call__(var2, var1.getlocal(1), var1.getlocal(4), var1.getlocal(3), var1.getlocal(10)));
                  }

                  throw var5;
               }

               var1.setline(724);
               PyString var6 = PyString.fromInterned("%");
               var10000 = var6._in(var1.getlocal(11));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(725);
                  var10000 = var1.getlocal(0).__getattr__("_interpolate_some");
                  PyObject[] var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(11), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)._add(Py.newInteger(1))};
                  var10000.__call__(var2, var7);
               } else {
                  var1.setline(728);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(11));
               }
            }
         }
      }
   }

   public PyObject set$58(PyFrame var1, ThreadState var2) {
      var1.setline(735);
      PyString.fromInterned("Set an option.  Extend ConfigParser.set: check for string values.");
      var1.setline(741);
      PyObject var3 = var1.getlocal(0).__getattr__("_optcre");
      PyObject var10000 = var3._is(var1.getlocal(0).__getattr__("OPTCRE"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(3);
      }

      if (var10000.__nonzero__()) {
         var1.setline(742);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__not__().__nonzero__()) {
            var1.setline(743);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("option values must be strings")));
         }
      }

      var1.setline(744);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(747);
         var3 = var1.getlocal(3).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%%"), (PyObject)PyString.fromInterned(""));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(748);
         var3 = var1.getlocal(0).__getattr__("_interpvar_re").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(4));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(750);
         PyString var4 = PyString.fromInterned("%");
         var10000 = var4._in(var1.getlocal(4));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(751);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("invalid interpolation syntax in %r at position %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"))}))));
         }
      }

      var1.setline(753);
      var1.getglobal("ConfigParser").__getattr__("set").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public ConfigParser$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Error$1 = Py.newCode(0, var2, var1, "Error", 112, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      _get_message$2 = Py.newCode(1, var2, var1, "_get_message", 115, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "value"};
      _set_message$3 = Py.newCode(2, var2, var1, "_set_message", 120, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      __init__$4 = Py.newCode(2, var2, var1, "__init__", 130, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$5 = Py.newCode(1, var2, var1, "__repr__", 134, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NoSectionError$6 = Py.newCode(0, var2, var1, "NoSectionError", 139, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "section"};
      __init__$7 = Py.newCode(2, var2, var1, "__init__", 142, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DuplicateSectionError$8 = Py.newCode(0, var2, var1, "DuplicateSectionError", 147, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "section"};
      __init__$9 = Py.newCode(2, var2, var1, "__init__", 150, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      NoOptionError$10 = Py.newCode(0, var2, var1, "NoOptionError", 155, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option", "section"};
      __init__$11 = Py.newCode(3, var2, var1, "__init__", 158, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InterpolationError$12 = Py.newCode(0, var2, var1, "InterpolationError", 165, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option", "section", "msg"};
      __init__$13 = Py.newCode(4, var2, var1, "__init__", 168, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InterpolationMissingOptionError$14 = Py.newCode(0, var2, var1, "InterpolationMissingOptionError", 174, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option", "section", "rawval", "reference", "msg"};
      __init__$15 = Py.newCode(5, var2, var1, "__init__", 177, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InterpolationSyntaxError$16 = Py.newCode(0, var2, var1, "InterpolationSyntaxError", 188, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InterpolationDepthError$17 = Py.newCode(0, var2, var1, "InterpolationDepthError", 192, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "option", "section", "rawval", "msg"};
      __init__$18 = Py.newCode(4, var2, var1, "__init__", 195, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ParsingError$19 = Py.newCode(0, var2, var1, "ParsingError", 204, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename"};
      __init__$20 = Py.newCode(2, var2, var1, "__init__", 207, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lineno", "line"};
      append$21 = Py.newCode(3, var2, var1, "append", 213, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MissingSectionHeaderError$22 = Py.newCode(0, var2, var1, "MissingSectionHeaderError", 217, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "lineno", "line"};
      __init__$23 = Py.newCode(4, var2, var1, "__init__", 220, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      RawConfigParser$24 = Py.newCode(0, var2, var1, "RawConfigParser", 231, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "defaults", "dict_type", "allow_no_value", "key", "value"};
      __init__$25 = Py.newCode(4, var2, var1, "__init__", 232, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      defaults$26 = Py.newCode(1, var2, var1, "defaults", 245, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      sections$27 = Py.newCode(1, var2, var1, "sections", 248, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section"};
      add_section$28 = Py.newCode(2, var2, var1, "add_section", 253, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section"};
      has_section$29 = Py.newCode(2, var2, var1, "has_section", 267, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "opts"};
      options$30 = Py.newCode(2, var2, var1, "options", 274, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filenames", "read_ok", "filename", "fp"};
      read$31 = Py.newCode(2, var2, var1, "read", 285, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "filename"};
      readfp$32 = Py.newCode(3, var2, var1, "readfp", 310, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "option", "opt"};
      get$33 = Py.newCode(3, var2, var1, "get", 326, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "d2", "d"};
      items$34 = Py.newCode(2, var2, var1, "items", 342, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "conv", "option"};
      _get$35 = Py.newCode(4, var2, var1, "_get", 355, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "option"};
      getint$36 = Py.newCode(3, var2, var1, "getint", 358, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "option"};
      getfloat$37 = Py.newCode(3, var2, var1, "getfloat", 361, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "option", "v"};
      getboolean$38 = Py.newCode(3, var2, var1, "getboolean", 367, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "optionstr"};
      optionxform$39 = Py.newCode(2, var2, var1, "optionxform", 373, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "option"};
      has_option$40 = Py.newCode(3, var2, var1, "has_option", 376, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "option", "value", "sectdict"};
      set$41 = Py.newCode(4, var2, var1, "set", 388, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "key", "value", "section"};
      write$42 = Py.newCode(2, var2, var1, "write", 399, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "option", "sectdict", "existed"};
      remove_option$43 = Py.newCode(3, var2, var1, "remove_option", 416, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "existed"};
      remove_section$44 = Py.newCode(2, var2, var1, "remove_section", 431, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "fpname", "cursect", "optname", "lineno", "e", "line", "value", "mo", "sectname", "vi", "optval", "pos", "all_sections", "options", "name", "val"};
      _read$45 = Py.newCode(3, var2, var1, "_read", 464, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Chainmap$46 = Py.newCode(0, var2, var1, "_Chainmap", 558, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "maps"};
      __init__$47 = Py.newCode(2, var2, var1, "__init__", 567, true, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "mapping"};
      __getitem__$48 = Py.newCode(2, var2, var1, "__getitem__", 570, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "seen", "mapping", "key"};
      keys$49 = Py.newCode(1, var2, var1, "keys", 578, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ConfigParser$50 = Py.newCode(0, var2, var1, "ConfigParser", 588, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "section", "option", "raw", "vars", "sectiondict", "vardict", "key", "value", "d"};
      get$51 = Py.newCode(5, var2, var1, "get", 590, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "raw", "vars", "d", "key", "value", "options", "_[651_20]", "option", "_[654_20]"};
      items$52 = Py.newCode(4, var2, var1, "items", 625, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "option", "rawval", "vars", "value", "depth", "e"};
      _interpolate$53 = Py.newCode(5, var2, var1, "_interpolate", 657, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "match", "s"};
      _interpolation_replace$54 = Py.newCode(2, var2, var1, "_interpolation_replace", 678, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      SafeConfigParser$55 = Py.newCode(0, var2, var1, "SafeConfigParser", 686, false, false, self, 55, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "section", "option", "rawval", "vars", "L"};
      _interpolate$56 = Py.newCode(5, var2, var1, "_interpolate", 688, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "option", "accum", "rest", "section", "map", "depth", "p", "c", "m", "var", "v"};
      _interpolate_some$57 = Py.newCode(7, var2, var1, "_interpolate_some", 696, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "section", "option", "value", "tmp_value"};
      set$58 = Py.newCode(4, var2, var1, "set", 734, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ConfigParser$py("ConfigParser$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ConfigParser$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Error$1(var2, var3);
         case 2:
            return this._get_message$2(var2, var3);
         case 3:
            return this._set_message$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.__repr__$5(var2, var3);
         case 6:
            return this.NoSectionError$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.DuplicateSectionError$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.NoOptionError$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.InterpolationError$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.InterpolationMissingOptionError$14(var2, var3);
         case 15:
            return this.__init__$15(var2, var3);
         case 16:
            return this.InterpolationSyntaxError$16(var2, var3);
         case 17:
            return this.InterpolationDepthError$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.ParsingError$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.append$21(var2, var3);
         case 22:
            return this.MissingSectionHeaderError$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.RawConfigParser$24(var2, var3);
         case 25:
            return this.__init__$25(var2, var3);
         case 26:
            return this.defaults$26(var2, var3);
         case 27:
            return this.sections$27(var2, var3);
         case 28:
            return this.add_section$28(var2, var3);
         case 29:
            return this.has_section$29(var2, var3);
         case 30:
            return this.options$30(var2, var3);
         case 31:
            return this.read$31(var2, var3);
         case 32:
            return this.readfp$32(var2, var3);
         case 33:
            return this.get$33(var2, var3);
         case 34:
            return this.items$34(var2, var3);
         case 35:
            return this._get$35(var2, var3);
         case 36:
            return this.getint$36(var2, var3);
         case 37:
            return this.getfloat$37(var2, var3);
         case 38:
            return this.getboolean$38(var2, var3);
         case 39:
            return this.optionxform$39(var2, var3);
         case 40:
            return this.has_option$40(var2, var3);
         case 41:
            return this.set$41(var2, var3);
         case 42:
            return this.write$42(var2, var3);
         case 43:
            return this.remove_option$43(var2, var3);
         case 44:
            return this.remove_section$44(var2, var3);
         case 45:
            return this._read$45(var2, var3);
         case 46:
            return this._Chainmap$46(var2, var3);
         case 47:
            return this.__init__$47(var2, var3);
         case 48:
            return this.__getitem__$48(var2, var3);
         case 49:
            return this.keys$49(var2, var3);
         case 50:
            return this.ConfigParser$50(var2, var3);
         case 51:
            return this.get$51(var2, var3);
         case 52:
            return this.items$52(var2, var3);
         case 53:
            return this._interpolate$53(var2, var3);
         case 54:
            return this._interpolation_replace$54(var2, var3);
         case 55:
            return this.SafeConfigParser$55(var2, var3);
         case 56:
            return this._interpolate$56(var2, var3);
         case 57:
            return this._interpolate_some$57(var2, var3);
         case 58:
            return this.set$58(var2, var3);
         default:
            return null;
      }
   }
}
