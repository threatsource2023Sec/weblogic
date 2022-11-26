@start rule: main
/*
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 */

@packageStatement

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.beans.Beans;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import weblogic.utils.UnsyncHashtable;
import weblogic.utils.UnsyncStringBuffer;
import weblogicx.jsp.tags.BeanParamInterpreter;

public class @generated_class_name implements BeanParamInterpreter {
    static boolean debug = false;

    /* boolean,byte,short,char,int,float,long,double */
    private Object genericArrayAdd(Object array, Object value) throws Exception {
	if (!array.getClass().isArray())
	    throw new IllegalArgumentException("not array: " + array.getClass().getName());
	Class c = array.getClass().getComponentType();
	if (c == Boolean.TYPE)
	    return booleanArrayAdd(array, ((Boolean)value).booleanValue());
	else if (c == Byte.TYPE)
	    return byteArrayAdd(array, ((Byte)value).byteValue());
	else if (c == Short.TYPE)
	    return shortArrayAdd(array, ((Short)value).shortValue());
	else if (c == Character.TYPE)
	    return charArrayAdd(array, ((Character)value).charValue());
	else if (c == Integer.TYPE)
	    return intArrayAdd(array, ((Integer)value).intValue());
	else if (c == Float.TYPE)
	    return floatArrayAdd(array, ((Float)value).floatValue());
	else if (c == Long.TYPE)
	    return longArrayAdd(array, ((Long)value).longValue());
	else if (c == Double.TYPE)
	    return doubleArrayAdd(array, ((Double)value).doubleValue());
	else
	    return ObjectArrayAdd(array, value);
    }

    /* boolean,byte,short,char,int,float,long,double */
    private Object genericArrayDelete(Object array, int index) throws Exception {
	if (!array.getClass().isArray())
	    throw new IllegalArgumentException("not array: " + array.getClass().getName());
	Class c = array.getClass().getComponentType();
	if (c == Boolean.TYPE)
	    return booleanArrayDelete(array, index);
	else if (c == Byte.TYPE)
	    return byteArrayDelete(array, index);
	else if (c == Short.TYPE)
	    return shortArrayDelete(array, index);
	else if (c == Character.TYPE)
	    return charArrayDelete(array, index);
	else if (c == Integer.TYPE)
	    return intArrayDelete(array, index);
	else if (c == Float.TYPE)
	    return floatArrayDelete(array, index);
	else if (c == Long.TYPE)
	    return longArrayDelete(array, index);
	else if (c == Double.TYPE)
	    return doubleArrayDelete(array, index);
	else
	    return ObjectArrayDelete(array, index);
    }

    /* boolean,byte,short,char,int,float,long,double */
    private Object genericArrayShift(Object array, int index, boolean up) throws Exception {
	if (!array.getClass().isArray())
	    throw new IllegalArgumentException("not array: " + array.getClass().getName());
	Class c = array.getClass().getComponentType();
	if (c == Boolean.TYPE)
	    return booleanArrayShift(array, index, up);
	else if (c == Byte.TYPE)
	    return byteArrayShift(array, index, up);
	else if (c == Short.TYPE)
	    return shortArrayShift(array, index, up);
	else if (c == Character.TYPE)
	    return charArrayShift(array, index, up);
	else if (c == Integer.TYPE)
	    return intArrayShift(array, index, up);
	else if (c == Float.TYPE)
	    return floatArrayShift(array, index, up);
	else if (c == Long.TYPE)
	    return longArrayShift(array, index, up);
	else if (c == Double.TYPE)
	    return doubleArrayShift(array, index, up);
	else
	    return ObjectArrayShift(array, index, up);
    }

    @typeArrayShiftMethods
    /* { 0, 1, 2 } -> { 1, 2 }
    *  { 0, 1, 2 } -> { 0, 2 }
    *  { 0, 1, 2 } -> { 0, 1 }
    */
    @typeArrayDeleteMethods

    @typeArrayAddMethods
 
