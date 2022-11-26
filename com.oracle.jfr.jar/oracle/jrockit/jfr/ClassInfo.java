package oracle.jrockit.jfr;

class ClassInfo {
   final String name;
   final long id;
   final long loaderClassId;

   public ClassInfo(String name, long id, long loaderClassId) {
      this.name = name;
      this.id = id;
      this.loaderClassId = loaderClassId;
   }
}
