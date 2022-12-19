package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.PlasmidsResults;
import edu.berkeley.harrislab.samplemanager.domain.PlasmidsFilters;
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

@Service("PlasmidsFilterService")
@Transactional
public class PlasmidsFilterService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;


	/**
	 * Metodo que retorna una lista de Anticuerpos segun los parámetros de búsqueda ingresados
	 * param filter (antibody_id, antibody_name
	 * @return @return Lista de AntibodyResults
	 */

    public List<PlasmidsResults> getPlasmidsByFilter(PlasmidsFilters filter){
        Session session = sessionFactory.getCurrentSession();

        String sQuery = "SELECT plasmids.systemId as systemId, plasmids.plasmids_id as plasmids_id,plasmids.plasmid_name as plasmid_name , plasmids.sequencing_confirmed as sequencing_confirmed, " +
                " plasmids.sequencing_primers as sequencing_primers, plasmids.backbone_vector as backbone_vector, plasmids.antibiotic_resistant as antibiotic_resistant, plasmids.growth_conditions as growth_conditions, " +
                " plasmids.midiprep_purified as midiprep_purified,plasmids.concentration as concentration, plasmids.comments as comments,plasmids.glycerol_stock as glycerol_stock , " +

                " (select e.name from Equip e where e.systemId = plasmids.loc_freezer ) as loc_freezer, (select rk.name from Rack rk where plasmids.loc_rack = rk.systemId ) as loc_rack, " +
                " (select b.name from Box b where b.systemId = plasmids.loc_box ) as loc_box,plasmids.loc_pos as loc_pos, " +
                " DATE_FORMAT(plasmids.recordDate, '%Y-%m-%d') as recordDate, plasmids.recordUser as recordUser, plasmids.recordIp as recordIp, (CASE WHEN plasmids.pasive = '1' then '0' else '1' END) as desPasive "+

                "FROM Plasmids plasmids where 1=1 ";

        Query queryPlasmidsFilter = null;

        if (filter.getPlasmids_id() != null) {
            sQuery += "and plasmids.plasmids_id  like :plasmids_id";
        }
        if (filter.getPlasmid_name()!= null) {
            sQuery += "and plasmids.plasmid_name like :plasmid_name";
        }

        if (filter.getSequencing_primers() != null) {
            sQuery += "and plasmids.sequencing_primers  like :sequencing_primers";
        }

        queryPlasmidsFilter =  session.createQuery(sQuery);

        if (filter.getPlasmids_id() != null) {
            queryPlasmidsFilter.setParameter("plasmids_id", '%' + filter.getPlasmids_id() + '%');
        }

        if (filter.getPlasmid_name()!= null) {
            queryPlasmidsFilter.setParameter("plasmid_name", '%' + filter.getPlasmid_name()+ '%');
        }
        if (filter.getSequencing_primers() != null) {
            queryPlasmidsFilter.setParameter("sequencing_primers", '%' + filter.getSequencing_primers() + '%');
        }

        queryPlasmidsFilter.setResultTransformer(Transformers.aliasToBean(PlasmidsResults.class));
        System.out.println("queryPlasmidsFilter.list(); = "+ queryPlasmidsFilter.list());
        return queryPlasmidsFilter.list();
    }

	}
	

