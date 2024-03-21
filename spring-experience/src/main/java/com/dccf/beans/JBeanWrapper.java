package com.dccf.beans;

public class JBeanWrapper {
    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public JBeanWrapper() {
    }

    public JBeanWrapper(Object wrappedInstance, Class<?> wrappedClass) {
        this.wrappedInstance = wrappedInstance;
        this.wrappedClass = wrappedClass;
    }

    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    public void setWrappedInstance(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return wrappedClass;
    }

    public void setWrappedClass(Class<?> wrappedClass) {
        this.wrappedClass = wrappedClass;
    }
}
