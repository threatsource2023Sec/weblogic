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
@Filename("cookielib.py")
public class cookielib$py extends PyFunctionTable implements PyRunnable {
   static cookielib$py self;
   static final PyCode f$0;
   static final PyCode _debug$1;
   static final PyCode _warn_unhandled_exception$2;
   static final PyCode _timegm$3;
   static final PyCode time2isoz$4;
   static final PyCode time2netscape$5;
   static final PyCode offset_from_tz_string$6;
   static final PyCode _str2time$7;
   static final PyCode http2time$8;
   static final PyCode iso2time$9;
   static final PyCode unmatched$10;
   static final PyCode split_header_words$11;
   static final PyCode join_header_words$12;
   static final PyCode _strip_quotes$13;
   static final PyCode parse_ns_headers$14;
   static final PyCode is_HDN$15;
   static final PyCode domain_match$16;
   static final PyCode liberal_is_HDN$17;
   static final PyCode user_domain_match$18;
   static final PyCode request_host$19;
   static final PyCode eff_request_host$20;
   static final PyCode request_path$21;
   static final PyCode request_port$22;
   static final PyCode uppercase_escaped_char$23;
   static final PyCode escape_path$24;
   static final PyCode reach$25;
   static final PyCode is_third_party$26;
   static final PyCode Cookie$27;
   static final PyCode __init__$28;
   static final PyCode has_nonstandard_attr$29;
   static final PyCode get_nonstandard_attr$30;
   static final PyCode set_nonstandard_attr$31;
   static final PyCode is_expired$32;
   static final PyCode __str__$33;
   static final PyCode __repr__$34;
   static final PyCode CookiePolicy$35;
   static final PyCode set_ok$36;
   static final PyCode return_ok$37;
   static final PyCode domain_return_ok$38;
   static final PyCode path_return_ok$39;
   static final PyCode DefaultCookiePolicy$40;
   static final PyCode __init__$41;
   static final PyCode blocked_domains$42;
   static final PyCode set_blocked_domains$43;
   static final PyCode is_blocked$44;
   static final PyCode allowed_domains$45;
   static final PyCode set_allowed_domains$46;
   static final PyCode is_not_allowed$47;
   static final PyCode set_ok$48;
   static final PyCode set_ok_version$49;
   static final PyCode set_ok_verifiability$50;
   static final PyCode set_ok_name$51;
   static final PyCode set_ok_path$52;
   static final PyCode set_ok_domain$53;
   static final PyCode set_ok_port$54;
   static final PyCode return_ok$55;
   static final PyCode return_ok_version$56;
   static final PyCode return_ok_verifiability$57;
   static final PyCode return_ok_secure$58;
   static final PyCode return_ok_expires$59;
   static final PyCode return_ok_port$60;
   static final PyCode return_ok_domain$61;
   static final PyCode domain_return_ok$62;
   static final PyCode path_return_ok$63;
   static final PyCode vals_sorted_by_key$64;
   static final PyCode deepvalues$65;
   static final PyCode Absent$66;
   static final PyCode CookieJar$67;
   static final PyCode __init__$68;
   static final PyCode set_policy$69;
   static final PyCode _cookies_for_domain$70;
   static final PyCode _cookies_for_request$71;
   static final PyCode _cookie_attrs$72;
   static final PyCode f$73;
   static final PyCode add_cookie_header$74;
   static final PyCode _normalized_cookie_tuples$75;
   static final PyCode _cookie_from_cookie_tuple$76;
   static final PyCode _cookies_from_attrs_set$77;
   static final PyCode _process_rfc2109_cookies$78;
   static final PyCode make_cookies$79;
   static final PyCode no_matching_rfc2965$80;
   static final PyCode set_cookie_if_ok$81;
   static final PyCode set_cookie$82;
   static final PyCode extract_cookies$83;
   static final PyCode clear$84;
   static final PyCode clear_session_cookies$85;
   static final PyCode clear_expired_cookies$86;
   static final PyCode __iter__$87;
   static final PyCode __len__$88;
   static final PyCode __repr__$89;
   static final PyCode __str__$90;
   static final PyCode LoadError$91;
   static final PyCode FileCookieJar$92;
   static final PyCode __init__$93;
   static final PyCode save$94;
   static final PyCode load$95;
   static final PyCode revert$96;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("HTTP cookie handling for web clients.\n\nThis module has (now fairly distant) origins in Gisle Aas' Perl module\nHTTP::Cookies, from the libwww-perl library.\n\nDocstrings, comments and debug strings in this code refer to the\nattributes of the HTTP cookie system as cookie-attributes, to distinguish\nthem clearly from Python attributes.\n\nClass diagram (note that BSDDBCookieJar and the MSIE* classes are not\ndistributed with the Python standard library, but are available from\nhttp://wwwsearch.sf.net/):\n\n                        CookieJar____\n                        /     \\      \\\n            FileCookieJar      \\      \\\n             /    |   \\         \\      \\\n MozillaCookieJar | LWPCookieJar \\      \\\n                  |               |      \\\n                  |   ---MSIEBase |       \\\n                  |  /      |     |        \\\n                  | /   MSIEDBCookieJar BSDDBCookieJar\n                  |/\n               MSIECookieJar\n\n"));
      var1.setline(26);
      PyString.fromInterned("HTTP cookie handling for web clients.\n\nThis module has (now fairly distant) origins in Gisle Aas' Perl module\nHTTP::Cookies, from the libwww-perl library.\n\nDocstrings, comments and debug strings in this code refer to the\nattributes of the HTTP cookie system as cookie-attributes, to distinguish\nthem clearly from Python attributes.\n\nClass diagram (note that BSDDBCookieJar and the MSIE* classes are not\ndistributed with the Python standard library, but are available from\nhttp://wwwsearch.sf.net/):\n\n                        CookieJar____\n                        /     \\      \\\n            FileCookieJar      \\      \\\n             /    |   \\         \\      \\\n MozillaCookieJar | LWPCookieJar \\      \\\n                  |               |      \\\n                  |   ---MSIEBase |       \\\n                  |  /      |     |        \\\n                  | /   MSIEDBCookieJar BSDDBCookieJar\n                  |/\n               MSIECookieJar\n\n");
      var1.setline(28);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("Cookie"), PyString.fromInterned("CookieJar"), PyString.fromInterned("CookiePolicy"), PyString.fromInterned("DefaultCookiePolicy"), PyString.fromInterned("FileCookieJar"), PyString.fromInterned("LWPCookieJar"), PyString.fromInterned("lwp_cookie_str"), PyString.fromInterned("LoadError"), PyString.fromInterned("MozillaCookieJar")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(32);
      PyObject var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var6 = imp.importOne("urlparse", var1, -1);
      var1.setlocal("urlparse", var6);
      var3 = null;
      var6 = imp.importOne("copy", var1, -1);
      var1.setlocal("copy", var6);
      var3 = null;
      var6 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var6);
      var3 = null;
      var6 = imp.importOne("urllib", var1, -1);
      var1.setlocal("urllib", var6);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(34);
         var6 = imp.importOneAs("threading", var1, -1);
         var1.setlocal("_threading", var6);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(36);
         var4 = imp.importOneAs("dummy_threading", var1, -1);
         var1.setlocal("_threading", var4);
         var4 = null;
      }

      var1.setline(37);
      var6 = imp.importOne("httplib", var1, -1);
      var1.setlocal("httplib", var6);
      var3 = null;
      var1.setline(38);
      String[] var8 = new String[]{"timegm"};
      PyObject[] var9 = imp.importFrom("calendar", var8, var1, -1);
      var4 = var9[0];
      var1.setlocal("timegm", var4);
      var4 = null;
      var1.setline(40);
      var6 = var1.getname("False");
      var1.setlocal("debug", var6);
      var3 = null;
      var1.setline(41);
      var6 = var1.getname("None");
      var1.setlocal("logger", var6);
      var3 = null;
      var1.setline(43);
      var9 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var9, _debug$1, (PyObject)null);
      var1.setlocal("_debug", var10);
      var3 = null;
      var1.setline(53);
      var6 = var1.getname("str").__call__(var2, var1.getname("httplib").__getattr__("HTTP_PORT"));
      var1.setlocal("DEFAULT_HTTP_PORT", var6);
      var3 = null;
      var1.setline(54);
      PyString var11 = PyString.fromInterned("a filename was not supplied (nor was the CookieJar instance initialised with one)");
      var1.setlocal("MISSING_FILENAME_TEXT", var11);
      var3 = null;
      var1.setline(57);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, _warn_unhandled_exception$2, (PyObject)null);
      var1.setlocal("_warn_unhandled_exception", var10);
      var3 = null;
      var1.setline(71);
      PyInteger var12 = Py.newInteger(1970);
      var1.setlocal("EPOCH_YEAR", var12);
      var3 = null;
      var1.setline(72);
      var9 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var9, _timegm$3, (PyObject)null);
      var1.setlocal("_timegm", var10);
      var3 = null;
      var1.setline(80);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("Mon"), PyString.fromInterned("Tue"), PyString.fromInterned("Wed"), PyString.fromInterned("Thu"), PyString.fromInterned("Fri"), PyString.fromInterned("Sat"), PyString.fromInterned("Sun")});
      var1.setlocal("DAYS", var3);
      var3 = null;
      var1.setline(81);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("Jan"), PyString.fromInterned("Feb"), PyString.fromInterned("Mar"), PyString.fromInterned("Apr"), PyString.fromInterned("May"), PyString.fromInterned("Jun"), PyString.fromInterned("Jul"), PyString.fromInterned("Aug"), PyString.fromInterned("Sep"), PyString.fromInterned("Oct"), PyString.fromInterned("Nov"), PyString.fromInterned("Dec")});
      var1.setlocal("MONTHS", var3);
      var3 = null;
      var1.setline(83);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("MONTHS_LOWER", var3);
      var3 = null;
      var1.setline(84);
      var6 = var1.getname("MONTHS").__iter__();

      while(true) {
         var1.setline(84);
         var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(86);
            var9 = new PyObject[]{var1.getname("None")};
            var10 = new PyFunction(var1.f_globals, var9, time2isoz$4, PyString.fromInterned("Return a string representing time in seconds since epoch, t.\n\n    If the function is called without an argument, it will use the current\n    time.\n\n    The format of the returned string is like \"YYYY-MM-DD hh:mm:ssZ\",\n    representing Universal Time (UTC, aka GMT).  An example of this format is:\n\n    1994-11-24 08:49:37Z\n\n    "));
            var1.setlocal("time2isoz", var10);
            var3 = null;
            var1.setline(103);
            var9 = new PyObject[]{var1.getname("None")};
            var10 = new PyFunction(var1.f_globals, var9, time2netscape$5, PyString.fromInterned("Return a string representing time in seconds since epoch, t.\n\n    If the function is called without an argument, it will use the current\n    time.\n\n    The format of the returned string is like this:\n\n    Wed, DD-Mon-YYYY HH:MM:SS GMT\n\n    "));
            var1.setlocal("time2netscape", var10);
            var3 = null;
            var1.setline(120);
            PyDictionary var13 = new PyDictionary(new PyObject[]{PyString.fromInterned("GMT"), var1.getname("None"), PyString.fromInterned("UTC"), var1.getname("None"), PyString.fromInterned("UT"), var1.getname("None"), PyString.fromInterned("Z"), var1.getname("None")});
            var1.setlocal("UTC_ZONES", var13);
            var3 = null;
            var1.setline(122);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^([-+])?(\\d\\d?):?(\\d\\d)?$"));
            var1.setlocal("TIMEZONE_RE", var6);
            var3 = null;
            var1.setline(123);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, offset_from_tz_string$6, (PyObject)null);
            var1.setlocal("offset_from_tz_string", var10);
            var3 = null;
            var1.setline(137);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, _str2time$7, (PyObject)null);
            var1.setlocal("_str2time", var10);
            var3 = null;
            var1.setline(190);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[SMTWF][a-z][a-z], (\\d\\d) ([JFMASOND][a-z][a-z]) (\\d\\d\\d\\d) (\\d\\d):(\\d\\d):(\\d\\d) GMT$"));
            var1.setlocal("STRICT_DATE_RE", var6);
            var3 = null;
            var1.setline(193);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^(?:Sun|Mon|Tue|Wed|Thu|Fri|Sat)[a-z]*,?\\s*"), (PyObject)var1.getname("re").__getattr__("I"));
            var1.setlocal("WEEKDAY_RE", var6);
            var3 = null;
            var1.setline(195);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\n    (\\d\\d?)            # day\n       (?:\\s+|[-\\/])\n    (\\w+)              # month\n        (?:\\s+|[-\\/])\n    (\\d+)              # year\n    (?:\n          (?:\\s+|:)    # separator before clock\n       (\\d\\d?):(\\d\\d)  # hour:min\n       (?::(\\d\\d))?    # optional seconds\n    )?                 # optional clock\n       \\s*\n    ([-+]?\\d{2,4}|(?![APap][Mm]\\b)[A-Za-z]+)? # timezone\n       \\s*\n    (?:\\(\\w+\\))?       # ASCII representation of timezone in parens.\n       \\s*$"), (PyObject)var1.getname("re").__getattr__("X"));
            var1.setlocal("LOOSE_HTTP_DATE_RE", var6);
            var3 = null;
            var1.setline(212);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, http2time$8, PyString.fromInterned("Returns time in seconds since epoch of time represented by a string.\n\n    Return value is an integer.\n\n    None is returned if the format of str is unrecognized, the time is outside\n    the representable range, or the timezone string is not recognized.  If the\n    string contains no timezone, UTC is assumed.\n\n    The timezone in the string may be numerical (like \"-0800\" or \"+0100\") or a\n    string timezone (like \"UTC\", \"GMT\", \"BST\" or \"EST\").  Currently, only the\n    timezone strings equivalent to UTC (zero offset) are known to the function.\n\n    The function loosely parses the following formats:\n\n    Wed, 09 Feb 1994 22:23:32 GMT       -- HTTP format\n    Tuesday, 08-Feb-94 14:15:29 GMT     -- old rfc850 HTTP format\n    Tuesday, 08-Feb-1994 14:15:29 GMT   -- broken rfc850 HTTP format\n    09 Feb 1994 22:23:32 GMT            -- HTTP format (no weekday)\n    08-Feb-94 14:15:29 GMT              -- rfc850 format (no weekday)\n    08-Feb-1994 14:15:29 GMT            -- broken rfc850 format (no weekday)\n\n    The parser ignores leading and trailing whitespace.  The time may be\n    absent.\n\n    If the year is given with only 2 digits, the function will select the\n    century that makes the year closest to the current date.\n\n    "));
            var1.setlocal("http2time", var10);
            var3 = null;
            var1.setline(268);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\n    (\\d{4})              # year\n       [-\\/]?\n    (\\d\\d?)              # numerical month\n       [-\\/]?\n    (\\d\\d?)              # day\n   (?:\n         (?:\\s+|[-:Tt])  # separator before clock\n      (\\d\\d?):?(\\d\\d)    # hour:min\n      (?::?(\\d\\d(?:\\.\\d*)?))?  # optional seconds (and fractional)\n   )?                    # optional clock\n      \\s*\n   ([-+]?\\d\\d?:?(:?\\d\\d)?\n    |Z|z)?               # timezone  (Z is \"zero meridian\", i.e. GMT)\n      \\s*$"), (PyObject)var1.getname("re").__getattr__("X"));
            var1.setlocal("ISO_DATE_RE", var6);
            var3 = null;
            var1.setline(284);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, iso2time$9, PyString.fromInterned("\n    As for http2time, but parses the ISO 8601 formats:\n\n    1994-02-03 14:15:29 -0100    -- ISO 8601 format\n    1994-02-03 14:15:29          -- zone is optional\n    1994-02-03                   -- only date\n    1994-02-03T14:15:29          -- Use T as separator\n    19940203T141529Z             -- ISO 8601 compact format\n    19940203                     -- only date\n\n    "));
            var1.setlocal("iso2time", var10);
            var3 = null;
            var1.setline(317);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, unmatched$10, PyString.fromInterned("Return unmatched part of re.Match object."));
            var1.setlocal("unmatched", var10);
            var3 = null;
            var1.setline(322);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\s*([^=\\s;,]+)"));
            var1.setlocal("HEADER_TOKEN_RE", var6);
            var3 = null;
            var1.setline(323);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\s*=\\s*\\\"([^\\\"\\\\]*(?:\\\\.[^\\\"\\\\]*)*)\\\""));
            var1.setlocal("HEADER_QUOTED_VALUE_RE", var6);
            var3 = null;
            var1.setline(324);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\s*=\\s*([^\\s;,]*)"));
            var1.setlocal("HEADER_VALUE_RE", var6);
            var3 = null;
            var1.setline(325);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\\(.)"));
            var1.setlocal("HEADER_ESCAPE_RE", var6);
            var3 = null;
            var1.setline(326);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, split_header_words$11, PyString.fromInterned("Parse header values into a list of lists containing key,value pairs.\n\n    The function knows how to deal with \",\", \";\" and \"=\" as well as quoted\n    values after \"=\".  A list of space separated tokens are parsed as if they\n    were separated by \";\".\n\n    If the header_values passed as argument contains multiple values, then they\n    are treated as if they were a single value separated by comma \",\".\n\n    This means that this function is useful for parsing header fields that\n    follow this syntax (BNF as from the HTTP/1.1 specification, but we relax\n    the requirement for tokens).\n\n      headers           = #header\n      header            = (token | parameter) *( [\";\"] (token | parameter))\n\n      token             = 1*<any CHAR except CTLs or separators>\n      separators        = \"(\" | \")\" | \"<\" | \">\" | \"@\"\n                        | \",\" | \";\" | \":\" | \"\\\" | <\">\n                        | \"/\" | \"[\" | \"]\" | \"?\" | \"=\"\n                        | \"{\" | \"}\" | SP | HT\n\n      quoted-string     = ( <\"> *(qdtext | quoted-pair ) <\"> )\n      qdtext            = <any TEXT except <\">>\n      quoted-pair       = \"\\\" CHAR\n\n      parameter         = attribute \"=\" value\n      attribute         = token\n      value             = token | quoted-string\n\n    Each header is represented by a list of key/value pairs.  The value for a\n    simple token (not part of a parameter) is None.  Syntactically incorrect\n    headers will not necessarily be parsed as you would want.\n\n    This is easier to describe with some examples:\n\n    >>> split_header_words(['foo=\"bar\"; port=\"80,81\"; discard, bar=baz'])\n    [[('foo', 'bar'), ('port', '80,81'), ('discard', None)], [('bar', 'baz')]]\n    >>> split_header_words(['text/html; charset=\"iso-8859-1\"'])\n    [[('text/html', None), ('charset', 'iso-8859-1')]]\n    >>> split_header_words([r'Basic realm=\"\\\"foo\\bar\\\"\"'])\n    [[('Basic', None), ('realm', '\"foobar\"')]]\n\n    "));
            var1.setlocal("split_header_words", var10);
            var3 = null;
            var1.setline(411);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([\\\"\\\\])"));
            var1.setlocal("HEADER_JOIN_ESCAPE_RE", var6);
            var3 = null;
            var1.setline(412);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, join_header_words$12, PyString.fromInterned("Do the inverse (almost) of the conversion done by split_header_words.\n\n    Takes a list of lists of (key, value) pairs and produces a single header\n    value.  Attribute values are quoted if needed.\n\n    >>> join_header_words([[(\"text/plain\", None), (\"charset\", \"iso-8859/1\")]])\n    'text/plain; charset=\"iso-8859/1\"'\n    >>> join_header_words([[(\"text/plain\", None)], [(\"charset\", \"iso-8859/1\")]])\n    'text/plain, charset=\"iso-8859/1\"'\n\n    "));
            var1.setlocal("join_header_words", var10);
            var3 = null;
            var1.setline(437);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, _strip_quotes$13, (PyObject)null);
            var1.setlocal("_strip_quotes", var10);
            var3 = null;
            var1.setline(444);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, parse_ns_headers$14, PyString.fromInterned("Ad-hoc parser for Netscape protocol cookie-attributes.\n\n    The old Netscape cookie format for Set-Cookie can for instance contain\n    an unquoted \",\" in the expires field, so we have to use this ad-hoc\n    parser instead of split_header_words.\n\n    XXX This may not make the best possible effort to parse all the crap\n    that Netscape Cookie headers contain.  Ronald Tschalar's HTTPClient\n    parser is probably better, so could do worse than following that if\n    this ever gives any trouble.\n\n    Currently, this is also used for parsing RFC 2109 cookies.\n\n    "));
            var1.setlocal("parse_ns_headers", var10);
            var3 = null;
            var1.setline(496);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\.\\d+$"));
            var1.setlocal("IPV4_RE", var6);
            var3 = null;
            var1.setline(497);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, is_HDN$15, PyString.fromInterned("Return True if text is a host domain name."));
            var1.setlocal("is_HDN", var10);
            var3 = null;
            var1.setline(512);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, domain_match$16, PyString.fromInterned("Return True if domain A domain-matches domain B, according to RFC 2965.\n\n    A and B may be host domain names or IP addresses.\n\n    RFC 2965, section 1:\n\n    Host names can be specified either as an IP address or a HDN string.\n    Sometimes we compare one host name with another.  (Such comparisons SHALL\n    be case-insensitive.)  Host A's name domain-matches host B's if\n\n         *  their host name strings string-compare equal; or\n\n         * A is a HDN string and has the form NB, where N is a non-empty\n            name string, B has the form .B', and B' is a HDN string.  (So,\n            x.y.com domain-matches .Y.com but not Y.com.)\n\n    Note that domain-match is not a commutative operation: a.b.c.com\n    domain-matches .c.com, but not the reverse.\n\n    "));
            var1.setlocal("domain_match", var10);
            var3 = null;
            var1.setline(551);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, liberal_is_HDN$17, PyString.fromInterned("Return True if text is a sort-of-like a host domain name.\n\n    For accepting/blocking domains.\n\n    "));
            var1.setlocal("liberal_is_HDN", var10);
            var3 = null;
            var1.setline(561);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, user_domain_match$18, PyString.fromInterned("For blocking/accepting domains.\n\n    A and B may be host domain names or IP addresses.\n\n    "));
            var1.setlocal("user_domain_match", var10);
            var3 = null;
            var1.setline(581);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":\\d+$"));
            var1.setlocal("cut_port_re", var6);
            var3 = null;
            var1.setline(582);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, request_host$19, PyString.fromInterned("Return request-host, as defined by RFC 2965.\n\n    Variation from RFC: returned value is lowercased, for convenient\n    comparison.\n\n    "));
            var1.setlocal("request_host", var10);
            var3 = null;
            var1.setline(598);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, eff_request_host$20, PyString.fromInterned("Return a tuple (request-host, effective request-host name).\n\n    As defined by RFC 2965, except both are lowercased.\n\n    "));
            var1.setlocal("eff_request_host", var10);
            var3 = null;
            var1.setline(609);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, request_path$21, PyString.fromInterned("Path component of request-URI, as defined by RFC 2965."));
            var1.setlocal("request_path", var10);
            var3 = null;
            var1.setline(619);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, request_port$22, (PyObject)null);
            var1.setlocal("request_port", var10);
            var3 = null;
            var1.setline(635);
            var11 = PyString.fromInterned("%/;:@&=+$,!~*'()");
            var1.setlocal("HTTP_PATH_SAFE", var11);
            var3 = null;
            var1.setline(636);
            var6 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%([0-9a-fA-F][0-9a-fA-F])"));
            var1.setlocal("ESCAPED_CHAR_RE", var6);
            var3 = null;
            var1.setline(637);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, uppercase_escaped_char$23, (PyObject)null);
            var1.setlocal("uppercase_escaped_char", var10);
            var3 = null;
            var1.setline(639);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, escape_path$24, PyString.fromInterned("Escape any invalid characters in HTTP URL, and uppercase all escapes."));
            var1.setlocal("escape_path", var10);
            var3 = null;
            var1.setline(655);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, reach$25, PyString.fromInterned("Return reach of host h, as defined by RFC 2965, section 1.\n\n    The reach R of a host name H is defined as follows:\n\n       *  If\n\n          -  H is the host domain name of a host; and,\n\n          -  H has the form A.B; and\n\n          -  A has no embedded (that is, interior) dots; and\n\n          -  B has at least one embedded dot, or B is the string \"local\".\n             then the reach of H is .B.\n\n       *  Otherwise, the reach of H is H.\n\n    >>> reach(\"www.acme.com\")\n    '.acme.com'\n    >>> reach(\"acme.com\")\n    'acme.com'\n    >>> reach(\"acme.local\")\n    '.local'\n\n    "));
            var1.setlocal("reach", var10);
            var3 = null;
            var1.setline(690);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, is_third_party$26, PyString.fromInterned("\n\n    RFC 2965, section 3.3.6:\n\n        An unverifiable transaction is to a third-party host if its request-\n        host U does not domain-match the reach R of the request-host O in the\n        origin transaction.\n\n    "));
            var1.setlocal("is_third_party", var10);
            var3 = null;
            var1.setline(707);
            var9 = Py.EmptyObjects;
            var4 = Py.makeClass("Cookie", var9, Cookie$27);
            var1.setlocal("Cookie", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(805);
            var9 = Py.EmptyObjects;
            var4 = Py.makeClass("CookiePolicy", var9, CookiePolicy$35);
            var1.setlocal("CookiePolicy", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(838);
            var9 = new PyObject[]{var1.getname("CookiePolicy")};
            var4 = Py.makeClass("DefaultCookiePolicy", var9, DefaultCookiePolicy$40);
            var1.setlocal("DefaultCookiePolicy", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(1175);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, vals_sorted_by_key$64, (PyObject)null);
            var1.setlocal("vals_sorted_by_key", var10);
            var3 = null;
            var1.setline(1180);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, deepvalues$65, PyString.fromInterned("Iterates over nested mapping, depth-first, in sorted order by key."));
            var1.setlocal("deepvalues", var10);
            var3 = null;
            var1.setline(1199);
            var9 = Py.EmptyObjects;
            var4 = Py.makeClass("Absent", var9, Absent$66);
            var1.setlocal("Absent", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(1201);
            var9 = Py.EmptyObjects;
            var4 = Py.makeClass("CookieJar", var9, CookieJar$67);
            var1.setlocal("CookieJar", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(1731);
            var9 = new PyObject[]{var1.getname("IOError")};
            var4 = Py.makeClass("LoadError", var9, LoadError$91);
            var1.setlocal("LoadError", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(1733);
            var9 = new PyObject[]{var1.getname("CookieJar")};
            var4 = Py.makeClass("FileCookieJar", var9, FileCookieJar$92);
            var1.setlocal("FileCookieJar", var4);
            var4 = null;
            Arrays.fill(var9, (Object)null);
            var1.setline(1793);
            var8 = new String[]{"LWPCookieJar", "lwp_cookie_str"};
            var9 = imp.importFrom("_LWPCookieJar", var8, var1, -1);
            var4 = var9[0];
            var1.setlocal("LWPCookieJar", var4);
            var4 = null;
            var4 = var9[1];
            var1.setlocal("lwp_cookie_str", var4);
            var4 = null;
            var1.setline(1794);
            var8 = new String[]{"MozillaCookieJar"};
            var9 = imp.importFrom("_MozillaCookieJar", var8, var1, -1);
            var4 = var9[0];
            var1.setlocal("MozillaCookieJar", var4);
            var4 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("month", var4);
         var1.setline(84);
         var1.getname("MONTHS_LOWER").__getattr__("append").__call__(var2, var1.getname("month").__getattr__("lower").__call__(var2));
      }
   }

   public PyObject _debug$1(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      if (var1.getglobal("debug").__not__().__nonzero__()) {
         var1.setline(45);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(47);
         PyObject var3;
         if (var1.getglobal("logger").__not__().__nonzero__()) {
            var1.setline(48);
            var3 = imp.importOne("logging", var1, -1);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(49);
            var3 = var1.getlocal(1).__getattr__("getLogger").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cookielib"));
            var1.setglobal("logger", var3);
            var3 = null;
         }

         var1.setline(50);
         PyObject var10000 = var1.getglobal("logger").__getattr__("debug");
         PyObject[] var5 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var5, var4, var1.getlocal(0), (PyObject)null);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _warn_unhandled_exception$2(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var3 = imp.importOne("StringIO", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getlocal(2).__getattr__("StringIO").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(63);
      var1.getlocal(1).__getattr__("print_exc").__call__(var2, var1.getglobal("None"), var1.getlocal(3));
      var1.setline(64);
      var3 = var1.getlocal(3).__getattr__("getvalue").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(65);
      PyObject var10000 = var1.getlocal(0).__getattr__("warn");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("cookielib bug!\n%s")._mod(var1.getlocal(4)), Py.newInteger(2)};
      String[] var4 = new String[]{"stacklevel"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _timegm$3(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
      PyObject[] var4 = Py.unpackSequence(var3, 6);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(74);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(var1.getglobal("EPOCH_YEAR"));
      var3 = null;
      if (var10000.__nonzero__()) {
         PyInteger var6 = Py.newInteger(1);
         PyObject var10001 = var1.getlocal(2);
         PyInteger var8 = var6;
         var3 = var10001;
         PyObject var7;
         if ((var7 = var8._le(var10001)).__nonzero__()) {
            var7 = var3._le(Py.newInteger(12));
         }

         var10000 = var7;
         var3 = null;
         if (var7.__nonzero__()) {
            var6 = Py.newInteger(1);
            var10001 = var1.getlocal(3);
            var8 = var6;
            var3 = var10001;
            if ((var7 = var8._le(var10001)).__nonzero__()) {
               var7 = var3._le(Py.newInteger(31));
            }

            var10000 = var7;
            var3 = null;
            if (var7.__nonzero__()) {
               var6 = Py.newInteger(0);
               var10001 = var1.getlocal(4);
               var8 = var6;
               var3 = var10001;
               if ((var7 = var8._le(var10001)).__nonzero__()) {
                  var7 = var3._le(Py.newInteger(24));
               }

               var10000 = var7;
               var3 = null;
               if (var7.__nonzero__()) {
                  var6 = Py.newInteger(0);
                  var10001 = var1.getlocal(5);
                  var8 = var6;
                  var3 = var10001;
                  if ((var7 = var8._le(var10001)).__nonzero__()) {
                     var7 = var3._le(Py.newInteger(59));
                  }

                  var10000 = var7;
                  var3 = null;
                  if (var7.__nonzero__()) {
                     var6 = Py.newInteger(0);
                     var10001 = var1.getlocal(6);
                     var8 = var6;
                     var3 = var10001;
                     if ((var7 = var8._le(var10001)).__nonzero__()) {
                        var7 = var3._le(Py.newInteger(61));
                     }

                     var10000 = var7;
                     var3 = null;
                  }
               }
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(76);
         var3 = var1.getglobal("timegm").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(78);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject time2isoz$4(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      PyString.fromInterned("Return a string representing time in seconds since epoch, t.\n\n    If the function is called without an argument, it will use the current\n    time.\n\n    The format of the returned string is like \"YYYY-MM-DD hh:mm:ssZ\",\n    representing Universal Time (UTC, aka GMT).  An example of this format is:\n\n    1994-11-24 08:49:37Z\n\n    ");
      var1.setline(98);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(98);
         var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(99);
      var3 = var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(0)).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
      PyObject[] var4 = Py.unpackSequence(var3, 6);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(100);
      var3 = PyString.fromInterned("%04d-%02d-%02d %02d:%02d:%02dZ")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject time2netscape$5(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyString.fromInterned("Return a string representing time in seconds since epoch, t.\n\n    If the function is called without an argument, it will use the current\n    time.\n\n    The format of the returned string is like this:\n\n    Wed, DD-Mon-YYYY HH:MM:SS GMT\n\n    ");
      var1.setline(114);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(115);
      var3 = var1.getglobal("time").__getattr__("gmtime").__call__(var2, var1.getlocal(0)).__getslice__((PyObject)null, Py.newInteger(7), (PyObject)null);
      PyObject[] var4 = Py.unpackSequence(var3, 7);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[6];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(116);
      var3 = PyString.fromInterned("%s %02d-%s-%04d %02d:%02d:%02d GMT")._mod(new PyTuple(new PyObject[]{var1.getglobal("DAYS").__getitem__(var1.getlocal(7)), var1.getlocal(3), var1.getglobal("MONTHS").__getitem__(var1.getlocal(2)._sub(Py.newInteger(1))), var1.getlocal(1), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject offset_from_tz_string$6(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(125);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getglobal("UTC_ZONES"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(126);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(1, var4);
         var3 = null;
      } else {
         var1.setline(128);
         var3 = var1.getglobal("TIMEZONE_RE").__getattr__("search").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(129);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(130);
            var3 = Py.newInteger(3600)._mul(var1.getglobal("int").__call__(var2, var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2))));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(131);
            if (var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(3)).__nonzero__()) {
               var1.setline(132);
               var3 = var1.getlocal(1)._add(Py.newInteger(60)._mul(var1.getglobal("int").__call__(var2, var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(3)))));
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(133);
            var3 = var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var10000 = var3._eq(PyString.fromInterned("-"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(134);
               var3 = var1.getlocal(1).__neg__();
               var1.setlocal(1, var3);
               var3 = null;
            }
         }
      }

      var1.setline(135);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _str2time$7(PyFrame var1, ThreadState var2) {
      PyException var3;
      PyObject var5;
      PyObject var9;
      try {
         var1.setline(141);
         var9 = var1.getglobal("MONTHS_LOWER").__getattr__("index").__call__(var2, var1.getlocal(1).__getattr__("lower").__call__(var2))._add(Py.newInteger(1));
         var1.setlocal(1, var9);
         var3 = null;
      } catch (Throwable var8) {
         var3 = Py.setException(var8, var1);
         if (!var3.match(var1.getglobal("ValueError"))) {
            throw var3;
         }

         PyException var4;
         PyObject var10;
         try {
            var1.setline(145);
            var10 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
            var1.setlocal(7, var10);
            var4 = null;
         } catch (Throwable var7) {
            var4 = Py.setException(var7, var1);
            if (var4.match(var1.getglobal("ValueError"))) {
               var1.setline(147);
               var5 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var5;
            }

            throw var4;
         }

         var1.setline(148);
         PyInteger var12 = Py.newInteger(1);
         PyObject var10001 = var1.getlocal(7);
         PyInteger var10000 = var12;
         var10 = var10001;
         PyObject var6;
         if ((var6 = var10000._le(var10001)).__nonzero__()) {
            var6 = var10._le(Py.newInteger(12));
         }

         var4 = null;
         if (!var6.__nonzero__()) {
            var1.setline(151);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(149);
         var10 = var1.getlocal(7);
         var1.setlocal(1, var10);
         var4 = null;
      }

      var1.setline(154);
      var9 = var1.getlocal(3);
      PyObject var14 = var9._is(var1.getglobal("None"));
      var3 = null;
      PyInteger var11;
      if (var14.__nonzero__()) {
         var1.setline(154);
         var11 = Py.newInteger(0);
         var1.setlocal(3, var11);
         var3 = null;
      }

      var1.setline(155);
      var9 = var1.getlocal(4);
      var14 = var9._is(var1.getglobal("None"));
      var3 = null;
      if (var14.__nonzero__()) {
         var1.setline(155);
         var11 = Py.newInteger(0);
         var1.setlocal(4, var11);
         var3 = null;
      }

      var1.setline(156);
      var9 = var1.getlocal(5);
      var14 = var9._is(var1.getglobal("None"));
      var3 = null;
      if (var14.__nonzero__()) {
         var1.setline(156);
         var11 = Py.newInteger(0);
         var1.setlocal(5, var11);
         var3 = null;
      }

      var1.setline(158);
      var9 = var1.getglobal("int").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(159);
      var9 = var1.getglobal("int").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var9);
      var3 = null;
      var1.setline(160);
      var9 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(161);
      var9 = var1.getglobal("int").__call__(var2, var1.getlocal(4));
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(162);
      var9 = var1.getglobal("int").__call__(var2, var1.getlocal(5));
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(164);
      var9 = var1.getlocal(2);
      var14 = var9._lt(Py.newInteger(1000));
      var3 = null;
      if (var14.__nonzero__()) {
         var1.setline(166);
         var9 = var1.getglobal("time").__getattr__("localtime").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2)).__getitem__(Py.newInteger(0));
         var1.setlocal(8, var9);
         var3 = null;
         var1.setline(167);
         var9 = var1.getlocal(8)._mod(Py.newInteger(100));
         var1.setlocal(9, var9);
         var3 = null;
         var1.setline(168);
         var9 = var1.getlocal(2);
         var1.setlocal(10, var9);
         var3 = null;
         var1.setline(169);
         var9 = var1.getlocal(2)._add(var1.getlocal(8))._sub(var1.getlocal(9));
         var1.setlocal(2, var9);
         var3 = null;
         var1.setline(170);
         var9 = var1.getlocal(9)._sub(var1.getlocal(10));
         var1.setlocal(9, var9);
         var3 = null;
         var1.setline(171);
         var9 = var1.getglobal("abs").__call__(var2, var1.getlocal(9));
         var14 = var9._gt(Py.newInteger(50));
         var3 = null;
         if (var14.__nonzero__()) {
            var1.setline(172);
            var9 = var1.getlocal(9);
            var14 = var9._gt(Py.newInteger(0));
            var3 = null;
            if (var14.__nonzero__()) {
               var1.setline(172);
               var9 = var1.getlocal(2)._add(Py.newInteger(100));
               var1.setlocal(2, var9);
               var3 = null;
            } else {
               var1.setline(173);
               var9 = var1.getlocal(2)._sub(Py.newInteger(100));
               var1.setlocal(2, var9);
               var3 = null;
            }
         }
      }

      var1.setline(176);
      var9 = var1.getglobal("_timegm").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1), var1.getlocal(0), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)})));
      var1.setlocal(11, var9);
      var3 = null;
      var1.setline(178);
      var9 = var1.getlocal(11);
      var14 = var9._isnot(var1.getglobal("None"));
      var3 = null;
      if (var14.__nonzero__()) {
         var1.setline(180);
         var9 = var1.getlocal(6);
         var14 = var9._is(var1.getglobal("None"));
         var3 = null;
         if (var14.__nonzero__()) {
            var1.setline(181);
            PyString var13 = PyString.fromInterned("UTC");
            var1.setlocal(6, var13);
            var3 = null;
         }

         var1.setline(182);
         var9 = var1.getlocal(6).__getattr__("upper").__call__(var2);
         var1.setlocal(6, var9);
         var3 = null;
         var1.setline(183);
         var9 = var1.getglobal("offset_from_tz_string").__call__(var2, var1.getlocal(6));
         var1.setlocal(12, var9);
         var3 = null;
         var1.setline(184);
         var9 = var1.getlocal(12);
         var14 = var9._is(var1.getglobal("None"));
         var3 = null;
         if (var14.__nonzero__()) {
            var1.setline(185);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(186);
         var9 = var1.getlocal(11)._sub(var1.getlocal(12));
         var1.setlocal(11, var9);
         var3 = null;
      }

      var1.setline(188);
      var5 = var1.getlocal(11);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject http2time$8(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyString.fromInterned("Returns time in seconds since epoch of time represented by a string.\n\n    Return value is an integer.\n\n    None is returned if the format of str is unrecognized, the time is outside\n    the representable range, or the timezone string is not recognized.  If the\n    string contains no timezone, UTC is assumed.\n\n    The timezone in the string may be numerical (like \"-0800\" or \"+0100\") or a\n    string timezone (like \"UTC\", \"GMT\", \"BST\" or \"EST\").  Currently, only the\n    timezone strings equivalent to UTC (zero offset) are known to the function.\n\n    The function loosely parses the following formats:\n\n    Wed, 09 Feb 1994 22:23:32 GMT       -- HTTP format\n    Tuesday, 08-Feb-94 14:15:29 GMT     -- old rfc850 HTTP format\n    Tuesday, 08-Feb-1994 14:15:29 GMT   -- broken rfc850 HTTP format\n    09 Feb 1994 22:23:32 GMT            -- HTTP format (no weekday)\n    08-Feb-94 14:15:29 GMT              -- rfc850 format (no weekday)\n    08-Feb-1994 14:15:29 GMT            -- broken rfc850 format (no weekday)\n\n    The parser ignores leading and trailing whitespace.  The time may be\n    absent.\n\n    If the year is given with only 2 digits, the function will select the\n    century that makes the year closest to the current date.\n\n    ");
      var1.setline(242);
      PyObject var3 = var1.getglobal("STRICT_DATE_RE").__getattr__("search").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(243);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(244);
         var3 = var1.getlocal(1).__getattr__("groups").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(245);
         var3 = var1.getglobal("MONTHS_LOWER").__getattr__("index").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)).__getattr__("lower").__call__(var2))._add(Py.newInteger(1));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(246);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(2))), var1.getlocal(3), var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0))), var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(3))), var1.getglobal("int").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(4))), var1.getglobal("float").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(5)))});
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(248);
         var3 = var1.getglobal("_timegm").__call__(var2, var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(253);
         PyObject var4 = var1.getlocal(0).__getattr__("lstrip").__call__(var2);
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(254);
         var4 = var1.getglobal("WEEKDAY_RE").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(1));
         var1.setlocal(0, var4);
         var4 = null;
         var1.setline(257);
         var4 = (new PyList(new PyObject[]{var1.getglobal("None")}))._mul(Py.newInteger(7));
         PyObject[] var5 = Py.unpackSequence(var4, 7);
         PyObject var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var5[4];
         var1.setlocal(8, var6);
         var6 = null;
         var6 = var5[5];
         var1.setlocal(9, var6);
         var6 = null;
         var6 = var5[6];
         var1.setlocal(10, var6);
         var6 = null;
         var4 = null;
         var1.setline(260);
         var4 = var1.getglobal("LOOSE_HTTP_DATE_RE").__getattr__("search").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(261);
         var4 = var1.getlocal(1);
         PyObject var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(262);
            var4 = var1.getlocal(1).__getattr__("groups").__call__(var2);
            var5 = Py.unpackSequence(var4, 7);
            var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[4];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var5[5];
            var1.setlocal(9, var6);
            var6 = null;
            var6 = var5[6];
            var1.setlocal(10, var6);
            var6 = null;
            var4 = null;
            var1.setline(266);
            var10000 = var1.getglobal("_str2time");
            PyObject[] var8 = new PyObject[]{var1.getlocal(5), var1.getlocal(3), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10)};
            var3 = var10000.__call__(var2, var8);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(264);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject iso2time$9(PyFrame var1, ThreadState var2) {
      var1.setline(295);
      PyString.fromInterned("\n    As for http2time, but parses the ISO 8601 formats:\n\n    1994-02-03 14:15:29 -0100    -- ISO 8601 format\n    1994-02-03 14:15:29          -- zone is optional\n    1994-02-03                   -- only date\n    1994-02-03T14:15:29          -- Use T as separator\n    19940203T141529Z             -- ISO 8601 compact format\n    19940203                     -- only date\n\n    ");
      var1.setline(297);
      PyObject var3 = var1.getlocal(0).__getattr__("lstrip").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(300);
      var3 = (new PyList(new PyObject[]{var1.getglobal("None")}))._mul(Py.newInteger(7));
      PyObject[] var4 = Py.unpackSequence(var3, 7);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[6];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(303);
      var3 = var1.getglobal("ISO_DATE_RE").__getattr__("search").__call__(var2, var1.getlocal(0));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(304);
      var3 = var1.getlocal(8);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(307);
         var3 = var1.getlocal(8).__getattr__("groups").__call__(var2);
         var4 = Py.unpackSequence(var3, 8);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[5];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[6];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[7];
         var1.setlocal(9, var5);
         var5 = null;
         var3 = null;
         var1.setline(311);
         var10000 = var1.getglobal("_str2time");
         var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)};
         var3 = var10000.__call__(var2, var4);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(309);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unmatched$10(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyString.fromInterned("Return unmatched part of re.Match object.");
      var1.setline(319);
      PyObject var3 = var1.getlocal(0).__getattr__("span").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(320);
      var3 = var1.getlocal(0).__getattr__("string").__getslice__((PyObject)null, var1.getlocal(1), (PyObject)null)._add(var1.getlocal(0).__getattr__("string").__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject split_header_words$11(PyFrame var1, ThreadState var2) {
      var1.setline(370);
      PyString.fromInterned("Parse header values into a list of lists containing key,value pairs.\n\n    The function knows how to deal with \",\", \";\" and \"=\" as well as quoted\n    values after \"=\".  A list of space separated tokens are parsed as if they\n    were separated by \";\".\n\n    If the header_values passed as argument contains multiple values, then they\n    are treated as if they were a single value separated by comma \",\".\n\n    This means that this function is useful for parsing header fields that\n    follow this syntax (BNF as from the HTTP/1.1 specification, but we relax\n    the requirement for tokens).\n\n      headers           = #header\n      header            = (token | parameter) *( [\";\"] (token | parameter))\n\n      token             = 1*<any CHAR except CTLs or separators>\n      separators        = \"(\" | \")\" | \"<\" | \">\" | \"@\"\n                        | \",\" | \";\" | \":\" | \"\\\" | <\">\n                        | \"/\" | \"[\" | \"]\" | \"?\" | \"=\"\n                        | \"{\" | \"}\" | SP | HT\n\n      quoted-string     = ( <\"> *(qdtext | quoted-pair ) <\"> )\n      qdtext            = <any TEXT except <\">>\n      quoted-pair       = \"\\\" CHAR\n\n      parameter         = attribute \"=\" value\n      attribute         = token\n      value             = token | quoted-string\n\n    Each header is represented by a list of key/value pairs.  The value for a\n    simple token (not part of a parameter) is None.  Syntactically incorrect\n    headers will not necessarily be parsed as you would want.\n\n    This is easier to describe with some examples:\n\n    >>> split_header_words(['foo=\"bar\"; port=\"80,81\"; discard, bar=baz'])\n    [[('foo', 'bar'), ('port', '80,81'), ('discard', None)], [('bar', 'baz')]]\n    >>> split_header_words(['text/html; charset=\"iso-8859-1\"'])\n    [[('text/html', None), ('charset', 'iso-8859-1')]]\n    >>> split_header_words([r'Basic realm=\"\\\"foo\\bar\\\"\"'])\n    [[('Basic', None), ('realm', '\"foobar\"')]]\n\n    ");
      var1.setline(371);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("basestring")).__not__().__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(372);
         PyList var3 = new PyList(Py.EmptyObjects);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(373);
         PyObject var8 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(373);
            PyObject var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(409);
               var8 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var8;
            }

            var1.setlocal(2, var4);
            var1.setline(374);
            PyObject var5 = var1.getlocal(2);
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(375);
            PyList var9 = new PyList(Py.EmptyObjects);
            var1.setlocal(4, var9);
            var5 = null;

            while(true) {
               var1.setline(376);
               if (!var1.getlocal(2).__nonzero__()) {
                  var1.setline(408);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(408);
                     var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
                  }
                  break;
               }

               var1.setline(377);
               var5 = var1.getglobal("HEADER_TOKEN_RE").__getattr__("search").__call__(var2, var1.getlocal(2));
               var1.setlocal(5, var5);
               var5 = null;
               var1.setline(378);
               if (var1.getlocal(5).__nonzero__()) {
                  var1.setline(379);
                  var5 = var1.getglobal("unmatched").__call__(var2, var1.getlocal(5));
                  var1.setlocal(2, var5);
                  var5 = null;
                  var1.setline(380);
                  var5 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setlocal(6, var5);
                  var5 = null;
                  var1.setline(381);
                  var5 = var1.getglobal("HEADER_QUOTED_VALUE_RE").__getattr__("search").__call__(var2, var1.getlocal(2));
                  var1.setlocal(5, var5);
                  var5 = null;
                  var1.setline(382);
                  if (var1.getlocal(5).__nonzero__()) {
                     var1.setline(383);
                     var5 = var1.getglobal("unmatched").__call__(var2, var1.getlocal(5));
                     var1.setlocal(2, var5);
                     var5 = null;
                     var1.setline(384);
                     var5 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(385);
                     var5 = var1.getglobal("HEADER_ESCAPE_RE").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\1"), (PyObject)var1.getlocal(7));
                     var1.setlocal(7, var5);
                     var5 = null;
                  } else {
                     var1.setline(387);
                     var5 = var1.getglobal("HEADER_VALUE_RE").__getattr__("search").__call__(var2, var1.getlocal(2));
                     var1.setlocal(5, var5);
                     var5 = null;
                     var1.setline(388);
                     if (var1.getlocal(5).__nonzero__()) {
                        var1.setline(389);
                        var5 = var1.getglobal("unmatched").__call__(var2, var1.getlocal(5));
                        var1.setlocal(2, var5);
                        var5 = null;
                        var1.setline(390);
                        var5 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                        var1.setlocal(7, var5);
                        var5 = null;
                        var1.setline(391);
                        var5 = var1.getlocal(7).__getattr__("rstrip").__call__(var2);
                        var1.setlocal(7, var5);
                        var5 = null;
                     } else {
                        var1.setline(394);
                        var5 = var1.getglobal("None");
                        var1.setlocal(7, var5);
                        var5 = null;
                     }
                  }

                  var1.setline(395);
                  var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7)})));
               } else {
                  var1.setline(396);
                  if (var1.getlocal(2).__getattr__("lstrip").__call__(var2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__nonzero__()) {
                     var1.setline(398);
                     var5 = var1.getlocal(2).__getattr__("lstrip").__call__(var2).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
                     var1.setlocal(2, var5);
                     var5 = null;
                     var1.setline(399);
                     if (var1.getlocal(4).__nonzero__()) {
                        var1.setline(399);
                        var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
                     }

                     var1.setline(400);
                     var9 = new PyList(Py.EmptyObjects);
                     var1.setlocal(4, var9);
                     var5 = null;
                  } else {
                     var1.setline(403);
                     var5 = var1.getglobal("re").__getattr__("subn").__call__((ThreadState)var2, PyString.fromInterned("^[=\\s;]*"), (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(2));
                     PyObject[] var6 = Py.unpackSequence(var5, 2);
                     PyObject var7 = var6[0];
                     var1.setlocal(8, var7);
                     var7 = null;
                     var7 = var6[1];
                     var1.setlocal(9, var7);
                     var7 = null;
                     var5 = null;
                     var1.setline(404);
                     if (var1.getglobal("__debug__").__nonzero__()) {
                        var5 = var1.getlocal(9);
                        var10000 = var5._gt(Py.newInteger(0));
                        var5 = null;
                        if (!var10000.__nonzero__()) {
                           throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("split_header_words bug: '%s', '%s', %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2), var1.getlocal(4)})));
                        }
                     }

                     var1.setline(407);
                     var5 = var1.getlocal(8);
                     var1.setlocal(2, var5);
                     var5 = null;
                  }
               }
            }
         }
      }
   }

   public PyObject join_header_words$12(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyString.fromInterned("Do the inverse (almost) of the conversion done by split_header_words.\n\n    Takes a list of lists of (key, value) pairs and produces a single header\n    value.  Attribute values are quoted if needed.\n\n    >>> join_header_words([[(\"text/plain\", None), (\"charset\", \"iso-8859/1\")]])\n    'text/plain; charset=\"iso-8859/1\"'\n    >>> join_header_words([[(\"text/plain\", None)], [(\"charset\", \"iso-8859/1\")]])\n    'text/plain, charset=\"iso-8859/1\"'\n\n    ");
      var1.setline(424);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(425);
      PyObject var9 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(425);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(435);
            var9 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(2, var4);
         var1.setline(426);
         PyList var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(427);
         PyObject var10 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(427);
            PyObject var6 = var10.__iternext__();
            if (var6 == null) {
               var1.setline(434);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(434);
                  var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("; ").__getattr__("join").__call__(var2, var1.getlocal(3)));
               }
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(4, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(5, var8);
            var8 = null;
            var1.setline(428);
            PyObject var11 = var1.getlocal(5);
            PyObject var10000 = var11._isnot(var1.getglobal("None"));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(429);
               if (var1.getglobal("re").__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\w+$"), (PyObject)var1.getlocal(5)).__not__().__nonzero__()) {
                  var1.setline(430);
                  var11 = var1.getglobal("HEADER_JOIN_ESCAPE_RE").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\\\\1"), (PyObject)var1.getlocal(5));
                  var1.setlocal(5, var11);
                  var7 = null;
                  var1.setline(431);
                  var11 = PyString.fromInterned("\"%s\"")._mod(var1.getlocal(5));
                  var1.setlocal(5, var11);
                  var7 = null;
               }

               var1.setline(432);
               var11 = PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)}));
               var1.setlocal(4, var11);
               var7 = null;
            }

            var1.setline(433);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject _strip_quotes$13(PyFrame var1, ThreadState var2) {
      var1.setline(438);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"")).__nonzero__()) {
         var1.setline(439);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(440);
      if (var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"")).__nonzero__()) {
         var1.setline(441);
         var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(442);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse_ns_headers$14(PyFrame var1, ThreadState var2) {
      var1.setline(458);
      PyString.fromInterned("Ad-hoc parser for Netscape protocol cookie-attributes.\n\n    The old Netscape cookie format for Set-Cookie can for instance contain\n    an unquoted \",\" in the expires field, so we have to use this ad-hoc\n    parser instead of split_header_words.\n\n    XXX This may not make the best possible effort to parse all the crap\n    that Netscape Cookie headers contain.  Ronald Tschalar's HTTPClient\n    parser is probably better, so could do worse than following that if\n    this ever gives any trouble.\n\n    Currently, this is also used for parsing RFC 2109 cookies.\n\n    ");
      var1.setline(459);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("expires"), PyString.fromInterned("domain"), PyString.fromInterned("path"), PyString.fromInterned("secure"), PyString.fromInterned("version"), PyString.fromInterned("port"), PyString.fromInterned("max-age")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(463);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var10);
      var3 = null;
      var1.setline(464);
      PyObject var11 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(464);
         PyObject var4 = var11.__iternext__();
         if (var4 == null) {
            var1.setline(493);
            var11 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var11;
         }

         var1.setlocal(3, var4);
         var1.setline(465);
         PyList var5 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(466);
         PyObject var12 = var1.getglobal("False");
         var1.setlocal(5, var12);
         var5 = null;
         var1.setline(467);
         var12 = var1.getglobal("enumerate").__call__(var2, var1.getglobal("re").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";\\s*"), (PyObject)var1.getlocal(3))).__iter__();

         while(true) {
            var1.setline(467);
            PyObject var6 = var12.__iternext__();
            if (var6 == null) {
               var1.setline(488);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(489);
                  if (var1.getlocal(5).__not__().__nonzero__()) {
                     var1.setline(490);
                     var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("version"), PyString.fromInterned("0")})));
                  }

                  var1.setline(491);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
               }
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 2);
            PyObject var8 = var7[0];
            var1.setlocal(6, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(7, var8);
            var8 = null;
            var1.setline(468);
            PyObject var13 = var1.getlocal(7).__getattr__("rstrip").__call__(var2);
            var1.setlocal(7, var13);
            var7 = null;
            var1.setline(469);
            var13 = var1.getlocal(7);
            PyObject var10000 = var13._eq(PyString.fromInterned(""));
            var7 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(470);
               PyString var14 = PyString.fromInterned("=");
               var10000 = var14._notin(var1.getlocal(7));
               var7 = null;
               PyObject var9;
               PyObject[] var16;
               if (var10000.__nonzero__()) {
                  var1.setline(471);
                  PyTuple var15 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getglobal("None")});
                  var16 = Py.unpackSequence(var15, 2);
                  var9 = var16[0];
                  var1.setlocal(8, var9);
                  var9 = null;
                  var9 = var16[1];
                  var1.setlocal(9, var9);
                  var9 = null;
                  var7 = null;
               } else {
                  var1.setline(473);
                  var13 = var1.getglobal("re").__getattr__("split").__call__((ThreadState)var2, PyString.fromInterned("\\s*=\\s*"), (PyObject)var1.getlocal(7), (PyObject)Py.newInteger(1));
                  var16 = Py.unpackSequence(var13, 2);
                  var9 = var16[0];
                  var1.setlocal(8, var9);
                  var9 = null;
                  var9 = var16[1];
                  var1.setlocal(9, var9);
                  var9 = null;
                  var7 = null;
                  var1.setline(474);
                  var13 = var1.getlocal(8).__getattr__("lstrip").__call__(var2);
                  var1.setlocal(8, var13);
                  var7 = null;
               }

               var1.setline(475);
               var13 = var1.getlocal(6);
               var10000 = var13._ne(Py.newInteger(0));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(476);
                  var13 = var1.getlocal(8).__getattr__("lower").__call__(var2);
                  var1.setlocal(10, var13);
                  var7 = null;
                  var1.setline(477);
                  var13 = var1.getlocal(10);
                  var10000 = var13._in(var1.getlocal(1));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(478);
                     var13 = var1.getlocal(10);
                     var1.setlocal(8, var13);
                     var7 = null;
                  }

                  var1.setline(479);
                  var13 = var1.getlocal(8);
                  var10000 = var13._eq(PyString.fromInterned("version"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(481);
                     var13 = var1.getglobal("_strip_quotes").__call__(var2, var1.getlocal(9));
                     var1.setlocal(9, var13);
                     var7 = null;
                     var1.setline(482);
                     var13 = var1.getglobal("True");
                     var1.setlocal(5, var13);
                     var7 = null;
                  }

                  var1.setline(483);
                  var13 = var1.getlocal(8);
                  var10000 = var13._eq(PyString.fromInterned("expires"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(485);
                     var13 = var1.getglobal("http2time").__call__(var2, var1.getglobal("_strip_quotes").__call__(var2, var1.getlocal(9)));
                     var1.setlocal(9, var13);
                     var7 = null;
                  }
               }

               var1.setline(486);
               var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9)})));
            }
         }
      }
   }

   public PyObject is_HDN$15(PyFrame var1, ThreadState var2) {
      var1.setline(498);
      PyString.fromInterned("Return True if text is a host domain name.");
      var1.setline(504);
      PyObject var3;
      if (var1.getglobal("IPV4_RE").__getattr__("search").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(505);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(506);
         PyObject var4 = var1.getlocal(0);
         PyObject var10000 = var4._eq(PyString.fromInterned(""));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(507);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(508);
            var4 = var1.getlocal(0).__getitem__(Py.newInteger(0));
            var10000 = var4._eq(PyString.fromInterned("."));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
               var10000 = var4._eq(PyString.fromInterned("."));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(509);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(510);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject domain_match$16(PyFrame var1, ThreadState var2) {
      var1.setline(532);
      PyString.fromInterned("Return True if domain A domain-matches domain B, according to RFC 2965.\n\n    A and B may be host domain names or IP addresses.\n\n    RFC 2965, section 1:\n\n    Host names can be specified either as an IP address or a HDN string.\n    Sometimes we compare one host name with another.  (Such comparisons SHALL\n    be case-insensitive.)  Host A's name domain-matches host B's if\n\n         *  their host name strings string-compare equal; or\n\n         * A is a HDN string and has the form NB, where N is a non-empty\n            name string, B has the form .B', and B' is a HDN string.  (So,\n            x.y.com domain-matches .Y.com but not Y.com.)\n\n    Note that domain-match is not a commutative operation: a.b.c.com\n    domain-matches .c.com, but not the reverse.\n\n    ");
      var1.setline(535);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(536);
      var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(537);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(538);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(539);
         if (var1.getglobal("is_HDN").__call__(var2, var1.getlocal(0)).__not__().__nonzero__()) {
            var1.setline(540);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(541);
            PyObject var4 = var1.getlocal(0).__getattr__("rfind").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(542);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(Py.newInteger(-1));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(2);
               var10000 = var4._eq(Py.newInteger(0));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(544);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(545);
               if (var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__not__().__nonzero__()) {
                  var1.setline(546);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(547);
                  if (var1.getglobal("is_HDN").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null)).__not__().__nonzero__()) {
                     var1.setline(548);
                     var3 = var1.getglobal("False");
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(549);
                     var3 = var1.getglobal("True");
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      }
   }

   public PyObject liberal_is_HDN$17(PyFrame var1, ThreadState var2) {
      var1.setline(556);
      PyString.fromInterned("Return True if text is a sort-of-like a host domain name.\n\n    For accepting/blocking domains.\n\n    ");
      var1.setline(557);
      PyObject var3;
      if (var1.getglobal("IPV4_RE").__getattr__("search").__call__(var2, var1.getlocal(0)).__nonzero__()) {
         var1.setline(558);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(559);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject user_domain_match$18(PyFrame var1, ThreadState var2) {
      var1.setline(566);
      PyString.fromInterned("For blocking/accepting domains.\n\n    A and B may be host domain names or IP addresses.\n\n    ");
      var1.setline(567);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(568);
      var3 = var1.getlocal(1).__getattr__("lower").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(569);
      PyObject var10000 = var1.getglobal("liberal_is_HDN").__call__(var2, var1.getlocal(0));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("liberal_is_HDN").__call__(var2, var1.getlocal(1));
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(570);
         var3 = var1.getlocal(0);
         var10000 = var3._eq(var1.getlocal(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(572);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(573);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(574);
         PyObject var4 = var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(575);
         var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("endswith").__call__(var2, var1.getlocal(1));
         }

         if (var10000.__nonzero__()) {
            var1.setline(576);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(577);
            var10000 = var1.getlocal(2).__not__();
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(0);
               var10000 = var4._eq(var1.getlocal(1));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(578);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(579);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject request_host$19(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyString.fromInterned("Return request-host, as defined by RFC 2965.\n\n    Variation from RFC: returned value is lowercased, for convenient\n    comparison.\n\n    ");
      var1.setline(589);
      PyObject var3 = var1.getlocal(0).__getattr__("get_full_url").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(590);
      var3 = var1.getglobal("urlparse").__getattr__("urlparse").__call__(var2, var1.getlocal(1)).__getitem__(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(591);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(592);
         var3 = var1.getlocal(0).__getattr__("get_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Host"), (PyObject)PyString.fromInterned(""));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(595);
      var3 = var1.getglobal("cut_port_re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(596);
      var3 = var1.getlocal(2).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject eff_request_host$20(PyFrame var1, ThreadState var2) {
      var1.setline(603);
      PyString.fromInterned("Return a tuple (request-host, effective request-host name).\n\n    As defined by RFC 2965, except both are lowercased.\n\n    ");
      var1.setline(604);
      PyObject var3 = var1.getglobal("request_host").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var1.setlocal(2, var3);
      var1.setline(605);
      var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("IPV4_RE").__getattr__("search").__call__(var2, var1.getlocal(2)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(606);
         var3 = var1.getlocal(2)._add(PyString.fromInterned(".local"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(607);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject request_path$21(PyFrame var1, ThreadState var2) {
      var1.setline(610);
      PyString.fromInterned("Path component of request-URI, as defined by RFC 2965.");
      var1.setline(611);
      PyObject var3 = var1.getlocal(0).__getattr__("get_full_url").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(612);
      var3 = var1.getglobal("urlparse").__getattr__("urlsplit").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(613);
      var3 = var1.getglobal("escape_path").__call__(var2, var1.getlocal(2).__getattr__("path"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(614);
      if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")).__not__().__nonzero__()) {
         var1.setline(616);
         var3 = PyString.fromInterned("/")._add(var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(617);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject request_port$22(PyFrame var1, ThreadState var2) {
      var1.setline(620);
      PyObject var3 = var1.getlocal(0).__getattr__("get_host").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(621);
      var3 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(622);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(623);
         var3 = var1.getlocal(1).__getslice__(var1.getlocal(2)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;

         try {
            var1.setline(625);
            var1.getglobal("int").__call__(var2, var1.getlocal(3));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("ValueError"))) {
               var1.setline(627);
               var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("nonnumeric port: '%s'"), (PyObject)var1.getlocal(3));
               var1.setline(628);
               var4 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var4;
            }

            throw var6;
         }
      } else {
         var1.setline(630);
         var3 = var1.getglobal("DEFAULT_HTTP_PORT");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(631);
      var4 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject uppercase_escaped_char$23(PyFrame var1, ThreadState var2) {
      var1.setline(638);
      PyObject var3 = PyString.fromInterned("%%%s")._mod(var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("upper").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject escape_path$24(PyFrame var1, ThreadState var2) {
      var1.setline(640);
      PyString.fromInterned("Escape any invalid characters in HTTP URL, and uppercase all escapes.");
      var1.setline(649);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
         var1.setline(650);
         var3 = var1.getlocal(0).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(651);
      var3 = var1.getglobal("urllib").__getattr__("quote").__call__(var2, var1.getlocal(0), var1.getglobal("HTTP_PATH_SAFE"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(652);
      var3 = var1.getglobal("ESCAPED_CHAR_RE").__getattr__("sub").__call__(var2, var1.getglobal("uppercase_escaped_char"), var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(653);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject reach$25(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyString.fromInterned("Return reach of host h, as defined by RFC 2965, section 1.\n\n    The reach R of a host name H is defined as follows:\n\n       *  If\n\n          -  H is the host domain name of a host; and,\n\n          -  H has the form A.B; and\n\n          -  A has no embedded (that is, interior) dots; and\n\n          -  B has at least one embedded dot, or B is the string \"local\".\n             then the reach of H is .B.\n\n       *  Otherwise, the reach of H is H.\n\n    >>> reach(\"www.acme.com\")\n    '.acme.com'\n    >>> reach(\"acme.com\")\n    'acme.com'\n    >>> reach(\"acme.local\")\n    '.local'\n\n    ");
      var1.setline(681);
      PyObject var3 = var1.getlocal(0).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(682);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(684);
         var3 = var1.getlocal(0).__getslice__(var1.getlocal(1)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(685);
         var3 = var1.getlocal(2).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(686);
         var10000 = var1.getglobal("is_HDN").__call__(var2, var1.getlocal(0));
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._ge(Py.newInteger(0));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(2);
               var10000 = var3._eq(PyString.fromInterned("local"));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(687);
            var3 = PyString.fromInterned(".")._add(var1.getlocal(2));
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(688);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_third_party$26(PyFrame var1, ThreadState var2) {
      var1.setline(699);
      PyString.fromInterned("\n\n    RFC 2965, section 3.3.6:\n\n        An unverifiable transaction is to a third-party host if its request-\n        host U does not domain-match the reach R of the request-host O in the\n        origin transaction.\n\n    ");
      var1.setline(700);
      PyObject var3 = var1.getglobal("request_host").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(701);
      if (var1.getglobal("domain_match").__call__(var2, var1.getlocal(1), var1.getglobal("reach").__call__(var2, var1.getlocal(0).__getattr__("get_origin_req_host").__call__(var2))).__not__().__nonzero__()) {
         var1.setline(702);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(704);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject Cookie$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("HTTP Cookie.\n\n    This class represents both Netscape and RFC 2965 cookies.\n\n    This is deliberately a very simple class.  It just holds attributes.  It's\n    possible to construct Cookie instances that don't comply with the cookie\n    standards.  CookieJar.make_cookies is the factory function for Cookie\n    objects -- it deals with cookie parsing, supplying defaults, and\n    normalising to the representation used in this class.  CookiePolicy is\n    responsible for checking them to see whether they should be accepted from\n    and returned to the server.\n\n    Note that the port may be present in the headers, but unspecified (\"Port\"\n    rather than\"Port=80\", for example); if this is the case, port is None.\n\n    "));
      var1.setline(723);
      PyString.fromInterned("HTTP Cookie.\n\n    This class represents both Netscape and RFC 2965 cookies.\n\n    This is deliberately a very simple class.  It just holds attributes.  It's\n    possible to construct Cookie instances that don't comply with the cookie\n    standards.  CookieJar.make_cookies is the factory function for Cookie\n    objects -- it deals with cookie parsing, supplying defaults, and\n    normalising to the representation used in this class.  CookiePolicy is\n    responsible for checking them to see whether they should be accepted from\n    and returned to the server.\n\n    Note that the port may be present in the headers, but unspecified (\"Port\"\n    rather than\"Port=80\", for example); if this is the case, port is None.\n\n    ");
      var1.setline(725);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$28, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(767);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, has_nonstandard_attr$29, (PyObject)null);
      var1.setlocal("has_nonstandard_attr", var4);
      var3 = null;
      var1.setline(769);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, get_nonstandard_attr$30, (PyObject)null);
      var1.setlocal("get_nonstandard_attr", var4);
      var3 = null;
      var1.setline(771);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_nonstandard_attr$31, (PyObject)null);
      var1.setlocal("set_nonstandard_attr", var4);
      var3 = null;
      var1.setline(774);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, is_expired$32, (PyObject)null);
      var1.setlocal("is_expired", var4);
      var3 = null;
      var1.setline(780);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$33, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(790);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$34, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$28(PyFrame var1, ThreadState var2) {
      var1.setline(738);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(738);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(739);
      var3 = var1.getlocal(12);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(739);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(12));
         var1.setlocal(12, var3);
         var3 = null;
      }

      var1.setline(740);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(5);
         var10000 = var3._is(var1.getglobal("True"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(741);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("if port is None, port_specified must be false")));
      } else {
         var1.setline(743);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("version", var3);
         var3 = null;
         var1.setline(744);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("name", var3);
         var3 = null;
         var1.setline(745);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("value", var3);
         var3 = null;
         var1.setline(746);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("port", var3);
         var3 = null;
         var1.setline(747);
         var3 = var1.getlocal(5);
         var1.getlocal(0).__setattr__("port_specified", var3);
         var3 = null;
         var1.setline(749);
         var3 = var1.getlocal(6).__getattr__("lower").__call__(var2);
         var1.getlocal(0).__setattr__("domain", var3);
         var3 = null;
         var1.setline(750);
         var3 = var1.getlocal(7);
         var1.getlocal(0).__setattr__("domain_specified", var3);
         var3 = null;
         var1.setline(755);
         var3 = var1.getlocal(8);
         var1.getlocal(0).__setattr__("domain_initial_dot", var3);
         var3 = null;
         var1.setline(756);
         var3 = var1.getlocal(9);
         var1.getlocal(0).__setattr__("path", var3);
         var3 = null;
         var1.setline(757);
         var3 = var1.getlocal(10);
         var1.getlocal(0).__setattr__("path_specified", var3);
         var3 = null;
         var1.setline(758);
         var3 = var1.getlocal(11);
         var1.getlocal(0).__setattr__("secure", var3);
         var3 = null;
         var1.setline(759);
         var3 = var1.getlocal(12);
         var1.getlocal(0).__setattr__("expires", var3);
         var3 = null;
         var1.setline(760);
         var3 = var1.getlocal(13);
         var1.getlocal(0).__setattr__("discard", var3);
         var3 = null;
         var1.setline(761);
         var3 = var1.getlocal(14);
         var1.getlocal(0).__setattr__("comment", var3);
         var3 = null;
         var1.setline(762);
         var3 = var1.getlocal(15);
         var1.getlocal(0).__setattr__("comment_url", var3);
         var3 = null;
         var1.setline(763);
         var3 = var1.getlocal(17);
         var1.getlocal(0).__setattr__("rfc2109", var3);
         var3 = null;
         var1.setline(765);
         var3 = var1.getglobal("copy").__getattr__("copy").__call__(var2, var1.getlocal(16));
         var1.getlocal(0).__setattr__("_rest", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject has_nonstandard_attr$29(PyFrame var1, ThreadState var2) {
      var1.setline(768);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_rest"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_nonstandard_attr$30(PyFrame var1, ThreadState var2) {
      var1.setline(770);
      PyObject var3 = var1.getlocal(0).__getattr__("_rest").__getattr__("get").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_nonstandard_attr$31(PyFrame var1, ThreadState var2) {
      var1.setline(772);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("_rest").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_expired$32(PyFrame var1, ThreadState var2) {
      var1.setline(775);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(775);
         var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(776);
      var3 = var1.getlocal(0).__getattr__("expires");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("expires");
         var10000 = var3._le(var1.getlocal(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(777);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(778);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __str__$33(PyFrame var1, ThreadState var2) {
      var1.setline(781);
      PyObject var3 = var1.getlocal(0).__getattr__("port");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(781);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(1, var4);
         var3 = null;
      } else {
         var1.setline(782);
         var3 = PyString.fromInterned(":")._add(var1.getlocal(0).__getattr__("port"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(783);
      var3 = var1.getlocal(0).__getattr__("domain")._add(var1.getlocal(1))._add(var1.getlocal(0).__getattr__("path"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(784);
      var3 = var1.getlocal(0).__getattr__("value");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(785);
         var3 = PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getlocal(0).__getattr__("value")}));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(787);
         var3 = var1.getlocal(0).__getattr__("name");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(788);
      var3 = PyString.fromInterned("<Cookie %s for %s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$34(PyFrame var1, ThreadState var2) {
      var1.setline(791);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(792);
      PyObject var6 = (new PyTuple(new PyObject[]{PyString.fromInterned("version"), PyString.fromInterned("name"), PyString.fromInterned("value"), PyString.fromInterned("port"), PyString.fromInterned("port_specified"), PyString.fromInterned("domain"), PyString.fromInterned("domain_specified"), PyString.fromInterned("domain_initial_dot"), PyString.fromInterned("path"), PyString.fromInterned("path_specified"), PyString.fromInterned("secure"), PyString.fromInterned("expires"), PyString.fromInterned("discard"), PyString.fromInterned("comment"), PyString.fromInterned("comment_url")})).__iter__();

      while(true) {
         var1.setline(792);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(800);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("rest=%s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("_rest"))));
            var1.setline(801);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("rfc2109=%s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(0).__getattr__("rfc2109"))));
            var1.setline(802);
            var6 = PyString.fromInterned("Cookie(%s)")._mod(PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1)));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(798);
         PyObject var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(799);
         var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("repr").__call__(var2, var1.getlocal(3))})));
      }
   }

   public PyObject CookiePolicy$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Defines which cookies get accepted from and returned to server.\n\n    May also modify cookies, though this is probably a bad idea.\n\n    The subclass DefaultCookiePolicy defines the standard rules for Netscape\n    and RFC 2965 cookies -- override that if you want a customised policy.\n\n    "));
      var1.setline(813);
      PyString.fromInterned("Defines which cookies get accepted from and returned to server.\n\n    May also modify cookies, though this is probably a bad idea.\n\n    The subclass DefaultCookiePolicy defines the standard rules for Netscape\n    and RFC 2965 cookies -- override that if you want a customised policy.\n\n    ");
      var1.setline(814);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, set_ok$36, PyString.fromInterned("Return true if (and only if) cookie should be accepted from server.\n\n        Currently, pre-expired cookies never get this far -- the CookieJar\n        class deletes such cookies itself.\n\n        "));
      var1.setlocal("set_ok", var4);
      var3 = null;
      var1.setline(823);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, return_ok$37, PyString.fromInterned("Return true if (and only if) cookie should be returned to server."));
      var1.setlocal("return_ok", var4);
      var3 = null;
      var1.setline(827);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, domain_return_ok$38, PyString.fromInterned("Return false if cookies should not be returned, given cookie domain.\n        "));
      var1.setlocal("domain_return_ok", var4);
      var3 = null;
      var1.setline(832);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, path_return_ok$39, PyString.fromInterned("Return false if cookies should not be returned, given cookie path.\n        "));
      var1.setlocal("path_return_ok", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject set_ok$36(PyFrame var1, ThreadState var2) {
      var1.setline(820);
      PyString.fromInterned("Return true if (and only if) cookie should be accepted from server.\n\n        Currently, pre-expired cookies never get this far -- the CookieJar\n        class deletes such cookies itself.\n\n        ");
      var1.setline(821);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2));
   }

   public PyObject return_ok$37(PyFrame var1, ThreadState var2) {
      var1.setline(824);
      PyString.fromInterned("Return true if (and only if) cookie should be returned to server.");
      var1.setline(825);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2));
   }

   public PyObject domain_return_ok$38(PyFrame var1, ThreadState var2) {
      var1.setline(829);
      PyString.fromInterned("Return false if cookies should not be returned, given cookie domain.\n        ");
      var1.setline(830);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject path_return_ok$39(PyFrame var1, ThreadState var2) {
      var1.setline(834);
      PyString.fromInterned("Return false if cookies should not be returned, given cookie path.\n        ");
      var1.setline(835);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DefaultCookiePolicy$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Implements the standard rules for accepting and returning cookies."));
      var1.setline(839);
      PyString.fromInterned("Implements the standard rules for accepting and returning cookies.");
      var1.setline(841);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("DomainStrictNoDots", var3);
      var3 = null;
      var1.setline(842);
      var3 = Py.newInteger(2);
      var1.setlocal("DomainStrictNonDomain", var3);
      var3 = null;
      var1.setline(843);
      var3 = Py.newInteger(4);
      var1.setlocal("DomainRFC2965Match", var3);
      var3 = null;
      var1.setline(845);
      var3 = Py.newInteger(0);
      var1.setlocal("DomainLiberal", var3);
      var3 = null;
      var1.setline(846);
      PyObject var4 = var1.getname("DomainStrictNoDots")._or(var1.getname("DomainStrictNonDomain"));
      var1.setlocal("DomainStrict", var4);
      var3 = null;
      var1.setline(848);
      PyObject[] var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("True"), var1.getname("False"), var1.getname("None"), var1.getname("False"), var1.getname("False"), var1.getname("True"), var1.getname("False"), var1.getname("DomainLiberal"), var1.getname("False"), var1.getname("False")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$41, PyString.fromInterned("Constructor arguments should be passed as keyword arguments only."));
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(881);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, blocked_domains$42, PyString.fromInterned("Return the sequence of blocked domains (as a tuple)."));
      var1.setlocal("blocked_domains", var6);
      var3 = null;
      var1.setline(884);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_blocked_domains$43, PyString.fromInterned("Set the sequence of blocked domains."));
      var1.setlocal("set_blocked_domains", var6);
      var3 = null;
      var1.setline(888);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_blocked$44, (PyObject)null);
      var1.setlocal("is_blocked", var6);
      var3 = null;
      var1.setline(894);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, allowed_domains$45, PyString.fromInterned("Return None, or the sequence of allowed domains (as a tuple)."));
      var1.setlocal("allowed_domains", var6);
      var3 = null;
      var1.setline(897);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_allowed_domains$46, PyString.fromInterned("Set the sequence of allowed domains, or None."));
      var1.setlocal("set_allowed_domains", var6);
      var3 = null;
      var1.setline(903);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, is_not_allowed$47, (PyObject)null);
      var1.setlocal("is_not_allowed", var6);
      var3 = null;
      var1.setline(911);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_ok$48, PyString.fromInterned("\n        If you override .set_ok(), be sure to call this method.  If it returns\n        false, so should your subclass (assuming your subclass wants to be more\n        strict about which cookies to accept).\n\n        "));
      var1.setlocal("set_ok", var6);
      var3 = null;
      var1.setline(930);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_ok_version$49, (PyObject)null);
      var1.setlocal("set_ok_version", var6);
      var3 = null;
      var1.setline(945);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_ok_verifiability$50, (PyObject)null);
      var1.setlocal("set_ok_verifiability", var6);
      var3 = null;
      var1.setline(957);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_ok_name$51, (PyObject)null);
      var1.setlocal("set_ok_name", var6);
      var3 = null;
      var1.setline(966);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_ok_path$52, (PyObject)null);
      var1.setlocal("set_ok_path", var6);
      var3 = null;
      var1.setline(977);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_ok_domain$53, (PyObject)null);
      var1.setlocal("set_ok_domain", var6);
      var3 = null;
      var1.setline(1036);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_ok_port$54, (PyObject)null);
      var1.setlocal("set_ok_port", var6);
      var3 = null;
      var1.setline(1057);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, return_ok$55, PyString.fromInterned("\n        If you override .return_ok(), be sure to call this method.  If it\n        returns false, so should your subclass (assuming your subclass wants to\n        be more strict about which cookies to return).\n\n        "));
      var1.setlocal("return_ok", var6);
      var3 = null;
      var1.setline(1075);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, return_ok_version$56, (PyObject)null);
      var1.setlocal("return_ok_version", var6);
      var3 = null;
      var1.setline(1084);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, return_ok_verifiability$57, (PyObject)null);
      var1.setlocal("return_ok_verifiability", var6);
      var3 = null;
      var1.setline(1096);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, return_ok_secure$58, (PyObject)null);
      var1.setlocal("return_ok_secure", var6);
      var3 = null;
      var1.setline(1102);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, return_ok_expires$59, (PyObject)null);
      var1.setlocal("return_ok_expires", var6);
      var3 = null;
      var1.setline(1108);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, return_ok_port$60, (PyObject)null);
      var1.setlocal("return_ok_port", var6);
      var3 = null;
      var1.setline(1122);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, return_ok_domain$61, (PyObject)null);
      var1.setlocal("return_ok_domain", var6);
      var3 = null;
      var1.setline(1144);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, domain_return_ok$62, (PyObject)null);
      var1.setlocal("domain_return_ok", var6);
      var3 = null;
      var1.setline(1166);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, path_return_ok$63, (PyObject)null);
      var1.setlocal("path_return_ok", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$41(PyFrame var1, ThreadState var2) {
      var1.setline(860);
      PyString.fromInterned("Constructor arguments should be passed as keyword arguments only.");
      var1.setline(861);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("netscape", var3);
      var3 = null;
      var1.setline(862);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("rfc2965", var3);
      var3 = null;
      var1.setline(863);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("rfc2109_as_netscape", var3);
      var3 = null;
      var1.setline(864);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("hide_cookie2", var3);
      var3 = null;
      var1.setline(865);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("strict_domain", var3);
      var3 = null;
      var1.setline(866);
      var3 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("strict_rfc2965_unverifiable", var3);
      var3 = null;
      var1.setline(867);
      var3 = var1.getlocal(9);
      var1.getlocal(0).__setattr__("strict_ns_unverifiable", var3);
      var3 = null;
      var1.setline(868);
      var3 = var1.getlocal(10);
      var1.getlocal(0).__setattr__("strict_ns_domain", var3);
      var3 = null;
      var1.setline(869);
      var3 = var1.getlocal(11);
      var1.getlocal(0).__setattr__("strict_ns_set_initial_dollar", var3);
      var3 = null;
      var1.setline(870);
      var3 = var1.getlocal(12);
      var1.getlocal(0).__setattr__("strict_ns_set_path", var3);
      var3 = null;
      var1.setline(872);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(873);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("_blocked_domains", var3);
         var3 = null;
      } else {
         var1.setline(875);
         PyTuple var4 = new PyTuple(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"_blocked_domains", var4);
         var3 = null;
      }

      var1.setline(877);
      var3 = var1.getlocal(2);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(878);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(879);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_allowed_domains", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject blocked_domains$42(PyFrame var1, ThreadState var2) {
      var1.setline(882);
      PyString.fromInterned("Return the sequence of blocked domains (as a tuple).");
      var1.setline(883);
      PyObject var3 = var1.getlocal(0).__getattr__("_blocked_domains");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_blocked_domains$43(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      PyString.fromInterned("Set the sequence of blocked domains.");
      var1.setline(886);
      PyObject var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("_blocked_domains", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_blocked$44(PyFrame var1, ThreadState var2) {
      var1.setline(889);
      PyObject var3 = var1.getlocal(0).__getattr__("_blocked_domains").__iter__();

      PyObject var5;
      do {
         var1.setline(889);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(892);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(890);
      } while(!var1.getglobal("user_domain_match").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__nonzero__());

      var1.setline(891);
      var5 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject allowed_domains$45(PyFrame var1, ThreadState var2) {
      var1.setline(895);
      PyString.fromInterned("Return None, or the sequence of allowed domains (as a tuple).");
      var1.setline(896);
      PyObject var3 = var1.getlocal(0).__getattr__("_allowed_domains");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_allowed_domains$46(PyFrame var1, ThreadState var2) {
      var1.setline(898);
      PyString.fromInterned("Set the sequence of allowed domains, or None.");
      var1.setline(899);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(900);
         var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(901);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_allowed_domains", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject is_not_allowed$47(PyFrame var1, ThreadState var2) {
      var1.setline(904);
      PyObject var3 = var1.getlocal(0).__getattr__("_allowed_domains");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(905);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(906);
         PyObject var4 = var1.getlocal(0).__getattr__("_allowed_domains").__iter__();

         do {
            var1.setline(906);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(909);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(2, var5);
            var1.setline(907);
         } while(!var1.getglobal("user_domain_match").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__nonzero__());

         var1.setline(908);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject set_ok$48(PyFrame var1, ThreadState var2) {
      var1.setline(917);
      PyString.fromInterned("\n        If you override .set_ok(), be sure to call this method.  If it returns\n        false, so should your subclass (assuming your subclass wants to be more\n        strict about which cookies to accept).\n\n        ");
      var1.setline(918);
      var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned(" - checking cookie %s=%s"), (PyObject)var1.getlocal(1).__getattr__("name"), (PyObject)var1.getlocal(1).__getattr__("value"));
      var1.setline(920);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(1).__getattr__("name");
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(922);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("version"), PyString.fromInterned("verifiability"), PyString.fromInterned("name"), PyString.fromInterned("path"), PyString.fromInterned("domain"), PyString.fromInterned("port")})).__iter__();

      PyObject var5;
      do {
         var1.setline(922);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(928);
            var5 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(923);
         var5 = PyString.fromInterned("set_ok_")._add(var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(924);
         var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(925);
      } while(!var1.getlocal(5).__call__(var2, var1.getlocal(1), var1.getlocal(2)).__not__().__nonzero__());

      var1.setline(926);
      var5 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject set_ok_version$49(PyFrame var1, ThreadState var2) {
      var1.setline(931);
      PyObject var3 = var1.getlocal(1).__getattr__("version");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(934);
         var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("   Set-Cookie2 without version attribute (%s=%s)"), (PyObject)var1.getlocal(1).__getattr__("name"), (PyObject)var1.getlocal(1).__getattr__("value"));
         var1.setline(936);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(937);
         PyObject var4 = var1.getlocal(1).__getattr__("version");
         var10000 = var4._gt(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("rfc2965").__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(938);
            var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   RFC 2965 cookies are switched off"));
            var1.setline(939);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(940);
            var4 = var1.getlocal(1).__getattr__("version");
            var10000 = var4._eq(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("netscape").__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(941);
               var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   Netscape cookies are switched off"));
               var1.setline(942);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(943);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject set_ok_verifiability$50(PyFrame var1, ThreadState var2) {
      var1.setline(946);
      PyObject var10000 = var1.getlocal(2).__getattr__("is_unverifiable").__call__(var2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("is_third_party").__call__(var2, var1.getlocal(2));
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(947);
         var3 = var1.getlocal(1).__getattr__("version");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("strict_rfc2965_unverifiable");
         }

         if (var10000.__nonzero__()) {
            var1.setline(948);
            var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   third-party RFC 2965 cookie during unverifiable transaction"));
            var1.setline(950);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(951);
         PyObject var4 = var1.getlocal(1).__getattr__("version");
         var10000 = var4._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("strict_ns_unverifiable");
         }

         if (var10000.__nonzero__()) {
            var1.setline(952);
            var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   third-party Netscape cookie during unverifiable transaction"));
            var1.setline(954);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(955);
      var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_ok_name$51(PyFrame var1, ThreadState var2) {
      var1.setline(960);
      PyObject var3 = var1.getlocal(1).__getattr__("version");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("strict_ns_set_initial_dollar");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("name").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$"));
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(962);
         var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   illegal name (starts with '$'): '%s'"), (PyObject)var1.getlocal(1).__getattr__("name"));
         var1.setline(963);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(964);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject set_ok_path$52(PyFrame var1, ThreadState var2) {
      var1.setline(967);
      PyObject var3;
      if (var1.getlocal(1).__getattr__("path_specified").__nonzero__()) {
         var1.setline(968);
         var3 = var1.getglobal("request_path").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(969);
         var3 = var1.getlocal(1).__getattr__("version");
         PyObject var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getattr__("version");
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("strict_ns_set_path");
            }
         }

         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__getattr__("startswith").__call__(var2, var1.getlocal(1).__getattr__("path")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(972);
            var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("   path attribute %s is not a prefix of request path %s"), (PyObject)var1.getlocal(1).__getattr__("path"), (PyObject)var1.getlocal(3));
            var1.setline(974);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(975);
      var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_ok_domain$53(PyFrame var1, ThreadState var2) {
      var1.setline(978);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("is_blocked").__call__(var2, var1.getlocal(1).__getattr__("domain")).__nonzero__()) {
         var1.setline(979);
         var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   domain %s is in user block-list"), (PyObject)var1.getlocal(1).__getattr__("domain"));
         var1.setline(980);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(981);
         if (var1.getlocal(0).__getattr__("is_not_allowed").__call__(var2, var1.getlocal(1).__getattr__("domain")).__nonzero__()) {
            var1.setline(982);
            var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   domain %s is not in user allow-list"), (PyObject)var1.getlocal(1).__getattr__("domain"));
            var1.setline(983);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(984);
            if (var1.getlocal(1).__getattr__("domain_specified").__nonzero__()) {
               var1.setline(985);
               PyObject var4 = var1.getglobal("eff_request_host").__call__(var2, var1.getlocal(2));
               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var4 = null;
               var1.setline(986);
               var4 = var1.getlocal(1).__getattr__("domain");
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(987);
               PyObject var10000 = var1.getlocal(0).__getattr__("strict_domain");
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(5).__getattr__("count").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                  var10000 = var4._ge(Py.newInteger(2));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(991);
                  var4 = var1.getlocal(5).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                  var1.setlocal(6, var4);
                  var4 = null;
                  var1.setline(992);
                  var4 = var1.getlocal(5).__getattr__("rfind").__call__((ThreadState)var2, PyString.fromInterned("."), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(6));
                  var1.setlocal(7, var4);
                  var4 = null;
                  var1.setline(993);
                  var4 = var1.getlocal(7);
                  var10000 = var4._eq(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(994);
                     var4 = var1.getlocal(5).__getslice__(var1.getlocal(6)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
                     var1.setlocal(8, var4);
                     var4 = null;
                     var1.setline(995);
                     var4 = var1.getlocal(5).__getslice__(var1.getlocal(7)._add(Py.newInteger(1)), var1.getlocal(6), (PyObject)null);
                     var1.setlocal(9, var4);
                     var4 = null;
                     var1.setline(996);
                     var4 = var1.getlocal(9).__getattr__("lower").__call__(var2);
                     var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("co"), PyString.fromInterned("ac"), PyString.fromInterned("com"), PyString.fromInterned("edu"), PyString.fromInterned("org"), PyString.fromInterned("net"), PyString.fromInterned("gov"), PyString.fromInterned("mil"), PyString.fromInterned("int"), PyString.fromInterned("aero"), PyString.fromInterned("biz"), PyString.fromInterned("cat"), PyString.fromInterned("coop"), PyString.fromInterned("info"), PyString.fromInterned("jobs"), PyString.fromInterned("mobi"), PyString.fromInterned("museum"), PyString.fromInterned("name"), PyString.fromInterned("pro"), PyString.fromInterned("travel"), PyString.fromInterned("eu")}));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(8));
                        var10000 = var4._eq(Py.newInteger(2));
                        var4 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(1001);
                        var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   country-code second level domain %s"), (PyObject)var1.getlocal(5));
                        var1.setline(1002);
                        var3 = var1.getglobal("False");
                        var1.f_lasti = -1;
                        return var3;
                     }
                  }
               }

               var1.setline(1003);
               if (var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__nonzero__()) {
                  var1.setline(1004);
                  var4 = var1.getlocal(5).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
                  var1.setlocal(10, var4);
                  var4 = null;
               } else {
                  var1.setline(1006);
                  var4 = var1.getlocal(5);
                  var1.setlocal(10, var4);
                  var4 = null;
               }

               var1.setline(1007);
               var4 = var1.getlocal(10).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
               var10000 = var4._ge(Py.newInteger(0));
               var4 = null;
               var4 = var10000;
               var1.setlocal(11, var4);
               var4 = null;
               var1.setline(1008);
               var10000 = var1.getlocal(11).__not__();
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(5);
                  var10000 = var4._ne(PyString.fromInterned(".local"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1009);
                  var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   non-local domain %s contains no embedded dot"), (PyObject)var1.getlocal(5));
                  var1.setline(1011);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(1012);
               var4 = var1.getlocal(1).__getattr__("version");
               var10000 = var4._eq(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1013);
                  var10000 = var1.getlocal(4).__getattr__("endswith").__call__(var2, var1.getlocal(5)).__not__();
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(4).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__not__();
                     if (var10000.__nonzero__()) {
                        var10000 = PyString.fromInterned(".")._add(var1.getlocal(4)).__getattr__("endswith").__call__(var2, var1.getlocal(5)).__not__();
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1016);
                     var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("   effective request-host %s (even with added initial dot) does not end with %s"), (PyObject)var1.getlocal(4), (PyObject)var1.getlocal(5));
                     var1.setline(1019);
                     var3 = var1.getglobal("False");
                     var1.f_lasti = -1;
                     return var3;
                  }
               }

               var1.setline(1020);
               var4 = var1.getlocal(1).__getattr__("version");
               var10000 = var4._gt(Py.newInteger(0));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("strict_ns_domain")._and(var1.getlocal(0).__getattr__("DomainRFC2965Match"));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1022);
                  if (var1.getglobal("domain_match").__call__(var2, var1.getlocal(4), var1.getlocal(5)).__not__().__nonzero__()) {
                     var1.setline(1023);
                     var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("   effective request-host %s does not domain-match %s"), (PyObject)var1.getlocal(4), (PyObject)var1.getlocal(5));
                     var1.setline(1025);
                     var3 = var1.getglobal("False");
                     var1.f_lasti = -1;
                     return var3;
                  }
               }

               var1.setline(1026);
               var4 = var1.getlocal(1).__getattr__("version");
               var10000 = var4._gt(Py.newInteger(0));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("strict_ns_domain")._and(var1.getlocal(0).__getattr__("DomainStrictNoDots"));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1028);
                  var4 = var1.getlocal(3).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getlocal(5)).__neg__(), (PyObject)null);
                  var1.setlocal(12, var4);
                  var4 = null;
                  var1.setline(1029);
                  var4 = var1.getlocal(12).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                  var10000 = var4._ge(Py.newInteger(0));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("IPV4_RE").__getattr__("search").__call__(var2, var1.getlocal(3)).__not__();
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1031);
                     var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("   host prefix %s for domain %s contains a dot"), (PyObject)var1.getlocal(12), (PyObject)var1.getlocal(5));
                     var1.setline(1033);
                     var3 = var1.getglobal("False");
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }

            var1.setline(1034);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject set_ok_port$54(PyFrame var1, ThreadState var2) {
      var1.setline(1037);
      PyObject var6;
      if (var1.getlocal(1).__getattr__("port_specified").__nonzero__()) {
         var1.setline(1038);
         PyObject var3 = var1.getglobal("request_port").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1039);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1040);
            PyString var8 = PyString.fromInterned("80");
            var1.setlocal(3, var8);
            var3 = null;
         } else {
            var1.setline(1042);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(1043);
         var3 = var1.getlocal(1).__getattr__("port").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

         do {
            var1.setline(1043);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1052);
               var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("   request port (%s) not found in %s"), (PyObject)var1.getlocal(3), (PyObject)var1.getlocal(1).__getattr__("port"));
               var1.setline(1054);
               var6 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var6;
            }

            var1.setlocal(4, var4);

            PyException var5;
            try {
               var1.setline(1045);
               var1.getglobal("int").__call__(var2, var1.getlocal(4));
            } catch (Throwable var7) {
               var5 = Py.setException(var7, var1);
               if (var5.match(var1.getglobal("ValueError"))) {
                  var1.setline(1047);
                  var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   bad port %s (not numeric)"), (PyObject)var1.getlocal(4));
                  var1.setline(1048);
                  var6 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var6;
               }

               throw var5;
            }

            var1.setline(1049);
            PyObject var9 = var1.getlocal(4);
            var10000 = var9._eq(var1.getlocal(3));
            var5 = null;
         } while(!var10000.__nonzero__());
      }

      var1.setline(1055);
      var6 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject return_ok$55(PyFrame var1, ThreadState var2) {
      var1.setline(1063);
      PyString.fromInterned("\n        If you override .return_ok(), be sure to call this method.  If it\n        returns false, so should your subclass (assuming your subclass wants to\n        be more strict about which cookies to return).\n\n        ");
      var1.setline(1066);
      var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned(" - checking cookie %s=%s"), (PyObject)var1.getlocal(1).__getattr__("name"), (PyObject)var1.getlocal(1).__getattr__("value"));
      var1.setline(1068);
      PyObject var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("version"), PyString.fromInterned("verifiability"), PyString.fromInterned("secure"), PyString.fromInterned("expires"), PyString.fromInterned("port"), PyString.fromInterned("domain")})).__iter__();

      PyObject var5;
      do {
         var1.setline(1068);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1073);
            var5 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(1069);
         var5 = PyString.fromInterned("return_ok_")._add(var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(1070);
         var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(1071);
      } while(!var1.getlocal(5).__call__(var2, var1.getlocal(1), var1.getlocal(2)).__not__().__nonzero__());

      var1.setline(1072);
      var5 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject return_ok_version$56(PyFrame var1, ThreadState var2) {
      var1.setline(1076);
      PyObject var3 = var1.getlocal(1).__getattr__("version");
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("rfc2965").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1077);
         var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   RFC 2965 cookies are switched off"));
         var1.setline(1078);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1079);
         PyObject var4 = var1.getlocal(1).__getattr__("version");
         var10000 = var4._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("netscape").__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1080);
            var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   Netscape cookies are switched off"));
            var1.setline(1081);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1082);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject return_ok_verifiability$57(PyFrame var1, ThreadState var2) {
      var1.setline(1085);
      PyObject var10000 = var1.getlocal(2).__getattr__("is_unverifiable").__call__(var2);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("is_third_party").__call__(var2, var1.getlocal(2));
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(1086);
         var3 = var1.getlocal(1).__getattr__("version");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("strict_rfc2965_unverifiable");
         }

         if (var10000.__nonzero__()) {
            var1.setline(1087);
            var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   third-party RFC 2965 cookie during unverifiable transaction"));
            var1.setline(1089);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(1090);
         PyObject var4 = var1.getlocal(1).__getattr__("version");
         var10000 = var4._eq(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("strict_ns_unverifiable");
         }

         if (var10000.__nonzero__()) {
            var1.setline(1091);
            var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   third-party Netscape cookie during unverifiable transaction"));
            var1.setline(1093);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(1094);
      var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject return_ok_secure$58(PyFrame var1, ThreadState var2) {
      var1.setline(1097);
      PyObject var10000 = var1.getlocal(1).__getattr__("secure");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2).__getattr__("get_type").__call__(var2);
         var10000 = var3._ne(PyString.fromInterned("https"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1098);
         var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   secure cookie with non-secure request"));
         var1.setline(1099);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1100);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject return_ok_expires$59(PyFrame var1, ThreadState var2) {
      var1.setline(1103);
      PyObject var3;
      if (var1.getlocal(1).__getattr__("is_expired").__call__(var2, var1.getlocal(0).__getattr__("_now")).__nonzero__()) {
         var1.setline(1104);
         var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   cookie expired"));
         var1.setline(1105);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1106);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject return_ok_port$60(PyFrame var1, ThreadState var2) {
      var1.setline(1109);
      PyObject var5;
      if (var1.getlocal(1).__getattr__("port").__nonzero__()) {
         var1.setline(1110);
         PyObject var3 = var1.getglobal("request_port").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1111);
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1112);
            PyString var6 = PyString.fromInterned("80");
            var1.setlocal(3, var6);
            var3 = null;
         }

         var1.setline(1113);
         var3 = var1.getlocal(1).__getattr__("port").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

         do {
            var1.setline(1113);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(1117);
               var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("   request port %s does not match cookie port %s"), (PyObject)var1.getlocal(3), (PyObject)var1.getlocal(1).__getattr__("port"));
               var1.setline(1119);
               var5 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var5;
            }

            var1.setlocal(4, var4);
            var1.setline(1114);
            var5 = var1.getlocal(4);
            var10000 = var5._eq(var1.getlocal(3));
            var5 = null;
         } while(!var10000.__nonzero__());
      }

      var1.setline(1120);
      var5 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject return_ok_domain$61(PyFrame var1, ThreadState var2) {
      var1.setline(1123);
      PyObject var3 = var1.getglobal("eff_request_host").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1124);
      var3 = var1.getlocal(1).__getattr__("domain");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1127);
      var3 = var1.getlocal(1).__getattr__("version");
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("strict_ns_domain")._and(var1.getlocal(0).__getattr__("DomainStrictNonDomain"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("domain_specified").__not__();
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(5);
               var10000 = var3._ne(var1.getlocal(4));
               var3 = null;
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1130);
         var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   cookie with unspecified domain does not string-compare equal to request domain"));
         var1.setline(1132);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1134);
         PyObject var6 = var1.getlocal(1).__getattr__("version");
         var10000 = var6._gt(Py.newInteger(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("domain_match").__call__(var2, var1.getlocal(4), var1.getlocal(5)).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1135);
            var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("   effective request-host name %s does not domain-match RFC 2965 cookie domain %s"), (PyObject)var1.getlocal(4), (PyObject)var1.getlocal(5));
            var1.setline(1137);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1138);
            var6 = var1.getlocal(1).__getattr__("version");
            var10000 = var6._eq(Py.newInteger(0));
            var4 = null;
            if (var10000.__nonzero__()) {
               var10000 = PyString.fromInterned(".")._add(var1.getlocal(4)).__getattr__("endswith").__call__(var2, var1.getlocal(5)).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(1139);
               var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("   request-host %s does not match Netscape cookie domain %s"), (PyObject)var1.getlocal(3), (PyObject)var1.getlocal(5));
               var1.setline(1141);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1142);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject domain_return_ok$62(PyFrame var1, ThreadState var2) {
      var1.setline(1147);
      PyObject var3 = var1.getglobal("eff_request_host").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1148);
      if (var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__not__().__nonzero__()) {
         var1.setline(1149);
         var3 = PyString.fromInterned(".")._add(var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1150);
      if (var1.getlocal(4).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__not__().__nonzero__()) {
         var1.setline(1151);
         var3 = PyString.fromInterned(".")._add(var1.getlocal(4));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(1152);
      PyObject var10000 = var1.getlocal(3).__getattr__("endswith").__call__(var2, var1.getlocal(1));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(4).__getattr__("endswith").__call__(var2, var1.getlocal(1));
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(1155);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1157);
         if (var1.getlocal(0).__getattr__("is_blocked").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(1158);
            var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   domain %s is in user block-list"), (PyObject)var1.getlocal(1));
            var1.setline(1159);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1160);
            if (var1.getlocal(0).__getattr__("is_not_allowed").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(1161);
               var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   domain %s is not in user allow-list"), (PyObject)var1.getlocal(1));
               var1.setline(1162);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1164);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject path_return_ok$63(PyFrame var1, ThreadState var2) {
      var1.setline(1167);
      var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("- checking cookie path=%s"), (PyObject)var1.getlocal(1));
      var1.setline(1168);
      PyObject var3 = var1.getglobal("request_path").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1169);
      if (var1.getlocal(3).__getattr__("startswith").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(1170);
         var1.getglobal("_debug").__call__((ThreadState)var2, PyString.fromInterned("  %s does not path-match %s"), (PyObject)var1.getlocal(3), (PyObject)var1.getlocal(1));
         var1.setline(1171);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1172);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject vals_sorted_by_key$64(PyFrame var1, ThreadState var2) {
      var1.setline(1176);
      PyObject var3 = var1.getlocal(0).__getattr__("keys").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1177);
      var1.getlocal(1).__getattr__("sort").__call__(var2);
      var1.setline(1178);
      var3 = var1.getglobal("map").__call__(var2, var1.getlocal(0).__getattr__("get"), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject deepvalues$65(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject Absent$66(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1199);
      return var1.getf_locals();
   }

   public PyObject CookieJar$67(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Collection of HTTP cookies.\n\n    You may not need to know about this class: try\n    urllib2.build_opener(HTTPCookieProcessor).open(url).\n\n    "));
      var1.setline(1207);
      PyString.fromInterned("Collection of HTTP cookies.\n\n    You may not need to know about this class: try\n    urllib2.build_opener(HTTPCookieProcessor).open(url).\n\n    ");
      var1.setline(1209);
      PyObject var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\W"));
      var1.setlocal("non_word_re", var3);
      var3 = null;
      var1.setline(1210);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([\\\"\\\\])"));
      var1.setlocal("quote_re", var3);
      var3 = null;
      var1.setline(1211);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\.?[^.]*"));
      var1.setlocal("strict_domain_re", var3);
      var3 = null;
      var1.setline(1212);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[^.]*"));
      var1.setlocal("domain_re", var3);
      var3 = null;
      var1.setline(1213);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\.+"));
      var1.setlocal("dots_re", var3);
      var3 = null;
      var1.setline(1215);
      PyString var4 = PyString.fromInterned("^\\#LWP-Cookies-(\\d+\\.\\d+)");
      var1.setlocal("magic_re", var4);
      var3 = null;
      var1.setline(1217);
      PyObject[] var5 = new PyObject[]{var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$68, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(1225);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_policy$69, (PyObject)null);
      var1.setlocal("set_policy", var6);
      var3 = null;
      var1.setline(1228);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _cookies_for_domain$70, (PyObject)null);
      var1.setlocal("_cookies_for_domain", var6);
      var3 = null;
      var1.setline(1246);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _cookies_for_request$71, PyString.fromInterned("Return a list of cookies to be returned to server."));
      var1.setlocal("_cookies_for_request", var6);
      var3 = null;
      var1.setline(1253);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _cookie_attrs$72, PyString.fromInterned("Return a list of cookie-attributes to be returned to server.\n\n        like ['foo=\"bar\"; $Path=\"/\"', ...]\n\n        The $Version attribute is also added when appropriate (currently only\n        once per request).\n\n        "));
      var1.setlocal("_cookie_attrs", var6);
      var3 = null;
      var1.setline(1312);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, add_cookie_header$74, PyString.fromInterned("Add correct Cookie: header to request (urllib2.Request object).\n\n        The Cookie2 header is also added unless policy.hide_cookie2 is true.\n\n        "));
      var1.setlocal("add_cookie_header", var6);
      var3 = null;
      var1.setline(1345);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _normalized_cookie_tuples$75, PyString.fromInterned("Return list of tuples containing normalised cookie information.\n\n        attrs_set is the list of lists of key,value pairs extracted from\n        the Set-Cookie or Set-Cookie2 headers.\n\n        Tuples are name, value, standard, rest, where name and value are the\n        cookie name and value, standard is a dictionary containing the standard\n        cookie-attributes (discard, secure, version, expires or max-age,\n        domain, path and port) and rest is a dictionary containing the rest of\n        the cookie-attributes.\n\n        "));
      var1.setlocal("_normalized_cookie_tuples", var6);
      var3 = null;
      var1.setline(1442);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _cookie_from_cookie_tuple$76, (PyObject)null);
      var1.setlocal("_cookie_from_cookie_tuple", var6);
      var3 = null;
      var1.setline(1534);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _cookies_from_attrs_set$77, (PyObject)null);
      var1.setlocal("_cookies_from_attrs_set", var6);
      var3 = null;
      var1.setline(1543);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _process_rfc2109_cookies$78, (PyObject)null);
      var1.setlocal("_process_rfc2109_cookies", var6);
      var3 = null;
      var1.setline(1555);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, make_cookies$79, PyString.fromInterned("Return sequence of Cookie objects extracted from response object."));
      var1.setlocal("make_cookies", var6);
      var3 = null;
      var1.setline(1609);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_cookie_if_ok$81, PyString.fromInterned("Set a cookie if policy says it's OK to do so."));
      var1.setlocal("set_cookie_if_ok", var6);
      var3 = null;
      var1.setline(1622);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_cookie$82, PyString.fromInterned("Set a cookie, without checking whether or not it should be set."));
      var1.setlocal("set_cookie", var6);
      var3 = null;
      var1.setline(1635);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, extract_cookies$83, PyString.fromInterned("Extract cookies from response, where allowable given the request."));
      var1.setlocal("extract_cookies", var6);
      var3 = null;
      var1.setline(1649);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, clear$84, PyString.fromInterned("Clear some cookies.\n\n        Invoking this method without arguments will clear all cookies.  If\n        given a single argument, only cookies belonging to that domain will be\n        removed.  If given two arguments, cookies belonging to the specified\n        path within that domain are removed.  If given three arguments, then\n        the cookie with the specified name, path and domain is removed.\n\n        Raises KeyError if no matching cookie exists.\n\n        "));
      var1.setlocal("clear", var6);
      var3 = null;
      var1.setline(1676);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, clear_session_cookies$85, PyString.fromInterned("Discard all session cookies.\n\n        Note that the .save() method won't save session cookies anyway, unless\n        you ask otherwise by passing a true ignore_discard argument.\n\n        "));
      var1.setlocal("clear_session_cookies", var6);
      var3 = null;
      var1.setline(1691);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, clear_expired_cookies$86, PyString.fromInterned("Discard all expired cookies.\n\n        You probably don't need to call this method: expired cookies are never\n        sent back to the server (provided you're using DefaultCookiePolicy),\n        this method is called by CookieJar itself every so often, and the\n        .save() method won't save expired cookies anyway (unless you ask\n        otherwise by passing a true ignore_expires argument).\n\n        "));
      var1.setlocal("clear_expired_cookies", var6);
      var3 = null;
      var1.setline(1710);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __iter__$87, (PyObject)null);
      var1.setlocal("__iter__", var6);
      var3 = null;
      var1.setline(1713);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __len__$88, PyString.fromInterned("Return number of contained cookies."));
      var1.setlocal("__len__", var6);
      var3 = null;
      var1.setline(1719);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __repr__$89, (PyObject)null);
      var1.setlocal("__repr__", var6);
      var3 = null;
      var1.setline(1724);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, __str__$90, (PyObject)null);
      var1.setlocal("__str__", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$68(PyFrame var1, ThreadState var2) {
      var1.setline(1218);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1219);
         var3 = var1.getglobal("DefaultCookiePolicy").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1220);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_policy", var3);
      var3 = null;
      var1.setline(1222);
      var3 = var1.getglobal("_threading").__getattr__("RLock").__call__(var2);
      var1.getlocal(0).__setattr__("_cookies_lock", var3);
      var3 = null;
      var1.setline(1223);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_cookies", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_policy$69(PyFrame var1, ThreadState var2) {
      var1.setline(1226);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_policy", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _cookies_for_domain$70(PyFrame var1, ThreadState var2) {
      var1.setline(1229);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1230);
      if (var1.getlocal(0).__getattr__("_policy").__getattr__("domain_return_ok").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__not__().__nonzero__()) {
         var1.setline(1231);
         var3 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1232);
         var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Checking %s for cookies to return"), (PyObject)var1.getlocal(1));
         var1.setline(1233);
         PyObject var4 = var1.getlocal(0).__getattr__("_cookies").__getitem__(var1.getlocal(1));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1234);
         var4 = var1.getlocal(4).__getattr__("keys").__call__(var2).__iter__();

         while(true) {
            do {
               var1.setline(1234);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(1244);
                  PyObject var8 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var8;
               }

               var1.setlocal(5, var5);
               var1.setline(1235);
            } while(var1.getlocal(0).__getattr__("_policy").__getattr__("path_return_ok").__call__(var2, var1.getlocal(5), var1.getlocal(2)).__not__().__nonzero__());

            var1.setline(1237);
            PyObject var6 = var1.getlocal(4).__getitem__(var1.getlocal(5));
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(1238);
            var6 = var1.getlocal(6).__getattr__("values").__call__(var2).__iter__();

            while(true) {
               var1.setline(1238);
               PyObject var7 = var6.__iternext__();
               if (var7 == null) {
                  break;
               }

               var1.setlocal(7, var7);
               var1.setline(1239);
               if (var1.getlocal(0).__getattr__("_policy").__getattr__("return_ok").__call__(var2, var1.getlocal(7), var1.getlocal(2)).__not__().__nonzero__()) {
                  var1.setline(1240);
                  var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   not returning cookie"));
               } else {
                  var1.setline(1242);
                  var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   it's a match"));
                  var1.setline(1243);
                  var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(7));
               }
            }
         }
      }
   }

   public PyObject _cookies_for_request$71(PyFrame var1, ThreadState var2) {
      var1.setline(1247);
      PyString.fromInterned("Return a list of cookies to be returned to server.");
      var1.setline(1248);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1249);
      PyObject var5 = var1.getlocal(0).__getattr__("_cookies").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(1249);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1251);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(1250);
         var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("_cookies_for_domain").__call__(var2, var1.getlocal(3), var1.getlocal(1)));
      }
   }

   public PyObject _cookie_attrs$72(PyFrame var1, ThreadState var2) {
      var1.setline(1261);
      PyString.fromInterned("Return a list of cookie-attributes to be returned to server.\n\n        like ['foo=\"bar\"; $Path=\"/\"', ...]\n\n        The $Version attribute is also added when appropriate (currently only\n        once per request).\n\n        ");
      var1.setline(1263);
      PyObject var10000 = var1.getlocal(1).__getattr__("sort");
      PyObject[] var3 = new PyObject[2];
      var1.setline(1263);
      PyObject[] var4 = Py.EmptyObjects;
      var3[0] = new PyFunction(var1.f_globals, var4, f$73);
      var3[1] = var1.getglobal("True");
      String[] var7 = new String[]{"key", "reverse"};
      var10000.__call__(var2, var3, var7);
      var3 = null;
      var1.setline(1265);
      PyObject var6 = var1.getglobal("False");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(1267);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(1268);
      var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1268);
         PyObject var9 = var6.__iternext__();
         if (var9 == null) {
            var1.setline(1310);
            var6 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var9);
         var1.setline(1275);
         PyObject var5 = var1.getlocal(4).__getattr__("version");
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(1276);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(1277);
            var5 = var1.getglobal("True");
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(1278);
            var5 = var1.getlocal(5);
            var10000 = var5._gt(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1279);
               var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("$Version=%s")._mod(var1.getlocal(5)));
            }
         }

         var1.setline(1284);
         var5 = var1.getlocal(4).__getattr__("value");
         var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("non_word_re").__getattr__("search").__call__(var2, var1.getlocal(4).__getattr__("value"));
            if (var10000.__nonzero__()) {
               var5 = var1.getlocal(5);
               var10000 = var5._gt(Py.newInteger(0));
               var5 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(1286);
            var5 = var1.getlocal(0).__getattr__("quote_re").__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\\\\1"), (PyObject)var1.getlocal(4).__getattr__("value"));
            var1.setlocal(6, var5);
            var5 = null;
         } else {
            var1.setline(1288);
            var5 = var1.getlocal(4).__getattr__("value");
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(1291);
         var5 = var1.getlocal(4).__getattr__("value");
         var10000 = var5._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1292);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(4).__getattr__("name"));
         } else {
            var1.setline(1294);
            var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4).__getattr__("name"), var1.getlocal(6)})));
         }

         var1.setline(1295);
         var5 = var1.getlocal(5);
         var10000 = var5._gt(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1296);
            if (var1.getlocal(4).__getattr__("path_specified").__nonzero__()) {
               var1.setline(1297);
               var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("$Path=\"%s\"")._mod(var1.getlocal(4).__getattr__("path")));
            }

            var1.setline(1298);
            if (var1.getlocal(4).__getattr__("domain").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__nonzero__()) {
               var1.setline(1299);
               var5 = var1.getlocal(4).__getattr__("domain");
               var1.setlocal(7, var5);
               var5 = null;
               var1.setline(1300);
               var10000 = var1.getlocal(4).__getattr__("domain_initial_dot").__not__();
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(7).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1302);
                  var5 = var1.getlocal(7).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
                  var1.setlocal(7, var5);
                  var5 = null;
               }

               var1.setline(1303);
               var1.getlocal(3).__getattr__("append").__call__(var2, PyString.fromInterned("$Domain=\"%s\"")._mod(var1.getlocal(7)));
            }

            var1.setline(1304);
            var5 = var1.getlocal(4).__getattr__("port");
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1305);
               PyString var10 = PyString.fromInterned("$Port");
               var1.setlocal(8, var10);
               var5 = null;
               var1.setline(1306);
               if (var1.getlocal(4).__getattr__("port_specified").__nonzero__()) {
                  var1.setline(1307);
                  var5 = var1.getlocal(8)._add(PyString.fromInterned("=\"%s\"")._mod(var1.getlocal(4).__getattr__("port")));
                  var1.setlocal(8, var5);
                  var5 = null;
               }

               var1.setline(1308);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(8));
            }
         }
      }
   }

   public PyObject f$73(PyFrame var1, ThreadState var2) {
      var1.setline(1263);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("path"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject add_cookie_header$74(PyFrame var1, ThreadState var2) {
      var1.setline(1317);
      PyString.fromInterned("Add correct Cookie: header to request (urllib2.Request object).\n\n        The Cookie2 header is also added unless policy.hide_cookie2 is true.\n\n        ");
      var1.setline(1318);
      var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("add_cookie_header"));
      var1.setline(1319);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1322);
         PyObject var4 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2));
         var1.getlocal(0).__getattr__("_policy").__setattr__("_now", var4);
         var1.getlocal(0).__setattr__("_now", var4);
         var1.setline(1324);
         var4 = var1.getlocal(0).__getattr__("_cookies_for_request").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1326);
         var4 = var1.getlocal(0).__getattr__("_cookie_attrs").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1327);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(1328);
            if (var1.getlocal(1).__getattr__("has_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cookie")).__not__().__nonzero__()) {
               var1.setline(1329);
               var1.getlocal(1).__getattr__("add_unredirected_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cookie"), (PyObject)PyString.fromInterned("; ").__getattr__("join").__call__(var2, var1.getlocal(3)));
            }
         }

         var1.setline(1333);
         PyObject var10000 = var1.getlocal(0).__getattr__("_policy").__getattr__("rfc2965");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_policy").__getattr__("hide_cookie2").__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(1).__getattr__("has_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cookie2")).__not__();
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(1335);
            var4 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(1335);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  break;
               }

               var1.setlocal(4, var5);
               var1.setline(1336);
               PyObject var6 = var1.getlocal(4).__getattr__("version");
               var10000 = var6._ne(Py.newInteger(1));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1337);
                  var1.getlocal(1).__getattr__("add_unredirected_header").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cookie2"), (PyObject)PyString.fromInterned("$Version=\"1\""));
                  break;
               }
            }
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(1341);
         var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(1341);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
      var1.setline(1343);
      var1.getlocal(0).__getattr__("clear_expired_cookies").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _normalized_cookie_tuples$75(PyFrame var1, ThreadState var2) {
      var1.setline(1357);
      PyString.fromInterned("Return list of tuples containing normalised cookie information.\n\n        attrs_set is the list of lists of key,value pairs extracted from\n        the Set-Cookie or Set-Cookie2 headers.\n\n        Tuples are name, value, standard, rest, where name and value are the\n        cookie name and value, standard is a dictionary containing the standard\n        cookie-attributes (discard, secure, version, expires or max-age,\n        domain, path and port) and rest is a dictionary containing the rest of\n        the cookie-attributes.\n\n        ");
      var1.setline(1358);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1360);
      PyTuple var10 = new PyTuple(new PyObject[]{PyString.fromInterned("discard"), PyString.fromInterned("secure")});
      var1.setlocal(3, var10);
      var3 = null;
      var1.setline(1361);
      var10 = new PyTuple(new PyObject[]{PyString.fromInterned("version"), PyString.fromInterned("expires"), PyString.fromInterned("max-age"), PyString.fromInterned("domain"), PyString.fromInterned("path"), PyString.fromInterned("port"), PyString.fromInterned("comment"), PyString.fromInterned("commenturl")});
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(1366);
      PyObject var11 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1366);
         PyObject var4 = var11.__iternext__();
         if (var4 == null) {
            var1.setline(1440);
            var11 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var11;
         }

         var1.setlocal(5, var4);
         var1.setline(1367);
         PyObject var5 = var1.getlocal(5).__getitem__(Py.newInteger(0));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(1377);
         var5 = var1.getglobal("False");
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(1379);
         var5 = var1.getglobal("False");
         var1.setlocal(9, var5);
         var5 = null;
         var1.setline(1381);
         PyDictionary var13 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(10, var13);
         var5 = null;
         var1.setline(1382);
         var13 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(11, var13);
         var5 = null;
         var1.setline(1383);
         var5 = var1.getlocal(5).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         label99:
         while(true) {
            while(true) {
               PyObject var8;
               PyObject var10000;
               do {
                  var1.setline(1383);
                  PyObject var12 = var5.__iternext__();
                  if (var12 == null) {
                     break label99;
                  }

                  PyObject[] var14 = Py.unpackSequence(var12, 2);
                  var8 = var14[0];
                  var1.setlocal(12, var8);
                  var8 = null;
                  var8 = var14[1];
                  var1.setlocal(13, var8);
                  var8 = null;
                  var1.setline(1384);
                  var7 = var1.getlocal(12).__getattr__("lower").__call__(var2);
                  var1.setlocal(14, var7);
                  var7 = null;
                  var1.setline(1386);
                  var7 = var1.getlocal(14);
                  var10000 = var7._in(var1.getlocal(4));
                  var7 = null;
                  if (!var10000.__nonzero__()) {
                     var7 = var1.getlocal(14);
                     var10000 = var7._in(var1.getlocal(3));
                     var7 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1387);
                     var7 = var1.getlocal(14);
                     var1.setlocal(12, var7);
                     var7 = null;
                  }

                  var1.setline(1388);
                  var7 = var1.getlocal(12);
                  var10000 = var7._in(var1.getlocal(3));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var7 = var1.getlocal(13);
                     var10000 = var7._is(var1.getglobal("None"));
                     var7 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1391);
                     var7 = var1.getglobal("True");
                     var1.setlocal(13, var7);
                     var7 = null;
                  }

                  var1.setline(1392);
                  var7 = var1.getlocal(12);
                  var10000 = var7._in(var1.getlocal(10));
                  var7 = null;
               } while(var10000.__nonzero__());

               var1.setline(1395);
               var7 = var1.getlocal(12);
               var10000 = var7._eq(PyString.fromInterned("domain"));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1396);
                  var7 = var1.getlocal(13);
                  var10000 = var7._is(var1.getglobal("None"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1397);
                     var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   missing value for domain attribute"));
                     var1.setline(1398);
                     var7 = var1.getglobal("True");
                     var1.setlocal(9, var7);
                     var7 = null;
                     break label99;
                  }

                  var1.setline(1401);
                  var7 = var1.getlocal(13).__getattr__("lower").__call__(var2);
                  var1.setlocal(13, var7);
                  var7 = null;
               }

               var1.setline(1402);
               var7 = var1.getlocal(12);
               var10000 = var7._eq(PyString.fromInterned("expires"));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1403);
                  if (var1.getlocal(8).__nonzero__()) {
                     continue;
                  }

                  var1.setline(1406);
                  var7 = var1.getlocal(13);
                  var10000 = var7._is(var1.getglobal("None"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(1407);
                     var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   missing or invalid value for expires attribute: treating as session cookie"));
                     continue;
                  }
               }

               var1.setline(1410);
               var7 = var1.getlocal(12);
               var10000 = var7._eq(PyString.fromInterned("max-age"));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1411);
                  var7 = var1.getglobal("True");
                  var1.setlocal(8, var7);
                  var7 = null;

                  try {
                     var1.setline(1413);
                     var7 = var1.getglobal("int").__call__(var2, var1.getlocal(13));
                     var1.setlocal(13, var7);
                     var7 = null;
                  } catch (Throwable var9) {
                     PyException var15 = Py.setException(var9, var1);
                     if (var15.match(var1.getglobal("ValueError"))) {
                        var1.setline(1415);
                        var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   missing or invalid (non-numeric) value for max-age attribute"));
                        var1.setline(1417);
                        var8 = var1.getglobal("True");
                        var1.setlocal(9, var8);
                        var8 = null;
                        break label99;
                     }

                     throw var15;
                  }

                  var1.setline(1423);
                  PyString var16 = PyString.fromInterned("expires");
                  var1.setlocal(12, var16);
                  var7 = null;
                  var1.setline(1424);
                  var7 = var1.getlocal(0).__getattr__("_now")._add(var1.getlocal(13));
                  var1.setlocal(13, var7);
                  var7 = null;
               }

               var1.setline(1425);
               var7 = var1.getlocal(12);
               var10000 = var7._in(var1.getlocal(4));
               var7 = null;
               if (!var10000.__nonzero__()) {
                  var7 = var1.getlocal(12);
                  var10000 = var7._in(var1.getlocal(3));
                  var7 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1426);
                  var7 = var1.getlocal(13);
                  var10000 = var7._is(var1.getglobal("None"));
                  var7 = null;
                  if (var10000.__nonzero__()) {
                     var7 = var1.getlocal(12);
                     var10000 = var7._notin(new PyTuple(new PyObject[]{PyString.fromInterned("port"), PyString.fromInterned("comment"), PyString.fromInterned("commenturl")}));
                     var7 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(1428);
                     var1.getglobal("_debug").__call__(var2, PyString.fromInterned("   missing value for %s attribute")._mod(var1.getlocal(12)));
                     var1.setline(1429);
                     var7 = var1.getglobal("True");
                     var1.setlocal(9, var7);
                     var7 = null;
                     break label99;
                  }

                  var1.setline(1431);
                  var7 = var1.getlocal(13);
                  var1.getlocal(10).__setitem__(var1.getlocal(12), var7);
                  var7 = null;
               } else {
                  var1.setline(1433);
                  var7 = var1.getlocal(13);
                  var1.getlocal(11).__setitem__(var1.getlocal(12), var7);
                  var7 = null;
               }
            }
         }

         var1.setline(1435);
         if (!var1.getlocal(9).__nonzero__()) {
            var1.setline(1438);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7), var1.getlocal(10), var1.getlocal(11)})));
         }
      }
   }

   public PyObject _cookie_from_cookie_tuple$76(PyFrame var1, ThreadState var2) {
      var1.setline(1445);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(1447);
      var3 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("domain"), (PyObject)var1.getglobal("Absent"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1448);
      var3 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("path"), (PyObject)var1.getglobal("Absent"));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(1449);
      var3 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("port"), (PyObject)var1.getglobal("Absent"));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(1450);
      var3 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expires"), (PyObject)var1.getglobal("Absent"));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(1453);
      var3 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("version"), (PyObject)var1.getglobal("None"));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(1454);
      var3 = var1.getlocal(11);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      PyObject var9;
      PyException var10;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1456);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(11));
            var1.setlocal(11, var3);
            var3 = null;
         } catch (Throwable var7) {
            var10 = Py.setException(var7, var1);
            if (var10.match(var1.getglobal("ValueError"))) {
               var1.setline(1458);
               var9 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var9;
            }

            throw var10;
         }
      }

      var1.setline(1459);
      var3 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("secure"), (PyObject)var1.getglobal("False"));
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(1461);
      var3 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("discard"), (PyObject)var1.getglobal("False"));
      var1.setlocal(13, var3);
      var3 = null;
      var1.setline(1462);
      var3 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("comment"), (PyObject)var1.getglobal("None"));
      var1.setlocal(14, var3);
      var3 = null;
      var1.setline(1463);
      var3 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("commenturl"), (PyObject)var1.getglobal("None"));
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(1466);
      var3 = var1.getlocal(8);
      var10000 = var3._isnot(var1.getglobal("Absent"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(8);
         var10000 = var3._ne(PyString.fromInterned(""));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(1467);
         var3 = var1.getglobal("True");
         var1.setlocal(16, var3);
         var3 = null;
         var1.setline(1468);
         var3 = var1.getglobal("escape_path").__call__(var2, var1.getlocal(8));
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(1470);
         var3 = var1.getglobal("False");
         var1.setlocal(16, var3);
         var3 = null;
         var1.setline(1471);
         var3 = var1.getglobal("request_path").__call__(var2, var1.getlocal(2));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(1472);
         var3 = var1.getlocal(8).__getattr__("rfind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"));
         var1.setlocal(17, var3);
         var3 = null;
         var1.setline(1473);
         var3 = var1.getlocal(17);
         var10000 = var3._ne(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1474);
            var3 = var1.getlocal(11);
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1476);
               var3 = var1.getlocal(8).__getslice__((PyObject)null, var1.getlocal(17), (PyObject)null);
               var1.setlocal(8, var3);
               var3 = null;
            } else {
               var1.setline(1478);
               var3 = var1.getlocal(8).__getslice__((PyObject)null, var1.getlocal(17)._add(Py.newInteger(1)), (PyObject)null);
               var1.setlocal(8, var3);
               var3 = null;
            }
         }

         var1.setline(1479);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(8));
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1479);
            PyString var12 = PyString.fromInterned("/");
            var1.setlocal(8, var12);
            var3 = null;
         }
      }

      var1.setline(1482);
      var3 = var1.getlocal(7);
      var10000 = var3._isnot(var1.getglobal("Absent"));
      var3 = null;
      var3 = var10000;
      var1.setlocal(18, var3);
      var3 = null;
      var1.setline(1484);
      var3 = var1.getglobal("False");
      var1.setlocal(19, var3);
      var3 = null;
      var1.setline(1485);
      if (var1.getlocal(18).__nonzero__()) {
         var1.setline(1486);
         var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(7).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")));
         var1.setlocal(19, var3);
         var3 = null;
      }

      var1.setline(1487);
      var3 = var1.getlocal(7);
      var10000 = var3._is(var1.getglobal("Absent"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1488);
         var3 = var1.getglobal("eff_request_host").__call__(var2, var1.getlocal(2));
         PyObject[] var11 = Py.unpackSequence(var3, 2);
         PyObject var6 = var11[0];
         var1.setlocal(20, var6);
         var6 = null;
         var6 = var11[1];
         var1.setlocal(21, var6);
         var6 = null;
         var3 = null;
         var1.setline(1489);
         var3 = var1.getlocal(21);
         var1.setlocal(7, var3);
         var3 = null;
      } else {
         var1.setline(1490);
         if (var1.getlocal(7).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__not__().__nonzero__()) {
            var1.setline(1491);
            var3 = PyString.fromInterned(".")._add(var1.getlocal(7));
            var1.setlocal(7, var3);
            var3 = null;
         }
      }

      var1.setline(1494);
      var3 = var1.getglobal("False");
      var1.setlocal(22, var3);
      var3 = null;
      var1.setline(1495);
      var3 = var1.getlocal(9);
      var10000 = var3._isnot(var1.getglobal("Absent"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1496);
         var3 = var1.getlocal(9);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1499);
            var3 = var1.getglobal("request_port").__call__(var2, var1.getlocal(2));
            var1.setlocal(9, var3);
            var3 = null;
         } else {
            var1.setline(1501);
            var3 = var1.getglobal("True");
            var1.setlocal(22, var3);
            var3 = null;
            var1.setline(1502);
            var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("\\s+"), (PyObject)PyString.fromInterned(""), (PyObject)var1.getlocal(9));
            var1.setlocal(9, var3);
            var3 = null;
         }
      } else {
         var1.setline(1505);
         var3 = var1.getglobal("None");
         var1.setlocal(9, var3);
         var3 = null;
      }

      var1.setline(1508);
      var3 = var1.getlocal(10);
      var10000 = var3._is(var1.getglobal("Absent"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1509);
         var3 = var1.getglobal("None");
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(1510);
         var3 = var1.getglobal("True");
         var1.setlocal(13, var3);
         var3 = null;
      } else {
         var1.setline(1511);
         var3 = var1.getlocal(10);
         var10000 = var3._le(var1.getlocal(0).__getattr__("_now"));
         var3 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(1515);
               var1.getlocal(0).__getattr__("clear").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(3));
            } catch (Throwable var8) {
               var10 = Py.setException(var8, var1);
               if (!var10.match(var1.getglobal("KeyError"))) {
                  throw var10;
               }

               var1.setline(1517);
            }

            var1.setline(1518);
            var1.getglobal("_debug").__call__(var2, PyString.fromInterned("Expiring cookie, domain='%s', path='%s', name='%s'"), var1.getlocal(7), var1.getlocal(8), var1.getlocal(3));
            var1.setline(1520);
            var9 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var9;
         }
      }

      var1.setline(1522);
      var10000 = var1.getglobal("Cookie");
      PyObject[] var13 = new PyObject[]{var1.getlocal(11), var1.getlocal(3), var1.getlocal(4), var1.getlocal(9), var1.getlocal(22), var1.getlocal(7), var1.getlocal(18), var1.getlocal(19), var1.getlocal(8), var1.getlocal(16), var1.getlocal(12), var1.getlocal(10), var1.getlocal(13), var1.getlocal(14), var1.getlocal(15), var1.getlocal(6)};
      var9 = var10000.__call__(var2, var13);
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject _cookies_from_attrs_set$77(PyFrame var1, ThreadState var2) {
      var1.setline(1535);
      PyObject var3 = var1.getlocal(0).__getattr__("_normalized_cookie_tuples").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1537);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(1538);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(1538);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(1541);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(1539);
         PyObject var5 = var1.getlocal(0).__getattr__("_cookie_from_cookie_tuple").__call__(var2, var1.getlocal(5), var1.getlocal(2));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(1540);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(1540);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
         }
      }
   }

   public PyObject _process_rfc2109_cookies$78(PyFrame var1, ThreadState var2) {
      var1.setline(1544);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("_policy"), (PyObject)PyString.fromInterned("rfc2109_as_netscape"), (PyObject)var1.getglobal("None"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1545);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1546);
         var3 = var1.getlocal(0).__getattr__("_policy").__getattr__("rfc2965").__not__();
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1547);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1547);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(1548);
         PyObject var5 = var1.getlocal(3).__getattr__("version");
         var10000 = var5._eq(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1549);
            var5 = var1.getglobal("True");
            var1.getlocal(3).__setattr__("rfc2109", var5);
            var5 = null;
            var1.setline(1550);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(1553);
               PyInteger var6 = Py.newInteger(0);
               var1.getlocal(3).__setattr__((String)"version", var6);
               var5 = null;
            }
         }
      }
   }

   public PyObject make_cookies$79(PyFrame var1, ThreadState var2) {
      var1.setline(1556);
      PyString.fromInterned("Return sequence of Cookie objects extracted from response object.");
      var1.setline(1558);
      PyObject var3 = var1.getlocal(1).__getattr__("info").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1559);
      var3 = var1.getlocal(3).__getattr__("getheaders").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Set-Cookie2"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1560);
      var3 = var1.getlocal(3).__getattr__("getheaders").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Set-Cookie"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1562);
      var3 = var1.getlocal(0).__getattr__("_policy").__getattr__("rfc2965");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1563);
      var3 = var1.getlocal(0).__getattr__("_policy").__getattr__("netscape");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(1565);
      PyObject var10000 = var1.getlocal(4).__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(5).__not__();
      }

      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(5).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(6).__not__();
         }

         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(4).__not__();
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(7).__not__();
            }

            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(7).__not__();
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(6).__not__();
               }
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1569);
         PyList var12 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var12;
      } else {
         PyException var4;
         PyList var5;
         PyObject var9;
         try {
            var1.setline(1572);
            var9 = var1.getlocal(0).__getattr__("_cookies_from_attrs_set").__call__(var2, var1.getglobal("split_header_words").__call__(var2, var1.getlocal(4)), var1.getlocal(2));
            var1.setlocal(8, var9);
            var4 = null;
         } catch (Throwable var8) {
            var4 = Py.setException(var8, var1);
            if (!var4.match(var1.getglobal("Exception"))) {
               throw var4;
            }

            var1.setline(1575);
            var1.getglobal("_warn_unhandled_exception").__call__(var2);
            var1.setline(1576);
            var5 = new PyList(Py.EmptyObjects);
            var1.setlocal(8, var5);
            var5 = null;
         }

         var1.setline(1578);
         var10000 = var1.getlocal(5);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(7);
         }

         if (var10000.__nonzero__()) {
            try {
               var1.setline(1581);
               var9 = var1.getlocal(0).__getattr__("_cookies_from_attrs_set").__call__(var2, var1.getglobal("parse_ns_headers").__call__(var2, var1.getlocal(5)), var1.getlocal(2));
               var1.setlocal(9, var9);
               var4 = null;
            } catch (Throwable var7) {
               var4 = Py.setException(var7, var1);
               if (!var4.match(var1.getglobal("Exception"))) {
                  throw var4;
               }

               var1.setline(1584);
               var1.getglobal("_warn_unhandled_exception").__call__(var2);
               var1.setline(1585);
               var5 = new PyList(Py.EmptyObjects);
               var1.setlocal(9, var5);
               var5 = null;
            }

            var1.setline(1586);
            var1.getlocal(0).__getattr__("_process_rfc2109_cookies").__call__(var2, var1.getlocal(9));
            var1.setline(1594);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(1595);
               PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
               var1.setlocal(10, var10);
               var4 = null;
               var1.setline(1596);
               var9 = var1.getlocal(8).__iter__();

               while(true) {
                  var1.setline(1596);
                  PyObject var11 = var9.__iternext__();
                  if (var11 == null) {
                     var1.setline(1599);
                     PyObject[] var13 = new PyObject[]{var1.getlocal(10)};
                     PyFunction var14 = new PyFunction(var1.f_globals, var13, no_matching_rfc2965$80, (PyObject)null);
                     var1.setlocal(12, var14);
                     var4 = null;
                     var1.setline(1602);
                     var9 = var1.getglobal("filter").__call__(var2, var1.getlocal(12), var1.getlocal(9));
                     var1.setlocal(9, var9);
                     var4 = null;
                     break;
                  }

                  var1.setlocal(11, var11);
                  var1.setline(1597);
                  PyObject var6 = var1.getglobal("None");
                  var1.getlocal(10).__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(11).__getattr__("domain"), var1.getlocal(11).__getattr__("path"), var1.getlocal(11).__getattr__("name")})), var6);
                  var6 = null;
               }
            }

            var1.setline(1604);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(1605);
               var1.getlocal(8).__getattr__("extend").__call__(var2, var1.getlocal(9));
            }
         }

         var1.setline(1607);
         var3 = var1.getlocal(8);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject no_matching_rfc2965$80(PyFrame var1, ThreadState var2) {
      var1.setline(1600);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("domain"), var1.getlocal(0).__getattr__("path"), var1.getlocal(0).__getattr__("name")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1601);
      PyObject var4 = var1.getlocal(2);
      PyObject var10000 = var4._notin(var1.getlocal(1));
      var3 = null;
      var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject set_cookie_if_ok$81(PyFrame var1, ThreadState var2) {
      var1.setline(1610);
      PyString.fromInterned("Set a cookie if policy says it's OK to do so.");
      var1.setline(1611);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1613);
         PyObject var4 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2));
         var1.getlocal(0).__getattr__("_policy").__setattr__("_now", var4);
         var1.getlocal(0).__setattr__("_now", var4);
         var1.setline(1615);
         if (var1.getlocal(0).__getattr__("_policy").__getattr__("set_ok").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__nonzero__()) {
            var1.setline(1616);
            var1.getlocal(0).__getattr__("set_cookie").__call__(var2, var1.getlocal(1));
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1620);
         var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1620);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_cookie$82(PyFrame var1, ThreadState var2) {
      var1.setline(1623);
      PyString.fromInterned("Set a cookie, without checking whether or not it should be set.");
      var1.setline(1624);
      PyObject var3 = var1.getlocal(0).__getattr__("_cookies");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1625);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("acquire").__call__(var2);
      var3 = null;

      try {
         var1.setline(1627);
         PyObject var4 = var1.getlocal(1).__getattr__("domain");
         PyObject var10000 = var4._notin(var1.getlocal(2));
         var4 = null;
         PyDictionary var6;
         if (var10000.__nonzero__()) {
            var1.setline(1627);
            var6 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(2).__setitem__((PyObject)var1.getlocal(1).__getattr__("domain"), var6);
            var4 = null;
         }

         var1.setline(1628);
         var4 = var1.getlocal(2).__getitem__(var1.getlocal(1).__getattr__("domain"));
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1629);
         var4 = var1.getlocal(1).__getattr__("path");
         var10000 = var4._notin(var1.getlocal(3));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1629);
            var6 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(3).__setitem__((PyObject)var1.getlocal(1).__getattr__("path"), var6);
            var4 = null;
         }

         var1.setline(1630);
         var4 = var1.getlocal(3).__getitem__(var1.getlocal(1).__getattr__("path"));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1631);
         var4 = var1.getlocal(1);
         var1.getlocal(4).__setitem__(var1.getlocal(1).__getattr__("name"), var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(1633);
         var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(1633);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject extract_cookies$83(PyFrame var1, ThreadState var2) {
      var1.setline(1636);
      PyString.fromInterned("Extract cookies from response, where allowable given the request.");
      var1.setline(1637);
      var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("extract_cookies: %s"), (PyObject)var1.getlocal(1).__getattr__("info").__call__(var2));
      var1.setline(1638);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1640);
         PyObject var4 = var1.getglobal("int").__call__(var2, var1.getglobal("time").__getattr__("time").__call__(var2));
         var1.getlocal(0).__getattr__("_policy").__setattr__("_now", var4);
         var1.getlocal(0).__setattr__("_now", var4);
         var1.setline(1642);
         var4 = var1.getlocal(0).__getattr__("make_cookies").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__iter__();

         while(true) {
            var1.setline(1642);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(3, var5);
            var1.setline(1643);
            if (var1.getlocal(0).__getattr__("_policy").__getattr__("set_ok").__call__(var2, var1.getlocal(3), var1.getlocal(2)).__nonzero__()) {
               var1.setline(1644);
               var1.getglobal("_debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" setting cookie: %s"), (PyObject)var1.getlocal(3));
               var1.setline(1645);
               var1.getlocal(0).__getattr__("set_cookie").__call__(var2, var1.getlocal(3));
            }
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(1647);
         var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(1647);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear$84(PyFrame var1, ThreadState var2) {
      var1.setline(1660);
      PyString.fromInterned("Clear some cookies.\n\n        Invoking this method without arguments will clear all cookies.  If\n        given a single argument, only cookies belonging to that domain will be\n        removed.  If given two arguments, cookies belonging to the specified\n        path within that domain are removed.  If given three arguments, then\n        the cookie with the specified name, path and domain is removed.\n\n        Raises KeyError if no matching cookie exists.\n\n        ");
      var1.setline(1661);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1662);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(2);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(1663);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("domain and path must be given to remove a cookie by name")));
         }

         var1.setline(1665);
         var1.getlocal(0).__getattr__("_cookies").__getitem__(var1.getlocal(1)).__getitem__(var1.getlocal(2)).__delitem__(var1.getlocal(3));
      } else {
         var1.setline(1666);
         var3 = var1.getlocal(2);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1667);
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1668);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("domain must be given to remove cookies by path")));
            }

            var1.setline(1670);
            var1.getlocal(0).__getattr__("_cookies").__getitem__(var1.getlocal(1)).__delitem__(var1.getlocal(2));
         } else {
            var1.setline(1671);
            var3 = var1.getlocal(1);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1672);
               var1.getlocal(0).__getattr__("_cookies").__delitem__(var1.getlocal(1));
            } else {
               var1.setline(1674);
               PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
               var1.getlocal(0).__setattr__((String)"_cookies", var4);
               var3 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear_session_cookies$85(PyFrame var1, ThreadState var2) {
      var1.setline(1682);
      PyString.fromInterned("Discard all session cookies.\n\n        Note that the .save() method won't save session cookies anyway, unless\n        you ask otherwise by passing a true ignore_discard argument.\n\n        ");
      var1.setline(1683);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1685);
         PyObject var4 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(1685);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(1, var5);
            var1.setline(1686);
            if (var1.getlocal(1).__getattr__("discard").__nonzero__()) {
               var1.setline(1687);
               var1.getlocal(0).__getattr__("clear").__call__(var2, var1.getlocal(1).__getattr__("domain"), var1.getlocal(1).__getattr__("path"), var1.getlocal(1).__getattr__("name"));
            }
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(1689);
         var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(1689);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear_expired_cookies$86(PyFrame var1, ThreadState var2) {
      var1.setline(1700);
      PyString.fromInterned("Discard all expired cookies.\n\n        You probably don't need to call this method: expired cookies are never\n        sent back to the server (provided you're using DefaultCookiePolicy),\n        this method is called by CookieJar itself every so often, and the\n        .save() method won't save expired cookies anyway (unless you ask\n        otherwise by passing a true ignore_expires argument).\n\n        ");
      var1.setline(1701);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("acquire").__call__(var2);
      Object var3 = null;

      try {
         var1.setline(1703);
         PyObject var4 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1704);
         var4 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(1704);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(2, var5);
            var1.setline(1705);
            if (var1.getlocal(2).__getattr__("is_expired").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(1706);
               var1.getlocal(0).__getattr__("clear").__call__(var2, var1.getlocal(2).__getattr__("domain"), var1.getlocal(2).__getattr__("path"), var1.getlocal(2).__getattr__("name"));
            }
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(1708);
         var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(1708);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __iter__$87(PyFrame var1, ThreadState var2) {
      var1.setline(1711);
      PyObject var3 = var1.getglobal("deepvalues").__call__(var2, var1.getlocal(0).__getattr__("_cookies"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$88(PyFrame var1, ThreadState var2) {
      var1.setline(1714);
      PyString.fromInterned("Return number of contained cookies.");
      var1.setline(1715);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1716);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1716);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(1717);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(1716);
         PyObject var5 = var1.getlocal(1)._add(Py.newInteger(1));
         var1.setlocal(1, var5);
         var5 = null;
      }
   }

   public PyObject __repr__$89(PyFrame var1, ThreadState var2) {
      var1.setline(1720);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1721);
      PyObject var5 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1721);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1722);
            var5 = PyString.fromInterned("<%s[%s]>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1))}));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(1721);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject __str__$90(PyFrame var1, ThreadState var2) {
      var1.setline(1725);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1726);
      PyObject var5 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1726);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(1727);
            var5 = PyString.fromInterned("<%s[%s]>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(1))}));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(1726);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(2)));
      }
   }

   public PyObject LoadError$91(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1731);
      return var1.getf_locals();
   }

   public PyObject FileCookieJar$92(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("CookieJar that can be loaded from and saved to a file."));
      var1.setline(1734);
      PyString.fromInterned("CookieJar that can be loaded from and saved to a file.");
      var1.setline(1736);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$93, PyString.fromInterned("\n        Cookies are NOT loaded from the named file until either the .load() or\n        .revert() method is called.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1751);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, save$94, PyString.fromInterned("Save cookies to a file."));
      var1.setlocal("save", var4);
      var3 = null;
      var1.setline(1755);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, load$95, PyString.fromInterned("Load cookies from a file."));
      var1.setlocal("load", var4);
      var3 = null;
      var1.setline(1767);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("False"), var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, revert$96, PyString.fromInterned("Clear all cookies and reload cookies from a saved file.\n\n        Raises LoadError (or IOError) if reversion is not successful; the\n        object's state will not be altered if this happens.\n\n        "));
      var1.setlocal("revert", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$93(PyFrame var1, ThreadState var2) {
      var1.setline(1741);
      PyString.fromInterned("\n        Cookies are NOT loaded from the named file until either the .load() or\n        .revert() method is called.\n\n        ");
      var1.setline(1742);
      var1.getglobal("CookieJar").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(3));
      var1.setline(1743);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(1745);
            var1.getlocal(1)._add(PyString.fromInterned(""));
         } catch (Throwable var4) {
            Py.setException(var4, var1);
            var1.setline(1747);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("filename must be string-like")));
         }
      }

      var1.setline(1748);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(1749);
      var3 = var1.getglobal("bool").__call__(var2, var1.getlocal(2));
      var1.getlocal(0).__setattr__("delayload", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject save$94(PyFrame var1, ThreadState var2) {
      var1.setline(1752);
      PyString.fromInterned("Save cookies to a file.");
      var1.setline(1753);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__(var2));
   }

   public PyObject load$95(PyFrame var1, ThreadState var2) {
      var1.setline(1756);
      PyString.fromInterned("Load cookies from a file.");
      var1.setline(1757);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1758);
         var3 = var1.getlocal(0).__getattr__("filename");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1759);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("MISSING_FILENAME_TEXT")));
         }

         var1.setline(1758);
         var3 = var1.getlocal(0).__getattr__("filename");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1761);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(1763);
         var1.getlocal(0).__getattr__("_really_load").__call__(var2, var1.getlocal(4), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(1765);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(1765);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject revert$96(PyFrame var1, ThreadState var2) {
      var1.setline(1774);
      PyString.fromInterned("Clear all cookies and reload cookies from a saved file.\n\n        Raises LoadError (or IOError) if reversion is not successful; the\n        object's state will not be altered if this happens.\n\n        ");
      var1.setline(1775);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1776);
         var3 = var1.getlocal(0).__getattr__("filename");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(1777);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, var1.getglobal("MISSING_FILENAME_TEXT")));
         }

         var1.setline(1776);
         var3 = var1.getlocal(0).__getattr__("filename");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1779);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("acquire").__call__(var2);
      var3 = null;

      try {
         var1.setline(1782);
         PyObject var4 = var1.getglobal("copy").__getattr__("deepcopy").__call__(var2, var1.getlocal(0).__getattr__("_cookies"));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1783);
         PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"_cookies", var8);
         var4 = null;

         try {
            var1.setline(1785);
            var1.getlocal(0).__getattr__("load").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         } catch (Throwable var6) {
            PyException var9 = Py.setException(var6, var1);
            if (var9.match(new PyTuple(new PyObject[]{var1.getglobal("LoadError"), var1.getglobal("IOError")}))) {
               var1.setline(1787);
               PyObject var5 = var1.getlocal(4);
               var1.getlocal(0).__setattr__("_cookies", var5);
               var5 = null;
               var1.setline(1788);
               throw Py.makeException();
            }

            throw var9;
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(1791);
         var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(1791);
      var1.getlocal(0).__getattr__("_cookies_lock").__getattr__("release").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public cookielib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"args", "logging"};
      _debug$1 = Py.newCode(1, var2, var1, "_debug", 43, true, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"warnings", "traceback", "StringIO", "f", "msg"};
      _warn_unhandled_exception$2 = Py.newCode(0, var2, var1, "_warn_unhandled_exception", 57, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tt", "year", "month", "mday", "hour", "min", "sec"};
      _timegm$3 = Py.newCode(1, var2, var1, "_timegm", 72, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t", "year", "mon", "mday", "hour", "min", "sec"};
      time2isoz$4 = Py.newCode(1, var2, var1, "time2isoz", 86, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t", "year", "mon", "mday", "hour", "min", "sec", "wday"};
      time2netscape$5 = Py.newCode(1, var2, var1, "time2netscape", 103, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"tz", "offset", "m"};
      offset_from_tz_string$6 = Py.newCode(1, var2, var1, "offset_from_tz_string", 123, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"day", "mon", "yr", "hr", "min", "sec", "tz", "imon", "cur_yr", "m", "tmp", "t", "offset"};
      _str2time$7 = Py.newCode(7, var2, var1, "_str2time", 137, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "m", "g", "mon", "tt", "day", "yr", "hr", "min", "sec", "tz"};
      http2time$8 = Py.newCode(1, var2, var1, "http2time", 212, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text", "day", "mon", "yr", "hr", "min", "sec", "tz", "m", "_"};
      iso2time$9 = Py.newCode(1, var2, var1, "iso2time", 284, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"match", "start", "end"};
      unmatched$10 = Py.newCode(1, var2, var1, "unmatched", 317, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"header_values", "result", "text", "orig_text", "pairs", "m", "name", "value", "non_junk", "nr_junk_chars"};
      split_header_words$11 = Py.newCode(1, var2, var1, "split_header_words", 326, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"lists", "headers", "pairs", "attr", "k", "v"};
      join_header_words$12 = Py.newCode(1, var2, var1, "join_header_words", 412, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      _strip_quotes$13 = Py.newCode(1, var2, var1, "_strip_quotes", 437, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ns_headers", "known_attrs", "result", "ns_header", "pairs", "version_set", "ii", "param", "k", "v", "lc"};
      parse_ns_headers$14 = Py.newCode(1, var2, var1, "parse_ns_headers", 444, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      is_HDN$15 = Py.newCode(1, var2, var1, "is_HDN", 497, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"A", "B", "i"};
      domain_match$16 = Py.newCode(2, var2, var1, "domain_match", 512, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"text"};
      liberal_is_HDN$17 = Py.newCode(1, var2, var1, "liberal_is_HDN", 551, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"A", "B", "initial_dot"};
      user_domain_match$18 = Py.newCode(2, var2, var1, "user_domain_match", 561, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"request", "url", "host"};
      request_host$19 = Py.newCode(1, var2, var1, "request_host", 582, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"request", "erhn", "req_host"};
      eff_request_host$20 = Py.newCode(1, var2, var1, "eff_request_host", 598, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"request", "url", "parts", "path"};
      request_path$21 = Py.newCode(1, var2, var1, "request_path", 609, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"request", "host", "i", "port"};
      request_port$22 = Py.newCode(1, var2, var1, "request_port", 619, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"match"};
      uppercase_escaped_char$23 = Py.newCode(1, var2, var1, "uppercase_escaped_char", 637, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      escape_path$24 = Py.newCode(1, var2, var1, "escape_path", 639, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"h", "i", "b"};
      reach$25 = Py.newCode(1, var2, var1, "reach", 655, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"request", "req_host"};
      is_third_party$26 = Py.newCode(1, var2, var1, "is_third_party", 690, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Cookie$27 = Py.newCode(0, var2, var1, "Cookie", 707, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "version", "name", "value", "port", "port_specified", "domain", "domain_specified", "domain_initial_dot", "path", "path_specified", "secure", "expires", "discard", "comment", "comment_url", "rest", "rfc2109"};
      __init__$28 = Py.newCode(18, var2, var1, "__init__", 725, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      has_nonstandard_attr$29 = Py.newCode(2, var2, var1, "has_nonstandard_attr", 767, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "default"};
      get_nonstandard_attr$30 = Py.newCode(3, var2, var1, "get_nonstandard_attr", 769, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value"};
      set_nonstandard_attr$31 = Py.newCode(3, var2, var1, "set_nonstandard_attr", 771, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now"};
      is_expired$32 = Py.newCode(2, var2, var1, "is_expired", 774, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "p", "limit", "namevalue"};
      __str__$33 = Py.newCode(1, var2, var1, "__str__", 780, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "name", "attr"};
      __repr__$34 = Py.newCode(1, var2, var1, "__repr__", 790, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CookiePolicy$35 = Py.newCode(0, var2, var1, "CookiePolicy", 805, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "cookie", "request"};
      set_ok$36 = Py.newCode(3, var2, var1, "set_ok", 814, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request"};
      return_ok$37 = Py.newCode(3, var2, var1, "return_ok", 823, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "domain", "request"};
      domain_return_ok$38 = Py.newCode(3, var2, var1, "domain_return_ok", 827, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "request"};
      path_return_ok$39 = Py.newCode(3, var2, var1, "path_return_ok", 832, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DefaultCookiePolicy$40 = Py.newCode(0, var2, var1, "DefaultCookiePolicy", 838, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "blocked_domains", "allowed_domains", "netscape", "rfc2965", "rfc2109_as_netscape", "hide_cookie2", "strict_domain", "strict_rfc2965_unverifiable", "strict_ns_unverifiable", "strict_ns_domain", "strict_ns_set_initial_dollar", "strict_ns_set_path"};
      __init__$41 = Py.newCode(13, var2, var1, "__init__", 848, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      blocked_domains$42 = Py.newCode(1, var2, var1, "blocked_domains", 881, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "blocked_domains"};
      set_blocked_domains$43 = Py.newCode(2, var2, var1, "set_blocked_domains", 884, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "domain", "blocked_domain"};
      is_blocked$44 = Py.newCode(2, var2, var1, "is_blocked", 888, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      allowed_domains$45 = Py.newCode(1, var2, var1, "allowed_domains", 894, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "allowed_domains"};
      set_allowed_domains$46 = Py.newCode(2, var2, var1, "set_allowed_domains", 897, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "domain", "allowed_domain"};
      is_not_allowed$47 = Py.newCode(2, var2, var1, "is_not_allowed", 903, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request", "n", "fn_name", "fn"};
      set_ok$48 = Py.newCode(3, var2, var1, "set_ok", 911, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request"};
      set_ok_version$49 = Py.newCode(3, var2, var1, "set_ok_version", 930, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request"};
      set_ok_verifiability$50 = Py.newCode(3, var2, var1, "set_ok_verifiability", 945, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request"};
      set_ok_name$51 = Py.newCode(3, var2, var1, "set_ok_name", 957, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request", "req_path"};
      set_ok_path$52 = Py.newCode(3, var2, var1, "set_ok_path", 966, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request", "req_host", "erhn", "domain", "i", "j", "tld", "sld", "undotted_domain", "embedded_dots", "host_prefix"};
      set_ok_domain$53 = Py.newCode(3, var2, var1, "set_ok_domain", 977, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request", "req_port", "p"};
      set_ok_port$54 = Py.newCode(3, var2, var1, "set_ok_port", 1036, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request", "n", "fn_name", "fn"};
      return_ok$55 = Py.newCode(3, var2, var1, "return_ok", 1057, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request"};
      return_ok_version$56 = Py.newCode(3, var2, var1, "return_ok_version", 1075, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request"};
      return_ok_verifiability$57 = Py.newCode(3, var2, var1, "return_ok_verifiability", 1084, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request"};
      return_ok_secure$58 = Py.newCode(3, var2, var1, "return_ok_secure", 1096, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request"};
      return_ok_expires$59 = Py.newCode(3, var2, var1, "return_ok_expires", 1102, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request", "req_port", "p"};
      return_ok_port$60 = Py.newCode(3, var2, var1, "return_ok_port", 1108, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request", "req_host", "erhn", "domain"};
      return_ok_domain$61 = Py.newCode(3, var2, var1, "return_ok_domain", 1122, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "domain", "request", "req_host", "erhn"};
      domain_return_ok$62 = Py.newCode(3, var2, var1, "domain_return_ok", 1144, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "request", "req_path"};
      path_return_ok$63 = Py.newCode(3, var2, var1, "path_return_ok", 1166, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"adict", "keys"};
      vals_sorted_by_key$64 = Py.newCode(1, var2, var1, "vals_sorted_by_key", 1175, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"mapping", "values", "obj", "subobj"};
      deepvalues$65 = Py.newCode(1, var2, var1, "deepvalues", 1180, false, false, self, 65, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      Absent$66 = Py.newCode(0, var2, var1, "Absent", 1199, false, false, self, 66, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CookieJar$67 = Py.newCode(0, var2, var1, "CookieJar", 1201, false, false, self, 67, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "policy"};
      __init__$68 = Py.newCode(2, var2, var1, "__init__", 1217, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "policy"};
      set_policy$69 = Py.newCode(2, var2, var1, "set_policy", 1225, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "domain", "request", "cookies", "cookies_by_path", "path", "cookies_by_name", "cookie"};
      _cookies_for_domain$70 = Py.newCode(3, var2, var1, "_cookies_for_domain", 1228, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "cookies", "domain"};
      _cookies_for_request$71 = Py.newCode(2, var2, var1, "_cookies_for_request", 1246, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookies", "version_set", "attrs", "cookie", "version", "value", "domain", "p"};
      _cookie_attrs$72 = Py.newCode(2, var2, var1, "_cookie_attrs", 1253, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"arg"};
      f$73 = Py.newCode(1, var2, var1, "<lambda>", 1263, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "request", "cookies", "attrs", "cookie"};
      add_cookie_header$74 = Py.newCode(2, var2, var1, "add_cookie_header", 1312, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs_set", "cookie_tuples", "boolean_attrs", "value_attrs", "cookie_attrs", "name", "value", "max_age_set", "bad_cookie", "standard", "rest", "k", "v", "lc"};
      _normalized_cookie_tuples$75 = Py.newCode(2, var2, var1, "_normalized_cookie_tuples", 1345, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tup", "request", "name", "value", "standard", "rest", "domain", "path", "port", "expires", "version", "secure", "discard", "comment", "comment_url", "path_specified", "i", "domain_specified", "domain_initial_dot", "req_host", "erhn", "port_specified"};
      _cookie_from_cookie_tuple$76 = Py.newCode(3, var2, var1, "_cookie_from_cookie_tuple", 1442, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs_set", "request", "cookie_tuples", "cookies", "tup", "cookie"};
      _cookies_from_attrs_set$77 = Py.newCode(3, var2, var1, "_cookies_from_attrs_set", 1534, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookies", "rfc2109_as_ns", "cookie"};
      _process_rfc2109_cookies$78 = Py.newCode(2, var2, var1, "_process_rfc2109_cookies", 1543, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "response", "request", "headers", "rfc2965_hdrs", "ns_hdrs", "rfc2965", "netscape", "cookies", "ns_cookies", "lookup", "cookie", "no_matching_rfc2965"};
      make_cookies$79 = Py.newCode(3, var2, var1, "make_cookies", 1555, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ns_cookie", "lookup", "key"};
      no_matching_rfc2965$80 = Py.newCode(2, var2, var1, "no_matching_rfc2965", 1599, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "request"};
      set_cookie_if_ok$81 = Py.newCode(3, var2, var1, "set_cookie_if_ok", 1609, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie", "c", "c2", "c3"};
      set_cookie$82 = Py.newCode(2, var2, var1, "set_cookie", 1622, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "response", "request", "cookie"};
      extract_cookies$83 = Py.newCode(3, var2, var1, "extract_cookies", 1635, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "domain", "path", "name"};
      clear$84 = Py.newCode(4, var2, var1, "clear", 1649, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cookie"};
      clear_session_cookies$85 = Py.newCode(1, var2, var1, "clear_session_cookies", 1676, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "now", "cookie"};
      clear_expired_cookies$86 = Py.newCode(1, var2, var1, "clear_expired_cookies", 1691, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$87 = Py.newCode(1, var2, var1, "__iter__", 1710, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "cookie"};
      __len__$88 = Py.newCode(1, var2, var1, "__len__", 1713, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "r", "cookie"};
      __repr__$89 = Py.newCode(1, var2, var1, "__repr__", 1719, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "r", "cookie"};
      __str__$90 = Py.newCode(1, var2, var1, "__str__", 1724, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LoadError$91 = Py.newCode(0, var2, var1, "LoadError", 1731, false, false, self, 91, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FileCookieJar$92 = Py.newCode(0, var2, var1, "FileCookieJar", 1733, false, false, self, 92, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "delayload", "policy"};
      __init__$93 = Py.newCode(4, var2, var1, "__init__", 1736, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "ignore_discard", "ignore_expires"};
      save$94 = Py.newCode(4, var2, var1, "save", 1751, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "ignore_discard", "ignore_expires", "f"};
      load$95 = Py.newCode(4, var2, var1, "load", 1755, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "ignore_discard", "ignore_expires", "old_state"};
      revert$96 = Py.newCode(4, var2, var1, "revert", 1767, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new cookielib$py("cookielib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(cookielib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._debug$1(var2, var3);
         case 2:
            return this._warn_unhandled_exception$2(var2, var3);
         case 3:
            return this._timegm$3(var2, var3);
         case 4:
            return this.time2isoz$4(var2, var3);
         case 5:
            return this.time2netscape$5(var2, var3);
         case 6:
            return this.offset_from_tz_string$6(var2, var3);
         case 7:
            return this._str2time$7(var2, var3);
         case 8:
            return this.http2time$8(var2, var3);
         case 9:
            return this.iso2time$9(var2, var3);
         case 10:
            return this.unmatched$10(var2, var3);
         case 11:
            return this.split_header_words$11(var2, var3);
         case 12:
            return this.join_header_words$12(var2, var3);
         case 13:
            return this._strip_quotes$13(var2, var3);
         case 14:
            return this.parse_ns_headers$14(var2, var3);
         case 15:
            return this.is_HDN$15(var2, var3);
         case 16:
            return this.domain_match$16(var2, var3);
         case 17:
            return this.liberal_is_HDN$17(var2, var3);
         case 18:
            return this.user_domain_match$18(var2, var3);
         case 19:
            return this.request_host$19(var2, var3);
         case 20:
            return this.eff_request_host$20(var2, var3);
         case 21:
            return this.request_path$21(var2, var3);
         case 22:
            return this.request_port$22(var2, var3);
         case 23:
            return this.uppercase_escaped_char$23(var2, var3);
         case 24:
            return this.escape_path$24(var2, var3);
         case 25:
            return this.reach$25(var2, var3);
         case 26:
            return this.is_third_party$26(var2, var3);
         case 27:
            return this.Cookie$27(var2, var3);
         case 28:
            return this.__init__$28(var2, var3);
         case 29:
            return this.has_nonstandard_attr$29(var2, var3);
         case 30:
            return this.get_nonstandard_attr$30(var2, var3);
         case 31:
            return this.set_nonstandard_attr$31(var2, var3);
         case 32:
            return this.is_expired$32(var2, var3);
         case 33:
            return this.__str__$33(var2, var3);
         case 34:
            return this.__repr__$34(var2, var3);
         case 35:
            return this.CookiePolicy$35(var2, var3);
         case 36:
            return this.set_ok$36(var2, var3);
         case 37:
            return this.return_ok$37(var2, var3);
         case 38:
            return this.domain_return_ok$38(var2, var3);
         case 39:
            return this.path_return_ok$39(var2, var3);
         case 40:
            return this.DefaultCookiePolicy$40(var2, var3);
         case 41:
            return this.__init__$41(var2, var3);
         case 42:
            return this.blocked_domains$42(var2, var3);
         case 43:
            return this.set_blocked_domains$43(var2, var3);
         case 44:
            return this.is_blocked$44(var2, var3);
         case 45:
            return this.allowed_domains$45(var2, var3);
         case 46:
            return this.set_allowed_domains$46(var2, var3);
         case 47:
            return this.is_not_allowed$47(var2, var3);
         case 48:
            return this.set_ok$48(var2, var3);
         case 49:
            return this.set_ok_version$49(var2, var3);
         case 50:
            return this.set_ok_verifiability$50(var2, var3);
         case 51:
            return this.set_ok_name$51(var2, var3);
         case 52:
            return this.set_ok_path$52(var2, var3);
         case 53:
            return this.set_ok_domain$53(var2, var3);
         case 54:
            return this.set_ok_port$54(var2, var3);
         case 55:
            return this.return_ok$55(var2, var3);
         case 56:
            return this.return_ok_version$56(var2, var3);
         case 57:
            return this.return_ok_verifiability$57(var2, var3);
         case 58:
            return this.return_ok_secure$58(var2, var3);
         case 59:
            return this.return_ok_expires$59(var2, var3);
         case 60:
            return this.return_ok_port$60(var2, var3);
         case 61:
            return this.return_ok_domain$61(var2, var3);
         case 62:
            return this.domain_return_ok$62(var2, var3);
         case 63:
            return this.path_return_ok$63(var2, var3);
         case 64:
            return this.vals_sorted_by_key$64(var2, var3);
         case 65:
            return this.deepvalues$65(var2, var3);
         case 66:
            return this.Absent$66(var2, var3);
         case 67:
            return this.CookieJar$67(var2, var3);
         case 68:
            return this.__init__$68(var2, var3);
         case 69:
            return this.set_policy$69(var2, var3);
         case 70:
            return this._cookies_for_domain$70(var2, var3);
         case 71:
            return this._cookies_for_request$71(var2, var3);
         case 72:
            return this._cookie_attrs$72(var2, var3);
         case 73:
            return this.f$73(var2, var3);
         case 74:
            return this.add_cookie_header$74(var2, var3);
         case 75:
            return this._normalized_cookie_tuples$75(var2, var3);
         case 76:
            return this._cookie_from_cookie_tuple$76(var2, var3);
         case 77:
            return this._cookies_from_attrs_set$77(var2, var3);
         case 78:
            return this._process_rfc2109_cookies$78(var2, var3);
         case 79:
            return this.make_cookies$79(var2, var3);
         case 80:
            return this.no_matching_rfc2965$80(var2, var3);
         case 81:
            return this.set_cookie_if_ok$81(var2, var3);
         case 82:
            return this.set_cookie$82(var2, var3);
         case 83:
            return this.extract_cookies$83(var2, var3);
         case 84:
            return this.clear$84(var2, var3);
         case 85:
            return this.clear_session_cookies$85(var2, var3);
         case 86:
            return this.clear_expired_cookies$86(var2, var3);
         case 87:
            return this.__iter__$87(var2, var3);
         case 88:
            return this.__len__$88(var2, var3);
         case 89:
            return this.__repr__$89(var2, var3);
         case 90:
            return this.__str__$90(var2, var3);
         case 91:
            return this.LoadError$91(var2, var3);
         case 92:
            return this.FileCookieJar$92(var2, var3);
         case 93:
            return this.__init__$93(var2, var3);
         case 94:
            return this.save$94(var2, var3);
         case 95:
            return this.load$95(var2, var3);
         case 96:
            return this.revert$96(var2, var3);
         default:
            return null;
      }
   }
}
