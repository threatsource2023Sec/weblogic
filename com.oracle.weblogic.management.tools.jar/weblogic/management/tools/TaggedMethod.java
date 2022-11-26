package weblogic.management.tools;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class TaggedMethod {
   public String[] m_tags;
   public String m_methodSignature;
   public String m_methodName;
   public String m_returnType;

   public TaggedMethod(String methodSignature, List tagList) {
      this.m_methodSignature = methodSignature;
      StringTokenizer st = new StringTokenizer(methodSignature);
      this.m_returnType = st.nextToken();
      if ("public".equals(this.m_returnType) || "private".equals(this.m_returnType) || "protected".equals(this.m_returnType)) {
         this.m_returnType = st.nextToken();
      }

      this.m_methodName = st.nextToken();
      this.m_tags = new String[tagList.size()];
      Iterator it = tagList.iterator();

      for(int i = 0; i < this.m_tags.length; ++i) {
         this.m_tags[i] = it.next().toString();
      }

   }

   public String getReturnType() {
      return this.m_returnType;
   }

   public String getMethodName() {
      return this.m_methodName;
   }

   public String getMethodSignature() {
      return this.m_methodSignature;
   }

   public String getFieldName() {
      String res = this.trimAction();
      return ToXML.toElementName(res);
   }

   public String getGetMethodName() {
      String res = this.trimAction();
      return this.getGetPrefix() + res;
   }

   public String[] getTags() {
      return this.m_tags;
   }

   public String getTagValue(String tagName) {
      for(int i = 0; i < this.m_tags.length; ++i) {
         if (-1 != this.m_tags[i].indexOf(tagName)) {
            return this.m_tags[i].substring(1 + tagName.length());
         }
      }

      return null;
   }

   public boolean containsTag(String tagName) {
      for(int i = 0; i < this.m_tags.length; ++i) {
         if (this.m_tags[i].indexOf(tagName) >= 0) {
            return true;
         }
      }

      return false;
   }

   public String toString() {
      return "[TaggedMethod:" + this.getMethodSignature() + "]";
   }

   String getGetPrefix() {
      return this.m_methodName.startsWith("is") ? "is" : "get";
   }

   private String trimAction() {
      String res = this.m_methodName;
      if (this.m_methodName.startsWith("get")) {
         res = this.m_methodName.substring(3);
      } else if (this.m_methodName.startsWith("set")) {
         res = this.m_methodName.substring(3);
      } else if (this.m_methodName.startsWith("is")) {
         res = this.m_methodName.substring(2);
      } else if (this.m_methodName.startsWith("add")) {
         res = this.m_methodName.substring(3);
      } else if (this.m_methodName.startsWith("remove")) {
         res = this.m_methodName.substring(6);
      }

      res = res.substring(0, res.length() - 3);
      return res;
   }
}
