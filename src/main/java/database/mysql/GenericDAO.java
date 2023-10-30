package database.mysql;

import java.util.List;

public interface GenericDAO<T, U> {
    public List<T> getAll();

    public T getOneByPK(U PK);

    public void storeOne(T type);
}
