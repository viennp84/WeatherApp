package dao;

import java.util.List;

/*
 * Vien Nguyen
 * CST361
 * Data Access Object
 * This is interface that contains the regular methods.
 */
//Methods for CRUD
public interface DataAccessInterface <T> {

	public boolean create(T t);
	public List<T> read(T t);
	public boolean update(T t);
	public boolean delete(T t);
}
