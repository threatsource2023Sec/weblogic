package com.bea.util.annogen.generate;

public class AnnoBeanMapping {
   private static final char WILD = '*';
   private static final boolean VERBOSE = false;
   private String mTypePattern;
   private String mBeanPattern;
   private String mTypeBeforeStar = null;
   private String mTypeAfterStar = null;
   private String mBeanBeforeStar = null;
   private String mBeanAfterStar = null;

   public AnnoBeanMapping() {
   }

   public AnnoBeanMapping(String typePattern, String beanPattern) {
      this.setType(typePattern);
      this.setBean(beanPattern);
   }

   public String getAnnoBeanFor(String classname) {
      if (classname == null) {
         throw new IllegalArgumentException("null classname");
      } else {
         classname = classname.trim();
         if (this.mBeanBeforeStar == null && this.mTypeBeforeStar == null) {
            return this.mTypePattern.equals(classname) ? this.mBeanPattern : null;
         } else if (this.mBeanBeforeStar == null) {
            throw new IllegalStateException("* must be specified in both bean and type");
         } else if (this.mTypeBeforeStar == null) {
            throw new IllegalStateException("* must be specified in both bean and type");
         } else if (classname.startsWith(this.mTypeBeforeStar) && (this.mTypeAfterStar == null || classname.endsWith(this.mTypeAfterStar))) {
            String sub = classname.substring(this.mTypeBeforeStar.length(), classname.length() - (this.mTypeAfterStar == null ? 0 : this.mTypeAfterStar.length()));
            String out = this.mBeanBeforeStar + sub + (this.mBeanAfterStar == null ? "" : this.mBeanAfterStar);
            return out;
         } else {
            return null;
         }
      }
   }

   public String getType() {
      return this.mTypePattern;
   }

   public void setType(String type) {
      if (type == null) {
         throw new IllegalArgumentException("null type");
      } else if (type.length() == 0) {
         throw new IllegalArgumentException("empty type");
      } else {
         this.mTypePattern = type.trim();
         int wild;
         if ((wild = type.indexOf(42)) != -1) {
            this.mTypeBeforeStar = this.mTypePattern.substring(0, wild);
            if (wild == this.mTypePattern.length() - 1) {
               this.mTypeAfterStar = null;
            } else {
               this.mTypeAfterStar = this.mTypePattern.substring(wild + 1);
            }
         }

      }
   }

   public String getBean() {
      return this.mBeanPattern;
   }

   public void setBean(String bean) {
      if (bean == null) {
         throw new IllegalArgumentException("null bean");
      } else if (bean.length() == 0) {
         throw new IllegalArgumentException("empty bean");
      } else {
         this.mBeanPattern = bean;
         int wild;
         if ((wild = bean.indexOf(42)) != -1) {
            this.mBeanBeforeStar = this.mBeanPattern.substring(0, wild);
            if (wild == this.mBeanPattern.length()) {
               this.mBeanBeforeStar = null;
            } else {
               this.mBeanAfterStar = this.mBeanPattern.substring(wild + 1);
            }
         }

      }
   }

   private static final void verbose(String msg) {
      System.out.println("[AnnoBeanMapping] " + msg);
   }
}
