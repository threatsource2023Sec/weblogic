import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("htmllib.py")
public class htmllib$py extends PyFunctionTable implements PyRunnable {
   static htmllib$py self;
   static final PyCode f$0;
   static final PyCode HTMLParseError$1;
   static final PyCode HTMLParser$2;
   static final PyCode __init__$3;
   static final PyCode error$4;
   static final PyCode reset$5;
   static final PyCode handle_data$6;
   static final PyCode save_bgn$7;
   static final PyCode save_end$8;
   static final PyCode anchor_bgn$9;
   static final PyCode anchor_end$10;
   static final PyCode handle_image$11;
   static final PyCode start_html$12;
   static final PyCode end_html$13;
   static final PyCode start_head$14;
   static final PyCode end_head$15;
   static final PyCode start_body$16;
   static final PyCode end_body$17;
   static final PyCode start_title$18;
   static final PyCode end_title$19;
   static final PyCode do_base$20;
   static final PyCode do_isindex$21;
   static final PyCode do_link$22;
   static final PyCode do_meta$23;
   static final PyCode do_nextid$24;
   static final PyCode start_h1$25;
   static final PyCode end_h1$26;
   static final PyCode start_h2$27;
   static final PyCode end_h2$28;
   static final PyCode start_h3$29;
   static final PyCode end_h3$30;
   static final PyCode start_h4$31;
   static final PyCode end_h4$32;
   static final PyCode start_h5$33;
   static final PyCode end_h5$34;
   static final PyCode start_h6$35;
   static final PyCode end_h6$36;
   static final PyCode do_p$37;
   static final PyCode start_pre$38;
   static final PyCode end_pre$39;
   static final PyCode start_xmp$40;
   static final PyCode end_xmp$41;
   static final PyCode start_listing$42;
   static final PyCode end_listing$43;
   static final PyCode start_address$44;
   static final PyCode end_address$45;
   static final PyCode start_blockquote$46;
   static final PyCode end_blockquote$47;
   static final PyCode start_ul$48;
   static final PyCode end_ul$49;
   static final PyCode do_li$50;
   static final PyCode start_ol$51;
   static final PyCode end_ol$52;
   static final PyCode start_menu$53;
   static final PyCode end_menu$54;
   static final PyCode start_dir$55;
   static final PyCode end_dir$56;
   static final PyCode start_dl$57;
   static final PyCode end_dl$58;
   static final PyCode do_dt$59;
   static final PyCode do_dd$60;
   static final PyCode ddpop$61;
   static final PyCode start_cite$62;
   static final PyCode end_cite$63;
   static final PyCode start_code$64;
   static final PyCode end_code$65;
   static final PyCode start_em$66;
   static final PyCode end_em$67;
   static final PyCode start_kbd$68;
   static final PyCode end_kbd$69;
   static final PyCode start_samp$70;
   static final PyCode end_samp$71;
   static final PyCode start_strong$72;
   static final PyCode end_strong$73;
   static final PyCode start_var$74;
   static final PyCode end_var$75;
   static final PyCode start_i$76;
   static final PyCode end_i$77;
   static final PyCode start_b$78;
   static final PyCode end_b$79;
   static final PyCode start_tt$80;
   static final PyCode end_tt$81;
   static final PyCode start_a$82;
   static final PyCode end_a$83;
   static final PyCode do_br$84;
   static final PyCode do_hr$85;
   static final PyCode do_img$86;
   static final PyCode do_plaintext$87;
   static final PyCode unknown_starttag$88;
   static final PyCode unknown_endtag$89;
   static final PyCode test$90;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("HTML 2.0 parser.\n\nSee the HTML 2.0 specification:\nhttp://www.w3.org/hypertext/WWW/MarkUp/html-spec/html-spec_toc.html\n"));
      var1.setline(5);
      PyString.fromInterned("HTML 2.0 parser.\n\nSee the HTML 2.0 specification:\nhttp://www.w3.org/hypertext/WWW/MarkUp/html-spec/html-spec_toc.html\n");
      var1.setline(7);
      String[] var3 = new String[]{"warnpy3k"};
      PyObject[] var5 = imp.importFrom("warnings", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("warnpy3k", var4);
      var4 = null;
      var1.setline(8);
      PyObject var10000 = var1.getname("warnpy3k");
      var5 = new PyObject[]{PyString.fromInterned("the htmllib module has been removed in Python 3.0"), Py.newInteger(2)};
      String[] var7 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var7);
      var3 = null;
      var1.setline(10);
      var1.dellocal("warnpy3k");
      var1.setline(12);
      PyObject var6 = imp.importOne("sgmllib", var1, -1);
      var1.setlocal("sgmllib", var6);
      var3 = null;
      var1.setline(14);
      var3 = new String[]{"AS_IS"};
      var5 = imp.importFrom("formatter", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("AS_IS", var4);
      var4 = null;
      var1.setline(16);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("HTMLParser"), PyString.fromInterned("HTMLParseError")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(19);
      var5 = new PyObject[]{var1.getname("sgmllib").__getattr__("SGMLParseError")};
      var4 = Py.makeClass("HTMLParseError", var5, HTMLParseError$1);
      var1.setlocal("HTMLParseError", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(23);
      var5 = new PyObject[]{var1.getname("sgmllib").__getattr__("SGMLParser")};
      var4 = Py.makeClass("HTMLParser", var5, HTMLParser$2);
      var1.setlocal("HTMLParser", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(451);
      var5 = new PyObject[]{var1.getname("None")};
      PyFunction var9 = new PyFunction(var1.f_globals, var5, test$90, (PyObject)null);
      var1.setlocal("test", var9);
      var3 = null;
      var1.setline(490);
      var6 = var1.getname("__name__");
      var10000 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(491);
         var1.getname("test").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HTMLParseError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Error raised when an HTML document can't be parsed."));
      var1.setline(20);
      PyString.fromInterned("Error raised when an HTML document can't be parsed.");
      return var1.getf_locals();
   }

   public PyObject HTMLParser$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This is the basic HTML parser class.\n\n    It supports all entity names required by the XHTML 1.0 Recommendation.\n    It also defines handlers for all HTML 2.0 and many HTML 3.0 and 3.2\n    elements.\n\n    "));
      var1.setline(30);
      PyString.fromInterned("This is the basic HTML parser class.\n\n    It supports all entity names required by the XHTML 1.0 Recommendation.\n    It also defines handlers for all HTML 2.0 and many HTML 3.0 and 3.2\n    elements.\n\n    ");
      var1.setline(32);
      String[] var3 = new String[]{"entitydefs"};
      PyObject[] var5 = imp.importFrom("htmlentitydefs", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("entitydefs", var4);
      var4 = null;
      var1.setline(34);
      var5 = new PyObject[]{Py.newInteger(0)};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$3, PyString.fromInterned("Creates an instance of the HTMLParser class.\n\n        The formatter parameter is the formatter instance associated with\n        the parser.\n\n        "));
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(44);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, error$4, (PyObject)null);
      var1.setlocal("error", var6);
      var3 = null;
      var1.setline(47);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, reset$5, (PyObject)null);
      var1.setlocal("reset", var6);
      var3 = null;
      var1.setline(63);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_data$6, (PyObject)null);
      var1.setlocal("handle_data", var6);
      var3 = null;
      var1.setline(74);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, save_bgn$7, PyString.fromInterned("Begins saving character data in a buffer instead of sending it\n        to the formatter object.\n\n        Retrieve the stored data via the save_end() method.  Use of the\n        save_bgn() / save_end() pair may not be nested.\n\n        "));
      var1.setlocal("save_bgn", var6);
      var3 = null;
      var1.setline(84);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, save_end$8, PyString.fromInterned("Ends buffering character data and returns all data saved since\n        the preceding call to the save_bgn() method.\n\n        If the nofill flag is false, whitespace is collapsed to single\n        spaces.  A call to this method without a preceding call to the\n        save_bgn() method will raise a TypeError exception.\n\n        "));
      var1.setlocal("save_end", var6);
      var3 = null;
      var1.setline(101);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, anchor_bgn$9, PyString.fromInterned("This method is called at the start of an anchor region.\n\n        The arguments correspond to the attributes of the <A> tag with\n        the same names.  The default implementation maintains a list of\n        hyperlinks (defined by the HREF attribute for <A> tags) within\n        the document.  The list of hyperlinks is available as the data\n        attribute anchorlist.\n\n        "));
      var1.setlocal("anchor_bgn", var6);
      var3 = null;
      var1.setline(115);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, anchor_end$10, PyString.fromInterned("This method is called at the end of an anchor region.\n\n        The default implementation adds a textual footnote marker using an\n        index into the list of hyperlinks created by the anchor_bgn()method.\n\n        "));
      var1.setlocal("anchor_end", var6);
      var3 = null;
      var1.setline(128);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, handle_image$11, PyString.fromInterned("This method is called to handle images.\n\n        The default implementation simply passes the alt value to the\n        handle_data() method.\n\n        "));
      var1.setlocal("handle_image", var6);
      var3 = null;
      var1.setline(139);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_html$12, (PyObject)null);
      var1.setlocal("start_html", var6);
      var3 = null;
      var1.setline(140);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_html$13, (PyObject)null);
      var1.setlocal("end_html", var6);
      var3 = null;
      var1.setline(142);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_head$14, (PyObject)null);
      var1.setlocal("start_head", var6);
      var3 = null;
      var1.setline(143);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_head$15, (PyObject)null);
      var1.setlocal("end_head", var6);
      var3 = null;
      var1.setline(145);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_body$16, (PyObject)null);
      var1.setlocal("start_body", var6);
      var3 = null;
      var1.setline(146);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_body$17, (PyObject)null);
      var1.setlocal("end_body", var6);
      var3 = null;
      var1.setline(150);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_title$18, (PyObject)null);
      var1.setlocal("start_title", var6);
      var3 = null;
      var1.setline(153);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_title$19, (PyObject)null);
      var1.setlocal("end_title", var6);
      var3 = null;
      var1.setline(156);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_base$20, (PyObject)null);
      var1.setlocal("do_base", var6);
      var3 = null;
      var1.setline(161);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_isindex$21, (PyObject)null);
      var1.setlocal("do_isindex", var6);
      var3 = null;
      var1.setline(164);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_link$22, (PyObject)null);
      var1.setlocal("do_link", var6);
      var3 = null;
      var1.setline(167);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_meta$23, (PyObject)null);
      var1.setlocal("do_meta", var6);
      var3 = null;
      var1.setline(170);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_nextid$24, (PyObject)null);
      var1.setlocal("do_nextid", var6);
      var3 = null;
      var1.setline(177);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_h1$25, (PyObject)null);
      var1.setlocal("start_h1", var6);
      var3 = null;
      var1.setline(181);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_h1$26, (PyObject)null);
      var1.setlocal("end_h1", var6);
      var3 = null;
      var1.setline(185);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_h2$27, (PyObject)null);
      var1.setlocal("start_h2", var6);
      var3 = null;
      var1.setline(189);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_h2$28, (PyObject)null);
      var1.setlocal("end_h2", var6);
      var3 = null;
      var1.setline(193);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_h3$29, (PyObject)null);
      var1.setlocal("start_h3", var6);
      var3 = null;
      var1.setline(197);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_h3$30, (PyObject)null);
      var1.setlocal("end_h3", var6);
      var3 = null;
      var1.setline(201);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_h4$31, (PyObject)null);
      var1.setlocal("start_h4", var6);
      var3 = null;
      var1.setline(205);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_h4$32, (PyObject)null);
      var1.setlocal("end_h4", var6);
      var3 = null;
      var1.setline(209);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_h5$33, (PyObject)null);
      var1.setlocal("start_h5", var6);
      var3 = null;
      var1.setline(213);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_h5$34, (PyObject)null);
      var1.setlocal("end_h5", var6);
      var3 = null;
      var1.setline(217);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_h6$35, (PyObject)null);
      var1.setlocal("start_h6", var6);
      var3 = null;
      var1.setline(221);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_h6$36, (PyObject)null);
      var1.setlocal("end_h6", var6);
      var3 = null;
      var1.setline(227);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_p$37, (PyObject)null);
      var1.setlocal("do_p", var6);
      var3 = null;
      var1.setline(230);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_pre$38, (PyObject)null);
      var1.setlocal("start_pre", var6);
      var3 = null;
      var1.setline(235);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_pre$39, (PyObject)null);
      var1.setlocal("end_pre", var6);
      var3 = null;
      var1.setline(240);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_xmp$40, (PyObject)null);
      var1.setlocal("start_xmp", var6);
      var3 = null;
      var1.setline(244);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_xmp$41, (PyObject)null);
      var1.setlocal("end_xmp", var6);
      var3 = null;
      var1.setline(247);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_listing$42, (PyObject)null);
      var1.setlocal("start_listing", var6);
      var3 = null;
      var1.setline(251);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_listing$43, (PyObject)null);
      var1.setlocal("end_listing", var6);
      var3 = null;
      var1.setline(254);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_address$44, (PyObject)null);
      var1.setlocal("start_address", var6);
      var3 = null;
      var1.setline(258);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_address$45, (PyObject)null);
      var1.setlocal("end_address", var6);
      var3 = null;
      var1.setline(262);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_blockquote$46, (PyObject)null);
      var1.setlocal("start_blockquote", var6);
      var3 = null;
      var1.setline(266);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_blockquote$47, (PyObject)null);
      var1.setlocal("end_blockquote", var6);
      var3 = null;
      var1.setline(272);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_ul$48, (PyObject)null);
      var1.setlocal("start_ul", var6);
      var3 = null;
      var1.setline(277);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_ul$49, (PyObject)null);
      var1.setlocal("end_ul", var6);
      var3 = null;
      var1.setline(282);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_li$50, (PyObject)null);
      var1.setlocal("do_li", var6);
      var3 = null;
      var1.setline(291);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_ol$51, (PyObject)null);
      var1.setlocal("start_ol", var6);
      var3 = null;
      var1.setline(301);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_ol$52, (PyObject)null);
      var1.setlocal("end_ol", var6);
      var3 = null;
      var1.setline(306);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_menu$53, (PyObject)null);
      var1.setlocal("start_menu", var6);
      var3 = null;
      var1.setline(309);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_menu$54, (PyObject)null);
      var1.setlocal("end_menu", var6);
      var3 = null;
      var1.setline(312);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_dir$55, (PyObject)null);
      var1.setlocal("start_dir", var6);
      var3 = null;
      var1.setline(315);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_dir$56, (PyObject)null);
      var1.setlocal("end_dir", var6);
      var3 = null;
      var1.setline(318);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_dl$57, (PyObject)null);
      var1.setlocal("start_dl", var6);
      var3 = null;
      var1.setline(322);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_dl$58, (PyObject)null);
      var1.setlocal("end_dl", var6);
      var3 = null;
      var1.setline(326);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_dt$59, (PyObject)null);
      var1.setlocal("do_dt", var6);
      var3 = null;
      var1.setline(329);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_dd$60, (PyObject)null);
      var1.setlocal("do_dd", var6);
      var3 = null;
      var1.setline(334);
      var5 = new PyObject[]{Py.newInteger(0)};
      var6 = new PyFunction(var1.f_globals, var5, ddpop$61, (PyObject)null);
      var1.setlocal("ddpop", var6);
      var3 = null;
      var1.setline(345);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_cite$62, (PyObject)null);
      var1.setlocal("start_cite", var6);
      var3 = null;
      var1.setline(346);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_cite$63, (PyObject)null);
      var1.setlocal("end_cite", var6);
      var3 = null;
      var1.setline(348);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_code$64, (PyObject)null);
      var1.setlocal("start_code", var6);
      var3 = null;
      var1.setline(349);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_code$65, (PyObject)null);
      var1.setlocal("end_code", var6);
      var3 = null;
      var1.setline(351);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_em$66, (PyObject)null);
      var1.setlocal("start_em", var6);
      var3 = null;
      var1.setline(352);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_em$67, (PyObject)null);
      var1.setlocal("end_em", var6);
      var3 = null;
      var1.setline(354);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_kbd$68, (PyObject)null);
      var1.setlocal("start_kbd", var6);
      var3 = null;
      var1.setline(355);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_kbd$69, (PyObject)null);
      var1.setlocal("end_kbd", var6);
      var3 = null;
      var1.setline(357);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_samp$70, (PyObject)null);
      var1.setlocal("start_samp", var6);
      var3 = null;
      var1.setline(358);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_samp$71, (PyObject)null);
      var1.setlocal("end_samp", var6);
      var3 = null;
      var1.setline(360);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_strong$72, (PyObject)null);
      var1.setlocal("start_strong", var6);
      var3 = null;
      var1.setline(361);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_strong$73, (PyObject)null);
      var1.setlocal("end_strong", var6);
      var3 = null;
      var1.setline(363);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_var$74, (PyObject)null);
      var1.setlocal("start_var", var6);
      var3 = null;
      var1.setline(364);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_var$75, (PyObject)null);
      var1.setlocal("end_var", var6);
      var3 = null;
      var1.setline(368);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_i$76, (PyObject)null);
      var1.setlocal("start_i", var6);
      var3 = null;
      var1.setline(370);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_i$77, (PyObject)null);
      var1.setlocal("end_i", var6);
      var3 = null;
      var1.setline(373);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_b$78, (PyObject)null);
      var1.setlocal("start_b", var6);
      var3 = null;
      var1.setline(375);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_b$79, (PyObject)null);
      var1.setlocal("end_b", var6);
      var3 = null;
      var1.setline(378);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_tt$80, (PyObject)null);
      var1.setlocal("start_tt", var6);
      var3 = null;
      var1.setline(380);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_tt$81, (PyObject)null);
      var1.setlocal("end_tt", var6);
      var3 = null;
      var1.setline(383);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, start_a$82, (PyObject)null);
      var1.setlocal("start_a", var6);
      var3 = null;
      var1.setline(397);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, end_a$83, (PyObject)null);
      var1.setlocal("end_a", var6);
      var3 = null;
      var1.setline(402);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_br$84, (PyObject)null);
      var1.setlocal("do_br", var6);
      var3 = null;
      var1.setline(407);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_hr$85, (PyObject)null);
      var1.setlocal("do_hr", var6);
      var3 = null;
      var1.setline(412);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_img$86, (PyObject)null);
      var1.setlocal("do_img", var6);
      var3 = null;
      var1.setline(438);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, do_plaintext$87, (PyObject)null);
      var1.setlocal("do_plaintext", var6);
      var3 = null;
      var1.setline(444);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, unknown_starttag$88, (PyObject)null);
      var1.setlocal("unknown_starttag", var6);
      var3 = null;
      var1.setline(447);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, unknown_endtag$89, (PyObject)null);
      var1.setlocal("unknown_endtag", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyString.fromInterned("Creates an instance of the HTMLParser class.\n\n        The formatter parameter is the formatter instance associated with\n        the parser.\n\n        ");
      var1.setline(41);
      var1.getglobal("sgmllib").__getattr__("SGMLParser").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(2));
      var1.setline(42);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("formatter", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject error$4(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      throw Py.makeException(var1.getglobal("HTMLParseError").__call__(var2, var1.getlocal(1)));
   }

   public PyObject reset$5(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      var1.getglobal("sgmllib").__getattr__("SGMLParser").__getattr__("reset").__call__(var2, var1.getlocal(0));
      var1.setline(49);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("savedata", var3);
      var3 = null;
      var1.setline(50);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"isindex", var4);
      var3 = null;
      var1.setline(51);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("title", var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("base", var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("anchor", var3);
      var3 = null;
      var1.setline(54);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"anchorlist", var5);
      var3 = null;
      var1.setline(55);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"nofill", var4);
      var3 = null;
      var1.setline(56);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"list_stack", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_data$6(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getlocal(0).__getattr__("savedata");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(65);
         var3 = var1.getlocal(0).__getattr__("savedata")._add(var1.getlocal(1));
         var1.getlocal(0).__setattr__("savedata", var3);
         var3 = null;
      } else {
         var1.setline(67);
         if (var1.getlocal(0).__getattr__("nofill").__nonzero__()) {
            var1.setline(68);
            var1.getlocal(0).__getattr__("formatter").__getattr__("add_literal_data").__call__(var2, var1.getlocal(1));
         } else {
            var1.setline(70);
            var1.getlocal(0).__getattr__("formatter").__getattr__("add_flowing_data").__call__(var2, var1.getlocal(1));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_bgn$7(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("Begins saving character data in a buffer instead of sending it\n        to the formatter object.\n\n        Retrieve the stored data via the save_end() method.  Use of the\n        save_bgn() / save_end() pair may not be nested.\n\n        ");
      var1.setline(82);
      PyString var3 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"savedata", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save_end$8(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyString.fromInterned("Ends buffering character data and returns all data saved since\n        the preceding call to the save_bgn() method.\n\n        If the nofill flag is false, whitespace is collapsed to single\n        spaces.  A call to this method without a preceding call to the\n        save_bgn() method will raise a TypeError exception.\n\n        ");
      var1.setline(93);
      PyObject var3 = var1.getlocal(0).__getattr__("savedata");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("savedata", var3);
      var3 = null;
      var1.setline(95);
      if (var1.getlocal(0).__getattr__("nofill").__not__().__nonzero__()) {
         var1.setline(96);
         var3 = PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(1).__getattr__("split").__call__(var2));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(97);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject anchor_bgn$9(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("This method is called at the start of an anchor region.\n\n        The arguments correspond to the attributes of the <A> tag with\n        the same names.  The default implementation maintains a list of\n        hyperlinks (defined by the HREF attribute for <A> tags) within\n        the document.  The list of hyperlinks is available as the data\n        attribute anchorlist.\n\n        ");
      var1.setline(111);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("anchor", var3);
      var3 = null;
      var1.setline(112);
      if (var1.getlocal(0).__getattr__("anchor").__nonzero__()) {
         var1.setline(113);
         var1.getlocal(0).__getattr__("anchorlist").__getattr__("append").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject anchor_end$10(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyString.fromInterned("This method is called at the end of an anchor region.\n\n        The default implementation adds a textual footnote marker using an\n        index into the list of hyperlinks created by the anchor_bgn()method.\n\n        ");
      var1.setline(122);
      if (var1.getlocal(0).__getattr__("anchor").__nonzero__()) {
         var1.setline(123);
         var1.getlocal(0).__getattr__("handle_data").__call__(var2, PyString.fromInterned("[%d]")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("anchorlist"))));
         var1.setline(124);
         PyObject var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("anchor", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject handle_image$11(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyString.fromInterned("This method is called to handle images.\n\n        The default implementation simply passes the alt value to the\n        handle_data() method.\n\n        ");
      var1.setline(135);
      var1.getlocal(0).__getattr__("handle_data").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_html$12(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_html$13(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_head$14(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_head$15(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_body$16(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_body$17(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_title$18(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      var1.getlocal(0).__getattr__("save_bgn").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_title$19(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyObject var3 = var1.getlocal(0).__getattr__("save_end").__call__(var2);
      var1.getlocal(0).__setattr__("title", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_base$20(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(157);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(158);
         PyObject var7 = var1.getlocal(2);
         PyObject var10000 = var7._eq(PyString.fromInterned("href"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(159);
            var7 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("base", var7);
            var5 = null;
         }
      }
   }

   public PyObject do_isindex$21(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"isindex", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_link$22(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_meta$23(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_nextid$24(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_h1$25(PyFrame var1, ThreadState var2) {
      var1.setline(178);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(179);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("h1"), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_h1$26(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(183);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_h2$27(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(187);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("h2"), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_h2$28(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(191);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_h3$29(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(195);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("h3"), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_h3$30(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(199);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_h4$31(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(203);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("h4"), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_h4$32(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(207);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_h5$33(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(211);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("h5"), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_h5$34(PyFrame var1, ThreadState var2) {
      var1.setline(214);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(215);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_h6$35(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(219);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("h6"), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_h6$36(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(223);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_p$37(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_pre$38(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(232);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("AS_IS"), var1.getglobal("AS_IS"), var1.getglobal("AS_IS"), Py.newInteger(1)})));
      var1.setline(233);
      PyObject var3 = var1.getlocal(0).__getattr__("nofill")._add(Py.newInteger(1));
      var1.getlocal(0).__setattr__("nofill", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_pre$39(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(237);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.setline(238);
      PyObject var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(0).__getattr__("nofill")._sub(Py.newInteger(1)));
      var1.getlocal(0).__setattr__("nofill", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_xmp$40(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      var1.getlocal(0).__getattr__("start_pre").__call__(var2, var1.getlocal(1));
      var1.setline(242);
      var1.getlocal(0).__getattr__("setliteral").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xmp"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_xmp$41(PyFrame var1, ThreadState var2) {
      var1.setline(245);
      var1.getlocal(0).__getattr__("end_pre").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_listing$42(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      var1.getlocal(0).__getattr__("start_pre").__call__(var2, var1.getlocal(1));
      var1.setline(249);
      var1.getlocal(0).__getattr__("setliteral").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("listing"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_listing$43(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      var1.getlocal(0).__getattr__("end_pre").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_address$44(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(256);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("AS_IS"), Py.newInteger(1), var1.getglobal("AS_IS"), var1.getglobal("AS_IS")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_address$45(PyFrame var1, ThreadState var2) {
      var1.setline(259);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(260);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_blockquote$46(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(264);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_margin").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("blockquote"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_blockquote$47(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(268);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_margin").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_ul$48(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__(var2, var1.getlocal(0).__getattr__("list_stack").__not__());
      var1.setline(274);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_margin").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ul"));
      var1.setline(275);
      var1.getlocal(0).__getattr__("list_stack").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("ul"), PyString.fromInterned("*"), Py.newInteger(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_ul$49(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      if (var1.getlocal(0).__getattr__("list_stack").__nonzero__()) {
         var1.setline(278);
         var1.getlocal(0).__getattr__("list_stack").__delitem__((PyObject)Py.newInteger(-1));
      }

      var1.setline(279);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__(var2, var1.getlocal(0).__getattr__("list_stack").__not__());
      var1.setline(280);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_margin").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_li$50(PyFrame var1, ThreadState var2) {
      var1.setline(283);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(284);
      PyObject var3;
      PyObject[] var4;
      PyObject var5;
      if (var1.getlocal(0).__getattr__("list_stack").__nonzero__()) {
         var1.setline(285);
         var3 = var1.getlocal(0).__getattr__("list_stack").__getitem__(Py.newInteger(-1));
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(4, var5);
         var5 = null;
         var1.setlocal(5, var3);
         var1.setline(286);
         var3 = var1.getlocal(4)._add(Py.newInteger(1));
         var1.getlocal(5).__setitem__((PyObject)Py.newInteger(2), var3);
         var1.setlocal(4, var3);
      } else {
         var1.setline(288);
         PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("*"), Py.newInteger(0)});
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(289);
      var1.getlocal(0).__getattr__("formatter").__getattr__("add_label_data").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_ol$51(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__(var2, var1.getlocal(0).__getattr__("list_stack").__not__());
      var1.setline(293);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_margin").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ol"));
      var1.setline(294);
      PyString var3 = PyString.fromInterned("1.");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(295);
      PyObject var7 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(295);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(299);
            var1.getlocal(0).__getattr__("list_stack").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("ol"), var1.getlocal(2), Py.newInteger(0)})));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(296);
         PyObject var8 = var1.getlocal(3);
         PyObject var10000 = var8._eq(PyString.fromInterned("type"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(297);
            var8 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
            var10000 = var8._eq(Py.newInteger(1));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(297);
               var8 = var1.getlocal(4)._add(PyString.fromInterned("."));
               var1.setlocal(4, var8);
               var5 = null;
            }

            var1.setline(298);
            var8 = var1.getlocal(4);
            var1.setlocal(2, var8);
            var5 = null;
         }
      }
   }

   public PyObject end_ol$52(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      if (var1.getlocal(0).__getattr__("list_stack").__nonzero__()) {
         var1.setline(302);
         var1.getlocal(0).__getattr__("list_stack").__delitem__((PyObject)Py.newInteger(-1));
      }

      var1.setline(303);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__(var2, var1.getlocal(0).__getattr__("list_stack").__not__());
      var1.setline(304);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_margin").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_menu$53(PyFrame var1, ThreadState var2) {
      var1.setline(307);
      var1.getlocal(0).__getattr__("start_ul").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_menu$54(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      var1.getlocal(0).__getattr__("end_ul").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_dir$55(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      var1.getlocal(0).__getattr__("start_ul").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_dir$56(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      var1.getlocal(0).__getattr__("end_ul").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_dl$57(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(320);
      var1.getlocal(0).__getattr__("list_stack").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("dl"), PyString.fromInterned(""), Py.newInteger(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_dl$58(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      var1.getlocal(0).__getattr__("ddpop").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(324);
      if (var1.getlocal(0).__getattr__("list_stack").__nonzero__()) {
         var1.setline(324);
         var1.getlocal(0).__getattr__("list_stack").__delitem__((PyObject)Py.newInteger(-1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_dt$59(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      var1.getlocal(0).__getattr__("ddpop").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_dd$60(PyFrame var1, ThreadState var2) {
      var1.setline(330);
      var1.getlocal(0).__getattr__("ddpop").__call__(var2);
      var1.setline(331);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_margin").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dd"));
      var1.setline(332);
      var1.getlocal(0).__getattr__("list_stack").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("dd"), PyString.fromInterned(""), Py.newInteger(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ddpop$61(PyFrame var1, ThreadState var2) {
      var1.setline(335);
      var1.getlocal(0).__getattr__("formatter").__getattr__("end_paragraph").__call__(var2, var1.getlocal(1));
      var1.setline(336);
      if (var1.getlocal(0).__getattr__("list_stack").__nonzero__()) {
         var1.setline(337);
         PyObject var3 = var1.getlocal(0).__getattr__("list_stack").__getitem__(Py.newInteger(-1)).__getitem__(Py.newInteger(0));
         PyObject var10000 = var3._eq(PyString.fromInterned("dd"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(338);
            var1.getlocal(0).__getattr__("list_stack").__delitem__((PyObject)Py.newInteger(-1));
            var1.setline(339);
            var1.getlocal(0).__getattr__("formatter").__getattr__("pop_margin").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_cite$62(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      var1.getlocal(0).__getattr__("start_i").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_cite$63(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      var1.getlocal(0).__getattr__("end_i").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_code$64(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      var1.getlocal(0).__getattr__("start_tt").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_code$65(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      var1.getlocal(0).__getattr__("end_tt").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_em$66(PyFrame var1, ThreadState var2) {
      var1.setline(351);
      var1.getlocal(0).__getattr__("start_i").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_em$67(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      var1.getlocal(0).__getattr__("end_i").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_kbd$68(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      var1.getlocal(0).__getattr__("start_tt").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_kbd$69(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      var1.getlocal(0).__getattr__("end_tt").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_samp$70(PyFrame var1, ThreadState var2) {
      var1.setline(357);
      var1.getlocal(0).__getattr__("start_tt").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_samp$71(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      var1.getlocal(0).__getattr__("end_tt").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_strong$72(PyFrame var1, ThreadState var2) {
      var1.setline(360);
      var1.getlocal(0).__getattr__("start_b").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_strong$73(PyFrame var1, ThreadState var2) {
      var1.setline(361);
      var1.getlocal(0).__getattr__("end_b").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_var$74(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      var1.getlocal(0).__getattr__("start_i").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_var$75(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      var1.getlocal(0).__getattr__("end_i").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_i$76(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("AS_IS"), Py.newInteger(1), var1.getglobal("AS_IS"), var1.getglobal("AS_IS")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_i$77(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_b$78(PyFrame var1, ThreadState var2) {
      var1.setline(374);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("AS_IS"), var1.getglobal("AS_IS"), Py.newInteger(1), var1.getglobal("AS_IS")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_b$79(PyFrame var1, ThreadState var2) {
      var1.setline(376);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_tt$80(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      var1.getlocal(0).__getattr__("formatter").__getattr__("push_font").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("AS_IS"), var1.getglobal("AS_IS"), var1.getglobal("AS_IS"), Py.newInteger(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject end_tt$81(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      var1.getlocal(0).__getattr__("formatter").__getattr__("pop_font").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject start_a$82(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(385);
      var3 = PyString.fromInterned("");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(386);
      var3 = PyString.fromInterned("");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(387);
      PyObject var7 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(387);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(395);
            var1.getlocal(0).__getattr__("anchor_bgn").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(388);
         PyObject var8 = var1.getlocal(6).__getattr__("strip").__call__(var2);
         var1.setlocal(6, var8);
         var5 = null;
         var1.setline(389);
         var8 = var1.getlocal(5);
         PyObject var10000 = var8._eq(PyString.fromInterned("href"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(390);
            var8 = var1.getlocal(6);
            var1.setlocal(2, var8);
            var5 = null;
         }

         var1.setline(391);
         var8 = var1.getlocal(5);
         var10000 = var8._eq(PyString.fromInterned("name"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(392);
            var8 = var1.getlocal(6);
            var1.setlocal(3, var8);
            var5 = null;
         }

         var1.setline(393);
         var8 = var1.getlocal(5);
         var10000 = var8._eq(PyString.fromInterned("type"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(394);
            var8 = var1.getlocal(6).__getattr__("lower").__call__(var2);
            var1.setlocal(4, var8);
            var5 = null;
         }
      }
   }

   public PyObject end_a$83(PyFrame var1, ThreadState var2) {
      var1.setline(398);
      var1.getlocal(0).__getattr__("anchor_end").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_br$84(PyFrame var1, ThreadState var2) {
      var1.setline(403);
      var1.getlocal(0).__getattr__("formatter").__getattr__("add_line_break").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_hr$85(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      var1.getlocal(0).__getattr__("formatter").__getattr__("add_hor_rule").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_img$86(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(414);
      var3 = PyString.fromInterned("(image)");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(415);
      var3 = PyString.fromInterned("");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(416);
      var3 = PyString.fromInterned("");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(417);
      PyInteger var10 = Py.newInteger(0);
      var1.setlocal(6, var10);
      var3 = null;
      var1.setline(418);
      var10 = Py.newInteger(0);
      var1.setlocal(7, var10);
      var3 = null;
      var1.setline(419);
      PyObject var11 = var1.getlocal(1).__iter__();

      while(true) {
         PyObject[] var5;
         PyObject var9;
         PyException var13;
         PyObject var10000;
         do {
            var1.setline(419);
            PyObject var4 = var11.__iternext__();
            if (var4 == null) {
               var1.setline(434);
               var10000 = var1.getlocal(0).__getattr__("handle_image");
               PyObject[] var12 = new PyObject[]{var1.getlocal(5), var1.getlocal(3), var1.getlocal(4), var1.getlocal(2), var1.getlocal(6), var1.getlocal(7)};
               var10000.__call__(var2, var12);
               var1.f_lasti = -1;
               return Py.None;
            }

            var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(9, var6);
            var6 = null;
            var1.setline(420);
            var9 = var1.getlocal(8);
            var10000 = var9._eq(PyString.fromInterned("align"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(421);
               var9 = var1.getlocal(9);
               var1.setlocal(2, var9);
               var5 = null;
            }

            var1.setline(422);
            var9 = var1.getlocal(8);
            var10000 = var9._eq(PyString.fromInterned("alt"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(423);
               var9 = var1.getlocal(9);
               var1.setlocal(3, var9);
               var5 = null;
            }

            var1.setline(424);
            var9 = var1.getlocal(8);
            var10000 = var9._eq(PyString.fromInterned("ismap"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(425);
               var9 = var1.getlocal(9);
               var1.setlocal(4, var9);
               var5 = null;
            }

            var1.setline(426);
            var9 = var1.getlocal(8);
            var10000 = var9._eq(PyString.fromInterned("src"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(427);
               var9 = var1.getlocal(9);
               var1.setlocal(5, var9);
               var5 = null;
            }

            var1.setline(428);
            var9 = var1.getlocal(8);
            var10000 = var9._eq(PyString.fromInterned("width"));
            var5 = null;
            if (var10000.__nonzero__()) {
               try {
                  var1.setline(429);
                  var9 = var1.getglobal("int").__call__(var2, var1.getlocal(9));
                  var1.setlocal(6, var9);
                  var5 = null;
               } catch (Throwable var7) {
                  var13 = Py.setException(var7, var1);
                  if (!var13.match(var1.getglobal("ValueError"))) {
                     throw var13;
                  }

                  var1.setline(430);
               }
            }

            var1.setline(431);
            var9 = var1.getlocal(8);
            var10000 = var9._eq(PyString.fromInterned("height"));
            var5 = null;
         } while(!var10000.__nonzero__());

         try {
            var1.setline(432);
            var9 = var1.getglobal("int").__call__(var2, var1.getlocal(9));
            var1.setlocal(7, var9);
            var5 = null;
         } catch (Throwable var8) {
            var13 = Py.setException(var8, var1);
            if (!var13.match(var1.getglobal("ValueError"))) {
               throw var13;
            }

            var1.setline(433);
         }
      }
   }

   public PyObject do_plaintext$87(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      var1.getlocal(0).__getattr__("start_pre").__call__(var2, var1.getlocal(1));
      var1.setline(440);
      var1.getlocal(0).__getattr__("setnomoretags").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_starttag$88(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject unknown_endtag$89(PyFrame var1, ThreadState var2) {
      var1.setline(448);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$90(PyFrame var1, ThreadState var2) {
      var1.setline(452);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var3 = imp.importOne("formatter", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(454);
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(455);
         var3 = var1.getlocal(1).__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(457);
      PyObject var10000 = var1.getlocal(0);
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._eq(PyString.fromInterned("-s"));
         var3 = null;
      }

      var3 = var10000;
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(458);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(459);
         var1.getlocal(0).__delitem__((PyObject)Py.newInteger(0));
      }

      var1.setline(461);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(462);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(464);
         PyString var6 = PyString.fromInterned("test.html");
         var1.setlocal(4, var6);
         var3 = null;
      }

      var1.setline(466);
      var3 = var1.getlocal(4);
      var10000 = var3._eq(PyString.fromInterned("-"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(467);
         var3 = var1.getlocal(1).__getattr__("stdin");
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         try {
            var1.setline(470);
            var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("r"));
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (!var7.match(var1.getglobal("IOError"))) {
               throw var7;
            }

            PyObject var4 = var7.value;
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(472);
            Py.printComma(var1.getlocal(4));
            Py.printComma(PyString.fromInterned(":"));
            Py.println(var1.getlocal(6));
            var1.setline(473);
            var1.getlocal(1).__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
         }
      }

      var1.setline(475);
      var3 = var1.getlocal(5).__getattr__("read").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(477);
      var3 = var1.getlocal(5);
      var10000 = var3._isnot(var1.getlocal(1).__getattr__("stdin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(478);
         var1.getlocal(5).__getattr__("close").__call__(var2);
      }

      var1.setline(480);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(481);
         var3 = var1.getlocal(2).__getattr__("NullFormatter").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(483);
         var3 = var1.getlocal(2).__getattr__("AbstractFormatter").__call__(var2, var1.getlocal(2).__getattr__("DumbWriter").__call__(var2));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(485);
      var3 = var1.getglobal("HTMLParser").__call__(var2, var1.getlocal(5));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(486);
      var1.getlocal(8).__getattr__("feed").__call__(var2, var1.getlocal(7));
      var1.setline(487);
      var1.getlocal(8).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public htmllib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      HTMLParseError$1 = Py.newCode(0, var2, var1, "HTMLParseError", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      HTMLParser$2 = Py.newCode(0, var2, var1, "HTMLParser", 23, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "formatter", "verbose"};
      __init__$3 = Py.newCode(3, var2, var1, "__init__", 34, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message"};
      error$4 = Py.newCode(2, var2, var1, "error", 44, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reset$5 = Py.newCode(1, var2, var1, "reset", 47, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      handle_data$6 = Py.newCode(2, var2, var1, "handle_data", 63, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      save_bgn$7 = Py.newCode(1, var2, var1, "save_bgn", 74, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      save_end$8 = Py.newCode(1, var2, var1, "save_end", 84, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "href", "name", "type"};
      anchor_bgn$9 = Py.newCode(4, var2, var1, "anchor_bgn", 101, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      anchor_end$10 = Py.newCode(1, var2, var1, "anchor_end", 115, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "src", "alt", "args"};
      handle_image$11 = Py.newCode(4, var2, var1, "handle_image", 128, true, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_html$12 = Py.newCode(2, var2, var1, "start_html", 139, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_html$13 = Py.newCode(1, var2, var1, "end_html", 140, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_head$14 = Py.newCode(2, var2, var1, "start_head", 142, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_head$15 = Py.newCode(1, var2, var1, "end_head", 143, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_body$16 = Py.newCode(2, var2, var1, "start_body", 145, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_body$17 = Py.newCode(1, var2, var1, "end_body", 146, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_title$18 = Py.newCode(2, var2, var1, "start_title", 150, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_title$19 = Py.newCode(1, var2, var1, "end_title", 153, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "a", "v"};
      do_base$20 = Py.newCode(2, var2, var1, "do_base", 156, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_isindex$21 = Py.newCode(2, var2, var1, "do_isindex", 161, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_link$22 = Py.newCode(2, var2, var1, "do_link", 164, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_meta$23 = Py.newCode(2, var2, var1, "do_meta", 167, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_nextid$24 = Py.newCode(2, var2, var1, "do_nextid", 170, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_h1$25 = Py.newCode(2, var2, var1, "start_h1", 177, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_h1$26 = Py.newCode(1, var2, var1, "end_h1", 181, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_h2$27 = Py.newCode(2, var2, var1, "start_h2", 185, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_h2$28 = Py.newCode(1, var2, var1, "end_h2", 189, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_h3$29 = Py.newCode(2, var2, var1, "start_h3", 193, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_h3$30 = Py.newCode(1, var2, var1, "end_h3", 197, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_h4$31 = Py.newCode(2, var2, var1, "start_h4", 201, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_h4$32 = Py.newCode(1, var2, var1, "end_h4", 205, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_h5$33 = Py.newCode(2, var2, var1, "start_h5", 209, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_h5$34 = Py.newCode(1, var2, var1, "end_h5", 213, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_h6$35 = Py.newCode(2, var2, var1, "start_h6", 217, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_h6$36 = Py.newCode(1, var2, var1, "end_h6", 221, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_p$37 = Py.newCode(2, var2, var1, "do_p", 227, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_pre$38 = Py.newCode(2, var2, var1, "start_pre", 230, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_pre$39 = Py.newCode(1, var2, var1, "end_pre", 235, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_xmp$40 = Py.newCode(2, var2, var1, "start_xmp", 240, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_xmp$41 = Py.newCode(1, var2, var1, "end_xmp", 244, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_listing$42 = Py.newCode(2, var2, var1, "start_listing", 247, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_listing$43 = Py.newCode(1, var2, var1, "end_listing", 251, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_address$44 = Py.newCode(2, var2, var1, "start_address", 254, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_address$45 = Py.newCode(1, var2, var1, "end_address", 258, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_blockquote$46 = Py.newCode(2, var2, var1, "start_blockquote", 262, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_blockquote$47 = Py.newCode(1, var2, var1, "end_blockquote", 266, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_ul$48 = Py.newCode(2, var2, var1, "start_ul", 272, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_ul$49 = Py.newCode(1, var2, var1, "end_ul", 277, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "dummy", "label", "counter", "top"};
      do_li$50 = Py.newCode(2, var2, var1, "do_li", 282, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "label", "a", "v"};
      start_ol$51 = Py.newCode(2, var2, var1, "start_ol", 291, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_ol$52 = Py.newCode(1, var2, var1, "end_ol", 301, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_menu$53 = Py.newCode(2, var2, var1, "start_menu", 306, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_menu$54 = Py.newCode(1, var2, var1, "end_menu", 309, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_dir$55 = Py.newCode(2, var2, var1, "start_dir", 312, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_dir$56 = Py.newCode(1, var2, var1, "end_dir", 315, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_dl$57 = Py.newCode(2, var2, var1, "start_dl", 318, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_dl$58 = Py.newCode(1, var2, var1, "end_dl", 322, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_dt$59 = Py.newCode(2, var2, var1, "do_dt", 326, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_dd$60 = Py.newCode(2, var2, var1, "do_dd", 329, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bl"};
      ddpop$61 = Py.newCode(2, var2, var1, "ddpop", 334, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_cite$62 = Py.newCode(2, var2, var1, "start_cite", 345, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_cite$63 = Py.newCode(1, var2, var1, "end_cite", 346, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_code$64 = Py.newCode(2, var2, var1, "start_code", 348, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_code$65 = Py.newCode(1, var2, var1, "end_code", 349, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_em$66 = Py.newCode(2, var2, var1, "start_em", 351, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_em$67 = Py.newCode(1, var2, var1, "end_em", 352, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_kbd$68 = Py.newCode(2, var2, var1, "start_kbd", 354, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_kbd$69 = Py.newCode(1, var2, var1, "end_kbd", 355, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_samp$70 = Py.newCode(2, var2, var1, "start_samp", 357, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_samp$71 = Py.newCode(1, var2, var1, "end_samp", 358, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_strong$72 = Py.newCode(2, var2, var1, "start_strong", 360, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_strong$73 = Py.newCode(1, var2, var1, "end_strong", 361, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_var$74 = Py.newCode(2, var2, var1, "start_var", 363, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_var$75 = Py.newCode(1, var2, var1, "end_var", 364, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_i$76 = Py.newCode(2, var2, var1, "start_i", 368, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_i$77 = Py.newCode(1, var2, var1, "end_i", 370, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_b$78 = Py.newCode(2, var2, var1, "start_b", 373, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_b$79 = Py.newCode(1, var2, var1, "end_b", 375, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      start_tt$80 = Py.newCode(2, var2, var1, "start_tt", 378, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_tt$81 = Py.newCode(1, var2, var1, "end_tt", 380, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "href", "name", "type", "attrname", "value"};
      start_a$82 = Py.newCode(2, var2, var1, "start_a", 383, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      end_a$83 = Py.newCode(1, var2, var1, "end_a", 397, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_br$84 = Py.newCode(2, var2, var1, "do_br", 402, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_hr$85 = Py.newCode(2, var2, var1, "do_hr", 407, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "align", "alt", "ismap", "src", "width", "height", "attrname", "value"};
      do_img$86 = Py.newCode(2, var2, var1, "do_img", 412, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs"};
      do_plaintext$87 = Py.newCode(2, var2, var1, "do_plaintext", 438, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag", "attrs"};
      unknown_starttag$88 = Py.newCode(3, var2, var1, "unknown_starttag", 444, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tag"};
      unknown_endtag$89 = Py.newCode(2, var2, var1, "unknown_endtag", 447, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "sys", "formatter", "silent", "file", "f", "msg", "data", "p"};
      test$90 = Py.newCode(1, var2, var1, "test", 451, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new htmllib$py("htmllib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(htmllib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.HTMLParseError$1(var2, var3);
         case 2:
            return this.HTMLParser$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.error$4(var2, var3);
         case 5:
            return this.reset$5(var2, var3);
         case 6:
            return this.handle_data$6(var2, var3);
         case 7:
            return this.save_bgn$7(var2, var3);
         case 8:
            return this.save_end$8(var2, var3);
         case 9:
            return this.anchor_bgn$9(var2, var3);
         case 10:
            return this.anchor_end$10(var2, var3);
         case 11:
            return this.handle_image$11(var2, var3);
         case 12:
            return this.start_html$12(var2, var3);
         case 13:
            return this.end_html$13(var2, var3);
         case 14:
            return this.start_head$14(var2, var3);
         case 15:
            return this.end_head$15(var2, var3);
         case 16:
            return this.start_body$16(var2, var3);
         case 17:
            return this.end_body$17(var2, var3);
         case 18:
            return this.start_title$18(var2, var3);
         case 19:
            return this.end_title$19(var2, var3);
         case 20:
            return this.do_base$20(var2, var3);
         case 21:
            return this.do_isindex$21(var2, var3);
         case 22:
            return this.do_link$22(var2, var3);
         case 23:
            return this.do_meta$23(var2, var3);
         case 24:
            return this.do_nextid$24(var2, var3);
         case 25:
            return this.start_h1$25(var2, var3);
         case 26:
            return this.end_h1$26(var2, var3);
         case 27:
            return this.start_h2$27(var2, var3);
         case 28:
            return this.end_h2$28(var2, var3);
         case 29:
            return this.start_h3$29(var2, var3);
         case 30:
            return this.end_h3$30(var2, var3);
         case 31:
            return this.start_h4$31(var2, var3);
         case 32:
            return this.end_h4$32(var2, var3);
         case 33:
            return this.start_h5$33(var2, var3);
         case 34:
            return this.end_h5$34(var2, var3);
         case 35:
            return this.start_h6$35(var2, var3);
         case 36:
            return this.end_h6$36(var2, var3);
         case 37:
            return this.do_p$37(var2, var3);
         case 38:
            return this.start_pre$38(var2, var3);
         case 39:
            return this.end_pre$39(var2, var3);
         case 40:
            return this.start_xmp$40(var2, var3);
         case 41:
            return this.end_xmp$41(var2, var3);
         case 42:
            return this.start_listing$42(var2, var3);
         case 43:
            return this.end_listing$43(var2, var3);
         case 44:
            return this.start_address$44(var2, var3);
         case 45:
            return this.end_address$45(var2, var3);
         case 46:
            return this.start_blockquote$46(var2, var3);
         case 47:
            return this.end_blockquote$47(var2, var3);
         case 48:
            return this.start_ul$48(var2, var3);
         case 49:
            return this.end_ul$49(var2, var3);
         case 50:
            return this.do_li$50(var2, var3);
         case 51:
            return this.start_ol$51(var2, var3);
         case 52:
            return this.end_ol$52(var2, var3);
         case 53:
            return this.start_menu$53(var2, var3);
         case 54:
            return this.end_menu$54(var2, var3);
         case 55:
            return this.start_dir$55(var2, var3);
         case 56:
            return this.end_dir$56(var2, var3);
         case 57:
            return this.start_dl$57(var2, var3);
         case 58:
            return this.end_dl$58(var2, var3);
         case 59:
            return this.do_dt$59(var2, var3);
         case 60:
            return this.do_dd$60(var2, var3);
         case 61:
            return this.ddpop$61(var2, var3);
         case 62:
            return this.start_cite$62(var2, var3);
         case 63:
            return this.end_cite$63(var2, var3);
         case 64:
            return this.start_code$64(var2, var3);
         case 65:
            return this.end_code$65(var2, var3);
         case 66:
            return this.start_em$66(var2, var3);
         case 67:
            return this.end_em$67(var2, var3);
         case 68:
            return this.start_kbd$68(var2, var3);
         case 69:
            return this.end_kbd$69(var2, var3);
         case 70:
            return this.start_samp$70(var2, var3);
         case 71:
            return this.end_samp$71(var2, var3);
         case 72:
            return this.start_strong$72(var2, var3);
         case 73:
            return this.end_strong$73(var2, var3);
         case 74:
            return this.start_var$74(var2, var3);
         case 75:
            return this.end_var$75(var2, var3);
         case 76:
            return this.start_i$76(var2, var3);
         case 77:
            return this.end_i$77(var2, var3);
         case 78:
            return this.start_b$78(var2, var3);
         case 79:
            return this.end_b$79(var2, var3);
         case 80:
            return this.start_tt$80(var2, var3);
         case 81:
            return this.end_tt$81(var2, var3);
         case 82:
            return this.start_a$82(var2, var3);
         case 83:
            return this.end_a$83(var2, var3);
         case 84:
            return this.do_br$84(var2, var3);
         case 85:
            return this.do_hr$85(var2, var3);
         case 86:
            return this.do_img$86(var2, var3);
         case 87:
            return this.do_plaintext$87(var2, var3);
         case 88:
            return this.unknown_starttag$88(var2, var3);
         case 89:
            return this.unknown_endtag$89(var2, var3);
         case 90:
            return this.test$90(var2, var3);
         default:
            return null;
      }
   }
}
