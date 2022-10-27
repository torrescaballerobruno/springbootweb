package com.bolsadeideas.springboot.app.models.dao;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ClienteDaoImpl implements IClienteDao{

    @PersistenceContext  //Usa la H2, por defecto si no se a configurado otra
    private EntityManager em;


    @Override
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente").getResultList();
    }

    @Override
    public void save(Cliente cliente){
        if(cliente.getId() != null && cliente.getId()>0){
            em.merge(cliente);
        }else {
            em.persist(cliente);
        }
    }

    @Override
    public Cliente findOne(Long id){
        return em.find(Cliente.class,id);
    }

    @Override
    public void delete(Long id) {
        Cliente cliente = findOne(id);
        em.remove(findOne(id));
    }


}