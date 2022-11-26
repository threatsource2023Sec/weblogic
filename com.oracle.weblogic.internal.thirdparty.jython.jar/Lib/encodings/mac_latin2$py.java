package encodings;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("encodings/mac_latin2.py")
public class mac_latin2$py extends PyFunctionTable implements PyRunnable {
   static mac_latin2$py self;
   static final PyCode f$0;
   static final PyCode Codec$1;
   static final PyCode encode$2;
   static final PyCode decode$3;
   static final PyCode IncrementalEncoder$4;
   static final PyCode encode$5;
   static final PyCode IncrementalDecoder$6;
   static final PyCode decode$7;
   static final PyCode StreamWriter$8;
   static final PyCode StreamReader$9;
   static final PyCode getregentry$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned(" Python Character Mapping Codec generated from 'LATIN2.TXT' with gencodec.py.\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n(c) Copyright 2000 Guido van Rossum.\n\n"));
      var1.setline(8);
      PyString.fromInterned(" Python Character Mapping Codec generated from 'LATIN2.TXT' with gencodec.py.\n\nWritten by Marc-Andre Lemburg (mal@lemburg.com).\n\n(c) Copyright CNRI, All Rights Reserved. NO WARRANTY.\n(c) Copyright 2000 Guido van Rossum.\n\n");
      var1.setline(10);
      PyObject var3 = imp.importOne("codecs", var1, -1);
      var1.setlocal("codecs", var3);
      var3 = null;
      var1.setline(14);
      PyObject[] var5 = new PyObject[]{var1.getname("codecs").__getattr__("Codec")};
      PyObject var4 = Py.makeClass("Codec", var5, Codec$1);
      var1.setlocal("Codec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(22);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalEncoder")};
      var4 = Py.makeClass("IncrementalEncoder", var5, IncrementalEncoder$4);
      var1.setlocal("IncrementalEncoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(26);
      var5 = new PyObject[]{var1.getname("codecs").__getattr__("IncrementalDecoder")};
      var4 = Py.makeClass("IncrementalDecoder", var5, IncrementalDecoder$6);
      var1.setlocal("IncrementalDecoder", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(30);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamWriter")};
      var4 = Py.makeClass("StreamWriter", var5, StreamWriter$8);
      var1.setlocal("StreamWriter", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(33);
      var5 = new PyObject[]{var1.getname("Codec"), var1.getname("codecs").__getattr__("StreamReader")};
      var4 = Py.makeClass("StreamReader", var5, StreamReader$9);
      var1.setlocal("StreamReader", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(38);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, getregentry$10, (PyObject)null);
      var1.setlocal("getregentry", var6);
      var3 = null;
      var1.setline(51);
      var3 = var1.getname("codecs").__getattr__("make_identity_dict").__call__(var2, var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)));
      var1.setlocal("decoding_map", var3);
      var3 = null;
      var1.setline(52);
      PyObject var10000 = var1.getname("decoding_map").__getattr__("update");
      PyObject[] var10004 = new PyObject[252];
      set$$0(var10004);
      var10000.__call__((ThreadState)var2, (PyObject)(new PyDictionary(var10004)));
      var1.setline(183);
      var3 = var1.getname("codecs").__getattr__("make_encoding_map").__call__(var2, var1.getname("decoding_map"));
      var1.setlocal("encoding_map", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Codec$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("strict")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$2, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      var1.setline(19);
      var3 = new PyObject[]{PyString.fromInterned("strict")};
      var4 = new PyFunction(var1.f_globals, var3, decode$3, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$2(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_encode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("encoding_map"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject decode$3(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_decode").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getglobal("decoding_map"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalEncoder$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(23);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, encode$5, (PyObject)null);
      var1.setlocal("encode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject encode$5(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_encode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getglobal("encoding_map")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IncrementalDecoder$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(27);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, decode$7, (PyObject)null);
      var1.setlocal("decode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject decode$7(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getglobal("codecs").__getattr__("charmap_decode").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("errors"), var1.getglobal("decoding_map")).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject StreamWriter$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(31);
      return var1.getf_locals();
   }

   public PyObject StreamReader$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(34);
      return var1.getf_locals();
   }

   public PyObject getregentry$10(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyObject var10000 = var1.getglobal("codecs").__getattr__("CodecInfo");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("mac-latin2"), var1.getglobal("Codec").__call__(var2).__getattr__("encode"), var1.getglobal("Codec").__call__(var2).__getattr__("decode"), var1.getglobal("IncrementalEncoder"), var1.getglobal("IncrementalDecoder"), var1.getglobal("StreamReader"), var1.getglobal("StreamWriter")};
      String[] var4 = new String[]{"name", "encode", "decode", "incrementalencoder", "incrementaldecoder", "streamreader", "streamwriter"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   private static void set$$0(PyObject[] var0) {
      var0[0] = Py.newInteger(128);
      var0[1] = Py.newInteger(196);
      var0[2] = Py.newInteger(129);
      var0[3] = Py.newInteger(256);
      var0[4] = Py.newInteger(130);
      var0[5] = Py.newInteger(257);
      var0[6] = Py.newInteger(131);
      var0[7] = Py.newInteger(201);
      var0[8] = Py.newInteger(132);
      var0[9] = Py.newInteger(260);
      var0[10] = Py.newInteger(133);
      var0[11] = Py.newInteger(214);
      var0[12] = Py.newInteger(134);
      var0[13] = Py.newInteger(220);
      var0[14] = Py.newInteger(135);
      var0[15] = Py.newInteger(225);
      var0[16] = Py.newInteger(136);
      var0[17] = Py.newInteger(261);
      var0[18] = Py.newInteger(137);
      var0[19] = Py.newInteger(268);
      var0[20] = Py.newInteger(138);
      var0[21] = Py.newInteger(228);
      var0[22] = Py.newInteger(139);
      var0[23] = Py.newInteger(269);
      var0[24] = Py.newInteger(140);
      var0[25] = Py.newInteger(262);
      var0[26] = Py.newInteger(141);
      var0[27] = Py.newInteger(263);
      var0[28] = Py.newInteger(142);
      var0[29] = Py.newInteger(233);
      var0[30] = Py.newInteger(143);
      var0[31] = Py.newInteger(377);
      var0[32] = Py.newInteger(144);
      var0[33] = Py.newInteger(378);
      var0[34] = Py.newInteger(145);
      var0[35] = Py.newInteger(270);
      var0[36] = Py.newInteger(146);
      var0[37] = Py.newInteger(237);
      var0[38] = Py.newInteger(147);
      var0[39] = Py.newInteger(271);
      var0[40] = Py.newInteger(148);
      var0[41] = Py.newInteger(274);
      var0[42] = Py.newInteger(149);
      var0[43] = Py.newInteger(275);
      var0[44] = Py.newInteger(150);
      var0[45] = Py.newInteger(278);
      var0[46] = Py.newInteger(151);
      var0[47] = Py.newInteger(243);
      var0[48] = Py.newInteger(152);
      var0[49] = Py.newInteger(279);
      var0[50] = Py.newInteger(153);
      var0[51] = Py.newInteger(244);
      var0[52] = Py.newInteger(154);
      var0[53] = Py.newInteger(246);
      var0[54] = Py.newInteger(155);
      var0[55] = Py.newInteger(245);
      var0[56] = Py.newInteger(156);
      var0[57] = Py.newInteger(250);
      var0[58] = Py.newInteger(157);
      var0[59] = Py.newInteger(282);
      var0[60] = Py.newInteger(158);
      var0[61] = Py.newInteger(283);
      var0[62] = Py.newInteger(159);
      var0[63] = Py.newInteger(252);
      var0[64] = Py.newInteger(160);
      var0[65] = Py.newInteger(8224);
      var0[66] = Py.newInteger(161);
      var0[67] = Py.newInteger(176);
      var0[68] = Py.newInteger(162);
      var0[69] = Py.newInteger(280);
      var0[70] = Py.newInteger(164);
      var0[71] = Py.newInteger(167);
      var0[72] = Py.newInteger(165);
      var0[73] = Py.newInteger(8226);
      var0[74] = Py.newInteger(166);
      var0[75] = Py.newInteger(182);
      var0[76] = Py.newInteger(167);
      var0[77] = Py.newInteger(223);
      var0[78] = Py.newInteger(168);
      var0[79] = Py.newInteger(174);
      var0[80] = Py.newInteger(170);
      var0[81] = Py.newInteger(8482);
      var0[82] = Py.newInteger(171);
      var0[83] = Py.newInteger(281);
      var0[84] = Py.newInteger(172);
      var0[85] = Py.newInteger(168);
      var0[86] = Py.newInteger(173);
      var0[87] = Py.newInteger(8800);
      var0[88] = Py.newInteger(174);
      var0[89] = Py.newInteger(291);
      var0[90] = Py.newInteger(175);
      var0[91] = Py.newInteger(302);
      var0[92] = Py.newInteger(176);
      var0[93] = Py.newInteger(303);
      var0[94] = Py.newInteger(177);
      var0[95] = Py.newInteger(298);
      var0[96] = Py.newInteger(178);
      var0[97] = Py.newInteger(8804);
      var0[98] = Py.newInteger(179);
      var0[99] = Py.newInteger(8805);
      var0[100] = Py.newInteger(180);
      var0[101] = Py.newInteger(299);
      var0[102] = Py.newInteger(181);
      var0[103] = Py.newInteger(310);
      var0[104] = Py.newInteger(182);
      var0[105] = Py.newInteger(8706);
      var0[106] = Py.newInteger(183);
      var0[107] = Py.newInteger(8721);
      var0[108] = Py.newInteger(184);
      var0[109] = Py.newInteger(322);
      var0[110] = Py.newInteger(185);
      var0[111] = Py.newInteger(315);
      var0[112] = Py.newInteger(186);
      var0[113] = Py.newInteger(316);
      var0[114] = Py.newInteger(187);
      var0[115] = Py.newInteger(317);
      var0[116] = Py.newInteger(188);
      var0[117] = Py.newInteger(318);
      var0[118] = Py.newInteger(189);
      var0[119] = Py.newInteger(313);
      var0[120] = Py.newInteger(190);
      var0[121] = Py.newInteger(314);
      var0[122] = Py.newInteger(191);
      var0[123] = Py.newInteger(325);
      var0[124] = Py.newInteger(192);
      var0[125] = Py.newInteger(326);
      var0[126] = Py.newInteger(193);
      var0[127] = Py.newInteger(323);
      var0[128] = Py.newInteger(194);
      var0[129] = Py.newInteger(172);
      var0[130] = Py.newInteger(195);
      var0[131] = Py.newInteger(8730);
      var0[132] = Py.newInteger(196);
      var0[133] = Py.newInteger(324);
      var0[134] = Py.newInteger(197);
      var0[135] = Py.newInteger(327);
      var0[136] = Py.newInteger(198);
      var0[137] = Py.newInteger(8710);
      var0[138] = Py.newInteger(199);
      var0[139] = Py.newInteger(171);
      var0[140] = Py.newInteger(200);
      var0[141] = Py.newInteger(187);
      var0[142] = Py.newInteger(201);
      var0[143] = Py.newInteger(8230);
      var0[144] = Py.newInteger(202);
      var0[145] = Py.newInteger(160);
      var0[146] = Py.newInteger(203);
      var0[147] = Py.newInteger(328);
      var0[148] = Py.newInteger(204);
      var0[149] = Py.newInteger(336);
      var0[150] = Py.newInteger(205);
      var0[151] = Py.newInteger(213);
      var0[152] = Py.newInteger(206);
      var0[153] = Py.newInteger(337);
      var0[154] = Py.newInteger(207);
      var0[155] = Py.newInteger(332);
      var0[156] = Py.newInteger(208);
      var0[157] = Py.newInteger(8211);
      var0[158] = Py.newInteger(209);
      var0[159] = Py.newInteger(8212);
      var0[160] = Py.newInteger(210);
      var0[161] = Py.newInteger(8220);
      var0[162] = Py.newInteger(211);
      var0[163] = Py.newInteger(8221);
      var0[164] = Py.newInteger(212);
      var0[165] = Py.newInteger(8216);
      var0[166] = Py.newInteger(213);
      var0[167] = Py.newInteger(8217);
      var0[168] = Py.newInteger(214);
      var0[169] = Py.newInteger(247);
      var0[170] = Py.newInteger(215);
      var0[171] = Py.newInteger(9674);
      var0[172] = Py.newInteger(216);
      var0[173] = Py.newInteger(333);
      var0[174] = Py.newInteger(217);
      var0[175] = Py.newInteger(340);
      var0[176] = Py.newInteger(218);
      var0[177] = Py.newInteger(341);
      var0[178] = Py.newInteger(219);
      var0[179] = Py.newInteger(344);
      var0[180] = Py.newInteger(220);
      var0[181] = Py.newInteger(8249);
      var0[182] = Py.newInteger(221);
      var0[183] = Py.newInteger(8250);
      var0[184] = Py.newInteger(222);
      var0[185] = Py.newInteger(345);
      var0[186] = Py.newInteger(223);
      var0[187] = Py.newInteger(342);
      var0[188] = Py.newInteger(224);
      var0[189] = Py.newInteger(343);
      var0[190] = Py.newInteger(225);
      var0[191] = Py.newInteger(352);
      var0[192] = Py.newInteger(226);
      var0[193] = Py.newInteger(8218);
      var0[194] = Py.newInteger(227);
      var0[195] = Py.newInteger(8222);
      var0[196] = Py.newInteger(228);
      var0[197] = Py.newInteger(353);
      var0[198] = Py.newInteger(229);
      var0[199] = Py.newInteger(346);
      var0[200] = Py.newInteger(230);
      var0[201] = Py.newInteger(347);
      var0[202] = Py.newInteger(231);
      var0[203] = Py.newInteger(193);
      var0[204] = Py.newInteger(232);
      var0[205] = Py.newInteger(356);
      var0[206] = Py.newInteger(233);
      var0[207] = Py.newInteger(357);
      var0[208] = Py.newInteger(234);
      var0[209] = Py.newInteger(205);
      var0[210] = Py.newInteger(235);
      var0[211] = Py.newInteger(381);
      var0[212] = Py.newInteger(236);
      var0[213] = Py.newInteger(382);
      var0[214] = Py.newInteger(237);
      var0[215] = Py.newInteger(362);
      var0[216] = Py.newInteger(238);
      var0[217] = Py.newInteger(211);
      var0[218] = Py.newInteger(239);
      var0[219] = Py.newInteger(212);
      var0[220] = Py.newInteger(240);
      var0[221] = Py.newInteger(363);
      var0[222] = Py.newInteger(241);
      var0[223] = Py.newInteger(366);
      var0[224] = Py.newInteger(242);
      var0[225] = Py.newInteger(218);
      var0[226] = Py.newInteger(243);
      var0[227] = Py.newInteger(367);
      var0[228] = Py.newInteger(244);
      var0[229] = Py.newInteger(368);
      var0[230] = Py.newInteger(245);
      var0[231] = Py.newInteger(369);
      var0[232] = Py.newInteger(246);
      var0[233] = Py.newInteger(370);
      var0[234] = Py.newInteger(247);
      var0[235] = Py.newInteger(371);
      var0[236] = Py.newInteger(248);
      var0[237] = Py.newInteger(221);
      var0[238] = Py.newInteger(249);
      var0[239] = Py.newInteger(253);
      var0[240] = Py.newInteger(250);
      var0[241] = Py.newInteger(311);
      var0[242] = Py.newInteger(251);
      var0[243] = Py.newInteger(379);
      var0[244] = Py.newInteger(252);
      var0[245] = Py.newInteger(321);
      var0[246] = Py.newInteger(253);
      var0[247] = Py.newInteger(380);
      var0[248] = Py.newInteger(254);
      var0[249] = Py.newInteger(290);
      var0[250] = Py.newInteger(255);
      var0[251] = Py.newInteger(711);
   }

   public mac_latin2$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Codec$1 = Py.newCode(0, var2, var1, "Codec", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "errors"};
      encode$2 = Py.newCode(3, var2, var1, "encode", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "input", "errors"};
      decode$3 = Py.newCode(3, var2, var1, "decode", 19, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalEncoder$4 = Py.newCode(0, var2, var1, "IncrementalEncoder", 22, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      encode$5 = Py.newCode(3, var2, var1, "encode", 23, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IncrementalDecoder$6 = Py.newCode(0, var2, var1, "IncrementalDecoder", 26, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "input", "final"};
      decode$7 = Py.newCode(3, var2, var1, "decode", 27, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      StreamWriter$8 = Py.newCode(0, var2, var1, "StreamWriter", 30, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      StreamReader$9 = Py.newCode(0, var2, var1, "StreamReader", 33, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getregentry$10 = Py.newCode(0, var2, var1, "getregentry", 38, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new mac_latin2$py("encodings/mac_latin2$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(mac_latin2$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Codec$1(var2, var3);
         case 2:
            return this.encode$2(var2, var3);
         case 3:
            return this.decode$3(var2, var3);
         case 4:
            return this.IncrementalEncoder$4(var2, var3);
         case 5:
            return this.encode$5(var2, var3);
         case 6:
            return this.IncrementalDecoder$6(var2, var3);
         case 7:
            return this.decode$7(var2, var3);
         case 8:
            return this.StreamWriter$8(var2, var3);
         case 9:
            return this.StreamReader$9(var2, var3);
         case 10:
            return this.getregentry$10(var2, var3);
         default:
            return null;
      }
   }
}
