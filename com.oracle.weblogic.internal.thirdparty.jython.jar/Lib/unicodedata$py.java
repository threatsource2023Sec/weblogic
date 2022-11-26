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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("unicodedata.py")
public class unicodedata$py extends PyFunctionTable implements PyRunnable {
   static unicodedata$py self;
   static final PyCode f$0;
   static final PyCode _validate_unichr$1;
   static final PyCode _get_codepoint$2;
   static final PyCode name$3;
   static final PyCode lookup$4;
   static final PyCode digit$5;
   static final PyCode decimal$6;
   static final PyCode numeric$7;
   static final PyCode _get_decomp_type$8;
   static final PyCode decomposition$9;
   static final PyCode f$10;
   static final PyCode category$11;
   static final PyCode bidirectional$12;
   static final PyCode combining$13;
   static final PyCode mirrored$14;
   static final PyCode east_asian_width$15;
   static final PyCode normalize$16;
   static final PyCode get_icu_version$17;
   static final PyCode f$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("java.lang.Character", var1, -1);
      var1.setlocal("java", var3);
      var3 = null;

      String[] var4;
      PyObject[] var9;
      try {
         var1.setline(4);
         String[] var8 = new String[]{"Normalizer"};
         var9 = imp.importFrom("org.python.icu.text", var8, var1, -1);
         PyObject var11 = var9[0];
         var1.setlocal("Normalizer", var11);
         var4 = null;
         var1.setline(5);
         var8 = new String[]{"UCharacter", "UProperty"};
         var9 = imp.importFrom("org.python.icu.lang", var8, var1, -1);
         var11 = var9[0];
         var1.setlocal("UCharacter", var11);
         var4 = null;
         var11 = var9[1];
         var1.setlocal("UProperty", var11);
         var4 = null;
         var1.setline(6);
         var8 = new String[]{"VersionInfo"};
         var9 = imp.importFrom("org.python.icu.util", var8, var1, -1);
         var11 = var9[0];
         var1.setlocal("VersionInfo", var11);
         var4 = null;
         var1.setline(7);
         var8 = new String[]{"EastAsianWidth", "DecompositionType"};
         var9 = imp.importFrom("org.python.icu.lang.UCharacter", var8, var1, -1);
         var11 = var9[0];
         var1.setlocal("EastAsianWidth", var11);
         var4 = null;
         var11 = var9[1];
         var1.setlocal("DecompositionType", var11);
         var4 = null;
         var1.setline(8);
         var8 = new String[]{"ECharacterCategory", "ECharacterDirection"};
         var9 = imp.importFrom("org.python.icu.lang.UCharacterEnums", var8, var1, -1);
         var11 = var9[0];
         var1.setlocal("ECharacterCategory", var11);
         var4 = null;
         var11 = var9[1];
         var1.setlocal("ECharacterDirection", var11);
         var4 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(11);
         var4 = new String[]{"Normalizer"};
         PyObject[] var10 = imp.importFrom("com.ibm.icu.text", var4, var1, -1);
         PyObject var5 = var10[0];
         var1.setlocal("Normalizer", var5);
         var5 = null;
         var1.setline(12);
         var4 = new String[]{"UCharacter", "UProperty"};
         var10 = imp.importFrom("com.ibm.icu.lang", var4, var1, -1);
         var5 = var10[0];
         var1.setlocal("UCharacter", var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal("UProperty", var5);
         var5 = null;
         var1.setline(13);
         var4 = new String[]{"VersionInfo"};
         var10 = imp.importFrom("com.ibm.icu.util", var4, var1, -1);
         var5 = var10[0];
         var1.setlocal("VersionInfo", var5);
         var5 = null;
         var1.setline(14);
         var4 = new String[]{"EastAsianWidth", "DecompositionType"};
         var10 = imp.importFrom("com.ibm.icu.lang.UCharacter", var4, var1, -1);
         var5 = var10[0];
         var1.setlocal("EastAsianWidth", var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal("DecompositionType", var5);
         var5 = null;
         var1.setline(15);
         var4 = new String[]{"ECharacterCategory", "ECharacterDirection"};
         var10 = imp.importFrom("com.ibm.icu.lang.UCharacterEnums", var4, var1, -1);
         var5 = var10[0];
         var1.setlocal("ECharacterCategory", var5);
         var5 = null;
         var5 = var10[1];
         var1.setlocal("ECharacterDirection", var5);
         var5 = null;
      }

      var1.setline(18);
      PyTuple var12 = new PyTuple(new PyObject[]{PyString.fromInterned("bidirectional"), PyString.fromInterned("category"), PyString.fromInterned("combining"), PyString.fromInterned("decimal"), PyString.fromInterned("decomposition"), PyString.fromInterned("digit"), PyString.fromInterned("east_asian_width"), PyString.fromInterned("lookup"), PyString.fromInterned("mirrored"), PyString.fromInterned("name"), PyString.fromInterned("normalize"), PyString.fromInterned("numeric"), PyString.fromInterned("unidata_version")});
      var1.setlocal("__all__", var12);
      var3 = null;
      var1.setline(23);
      PyDictionary var13 = new PyDictionary(new PyObject[]{PyString.fromInterned("NFC"), var1.getname("Normalizer").__getattr__("NFC"), PyString.fromInterned("NFKC"), var1.getname("Normalizer").__getattr__("NFKC"), PyString.fromInterned("NFD"), var1.getname("Normalizer").__getattr__("NFD"), PyString.fromInterned("NFKD"), var1.getname("Normalizer").__getattr__("NFKD")});
      var1.setlocal("_forms", var13);
      var3 = null;
      var1.setline(30);
      var3 = var1.getname("object").__call__(var2);
      var1.setlocal("Nonesuch", var3);
      var3 = null;
      var1.setline(33);
      var9 = Py.EmptyObjects;
      PyFunction var14 = new PyFunction(var1.f_globals, var9, _validate_unichr$1, (PyObject)null);
      var1.setlocal("_validate_unichr", var14);
      var3 = null;
      var1.setline(40);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, _get_codepoint$2, (PyObject)null);
      var1.setlocal("_get_codepoint", var14);
      var3 = null;
      var1.setline(45);
      var9 = new PyObject[]{var1.getname("Nonesuch")};
      var14 = new PyFunction(var1.f_globals, var9, name$3, (PyObject)null);
      var1.setlocal("name", var14);
      var3 = null;
      var1.setline(56);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, lookup$4, (PyObject)null);
      var1.setlocal("lookup", var14);
      var3 = null;
      var1.setline(63);
      var9 = new PyObject[]{var1.getname("Nonesuch")};
      var14 = new PyFunction(var1.f_globals, var9, digit$5, (PyObject)null);
      var1.setlocal("digit", var14);
      var3 = null;
      var1.setline(73);
      var9 = new PyObject[]{var1.getname("Nonesuch")};
      var14 = new PyFunction(var1.f_globals, var9, decimal$6, (PyObject)null);
      var1.setlocal("decimal", var14);
      var3 = null;
      var1.setline(83);
      var9 = new PyObject[]{var1.getname("Nonesuch")};
      var14 = new PyFunction(var1.f_globals, var9, numeric$7, (PyObject)null);
      var1.setlocal("numeric", var14);
      var3 = null;
      var1.setline(93);
      var13 = new PyDictionary(new PyObject[]{var1.getname("DecompositionType").__getattr__("CANONICAL"), PyString.fromInterned("canonical"), var1.getname("DecompositionType").__getattr__("CIRCLE"), PyString.fromInterned("circle"), var1.getname("DecompositionType").__getattr__("COMPAT"), PyString.fromInterned("compat"), var1.getname("DecompositionType").__getattr__("FINAL"), PyString.fromInterned("final"), var1.getname("DecompositionType").__getattr__("FONT"), PyString.fromInterned("font"), var1.getname("DecompositionType").__getattr__("FRACTION"), PyString.fromInterned("fraction"), var1.getname("DecompositionType").__getattr__("INITIAL"), PyString.fromInterned("initial"), var1.getname("DecompositionType").__getattr__("ISOLATED"), PyString.fromInterned("isolated"), var1.getname("DecompositionType").__getattr__("MEDIAL"), PyString.fromInterned("medial"), var1.getname("DecompositionType").__getattr__("NARROW"), PyString.fromInterned("narrow"), var1.getname("DecompositionType").__getattr__("NOBREAK"), PyString.fromInterned("nobreak"), var1.getname("DecompositionType").__getattr__("NONE"), var1.getname("None"), var1.getname("DecompositionType").__getattr__("SMALL"), PyString.fromInterned("small"), var1.getname("DecompositionType").__getattr__("SQUARE"), PyString.fromInterned("square"), var1.getname("DecompositionType").__getattr__("SUB"), PyString.fromInterned("sub"), var1.getname("DecompositionType").__getattr__("SUPER"), PyString.fromInterned("super"), var1.getname("DecompositionType").__getattr__("VERTICAL"), PyString.fromInterned("vertical"), var1.getname("DecompositionType").__getattr__("WIDE"), PyString.fromInterned("wide")});
      var1.setlocal("_decomp", var13);
      var3 = null;
      var1.setline(114);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, _get_decomp_type$8, (PyObject)null);
      var1.setlocal("_get_decomp_type", var14);
      var3 = null;
      var1.setline(122);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, decomposition$9, (PyObject)null);
      var1.setlocal("decomposition", var14);
      var3 = null;
      var1.setline(151);
      var13 = new PyDictionary(new PyObject[]{var1.getname("ECharacterCategory").__getattr__("COMBINING_SPACING_MARK"), PyString.fromInterned("Mc"), var1.getname("ECharacterCategory").__getattr__("CONNECTOR_PUNCTUATION"), PyString.fromInterned("Pc"), var1.getname("ECharacterCategory").__getattr__("CONTROL"), PyString.fromInterned("Cc"), var1.getname("ECharacterCategory").__getattr__("CURRENCY_SYMBOL"), PyString.fromInterned("Sc"), var1.getname("ECharacterCategory").__getattr__("DASH_PUNCTUATION"), PyString.fromInterned("Pd"), var1.getname("ECharacterCategory").__getattr__("DECIMAL_DIGIT_NUMBER"), PyString.fromInterned("Nd"), var1.getname("ECharacterCategory").__getattr__("ENCLOSING_MARK"), PyString.fromInterned("Me"), var1.getname("ECharacterCategory").__getattr__("END_PUNCTUATION"), PyString.fromInterned("Pe"), var1.getname("ECharacterCategory").__getattr__("FINAL_PUNCTUATION"), PyString.fromInterned("Pf"), var1.getname("ECharacterCategory").__getattr__("FORMAT"), PyString.fromInterned("Cf"), var1.getname("ECharacterCategory").__getattr__("GENERAL_OTHER_TYPES"), PyString.fromInterned("Cn Not Assigned"), var1.getname("ECharacterCategory").__getattr__("INITIAL_PUNCTUATION"), PyString.fromInterned("Pi"), var1.getname("ECharacterCategory").__getattr__("LETTER_NUMBER"), PyString.fromInterned("Nl"), var1.getname("ECharacterCategory").__getattr__("LINE_SEPARATOR"), PyString.fromInterned("Zl"), var1.getname("ECharacterCategory").__getattr__("LOWERCASE_LETTER"), PyString.fromInterned("Ll"), var1.getname("ECharacterCategory").__getattr__("MATH_SYMBOL"), PyString.fromInterned("Sm"), var1.getname("ECharacterCategory").__getattr__("MODIFIER_LETTER"), PyString.fromInterned("Lm"), var1.getname("ECharacterCategory").__getattr__("MODIFIER_SYMBOL"), PyString.fromInterned("Sk"), var1.getname("ECharacterCategory").__getattr__("NON_SPACING_MARK"), PyString.fromInterned("Mn"), var1.getname("ECharacterCategory").__getattr__("OTHER_LETTER"), PyString.fromInterned("Lo"), var1.getname("ECharacterCategory").__getattr__("OTHER_NUMBER"), PyString.fromInterned("No"), var1.getname("ECharacterCategory").__getattr__("OTHER_PUNCTUATION"), PyString.fromInterned("Po"), var1.getname("ECharacterCategory").__getattr__("OTHER_SYMBOL"), PyString.fromInterned("So"), var1.getname("ECharacterCategory").__getattr__("PARAGRAPH_SEPARATOR"), PyString.fromInterned("Zp"), var1.getname("ECharacterCategory").__getattr__("PRIVATE_USE"), PyString.fromInterned("Co"), var1.getname("ECharacterCategory").__getattr__("SPACE_SEPARATOR"), PyString.fromInterned("Zs"), var1.getname("ECharacterCategory").__getattr__("START_PUNCTUATION"), PyString.fromInterned("Ps"), var1.getname("ECharacterCategory").__getattr__("SURROGATE"), PyString.fromInterned("Cs"), var1.getname("ECharacterCategory").__getattr__("TITLECASE_LETTER"), PyString.fromInterned("Lt"), var1.getname("ECharacterCategory").__getattr__("UNASSIGNED"), PyString.fromInterned("Cn"), var1.getname("ECharacterCategory").__getattr__("UPPERCASE_LETTER"), PyString.fromInterned("Lu")});
      var1.setlocal("_cat", var13);
      var3 = null;
      var1.setline(187);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, category$11, (PyObject)null);
      var1.setlocal("category", var14);
      var3 = null;
      var1.setline(191);
      var13 = new PyDictionary(new PyObject[]{var1.getname("ECharacterDirection").__getattr__("ARABIC_NUMBER"), PyString.fromInterned("An"), var1.getname("ECharacterDirection").__getattr__("BLOCK_SEPARATOR"), PyString.fromInterned("B"), var1.getname("ECharacterDirection").__getattr__("BOUNDARY_NEUTRAL"), PyString.fromInterned("BN"), var1.getname("ECharacterDirection").__getattr__("COMMON_NUMBER_SEPARATOR"), PyString.fromInterned("CS"), var1.getname("ECharacterDirection").__getattr__("DIR_NON_SPACING_MARK"), PyString.fromInterned("NSM"), var1.getname("ECharacterDirection").__getattr__("EUROPEAN_NUMBER"), PyString.fromInterned("EN"), var1.getname("ECharacterDirection").__getattr__("EUROPEAN_NUMBER_SEPARATOR"), PyString.fromInterned("ES"), var1.getname("ECharacterDirection").__getattr__("EUROPEAN_NUMBER_TERMINATOR"), PyString.fromInterned("ET"), var1.getname("ECharacterDirection").__getattr__("FIRST_STRONG_ISOLATE"), PyString.fromInterned("FSI"), var1.getname("ECharacterDirection").__getattr__("LEFT_TO_RIGHT"), PyString.fromInterned("L"), var1.getname("ECharacterDirection").__getattr__("LEFT_TO_RIGHT_EMBEDDING"), PyString.fromInterned("LRE"), var1.getname("ECharacterDirection").__getattr__("LEFT_TO_RIGHT_ISOLATE"), PyString.fromInterned("LRI"), var1.getname("ECharacterDirection").__getattr__("LEFT_TO_RIGHT_OVERRIDE"), PyString.fromInterned("LRO"), var1.getname("ECharacterDirection").__getattr__("OTHER_NEUTRAL"), PyString.fromInterned("ON"), var1.getname("ECharacterDirection").__getattr__("POP_DIRECTIONAL_FORMAT"), PyString.fromInterned("PDF"), var1.getname("ECharacterDirection").__getattr__("POP_DIRECTIONAL_ISOLATE"), PyString.fromInterned("PDI"), var1.getname("ECharacterDirection").__getattr__("RIGHT_TO_LEFT"), PyString.fromInterned("R"), var1.getname("ECharacterDirection").__getattr__("RIGHT_TO_LEFT_ARABIC"), PyString.fromInterned("AL"), var1.getname("ECharacterDirection").__getattr__("RIGHT_TO_LEFT_EMBEDDING"), PyString.fromInterned("RLE"), var1.getname("ECharacterDirection").__getattr__("RIGHT_TO_LEFT_ISOLATE"), PyString.fromInterned("RLI"), var1.getname("ECharacterDirection").__getattr__("RIGHT_TO_LEFT_OVERRIDE"), PyString.fromInterned("RLO"), var1.getname("ECharacterDirection").__getattr__("SEGMENT_SEPARATOR"), PyString.fromInterned("S"), var1.getname("ECharacterDirection").__getattr__("WHITE_SPACE_NEUTRAL"), PyString.fromInterned("WS")});
      var1.setlocal("_dir", var13);
      var3 = null;
      var1.setline(217);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, bidirectional$12, (PyObject)null);
      var1.setlocal("bidirectional", var14);
      var3 = null;
      var1.setline(221);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, combining$13, (PyObject)null);
      var1.setlocal("combining", var14);
      var3 = null;
      var1.setline(225);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, mirrored$14, (PyObject)null);
      var1.setlocal("mirrored", var14);
      var3 = null;
      var1.setline(229);
      var13 = new PyDictionary(new PyObject[]{var1.getname("EastAsianWidth").__getattr__("AMBIGUOUS"), PyString.fromInterned("A"), var1.getname("EastAsianWidth").__getattr__("COUNT"), PyString.fromInterned("?"), var1.getname("EastAsianWidth").__getattr__("FULLWIDTH"), PyString.fromInterned("F"), var1.getname("EastAsianWidth").__getattr__("HALFWIDTH"), PyString.fromInterned("H"), var1.getname("EastAsianWidth").__getattr__("NARROW"), PyString.fromInterned("Na"), var1.getname("EastAsianWidth").__getattr__("NEUTRAL"), PyString.fromInterned("N"), var1.getname("EastAsianWidth").__getattr__("WIDE"), PyString.fromInterned("W")});
      var1.setlocal("_eaw", var13);
      var3 = null;
      var1.setline(240);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, east_asian_width$15, (PyObject)null);
      var1.setlocal("east_asian_width", var14);
      var3 = null;
      var1.setline(244);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, normalize$16, PyString.fromInterned("\n    Return the normal form 'form' for the Unicode string unistr.  Valid\n    values for form are 'NFC', 'NFKC', 'NFD', and 'NFKD'.\n    "));
      var1.setlocal("normalize", var14);
      var3 = null;
      var1.setline(258);
      var9 = Py.EmptyObjects;
      var14 = new PyFunction(var1.f_globals, var9, get_icu_version$17, (PyObject)null);
      var1.setlocal("get_icu_version", var14);
      var3 = null;
      var1.setline(267);
      var3 = var1.getname("get_icu_version").__call__(var2);
      var1.setlocal("unidata_version", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _validate_unichr$1(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__not__().__nonzero__()) {
         var1.setline(35);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("must be unicode, not {}").__getattr__("format").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(0)).__getattr__("__name__"))));
      } else {
         var1.setline(36);
         PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         PyObject var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(37);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("need a single Unicode character as parameter")));
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _get_codepoint$2(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      var1.getglobal("_validate_unichr").__call__(var2, var1.getlocal(0));
      var1.setline(42);
      PyObject var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject name$3(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getglobal("UCharacter").__getattr__("getName").__call__(var2, var1.getglobal("_get_codepoint").__call__(var2, var1.getlocal(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(49);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("Nonesuch"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(50);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(52);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no such name")));
         }
      } else {
         var1.setline(53);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject lookup$4(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyObject var3 = var1.getglobal("UCharacter").__getattr__("getCharFromName").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(59);
         throw Py.makeException(var1.getglobal("KeyError").__call__(var2, PyString.fromInterned("undefined character name '{}").__getattr__("format").__call__(var2, var1.getlocal(0))));
      } else {
         var1.setline(60);
         var3 = var1.getglobal("unichr").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject digit$5(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getglobal("UCharacter").__getattr__("digit").__call__(var2, var1.getglobal("_get_codepoint").__call__(var2, var1.getlocal(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(66);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("Nonesuch"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(67);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(69);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not a digit")));
         }
      } else {
         var1.setline(70);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject decimal$6(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getglobal("UCharacter").__getattr__("getNumericValue").__call__(var2, var1.getglobal("_get_codepoint").__call__(var2, var1.getlocal(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._gt(Py.newInteger(9));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(76);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("Nonesuch"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(77);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(79);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not a decimal")));
         }
      } else {
         var1.setline(80);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject numeric$7(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      PyObject var3 = var1.getglobal("UCharacter").__getattr__("getUnicodeNumericValue").__call__(var2, var1.getglobal("_get_codepoint").__call__(var2, var1.getlocal(0)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(85);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(var1.getglobal("UCharacter").__getattr__("NO_NUMERIC_VALUE"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(86);
         var3 = var1.getlocal(1);
         var10000 = var3._isnot(var1.getglobal("Nonesuch"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(87);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(89);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("not a numeric")));
         }
      } else {
         var1.setline(90);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _get_decomp_type$8(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(PyUnicode.fromInterned("⁄"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(118);
         PyString var4 = PyString.fromInterned("fraction");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(120);
         var3 = var1.getglobal("_decomp").__getitem__(var1.getglobal("UCharacter").__getattr__("getIntPropertyValue").__call__(var2, var1.getglobal("ord").__call__(var2, var1.getlocal(0)), var1.getglobal("UProperty").__getattr__("DECOMPOSITION_TYPE")));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject decomposition$9(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      var1.getglobal("_validate_unichr").__call__(var2, var1.getlocal(0));
      var1.setline(124);
      PyObject var3 = var1.getglobal("Normalizer").__getattr__("decompose").__call__(var2, var1.getlocal(0), var1.getglobal("True"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(125);
      var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(127);
         var3 = var1.getglobal("_get_decomp_type").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(129);
         var3 = var1.getlocal(1).__iter__();

         do {
            var1.setline(129);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(130);
            PyObject var5 = var1.getglobal("_get_decomp_type").__call__(var2, var1.getlocal(3));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(132);
            var5 = var1.getlocal(2);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
         } while(!var10000.__nonzero__());
      }

      var1.setline(134);
      var10000 = PyString.fromInterned(" ").__getattr__("join");
      var1.setline(134);
      PyObject[] var7 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var7, f$10, (PyObject)null);
      PyObject var10002 = var6.__call__(var2, var1.getlocal(1).__iter__());
      Arrays.fill(var7, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(135);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(136);
         var3 = PyString.fromInterned("<{}> {}").__getattr__("format").__call__(var2, var1.getlocal(2), var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(137);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(138);
            PyString var8 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var8;
         } else {
            var1.setline(140);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject f$10(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(134);
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

      var1.setline(134);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(134);
         var1.setline(134);
         var6 = PyString.fromInterned("{0:04X}").__getattr__("format").__call__(var2, var1.getglobal("ord").__call__(var2, var1.getlocal(1)));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject category$11(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyObject var3 = var1.getglobal("_cat").__getitem__(var1.getglobal("UCharacter").__getattr__("getType").__call__(var2, var1.getglobal("_get_codepoint").__call__(var2, var1.getlocal(0))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject bidirectional$12(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyObject var3 = var1.getglobal("_dir").__getitem__(var1.getglobal("UCharacter").__getattr__("getDirection").__call__(var2, var1.getglobal("_get_codepoint").__call__(var2, var1.getlocal(0))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject combining$13(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyObject var3 = var1.getglobal("UCharacter").__getattr__("getCombiningClass").__call__(var2, var1.getglobal("_get_codepoint").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mirrored$14(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyObject var3 = var1.getglobal("UCharacter").__getattr__("isMirrored").__call__(var2, var1.getglobal("_get_codepoint").__call__(var2, var1.getlocal(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject east_asian_width$15(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyObject var3 = var1.getglobal("_eaw").__getitem__(var1.getglobal("UCharacter").__getattr__("getIntPropertyValue").__call__(var2, var1.getglobal("_get_codepoint").__call__(var2, var1.getlocal(0)), var1.getglobal("UProperty").__getattr__("EAST_ASIAN_WIDTH")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject normalize$16(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("\n    Return the normal form 'form' for the Unicode string unistr.  Valid\n    values for form are 'NFC', 'NFKC', 'NFD', and 'NFKD'.\n    ");

      PyException var3;
      PyObject var5;
      try {
         var1.setline(251);
         var5 = var1.getglobal("_forms").__getitem__(var1.getlocal(0));
         var1.setlocal(2, var5);
         var3 = null;
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (var3.match(var1.getglobal("KeyError"))) {
            var1.setline(253);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("invalid normalization form")));
         }

         throw var3;
      }

      var1.setline(255);
      var5 = var1.getglobal("Normalizer").__getattr__("normalize").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject get_icu_version$17(PyFrame var1, ThreadState var2) {
      var1.setline(259);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(260);
      PyObject var6 = var1.getglobal("VersionInfo").__getattr__("__dict__").__getattr__("iterkeys").__call__(var2).__iter__();

      while(true) {
         var1.setline(260);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(264);
            PyObject var10000 = PyString.fromInterned(".").__getattr__("join");
            var1.setline(264);
            PyObject[] var7 = Py.EmptyObjects;
            PyFunction var8 = new PyFunction(var1.f_globals, var7, f$18, (PyObject)null);
            PyObject var10002 = var8.__call__(var2, var1.getglobal("max").__call__(var2, var1.getlocal(0)).__iter__());
            Arrays.fill(var7, (Object)null);
            var6 = var10000.__call__(var2, var10002);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(1, var4);
         var1.setline(261);
         if (var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNICODE_")).__nonzero__()) {
            var1.setline(262);
            PyObject var5 = var1.getglobal("getattr").__call__(var2, var1.getglobal("VersionInfo"), var1.getlocal(1));
            var1.setlocal(2, var5);
            var5 = null;
            var1.setline(263);
            var1.getlocal(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("getMajor").__call__(var2), var1.getlocal(2).__getattr__("getMinor").__call__(var2), var1.getlocal(2).__getattr__("getMilli").__call__(var2)})));
         }
      }
   }

   public PyObject f$18(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(264);
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

      var1.setline(264);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(264);
         var1.setline(264);
         var6 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public unicodedata$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"unichr"};
      _validate_unichr$1 = Py.newCode(1, var2, var1, "_validate_unichr", 33, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr"};
      _get_codepoint$2 = Py.newCode(1, var2, var1, "_get_codepoint", 40, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr", "default", "n"};
      name$3 = Py.newCode(2, var2, var1, "name", 45, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "codepoint"};
      lookup$4 = Py.newCode(1, var2, var1, "lookup", 56, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr", "default", "d"};
      digit$5 = Py.newCode(2, var2, var1, "digit", 63, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr", "default", "d"};
      decimal$6 = Py.newCode(2, var2, var1, "decimal", 73, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr", "default", "n"};
      numeric$7 = Py.newCode(2, var2, var1, "numeric", 83, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr"};
      _get_decomp_type$8 = Py.newCode(1, var2, var1, "_get_decomp_type", 114, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr", "d", "decomp_type", "c", "hexed", "_(134_22)"};
      decomposition$9 = Py.newCode(1, var2, var1, "decomposition", 122, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "c"};
      f$10 = Py.newCode(1, var2, var1, "<genexpr>", 134, false, false, self, 10, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"unichr"};
      category$11 = Py.newCode(1, var2, var1, "category", 187, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr"};
      bidirectional$12 = Py.newCode(1, var2, var1, "bidirectional", 217, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr"};
      combining$13 = Py.newCode(1, var2, var1, "combining", 221, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr"};
      mirrored$14 = Py.newCode(1, var2, var1, "mirrored", 225, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"unichr"};
      east_asian_width$15 = Py.newCode(1, var2, var1, "east_asian_width", 240, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"form", "unistr", "normalizer_form"};
      normalize$16 = Py.newCode(2, var2, var1, "normalize", 244, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"versions", "k", "v", "_(264_20)"};
      get_icu_version$17 = Py.newCode(0, var2, var1, "get_icu_version", 258, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "x"};
      f$18 = Py.newCode(1, var2, var1, "<genexpr>", 264, false, false, self, 18, (String[])null, (String[])null, 0, 4129);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new unicodedata$py("unicodedata$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(unicodedata$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._validate_unichr$1(var2, var3);
         case 2:
            return this._get_codepoint$2(var2, var3);
         case 3:
            return this.name$3(var2, var3);
         case 4:
            return this.lookup$4(var2, var3);
         case 5:
            return this.digit$5(var2, var3);
         case 6:
            return this.decimal$6(var2, var3);
         case 7:
            return this.numeric$7(var2, var3);
         case 8:
            return this._get_decomp_type$8(var2, var3);
         case 9:
            return this.decomposition$9(var2, var3);
         case 10:
            return this.f$10(var2, var3);
         case 11:
            return this.category$11(var2, var3);
         case 12:
            return this.bidirectional$12(var2, var3);
         case 13:
            return this.combining$13(var2, var3);
         case 14:
            return this.mirrored$14(var2, var3);
         case 15:
            return this.east_asian_width$15(var2, var3);
         case 16:
            return this.normalize$16(var2, var3);
         case 17:
            return this.get_icu_version$17(var2, var3);
         case 18:
            return this.f$18(var2, var3);
         default:
            return null;
      }
   }
}
