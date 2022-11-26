package org.python.core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.UnsupportedCharsetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.python.antlr.BaseParser;
import org.python.antlr.NoCloseReaderStream;
import org.python.antlr.ParseException;
import org.python.antlr.PythonPartialLexer;
import org.python.antlr.PythonPartialParser;
import org.python.antlr.PythonTokenSource;
import org.python.antlr.PythonTree;
import org.python.antlr.base.mod;
import org.python.antlr.runtime.CharStream;
import org.python.antlr.runtime.CommonTokenStream;
import org.python.core.io.StreamIO;
import org.python.core.io.TextIOInputStream;
import org.python.core.io.UniversalIOWrapper;
import org.python.core.util.StringUtil;

public class ParserFacade {
   private static int MARK_LIMIT = 100000;
   private static final Pattern pep263EncodingPattern = Pattern.compile("#.*coding[:=]\\s*([-\\w.]+)");

   private ParserFacade() {
   }

   private static String getLine(ExpectedEncodingBufferedReader reader, int line) {
      if (reader == null) {
         return "";
      } else {
         String text = null;

         try {
            for(int i = 0; i < line; ++i) {
               text = reader.readLine();
            }

            if (text == null) {
               return text;
            } else {
               if (reader.encoding != null) {
                  Charset cs = Charset.forName(reader.encoding);
                  ByteBuffer decoded = cs.encode(text);
                  text = StringUtil.fromBytes(decoded);
               }

               return text + "\n";
            }
         } catch (IOException var5) {
            return text;
         }
      }
   }

   public static PyException fixParseError(ExpectedEncodingBufferedReader reader, Throwable t, String filename) {
      if (reader != null) {
         try {
            reader.reset();
         } catch (IOException var9) {
            reader = null;
         }
      }

      if (t instanceof ParseException) {
         ParseException e = (ParseException)t;
         PythonTree node = (PythonTree)e.node;
         int line = e.line;
         int col = e.charPositionInLine;
         if (node != null) {
            line = node.getLine();
            col = node.getCharPositionInLine();
         }

         String text = getLine(reader, line);
         String msg = e.getMessage();
         return (PyException)(e.getType() == Py.IndentationError ? new PyIndentationError(msg, line, col, text, filename) : new PySyntaxError(msg, line, col, text, filename));
      } else if (t instanceof CharacterCodingException) {
         String msg;
         if (reader.encoding == null) {
            msg = "Non-ASCII character in file '" + filename + "', but no encoding declared" + "; see http://www.python.org/peps/pep-0263.html for details";
         } else {
            msg = "Illegal character in file '" + filename + "' for encoding '" + reader.encoding + "'";
         }

         throw Py.SyntaxError(msg);
      } else {
         return Py.JavaError(t);
      }
   }

   public static mod parseExpressionOrModule(Reader reader, String filename, CompilerFlags cflags) {
      ExpectedEncodingBufferedReader bufReader = null;

      try {
         bufReader = prepBufReader(reader, cflags, filename);
         return parse(bufReader, CompileMode.eval, filename, cflags);
      } catch (Throwable var7) {
         if (bufReader == null) {
            throw Py.JavaError(var7);
         } else {
            try {
               bufReader.reset();
               return parse(bufReader, CompileMode.exec, filename, cflags);
            } catch (Throwable var6) {
               throw fixParseError(bufReader, var6, filename);
            }
         }
      }
   }

   private static mod parse(ExpectedEncodingBufferedReader reader, CompileMode kind, String filename, CompilerFlags cflags) throws Throwable {
      reader.mark(MARK_LIMIT);
      if (kind != null) {
         CharStream cs = new NoCloseReaderStream(reader);
         BaseParser parser = new BaseParser(cs, filename, cflags.encoding);
         return kind.dispatch(parser);
      } else {
         throw Py.ValueError("parse kind must be eval, exec, or single");
      }
   }

