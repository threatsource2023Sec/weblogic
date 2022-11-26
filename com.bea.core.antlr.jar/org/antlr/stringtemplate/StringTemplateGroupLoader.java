package org.antlr.stringtemplate;

public interface StringTemplateGroupLoader {
   StringTemplateGroup loadGroup(String var1);

   StringTemplateGroup loadGroup(String var1, StringTemplateGroup var2);

   StringTemplateGroup loadGroup(String var1, Class var2, StringTemplateGroup var3);

   StringTemplateGroupInterface loadInterface(String var1);
}
