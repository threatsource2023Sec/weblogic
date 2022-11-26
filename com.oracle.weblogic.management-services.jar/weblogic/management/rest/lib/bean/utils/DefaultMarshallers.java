package weblogic.management.rest.lib.bean.utils;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.composite.CompositeUtil;
import org.glassfish.admin.rest.model.ApiInfo;
import org.glassfish.admin.rest.model.ArrayTypeInfo;
import org.glassfish.admin.rest.model.BeanReferenceTypeInfo;
import org.glassfish.admin.rest.model.BeanReferencesTypeInfo;
import org.glassfish.admin.rest.model.EntityInfo;
import org.glassfish.admin.rest.model.PrimitiveTypeInfo;
import org.glassfish.admin.rest.model.ResponseBody;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.utils.ExceptionUtil;
import org.glassfish.admin.rest.utils.JsonFilter;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.management.rest.lib.utils.HealthUtils;

public class DefaultMarshallers {
   public static final DefaultMarshallers _instance = new DefaultMarshallers();
   private Set unmarshallableTypes = new HashSet();
   private Set unUnmarshallableTypes = new HashSet();
   private Map marshallers = new HashMap();
   private Map unmarshallers = new HashMap();

   public static DefaultMarshallers instance() {
      return _instance;
   }

   private DefaultMarshallers() {
      this.registerScalarMarshaller("long", Long.class, new Long(0L), PrimitiveTypeInfo.LONG, Long.TYPE, Long.class, Integer.class);
      this.registerScalarMarshaller("int", Integer.class, new Integer(0), PrimitiveTypeInfo.INTEGER, Integer.TYPE, Integer.class);
      this.registerScalarMarshaller("double", Double.class, new Double(0.0), PrimitiveTypeInfo.DOUBLE, Double.TYPE, Double.class, Float.class);
      this.registerScalarMarshaller("float", Float.class, new Float(0.0F), PrimitiveTypeInfo.FLOAT, Float.TYPE, Float.class);
      this.registerScalarMarshaller("boolean", Boolean.class, Boolean.FALSE, PrimitiveTypeInfo.BOOLEAN, Boolean.TYPE, Boolean.class);
      this.registerBaseMarshaller(String.class, new StringMarshaller());
      this.registerBaseMarshaller(Properties.class, new PropertiesMarshaller());
      this.registerBaseMarshaller(Date.class, new DateMarshaller());
      this.registerBaseMarshaller(Throwable.class, new ThrowableMarshaller());
      this.registerBaseMarshaller(HealthState.class, new HealthStateMarshaller());
   }

   private void registerScalarMarshaller(String jsonTypeDescription, Class scalarClass, Object defaultValue, PrimitiveTypeInfo typeInfo, Class scalarType, Class... jsonTypes) {
      this.registerBaseMarshaller(scalarClass, new ScalarMarshaller(jsonTypeDescription, scalarClass, defaultValue, typeInfo, jsonTypes));
      this.registerBaseMarshaller(scalarType, new ScalarMarshaller(jsonTypeDescription, scalarType, defaultValue, typeInfo, jsonTypes));
   }

   private void registerBaseMarshaller(Class javaType, BaseMarshaller baseMarshaller) {
      if (baseMarshaller instanceof Marshaller) {
         this.marshallers.put(javaType, (Marshaller)baseMarshaller);
      }

      if (baseMarshaller instanceof Unmarshaller) {
         this.unmarshallers.put(javaType, (Unmarshaller)baseMarshaller);
      }

   }

   public Marshaller getMarshaller(HttpServletRequest request, Class javaType) throws Exception {
      Marshaller m = this.findMarshaller(request, javaType);
      if (m == null) {
         throw new Exception(MessageUtils.beanFormatter(request).msgMarshalUnknownType(javaType.getName()));
      } else {
         return m;
      }
   }

   public Unmarshaller getUnmarshaller(HttpServletRequest request, Class javaType) throws Exception {
      Unmarshaller m = this.findUnmarshaller(request, javaType);
      if (m == null) {
         throw new Exception(MessageUtils.beanFormatter(request).msgUnmarshalUnknownType(javaType.getName()));
      } else {
         return m;
      }
   }

