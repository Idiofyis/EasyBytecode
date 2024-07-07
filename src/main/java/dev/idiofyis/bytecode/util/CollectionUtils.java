package dev.idiofyis.bytecode.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class CollectionUtils {

    public static <T> Collection<Collection<T>> trimTill(Iterable<T> src, Supplier<Collection<T>> collectionSupplier, Supplier<Collection<Collection<T>>> containerSupplier, Predicate<T> endHere) {
        Iterator<T> iterator = src.iterator();
        Collection<Collection<T>> container = containerSupplier.get();
        while (iterator.hasNext()) {
            Collection<T> context = collectionSupplier.get();
            while (iterator.hasNext()) {
                context.add(iterator.next());
            }
            container.add(context);
        }
        return container;
    }

}
