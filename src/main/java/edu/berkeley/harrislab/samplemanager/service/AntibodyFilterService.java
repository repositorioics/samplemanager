package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.AntibodiesResults;
import edu.berkeley.harrislab.samplemanager.domain.AntibodysFilters;
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
 * @author William Aviles
 * 
 **/

@Service("antibodyFilterService")
@Transactional
public class AntibodyFilterService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;


	/**
	 * Metodo que retorna una lista de Anticuerpos segun los parámetros de búsqueda ingresados
	 * param filter (antibody_id, antibody_name
	 * @return @return Lista de AntibodyResults
	 */

    public List<AntibodiesResults> getAntibodyByFilter(AntibodysFilters filter){
        Session session = sessionFactory.getCurrentSession();

        String sQuery = "SELECT antibody.systemId as systemId, antibody.antibody_id as antibody_id,antibody.antibody_name as antibody_name , antibody.target_protein as target_protein, " +
                " antibody.target_domain as target_domain, antibody.target_epitope as target_epitope, antibody.antibody_isotype as antibody_isotype, antibody.recognizes as recognizes, " +
                " antibody.batch_number as batch_number, DATE_FORMAT(antibody.date_produced, '%Y-%m-%d') as date_produced, antibody.person_name as person_name, antibody.source_name as source_name, antibody.raised_in as raised_in, " +
                " antibody.aliquot_volume as aliquot_volume, antibody.medium_buffer as medium_buffer, antibody.concentration as concentration, antibody.technique1 as technique1, antibody.technique1_concentration1 as technique1_concentration1, " +
                " antibody.technique2 as technique2, antibody.technique2_concentration2 as technique2_concentration2, antibody.technique3 as technique3, antibody.technique3_concentration3 as technique3_concentration3, " +
                " antibody.storage_temperature as storage_temperature, (select e.name from Equip e where e.systemId = antibody.freezer_name ) as freezer_name, (select rk.name from Rack rk where antibody.rack_name = rk.systemId ) as rack_name, " +
                " (select b.name from Box b where b.systemId = antibody.box_name ) as box_name,antibody.position_in_the_box as position, antibody.additional_comments as additional_comments, " +
                " DATE_FORMAT(antibody.recordDate, '%Y-%m-%d') as recordDate, antibody.recordUser as recordUser, antibody.recordIp as recordIp, (CASE WHEN antibody.pasive = '1' then '0' else '1' END) as desPasive "+

                "FROM Antibody antibody where 1=1 ";

        Query queryAntibodyFilter = null;

        if (filter.getantibody_id() != null) {
            sQuery += "and antibody.antibody_id like :antibody_id";
        }
        if (filter.getantibody_name() != null) {
            sQuery += "and antibody.antibody_name like :antibody_name";
        }

        queryAntibodyFilter =  session.createQuery(sQuery);

        if (filter.getantibody_id() != null) {
            queryAntibodyFilter.setParameter("antibody_id", '%' + filter.getantibody_id() + '%');
        }

        if (filter.getantibody_name()!= null) {
            queryAntibodyFilter.setParameter("antibody_name", '%' + filter.getantibody_name()+ '%');
        }


        queryAntibodyFilter.setResultTransformer(Transformers.aliasToBean(AntibodiesResults.class));
        return queryAntibodyFilter.list();
    }

	}
	

