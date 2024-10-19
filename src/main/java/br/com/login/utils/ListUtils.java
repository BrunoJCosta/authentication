package br.com.login.utils;

import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused")
public class ListUtils {

    public boolean empty(Collection<?> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public boolean filled(Collection<?> collection) {
        return !empty(collection);
    }
}