   public Marshaller findMarshaller(HttpServletRequest request, Class javaType) throws Exception {
      if (this.unmarshallableTypes.contains(javaType)) {
         return null;
      } else {
         Marshaller marshaller = (Marshaller)this.marshallers.get(javaType);
         if (marshaller == null) {
            if (javaType.isArray()) {
               Class ct = javaType.getComponentType();
               if (BeanUtils.isBeanClass(ct)) {
                  marshaller = new BeansMarshaller(javaType);
               } else {
                  Marshaller componentMarshaller = this.findMarshaller(request, javaType.getComponentType());
                  if (componentMarshaller != null) {
                     marshaller = new ArrayMarshaller(javaType, componentMarshaller);
                  }
               }
            } else if (BeanUtils.isBeanClass(javaType)) {
               marshaller = (Marshaller)this.marshallers.get(javaType);
               if (marshaller == null) {
                  marshaller = new BeanMarshaller(javaType);
               }
            } else if (BeanUtils.isVBeanClass(javaType)) {
               marshaller = (Marshaller)this.marshallers.get(javaType);
               if (marshaller == null) {
                  marshaller = new VBeanMarshaller(request, javaType);
               }
            } else if (Throwable.class.isAssignableFrom(javaType)) {
               marshaller = (Marshaller)this.marshallers.get(Throwable.class);
            }

            if (marshaller != null) {
               this.registerBaseMarshaller(javaType, (BaseMarshaller)marshaller);
            } else {
               this.unmarshallableTypes.add(javaType);
            }
         }

         return (Marshaller)marshaller;
      }
   }

   public Unmarshaller findUnmarshaller(HttpServletRequest request, Class javaType) throws Exception {
      if (this.unUnmarshallableTypes.contains(javaType)) {
         return null;
      } else {
         Unmarshaller unmarshaller = (Unmarshaller)this.unmarshallers.get(javaType);
         if (unmarshaller == null) {
            if (javaType.isArray()) {
               Class ct = javaType.getComponentType();
               if (BeanUtils.isBeanClass(ct)) {
                  unmarshaller = new BeansMarshaller(javaType);
               } else {
                  Unmarshaller componentUnmarshaller = this.findUnmarshaller(request, javaType.getComponentType());
                  if (componentUnmarshaller != null) {
                     unmarshaller = new ArrayUnmarshaller(javaType, componentUnmarshaller);
                  }
               }
            } else if (BeanUtils.isBeanClass(javaType)) {
               unmarshaller = (Unmarshaller)this.unmarshallers.get(javaType);
               if (unmarshaller == null) {
                  unmarshaller = new BeanMarshaller(javaType);
               }
            }

            if (unmarshaller != null) {
               this.registerBaseMarshaller(javaType, (BaseMarshaller)unmarshaller);
            } else {
               this.unUnmarshallableTypes.add(javaType);
            }
         }

         return (Unmarshaller)unmarshaller;
      }
   }

   private static class HealthStateMarshaller extends MarshallerImpl {
      private HealthStateMarshaller() {
         super("{ state: string, subsystemName: string, partitionName: string, symptoms: [ { type: string, severity: string, instanceId: string, info: string } ] }", HealthState.class, (TypeInfo)null);
      }

      public TypeInfo getDocType(HttpServletRequest request, ApiInfo api) throws Exception {
         return this.getHealthStateEntity(request, api);
      }

      private EntityInfo getSymptomEntity(HttpServletRequest request, ApiInfo api) throws Exception {
         String entityClassName = Symptom.class.getName();
         EntityInfo e = api.getEntity(entityClassName);
         if (e == null) {
            e = MetaDataUtils.createEntity(request, api, entityClassName);
            MetaDataUtils.addStringProperty(request, e, "type");
            MetaDataUtils.addStringProperty(request, e, "severity");
            MetaDataUtils.addStringProperty(request, e, "instanceId");
            MetaDataUtils.addStringProperty(request, e, "info");
         }

         return e;
      }

