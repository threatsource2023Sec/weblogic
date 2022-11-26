package com.bea.security.providers.xacml;

import javax.security.auth.Subject;

public interface SubjectConverterFactory {
   SubjectConverter getConverter(Subject var1);
}
