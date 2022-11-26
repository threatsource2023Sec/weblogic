package org.glassfish.tyrus.core.uri.internal;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UriTemplate {
   private static final String[] EMPTY_VALUES = new String[0];
   public static final Comparator COMPARATOR = new Comparator() {
      public int compare(UriTemplate o1, UriTemplate o2) {
         if (o1 == null && o2 == null) {
            return 0;
         } else if (o1 == null) {
            return 1;
         } else if (o2 == null) {
            return -1;
         } else if (o1 == UriTemplate.EMPTY && o2 == UriTemplate.EMPTY) {
            return 0;
         } else if (o1 == UriTemplate.EMPTY) {
            return 1;
         } else if (o2 == UriTemplate.EMPTY) {
            return -1;
         } else {
            int i = o2.getNumberOfExplicitCharacters() - o1.getNumberOfExplicitCharacters();
            if (i != 0) {
               return i;
            } else {
               i = o2.getNumberOfTemplateVariables() - o1.getNumberOfTemplateVariables();
               if (i != 0) {
                  return i;
               } else {
                  i = o2.getNumberOfExplicitRegexes() - o1.getNumberOfExplicitRegexes();
                  return i != 0 ? i : o2.pattern.getRegex().compareTo(o1.pattern.getRegex());
               }
            }
         }
      }
   };
   private static final Pattern TEMPLATE_NAMES_PATTERN = Pattern.compile("\\{([\\w\\?;][-\\w\\.,]*)\\}");
   public static final UriTemplate EMPTY = new UriTemplate();
   private final String template;
   private final String normalizedTemplate;
   private final PatternWithGroups pattern;
   private final boolean endsWithSlash;
   private final List templateVariables;
   private final int numOfExplicitRegexes;
   private final int numOfRegexGroups;
   private final int numOfCharacters;

   private UriTemplate() {
      this.template = this.normalizedTemplate = "";
      this.pattern = PatternWithGroups.EMPTY;
      this.endsWithSlash = false;
      this.templateVariables = Collections.emptyList();
      this.numOfExplicitRegexes = this.numOfCharacters = this.numOfRegexGroups = 0;
   }

   public UriTemplate(String template) throws PatternSyntaxException, IllegalArgumentException {
      this(new UriTemplateParser(template));
   }

   protected UriTemplate(UriTemplateParser templateParser) throws PatternSyntaxException, IllegalArgumentException {
      this.template = templateParser.getTemplate();
      this.normalizedTemplate = templateParser.getNormalizedTemplate();
      this.pattern = initUriPattern(templateParser);
      this.numOfExplicitRegexes = templateParser.getNumberOfExplicitRegexes();
      this.numOfRegexGroups = templateParser.getNumberOfRegexGroups();
      this.numOfCharacters = templateParser.getNumberOfLiteralCharacters();
      this.endsWithSlash = this.template.charAt(this.template.length() - 1) == '/';
      this.templateVariables = Collections.unmodifiableList(templateParser.getNames());
   }

   private static PatternWithGroups initUriPattern(UriTemplateParser templateParser) {
      return new PatternWithGroups(templateParser.getPattern(), templateParser.getGroupIndexes());
   }

   public static URI resolve(URI baseUri, String refUri) {
      return resolve(baseUri, URI.create(refUri));
   }

   public static URI resolve(URI baseUri, URI refUri) {
      if (baseUri == null) {
         throw new NullPointerException("Input base URI parameter must not be null.");
      } else if (refUri == null) {
         throw new NullPointerException("Input reference URI parameter must not be null.");
      } else {
         String refString = refUri.toString();
         if (refString.isEmpty()) {
            refUri = URI.create("#");
         } else if (refString.startsWith("?")) {
            String baseString = baseUri.toString();
            int qIndex = baseString.indexOf(63);
            baseString = qIndex > -1 ? baseString.substring(0, qIndex) : baseString;
            return URI.create(baseString + refString);
         }

         URI result = baseUri.resolve(refUri);
         if (refString.isEmpty()) {
            String resolvedString = result.toString();
            result = URI.create(resolvedString.substring(0, resolvedString.indexOf(35)));
         }

         return normalize(result);
      }
   }

   public static URI normalize(String uri) {
      return normalize(URI.create(uri));
   }

   public static URI normalize(URI uri) {
      if (uri == null) {
         throw new NullPointerException("Input reference URI parameter must not be null.");
      } else {
         String path = uri.getPath();
         if (path != null && !path.isEmpty() && path.contains("/.")) {
            String[] segments = path.split("/");
            Deque resolvedSegments = new ArrayDeque(segments.length);
            String[] var4 = segments;
            int var5 = segments.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String segment = var4[var6];
               if (!segment.isEmpty() && !".".equals(segment)) {
                  if ("..".equals(segment)) {
                     resolvedSegments.pollLast();
                  } else {
                     resolvedSegments.offer(segment);
                  }
               }
            }

            StringBuilder pathBuilder = new StringBuilder();
            Iterator var9 = resolvedSegments.iterator();

            while(var9.hasNext()) {
               String segment = (String)var9.next();
               pathBuilder.append('/').append(segment);
            }

            String resultString = createURIWithStringValues(uri.getScheme(), uri.getAuthority(), (String)null, (String)null, (String)null, pathBuilder.toString(), uri.getQuery(), uri.getFragment(), (String[])EMPTY_VALUES, false, false);
            return URI.create(resultString);
         } else {
            return uri;
         }
      }
   }

   public static URI relativize(URI baseUri, URI refUri) {
      if (baseUri == null) {
         throw new NullPointerException("Input base URI parameter must not be null.");
      } else if (refUri == null) {
         throw new NullPointerException("Input reference URI parameter must not be null.");
      } else {
         return normalize(baseUri.relativize(refUri));
      }
   }

   public final String getTemplate() {
      return this.template;
   }

   public final PatternWithGroups getPattern() {
      return this.pattern;
   }

   public final boolean endsWithSlash() {
      return this.endsWithSlash;
   }

   public final List getTemplateVariables() {
      return this.templateVariables;
   }

   public final boolean isTemplateVariablePresent(String name) {
      Iterator var2 = this.templateVariables.iterator();

      String s;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         s = (String)var2.next();
      } while(!s.equals(name));

      return true;
   }

   public final int getNumberOfExplicitRegexes() {
      return this.numOfExplicitRegexes;
   }

   public final int getNumberOfRegexGroups() {
      return this.numOfRegexGroups;
   }

   public final int getNumberOfExplicitCharacters() {
      return this.numOfCharacters;
   }

   public final int getNumberOfTemplateVariables() {
      return this.templateVariables.size();
   }

   public final boolean match(CharSequence uri, Map templateVariableToValue) throws IllegalArgumentException {
      if (templateVariableToValue == null) {
         throw new IllegalArgumentException();
      } else {
         return this.pattern.match(uri, this.templateVariables, templateVariableToValue);
      }
   }

   public final boolean match(CharSequence uri, List groupValues) throws IllegalArgumentException {
      if (groupValues == null) {
         throw new IllegalArgumentException();
      } else {
         return this.pattern.match(uri, groupValues);
      }
   }

   public final String createURI(final Map values) {
      StringBuilder sb = new StringBuilder();
      resolveTemplate(this.normalizedTemplate, sb, new TemplateValueStrategy() {
         public String valueFor(String templateVariable, String matchedGroup) {
            return (String)values.get(templateVariable);
         }
      });
      return sb.toString();
   }

   public final String createURI(String... values) {
      return this.createURI(values, 0, values.length);
   }

   public final String createURI(final String[] values, final int offset, final int length) {
      TemplateValueStrategy ns = new TemplateValueStrategy() {
         private final int lengthPlusOffset = length + offset;
         private int v = offset;
         private final Map mapValues = new HashMap();

         public String valueFor(String templateVariable, String matchedGroup) {
            String tValue = (String)this.mapValues.get(templateVariable);
            if (tValue == null && this.v < this.lengthPlusOffset) {
               tValue = values[this.v++];
               if (tValue != null) {
                  this.mapValues.put(templateVariable, tValue);
               }
            }

            return tValue;
         }
      };
      StringBuilder sb = new StringBuilder();
      resolveTemplate(this.normalizedTemplate, sb, ns);
      return sb.toString();
   }

   private static void resolveTemplate(String normalizedTemplate, StringBuilder builder, TemplateValueStrategy valueStrategy) {
      Matcher m = TEMPLATE_NAMES_PATTERN.matcher(normalizedTemplate);

      int i;
      for(i = 0; m.find(); i = m.end()) {
         builder.append(normalizedTemplate, i, m.start());
         String variableName = m.group(1);
         char firstChar = variableName.charAt(0);
         if (firstChar != '?' && firstChar != ';') {
            String value = valueStrategy.valueFor(variableName, m.group());
            if (value != null) {
               builder.append(value);
            }
         } else {
            char prefix;
            char separator;
            String emptyValueAssignment;
            if (firstChar == '?') {
               prefix = '?';
               separator = '&';
               emptyValueAssignment = "=";
            } else {
               prefix = ';';
               separator = ';';
               emptyValueAssignment = "";
            }

            int index = builder.length();
            String[] variables = variableName.substring(1).split(", ?");
            String[] var12 = variables;
            int var13 = variables.length;

            for(int var14 = 0; var14 < var13; ++var14) {
               String variable = var12[var14];

               try {
                  String value = valueStrategy.valueFor(variable, m.group());
                  if (value != null) {
                     if (index != builder.length()) {
                        builder.append(separator);
                     }

                     builder.append(variable);
                     if (value.isEmpty()) {
                        builder.append(emptyValueAssignment);
                     } else {
                        builder.append('=');
                        builder.append(value);
                     }
                  }
               } catch (IllegalArgumentException var17) {
               }
            }

            if (index != builder.length() && (index == 0 || builder.charAt(index - 1) != prefix)) {
               builder.insert(index, prefix);
            }
         }
      }

      builder.append(normalizedTemplate, i, normalizedTemplate.length());
   }

   public final String toString() {
      return this.pattern.toString();
   }

   public final int hashCode() {
      return this.pattern.hashCode();
   }

   public final boolean equals(Object o) {
      if (o instanceof UriTemplate) {
         UriTemplate that = (UriTemplate)o;
         return this.pattern.equals(that.pattern);
      } else {
         return false;
      }
   }

   public static String createURI(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, Map values, boolean encode, boolean encodeSlashInPath) {
      Map stringValues = new HashMap();
      Iterator var12 = values.entrySet().iterator();

      while(var12.hasNext()) {
         Map.Entry e = (Map.Entry)var12.next();
         if (e.getValue() != null) {
            stringValues.put(e.getKey(), e.getValue().toString());
         }
      }

      return createURIWithStringValues(scheme, authority, userInfo, host, port, path, query, fragment, (Map)stringValues, encode, encodeSlashInPath);
   }

   public static String createURIWithStringValues(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, Map values, boolean encode, boolean encodeSlashInPath) {
      return createURIWithStringValues(scheme, authority, userInfo, host, port, path, query, fragment, EMPTY_VALUES, encode, encodeSlashInPath, values);
   }

   public static String createURI(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, Object[] values, boolean encode, boolean encodeSlashInPath) {
      String[] stringValues = new String[values.length];

      for(int i = 0; i < values.length; ++i) {
         if (values[i] != null) {
            stringValues[i] = values[i].toString();
         }
      }

      return createURIWithStringValues(scheme, authority, userInfo, host, port, path, query, fragment, stringValues, encode, encodeSlashInPath);
   }

   public static String createURIWithStringValues(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, String[] values, boolean encode, boolean encodeSlashInPath) {
      Map mapValues = new HashMap();
      return createURIWithStringValues(scheme, authority, userInfo, host, port, path, query, fragment, values, encode, encodeSlashInPath, mapValues);
   }

   private static String createURIWithStringValues(String scheme, String authority, String userInfo, String host, String port, String path, String query, String fragment, String[] values, boolean encode, boolean encodeSlashInPath, Map mapValues) {
      StringBuilder sb = new StringBuilder();
      int offset = 0;
      if (scheme != null) {
         offset = createUriComponent(UriComponent.Type.SCHEME, scheme, values, offset, false, mapValues, sb);
         sb.append(':');
      }

      boolean hasAuthority = false;
      if (!notEmpty(userInfo) && !notEmpty(host) && !notEmpty(port)) {
         if (notEmpty(authority)) {
            hasAuthority = true;
            sb.append("//");
            offset = createUriComponent(UriComponent.Type.AUTHORITY, authority, values, offset, encode, mapValues, sb);
         }
      } else {
         hasAuthority = true;
         sb.append("//");
         if (notEmpty(userInfo)) {
            offset = createUriComponent(UriComponent.Type.USER_INFO, userInfo, values, offset, encode, mapValues, sb);
            sb.append('@');
         }

         if (notEmpty(host)) {
            offset = createUriComponent(UriComponent.Type.HOST, host, values, offset, encode, mapValues, sb);
         }

         if (notEmpty(port)) {
            sb.append(':');
            offset = createUriComponent(UriComponent.Type.PORT, port, values, offset, false, mapValues, sb);
         }
      }

      if (notEmpty(path) || notEmpty(query) || notEmpty(fragment)) {
         if (hasAuthority && (path == null || path.isEmpty() || path.charAt(0) != '/')) {
            sb.append('/');
         }

         if (notEmpty(path)) {
            UriComponent.Type t = encodeSlashInPath ? UriComponent.Type.PATH_SEGMENT : UriComponent.Type.PATH;
            offset = createUriComponent(t, path, values, offset, encode, mapValues, sb);
         }

         if (notEmpty(query)) {
            sb.append('?');
            offset = createUriComponent(UriComponent.Type.QUERY_PARAM, query, values, offset, encode, mapValues, sb);
         }

         if (notEmpty(fragment)) {
            sb.append('#');
            createUriComponent(UriComponent.Type.FRAGMENT, fragment, values, offset, encode, mapValues, sb);
         }
      }

      return sb.toString();
   }

   private static boolean notEmpty(String string) {
      return string != null && !string.isEmpty();
   }

   private static int createUriComponent(final UriComponent.Type componentType, String template, final String[] values, final int valueOffset, final boolean encode, final Map _mapValues, StringBuilder b) {
      if (template.indexOf(123) == -1) {
         b.append(template);
         return valueOffset;
      } else {
         template = (new UriTemplateParser(template)).getNormalizedTemplate();

         class ValuesFromArrayStrategy implements TemplateValueStrategy {
            private int offset = valueOffset;

            public String valueFor(String templateVariable, String matchedGroup) {
               Object value = _mapValues.get(templateVariable);
               if (value == null && this.offset < values.length) {
                  value = values[this.offset++];
                  _mapValues.put(templateVariable, value);
               }

               if (value == null) {
                  throw new IllegalArgumentException(String.format("The template variable '%s' has no value", templateVariable));
               } else {
                  return encode ? UriComponent.encode(value.toString(), componentType) : UriComponent.contextualEncode(value.toString(), componentType);
               }
            }
         }

         ValuesFromArrayStrategy cs = new ValuesFromArrayStrategy();
         resolveTemplate(template, b, cs);
         return cs.offset;
      }
   }

   public static String resolveTemplateValues(final UriComponent.Type type, String template, final boolean encode, final Map _mapValues) {
      if (template != null && !template.isEmpty() && template.indexOf(123) != -1) {
         template = (new UriTemplateParser(template)).getNormalizedTemplate();
         StringBuilder sb = new StringBuilder();
         resolveTemplate(template, sb, new TemplateValueStrategy() {
            public String valueFor(String templateVariable, String matchedGroup) {
               Object value = _mapValues.get(templateVariable);
               if (value != null) {
                  String valuex;
                  if (encode) {
                     valuex = UriComponent.encode(value.toString(), type);
                  } else {
                     valuex = UriComponent.contextualEncode(value.toString(), type);
                  }

                  return valuex.toString();
               } else if (_mapValues.containsKey(templateVariable)) {
                  throw new IllegalArgumentException(String.format("The value associated of the template value map for key '%s' is 'null'.", templateVariable));
               } else {
                  return matchedGroup;
               }
            }
         });
         return sb.toString();
      } else {
         return template;
      }
   }

   private interface TemplateValueStrategy {
      String valueFor(String var1, String var2);
   }
}