      private EntityInfo getHealthStateEntity(HttpServletRequest request, ApiInfo api) throws Exception {
         String entityClassName = HealthState.class.getName();
         EntityInfo e = api.getEntity(entityClassName);
         if (e == null) {
            e = MetaDataUtils.createEntity(request, api, entityClassName);
            MetaDataUtils.addStringProperty(request, e, "state");
            MetaDataUtils.addStringProperty(request, e, "subsystemName");
            MetaDataUtils.addStringProperty(request, e, "partitionName");
            MetaDataUtils.addProperty((HttpServletRequest)request, (EntityInfo)e, (String)"symptoms", (TypeInfo)(new ArrayTypeInfo(this.getSymptomEntity(request, api))));
         }

         return e;
      }

      protected Object marshalNonNull(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
         JsonFilter.Scope filter = QueryUtils.getPropertiesFilter(ic.request(), ic.query()).newScope();
         filter.beginObjectAttr(property);

         JSONObject var7;
         try {
            JSONObject json = HealthUtils.getDetailedHealth((HealthState)javaVal);
            filter.trimJsonObject(json);
            var7 = json;
         } finally {
            filter.endObjectAttr();
         }

         return var7;
      }

      // $FF: synthetic method
      HealthStateMarshaller(Object x0) {
         this();
      }
   }

   private static class ThrowableMarshaller extends MarshallerImpl {
      private ThrowableMarshaller() {
         super("{ message: string, cause: { throwable ... } }", Throwable.class);
      }

      public TypeInfo getDocType(HttpServletRequest request, ApiInfo api) throws Exception {
         return this.getThrowableEntity(request, api);
      }

      private EntityInfo getThrowableEntity(HttpServletRequest request, ApiInfo api) throws Exception {
         String entityClassName = Throwable.class.getName();
         EntityInfo e = api.getEntity(entityClassName);
         if (e == null) {
            e = MetaDataUtils.createEntity(request, api, entityClassName);
            MetaDataUtils.addStringProperty(request, e, "state");
            MetaDataUtils.addStringProperty(request, e, "message");
            MetaDataUtils.addProperty((HttpServletRequest)request, (EntityInfo)e, (String)"cause", (TypeInfo)e);
         }

         return e;
      }

      protected Object marshalNonNull(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
         Throwable throwable = (Throwable)javaVal;
         JSONObject j = new JSONObject();
         j.put("message", ExceptionUtil.getThrowableMessage(throwable, false));
         Marshaller m = DefaultMarshallers.instance().getMarshaller(ic.request(), Throwable.class);
         j.put("cause", m.marshal(ic, rb, property, throwable.getCause()));
         return j;
      }

      // $FF: synthetic method
      ThrowableMarshaller(Object x0) {
         this();
      }
   }

   private static class VBeanMarshaller extends FullMarshallerImpl {
      private BeanType type;

      private VBeanMarshaller(HttpServletRequest request, Class javaType) throws Exception {
         super(describeType(request, javaType), javaType);
         this.type = getBeanType(request, javaType);
      }

      private static BeanType getBeanType(HttpServletRequest request, Class javaType) throws Exception {
         return BeanType.getBeanType(request, javaType.getName());
      }

      private static String describeType(HttpServletRequest request, Class javaType) throws Exception {
         BeanType type = getBeanType(request, javaType);
         StringBuilder sb = new StringBuilder();
         sb.append("{ ");
         boolean first = true;
         Iterator var5 = type.getPropertyTypes().iterator();

         while(var5.hasNext()) {
            PropertyType p = (PropertyType)var5.next();
            if (first) {
               first = false;
            } else {
               sb.append(", ");
            }

            sb.append(p.getName());
            sb.append(": ");
            sb.append(p.getMarshaller().getValueMarshaller().describeJsonType());
         }

         sb.append(" }");
         return sb.toString();
      }

      public TypeInfo getDocType(HttpServletRequest request, ApiInfo api) throws Exception {
         String typeName = this.type.getName();
         MetaDataUtils.createEntities(request, api, typeName, false);
         return api.getEntity(typeName);
      }

