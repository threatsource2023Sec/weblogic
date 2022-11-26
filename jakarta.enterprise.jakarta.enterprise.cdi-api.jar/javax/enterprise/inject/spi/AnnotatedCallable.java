package javax.enterprise.inject.spi;

import java.util.List;

public interface AnnotatedCallable extends AnnotatedMember {
   List getParameters();
}
