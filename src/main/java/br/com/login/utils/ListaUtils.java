package br.com.login.utils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
public class ListaUtils {

    public boolean empty(Collection<?> collection) {
        return Objects.isNull(collection) || collection.isEmpty();
    }

    public boolean filled(Collection<?> collection) {
        return !empty(collection);
    }
}
