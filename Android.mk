LOCAL_PATH:= $(call my-dir)



include $(CLEAR_VARS)
LOCAL_PROGUARD_FLAG_FILES := proguard.flags
LOCAL_JAVA_LIBRARIES := org.apache.http.legacy
LOCAL_MANIFEST_FILE := app/src/main/AndroidManifest.xml
LOCAL_DEX_PREOPT := false
LOCAL_CERTIFICATE := platform


LOCAL_RESOURCE_DIR := $(LOCAL_PATH)/app/src/main/res
LOCAL_SRC_FILES := $(call all-java-files-under, app/src/main/java)

LOCAL_PACKAGE_NAME := 	MGCarAppStore

include $(BUILD_PACKAGE)
include $(CLEAR_VARS)
include $(BUILD_MULTI_PREBUILT)
include $(call all-makefiles-under,$(LOCAL_PATH))
