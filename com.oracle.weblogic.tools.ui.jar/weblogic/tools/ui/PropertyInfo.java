package weblogic.tools.ui;

public class PropertyInfo {
   private boolean m_required;
   private Object[] m_constrainedObjects;
   private String[] m_constrainedStrings;
   private String m_name;
   private String m_label;
   private String m_tooltip;
   private String m_helpKey;
   private boolean m_emptyStringIsNull;
   private int numberMin;
   private int numberMax;
   private int numberInc;
   private boolean selectFirstListElement;
   private boolean allowListEditing;
   private boolean allowNullObject;

   public PropertyInfo(String name, String label, String tooltip, Object[] values, boolean required) {
      this.m_emptyStringIsNull = true;
      this.numberMin = -1;
      this.numberMax = -1;
      this.numberInc = -1;
      this.selectFirstListElement = false;
      this.allowListEditing = false;
      this.allowNullObject = false;
      this.m_name = name;
      this.m_label = label;
      this.m_tooltip = tooltip;
      if (values != null) {
         if (values instanceof String[]) {
            this.m_constrainedStrings = (String[])((String[])values);
         } else {
            this.m_constrainedObjects = values;
         }
      }

      this.m_required = required;
   }

   public PropertyInfo(String name, String label, String tooltip) {
      this(name, label, tooltip, (Object[])null, false);
   }

   public PropertyInfo(String name, String label, String tooltip, boolean required) {
      this(name, label, tooltip, (Object[])null, required);
   }

   public String getName() {
      return this.m_name;
   }

   public String getLabel() {
      return this.m_label;
   }

   public String getTooltip() {
      return this.m_tooltip;
   }

   public Object[] getConstrainedObjects() {
      return this.m_constrainedObjects;
   }

   public String[] getConstrainedStrings() {
      return this.m_constrainedStrings;
   }

   public boolean isRequired() {
      return this.m_required;
   }

   public PropertyInfo setRequired(boolean b) {
      this.m_required = b;
      return this;
   }

   public PropertyInfo setConstrained(Object[] obj) {
      if (obj == null) {
         this.m_constrainedStrings = null;
         this.m_constrainedObjects = null;
      } else if (obj instanceof String[]) {
         this.m_constrainedStrings = (String[])((String[])obj);
         this.m_constrainedObjects = null;
      } else {
         this.m_constrainedObjects = obj;
         this.m_constrainedStrings = null;
      }

      return this;
   }

   public PropertyInfo setEmptyStringIsNull(boolean b) {
      this.m_emptyStringIsNull = b;
      return this;
   }

   public boolean isEmptyStringNull() {
      return this.m_emptyStringIsNull;
   }

   public PropertyInfo setNumberMin(int n) {
      this.numberMin = n;
      return this;
   }

   public int getNumberMin() {
      return this.numberMin;
   }

   public PropertyInfo setNumberMax(int n) {
      this.numberMax = n;
      return this;
   }

   public int getNumberMax() {
      return this.numberMax;
   }

   public PropertyInfo setNumberIncrement(int n) {
      this.numberInc = n;
      return this;
   }

   public int getNumberIncrement() {
      return this.numberInc;
   }

   public PropertyInfo setSelectFirstListElement(boolean b) {
      this.selectFirstListElement = b;
      return this;
   }

   public boolean getSelectFirstListElement() {
      return this.selectFirstListElement;
   }

   public PropertyInfo setAllowListEditing(boolean b) {
      this.allowListEditing = b;
      return this;
   }

   public boolean getAllowListEditing() {
      return this.allowListEditing;
   }

   public PropertyInfo setAllowNullObject(boolean b) {
      this.allowNullObject = b;
      return this;
   }

   public boolean getAllowNullObject() {
      return this.allowNullObject;
   }

   public static PropertyInfo fromArray(Object[] objs) {
      if (objs == null) {
         throw new NullPointerException("null array");
      } else if (objs.length < 3) {
         throw new IllegalArgumentException("bad array");
      } else {
         String name = null;
         String label = null;
         String tooltip = null;
         Object[] values = null;
         boolean required = false;
         name = (String)objs[0];
         label = (String)objs[1];
         tooltip = (String)objs[2];
         if (objs.length > 3 && objs[3] != null && objs[3] instanceof Object[]) {
            values = (Object[])((Object[])objs[3]);
         }

         Object last = objs[objs.length - 1];
         if (objs.length > 3 && last != null && last instanceof Boolean) {
            Boolean bool = (Boolean)last;
            required = bool;
         }

         return new PropertyInfo(name, label, tooltip, values, required);
      }
   }
}
