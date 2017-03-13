package br.com.lucaskauer.fileutilities.domain.interfaces;

public interface IBaseObject<T> {

    public T get();

    public void set(T anotherValue);

    @Override
    public String toString();

    @Override
    public boolean equals(Object obj);

    @Override
    public int hashCode();
}
