package org.glassfish.tyrus.core.uri.internal;

public final class PathTemplate extends UriTemplate {
   public PathTemplate(String path) {
      super((UriTemplateParser)(new PathTemplateParser(prefixWithSlash(path))));
   }

   private static String prefixWithSlash(String path) {
      return !path.isEmpty() && path.charAt(0) == '/' ? path : "/" + path;
   }

   private static final class PathTemplateParser extends UriTemplateParser {
      public PathTemplateParser(String path) {
         super(path);
      }

      protected String encodeLiteralCharacters(String literalCharacters) {
         return UriComponent.contextualEncode(literalCharacters, UriComponent.Type.PATH);
      }
   }
}
