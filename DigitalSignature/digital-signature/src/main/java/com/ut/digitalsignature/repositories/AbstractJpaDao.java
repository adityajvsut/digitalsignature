package com.ut.digitalsignature.repositories;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;

@SuppressWarnings("unchecked")

public abstract class AbstractJpaDao< T extends Serializable > {
 
   private Class< T > clazz;
 
   @PersistenceContext
   EntityManager entityManager;

 
   public void setClazz( Class< T > clazzToSet ) {
      this.clazz = clazzToSet;
   }
 
   public T findOne( final Long id ){
      return entityManager.find( clazz, id );
   }

   public List<T> findEmail( final String email ){
      return entityManager.createQuery( "from " + clazz.getName() + " a " + " where " + " a.email="+"'"+email+"'" ).getResultList();
   }
   // public List< T > findAll(){
   //    return entityManager.createQuery( "from " + clazz.getName() )
   //     .getResultList();
   // }

   public List<T> findValueByColumn(String Column,String Value){
      List<T> querylist = entityManager.createQuery("from "+clazz.getName()+" a where a."+Column+"= '"+Value+"'").getResultList();
      // if(querylist.isEmpty()) throw new ColumnValueNotFoundException(Column + " " + Value + " does not exist"); 
      return querylist;
   }
 
   public List<T> findValueByColumns(String Column1,String Value1,String Column2,String Value2,String Column3,String Value3){
      List<T> querylist = entityManager.createQuery("from "+clazz.getName()+" a where a."+Column1+"='"+Value1+
                           "' and a."+Column2+"='"+Value2+
                           "' and a."+Column3+"='"+Value3+"'").getResultList();
      // if(querylist.isEmpty()) throw new ColumnValueNotFoundException(Column1 + " " + Value1 + " does not exist"); 
      return querylist;
   }

   @Transactional
   public void save( T entity ){
      createSession().persist(entity);
   }
 
   @Transactional
   public void update( T entity ){
      entityManager.merge( entity );
   }
 
   public void delete( T entity ){
      entityManager.remove( entity );
   }
   // public void deleteById( Long entityId ){
   //    T entity = getById( entityId );
   //    delete( entity );
   // }

   public Session createSession(){
      return entityManager.unwrap(Session.class);
   }

}