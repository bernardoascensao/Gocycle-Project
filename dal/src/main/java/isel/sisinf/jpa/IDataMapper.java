package isel.sisinf.jpa;

public interface IDataMapper<T> {
    T create(T entity);
    T update(T entity);
    T delete(T entity);
}
