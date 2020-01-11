package org.ftd.mytask.web.cmds;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.ftd.mytask.web.adapters.LearningGridAdapter;
import org.ftd.mytask.web.mvc.abstracts.MVC;
import org.ftd.mytask.web.mvc.interfaces.ICmd;
import org.neogrid.dataquality.daos.LearningDAO;
import org.neogrid.dataquality.daos.TermObjectDAO;
import org.neogrid.dataquality.daos.TermObjectGroupDAO;
import org.neogrid.dataquality.daos.TermObjectTypeDAO;
import org.neogrid.dataquality.entities.Learning;
import org.neogrid.dataquality.entities.TermObject;
import org.neogrid.dataquality.entities.TermObjectGroup;
import org.neogrid.dataquality.entities.TermObjectType;

/**
 *
 * @author Fabio Tavares Dippold
 * @version 1.0.0 - 2018-10-25
 *
 */
public class LearningCmd extends MVC implements ICmd {

    @Override
    public String buildGridModel(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("urlToCreate",
                MVC.URL_MVC_SERVICE
                + "?" + MVC.PARAMETER_NAME_CMD
                + "=" + this.getClass().getSimpleName()
                + "&" + MVC.PARAMETER_NAME_ACTION
                + "=" + MVC.MVC_ACTION_BUILD_ADD_MODEL
        );

        request.setAttribute("urlToUpdate",
                MVC.URL_MVC_SERVICE
                + "?" + MVC.PARAMETER_NAME_CMD
                + "=" + this.getClass().getSimpleName()
                + "&" + MVC.PARAMETER_NAME_ACTION
                + "=" + MVC.MVC_ACTION_BUILD_UPD_MODEL
                + "&" + MVC.PARAMETER_NAME_ID
                + "="
        );

        request.setAttribute("urlToView",
                MVC.URL_MVC_SERVICE
                + "?" + MVC.PARAMETER_NAME_CMD
                + "=" + this.getClass().getSimpleName()
                + "&" + MVC.PARAMETER_NAME_ACTION
                + "=" + MVC.MVC_ACTION_BUILD_VIEW_MODEL
                + "&" + MVC.PARAMETER_NAME_ID
                + "="
        );

        final String[] headers = {"Grupo", "Objeto", "Termo1", "Termo2", "Tipo", "Sub Tipo", "Init", "Finish", "Ações"};

        request.setAttribute("headers", headers);
        request.setAttribute("title", "Aprendizados");
        request.setAttribute("datasource", findAllLearning());

        return "WEB-INF/views/LearningListView.jsp";
    }

    @Override
    public String buildAddNewModel(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("title", "Novo Aprendizado");

        request.setAttribute("urlToGo",
                MVC.URL_MVC_SERVICE
                + "?" + MVC.PARAMETER_NAME_CMD
                + "=" + this.getClass().getSimpleName()
                + "&" + MVC.PARAMETER_NAME_ACTION
                + "=" + MVC.MVC_ACTION_ADDNEW
        );

        request.setAttribute("urlToGoBack",
                MVC.URL_MVC_SERVICE
                + "?" + MVC.PARAMETER_NAME_CMD
                + "=" + this.getClass().getSimpleName()
                + "&" + MVC.PARAMETER_NAME_ACTION
                + "=" + MVC.MVC_ACTION_BUILD_GRID_MODEL
        );

        request.setAttribute("groups", findAllGroups());
        request.setAttribute("objects", findAllObjects());
        request.setAttribute("types", findAllTypes());

        return "WEB-INF/views/LearningCreateView.jsp";
    }

    @Override
    public String buildUpdateModel(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("title", "Editando Aprendizado");
        String id = MVC.readParameter(request, "id");

        request.setAttribute("urlToGo",
                MVC.URL_MVC_SERVICE
                + "?" + MVC.PARAMETER_NAME_CMD
                + "=" + this.getClass().getSimpleName()
                + "&" + MVC.PARAMETER_NAME_ACTION
                + "=" + MVC.MVC_ACTION_UPDATE
                + "&" + MVC.PARAMETER_NAME_ID
                + "=" + id
        );

        request.setAttribute("urlToGoBack",
                MVC.URL_MVC_SERVICE
                + "?" + MVC.PARAMETER_NAME_CMD
                + "=" + this.getClass().getSimpleName()
                + "&" + MVC.PARAMETER_NAME_ACTION
                + "=" + MVC.MVC_ACTION_BUILD_GRID_MODEL
        );

        /* FIND DE ENTITY... */
        Learning o = this.findLearning(Long.parseLong(id));
        request.setAttribute("entity", o);

        request.setAttribute("groups", findAllGroups());
        request.setAttribute("objects", findAllObjects());
        request.setAttribute("types", findAllTypes());

        return "WEB-INF/views/LearningUpdateView.jsp";
    }

