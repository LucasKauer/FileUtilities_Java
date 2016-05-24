package br.com.lucaskauer.fileutilities.specification;

public interface BaseSpecification<T> {
	@SuppressWarnings("unchecked")
	public boolean isSatisfactoryFor(T... objects);
	
	public boolean isSatisfactoryFor(T object);
}
