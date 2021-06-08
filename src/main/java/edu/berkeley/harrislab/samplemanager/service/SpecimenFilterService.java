package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.SpecimensResults;
import edu.berkeley.harrislab.samplemanager.domain.SpecimensFilters;
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

@Service("specimenFilterService")
@Transactional
public class SpecimenFilterService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;


	/**
	 * Metodo que retorna una lista de Muestras segun los parámetros de búsqueda ingresados
	 * param filter (specimenId, orthocode, date, studyId, box
	 * @return @return Lista de SpecimensResults
	 */

    public List<SpecimensResults> getSpecimensByFilter(SpecimensFilters filter){
        Session session = sessionFactory.getCurrentSession();

        String sQuery = "SELECT specimen.systemId as systemId, specimen.specimenId as specimenId,  DATE_FORMAT(specimen.labReceiptDate, '%Y-%m-%d')  as labReceiptDate, specimen.specimenType as specimenType, specimen.specimenCondition as specimenCondition, " +
                "specimen.varA as varA, specimen.varB as varB, specimen.volume as volume, specimen.volUnits as volUnits, (select subj.systemId from Subject as subj where subj.systemId = specimen.subjectId.systemId) as subjectId , (select subj.subjectId from Subject as subj where subj.systemId = specimen.subjectId.systemId ) as subjectName, specimen.inStorage as inStorage,  " +
                "specimen.orthocode as orthocode, specimen.obs as obs, DATE_FORMAT(specimen.recordDate, '%Y-%m-%d') as recordDate, specimen.recordUser as recordUser, specimen.recordIp as recordIp, (CASE WHEN specimen.pasive = '1' then '0' else '1' END) as desPasive  " +
                "FROM Specimen specimen where 1=1 ";

        Query querySpecimenFilter = null;

        if (filter.getSpecimenId() != null) {
            sQuery += "and specimen.specimenId like :specimenId";
        }
        if (filter.getOrthocode() != null) {
            sQuery += "and specimen.orthocode =:orthocode ";
        }

        if (filter.getLabReceiptDate() != null) {
            sQuery += "and DATE_FORMAT(specimen.labReceiptDate, '%Y-%m-%d') =:labReceiptDate ";

        }
        if (filter.getStudyId() != null) {
            sQuery += "and specimen.subjectId.systemId =:studyId ";
        }

        if (filter.getBox() != null) {
            sQuery += "and specimen.systemId in (select storage.specimen.systemId FROM SpecimenStorage storage where storage.box.name =:box) ";
        }

        querySpecimenFilter =  session.createQuery(sQuery);

        if (filter.getSpecimenId() != null) {
            querySpecimenFilter.setParameter("specimenId", '%' + filter.getSpecimenId() + '%');
        }

        if (filter.getOrthocode()!= null) {
            querySpecimenFilter.setParameter("orthocode", filter.getOrthocode());
        }

        if (filter.getLabReceiptDate()!= null) {
            querySpecimenFilter.setParameter("labReceiptDate", filter.getLabReceiptDate());
        }

        if (filter.getStudyId()!= null) {
            querySpecimenFilter.setParameter("studyId", filter.getStudyId());
        }

        if (filter.getBox()!= null) {
            querySpecimenFilter.setParameter("box", filter.getBox());
        }

        querySpecimenFilter.setResultTransformer(Transformers.aliasToBean(SpecimensResults.class));
        return querySpecimenFilter.list();
    }

	}
	