   public static mod parse(Reader reader, CompileMode kind, String filename, CompilerFlags cflags) {
      ExpectedEncodingBufferedReader bufReader = null;

      mod var5;
      try {
         bufReader = prepBufReader(reader, cflags, filename);
         var5 = parse(bufReader, kind, filename, cflags);
      } catch (Throwable var9) {
         throw fixParseError(bufReader, var9, filename);
      } finally {
         close(bufReader);
      }

      return var5;
   }

   public static mod parse(InputStream stream, CompileMode kind, String filename, CompilerFlags cflags) {
      ExpectedEncodingBufferedReader bufReader = null;

      mod var5;
      try {
         bufReader = prepBufReader(stream, cflags, filename, false);
         var5 = parse(bufReader, kind, filename, cflags);
      } catch (Throwable var9) {
         throw fixParseError(bufReader, var9, filename);
      } finally {
         close(bufReader);
      }

      return var5;
   }

   public static mod parse(String string, CompileMode kind, String filename, CompilerFlags cflags) {
      ExpectedEncodingBufferedReader bufReader = null;

      mod var5;
      try {
         bufReader = prepBufReader(string, cflags, filename);
         var5 = parse(bufReader, kind, filename, cflags);
      } catch (Throwable var9) {
         throw fixParseError(bufReader, var9, filename);
      } finally {
         close(bufReader);
      }

      return var5;
   }

   public static mod partialParse(String string, CompileMode kind, String filename, CompilerFlags cflags, boolean stdprompt) {
      ExpectedEncodingBufferedReader reader = null;

      Object var8;
      try {
         reader = prepBufReader(string, cflags, filename);
         mod var6 = parse(reader, kind, filename, cflags);
         return var6;
      } catch (Throwable var12) {
         PyException p = fixParseError(reader, var12, filename);
         if (reader == null || !validPartialSentence(reader, kind, filename)) {
            throw p;
         }

         var8 = null;
      } finally {
         close(reader);
      }

      return (mod)var8;
   }

   private static boolean validPartialSentence(BufferedReader bufreader, CompileMode kind, String filename) {
      PythonPartialLexer lexer = null;

      try {
         bufreader.reset();
         CharStream cs = new NoCloseReaderStream(bufreader);
         lexer = new PythonPartialLexer(cs);
         CommonTokenStream tokens = new CommonTokenStream(lexer);
         PythonTokenSource indentedSource = new PythonTokenSource(tokens, filename);
         tokens = new CommonTokenStream(indentedSource);
         PythonPartialParser parser = new PythonPartialParser(tokens);
         switch (kind) {
            case single:
               parser.single_input();
               break;
            case eval:
               parser.eval_input();
               break;
            default:
               return false;
         }

         return true;
      } catch (Exception var8) {
         return lexer.eofWhileNested;
      }
   }

   private static ExpectedEncodingBufferedReader prepBufReader(Reader reader, CompilerFlags cflags, String filename) throws IOException {
      cflags.source_is_utf8 = true;
      cflags.encoding = "utf-8";
      BufferedReader bufferedReader = new BufferedReader(reader);
      bufferedReader.mark(MARK_LIMIT);
      if (findEncoding(bufferedReader) != null) {
         throw new ParseException("encoding declaration in Unicode string");
      } else {
         bufferedReader.reset();
         return new ExpectedEncodingBufferedReader(bufferedReader, (String)null);
      }
   }

   private static ExpectedEncodingBufferedReader prepBufReader(InputStream input, CompilerFlags cflags, String filename, boolean fromString) throws IOException {
      return prepBufReader(input, cflags, filename, fromString, true);
   }

