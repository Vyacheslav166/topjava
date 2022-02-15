package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractBaseEntity {
    protected AtomicInteger id;

    protected AbstractBaseEntity(AtomicInteger id) {
        this.id = id;
    }

    public void setId(AtomicInteger id) {
        this.id = id;
    }

    public AtomicInteger getId() {
        return id;
    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}