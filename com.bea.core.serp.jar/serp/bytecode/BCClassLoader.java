package serp.bytecode;

public class BCClassLoader extends ClassLoader {
   private Project _project = null;

   public BCClassLoader(Project project) {
      this._project = project;
   }

   public BCClassLoader(Project project, ClassLoader loader) {
      super(loader);
      this._project = project;
   }

   public Project getProject() {
      return this._project;
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      byte[] bytes;
      try {
         BCClass type;
         if (!this._project.containsClass(name)) {
            type = this.createClass(name);
         } else {
            type = this._project.loadClass(name);
         }

         if (type == null) {
            throw new ClassNotFoundException(name);
         }

         bytes = type.toByteArray();
      } catch (RuntimeException var4) {
         throw new ClassNotFoundException(var4.toString());
      }

      return this.defineClass(name, bytes, 0, bytes.length);
   }

   protected BCClass createClass(String name) {
      return null;
   }
}