   private static ExpectedEncodingBufferedReader prepBufReader(InputStream input, CompilerFlags cflags, String filename, boolean fromString, boolean universalNewlines) throws IOException {
      InputStream input = new BufferedInputStream(input);
      boolean bom = adjustForBOM((InputStream)input);
      String encoding = readEncoding((InputStream)input);
      if (encoding == null) {
         if (bom) {
            encoding = "utf-8";
         } else if (cflags != null && cflags.encoding != null) {
            encoding = cflags.encoding;
         }
      }

      if (cflags.source_is_utf8) {
         if (encoding != null) {
            throw new ParseException("encoding declaration in Unicode string");
         }

         encoding = "utf-8";
      }

      cflags.encoding = encoding;
      if (universalNewlines) {
         StreamIO rawIO = new StreamIO((InputStream)input, true);
         org.python.core.io.BufferedReader bufferedIO = new org.python.core.io.BufferedReader(rawIO, 0);
         UniversalIOWrapper textIO = new UniversalIOWrapper(bufferedIO);
         input = new TextIOInputStream(textIO);
      }

      Charset cs;
      try {
         if (encoding == null) {
            if (fromString) {
               cs = Charset.forName("ISO-8859-1");
            } else {
               cs = Charset.forName("ascii");
            }
         } else {
            cs = Charset.forName(encoding);
         }
      } catch (UnsupportedCharsetException var10) {
         throw new PySyntaxError("Unknown encoding: " + encoding, 1, 0, "", filename);
      }

      CharsetDecoder dec = cs.newDecoder();
      dec.onMalformedInput(CodingErrorAction.REPORT);
      dec.onUnmappableCharacter(CodingErrorAction.REPORT);
      return new ExpectedEncodingBufferedReader(new InputStreamReader((InputStream)input, dec), encoding);
   }

   private static ExpectedEncodingBufferedReader prepBufReader(String string, CompilerFlags cflags, String filename) throws IOException {
      if (cflags.source_is_utf8) {
         return prepBufReader((Reader)(new StringReader(string)), cflags, filename);
      } else {
         byte[] stringBytes = StringUtil.toBytes(string);
         return prepBufReader(new ByteArrayInputStream(stringBytes), cflags, filename, true, false);
      }
   }

   private static boolean adjustForBOM(InputStream stream) throws IOException {
      stream.mark(3);
      int ch = stream.read();
      if (ch == 239) {
         if (stream.read() != 187) {
            throw new ParseException("Incomplete BOM at beginning of file");
         } else if (stream.read() != 191) {
            throw new ParseException("Incomplete BOM at beginning of file");
         } else {
            return true;
         }
      } else {
         stream.reset();
         return false;
      }
   }

   private static String readEncoding(InputStream stream) throws IOException {
      stream.mark(MARK_LIMIT);
      String encoding = null;
      BufferedReader br = new BufferedReader(new InputStreamReader(stream, "ISO-8859-1"), 512);
      encoding = findEncoding(br);
      stream.reset();
      return encodingMap(encoding);
   }

   private static String findEncoding(BufferedReader br) throws IOException {
      String encoding = null;

      for(int i = 0; i < 2; ++i) {
         String strLine = br.readLine();
         if (strLine == null) {
            break;
         }

         String result = matchEncoding(strLine);
         if (result != null) {
            encoding = result;
            break;
         }
      }

      return encoding;
   }

   private static String encodingMap(String encoding) {
      if (encoding == null) {
         return null;
      } else {
         return !encoding.equals("Latin-1") && !encoding.equals("latin-1") ? encoding : "ISO8859_1";
      }
   }

   private static String matchEncoding(String inputStr) {
      Matcher matcher = pep263EncodingPattern.matcher(inputStr);
      boolean matchFound = matcher.find();
      if (matchFound && matcher.groupCount() == 1) {
         String groupStr = matcher.group(1);
         return groupStr;
      } else {
         return null;
      }
   }

   private static void close(BufferedReader reader) {
      try {
         if (reader != null) {
            reader.close();
         }
      } catch (IOException var2) {
      }

   }

   private static class ExpectedEncodingBufferedReader extends BufferedReader {
      public final String encoding;

      public ExpectedEncodingBufferedReader(Reader in, String encoding) {
         super(in);
         this.encoding = encoding;
      }
   }
}
