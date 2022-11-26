package org.glassfish.tyrus.core.uri.internal;

class UriParser {
   private static final String ERROR_STATE = "The parser was not executed yet. Call the parse() method first.";
   private final String input;
   private CharacterIterator ci;
   private String scheme;
   private String userInfo;
   private String host;
   private String port;
   private String query;
   private String path;
   private String fragment;
   private String ssp;
   private String authority;
   private boolean opaque;
   private boolean parserExecuted;

   UriParser(String uri) {
      this.input = uri;
   }

   private String parseComponentWithIP(String delimiters, boolean mayEnd) {
      return this.parseComponent(delimiters, mayEnd, true);
   }

   private String parseComponent(String delimiters, boolean mayEnd) {
      return this.parseComponent(delimiters, mayEnd, false);
   }

   private String parseComponent(String delimiters, boolean mayEnd, boolean isIp) {
      int curlyBracketsCount = 0;
      int squareBracketsCount = 0;
      StringBuilder sb = new StringBuilder();
      boolean endOfInput = false;
      Character c = this.ci.current();

      while(!endOfInput) {
         if (c == '{') {
            ++curlyBracketsCount;
            sb.append(c);
         } else if (c == '}') {
            --curlyBracketsCount;
            sb.append(c);
         } else if (isIp && c == '[') {
            ++squareBracketsCount;
            sb.append(c);
         } else if (isIp && c == ']') {
            --squareBracketsCount;
            sb.append(c);
         } else {
            if (delimiters != null && delimiters.indexOf(c) >= 0 && (!isIp || squareBracketsCount == 0) && curlyBracketsCount == 0) {
               return sb.length() == 0 ? null : sb.toString();
            }

            sb.append(c);
         }

         endOfInput = !this.ci.hasNext();
         if (!endOfInput) {
            c = this.ci.next();
         }
      }

      if (mayEnd) {
         return sb.length() == 0 ? null : sb.toString();
      } else {
         throw new IllegalArgumentException("does not end by a delimiter '" + delimiters + "'");
      }
   }

   public void parse() {
      this.parserExecuted = true;
      this.ci = new CharacterIterator(this.input);
      if (!this.ci.hasNext()) {
         this.path = "";
         this.ssp = "";
      } else {
         this.ci.next();
         String comp = this.parseComponent(":/?#", true);
         if (this.ci.hasNext()) {
            this.ssp = this.ci.getInput().substring(this.ci.pos() + 1);
         }

         this.opaque = false;
         if (this.ci.current() == ':') {
            if (comp == null) {
               throw new IllegalArgumentException(String.format("Expected scheme name at index %d: \"%s\"", this.ci.pos(), this.input));
            }

            this.scheme = comp;
            if (!this.ci.hasNext()) {
               this.path = "";
               this.ssp = "";
               return;
            }

            char c = this.ci.next();
            if (c == '/') {
               this.parseHierarchicalUri();
            } else {
               this.opaque = true;
            }
         } else {
            this.ci.setPosition(0);
            if (this.ci.current() == '/') {
               this.parseHierarchicalUri();
            } else {
               this.parsePath();
            }
         }

      }
   }

   private void parseHierarchicalUri() {
      if (this.ci.hasNext() && this.ci.peek() == '/') {
         this.ci.next();
         this.ci.next();
         this.parseAuthority();
      }

      if (!this.ci.hasNext()) {
         if (this.ci.current() == '/') {
            this.path = "/";
         }

      } else {
         this.parsePath();
      }
   }

   private void parseAuthority() {
      int start = this.ci.pos();
      String comp = this.parseComponentWithIP("@/?#", true);
      if (this.ci.current() == '@') {
         this.userInfo = comp;
         if (!this.ci.hasNext()) {
            return;
         }

         this.ci.next();
         comp = this.parseComponentWithIP(":/?#", true);
      } else {
         this.ci.setPosition(start);
         comp = this.parseComponentWithIP("@:/?#", true);
      }

      this.host = comp;
      if (this.ci.current() == ':') {
         if (!this.ci.hasNext()) {
            return;
         }

         this.ci.next();
         this.port = this.parseComponent("/?#", true);
      }

      this.authority = this.ci.getInput().substring(start, this.ci.pos());
      if (this.authority.length() == 0) {
         this.authority = null;
      }

   }

   private void parsePath() {
      this.path = this.parseComponent("?#", true);
      if (this.ci.current() == '?') {
         if (!this.ci.hasNext()) {
            return;
         }

         this.ci.next();
         this.query = this.parseComponent("#", true);
      }

      if (this.ci.current() == '#') {
         if (!this.ci.hasNext()) {
            return;
         }

         this.ci.next();
         this.fragment = this.parseComponent((String)null, true);
      }

   }

   public String getSsp() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.ssp;
      }
   }

   public String getScheme() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.scheme;
      }
   }

   public String getUserInfo() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.userInfo;
      }
   }

   public String getHost() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.host;
      }
   }

   public String getPort() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.port;
      }
   }

   public String getQuery() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.query;
      }
   }

   public String getPath() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.path;
      }
   }

   public String getFragment() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.fragment;
      }
   }

   public String getAuthority() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.authority;
      }
   }

   public boolean isOpaque() {
      if (!this.parserExecuted) {
         throw new IllegalStateException("The parser was not executed yet. Call the parse() method first.");
      } else {
         return this.opaque;
      }
   }
}
