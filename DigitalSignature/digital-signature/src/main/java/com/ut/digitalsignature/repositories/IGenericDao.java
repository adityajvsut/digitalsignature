package com.ut.digitalsignature.repositories;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<T extends Serializable> {
 
//   T findOne(final long id);
 
//    List<T> findAll();
 
//   void create(final T entity);
 
   void update(final T entity);
 
   void delete(final T entity);

   void save(T entity);

   List<T> findEmail(String email);

   List<T> findValueByColumns(String Column1,String Value1,String Column2,String Value2,String Column3,String Value3);

   void setClazz( Class< T > clazzToSet );

   public List<T> findValueByColumn(String Column,String Value);
 
//   void deleteById(final long entityId);
}