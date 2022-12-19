package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.Primers;
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

@Service("PrimersService")
@Transactional
public class PrimersService {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retorna todos los registros
     */

    @SuppressWarnings("unchecked")
    public List<Primers> getPrimers() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Primers");
        // Retrieve all
        return  query.list();
    }

    /**
     * Retorna todos los registros activos
     */

    @SuppressWarnings("unchecked")
    public List<Primers> getActivePrimers() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Primers primers where primers.pasive = '0'");
        // Retrieve all
        return  query.list();
    }


    /**
     * Retorna un registro
     * @param primers_id
     */

    public Primers getPrimersByPrimers_id(String primers_id) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Primers primers where " +
                "primers.primers_id  =:primers_id");
        query.setParameter("primers_id",primers_id);
        Primers primers = (Primers) query.uniqueResult();
        return primers;
    }

    /**
     * Regresa un registro
     * @param systemId
     * @return un <code>Plasmids</code>
     */

    public Primers getPrimersBySystemId(String systemId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Primers primers where " +
                "primers.systemId =:systemId");
        query.setParameter("systemId",systemId);
        Primers primers = (Primers) query.uniqueResult();
        return primers;
    }


    @SuppressWarnings("unchecked")
    public List<Primers> getPrimersForBox(String boxId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Primers primers where primers.loc_box =:boxId");
        query.setParameter("boxId",boxId);
        // Retrieve all
        return  query.list();
    }

    /**
     * Guarda un registro
     *
     */
    public void savePrimers(Primers primers) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(primers);
    }

}
