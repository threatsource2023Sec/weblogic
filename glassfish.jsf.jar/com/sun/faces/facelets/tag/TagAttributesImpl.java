package com.sun.faces.facelets.tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;

public final class TagAttributesImpl extends TagAttributes {
   private static final TagAttribute[] EMPTY = new TagAttribute[0];
   private final TagAttribute[] attrs;
   private final String[] ns;
   private final List nsattrs;
   private Tag tag;

   public TagAttributesImpl(TagAttribute[] attrs) {
      this.attrs = attrs;
      int i = false;
      Set set = new HashSet();

      int i;
      for(i = 0; i < this.attrs.length; ++i) {
         set.add(this.attrs[i].getNamespace());
      }

      this.ns = (String[])((String[])set.toArray(new String[set.size()]));
      Arrays.sort(this.ns);
      this.nsattrs = new ArrayList();

      for(i = 0; i < this.ns.length; ++i) {
         this.nsattrs.add(i, new ArrayList());
      }

      int nsIdx = false;

      for(i = 0; i < this.attrs.length; ++i) {
         int nsIdx = Arrays.binarySearch(this.ns, this.attrs[i].getNamespace());
         ((List)this.nsattrs.get(nsIdx)).add(this.attrs[i]);
      }

      for(i = 0; i < this.ns.length; ++i) {
         List r = (List)this.nsattrs.get(i);
         this.nsattrs.set(i, r.toArray(new TagAttribute[r.size()]));
      }

   }

   public TagAttribute[] getAll() {
      return this.attrs;
   }

   public TagAttribute get(String localName) {
      return this.get("", localName);
   }

   public TagAttribute get(String ns, String localName) {
      if (ns != null && localName != null) {
         int idx = Arrays.binarySearch(this.ns, ns);
         if (idx >= 0) {
            TagAttribute[] uia = (TagAttribute[])((TagAttribute[])this.nsattrs.get(idx));

            for(int i = 0; i < uia.length; ++i) {
               if (localName.equals(uia[i].getLocalName())) {
                  return uia[i];
               }
            }
         }
      }

      return null;
   }

   public TagAttribute[] getAll(String namespace) {
      int idx = false;
      int idx;
      if (namespace == null) {
         idx = Arrays.binarySearch(this.ns, "");
      } else {
         idx = Arrays.binarySearch(this.ns, namespace);
      }

      return idx >= 0 ? (TagAttribute[])((TagAttribute[])this.nsattrs.get(idx)) : EMPTY;
   }

   public String[] getNamespaces() {
      return this.ns;
   }

   public Tag getTag() {
      return this.tag;
   }

   public void setTag(Tag tag) {
      this.tag = tag;
      TagAttribute[] var2 = this.attrs;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TagAttribute cur = var2[var4];
         cur.setTag(tag);
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < this.attrs.length; ++i) {
         sb.append(this.attrs[i]);
         sb.append(' ');
      }

      if (sb.length() > 1) {
         sb.setLength(sb.length() - 1);
      }

      return sb.toString();
   }
}
