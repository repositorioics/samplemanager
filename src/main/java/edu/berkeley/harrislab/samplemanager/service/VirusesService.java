package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.Viruses;
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

@Service("VirusesService")
@Transactional
public class VirusesService {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retorna todos los registros
     */

    @SuppressWarnings("unchecked")
    public List<Viruses> getViruses() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Viruses");
        // Retrieve all
        return  query.list();
    }

    /**
     * Retorna todos los registros activos
     */

    @SuppressWarnings("unchecked")
    public List<Viruses> getActiveViruses() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Viruses viruses where viruses.pasive = '0'");
        // Retrieve all
        return  query.list();
    }


    /**
     * Retorna un registro
     * @param virusId
     */

    public Viruses getRVPsByrvpId(String virusid) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Viruses viruses where " +
                "viruses.virusId  =: virusid");
        query.setParameter("virusid",virusid);
        Viruses vrs = (Viruses) query.uniqueResult();
        return vrs;
    }

    /**
     * Regresa un registro
     * @param systemId
     * @return un <code>RVP</code>
     */

    public Viruses getVirusesBySystemId(String systemId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Viruses viruses where " +
                "viruses.systemId =:systemId");
        query.setParameter("systemId",systemId);
        Viruses vrs = (Viruses) query.uniqueResult();
        return vrs;
    }


    @SuppressWarnings("unchecked")
    public List<Viruses> getVirusesForBox(String boxId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM Viruses viruses where viruses.loc_box =:boxId");
        query.setParameter("boxId",boxId);
        // Retrieve all
        return  query.list();
    }

    /**
     * Guarda un registro
     *
     */
    public void saveViruses(Viruses viruses) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(viruses);
    }

}