    @classBody
    /* the following rules must be called *AFTER* the classBody rule!!! */
    public void setArrayProp(String id, Object array, HttpSession session) throws Exception {
	int dot = id.lastIndexOf('.');
	if (dot < 0) {
	    throw new IllegalArgumentException("invalid array prop id (no '.'): '" + id + "'");
	}
	String objid = id.substring(0, dot);
	String paramName = id.substring(dot + 1);
	System.err.println("[set-array-prop]: setting '" + paramName + "' on object '"
			   + objid + "' arraylen=" + Array.getLength(array));
	Object o = get(objid, session);
	if (o == null) {
	    throw new IllegalArgumentException("no such object '" + objid + "'");
	}
	@allArrayPropSetters
    }

    private String removeArraySyntax(String name) {
	if (name.endsWith(".")) {
	    throw new IllegalArgumentException("name cannot end with '.': " + name);
	}
	UnsyncStringBuffer ret = new UnsyncStringBuffer();
	Vector v = new Vector();
	int dot;
	while ((dot = name.indexOf('.')) >= 0) {
	    String part = name.substring(0, dot);
	    name = name.substring(dot + 1);
	    v.addElement(part);
	}
	v.addElement(name);
	for (int i = 0; i < v.size(); i++) {
	    String s = (String)v.elementAt(i);
	    int ind = s.indexOf('[');
	    if (ind >= 0) {
		s = s.substring(0, ind);
	    }
	    ret.append(s);
	    if (i != v.size() - 1) {
		ret.append('.');
	    }
	}
	return ret.toString();
    }

    private Class getArrayComponentType(String id, HttpSession session) throws Exception {
	System.err.println("[get-array-component-type]: enter on id=" + id);
	int dot = id.indexOf('.');
	if (dot < 0) {
	    throw new IllegalArgumentException("cannot find top-level component (no '.') for id '" + id + "'");
	}
	String topName = id.substring(0, dot);
	String remainingName = id.substring(dot); // important: keep the first '.'!
	Object o = session.getAttribute(topName);
	if (o == null) {
	    throw new IllegalArgumentException("no object found in session for '" + topName + "' (full id='" + id + "')");
	}
	remainingName = removeArraySyntax(remainingName);
	System.err.println("[get-array-component-type]: check remainingName='" + remainingName +
			   "', id=" + id + ", type=" + o.getClass().getName());
	@arrayComponentTypeLoop
	throw new IllegalArgumentException("don't understand object in session, type='" + o.getClass().getName() + "'");
    }

