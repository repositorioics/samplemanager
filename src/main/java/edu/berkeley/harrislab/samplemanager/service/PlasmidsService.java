package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.Plasmids;
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

@Service("PlasmidsService")
@Transactional
public class PlasmidsService {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retorna todos los registros
     */

    @SuppressWarnings("unchecked")
    public List<Plasmids> getPlasmids() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Plasmids");
        // Retrieve all
        return  query.list();
    }

    /**
     * Retorna todos los registros activos
     */

    @SuppressWarnings("unchecked")
    public List<Plasmids> getActivePlasmids() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Plasmids plasmids where plasmids.pasive = '0'");
        // Retrieve all
        return  query.list();
    }


    /**
     * Retorna un registro
     * @param plasmids_id
     */

    public Plasmids getPlasmidsByPlasmids_id(String plasmids_id) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Plasmids plasmids where " +
                "plasmids.plasmids_id  =:plasmids_id");
        query.setParameter("plasmids_id",plasmids_id);
        Plasmids plasmids = (Plasmids) query.uniqueResult();
        return plasmids;
    }

    /**
     * Regresa un registro
     * @param systemId
     * @return un <code>Plasmids</code>
     */

    public Plasmids getPlasmidsBySystemId(String systemId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Plasmids plasmids where " +
                "plasmids.systemId =:systemId");
        query.setParameter("systemId",systemId);
        Plasmids plasmids = (Plasmids) query.uniqueResult();
        return plasmids;
    }


    @SuppressWarnings("unchecked")
    public List<Plasmids> getPlasmidsForBox(String boxId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Plasmids plasmids where plasmids.loc_box =:boxId");
        query.setParameter("boxId",boxId);
        // Retrieve all
        return  query.list();
    }

    /**
     * Guarda un registro
     *
     */
    public void savePlasmids(Plasmids plasmids) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(plasmids);
    }

}
