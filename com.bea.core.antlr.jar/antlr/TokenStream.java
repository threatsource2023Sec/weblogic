package antlr;

public interface TokenStream {
   Token nextToken() throws TokenStreamException;
}
