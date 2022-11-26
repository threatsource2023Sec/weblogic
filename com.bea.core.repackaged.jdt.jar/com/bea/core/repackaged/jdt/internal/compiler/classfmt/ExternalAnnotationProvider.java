package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryElementValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.env.ITypeAnnotationWalker;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LookupEnvironment;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SignatureWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExternalAnnotationProvider {
   public static final String ANNOTATION_FILE_EXTENSION = "eea";
   public static final String CLASS_PREFIX = "class ";
   public static final String SUPER_PREFIX = "super ";
   public static final char NULLABLE = '0';
   public static final char NONNULL = '1';
   public static final char NO_ANNOTATION = '@';
   public static final String ANNOTATION_FILE_SUFFIX = ".eea";
   private static final String TYPE_PARAMETER_PREFIX = " <";
   private String typeName;
   String typeParametersAnnotationSource;
   Map supertypeAnnotationSources;
   private Map methodAnnotationSources;
   private Map fieldAnnotationSources;
   SingleMarkerAnnotation NULLABLE_ANNOTATION;
   SingleMarkerAnnotation NONNULL_ANNOTATION;

   public ExternalAnnotationProvider(InputStream input, String typeName) throws IOException {
      this.typeName = typeName;
      this.initialize(input);
   }

   private void initialize(InputStream input) throws IOException {
      Throwable var2 = null;
      Object var3 = null;

      try {
         LineNumberReader reader = new LineNumberReader(new InputStreamReader(input));

         try {
            assertClassHeader(reader.readLine(), this.typeName);
            String line;
            if ((line = reader.readLine()) != null) {
               if (line.startsWith(" <")) {
                  if ((line = reader.readLine()) == null) {
                     return;
                  }

                  if (line.startsWith(" <")) {
                     this.typeParametersAnnotationSource = line.substring(" <".length());
                     if ((line = reader.readLine()) == null) {
                        return;
                     }
                  }
               }

               String pendingLine;
               do {
                  pendingLine = null;
                  line = line.trim();
                  if (!line.isEmpty()) {
                     label454: {
                        String rawSig = null;
                        String annotSig = null;
                        String selector = line;
                        boolean isSuper = line.startsWith("super ");
                        if (isSuper) {
                           selector = line.substring("super ".length());
                        }

                        int errLine = -1;

                        try {
                           line = reader.readLine();
                           if (line != null && !line.isEmpty() && line.charAt(0) == ' ') {
                              rawSig = line.substring(1);
                           } else {
                              errLine = reader.getLineNumber();
                           }

                           line = reader.readLine();
                           if (line == null || line.isEmpty()) {
                              break label454;
                           }

                           if (line.charAt(0) != ' ') {
                              pendingLine = line;
                              break label454;
                           }

                           annotSig = line.substring(1);
                        } catch (Exception var18) {
                        }

                        if (rawSig == null || annotSig == null) {
                           if (errLine == -1) {
                              errLine = reader.getLineNumber();
                           }

                           throw new IOException("Illegal format in annotation file for " + this.typeName + " at line " + errLine);
                        }

                        annotSig = trimTail(annotSig);
                        if (isSuper) {
                           if (this.supertypeAnnotationSources == null) {
                              this.supertypeAnnotationSources = new HashMap();
                           }

                           this.supertypeAnnotationSources.put('L' + selector + rawSig + ';', annotSig);
                        } else if (rawSig.contains("(")) {
                           if (this.methodAnnotationSources == null) {
                              this.methodAnnotationSources = new HashMap();
                           }

                           this.methodAnnotationSources.put(selector + rawSig, annotSig);
                        } else {
                           if (this.fieldAnnotationSources == null) {
                              this.fieldAnnotationSources = new HashMap();
                           }

                           this.fieldAnnotationSources.put(selector + ':' + rawSig, annotSig);
                        }
                     }
                  }

                  line = pendingLine;
               } while(pendingLine != null || (line = reader.readLine()) != null);

               return;
            }
         } finally {
            if (reader != null) {
               reader.close();
            }

         }

      } catch (Throwable var20) {
         if (var2 == null) {
            var2 = var20;
         } else if (var2 != var20) {
            var2.addSuppressed(var20);
         }

         throw var2;
      }
   }

   public static void assertClassHeader(String line, String typeName) throws IOException {
      if (line != null && line.startsWith("class ")) {
         line = line.substring("class ".length());
         if (!trimTail(line).equals(typeName)) {
            throw new IOException("mismatching class name in annotation file, expected " + typeName + ", but header said " + line);
         }
      } else {
         throw new IOException("missing class header in annotation file for " + typeName);
      }
   }

   public static String extractSignature(String line) {
      return line != null && !line.isEmpty() && line.charAt(0) == ' ' ? trimTail(line.substring(1)) : null;
   }

   protected static String trimTail(String line) {
      int tail = line.indexOf(32);
      if (tail == -1) {
         tail = line.indexOf(9);
      }

      return tail != -1 ? line.substring(0, tail) : line;
   }

   public ITypeAnnotationWalker forTypeHeader(LookupEnvironment environment) {
      return (ITypeAnnotationWalker)(this.typeParametersAnnotationSource == null && this.supertypeAnnotationSources == null ? ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER : new DispatchingAnnotationWalker(environment));
   }

   public ITypeAnnotationWalker forMethod(char[] selector, char[] signature, LookupEnvironment environment) {
      Map sources = this.methodAnnotationSources;
      if (sources != null) {
         String source = (String)sources.get(String.valueOf(CharOperation.concat(selector, signature)));
         if (source != null) {
            return new MethodAnnotationWalker(source.toCharArray(), 0, environment);
         }
      }

      return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
   }

   public ITypeAnnotationWalker forField(char[] selector, char[] signature, LookupEnvironment environment) {
      if (this.fieldAnnotationSources != null) {
         String source = (String)this.fieldAnnotationSources.get(String.valueOf(CharOperation.concat(selector, signature, ':')));
         if (source != null) {
            return new FieldAnnotationWalker(source.toCharArray(), 0, environment);
         }
      }

      return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("External Annotations for ").append(this.typeName).append('\n');
      sb.append("Methods:\n");
      if (this.methodAnnotationSources != null) {
         Iterator var3 = this.methodAnnotationSources.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry e = (Map.Entry)var3.next();
            sb.append('\t').append((String)e.getKey()).append('\n');
         }
      }

      return sb.toString();
   }

   void initAnnotations(final LookupEnvironment environment) {
      if (this.NULLABLE_ANNOTATION == null) {
         this.NULLABLE_ANNOTATION = new SingleMarkerAnnotation(this) {
            public char[] getTypeName() {
               return this.getBinaryTypeName(environment.getNullableAnnotationName());
            }
         };
      }

      if (this.NONNULL_ANNOTATION == null) {
         this.NONNULL_ANNOTATION = new SingleMarkerAnnotation(this) {
            public char[] getTypeName() {
               return this.getBinaryTypeName(environment.getNonNullAnnotationName());
            }
         };
      }

   }

   abstract class BasicAnnotationWalker implements ITypeAnnotationWalker {
      char[] source;
      SignatureWrapper wrapper;
      int pos;
      int prevTypeArgStart;
      int currentTypeBound;
      LookupEnvironment environment;

      BasicAnnotationWalker(char[] source, int pos, LookupEnvironment environment) {
         this.source = source;
         this.pos = pos;
         this.environment = environment;
         ExternalAnnotationProvider.this.initAnnotations(environment);
      }

      SignatureWrapper wrapperWithStart(int start) {
         if (this.wrapper == null) {
            this.wrapper = new SignatureWrapper(this.source);
         }

         this.wrapper.start = start;
         this.wrapper.bracket = -1;
         return this.wrapper;
      }

      public ITypeAnnotationWalker toReceiver() {
         return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
      }

      public ITypeAnnotationWalker toTypeParameter(boolean isClassTypeParameter, int rank) {
         return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
      }

      public ITypeAnnotationWalker toTypeParameterBounds(boolean isClassTypeParameter, int parameterRank) {
         return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
      }

      public ITypeAnnotationWalker toTypeBound(short boundIndex) {
         return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
      }

      public ITypeAnnotationWalker toSupertype(short index, char[] superTypeSignature) {
         return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
      }

      public ITypeAnnotationWalker toTypeArgument(int rank) {
         int next;
         if (rank == 0) {
            next = CharOperation.indexOf('<', this.source, this.pos) + 1;
            this.prevTypeArgStart = next;
            return ExternalAnnotationProvider.this.new MethodAnnotationWalker(this.source, next, this.environment);
         } else {
            next = this.prevTypeArgStart;
            switch (this.source[next]) {
               case '*':
                  next = this.skipNullAnnotation(next + 1);
                  break;
               case '+':
               case '-':
                  next = this.skipNullAnnotation(next + 1);
               case ',':
               default:
                  next = this.wrapperWithStart(next).computeEnd();
                  ++next;
            }

            this.prevTypeArgStart = next;
            return ExternalAnnotationProvider.this.new MethodAnnotationWalker(this.source, next, this.environment);
         }
      }

      public ITypeAnnotationWalker toWildcardBound() {
         switch (this.source[this.pos]) {
            case '+':
            case '-':
               int newPos = this.skipNullAnnotation(this.pos + 1);
               return ExternalAnnotationProvider.this.new MethodAnnotationWalker(this.source, newPos, this.environment);
            case ',':
            default:
               return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
         }
      }

      public ITypeAnnotationWalker toNextArrayDimension() {
         if (this.source[this.pos] == '[') {
            int newPos = this.skipNullAnnotation(this.pos + 1);
            return ExternalAnnotationProvider.this.new MethodAnnotationWalker(this.source, newPos, this.environment);
         } else {
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
         }
      }

      public ITypeAnnotationWalker toNextNestedType() {
         return this;
      }

      public IBinaryAnnotation[] getAnnotationsAtCursor(int currentTypeId, boolean mayApplyArrayContentsDefaultNullness) {
         if (this.pos != -1 && this.pos < this.source.length - 2) {
            switch (this.source[this.pos]) {
               case '*':
               case '+':
               case '-':
               case 'L':
               case 'T':
               case '[':
                  switch (this.source[this.pos + 1]) {
                     case '0':
                        return new IBinaryAnnotation[]{ExternalAnnotationProvider.this.NULLABLE_ANNOTATION};
                     case '1':
                        return new IBinaryAnnotation[]{ExternalAnnotationProvider.this.NONNULL_ANNOTATION};
                  }
            }
         }

         return NO_ANNOTATIONS;
      }

      int skipNullAnnotation(int cur) {
         if (cur >= this.source.length) {
            return cur;
         } else {
            switch (this.source[cur]) {
               case '0':
               case '1':
                  return cur + 1;
               default:
                  return cur;
            }
         }
      }
   }

   class DispatchingAnnotationWalker implements ITypeAnnotationWalker {
      private LookupEnvironment environment;
      private TypeParametersAnnotationWalker typeParametersWalker;

      public DispatchingAnnotationWalker(LookupEnvironment environment) {
         this.environment = environment;
      }

      public ITypeAnnotationWalker toTypeParameter(boolean isClassTypeParameter, int rank) {
         String source = ExternalAnnotationProvider.this.typeParametersAnnotationSource;
         if (source != null) {
            if (this.typeParametersWalker == null) {
               this.typeParametersWalker = ExternalAnnotationProvider.this.new TypeParametersAnnotationWalker(source.toCharArray(), 0, 0, (int[])null, this.environment);
            }

            return this.typeParametersWalker.toTypeParameter(isClassTypeParameter, rank);
         } else {
            return this;
         }
      }

      public ITypeAnnotationWalker toTypeParameterBounds(boolean isClassTypeParameter, int parameterRank) {
         return (ITypeAnnotationWalker)(this.typeParametersWalker != null ? this.typeParametersWalker.toTypeParameterBounds(isClassTypeParameter, parameterRank) : this);
      }

      public ITypeAnnotationWalker toSupertype(short index, char[] superTypeSignature) {
         Map sources = ExternalAnnotationProvider.this.supertypeAnnotationSources;
         if (sources != null) {
            String source = (String)sources.get(String.valueOf(superTypeSignature));
            if (source != null) {
               return ExternalAnnotationProvider.this.new SuperTypesAnnotationWalker(source.toCharArray(), this.environment);
            }
         }

         return this;
      }

      public ITypeAnnotationWalker toField() {
         return this;
      }

      public ITypeAnnotationWalker toThrows(int rank) {
         return this;
      }

      public ITypeAnnotationWalker toTypeArgument(int rank) {
         return this;
      }

      public ITypeAnnotationWalker toMethodParameter(short index) {
         return this;
      }

      public ITypeAnnotationWalker toTypeBound(short boundIndex) {
         return this;
      }

      public ITypeAnnotationWalker toMethodReturn() {
         return this;
      }

      public ITypeAnnotationWalker toReceiver() {
         return this;
      }

      public ITypeAnnotationWalker toWildcardBound() {
         return this;
      }

      public ITypeAnnotationWalker toNextArrayDimension() {
         return this;
      }

      public ITypeAnnotationWalker toNextNestedType() {
         return this;
      }

      public IBinaryAnnotation[] getAnnotationsAtCursor(int currentTypeId, boolean mayApplyArrayContentsDefaultNullness) {
         return NO_ANNOTATIONS;
      }
   }

   class FieldAnnotationWalker extends BasicAnnotationWalker {
      public FieldAnnotationWalker(char[] source, int pos, LookupEnvironment environment) {
         super(source, pos, environment);
      }

      public ITypeAnnotationWalker toField() {
         return this;
      }

      public ITypeAnnotationWalker toMethodReturn() {
         throw new UnsupportedOperationException("Field has no method return");
      }

      public ITypeAnnotationWalker toMethodParameter(short index) {
         throw new UnsupportedOperationException("Field has no method parameter");
      }

      public ITypeAnnotationWalker toThrows(int index) {
         throw new UnsupportedOperationException("Field has no throws");
      }
   }

   public interface IMethodAnnotationWalker extends ITypeAnnotationWalker {
      int getParameterCount();
   }

   class MethodAnnotationWalker extends BasicAnnotationWalker implements IMethodAnnotationWalker {
      int prevParamStart;
      TypeParametersAnnotationWalker typeParametersWalker;

      MethodAnnotationWalker(char[] source, int pos, LookupEnvironment environment) {
         super(source, pos, environment);
      }

      int typeEnd(int start) {
         while(this.source[start] == '[') {
            ++start;
            start = this.skipNullAnnotation(start);
         }

         SignatureWrapper wrapper1 = this.wrapperWithStart(start);
         int end = wrapper1.skipAngleContents(wrapper1.computeEnd());
         return end;
      }

      public ITypeAnnotationWalker toTypeParameter(boolean isClassTypeParameter, int rank) {
         if (this.source[0] == '<') {
            return (ITypeAnnotationWalker)(this.typeParametersWalker == null ? (this.typeParametersWalker = ExternalAnnotationProvider.this.new TypeParametersAnnotationWalker(this.source, this.pos + 1, rank, (int[])null, this.environment)) : this.typeParametersWalker.toTypeParameter(isClassTypeParameter, rank));
         } else {
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
         }
      }

      public ITypeAnnotationWalker toTypeParameterBounds(boolean isClassTypeParameter, int parameterRank) {
         return this.typeParametersWalker != null ? this.typeParametersWalker.toTypeParameterBounds(isClassTypeParameter, parameterRank) : ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
      }

      public ITypeAnnotationWalker toMethodReturn() {
         int close = CharOperation.indexOf(')', this.source);
         if (close != -1) {
            this.pos = close + 1;
            return this;
         } else {
            return ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER;
         }
      }

      public ITypeAnnotationWalker toMethodParameter(short index) {
         int end;
         if (index == 0) {
            end = CharOperation.indexOf('(', this.source) + 1;
            this.prevParamStart = end;
            this.pos = end;
            return this;
         } else {
            end = this.typeEnd(this.prevParamStart);
            ++end;
            this.prevParamStart = end;
            this.pos = end;
            return this;
         }
      }

      public ITypeAnnotationWalker toThrows(int index) {
         return this;
      }

      public ITypeAnnotationWalker toField() {
         throw new UnsupportedOperationException("Methods have no fields");
      }

      public int getParameterCount() {
         int count = 0;

         for(int start = CharOperation.indexOf('(', this.source) + 1; start < this.source.length && this.source[start] != ')'; ++count) {
            start = this.typeEnd(start) + 1;
         }

         return count;
      }
   }

   abstract class SingleMarkerAnnotation implements IBinaryAnnotation {
      public IBinaryElementValuePair[] getElementValuePairs() {
         return ElementValuePairInfo.NoMembers;
      }

      public boolean isExternalAnnotation() {
         return true;
      }

      protected char[] getBinaryTypeName(char[][] name) {
         return CharOperation.concat('L', CharOperation.concatWith(name, '/'), ';');
      }
   }

   class SuperTypesAnnotationWalker extends BasicAnnotationWalker {
      SuperTypesAnnotationWalker(char[] source, LookupEnvironment environment) {
         super(source, 0, environment);
      }

      public ITypeAnnotationWalker toField() {
         throw new UnsupportedOperationException("Supertype has no field annotations");
      }

      public ITypeAnnotationWalker toMethodReturn() {
         throw new UnsupportedOperationException("Supertype has no method return");
      }

      public ITypeAnnotationWalker toMethodParameter(short index) {
         throw new UnsupportedOperationException("Supertype has no method parameter");
      }

      public ITypeAnnotationWalker toThrows(int index) {
         throw new UnsupportedOperationException("Supertype has no throws");
      }
   }

   public class TypeParametersAnnotationWalker extends BasicAnnotationWalker {
      int[] rankStarts;
      int currentRank;

      TypeParametersAnnotationWalker(char[] source, int pos, int rank, int[] rankStarts, LookupEnvironment environment) {
         super(source, pos, environment);
         this.currentRank = rank;
         if (rankStarts != null) {
            this.rankStarts = rankStarts;
         } else {
            int length = source.length;
            rankStarts = new int[length];
            int curRank = 0;
            int depth = 0;
            boolean pendingVariable = true;

            label75:
            for(int i = pos; i < length; ++i) {
               switch (this.source[i]) {
                  case ':':
                     if (depth == 0) {
                        pendingVariable = true;
                     }

                     ++i;

                     while(i < length && this.source[i] == '[') {
                        ++i;
                     }

                     if (i < length && this.source[i] == 'L') {
                        for(int currentdepth = depth; i < length && (currentdepth != depth || this.source[i] != ';'); ++i) {
                           if (this.source[i] == '<') {
                              ++currentdepth;
                           }

                           if (this.source[i] == '>') {
                              --currentdepth;
                           }
                        }
                     }

                     --i;
                     break;
                  case ';':
                     if (depth == 0 && i + 1 < length && this.source[i + 1] != ':') {
                        pendingVariable = true;
                     }
                     break;
                  case '<':
                     ++depth;
                     break;
                  case '=':
                  default:
                     if (pendingVariable) {
                        pendingVariable = false;
                        rankStarts[curRank++] = i;
                     }
                     break;
                  case '>':
                     --depth;
                     if (depth < 0) {
                        break label75;
                     }
               }
            }

            System.arraycopy(rankStarts, 0, this.rankStarts = new int[curRank], 0, curRank);
         }

      }

      public ITypeAnnotationWalker toTypeParameter(boolean isClassTypeParameter, int rank) {
         if (rank == this.currentRank) {
            return this;
         } else {
            return (ITypeAnnotationWalker)(rank < this.rankStarts.length ? ExternalAnnotationProvider.this.new TypeParametersAnnotationWalker(this.source, this.rankStarts[rank], rank, this.rankStarts, this.environment) : ITypeAnnotationWalker.EMPTY_ANNOTATION_WALKER);
         }
      }

      public ITypeAnnotationWalker toTypeParameterBounds(boolean isClassTypeParameter, int parameterRank) {
         return ExternalAnnotationProvider.this.new TypeParametersAnnotationWalker(this.source, this.rankStarts[parameterRank], parameterRank, this.rankStarts, this.environment);
      }

      public ITypeAnnotationWalker toTypeBound(short boundIndex) {
         int p = this.pos;
         int i = this.currentTypeBound;

         while(true) {
            int colon = CharOperation.indexOf(':', this.source, p);
            if (colon != -1) {
               p = colon + 1;
            }

            ++i;
            if (i > boundIndex) {
               this.pos = p;
               this.currentTypeBound = boundIndex;
               return this;
            }

            p = this.wrapperWithStart(p).computeEnd() + 1;
         }
      }

      public ITypeAnnotationWalker toField() {
         throw new UnsupportedOperationException("Cannot navigate to fields");
      }

      public ITypeAnnotationWalker toMethodReturn() {
         throw new UnsupportedOperationException("Cannot navigate to method return");
      }

      public ITypeAnnotationWalker toMethodParameter(short index) {
         throw new UnsupportedOperationException("Cannot navigate to method parameter");
      }

      public ITypeAnnotationWalker toThrows(int index) {
         throw new UnsupportedOperationException("Cannot navigate to throws");
      }

      public IBinaryAnnotation[] getAnnotationsAtCursor(int currentTypeId, boolean mayApplyArrayContentsDefaultNullness) {
         if (this.pos != -1 && this.pos < this.source.length - 1) {
            switch (this.source[this.pos]) {
               case '0':
                  return new IBinaryAnnotation[]{ExternalAnnotationProvider.this.NULLABLE_ANNOTATION};
               case '1':
                  return new IBinaryAnnotation[]{ExternalAnnotationProvider.this.NONNULL_ANNOTATION};
            }
         }

         return super.getAnnotationsAtCursor(currentTypeId, mayApplyArrayContentsDefaultNullness);
      }
   }
}
