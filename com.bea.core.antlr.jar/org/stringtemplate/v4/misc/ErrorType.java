package org.stringtemplate.v4.misc;

public enum ErrorType {
   NO_SUCH_TEMPLATE("no such template: %s"),
   NO_IMPORTED_TEMPLATE("no such template: super.%s"),
   NO_SUCH_ATTRIBUTE("attribute %s isn't defined"),
   NO_SUCH_ATTRIBUTE_PASS_THROUGH("could not pass through undefined attribute %s"),
   REF_TO_IMPLICIT_ATTRIBUTE_OUT_OF_SCOPE("implicitly-defined attribute %s not visible"),
   MISSING_FORMAL_ARGUMENTS("missing argument definitions"),
   NO_SUCH_PROPERTY("no such property or can't access: %s"),
   MAP_ARGUMENT_COUNT_MISMATCH("iterating through %s values in zip map but template has %s declared arguments"),
   ARGUMENT_COUNT_MISMATCH("passed %s arg(s) to template %s with %s declared arg(s)"),
   EXPECTING_STRING("function %s expects a string not %s"),
   WRITER_CTOR_ISSUE("%s(Writer) constructor doesn't exist"),
   CANT_IMPORT("can't find template(s) in import \"%s\""),
   SYNTAX_ERROR("%s"),
   TEMPLATE_REDEFINITION("redefinition of template %s"),
   EMBEDDED_REGION_REDEFINITION("region %s is embedded and thus already implicitly defined"),
   REGION_REDEFINITION("redefinition of region %s"),
   MAP_REDEFINITION("redefinition of dictionary %s"),
   ALIAS_TARGET_UNDEFINED("cannot alias %s to undefined template: %s"),
   TEMPLATE_REDEFINITION_AS_MAP("redefinition of template %s as a map"),
   LEXER_ERROR("%s"),
   NO_DEFAULT_VALUE("missing dictionary default value"),
   NO_SUCH_FUNCTION("no such function: %s"),
   NO_SUCH_REGION("template %s doesn't have a region called %s"),
   NO_SUCH_OPTION("no such option: %s"),
   INVALID_TEMPLATE_NAME("invalid template name or path: %s"),
   ANON_ARGUMENT_MISMATCH("anonymous template has %s arg(s) but mapped across %s value(s)"),
   REQUIRED_PARAMETER_AFTER_OPTIONAL("required parameters (%s) must appear before optional parameters"),
   INTERNAL_ERROR("%s"),
   WRITE_IO_ERROR("error writing output caused by"),
   CANT_LOAD_GROUP_FILE("can't load group file %s");

   public String message;

   private ErrorType(String m) {
      this.message = m;
   }
}
