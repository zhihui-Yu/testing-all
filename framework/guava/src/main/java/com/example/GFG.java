package com.example;

import java.lang.reflect.Method;
import java.math.BigInteger;


/**
 * @author simple
 */
public class GFG {
    private static class InnerClass {

        private int x;

        public InnerClass() {
        }

        private void y() {
        }
    }

    public static void main(String[] args) {
        InnerClass inner = new InnerClass();

        inner.x = 2;

        System.out.println(inner.x);

        inner.y();

        for (Method m : InnerClass.class.getDeclaredMethods()) {
            System.out.println(String.format("%08X", m.getModifiers()) + " "
                + m.getName());
        }

    }

    static void checkSynthetic(String name) {
        try {
            System.out.println(name + " : " + Class.forName(name).isSynthetic());
        } catch (ClassNotFoundException exc) {
            exc.printStackTrace(System.out);
        }
    }

    public static void main0(String[] args) throws Exception {
        new Inner();
        checkSynthetic("com.example.GFG");
        checkSynthetic("com.example.GFG$Inner");
        checkSynthetic("com.example.GFG$1");
    }

    // create main method
    public static void main2(String args[]) {

        try {
            // create object of Demo class
            Demo obj = new Demo();

            // print message of nested demo class
            System.out.println("private Message"
                + " variable of Demo class"
                + obj.message);

            // get class object of demo class
            Class classobj = obj.getClass();

            // get list of declared method objects
            // of class object of Demo
            Method[] methods = classobj.getDeclaredMethods();

            // loop through method list
            for (Method method : methods) {

                // check method is Synthetic or not
                boolean isSynthetic = method.isSynthetic();

                // print result
                System.out.println(method
                    + " method is Synthetic Method :"
                    + isSynthetic);
            }
        } catch (Exception e) {

            // Print Exception if any Exception occurs
            e.printStackTrace();
        }
    }

    // create main method
    public static void main1(String args[]) {

        try {

            // create class object for class BigInteger
            Class c = BigInteger.class;

            // get list of Method object
            Method[] methods = c.getMethods();

            System.out.println("Synthetic Methods"
                + " of BigInteger Class are");
            // Loop through Methods list
            for (Method m : methods) {

                // check whether the method is
                // Synthetic Method or not
                if (m.isSynthetic()) {
                    // Print Method name
                    System.out.println("Method: "
                        + m.getName());
                }
            }
        } catch (Exception e) {
            // print Exception is any Exception occurs
            e.printStackTrace();
        }
    }

    // create Demo class
    private static final class Demo {
        private String message = "A Computer Science"
            + " portal for geeks";
    }

    private static class Inner {
    }
}
