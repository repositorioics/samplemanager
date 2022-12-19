package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.RVP;
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

@Service("RVPsService")
@Transactional
public class RVPsService {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retorna todos los registros
     */

    @SuppressWarnings("unchecked")
    public List<RVP> getRVPs() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM RVP");
        // Retrieve all
        return  query.list();
    }

    /**
     * Retorna todos los registros activos
     */

    @SuppressWarnings("unchecked")
    public List<RVP> getActiveRVPs() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM RVP rvp where rvp.pasive = '0'");
        // Retrieve all
        return  query.list();
    }


    /**
     * Retorna un registro
     * @param rvpId
     */

    public RVP getRVPsByrvpId(String rvpId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM RVP rvp where " +
                "rvp.rvpId  =:rvpId");
        query.setParameter("rvpId",rvpId);
        RVP rvp = (RVP) query.uniqueResult();
        return rvp;
    }

    /**
     * Regresa un registro
     * @param systemId
     * @return un <code>RVP</code>
     */

    public RVP getRVPsBySystemId(String systemId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM RVP rvp where " +
                "rvp.systemId =:systemId");
        query.setParameter("systemId",systemId);
        RVP rvp = (RVP) query.uniqueResult();
        return rvp;
    }


    @SuppressWarnings("unchecked")
    public List<RVP> getRVPsForBox(String boxId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM RVP rvp where rvp.loc_box =:boxId");
        query.setParameter("boxId",boxId);
        // Retrieve all
        return  query.list();
    }

    /**
     * Guarda un registro
     *
     */
    public void saveRVPs(RVP rvp) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(rvp);
    }

}
