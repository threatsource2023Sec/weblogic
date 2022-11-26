package weblogic.descriptor.codegen;

import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JComment;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;
import com.bea.util.jam.JamClassLoader;

public abstract class AnnotatableToken {
   public static String[] KEY_WORD_2 = new String[]{"do", "if"};
   public static String[] KEY_WORD_3 = new String[]{"for", "int", "new", "try"};
   public static String[] KEY_WORD_4 = new String[]{"byte", "case", "char", "else", "goto", "long", "this", "void"};
   public static String[] KEY_WORD_5 = new String[]{"break", "catch", "class", "const", "final", "float", "short", "super", "throw", "while"};
   public static String[] KEY_WORD_6 = new String[]{"double", "import", "native", "public", "return", "static", "switch", "throws"};
   public static String[] KEY_WORD_7 = new String[]{"boolean", "default", "extends", "finally", "package", "private"};
   public static String[] KEY_WORD_8 = new String[]{"abstract", "continue", "strictfp", "volatile"};
   public static String[] KEY_WORD_9 = new String[]{"interface", "protected", "transient"};
   public static String[] KEY_WORD_10 = new String[]{"implements", "instanceof"};
   public static String[] KEY_WORD_12 = new String[]{"synchronized"};
   protected JAnnotatedElement jNode;
   protected JamClassLoader loader;
   protected String[] comments;
   protected String name;

   public AnnotatableToken(JAnnotatedElement jNode, JamClassLoader loader) {
      this.jNode = jNode;
      this.loader = loader;
   }

   public String getName() {
      if (this.name == null) {
         this.name = this.jNode.getSimpleName();
         int index = this.name.lastIndexOf(46);
         if (index > 0) {
            this.name = this.name.substring(index + 1);
         }
      }

      return this.name;
   }

   public Annotation getAnnotation(String tag) {
      return this.newAnnotation(this.jNode.getAnnotation(tag), this.loader);
   }

   public Annotation[] getAnnotations() {
      return this.newAnnotations(this.jNode.getAnnotations(), this.loader);
   }

   public boolean hasAnnotation(String tag) {
      return this.jNode.getAnnotation(tag) != null;
   }

   public boolean hasAnnotations() {
      JAnnotation[] ja = this.jNode.getAnnotations();
      return ja != null && ja.length > 0;
   }

   public boolean hasComments() {
      return this.getComments().length > 0;
   }

   public String[] getComments() {
      if (this.comments == null) {
         JComment jComment = this.jNode.getComment();
         if (jComment == null) {
            this.comments = new String[0];
         } else {
            this.comments = new String[]{jComment.getText()};
         }
      }

      return this.comments;
   }

   protected String toLower(String n) {
      char c = n.charAt(0);
      return c >= 'A' && c <= 'Z' ? (char)(c + 32) + n.substring(1) : n;
   }

   protected String toUpper(String n) {
      char c = n.charAt(0);
      return c >= 'a' && c <= 'z' ? (char)(c - 32) + n.substring(1) : n;
   }

   String plural(String n) {
      return Utils.plural(n);
   }

   String singular(String n) {
      return Utils.singular(n);
   }

   protected boolean isKeyWord(String n) {
      char first = n.charAt(0);
      if (!Character.isLowerCase(first)) {
         return false;
      } else {
         int len = n.length();
         int i;
         switch (len) {
            case 2:
               for(i = 0; i < KEY_WORD_2.length; ++i) {
                  if (n.equals(KEY_WORD_2[i])) {
                     return true;
                  }
               }

               return false;
            case 3:
               for(i = 0; i < KEY_WORD_3.length; ++i) {
                  if (n.equals(KEY_WORD_3[i])) {
                     return true;
                  }
               }

               return false;
            case 4:
               for(i = 0; i < KEY_WORD_4.length; ++i) {
                  if (n.equals(KEY_WORD_4[i])) {
                     return true;
                  }
               }

               return false;
            case 5:
               for(i = 0; i < KEY_WORD_5.length; ++i) {
                  if (n.equals(KEY_WORD_5[i])) {
                     return true;
                  }
               }

               return false;
            case 6:
               for(i = 0; i < KEY_WORD_6.length; ++i) {
                  if (n.equals(KEY_WORD_6[i])) {
                     return true;
                  }
               }

               return false;
            case 7:
               for(i = 0; i < KEY_WORD_7.length; ++i) {
                  if (n.equals(KEY_WORD_7[i])) {
                     return true;
                  }
               }

               return false;
            case 8:
               for(i = 0; i < KEY_WORD_8.length; ++i) {
                  if (n.equals(KEY_WORD_8[i])) {
                     return true;
                  }
               }

               return false;
            case 9:
               for(i = 0; i < KEY_WORD_9.length; ++i) {
                  if (n.equals(KEY_WORD_9[i])) {
                     return true;
                  }
               }

               return false;
            case 10:
               for(i = 0; i < KEY_WORD_10.length; ++i) {
                  if (n.equals(KEY_WORD_10[i])) {
                     return true;
                  }
               }

               return false;
            case 11:
            default:
               return false;
            case 12:
               for(i = 0; i < KEY_WORD_12.length; ++i) {
                  if (n.equals(KEY_WORD_12[i])) {
                     return true;
                  }
               }

               return false;
         }
      }
   }

   protected Annotation newAnnotation(JAnnotation annotation, JamClassLoader loader) {
      return annotation == null ? null : new Annotation(annotation, loader);
   }

   protected Annotation[] newAnnotations(JAnnotation[] annotations, JamClassLoader loader) {
      if (annotations == null) {
         return new Annotation[0];
      } else {
         Annotation[] annos = new Annotation[annotations.length];

         for(int i = 0; i < annotations.length; ++i) {
            annos[i] = this.newAnnotation(annotations[i], loader);
         }

         return annos;
      }
   }

   protected AnnotatableClass newAnnotatableClass(JClass jClass) {
      return new AnnotatableClass(jClass);
   }

   protected AnnotatableMethod newAnnotatableMethod(JMethod jMethod) {
      return new AnnotatableMethod(jMethod);
   }

   protected AnnotatableAttribute newAnnotatableAttribute(JParameter pa) {
      return new AnnotatableAttribute(pa);
   }

   protected AnnotatableAttribute newAnnotatableAttribute(JClass jClass) {
      return new AnnotatableAttribute(jClass);
   }

   protected AnnotatableAttribute newAnnotatableAttribute(JMethod jMethod) {
      return new AnnotatableAttribute(jMethod);
   }
}
