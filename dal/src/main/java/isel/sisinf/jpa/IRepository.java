package isel.sisinf.jpa;

public interface IRepository<T,TCol,TK> {
    T findByKey(TK key);
    TCol find(String jpql, Object... params);
}