    /* public new method */
    public void processNew(ServletRequest rq, HttpSession session) throws Exception {
	/* collect together all the params that end with "-new" */
	UnsyncHashtable allNewParams = new UnsyncHashtable();
	Enumeration e = rq.getParameterNames();
	while (e.hasMoreElements()) {
	    String name = (String)e.nextElement();
	    if (!name.endsWith("-new")) continue;
	    if (name.equals("-new")) throw new IllegalArgumentException("invalid new parameter name: '" + name + "'");
	    String val = (String)rq.getParameter(name);
	    name = name.substring(0, name.length() - 4);
	    name = name.toLowerCase();
	    allNewParams.put(name,val);
	}
	if (allNewParams.size() == 0) return;
	/* Collate all the params where the names match up to
	 * the last '.' (i.e., they have the same root object id...)
	 */
	UnsyncHashtable groupedNewParams = new UnsyncHashtable();
	e = allNewParams.keys();
	while (e.hasMoreElements()) {
	    String name = (String)e.nextElement();
	    int lastDot = name.lastIndexOf('.');
	    if (lastDot <= 0) {
		throw new IllegalArgumentException("invalid new parameter name (no '.'): \"" + name + "\"");
	    }
	    /* At some point, we have to distinguish between
	     * the syntax for attributes on a new complex type
	     * (e.g., where subbeans is an array of complex beans):
	     *  cb.root.subbeans.title-name = "some title"
	     * and the syntax for new primitive types:
	     *  cb.root.floats-new = "4.5"
	     * so we'll do this right here...
	     */
	    boolean isPrimitive = false;
	    String val = (String)allNewParams.get(name);
	    String objectId = name.substring(0, lastDot);
	    String paramName = name.substring(lastDot + 1);
	    try {
		Class seeIfThisIsPrimitive = getArrayComponentType(name, session);
		if (seeIfThisIsPrimitive.isPrimitive()) {
		    System.err.println("[process-new]: put primitive array under " + name);
		    isPrimitive = true;
		} else {
		    System.err.println("[process-new]: id='" + name + "' not a primitive array type, isA: " +
				       seeIfThisIsPrimitive.getName());
		}
	    } catch (ThreadDeath td) {
		throw td;
	    } catch (Throwable ignore) {
		System.err.println("[process-new]: id='" + name + "' not a primitive array type: " + ignore);
	    }
	    if (isPrimitive) {
		objectId = name;
		paramName = "";
	    } else {
		System.err.println("[process-new]: put non primitive array under " + paramName);
	    }
	    UnsyncHashtable oneNewObjectParams = (UnsyncHashtable)groupedNewParams.get(objectId);
	    if (oneNewObjectParams == null) {
		oneNewObjectParams = new UnsyncHashtable();
		groupedNewParams.put(objectId, oneNewObjectParams);
	    }
	    oneNewObjectParams.put(paramName, val);
	}
	e = groupedNewParams.keys();
	while (e.hasMoreElements()) {
	    String rootObjId = (String)e.nextElement();
	    UnsyncHashtable params = (UnsyncHashtable)groupedNewParams.get(rootObjId);
	    System.err.println("[process-new]:  rootid=" + rootObjId + " w/ params=" + params);
	    Class newObjectType = getArrayComponentType(rootObjId, session);
	    /* get a handle on the root object, which should be an array */
	    Object root = get(rootObjId, session);
	    if (root == null) {
		root = Array.newInstance(newObjectType, 0);
	    }
	    if (!root.getClass().isArray()) {
		throw new IllegalArgumentException("object with id '" + rootObjId + "' not array: is A " +
						   root.getClass().getName());
	    }
	    /* rootObjId refers to an array type.  Now need to:
	     * - figure out the component type for that array
	     * - construct a complex type for the params, or
	     * - construct a wrapper for a primitive
	     * - fill in attrubutes (invoke proper set_...() method)
	     * - call genericArrayAdd()
	     * - call setArrayProp()
	     */
	    Object newObj = null;
	    if (newObjectType.isPrimitive() || newObjectType == java.lang.String.class) {
		/* should only be one parameter */
		if (params.size() != 1) {
		    throw new IllegalArgumentException("for primitive object id='" + rootObjId +
						       "' should only have one param not " +
						       params.size() + ", (" + params + ")");
		}
		String pval = (String)params.elements().nextElement();
		if (newObjectType != java.lang.String.class) pval = pval.trim();
		if (newObjectType == java.lang.String.class) {
		    newObj = pval;
		} else if (newObjectType == Boolean.TYPE) {
		    newObj = Boolean.valueOf(pval);
		} else if (newObjectType == Byte.TYPE) {
		    newObj = Byte.valueOf(pval);
		} else if (newObjectType == Short.TYPE) {
		    newObj = Short.decode(pval);
		} else if (newObjectType == Character.TYPE) {
		    /* FIXME: no conversion from String -> char */
		    throw new IllegalArgumentException("FIXME: no conversion from String -> char");
		} else if (newObjectType == Integer.TYPE) {
		    newObj = Integer.decode(pval);
		} else if (newObjectType == Float.TYPE) {
		    newObj = Float.valueOf(pval);
		} else if (newObjectType == Long.TYPE) {
		    newObj = Long.valueOf(pval);
		} else if (newObjectType == Double.TYPE) {
		    newObj = Double.valueOf(pval);
		} else {
		    throw new IllegalArgumentException("don't understand primitive type: " + newObjectType.getName() + "??");
		}
	    } else {
		XParams xp = new XParams(rootObjId, params);
		newObj = Beans.instantiate(getClass().getClassLoader(), newObjectType.getName());
		Class c = newObjectType;
		@beanFillerCode
	    }
	    root = genericArrayAdd(root, newObj);
	    setArrayProp(rootObjId, root, session);
	}
    }

