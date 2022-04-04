package com.rainchat.placeprotect.utils.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CaseInsensitiveStringMap<V> implements Map<String, V> {
    private final Map<String, V> delegate;

    public CaseInsensitiveStringMap(Map<String, V> delegate) {
        this.delegate = delegate;
        this.normalize();
    }

    private String getLowerCase(Object obj) {
        return String.valueOf(obj).toLowerCase(Locale.ROOT);
    }

    private void normalize() {
        Map<String, V> linkedMap = new LinkedHashMap(this.delegate);
        this.clear();
        this.putAll(linkedMap);
        linkedMap.clear();
    }

    public int size() {
        return this.delegate.size();
    }

    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    public boolean containsKey(Object o) {
        return this.delegate.containsKey(this.getLowerCase(o));
    }

    public boolean containsValue(Object o) {
        return this.delegate.containsValue(o);
    }

    public V get(Object o) {
        return this.delegate.get(this.getLowerCase(o));
    }

    @Nullable
    public V put(String s, V v) {
        return this.delegate.put(this.getLowerCase(s), v);
    }

    public V remove(Object o) {
        return this.delegate.remove(this.getLowerCase(o));
    }

    public void putAll(@NotNull Map<? extends String, ? extends V> map) {
        map.forEach(this::put);
    }

    public void clear() {
        this.delegate.clear();
    }

    @NotNull
    public Set<String> keySet() {
        return this.delegate.keySet();
    }

    @NotNull
    public Collection<V> values() {
        return this.delegate.values();
    }

    @NotNull
    public Set<Entry<String, V>> entrySet() {
        return this.delegate.entrySet();
    }
}