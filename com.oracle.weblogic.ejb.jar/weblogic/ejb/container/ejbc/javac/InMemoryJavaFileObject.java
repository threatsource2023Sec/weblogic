package weblogic.ejb.container.ejbc.javac;

import java.net.URI;
import javax.tools.SimpleJavaFileObject;
import javax.tools.JavaFileObject.Kind;

public class InMemoryJavaFileObject extends SimpleJavaFileObject {
   final String content;

   InMemoryJavaFileObject(String name, String content) {
      super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
      this.content = content;
   }

   public CharSequence getCharContent(boolean ignoreEncodingErrors) {
      return this.content;
   }
}
