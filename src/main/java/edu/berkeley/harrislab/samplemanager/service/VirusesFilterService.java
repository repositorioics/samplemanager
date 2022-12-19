package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.VirusessFilters;
import edu.berkeley.harrislab.samplemanager.domain.VirusesResults;
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

@Service("VirusesFilterService")
@Transactional
public class VirusesFilterService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;


	/**
	 * Metodo que retorna una lista de RVPs segun los parámetros de búsqueda ingresados
	 * param filter (rvpid, batchnum, primer_name, primer_sequence
	 * @return @return Lista de RVPsResults
	 */

    public List<VirusesResults> getVirusesByFilter(VirusessFilters filter){
        Session session = sessionFactory.getCurrentSession();

        String sQuery = "SELECT vrs.systemId as systemId, vrs.virusId as virusId,vrs.virus_Serotype  as virus_Serotype , vrs.strain as strain, " +
                "  vrs.batch_number as batch_number, vrs.passage as passage, vrs.experiment_id as experiment_id, " +
                " vrs.stage_of_production as stage_of_production,vrs.amount_of_Unconc_virus as amount_of_Unconc_virus,DATE_FORMAT(vrs.date_Uncon_Collected, '%Y-%m-%d') as date_Uncon_Collected, " +
                " vrs.num_aliquots as num_aliquots, vrs.aliquot_volume as aliquot_volume, vrs.amount_of_Conc_virus as amount_of_Conc_virus, " +
                " DATE_FORMAT(vrs.date_of_Conc, '%Y-%m-%d') as date_of_Conc, vrs.amount_of_Purified as amount_of_Purified,DATE_FORMAT(vrs.date_of_Purification, '%Y-%m-%d') as date_of_Purification," +
                "vrs.qc_PCR_Check as qc_PCR_Check, vrs.qc_ELISA_Check as qc_ELISA_Check, vrs.notes_on_QC as notes_on_QC, vrs.num_of_collections as num_of_collections," +
                "vrs.day_Virus_Collected as day_Virus_Collected, vrs.cell_line_and_passage as cell_line_and_passage, vrs.viral_inoculum_vial_ID as viral_inoculum_vial_ID,"+
                "vrs.bca_Concentration as bca_Concentration, vrs.fluorospot_check as fluorospot_check, vrs.notes_on_FS_check as notes_on_FS_check, vrs.viral_Titer as viral_Titer," +
                "vrs.storage_temperature as storage_temperature,vrs.comments as comments"+

                " (select e.name from Equip e where e.systemId = vrs.loc_freezer ) as loc_freezer, (select rk.name from Rack rk where vrs.loc_rack = rk.systemId ) as loc_rack, " +
                " (select b.name from Box b where b.systemId = vrs.loc_box ) as loc_box,vrs.loc_pos as loc_pos, " +
                " DATE_FORMAT(vrs.recordDate, '%Y-%m-%d') as recordDate, vrs.recordUser as recordUser, vrs.recordIp as recordIp, (CASE WHEN vrs.pasive = '1' then '0' else '1' END) as desPasive "+

                "FROM Viruses vrs where 1=1 ";

        Query queryVirusesFilter = null;

        if (filter.getVirusId() != null) {
            sQuery += "and vrs.virusId  like :virusId";
        }
        if (filter.getExperiment_id()!= null) {
            sQuery += "and vrs.experiment_id like : experiment_id";
        }

        if (filter.getVirus_Serotype() != null) {
            sQuery += "and vrs.virus_Serotype  like :virus_Serotype";
        }

        if (filter.getAliquot_volume() != null) {
            sQuery += "and vrs.aliquot_volume  like :aliquot_volume";
        }
        if (filter.getNum_aliquots() != null) {
            sQuery += "and vrs.num_aliquots  like :num_aliquots";
        }

        queryVirusesFilter =  session.createQuery(sQuery);

        if (filter.getVirusId() != null) {
            queryVirusesFilter.setParameter("rvpId", '%' + filter.getVirusId() + '%');
        }

        if (filter.getExperiment_id()!= null) {
            queryVirusesFilter.setParameter("experiment_id", '%' + filter.getExperiment_id()+ '%');
        }

        if (filter.getVirus_Serotype()!= null) {
            queryVirusesFilter.setParameter("virus_Serotype", '%' + filter.getVirus_Serotype()+ '%');
        }
        if (filter.getAliquot_volume() != null) {
            queryVirusesFilter.setParameter("aliquot_volume", '%' + filter.getAliquot_volume() + '%');
        }

        queryVirusesFilter.setResultTransformer(Transformers.aliasToBean(VirusesResults.class));
        System.out.println("queryRVPsFilter.list(); = "+ queryVirusesFilter.list());
        return queryVirusesFilter.list();
    }

	}
	

