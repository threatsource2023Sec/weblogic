# machine-generated DO NOT EDIT
#
# this makefile is meant for Microsoft's nmake.exe utility
# it WILL NOT WORK with GNU make or a Unix make

!ifndef JAVACOMPILER
JAVACOMPILER=javac
!endif

!ifndef JAVA_HOME
JAVA_HOME=C:\java\java130
!endif

TARGET=F:\weblogic\src810_141_03sj\server\classes
LOCAL_TARGET=$(TARGET)\weblogic\servlet\ejb2jsp\gui

CPATH=$(TARGET);F:\weblogic\dev\src810;$(JAVA_HOME)\jre\lib\rt.jar;$(JAVA_HOME)\lib\classes.zip;$(CLASSPATH)

WEBLOGIC_SERVLET_EJB2JSP_GUI=$(TARGET)\weblogic\servlet\ejb2jsp\gui

CLASSES=\
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\ArrayEditorDialog.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\BasePanel.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\BeanDescriptorPanel.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\EJBMethodDescriptorPanel.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\EJBTaglibDescriptorPanel.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\EJBTaglibDescriptorTree.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\FilesystemInfoDescriptorPanel.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\HelpFrame.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\Main.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\MethodParamDescriptorPanel.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\PreferencesDialog.class \
	$(WEBLOGIC_SERVLET_EJB2JSP_GUI)\Prefs.class 


CPFILES=\
	$(LOCAL_TARGET)\documentation.html \
	$(LOCAL_TARGET)\help.html 


all: .INIT $(CLASSES) $(CPFILES)

.INIT: FORCE
	@if not exist $(LOCAL_TARGET) @mkdir $(LOCAL_TARGET)

clean: .INIT FORCE
	cd $(WEBLOGIC_SERVLET_EJB2JSP_GUI)
	rm -f *.*

clean_subdirs: clean


FORCE :



.SUFFIXES: .g .java .java .class .txt .txt .gif .gif .jpg .jpg .html .html .j .j .dtd .dtd .properties .properties

.java{$(WEBLOGIC_SERVLET_EJB2JSP_GUI)}.class :
	$(JAVACOMPILER) $(JAVACOPTS) -classpath $(CPATH) \
	-d $(TARGET) $<

.txt{$(LOCAL_TARGET)}.txt :
	copy $< $(LOCAL_TARGET)

.html{$(LOCAL_TARGET)}.html :
	copy $< $(LOCAL_TARGET)

.gif{$(LOCAL_TARGET)}.gif :
	copy $< $(LOCAL_TARGET)

.properties{$(LOCAL_TARGET)}.properties :
	copy $< $(LOCAL_TARGET)

.jpg{$(LOCAL_TARGET)}.jpg :
	copy $< $(LOCAL_TARGET)

.dtd{$(LOCAL_TARGET)}.dtd :
	copy $< $(LOCAL_TARGET)

.j{$(LOCAL_TARGET)}.j :
	copy $< $(LOCAL_TARGET)

