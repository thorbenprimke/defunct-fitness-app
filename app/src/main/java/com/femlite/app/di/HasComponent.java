package com.femlite.app.di;

/**
 * An interface that ensures a class can provide a specific component.
 */
public interface HasComponent<C> {
    C getComponent();
}
