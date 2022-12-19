package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.RVPsFilters;
import edu.berkeley.harrislab.samplemanager.domain.RvpsResults;
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

@Service("RVPsFilterService")
@Transactional
public class RVPsFilterService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;


	/**
	 * Metodo que retorna una lista de RVPs segun los parámetros de búsqueda ingresados
	 * param filter (rvpid, batchnum, primer_name, primer_sequence
	 * @return @return Lista de RVPsResults
	 */

    public List<RvpsResults> getRVPsByFilter(RVPsFilters filter){
        Session session = sessionFactory.getCurrentSession();

        String sQuery = "SELECT rvp.systemId as systemId, rvp.rvpId as rvpId,rvp.batchName as batchName , rvp.virusSerotype as virusSerotype, " +
                "  rvp.provider as provider, DATE_FORMAT(rvp.date_exp, '%Y-%m-%d') as date_exp, rvp.aliquot_volume as aliquot_volume, " +
                " rvp.num_aliq as num_aliq,rvp.working_dilution as working_dilution,rvp.infection_percentage as infection_percentage, " +
                " rvp.cellType as cellType, rvp.time_hpi as Time_hpi, rvp.comments as comments, " +

                " (select e.name from Equip e where e.systemId = rvp.loc_freezer ) as loc_freezer, (select rk.name from Rack rk where rvp.loc_rack = rk.systemId ) as loc_rack, " +
                " (select b.name from Box b where b.systemId = rvp.loc_box ) as loc_box,rvp.loc_pos as loc_pos, " +
                " DATE_FORMAT(rvp.recordDate, '%Y-%m-%d') as recordDate, rvp.recordUser as recordUser, rvp.recordIp as recordIp, (CASE WHEN rvp.pasive = '1' then '0' else '1' END) as desPasive "+

                "FROM RVP rvp where 1=1 ";

        Query queryRVPsFilter = null;

        if (filter.getRvpId() != null) {
            sQuery += "and rvp.rvpId  like :rvpId";
        }
        if (filter.getNum_aliq()!= null) {
            sQuery += "and rvp.num_aliq like :num_aliq";
        }

        if (filter.getVirusSerotype() != null) {
            sQuery += "and rvp.virusSerotype  like :virusSerotype";
        }

        if (filter.getBatchName() != null) {
            sQuery += "and rvp.batchName  like :batchName";
        }

        queryRVPsFilter =  session.createQuery(sQuery);

        if (filter.getRvpId() != null) {
            queryRVPsFilter.setParameter("rvpId", '%' + filter.getRvpId() + '%');
        }

        if (filter.getNum_aliq()!= null) {
            queryRVPsFilter.setParameter("num_aliq", '%' + filter.getNum_aliq()+ '%');
        }

        if (filter.getVirusSerotype()!= null) {
            queryRVPsFilter.setParameter("virusSerotype", '%' + filter.getVirusSerotype()+ '%');
        }
        if (filter.getBatchName() != null) {
            queryRVPsFilter.setParameter("batchName", '%' + filter.getBatchName() + '%');
        }

        queryRVPsFilter.setResultTransformer(Transformers.aliasToBean(RvpsResults.class));
        System.out.println("queryRVPsFilter.list(); = "+ queryRVPsFilter.list());
        return queryRVPsFilter.list();
    }

	}
	