    /* public delete method */
    public void processDelete(ServletRequest rq, HttpSession session) throws Exception {
        Enumeration enum = rq.getParameterNames();
        while (enum.hasMoreElements()) {
            String name = (String)enum.nextElement();
            name = name.toLowerCase().trim();
            if (!name.endsWith("-delete")) continue;
            if (name.equals("-delete")) throw new IllegalArgumentException("invalid delete parameter: '-delete'");
            int ind = name.lastIndexOf('-');
            String objId = name.substring(0, ind);
	    if (objId.equals(".")) {
		throw new IllegalArgumentException("don't understand delete parameter: '" + name + "'");
	    }
	    if (objId.endsWith(".")) {
		objId = objId.substring(0, objId.length() - 1);
	    }
	    int arrayOpen = objId.lastIndexOf('[');
	    int arrayClose = objId.lastIndexOf(']');
	    if (arrayOpen == -1) {
		throw new IllegalArgumentException("bad array syntax in delete id='" + name + "'");
	    } else if (arrayClose != objId.length() - 1) {
		throw new IllegalArgumentException("bad array syntax in delete id='" + name + "'");
	    }
	    String arrayId = objId.substring(0, arrayOpen);
	    String indexStr = objId.substring(arrayOpen + 1, arrayClose);
	    int arrayIndex = -1;
	    try {
		arrayIndex = Integer.parseInt(indexStr);
	    } catch (NumberFormatException nfe) {
		throw new IllegalArgumentException("malformed array syntax (bad numeric index '" + indexStr + "' in " + name + ")");
	    }
	    Object o = get(arrayId, session);
	    if (o == null) {
		throw new IllegalStateException("getter for array property '" +
						arrayId + "' returned null on delete");
	    }
	    if (!o.getClass().isArray()) {
		throw new IllegalStateException("getter for array property '" +
						arrayId + "' returned non-array type on delete (is a '" +
						o.getClass().getName() + "')");
	    }
	    System.err.println("[delete]: deleting index " + arrayIndex + " from array attribute " +
			       arrayId);
	    o = genericArrayDelete(o, arrayIndex);
	    setArrayProp(objId.substring(0, arrayOpen), o, session);
        } // close while loop
    }

    /* public position change method */
    public void processShift(ServletRequest rq, HttpSession session) throws Exception {
	String[] upOrDown = rq.getParameterValues("shift-direction");
	if (upOrDown == null || upOrDown.length == 0) return;
	if (upOrDown.length > 1) throw new IllegalArgumentException("parameter 'shift-direction' multiply defined on request");
	upOrDown[0] = upOrDown[0].trim();
	boolean up = false;
	if ("up".equalsIgnoreCase(upOrDown[0]))
	    up = true;
	else if ("down".equalsIgnoreCase(upOrDown[0]))
	    up = false;
	else
	    throw new IllegalArgumentException("don't understand value for shift parameter: '" + upOrDown[0]);
	/* get the parameter values that are named "shift" */
	String[] shifts = rq.getParameterValues("shift");
	if (shifts == null) return;
	for (int i = 0; i < shifts.length; i++) {
	    String objid = shifts[i].toLowerCase();
	    System.err.println("[shift]: move object '" + objid + "' " + (up? "up": "down"));
	    /* verify that it ends with valid array syntax,
	     * and get the array index
	     */
	    int arrayOpen = objid.lastIndexOf('[');
	    int arrayClose = objid.lastIndexOf(']');
	    if (arrayOpen == -1) {
		throw new IllegalArgumentException("bad array syntax in shift id='" + shifts[i] + "'");
	    } else if (arrayClose != objid.length() - 1) {
		throw new IllegalArgumentException("bad array syntax in shift id='" + shifts[i] + "'");
	    } else if (arrayOpen == (arrayClose - 1)) {
		throw new IllegalArgumentException("bad array syntax in shift id='" + shifts[i] + "'");
	    }
	    String arrayid = objid.substring(0, arrayOpen);
	    String indexStr = objid.substring(arrayOpen + 1, arrayClose);
	    int arrayIndex = -1;
	    try {
		arrayIndex = Integer.parseInt(indexStr);
	    } catch (NumberFormatException nfe) {
		throw new IllegalArgumentException("malformed array syntax (bad numeric index '" + indexStr + "' in " + shifts[i] + ")");
	    }
	    System.err.println("[shift]: move index '" + arrayIndex +
			       "' of array '" + arrayid +  "' " + (up? "up": "down"));
	    Object array = get(arrayid, session);
	    if (array == null) {
		throw new IllegalArgumentException("cannot find array '" + arrayid + "' for shift param '" +
						   shifts[i] + "'");
	    }
	    array = genericArrayShift(array, arrayIndex, up);
	    setArrayProp(arrayid, array, session);
	}
	
    }
    /* public all method */
    public void processAll(ServletRequest rq, HttpSession session) throws Exception {
        set(rq, session);
        processNew(rq, session);
        processDelete(rq, session);
        processShift(rq, session);
    }

