package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> getById(long id) throws SQLException;

    List<T> getAll() throws SQLException;

    int delete(T t) throws SQLException;

    int update(T t) throws SQLException;

    void saveExecute(T t) throws SQLException;

    void savePrep(T t) throws SQLException;

    void saveBatch(T t) throws SQLException;
}
