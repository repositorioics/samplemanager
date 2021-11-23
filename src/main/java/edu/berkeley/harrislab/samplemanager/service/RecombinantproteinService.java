package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.RecombinantProtein;
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

@Service("RecombinantProteinService")
@Transactional
public class RecombinantproteinService {

    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;

    /**
     * Retorna todos los registros
     */

    @SuppressWarnings("unchecked")
    public List<RecombinantProtein> getRecombinantProtein() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM RecombinantProtein");
        // Retrieve all
        return  query.list();
    }

    /**
     * Retorna todos los registros activos
     */

    @SuppressWarnings("unchecked")
    public List<RecombinantProtein> getActiveRecombinantProtein() {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM RecombinantProtein recombinantProtein where recombinantProtein.pasive = '0'");
        // Retrieve all
        return  query.list();
    }


    /**
     * Retorna un registro
     * @param protrecombinante_id
     */

    public RecombinantProtein getRecombinantProteinByRecombinantProtein_id(String protrecombinante_id) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM RecombinantProtein recombinantProtein where " +
                "recombinantProtein.protrecombinante_id  =:protrecombinante_id");
        query.setParameter("protrecombinante_id",protrecombinante_id);
        RecombinantProtein recombinantProtein = (RecombinantProtein) query.uniqueResult();
        return recombinantProtein;
    }

    /**
     * Regresa un registro
     * @param systemId
     * @return un <code>RecombinantProtein</code>
     */

    public RecombinantProtein getRecombninantProteinBySystemId(String systemId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM RecombinantProtein recombinantProtein where " +
                "recombinantProtein.systemId =:systemId");
        query.setParameter("systemId",systemId);
        RecombinantProtein recombinantProtein = (RecombinantProtein) query.uniqueResult();
        return recombinantProtein;
    }


    @SuppressWarnings("unchecked")
    public List<RecombinantProtein> getRecombninantProteinForBox(String boxId) {
        // Retrieve session from Hibernate
        Session session = sessionFactory.getCurrentSession();
        // Create a Hibernate query (HQL)
        Query query = session.createQuery("FROM RecombinantProtein recombinantProtein where recombinantProtein.loc_box =:boxId");
        query.setParameter("boxId",boxId);
        // Retrieve all
        return  query.list();
    }

    /**
     * Guarda un registro
     *
     */
    public void saveRecombinantProtein(RecombinantProtein recombinantProtein) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(recombinantProtein);
    }

}