    /* public set method */
    public void set(ServletRequest rq, HttpSession session) throws Exception {
        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String name = (String)e.nextElement();
            Object val = session.getAttribute(name);
            /* REMIND/FIXME: inhertance might make us call the wrong method here */
	    @publicSetLoop
	} // end while
    }

    /* public get method */
    public Object get(String objectid, HttpSession session) throws Exception {
        int dot = objectid.indexOf('.');
        if (dot < 0) return session.getAttribute(objectid);
        String topName = objectid.substring(0, dot);
        String remainingName = objectid.substring(dot + 1);
        Object val = session.getAttribute(topName);
        if (val == null) {
            throw new IllegalArgumentException("no object found by name '" + topName + "' in session: fullname=" + objectid);
        }
        /* REMIND/FIXME: inhertance might make us call the wrong method here */
        @publicGetLoop 
    } // end public get get method

    @checkDefaultConstructorBlock
} // end entire class

@end rule: main

@start rule: bean_set_method

void @set_method_name(@currentBeanType bean, XParams x) throws Exception {
    if (debug) System.out.println("[set]: enter method for " + bean.getClass().getName() + " id=" + x.getBeanId());
    Enumeration enum = x.getNames();
    while (enum.hasMoreElements()) {
        String name = (String)enum.nextElement();
        name = name.toLowerCase();
        if (name.endsWith("-new") || name.endsWith("-delete")) continue;
        String val = x.get(name);
        System.out.println("[set]: examining '" + name + "'='" + val + "' for type "  + bean.getClass().getName() + ":");
	/** figure out if we're using array syntax on
	 * this component of the name (up to '.' or the
	 * end, whichever is first)...
	 */
	boolean arraySyntax = false;
	int arrayIndex = -1;
	int dot = name.indexOf('.');
	boolean atEnd = true;
	if (dot >= 0)
	    atEnd = false;
	int arrayOpen = name.indexOf('[');
	if (arrayOpen >= 0) {
	    int arrayClose = name.indexOf(']');
	    if (arrayClose < 0)
		throw new IllegalArgumentException("malformed array syntax (no ']') in param '" + name + "'");
	    if (arrayClose < arrayOpen)
		throw new IllegalArgumentException("malformed array syntax (']' preceeds '[') in param '" + name + "'");
	    /* decide if arraySyntax is true */
	    if (!atEnd) {
		if (arrayOpen < dot && arrayClose < dot) {
		    arraySyntax = true;
		} else if (arrayOpen < dot) {
		    throw new IllegalArgumentException("malformed array syntax ('.' between '[' and ']') in param '" + name + "'");
		}
	    } else {
		arraySyntax = true;
	    }
	    if (arraySyntax) {
		/* figure out index */
		String indexStr = name.substring(arrayOpen + 1, arrayClose).trim();
		try {
		    arrayIndex = Integer.parseInt(indexStr);
		} catch (NumberFormatException nfe) {
		    throw new IllegalArgumentException("malformed array syntax (bad numeric index '" + indexStr + "' in " + name + ")");
		}
	    }
	}
        @currentBeanSetterLoop
    } // end while
}
@end rule: bean_set_method
	
