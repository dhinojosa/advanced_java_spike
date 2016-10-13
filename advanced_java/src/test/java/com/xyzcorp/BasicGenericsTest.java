package com.xyzcorp;

import com.xyzcorp.people.*;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class BasicGenericsTest {

    @Test
    public void testDiamonds() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Hello");
        String answer = arrayList.get(0);
    }

    @Test
    public void testUsingBox() {
        Box<String> box = new Box<>();
        box.setContents("Hello");
        assertEquals("Hello", box.getContents());

        List<String> listOfTypes = Arrays.stream(box.getClass().getMethods())
                .map(m -> m.getGenericReturnType() + " from " + m.getName())
                .collect(Collectors.toList());

        System.out.println(box.getContents().getClass().getName());
    }

    @Test
    public void testUsingBox2() {
        Box<Box<Integer>> box = new Box<>(new Box<>(5));
        assertEquals(new Integer(5), box.getContents().getContents());
    }

    //Look at Bridge

    @Test
    public void testInvariance() {
        Box<Californian> boxOfCalifornians = new Box<Californian>();
        boxOfCalifornians.setContents(new Californian());
        Californian californian = boxOfCalifornians.getContents();
        System.out.println("californian = " + californian);
    }


    @Test
    public void testCovarianceAssignments() {
        Box<NorthernCalifornian> northernCalifornianBox = new Box<>();
        Box<?> boxOfObjects = northernCalifornianBox; //(<?> == <? extends Object>
        Box<? extends American> boxOfAmericans = northernCalifornianBox;
        Box<? extends Californian> boxOfCalifornians = northernCalifornianBox;
        Box<? extends NorthernCalifornian> boxOfNorthernCalifornians = northernCalifornianBox;
        //Box<? extends SanFranciscan> boxOfSanFrancisicans = northernCalifornianBox;
    }

    @Test
    public void testCovarianceGetPrinciple() {
        Box<SanFranciscan> sanFranciscanBox = new Box<>();
        Box<? extends Californian> californiansOrSubClasses = sanFranciscanBox;

        Object object = californiansOrSubClasses.getContents();
        American americans = californiansOrSubClasses.getContents();
        Californian californians = californiansOrSubClasses.getContents();
        //NorthernCalifornian northernCalifornian = californiansOrSubClasses.getContents();
        //I won't bother with San Franciscan
    }

    @Test
    public void testCovarianceSetPrinciple() throws Exception {
        Box<SanFranciscan> sanFranciscanBox = new Box<>();
        Box<? extends Californian> californiansOrSubClasses = sanFranciscanBox;

        //californiansOrSubClasses.setContents(new Object());
        //californiansOrSubClasses.setContents(new American());
        //californiansOrSubClasses.setContents(new Californian());
        //californiansOrSubClasses.setContents(new NorthernCalifornian());
        //californiansOrSubClasses.setContents(new SanFranciscan());
        californiansOrSubClasses.setContents(null);
    }

    @Test
    public void testContravarianceAssignments() {
        Box<Californian> californianBox = new Box<>();
        //----We do not see what is behind this line

        //Box<? super Object> boxOfObject = californianBox;
        //Box<? super Person> boxOfPeople = californianBox;
        //Box<? super American> boxOfAmerican = californianBox;
        Box<? super Californian> boxOfCalifornians = californianBox;
        Box<? super NorthernCalifornian> boxOfNorthernCalifornians = californianBox;
        Box<? super SanFranciscan> boxOfSanFranciscan = californianBox;
    }

    @Test
    public void testContravarianceGetPrinciple() {
        Box<Californian> boxCalifornians = new Box<>();
        Box<? super SanFranciscan> boxOfSanFranciscansAndHigher = boxCalifornians;

        //SanFranciscan sanFranciscan = boxOfSanFranciscansAndHigher.getContents();
        //NorthernCalifornian northernCalifornian = boxOfSanFranciscansAndHigher.getContents();
        //Californian californian = boxOfSanFranciscansAndHigher.getContents();
        //American american = boxOfSanFranciscansAndHigher.getContents();
        //Person person = boxOfSanFranciscansAndHigher.getContents();
        Object object = boxOfSanFranciscansAndHigher.getContents();
    }

    public void testUseZoo() {
         Zoo<Integer> zoo = new Zoo();
        zoo.foo(40, "Hello", new ArrayList<>());
    }

    @Test
    public void testBoxMap() {
        Box<String> intBox = new Box<>("Foo");
        System.out.println(intBox.map(String::length));

    }

    public void listMethodsFromClass(Class<? extends Object> clazz) {
            clazz.getMethods();
    }

    public void testWIthClass() {
        listMethodsFromClass(Person.class);

    }




}
