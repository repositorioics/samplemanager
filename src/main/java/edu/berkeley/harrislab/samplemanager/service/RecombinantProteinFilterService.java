package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.RecombinantProteinResults ;
import edu.berkeley.harrislab.samplemanager.domain.RecombinantProteinsFilters;
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

@Service("RecombinantProteinFilterService")
@Transactional
public class RecombinantProteinFilterService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;


	/**
	 * Metodo que retorna una lista de Anticuerpos segun los parámetros de búsqueda ingresados
	 * param filter (antibody_id, antibody_name
	 * @return @return Lista de AntibodyResults
	 */

    public List<RecombinantProteinResults> getRecombinantProteinByFilter(RecombinantProteinsFilters filter){
        Session session = sessionFactory.getCurrentSession();

        String sQuery = "SELECT recombinantProtein.systemId as systemId, recombinantProtein.protrecombinante_id as protrecombinante_id,recombinantProtein.protein_name as protein_name , recombinantProtein.protein_origin as protein_origin, " +
                " recombinantProtein.construct_name as Construct_name, DATE_FORMAT(recombinantProtein.date_transfection, '%Y-%m-%d') as date_transfection, recombinantProtein.vol_sn as vol_sn, recombinantProtein.pur_method as pur_method, " +
                " recombinantProtein.frac_retained as frac_retained,recombinantProtein.vol_usable as vol_usable, recombinantProtein.dialysis_buffer as dialysis_buffer,DATE_FORMAT(recombinantProtein.date_purification, '%Y-%m-%d') as date_purification , " +
                " recombinantProtein.num_aliquot as num_aliquot, recombinantProtein.vol_aliquot as vol_aliquot, recombinantProtein.conc_protein as conc_protein, " +
                " (select e.name from Equip e where e.systemId = recombinantProtein.loc_freezer ) as loc_freezer, (select rk.name from Rack rk where recombinantProtein.loc_rack = rk.systemId ) as loc_rack, " +
                " (select b.name from Box b where b.systemId = recombinantProtein.loc_box ) as loc_box,recombinantProtein.loc_pos as loc_pos, recombinantProtein.comments as comments, " +
                " DATE_FORMAT(recombinantProtein.recordDate, '%Y-%m-%d') as recordDate, recombinantProtein.recordUser as recordUser, recombinantProtein.recordIp as recordIp, (CASE WHEN recombinantProtein.pasive = '1' then '0' else '1' END) as desPasive "+

                "FROM RecombinantProtein recombinantProtein where 1=1 ";

        Query queryRecombinantProteinFilter = null;

        if (filter.getProtrecombinante_id() != null) {
            sQuery += "and recombinantProtein.protrecombinante_id  like :protrecombinante_id";
        }
        if (filter.getProtein_name()!= null) {
            sQuery += "and recombinantProtein.protein_name like :protein_name";
        }

        if (filter.getProtein_origin() != null) {
            sQuery += "and recombinantProtein.protein_origin  like :protein_origin";
        }
        if (filter.getConstruct_name()!= null) {
            sQuery += "and recombinantProtein.Construct_name like :Construct_name";
        }
        if (filter.getDate_transfection()!= null) {
            sQuery += "and recombinantProtein.date_transfection like :date_transfection";
        }


        queryRecombinantProteinFilter =  session.createQuery(sQuery);

        if (filter.getProtrecombinante_id() != null) {
            queryRecombinantProteinFilter.setParameter("protrecombinante_id", '%' + filter.getProtrecombinante_id() + '%');
        }

        if (filter.getProtein_name()!= null) {
            queryRecombinantProteinFilter.setParameter("protein_name", '%' + filter.getProtein_name()+ '%');
        }
        if (filter.getProtein_origin() != null) {
            queryRecombinantProteinFilter.setParameter("protein_origin", '%' + filter.getProtein_origin() + '%');
        }

        if (filter.getConstruct_name()!= null) {
            queryRecombinantProteinFilter.setParameter("Construct_name", '%' + filter.getConstruct_name()+ '%');
        }

        if (filter.getDate_transfection()!= null) {
            queryRecombinantProteinFilter.setParameter("date_transfection", '%' + filter.getDate_transfection().toString() + '%');
        }

        queryRecombinantProteinFilter.setResultTransformer(Transformers.aliasToBean(RecombinantProteinResults.class));
        System.out.println("queryRecombinantProteinFilter.list(); = "+ queryRecombinantProteinFilter.list());
        return queryRecombinantProteinFilter.list();
    }

	}
	

