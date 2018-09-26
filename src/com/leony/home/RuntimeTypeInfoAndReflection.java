package com.leony.home;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

public class RuntimeTypeInfoAndReflection {
    private class InnerPerson implements Runnable {
        private String name;
        private int age;
        private final String sex = "M";

        public InnerPerson(){}

        public InnerPerson(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public void setName(String name) {this.name = name;}
        public String getName() {return this.name;}
        public void setAge(int name) {this.age = age;}
        public int getAge() {return this.age;}

        @Override
        public void run() { /* empty */ }
    }

    public void getClassTypeInformation() {
        InnerPerson person = new InnerPerson("Leon", 32);
        Class<?> classObj = person.getClass();
        System.out.println("Print class name using getClass() method");
        showClassInfo(classObj);
        try {
            Class<?> classObj2 = Class.forName("com.leony.home.RuntimeTypeInfoAndReflection$InnerPerson");
            System.out.println("\nPrint class name using Class.forName() method");
            showClassInfo(classObj2);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Class<InnerPerson> classObj3 = InnerPerson.class;
        System.out.println("\nPrint class name using InnerPerson.class property");
        showClassInfo(classObj);

        Class<?> superClass = person.getClass().getSuperclass();
        System.out.println("\nSuperclass for InnerPerson is : " + superClass.getSimpleName());
        Class<?>[] interfaces = person.getClass().getInterfaces();
        boolean isInterface = interfaces[0].isInterface() == true; // show usage of isInterface() method
        System.out.println("Interfaces for InnerPerson is :" + interfaces[0].getSimpleName());
        int modifiers = person.getClass().getModifiers();
        if ((modifiers & Modifier.PRIVATE) > 0) {
            System.out.println("Is public modifier - bitwise check: true");
        }
        System.out.println("Is public modifier - boolean check: " + Modifier.isPrivate(modifiers));

        Field[] fields = classObj.getFields(); // only public fields
        Field[] declaredFields = classObj.getDeclaredFields(); // all fields
        System.out.println("\nPrint object fields");
        for (Field field : fields) {
            System.out.printf("%s | %s\n", field.getName(), field.getType());
        }
        System.out.println("\n Print object declared fields");
        for (Field field : declaredFields) {
            System.out.printf("%s | %s\n", field.getName(), field.getType());
        }

        Method[] methods = classObj.getMethods(); // all methods, including inherited
        Method[] declaredMethods = classObj.getDeclaredMethods(); // declared public methods
        System.out.println("\nPrint object methods");
        for (Method method : methods) {
            /* Excluding object methods - commented */
            // if (method.getDeclaringClass() != Object.class) {
                System.out.printf("%s | %s\n", method.getName(), method.getReturnType());
            // }
        }
        System.out.println("\nPrint object declared methods");
        for (Method method : declaredMethods) {
            System.out.printf("%s | %s\n", method.getName(), method.getReturnType());
        }
    }

    public void interactWithClassTypeInformation() {
        InnerPerson person = new InnerPerson("Leon", 32);
        Class<?> classObj = person.getClass();

        try {
            Method setNameMethod = classObj.getMethod("setName", String.class);
            setNameMethod.invoke(person, "Lalala: updated using reflection");
            System.out.println("Use method setName via reflection to update name");
            Method getNameMethod = classObj.getMethod("getName");
            Object result = getNameMethod.invoke(person);
            System.out.println("Print method getName invocation result: " + result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Create object instance using class type information");
        try {
            InnerPerson newPerson = (InnerPerson) classObj.newInstance(); // simplified creation for non-argument constructor
            Constructor constructor = classObj.getConstructor(String.class, int.class); // constructor with parameters
            InnerPerson newPerson2 = (InnerPerson)constructor.newInstance("Leon", 32);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showClassInfo(Class<?> classObj) {
        System.out.println("Print class name: " + classObj.getSimpleName());
        System.out.println("Print class fields: " + classObj.getFields());
        System.out.println("Print class constructors; " + classObj.getConstructors());
        System.out.println("Print class methods: " + classObj.getMethods());
    }
}
