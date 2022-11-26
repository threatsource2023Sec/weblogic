package weblogic.diagnostics.flightrecorder2.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.Name;

@MetadataDefinition
@Description("URI of the relation key")
@Label("RelationKey")
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Name("com.oracle.weblogic.impl.RelationKey")
public @interface RelationKey {
   String value();
}
