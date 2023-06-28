package com.tokarevaa.webapp;

import com.tokarevaa.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume();
        Class<? extends Resume> clazz = r.getClass();

        Field field = clazz.getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);

        // TODO : invoke r.toString via reflection
        try {
            Method method = clazz.getDeclaredMethod("toString");
            Object string = method.invoke(r);
            System.out.println("Invoke result: " + string);
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