    @Override
    public String buildViewModel(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("title", "Aprendizado");
        String id = MVC.readParameter(request, "id");

        request.setAttribute("urlToGo",
                MVC.URL_MVC_SERVICE
                + "?" + MVC.PARAMETER_NAME_CMD
                + "=" + this.getClass().getSimpleName()
                + "&" + MVC.PARAMETER_NAME_ACTION
                + "=" + MVC.MVC_ACTION_DELETE
                + "&" + MVC.PARAMETER_NAME_ID
                + "=" + id
        );

        request.setAttribute("urlToGoBack",
                MVC.URL_MVC_SERVICE
                + "?" + MVC.PARAMETER_NAME_CMD
                + "=" + this.getClass().getSimpleName()
                + "&" + MVC.PARAMETER_NAME_ACTION
                + "=" + MVC.MVC_ACTION_BUILD_GRID_MODEL
        );

        /* FIND DE ENTITY... */
        Learning o = this.findLearning(Long.parseLong(id));

        /* HTML FORM DEFINITIONS...*/
        StringBuilder description = new StringBuilder();
        description.append("Id: ");
        description.append(o.getId());
        description.append("\n");
        description.append("Grupo: ");
        description.append(findGroup(o.getTermObjectGroupId()));
        description.append("\n");
        description.append("Classificação: ");
        description.append(findTermObject(o.getTermObjectId()));
        description.append("\n");
        description.append("Termo-1: '");
        description.append(o.getTerm1());
        description.append("'\n");
        if (o.getTerm2() != null) {
            description.append("Termo-2: '");
            description.append(o.getTerm2());
            description.append("'\n");
        }
        description.append("Init Like: '");
        description.append(o.getInitLike());
        description.append("'\n");       
        description.append("Finish Like: '");
        description.append(o.getFinishLike());
        description.append("'\n");
        description.append("Tipo: ");
        description.append(findTermObjectType(o.getTermObjectTypeId()));
        description.append("\n");
        description.append("Sub-tipo: ");
        description.append(o.getTermObjectSubType());

        request.setAttribute("descriptionFieldLabel", "Informações");
        request.setAttribute("descriptionFieldValue", description.toString());
        request.setAttribute("descriptionFieldId", "inputDescription");
        request.setAttribute("descriptionFieldPlaceHolder", "Digite o valor da chave");
        request.setAttribute("descriptionFieldType", "textArea");
        request.setAttribute("descriptionFieldMaxSize", "255");
        request.setAttribute("descriptionFieldRows", "10");

        return "WEB-INF/views/BaseReadDeleteView.jsp";
    }

