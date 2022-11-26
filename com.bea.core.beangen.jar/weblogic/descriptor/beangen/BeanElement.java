package weblogic.descriptor.beangen;

import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JComment;
import com.bea.util.jam.JElement;
import com.bea.util.jam.JSourcePosition;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import weblogic.descriptor.annotation.AnnotationDefinition;
import weblogic.descriptor.annotation.GlobalAnnotations;

public class BeanElement {
   private JAnnotatedElement element;
   private static final String ANNOTATION_PACKAGE = AnnotationDefinition.class.getPackage().getName();
   private static final Set htmlTags = new HashSet(Arrays.asList("<A>", "<ABBREV>", "<ACRONYM>", "<ADDRESS>", "<APPLET>", "<AREA>", "<AU>", "<AUTHOR>", "<B>", "<BANNER>", "<BASE>", "<BASEFONT>", "<BGSOUND>", "<BIG>", "<BLINK>", "<BLOCKQUOTE>", "<BQ>", "<BODY>", "<BR>", "<CAPTION>", "<CENTER>", "<CITE>", "<CODE>", "<COL>", "<COLGROUP>", "<CREDIT>", " <DEL>", "<DFN>", "<DIR>", "<DIV>", "<DL>", "<DT>", "<DD>", "<EM>", "<EMBED>", "<FIG>", "<FN>", "<FONT>", "<FORM>", "<FRAME>", "<FRAMESET>", "<H1>", "<H2>", "<H3>", "<H4>", "<H5>", "<H6>", "<HEAD>", "<HR>", "<HTML>", "<I>", "<IFRAME>", "<IMG>", " <INPUT>", "<INS>", "<ISINDEX>", "<KBD>", "<LANG>", "<LH>", "<LI>", "<LINK>", "<LISTING>", "<MAP>", "<MARQUEE>", "<MATH>", "<MENU>", "<META>", "<MULTICOL>", "<NOBR>", "<NOFRAMES>", "<NOTE>", "<OL>", "<OVERLAY>", "<P>", "<PARAM>", "<PERSON>", "<PLAINTEXT>", "<PRE>", "<Q>", "<RANGE>", "<SAMP>", "<SCRIPT>", "<SELECT>", "<SMALL>", "<SPACER>", "<SPOT>", "<STRIKE>", "<STRONG>", "<SUB>", "<SUP>", "<TAB>", "<TABLE>", "<TBODY>", "<TD>", "<TEXTAREA>", "<TEXTFLOW>", "<TFOOT>", "<TH>", "<THEAD>", "<TITLE>", "<TR>", "<TT>", "<U>", "<UL>", "<VAR>", "<WBR>", "<XMP>"));

   protected BeanElement() {
   }

   protected BeanElement(JAnnotatedElement element) {
      this.element = element;
   }

   public final String getCommentText() {
      if (this.element == null) {
         return null;
      } else {
         JComment comment = this.element.getComment();
         return comment != null ? comment.getText() : "";
      }
   }

   public JElement getJElement() {
      return this.element;
   }

   public JClass getDeclaringClass() {
      if (this.element == null) {
         return null;
      } else {
         Object cur;
         for(cur = this.element; !(cur instanceof JClass); cur = ((JElement)cur).getParent()) {
         }

         return (JClass)cur;
      }
   }

   public boolean isObsolete() {
      return this.isAnnotationDefined(GlobalAnnotations.OBSOLETE);
   }

   public boolean isVisibleToPartitions() {
      return this.isAnnotationDefined(GlobalAnnotations.VISIBLE_TO_PARTITION);
   }

   public boolean isAnnotationDefined(String nameOrTag) {
      return this.isAnnotationDefined(this.getAnnotationDefinition(nameOrTag));
   }

   public boolean isAnnotationDefined(AnnotationDefinition def) {
      return this.getAnnotationValue(def) != null;
   }

   public boolean isAnnotationTrue(String nameOrTag) {
      return this.isAnnotationTrue(this.getAnnotationDefinition(nameOrTag));
   }

   public boolean isAnnotationTrue(AnnotationDefinition def) {
      JAnnotationValue val = this.getAnnotationValue(def);
      return val == null ? false : !"false".equals(val.asString());
   }

   public String getAnnotationString(String nameOrTag) {
      return this.getAnnotationString(this.getAnnotationDefinition(nameOrTag));
   }

   public String[] getAllAnnotationInstancesAsStrings(String nameOrTag) {
      return this.getAllAnnotationInstancesAsStrings(this.getAnnotationDefinition(nameOrTag));
   }

