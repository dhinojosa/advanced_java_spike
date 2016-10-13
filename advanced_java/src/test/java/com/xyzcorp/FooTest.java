package com.xyzcorp;

import com.xyzcorp.people.Californian;
import com.xyzcorp.people.Person;
import com.xyzcorp.people.SanFranciscan;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FooTest {

    @Test
    public void testFooInvariant() throws Exception {
        Foo<Californian> foo = new Foo<>();
        foo.addTo(new ArrayList<Californian>(), new Californian());
    }

    @Test
    public void testSetterWithSuper() throws Exception {
        Foo<SanFranciscan> foo = new Foo<>();
        foo.addTo(new ArrayList<Californian>(), new SanFranciscan());
    }

    @Test
    public void testGetterWithExtends() {
        Foo<Californian> foo = new Foo<>();
        List<SanFranciscan> list = new ArrayList<>();
        foo.getFrom(list);
    }
}
