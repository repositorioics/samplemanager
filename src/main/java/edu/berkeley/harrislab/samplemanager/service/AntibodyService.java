package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.Antibody;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Everts Morales Reyes.
 */

@Service("AntibodyService")
@Transactional
public class AntibodyService {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retorna todos los registros
     */

    @SuppressWarnings("unchecked")
    public List<Antibody> getAntibody() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Antibody");
        // Retrieve all
        return  query.list();
    }

    /**
     * Retorna todos los registros activos
     */

    @SuppressWarnings("unchecked")
    public List<Antibody> getActiveAntibody() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Antibody antibody where antibody.pasive = '0'");
        // Retrieve all
        return  query.list();
    }


    /**
     * Retorna un registro
     * @param antibody_id
     */

    public Antibody getAntibodyByantibody_id(String antibody_id) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Antibody antibody where " +
                "antibody.antibody_id =:antibody_id");
        query.setParameter("antibody_id",antibody_id);
        Antibody antibody = (Antibody) query.uniqueResult();
        return antibody;
    }

    /**
     * Guarda un registro
     *
     */
    public void saveAntibody(Antibody antibody) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(antibody);
    }

}
