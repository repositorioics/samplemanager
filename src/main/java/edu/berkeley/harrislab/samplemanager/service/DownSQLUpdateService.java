package edu.berkeley.harrislab.samplemanager.service;

import edu.berkeley.harrislab.samplemanager.domain.SqlQueryToInventory;
import edu.berkeley.harrislab.samplemanager.domain.SpecimensFilters;
import edu.berkeley.harrislab.samplemanager.domain.SpecimensResults;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import edu.berkeley.harrislab.samplemanager.domain.Rack;
/**
 * Created by Dell on 22/7/2021.
 */
@Service("DownSQLUpdateService")
@Transactional
public class DownSQLUpdateService {
    @Resource(name="sessionFactory")
    private SessionFactory sessionFactory;


    /**
     * Metodo que retorna una lista de Muestrass para actualizar

     * @return @return Lista de campos de query
     */

    public List<SqlQueryToInventory> getSpecimensByFilter1(){
        Session session = sessionFactory.getCurrentSession();

                String sQuery = "SELECT sp.specimenId as specimenId,sp.orthocode as orthocode,sp.specimenCondition as specimenCondition,sp.volUnits as volUnits,sp.volume as volume, " +
                " sp.varA as VarA,sp.varB as VarB,DATE_FORMAT(sp.labReceiptDate, '%Y-%m-%d')  as labReceiptDate ,sp.recordUser as recordUser,sp.specimenType as specimenType, " +
                " (select st.pos from SpecimenStorage st where sp.systemId = st.specimen) as position, " +
                " Study.id as Study_id, Subject.subjectId as Study_id2, (select st.box.id as box_id from SpecimenStorage st where sp.systemId = st.specimen ) as box_id, " +
                " (select st.box.rack.name as Rack_name from SpecimenStorage st where sp.systemId = st.specimen ) as Rack_name, " +
                " (select st.box.rack.equip.id as Equip_id from SpecimenStorage st where sp.systemId = st.specimen ) as Equip_id, " +
                " (select st.box.rack.equip.name as Equip_name from SpecimenStorage st where sp.systemId = st.specimen ) as Equip_name " +
                " from Specimen sp left join sp.subjectId as Subject left join Subject.studyId as Study  where sp.subjectId = Subject.systemId and Subject.studyId = Study.systemId " ;

        Query querySpecimenFilter = null;
        querySpecimenFilter =  session.createQuery(sQuery);
        querySpecimenFilter.setResultTransformer(Transformers.aliasToBean(SqlQueryToInventory.class));


       // Query query = null;
      //  List<ClassExcForm> query1 = session.createQuery(sQuery).list();
        //for (ClassExcForm datos : query1) {
       //    System.out.println("lista_entidades1::" + query1);
        //}
        return querySpecimenFilter.list();
    }

}