      public boolean matchesNonNull(InvocationContext ic, Object jsonVal) throws Exception {
         if (!(jsonVal instanceof JSONObject)) {
            return false;
         } else {
            JSONObject j = (JSONObject)jsonVal;
            Iterator var4 = this.type.getPropertyTypes().iterator();

            while(var4.hasNext()) {
               PropertyType p = (PropertyType)var4.next();
               if (p.isVisibleToRequest(ic)) {
                  if (!j.has(p.getName())) {
                     return false;
                  }

                  if (!p.getUnmarshaller().matches(ic, j.get(p.getName()))) {
                     return false;
                  }
               }
            }

            return true;
         }
      }

      protected Object unmarshalNonNull(InvocationContext ic, Object jsonVal) throws Exception {
         Object model = CompositeUtil.instance().getModel(this.type.getBeanClass());
         BeanUtils.setVBeanProperties(ic.clone(model), this.type, (JSONObject)jsonVal);
         return model;
      }

      protected Object marshalNonNull(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
         return BeanUtils.getVBean(ic.clone(javaVal), this.type);
      }

      // $FF: synthetic method
      VBeanMarshaller(HttpServletRequest x0, Class x1, Object x2) throws Exception {
         this(x0, x1);
      }
   }

   private static class BeansMarshaller extends FullMarshallerImpl {
      private BeansMarshaller(Class javaType) throws Exception {
         super(describeType(), javaType, new BeanReferencesTypeInfo(MetaDataUtils.entityDisplayName(javaType.getComponentType().getName()), javaType.getComponentType().getName()));
      }

      private static String describeType() {
         StringBuilder sb = new StringBuilder();
         sb.append("[ ").append("{ ").append("identity").append(": [ string ], ").append("links").append(": [ ").append("{ ").append("rel").append(": '").append("canonical").append("', ").append("href").append(": string").append(" }").append(" ]").append(" }").append("  ]");
         return sb.toString();
      }

      public boolean matches(InvocationContext ic, Object jsonVal) throws Exception {
         if (!(jsonVal instanceof JSONArray)) {
            return false;
         } else {
            JSONArray a = (JSONArray)jsonVal;

            for(int i = 0; i < a.length(); ++i) {
               JSONObject o = a.optJSONObject(i);
               if (o == null) {
                  return false;
               }

               JSONArray a2 = o.optJSONArray("identity");
               if (a2 == null) {
                  return false;
               }

               for(int i2 = 0; i2 < a2.length(); ++i2) {
                  if (!(a2.get(i2) instanceof String)) {
                     return false;
                  }
               }
            }

            return true;
         }
      }

      protected Object unmarshalNonNull(InvocationContext ic, Object jsonVal) throws Exception {
         JSONArray jsonArray = (JSONArray)jsonVal;
         Object[] javaArray = (Object[])((Object[])Array.newInstance(this.javaType().getComponentType(), jsonArray.length()));

         for(int i = 0; i < jsonArray.length(); ++i) {
            JSONObject item = jsonArray.getJSONObject(i);
            JSONArray identity = item.getJSONArray("identity");
            Object b = PathUtils.findBean(ic, identity);
            if (b != null && !PartitionUtils.isVisible(ic.clone(b))) {
               b = null;
            }

            if (b == null) {
               ExceptionUtil.badRequest(MessageUtils.beanFormatter(ic.request()).msgBeanNotFound(identity.toString()));
            }

            javaArray[i] = b;
         }

         return javaArray;
      }

      public Object marshal(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
         JSONArray items = new JSONArray();
         if (javaVal != null) {
            Object[] beans = (Object[])((Object[])javaVal);

            for(int i = 0; i < beans.length; ++i) {
               Object javaItem = beans[i];
               InvocationContext childIc = ic.clone(javaItem);
               if (PartitionUtils.isVisible(childIc)) {
                  JSONObject jsonItem = new JSONObject();
                  jsonItem.put("identity", PathUtils.getReferencedBeanJson(childIc));
                  RestJsonResponseBody itemRb = new RestJsonResponseBody(ic.request(), QueryUtils.getLinksFilter(ic.request(), ic.query()));
                  itemRb.setEntity(jsonItem);
                  if (javaItem != null) {
                     itemRb.addResourceLink("canonical", PathUtils.getUri(childIc));
                  }

                  items.put(itemRb.toJson());
               }
            }
         }

         return items;
      }

