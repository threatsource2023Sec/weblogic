package com.bea.xbean.piccolo.xml;

import com.bea.xbean.piccolo.io.FileFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

final class XMLDeclParser {
   public static final int YYEOF = -1;
   private static final int YY_BUFFERSIZE = 0;
   public static final int GOT_ENCODING = 5;
   public static final int ENCODING = 4;
   public static final int XML_DECL = 1;
   public static final int YYINITIAL = 0;
   public static final int STANDALONE = 6;
   public static final int GOT_VERSION = 3;
   public static final int VERSION = 2;
   public static final int GOT_STANDALONE = 7;
   private static final String yycmap_packed = "\t\u0000\u0001\u0001\u0001\u0001\u0002\u0000\u0001\u0001\u0012\u0000\u0001\u0001\u0001\u0000\u0001\u0012\u0004\u0000\u0001\u0016\u0005\u0000\u0001 \u0001\u0014\u0001\u0000\u0001\u0015\u0001\u0013\u0003\u0005\u0001(\u0001&\u0001\u0005\u0001!\u0001)\u0001\u0003\u0001\u0000\u0001\u0006\u0001\u0002\u0001\u001c\u0001\u0007\u0001\u0000\u0001#\u0001\u0004\u0001$\u0002\u0004\u0001\u001f\u0002\u0004\u0001%\u0005\u0004\u0001'\u0003\u0004\u0001\"\u0001\u001e\u0001\u001d\u0005\u0004\u0004\u0000\u0001\u0005\u0001\u0000\u0001\u001b\u0001\u0004\u0001\u0017\u0001\u0018\u0001\f\u0001\u0004\u0001\u0019\u0001\u0004\u0001\u000f\u0002\u0004\u0001\n\u0001\t\u0001\u0011\u0001\u0010\u0002\u0004\u0001\r\u0001\u000e\u0001\u001a\u0001\u0004\u0001\u000b\u0001\u0004\u0001\b\u0001*\u0001\u0004ﾅ\u0000";
   private static final char[] yycmap = yy_unpack_cmap("\t\u0000\u0001\u0001\u0001\u0001\u0002\u0000\u0001\u0001\u0012\u0000\u0001\u0001\u0001\u0000\u0001\u0012\u0004\u0000\u0001\u0016\u0005\u0000\u0001 \u0001\u0014\u0001\u0000\u0001\u0015\u0001\u0013\u0003\u0005\u0001(\u0001&\u0001\u0005\u0001!\u0001)\u0001\u0003\u0001\u0000\u0001\u0006\u0001\u0002\u0001\u001c\u0001\u0007\u0001\u0000\u0001#\u0001\u0004\u0001$\u0002\u0004\u0001\u001f\u0002\u0004\u0001%\u0005\u0004\u0001'\u0003\u0004\u0001\"\u0001\u001e\u0001\u001d\u0005\u0004\u0004\u0000\u0001\u0005\u0001\u0000\u0001\u001b\u0001\u0004\u0001\u0017\u0001\u0018\u0001\f\u0001\u0004\u0001\u0019\u0001\u0004\u0001\u000f\u0002\u0004\u0001\n\u0001\t\u0001\u0011\u0001\u0010\u0002\u0004\u0001\r\u0001\u000e\u0001\u001a\u0001\u0004\u0001\u000b\u0001\u0004\u0001\b\u0001*\u0001\u0004ﾅ\u0000");
   private static final int[] yy_rowMap = new int[]{0, 43, 86, 129, 172, 215, 258, 301, 344, 387, 344, 430, 473, 516, 559, 602, 645, 688, 731, 774, 817, 860, 903, 430, 473, 946, 989, 1032, 344, 1075, 1118, 1161, 1204, 602, 1247, 1290, 1333, 1376, 1419, 1462, 1505, 1548, 731, 1591, 1634, 1677, 1720, 860, 1763, 1806, 1849, 1892, 344, 1935, 1978, 344, 2021, 2064, 2107, 2150, 2193, 2236, 2279, 2322, 2365, 2408, 2451, 2494, 2537, 2580, 2623, 2666, 2709, 2752, 2795, 2838, 2881, 2924, 2967, 3010, 3053, 3096, 344, 3139, 3182, 3225, 3268, 3311, 3354, 344, 3397, 3440, 3483, 3526, 3569, 3612, 3655, 3698, 3741, 3784, 344, 3225, 3827, 3870, 3913, 3956, 344, 3999, 4042, 4085, 4128, 4171, 4214, 4257, 4300, 344, 344, 4343, 4386, 4429, 4472, 4515, 4558, 4601, 4644, 4687, 4730, 4773, 4816, 4859, 4902, 4945, 4988, 5031, 5074, 5117, 5160, 344};
   private static final String yy_packed0 = "\u0006\t\u0001\n$\t\u0001\u000b\u0001\f\u0005\u000b\u0001\r5\u000b\u0001\u000e\u0003\u000b\u0001\u000f\u0015\u000b\u0001\u0010\u0005\u000b\u0001\r5\u000b\u0001\u0011\u0003\u000b\u0001\u0012\u0015\u000b\u0001\u0013\u0005\u000b\u0001\r5\u000b\u0001\u0014\u0003\u000b\u0001\u0015\u0015\u000b\u0001\u0016\u0005\u000b\u0001\r#\u000b2\u0000\u0001\u0017$\u0000\u0001\u0018\u0005\u0000\u0001\u0019\u0003\u0000\u0001\u001a\u0001\u001b\u0001\u0000\u0001\u001c8\u0000\u0001\u001d\u0011\u0000\u0003\u001e\u0002\u0000\n\u001e\u0001\u0000\u0001\u001f\u0002\u001e\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0001!\u0002 \u0001\u0000\u0005 \u0001\u0000\u000e \u0001\u0000\u0001\"\u0005\u0000\u0001\u0019\u0004\u0000\u0001\u001b\u0001\u0000\u0001\u001c \u0000\u0001#\u0003\u0000\n#\u0005\u0000\u0005#\u0001\u0000\u0001$\u0002#\u0002\u0000\u0001#\u0001%\u0001#\u0001&\u0001\u0000\u0001#\u0002\u0000\u0001#\u0004\u0000\u0001'\u0003\u0000\n'\u0005\u0000\u0005'\u0001\u0000\u0001(\u0002'\u0002\u0000\u0001'\u0001)\u0001'\u0001*\u0001\u0000\u0001'\u0002\u0000\u0001'\u0001\u0000\u0001+\u0005\u0000\u0001\u0019\u0006\u0000\u0001\u001c-\u0000\u0001,\u0018\u0000\u0001-\u0011\u0000\u0001.\u0018\u0000\u0001/\u0001\u0000\u00010\u0005\u0000\u0001\u0019+\u0000\u00011.\u0000\u00012/\u0000\u000133\u0000\u00014\u0013\u0000\u0003\u001e\u0002\u0000\n\u001e\u00015\u0003\u001e\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003\u001e\u0002\u0000\n\u001e\u00015\u0001\u001e\u00016\u0001\u001e\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0003 \u00015\u0005 \u0001\u0000\u000e \u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0001 \u00017\u0001 \u00015\u0005 \u0001\u0000\u000e \u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0001#\u00019\u0003#\u0001:\b#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0005#\u0001;\b#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0005#\u0001<\b#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u000e'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0001'\u0001=\u0003'\u0001>\b'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0005'\u0001?\b'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0005'\u0001@\b'\u0010\u0000\u0001A&\u0000\u0001B.\u0000\u0001C&\u0000\u0001D'\u0000\u0001E.\u0000\u0001F4\u0000\u0001G.\u0000\u0001H\u0012\u0000\u0003\u001e\u0002\u0000\n\u001e\u00015\u0002\u001e\u0001I\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0002 \u0001J\u00015\u0005 \u0001\u0000\u000e \u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0002#\u0001K\u000b#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0003#\u0001L\n#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0007#\u0001M\u0006#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\n#\u0001N\u0003#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0002'\u0001O\u000b'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0003'\u0001P\n'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0007'\u0001Q\u0006'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\n'\u0001R\u0003'\u0012\u0000\u0001S&\u0000\u0001T2\u0000\u0001S\"\u0000\u0001U&\u0000\u0001V.\u0000\u0001W,\u0000\u0001X+\u0000\u0001Y\u001c\u0000\u0003\u001e\u0002\u0000\n\u001e\u0001Z\u0003\u001e\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0003 \u0001Z\u0005 \u0001\u0000\u000e \u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0001[\u0002#\u0001\u0000\u0005#\u0001\u0000\u0003#\u0001\\\u0001]\t#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0006#\u0001%\u0007#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\b#\u0001^\u0005#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0003#\u0001_\n#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0001`\u0002'\u00018\u0005'\u0001\u0000\u0003'\u0001a\u0001b\t'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0006'\u0001)\u0007'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\b'\u0001c\u0005'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0003'\u0001d\n'\u0012\u0000\u0001e.\u0000\u0001e\u0015\u0000\u0001f8\u0000\u0001g3\u0000\u0001h*\u0000\u0001i\u0016\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\t#\u0001j\u0004#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0001[\u0002#\u0001\u0000\u0005#\u0001\u0000\u0004#\u0001]\t#\u0004\u0000\u0002#\u0002\u0000\n#\u0001k\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\b#\u0001l\u0005#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0004#\u0001m\t#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\t'\u0001n\u0004'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0001`\u0002'\u00018\u0005'\u0001\u0000\u0004'\u0001b\t'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u0001k\u0005'\u0001\u0000\u000e'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\b'\u0001o\u0005'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0004'\u0001p\t'\u0010\u0000\u0001q)\u0000\u0001r6\u0000\u0001s\u0013\u0000\u0002#\u0002\u0000\n#\u0001t\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002#\u0002\u0000\n#\u0001u\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0004#\u0001v\t#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u0001t\u0005'\u0001\u0000\u000e'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u0001u\u0005'\u0001\u0000\u000e'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0004'\u0001w\t'\u0011\u0000\u0001x*\u0000\u0001y#\u0000\u0001z$\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u000b#\u0001{\u0002#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u000b'\u0001|\u0002'\u0001\u0000\u0001x\u0001}A\u0000\u0001~!\u0000\u0001\u007f\u001e\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\f#\u0001\u0080\u0001#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\f'\u0001\u0081\u0001'\u0001\u0000\u0001}*\u0000\u0001~\u0001\u00829\u0000\u0001\u0083\u001d\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0003#\u0001\u0084\n#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0003'\u0001\u0085\n'\u0001\u0000\u0001\u00825\u0000\u0001\u0086\"\u0000\u0002#\u0002\u0000\n#\u00018\u0001\u0087\u0002#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0001\u0088\u0002'\u00018\u0005'\u0001\u0000\u000e'\u0001\u0000\u0001\u0086\u0001\u0089,\u0000\u0002#\u0002\u0000\n#\u0001\u008a\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u0001\u008a\u0005'\u0001\u0000\u000e'\u0001\u0000\u0001\u0089)\u0000";
   private static final int[] yytrans = yy_unpack();
   private static final int YY_UNKNOWN_ERROR = 0;
   private static final int YY_ILLEGAL_STATE = 1;
   private static final int YY_NO_MATCH = 2;
   private static final int YY_PUSHBACK_2BIG = 3;
   private static final int YY_SKIP_2BIG = 4;
   private static final String[] YY_ERROR_MSG = new String[]{"Unkown internal scanner error", "Internal error: unknown state", "Error: could not match input", "Error: pushback value was too large", "Error: skip value was too large"};
   private static final byte[] YY_ATTRIBUTE = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 9, 1, 9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 2, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 5, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 9};
   private Reader yy_reader;
   private int yy_state;
   private int yy_lexical_state;
   private char[] yy_buffer;
   private char[] yy_saved_buffer;
   private int yy_markedPos;
   private int yy_pushbackPos;
   private int yy_currentPos;
   private int yy_startRead;
   private int yy_endRead;
   private int yyline;
   private int yychar;
   private int yycolumn;
   private boolean yy_atBOL;
   private boolean yy_atEOF;
   public static final int SUCCESS = 1;
   public static final int NO_DECLARATION = -1;
   private String xmlVersion;
   private String xmlEncoding;
   private boolean xmlStandalone;
   private boolean xmlStandaloneDeclared;
   private int yy_currentPos_l;
   private int yy_startRead_l;
   private int yy_markedPos_l;
   private int yy_endRead_l;
   private char[] yy_buffer_l;
   private char[] yycmap_l;
   private boolean yy_sawCR;
   private boolean yy_prev_sawCR;
   private int yyline_next;
   private int yycolumn_next;

   public XMLDeclParser(char[] buf, int off, int len) throws IOException {
      this.yy_lexical_state = 0;
      this.yy_buffer = new char[0];
      this.yy_saved_buffer = null;
      this.yy_atBOL = true;
      this.xmlVersion = null;
      this.xmlEncoding = null;
      this.xmlStandalone = false;
      this.xmlStandaloneDeclared = false;
      this.yy_sawCR = false;
      this.yy_prev_sawCR = false;
      this.yyline_next = 0;
      this.yycolumn_next = 0;
      this.yyreset(buf, off, len);
   }

   public XMLDeclParser() {
      this.yy_lexical_state = 0;
      this.yy_buffer = new char[0];
      this.yy_saved_buffer = null;
      this.yy_atBOL = true;
      this.xmlVersion = null;
      this.xmlEncoding = null;
      this.xmlStandalone = false;
      this.xmlStandaloneDeclared = false;
      this.yy_sawCR = false;
      this.yy_prev_sawCR = false;
      this.yyline_next = 0;
      this.yycolumn_next = 0;
   }

   public void reset(char[] buf, int off, int len) throws IOException {
      this.xmlVersion = this.xmlEncoding = null;
      this.xmlStandaloneDeclared = this.xmlStandalone = false;
      this.yyreset(buf, off, len);
   }

   public String getXMLVersion() {
      return this.xmlVersion;
   }

   public String getXMLEncoding() {
      return this.xmlEncoding;
   }

   public boolean isXMLStandaloneDeclared() {
      return this.xmlStandaloneDeclared;
   }

   public boolean isXMLStandalone() {
      return this.xmlStandalone;
   }

   public int getCharsRead() {
      return this.yychar + this.yylength();
   }

   XMLDeclParser(Reader in) {
      this.yy_lexical_state = 0;
      this.yy_buffer = new char[0];
      this.yy_saved_buffer = null;
      this.yy_atBOL = true;
      this.xmlVersion = null;
      this.xmlEncoding = null;
      this.xmlStandalone = false;
      this.xmlStandaloneDeclared = false;
      this.yy_sawCR = false;
      this.yy_prev_sawCR = false;
      this.yyline_next = 0;
      this.yycolumn_next = 0;
      this.yy_reader = in;
   }

   XMLDeclParser(InputStream in) {
      this((Reader)(new InputStreamReader(in)));
   }

   private static int[] yy_unpack() {
      int[] trans = new int[5203];
      int offset = 0;
      yy_unpack("\u0006\t\u0001\n$\t\u0001\u000b\u0001\f\u0005\u000b\u0001\r5\u000b\u0001\u000e\u0003\u000b\u0001\u000f\u0015\u000b\u0001\u0010\u0005\u000b\u0001\r5\u000b\u0001\u0011\u0003\u000b\u0001\u0012\u0015\u000b\u0001\u0013\u0005\u000b\u0001\r5\u000b\u0001\u0014\u0003\u000b\u0001\u0015\u0015\u000b\u0001\u0016\u0005\u000b\u0001\r#\u000b2\u0000\u0001\u0017$\u0000\u0001\u0018\u0005\u0000\u0001\u0019\u0003\u0000\u0001\u001a\u0001\u001b\u0001\u0000\u0001\u001c8\u0000\u0001\u001d\u0011\u0000\u0003\u001e\u0002\u0000\n\u001e\u0001\u0000\u0001\u001f\u0002\u001e\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0001!\u0002 \u0001\u0000\u0005 \u0001\u0000\u000e \u0001\u0000\u0001\"\u0005\u0000\u0001\u0019\u0004\u0000\u0001\u001b\u0001\u0000\u0001\u001c \u0000\u0001#\u0003\u0000\n#\u0005\u0000\u0005#\u0001\u0000\u0001$\u0002#\u0002\u0000\u0001#\u0001%\u0001#\u0001&\u0001\u0000\u0001#\u0002\u0000\u0001#\u0004\u0000\u0001'\u0003\u0000\n'\u0005\u0000\u0005'\u0001\u0000\u0001(\u0002'\u0002\u0000\u0001'\u0001)\u0001'\u0001*\u0001\u0000\u0001'\u0002\u0000\u0001'\u0001\u0000\u0001+\u0005\u0000\u0001\u0019\u0006\u0000\u0001\u001c-\u0000\u0001,\u0018\u0000\u0001-\u0011\u0000\u0001.\u0018\u0000\u0001/\u0001\u0000\u00010\u0005\u0000\u0001\u0019+\u0000\u00011.\u0000\u00012/\u0000\u000133\u0000\u00014\u0013\u0000\u0003\u001e\u0002\u0000\n\u001e\u00015\u0003\u001e\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003\u001e\u0002\u0000\n\u001e\u00015\u0001\u001e\u00016\u0001\u001e\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0003 \u00015\u0005 \u0001\u0000\u000e \u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0001 \u00017\u0001 \u00015\u0005 \u0001\u0000\u000e \u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0001#\u00019\u0003#\u0001:\b#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0005#\u0001;\b#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0005#\u0001<\b#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u000e'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0001'\u0001=\u0003'\u0001>\b'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0005'\u0001?\b'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0005'\u0001@\b'\u0010\u0000\u0001A&\u0000\u0001B.\u0000\u0001C&\u0000\u0001D'\u0000\u0001E.\u0000\u0001F4\u0000\u0001G.\u0000\u0001H\u0012\u0000\u0003\u001e\u0002\u0000\n\u001e\u00015\u0002\u001e\u0001I\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0002 \u0001J\u00015\u0005 \u0001\u0000\u000e \u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0002#\u0001K\u000b#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0003#\u0001L\n#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0007#\u0001M\u0006#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\n#\u0001N\u0003#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0002'\u0001O\u000b'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0003'\u0001P\n'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0007'\u0001Q\u0006'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\n'\u0001R\u0003'\u0012\u0000\u0001S&\u0000\u0001T2\u0000\u0001S\"\u0000\u0001U&\u0000\u0001V.\u0000\u0001W,\u0000\u0001X+\u0000\u0001Y\u001c\u0000\u0003\u001e\u0002\u0000\n\u001e\u0001Z\u0003\u001e\u0001\u0000\u0005\u001e\u0001\u0000\u000e\u001e\u0003\u0000\u0003 \u0002\u0000\n \u0001\u0000\u0003 \u0001Z\u0005 \u0001\u0000\u000e \u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0001[\u0002#\u0001\u0000\u0005#\u0001\u0000\u0003#\u0001\\\u0001]\t#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0006#\u0001%\u0007#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\b#\u0001^\u0005#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0003#\u0001_\n#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0001`\u0002'\u00018\u0005'\u0001\u0000\u0003'\u0001a\u0001b\t'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0006'\u0001)\u0007'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\b'\u0001c\u0005'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0003'\u0001d\n'\u0012\u0000\u0001e.\u0000\u0001e\u0015\u0000\u0001f8\u0000\u0001g3\u0000\u0001h*\u0000\u0001i\u0016\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\t#\u0001j\u0004#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0001[\u0002#\u0001\u0000\u0005#\u0001\u0000\u0004#\u0001]\t#\u0004\u0000\u0002#\u0002\u0000\n#\u0001k\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\b#\u0001l\u0005#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0004#\u0001m\t#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\t'\u0001n\u0004'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0001`\u0002'\u00018\u0005'\u0001\u0000\u0004'\u0001b\t'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u0001k\u0005'\u0001\u0000\u000e'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\b'\u0001o\u0005'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0004'\u0001p\t'\u0010\u0000\u0001q)\u0000\u0001r6\u0000\u0001s\u0013\u0000\u0002#\u0002\u0000\n#\u0001t\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002#\u0002\u0000\n#\u0001u\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0004#\u0001v\t#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u0001t\u0005'\u0001\u0000\u000e'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u0001u\u0005'\u0001\u0000\u000e'\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0004'\u0001w\t'\u0011\u0000\u0001x*\u0000\u0001y#\u0000\u0001z$\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u000b#\u0001{\u0002#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u000b'\u0001|\u0002'\u0001\u0000\u0001x\u0001}A\u0000\u0001~!\u0000\u0001\u007f\u001e\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\f#\u0001\u0080\u0001#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\f'\u0001\u0081\u0001'\u0001\u0000\u0001}*\u0000\u0001~\u0001\u00829\u0000\u0001\u0083\u001d\u0000\u0002#\u0002\u0000\n#\u00018\u0003#\u0001\u0000\u0005#\u0001\u0000\u0003#\u0001\u0084\n#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u00018\u0005'\u0001\u0000\u0003'\u0001\u0085\n'\u0001\u0000\u0001\u00825\u0000\u0001\u0086\"\u0000\u0002#\u0002\u0000\n#\u00018\u0001\u0087\u0002#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0001\u0088\u0002'\u00018\u0005'\u0001\u0000\u000e'\u0001\u0000\u0001\u0086\u0001\u0089,\u0000\u0002#\u0002\u0000\n#\u0001\u008a\u0003#\u0001\u0000\u0005#\u0001\u0000\u000e#\u0004\u0000\u0002'\u0002\u0000\n'\u0001\u0000\u0003'\u0001\u008a\u0005'\u0001\u0000\u000e'\u0001\u0000\u0001\u0089)\u0000", offset, trans);
      return trans;
   }

   private static int yy_unpack(String packed, int offset, int[] trans) {
      int i = 0;
      int j = offset;
      int l = packed.length();

      while(i < l) {
         int count = packed.charAt(i++);
         int value = packed.charAt(i++);
         --value;

         while(true) {
            trans[j++] = value;
            --count;
            if (count <= 0) {
               break;
            }
         }
      }

      return j;
   }

   private static char[] yy_unpack_cmap(String packed) {
      char[] map = new char[65536];
      int i = 0;
      int j = 0;

      while(i < 144) {
         int count = packed.charAt(i++);
         char value = packed.charAt(i++);

         while(true) {
            map[j++] = value;
            --count;
            if (count <= 0) {
               break;
            }
         }
      }

      return map;
   }

   private boolean yy_refill() throws IOException {
      if (this.yy_startRead > 0) {
         System.arraycopy(this.yy_buffer, this.yy_startRead, this.yy_buffer, 0, this.yy_endRead - this.yy_startRead);
         this.yy_endRead -= this.yy_startRead;
         this.yy_currentPos -= this.yy_startRead;
         this.yy_markedPos -= this.yy_startRead;
         this.yy_pushbackPos -= this.yy_startRead;
         this.yy_startRead = 0;
      }

      if (this.yy_markedPos >= this.yy_buffer.length || this.yy_currentPos >= this.yy_buffer.length) {
         char[] newBuffer = new char[this.yy_buffer.length * 2];
         System.arraycopy(this.yy_buffer, 0, newBuffer, 0, this.yy_buffer.length);
         this.yy_buffer = newBuffer;
      }

      int numRead = this.yy_reader.read(this.yy_buffer, this.yy_endRead, this.yy_buffer.length - this.yy_endRead);
      if (numRead < 0) {
         return true;
      } else {
         this.yy_endRead += numRead;
         return false;
      }
   }

   public final void yyclose() throws IOException {
      this.yy_atEOF = true;
      this.yy_endRead = this.yy_startRead;
      if (this.yy_reader != null) {
         this.yy_reader.close();
      }

   }

   public final void yyreset(Reader reader) throws IOException {
      this.yyclose();
      if (this.yy_saved_buffer != null) {
         this.yy_buffer = this.yy_saved_buffer;
         this.yy_saved_buffer = null;
      }

      this.yy_reader = reader;
      this.yy_atBOL = true;
      this.yy_atEOF = false;
      this.yy_endRead = this.yy_startRead = 0;
      this.yy_currentPos = this.yy_markedPos = this.yy_pushbackPos = 0;
      this.yyline = this.yychar = this.yycolumn = 0;
      this.yy_state = this.yy_lexical_state = 0;
      this.yy_sawCR = false;
      this.yyline_next = this.yycolumn_next = 0;
   }

   public final void yyreset(char[] buffer, int off, int len) throws IOException {
      this.yyclose();
      if (this.yy_saved_buffer == null) {
         this.yy_saved_buffer = this.yy_buffer;
      }

      this.yy_buffer = buffer;
      this.yy_reader = null;
      this.yy_atBOL = true;
      this.yy_atEOF = true;
      this.yy_currentPos = this.yy_markedPos = this.yy_pushbackPos = this.yy_startRead = off;
      this.yy_endRead = off + len;
      this.yyline = this.yychar = this.yycolumn = 0;
      this.yy_state = this.yy_lexical_state = 0;
      this.yy_sawCR = false;
      this.yyline_next = this.yycolumn_next = 0;
      this.yy_endRead_l = this.yy_endRead;
      this.yy_buffer_l = this.yy_buffer;
   }

   public final int yystate() {
      return this.yy_lexical_state;
   }

   public final void yybegin(int newState) {
      this.yy_lexical_state = newState;
   }

   public final String yytext() {
      return new String(this.yy_buffer, this.yy_startRead, this.yy_markedPos - this.yy_startRead);
   }

   public final String yytext(int offset, int length) {
      return new String(this.yy_buffer, this.yy_startRead + offset, length);
   }

   public final void yynextAction() {
      this.yyline = this.yyline_next;
      this.yycolumn = this.yycolumn_next;
      this.yy_currentPos = this.yy_startRead = this.yy_markedPos;
   }

   public final int yynextChar() throws IOException {
      char yy_input;
      if (this.yy_markedPos < this.yy_endRead) {
         yy_input = this.yy_buffer[this.yy_markedPos++];
      } else {
         if (this.yy_atEOF) {
            return -1;
         }

         boolean eof = this.yy_refill();
         this.yy_buffer_l = this.yy_buffer;
         this.yy_endRead_l = this.yy_endRead;
         if (eof) {
            return -1;
         }

         yy_input = this.yy_buffer[this.yy_markedPos++];
      }

      this.yy_doCount(yy_input);
      return yy_input;
   }

   public final int yynextBufferChar() throws IOException {
      int yy_input = this.yy_buffer[this.yy_markedPos++];
      this.yy_doCount(yy_input);
      return yy_input;
   }

   private final int yy_doCount(int yy_input) {
      switch (yy_input) {
         case 10:
            if (this.yy_sawCR) {
               this.yy_sawCR = false;
            } else {
               ++this.yyline_next;
               this.yycolumn_next = 0;
            }
            break;
         case 13:
            ++this.yyline_next;
            this.yycolumn_next = 0;
            this.yy_sawCR = true;
            break;
         default:
            this.yy_sawCR = false;
            ++this.yycolumn_next;
      }

      return yy_input;
   }

   public final char yycharat(int pos) {
      return this.yy_buffer[this.yy_startRead + pos];
   }

   public final int yybufferLeft() {
      return this.yy_endRead - this.yy_markedPos;
   }

   public final void yyskip(int n) {
      this.yy_markedPos += n;
      this.yy_markedPos_l = this.yy_markedPos;
      if (this.yy_markedPos > this.yy_endRead) {
         this.yy_ScanError(4);
      }

   }

   public final int yylength() {
      return this.yy_markedPos - this.yy_startRead;
   }

   private void yy_ScanError(int errorCode) {
      String message;
      try {
         message = YY_ERROR_MSG[errorCode];
      } catch (ArrayIndexOutOfBoundsException var4) {
         message = YY_ERROR_MSG[0];
      }

      throw new Error(message);
   }

   private void yypushback(int number) {
      if (number > this.yylength()) {
         this.yy_ScanError(3);
      }

      this.yy_markedPos -= number;
      this.yyline_next = this.yyline;
      this.yycolumn_next = this.yycolumn;
      this.yy_sawCR = this.yy_prev_sawCR;

      for(int pos = this.yy_startRead; pos < this.yy_markedPos; ++pos) {
         this.yy_doCount(this.yy_buffer[pos]);
      }

   }

   public int parse() throws IOException, FileFormatException {
      this.yy_endRead_l = this.yy_endRead;
      this.yy_buffer_l = this.yy_buffer;
      this.yycmap_l = yycmap;
      int[] yytrans_l = yytrans;
      int[] yy_rowMap_l = yy_rowMap;
      byte[] yy_attr_l = YY_ATTRIBUTE;
      int yy_pushbackPos_l = this.yy_pushbackPos = -1;

      while(true) {
         this.yy_markedPos_l = this.yy_markedPos;
         this.yychar += this.yy_markedPos_l - this.yy_startRead;
         int yy_action = -1;
         this.yy_startRead_l = this.yy_currentPos_l = this.yy_currentPos = this.yy_startRead = this.yy_markedPos_l;
         this.yy_state = this.yy_lexical_state;
         boolean yy_was_pushback = false;

         int yy_input;
         while(true) {
            if (this.yy_currentPos_l < this.yy_endRead_l) {
               yy_input = this.yy_buffer_l[this.yy_currentPos_l++];
            } else {
               if (this.yy_atEOF) {
                  yy_input = -1;
                  break;
               }

               this.yy_currentPos = this.yy_currentPos_l;
               this.yy_markedPos = this.yy_markedPos_l;
               this.yy_pushbackPos = yy_pushbackPos_l;
               boolean eof = this.yy_refill();
               this.yy_currentPos_l = this.yy_currentPos;
               this.yy_markedPos_l = this.yy_markedPos;
               this.yy_buffer_l = this.yy_buffer;
               this.yy_endRead_l = this.yy_endRead;
               yy_pushbackPos_l = this.yy_pushbackPos;
               if (eof) {
                  yy_input = -1;
                  break;
               }

               yy_input = this.yy_buffer_l[this.yy_currentPos_l++];
            }

            int yy_next = yytrans_l[yy_rowMap_l[this.yy_state] + this.yycmap_l[yy_input]];
            if (yy_next == -1) {
               break;
            }

            this.yy_state = yy_next;
            int yy_attributes = yy_attr_l[this.yy_state];
            if ((yy_attributes & 2) == 2) {
               yy_pushbackPos_l = this.yy_currentPos_l;
            }

            if ((yy_attributes & 1) == 1) {
               yy_was_pushback = (yy_attributes & 4) == 4;
               yy_action = this.yy_state;
               this.yy_markedPos_l = this.yy_currentPos_l;
               if ((yy_attributes & 8) == 8) {
                  break;
               }
            }
         }

         this.yy_markedPos = this.yy_markedPos_l;
         if (yy_was_pushback) {
            this.yy_markedPos = yy_pushbackPos_l;
         }

         switch (yy_action) {
            case 8:
            case 9:
               return -1;
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
               throw new FileFormatException("XML Declaration not well-formed", -1, -1);
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 53:
            case 54:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 80:
            case 81:
            case 83:
            case 84:
            case 85:
            case 86:
            case 87:
            case 88:
            case 90:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            case 99:
            case 102:
            case 103:
            case 104:
            case 105:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 125:
            case 126:
            case 127:
            case 128:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 138:
            default:
               if (yy_input == -1 && this.yy_startRead == this.yy_currentPos) {
                  this.yy_atEOF = true;
                  return -1;
               }

               this.yy_ScanError(2);
               break;
            case 28:
               return 1;
            case 52:
               this.xmlVersion = this.yytext(1, this.yylength() - 2);
               this.yybegin(3);
               break;
            case 55:
               this.xmlEncoding = this.yytext(1, this.yylength() - 2);
               this.yybegin(5);
               break;
            case 82:
               this.xmlStandalone = false;
               this.yybegin(7);
               break;
            case 89:
               this.xmlVersion = "1.0";
               this.yybegin(3);
               break;
            case 100:
               this.xmlStandalone = true;
               this.yybegin(7);
               break;
            case 101:
               this.yybegin(1);
               break;
            case 106:
               this.xmlEncoding = "UTF-8";
               this.yybegin(5);
               break;
            case 115:
               this.xmlEncoding = "UTF-16";
               this.yybegin(5);
               break;
            case 116:
               this.xmlEncoding = "US-ASCII";
               this.yybegin(5);
               break;
            case 124:
               this.yybegin(2);
               break;
            case 129:
               this.yybegin(4);
               break;
            case 136:
               this.xmlStandaloneDeclared = true;
               this.yybegin(6);
               break;
            case 137:
               this.xmlEncoding = "ISO-8859-1";
               this.yybegin(5);
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
         }
      }
   }
}
