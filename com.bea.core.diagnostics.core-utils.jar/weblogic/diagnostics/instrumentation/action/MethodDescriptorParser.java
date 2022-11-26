package weblogic.diagnostics.instrumentation.action;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MethodDescriptorParser extends LLkParser implements MethodDescriptorParserTokenTypes {
   private List inputParameters;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "WS", "LPAREN", "RPAREN", "BYTE", "BOOLEAN", "CHAR", "DOUBLE", "FLOAT", "INT", "LONG", "SHORT", "VOID", "ARRAY_PREFIX", "REFERENCE_TYPE_PREFIX", "SEMI_COLON", "CLASS_NAME"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

   public List getInputParameters() {
      return this.inputParameters;
   }

   public static void main(String[] args) throws Exception {
      if (args.length != 1) {
         System.err.println("Invalid number of arguments");
         System.exit(1);
      }

      MethodDescriptorLexer l = new MethodDescriptorLexer(new StringReader(args[0]));
      MethodDescriptorParser p = new MethodDescriptorParser(l);
      p.methodDescriptor();
      System.out.println("Parsed method descriptor: " + p.inputParameters);
   }

   protected MethodDescriptorParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.inputParameters = new ArrayList();
      this.tokenNames = _tokenNames;
   }

   public MethodDescriptorParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected MethodDescriptorParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.inputParameters = new ArrayList();
      this.tokenNames = _tokenNames;
   }

   public MethodDescriptorParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public MethodDescriptorParser(ParserSharedInputState state) {
      super(state, 1);
      this.inputParameters = new ArrayList();
      this.tokenNames = _tokenNames;
   }

   public final void methodDescriptor() throws RecognitionException, TokenStreamException {
      this.match(5);
      this.inputParams();
      this.match(6);
      this.anyType();
   }

   public final void inputParams() throws RecognitionException, TokenStreamException {
      while(_tokenSet_0.member(this.LA(1))) {
         String a = this.arrayType();
         this.inputParameters.add(a);
      }

   }

   public final void anyType() throws RecognitionException, TokenStreamException {
      switch (this.LA(1)) {
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 16:
         case 19:
            this.arrayType();
            break;
         case 15:
            this.match(15);
            break;
         case 17:
         case 18:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

   }

   public final String arrayType() throws RecognitionException, TokenStreamException {
      String r = null;
      Token a = null;

      String arrayNotation;
      for(arrayNotation = ""; this.LA(1) == 16; arrayNotation = arrayNotation + "[]") {
         a = this.LT(1);
         this.match(16);
      }

      String param = this.inputArgType();
      r = param + arrayNotation;
      return r;
   }

   public final String inputArgType() throws RecognitionException, TokenStreamException {
      String r = null;
      switch (this.LA(1)) {
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
            r = this.primitiveType();
            break;
         case 15:
         case 16:
         case 17:
         case 18:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 19:
            r = this.referenceType();
      }

      return r;
   }

   public final String primitiveType() throws RecognitionException, TokenStreamException {
      String r = null;
      switch (this.LA(1)) {
         case 7:
            this.match(7);
            r = "byte";
            break;
         case 8:
            this.match(8);
            r = "boolean";
            break;
         case 9:
            this.match(9);
            r = "char";
            break;
         case 10:
            this.match(10);
            r = "double";
            break;
         case 11:
            this.match(11);
            r = "float";
            break;
         case 12:
            this.match(12);
            r = "int";
            break;
         case 13:
            this.match(13);
            r = "long";
            break;
         case 14:
            this.match(14);
            r = "short";
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return r;
   }

   public final String referenceType() throws RecognitionException, TokenStreamException {
      String r = null;
      Token c = null;
      c = this.LT(1);
      this.match(19);
      r = c.getText().replace('/', '.');
      return r;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{622464L, 0L};
      return data;
   }
}