      // $FF: synthetic method
      BeansMarshaller(Class x0, Object x1) throws Exception {
         this(x0);
      }
   }

   private static class BeanMarshaller extends FullMarshallerImpl {
      private BeanMarshaller(Class javaType) throws Exception {
         super(describeType(), javaType, new BeanReferenceTypeInfo(MetaDataUtils.entityDisplayName(javaType.getName()), javaType.getName()));
      }

      private static String describeType() {
         return "[ string ]";
      }

      protected boolean matchesNonNull(InvocationContext ic, Object jsonVal) throws Exception {
         if (!(jsonVal instanceof JSONArray)) {
            return false;
         } else {
            JSONArray a = (JSONArray)jsonVal;

            for(int i = 0; i < a.length(); ++i) {
               if (!(a.get(i) instanceof String)) {
                  return false;
               }
            }

            return true;
         }
      }

      protected Object unmarshalNonNull(InvocationContext ic, Object jsonVal) throws Exception {
         Object b = PathUtils.findBean(ic, (JSONArray)jsonVal);
         if (b != null && !PartitionUtils.isVisible(ic.clone(b))) {
            b = null;
         }

         if (b == null) {
            ExceptionUtil.badRequest(MessageUtils.beanFormatter(ic.request()).msgBeanNotFound(jsonVal.toString()));
         }

         return b;
      }

      protected Object marshalNonNull(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
         InvocationContext refIc = ic.clone(javaVal);
         if (!PartitionUtils.isVisible(refIc)) {
            javaVal = null;
            refIc = ic.clone(javaVal);
         }

         if (javaVal != null) {
            rb.addResourceLink(property, PathUtils.getUri(refIc));
         }

         return PathUtils.getReferencedBeanJson(refIc);
      }

      // $FF: synthetic method
      BeanMarshaller(Class x0, Object x1) throws Exception {
         this(x0);
      }
   }

   private static class ArrayUnmarshaller extends UnmarshallerImpl {
      private Unmarshaller componentUnmarshaller;

      private ArrayUnmarshaller(Class arrayType, Unmarshaller componentUnmarshaller) throws Exception {
         super("[ " + componentUnmarshaller.describeJsonType() + " ]", arrayType);
         this.componentUnmarshaller = componentUnmarshaller;
      }

      public TypeInfo getDocType(HttpServletRequest request, ApiInfo api) throws Exception {
         return new ArrayTypeInfo(this.componentUnmarshaller.getDocType(request, api));
      }

      protected boolean matchesNonNull(InvocationContext ic, Object jsonVal) throws Exception {
         if (!(jsonVal instanceof JSONArray)) {
            return false;
         } else {
            JSONArray a = (JSONArray)jsonVal;

            for(int i = 0; i < a.length(); ++i) {
               if (!this.componentUnmarshaller.matches(ic, a.get(i))) {
                  return false;
               }
            }

            return true;
         }
      }

      protected Object unmarshalNonNull(InvocationContext ic, Object json) throws Exception {
         JSONArray jsonArray = (JSONArray)json;
         Object[] javaArray = (Object[])((Object[])Array.newInstance(this.javaType().getComponentType(), jsonArray.length()));

         for(int i = 0; i < jsonArray.length(); ++i) {
            javaArray[i] = this.componentUnmarshaller.unmarshal(ic, jsonArray.get(i));
         }

         return javaArray;
      }

      // $FF: synthetic method
      ArrayUnmarshaller(Class x0, Unmarshaller x1, Object x2) throws Exception {
         this(x0, x1);
      }
   }

   private static class ArrayMarshaller extends MarshallerImpl {
      private Marshaller componentMarshaller;

