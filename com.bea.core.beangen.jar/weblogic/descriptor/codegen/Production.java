package weblogic.descriptor.codegen;

import com.bea.util.jam.JClass;
import java.util.Date;

public class Production {
   public static final String date = "date";
   public static final String author = "author";
   public static final String source = "source";
   public static final String target = "target";
   public static final String version = "version";
   public static final String copyright = "copyright";
   public static final String templateVersion = "0.1";
   protected JClass sourceJClass;
   protected CodeGenerator generator;
   protected AnnotatableClass _source;
   protected AnnotatableClass _target;
   protected String suffix;
   protected String date_;

   public Production(JClass sourceJClass, CodeGenerator generator) {
      this.sourceJClass = sourceJClass;
      this.generator = generator;
      this.suffix = generator.getOpts().getSuffix();
   }

   public String getSuffix() {
      return this.suffix;
   }

   public Object getAnnotatableTarget() {
      if (this._target == null) {
         this._target = new AnnotatableClass(this.sourceJClass);
      }

      return this._target;
   }

   public AnnotatableClass getAnnotatableSource() {
      if (this._source == null) {
         this._source = new AnnotatableClass(this.sourceJClass);
      }

      return this._source;
   }

   public String copyright() {
      return "@copyright Copyright (c) 2003,2014, Oracle and/or its affiliates. All rights reserved.";
   }

   public String author() {
      return "@author Copyright (c) 2003,2014, Oracle and/or its affiliates. All rights reserved.";
   }

   public String date() {
      if (this.date_ == null) {
         this.date_ = (new Date()).toString();
      }

      return this.date_;
   }

   public String version() {
      return "@version " + this.versionNumber();
   }

   protected String versionNumber() {
      return "0.1";
   }
}
