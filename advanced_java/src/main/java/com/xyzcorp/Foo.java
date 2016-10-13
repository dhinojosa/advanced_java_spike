package com.xyzcorp;

import java.util.List;
import java.util.Optional;

public class Foo<A> { //A is a Californian
   public void addTo(List<? super A> list, A a) {
      list.add(a);
   }

   public Optional<A> getFrom(List<? extends A> list) {
      if (list.isEmpty()) return Optional.empty();
      return Optional.of(list.get(0));
   }
}