    @Override
    public String doAddNew(HttpServletRequest request, HttpServletResponse response) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);

        try {
            LearningDAO dao = new LearningDAO(factory);
            Learning o = new Learning();

            o.setTermObjectGroupId(Long.parseLong(MVC.readParameter(request, "groupInput")));
            o.setTermObjectId(Long.parseLong(MVC.readParameter(request, "objectInput")));
            o.setTerm1(MVC.readParameter(request, "term1Input"));
            o.setTerm2(MVC.readParameter(request, "term2Input"));
            o.setInitLike(MVC.readParameter(request, "initLikeInput"));
            o.setFinishLike(MVC.readParameter(request, "finishLikeInput"));
            o.setTermObjectTypeId(Long.parseLong(MVC.readParameter(request, "typeInput")));
            o.setTermObjectSubType(MVC.readParameter(request, "subTypeInput"));

            dao.create(o);

            request.setAttribute("msg", "Id=" + o.getId() + " " + MVC.MSG_CRUD_CREATE_SUCESS);

            return MVC.URL_MVC_SERVICE
                    + "?" + MVC.PARAMETER_NAME_CMD
                    + "=" + this.getClass().getSimpleName()
                    + "&" + MVC.PARAMETER_NAME_ACTION
                    + "=" + MVC.MVC_ACTION_BUILD_GRID_MODEL;

        } catch (Exception e) {

            //request.setAttribute("msg", MVC.MSG_CRUD_CREATE_FAILURE);
            request.setAttribute("msg", e.getMessage());
            
            return MVC.URL_MVC_SERVICE
                    + "?" + MVC.PARAMETER_NAME_CMD
                    + "=" + this.getClass().getSimpleName()
                    + "&" + MVC.PARAMETER_NAME_ACTION
                    + "=" + MVC.MVC_ACTION_BUILD_ADD_MODEL;
        }
    }

    @Override
    public String doUpdate(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(MVC.readParameter(request, "id"));
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
            LearningDAO dao = new LearningDAO(factory);
            Learning o = dao.findLearning(id);

            o.setTermObjectGroupId(Long.parseLong(MVC.readParameter(request, "groupInput")));
            o.setTermObjectId(Long.parseLong(MVC.readParameter(request, "objectInput")));
            o.setTerm1(MVC.readParameter(request, "term1Input"));
            o.setTerm2(MVC.readParameter(request, "term2Input"));
            o.setInitLike(MVC.readParameter(request, "initLikeInput"));
            o.setFinishLike(MVC.readParameter(request, "finishLikeInput"));
            o.setTermObjectTypeId(Long.parseLong(MVC.readParameter(request, "typeInput")));
            o.setTermObjectSubType(MVC.readParameter(request, "subTypeInput"));

            dao.edit(o);

            request.setAttribute("msg", "Id=" + o.getId() + " " + MVC.MSG_CRUD_UPDATE_SUCESS);

            return MVC.URL_MVC_SERVICE
                    + "?" + MVC.PARAMETER_NAME_CMD
                    + "=" + this.getClass().getSimpleName()
                    + "&" + MVC.PARAMETER_NAME_ACTION
                    + "=" + MVC.MVC_ACTION_BUILD_GRID_MODEL;

        } catch (Exception e) {

            request.setAttribute("msg", MVC.MSG_CRUD_UPDATE_FAILURE);

            return MVC.URL_MVC_SERVICE
                    + "?" + MVC.PARAMETER_NAME_CMD
                    + "=" + this.getClass().getSimpleName()
                    + "&" + MVC.PARAMETER_NAME_ACTION
                    + "=" + MVC.MVC_ACTION_BUILD_UPD_MODEL
                    + "&" + MVC.PARAMETER_NAME_ID
                    + "=" + id;

        }
    }

    @Override
    public String doRemove(HttpServletRequest request, HttpServletResponse response) {

        Long id = Long.parseLong(MVC.readParameter(request, "id"));
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
            LearningDAO dao = new LearningDAO(factory);
            dao.destroy(id);

            request.setAttribute("msg", "Id=" + id + " " + MVC.MSG_CRUD_DELETE_SUCESS);

            return MVC.URL_MVC_SERVICE
                    + "?" + MVC.PARAMETER_NAME_CMD
                    + "=" + this.getClass().getSimpleName()
                    + "&" + MVC.PARAMETER_NAME_ACTION
                    + "=" + MVC.MVC_ACTION_BUILD_GRID_MODEL;

        } catch (Exception e) {

            request.setAttribute("msg", MVC.MSG_CRUD_DELETE_FAILURE);

            return MVC.URL_MVC_SERVICE
                    + "?" + MVC.PARAMETER_NAME_CMD
                    + "=" + this.getClass().getSimpleName()
                    + "&" + MVC.PARAMETER_NAME_ACTION
                    + "=" + MVC.MVC_ACTION_BUILD_VIEW_MODEL
                    + "&" + MVC.PARAMETER_NAME_ID
                    + "=" + id;
        }
    }

    private Learning findLearning(Long id) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
        LearningDAO dao = new LearningDAO(factory);

        return dao.findLearning(id);
    }

    private TermObjectGroup findGroup(Long id) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
        TermObjectGroupDAO dao = new TermObjectGroupDAO(factory);

        return dao.findById(id);
    }

    private TermObject findTermObject(Long id) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
        TermObjectDAO dao = new TermObjectDAO(factory);

        return dao.findById(id);
    }

    private TermObjectType findTermObjectType(Long id) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
        TermObjectTypeDAO dao = new TermObjectTypeDAO(factory);

        return dao.findById(id);
    }

    private List<LearningGridAdapter> findAllLearning() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
        TermObjectGroupDAO termObjectGroupDAO = new TermObjectGroupDAO(factory);
        TermObjectDAO termObjectDAO = new TermObjectDAO(factory);
        TermObjectTypeDAO termObjectTypeDAO = new TermObjectTypeDAO(factory);
        LearningDAO dao = new LearningDAO(factory);
        List<Learning> lst = dao.findAllOrder();
        List<LearningGridAdapter> datasource = new ArrayList<>();

        lst.forEach((o) -> {
            TermObjectGroup group = termObjectGroupDAO.findById(o.getTermObjectGroupId());
            TermObject term = termObjectDAO.findById(o.getTermObjectId());
            TermObjectType type = termObjectTypeDAO.findById(o.getTermObjectTypeId());
            datasource.add(new LearningGridAdapter(o.getId(), group.getTermObjectGroupName(), term.getTermObjectName(), o.getTerm1(), o.getTerm2(), o.getInitLike(), o.getFinishLike(), type.getName(), o.getTermObjectSubType()));
        });

        return datasource;
    }

    private List<TermObjectGroup> findAllGroups() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
        TermObjectGroupDAO dao = new TermObjectGroupDAO(factory);

        return dao.findAll();
    }

    private List<TermObject> findAllObjects() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
        TermObjectDAO dao = new TermObjectDAO(factory);

        return dao.findAll();
    }

    private List<TermObjectType> findAllTypes() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(MVC.PERSISTENCE_UNIT);
        TermObjectTypeDAO dao = new TermObjectTypeDAO(factory);

        return dao.findAll();
    }

}