   public String[] getAllAnnotationInstancesAsStrings(AnnotationDefinition tag) {
      JAnnotationValue[] aValues = tag.getAnnotationValues(this.element);
      ArrayList sValues = new ArrayList();

      for(int count = 0; count < aValues.length; ++count) {
         if (aValues[count].getName().equals("value")) {
            sValues.add(aValues[count].asString());
         }
      }

      return (String[])((String[])sValues.toArray(new String[0]));
   }

   protected String[] getAllAnnotationNames() {
      JAnnotation[] annotations = this.element.getAnnotations();
      ArrayList annotationNames = new ArrayList();

      for(int count = 0; count < annotations.length; ++count) {
         JAnnotationValue[] values = annotations[count].getValues();
         boolean hasElements = false;

         for(int i = 0; i < values.length; ++i) {
            if (!values[i].getName().equals("value")) {
               hasElements = true;
               annotationNames.add(annotations[count].getQualifiedName() + "@" + values[i].getName());
            }
         }

         if (!hasElements) {
            annotationNames.add(annotations[count].getQualifiedName());
         }
      }

      return (String[])((String[])annotationNames.toArray(new String[0]));
   }

   public String getAnnotationString(AnnotationDefinition tag) {
      JAnnotationValue a = this.getAnnotationValue(tag);
      if (a == null) {
         return null;
      } else {
         String val = a.asString().trim();
         return val.length() != 0 && !"null".equals(val) ? val : null;
      }
   }

   public AnnotationDefinition getAnnotationDefinition(String nameOrTag) {
      int dotIdx = nameOrTag.indexOf(46);
      if (dotIdx == -1) {
         return new AnnotationDefinition(nameOrTag);
      } else {
         String defClassName = ANNOTATION_PACKAGE + nameOrTag.substring(0, dotIdx);
         String defFieldName = nameOrTag.substring(dotIdx);

         try {
            Class cls = Class.forName(defClassName);
            Field field = cls.getField(defFieldName);
            return (AnnotationDefinition)field.get((Object)null);
         } catch (ClassNotFoundException var7) {
            throw new BeanGenerationException(nameOrTag + " is not an AnnotationDefinition constant defined in " + ANNOTATION_PACKAGE);
         } catch (NoSuchFieldException var8) {
            throw new BeanGenerationException(nameOrTag + " is not an AnnotationDefinition constant defined in " + ANNOTATION_PACKAGE);
         } catch (IllegalAccessException var9) {
            throw new BeanGenerationException(nameOrTag + " is not an public AnnotationDefinition constant");
         } catch (ClassCastException var10) {
            throw new AssertionError(var10);
         }
      }
   }

   public JAnnotationValue getAnnotationValue(AnnotationDefinition tag) {
      return tag.getAnnotationValue(this.element);
   }

   void error(String message) {
      Context.get().getLog().error(message, this.getJElement());
   }

   void warning(String message) {
      Context.get().getLog().warning(message, this.getJElement());
   }

   String getLocation() {
      JSourcePosition pos = this.element.getSourcePosition();
      String loc = "(Unknown)";
      if (pos != null) {
         loc = pos.getSourceURI().getPath() + "(" + pos.getLine() + ")";
      }

      return loc;
   }

   protected static String stripHTML(String text) {
      StringBuffer sbuf = new StringBuffer(text);
      int lbracket = -1;
      int rbracket = true;

      while(true) {
         lbracket = sbuf.indexOf("<", lbracket + 1);
         String tag;
         if (lbracket < 0) {
            tag = sbuf.toString();
            return tag.replaceAll("&lt;|&amp;|&lt;", "");
         }

         int rbracket = sbuf.indexOf(">", lbracket);
         if (rbracket >= 0) {
            tag = sbuf.substring(lbracket, rbracket + 1).toUpperCase(Locale.US);
            String toInsert = null;
            if ("</LI>".equals(tag)) {
               toInsert = "\n";
            } else if ("<P>".equals(tag)) {
               toInsert = "\n\n";
            } else if ("<LI>".equals(tag)) {
               toInsert = " - ";
            }

            if (toInsert != null) {
               sbuf.insert(lbracket, toInsert);
               lbracket += toInsert.length();
               rbracket += toInsert.length();
            }

            if (tag.charAt(1) == '/') {
               tag = "<" + tag.substring(2);
            }

            if (htmlTags.contains(tag)) {
               sbuf.delete(lbracket, rbracket + 1);
               int len = rbracket - lbracket + 1;
               lbracket -= len;
               int var10000 = rbracket - len;
            }
         }
      }
   }
}
