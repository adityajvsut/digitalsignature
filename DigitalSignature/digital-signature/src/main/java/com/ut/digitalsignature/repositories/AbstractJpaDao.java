package com.ut.digitalsignature.repositories;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.Transaction;

@SuppressWarnings("unchecked")

public abstract class AbstractJpaDao< T extends Serializable > {
 
   private Class< T > clazz;
 
   @PersistenceContext
   EntityManager entityManager;

 
   public void setClazz( Class< T > clazzToSet ) {
      this.clazz = clazzToSet;
   }
 
   public T findOne( final T id ){
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
      return querylist;
   }
 
   public List<T> findValueByColumns(String Column1,String Value1,String Column2,String Value2,String Column3,String Value3){
      List<T> querylist = entityManager.createQuery("from "+clazz.getName()+" a where a."+Column1+"='"+Value1+
                           "' and a."+Column2+"='"+Value2+
                           "' and a."+Column3+"='"+Value3+"'").getResultList();
      return querylist;
   }

   public void save( T entity ){
      Session session = createSession().getSessionFactory().openSession();
      Transaction transaction = session.beginTransaction();
      session.save(entity);
      transaction.commit();
      session.close();
   }
 
   @Transactional
   public void update( T entity ){
      entityManager.merge( entity );
   }
 
   public void delete( T entity ){
      entityManager.remove( entity );
   }

   // public List<T> selectQuery(){
   //    CriteriaBuilder builder = entityManager.getCriteriaBuilder();
   //    CriteriaQuery<T> cquery = builder.createQuery(clazz);
   //    Root<T> root = cquery.from(clazz);
   //    cquery.multiselect(root.get(clazz.getClass());
   //    List<T> result = em.createQuery(cq).getResultList();
   // }

   public Session createSession(){
      return entityManager.unwrap(Session.class);
   }

}