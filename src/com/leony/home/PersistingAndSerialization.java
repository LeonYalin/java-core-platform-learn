package com.leony.home;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by leony on 26/09/2018.
 */
public class PersistingAndSerialization implements Serializable {
    private static final long serialVersionUID = -6607866523300872961L;
    String filePath = new File("").getAbsolutePath() + "\\src\\com\\leony\\resources\\grades7.txt";

    private class InnerSerializable implements Serializable {
        private static final long serialVersionUID = 8400262784897505343L;
        private String name;
        private int age;
        private final String hobby = "Playing guitar";
        private  transient final int weigth = 85; // won't be serialized, but WILL be de-serialized.

        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public int getAge() {return age;}
        public void setAge(int age) {this.age = age;}

        public InnerSerializable(){}
        public InnerSerializable(String name, int age) {
            this.name = name;
            this.age = age;
        }

        /**
         * Customizing the serialization process
         */
        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
        }

        /**
         * Customizing the deserialization process
         */
        private void readObject(ObjectInputStream in) throws Exception {
//             in.defaultReadObject(); // default behavior

            ObjectInputStream.GetField fields = in.readFields();
            name = (String)fields.get("name", null);
            age = (int)fields.get("age", 0);
        }

        @Override
        public String toString() {
            return String.format("InnerSerializable: name - %s, age - %d, hobby - %s", name, age, hobby);
        }
    }

    public void serializingObjects() {
        InnerSerializable person = new InnerSerializable("Leon Yalin", 32);

        System.out.println("Serializing an object: " + person);
        this.savePerson(person);

        InnerSerializable newPerson = this.loadPerson();
        System.out.println("DeSerializing an object from file: " + newPerson);

    }

    private void savePerson(InnerSerializable person) {
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
            stream.writeObject(person);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private InnerSerializable loadPerson() {
        InnerSerializable person = null;
        try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(Paths.get(filePath)))) {
            person = (InnerSerializable)stream.readObject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return person;
    }
}