      private ArrayMarshaller(Class arrayType, Marshaller componentMarshaller) throws Exception {
         super("[ " + componentMarshaller.describeJsonType() + " ]", arrayType);
         this.componentMarshaller = componentMarshaller;
      }

      public TypeInfo getDocType(HttpServletRequest request, ApiInfo api) throws Exception {
         return new ArrayTypeInfo(this.componentMarshaller.getDocType(request, api));
      }

      protected Object marshalNonNull(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
         Object[] a = (Object[])((Object[])javaVal);
         JSONArray j = new JSONArray();
         Object[] var7 = a;
         int var8 = a.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Object o = var7[var9];
            j.put(this.componentMarshaller.marshal(ic, rb, property, o));
         }

         return j;
      }

      // $FF: synthetic method
      ArrayMarshaller(Class x0, Marshaller x1, Object x2) throws Exception {
         this(x0, x1);
      }
   }

   private static class DateMarshaller extends MarshallerImpl {
      private DateMarshaller() {
         super("string", Date.class, PrimitiveTypeInfo.DATE);
      }

      protected Object marshalNonNull(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
         return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")).format((Date)javaVal);
      }

      // $FF: synthetic method
      DateMarshaller(Object x0) {
         this();
      }
   }

   private static class PropertiesMarshaller extends FullMarshallerImpl {
      private PropertiesMarshaller() {
         super("{ key: string }", Properties.class, PrimitiveTypeInfo.PROPERTIES);
      }

      protected boolean matchesNonNull(InvocationContext ic, Object jsonVal) throws Exception {
         if (!(jsonVal instanceof JSONObject)) {
            return false;
         } else {
            JSONObject o = (JSONObject)jsonVal;
            Iterator i = o.keys();

            String k;
            do {
               if (!i.hasNext()) {
                  return true;
               }

               k = (String)i.next();
            } while(o.get(k) instanceof String);

            return false;
         }
      }

      protected Object unmarshalNonNull(InvocationContext ic, Object jsonVal) throws Exception {
         Properties p = new Properties();
         JSONObject o = (JSONObject)jsonVal;
         Iterator i = o.keys();

         while(i.hasNext()) {
            String k = (String)i.next();
            p.put(k, o.getString(k));
         }

         return p;
      }

      protected Object marshalNonNull(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
         Properties p = (Properties)javaVal;
         JSONObject j = new JSONObject();
         Iterator var7 = p.stringPropertyNames().iterator();

         while(var7.hasNext()) {
            String k = (String)var7.next();
            j.put(k, p.getProperty(k));
         }

         return j;
      }

      // $FF: synthetic method
      PropertiesMarshaller(Object x0) {
         this();
      }
   }

   private static class StringMarshaller extends FullMarshallerImpl {
      private StringMarshaller() {
         super("string", String.class, PrimitiveTypeInfo.STRING);
      }

      protected boolean matchesNonNull(InvocationContext ic, Object jsonVal) throws Exception {
         return jsonVal instanceof String;
      }

      // $FF: synthetic method
      StringMarshaller(Object x0) {
         this();
      }
   }

   private static class ScalarMarshaller extends FullMarshallerImpl {
      private Object defaultValue;
      private Class[] jsonTypes;

      private ScalarMarshaller(String jsonTypeDescription, Class javaType, Object defaultValue, PrimitiveTypeInfo typeInfo, Class... jsonTypes) {
         super(jsonTypeDescription, javaType, typeInfo);
         this.defaultValue = defaultValue;
         this.jsonTypes = jsonTypes;
      }

      public boolean matches(InvocationContext ic, Object jsonVal) throws Exception {
         if (this.isJsonNull(jsonVal)) {
            return false;
         } else {
            Class[] var3 = this.jsonTypes;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               Class jsonType = var3[var5];
               if (jsonType.isAssignableFrom(jsonVal.getClass())) {
                  return true;
               }
            }

            return false;
         }
      }

      public Object getDefaultValue() throws Exception {
         return this.defaultValue;
      }

      // $FF: synthetic method
      ScalarMarshaller(String x0, Class x1, Object x2, PrimitiveTypeInfo x3, Class[] x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }
}
