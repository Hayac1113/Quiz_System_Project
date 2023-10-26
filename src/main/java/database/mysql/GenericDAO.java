package database.mysql;

import java.util.List;

public interface GenericDAO<T> {
    public List<T> getAll();
    public T getOneByPK(T PK);
    public void storeOne(T type);
}
