package com.bea.xbean.piccolo.xml;

import com.bea.xbean.piccolo.io.FileFormatException;
import com.bea.xbean.piccolo.io.IllegalCharException;
import com.bea.xbean.piccolo.util.DuplicateKeyException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.Locale;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public class Piccolo implements Parser, Locator, XMLReader {
   boolean yydebug;
   int yynerrs;
   int yyerrflag;
   int yychar;
   static final int YYSTACKSIZE = 500;
   int[] statestk = new int[500];
   int stateptr;
   int stateptrmax;
   int statemax;
   String yytext;
   String yyval;
   String yylval;
   String[] valstk = new String[500];
   int valptr;
   public static final short CDATA = 257;
   public static final short TAG_END = 258;
   public static final short PI = 259;
   public static final short NAME = 260;
   public static final short STRING = 261;
   public static final short EQ = 262;
   public static final short OPEN_TAG = 263;
   public static final short CLOSE_TAG = 264;
   public static final short EMPTY_TAG = 265;
   public static final short WHITESPACE = 266;
   public static final short DTD_START = 267;
   public static final short DTD_START_SKIPEXTERNAL = 268;
   public static final short SYSTEM = 269;
   public static final short PUBLIC = 270;
   public static final short REQUIRED = 271;
   public static final short IMPLIED = 272;
   public static final short FIXED = 273;
   public static final short LPAREN = 274;
   public static final short RPAREN = 275;
   public static final short LBRACKET = 276;
   public static final short PIPE = 277;
   public static final short ENTITY_DECL_START = 278;
   public static final short ATTLIST_START = 279;
   public static final short NOTATION_START = 280;
   public static final short RBRACKET_END = 281;
   public static final short DOUBLE_RBRACKET_END = 282;
   public static final short PERCENT = 283;
   public static final short ENUMERATION = 284;
   public static final short NOTATION = 285;
   public static final short ID = 286;
   public static final short IDREF = 287;
   public static final short IDREFS = 288;
   public static final short ENTITY = 289;
   public static final short ENTITIES = 290;
   public static final short NMTOKEN = 291;
   public static final short NMTOKENS = 292;
   public static final short ENTITY_REF = 293;
   public static final short ENTITY_END = 294;
   public static final short INTERNAL_ENTITY_REF = 295;
   public static final short EXTERNAL_ENTITY_REF = 296;
   public static final short SKIPPED_ENTITY_REF = 297;
   public static final short PREFIXED_NAME = 298;
   public static final short UNPREFIXED_NAME = 299;
   public static final short NDATA = 300;
   public static final short COMMENT = 301;
   public static final short CONDITIONAL_START = 302;
   public static final short IGNORED_CONDITIONAL_START = 303;
   public static final short INCLUDE = 304;
   public static final short IGNORE = 305;
   public static final short MODIFIER = 306;
   public static final short PCDATA = 307;
   public static final short ELEMENT_DECL_START = 308;
   public static final short EMPTY = 309;
   public static final short ANY = 310;
   public static final short STAR = 311;
   public static final short COMMA = 312;
   public static final short QUESTION = 313;
   public static final short PLUS = 314;
   public static final short XML_DOC_DECL = 315;
   public static final short XML_TEXT_DECL = 316;
   public static final short XML_DOC_OR_TEXT_DECL = 317;
   public static final short YYERRCODE = 256;
   static final short[] yylhs = new short[]{-1, 0, 0, 1, 1, 1, 5, 5, 3, 3, 3, 4, 4, 7, 7, 7, 8, 8, 9, 9, 2, 2, 2, 2, 2, 2, 12, 12, 14, 14, 10, 10, 10, 13, 13, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 15, 15, 20, 20, 21, 21, 22, 22, 16, 16, 16, 16, 16, 16, 18, 18, 17, 23, 24, 24, 25, 25, 25, 25, 25, 25, 25, 25, 26, 26, 26, 26, 26, 26, 26, 26, 26, 26, 27, 27, 19, 28, 28, 28, 28, 29, 29, 29, 29, 30, 30, 30, 34, 34, 36, 36, 35, 35, 35, 35, 31, 31, 33, 33, 32, 32, 32, 32, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6};
   static final short[] yylen = new short[]{2, 4, 3, 1, 1, 0, 1, 1, 1, 3, 2, 2, 0, 1, 1, 1, 1, 2, 0, 1, 4, 3, 4, 3, 6, 2, 2, 4, 7, 9, 3, 3, 5, 3, 5, 0, 2, 2, 2, 2, 2, 2, 3, 3, 4, 4, 4, 3, 2, 3, 2, 0, 4, 7, 7, 11, 8, 8, 11, 7, 9, 4, 3, 0, 3, 5, 5, 5, 5, 7, 7, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 5, 7, 1, 5, 7, 1, 1, 1, 1, 6, 5, 10, 2, 2, 2, 2, 5, 5, 1, 4, 3, 3, 3, 2, 4, 2, 4, 2, 1, 1, 1, 0, 0, 4, 4, 5, 4, 2, 2, 2, 2, 2};
   static final short[] yydefred = new short[]{0, 3, 4, 0, 0, 14, 113, 8, 13, 0, 0, 15, 0, 0, 0, 35, 0, 0, 0, 0, 0, 0, 0, 2, 0, 25, 10, 0, 6, 7, 35, 0, 26, 35, 122, 119, 113, 9, 118, 121, 113, 0, 120, 16, 30, 0, 0, 31, 0, 1, 11, 0, 0, 0, 21, 35, 0, 0, 0, 40, 36, 37, 38, 39, 41, 63, 0, 23, 0, 0, 0, 113, 0, 17, 0, 0, 20, 0, 0, 0, 0, 0, 0, 0, 35, 0, 0, 0, 0, 35, 51, 0, 0, 22, 27, 117, 114, 0, 115, 0, 0, 0, 0, 32, 0, 0, 0, 0, 62, 0, 0, 0, 0, 16, 48, 50, 0, 0, 0, 0, 0, 116, 0, 0, 33, 0, 24, 0, 0, 0, 47, 49, 45, 46, 51, 0, 0, 0, 64, 61, 28, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 86, 87, 0, 88, 89, 0, 0, 0, 0, 0, 34, 0, 0, 0, 0, 0, 0, 0, 0, 52, 0, 93, 96, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 110, 109, 111, 94, 95, 73, 0, 0, 74, 75, 76, 77, 78, 79, 80, 0, 0, 29, 53, 0, 54, 0, 0, 0, 0, 59, 0, 0, 0, 0, 104, 0, 0, 0, 0, 0, 0, 85, 0, 0, 0, 0, 0, 56, 57, 0, 0, 0, 101, 0, 102, 103, 105, 0, 0, 107, 83, 0, 0, 71, 65, 67, 0, 72, 66, 68, 0, 0, 0, 60, 0, 0, 0, 0, 100, 0, 0, 0, 0, 0, 0, 90, 0, 98, 0, 0, 81, 0, 0, 69, 70, 55, 58, 0, 0, 0, 0, 0, 84, 82, 0, 92};
   static final short[] yydgoto = new short[]{3, 4, 12, 26, 23, 30, 18, 59, 104, 46, 15, 27, 16, 79, 17, 60, 61, 62, 63, 64, 89, 90, 117, 65, 92, 138, 202, 244, 154, 173, 174, 181, 190, 182, 183, 184, 185};
   static final short[] yysindex = new short[]{-120, 0, 0, 0, 181, 0, 0, 0, 0, -213, -166, 0, -250, -248, 181, 0, -10, -206, -119, -239, -188, -248, -250, 0, -248, 0, 0, -41, 0, 0, 0, -16, 0, 0, 0, 0, 0, 0, 0, 0, 0, -10, 0, 0, 0, 165, -237, 0, 225, 0, 0, -194, -194, -194, 0, 0, -10, -223, -194, 0, 0, 0, 0, 0, 0, 0, -7, 0, 18, 102, 117, 0, 127, 0, -194, -194, 0, -194, -194, -137, -146, -88, -54, 77, 0, 77, -194, -194, -209, 0, 0, -24, -194, 0, 0, 0, 0, 142, 0, -108, -76, -53, -15, 0, -135, -117, -194, -79, 0, -194, 77, -66, -59, 0, 0, 0, 43, -139, -194, -147, -32, 0, -194, -194, 0, -194, 0, 204, 227, 232, 0, 0, 0, 0, 0, -264, -194, -194, 0, 0, 0, 15, 76, -194, -194, 210, -194, -194, -194, -133, -103, -86, 0, 0, -194, 0, 0, 106, 106, 198, 198, -194, 0, 16, -235, 35, -194, -194, -192, 97, 92, 0, -103, 0, 0, 106, 106, 106, -55, -55, 55, 106, 106, -194, -194, -194, 113, 0, 0, 0, 0, 0, 0, -194, -194, 0, 0, 0, 0, 0, 0, 0, -194, -194, 0, 0, -194, 0, 123, 130, -194, -194, 0, -194, -55, 106, 106, 0, -194, -194, -194, 49, -251, 116, 0, 135, -39, 44, 143, 67, 0, 0, 68, 76, 144, 0, -28, 0, 0, 0, -194, -55, 0, 0, -194, -194, 0, 0, 0, -194, 0, 0, 0, -194, -194, -194, 0, 89, -194, -55, -194, 0, 112, 135, 103, 109, 148, 152, 0, 135, 0, -194, 65, 0, -194, -194, 0, 0, 0, 0, -194, 168, 169, 119, 150, 0, 0, 131, 0};
   static final short[] yyrindex = new short[]{191, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 452, 0, 0, 52, 0, 0, 202, 0, 452, 0, 0, 452, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 167, 0, 0, 0, 208, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -80, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 202, 0, 0, 0, -50, 0, -12, 193, 193, 0, 0, 0, 0, 202, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -153, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 208, 0, 0, -178, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 202, 202, 0, 0, 0, 202, 0, 31, 160, 0, 0, 202, 0, 0, -25, -25, 0, 0, -178, 0, 0, 208, 0, 202, 202, 0, 0, 0, 0, 0, 0, 0, -13, 17, -263, 170, 0, 0, -263, -263, 200, -186, 200, 0, 0, 0, 0, 0, 0, 0, 218, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 202, 0, -164, 0, -258, -200, 0, 164, -164, -164, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 208, 0, 0, 0, 0, 0, 0, -124, 0, 0, 0, 164, 218, 0, 0, 0, 0, 0, 0, 0, 0, 202, 202, 0, 42, 218, 0, -252, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 176, 0, 0, 218, 164, 0, 0, 0, 0, 164, 0, 0, 0, 0, 0, 0, 0, 0};
   static final short[] yygindex = new short[]{0, 0, 467, 300, 328, 27, 125, 244, -19, -51, 0, 46, 0, 28, 0, 0, 0, 0, 0, 0, 411, 412, 369, 0, 0, 0, 344, 66, 0, 370, 371, -130, -152, -128, 248, -174, 267};
   static final int YYTABLESIZE = 508;
   static final short[] yytable = new short[]{45, 48, 150, 112, 217, 157, 191, 158, 106, 5, 151, 5, 112, 6, 112, 7, 8, 106, 8, 106, 175, 76, 176, 99, 191, 213, 240, 43, 105, 219, 220, 73, 80, 81, 82, 111, 112, 44, 88, 91, 217, 120, 175, 43, 176, 152, 153, 19, 215, 112, 216, 11, 32, 11, 106, 99, 100, 113, 101, 102, 18, 241, 31, 219, 220, 206, 108, 260, 71, 88, 33, 140, 43, 119, 73, 108, 66, 108, 43, 68, 18, 86, 87, 84, 215, 271, 216, 127, 47, 99, 129, 18, 163, 165, 20, 86, 87, 170, 18, 135, 180, 83, 85, 186, 141, 19, 142, 19, 210, 145, 204, 18, 108, 18, 106, 208, 209, 159, 160, 73, 73, 19, 19, 19, 19, 164, 18, 168, 169, 43, 110, 73, 221, 222, 223, 116, 18, 107, 34, 103, 35, 126, 225, 133, 36, 37, 38, 39, 18, 171, 18, 136, 137, 122, 19, 144, 146, 148, 73, 19, 234, 69, 235, 172, 134, 70, 72, 236, 237, 238, 134, 151, 108, 167, 177, 226, 40, 41, 73, 35, 178, 128, 42, 227, 228, 123, 35, 229, 179, 259, 73, 232, 233, 262, 263, 1, 97, 2, 35, 35, 35, 35, 35, 266, 267, 177, 109, 269, 124, 272, 130, 214, 73, 73, 35, 35, 35, 131, 5, 179, 281, 35, 35, 282, 283, 8, 139, 73, 35, 284, 264, 42, 42, 112, 265, 245, 118, 51, 52, 53, 54, 112, 73, 5, 42, 106, 125, 257, 14, 258, 8, 73, 5, 106, 55, 56, 22, 24, 14, 8, 11, 57, 51, 52, 53, 24, 22, 58, 24, 43, 43, 51, 52, 53, 205, 108, 161, 5, 67, 55, 56, 73, 43, 108, 8, 11, 57, 93, 55, 56, 44, 44, 58, 207, 11, 57, 51, 52, 53, 94, 91, 58, 5, 44, 13, 246, 28, 29, 91, 8, 73, 35, 21, 55, 56, 247, 248, 249, 35, 11, 57, 51, 52, 53, 239, 132, 58, 254, 255, 275, 35, 35, 35, 73, 73, 280, 5, 162, 55, 56, 17, 17, 73, 8, 11, 57, 35, 35, 35, 49, 212, 58, 50, 35, 35, 51, 52, 53, 211, 34, 35, 35, 218, 73, 276, 36, 95, 38, 39, 73, 277, 224, 55, 56, 34, 73, 35, 241, 11, 57, 36, 230, 38, 39, 34, 58, 35, 273, 231, 274, 36, 242, 38, 39, 286, 243, 274, 40, 41, 34, 268, 35, 256, 42, 250, 36, 278, 38, 39, 73, 279, 96, 40, 41, 251, 252, 253, 187, 42, 188, 189, 98, 40, 41, 113, 287, 113, 274, 42, 285, 113, 73, 113, 113, 74, 75, 121, 40, 41, 18, 5, 18, 288, 42, 6, 240, 7, 8, 9, 10, 5, 97, 12, 18, 5, 192, 5, 5, 5, 5, 18, 113, 113, 113, 73, 143, 19, 18, 113, 18, 73, 166, 193, 77, 78, 18, 73, 16, 18, 77, 78, 25, 11, 194, 195, 196, 197, 198, 199, 200, 201, 73, 5, 43, 77, 78, 77, 78, 73, 114, 115, 77, 147, 149, 203, 155, 156, 270, 261};
   static final short[] yycheck = new short[]{19, 20, 266, 266, 178, 135, 158, 135, 266, 259, 274, 259, 275, 263, 277, 265, 266, 275, 266, 277, 150, 258, 150, 275, 176, 177, 277, 266, 79, 181, 182, 266, 51, 52, 53, 86, 87, 276, 57, 58, 214, 92, 172, 266, 172, 309, 310, 260, 178, 312, 178, 301, 258, 301, 312, 74, 75, 266, 77, 78, 312, 312, 16, 215, 216, 300, 266, 241, 41, 88, 276, 122, 266, 92, 266, 275, 30, 277, 266, 33, 258, 304, 305, 56, 214, 259, 214, 106, 276, 275, 109, 277, 143, 144, 260, 304, 305, 148, 276, 118, 151, 55, 56, 154, 123, 258, 125, 260, 300, 128, 161, 275, 312, 277, 260, 166, 167, 136, 137, 266, 266, 274, 275, 276, 277, 144, 312, 146, 147, 266, 84, 266, 183, 184, 185, 89, 260, 283, 257, 276, 259, 258, 193, 282, 263, 264, 265, 266, 312, 282, 274, 298, 299, 261, 307, 127, 128, 129, 266, 312, 211, 36, 213, 266, 303, 40, 41, 218, 219, 220, 303, 274, 260, 145, 260, 194, 295, 296, 266, 259, 266, 260, 301, 202, 203, 261, 266, 206, 274, 240, 266, 210, 211, 244, 245, 315, 71, 317, 278, 279, 280, 281, 282, 254, 255, 260, 260, 258, 261, 260, 276, 266, 266, 266, 294, 295, 296, 276, 259, 274, 271, 301, 302, 274, 275, 266, 258, 266, 308, 280, 249, 281, 282, 258, 253, 274, 260, 278, 279, 280, 281, 266, 266, 259, 294, 258, 261, 275, 4, 277, 266, 266, 259, 266, 295, 296, 12, 13, 14, 266, 301, 302, 278, 279, 280, 21, 22, 308, 24, 281, 282, 278, 279, 280, 258, 258, 261, 259, 294, 295, 296, 266, 294, 266, 266, 301, 302, 294, 295, 296, 281, 282, 308, 258, 301, 302, 278, 279, 280, 281, 258, 308, 259, 294, 4, 261, 316, 317, 266, 266, 266, 259, 12, 295, 296, 271, 272, 273, 266, 301, 302, 278, 279, 280, 275, 282, 308, 260, 260, 263, 278, 279, 280, 266, 266, 269, 259, 261, 295, 296, 309, 310, 266, 266, 301, 302, 294, 295, 296, 21, 258, 308, 24, 301, 302, 278, 279, 280, 261, 257, 308, 259, 307, 266, 261, 263, 264, 265, 266, 266, 261, 258, 295, 296, 257, 266, 259, 312, 301, 302, 263, 258, 265, 266, 257, 308, 259, 275, 258, 277, 263, 275, 265, 266, 275, 260, 277, 295, 296, 257, 311, 259, 258, 301, 261, 263, 258, 265, 266, 266, 258, 294, 295, 296, 271, 272, 273, 311, 301, 313, 314, 294, 295, 296, 257, 275, 259, 277, 301, 260, 263, 266, 265, 266, 269, 270, 294, 295, 296, 275, 259, 277, 311, 301, 263, 277, 265, 266, 267, 268, 259, 275, 0, 277, 263, 257, 265, 266, 267, 268, 258, 294, 295, 296, 266, 261, 258, 307, 301, 276, 266, 261, 274, 269, 270, 275, 266, 307, 260, 269, 270, 14, 301, 285, 286, 287, 288, 289, 290, 291, 292, 266, 301, 266, 269, 270, 269, 270, 266, 88, 88, 269, 270, 134, 160, 135, 135, 259, 241};
   static final short YYFINAL = 3;
   static final short YYMAXTOKEN = 317;
   static final String[] yyname = new String[]{"end-of-file", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "CDATA", "TAG_END", "PI", "NAME", "STRING", "EQ", "OPEN_TAG", "CLOSE_TAG", "EMPTY_TAG", "WHITESPACE", "DTD_START", "DTD_START_SKIPEXTERNAL", "SYSTEM", "PUBLIC", "REQUIRED", "IMPLIED", "FIXED", "LPAREN", "RPAREN", "LBRACKET", "PIPE", "ENTITY_DECL_START", "ATTLIST_START", "NOTATION_START", "RBRACKET_END", "DOUBLE_RBRACKET_END", "PERCENT", "ENUMERATION", "NOTATION", "ID", "IDREF", "IDREFS", "ENTITY", "ENTITIES", "NMTOKEN", "NMTOKENS", "ENTITY_REF", "ENTITY_END", "INTERNAL_ENTITY_REF", "EXTERNAL_ENTITY_REF", "SKIPPED_ENTITY_REF", "PREFIXED_NAME", "UNPREFIXED_NAME", "NDATA", "COMMENT", "CONDITIONAL_START", "IGNORED_CONDITIONAL_START", "INCLUDE", "IGNORE", "MODIFIER", "PCDATA", "ELEMENT_DECL_START", "EMPTY", "ANY", "STAR", "COMMA", "QUESTION", "PLUS", "XML_DOC_DECL", "XML_TEXT_DECL", "XML_DOC_OR_TEXT_DECL"};
   static final String[] yyrule = new String[]{"$accept : document", "document : xml_decl dtd body epilog", "document : xml_decl body epilog", "xml_decl : XML_DOC_DECL", "xml_decl : XML_DOC_OR_TEXT_DECL", "xml_decl :", "xml_text_decl : XML_TEXT_DECL", "xml_text_decl : XML_DOC_OR_TEXT_DECL", "body : EMPTY_TAG", "body : OPEN_TAG content CLOSE_TAG", "body : misc body", "epilog : misc epilog", "epilog :", "misc : WHITESPACE", "misc : PI", "misc : COMMENT", "ws : WHITESPACE", "ws : ws WHITESPACE", "opt_ws :", "opt_ws : ws", "dtd : DTD_START NAME opt_ws TAG_END", "dtd : dtd_only_internal_start dtd_content RBRACKET_END", "dtd : dtd_with_external xml_text_decl dtd_content ENTITY_END", "dtd : dtd_with_external dtd_content ENTITY_END", "dtd : DTD_START_SKIPEXTERNAL NAME ws external_id opt_ws TAG_END", "dtd : misc dtd", "dtd_with_external : dtd_with_external_start TAG_END", "dtd_with_external : dtd_with_external_start LBRACKET dtd_content RBRACKET_END", "dtd_with_external_start : DTD_START NAME ws SYSTEM ws STRING opt_ws", "dtd_with_external_start : DTD_START NAME ws PUBLIC ws STRING ws STRING opt_ws", "dtd_only_internal_start : DTD_START NAME LBRACKET", "dtd_only_internal_start : DTD_START_SKIPEXTERNAL NAME LBRACKET", "dtd_only_internal_start : DTD_START_SKIPEXTERNAL NAME ws external_id LBRACKET", "external_id : SYSTEM ws STRING", "external_id : PUBLIC ws STRING ws STRING", "dtd_content :", "dtd_content : dtd_content dtd_conditional", "dtd_content : dtd_content dtd_entity", "dtd_content : dtd_content dtd_attlist", "dtd_content : dtd_content dtd_notation", "dtd_content : dtd_content misc", "dtd_content : dtd_content dtd_element", "dtd_content : dtd_content INTERNAL_ENTITY_REF dtd_content", "dtd_content : dtd_content EXTERNAL_ENTITY_REF dtd_content", "dtd_content : dtd_content EXTERNAL_ENTITY_REF xml_text_decl dtd_content", "dtd_conditional : CONDITIONAL_START dtd_include dtd_content DOUBLE_RBRACKET_END", "dtd_conditional : CONDITIONAL_START dtd_ignore ignored_dtd_content DOUBLE_RBRACKET_END", "dtd_include : INCLUDE opt_ws LBRACKET", "dtd_include : ws dtd_include", "dtd_ignore : IGNORE opt_ws LBRACKET", "dtd_ignore : ws dtd_ignore", "ignored_dtd_content :", "ignored_dtd_content : ignored_dtd_content IGNORED_CONDITIONAL_START ignored_dtd_content DOUBLE_RBRACKET_END", "dtd_entity : ENTITY_DECL_START ws NAME ws STRING opt_ws TAG_END", "dtd_entity : ENTITY_DECL_START ws NAME ws external_id opt_ws TAG_END", "dtd_entity : ENTITY_DECL_START ws NAME ws external_id ws NDATA ws NAME opt_ws TAG_END", "dtd_entity : ENTITY_DECL_START ws PERCENT NAME ws STRING opt_ws TAG_END", "dtd_entity : ENTITY_DECL_START ws PERCENT NAME ws external_id opt_ws TAG_END", "dtd_entity : ENTITY_DECL_START ws PERCENT NAME external_id ws NDATA ws NAME opt_ws TAG_END", "dtd_notation : NOTATION_START ws NAME ws external_id opt_ws TAG_END", "dtd_notation : NOTATION_START ws NAME ws PUBLIC ws STRING opt_ws TAG_END", "dtd_attlist : attlist_start att_def_list opt_ws TAG_END", "attlist_start : ATTLIST_START ws NAME", "att_def_list :", "att_def_list : att_def_list ws att_def", "att_def : PREFIXED_NAME ws att_type ws REQUIRED", "att_def : UNPREFIXED_NAME ws att_type ws REQUIRED", "att_def : PREFIXED_NAME ws att_type ws IMPLIED", "att_def : UNPREFIXED_NAME ws att_type ws IMPLIED", "att_def : PREFIXED_NAME ws att_type ws FIXED ws STRING", "att_def : UNPREFIXED_NAME ws att_type ws FIXED ws STRING", "att_def : PREFIXED_NAME ws att_type ws STRING", "att_def : UNPREFIXED_NAME ws att_type ws STRING", "att_type : CDATA", "att_type : ID", "att_type : IDREF", "att_type : IDREFS", "att_type : ENTITY", "att_type : ENTITIES", "att_type : NMTOKEN", "att_type : NMTOKENS", "att_type : LPAREN opt_ws word_list opt_ws RPAREN", "att_type : NOTATION ws LPAREN opt_ws word_list opt_ws RPAREN", "word_list : NAME", "word_list : word_list opt_ws PIPE opt_ws NAME", "dtd_element : ELEMENT_DECL_START ws NAME ws element_spec opt_ws TAG_END", "element_spec : EMPTY", "element_spec : ANY", "element_spec : element_spec_mixed", "element_spec : element_spec_children", "element_spec_mixed : LPAREN opt_ws PCDATA opt_ws RPAREN STAR", "element_spec_mixed : LPAREN opt_ws PCDATA opt_ws RPAREN", "element_spec_mixed : LPAREN opt_ws PCDATA opt_ws PIPE opt_ws word_list opt_ws RPAREN STAR", "element_spec_mixed : WHITESPACE element_spec_mixed", "element_spec_children : element_choice element_modifier", "element_spec_children : element_seq element_modifier", "element_spec_children : WHITESPACE element_spec_children", "element_cp_pipe_list : element_cp opt_ws PIPE opt_ws element_cp", "element_cp_pipe_list : element_cp opt_ws PIPE opt_ws element_cp_pipe_list", "element_cp_comma_list : element_cp", "element_cp_comma_list : element_cp opt_ws COMMA element_cp_comma_list", "element_cp : NAME element_modifier opt_ws", "element_cp : element_choice element_modifier opt_ws", "element_cp : element_seq element_modifier opt_ws", "element_cp : WHITESPACE element_cp", "element_choice : LPAREN element_cp_pipe_list opt_ws RPAREN", "element_choice : WHITESPACE element_choice", "element_seq : LPAREN element_cp_comma_list opt_ws RPAREN", "element_seq : WHITESPACE element_seq", "element_modifier : QUESTION", "element_modifier : STAR", "element_modifier : PLUS", "element_modifier :", "content :", "content : content INTERNAL_ENTITY_REF content ENTITY_END", "content : content EXTERNAL_ENTITY_REF content ENTITY_END", "content : content EXTERNAL_ENTITY_REF xml_text_decl content ENTITY_END", "content : content OPEN_TAG content CLOSE_TAG", "content : content EMPTY_TAG", "content : content PI", "content : content COMMENT", "content : content WHITESPACE", "content : content CDATA"};
   DocumentHandler documentHandler = null;
   DTDHandler dtdHandler = null;
   ErrorHandler errorHandler = null;
   ContentHandler contentHandler = null;
   int saxVersion = 0;
   int attributeType = -1;
   StringBuffer modelBuffer = new StringBuffer(100);
   ElementDefinition elementDefinition = null;
   String pubID = null;
   String sysID = null;
   String dtdName = null;
   String dtdPubID = null;
   String dtdSysID = null;
   PiccoloLexer lexer = new PiccoloLexer(this);
   DocumentEntity docEntity = new DocumentEntity();
   LexicalHandler lexHandler = null;
   DeclHandler declHandler = null;
   boolean parsingInProgress = false;
   private StartLocator startLocator;
   boolean fNamespaces = true;
   boolean fNamespacePrefixes = false;
   boolean fResolveDTDURIs = true;
   boolean fExternalGeneralEntities = true;
   boolean fExternalParameterEntities = true;
   boolean fLexicalParameterEntities = true;
   private char[] oneCharBuffer = new char[1];
   int yyn;
   int yym;
   int yystate;
   String yys;

   public void setMaxTotalEntityBytes(int size) {
      this.lexer.setMaxTotalEntityBytes(size);
   }

   public void resetEntityByteCounts() {
      this.lexer.resetEntityByteCounts();
   }

   void debug(String msg) {
      if (this.yydebug) {
         System.out.println(msg);
      }

   }

   final void state_push(int state) {
      try {
         ++this.stateptr;
         this.statestk[this.stateptr] = state;
      } catch (ArrayIndexOutOfBoundsException var6) {
         int oldsize = this.statestk.length;
         int newsize = oldsize * 2;
         int[] newstack = new int[newsize];
         System.arraycopy(this.statestk, 0, newstack, 0, oldsize);
         this.statestk = newstack;
         this.statestk[this.stateptr] = state;
      }

   }

   final int state_pop() {
      return this.statestk[this.stateptr--];
   }

   final void state_drop(int cnt) {
      this.stateptr -= cnt;
   }

   final int state_peek(int relative) {
      return this.statestk[this.stateptr - relative];
   }

   final boolean init_stacks() {
      this.stateptr = -1;
      this.val_init();
      return true;
   }

   void dump_stacks(int count) {
      System.out.println("=index==state====value=     s:" + this.stateptr + "  v:" + this.valptr);

      for(int i = 0; i < count; ++i) {
         System.out.println(" " + i + "    " + this.statestk[i] + "      " + this.valstk[i]);
      }

      System.out.println("======================");
   }

   final void val_init() {
      this.yyval = new String();
      this.yylval = new String();
      this.valptr = -1;
   }

   final void val_push(String val) {
      try {
         ++this.valptr;
         this.valstk[this.valptr] = val;
      } catch (ArrayIndexOutOfBoundsException var6) {
         int oldsize = this.valstk.length;
         int newsize = oldsize * 2;
         String[] newstack = new String[newsize];
         System.arraycopy(this.valstk, 0, newstack, 0, oldsize);
         this.valstk = newstack;
         this.valstk[this.valptr] = val;
      }

   }

   final String val_pop() {
      return this.valstk[this.valptr--];
   }

   final void val_drop(int cnt) {
      this.valptr -= cnt;
   }

   final String val_peek(int relative) {
      return this.valstk[this.valptr - relative];
   }

   public Piccolo() {
   }

   public Piccolo(Piccolo template) {
      this.fNamespaces = template.fNamespaces;
      this.fNamespacePrefixes = template.fNamespacePrefixes;
      this.fExternalGeneralEntities = template.fExternalGeneralEntities;
      this.fExternalParameterEntities = template.fExternalParameterEntities;
      this.fLexicalParameterEntities = template.fLexicalParameterEntities;
      this.lexer.enableNamespaces(this.fNamespaces);
      this.fResolveDTDURIs = template.fResolveDTDURIs;
   }

   private void reset() {
      this.modelBuffer.setLength(0);
      this.pubID = this.sysID = this.dtdName = this.dtdPubID = this.dtdSysID = null;
      this.elementDefinition = null;
   }

   private void validateParseState() throws SAXException {
      if (!this.fNamespaces && !this.fNamespacePrefixes) {
         throw new FatalParsingException("The 'namespaces' and 'namespace-prefixes' features must not both be false");
      }
   }

   public void setDebug(boolean debug) {
      this.yydebug = debug;
   }

   public void parse(InputSource source) throws IOException, SAXException {
      try {
         this.reset();
         this.validateParseState();

         try {
            this.docEntity.reset(source);
            this.lexer.reset(this.docEntity);
         } finally {
            this.reportStartDocument();
         }

         this.yyparse();
      } catch (IllegalCharException var15) {
         this.reportFatalError(var15.getMessage(), var15);
      } catch (FileFormatException var16) {
         this.reportFatalError(var16.getMessage(), var16);
      } catch (FatalParsingException var17) {
         this.reportFatalError(var17.getMessage(), var17.getException());
      } finally {
         this.reportEndDocument();
      }

   }

   public void parse(String sysID) throws IOException, SAXException {
      try {
         this.reset();
         this.validateParseState();

         try {
            this.docEntity.reset(sysID);
            this.lexer.reset(this.docEntity);
         } finally {
            this.reportStartDocument();
         }

         this.yyparse();
      } catch (IllegalCharException var15) {
         this.reportFatalError(var15.getMessage(), var15);
      } catch (FileFormatException var16) {
         this.reportFatalError(var16.getMessage(), var16);
      } catch (FatalParsingException var17) {
         this.reportFatalError(var17.getMessage(), var17.getException());
      } finally {
         this.reportEndDocument();
      }

   }

   public void setDocumentHandler(DocumentHandler handler) {
      this.documentHandler = handler;
      if (this.documentHandler != null) {
         this.saxVersion = 1;
         this.fNamespaces = false;
         this.lexer.enableNamespaces(false);
         this.fNamespacePrefixes = true;
         this.documentHandler.setDocumentLocator(this);
      } else {
         this.saxVersion = 0;
      }

   }

   public void setDTDHandler(DTDHandler handler) {
      this.dtdHandler = handler;
   }

   public void setEntityResolver(EntityResolver resolver) {
      this.lexer.entityManager.setResolver(resolver);
   }

   public void setErrorHandler(ErrorHandler handler) {
      this.errorHandler = handler;
   }

   public void setLocale(Locale locale) throws SAXException {
      if (!"en".equals(locale.getLanguage())) {
         throw new SAXException("Only English (EN) locales are supported");
      }
   }

   public int getColumnNumber() {
      return this.lexer.getColumnNumber();
   }

   public int getLineNumber() {
      return this.lexer.getLineNumber();
   }

   public String getPublicId() {
      return this.lexer.getPublicID();
   }

   public String getSystemId() {
      return this.lexer.getSystemID();
   }

   public Locator getStartLocator() {
      if (this.startLocator == null) {
         this.startLocator = new StartLocator();
      }

      return this.startLocator;
   }

   public String getVersion() {
      return this.lexer.getVersion();
   }

   public String getEncoding() {
      return this.lexer.getEncoding();
   }

   public ContentHandler getContentHandler() {
      return this.contentHandler;
   }

   public void setContentHandler(ContentHandler handler) {
      this.contentHandler = handler;
      if (this.contentHandler != null) {
         if (this.saxVersion == 1) {
            this.fNamespaces = true;
            this.lexer.enableNamespaces(true);
            this.fNamespacePrefixes = false;
         }

         this.saxVersion = 2;
         this.contentHandler.setDocumentLocator(this);
      } else {
         this.saxVersion = 0;
      }

   }

   public DTDHandler getDTDHandler() {
      return this.dtdHandler;
   }

   public EntityResolver getEntityResolver() {
      return this.lexer.entityManager.getResolver();
   }

   public ErrorHandler getErrorHandler() {
      return this.errorHandler;
   }

   public boolean getFeature(String name) throws SAXNotSupportedException, SAXNotRecognizedException {
      if (name.equals("http://xml.org/sax/features/namespaces")) {
         return this.fNamespaces;
      } else if (name.equals("http://xml.org/sax/features/namespace-prefixes")) {
         return this.fNamespacePrefixes;
      } else if (name.equals("http://xml.org/sax/features/external-general-entities")) {
         return this.fExternalGeneralEntities;
      } else if (name.equals("http://xml.org/sax/features/external-parameter-entities")) {
         return this.fExternalGeneralEntities;
      } else if (name.equals("http://xml.org/sax/features/lexical-handler/parameter-entities")) {
         return this.fLexicalParameterEntities;
      } else if (name.equals("http://xml.org/sax/features/string-interning")) {
         return true;
      } else if (name.equals("http://xml.org/sax/features/is-standalone")) {
         return this.docEntity.isStandalone();
      } else if (name.equals("http://xml.org/sax/features/resolve-dtd-uris")) {
         return this.fResolveDTDURIs;
      } else if (!name.equals("http://xml.org/sax/features/use-attributes2") && !name.equals("http://xml.org/sax/features/validation") && !name.equals("http://xml.org/sax/features/use-locator2") && !name.equals("http://xml.org/sax/features/use-entity2") && !name.equals("http://xml.org/sax/features/use-locator2")) {
         throw new SAXNotRecognizedException(name);
      } else {
         return false;
      }
   }

   public void setFeature(String name, boolean value) throws SAXNotSupportedException, SAXNotRecognizedException {
      if (name.equals("http://xml.org/sax/features/namespaces")) {
         if (this.parsingInProgress) {
            throw new SAXNotSupportedException("Can't change namespace settings while parsing");
         }

         this.fNamespaces = value;
         this.lexer.enableNamespaces(value);
      } else if (name.equals("http://xml.org/sax/features/namespace-prefixes")) {
         if (this.parsingInProgress) {
            throw new SAXNotSupportedException("Can't change namespace settings while parsing");
         }

         this.fNamespacePrefixes = value;
      } else if (name.equals("http://xml.org/sax/features/external-general-entities")) {
         this.fExternalGeneralEntities = value;
      } else if (name.equals("http://xml.org/sax/features/external-parameter-entities")) {
         this.fExternalParameterEntities = value;
      } else if (name.equals("http://xml.org/sax/features/lexical-handler/parameter-entities")) {
         this.fLexicalParameterEntities = value;
      } else if (name.equals("http://xml.org/sax/features/resolve-dtd-uris")) {
         this.fResolveDTDURIs = value;
      } else if (name.equals("http://xml.org/sax/features/validation")) {
         if (value) {
            throw new SAXNotSupportedException("validation is not supported");
         }
      } else if (name.equals("http://xml.org/sax/features/string-interning")) {
         if (!value) {
            throw new SAXNotSupportedException("strings are always internalized");
         }
      } else {
         if (!name.equals("http://xml.org/sax/features/use-attributes2") && !name.equals("http://xml.org/sax/features/validation") && !name.equals("http://xml.org/sax/features/use-locator2") && !name.equals("http://xml.org/sax/features/use-entity2") && !name.equals("http://xml.org/sax/features/use-locator2")) {
            throw new SAXNotRecognizedException(name);
         }

         if (value) {
            throw new SAXNotSupportedException(name);
         }
      }

   }

   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (name.equals("http://xml.org/sax/properties/declaration-handler")) {
         return this.declHandler;
      } else if (name.equals("http://xml.org/sax/properties/lexical-handler")) {
         return this.lexHandler;
      } else {
         throw new SAXNotRecognizedException(name);
      }
   }

   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (name.equals("http://xml.org/sax/properties/declaration-handler")) {
         try {
            this.declHandler = (DeclHandler)value;
         } catch (ClassCastException var5) {
            throw new SAXNotSupportedException("property value is not a DeclHandler");
         }
      } else {
         if (!name.equals("http://xml.org/sax/properties/lexical-handler")) {
            throw new SAXNotRecognizedException(name);
         }

         try {
            this.lexHandler = (LexicalHandler)value;
         } catch (ClassCastException var4) {
            throw new SAXNotSupportedException("property value is not a LexicalHandler");
         }
      }

   }

   void reportCdata() throws SAXException {
      this.reportCdata(this.lexer.cdataBuffer, this.lexer.cdataStart, this.lexer.cdataLength);
   }

   void reportCdata(char c) throws SAXException {
      this.oneCharBuffer[0] = c;
      this.reportCdata(this.oneCharBuffer, 0, 1);
   }

   void reportCdata(char[] buf, int off, int len) throws SAXException {
      switch (this.saxVersion) {
         case 1:
            this.documentHandler.characters(buf, off, len);
            break;
         case 2:
            this.contentHandler.characters(buf, off, len);
      }

   }

   void reportWhitespace() throws SAXException {
      this.reportWhitespace(this.lexer.cdataBuffer, this.lexer.cdataStart, this.lexer.cdataLength);
   }

   void reportWhitespace(char[] buf, int off, int len) throws SAXException {
      switch (this.saxVersion) {
         case 1:
            this.documentHandler.characters(buf, off, len);
            break;
         case 2:
            this.contentHandler.characters(buf, off, len);
      }

   }

   void reportError(String msg) throws SAXException {
      if (this.errorHandler != null) {
         this.errorHandler.error(new SAXParseException(msg, this.getPublicId(), this.getSystemId(), this.getLineNumber(), this.getColumnNumber()));
      }

   }

   void reportFatalError(String msg) throws SAXException {
      this.reportFatalError(msg, (Exception)null);
   }

   void reportFatalError(String msg, Exception e) throws SAXException {
      if (e != null) {
         StringWriter stackTrace = new StringWriter();
         e.printStackTrace(new PrintWriter(stackTrace));
         if (msg != null) {
            msg = msg + "\n" + stackTrace.toString();
         } else {
            msg = stackTrace.toString();
         }
      }

      SAXParseException spe = new SAXParseException(msg, this.getPublicId(), this.getSystemId(), this.getLineNumber(), this.getColumnNumber(), e);
      if (this.errorHandler != null) {
         this.errorHandler.fatalError(spe);
      } else {
         throw spe;
      }
   }

   void reportSkippedEntity(String entity) throws SAXException {
      if (this.saxVersion == 2) {
         this.contentHandler.skippedEntity(entity);
      }

   }

   void reportPI(String entity, String data) throws SAXException {
      switch (this.saxVersion) {
         case 1:
            this.documentHandler.processingInstruction(entity, data);
            break;
         case 2:
            this.contentHandler.processingInstruction(entity, data);
      }

   }

   void reportUnparsedEntityDecl(String entity, String pubID, String sysID, String notation) throws SAXException {
      if (this.dtdHandler != null) {
         this.dtdHandler.unparsedEntityDecl(entity, pubID, this.resolveSystemID(sysID), notation);
      }

   }

   void reportNotationDecl(String name, String pubID, String sysID) throws SAXException {
      if (this.dtdHandler != null) {
         this.dtdHandler.notationDecl(name, pubID, this.resolveSystemID(sysID));
      }

   }

   void reportStartTag(String ns, String entity, String qEntity) throws SAXException {
      switch (this.saxVersion) {
         case 1:
            this.documentHandler.startElement(qEntity, this.lexer.attribs);
            break;
         case 2:
            this.contentHandler.startElement(ns, entity, qEntity, this.lexer.attribs);
      }

   }

   void reportEndTag(String ns, String entity, String qEntity) throws SAXException {
      switch (this.saxVersion) {
         case 1:
            this.documentHandler.endElement(qEntity);
            break;
         case 2:
            this.contentHandler.endElement(ns, entity, qEntity);
      }

   }

   void reportStartPrefixMapping(String prefix, String uri) throws SAXException {
      if (this.saxVersion == 2) {
         this.contentHandler.startPrefixMapping(prefix, uri);
      }

   }

   void reportEndPrefixMapping(String prefix) throws SAXException {
      if (this.saxVersion == 2) {
         this.contentHandler.endPrefixMapping(prefix);
      }

   }

   void reportStartDocument() throws SAXException {
      this.parsingInProgress = true;
      switch (this.saxVersion) {
         case 1:
            this.documentHandler.startDocument();
            break;
         case 2:
            this.contentHandler.startDocument();
      }

   }

   void reportEndDocument() throws SAXException {
      this.parsingInProgress = false;
      switch (this.saxVersion) {
         case 1:
            this.documentHandler.endDocument();
            break;
         case 2:
            this.contentHandler.endDocument();
      }

   }

   void reportStartDTD(String name, String pubID, String sysID) throws SAXException {
      if (this.lexHandler != null) {
         this.lexHandler.startDTD(name, pubID, sysID);
      }

   }

   void reportEndDTD() throws SAXException {
      if (this.lexHandler != null) {
         this.lexHandler.endDTD();
      }

   }

   void reportStartEntity(String name) throws SAXException {
      if (this.lexHandler != null && (this.fLexicalParameterEntities || name.charAt(0) != '%')) {
         this.lexHandler.startEntity(name);
      }

   }

   void reportEndEntity(String name) throws SAXException {
      if (this.lexHandler != null && (this.fLexicalParameterEntities || name.charAt(0) != '%')) {
         this.lexHandler.endEntity(name);
      }

   }

   void reportStartCdata() throws SAXException {
      if (this.lexHandler != null) {
         this.lexHandler.startCDATA();
      }

   }

   void reportEndCdata() throws SAXException {
      if (this.lexHandler != null) {
         this.lexHandler.endCDATA();
      }

   }

   void reportComment(char[] ch, int start, int length) throws SAXException {
      if (this.lexHandler != null) {
         this.lexHandler.comment(ch, start, length);
      }

   }

   private void addAttributeDefinition(String qName, int valueType, int defaultType, String defaultValue) throws SAXException, IOException {
      String prefix = "";
      String localName = "";
      if (this.fNamespaces) {
         localName = qName;
         if (qName == "xmlns" && defaultValue != null) {
            defaultValue.intern();
         }
      }

      this.saveAttributeDefinition(prefix, localName, qName, valueType, defaultType, defaultValue);
   }

   private void addPrefixedAttributeDefinition(String qName, int valueType, int defaultType, String defaultValue) throws SAXException, IOException {
      String prefix;
      String localName;
      if (this.fNamespaces) {
         int colon = qName.indexOf(58);
         int len = qName.length();
         qName.getChars(0, len, this.lexer.cbuf, 0);
         prefix = this.lexer.stringConverter.convert(this.lexer.cbuf, 0, colon);
         localName = this.lexer.stringConverter.convert(this.lexer.cbuf, colon + 1, len - (colon + 1));
      } else {
         localName = "";
         prefix = "";
      }

      this.saveAttributeDefinition(prefix, localName, qName, valueType, defaultType, defaultValue);
   }

   private void saveAttributeDefinition(String prefix, String localName, String qName, int valueType, int defaultType, String defaultValue) throws SAXException, IOException {
      try {
         if (defaultValue != null) {
            if (valueType == 9 || valueType == 10) {
               defaultValue = this.lexer.normalizeValue(defaultValue);
            }

            defaultValue = this.lexer.rescanAttributeValue(defaultValue);
         }

         if (this.declHandler != null) {
            String valueTypeString = null;
            if (valueType == 2) {
               this.modelBuffer.insert(0, "NOTATION (");
               this.modelBuffer.append(')');
               valueTypeString = this.modelBuffer.toString();
            } else if (valueType == 1) {
               this.modelBuffer.insert(0, '(');
               this.modelBuffer.append(')');
               valueTypeString = this.modelBuffer.toString();
            } else {
               valueTypeString = AttributeDefinition.getValueTypeString(valueType);
            }

            this.declHandler.attributeDecl(this.elementDefinition.getName(), qName, valueTypeString, AttributeDefinition.getDefaultTypeString(defaultType), defaultValue);
            this.modelBuffer.setLength(0);
         }

         this.elementDefinition.addAttribute(new AttributeDefinition(prefix, localName, qName, valueType, (String[])null, defaultType, defaultValue));
      } catch (DuplicateKeyException var8) {
      }

   }

   private String resolveSystemID(String sysID) {
      if (this.fResolveDTDURIs) {
         try {
            return EntityManager.resolveSystemID(this.docEntity.getSystemID(), sysID);
         } catch (MalformedURLException var4) {
            return sysID;
         }
      } else {
         return sysID;
      }
   }

   private int yylex() throws IOException, SAXException {
      try {
         int tok = this.lexer.yylex();
         this.yylval = this.lexer.stringValue;
         this.lexer.stringValue = null;
         return tok;
      } catch (IOException var5) {
         while(this.lexer.currentEntity == null && this.lexer.entityStack.size() > 0) {
            this.lexer.currentEntity = (Entity)this.lexer.entityStack.pop();

            try {
               if (this.lexer.yymoreStreams()) {
                  this.lexer.yypopStream();
               }
            } catch (IOException var4) {
            }
         }

         throw var5;
      } catch (SAXException var6) {
         while(this.lexer.currentEntity == null && this.lexer.entityStack.size() > 0) {
            this.lexer.currentEntity = (Entity)this.lexer.entityStack.pop();

            try {
               if (this.lexer.yymoreStreams()) {
                  this.lexer.yypopStream();
               }
            } catch (IOException var3) {
            }
         }

         throw var6;
      }
   }

   void yyerror(String msg) throws SAXException {
      if (this.yychar <= 0) {
         throw new FatalParsingException("Unexpected end of file after " + this.yylval);
      } else {
         throw new FatalParsingException("Unexpected element: " + yyname[this.yychar]);
      }
   }

   void yylexdebug(int state, int ch) {
      String s = null;
      if (ch < 0) {
         ch = 0;
      }

      if (ch <= 317) {
         s = yyname[ch];
      }

      if (s == null) {
         s = "illegal-symbol";
      }

      this.debug("state " + state + ", reading " + ch + " (" + s + ")");
   }

   int yyparse() throws SAXException, IOException {
      this.init_stacks();
      this.yynerrs = 0;
      this.yyerrflag = 0;
      this.yychar = -1;
      this.yystate = 0;
      this.state_push(this.yystate);

      do {
         while(true) {
            boolean doaction;
            do {
               doaction = true;

               for(this.yyn = yydefred[this.yystate]; this.yyn == 0; this.yyn = yydefred[this.yystate]) {
                  if (this.yychar < 0) {
                     this.yychar = this.yylex();
                  }

                  this.yyn = yysindex[this.yystate];
                  if (this.yyn != 0 && (this.yyn += this.yychar) >= 0 && this.yyn <= 508 && yycheck[this.yyn] == this.yychar) {
                     this.yystate = yytable[this.yyn];
                     this.state_push(this.yystate);
                     this.val_push(this.yylval);
                     this.yychar = -1;
                     if (this.yyerrflag > 0) {
                        --this.yyerrflag;
                     }

                     doaction = false;
                     break;
                  }

                  this.yyn = yyrindex[this.yystate];
                  if (this.yyn != 0 && (this.yyn += this.yychar) >= 0 && this.yyn <= 508 && yycheck[this.yyn] == this.yychar) {
                     this.yyn = yytable[this.yyn];
                     doaction = true;
                     break;
                  }

                  if (this.yyerrflag == 0) {
                     this.yyerror("syntax error");
                     ++this.yynerrs;
                  }

                  if (this.yyerrflag >= 3) {
                     if (this.yychar == 0) {
                        return 1;
                     }

                     this.yychar = -1;
                  } else {
                     this.yyerrflag = 3;

                     while(true) {
                        this.yyn = yysindex[this.state_peek(0)];
                        if (this.yyn != 0 && (this.yyn += 256) >= 0 && this.yyn <= 508 && yycheck[this.yyn] == 256) {
                           this.yystate = yytable[this.yyn];
                           this.state_push(this.yystate);
                           this.val_push(this.yylval);
                           doaction = false;
                           break;
                        }

                        this.state_pop();
                        this.val_pop();
                     }
                  }
               }
            } while(!doaction);

            this.yym = yylen[this.yyn];
            if (this.yym > 0) {
               this.yyval = this.val_peek(this.yym - 1);
            }

            PiccoloLexer var10001;
            switch (this.yyn) {
               case 20:
                  this.dtdName = this.val_peek(2);
                  this.lexer.yybegin(0);
                  this.reportStartDTD(this.dtdName, (String)null, (String)null);
                  this.reportEndDTD();
                  break;
               case 21:
                  this.lexer.yybegin(0);
                  this.reportEndDTD();
                  break;
               case 22:
                  this.lexer.yybegin(0);
                  this.reportEndDTD();
                  break;
               case 23:
                  this.lexer.yybegin(0);
                  this.reportEndDTD();
                  break;
               case 24:
                  this.dtdName = this.val_peek(4);
                  this.lexer.yybegin(0);
                  this.reportStartDTD(this.dtdName, this.pubID, this.sysID);
                  this.reportEndDTD();
               case 25:
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
               case 48:
               case 50:
               case 51:
               case 52:
               case 63:
               case 64:
               case 113:
               case 117:
               case 118:
               case 119:
               case 120:
               default:
                  break;
               case 26:
                  this.lexer.pushEntity("[dtd]", this.dtdPubID, this.dtdSysID, false, true);
                  var10001 = this.lexer;
                  this.lexer.yybegin(21);
                  break;
               case 27:
                  this.lexer.pushEntity("[dtd]", this.dtdPubID, this.dtdSysID, false, true);
                  var10001 = this.lexer;
                  this.lexer.yybegin(21);
                  break;
               case 28:
                  this.dtdName = this.lexer.normalizeValue(this.val_peek(5));
                  this.dtdPubID = null;
                  this.dtdSysID = this.lexer.normalizeValue(this.val_peek(1));
                  this.reportStartDTD(this.dtdName, this.dtdPubID, this.dtdSysID);
                  break;
               case 29:
                  this.dtdName = this.val_peek(7);
                  this.dtdPubID = this.lexer.normalizeValue(this.val_peek(3));
                  this.dtdSysID = this.lexer.normalizeValue(this.val_peek(1));
                  this.reportStartDTD(this.dtdName, this.dtdPubID, this.dtdSysID);
                  break;
               case 30:
                  this.dtdName = this.val_peek(1);
                  this.reportStartDTD(this.dtdName, (String)null, (String)null);
                  break;
               case 31:
                  this.dtdName = this.val_peek(1);
                  this.reportStartDTD(this.dtdName, (String)null, (String)null);
                  break;
               case 32:
                  this.dtdName = this.val_peek(3);
                  this.reportStartDTD(this.dtdName, this.pubID, this.sysID);
                  break;
               case 33:
                  this.pubID = null;
                  this.sysID = this.lexer.normalizeValue(this.val_peek(0));
                  break;
               case 34:
                  this.pubID = this.lexer.normalizeValue(this.val_peek(2));
                  this.sysID = this.lexer.normalizeValue(this.val_peek(0));
                  break;
               case 46:
                  var10001 = this.lexer;
                  this.lexer.yybegin(21);
                  break;
               case 47:
                  var10001 = this.lexer;
                  this.lexer.yybegin(21);
                  break;
               case 49:
                  var10001 = this.lexer;
                  this.lexer.yybegin(28);
                  break;
               case 53:
                  this.lexer.entityManager.putInternal(this.val_peek(4), this.val_peek(2), 0);
                  if (this.declHandler != null) {
                     this.declHandler.internalEntityDecl(this.val_peek(4), this.val_peek(2));
                  }
                  break;
               case 54:
                  try {
                     this.lexer.entityManager.putExternal(this.lexer.currentEntity, this.val_peek(4), this.pubID, this.sysID, 0);
                     if (this.declHandler != null) {
                        this.declHandler.externalEntityDecl(this.val_peek(4), this.pubID, this.resolveSystemID(this.sysID));
                     }
                  } catch (MalformedURLException var6) {
                     this.reportFatalError("Invalid system identifier: " + this.sysID + "; " + var6.getMessage());
                  }
                  break;
               case 55:
                  try {
                     this.lexer.entityManager.putUnparsed(this.lexer.currentEntity, this.val_peek(8), this.pubID, this.sysID, this.val_peek(2), 0);
                     this.reportUnparsedEntityDecl(this.val_peek(8), this.pubID, this.sysID, this.val_peek(2));
                  } catch (MalformedURLException var5) {
                     this.reportFatalError("Invalid system identifier: " + this.sysID + "; " + var5.getMessage());
                  }
                  break;
               case 56:
                  this.lexer.entityManager.putInternal(this.val_peek(4), this.val_peek(2), 1);
                  if (this.declHandler != null) {
                     this.declHandler.internalEntityDecl("%" + this.val_peek(4), this.val_peek(2));
                  }
                  break;
               case 57:
                  try {
                     this.lexer.entityManager.putExternal(this.lexer.currentEntity, this.val_peek(4), this.pubID, this.sysID, 1);
                     if (this.declHandler != null) {
                        this.declHandler.externalEntityDecl("%" + this.val_peek(4), this.pubID, this.resolveSystemID(this.sysID));
                     }
                  } catch (MalformedURLException var4) {
                     this.reportFatalError("Invalid system identifier: " + this.sysID + "; " + var4.getMessage());
                  }
                  break;
               case 58:
                  try {
                     this.lexer.entityManager.putUnparsed(this.lexer.currentEntity, this.val_peek(7), this.pubID, this.sysID, this.val_peek(2), 1);
                     this.reportUnparsedEntityDecl(this.val_peek(7), this.pubID, this.sysID, this.val_peek(2));
                  } catch (MalformedURLException var3) {
                     this.reportFatalError("Invalid system identifier: " + this.sysID + "; " + var3.getMessage());
                  }
                  break;
               case 59:
                  this.reportNotationDecl(this.val_peek(4), this.pubID, this.sysID);
                  break;
               case 60:
                  this.reportNotationDecl(this.val_peek(6), this.lexer.normalizeValue(this.val_peek(2)), (String)null);
                  break;
               case 61:
                  this.lexer.defineElement(this.elementDefinition.getName(), this.elementDefinition);
                  break;
               case 62:
                  this.elementDefinition = this.lexer.getElement(this.val_peek(0));
                  if (this.elementDefinition == null) {
                     this.elementDefinition = new ElementDefinition(this.val_peek(0));
                  }
                  break;
               case 65:
                  var10001 = this.lexer;
                  this.lexer.yybegin(23);
                  this.addPrefixedAttributeDefinition(this.val_peek(4), this.attributeType, 2, (String)null);
                  break;
               case 66:
                  var10001 = this.lexer;
                  this.lexer.yybegin(23);
                  this.addAttributeDefinition(this.val_peek(4), this.attributeType, 2, (String)null);
                  break;
               case 67:
                  var10001 = this.lexer;
                  this.lexer.yybegin(23);
                  this.addPrefixedAttributeDefinition(this.val_peek(4), this.attributeType, 1, (String)null);
                  break;
               case 68:
                  var10001 = this.lexer;
                  this.lexer.yybegin(23);
                  this.addAttributeDefinition(this.val_peek(4), this.attributeType, 1, (String)null);
                  break;
               case 69:
                  var10001 = this.lexer;
                  this.lexer.yybegin(23);
                  this.addPrefixedAttributeDefinition(this.val_peek(6), this.attributeType, 3, this.val_peek(0));
                  break;
               case 70:
                  var10001 = this.lexer;
                  this.lexer.yybegin(23);
                  this.addAttributeDefinition(this.val_peek(6), this.attributeType, 3, this.val_peek(0));
                  break;
               case 71:
                  var10001 = this.lexer;
                  this.lexer.yybegin(23);
                  this.addPrefixedAttributeDefinition(this.val_peek(4), this.attributeType, 0, this.val_peek(0));
                  break;
               case 72:
                  var10001 = this.lexer;
                  this.lexer.yybegin(23);
                  this.addAttributeDefinition(this.val_peek(4), this.attributeType, 0, this.val_peek(0));
                  break;
               case 73:
                  this.attributeType = 3;
                  break;
               case 74:
                  this.attributeType = 4;
                  break;
               case 75:
                  this.attributeType = 5;
                  break;
               case 76:
                  this.attributeType = 6;
                  break;
               case 77:
                  this.attributeType = 7;
                  break;
               case 78:
                  this.attributeType = 8;
                  break;
               case 79:
                  this.attributeType = 9;
                  break;
               case 80:
                  this.attributeType = 10;
                  break;
               case 81:
                  this.attributeType = 1;
                  break;
               case 82:
                  this.attributeType = 2;
                  break;
               case 83:
                  if (this.declHandler != null) {
                     this.modelBuffer.append(this.val_peek(0));
                  }
                  break;
               case 84:
                  if (this.declHandler != null) {
                     this.modelBuffer.append('|');
                     this.modelBuffer.append(this.val_peek(0));
                  }
                  break;
               case 85:
                  if (this.declHandler != null) {
                     this.declHandler.elementDecl(this.val_peek(4), this.val_peek(2));
                  }
                  break;
               case 86:
                  if (this.declHandler != null) {
                     this.yyval = "EMPTY";
                  }
                  break;
               case 87:
                  if (this.declHandler != null) {
                     this.yyval = "ANY";
                  }
                  break;
               case 88:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(0);
                  }
                  break;
               case 89:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(0);
                  }
                  break;
               case 90:
                  if (this.declHandler != null) {
                     this.yyval = "(#PCDATA)*";
                  }
                  break;
               case 91:
                  if (this.declHandler != null) {
                     this.yyval = "(#PCDATA)";
                  }
                  break;
               case 92:
                  if (this.declHandler != null) {
                     this.yyval = "(#PCDATA|" + this.modelBuffer.toString() + ")*";
                  }
                  break;
               case 93:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(0);
                  }
                  break;
               case 94:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(1) + this.val_peek(0);
                  }
                  break;
               case 95:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(1) + this.val_peek(0);
                  }
                  break;
               case 96:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(0);
                  }
                  break;
               case 97:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(4) + "|" + this.val_peek(0);
                  }
                  break;
               case 98:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(4) + "|" + this.val_peek(0);
                  }
                  break;
               case 99:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(0);
                  }
                  break;
               case 100:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(3) + "," + this.val_peek(0);
                  }
                  break;
               case 101:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(2) + this.val_peek(1);
                  }
                  break;
               case 102:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(2) + this.val_peek(1);
                  }
                  break;
               case 103:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(2) + this.val_peek(1);
                  }
                  break;
               case 104:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(0);
                  }
                  break;
               case 105:
                  if (this.declHandler != null) {
                     this.yyval = "(" + this.val_peek(2) + ")";
                  }
                  break;
               case 106:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(0);
                  }
                  break;
               case 107:
                  if (this.declHandler != null) {
                     this.yyval = "(" + this.val_peek(2) + ")";
                  }
                  break;
               case 108:
                  if (this.declHandler != null) {
                     this.yyval = this.val_peek(0);
                  }
                  break;
               case 109:
                  if (this.declHandler != null) {
                     this.yyval = "?";
                  }
                  break;
               case 110:
                  if (this.declHandler != null) {
                     this.yyval = "*";
                  }
                  break;
               case 111:
                  if (this.declHandler != null) {
                     this.yyval = "+";
                  }
                  break;
               case 112:
                  if (this.declHandler != null) {
                     this.yyval = "";
                  }
                  break;
               case 114:
                  this.lexer.setTokenize(false);
                  break;
               case 115:
                  this.lexer.setTokenize(false);
                  break;
               case 116:
                  this.lexer.setTokenize(false);
                  break;
               case 121:
                  this.reportWhitespace();
            }

            this.state_drop(this.yym);
            this.yystate = this.state_peek(0);
            this.val_drop(this.yym);
            this.yym = yylhs[this.yyn];
            if (this.yystate == 0 && this.yym == 0) {
               this.yystate = 3;
               this.state_push(3);
               this.val_push(this.yyval);
               if (this.yychar < 0) {
                  this.yychar = this.yylex();
               }
               break;
            }

            this.yyn = yygindex[this.yym];
            if (this.yyn != 0 && (this.yyn += this.yystate) >= 0 && this.yyn <= 508 && yycheck[this.yyn] == this.yystate) {
               this.yystate = yytable[this.yyn];
            } else {
               this.yystate = yydgoto[this.yym];
            }

            this.state_push(this.yystate);
            this.val_push(this.yyval);
         }
      } while(this.yychar != 0);

      return 0;
   }

   private class StartLocator implements Locator {
      private StartLocator() {
      }

      public int getLineNumber() {
         return Piccolo.this.lexer.tokenStartLine;
      }

      public int getColumnNumber() {
         return Piccolo.this.lexer.tokenStartColumn;
      }

      public String getPublicId() {
         return null;
      }

      public String getSystemId() {
         return null;
      }

      // $FF: synthetic method
      StartLocator(Object x1) {
         this();
      }
   }
}
