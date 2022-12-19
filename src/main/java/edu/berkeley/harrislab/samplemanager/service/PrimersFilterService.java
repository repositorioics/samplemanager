package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.PrimersFilters;
import edu.berkeley.harrislab.samplemanager.domain.PrimersResults;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Servicio para el objeto Specimen Filter
 * 
 *
 * 
 **/

@Service("PrimersFilterService")
@Transactional
public class PrimersFilterService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;


	/**
	 * Metodo que retorna una lista de Primers segun los parámetros de búsqueda ingresados
	 * param filter (primers_id, primer_number, primer_name, primer_sequence
	 * @return @return Lista de PrimersResults
	 */

    public List<PrimersResults> getPlasmidsByFilter(PrimersFilters filter){
        Session session = sessionFactory.getCurrentSession();

        String sQuery = "SELECT primers.systemId as systemId, primers.primers_id as primers_id,primers.primer_number as primer_number , primers.position_in_sequence as position_in_sequence, " +
                " DATE_FORMAT(primers.date_received, '%Y-%m-%d') as date_received, primers.primer_name as primer_name, primers.primer_description as primer_description, primers.primer_sequence as primer_sequence, " +
                " primers.primer_lenght as primer_lenght,primers.primer_designer as primer_designer, primers.comments as comments, " +

                " (select e.name from Equip e where e.systemId = primers.loc_freezer ) as loc_freezer, (select rk.name from Rack rk where primers.loc_rack = rk.systemId ) as loc_rack, " +
                " (select b.name from Box b where b.systemId = primers.loc_box ) as loc_box,primers.loc_pos as loc_pos, " +
                " DATE_FORMAT(primers.recordDate, '%Y-%m-%d') as recordDate, primers.recordUser as recordUser, primers.recordIp as recordIp, (CASE WHEN primers.pasive = '1' then '0' else '1' END) as desPasive "+

                "FROM Primers primers where 1=1 ";

        Query queryPrimersFilter = null;

        if (filter.getPrimers_id() != null) {
            sQuery += "and primers.primers_id  like :primers_id";
        }
        if (filter.getPrimer_number()!= null) {
            sQuery += "and primers.primer_number like :primer_number";
        }

        if (filter.getPrimer_name() != null) {
            sQuery += "and primers.primer_name  like :primer_name";
        }

        if (filter.getPrimer_sequence() != null) {
            sQuery += "and primers.primer_sequence  like :primer_sequence";
        }

        queryPrimersFilter =  session.createQuery(sQuery);

        if (filter.getPrimers_id() != null) {
            queryPrimersFilter.setParameter("primers_id", '%' + filter.getPrimers_id() + '%');
        }

        if (filter.getPrimer_number()!= null) {
            queryPrimersFilter.setParameter("primer_number", '%' + filter.getPrimer_number()+ '%');
        }

        if (filter.getPrimer_name()!= null) {
            queryPrimersFilter.setParameter("primer_name", '%' + filter.getPrimer_name()+ '%');
        }
        if (filter.getPrimer_sequence() != null) {
            queryPrimersFilter.setParameter("primer_sequence", '%' + filter.getPrimer_sequence() + '%');
        }

        queryPrimersFilter.setResultTransformer(Transformers.aliasToBean(PrimersResults.class));
        System.out.println("queryPrimersFilter.list(); = "+ queryPrimersFilter.list());
        return queryPrimersFilter.list();
    }

	}
	