@start rule: bean_get_method
Object @get_method_name(@currentBeanType bean, String id) throws Exception {
        if (debug) System.out.println("[get]: enter method for " + bean.getClass().getName() + " id=" + id);
        int dot = id.indexOf('.');
        String componentName = id;
        String remainingName = null;
        boolean atEnd = false;
        if (dot >= 0) {
            componentName = id.substring(0, dot);
            remainingName = id.substring(dot + 1);
        } else {
            atEnd = true;
        }
        boolean arraySyntax = false;
        int arrayIndex = -1;
        { // figure out array index for this component, if any...
            int arrayOpenIndex = componentName.indexOf('[');
            int arrayCloseIndex = componentName.indexOf(']');
            if (arrayOpenIndex > -1 && arrayCloseIndex > -1 && arrayOpenIndex < arrayCloseIndex) {
                String indexStr = componentName.substring(arrayOpenIndex + 1, arrayCloseIndex).trim();
                componentName = componentName.substring(0, arrayOpenIndex);
                if (indexStr.length() > 0) {
                    arraySyntax = true;
                    try { arrayIndex = Integer.parseInt(indexStr); } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("malformed array index in '" + id + "'");
                    } // close catch
                } // close array index if
            } else {
            }
        } // close figure out array index for this comp...
	@currentBeanGetterLoop
}
@end rule: bean_get_method

@start rule: type_array_delete_method
    private @type[] @typeArrayDelete(Object o, int index) {
	@type[] array = (@type[])o;
	/* arraycopy(): src, srcpos, dst, dstpos, len */
	if (array.length == 0) {
	    return array;
	}
	@type[] ret = null;
	if (array.getClass().getComponentType().isPrimitive())
	    ret = new @type[array.length - 1];
	else
	    ret = (@type[])Array.newInstance(array.getClass().getComponentType(), array.length - 1);

	/* copy the beginning of the original to the beginning of the new one */
	System.arraycopy(array, 0, ret, 0, index);
	/* copy the remainder, iff it wasn't the last one */
	if (index < array.length - 1)
	    System.arraycopy(array, index + 1, ret, index, ret.length - index);
	return ret;
    }

@end rule: type_array_delete_method

@start rule: type_array_shift_method
    private @type[] @typeArrayShift(Object o, int index, boolean up) {
	@type[] array = (@type[])o;
	if (index == 0 && up) return array;
	if (index == array.length - 1 && !up) return array;
	@type toShift = array[index];
	@type neighbor;
	if (up) {
	    neighbor = array[index - 1];
	    array[index - 1] = toShift;
	} else {
	    neighbor = array[index + 1];
	    array[index + 1] = toShift;
	}
	array[index] = neighbor;
	return array;
    }

@end rule: type_array_shift_method

@start rule: type_array_add_method
    private @type[] @typeArrayAdd(Object o, @type value) {
	@type[] array = (@type[])o;
	/* arraycopy(): src, srcpos, dst, dstpos, len */
	@type[] ret = null;
	if (array.getClass().getComponentType().isPrimitive())
	    ret = new @type[array.length + 1];
	else
	    ret = (@type[])Array.newInstance(array.getClass().getComponentType(), array.length + 1);

	/* copy the beginning of the original to the beginning of the new one */
	System.arraycopy(array, 0, ret, 0, array.length);
	ret[array.length] = value;
	return ret;
    }

@end rule: type_array_add_method
    
